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
        success: function () {
            alertBlur("onAddDevice")
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
            alert("Find")
            clearDeviceList();
            findDeviceList(result.data);
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
        success: function () {
            alertBlur("onDeleteDevice")
            onFindDevice();
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
        success: function () {
            alertBlur(onUpdateDevice)
            onFindDevice();
        }
    });
}

function alertBlur(str) {
    checkEnter = false;
    setTimeout("document.body.style.filter = 'blur(10px)'", 0);
    setTimeout("alert('" + str + "');", 300); //通过延时来“同时”执行第二个function，否则单击一次执行一次
    setTimeout("document.body.style.filter = 'blur(0px)'", 300);
}