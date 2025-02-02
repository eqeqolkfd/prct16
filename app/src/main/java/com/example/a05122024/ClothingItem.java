package com.example.a05122024;

public class ClothingItem {
    private String name;
    private String description;
    private String imageName;

    public ClothingItem(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
