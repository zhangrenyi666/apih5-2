import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col } from "antd";
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orgID: '',
            beginDate: '',
            endDate: '',
            resID: ''
        }
    }
    componentDidMount() {

    }
    render() {
        const { orgID, beginDate, endDate, resID } = this.state;
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
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
                                    label: '单位名称',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'orgName',
                                        value: 'iecmOrgID'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxEqIecmOrgList'
                                    },
                                    placeholder: '请选择',
                                    span: 5
                                },
                                {
                                    type: 'date',
                                    label: '开始时间',
                                    field: 'beginDate',
                                    span: 5
                                },
                                {
                                    type: 'date',
                                    label: '结束时间',
                                    field: 'endDate',
                                    span: 5
                                },
                                {
                                    label: '物资编码',
                                    field: 'resID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'orgName',
                                        value: 'iecmOrgID'
                                    },
                                    fetchConfig: {
                                        apiName: 'getZxEqIecmOrgList'
                                    },
                                    placeholder: '请选择',
                                    span: 5
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                           
                                                if (value.orgID) {
                                                    this.setState({
                                                        orgID: '',
                                                        beginDate: '',
                                                        endDate: '',
                                                        resID: ''
                                                    }, () => {
                                                        this.setState({
                                                            orgID: value.orgID ?value.orgID:null,
                                                            beginDate: value.beginDate ? new Date(value.beginDate._d).getTime():null,
                                                            endDate: value.endDate ? new Date(value.endDate._d).getTime():null,
                                                            resID: value.resID ? value.resID:null
                                                        }, () => {
                                                            this.table.refresh();
                                                        })
                                                    })
                                                } else {
                                                    Msg.warn('请选择单位名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                             
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:zxSkTurnoverCheckList.xml&url=${domain}&orgID=${value.orgID? value.orgID :null}&beginDate=${new Date(value.beginDate ? value.beginDate._d:null).getTime()}&endDate=${new Date(value.endDate ? value.endDate._d :null).getTime()}&resID=${value.resID ? value.resID:null}`;
                                                    if (value.orgID) {
                                                        window.open(URL);
                                                    } else {
                                                        Msg.warn('请选择筛选条件！')
                                                    }
                                                }}>导出</Button></div>
                                        );
                                    },
                                    span: 4
                                }
                            ]}
                        />
                    </Col>
                </Row>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={orgID ? {
                        apiName: 'getUreportZxSkTurnoverCheckList',
                        otherParams: {
                            companyID: '1',
                            orgID: orgID,
                            beginDate: beginDate,
                            endDate: endDate,
                            resID: resID
                        }
                    } : {}}
                    actionBtns={[]}
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
                                title: '项目名称',
                                dataIndex: 'orgName',
                                width: 200,
                                tooltip: 23,
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '冲账单编号',
                                dataIndex: 'billNo',
                                width: 200,
                                tooltip: 23,
                                key: 'billNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发票号',
                                dataIndex: 'invoiceNo',
                                width: 200,
                                tooltip: 23,
                                key: 'invoiceNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '开票日期',
                                dataIndex: 'busDate',
                                key: 'busDate',
                                format: 'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资来源',
                                dataIndex: 'materialSource',
                                width: 200,
                                tooltip: 23,
                                key: 'materialSource'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '预收单编号',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'ysdNo',
                                key: 'ysdNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资编码',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'resCode',
                                key: 'resCode'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资名称',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'resName',
                                key: 'resName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '物资规格',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '单位',
                                width: 120,
                                dataIndex: 'resUnit',
                                key: 'resUnit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数量',
                                dataIndex: 'inQty',
                                key: 'inQty'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '含税单价',
                                dataIndex: 'inPrice',
                                key: 'inPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '不含税单价',
                                width: 120,
                                dataIndex: 'inPriceNoTax',
                                key: 'inPriceNoTax'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '原值',
                                width: 120,
                                dataIndex: 'inAmt',
                                key: 'inAmt'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '累计摊销',
                                width: 120,
                                dataIndex: 'feeSum',
                                key: 'feeSum'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '净值',
                                width: 120,
                                dataIndex: 'remainAmt',
                                key: 'remainAmt'
                            },
                            isInForm: false
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;