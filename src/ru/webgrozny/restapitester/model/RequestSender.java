package ru.webgrozny.restapitester.model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class RequestSender {
    public static List<String> defaultHeaders = Arrays.asList("Content-type: application/json");
    public static List<String> methods = Arrays.asList("GET", "POST", "PUT", "DELETE");

    public static boolean sendBody(String method) {
        return method != null && (method.equals("POST") || method.equals("PUT"));
    }

    public ServerAnswer send(String host, String json, int methodIndex, String headers) {
        if(host.length() == 0) {
            return new ServerAnswer("Host is not defined!", "", .0);
        }
        String content;
        double result = .0;
        StringBuilder responseHeaders = new StringBuilder();
        List<String> usingHeaders = defaultHeaders;
        try {
            String gotHeaders = headers.replace("\r\n", "\n").replace("\r", "\n");
            while (gotHeaders.contains("\n\n")) {
                gotHeaders = gotHeaders.replace("\n\n", "\n");
            }
            usingHeaders = Arrays.asList(headers.split("\n"));
        } catch (Exception e) {

        }

        try {
            long timeStart = Calendar.getInstance().getTimeInMillis();
            if(!host.startsWith("http://") && !host.startsWith("https://")) {
                host = "http://" + host;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(host).openConnection();
            httpURLConnection.setRequestMethod(methods.get(methodIndex));


            byte[] data = json.getBytes();
            int length = data.length;
            for(String header : usingHeaders) {
                String[] headerVals = header.split(":");
                if(headerVals.length == 2) {
                    String key = headerVals[0].trim();
                    String value = headerVals[1].trim();
                    httpURLConnection.setRequestProperty(key, value);
                }
            }
            if(sendBody(methods.get(methodIndex))) {
                httpURLConnection.setRequestProperty("Content-length", length + "");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data);
            }

            Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
            Set<String> keys = headerFields.keySet();
            for(String key : keys) {
                if(key == null) {
                    responseHeaders.append(headerFields.get(null).get(0));
                } else {
                    responseHeaders.append(key).append(": ").append(headerFields.get(key).get(0));
                }
                responseHeaders.append("\r\n");
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read;
            while ( (read = inputStream.read(buffer)) > 0 ) {
                byteArrayOutputStream.write(buffer, 0, read);
            }
            content = new String(byteArrayOutputStream.toByteArray(), Charset.forName("UTF-8"));
            httpURLConnection.disconnect();
            long timeEnd = Calendar.getInstance().getTimeInMillis();

            long resultMs = timeEnd - timeStart;
            result = resultMs / 1000.;
        } catch (Exception e) {
            content = "Can't connect to \"" + host + "\"\r\n";
        }
        return new ServerAnswer(content, responseHeaders.toString(), result);
    }
}
