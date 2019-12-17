package DepartmentServlet;

import Beans.Group;
import DAO.DepartmentDAO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/DepartmentGroups")
public class DepartmentGroups extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentGroups(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentGroups(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentGroups(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpDepartmentGroupsEnviroment(request,response);
    }

    private void setUpDepartmentGroupsEnviroment(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        String departmentID = getDepartmentSessionID(request,response);
        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        getDepartmentTeachers(request,response,departmentID);
    }

    private void getDepartmentTeachers(HttpServletRequest request, HttpServletResponse response, String departmentID) throws SQLException, ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        List<Group> groupList = departmentDAO.getGroupsByDepartmentID(Integer.parseInt(departmentID));
        Set<String> nameSet = new HashSet<>();
        List<Group> groupDistinctByName = groupList.stream()
                .filter(e -> nameSet.add(e.getName()))
                .collect(Collectors.toList());
        request.setAttribute("departmentGroups",groupDistinctByName);
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/department_groups.jsp");
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
