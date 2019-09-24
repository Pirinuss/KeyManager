package frames.components;

import listener.CategorieTreeListener;
import models.Categorie;
import util.FileUtil;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.JTree;
import javax.swing.tree.*;

public class CategorieTree {

    private JTree tree;
    private DefaultMutableTreeNode root;
    private HashMap<Integer, Categorie> categories;

    public CategorieTree() {

        root = new DefaultMutableTreeNode("Kategorien");
        tree = new JTree(root);
        tree.addTreeSelectionListener(new CategorieTreeListener());
        tree.setPreferredSize(new Dimension(150,1000));

        CategorieTreeCellRenderer renderer = new CategorieTreeCellRenderer();
        //renderer.setBackgroundSelectionColor(Color.BLUE);
        //renderer.setBorderSelectionColor(Color.BLUE.darker());
        //renderer.setBackground(new Color(0xe6e6e6));
        //renderer.setBackgroundNonSelectionColor(new Color(0xe6e6e6));
        tree.setCellRenderer(renderer);

        loadData();
    }

    private void loadData() {

        categories = new HashMap<Integer, Categorie>();

        try {
            Categorie[] categorieCollection = FileUtil.readFromFile();

            for (int i = 0; i<categorieCollection.length; i++) {
                Categorie categorie = (Categorie) categorieCollection[i];
                addCategorie(categorie, categorie.getId());
            }

        } catch (FileNotFoundException e) {
            // TODO
        }
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

    public void removeCategorie(String removeCategorieName) {

        Categorie removeCategorie = getCategorieByName(removeCategorieName);

        for (int i=0; i<root.getChildCount(); i++) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) root.getChildAt(i);
            Categorie currentCategorie = (Categorie) currentNode.getUserObject();
            if (currentCategorie.getName().equals(removeCategorieName)) {
                root.remove(currentNode);
            }
        }
        tree.updateUI();

        categories.remove(removeCategorie.getId());
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
