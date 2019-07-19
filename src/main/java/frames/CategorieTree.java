package frames;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class CategorieTree {

    private JTree tree;
    private DefaultMutableTreeNode root;
    private ArrayList<String> catnames = new ArrayList<String>();

    CategorieTree() {
        root = new DefaultMutableTreeNode("Kategorien");
        tree = new JTree(root);
        tree.setPreferredSize(new Dimension(150,1000));
    }

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) {
        this.tree = tree;
    }

    public void addCategorie(String name) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel()
                .getRoot();
        DefaultMutableTreeNode newCat = new DefaultMutableTreeNode(name);

        catnames.add(name);
        model.insertNodeInto(newCat, root, root.getChildCount());
        tree.scrollPathToVisible(new TreePath(newCat.getPath()));
        model.reload(root);
    }

    public void removeCategorie(int childIndex) {
        root.remove(childIndex);
        catnames.remove(childIndex);
        tree.updateUI();
    }

    public ArrayList<String> getCatnames() {
        return catnames;
    }

}
