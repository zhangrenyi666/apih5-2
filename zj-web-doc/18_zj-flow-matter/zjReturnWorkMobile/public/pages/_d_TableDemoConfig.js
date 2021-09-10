// test start 
var apisByProject = {
    add: "addZxQrcodeOrganizationLevel",
    del: 'batchDeleteUpdateZxQrcodeOrganizationLevel',
    update: 'pcUpdateZxQrcodeOrganizationLevelRootNode',
    list: 'getZxQrcodeOrganizationLevelList',
}
var id = null;
var formData = null;
//tab项布局
var formItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
    }
};
//tab项布局
var formItemLayout1 = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
    }
};
var formItemLayout1_1 = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
    }
};
// test end 


window.TableDemoConfig = {

    common: {
        firstRowIsSearch: true,
        fetchConfig: {//表格的ajax配置
            apiName: apisByProject.list,
            otherParams: {
                parentId: '0'
            }
        },
        antd: { //同步antd table组件配置 ***必传
            rowKey: "levelId",
            size: "small"
        },
        drawerConfig: {
            width: '900px'
        },
        paginationConfig: {// 同步antd的分页组件配置   
            position: 'bottom'
        },

        isShowRowSelect: true,//是否显示选择框  默认true
        actionBtns: function (obj) {
            // console.log(obj.routerInfo)
            var btns = [
                {
                    name: 'add',//内置add del
                    icon: 'plus',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '新增',
                    // hide:true,
                    formBtns: [
                        {
                            name: 'cancel', //关闭右边抽屉
                            type: 'dashed',//类型  默认 primary
                            label: '取消',
                        },
                        {
                            name: 'submit', //关闭右边抽屉
                            type: 'primary',//类型  默认 primary
                            label: '提交',
                            fetchConfig: {
                                apiName: apisByProject.add
                            }
                        },

                        // {
                        //     name: 'submitDiy',//内置add del
                        //     type: 'primary',//类型  默认 primary
                        //     label: '提交',//提交数据并且关闭右边抽屉 
                        //     onClick: function (obj) {//ajax配置  
                        //         //合并tab项
                        //         var msg = obj.btnCallbackFn.msg;
                        //         var fetch = obj.btnCallbackFn.fetch;
                        //         var closeDrawer = obj.btnCallbackFn.closeDrawer;
                        //         var refresh = obj.btnCallbackFn.refresh;
                        //         var formatData = obj.btnCallbackFn.formatData;
                        //         var tabs = window.ProjectManagement.tabs;
                        //         var formConfigAll = [];


                        //         for (var i = 0; i < tabs.length; i++) {
                        //             formConfigAll = formConfigAll.concat(tabs[i].content.formConfig);
                        //         }
                        //         var data = formatData(obj._formData,formConfigAll,'get');
                        //         if (!data.levelCode || !data.levelName || !data.levelShortName) {
                        //             msg.error("请先填写基本信息！");
                        //             return;
                        //         }
                        //         if (obj.btnCallbackFn.getActiveKey() === "0") {
                        //             obj.btnCallbackFn.setActiveKey('1');
                        //         } else {
                        //             fetch(apisByProject.add,data,function (res) {
                        //                 var success = res.success;
                        //                 var message = res.message;
                        //                 // var resData = res.data;
                        //                 if (success) {
                        //                     msg.success(message);
                        //                     closeDrawer(false);
                        //                     refresh()
                        //                 } else {
                        //                     msg.error(message);
                        //                 }
                        //             })
                        //         }
                        //     }
                        // }
                    ]
                },
                {
                    name: 'del',//内置add del
                    icon: 'delete',//icon
                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                    label: '删除',
                    fetchConfig: {//ajax配置
                        apiName: apisByProject.del,
                    },
                }
            ];

            return btns;
        },


        formConfig: [
            //表单中显示的字段
            {
                isInTable: false,
                form: {
                    field: 'levelId',
                    hide: true,
                    type: 'string',
                    required: true,
                    placeholder: '请输入'
                }
            },
            {
                isInTable: false,
                form: {
                    field: 'parentId',
                    hide: true,
                    type: 'string',
                    required: true,
                    initialValue: '0',
                    placeholder: '请输入'
                }
            },
            {
                isInTable: false,
                form: {
                    field: 'propertyFlag',
                    hide: true,
                    type: 'string',
                    required: true,
                    initialValue: '0',
                    placeholder: '请输入'
                }
            },


            {
                isInTable: false,
                form: {
                    field: 'levelName', //表格里面的字段
                    label: '项目名称', //表头标题
                    type: 'string',
                    required: true,
                    placeholder: '请输入项目名称',
                }
            },

            {
                isInTable: false,
                form: {
                    label: "项目简称",
                    field: 'levelShortName', //表格里面的字段
                    type: 'string',
                    required: true,
                    placeholder: '请输入项目简称',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: "项目编号",
                    field: 'levelCode', //表格里面的字段
                    type: 'string',
                    required: true,
                    placeholder: '请输入项目编号',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: "项目分类",
                    field: "category",
                    type: 'select',
                    // required: true,
                    placeholder: '请选择',
                    required: true,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'xiangmufenlei'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '项目状态', //表头标题
                    field: 'prjStatus', //表头标题
                    type: 'select',
                    placeholder: '请选择',
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'xiangmuzhuangtai'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '项目地址', //表头标题
                    field: 'pcdMergeCode', //表格里面的字段
                    type: 'cascader',
                    required: true,
                    placeholder: '请选择项目地址',
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
                    spanForm: 12,
                    // offset:2,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    spanForm: 12,
                    label: '', //表头标题
                    field: 'detailedAddress', //表格里面的字段
                    type: 'string',
                    placeholder: '请输入项目详细地址',
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 0 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 24 }
                        }
                    }
                }
            },
            {
                isInTable: false,
                form: {
                    field: 'lngLat', //表格里面的字段
                    label: '经纬度', //表头标题
                    type: 'string',
                    placeholder: '请输入经纬度',
                }
            },
            {
                isInTable: false,
                form: {
                    label: '百度地图坐标拾取', //表头标题
                    field: 'baidu_zuobiao', //表格里面的字段
                    type: 'Component',
                    Component: "baidu_zuobiao",
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },

            {
                isInTable: false,
                form: {
                    label: '是否集团投资项目', //表头标题
                    field: 'isGroupInvestment', //表格里面的字段
                    type: 'radio',
                    required: true,
                    placeholder: '请输入',
                    initialValue: "0",
                    optionData: [
                        {
                            label: "否",value: '0'
                        },

                        {
                            label: "是",value: '1'
                        }
                    ],
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '是否政府工程', //表头标题
                    field: 'isGovernmentProject', //表格里面的字段
                    type: 'radio',
                    required: true,
                    placeholder: '请输入',
                    initialValue: "0",
                    optionData: [
                        {
                            label: "否",value: '0'
                        },

                        {
                            label: "是",value: '1'
                        }
                    ],
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    }
                }
            },
            {
                isInTable: false,
                form: {
                    label: '建设单位名称', //表头标题
                    field: 'buildCorpName', //表格里面的字段
                    // type: 'select',
                    type: 'string',
                    placeholder: '请输入',
                    required: true,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '项目负责人', //表头标题
                    field: 'buildLeaderName', //表格里面的字段 
                    type: 'string',
                    placeholder: '请输入建设单位项目负责人',
                    // disabled: true,
                    required: true,
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },

                }
            },
            {
                isInTable: false,
                form: {
                    label: '电话', //表头标题
                    field: 'buildLeaderPhone', //表格里面的字段 
                    type: 'string',
                    placeholder: '请输入建设单位项目负责人电话',
                    required: true,
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: "建设单位统一社会信用代码",
                    field: "buildCorpCode",
                    type: 'string',
                    placeholder: '请输入建设单位统一社会信用代码',
                    formItemLayoutForm: formItemLayout,
                }
            },

            {
                isInTable: false,
                form: {
                    label: "建设用地规划许可证编号",
                    field: "buildPlanNum",
                    type: 'string',
                    placeholder: '请输入建设用地规划许可证编号',
                    formItemLayoutForm: formItemLayout,
                }
            },
            {
                isInTable: false,
                form: {
                    label: "建设工程规划许可证编号",
                    field: "prjPlanNum",
                    type: 'string',
                    placeholder: '请输入建设用地规划许可证编号',
                    formItemLayoutForm: formItemLayout,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '开工日期', //表头标题
                    field: 'plannedStartDate', //表头标题
                    type: 'date',
                    placeholder: '请选择',
                    showTime: false,
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '竣工日期', //表头标题
                    field: 'plannedCompletionDate', //表头标题
                    type: 'date',
                    placeholder: '请选择',
                    showTime: false,
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '总投资（万元）', //表头标题
                    field: 'invest', //表头标题
                    type: 'number',
                    placeholder: '请输入',
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '总面积（平方米）', //表头标题
                    field: 'buildingArea', //表头标题
                    type: 'number',
                    placeholder: '请输入',
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '总长度（米）', //表头标题
                    field: 'buildingLength', //表头标题 
                    type: 'number',
                    placeholder: '请输入',
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '立项文号', //表头标题
                    field: 'approvalNum', //表头标题
                    type: 'string',
                    placeholder: '请输入',
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '建设规模', //表头标题
                    field: 'prjSize', //表头标题
                    type: 'select',
                    placeholder: '请选择',
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'jiansheguimo'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '建设性质', //表头标题
                    field: 'propertyNum', //表头标题
                    type: 'select',
                    placeholder: '请选择',
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'jianshexingzhifenlei'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '立项级别', //表头标题
                    field: 'approvalLevelNum', //表头标题
                    type: 'select',
                    placeholder: '请选择',
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'lixiangjibie'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '工程用途', //表头标题
                    field: 'prjNum', //表头标题
                    type: 'select',
                    placeholder: '请选择',
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'gongchengyongtu'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '施工许可证号', //表头标题
                    field: 'builderLicenseNum', //表头标题
                    type: 'string',
                    placeholder: '请输入',
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                    colWrapperStyle: {
                        paddingRight: 0
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '国籍或地区', //表头标题
                    field: 'nationNum', //表头标题
                    type: 'select',
                    placeholder: '请选择',
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'GuoJiHuoDiQu'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    spanForm: 12,
                    formItemLayoutForm: formItemLayout1,
                }
            },
            {
                isInTable: false,
                form: {
                    label: '备注', //表头标题
                    field: 'remarks', //表格里面的字段
                    type: 'textarea',
                    placeholder: '请输入'
                }
            },
            {
                isInTable: false,
                form: {
                    label: '项目备案资料',
                    field: 'governmentAttachmentList',
                    type: 'files',
                    initialValue: [],
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '项目建设手续资料',
                    field: 'constructionAttachmentList',
                    type: 'files',
                    initialValue: [],
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: ' 建设工程施工合同',
                    field: 'buildContractAttachmentList',
                    type: 'files',
                    initialValue: [],
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '工程款支付凭证',
                    field: 'projectFundsAttachmentList',
                    type: 'files',
                    initialValue: [],
                    fetchConfig: {
                        apiName: window.configs.domain + 'upload',
                    },
                }
            },





            //表格中显示的字段
            {
                isInSearch: false,
                isInForm: false,
                table: {
                    width: 160,
                    // fixed: 'left',
                    title: '<center>项目名称</center>', //表头标题
                    dataIndex: 'levelName', //表格里面的字段
                    key: 'levelName',//表格的唯一key  
                    onClick: "detail",
                    tooltip: 9,
                    // align: 'center',
                    willExecute: function (data) {
                        id = data.rowData.levelId;
                    }
                }
            },

            {
                isInSearch: false,
                isInForm: false,
                table: {
                    width: 170,
                    title: '<center>建设单位名称</center>', //表头标题
                    dataIndex: 'buildCorpName', //表格里面的字段
                    key: 'buildCorpName',//表格的唯一key  
                    tooltip: 15,
                    // align: 'center',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
            },
            {
                isInSearch: false,
                isInForm: false,
                table: {
                    width: 200,
                    title: '<center>施工（总承包）单位名称</center>', //表头标题
                    dataIndex: 'contractorCorpName', //表格里面的字段
                    key: 'contractorCorpName',//表格的唯一key  
                    tooltip: 15,
                    align: 'left',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
            },

            {
                isInSearch: false,
                isInForm: false,
                table: {
                    width: 120,
                    align: 'center',
                    title: '是否政府工程', //表头标题
                    dataIndex: 'isGovernmentProject', //表格里面的字段
                    key: 'isGovernmentProject',//表格的唯一key  
                    render: (data) => data === "0" ? '<div style="text-align:left">否</div>' : data === '1' ? '<div style="text-align:left">是</div>' : '-',
                    fieldsConfig: {
                        type: "radio",
                        placeholder: "请选择...",
                        optionData: [
                            {
                                label: "否",value: '0'
                            },

                            {
                                label: "是",value: '1'
                            }
                        ],
                    },
                },
                form: {
                    spanForm: 12,
                    // spanSearch:12,
                    type: 'radio',
                    required: true,
                    placeholder: '请输入',
                    optionData: [
                        {
                            label: "否",value: '0'
                        },

                        {
                            label: "是",value: '1'
                        }
                    ],
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    }
                }
            },
            {
                isInSearch: false,
                isInForm: false,
                table: {
                    width: 200,
                    title: '项目负责人', //表头标题 
                    dataIndex: 'buildLeaderName', //表格里面的字段
                    key: 'buildLeaderName',//表格的唯一key  ,
                    // title: '劳资专管员', //表头标题
                    // dataIndex: 'laborCommissionerName', //表格里面的字段
                    align: 'center',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
                form: {
                    type: 'string',
                    required: true,
                    placeholder: '请输入劳资专管员',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    }
                }
            },
            {
                isInSearch: false,
                isInForm: false,
                table: {
                    width: 120,
                    title: '手机号', //表头标题
                    dataIndex: 'buildLeaderPhone', //表格里面的字段
                    // dataIndex: 'laborCommissionerPhone', //表格里面的字段
                    key: 'buildLeaderPhone',//表格的唯一key  , 
                    align: 'center',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
                form: {
                    type: 'string',
                    required: true,
                    placeholder: '请输入劳资专管员手机号',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    }
                }
            },



            {
                isInSearch: false,
                isInTable: false,
                isInForm: false,
                table: {
                    width: 140,
                    title: '项目负责人', //表头标题
                    dataIndex: 'buildLeaderName', //表格里面的字段
                    key: 'buildLeaderName',//表格的唯一key  
                    align: 'center',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
                form: {
                    type: 'string',
                    placeholder: '请输入建设单位项目负责人姓名',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    }
                }
            },
            {
                isInSearch: false,
                isInTable: false,
                isInForm: false,
                table: {
                    width: 150,
                    title: '手机号', //表头标题
                    dataIndex: 'constructionLeaderPhone', //表格里面的字段
                    key: 'constructionLeaderPhone',//表格的唯一key   
                    align: 'center',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
                form: {
                    type: 'string',
                    placeholder: '请输入建设单位项目负责人手机号',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    }
                }
            },



            {
                isInSearch: false,
                isInTable: false,
                isInForm: false,
                table: {
                    width: 160,
                    title: '项目负责人', //表头标题
                    dataIndex: 'buildLeaderName', //表格里面的字段
                    key: 'buildLeaderName',//表格的唯一key   
                    align: 'center',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
                form: {
                    type: 'string',
                    placeholder: '请输入施工单位项目负责人姓名',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    },
                    colWrapperStyle: {
                        paddingRight: 0
                    }
                }
            },
            {
                isInSearch: false,
                isInTable: false,
                isInForm: false,
                table: {
                    width: 150,
                    title: '手机号', //表头标题
                    dataIndex: 'buildLeaderPhone', //表格里面的字段
                    key: 'buildLeaderPhone',//表格的唯一key 
                    align: 'center',
                    render: function (data) {
                        return '<div style="text-align:left">' + data + '</div>'
                    }
                },
                form: {
                    type: 'string',
                    placeholder: '请输入施工单位项目负责人手机号',
                    spanForm: 12,
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 16 }
                        }
                    }
                }
            },

            // {
            //     isInTable: false,
            //     table: {
            //         title: '备注', //表头标题
            //         dataIndex: 'remarks', //表格里面的字段
            //         key: 'remarks',//表格的唯一key  
            //         tooltip: 20
            //     },
            //     form: {
            //         type: 'textarea',
            //         placeholder: '请输入'
            //     }
            // },


            {
                isInForm: false,
                table: {
                    width: 70,
                    showType: 'tile',
                    dataIndex: 'action', //表格里面的字段
                    key: 'action',//表格的唯一key  
                    // fixed: 'right',
                    btns: [
                        {
                            name: 'edit',
                            render: function (rowData) {
                                return '<a>修改</a>';
                            },
                            willExecute: function (data) {
                                id = data.rowData.levelId;
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
                                        apiName: apisByProject.update,
                                    }
                                },
                                // {
                                //     name: 'submitDiy2',//内置add del
                                //     type: 'primary',//类型  默认 primary
                                //     label: '提交',//提交数据并且关闭右边抽屉 
                                //     onClick: function (obj) {//ajax配置  
                                //         //合并tab项
                                //         var msg = obj.btnCallbackFn.msg;
                                //         var fetch = obj.btnCallbackFn.fetch;
                                //         var closeDrawer = obj.btnCallbackFn.closeDrawer;
                                //         var refresh = obj.btnCallbackFn.refresh;
                                //         var formatData = obj.btnCallbackFn.formatData;
                                //         var tabs = window.ProjectManagement.tabs;
                                //         var formConfigAll = [];
                                //         for (var i = 0; i < tabs.length; i++) {
                                //             formConfigAll = formConfigAll.concat(tabs[i].content.formConfig);
                                //         }
                                //         var data = formatData(obj._formData,formConfigAll,'get');
                                //         fetch(apisByProject.update,data,function (res) {
                                //             var success = res.success;
                                //             var message = res.message;
                                //             if (success) {
                                //                 msg.success(message);
                                //                 // closeDrawer(false); 
                                //                 if (obj.btnCallbackFn.getActiveKey() === "0") {
                                //                     obj.btnCallbackFn.setActiveKey('1');
                                //                 } else {
                                //                     closeDrawer(false);
                                //                 }
                                //                 refresh()
                                //             } else {
                                //                 msg.error(message);
                                //             }
                                //         })
                                //     }
                                // }
                            ]
                        }
                    ]
                }
            }
        ]

    },


    //普通配置1
    common1: {

        fetchConfig: {
            apiName: "zong",
            // apiName: "getZxQrcodeOrganizationLevelList",
            otherParams: {},
        },
        // formItemLayout: {
        //     labelCol: {
        //         xs: { span: 24 },
        //         sm: { span: 6 }
        //     },
        //     wrapperCol: {
        //         xs: { span: 24 },
        //         sm: { span: 18 }
        //     }
        // },
        //是否在表格第一个行加搜索
        firstRowIsSearch: true,
        data: [
            {
                workId: "0",
                flowStatus: "123456",
                flowName1: '0000',
                dateTest111: 1234567891234,
                dateTest: 1234567891234,
                otherTest: '沈阳市辉南区',
                limitTime: 122,
                money: 1010500,
                other3: "other3",
                checkbox: "1",
                qnnFormTest2: {
                    name: '张三',
                    age: 88
                },
                selectByPagingTest: "1DF031KHR0003400A8C000006108E506",
                // selectPage2: '1DF031KHR0003400A8C000006108E506',
                levelName: "1分部",
                "levelName2-1": "2分部",
                grade: "0",
                class: "1",
            },
            {
                workId: "1",
                flowStatus: "3215644",
                flowName1: '2222',
                dateTest: 1234567891234,
                otherTest: '大连市中山区',
                selectByPagingTest: "1DF031KHR0003400A8C000006108E506",
                limitTime: 22,
                other2: "other2",
                money: 4010500,
                other3: "other3",
                checkbox: "1",
                qnnFormTest2: {
                    name: '李四',
                    age: 50
                },
                "levelName2-1": "2分部",
                levelName: "2分部",
                grade: "1",
                class: "0",
            }
        ],
        getRowSelection: function (obj) {
            return {
                getCheckboxProps: function (record) {
                    return {
                        name: record.name,
                        disabled: record.flowStatus === '审核中', // Column configuration not to be checked
                    }
                }
            }
        },
        antd: {
            rowKey: "workId",
            size: "small",
        },
        // diyTableRow: function (reactDom) {
        //     // console.log(reactDom);
        //     // if (reactDom.props.index === 2) {
        //     //     delete reactDom.props.columns[0]
        //     // }
        //     console.log(reactDom)
        //     // let aaa = reactDom.props.children();
        //     // console.log(aaa)
        //     return reactDom;
        // },
        // desc: "这是一段描述...",
        //和表格连在一起的地步表格按钮配置
        // drawerConfig: {
        //     width: "800px"
        //     maskClosable:false, //点击蒙层是否关闭抽屉  默认true
        // },
        // infoAlert: function(selectedRows) {
        //     return "已选择 " + selectedRows.length + "项";
        // }, 
        searchFormColNum: 4,
        paginationConfig: {
            position: "bottom"
        },
        actionBtns: [
            {
                name: "add",
                icon: "plus",
                type: "primary",
                label: "新增",
                formBtns: [
                    {
                        field: "getDataBtn",
                        name: "diy",
                        type: "dashed",
                        label: "拿数据",
                        onClick: "bind:getDataBtn",
                        isValidate: false,//点击后是否验证表单 默认true
                    },
                    {
                        field: "cancelBtn",
                        name: "cancel",
                        type: "dashed",
                        label: "取消"
                    },
                    {
                        field: "submitBtn",
                        name: "submit",
                        type: "primary",
                        label: "提交",
                        fetchConfig: {
                            //---新增接口
                            apiName: "add"
                        }
                    }
                ],
                hide: "bind:addBtnIsHide",
            },
            {
                name: "diyAdd",
                icon: "plus",
                type: "primary",
                label: "模拟新增按钮",
                onClick: "bind:diyAdd",
            },
            {
                label: "自定义组件",
                name: "Component",
                Component: "diyComponent"
            },
            {
                name: 'diy',//diy一个弹出选人的组价
                icon: 'person',//icon
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '人员添加',
                onClick: "bind:diyPersonAdd",
            },
            {
                name: 'diy',//diy一个弹出选人的组价
                icon: 'refresh',//icon
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '项目刷新',
                onClick: function (props) {
                    props.props.updateProjectListData()
                },
            },
            {
                name: "del",
                icon: "delete",
                type: "danger",
                label: "删除",
                fetchConfig: {
                    apiName: "del",
                },
                //params是行数据 props是页面props  
                // isCanSubmit: ({ params, props }, callback) => {
                //     console.log(params)
                //     callback(true)
                // },  
                isCanSubmit: ({ params },callback) => {
                    for (let i = 0; i < params.length; i++) {
                        if (params[i].apih5FlowStatus !== '' || params[i].apih5FlowStatus !== '0' || params[i].apih5FlowStatus !== '3') {
                            Msg.error('审核中的数据不允许删除！');
                            callback(false);
                            // break;
                            return;
                        } else if (i === params.length && (params[i].apih5FlowStatus !== '' || params[i].apih5FlowStatus !== '0' || params[i].apih5FlowStatus !== '3')) {
                            callback(true);
                        }
                    }
                },

            },
        ],

        // getBackEndConfig:{apiName:'todoList'},
        formConfig: [
            {
                isInTable: false,
                table: {
                    title: "主键id",
                    dataIndex: "educationId",

                },
                form: {
                    label: "主键id",
                    field: "educationId",
                    type: "string",
                    hide: true
                }
            },

            ...Array.from(new Array(50)).map((_,i) => {
                return {
                    isInTable: false,
                    form: {
                        type: "string",
                        label: "test" + i,
                        field: "test" + i, //唯一的字段名 ***必传 
                        spanForm:12
                    }
                }
            }),

            {
                isInTable: false,
                form: {
                    type: "richText",
                    label: "富文本描述",
                    field: "richText", //唯一的字段名 ***必传
                    ueditorConfig: {
                        //百度ue配置 可用默认的
                        // toolbars: [["fullscreen", "source", "undo", "redo", "bold"]]
                    },
                    fetchConfig: {
                        uploadUrl: window.configs.domain + "upload"
                    }
                }

            },
            {
                isInTable: false,
                form: {
                    //普通选择框 可以和其他字段关联
                    type: "selectByPaging",
                    // type: "select",
                    label: "分页下拉",
                    field: "selectByPagingTest", //唯一的字段名 ***必传
                    placeholder: "请选择",
                    fetchConfig: "bind:selectByPagingTestFn",
                    initialValue: "1DF031KHR0003400A8C000006108E506",
                    optionConfig: {
                        //下拉选项配置
                        label: "levelName",
                        value: "levelId",
                        linkageFields: {
                            dateTest: "createTime",
                            otherTest: "levelName",
                        }
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    //普通选择框 可以和其他字段关联
                    type: "datetime",
                    label: "日期测试",
                    field: "dateTest", //唯一的字段名 ***必传
                    placeholder: "请选择",
                }
            },
            {
                isInTable: false,
                form: {
                    //普通选择框 可以和其他字段关联
                    type: "datetime",
                    label: "日期测试1",
                    field: "dateTest111", //唯一的字段名 ***必传
                    placeholder: "请选择",
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                }
            },
            {
                isInTable: false,
                form: {
                    //普通选择框 可以和其他字段关联
                    type: "string",
                    label: "其他测试",
                    field: "otherTest",

                    // condition: [
                    //     {
                    //         //条件
                    //         regex: {
                    //             //匹配规则 正则或者字符串
                    //             otherTest: "1"
                    //         },
                    //         action: "hide" //disabled,  show,  hide, function(){}
                    //     }
                    // ]
                }
            },

            // {
            //     isInTable: false,
            //     form: {
            //         //分页选择框 可以和其他字段关联
            //         type: "selectByPaging",
            //         label: "下拉分页2",
            //         field: "selectPage2", //唯一的字段名 ***必传
            //         placeholder: "请选择",
            //         fetchConfig: {
            //             apiName: "getZxQrcodeOrganizationLevelList",
            //             otherParams: {
            //                 parentId: "0",
            //                 first: false
            //             },
            //             params: {
            //                 aaa: "selectPage1",
            //                 bbb: "sendUserName1",
            //             }
            //             // searchKey:'itemName'
            //         },
            //         // initialValue:"qiyezizhizigezhuanyeleibie",
            //         optionConfig: {
            //             //下拉选项配置
            //             label: "levelShortName",
            //             value: "levelId", //这里不能和select类型一样配置为数组 这里只能配置为string 
            //         },
            //         pageConfig: {
            //             //设置每页显示多少条数据
            //             limit: 20
            //         }
            //     }
            // },

            {
                isInTable: false,
                form: {
                    type: "linkage",
                    children: {
                        //所有配置见qnn-form
                        form: {
                            label: "年级",
                            field: "grade",
                            type: "select",
                            placeholder: "请选择",
                            optionData: [
                                {
                                    label: "一年级",
                                    value: "0"
                                },
                                {
                                    label: "二年级",
                                    value: "1"
                                }
                            ]
                        },
                        children: {
                            form: {
                                label: "班级",
                                placeholder: "请选择",
                                field: "class",
                                type: "select",
                                optionData: [
                                    {
                                        label: "一年级二班",
                                        value: "0"
                                    },
                                    {
                                        label: "二年级三班",
                                        value: "1"
                                    }
                                ]
                            }
                        }
                    }
                }
            },


            {
                isInSearch: true,
                table: {
                    // fixed: 'left',
                    // width: 120,
                    title: "<center>所属分部</center>",
                    noHaveSearchInput: true,
                    // dataIndex: "levelName1",
                    // key: "flowStatus1",

                    // onClick: "detail",
                    // btns: [{
                    //     name: "详情按钮",
                    //     label: "删除",
                    // }],

                    children: [
                        {
                            title: "子集1",
                            noHaveSearchInput: true,
                            // dataIndex: "levelName1-1",
                            // key: "flowStatus1-1",
                            children: [
                                {
                                    width: 200,
                                    title: "子集1-1",
                                    dataIndex: "levelName",
                                    key: "flowStatus",
                                    align: "center",
                                    // render:function(){
                                    //     // return "123"
                                    // }
                                }
                            ]
                        },
                        {
                            title: "子集2",
                            noHaveSearchInput: true,
                            dataIndex: "levelName2",
                            key: "flowStatus2",
                            children: [
                                {
                                    width: 200,
                                    title: "子集2-1",
                                    dataIndex: "levelName2-1",
                                    key: "flowStatus2-1",
                                    align: "center"
                                }
                            ]
                        }
                    ],

                    // filter: true, 
                    // tdEdit: function (rowData,colData,props) {
                    //     // console.log(rowData);
                    //     // if (rowData.flowName === '测试') return false;
                    //     return true;
                    // },
                    fieldsConfig: {
                        field: "aaa",
                        type: "select",
                        placeholder: "请选择",
                        optionData: [
                            {
                                label: "选项一",
                                value: '0'
                            },
                            {
                                label: "选项二",
                                value: '1'
                            }
                        ],
                        disabled: function (obj) {
                            if (obj.record.flowStatus === "654321") {
                                return true
                            }
                            return false;
                        }

                        // optionConfig: {
                        //     //下拉选项k值
                        //     label: "departmentName",
                        //     value: "departmentId"
                        // },
                        // fetchConfig: {
                        //     //下拉接口
                        //     apiName: "getSysDepartmentListByCondition",
                        //     //下拉接口需要的参数
                        //     otherParams: {
                        //         departmentPath: "",
                        //         departmentFlag: 2
                        //     }
                        // }
                    },
                    tdEditFetchConfig: {
                        apiName: "selectUpDate",
                        params: {}, //可为func
                        otherParams: {}, //可为func
                    }
                },
                form: {
                    label: "所属分部",
                    field: "branchtId",
                    type: "select",
                    placeholder: "请选择",
                    optionConfig: {
                        //下拉选项k值
                        label: "departmentName",
                        value: "departmentId"
                    },
                    fetchConfig: {
                        //下拉接口
                        apiName: "getSysDepartmentListByCondition",
                        //下拉接口需要的参数
                        otherParams: { departmentPath: "",departmentFlag: 2 }
                    },
                    spanForm: 12, //表单中行格数 一行24格 默认24   form
                    formItemLayoutForm: {
                        labelCol: {
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            sm: { span: 16 }
                        }
                    }
                }
            },
            {
                table: {
                    width: 180,
                    title: "<center>交底名称</center>",
                    dataIndex: "sendUserName",
                    key: "sendUserName",
                    tdEdit: true,
                    // // filter: true,
                    // //单元格可编辑
                    // tdEdit: true,
                    // //配置tdEdit后fieldsConfig必须配置
                    // //该配置和formConfig的form【配置项】一样  field默认和dataIndex一样【也可单独配置】
                    fieldsConfig: {
                        field: "keyword",
                        type: "string",
                        placeholder: "请输入...",
                        disabled: "bind:sendUserNameDisabled"
                    },
                    render: "bind:sendUserNameRennder"
                },
                form: {
                    // addDisabled: true,
                    required: true,
                    label: "交底名称",
                    type: "string",

                    labelClcik: function (obj) {
                        // console.log(obj);
                        var openTree = obj.tableFns.openTree;
                        var setFieldsValue = obj.form.setFieldsValue;

                        openTree({
                            field: "testaa",
                            fetchConfig: {//获取树结构的配置
                                apiName: 'getSysDepartmentUserAllTree',
                            },
                            initialValue: [
                                {
                                    value: "000",
                                    label: '数据一'
                                }
                            ],
                            onCancel: function () {
                                openTree({
                                    openTree: false
                                })
                            },
                            onSave: function (val) {
                                // console.log(val);
                                setFieldsValue({
                                    flowName1: val[0].label
                                });
                                openTree({
                                    openTree: false
                                })
                            },
                        })
                    },

                    placeholder: "请输入",
                    spanForm: 12, //表单中行格数 一行24格 默认24   form
                    // initialValue:[],
                    formItemLayoutForm: {
                        labelCol: {
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            sm: { span: 16 }
                        }
                    }
                }
            },
            {
                isInSearch: true,
                table: {
                    width: 230,
                    title: "培训时间",
                    dataIndex: "sendTime",
                    key: "sendTime",
                    format: "YYYY-MM-DD HH:mm:ss",
                    // filter: true,
                    //单元格可编辑
                    tdEdit: true,
                    fieldsConfig: {
                        // type: "datetime",
                        type: "rangeDate",
                        placeholder: "请选择"
                    },
                    tdEditFetchConfig: {
                        apiName: "upDate",
                        params: {}, //可为func
                        otherParams: {} //可为func
                    }
                },
                form: {
                    spanSearch: 12,
                    label: "培训时间",
                    // type: "month",
                    type: "rangeDate",
                    showTime: true,
                    placeholder: "请选择"
                }
            },

            {
                table: {
                    title: "培训地点",
                    dataIndex: "sendUserName1",
                    key: "sendUserName1",
                    fieldsConfig: {
                        field: 'addressSearch',
                        type: "string",
                        placeholder: "请输入..."
                    },
                },
                form: {
                    label: "培训地点",
                    type: "string",
                    placeholder: "请输入",
                    // initialValue:"123"
                    initialValue: function () {
                        return "444"
                    }
                }
            },
            {
                table: {
                    title: "培训学时",
                    dataIndex: "limitTime",
                    key: "limitTime",
                    align: "right",
                    defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                    sorter: function (a,b) { return a.educationPeriod - b.educationPeriod }, //排序规则
                    // filter: true 
                    fieldsConfig: {
                        field: 'limitTimeSearch',
                        type: "string",
                        placeholder: "请输入..."
                    },
                },
                form: {
                    label: "培训学时",
                    type: "number",
                    placeholder: "请输入"
                }
            },
            {
                isInTable: false,
                form: {
                    label: "复选框",
                    field: "checkbox",
                    type: "checkbox",
                    optionData: [
                        {
                            label: "测试1",
                            value: "0"
                        },
                        {
                            label: "测试2",
                            value: "1"
                        },
                        {
                            label: "测试3",
                            value: "2"
                        }
                    ]
                }
            },
            {
                isInTable: false,
                isInForm: false,
                form: {
                    label: "表格字段测试",
                    field: "qnnTable",
                    type: "qnnTable",
                    qnnTableConfig: {
                        fetchConfig: {
                            apiName: "getTodoList",
                            otherParams: {
                                flowId: "zxHwZlTroubleJl"
                            }
                        },
                        antd: {
                            rowKey: function (row) {
                                //---row.主键id
                                return row.workId;
                            }
                        },
                        //没有分页的table才能在使用增加行的操作
                        paginationConfig: false,
                        //不设置分页后需要将pageSize手动调大些
                        pageSize: 9999,
                        //操作按钮的位置  top | bottom  [string]  默认top
                        actionBtnsPosition: "bottom",
                        actionBtns: [
                            {
                                name: "addRow",
                                icon: "plus",
                                type: "primary",
                                label: "新增行",
                                //新增时候的默认数据
                                addRowDefaultData: {
                                    workId: "new",
                                    flowStatus: "请选择",
                                    flowName: "请输入",
                                    sendTime: "请选择",
                                    title: "请输入",
                                    limitTime: "请输入"
                                },
                                //ajax配置 和 fetchConfig一样
                                addRowFetchConfig: {
                                    apiName: "addRow",
                                    otherParams: {}
                                }
                            },
                            {
                                name: "add", //【add, addRow,  del, edit, detail, Component, form】
                                icon: "plus", //icon
                                type: "primary", //类型  默认 primary
                                label: "抽屉形式新增",
                                drawerTitle: "新增", //点击后的抽屉标题
                                //表单里面的按钮  name内置 【submit， cancel】
                                formBtns: [
                                    {
                                        name: "cancel", //关闭右边抽屉
                                        type: "dashed", //类型  默认 primary
                                        label: "取消"
                                    },
                                    {
                                        name: "submit", //内置add del
                                        type: "primary", //类型  默认 primary
                                        label: "提交", //提交数据并且关闭右边抽屉
                                        fetchConfig: {
                                            //ajax配置
                                            apiName: "submit"
                                        }
                                    }
                                ]
                            },
                            {
                                name: "del",
                                icon: "delete",
                                type: "danger",
                                label: "删除",
                                fetchConfig: {
                                    //---删除接口
                                    apiName: "del"
                                }
                            }
                        ],
                        formConfig: [
                            {
                                isInSearch: false,
                                table: {
                                    width: 120,
                                    title: "所属分部",
                                    dataIndex: "flowStatus",
                                    key: "flowStatus",
                                    filter: true,
                                    tdEdit: true,
                                    fieldsConfig: {
                                        type: "select",
                                        placeholder: "请选择...",
                                        optionConfig: {
                                            //下拉选项k值
                                            label: "departmentName",
                                            value: "departmentId"
                                        },
                                        fetchConfig: {
                                            //下拉接口
                                            apiName:
                                                "getSysDepartmentListByCondition",
                                            //下拉接口需要的参数
                                            otherParams: {
                                                departmentPath: "",
                                                departmentFlag: 2
                                            }
                                        }
                                    },
                                    tdEditFetchConfig: {
                                        apiName: "selectUpDate",
                                        params: {}, //可为func
                                        otherParams: {} //可为func
                                    }
                                },
                                form: {
                                    label: "所属分部",
                                    field: "branchtId",
                                    type: "select",
                                    placeholder: "请选择",
                                    optionConfig: {
                                        //下拉选项k值
                                        label: "departmentName",
                                        value: "departmentId"
                                    },
                                    fetchConfig: {
                                        //下拉接口
                                        apiName:
                                            "getSysDepartmentListByCondition",
                                        //下拉接口需要的参数
                                        otherParams: {
                                            departmentPath: "",
                                            departmentFlag: 2
                                        }
                                    },
                                    spanForm: 12, //表单中行格数 一行24格 默认24   form
                                    formItemLayoutForm: {
                                        labelCol: {
                                            sm: { span: 8 }
                                        },
                                        wrapperCol: {
                                            sm: { span: 16 }
                                        }
                                    }
                                }
                            },
                            {
                                isInSearch: false,
                                table: {
                                    width: 200,
                                    title: "交底名称",
                                    dataIndex: "flowName",
                                    key: "flowName",
                                    filter: true,
                                    //单元格可编辑
                                    tdEdit: true,
                                    fieldsConfig: {
                                        type: "string",
                                        placeholder: "请输入..."
                                    },
                                    tdEditFetchConfig: {
                                        apiName: "upDate",
                                        params: {}, //可为func
                                        otherParams: {} //可为func
                                    }
                                },
                                form: {
                                    label: "交底名称",
                                    type: "string",
                                    placeholder: "请输入",
                                    spanForm: "12", //表单中行格数 一行24格 默认24   form
                                    formItemLayoutForm: {
                                        labelCol: {
                                            sm: { span: 8 }
                                        },
                                        wrapperCol: {
                                            sm: { span: 16 }
                                        }
                                    }
                                }
                            },
                            {
                                isInSearch: false,
                                table: {
                                    width: 220,
                                    title: "培训时间",
                                    dataIndex: "sendTime",
                                    key: "sendTime",
                                    format: "YYYY-MM-DD HH:mm:ss",
                                    filter: true,
                                    //单元格可编辑
                                    tdEdit: true,
                                    fieldsConfig: {
                                        type: "datetime",
                                        placeholder: "请输入..."
                                    },
                                    tdEditFetchConfig: {
                                        apiName: "upDate",
                                        params: {}, //可为func
                                        otherParams: {} //可为func
                                    }
                                },
                                form: {
                                    label: "培训时间",
                                    type: "datetime",
                                    placeholder: "请选择"
                                }
                            },

                            {
                                table: {
                                    width: 180,
                                    title: "培训地点",
                                    dataIndex: "title",
                                    key: "title",
                                    tdEdit: true,
                                    fieldsConfig: {
                                        type: "string",
                                        placeholder: "请输入..."
                                    },
                                    tdEditFetchConfig: {
                                        apiName: "upDate",
                                        params: {}, //可为func
                                        otherParams: {} //可为func
                                    },
                                    tooltip: 12
                                },
                                form: {
                                    label: "培训地点",
                                    type: "string",
                                    placeholder: "请输入"
                                }
                            },
                            {
                                table: {
                                    width: 140,
                                    title: "培训学时",
                                    dataIndex: "limitTime",
                                    key: "limitTime",
                                    align: "right",
                                    defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                                    sorter: function (a,b) {
                                        return a.educationPeriod - b.educationPeriod; //排序规则
                                    },
                                    // filter: true,
                                    tdEdit: true,
                                    fieldsConfig: {
                                        type: "string",
                                        placeholder: "请输入..."
                                    },
                                    tdEditFetchConfig: {
                                        apiName: "upDate",
                                        params: {}, //可为func
                                        otherParams: {} //可为func
                                    }
                                },
                                form: {
                                    label: "培训学时",
                                    type: "string",
                                    placeholder: "请输入"
                                }
                            }
                        ]
                    }
                }
            },
            {
                isInTable: false,
                form: {
                    label: "表单字段测试1",
                    field: "qnnFormTest1",
                    type: "qnnForm",
                    canAddForm: true,
                    qnnFormConfig: {
                        formConfig: [
                            {
                                field: 'name',
                                label: "姓名",
                                type: "string",
                                placeholder: "请输入"
                            },
                            {
                                field: 'age',
                                label: "年纪",
                                type: "number",
                                placeholder: "请输入"
                            }
                        ]
                    }
                }
            },
            {
                isInTable: false,
                form: {
                    label: "表单字段测试2",
                    field: "qnnFormTest2",
                    type: "qnnForm",
                    qnnFormConfig: {
                        formConfig: [
                            {
                                field: 'name',
                                label: "姓名",
                                type: "string",
                                placeholder: "请输入"
                            },
                            {
                                field: 'age',
                                label: "年纪",
                                type: "number",
                                placeholder: "请输入"
                            }
                        ]
                    }
                }
            },
            // {
            //     isInTable: false,
            //     form: {
            //         label: "富文本测试",
            //         field: "Rich",
            //         type: "richtext",
            //         defaultVlaue: ""
            //     }
            // },
            {
                isInSearch: false,
                isInTable: false,
                form: {
                    label: "发起对象",
                    field: "launcherList",
                    type: "treeSelect",
                    treeSelectOption: {
                        search: true,
                        searchParamsKey: "search",
                        searchApi: "getSysDepartmentUserAllTree",
                        selectType: "1",
                        fetchConfig: {
                            apiName: "getSysDepartmentUserAllTree"
                        }
                    }
                }
            },
            {
                isInSearch: false,
                isInTable: false,
                form: {
                    label: "被选对象",
                    field: "candidateList",
                    type: "treeSelect",
                    treeSelectOption: {
                        selectType: "2",
                        search: true,
                        searchParamsKey: "search",
                        searchApi: "getSysDepartmentUserAllTree",
                        fetchConfig: {
                            apiName: "getSysDepartmentUserAllTree"
                        }
                    }
                }
            },
            {
                table: {
                    dataIndex: "money",
                    title: 'money',
                    type: "money",
                    //保留小数点位数
                    // decimals:2,

                    // tdEdit: true,
                    // fieldsConfig: {
                    //     type: "money",
                    //     placeholder: "请输入..."
                    // }, 
                },
                form: {
                    label: "money",
                    field: "money",
                    type: "money",
                    initialValue: 123456786
                }
            },
            {
                table: {
                    dataIndex: "other3",
                    title: 'other3',
                },
                form: {
                    label: "other3",
                    field: "other3",
                    type: "string"
                }
            },
            {
                isInSearch: true,
                isInTable: false,
                form: {
                    label: "other4",
                    field: "other4",
                    type: "string",
                    spanSearch: 6,
                    formItemLayoutSearch: {
                        labelCol: { sm: { span: 7 } },
                        wrapperCol: { sm: { span: 17 } }
                    },
                    colWrapperStyle: {
                        paddingRight: '3px',
                    },
                }
            },
            {
                isInSearch: true,
                isInTable: false,
                form: {
                    label: "~",
                    field: "other5",
                    type: "string",
                    spanSearch: 6,
                    formItemLayoutSearch: {
                        colon: false,
                        labelCol: { sm: { span: 2 } },
                        wrapperCol: { sm: { span: 22 } }
                    },
                    colWrapperStyle: {
                        paddingLeft: '0px',
                    },
                }
            },
            {
                isInSearch: true,
                isInTable: false,
                form: {
                    label: "other6",
                    field: "other6",
                    type: "string"
                }
            },
            {
                isInTable: false,
                form: {
                    label: "附件",
                    field: "zxQrcodeAttachmentList",
                    type: "files",
                    initialValue: [],
                    fetchConfig: {
                        apiName: window.configs.domain + "upload"
                    }
                }
            },
            {
                isInForm: false,
                table: {
                    width: 110,
                    title: "操作",
                    align: "center",
                    // showType: "tile",
                    dataIndex: "action", //表格里面的字段
                    key: "action", //表格的唯一key
                    fixed: "right",
                    btns: function (props) {
                        return [
                            {
                                name: "edit",
                                label: "修改1",
                                //抽屉中的按钮
                                formBtns: [
                                    {
                                        field: "upCancelBtn",
                                        name: "cancel",
                                        type: "dashed",
                                        label: "取消",
                                    },
                                    {
                                        field: "upSubmitBtn",
                                        name: "submit",
                                        type: "primary",
                                        label: "提交",
                                        // fetchConfig: {
                                        //     //修改接口
                                        //     apiName: "update"
                                        // }
                                    }
                                ],

                                //条件配置
                                condition: [
                                    {
                                        //匹配规则 正则或者字符串
                                        //eg:行数据中的字段 id:'01' 时将禁用输入框
                                        regex: {
                                            workId: '1',
                                        },
                                        action: 'hide', //disabled,  show,  hide, function(){}
                                    }
                                ]

                            },
                            {
                                name: "detail",
                                label: "详情",
                                labelStyle: { color: '#1890ff',cursor: 'pointer' },
                            }
                        ]
                    }
                }
            }
        ]
    },

    //tabs页面配置==============
    tabsPage: {
        //   fetchConfig: {
        //     apiName: "getZxQrcodeTechnicalBasisList"
        //   },
        antd: {
            rowKey: function (row) {
                //---row.主键id
                return row.educationId;
            },
            size: "small",
        },
        desc: "这是一段描述...",
        // drawerConfig: {
        //     width: "800px"
        //     maskClosable:false, //点击蒙层是否关闭抽屉  默认true
        // },
        // infoAlert: function (selectedRows) {
        //     return "已选择 " + selectedRows.length + "项";
        // },
        //是否在表格第一个行加搜索
        firstRowIsSearch: true,
        searchFormColNum: 4,
        paginationConfig: {
            position: "bottom"
        },
        isShowRowSelect: true,
        data: [
            {
                educationId: "1",
                branchtName: "一分部",
                educationName: "测试交底",
                educationDateTime: 1234567891324,
                month: new Date().getTime(),
                checkbox: "0",
                name: "张三",
                age: 100
            },
            {
                educationId: "2",
                branchtName: "二分部",
                educationName: "测试交底2",
                educationDateTime: 1234567891324,
                month: new Date().getTime(),
                checkbox: "0",
            },
            {
                educationId: "3",
                branchtName: "三分部",
                educationName: "测试交底3",
                educationDateTime: 1234567891324,
                month: new Date().getTime(),
                checkbox: "0",
            },
            {
                educationId: "4",
                branchtName: "四分部",
                educationName: "测试交底3",
                educationDateTime: 1234567891324,
                month: new Date().getTime(),
                checkbox: "0",
            }
        ],
        actionBtns: [
            {
                name: "add",
                icon: "plus",
                type: "primary",
                label: "新增",
                formBtns: [
                    {
                        name: "diy",
                        type: "dashed",
                        label: "拿数据",
                        // hide: true,
                        hide: function (obj) {
                            if (obj.btnCallbackFn.getActiveKey() === "2") {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        onClick: function (obj) {
                            console.log(obj);
                            // console.log(obj.props.form.getFieldsValue());
                        }
                    },
                    {
                        name: "cancel",
                        type: "dashed",
                        label: "取消"
                    },
                    {
                        name: "submit",
                        type: "primary",
                        label: "提交",
                        fetchConfig: {
                            //---新增接口
                            apiName: "add"
                        }
                    },

                    {
                        type: "primary",
                        label: "切换tab",
                        filed: "changeTab",
                        isValidate: false, //是否验证表单 默认true 
                        onClick: function (args) {
                            console.log('切换')
                            args.btnCallbackFn.setActiveKey("1")
                        }
                    },
                ]
            },
            {
                name: "editDiy",
                icon: "edit",
                type: "primary",
                label: "模拟编辑",
                disabled: function (props) {
                    return !props.btnCallbackFn.getSelectedRows().length
                },
                formBtns: [
                    {
                        name: "cancel",
                        type: "dashed",
                        label: "取消"
                    },
                    {
                        name: "submit",
                        type: "primary",
                        label: "提交",
                        fetchConfig: {
                            //---新增接口
                            apiName: "add"
                        }
                    }
                ]
            },
            {
                name: "del",
                icon: "delete",
                type: "danger",
                label: "删除",
                fetchConfig: {
                    //---删除接口
                    apiName: "del"
                }
            }
        ],
        //存在tabs配置 所有打开的抽屉都是这个配置
        tabs: [
            {
                //content就是qnn-form配置
                field: "form1",
                name: "qnnForm",
                title: "表单",
                content: {
                    // fetchConfig: {
                    //     apiName: "getTodoList"
                    // },
                    formConfig: [
                        {
                            field: "month",
                            type: "month",
                            placeholder: "请选择"
                        },
                        {
                            type: "string",
                            label: "姓名",
                            field: "name", //唯一的字段名 ***必传
                            placeholder: "请输入", //占位符
                            initialValue: function (obj) {
                                // console.log(obj);
                                return "123"
                            }
                        },
                        {
                            type: "number",
                            label: "年纪",
                            field: "age", //唯一的字段名 ***必传
                            placeholder: "请输入" //占位符
                        },
                        {
                            type: "linkage",
                            // fetchConfig: {
                            //     apiName: "getZjFlowPartyFeeDetailAllList"
                            // },
                            optionConfig: {
                                value: "detailId",
                                label: "detailName",
                                children: "currentList"
                            },
                            children: {
                                //所有配置见qnn-form
                                form: {
                                    label: "党费明细一",
                                    field: "feeDetailId1",
                                    type: "select",
                                    // required: true, //是否必填
                                    placeholder: "请选择"
                                },
                                children: {
                                    form: {
                                        label: "党费明细二",
                                        placeholder: "请选择",
                                        field: "feeDetailId2",
                                        // required: true, //是否必填
                                        type: "select"
                                    }
                                }
                            }
                        }
                    ]
                }
            },
            {
                //content就是qnn-form配置
                field: "form2",
                name: "qnnForm",
                title: "表单2",
                // disabled: function () {
                //     return true
                // },
                content: {
                    // fetchConfig: {
                    //     apiName: "getTodoList"
                    // },
                    formConfig: [
                        //普通字段
                        {
                            type: "string",
                            label: "普通字段",
                            field: "title2", //唯一的字段名 ***必传
                            placeholder: "请输入" //占位符
                        },
                        //数字类型
                        {
                            type: "number",
                            label: "数字字段",
                            field: "number2", //唯一的字段名 ***必传
                            placeholder: "请输入",
                            initialValue: 100
                        }
                    ]
                }
            },
            {
                //content就是qnn-table配置
                field: "table1",
                name: "qnnTable",
                title: "表格",
                // disabled: true,
                content: {
                    fetchConfig: {
                        apiName: "list-aaa"
                    },
                    // tabs: [], //注意：嵌入到tabs中的qnn-table配置必须配置tabs项 否则tabs项将和上级重复
                    drawerConfig: {
                        width: "800px"
                        // maskClosable:false, //点击蒙层是否关闭抽屉  默认true
                    },
                    antd: {
                        rowKey: function (row) {
                            //---row.主键id
                            return row.id;
                        },
                        size: "small"
                    },
                    actionBtns: [
                        {
                            name: "add", //【add,  del, edit, detail, Component, form】
                            icon: "plus", //icon
                            type: "primary", //类型  默认 primary
                            label: "新增",
                            drawerTitle: "新增", //点击后的抽屉标题
                            //表单里面的按钮  name内置 【submit， cancel】
                            formBtns: [
                                {
                                    name: "cancel", //关闭右边抽屉
                                    type: "dashed", //类型  默认 primary
                                    label: "取消"
                                },
                                {
                                    name: "submit", //内置add del
                                    type: "primary", //类型  默认 primary
                                    label: "提交", //提交数据并且关闭右边抽屉
                                    fetchConfig: {
                                        apiName: "submit",
                                        otherParams: function (obj) {
                                            console.log(obj)
                                        }
                                    }
                                }
                            ],

                        }
                    ],
                    paginationConfig: {
                        position: "bottom"
                    },
                    data: [
                        {
                            id: "0",
                            test: "车船税",
                            month: 1234567891234,
                            createUserName: "oldWang",
                            educationPlace: "沈阳高新区"
                        }
                    ],
                    formConfig: [
                        {
                            table: {
                                title: "测试",
                                dataIndex: "test",
                                key: "test",
                                onClick: "edit",
                            },
                            form: {
                                type: "string",
                                placeholder: "请输入",
                                // hide: function (obj) {
                                //     console.log(obj)
                                // },
                                // addShow:false,
                                // editShow:true,
                                // detailShow:true,
                            }
                        },
                        {
                            table: {
                                title: "月测试",
                                dataIndex: "month",
                                key: "month",
                                format: "YYYY-MM"
                            },
                            form: {
                                field: "month",
                                type: "month",
                                placeholder: "请选择"
                            }
                        },
                        {
                            table: {
                                title: "培训讲师",
                                dataIndex: "createUserName",
                                key: "createUserName",
                            },
                            form: {
                                label: "培训讲师",
                                field: "createUserName",
                                type: "string",
                                placeholder: "请输入"
                            }
                        },
                        {
                            table: {
                                title: "培训地点",
                                dataIndex: "educationPlace",
                                key: "educationPlace"
                            },
                            form: {
                                label: "培训地点",
                                field: "educationPlace",
                                type: "string",
                                placeholder: "请输入"
                            }
                        }
                    ]
                }
            }
        ],

        //正常的qnn-table配置 需要注意的地方所有项的isInForm都只能为false
        formConfig: [
            {
                isInTable: false,
                isInForm: false, //tabs配置存在时必须设为false
                table: {
                    title: "主键id",
                    dataIndex: "educationId"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "所属分部",
                    dataIndex: "branchtName",
                    key: "branchtName",
                    onClick: "detail",
                    btns: false,
                    render: function (data,row,index) {
                        if (row.educationId === "1") {
                            return {
                                children: data,
                                props: {
                                    colSpan: 2,
                                }
                            }
                        }
                        if (row.educationId === "2") {
                            return {
                                children: data,
                                props: {
                                    rowSpan: 2,
                                }
                            }
                        } else if (row.educationId === "3") {
                            return {
                                children: data,
                                props: {
                                    rowSpan: 0,
                                }
                            }
                        }
                        return data;
                    },
                    // filter: true
                },
                form: {
                    type: "string"
                }
            },
            {
                isInSearch: true,
                isInForm: false,
                table: {
                    title: "交底名称",
                    dataIndex: "educationName",
                    key: "educationName",
                    render: function (data,row,index) {
                        if (index === 0) {
                            return {
                                children: data,
                                props: {
                                    colSpan: 0,
                                }
                            }
                        }
                        return data;
                    }
                },
                form: {
                    type: "string"
                }
            },
            {
                isInSearch: true,
                isInForm: false,
                table: {
                    // colSpan:2,
                    title: "培训时间",
                    dataIndex: "educationDateTime",
                    key: "educationDateTime",
                    format: "YYYY-MM-DD HH:mm:ss"
                },
                form: {
                    type: "datetime"
                }
            },

            {
                isInSearch: true,
                isInForm: false,
                table: {
                    title: "月测试",
                    dataIndex: "month",
                    key: "month",
                    format: "YYYY-MM"
                },
                form: {
                    type: "month"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "培训讲师",
                    dataIndex: "createUserName",
                    key: "createUserName"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "培训地点",
                    dataIndex: "educationPlace",
                    key: "educationPlace"
                }
            },
            {
                isInForm: false,
                table: {
                    title: "培训学时",
                    dataIndex: "educationPeriod",
                    key: "educationPeriod",
                    align: "right",
                    defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                    sorter: function (a,b) {
                        return a.educationPeriod - b.educationPeriod
                    } //排序规则
                }
            },
            {
                isInForm: false,
                table: {
                    width: 110,
                    title: "操作",
                    align: "center",
                    showType: "tile",
                    dataIndex: "action", //表格里面的字段
                    key: "action", //表格的唯一key
                    btns: function (obj) {
                        console.log(obj)
                        return [
                            {
                                name: "edit",
                                render: function () {
                                    return "<a>修改</a>";
                                },
                                formBtns: [
                                    {
                                        name: "cancel",
                                        type: "dashed",
                                        label: "取消"
                                    },
                                    {
                                        name: "submit",
                                        type: "primary",
                                        label: "提交",
                                        fetchConfig: {
                                            //修改接口
                                            apiName: "update"
                                        }
                                    }
                                ]
                            },
                            {
                                name: "detail",
                                render: function () {
                                    return "<a>详情</a>";
                                },
                                condition: [
                                    {
                                        regex: {
                                            educationId: "1"
                                        },
                                        action: "hide"
                                    }
                                ]
                            }
                        ]
                    }
                }
            }
        ]
    },

    //无分页列表配置=============
    noPages: {
        fetchConfig: {
            apiName: "getTodoList",
            otherParams: {
                flowId: "zxHwZlTroubleJl"
            }
        },
        data: [{
            workId: "0",
            flowName: "ddd"
        },{
            workId: "1",
            flowName: "10"
        }],
        antd: {
            rowKey: function (row) {
                //---row.主键id
                return row.workId;
            },
            size: "small",
        },
        //没有分页的table才能在使用增加行的操作
        paginationConfig: false,
        //不设置分页后需要将pageSize手动调大些
        pageSize: 9999,
        //操作按钮的位置  top | bottom  [string]  默认top
        actionBtnsPosition: "bottom",
        actionBtns: [
            {
                name: "addRow",
                icon: "plus",
                type: "primary",
                label: "新增行",
                //新增时候的默认数据
                addRowDefaultData: {
                    workId: "new",
                    flowStatus: "请选择",
                    flowName: "请输入",
                    sendTime: "请选择",
                    title: "请输入",
                    limitTime: "请输入"
                },
                //ajax配置 和 fetchConfig一样
                addRowFetchConfig: {
                    apiName: "addRow",
                    otherParams: {}
                }
                //新增行完后的回调在配置fetchConfig后将无意义
                // addCb:(obj)=>{
                //     console.log(obj)
                // }
            },
            {
                name: "del",
                icon: "delete",
                type: "danger",
                label: "删除",
                fetchConfig: {
                    //---删除接口
                    apiName: "del"
                }
            }
        ],
        formConfig: [
            {
                isInSearch: false,
                table: {
                    width: 120,
                    title: "所属分部",
                    dataIndex: "flowStatus",
                    key: "flowStatus",
                    filter: true,
                    tdEdit: true,
                    fieldsConfig: {
                        type: "select",
                        placeholder: "请选择...",
                        optionConfig: {
                            //下拉选项k值
                            label: "departmentName",
                            value: "departmentId"
                        },
                        fetchConfig: {
                            //下拉接口
                            apiName: "getSysDepartmentListByCondition",
                            //下拉接口需要的参数
                            otherParams: {
                                departmentPath: "",
                                departmentFlag: 2
                            }
                        }
                    },
                    tdEditFetchConfig: {
                        apiName: "selectUpDate",
                        params: {}, //可为func
                        otherParams: {} //可为func
                    }
                },
                form: {
                    label: "所属分部",
                    field: "branchtId",
                    type: "select",
                    placeholder: "请选择",
                    // optionConfig: {
                    //     //下拉选项k值
                    //     label: "departmentName",
                    //     value: "departmentId"
                    // },
                    // fetchConfig: {
                    //     //下拉接口
                    //     apiName: "getSysDepartmentListByCondition",
                    //     //下拉接口需要的参数
                    //     otherParams: { departmentPath: "",departmentFlag: 2 }
                    // },
                    optionData: [{ label: "分部一",value: '0' },{ label: "分部二",value: '1' }],
                    spanForm: "12", //表单中行格数 一行24格 默认24   form
                    formItemLayoutForm: {
                        labelCol: {
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            sm: { span: 16 }
                        }
                    }
                }
            },
            {
                isInForm: false,
                table: {
                    title: '下拉测试',
                    dataIndex: 'selctTest',
                    tdEdit: true,
                    fieldsConfig: {
                        type: "select",
                        placeholder: "请输入...",
                        fetchConfig: {
                            apiName: 'getTodoList',
                            otherParams: {}
                        },
                        optionConfig: {
                            label: 'title',
                            value: "workId",
                        },
                    },
                    tdEditFetchConfig: {
                        apiName: "upDate",
                        params: {}, //可为func
                        otherParams: {}, //可为func  
                    },
                    render: (data) => {
                        return data
                    }
                }
            },
            {
                isInSearch: false,
                table: {
                    width: 180,
                    title: "交底名称",
                    dataIndex: "flowName",
                    key: "flowName",
                    filter: true,
                    //单元格可编辑
                    tdEdit: function (obj,row,a) {
                        // console.log(a)
                        return true
                    },
                    //配置tdEdit后fieldsConfig必须配置
                    //该配置和formConfig的form【配置项】一样  field默认和dataIndex一样【也可单独配置】
                    fieldsConfig: {
                        type: "string",
                        placeholder: "请输入...",
                        disabled: function (obj) {
                            // console.log(obj)
                            if (obj.record.workId === "0") {
                                return true
                            }
                            return false;
                        },
                    },
                    //不配置fetchConfig的回调（配置fetchConfig该属性将无意义） 包含新旧行数据 新旧table数据以及插件props和内置方法
                    // tdEditCb:(obj)=>{
                    //     console.log(obj)
                    // },

                    tdEditFetchConfig: {
                        apiName: "upDate",
                        params: {}, //可为func
                        otherParams: {}, //可为func 

                    }
                    //配置了fetchConfig的回调 参数为后台返回数据 不配置将采用内置行为(失败成功都只是弹出提示)
                    // fetchCB:(res)=>{
                    //     console.log(res)
                    // }
                },
                form: {
                    label: "交底名称",
                    type: "string",
                    placeholder: "请输入",
                    spanForm: "12", //表单中行格数 一行24格 默认24   form
                    formItemLayoutForm: {
                        labelCol: {
                            sm: { span: 8 }
                        },
                        wrapperCol: {
                            sm: { span: 16 }
                        }
                    }
                }
            },
            {
                isInSearch: false,
                table: {
                    width: 180,
                    title: "培训时间",
                    dataIndex: "sendTime",
                    key: "sendTime",
                    format: "YYYY-MM-DD HH:mm:ss",
                    filter: true,
                    //单元格可编辑
                    tdEdit: true,
                    fieldsConfig: {
                        type: "datetime",
                        placeholder: "请输入..."
                    },
                    tdEditFetchConfig: {
                        apiName: "upDate",
                        params: {}, //可为func
                        otherParams: {} //可为func
                    }
                },
                form: {
                    label: "培训时间",
                    type: "month",
                    placeholder: "请选择"
                }
            },

            {
                table: {
                    title: "培训地点",
                    dataIndex: "title",
                    key: "title",
                    tdEdit: true,
                    fieldsConfig: {
                        type: "string",
                        placeholder: "请输入..."
                    },
                    tdEditFetchConfig: {
                        apiName: "upDate",
                        params: {}, //可为func
                        otherParams: {} //可为func
                    }
                },
                form: {
                    label: "培训地点",
                    type: "string",
                    placeholder: "请输入"
                }
            },
            {
                table: {
                    title: "培训学时",
                    dataIndex: "limitTime",
                    key: "limitTime",
                    // align: "right",
                    // defaultSortOrder: "descend", //默认排序  'ascend' | 'descend'
                    // sorter: function (a,b) {
                    //     return a.educationPeriod - b.educationPeriod
                    // }, //排序规则
                    filter: true,
                    tdEdit: true,
                    fieldsConfig: {
                        type: "string",
                        placeholder: "请输入..."
                    },
                    tdEditFetchConfig: {
                        apiName: "upDate",
                        params: {}, //可为func
                        otherParams: {} //可为func
                    }
                },
                form: {
                    label: "培训学时",
                    type: "string",
                    placeholder: "请输入"
                }
            }
        ]
    }
};
