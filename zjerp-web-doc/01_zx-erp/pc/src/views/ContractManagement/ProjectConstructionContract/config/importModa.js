import React from "react";
export default {
    formItemLayout: {
        labelCol: {
            sm: { span: 6 }
        },
        wrapperCol: {
            sm: { span: 18 }
        }
    },
    tailFormItemLayout: {
        wrapperCol: {
            sm: { offset: 6 }
        },
    },
    btns: [
        {
            label: "开始导入",
            onClick: "bind:startImport",
            field: "startImport",
            type: "primary"
        }
    ],
    formConfig: [
        {
            type: 'select',
            label: '清单类型',
            field: 'inputWorkType',
            placeholder: '请选择',
            required: true,
            allowClear: false,
            initialValue: "1",

            optionData: [
                { label: "公路清单", value: "1" },
                { label: "铁路清单", value: "2" },
                { label: "市政清单", value: "3" },
                { label: "房建清单", value: "4" },
            ]
        },

        {
            type: 'Component',
            label: '导入模板下载',
            field: 'Component',
            dependencies: ["inputWorkType"],
            Component: (arg) => {
                const access_token = arg.loginAndLogoutInfo?.loginInfo?.access_token; 
                const templateAddress = window.configs?.templateAddress;
                const inputWorkType = arg.form.getFieldValue("inputWorkType")
                // http://test.apih5.com:18090/system-server/downloadFile?filePath=import/【工程类合同评审】模块清单导入模板-铁路清单.xls&downName=【工程类合同评审】模块清单导入模板-铁路清单.xls
                const urls = {
                    "1": templateAddress + "【工程类合同评审】模块清单导入模板-公路清单.xls&downName=【工程类合同评审】模块清单导入模板-公路清单.xls",
                    "2": templateAddress + "【工程类合同评审】模块清单导入模板-铁路清单.xls&downName=【工程类合同评审】模块清单导入模板-铁路清单.xls",
                    "3": templateAddress + "【工程类合同评审】模块清单导入模板-市政清单.xls&downName=【工程类合同评审】模块清单导入模板-市政清单.xls",
                    "4": templateAddress + "【工程类合同评审】模块清单导入模板-房建清单.xls&downName=【工程类合同评审】模块清单导入模板-房建清单.xls",
                };

                // 【工程类合同评审】模块清单导入模板-铁路清单.xls
                // 【工程类合同评审】模块清单导入模板-市政清单.xls
                // 【工程类合同评审】模块清单导入模板-公路清单.xls
                // 【工程类合同评审】模块清单导入模板-房建清单.xls
                
                return <div style={{ paddingLeft: "125px" }}><a href={`${urls[inputWorkType]}&access_token=${access_token}`}>导入模板下载</a></div>
            }
        },


        {
            type: 'files',
            label: '导入数据文件',
            field: 'attachmentList',
            required: true,
            fetchConfig: { apiName: 'upload' },
            max: 1,
        },

        // {
        //     type: 'select',
        //     label: '覆盖类型',
        //     field: 'inputWorkType', 
        //     placeholder: '请选择',   
        //     required: true, 
        //     optionData:[
        //         { lable:"当前类型", value:"1" },
        //         { lable:"所有类型", value:"2" }, 
        //     ]
        //  },

    ]
}