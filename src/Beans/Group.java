package Beans;

import java.sql.Blob;

public class Group {

    private Integer id;
    private String name;
    private String groupCourse;
    private String fileName;
    private Blob filedata;

    public Group(Integer id, String name, String groupCourse, String fileName, Blob filedata) {
        this.id = id;
        this.name = name;
        this.groupCourse = groupCourse;
        this.fileName = fileName;
        this.filedata = filedata;
    }

    public Group() {
    }

    public String getGroupCourse() {
        return groupCourse;
    }

    public void setGroupCourse(String groupCourse) {
        this.groupCourse = groupCourse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Blob getFiledata() {
        return filedata;
    }

    public void setFiledata(Blob filedata) {
        this.filedata = filedata;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groupCourse='" + groupCourse + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filedata=" + filedata +
                '}';
    }
}
