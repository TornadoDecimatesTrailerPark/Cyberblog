<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="navBar" class="container">
  <input type="hidden" id="hdnSession" data-value="${userName}"/>
  <input type="hidden" id="hdnSessionUid" data-value="${uid}" />
  <nav class="navbar navbar-light">
    <div id="cyberBloggerButton">
      <a href="./index" class="cyberBloggerButtonLabel" style="text-decoration: none">CyberBlogger</a>
    </div>
    <div id="signButton" class="navButtons" style="width: auto; height: auto;">
      <a style="text-decoration: none" href="./writeNew" id="writeNewButton">Write new</a>
      <a style="text-decoration: none" href="./myblog" id="myBlogButton">My blog</a>
      <a style="text-decoration: none" href="./signOut" id="signOutButton">Sign out</a>
      <a style="text-decoration: none" href="./login" id="signInButton">Sign in</a>
      <a style="text-decoration: none" href="./signUp" id="signUpButton">Sign up</a>
    </div>
  </nav>
</div>
