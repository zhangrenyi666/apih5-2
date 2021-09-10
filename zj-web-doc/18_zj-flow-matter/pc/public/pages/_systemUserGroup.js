// 系统用户组
window._systemUserGroup = {
    fetchConfig: {
        apiName: 'getSysUserGroupList',
        params: {},
        otherParams: {},
    },
    antd: {
        rowKey: "groupId",
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
                    //格式化数据
                    //paramsFormat:({params, props, ...})=>{return {...}},
                    //控制是否提交表单
                    //isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)
                    fetchConfig: {//ajax配置
                        apiName: 'addSysUserGroup',
                        //删除参数中的name、age字段
                        delParams: []
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
                apiName: 'batchDeleteUpdateSysUserGroup',
                //删除参数中的name、age字段
                delParams: []
            },
            type: 'danger',
            label: '删除',
            // 配置 可控制是否提交表单  params在这是个数组
            //isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)
        },
    ],
    formConfig: [
        {
            table: {
                title: '用户组ID',
                dataIndex: 'groupId',
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
                editDisabled:true,
                addDisabled:false,
            },
        },
        {
            table: {
                title: '用户组名称',
                dataIndex: 'groupName',
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
            },
        },
        {
            isInTable: false
            ,form: {
                required: true,
                type: 'treeSelect',
                label: '组员',
                field: 'sysUserGroupInfoList',
                treeSelectOption: {
                    selectType:"2",
                    //配置参照oa拉人组件配置
                    fetchConfig: { apiName: 'getSysDepartmentUserAllTree',otherParams: {} },
                }, 
            }
        },
        {
            table: {
                title: '备注',
                dataIndex: 'remarks',
            }
            ,form: {
                type: 'textarea',
                placeholder: '请输入',
            },
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
                                    apiName: 'updateSysUserGroup',
                                }
                            }
                        ]
                    }
                ]
            }
        },
    ]
}