<%@ page import="Beans.Student" %>
<%@ page import="java.util.List" %>
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

<body class="bg-gradient-primary" style="overflow: hidden">
<script>
  var outImage ="avatar";
  function preview_2(obj) {
    if (FileReader) {
      var reader = new FileReader();
      reader.readAsDataURL(obj.files[0]);
      reader.onload = function (e) {
        var image = new Image();
        image.src = e.target.result;
        image.onload = function () {
          document.getElementById(outImage).src = image.src;
          document.getElementById("img_check").value="notNull";
        };
      }
    } else {
      // Not supported
    }
    
  }
  function myFunctionDeleteImg() {
    document.getElementById("fileupload").value = "";
    var image = new Image();
    image.src = "<%=request.getContextPath()%>\\accountImages\\2019-10-24-15-04\\null.jpg";

      document.getElementById("avatar").src = image.src;
      document.getElementById("img_check").value="null";

  }
</script>

  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row" style="height: 647px;">
              <div class="text-center" style="padding-top: 19px;width: 100%;">
                <h1 class="h4 text-gray-900 mb-2">Change Your Personal Information</h1>
              </div>
              <form style="display: flex;" class="user" method="post" enctype='multipart/form-data' action="ChangeProfile" id="changeProfile">
                <input type="text" name="process" value="reset" hidden>
                <input type="text" name="userFullName" value="<%=request.getAttribute("userFullName")%>" hidden>
                <input type="text" name="id" value="<%=request.getAttribute("userID")%>" hidden>
                <input type="text" name="userPosition" value="<%=request.getAttribute("userPosition")%>" hidden>
                <div style="margin-left: 40px;"><label for="fileupload">
                <%if( request.getAttribute("userImg")==null){%>
                <img  style="width: 300px;height: 400px" src="<%=request.getContextPath()%>\accountImages\2019-10-24-15-04\null.jpg" id="avatar">
                <%}else{%>
                <img  style="width: 300px;height: 300px; margin: 47px;" src=<%=request.getContextPath()%>"/<%=request.getAttribute("userImg")%>" id="avatar">
             <%}%>
              </label>
                  <div style="text-align: center">
                    <button style="width: 40%; margin-left: 10px;background-color: #ff4d4d;border: none; color: white; border-radius: 25px;" type="reset" onclick="myFunctionDeleteImg()">delete</button></div>
                 </div>
                <%if(request.getAttribute("errorMsg")!=null){%>
                <h1 style="color: red; font-size: 15px; margin-right: 44px; margin-top: 11px;">
                  <%=request.getAttribute("errorMsg")%></h1>
                <%}%>
          <input class="input2" type="file" name="imgUpload"  accept="image/*" id="fileupload" onChange="preview_2(this);" hidden>

                <input type="text"  name="img_check" id="img_check" value="null" hidden>

              <div class="col-lg-6">
                <div class="p-5">


                    <table style="width: 130%;">
                      <tbody>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Name :</label></th><td style="padding-top: 10px;"><input type="text" value="<%=request.getAttribute("userName")%>" class="form-control form-control-user" name="name" aria-describedby="emailHelp" placeholder="Name" readonly></td>
                      </tr>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Surname :</label></th><td style="padding-top: 10px;"><input type="text" value="<%=request.getAttribute("userSurname")%>" class="form-control form-control-user" name="surname" aria-describedby="emailHelp" placeholder="Sure Name" readonly></td>
                      </tr>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Birth Date :</label></th><td style="padding-top: 10px;"><input type="text" value="<%=request.getAttribute("userBirthday")%>" class="form-control form-control-user" name="gender" aria-describedby="emailHelp" placeholder="Enter Email Address..." readonly></td>
                      </tr>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Nationality :</label></th><td style="padding-top: 10px;"><input type="text" value="<%=request.getAttribute("userNationality")%>" class="form-control form-control-user" name="birth_day" aria-describedby="emailHelp" placeholder="Enter Email Address..." readonly></td>
                      </tr>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Gender :</label></th><td style="padding-top: 10px;"><input type="text" value="<%=request.getAttribute("userGender")%>" class="form-control form-control-user" name="nationality" aria-describedby="emailHelp" placeholder="Enter Email Address..." readonly></td>
                      </tr>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Email :</label></th><td style="padding-top: 10px;"><input type="text" value="<%=request.getAttribute("userEmail")%>" class="form-control form-control-user" name="email" aria-describedby="emailHelp" placeholder="Enter Email Address..." readonly></td>
                      </tr>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Recovery Email :</label></th><td style="padding-top: 10px;">
                        <input type="text" value="<%=request.getAttribute("userRecoveryEmail")%>"
                               class="form-control form-control-user" name="recovery_email" aria-describedby="emailHelp"
                               placeholder="Enter Email Address..." pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"></td>
                      </tr>
                      <tr>
                        <th style="padding-top: 10px;"><label style="margin: auto;">Phone :</label></th><td style="padding-top: 10px;">
                        <input type="tel" value="<%=request.getAttribute("userPhone")%>"
                               class="form-control form-control-user" name="phone" aria-describedby="emailHelp" placeholder="Enter Email Address..."
                               pattern='[\+]\d{3}[\(]\d{2}[\)]\d{6}'></td>

                      </tr>
                        </tbody>
                    </table>


                  <input  style="width: 70%; background-color: #4CAF50; border: none; color: white; border-radius: 25px; padding: 3px;  margin-top: 13px; margin-left: 70px;" type="submit" value= "Reset" />

                </div>
              </div>

              </form>
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
