package listener;

import frames.ContentFrame;
import frames.MainFrame;
import models.Categorie;
import models.CategorieOption;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContentFrameListener {

    public static class editCategorieTableListener implements ActionListener {

        private JButton editButton;

        public editCategorieTableListener(JButton button) {
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
            editButton.addActionListener(new saveCategorieTableListener(editButton));

        }
    }

    public static class saveCategorieTableListener implements  ActionListener {

        private JButton saveButton;

        public saveCategorieTableListener(JButton editButton) {
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
            saveButton.addActionListener(new editCategorieTableListener(saveButton));
        }
    }

}
