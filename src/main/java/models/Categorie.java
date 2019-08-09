package models;

import java.util.ArrayList;

public class Categorie {

    private int id;
    private String name;
    private CategorieOption catOption;
    private ArrayList<PasswordEntity> passwords = new ArrayList<PasswordEntity>();

    private static final int MAXPASSWORDSAMOUNT = 20;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategorieOption getCatOption() {
        return catOption;
    }

    public void setCatOption(CategorieOption catOption) {
        this.catOption = catOption;
    }

    public ArrayList<PasswordEntity> getPasswords() {
        return passwords;
    }

    public void addPassword(PasswordEntity password) {
        passwords.add(password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getMaxpasswordsAmount() {
        return MAXPASSWORDSAMOUNT;
    }

    @Override
    public String toString() {
        return name;
    }
}
