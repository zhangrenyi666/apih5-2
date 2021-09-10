import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'contractID',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    pageSize: 99999999,
    paginationConfig: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    },
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            queryOrgID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            formData: {
                queryOrgID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryPeriod:moment(new Date()).format('YYYYMM')
            }
        }
    }
    render() {
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { formData, departmentId, queryOrgID } = this.state;
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
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || queryOrgID ? true : false,
                            span: 6
                        },
                        {
                            label: '项目名称',
                            field: 'queryOrgID',
                            type: 'select',
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId'
                            },
                            parent: 'queryComID',
                            fetchConfig: {
                                apiName: 'getSysProjectList',
                                params: {
                                    departmentId: 'queryComID'
                                }
                            },
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || queryOrgID ? true : false,
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            span: 6
                        },
                        {
                            label: '合同编号',
                            field: 'contractNo',
                            type: 'string',
                            span: 6
                        },
                        {
                            label: '合同名称',
                            field: 'contractName',
                            type: 'string',
                            span: 6
                        },
                        {
                            label: '签订日期',
                            field: 'queryDate',
                            type: 'rangeDate',
                            span: 6
                        },
                        {
                            label: '合同类型',
                            field: 'queryContractType',
                            type: 'select',
                            optionConfig: {
                                label: 'label',
                                value: 'value'
                            },
                            optionData: [
                                {
                                    label: '工程施工类合同',
                                    value: 'P2'
                                },
                                {
                                    label: '物资类合同',
                                    value: 'P5'
                                },
                                {
                                    label: '设备合同',
                                    value: 'P4'
                                },
                                {
                                    label: '其它类合同',
                                    value: 'P8'
                                },
                                {
                                    label: '附属类合同',
                                    value: 'P9'
                                }
                            ],
                            span: 6
                        },
                        {
                            type: 'month',
                            label: '期次',
                            field: 'queryPeriod',
                            span: 6
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        let value = this.form.form.getFieldsValue();
                                        if(value.queryPeriod){
                                            value.queryPeriod = moment(value.queryPeriod).format('YYYYMM'); 
                                         }
                                         if (value?.queryDate?.length) {
                                            value.queryBeginDate = moment(value.queryDate[0]).startOf('date').format('YYYY-MM-DD');
                                            value.queryEndDate = moment(value.queryDate[1]).endOf('date').format('YYYY-MM-DD');
                                        }
                                        delete value.queryDate;
                                        this.setState({
                                            formData: value
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if(value.queryPeriod){
                                                        value.queryPeriod = moment(value.queryPeriod).format('YYYYMM'); 
                                                     }
                                                     if (value?.queryDate?.length) {
                                                        value.queryBeginDate = moment(value.queryDate[0]).startOf('date').format('YYYY-MM-DD');
                                                        value.queryEndDate = moment(value.queryDate[1]).endOf('date').format('YYYY-MM-DD');
                                                    }
                                                    delete value.queryDate;
                                                    let stringData = '';
                                                    for(let key in value){
                                                        if(value?.[key]){
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxCtContrDeviRpt.xml&access_token=${access_token}&delFlag=0${stringData}&_n=合同偏差统计表`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>导出</Button></div>
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
                        apiName: 'exportZxCtContrDeviRpt',
                        otherParams: {
                            ...formData
                        }
                    }}
                    formConfig={[
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
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
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
                                title: '合同编号',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
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
                                title: '合同名称',
                                dataIndex: 'contractName',
                                key: 'contractName',
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
                                title: '甲方',
                                dataIndex: 'firstName',
                                key: 'firstName',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '乙方',
                                dataIndex: 'secondName',
                                key: 'secondName',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '初始合同金额',
                                dataIndex: 'contractCost',
                                key: 'contractCost',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '签订日期',
                                dataIndex: 'signatureDate',
                                key: 'signatureDate',
                                width: 100
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '最终结算清单小计金额(到本期末累计结算)',
                                dataIndex: 'totalAmt',
                                key: 'totalAmt',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '最终结算合计结算金额(到本期末累计结算)',
                                dataIndex: 'totalAmt2',
                                key: 'totalAmt2',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '最终结算日期',
                                dataIndex: 'businessDate',
                                key: 'businessDate',
                                width: 120
                            },
                            form: {
                                type: 'date',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '合同金额偏差',
                                dataIndex: 't1',
                                key: 't1',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '结算-合同偏差',
                                dataIndex: 't2',
                                key: 't2',
                                width: 120
                            },
                            form: {
                                type: 'number',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;