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
                                    name: 'addDiy',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    onClick: (obj) => {
                                        if (projectId) {
                                            obj.qnnTableInstance.btnAction({
                                                btnConfig: {
                                                    name: "add",
                                                    drawerTitle: "",
                                                    formBtns: [
                                                        {
                                                            name: 'cancel', //??????????????????
                                                            type: 'dashed',//??????  ?????? primary
                                                            label: '??????',
                                                        },
                                                        {
                                                            name: 'diySubmit',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '??????',//???????????????????????????????????? 
                                                            onClick: async (obj) => {
                                                                switch (obj.qnnTableInstance.getTabsIndex()) {
                                                                    case '0': // ?????????
                                                                        this.props.myFetch('addZjXmSalaryOvermanApproval', obj._formData).then(({ data, success, message }) => {
                                                                            if (success) {
                                                                                Msg.success('????????????!')
                                                                                this.setState({
                                                                                    primaryKey: data.zjXmSalaryOvermanApprovalId
                                                                                })
                                                                                obj.qnnTableInstance.closeDrawer()
                                                                            } else {
                                                                                Msg.error(message)
                                                                            }
                                                                        })
                                                                        break
                                                                    // case '1': // ??????
                                                                    //     this.props.myFetch('batchZjXmSalaryOvermanSub', {
                                                                    //         zjXmSalaryOvermanApprovalId: this.state.primaryKey,
                                                                    //         subList: this.buttomTable.getTableData()
                                                                    //     }).then(({ data, success, message }) => {
                                                                    //         if (success) {
                                                                    //             Msg.success('????????????!')
                                                                    //         } else {
                                                                    //             Msg.error(message)
                                                                    //         }
                                                                    //     })
                                                                    //     break
                                                                    default:
                                                                        break
                                                                }
                                                            },
                                                            // fetchConfig: {//ajax??????
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
                                            Msg.warning('???????????????????????????????????????????????????!')
                                        }
                                    },

                                },
                                // {
                                //     name: 'edit',//??????add del
                                //     icon: 'edit',//icon
                                //     type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                //     label: '??????',
                                //     formBtns: [
                                //         {
                                //             name: 'cancel', //??????????????????
                                //             type: 'dashed',//??????  ?????? primary
                                //             label: '??????',
                                //         },
                                //         {
                                //             name: 'submit',//??????add del
                                //             type: 'primary',//??????  ?????? primary
                                //             label: '??????',//???????????????????????????????????? 
                                //             fetchConfig: {//ajax??????
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
                                    label: '????????????',
                                    drawerTitle: '????????????',
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
                                        //     return <div style={{ fontSize: '20px', padding: '10px' }}>?????????????????????!</div>
                                        // }
                                    }
                                },
                                {
                                    name: 'Component',
                                    type: 'primary',
                                    label: '(??????)????????????',
                                    drawerTitle: '(??????)????????????',
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
                                        //     return <div style={{ fontSize: '20px', padding: '10px' }}>?????????????????????!</div>
                                        // }

                                        return <div style={{ fontSize: '20px', padding: '10px' }}>????????????</div>
                                    }
                                },
                                {
                                    name: 'del',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    fetchConfig: {//ajax??????
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
                                        title: '??????',
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
                                        title: '????????????',
                                        dataIndex: 'declareTime',
                                        key: 'declareTime',
                                        width: 100,
                                        format: 'YYYY-MM-DD'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',
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
                                        title: '??????????????????',
                                        dataIndex: 'approvalLocation',
                                        key: 'approvalLocation',
                                        width: 120
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'apih5FlowStatus',
                                        key: 'apih5FlowStatus',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        field: "apih5FlowStatus",
                                        type: "select",
                                        placeholder: "?????????...",
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
                                        title: '????????????',
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
                                        showType: "tile", //??????????????? bubble????????????  tile???????????? ??????bubble  ???0.6.15??????????????????????????????table????????????????????????table?????????
                                        width: 110,
                                        title: "??????",
                                        key: "action", //???????????????
                                        fixed: "right", //???????????????
                                        align: "center",
                                        btns: [
                                            {
                                                name: "editDiy", // ??????name??????add,  del, edit, detail, Component, form???
                                                label: "??????",
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
                                                                    name: 'cancel', //??????????????????
                                                                    type: 'dashed',//??????  ?????? primary
                                                                    label: '??????',
                                                                },
                                                                {
                                                                    name: 'submitDiy',//??????add del
                                                                    type: 'primary',//??????  ?????? primary
                                                                    label: '??????',//???????????????????????????????????? 
                                                                    onClick: async (obj) => {
                                                                        switch (obj.qnnTableInstance.getTabsIndex()) {
                                                                            case '0': // ?????????
                                                                                this.props.myFetch('updateZjXmSalaryOvermanApproval', obj._formData).then(({ data, success, message }) => {
                                                                                    if (success) {
                                                                                        Msg.success('????????????!')
                                                                                    } else {
                                                                                        Msg.error(message)
                                                                                    }
                                                                                })
                                                                                break
                                                                            // case '1': // ??????
                                                                            //     this.props.myFetch('batchZjXmSalaryOvermanSub', {
                                                                            //         zjXmSalaryOvermanApprovalId: obj._formData.zjXmSalaryOvermanApprovalId,
                                                                            //         subList: this.buttomTable.getTableData()
                                                                            //     }).then(({ data, success, message }) => {
                                                                            //         if (success) {
                                                                            //             Msg.success('????????????!')
                                                                            //         } else {
                                                                            //             Msg.error(message)
                                                                            //         }
                                                                            //     })
                                                                            //     break
                                                                            default:
                                                                                break
                                                                        }
                                                                    }
                                                                    // fetchConfig: {//ajax??????
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
                                    title: "????????????",
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
                                                label: '??????',
                                                field: 'titleName',
                                                type: 'string',
                                                required: true,
                                                placeholder: '?????????'
                                            },
                                            {
                                                label: '????????????',
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
                                                placeholder: '?????????'
                                            },
                                            {
                                                label: '??????ID',
                                                type: 'string',
                                                field: 'departmentId',
                                                initialValue: departmentId,
                                                hide: true
                                            },
                                            {
                                                label: '????????????',
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
                                                placeholder: '?????????'
                                            },
                                            {
                                                label: '????????????code',
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
                                                label: '?????????',
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
                                                placeholder: '?????????'
                                            },
                                            {
                                                type: 'select',
                                                label: '????????????',
                                                field: 'proType',
                                                placeholder: '?????????',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proType : null,
                                                optionConfig: {
                                                    label: 'itemName', //?????? label
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
                                                label: '??????????????????',
                                                type: 'string',
                                                field: 'proTypeName',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proTypeName : null,
                                                hide: true
                                            },
                                            {
                                                type: 'select',
                                                label: '????????????',
                                                field: 'proLevel',
                                                placeholder: '?????????',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevel : null,
                                                optionConfig: {
                                                    label: 'itemName', //?????? label
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
                                                label: '??????????????????',
                                                type: 'string',
                                                field: 'proLevelName',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.proLevelName : null,
                                                hide: true
                                            },
                                            {
                                                label: '???????????????',
                                                field: 'supplementNum',
                                                type: 'number',
                                                placeholder: '?????????',
                                                initialValue: this.state.greyContainerData ? this.state.greyContainerData.supplementNum : null,
                                                span: 24,
                                                required: true,
                                                disabled: true,
                                            },
                                            {
                                                type: 'textarea',
                                                label: '???????????????8???',
                                                field: 'standardPost',
                                                placeholder: '?????????',
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
                                                label: '???????????????9???',
                                                field: 'actualPost',
                                                placeholder: '?????????',
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
                                                label: "??????",
                                                field: "psot",
                                                type: "select",
                                                placeholder: "?????????...",
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
                                                label: '????????????',
                                                type: 'number',
                                                field: 'num',
                                                placeholder: '?????????',
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
                                                label: '????????????',
                                                field: 'overmanReason',
                                                placeholder: '?????????',
                                                span: 24,
                                                required: true,
                                            },
                                            {
                                                label: "??????",
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
                                //     title: "??????",
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
                                //                     label: '??????id',
                                //                     field: 'zjXmSalaryOvermanSubId',
                                //                     hide: true
                                //                 }
                                //             },
                                //             {
                                //                 isInTable: false,
                                //                 form: {
                                //                     label: '??????id',
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
                                //                     title: '??????',
                                //                     dataIndex: 'psot',
                                //                     key: 'psot',
                                //                     width: 100,
                                //                     type: 'select',
                                //                     tdEdit: true
                                //                 },
                                //                 form: {
                                //                     field: "psot",
                                //                     type: "select",
                                //                     placeholder: "?????????...",
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
                                //                     title: '????????????',
                                //                     dataIndex: 'num',
                                //                     key: 'num',
                                //                     width: 100,
                                //                     tdEdit: true
                                //                 },
                                //                 form: {
                                //                     type: 'number',
                                //                     field: 'num',
                                //                     placeholder: '?????????',
                                //                 },
                                //             },
                                //             {
                                //                 table: {
                                //                     title: '??????',
                                //                     dataIndex: 'remarks',
                                //                     key: 'remarks',
                                //                     width: 100,
                                //                     tdEdit: true
                                //                 },
                                //                 form: {
                                //                     type: 'string',
                                //                     field: 'remarks',
                                //                     placeholder: '?????????',
                                //                 },
                                //             },
                                //         ],
                                //         actionBtns: [
                                //             {
                                //                 name: "addRow",
                                //                 icon: "plus",
                                //                 type: "primary",
                                //                 label: "?????????",
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
                                //                 label: '??????',
                                //                 fetchConfig: {//ajax??????
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