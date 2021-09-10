// 系统项目管理
window._ProjectManage = {
    fetchConfig: {
        apiName: 'getSysProjectList',
        params: {},
        otherParams: {},
    },
    antd: {
        rowKey: "departmentId",
        size: "small"
    },
    isShowRowSelect: true,
    actionBtns: [
        {
            field: 'addCancelBtn',
            //willExecute:()=>void //点击前
            name: 'add',//【add, addRow,  del, edit, detail, Component, form】
            icon: 'plus',
            type: 'primary',
            label: '新增',
            drawerTitle: '新增',
            formBtns: [
                {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                    field: 'addCancelBtn',
                },
                {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '提交',//提交数据并且关闭右边抽屉 
                    fetchConfig: {//ajax配置
                        apiName: 'addSysProjectByRelation',
                    },
                    //自定义按钮key值 必须配置 否则可能按钮点击后不会置为loading状态可能会导致重复提交问题
                    field: 'addSubmitBtn',
                    isValidate: true,//点击后是否验证表单 默认true
                }
            ]
        },
        {
            field: 'delBtn',
            //willExecute:()=>void //点击前
            name: 'del',//【add, addRow,  del, edit, detail, Component, form】
            icon: 'delete',
            fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateSysProject',
            },
            type: 'danger',
            label: '删除',
        },
    ],
    formConfig: [

        {
            table: {
                title: '项目名称',
                dataIndex: 'projectName',
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
                required: true
            },
        },
        // {
        //     table: {
        //         title: '项目简称',
        //         dataIndex: '项目简称',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required:true
        //     },
        // }, 
        {
            isInTable: false
            ,form: {
                required: true,
                type: 'treeSelect',
                label: '挂载上级',
                field: 'confUpJSONArray',
                treeSelectOption: {
                    selectType: "1",
                    //配置参照oa拉人组件配置
                    fetchConfig: { apiName: 'getSysDepartmentAllTree',otherParams: {} },
                },
            }
        },
        {
            isInTable: false
            ,form: {
                required: true,
                type: 'treeSelect',
                label: '挂载下级',
                field: 'confDownJSONArray',
                treeSelectOption: {
                    selectType: "1",
                    //配置参照oa拉人组件配置
                    fetchConfig: { apiName: 'getSysDepartmentAllTree',otherParams: {} },
                },
            }
        },

        {
            isInForm: false,
            table: {
                dataIndex: 'action', //表格里面的字段
                key: 'action',//表格的唯一key  
                showType: 'tile',
                title: "操作",
                width: 80,
                btns: [
                    {
                        name: 'edit',
                        label: "修改",
                        formBtns: [
                            {
                                name: 'cancel', //关闭右边抽屉
                                type: 'dashed',//类型  默认 primary
                                label: '取消',
                            },
                            {
                                name: 'submit',//内置add del
                                type: 'primary',//类型  默认 primary
                                label: '提交',//提交数据并且关闭右边抽屉 
                                fetchConfig: {//ajax配置
                                    apiName: 'updateSysProjectByRelation',
                                }
                            }
                        ]
                    }
                ]
            }
        },
    ]
}