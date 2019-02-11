$(document).ready(function () {
    var countdown = 60;

    function settime(obj) { //发送验证码倒计时
        if (countdown == 0) {
            obj.attr('disabled', false);//obj.removeattr("disabled");
            obj.html("免费获取验证码");
            countdown = 60;
            return;
        } else {
            obj.attr('disabled', true);
            obj.html("重新发送(" + countdown + ")");
            countdown--;
        }
        setTimeout(function () {
            settime(obj)
        }, 1000)
    }

    $("#sendVerification").click(function () {
        var phone = form.phone.value;
        var regex = new RegExp("^1(3|4|5|7|8)\\d{9}$");
        if(regex.test(phone)) {
            var obj = $("#sendVerification");
            settime(obj);
            $.ajax({
                url: "/sendVerification",
                data: {
                    "phone": phone
                },
                dataType: "text",
                success: function (data) {
                    alert(data)
                },
                error: function (xhr) {
                    alert("出现错误，错误码为：" + xhr.status)
                }
            })
        }else {
            alert("请输入正确的手机号")
        }
    });
});