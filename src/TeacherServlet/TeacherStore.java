package TeacherServlet;

import Beans.TeacherFiles;
import DAO.MailSendingDAO;
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

@WebServlet("/TeacherStore")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)
public class TeacherStore extends HttpServlet {
    private static final String SAVE_DIR = "teacherPDFStore";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            TeacherStore(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TeacherStore(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void TeacherStore(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpTeacherStore(request,response);

    }

    private void setUpTeacherStore(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        String process = request.getParameter("process");
        String teacherID = getTeacherSessionID(request);
        if(process.equals("upload-file")){
            uploadToTeacherStore(request,response,teacherID);
            setUpTeacherStoreInformation(request,response,teacherID);
        }else if(process.equals("delete")) {
            String selectedMessages[] = request.getParameterValues("checkbox");
            if(selectedMessages==null){
                setUpTeacherStoreInformation(request,response,teacherID);
            }else {
                TeacherDAO teacherDAO = new TeacherDAO();
                for (int i = 0; i < selectedMessages.length; i++) {
                    if (selectedMessages[i] != null) {
                        teacherDAO.deleteTeacherSelectedFiles(Integer.parseInt(selectedMessages[i]));
                    } else {
                    }
                }
                setUpTeacherStoreInformation(request,response,teacherID);
            }
        }else{
            setUpTeacherStoreInformation(request,response,teacherID);
        }
    }

    private void setUpTeacherStoreInformation(HttpServletRequest request, HttpServletResponse response, String teacherID) throws ServletException, IOException, SQLException, ClassNotFoundException {
        getTeacherStoreFiles(request,response,teacherID);
        getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
        getTeacherMessageCount(request,Integer.parseInt(teacherID));
        getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/teacher_store.jsp");
        rd.forward(request, response);
    }

    private void getTeacherStoreFiles(HttpServletRequest request, HttpServletResponse response, String teacherID) throws SQLException, ClassNotFoundException {

        TeacherDAO teacherDAO = new TeacherDAO();
        List<TeacherFiles> teacherFiles = teacherDAO.getTeacherPDFFiles(Integer.parseInt(teacherID));
        request.setAttribute("teacherFiles",teacherFiles);
    }

    private void uploadToTeacherStore(HttpServletRequest request, HttpServletResponse response,String teacherID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        LocalDateTime today = LocalDateTime.now();
        String subject = request.getParameter("subject");
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

        List<Part> parts = (List<Part>) request.getParts();
        for (Part part : parts) {
            String fileName = getFileName(part);
            if(fileName!=null) {

                part.write(savePath + File.separator + fileName);

              //  mailSendingDAO.setPDF(AES256.encrypt(savePath + File.separator + fileName),AES256.encrypt(fileName),msgID);
                TeacherDAO teacherDAO = new TeacherDAO();
                teacherDAO.setFileToTeacherStore(AES256.encrypt(savePath + File.separator + fileName),AES256.encrypt(fileName),subject,Integer.parseInt(teacherID));
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
    private static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
