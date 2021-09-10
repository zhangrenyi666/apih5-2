// 系统项目管理
window._ProjectManage = {
    fetchConfig: {
        apiName: 'getSysProjectList',
        otherParams: function (obj) {
            return {
                departmentId: obj.props.getOrgId()
            }
        },
    },
    antd: {
        rowKey: "departmentId",
        size: "small"
    },
    isShowRowSelect: true,
    drawerConfig: {
        width: '1200px'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    },
    formConfig: [
        {
            isInTable: false,
            form: {
                type: "component",
                field: "component1",
                Component: 'component1'
            }
        },
        {
            isInTable: false,
            form: {
                field: "departmentId",
                type: "string",
                hide: true
            }
        },
        {
            table: {
                title: '所属公司',
                dataIndex: 'companyName',
                key: 'companyName'
            },
            isInForm:false
        },
        {
            table: {
                title: '项目名称',
                dataIndex: 'departmentName',
                key: 'departmentName',
                onClick: 'detail',
                filter:true
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                // spanForm: 8,
                spanForm: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
                required: true
            },
        },
        {
            isInTable: true,
            table: {
                title: '项目全称',
                dataIndex: 'departmentFullName',
                key: 'departmentFullName',
                filter:true
            },
            form: {
                label: '项目全称',
                field: 'departmentFullName',
                type: 'string',
                placeholder: '请输入',
                // spanForm: 8,
                spanForm: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
                required: true
            },
        },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目首字母',
        //         field: 'projectPinyin',
        //         type: 'string',
        //         placeholder: '请输入',
        //         spanForm: 8,
        //         diyRules: (obj) => {
        //             var required = obj.required;
        //             var message = obj.message;
        //             return [
        //                 {
        //                     required: required,
        //                     message: message
        //                 },
        //                 {
        //                     pattern: new RegExp(/^[A-Z]+$/),
        //                     message: "请输入英文大写"
        //                 }
        //             ];
        //         },
        //         required: true,
        //         editDisabled:true
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '标段号',
        //         field: 'lotNo',
        //         type: 'string',
        //         placeholder: '请输入',
        //         diyRules: (obj) => {
        //             var required = obj.required;
        //             var message = obj.message;
        //             return [
        //                 {
        //                     required: required,
        //                     message: message
        //                 },
        //                 {
        //                     pattern: new RegExp(/^\d{2}$/),
        //                     message: "请输入两位数字"
        //                 }
        //             ];
        //         },
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     table: {
        //         title: '承建单位简称',
        //         dataIndex: 'contractor',
        //         key: 'contractor',
        //         type: 'select'
        //     },
        //     form: {
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         fetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //             otherParams: {
        //                 itemId: 'chengJianDanWeiJianCheng'
        //             }
        //         },
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     }
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目施工单位',
        //         field: 'locationInDeprFormula',
        //         type: 'string',
        //         placeholder: '请输入',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     table: {
        //         title: '所属区域',
        //         dataIndex: 'subArea',
        //         key: 'subArea',
        //         type: 'select'
        //     },
        //     form: {
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         fetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //             otherParams: {
        //                 itemId: 'suoShuQuYu'
        //             }
        //         },
        //         showSearch: true,
        //         required: false,
        //         onChange: (val, obj) => {
        //             obj.form.setFieldsValue({
        //                 projectLocation: null,
        //                 provinceAbbreviation: null,
        //                 cityName: null
        //             })
        //         },
        //         placeholder: '请选择',
        //         spanForm: 8
        //     }
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目所在地',
        //         field: 'projectLocation',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId',
        //             linkageFields: {
        //                 provinceAbbreviation: 'itemAsName',
        //             }
        //         },
        //         parent:'subArea',
        //         fetchConfig: (obj) => {
        //             if (obj.form.getFieldValue('subArea') === '10001') {
        //                 return {
        //                     apiName: 'getBaseCodeSelect',
        //                     otherParams: {
        //                         itemId: 'xingzhengquhuadaima'
        //                     }
        //                 }
        //             } else if (obj.form.getFieldValue('subArea') === '10002') {
        //                 return {
        //                     apiName: 'getBaseCodeSelect',
        //                     otherParams: {
        //                         itemId: 'haiWaiXiangMuSuoZaiShengFenJianCheng'
        //                     }
        //                 }
        //             } else {
        //                 return {};
        //             }
        //         },
        //         onChange: (val, obj) => {
        //             obj.form.setFieldsValue({
        //                 cityName: null
        //             })
        //         },
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目所在省份简称',
        //         field: 'provinceAbbreviation',
        //         type: 'string',
        //         addDisabled: true,
        //         editDisabled: true,
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '市级',
        //         field: 'cityName',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         parent:'projectLocation',
        //         fetchConfig: (obj) => {
        //             if (obj.form.getFieldValue('projectLocation')) {
        //                 return {
        //                     apiName: 'getBaseCodeSelect',
        //                     otherParams: {
        //                         itemId: obj.form.getFieldValue('projectLocation')
        //                     }
        //                 }
        //             }
        //         },
        //         condition: [
        //             {
        //                 regex: {
        //                     subArea: '10002'
        //                 },
        //                 action: 'disabled'
        //             }
        //         ],
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     table: {
        //         title: '项目性质',
        //         dataIndex: 'projectProperty',
        //         key: 'projectProperty',
        //         type: 'select'
        //     },
        //     form: {
        //         label: '项目性质',
        //         field: 'projectProperty',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         fetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //             otherParams: {
        //                 itemId: 'xiangMuXingZhi'
        //             }
        //         },
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '工程类别',
        //         field: 'projType',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         fetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //             otherParams: {
        //                 itemId: 'gongChengLeiBie'
        //             }
        //         },
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目特征',
        //         field: 'proDescribe',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         fetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //             otherParams: {
        //                 itemId: 'xiangMuTeZheng'
        //             }
        //         },
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     table: {
        //         title: '所属事业部',
        //         dataIndex: 'bizDep',
        //         key: 'bizDep',
        //         type: 'select'
        //     },
        //     form: {
        //         label: '项目所属事业部',
        //         field: 'bizDep',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         fetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //             otherParams: {
        //                 itemId: 'suoShuShiYeBu'
        //             }
        //         },
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '是否局直属',
        //         field: 'juFlag',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         optionData: [
        //             {
        //                 itemName: '否',
        //                 itemId: '0'
        //             },
        //             {
        //                 itemName: '是',
        //                 itemId: '1'
        //             }
        //         ],
        //         showSearch: true,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目承包模式',
        //         field: 'contractingType',
        //         type: 'select',
        //         optionConfig: {
        //             label: 'itemName', //默认 label
        //             value: 'itemId'
        //         },
        //         fetchConfig: {
        //             apiName: 'getBaseCodeSelect',
        //             otherParams: {
        //                 itemId: 'xiangMuCanJianXingZhi'
        //             }
        //         },
        //         showSearch: true,
        //         required: false,
        //         placeholder: '请选择',
        //         spanForm: 8
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目经理姓名',
        //         field: 'projectChiefName',
        //         type: 'string',
        //         placeholder: '请输入',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '项目经理电话',
        //         field: 'projectChiefTel',
        //         type: 'phone',
        //         placeholder: '请输入',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        {
            isInTable: false,
            form: {
                type: "component",
                field: "component2",
                Component: 'component2'
            }
        },
        {
            isInTable: false,
            form: {
                label: '所属单位',
                field: 'companyJSONArray',
                type: 'treeSelect',
                treeSelectOption: {
                    maxNumber: 1,
                    selectType: "1",
                    //配置参照oa拉人组件配置
                    fetchConfig: {
                        apiName: 'getSysDepartmentCurrentTree',
                        paramsKey: 'departmentParentId'
                    },
                },
                required: true,
                editDisabled: true,
                spanForm: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
            }
        },
        {
            table: {
                title: '承建项目类型',
                dataIndex: 'contractorType',
                key: 'contractorType',
                type: 'select',
            },
            form: {
                type: 'select',
                optionConfig: {
                    label: 'itemName', //默认 label
                    value: 'itemId'
                },
                fetchConfig: {
                    apiName: 'getBaseCodeSelect',
                    otherParams: {
                        itemId: 'projectType'
                    }
                },
                showSearch: true,
                required: true,
                onChange: (val, obj) => {
                    obj.form.setFieldsValue({
                        confUpJSONArray: null
                    })
                },
                editDisabled: true,
                placeholder: '请选择',
                spanForm: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
            },
        },
        {
            isInTable: false,
            form: {
                label: '是否显示',
                field: 'confDownShowFlag',
                type: 'radio',
                optionData: [
                    {
                        label: '否',
                        value: '0'
                    },
                    {
                        label: '是',
                        value: '1'
                    }
                ],
                condition: [
                    {
                        regex: {
                            contractorType: ['!', /(4|5)/ig]
                        },
                        action: 'hide'
                    }
                ],
                spanForm: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
                required: true,
                editDisabled: true
            },
        },
        {
            isInTable: false,
            form: {
                label: '挂接总部',
                field: 'confUpJSONArray',
                type: 'treeSelect',
                treeSelectOption: {
                    maxNumber: 1,
                    selectType: "1",
                    //配置参照oa拉人组件配置
                    fetchConfig: {
                        apiName: 'getSysDepartmentCurrentTreeByZb',
                        otherParams: function (obj) {
                            var vals = obj.getTableRef().getDeawerValuesSync() || {};
                            return {
                                contractorType: vals.contractorType
                            }
                        },
                        paramsKey: 'departmentParentId'
                    },
                },
                condition: [
                    {
                        regex: {
                            contractorType: ['!', /(4|5)/ig]
                        },
                        action: 'hide'
                    }
                ],
                required: true,
                editDisabled: true,
                spanForm: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
            },
        },
        {
            isInTable: false,
            form: {
                label: '托管机构',
                field: 'delegateJSONArray',
                type: 'treeSelect',
                treeSelectOption: {
                    maxNumber: 1,
                    selectType: "1",
                    //配置参照oa拉人组件配置
                    fetchConfig: { apiName: 'getSysDepartmentCurrentTree', otherParams: {}, paramsKey: 'departmentParentId' },
                },
                condition: [
                    {
                        regex: {
                            contractorType: ['!', /(3)/ig]
                        },
                        action: 'hide'
                    }
                ],
                required: true,
                editDisabled: false,
                spanForm: 12,
                formItemLayout: {
                    labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                    }
                },
            }
        },
        // {
        //     isInTable: false,
        //     form: {
        //         type: "component",
        //         field: "component3",
        //         Component: 'component3'
        //     }
        // },
        // {
        //     table: {
        //         title: '项目开工日期',
        //         dataIndex: 'actualStartDate',
        //         key: 'actualStartDate',
        //         format:'YYYY-MM-DD'
        //     },
        //     form: {
        //         type: 'date',
        //         placeholder: '请选择',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     table: {
        //         title: '主体完工日期',
        //         dataIndex: 'mainEndDate',
        //         key: 'mainEndDate',
        //         format:'YYYY-MM-DD'
        //     },
        //     form: {
        //         type: 'date',
        //         placeholder: '请选择',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     table: {
        //         title: '项目交工日期',
        //         dataIndex: 'deliveryDate',
        //         key: 'deliveryDate',
        //         format:'YYYY-MM-DD'
        //     },
        //     form: {
        //         type: 'date',
        //         placeholder: '请选择',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     table: {
        //         title: '项目竣工日期',
        //         dataIndex: 'actualEndDate',
        //         key: 'actualEndDate',
        //         format:'YYYY-MM-DD'
        //     },
        //     form: {
        //         type: 'date',
        //         placeholder: '请选择',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     table: {
        //         title: '项目归档日期',
        //         dataIndex: 'docDate',
        //         key: 'docDate',
        //         format:'YYYY-MM-DD'
        //     },
        //     form: {
        //         type: 'date',
        //         placeholder: '请选择',
        //         spanForm: 8,
        //         required: false
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '主要工程项目及工程数量',
        //         field: 'detail',
        //         type: 'textarea',
        //         placeholder: '请输入',
        //         required: false,
        //         formItemLayout: {
        //             labelCol: {
        //                 xs: { span: 4 },
        //                 sm: { span: 4 }
        //             },
        //             wrapperCol: {
        //                 xs: { span: 20 },
        //                 sm: { span: 20 }
        //             }
        //         }
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '备注信息',
        //         field: 'remarks',
        //         type: 'textarea',
        //         placeholder: '请输入',
        //         formItemLayout: {
        //             labelCol: {
        //                 xs: { span: 4 },
        //                 sm: { span: 4 }
        //             },
        //             wrapperCol: {
        //                 xs: { span: 20 },
        //                 sm: { span: 20 }
        //             }
        //         }
        //     },
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         label: '附件',
        //         field: 'sysFileList',
        //         type: 'files',
        //         fetchConfig: {
        //             apiName: 'upload'
        //         },
        //         formItemLayout: {
        //             labelCol: {
        //                 xs: { span: 4 },
        //                 sm: { span: 4 }
        //             },
        //             wrapperCol: {
        //                 xs: { span: 20 },
        //                 sm: { span: 20 }
        //             }
        //         }
        //     }
        // },
        // {
        //     table: {
        //         title: '是否锁定',
        //         dataIndex: '20',
        //         key: '20',
        //         render:(data) => {
        //             if(data === '0'){
        //                 return '否';
        //             }else if(data === '1'){
        //                 return '是';
        //             }else{
        //                 return '--';
        //             }
        //         }
        //     },
        //     isInForm:false
        // },
        // {
        //     isInTable: false,
        //     form: {
        //         required: false,
        //         type: 'treeSelect',
        //         label: '挂载总部',
        //         field: 'confUpJSONArray',
        //         treeSelectOption: {
        //             selectType: "1",
        //             //配置参照oa拉人组件配置
        //             fetchConfig: { apiName: 'getSysDepartmentCurrentTree', otherParams: {}, paramsKey: 'departmentParentId' },
        //         },
        //     }
        // },
        // {
        //     isInTable: false
        //     ,form: {
        //         required: false,
        //         type: 'treeSelect',
        //         label: '挂载下级',
        //         field: 'confDownJSONArray',
        //         treeSelectOption: {
        //             selectType: "1",
        //             //配置参照oa拉人组件配置
        //             fetchConfig: { apiName: 'getSysDepartmentCurrentTree',otherParams: {}, paramsKey:'departmentParentId'},
        //         },
        //     }
        // },

        // {
        //     isInForm: false,
        //     table: {
        //         dataIndex: 'action', //表格里面的字段
        //         key: 'action',//表格的唯一key  
        //         showType: 'tile',
        //         title: "操作",
        //         width: 80,
        //         btns: [
        //             {
        //                 name: 'edit',
        //                 label: "修改",
        //                 formBtns: [
        //                     {
        //                         name: 'cancel', //关闭右边抽屉
        //                         type: 'dashed',//类型  默认 primary
        //                         label: '取消',
        //                     },
        //                     {
        //                         name: 'submit',//内置add del
        //                         type: 'primary',//类型  默认 primary
        //                         label: '提交',//提交数据并且关闭右边抽屉 
        //                         fetchConfig: {//ajax配置
        //                             apiName: 'updateSysProjectByRelation',
        //                         }
        //                     }
        //                 ]
        //             }
        //         ]
        //     }
        // },
    ]
}