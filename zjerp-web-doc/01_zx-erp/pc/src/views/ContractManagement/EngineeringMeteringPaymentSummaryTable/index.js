import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'orgID',
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
                            showSearch: true,
                            allowClear: false,
                            span: 6
                        },
                        {
                            type: 'month',
                            label: '期次',
                            allowClear: false,
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
                                                    let stringData = '';
                                                    for(let key in value){
                                                        if(value?.[key]){
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxCtProjBalPayTotalRpt.xml&access_token=${access_token}&delFlag=0${stringData}&_n=工程计量支付汇总表`;
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
                        apiName: 'exportZxCtProjBalPayTotalRpt',
                        otherParams: {
                            ...formData
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'orgID',
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
                                key: 'orgName',
                                width: 150,
                                fixed: 'left',
                                render:(data) => {
                                    return <xmp>{data}</xmp>;
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '施工产值',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 300,
                                children: [
                                    {
                                        title: '上期末',
                                        dataIndex: 'lastProAmt',
                                        key: 'lastProAmt',
                                        width: 100
                                    },
                                    {
                                        title: '本期',
                                        dataIndex: 'thisProAmt',
                                        key: 'thisProAmt',
                                        width: 100
                                    },
                                    {
                                        title: '本期末',
                                        dataIndex: 'totalProAmt',
                                        key: 'totalProAmt',
                                        width: 100,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '计量支付',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 300,
                                children: [
                                    {
                                        title: '上期末',
                                        dataIndex: 'lastBalAmt',
                                        key: 'lastBalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '本期',
                                        dataIndex: 'thisBalAmt',
                                        key: 'thisBalAmt',
                                        width: 100
                                    },
                                    {
                                        title: '本期末',
                                        dataIndex: 'totalBalAmt',
                                        key: 'totalBalAmt',
                                        width: 100,
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '百分比(%)',
                                dataIndex: 'rate',
                                key: 'rate',
                                width: 120
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '实际支付',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 300,
                                children: [
                                    {
                                        title: '上期末',
                                        dataIndex: 'lastPayAmt',
                                        key: 'lastPayAmt',
                                        width: 100
                                    },
                                    {
                                        title: '本期',
                                        dataIndex: 'thisPayAmt',
                                        key: 'thisPayAmt',
                                        width: 100
                                    },
                                    {
                                        title: '本期末',
                                        dataIndex: 'totalPayAmt',
                                        key: 'totalPayAmt',
                                        width: 100,
                                    }
                                ]
                            }
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;