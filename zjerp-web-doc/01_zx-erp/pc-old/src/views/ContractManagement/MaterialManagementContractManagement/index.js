import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Modal, Button, Row, Col, message as Msg, Drawer, Tabs } from 'antd';
import s from './style.less';
import moment from 'moment';
import TabTwoCGList from '../../SettlementManagement/MaterialPurchaseSettlement/tabTwo';
import TabTwoZLList from '../../SettlementManagement/MaterialLeasingSettlement/tabTwo';
import TabThreeCGList from '../../SettlementManagement/MaterialPurchaseSettlement/tabThree';
import TabThreeZLList from '../../SettlementManagement/MaterialLeasingSettlement/tabThree';
import Operation from './operation';
const confirm = Modal.confirm;
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: function (row) {
            return row.zxCtSuppliesContractId
        },
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
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            flowData: null,
            visible: false,
            activeKey: '1'
        }
    }
    onClose = () => {
        this.setState({
            visible: false,
        });
    };
    tabsCallback = (activeKey) => {
        this.setState({ activeKey })
    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { flowData, visible, activeKey } = this.state;
        return (
            <div className={s.root}>
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
                        apiName: 'getZxCtSuppliesContractList',
                        otherParams: {
                            pp9: '1',
                            orgID: departmentId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'edit',//??????add del
                            icon: 'edit',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            drawerTitle: '??????',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            formBtns: [
                                {
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                },
                                {
                                    name: 'submit',
                                    field: 'diySubmit',
                                    type: 'primary',
                                    label: '??????',
                                    hide: (obj) => {
                                        let index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'updateZxCtSuppliesContractByContId'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',//??????add del
                            icon: 'delete',//icon
                            type: 'danger',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            fetchConfig: {//ajax??????
                                apiName: 'batchDeleteUpdateZxCtSuppliesContract',
                            },
                        },
                        {
                            label: "?????????/??????",
                            name: "resumptionOfExecution",
                            type: "primary",
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '???????????????????????????????',
                                    onOk: () => {
                                        var selectedRows = obj.selectedRows;
                                        selectedRows[0].contractStatus = selectedRows[0].contractStatus === '1' ? '3' : '1';
                                        const { fetch, msg } = obj.btnCallbackFn;
                                        fetch('updateZxCtSuppliesContract', ...selectedRows, ({ data, success, message }) => {
                                            if (success) {
                                                msg.success(message)
                                                obj.btnCallbackFn.refresh();
                                                obj.btnCallbackFn.clearSelectedRows();
                                            } else {
                                                msg.error(message);
                                            }
                                        })
                                    }
                                })
                            }
                        },
                    ]}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.clearSelectedRows();
                            obj.btnCallbackFn.refresh();
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "????????????",
                            content: {
                                formConfig: [
                                    {
                                        field: 'workId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'zxCtSuppliesContractId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'beginPer',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'resCategoryName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'comID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'comName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgID',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'orgName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'pp9',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        type: 'component',
                                        field: 'component1',
                                        span: 24,
                                        Component: obj => {
                                            console.log(obj);
                                            return (
                                                <Row>
                                                    <Col span={1}></Col>
                                                    <Col span={23}>
                                                        <div style={{ height: '56px', lineHeight: '56px' }}>
                                                            <Button type="primary" onClick={() => {
                                                                this.props.myFetch('openFlowByReport', { apiName: 'getZxCtSuppliesContrApplyFlowDetail', apiType: 'POST', flowId: 'ZxCtSuppliesContrApply', workId: obj.initialValues.workId }).then(
                                                                    ({ success, message, data }) => {
                                                                        if (success) {
                                                                            this.setState({
                                                                                activeKey:'1',
                                                                                flowData: data,
                                                                                visible: true
                                                                            })
                                                                        } else {
                                                                            Msg.error(message)
                                                                        }
                                                                    }
                                                                );
                                                            }}>????????????????????????</Button>
                                                        </div>
                                                    </Col>
                                                </Row>
                                            );
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "signatureDate",
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractName",
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "firstName",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "secondName",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????',
                                        field: "subject",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "projectManager",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "secondPrincipal",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "firstUnitTel",
                                        type: 'phone',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "code7",
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'category_contr_type_CL'
                                            }
                                        },
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "materialSource",
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'category_contract_materialSource'
                                            }
                                        },
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "paymentAssumpsit",
                                        type: 'string',
                                        placeholder: '?????????',
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 5 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 19 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "secondUnitTel",
                                        type: 'specialPlane',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "agent",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '????????????',
                                                value: '0'
                                            },
                                            {
                                                label: '?????????',
                                                value: '1'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????????????????',
                                        field: "contractCost",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '?????????????????????????????????',
                                        field: "contractCostNoTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????????????????',
                                        field: "contractCostTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '???????????????????????????????????????',
                                        field: "alterContractSum",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????????????????????????????',
                                        field: "alterContractSumNoTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '?????????????????????????????????',
                                        field: "alterContractSumTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "isDeduct",
                                        type: 'checkbox',
                                        disabled: true,
                                        optionData: [
                                            {
                                                value: '1'
                                            }
                                        ],
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "transferAssumpsit",
                                        type: 'string',
                                        placeholder: '?????????',
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 5 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 19 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: "otherAssumpsit",
                                        type: 'string',
                                        placeholder: '?????????',
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 5 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 19 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "pp1",
                                        type: 'phone',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '?????????????????????',
                                        field: "secondPrincipalIDCard",
                                        type: 'identity',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "pp2",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "planStartDate",
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "planEndDate",
                                        type: 'date',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '?????????????????????',
                                        field: "csTimeLimit",
                                        type: 'string',
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "apih5FlowStatus",
                                        disabled: true,
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '?????????',
                                                value: ''
                                            },
                                            {
                                                label: '?????????',
                                                value: '1'
                                            },
                                            {
                                                label: '?????????',
                                                value: '2'
                                            },
                                            {
                                                label: '??????',
                                                value: '3'
                                            }
                                        ],
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "contractStatus",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId'
                                        },
                                        // fetchConfig: {
                                        //     apiName: 'getBaseCodeSelect',
                                        //     otherParams: {
                                        //         itemId: 'heTongZhuangTai'
                                        //     }
                                        // },
                                        optionData: [
                                            {
                                                itemName: '??????',
                                                itemId: '3'
                                            },
                                            {
                                                itemName: '?????????',
                                                itemId: '1'
                                            }
                                        ],
                                        required: true,
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "settleType",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '?????????????????????',
                                                value: '0'
                                            },
                                            {
                                                label: '???????????????',
                                                value: '1'
                                            },
                                            {
                                                label: '??????????????????',
                                                value: '2'
                                            },
                                            {
                                                label: '???????????????',
                                                value: '3'
                                            }
                                        ],
                                        disabled: true,
                                        required: true,
                                        placeholder: '?????????',
                                        span: 12
                                    },
                                    {
                                        label: '????????????',
                                        field: "content",
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 5 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 19 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        label: '??????',
                                        field: "remarks",
                                        type: 'string',
                                        placeholder: '?????????',
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 5 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 19 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                    {
                                        type: 'files',
                                        label: '??????',
                                        field: 'suppliesContractFileList',
                                        fetchConfig: {
                                            apiName: 'upload'
                                        },
                                        required: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 5 },
                                                sm: { span: 5 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 19 },
                                                sm: { span: 19 }
                                            }
                                        }
                                    },
                                ]
                            }
                        },
                        {
                            field: "form2",
                            name: "qnnForm",
                            title: "??????????????????",
                            content: {
                                formConfig: [
                                    {
                                        label: "?????????????????????(??????)",
                                        field: "contractCostNoTax",
                                        type: 'number',
                                        disabled: true,
                                        span: 12,
                                        precision: 6,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 8 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            }
                                        }
                                    },
                                    {
                                        label: "??????????????????(??????)",
                                        field: "contractCost",
                                        type: 'number',
                                        disabled: true,
                                        span: 12,
                                        precision: 6,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 8 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            }
                                        }
                                    },
                                    {
                                        label: "????????????????????????(??????)",
                                        field: "alterContractSumNoTax",
                                        type: 'number',
                                        disabled: true,
                                        span: 12,
                                        precision: 6,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 8 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            }
                                        }
                                    },
                                    {
                                        label: "?????????????????????(??????)",
                                        field: "alterContractSum",
                                        type: 'number',
                                        disabled: true,
                                        span: 12,
                                        precision: 6,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 8 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 12 },
                                                sm: { span: 12 }
                                            }
                                        }
                                    },
                                    {
                                        type: 'qnnTable',
                                        field: 'bondList',
                                        qnnTableConfig: {
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 8 },
                                                    sm: { span: 8 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 12 },
                                                    sm: { span: 12 }
                                                }
                                            },
                                            fetchConfig: () => {
                                                if (this.table?.form?.getFieldValue?.('zxCtSuppliesContractId')) {
                                                    if (this.table?.form?.getFieldValue?.('code7') === 'WL') {
                                                        return {
                                                            apiName: 'getZxCtSuppliesContrLeaseListList',
                                                            otherParams: {
                                                                contractID: this.table?.form?.getFieldValue?.('zxCtSuppliesContractId')
                                                            }
                                                        }
                                                    } else {
                                                        return {
                                                            apiName: 'getZxCtSuppliesContrShopListList',
                                                            otherParams: {
                                                                contractID: this.table?.form?.getFieldValue?.('zxCtSuppliesContractId')
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    return {};
                                                }
                                            },
                                            antd: {
                                                size: "small",
                                                rowKey: (rowData) => {
                                                    return rowData.applyLeaseListId ? rowData.applyLeaseListId : rowData.applyShopListId;
                                                }
                                            },
                                            drawerConfig: {
                                                width: '1200px'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            isShowRowSelect: false,
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'applyLeaseListId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'applyShopListId',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'pp5',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'workID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'workType',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'workType',
                                                        key: 'workType',
                                                        width: 150,
                                                        fixed: 'left'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'workNo',
                                                        key: 'workNo',
                                                        width: 150,
                                                        fixed: 'left'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 150
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'spec',
                                                        key: 'spec',
                                                        width: 100
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 100
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'isNetPrice',
                                                        key: 'isNetPrice',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '???';
                                                            } else {
                                                                return '???';
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: () => {
                                                        if (this.table?.form?.getFieldValue?.('code7') === 'WL') {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'rentUnit',
                                                        key: 'rentUnit',
                                                        width: 100
                                                    },
                                                },
                                                {
                                                    isInTable: () => {
                                                        if (this.table?.form?.getFieldValue?.('code7') === 'WL') {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'contrTrrm',
                                                        key: 'contrTrrm',
                                                        width: 100
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        width: 100,
                                                        render: (data, rowData) => {
                                                            if (rowData.qty || rowData.qty === 0) {
                                                                return rowData.qty.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'price',
                                                        key: 'price',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.price || rowData.price === 0) {
                                                                return rowData.price.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'contractSum',
                                                        key: 'contractSum',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSum || rowData.contractSum === 0) {
                                                                return rowData.contractSum.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'priceNoTax',
                                                        key: 'priceNoTax',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                                return rowData.priceNoTax.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'contractSumNoTax',
                                                        key: 'contractSumNoTax',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                                return rowData.contractSumNoTax.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????(%)',
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        type: 'select'
                                                    },
                                                    form: {
                                                        type: 'select',
                                                        optionConfig: {
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId'
                                                        },
                                                        fetchConfig: {
                                                            apiName: 'getBaseCodeSelect',
                                                            otherParams: {
                                                                itemId: 'shuiLV'
                                                            }
                                                        },
                                                        placeholder: '?????????'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'isDeduct',
                                                        key: 'isDeduct',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '???';
                                                            } else {
                                                                return '???';
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: () => {
                                                        if (this.table?.form?.getFieldValue?.('code7') === 'WL') {
                                                            return true;
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'alterTrrm',
                                                        key: 'alterTrrm',
                                                        width: 120
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'changeQty',
                                                        key: 'changeQty',
                                                        width: 120,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeQty || rowData.changeQty === 0) {
                                                                return rowData.changeQty.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'changePrice',
                                                        key: 'changePrice',
                                                        width: 150,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePrice || rowData.changePrice === 0) {
                                                                return rowData.changePrice.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        dataIndex: 'changePriceNoTax',
                                                        key: 'changePriceNoTax',
                                                        width: 150,
                                                        render: (data, rowData) => {
                                                            if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                                return rowData.changePriceNoTax.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'changeContractSum',
                                                        key: 'changeContractSum',
                                                        width: 150,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                                return rowData.changeContractSum.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????????????????',
                                                        dataIndex: 'changeContractSumNoTax',
                                                        key: 'changeContractSumNoTax',
                                                        width: 180,
                                                        render: (data, rowData) => {
                                                            if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                                return rowData.changeContractSumNoTax.toFixed(2)
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'changeDate',
                                                        key: 'changeDate',
                                                        width: 100,
                                                        format: 'YYYY-MM-DD'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'remarks',
                                                        key: 'remarks',
                                                        width: 100,
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "table2",
                            name: "qnnTable",
                            title: "????????????",
                            content: {
                                tabs: [],
                                fetchConfig: (obj) => {
                                    return {
                                        apiName: 'getZxCtSuppliesContrReplenishAgreementListByContrId',
                                        otherParams: {
                                            pp6: obj.tableFns.qnnForm.form.getFieldValue('zxCtSuppliesContractId'),
                                            apih5FlowStatus: '2'
                                        }
                                    }
                                },
                                drawerConfig: {
                                    width: '1200px'
                                },
                                antd: {
                                    size: "small",
                                    rowKey: 'replenishAgreementId'
                                },
                                paginationConfig: false,
                                isShowRowSelect: true,
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
                                actionBtns: [
                                    {
                                        name: 'edit',//??????add del
                                        icon: 'edit',//icon
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        label: '??????',
                                        drawerTitle: '??????',
                                        disabled: (obj) => {
                                            let data = obj.btnCallbackFn.getSelectedRows();
                                            if (data.length === 1) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        formBtns: [
                                            {
                                                name: 'cancel', //??????????????????
                                                type: 'dashed',//??????  ?????? primary
                                                label: '??????',
                                                hide: (obj) => {
                                                    let index = obj.btnCallbackFn.getActiveKey();
                                                    if (index === "1") {
                                                        return true;
                                                    } else {
                                                        return false;
                                                    }
                                                },
                                            },
                                            {
                                                name: 'submit',
                                                field: 'diySubmit',
                                                type: 'primary',
                                                label: '??????',
                                                hide: (obj) => {
                                                    let index = obj.btnCallbackFn.getActiveKey();
                                                    if (index === "1") {
                                                        return true;
                                                    } else {
                                                        return false;
                                                    }
                                                },
                                                fetchConfig: {
                                                    apiName: 'updateZxCtSuppliesContrReplenishAgreement'
                                                }
                                            }
                                        ]
                                    }
                                ],
                                tabs: [
                                    {
                                        field: "form1",
                                        name: "qnnForm",
                                        title: "????????????",
                                        content: {
                                            formConfig: [
                                                {
                                                    field: 'replenishAgreementId',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'apih5FlowStatus',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'comID',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'orgID',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'orgName',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'firstName',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'secondName',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'contractName',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'beginPer',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    label: '??????????????????',
                                                    field: "contractNo",
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '??????????????????',
                                                    field: "pp3",
                                                    type: 'string',
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "pp6",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'contractName',
                                                        value: 'zxCtSuppliesContractId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getZxCtSuppliesContractListAddAgreementNum',
                                                        otherParams: {
                                                            pp9: '1',
                                                            orgID: departmentId
                                                        }
                                                    },
                                                    condition: [
                                                        {
                                                            regex: { replenishAgreementId: ['!', /(''|null|undefined)/ig] },
                                                            action: 'disabled',
                                                        }
                                                    ],
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '???????????????',
                                                    field: "agent",
                                                    type: 'string',
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "startDate",
                                                    type: 'date',
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "endDate",
                                                    type: 'date',
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "csTimeLimit",
                                                    type: 'string',
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "contractType",
                                                    type: 'string',
                                                    disabled: true,
                                                    initialValue: '???????????????????????????',
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "firstID",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'orgName',
                                                        value: 'orgID'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getZxCtContractListByStatus',
                                                        // otherParams: {
                                                        //     contrStatus: '1'
                                                        // }
                                                    },
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "secondID",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'customerName',
                                                        value: 'zxCrCustomerNewId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getZxCrCustomerNewList'
                                                    },
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '??????????????????(??????)',
                                                    field: "contractCost",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '?????????????????????(??????)',
                                                    field: "contractCostNoTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????(??????)',
                                                    field: "contractCostTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????????????????(??????)',
                                                    field: "pp4",
                                                    type: 'string',
                                                    disabled: true,
                                                    initialValue: "0",
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '???????????????????????????(??????)',
                                                    field: "pp4NoTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '??????????????????(??????)',
                                                    field: "pp4Tax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '?????????????????????(??????)',
                                                    field: "alterContractSum",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????????????????(??????)',
                                                    field: "alterContractSumNoTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '???????????????(??????)',
                                                    field: "alterContractSumTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "isDeduct",
                                                    type: 'checkbox',
                                                    optionData: [
                                                        {
                                                            value: '1'
                                                        }
                                                    ],
                                                    disabled: true,
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "code7",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'itemName', //?????? label
                                                        value: 'itemId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getBaseCodeSelect',
                                                        otherParams: {
                                                            itemId: 'category_contr_type_CL'
                                                        }
                                                    },
                                                    placeholder: '?????????',
                                                    disabled: true,
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "materialSource",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'itemName', //?????? label
                                                        value: 'itemId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getBaseCodeSelect',
                                                        otherParams: {
                                                            itemId: 'category_contract_materialSource'
                                                        }
                                                    },
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                // {
                                                //     label: '????????????',
                                                //     field: "shopWay",
                                                //     type: 'select',
                                                //     optionConfig: {
                                                //         label: 'label', //?????? label
                                                //         value: 'value'
                                                //     },
                                                //     optionData: [
                                                //         {
                                                //             label: '????????????',
                                                //             value: '0'
                                                //         },
                                                //         {
                                                //             label: '???????????????',
                                                //             value: '1'
                                                //         },
                                                //         {
                                                //             label: '????????????',
                                                //             value: '2'
                                                //         }
                                                //     ],
                                                //     placeholder: '?????????',
                                                //     disabled: true,
                                                //     span: 12
                                                // },
                                                // {
                                                //     label: '????????????',
                                                //     field: "shopNumber",
                                                //     type: 'string',
                                                //     dependencies: ["shopWay"],
                                                //     hide: (obj) => {
                                                //         if (obj.form.getFieldValue('shopWay') !== '0' && obj.form.getFieldValue('shopWay') !== '1') {
                                                //             return true;
                                                //         } else {
                                                //             return false;
                                                //         }
                                                //     },
                                                //     disabled: true,
                                                //     placeholder: '?????????',
                                                //     span: 12
                                                // },
                                                {
                                                    label: '????????????',
                                                    field: 'content',
                                                    type: 'textarea',
                                                    placeholder: '?????????',
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 4 },
                                                            sm: { span: 4 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 20 },
                                                            sm: { span: 20 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '??????',
                                                    field: 'remarks',
                                                    type: 'textarea',
                                                    placeholder: '?????????',
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 4 },
                                                            sm: { span: 4 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 20 },
                                                            sm: { span: 20 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '??????',
                                                    field: 'replenishAgreementFileList',
                                                    type: 'files',
                                                    fetchConfig: {
                                                        apiName: 'upload'
                                                    },
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 4 },
                                                            sm: { span: 4 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 24 }
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    {
                                        field: "form2",
                                        name: "qnnForm",
                                        title: "??????",
                                        disabled: (obj) => {
                                            return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("replenishAgreementId"))
                                        },
                                        content: {
                                            formConfig: [
                                                {
                                                    field: 'zxCtSuppliesContractChangeId',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'tableOneID',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    label: '??????????????????',
                                                    field: "contractNo",
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "replyUnit",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'label',
                                                        value: 'value'
                                                    },
                                                    optionData: [
                                                        {
                                                            label: '??????',
                                                            value: '0'
                                                        },
                                                        {
                                                            label: '??????',
                                                            value: '1'
                                                        },
                                                        {
                                                            label: '???',
                                                            value: '2'
                                                        }
                                                    ],
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "replyDate",
                                                    type: 'date',
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: 'alterContent',
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 4 },
                                                            sm: { span: 4 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 20 },
                                                            sm: { span: 20 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '????????????',
                                                    field: 'alterReason',
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '?????????',
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 4 },
                                                            sm: { span: 4 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 20 },
                                                            sm: { span: 20 }
                                                        }
                                                    }
                                                },
                                                {
                                                    type: "qnnTable",
                                                    field: "replenishShopDetailedList",
                                                    incToForm: true,
                                                    hide: (obj) => {
                                                        let rowData = obj?.tableFns?.qnnForm?.form?.getFieldsValue?.();
                                                        if (rowData && rowData.code7 !== 'WL') {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    },
                                                    qnnTableConfig: {
                                                        antd: {
                                                            size: "small",
                                                            rowKey: 'zxCtSuppliesContrReplenishShopDetailId'
                                                        },
                                                        paginationConfig: false,
                                                        isShowRowSelect: true,
                                                        formConfig: [
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'zxCtSuppliesContrReplenishShopDetailId',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'coBookID',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'pp5',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'workNo',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'workType',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'alterType',
                                                                    key: 'alterType',
                                                                    width: 100,
                                                                    type: 'select',
                                                                    fixed: 'left'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: {
                                                                        label: 'label', //?????? label
                                                                        value: 'value'
                                                                    },
                                                                    optionData: [
                                                                        {
                                                                            label: '??????',
                                                                            value: '1'
                                                                        },
                                                                        {
                                                                            label: '??????',
                                                                            value: '2'
                                                                        }
                                                                    ],
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'workTypeID',
                                                                    key: 'workTypeID',
                                                                    width: 150,
                                                                    fixed: 'left',
                                                                    render: (data, rowData) => {
                                                                        return rowData.workType;
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    optionConfig: {
                                                                        label: 'catName', //?????? label
                                                                        value: 'id'
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: 'getZxSkResCategoryMaterialsList',
                                                                        otherParams: {
                                                                            parentID: '0002'
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'workID',
                                                                    key: 'workID',
                                                                    width: 150,
                                                                    // type: 'select',
                                                                    fixed: 'left',
                                                                    render: (data, rowData) => {
                                                                        return rowData.workNo;
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: (obj) => {
                                                                        if (obj.rowData.alterType === '1') {
                                                                            return {
                                                                                label: 'resCode', //?????? label
                                                                                value: 'id'
                                                                            }
                                                                        } else {
                                                                            return {
                                                                                label: 'workNo', //?????? label
                                                                                value: 'workID'
                                                                            }
                                                                        }
                                                                    },
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType) {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    fetchConfig: (obj) => {
                                                                        if (obj.rowData.alterType === '1') {
                                                                            if (obj.rowData.workTypeID) {
                                                                                return {
                                                                                    apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                                    otherParams: {
                                                                                        id: obj.rowData.workTypeID
                                                                                    }
                                                                                }
                                                                            }
                                                                        } else {
                                                                            return {
                                                                                apiName: 'getZxCtSuppliesContrShopListList',
                                                                                // apiName: 'getZxCtSuppliesContrApplyShopListList',
                                                                                otherParams: {
                                                                                    contractID: this.table.form.getFieldValue('pp6')
                                                                                }
                                                                            }
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'workName',
                                                                    key: 'workName',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'spec',
                                                                    key: 'spec',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'unit',
                                                                    key: 'unit',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'isNetPrice',
                                                                    key: 'isNetPrice',
                                                                    width: 100,
                                                                    tdEdit: true,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '???';
                                                                        } else {
                                                                            return '???';
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'checkbox',
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    optionData: [
                                                                        {
                                                                            value: "1"
                                                                        }
                                                                    ]
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'qty',
                                                                    key: 'qty',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.qty || rowData.qty === 0) {
                                                                            return rowData.qty.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'price',
                                                                    key: 'price',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.price || rowData.price === 0) {
                                                                            return rowData.price.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'contractSum',
                                                                    key: 'contractSum',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.contractSum || rowData.contractSum === 0) {
                                                                            return rowData.contractSum.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'priceNoTax',
                                                                    key: 'priceNoTax',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                                            return rowData.priceNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'contractSumNoTax',
                                                                    key: 'contractSumNoTax',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                                            return rowData.contractSumNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'contractSumTax',
                                                                    key: 'contractSumTax',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                                                                            return rowData.contractSumTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????(%)',
                                                                    dataIndex: 'taxRate',
                                                                    key: 'taxRate',
                                                                    width: 100,
                                                                    type: 'select'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: {
                                                                        label: 'itemName', //?????? label
                                                                        value: 'itemId'
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: 'getBaseCodeSelect',
                                                                        otherParams: {
                                                                            itemId: 'shuiLV'
                                                                        }
                                                                    },
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'isDeduct',
                                                                    key: 'isDeduct',
                                                                    width: 100,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '???';
                                                                        } else {
                                                                            return '???';
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'a',
                                                                    key: 'a',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.a || rowData.a === 0) {
                                                                            return rowData.a.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'changeQty',
                                                                    key: 'changeQty',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeQty || rowData.changeQty === 0) {
                                                                            return rowData.changeQty.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'changePrice',
                                                                    key: 'changePrice',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changePrice || rowData.changePrice === 0) {
                                                                            return rowData.changePrice.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    onBlur: (e, obj, btnCallbackFn) => {
                                                                        let val = e.target.value;
                                                                        setTimeout(async () => {
                                                                            await btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                                                editRowData.changePrice = val ? Number(val) : 0;
                                                                                editRowData.changePriceNoTax = (val ? Number(val) : 0) / ((editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1));
                                                                                editRowData.changeContractSum = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0);
                                                                                editRowData.changeContractSumNoTax = ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                                editRowData.changeContractSumTax = (editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0) - ((editRowData.changeQty ? editRowData.changeQty : 0) * (val ? Number(val) : 0)) / (editRowData.taxRate ? (1 + Number(editRowData.taxRate) / 100) : 1);
                                                                                btnCallbackFn.setEditedRowData({ ...editRowData });
                                                                            })
                                                                        }, 0)
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????????????????',
                                                                    dataIndex: 'changePriceNoTax',
                                                                    key: 'changePriceNoTax',
                                                                    width: 130,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                                            return rowData.changePriceNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'changeContractSum',
                                                                    key: 'changeContractSum',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                                            return rowData.changeContractSum.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????????????????',
                                                                    dataIndex: 'changeContractSumNoTax',
                                                                    key: 'changeContractSumNoTax',
                                                                    width: 130,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                                            return rowData.changeContractSumNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'changeContractSumTax',
                                                                    key: 'changeContractSumTax',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeContractSumTax || rowData.changeContractSumTax === 0) {
                                                                            return rowData.changeContractSumTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'remarks',
                                                                    key: 'remarks',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    type: "qnnTable",
                                                    field: "replenishLeaseDetailedList",
                                                    incToForm: true,
                                                    hide: (obj) => {
                                                        let rowData = obj?.tableFns?.qnnForm?.form?.getFieldsValue?.();
                                                        if (rowData && rowData.code7 === 'WL') {
                                                            return false;
                                                        } else {
                                                            return true;
                                                        }
                                                    },
                                                    qnnTableConfig: {
                                                        antd: {
                                                            size: "small",
                                                            rowKey: 'zxCtSuppliesContrReplenishLeaseDetailId'
                                                        },
                                                        paginationConfig: false,
                                                        isShowRowSelect: true,
                                                        formConfig: [
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'zxCtSuppliesContrReplenishLeaseDetailId',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'coBookID',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'pp5',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'workNo',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                isInTable: false,
                                                                form: {
                                                                    field: 'workType',
                                                                    type: 'string',
                                                                    hide: true,
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'alterType',
                                                                    key: 'alterType',
                                                                    width: 100,
                                                                    type: 'select',
                                                                    fixed: 'left'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: {
                                                                        label: 'label', //?????? label
                                                                        value: 'value'
                                                                    },
                                                                    optionData: [
                                                                        {
                                                                            label: '??????',
                                                                            value: '1'
                                                                        },
                                                                        {
                                                                            label: '??????',
                                                                            value: '2'
                                                                        }
                                                                    ],
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'workTypeID',
                                                                    key: 'workTypeID',
                                                                    width: 150,
                                                                    // type: 'select',
                                                                    fixed: 'left',
                                                                    render: (data, rowData) => {
                                                                        return rowData.workType;
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    optionConfig: {
                                                                        label: 'catName', //?????? label
                                                                        value: 'id'
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: 'getZxSkResCategoryMaterialsList',
                                                                        otherParams: {
                                                                            parentID: '0002'
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'workID',
                                                                    key: 'workID',
                                                                    width: 150,
                                                                    fixed: 'left',
                                                                    render: (data, rowData) => {
                                                                        return rowData.workNo;
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: (obj) => {
                                                                        if (obj.rowData.alterType === '1') {
                                                                            return {
                                                                                label: 'resCode', //?????? label
                                                                                value: 'id'
                                                                            }
                                                                        } else {
                                                                            return {
                                                                                label: 'workNo', //?????? label
                                                                                value: 'workID'
                                                                            }
                                                                        }
                                                                    },
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType) {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    fetchConfig: (obj) => {
                                                                        if (obj.rowData.alterType === '1') {
                                                                            if (obj.rowData.workTypeID) {
                                                                                return {
                                                                                    apiName: 'getZxSkResourceMaterialsListNameJoinResource',
                                                                                    otherParams: {
                                                                                        id: obj.rowData.workTypeID
                                                                                    }
                                                                                }
                                                                            }
                                                                        } else {
                                                                            return {
                                                                                apiName: 'getZxCtSuppliesContrLeaseListList',
                                                                                // apiName: 'getZxCtSuppliesContrApplyLeaseListList',
                                                                                otherParams: {
                                                                                    contractID: this.table.form.getFieldValue('pp6')
                                                                                }
                                                                            }
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'workName',
                                                                    key: 'workName',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'spec',
                                                                    key: 'spec',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'unit',
                                                                    key: 'unit',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'isNetPrice',
                                                                    key: 'isNetPrice',
                                                                    width: 100,
                                                                    tdEdit: true,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '???';
                                                                        } else {
                                                                            return '???';
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'checkbox',
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    optionData: [
                                                                        {
                                                                            value: "1"
                                                                        }
                                                                    ]
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'contrTrrm',
                                                                    key: 'contrTrrm',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'qty',
                                                                    key: 'qty',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.qty || rowData.qty === 0) {
                                                                            return rowData.qty.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'price',
                                                                    key: 'price',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.price || rowData.price === 0) {
                                                                            return rowData.price.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????????????????',
                                                                    dataIndex: 'contractSum',
                                                                    key: 'contractSum',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.contractSum || rowData.contractSum === 0) {
                                                                            return rowData.contractSum.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'priceNoTax',
                                                                    key: 'priceNoTax',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                                            return rowData.priceNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'contractSumNoTax',
                                                                    key: 'contractSumNoTax',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                                            return rowData.contractSumNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'contractSumTax',
                                                                    key: 'contractSumTax',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                                                                            return rowData.contractSumTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????(%)',
                                                                    dataIndex: 'taxRate',
                                                                    key: 'taxRate',
                                                                    width: 100,
                                                                    type: 'select'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: {
                                                                        label: 'itemName', //?????? label
                                                                        value: 'itemId'
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: 'getBaseCodeSelect',
                                                                        otherParams: {
                                                                            itemId: 'shuiLV'
                                                                        }
                                                                    },
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'isDeduct',
                                                                    key: 'isDeduct',
                                                                    width: 100,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '???';
                                                                        } else {
                                                                            return '???';
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'a',
                                                                    key: 'a',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.a || rowData.a === 0) {
                                                                            return rowData.a.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'rentUnit',
                                                                    key: 'rentUnit',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'alterTrrm',
                                                                    key: 'alterTrrm',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'changeQty',
                                                                    key: 'changeQty',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeQty || rowData.changeQty === 0) {
                                                                            return rowData.changeQty.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'changePrice',
                                                                    key: 'changePrice',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changePrice || rowData.changePrice === 0) {
                                                                            return rowData.changePrice.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    disabled: (obj) => {
                                                                        if (!obj.record.alterType || obj.record.alterType === '2') {
                                                                            return true
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    },
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????????????????',
                                                                    dataIndex: 'changePriceNoTax',
                                                                    key: 'changePriceNoTax',
                                                                    width: 130,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changePriceNoTax || rowData.changePriceNoTax === 0) {
                                                                            return rowData.changePriceNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '?????????????????????',
                                                                    dataIndex: 'changeContractSum',
                                                                    key: 'changeContractSum',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeContractSum || rowData.changeContractSum === 0) {
                                                                            return rowData.changeContractSum.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '????????????????????????',
                                                                    dataIndex: 'changeContractSumNoTax',
                                                                    key: 'changeContractSumNoTax',
                                                                    width: 130,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeContractSumNoTax || rowData.changeContractSumNoTax === 0) {
                                                                            return rowData.changeContractSumNoTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'changeContractSumTax',
                                                                    key: 'changeContractSumTax',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeContractSumTax || rowData.changeContractSumTax === 0) {
                                                                            return rowData.changeContractSumTax.toFixed(2)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 2,
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'remarks',
                                                                    key: 'remarks',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    label: '??????',
                                                    field: 'replenishShopListFileList',
                                                    type: 'files',
                                                    fetchConfig: {
                                                        apiName: 'upload'
                                                    },
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 4 },
                                                            sm: { span: 4 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 24 }
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'replenishAgreementId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????????????????',
                                            dataIndex: 'contractNo',
                                            key: 'contractNo',
                                            width: 150,
                                            fixed: 'left'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????????????????',
                                            dataIndex: 'pp3',
                                            key: 'pp3',
                                            width: 150,
                                            fixed: 'left',
                                            onClick: 'detail'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'contractName',
                                            key: 'contractName',
                                            width: 150
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'contractType',
                                            key: 'contractType',
                                            width: 100
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'firstID',
                                            key: 'firstID',
                                            width: 100,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'orgName',
                                                value: 'orgID'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCtContractListByStatus'
                                            },
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'secondID',
                                            key: 'secondID',
                                            width: 100,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'customerName',
                                                value: 'zxCrCustomerNewId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCrCustomerNewList'
                                            },
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'agent',
                                            key: 'agent',
                                            width: 100,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????????????????????????????',
                                            dataIndex: 'contractCost',
                                            key: 'contractCost',
                                            width: 180,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????????????????????????????',
                                            dataIndex: 'pp4',
                                            key: 'pp4',
                                            width: 200
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????????????????????????????',
                                            dataIndex: 'alterContractSum',
                                            key: 'alterContractSum',
                                            width: 180
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'isDeduct',
                                            key: 'isDeduct',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '1') {
                                                    return '???';
                                                } else {
                                                    return '???';
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'startDate',
                                            key: 'startDate',
                                            width: 100,
                                            format: 'YYYY-MM-DD'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'endDate',
                                            key: 'endDate',
                                            width: 100,
                                            format: 'YYYY-MM-DD'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'beginPer',
                                            key: 'beginPer',
                                            width: 100
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'apih5FlowStatus',
                                            key: 'apih5FlowStatus',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '2') {
                                                    return '????????????';
                                                } else {
                                                    return '--';
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'materialSource',
                                            key: 'materialSource',
                                            width: 120,
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName', //?????? label
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'category_contract_materialSource'
                                                }
                                            },
                                            placeholder: '?????????'
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "table3",
                            name: "qnnTable",
                            title: "?????????????????????",
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxCtSuppliesMarginRatioList',
                                    otherParams: (obj) => {
                                        return {
                                            contractID: obj.tableFns.qnnForm.form.getFieldValue('zxCtSuppliesContractId')
                                        }
                                    }
                                },
                                antd: {
                                    size: "small",
                                    rowKey: 'zxCtSuppliesMarginRatioId'
                                },
                                drawerConfig: {
                                    width: '1200px'
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
                                },
                                isShowRowSelect: true,
                                actionBtns: [
                                    {
                                        name: 'add',//??????add del
                                        icon: 'plus',//icon
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        label: '??????',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
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
                                                    apiName: 'addZxCtSuppliesMarginRatio',
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'edit',//??????add del
                                        icon: 'edit',//icon
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        label: '??????',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
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
                                                    apiName: 'updateZxCtSuppliesMarginRatio',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//??????add del
                                        icon: 'delete',//icon
                                        type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                        label: '??????',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        fetchConfig: {//ajax??????
                                            apiName: 'batchDeleteUpdateZxCtSuppliesMarginRatio',
                                        },
                                    }
                                ],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'zxCtSuppliesMarginRatioId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'orgID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj.tableFns.qnnForm.form.getFieldValue('orgID');
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'contractID',
                                            type: 'string',
                                            initialValue: (obj) => {
                                                return obj.tableFns.qnnForm.form.getFieldValue('zxCtSuppliesContractId');
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'statisticsName',
                                            key: 'statisticsName',
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????(%)',
                                            dataIndex: 'statisticsRate',
                                            key: 'statisticsRate'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            max: 100,
                                            precision: 2,
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'timeLimit',
                                            key: 'timeLimit'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'backCondition',
                                            key: 'backCondition'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '?????????'
                                        }
                                    },
                                ]
                            }
                        },
                        {
                            field: "table4",
                            name: "qnnTable",
                            title: "?????????",
                            content: {
                                tabs: [],
                                fetchConfig: (obj) => {
                                    if (obj.tableFns.qnnForm.form.getFieldValue('code7') === 'WL') {
                                        return {
                                            apiName: 'getZxCtSuppliesLeaseSettlementListListByOrgId',
                                            otherParams: {
                                                apih5FlowStatus: '2',
                                                contractID: obj.tableFns.qnnForm.form.getFieldValue('zxCtSuppliesContractId')
                                            }
                                        }
                                    } else {
                                        return {
                                            apiName: 'getZxCtSuppliesShopSettlementListListByOrgId',
                                            otherParams: {
                                                apih5FlowStatus: '2',
                                                contractID: obj.tableFns.qnnForm.form.getFieldValue('zxCtSuppliesContractId')
                                            },
                                        }
                                    }
                                },
                                antd: {
                                    size: "small",
                                    rowKey: (rowData) => {
                                        return rowData.zxCtSuppliesLeaseSettlementListId ? rowData.zxCtSuppliesLeaseSettlementListId : rowData.zxCtSuppliesShopSettlementId;
                                    }
                                },
                                drawerConfig: {
                                    width: '1200px'
                                },
                                paginationConfig: false,
                                isShowRowSelect: false,
                                tabs: [
                                    {
                                        field: "form1",
                                        name: "qnnForm",
                                        title: "????????????",
                                        content: {
                                            formConfig: [
                                                {
                                                    field: 'workId',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'stockBillNos',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'period',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'apih5FlowStatus',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'contractNo',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'zxCtSuppliesShopSettlementId',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'zxCtSuppliesLeaseSettlementListId',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'secondName',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'orgName',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'comID',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    field: 'comName',
                                                    type: 'string',
                                                    hide: true,
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "orgID",
                                                    type: 'select',
                                                    showSearch: true,
                                                    span: 12,
                                                    required: true,
                                                    optionConfig: {
                                                        label: 'departmentName',
                                                        value: 'departmentId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getSysProjectBySelect',
                                                        otherParams: { departmentId }
                                                    },
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "contractID",
                                                    type: 'select',
                                                    showSearch: true,
                                                    span: 12,
                                                    required: true,
                                                    placeholder: '?????????',
                                                    optionConfig: {
                                                        label: 'contractNo',
                                                        value: 'zxCtSuppliesContractId'
                                                    },
                                                    parent: 'orgID',
                                                    fetchConfig: {
                                                        apiName: 'getZxCtSuppliesContractListByOrgId',
                                                        otherParams: {
                                                            code7: 'WZ',
                                                            pp9: '1',
                                                            contractStatus: '1'
                                                        },
                                                        params: {
                                                            orgID: 'orgID'
                                                        }
                                                    },
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
                                                    field: "contractName",
                                                    type: 'string',
                                                    span: 12,
                                                    disabled: true,
                                                },
                                                {
                                                    label: '???????????????',
                                                    field: "signedNo",
                                                    type: 'string',
                                                    span: 12,
                                                    disabled: true
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "secondID",
                                                    disabled: true,
                                                    span: 12,
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'customerName',
                                                        value: 'zxCrCustomerNewId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getZxCrCustomerNewList'
                                                    }
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "periodDate",
                                                    type: 'month',
                                                    placeholder: '?????????',
                                                    required: true,
                                                    span: 12
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "billType",
                                                    type: 'select',
                                                    required: true,
                                                    placeholder: '?????????',
                                                    span: 12,
                                                    optionConfig: {
                                                        label: 'label',
                                                        value: 'value',
                                                    },
                                                    optionData: (obj) => {
                                                        if (obj.form.getFieldValue('isFished') === '1' && obj.form.getFieldValue('settleType') === '3') {
                                                            return [
                                                                { label: '???????????????', value: '2' }
                                                            ]
                                                        } else {
                                                            return [
                                                                { label: '??????', value: '0' },
                                                                { label: '??????', value: '1' },
                                                            ]
                                                        }
                                                    },
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "isDeduct",
                                                    type: 'radio',
                                                    disabled: true,
                                                    optionData: [
                                                        { label: '???', value: '1' },
                                                        { label: '???', value: '0' }
                                                    ],
                                                    initialValue: '0',
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 12 },
                                                            sm: { span: 12 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 8 },
                                                            sm: { span: 8 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '?????????????????????',
                                                    field: "isFished",
                                                    type: 'radio',
                                                    optionData: [
                                                        { label: '???', value: '1' },
                                                        { label: '???', value: '0' }
                                                    ],
                                                    initialValue: '0',
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 12 },
                                                            sm: { span: 12 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 8 },
                                                            sm: { span: 8 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '??????????????????',
                                                    field: "isFirst",
                                                    type: 'radio',
                                                    optionData: [
                                                        { label: '???', value: '1' },
                                                        { label: '???', value: '0' }
                                                    ],
                                                    initialValue: '0',
                                                    disabled: true,
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 12 },
                                                            sm: { span: 12 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 8 },
                                                            sm: { span: 8 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '???????????????????????????',
                                                    field: "initSerialNumber",
                                                    required: true,
                                                    span: 12,
                                                    placeholder: '?????????',
                                                    type: 'string'
                                                },
                                                {
                                                    label: '?????????',
                                                    field: "reportPerson",
                                                    type: 'string',
                                                    span: 12,
                                                    placeholder: '?????????'
                                                },
                                                {
                                                    label: '?????????',
                                                    field: "countPerson",
                                                    type: 'string',
                                                    span: 12,
                                                    placeholder: '?????????',
                                                },
                                                {
                                                    label: '?????????',
                                                    field: "reCountPerson",
                                                    type: 'string',
                                                    span: 12,
                                                    placeholder: '?????????',
                                                },
                                                {
                                                    label: '??????????????????',
                                                    field: "startDate",
                                                    span: 12,
                                                    type: 'date',
                                                    condition: [
                                                        {
                                                            regex: {
                                                                zxCtSuppliesLeaseSettlementListId: ['!', /(''|null|undefined)/ig]
                                                            },
                                                            action: 'hide'
                                                        }
                                                    ],
                                                    required: true,
                                                },
                                                {
                                                    label: '??????????????????',
                                                    field: "documentsEndTime",
                                                    span: 12,
                                                    type: 'date',
                                                    condition: [
                                                        {
                                                            regex: {
                                                                zxCtSuppliesLeaseSettlementListId: ['!', /(''|null|undefined)/ig]
                                                            },
                                                            action: 'hide'
                                                        }
                                                    ],
                                                    required: true,
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "reportDate",
                                                    span: 12,
                                                    type: 'date',
                                                    required: true,
                                                },
                                                {
                                                    label: '????????????',
                                                    field: "businessDate",
                                                    span: 12,
                                                    type: 'date',
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
                                                    label: '????????????',
                                                    field: 'content',
                                                    type: 'textarea',
                                                    placeholder: '?????????',
                                                    span: 12,
                                                },
                                                {
                                                    label: '??????????????????',
                                                    field: 'appraisal',
                                                    type: 'textarea',
                                                    placeholder: '?????????',
                                                    span: 12,
                                                },
                                                {
                                                    label: '??????',
                                                    field: 'cwStatusRemark',
                                                    type: 'textarea',
                                                    span: 12,
                                                    placeholder: '?????????',
                                                },
                                                {
                                                    label: '????????????',
                                                    field: 'remarks',
                                                    type: 'textarea',
                                                    span: 12,
                                                    placeholder: '?????????',
                                                },
                                                {
                                                    label: '??????',
                                                    field: 'settlementFileList',
                                                    type: 'files',
                                                    span: 12,
                                                    fetchConfig: {
                                                        apiName: 'upload'
                                                    },
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 4 },
                                                            sm: { span: 4 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 24 },
                                                            sm: { span: 24 }
                                                        }
                                                    }
                                                },
                                            ]
                                        }
                                    },
                                    {
                                        name: "TabTwoList",
                                        field: "TabTwoList",
                                        title: "????????????",
                                        content: obj => {
                                            if (obj.initialValues.zxCtSuppliesShopSettlementId) {
                                                let params = { ...this.props }
                                                let rowData = obj.initialValues;
                                                let paramsData = {};
                                                paramsData.stockBillNos = rowData.stockBillNos ? rowData.stockBillNos : 0;
                                                paramsData.billType = rowData?.billType;
                                                paramsData.periodDate = moment(rowData?.periodDate).valueOf();
                                                paramsData.clickName = obj.clickCb.rowInfo.name;
                                                paramsData.startDate = moment(rowData?.startDate).valueOf();
                                                paramsData.orgID = rowData?.orgID;
                                                paramsData.secondID = rowData?.secondID;
                                                paramsData.contractID = rowData?.contractID;
                                                paramsData.zxCtSuppliesShopSettlementId = rowData?.zxCtSuppliesShopSettlementId;
                                                paramsData.documentsEndTime = moment(rowData?.documentsEndTime).valueOf();
                                                paramsData.tabIndex = obj.btnCallbackFn.getActiveKey();
                                                params.paramsData = paramsData;
                                                return <TabTwoCGList {...params} />;
                                            } else {
                                                let params = { ...this.props }
                                                let rowData = obj.initialValues;
                                                let paramsData = {};
                                                paramsData.billNo = rowData.billNo ? rowData.billNo : '';
                                                paramsData.billType = rowData?.billType;
                                                paramsData.clickName = obj.clickCb.rowInfo.name;
                                                paramsData.contractID = rowData?.contractID;
                                                paramsData.zxCtSuppliesLeaseSettlementListId = rowData?.zxCtSuppliesLeaseSettlementListId;
                                                paramsData.tabIndex = obj.btnCallbackFn.getActiveKey();
                                                params.paramsData = paramsData;
                                                return <TabTwoZLList {...params} />;
                                            }
                                        }
                                    },
                                    {
                                        name: "TabThreeList",
                                        field: "TabThreeList",
                                        title: "???????????????",
                                        content: obj => {
                                            if (obj.initialValues.zxCtSuppliesShopSettlementId) {
                                                let params = { ...this.props }
                                                let rowData = obj.initialValues;
                                                let paramsData = {};
                                                paramsData.billType = rowData?.billType;
                                                paramsData.clickName = obj.clickCb.rowInfo.name;
                                                paramsData.contractID = rowData?.contractID;
                                                paramsData.zxCtSuppliesShopSettlementId = rowData?.zxCtSuppliesShopSettlementId;
                                                paramsData.orgID = rowData?.orgID;
                                                paramsData.tabIndex = obj.btnCallbackFn.getActiveKey();
                                                params.paramsData = paramsData;
                                                return <TabThreeCGList {...params} />;
                                            } else {
                                                let params = { ...this.props }
                                                let rowData = obj.initialValues;
                                                let paramsData = {};
                                                paramsData.billType = rowData?.billType;
                                                paramsData.clickName = obj.clickCb.rowInfo.name;
                                                paramsData.zxCtSuppliesLeaseSettlementListId = rowData?.zxCtSuppliesLeaseSettlementListId;
                                                paramsData.orgID = rowData?.orgID;
                                                paramsData.tabIndex = obj.btnCallbackFn.getActiveKey();
                                                params.paramsData = paramsData;
                                                return <TabThreeZLList {...params} />;
                                            }
                                        }
                                    },
                                    {
                                        field: "table1",
                                        name: "qnnTable",
                                        title: "?????????",
                                        content: {
                                            fetchConfig: (obj) => {
                                                if (obj.initialValues.zxCtSuppliesLeaseSettlementListId) {
                                                    return {
                                                        apiName: 'getZxCtSuppliesLeaseSettlementItemListByConID',
                                                        otherParams: {
                                                            contractID: obj.initialValues.contractID,
                                                            zxCtSuppliesLeaseSettlementListId: obj.initialValues.zxCtSuppliesLeaseSettlementListId
                                                        }
                                                    }
                                                } else {
                                                    return {
                                                        apiName: 'getZxCtSuppliesShopSettlementItemListByConID',
                                                        otherParams: {
                                                            contractID: obj.initialValues.contractID,
                                                            zxCtSuppliesShopSettlementId: obj.initialValues.zxCtSuppliesShopSettlementId
                                                        },
                                                    }
                                                }
                                            },
                                            antd: {
                                                size: "small",
                                                rowKey: (rowData) => {
                                                    return rowData.zxCtSuppliesLeaseSettlementListId ? rowData.zxCtSuppliesLeaseSettlementListId : rowData.zxCtSuppliesShopSettlementItemId;
                                                }
                                            },
                                            paginationConfig: false,
                                            pageSize: 999999,
                                            isShowRowSelect: false,
                                            formConfig: [
                                                {
                                                    table: {
                                                        title: '?????????',
                                                        dataIndex: 'statisticsName',
                                                        key: 'statisticsName',
                                                        width: 100,
                                                        type: 'string',
                                                        align: 'center',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'thisAmt',
                                                        key: 'thisAmt',
                                                        width: 150,
                                                        type: 'string',
                                                        align: 'center',
                                                        render: (val) => {
                                                            if (val && !isNaN(val)) {
                                                                return Number(val).toFixed(2)
                                                            } else {
                                                                return val
                                                            }
                                                        },
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'totalAmt',
                                                        key: 'totalAmt',
                                                        width: 100,
                                                        type: 'string',
                                                        align: 'center',
                                                        render: (val) => {
                                                            if (val && !isNaN(val)) {
                                                                return Number(val).toFixed(2)
                                                            } else {
                                                                return val
                                                            }
                                                        },
                                                    },
                                                }
                                            ]
                                        }
                                    }
                                ],
                                formConfig: [
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'billNo',
                                            key: 'billNo',
                                            filter: true,
                                            fieldsConfig: { type: 'string' },
                                            fixed: 'left',
                                            width: 150,
                                            tooltip: 15,
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
                                            fixed: 'left',
                                            width: 150,
                                            tooltip: 15,
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
                                            dataIndex: 'billType',
                                            key: 'billType',
                                            width: 120,
                                            render: (h) => {
                                                if (h === '0') {
                                                    return '??????'
                                                } else if (h === '1') {
                                                    return '??????'
                                                } else if (h === '2') {
                                                    return '???????????????'
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'contractName',
                                            key: 'contractName',
                                            filter: true,
                                            fieldsConfig: { type: 'string' },
                                            width: 150,
                                            tooltip: 15,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'secondID',
                                            key: 'secondID',
                                            width: 180,
                                            filter: true,
                                            type: 'select',
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'customerName',
                                                value: 'zxCrCustomerNewId',
                                            },
                                            fetchConfig: { apiName: 'getZxCrCustomerNewList' }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????????????????',
                                            dataIndex: 'thisAmt',
                                            key: 'thisAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????????????????',
                                            dataIndex: 'totalAmt',
                                            key: 'totalAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '???????????????????????????',
                                            dataIndex: 'thisPayAmt',
                                            key: 'thisPayAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '???????????????????????????',
                                            dataIndex: 'totalPayAmt',
                                            key: 'totalPayAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????????????????',
                                            dataIndex: 'notPassNum',
                                            key: 'notPassNum',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????????????????',
                                            dataIndex: 'beginDate',
                                            key: 'beginDate',
                                            width: 150,
                                            format: "YYYY-MM-DD"
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????????????????',
                                            dataIndex: 'endDate',
                                            key: 'endDate',
                                            width: 150,
                                            format: "YYYY-MM-DD"
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'businessDate',
                                            key: 'businessDate',
                                            width: 100,
                                            format: "YYYY-MM-DD"
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'createUserName',
                                            key: 'createUserName',
                                            width: 100,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'apih5FlowStatus',
                                            key: 'apih5FlowStatus',
                                            width: 100,
                                            fixed: 'right',
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
                                ]
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxCtSuppliesContractId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'firstName',
                                key: 'firstName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'code7',
                                key: 'code7',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //?????? label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'category_contr_type_CL'
                                    }
                                },
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                filter: true,
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //?????? label
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'category_contract_materialSource'
                                    }
                                },
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????(??????)',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 150,
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '???????????????????????????(??????)',
                                // dataIndex: 'changeContractSum',
                                // key: 'changeContractSum',
                                dataIndex: 'alterContractSum',
                                key: 'alterContractSum',
                                width: 200,
                            },
                            form: {
                                type: 'number',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'isDeduct',
                                key: 'isDeduct',
                                width: 100,
                                render: (data) => {
                                    if (data === '1') {
                                        return '???';
                                    } else {
                                        return '???';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'csTimeLimit',
                                key: 'csTimeLimit',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 120,
                                render: (data) => {
                                    if (!data) {
                                        return '?????????';
                                    } else if (data === '1') {
                                        return '?????????';
                                    } else if (data === '2') {
                                        return '????????????';
                                    } else if (data === '3') {
                                        return '??????';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractStatus',
                                key: 'contractStatus',
                                type: 'select',
                                width: 100,
                                fixed: 'right'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //?????? label
                                    value: 'itemId'
                                },
                                // fetchConfig: {
                                //     apiName: 'getBaseCodeSelect',
                                //     otherParams: {
                                //         itemId: 'heTongZhuangTai'
                                //     }
                                // },
                                optionData: [
                                    {
                                        itemName: '??????',
                                        itemId: '3'
                                    },
                                    {
                                        itemName: '?????????',
                                        itemId: '1'
                                    }
                                ],
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'settleType',
                                key: 'settleType',
                                type: 'select',
                                width: 120,
                                fixed: 'right'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '?????????????????????',
                                        value: '0'
                                    },
                                    {
                                        label: '???????????????',
                                        value: '1'
                                    },
                                    {
                                        label: '??????????????????',
                                        value: '2'
                                    },
                                    {
                                        label: '???????????????',
                                        value: '3'
                                    }
                                ],
                                placeholder: '?????????'
                            }
                        }
                    ]}
                />
                {
                    visible ? <Drawer
                        title={'??????????????????'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visible}
                        width={1200}
                        className={'drawerClass'}
                        footer={
                            <div
                                style={{
                                    textAlign: 'right',
                                }}
                            >
                                <Button onClick={this.onClose}>
                                    ??????
                              </Button>
                            </div>
                        }
                    >
                        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                            <TabPane tab="????????????" key="1">
                                <QnnForm
                                    {...this.props}
                                    match={this.props.match}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    data={flowData?.apiData ? JSON.parse(flowData.apiData) : null}
                                    formConfig={[
                                        {
                                            label: '????????????',
                                            field: "contractNo",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "contractName",
                                            type: 'string',
                                            required: true,
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "contractType",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "firstID",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'orgName',
                                                value: 'orgID',
                                                linkageFields: {
                                                    contractNo: 'contractNo',
                                                }
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCtContractListByStatus'
                                            },
                                            required: true,
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "secondID",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'customerName',
                                                value: 'zxCrCustomerNewId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCrCustomerNewList'
                                            },
                                            required: true,
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '???????????????',
                                            field: "agent",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '??????????????????(??????)',
                                            field: "contractCost",
                                            type: 'number',
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
                                            precision: 6,
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
                                        },
                                        {
                                            label: '?????????????????????(??????)',
                                            field: "contractCostNoTax",
                                            type: 'number',
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
                                            precision: 6,
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
                                        },
                                        {
                                            label: '????????????(??????)',
                                            field: "contractCostTax",
                                            type: 'number',
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
                                            precision: 6,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "isDeduct",
                                            type: 'checkbox',
                                            optionData: [
                                                {
                                                    value: '1'
                                                }
                                            ],
                                            disabled: true,
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "csTimeLimit",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "code7",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName', //?????? label
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'category_contr_type_CL'
                                                }
                                            },
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                            required: true
                                        },
                                        {
                                            label: '????????????',
                                            field: "materialSource",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName', //?????? label
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'category_contract_materialSource'
                                                }
                                            },
                                            condition: [
                                                {
                                                    regex: { code7: 'WZ' },
                                                    action: ['required', 'unDisabled'],
                                                },
                                                {
                                                    regex: { code7: ['!', 'WZ'] },
                                                    action: ['unRequired', 'disabled'],
                                                }
                                            ],
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: "resCategoryID",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'catName', //?????? label
                                                value: 'id'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxSkResCategoryMaterialsList',
                                                otherParams: {
                                                    parentID: '0002'
                                                }
                                            },
                                            disabled: true,
                                            placeholder: '?????????',
                                            span: 12,
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
                                        },
                                        {
                                            label: '????????????',
                                            field: 'content',
                                            type: 'textarea',
                                            placeholder: '?????????',
                                            disabled: true,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 4 },
                                                    sm: { span: 4 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 20 },
                                                    sm: { span: 20 }
                                                }
                                            }
                                        },
                                        {
                                            label: '??????',
                                            field: 'remarks',
                                            type: 'textarea',
                                            placeholder: '?????????',
                                            disabled: true,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 4 },
                                                    sm: { span: 4 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 20 },
                                                    sm: { span: 20 }
                                                }
                                            }
                                        },
                                        {
                                            type: 'qnnTable',
                                            field: 'qnnTableList',
                                            qnnTableConfig: {
                                                fetchConfig: (obj) => {
                                                    if (obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.code7 && obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.code7 !== 'WL') {
                                                        return {
                                                            apiName: 'getZxCtSuppliesContrApplyShopListList',
                                                            otherParams: {
                                                                pp5: obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.applyId
                                                            }
                                                        }
                                                    } else if (obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.code7 && obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.code7 === 'WL') {
                                                        return {
                                                            apiName: 'getZxCtSuppliesContrApplyLeaseListList',
                                                            otherParams: {
                                                                pp5: obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.applyId
                                                            }
                                                        }
                                                    } else {
                                                        return {};
                                                    }
                                                },
                                                antd: {
                                                    size: "small",
                                                    rowKey: (rowData) => {
                                                        return rowData.applyLeaseListId ? rowData.applyLeaseListId : rowData.applyShopListId;
                                                    }
                                                },
                                                paginationConfig: {
                                                    position: 'bottom'
                                                },
                                                isShowRowSelect: false,
                                                formConfig: [
                                                    {
                                                        table: {
                                                            title: '????????????',
                                                            dataIndex: 'workType',
                                                            key: 'workType',
                                                            width: 150,
                                                            fixed: 'left'
                                                        },
                                                        form: {
                                                            type: 'select',
                                                            optionConfig: {
                                                                label: 'catName', //?????? label
                                                                value: 'id'
                                                            },
                                                            fetchConfig: {
                                                                apiName: 'getZxSkResCategoryMaterialsList',
                                                                otherParams: {
                                                                    parentID: '0002'
                                                                }
                                                            },
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '????????????',
                                                            dataIndex: 'workNo',
                                                            key: 'workNo',
                                                            width: 150,
                                                            fixed: 'left'
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '????????????',
                                                            dataIndex: 'workName',
                                                            key: 'workName',
                                                            width: 150
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '????????????',
                                                            dataIndex: 'spec',
                                                            key: 'spec',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '??????',
                                                            dataIndex: 'unit',
                                                            key: 'unit',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '????????????',
                                                            dataIndex: 'isNetPrice',
                                                            key: 'isNetPrice',
                                                            width: 100,
                                                            render: (data) => {
                                                                if (data === '1') {
                                                                    return '???';
                                                                } else {
                                                                    return '???';
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'checkbox',
                                                            optionData: [
                                                                {
                                                                    value: "1"
                                                                }
                                                            ]
                                                        }
                                                    },
                                                    {
                                                        isInTable: (obj) => {
                                                            let rowData = obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data;
                                                            if (rowData?.code7 === 'WL') {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        },
                                                        table: {
                                                            title: '????????????',
                                                            dataIndex: 'rentUnit',
                                                            key: 'rentUnit',
                                                            width: 150
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        isInTable: (obj) => {
                                                            let rowData = obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data;
                                                            if (rowData?.code7 === 'WL') {
                                                                return true;
                                                            } else {
                                                                return false
                                                            }
                                                        },
                                                        table: {
                                                            title: '??????',
                                                            dataIndex: 'contrTrrm',
                                                            key: 'contrTrrm',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '??????',
                                                            dataIndex: 'qty',
                                                            key: 'qty',
                                                            width: 100,
                                                            render: (data, rowData) => {
                                                                if (rowData.qty || rowData.qty === 0) {
                                                                    return rowData.qty.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 2,
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '??????????????????',
                                                            dataIndex: 'price',
                                                            key: 'price',
                                                            width: 120,
                                                            render: (data, rowData) => {
                                                                if (rowData.price || rowData.price === 0) {
                                                                    return rowData.price.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 2,
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '??????????????????',
                                                            dataIndex: 'contractSum',
                                                            key: 'contractSum',
                                                            width: 120,
                                                            render: (data, rowData) => {
                                                                if (rowData.contractSum || rowData.contractSum === 0) {
                                                                    return rowData.contractSum.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 2,
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '?????????????????????',
                                                            dataIndex: 'priceNoTax',
                                                            key: 'priceNoTax',
                                                            width: 120,
                                                            render: (data, rowData) => {
                                                                if (rowData.priceNoTax || rowData.priceNoTax === 0) {
                                                                    return rowData.priceNoTax.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 2,
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '?????????????????????',
                                                            dataIndex: 'contractSumNoTax',
                                                            key: 'contractSumNoTax',
                                                            width: 120,
                                                            render: (data, rowData) => {
                                                                if (rowData.contractSumNoTax || rowData.contractSumNoTax === 0) {
                                                                    return rowData.contractSumNoTax.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 2,
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '??????',
                                                            dataIndex: 'contractSumTax',
                                                            key: 'contractSumTax',
                                                            width: 100,
                                                            render: (data, rowData) => {
                                                                if (rowData.contractSumTax || rowData.contractSumTax === 0) {
                                                                    return rowData.contractSumTax.toFixed(2)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 2,
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '??????(%)',
                                                            dataIndex: 'taxRate',
                                                            key: 'taxRate',
                                                            width: 100,
                                                            type: 'select'
                                                        },
                                                        form: {
                                                            type: 'select',
                                                            optionConfig: {
                                                                label: 'itemName', //?????? label
                                                                value: 'itemId'
                                                            },
                                                            fetchConfig: {
                                                                apiName: 'getBaseCodeSelect',
                                                                otherParams: {
                                                                    itemId: 'shuiLV'
                                                                }
                                                            },
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '????????????',
                                                            dataIndex: 'isDeduct',
                                                            key: 'isDeduct',
                                                            width: 100,
                                                            render: (data) => {
                                                                if (data === '1') {
                                                                    return '???';
                                                                } else {
                                                                    return '???';
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '?????????'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '??????',
                                                            dataIndex: 'remarks',
                                                            key: 'remarks',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '?????????'
                                                        }
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            type: "textarea",
                                            label: "????????????",
                                            field: "opinionField1",
                                            formatter: (val) => {
                                                if (val) {
                                                    if (val.indexOf('<br/>') !== -1) {
                                                        return val.replace("<br/>", "\r\n");
                                                    }
                                                }
                                            },
                                            disabled: true,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 4 },
                                                    sm: { span: 4 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 20 },
                                                    sm: { span: 20 }
                                                }
                                            }
                                        }
                                    ]}
                                />
                            </TabPane>
                            <TabPane tab="????????????" key="2">
                                <Operation {...this.props} flowData={flowData}/>
                            </TabPane>
                        </Tabs>
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;