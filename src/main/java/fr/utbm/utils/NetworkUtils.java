package fr.utbm.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkUtils {
    private final static String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private final static String MACADDRESS_PATTERN = "([0-9A-Fa-f]{2}[\\.:-]){5}([0-9A-Fa-f]{2})";
    private static final Pattern ipPattern = Pattern.compile(IPADDRESS_PATTERN);
    private static final Pattern macPattern = Pattern.compile(MACADDRESS_PATTERN);

    private static HashMap<String, String> arpEntries = new HashMap<String, String>();
    
    public static String sendRequest(String stringUrl, Integer readTimeout, Integer connectTimeout) {
        InputStream is = null;
        String response = null;
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(readTimeout /* milliseconds */);
            urlConnection.setConnectTimeout(connectTimeout /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            urlConnection.connect();
            System.out.println("Request SENT : " + stringUrl);
            is = urlConnection.getInputStream();
            response = readIt(is, is.available());
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }

        if (response == null) {
            return "";
        }
        return response;
    }
    
    private static void readArpTable() {
        BufferedReader inStreamReader = null;
        arpEntries.clear();

        if (OSChecker.isWindows()) {
            try {
                ProcessBuilder pb = new ProcessBuilder("arp", "-a");
                pb.redirectErrorStream(true);
                Process process = pb.start();
                inStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            } catch (IOException ex) {

            }
        } else if (OSChecker.isUnix()) {
            try {
                inStreamReader = new BufferedReader(new FileReader("/proc/net/arp"));
            } catch (FileNotFoundException ex) {
                System.out.println("ARP File not found");
            }
        }

        if (inStreamReader != null) {
            try {
                String line;
                while ((line = inStreamReader.readLine()) != null) {
                    System.out.println(line);

                    Matcher ipMatcher = ipPattern.matcher(line);
                    Matcher macMatcher = macPattern.matcher(line);
                    if (ipMatcher.find() && macMatcher.find() && !macMatcher.group().endsWith("ff")) {
                        arpEntries.put(ipMatcher.group(), macMatcher.group());
                        System.out.println("ARP Entry found : " + ipMatcher.group() + " - " + macMatcher.group());
                    }
                }
            } catch (IOException ex) {

            }
        }
    }

    public static HashMap<String, String> getArpEntries() {
        if (arpEntries.isEmpty()) {
            readArpTable();
        }
        return arpEntries;
    }

    // Reads an InputStream and converts it to a String.
    private static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
