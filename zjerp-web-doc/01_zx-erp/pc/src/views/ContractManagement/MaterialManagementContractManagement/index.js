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
import DetailForm from '../MaterialManagementContract/detail';
import Operation from './operation';
import SelectFilesDownLoad from '../SelectFilesDownLoad';
const confirm = Modal.confirm;
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: 'zxCtSuppliesContractId',
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
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            flowData: null,
            visible: false,
            activeKey: '1',
            loginUser: curCompany.realLabel
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
    render () {
        const { departmentId, flowData, visible, activeKey, loginUser } = this.state;
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
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            let props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "MaterialManagementContractManagement"
                            }
                        }
                    }}
                    method={{
                        diySubmitSuccess: (obj) => {
                            if (obj.response.success) {
                                this.table.refresh();
                            }
                        },
                        editDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        diySubmitHide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === "0") {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        diySubmit3Hide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === "3") {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        diySubmit3Click: (obj) => {
                            if (this.tableThree.getVTableData()?.length) {
                                this.props.myFetch('batchAddZxCtSuppliesMarginRatioByConID', { contractID: obj._formData.zxCtSuppliesContractId, marginRatioList: this.tableThree.getVTableData() }).then(({ success, message }) => {
                                    if (success) {
                                        Msg.success(message);
                                    } else {
                                        Msg.error(message);
                                    }
                                });
                            } else {
                                Msg.error('?????????????????????!');
                            }
                        },
                        diySubmit6Hide: (obj) => {
                            let index = obj.btnCallbackFn.getActiveKey();
                            if (index === "5") {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        //??????4??????????????????
                        configForNo_4Fetch: (obj) => {
                            let params = {}
                            params.alterContractSum = obj.rowData.alterContractSum
                            params.contractNo = obj.rowData.contractNo
                            params.contractName = obj.rowData.contractName
                            params.secondName = obj.rowData.secondName
                            params.zxCtSuppliesContractId = obj.rowData.zxCtSuppliesContractId
                            return {
                                apiName: 'updateZxCtSuppliesContract',
                                otherParams: params
                            }
                        },
                        resumptionOfExecutionDisabled: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        resumptionOfExecutionClick: (obj) => {
                            console.log(obj)
                            confirm({
                                content: '???????????????????????????????',
                                onOk: () => {
                                    var selectedRows = obj.selectedRows;
                                    selectedRows[0].contractStatus = selectedRows[0].contractStatus === '1' ? '3' : '1';
                                    this.props.myFetch('updateZxCtSuppliesContract', ...selectedRows).then(({ success, message }) => {
                                        if (success) {
                                            Msg.success(message)
                                            obj.btnCallbackFn.refresh();
                                            obj.btnCallbackFn.clearSelectedRows();
                                        } else {
                                            Msg.error(message);
                                        }
                                    })
                                }
                            })
                        },
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                    }}
                    componentsKey={{
                        DetailForm: (obj) => {
                            return <DetailForm
                                {...this.props}
                                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                            />
                        }
                    }}
                    // drawerShowToggle={(obj) => {
                    //     let { drawerIsShow } = obj;
                    //     if (!drawerIsShow) {
                    //         obj.btnCallbackFn.clearSelectedRows();
                    //         obj.btnCallbackFn.refresh();
                    //     }
                    // }}
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
                                        label: '??????????????????',
                                        field: "audit",
                                        hide: true,
                                        type: 'string',
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
                                        field: 'firstId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'secondID',
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
                                            return (
                                                <Row>
                                                    <Col span={1}></Col>
                                                    <Col span={23}>
                                                        <div style={{ height: '56px', lineHeight: '56px' }}>
                                                            <Button type="primary" onClick={() => {
                                                                this.props.myFetch('openFlowByReport', { apiName: 'getZxCtSuppliesContrApplyFlowDetail', apiType: 'POST', flowId: 'ZxCtSuppliesContrApply', workId: obj.form.getFieldValue('workId') }).then(
                                                                    ({ success, message, data }) => {
                                                                        if (success) {
                                                                            this.setState({
                                                                                activeKey: '1',
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
                                        type: 'string',
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
                                        type: 'string',
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
                                        field: "XTLULX",
                                        disabled: true,
                                        initialValue: '????????????',
                                        type: 'string',
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
                                    // {
                                    //     label: "?????????????????????(??????)",
                                    //     field: "contractCostNoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     span: 12,
                                    //     precision: 6,
                                    //     formItemLayout: {
                                    //         labelCol: {
                                    //             xs: { span: 8 },
                                    //             sm: { span: 8 }
                                    //         },
                                    //         wrapperCol: {
                                    //             xs: { span: 12 },
                                    //             sm: { span: 12 }
                                    //         }
                                    //     }
                                    // },
                                    // {
                                    //     label: "??????????????????(??????)",
                                    //     field: "contractCost",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     span: 12,
                                    //     precision: 6,
                                    //     formItemLayout: {
                                    //         labelCol: {
                                    //             xs: { span: 8 },
                                    //             sm: { span: 8 }
                                    //         },
                                    //         wrapperCol: {
                                    //             xs: { span: 12 },
                                    //             sm: { span: 12 }
                                    //         }
                                    //     }
                                    // },
                                    // {
                                    //     label: "????????????????????????(??????)",
                                    //     field: "alterContractSumNoTax",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     span: 12,
                                    //     precision: 6,
                                    //     formItemLayout: {
                                    //         labelCol: {
                                    //             xs: { span: 8 },
                                    //             sm: { span: 8 }
                                    //         },
                                    //         wrapperCol: {
                                    //             xs: { span: 12 },
                                    //             sm: { span: 12 }
                                    //         }
                                    //     }
                                    // },
                                    // {
                                    //     label: "?????????????????????(??????)",
                                    //     field: "alterContractSum",
                                    //     type: 'number',
                                    //     disabled: true,
                                    //     span: 12,
                                    //     precision: 6,
                                    //     formItemLayout: {
                                    //         labelCol: {
                                    //             xs: { span: 8 },
                                    //             sm: { span: 8 }
                                    //         },
                                    //         wrapperCol: {
                                    //             xs: { span: 12 },
                                    //             sm: { span: 12 }
                                    //         }
                                    //     }
                                    // },
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
                                            fetchConfig: (obj) => {
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
                                                    dependencies: ["code7"],
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
                                                                table: {
                                                                    title: '??????',
                                                                    dataIndex: 'qty',
                                                                    key: 'qty',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.qty || rowData.qty === 0) {
                                                                            return rowData.qty.toFixed(3)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 3,
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
                                                                            return rowData.price.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                                                            return rowData.priceNoTax.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                                            // {
                                                            //     table: {
                                                            //         title: '???????????????',
                                                            //         dataIndex: 'settledQty',
                                                            //         key: 'settledQty',
                                                            //         width: 100,
                                                            //         render: (data, rowData) => {
                                                            //             if (rowData.a || rowData.a === 0) {
                                                            //                 return rowData.a.toFixed(2)
                                                            //             }
                                                            //         }
                                                            //     },
                                                            //     form: {
                                                            //         type: 'number',
                                                            //         placeholder: '?????????'
                                                            //     }
                                                            // },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'changeQty',
                                                                    key: 'changeQty',
                                                                    width: 120,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.changeQty || rowData.changeQty === 0) {
                                                                            return rowData.changeQty.toFixed(3)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 3,
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
                                                                            return rowData.changePrice.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                                                            return rowData.changePriceNoTax.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                                    dependencies: ["code7"],
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
                                                                    type: 'select',
                                                                    fixed: 'left',
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
                                                                    dataIndex: 'workID',
                                                                    key: 'workID',
                                                                    width: 150,
                                                                    type: 'select',
                                                                    fixed: 'left'
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
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.contrTrrm || rowData.contrTrrm === 0) {
                                                                            return rowData.contrTrrm.toFixed(3)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 3,
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
                                                                            return rowData.qty.toFixed(3)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 3,
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
                                                                            return rowData.price.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                                                            return rowData.priceNoTax.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                                            // {
                                                            //     table: {
                                                            //         title: '???????????????',
                                                            //         dataIndex: 'settledQty',
                                                            //         key: 'settledQty',
                                                            //         width: 100,
                                                            //         render: (data, rowData) => {
                                                            //             if (rowData.a || rowData.a === 0) {
                                                            //                 return rowData.a.toFixed(2)
                                                            //             }
                                                            //         }
                                                            //     },
                                                            //     form: {
                                                            //         type: 'number',
                                                            //         placeholder: '?????????'
                                                            //     }
                                                            // },
                                                            {
                                                                table: {
                                                                    title: '????????????',
                                                                    dataIndex: 'rentUnit',
                                                                    key: 'rentUnit',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '?????????'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '???????????????',
                                                                    dataIndex: 'alterTrrm',
                                                                    key: 'alterTrrm',
                                                                    width: 100,
                                                                    render: (data, rowData) => {
                                                                        if (rowData.alterTrrm || rowData.alterTrrm === 0) {
                                                                            return rowData.alterTrrm.toFixed(3)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 3,
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
                                                                            return rowData.changeQty.toFixed(3)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 3,
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
                                                                            return rowData.changePrice.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                                                            return rowData.changePriceNoTax.toFixed(6)
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    precision: 6,
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
                                            dataIndex: 'firstName',
                                            key: 'firstName',
                                            width: 100
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
                                            dataIndex: 'secondName',
                                            key: 'secondName',
                                            width: 100
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
                            field: "form3",
                            name: "qnnForm",
                            title: "?????????????????????",
                            content: {
                                formConfig: [
                                    {
                                        type: 'qnnTable',
                                        field: 'BZJList',
                                        incToForm: true,
                                        qnnTableConfig: {
                                            fetchConfig: {
                                                apiName: 'getZxCtSuppliesMarginRatioList',
                                                otherParams: (obj) => {
                                                    return {
                                                        contractID: obj.tableFns.qnnForm.form.getFieldValue('zxCtSuppliesContractId')
                                                    }
                                                }
                                            },
                                            wrappedComponentRef: (me) => {
                                                this.tableThree = me;
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
                                                    name: 'addRow',//??????add del
                                                    icon: 'plus',//icon
                                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                                    label: '??????',
                                                    addRowDefaultData: () => {
                                                        let rowData = this.table.form.getFieldsValue();
                                                        return {
                                                            orgID: rowData.orgID,
                                                            contractID: rowData.zxCtSuppliesContractId
                                                        }
                                                    }
                                                },
                                                {
                                                    name: 'delRow',//??????add del
                                                    icon: 'delete',//icon
                                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                                    label: '??????'
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
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'contractID',
                                                        type: 'string',
                                                        hide: true,
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: `<div>?????????<span style='color: #ff4d4f'>*</span></div>`,
                                                        dataIndex: 'statisticsName',
                                                        key: 'statisticsName',
                                                        tdEdit: true
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
                                                        key: 'statisticsRate',
                                                        tdEdit: true
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
                                                        key: 'timeLimit',
                                                        tdEdit: true
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
                                                        key: 'backCondition',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????'
                                                    }
                                                },
                                            ]
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "table4",
                            name: "qnnTable",
                            title: "?????????",
                            content: {
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
                                                    hide: true
                                                },
                                                {
                                                    label: '????????????',
                                                    field: 'contractNo',
                                                    type: 'string',
                                                    span: 12,
                                                    required: true,
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
                                            if (obj.form.getFieldValue('zxCtSuppliesShopSettlementId')) {
                                                let params = { ...this.props }
                                                let rowData = obj.form.getFieldsValue();
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
                                                let rowData = obj.form.getFieldsValue();
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
                                            if (obj.form.getFieldValue('zxCtSuppliesShopSettlementId')) {
                                                let params = { ...this.props }
                                                let rowData = obj.form.getFieldsValue();
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
                                                let rowData = obj.form.getFieldsValue();
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
                                                if (obj.props.parentProps.form.getFieldValue('zxCtSuppliesShopSettlementId')) {
                                                    return {
                                                        apiName: 'getZxCtSuppliesShopSettlementItemListByConID',
                                                        otherParams: {
                                                            contractID: obj.props.parentProps.form.getFieldValue('contractID'),
                                                            zxCtSuppliesShopSettlementId: obj.props.parentProps.form.getFieldValue('zxCtSuppliesShopSettlementId')
                                                        },
                                                    }
                                                } else {
                                                    return {
                                                        apiName: 'getZxCtSuppliesLeaseSettlementItemListByConID',
                                                        otherParams: {
                                                            contractID: obj.props.parentProps.form.getFieldValue('contractID'),
                                                            zxCtSuppliesLeaseSettlementListId: obj.props.parentProps.form.getFieldValue('zxCtSuppliesLeaseSettlementListId')
                                                        }
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
                                                        align: 'center',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '???????????????',
                                                        dataIndex: 'thisAmt',
                                                        key: 'thisAmt',
                                                        width: 150,
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
                        },
                        {
                            field: "form6",
                            name: "qnnForm",
                            title: "????????????",
                            disabled: (obj) => { return obj.clickCb?.rowData.code7 === 'WZ' },
                            content: {
                                fetchConfig: (obj) => {
                                    return {
                                        apiName: 'getZxCtSuppliesContractDetail',
                                        otherParams: { zxCtSuppliesContractId: this.table?.form?.getFieldValue?.('zxCtSuppliesContractId') },
                                        success: ({ data, success }) => {
                                            if (success) {
                                                obj.form.setFieldsValue({
                                                    pp4_pp5: (data.pp4 ? data.pp4 : 0) - (data?.pp5 ? Number(data.pp5) : 0)
                                                })
                                            }
                                        }
                                    }
                                },
                                formConfig: [
                                    {
                                        type: "component",
                                        field: "component2",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ????????????
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: 'contractNames',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'contractNos',
                                        type: 'string',
                                        disabled: true,
                                        span: 8,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: "secondNames",
                                        type: 'string',
                                        span: 8,
                                        disabled: true,
                                    },
                                    {
                                        label: '???????????????????????????',
                                        field: 'alterContractSums',
                                        type: 'number',
                                        placeholder: '?????????',
                                        disabled: true,
                                        span: 8,
                                        precision: 6,
                                        initialValue: 0,
                                    },
                                    {
                                        type: "component",
                                        field: "component3",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ????????????
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'secondIDCode',
                                        type: 'selectByQnnTable',
                                        optionConfig: {
                                            label: 'lswldwWldwbh',
                                            value: 'lswldwWldwbh',
                                            linkageFields: {
                                                secondIDCodeName: 'lswldwDwmc',
                                                orgCertificate: 'lswldwSh',
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            if (!val) {
                                                obj.form.setFieldsValue({
                                                    secondIDCodeName: null,
                                                    orgCertificate: null,
                                                })
                                            } else {
                                                obj.form.setFieldsValue({
                                                    zjxmhtbBh: null,
                                                    pp5: null,
                                                    pp4_pp5: null,
                                                    accountUnitName: null,
                                                    accountUnitCode: null,
                                                    fiCtrState: null,
                                                    invoiceType: null,
                                                    ctrNature: null,
                                                    revenueDir: null,
                                                    keyCtr: null,
                                                    staCtr: null,
                                                    advanceRate: null,
                                                    progressRate: null,
                                                    completionRate: null,
                                                    ZJGCXMXMMC: null,
                                                    taxCountWay: null,
                                                })
                                            }
                                        },
                                        required: true,
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "lswldwWldwbh"
                                            },
                                            fetchConfig: {
                                                apiName: "getZxSaFiWldwList",
                                                otherParams: () => {
                                                    return {
                                                        lswldwDwmc: this.table?.form?.getFieldValue('secondName')
                                                    }
                                                }
                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "lswldwDwmc",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "lswldwSh",
                                                        title: "????????????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "lswldwWldwbh",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                            ]
                                        },
                                        placeholder: '?????????',
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'secondIDCodeName',
                                        type: 'string',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '????????????????????????',
                                        field: 'orgCertificate',
                                        type: 'string',
                                        placeholder: '?????????',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: "zjxmhtbMc",
                                        type: 'selectByQnnTable',
                                        span: 8,
                                        required: true,
                                        dependencies: ['secondIDCode'],
                                        dependenciesReRender: true,
                                        parent: 'secondIDCode',
                                        allowClear: false,
                                        optionConfig: {
                                            label: 'zjxmhtbHtmc',
                                            value: 'zjxmhtbHtmc',
                                            linkageFields: {
                                                zjxmhtbBh: 'zjxmhtbBh',//??????????????????
                                                pp5: 'klzfje',//??????????????????
                                                accountUnitName: 'zjxmhtbDwmc',//??????????????????
                                                accountUnitCode: 'zjxmhtbDwbh',//??????????????????
                                                fiCtrState: 'htzt',//??????????????????
                                                invoiceType: 'fplx',//????????????
                                                ctrNature: 'htxz',//????????????
                                                revenueDir: 'szfx',//????????????
                                                keyCtr: 'sfzd',//????????????
                                                staCtr: 'sfzs',//????????????
                                                advanceRate: 'yfkbl',//?????????
                                                progressRate: 'jdkbl',//??????
                                                completionRate: 'jgkbl',//??????
                                                ZJGCXMXMMC: 'zjgcxmXmmc',//????????????
                                                taxCountWay: 'jsff'//????????????
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            let DrawerData = obj.form.getFieldsValue()
                                            obj.form.setFieldsValue({
                                                pp4_pp5: DrawerData?.pp4 - DrawerData.pp5 >= 0 ? DrawerData.pp4 - DrawerData.pp5 : 0
                                            })
                                        },
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "zjxmhtbNm"
                                            },
                                            fetchConfig: (obj) => {
                                                let pageData = obj.props.qnnFormProps?.form?.getFieldsValue()
                                                return {
                                                    apiName: "getZxSaFiZjxmhtbInSpHookup",
                                                    otherParams: {
                                                        zjxmhtbKhnm: pageData?.secondIDCode,
                                                        zjxmhtbBh: pageData?.contractNo,
                                                        contractID: pageData?.zxCtSuppliesContractId,
                                                    }
                                                }

                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "zjxmhtbBh",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "zjxmhtbHtmc",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "klzfje",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "zjxmhtbDwmc",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                            ]
                                        },
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'zjxmhtbBh',
                                        type: 'string',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????(PM)',
                                        field: 'pp4',
                                        type: 'number',
                                        disabled: true,
                                        span: 8,
                                        initialValue: 0
                                    },
                                    {
                                        label: '????????????',
                                        field: 'accountUnitName',
                                        type: 'string',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'accountUnitCode',
                                        type: 'string',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '??????????????????(GS)',
                                        field: 'pp5',
                                        type: 'number',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: "accountDepName",
                                        type: 'selectByQnnTable',
                                        span: 8,
                                        required: true,
                                        dependencies: ['zjxmhtbMc'],
                                        dependenciesReRender: true,
                                        parent: 'zjxmhtbMc',
                                        optionConfig: {
                                            label: 'lsbmzdDwmc',
                                            value: 'lsbmzdDwmc',
                                            linkageFields: {
                                                accountDepCode: 'lsbmzdBmbh'//??????????????????
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            if (!val) {
                                                obj.form.setFieldsValue({
                                                    accountDepCode: null,
                                                })
                                            }
                                        },
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "lsbmzdDwmc"
                                            },
                                            fetchConfig: (obj) => {
                                                let pageData = obj.props.qnnFormProps?.form?.getFieldsValue()
                                                return {
                                                    apiName: "getZxSaFiBmzdList",
                                                    otherParams: {
                                                        lsbmzdDwbh: pageData?.accountUnitCode,
                                                    }
                                                }

                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "lsbmzdDwmc",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        dataIndex: "lsbmzdBmbh",
                                                        title: "??????????????????",
                                                    },
                                                    form: {
                                                        type: "string"
                                                    }
                                                },
                                            ]
                                        },
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'accountDepCode',
                                        type: 'string',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '?????????????????????',
                                        field: 'pp4_pp5',
                                        type: 'number',
                                        disabled: true,
                                        span: 8,
                                        initialValue: (obj) => {
                                            let drawerData = obj.form.getFieldsValue()
                                            return (drawerData?.pp4 ? drawerData.pp4 : 0) - (drawerData?.pp5 ? drawerData.pp5 : 0)
                                        }
                                    },
                                    {
                                        label: '????????????',
                                        field: 'ZJGCXMXMMC',
                                        type: 'string',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'taxCountWay',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        required: true,
                                        optionData: [
                                            { label: '??????????????????', value: '1' },
                                            { label: '??????????????????', value: '2' }
                                        ],
                                        placeholder: '?????????',
                                        onChange: (val, obj) => {
                                            if (val) {
                                                obj.form.setFieldsValue({
                                                    taxRate: null,
                                                })
                                            }
                                        },
                                        span: 8
                                    },
                                    {
                                        label: '??????%',
                                        field: 'taxRate',
                                        type: 'select',
                                        required: true,
                                        placeholder: '?????????',
                                        span: 8,
                                        optionConfig: {
                                            label: 'value',
                                            value: 'value',
                                        },
                                        dependencies: ['taxCountWay', 'invoiceType'],
                                        dependenciesReRender: true,
                                        disabled: (obj) => {
                                            let objData = obj?.form?.getFieldsValue()
                                            if (!objData.taxCountWay || !objData.invoiceType) return true
                                            return false
                                        },
                                        optionData: (obj) => {
                                            let objData = obj?.form?.getFieldsValue()
                                            if (objData?.invoiceType === '1') {
                                                return [{ value: '0' }]
                                            }
                                            if (objData.taxCountWay === '2') {
                                                return [{ value: '0' }, { value: '3' }, { value: '5' }]
                                            }
                                            if (objData.taxCountWay === '1') {
                                                return [
                                                    { value: '0' }, { value: '1' }, { value: '1.5' },
                                                    { value: '3' }, { value: '5' }, { value: '6' },
                                                    { value: '9' }, { value: '10' }, { value: '11' },
                                                    { value: '13' }, { value: '16' }, { value: '17' },
                                                ]
                                            }
                                        },
                                    },
                                    {
                                        label: '??????????????????',
                                        field: 'fiCtrState',
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '??????', value: '1' },
                                            { label: '??????', value: '2' },
                                            { label: '??????', value: '3' },
                                        ],
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'invoiceType',
                                        type: 'select',
                                        required: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '?????????????????????', value: '0' },
                                            { label: '?????????????????????', value: '1' }
                                        ],
                                        span: 8,
                                        onChange: (val, obj) => {
                                            if (val) {
                                                obj.form.setFieldsValue({
                                                    taxRate: null,
                                                })
                                            }
                                        },
                                    },
                                    {
                                        label: '??????',
                                        field: 'bz',
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
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'ctrNature',
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '????????????', value: '1' },
                                            { label: '??????', value: '2' },
                                            { label: '??????', value: '3' },
                                            { label: '??????', value: '4' },
                                            { label: '??????', value: '5' },
                                            { label: '??????', value: '6' },
                                            { label: '??????', value: '4' },
                                            { label: '??????', value: '5' },
                                            { label: '????????????', value: '6' },
                                        ],
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'revenueDir',
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '??????', value: '01' },
                                            { label: '??????', value: '02' },
                                        ],
                                        span: 8
                                    },
                                    {
                                        label: '????????????',
                                        field: 'keyCtr',
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '???', value: '0' },
                                            { label: '???', value: '1' }
                                        ],
                                        span: 4
                                    },
                                    {
                                        label: '????????????',
                                        field: 'staCtr',
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            { label: '???', value: '0' },
                                            { label: '???', value: '1' }
                                        ],
                                        span: 4
                                    },
                                    {
                                        label: '???????????????(%)',
                                        field: 'advanceRate',
                                        type: 'number',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '???????????????(%)',
                                        field: 'progressRate',
                                        type: 'number',
                                        disabled: true,
                                        span: 8
                                    },
                                    {
                                        label: '???????????????(%)',
                                        field: 'completionRate',
                                        type: 'number',
                                        disabled: true,
                                        span: 8
                                    },

                                    {
                                        type: "component",
                                        field: "component4",
                                        Component: obj => {
                                            return (
                                                <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                                                    ??????
                                                </div>
                                            );
                                        }
                                    },
                                    {
                                        label: '?????????',
                                        field: 'writer',
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: true,
                                        span: 6,
                                        initialValue: loginUser
                                    },
                                    {
                                        label: '????????????',
                                        field: 'writeDate',
                                        type: 'date',
                                        required: true,
                                        span: 6
                                    },
                                    {
                                        label: '?????????',
                                        field: 'doubleCheckPerson',
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: true,
                                        span: 6
                                    },
                                    {
                                        label: '????????????',
                                        field: 'doubleCheckDate',
                                        type: 'date',
                                        required: true,
                                        span: 6
                                    }
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
                                dataIndex: 'XTLULX',
                                key: 'XTLULX',
                                width: 120,
                                render: () => {
                                    return '????????????';
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
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'suppliesContractFileList',
                            key: 'suppliesContractFileList',
                            width: 100,
                            fixed:'right',
                            align:'center',
                            render: (val,obj) => {
                              if (obj?.suppliesContractFileList.length) {
                                return <SelectFilesDownLoad dataList={obj?.suppliesContractFileList} />
                              } else {
                                return '?????????'
                              }
                            }
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
                                                            width: 100,
                                                            render: (data, rowData) => {
                                                                if (rowData.contrTrrm || rowData.contrTrrm === 0) {
                                                                    return rowData.contrTrrm.toFixed(3)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 3,
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
                                                                    return rowData.qty.toFixed(3)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 3,
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
                                                                    return rowData.price.toFixed(6)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 6,
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
                                                                    return rowData.priceNoTax.toFixed(6)
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            precision: 6,
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
                                            label: "?????????????????????",
                                            field: "opinionField2",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "?????????????????????",
                                            field: "opinionField3",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "????????????????????????",
                                            field: "opinionField4",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "???????????????(????????????)??????",
                                            field: "opinionField5",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "????????????????????????",
                                            field: "opinionField6",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "?????????????????????",
                                            field: "opinionField7",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "????????????????????????",
                                            field: "opinionField8",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "?????????????????????",
                                            field: "opinionField9",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "??????????????????",
                                            field: "opinionField10",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "??????????????????????????????",
                                            field: "opinionField11",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "???????????????????????????",
                                            field: "opinionField12",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "????????????????????????",
                                            field: "opinionField13",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "???????????????",
                                            field: "opinionField14",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "?????????????????????",
                                            field: "opinionField15",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "?????????????????????",
                                            field: "opinionField16",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "???????????????????????????",
                                            field: "opinionField17",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "??????????????????",
                                            field: "opinionField18",
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
                                        },
                                        {
                                            type: "textarea",
                                            label: "????????????",
                                            field: "opinionField19",
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
                                <Operation {...this.props} flowData={flowData} />
                            </TabPane>
                        </Tabs>
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;