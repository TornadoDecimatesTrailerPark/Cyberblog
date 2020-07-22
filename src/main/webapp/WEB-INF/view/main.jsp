<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <jsp:include page="include.jsp"/>
    <script src="./assets/js/nav.js"></script>
    <script src="./assets/js/mainPage.js"></script>
    <title>Cyber Blogger</title>
</head>
<body>
<div class="wholePage container">
    <jsp:include page="nav.jsp"/>
    <main role="main" class="container">
        <div class="row">
            <div class="col-md-7 offset-1 col-sm-10 blog-main" id="scroll-blog-div">
                <!-- /.blog-post -- load zone-->
            </div>
            <aside class="col-md-3 offset-1 col-sm-4" id="mostwatchborder">
                <h4 id="mostwatched">Most Watched :</h4>
                <hr id="hr-of-mostwatch">
                <ul class="list-inline m-2 decimal">
                  <c:forEach items="${topArticles}" var="article">
                    <li class="mostwatch_li"><a href="./article?aId=${article.id}"
                                                class="mosttop5">${article.title}</a>
                    </li>
                  </c:forEach>
                </ul>
            </aside>
        </div><!-- /.row -->
    </main>
</div>
</body>
</html>
