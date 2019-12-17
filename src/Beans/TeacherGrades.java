package Beans;

public class TeacherGrades {

    private Integer gradeID;
    private String studentName;
    private Integer studentID;
    private String exam_1;
    private String exam_2;
    private String finalExam;
    private String semester;
    private String semesterYear;
    private String subject;

    public TeacherGrades(Integer gradeID, String studentName, Integer studentID, String exam_1, String exam_2, String finalExam, String semester, String semesterYear, String subject) {
        this.gradeID = gradeID;
        this.studentName = studentName;
        this.studentID = studentID;
        this.exam_1 = exam_1;
        this.exam_2 = exam_2;
        this.finalExam = finalExam;
        this.semester = semester;
        this.semesterYear = semesterYear;
        this.subject = subject;
    }

    public TeacherGrades() {
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getGradeID() {
        return gradeID;
    }

    public void setGradeID(Integer gradeID) {
        this.gradeID = gradeID;
    }

    public String getExam_1() {
        return exam_1;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getExam_1(String exam_1) {
        return this.exam_1;
    }

    public void setExam_1(String exam_1) {
        this.exam_1 = exam_1;
    }

    public String getExam_2() {
        return exam_2;
    }

    public void setExam_2(String exam_2) {
        this.exam_2 = exam_2;
    }

    public String getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(String finalExam) {
        this.finalExam = finalExam;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSemesterYear() {
        return semesterYear;
    }

    public void setSemesterYear(String semesterYear) {
        this.semesterYear = semesterYear;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "TeacherGrades{" +
                "gradeID=" + gradeID +
                ", studentName='" + studentName + '\'' +
                ", studentID=" + studentID +
                ", exam_1='" + exam_1 + '\'' +
                ", exam_2='" + exam_2 + '\'' +
                ", finalExam='" + finalExam + '\'' +
                ", semester='" + semester + '\'' +
                ", semesterYear='" + semesterYear + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
