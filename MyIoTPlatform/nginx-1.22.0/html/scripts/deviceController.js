const URL_HEAD_MQTT = "/api/mqtt/";
const URL_HEAD_DEVICE = "/api/device/";
var DelayTime = 0;
var Limit = 0;
var firstRun = true;

//整秒更新
function secsReport() {
    var time = new Date() //当前时间
    var msecs = time.getMilliseconds() //毫秒

    //下一次整秒更新间隔
    var next = 1000 - msecs
    this.secsReportTimeout = setTimeout(this.secsReport, next)

    if (this.secsReportFirstRun == true) {
        console.log('整秒刷新 初始化校时中......');
        this.secsReportFirstRun = false
    } else {
        // 以下为需要整秒刷新的方法
        updateData();
    }
}

function changeJG() {
    var JG = document.getElementById("JG").value;
    var JGSwitch = document.getElementById("JGSwitch");
    Send(JGSwitch, "{\'DelayTime\':" + JG + "}");
}

function changeYZ() {
    var YZ = document.getElementById("YZ").value;
    var YZSwitch = document.getElementById("YZSwitch");
    Send(YZSwitch, "{\'Limit\':" + YZ + "}");
}

//发送指令
function Send(Button, Cmd) {
    Button.innerText = "命令发送中......";
    $.ajax({
        url: URL_HEAD_MQTT + "cmd",
        type: "post",
        data: {
            sign: Cmd
        },
        dataType: "json",
        success: function (result) {
            Button.innerText = "命令已发送！";
            console.log(true);
        },
        error: function (xhr, textStatus, errorThrown) {
            Button.innerText = "命令发送失败！";
            console.error(xhr, textStatus, errorThrown);
        }
    });
}

function updateData() {
    var id = document.getElementById("setDevice").value;
    $.ajax({
        url: URL_HEAD_DEVICE + "findLastByDeviceId",
        type: "post",
        dataType: "json",
        data: {
            deviceId: id
        },
        success: function (result) {
            var secretCode = result.data.news;
            console.log("updateData", secretCode);
            document.getElementById("WSDtime").innerText = result.data.time;
            document.getElementById("WD").innerText = secretCode.substr(0, 2) + "°C";
            document.getElementById("SD").innerText = secretCode.substr(2, 2) + "%";
            if (secretCode.substr(4, 1) == "0") {
                document.getElementById("LED").innerText = "⚫ 灯灭";
                document.getElementById("LEDSwitch").innerText = "开灯";
                document.getElementById("LEDSwitch").onclick = Function('Send(this,"{\'LedCmd\':1}");');
            } else {
                document.getElementById("LED").innerText = "⚪ 灯亮";
                document.getElementById("LEDSwitch").innerText = "关灯";
                document.getElementById("LEDSwitch").onclick = Function('Send(this,"{\'LedCmd\':0}");');
            }
            if (secretCode.substr(5, 1) == "0") {
                document.getElementById("FAN").innerText = "⚫ 关闭";
                document.getElementById("FANSwitch").innerText = "开风扇";
                document.getElementById("FANSwitch").onclick = Function('Send(this,"{\'Fans\':1}");');
            } else {
                document.getElementById("FAN").innerText = "⚪ 开启";
                document.getElementById("FANSwitch").innerText = "关风扇";
                document.getElementById("FANSwitch").onclick = Function('Send(this,"{\'Fans\':0}");');
            }
            if (DelayTime != parseInt(secretCode.substr(9, 1)) || firstRun == true) {
                DelayTime = parseInt(secretCode.substr(9, 1));
                document.getElementById("JG").value = DelayTime;
                document.getElementById("JGSwitch").innerText = "更改间隔";
                firstRun = false;
            }
            if (Limit != parseInt(secretCode.substr(6, 2)) || firstRun == true) {
                Limit = parseInt(secretCode.substr(6, 2));
                document.getElementById("YZ").value = Limit;
                document.getElementById("YZSwitch").innerText = "更改阈值";
                firstRun = false;
            }
            if (secretCode.substr(8, 1) == "0") {
                document.getElementById("FANMODE").innerText = "⚫ 手动模式";
                document.getElementById("FANMODESwitch").innerText = "切换为自动模式";
                document.getElementById("FANMODESwitch").onclick = Function('Send(this,"{\'AutoMod\':1}");');
            } else {
                document.getElementById("FANMODE").innerText = "⚪ 自动模式";
                document.getElementById("FANMODESwitch").innerText = "切换为手动模式";
                document.getElementById("FANMODESwitch").onclick = Function('Send(this,"{\'AutoMod\':0}");');
            }


        },
        error: function (xhr, textStatus, errorThrown) {
            console.error(xhr, textStatus, errorThrown);
        }
    });
}

function getAllDevice() {
    $.ajax({
        url: URL_HEAD_DEVICE + "find",
        type: "get",
        dataType: "json",
        success: function (result) {
            if (result.status != 1) {
                alertBlur(result.msg);
                return;
            }
            console.log(result.data);
            var deviceSelectList = document.getElementById("setDevice");
            var optionStr = "";
            if (result.data != null) {
                for (var i = 0; i < result.data.length; i++) {
                    if (result.data[i].name.includes("温湿度")) {
                        optionStr = optionStr + '<option value="' + result.data[i].id +
                            '">' + result.data[i].name + ' | ' + result.data[i].id + '</option>'
                    } else {
                        optionStr = optionStr + '<option disabled="disabled" value="' + result.data[i].id +
                            '">' + result.data[i].name + ' | ' + result.data[i].id + '</option>'
                    }
                }
                deviceSelectList.innerHTML = optionStr;
            }
            secsReport(); //启动整秒刷新
        },
        error: function (xhr, textStatus, errorThrown) {
            console.error(xhr, textStatus, errorThrown);
        }
    });
}
