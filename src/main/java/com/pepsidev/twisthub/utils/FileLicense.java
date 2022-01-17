package com.pepsidev.twisthub.utils;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.setup.PermissionCore;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class FileLicense {

    private String licenseKey;
    private Plugin plugin;
    private String validationServer;
    private FileLicense.LogType logType = FileLicense.LogType.NORMAL;
    private String securityKey = "YecoF0I6M05thxLeokoHuW8iUhTdIUInjkfF";
    private boolean debug = false;


    public FileLicense(String licenseKey, String validationServer, Plugin plugin) {
        this.licenseKey = licenseKey;
        this.plugin = plugin;
        this.validationServer = validationServer;
    }

    public FileLicense setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
        return this;
    }

    public FileLicense setConsoleLog(FileLicense.LogType logType) {
        this.logType = logType;
        return this;
    }

    public FileLicense debug() {
        debug = true;
        return this;
    }

    public boolean register() {
        CC.log("");
        CC.log("");
        CC.log(" &7➥ &eAuthor&7: &flRusian");
        CC.log(" &7➥ &eVersion&7: " + Hub.getInstance().getDescription().getVersion());
        CC.log(" &7➥ &eWebsite&7: " + Hub.getInstance().getDescription().getWebsite());
        CC.log(" &7➥ &eRank System&7: &f" + ConfigFile.getConfig().getString("system.rank"));
        FileLicense.ValidationType vt = isValid();
        if (vt == FileLicense.ValidationType.VALID) {
            CC.log(" &7➥ &eLicense&7: &f" + Hub.getInstance().getConfig().getString("server-information.license"));
            CC.log("");
            CC.log("");
            return true;
        } else {
            CC.log(" &7➥ &eLicense&7: &cInvalid");
            CC.log(" &7➥ &c&lFailed as a result of " + vt.toString());
            CC.log("");
            CC.log("");
            Bukkit.getScheduler().cancelTasks(plugin);
            Bukkit.getPluginManager().disablePlugin(plugin);
            return false;
        }

    }

    public boolean isValidSimple() {
        return (isValid() == FileLicense.ValidationType.VALID);
    }

    private String requestServer(String v1, String v2) throws IOException {
        URL url = new URL(validationServer + "?v1=" + v1 + "&v2=" + v2 + "&pl=" + plugin.getName());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        if (debug) {
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();
        }
    }

    public FileLicense.ValidationType isValid() {
        String rand = toBinary(UUID.randomUUID().toString());
        String sKey = toBinary(securityKey);
        String key = toBinary(licenseKey);

        try {
            String response = requestServer(xor(rand, sKey), xor(rand, key));

            if (response.startsWith("<")) {
                log(1, "The License-Server returned an invalid response!");
                log(1, "In most cases this is caused by:");
                log(1, "1) Your Web-Host injects JS into the page (often caused by free hosts)");
                log(1, "2) Your ValidationServer-URL is wrong");
                log(1,
                        "SERVER-RESPONSE: " + (response.length() < 150 || debug ? response : response.substring(0, 150) + "..."));
                return FileLicense.ValidationType.PAGE_ERROR;
            }

            try {
                return FileLicense.ValidationType.valueOf(response);
            } catch (IllegalArgumentException exc) {
                String respRand = xor(xor(response, key), sKey);
                if (rand.substring(0, respRand.length()).equals(respRand))
                    return FileLicense.ValidationType.VALID;
                else
                    return FileLicense.ValidationType.WRONG_RESPONSE;
            }
        } catch (IOException e) {
            if (debug)
                e.printStackTrace();
            return FileLicense.ValidationType.PAGE_ERROR;
        }
    }

    //
    // Cryptographic
    //

    private static String xor(String s1, String s2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < (Math.min(s1.length(), s2.length())); i++)
            result.append(Byte.parseByte("" + s1.charAt(i)) ^ Byte.parseByte(s2.charAt(i) + ""));
        return result.toString();
    }

    //
    // Enums
    //

    public enum LogType {
        NORMAL, LOW, NONE;
    }

    public enum ValidationType {
        WRONG_RESPONSE, PAGE_ERROR, URL_ERROR, KEY_OUTDATED, KEY_NOT_FOUND, NOT_VALID_IP, INVALID_PLUGIN, VALID;
    }

    //
    // Binary methods
    //

    private String toBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    //
    // Console-Log
    //

    private void log(int type, String message) {
        if (logType == FileLicense.LogType.NONE || (logType == FileLicense.LogType.LOW && type == 0))
            return;
        System.out.println(message);
    }
}
