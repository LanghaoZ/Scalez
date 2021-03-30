function login(){
    $("#loginForm").validate({
        submitHandler:function(form){
            submitLogin();
        }
    });
}
function submitLogin(){

    displayLoadingPage();

    var inputPass = $("#password").val();
    var salt = fixedSalt;
    var str = "" + salt.charAt(2) + salt.charAt(5) + inputPass + salt.charAt(1) + salt.charAt(0);
    var password = md5(str);

    $.ajax({
        url: "/auth/login",
        type: "POST",
        data:{
            phone:$("#phone").val(),
            password: password
        },
        success:function(data){
            layer.closeAll();
            if(data.code == 1200){
                layer.msg("Success");
                window.location.href="/product/index/";
            }else{
                layer.msg(data.message);
            }
        },
        error:function(){
            layer.closeAll();
        }
    });
}

function displayLoadingPage(){
    var idx = layer.msg('Logging in...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;
    return idx;
}
//salt
var fixedSalt="da4Kj32k"