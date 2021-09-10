
window.FormTest = {
    // fetchConfig: {
    //     apiName: "getSysDepartmentAllTree",
    //     params: {
    //         field2: 'field2'
    //     }
    // },
    formConfig: [
        {
            type: 'qnnTable',
            label: '',
            field: 'qnnTable',
            qnnTableConfig: {
                //所有配置见qnn-form
                antd: {
                    rowKey: "key" 
                },
                isShowRowSelect: false,
                data: [
                    {
                        id: 'qwe',
                        key: 1,
                        evalPro: '项目1',
                        deduct: 23,
                        deductDesc: '鹅鹅鹅饿饿',
                        children: [
                            {
                                id: 'rt',
                                key: 56,
                                evalPro: '11',
                                deduct: 34,
                                deductDesc: '哈哈哈哈',
                            },
                            {
                                id: 'tuyt',
                                key: 32,
                                evalPro: '111',
                                deduct: 5,
                                deductDesc: '嘤嘤嘤嘤'
                            }
                        ]
                    },
                    {
                        id: 'hgf',
                        key: 2,
                        evalPro: '项目2',
                        age: 32,
                        deductDesc: '更不讲究',
                        children: [
                            {
                                id: 'erwtg',
                                key: 34,
                                evalPro: '222',
                                deduct: 7,
                                deductDesc: '日日日日',
                            },
                            {
                                id: 'tryru',
                                key: 23,
                                evalPro: '22222',
                                deduct: 9,
                                deductDesc: '广东话的'
                            }
                        ]
                    },
                ],
                formConfig: [
                    {
                        table: {
                            title: 'test1',
                            dataIndex: 'evalPro',
                            // tdEdit: true,
                            // tdEditCb: (props) => {
                            //     console.log(props)
                            // }
                        }
                        ,form: {
                            type: 'string',
                            placeholder: '请输入',
                            required: false,
                        }
                    },
                    {
                        table: {
                            title: 'test2',
                            dataIndex: 'deductDesc',
                            tdEdit: true
                        }
                        ,form: {
                            type: 'string',
                            placeholder: '请输入',
                            required: false,
                        }
                    },
                ],
                actionBtns: []
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
                    label: "值被更改",
                    value: null
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


