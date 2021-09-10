import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, Modal } from "antd";
const { confirm } = Modal;
const config = {
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
            queryOrgID:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryPeriod: new Date()
            },
            queryPeriod: moment(new Date()).format('YYYY') + '0' + moment(new Date()).quarter()
        }
    }
    render () {
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId, formData, queryPeriod, queryOrgID } = this.state;
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
                            type: 'quarter',
                            label: '季度',
                            field: 'queryPeriod',
                            required: true,
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
                                                if (moment(value.queryPeriod).quarter() === 1) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY01');
                                                } else if (moment(value.queryPeriod).quarter() === 2) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY02');
                                                } else if (moment(value.queryPeriod).quarter() === 3) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY03');
                                                } else if (moment(value.queryPeriod).quarter() === 4) {
                                                    value.queryPeriod = moment(value.queryPeriod).format('YYYY04');
                                                }
                                            }
                                            this.setState({
                                                formData: value,
                                                queryPeriod: value.queryPeriod
                                            })
                                        })
                                    }}>查询</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '确定导出数据吗?',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    if (value?.queryPeriod) {
                                                        if (moment(value.queryPeriod).quarter() === 1) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY01');
                                                        } else if (moment(value.queryPeriod).quarter() === 2) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY02');
                                                        } else if (moment(value.queryPeriod).quarter() === 3) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY03');
                                                        } else if (moment(value.queryPeriod).quarter() === 4) {
                                                            value.queryPeriod = moment(value.queryPeriod).format('YYYY04');
                                                        }
                                                    }
                                                    let stringData = '';
                                                    for (let key in value) {
                                                        if (value?.[key]) {
                                                            stringData += `&${key}=${value[key]}`;
                                                        }
                                                    }
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    let URL = `${ureport}excel?_u=minio:ZxSkStockTransferRptNewWen.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=中交物资收发存季度统计报表`;
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
                            pageData.forEach(({ qcinQty, qcinAmt, c4Qty, c4Amt, c1Qty, c1Amt, f3outQty, f3outAmt, curQty, curAmt }) => {
                                total1 += (qcinQty ? qcinQty : 0 * qcinAmt ? qcinAmt : 0);
                                total2 += (c4Qty ? c4Qty : 0 * c4Amt ? c4Amt : 0);
                                total3 += (c1Qty ? c1Qty : 0 * c1Amt ? c1Amt : 0);
                                total4 += (f3outQty ? f3outQty : 0 * f3outAmt ? f3outAmt : 0);
                                total5 += (curQty ? curQty : 0 * curAmt ? curAmt : 0);
                            });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}>金额总合计(万元)</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5} colSpan={2}>{total1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7} colSpan={2}>{total2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9} colSpan={2}>{total3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13} colSpan={2}>{total4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={15}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={16} colSpan={2}>{total5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={17}></QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSkStockTransferRptNewWen',
                        otherParams: { ...formData, queryPeriod }
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
                                title: '类别',
                                dataIndex: 'num',
                                width: 120,
                                key: 'num',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 150,
                                key: 'resName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '特征字符',
                                dataIndex: 'resType',
                                width: 150,
                                key: 'resType',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '计量单位',
                                dataIndex: 'title_1',
                                width: 200,
                                key: 'title_1',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'spec',
                                        key: 'spec',
                                        width: 100
                                    },
                                    {
                                        title: '金额(万元)',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        width: 100
                                    },
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本季期初库存',
                                dataIndex: 'title_2',
                                width: 200,
                                key: 'title_2',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'qcinQty',
                                        key: 'qcinQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额(万元)',
                                        dataIndex: 'qcinAmt',
                                        key: 'qcinAmt',
                                        width: 100
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本季收入',
                                dataIndex: 'title_3',
                                width: 600,
                                key: 'title_3',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'c4Qty',
                                        key: 'c4Qty',
                                        width: 100
                                    },
                                    {
                                        title: '金额(万元)',
                                        dataIndex: 'c4Amt',
                                        key: 'c4Amt',
                                        width: 100
                                    },
                                    {
                                        title: '其中：',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 400,
                                        children: [
                                            {
                                                title: '甲供物资',
                                                dataIndex: 'title_311',
                                                key: 'title_311',
                                                width: 200,
                                                children: [
                                                    {
                                                        title: '数量',
                                                        dataIndex: 'c1Qty',
                                                        key: 'c1Qty',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '金额(万元)',
                                                        dataIndex: 'c1Amt',
                                                        key: 'c1Amt',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                            {
                                                title: '境外工程的国内采购',
                                                dataIndex: 'title_312',
                                                key: 'title_312',
                                                width: 200,
                                                children: [
                                                    {
                                                        title: '数量',
                                                        dataIndex: 's1',
                                                        key: 's1',
                                                        width: 100
                                                    },
                                                    {
                                                        title: '金额(万元)',
                                                        dataIndex: 's2',
                                                        key: 's2',
                                                        width: 100
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本季发出',
                                dataIndex: 'title_4',
                                width: 350,
                                key: 'title_4',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'f3outQty',
                                        key: 'f3outQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额(万元)',
                                        dataIndex: 'f3outAmt',
                                        key: 'f3outAmt',
                                        width: 100
                                    },
                                    {
                                        title: '占本期营业额的比重(%)',
                                        dataIndex: 's3',
                                        key: 's3',
                                        width: 150
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '本季期末库存',
                                dataIndex: 'title_5',
                                width: 200,
                                key: 'title_5',
                                children: [
                                    {
                                        title: '数量',
                                        dataIndex: 'curQty',
                                        key: 'curQty',
                                        width: 100
                                    },
                                    {
                                        title: '金额(万元)',
                                        dataIndex: 'curAmt',
                                        key: 'curAmt',
                                        width: 100
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