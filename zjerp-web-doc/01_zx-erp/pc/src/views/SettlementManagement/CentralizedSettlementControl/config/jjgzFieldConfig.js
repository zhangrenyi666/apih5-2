//计价规则字段配置
const jjgzFieldConfig = {
    label: "计价规则",
    field: "ruleName",
    type: "selectByQnnTable",
    formItemLayout: {
        labelCol: {
            sm: { span: 6 }
        },
        wrapperCol: {
            sm: { span: 18 }
        }
    },
    optionConfig: {
        value: 'id',
        label: "ruleName"
    },
    dropdownMatchSelectWidth: 800,
    qnnTableConfig: {
        antd: {
            rowKey: "id",
            scroll:{
                x:"200px"
            }
        },
        selectType: "radio", //默认radio单选  值为 string 
        searchBtnsStyle: "inline",
        formConfig: [
            {
                isInSearch:true,
                table: {
                    dataIndex: "orderNum",
                    title: "序号",  
                },
                form: {
                    type: "string"
                }
            },
            {
                isInSearch:true,
                table: {
                    dataIndex: "ruleName",
                    title: "计价规则名称", 
                },
                form: {
                    type: "string"
                }
            },
            {
                table: {
                    dataIndex: "remark",
                    title: "备注",  
                },
                form: {
                    type: "string"
                }
            }
        ]
    } 
}

export default jjgzFieldConfig