<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
  <jsp:include page="include.jsp" />
  <script src="./assets/js/signInAndUp.js"></script>
  <script src="./assets/js/validator/just-validate.min.js"></script>
  <title>Sign Up</title>
</head>
<body>
<jsp:include page="loadingModal.jsp" />
<div class="container container-login">
  <div class="d-flex justify-content-center h-100">
    <div class="card">
      <div class="card-header">
        <div class="title">&nbsp;
          <h3>Join CyberBlogger</h3>
          <img src="./assets/images/A4F06EBBA1DE527DC504866C383560B1.png" alt="underline">
        </div>
      </div>
      <div class="card-body">
        <form class="singUp-form">
          <div class="form-group row">
            <label for="firstName" class="col-md-3 col-form-label">Full name</label>
            <div class="col-md-4">
              <input type="text" class="form-control cyber-form" name="firstName" placeholder="First name"
                     data-validate-field="fname" id="firstName"/>
            </div>
            <div class="col-md-4">
              <input type="text" class="form-control cyber-form" name="lastName" placeholder="Last name"
                     data-validate-field="sname" id="secondName"/>
            </div>
          </div>

          <div class="form-group row">
            <label for="username" class="col-md-3 col-form-label">Username</label>
            <div class="col-md-5">
              <input id="username" type="text" class="form-control cyber-form" name="username" placeholder="username"
                     data-validate-field="username"/>
            </div>
          </div>

          <div class="form-group row">
            <label for="email" class="col-md-3 col-form-label">Email address</label>
            <div class="col-md-5">
              <input type="text" class="form-control cyber-form" name="email" placeholder="email"
                     data-validate-field="email" id="email"/>
            </div>
          </div>
          <div class="form-group row">
            <label for="password" class="col-md-3 col-form-label">Password</label>
            <div class="col-md-5">
              <input id="password" type="password" class="form-control cyber-form" name="password" placeholder="password"
                     data-validate-field="password"/>
            </div>
          </div>
          <div class="form-group row d-none" id="pwd-confirm-div">
            <label for="pwd-confirm" class="col-md-3 col-form-label">Confirm Password</label>
            <div class="col-md-5">
              <input id="pwd-confirm" type="password" class="form-control cyber-form" name="password"
                     data-validate-field="pwdConfirm"/>
            </div>
          </div>
          <div class="form-group row">
            <label for="datepicker" class="col-md-3 col-form-label">Birthday</label>
            <div class="col-md-5">
              <input id="datepicker" class="cyber-form" width="250"  />
            </div>
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" rows="7"></textarea>
          </div>
          <div class="d-flex flex-row-reverse">
            <input id="register-btn" type="submit" value="Sign Up" class="btn login_btn">
          </div>
        </form>
      </div>
      <div class="card-footer">
        <div class="d-flex justify-content-center links">
          <span class="mr-2">Alreay a Cyberpalace citizen?</span>
          <a href="./login"><span>Sign in instead</span></a>
        </div>
      </div>
    </div>

  </div>
</div>
</body>
</html>
