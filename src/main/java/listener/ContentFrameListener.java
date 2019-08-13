package listener;

import frames.ContentFrame;
import frames.MainFrame;
import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;

import javax.swing.*;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContentFrameListener {

    public static class EditCategorieTableListener implements ActionListener {

        private JButton editButton;

        public EditCategorieTableListener(JButton button) {
            this.editButton = button;
        }

        public void actionPerformed(ActionEvent e) {

            JTable table = ContentFrame.getCategorieInfoTable();

            table.setDefaultRenderer(Object.class, new ContentFrame.EditCategorieTableRenderer());
            table.setDefaultEditor(Object.class, new ContentFrame.CategorieTableEditor());
            table.updateUI();

            ContentFrame.TableModel model = (ContentFrame.TableModel) table.getModel();
            model.setCellEditable(0,1, true);
            model.setCellEditable(1,1, true);

            editButton.setText("Speichern");
            editButton.removeActionListener(editButton.getActionListeners()[0]);
            editButton.addActionListener(new SaveCategorieTableListener(editButton));

            JPanel buttonPanel = (JPanel) ContentFrame.getCategoriePanel().getComponent(2);
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

            if (ContentFrame.getCategorieInfoTable().getCellEditor() != null) {
                ContentFrame.getCategorieInfoTable().getCellEditor().stopCellEditing();
            }
            ContentFrame.getCategorieInfoTable().setDefaultRenderer(Object.class, new ContentFrame.SaveCategorieTableRenderer());
            ContentFrame.getCategorieInfoTable().updateUI();

            Categorie categorie = MainFrame.getCatTree().getCategorieById(ContentFrame.getCategorie().getId());
            categorie.setName((String) ContentFrame.getCategorieTableContent(0,1));
            categorie.setCatOption((CategorieOption.fromString((String) ContentFrame.getCategorieTableContent(1,1))));

            if (categorie.getName().equals("")) {
                JOptionPane.showMessageDialog(MainFrame.getContentPanel().getCategoriePanel(), "Bitte einen gültigen Namen für die Kategorie eingeben", "Fehler!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            MainFrame.getCatTree().getTree().updateUI();
            for (int i=0; i<2; i++) {
                ContentFrame.TableModel model = (ContentFrame.TableModel) ContentFrame.getCategorieInfoTable().getModel();
                model.setCellEditable(i,1, false);
            }

            ContentFrame.getCategorieInfoTable().updateUI();

            saveButton.setText("Bearbeiten");
            saveButton.removeActionListener(saveButton.getActionListeners()[0]);
            saveButton.addActionListener(new EditCategorieTableListener(saveButton));

            JPanel buttonPanel = (JPanel) ContentFrame.getCategoriePanel().getComponent(2);
            Component[] components = buttonPanel.getComponents();
            for (Component component : components) {
                if (component.getName() != null) {
                    if (component.getName().equals("newPasswordButton") || component.getName().equals("deleteButton")) {
                        component.setVisible(true);
                    }
                }
            }
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

            Categorie currentCategorie = MainFrame.getContentPanel().getCategorie();
            JButton activeButton = (JButton) e.getSource();
            int passwordIndex = Integer.valueOf(activeButton.getName());
            PasswordEntity selectedPassword = currentCategorie.getPasswords().get(passwordIndex);

            currentCategorie.removePassword(selectedPassword);
            MainFrame.getContentPanel().updateCategoriePanel(currentCategorie);

            JTree tree = MainFrame.getCatTree().getTree();
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

        }
    }

}
