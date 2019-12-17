package StudentServlet;

import Beans.Student;
import Beans.Teacher;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/StudentGroup")
public class StudentGroup extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentGroup(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentGroup(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void StudentGroup(HttpServletRequest request,HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        checkSessionIsAlive(request,response);
        setUpStudentGroupEnvironment(request,response);

    }
    private void setUpStudentGroupEnvironment(HttpServletRequest request,HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        RequestDispatcher rd = null;
        String studentID = getStudentSessionID(request);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentGroupFriends(request,studentID);
        getStudentMessageCount(request,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,Integer.parseInt(studentID));
        rd = request.getRequestDispatcher("/Account_Pages/student_Groupf.jsp");
        rd.forward(request,response);
    }

    private void getStudentGroupFriends(HttpServletRequest request, String studentID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.getStudentGroupFriends(Integer.parseInt(studentID));
        Comparator<Student> compareByName = Comparator.comparing(Student::getName);
        Collections.sort(studentList, compareByName);
        String groupName = studentDAO.getStudentGroupName(Integer.parseInt(studentID));
        request.setAttribute("studentGroupFriends",studentList);
        request.setAttribute("studentGroupName",groupName);
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
