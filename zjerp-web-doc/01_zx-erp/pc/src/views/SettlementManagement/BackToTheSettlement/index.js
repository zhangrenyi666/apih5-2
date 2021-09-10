import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg } from "antd";
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
                            allowClear: false,
                            required: true,
                            field: 'periodTime',
                            initialValue: new Date(),
                            span: 8
                        },
                        {
                            label: '公司名称',
                            field: 'departmentId',
                            type: 'select',
                            showSearch: true,
                            allowClear: false,
                            required: true,
                            optionConfig: {
                                label: 'companyName',
                                value: 'companyId',
                                linkageFields: {
                                    departmentName: 'companyName',
                                    departmentFlag: 'departmentFlag'
                                }
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
                                                    this.props.myFetch('exportZxSaReturnSettlement', value).then(
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
                        apiName: 'getZxSaReturnSettlement',
                        otherParams: { ...formData },
                        success: (obj) => {
                            if (obj.data && obj.data.length) {
                                let setExpandedRowsKey = this.table.setExpandedRowsKey;
                                setExpandedRowsKey([obj.data[0].id]);
                            }
                        }
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
                                title: '机构名称',
                                dataIndex: 'departmentName',
                                width: 150,
                                key: 'departmentName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '实际开工日期',
                                dataIndex: 'realBeginDate',
                                width: 120,
                                key: 'realBeginDate',
                                fixed: 'left',
                                format: 'YYYY-MM-DD'
                            }
                        },
                        {
                            table: {
                                title: '工程类合同',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 400,
                                children: [
                                    {
                                        title: '上报份数',
                                        dataIndex: 'title_11',
                                        key: 'title_11',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'gcThisReportNum',
                                                key: 'gcThisReportNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'gcTotalReportNum',
                                                key: 'gcTotalReportNum',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '退回份数',
                                        dataIndex: 'title_12',
                                        key: 'title_12',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'gcThisBackNum',
                                                key: 'gcThisBackNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'gcTotalBackNum',
                                                key: 'gcTotalBackNum',
                                                width: 100
                                            },
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '物资类合同',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 400,
                                children: [
                                    {
                                        title: '上报份数',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'wzThisReportNum',
                                                key: 'wzThisReportNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'wzTotalReportNum',
                                                key: 'wzTotalReportNum',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '退回份数',
                                        dataIndex: 'title_22',
                                        key: 'title_22',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'wzThisBackNum',
                                                key: 'wzThisBackNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'wzTotalBackNum',
                                                key: 'wzTotalBackNum',
                                                width: 100
                                            },
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '机械租赁类合同',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 400,
                                children: [
                                    {
                                        title: '上报份数',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'jxThisReportNum',
                                                key: 'jxThisReportNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'jxTotalReportNum',
                                                key: 'jxTotalReportNum',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '退回份数',
                                        dataIndex: 'title_32',
                                        key: 'title_32',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'jxThisBackNum',
                                                key: 'jxThisBackNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'jxTotalBackNum',
                                                key: 'jxTotalBackNum',
                                                width: 100
                                            },
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '其它类合同',
                                dataIndex: 'title_4',
                                key: 'title_4',
                                width: 400,
                                children: [
                                    {
                                        title: '上报份数',
                                        dataIndex: 'title_41',
                                        key: 'title_41',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'qtThisReportNum',
                                                key: 'qtThisReportNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'qtThisReportNum',
                                                key: 'qtThisReportNum',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '退回份数',
                                        dataIndex: 'title_42',
                                        key: 'title_42',
                                        width: 200,
                                        children: [
                                            {
                                                title: '本期',
                                                dataIndex: 'qtThisBackNum',
                                                key: 'qtThisBackNum',
                                                width: 100
                                            },
                                            {
                                                title: '累计',
                                                dataIndex: 'qtTotalBackNum',
                                                key: 'qtTotalBackNum',
                                                width: 100
                                            },
                                        ]
                                    }
                                ]
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;