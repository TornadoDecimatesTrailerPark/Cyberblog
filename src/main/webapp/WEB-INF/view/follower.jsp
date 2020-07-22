<%--
  Created by IntelliJ IDEA.
  User: Chris Yu
  Date: 2020/2/10
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <jsp:include page="include.jsp"/>
    <script src="./assets/js/nav.js"></script>
    <title>Follower</title>
</head>
<body>
<div class="wholePage">
    <jsp:include page="nav.jsp"/>
    <div class="mainInf container">
        <div class="row">
            <div class="content col-md-7">
                <div class="myself">
                    <img src="${user.user.avatarUrl}" alt="myself">
                    <p id="myName">Follower</p>
                </div>

                <div class="followers">
                    <c:forEach items="${followers}" var="follower">
                        <div class="oneFollower"><img src="${follower.follower.avatarUrl}" alt="" class="img-thumbnail">
                            <p>${follower.follower.f_name}</p></div>
                    </c:forEach>
                </div>

            </div>
            <div class="col">
                <div class="personalInf col-md-7 offset-3">
                    <div class="rightMyself">
                        <img src="${user.user.avatarUrl}" alt="myself">
                        <p class="rightName">${user.f_name}.${user.l_name}</p>
                    </div>
                    <div class="head">Personal information</div>
                    <div class="personalDetails">
                        <p>Join date: ${user.createTime}</p>
                        <p>Date of birth: ${user.birthday}</p>
                        <p>Description:</p>
                        <p>${user.description}</p>
                    </div>
                </div>
                <div id="hrefOfFollowing"><a href="./following.jsp"> All people I am following</a></div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
