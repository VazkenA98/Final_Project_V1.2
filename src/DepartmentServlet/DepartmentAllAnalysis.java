package DepartmentServlet;

import Beans.Group;
import Beans.Student;
import Beans.StudentGrades;
import DAO.DepartmentDAO;
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/DepartmentAllAnalysis")
public class DepartmentAllAnalysis extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            try {
                departmentAllAnalysis(request,response);
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
                departmentAllAnalysis(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void departmentAllAnalysis(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException, ParseException {
        checkSessionIsAlive(request,response);
        setUpDepartmentAnalysisEnvironment(request,response);
    }

    private void setUpDepartmentAnalysisEnvironment(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException, ParseException {
        String departmentID = getDepartmentSessionID(request,response);

        getDepartmentProfileImgBySession(request,Integer.parseInt(departmentID));
        getDepartmentMessageCount(request,response,Integer.parseInt(departmentID));
        List<Student> students = getAllDepartmenStudents(Integer.parseInt(departmentID));
        getAllStudentSexRatio(request,students);
        getAllStudentsNationalityRatio(request,students);
        getTeachersCount(request,departmentID);
        getGroupStudentsCountWithGroupAVGGPA(request,departmentID);
        getCoursesCountWithParticipantsCount(request,students,Integer.parseInt(departmentID));


        RequestDispatcher rd = null;
        rd = request.getRequestDispatcher("/Account_Pages/department_analysis.jsp");
        rd.forward(request,response);
    }

    private void getTeachersCount(HttpServletRequest request, String departmentID) throws SQLException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        List<Integer> groupIDs = departmentDAO.getDepartmentGroupIDs(Integer.parseInt(departmentID));
        List<List<Integer>> teacherIDs = new ArrayList<>();
        for(int i=0;i<groupIDs.size();i++){
            teacherIDs.add(departmentDAO.getTeacherIDByGroupID(groupIDs.get(i)));
        }
        int tCount = 0;
        for(int i=0;i<teacherIDs.size();i++){
            for(int j=0;j<teacherIDs.get(i).size();j++){
                if(!teacherIDs.get(i).isEmpty()) {
                    tCount++;
                }
            }
        }
        request.setAttribute("teachersCount",tCount);
    }

    private void getGroupStudentsCountWithGroupAVGGPA(HttpServletRequest request, String departmentID) throws SQLException, ParseException, ClassNotFoundException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        StudentDAO studentDAO = new StudentDAO();
        List<Group> groupList = departmentDAO.getGroupsByDepartmentID(Integer.parseInt(departmentID));
        List<Integer> groupStudentsCount  = new ArrayList<>();
        List<Double> groupAvgGPA  = new ArrayList<>();
        List<String> groupsName = new ArrayList<>();
        for(int i=0;i<groupList.size();i++){
            List<Student> students = studentDAO.getStudentsByGroupID(groupList.get(i).getId());
            groupStudentsCount.add(students.size());
            groupAvgGPA.add(getStudentsGrades(request,Integer.toString(groupList.get(i).getId())));
            groupsName.add(groupList.get(i).getName());
        }
        double sum = 0.0;
        for(int j=0;j<groupAvgGPA.size();j++){
            sum+=groupAvgGPA.get(j);
        }

        double avgTotalGPA = sum/groupAvgGPA.size();
        avgTotalGPA =Double.parseDouble(new DecimalFormat("##.##").format(avgTotalGPA));
        request.setAttribute("groupAvgGPA",groupAvgGPA);
        request.setAttribute("groupStudentsCount",groupStudentsCount);
        request.setAttribute("groupsName",groupsName);
        request.setAttribute("totlaGroupsAVGGpa",avgTotalGPA);
        request.setAttribute("groupsCount",groupList.size());



    }
    private void getCoursesCountWithParticipantsCount(HttpServletRequest request, List<Student> students,int departmentID) throws SQLException, ParseException, ClassNotFoundException {

        DepartmentDAO departmentDAO = new DepartmentDAO();
        StudentDAO studentDAO = new StudentDAO();
        Integer faculityID = departmentDAO.getDepartmentFaculityID(departmentID);
        List<String> coursesOfFaculity =  departmentDAO.getDepartmentCourses(faculityID);

        List<Group> groupList = departmentDAO.getGroupsByDepartmentID(departmentID);
        List<Integer> studentList = studentDAO.getAllStudentsGroupID();

        Map<String,Integer> map = new HashMap<>();

        for(int i=0;i<coursesOfFaculity.size();i++){
            int count = 0;
            for(int j=0;j<groupList.size();j++){
                if(coursesOfFaculity.get(i).equals(groupList.get(j).getGroupCourse())) {
                    for (int k = 0; k < studentList.size(); k++) {
                        if (studentList.get(k) == groupList.get(j).getId()){
                            count++;
                        }
                    }
                }
            }
            map.put(coursesOfFaculity.get(i),count);

        }

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        System.out.println(sortedMap);
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
       request.setAttribute("facultiyMap",sortedMap);
        request.setAttribute("coursesCount",sortedMap.size());


    }



    private Double getStudentsGrades(HttpServletRequest request,String groupID) throws SQLException, ClassNotFoundException, ParseException {
        StudentDAO studentDAO = new StudentDAO();
        List<Double> avgs = null;
        List<String> semesters = null;
        double groupAVG = 0;
        List<Student> students = studentDAO.getStudentsByGroupID(Integer.parseInt(groupID));
        String studentEntranceYear = studentDAO.getStudentEntranceYear(students.get(0).getId());
        List<List<StudentGrades>>  list= new ArrayList<>();

        List<StudentGrades> studentList = null;
        for (int i = 0; i < students.size(); i++) {
            studentList = studentDAO.getStudentGrades(students.get(i).getId());
            if(studentList == null){
                list.add(null);
            }else {
                list.add(calculatGrades(studentList));
            }
        }
        int nullCount = 0;
        System.out.println("++++++++++" +list);
        for(int u =0;u<list.size();u++) {
            if(list.get(u).isEmpty()) {
                nullCount++;
            }
        }
        if(nullCount == list.size()){
            groupAVG = 0.0;
            avgs = new ArrayList<>();
            request.setAttribute("studentsAVGGrades", avgs);
        }else {

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
                for (int j = 0; j < list.size(); j++) {
                    for (int k = 0; k < list.get(j).size(); k++) {
                        String str = list.get(j).get(k).getSemesterYear();
                        if (str.equals(strDate)) {
                            totalMarks.add(list.get(j).get(k).getTotalScore());
                        }
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
        }




      /*  request.setAttribute("groupID",groupID);
        request.setAttribute("studentsGrades",map);*/
      return groupAVG;
    }












    private List<Student> getAllDepartmenStudents(int departmentID) throws SQLException {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        List<Group> groupList = departmentDAO.getGroupsByDepartmentID(departmentID);
        Set<String> nameSet = new HashSet<>();
        List<Group> groupDistinctByName = groupList.stream()
                .filter(e -> nameSet.add(e.getName()))
                .collect(Collectors.toList());
        /*request.setAttribute("departmentGroups",groupDistinctByName);*/
        TeacherDAO teacherDAO =  new TeacherDAO();
        List<List<Student>> allStudents = new ArrayList<>();
        for(int i=0;i<groupDistinctByName.size();i++) {
            List<Student> departmentStudents = teacherDAO.getTeacherGroupStudents(groupDistinctByName.get(i).getId());
            Comparator<Student> compareByName = Comparator.comparing(Student::getName);
            Collections.sort(departmentStudents, compareByName);
            allStudents.add(departmentStudents);
        }
       List<Student> students = new ArrayList<>();
    for(int j=0;j<allStudents.size();j++){
        for(int k =0; k<allStudents.get(j).size();k++){
            students.add(allStudents.get(j).get(k));
        }
    }
    return students;
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
    private void getAllStudentSexRatio(HttpServletRequest request, List<Student> students) {
        int maleCount = 0;
        int femaleCount = 0;
        double malePers = 0.0;
        double femalePers = 0.0;
        int studentsCount = students.size();
        for(int i=0;i<students.size();i++){
            if(students.get(i).getGender().equals("Male")){
                maleCount++;
            }else{
                femaleCount++;
            }
        }
        if(maleCount == 0) {
            malePers = 0.0;
        }else {
            malePers = (maleCount * 100) / students.size();
        }
        if(femaleCount == 0) {

        }else {
            femalePers = (femaleCount * 100) / students.size();
        }
        request.setAttribute("maleCount",maleCount);
        request.setAttribute("femaleCount",femaleCount);
        request.setAttribute("malePers",malePers);
        request.setAttribute("femalePers",femalePers);
        request.setAttribute("studentsCount",studentsCount);
    }
    private void getAllStudentsNationalityRatio(HttpServletRequest request, List<Student> students) {
        List<String> set = new ArrayList<>();
        for(int i=0;i<students.size();i++){
            set.add(students.get(i).getNationality());
        }
        Map<String,Double> natRatios = new HashMap<>();
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(set);
        Map<String,Integer> natCounts = new HashMap<>();
        ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);

        for (int j =0 ;j<listWithoutDuplicates.size();j++) {
            int count = 0;

            for(int i=0;i<students.size();i++) {
                if(listWithoutDuplicates.get(j).equals(students.get(i).getNationality())){
                    count++;
                }
            }
            natCounts.put(listWithoutDuplicates.get(j),count);
            double avg = 0.0;
            avg = (count*100)/students.size();
            natRatios.put(listWithoutDuplicates.get(j),avg);
        }
        request.setAttribute("natRatio",natRatios);
        request.setAttribute("natCount",natCounts.size());
    }
    private List<StudentGrades> calculatGrades(List<StudentGrades> studentList ){
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
}
