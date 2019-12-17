package TeacherServlet;

import Beans.Student;
import Beans.Teacher;
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

@WebServlet("/TeacherTeachers")
public class TeacherTeachers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherTeachers(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherTeachers(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void teacherTeachers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpTeacherTeachers(request,response);
    }

    private void setUpTeacherTeachers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        String teacherID = getTeacherSessionID(request);
        getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
        getTeacherMessageCount(request,Integer.parseInt(teacherID));
        getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));
        getTeacherFriends(request,response,Integer.parseInt(teacherID));
    }

    private void getTeacherFriends(HttpServletRequest request, HttpServletResponse response, int teacherID) throws SQLException, ServletException, IOException, ClassNotFoundException {
        TeacherDAO teacherDAO = new TeacherDAO();
        List<Integer> ids = teacherDAO.getTeacherGroupsID(teacherID);
        List<List<Integer>> teacherIds = new ArrayList<>();
        for(int i=0;i<ids.size();i++) {
            teacherIds.add(teacherDAO.getGroupsTeachersID(ids.get(i)));
        }
        List<Integer> tIDs = new ArrayList<>();
        for (int i=0;i<teacherIds.size();i++){
            for(int j=0;j<teacherIds.get(i).size();j++){
                if(teacherIds.get(i).get(j) != teacherID) {
                    tIDs.add(teacherIds.get(i).get(j));
                }
            }
        }
        List<Integer> newList = tIDs.stream()
                .distinct()
                .collect(Collectors.toList());
        List<Teacher> teacherList = new ArrayList<>();
        for (int i=0;i<newList.size();i++) {
            teacherList.add(teacherDAO.getTeacherTeachersPersonalInfo(newList.get(i)));
        }
        Comparator<Teacher> compareByName = Comparator.comparing(Teacher::getName);
        Collections.sort(teacherList, compareByName);
        request.setAttribute("teachersList",teacherList);
        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/teacher_teachers.jsp");
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
