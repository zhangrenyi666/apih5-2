
var geted = false;
window._SetTimeOutLogPage = {
    fetchConfig: {
        apiName: "getZxQrtzLogList",
    },
    antd: {
        rowKey: "logId",
        size: "small"
    },
    actionBtns: [

        {
            field: 'goBack',
            name: 'goBack',
            type: 'default',
            label: '返回',
            onClick: "bind:goBack",
            icon: "left"
        },

    ],
    formConfig: [

        {
            table: {
                title: '任务ID',
                dataIndex: 'jobName',
                onClick: 'detail',
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
                tooltip: 20
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
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
                placeholder: '请输入',
                required: true,
            }
        },
        {
            table: {
                title: '类路径',
                dataIndex: 'jobClass',
                width:220
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
            }
        },
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
            isInTable:false,
            table: {
                title: '参数',
                dataIndex: 'jobDataMap',
            }
            ,form: {
                type: 'textarea',
                placeholder: '请输入',
                required: true, 
            }
        },
        {
            // isInForm: false,
            table: {
                title: '状态',
                dataIndex: 'stateName',
                width:60
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
            }
        },
        {
            // isInForm: false,
            table: {
                title: '耗时/ms',
                dataIndex: 'jobRunTime',
                width:90
            }
            ,form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
            }
        },
        {
            // isInForm: false,
            table: {
                title: '执行时间',
                dataIndex: 'createTime',
                format:"YYYY-MM-DD HH:mm:ss",
                width:150
            }
            ,form: {
                type: 'datetime', 
                required: true,
            }
        },

    ]
}
