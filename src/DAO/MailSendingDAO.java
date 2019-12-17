package DAO;


import Beans.Message;
import DBConnection.DBConnection;
import Utils.AES256;
import Utils.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MailSendingDAO {
    /*UPDATE `mydb`.`student` SET `online_status`=1 WHERE `id_Student`=3;*/
    public Integer sendMessage(String reciverID, String senderID, String subject, String content,String msgPerpose) throws SQLException, ClassNotFoundException, IOException, ServletException {
        StudentDAO studentDAO = null;
        String formattedDateTime = null;
        String senderCustemId = null;
        String sql = "INSERT INTO mydb.email_store (subject,contant,send_date,sender,reciver,seen,perpose) values (?,?,?,?,?,?,?);";
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = con
                    .prepareStatement(sql);
            studentDAO = new StudentDAO();
            Integer sender = Integer.parseInt(senderID);
             senderCustemId = studentDAO.getStudentCustemIDByStudentID(sender);
            /*String reciverCustemId = studentDAO.getStudentCustemIDByStudentRecoveryEmail(reciverEmail);*/

            LocalDateTime today = LocalDateTime.now();

            ZoneId id = ZoneId.of("Europe/Paris");
            ZonedDateTime zonedDateTime = ZonedDateTime.of(today, id);      //That's how you add timezone to date

             formattedDateTime = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm")
                    .format(zonedDateTime);


            preparedStatement.setString(1,subject);
            preparedStatement.setString(2,content);
            preparedStatement.setString(3,formattedDateTime);

            preparedStatement.setString(4,senderCustemId);
            preparedStatement.setString(5,reciverID);
            preparedStatement.setInt(6,0);

            preparedStatement.setString(7,msgPerpose);




            // sends the statement to the database server
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
        System.out.println(e);
        }
        Integer msgID = getMessageID(reciverID,senderCustemId,subject,content,formattedDateTime,msgPerpose);
        return  msgID;
    }
    public Integer teacherSendMessage(String reciverID, String senderID, String subject, String content,String msgPerpose) throws SQLException, ClassNotFoundException, IOException, ServletException {
        TeacherDAO teacherDAO = null;
        String formattedDateTime = null;
        String senderCustemId = null;
        String sql = "INSERT INTO mydb.email_store (subject,contant,send_date,sender,reciver,seen,perpose) values (?,?,?,?,?,?,?);";
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = con
                    .prepareStatement(sql);
            teacherDAO = new TeacherDAO();
            Integer sender = Integer.parseInt(senderID);
            senderCustemId = teacherDAO.getTeacherCustemIDByTeacherID(sender);
            /*String reciverCustemId = studentDAO.getStudentCustemIDByStudentRecoveryEmail(reciverEmail);*/

            LocalDateTime today = LocalDateTime.now();

            ZoneId id = ZoneId.of("Europe/Paris");
            ZonedDateTime zonedDateTime = ZonedDateTime.of(today, id);      //That's how you add timezone to date

            formattedDateTime = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm")
                    .format(zonedDateTime);


            preparedStatement.setString(1,subject);
            preparedStatement.setString(2,content);
            preparedStatement.setString(3,formattedDateTime);

            preparedStatement.setString(4,senderCustemId);
            preparedStatement.setString(5,reciverID);
            preparedStatement.setInt(6,0);

            preparedStatement.setString(7,msgPerpose);




            // sends the statement to the database server
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            System.out.println(e);
        }
        Integer msgID = getMessageID(reciverID,senderCustemId,subject,content,formattedDateTime,msgPerpose);
        return  msgID;
    }
    public Integer departmentSendMessage(String reciverID, String senderID, String subject, String content,String msgPerpose) throws SQLException, ClassNotFoundException, IOException, ServletException {
        DepartmentDAO departmentDAO = null;
        String formattedDateTime = null;
        String senderCustemId = null;
        String sql = "INSERT INTO mydb.email_store (subject,contant,send_date,sender,reciver,seen,perpose) values (?,?,?,?,?,?,?);";
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = con
                    .prepareStatement(sql);
            departmentDAO = new DepartmentDAO();
            Integer sender = Integer.parseInt(senderID);
            senderCustemId = departmentDAO.getDepartmentCustemIDByDepartmentID(sender);
            /*String reciverCustemId = studentDAO.getStudentCustemIDByStudentRecoveryEmail(reciverEmail);*/

            LocalDateTime today = LocalDateTime.now();

            ZoneId id = ZoneId.of("Europe/Paris");
            ZonedDateTime zonedDateTime = ZonedDateTime.of(today, id);      //That's how you add timezone to date

            formattedDateTime = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm")
                    .format(zonedDateTime);


            preparedStatement.setString(1,subject);
            preparedStatement.setString(2,content);
            preparedStatement.setString(3,formattedDateTime);

            preparedStatement.setString(4,senderCustemId);
            preparedStatement.setString(5,reciverID);
            preparedStatement.setInt(6,0);

            preparedStatement.setString(7,msgPerpose);




            // sends the statement to the database server
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            System.out.println(e);
        }
        Integer msgID = getMessageID(reciverID,senderCustemId,subject,content,formattedDateTime,msgPerpose);
        return  msgID;
    }
    public void setMessageSeen(Integer messageID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "UPDATE `mydb`.`email_store` SET `seen`=1 WHERE `idEmail_Store`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, messageID);
            // execute the java preparedstatement
            preparedStmt.executeUpdate();


        } catch (SQLException e) {
            // process sql exception

        }
    }
    public String getMessageSenderIDByMessageID(int messageID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        String senderCustemId = null;

        try {

                connection = DBConnection.getConnection();
                String sql = "SELECT * FROM `mydb`.`email_store`;";
                statment = connection.createStatement();
                rs = statment.executeQuery(sql);
                // extract data from the ResultSet
                while (rs.next()) {
                    int wantedID = rs.getInt("email_store.idEmail_Store");
System.out.println(messageID);
                    if(wantedID == messageID) {
                        senderCustemId =  rs.getString("email_store.sender");
                    }

                }



        } catch (SQLException e) {
            // process sql exception

        }
        return senderCustemId;
    }


    public Integer getMyMessageCount(int userID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        StudentDAO studentDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            studentDAO = new StudentDAO();
            String userCustemID = studentDAO.getStudentCustemIDByStudentID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");
                Integer messageStatus = rs.getInt("email_store.seen");
                String messageType = rs.getString("email_store.perpose");
                if(userCustemID.equals(wantedID) && messageStatus == 0 && messageType.equals("msg")) {
                    count++;
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
        return count;

    }


    public Integer getTeacherMessageCount(int userID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        TeacherDAO teacherDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            teacherDAO = new TeacherDAO();
            String userCustemID = teacherDAO.getTeacherCustemIDByTeacherID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");
                Integer messageStatus = rs.getInt("email_store.seen");
                String messageType = rs.getString("email_store.perpose");
                if(userCustemID.equals(wantedID) && messageStatus == 0 && messageType.equals("msg")) {
                    count++;
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
        return count;

    }
    public Integer getDepartmentMessageCount(int userID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        DepartmentDAO departmentDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            departmentDAO = new DepartmentDAO();
            String userCustemID = departmentDAO.getDepartmentCustemIDByDepartmentID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");
                Integer messageStatus = rs.getInt("email_store.seen");
                String messageType = rs.getString("email_store.perpose");
                if(userCustemID.equals(wantedID) && messageStatus == 0 && messageType.equals("msg")) {
                    count++;
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
        return count;

    }
    public Integer getTeacherHomwWorkCount(int userID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        TeacherDAO teacherDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            teacherDAO = new TeacherDAO();
            String userCustemID = teacherDAO.getTeacherCustemIDByTeacherID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");
                Integer messageStatus = rs.getInt("email_store.seen");
                String messageType = rs.getString("email_store.perpose");
                if(userCustemID.equals(wantedID) && messageStatus == 0 && messageType.equals("homework")) {
                    count++;
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
        return count;

    }


    public Integer getMyHomwWorkCount(int userID) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        StudentDAO studentDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            studentDAO = new StudentDAO();
            String userCustemID = studentDAO.getStudentCustemIDByStudentID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");
                Integer messageStatus = rs.getInt("email_store.seen");
                String messageType = rs.getString("email_store.perpose");
                if(userCustemID.equals(wantedID) && messageStatus == 0 && messageType.equals("homework")) {
                    count++;
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
        return count;

    }

    public List<Message> getMyMessage(int userID) throws SQLException, ClassNotFoundException {
        List<Message> aboutList = new ArrayList<>();

        Message message = null;
        String senderFullName = null;
        ResultSet rs = null;
        Blob file = null;
        byte[] filedata = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        StudentDAO studentDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            studentDAO = new StudentDAO();
            String userCustemID = studentDAO.getStudentCustemIDByStudentID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");

                if(userCustemID.equals(wantedID)) {
                    message = new Message();
                    message.setMessageID(rs.getInt("email_store.idEmail_Store"));
                    /*message.setFileName(rs.getString("email_store.file_name"));*/
                    message.setSubject(AES256.decrypt(rs.getString("email_store.subject")));
                    message.setContent(AES256.decrypt(rs.getString("email_store.contant")));
                    message.setDate(rs.getString("email_store.send_date"));
                    message.setReciver(rs.getString("email_store.reciver"));
                    senderFullName = getSenderFullNameByCustemID(rs.getString("email_store.sender"));
                    message.setSender(senderFullName);
                    message.setSeen(rs.getInt("email_store.seen"));
                    message.setPerpose(rs.getString("email_store.perpose"));

                    String img = getSenderImg(rs.getString("email_store.sender"));
                    message.setSenderImg(img);

                    message.setFilePath(getPDFData(rs.getInt("email_store.idEmail_Store")));
                    message.setFilePathName(getPDFFileName(rs.getInt("email_store.idEmail_Store")));


                    aboutList.add(message);
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
    public List<Message> getTeacherMessage(int userID) throws SQLException, ClassNotFoundException {
        List<Message> aboutList = new ArrayList<>();

        Message message = null;
        String senderFullName = null;
        ResultSet rs = null;
        Blob file = null;
        byte[] filedata = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        TeacherDAO teacherDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            teacherDAO = new TeacherDAO();
            String userCustemID = teacherDAO.getTeacherCustemIDByTeacherID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");

                if(userCustemID.equals(wantedID)) {
                    message = new Message();
                    message.setMessageID(rs.getInt("email_store.idEmail_Store"));
                    /*message.setFileName(rs.getString("email_store.file_name"));*/
                    message.setSubject(AES256.decrypt(rs.getString("email_store.subject")));
                    message.setContent(AES256.decrypt(rs.getString("email_store.contant")));
                    message.setDate(rs.getString("email_store.send_date"));
                    message.setReciver(rs.getString("email_store.reciver"));
                    senderFullName = getSenderFullNameByCustemID(rs.getString("email_store.sender"));
                    message.setSender(senderFullName);
                    message.setSeen(rs.getInt("email_store.seen"));
                    message.setPerpose(rs.getString("email_store.perpose"));

                    String img = getSenderImg(rs.getString("email_store.sender"));
                    message.setSenderImg(img);

                    message.setFilePath(getPDFData(rs.getInt("email_store.idEmail_Store")));
                    message.setFilePathName(getPDFFileName(rs.getInt("email_store.idEmail_Store")));


                    aboutList.add(message);
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
    public List<Message> getDepartmentMessage(int userID) throws SQLException, ClassNotFoundException {
        List<Message> aboutList = new ArrayList<>();

        Message message = null;
        String senderFullName = null;
        ResultSet rs = null;
        Blob file = null;
        byte[] filedata = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        DepartmentDAO departmentDAO = null;
        int count = 0;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.email_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet
            departmentDAO = new DepartmentDAO();
            String userCustemID = departmentDAO.getDepartmentCustemIDByDepartmentID(userID);
            while (rs.next()) {
                String wantedID = rs.getString("email_store.reciver");

                if(userCustemID.equals(wantedID)) {
                    message = new Message();
                    message.setMessageID(rs.getInt("email_store.idEmail_Store"));
                    /*message.setFileName(rs.getString("email_store.file_name"));*/
                    message.setSubject(AES256.decrypt(rs.getString("email_store.subject")));
                    message.setContent(AES256.decrypt(rs.getString("email_store.contant")));
                    message.setDate(rs.getString("email_store.send_date"));
                    message.setReciver(rs.getString("email_store.reciver"));
                    senderFullName = getSenderFullNameByCustemID(rs.getString("email_store.sender"));
                    message.setSender(senderFullName);
                    message.setSeen(rs.getInt("email_store.seen"));
                    message.setPerpose(rs.getString("email_store.perpose"));

                    String img = getSenderImg(rs.getString("email_store.sender"));
                    message.setSenderImg(img);

                    message.setFilePath(getPDFData(rs.getInt("email_store.idEmail_Store")));
                    message.setFilePathName(getPDFFileName(rs.getInt("email_store.idEmail_Store")));


                    aboutList.add(message);
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
    private List<String> getPDFFileName(int msgID) throws SQLException {
        List<String> fileNames = new ArrayList<>();
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        Blob file = null;
        byte[] filedata = null;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.pdf_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet


            while (rs.next()) {

                Integer messageid= rs.getInt("pdf_store.message_id");

                if(messageid==msgID) {
                    fileNames.add(AES256.decrypt(rs.getString("pdf_store.file_name")));
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
        return fileNames;
    }

    private List<String> getPDFData(int msgID) throws SQLException {
        List<String> filePaths = new ArrayList<>();
        ResultSet rs = null;
        Statement statment = null;
        PreparedStatement pstmt = null;
        Connection connection = null;
        Blob file = null;
        String filePath = null;
        byte[] filedata = null;
        try {
            connection = DBConnection.getConnection();
            String sql = " SELECT * FROM mydb.pdf_store ;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            // extract data from the ResultSet


            while (rs.next()) {

                Integer messageid= rs.getInt("pdf_store.message_id");

                if(messageid==msgID) {
                    filePaths.add(AES256.decrypt(rs.getString("pdf_store.file_path")));
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
        return filePaths;

    }

    public String getSenderFullNameByCustemID(String senderCustemID) throws SQLException, ClassNotFoundException {

        String senderFullName = null;

        if(senderCustemID.substring(0,1).equals("S")){
            senderFullName = getStudentFullNameByCustemID(senderCustemID);
        }else if(senderCustemID.substring(0,1).equals("T")){
            senderFullName = getTeacherFullNameByCustemID(senderCustemID);
        }else if(senderCustemID.substring(0,1).equals("D")){
            senderFullName = getDepartmentFullNameByCustemID(senderCustemID);
        }
        else{

        }
        return senderFullName;
    }

    private String getDepartmentFullNameByCustemID(String senderCustemID) throws SQLException {
        String senderFullName = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.head_of_department;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("head_of_department.custem_ID");
                if (senderCustemID.equals(wantedCustemID)) {

                    senderFullName =  rs.getString("head_of_department.name") +" "+ rs.getString("head_of_department.surname");

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
        return senderFullName;
    }

    public String getStudentFullNameByCustemID(String studentCustemID) throws SQLException, ClassNotFoundException {
        String senderFullName = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("student.custem_ID");
                if (studentCustemID.equals(wantedCustemID)) {

                    senderFullName =  rs.getString("student.name") +" "+ rs.getString("student.surname");

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
        return senderFullName;
    }
    public String getTeacherFullNameByCustemID(String studentCustemID) throws SQLException, ClassNotFoundException {
        String senderFullName = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("teacher.custem_ID");
                if (studentCustemID.equals(wantedCustemID)) {

                    senderFullName =  rs.getString("teacher.name") +" "+ rs.getString("teacher.surname");

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
        return senderFullName;
    }














    public String getSenderImg(String senderCustemID) throws SQLException, ClassNotFoundException {

        String senderImg = null;

        if(senderCustemID.substring(0,1).equals("S")){
            senderImg = getStudentImg(senderCustemID);
        }else if(senderCustemID.substring(0,1).equals("T")){
            senderImg = getTeacherImg(senderCustemID);
        }else if(senderCustemID.substring(0,1).equals("D")){
            senderImg = getDepartmentImg(senderCustemID);
        }
        else{

        }
        return senderImg;
    }

    private String getDepartmentImg(String senderCustemID) throws SQLException {
        String senderImg = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.head_of_department;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("head_of_department.custem_ID");
                if (senderCustemID.equals(wantedCustemID)) {

                    senderImg =  rs.getString("head_of_department.img");

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
        return senderImg;
    }

    public String getStudentImg(String studentCustemID) throws SQLException, ClassNotFoundException {
        String senderImg = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("student.custem_ID");
                if (studentCustemID.equals(wantedCustemID)) {

                    senderImg =  rs.getString("student.img");

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
        return senderImg;
    }
    public String getTeacherImg(String studentCustemID) throws SQLException, ClassNotFoundException {
        String senderImg = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("teacher.custem_ID");
                if (studentCustemID.equals(wantedCustemID)) {

                    senderImg =  rs.getString("teacher.img");

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
        return senderImg;
    }






















    public String getSenderRecoveryEmailByCustemID(String senderCustemID) throws SQLException, ClassNotFoundException {

        String senderRecoveryEmail = null;

        if(senderCustemID.substring(0,1).equals("S")){
            senderRecoveryEmail = getStudentRecoveryEmailByCustemID(senderCustemID);
        }else if(senderCustemID.substring(0,1).equals("T")){
            senderRecoveryEmail = getTeacherRecoveryEmailByCustemID(senderCustemID);
        }else if(senderCustemID.substring(0,1).equals("D")){
            senderRecoveryEmail = getDepartmentRecoveryEmailByCustemID(senderCustemID);
        }
        else{

        }
        return senderRecoveryEmail;
    }

    public String getStudentRecoveryEmailByCustemID(String studentCustemID) throws SQLException, ClassNotFoundException {
        String senderRecoveryEmail = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.student;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("student.custem_ID");
                if (studentCustemID.equals(wantedCustemID)) {

                    senderRecoveryEmail =  rs.getString("student.recover_email");

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
        return senderRecoveryEmail;
    }
    public String getTeacherRecoveryEmailByCustemID(String studentCustemID) throws SQLException, ClassNotFoundException {
        String senderRecoveryEmail = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.teacher;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("teacher.custem_ID");
                if (studentCustemID.equals(wantedCustemID)) {

                    senderRecoveryEmail =  rs.getString("teacher.recover_email");

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
        return senderRecoveryEmail;
    }
    public String getDepartmentRecoveryEmailByCustemID(String departmentCustemID) throws SQLException, ClassNotFoundException {
        String senderRecoveryEmail = null;


        ResultSet rs = null;
        Statement statment = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM mydb.head_of_department;";
            statment = connection.createStatement();
            rs = statment.executeQuery(sql);
            while (rs.next()) {
                String wantedCustemID = rs.getString("head_of_department.custem_ID");
                if (departmentCustemID.equals(wantedCustemID)) {

                    senderRecoveryEmail =  rs.getString("head_of_department.recover_email");

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
        return senderRecoveryEmail;
    }

    public void setPDF(String file_path, String file_name,Integer msgId) throws SQLException, ClassNotFoundException, IOException, ServletException {


        String sql = "INSERT INTO mydb.pdf_store (file_path,file_name,message_id) values (?,?,?);";
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = con
                    .prepareStatement(sql);


            preparedStatement.setString(1, file_path);
            preparedStatement.setString(2, file_name);
            preparedStatement.setInt(3,msgId);




                // fetches input stream of the upload file for the blob column


            // sends the statement to the database server
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception

        }
    }


    public Integer getMessageID(String reciverCustemID, String studentID, String subject,String contant,String send_date, String msgType) {

            ResultSet rs = null;
            Statement statment = null;
            PreparedStatement pstmt = null;
            Connection connection = null;
            Integer msgID = 0;

            try {

                connection = DBConnection.getConnection();
                String sql = "SELECT * FROM `mydb`.`email_store`;";
                statment = connection.createStatement();
                rs = statment.executeQuery(sql);
                // extract data from the ResultSet
                while (rs.next()) {
                    String reciver = rs.getString("email_store.reciver");
                    String sender = rs.getString("email_store.sender");
                    String msgDate = rs.getString("email_store.send_date");
                    String msgSubject = rs.getString("email_store.subject");
                    String msgContant= rs.getString("email_store.contant");
                    String type = rs.getString("email_store.perpose");

                    if(reciver.equals(reciverCustemID) && sender.equals(studentID)&& msgSubject.equals(subject) && msgDate.equals(send_date) && msgContant.equals(contant) && type.equals(msgType)) {
                        msgID =  rs.getInt("email_store.idEmail_Store");
                    }

                }



            } catch (SQLException | ClassNotFoundException e) {
                // process sql exception

            }
            return msgID;

    }

    public void deleteMessages(Integer messageID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "DELETE FROM `mydb`.`email_store` WHERE `idEmail_Store`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, messageID);
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
    public void deleteMessagesPDF(Integer messageID) throws SQLException, ClassNotFoundException, IOException, ServletException {



        Connection con = null;
        ResultSet rs = null;
        Connection connection = null;

        try {
            String sql = "DELETE FROM `mydb`.`pdf_store` WHERE `message_id`=?;";
            connection = DBConnection.getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setInt   (1, messageID);
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
}
