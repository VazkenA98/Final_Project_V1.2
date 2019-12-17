package Beans;

public class StudentGrades {

    private Integer id;
    private String exam_1;
    private String exam_2;
    private String finalExam;
    private String totalScore;
    private String scoreSymbol;
    private String semester;
    private String semesterYear;
    private String subject;
    private Integer studentID;

    public StudentGrades(Integer id, String exam_1, String exam_2, String finalExam, String totalScore, String scoreSymbol, String semester, String semesterYear, String subject, Integer studentID) {
        this.id = id;
        this.exam_1 = exam_1;
        this.exam_2 = exam_2;
        this.finalExam = finalExam;
        this.totalScore = totalScore;
        this.scoreSymbol = scoreSymbol;
        this.semester = semester;
        this.semesterYear = semesterYear;
        this.subject = subject;
        this.studentID = studentID;
    }

    public StudentGrades() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExam_1() {
        return exam_1;
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

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getScoreSymbol() {
        return scoreSymbol;
    }

    public void setScoreSymbol(String scoreSymbol) {
        this.scoreSymbol = scoreSymbol;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "StudentGrades{" +
                "id=" + id +
                ", exam_1='" + exam_1 + '\'' +
                ", exam_2='" + exam_2 + '\'' +
                ", finalExam='" + finalExam + '\'' +
                ", totalScore='" + totalScore + '\'' +
                ", scoreSymbol='" + scoreSymbol + '\'' +
                ", semester='" + semester + '\'' +
                ", semesterYear='" + semesterYear + '\'' +
                ", subject='" + subject + '\'' +
                ", studentID=" + studentID +
                '}';
    }
}
