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

@WebServlet("/DepartmentMessages")
public class DepartmentMessages extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentMessages(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            departmentMessages(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentMessages(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUpDepartmentMessagesEnviroment(request,response);
    }

    private void setUpDepartmentMessagesEnviroment(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String departmentID = getDepartmentSessionID(request,response);
        checkProcessToDo(request,response,departmentID);
        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        getDepartmentMessages(request,response,departmentID);
    }
    private void checkProcessToDo(HttpServletRequest request,HttpServletResponse response,String departmentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String process=request.getParameter("process");
        if(process.equals("delete")) {
            String selectedMessages[] = request.getParameterValues("checkbox");
            if(selectedMessages==null){
                getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
                getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
                getDepartmentMessages(request,response,departmentID);
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

    private void getDepartmentMessages(HttpServletRequest request, HttpServletResponse response,String departmentID) throws SQLException, ClassNotFoundException, ServletException, IOException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        String messageType =  request.getParameter("messageType");
        List<Message> aboutList = mailSendingDAO.getDepartmentMessage(Integer.parseInt(departmentID));
        request.setAttribute("departmentMessages",aboutList);
        if(messageType.equals("msg")){
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/department_messages.jsp");
            rd.forward(request,response);
        }else{
            /*RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/student_homeworks.jsp");
            rd.forward(request,response);*/
        }
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
