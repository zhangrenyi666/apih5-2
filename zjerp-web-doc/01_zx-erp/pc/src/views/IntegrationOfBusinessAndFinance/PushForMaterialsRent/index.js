import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { Modal, message as Msg, } from 'antd';
import TabThreeList from './TabThree';
import TabTwoList from './TabTwo';
import TabFourList from './TabFour';
import TabFiveList from './TabFive';
import TabSixList from './TabSix';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxCtSuppliesLeaseSettlementListId',
        size: 'small'
    },
    drawerConfig: {
        width: '80%'
    },
    paginationConfig: {
        position: 'bottom'
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
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            showModel: false,
            opdjnm: null,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
        }
    }
    handleCancelBjdh = () => {
        this.setState({ showModel: false });
    }
    onRef = (ref) => {
        this.child = ref
    }
    render() {
        const { departmentId, showModel, opdjnm } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxCtSuppliesLeaseSettlementListListByOrgId',
                        otherParams: { orgID: departmentId, apih5FlowStatus: '2' }
                    }}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.refresh();
                            obj.btnCallbackFn.clearSelectedRows()
                        }
                    }}
                    tabsWillChange={(obj, canChange) => {
                        if (this.child?.table) {
                            this.child.table.refresh();
                        }
                        canChange(true);
                    }}
                    method={{
                        //????????????????????????
                        hideForEdit: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== '0') return true;
                            return false
                        },
                        //????????????????????????
                        editSuccessCb: (obj) => {
                            if (obj.response.success) {
                                this.table.setTabsIndex('1');
                            }
                        },
                        disabledForPush: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data?.[0].isSend === '0') return false
                            return true
                        },
                        saveOtherParams: (obj) => {
                            return { contractAmt: obj.qnnTableInstance.getDeawerValuesSync().contractAmt / 10000 }
                        },
                        fetchForPush: (obj) => {
                            confirm({
                                content: '???????????????????????????????',
                                onOk: () => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    data[0].isSend = '2'
                                    this.props.myFetch('pushSpZxSaEquipSettleAuditToLc', data[0])
                                        .then(({ success, message }) => {
                                            if (success) {
                                                Msg.success(message)
                                                this.table.refresh()
                                                this.table.clearSelectedRows()
                                            } else {
                                                Msg.error(message)
                                            }
                                        })
                                }
                            })
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "PushForMaterialsRent"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'comName',
                                key: 'comName',
                                fixed: 'left',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'billNo',
                                key: 'billNo',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                fixed: 'left',
                                width: 180,
                                onClick: 'detail',
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                width: 120,
                                format: 'YYYY-MM'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contrType',
                                key: 'contrType',
                                width: 120,
                                render: () => {
                                    return '????????????'
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '??????????????????(???)',
                                dataIndex: 'changeAmt',
                                key: 'changeAmt',
                                width: 150,
                                render: (h, obj) => {
                                    if (h) {
                                        return h * 10000
                                    } else if (obj.contractAmt) {
                                        return obj.contractAmt * 10000
                                    }
                                    return 0
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????????????????(???)',
                                dataIndex: 'thisPayAmt',
                                key: 'thisPayAmt',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????????????????(???)',
                                dataIndex: 'totalPayAmt',
                                key: 'totalPayAmt',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????????????????(???)',
                                dataIndex: 'thisAmt',
                                key: 'thisAmt',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        // {
                        //     table: {
                        //         title: '???????????????????????????(???)',
                        //         dataIndex: 'thisAmtNoTax',
                        //         key: 'thisAmtNoTax',
                        //         width: 150,
                        //     }
                        // },
                        {
                            table: {
                                title: '??????????????????????????????(???)',
                                dataIndex: 'bqtchjsnotax',
                                key: 'bqtchjsnotax',
                                width: 200,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        // {
                        //     table: {
                        //         title: '??????????????????(???)',
                        //         dataIndex: 'thisAmtTax',
                        //         key: 'thisAmtTax',
                        //         width: 150,
                        //     }
                        // },
                        {
                            table: {
                                title: '??????????????????(???)',
                                dataIndex: 'totalAmt',
                                key: 'totalAmt',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'tcje',
                                key: 'tcje',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'tcse',
                                key: 'tcse',
                                width: 120,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                dataIndex: 'tchljjsje',
                                key: 'tchljjsje',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'bqtchse',
                                key: 'bqtchse',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '???????????????????????????',
                                dataIndex: 'bqtchjsje',
                                key: 'bqtchjsje',
                                width: 150,
                                render: (h) => {
                                    if (h) {
                                        return h.toFixed(2)
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'taxCountWay',
                                key: 'taxCountWay',
                                width: 150,
                                render: (h) => {
                                    if (h === '1') {
                                        return '??????????????????'
                                    } else if (2) {
                                        return '??????????????????'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else if (data === '1') {
                                        return '?????????';
                                    } else if (data === '2') {
                                        return '????????????';
                                    } else if (data === '3') {
                                        return '??????';
                                    } else if (data === '-1') {
                                        return '?????????';
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'sendDate',
                                key: 'sendDate',
                                width: 120,
                                format: "YYYY-MM-DD"
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'cwStatusRemark',
                                key: 'cwStatusRemark',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'isSend',
                                key: 'isSend',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else if (data === '1') {
                                        return '???????????????';
                                    } else if (data === '2') {
                                        return '?????????';
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'cwStatus',
                                key: 'cwStatus',
                                fixed: 'right',
                                width: 100,
                                render: (h, obj2) => {
                                    return <a onClick={() => {
                                        this.setState({
                                            showModel: true,
                                            opdjnm: obj2.zxCtSuppliesLeaseSettlementListId
                                        })
                                    }}> {h}</a>
                                }
                            }
                        },
                    ]}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        field: 'zxCtSuppliesLeaseSettlementListId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'taxRate',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'contractAmt',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'secondIDCode',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'secondIDCodeName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'setDir',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'contractID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        type: "component",
                                        field: "component1",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ????????????
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '???????????????',
                                        field: "billNo",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '????????????',
                                        field: "orgName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "periodDate",
                                        type: 'month',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractNo",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "secondName",
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '????????????????????????',
                                        field: "orgCertificate",
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????(???)',
                                        field: "changeAmt",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????(???)',
                                        field: "thisAmt",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '???????????????????????????(???)',
                                        field: "bqtchjsje",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????(???)',
                                        field: "tcje",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '???????????????????????????(???)',
                                        field: "thisAmtNoTax",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????????????????(???)',
                                        field: "bqtchjsnotax",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????(???)',
                                        field: "thisAmtTax",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '?????????????????????(???)',
                                        field: "bqtchse",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '????????????(???)',
                                        field: "tcse",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????(???)',
                                        field: "totalAmt",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '???????????????????????????(???)',
                                        field: "tchljjsje",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '???????????????????????????(???)',
                                        field: "upAmt",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12,
                                        initialValue: (obj) => {
                                            return Number((obj.clickCb.rowData.totalAmt - obj.clickCb.rowData.thisAmt).toFixed(2))
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            let drawerData = obj.clickCb.rowData
                                            let zxCtSuppliesContractId = drawerData.contractID
                                            let zxCtSuppliesLeaseSettlementListId = drawerData.zxCtSuppliesLeaseSettlementListId
                                            this.props.myFetch('getZxCtSuppliesContractDetail', { zxCtSuppliesContractId })
                                                .then(({ data, success }) => {
                                                    if (success) {
                                                        obj.form.setFieldsValue({
                                                            zjxmhtbBh: data?.zjxmhtbBh,
                                                            zjxmhtbMc: data?.zjxmhtbMc,
                                                            taxCountWay: data?.taxCountWay,
                                                            accountUnitCode: data?.accountUnitCode,
                                                            accountUnitName: data?.accountUnitName,
                                                            accountDepCode: data?.accountDepCode,
                                                            accountDepName: data?.accountDepName,
                                                            exchangeRate: 1,
                                                            taxRate: data?.taxRate,
                                                            orgCertificate: data?.orgCertificate,
                                                            secondCode: data?.orgCertificate,
                                                            zjgcxmXmmc: data?.zjgcxmXmmc,
                                                            setDir: 'W',
                                                            secondIDCodeName: data?.secondIDCodeName,
                                                            secondIDCode: data?.secondIDCode,
                                                            changeAmt: drawerData.changeAmt ? Number((drawerData.changeAmt * 10000).toFixed(2)) : drawerData.contractAmt ? Number((drawerData.contractAmt * 10000).toFixed(2)) : 0
                                                        })
                                                    }
                                                })
                                            this.props.myFetch('getliesLeaseSettlementThisAmtAndTax', { zxCtSuppliesLeaseSettlementListId })
                                                .then(({ data, success }) => {
                                                    if (success) {
                                                        obj.form.setFieldsValue({
                                                            thisAmtNoTax: data?.thisAmtNoTax,
                                                            thisAmtTax: data?.thisAmtTax,
                                                        })
                                                        if (!drawerData.bqtchjsnotax && drawerData.bqtchjsnotax !== 0) {
                                                            obj.form.setFieldsValue({
                                                                bqtchjsnotax: data?.thisAmtNoTax,
                                                            })
                                                        }
                                                        if (!drawerData.bqtchse && drawerData.bqtchse !== 0) {
                                                            obj.form.setFieldsValue({
                                                                bqtchse: data?.thisAmtTax,
                                                            })
                                                        }
                                                    }
                                                })
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ????????????
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '????????????????????????',
                                        field: "beginDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '????????????????????????',
                                        field: "endDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "confirmDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "isSign",
                                        type: 'radio',
                                        optionData: [
                                            { label: '???', value: '1' },
                                            { label: '???', value: '0' }
                                        ],
                                        initialValue: '1',
                                        span: 12,
                                    },
                                    {
                                        label: '??????',
                                        field: "summary",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '????????????',
                                        field: "numOfSheets",
                                        type: 'number',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "zjxmhtbBh",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "zjxmhtbMc",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '????????????',
                                        field: "taxCountWay",
                                        type: 'select',
                                        span: 12,
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '??????????????????', value: '1' },
                                            { label: '??????????????????', value: '2' }
                                        ],
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "accountUnitCode",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "accountUnitName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '??????',
                                        field: "currency",
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [{
                                            label: '?????????',
                                            value: '1',
                                        }],
                                        initialValue: '1',
                                        span: 12,
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "accountDepCode",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "accountDepName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '??????',
                                        field: "exchangeRate",
                                        type: 'number',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '???????????????',
                                        field: "secondCode",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '????????????',
                                        field: "zjgcxmXmmc",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ??????
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: "reportDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '?????????',
                                        field: "reportPerson",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                        placeholder: '?????????',
                                    },
                                    {
                                        label: '?????????',
                                        field: "countPerson",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                        placeholder: '?????????',
                                    },
                                    {
                                        label: '????????????',
                                        field: "createTime",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                ]
                            }
                        },
                        {
                            name: "diyComponent",
                            field: "TabTwoList",
                            title: "????????????",
                            content: obj => {
                                let params = { ...this.props }
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.TabOneRef = this.table
                                return <TabTwoList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent2",
                            field: "TabThreeList",
                            title: "????????????",
                            content: obj => {
                                let params = { ...this.props }
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.TabOneRef = this.table
                                return <TabThreeList {...params} onRef={this.onRef} />;
                            }
                        },
                        {
                            name: "diyComponent3",
                            field: "TabFourList",
                            title: "?????????",
                            content: obj => {
                                let params = { ...this.props }
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                return <TabFourList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent4",
                            field: "TabFiveList",
                            title: "?????????",
                            content: obj => {
                                let params = { ...this.props }
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                return <TabFiveList {...params} />;
                            }
                        },
                        {
                            name: "diyComponent5",
                            field: "TabSixList",
                            title: "????????????",
                            content: obj => {
                                let params = { ...this.props }
                                params.baseData = obj.tableFns.qnnForm.form.getFieldsValue()
                                params.clickName = obj.clickCb.rowInfo.name
                                params.tabIndex = obj.state.tabsIndex
                                return <TabSixList {...params} />;
                            }
                        },
                    ]}
                />
                <Modal
                    width='700px'
                    style={{ top: '0' }}
                    title="??????????????????"
                    visible={showModel}
                    footer={null}
                    onCancel={this.handleCancelBjdh}
                    bodyStyle={{ width: '700px', height: '300px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelBjdh}
                    wrapClassName={'modals'}
                >
                    <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        antd={{
                            rowKey: 'opid',
                            size: 'small'
                        }}
                        fetchConfig={{
                            apiName: 'getZxSaFiOplcjdList',
                            otherParams: () => {
                                return {
                                    opdjnm
                                }
                            }
                        }}
                        formConfig={[
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'opclr',
                                    key: 'opclr',
                                    width: 120,
                                }
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    dataIndex: 'oplcjd',
                                    key: 'oplcjd',
                                    width: 120,
                                }
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    dataIndex: 'opspyj',
                                    key: 'opspyj',
                                    width: 180,
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'opDate',
                                    key: 'opDate',
                                    width: 120,
                                    format: 'YYYY-MM-DD'
                                }
                            },
                        ]}
                    />
                </Modal>
            </div>
        )
    }
}

export default index;