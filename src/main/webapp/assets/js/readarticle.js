window.addEventListener('load', function () {
  const aId = getUrlParam("aId");
  const uId = $('#hdnSessionUid').attr('data-value');
  $('.counter-type').click(function () {
    let ele = $(this).find('span');
    let type = ele.attr('id');

    $.ajax({
      type: "GET",
      url: `./common?method=incrNum&type=${type}&aId=${aId}`,
      success: function (response) {
        if (response.code === 1) {
          ele.html(response.data);
        } else {
          console.log(response);
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    });
  });

  function getUrlParam(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
  }

  function templateReplyDiv(data) {
    // 一个reply的div
    let templatefrag = `<div class="parent_reply">
                              <div class="reply-user-head">
                                <img src="${data.fromUser.avatarUrl}" alt="" class="img-authorhead">
                                <span class="comment_author">${data.fromUser.userName}</span>
                                <span>to</span>
                                <img src="${data.toUser.avatarUrl}" alt="" class="img-authorhead">
                                <span class="comment_author">${data.toUser.userName}</span>
                                <span class="date">${data.updateTime}</span>
                              </div>`;
    if (uId === '') {
      templatefrag += `<button type="button" class="btn comment_button ml-5 d-none" data-value="${data.fromUser.id}"
                                      data-toggle="collapse" data-target="#cp-${data.id}" aria-expanded="false"
                                      aria-controls="collapseExample">
                                <i class="far fa-comments"></i></button>`;
    } else {
      templatefrag += `<button type="button" class="btn comment_button ml-5" data-value="${data.fromUser.id}"
                                      data-toggle="collapse" data-target="#cp-${data.id}" aria-expanded="false"
                                      aria-controls="collapseExample">
                                <i class="far fa-comments"></i></button>
                       <button type="button" class="btn d-reply_button ml-3" data-value="${data.id}">
                                <i class="far fa-trash-alt"></i></button>`;
    }
    templatefrag += `<p class="comment_content">${data.content}</p>
                              <div class="collapse" id="cp-${data.id}">
                                <textarea class="form-control"  rows="7">
                                </textarea>
                                <div class="d-flex mt-2">
                                  <button class="btn ml-auto reply-comment"><i class="far fa-comment"></i></button>
                                </div>
                              </div>`;

    templatefrag += `<div class="reply-area"></div>`;
    if (data.replyNum !== 0) {
      templatefrag += `<a href="" class="more-reply" data-value="${data.id}">show ${data.replyNum} reply</a>`
    }

    templatefrag += `<hr class="my-3">
                            </div>`;

    return templatefrag;
  }

  function templateDiv(data) {
    let templateComment = `<div class="main_comment" id="c-${data.id}">
                              <img src="${data.userAvatarUrl}" alt="" class="img-authorhead">
                              <span class="comment_author">${data.username}</span>
                              <span class="date">${data.updateTime}</span>`;
    if (uId === '') {
      templateComment += `<button type="button" class="btn comment_button ml-5 d-none" data-value="${data.userId}"
                                          data-toggle="collapse" data-target="#cc-${data.id}" aria-expanded="false"
                                          aria-controls="collapseExample">
                                    <i class="far fa-comments"></i></button>`;
    } else {
      templateComment += `<button type="button" class="btn comment_button ml-5" data-value="${data.userId}"
                                          data-toggle="collapse" data-target="#cc-${data.id}" aria-expanded="false"
                                          aria-controls="collapseExample">
                                    <i class="far fa-comments"></i></button>
                                  <button type="button" class="btn d-comment_button ml-3" data-value="${data.id}">
                                    <i class="far fa-trash-alt"></i></button>`;
    }
    templateComment += `<p class="comment_content">${data.content}</p>
                              <div class="collapse" id="cc-${data.id}">
                                <textarea class="form-control"  rows="7">
                                </textarea>
                                <div class="d-flex mt-2">
                                  <button class="btn ml-auto chat" ><i class="far fa-comment"></i></button>
                                </div>
                              </div>
                              <div class="reply-area"></div>`;
    if (data.replyNumber !== 0) {
      templateComment += `<a href="" class="more-comment-reply" data-value="${data.id}">show ${data.replyNumber} reply</a>`
    }
    templateComment += `<hr class="my-3"></div>`;
    return templateComment;
  }

  $('#submit_comment').click(function () {
    //add new comment for article
    let commentInfo = {};
    //construct
    commentInfo.content = $('#new_comment').val();
    commentInfo.userId = parseInt(uId);
    commentInfo.username = $('#hdnSession').attr('data-value');
    commentInfo.userAvatarUrl = '';
    commentInfo.replyNumber = 0;
    commentInfo.articleId = aId;
    $.ajax({
      type: "POST",
      url: "./addNewComment",
      data: JSON.stringify(commentInfo),
      dataType: "json",
      success: function (response) {
        if (response.code === 1) {
          //clear textarea
          $('#new_comment').val("");
          // dynamic load comment area.
          let html = templateDiv(response.data);
          $('#comment_area').prepend(html);
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    })
  });

  $(document).on('click', '.chat', function () {
    let reply = {};
    reply.fromUser = {};
    reply.toUser = {};
    reply.parent = {};
    let commentArea = $(this).parents('div.main_comment')[0];
    let area = $(commentArea).find('textarea')[0];
    reply.content = $(area).val();
    reply.fromUser.id = uId;
    let tmp = $(commentArea).find('button.comment_button')[0];
    reply.toUser.id = $(tmp).attr('data-value');
    reply.commentId = $(tmp).attr('data-target').slice(4);
    let collapseArea = $(this).parents(`div#cc-${reply.commentId}.collapse`);
    let replyArea = $(this).parents(`div#cc-${reply.commentId}.collapse`).siblings('div.reply-area');
    $.ajax({
      type: "POST",
      url: "./addNewReply",
      data: JSON.stringify(reply),
      dataType: "json",
      success: function (response) {
        if (response.code === 1) {
          //clear textarea
          $(area).val('');
          collapseArea.collapse('hide');
          // dynamic load comment area.
          let html = templateReplyDiv(response.data);
          replyArea.prepend(html);
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    });

  });

  $(document).on('click', '.reply-comment', function () {
    let reply = {};
    reply.fromUser = {};
    reply.toUser = {};
    reply.parent = {};
    let commentArea = $(this).parents('div.main_comment')[0];
    let parentReplyArea = $(this).parents('.parent_reply')[0];
    let area = $(parentReplyArea).find('textarea')[0];
    reply.content = $(area).val();
    reply.fromUser.id = uId;
    let tmp = $(parentReplyArea).find('button.comment_button')[0];
    reply.toUser.id = $(tmp).attr('data-value');
    reply.parent.id = $(tmp).attr('data-target').slice(4);
    reply.commentId = $(commentArea).attr('id').slice(2);
    reply.replyType = 1;
    let collapseArea = $(this).parents(`div#cp-${reply.parent.id}.collapse`);
    let replyArea = collapseArea.siblings('div.reply-area');
    $.ajax({
      type: "POST",
      url: "./addNewReply",
      data: JSON.stringify(reply),
      dataType: "json",
      success: function (response) {
        if (response.code === 1) {
          //clear textarea
          $(area).val('');
          collapseArea.collapse('hide');
          // dynamic load comment area.
          let html = templateReplyDiv(response.data);
          replyArea.prepend(html);
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    });

  });

  $(document).on('click', '.more-comment-reply', function (event) {
    event.preventDefault();
    let self = $(this);
    let commentArea = $(this).parents('div.main_comment')[0];
    let commentId = $(commentArea).find('button.comment_button').attr('data-target').slice(4);
    let replyDisplay = $(commentArea).find('.reply-area');
    $.ajax({
      type: "GET",
      url: `./getReply?cId=${commentId}&rId=`,
      success: function (response) {
        if (response.code === 1) {
          // dynamic load comment area.
          response.data.forEach(function (item) {
            let htmlFrag = templateReplyDiv(item);
            replyDisplay.append(htmlFrag);
          });
          $(self).hide();
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    });
  });

  $(document).on('click', '.more-reply', function (event) {
    event.preventDefault();
    let self = $(this);
    let commentArea = $(this).parents('div.parent_reply')[0];
    let replyId = $(commentArea).find('button.comment_button').attr('data-target').slice(4);
    let replyDisplay = $(commentArea).find('.reply-area');
    $.ajax({
      type: "GET",
      url: `./getReply?cId=&rId=${replyId}`,
      success: function (response) {
        if (response.code === 1) {
          // dynamic load comment area.
          response.data.forEach(function (item) {
            let htmlFrag = templateReplyDiv(item);
            replyDisplay.append(htmlFrag);
          });
          $(self).hide();
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    });
  });

  $(document).on('click', '.d-reply_button', function (){
    let rId = $(this).attr('data-value');
    let replyDiv = $(this).parents('.parent_reply')[0];
    // delete reply and all child
    $.ajax({
      type: "GET",
      url: `./deleteComment?cId=&rId=${rId}&aId=${aId}`,
      success: function (response) {
        if (response.code === 1) {
           $(replyDiv).remove();
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    })
  });

  $(document).on('click', '.d-comment_button', function () {
    let cId = $(this).attr('data-value');
    let commentDiv= $(this).parents('.main_comment')[0];
    $.ajax({
      type: "GET",
      url: `./deleteComment?cId=${cId}&rId=&aId=${aId}`,
      success: function (response) {
        if (response.code === 1) {
          $(commentDiv).remove();
        }
      },
      error: function (response) {
        // show error modal
        console.info(response);
      }
    })
  })
});
