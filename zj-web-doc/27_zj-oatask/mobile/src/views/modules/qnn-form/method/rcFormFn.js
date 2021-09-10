import { message as Msg } from "antd";
import getDeviceType from "../tool/getDeviceType";
let _isMobile = () => getDeviceType() === "mobile";
//文件上传后转换
const normFile = e => {
    const { fileList } = e;
    let newFileList = fileList.map((item,index) => {
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
const getRules = ({ type,required,message,typeMessage }) => {
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
        case "selectByPaging":
            return [
                {
                    required: required,
                    message
                }
            ];
        case "identity":
            return [
                {
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/),
                    message: "请输入正确的身份证号码格式"
                }
            ];

        // 军官证或士兵证
        case "officers":
            return [
                {
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/^[a-zA-Z0-9]{7,21}$/),
                    message: "证件号码格式错误"
                }
            ];

        // 警官证
        case "officersJg":
            return [
                {
                    required: required,
                    message
                },
                {
                    // pattern: new RegExp(/^[a-zA-Z0-9]{7,21}$/),
                    type: "string",
                    message: "证件号码格式错误"
                }
            ];

        // 台湾居民身份证
        case "taiWanIdentity":
            return [
                {
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/^[a-zA-Z][0-9]{9}$/),
                    message: "证件号码格式错误"
                }
            ];

        // 香港永久性居民身份证
        case "hongKongPerpetualIdentity":
            return [
                {
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/^((\s?[A-Za-z])|([A-Za-z]{2}))\d{6}(\([0−9aA]\)|[0-9aA])$/),
                    message: "证件号码格式错误"
                }
            ];

        // 护照
        case "passport":
            return [
                {
                    required: required,
                    message
                },
                {
                    // pattern: new RegExp(/^[a-zA-Z0-9]{7,21}$/),
                    // message: "证件号码格式错误"
                    validator: (rule,value,callback) => {
                        if (value) {
                            let reg1 = new RegExp(/^[a-zA-Z0-9]{7,21}$/);
                            let reg2 = new RegExp(/^(P\d{7})|(G\d{8})$/);
                            if (!reg1.test(value) && !reg2.test(value)) {
                                callback('证件号码格式错误')
                            }
                        }
                        // // Note: 必须总是返回一个 callback，否则 validateFieldsAndScroll 无法响应
                        callback()
                    }
                }
            ];

        // 户口本
        case "householdRegister":
            return [
                {
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/^[a-zA-Z0-9]{3,21}$/),
                    message: "证件号码格式错误"
                }
            ];

        // 出生证
        case "birthCertificate":
            return [
                {
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/^[a-zA-Z0-9]{5,21}$/),
                    message: "证件号码格式错误"
                }
            ];

        // 邮政编码
        case "postalCode":
            return [
                {
                    
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/^[0-9][0-9]{3,5}$/),
                    message: "格式错误"
                }
            ];


        //手机和座机
        case "phone":
            return [
                {
                    required: required,
                    message
                },
                {
                    // pattern: new RegExp(/^1(?:3\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\d|9\d)\d{8}$/),
                    // message: "请输入正确的手机号格式"
                    validator: (rule,value,callback) => {
                        // const { getFieldValue } = this.props.form
                        if (value) {
                            // callback('两次输入不一致！')
                            let phoneReg = new RegExp(/^1(?:3\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\d|9\d)\d{8}$/);
                            let specialPlaneReg = new RegExp(/^0\d{2,3}-\d{7,8}$/);
                            if (!phoneReg.test(value) && !specialPlaneReg.test(value)) {
                                callback('请输入正确的格式')
                            }
                        }
                        // // Note: 必须总是返回一个 callback，否则 validateFieldsAndScroll 无法响应
                        callback()
                    }
                }
            ];
        //座机
        case "specialPlane":
            return [
                {
                    required: required,
                    message
                },
                {
                    pattern: new RegExp(/0\d{2,3}-\d{7,8}/),
                    message: "请输入正确的座机号码格式",
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

export { normFile,getRules };
