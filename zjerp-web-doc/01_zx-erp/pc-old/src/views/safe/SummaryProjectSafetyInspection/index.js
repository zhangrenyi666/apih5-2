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
            checkDate: null,
            checkContext: null,
            checkGroup: null,
            checkEmployee: null,
            riskNum: null,
            zGaiNum: null,
            remarks: null,
            orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId,
            startDate: null,
            endDate: null
        }
    }
    componentDidMount() {

    }
    render() {
        const { checkDate, checkContext, checkGroup, checkEmployee, riskNum, zGaiNum, remarks, orgID, startDate, endDate } = this.state;
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;

        const { companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                                    label: '项目名称',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'projectName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect'
                                    },
                                    placeholder: '请选择',
                                    span: 6
                                },
                                {
                                    type: 'date',
                                    label: '开始时间 ',
                                    field: 'startDate', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //不显示时间
                                    scope: false, //是否可选择范围
                                    span: 6
                                },
                                {
                                    type: 'date',
                                    label: '结束时间 ',
                                    field: 'endDate', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //不显示时间
                                    scope: false, //是否可选择范围
                                    span: 6
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'start', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                if (value.orgID) {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            orgID: value.orgID ? value.orgID : null,
                                                            startDate: value.startDate ? value.startDate : null,
                                                            endDate: value.endDate ? value.endDate : null
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('请选择项目名称！')
                                                }
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&orgID=${value.orgID ? value.orgID : null}&startDate=${value.startDate ? value.startDate : null}&endDate=${value.endDate ? value.endDate : null}`;
                                                    if (value.orgID) {
                                                        if (value.startDate&&value.endDate) {
                                                            window.open(URL);
                                                        } else {
                                                            Msg.warn('请选择开始、结束时间！')
                                                        }
                                                    } else {
                                                        Msg.warn('请选择项目名称！')
                                                    }
                                                }}>导出</Button></div>
                                        );
                                    },
                                    span: 24
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
                    fetchConfig={{
                        apiName: 'getFormZxSfCheckList',
                        otherParams: {
                            orgID: orgID,
                            startDate: startDate,
                            endDate: endDate
                        }
                    }}
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfCheckId',
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
                            isInForm: false
                        },
                        {
                            table: {
                                title: '检查日期',
                                dataIndex: 'checkDate',
                                key: 'checkDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '检查内容',
                                dataIndex: 'checkContext',
                                key: 'checkContext',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '检查组长',
                                width: 120,
                                dataIndex: 'checkGroup',
                                key: 'checkGroup'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '参检人员',
                                dataIndex: 'checkEmployee',
                                key: 'checkEmployee',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '存在隐患个数',
                                dataIndex: 'riskNum',
                                key: 'riskNum'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '整改完成个数',
                                dataIndex: 'zGaiNum',
                                key: 'zGaiNum'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks'
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