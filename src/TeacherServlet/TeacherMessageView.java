package TeacherServlet;

import Beans.Message;
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

@WebServlet("/TeacherMessageView")
public class TeacherMessageView extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherMessageView(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            teacherMessageView(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void teacherMessageView(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpTeacherMessageViewEnvironment(request,response);

    }
    private void setUpTeacherMessageViewEnvironment(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String teacherID = getTeacherSessionID(request);
        getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
        getTeacherMessageCount(request,Integer.parseInt(teacherID));
        getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));
        getTeacherMessagesToView(request,teacherID);
        RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/teacher_messege_view.jsp");
        rd.forward(request,response);
    }

    private void getTeacherMessagesToView(HttpServletRequest request,String teacherID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String messageID = request.getParameter("messageID");
        String messageType = request.getParameter("msgType");
        setOpenedMessageSeen(messageID);
        request.setAttribute("msgType",messageType);
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        List<Message> aboutList = mailSendingDAO.getTeacherMessage(Integer.parseInt(teacherID));

        for(int i=0;i<aboutList.size();i++){
            if(aboutList.get(i).getMessageID() != Integer.parseInt(messageID)){
                aboutList.remove(i);
                i--;
            }

        }
        request.setAttribute("teacherMessages",aboutList);
    }



    private void setOpenedMessageSeen(String messageID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        mailSendingDAO.setMessageSeen(Integer.parseInt(messageID));

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
