package DepartmentServlet;

import Beans.Department;
import Beans.Teacher;
import DAO.DepartmentDAO;
import DAO.MailSendingDAO;
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

@WebServlet("/Account_Pages/DepartmentLogin")
public class DepartmentLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DepartmentLogin(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DepartmentLogin(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void DepartmentLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {


        String process =  request.getParameter("process");
        if(process.equals("afterLogin")){
            checkSessionIsAlive(request,response);
            String teacherID = getDepartmentSessionID(request,response);
            setUpTeacherEnvironmentWhileOnline(request,response,Integer.parseInt(teacherID));
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/department_dashboard.jsp");
            rd.forward(request,response);
        }else {
            String email = (String) request.getAttribute("loginEmail");
            String id = (String) request.getAttribute("loginCustemID");
            String password = (String) request.getAttribute("loginPassword");
            getAllDepartments(request, response, email, id, password);
        }

    }
    private void getAllDepartments(HttpServletRequest request, HttpServletResponse response,String email, String custem_ID, String password) throws SQLException, ClassNotFoundException, IOException, ServletException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        Department department = new Department();
        String departmentSessionFullName = null;
        Integer requestedID = departmentDAO.getDepartmentID(email,custem_ID, SHA512.encryptPassword(password));
        List<Department> departmentList = departmentDAO.getAllDepartments();
        for (int i = 0; i <departmentList.size() ; i++) {
            if (departmentList.get(i).getId() == requestedID) {
                Integer studentId = departmentList.get(i).getId();
                String name = departmentList.get(i).getName();
                String surname = departmentList.get(i).getSurname();
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(600);
                session.setAttribute("departmentSessionID", Security.sessionEncrypt(studentId.toString()));
                departmentSessionFullName = name + " " + surname;
                request.setAttribute("departmentFullName", departmentSessionFullName);
                department = departmentList.get(i);
            } else {
                continue;
            }
        }
        checkDepartmentExistence(request,response,department);


    }
    private void checkDepartmentExistence(HttpServletRequest request, HttpServletResponse response, Department department) throws IOException, ServletException, SQLException, ClassNotFoundException {
        String errorMessage = null;
        if(department.getId() == null){
            errorMessage = "Invalid ID or Password";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }else{
            setUpDepartmentEnvironment(request,response,department);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/department_dashboard.jsp");
            rd.forward(request,response);
        }

    }
    private void setUpDepartmentEnvironment(HttpServletRequest request,HttpServletResponse response,Department department) throws ClassNotFoundException, SQLException, ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        getDepartmentMessageCount(request,response,department.getId());
        setDepartmentOnlineStatus(departmentDAO,department);
        getDepartmentProfileImg(request,department);
    }
    private void setUpTeacherEnvironmentWhileOnline(HttpServletRequest request,HttpServletResponse response,Integer departmentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        getDepartmentProfileImgBySession(request,departmentID);
        getDepartmentMessageCount(request,response,departmentID);

    }

    private void getDepartmentProfileImgBySession(HttpServletRequest request, Integer departmentID) throws SQLException, ClassNotFoundException {

        DepartmentDAO departmentDAO = new DepartmentDAO();
        String profileImg = departmentDAO.getTeacherProfileImgIDByDepartmentID(departmentID);
        request.setAttribute("profileImg",profileImg);
    }

    private void getDepartmentProfileImg(HttpServletRequest request,Department department) throws SQLException {
        request.setAttribute("profileImg",department.getImg());
    }

    private void getDepartmentMessageCount(HttpServletRequest request, HttpServletResponse response,int departmentID) throws IOException, ServletException, SQLException, ClassNotFoundException {
        MailSendingDAO mailSendingDAO = new MailSendingDAO();
        Integer messegeCount = mailSendingDAO.getDepartmentMessageCount(departmentID);
        request.setAttribute("msgCount",messegeCount.toString());

    }
    private void setDepartmentOnlineStatus(DepartmentDAO departmentDAO,Department department) throws ClassNotFoundException, SQLException, ServletException, IOException {
        departmentDAO.setDepartmentOnline(department.getId());
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
