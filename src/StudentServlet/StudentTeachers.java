package StudentServlet;

import Beans.Group;
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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/StudentTeachers")
public class StudentTeachers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentTeachers(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentTeachers(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void StudentTeachers(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException, ServletException {
        checkSessionIsAlive(request,response);
        setUpStudentTeachers(request,response);
    }
    private void setUpStudentTeachers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        RequestDispatcher rd = null;
        String studentID = getStudentSessionID(request,response);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentTeachers(request,studentID);
        getStudentMessageCount(request,response,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,response,Integer.parseInt(studentID));
        rd = request.getRequestDispatcher("/Account_Pages/student_teachers.jsp");
        rd.forward(request,response);
    }

    private void getStudentTeachers(HttpServletRequest request, String studentID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        List<Teacher> studentList = studentDAO.getStudentTeachers(Integer.parseInt(studentID));
        List<Teacher> teachers = processTeachers(studentList);
        System.out.println(studentList);
        Set<String> nameSet = new HashSet<>();
        List<Teacher> groupDistinctByName = teachers.stream()
                .filter(e -> nameSet.add(e.getCustem_ID()))
                .collect(Collectors.toList());
        Comparator<Teacher> compareByName = Comparator.comparing(Teacher::getName);
        Collections.sort(groupDistinctByName, compareByName);
        request.setAttribute("studentTeachers",groupDistinctByName);
    }

    private List<Teacher> processTeachers(List<Teacher> studentList) {
        for(int i=0;i<studentList.size();i++){
            for(int j=i+1;j<studentList.size();j++){
                if(studentList.get(i).getId()==studentList.get(j).getId()){
                    studentList.get(i).setSbjectTeachesToGroup(studentList.get(i).getSbjectTeachesToGroup()+"-"+studentList.get(j).getSbjectTeachesToGroup());
                }
            }
        }
        return studentList;
    }

    private String getStudentSessionID(HttpServletRequest request, HttpServletResponse response) {
        String studentFullName = request.getParameter("studentName");
        request.setAttribute("studentFullName", studentFullName);
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
