package StudentServlet;

import Beans.Student;
import DAO.MailSendingDAO;
import DAO.StudentDAO;
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

@WebServlet("/StudentPersonalInfo")
public class StudentPersonalInfo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentPersonalInfo(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentPersonalInfo(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void StudentPersonalInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException, ServletException {
        checkSessionIsAlive(request,response);
        setUpStudentPerofileEnvironment(request,response);
    }

    private void setUpStudentPerofileEnvironment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        RequestDispatcher rd = null;
        String studentID = getStudentSessionID(request,response);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentPersonalInformation(request,studentID);
        getStudentMessageCount(request,response,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,response,Integer.parseInt(studentID));
        rd = request.getRequestDispatcher("/Account_Pages/student_profile.jsp");
        rd.forward(request,response);
    }
    private void getStudentPersonalInformation(HttpServletRequest request,String studentID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.getStudentPersonalInfo(Integer.parseInt(studentID));
        request.setAttribute("studentInfo",studentList);
    }

    private String getStudentSessionID(HttpServletRequest request, HttpServletResponse response) {
        String studentFullName = request.getParameter("studentName");
        request.setAttribute("studentFullName", studentFullName);
        String s = request.getParameter("id");
        return Security.sessionDecrypt(request.getParameter("id"));

    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/Account_Pages/login.jsp");
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

}
