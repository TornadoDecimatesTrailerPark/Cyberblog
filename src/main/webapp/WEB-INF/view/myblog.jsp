<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
  <jsp:include page="include.jsp"/>
  <script src="./assets/js/nav.js"></script>
  <script src="./assets/js/myblog.js"></script>
  <title>Title</title>
</head>
<body>
<div class="wholePage">
  <div class="mainInf container">
    <jsp:include page="nav.jsp"/>
    <div class="row">
      <div class="content col-md-6 offset-2">
        <div class="myself">
          <img id="avatarUrl" src="${userObj.avatarUrl}" alt="myself">
          <input type="hidden" id="hBirthday" data-value="${birthDay}" />
          <input type="hidden" id="hfName" data-value="${userObj.f_name}" />
          <input type="hidden" id="hlName" data-value="${userObj.l_name}" />
          <button type="button" class="btn btn-danger ml-5 ml-md-2" id="u-del">Delete</button>
          <button type="button" class="btn btn-primary ml-3 ml-md-1" id="u-modify">Modify</button>
          <p id="myName">${userObj.f_name} ${userObj.l_name}</p>
          <p id="myUserName">${userName}</p>
          <p id="myEmail">${userObj.email}</p>
          <p id="abstract">${userObj.description}</p>
        </div>
        <div id="title">My articles</div>

        <div id="data-container"></div>
        <div id="pagination-container"></div>
      </div>
      <div class="col">
        <div class="personalInf col-md-9 offset-1">
          <div class="rightMyself">
            <img src="${userObj.avatarUrl}" alt="myself">
            <p class="rightName">${userObj.f_name}.${userObj.l_name}</p>
          </div>
          <div class="head">Personal information</div>
          <div class="personalDetails">
            <p>Join date:${createTime}</p>
            <p>Date of birth:${birthDay}</p>
            <p>Description:</p>
            <p>${userObj.description}</p>
          </div>
        </div>
        <%--<div class="follow col-md-9 offset-1">
          <div>
            <div class="followTitle">Following:</div>
            <div>
              <c:forEach items="${userObj.followings}" var="followingItem">
                <div class="followDetails">
                  <img src="${followingItem.followerAvatarUrl}" alt="">
                  <p>${followingItem.followerName}</p>
                </div>
              </c:forEach>
            </div>
            <div class="linkToPage">
              <a href="./following">> All people i am following…</a>
            </div>
          </div>
          <div>
            <div class="followTitle">Followers:</div>
            <div>
              <c:forEach items="${userObj.followers}" var="followerItem">
                <div class="followDetails">
                  <img src="${followerItem.followerAvatarUrl}" alt="">
                  <p>${followerItem.followerName}</p>
                </div>
              </c:forEach>
            </div>
            <div class="linkToPage">
              <a href="./follower" class="follow_link"> All people who followed me…</a>
            </div>
          </div>


        </div>--%>
      </div>
    </div>
  </div>
  <jsp:include page="editProfileModal.jsp" />
</div>
</body>

</html>
