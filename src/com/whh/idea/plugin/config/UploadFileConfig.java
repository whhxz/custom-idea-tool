package com.whh.idea.plugin.config;

import java.io.IOException;
import java.util.Properties;

/**
 * UploadFileConfig
 * Created by xuzhuo on 2018/4/25.
 */
public class UploadFileConfig {
    private static Properties properties = new Properties();

    public static void initProperties() {
        try {
            properties.load(UploadFileConfig.class.getClassLoader().getResourceAsStream("uploadFiledefault.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String value(String key) {
        return properties.getProperty(key);
    }

}
