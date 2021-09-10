
var geted = false;
window._SetTimeOutPage = {
    fetchConfig: {
        apiName: "getZxQrtzJobDetailsListByJoin",
        // otherParams: "bind:listOtherParams"
    },
    antd: {
        rowKey: "jobName",
        size: "small"
    },
    actionBtns: [
        {
            field: 'add',
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
                        apiName: 'addZxQrtzJob',
                        // otherParams: "bind:addOtherParams",
                        //删除参数中的name、age字段
                        delParams: []
                    },
                    //自定义按钮key值 必须配置 否则可能按钮点击后不会置为loading状态可能会导致重复提交问题
                    field: 'addSubmitBtn',
                }
            ]
        },
        {
            field: 'pause',
            name: 'pause',
            type: 'danger',
            label: '暂停',
            onClick: "bind:pause",
            disabled: "bind:_actionBtnNoSelected"
        },
        {
            field: 'recover',
            name: 'recover',
            type: 'primary',
            label: '恢复',
            onClick: "bind:recover",
            disabled: "bind:_actionBtnNoSelected"
        },
        {
            field: 'immediateExecution',
            name: 'immediateExecution',
            type: 'primary',
            label: '立即执行',
            onClick: "bind:immediateExecution",
            disabled: "bind:_actionBtnNoSelected"
        },
        {
            name: 'del',//内置add del
            icon: 'delete',//icon
            type: 'danger',//类型  默认 primary  [primary dashed danger]
            label: '删除',
            fetchConfig: {//ajax配置
                apiName: 'removeZxQrtzJob',
            },
        },

        {
            field: 'logList',
            name: 'logList',
            type: 'primary',
            label: '日志列表',
            onClick: "bind:logList",
        },
    ],
    formConfig: [ 
        {
            table: {
                title: '表达式',
                dataIndex: 'cronExpression',
            }
            ,form: {
                type: 'cron', 
                required: true,
            }
        },
        {
            table: {
                title: '任务ID',
                dataIndex: 'jobName',
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
                placeholder: "给类名即可ZjWoaSyncJuToSubUserInfo"
            }
        },
        {
            table: {
                title: '任务名称',
                dataIndex: 'description',
                tooltip: 30,
                // width: 350, 
            }
            ,form: {
                type: 'string',
                placeholder: '有意义的汉子',
                required: true,
            }
        },
        {
            table: {
                title: '任务组',
                dataIndex: 'jobGroup',
            }
            ,form: {
                type: 'string',
                placeholder: '例如zjWoa',
                required: true,
            }
        },
        {
            table: {
                width: 200, 
                title: '类路径',
                dataIndex: 'jobClassName',
            }
            ,form: {
                type: 'string',
                placeholder: 'com.apih5.zjXxxxJob',
                required: true,
            }
        },
       
        {
            isInTable:false,
            table: {
                title: '参数',
                dataIndex: 'jobDataMap',
            }
            ,form: {
                type: 'textarea',
                placeholder: 'JSON格式  { "urlPar": "参数value" }',
                required: false,
                parser: function (value) {
                    return value && !geted ? JSON.stringify(val,null,4) : value
                },
                formatter: function (val) {
                    if (val && !geted) {
                        geted = true;
                        return JSON.stringify(val,null,4);
                    } else {
                        return val;
                    }
                },
            }
        },
        {
            isInForm: false,
            table: {
                width:100,
                title: '状态',
                dataIndex: 'triggerState',
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
            }
        },

        {
            isInForm: false,
            table: {
                showType: 'tile', //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                width: 80,
                title: "操作",
                key: "action",//操作列名称
                fixed: 'right',//固定到右边
                btns: [
                    {
                        label: "编辑",
                        name: 'edit',
                        formBtns: [
                            {
                                name: 'cancel', //关闭右边抽屉
                                type: 'dashed',//类型  默认 primary
                                label: '取消',
                            },
                            {
                                //自定义按钮key值 必须配置
                                field: "addCancelBtn",
                                name: 'submit',//内置add del
                                type: 'primary',//类型  默认 primary
                                label: '保存',//提交数据并且关闭右边抽屉
                                fetchConfig: {//ajax配置  ---可为func
                                    apiName: 'getZxQrtzJobDetailsListByJoin',
                                    // otherParams: "bind:addOtherParams"
                                }
                            }
                        ],
                    }
                ]
            }
        }

    ]
}
