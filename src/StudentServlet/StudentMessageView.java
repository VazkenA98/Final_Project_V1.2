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

@WebServlet("/StudentMessageView")
public class StudentMessageView extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            studentMessageView(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            studentMessageView(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void studentMessageView(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpStudentMessageViewEnvironment(request,response);

    }
    private void setUpStudentMessageViewEnvironment(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String studentID = getStudentSessionID(request,response);
        getStudentProfileImgBySession(request,Integer.parseInt(studentID));
        getStudentMessageCount(request,response,Integer.parseInt(studentID));
        getStudentHomeWorkCount(request,response,Integer.parseInt(studentID));
        getStudentMessagesToView(request,studentID);
        RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/student_messege_view.jsp");
        rd.forward(request,response);
    }

    private void getStudentMessagesToView(HttpServletRequest request,String studentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String messageID = request.getParameter("messageID");
        String messageType = request.getParameter("msgType");
        setOpenedMessageSeen(messageID);
        request.setAttribute("msgType",messageType);
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        List<Message> aboutList = mailSendingDAO.getMyMessage(Integer.parseInt(studentID));

        for(int i=0;i<aboutList.size();i++){
            if(aboutList.get(i).getMessageID() != Integer.parseInt(messageID)){
                aboutList.remove(i);
                i--;
            }

        }
        request.setAttribute("studentMessages",aboutList);
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
    private void setOpenedMessageSeen(String messageID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        mailSendingDAO.setMessageSeen(Integer.parseInt(messageID));

    }
    private void getStudentProfileImgBySession(HttpServletRequest request, Integer studentID) throws SQLException, ClassNotFoundException {

        StudentDAO studentDAO = new StudentDAO();
        String profileImg = studentDAO.getStudentProfileImgIDByStudentID(studentID);
        request.setAttribute("profileImg",profileImg);
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

}
