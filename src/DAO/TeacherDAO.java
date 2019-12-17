package DAO;

import Beans.*;
import DBConnection.DBConnection;
import Utils.AES256;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {

    public Integer getTeacherID(String email, String custemID, String password) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        Integer teacherID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT teacher.id_Teacher FROM `teacher` WHERE teacher.custem_ID = ? AND teacher.email = ? AND teacher.password = ?";
            pstmt = connection.prepareStatement(sql); // create a statement
            pstmt.setString(1, custemID);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            while (rs.next()) {
                teacherID = rs.getInt("teacher.id_Teacher");
                System.out.println("teacher id databse " + teacherID);
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
        return teacherID;

    }
    public List<Teacher> getAllTeachers() throws SQLException, ClassNotFoundException {
        List<Teacher> aboutList = new ArrayList<>();
        Teacher teacher = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `teacher` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                teacher = new Teacher();

                teacher.setId(rs.getInt("teacher.id_Teacher"));
                teacher.setCustem_ID(rs.getString("teacher.custem_ID"));
                teacher.setName(rs.getString("teacher.name"));
                teacher.setSurname(rs.getString("teacher.surname"));
                teacher.setGender(rs.getString("teacher.gender"));
                teacher.setBirth_date(rs.getString("teacher.date_of_birth"));
                teacher.setNationality(rs.getString("teacher.nationality"));
                teacher.setEmail(rs.getString("teacher.email"));
                teacher.setPassword(rs.getString("teacher.password"));
                teacher.setPhone(rs.getString("teacher.phone"));
                teacher.setRecover_email(rs.getString("teacher.recover_email"));
                teacher.setOnline_status(rs.getInt("teacher.online_status"));
                teacher.setImg(rs.getString("teacher.img"));

                aboutList.add(teacher);
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
    public void setTeacherOnline(Integer teacherID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`teacher` SET `online_status`=1 WHERE `id_Teacher`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, teacherID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public String getTeacherProfileImgIDByTeacherID(int teacherID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String img = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("teacher.id_Teacher");
                if(teacherID == wantedID) {
                    img = rs.getString("teacher.img");
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

    public List<Teacher> getTeacherPersonalInfo(int teacherID) throws SQLException, ClassNotFoundException {
        List<Teacher> aboutList = new ArrayList<>();
        Teacher teacher = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if (teacherID == rs.getInt("teacher.id_Teacher")) {
                    teacher = new Teacher();

                    teacher.setId(rs.getInt("teacher.id_Teacher"));
                    teacher.setCustem_ID(rs.getString("teacher.custem_ID"));
                    teacher.setName(rs.getString("teacher.name"));
                    teacher.setSurname(rs.getString("teacher.surname"));
                    teacher.setGender(rs.getString("teacher.gender"));
                    teacher.setBirth_date(rs.getString("teacher.date_of_birth"));
                    teacher.setNationality(rs.getString("teacher.nationality"));
                    teacher.setEmail(rs.getString("teacher.email"));
                    teacher.setPassword(rs.getString("teacher.password"));
                    teacher.setPhone(rs.getString("teacher.phone"));
                    teacher.setRecover_email(rs.getString("teacher.recover_email"));
                    teacher.setOnline_status(rs.getInt("teacher.online_status"));
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
    public Teacher getTeacherTeachersPersonalInfo(int teacherID) throws SQLException, ClassNotFoundException {

        Teacher teacher = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if (teacherID == rs.getInt("teacher.id_Teacher")) {
                    teacher = new Teacher();

                    teacher.setId(rs.getInt("teacher.id_Teacher"));
                    teacher.setCustem_ID(rs.getString("teacher.custem_ID"));
                    teacher.setName(rs.getString("teacher.name"));
                    teacher.setSurname(rs.getString("teacher.surname"));
                    teacher.setGender(rs.getString("teacher.gender"));
                    teacher.setBirth_date(rs.getString("teacher.date_of_birth"));
                    teacher.setNationality(rs.getString("teacher.nationality"));
                    teacher.setEmail(rs.getString("teacher.email"));
                    teacher.setPassword(rs.getString("teacher.password"));
                    teacher.setPhone(rs.getString("teacher.phone"));
                    teacher.setRecover_email(rs.getString("teacher.recover_email"));
                    teacher.setOnline_status(rs.getInt("teacher.online_status"));

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
        return teacher;
    }

    public String getTeacherCustemIDByTeacherID(int teacherID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String custemID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("teacher.id_Teacher");
                if(teacherID == wantedID) {
                    custemID = rs.getString("teacher.custem_ID");
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

    public void updateTeacherProfileIMG(Integer teacherID, String img) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`teacher` SET `img`=? WHERE `id_Teacher`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);

            preparedStmt.setString(1, img);

            preparedStmt.setInt   (2, teacherID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }

    public void updateTeacherEmailOrPhone(Integer teacherID, String recovery_email, String phone) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`teacher` SET `recover_email`=?,`phone`=?WHERE `id_Teacher`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString   (1, recovery_email);
            preparedStmt.setString   (2, phone);

            preparedStmt.setInt   (3, teacherID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }

    public void setTeacherOffline(Integer teacherID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`teacher` SET `online_status`=0 WHERE `id_Teacher`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, teacherID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public void setFileToTeacherStore(String file_path, String file_name,String subject,Integer teacherID) throws SQLException, ClassNotFoundException, IOException, ServletException {


        String sql = "INSERT INTO mydb.teacher_store (file_path,file_name,subject,Teacher_id_Teacher) values (?,?,?,?);";
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = con
                    .prepareStatement(sql);


            preparedStatement.setString(1, file_path);
            preparedStatement.setString(2, file_name);
            preparedStatement.setString(3,subject);
            preparedStatement.setInt(4,teacherID);




            // fetches input stream of the upload file for the blob column


            // sends the statement to the database server
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception

        }
    }

    public List<TeacherFiles> getTeacherPDFFiles(int teacherID) throws SQLException, ClassNotFoundException {
        List<TeacherFiles> aboutList = new ArrayList<>();

        TeacherFiles teacherFiles = null;
        String senderFullName = null;
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        TeacherDAO teacherDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.teacher_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet

            while (rs.next()) {
                String wantedID = rs.getString("teacher_store.Teacher_id_Teacher");

                if(teacherID == Integer.parseInt(wantedID)) {
                    teacherFiles = new TeacherFiles();

                    teacherFiles.setFileID(rs.getInt("teacher_store.id_Teacher_Store"));
                    teacherFiles.setFilePath(AES256.decrypt(rs.getString("teacher_store.file_path")));
                    teacherFiles.setFilePathName(AES256.decrypt(rs.getString("teacher_store.file_name")));
                    teacherFiles.setSubject(rs.getString("teacher_store.subject"));


                    aboutList.add(teacherFiles);
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
    public void deleteTeacherSelectedFiles(Integer fileID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "DELETE FROM `mydb`.`teacher_store` WHERE `id_Teacher_Store`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, fileID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
        }finally {
            if (connection != null) {
                connection.close();
            }
            if (rs != null) {
                rs.close();
            }

        }

    }

    public List<Integer> getTeacherGroupsID(int teacherID) throws SQLException {
        List<Integer> ids = new ArrayList<>();



        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.teacher_groups ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet

            while (rs.next()) {
                String wantedID = rs.getString("teacher_groups.Teacher_id_Teacher");
                if(teacherID == Integer.parseInt(wantedID)) {

                    ids.add(rs.getInt("teacher_groups.Group_id_Group"));
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
        return ids;

    }

    public Group getTeacherGroups(Integer groupID) throws SQLException {


        Group group = null;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT mydb.group.id_Group,mydb.group.group_name, mydb.courses.name as groupCourseName\n" +
                    "                    from mydb.group\n" +
                    "                    Inner join mydb.courses on mydb.courses.id_Faculty_Courses = mydb.group.Courses_id_Faculty_Courses;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);

            while (rs.next()) {
                if(rs.getInt("group.id_Group") == groupID) {
                    group = new Group();
                    group.setId(rs.getInt("group.id_Group"));
                    group.setName(rs.getString("group.group_name"));
                    group.setGroupCourse(rs.getString("groupCourseName"));


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


        return group;
    }

    public List<Student> getTeacherGroupStudents(int groupID) throws SQLException {
        List<Student> aboutList = new ArrayList<>();
        Student student = null;
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `student` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet

            while (rs.next()) {
                String wantedID = rs.getString("student.Group_id_Group");
                if(groupID == Integer.parseInt(wantedID)) {
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

    public List<Integer> getGroupsTeachersID(int groupID) throws SQLException {
        List<Integer> ids = new ArrayList<>();



        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.teacher_groups ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet

            while (rs.next()) {
                String wantedID = rs.getString("teacher_groups.Group_id_Group");
                if(groupID == Integer.parseInt(wantedID)) {

                    ids.add(rs.getInt("teacher_groups.Teacher_id_Teacher"));
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
        return ids;

    }

    public List<Integer> getTeacherGroupSubject(int groupID, int teacherID) throws SQLException {

        List<Integer> ids = new ArrayList<>();



        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.teacher_groups ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet

            while (rs.next()) {
                String wantedGroupID = rs.getString("teacher_groups.Group_id_Group");
                Integer  wantedTeacherID =  rs.getInt("teacher_groups.Teacher_id_Teacher");
                if(groupID == Integer.parseInt(wantedGroupID) && wantedTeacherID == teacherID) {

                    ids.add(rs.getInt("teacher_groups.subject"));
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
        return ids;
    }

    public Integer getTeacherGroupSubjectSemesters(int groupID, int teacherID,int subjectID) throws SQLException {

       int semester = 0;



        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.teacher_groups ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet

            while (rs.next()) {
                String wantedGroupID = rs.getString("teacher_groups.Group_id_Group");
                Integer  wantedTeacherID =  rs.getInt("teacher_groups.Teacher_id_Teacher");
                Integer wantedSubjectId =  rs.getInt("teacher_groups.subject");
                if(groupID == Integer.parseInt(wantedGroupID) && wantedTeacherID == teacherID && subjectID == wantedSubjectId) {

                    semester = rs.getInt("teacher_groups.first_semester")-rs.getInt("teacher_groups.second_semester");
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
        return semester;
    }

    public void updateStudentGrades(String exam1, String exam2, String finalexam, String date,Integer gradeID) throws SQLException, ClassNotFoundException, IOException, ServletException {

        String sql = "UPDATE student_grade\n" +
                "SET student_grade.exam_1 = ?, student_grade.exam_2= ?, student_grade.final_exam=?,student_grade.date_of_exam=?\n" +
                "WHERE student_grade.id_Student_Grade = ?;";
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = con
                    .prepareStatement(sql);

            preparedStatement.setString(1,exam1);
            preparedStatement.setString(2,exam2);
            preparedStatement.setString(3,finalexam);

            preparedStatement.setString(4,date);
            preparedStatement.setInt(5,gradeID);





            // sends the statement to the database server
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            System.out.println(e);
        }

    }
    public Teacher getTeacherByID(Integer teacherID) throws SQLException, ClassNotFoundException {

        Teacher teacher = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `teacher` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if(teacherID == rs.getInt("teacher.id_Teacher")) {
                    teacher = new Teacher();

                    teacher.setId(rs.getInt("teacher.id_Teacher"));
                    teacher.setCustem_ID(rs.getString("teacher.custem_ID"));
                    teacher.setName(rs.getString("teacher.name"));
                    teacher.setSurname(rs.getString("teacher.surname"));
                    teacher.setGender(rs.getString("teacher.gender"));
                    teacher.setBirth_date(rs.getString("teacher.date_of_birth"));
                    teacher.setNationality(rs.getString("teacher.nationality"));
                    teacher.setEmail(rs.getString("teacher.email"));
                    teacher.setPassword(rs.getString("teacher.password"));
                    teacher.setPhone(rs.getString("teacher.phone"));
                    teacher.setRecover_email(rs.getString("teacher.recover_email"));
                    teacher.setOnline_status(rs.getInt("teacher.online_status"));
                    teacher.setImg(rs.getString("teacher.img"));


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
        return teacher;
    }

    public Integer getTeacherIDByCustemID(String userCustemID) throws SQLException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        int id = 0;
        Connection connection = null;
        String groupName = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.`teacher`;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                String wantedID = rs.getString("teacher.custem_ID");
                if(userCustemID.equals(wantedID)) {
                    id = rs.getInt("teacher.id_Teacher");
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
}
