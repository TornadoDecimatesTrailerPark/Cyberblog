/*Chris Yu*/
window.addEventListener('load', function () {
  const SIGNUP_URL = './signUp';
  const MAINPAGE_URL = './index';
  //-- load datapicker--
  let dataPicker = $('#datepicker').datepicker({
    uiLibrary: 'bootstrap4',
    iconsLibrary: 'fontawesome',
    format: 'yyyy-mm-dd'
  });
  //--load end---

  //--confirmation password effect----
  $('#password').focus(function () {
    $('#pwd-confirm-div').removeClass("d-none");
  });
  //-- end---

  // ---use just-validate plugin to validate input---
  if ($('.singUp-form').length > 0) {
    new window.JustValidate('.singUp-form', {
          rules: {
            fname: {
              required: true,
              name: true
            },
            sname: {
              required: true,
              name: true
            },
            email: {
              email: true
            },
            username: {
              required: true,
            },
            password: {
              strength: {
                default: true,
              }
            },
            pwdConfirm: {
              function: (name, value) => {
                let temp = $('#password').val();
                return (value === temp);
              }
            }
          },
          messages: {
            fname: {
              required: 'first name can not be empty',
              minLength: 'first name can not be less than 3 characters'
            },
            sname: {
              required: 'second name can not be empty',
              minLength: 'second name can not be less than 3 characters'
            },
            username: 'username can not be empty',
            email: 'email is not a valid email address',
            pwdConfirm: 'password does not match'
          },
          submitHandler: function (form, values, ajax) {
            getAllSignInfo();
            $.ajax({
              type: "POST",
              url: SIGNUP_URL,
              data: JSON.stringify(signUpInfo),
              dataType: "json",
              beforeSend: function () {
                $("#close-modal-btn").hide();
                $("#modal-show-info").text("Processing...");
                $("#loadingModal").modal("show");
              },
              success: function (response) {

                $("#loadingModal").modal("hide");
                if(response.code === 1){
                  //success signUp
                  $(location).attr('href', MAINPAGE_URL);
                }else {
                  dynamicShowErrorOnModal(response);
                }
              },
              error: function (response) {
                console.info(response);
              }
            });
          },
        }
    );
  }

  if ($('.login-form').length > 0) {
    new window.JustValidate('.login-form', {
      rules: {
        username: {
          required: true
        },
        password: {
          required: true
        }
      },
      messages: {
        username: "username is required",
        password: "password is required"
      }
    });
  }

  // ----validate part end------

  // submit signUp info to back-side
  let signUpInfo = {};
  // $('#register-btn').click(function () {
  //   // use jquery or native javascript querySelector to get info from page
  //   // construct info to a body entity
  //   // use ajax to send request to back-side
  //   // if return success then redirectUrl(or other function)
  //
  // });

  function getAllSignInfo() {
    signUpInfo.firstName = $('#firstName').val();
    signUpInfo.secondName = $('#secondName').val();
    signUpInfo.username = $('#username').val();
    signUpInfo.email = $('#email').val();
    signUpInfo.birthday = dataPicker.value();
    signUpInfo.password = $('#password').val();
    signUpInfo.description = $('#description').val();

  }

  function dynamicShowErrorOnModal(item) {
    $("#modal-show-info").text(item.message);
    $("#close-modal-btn").show();
  }
})
;
