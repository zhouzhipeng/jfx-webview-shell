package com.zhouzhipeng;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Collection;

public class ConfigLoader {

    private static Object javaBridgeObj;

    private static Config config;

    private static String homePageContent;

    public static Config getConfig() {
        return config;
    }

    public static String getHomePageContent() {
        return homePageContent;
    }


    public static void loadConfig() {
        try {

            //加载配置文件
            String configContent = IOUtils.toString(ConfigLoader.class.getClassLoader().getResourceAsStream("config.json"), "UTF-8");
            config = JSONObject.parseObject(configContent, Config.class);

            homePageContent = IOUtils.toString(ConfigLoader.class.getClassLoader().getResourceAsStream("index.html"), "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Object getJavaObj(Document document) {

        try {
            if (javaBridgeObj == null) {

                //加载桥接类并初始化
                Class<?> aClass = Class.forName(config.getBridgeClassName());
                Constructor<?> constructor = aClass.getConstructor(Document.class);
                if (constructor == null) {
                    constructor = aClass.getConstructor();
                    javaBridgeObj = constructor.newInstance();
                } else {
                    javaBridgeObj = constructor.newInstance(document);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return javaBridgeObj;
    }
}
