
window.FormTest = {

    fieldCanDrag: true,
    fieldDragCb: function (obj) {
        console.log('fieldDragCb',obj)
    },

    formConfig: [
        {
            type: 'string',
            label: '姓名0',
            field: 'name',
            placeholder: '请输入',
            required: false,
        },
        {
            type: 'string',
            label: '年纪1',
            field: 'age',
            placeholder: '请输入',
            required: false,
            dependencies: ["name"],
        },
        {
            type: 'component',
            field: 'component',
            Component: 'diyComponent',
            dependencies: ["age"],
            // disabled: () => { console.log('1111') }
        },
        {
            type: 'qnnTable',
            label: '表格信息',
            field: 'qnnTable',
            // dependencies: ["age"],
            // disabled: (obj) => { 
            //     return  obj.form.getFieldValue('age') === "11"
            // },
            //将列表数据存入到表单数据中
            //开启后将不可进行单字段配置ajax
            //addRow和del按钮配置的 addCb 和 ajax配置 将无效 因为已经被内置方法接管了
            //各字段的 tdEditCb 配置将无效 因为已经被内置方法接管了
            incToForm: true,
            qnnTableConfig: {
                antd: {
                    size: "small",
                    rowKey: "id",
                },
                paginationConfig: false,
                actionBtns: [
                    {
                        name: 'addRow',
                        icon: 'plus',
                        type: 'primary',
                        label: '新增行',
                    },
                    {
                        name: 'del',
                        icon: 'del',
                        type: 'danger',
                        label: '删除选择行',
                    }
                ],
                formConfig: [
                    {
                        table: {
                            title: '序号',
                            dataIndex: 'no',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'string',
                            }
                        }
                    },
                    {
                        table: {
                            title: '公司名称',
                            dataIndex: 'name',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'select',
                            }
                        }
                    },
                    {
                        table: {
                            title: '备注',
                            dataIndex: 'beizhu',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'files',
                                max: 1,
                                "fetchConfig": {
                                    "apiName": window.configs.domain + "upload"
                                },
                            }
                        }
                    },
                ]
            }
        },
    ],
    btns: [
        {
            field: "diy",
            label: "设置值",
            type: 'primary',
            icon: "testIcon",
            onClick: function (obj) {
                obj.btnCallbackFn.setValues({
                    name: "值被更改",
                    qnnTable: [
                        {
                            id: "1",
                            "公司名称": "2222",
                            "序号": "111"
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
                obj.btnCallbackFn.getValues(true,function (vals) {
                    console.log(vals)
                })
            }
        },

    ]
};


