
//车辆年检
const inspectionAddformData = {
    dev: true,//是否调试模式
    apiName: 'getZjCjCarRemind',//有的话会直接去请求数据
    params: ['id'],//请求的参数
    formConfig: [//表格数据  //必填选项
        {
            label: '车辆id',//中文名
            field: 'id',//字段 
            type: 'text',
            isHide: true,
            isUrlParams: true,//是否从浏览器上取值
        }, {
            label: '人员approvalUserId',//中文名
            field: 'approvalUserId',//字段 
            type: 'text',
            isHide: true,
        }, {
            label: '办理类型',//中文名
            field: 'approvalType',//字段 
            type: 'text',
            initialValue: '0',//0 车辆年检  1车辆车检
            isHide: true,
        },
        {
            label: '车辆名称',//中文名
            field: 'carName',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆名称',
                editable: false,//禁止编辑
            }
        },
        {
            label: '车辆型号',//中文名
            field: 'modelNumber',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆型号',
                editable: false,//禁止编辑
            }
        },
        {
            label: '使用单位',//中文名
            field: 'useUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入使用单位',
                editable: false,//禁止编辑
            }
        },
        {
            label: '所属单位',//中文名
            field: 'subordinateUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所属单位',
                editable: false,//禁止编辑
            }
        },
        {
            label: '所在地点',//中文名
            field: 'location',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所在地点',
                editable: false,//禁止编辑
            }
        },
        {
            label: '审批人员',//中文名
            field: 'approvalUser',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                editable: false,//禁止编辑
                placeholder: '请输入审批人员',
            }
        },
        {
            label: '办理类型',//中文名
            field: 'approvalTypeText',//字段 
            type: 'text',
            initialValue: '车辆年检',
            other: {//同步蚂蚁   
                editable: false,//禁止编辑
            }
        },
        {
            label: '入账日期',//中文名
            field: 'accountedForDate',//字段 
            type: 'date',
            must: true,
            other: {
                mode: 'date',
            }
        },
        {
            label: '图片',//中文名
            field: 'imageList',//字段 
            type: 'images',
            other: {}
        }
    ],
    btns: [//yes(确定按钮 必选 valus在回调中)  ...（other） 
        {
            field: 'cancel',//提交按钮
            label: '取消申请',
            confirmText: '确定取消吗？',
            other: {//同步蚂蚁按钮类型
                type: 'ghost',
            },
            click: ({ goBack }) => {
                goBack()
            }
        },
        {
            type: 'sendAjax',//直接发送ajax 
            field: 'yes',//提交按钮
            label: '提交申请',
            apiName: 'addZjCjApplicationForApproval',
            confirmText: '确定提交吗？',//有提示文字会自动弹出提示框  string || {desc,title}
            other: {//同步蚂蚁按钮类型
                type: 'primary'
            },
            click: ({ replace }) => {
                replace('/applyList');
            }
        }
    ]
}

//车辆保险数据
const insuranceformData = {
    dev: true,//是否调试模式
    apiName: 'getZjCjCarRemind',//有的话会直接去请求数据
    params: ['id'],//请求的参数
    formConfig: [//表格数据  //必填选项
        {
            label: '车辆id',//中文名
            field: 'id',//字段 
            type: 'text',
            isHide: true,
            isUrlParams: true,//是否从浏览器上取值
        }, {
            label: '人员approvalUserId',//中文名
            field: 'approvalUserId',//字段 
            type: 'text',
            isHide: true,
        }, {
            label: '办理类型',//中文名
            field: 'approvalType',//字段 
            type: 'text',
            initialValue: '1',//0 车辆年检  1车辆保险
            isHide: true,
        },
        {
            label: '车辆名称',//中文名
            field: 'carName',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆名称',
                editable: false,//禁止编辑
            }
        },
        {
            label: '车辆型号',//中文名
            field: 'modelNumber',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆型号',
                editable: false,//禁止编辑
            }
        },
        {
            label: '使用单位',//中文名
            field: 'useUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入使用单位',
                editable: false,//禁止编辑
            }
        },
        {
            label: '所属单位',//中文名
            field: 'subordinateUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所属单位',
                editable: false,//禁止编辑
            }
        },
        {
            label: '所在地点',//中文名
            field: 'location',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所在地点',
                editable: false,//禁止编辑
            }
        },
        {
            label: '审批人员',//中文名
            field: 'approvalUser',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                editable: false,//禁止编辑
                placeholder: '请输入审批人员',
            }
        },
        {
            label: '办理类型',//中文名
            field: 'approvalTypeText',//字段 
            type: 'text',
            initialValue: '车辆保险',
            other: {//同步蚂蚁   
                editable: false,//禁止编辑
            }
        },
        {
            label: '入账日期',//中文名
            field: 'accountedForDate',//字段 
            type: 'date',
            must: true,
            other: {
                mode: 'date',
            }
        },
        {
            label: '图片',//中文名
            field: 'imageList',//字段 
            type: 'images',
            other: {}
        }
    ],
    btns: [//yes(确定按钮 必选 valus在回调中)  ...（other） 
        {
            field: 'cancel',//提交按钮
            label: '取消申请',
            confirmText: '确定取消吗？',
            other: {//同步蚂蚁按钮类型
                type: 'ghost',
            },
            click: ({ goBack }) => {
                goBack()
            }
        },
        {
            type: 'sendAjax',//直接发送ajax 
            field: 'yes',//提交按钮
            label: '提交申请',
            apiName: 'addZjCjApplicationForApproval',
            confirmText: '确定提交吗？',//有提示文字会自动弹出提示框  string || {desc,title}
            other: {//同步蚂蚁按钮类型
                type: 'primary'
            },
            click: ({ replace }) => {
                replace('/applyList');
            }
        }
    ]
}

//车辆详情数据
const carDetailData = {
    dev: true,//是否调试模式 
    apiName: 'getZjCjCarRemind',//有的话会直接去请求数据并给表单赋值
    params: ['id'],//请求的参数 
    formConfig: [//表格数据  //必填选项
        {
            label: '车辆id',//中文名
            field: 'id',//字段 
            type: 'text',
            isHide: true,
            isUrlParams: true,
        },
        {
            label: '年检状态',//中文名
            field: 'isCarCheck',//字段 
            type: 'text',
            isHide: true,
        },
        {
            label: '保险状态',//中文名
            field: 'isCarInsurance',//字段 
            type: 'text',
            isHide: true,
        },
        {
            label: '车辆名称',//中文名
            field: 'carName',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆名称',
                disabled: true,
            }
        },
        {
            label: '车辆型号',//中文名
            field: 'modelNumber',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆型号',
                disabled: true,
            }
        },
        {
            label: '使用单位',//中文名
            field: 'useUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入使用单位',
                disabled: true,
            }
        },
        {
            label: '所属单位',//中文名
            field: 'subordinateUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所属单位',
                disabled: true,
            }
        },
        {
            label: '所在地点',//中文名
            field: 'location',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所在地点',
                disabled: true,
            }
        },
        {
            label: '入账日期',//中文名
            field: 'accountedForDate',//字段 
            type: 'date',
            must: true,
            other: {
                mode: 'date',
                disabled: true,
                arrow: false
            }
        }
    ],
    btns: [
        {
            label: '返回',//中文名
            field: 'goback',//字段 
            isHide: true,
            conitions: [//（0：未办理；1：代办理；2：审批中；3：通过；4：驳回）
                {
                    exg: { isCarInsurance: '0' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarInsurance: '1' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarInsurance: '4' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarCheck: '0' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarCheck: '1' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarCheck: '4' },//值
                    action: 'show', //动作
                }
            ],
            other: {
                type: 'ghost'
            },
            click: ({ goBack }) => {
                goBack()
            }
        },
        {
            label: '年检申请',//中文名
            field: 'goYear',//字段   
            isHide: true,
            conitions: [//（0：未办理；1：代办理；2：审批中；3：通过；4：驳回） 
                {
                    exg: { isCarCheck: '0' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarCheck: '1' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarCheck: '4' },//值
                    action: 'show', //动作
                }
            ],
            other: {
                type: 'primary'
            },
            click: ({ push, values }) => {
                push(`/applyAdd/${values.id}`)
            }
        },
        {
            label: '保险申请',//中文名
            field: 'goInsurance',//字段   
            isHide: true,
            conitions: [//（0：未办理；1：代办理；2：审批中；3：通过；4：驳回）
                {
                    exg: { isCarInsurance: '0' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarInsurance: '1' },//值
                    action: 'show', //动作
                },
                {
                    exg: { isCarInsurance: '4' },//值
                    action: 'show', //动作
                }
            ],
            other: {
                type: 'primary'
            },
            click: ({ push, values }) => {
                push(`/insuranceAdd/${values.id}`)
            }
        }
    ]
}

//申请详情数据
const applyDetailformData = {
    dev: true,//是否调试模式
    apiName: 'getApplyDetailForWechat',//有的话会直接去请求数据
    params: ['approvalId'],//请求的参数
    formConfig: [//表格数据  //必填选项
        {
            label: '审批人id',//中文名
            field: 'approvalId',//字段 
            type: 'text',
            isHide: true,
            isUrlParams: true,//是否从浏览器上取值
        }, {
            label: '审批状态',//中文名
            field: 'approvalFlag',//字段 
            type: 'text',
            isHide: true,
        },{
            label: '人员approvalUserId',//中文名
            field: 'approvalUserId',//字段 
            type: 'text',
            isHide: true,
        },
        {
            label: '车辆名称',//中文名
            field: 'carName',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆名称',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '车辆型号',//中文名
            field: 'modelNumber',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆型号',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '使用单位',//中文名
            field: 'useUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入使用单位',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '所属单位',//中文名
            field: 'subordinateUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所属单位',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '所在地点',//中文名
            field: 'location',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所在地点',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '审批人员',//中文名
            field: 'approvalUser',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                disabled: true,//禁止编辑
                placeholder: '请输入审批人员',
            }
        },
        {
            label: '办理类型',//中文名
            field: 'approvalType',//字段 
            type: 'text',
            initialValue: '车辆年检',
            render: ({ value, prev, allValue }) => {
                if (value === '0' || value === '车辆年检') {
                    return '车辆年检'
                } else if (value === '1' || value === '车辆保险') {
                    return '车辆保险'
                } else {
                    return '未知'
                }
            },
            other: {//同步蚂蚁   
                disabled: true
            }
        },
        {
            label: '入账日期',//中文名
            field: 'accountedForDate',//字段 
            type: 'date',
            must: true,
            other: {
                mode: 'date',
                disabled: true
            }
        },
        {
            label: '图片列表',//中文名
            field: 'imageList',//字段 
            type: 'images',
            selectable: false,
            other: {
                disabled: true,
            }
        },
        {
            label: '审批结果',//中文名
            field: 'approvalResult',//字段 
            type: 'text',
            isHide: true,
            conitions: [//（0：未审批；1：已审批） 
                {
                    exg: { approvalFlag: '0' },//值
                    action: 'hide', //动作
                },
                {
                    exg: { approvalFlag: '1' },//值
                    action: 'show', //动作
                }
            ],
            render: ({ value, prev, allValue }) => {
                if (value === '0' || value === '审批通过') {
                    return '审批通过'
                } else if (value === '1' || value === '审批驳回') {
                    return '审批驳回'
                } else {
                    return '未知'
                }
            },
            other: {
                disabled: true,
                placeholder: '审批结果',
            }
        },
        {
            label: '审批意见',//中文名
            field: 'approvalOpinion',//字段 
            type: 'textarea',
            isHide: true,
            other: {
                rows: 5,
                placeholder: '请输入审批意见', 
            },
            render: ({ value, prev, allValue }) => {
                let { approvalFlag } = allValue 
                if (!value && approvalFlag === '1') {
                    return '未填写审批意见';
                } else {
                    return value;
                }
            },
            conitions: [//（0：未审批；1：已审批） 
                {
                    exg: { approvalFlag: '1' },//值
                    action: 'show', //动作
                },
                {
                    exg: { approvalFlag: '0' },//值
                    action: 'hide', //动作
                },
                {
                    exg: { approvalFlag: '1' },//值
                    action: 'disabled', //动作
                }
            ],
        }
    ]
}

//审批详情
const approvalDetailformData = {
    dev: true,//是否调试模式
    apiName: 'getApplyDetailForWechat',//有的话会直接去请求数据
    params: ['approvalId'],//请求的参数
    formConfig: [//表格数据  //必填选项
        {
            label: '车辆id',//中文名
            field: 'id',//字段 
            type: 'text',
            isHide: true,
            isUrlParams: true,//是否从浏览器上取值
        },
        {
            label: '申请人id',//中文名
            field: 'applyUserId',//字段 
            type: 'text', 
            isHide: true, 
        }, {
            label: '审批状态',//中文名
            field: 'approvalFlag',//字段 
            type: 'text',
            isHide: true,
        }, {
            label: '审批id',//中文名
            field: 'approvalId',//字段 
            type: 'text',
            isHide: true,
        },
        {
            label: '办理类型',//中文名
            field: 'approvalType',//字段 
            type: 'text',
            isHide:true,
        },
        {
            label: '车辆名称',//中文名
            field: 'carName',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆名称',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '车辆型号',//中文名
            field: 'modelNumber',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入车辆型号',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '使用单位',//中文名
            field: 'useUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入使用单位',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '所属单位',//中文名
            field: 'subordinateUnit',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所属单位',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '所在地点',//中文名
            field: 'location',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入所在地点',
                disabled: true,//禁止编辑
            }
        },
        {
            label: '申请人员',//中文名
            field: 'applyUser',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                disabled: true,//禁止编辑
                placeholder: '暂无数据',
            }
        },
        {
            label: '审批人员',//中文名
            field: 'approvalUser',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁  
                disabled: true,//禁止编辑
                placeholder: '请输入审批人员',
            }
        },
        {
            label: '办理类型',//中文名
            field: 'approvalTypeText',//字段 
            type: 'text',
            initialValue: '车辆年检',
            render: ({ value, prev, allValue }) => {
                let { approvalType } = allValue;
                if (approvalType === '0' || approvalType === '车辆年检') {
                    return '车辆年检'
                } else if (approvalType === '1' || approvalType === '车辆保险') {
                    return '车辆保险'
                } else {
                    return '未知'
                }
            },
            other: {//同步蚂蚁   
                disabled: true
            }
        },
        {
            label: '入账日期',//中文名
            field: 'accountedForDate',//字段 
            type: 'date',
            must: true,
            other: {
                mode: 'date',
                disabled: true
            }
        },
        {
            label: '图片列表',//中文名
            field: 'imageList',//字段 
            type: 'images',
            selectable: false,
            other: {
                disabled: true
            }
        },
        {
            label: '审批结果',//中文名
            field: 'approvalResult',//字段 
            type: 'text',
            isHide: true,
            conitions: [//（0：已审批；1：未审批） 
                {
                    exg: { approvalFlag: '0' },//值
                    action: 'hide', //动作
                },
                {
                    exg: { approvalFlag: '1' },//值
                    action: 'show', //动作
                }
            ],
            render: ({ value, prev, allValue }) => {
                if (value === '0' || value === '审批通过') {
                    return '审批通过'
                } else if (value === '1' || value === '审批驳回') {
                    return '审批驳回'
                } else {
                    return '未知'
                }
            },
            other: {
                disabled: true,
                placeholder: '审批结果',
            }
        },
        {
            label: '审批意见',//中文名
            field: 'approvalOpinion',//字段 
            type: 'textarea',
            other: {
                rows: 5,
                placeholder: '请输入审批意见',
                autoFocus:true
            },
            render: ({ value, prev, allValue }) => {
                let { approvalFlag } = allValue 
                if (!value && approvalFlag === '1') {
                    return '未填写审批意见';
                } else {
                    return value;
                }
            },
            conitions: [//（0：已审批；1：未审批） 
                {
                    exg: { approvalFlag: '1' },//值
                    action: 'disabled', //动作
                }
            ],
        }
    ],
    btns: [
        {
            label: '驳回',//中文名
            field: 'disagree',//字段   
            isHide: true,
            confirmText: '确定驳回吗？',//有提示文字会自动弹出提示框  string || {desc,title}
            conitions: [//（0：已审批；1：未审批）
                {
                    exg: { approvalFlag: '0' },//值
                    action: 'show', //动作
                }
            ],
            other: {
                type: 'warning'
            },
            click: ({ replace, values, myFetch, Toast }) => {
                let _values = values;
                _values.approvalResult = '1';//0同意  1不同意
                console.log('不同意_values：', _values)
                Toast.loading('提交中...', 0); 
                myFetch('updateZjCjApplicationForApproval', _values).then(({ success, message, data }) => {
                    console.log(success, message, data)
                    Toast.hide()
                    if (success) {
                        Toast.success(message, 3, () => {
                            replace('/examineList');
                        })
                    } else {
                        Toast.fail(message, 2)
                    }
                })
            }
        },
        {
            label: '同意',//中文名
            field: 'consent',//字段   
            isHide: true,
            confirmText: '确定同意吗？',//有提示文字会自动弹出提示框  string || {desc,title}
            conitions: [//（0：已审批；1：未审批） 
                {
                    exg: { approvalFlag: '0' },//值
                    action: 'show', //动作
                }
            ],
            other: {
                type: 'primary'
            },
            click: ({ replace, values, myFetch, Toast }) => {
                let _values = values;
                _values.approvalResult = '0';//0同意  1不同意
                console.log('_values：', _values)
                Toast.loading('提交中...', 0); 
                myFetch('updateZjCjApplicationForApproval', _values).then(({ success, message, data }) => {
                    Toast.hide()
                    if (success) {
                        Toast.success(message, 3, () => {
                            replace('/examineList');
                        })
                    } else {
                        Toast.fail(message, 2)
                    }
                })
            }
        }
    ]
}

export {
    inspectionAddformData,
    insuranceformData,
    carDetailData,
    applyDetailformData,
    approvalDetailformData
}; 