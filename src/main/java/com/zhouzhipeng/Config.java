package com.zhouzhipeng;

public class Config {

    private String bridgeClassName;
    private String title ="默认标题";
    private String jsName="jb";
    private int windowWidth=500;
    private int windowHeight=600;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
