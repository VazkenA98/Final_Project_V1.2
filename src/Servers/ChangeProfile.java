package Servers;

import Beans.Department;
import Beans.Student;
import Beans.Teacher;
import DAO.DepartmentDAO;
import DAO.SettingsDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Utils.Security;
import Utils.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/ChangeProfile")
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class ChangeProfile extends HttpServlet {
    private static final String SAVE_DIR = "accountImages";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            changeProfile(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            changeProfile(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void changeProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
       checkSessionIsAlive(request,response);
       setUpUserProfileChangingEnvironment(request,response);

    }

    private void setUpUserProfileChangingEnvironment(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String process = request.getParameter("process");
        String userPosition =  request.getParameter("userPosition");
        checkProcessType(request,response,userPosition,process);
    }

    private void checkProcessType(HttpServletRequest request, HttpServletResponse response,String userPosition,String process) throws ServletException, IOException, SQLException, ClassNotFoundException {
        RequestDispatcher rd = null;
        if(process.equals("check")){
            checkUserType(request,response,userPosition,process);
            rd = request.getRequestDispatcher("/Account_Pages/change_personal_info.jsp");
            rd.forward(request,response);
        }else{

            checkUserType(request,response,userPosition,process);
        }
    }

    private void checkUserType(HttpServletRequest request, HttpServletResponse response, String userPosition,String process) throws ServletException, IOException, SQLException, ClassNotFoundException {
        if(userPosition.equals("student")){
            if(process.equals("check")) {
                String id = getSessionID(request, response);
                request.setAttribute("userID", id);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("studentName");
                request.setAttribute("userFullName",userName);
                StudentDAO studentDAO = new StudentDAO();
                List<Student> studentList = studentDAO.getStudentsPersonalInfo(Integer.parseInt(id));
                setStudentInfo(request,studentList);
            }
            if(!process.equals("check")) {

                SettingsDAO settingsDAO = new SettingsDAO();
                String studentID = request.getParameter("id");
                changeStudentInfo(request,response,Integer.parseInt(studentID),"student");
                checkUserTypeToLogout(request,response,studentID,"S");

            }
        }else if(userPosition.equals("teacher")){
            if(process.equals("check")) {
                String id = getSessionID(request, response);
                request.setAttribute("userID", id);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("teacherName");
                request.setAttribute("userFullName",userName);
                TeacherDAO teacherDAO = new TeacherDAO();
                List<Teacher> teacherList = teacherDAO.getTeacherPersonalInfo(Integer.parseInt(id));
                setTeacherInfo(request,teacherList);
            }
            if(!process.equals("check")) {

                SettingsDAO settingsDAO = new SettingsDAO();
                String teacherID = request.getParameter("id");
                changeStudentInfo(request,response,Integer.parseInt(teacherID),"teacher");
                checkUserTypeToLogout(request,response,teacherID,"T");

            }
        }else if(userPosition.equals("department")){
            if(process.equals("check")) {
                String id = getSessionID(request, response);
                request.setAttribute("userID", id);
                request.setAttribute("userPosition", userPosition);
                String userName = request.getParameter("departmentName");
                request.setAttribute("departmentFullName",userName);
                DepartmentDAO departmentDAO = new DepartmentDAO();
                List<Department> departmentList = departmentDAO.getDepartmentPersonalInfo(Integer.parseInt(id));
                setDepartmentInfo(request,departmentList);
            }
            if(!process.equals("check")) {

                SettingsDAO settingsDAO = new SettingsDAO();
                String departmentID = request.getParameter("id");
                changeStudentInfo(request,response,Integer.parseInt(departmentID),"department");
                checkUserTypeToLogout(request,response,departmentID,"D");

            }
        }else{

        }
    }



    private String getSessionID(HttpServletRequest request, HttpServletResponse response) {
/*        String studentFullName = request.getParameter("studentName");
        request.setAttribute("studentFullName", studentFullName);*/
        return Security.sessionDecrypt(request.getParameter("id"));
    }

    private void checkSessionIsAlive(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null){
            response.sendRedirect("/Account_Pages/login.jsp");
        }
    }

    private void changeStudentInfo(HttpServletRequest request,HttpServletResponse response,Integer id,String position) throws ClassNotFoundException, SQLException, ServletException, IOException {
        String recovery_email = request.getParameter("recovery_email");
        String phone = request.getParameter("phone");
        Part filePart = request.getPart("imgUpload");
        String img_exist = request.getParameter("img_check");


        String path = null;
if(img_exist.equals("notNull")) {
    LocalDateTime today = LocalDateTime.now();
    ZoneId zid = ZoneId.of("Europe/Paris");
    ZonedDateTime zonedDateTime = ZonedDateTime.of(today, zid);      //That's how you add timezone to date
    String formattedDateTime = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH-mm")
            .format(zonedDateTime);
    // gets absolute path of the web application
    String appPath = request.getServletContext().getRealPath("");
    // constructs path of the directory to save uploaded file
    String stringWithoutSpaces = formattedDateTime.replaceAll("\\s+", "-");
    String savePath = appPath + SAVE_DIR + File.separator + stringWithoutSpaces;
    System.out.println("save Path " + savePath);
    System.out.println("appPath " + appPath);

    // creates the save directory if it does not exists
    File fileSaveDir = new File(savePath);
    if (!fileSaveDir.exists()) {
        fileSaveDir.mkdir();
    }


    String fileName = getFileName(filePart);
    filePart.write(savePath + File.separator + fileName);
   path =SAVE_DIR + File.separator + stringWithoutSpaces + File.separator + fileName;
}else{
    path = "accountImages\\2019-10-24-15-04\\null.jpg";
}
if(position.equals("student")) {
    StudentDAO studentDAO = new StudentDAO();

    studentDAO.updateStudentProfileIMG(id, path);
    /* checkEmailAndPhoneValidation(request,response,recovery_email,phone,id);*/
    studentDAO.updateStudentEmailOrPhone(id, recovery_email, phone);
}else if(position.equals("teacher")){
    TeacherDAO teacherDAO = new TeacherDAO();
    teacherDAO.updateTeacherProfileIMG(id,path);
    teacherDAO.updateTeacherEmailOrPhone(id,recovery_email,phone);
}else if(position.equals("department")){
    DepartmentDAO departmentDAO = new DepartmentDAO();
    departmentDAO.updateDepartmentProfileIMG(id,path);
    departmentDAO.updateDepartmentEmailOrPhone(id,recovery_email,phone);
}else{

}


    }

   /* private void checkEmailAndPhoneValidation(HttpServletRequest request,HttpServletResponse response,String recovery_email,String phone,Integer id) throws ServletException, IOException, SQLException, ClassNotFoundException {
        String errorMessage = null;
        request.setAttribute("userRecoveryEmail",recovery_email);
        request.setAttribute("userPhone",phone);
        StudentDAO studentDAO = null;
            boolean b = Validation.isValidEmailAddress(recovery_email);
        if(b==false){
            studentDAO = new StudentDAO();
            List<Student> studentList = studentDAO.getStudentsPersonalInfo(id);
            setStudentInfoAfterChecking(request,studentList);
            errorMessage = "Invalid Email Address";
            request.setAttribute("errorMsg",errorMessage);
            RequestDispatcher rd = request.getRequestDispatcher("/Account_Pages/change_personal_info.jsp");
            rd.forward(request, response);
        }
    }*/

    private void setStudentInfo(HttpServletRequest request, List<Student> studentList) {
        request.setAttribute("userName",studentList.get(0).getName());
        request.setAttribute("userSurname",studentList.get(0).getSurname());
        request.setAttribute("userBirthday",studentList.get(0).getBirth_date());
        request.setAttribute("userNationality",studentList.get(0).getNationality());
        request.setAttribute("userGender",studentList.get(0).getGender());
        request.setAttribute("userEmail",studentList.get(0).getEmail());
        request.setAttribute("userRecoveryEmail",studentList.get(0).getRecover_email());
        request.setAttribute("userPhone",studentList.get(0).getPhone());
        request.setAttribute("userImg",studentList.get(0).getImg());

    }
    private void setTeacherInfo(HttpServletRequest request, List<Teacher> teacherList) {
        request.setAttribute("userName",teacherList.get(0).getName());
        request.setAttribute("userSurname",teacherList.get(0).getSurname());
        request.setAttribute("userBirthday",teacherList.get(0).getBirth_date());
        request.setAttribute("userNationality",teacherList.get(0).getNationality());
        request.setAttribute("userGender",teacherList.get(0).getGender());
        request.setAttribute("userEmail",teacherList.get(0).getEmail());
        request.setAttribute("userRecoveryEmail",teacherList.get(0).getRecover_email());
        request.setAttribute("userPhone",teacherList.get(0).getPhone());
        request.setAttribute("userImg",teacherList.get(0).getImg());

    }
    private void setDepartmentInfo(HttpServletRequest request, List<Department> departmentList) {
        request.setAttribute("userName",departmentList.get(0).getName());
        request.setAttribute("userSurname",departmentList.get(0).getSurname());
        request.setAttribute("userBirthday",departmentList.get(0).getBirth_date());
        request.setAttribute("userNationality",departmentList.get(0).getNationality());
        request.setAttribute("userGender",departmentList.get(0).getGender());
        request.setAttribute("userEmail",departmentList.get(0).getEmail());
        request.setAttribute("userRecoveryEmail",departmentList.get(0).getRecover_email());
        request.setAttribute("userPhone",departmentList.get(0).getPhone());
        request.setAttribute("userImg",departmentList.get(0).getImg());

    }
    private static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    private void checkUserTypeToLogout(HttpServletRequest request, HttpServletResponse response,String id,String type) throws ClassNotFoundException, SQLException, ServletException, IOException {

        if(type.equals("S")){
            logoutStudent(Integer.parseInt(id));
        }else if(type.equals("T")){
            logoutTeacher(Integer.parseInt(id));
        }else if(type.equals("D")){
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

    private void logoutDepartment(int departmentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentDAO.setDepartmentOffline(departmentID);
    }

    private void logoutStudent(Integer studentID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();

        studentDAO.setStudentOffline(studentID);

    }

    private void logoutTeacher(Integer teacherID) throws ClassNotFoundException, SQLException, ServletException, IOException {
        TeacherDAO teacherDAO = new TeacherDAO();
        teacherDAO.setTeacherOffline(teacherID);

    }
    private void setStudentInfoAfterChecking(HttpServletRequest request, List<Student> studentList) {
        request.setAttribute("userName",studentList.get(0).getName());
        request.setAttribute("userSurname",studentList.get(0).getSurname());
        request.setAttribute("userBirthday",studentList.get(0).getBirth_date());
        request.setAttribute("userNationality",studentList.get(0).getNationality());
        request.setAttribute("userGender",studentList.get(0).getGender());
        request.setAttribute("userEmail",studentList.get(0).getEmail());
        request.setAttribute("userImg",studentList.get(0).getImg());

    }
}
