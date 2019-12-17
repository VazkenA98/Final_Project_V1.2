package Beans;

import java.util.ArrayList;
import java.util.List;

public class TeacherFiles {
    private Integer fileID;
    private String filePathName;
    private String filePath;
    private  String subject;

    public TeacherFiles() {
    }

    public TeacherFiles(Integer fileID, String filePathName, String filePath, String subject) {
        this.fileID = fileID;
        this.filePathName = filePathName;
        this.filePath = filePath;
        this.subject = subject;
    }

    public Integer getFileID() {
        return fileID;
    }

    public void setFileID(Integer fileID) {
        this.fileID = fileID;
    }

    public String getFilePathName() {
        return filePathName;
    }

    public void setFilePathName(String filePathName) {
        this.filePathName = filePathName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
