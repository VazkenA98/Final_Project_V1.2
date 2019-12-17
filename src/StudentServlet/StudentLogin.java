package StudentServlet;

import Beans.Group;
import Beans.Student;
import DAO.MailSendingDAO;
import DAO.StudentDAO;
import Utils.SHA512;
import Utils.Security;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@WebServlet("/Account_Pages/StudentLogin")
public class StudentLogin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentLogin(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StudentLogin(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void StudentLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {


        String process =  request.getParameter("process");
        if(process.equals("afterLogin")){
            checkSessionIsAlive(request,response);
            String studentID = getStudentSessionID(request,response);
            setUpStudentEnvironmentWhileOnline(request,response,Integer.parseInt(studentID));
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/student_dashboard.jsp");
            rd.forward(request,response);
        }else {
            String email = (String) request.getAttribute("loginEmail");
            String id = (String) request.getAttribute("loginCustemID");
            String password = (String) request.getAttribute("loginPassword");
            getAllStudents(request, response, email, id, password);
        }

    }
    private void getAllStudents(HttpServletRequest request, HttpServletResponse response,String email, String custem_ID, String password) throws SQLException, ClassNotFoundException, IOException, ServletException {
        Student student = new Student();
        String studentSessionFullName = null;
        StudentDAO studentDAO = new StudentDAO();
        Integer requestedID = studentDAO.getStudentID(email,custem_ID, SHA512.encryptPassword(password));
        List<Student> studentList = studentDAO.getAllStudents();
        for (int i = 0; i <studentList.size() ; i++) {
            if (studentList.get(i).getId() == requestedID) {
                Integer studentId = studentList.get(i).getId();
                String name = studentList.get(i).getName();
                String surname = studentList.get(i).getSurname();
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(600);
                session.setAttribute("studentSessionID", Security.sessionEncrypt(studentId.toString()));
                studentSessionFullName = name + " " + surname;
                request.setAttribute("studentFullName", studentSessionFullName);
                student = studentList.get(i);
            } else {
                continue;
            }
        }
            checkStudentExistence(request,response,student);


    }
    private void checkStudentExistence(HttpServletRequest request, HttpServletResponse response, Student student) throws IOException, ServletException, SQLException, ClassNotFoundException {
       String errorMessage = null;
        if(student.getId() == null){
            errorMessage = "Invalid ID or Password";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }else{
            setUpStudentEnvironment(request,response,student);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/student_dashboard.jsp");
            rd.forward(request,response);
        }

    }
    private void setUpStudentEnvironment(HttpServletRequest request,HttpServletResponse response,Student student) throws ClassNotFoundException, SQLException, ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        getStudentScheduale(request,student.getId());
        getStudentMessageCount(request,response,student.getId());
        getStudentHomeWorkCount(request,response,student.getId());
        setStudentOnlineStatus(studentDAO,student);
        getStudentProfileImg(request,student);
    }
    private void setUpStudentEnvironmentWhileOnline(HttpServletRequest request,HttpServletResponse response,Integer studentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        getStudentScheduale(request,studentID);
        getStudentProfileImgBySession(request,studentID);
        getStudentMessageCount(request,response,studentID);
        getStudentHomeWorkCount(request,response,studentID);


    }

    private void getStudentProfileImgBySession(HttpServletRequest request, Integer studentID) throws SQLException, ClassNotFoundException {

        StudentDAO studentDAO = new StudentDAO();
        String profileImg = studentDAO.getStudentProfileImgIDByStudentID(studentID);
        request.setAttribute("profileImg",profileImg);
    }

    private void getStudentScheduale(HttpServletRequest request, Integer id) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        Integer groupID = studentDAO.getStudentGroupID(id);
        String groupScheduale= studentDAO.getStudentGroupScheduale(groupID);

        request.setAttribute("schedual",groupScheduale);

    }
    private void getStudentProfileImg(HttpServletRequest request,Student student) throws SQLException {
        request.setAttribute("profileImg",student.getImg());
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
    private void setStudentOnlineStatus(StudentDAO studentDAO,Student student) throws ClassNotFoundException, SQLException, ServletException, IOException {
        studentDAO.setStudentOnline(student.getId());
    }
    private String getStudentSessionID(HttpServletRequest request, HttpServletResponse response) {
        String studentFullName = request.getParameter("studentName");
        request.setAttribute("studentFullName", studentFullName);
        return Security.sessionDecrypt(request.getParameter("id"));
    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }


}
