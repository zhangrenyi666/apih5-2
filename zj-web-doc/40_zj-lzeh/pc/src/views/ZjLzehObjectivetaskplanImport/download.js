/*
post请求下载文件
@params 地址
@params 参数
@params header头
@params 成功回调
@params 失败回调
*/

import { message as Msg } from "antd";
import moment from 'moment';
export default (_URL,data = {},_myHeaders = {},successCb,errorCb) => {
    const myHeaders = new Headers({
        Accept: "application/json",
        "Content-Type": "application/json;charset=utf-8",
        "X-Requested-With": "XMLHttpRequest",
        // "Content-Disposition": "attachment; filename=filename.excel",
        ..._myHeaders
    });
    const myInit = {
        method: "POST",
        headers: myHeaders,
        mode: "cors",
        cache: "default",
        body: JSON.stringify(data)
    };
    const myRequest = new Request(_URL,myInit);
    let name;
    Msg.loading('loading...',0);
    fetch(myRequest)
        .then(response => {
            const nameInfo = response.headers.get("Content-Disposition");
            Msg.destroy()
            name = `${data.fileName}.xlsx`;
            return response.blob();
        })
        .then(blob => {
            var a = document.createElement("a");
            var url = window.URL.createObjectURL(blob);
            var filename = name;
            a.href = url;
            a.download = filename;
            a.click();
            window.URL.revokeObjectURL(url);
            if (successCb) {
                successCb();
            }
        })
        .catch(error => {
            if (errorCb) {
                errorCb(error);
            }
            console.log("catch",error);
        });
};
