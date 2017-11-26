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
package org.netbeans.modules.spellchecker.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.options.OptionsDisplayer;
import org.netbeans.modules.spellchecker.Utilities;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.DialogDisplayer;
import org.openide.NotificationLineSupport;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

@OptionsPanelController.Keywords(keywords = {"install", "dictionary", "#KW_DictionaryInstallerOptions"}, location = OptionsDisplayer.EDITOR, tabTitle="#TITLE_OptionsPanel")
public class DictionaryInstallerPanel extends javax.swing.JPanel {
    private final JButton okButton;

    private final Collection<? extends String> existingLocales;
    
    public DictionaryInstallerPanel (JButton okButton, Collection<? extends String> existingLocales) {
        this.okButton = okButton;
        this.existingLocales = existingLocales;
        
        initComponents ();
        initValues ();
        DocumentListener l = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateErrors(); }
            public void removeUpdate(DocumentEvent e) { updateErrors(); }
            public void changedUpdate(DocumentEvent e) {}
        };

        tDictionary.getDocument().addDocumentListener(l);
        tLocale.getDocument().addDocumentListener(l);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        file = new javax.swing.JLabel();
        tDictionary = new javax.swing.JTextField();
        bBrowse = new javax.swing.JButton();
        encoding = new javax.swing.JLabel();
        cEncoding = new javax.swing.JComboBox();
        locale = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tLocale = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 9, 9, 9));
        setLayout(new java.awt.GridBagLayout());

        file.setLabelFor(tDictionary);
        org.openide.awt.Mnemonics.setLocalizedText(file, org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "DictionaryInstallerPanel.file.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(file, gridBagConstraints);

        tDictionary.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(tDictionary, gridBagConstraints);
        tDictionary.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "tDictionary_ACSN")); // NOI18N
        tDictionary.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "tDictionary_ACSD")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(bBrowse, org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "bBrowse")); // NOI18N
        bBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBrowseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bBrowse, gridBagConstraints);
        bBrowse.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "bBrowse_ACSN")); // NOI18N
        bBrowse.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "bBrowse_ACSD")); // NOI18N

        encoding.setLabelFor(cEncoding);
        org.openide.awt.Mnemonics.setLocalizedText(encoding, org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "DictionaryInstallerPanel.encoding.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(encoding, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(cEncoding, gridBagConstraints);
        cEncoding.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "cEncoding_ACSN")); // NOI18N
        cEncoding.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "cEncoding_ACSD")); // NOI18N

        locale.setLabelFor(tLocale);
        org.openide.awt.Mnemonics.setLocalizedText(locale, org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "DictionaryInstallerPanel.locale.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(locale, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        tLocale.setColumns(8);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(tLocale, gridBagConstraints);
        tLocale.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "tLocale_ACSN")); // NOI18N
        tLocale.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(DictionaryInstallerPanel.class, "tLocale_ACSD")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bBrowseActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBrowseActionPerformed
        JFileChooser filechooser = new JFileChooser (tDictionary.getText ());
        int ret = filechooser.showOpenDialog (null);
        if (ret == JFileChooser.APPROVE_OPTION)
            tDictionary.setText (filechooser.getSelectedFile ().getAbsolutePath ());
    }//GEN-LAST:event_bBrowseActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bBrowse;
    public javax.swing.JComboBox cEncoding;
    public javax.swing.JLabel encoding;
    public javax.swing.JLabel file;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JLabel locale;
    public javax.swing.JTextField tDictionary;
    public javax.swing.JTextField tLocale;
    // End of variables declaration//GEN-END:variables
    
    public static String getMessage (String key) {
        return NbBundle.getMessage (DictionaryInstallerPanel.class, key);
    }
    private static String homedir = System.getProperty ("netbeans.home"); // NOI18N
    private static String userdir = System.getProperty ("netbeans.user"); // NOI18N
    private static boolean availHomedir = new File (homedir).canWrite ();
    private static boolean availUserdir = new File (userdir).canWrite ();
    
    public final void initValues () {
        tDictionary.setText (System.getProperty ("user.home")); // NOI18N
        Set<String> set = Charset.availableCharsets ().keySet ();
        cEncoding.setModel(new javax.swing.DefaultComboBoxModel(set.toArray (new String[set.size ()])));
        cEncoding.setSelectedItem ("ISO-8859-1"); // NOI18N
        tLocale.setText (""); // NOI18N
//        rAllUsers.setEnabled (availHomedir);
//        rCurrentUser.setEnabled (availUserdir);
//        if (availHomedir)
//            rAllUsers.setSelected (true);
//        else if (availUserdir)
//            rCurrentUser.setSelected (true);
//        else {
//            rAllUsers.setSelected (false);
//            rCurrentUser.setSelected (false);
//        }
    }

    private NotificationLineSupport notifications;

    void setNotifications(NotificationLineSupport notifications) {
        this.notifications = notifications;
        updateErrors();
    }

    private void updateErrors() {
        if (notifications == null) return;
        
        notifications.clearMessages();
        okButton.setEnabled(false);

        File dictFile = new File(tDictionary.getText());

        if (!dictFile.exists()) {
            notifications.setErrorMessage(getMessage("ERR_DictionaryFileDoesNotExist"));
            return;
        }

        if (!dictFile.isFile()) {
            notifications.setErrorMessage(getMessage("ERR_DictionaryFileNotFile"));
            return;
        }

        if (!dictFile.canRead()) {
            notifications.setErrorMessage(getMessage("ERR_DictionaryFileCannotBeRead"));
            return;
        }

        String error = SpellcheckerOptionsPanel.getErrorsForLocale(tLocale.getText());

        if (error != null) {
            notifications.setErrorMessage(getMessage(error));
            return;
        }

        if (existingLocales.contains(tLocale.getText())) {
            notifications.setErrorMessage(getMessage("ERR_DictionaryAlreadyExists"));
            return;
        }

        okButton.setEnabled(true);
    }
    
    private static final int BUFFER_LENGTH = 65536;

    private static File dictionaryFile(String loc, boolean shared) {
        String filename = shared ? homedir : userdir;

        filename += File.separator + "modules" + File.separator + "dict"; // NOI18N
        filename += File.separator + "dictionary";
        if (loc != null  &&  !"".equals (loc))
            filename += "_" + loc;
        return new File (filename + ".txt"); // NOI18N
    }
    
    public static void doInstall (DictionaryDescription description) {
        InputStreamReader input = null;
        OutputStreamWriter output = null;
        try {
            boolean shared;
            if (description.rAllUsers)
                shared = true;
            else if (description.rCurrentUser)
                shared = false;
            else {
                DialogDisplayer.getDefault ().notify (new NotifyDescriptor.Message (getMessage ("MSG_NoInstallDirectoryAvailable"), NotifyDescriptor.ERROR_MESSAGE)); // NOI18N
                return;
            }

            File file = dictionaryFile(description.targetLocale, shared);

            file.getParentFile().mkdirs();

            //TODO: if the dictionary already exists, provide user with a warning.
            input = new InputStreamReader (new FileInputStream (description.dictionaryFile), description.fileEncoding);
            output = new OutputStreamWriter (new FileOutputStream (file), "UTF-8"); // NOI18N
            char[] buffer = new char[BUFFER_LENGTH];
            int len = BUFFER_LENGTH;
            do {
                len = input.read (buffer);
                output.write (buffer, 0, len);
            } while (len == BUFFER_LENGTH);
            StatusDisplayer.getDefault ().setStatusText (getMessage ("MSG_DictionaryWasInstalled")); // NOI18N
        } catch (FileNotFoundException e) {
            DialogDisplayer.getDefault ().notify (new NotifyDescriptor.Message (getMessage ("MSG_InputFileNotFound"), NotifyDescriptor.ERROR_MESSAGE)); // NOI18N
        } catch (UnsupportedEncodingException e) {
            DialogDisplayer.getDefault ().notify (new NotifyDescriptor.Message (getMessage ("MSG_UnsupportedEncoding"), NotifyDescriptor.ERROR_MESSAGE)); // NOI18N
        } catch (IOException e) {
            DialogDisplayer.getDefault ().notify (new NotifyDescriptor.Message (getMessage ("MSG_IOErrorDuringInstallation"), NotifyDescriptor.ERROR_MESSAGE)); // NOI18N
        } finally {
            if (input != null) try { input.close (); } catch (IOException e) {}
            if (output != null) try { output.close (); } catch (IOException e) {}
            
            //TODO: Dictionary.clearDictionaries();
        }
    }

    public DictionaryDescription createDescription() {
        return new DictionaryDescription(false, true, /*rAllUsers.isSelected(), rCurrentUser.isSelected(), */tDictionary.getText(), (String) cEncoding.getSelectedItem(), tLocale.getText());
    }

    public static void removeDictionary(Locale remove) {
        File toRemove = dictionaryFile(remove.toString(), false);

        toRemove.delete();
        toRemove = dictionaryFile(remove.toString(), true);
        toRemove.delete();

        if (InstalledFileLocator.getDefault().locate("modules/dict/dictionary_" + remove.toString() + ".description", null, false) != null) {
            String filename = userdir;

            filename += File.separator + "modules" + File.separator + "dict"; // NOI18N
            filename += File.separator + "dictionary";
            filename += "_" + remove.toString();

            File hiddenDictionaryFile = new File(filename + ".description_hidden");

            hiddenDictionaryFile.getParentFile().mkdirs();

            try {
                new FileOutputStream(hiddenDictionaryFile).close(); // NOI18N
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
    
    public static class DictionaryDescription {
        private boolean rAllUsers;
        private boolean rCurrentUser;
        private String  dictionaryFile;
        private String  fileEncoding;
        private String  targetLocale;

        public DictionaryDescription(boolean rAllUsers, boolean rCurrentUser, String dictionaryFile, String fileEncoding, String targetLocale) {
            this.rAllUsers = rAllUsers;
            this.rCurrentUser = rCurrentUser;
            this.dictionaryFile = dictionaryFile;
            this.fileEncoding = fileEncoding;
            this.targetLocale = targetLocale;
        }

        public Locale getLocale() {
            return Utilities.name2Locale(targetLocale);
        }
    }
    
}
