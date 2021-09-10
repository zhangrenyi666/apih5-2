window.QnnTableConfigByTechnical = {
    fetchConfig: {
        apiName: 'getZxQrcodeTechnicalBasisList'
    },
    antd: {
        rowKey: function (row) {
            //---row.主键id
            return row.educationId
        }
    },
    drawerConfig: {
        width: '800px'
    },
    infoAlert: function (selectedRows) {
        return '已选择 ' + selectedRows.length + '项';
    },
    paginationConfig: {
        position: 'bottom'
    },
    actionBtns: [
        {
            name: 'add',
            icon: 'plus',
            type: 'primary',
            label: '新增',
            formBtns: [
                {
                    name: 'cancel',
                    type: 'dashed',
                    label: '取消',
                },
                {
                    name: 'submit',
                    type: 'primary',
                    label: '提交',
                    fetchConfig: {
                        //---新增接口
                        apiName: 'addZxQrcodeTechnicalBasis',
                    }
                }
            ]
        },
        {
            name: 'del',
            icon: 'delete',
            type: 'danger',
            label: '删除',
            fetchConfig: {
                //---删除接口
                apiName: 'batchDeleteUpdateZxQrcodeTechnicalBasis',
            },
        }
    ],

    formConfig: [
        {
            isInTable: false,
            form: {
                label: '主键id',
                field: 'educationId',
                type: 'string',
                hide: true,
            }
        },
        {
            isInSearch: true,
            table: {
                title: '所属分部',
                dataIndex: 'branchtName',
                key: 'branchtName',
            },
            form: {
                label: "所属分部",
                field: 'branchtId',
                type: 'select',
                placeholder: '请选择',
                optionConfig: {
                    //下拉选项k值
                    label: 'departmentName',
                    value: 'departmentId',
                },
                fetchConfig: {
                    //下拉接口
                    apiName: 'getSysDepartmentListByCondition',
                    //下拉接口需要的参数
                    otherParams: { departmentPath: '', departmentFlag: 2 }
                }
            },
        },
        {
            isInSearch: true,
            table: {
                title: '交底名称',
                dataIndex: 'educationName',
                key: 'educationName',
            },
            form: {
                label: "交底名称",
                field: "educationName",
                type: 'string',
                placeholder: '请输入'
            }
        },
        {
            isInSearch: true,
            table: {
                title: '培训时间',
                dataIndex: 'educationDateTime',
                key: 'educationDateTime',
                format: 'YYYY-MM-DD HH:mm:ss',
            },
            form: {
                label: '培训时间',
                field: "educationDateTime",
                type: 'datetime',
                placeholder: '请选择'
            }
        },
        {
            isInSearch: true,
            table: {
                title: '培训讲师',
                dataIndex: 'createUserName',
                key: 'createUserName',
            },
            form: {
                label: '培训讲师',
                field: "createUserName",
                type: 'string',
                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '培训地点',
                dataIndex: 'educationPlace',
                key: 'educationPlace',
            },
            form: {
                label: '培训地点',
                field: 'educationPlace',
                type: 'string',
                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '培训学时',
                dataIndex: 'educationPeriod',
                key: 'educationPeriod',
            },
            form: {
                label: '培训学时',
                field: "educationPeriod",
                type: 'string',
                placeholder: '请输入'
            }
        },
        {
            isInTable: false,
            form: {
                label: 'other1',
                field: 'other1',
                type: 'string', 
            }
        },
        {
            isInTable: false,
            form: {
                label: 'other2',
                field: 'other2',
                type: 'string', 
            }
        },
        {
            isInTable: false,
            form: {
                label: 'other3',
                field: 'other3',
                type: 'string', 
            }
        },
        {
            isInSearch: true, 
            isInTable: false,
            form: {
                label: 'other4',
                field: 'other4',
                type: 'string', 
            }
        },
        {
            isInSearch: true,
            isInTable: false,
            form: {
                label: 'other5',
                field: 'other5',
                type: 'string', 
            }
        },
        {
            isInSearch: true,
            isInTable: false,
            form: {
                label: 'other6',
                field: 'other6',
                type: 'string', 
            }
        },
        {  
            isInTable: false,
            form: {
                label: '附件',
                field: 'zxQrcodeAttachmentList',
                type: 'files',
                initialValue: [],
                fetchConfig: {
                    apiName: window.configs.domain + 'upload',
                },
            }
        },
        {
            isInForm: false,
            table: {
                dataIndex: 'action', //表格里面的字段
                key: 'action',//表格的唯一key  
                btns: [
                    {
                        name: 'edit',
                        render: function () {
                            return '<a>修改</a>';
                        },
                        formBtns: [
                            {
                                name: 'cancel',
                                type: 'dashed',
                                label: '取消',
                            },
                            {
                                name: 'submit',
                                type: 'primary',
                                label: '提交',
                                fetchConfig: {
                                    //修改接口
                                    apiName: 'updateZxQrcodeTechnicalBasis',
                                }
                            }
                        ]
                    },
                    {
                        name: 'detail',
                        render: function () {
                            return '<a>详情</a>';
                        }
                    }
                ]
            }
        }
    ]

}