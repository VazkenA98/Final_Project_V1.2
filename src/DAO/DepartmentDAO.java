package DAO;

import Beans.Department;
import Beans.Group;
import Beans.Teacher;
import DBConnection.DBConnection;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public Integer getDepartmentID(String email, String custemID, String password) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        Integer departmentID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT head_of_department.id_Head_Department FROM `head_of_department` WHERE head_of_department.custem_ID = ? AND head_of_department.email = ? AND head_of_department.password = ?";
            pstmt = connection.prepareStatement(sql); // create a statement
            pstmt.setString(1, custemID);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            while (rs.next()) {
                departmentID = rs.getInt("head_of_department.id_Head_Department");
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
        return departmentID;

    }
    public List<Department> getAllDepartments() throws SQLException, ClassNotFoundException {
        List<Department> aboutList = new ArrayList<>();
        Department department = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `head_of_department` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                department = new Department();

                department.setId(rs.getInt("head_of_department.id_Head_Department"));
                department.setCustem_ID(rs.getString("head_of_department.custem_ID"));
                department.setName(rs.getString("head_of_department.name"));
                department.setSurname(rs.getString("head_of_department.surname"));
                department.setGender(rs.getString("head_of_department.gender"));
                department.setBirth_date(rs.getString("head_of_department.date_of_birth"));
                department.setNationality(rs.getString("head_of_department.nationality"));
                department.setEmail(rs.getString("head_of_department.email"));
                department.setPassword(rs.getString("head_of_department.password"));
                department.setPhone(rs.getString("head_of_department.phone"));
                department.setRecover_email(rs.getString("head_of_department.recover_email"));
                department.setOnline_status(rs.getInt("head_of_department.online_status"));
                department.setImg(rs.getString("head_of_department.img"));
                department.setFacultyEnrollment(getDepartmentFaculityEnrollment(rs.getInt("head_of_department.Faculties_id_Faculties")));
                aboutList.add(department);
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

    private String getDepartmentFaculityEnrollment(Integer faculityID) throws SQLException {
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String faculityName = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT faculties.faculty_name FROM `faculties` WHERE faculties.id_Faculties = ?";
            pstmt = connection.prepareStatement(sql); // create a statement
            pstmt.setInt(1, faculityID);

            rs = pstmt.executeQuery();
            // extract data from the ResultSet
            while (rs.next()) {
                faculityName = rs.getString("faculties.faculty_name");
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
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return faculityName;

    }

    public String getTeacherProfileImgIDByDepartmentID(int departmentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String img = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.head_of_department ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("head_of_department.id_Head_Department");
                if(departmentID == wantedID) {
                    img = rs.getString("head_of_department.img");
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
    public void setDepartmentOnline(Integer departmentID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`head_of_department` SET `online_status`=1 WHERE `id_Head_Department`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, departmentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public String getDepartmentCustemIDByDepartmentID(int departmentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String custemID = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.head_of_department ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("head_of_department.id_Head_Department");
                if(departmentID == wantedID) {
                    custemID = rs.getString("head_of_department.custem_ID");
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
    public List<Department> getDepartmentPersonalInfo(int departmentID) throws SQLException, ClassNotFoundException {
        List<Department> aboutList = new ArrayList<>();
        Department department = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.head_of_department ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if (departmentID == rs.getInt("head_of_department.id_Head_Department")) {
                    department = new Department();

                    department.setId(rs.getInt("head_of_department.id_Head_Department"));
                    department.setCustem_ID(rs.getString("head_of_department.custem_ID"));
                    department.setName(rs.getString("head_of_department.name"));
                    department.setSurname(rs.getString("head_of_department.surname"));
                    department.setGender(rs.getString("head_of_department.gender"));
                    department.setBirth_date(rs.getString("head_of_department.date_of_birth"));
                    department.setNationality(rs.getString("head_of_department.nationality"));
                    department.setEmail(rs.getString("head_of_department.email"));
                    department.setPassword(rs.getString("head_of_department.password"));
                    department.setPhone(rs.getString("head_of_department.phone"));
                    department.setRecover_email(rs.getString("head_of_department.recover_email"));
                    department.setOnline_status(rs.getInt("head_of_department.online_status"));
                    department.setImg(rs.getString("head_of_department.img"));

                    aboutList.add(department);
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
    public void setDepartmentOffline(Integer departmentID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`head_of_department` SET `online_status`=0 WHERE `id_Head_Department`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, departmentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public void updateDepartmentEmailOrPhone(Integer departmentID, String recovery_email, String phone) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`head_of_department` SET `recover_email`=?,`phone`=?WHERE `id_Head_Department`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString   (1, recovery_email);
            preparedStmt.setString   (2, phone);

            preparedStmt.setInt   (3, departmentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }


    public void updateDepartmentProfileIMG(Integer departmentID, String img) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`head_of_department` SET `img`=? WHERE `id_Head_Department`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);

            preparedStmt.setString(1, img);

            preparedStmt.setInt   (2, departmentID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }

    public List<Integer> getDepartmentGroupIDs(Integer departmentID) throws SQLException {

        /*SELECT mydb.group.id_Group from mydb.group where mydb.group.Head_of_department_id_Head_Department = 1;*/
        List<Integer> idList = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT *  FROM `group`;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if (departmentID == rs.getInt("group.Head_of_department_id_Head_Department")) {

                    idList.add(rs.getInt("group.id_Group"));
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
        return idList;
    }

    public List<Integer> getTeacherIDByGroupID(Integer groupID) throws SQLException {
        List<Integer> idList = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT *  FROM `teacher_groups`;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if (groupID == rs.getInt("teacher_groups.Group_id_Group")) {

                    idList.add(rs.getInt("teacher_groups.Teacher_id_Teacher"));
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
        return idList;
    }

    public List<Group> getGroupsByDepartmentID(Integer departmentID) throws SQLException {
        List<Group> aboutList = new ArrayList<>();
        Group group = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT mydb.group.Head_of_department_id_Head_Department,mydb.group.id_Group,mydb.group.group_name, mydb.courses.name as groupCourseName\n" +
                    "                                       from mydb.group\n" +
                    "                                    Inner join mydb.courses on mydb.courses.id_Faculty_Courses = mydb.group.Courses_id_Faculty_Courses;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);

            while (rs.next()) {
                if(rs.getInt("group.Head_of_department_id_Head_Department") == departmentID) {
                    group = new Group();
                    group.setId(rs.getInt("group.id_Group"));
                    group.setName(rs.getString("group.group_name"));
                    group.setGroupCourse(rs.getString("groupCourseName"));
                    aboutList.add(group);

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

    public Integer getDepartmentFaculityID(int departmentID) throws SQLException {
    int id = 0;
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `head_of_department` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
              if(rs.getInt("head_of_department.id_Head_Department") == departmentID) {

                  id = rs.getInt("head_of_department.Faculties_id_Faculties");
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

    public List<String> getDepartmentCourses(Integer faculityID) throws SQLException {
        List<String> courses = new ArrayList<>();
        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM `courses` ";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                if(rs.getInt("courses.Faculties_id_Faculties") == faculityID) {

                    courses.add(rs.getString("courses.name"));
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
        return courses;
    }
}
