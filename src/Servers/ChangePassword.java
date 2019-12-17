package Servers;

import DAO.DepartmentDAO;
import DAO.SettingsDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Utils.SHA512;
import Utils.Security;
import Utils.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            changePassword(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            changePassword(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        checkSessionIsAlive(request,response);
        setUserPasswordChangingEnvironment(request,response);
    }

    private void setUserPasswordChangingEnvironment(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String process = request.getParameter("process");
        String userType =  request.getParameter("userPosition");
        checkProcessType(request,response,userType,process);
    }

    private void checkProcessType(HttpServletRequest request, HttpServletResponse response,String userPosition,String process) throws ServletException, IOException, SQLException, ClassNotFoundException {
        RequestDispatcher rd = null;
        if(process.equals("check")){
            checkUserType(request,response,userPosition,process,null, null, null);
            rd = request.getRequestDispatcher("/Account_Pages/change_password.jsp");
            rd.forward(request,response);
        }else{
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String reNewPassword = request.getParameter("reNewPassword");
            checkUserType(request,response,userPosition,process,oldPassword, newPassword, reNewPassword);
            //checkValidationOfPassword(request,response,oldPassword,newPassword,reNewPassword);
        }
    }

    private boolean checkValidationOfPassword(HttpServletRequest request,HttpServletResponse response,String oldPassword, String newPassword, String reNewPassword, String oldDBPassword) throws ServletException, IOException {
        String errorMessage = null;
        request.setAttribute("oldPassword",oldPassword);
        request.setAttribute("newPassword",newPassword);
        request.setAttribute("reNewPassword",reNewPassword);
        boolean flag = true;
        if(!Validation.checkPasswordValidation(newPassword)){
            errorMessage = "New Password Shouldn't Less Than 8 Characters";
            request.setAttribute("errorMsg",errorMessage);
           // sendRequestOnError(request,response);
            flag = false;

        }

       if(!checkEqualStrings(SHA512.encryptPassword(oldPassword),oldDBPassword)){
            errorMessage ="Your Previous Password Is Incorrect";
           request.setAttribute("errorMsg",errorMessage);
           flag = false;
           /* sendRequestOnError(request,response);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/change_password.jsp");
            rd.forward(request, response);*/
        }

       /* if(!checkEqualStrings(newPassword, reNewPassword)){
            errorMessage ="Your New Password Doesn't Match";
            request.setAttribute("errorMsg",errorMessage);
            flag = false;
           // sendRequestOnError(request,response);


        }

        */
        return flag;

    }

    private boolean checkEqualStrings(String OldString,String newString) {
        return OldString.equals(newString);
    }

    private void sendRequestOnError(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/change_password.jsp");
            rd.forward(request, response);
    }

    private void checkUserType(HttpServletRequest request, HttpServletResponse response, String userPosition,String process,String oldPassword, String newPassword, String reNewPassword) throws ServletException, IOException, SQLException, ClassNotFoundException {
        if (userPosition.equals("student")) {

            if (process.equals("check")) {
                String id = getSessionID(request);
                request.setAttribute("userID", id);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("studentName");
                request.setAttribute("userName", userName);
            }
            if (!process.equals("check")) {
                SettingsDAO settingsDAO = new SettingsDAO();
                String studentID = request.getParameter("id");
                String oldDBPassword = settingsDAO.getStudentPassword(Integer.parseInt(studentID));
                request.setAttribute("userID", studentID);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("userName");
                request.setAttribute("userName", userName);
                if (checkValidationOfPassword(request, response, oldPassword, newPassword, reNewPassword, oldDBPassword) == true) {
                    String str = SHA512.encryptPassword(newPassword);
                    settingsDAO.updatePassword(SHA512.encryptPassword(newPassword), "student", Integer.parseInt(studentID));
                    checkUserTypeToLogout(request, response, studentID, "S");
                } else {
                    sendRequestOnError(request, response);
                }
            }


        } else if (userPosition.equals("teacher")) {

            if (process.equals("check")) {
                String id = getSessionID(request);
                request.setAttribute("userID", id);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("teacherName");
                request.setAttribute("userName", userName);
            }
            if (!process.equals("check")) {
                SettingsDAO settingsDAO = new SettingsDAO();
                String teacherID = request.getParameter("id");
                String oldDBPassword = settingsDAO.getTeacherPassword(Integer.parseInt(teacherID));
                request.setAttribute("userID", teacherID);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("userName");
                request.setAttribute("userName", userName);
                if (checkValidationOfPassword(request, response, oldPassword, newPassword, reNewPassword, oldDBPassword) == true) {
                    String str = SHA512.encryptPassword(newPassword);
                    settingsDAO.updatePassword(SHA512.encryptPassword(newPassword), "teacher", Integer.parseInt(teacherID));
                    checkUserTypeToLogout(request, response, teacherID, "T");
                } else {
                    sendRequestOnError(request, response);
                }

            }


        } else if (userPosition.equals("department")) {

            if (process.equals("check")) {
                String id = getSessionID(request);
                request.setAttribute("userID", id);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("departmentName");
                request.setAttribute("userName", userName);
            }
            if (!process.equals("check")) {
                SettingsDAO settingsDAO = new SettingsDAO();
                String departmentID = request.getParameter("id");
                String oldDBPassword = settingsDAO.getDepartmentPassword(Integer.parseInt(departmentID));
                request.setAttribute("userID", departmentID);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("userName");
                request.setAttribute("userName", userName);
                if (checkValidationOfPassword(request, response, oldPassword, newPassword, reNewPassword, oldDBPassword) == true) {
                    String str = SHA512.encryptPassword(newPassword);
                    settingsDAO.updatePassword(SHA512.encryptPassword(newPassword), "department", Integer.parseInt(departmentID));
                    checkUserTypeToLogout(request, response, departmentID, "D");
                } else {
                    sendRequestOnError(request, response);
                }

            } else {

            }
        }
    }


    private String getSessionID(HttpServletRequest request) {
        /*String studentFullName = request.getParameter("studentName");
        request.setAttribute("studentFullName", studentFullName);*/
        return Security.sessionDecrypt(request.getParameter("id"));
    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }
    private void checkUserTypeToLogout(HttpServletRequest request, HttpServletResponse response,String id,String type) throws ClassNotFoundException, SQLException, ServletException, IOException {

        if(type.equals("S")){
            logoutStudent(Integer.parseInt(id));
        }else if(type.equals("T")){
            logoutTeacher(Integer.parseInt(id));
        }
        else if(type.equals("D")){
            logoutDepartment(Integer.parseInt(id));
        }else{

        }

        HttpSession session = request.getSession();
        session.invalidate();
        if (!request.isRequestedSessionIdValid()) {
            response.reset();

            response.setHeader("Cache-Control", "private,no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "private,no-store");
            response.setHeader("Cache-Control", "must-revalidate");
            response.setDateHeader("Expires", 0);
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }

    private void logoutStudent(Integer studentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.setStudentOffline(studentID);
    }
    private void logoutTeacher(Integer teacherID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        TeacherDAO teacherDAO = new TeacherDAO();
        teacherDAO.setTeacherOffline(teacherID);
    }
    private void logoutDepartment(int departmentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentDAO.setDepartmentOffline(departmentID);
    }
}
