const TurnoverExamineAddData = {
    title: '出入机房申请表',
    tableData: [//有自带的默认数据
        {
            label: '进入时间',
            type: 'date',//字段类型
            name: 'entryTime',//唯一名字 
            disabled: false,//是否禁用
            required: true,//是否是必填
            errorMessage: '进入时间为必填项',
            mode:'datetime',//格式化时间可不填
            onChange: (v) => {}
        },
        {
            label: '预计离开时间',
            type: 'date',//字段类型
            mode:'datetime',//格式化时间可不填
            name: 'estimateLeaveTime',//唯一名字
            defaultValue: '',
            disabled: false,//是否禁用
            required: true,//是否是必填
            errorMessage: '预计离开时间为必填项',
            onChange: (v) => { }
        },
        {
            label: '编号',
            type: 'text',//字段类型
            name: 'approvalName',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用
            required: true,//是否是必填
            isHide:false,
            errorMessage: '编号为必填项',
            placeholder:'请输入编号',
            onChange: (v) => { }
        },
        {
            label: '申请人',
            type: 'text',//字段类型
            name: 'applyUserName',//唯一名字
            defaultValue: '', 
            required: true,//是否是必填
            disabled: true,//是否禁用
            errorMessage: '申请人为必填项',
            onChange: (v) => { }
        },
        {
            label: '联系方式',
            type: 'text',//字段类型
            name: 'applyUserTel',//唯一名字
            defaultValue: '', 
            // isHide:true,
            required: true,//是否是必填
            disabled: true,//是否禁用
            errorMessage: '联系方式为必填项',
            onChange: (v) => { }
        },
        {
            label: '所属单位',
            type: 'text',//字段类型
            name: 'subordinateUnit',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用
            // disabled: true,//是否禁用
            // isHide:true,
            required: true,//是否是必填
            errorMessage: '所属单位为必填项',
            onChange: (v) => { }
        },
        {
            label: '进入机房目的',
            placeholder:'请输入进入机房目的',
            type: 'textarea',//字段类型
            name: 'equipmentInvolving',//唯一名字
            defaultValue: '',
            disabled: false,//是否禁用
            required: true,//是否是必填
            errorMessage: '进入机房目的为必填项',
            onChange: (v) => { }
        },
        {
            label: '涉及机房内设备',
            type: 'text',//字段类型
            name: 'purpose',//唯一名字
            defaultValue: '',
            disabled: false,//是否禁用
            required: true,//是否是必填
            errorMessage: '涉及机房内设备为必填项',
            placeholder:'请输入涉及机房内设备',
            onChange: (v) => { }
        },
        {
            label: '申请单位审批结果',
            type: 'text',//字段类型
            name: 'unitExamineResult',//唯一名字 
            defaultValue: '',
            disabled: true,//是否禁用
            isHide:true,
            required: true,//是否是必填
            errorMessage: '申请单位审批结果为必填项',
            onChange: (v) => { }
        },
        {
            label: '选择申请单位审批人',
            type: 'oa',//字段类型
            name: 'applyExaminePer',//唯一名字 
            isHide: false,//是否隐藏
            defaultValue: '',
            disabled: false,//是否禁用
            required: true,//是否是必填
            errorMessage: '单位审批人为必填项',
            onChange: (v) => { }
        },
        {
            label: '上传图片',
            type: 'image',//字段类型
            name: 'imageList',//唯一名字
            isHide: false,//是否隐藏
            defaultValue: '',
            disabled: false,//是否禁用
            // required: true,//是否是必填
            errorMessage: '上传图片为必填项',
            onChange: (v) => { }
        }
    ]
}

const TurnoverExamineDetailData = {
    title: '出入机房审批详情',
    tableData: [//出入机房审批详情页面
        {
            type: 'text',//字段类型
            label: 'id',
            name:'approvalId',
            isHide:true
        },
        {
            label: '编号',
            type: 'text',//字段类型
            name: 'approvalName',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项',
            onChange: (v) => { }
        },
        {
            label: '申请人',
            type: 'text',//字段类型
            name: 'applyUserName',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        {
            label: '联系方式',
            type: 'text',//字段类型
            name: 'applyUserTel',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        }, 
        {
            label: '所属单位',
            type: 'text',//字段类型
            name: 'subordinateUnit',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        }, 
        {
            label: '进入时间',
            type: 'date',//字段类型
            name: 'entryTime',//唯一名字
            defaultValue: '',
            mode:'datetime',//格式化时间可不填
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        {
            label: '预计离开时间',
            type: 'date',//字段类型
            name: 'estimateLeaveTime',//唯一名字
            mode:'datetime',//格式化时间可不填
            defaultValue: '',
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        // {
        //     label: '创建者',
        //     type: 'text',//字段类型
        //     name: 'createUser',//唯一名字
        //     defaultValue: '',
        //     disabled: true,//是否禁用 
        //     errorMessage: '这个字段为必填项', 
        // },
        // {
        //     label: '更新者',
        //     type: 'text',//字段类型
        //     name: 'modifyUser',//唯一名字
        //     defaultValue: '',
        //     disabled: true,//是否禁用 
        //     errorMessage: '这个字段为必填项', 
        // },
        {
            label: '更新时间',
            type: 'date',//字段类型
            name: 'modifyTime',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        // {
        //     label: '申请单位是否审批',
        //     type: 'text',//字段类型
        //     isSelect:true,
        //     select:[{
        //         value:0,
        //         label:'已审批'
        //     },{
        //         value:1,
        //         label:'未审批'
        //     }],
        //     name: 'applyUnitFlag',//唯一名字
        //     defaultValue: '',
        //     disabled: true,//是否禁用 
        //     errorMessage: '这个字段为必填项', 
        // },
        // {
        //     label: '信息化部门人员是否审批',
        //     type: 'text',//字段类型
        //     isSelect:true,
        //     select:[{
        //         value:0,
        //         label:'已审批'
        //     },{
        //         value:1,
        //         label:'未审批'
        //     }],
        //     name: 'imDepartmentFlag',//唯一名字
        //     defaultValue: '',
        //     disabled: true,//是否禁用 
        //     errorMessage: '这个字段为必填项', 
        // },
        // {
        //     label: '信息化部门领导是否审批',
        //     type: 'text',//字段类型
        //     isSelect:true,
        //     select:[{
        //         value:0,
        //         label:'已审批'
        //     },{
        //         value:1,
        //         label:'未审批'
        //     }],
        //     name: 'imDepartmentLeaderFlag',//唯一名字
        //     defaultValue: '',
        //     disabled: true,//是否禁用 
        //     errorMessage: '这个字段为必填项', 
        // },
        {
            label: '图片',
            type: 'image',//字段类型
            name: 'imageList',//唯一名字
            defaultValue: '',
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        }, 
        {
            label: '申请单位人名称',
            type: 'text',//字段类型
            name: 'applyUnitUserName',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        }, 
        {
            label: '申请单位审批结果',
            type: 'text',//字段类型
            isSelect:true,
            select:[{
                value:0,
                label:'同意'
            },{
                value:1,
                label:'未同意'
            }],
            name: 'applyUnitResult',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        {
            label: '申请单位审批意见',
            type: 'textarea',//字段类型
            defaultValue:'无',
            name: 'applyUnitOpinion',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
            time:''
        },
        {
            label: '局信息化部门人员名称',
            type: 'text',//字段类型
            name: 'imDepartmentUserName',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        }, 
        {
            label: '局信息化管理部审批结果',
            type: 'text',//字段类型 
            isSelect:true,
            select:[{
                value:0,
                label:'同意'
            },{
                value:1,
                label:'未同意'
            }],
            name: 'imDepartmentResult',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        {
            label: '局信息化管理部审批意见',
            type: 'textarea',//字段类型
            name: 'imDepartmentOpinion',//唯一名字 
            disabled: true,//是否禁用 
            defaultValue:'无',
            errorMessage: '这个字段为必填项', 
            time:''
        }, 
        {
            label: '局信息化部门领导名称',
            type: 'text',//字段类型
            name: 'imDepartmentLeaderName',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        {
            label: '局信息化部门领导审批结果',
            type: 'text',//字段类型 
            isSelect:true,
            select:[{
                value:0,
                label:'同意'
            },{
                value:1,
                label:'未同意'
            }],
            name: 'imDepartmentLeaderResult',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
        },
        {
            label: '局信息化部门领导审批意见',
            type: 'textarea',//字段类型
            name: 'imDepartmentLeaderOpinion',//唯一名字 
            disabled: true,//是否禁用 
            errorMessage: '这个字段为必填项', 
            time:''
        }, 
        {
            label: '是否同意',
            explicitImplicit:true,//显隐
            type: 'radio',//字段类型
            name: 'approvalResult',//唯一名字 
            defaultValue:'无',
            disabled: false,//是否禁用 
            errorMessage: '这个字段为必填项', 
        }, 
        {
            label: '领导意见',
            explicitImplicit:true,//显隐
            type: 'textarea',//字段类型
            name: 'approvalOpinion',//唯一名字 
            disabled: false,//是否禁用 
            errorMessage: '这个字段为必填项', 
        }
    ]
}

export { TurnoverExamineAddData, TurnoverExamineDetailData };