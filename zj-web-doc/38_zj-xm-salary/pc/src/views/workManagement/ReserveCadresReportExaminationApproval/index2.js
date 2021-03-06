import React from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from "antd";
import FlowFormByMaterialManagementContract from './form';
import PersonInfo from '../comp/PersonInfo'
import Apih5 from "qnn-apih5"

const fourItemLayout = {
    labelCol: {
        sm: { span: 8 }
    },
    wrapperCol: {
        sm: { span: 16 }
    }
}
const config = {
    antd: {
        rowKey: 'zjXmSalaryEmployApprovalId',
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
            modalShowStatus: false,
            projectId: ''
        }
    }

    positionData = null
    render() {
        const { companyId, companyName, projectId, projectName, departmentName, departmentId } = this.apih5.getUserInfo('curCompany')
        const { realName } = this.apih5.getUserInfo()
        const orgId = this.apih5.getOrgId()

        return (
            <div>
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
                        apiName: 'getZjXmSalaryEmployApprovalList',
                        otherParams: {
                            topId: orgId,
                            approvalFlag: "reserveCadre"
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
                                                        const { data, success, message } = await this.props.myFetch('addZjXmSalaryEmployApproval', {
                                                            ...obj._formData,
                                                            companyId: companyId || null,
                                                            companyName: companyName || null,
                                                            projectId: projectId || null,
                                                            projectName: projectName || null,
                                                            departmentName: departmentName || null,
                                                            departmentId: departmentId || null,
                                                            topId: orgId,
                                                            approvalFlag: 'reserveCadre'
                                                        })

                                                        if (success) {
                                                            Msg.success('????????????!')
                                                            this.setState({
                                                                primaryKey: data.zjXmSalaryEmployApprovalId
                                                            }, () => {
                                                                obj.btnCallbackFn.refresh();
                                                                obj.btnCallbackFn.setActiveKey('1');
                                                            })
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    },
                                                    hide: (obj) => {
                                                        let index = obj.btnCallbackFn.getActiveKey();
                                                        if (index === "1") {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
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
                        //                 apiName: 'updateZjXmSalaryEmployApproval',
                        //                 otherParams: {
                        //                     approvalFlag: "salary"
                        //                 }
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
                                if (flowData && flowData.contentLength > 0) {
                                    return <FlowFormByMaterialManagementContract  {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
                                } else {
                                    return <div style={{ fontSize: '20px', padding: '10px' }}>?????????????????????!</div>
                                }
                            }
                        },
                        {
                            name: 'del',//??????add del
                            icon: 'delete',//icon
                            type: 'danger',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            fetchConfig: {//ajax??????
                                apiName: 'batchDeleteApproval',
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
                                field: 'zjXmSalaryEmployApprovalId',
                                type: 'string',
                                hide: true,
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
                                        primaryKey: obj.rowData.zjXmSalaryEmployApprovalId
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
                                                primaryKey: obj.rowData.zjXmSalaryEmployApprovalId
                                            }, () => {
                                                obj.btnCallbackFn.btnAction({
                                                    btnConfig: {
                                                        name: "edit",
                                                        drawerTitle: ""
                                                    },
                                                    attrBindInfo: {
                                                        rowData: {
                                                            ...obj.rowData
                                                        }
                                                    }
                                                })
                                            })
                                        },
                                        disabled: (args) => {
                                            return args.rowData.apih5FlowStatus === '1' || args.rowData.apih5FlowStatus === '2'
                                        },
                                        formBtns: [
                                            {
                                                name: 'cancel', //??????????????????
                                                type: 'dashed',//??????  ?????? primary
                                                label: '??????',
                                            },
                                            {
                                                name: 'submit',//??????add del
                                                type: 'primary',//??????  ?????? primary
                                                label: '??????',//???????????????????????????????????? 
                                                fetchConfig: {//ajax??????
                                                    apiName: 'updateZjXmSalaryEmployApproval',
                                                    otherParams: (obj) => {
                                                        return {
                                                            approvalFlag: "reserveCadre",
                                                            zjXmSalaryEmployApprovalId: this.state.primaryKey
                                                        }
                                                    }
                                                },
                                                //??????tab?????????????????????,???????????????
                                                hide: (obj) => {
                                                    let index = obj.btnCallbackFn.getActiveKey();
                                                    if (index === "1") {
                                                        return true;
                                                    } else {
                                                        return false;
                                                    }
                                                },
                                            }
                                        ]
                                    },
                                ]
                            }
                        },
                    ]}
                    onTabsChange={(tabKey, qnnFormInstance, arg) => {
                        if (tabKey === '0') {
                            this.props.myFetch('getBaseCodeTree', { itemId: 'gangWeiGuanLi' }).then(({ data, success, message }) => {
                                if (success) {
                                    this.positionData = data
                                }
                            })
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "????????????",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: {
                                formConfig: [
                                    {
                                        label: '??????',
                                        field: 'titleName',
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????'
                                    },
                                    {
                                        label: '????????????',
                                        type: 'string',
                                        field: 'applyForUnitName',
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: departmentName,
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
                                        label: '????????????',
                                        type: 'string',
                                        field: 'applyForUnitCode',
                                        initialValue: departmentId,
                                        hide: true
                                    },
                                    {
                                        label: '?????????',
                                        field: 'applyForUser',
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
                                        label: "??????",
                                        field: "fileList",
                                        type: "files",
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: window.configs.domain + "upload"
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "tableqd",
                            name: "qnnTable",
                            title: "??????",
                            disabled: function (obj) {
                                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCrJYearCreditEvaId"))
                            },
                            content: {
                                wrappedComponentRef: (me) => {
                                    this.buttomTable = me
                                },
                                antd: {
                                    rowKey: 'zjXmSalaryEmployApprovalId'
                                },
                                fetchConfig: {
                                    apiName: 'getZjXmSalaryUserExtensionHistoryList',
                                    otherParams: () => {
                                        return {
                                            zjXmSalaryEmployApprovalId: this.state.primaryKey
                                        }
                                    }
                                },
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '??????id',
                                            field: 'id',
                                            hide: true
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '??????id',
                                            field: 'extensionHistoryId',
                                            hide: true
                                        }
                                    },
                                    {
                                        table: {
                                            title: 'No.',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            fixed: 'left',
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'realName',
                                            key: 'realName',
                                            width: 100,
                                            align: "center",
                                            onClick: (obj) => {
                                                this.setState({
                                                    extensionHistoryId: obj.rowData.extensionHistoryId
                                                }, () => {
                                                    this.setState({
                                                        modalShowStatus: 'detail',
                                                    })
                                                })
                                            }
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '?????????',
                                        },
                                    },
                                    {
                                        table: {
                                            title: '????????????/??????',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            width: 100,
                                            align: "center",
                                        },
                                        isInForm: false
                                    },
                                    {
                                        form: {
                                            // orgId
                                            type: "string",
                                            field: 'orgId',
                                            hide: true
                                        },
                                        isInTable: false
                                    },

                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'position',
                                            key: 'position',
                                            width: 380,
                                            align: "center",
                                            render: (val, rowData) => {
                                                let val1 = ''
                                                // '??????/ ??????/ ??????'
                                                // projectName  departmentName  position
                                                let arrList = []
                                                let TemporaryData = this.positionData

                                                const positionArr = val.split(',')
                                                const outerLoopFunc = (aouterData, targetData, i) => {
                                                    targetData.map(item => {
                                                        if (aouterData === item.itemId) {
                                                            arrList[i] = item.itemName
                                                            TemporaryData = item.children
                                                        }
                                                        return true
                                                    })
                                                }

                                                if (positionArr.length) {
                                                    // ??????position
                                                    for (let i = 0; i < positionArr.length; i++) {
                                                        outerLoopFunc(positionArr[i], TemporaryData, i)
                                                    }

                                                    if (rowData.orgName && rowData.departmentName && arrList.join(' / ')) {
                                                        val1 = `${rowData.orgName} / ${rowData.departmentName} / ${arrList.join(' / ')}`
                                                    } else {
                                                        val1 = ''
                                                    }

                                                } else {
                                                    // ????????????????????????????????????????????????0???name????????????
                                                    if (val) {
                                                        const filterData = this.positionData.filter(ele => ele.itemId === rowData.position)
                                                        const positionName = filterData.length ? filterData[0].itemName : false
                                                        if (positionName) {
                                                            if (rowData.orgName && rowData.departmentName) {
                                                                val1 = `${rowData.orgName} / ${rowData.departmentName} / ${positionName} `
                                                            }
                                                        } else {
                                                            val1 = `${rowData.orgName} / ${rowData.departmentName} `
                                                        }
                                                    } else {
                                                        if (rowData.orgName && rowData.departmentName) {
                                                            val1 = `${rowData.orgName} / ${rowData.departmentName} `
                                                        } else {
                                                            val1 = ''
                                                        }
                                                    }
                                                }
                                                return <div>{val1}</div>
                                            }
                                        },
                                        isInForm: false
                                    },
                                    // positionAfter
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'positionAfter',
                                            key: 'positionAfter',
                                            width: 380,
                                            align: "center",
                                            type: "cascader",
                                        },
                                        form: {
                                            field: "positionAfter", //???????????????key
                                            type: "cascader",
                                            required: true,
                                            fetchConfig: {
                                                apiName: "getBaseCodeTree",
                                                otherParams: {
                                                    itemId: 'tuijiangangwei'
                                                }
                                            },
                                            disabled: true,
                                            optionConfig: {//??????????????????
                                                label: 'itemName', //?????? label
                                                value: 'itemId',//
                                                children: 'children'
                                            },
                                            span: 12,
                                            formItemLayout: fourItemLayout,
                                        },
                                    },
                                    // {
                                    //     table: {
                                    //         title: '??????',
                                    //         dataIndex: '11',
                                    //         key: '11',
                                    //         width: 100,

                                    //     },
                                    //     form: {
                                    //         type: 'string',
                                    //         placeholder: '?????????',
                                    //     },
                                    // },
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
                                                    name: "diyEdit", // ??????name??????add,  del, edit, detail, Component, form???
                                                    label: "??????",
                                                    onClick: (obj) => {
                                                        this.setState({
                                                            extensionHistoryId: obj.rowData.extensionHistoryId
                                                        }, () => {
                                                            this.setState({
                                                                modalShowStatus: 'edit',
                                                            })
                                                        })
                                                    },
                                                },
                                            ]
                                        }
                                    }
                                ],
                                actionBtns: [
                                    {
                                        name: "diyAdd",
                                        icon: "plus",
                                        type: "primary",
                                        label: "?????????",
                                        onClick: async (obj) => {
                                            this.setState({
                                                modalShowStatus: 'add',
                                                projectId: ''
                                            })
                                        }
                                    },
                                    {
                                        name: 'del',//??????add del
                                        icon: 'delete',//icon
                                        type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                        label: '??????',
                                        fetchConfig: {//ajax??????
                                            apiName: 'batchDeleteZjXmSalaryUserExtensionHistory',
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                />
                <PersonInfo
                    propsData={this.props}
                    modalShowStatus={this.state.modalShowStatus}
                    projectId={this.state.projectId}
                    extensionHistoryId={this.state.extensionHistoryId}
                    primaryKey={this.state.primaryKey}
                    type={'reserveCadre'}
                    closeCb={(val) => {
                        this.setState({
                            modalShowStatus: val
                        }, () => {
                            if (val === 'saveSuccess') {
                                this.buttomTable.refresh()
                            }
                        })

                    }}
                    tabsDataFunc={(data) => {
                        console.log(data)
                    }}
                />
            </div>
        );
    }
}

export default index;