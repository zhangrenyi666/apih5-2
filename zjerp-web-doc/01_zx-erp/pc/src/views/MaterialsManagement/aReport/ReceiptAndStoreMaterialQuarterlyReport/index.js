import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../../modules/qnn-table/qnn-form";
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
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            formData: {
                queryComID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
                queryOrgID: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                queryPeriod: new Date()
            },
            queryPeriod: moment(new Date()).format('YYYY') + '0' + moment(new Date()).quarter()
        }
    }
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
                    data={formData}
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
                            type: 'string',
                            label: '??????',
                            field: 'queryComID',
                            hide: true
                        },
                        {
                            label: '????????????',
                            field: 'queryOrgID',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.queryOrgID ? false : true,
                            required: true,
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
                            type: 'quarter',
                            label: '??????',
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
                                    }}>??????</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '??????????????????????',
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
                                                    let URL = `${ureport}excel?_u=minio:ZxSkStockTransferRptWen.ureport.xml&access_token=${access_token}&delFlag=0${stringData}&_n=???????????????????????????`;
                                                    window.open(URL);
                                                }
                                            })
                                        }}>??????</Button></div>
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
                        apiName: 'exportZxSkStockTransferRptWen',
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
                                title: '??????',
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
                                placeholder: '?????????'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'resName',
                                width: 150,
                                key: 'resName',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 200,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'spec',
                                        key: 'spec',
                                        width: 100
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 200,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'qcinQty',
                                        key: 'qcinQty',
                                        width: 100
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'qcinAmt',
                                        key: 'qcinAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 1100,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 300,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'c1Qty',
                                                key: 'c1Qty',
                                                width: 100
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'c1Price',
                                                key: 'c1Price',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'c1Amt',
                                                key: 'c1Amt',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'title_32',
                                        key: 'title_32',
                                        width: 300,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'c2Qty',
                                                key: 'c2Qty',
                                                width: 100
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'c2Price',
                                                key: 'c2Price',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'c2Amt',
                                                key: 'c2Amt',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'title_33',
                                        key: 'title_33',
                                        width: 300,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'c3Qty',
                                                key: 'c3Qty',
                                                width: 100
                                            },
                                            {
                                                title: '????????????',
                                                dataIndex: 'c3Price',
                                                key: 'c3Price',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'c3Amt',
                                                key: 'c3Amt',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'title_34',
                                        key: 'title_34',
                                        width: 200,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'hQty',
                                                key: 'hQty',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'hAmt',
                                                key: 'hAmt',
                                                width: 100
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'title_4',
                                key: 'title_4',
                                width: 200,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'totalQtyS',
                                        key: 'totalQtyS',
                                        width: 100
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'totalAmtS',
                                        key: 'totalAmtS',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'title_5',
                                key: 'title_5',
                                width: 600,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'title_51',
                                        key: 'title_51',
                                        width: 200,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'f1outQty',
                                                key: 'f1outQty',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'f1outAmt',
                                                key: 'f1outAmt',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'title_52',
                                        key: 'title_52',
                                        width: 200,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'f1outQtyQ',
                                                key: 'f1outQtyQ',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'f1outAmtQ',
                                                key: 'f1outAmtQ',
                                                width: 100
                                            },
                                        ]
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'title_53',
                                        key: 'title_53',
                                        width: 200,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'qtyT',
                                                key: 'qtyT',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'amtT',
                                                key: 'amtT',
                                                width: 100
                                            },
                                        ]
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'title_6',
                                key: 'title_6',
                                width: 200,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'f1outQtyN',
                                        key: 'f1outQtyN',
                                        width: 100
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'f1outAmtN',
                                        key: 'f1outAmtN',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'title_7',
                                key: 'title_7',
                                width: 200,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'tQty',
                                        key: 'tQty',
                                        width: 100
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'tAmt',
                                        key: 'tAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;