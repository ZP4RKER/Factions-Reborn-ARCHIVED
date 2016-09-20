package com.zp4rker.core;

import com.zp4rker.freborn.FactionsReborn;

import java.io.*;

public class ConfigManager {
    private FactionsReborn plugin;

    public ConfigManager(FactionsReborn plugin) {
        this.plugin = plugin;
    }

    public com.zp4rker.core.Config getNewConfig(String fileName, String[] header) {
        File file = this.getConfigFile(fileName);

        if (!file.exists()) {
            this.prepareFile(fileName);
            if (header != null && header.length != 0)
                this.setHeader(file, header);
        }

        com.zp4rker.core.Config config = new com.zp4rker.core.Config(file, this.getCommentsNum(file), plugin);
        return config;
    }

    public com.zp4rker.core.Config getNewConfig(String fileName) {
        return this.getNewConfig(fileName, null);
    }

    private File getConfigFile(String file) {
        if (file.isEmpty() || file == null)
            return null;

        File configFile;

        if (file.contains("/")) {
            if (file.startsWith("/"))
                configFile = new File(plugin.getDataFolder() + file.replace("/", File.separator));
            else configFile = new File(plugin.getDataFolder() + File.separator + file.replace("/", File.separator));
        } else configFile = new File(plugin.getDataFolder(), file);

        return configFile;
    }

    public void prepareFile(String filePath, String resource) {
        File file = this.getConfigFile(filePath);

        if (file.exists())
            return;

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            if (resource != null)
                if (!resource.isEmpty())
                    this.copyResource(plugin.getResource(resource), file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareFile(String filePath) {
        this.prepareFile(filePath, null);
    }

    public void setHeader(File file, String[] header) {
        if (!file.exists())
            return;

        try {
            String currentLine;
            StringBuilder config = new StringBuilder("");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((currentLine = reader.readLine()) != null)
                config.append(currentLine + "\n");

            reader.close();
            config.append("# +----------------------------------------------------+ #\n");

            for (String line : header) {
                if (line.length() > 50)
                    continue;

                int lenght = (50 - line.length()) / 2;
                StringBuilder finalLine = new StringBuilder(line);

                for (int i = 0; i < lenght; i++) {
                    finalLine.append(" ");
                    finalLine.reverse();
                    finalLine.append(" ");
                    finalLine.reverse();
                }

                if (line.length() % 2 != 0)
                    finalLine.append(" ");

                config.append("# < " + finalLine.toString() + " > #\n");
            }
            config.append("# +----------------------------------------------------+ #");

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(this.prepareConfigString(config.toString()));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getCommentsNum(File file) {
        if (!file.exists())
            return 0;
        try {
            int comments = 0;
            String currentLine;

            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((currentLine = reader.readLine()) != null)
                if (currentLine.startsWith("#"))
                    comments++;

            reader.close();
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String prepareConfigString(String configString) {
        int lastLine = 0;
        int headerLine = 0;

        String[] lines = configString.split("\n");
        StringBuilder config = new StringBuilder("");

        for (String line : lines) {
            if (line.startsWith(this.getPluginName() + "_COMMENT")) {
                String comment = "#" + line.trim().substring(line.indexOf(":") + 1);

                if (comment.startsWith("# +-")) {
                    if (headerLine == 0) {
                        config.append(comment + "\n");
                        lastLine = 0;
                        headerLine = 1;
                    } else if (headerLine == 1) {
                        config.append(comment + "\n\n");
                        lastLine = 0;
                        headerLine = 0;
                    }
                } else {
                    String normalComment;
                    if (comment.startsWith("# ' "))
                        normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");
                    else normalComment = comment;

                    if (lastLine == 0)
                        config.append(normalComment + "\n");
                    else if (lastLine == 1)
                        config.append("\n" + normalComment + "\n");

                    lastLine = 0;
                }
            } else {
                config.append(line + "\n");
                lastLine = 1;
            }
        }
        return config.toString();
    }

    public void saveConfig(String configString, File file) {
        String configuration = this.prepareConfigString(configString);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(configuration);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPluginName() {
        return plugin.getDescription().getName();
    }

    private void copyResource(InputStream resource, File file) {
        try {
            OutputStream out = new FileOutputStream(file);

            int length;
            byte[] buf = new byte[1024];

            while ((length = resource.read(buf)) > 0)
                out.write(buf, 0, length);

            out.close();
            resource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
