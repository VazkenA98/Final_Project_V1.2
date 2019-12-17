package TeacherServlet;

import DAO.MailSendingDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Utils.AES256;
import Utils.Security;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/TeacherGroupMailing")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)

public class TeacherGroupMailing extends HttpServlet {
    private static final String SAVE_DIR = "uploadFilesStore";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            teacherGroupMailing(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherGroupMailing(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void teacherGroupMailing(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request, response);
        setUpSendingEmailEnvironment(request,response);

    }

    private void setUpSendingEmailEnvironment(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String teacherID = getTeacherSessionID(request);
        getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
        getTeacherMessageCount(request,Integer.parseInt(teacherID));
        getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));
        checkProcess(request,response,teacherID);
    }
    private void checkProcess(HttpServletRequest request, HttpServletResponse response,String teacherID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        String process = request.getParameter("process");
        RequestDispatcher rd = null;
        String groupID = request.getParameter("groupID");
        request.setAttribute("groupID",groupID);
        String groupName = request.getParameter("groupName");
        request.setAttribute("groupName",groupName);
        String msgType = request.getParameter("msgType");
        request.setAttribute("msgType",msgType);

        if (process.equals("check")) {
            rd = request.getRequestDispatcher("/Account_Pages/teacher_group_messege_send.jsp");
            rd.forward(request, response);
        } else {

            String subject = request.getParameter("subject");
            String content = request.getParameter("content");
            MailSendingDAO mailSendingDAO = new MailSendingDAO();
            StudentDAO studentDAO =  new StudentDAO();
            List<String> groupStudentsID = studentDAO.getStudentCustemIDByGroupID(Integer.parseInt(groupID));
            for (int i = 0;i<groupStudentsID.size();i++) {
                Integer msgID = mailSendingDAO.teacherSendMessage(groupStudentsID.get(i), teacherID, AES256.encrypt(subject), AES256.encrypt(content), msgType);
                if(checkFileExicteance(request) == true) {
                    handleFileUploadRequest(request, response, msgID);
                }
            }

            request.setAttribute("errorMessage","Sent Successfully");
            rd = request.getRequestDispatcher("/Account_Pages/teacher_group_messege_send.jsp");
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
    private String getTeacherSessionID(HttpServletRequest request) {
        String teacherFullName = request.getParameter("teacherName");
        request.setAttribute("teacherFullName", teacherFullName);
        return Security.sessionDecrypt(request.getParameter("id"));

    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }
    private void getTeacherMessageCount(HttpServletRequest request,int teacherID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getTeacherMessageCount(teacherID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private void getTeacherHomeWorkCount(HttpServletRequest request,int teacherID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getTeacherHomwWorkCount(teacherID);
        request.setAttribute("homeWorkCount",messegeCount.toString());

    }
    private void getTeacherProfileImgBySession(HttpServletRequest request, Integer teacherID) throws SQLException, ClassNotFoundException {
        TeacherDAO teacherDAO = new TeacherDAO();
        String profileImg = teacherDAO.getTeacherProfileImgIDByTeacherID(teacherID);
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
