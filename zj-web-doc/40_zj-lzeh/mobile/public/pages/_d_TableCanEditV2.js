window.tableCanEditV2 = {
    isShowRowSelect: false,
    // firstRowIsSearch: true, 
    antd: {
        // rowKey: "levelId",
        rowKey: "itemId",
        size: "small",
        sticky: true
    },
    // fetchConfig:{
    //     apiName:"getBaseCodeSelect",
    //     otherParams:{
    //         itemId:"minzhu"
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
    // desc: "paginationConfig:false, 配置可设置隐藏表格底部分页",
    actionBtns: [
        {
            name: 'addRow',
            icon: 'plus',
            type: 'primary',
            label: '新增行',
            detailShow: true,
        },
    ],
    //是否是编辑表格 true | false
    //true的情况会在表格后面追加编辑操作列
    tableTdEdit: true,
    tableTdEditSaveCb: (newRowData, oldRowData, props) => {
        console.log(newRowData)
    },
    // tableTdEditFetchConfig: {
    //     apiName: 'getBaseCodeSelect',
    //     otherParams: {
    //         aaaa: '1111'
    //     }
    // },

    // rowDisabledCondition: function (rowData,props) { 
    //     return rowData.id === '3'
    // },
    data: [
        ...Array.from(new Array(13)).map((item, i) => {
            return {
                itemId: i + '',
                // itemName: ["" + i],
                itemName: "" + i,
                address: "大连村" + i,
                age: i,
                sex: "男",
                hobby: "0",
                birthday: 1582275494301 + i,
                year: 1582275494301 + i,
                rangeDate: [1582275494301, 1582575494301],
            }
        })
    ],
    formConfig: [
        {
            table: {
                title: "姓名",
                dataIndex: "itemName",
                tdEdit: true,
                tdEditFetchConfig: {
                    apiName: 'getBaseCodeSelect',
                },

                //用于表格显示
                type: "select",
                optionData: [
                    { label: "下拉0", value: "0", },
                    { label: "下拉1", value: "1" }
                ],

                fieldConfig: {
                    // field: "itemName",
                    // multiple:true,
                    optionData: [
                        {
                            label: "下拉0", value: "0",
                        },
                        {
                            label: "下拉1", value: "1",
                            children: [{ label: "下拉0-1", value: "0-1" }]
                        }
                    ],

                    //每一行数据设置不同的type
                    type: function (rowData, props) {
                        if (rowData.itemId === "0") {
                            return 'select'
                        } else {
                            return 'string'
                        }
                    },
                    onChange: async function (val, obj, tableBtnCallbackFn) {
                        //获取编辑行的单元格值
                        const vals = await tableBtnCallbackFn.getEditedRowData();
                        console.log(vals)
                        //设置别的单元格值
                        await tableBtnCallbackFn.setEditedRowData({
                            itemName2: val,

                            //这个值一定需要设置  否则将会丢失改单元格输入框的值
                            itemName: val
                        })


                        //设置别的单元格下拉选项(下拉类型的单元格) linkageOptions
                        const chaildrenOptionData = obj.itemdata.children;
                        // console.log('chaildrenOptionData', chaildrenOptionData)
                        // console.log(tableBtnCallbackFn.getCurEditRowFns());
                        // return;
                        const foo = tableBtnCallbackFn.getCurEditRowFns("linkageOptions");
                        // console.log("chaildrenOptionData:", chaildrenOptionData)
                        foo.setOptionData(chaildrenOptionData)
                        // console.log(foo.getOptionData())
                        // tableBtnCallbackFn.getCurEditRowFns("linkageOptions", chaildrenOptionData)

                    }
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
                title: "联动姓名",
                dataIndex: "itemName2",
                tdEdit: true,
                fieldConfig: {
                    type: "string",
                    disabled: true,
                },
            },
            form: {
                type: "string",
            },
        },
        {
            table: {
                // width: 120,
                title: "联动下拉选项",
                dataIndex: "linkageOptions",
                tdEdit: true,
                fieldsConfig: {
                    optionConfig: {
                        label: "label",
                        value: "value"
                    },
                }
            },
            form: {
                type: "select",
                placeholder: "选择...",
            },
        },

        {
            table: {
                title: "下拉表格",
                tdEdit: true,
                dataIndex: "age",
                // tdEditFetchConfig: {
                //     apiName: 'getBaseCodeSelect',
                // },
                fieldConfig: {
                    // field: "age1",
                    // type: "time",
                    // allowClear: false
                    // required: true,

                    // 表格下拉
                    type: 'selectByQnnTable',
                    label: '下拉表格',
                    field: 'selectByQnnTable',
                    //用于下拉控件 的输入框显示 选择后的表格数据
                    optionConfig: {
                        value: "itemId",
                        label: "itemName"
                    },
                    dropdownMatchSelectWidth: false,
                    //qnnTable组件配置
                    qnnTableConfig: {
                        antd: {
                            rowKey: "itemId"
                        },
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: "minzhu"
                            }
                        },
                        searchBtnsStyle: "inline",
                        formItemLayoutSearch: {
                            labelCol: {
                                sm: { span: 8 }
                            },
                            wrapperCol: {
                                sm: { span: 16 }
                            }
                        },
                        formConfig: [
                            {
                                // isInSearch: true,
                                isInForm: false,
                                table: {
                                    dataIndex: "itemName",
                                    title: "数据名",
                                    // filter:true,
                                    // fieldsConfig:{
                                    //     type:"string"
                                    // }
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
                                    format: "YYYY/MM/DD"
                                }
                            }
                        ]
                    },

                    onChange: (val, formProps, tableProps) => {
                        // tableProps.setEditedRowData({
                        //     itemName2:val
                        // })
                    }
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
        //         tdEditFetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //         },
        //         fieldsConfig: {
        //             type: "select",
        //             field: "aiHao",
        //             // mode: "tags",
        //             fetchConfig: {
        //                 apiName: "getBaseCodeSelect",
        //                 otherParams: {
        //                     itemId: "minzhu"
        //                 }
        //             },
        //             optionConfig: {
        //                 label: "itemName",
        //                 value: "itemId"
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
                // width: 120,
                title: "住址",
                dataIndex: "address",
                tdEdit: true,
                tdEditFetchConfig: {
                    apiName: 'getBaseCodeSelect',
                },
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },
        ...new Array(20).fill('1').map((item, index) => {
            return {
                table: {
                    title: 'string' + index,
                    dataIndex: 'string' + index,
                    tdEdit: true,
                }
                , form: {
                    type: 'string',
                    placeholder: '请输入',
                    required: false,
                }
            }
        })

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