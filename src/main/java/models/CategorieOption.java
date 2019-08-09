package models;

public enum CategorieOption {

    NONE, FINANCE, ADMINISTRATION, INTERNET_MOBILE;

    public static String toString(CategorieOption catOption) {

        switch (catOption) {
            case NONE:
                return "--Ohne Bereich--";
            case FINANCE:
                return "Finanzen";
            case ADMINISTRATION:
                return "Behörden";
            case INTERNET_MOBILE:
                return "Internet/Mobile";
            default:
                return null;
        }
    }

    public static CategorieOption fromString(String name) {
        if ("--Ohne Bereich--".equals(name)) {
            return NONE;
        } else if ("Finanzen".equals(name)) {
            return FINANCE;
        } else if ("Behörden".equals(name)) {
            return ADMINISTRATION;
        } else if ("Internet/Mobile".equals(name)) {
            return INTERNET_MOBILE;
        }
        return NONE;
    }

    public static String[] getAllCatNames() {

        String[] names = new String[CategorieOption.values().length];
        int counter = 0;

        for (CategorieOption catOption : CategorieOption.values()) {
            names[counter] = catOption.toString(catOption);
            counter++;
        }

        counter = 0;
        return names;
    }
}
