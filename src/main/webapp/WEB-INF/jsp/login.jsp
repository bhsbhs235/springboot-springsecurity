<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/login.css">
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/bootstrap.bundle.min.js"></script>
<script src="resources/js/jquery.slim.min.js"></script>
<html lang="utf-8">
<head>
<meta charset="utf-8">
    <title>Log in with your account</title>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
              <div class="card card-signin my-5">
                <div class="card-body">
                  <h5 class="card-title text-center">Sign In</h5>  
                    <form method="POST" action="/login" class="form-signin">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-label-group">
                            <input id="inputUsername" name="username" type="text" class="form-control" placeholder="Username" required autofocus="true">
                            <label for="inputUsername">Username</label>
                        </div>
                        <div class="form-label-group">
                            <input id="inputPassword" name="password" type="password" class="form-control" placeholder="Password"/>
                            <label for="inputPassword">Password</label>
                            <span>${error}</span>
                            <span>${successMessage}</span>
                        </div>
                        <div class="custom-control custom-checkbox mb-3">
                            <input type="checkbox" class="custom-control-input" id="customCheck1">
                            <label class="custom-control-label" for="customCheck1">Remember password</label>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Sign in</button>
                        <hr class="my-4">
                        <button class="btn btn-lg btn-google btn-block text-uppercase" onclick="location.href='/signup'">Create an account</a></button>
                        <button class="btn btn-lg btn-facebook btn-block text-uppercase" onclick="location.href='/forgot'">Forgot your password?</button>
                    </form>
                </div>
              </div>
            </div>
        </div>
    </div>
</body>
</html>