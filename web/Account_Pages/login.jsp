<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">


  <title>SB Admin 2 - Login</title>

  <!-- Custom fonts for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="<%=request.getContextPath()%>/Account_Pages/css/sb-admin-2.min.css" rel="stylesheet">
  <script type="text/javascript">
    function myFunction() {
      var x = document.getElementById("exampleInputPassword");
      if (x.type === "password") {
        x.type = "text";
      } else {
        x.type = "password";
      }
    }
    function getFocus() {
      document.getElementById("exampleInputEmail").focus();
    }
    function disableBackButton()
    {
      window.history.forward();
    }
   /* setTimeout("disableBackButton()", 0);
    window.onbeforeunload = function (e) {
      var message = "Are you sure ?";
      var firefox = /Firefox[\/\s](\d+)/.test(navigator.userAgent);
      if (firefox) {
        //Add custom dialog
        //Firefox does not accept window.showModalDialog(), window.alert(), window.confirm(), and window.prompt() furthermore
        var dialog = document.createElement("div");
        document.body.appendChild(dialog);
        dialog.id = "dialog";
        dialog.style.visibility = "hidden";
        dialog.innerHTML = message;
        var left = document.body.clientWidth / 2 - dialog.clientWidth / 2;
        dialog.style.left = left + "px";
        dialog.style.visibility = "visible";
        var shadow = document.createElement("div");
        document.body.appendChild(shadow);
        shadow.id = "shadow";
        //tip with setTimeout
        setTimeout(function () {
          document.body.removeChild(document.getElementById("dialog"));
          document.body.removeChild(document.getElementById("shadow"));
        }, 0);
      }
      return message;
    };

    function myFunction() {

    }*/

    (function (global) {
      if(typeof (global) === "undefined") {
        throw new Error("window is undefined");
      }
      var _hash = "!";
      var noBackPlease = function () {
        global.location.href += "#";
        // making sure we have the fruit available for juice (^__^)
        global.setTimeout(function () {
          global.location.href += "!";
        }, 50);
      };
      global.onhashchange = function () {
        if (global.location.hash !== _hash) {
          global.location.hash = _hash;
        }
      };
      global.onload = function () {
        noBackPlease();
        // disables backspace on page except on input fields and textarea..
        document.body.onkeydown = function (e) {
          var elm = e.target.nodeName.toLowerCase();
          if (e.which === 8 && (elm !== 'input' && elm !== 'textarea')) {
            e.preventDefault();
          }
          // stopping event bubbling up the DOM tree..
          e.stopPropagation();
        };
      }
    })(window);


  </script>
</head>

<body class="bg-gradient-primary" >


  <div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                  </div>

                  <form action="<%=request.getContextPath()%>/Account_Pages/LoginToAccounts" class="user" method="post" style=" width: 391px;">
                    <div class="form-group">
                      <input type="email" name="email" style="width: 343px;" value="${requestScope.email}"  class="form-control form-control-user" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Email Address..." required>
                    </div>
                    <div class="form-group">
                      <input type="text" name="ID" value="${requestScope.id}"  style=" width: 343px;" class="form-control form-control-user" id="exampleInputID" aria-describedby="emailHelp" placeholder="ID" required>
                    </div>
                    <div class="form-group" style="display: flex;">
                      <input type="password" name="password" value="${requestScope.oldPassword}" style=" width: 343px;" class="form-control form-control-user" id="exampleInputPassword" placeholder="Password" required>
                      <div style="margin: auto; "><i class="fas fa-eye" onclick="myFunction()"></i></div>
                    </div>
                    <input type="text" name="process" value="login" hidden>
                    <div style="text-align: center">
                    <input class="login_but" style="margin-right: 44px;background-color: #4CAF50;border: none; color: white; border-radius: 25px; width: 57%;  padding: 7px 32px;  text-align: center;  text-decoration: none;  display: inline-block; font-size: 16px;" type="submit" value= "Login" />
                    </div>

                    <div style="text-align: center">
                    <%if(request.getAttribute("errorMsg")!=null){%>
                    <h1 style="color: red; font-size: 15px; margin-right: 44px; margin-top: 11px;">
                      <%=request.getAttribute("errorMsg")%></h1>
                    <%}%>
                    </div>

                  </form>
                  <div class="text-center">
                    <a class="small" href="forgot-password.html">Forgot Password?</a>
                  </div>
                  <div class="text-center">
                    <a class="small" href="register.html">Request an Account!</a>
                  </div>
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
