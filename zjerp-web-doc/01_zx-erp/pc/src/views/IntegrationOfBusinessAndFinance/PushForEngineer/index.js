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
        rowKey: 'id',
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
                        apiName: 'getZxSaProjectSettleAuditList',
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
                        //修改保存按钮隐藏
                        hideForEdit: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index !== '0') return true;
                            return false
                        },
                        //修改保存按钮回调
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
                        fetchForPush: (obj) => {
                            confirm({
                                content: '确定推送选中的数据吗?',
                                onOk: () => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    data[0].isSend = '2'
                                    this.props.myFetch('pushZxSaProjectSettleAuditToLc', data[0])
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
                                tableField: "PushForEngineer"
                            }
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'comName',
                                key: 'comName',
                                fixed: 'left',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '结算单号',
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
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true,
                                fieldsConfig: { type: 'string' },
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '结算期次',
                                dataIndex: 'periodTimeWasted',
                                key: 'periodTimeWasted',
                                width: 120,
                                format: 'YYYY-MM'
                            }
                        },
                        {
                            table: {
                                title: '合同类型',
                                dataIndex: 'contrType',
                                key: 'contrType',
                                width: 120,
                                render: () => {
                                    return '设备租赁'
                                }
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '合同乙方',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 180,
                            }
                        },
                        {
                            table: {
                                title: '含税合同金额(元)',
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
                                title: '本期应付金额(元)',
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
                                title: '开累应付金额(元)',
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
                                title: '本期结算金额(元)',
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
                        //         title: '本期结算不含税金额(元)',
                        //         dataIndex: 'thisAmtNoTax',
                        //         key: 'thisAmtNoTax',
                        //         width: 150,
                        //     }
                        // },
                        {
                            table: {
                                title: '本期调差后不含税金额(元)',
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
                        //         title: '本期结算税额(元)',
                        //         dataIndex: 'thisAmtTax',
                        //         key: 'thisAmtTax',
                        //         width: 150,
                        //     }
                        // },
                        {
                            table: {
                                title: '开累结算金额(元)',
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
                                title: '结算金额差值',
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
                                title: '税额差值',
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
                                title: '调差后累计结算金额',
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
                                title: '本期调差后税额',
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
                                title: '本期调差后结算金额',
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
                                title: '项目',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '计税方法',
                                dataIndex: 'taxCountWay',
                                key: 'taxCountWay',
                                width: 150,
                                render: (h) => {
                                    if (h === '1') {
                                        return '一般计税方法'
                                    } else if (2) {
                                        return '简易计税方法'
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '评审状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核完成';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '-1') {
                                        return '未审核';
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '推送时间',
                                dataIndex: 'sendDate',
                                key: 'sendDate',
                                width: 120,
                                format: "YYYY-MM-DD"
                            }
                        },
                        {
                            table: {
                                title: '财务审批状态说明',
                                dataIndex: 'cwStatusRemark',
                                key: 'cwStatusRemark',
                                width: 150,
                            }
                        },
                        {
                            table: {
                                title: '推送状态',
                                dataIndex: 'isSend',
                                key: 'isSend',
                                width: 100,
                                render: (data) => {
                                    if (data === '0') {
                                        return '待推送';
                                    } else if (data === '1') {
                                        return '信息未完善';
                                    } else if (data === '2') {
                                        return '已推送';
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '财务审批状态',
                                dataIndex: 'cwStatus',
                                key: 'cwStatus',
                                fixed: 'right',
                                width: 100,
                                render: (h, obj2) => {
                                    return <a onClick={() => {
                                        this.setState({
                                            showModel: true,
                                            opdjnm: obj2.id
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
                            title: "结算信息",
                            content: {
                                formConfig: [
                                    {
                                        field: 'id',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'contractAmt',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'taxRate',
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
                                                    业务信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '结算单编号',
                                        field: "billNo",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '项目名称',
                                        field: "orgName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '结算期次',
                                        field: "periodTimeWasted",
                                        type: 'month',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '合同编号',
                                        field: "contractNo",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '合同名称',
                                        field: "contractName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true,
                                    },
                                    {
                                        label: '合同乙方',
                                        field: "secondName",
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '统一社会信用代码',
                                        field: "orgCertificate",
                                        type: 'string',
                                        disabled: true,
                                        span: 12
                                    },
                                    {
                                        label: '含税合同金额(元)',
                                        field: "changeAmt",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '本期结算金额(元)',
                                        field: "thisAmt",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '本期调差后结算金额(元)',
                                        field: "bqtchjsje",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '结算金额差值(元)',
                                        field: "tcje",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '本期结算不含税金额(元)',
                                        field: "thisAmtNoTax",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '本期调差后不含税金额(元)',
                                        field: "bqtchjsnotax",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '本期结算税额(元)',
                                        field: "thisAmtTax",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '本期调差后税额(元)',
                                        field: "bqtchse",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '税额差值(元)',
                                        field: "tcse",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '开累结算金额(元)',
                                        field: "totalAmt",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '调差后累计结算金额(元)',
                                        field: "tchljjsje",
                                        type: 'number',
                                        disabled: true,
                                        precision:2,
                                        span: 12
                                    },
                                    {
                                        label: '截至到上期累计金额(元)',
                                        field: "upAmt",
                                        type: 'number',
                                        disabled: true,
                                        span: 12,
                                        precision:2,
                                        initialValue: (obj) => {
                                            return Number((obj.clickCb.rowData.totalAmt - obj.clickCb.rowData.thisAmt).toFixed(2))
                                        }
                                    },
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            let drawerData = obj.clickCb.rowData
                                            let contractID = drawerData.contractID
                                            this.props.myFetch('getZxGcsgCtContractDetails', { ctContractId: contractID })
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
                                                            secondCode: data?.secondIDCode,
                                                            zjgcxmXmmc: data?.zjgcxmXmmc,
                                                            setDir: 'W',
                                                            secondIDCodeName: data?.secondIDCodeName,
                                                            secondIDCode: data?.secondIDCode,
                                                            changeAmt: drawerData.changeAmt ? Number((drawerData.changeAmt * 10000).toFixed(2)) : drawerData.contractAmt ? Number((drawerData.contractAmt * 10000).toFixed(2)) : 0
                                                        })
                                                    }
                                                })
                                            this.props.myFetch('getZxSaEquipSettleAuditAmtAndTax', { zxSaEquipSettleAuditId: drawerData.id })//接口未替换
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
                                                    财务信息
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '结算期限开始时间',
                                        field: "beginDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '结算期限结束时间',
                                        field: "endDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '计量确认时间',
                                        field: "confirmDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '是否签认',
                                        field: "isSign",
                                        type: 'radio',
                                        optionData: [
                                            { label: '是', value: '1' },
                                            { label: '否', value: '0' }
                                        ],
                                        initialValue: '1',
                                        span: 12,
                                    },
                                    {
                                        label: '摘要',
                                        field: "summary",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '附件张数',
                                        field: "numOfSheets",
                                        type: 'number',
                                        span: 12,
                                        required: true,
                                    },
                                    {
                                        label: '财务合同编号',
                                        field: "zjxmhtbBh",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '财务合同名称',
                                        field: "zjxmhtbMc",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '计税方法',
                                        field: "taxCountWay",
                                        type: 'select',
                                        span: 12,
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '一般计税方法', value: '1' },
                                            { label: '简易计税方法', value: '2' }
                                        ],
                                    },
                                    {
                                        label: '核算单位编号',
                                        field: "accountUnitCode",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '核算单位名称',
                                        field: "accountUnitName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '币种',
                                        field: "currency",
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [{
                                            label: '人民币',
                                            value: '1',
                                        }],
                                        initialValue: '1',
                                        span: 12,
                                    },
                                    {
                                        label: '核算部门编号',
                                        field: "accountDepCode",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '核算部门名称',
                                        field: "accountDepName",
                                        type: 'string',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '汇率',
                                        field: "exchangeRate",
                                        type: 'number',
                                        span: 12,
                                        disabled: true
                                    },
                                    {
                                        label: '债权人编号',
                                        field: "secondCode",
                                        type: 'string',
                                        span: 12,
                                    },
                                    {
                                        label: '财务项目',
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
                                                    其他
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '录入日期',
                                        field: "reportDate",
                                        span: 12,
                                        type: 'date',
                                        required: true,
                                    },
                                    {
                                        label: '填报人',
                                        field: "reportPerson",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                        placeholder: '请输入',
                                    },
                                    {
                                        label: '计算人',
                                        field: "countPerson",
                                        type: 'string',
                                        span: 12,
                                        required: true,
                                        placeholder: '请输入',
                                    },
                                    {
                                        label: '创建时间',
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
                            title: "发票信息",
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
                            title: "数据校验",
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
                            title: "结算项",
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
                            title: "负计量",
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
                            title: "成本科目",
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
                    title="财务审批状态"
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
                                    title: '审批人',
                                    dataIndex: 'opclr',
                                    key: 'opclr',
                                    width: 120,
                                }
                            },
                            {
                                table: {
                                    title: '财务审批状态',
                                    dataIndex: 'oplcjd',
                                    key: 'oplcjd',
                                    width: 120,
                                }
                            },
                            {
                                table: {
                                    title: '财务审批原因',
                                    dataIndex: 'opspyj',
                                    key: 'opspyj',
                                    width: 180,
                                }
                            },
                            {
                                table: {
                                    title: '时间',
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