// JavaScript Document

function sendHttpApiWithHelloHeader(target) {
    const content = $("#txtHeaderContent").val()
    $.ajax({
        url: "http://localhost:8080/interceptor/" + target,
        //通过type来判断调用哪个方法
        type: "get",
        headers: { "content": content },
        dataType: "json",
        success: function (result) {
            alert(result.msg);
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("测试hello拦截器", xhr, textStatus, errorThrown);
        }
    });
}

function onSendHttpApiWithHelloHeaderToMethodWithHelloAnnotation() {
    sendHttpApiWithHelloHeader("with_annotation")
}

function onSendHttpApiWithHelloHeaderToMethodWithoutHelloAnnotation() {
    sendHttpApiWithHelloHeader("without_annotation")
}

function onGotoUserModule() {
    window.location.href="login.html";
}