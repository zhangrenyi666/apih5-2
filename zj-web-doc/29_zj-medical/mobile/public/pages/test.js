window.testTable = {
    actionBtns: [
        {
            name: "add",
            icon: "plus",
            type: "primary",
            label: "新增",
            formBtns: [
                {
                    name: "submit",
                    type: "primary",
                    label: "提交",
                    fetchConfig: {
                        apiName: "submit"
                    }
                }
            ]
        }
    ],
    //字段配置
    formConfig: [
        {
            isInSearch: true,
            table: {
                title: "字段一",
                dataIndex: "field1",
                key: "field1"
            },
            form: {
                type: "string",
                placeholder: "请输入"
            }
        },
        {
            isInSearch: true,
            table: {
                title: "字段二",
                dataIndex: "field2",
                key: "field2"
            },
            form: {
                type: "string",
                placeholder: "请输入"
            }
        },
        {
            isInSearch: true,
            table: {
                title: "字段三",
                dataIndex: "field3",
                key: "field3"
            },
            form: {
                type: "string",
                placeholder: "请输入"
            }
        }
    ]
};
