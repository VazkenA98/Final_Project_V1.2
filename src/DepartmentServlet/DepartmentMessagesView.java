package DepartmentServlet;

import Beans.Message;
import DAO.DepartmentDAO;
import DAO.MailSendingDAO;
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

@WebServlet("/DepartmentMessagesView")
public class DepartmentMessagesView extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentMessagesView(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentMessagesView(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentMessagesView(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpDepartmentMessagesView(request,response);
    }

    private void setUpDepartmentMessagesView(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        String departmentID = getDepartmentSessionID(request,response);

        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        getDepartmentMessagesToView(request,departmentID);
        RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/department_messege_view.jsp");
        rd.forward(request,response);
    }
    private void getDepartmentMessagesToView(HttpServletRequest request,String departmentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String messageID = request.getParameter("messageID");
        String messageType = request.getParameter("msgType");
        setOpenedMessageSeen(messageID);
        request.setAttribute("msgType",messageType);
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        List<Message> aboutList = mailSendingDAO.getDepartmentMessage(Integer.parseInt(departmentID));

        for(int i=0;i<aboutList.size();i++){
            if(aboutList.get(i).getMessageID() != Integer.parseInt(messageID)){
                aboutList.remove(i);
                i--;
            }

        }
        request.setAttribute("departmentMessages",aboutList);
    }



    private void setOpenedMessageSeen(String messageID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        mailSendingDAO.setMessageSeen(Integer.parseInt(messageID));

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
}
