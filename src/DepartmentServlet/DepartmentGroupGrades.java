package DepartmentServlet;

import Beans.Student;
import Beans.StudentGrades;
import DAO.DepartmentDAO;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/DepartmentGroupGrades")
public class DepartmentGroupGrades extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentGroupGrades(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentGroupGrades(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void departmentGroupGrades(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        checkSessionIsAlive(request,response);
        String departmentID = getDepartmentSessionID(request,response);
        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        checkProcess(request,response);
    }

    private void checkProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String process = request.getParameter("process");
        String groupID =  request.getParameter("groupID");
        RequestDispatcher rd = null;
        if(process.equals("check")){
            getStudentsGrades(request,groupID);
            rd = request.getRequestDispatcher("/Account_Pages/department_group_grades.jsp");
            rd.forward(request,response);
        }
    }

    private void getStudentsGrades(HttpServletRequest request,String groupID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        List<Student> students = studentDAO.getStudentsByGroupID(Integer.parseInt(groupID));
        Map<String, List<StudentGrades>> map = new HashMap<>();
        for(int i=0;i<students.size();i++) {
            List<StudentGrades> studentList = studentDAO.getStudentGrades(students.get(i).getId());

            String fullName = students.get(i).getName()+" "+students.get(i).getSurname();
            map.put(fullName,calculatGrades(studentList));
        }
        request.setAttribute("groupID",groupID);
        request.setAttribute("studentsGrades",map);
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
    private List<StudentGrades> calculatGrades(List<StudentGrades> studentList ){
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
        return studentList;
    }
}
