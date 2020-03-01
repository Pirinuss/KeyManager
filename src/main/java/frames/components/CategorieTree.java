package frames.components;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import listener.CategorieTreeListener;
import models.Categorie;
import models.PasswordEntity;
import util.FileUtil;

public class CategorieTree {

	private static JTree tree;
	private DefaultMutableTreeNode root;
	private HashMap<Integer, Categorie> categories;

	public CategorieTree() {

		root = new DefaultMutableTreeNode("Kategorien");
		tree = new JTree(root);
		tree.addTreeSelectionListener(new CategorieTreeListener());
		tree.setPreferredSize(new Dimension(200, 1000));
		tree.setCellRenderer(new CategorieTreeCellRenderer());

		loadData();
	}

	private void loadData() {

		categories = new HashMap<Integer, Categorie>();

		try {
			Categorie[] categorieCollection = FileUtil.readFromFile();

			for (int i = 0; i < categorieCollection.length; i++) {
				Categorie categorie = (Categorie) categorieCollection[i];
				addCategorie(categorie, categorie.getId());
			}

			Set<Map.Entry<Integer, Categorie>> categorieList = categories
					.entrySet();
			for (Map.Entry entry : categorieList) {
				Categorie categorie = (Categorie) entry.getValue();
				for (PasswordEntity passwordEntity : categorie.getPasswords()) {
					insertPassword(categorie, passwordEntity);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO
		}
	}

	public static void insertPassword(Categorie categorie,
			PasswordEntity passwordEntity) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		int index = 0;
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) root
					.getChildAt(i);
			Categorie nodeCategorie = (Categorie) childNode.getUserObject();
			if (nodeCategorie.getName().equals(categorie.getName())) {
				index = i;
			}
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
				passwordEntity);
		model.insertNodeInto(newNode,
				(DefaultMutableTreeNode) root.getChildAt(index),
				root.getChildAt(index).getChildCount());

		tree.updateUI();
	}

	public JTree getTree() {
		return tree;
	}

	public void addCategorie(Categorie categorie, long id) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel()
				.getRoot();
		DefaultMutableTreeNode newCat = new DefaultMutableTreeNode(categorie);

		model.insertNodeInto(newCat, root, root.getChildCount());
		tree.scrollPathToVisible(new TreePath(newCat.getPath()));
		categories.put((int) id, categorie);

		model.reload();
	}

	public void removeCategorie(String removeCategorieName) {

		Categorie removeCategorie = getCategorieByName(removeCategorieName);

		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) root
					.getChildAt(i);
			Categorie currentCategorie = (Categorie) currentNode
					.getUserObject();
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
