package listener;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import frames.MainFrame;
import models.Categorie;
import models.PasswordEntity;

public class CategorieTreeListener implements TreeSelectionListener {

    public void valueChanged(TreeSelectionEvent e) {

        TreePath treePath = e.getNewLeadSelectionPath();
        if (treePath == null) {
            return;
        }
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
        if (selectedNode.isRoot()) {
            return;
        }

        // Handle click on categorie
        if (selectedNode.getUserObject().getClass() == Categorie.class) {
            clickOnCategorie((Categorie) selectedNode.getUserObject());
        }

        // Handle click on password
        if (selectedNode.getUserObject().getClass() == PasswordEntity.class) {
            clickOnPassword((PasswordEntity) selectedNode.getUserObject());
        }

    }

    private void clickOnCategorie(Categorie categorie) {
        MainFrame.getContentPanel().updateCategoriePanel(categorie);
    }

    private void clickOnPassword(PasswordEntity password) {
        MainFrame.getContentPanel().updatePasswordPanel(password);
    }

}
