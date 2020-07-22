window.addEventListener("load", function () {
    function init() {
        const user = document.querySelector("#hdnSession").dataset.value;
        if (user === "") {
          const writenew = document.querySelector("#writeNewButton");
          const myblog = document.querySelector("#myBlogButton");
          const signout = document.querySelector("#signOutButton");
          writenew.style.display = 'none';
          myblog.style.display = 'none';
          signout.style.display = 'none';
        } else {
          const signin = document.querySelector("#signInButton");
          const signup = document.querySelector("#signUpButton");
          signin.style.display = 'none';
          signup.style.display = 'none';
        }
    }
    init();
});
