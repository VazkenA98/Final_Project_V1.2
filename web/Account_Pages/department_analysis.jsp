<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.LinkedHashMap" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<% Map<String,Double> natRatio = (Map<String, Double>) request.getAttribute("natRatio");%>
<% LinkedHashMap<String,Integer> facMap = (LinkedHashMap<String, Integer>) request.getAttribute("facultiyMap");%>
<%List<String> groupsName = (List<String>) request.getAttribute("groupsName");%>
<%List<Double> groupAvgGPA = (List<Double>) request.getAttribute("groupAvgGPA");%>
<%List<Integer> groupStudentsCount = (List<Integer>) request.getAttribute("groupStudentsCount");%>
<html lang="en">
<head>

    <meta charset="utf-8">
    <%--asi refreshi hamare tebi servlet (message count)--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>NPUA_Department</title>

    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/Account_Pages/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<%=request.getContextPath()%>/Account_Pages/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">
<script >
    window.onload = function () {

        var chart = new CanvasJS.Chart("chartContainer", {
            animationEnabled: true,
            exportEnabled: true,
            title: {
                text: "Students Nationality Ratio"
            },
            data: [{
                type: "pie",
                startAngle: 240,
                yValueFormatString: "##0.00\"%\"",
                indexLabel: "{label} {y}",
                dataPoints: [

                    <%  int count =0;
                    for (Map.Entry<String,Double> entry : natRatio.entrySet()) {
                    count++;
                    if(count != natRatio.size()){
                    %>
                    {y: <%=entry.getValue()%>, label: "<%=entry.getKey()%>"},
                    <%}else{%>
                    {y: <%=entry.getValue()%>, label: "<%=entry.getKey()%>"}
                    <%}}%>
                ]
            }]
        });

        var chart2 = new CanvasJS.Chart("chartContainer2", {
            exportEnabled: true,
            animationEnabled: true,
            title:{
                text: "Students Male/Female Ratio"
            },
            legend:{
                cursor: "pointer",
                itemclick: explodePie
            },
            data: [{
                type: "pie",
                showInLegend: true,
                toolTipContent: "{name}: <strong>{y}%</strong>",
                indexLabel: "{name} - {y}%",
                dataPoints: [
                    { y: <%=request.getAttribute("malePers")%>, name: "Male", exploded: true },
                    { y: <%=request.getAttribute("femalePers")%>, name: "Female"}
                ]
            }]
        });

        var chart3 = new CanvasJS.Chart("chartContainer3", {
            animationEnabled: true,
            exportEnabled: true,
            title:{
                text: "Groups AVG GPA & Students Count"
            },
            axisY: {
                title: "Average Group GPA",
                titleFontColor: "#4F81BC",
                lineColor: "#4F81BC",
                labelFontColor: "#4F81BC",
                tickColor: "#4F81BC"
            },
            axisY2: {
                title: "Students Count",
                titleFontColor: "#C0504E",
                lineColor: "#C0504E",
                labelFontColor: "#C0504E",
                tickColor: "#C0504E"
            },
            toolTip: {
                shared: true
            },
            legend: {
                cursor:"pointer",
                itemclick: toggleDataSeries
            },
            data: [{
                type: "column",
                name: "AVG Group GPA",
                legendText: "AVG Group GPA",
                showInLegend: true,
                dataPoints:[

                    <% int i =0;
                        for (int k = 0; k<groupAvgGPA.size();k++) {
                        i++;
                        if(i != groupAvgGPA.size()){
                        %>
                    {label: "<%=groupsName.get(i-1)%>", y: <%=groupAvgGPA.get(k)%>},
                    <%}else{%>
                    {label: "<%=groupsName.get(i-1)%>", y: <%=groupAvgGPA.get(k)%>}
                    <%}}%>
                ]
            },
                {
                    type: "column",
                    name: "Students Count",
                    legendText: "Students Count",
                    axisYType: "secondary",
                    showInLegend: true,
                    dataPoints:[
                        <% int j =0;
                         for (int k = 0; k<groupStudentsCount.size();k++) {
                         j++;
                         if(j != groupStudentsCount.size()){
                         %>
                        {label: "<%=groupsName.get(j-1)%>", y: <%=groupStudentsCount.get(k)%>},
                        <%}else{%>
                        {label: "<%=groupsName.get(j-1)%>", y: <%=groupStudentsCount.get(k)%>}
                        <%}}%>
                    ]
                }]
        });
        var chart4 = new CanvasJS.Chart("chartContainer5", {
            animationEnabled: true,
            exportEnabled: true,
            title:{
                text:"Students Count For Each Major"
            },
            axisX:{
                interval: 1
            },
            axisY2:{
                interlacedColor: "rgba(1,77,101,.2)",
                gridColor: "rgba(1,77,101,.1)",
                title: "Number of Students"
            },
            data: [{
                type: "bar",
                name: "companies",
                axisYType: "secondary",
                color: "#014D65",
                dataPoints: [

                    <%  int count2 =0;
                     for (Map.Entry entry:facMap.entrySet()) {
                     count2++;
                     if(count2 != facMap.size()){
                     %>
                    { y: <%=entry.getValue()%>, label: "<%=entry.getKey()%>" },
                    <%}else{%>
                    {y: <%=entry.getValue()%>, label: "<%=entry.getKey()%>"}
                    <%}}%>

                ]
            }]
        });
        chart.render();
        chart2.render();
        chart3.render();
        chart4.render();


        function explodePie (e) {
            if(typeof (e.dataSeries.dataPoints[e.dataPointIndex].exploded) === "undefined" || !e.dataSeries.dataPoints[e.dataPointIndex].exploded) {
                e.dataSeries.dataPoints[e.dataPointIndex].exploded = true;
            } else {
                e.dataSeries.dataPoints[e.dataPointIndex].exploded = false;
            }
            e.chart.render();

        }
        function toggleDataSeries(e) {
            if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                e.dataSeries.visible = false;
            } else {
                e.dataSeries.visible = true;
            }
            chart3.render();
        }
    }
    //Animate my counter from 0 to set number (6)


</script>
<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <<form action="${pageContext.request.contextPath}/Account_Pages/DepartmentLogin" method="post" name="secureForm" id="dashboard_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
        <input type="hidden" name="departmentName" value='<%=request.getAttribute("departmentFullName")%>'>
        <input type="hidden" name="process" value="afterLogin">
    </form>
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="#" onclick="document.getElementById('dashboard_form').submit();">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Department</div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">


        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading -->
        <div class="sidebar-heading">
            Menu
        </div>
        <form action="${pageContext.request.contextPath}/DepartmentPersonalInfo" method="post" name="secureForm" id="profile_form">
            <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
            <input type="hidden" name="departmentName" value='<%=request.getAttribute("departmentFullName")%>'>
        </form>
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="document.getElementById('profile_form').submit();">
                <i class="fas fa-fw fa-table"></i>
                <span>My Profile</span></a>
        </li>

        <form action="${pageContext.request.contextPath}/DepartmentDepartment" method="post" name="secureForm" id="Department_form">
            <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
            <input type="hidden" name="departmentName" value='<%=request.getAttribute("departmentFullName")%>'>
        </form>
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="document.getElementById('Department_form').submit();">
                <i class="fas fa-fw fa-table"></i>
                <span>Departments</span></a>
        </li>
        <!-- Nav Item - Tables -->

        <!-- Nav Item - Group -->
        <form action="${pageContext.request.contextPath}/DepartmentTeachers" method="post" name="secureForm" id="Group_form">
            <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
            <input type="hidden" name="departmentName" value='<%=request.getAttribute("departmentFullName")%>'>
        </form>
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="document.getElementById('Group_form').submit();">
                <i class="fas fa-fw fa-table"></i>
                <span>Teachers</span></a>
        </li>
        <form action="${pageContext.request.contextPath}/DepartmentGroups" method="post" name="secureForm" id="teacher_form">
            <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
            <input type="hidden" name="departmentName" value='<%=request.getAttribute("departmentFullName")%>'>
        </form>
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="document.getElementById('teacher_form').submit();">
                <i class="fas fa-fw fa-table"></i>
                <span>Groups</span></a>
        </li>

        <form action="${pageContext.request.contextPath}/DepartmentAllAnalysis" method="post" name="secureForm" id="analysis_form">
            <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
            <input type="hidden" name="departmentName" value='<%=request.getAttribute("departmentFullName")%>'>
        </form>
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="document.getElementById('analysis_form').submit();">
                <i class="fas fa-fw fa-table"></i>
                <span>Analysis</span></a>
        </li>


        <form action="${pageContext.request.contextPath}/DepartmentMessages" method="post" name="secureForm" id="Messages_form">
            <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
            <input type="hidden" name="departmentName" value='<%=request.getAttribute("departmentFullName")%>'>
            <input type="hidden" name="messageType" value="msg">
            <input type="hidden" name="process" value="view">
        </form>
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="document.getElementById('Messages_form').submit();">
                <i class="fas fa-fw fa-table"></i>
                <span>My Messages</span><span class="badge badge-danger badge-counter"><%=request.getAttribute("msgCount")%></span></a>
        </li>




        <!-- Divider -->
        <hr class="sidebar-divider d-none d-md-block">

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>



                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <!-- Dropdown - Messages -->
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>
                    <div class="topbar-divider d-none d-sm-block"></div>
                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=request.getAttribute("departmentFullName")%></span>
                            <img class="img-profile rounded-circle" src="<%=request.getContextPath()%>/<%=request.getAttribute("profileImg")%>">
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                            <form action="${pageContext.request.contextPath}/ChangePassword" method="post" name="secureForm" id="changePassword_form">
                                <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
                                <input type="hidden" name="teacherName" value='<%=request.getAttribute("departmentFullName")%>'>
                                <input type="hidden" name="userPosition" value="department">
                                <input type="hidden" name="process" value="check">
                            </form>
                            <a class="dropdown-item" href="#" onclick="document.getElementById('changePassword_form').submit();">
                                <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                Change Password
                            </a>
                            <form action="${pageContext.request.contextPath}/ChangeProfile" method="post" name="secureForm" id="changeProfile_form">
                                <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
                                <input type="hidden" name="teacherName" value='<%=request.getAttribute("departmentFullName")%>'>
                                <input type="hidden" name="userPosition" value="department">
                                <input type="hidden" name="process" value="check">
                            </form>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" onclick="document.getElementById('changeProfile_form').submit();">
                                <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                Change Profile
                            </a>
                            <div class="dropdown-divider"></div>

                            <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                Logout
                            </a>
                        </div>
                    </li>

                </ul>

            </nav>

            <!-- End of Topbar -->
            <!-- Begin Page Content -->
            <!-- Begin Page Content -->
            <div class="container-fluid" style="text-align: center">

                <!-- Page Heading -->
                <h1 class="h3 mb-4 text-gray-800">Department</h1>

            </div>

            <!-- /.container-fluid -->
            <%--<object data="your_url_to_pdf" type="application/pdf">
      &lt;%&ndash;         // <embed  src="viewFile.pdf" width="100%" height="100%"/>&ndash;%&gt;
              <iframe src="viewFile.pdf" style="width:600px; height:500px;" frameborder="0"></iframe>
            </object>--%>
            <div style="text-align: center">

                <!-- Area Chart -->
                <div style="display: inline-flex; width: 100%">
                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #ca0f208a!important;">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col-auto">
                                    <i class="fas fa-book-open fa-2x text-gray-300" style="color:#ca0f208a!important;"></i>
                                </div>
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #ca0f208a!important;">Courses</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("coursesCount")%></div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #2190ef8a!important;">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col-auto">
                                    <i class="fas fa-users fa-2x text-gray-300" style="color:#2190ef8a!important;"></i>
                                </div>
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #2190ef8a!important;">Groups</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("groupsCount")%></div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #cc731199!important;">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col-auto">
                                    <i class="fas fa-chalkboard-teacher fa-2x text-gray-300" style="color:#cc731199!important;"></i>
                                </div>
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #cc731199!important;">Teachers</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("teachersCount")%></div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #1ca93099!important;">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col-auto">
                                    <i class="fas fa-calculator fa-2x text-gray-300" style="color:#1ca93099!important;"></i>
                                </div>
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #1ca93099!important;">Total AVG GPA</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("totlaGroupsAVGGpa")%></div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                </div>

                <div id="chartContainer5" style="height: 370px; width: 100%;margin-bottom: 20px;"></div>







                <div id="chartContainer3" style="height: 370px; width: 100%;margin-bottom: 20px;"></div>

            <%--    ////////////////////////////////////////////////////--%>



                <div style="text-align: center">

                    <!-- Area Chart -->
                    <div style="display: inline-flex; width: 100%">
                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #2821ef8a!important;">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col-auto">
                                            <i class="fas fa-mars fa-2x text-gray-300" style="color:#2821ef8a!important;"></i>
                                        </div>
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #2821ef8a!important;">Male</div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("maleCount")%></div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #bd73af8a!important;">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col-auto">
                                            <i class="fas fa-venus fa-2x text-gray-300" style="color:#bd73af8a!important;"></i>
                                        </div>
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #bd73af8a!important;">Female</div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("femaleCount")%></div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #cc731199!important;">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col-auto">
                                            <i class="fas fa-user-graduate fa-2x text-gray-300" style="color:#cc731199!important;"></i>
                                        </div>
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #cc731199!important;">Students</div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("studentsCount")%></div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-xl-3 col-md-6 mb-4">
                            <div class="card border-left-success shadow h-100 py-2" style="border-left: .25rem solid #1ca93099!important;">
                                <div class="card-body">
                                    <div class="row no-gutters align-items-center">
                                        <div class="col-auto">
                                            <i class="fas fa-globe-americas fa-2x text-gray-300" style="color:#1ca93099!important;"></i>
                                        </div>
                                        <div class="col mr-2">
                                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1" style="color: #1ca93099!important;">Nationality</div>
                                            <div class="h5 mb-0 font-weight-bold text-gray-800"><%=request.getAttribute("natCount")%></div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>










                <div style="display: inline-flex; width: 100%">
                    <div>
                        <div id="chartContainer2" style=" height: 370px;float: left; width: 628px;"></div>
                    </div>
                    <div style="width: 100%;float: right;">
                        <div id="chartContainer" style=" height: 370px;float: left; width: 675px;"></div>
                    </div>
                </div>











</div>
























            </div>

                <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Your Website 2019</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                <%--<button class="close" type="button" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">×</span>
                </button>--%>
            </div>
            <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
            <div class="modal-footer">
                <form action="${pageContext.request.contextPath}/LogoutServlet" method="post" name="secureForm" id="logout_form">
                    <input type="hidden" name="id" value='<%=session.getAttribute("departmentSessionID")%>'>
                    <input type="hidden" name="type" value="department">
                </form>
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <a class="btn btn-primary" href="#" onclick="document.getElementById('logout_form').submit();">Logout</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript-->
<script src="<%=request.getContextPath()%>/Account_Pages/vendor/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/Account_Pages/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="<%=request.getContextPath()%>/Account_Pages/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="<%=request.getContextPath()%>/Account_Pages/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="<%=request.getContextPath()%>/Account_Pages/vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="<%=request.getContextPath()%>/Account_Pages/js/demo/chart-area-demo.js"></script>
<script src="<%=request.getContextPath()%>/Account_Pages/js/demo/chart-pie-demo.js"></script>

</body>

</html>
