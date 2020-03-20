package util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import models.Categorie;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

public class FileUtil {

    private static String userDir = System.getProperty("user.dir");
    private static Logger logger = LoggerUtil.getLogger();
    private static Gson gson = new Gson();

    public static void writeToFile(String data) {

        File file = new File(userDir + "/data/data.txt");
        if (!file.exists()) {
            initDirectory(file);
        } else {
            try {
                FileWriter emptyWriter = new FileWriter(file);
                BufferedWriter emptyOut = new BufferedWriter(emptyWriter);
                emptyOut.write(" ");
                emptyOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter dataWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(dataWriter);

            out.write(data);
            out.close();

            logger.info("Daten wurden erfolgreich in " + userDir + "/data/data.txt gespeichert");
        } catch (IOException e) {
            logger.severe("Fehler beim Speichern der Daten: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Categorie[] readFromFile() throws FileNotFoundException {

        File file = new File(userDir + "/data/data.txt");
        if (!file.exists()) {
            logger.severe("Fehler beim Laden der Daten: Datei wurde nicht gefunden");
            throw new FileNotFoundException();
        }

        Categorie[] categories = null;
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");

            JsonReader reader = new JsonReader(in);
            categories = gson.fromJson(reader, Categorie[].class);
        } catch (UnsupportedEncodingException e) {
            logger.severe("Fehler beim Laden der Daten: Fehlerhafter Zeichensatz");
            e.printStackTrace();
        }

        return categories;
    }
    
    public static void readMessages() throws FileNotFoundException {
        File file = new File(userDir + "/src/main/resources/messages.json");
        if (!file.exists()) {
            logger.severe("Fehler beim Laden der Daten: Datei wurde nicht gefunden");
            throw new FileNotFoundException();
        }
        
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");

            JsonReader reader = new JsonReader(in);
            HashMap<String, String> messages = gson.fromJson(reader, HashMap.class);
        } catch (UnsupportedEncodingException e) {
            logger.severe("Fehler beim Laden der Daten: Fehlerhafter Zeichensatz");
            e.printStackTrace();
        }
    }

    private static void initDirectory(File file) {
        File directory = new File(userDir + "/data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.severe("Verzeichnisstruktur konnte nicht angelegt werden!");
            e.printStackTrace();
        }
        logger.info("Verzeichnisstruktur wurde angelegt");
    }

}
