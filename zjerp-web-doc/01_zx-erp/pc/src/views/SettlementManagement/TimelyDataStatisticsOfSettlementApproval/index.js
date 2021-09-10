import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg, Drawer } from "antd";
import Detail from '../EngineeringSettlement/detail';
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'id',
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
            xs: { span: 6 },
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 18 },
            sm: { span: 18 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                orgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
                settlementApprovalTimelinessFlag: '0'
            },
            visible: false,
            flowData:null
        }
    }
    onClose = () => {
        this.setState({
            visible: false,
        });
    };
    render() {
        const { formData, visible, flowData, departmentId } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    data={formData}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 7 },
                            sm: { span: 7 }
                        },
                        wrapperCol: {
                            xs: { span: 17 },
                            sm: { span: 17 }
                        }
                    }}
                    formConfig={[
                        {
                            label: '项目名称',
                            field: 'orgID',
                            type: 'select',
                            showSearch: true,
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId'
                            },
                            fetchConfig: {
                                apiName: 'getSysProjectBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            allowClear:false,
                            span: 6
                        },
                        {
                            type: 'rangeDate',
                            label: '期次',
                            field: 'periodTime',
                            span: 6
                        },
                        {
                            label: '统计方式',
                            field: 'settlementApprovalTimelinessFlag',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                { label: '全部', value: '0' },
                                { label: '项目级结算主管部门审核时长超过3天', value: '1' },
                                { label: '公司级结算主管部门审核时长超过3天', value: '2' },
                                { label: '总部级结算主管部门审核时长超过3天', value: '3' },
                                { label: '所有层级审批周期大于7天', value: '4' },
                                { label: '当月结算在自然月内我未完成审批', value: '5' }
                            ],
                            allowClear:false,
                            span: 6
                        },
                        {
                            label: '合同名称',
                            field: 'contractName',
                            type: 'string',
                            span: 6
                        },
                        {
                            label: '合同乙方',
                            field: 'secondName',
                            type: 'string',
                            span: 6
                        },
                        {
                            label: '结算单编号',
                            field: 'billNo',
                            type: 'string',
                            span: 6
                        },
                        {
                            label: '结算类型',
                            field: 'billType',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                { label: '返还保证金', value: '2' },
                                { label: '中期', value: '0' },
                                { label: '最终', value: '1' },
                            ],
                            span: 6
                        },
                        {
                            label: '审批状态',
                            field: 'apih5FlowStatus',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                { label: '未审核', value: '-1' },
                                { label: '待提交', value: '0' },
                                { label: '审核中', value: '1' },
                                { label: '评审通过', value: '2' },
                                { label: '退回', value: '3' },
                            ],
                            span: 6
                        },
                        {
                            type: 'rangeDate',
                            label: '填报日期',
                            field: 'approvalTime',
                            span: 6
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}>
                                        <Button type="primary" onClick={() => {
                                            let value = this.form.form.getFieldsValue();
                                            if (value?.periodTime?.length) {
                                                value.beginDate = moment(value.periodTime[0]).startOf('date').valueOf();
                                                value.endDate = moment(value.periodTime[1]).endOf('date').valueOf();
                                            }
                                            if (value?.approvalTime?.length) {
                                                value.flowBeginDate = moment(value.approvalTime[0]).startOf('date').valueOf();
                                                value.flowEndDate = moment(value.approvalTime[1]).endOf('date').valueOf();
                                            }
                                            delete value.periodTime;
                                            delete value.approvalTime;
                                            this.setState({
                                                formData: value
                                            })
                                        }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            if(this.table.getSelectedRows().length === 1){
                                                this.setState({
                                                    visible:true
                                                })
                                            }else{
                                                Msg.error('请选择一条数据查看!');
                                            }
                                        }}>详细</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.periodTime?.length) {
                                                        value.beginDate = moment(value.periodTime[0]).startOf('date').valueOf();
                                                        value.endDate = moment(value.periodTime[1]).endOf('date').valueOf();
                                                    }
                                                    if (value?.approvalTime?.length) {
                                                        value.flowBeginDate = moment(value.approvalTime[0]).startOf('date').valueOf();
                                                        value.flowEndDate = moment(value.approvalTime[1]).endOf('date').valueOf();
                                                    }
                                                    delete value.periodTime;
                                                    delete value.approvalTime;
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    value.reportUrl = ureport;
                                                    value.accessToken = access_token;
                                                    this.props.myFetch('exportZxSaReturnSettlement', value).then(
                                                        ({ success, message, data }) => {
                                                            if (success) {
                                                                window.open(data);
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    );
                                                }
                                            })
                                        }}>导出</Button>
                                    </div>
                                );
                            },
                            span: 6
                        }
                    ]}
                />
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
                        apiName: 'getZxSaProjectSettleAuditEarlyWarningList',
                        otherParams: { ...formData }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'id',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '公司名称',
                                dataIndex: 'comName',
                                width: 150,
                                key: 'comName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '结算单编号',
                                dataIndex: 'billNo',
                                width: 150,
                                key: 'billNo',
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 150,
                                key: 'orgName',
                                fixed: 'left'
                            },
                            form: {
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '结算期次',
                                dataIndex: 'period',
                                width: 100,
                                key: 'period'
                            },
                            form: {
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '合同编号',
                                field: 'contractNo',
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '合同名称',
                                dataIndex: 'contractName',
                                width: 150,
                                key: 'contractName'
                            },
                            form: {
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '合同乙方',
                                dataIndex: 'secondName',
                                width: 150,
                                key: 'secondName'
                            },
                            form: {
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '结算类型',
                                dataIndex: 'billType',
                                width: 100,
                                key: 'billType',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    { label: '返还保证金', value: '2' },
                                    { label: '中期', value: '0' },
                                    { label: '最终', value: '1' },
                                ],
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '结算期限开始时间',
                                dataIndex: 'beginDate',
                                width: 130,
                                key: 'beginDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '结算期限结束时间',
                                dataIndex: 'endDate',
                                width: 130,
                                key: 'endDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                spanForm: 12
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '本期结算金额(元)',
                                dataIndex: 'thisAmt',
                                width: 130,
                                key: 'thisAmt'
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '开累结算金额(元)',
                                dataIndex: 'totalAmt',
                                width: 130,
                                key: 'totalAmt'
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '本期应支付金额(元)',
                                dataIndex: 'thisPayAmt',
                                width: 140,
                                key: 'thisPayAmt'
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '开累应支付金额(元)',
                                dataIndex: 'totalPayAmt',
                                width: 140,
                                key: 'totalPayAmt'
                            }
                        },
                        {
                            table: {
                                title: '财务审批状态',
                                dataIndex: 'cwStatus',
                                width: 120,
                                key: 'cwStatus'
                            },
                            form: {
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '财务审批状态说明',
                                dataIndex: 'cwStatusRemark',
                                width: 150,
                                key: 'cwStatusRemark'
                            },
                            form: {
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '调差后累计结算金额',
                                dataIndex: 'tchljjsje',
                                width: 150,
                                key: 'tchljjsje'
                            },
                            form: {
                                type: 'number',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '本期调整后结算金额',
                                dataIndex: 'bqtchjsje',
                                width: 150,
                                key: 'bqtchjsje'
                            },
                            form: {
                                type: 'number',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '交工日期',
                                dataIndex: 'finishDate',
                                width: 100,
                                key: 'finishDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '业务日期',
                                dataIndex: 'businessDate',
                                width: 100,
                                key: 'businessDate',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                spanForm: 12
                            }
                        },
                        {
                            table: {
                                title: '重新评审次数',
                                dataIndex: 'notPassNum',
                                width: 120,
                                key: 'notPassNum'
                            },
                            form: {
                                type: 'number',
                                spanForm: 12
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '填报人',
                                field: 'reportPerson',
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '备注',
                                field: 'remark',
                                type: 'textarea',
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
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '计算人',
                                field: 'countPerson',
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '复核人',
                                field: 'reCountPerson',
                                type: 'string',
                                spanForm: 12
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                label: '',
                                field: 'projectSettleAuditItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    isShowRowSelect: false,
                                    antd: {
                                        rowKey: "statisticsID",
                                        size: 'small',
                                    },
                                    formConfig: [
                                        {
                                            table: {
                                                title: '统计项',
                                                dataIndex: 'statisticsName',
                                                align: "center"
                                            }
                                            , form: {
                                                type: 'string',
                                            }
                                        },
                                        {
                                            table: {
                                                title: '本期（元）',
                                                dataIndex: 'thisAmt',
                                                align: "center"
                                            }
                                            , form: {
                                                type: 'string',
                                            }
                                        },

                                        {
                                            table: {
                                                title: '开累（元）',
                                                dataIndex: 'totalAmt',
                                                align: "center"
                                            }
                                            , form: {
                                                type: 'string',
                                            }
                                        },

                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: "附件",
                                field: "zxErpFileList",
                                type: "files",
                                fetchConfig: {
                                    apiName: "upload"
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
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '发起人',
                                dataIndex: 'flowBeginPerson',
                                width: 100,
                                key: 'flowBeginPerson',
                                fixed: 'right'
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '审批状态',
                                dataIndex: 'apih5FlowStatus',
                                width: 100,
                                key: 'apih5FlowStatus',
                                fixed: 'right',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    { label: '未审核', value: '-1' },
                                    { label: '待提交', value: '0' },
                                    { label: '审核中', value: '1' },
                                    { label: '评审通过', value: '2' },
                                    { label: '退回', value: '3' },
                                ],
                            }
                        },
                    ]}
                />
                {
                    visible ? <Drawer
                        title={'结算会签单'}
                        placement="right"
                        onClose={this.onClose}
                        visible={visible}
                        width={window.screen.width * 0.75}
                        className={'drawerClass'}
                    >
                        <Detail {...this.props} flowData={flowData} />
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;