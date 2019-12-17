package StudentServlet;

import Beans.StudentGrades;
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
import java.util.List;


@WebServlet("/StudentGradesDisplay")
public class StudentGradesDisplay extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            studentGradesDisplay(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            studentGradesDisplay(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void studentGradesDisplay(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException, ServletException {
        checkSessionIsAlive(request,response);
        setUpStudentGradesEnvironment(request,response);
    }

    private void setUpStudentGradesEnvironment(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        RequestDispatcher rd = null;
        String studentID = getStudentSessionID(request,response);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentGrades(request,studentID);
        getStudentMessageCount(request,response,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,response,Integer.parseInt(studentID));
        rd = request.getRequestDispatcher("/Account_Pages/student_grades.jsp");
        rd.forward(request,response);

    }
    private void getStudentGrades(HttpServletRequest request,String studentID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        List<StudentGrades> studentList = studentDAO.getStudentGrades(Integer.parseInt(studentID));
        calculatGrades(request,studentList);
    }

    private String getStudentSessionID(HttpServletRequest request, HttpServletResponse response) {
        String studentFullName = request.getParameter("studentName");
        request.setAttribute("studentFullName", studentFullName);
        String profileImg =  request.getParameter("profileImg");
        request.setAttribute("profileImg",profileImg);
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
    private void getStudentMessageCount(HttpServletRequest request, HttpServletResponse response,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyMessageCount(studentID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private void getStudentProfileImgBySession(HttpServletRequest request, Integer studentID) throws SQLException, ClassNotFoundException {

        StudentDAO studentDAO = new StudentDAO();
        String profileImg = studentDAO.getStudentProfileImgIDByStudentID(studentID);
        request.setAttribute("profileImg",profileImg);
    }
    private void getStudentHomeWorkCount(HttpServletRequest request, HttpServletResponse response,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyHomwWorkCount(studentID);
        request.setAttribute("homeWorkCount",messegeCount.toString());

    }
}
