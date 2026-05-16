package ru.esstu.maven.models;

public class Directory {

    private int id;
    private Integer parentId;
    private String name;

    public Directory(int id, Integer parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }
}