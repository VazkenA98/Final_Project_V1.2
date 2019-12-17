package DAO;

import Beans.*;
import DBConnection.DBConnection;
import Utils.Tools;


import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {


    //TODO: get student ID from database for session
    public Integer getStudentID(String email, String custemID, String password) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        // Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        Integer studentID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT student.id_Student FROM `student` WHERE student.custem_ID = ? AND student.email = ? AND student.password= ?";
            pstmt = connection.prepareStatement(sql); // create a statement
            pstmt.setString(1, custemID);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            while (rs.next()) {
                studentID = rs.getInt("student.id_Student");
                System.out.println("student id databse " + studentID);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return studentID;

    }
    public void setStudentOnline(Integer studentID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`student` SET `online_status`=1 WHERE `id_Student`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, studentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public void setStudentOffline(Integer studentID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`student` SET `online_status`=0 WHERE `id_Student`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, studentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public void updateStudentEmailOrPhone(Integer studentID, String recovery_email, String phone) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`student` SET `recover_email`=?,`phone`=?WHERE `id_Student`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString   (1, recovery_email);
            preparedStmt.setString   (2, phone);

            preparedStmt.setInt   (3, studentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public void updateStudentProfileIMG(Integer studentID, String img) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`student` SET `img`=? WHERE `id_Student`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);

                preparedStmt.setString(1, img);

            preparedStmt.setInt   (2, studentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }

    //TODO : getting information of whole students
    public List<Student> getAllStudents() throws SQLException, ClassNotFoundException {
        List<Student> aboutList = new ArrayList<>();
        Student student = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `student` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                student = new Student();

                student.setId(rs.getInt("student.id_Student"));
                student.setCustem_ID(rs.getString("student.custem_ID"));
                student.setName(rs.getString("student.name"));
                student.setSurname(rs.getString("student.surname"));
                student.setGender(rs.getString("student.gendre"));
                student.setBirth_date(rs.getString("student.date_of_birth"));
                student.setNationality(rs.getString("student.nationality"));
                student.setEmail(rs.getString("student.email"));
                student.setPassword(rs.getString("student.password"));
                student.setPhone(rs.getString("student.phone"));
                student.setMajor(rs.getString("student.major"));
                student.setEntrance_year(rs.getString("student.entrance_year"));
                student.setGpa(rs.getString("student.gpa"));
                student.setRecover_email(rs.getString("student.recover_email"));
                student.setOnline_status(rs.getInt("student.online_status"));
                student.setImg(rs.getString("student.img"));

                aboutList.add(student);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }
    public List<Student> getStudentsPersonalInfo(int studentID) throws SQLException, ClassNotFoundException {
        List<Student> aboutList = new ArrayList<>();
        Student student = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `student`";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                Integer wantedId = rs.getInt("student.id_Student");
                if(wantedId == studentID) {

                    student = new Student();

                    student.setId(rs.getInt("student.id_Student"));
                    student.setCustem_ID(rs.getString("student.custem_ID"));
                    student.setName(rs.getString("student.name"));
                    student.setSurname(rs.getString("student.surname"));
                    student.setGender(rs.getString("student.gendre"));
                    student.setBirth_date(rs.getString("student.date_of_birth"));
                    student.setNationality(rs.getString("student.nationality"));
                    student.setEmail(rs.getString("student.email"));
                    student.setPassword(rs.getString("student.password"));
                    student.setPhone(rs.getString("student.phone"));
                    student.setMajor(rs.getString("student.major"));
                    student.setEntrance_year(rs.getString("student.entrance_year"));
                    student.setGpa(rs.getString("student.gpa"));
                    student.setRecover_email(rs.getString("student.recover_email"));
                    student.setOnline_status(rs.getInt("student.online_status"));
                    student.setImg(rs.getString("student.img"));
               /*     String img = Tools.convertBlobToString(rs.getBlob("student.img"));
                    student.setImg(img);*/

                    aboutList.add(student);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }

    public List<Student> getStudentPersonalInfo(int studentID) throws SQLException, ClassNotFoundException {
        List<Student> aboutList = new ArrayList<>();
        Student student = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if (studentID == rs.getInt("student.id_Student")) {
                    student = new Student();

                    student.setId(rs.getInt("student.id_Student"));
                    student.setCustem_ID(rs.getString("student.custem_ID"));
                    student.setName(rs.getString("student.name"));
                    student.setSurname(rs.getString("student.surname"));
                    student.setGender(rs.getString("student.gendre"));
                    student.setBirth_date(rs.getString("student.date_of_birth"));
                    student.setNationality(rs.getString("student.nationality"));
                    student.setEmail(rs.getString("student.email"));
                    student.setPassword(rs.getString("student.password"));
                    student.setPhone(rs.getString("student.phone"));
                    student.setMajor(rs.getString("student.major"));
                    student.setEntrance_year(rs.getString("student.entrance_year"));
                    student.setGpa(rs.getString("student.gpa"));
                    student.setRecover_email(rs.getString("student.recover_email"));
                    student.setOnline_status(rs.getInt("student.online_status"));

                    aboutList.add(student);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }

    public List<StudentGrades> getStudentGrades(int studentID) throws SQLException, ClassNotFoundException {
        List<StudentGrades> aboutList = new ArrayList<>();
        StudentGrades grades = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT mydb.student_grade.id_Student_Grade,mydb.student_grade.exam_1,mydb.student_grade.exam_2 ,mydb.student_grade.final_exam,mydb.student_grade.date_of_exam, mydb.student_grade.semester, mydb.subjects.name as sname,mydb.student_grade.Student_id_Student\n" +
                    "    from mydb.student_grade\n" +
                    "    Inner JOIN mydb.subjects ON mydb.subjects.id_subjects = mydb.student_grade.courses_idcourses;";
            /*pstmt = connection.prepareStatement(sql); // create a statement
           // pstmt.setInt(1,studentID);
            rs = pstmt.executeQuery(sql);*/
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);

            while (rs.next()) {
                Integer wantedStudentID =rs.getInt("student_grade.Student_id_Student");
                if (studentID == wantedStudentID) {
                    grades = new StudentGrades();
                    String str = rs.getString("student_grade.exam_1");
                    grades.setExam_1(rs.getString("student_grade.exam_1"));
                    grades.setExam_2(rs.getString("student_grade.exam_2"));
                    grades.setFinalExam(rs.getString("student_grade.final_exam"));
                    grades.setSemester(rs.getString("student_grade.semester"));
                    grades.setSemesterYear(rs.getString("student_grade.date_of_exam"));
                    grades.setSubject(rs.getString("sname"));
                    grades.setId(rs.getInt("student_grade.id_Student_Grade"));
                    System.out.println(rs.getString("sname"));

                    aboutList.add(grades);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }


    public List<TeacherGrades> getStudentGradesBySubjectAndID(int studentID,int subjectID) throws SQLException, ClassNotFoundException {
        List<TeacherGrades> aboutList = new ArrayList<>();
        TeacherGrades grades = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT mydb.student.name as name, mydb.student.surname as surname,mydb.student_grade.id_Student_Grade,mydb.student_grade.courses_idcourses,mydb.student_grade.exam_1,mydb.student_grade.exam_2 ,mydb.student_grade.final_exam,mydb.student_grade.date_of_exam, mydb.student_grade.semester, mydb.subjects.name as sname,mydb.student_grade.Student_id_Student\n" +
                    "                                                                       from mydb.student_grade \n" +
                    "                                                                     Inner JOIN mydb.subjects ON mydb.subjects.id_subjects = mydb.student_grade.courses_idcourses\n" +
                    "                                                                     Inner JOIN mydb.student ON mydb.student.id_Student = mydb.student_grade.Student_id_Student;";
            /*pstmt = connection.prepareStatement(sql); // create a statement
           // pstmt.setInt(1,studentID);
            rs = pstmt.executeQuery(sql);*/
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);

            while (rs.next()) {
                Integer wantedStudentID =rs.getInt("student_grade.Student_id_Student");
                Integer wantedCourseID = rs.getInt("student_grade.courses_idcourses");
                if (studentID == wantedStudentID && wantedCourseID == subjectID) {
                    grades = new TeacherGrades();
                    String str = rs.getString("name")+" "+rs.getString("surname");

                    grades.setStudentName(str);
                    grades.setStudentID(rs.getInt("student_grade.Student_id_Student"));
                    grades.setGradeID(rs.getInt("student_grade.id_Student_Grade"));
                    grades.setSubject(rs.getString("sname"));
                    grades.setExam_1(rs.getString("student_grade.exam_1"));
                    if(grades.getExam_1()==null){
                        grades.setExam_1("0");
                    }
                    grades.setExam_2(rs.getString("student_grade.exam_2"));
                    if(grades.getExam_2()==null){
                        grades.setExam_2("0");
                    }
                    grades.setFinalExam(rs.getString("student_grade.final_exam"));
                    if(grades.getFinalExam()==null){
                        grades.setFinalExam("0");
                    }
                    grades.setSemester(rs.getString("student_grade.semester"));
                    grades.setSemesterYear(rs.getString("student_grade.date_of_exam"));



                    aboutList.add(grades);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }
    public Integer getStudentGroupID(int studentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        Integer groupID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("student.id_Student");
                if(studentID == wantedID) {
                    groupID = rs.getInt("student.Group_id_Group");
                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return groupID;

    }

    public String getStudentGroupName(int studentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        Integer groupID = getStudentGroupID(studentID);
        String groupName = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.`group`;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("group.id_Group");
                if(groupID == wantedID) {
                    groupName = rs.getString("group.group_name");
                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return groupName;

    }



    public List<Student> getStudentGroupFriends(int studentID) throws SQLException, ClassNotFoundException {
        List<Student> aboutList = new ArrayList<>();
        Student student = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            Integer groupID = getStudentGroupID(studentID);
            while (rs.next()) {
                Integer wantedGroupID = rs.getInt("student.Group_id_Group");
                Integer requestStudentID = rs.getInt("student.id_Student");
                if(groupID == wantedGroupID && studentID != requestStudentID) {
                    student = new Student();

                    student.setId(rs.getInt("student.id_Student"));
                    student.setCustem_ID(rs.getString("student.custem_ID"));
                    student.setName(rs.getString("student.name"));
                    student.setSurname(rs.getString("student.surname"));
                    student.setGender(rs.getString("student.gendre"));
                    student.setBirth_date(rs.getString("student.date_of_birth"));
                    student.setNationality(rs.getString("student.nationality"));
                    student.setEmail(rs.getString("student.email"));
                    student.setPassword(rs.getString("student.password"));
                    student.setPhone(rs.getString("student.phone"));
                    student.setMajor(rs.getString("student.major"));
                    student.setEntrance_year(rs.getString("student.entrance_year"));
                    student.setGpa(rs.getString("student.gpa"));
                    student.setRecover_email(rs.getString("student.recover_email"));
                    student.setOnline_status(rs.getInt("student.online_status"));
                    student.setImg(rs.getString("student.img"));


                    aboutList.add(student);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }
    /////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////



    public List<Teacher> getStudentTeachers(int studentID) throws SQLException, ClassNotFoundException {
        List<Teacher> aboutList = new ArrayList<>();
        Teacher teacher = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT mydb.teacher_groups.Group_id_Group,mydb.teacher.phone,mydb.teacher.name,mydb.teacher.img,mydb.teacher.id_Teacher ,mydb.teacher.custem_ID ,mydb.teacher.recover_email , mydb.teacher.surname , mydb.teacher.online_status as tOnlineStatus , mydb.subjects.name as subjName  " +
                    "from mydb.teacher_groups " +
                    "Inner join mydb.teacher on mydb.teacher.id_Teacher = mydb.teacher_groups.Teacher_id_Teacher " +
                    "Inner join mydb.subjects on mydb.subjects.id_subjects = mydb.teacher_groups.subject;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            Integer groupID = getStudentGroupID(studentID);
            while (rs.next()) {
                Integer wantedGroupID = rs.getInt("teacher_groups.Group_id_Group");

                if(groupID == wantedGroupID) {
                    teacher = new Teacher();
                    teacher.setId(rs.getInt("teacher.id_Teacher"));
                    teacher.setName(rs.getString("teacher.name"));
                    teacher.setSurname(rs.getString("teacher.surname"));
                    teacher.setOnline_status(rs.getInt("tOnlineStatus"));
                    teacher.setSbjectTeachesToGroup(rs.getString("subjName"));
                    teacher.setPhone(rs.getString("teacher.phone"));
                    teacher.setRecover_email(rs.getString("recover_email"));
                    teacher.setCustem_ID(rs.getString("custem_ID"));
                    teacher.setImg(rs.getString("teacher.img"));


                    aboutList.add(teacher);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }


    public String getStudentCustemIDByStudentID(int studentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String custemID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("student.id_Student");
                if(studentID == wantedID) {
                    custemID = rs.getString("student.custem_ID");
                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return custemID;

    }
    public List<String> getStudentCustemIDByGroupID(int groupID) throws SQLException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String custemID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("student.Group_id_Group");
                if(groupID == wantedID) {
                    ids.add(rs.getString("student.custem_ID"));
                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return ids;

    }
    public String getStudentCustemIDByStudentRecoveryEmail(String studentEmail) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String custemID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                String wantedID = rs.getString("student.recover_email");
                if(studentEmail.equals(wantedID)) {
                    custemID = rs.getString("student.custem_ID");
                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return custemID;

    }

    public String getStudentRecoveryEmailByStudentCustemID(String studentCustemID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String recoveryEmail= null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                String wantedID = rs.getString("student.custem_ID");
                if(studentCustemID.equals(wantedID)) {
                    recoveryEmail = rs.getString("student.recover_email");
                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return recoveryEmail;

    }
    public String getStudentGroupScheduale(int groupID) throws SQLException, ClassNotFoundException {


        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;

        String groupScheduale = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.`group`;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("group.id_Group");
                if(groupID == wantedID) {
                     groupScheduale = rs.getString("group.img");

                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return groupScheduale;

    }


    public String getStudentProfileImgIDByStudentID(int studentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String img = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("student.id_Student");
                if(studentID == wantedID) {
                    img = rs.getString("student.img");
                }

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return img;

    }


    public Integer getStudentIDByCustemID(String userCustemID) throws SQLException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        int id = 0;
        Connection connection = null;
        String groupName = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.`student`;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                String wantedID = rs.getString("student.custem_ID");
                if(userCustemID.equals(wantedID)) {
                    id = rs.getInt("student.id_Student");
                }

            }

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return id;
    }

    public List<Student> getStudentsByGroupID(int groupID) throws SQLException {
        List<Student> aboutList = new ArrayList<>();
        Student student = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `student` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                Integer wantedGroupID = rs.getInt("student.Group_id_Group");
                if(wantedGroupID == groupID) {
                    student = new Student();

                    student.setId(rs.getInt("student.id_Student"));
                    student.setCustem_ID(rs.getString("student.custem_ID"));
                    student.setName(rs.getString("student.name"));
                    student.setSurname(rs.getString("student.surname"));
                    student.setGender(rs.getString("student.gendre"));
                    student.setGpa(rs.getString("student.gpa"));

                    aboutList.add(student);
                }
            }

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }

    public String getStudentEntranceYear(Integer studentID) throws SQLException {
        String entranceYear = null;

        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `student` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if(studentID.equals(rs.getInt("student.id_Student"))) {

                    entranceYear = rs.getString("student.entrance_year");

                }
            }

        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return entranceYear;
    }
    public List<Integer> getAllStudentsGroupID() throws SQLException, ClassNotFoundException {
        List<Integer> aboutList = new ArrayList<>();

        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `student` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {


                aboutList.add(rs.getInt("student.Group_id_Group"));

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("sqlException in Application in Admin Section  : " + exception);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (statment != null) {
                statment.close();
            }
        }
        return aboutList;
    }
}


