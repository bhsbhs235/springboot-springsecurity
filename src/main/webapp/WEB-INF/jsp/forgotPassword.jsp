<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/jquery.min.js"></script>

<html lang="utf-8">
    <head>
        <meta charset="utf-8">
        <title>Forgot Password?</title>
    </head>
    <div class="form-gap"></div>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default">
                <div class="panel-body">
                    <div class="text-center">
                    <h3><i class="fa fa-lock fa-4x"></i></h3>
                    <h2 class="text-center">Forgot Password?</h2>
                    <p>You can reset your password here.</p>
                    <div class="panel-body">
                        <form:form method="post" id="register-form" role="form" autocomplete="off" class="form">
                            <div class="form-group">
                                <div class="input-group">
                                <span class="input-group-addon"><i class="glyphicon glyphicon-envelope color-blue"></i></span>
                                <input id="email" name="email" placeholder="email address" class="form-control"  type="email">
                                </div>
                            </div>
                            <div class="form-group">
                                <input name="recover-submit" class="btn btn-lg btn-primary btn-block" value="Reset Password" type="submit">
                            </div>
                            <input type="hidden" class="hide" name="token" id="token" value=""> 
                        </form:form>
                        <span>${errorMessage}${successMessage}</span>
                    </div>
                    </div>
                </div>
                </div>
            </div>
        </div>
    </div>
</html>
