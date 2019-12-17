package Beans;

import java.sql.Blob;

public class Student {
    private Integer id;
    private String img;
    private String custem_ID;
    private String name;
    private String surname;
    private String gender;
    private String birth_date;
    private String nationality;
    private String email;
    private String password;
    private String major;
    private String entrance_year;
    private String gpa;
    private String recover_email;
    private String phone;
    private Integer online_status;

    public Student(Integer id, String img, String custem_ID, String name, String surname, String gender, String birth_date, String nationality, String email, String password, String major, String entrance_year, String gpa, String recover_email, String phone, Integer online_status) {
        this.id = id;
        this.img = img;
        this.custem_ID = custem_ID;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birth_date = birth_date;
        this.nationality = nationality;
        this.email = email;
        this.password = password;
        this.major = major;
        this.entrance_year = entrance_year;
        this.gpa = gpa;
        this.recover_email = recover_email;
        this.phone = phone;
        this.online_status = online_status;
    }

    public Student() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustem_ID() {
        return custem_ID;
    }

    public void setCustem_ID(String custem_ID) {
        this.custem_ID = custem_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEntrance_year() {
        return entrance_year;
    }

    public void setEntrance_year(String entrance_year) {
        this.entrance_year = entrance_year;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getRecover_email() {
        return recover_email;
    }

    public void setRecover_email(String recover_email) {
        this.recover_email = recover_email;
    }

    public Integer getOnline_status() {
        return online_status;
    }

    public void setOnline_status(Integer online_status) {
        this.online_status = online_status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", custem_ID='" + custem_ID + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", nationality='" + nationality + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", major='" + major + '\'' +
                ", entrance_year='" + entrance_year + '\'' +
                ", gpa='" + gpa + '\'' +
                ", recover_email='" + recover_email + '\'' +
                ", phone='" + phone + '\'' +
                ", online_status=" + online_status +
                '}';
    }
}
