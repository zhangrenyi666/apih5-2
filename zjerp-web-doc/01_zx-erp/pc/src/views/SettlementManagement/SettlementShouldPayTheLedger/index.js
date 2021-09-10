import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'costRatioId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    pageSize: 99999999,
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                periodTime: moment(new Date()).valueOf()
            }
        }
    }
    render () {
        const { departmentId, formData } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    data={formData}
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
                            field: 'departmentId',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.departmentId ? false : true,
                            required: true,
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId',
                                linkageFields: {
                                    departmentName: 'departmentName'
                                }
                            },
                            fetchConfig: {
                                apiName: 'getSysProjectBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 8
                        },
                        {
                            field: 'departmentName',
                            type: 'string',
                            hide: true
                        },
                        {
                            type: 'month',
                            label: '期次',
                            allowClear: false,
                            required: true,
                            field: 'periodTime',
                            span: 8
                        },
                        {
                            label: '合同类别',
                            field: 'contractType',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'categorycm1001500'
                                }
                            },
                            span: 8
                        },
                        {
                            label: '合同编号',
                            field: 'contractNo',
                            type: 'string',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            value.periodTime = moment(value.periodTime).valueOf();
                                            this.setState({
                                                formData: value
                                            })
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    value.periodTime = moment(value.periodTime).valueOf();
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    value.reportUrl = ureport;
                                                    value.accessToken = access_token;
                                                    this.props.myFetch('exportZxProjectSettleAccountsPayable', value).then(
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
                                        }}>导出</Button></div>
                                );
                            },
                            span: 8
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
                        apiName: 'getZxProjectSettleAccountsPayable',
                        otherParams: { ...formData }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'costRatioId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                width: 150,
                                key: 'contractNo',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '合同类别',
                                dataIndex: 'contractType',
                                width: 120,
                                key: 'contractType',
                                fixed: 'left',
                                // type:'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeList',
                                    otherParams: {
                                        departmentId: 'categorycm1001500'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '乙方名称',
                                dataIndex: 'secondName',
                                width: 150,
                                key: 'secondName'
                            }
                        },
                        {
                            table: {
                                title: '乙方负责人',
                                dataIndex: 'secondPrincipal',
                                width: 100,
                                key: 'secondPrincipal'
                            }
                        },
                        {
                            table: {
                                title: '合同内容',
                                dataIndex: 'content',
                                width: 150,
                                key: 'content'
                            }
                        },
                        {
                            table: {
                                title: '合同金额(元)',
                                dataIndex: 'contractCost',
                                width: 120,
                                key: 'contractCost'
                            }
                        },
                        {
                            table: {
                                title: '结算类型',
                                dataIndex: 'billType',
                                width: 120,
                                key: 'billType',
                                // type:'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeList',
                                    otherParams: {
                                        departmentId: 'category_settle01'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '上期末结算单编号',
                                dataIndex: 'upBillNo',
                                width: 150,
                                key: 'upBillNo'
                            }
                        },
                        {
                            table: {
                                title: '结算单编号',
                                dataIndex: 'billNo',
                                width: 150,
                                key: 'billNo'
                            }
                        },
                        {
                            table: {
                                title: '结算金额(元)',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 600,
                                children: [
                                    {
                                        title: '本月不含税',
                                        dataIndex: 'thisAmtNoTax',
                                        key: 'thisAmtNoTax',
                                        width: 100
                                    },
                                    {
                                        title: '本月税额',
                                        dataIndex: 'thisAmtTax',
                                        key: 'thisAmtTax',
                                        width: 100
                                    },
                                    {
                                        title: '本月含税',
                                        dataIndex: 'thisAmt',
                                        key: 'thisAmt',
                                        width: 100
                                    },
                                    {
                                        title: '累计不含税',
                                        dataIndex: 'totalAmtNoTax',
                                        key: 'totalAmtNoTax',
                                        width: 100
                                    },
                                    {
                                        title: '累计税额',
                                        dataIndex: 'totalAmtTax',
                                        key: 'totalAmtTax',
                                        width: 100
                                    },
                                    {
                                        title: '累计含税',
                                        dataIndex: 'totalAmt',
                                        key: 'totalAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '扣除保证金(元)',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 200,
                                children: [
                                    {
                                        title: '质量保证金',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'thisKcAmt',
                                                key: 'thisKcAmt',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'totalKcAmt',
                                                key: 'totalKcAmt',
                                                width: 100
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '返还保证金(元)',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 200,
                                children: [
                                    {
                                        title: '质量保证金',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本月',
                                                dataIndex: 'thisFhAmt',
                                                key: 'thisFhAmt',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'totalFhAmt',
                                                key: 'totalFhAmt',
                                                width: 100
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '应付款(元)',
                                dataIndex: 'title_5',
                                key: 'title_5',
                                width: 200,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'thisYfgckAmt',
                                        key: 'thisYfgckAmt',
                                        width: 100
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'totalYfgckAmt',
                                        key: 'totalYfgckAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark',
                                width: 150
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;