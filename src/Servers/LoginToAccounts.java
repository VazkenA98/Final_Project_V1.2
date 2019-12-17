package Servers;

import Utils.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Account_Pages/LoginToAccounts")
public class LoginToAccounts extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginAccount(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loginAccount(request, response);
    }

    private void loginAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkParameters(request,response);

    }
    private void checkParameters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String id = request.getParameter("ID");
        String password = request.getParameter("password");
        request.setAttribute("loginEmail", email);
        request.setAttribute("loginCustemID", id);

        request.setAttribute("loginPassword", password);
        checkUser(request, response,email, id, password);
    }



    private void checkUser(HttpServletRequest request, HttpServletResponse response,String email, String id, String password) throws ServletException, IOException {
        //TODO: change to switchcase
        String errorMessage = null;
        String userID = id.substring(0, 1).toUpperCase();

        RequestDispatcher rd = null;
        switch (userID) {
            case "S":
                checkUserInputeValidations(request,response,email,id,password);
                rd = request.getRequestDispatcher("/Account_Pages/StudentLogin?process=login");
                rd.forward(request, response);
                break;
            case "T":
                checkUserInputeValidations(request,response,email,id,password);
                rd = request.getRequestDispatcher("/Account_Pages/TeacherLogin?process=login");
                rd.forward(request, response);
                break;
            case "D":
                checkUserInputeValidations(request,response,email,id,password);
                rd = request.getRequestDispatcher("/Account_Pages/DepartmentLogin?process=login");
                rd.forward(request, response);
                break;
            case "R":

                break;
            case "F":

                break;
            default:
                errorMessage = "Invalid ID Please Check Again";
                request.setAttribute("errorMsg",errorMessage);
                rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
                rd.forward(request, response);
                break;

        }

    }

    private void checkUserInputeValidations(HttpServletRequest request,HttpServletResponse response,String email, String id, String password) throws ServletException, IOException {
        String errorMessage = null;
        String oldEmail = email;
        String oldID = id;
        String oldPassword = password;
        request.setAttribute("email",oldEmail);
        request.setAttribute("id",oldID);
        request.setAttribute("password",oldPassword);
        if(email==null || id==null || password==null){
            errorMessage = "Missing an Input";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }
        if(!Validation.checkPasswordValidation(password)){
            errorMessage = "Password is less than 8 characters";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }
        if(Validation.checkCustemIDValidation(id)==false){
            errorMessage = "ID is Invalid";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }
        if(Validation.checkCustemIdIsOnlyNumbers(id)==false){
            errorMessage = "ID is Invalid";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }
        if(Validation.checkUserUniversityEmail(email)==false){
            errorMessage = "Email is Invalid";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/login.jsp");
            rd.forward(request, response);
        }

    }

}
