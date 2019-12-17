package DAO;

import DBConnection.DBConnection;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

public class SettingsDAO {
    public String getStudentPassword(int studentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String password = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("student.id_Student");
                if(studentID == wantedID) {
                    password = rs.getString("student.password");
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
        return password;

    }
    public void updatePassword(String newPassword,String user,Integer userID) throws SQLException, ClassNotFoundException, IOException, ServletException {

    if(user.equals("student")){
        updateStudentPassword(newPassword,userID);
    }else if(user.equals("teacher")){
        updateTeacherPassword(newPassword,userID);
    }else if(user.equals("department")){
        updateDepartmentPassword(newPassword,userID);
    }else{

    }

    }
    public void updateDepartmentPassword(String newPassword,Integer departmentID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`head_of_department` SET `password`= ? WHERE `id_Head_Department`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1,newPassword);
            preparedStmt.setInt(2, departmentID);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public void updateStudentPassword(String newPassword,Integer studentID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`student` SET `password`= ? WHERE `id_Student`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1,newPassword);
            preparedStmt.setInt(2, studentID);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public String getTeacherPassword(int teacherID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String password = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("teacher.id_Teacher");
                if(teacherID == wantedID) {
                    password = rs.getString("teacher.password");
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
        return password;

    }
    public String getDepartmentPassword(int departmentID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String password = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.head_of_department ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            while (rs.next()) {
                Integer wantedID = rs.getInt("head_of_department.id_Head_Department");
                if(departmentID == wantedID) {
                    password = rs.getString("head_of_department.password");
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
        return password;

    }
    public void updateTeacherPassword(String newPassword,Integer teacherID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`teacher` SET `password`= ? WHERE `id_Teacher`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1,newPassword);
            preparedStmt.setInt(2, teacherID);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }


}
