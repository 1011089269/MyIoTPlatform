function showError(label, xhr, textStatus, errorThrown) {
    alert(label + "：\n"
        + "状态码：" +xhr.status +"\n"
        + "状态：" +xhr.readyState +"\n"
        + "错误信息：" +xhr.statusText +"\n"
        + "返回响应信息：" +xhr.responseText +"\n"
        + "请求状态：" +textStatus +"\n"
        + "完整异常：" +errorThrown +"\n"
    )
}

function StringBuilder() {
    this.strings = new Array("");
}

StringBuilder.prototype.append = function(value) {
    if (value) {
        this.strings.push(value);
    }
}

StringBuilder.prototype.clear = function () {
    this.strings.length = 0;
}

StringBuilder.prototype.toString = function () {
    return this.strings.join("");
}

function getQueryString(name) {
    const r = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    if(r != null) {
        return decodeURI(r[2]);
    }
    return null;
}