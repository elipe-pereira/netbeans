/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.upgrade;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import org.netbeans.util.Util;
import org.openide.ErrorManager;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.LocalFileSystem;
import org.openide.filesystems.MultiFileSystem;
import org.openide.filesystems.XMLFileSystem;
import org.openide.modules.InstalledFileLocator;
import org.openide.modules.SpecificationVersion;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.xml.sax.SAXException;

/** pending
 *
 * @author  Jiri Rechtacek, Jiri Skrivanek
 */
public final class AutoUpgrade {

    private static final Logger LOGGER = Logger.getLogger(AutoUpgrade.class.getName());

    public static void main (String[] args) throws Exception {
        // show warning if starts for the 1st time on changed userdir (see issue 196075)
        String noteChangedDefaults = "";
        if (madeObsoleteMessagesLog()) {
            noteChangedDefaults = NbBundle.getMessage (AutoUpgrade.class, "MSG_ChangedDefaults", System.getProperty ("netbeans.user", "")); // NOI18N
        }
        
        // try new place
        File sourceFolder = checkPreviousOnOsSpecificPlace (APACHE_VERSION_TO_CHECK);
        if (sourceFolder != null) {
            if (!showUpgradeDialog (sourceFolder, noteChangedDefaults)) {
                throw new org.openide.util.UserCancelException ();
            }
        } else if (! noteChangedDefaults.isEmpty()) {
            // show a note only
            showNoteDialog(noteChangedDefaults);
        }
    }

    static final Comparator<String> APACHE_VERSION_COMPARATOR = (v1, v2) -> new SpecificationVersion(v1).compareTo(new SpecificationVersion(v2));
    
    static final List<String> APACHE_VERSION_TO_CHECK = Arrays.asList(NbBundle.getMessage(AutoUpgrade.class, "apachenetbeanspreviousversion").split(",")).stream().sorted(APACHE_VERSION_COMPARATOR.reversed()).collect(Collectors.toList());
    
    private static File checkPreviousOnOsSpecificPlace (final List<String> versionsToCheck) {
        String defaultUserdirRoot = System.getProperty ("netbeans.default_userdir_root"); // NOI18N
        File sourceFolder;
        if (defaultUserdirRoot != null) {
            File userHomeFile = new File (defaultUserdirRoot);
            for (String ver : versionsToCheck) {
                sourceFolder = new File (userHomeFile.getAbsolutePath (), ver);
                if (sourceFolder.exists () && sourceFolder.isDirectory ()) {
                    return sourceFolder;
                }
            }
        }
        return null;
    }
    
    private static boolean madeObsoleteMessagesLog() {
        String ud = System.getProperty ("netbeans.user", "");
        if ((Utilities.isMac() || Utilities.isWindows()) && ud.endsWith(File.separator + "dev")) { // NOI18N
            String defaultUserdirRoot = System.getProperty ("netbeans.default_userdir_root", null); // NOI18N
            if (defaultUserdirRoot != null) {
                if (new File(ud).getParentFile().equals(new File(defaultUserdirRoot))) {
                    // check the former default root
                    String userHome = System.getProperty("user.home"); // NOI18N
                    if (userHome != null) {
                        File oldUserdir = new File(new File (userHome).getAbsolutePath (), ".netbeans/dev"); // NOI18N
                        if (oldUserdir.exists() && ! oldUserdir.equals(new File(ud))) {
                            // 1. modify messages log
                            File log = new File (oldUserdir, "/var/log/messages.log");
                            File obsolete = new File (oldUserdir, "/var/log/messages.log.obsolete");
                            if (! obsolete.exists() && log.exists()) {
                                return log.renameTo(obsolete);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private static boolean showUpgradeDialog (final File source, String note) {
        Util.setDefaultLookAndFeel();

	JPanel panel = new JPanel(new BorderLayout());
	panel.add(new AutoUpgradePanel (source.getAbsolutePath (), note), BorderLayout.CENTER);
	JProgressBar progressBar = new JProgressBar(0, 100);
	progressBar.setValue(0);
	progressBar.setStringPainted(true);
	progressBar.setIndeterminate(true);
	panel.add(progressBar, BorderLayout.SOUTH);
	progressBar.setVisible(false);
	
	JButton bYES = new JButton("Yes");
	bYES.setMnemonic(KeyEvent.VK_Y);
	JButton bNO = new JButton("No");
	bNO.setMnemonic(KeyEvent.VK_N);
	JButton[] options = new JButton[] {bYES, bNO};
        JOptionPane p = new JOptionPane (panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, null, options, bYES);
        JDialog d = Util.createJOptionProgressDialog(p, NbBundle.getMessage (AutoUpgrade.class, "MSG_Confirmation_Title"), source, progressBar);
        d.setVisible (true);

        return Integer.valueOf(JOptionPane.YES_OPTION).equals (p.getValue ());
    }

    private static void showNoteDialog (String note) {
        Util.setDefaultLookAndFeel();
        JOptionPane p = new JOptionPane(new AutoUpgradePanel (null, note), JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog d = Util.createJOptionDialog(p, NbBundle.getMessage (AutoUpgrade.class, "MSG_Note_Title"));
        d.setVisible (true);
    }

    static void doUpgrade (File source, String oldVersion) 
    throws java.io.IOException, java.beans.PropertyVetoException {        
        File userdir = new File(System.getProperty ("netbeans.user", "")); // NOI18N

        java.util.Set<?> includeExclude;
        try (Reader r = new InputStreamReader (
                    AutoUpgrade.class.getResourceAsStream ("copy" + oldVersion), // NOI18N
                    "utf-8"); // NOI18N
                ) {
            includeExclude = IncludeExclude.create (r);
        } catch (IOException ex) {
            throw new IOException("Cannot import from version: " + oldVersion, ex);
        }

        ErrorManager.getDefault ().log (
            ErrorManager.USER, "Import: Old version: " // NOI18N
            + oldVersion + ". Importing from " + source + " to " + userdir // NOI18N
        );
        
        File oldConfig = new File (source, "config"); // NOI18N
        org.openide.filesystems.FileSystem old;
        {
            LocalFileSystem lfs = new LocalFileSystem ();
            lfs.setRootDirectory (oldConfig);
            
            XMLFileSystem xmlfs = null;
            try {
                URL url = AutoUpgrade.class.getResource("layer" + oldVersion + ".xml"); // NOI18N
                xmlfs = (url != null) ? new XMLFileSystem(url) : null;
            } catch (SAXException ex) {
                throw new IOException("Cannot import from version: " + oldVersion, ex);
            }
            
            old = (xmlfs != null) ? createLayeredSystem(lfs, xmlfs) : lfs;
        }
        
        Copy.copyDeep (old.getRoot (), FileUtil.getConfigRoot (), includeExclude, PathTransformation.getInstance(oldVersion));
        
    }
    
    static MultiFileSystem createLayeredSystem(final LocalFileSystem lfs, final XMLFileSystem xmlfs) {
        MultiFileSystem old;
        
        old = new MultiFileSystem (
            new org.openide.filesystems.FileSystem[] { lfs, xmlfs }
        ) {
            {
                setPropagateMasks(true);
            }
        };
        return old;
    }

    /* Copy files from source folder to current userdir according to include/exclude
     * patterns in etc/netbeans.import file. */
    private static void copyToUserdir(File source) throws IOException, PropertyVetoException {
        File userdir = new File(System.getProperty("netbeans.user", "")); // NOI18N
        File netBeansDir = InstalledFileLocator.getDefault().locate("modules", null, false).getParentFile().getParentFile();  //NOI18N
        File importFile = new File(netBeansDir, "etc/netbeans.import");  //NOI18N
        LOGGER.fine("Import file: " + importFile);
        LOGGER.info("Importing from " + source + " to " + userdir); // NOI18N
        CopyFiles.copyDeep(source, userdir, importFile);
    }

    public static void doCopyToUserDir(File source) throws IOException, PropertyVetoException {
	copyToUserdir(source);
    }
}
