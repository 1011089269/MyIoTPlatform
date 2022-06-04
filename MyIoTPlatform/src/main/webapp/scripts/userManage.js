const URL_HEAD = "http://localhost:8081/user/";

function onUserManageLoad() {
    findAllUser();
}

function onAddUser() {
    const userName = $("#txtInsertUserName").val();
    const email = $("#txtInsertEmail").val();
    const password = $("#txtInsertPassword").val();
    const age = Number($("#txtInsertAge").val());
    $.ajax( {
        url: URL_HEAD +"add",
        type: "post",
        data: {"name": userName, "password":password, "email":email, "age":age},
        dataType: "json",
        success: function (result) {
            alert(result.msg);
            findAllUser();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("新增用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function onFindUser() {
    const id = Number($("txtFindId").val());
    const userName = $("#txtFindUserName").val();
    const email = $("#txtFindEmail").val();
    const age = Number($("#txtFindAge").val());
    findUser(id, userName, email, age);
}

function findUser(id, userName, email, age) {
    $.ajax( {
        url: URL_HEAD +"find",
        type: "get",
        data: {"id":id, "name": userName, "email":email, "age":age},
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
    findUser(0,null,null,0);
}

function findUserList(users) {
    let builder = new StringBuilder();
    for (let i = 0; i<users.length; i++) {
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
    if (id &&!(userName || password || email || age)) {
        alert("请保证更新参数不为空");
        return;
    }
    $.ajax( {
        url: URL_HEAD +"update",
        type: "put",
        data: {"id":id, "name": userName, "password":password, "email":email, "age":age},
        dataType: "json",
        success: function (result) {
            alert(result.msg);
            findAllUser();
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
    $.ajax( {
        url: URL_HEAD +"delete",
        type: "delete",
        data: {"id":id, "name": userName, "email":email, "age":age},
        dataType: "json",
        success: function (result) {
            alert(result.msg);
            findAllUser();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("删除用户信息异常", xhr, textStatus, errorThrown);
        }
    });
}