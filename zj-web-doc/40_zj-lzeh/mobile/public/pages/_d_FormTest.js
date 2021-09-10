
window.FormTest = {
    // fetchConfig: {
    //     apiName: 'getBaseCodeSelect',//可为function 返回必须为string
    //     params: {},
    //     otherParams: {
    //         itemId: "minzhu"
    //     },
    // },
    // initialValues:{
    //     itemName:"函数"

    // },
    // initialValues: {
    //     // halfYear:1767196800000 
    //     linkageOne: "120000",
    //     linkageThree: "120103",
    //     linkageTwo: "120103",
    // },
    formConfig: [
        {
            field: 'radio',
            label: 'radio',
            type: 'radio',
            optionData: [
                { label: "字符串", value: "string" },
                { label: "数字", value: "number" },
                { label: "下拉", value: "select" },
                { label: "文件上传", value: "files" },
            ]
        },
        {
            dependencies: ["radio"],
            type: function (args) {
                return args._formData().radio || "string"
            },
            label: '动态type',
            field: 'dtType',
            optionData: [
                { label: "张三", value: "0" },
                { label: "李四", value: "1" },
            ]
        },
        {
            type: 'halfYear',
            label: '上/下半年选择',
            field: 'halfYear',
            placeholder: '请选择',
            required: true,
            condition: [
                {
                    regex: { radio: "string" },
                    action: 'hide', //disabled, show, hide, function(){}
                },
            ],
        },

        // {
        //     type: 'radio',
        //     label: '是否检索',
        //     field: 'isJianSuo',
        //     // initialValue: "0",
        //     optionData: [//可为function (props)=>array
        //         { label: '否', value: '0' },
        //         { label: '是', value: '1' },
        //     ],
        //     // span:12
        // },
        // {
        //     type: 'string',
        //     label: '姓名111',
        //     field: 'name111',
        //     placeholder: '请输入', 
        //     condition: [
        //         {
        //             regex: {
        //                 isJianSuo: '1',
        //             },
        //             action: ['required', 'unDisabled'],
        //         },
        //         {
        //             regex: {
        //                 isJianSuo: '0',
        //             },
        //             action: ['disabled', 'unRequired'],
        //         },

        //     ]
        // },
        // {
        //     type: 'string',
        //     label: '姓名222',
        //     field: 'name222',
        //     placeholder: '请输入',
        //     condition: [
        //         {
        //             regex: {
        //                 radio: '0',
        //             },
        //             action: 'hide',
        //         },
        //         //条件2
        //         {
        //             regex: {
        //                 isJianSuo: '0',
        //             },
        //             action: 'disabled',
        //         }
        //     ]
        // },
        // {
        //     type: 'string',
        //     label: '年纪',
        //     field: 'age',
        //     placeholder: '请输入',
        //     required: false,
        // },
        // {
        //     type: 'string',
        //     label: '其他',
        //     field: 'other',
        //     placeholder: '请输入',
        //     required: false,
        // },
        // {
        //     // type: 'camera',
        //     // cameraConfig:{  showName:true,},
        //     type: 'files', 
        //     onPreview:"bind:_docFilesByOfficeUrl", 
        //     label: '移动端文件',
        //     field: 'files',
        //     required: false,
        //     fetchConfig: { apiName: window.configs.domain + 'upload' },
        //     max: 999,
        //     initialValue: [
        //         {
        //             mobileThumbUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //             , mobileUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //             , name: "1095.jpg"
        //             , relativeThumbUrl: "upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //             , relativeUrl: "upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //             , size: "39"
        //             , thumbUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //             , type: "image/jpeg"
        //             , uid: ""
        //             , url: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //         },
        //         {
        //             mobileThumbUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BSL0O0TDC8C01AAC00004EE1543C.jpg"
        //             , mobileUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BSL0O0TDC8C01AAC00004EE1543C.jpg"
        //             , name: "微信图片_20190709114516.jpg"
        //             , relativeThumbUrl: "upload/1EM6BSL0O0TDC8C01AAC00004EE1543C.jpg"
        //             , relativeUrl: "upload/1EM6BSL0O0TDC8C01AAC00004EE1543C.jpg"
        //             , size: "51"
        //             , thumbUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BSL0O0TDC8C01AAC00004EE1543C.jpg"
        //             , type: "image/jpeg"
        //             , uid: ""
        //             , url: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BSL0O0TDC8C01AAC00004EE1543C.jpg"
        //         },
        //         {
        //             mobileThumbUrl: "http://office.apih5.com:8088/?furl=46JcKz4JzvskwX_xoONJF2MQ8HUHOVZx4dmlifovwDhYs7Hymt95ztAdCKoQLzY_XHXDckXdxd9D5W4fs3eCk_@c4lvRyqfZrdQ9MTWOzKs="
        //             , mobileUrl: "http://office.apih5.com:8088/?furl=46JcKz4JzvskwX_xoONJF2MQ8HUHOVZx4dmlifovwDhYs7Hymt95ztAdCKoQLzY_XHXDckXdxd9D5W4fs3eCk_@c4lvRyqfZrdQ9MTWOzKs="
        //             , name: "是是是.pdf"
        //             , relativeThumbUrl: "upload/1EM6BTHUB0TEC8C01AAC00006DE81720.pdf"
        //             , relativeUrl: "upload/1EM6BTHUB0TEC8C01AAC00006DE81720.pdf"
        //             , size: "17"
        //             , thumbUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BTHUB0TEC8C01AAC00006DE81720.pdf"
        //             , type: "application/pdf"
        //             , uid: ""
        //             , url: "http://uat.bjzxkj.com:99/apiZt17lw/downloadFile?filePath=upload/1EM6BTHUB0TEC8C01AAC00006DE81720.pdf&downName=%E6%98%AF%E6%98%AF%E6%98%AF.pdf"

        //         }
        //     ]
        // },

        // {
        //     "type": "select",
        //     "field": "linkageOne",
        //     "label": "无限联动一级",

        //     "optionConfig": {
        //         "label": "itemName",
        //         "value": "itemId",
        //         "children": "children"
        //     },
        //     "fetchConfig": {
        //         "apiName": "getBaseCodeTree",
        //         "otherParams": {
        //             "itemId": "xingzhengquhuadaima"
        //         }
        //     },
        //     // "optionConfig": {
        //     //     "label": "label1",
        //     //     "value": "value",
        //     //     linkageFields:{
        //     //         halfYear:"halfYear"
        //     //     }
        //     // },
        //     // "optionData": [
        //     //     {
        //     //         "label1": "阿里巴巴",
        //     //         "value": "alibaba",
        //     //         "ext1": "第一条数据",
        //     //         halfYear:1234567897111,
        //     //         "childrenList": [
        //     //             {
        //     //                 "label1": "淘宝",
        //     //                 "value": "taobao",
        //     //                 "childrenList": [
        //     //                     {
        //     //                         "label1": "天猫",
        //     //                         "value": "tm"
        //     //                     },
        //     //                     {
        //     //                         "label1": "菜鸟",
        //     //                         "value": "cn"
        //     //                     }
        //     //                 ]
        //     //             },
        //     //             {
        //     //                 "label1": "支付宝",
        //     //                 "value": "zhifubao"
        //     //             }
        //     //         ],
        //     //         "childrenList222":[
        //     //             {
        //     //                 "label1": "test001",
        //     //                 "value": "test001"
        //     //             }
        //     //         ]
        //     //     },
        //     //     {
        //     //         "label1": "腾讯",
        //     //         "value": "tx",
        //     //         "ext1": "第二条数据",
        //     //         "childrenList": [
        //     //             {
        //     //                 "label1": "qq",
        //     //                 "value": "qq"
        //     //             },
        //     //             {
        //     //                 "label1": "微信",
        //     //                 "value": "weixin"
        //     //             }
        //     //         ]
        //     //     },
        //     //     {
        //     //         "label1": "百度",
        //     //         "value": "baidu",
        //     //         "ext1": "第三条数据",
        //     //         "childrenList": [
        //     //             {
        //     //                 "label1": "百度云",
        //     //                 "value": "baiduyun"
        //     //             }
        //     //         ]
        //     //     }
        //     // ],
        //     // children: [
        //     //     {
        //     //         "field": "linkageTwo",
        //     //         "optionConfig": {
        //     //             "children": "children"
        //     //         },
        //     //     },
        //     //     {
        //     //         "field": "linkageThree",
        //     //         "optionConfig": {
        //     //             "children": "children"
        //     //         },
        //     //     }
        //     // ]
        // },

        {
            type: 'number',
            label: '数字',
            field: 'number',
            min: 10,
            max: 100,
            //是否强制清除超出范围内的数值
            // autoClearOver: true,
            // 是否为范围输入
            range: true,
            initialValue: [22, 22]
        },
        {
            type: 'number',
            label: '数字',
            field: 'number2',
            min: 10,
            max: 100,
            //是否强制清除超出范围内的数值
            autoClearOver: true,
            required: true
        },

        {
            type: 'string',
            label: '字符串',
            field: 'string',
            placeholder: '请输入',
            required: false,
            labelCanClick: () => {
                return true
            },
            labelClick: () => {
                console.log('label点击')
            }
        },
        // {
        //     type: 'string',
        //     label: '字符串2',
        //     field: 'string2', 
        //     placeholder: '请输入',  
        //     required: false, 
        //  },

        // {
        //     "type": "select",
        //     "field": "linkageTwo",
        //     "label": "无限联动二级",
        //     "showSearch": true,
        //     "fetchConfig": {
        //         "apiName": "getBaseCodeTree",
        //         "otherParams": {
        //             "itemId": "xingzhengquhuadaima"
        //         }
        //     },
        //     // "optionConfig": {
        //     //     "label": "itemName",
        //     //     "value": "itemId",
        //     // },
        //     // "parent": "linkageOne"
        // },

        // {
        //     // 表格下拉
        //     type: 'selectByQnnTable',
        //     label: '下拉表格-单选',
        //     field: 'selectByQnnTable',
        //     initialValue: "自定义输入",
        //     canInput: false,
        //     //用于下拉控件 的输入框显示 选择后的表格数据
        //     optionConfig: {
        //         value: "itemId",
        //         label: "itemName",
        //         children: "children",
        //         // linkageFields:{

        //         // }
        //     },
        //     //qnnTable组件配置
        //     qnnTableConfig: {
        //         antd: {
        //             rowKey: "itemId"
        //         },
        //         // selectType:"checkbox",
        //         // firstRowIsSearch:true,
        //         // fetchConfig: {
        //         //     apiName: "getBaseCodeTree",
        //         //     otherParams: {
        //         //         itemId: "xingzhengquhuadaima"
        //         //     }
        //         // },
        //         fetchConfig: {
        //             apiName: "getBaseCodeSelect",
        //             otherParams: {
        //                 itemId: "minzhu"
        //             }
        //         },
        //         searchBtnsStyle: "inline",
        //         formConfig: [
        //             {
        //                 isInForm: false,
        //                 table: {
        //                     dataIndex: "itemName",
        //                     title: "数据名",
        //                     required: true
        //                 },
        //                 form: {
        //                     type: "string"
        //                 }
        //             },
        //             {
        //                 isInForm: false,
        //                 table: {
        //                     dataIndex: "createUserName",
        //                     title: "创建人名",
        //                 },
        //                 form: {
        //                     type: "string"
        //                 }
        //             },
        //             {
        //                 isInForm: false,
        //                 table: {
        //                     dataIndex: "createTime",
        //                     title: "创建时间",
        //                     format: "YYYY/MM/DD"
        //                 }
        //             }
        //         ]
        //     }
        // },

        // {
        //     // "parent": "linkageTwo",
        //     "parent": "selectByQnnTable",

        //     "type": "select",
        //     "field": "linkageThree",
        //     // "label": "无限联动三级",
        //     "label": "联动二级",
        //     "showSearch": true,
        //     "optionConfig": {
        //         "label": "itemName",
        //         "value": "itemId",
        //     },
        //     // "fetchConfig": {
        //     //     "apiName": "getBaseCodeTree",
        //     //     "otherParams": (props) => {
        //     //         const vals = props.btnCallbackFn.getValues();
        //     //         return {
        //     //             ...vals,
        //     //             "itemId": "xingzhengquhuadaima"
        //     //         }
        //     //     }
        //     // },
        // },

        {
            type: 'qnnTable',
            label: 'qnnTable',
            field: 'qnnTable',
            incToForm: true,
            initialValue: [
                ...Array.from(new Array(13)).map((item, i) => {
                    return {
                        itemId: i + '', 
                    }
                })
            ],
            qnnTableConfig: {
                tableTdEdit: true,
                antd: {
                    rowKey: "itemId"
                },
                actionBtns: [
                    {
                        name: 'addRow',
                        icon: 'plus',
                        type: 'primary',
                        label: '新增行',
                        // addCb:()=>{
                        //     console.log('addcb')
                        // }
                    },
                    {
                        name: 'del',
                        icon: 'del',
                        type: 'primary',
                        label: '删除行',
                    },
                ],
                // selectType:"checkbox",
                // firstRowIsSearch:true,
                // fetchConfig: {
                //     apiName: "getBaseCodeTree",
                //     otherParams: {
                //         itemId: "xingzhengquhuadaima"
                //     }
                // },
                // fetchConfig: {
                //     apiName: "getBaseCodeSelect",
                //     otherParams: {
                //         itemId: "minzhu"
                //     }
                // },
                formConfig: [
                    {
                        // isInSearch: true,
                        isInForm: false,
                        table: {
                            dataIndex: "itemName",
                            title: "数据名",
                            tdEdit: true,
                            fieldConfig: {
                                required: true,
                            }
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        // isInSearch: true,
                        isInForm: false,
                        table: {
                            dataIndex: "createUserName",
                            title: "创建人名",
                            tdEdit: true
                        },
                        form: {
                            type: "string"
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            dataIndex: "createTime",
                            title: "创建时间",
                            format: "YYYY/MM/DD",
                            tdEdit: true
                        },
                        form: {
                            type: "date"
                        }
                    }
                ]
            }
        },


        // {
        //     type: 'qnnTable',
        //     label: 'qnnTable2',
        //     field: 'qnnTable2',
        //     incToForm: true,
        //     //需要注意incToForm情况下 data需要给 initialValue
        //     initialValue: [
        //         { itemId: "0" },
        //         { itemId: "1" },
        //         { itemId: "2" },
        //     ],
        //     qnnTableConfig: {
        //         // tableTdEdit: true,
        //         antd: {
        //             rowKey: "itemId"
        //         },

        //         actionBtns: [
        //             {
        //                 name: 'addRow',
        //                 icon: 'plus',
        //                 type: 'primary',
        //                 label: '新增行',
        //             },
        //             {
        //                 name: 'del',
        //                 icon: 'del',
        //                 type: 'primary',
        //                 label: '删除行',
        //             },
        //         ],
        //         // fetchConfig: {
        //         //     apiName: "getBaseCodeSelect",
        //         //     otherParams: {
        //         //         itemId: "minzhu"
        //         //     }
        //         // },
        //         formConfig: [
        //             {
        //                 isInForm: false,
        //                 table: {
        //                     dataIndex: "addNum1",
        //                     title: "加数1",
        //                     tdEdit: true,
        //                     fieldConfig: {
        //                         type: "number",
        //                         onChange: async (val, formBtnCallbackFn, tableBtnCallbackFn) => {
        //                             //值切换是改变count单元格数据 
        //                             const { addNum1 = 0, addNum2 = 0 } = await tableBtnCallbackFn.getEditedRowData(false);
        //                             // console.log(addNum1 + addNum2)
        //                             tableBtnCallbackFn.setEditedRowData({
        //                                 count: addNum1 + addNum2,
        //                             });
        //                         }
        //                     }
        //                 }
        //             },
        //             {
        //                 isInForm: false,
        //                 table: {
        //                     dataIndex: "addNum2",
        //                     title: "加数2",
        //                     tdEdit: true,
        //                     fieldConfig: {
        //                         type: "number",
        //                         onChange: async (val, formBtnCallbackFn, tableBtnCallbackFn) => {
        //                             //值切换是改变count单元格数据 
        //                             const { addNum1 = 0, addNum2 = 0 } = await tableBtnCallbackFn.getEditedRowData(false);
        //                             // console.log(addNum1 + addNum2)
        //                             tableBtnCallbackFn.setEditedRowData({
        //                                 count: addNum1 + addNum2,
        //                             });
        //                         }
        //                     }
        //                 }
        //             },
        //             {
        //                 isInForm: false,
        //                 table: {
        //                     dataIndex: "count",
        //                     title: "和",
        //                     tdEdit: true,
        //                     fieldConfig: {
        //                         type: "number",
        //                     }
        //                 }
        //             },
        //             // {
        //             //     isInForm: false,
        //             //     table: {
        //             //         dataIndex: "itemName",
        //             //         title: "数据名",
        //             //         tdEdit: true,
        //             //         fieldConfig: {
        //             //             required: true,
        //             //         }
        //             //     },
        //             //     form: {
        //             //         type: "string"
        //             //     }
        //             // },
        //             // { 
        //             //     isInForm: false,
        //             //     table: {
        //             //         dataIndex: "createUserName",
        //             //         title: "创建人名",
        //             //         tdEdit: true
        //             //     },
        //             //     form: {
        //             //         type: "string"
        //             //     }
        //             // },
        //             // {
        //             //     isInForm: false,
        //             //     table: {
        //             //         dataIndex: "createTime",
        //             //         title: "创建时间",
        //             //         format: "YYYY/MM/DD",
        //             //         tdEdit: true
        //             //     },
        //             //     form: {
        //             //         type: "date"
        //             //     }
        //             // }
        //         ]
        //     }
        // },

        // 自定义中嵌套 qnnTable
        // {
        //     type: 'component',
        //     field: 'Table',
        //     Component: "Table"
        // },

        // 自定义中嵌套 qnnForm
        // {
        //     type: 'component',
        //     field: 'Form',
        //     Component: "Form"
        // },


    ],
    btns: [
        {
            field: "diy",
            label: "设置值",
            type: 'primary',
            icon: "testIcon",

            // fetchConfig: {
            //     apiName: "login"
            // },
            onClick: function (obj) { //此时里面会多一个 response
                obj.btnCallbackFn.setValues({
                    halfYear: 1672502400000,
                    number: [100, 50],
                    selectByQnnTable: "自定义输入222",
                    qnnTable: [
                        {
                            createTime: 1610528083094
                            , createUserName: "222"
                            , itemId: "1611564865140_1"
                            , itemName: "222"
                        }
                    ]
                })
            }
        },


        {
            field: "diy",
            label: "获取值",
            type: 'primary',
            icon: "testIcon",
            onClick: function (obj) { //此时里面会多一个 response
                obj.btnCallbackFn.getValues(true, function (vals) {
                    console.log(vals)
                })
            }
        },

        {
            field: "setName",
            label: "setName",
            type: 'primary',
            icon: "testIcon",
            isValidate: false,
            onClick: "bind:setName"
        },




    ]
};


