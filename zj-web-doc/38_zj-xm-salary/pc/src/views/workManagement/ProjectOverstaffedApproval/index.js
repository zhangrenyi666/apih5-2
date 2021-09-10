import React from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from "antd";
import FlowFormByMaterialManagementContract from './form';
import Apih5 from "qnn-apih5"


const config = {
    antd: {
        rowKey: 'zjXmSalaryOvermanApprovalId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
};


class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {
            primaryKey: '',
            modalShowStatus: false,
            greyContainerData: null,
            noProject: false,
        }
        this.noProject = false
        const { projectId } = this.apih5.getUserInfo('curCompany')
        if (projectId) {
            this.props.myFetch('getSysProjectDeptConfByOverman', { projectId }).then(({ data, success, message }) => {
                if (success) {
                    let p1 = new Promise((resolve, reject) => {
                        this.props.myFetch('getBaseCodeSelect', { itemId: 'gongChengLeiXing' }).then(({ data, success }) => {
                            resolve(data)
                        })
                    })

                    let p2 = new Promise((resolve, reject) => {
                        this.props.myFetch('getBaseCodeSelect', { itemId: 'xiangMuJiBie' }).then(({ data, success }) => {
                            resolve(data)
                        })
                    })

                    Promise.all([p1, p2]).then((result) => {
                        this.setState({
                            greyContainerData: {
                                ...data,
                                proTypeName: result[0].map(item => {
                                    if (item.itemId === data.proType) {
                                        return item.itemName
                                    } else {
                                        return ''
                                    }
                                }).join(''),
                                proLevelName: result[1].map(item => {
                                    if (item.itemId === data.proLevel) {
                                        return item.itemName
                                    } else {
                                        return ''
                                    }
                                }).join(''),
                            }
                        })
                    })

                }
            })
        } else {
            this.noProject = true
        }

    }

    getBaseCodeNameFunc = () => {

    }
    addAterPrimaryKey = ''
    render() {

        const { projectId, projectName, departmentName, departmentId } = this.apih5.getUserInfo('curCompany')
        const { realName } = this.apih5.getUserInfo()
        const orgId = this.apih5.getOrgId()
        return (
            <div>
                {

                    this.state.greyContainerData || this.noProject ?
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryOvermanApprovalList',
                                otherParams: {
                                    topId: orgId
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'addDiy',//内置add del
                                    icon: 'plus',//icon
                                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                                    label: '新增',
                                    onClick: (obj) => {
                                        if (projectId) {
                                            obj.qnnTableInstance.btnAction({
                                                btnConfig: {
                                                    name: "add",
                                                    drawerTitle: "",
                                                    formBtns: [
                                                        {
                                                            name: 'cancel', //关闭右边抽屉
                                                            type: 'dashed',//类型  默认 primary
                                                            label: '取消',
                                                        },
                                                        {
                                                            name: 'diySubmit',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '保存',//提交数据并且关闭右边抽屉 
                                                            onClick: async (obj) => {
                                                                switch (obj.qnnTableInstance.getTabsIndex()) {
                                                                    case '0': // 第一页
                                                                        this.props.myFetch('addZjXmSalaryOvermanApproval', obj._formData).then(({ data, success, message }) => {
                                                                            if (success) {
                                                                                Msg.success('保存成功!')
                                                                                this.setState({
                                                                                    primaryKey: data.zjXmSalaryOvermanApprovalId
                                                                                })
                                                                                obj.qnnTableInstance.closeDrawer()
                                                                            } else {
                                                                                Msg.error(message)
                                                                            }
                                                                        })
                                                                        break
                                                                    // case '1': // 明细
                                                                    //     this.props.myFetch('batchZjXmSalaryOvermanSub', {
                                                                    //         zjXmSalaryOvermanApprovalId: this.state.primaryKey,
                                                                    //         subList: this.buttomTable.getTableData()
                                                                    //     }).then(({ data, success, message }) => {
                                                                    //         if (success) {
                                                                    //             Msg.success('保存成功!')
                                                                    //         } else {
                                                                    //             Msg.error(message)
                                                                    //         }
                                                                    //     })
                                                                    //     break
                                                                    default:
                                                                        break
                                                                }
                                                            },
                                                            // fetchConfig: {//ajax配置
                                                            //     apiName: 'addZjXmSalaryOvermanApproval',
                                                            // }
                                                        }
                                                    ]

                                                },
                                                attrBindInfo: {
                                                    rowData: {
                                                        ...obj.rowData
                                                    }
                                                }
                                            })
                                        } else {
                                            Msg.warning('请将右上角的当前项目切换为项目类型!')
                                        }
                                    },

                                },
                                // {
                                //     name: 'edit',//内置add del
                                //     icon: 'edit',//icon
                                //     type: 'primary',//类型  默认 primary  [primary dashed danger]
                                //     label: '修改',
                                //     formBtns: [
                                //         {
                                //             name: 'cancel', //关闭右边抽屉
                                //             type: 'dashed',//类型  默认 primary
                                //             label: '取消',
                                //         },
                                //         {
                                //             name: 'submit',//内置add del
                                //             type: 'primary',//类型  默认 primary
                                //             label: '保存',//提交数据并且关闭右边抽屉 
                                //             fetchConfig: {//ajax配置
                                //                 apiName: '',
                                //             },
                                //             onClick: (obj) => {
                                //                 obj.btnCallbackFn.clearSelectedRows();
                                //             }
                                //         }
                                //     ]
                                // },
                                {
                                    name: 'Component',
                                    type: 'primary',
                                    label: '超员审批',
                                    drawerTitle: '超员审批',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && !data[0].workId) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    Component: (props) => {
                                        let flowData = props?.btnCallbackFn?.getSelectedRows?.()?.[0];
                                        return <FlowFormByMaterialManagementContract  {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
                                        // if (flowData && flowData.contentLength > 0) {
                                        //     return <FlowFormByMaterialManagementContract  {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
                                        // } else {
                                        //     return <div style={{ fontSize: '20px', padding: '10px' }}>请填写详细数据!</div>
                                        // }
                                    }
                                },
                                {
                                    name: 'Component',
                                    type: 'primary',
                                    label: '(司机)超员审批',
                                    drawerTitle: '(司机)超员审批',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && !data[0].workId) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    Component: (props) => {
                                        // let flowData = props?.btnCallbackFn?.getSelectedRows?.()?.[0];

                                        // if (flowData && flowData.contentLength > 0) {
                                        //     return <FlowFormByMaterialManagementContract  {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
                                        // } else {
                                        //     return <div style={{ fontSize: '20px', padding: '10px' }}>请填写详细数据!</div>
                                        // }

                                        return <div style={{ fontSize: '20px', padding: '10px' }}>等待确定</div>
                                    }
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteZjXmSalaryOvermanApproval',
                                    }
                                }
                            ]}
                            rowSelection={{
                                type: 'check',
                                getCheckboxProps: record => ({
                                    // name:record.name,
                                    disabled: record.apih5FlowStatus === '1' || record.apih5FlowStatus === '2',
                                }),
                            }}
                            drawerShowToggle={(args) => {
                                if (!args.drawerIsShow) {
                                    this.table.refresh()
                                }
                            }}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zjXmSalaryOvermanApprovalId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'workId',
                                        hide: true
                                    }
                                },
                                {
                                    table: {
                                        title: '标题',
                                        dataIndex: 'titleName',
                                        key: 'titleName',
                                        filter: true,
                                        width: 200,
                                        onClick: (obj, rowData) => {
                                            this.setState({
                                                primaryKey: obj.rowData.zjXmSalaryOvermanApprovalId
                                            }, () => {
                                                obj.btnCallbackFn.btnAction({
                                                    btnConfig: {
                                                        name: "detail",
                                                        drawerTitle: ""
                                                    },
                                                    attrBindInfo: {
                                                        rowData: {
                                                            ...obj.rowData
                                                        }
                                                    }
                                                })
                                            })
                                        }
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '申报时间',
                                        dataIndex: 'declareTime',
                                        key: 'declareTime',
                                        width: 100,
                                        format: 'YYYY-MM-DD'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '报审单位',
                                        dataIndex: 'applyForUnitName',
                                        key: 'applyForUnitName',
                                        width: 100
                                    },
                                    isInForm: false
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'companyCode',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '当前审批位置',
                                        dataIndex: 'approvalLocation',
                                        key: 'approvalLocation',
                                        width: 120
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '审批状态',
                                        dataIndex: 'apih5FlowStatus',
                                        key: 'apih5FlowStatus',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        field: "apih5FlowStatus",
                                        type: "select",
                                        placeholder: "请选择...",
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'liuChengZhuangTai'
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId',
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '审批时间',
                                        dataIndex: 'approvalTime',
                                        key: 'approvalTime',
                                        width: 100,
                                        format: 'YYYY-MM-DD'
                                    },
                                    isInForm: false
                                },
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
                                                name: "editDiy", // 内置name有【add,  del, edit, detail, Component, form】
                                                label: "修改",
                                                onClick: (obj) => {
                                                    this.setState({
                                                        primaryKey: obj.rowData.zjXmSalaryOvermanApprovalId
                                                    })
                                                    obj.qnnTableInstance.btnAction({
                                                        btnConfig: {
                                                            name: "edit",
                                                            drawerTitle: "",
                                                            formBtns: [
                                                                {
                                                                    name: 'cancel', //关闭右边抽屉
                                                                    type: 'dashed',//类型  默认 primary
                                                                    label: '取消',
                                                                },
                                                                {
                                                                    name: 'submitDiy',//内置add del
                                                                    type: 'primary',//类型  默认 primary
                                                                    label: '保存',//提交数据并且关闭右边抽屉 
                                                                    onClick: async (obj) => {
                                                                        switch (obj.qnnTableInstance.getTabsIndex()) {
                                                                            case '0': // 第一页
                                                                                this.props.myFetch('updateZjXmSalaryOvermanApproval', obj._formData).then(({ data, success, message }) => {
                                                                                    if (success) {
                                                                                        Msg.success('保存成功!')
                                                                                    } else {
                                                                                        Msg.error(message)
                                                                                    }
                                                                                })
                                                                                break
                                                                            // case '1': // 明细
                                                                            //     this.props.myFetch('batchZjXmSalaryOvermanSub', {
                                                                            //         zjXmSalaryOvermanApprovalId: obj._formData.zjXmSalaryOvermanApprovalId,
                                                                            //         subList: this.buttomTable.getTableData()
                                                                            //     }).then(({ data, success, message }) => {
                                                                            //         if (success) {
                                                                            //             Msg.success('保存成功!')
                                                                            //         } else {
                                                                            //             Msg.error(message)
                                                                            //         }
                                                                            //     })
                                                                            //     break
                                                                            default:
                                                                                break
                                                                        }
                                                                    }
                                                                    // fetchConfig: {//ajax配置
                                                                    //     apiName: 'updateZjXmSalaryOvermanApproval',
                                                                    // }
                                                                }
                                                            ]

                                                        },
                                                        attrBindInfo: {
                                                            rowData: {
                                                                ...obj.rowData
                                                            }
                                                        }
                                                    })
                                                },
                                                disabled: (args) => {
                                                    return args.rowData.apih5FlowStatus === '1' || args.rowData.apih5FlowStatus === '2'
                                                },
                                            },
                                        ]
                                    }
                                },
                            ]}
                            // onTabsChange={async (tabKey, qnnFormInstance, arg) => {
                            //     if (tabKey === '0') {
                            //         const { data, success, message } = await this.props.myFetch('getSysProjectDeptConfByOverman', { projectId })
                            //         if (success) {
                            //             console.log(qnnFormInstance)
                            //             const formData = arg.qnnTableInstance.getQnnForm().form.getFieldsValue()
                            //             arg.qnnTableInstance.getQnnForm().form.setFieldsValue({ ...formData, ...data })
                            //         }
                            //     }
                            // }}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    disabled: (obj) => {
                                        return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                                    },
                                    content: {
                                        fetchConfig: (obj) => {
                                            return {
                                                apiName: 'getZjXmSalaryOvermanApprovalDetail',
                                                otherParams: {
                                                    zjXmSalaryOvermanApprovalId: obj._formData().zjXmSalaryOvermanApprovalId
                                                }
                                            }
                                        },
                                        formConfig: [
                                            {
                                                field: 'zjXmSalaryOvermanApprovalId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                label: '标题',
                                                field: 'titleName',
                                                type: 'string',
                                                required: true,
                                                placeholder: '请选择'
                                            },
                                            {
                                                label: '部门名称',
                                                field: 'departmentName',
                                                type: 'string',
                                                span: 12,
                                                required: true,
                                                disabled: true,
                                                initialValue: departmentName,
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
                                                placeholder: '请输入'
                                            },
                                            {
                                                label: '部门ID',
                                                type: 'string',
                                                field: 'departmentId',
                                                initialValue: departmentId,
                                                hide: true
                                            },
                                            {
                                                label: '报审单位',
                                                type: 'string',
                                                field: 'applyForUnitName',
                                                addDisabled: true,
                                                editDisabled: true,
                                                initialValue: projectName,
                                                span: 12,
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
                                                placeholder: '请输入'
                                            },
                                            {
                                                label: '报审单位code',
                                                type: 'string',
                                                field: 'companyCode',
                                                initialValue: projectId,
                                                hide: true
                                            },
                                            {
                                                label: 'projectId',
                                                type: 'string',
                                                field: 'projectId',
                                                initialValue: projectId,
                                                hide: true
                                            },
                                            {
                                                label: 'projectName',
                                                type: 'string',
                                                field: 'projectName',
                                                initialValue: projectName,
                                                hide: true
                                            },
                                            {
                                                label: '报审人',
                                                field: 'realName',
                                                type: 'string',
                                                addDisabled: true,
                                                editDisabled: true,
                                                initialValue: realName,
                                                span: 12,
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
                                                placeholder: '请输入'
                                            },
                                            {
                                                type: 'select',
                                                label: '工程类型',
                                                field: 'proType',
                                                placeholder: '请选择',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proType : null,
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId',
                                                    linkageFields: {
                                                        'proTypeName': 'itemName',
                                                    }
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'gongChengLeiXing'
                                                    }
                                                },
                                                pageConfig: { limit: 20 },
                                                span: 12,
                                                required: true,
                                                disabled: true,
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
                                            {
                                                label: '工程类型名称',
                                                type: 'string',
                                                field: 'proTypeName',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proTypeName : null,
                                                hide: true
                                            },
                                            {
                                                type: 'select',
                                                label: '项目级别',
                                                field: 'proLevel',
                                                placeholder: '请选择',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevel : null,
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId',
                                                    linkageFields: {
                                                        'proLevelName': 'itemName',
                                                    }
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xiangMuJiBie'
                                                    }
                                                },
                                                pageConfig: { limit: 20 },
                                                span: 12,
                                                required: true,
                                                disabled: true,
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
                                            {
                                                label: '项目级别名称',
                                                type: 'string',
                                                field: 'proLevelName',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevelName : null,
                                                hide: true
                                            },
                                            {
                                                label: '已超员人数',
                                                field: 'supplementNum',
                                                type: 'number',
                                                placeholder: '请输入',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.supplementNum : null,
                                                span: 24,
                                                required: true,
                                                disabled: true,
                                            },
                                            {
                                                type: 'textarea',
                                                label: '标准岗位（8）',
                                                field: 'standardPost',
                                                placeholder: '请输入',
                                                required: false,
                                                span: 12,
                                                // required: true,
                                                disabled: true,
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
                                            {
                                                type: 'textarea',
                                                label: '实际岗位（9）',
                                                field: 'actualPost',
                                                placeholder: '请输入',
                                                required: false,
                                                span: 12,
                                                // required: true,
                                                disabled: true,
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
                                            {
                                                label: "岗位",
                                                field: "psot",
                                                type: "select",
                                                placeholder: "请选择...",
                                                fetchConfig: {
                                                    apiName: "getSysProjectDeptConfSelect",
                                                    otherParams: {
                                                        projectId: orgId
                                                    }
                                                },
                                                optionConfig: {
                                                    label: 'jobTypeName',
                                                    value: 'jobType',
                                                },
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
                                                onChange: (val, obj) => {
                                                    this.table.setDeawerValues({
                                                        postName: obj.children
                                                    })
                                                },
                                                span: 12
                                            },
                                            {
                                                field: 'postName',
                                                hide: true
                                            },
                                            {
                                                label: '人数添加',
                                                type: 'number',
                                                field: 'num',
                                                placeholder: '请输入',
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
                                                span: 12
                                            },
                                            {
                                                type: 'textarea',
                                                label: '超员原因',
                                                field: 'overmanReason',
                                                placeholder: '请输入',
                                                span: 24,
                                                required: true,
                                            },
                                            {
                                                label: "附件",
                                                field: "fileList",
                                                type: "files",
                                                initialValue: [],
                                                fetchConfig: {
                                                    apiName: "upload"
                                                }
                                            }
                                        ]
                                    }
                                },
                                // {
                                //     field: "tableqd",
                                //     name: "qnnTable",
                                //     title: "明细",
                                //     disabled: function (obj) {
                                //         return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCrJYearCreditEvaId"))
                                //     },
                                //     content: {
                                //         wrappedComponentRef: (me) => {
                                //             this.buttomTable = me
                                //         },
                                //         antd: {
                                //             rowKey: 'zjXmSalaryOvermanSubId'
                                //         },
                                //         fetchConfig: {
                                //             apiName: 'getZjXmSalaryOvermanSubList',
                                //             otherParams: () => {
                                //                 return {
                                //                     zjXmSalaryOvermanApprovalId: this.state.primaryKey
                                //                 }
                                //             }
                                //         },
                                //         formConfig: [
                                //             {
                                //                 isInTable: false,
                                //                 form: {
                                //                     label: '主键id',
                                //                     field: 'zjXmSalaryOvermanSubId',
                                //                     hide: true
                                //                 }
                                //             },
                                //             {
                                //                 isInTable: false,
                                //                 form: {
                                //                     label: '主键id',
                                //                     field: 'zjXmSalaryOvermanApprovalId',
                                //                     hide: true
                                //                 }
                                //             },
                                //             {
                                //                 table: {
                                //                     title: 'No.',
                                //                     dataIndex: 'index',
                                //                     key: 'index',
                                //                     width: 50,
                                //                     fixed: 'left',
                                //                     render: (data, rowData, index) => {
                                //                         return index + 1;
                                //                     }
                                //                 },
                                //                 isInForm: false
                                //             },
                                //             {
                                //                 table: {
                                //                     title: '岗位',
                                //                     dataIndex: 'psot',
                                //                     key: 'psot',
                                //                     width: 100,
                                //                     type: 'select',
                                //                     tdEdit: true
                                //                 },
                                //                 form: {
                                //                     field: "psot",
                                //                     type: "select",
                                //                     placeholder: "请选择...",
                                //                     fetchConfig: {
                                //                         apiName: "getSysProjectDeptConfSelect",
                                //                         otherParams: {
                                //                             projectId: orgId
                                //                         }
                                //                     },
                                //                     optionConfig: {
                                //                         label: 'jobTypeName',
                                //                         value: 'jobType',
                                //                     },
                                //                 },
                                //             },
                                //             {
                                //                 table: {
                                //                     title: '人数添加',
                                //                     dataIndex: 'num',
                                //                     key: 'num',
                                //                     width: 100,
                                //                     tdEdit: true
                                //                 },
                                //                 form: {
                                //                     type: 'number',
                                //                     field: 'num',
                                //                     placeholder: '请输入',
                                //                 },
                                //             },
                                //             {
                                //                 table: {
                                //                     title: '备注',
                                //                     dataIndex: 'remarks',
                                //                     key: 'remarks',
                                //                     width: 100,
                                //                     tdEdit: true
                                //                 },
                                //                 form: {
                                //                     type: 'string',
                                //                     field: 'remarks',
                                //                     placeholder: '请输入',
                                //                 },
                                //             },
                                //         ],
                                //         actionBtns: [
                                //             {
                                //                 name: "addRow",
                                //                 icon: "plus",
                                //                 type: "primary",
                                //                 label: "新增行",
                                //                 // onClick: async (obj) => {
                                //                 //     this.setState({
                                //                 //         modalShowStatus: 'add'
                                //                 //     })
                                //                 // }
                                //             },
                                //             {
                                //                 name: 'del',
                                //                 icon: 'delete',
                                //                 type: 'danger',
                                //                 label: '删除',
                                //                 fetchConfig: {//ajax配置
                                //                     apiName: 'batchDeleteZjXmSalaryOvermanSub',
                                //                 },
                                //             }
                                //         ]
                                //     }
                                // }
                            ]}
                        /> : null
                }

            </div>
        );
    }
}

export default index;