import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg } from "antd";
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
            departmentId:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryPeriod: moment(new Date()).format('YYYYMM')
            },
            summary: null
        }
    }
    componentDidMount(){
        this.refresh();
    }
    refresh = () => {
        const { formData } = this.state;
        this.props.myFetch('exportZxSkStockDifMonthQtySumProjHead', formData).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    summary: data
                })
            } else {
                Msg.error(message);
            }
        });
    }
    render() {
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId, formData, summary, queryOrgID } = this.state;
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
                            required:true,
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
                            condition: [
                                {
                                    regex: { queryComID: ['!', /(''|null|undefined)/ig] },
                                    action: 'disabled',
                                }
                            ],
                            disabled:curCompany?.ext1 === '3' || curCompany?.ext1 === '4' || queryOrgID ? true : false,
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            required:true,
                            span: 6
                        },
                        {
                            type: 'month',
                            label: '月度',
                            field: 'queryPeriod',
                            allowClear: false,
                            required:true,
                            span: 6
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
                                                formData: value,
                                                summary:null
                                            },() => {
                                                this.refresh();
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
                                                    let URL = `${ureport}excel?_u=minio:ZxSkStockDifMonthQtySumProj.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=项目月度量差表`;
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
                {summary ? <QnnTable
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
                            // let totalb1 = 0;
                            // let totalb2 = 0;
                            // let totalb3 = 0;
                            // let totalb4 = 0;
                            // let totalb5 = 0;
                            // let totalb6 = 0;
                            // let totalb7 = 0;
                            // let totalb8 = 0;
                            // let totalb9 = 0;
                            // let totalb10 = 0;
                            // let totalb11 = 0;
                            // let totalb12 = 0;
                            // let totalb13 = 0;
                            // let totalb14 = 0;
                            // pageData.forEach(({ b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14 }) => {
                            //     totalb1 += b1 ? b1 : 0;
                            //     totalb2 += b2 ? b2 : 0;
                            //     totalb3 += b3 ? b3 : 0;
                            //     totalb4 += b4 ? b4 : 0;
                            //     totalb5 += b5 ? b5 : 0;
                            //     totalb6 += b6 ? b6 : 0;
                            //     totalb7 += b7 ? b7 : 0;
                            //     totalb8 += b8 ? b8 : 0;
                            //     totalb9 += b9 ? b9 : 0;
                            //     totalb10 += b10 ? b10 : 0;
                            //     totalb11 += b11 ? b11 : 0;
                            //     totalb12 += b12 ? b12 : 0;
                            //     totalb13 += b13 ? b13 : 0;
                            //     totalb14 += b14 ? b14 : 0;
                            // });
                            return (
                                <>
                                    <QnnTable.Summary.Row>
                                        <QnnTable.Summary.Cell index={0}>合计</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={1}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={2}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={3}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={4}></QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={5}>{summary?.b1}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={6}>{summary?.b2}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={7}>{summary?.b3}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={8}>{summary?.b4}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={9}>{summary?.b5}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={10}>{summary?.b6}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={11}>{summary?.b7}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={12}>{summary?.b8}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={13}>{summary?.b9}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={14}>{summary?.b10}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={15}>{summary?.b11}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={16}>{summary?.b12}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={17}>{summary?.b13}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={18}>{summary?.b14}</QnnTable.Summary.Cell>
                                        <QnnTable.Summary.Cell index={19}></QnnTable.Summary.Cell>
                                    </QnnTable.Summary.Row>
                                </>
                            )
                        }
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'exportZxSkStockDifMonthQtySumProj',
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
                                title: '物资名称',
                                dataIndex: 'resName',
                                width: 150,
                                key: 'resName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '物资编码',
                                dataIndex: 'resCode',
                                width: 150,
                                key: 'resCode',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '规格型号',
                                dataIndex: 'spec',
                                width: 100,
                                key: 'spec',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '计量单位',
                                dataIndex: 'unit',
                                width: 100,
                                key: 'unit',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '类型',
                                dataIndex: 'mtType',
                                width: 100,
                                key: 'mtType',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '本月物资实际消耗',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                children: [
                                    {
                                        title: '实际消耗数量',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        children: [
                                            {
                                                title: '1',
                                                dataIndex: 'b1',
                                                key: 'b1',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '采购加权单价(元)',
                                        dataIndex: 'title_12',
                                        key: 'title_12',
                                        children: [
                                            {
                                                title: '2',
                                                dataIndex: 'b2',
                                                key: 'b2',
                                                width: 130
                                            },
                                        ]
                                    },
                                    {
                                        title: '实际消耗金额(元)',
                                        dataIndex: 'title_13',
                                        key: 'title_13',
                                        children: [
                                            {
                                                title: '3=1*2',
                                                dataIndex: 'b3',
                                                key: 'b3',
                                                width: 130
                                            },
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '本月量差核算情况',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                children: [
                                    {
                                        title: '设计用量',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        children: [
                                            {
                                                title: '4',
                                                dataIndex: 'b4',
                                                key: 'b4',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '设计用量金额(元)',
                                        dataIndex: 'title_22',
                                        key: 'title_22',
                                        children: [
                                            {
                                                title: '5=4*2',
                                                dataIndex: 'b5',
                                                key: 'b5',
                                                width: 130
                                            },
                                        ]
                                    },
                                    {
                                        title: '局定额损耗率(%)',
                                        dataIndex: 'title_23',
                                        key: 'title_23',
                                        children: [
                                            {
                                                title: '6',
                                                dataIndex: 'b6',
                                                key: 'b6',
                                                width: 130
                                            },
                                        ]
                                    },
                                    {
                                        title: '理论消耗量',
                                        dataIndex: 'title_24',
                                        key: 'title_24',
                                        children: [
                                            {
                                                title: '7=4+4*6/100',
                                                dataIndex: 'b7',
                                                key: 'b7',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '理论消耗金额',
                                        dataIndex: 'title_25',
                                        key: 'title_25',
                                        children: [
                                            {
                                                title: '8=7*2',
                                                dataIndex: 'b8',
                                                key: 'b8',
                                                width: 120
                                            },
                                        ]
                                    },
                                    {
                                        title: '与设计用量对比(节+超-)',
                                        dataIndex: 'title_26',
                                        key: 'title_26',
                                        children: [
                                            {
                                                title: '节超数量',
                                                dataIndex: 'title_261',
                                                key: 'title_261',
                                                children: [
                                                    {
                                                        title: '9=4-1',
                                                        dataIndex: 'b9',
                                                        key: 'b9',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超金额(元)',
                                                dataIndex: 'title_262',
                                                key: 'title_262',
                                                children: [
                                                    {
                                                        title: '10=9*2',
                                                        dataIndex: 'b10',
                                                        key: 'b10',
                                                        width: 120
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超率(%)',
                                                dataIndex: 'title_263',
                                                key: 'title_263',
                                                children: [
                                                    {
                                                        title: '11=10/5',
                                                        dataIndex: 'b11',
                                                        key: 'b11',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                        ]
                                    },
                                    {
                                        title: '与理论用量对比(节+超-)',
                                        dataIndex: 'title_27',
                                        key: 'title_27',
                                        children: [
                                            {
                                                title: '节超数量',
                                                dataIndex: 'title_271',
                                                key: 'title_271',
                                                children: [
                                                    {
                                                        title: '12=7-1',
                                                        dataIndex: 'b12',
                                                        key: 'b12',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超金额(元)',
                                                dataIndex: 'title_272',
                                                key: 'title_272',
                                                children: [
                                                    {
                                                        title: '13=12*2',
                                                        dataIndex: 'b13',
                                                        key: 'b13',
                                                        width: 120
                                                    }
                                                ]
                                            },
                                            {
                                                title: '节超率(%)',
                                                dataIndex: 'title_273',
                                                key: 'title_273',
                                                children: [
                                                    {
                                                        title: '14=13/5',
                                                        dataIndex: 'b14',
                                                        key: 'b14',
                                                        width: 100
                                                    }
                                                ]
                                            },
                                        ]
                                    },
                                    {
                                        title: '备注',
                                        dataIndex: 'remarks',
                                        width: 150,
                                        key: 'remarks'
                                    }
                                ]
                            }
                        }
                    ]}
                /> : null}
            </div>
        );
    }
}

export default index;