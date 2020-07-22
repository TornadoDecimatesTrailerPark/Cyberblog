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
        <div class="navButtons">
            <a id="save-btn" style="text-decoration: none" href="#" class="sumbitButton">Save</a>
            <a id="submit-btn" style="text-decoration: none" href="#" class="sumbitButton">Submit</a>
        </div>
    </nav>
</div>
