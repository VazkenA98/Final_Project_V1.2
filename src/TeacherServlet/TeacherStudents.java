package TeacherServlet;

import Beans.Group;
import Beans.Student;
import Beans.TeacherGrades;
import DAO.MailSendingDAO;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/TeacherStudents")
public class TeacherStudents extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherStudents(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherStudents(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void teacherStudents(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpTeacherStudentsGroup(request,response);

    }

    private void setUpTeacherStudentsGroup(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        String teacherID = getTeacherSessionID(request);
        String groupID = request.getParameter("groupID");
        getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
        getTeacherMessageCount(request,Integer.parseInt(teacherID));
        getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));
        getTeacherStudents(request,response,Integer.parseInt(teacherID),Integer.parseInt(groupID));

    }
    private void getTeacherStudents(HttpServletRequest request, HttpServletResponse response, int teacherID,int groupID) throws SQLException, ServletException, IOException {
        TeacherDAO teacherDAO =  new TeacherDAO();
        List<Student> teacherStudents = teacherDAO.getTeacherGroupStudents(groupID);
        Comparator<Student> compareByName = Comparator.comparing(Student::getName);
        Collections.sort(teacherStudents, compareByName);
        request.setAttribute("teacherStudents",teacherStudents);
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/teacher_students.jsp");
        rd.forward(request,response);
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
}
