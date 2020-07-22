/**
 * Created by Chris on 10/02/20.
 *
 * @author Chris
 */
window.addEventListener('load', function () {
  const SIGNOUT_URL = './signOut';
  let dataPicker = $('#datepicker').datepicker({
    uiLibrary: 'bootstrap4',
    iconsLibrary: 'fontawesome',
    format: 'yyyy-mm-dd'
  });

  let pageSize = 5;
  let uId = $('#hdnSessionUid').attr('data-value');
  let dataContainer = $("data-container");
  $('#pagination-container').pagination({
    dataSource: function(done) {
      $.ajax({
        type: 'GET',
        url: `./common?method=getAllArticleByuId&uId=${uId}`,
        success: function(response) {
          done(response.data);
        }
      });
    },
    locator: 'items',
    totalNumberLocator: function(response) {
      return (Math.ceil(response.data.length / pageSize) + 1) * pageSize;
    },
    pageSize: pageSize,
    ajax: {
      beforeSend: function() {
        $('#data-container').html('Loading data from blogger ...');
      }
    },
    callback: function(data, pagination) {
      let html = template(data);
      $('#data-container').html(html);
    }
  });
  function template(data) {
    let dataHtml = '<div>';
    data.forEach(function (item) {
      let date = new Date(item.updateTime).toLocaleString("us-nz");
      let templateHtml = `<div class="Articles">
          <p class="Subtitle" id="${item.id}"><i class="fas fa-file"></i>
<a href="./modify?aId=${item.id}">${item.title}</a>
 <span>${date}</span>
 <button type="button" class="btn delete-article ml-1"><i class="far fa-trash-alt"></i></button> </p></div>`;
      dataHtml += templateHtml;
    });
    dataHtml += '</div>';
    return dataHtml;
  }

  $(document).on('click', '.delete-article', function (){
    let parent = $(this).parents('.Subtitle')[0];
    let aId = $(parent).attr('id');
    $.ajax({
      type: 'GET',
      url: `./deleteArticle?aId=${aId}`,
      success: function(response) {
       // refresh page
        if (response.code === 1){
          location.reload();
        }
      },
      error: function (response) {
         console.log(response);
      }
    })
  });

  $('#u-del').click(function () {
    $.ajax({
      type: 'GET',
      url: `./common?method=delUserByuId&uId=${uId}`,
      success: function(response) {
        // refresh page
        if (response.code === 1){
          $(location).attr('href', SIGNOUT_URL);
        }
      },
      error: function (response) {
        console.log(response);
      }
    })
  });
  $('#u-modify').click(function () {
    let userinfo = {};
    let userInfoDiv = $('.myself');
    userinfo.f_name = $(userInfoDiv).find('#hfName').attr('data-value');
    userinfo.l_name = $(userInfoDiv).find('#hlName').attr('data-value');
    userinfo.description = $(userInfoDiv).find('#abstract').val();
    userinfo.userName = $(userInfoDiv).find('#userName').val();
    userinfo.birthDay = $(userInfoDiv).find('#hBirthday').attr('data-value');
    userinfo.email = $(userInfoDiv).find('#myEmail').val();
    $('#editProfileModal').modal('show')

  });

  $('#editProfileModal').on('show.bs.modal', function (e) {

  });
});
