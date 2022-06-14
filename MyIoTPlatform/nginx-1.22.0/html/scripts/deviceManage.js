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
        success: function (result) {
            alertBlur(result.msg)

        },
        error: function (xhr, textStatus, errorThrown) {
            showError("新增用户异常", xhr, textStatus, errorThrown);
        }
    });
    onFindDevice();
}

function deviceAddData() {
    var addData = document.getElementById("txtDeviceAddData").value;
    var deviceId = document.getElementById("setDevice").value;
    $.ajax({
        url: URL_HEAD_DEVICE + "addData",
        type: "post",
        data: { "id": deviceId, "data": addData },
        dataType: "json",
        success: function (result) {
            console.log(result);
            alertBlur(result.msg);

        },
        error: function (xhr, textStatus, errorThrown) {
            showError("新增设备数据错误", xhr, textStatus, errorThrown);
        }
    });
}

function findDeviceData() {
    var id = document.getElementById("setDevice").value;
    var dataSource = document.getElementById("deviceSource").value;
    var begin = document.getElementById("txtBeginDeviceTime").value;
    var end = document.getElementById("txtEndDeviceTime").value;
    var dataSourceURL;
    var period = 0;
    if (dataSource == 0) {
        dataSourceURL = "findLastByDeviceId";
    } else {
        dataSourceURL = "findDataById";
    }

    if(begin!= "" && end!= ""){
        begin = begin + " 00:00:00";
        end = end+ " 24:00:00";
        dataSourceURL = "findListByPeriod";
        console.log(begin+end);
        period = 1;
    }

    $.ajax({
        url: URL_HEAD_DEVICE + dataSourceURL,
        type: "post",
        dataType: "json",
        data: {
            deviceId: id, begin:begin, end:end
        },
        success: function (result) {

            console.log(result);
            var historyBox = document.getElementById("historyBox");
            var dataSource = document.getElementById("deviceSource").value;
            var str = "";
            console.log(dataSource);
            var rs = result.data;
            if (dataSource == 1 || period == 1) {
                for (let i = 0; i < rs.length; i++) {
                    let r = rs[i];
                    if (result.status == 1) {
                        str = str + "\n" + "id[" + r.id + "]   time[" + r.time + "]   value[" + r.value + "]";
                    } else if (result.status == 2) {
                        str = str + "\n" + "id[" + r.id + "]   time[" + r.time + "]   value[" + r.news + "]";
                    } else if (result.status == 3) {
                        str = str + "\n" + "id[" + r.id + "]   time[" + r.time + "]   value[" + r.status + "]";
                    }
                }
            } else {
                if (result.status == 1) {
                    str = str + "\n" + "id[" + rs.id + "]   time[" + rs.time + "]   value[" + rs.value + "]";
                } else if (result.status == 2) {
                    str = str + "\n" + "id[" + rs.id + "]   time[" + rs.time + "]   value[" + rs.news + "]";
                } else if (result.status == 3) {
                    str = str + "\n" + "id[" + rs.id + "]   time[" + rs.time + "]   value[" + rs.status + "]";
                }
            }

            console.log(str);
            historyBox.innerText = historyBox.innerText + str;
            historyBox.scrollTop = historyBox.scrollHeight;
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("查询用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function deleteDeviceAllHistory() {
    var id = document.getElementById("setDevice").value;
    console.log(id);
    $.ajax({
        url: URL_HEAD_DEVICE + "deleteDataById",
        type: "post",
        dataType: "json",
        data: {
            deviceId: id
        },
        success: function (result) {
            if(result.status == 5) {
                document.getElementById("historyBox").innerText = "历史数据";
                alertBlur("设备 " + id + " 历史数据删除完成!");
            } else {
                alertBlur(result.msg)
            }
        },
        error: function (xhr, textStatus, errorThrown) {
            alertBlur("设备 " + id + " 历史数据删除失败!");
            showError("查询用户异常", xhr, textStatus, errorThrown);
        }
    });
}

function getAllDevice() {
    $.ajax({
        url: URL_HEAD_DEVICE + "find",
        type: "get",
        dataType: "json",
        success: function (result) {
            if(result.status != 1){
                alertBlur(result.msg);
                return;
            }
            console.log(result);
            var deviceSelectList = document.getElementById("setDevice");
            var optionStr = "";
            if (result.data != null) {
                for (var i = 0; i < result.data.length; i++) {
                    optionStr = optionStr + '<option value="' + result.data[i].id +
                        '">' + result.data[i].name + ' | ' + result.data[i].id + '</option>'
                }
                deviceSelectList.innerHTML = optionStr;
            }
        },
        error: function (xhr, textStatus, errorThrown) {
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
        success: function (result) {
            if(result.status == 1){
                clearDeviceList();
                findDeviceList(result.data);
            } else {
                alertBlur(result.msg);
            }
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
        url: URL_HEAD_DEVICE + "delete",
        type: "delete",
        data: { "id": id },
        dataType: "json",
        success: function (result) {
            alertBlur(result.msg)
        },
        error: function (xhr, textStatus, errorThrown) {
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
        success: function (result) {
            alertBlur(result.msg)
        },
        error: function (xhr, textStatus, errorThrown) {
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