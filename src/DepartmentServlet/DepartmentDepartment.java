package DepartmentServlet;

import Beans.Department;
import Beans.Teacher;
import DAO.DepartmentDAO;
import DAO.MailSendingDAO;
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

@WebServlet("/DepartmentDepartment")
public class DepartmentDepartment extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            departmentDepartment(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentDepartment(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentDepartment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpDepartmentFriendsEnvironment(request,response);
    }

    private void setUpDepartmentFriendsEnvironment(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {

        String departmentID = getDepartmentSessionID(request,response);
        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        getDepartmentFriends(request,response,departmentID);
    }

    private void getDepartmentFriends(HttpServletRequest request,HttpServletResponse response, String departmentID) throws SQLException, ClassNotFoundException, ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        List<Department> departmentList = departmentDAO.getAllDepartments();
        for(int i =0;i<departmentList.size();i++){
            if(departmentList.get(i).getId() == Integer.parseInt(departmentID)){
                departmentList.remove(i);
            }
        }
        System.out.println(departmentList);
        Comparator<Department> compareByName = Comparator.comparing(Department::getName);
        Collections.sort(departmentList, compareByName);
        request.setAttribute("departmentsList",departmentList);
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/department_department.jsp");
        rd.forward(request,response);
    }

    private void getDepartmentProfileImgBySession(HttpServletRequest request, Integer departmentID) throws SQLException, ClassNotFoundException {

        DepartmentDAO departmentDAO = new DepartmentDAO();
        String profileImg = departmentDAO.getTeacherProfileImgIDByDepartmentID(departmentID);
        request.setAttribute("profileImg",profileImg);
    }

    private void getDepartmentMessageCount(HttpServletRequest request, HttpServletResponse response,int departmentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getDepartmentMessageCount(departmentID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private String getDepartmentSessionID(HttpServletRequest request, HttpServletResponse response) {
        String departmentFullName = request.getParameter("departmentName");
        request.setAttribute("departmentFullName", departmentFullName);
        return Security.sessionDecrypt(request.getParameter("id"));
    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }
}
