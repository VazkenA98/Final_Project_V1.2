package Servers;

import DAO.DepartmentDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Utils.Security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private String userId=null;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Logout(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Logout(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void Logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request, response);
        checkUserTypeToLogout(request, response);

    }

    private void checkUserTypeToLogout(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        userId = getSessionID(request);
        if (type.equals("student")) {
            logoutStudent(Integer.parseInt(getSessionID(request)));
        } else if (type.equals("teacher")) {
            logoutTeacher(Integer.parseInt(getSessionID(request)));
        } else if (type.equals("department")) {
            logoutDepartment(Integer.parseInt(getSessionID(request)));
        } else {

        }

        HttpSession session = request.getSession(false);
        session.invalidate();
        userId = null;

        if (!request.isRequestedSessionIdValid()) {
            response.reset();

            response.setHeader("Cache-Control", "private,no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "private,no-store");
            response.setHeader("Cache-Control", "must-revalidate");
            response.setDateHeader("Expires", 0);
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }

    private void logoutDepartment(int departmentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentDAO.setDepartmentOffline(departmentID);
    }

    private void logoutStudent(Integer studentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();

        studentDAO.setStudentOffline(studentID);

    }

    private void logoutTeacher(Integer teacherID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        TeacherDAO teacherDAO = new TeacherDAO();
        teacherDAO.setTeacherOffline(teacherID);

    }

    private String getSessionID(HttpServletRequest request) {

        return Security.sessionDecrypt(request.getParameter("id"));

    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }
}
