package DepartmentServlet;

import Beans.Department;
import Beans.Student;
import Beans.Teacher;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/DepartmentTeachers")
public class DepartmentTeachers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentTeachers(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentTeachers(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentTeachers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpDepartmentTeachersEnviroment(request,response);
    }

    private void setUpDepartmentTeachersEnviroment(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String departmentID = getDepartmentSessionID(request,response);
        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        getDepartmentTeachers(request,response,departmentID);
    }

    private void getDepartmentTeachers(HttpServletRequest request, HttpServletResponse response, String departmentID) throws SQLException, ClassNotFoundException, ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        List<Integer> groupIDs = departmentDAO.getDepartmentGroupIDs(Integer.parseInt(departmentID));
        List<List<Integer>> teacherIDs = new ArrayList<>();
        for(int i=0;i<groupIDs.size();i++){
            teacherIDs.add(departmentDAO.getTeacherIDByGroupID(groupIDs.get(i)));
        }
        List<Integer> listIDs = new ArrayList<>();
        for(int i=0;i<teacherIDs.size();i++){
            for(int j=0;j<teacherIDs.get(i).size();j++){
                if(!teacherIDs.get(i).isEmpty()) {
                    listIDs.add(teacherIDs.get(i).get(j));
                }
            }
        }
        List<Integer> newList = listIDs.stream()
                .distinct()
                .collect(Collectors.toList());

        List<Teacher> teachersList = getTeachers(newList);
        Comparator<Teacher> compareByName = Comparator.comparing(Teacher::getName);
        Collections.sort(teachersList, compareByName);
        request.setAttribute("departmentTeachers",teachersList);
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/department_teachers.jsp");
        rd.forward(request,response);


    }
    private List<Teacher> getTeachers(List<Integer> list) throws SQLException, ClassNotFoundException {
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Teacher> aboutList = new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            aboutList.add(teacherDAO.getTeacherByID(list.get(i)));
        }
        return aboutList;
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
