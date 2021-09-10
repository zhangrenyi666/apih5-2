export default {
    formItemLayout: {
        labelCol: { 
            sm: { span: 6 }
        },
        wrapperCol: { 
            sm: { span: 18 }
        }
    },
    tailFormItemLayout:{
        wrapperCol: { 
            sm: { offset: 6 }
        }, 
    },
    btns:[
        {
            label:"开始导入",
            onClick:"bind:startImport",
            field:"startImport",
            type:"primary"
        }
    ],
    formConfig: [
        {
            type: 'select',
            label: '清单类型',
            field: 'inputWorkType',
            placeholder: '请选择',
            required: true,
            optionData: [
                { label: "公路清单", value: "1" },
                { label: "铁路清单", value: "2" },
                { label: "市政清单", value: "3" },
                { label: "房建清单", value: "4" },
            ]
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