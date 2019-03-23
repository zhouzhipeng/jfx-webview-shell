package com.zhouzhipeng;


import com.alibaba.fastjson.JSONArray;
import com.sun.javafx.webkit.WebConsoleListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import net.dongliu.requests.Requests;
import netscape.javascript.JSObject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

public class JFXMain extends Application {

    //    @Override
    public void start(Stage stage) throws Exception {
        Plugin defaultPlugin = PluginLoader.getDefaultPlugin();


        stage.setWidth(defaultPlugin.getWindowWidth());
        stage.setHeight(defaultPlugin.getWindowHeight());
        Scene scene = new Scene(new Group());

        stage.setTitle(defaultPlugin.getName());


        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        webEngine.setJavaScriptEnabled(true);


        webEngine.setOnError(webErrorEvent -> {
            System.out.println(webErrorEvent.toString());
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);
        ReadOnlyObjectProperty<Worker.State> stateReadOnlyObjectProperty = webEngine.getLoadWorker().stateProperty();

        stateReadOnlyObjectProperty.addListener((ov, oldState, newState) -> {

            if (newState == Worker.State.SUCCEEDED) {


                JSObject win = (JSObject) webEngine.executeScript("window");

                win.setMember(defaultPlugin.getJsName(), PluginLoader.getJavaObj(webEngine.getDocument()));
            }

        });


        WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
            System.out.println("来自webview: " + message + " 【" + sourceId + " - " + lineNumber + "】");
        });


        webEngine.loadContent(PluginLoader.getHomePageContent());

//        webEngine.executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");

        scene.setRoot(browser);

        stage.setScene(scene);
        stage.show();


//        webEngine.executeScript()
    }

    public static void main(String[] args) throws Exception {


        //加载默认插件
//        List<Plugin> plugins= JSONArray.parseArray(Requests.get("https://api.zhouzhipeng.com/common/plugin-list").send().readToText(),Plugin.class);
//        Plugin plugin = plugins.get(0);
        Plugin plugin = new Plugin();
        plugin.setJsName("jb");
        plugin.setBridgeClassName("com.zhouzhipeng.test.JavaBridge");
        plugin.setName("【一键外网可见】客户端");
        plugin.setWindowHeight(600);
        plugin.setWindowWidth(500);
        PluginLoader.loadPlugin(plugin);


        launch(args);

        System.out.println("start up");


    }


}