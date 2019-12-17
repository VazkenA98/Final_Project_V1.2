package TeacherServlet;

import Beans.Student;
import Beans.Teacher;
import DAO.MailSendingDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/Account_Pages/TeacherLogin")
public class TeacherLogin extends HttpServlet {
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
            String teacherID = getTeacherSessionID(request,response);
            setUpTeacherEnvironmentWhileOnline(request,response,Integer.parseInt(teacherID));
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/teacher_dashboard.jsp");
            rd.forward(request,response);
        }else {
            String email = (String) request.getAttribute("loginEmail");
            String id = (String) request.getAttribute("loginCustemID");
            String password = (String) request.getAttribute("loginPassword");
            getAllTeachers(request, response, email, id, password);
        }

    }
    private void getAllTeachers(HttpServletRequest request, HttpServletResponse response,String email, String custem_ID, String password) throws SQLException, ClassNotFoundException, IOException, ServletException {
        Teacher teacher = new Teacher();
        String teacherSessionFullName = null;
        TeacherDAO teacherDAO = new TeacherDAO();
        Integer requestedID = teacherDAO.getTeacherID(email,custem_ID, SHA512.encryptPassword(password));
        List<Teacher> teacherList = teacherDAO.getAllTeachers();
        for (int i = 0; i <teacherList.size() ; i++) {
            if (teacherList.get(i).getId() == requestedID) {
                Integer studentId = teacherList.get(i).getId();
                String name = teacherList.get(i).getName();
                String surname = teacherList.get(i).getSurname();
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(600);
                session.setAttribute("teacherSessionID", Security.sessionEncrypt(studentId.toString()));
                teacherSessionFullName = name + " " + surname;
                request.setAttribute("teacherFullName", teacherSessionFullName);
                teacher = teacherList.get(i);
            } else {
                continue;
            }
        }
        checkStudentExistence(request,response,teacher);


    }
    private void checkStudentExistence(HttpServletRequest request, HttpServletResponse response, Teacher teacher) throws IOException, ServletException, SQLException, ClassNotFoundException {
        String errorMessage = null;
        if(teacher.getId() == null){
            errorMessage = "Invalid ID or Password";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }else{
            setUpTeacherEnvironment(request,response,teacher);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/teacher_dashboard.jsp");
            rd.forward(request,response);
        }

    }
    private void setUpTeacherEnvironment(HttpServletRequest request,HttpServletResponse response,Teacher teacher) throws ClassNotFoundException, SQLException, ServletException, IOException {
        TeacherDAO teacherDAO = new TeacherDAO();
        getTeacherMessageCount(request,response,teacher.getId());
        getTeacherHomeWorkCount(request,response,teacher.getId());
        setTeacherOnlineStatus(teacherDAO,teacher);
        getTeacherProfileImg(request,teacher);
    }
    private void setUpTeacherEnvironmentWhileOnline(HttpServletRequest request,HttpServletResponse response,Integer teacherID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        TeacherDAO teacherDAO = new TeacherDAO();
        getTeacherProfileImgBySession(request,teacherID);
        getTeacherMessageCount(request,response,teacherID);
        getTeacherHomeWorkCount(request,response,teacherID);


    }

    private void getTeacherProfileImgBySession(HttpServletRequest request, Integer teacherID) throws SQLException, ClassNotFoundException {

        TeacherDAO teacherDAO = new TeacherDAO();
        String profileImg = teacherDAO.getTeacherProfileImgIDByTeacherID(teacherID);
        request.setAttribute("profileImg",profileImg);
    }

    private void getTeacherProfileImg(HttpServletRequest request,Teacher teacher) throws SQLException {
        request.setAttribute("profileImg",teacher.getImg());
    }

    private void getTeacherMessageCount(HttpServletRequest request, HttpServletResponse response,int teacherID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getTeacherMessageCount(teacherID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private void getTeacherHomeWorkCount(HttpServletRequest request, HttpServletResponse response,int teacherID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getTeacherHomwWorkCount(teacherID);
        request.setAttribute("homeWorkCount",messegeCount.toString());

    }
    private void setTeacherOnlineStatus(TeacherDAO teacherDAO,Teacher teacher) throws ClassNotFoundException, SQLException, ServletException, IOException {
        teacherDAO.setTeacherOnline(teacher.getId());
    }
    private String getTeacherSessionID(HttpServletRequest request, HttpServletResponse response) {
        String studentFullName = request.getParameter("teacherName");
        request.setAttribute("teacherFullName", studentFullName);
        return Security.sessionDecrypt(request.getParameter("id"));
    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }


}
