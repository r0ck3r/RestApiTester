package ru.webgrozny.restapitester.model;

import java.io.*;

public class Storage {
    private final static String FILE_NAME = ".RestApiStorage";

    private static Storage instance = null;

    private String userDir;
    private String storagePath;
    private SavedContent savedContent;

    private Storage() {
        userDir = System.getProperty("user.home");
        storagePath = userDir + "/" + FILE_NAME;
        load();
    }

    private void load() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(storagePath))) {
            savedContent = (SavedContent) objectInputStream.readObject();
        } catch (Exception e) {
            savedContent = new SavedContent();
        }
    }

    public SavedContent getSavedContent() {
        return savedContent;
    }

    public void save() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(storagePath))) {
            objectOutputStream.writeObject(savedContent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Storage getInstance() {
        if(instance == null) {
            instance = new Storage();
        }
        return instance;
    }
}
