<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
  <jsp:include page="include.jsp"/>
  <script src="./assets/js/signInAndUp.js"></script>
  <script src="./assets/js/validator/just-validate.min.js"></script>
  <title>login</title>
</head>
<body>
<div class="container container-login">
  <div class="d-flex justify-content-center h-100">
    <div class="card">
      <div class="card-header">
        <div class="title">
          <h3>Please sign in.</h3>
          <img src="./assets/images/A4F06EBBA1DE527DC504866C383560B1.png" alt="underline">
        </div>
      </div>
      <div class="card-body">
        <form method="post" action="./login" class="login-form">
          <div class="input-group form-group">
            <div class="input-group-prepend">
               <span class="input-group-text">
                 <i class="fas fa-user"></i>
               </span>
            </div>
            <input id="username" type="text" class="form-control" placeholder="username" name="username"
                   data-validate-field="username">
          </div>
          <div class="input-group form-group">
            <div class="input-group-prepend">
               <span class="input-group-text">
                 <i class="fas fa-key"></i>
               </span>
            </div>
            <input id="password" type="password" class="form-control" placeholder="password" name="password"
                   data-validate-field="password">
          </div>
          <div class="row align-items-center remember">
            <input type="checkbox">Remember Me
          </div>
          <div class="form-group d-flex flex-row-reverse">
            <input type="submit" value="Login" class="btn login_btn">
          </div>
          <c:if test="${not empty errorMsg}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMsg}
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

        </form>
        <div class="mt-2">
          <p style="text-align:center"> OR </p>
          <div id="social-login" class="d-flex justify-content-center">
            <div class="g-signin2" data-width="240" data-height="50" data-longtitle="true"  data-onsuccess="onSignIn">
            </div>
          </div>

        </div>
      </div>
      <div class="card-footer">
        <div class="d-flex justify-content-center links">
          <span class="mr-2">Not a Cyberpalace citizen?</span>
          <a href="./signUp"><span>Join us now</span></a>
        </div>
      </div>
    </div>


  </div>
</div>


</body>
</html>
