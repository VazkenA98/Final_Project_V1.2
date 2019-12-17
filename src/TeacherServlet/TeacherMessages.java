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

@WebServlet("/TeacherMessages")
public class TeacherMessages extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TeacherMessages(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TeacherMessages(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void TeacherMessages(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpTeacherMessagesEnvironment(request,response);

    }
    private void setUpTeacherMessagesEnvironment(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String teacherID = getTeacherSessionID(request);
        checkProcessToDo(request,response,teacherID);
        getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
        getTeacherMessageCount(request,Integer.parseInt(teacherID));
        getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));
        getTeacherMessages(request,response,teacherID);
    }

    private void checkProcessToDo(HttpServletRequest request,HttpServletResponse response,String teacherID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String process=request.getParameter("process");
        if(process.equals("delete")) {
            String selectedMessages[] = request.getParameterValues("checkbox");
            if(selectedMessages==null){
                getTeacherProfileImgBySession(request,Integer.parseInt(teacherID));
                getTeacherMessageCount(request,Integer.parseInt(teacherID));
                getTeacherHomeWorkCount(request,Integer.parseInt(teacherID));
                getTeacherMessages(request,response,teacherID);
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

    private void getTeacherMessages(HttpServletRequest request, HttpServletResponse response,String teacherID) throws SQLException, ClassNotFoundException, ServletException, IOException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        String messageType =  request.getParameter("messageType");
        List<Message> aboutList = mailSendingDAO.getTeacherMessage(Integer.parseInt(teacherID));
        request.setAttribute("teacherMessages",aboutList);
        if(messageType.equals("msg")){
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/teacher_messages.jsp");
            rd.forward(request,response);
        }else{
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/teacher_homeworks.jsp");
            rd.forward(request,response);
        }
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
