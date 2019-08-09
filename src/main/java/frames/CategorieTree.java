package frames;

import listener.CategorieTreeListener;
import models.Categorie;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class CategorieTree {

    private JTree tree;
    private DefaultMutableTreeNode root;
    private HashMap<Integer, Categorie> categories = new HashMap<Integer, Categorie>();

    CategorieTree() {
        root = new DefaultMutableTreeNode("Kategorien");
        tree = new JTree(root);
        tree.addTreeSelectionListener(new CategorieTreeListener());
        tree.setPreferredSize(new Dimension(150,1000));

        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setTextSelectionColor(Color.BLACK);
        renderer.setBackgroundSelectionColor(Color.LIGHT_GRAY);
        renderer.setBorderSelectionColor(Color.LIGHT_GRAY);
    }

    public JTree getTree() {
        return tree;
    }

    public void addCategorie(Categorie categorie, long id) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        DefaultMutableTreeNode newCat = new DefaultMutableTreeNode(categorie);

        model.insertNodeInto(newCat, root, root.getChildCount());
        tree.scrollPathToVisible(new TreePath(newCat.getPath()));
        categories.put((int) id, categorie);
        model.reload();
    }

    public void removeCategorie (String name) {

        for (Categorie categorie : categories.values()) {
            if (categorie.getName().equals(name)) {
                categories.remove(categorie.getId());
            }
        }
    }

    public void removeCategorie(int childIndex) {
        root.remove(childIndex);
        categories.remove(childIndex);
        tree.updateUI();
    }

    public Categorie getCategorieById(int id) {
        return categories.get(id);
    }

    public Categorie getCategorieByName(String name) {
        for (Categorie categorie : categories.values()) {
            if (categorie.getName().equals(name)) {
                return categorie;
            }
        }
        return null;
    }

    public ArrayList<String> getCatNames() {

        ArrayList<String> catNames = new ArrayList<String>();

        if (!categories.isEmpty()) {
            for (Categorie categorie : categories.values()) {
                catNames.add(categorie.getName());
            }
        }
        return catNames;
    }

    public HashMap<Integer, Categorie> getCategories() {
        return categories;
    }
}
