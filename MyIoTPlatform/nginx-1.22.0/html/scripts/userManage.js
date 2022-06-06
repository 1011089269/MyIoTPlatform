const URL_HEAD = "/api/user/";

function onUserManageLoad() {
    findAllUser();
}

const ITEM_KEY_TOKEN_VALUE = "tokenValue";

function getTokenValue() {
    return window.localStorage.getItem(ITEM_KEY_TOKEN_VALUE);
}

function onAddUser() {
    const userName = $("#txtInsertUserName").val();
    const email = $("#txtInsertEmail").val();
    const password = $("#txtInsertPassword").val();
    const age = Number($("#txtInsertAge").val());
    $.ajax({
        url: URL_HEAD + "add",
        type: "post",
        data: { "name": userName, "password": password, "email": email, "age": age },
        dataType: "json",
        success: function (result) {
            alertBlur(result.msg);
            findAllUser();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("新增用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function onFindUser() {
    const id = Number($("#txtFindId").val());
    const userName = $("#txtFindUserName").val();
    const email = $("#txtFindEmail").val();
    const age = Number($("#txtFindAge").val());
    findUser(id, userName, email, age);
}

function findUser(id, userName, email, age) {
    $.ajax({
        url: URL_HEAD + "find",
        type: "get",
        data: { "id": id, "name": userName, "email": email, "age": age },
        dataType: "json",
        success: function (result) {
            clearUserList();
            findUserList(result.data);
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("查询用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function findAllUser() {
    findUser(0, null, null, 0);
}

function findUserList(users) {
    let builder = new StringBuilder();
    for (let i = 0; i < users.length; i++) {
        let user = users[i];
        builder.clear();
        builder.append("<tr><td>")
        builder.append(user.id);
        builder.append("</td><td>")
        builder.append(user.name);
        builder.append("</td><td>")
        builder.append(user.password);
        builder.append("</td><td>")
        builder.append(user.email);
        builder.append("</td><td>")
        builder.append(user.age);
        builder.append("</td></tr>")
        $("#tbUserList tbody").append(builder.toString());
    }
}

function clearUserList() {
    $("#tbUserList tbody").empty();
}

function onUpdateUser() {
    const id = Number($("#txtUpdateId").val());
    const userName = $("#txtUpdateUserName").val();
    const email = $("#txtUpdateEmail").val();
    const password = $("#txtUpdatePassword").val();
    const age = Number($("#txtUpdateAge").val());
    if (id && !(userName || password || email || age)) {
        alertBlur("请保证更新参数不为空");
        return;
    }
    $.ajax({
        url: URL_HEAD + "update",
        type: "put",
        data: { "id": id, "name": userName, "password": password, "email": email, "age": age },
        dataType: "json",
        success: function (result) {
            alertBlur(result.msg);
            findAllUser();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("更新用户信息异常", xhr, textStatus, errorThrown);
        }
    });
}

function onModifyUser() {
    const userName = $("#txtUpdateUserName").val();
    const email = $("#txtUpdateEmail").val();
    const age = Number($("#txtUpdateAge").val());
    if (id && !(userName || password || email || age)) {
        alertBlur("请保证更新参数不为空");
        return;
    }
    $.ajax({
        url: URL_HEAD + "modify",
        type: "put",
        headers: {
            "tokenValue":getTokenValue(),
            "name": userName,
            "email": email,
            "age": age },
        dataType: "json",
        success: function (result) {
            alertBlur(result.msg);
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("更新用户信息异常", xhr, textStatus, errorThrown);
        }
    });
}

function onDeleteUser() {
    const id = Number($("#txtDeleteId").val());
    const userName = $("#txtDeleteUserName").val();
    const email = $("#txtDeleteEmail").val();
    const age = Number($("#txtDeleteAge").val());
    $.ajax({
        url: URL_HEAD + "delete",
        type: "delete",
        data: { "id": id, "name": userName, "email": email, "age": age },
        dataType: "json",
        success: function (result) {
            alertBlur(result.msg);
            findAllUser();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("删除用户信息异常", xhr, textStatus, errorThrown);
        }
    });
}
function onLoginClick() {
    const userName = $("#txtLoginName").val();
    if (!userName) {
        alertBlur("用户名不得为空");
        return;
    }

    const password = $("#txtLoginPassword").val();
    if (!password) {
        alertBlur("密码不得为空");
        return;
    }

    $.ajax({
        url: URL_HEAD + "login",
        //通过type来判断调用哪个方法
        type: "post",
        data: {
            "name": userName,
            "password": password
        },
        dataType: "json",
        success: function (result) {
            alertBlur(result.msg);
            if (result.status === 1) {
                //跳转到注册页面
                window.location.href = "register.html";
            } else if (result.status === 3) {
                //登录成功，保存token
                document.cookie="tokenValue="+result.data;
                window.localStorage.setItem(ITEM_KEY_TOKEN_VALUE, result.data);
                window.location.href = "userCenter.html";
            }
            //密码错误
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("登录异常", xhr, textStatus, errorThrown);
        }
    });
}

function onRegisterClick() {
    const url = URL_HEAD + "register";
    const userName = $("#txtRegisterUserName").val();
    if (!userName) {
        alertBlur("用户名不得为空");
        return;
    }

    const registerPassword = $("#txtRegisterPassword").val();
    const confirmPassword = $("#txtRegisterConfirmPassword").val();
    if (registerPassword !== confirmPassword) {
        alertBlur("两次密码输入不一致");
        return;
    }

    const email = $("#txtRegisterEmail").val();
    const age = Number($("#txtRegisterAge").val());
    const role = Number($("#selRegisterRole").val());

    // alertBlur("userName: " + userName + ", registerPassword: " + registerPassword + ", email: " + email + ", age: " + age + ", role: " + role);

    $.ajax({
        url: url,
        //通过type来判断调用哪个方法
        type: "post",
        data: { "name": userName, "password": registerPassword, "email": email, "age": age, "role": role },
        dataType: "json",
        success: function (result) {
            alertBlur(result.msg);
            if (result.status === 1) {
                //跳转到登录页面
                window.location.href = "login.html";
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("注册异常", xhr, textStatus, errorThrown);
        }
    });
}

function onLoadUserCenter() {
    const url = URL_HEAD + "login/check";
    $.ajax({
        url: url,
        //通过type来判断调用哪个方法
        type: "post",
        data: { "tokenValue": getTokenValue() },
        dataType: "json",
        success: function (result) {
            $("#lblLoginState").text(result.msg);
            if (result.status === 0) {
                $("#btnLoginAction").text("双击退出登录");
                console.log(1);
            } else {
                window.localStorage.removeItem(ITEM_KEY_TOKEN_VALUE);
                $("#btnLoginAction").text("重新登录");
                console.log(2);
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("注册异常", xhr, textStatus, errorThrown);
        }
    });
}

function onLoginActionClick() {
    const tokenValue = getTokenValue();
    if (tokenValue) {
        logout();
    } else {
        window.location.href = "index.html";
    }
}

function logout() {
    $.ajax({
        url: URL_HEAD + "logout",
        //通过type来判断调用哪个方法
        type: "post",
        data: { "tokenValue": getTokenValue() },
        dataType: "json",
        success: function (result) {
            $("#lblLoginState").text(result.msg);
            $("#btnLoginAction").val("重新登录");
            window.localStorage.removeItem(ITEM_KEY_TOKEN_VALUE);
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("注册异常", xhr, textStatus, errorThrown);
        }
    });
}

function checkResult(result) {
    if (result.msg) {
        alertBlur(result.msg);
    }
    if (result.status === -1) {
        window.location.href = "login.html";
        return false;
    } else if (result.status < -1) {
        return false;
    } else {
        return true;
    }
}

function onModifyUserInfoClick() {
    $.ajax({
        url: URL_HEAD + "modify/info",
        //通过type来判断调用哪个方法
        type: "get",
        headers: { "tokenValue": getTokenValue() },
        dataType: "json",
        success: function (result) {
            if (checkResult(result)) {
                window.location.href = "modifyUserInfo.html";
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("修改用户信息异常", xhr, textStatus, errorThrown);
        }
    });
}

function onManageUserClick() {
    $.ajax({
        url: URL_HEAD + "manage",
        //通过type来判断调用哪个方法
        type: "get",
        headers: { "tokenValue": getTokenValue() },
        dataType: "json",
        success: function (result) {
            if (checkResult(result)) {
                window.location.href = "manageUser.html";
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("管理用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function onVersionUpdateClick() {
    $.ajax({
        url: URL_HEAD + "update/version",
        //通过type来判断调用哪个方法
        type: "get",
        headers: { "tokenValue": getTokenValue() },
        dataType: "json",
        success: function (result) {
            if (checkResult(result)) {
                window.location.href = "updateVersion.html";
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("版本更新异常", xhr, textStatus, errorThrown);
        }
    });
}

function onChangePassword() {
    const userName = $("#txtLoginName").val();
    if (!userName) {
        alert("用户名不得为空");
        return;
    }
    $.ajax({
        url: URL_HEAD + "changepwd",
        //通过type来判断调用哪个方法
        type: "post",
        data: { "name": userName },
        dataType: "json",
        success: function (result) {
            alert(result.msg);
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("版本更新异常", xhr, textStatus, errorThrown);
        }
    });
}

function updatepwd() {
    const oldpassword = $("#txtLoginName").val();
    if (!oldpassword) {
        alert("用户名不得为空");
        return;
    }
    const password1 = $("#txtLoginPassword").val();
    const password2 = $("#txtLoginPassword").val();
    if (password1==password2) {
        alert("密码一致");
        return;
    }
    $.ajax({
        url: URL_HEAD + "updatepwd",
        //通过type来判断调用哪个方法
        type: "post",
        headers: {
            "tokenValue":getTokenValue(),
            "oldpassword": oldpassword,
            "newpassword": password1
        },
        dataType: "json",
        success: function (result) {
            alert(result.msg);

        },
        error: function (xhr, textStatus, errorThrown) {
            showError("密码异常", xhr, textStatus, errorThrown);
        }
    });
}

//跳转页面
function jump(url) {
    window.location.href = url;
}

//模糊背景弹窗方法
//需要搭配 <style> body { transition: all var(--Fast) ease; } </style> 来使用
function alertBlur(str) {
    checkEnter = false;
    setTimeout("document.body.style.filter = 'blur(10px)'", 0);
    setTimeout("alert('" + str + "');", 300); //通过延时来“同时”执行第二个function，否则单击一次执行一次
    setTimeout("document.body.style.filter = 'blur(0px)'", 300);
}