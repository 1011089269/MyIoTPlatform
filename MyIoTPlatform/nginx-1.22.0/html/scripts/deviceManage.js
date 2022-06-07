const URL_HEAD = "/api/device/";
function onDeviceManageLoad() {
    onFindDevice();
}

function onAddDevice() {
    const name = $("#txtInsertDeviceName").val();
    const dataType = $("#dataType").val();
    $.ajax({
        url: URL_HEAD + "add",
        type: "post",
        data: { "name": name, "dataType": dataType},
        dataType: "json",
        success: function (result) {
            onFindDevice();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("新增用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function onFindDevice() {
    $.ajax({
        url: URL_HEAD + "find",
        type: "get",
        data: {},
        dataType: "json",
        success: function (result) {
            clearDeviceList();
            findDeviceList(result.data);
        },
        error: function (xhr, textStatus, errorThrown) {
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
        url: URL_HEAD + "delete",
        type: "delete",
        data: { "id": id },
        dataType: "json",
        success: function (result) {
            onFindDevice();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("删除用户信息异常", xhr, textStatus, errorThrown);
        }
    });
}

function onUpdateDevice() {
    const id = $("#txtUpdateDeviceID").val();
    const name = $("#txtUpdateName").val();
    const dataType = $("#txtUpdateDataType").val();

    $.ajax({
        url: URL_HEAD + "update",
        type: "put",
        data: { "id": id, "name": name, "dataType": dataType},
        dataType: "json",
        success: function (result) {
            onFindDevice();
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("更新用户信息异常", xhr, textStatus, errorThrown);
        }
    });
}