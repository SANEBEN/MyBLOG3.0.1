$(document).ready(function () {
    var referrer = document.referrer;
    $("#form").submit(function () {
        var email=form.email.value;
        var password=form.Password.value;
        var rememberMe=form.rememberMe.checked;
        console.log(referrer);
        if(email.toString().trim()!=""&&password.toString().trim()!=""){
            $.ajax({
                url: "/Login.do",
                type: "POST",
                data: {
                    "email":email,
                    "password":password,
                    "rememberMe":rememberMe,
                    "referrer":referrer
                },
                dataType: "text",
                success: function (data) {
					var RegUrl = new RegExp(); 
                    RegUrl.compile("[a-zA-z]+://[^\s]*");
					if(RegUrl.test(data)){
						  window.location.href=data;
					}else{
						alert(data);
					}
                },
                error: function (xhr) {
                    alert(xhr.status);
                }
            })
        }else{
            $("#inputPassword").attr("value","").focus();
            alert("用户名或密码不能为空")
        }
        return false;
    })
});