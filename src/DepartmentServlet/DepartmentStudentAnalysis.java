package DepartmentServlet;

import Beans.StudentGrades;
import DAO.DepartmentDAO;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/DepartmentStudentAnalysis")
public class DepartmentStudentAnalysis extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                departmentStudentAnalysis(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                departmentStudentAnalysis(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentStudentAnalysis(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ClassNotFoundException, ServletException, ParseException {
        checkSessionIsAlive(request,response);
        String departmentID = getDepartmentSessionID(request,response);
        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        String studentCustemID =  request.getParameter("studentID");
        getStudentGrades(request,studentCustemID);
        RequestDispatcher rd = null;
        getStudentsGrades(request,studentCustemID);
        rd = request.getRequestDispatcher("/Account_Pages/department_student_analysis.jsp");
        rd.forward(request,response);
    }
    private void getStudentGrades(HttpServletRequest request,String studentCustemID) throws SQLException, ClassNotFoundException {
        StudentDAO studentDAO = new StudentDAO();
        Integer studentID =  studentDAO.getStudentIDByCustemID(studentCustemID);
        List<StudentGrades> studentList = studentDAO.getStudentGrades(studentID);
        calculatGrades(request,studentList);
    }
    private void getStudentsGrades(HttpServletRequest request, String studentCustemID) throws SQLException, ClassNotFoundException, ParseException {
        StudentDAO studentDAO = new StudentDAO();
        int studentID = studentDAO.getStudentIDByCustemID(studentCustemID);
        List<StudentGrades> studentList = calculatGradesForGraph(studentDAO.getStudentGrades(studentID));
        List<Double> avgs = null;
        List<String> semesters = null;
        double groupAVG = 0;
        ArrayList<Date> date = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < studentList.size(); i++) {
            Date date1 = format.parse(studentList.get(i).getSemesterYear());
            date.add(date1);
        }

        Collections.sort(date);
        date.forEach(action -> System.out.println(format.format(action)));

        Map<String, Double> map = new HashMap<>();
        List<String> totalMarks = null;
        for (int i = 0; i < date.size(); i++) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(date.get(i));
            totalMarks = new ArrayList<>();
            for (int j = 0; j < studentList.size(); j++) {

                    String str = studentList.get(j).getSemesterYear();
                    if (str.equals(strDate)) {
                        totalMarks.add(studentList.get(j).getTotalScore());
                    }


            }
            int sum = 0;
            double avg = 0;
            for (int q = 0; q < totalMarks.size(); q++) {
                sum += Integer.parseInt(totalMarks.get(q));
            }

            avg = sum / totalMarks.size();
            double avgGPA = (avg * 4.0) / 100;
            map.put(strDate, avgGPA);

            avgs = new ArrayList<>();
            semesters = new ArrayList<>();
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                avgs.add(entry.getValue());
                semesters.add(entry.getKey());
            }


        }

        Collections.reverse(avgs);
        Collections.reverse(semesters);


        if (avgs.size() < 8) {
            for (int i = 8 - avgs.size(); i < 8; i++) {
                avgs.add(null);
            }
        }

        request.setAttribute("studentsAVGGrades", avgs);


        double groupSum = 0;

        for (Map.Entry<String, Double> entry : map.entrySet()) {
            groupSum += entry.getValue();
        }
        groupAVG = groupSum / map.size();
        request.setAttribute("groupAVG",groupAVG);

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
    private List<StudentGrades> calculatGradesForGraph(List<StudentGrades> studentList ){
        if(studentList.size()>0) {
            for (int i = 0; i < studentList.size(); i++) {
                Integer total = Integer.parseInt(studentList.get(i).getExam_1()) + Integer.parseInt(studentList.get(i).getExam_2()) + Integer.parseInt(studentList.get(i).getFinalExam());
                studentList.get(i).setTotalScore(total.toString());
                if (total >= 95) {
                    studentList.get(i).setScoreSymbol("A+");
                } else if (total >= 87 && total <= 94) {
                    studentList.get(i).setScoreSymbol("A");
                } else if (total >= 81 && total <= 86) {
                    studentList.get(i).setScoreSymbol("A-");
                } else if (total >= 75 && total <= 80) {
                    studentList.get(i).setScoreSymbol("B+");
                } else if (total >= 67 && total <= 74) {
                    studentList.get(i).setScoreSymbol("B");
                } else if (total >= 61 && total <= 66) {
                    studentList.get(i).setScoreSymbol("B-");
                } else if (total >= 55 && total <= 60) {
                    studentList.get(i).setScoreSymbol("C+");
                } else if (total >= 46 && total <= 54) {
                    studentList.get(i).setScoreSymbol("C");
                } else if (total >= 40 && total <= 45) {
                    studentList.get(i).setScoreSymbol("C-");
                } else {
                    studentList.get(i).setScoreSymbol("F");
                }
            }
        }else{

        }
        return studentList;
    }
    private void calculatGrades(HttpServletRequest request,List<StudentGrades> studentList ){
        for(int i=0;i<studentList.size();i++){
            Integer total = Integer.parseInt(studentList.get(i).getExam_1())+Integer.parseInt(studentList.get(i).getExam_2())+Integer.parseInt(studentList.get(i).getFinalExam());
            studentList.get(i).setTotalScore(total.toString());
            if(total>=95){
                studentList.get(i).setScoreSymbol("A+");
            }else if(total>=87 && total<=94){
                studentList.get(i).setScoreSymbol("A");
            }else if(total>=81 && total<=86){
                studentList.get(i).setScoreSymbol("A-");
            }else if(total>=75 && total<=80){
                studentList.get(i).setScoreSymbol("B+");
            }else if(total>=67 && total<=74){
                studentList.get(i).setScoreSymbol("B");
            } else if(total>=61 && total<=66){
                studentList.get(i).setScoreSymbol("B-");
            } else if(total>=55 && total<=60){
                studentList.get(i).setScoreSymbol("C+");
            } else if(total>=46 && total<=54){
                studentList.get(i).setScoreSymbol("C");
            } else if(total>=40 && total<=45){
                studentList.get(i).setScoreSymbol("C-");
            }else{
                studentList.get(i).setScoreSymbol("F");
            }
        }
        request.setAttribute("studentGrades",studentList);
    }
}
