import ProblemCard from '../ProblemCard'

//问题新增   人员用最新的那种选人
const problemAddData = {
    dev: false,//是否调试模式  
    formConfig: [//表格数据  //必填选项    
        {
            label: '选择项目',//中文名
            field: 'projectForWechat1',//字段 
            type: 'pullPerson',   // 拉人/部门类型
            fetchConfig: {
                params: [],
                apiName: 'getSysDepartmentAllTree',
            },
            // must: true,
            pullPersonOption: {//拉人组件专属配置 
                // routeControl:true,
                selectType: "1",
                meUrl: 'pullperson',
                search: true,
                searchApi: 'getSysDepartmentAllTree',
                searchParamsKey: 'search',
                maxNumber: 1,
            }
        },
        {
            label: '指定人员',//中文名
            field: 'chargeUserForWechat1',//字段 
            type: 'pullPerson',   // 拉人/部门类型
            fetchConfig: {
                params: [],
                apiName: 'getSysDepartmentUserAllTree',
            },
            // must: true,
            pullPersonOption: {//拉人组件专属配置 
                // routeControl:true,
                selectType: "2",
                meUrl: 'pullperson',
                search: true,
                searchApi: 'getSysDepartmentUserAllTree',
                searchParamsKey: 'search',
                maxNumber: 1,
            }
        },
        {
            label: '提问日期',//中文名
            field: 'dateOfQuestion',//字段 
            type: 'date',
            must: true,
        },
        {
            label: '截止日期',//中文名
            field: 'answerClosingDate',//字段 
            type: 'date',
            must: true,
        },
        {
            type: "linkage",
            field: "联动组",
            children: {
                type: "selectSearch",
                field: "questionTitle",
                label: "主题:",
                placeholder: '请选择',
                ajax: {
                    api: "getZjXmQuestionTitleSelectAllList",
                    value: "titleId",
                    label: "title",
                    searchParamsField: 'title' //搜索时给后台的搜索文字字段名
                    // parentParamsField: 'departmentName', //搜索时给后台的父级值的字段名
                    // params:{test:001}, //死参数
                },
                other: {
                    cols: 1
                },
                children: {
                    type: "selectSearch",
                    field: "departmentId",
                    label: "部门",
                    placeholder: '请选择',
                    parentName: 'questionTitle',
                    ajax: {
                        api: "getQuestionOneGradeSelectListForWechat",
                        value: "departmentId",
                        label: "departmentName",
                        searchParamsField: 'departmentName', //搜索时给后台的搜索文字字段名
                        parentParamsField: 'questionTitle', //搜索时给后台的父级值的字段名
                        // params:{test:001}, //死参数
                    },
                    other: {
                        disabled: true,
                        cols: 1
                    },
                    children: {
                        type: "selectSearch",
                        field: "questionId",
                        parentName: 'departmentId',
                        label: "类别",
                        placeholder: '请选择',
                        other: {
                            disabled: true,
                            cols: 1
                        },
                        ajax: {
                            api: "getQuestionTwoGradeSelectListForWechat",
                            value: "recordid",
                            label: "questionClassName",
                            searchParamsField: 'questionTitle', //搜索时给后台的字段
                            parentParamsField: 'department', //搜索时给后台的父级值的字段名 
                        },
                        children: {
                            type: "selectSearch",
                            field: "questionClassId",
                            parentName: 'questionId',
                            label: "检查项",
                            placeholder: '请选择',
                            other: {
                                disabled: true,
                                cols: 1
                            },
                            ajax: {
                                api: "getQuestionThreeGradeSelectLisForWechat",
                                value: "questionCheckItemId",
                                label: "questionCheckItemName",
                                searchParamsField: 'questionTitle', //搜索时给后台的字段
                                parentParamsField: 'recordid', //搜索时给后台的父级值的字段名 
                                setChildrenValue: {//子集是非下拉时才能有此属性
                                    api: "getQuestionFourGradeSelectListForWechat",
                                    parentParamsField: 'questionId', //搜索时给后台的父级值的字段名 
                                    value: "questionDescribe",
                                }
                            },
                            children: {
                                type: "textarea",
                                field: "questionDescribe",
                                label: "问题描述:",
                                parentName: 'questionClassId',
                                other: {
                                    placeholder: '请输入',
                                    disabled: true,
                                    rows: 5
                                }
                            }
                        }
                    }
                },
            },
        },
        {
            type: 'voice',
            label: '语音',//中文名
            field: 'askVoiceList',
            initialValue: [],
        },
        {
            label: '上传图片',//中文名
            field: 'imageList',//字段 
            type: 'images',
        }
    ],
    btns: [
        {
            label: '取消',//中文名
            field: 'cancelBtn',//字段      
            other: {
                type: 'primary'
            },
            click: ({ goBack }) => {
                goBack();
            }
        },
        {
            label: '提问',//中文名
            field: 'addBtn',//字段 
            isVerify: true,
            apiName: 'addZjQuestionApproval',
            other: {
                type: 'primary'
            },
            click: ({ success, message, push, Toast }) => {
                if (success) {
                    push('/problemList');
                }
            }
        },
        // {
        //     label: '测试',//中文名
        //     field: 'addBtn',//字段 
        //     isVerify: false, 
        //     other: {
        //         type: 'primary'
        //     },
        //     click: ({ values }) => {
        //         console.log(values)
        //     }
        // }
    ]

}

//问题详情
const problemDetailDataA = {
    dev: false,
    apiName: 'getQuestionDetailWechat',
    params: ['recordid', 'personnelFlag'],
    formConfig: [
        {
            type: 'text',
            field: 'recordid',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'remindFlag', //0显示提醒按钮  1不显示提醒按钮
            isHide: true,
        },
        {
            type: 'text',
            field: 'personnelFlag',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'displayFlag', //0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
            isHide: true,
        },
        {
            type: 'text',
            field: 'firstAnswerFlag',
            isHide: true,
        },
        {
            type: 'Component',
            field: 'ProblemCard',//字段  
            MyComponent: ProblemCard,
            fetchConfig: {
                apiName: 'getQuestionDetailWechat',
                params: ['recordid', 'personnelFlag']
            },
        }
    ],
    btns: [
        {
            label: '通过',//中文名
            field: 'overBtn',//字段  
            other: {//同步antd
                type: 'warning',
            },
            isHide: true,
            confirmText: '确定通过问题吗？',
            apiName: 'questionClose',
            params: ['recordid'],
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '2' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                },
                {
                    exg: { displayFlag: '3' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ push, success }) => {
                if (success) {
                    push('/problemList');
                }
            }
        },
        {
            label: '驳回',//中文名
            field: 'questionCloselyBtn',//字段   
            other: {
                type: 'primary'
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '2' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ myComponent }) => {
                let ProblemCard = myComponent.ProblemCardComponent;
                ProblemCard.setState({
                    modal1: true
                })
            }
        }, {
            label: '部门人员承办',//中文名
            field: 'questionTranspond',//字段   
            other: {
                type: 'primary'
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '2' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ myComponent }) => {
                let ProblemCard = myComponent.ProblemCardComponent;
                ProblemCard.setState({
                    modal4: true
                })
            }
        }, {
            label: '提醒',//中文名
            field: 'sendRemindForWechat',//字段   
            other: {
                type: 'primary'
            },
            params: ['recordid', 'firstAnswerFlag'],
            isHide: true,
            apiName: 'sendRemindForWechat',
            conitions: [//控制显隐
                {
                    exg: { remindFlag: '0' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
        },
        {
            label: '回复',//中文名
            field: 'answerBtn',//字段   
            other: {
                type: 'primary'
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { firstAnswerFlag: '0', displayFlag: '1' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ myComponent }) => {
                let ProblemCard = myComponent.ProblemCardComponent;
                ProblemCard.setState({
                    modal1: true
                })
            }
        },
    ]
}
const problemDetailDataAs = {
    dev: false,
    apiName: 'getQuestionLeaderDetailWechat',
    params: ['recordid', 'personnelFlag'],
    formConfig: [
        {
            type: 'text',
            field: 'recordid',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'remindFlag', //0显示提醒按钮  1不显示提醒按钮
            isHide: true,
        },
        {
            type: 'text',
            field: 'personnelFlag',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'displayFlag', //0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
            isHide: true,
        },
        {
            type: 'text',
            field: 'firstAnswerFlag',
            isHide: true,
        },
        {
            type: 'Component',
            field: 'ProblemCard',//字段  
            MyComponent: ProblemCard,
            fetchConfig: {
                apiName: 'getQuestionLeaderDetailWechat',
                params: ['recordid', 'personnelFlag']
            },
        }
    ],
    btns: [ 
        {
            label: '同意',//中文名
            field: 'consentBtn',//字段  
            other: {
                type: 'primary',
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '0' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ myComponent }) => {
                let ProblemCard = myComponent.ProblemCardComponent;
                ProblemCard.setState({
                    modal2: true
                })
            }
        },
        {
            label: '驳回',//中文名
            field: 'rejectBtn',//字段  
            other: {
                type: 'warning',
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '0' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ myComponent }) => {
                let ProblemCard = myComponent.ProblemCardComponent;
                ProblemCard.setState({
                    modal3: true
                })
            }
        }
    ]
}
//已阅
const problemDetailDatat = {
    dev: false,
    apiName: 'getQuestionDetailWechat',
    params: ['recordid', 'personnelFlag'],
    formConfig: [
        {
            type: 'text',
            field: 'recordid',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'remindFlag', //0显示提醒按钮  1不显示提醒按钮
            isHide: true,
        },
        {
            type: 'text',
            field: 'personnelFlag',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'displayFlag', //0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
            isHide: true,
        },
        {
            type: 'text',
            field: 'firstAnswerFlag',
            isHide: true,
        },
        {
            type: 'Component',
            field: 'ProblemCard',//字段  
            MyComponent: ProblemCard,
            fetchConfig: {
                apiName: 'getQuestionDetailWechat',
                params: ['recordid', 'personnelFlag']
            },
        }
    ],
    btns: [
        {
            label: '通过',//中文名
            field: 'overBtn',//字段  
            other: {//同步antd
                type: 'warning',
            },
            isHide: true,
            confirmText: '确定通过问题吗？',
            apiName: 'questionClose',
            params: ['recordid'],
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '2' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                },
                {
                    exg: { displayFlag: '3' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ replace, success }) => {
                if (success) {
                    replace('/problemList');
                }
            }
        },
        {
            label: '驳回',//中文名
            field: 'questionCloselyBtn',//字段   
            other: {
                type: 'primary'
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '2' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ myComponent }) => {
                let ProblemCard = myComponent.ProblemCardComponent;
                ProblemCard.setState({
                    modal1: true
                })
            }
        }, {
            label: '部门人员承办',//中文名
            field: 'questionTranspond',//字段   
            other: {
                type: 'primary'
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '2' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            click: ({ myComponent }) => {
                let ProblemCard = myComponent.ProblemCardComponent;
                ProblemCard.setState({
                    modal4: true
                })
            }
        }, {
            label: '提醒',//中文名
            field: 'sendRemindForWechat',//字段   
            other: {
                type: 'primary'
            },
            params: ['recordid', 'firstAnswerFlag'],
            isHide: true,
            apiName: 'sendRemindForWechat',
            conitions: [//控制显隐
                {
                    exg: { remindFlag: '0' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                }
            ],
        },
        {
            label: '已阅',//中文名
            field: 'answerBtn',//字段   
            other: {
                type: 'primary'
            },
            isHide: true,
            conitions: [//控制显隐
                {
                    exg: { firstAnswerFlag: '0', displayFlag: '1' },//条件
                    action: 'show', //动作 目前只写了  (hide、show、disabled)
                },
                {
                    exg: { questionState: '4' },//条件
                    action: 'hide', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            apiName: 'updateQueImprovementItemByChangerForWechat',
            click: ({ success, message, push, Toast }) => {
                if (success) {
                    push('/problemList');
                }
            }
        },
    ]
}
//问题回复
const problemReplyData = {
    dev: false,//是否调试模式 
    apiName: 'getQuestionDetailWechat', 
    params: ['recordid', 'personnelFlag'],
    style: {
        // height: '40vh',
        minHeight: '200px',
        paddingBottom: '10px'
    },
    formConfig: [//表格数据  //必填选项  
        {
            type: 'text',
            label: '主键id',//中文名
            field: 'recordid',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            label: '其他id',//中文名
            field: 'firstAnswerFlag',
            isHide: true,
            // must: true,
        },
        {
            type: 'text',
            label: '当前状态',//中文名
            field: 'displayFlag',
            isHide: true,
            must: true,
        },
        {
            type: 'text',
            field: 'departmentName',
            isHide: true,
        },
        {
            type: 'text',
            label: '人员',//中文名
            field: 'personnelFlag',
            isHide: true,
            isUrlParams: true,
        },
        {
            label: '回复对象',//中文名 回答的时候出现
            field: 'replyObject',//字段 
            type: 'text',
            must: true,
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            label: '项目经理',//中文名 
            field: 'oaProjectManagerId',//字段 
            type: 'select',
            must: true,
            fetchConfig: {
                apiName: 'getZjQuestionPAllListByProjectForWechat',
                params: ['recordid'],
                option: {
                    label: 'name',
                    value: 'orgId',
                    otherId: 'userid',
                    realValue: ['userid', 'name', 'orgId'],//可不传 最后传给后台的参数是label值+otherId值
                },
            },
            conitions: [//控制显隐
                {
                    exg: { displayFlag: '2' },//条件
                    action: 'hide', //动作 目前只写了  (hide、show、disabled)
                }
            ],
            other: {//同步蚂蚁
                cols: 1,
            },
        },
        {
            type: 'voice',
            label: '语音',//中文名
            field: 'voiceList',
            initialValue: []
        },
        {
            label: '图片列表',//中文名
            field: 'imageList',//字段 
            type: 'images',
            conitions: [//控制显隐  追问的时候不让上传图片
                {//displayFlag  0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
                    exg: {
                        firstAnswerFlag: '1'
                    },//条件
                    action: 'hide', //动作 目前只写了  (hide、show、disabled)
                },
                {//displayFlag  0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
                    exg: {
                        displayFlag: '2',
                    },//条件
                    action: 'hide', //动作 目前只写了  (hide、show、disabled)
                }
            ],
        },
        {
            // label: '回复内容',//中文名
            field: 'askContent',//字段 
            type: 'textarea',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入问题回复',
                rows: 5,
                count: 300,
            }
        },
    ],

}

const problemAgree = {
    dev: false,//是否调试模式 
    apiName: 'getQuestionDetailWechat',
    params: ['recordid', 'personnelFlag'],
    style: {
        // height: '40vh',
        minHeight: '200px',
        paddingBottom: '10px'
    },
    formConfig: [//表格数据  //必填选项  
        {
            type: 'text',
            field: 'recordid',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'remindFlag', //0显示提醒按钮  1不显示提醒按钮
            isHide: true,
        },
        {
            type: 'text',
            field: 'personnelFlag',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'displayFlag', //0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
            isHide: true,
        },
        {
            type: 'text',
            field: 'firstAnswerFlag',
            isHide: true,
        },
        {
            type: "linkage",
            field: "联动组",
            children: {
                type: "selectSearch",
                field: "checkUserId",
                label: "部门负责人:",
                placeholder: '请选择',
                must: true,
                ajax: {
                    api: "getZjXmQuestionOfficeLeaderAllList",
                    value: "oaUserId",
                    label: "oaUserName",
                },
                other: {
                    cols: 1
                },
            },
        },
        {
            // label: '回复内容',//中文名
            field: 'leaderOption',//字段 
            type: 'textarea',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入意见',
                rows: 5,
                count: 300,
            }
        }
    ],

}

const problemAgrees = {
    dev: false,//是否调试模式 
    apiName: 'getQuestionDetailWechat',
    params: ['recordid', 'personnelFlag'],
    style: {
        // height: '40vh',
        minHeight: '200px',
        paddingBottom: '10px'
    },
    formConfig: [//表格数据  //必填选项  
        {
            type: 'text',
            field: 'recordid',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'remindFlag', //0显示提醒按钮  1不显示提醒按钮
            isHide: true,
        },
        {
            type: 'text',
            field: 'personnelFlag',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'displayFlag', //0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
            isHide: true,
        },
        {
            type: 'text',
            field: 'firstAnswerFlag',
            isHide: true,
        },
        {
            // label: '回复内容',//中文名
            field: 'leaderOption',//字段 
            type: 'textarea',
            must: true,
            other: {//同步蚂蚁  
                placeholder: '请输入意见',
                rows: 5,
                count: 300,
            }
        }
    ],

}

const problemAgreet = {
    dev: false,//是否调试模式 
    apiName: 'getQuestionDetailWechat',
    params: ['recordid', 'personnelFlag'],
    style: {
        // height: '40vh',
        minHeight: '200px',
        paddingBottom: '10px'
    },
    formConfig: [//表格数据  //必填选项  
        {
            type: 'text',
            field: 'recordid',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'remindFlag', //0显示提醒按钮  1不显示提醒按钮
            isHide: true,
        },
        {
            type: 'text',
            field: 'personnelFlag',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'displayFlag', //0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
            isHide: true,
        },
        {
            type: 'text',
            field: 'firstAnswerFlag',
            isHide: true,
        },
        {
            label: '转发人员',//中文名 
            field: 'forwardUserId',//字段 
            type: 'select',
            must: true,
            fetchConfig: {
                apiName: 'getZjQuestionPAllListByProjectForWechat',
                params: ['recordid'],
                option: {
                    label: 'name',
                    value: 'orgId',
                    otherId: 'userid',
                    realValue: ['userid', 'name', 'orgId'],//可不传 最后传给后台的参数是label值+otherId值
                },
            },
            other: {//同步蚂蚁
                cols: 1,
            },
        },
    ],

}

const problemAddDatas = {
    dev: false,//是否调试模式 
    apiName: 'getZjQuestionCheckItemByProjectIdForWechat',
    params: ['screenId'],
    formConfig: [//表格数据  //必填选项    
        {
            type: 'text',
            field: 'screenId',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'projectId',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'projectOrgId',
            isHide: true,
            isUrlParams: true,
        },
        {
            label: '所属项目',
            type: 'text',
            field: 'projectName',
            isUrlParams: true,
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            label: '指定人员',//中文名
            field: 'chargeUserForWechat1',//字段 
            type: 'pullPerson',   // 拉人/部门类型
            fetchConfig: {
                params: [],
                apiName: 'getSysDepartmentUserAllTree',
            },
            // must: true,
            pullPersonOption: {//拉人组件专属配置 
                // routeControl:true,
                selectType: "2",
                meUrl: 'pullperson',
                search: true,
                searchApi: 'getSysDepartmentUserAllTree',
                searchParamsKey: 'search',
                maxNumber: 1,
            }
        },
        {
            label: '提问日期',//中文名
            field: 'dateOfQuestion',//字段 
            type: 'date',
            must: true,
        },
        {
            label: '截止日期',//中文名
            field: 'answerClosingDate',//字段 
            type: 'date',
            must: true,
        },
        {
            type: 'text',
            field: 'questionTitleId',
            isHide: true,
        },
        {
            label: '主题',
            type: 'text',
            field: 'questionTitle',
            isUrlParams: true,
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            type: 'text',
            field: 'departmentId',
            isHide: true,
        },
        {
            label: '部门',
            type: 'text',
            field: 'departmentName',
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            type: 'text',
            field: 'questionClassId',
            isHide: true,
        },
        {
            label: '类别',
            type: 'text',
            field: 'questionClassName',
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            type: 'text',
            field: 'questionCheckItemId',
            isHide: true,
        },
        {
            label: '检查项',
            type: 'text',
            field: 'questionCheckItemName',
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            label: '问题描述',
            type: 'textarea',
            field: 'questionDescribe',
			must:true,
            other: {
                placeholder: '请输入',
                rows: 5
            }
        },
        {
            type: 'voice',
            label: '语音',//中文名
            field: 'askVoiceList',
            initialValue: [],
        },
        {
            label: '上传图片',//中文名
            field: 'imageList',//字段 
            type: 'images',
        }
    ],
    btns: [
        {
            label: '取消',//中文名
            field: 'cancelBtn',//字段      
            other: {
                type: 'primary'
            },
            click: ({ goBack }) => {
                goBack();
            }
        },
        {
            // label: '提问',//中文名
            label: '提交',//中文名
            field: 'addBtn',//字段 
            isVerify: true,
            apiName: 'addZjQueRectificationAddInforIdForWechat',
            other: {
                type: 'primary'
            },
            click: ({ success, message, push, Toast }) => {
                if (success) {
                    push('/problemList');
                }
            }
        },
    ]

}

const problemAddDatat = {
    dev: false,//是否调试模式 
    apiName: 'getZjQuestionCheckItemByProjectIdForWechat',
    params: ['screenId'],
    formConfig: [//表格数据  //必填选项    
        {
            type: 'text',
            field: 'screenId',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'projectId',
            isHide: true,
            isUrlParams: true,
        },
        {
            type: 'text',
            field: 'projectOrgId',
            isHide: true,
            isUrlParams: true,
        },
        {
            label: '所属项目',
            type: 'text',
            field: 'projectName',
            isUrlParams: true,
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            label: '指定人员',//中文名
            field: 'chargeUserForWechat1',//字段 
            type: 'pullPerson',   // 拉人/部门类型
            fetchConfig: {
                params: [],
                apiName: 'getSysDepartmentUserAllTree',
            },
            // must: true,
            pullPersonOption: {//拉人组件专属配置 
                // routeControl:true,
                selectType: "2",
                meUrl: 'pullperson',
                search: true,
                searchApi: 'getSysDepartmentUserAllTree',
                searchParamsKey: 'search',
                maxNumber: 1,
            }
        },
        {
            label: '提问日期',//中文名
            field: 'dateOfQuestion',//字段 
            type: 'date',
            must: true,
        },
        {
            label: '截止日期',//中文名
            field: 'answerClosingDate',//字段 
            type: 'date',
            must: true,
        },
        {
            type: 'text',
            field: 'questionTitleId',
            isHide: true,
        },
        {
            label: '主题',
            type: 'text',
            field: 'questionTitle',
            isUrlParams: true,
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            type: 'text',
            field: 'departmentId',
            isHide: true,
        },
        {
            label: '部门',
            type: 'text',
            field: 'departmentName',
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            type: 'text',
            field: 'questionClassId',
            isHide: true,
        },
        {
            label: '类别',
            type: 'text',
            field: 'questionClassName',
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            type: 'text',
            field: 'questionCheckItemId',
            isHide: true,
        },
        {
            label: '检查项',
            type: 'text',
            field: 'questionCheckItemName',
            other: {//同步蚂蚁 
                disabled: true,
            }
        },
        {
            label: '问题描述',
            type: 'textarea',
            field: 'questionDescribe',
			must:true,
            other: {
                placeholder: '请输入',
                rows: 5
            }
        },
        {
            type: 'voice',
            label: '语音',//中文名
            field: 'askVoiceList',
            initialValue: [],
        },
        {
            label: '上传图片',//中文名
            field: 'imageList',//字段 
            type: 'images',
        }
    ],
    btns: [
        {
            label: '取消',//中文名
            field: 'cancelBtn',//字段      
            other: {
                type: 'primary'
            },
            click: ({ goBack }) => {
                goBack();
            }
        },
        {
            label: '提问',//中文名
            field: 'addBtn',//字段 
            isVerify: true,
            apiName: 'addZjQueImprovementAddInforIdForWechat',
            other: {
                type: 'primary'
            },
            click: ({ success, message, push, Toast }) => {
                if (success) {
                    push('/problemList');
                }
            }
        },
    ]

}
export {
    problemAddData,
    problemReplyData,
    problemAgree,
    problemAgrees,
    problemAgreet,
    problemDetailDataA,
    problemDetailDataAs,
    problemAddDatas,
    problemAddDatat,
    problemDetailDatat
}; 