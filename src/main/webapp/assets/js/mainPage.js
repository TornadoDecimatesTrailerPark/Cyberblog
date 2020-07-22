window.addEventListener("load", function () {


  // only display 20 blog on mainpage
  let totalPages = 20;
  let pageCounter = {
    pageno: 0,
    size: 5
  };

  let C = 10;//滚动条距离底部的距离
  getMoreArticle(pageCounter);
  $(window).scroll(function () {
    let scrollTop = $(this).scrollTop();
    let scrollHeight = $(document).height();
    let windowHeight = $(this).height();
    let positionValue = (scrollTop + windowHeight) - scrollHeight;
    if (positionValue >= -C) {
      if (pageCounter.pageno + pageCounter.size <= totalPages) {
        getMoreArticle(pageCounter);
        pageCounter.pageno = pageCounter.pageno + pageCounter.size;
      } else {

      }
    }
  });

  function constructBlogPost(data) {
    let blogPostDiv = $("#scroll-blog-div");
    data.forEach(function (item) {
      let date = new Date(item.updateTime).toLocaleDateString("us-nz");
      let template = document.createElement('div');
      template.innerHTML = item.content;
      let contentObj = $(template);
      let srcEle = contentObj.find(".image")[0];
      let imagSrc;
      if (srcEle) {
        imagSrc = srcEle.firstElementChild.getAttribute('src');
      }
      let blogPostTemplate = `<div class="blog-post">
<h2 class="blog-post-title">
<a href="./article?aId=${item.id}" class="title_in_mainpage">${item.title}</a></h2>
                    <div class="author_img_date">
                        <img src="${item.user.avatarUrl}" alt="" class="img-authorhead">
                        <span class="author">${item.user.f_name}.${item.user.l_name}</span>
                        <img class="the_l_between" src="./assets/picture/l_between_author_and_date.png" height="30"
                             width="2px"/>
                        <span class="date">${date}</span>
                    </div>
                    <img class="img-fluid my-3" src="${imagSrc}" />
                    <p class="blog-post-content">${item.excerpt}</p>
                    <hr class="my-4"></div>`;
      blogPostDiv.append(blogPostTemplate);
    })
  }

  function getMoreArticle(pageCounter) {
    $.ajax({
      type: "GET",
      url: `./common?method=getNewestArticlePaged&size=${pageCounter.size}&start=${pageCounter.pageno}`,
      success: function (response) {
        if (response.code === 1) {
          constructBlogPost(response.data);
          pageCounter.pageno = pageCounter.pageno + pageCounter.size;
        } else {
          console.log(response);
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    });
  }
});


