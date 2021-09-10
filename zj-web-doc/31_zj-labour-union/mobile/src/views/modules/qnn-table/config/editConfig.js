//开发者模式下的一些配置

//开发者按钮数据
const editBtnsConfig = [
    // {
    //     name: "allConfig",
    //     type: "primary",
    //     icon: "config",
    //     label: "主体配置"
    // },
    {
        name: "add",
        icon: "plus",
        type: "primary",
        label: "新增字段",
        formBtns: [
            {
                name: "_addField",
                type: "primary",
                label: "确定",
                icon: "check"
            }
        ]
    },
    // {
    //     name: "_exportData",
    //     type: "primary",
    //     icon: "export",
    //     label: "导出配置数据"
    // },
    {
        name: "_delField",
        icon: "delete",
        type: "danger",
        label: "删除"
    },
    // {
    //     name: "_prev",
    //     icon: "desktop",
    //     type: "primary",
    //     label: "预览"
    // }
];

//字段类型下拉
const type = [
    {
        label: "字符串",
        value: "string"
    },
    {
        label: "数字",
        value: "number"
    },
    {
        label: "整数",
        value: "integer"
    },
    {
        label: "密码",
        value: "password"
    },
    {
        label: "网址",
        value: "url"
    },
    {
        label: "多行文本",
        value: "textarea"
    },
    {
        label: "下拉选择",
        value: "select"
    },
    {
        label: "层叠联动",
        value: "cascader"
    },
    {
        label: "无限联动",
        value: "linkage"
    },
    {
        label: "邮箱",
        value: "email"
    },
    {
        label: "开关",
        value: "switch"
    },
    {
        label: "滑块",
        value: "slider"
    },
    {
        label: "打分",
        value: "rate"
    },
    {
        label: "单选",
        value: "radio"
    },
    {
        label: "多选",
        value: "checkbox"
    },
    {
        label: "选项",
        value: "item"
    },
    {
        label: "日期时间",
        value: "datetime"
    },
    {
        label: "日期选择",
        value: "date"
    },
    {
        label: "时间选择",
        value: "time"
    },
    {
        label: "年月选择",
        value: "month"
    },
    {
        label: "文件上传",
        value: "files"
    },
    {
        label: "图片上传",
        value: "images"
    },
    {
        label: "移动端上传",
        value: "camera"
    },
    {
        label: "富文本",
        value: "richText"
    },
    {
        label: "树节点",
        value: "treeNode"
    },
    {
        label: "人员/部门",
        value: "treeSelect"
    },
    {
        label: "qnnForm控件",
        value: "qnnForm"
    },
    {
        label: "qnnTable控件",
        value: "qnnTable"
    }
];

//一行另个输入控件的布局
const rowTowCol = {
    spanForm: 12,
    formItemLayoutForm: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 16 }
        }
    },
    colWrapperStyle: {
        paddingLeft: "16px"
    }
}

//开发者模式下的新增字段配置
const editTableConfig = {
    antd: {
        rowKey: function (row) {
            //---row.主键id
            return row._id;
        },

        size: "small"
    },
    formConfig: [
        {
            isInTable: false,
            table: {
                dataIndex: "_id",
                key: "_id",
                title: "内置id字段"
            },
            form: {
                hide: true,
                type: "string"
            }
        },
        {
            table: {
                dataIndex: "dataIndex",
                key: "dataIndex",
                title: "table英文名",
                // onClick: "edit",
                // btns: [
                //     {
                //         name: "_addField",
                //         type: "primary",
                //         label: "保存修改",
                //         icon: "check"
                //     }
                // ]
            },
            form: {
                required: true,
                type: "string",
                placeholder: "请输入..."
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "field",
                key: "field",
                title: "form英文名"
            },
            form: {
                type: "string",
                placeholder: "请输入...",
                help: "如果和表格中的字段一样的话可不用输入"
            }
        },
        {
            table: {
                dataIndex: "title",
                key: "title",
                title: "table中文名",
            },
            form: {
                required: true,
                type: "string",
                placeholder: "请输入..."
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "label",
                key: "label",
                title: "form中文名"
            },
            form: {
                type: "string",
                placeholder: "请输入...",
                help: "如果和表格中的字段中文名一样的话可不用输入"
            }
        },
        {
            table: {
                dataIndex: "type",
                key: "type",
                title: "类型",
                align: "center",
                render: data => {
                    let _d = type.filter(item => data === item.value);
                    return _d[0].label;
                }
            },
            form: {
                initialValue: "string",
                type: "select",
                optionData: type,
                placeholder: "选选择..."
            }
        },
        // {
        //     table: {
        //         dataIndex: "type",
        //         key: "type",
        //         title: "表单中行占比", 
        //     },
        //     form: {
        //         initialValue: "string",
        //         type: "select",
        //         optionData: type,
        //         placeholder: "选选择..."
        //     }
        // },
        {
            table: {
                dataIndex: "isInForm",
                key: "isInForm",
                title: "表单中显示",
                align: "center",
                render: data => {
                    return data ? "是" : "否";
                }
            },
            form: {
                type: "radio",
                initialValue: true,
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    },
                ]
            }
        },
        {
            table: {
                dataIndex: "isInTable",
                key: "isInTable",
                title: "表格中显示",
                align: "center",
                render: data => {
                    return !data ? "否" : "是";
                }
            },
            form: {
                type: "radio",
                initialValue: true,
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    }
                ]
            }
        },
        {
            table: {
                dataIndex: "isInSearch",
                key: "isInSearch",
                title: "是否为搜索条件",
                align: "center",
                render: data => {
                    return !data ? "否" : "是";
                }
            },
            form: {
                type: "radio",
                initialValue: false,
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    }
                ]
            }
        },
        {
            table: {
                dataIndex: "filter",
                key: "filter",
                title: "是否为表头搜索",
                align: "center",
                render: data => {
                    return !data ? "否" : "是";
                }
            },
            form: {
                type: "radio",
                initialValue: false,
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    }
                ]
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "disabled",
                key: "disabled",
                align: "center",
                title: "禁用",
                render: data => {
                    return !data ? "否" : "是";
                }
            },
            form: {
                type: "radio",
                initialValue: false,
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    }
                ]
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "addDisabled",
                key: "addDisabled",
                title: "仅新增时禁用",
                align: "center",
                render: data => {
                    return !data ? "否" : "是";
                }
            },
            form: {
                type: "radio",
                initialValue: false,
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    }
                ]
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "editDisabled",
                key: "editDisabled",
                title: "仅修改时禁用",
                render: data => {
                    return !data ? "否" : "是";
                }
            },
            form: {
                type: "radio",
                initialValue: false,
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    }
                ]
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "key",
                key: "key",
                title: "是否为主键"
            },
            form: {
                initialValue: false,
                type: "radio",
                ...rowTowCol,
                optionData: [
                    {
                        label: "否",
                        value: false
                    },
                    {
                        label: "是",
                        value: true
                    }
                ]
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "align",
                key: "align",
                title: "单元格内容对齐"
            },
            form: {
                initialValue: "left",
                type: "radio",
                optionData: [
                    {
                        label: "靠左",
                        value: "left"
                    },
                    {
                        label: "居中",
                        value: "center"
                    },
                    {
                        label: "靠右",
                        value: "right"
                    }
                ]
            }
        },
        {
            isInTable: false,
            table: {
                dataIndex: "fixed",
                key: "fixed",
                title: "列是否吸附在左/右"
            },
            form: {
                initialValue: "none",
                type: "radio",
                optionData: [
                    {
                        label: "正常排列",
                        value: "none"
                    },
                    {
                        label: "靠左",
                        value: "left"
                    },
                    {
                        label: "靠右",
                        value: "right"
                    }
                ]
            }
        },
        {
            isInForm: false,
            table: {
                width: 110,
                title: "操作",
                align: "center",
                showType: "tile",
                dataIndex: "action", //表格里面的字段
                key: "action", //表格的唯一key
                // fixed: "right",
                btns: function (props) {
                    return [
                        {
                            name: "edit",
                            label: "修改",
                            //抽屉中的按钮
                            formBtns: [
                                {
                                    name: "_addField",
                                    type: "primary",
                                    label: "保存修改",
                                    icon: "check"
                                }
                            ]
                        }
                    ]
                }
            }
        }
    ]
};

//主体设置配置
const allConfig = {
    tabs: [
        {
            field: "changYong", //唯一标识名必传***
            name: "qnnForm", //内置qnnForm  qnnTable  其他的为自定义
            title: "常用设置", //标题
            content: {
                formConfig: [
                    {
                        label: "主键",
                        field: "rowKey",
                        type: "select",
                        optionData: [],
                        placeholder: "请选择"
                    },
                    {
                        label: "顶部小提示文字",
                        field: "desc",
                        type: "string",
                        placeholder: "请输入..."
                    },
                    {
                        label: "全局提示框",
                        field: "infoAlert",
                        type: "string",
                        placeholder: "请输入...",
                        help: "可使用字符串模板，具体请看说明文档"
                    },
                    {
                        label: "是否显示选择框",
                        field: "isShowRowSelect",
                        type: "radio",
                        placeholder: "请选择",
                        initialValue: true,
                        optionData: [
                            {
                                label: "否",
                                value: false
                            },
                            {
                                label: "是",
                                value: true
                            }
                        ]
                    },
                    {
                        label: "抽屉状态时点击蒙层是否关闭抽屉",
                        field: "maskClosable",
                        type: "radio",
                        placeholder: "请选择",
                        initialValue: true,
                        optionData: [
                            {
                                label: "否",
                                value: false
                            },
                            {
                                label: "是",
                                value: true
                            }
                        ]
                    }
                ]
            }
        },
        {
            field: "changYong", //唯一标识名必传***
            name: "qnnForm", //内置qnnForm  qnnTable  其他的为自定义
            title: "布局设置", //标题
            content: {
                formConfig: [

                    {
                        label: "抽屉宽",
                        field: "width",
                        type: "number",
                        initialValue: 800,
                        placeholder: "请输入..."
                    },
                    {
                        label: "搜索区域每行字段数量",
                        field: "searchFormColNum",
                        type: "number",
                        placeholder: "请输入...",
                        initialValue: 3
                    },
                ]
            }
        }
    ],
    formConfig: []
};

export { editBtnsConfig,editTableConfig,allConfig };
