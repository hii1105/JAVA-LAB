package ru.esstu.maven.models;

public class FileItem {

    private int id;
    private int directoryId;
    private String name;
    private long size;

    public FileItem(int id, int directoryId,
                    String name, long size) {

        this.id = id;
        this.directoryId = directoryId;
        this.name = name;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public int getDirectoryId() {
        return directoryId;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }
}