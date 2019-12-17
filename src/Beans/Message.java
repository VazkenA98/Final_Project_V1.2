package Beans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Message {

    private Integer messageID;
    private String subject;
    private String content;
    private String date;
/*    private String fileName;
    private byte[] filedata;*/
    private List<String> fileName= new ArrayList<>();
    private List<byte[]> filedata= new ArrayList<>();
    private List<String>filePathName = new ArrayList<>();
    private List<String> filePath = new ArrayList<>();
    private String sender;
    private String reciver;
    private Integer seen;
    private String perpose;
    private String senderImg;

    public Message(Integer messageID, String subject, String content, String date, List<String> fileName, List<byte[]> filedata, List<String> filePathName, List<String> filePath, String sender, String reciver, Integer seen, String perpose, String senderImg) {
        this.messageID = messageID;
        this.subject = subject;
        this.content = content;
        this.date = date;
        this.fileName = fileName;
        this.filedata = filedata;
        this.filePathName = filePathName;
        this.filePath = filePath;
        this.sender = sender;
        this.reciver = reciver;
        this.seen = seen;
        this.perpose = perpose;
        this.senderImg = senderImg;
    }

    public Message() {
    }

    public List<String> getFilePathName() {
        return filePathName;
    }

    public void setFilePathName(List<String> filePathName) {
        this.filePathName = filePathName;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public List<String> getFileName() {
        return fileName;
    }

    public void setFileName(List<String> fileName) {
        this.fileName = fileName;
    }

    public List<byte[]> getFiledata() {
        return filedata;
    }

    public void setFiledata(List<byte[]> filedata) {
        this.filedata = filedata;
    }

    public String getSenderImg() {
        return senderImg;
    }

    public void setSenderImg(String senderImg) {
        this.senderImg = senderImg;
    }

    public String getPerpose() {
        return perpose;
    }

    public void setPerpose(String perpose) {
        this.perpose = perpose;
    }



    public Integer getMessageID() {
        return messageID;
    }

    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public Integer getSeen() {
        return seen;
    }

    public void setSeen(Integer seen) {
        this.seen = seen;
    }
}
