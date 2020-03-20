package listener;

import frames.MainFrame;
import frames.components.CategoriePanel;
import frames.components.ContentPanel;
import frames.components.PasswordTable.PasswordTable;
import frames.components.PasswordTable.PasswordTable1;
import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;
import util.LoggerUtil;

import javax.swing.*;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class ContentFrameListener {

    private static Logger logger = LoggerUtil.getLogger();

    public static class EditCategorieTableListener implements ActionListener {

        private JButton editButton;

        public EditCategorieTableListener(JButton button) {
            this.editButton = button;
        }

        public void actionPerformed(ActionEvent e) {

            JTable table = ContentPanel.getCategoriePanel().getCategorieInfoTable();

            table.setDefaultRenderer(Object.class, new CategoriePanel.EditCategorieTableRenderer());
            table.setDefaultEditor(Object.class, new CategoriePanel.CategorieTableEditor());
            table.updateUI();

            CategoriePanel.TableModel model = (CategoriePanel.TableModel) table.getModel();
            model.setCellEditable(0,1, true);
            model.setCellEditable(1,1, true);

            editButton.setText("Speichern");
            editButton.removeActionListener(editButton.getActionListeners()[0]);
            editButton.addActionListener(new SaveCategorieTableListener(editButton));

            JPanel buttonPanel = (JPanel) ContentPanel.getCategoriePanel().getComponent(2);
            Component[] components = buttonPanel.getComponents();
            for (Component component : components) {
                if (component.getName() != null) {
                    if (component.getName().equals("newPasswordButton") || component.getName().equals("deleteButton")) {
                        component.setVisible(false);
                    }
                }
            }

        }
    }

    public static class SaveCategorieTableListener implements  ActionListener {

        private JButton saveButton;

        public SaveCategorieTableListener(JButton editButton) {
            this.saveButton = editButton;
        }

        public void actionPerformed(ActionEvent e) {

            if (CategoriePanel.getCategorieInfoTable().getCellEditor() != null) {
                CategoriePanel.getCategorieInfoTable().getCellEditor().stopCellEditing();
            }
            CategoriePanel.getCategorieInfoTable().setDefaultRenderer(Object.class, new CategoriePanel.SaveCategorieTableRenderer());
            CategoriePanel.getCategorieInfoTable().updateUI();

            Categorie categorie = MainFrame.getCatTree().getCategorieById(ContentPanel.getCategoriePanel().getCategorie().getId());
            categorie.setName((String) CategoriePanel.getCategorieTableContent(0,1));
            categorie.setCatOption((CategorieOption.fromString((String) CategoriePanel.getCategorieTableContent(1,1))));

            if (categorie.getName().equals("")) {
                JOptionPane.showMessageDialog(MainFrame.getContentPanel().getCategoriePanel(), "Bitte einen gültigen Namen für die Kategorie eingeben", "Fehler!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            MainFrame.getCatTree().updateUI();
            for (int i=0; i<2; i++) {
                CategoriePanel.TableModel model = (CategoriePanel.TableModel) CategoriePanel.getCategorieInfoTable().getModel();
                model.setCellEditable(i,1, false);
            }

            CategoriePanel.getCategorieInfoTable().updateUI();

            saveButton.setText("Bearbeiten");
            saveButton.removeActionListener(saveButton.getActionListeners()[0]);
            saveButton.addActionListener(new EditCategorieTableListener(saveButton));

            JPanel buttonPanel = (JPanel) ContentPanel.getCategoriePanel().getComponent(2);
            Component[] components = buttonPanel.getComponents();
            for (Component component : components) {
                if (component.getName() != null) {
                    if (component.getName().equals("newPasswordButton") || component.getName().equals("deleteButton")) {
                        component.setVisible(true);
                    }
                }
            }

            logger.info("Kategorie \"" + categorie.getName() + "\" bearbeitet");
            ContentPanel.getCategoriePanel().setDebugInfo("Kategorie erfolgreich editiert!", 5000, Color.GREEN.darker());
        }
    }

    public static class DeletePasswordListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            // Confirmation
            String[] options = {"Ja", "Nein"};
            int option = JOptionPane.showOptionDialog(MainFrame.getFrame(),
                    "Soll der Eintrag wirklich gelöscht werden?",
                    "Achtung!", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (option == 1) {
                return;
            }

            Categorie currentCategorie = MainFrame.getContentPanel().getCategoriePanel().getCategorie();
            JButton activeButton = (JButton) e.getSource();
            int passwordIndex = Integer.valueOf(activeButton.getName());
            PasswordEntity selectedPassword = currentCategorie.getPasswords().get(passwordIndex);

            currentCategorie.removePassword(selectedPassword);
            MainFrame.getContentPanel().updateCategoriePanel(currentCategorie);

            JTree tree = MainFrame.getCatTree();
            DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();

            TreePath path = tree.getNextMatch(currentCategorie.getName(), 0, Position.Bias.Forward);
            DefaultMutableTreeNode deleteNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (deleteNode.getChildCount() != 0) {
                for (int i=0; i<deleteNode.getChildCount(); i++) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) deleteNode.getChildAt(i);
                    PasswordEntity passwordEntity = (PasswordEntity) node.getUserObject();
                    if (passwordEntity.getTitle().equals(selectedPassword.getTitle())) {
                        treeModel.removeNodeFromParent(node);
                    }
                }
            } else {
                treeModel.removeNodeFromParent(deleteNode);
            }

            treeModel.reload();
            tree.expandPath(tree.getNextMatch(currentCategorie.getName(),0, Position.Bias.Forward));
            tree.updateUI();

            logger.info("Passworteintrag \"" + selectedPassword.getTitle() + "\" in Kategorie \"" + currentCategorie.getName() + "\" gelöscht");
            ContentPanel.getCategoriePanel().setDebugInfo("Passworteintrag erfolgreich gelöscht!", 5000, Color.GREEN.darker());
        }
    }

    public static class EditPasswordListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            logger.warning("Funktion funktioniert noch nicht");
            ContentPanel.getCategoriePanel().setDebugInfo("Funktion noch nicht implementiert!", 3000, Color.RED);
        }
    }

    public static class LockPasswords implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            PasswordTable passwordTable = (PasswordTable) MainFrame.getContentPanel().getCategoriePanel().getPasswordTable();

            if (passwordTable.isShowPasswords()) {
                passwordTable.setLockIconName("LockIcon1.png");
                passwordTable.setShowPasswords(false);
            } else {
                passwordTable.setLockIconName("UnlockIcon1.png");
                passwordTable.setShowPasswords(true);
            }

            Categorie categorie = MainFrame.getContentPanel().getCategoriePanel().getCategorie();
            MainFrame.getContentPanel().updateCategoriePanel(categorie);
        }
    }

    public static class SwitchLayoutListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            MainFrame.getContentPanel().getCategoriePanel().switchPasswordTableLayout();
        }
    }
}
