window.tableCanEdit = {
    isShowRowSelect: false,
    // firstRowIsSearch: true, 
    antd: {
        // rowKey: "levelId",
        rowKey: "itemId",
        size: "small",
        sticky: true,
        scroll:{
            y: window.innerHeight - 280,
        }
    },
    actionBtns: [
        {
            name: 'addRow',
            icon: 'plus',
            type: 'primary',
            label: '新增行',
            //    addRowDefaultData:{},//新增时候的默认数据
            //    addRowFetchConfig:{apiName:''},//ajax配置 和 fetchConfig一样
        },
    ],
    data: new Array(10).fill("").map((_, i) => ({
        itemId: i + "",
        radio: 1,
        itemName: "王五" + i,
    })),
    tableTdEditSaveCb:(newRowData, oldRowData, props)=>{
        console.log(newRowData)
    },
    // fetchConfig: {
    //     apiName: "getBaseCodeSelect",
    //     otherParams: {
    //         itemId: "minzhu"
    //     }
    // },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 4 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 16 }
        }
    },
    // paginationConfig: false,
    desc: "paginationConfig:false, 配置可设置隐藏表格底部分页, 10[行] * 10[列] ",

    // rowDisabledCondition: function (rowData,props) { 
    //     return rowData.id === '3'
    // }, 
    formConfig: [

        {
            isInForm: false,
            table: {
                dataIndex: "addNum1",
                title: "加数1",
                tdEdit: true,
                fieldConfig: {
                    type: "number",
                    onChange: async (val, formBtnCallbackFn, tableBtnCallbackFn) => {
                        //值切换是改变count单元格数据 
                        const { addNum1 = 0, addNum2 = 0 } = await tableBtnCallbackFn.getEditedRowData(false);
                        // console.log(addNum1 + addNum2)
                        tableBtnCallbackFn.setEditedRowData({
                            count: addNum1 + addNum2,
                        });
                    }
                }
            }
        },
        {
            isInForm: false,
            table: {
                dataIndex: "addNum2",
                title: "加数2",
                tdEdit: true,
                fieldConfig: {
                    type: "number",
                    onChange: async (val, formBtnCallbackFn, tableBtnCallbackFn) => {
                        //值切换是改变count单元格数据 
                        const { addNum1 = 0, addNum2 = 0 } = await tableBtnCallbackFn.getEditedRowData(false);
                        // console.log(addNum1 + addNum2)
                        tableBtnCallbackFn.setEditedRowData({
                            count: addNum1 + addNum2,
                        });
                    }
                }
            }
        },
        {
            isInForm: false,
            table: {
                dataIndex: "count",
                title: "和",
                tdEdit: true,
                fieldConfig: {
                    type: "number",
                }
            }
        },

        {
            table: {
                dataIndex: 'radio',
                title: 'radio',
                tdEdit: true,
                tdEditCb:"bind:tdEditCb",
                width:120
            },
            form: {
                type: 'radio',
                optionData: [
                    { label: "是", value: 1 },
                    { label: "否", value: 0 }
                ]
            }
        },
        {
            table: {
                title: "姓名",
                dataIndex: "itemName",
                tdEdit: true,
                tdEditCb:"bind:tdEditCb",
                // tdEditFetchConfig: {
                //     apiName: 'getBaseCodeSelect',
                // },
                fieldConfig: {
                    // optionData: [{ label: "汉族", value: "汉族" }],
                    // type: function (rowData, props) {
                    //     // console.log(rowData)
                    //     if (rowData.itemId === "1") {
                    //         return 'select'
                    //     } else {
                    //         return 'string'
                    //     }
                    // },
                },
            },
            form: {
                type: "string",
                rows: 2,
                placeholder: "请输入..."
            },
        },

        {
            table: {
                title: "年纪",
                tdEdit: true,
                dataIndex: "age",
                tdEditCb:"bind:tdEditCb",
                // tdEditFetchConfig: {
                //     apiName: 'getBaseCodeSelect',
                // },
                fieldConfig: {
                    field: "age1",
                    type: "time",
                    allowClear: false
                },
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },
        // {
        //     // isInSearch:true,
        //     table: {
        //         // width: 120,
        //         title: "爱好",
        //         dataIndex: "hobby",
        //         tdEdit: true,
        //         tdEditCb:"bind:tdEditCb",
        //         // tdEditFetchConfig: {
        //         //     apiName: 'getBaseCodeSelect',
        //         // },

        //         fieldsConfig: {
        //             type: "selectByPaging",
        //             field: "aiHao",
        //             // mode: "tags",

        //             pageConfig: {
        //                 //设置每页显示多少条数据
        //                 limit: 5
        //             },
        //             fetchConfig: {
        //                 apiName: "getSysUserListByBg",
        //                 otherParams: {
        //                     departmentId: "2324017",
        //                     rootId: "999999999"
        //                 }
        //             },
        //             optionConfig: {
        //                 label: "realName",
        //                 value: "userKey"
        //             },
        //             allowClear: false




        //         }
        //     },
        //     form: {
        //         type: "select",
        //         placeholder: "选择...",

        //     },
        // },

        // {
        //     table: {
        //         // width: 300,
        //         title: "日期范围下拉(date、week、month、year)",
        //         format: 'YYYY-MM',
        //         dataIndex: "year",
        //         tdEdit: true,
        //         tdEditFetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //         },
        //         fieldsConfig: {
        //             placeholder: "请选择...",
        //             // type: "month",
        //             showTime: false,
        //             isLouDou: true
        //         }
        //     },
        //     form: {
        //         label: "日期范围下拉",
        //         type: "date",
        //         placeholder: "请输入..."
        //     },
        // },
        // {
        //     table: {
        //         // width: 300,
        //         title: "日期范围(date、week、month、year)",
        //         dataIndex: "rangeDate",
        //         format: 'YYYY-MM',
        //         tdEdit: true,
        //         tdEditFetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //         },
        //         fieldsConfig: {
        //             type: "rangeDate",
        //             picker: "month",
        //             placeholder: "请选择...",
        //             showTime: false,
        //         }
        //     },
        //     form: {
        //         label: "日期范围",
        //         type: "rangeDate",
        //         placeholder: "请输入..."
        //     },
        // },
        {
            table: { 
                title: "住址",
                dataIndex: "address",
                tdEdit: true,
                tdEditCb:"bind:tdEditCb", 
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },
        {
            table: { 
                title: "住址2",
                dataIndex: "address2",
                tdEdit: true,
                tdEditCb:"bind:tdEditCb", 
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },
        
        // {
        //     table: {
        //         title: "附件",
        //         dataIndex: "files",
        //         tdEdit: true,
        //         tdEditCb:"bind:tdEditCb",
        //     },
        //     form: {
        //         type: "files",
        //         showDownloadIcon: true,
        //         onPreview: "bind:_docFilesByOfficeUrl",
        //         initialValue: [
        //             {
        //                 mobileThumbUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //                 , mobileUrl: "http://office.apih5.com:8088/?furl=46JcKz4JzvskwX_xoONJF2MQ8HUHOVZx4dmlifovwDhYs7Hymt95ztAdCKoQLzY_XHXDckXdxd9D5W4fs3eCk_@c4lvRyqfZrdQ9MTWOzKs="
        //                 , name: "1095.jpg"
        //                 , relativeThumbUrl: "upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //                 , relativeUrl: "upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //                 , size: "39"
        //                 , thumbUrl: "http://uat.bjzxkj.com:99/apiZt17lw/upload/1EM6BNUU10TAC8C01AAC0000C01A6F8F.jpg"
        //                 , type: "image/jpeg"
        //                 , uid: ""
        //                 , url: "http://office.apih5.com:8088/?furl=46JcKz4JzvskwX_xoONJF2MQ8HUHOVZx4dmlifovwDhYs7Hymt95ztAdCKoQLzY_XHXDckXdxd9D5W4fs3eCk_@c4lvRyqfZrdQ9MTWOzKs="
        //             }
        //         ]
        //     },
        // }
        // {
        //     isInForm: false,
        //     table: {
        //         showType: 'tile',
        //         width: 80,
        //         key: "action",//操作列名称 
        //         btns: [
        //             {
        //                 name: 'add',
        //                 field: 'add',
        //                 label: "新增", 
        //             }
        //         ]
        //     }
        // }
    ],
}