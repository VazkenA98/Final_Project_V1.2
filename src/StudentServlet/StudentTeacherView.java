package StudentServlet;

import Beans.TeacherFiles;
import DAO.MailSendingDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Utils.Security;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/StudentTeacherView")
public class StudentTeacherView extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            setUpTeacherStore(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            setUpTeacherStore(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setUpTeacherStore(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException, ServletException {
        checkSessionIsAlive(request,response);
        setUpStoreEnvironment(request,response);
    }

    private void setUpStoreEnvironment(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        String studentID = getStudentSessionID(request);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentMessageCount(request,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,Integer.parseInt(studentID));
        getTeacherStoreFiles(request,response);
    }

    private void getTeacherStoreFiles(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String teacherID = request.getParameter("teacherID");
        TeacherDAO teacherDAO = new TeacherDAO();
        List<TeacherFiles> teacherFiles = teacherDAO.getTeacherPDFFiles(Integer.parseInt(teacherID));
        request.setAttribute("teacherFiles",teacherFiles);
        String teacherName =  request.getParameter("teacherName");
        request.setAttribute("teacherFullName",teacherName);
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/student_teacher_store.jsp");
        rd.forward(request,response);

    }

    private String getStudentSessionID(HttpServletRequest request) {
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
    private void getStudentMessageCount(HttpServletRequest request,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyMessageCount(studentID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private void getStudentHomeWorkCount(HttpServletRequest request,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyHomwWorkCount(studentID);
        request.setAttribute("homeWorkCount",messegeCount.toString());

    }
    private void getStudentProfileImgBySession(HttpServletRequest request, Integer studentID) throws SQLException, ClassNotFoundException {

        StudentDAO studentDAO = new StudentDAO();
        String profileImg = studentDAO.getStudentProfileImgIDByStudentID(studentID);
        request.setAttribute("profileImg",profileImg);
    }
}
