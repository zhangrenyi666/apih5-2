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
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
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
                            label: '公司',
                            field: 'queryComID',
                            type: 'string',
                            hide: true
                        },
                        {
                            label: '项目名称',
                            field: 'queryOrgID',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            required:true,
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
                                                    let URL = `${ureport}excel?_u=minio:ZxSaEquipSettleAudit21Rpt.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=其它结算台账(项目)报表`;
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
                        apiName: 'exportZxSaEquipSettleAudit21Rpt',
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
                                title: '合同类别',
                                dataIndex: 'contrType',
                                width: 120,
                                key: 'contrType',
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
                                title: '合同乙方',
                                dataIndex: 'secondName',
                                width: 120,
                                key: 'secondName'
                            }
                        },
                        {
                            table: {
                                title: '合同细目',
                                dataIndex: 'equipName',
                                width: 120,
                                key: 'equipName'
                            }
                        },
                        {
                            table: {
                                title: '计量单位',
                                dataIndex: 'unit',
                                width: 120,
                                key: 'unit'
                            }
                        },
                        {
                            table: {
                                title: '原合同清单',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 300,
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'qty',
                                        key: 'qty',
                                        width: 100
                                    },
                                    {
                                        title: '单价',
                                        dataIndex: 'price',
                                        key: 'price',
                                        width: 100
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'contractSum',
                                        key: 'contractSum',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '变更后合同清单',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 300,
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'alterQty',
                                        key: 'alterQty',
                                        width: 100
                                    },
                                    {
                                        title: '单价',
                                        dataIndex: 'alterPrice',
                                        key: 'alterPrice',
                                        width: 100
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'changeContractSum',
                                        key: 'changeContractSum',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '本月清单结算',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 200,
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'thisQty',
                                        key: 'thisQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'thisAmt',
                                        key: 'thisAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '累计清单结算',
                                dataIndex: 'title_4',
                                key: 'title_4',
                                width: 200,
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'totalQty',
                                        key: 'totalQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额',
                                        dataIndex: 'totalAmt',
                                        key: 'totalAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '支付项金额',
                                dataIndex: 'title_6',
                                key: 'title_6',
                                width: 200,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'payAmt',
                                        key: 'payAmt',
                                        width: 100
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'totalPayAmt',
                                        key: 'totalPayAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '结算金额',
                                dataIndex: 'title_7',
                                key: 'title_7',
                                width: 200,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'resAmt',
                                        key: 'resAmt',
                                        width: 100
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'totalResAmt',
                                        key: 'totalResAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '扣除保证金',
                                dataIndex: 'title_8',
                                key: 'title_8',
                                width: 200,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'kcAmt',
                                        key: 'kcAmt',
                                        width: 100
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'totalKcAmt',
                                        key: 'totalKcAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '返还保证金',
                                dataIndex: 'title_9',
                                key: 'title_9',
                                width: 200,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'wfAmt',
                                        key: 'wfAmt',
                                        width: 100
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'totalWfAmt',
                                        key: 'totalWfAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '应支付金额',
                                dataIndex: 'title_10',
                                key: 'title_10',
                                width: 200,
                                children: [
                                    {
                                        title: '本月',
                                        dataIndex: 'yfAmt',
                                        key: 'yfAmt',
                                        width: 100
                                    },
                                    {
                                        title: '累计',
                                        dataIndex: 'totalYfAmt',
                                        key: 'totalYfAmt',
                                        width: 100
                                    }
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