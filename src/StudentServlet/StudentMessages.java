package StudentServlet;

import Beans.Message;
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

@WebServlet("/StudentMessages")
public class StudentMessages extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            studentMessages(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            studentMessages(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void studentMessages(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpStudentMessagesEnvironment(request,response);

    }
    private void setUpStudentMessagesEnvironment(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String studentID = getStudentSessionID(request);
        checkProcessToDo(request,response,studentID);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentMessageCount(request,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,Integer.parseInt(studentID));
        getStudntMessages(request,response,studentID);
    }

    private void checkProcessToDo(HttpServletRequest request,HttpServletResponse response,String studentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String process=request.getParameter("process");
        if(process.equals("delete")) {
            String selectedMessages[] = request.getParameterValues("checkbox");
            if(selectedMessages==null){
                getStudentProfileImgBySession(request,Integer.parseInt(studentID));
                getStudentMessageCount(request,Integer.parseInt(studentID));
                getStudentHomeWorkCount(request,Integer.parseInt(studentID));
                getStudntMessages(request,response,studentID);
            }else {
                MailSendingDAO mailSendingDAO = new MailSendingDAO();
                for (int i = 0; i < selectedMessages.length; i++) {
                    if (selectedMessages[i] != null) {
                        mailSendingDAO.deleteMessagesPDF(Integer.parseInt(selectedMessages[i]));
                        mailSendingDAO.deleteMessages(Integer.parseInt(selectedMessages[i]));
                    } else {

                    }
                }
            }
        }
    }

    private void getStudntMessages(HttpServletRequest request, HttpServletResponse response,String studentID) throws SQLException, ClassNotFoundException, ServletException, IOException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        String messageType =  request.getParameter("messageType");
        List<Message> aboutList = mailSendingDAO.getMyMessage(Integer.parseInt(studentID));
        request.setAttribute("studentMessages",aboutList);
        if(messageType.equals("msg")){
        RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/student_messages.jsp");
        rd.forward(request,response);
        }else{
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/student_homeworks.jsp");
            rd.forward(request,response);
        }
    }


    private String getStudentSessionID(HttpServletRequest request) {
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
    private void getStudentMessageCount(HttpServletRequest request,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyMessageCount(studentID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private void getStudentProfileImgBySession(HttpServletRequest request, Integer studentID) throws SQLException, ClassNotFoundException {

        StudentDAO studentDAO = new StudentDAO();
        String profileImg = studentDAO.getStudentProfileImgIDByStudentID(studentID);
        request.setAttribute("profileImg",profileImg);
    }
    private void getStudentHomeWorkCount(HttpServletRequest request,int studentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getMyHomwWorkCount(studentID);
        request.setAttribute("homeWorkCount",messegeCount.toString());

    }



}
