<%@ page import="com.mysql.fabric.Response" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<html lang="en">


<script >

  var validNavigation = false;

  function wireUpEvents() {
    var dont_confirm_leave = 0;
    var leave_message = "You sure you want to leave ?";

    function goodbye(e) {
      if (!validNavigation) {
        if (dont_confirm_leave !== 1) {
          if (!e) e = window.event;
          e.cancelBubble = true;
          e.returnValue = leave_message;
//e.stopPropagation works in Firefox.
          if (e.stopPropagation) {
            e.stopPropagation();
            e.preventDefault();
          }
//return works for Chrome and Safari
          return leave_message;
        }
      }
    }

    window.onbeforeunload = goodbye;

    document.onkeydown = function () {
      switch (event.keyCode || e.which) {
        case 116 : //F5 button
          validNavigation = true;
        case 114 : //F5 button
          validNavigation = true;
        case 82 : //R button
          if (event.ctrlKey) {
            validNavigation = true;
          }
        case 13 : //Press enter
          validNavigation = true;
      }

    }
// Attach the event click for all links in the page
    $("a").bind("click", function () {
      validNavigation = true;
    });

// Attach the event submit for all forms in the page
    $("form").bind("submit", function () {
      validNavigation = true;
    });

// Attach the event click for all inputs in the page
    $("input[type=submit]").bind("click", function () {
      validNavigation = true;
    });
  }

  // Wire up the events as soon as the DOM tree is ready
  $(document).ready(function () {
    wireUpEvents();
  });
  /*var valid=false;
  function wireUpEvents() {

    if(valid){
      alert("Page Refreshed or Redirected");
    }else{

      window.onbeforeunload = askWhetherToClose;

    }
    function askWhetherToClose(event) {

      if(!valid){

        var msg;

        msg = "You're leaving the page, do you really want to?";
        event = event || window.event;
        event.returnValue = msg;
        return msg;
      }}
    $(document).bind('keypress', function(e) {
      if (e.keyCode == 116){

        // or you can insert some code to check page refresh
        valid = true;
        console.log(valid);

      }
    });
    $("a").bind("click", function() {
//To check redirection using Anchor tags
      valid = true;

    });
  }
  $(document).ready(function() {
    wireUpEvents();

  });
*/
</script>
<head>

  <meta charset="utf-8">
   <%--asi refreshi hamare tebi servlet (message count)--%>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin 2 - Dashboard</title>

  <!-- Custom fonts for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/css/sb-admin-2.min.css" rel="stylesheet">
  <script>
    window.onbeforeunload = function(event) {
      var isOK = confirm("Are you sure to Leave this Page?");
      alert("hello");
      if(isOK)
      {
        // try ajax for update your table

      }

    }


  </script>
</head>

<body id="page-top" onload="test()">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <form action="${pageContext.request.contextPath}/Account_Pages/StudentLogin" method="post" name="secureForm" id="dashboard_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
        <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
        <input type="hidden" name="process" value="afterLogin">
      </form>
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="#" onclick="document.getElementById('dashboard_form').submit();">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Student</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">


      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Menu
      </div>
<form action="${pageContext.request.contextPath}/StudentPersonalInfo" method="post" name="secureForm" id="profile_form">
  <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
  <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
</form>
        <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('profile_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Profile</span></a>
      </li>

      <form action="${pageContext.request.contextPath}/StudentGradesDisplay" method="post" name="secureForm" id="Grades_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
        <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Grades_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Grades</span></a>
      </li>
      <!-- Nav Item - Tables -->

       <!-- Nav Item - Group -->
      <form action="${pageContext.request.contextPath}/StudentGroup" method="post" name="secureForm" id="Group_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
        <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Group_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Group</span></a>
      </li>


      <form action="${pageContext.request.contextPath}/StudentTeachers" method="post" name="secureForm" id="Teacher_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
        <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Teacher_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Teachers</span></a>
      </li>


      <form action="${pageContext.request.contextPath}/StudentMessages" method="post" name="secureForm" id="Messages_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
        <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
        <input type="hidden" name="messageType" value="msg">
        <input type="hidden" name="process" value="view">
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Messages_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Messages</span><span class="badge badge-danger badge-counter"><%=request.getAttribute("msgCount")%></span></a>
      </li>


      <form action="${pageContext.request.contextPath}/StudentMessages" method="post" name="secureForm" id="Homeworks_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
        <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
        <input type="hidden" name="messageType" value="homework">
        <input type="hidden" name="process" value="view">
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Homeworks_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>Home Works</span><span class="badge badge-danger badge-counter"><%=request.getAttribute("homeWorkCount")%></span></a>
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
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=request.getAttribute("studentFullName")%></span>
                <%--<%if( request.getAttribute("profileImg")==null){%>
                <img  class="img-profile rounded-circle" src="https://www.w3schools.com/images/w3schools_green.jpg">
                <%}else{%>
                <img class="img-profile rounded-circle" src="data:image/png;base64,<%=request.getAttribute("profileImg")%>">
                <%}%>--%>
                <img class="img-profile rounded-circle" src="<%=request.getContextPath()%>/<%=request.getAttribute("profileImg")%>">
              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <form action="${pageContext.request.contextPath}/ChangePassword" method="post" name="secureForm" id="changePassword_form">
                  <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
                  <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
                  <input type="hidden" name="userPosition" value="student">
                  <input type="hidden" name="process" value="check">
                </form>
                <a class="dropdown-item" href="#" onclick="document.getElementById('changePassword_form').submit();">
                  <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                  Change Password
                </a>
                <form action="${pageContext.request.contextPath}/ChangeProfile" method="post" name="secureForm" id="changeProfile_form">
                  <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
                  <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
                  <input type="hidden" name="userPosition" value="student">
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
          <h1 class="h3 mb-4 text-gray-800">Schedule</h1>

        </div>

        <!-- /.container-fluid -->
        <%--<object data="your_url_to_pdf" type="application/pdf">
&lt;%&ndash;         // <embed  src="viewFile.pdf" width="100%" height="100%"/>&ndash;%&gt;
          <iframe src="viewFile.pdf" style="width:600px; height:500px;" frameborder="0"></iframe>
        </object>--%>
        <div style="text-align: center">
        <img src="<%=request.getContextPath()%>/<%=request.getAttribute("schedual")%>" alt="images Here" width="1100px" height="500px"/>
        </div>
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
            <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
            <input type="hidden" name="type" value="student">
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
