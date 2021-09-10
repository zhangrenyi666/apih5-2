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
            formData: {}
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
                            type: 'month',
                            label: '期次',
                            initialValue: new Date(),
                            allowClear: false,
                            required: true,
                            field: 'periodTime',
                            span: 8
                        },
                        {
                            label: '公司名称',
                            field: 'departmentId',
                            type: 'select',
                            showSearch: true,
                            allowClear: false,
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId',
                                linkageFields: {
                                    departmentName: 'companyName',
                                    departmentFlag: 'departmentFlag'
                                }
                            },
                            required: true,
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelectByDept',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 8
                        },
                        {
                            field: 'departmentFlag',
                            type: 'string',
                            hide: true
                        },
                        {
                            field: 'departmentName',
                            type: 'string',
                            hide: true
                        },
                        {
                            label: '分类',
                            field: 'businessDivisionFlag',
                            type: 'string',
                            initialValue: '1',
                            hide: true
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
                                                    this.props.myFetch('exportZxGcsgSettle', value).then(
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
                        apiName: 'getZxGcsgSettle',
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
                                title: '序号',
                                dataIndex: 'index',
                                width: 80,
                                key: 'index',
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            }
                        },
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'companyName',
                                width: 150,
                                key: 'companyName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 150,
                                key: 'orgName',
                                fixed: 'left'
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
                                title: '变更后合同金额',
                                dataIndex: 'alterContractSum',
                                width: 150,
                                key: 'alterContractSum'
                            }
                        },
                        {
                            table: {
                                title: '年初累计(元)',
                                dataIndex: 'earlyYearTotalAmt',
                                width: 120,
                                key: 'earlyYearTotalAmt'
                            }
                        },
                        {
                            table: {
                                title: '结算金额(元)',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 1200,
                                children: [
                                    {
                                        title: '1月',
                                        dataIndex: 'januaryTotalAmt',
                                        key: 'januaryTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '2月',
                                        dataIndex: 'februaryTotalAmt',
                                        key: 'februaryTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '3月',
                                        dataIndex: 'marchTotalAmt',
                                        key: 'marchTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '4月',
                                        dataIndex: 'aprilTotalAmt',
                                        key: 'aprilTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '5月',
                                        dataIndex: 'aprilTotalAmt',
                                        key: 'aprilTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '6月',
                                        dataIndex: 'juneTotalAmt',
                                        key: 'juneTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '7月',
                                        dataIndex: 'julyTotalAmt',
                                        key: 'julyTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '8月',
                                        dataIndex: 'augustTotalAmt',
                                        key: 'augustTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '9月',
                                        dataIndex: 'septemberTotalAmt',
                                        key: 'septemberTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '10月',
                                        dataIndex: 'octoberTotalAmt',
                                        key: 'octoberTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '11月',
                                        dataIndex: 'octoberTotalAmt',
                                        key: 'octoberTotalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '12月',
                                        dataIndex: 'decemberTotalAmt',
                                        key: 'decemberTotalAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '开累结算金额(元)',
                                dataIndex: 'totalAmt',
                                width: 130,
                                key: 'totalAmt'
                            }
                        },
                        {
                            table: {
                                title: '最后结算期次',
                                dataIndex: 'lastPeriod',
                                width: 120,
                                key: 'lastPeriod'
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;