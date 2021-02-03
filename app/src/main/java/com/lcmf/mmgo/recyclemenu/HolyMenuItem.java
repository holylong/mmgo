package com.lcmf.mmgo.recyclemenu;

public class HolyMenuItem {

    private String name;
    private int imageId;
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public HolyMenuItem(String name, int imageId, String className){
        this.name = name;
        this.imageId = imageId;
        this.className = className;

    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}