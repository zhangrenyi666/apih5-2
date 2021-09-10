//内部人员

//一行一个输入控件的布局和 formItemWrapperStyle
var oneItemLayout = {
    labelCol: {
        sm: { span: 2 }
    },
    wrapperCol: {
        sm: { span: 22 }
    }
};
var oneFormItemWrapperStyle = {
    marginLeft: -5
}

//一行四个字段
var fourItemSpan = 6;
var fourItemLayout = {
    labelCol: {
        sm: { span: 8 }
    },
    wrapperCol: {
        sm: { span: 16 }
    }
};
//和一行四个字段一样 只是label多了一点长度
var fourItemLayout2 = {
    labelCol: {
        sm: { span: 12 }
    },
    wrapperCol: {
        sm: { span: 12 }
    }
};

const updateFetchConfigs = {
    heTongGuanLi: {
        tdEditFetchConfig: {
            apiName: "updateZjXmSalaryContractManagement",
        }
    },
    training: {
        tdEditFetchConfig: {
            apiName: "updateZjXmSalaryTrainingSituation",
        }
    },
    family: {
        tdEditFetchConfig: {
            apiName: "updateZjXmSalaryFamilyBackground",
        }
    },
    health: {
        tdEditFetchConfig: {
            apiName: "updateZjXmSalaryHealthCondition",
        }
    },
    certificate: {
        tdEditFetchConfig: {
            apiName: "updateZjXmSalaryCertificateManagement",
        }
    },
    countenance: {
        tdEditFetchConfig: {
            apiName: "updateZjXmSalaryTrainingSituation",
        }
    }
}

window.personPage = {
    antd: {
        rowKey: "extensionId",
        size: 'small'
    },
    drawerConfig: {
        width: "90%"
    },
    actionBtns: [
        {
            name: "add", //内置add del
            icon: "plus", //icon
            type: "primary", //类型  默认 primary  [primary dashed danger]
            label: "新增",
            formBtns: [
                {
                    name: "cancel", //关闭右边抽屉
                    type: "dashed", //类型  默认 primary
                    label: "取消"
                },

                {
                    field: "submit",
                    name: "submit",
                    type: "primary",
                    label: "提交",
                    fetchConfig: {
                        apiName: 'addZjXmSalaryUserExtension',
                        params: {},
                        otherParams: {},
                    },
                }
            ]
        },
        {
            field: 'submitsp',
            name: 'diyCommit',
            fetchConfig: {//ajax配置
                apiName: '提交审批',
            },
            type: 'primary',
            disabled: "bind:_actionBtnNoSelected",
            label: '提交审批',
        },
        {
            field: 'submitsczh',
            name: 'diyCreate',
            fetchConfig: {//ajax配置
                apiName: '生成账号',
            },
            type: 'primary',
            disabled: "bind:_actionBtnNoSelected",
            label: '生成账号',
        },
        {
            field: 'delBtn',
            name: 'del',
            icon: 'delete',
            fetchConfig: {//ajax配置
                apiName: 'login',
            },
            type: 'danger',
            label: '删除',
        },
    ],
    fetchConfig: {
        apiName: 'pcGetZjXmSalaryUserExtensionList',//可为function 返回必须为string 
    },
    formConfig: [

        {
            // isInForm: false,
            isInSearch: false,
            table: {
                title: '姓名',
                dataIndex: 'realName',
                fixed: "left",
                // onClick:"detail",
            }
            , form: {
                type: 'string',
                placeholder: '请输入',
                required: false,
            }
        },
        {
            isInForm: false,
            table: {
                title: '性别',
                dataIndex: 'gender',
                render: "bind:renderFender",
                fixed: "left"
            }
            , form: {
                type: 'string',
                placeholder: '请输入',
                required: false,
            }
        },
        {
            isInForm: false,
            table: {
                title: '证件号码',
                dataIndex: 'idNumber',
            }
            , form: {
                type: 'string',
                placeholder: '请输入',
                required: false,
            }
        },
        {
            isInForm: false,
            table: {
                title: '手机号',
                dataIndex: 'phoneNumber',
            }
            , form: {
                type: 'string',
                placeholder: '请输入',
                required: false,
            }
        },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '岗位',
        //         dataIndex: 'appliedPosition',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '岗位等级',
        //         dataIndex: 'levelId',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '岗薪',
        //         dataIndex: 'salaryId',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        {
            isInForm: false,
            table: {
                // width:120,
                title: '所属机构',
                dataIndex: 'departmentName',
            }
            , form: {
                type: 'string',
                placeholder: '请输入',
                required: false,
            }
        },
        // {
        //     isInForm: false,
        //     table: {
        //         width: 140,
        //         title: '工资关系所在项目',
        //         dataIndex: 'wageOfProjectId',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        {
            isInForm: false,
            table: {
                // width:120,
                title: '所属科室',
                dataIndex: 'officeName',
            }
            , form: {
                type: 'string',
                placeholder: '请输入',
                required: false,
            }
        },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '入职日期',
        //         dataIndex: 'hiredate',
        //         format: "YYYY-MM-DD",
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '人员状态',
        //         dataIndex: 'userStatus',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         width: 120,
        //         title: '是否创建用户',
        //         dataIndex: '是否创建用户',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '创建者',
        //         dataIndex: 'createUser',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '创建时间',
        //         dataIndex: 'createTime',
        //         format: "YYYY-MM-DD",
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '修改者',
        //         dataIndex: 'modifyUser',
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        // {
        //     isInForm: false,
        //     table: {
        //         title: '修改时间',
        //         dataIndex: 'modifyTime',
        //         format: "YYYY-MM-DD",
        //     }
        //     ,form: {
        //         type: 'string',
        //         placeholder: '请输入',
        //         required: false,
        //     }
        // },
        {
            isInForm: false,
            table: {
                showType: "tile", //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                width: 110,
                title: "操作",
                key: "action", //操作列名称
                fixed: "right", //固定到右边
                align: "center",
                btns: [
                    {
                        name: "edit", // 内置name有【add,  del, edit, detail, Component, form】
                        label: "修改",
                        formBtns: [
                            {
                                name: "cancel", //关闭右边抽屉
                                type: "dashed", //类型  默认 primary
                                label: "取消"
                            },
                            {
                                name: "submit", //内置add del
                                type: "primary", //类型  默认 primary
                                label: "保存", //提交数据并且关闭右边抽屉  
                                fetchConfig: {
                                    apiName: "updateZjXmSalaryUserExtension",
                                    delParams: [],
                                }
                            }
                        ]
                    },
                    {
                        name: 'diy',//内置add del 
                        label: "更多",
                        btns: [
                            {
                                name: "jlsc", //关闭右边抽屉 
                                label: "简历生成",
                                onClick: "bind:jlsc"
                            },
                            {
                                name: "pyjl", //关闭右边抽屉 
                                label: "聘用记录",
                                onClick: "bind:pyjl"
                            }
                        ]
                    },
                ]
            }
        }


    ],

    tabs: [
        {
            field: "basic",
            name: "qnnForm",
            title: "基础信息",
            content: {
                fetchConfig: {
                    apiName: 'pcGetZjXmSalaryUserExtensionDetails',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                    otherParams: {},
                },
                formConfig: [
                    {
                        type: 'string',
                        label: 'extensionId',
                        field: 'extensionId',
                        hide: true
                    },
                    {
                        type: 'string',
                        label: '姓名',
                        field: 'realName',
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "gender", //表格里面的字段 
                        label: "性别",
                        required: true,
                        type: "radio",
                        optionData: [
                            //默认选项数据
                            {
                                label: "男",
                                value: "0"
                            },
                            {
                                label: "女",
                                value: "1"
                            }
                        ],
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "nation", //表格的唯一key
                        label: "民族",
                        required: true,
                        type: "select",
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'minzhu'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',// 
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,

                        formItemWrapperStyle: {
                            marginRight: '10px', //这样就可以把图片多占的一列补起来
                        }
                    },

                    {
                        type: "images",
                        desc: "上传近照",
                        label: " ",
                        colon: false,
                        field: "latestAttachmentList", //唯一的字段名 ***必传
                        fetchConfig: {
                            apiName: window.configs.domain + "upload"
                        },
                        max: 1,
                        className: "Upload-photo",
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        formItemWrapperStyle: {
                            position: "absolute",
                            right: "60px"
                        }
                    },

                    {
                        type: 'date',
                        label: '出生年月',
                        field: 'birthday',
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        label: '籍贯', //表头标题
                        field: 'nativePlace', //表格里面的字段
                        required: true,
                        type: 'cascader',
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        fetchConfig: {//配置后将会去请求下拉选项数据
                            apiName: 'getBaseCodeTree',
                            otherParams: {
                                itemId: 'xingzhengquhuadaima'
                            }
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "politicCountenance", //表格的唯一key
                        label: "政治面貌",
                        type: "select",
                        placeholder: "请输入",
                        required: true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'zhengzhimianmao'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        initialValue: "13",
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        formItemWrapperStyle: {
                            marginRight: '10px', //这样就可以把图片多占的一列补起来
                        }
                    },
                    {
                        field: "idType", //表格里面的字段 
                        label: "证件类型",
                        required: true,
                        type: "select",
                        optionData: [
                            //默认选项数据
                            {
                                label: "身份证",
                                value: "0"
                            },
                            {
                                label: "其他",
                                value: "1"
                            }
                        ],
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "idNumber", //表格里面的字段 
                        label: "证件号",
                        required: true,
                        type: "string",
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "userStatus", //表格的唯一key
                        label: "人员状态",
                        type: "select",
                        placeholder: "请输入",
                        required: true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'renYuanZhuangTai'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        formItemWrapperStyle: {
                            marginRight: '10px', //这样就可以把图片多占的一列补起来
                        }
                    },


                    {
                        label: '现居住地', //表头标题
                        field: 'presentAddress', //表格里面的字段
                        required: true,
                        type: 'cascader',
                        placeholder: '请选择',
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        fetchConfig: {//配置后将会去请求下拉选项数据
                            apiName: 'getBaseCodeTree',
                            otherParams: {
                                itemId: 'xingzhengquhuadaima'
                            }
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        // label: '详细地址', //表头标题
                        field: 'presentDetailedAddress', //表格里面的字段
                        type: 'string',
                        placeholder: '请输入详细地址',
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        formItemStyle: {
                            marginLeft: 0
                        },
                        required: true,
                    },
                    {
                        label: '邮编', //表头标题
                        field: 'postalCode', //表格里面的字段
                        type: 'postalCode',
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        required: true,
                        formItemWrapperStyle: {
                            marginRight: '10px', //这样就可以把图片多占的一列补起来
                        }
                    },


                    {
                        label: '户口所在地', //表头标题
                        field: 'residenceAddress', //表格里面的字段
                        required: true,
                        type: 'cascader',
                        placeholder: '请选择',
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        fetchConfig: {//配置后将会去请求下拉选项数据
                            apiName: 'getBaseCodeTree',
                            otherParams: {
                                itemId: 'xingzhengquhuadaima'
                            }
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: 'residenceDetailedAddress', //表格里面的字段
                        type: 'string',
                        placeholder: '请输入详细地址',
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        formItemStyle: {
                            marginLeft: 0
                        },
                        required: true,
                    },
                    {
                        field: "phoneNumber", //表格的唯一key
                        label: "联系电话",
                        type: "phone",
                        placeholder: "请输入",
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "maritalStatus", //表格的唯一key
                        label: "婚姻状况",
                        type: "select",
                        placeholder: "请输入",
                        required: true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'hunYinZhuangKuang'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },


                    {
                        label: '法律文书送达地址', //表头标题
                        field: 'legalAddress', //表格里面的字段
                        required: true,
                        type: 'cascader',
                        placeholder: '请选择',
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        fetchConfig: {//配置后将会去请求下拉选项数据
                            apiName: 'getBaseCodeTree',
                            otherParams: {
                                itemId: 'xingzhengquhuadaima'
                            }
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout2,
                    },
                    {
                        field: 'legalDetailedAddress', //表格里面的字段
                        type: 'string',
                        placeholder: '请输入详细地址',
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        formItemStyle: {
                            marginLeft: 0
                        },
                        required: true,
                    },
                    {
                        type: 'date',
                        label: '参加工作时间',
                        field: 'workFirstDate',
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout2,
                    },
                    {
                        type: 'date',
                        label: '入职时间',
                        field: 'hiredate',
                        placeholder: '请选择',
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },


                    {
                        field: "title", //表格的唯一key
                        label: "职称",
                        type: "select",
                        disabled: true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'minzhu'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "certificateName", //表格的唯一key
                        label: "职业资格",
                        type: "string",
                        disabled: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "userType", //表格的唯一key
                        label: "人员类别",
                        type: "select",
                        required: true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'pinYongLeiBie'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout2,
                    },
                    {
                        field: "position", //表格的唯一key
                        label: "岗位",
                        type: "cascader",
                        required: true,                        
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'gangWeiGuanLi'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },


                    {
                        field: "positionType", //表格的唯一key
                        label: "兼职情况",
                        type: "select",
                        disabled: true,
                        fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                                itemId: 'minzhu'
                            }
                        },
                        optionConfig: {//下拉选项配置
                            label: 'itemName', //默认 label
                            value: 'itemId',//
                            children: 'children'
                        },
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                    },
                    {
                        field: "projectTree",
                        label: "单位/项目",
                        type: "treeSelect",
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        treeSelectOption: {
                            selectType: "1",
                            maxNumber: 1,
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: "getSysDepartmentAllTree"
                            }
                        }
                    },
                    {
                        field: "departmentTree",
                        label: "所属部门",
                        type: "treeSelect",
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        treeSelectOption: {
                            selectType: "1",
                            maxNumber: 1,
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: "getSysDepartmentAllTree"
                            }
                        }
                    },
                    {
                        field: "officeTree",
                        label: "所属科室",
                        type: "treeSelect",
                        required: true,
                        span: fourItemSpan,
                        formItemLayout: fourItemLayout,
                        treeSelectOption: {
                            selectType: "1",
                            maxNumber: 1,
                            fetchConfig: {
                                //配置后将会去请求下拉选项数据
                                apiName: "getSysDepartmentAllTree"
                            }
                        }
                    },



                    {
                        field: "hobby", //表格的唯一key
                        label: "爱好及特长",
                        type: "textarea",
                        formItemLayout: oneItemLayout,
                        formItemWrapperStyle: oneFormItemWrapperStyle
                    },


                    {
                        type: 'files',
                        label: '身份证',
                        field: 'idAttachmentList',
                        required: false,
                        fetchConfig: { apiName: window.configs.domain + 'upload' },
                        max: 2,
                        formItemLayout: oneItemLayout,
                        formItemWrapperStyle: oneFormItemWrapperStyle
                    },

                    {
                        type: "qnnForm",
                        field: "healthInfo",
                        label: "健康情况",
                        formFields: [
                            // {
                            //     field: "健康情况Id",
                            //     hide: true,
                            //     type: "string"
                            // },
                            {
                                label: "体检类型",
                                field: "physicalType",
                                type: "select",
                                disabled: true,
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'gongrenzhongzhong'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',//
                                    children: 'children'
                                },
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },
                            {
                                label: "健康状况",
                                field: "healthCondition",
                                type: "select",
                                disabled: true,
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'gongrenzhongzhong'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',//
                                    children: 'children'
                                },
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },
                            {
                                label: "职业病情况",
                                field: "occupationalDisease",
                                type: "select",
                                disabled: true,
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'gongrenzhongzhong'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',//
                                    children: 'children'
                                },
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },



                            {
                                field: "height",
                                label: "身高/cm",
                                type: "number",
                                required: true,
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                                placeholder: "请输入...",
                            },
                            {
                                field: "weight",
                                label: "体重/kg",
                                type: "number",
                                required: true,
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                                placeholder: "请输入..."
                            },


                            {
                                label: "血型",
                                field: "bloodType",
                                type: "select",
                                optionData: [
                                    {
                                        label: "A",
                                        value: "0"
                                    },
                                    {
                                        label: "B",
                                        value: "1"
                                    },
                                    {
                                        label: "AB",
                                        value: "2"
                                    },
                                    {
                                        label: "O",
                                        value: "3"
                                    }
                                ],
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },
                        ]
                    },



                    {
                        type: "qnnForm",
                        field: "salaryInfo",
                        label: "薪酬情况",
                        qnnFormConfig: {

                            formConfig: [ 
                                {
                                    label: "岗位等级",
                                    field: "levelSalaryId",
                                    type:"cascader",
                                    required: true,
                                    fetchConfig: {
                                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                                        otherParams: {
                                            itemId: 'gongrenzhongzhong'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'label', //默认 label
                                        value: 'value',//
                                        children: 'showData',
                                        linkageFields:{
                                            "salaryInfo.salaryId":'value',
                                        }
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    //需要组件在 onChange 中返回当前所选的数据
                                    // onChange:(val, o)=>{
                                    //     console.log(val, o) 
                                    // }
                                },

                                {
                                    type: 'string',
                                    label: '岗薪',
                                    field: 'salaryId',
                                    disabled: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout, 
                                },
                                {
                                    label: "会计分类",
                                    field: "accountingType",
                                    type: "select",
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'kuaiJiFenLei'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    label: '社保参保地', //表头标题
                                    field: 'socialInsuranceArea', //表格里面的字段
                                    required: true,
                                    type: 'select',
                                    placeholder: '请选择',
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'xingzhengquhuadaima'
                                        }
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    label: '公积金参保地', //表头标题
                                    field: 'providentFundArea', //表格里面的字段
                                    required: true,
                                    type: 'select',
                                    placeholder: '请选择',
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'xingzhengquhuadaima'
                                        }
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "wageProjectTree",
                                    label: "工资关系所在项目",
                                    type: "treeSelect",
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout2,
                                    treeSelectOption: {
                                        selectType: "1",
                                        maxNumber: 1,
                                        fetchConfig: {
                                            //配置后将会去请求下拉选项数据
                                            apiName: "getSysDepartmentAllTree"
                                        }
                                    }
                                },
                                {
                                    label: '外在单位',
                                    field: 'externalUnit', //表格里面的字段
                                    required: true,
                                    type: 'select',
                                    placeholder: '请选择',
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'waiZaiDanWei'
                                        }
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    label: '参与课题', //表头标题
                                    field: 'involvedTopic', //表格里面的字段
                                    required: true,
                                    type: 'select',
                                    placeholder: '请选择',
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'canYuKeTi'
                                        }
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                            ],
                        },
                    },


                    {
                        type: "qnnForm",
                        field: "contractInfo",
                        label: "合同情况",
                        formFields: [
                            // {
                            //     field: "劳动合同期限Id",
                            //     hide: true,
                            //     type: "string"
                            // },
                            {
                                type: 'string',
                                label: '合同编号',
                                field: 'contractNo',
                                required: false,
                                disabled: true,
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },
                            {
                                type: 'date',
                                label: '签订时间',
                                field: 'signingDate',
                                required: false,
                                disabled: true,
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },
                            {
                                label: "合同类型",
                                field: "contractType",
                                type: "select",
                                disabled: true,
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'gongrenzhongzhong'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',//
                                    children: 'children'
                                },
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },

                            {
                                type: 'date',
                                label: '合同起始时间',
                                field: 'contractStartDate',
                                placeholder: '请选择',
                                disabled: true,
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },
                            {
                                type: 'date',
                                label: '合同截止时间',
                                field: 'contractEndDate',
                                placeholder: '请选择',
                                disabled: true,
                                span: fourItemSpan,
                                formItemLayout: fourItemLayout,
                            },
                        ],
                    },

                ]
            }
        },
        {
            field: "学历情况",
            name: "qnnTable",
            title: "学历情况",
            // disabled: "bind:tabItemDisabled",
            content: {
                qnnTableConfig:{

                },
                antd: {
                    rowKey: "educationId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryEducationBackground",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            title: '入学时间',
                            dataIndex: 'enrollmentDate',
                            format: 'YYYY-MM-DD',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "date"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '毕/肆业时间',
                            dataIndex: 'graduateDate',
                            format: 'YYYY-MM-DD',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "date"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '毕/肆业院校',
                            dataIndex: 'graduateSchool',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '学历',
                            dataIndex: 'education',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '学位',
                            dataIndex: 'degree',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '专业',
                            dataIndex: 'major',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '学位授予时间',
                            dataIndex: 'degreeAwardDate',
                            format: 'YYYY-MM-DD',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "date"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '是否第一学历',
                            dataIndex: 'isFirstEducation',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "radio",
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ]
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '是否最高学历',
                            dataIndex: 'isHighestEducation',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryEducationBackground"
                            },
                            fieldConfig: {
                                type: "radio",
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ]
                            }
                        }
                    },
                ]
            }
        },
        {
            field: "工作履历",
            name: "qnnTable",
            title: "工作履历",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "experienceId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryWorkExperienceList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryWorkExperience",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryWorkExperience',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [

                    {
                        isInForm: false,
                        table: {
                            title: '起始日期',
                            dataIndex: 'startDate',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: 'date',
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '截止日期',
                            dataIndex: 'endDate',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: 'date',
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '工作单位',
                            dataIndex: 'workUnit',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '所在部门',
                            dataIndex: 'department',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '岗位',
                            dataIndex: 'position',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '主要工作内容',
                            dataIndex: 'workContent',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '证明人',
                            dataIndex: 'certifier',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '联系电话',
                            dataIndex: 'phoneNumber',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: "phone"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '主、兼职',
                            dataIndex: 'positionType',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryWorkExperience"
                            },
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            }
                        }
                    },

                ]
            }
        },
        {
            field: "专业技术",
            title: "专业技术",
            name: "qnnTable",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "technologyId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryProfessionalTechnologyList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryProfessionalTechnology",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryProfessionalTechnology',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [

                    {
                        isInForm: false,
                        table: {
                            title: '职称名称',
                            dataIndex: 'title',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '职称专业',
                            dataIndex: 'specialty',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '职称级别',
                            dataIndex: 'level',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '职称取得途径',
                            dataIndex: 'access',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '取得资格文号',
                            dataIndex: 'qualificationNo',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },

                    {
                        isInForm: false,
                        table: {
                            title: '取得资格时间',
                            dataIndex: 'acquisitionDate',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: 'date',
                                placeholder: '请选择',
                                required: false,

                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '证书编号',
                            dataIndex: 'certificateNo',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '发证单位',
                            dataIndex: 'issueUnit',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '附件',
                            dataIndex: 'technologyAttachmentList',
                            tdEdit: true,
                            tdEditFetchConfig: {
                                apiName: "updateZjXmSalaryProfessionalTechnology"
                            },
                            fieldConfig: {
                                type: "files",
                                max: 1,
                                fetchConfig: {
                                    //配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + "upload"
                                },
                            }
                        }
                    },





                ]
            }
        },

        {
            field: "合同管理",
            title: "合同管理",
            name: "qnnTable",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "contractId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryContractManagementList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryContractManagement",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryContractManagement',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            title: '合同编号',
                            dataIndex: 'contractNo',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width:150,
                            title: '合同签订时间',
                            dataIndex: 'signingDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '合同类型',
                            dataIndex: 'contractType',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width:150,
                            title: '劳动合同期限·始',
                            dataIndex: 'startDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width:150,
                            title: '劳动合同期限·止',
                            dataIndex: 'endDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: { 
                            width:150,
                            title: '合同期限（月）',
                            dataIndex: 'contractPeriod',
                            tdEdit: true,
                            fieldConfig: {
                                type: "number",
                                disabled:true
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '试用期',
                            dataIndex: 'probation',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                }
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '签订类型',
                            dataIndex: 'signingType',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width:150,
                            title: '原单位离职证明',
                            dataIndex: 'quitAttachmentList',
                            tdEdit: true,
                            fieldConfig: {
                                type: "files",
                                max: 1,
                                fetchConfig: {
                                    //配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + "upload"
                                },
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },

                    {
                        isInForm: false,
                        table: {
                            title: '合同附件',
                            dataIndex: 'contractAttachmentList',
                            tdEdit: true,
                            fieldConfig: {
                                type: "files",
                                max: 1,
                                fetchConfig: {
                                    //配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + "upload"
                                },
                            },
                            ...updateFetchConfigs.heTongGuanLi
                        }
                    },

                ]
            }
        },
        {
            field: "培训情况",
            title: "培训情况",
            name: "qnnTable",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "trainingId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryTrainingSituationList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryTrainingSituation",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryTrainingSituation',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [

                    {
                        isInForm: false,
                        table: {
                            title: '起始日期',
                            dataIndex: 'startDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.training
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '截止日期',
                            dataIndex: 'endDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.training
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '培训名称',
                            dataIndex: 'trainingName',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.training
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '培训类型',
                            dataIndex: 'trainingType',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.training
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '培训学习方式',
                            dataIndex: 'trainingMode',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.training
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '培训学时',
                            dataIndex: 'trainingHours',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.training
                        }
                    },

                    {
                        isInForm: false,
                        table: {
                            title: '培训附件',
                            dataIndex: 'trainingAttachmentList',
                            tdEdit: true,
                            fieldConfig: {
                                type: "files",
                                max: 1,
                                fetchConfig: {
                                    //配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + "upload"
                                },
                            },
                            ...updateFetchConfigs.training
                        }
                    },

                ]
            }
        },
        {
            field: "家庭状况",
            title: "家庭状况",
            name: "qnnTable",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "familyId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryFamilyBackgroundList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryFamilyBackground",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryFamilyBackground',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            title: '与本人关系',
                            dataIndex: 'relationship',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.family
                        }
                    },

                    {
                        isInForm: false,
                        table: {
                            title: '姓名',
                            dataIndex: 'name',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.family
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '工作单位及职务',
                            dataIndex: 'unitPosition',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.family
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '住址',
                            dataIndex: 'address',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.family
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '联系电话',
                            dataIndex: 'phoneNumber',
                            tdEdit: true,
                            fieldConfig: {
                                type: "phone"
                            },
                            ...updateFetchConfigs.family
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '是否紧急联系人',
                            dataIndex: 'isUrgentLinkMan',
                            tdEdit: true,
                            fieldConfig: {
                                type: "radio",
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ]
                            },
                            ...updateFetchConfigs.family
                        }
                    },


                ]
            }
        },
        {
            field: "健康情况",
            title: "健康情况",
            name: "qnnTable",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "healthId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryHealthConditionList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryHealthCondition",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryHealthCondition',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            title: '体检类型',
                            dataIndex: 'physicalType',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.health
                        }
                    },

                    {
                        isInForm: false,
                        table: {
                            title: '体检机构',
                            dataIndex: 'physicalInstitution',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.health
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '健康情况',
                            dataIndex: 'healthCondition',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.health
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '职业病情况',
                            dataIndex: 'occupationalDisease',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.health
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '附件',
                            dataIndex: 'healthAttachmentList',
                            tdEdit: true,
                            fieldConfig: {
                                type: "files",
                                max: 1,
                                fetchConfig: {
                                    //配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + "upload"
                                },
                            },
                            ...updateFetchConfigs.health
                        }
                    },

                ]
            }
        },
        {
            field: "证书管理",
            title: "证书管理",
            name: "qnnTable",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "certificateId",
                    size: "small"
                },
                fetchConfig: {
                    apiName: 'getZjXmSalaryCertificateManagementList',//可为function 返回必须为string
                    params: {
                        extensionId: "extensionId"
                    },
                },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "addZjXmSalaryCertificateManagement",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'batchDeleteUpdateZjXmSalaryCertificateManagement',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            width: 140,
                            fixed: "left",
                            title: '证书类别',
                            dataIndex: 'certificateType',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },

                    {
                        isInForm: false,
                        table: {
                            width: 120,
                            fixed: "left",
                            title: '证书名称',
                            dataIndex: 'certificateName',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 120,
                            title: '证书专业',
                            dataIndex: 'certificateMajor',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 120,
                            title: '证书编号',
                            dataIndex: 'certificateNo',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '签发日期',
                            width: 140,
                            dataIndex: 'issueDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 150,
                            title: '证书履约所在项目',
                            dataIndex: 'projectId',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            }
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 130,
                            title: '一次性奖励标准',
                            dataIndex: 'rewardStandard',
                            tdEdit: true,
                            fieldConfig: {
                                type: "string"
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '分摊年度',
                            dataIndex: 'apportionYear',
                            tdEdit: true,

                            width: 300,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                                multiple: true,
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '已发放年度',
                            dataIndex: 'paidYear',
                            tdEdit: true,
                            width: 300,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                                multiple: true
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 120,
                            title: '月度补贴标准',
                            dataIndex: 'subsidyStandard',
                            tdEdit: true,
                            fieldConfig: {
                                type: "money"
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 140,
                            title: '发放开始时间',
                            dataIndex: 'startDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                                required: false,
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 140,
                            title: '发放截止时间',
                            dataIndex: 'endDate',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },


                    {
                        isInForm: false,
                        table: {
                            width: 130,
                            title: '附件',
                            dataIndex: 'certificateAttachmentList',
                            tdEdit: true,
                            fieldConfig: {
                                type: "files",
                                max: 1,
                                fetchConfig: {
                                    //配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + "upload"
                                },
                            },
                            ...updateFetchConfigs.certificate
                        }
                    },

                ]
            }
        },
        {
            field: "政治面貌",
            title: "政治面貌",
            name: "qnnTable",
            disabled: "bind:tabItemDisabled",
            content: {
                antd: {
                    rowKey: "countenanceId",
                    size: "small"
                },
                // fetchConfig: {
                //     apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                //     params: {
                //         extensionId: "extensionId"
                //     },
                // },
                actionBtns: [
                    {
                        name: "addRow", //内置add del
                        icon: "plus", //icon
                        type: "primary", //类型  默认 primary  [primary dashed danger]
                        label: "新增",
                        addRowFetchConfig: {
                            apiName: "login",
                            params: {
                                extensionId: "extensionId"
                            },
                        },
                    },
                    {
                        field: 'delBtn',
                        name: 'del',
                        icon: 'delete',
                        fetchConfig: {//ajax配置
                            apiName: 'del',
                        },
                        type: 'danger',
                        label: '删除',
                    },
                ],
                formConfig: [
                    {
                        isInForm: false,
                        table: {
                            fixed: "left",
                            title: '政治面貌',
                            dataIndex: '政治面貌',
                            tdEdit: true,
                            fieldConfig: {
                                type: "select",
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'minzhu'
                                    }
                                },
                                optionConfig: {//下拉选项配置
                                    label: 'itemName', //默认 label
                                    value: 'itemId',// 
                                },
                            },
                            ...updateFetchConfigs.countenance
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '参加时间',
                            dataIndex: '参加时间',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                                required: false,
                            },
                            ...updateFetchConfigs.countenance
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '所在党支部',
                            dataIndex: '所在党支部',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'string',
                            },
                            ...updateFetchConfigs.countenance
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '参加时所在单位',
                            dataIndex: '参加时所在单位',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'string',
                            },
                            ...updateFetchConfigs.countenance
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '介绍人',
                            dataIndex: '介绍人',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'string',
                            },
                            ...updateFetchConfigs.countenance
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '转正时间',
                            dataIndex: '转正时间',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'date',
                            },
                            ...updateFetchConfigs.countenance
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            title: '状态',
                            dataIndex: '状态',
                            tdEdit: true,
                            fieldConfig: {
                                type: 'string',
                            },
                            ...updateFetchConfigs.countenance
                        }
                    },
                ]
            }
        }
    ],

}

