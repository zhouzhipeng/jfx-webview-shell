package com.zhouzhipeng;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Collection;

public class PluginLoader {
    private static ClassLoader classLoader;

    private static Object javaBridgeObj;

    private static Plugin defaultPlugin;

    private static String homePageContent;

    public static Plugin getDefaultPlugin() {
        return defaultPlugin;
    }

    public static String getHomePageContent() {
        return homePageContent;
    }


    public static void loadPlugin(Plugin plugin) {
        try {
            defaultPlugin = plugin;

            //下载zip包并放到本地文件夹中
            String currentDir = System.getProperty("user.dir");

            File dir = new File(currentDir, "plugin");

            if (!dir.exists()) {
//                FileUtils.forceMkdir(dir);
//
//
//                //从网络下载到本地
//                System.out.println("load plugin from remote site!");
//
//                File targetFile = new File(dir, "default.zip");
//
//                FileUtils.copyURLToFile(new URL(plugin.getZipfileUrl()), targetFile, 3 * 1000, 20 * 1000);
//
//                ZipFile zipFile = new ZipFile(targetFile);
//                System.out.println(zipFile.stream());
//                Enumeration<? extends ZipEntry> entries = zipFile.entries();
//                while (entries.hasMoreElements()) {
//                    ZipEntry zipEntry = entries.nextElement();
//
//                    InputStream is = zipFile.getInputStream(zipEntry);
//
//                    FileUtils.copyInputStreamToFile(is, new File(dir, zipEntry.getName()));
//
//                }

                throw new RuntimeException("error plugin dir!");

            }else{
                System.out.println("load plugin from local disk!");
            }

            loadJarsFromDisk(dir);

            loadHtmlFromDisk(dir);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void loadHtmlFromDisk(File dir) throws Exception {
        if(defaultPlugin.isDevMode()){
            PluginLoader.class
        }
        homePageContent = FileUtils.readFileToString(new File(dir, "index.html"), Charset.forName("UTF-8"));

    }


    private static void loadJarsFromDisk(File dir) throws Exception {

        if(defaultPlugin.isDevMode()){
            //开发模式，直接用当前类的类加载器
            classLoader=PluginLoader.class.getClassLoader();
        }else {
            Collection<File> files = FileUtils.listFiles(dir, new String[]{"jar"}, false);

            URL[] urls = new URL[files.size()];
            int i = 0;
            for (File file : files) {
                urls[i++] = file.toURI().toURL();
            }

            classLoader = URLClassLoader.newInstance(urls);
        }

    }


    public static Object getJavaObj(Document document) {

        try {
            if (javaBridgeObj == null) {

                //加载桥接类并初始化
                Class<?> aClass = classLoader.loadClass(defaultPlugin.getBridgeClassName());
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
