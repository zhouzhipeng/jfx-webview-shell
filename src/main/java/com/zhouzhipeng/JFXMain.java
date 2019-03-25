package com.zhouzhipeng;


import com.sun.javafx.webkit.WebConsoleListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import netscape.javascript.JSObject;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class JFXMain extends Application {

    @Override
    public void start(Stage stage) {
        Config config = ConfigLoader.getConfig();


        stage.setWidth(config.getWindowWidth());
        stage.setHeight(config.getWindowHeight());
        Scene scene = new Scene(new Group());

        stage.setTitle(config.getTitle());


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

                win.setMember(config.getJsName(), ConfigLoader.getJavaObj(webEngine.getDocument()));
            }

        });


        WebConsoleListener.setDefaultListener((webView, message, lineNumber, sourceId) -> {
            System.out.println("来自webview: " + message + " 【" + sourceId + " - " + lineNumber + "】");
        });


        webEngine.loadContent(ConfigLoader.getHomePageContent());

//        webEngine.executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");

        scene.setRoot(browser);

        stage.setScene(scene);
        stage.show();


//        webEngine.executeScript()
    }

    public static void run() {
        //加载配置
        ConfigLoader.loadConfig();

        launch();

        System.out.println("start up");


    }


}