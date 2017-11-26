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
package org.netbeans.modules.jshell.launch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import javax.swing.ComboBoxModel;
import javax.swing.event.DocumentListener;
import org.netbeans.api.java.platform.*;
import org.netbeans.modules.java.api.common.ui.PlatformFilter;
import org.netbeans.modules.java.api.common.ui.PlatformUiSupport;
import org.openide.modules.SpecificationVersion;

/**
 *
 * @author sdedic
 */
public class JShellOptionsPanel extends javax.swing.JPanel 
        implements ActionListener, ItemListener {
    private ShellOptions options;
    private JShellOptionsController ctrl;
    
    /**
     * Creates new form JShellOptionsPanel
     */
    public JShellOptionsPanel(JShellOptionsController ctrl) {
        this.ctrl = ctrl;
        options = ShellOptions.get();

        initComponents();
        
        ComboBoxModel platforms = PlatformUiSupport.createPlatformComboBoxModel(null,
                Collections.singletonList(new PlatformFilter() {
                    @Override
                    public boolean accept(JavaPlatform platform) {
                        return options.accepts(platform);
                    }
            }));
        cbPlatform.setModel(platforms);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbPlatform = new javax.swing.JComboBox<>();
        btnManage = new javax.swing.JButton();
        chAutoOpen = new javax.swing.JCheckBox();
        chReuseConsoles = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        historyLines = new javax.swing.JSpinner();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(JShellOptionsPanel.class, "JShellOptionsPanel.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnManage, org.openide.util.NbBundle.getMessage(JShellOptionsPanel.class, "JShellOptionsPanel.btnManage.text")); // NOI18N
        btnManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(chAutoOpen, org.openide.util.NbBundle.getMessage(JShellOptionsPanel.class, "JShellOptionsPanel.chAutoOpen.text")); // NOI18N

        chReuseConsoles.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(chReuseConsoles, org.openide.util.NbBundle.getMessage(JShellOptionsPanel.class, "JShellOptionsPanel.chReuseConsoles.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(JShellOptionsPanel.class, "JShellOptionsPanel.jLabel2.text")); // NOI18N

        historyLines.setModel(new javax.swing.SpinnerNumberModel(50, 0, null, 1));
        historyLines.setName(""); // NOI18N
        historyLines.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                historyLinesStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPlatform, 0, 225, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnManage))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chReuseConsoles)
                            .addComponent(chAutoOpen)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(historyLines)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbPlatform, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnManage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chAutoOpen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chReuseConsoles)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(historyLines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(167, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageActionPerformed
        JavaPlatform old = getSelectedPlatformObject();
        PlatformsCustomizer.showCustomizer(null);
        if (old == null && cbPlatform.getSelectedItem() == null &&
                cbPlatform.getItemCount() > 0) {
            disableUpdates = true;
            setSelectedItem(options.getSelectedPlatform());
            if (cbPlatform.getSelectedItem() == null) {
                cbPlatform.setSelectedIndex(0);
            }
            disableUpdates = false;
        }
    }//GEN-LAST:event_btnManageActionPerformed

    private void historyLinesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_historyLinesStateChanged
        changed();
    }//GEN-LAST:event_historyLinesStateChanged
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnManage;
    private javax.swing.JComboBox<String> cbPlatform;
    private javax.swing.JCheckBox chAutoOpen;
    private javax.swing.JCheckBox chReuseConsoles;
    private javax.swing.JSpinner historyLines;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables


    public boolean isChanged() {
        if (chReuseConsoles.isSelected() != options.isReuseDeadConsoles()) {
            return true;
        }
        if (chAutoOpen.isSelected() != options.isOpenConsole()) {
            return true;
        }
        if (getSelectedPlatformObject() != options.getSelectedPlatform()) {
            return true;
        }
        Object val = historyLines.getModel().getValue();
        if (!(val instanceof Integer)) {
            return true;
        }
        if ((Integer)val != options.getHistoryLines()) {
            return true;
        }
        return false;
    }
    
    private boolean disableUpdates;
    
    private void setSelectedItem(JavaPlatform p) {
        if (p == null) {
            cbPlatform.setSelectedItem(null);
            return;
        }
        for (int i = 0; i < cbPlatform.getItemCount(); i++) {
            Object o = cbPlatform.getItemAt(i);
            JavaPlatform item = PlatformUiSupport.getPlatform(o);
            if (p.equals(item)) {
                cbPlatform.setSelectedIndex(i);
                return;
            }
        }
        cbPlatform.setSelectedItem(null);
    }
    
    public void load() {
        disableUpdates = true;
        chReuseConsoles.setSelected(options.isReuseDeadConsoles());
        chAutoOpen.setSelected(options.isOpenConsole());
        setSelectedItem(options.getSelectedPlatform());
        historyLines.getModel().setValue(options.getHistoryLines());
        disableUpdates = false;
    }
    
    public void store() {
        if (chReuseConsoles.isSelected() != options.isReuseDeadConsoles()) {
            options.setReuseDeadConsoles(chReuseConsoles.isSelected());
        }
        if (chAutoOpen.isSelected() != options.isOpenConsole()) {
            options.setOpenConsole(chAutoOpen.isSelected());
        }
        if (getSelectedPlatformObject() != options.getSelectedPlatform()) {
            options.setSelectedPlatform(getSelectedPlatform());
        }
        Object val = historyLines.getModel().getValue();
        if (val instanceof Integer && ((Integer)val != options.getHistoryLines())) {
            options.setHistoryLines((Integer)val);
        }
    }
    
    private JavaPlatform getSelectedPlatformObject() {
        Object key = cbPlatform.getSelectedItem();
        return key == null ? null : PlatformUiSupport.getPlatform(key);
    }
    
    private String getSelectedPlatform() {
        Object o = cbPlatform.getSelectedItem();
        return o == null ? null : o.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        changed();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        changed();
    }
    
    private void changed() {
        if (!disableUpdates) {
            ctrl.changed();
        }
    }
    
    public boolean valid() {
        if (cbPlatform.getSelectedItem() == null) {
            return options.getDefaultPlatform() == null;
        }
        return true;
    }
}
