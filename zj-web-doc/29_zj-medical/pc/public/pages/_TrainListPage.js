window.TrainListPage={//安全培训
    fetchConfig: {//表格的ajax配置
        apiName: 'getBaseFlowSponsorChooserList',
    },
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.sponsorChooserId
        }
    },
    drawerConfig: {
        width: '600px'
    },
    infoAlert: function (selectedRows) {
        return '已选择 ' + selectedRows.length + '项';
    },
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },

    isShowRowSelect: true,//是否显示选择框  默认true
    actionBtns: [
        {
            name: 'add',//内置add del
            icon: 'plus',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '新增',
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
                        apiName: 'addBaseFlowSponsorChooserByList',
                    }
                    // onClick:function(obj){
                    //     var fetch = obj.btnCallbackFn.fetch;
                    //     var msg = obj.btnCallbackFn.msg;
                    //     fetch('addBaseFlowSponsorChooserByList',obj._formData, function (data) {
                    //         if (data.success) {
                    //             obj.closeDrawer(false)
                    //             msg.success(data.message)
                    //         }else{
                    //             msg.error(data.message)
                    //         }
                    //     })
                    // }
                }
            ]
        },
        {
            name: 'del',//内置add del
            icon: 'delete',//icon
            type: 'danger',//类型  默认 primary  [primary dashed danger]
            label: '删除',
            fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateBaseFlowSponsorChooser',
            },
        }
    ],
    //每个表单项的布局 -- 搜索区域
    formItemLayoutSearch: {
        //默认数据
        labelCol: {
            xs: { span: 24 },
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 18 }
        }
    },
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'sponsorChooserId',
                type: 'string',
                placeholder: '请输入',
                addShow: false,
                editShow: false,
            }
        }, {
            isInSearch: false,
            table: {
                title: '权限名称', //表头标题
                dataIndex: 'flowNodeCustom', //表格里面的字段
                key: 'flowNodeCustom',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
            }
        },{   
            isInSearch:true,
            isInForm:false,
            table: {
                title: '流程名称', //表头标题
                dataIndex: 'apih5FlowName', //表格里面的字段
                key: 'apih5FlowName',//表格的唯一key  
            },
            form: {
                field: 'apih5FlowName',
                type: 'string',
                placeholder: '请输入',
            },
        },{   
            isInForm:false,
            table: {
                title: '开始节点', //表头标题
                dataIndex: 'startNodeId', //表格里面的字段
                key: 'startNodeId',//表格的唯一key  
            }
        }, {   
            isInForm:false,
            table: {
                title: '结束节点', //表头标题
                dataIndex: 'endNodeId', //表格里面的字段
                key: 'endNodeId',//表格的唯一key  
            }
        },
        {
            isInTable: false,//必须为false  默认false
            form: {
                type: 'linkage',
                fetchConfig: {//只有模式为0才写到这
                    apiName: 'getFlowNameSelectList',
                },
                children: {//所有配置见qnn-form 
                    form: {
                        required: true,
                        label: '流程名称',
                        field: 'apih5FlowId',
                        type: 'select',
                        placeholder: '请选择',
                        optionConfig: { // 暂时只有模式为0有意义 所有子集默认optionConfig
                            value: 'apih5FlowId',
                            label: 'apih5FlowName',
                            children: 'baseFlowCodeList'
                        },
                    },
                    children: {
                        form: {
                            required: true,
                            label: '开始节点',
                            placeholder: '请选择',
                            field: 'startNodeId',
                            type: 'select',
                            optionConfig: {
                                value: 'apih5NodeId',
                                label: 'apih5NodeName',
                                children: 'baseFlowCodeList1'
                            }
                        },
                        children: {
                            form: {
                                required: true,
                                label: '结束节点',
                                placeholder: '请选择',
                                field: 'endNodeId',
                                type: 'select',
                                optionConfig: {
                                    value: 'apih5NodeId',
                                    label: 'apih5NodeName',
                                }
                            }
                        }
                    }
                }
            }
        }, {//普通选择框
            isInSearch: false,
            isInTable: false,
            form: {
                label: '发起对象',
                field: 'launcherList',
                type: 'treeSelect',
                treeSelectOption: {
                    selectType: "2",
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentAllTree'
                    }
                }
            }
        }, {//普通选择框
            isInSearch: false,
            isInTable: false,
            form: {
                label: '被选对象',
                field: 'candidateList',
                type: 'treeSelect',
                treeSelectOption: {
                    selectType: "2",
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentAllTree'
                    }
                }
            }
        }, {
            isInSearch: false,
            isInForm: false,
            table: {
                title: '创建者', //表头标题
                dataIndex: 'createUserName', //表格里面的字段
                key: 'createUserName',//表格的唯一key  
            },
            form: {
                type: 'string',
                placeholder: '请输入',
            }
        }, {
            isInSearch: false,
            isInForm: false,
            table: {
                title: '创建时间', //表头标题
                dataIndex: 'createTime', //表格里面的字段
                key: 'createTime',//表格的唯一key  
                format: 'YYYY-MM-DD HH:mm:ss',
            },
            form: {
                type: 'string',
                placeholder: '请输入',
            }
        }, {
            isInForm: false,
            table: {
                dataIndex: 'action', //表格里面的字段
                key: 'action',//表格的唯一key  
                btns: [
                    {
                        name: 'edit',
                        render: function (rowData) {
                            return '<a>修改</a>';
                        },
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
                                    apiName: 'updateBaseFlowSponsorChooserByList',
                                }
                            }
                        ]
                    }
                ]
            }
        },
    ]
}