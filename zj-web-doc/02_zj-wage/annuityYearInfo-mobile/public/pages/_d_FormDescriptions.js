window.FormDescriptions = {
    field: "FormDescriptions",

    formType: "descriptions",
    descriptionsConfig: {
        column: 4,
        layout: "horizontal",
    },
    tailFormItemLayout: {
        labelCol: {
            sm: { span: 0 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 24 }
        }
    },
    formConfig: [
        {
            type: 'string',
            label: 'id',
            field: 'id',
            placeholder: '请输入',
            initialValue: "123456", 
            hide:true
        },
        {
            type: 'string',
            label: '外面的表单',
            field: 'aaa',
            placeholder: '请输入',
            initialValue: "123456", 
            formItem:true,
            span:12, 
        },
        {
            type: 'string',
            label: '姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名',
            field: 'createUserName',
            placeholder: '请输入',
            initialValue: "测试",
            disabled: true,
        
        },
        {
            type: 'radio',
            label: '性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别',
            field: '性别',
            style: { textAlign: "center" },
            initialValue: "0",
            disabled: true,
            required:true,
            optionData: [{ label: "女",value: "0" },{ label: "男",value: "1" }]
        },
        {
            type: 'date',
            label: '出生年月',
            field: '出生年月',
            placeholder: '请输入',
            initialValue: new Date().getTime(),
            disabled: true
        },
        {
            type: 'string',
            label: '民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族',
            field: '民族民族',
            placeholder: '请输入',
            initialValue: "汉族",
            disabled: true
        },
        {
            type: 'string',
            label: '文化程度',
            field: '文化程度',
            placeholder: '请输入',
        },
        {
            type: 'string',
            label: '婚姻状况',
            field: '婚姻状况',
            placeholder: '请输入',
        },
        {
            type: 'date',
            label: '参加工作<br/>时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;间',
            field: '参加工作时间',
            placeholder: '请输入',
        },
        {
            type: 'string',
            label: '政治面貌',
            field: '政治面貌',
            placeholder: '请输入',
        },
        {
            type: 'string',
            label: '籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯',
            field: '籍贯',
            placeholder: '请输入',
        },
        {
            type: 'string',
            label: '职业资格',
            field: '职业资格',
            placeholder: '请输入',
        },
        {
            type: 'string',
            label: '职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务',
            field: '职务',
            placeholder: '请输入',
        },
        {
            type: 'string',
            label: '职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称',
            field: '职称',
            placeholder: '请输入',
        },
        {
            type: 'string',
            label: '<div style="text-align:center">当前工作<br/>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位</div>',
            field: '当前工作单位',
            placeholder: '请输入',
            span: 2
        },
        {
            type: 'string',
            label: '拟调单位',
            field: '拟调单位',
            placeholder: '请输入',
            span: 2
        },
        // {
        //     type: 'string',
        //     label: '第一学历',
        //     field: 'ddxl',
        //     placeholder: '请输入',
        //     span: 4,
        //     tdStyle: {},
        //     children: {
        //         descriptionsConfig: {
        //             column: 5,
        //             layout: "vertical",
        //         },
        //         formConfig: [
        //             {
        //                 type: 'rangeDate',
        //                 label: '起止时间',
        //                 field: 'qzsj',
        //                 placeholder: '请输入',
        //                 disabled: true,
        //                 initialValue: [1587522893486,1590114894150],
        //                 tdStyle: {
        //                     width: "27%"
        //                 }
        //             },
        //             {
        //                 type: 'string',
        //                 label: '毕业学校',
        //                 field: 'byxx',
        //                 placeholder: '请输入',
        //                 tdStyle: {
        //                     width: "18.9%"
        //                 },
        //             },
        //             {
        //                 type: 'string',
        //                 label: '专业',
        //                 field: 'zy',
        //                 placeholder: '请输入',
        //             },
        //             {
        //                 type: 'string',
        //                 label: '学位',
        //                 field: 'xw',
        //                 placeholder: '请输入',
        //             },
        //             {
        //                 type: 'radio',
        //                 label: '是否统分',
        //                 field: 'tf',
        //                 style: { textAlign: "center" },
        //                 optionData: [{ label: "女",value: "0" },{ label: "男",value: "1" }]
        //             }
        //         ]
        //     }
        // },
        // {
        //     type: 'string',
        //     label: '最高学历',
        //     field: 'zgxl',
        //     placeholder: '请输入',
        //     span: 4,
        //     children: {
        //         descriptionsConfig: {
        //             column: 5,
        //             layout: "vertical",
        //         },
        //         formConfig: [
        //             {
        //                 type: 'rangeDate',
        //                 label: '起止时间',
        //                 field: 'qzsj',
        //                 placeholder: '请输入',
        //                 tdStyle: {
        //                     width: "27%"
        //                 }
        //             },
        //             {
        //                 type: 'string',
        //                 label: '毕业学校',
        //                 field: 'byxx',
        //                 placeholder: '请输入',
        //                 tdStyle: {
        //                     width: "18.9%"
        //                 }
        //             },
        //             {
        //                 type: 'string',
        //                 label: '专业',
        //                 field: 'zy',
        //                 placeholder: '请输入',
        //             },
        //             {
        //                 type: 'string',
        //                 label: '学位',
        //                 field: 'xw',
        //                 placeholder: '请输入',
        //             },
        //             {
        //                 type: 'radio',
        //                 label: '是否统分',
        //                 field: 'tf',
        //                 style: { textAlign: "center" },
        //                 optionData: [{ label: "女",value: "0" },{ label: "男",value: "1" }]
        //             }
        //         ]
        //     }
        // },

    ],

    btns: [
        {
            field: "diy",
            label: "设置值",
            type: 'primary',
            onClick: function (obj) {
                console.log(obj)
                obj.btnCallbackFn.setValues({
                    label: "值被更改",
                    value: null
                })
            }
        },
        {
            field: "diy123",
            label: "获取值",
            type: 'primary',
            // isValidate:false,
            onClick: function (obj) { //此时里面会多一个 response
                console.log(obj.values)
                // console.log('回调values',obj.form.getFieldsValue())
                obj.btnCallbackFn.getValues(true,function (vals) {
                    console.log(vals)
                })
            }
        },

    ]
};
