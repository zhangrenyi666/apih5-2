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
                                                    let URL = `${ureport}excel?_u=minio:ZxSaStockSettleAuditP5.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=物资采购结算台账(公司)报表`;
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
                    antd={{
                        rowKey: 'id',
                        size: 'small',
                        summary: (pageData) => {
                            let total1 = 0;
                            let total2 = 0;
                            let total3 = 0;
                            let total4 = 0;
                            let total5 = 0;
                            let total6 = 0;
                            let total7 = 0;
                            let total8 = 0;
                            let total9 = 0;
                            let total10 = 0;
                            let total11 = 0;
                            let total12 = 0;
                            pageData.forEach(({ contrCount2, contrCount1, contractSum, alterAmt, resAmt, totalResAmt, kcAmt, totalKcAmt, wfAmt, totalWfAmt, yfAmt, totalYfAmt }) => {
                                total1 += contrCount2 ? contrCount2 : 0;
                                total2 += contrCount1 ? contrCount1 : 0;
                                total3 += contractSum ? contractSum : 0;
                                total4 += alterAmt ? alterAmt : 0;
                                total5 += resAmt ? resAmt : 0;
                                total6 += totalResAmt ? totalResAmt : 0;
                                total7 += kcAmt ? kcAmt : 0;
                                total8 += totalKcAmt ? totalKcAmt : 0;
                                total9 += wfAmt ? wfAmt : 0;
                                total10 += totalWfAmt ? totalWfAmt : 0;
                                total11 += yfAmt ? yfAmt : 0;
                                total12 += totalYfAmt ? totalYfAmt : 0;
                            });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}>合计</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}>{total1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}>{total2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}>{total3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5}>{total4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}>{total5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7}>{total6}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}>{total7}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9}>{total8}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}>{total9}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}>{total10}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}>{total11}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13}>{total12}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}></QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSaStockSettleAuditP5',
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
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 150,
                                key: 'orgName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '项目已签订合同份数',
                                dataIndex: 'contrCount2',
                                width: 120,
                                key: 'contrCount2'
                            }
                        },
                        {
                            table: {
                                title: '项目已结算合同份数',
                                dataIndex: 'contrCount1',
                                width: 120,
                                key: 'contrCount1'
                            }
                        },
                        {
                            table: {
                                title: '合同总价(元)',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 200,
                                children: [
                                    {
                                        title: '合同',
                                        dataIndex: 'contractSum',
                                        key: 'contractSum',
                                        width: 100
                                    },
                                    {
                                        title: '变更后',
                                        dataIndex: 'alterAmt',
                                        key: 'alterAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '结算金额(元)',
                                dataIndex: 'title_2',
                                key: 'title_2',
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
                                title: '扣除保证金(元)',
                                dataIndex: 'title_3',
                                key: 'title_3',
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
                                title: '返还保证金(元)',
                                dataIndex: 'title_4',
                                key: 'title_4',
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
                                title: '应支付金额(元)',
                                dataIndex: 'title_5',
                                key: 'title_5',
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