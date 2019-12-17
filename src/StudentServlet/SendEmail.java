package StudentServlet;

import DAO.MailSendingDAO;
import DAO.StudentDAO;
import Utils.AES256;
import Utils.Security;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/SendEmail")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)

public class SendEmail extends HttpServlet {
    private static final String SAVE_DIR = "uploadFilesStore";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            sendEmail(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            sendEmail(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request, response);
        setUpSendingEmailEnvironment(request,response);

    }

    private void setUpSendingEmailEnvironment(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String studentID = getStudentSessionID(request, response);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentMessageCount(request,response,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,response,Integer.parseInt(studentID));
        checkProcess(request,response,studentID);
    }

    private void checkProcess(HttpServletRequest request, HttpServletResponse response,String studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        String process = request.getParameter("process");
        RequestDispatcher rd = null;
        String reciverEmail = request.getParameter("reciverEmail");
        request.setAttribute("sendTo", reciverEmail);
        String reciverCustemID =  request.getParameter("reciverCustemID");
        request.setAttribute("reciverCustemID", reciverCustemID);
        String msgType = request.getParameter("msgType");
        request.setAttribute("msgType",msgType);

        if (process.equals("check")) {
            rd = request.getRequestDispatcher("/Account_Pages/student_messege_send.jsp");
            rd.forward(request, response);
        }else if(process.equals("reply")) {

            MailSendingDAO mailSendingDAO = new MailSendingDAO();
            String messageID = request.getParameter("messageID");
            String senderCustemID = mailSendingDAO.getMessageSenderIDByMessageID(Integer.parseInt(messageID));
            request.setAttribute("reciverCustemID", senderCustemID);
            String senderEmail = mailSendingDAO.getSenderRecoveryEmailByCustemID(senderCustemID);
            request.setAttribute("sendTo", senderEmail);
            rd = request.getRequestDispatcher("/Account_Pages/student_messege_send.jsp");
            rd.forward(request, response);

        } else {

            String subject = request.getParameter("subject");
            String content = request.getParameter("content");
            MailSendingDAO mailSendingDAO = new MailSendingDAO();
            Integer msgID =  mailSendingDAO.sendMessage(reciverCustemID, studentID, AES256.encrypt(subject), AES256.encrypt(content),msgType);
           if(checkFileExicteance(request) == true) {
               handleFileUploadRequest(request, response, msgID);
           }
            request.setAttribute("errorMessage","Sent Successfully");
            rd = request.getRequestDispatcher("/Account_Pages/student_messege_send.jsp");
            rd.forward(request, response);

        }
    }



    public void handleFileUploadRequest(HttpServletRequest request, HttpServletResponse response,Integer msgID) throws ServletException, IOException, SQLException, ClassNotFoundException {

        LocalDateTime today = LocalDateTime.now();
        ZoneId id = ZoneId.of("Europe/Paris");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(today, id);      //That's how you add timezone to date
        String formattedDateTime = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH-mm")
                .format(zonedDateTime);
        // gets absolute path of the web application
        String appPath = request.getServletContext().getRealPath("WEB-INF");
        // constructs path of the directory to save uploaded file
        String stringWithoutSpaces = formattedDateTime.replaceAll("\\s+", "-");
        String savePath = appPath + File.separator + SAVE_DIR + File.separator + stringWithoutSpaces;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        MailSendingDAO mailSendingDAO = null;
        List<Part> parts = (List<Part>) request.getParts();
        for (Part part : parts) {
            String fileName = getFileName(part);
            if(fileName!=null) {

                part.write(savePath + File.separator + fileName);
                mailSendingDAO = new MailSendingDAO();
                mailSendingDAO.setPDF(AES256.encrypt(savePath + File.separator + fileName),AES256.encrypt(fileName),msgID);
            }
        }

    }

    private void getStudentMessageCount(HttpServletRequest request, HttpServletResponse response,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyMessageCount(studentID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private void getStudentHomeWorkCount(HttpServletRequest request, HttpServletResponse response,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyHomwWorkCount(studentID);
        request.setAttribute("homeWorkCount",messegeCount.toString());

    }
    private void getStudentProfileImgBySession(HttpServletRequest request, Integer studentID) throws SQLException, ClassNotFoundException {

        StudentDAO studentDAO = new StudentDAO();
        String profileImg = studentDAO.getStudentProfileImgIDByStudentID(studentID);
        request.setAttribute("profileImg",profileImg);
    }

    private static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


    private String getStudentSessionID(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String studentFullName = request.getParameter("studentName");
        request.setAttribute("studentFullName", studentFullName);
        return Security.sessionDecrypt(request.getParameter("id"));

    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }

    private  boolean checkFileExicteance(HttpServletRequest request) throws IOException, ServletException {
        boolean flag = false;
        List<Part> parts = (List<Part>) request.getParts();
        for (Part part : parts) {
            String fileName = getFileName(part);
            if(fileName!=null ) {
                if(!fileName.equals("")) {
                    flag = true;
                    break;
                }
            }
        }
        return  flag;
    }

}
