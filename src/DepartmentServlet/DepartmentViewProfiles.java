package DepartmentServlet;

import Beans.Student;
import Beans.StudentGrades;
import Beans.Teacher;
import DAO.DepartmentDAO;
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

@WebServlet("/DepartmentViewProfiles")
public class DepartmentViewProfiles extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentViewProfiles(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentViewProfiles(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentViewProfiles(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException, ServletException {
        checkSessionIsAlive(request,response);
        String departmentID = getDepartmentSessionID(request,response);
        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        checkUser(request,response);
    }

    private void checkUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String userType = request.getParameter("userType");
        String userCustemID = request.getParameter("reciverCustemID");
        RequestDispatcher rd = null;
        if(userType.equals("student")){
            getStudentInfo(request,response,userCustemID);
            request.setAttribute("userType","student");
            rd = request.getRequestDispatcher("/Account_Pages/department_ViewProfiles.jsp");
            rd.forward(request,response);
        }else if(userType.equals("teacher")){
            getTeacherInfo(request,response,userCustemID);
            request.setAttribute("userType","teacher");
            rd = request.getRequestDispatcher("/Account_Pages/department_ViewProfiles.jsp");
            rd.forward(request,response);
        }else{

        }
    }

    private void getTeacherInfo(HttpServletRequest request, HttpServletResponse response, String userCustemID) throws SQLException, ClassNotFoundException {
        TeacherDAO teacherDAO = new TeacherDAO();
        Integer teacherID = teacherDAO.getTeacherIDByCustemID(userCustemID);
        List<Teacher> teacherList = teacherDAO.getTeacherPersonalInfo(teacherID);
        request.setAttribute("teacherInfo",teacherList);
    }

    private void getStudentInfo(HttpServletRequest request, HttpServletResponse response,String userCustemID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        Integer studentID = studentDAO.getStudentIDByCustemID(userCustemID);
        getStudentProfileInfo(request,response,studentID);
        getStudentGrades(request,response,studentID);
    }

    private void getStudentGrades(HttpServletRequest request, HttpServletResponse response, Integer studentID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        List<StudentGrades> studentList = studentDAO.getStudentGrades(studentID);
        calculatGrades(request,studentList);
    }

    private void getStudentProfileInfo(HttpServletRequest request, HttpServletResponse response, int studentID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.getStudentPersonalInfo(studentID);
        request.setAttribute("studentInfo",studentList);
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
    private void calculatGrades(HttpServletRequest request,List<StudentGrades> studentList ){
        for(int i=0;i<studentList.size();i++){
            Integer total = Integer.parseInt(studentList.get(i).getExam_1())+Integer.parseInt(studentList.get(i).getExam_2())+Integer.parseInt(studentList.get(i).getFinalExam());
            studentList.get(i).setTotalScore(total.toString());
            if(total>=95){
                studentList.get(i).setScoreSymbol("A+");
            }else if(total>=87 && total<=94){
                studentList.get(i).setScoreSymbol("A");
            }else if(total>=81 && total<=86){
                studentList.get(i).setScoreSymbol("A-");
            }else if(total>=75 && total<=80){
                studentList.get(i).setScoreSymbol("B+");
            }else if(total>=67 && total<=74){
                studentList.get(i).setScoreSymbol("B");
            } else if(total>=61 && total<=66){
                studentList.get(i).setScoreSymbol("B-");
            } else if(total>=55 && total<=60){
                studentList.get(i).setScoreSymbol("C+");
            } else if(total>=46 && total<=54){
                studentList.get(i).setScoreSymbol("C");
            } else if(total>=40 && total<=45){
                studentList.get(i).setScoreSymbol("C-");
            }else{
                studentList.get(i).setScoreSymbol("F");
            }
        }
        request.setAttribute("studentGrades",studentList);
    }
}
