package com.zhouzhipeng;

public class Config {

    private String bridgeClassName;
    private String name;
    private String jsName;
    private int windowWidth;
    private int windowHeight;

    private boolean devMode;

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public String getJsName() {
        return jsName;
    }

    public void setJsName(String jsName) {
        this.jsName = jsName;
    }


    public String getBridgeClassName() {
        return bridgeClassName;
    }

    public void setBridgeClassName(String bridgeClassName) {
        this.bridgeClassName = bridgeClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
