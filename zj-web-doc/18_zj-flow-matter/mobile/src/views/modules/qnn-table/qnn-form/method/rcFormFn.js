import { message as Msg } from "antd";
import getDeviceType from "../tool/getDeviceType";
let _isMobile = () => getDeviceType() === "mobile";
//文件上传后转换
const normFile = e => {
    const { fileList } = e;
    let newFileList = fileList.map((item, index) => {
        //当失败时弹出失败信息。但是有时候成功需要弹出信息时也可以将success设置为false
        if (item && item.response && !item.response.success) {
            if (index === fileList.length - 1) {
                Msg.info(item.response.message);
            }
        }
        if (item.response && item.response.success && item.response.data) {
            if (!item.response.data.uid) {
                item.response.data.uid = index;
            }
            const res = item.response.data;
            //移动端上传时候需要将返回的url地址改为mobileUrl
            const obj = {
                ...res,
                url: _isMobile() ? res.mobileUrl : res.url,
                status: "done",
                fileName: res.name || res.fileName,
                fileUrl: res.url || res.fileUrl
            };
            return obj;
        }
        return { ...item };
    });

    return newFileList;
};

//获取类型的验证规则
const getRules = ({ type, required, message, typeMessage }) => {
    switch (type) {
        case "radio":
        case "checkbox":
        case "switch":
        case "rate":
        case "slider":
            return [
                {
                    required: required,
                    message
                }
            ];
        default:
            return [
                {
                    required: required,
                    message
                },
                {
                    type,
                    message: typeMessage
                }
            ];
    }
};

export { normFile, getRules };
