<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="modal fade" id="editProfileModal" tabindex="-1" role="dialog" aria-labelledby="article_extra"
     aria-hidden="true" data-backdrop="true">
  <div class="modal-dialog modal-lg modal-right modal-notify modal-info" role="document">
    <div class="modal-content general-box-shadow-effect">
      <!--Header-->
      <div class="modal-header bk-color general-box-shadow-effect">
        <p class="heading lead">Modify Profile</p>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true" class="white-text">×</span>
        </button>
      </div>
      <!--Body-->
      <div class="modal-body">
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
      </div>
      <!--Footer-->
      <div class="modal-footer justify-content-center">
        <a type="button" id="submit-M-user-btn" class="btn btn-primary mr-4">Send
          <i class="fa fa-paper-plane ml-1"></i>
        </a>
        <a type="button" class="btn btn-outline-primary" data-dismiss="modal">Cancel</a>
      </div>
    </div>
  </div>
</div>
