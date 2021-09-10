window.tableCanEdit = {
    isShowRowSelect:false,
    firstRowIsSearch: false, 
    antd: {
        rowKey: "id",
        size: "small",
    }, 
    // fetchConfig={{
    //     apiName:"getTodoList"
    // }}
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
    rowDisabledCondition: function (rowData,props) { 
        return rowData.id === '3'
    },
    data: [
        {
            id: "0",
            name: "某个单元格被禁用",
            address: "蒙渡村",
            age: 21,
            sex: "男",
            hobby: "0",
            birthday: 1582275494301,
            year: 1582275494301,
            rangeDate: [1582275494301,1582575494301],
        },
        {
            id: "2",
            name: "某一行被禁用",
            address: "沈阳村",
            age: 55,
            sex: "男",
            hobby: "0",
            birthday: 1582275494301,
            year: 1582275494301,
            rangeDate: [1582275494301,1582575494301],
        },
        {
            id: "3",
            name: "正常单元格",
            address: "大连村",
            age: 55,
            sex: "男",
            hobby: "0",
            birthday: 1582275494301,
            year: 1582275494301,
            rangeDate: [1582275494301,1582575494301],
        },
        {
            id: "4",
            name: "正常单元格",
            address: "大连村",
            age: 55,
            sex: "男",
            hobby: "0",
            birthday: 1582275494301,
            year: 1582275494301,
            rangeDate: [1582275494301,1582575494301],
        }
    ],
    formConfig: [
        { 
            table: {
                title: "姓名",
                dataIndex: "name", 
                tdEdit: true,
                tdEditFetchConfig: {
                    apiName: 'login', 
                },
                fieldsConfig: {
                    type: "string",
                    disabled:function(obj){ 
                        return obj.record.name === "某个单元格被禁用"
                    }
                }, 
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },

        { 
            table: { 
                title: "年纪",
                tdEdit: true,
                tdEditFetchConfig: {
                    apiName: 'login',
                },
                dataIndex: "age"
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        },
        {
            // isInSearch:true,
            table: {
                // width: 120,
                title: "爱好",
                dataIndex: "hobby",
                tdEdit: true,
                tdEditFetchConfig: {
                    apiName: 'login',
                },
                type: "select",
                fieldsConfig: {
                    type: "select",
                    // mode: "multiple",
                    optionData: [
                        {
                            label: "吃饭",
                            value: "0"
                        },
                        {
                            label: "睡觉",
                            value: "1"
                        },
                        {
                            label: "玩游戏",
                            value: "2"
                        }
                    ]
                }
            },
            form: {
                type: "select",
                placeholder: "选择...",
                optionData: [
                    {
                        label: "吃饭",
                        value: "0"
                    },
                    {
                        label: "睡觉",
                        value: "1"
                    },
                    {
                        label: "玩游戏",
                        value: "2"
                    }
                ]
            },
        },
        
        {
            table: {
                // width: 300,
                title: "日期范围下拉(date、week、month、year)",
                format: 'YYYY-MM',
                dataIndex: "year",
                tdEdit: true,
                tdEditFetchConfig: {
                    apiName: 'login',
                },
                fieldsConfig: {
                    placeholder: "请选择...",
                    type: "month",
                    showTime: false,
                    isLouDou: true
                }
            },
            form: {
                label: "日期范围下拉",
                type: "date",
                placeholder: "请输入..."
            },
        },
        {
            table: {
                // width: 300,
                title: "日期范围(date、week、month、year)",
                dataIndex: "rangeDate",
                format: 'YYYY-MM',
                tdEdit: true,
                tdEditFetchConfig: {
                    apiName: 'login',
                },
                fieldsConfig: {
                    type: "rangeDate",
                    picker: "month",
                    placeholder: "请选择...",
                    showTime: false,
                }
            },
            form: {
                label: "日期范围",
                type: "rangeDate",
                placeholder: "请输入..."
            },
        },
        {
            table: {
                // width: 120,
                title: "住址",
                dataIndex: "address",
                fixed:'right',
            },
            form: {
                type: "string",
                placeholder: "请输入..."
            },
        } 
    ],
}