package TeacherServlet;

import Beans.Student;
import Beans.StudentGrades;
import Beans.Teacher;
import Beans.TeacherGrades;
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
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/TeacherStudentsGrades")
public class TeacherStudentsGrades extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherStudentsGrades(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherStudentsGrades(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void teacherStudentsGrades(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpTeacherStudentsGrades(request,response);
    }

    private void setUpTeacherStudentsGrades(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {

        String teacherID = getTeacherSessionID(request);
        String process = request.getParameter("process");
        if(process.equals("save")){

            updateStudentGrades(request,response);
        }
        String groupID = request.getParameter("groupID");
        getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
        getTeacherMessageCount(request,Integer.parseInt(teacherID));
        getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));

        setUpStudentsGradeTable(request,response,Integer.parseInt(teacherID),Integer.parseInt(groupID));

    }

    private void updateStudentGrades(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {

        String order = request.getParameter("order");
        String listSize = request.getParameter("listSize");

        List<String> gradeID = new ArrayList<>();
        List<String> exam_1 = new ArrayList<>();
        List<String> exam_2 = new ArrayList<>();
        List<String> final_exam = new ArrayList<>();
        List<String> date = new ArrayList<>();
        for(int i=0;i<Integer.parseInt(listSize);i++){
            if(request.getParameter("gradeID"+order+""+i)!=null) {
                gradeID.add(request.getParameter("gradeID" + order + "" + i));
            }
            if(request.getParameter("exam_1"+order+""+i)!=null) {
                exam_1.add(request.getParameter("exam_1" + order + "" + i));
            }
            if(request.getParameter("exam_2"+order+""+i)!=null) {
                exam_2.add(request.getParameter("exam_2" + order + "" + i));
            }
            if(request.getParameter("finalExam"+order+""+i)!=null) {
                final_exam.add(request.getParameter("finalExam" + order + "" + i));
            }
            if(request.getParameter("semesterYear"+order+""+i)!=null) {
                date.add(request.getParameter("semesterYear" + order + "" + i));
            }
        }
        TeacherDAO teacherDAO = new TeacherDAO();
        for(int i=0;i<gradeID.size();i++){
            teacherDAO.updateStudentGrades(exam_1.get(i),exam_2.get(i),final_exam.get(i),date.get(i),Integer.parseInt(gradeID.get(i)));
        }

    }

    private void setUpStudentsGradeTable(HttpServletRequest request, HttpServletResponse response, int teacherID,int groupID) throws SQLException, ServletException, IOException, ClassNotFoundException {
        TeacherDAO teacherDAO =  new TeacherDAO();
        request.setAttribute("groupID",groupID);
        List<Student> teacherStudents = teacherDAO.getTeacherGroupStudents(groupID);
        request.setAttribute("teacherStudents",teacherStudents);
        List<Integer> subjectIds = teacherDAO.getTeacherGroupSubject(groupID,teacherID);
        List<Integer> semesters = new ArrayList<>();
        for(int i=0;i<subjectIds.size();i++){
            semesters.add(teacherDAO.getTeacherGroupSubjectSemesters(groupID,teacherID,subjectIds.get(i)));

        }

        List<List<TeacherGrades>> grades = new ArrayList<>();
        StudentDAO studentDAO = new StudentDAO();
        for(int i=0;i<subjectIds.size();i++){
            for(int j=0;j<teacherStudents.size();j++){
                grades.add(studentDAO.getStudentGradesBySubjectAndID(teacherStudents.get(j).getId(),subjectIds.get(i)));
            }
        }
        List<String> subjectName = new ArrayList<>();
        for (int i=0;i<grades.size();i++){
            for(int j=0;j<grades.get(i).size();j++){
                subjectName.add(grades.get(i).get(j).getSubject());
            }
        }
        List<String> subjectlistWithoutDuplicates = subjectName.stream().distinct().collect(Collectors.toList());

        List<TeacherGrades> studentGrades =  new ArrayList<>();
        for (int i=0;i<grades.size();i++){
            for(int j=0;j<grades.get(i).size();j++) {

                    studentGrades.add(grades.get(i).get(j));
            }
            }


        Comparator<TeacherGrades> compareByName = Comparator.comparing(TeacherGrades::getStudentName);

        Collections.sort(studentGrades, compareByName);

        System.out.println("//////////////////////////////");
        System.out.println(subjectlistWithoutDuplicates);
        System.out.println(studentGrades);

        request.setAttribute("subjects",subjectlistWithoutDuplicates);
        request.setAttribute("studentGrades",studentGrades);

        RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/teacher_student_grades.jsp");
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
