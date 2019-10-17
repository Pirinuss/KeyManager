package frames.components;

import models.Categorie;
import models.CategorieOption;
import models.PasswordEntity;
import util.IconHandler;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class CategorieTreeCellRenderer implements TreeCellRenderer {

    private JLabel renderedLabel = new JLabel();

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        Object object = ((DefaultMutableTreeNode) value).getUserObject();

        if (object instanceof Categorie) {
            Categorie categorie = (Categorie) object;

            renderedLabel.setText(categorie.getName());
            renderedLabel.setIcon(IconHandler.getIconForCategorieOption(categorie.getCatOption()));
            renderedLabel.setForeground(Color.BLACK);

            if (selected) {
                renderedLabel.setForeground(new Color(0xFBACBE));
            }

        } else if (object instanceof PasswordEntity) {
            PasswordEntity password = (PasswordEntity) object;
            renderedLabel = new JLabel(password.getTitle());
            renderedLabel.setIcon(IconHandler.getIcon("Key.png",16,16));
        } else {
            renderedLabel = new JLabel("Kategorie");
        }

        return renderedLabel;
    }
}
