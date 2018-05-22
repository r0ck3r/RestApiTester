package ru.webgrozny.restapitester.model;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UserDataSaver {
    private static final String FILE_NAME = ".RestApiTester";
    private static UserDataSaver userDataSaver;

    private String userDir = System.getProperty("user.dir");
    private String filePath = userDir + "/" + FILE_NAME;
    private String hostFromFile = "";
    private String jsonFromFile = "";
    private int methodFromFile = 0;
    private List<String> headersFromFile;

    private UserDataSaver() {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            ByteArrayOutputStream content = new ByteArrayOutputStream();
            int c = 0;
            while ( (c = fileInputStream.read()) != -1 ) {
                content.write(c);
            }
            String stringContent = new String(content.toByteArray());
            int newLineIndex = stringContent.indexOf("\r\n");
            if(newLineIndex != -1) {
                String[] savedData = stringContent.substring(0, newLineIndex).split("@");
                try {
                    methodFromFile = Integer.valueOf(savedData[0]);
                    hostFromFile = savedData[1];
                    jsonFromFile = stringContent.substring(newLineIndex + 2);
                    headersFromFile = Arrays.asList(savedData[2].replace("--atSymbol--", "@").split("--newLine--"));
                } catch (Exception e) {

                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    public void saveData(String host, String json, int methodIndex, List<String> headers) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            StringBuilder flatHeaders = new StringBuilder();
            for(String header : headers) {
                flatHeaders.append(header.replace("@", "--atSymbol--") + "--newLine--");
            }
            fileOutputStream.write( (methodIndex + "@" + host + "@" + flatHeaders.toString() + "\r\n").getBytes());
            fileOutputStream.write(json.getBytes());
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    public String getHost() {
        return hostFromFile;
    }

    public String getJson() {
        return jsonFromFile;
    }

    public int getMethod() {
        return methodFromFile;
    }

    public List<String> getHeaders() {
        return headersFromFile == null ? PostJsonSender.defaultHeaders : headersFromFile;
    }

    public static UserDataSaver getInstance() {
        return userDataSaver == null ? userDataSaver = new UserDataSaver() : userDataSaver;
    }
}
