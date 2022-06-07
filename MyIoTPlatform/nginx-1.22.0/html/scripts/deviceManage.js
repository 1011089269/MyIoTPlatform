const URL_HEAD_DEVICE = "/api/device/";

function onDeviceManageLoad() {
    onFindDevice();
}



function onAddDevice() {
    const name = $("#txtInsertDeviceName").val();
    const dataType = $("#dataType").val();
    $.ajax({
        url: URL_HEAD_DEVICE + "add",
        type: "post",
        data: { "name": name, "dataType": dataType },
        dataType: "json",
        success: function(result) {
            alertBlur(result)

        },
        error: function(xhr, textStatus, errorThrown) {
            showError("新增用户异常", xhr, textStatus, errorThrown);
        }
    });
    onFindDevice();
}

function getAllDevice() {
    $.ajax({
        url: URL_HEAD_DEVICE + "find",
        type: "get",
        dataType: "json",
        success: function(result) {
            console.log(result);
            var deviceSelectList = document.getElementById("setDevice");
            var optionStr = "";
            if (result.data != null) {
                for (var i = 0; i < result.data.length; i++) {
                    optionStr = optionStr + '<option value="' + i +
                        '">' + result.data[i].name + ' | ' + result.data[i].id + '</option>'
                }
                deviceSelectList.innerHTML = optionStr;
            }
        },
        error: function(xhr, textStatus, errorThrown) {
            showError("查询用户异常", xhr, textStatus, errorThrown);
        }
    });
}


function onFindDevice() {
    $.ajax({
        url: URL_HEAD_DEVICE + "find",
        type: "get",
        data: {},
        dataType: "json",
        success: function(result) {
            clearDeviceList();
            findDeviceList(result.data);
        },
        error: function(xhr, textStatus, errorThrown) {
            showError("查询用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function findDeviceList(devices) {
    let builder = new StringBuilder();
    for (let i = 0; i < devices.length; i++) {
        let device = devices[i];
        builder.clear();
        builder.append("<tr><td>")
        builder.append(device.id);
        builder.append("</td><td>")
        builder.append(device.name);
        builder.append("</td><td>")
        builder.append(device.time);
        builder.append("</td><td>")
        builder.append("在线");
        builder.append("</td><td>")
        builder.append(device.dataType);
        builder.append("</td></tr>")
        $("#tbDeviceList tbody").append(builder.toString());
    }
}

function clearDeviceList() {
    $("#tbDeviceList tbody").empty();
}

function onDeleteDevice() {
    const id = $("#txtDeviceDeleteId").val();
    $.ajax({
        url: URL_HEAD_DEVICE + "delete",
        type: "delete",
        data: { "id": id },
        dataType: "json",
        success: function(result) {
            alertBlur(result)
        },
        error: function(xhr, textStatus, errorThrown) {
            showError("删除用户信息异常", xhr, textStatus, errorThrown);
        }
    });
    onFindDevice();
}

function onUpdateDevice() {
    const id = $("#txtUpdateDeviceID").val();
    const name = $("#txtUpdateName").val();
    const dataType = $("#txtUpdateDataType").val();

    $.ajax({
        url: URL_HEAD_DEVICE + "update",
        type: "put",
        data: { "id": id, "name": name, "dataType": dataType },
        dataType: "json",
        success: function(result) {
            alertBlur(result)
        },
        error: function(xhr, textStatus, errorThrown) {
            showError("更新用户信息异常", xhr, textStatus, errorThrown);
        }
    });
    onFindDevice();
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