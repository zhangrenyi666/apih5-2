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
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryPeriod: new Date()
            },
            queryPeriod: moment(new Date()).format('YYYY') + '0' + moment(new Date()).quarter()
        }
    }
    // componentDidMount(){
    //     this.refresh();
    // }
    // refresh = () => {
    //     const { formData } = this.state;
    //     this.props.myFetch('exportZxSkStockDifMonthQtySumComJiduProjHead', formData).then(({ success, message, data }) => {
    //         if (success) {
    //             this.setState({
    //                 summary: data
    //             })
    //         } else {
    //             Msg.error(message);
    //         }
    //     });
    // }
    render () {
        const { departmentId, formData, queryPeriod } = this.state;
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
                            required: true,
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
                            type: 'quarter',
                            label: '季度',
                            allowClear: false,
                            required: true,
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
                                                    let URL = `${ureport}excel?_u=minio:ZxSkStockDifMonthQtySumComJiduProj.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=公司量差汇总表2（按项目）表`;
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
                            let totalb1 = 0;
                            let totalb2 = 0;
                            let totalb3 = 0;
                            let totalb4 = 0;
                            let totalb5 = 0;
                            let totalb6 = 0;
                            let totalb7 = 0;
                            let totalb8 = 0;
                            let totalb9 = 0;
                            let totalb10 = 0;
                            let totalb11 = 0;
                            let totalb12 = 0;
                            let totalb13 = 0;
                            let totalb14 = 0;
                            pageData.forEach(({ b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14 }) => {
                                totalb1 += b1 ? b1 : 0;
                                totalb2 += b2 ? b2 : 0;
                                totalb3 += b3 ? b3 : 0;
                                totalb4 += b4 ? b4 : 0;
                                totalb5 += b5 ? b5 : 0;
                                totalb6 += b6 ? b6 : 0;
                                totalb7 += b7 ? b7 : 0;
                                totalb8 += b8 ? b8 : 0;
                                totalb9 += b9 ? b9 : 0;
                                totalb10 += b10 ? b10 : 0;
                                totalb11 += b11 ? b11 : 0;
                                totalb12 += b12 ? b12 : 0;
                                totalb13 += b13 ? b13 : 0;
                                totalb14 += b14 ? b14 : 0;
                            });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}>合计</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}>{totalb1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}>{totalb2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}>{totalb3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5}>{totalb4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}>{totalb5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7}>{totalb6}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}>{totalb7}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9}>{totalb8}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}>{totalb9}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}>{totalb10}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}>{totalb11}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13}>{totalb12}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}>{totalb13}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={15}>{totalb14}</QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSkStockDifMonthQtySumComJiduProj',
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
                                dataIndex: 'projectName',
                                width: 150,
                                key: 'projectName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '实际消耗金额(万元)',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 220,
                                children: [
                                    {
                                        title: '本季',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 100,
                                        children: [
                                            {
                                                title: '1',
                                                dataIndex: 'b1',
                                                key: 'b1',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '自开工累计',
                                        dataIndex: 'title_12',
                                        key: 'title_12',
                                        width: 120,
                                        children: [
                                            {
                                                title: '2',
                                                dataIndex: 'b2',
                                                key: 'b2',
                                                width: 120
                                            },
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '设计用量金额(万元)',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 220,
                                children: [
                                    {
                                        title: '本季',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        width: 100,
                                        children: [
                                            {
                                                title: '3',
                                                dataIndex: 'b3',
                                                key: 'b3',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '自开工累计',
                                        dataIndex: 'title_22',
                                        key: 'title_22',
                                        width: 120,
                                        children: [
                                            {
                                                title: '4',
                                                dataIndex: 'b4',
                                                key: 'b4',
                                                width: 120
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '理论消耗金额(万元)',
                                dataIndex: 'title_3',
                                width: 220,
                                key: 'title_3',
                                children: [
                                    {
                                        title: '本季',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 100,
                                        children: [
                                            {
                                                title: '5',
                                                dataIndex: 'b5',
                                                key: 'b5',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '自开工累计',
                                        dataIndex: 'title_32',
                                        key: 'title_32',
                                        width: 120,
                                        children: [
                                            {
                                                title: '6',
                                                dataIndex: 'b6',
                                                key: 'b6',
                                                width: 120
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '与设计用量对比(节+超-)',
                                dataIndex: 'title_4',
                                width: 520,
                                key: 'title_4',
                                children: [
                                    {
                                        title: '本季节超金额(万元)',
                                        dataIndex: 'title_41',
                                        key: 'title_41',
                                        width: 140,
                                        children: [
                                            {
                                                title: '7=3-1',
                                                dataIndex: 'b7',
                                                key: 'b7',
                                                width: 140
                                            },
                                        ]
                                    },
                                    {
                                        title: '本季节超率(%)',
                                        dataIndex: 'title_42',
                                        key: 'title_42',
                                        width: 120,
                                        children: [
                                            {
                                                title: '8=7/3',
                                                dataIndex: 'b8',
                                                key: 'b8',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超金额(万元)',
                                        dataIndex: 'title_43',
                                        key: 'title_43',
                                        width: 140,
                                        children: [
                                            {
                                                title: '9=4-2',
                                                dataIndex: 'b9',
                                                key: 'b9',
                                                width: 140
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超率(%)',
                                        dataIndex: 'title_44',
                                        key: 'title_44',
                                        width: 120,
                                        children: [
                                            {
                                                title: '10=9/4',
                                                dataIndex: 'b10',
                                                key: 'b10',
                                                width: 120
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '与理论消耗金额对比(节+超-)',
                                dataIndex: 'title_5',
                                width: 520,
                                key: 'title_5',
                                children: [
                                    {
                                        title: '本季节超金额(万元)',
                                        dataIndex: 'title_51',
                                        key: 'title_51',
                                        width: 140,
                                        children: [
                                            {
                                                title: '11=5-1',
                                                dataIndex: 'b11',
                                                key: 'b11',
                                                width: 140
                                            },
                                        ]
                                    },
                                    {
                                        title: '本季节超率(%)',
                                        dataIndex: 'title_52',
                                        key: 'title_52',
                                        width: 120,
                                        children: [
                                            {
                                                title: '12=11/3',
                                                dataIndex: 'b12',
                                                key: 'b12',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超金额(万元)',
                                        dataIndex: 'title_53',
                                        key: 'title_53',
                                        width: 140,
                                        children: [
                                            {
                                                title: '13=6-2',
                                                dataIndex: 'b13',
                                                key: 'b13',
                                                width: 140
                                            },
                                        ]
                                    },
                                    {
                                        title: '开累节超率(%)',
                                        dataIndex: 'title_54',
                                        key: 'title_54',
                                        width: 120,
                                        children: [
                                            {
                                                title: '14=13/4',
                                                dataIndex: 'b14',
                                                key: 'b14',
                                                width: 120
                                            },
                                        ]
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