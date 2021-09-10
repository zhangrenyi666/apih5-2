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
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            drawerTitle: '修改',
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
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
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
                                    label: '保存',
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
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZxCtSuppliesContract',
                            },
                        },
                        {
                            label: "执行中/终止",
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
                                    content: '确定更改选中的数据吗?',
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
                            title: "合同信息",
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
                                                            }}>合同评审记录查看</Button>
                                                        </div>
                                                    </Col>
                                                </Row>
                                            );
                                        }
                                    },
                                    {
                                        label: '合同编号',
                                        field: "contractNo",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '签订时间',
                                        field: "signatureDate",
                                        type: 'date',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同名称',
                                        field: "contractName",
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同甲方',
                                        field: "firstName",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同乙方',
                                        field: "secondName",
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '摘要',
                                        field: "subject",
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '项目经理',
                                        field: "projectManager",
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '乙方代表',
                                        field: "secondPrincipal",
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '甲方联系电话',
                                        field: "firstUnitTel",
                                        type: 'phone',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同类别',
                                        field: "code7",
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'category_contr_type_CL'
                                            }
                                        },
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '物资来源',
                                        field: "materialSource",
                                        type: 'select',
                                        disabled: true,
                                        optionConfig: {
                                            label: 'itemName', //默认 label
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
                                        label: '付款约定',
                                        field: "paymentAssumpsit",
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '乙方（固话）',
                                        field: "secondUnitTel",
                                        type: 'specialPlane',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '乙方性质',
                                        field: "agent",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '生产厂家',
                                                value: '0'
                                            },
                                            {
                                                label: '经销商',
                                                value: '1'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '含税合同总价（万元）',
                                        field: "contractCost",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '不含税合同总价（万元）',
                                        field: "contractCostNoTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同税额（万元）',
                                        field: "contractCostTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '变更后含税合同金额（万元）',
                                        field: "alterContractSum",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '变更后不含税合同金额（万元）',
                                        field: "alterContractSumNoTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '变更后合同税额（万元）',
                                        field: "alterContractSumTax",
                                        type: 'number',
                                        disabled: true,
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '是否抵扣',
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
                                        label: '装运方式约定',
                                        field: "transferAssumpsit",
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '其他条款',
                                        field: "otherAssumpsit",
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '乙方（手机）',
                                        field: "pp1",
                                        type: 'phone',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '乙方代表身份证',
                                        field: "secondPrincipalIDCard",
                                        type: 'identity',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '乙方（传真）',
                                        field: "pp2",
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同开始日期',
                                        field: "planStartDate",
                                        type: 'date',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同结束日期',
                                        field: "planEndDate",
                                        type: 'date',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同工期（天）',
                                        field: "csTimeLimit",
                                        type: 'string',
                                        placeholder: '请输入',
                                        span: 12
                                    },
                                    {
                                        label: '合同录入类型',
                                        field: "apih5FlowStatus",
                                        disabled: true,
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '未审核',
                                                value: ''
                                            },
                                            {
                                                label: '审核中',
                                                value: '1'
                                            },
                                            {
                                                label: '已审核',
                                                value: '2'
                                            },
                                            {
                                                label: '退回',
                                                value: '3'
                                            }
                                        ],
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '合同状态',
                                        field: "contractStatus",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
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
                                                itemName: '终止',
                                                itemId: '3'
                                            },
                                            {
                                                itemName: '执行中',
                                                itemId: '1'
                                            }
                                        ],
                                        required: true,
                                        disabled: true,
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '结算情况',
                                        field: "settleType",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                label: '集中结算前终止',
                                                value: '0'
                                            },
                                            {
                                                label: '实际无结算',
                                                value: '1'
                                            },
                                            {
                                                label: '结算开始执行',
                                                value: '2'
                                            },
                                            {
                                                label: '已最终结算',
                                                value: '3'
                                            }
                                        ],
                                        disabled: true,
                                        required: true,
                                        placeholder: '请选择',
                                        span: 12
                                    },
                                    {
                                        label: '合同内容',
                                        field: "content",
                                        type: 'string',
                                        required: true,
                                        placeholder: '请输入',
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
                                        label: '备注',
                                        field: "remarks",
                                        type: 'string',
                                        placeholder: '请输入',
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
                                        label: '附件',
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
                            title: "合同清单明细",
                            content: {
                                formConfig: [
                                    {
                                        label: "合同不含税金额(万元)",
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
                                        label: "合同含税金额(万元)",
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
                                        label: "变更后不含税金额(万元)",
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
                                        label: "变更后含税金额(万元)",
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
                                                        title: '物资类别',
                                                        dataIndex: 'workType',
                                                        key: 'workType',
                                                        width: 150,
                                                        fixed: 'left'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资编码',
                                                        dataIndex: 'workNo',
                                                        key: 'workNo',
                                                        width: 150,
                                                        fixed: 'left'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '物资名称',
                                                        dataIndex: 'workName',
                                                        key: 'workName',
                                                        width: 150
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '规格型号',
                                                        dataIndex: 'spec',
                                                        key: 'spec',
                                                        width: 100
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '单位',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 100
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否网价',
                                                        dataIndex: 'isNetPrice',
                                                        key: 'isNetPrice',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '是';
                                                            } else {
                                                                return '否';
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
                                                        title: '租期单位',
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
                                                        title: '租期',
                                                        dataIndex: 'contrTrrm',
                                                        key: 'contrTrrm',
                                                        width: 100
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '数量',
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
                                                        title: '含税单价',
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
                                                        title: '含税金额',
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
                                                        title: '不含税单价',
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
                                                        title: '不含税金额',
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
                                                        title: '税率(%)',
                                                        dataIndex: 'taxRate',
                                                        key: 'taxRate',
                                                        width: 100,
                                                        type: 'select'
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
                                                                itemId: 'shuiLV'
                                                            }
                                                        },
                                                        placeholder: '请选择'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '是否抵扣',
                                                        dataIndex: 'isDeduct',
                                                        key: 'isDeduct',
                                                        width: 100,
                                                        render: (data) => {
                                                            if (data === '1') {
                                                                return '是';
                                                            } else {
                                                                return '否';
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
                                                        title: '变更后租期',
                                                        dataIndex: 'alterTrrm',
                                                        key: 'alterTrrm',
                                                        width: 120
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '变更后数量',
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
                                                        title: '变更后含税单价',
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
                                                        title: '变更后不含税单价',
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
                                                        title: '变更后含税金额',
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
                                                        title: '变更后不含税金额',
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
                                                        title: '变更日期',
                                                        dataIndex: 'changeDate',
                                                        key: 'changeDate',
                                                        width: 100,
                                                        format: 'YYYY-MM-DD'
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '备注',
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
                            title: "补充协议",
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
                                        name: 'edit',//内置add del
                                        icon: 'edit',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '修改',
                                        drawerTitle: '修改',
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
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
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
                                                label: '保存',
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
                                        title: "基础信息",
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
                                                    label: '补充协议编号',
                                                    field: "contractNo",
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '补充协议名称',
                                                    field: "pp3",
                                                    type: 'string',
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '合同名称',
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
                                                    placeholder: '请选择',
                                                    span: 12
                                                },
                                                {
                                                    label: '合同签订人',
                                                    field: "agent",
                                                    type: 'string',
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '开工日期',
                                                    field: "startDate",
                                                    type: 'date',
                                                    placeholder: '请选择',
                                                    span: 12
                                                },
                                                {
                                                    label: '竣工日期',
                                                    field: "endDate",
                                                    type: 'date',
                                                    placeholder: '请选择',
                                                    span: 12
                                                },
                                                {
                                                    label: '合同工期',
                                                    field: "csTimeLimit",
                                                    type: 'string',
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '合同类型',
                                                    field: "contractType",
                                                    type: 'string',
                                                    disabled: true,
                                                    initialValue: '物资管理类补充协议',
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '甲方名称',
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
                                                    placeholder: '请选择',
                                                    span: 12
                                                },
                                                {
                                                    label: '乙方名称',
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
                                                    placeholder: '请选择',
                                                    span: 12
                                                },
                                                {
                                                    label: '含税合同金额(万元)',
                                                    field: "contractCost",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '不含税合同金额(万元)',
                                                    field: "contractCostNoTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '合同税额(万元)',
                                                    field: "contractCostTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '本期含税增减金额(万元)',
                                                    field: "pp4",
                                                    type: 'string',
                                                    disabled: true,
                                                    initialValue: "0",
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '本期不含税增减金额(万元)',
                                                    field: "pp4NoTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '本期增减税额(万元)',
                                                    field: "pp4Tax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '变更后含税金额(万元)',
                                                    field: "alterContractSum",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '变更后不含税金额(万元)',
                                                    field: "alterContractSumNoTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '变更后税额(万元)',
                                                    field: "alterContractSumTax",
                                                    type: 'number',
                                                    disabled: true,
                                                    initialValue: 0,
                                                    precision: 6,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '是否抵扣',
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
                                                    label: '合同类别',
                                                    field: "code7",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'itemName', //默认 label
                                                        value: 'itemId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getBaseCodeSelect',
                                                        otherParams: {
                                                            itemId: 'category_contr_type_CL'
                                                        }
                                                    },
                                                    placeholder: '请选择',
                                                    disabled: true,
                                                    span: 12
                                                },
                                                {
                                                    label: '物资来源',
                                                    field: "materialSource",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'itemName', //默认 label
                                                        value: 'itemId'
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getBaseCodeSelect',
                                                        otherParams: {
                                                            itemId: 'category_contract_materialSource'
                                                        }
                                                    },
                                                    disabled: true,
                                                    placeholder: '请选择',
                                                    span: 12
                                                },
                                                // {
                                                //     label: '采购方式',
                                                //     field: "shopWay",
                                                //     type: 'select',
                                                //     optionConfig: {
                                                //         label: 'label', //默认 label
                                                //         value: 'value'
                                                //     },
                                                //     optionData: [
                                                //         {
                                                //             label: '招标采购',
                                                //             value: '0'
                                                //         },
                                                //         {
                                                //             label: '云电商采购',
                                                //             value: '1'
                                                //         },
                                                //         {
                                                //             label: '其他采购',
                                                //             value: '2'
                                                //         }
                                                //     ],
                                                //     placeholder: '请选择',
                                                //     disabled: true,
                                                //     span: 12
                                                // },
                                                // {
                                                //     label: '采购编号',
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
                                                //     placeholder: '请输入',
                                                //     span: 12
                                                // },
                                                {
                                                    label: '合同内容',
                                                    field: 'content',
                                                    type: 'textarea',
                                                    placeholder: '请输入',
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
                                                    label: '备注',
                                                    field: 'remarks',
                                                    type: 'textarea',
                                                    placeholder: '请输入',
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
                                                    label: '附件',
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
                                        title: "清单",
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
                                                    label: '补充协议编号',
                                                    field: "contractNo",
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '批复单位',
                                                    field: "replyUnit",
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'label',
                                                        value: 'value'
                                                    },
                                                    optionData: [
                                                        {
                                                            label: '项目',
                                                            value: '0'
                                                        },
                                                        {
                                                            label: '公司',
                                                            value: '1'
                                                        },
                                                        {
                                                            label: '局',
                                                            value: '2'
                                                        }
                                                    ],
                                                    disabled: true,
                                                    placeholder: '请选择',
                                                    span: 12
                                                },
                                                {
                                                    label: '批复日期',
                                                    field: "replyDate",
                                                    type: 'date',
                                                    disabled: true,
                                                    placeholder: '请输入',
                                                    span: 12
                                                },
                                                {
                                                    label: '变更内容',
                                                    field: 'alterContent',
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '请输入',
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
                                                    label: '变更原因',
                                                    field: 'alterReason',
                                                    type: 'string',
                                                    disabled: true,
                                                    placeholder: '请输入',
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
                                                                    title: '变更类型',
                                                                    dataIndex: 'alterType',
                                                                    key: 'alterType',
                                                                    width: 100,
                                                                    type: 'select',
                                                                    fixed: 'left'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: {
                                                                        label: 'label', //默认 label
                                                                        value: 'value'
                                                                    },
                                                                    optionData: [
                                                                        {
                                                                            label: '新增',
                                                                            value: '1'
                                                                        },
                                                                        {
                                                                            label: '修改',
                                                                            value: '2'
                                                                        }
                                                                    ],
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资类别',
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
                                                                        label: 'catName', //默认 label
                                                                        value: 'id'
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: 'getZxSkResCategoryMaterialsList',
                                                                        otherParams: {
                                                                            parentID: '0002'
                                                                        }
                                                                    },
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资编码',
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
                                                                                label: 'resCode', //默认 label
                                                                                value: 'id'
                                                                            }
                                                                        } else {
                                                                            return {
                                                                                label: 'workNo', //默认 label
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
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资名称',
                                                                    dataIndex: 'workName',
                                                                    key: 'workName',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资规格',
                                                                    dataIndex: 'spec',
                                                                    key: 'spec',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '单位',
                                                                    dataIndex: 'unit',
                                                                    key: 'unit',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '是否网价',
                                                                    dataIndex: 'isNetPrice',
                                                                    key: 'isNetPrice',
                                                                    width: 100,
                                                                    tdEdit: true,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '是';
                                                                        } else {
                                                                            return '否';
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
                                                                    title: '数量',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '含税合同单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '含税合同金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '不含税合同单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '不含税合同金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '税额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '税率(%)',
                                                                    dataIndex: 'taxRate',
                                                                    key: 'taxRate',
                                                                    width: 100,
                                                                    type: 'select'
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
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '是否抵扣',
                                                                    dataIndex: 'isDeduct',
                                                                    key: 'isDeduct',
                                                                    width: 100,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '是';
                                                                        } else {
                                                                            return '否';
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '已结算数量',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后数量',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后含税单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后不含税单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后含税金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后不含税金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后税额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '备注',
                                                                    dataIndex: 'remarks',
                                                                    key: 'remarks',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
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
                                                                    title: '变更类型',
                                                                    dataIndex: 'alterType',
                                                                    key: 'alterType',
                                                                    width: 100,
                                                                    type: 'select',
                                                                    fixed: 'left'
                                                                },
                                                                form: {
                                                                    type: 'select',
                                                                    optionConfig: {
                                                                        label: 'label', //默认 label
                                                                        value: 'value'
                                                                    },
                                                                    optionData: [
                                                                        {
                                                                            label: '新增',
                                                                            value: '1'
                                                                        },
                                                                        {
                                                                            label: '修改',
                                                                            value: '2'
                                                                        }
                                                                    ],
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资类别',
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
                                                                        label: 'catName', //默认 label
                                                                        value: 'id'
                                                                    },
                                                                    fetchConfig: {
                                                                        apiName: 'getZxSkResCategoryMaterialsList',
                                                                        otherParams: {
                                                                            parentID: '0002'
                                                                        }
                                                                    },
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资编码',
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
                                                                                label: 'resCode', //默认 label
                                                                                value: 'id'
                                                                            }
                                                                        } else {
                                                                            return {
                                                                                label: 'workNo', //默认 label
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
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资名称',
                                                                    dataIndex: 'workName',
                                                                    key: 'workName',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '物资规格',
                                                                    dataIndex: 'spec',
                                                                    key: 'spec',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '单位',
                                                                    dataIndex: 'unit',
                                                                    key: 'unit',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '是否网价',
                                                                    dataIndex: 'isNetPrice',
                                                                    key: 'isNetPrice',
                                                                    width: 100,
                                                                    tdEdit: true,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '是';
                                                                        } else {
                                                                            return '否';
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
                                                                    title: '租期',
                                                                    dataIndex: 'contrTrrm',
                                                                    key: 'contrTrrm',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '数量',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '含税合同单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '含税合同金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '不含税合同单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '不含税合同金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '税额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '税率(%)',
                                                                    dataIndex: 'taxRate',
                                                                    key: 'taxRate',
                                                                    width: 100,
                                                                    type: 'select'
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
                                                                    placeholder: '请选择'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '是否抵扣',
                                                                    dataIndex: 'isDeduct',
                                                                    key: 'isDeduct',
                                                                    width: 100,
                                                                    render: (data) => {
                                                                        if (data === '1') {
                                                                            return '是';
                                                                        } else {
                                                                            return '否';
                                                                        }
                                                                    }
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '已结算数量',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '租期单位',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后租期',
                                                                    dataIndex: 'alterTrrm',
                                                                    key: 'alterTrrm',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'number',
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后数量',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后含税单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后不含税单价',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后含税金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后不含税金额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '变更后税额',
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
                                                                    placeholder: '请输入'
                                                                }
                                                            },
                                                            {
                                                                table: {
                                                                    title: '备注',
                                                                    dataIndex: 'remarks',
                                                                    key: 'remarks',
                                                                    width: 100
                                                                },
                                                                form: {
                                                                    type: 'string',
                                                                    placeholder: '请输入'
                                                                }
                                                            }
                                                        ]
                                                    }
                                                },
                                                {
                                                    label: '附件',
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
                                            title: '补充协议编号',
                                            dataIndex: 'contractNo',
                                            key: 'contractNo',
                                            width: 150,
                                            fixed: 'left'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '补充协议名称',
                                            dataIndex: 'pp3',
                                            key: 'pp3',
                                            width: 150,
                                            fixed: 'left',
                                            onClick: 'detail'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同名称',
                                            dataIndex: 'contractName',
                                            key: 'contractName',
                                            width: 150
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同类型',
                                            dataIndex: 'contractType',
                                            key: 'contractType',
                                            width: 100
                                        }
                                    },
                                    {
                                        table: {
                                            title: '甲方名称',
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
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '乙方名称',
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
                                            placeholder: '请选择'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同签订人',
                                            dataIndex: 'agent',
                                            key: 'agent',
                                            width: 100,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '含税合同金额（万元）',
                                            dataIndex: 'contractCost',
                                            key: 'contractCost',
                                            width: 180,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '本期含税增减金额（万元）',
                                            dataIndex: 'pp4',
                                            key: 'pp4',
                                            width: 200
                                        }
                                    },
                                    {
                                        table: {
                                            title: '变更后含税金额（万元）',
                                            dataIndex: 'alterContractSum',
                                            key: 'alterContractSum',
                                            width: 180
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否抵扣',
                                            dataIndex: 'isDeduct',
                                            key: 'isDeduct',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '1') {
                                                    return '是';
                                                } else {
                                                    return '否';
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '开工日期',
                                            dataIndex: 'startDate',
                                            key: 'startDate',
                                            width: 100,
                                            format: 'YYYY-MM-DD'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '竣工日期',
                                            dataIndex: 'endDate',
                                            key: 'endDate',
                                            width: 100,
                                            format: 'YYYY-MM-DD'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '发起人',
                                            dataIndex: 'beginPer',
                                            key: 'beginPer',
                                            width: 100
                                        }
                                    },
                                    {
                                        table: {
                                            title: '评审状态',
                                            dataIndex: 'apih5FlowStatus',
                                            key: 'apih5FlowStatus',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '2') {
                                                    return '评审通过';
                                                } else {
                                                    return '--';
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '物资来源',
                                            dataIndex: 'materialSource',
                                            key: 'materialSource',
                                            width: 120,
                                            type: 'select'
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
                                                    itemId: 'category_contract_materialSource'
                                                }
                                            },
                                            placeholder: '请选择'
                                        }
                                    }
                                ]
                            }
                        },
                        {
                            field: "table3",
                            name: "qnnTable",
                            title: "保证金比例设置",
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
                                        name: 'add',//内置add del
                                        icon: 'plus',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '新增',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
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
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'addZxCtSuppliesMarginRatio',
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'edit',//内置add del
                                        icon: 'edit',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '修改',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
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
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'updateZxCtSuppliesMarginRatio',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        hide: (obj) => {
                                            if (obj.props.clickCb.rowInfo.name === 'detail') {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        fetchConfig: {//ajax配置
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
                                            title: '保证金',
                                            dataIndex: 'statisticsName',
                                            key: 'statisticsName',
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '扣除比例(%)',
                                            dataIndex: 'statisticsRate',
                                            key: 'statisticsRate'
                                        },
                                        form: {
                                            type: 'number',
                                            min: 0,
                                            max: 100,
                                            precision: 2,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '期限',
                                            dataIndex: 'timeLimit',
                                            key: 'timeLimit'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '返还条件',
                                            dataIndex: 'backCondition',
                                            key: 'backCondition'
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    },
                                ]
                            }
                        },
                        {
                            field: "table4",
                            name: "qnnTable",
                            title: "结算单",
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
                                        title: "基础信息",
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
                                                    label: '项目名称',
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
                                                    label: '合同编号',
                                                    field: "contractID",
                                                    type: 'select',
                                                    showSearch: true,
                                                    span: 12,
                                                    required: true,
                                                    placeholder: '请选择',
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
                                                    label: '结算单编号',
                                                    field: "billNo",
                                                    type: 'string',
                                                    span: 12,
                                                    disabled: true
                                                },
                                                {
                                                    label: '合同名称',
                                                    field: "contractName",
                                                    type: 'string',
                                                    span: 12,
                                                    disabled: true,
                                                },
                                                {
                                                    label: '签认单编号',
                                                    field: "signedNo",
                                                    type: 'string',
                                                    span: 12,
                                                    disabled: true
                                                },
                                                {
                                                    label: '合同乙方',
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
                                                    label: '结算期次',
                                                    field: "periodDate",
                                                    type: 'month',
                                                    placeholder: '请选择',
                                                    required: true,
                                                    span: 12
                                                },
                                                {
                                                    label: '结算类型',
                                                    field: "billType",
                                                    type: 'select',
                                                    required: true,
                                                    placeholder: '请选择',
                                                    span: 12,
                                                    optionConfig: {
                                                        label: 'label',
                                                        value: 'value',
                                                    },
                                                    optionData: (obj) => {
                                                        if (obj.form.getFieldValue('isFished') === '1' && obj.form.getFieldValue('settleType') === '3') {
                                                            return [
                                                                { label: '返还保证金', value: '2' }
                                                            ]
                                                        } else {
                                                            return [
                                                                { label: '中期', value: '0' },
                                                                { label: '最终', value: '1' },
                                                            ]
                                                        }
                                                    },
                                                },
                                                {
                                                    label: '是否抵扣',
                                                    field: "isDeduct",
                                                    type: 'radio',
                                                    disabled: true,
                                                    optionData: [
                                                        { label: '是', value: '1' },
                                                        { label: '否', value: '0' }
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
                                                    label: '是否完工后结算',
                                                    field: "isFished",
                                                    type: 'radio',
                                                    optionData: [
                                                        { label: '是', value: '1' },
                                                        { label: '否', value: '0' }
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
                                                    label: '是否首次结算',
                                                    field: "isFirst",
                                                    type: 'radio',
                                                    optionData: [
                                                        { label: '是', value: '1' },
                                                        { label: '否', value: '0' }
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
                                                    label: '结算单初始化顺序号',
                                                    field: "initSerialNumber",
                                                    required: true,
                                                    span: 12,
                                                    placeholder: '请输入',
                                                    type: 'string'
                                                },
                                                {
                                                    label: '填报人',
                                                    field: "reportPerson",
                                                    type: 'string',
                                                    span: 12,
                                                    placeholder: '请输入'
                                                },
                                                {
                                                    label: '计算人',
                                                    field: "countPerson",
                                                    type: 'string',
                                                    span: 12,
                                                    placeholder: '请输入',
                                                },
                                                {
                                                    label: '复合人',
                                                    field: "reCountPerson",
                                                    type: 'string',
                                                    span: 12,
                                                    placeholder: '请输入',
                                                },
                                                {
                                                    label: '单据开始时间',
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
                                                    label: '单据结束时间',
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
                                                    label: '填报日期',
                                                    field: "reportDate",
                                                    span: 12,
                                                    type: 'date',
                                                    required: true,
                                                },
                                                {
                                                    label: '业务日期',
                                                    field: "businessDate",
                                                    span: 12,
                                                    type: 'date',
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
                                                    label: '结算内容',
                                                    field: 'content',
                                                    type: 'textarea',
                                                    placeholder: '请输入',
                                                    span: 12,
                                                },
                                                {
                                                    label: '物资质量评价',
                                                    field: 'appraisal',
                                                    type: 'textarea',
                                                    placeholder: '请输入',
                                                    span: 12,
                                                },
                                                {
                                                    label: '说明',
                                                    field: 'cwStatusRemark',
                                                    type: 'textarea',
                                                    span: 12,
                                                    placeholder: '请输入',
                                                },
                                                {
                                                    label: '备注信息',
                                                    field: 'remarks',
                                                    type: 'textarea',
                                                    span: 12,
                                                    placeholder: '请输入',
                                                },
                                                {
                                                    label: '附件',
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
                                        title: "清单结算",
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
                                        title: "支付项结算",
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
                                        title: "统计项",
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
                                                        title: '统计项',
                                                        dataIndex: 'statisticsName',
                                                        key: 'statisticsName',
                                                        width: 100,
                                                        type: 'string',
                                                        align: 'center',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '本期（元）',
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
                                                        title: '开累（元）',
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
                                            title: '结算单编号',
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
                                            title: '项目名称',
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
                                            title: '结算期次',
                                            dataIndex: 'periodDate',
                                            key: 'periodDate',
                                            width: 120,
                                            format: 'YYYY-MM'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '结算类型',
                                            dataIndex: 'billType',
                                            key: 'billType',
                                            width: 120,
                                            render: (h) => {
                                                if (h === '0') {
                                                    return '中期'
                                                } else if (h === '1') {
                                                    return '最终'
                                                } else if (h === '2') {
                                                    return '返还保证金'
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同名称',
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
                                            title: '合同乙方',
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
                                            title: '本期结算含税金额',
                                            dataIndex: 'thisAmt',
                                            key: 'thisAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '开累结算含税金额',
                                            dataIndex: 'totalAmt',
                                            key: 'totalAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '本期应支付含税金额',
                                            dataIndex: 'thisPayAmt',
                                            key: 'thisPayAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '开累应支付含税金额',
                                            dataIndex: 'totalPayAmt',
                                            key: 'totalPayAmt',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '重新评审次数',
                                            dataIndex: 'notPassNum',
                                            key: 'notPassNum',
                                            width: 150,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '结算期限开始时间',
                                            dataIndex: 'beginDate',
                                            key: 'beginDate',
                                            width: 150,
                                            format: "YYYY-MM-DD"
                                        }
                                    },
                                    {
                                        table: {
                                            title: '结算期限结束时间',
                                            dataIndex: 'endDate',
                                            key: 'endDate',
                                            width: 150,
                                            format: "YYYY-MM-DD"
                                        }
                                    },
                                    {
                                        table: {
                                            title: '业务日期',
                                            dataIndex: 'businessDate',
                                            key: 'businessDate',
                                            width: 100,
                                            format: "YYYY-MM-DD"
                                        }
                                    },
                                    {
                                        table: {
                                            title: '发起人',
                                            dataIndex: 'createUserName',
                                            key: 'createUserName',
                                            width: 100,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '评审状态',
                                            dataIndex: 'apih5FlowStatus',
                                            key: 'apih5FlowStatus',
                                            width: 100,
                                            fixed: 'right',
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
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同甲方',
                                dataIndex: 'firstName',
                                key: 'firstName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同乙方',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 150,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同类别',
                                dataIndex: 'code7',
                                key: 'code7',
                                width: 100,
                                type: 'select'
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
                                        itemId: 'category_contr_type_CL'
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '物资来源',
                                dataIndex: 'materialSource',
                                key: 'materialSource',
                                filter: true,
                                width: 100,
                                type: 'select'
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
                                        itemId: 'category_contract_materialSource'
                                    }
                                },
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '含税合同金额(万元)',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 150,
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '变更后含税合同金额(万元)',
                                // dataIndex: 'changeContractSum',
                                // key: 'changeContractSum',
                                dataIndex: 'alterContractSum',
                                key: 'alterContractSum',
                                width: 200,
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '是否抵扣',
                                dataIndex: 'isDeduct',
                                key: 'isDeduct',
                                width: 100,
                                render: (data) => {
                                    if (data === '1') {
                                        return '是';
                                    } else {
                                        return '否';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同工期',
                                dataIndex: 'csTimeLimit',
                                key: 'csTimeLimit',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同录入类型',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                width: 120,
                                render: (data) => {
                                    if (!data) {
                                        return '未审核';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '评审通过';
                                    } else if (data === '3') {
                                        return '退回';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '合同状态',
                                dataIndex: 'contractStatus',
                                key: 'contractStatus',
                                type: 'select',
                                width: 100,
                                fixed: 'right'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName', //默认 label
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
                                        itemName: '终止',
                                        itemId: '3'
                                    },
                                    {
                                        itemName: '执行中',
                                        itemId: '1'
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '结算情况',
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
                                        label: '集中结算前终止',
                                        value: '0'
                                    },
                                    {
                                        label: '实际无结算',
                                        value: '1'
                                    },
                                    {
                                        label: '结算开始执行',
                                        value: '2'
                                    },
                                    {
                                        label: '已最终结算',
                                        value: '3'
                                    }
                                ],
                                placeholder: '请选择'
                            }
                        }
                    ]}
                />
                {
                    visible ? <Drawer
                        title={'合同评审详情'}
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
                                    取消
                              </Button>
                            </div>
                        }
                    >
                        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                            <TabPane tab="基础信息" key="1">
                                <QnnForm
                                    {...this.props}
                                    match={this.props.match}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    data={flowData?.apiData ? JSON.parse(flowData.apiData) : null}
                                    formConfig={[
                                        {
                                            label: '合同编号',
                                            field: "contractNo",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '合同名称',
                                            field: "contractName",
                                            type: 'string',
                                            required: true,
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '合同类型',
                                            field: "contractType",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '甲方名称',
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
                                            placeholder: '请选择',
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
                                            label: '乙方名称',
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
                                            placeholder: '请选择',
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
                                            label: '合同签订人',
                                            field: "agent",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '含税合同金额(万元)',
                                            field: "contractCost",
                                            type: 'number',
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '不含税合同金额(万元)',
                                            field: "contractCostNoTax",
                                            type: 'number',
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '合同税额(万元)',
                                            field: "contractCostTax",
                                            type: 'number',
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '是否抵扣',
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
                                            label: '合同工期',
                                            field: "csTimeLimit",
                                            type: 'string',
                                            disabled: true,
                                            placeholder: '请输入',
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
                                            label: '合同类别',
                                            field: "code7",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName', //默认 label
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getBaseCodeSelect',
                                                otherParams: {
                                                    itemId: 'category_contr_type_CL'
                                                }
                                            },
                                            disabled: true,
                                            placeholder: '请选择',
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
                                            label: '物资来源',
                                            field: "materialSource",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'itemName', //默认 label
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
                                            placeholder: '请选择',
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
                                            label: '物资类别',
                                            field: "resCategoryID",
                                            type: 'select',
                                            optionConfig: {
                                                label: 'catName', //默认 label
                                                value: 'id'
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxSkResCategoryMaterialsList',
                                                otherParams: {
                                                    parentID: '0002'
                                                }
                                            },
                                            disabled: true,
                                            placeholder: '请选择',
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
                                            label: '合同内容',
                                            field: 'content',
                                            type: 'textarea',
                                            placeholder: '请输入',
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
                                            label: '备注',
                                            field: 'remarks',
                                            type: 'textarea',
                                            placeholder: '请输入',
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
                                                            title: '物资类别',
                                                            dataIndex: 'workType',
                                                            key: 'workType',
                                                            width: 150,
                                                            fixed: 'left'
                                                        },
                                                        form: {
                                                            type: 'select',
                                                            optionConfig: {
                                                                label: 'catName', //默认 label
                                                                value: 'id'
                                                            },
                                                            fetchConfig: {
                                                                apiName: 'getZxSkResCategoryMaterialsList',
                                                                otherParams: {
                                                                    parentID: '0002'
                                                                }
                                                            },
                                                            placeholder: '请选择'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '物资编码',
                                                            dataIndex: 'workNo',
                                                            key: 'workNo',
                                                            width: 150,
                                                            fixed: 'left'
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '物资名称',
                                                            dataIndex: 'workName',
                                                            key: 'workName',
                                                            width: 150
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '物资规格',
                                                            dataIndex: 'spec',
                                                            key: 'spec',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '单位',
                                                            dataIndex: 'unit',
                                                            key: 'unit',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '是否网价',
                                                            dataIndex: 'isNetPrice',
                                                            key: 'isNetPrice',
                                                            width: 100,
                                                            render: (data) => {
                                                                if (data === '1') {
                                                                    return '是';
                                                                } else {
                                                                    return '否';
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
                                                            title: '租期单位',
                                                            dataIndex: 'rentUnit',
                                                            key: 'rentUnit',
                                                            width: 150
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
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
                                                            title: '租期',
                                                            dataIndex: 'contrTrrm',
                                                            key: 'contrTrrm',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '数量',
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
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '含税合同单价',
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
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '含税合同金额',
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
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '不含税合同单价',
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
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '不含税合同金额',
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
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '税额',
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
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '税率(%)',
                                                            dataIndex: 'taxRate',
                                                            key: 'taxRate',
                                                            width: 100,
                                                            type: 'select'
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
                                                                    itemId: 'shuiLV'
                                                                }
                                                            },
                                                            placeholder: '请选择'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '是否抵扣',
                                                            dataIndex: 'isDeduct',
                                                            key: 'isDeduct',
                                                            width: 100,
                                                            render: (data) => {
                                                                if (data === '1') {
                                                                    return '是';
                                                                } else {
                                                                    return '否';
                                                                }
                                                            }
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    },
                                                    {
                                                        table: {
                                                            title: '备注',
                                                            dataIndex: 'remarks',
                                                            key: 'remarks',
                                                            width: 100
                                                        },
                                                        form: {
                                                            type: 'string',
                                                            placeholder: '请输入'
                                                        }
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            type: "textarea",
                                            label: "审批意见",
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
                            <TabPane tab="操作历史" key="2">
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