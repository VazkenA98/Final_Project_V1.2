<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
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


</head>

<body id="page-top">
<script>
  function upload_check()
  {
    var upl = document.getElementById("id_file");


    if(upl.files[0].size > 1024*1024*50)
    {
      alert("File bigger than 50 MB!");
      upl.value = "";
    }
  };
</script>
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
            <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-4 text-gray-800">What's New...?</h1>
          <div class="main_div">
            <%if(request.getAttribute("errorMessage")!=null){%>
            <p style="color: #0f6848;    margin-top: 56px;font-size: 157px;"><%=request.getAttribute("errorMessage")%></p>
            <%}else{%>
            <form class="user" action="SendEmail" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                      <input type="text" name="reciver" value="<%=request.getAttribute("sendTo")%>" class="form-control form-control-user" style="width: 300px; font-size: 16px; color: black;" id="exampleInputReciver" aria-describedby="emailHelp" placeholder="To :" readonly>
                    </div>
                    <div class="form-group">
                      <input type="text" name="subject" class="form-control form-control-user" style="width: 300px; font-size: 16px; color: black;" id="exampleInputSubject" aria-describedby="emailHelp" placeholder="Subject" required>
                    </div>
                    <div class="form-group">
                      <textarea name="content" rows="10" cols="50" maxlength="500" class="form-control form-control-user" style="border-radius: 0px; padding: 7px; font-size: 16px; color: black; " required>
                      </textarea>
                    </div>

                    <input onchange="upload_check()" id="id_file" type="file" name="file" class="file_but" accept=".xlsx,.xls,image/*,.doc, .docx,.ppt, .pptx,.txt,.pdf" multiple="multiple"/>
              <input type="hidden" name="id" value='<%=session.getAttribute("studentSessionID")%>'>
              <input type="hidden" name="studentName" value='<%=request.getAttribute("studentFullName")%>'>
              <input type="text" name="process" value="send" hidden>
              <input type="text" name="reciverCustemID" value="<%=request.getAttribute("reciverCustemID")%>" hidden>
              <input type="text" name="msgType" value="<%=request.getAttribute("msgType")%>" hidden>
              <button type="submit" class="send_but" style="width: 22%;margin-left: 10px; background-color: #4CAF50;border: none;color: white; border-radius: 25px;">Send Messege!</button>
                    <hr>
                    <!-- <a href="index.html" class="btn btn-google btn-user btn-block">
                      <i class="fab fa-google fa-fw"></i> Login with Google
                    </a>
                    <a href="index.html" class="btn btn-facebook btn-user btn-block">
                      <i class="fab fa-facebook-f fa-fw"></i> Login with Facebook
                    </a> -->
                  </form>
            <%}%>
          </div>


        </div>

        <!-- /.container-fluid -->

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
          <%-- <button class="close" type="button" data-dismiss="modal" aria-label="Close">
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
