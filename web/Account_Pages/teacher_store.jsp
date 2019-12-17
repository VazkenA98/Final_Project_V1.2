<%@ page import="Beans.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="Beans.TeacherFiles" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<% List<TeacherFiles> storeList = (List<TeacherFiles>) request.getAttribute("teacherFiles");%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin 2 - Blank</title>

  <!-- Custom fonts for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/css/sb-admin-2.min.css" rel="stylesheet">
<style type="text/css" media="screen">

/* The Close Button */



.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
</head>

	

<body id="page-top">
<script>


// When the user clicks the button, open the modal 

function openBox() {
 var modal = document.getElementById("myModal1");
  modal.style.display = "block";
}
function closebox() {
 var modal = document.getElementById("myModal1");
  modal.style.display = "none";
}
function myFunctionSelectAll() {
  var checkboxes = document.getElementsByClassName('checkboxs');
  for(var i=0, n=checkboxes.length;i<n;i++) {
    checkboxes[i].style.display = "block";
    checkboxes[i].checked = true;
  }
  var x = document.getElementById("cancelBut");
  x.style.display="block";
  var x = document.getElementById("deleteBut");
  x.style.display="block";
}
function myFunctionUnSelectAll() {
  var checkboxes = document.getElementsByClassName('checkboxs');
  for(var i=0, n=checkboxes.length;i<n;i++) {
    checkboxes[i].style.display = "block";
    checkboxes[i].checked = false;
  }
  var x = document.getElementById("cancelBut");
  x.style.display="block";
  var x = document.getElementById("deleteBut");
  x.style.display="block";
}
function myFunctionCancelAll() {
  var checkboxes = document.getElementsByClassName('checkboxs');
  for(var i=0, n=checkboxes.length;i<n;i++) {
    checkboxes[i].style.display = "none";
    checkboxes[i].checked = false;
  }
  var x = document.getElementById("cancelBut");
  x.style.display="none";
  var x = document.getElementById("deleteBut");
  x.style.display="none";
}
function myFunction() {
  var input, filter, ul, li, a, i, txtValue;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  ul = document.getElementById("mainStoreDiv");
  li = ul.getElementsByClassName("searchDiv");
  for (i = 0; i < li.length; i++) {
    a = li[i].getElementsByClassName("pSearch")[0];
    txtValue = a.textContent || a.innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      li[i].style.display = "";
    } else {
      li[i].style.display = "none";
    }
  }
}
function upload_check()
{
  var upl = document.getElementById("id_file");


  if(upl.files[0].size > 1024*1024*50)
  {
    alert("File bigger than 50 MB!");
    upl.value = "";
  }
};
// When the user clicks anywhere outside of the modal, close it
</script>
<div id="myModal1" style=" display: none;position: fixed;z-index: 1;padding-top: 100px;left: 0;top: 0;width: 100%;height: 100%; overflow: auto; background-color: rgb(0,0,0); background-color: rgba(0,0,0,0.4);">
  <div style="background-color: #fefefe;margin: auto;padding: 10px;border: 1px solid #888;width: 50%;">
    <span class="close" onclick="closebox()">&times;</span>
    <form action="${pageContext.request.contextPath}/TeacherStore" enctype="multipart/form-data" method="POST" style="display: grid;padding: 25px;">
    <input type="text" name="subject" placeholder="Subject" >
    <input onchange="upload_check()" id="id_file"  type="file" name="filename" accept=".xlsx,.xls,image/*,.doc, .docx,.ppt, .pptx,.txt,.pdf" >
      <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
      <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
      <input type="hidden" name="process" value="upload-file">
    <input type="submit"/>
	</form>
  </div>
</div>
  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <form action="${pageContext.request.contextPath}/Account_Pages/TeacherLogin" method="post" name="secureForm" id="dashboard_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
        <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
        <input type="hidden" name="process" value="afterLogin">
      </form>
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="#" onclick="document.getElementById('dashboard_form').submit();">
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Teacher</div>
      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

   
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Menu
      </div>

      <!-- my profile nav -->
      <form action="${pageContext.request.contextPath}/TeacherPersonalInfo" method="post" name="secureForm" id="profile_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
        <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('profile_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Profile</span></a>
      </li>

      <form action="${pageContext.request.contextPath}/TeacherStore" method="post" name="secureForm" id="Store_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
        <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
        <input type="hidden" name="process" value="check">
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Store_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Store</span></a>
      </li>
      <!-- Nav Item - Tables -->

      <!-- Nav Item - Group -->
      <form action="${pageContext.request.contextPath}/TeacherGroup" method="post" name="secureForm" id="Group_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
        <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Group_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Students</span></a>
      </li>
      <form action="${pageContext.request.contextPath}/TeacherTeachers" method="post" name="secureForm" id="teacher_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
        <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('teacher_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Teachers</span></a>
      </li>




      <form action="${pageContext.request.contextPath}/TeacherMessages" method="post" name="secureForm" id="Messages_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
        <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
        <input type="hidden" name="messageType" value="msg">
        <input type="hidden" name="process" value="view">
      </form>
      <li class="nav-item">
        <a class="nav-link" href="#" onclick="document.getElementById('Messages_form').submit();">
          <i class="fas fa-fw fa-table"></i>
          <span>My Messages</span><span class="badge badge-danger badge-counter"><%=request.getAttribute("msgCount")%></span></a>
      </li>


      <form action="${pageContext.request.contextPath}/TeacherMessages" method="post" name="secureForm" id="Homeworks_form">
        <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
        <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
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
          <div class="input-group" style="width: 250px;">
            <input type="text" id="myInput" onkeyup="myFunction()"  class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
            <div class="input-group-append">
              <button class="btn btn-primary" type="button">
                <i class="fas fa-search fa-sm"></i>
              </button>
            </div>
          </div>
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
                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=request.getAttribute("teacherFullName")%></span>
                <img class="img-profile rounded-circle" src="<%=request.getContextPath()%>/<%=request.getAttribute("profileImg")%>">
              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                <form action="${pageContext.request.contextPath}/ChangePassword" method="post" name="secureForm" id="changePassword_form">
                  <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
                  <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
                  <input type="hidden" name="userPosition" value="teacher">
                  <input type="hidden" name="process" value="check">
                </form>
                <a class="dropdown-item" href="#" onclick="document.getElementById('changePassword_form').submit();">
                  <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                  Change Password
                </a>
                <form action="${pageContext.request.contextPath}/ChangeProfile" method="post" name="secureForm" id="changeProfile_form">
                  <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
                  <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
                  <input type="hidden" name="userPosition" value="teacher">
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
            <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-4 text-gray-800">My Schedule</h1>

        </div>
       <div style="display: flex">
        <button style="width: 11%; margin-left: 10px;background-color: #4CAF50;border: none; color: white; border-radius: 25px;" id="myBtn" onclick="openBox()">Open Modal</button>
         <%if(storeList.size()>0){%>
           <button style="width: 11%; margin-left: 10px;background-color: #4CAF50;border: none; color: white; border-radius: 25px;"  onclick="myFunctionUnSelectAll()">select</button>
           <button style="width: 11%; margin-left: 10px;background-color: #4CAF50;border: none; color: white; border-radius: 25px;"  onclick="myFunctionSelectAll()">select All</button>
           <button style="width: 11%; margin-left: 10px;background-color: goldenrod;border: none; color: white; border-radius: 25px; display: none;"  id= "cancelBut" onclick="myFunctionCancelAll()" style="display: none">Cancel</button>
           <button style="width: 11%; margin-left: 10px;background-color: #ff4d4d;border: none; color: white; border-radius: 25px; display: none";  type="submit" id= "deleteBut"  style="display: none" onclick="document.getElementById('delete_form').submit();">delete</button>

         <%}%>
  		</div>



        <%for(int i=0;i<storeList.size();i++){%>
        <form action="${pageContext.request.contextPath}/Account_Pages/viewFile.jsp" method="post" name="secureForm" id="pdf_form<%=i%>" >
          <input type="hidden" name="pdfPath" value='<%=storeList.get(i).getFilePath()%>'>
        </form>
        <%}%>
        <form method="post" action="TeacherStore" id="delete_form">

          <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
          <input type="hidden" name="teacherName" value='<%=request.getAttribute("teacherFullName")%>'>
          <input type="hidden" name="process" value="delete">
  <div style=" display: grid;grid-template-columns: 230px  230px  230px 230px 230px; grid-gap: 10px;padding: 20px;" id="mainStoreDiv">
  <%for(int i=0;i<storeList.size();i++){%>

    <div  style="display: flex; width: 250px; height: 100px;" class="searchDiv">
      <input class="checkboxs" type="checkbox" name="checkbox"  value="<%=storeList.get(i).getFileID()%>" style="margin-top: 41px;display: none">
  <a href="#" style="display: flex; width: 250px; height: 100px;" onclick="document.getElementById('pdf_form<%=i%>').submit();">
  <div style=" width: 200px; height: 100px;display: flex;">
    <div style="width: 50px;height: 50px;margin-top: 22px;"><i class="fas fa-file" style="font-size: -webkit-xxx-large; color: grey; margin-left: 10px; margin-top: 2px;"></i></div>
    <div style="margin: 35px 0px 0px 6px;width: 140px;word-wrap: break-word;">
    <p class="pSearch"><%=storeList.get(i).getSubject()%></p>
    </div>
  </div>

  </a>
    </div>
  <%}%>
  </form>


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
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <form action="${pageContext.request.contextPath}/LogoutServlet" method="post" name="secureForm" id="logout_form">
            <input type="hidden" name="id" value='<%=session.getAttribute("teacherSessionID")%>'>
            <input type="hidden" name="type" value="teacher">
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