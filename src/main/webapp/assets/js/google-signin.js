  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }
  function onLoad() {
    gapi.load('auth2', function() {
      gapi.auth2.init();
    });
  }
  function onSignIn(googleUser) {
    const profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
    console.log('id_token', googleUser.getAuthResponse().id_token);
    const user_id_token = googleUser.getAuthResponse().id_token;
    const redirectUrl = 'login';
    let auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect();

    const form = $('<form hidden action="' + redirectUrl + '" method="post">' +
      '<input type="text" name="id_token" value="' +
      user_id_token + '" />' +
      '</form>');
    $('body').append(form);
    form.submit();
  }
