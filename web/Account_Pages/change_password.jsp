<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>SB Admin 2 - Forgot Password</title>

  <!-- Custom fonts for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">
<script>
  function myFunction1() {
    var x = document.getElementById("exampleInputPassword1");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
  }
  function myFunction2() {
    var x = document.getElementById("exampleInputPassword2");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
  }
  function myFunction3() {
    var x = document.getElementById("exampleInputPassword3");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
  }
  var check = function() {
    if (document.getElementById('exampleInputPassword2').value ==
            document.getElementById('exampleInputPassword3').value) {
      document.getElementById('message').style.color = 'green';
      document.getElementById('message').innerHTML = 'matching';
      document.getElementById('resetbut').disabled  = false;
    } else {

      document.getElementById('resetbut').disabled  = true;
      document.getElementById('message').style.color = 'red';
      document.getElementById('message').innerHTML = 'not matching';
    }
  }
</script>
  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-password-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-2">Reset The Password</h1>
                  </div>
                  <form class="user" method="post" action="ChangePassword">
                    <input type="text" name="process" value="reset" >
                    <input type="text" name="userName" value="<%=request.getAttribute("userName")%>" >
                    <input type="text" name="id" value="<%=request.getAttribute("userID")%>" >
                    <input type="text" name="userPosition" value="<%=request.getAttribute("userPosition")%>" >
                    <div class="form-group" style="display: flex;">
                      <input type="password" minlength="8" maxlength="16" size="16"  id="exampleInputPassword1" value="${requestScope.oldPassword}" class="form-control form-control-user" name="oldPassword" aria-describedby="emailHelp" placeholder="Old Password" required>
                      <div style="margin: auto; "><i class="fas fa-eye" style=" margin-left: 10px;" onclick="myFunction1()"></i></div>
                    </div>
                     <div class="form-group" style="display: flex;">
                      <input type="password" onkeyup='check();' minlength="8" maxlength="16" size="16" id="exampleInputPassword2"   class="form-control form-control-user" name="newPassword" aria-describedby="emailHelp" placeholder="New Password" required>
                       <div style="margin: auto; "><i class="fas fa-eye" style=" margin-left: 10px;" onclick="myFunction2()"></i></div>
                    </div>
                     <div class="form-group" style="display: flex;">
                      <input type="password" onkeyup='check();' minlength="8" maxlength="16" size="16" id="exampleInputPassword3" class="form-control form-control-user" name="reNewPassword" aria-describedby="emailHelp" placeholder="Re-Enter New Password" required>
                       <div style="margin: auto; "><i class="fas fa-eye" style=" margin-left: 10px;" onclick="myFunction3()"></i></div>
                    </div>
                    <div style="display: flex; margin-left: 25%;">
                    <input type="submit" id="resetbut" value= "Reset" style="width: 50%; margin-left: 10px;background-color: #4CAF50;border: none; color: white; border-radius: 25px;" disabled/>
                    </div>
                  </form>
                  <hr>
                  <h1 style="color: red;font-size: 15px;margin-left: 130px;" id="message"></h1>
                  <%if(request.getAttribute("errorMsg")!=null){%>
                  <h1 style="color: red;font-size: 15px;margin-left: 32px;">
                    <%=request.getAttribute("errorMsg")%></h1>
                  <%}%>
                </div>
              </div>
            </div>
          </div>
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

</body>

</html>
