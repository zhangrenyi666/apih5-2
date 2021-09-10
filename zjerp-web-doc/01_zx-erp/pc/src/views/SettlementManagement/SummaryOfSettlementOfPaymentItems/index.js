import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'id',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    pageSize:99999999,
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryPeriod: moment(new Date()).format('YYYYMM')
            }
        }
    }
    render() {
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
                            label: '公司名称',
                            field: 'queryComID',
                            type: 'select',
                            showSearch: true,
                            allowClear: false,
                            required:true,
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId'
                            },
                            fetchConfig: {
                                apiName: 'getSysCompanyBySelectByDept',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 8
                        },
                        {
                            type: 'month',
                            label: '期次',
                            allowClear: false,
                            required:true,
                            field: 'queryPeriod',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            if (value?.queryPeriod) {
                                                value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                            }
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
                                                    if (value?.queryPeriod) {
                                                        value.queryPeriod = moment(value.queryPeriod).format('YYYYMM');
                                                    }
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSaProjPayCompRpt.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=支付项结算情况明细表`;
                                                    window.open(URL);
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
                        apiName: 'exportZxSaProjPayCompRpt',
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
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'orgName',
                                width: 150,
                                key: 'orgName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '合同额(元)',
                                dataIndex: 'contractCost',
                                width: 120,
                                key: 'contractCost'
                            }
                        },
                        {
                            table: {
                                title: '变更后合同额(元)',
                                dataIndex: 'alterContractSum',
                                width: 130,
                                key: 'alterContractSum'
                            }
                        },
                        {
                            table: {
                                title: '结算额(元)',
                                dataIndex: 'totalAmt',
                                width: 120,
                                key: 'totalAmt'
                            }
                        },
                        {
                            table: {
                                title: '其中(单位：元)',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 640,
                                children: [
                                    {
                                        title: '奖罚',
                                        dataIndex: 'a1',
                                        key: 'a1',
                                        width: 100
                                    },
                                    {
                                        title: '零星工程劳务',
                                        dataIndex: 'a2',
                                        key: 'a2',
                                        width: 120
                                    },
                                    {
                                        title: '零星工程机械',
                                        dataIndex: 'a3',
                                        key: 'a3',
                                        width: 120
                                    },
                                    {
                                        title: '补偿',
                                        dataIndex: 'a4',
                                        key: 'a4',
                                        width: 100
                                    },
                                    {
                                        title: '材料调拨',
                                        dataIndex: 'a5',
                                        key: 'a5',
                                        width: 100
                                    },
                                    {
                                        title: '其它项',
                                        dataIndex: 'a6',
                                        key: 'a6',
                                        width: 100
                                    },
                                ]
                            },
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                width: 150,
                                key: 'remarks'
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;