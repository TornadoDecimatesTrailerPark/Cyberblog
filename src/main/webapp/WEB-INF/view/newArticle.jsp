<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
  <jsp:include page="include.jsp"/>
  <title>Write Blog</title>
  <script src="./assets/js/ckeditor/ckeditor.js"></script>
  <script src="./assets/js/nav.js"></script>
  <script src="./assets/js/editNewArticle.js"></script>
</head>
<body>
<div class="container wholePage">
  <jsp:include page="nav_write.jsp"/>
  <jsp:include page="loadingModal.jsp" />
  <input type="hidden" id="hdArticleId" data-value="${articleId}" />
  <div class="blog-post-content" id="editor">
    <p>This is some sample content.</p>
    <p>test</p>
  </div>
</div>
<div>

  <!-- Modal: submitArticle-->
  <div class="modal fade" id="article_extra_info" tabindex="-1" role="dialog" aria-labelledby="article_extra"
       aria-hidden="true" data-backdrop="true">
    <div class="modal-dialog modal-lg modal-right modal-notify modal-info" role="document">
      <div class="modal-content general-box-shadow-effect">
        <!--Header-->
        <div class="modal-header bk-color general-box-shadow-effect">
          <p class="heading lead">Choose Article Settings</p>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true" class="white-text">×</span>
          </button>
        </div>
        <!--Body-->
        <div class="modal-body">
          <div>
            <p>
              <strong>Add tags for your article</strong>
            </p>
            <div class="container">
              <div class="row pl-4 pr-4" id="tags-row">
              </div>
            </div>
          </div>
          <hr>
          <!-- Radio -->
          <p class="">
            <strong>Permission Setting</strong>
          </p>
          <div class="container">
            <div class="row mb-4" >
              <div class="d-inline col-6">
                <label class="form-check-label mr-1" for="select-comment-status">Comment:</label>
                <select id="select-comment-status" class="selectpicker show-tick" data-style="bk-color">
                  <option value="0">Everyone</option>
                  <option value="1">Only Follower</option>
                  <option value="-1">Disable</option>
                </select>
              </div>
              <div class="d-inline col-6">
                <label class="form-check-label mr-1" for="select-view-type">Privacy:</label>
                <select id="select-view-type" class="selectpicker show-tick" data-style="bk-color">
                  <option value="0">Everyone</option>
                  <option value="1">Only Follower</option>
                  <option value="-1">Disable</option>
                </select>
              </div>
            </div>
            <div class="row mb-4 pl-4 pr-4 justify-content-around">
              <div class="">
                <input class="form-check-input" name="group" type="checkbox" id="check-18-prevent" value="0">
                <label class="form-check-label" for="check-18-prevent">Adult Content</label>
              </div>
              <div class="">
                <input class="form-check-input" name="group" type="checkbox" id="check-original"
                       value="1" checked>
                <label class="form-check-label" for="check-original">Original Article</label>
              </div>
            </div>
          </div>
        </div>
        <!--Footer-->
        <div class="modal-footer justify-content-center">
          <a type="button" id="submit-article-btn" class="btn btn-primary mr-4">Send
            <i class="fa fa-paper-plane ml-1"></i>
          </a>
          <a type="button" class="btn btn-outline-primary" data-dismiss="modal">Cancel</a>
        </div>
      </div>
    </div>
  </div>
  <!-- Modal: modalPoll -->
</div>
</body>
</html>
