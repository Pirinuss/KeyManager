package frames.components;

import java.awt.Color;
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

public class CategorieTree extends JTree {

	private static final long serialVersionUID = -2643179724856700031L;

	private HashMap<Integer, Categorie> categories;

	public CategorieTree() {

		super(new DefaultMutableTreeNode("Kategorien"));
		this.addTreeSelectionListener(new CategorieTreeListener());
		this.setPreferredSize(new Dimension(200, 1000));
		this.setCellRenderer(new CategorieTreeCellRenderer());
		this.setBackground(new Color(0xe6e6e6));

		categories = loadData();
	}

	private  HashMap<Integer, Categorie> loadData() {

		categories = new HashMap<Integer, Categorie>();

		try {
			Categorie[] categorieCollection = FileUtil.readFromFile();

			for (int i = 0; i < categorieCollection.length; i++) {
				Categorie categorie = (Categorie) categorieCollection[i];
				addCategorie(categorie, categorie.getId());
			}

			Set<Map.Entry<Integer, Categorie>> categorieList = categories.entrySet();
			for (Map.Entry<Integer, Categorie> entry : categorieList) {
				Categorie categorie = (Categorie) entry.getValue();
				for (PasswordEntity passwordEntity : categorie.getPasswords()) {
					insertPassword(categorie, passwordEntity);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO
		}
		
		return categories;
	}

	public void insertPassword(Categorie categorie, PasswordEntity passwordEntity) {
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		int index = 0;
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) root.getChildAt(i);
			Categorie nodeCategorie = (Categorie) childNode.getUserObject();
			if (nodeCategorie.getName().equals(categorie.getName())) {
				index = i;
			}
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(passwordEntity);
		model.insertNodeInto(newNode, (DefaultMutableTreeNode) root.getChildAt(index), root.getChildAt(index).getChildCount());

		this.updateUI();
	}

	public void addCategorie(Categorie categorie, long id) {
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.getModel().getRoot();
		DefaultMutableTreeNode newCat = new DefaultMutableTreeNode(categorie);

		model.insertNodeInto(newCat, root, root.getChildCount());
		this.scrollPathToVisible(new TreePath(newCat.getPath()));
		categories.put((int) id, categorie);

		model.reload();
	}

	public void removeCategorie(String removeCategorieName) {

		Categorie removeCategorie = getCategorieByName(removeCategorieName);
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.getModel().getRoot();

		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) root.getChildAt(i);
			Categorie currentCategorie = (Categorie) currentNode.getUserObject();
			if (currentCategorie.getName().equals(removeCategorieName)) {
				root.remove(currentNode);
			}
		}
		this.updateUI();

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
