<%--
  Created by IntelliJ IDEA.
  User: Chris Yu
  Date: 2020/2/10
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <jsp:include page="include.jsp"/>
    <script src="./assets/js/nav.js"></script>
    <script src="./assets/js/readarticle.js"></script>
    <title>Title</title>
</head>
<body>
<div id="background">
    <jsp:include page="nav.jsp"/>
    <div class="wholePage container">
        <main role="main" class="container">
            <div class="row">
                <div class="col-md-7 offset-1 blog-main">
                    <div class="blog-post" id="post">
                        <h1 class="blog-post-title">${article.title}</h1>
                        <div class="author_img_date">
                            <img src="${article.user.avatarUrl}" alt="" class="img-authorhead">
                            <span class="author">${article.user.f_name}.${article.user.l_name}</span>
                            <img class="the_l_between" src="./assets/picture/l_between_author_and_date.png"
                                 height="30"
                                 width="2px"/>
                            <span class="date">${formtUpdateDate}</span>
                        </div>
                        <div class="blog-post-content my-3">${article.content}</div>
                        <div class="postType">
                          <c:forEach items="${article.tagsExt}" var="tagExt">
                              <span class="label"
                                    style="padding: 4px 7px 4px 7px;border-radius: 50px">#<a>${tagExt.tagName}</a>
                            </span>
                          </c:forEach>

                        </div>
                        <div class="button-bar container">
                        <div class="d-flex justify-content-center">
                          <c:forEach items="${article.counterTypeList}" var="counterItem">
                            <c:if test="${not empty userName || counterItem.dictTypeId != 7 }">
                              <button class="btn btn-primary md-2 counter-type mr-3">${counterItem.dictTypeName}
                                <span class="badge badge-light"
                                      id="${counterItem.dictTypeId}">${counterItem.dictTypeVal}</span>
                              </button>
                            </c:if>
                          </c:forEach>
                        </div>
                      </div>
                        <hr class="my-4">


                    </div>
                    <div class="comment">
                      <div class="" id="comment_area">
                          <c:forEach items="${comments}" var="citem" varStatus="loop">
                            <div class="main_comment" id="c-${citem.id}">
                              <img src="${citem.userAvatarUrl}" alt="" class="img-authorhead">
                              <span class="comment_author">${citem.username}</span>
                              <span class="date">${citem.updateTime}</span>
                              <c:choose>
                                <c:when test="${ empty uid}">
                                  <button type="button" class="btn comment_button ml-5 d-none" data-value="${citem.userId}"
                                          data-toggle="collapse" data-target="#cc-${citem.id}" aria-expanded="false"
                                          aria-controls="collapseExample">
                                    <i class="far fa-comments"></i></button>
                                </c:when>
                                <c:otherwise>
                                  <button type="button" class="btn comment_button ml-5" data-value="${citem.userId}"
                                          data-toggle="collapse" data-target="#cc-${citem.id}" aria-expanded="false"
                                          aria-controls="collapseExample">
                                    <i class="far fa-comments"></i></button>
                                  <button type="button" class="btn d-comment_button ml-3" data-value="${citem.id}">
                                    <i class="far fa-trash-alt"></i></button>
                                </c:otherwise>
                              </c:choose>

                              <p class="comment_content">${citem.content}</p>
                              <div class="collapse" id="cc-${citem.id}">
                                <textarea class="form-control"  rows="7">
                                </textarea>
                                <div class="d-flex mt-2">
                                  <button class="btn ml-auto chat" ><i class="far fa-comment"></i></button>
                                </div>
                              </div>
                              <div class="reply-area"></div>
                              <c:if test="${citem.replyNumber > 0}">
                                <a href="" class="more-comment-reply" data-value="${citem.id}">show ${citem.replyNumber} reply</a>
                              </c:if>
                              <hr class="my-3">
                            </div>
                          </c:forEach>
                        <c:if test="${fn:length(comments) > 5}">
                          <div class="show_more_comment">
                            <a href="" id="expand">Expand more comments</a>
                          </div>
                        </c:if>
                        </div>
                      <div class="write_comment">
                        <c:choose>
                          <c:when test="${bCommented == 1}">
                            <div class="write_comment " id="w-comment-area">
                              <label for="new_comment">Write Comment</label>
                              <textarea class="form-control" id="new_comment" rows="7"></textarea>
                              <div class="d-flex mt-2">
                                <button class="btn btn-primary ml-auto" id="submit_comment">Submit</button>
                              </div>
                            </div>
                          </c:when>
                          <c:otherwise>
                            <div class="alert alert-danger" role="alert">
                               Comment is closed
                            </div>
                          </c:otherwise>
                        </c:choose>
                      </div>
                    </div>
                </div>
                <aside class="col-md-3 offset-1 blog-main" id="mostwatchborder">
                    <div class="rightMyself">
                        <img src="${article.user.avatarUrl}" alt="myself">
                        <p class="rightName">${article.user.f_name}.${article.user.l_name}</p>
                    </div>
                    <div class="head">Other articles</div>
                    <ul class="list-inline m-2 decimal">
                      <c:forEach items="${userArticles}" var="uArticle">
                        <li class="mostwatch_li">
                          <a href="./article?aId=${uArticle.id}" class="mosttop5">${uArticle.title}</a>
                        </li>
                      </c:forEach>

                    </ul>
                </aside><!-- /.blog-sidebar -->
            </div><!-- /.row -->
        </main>
    </div>
</div>
</body>
</html>
