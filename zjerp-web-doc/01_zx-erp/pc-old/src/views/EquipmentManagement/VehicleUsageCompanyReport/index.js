import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
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
            resCatalogID: '',
            equipName: '',
            inDate: null,
            outDate: null
        }
    }
    componentDidMount() {

    }
    render() {
        const { orgID, resCatalogID, equipName, inDate, outDate } = this.state;
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                                    label: '????????????',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject',
                                        otherParams: {
                                            departmentId
                                        }
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '????????????',
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '????????????',
                                    field: 'outDate',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'resCatalogID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'catName',
                                        value: 'id',
                                    },
                                    fetchConfig: {
                                        apiName: "getZxEqResCategoryList",
                                        otherParams: {
                                            parentID: '0003'
                                        }
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    type: 'string',
                                    field: 'equipName',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                this.setState({
                                                    orgID: '',
                                                    resCatalogID: '',
                                                    equipName: '',
                                                    inDate: null,
                                                    outDate: null
                                                }, () => {
                                                    this.setState({
                                                        orgID: value.orgID ? value.orgID : null,
                                                        resCatalogID: value.resCatalogID ? value.resCatalogID : null,
                                                        equipName: value.equipName ? value.equipName : null,
                                                        inDate: value.inDate ? new Date(value.inDate._d).getTime() : null,
                                                        outDate: value.outDate ? new Date(value.outDate._d).getTime() : null,
                                                    }, () => {
                                                        this.table.refresh();
                                                    })
                                                })
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '??????????????????????',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.orgID && filter.push('&orgID=' + value.orgID)
                                                            value.resCatalogID && filter.push('&resCatalogID=' + value.resCatalogID)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            value.inDate && filter.push('&inDate=' + moment(value.inDate).valueOf())
                                                            value.outDate && filter.push('&outDate=' + moment(value.outDate).valueOf())
                                                            var URL = `${ureport}excel?_u=file:??????????????????????????????.xml&url=${domain}&delFlag=0${filter.join('')}`;
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
                        apiName: 'getZxEqEWorkListForReport',
                        otherParams: { orgID, resCatalogID, equipName, inDate, outDate }
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
                                dataIndex: 'resCatalogName',
                                key: 'resCatalogName',
                                width: 150
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'equipNo',
                                width: 170,
                                tooltip: 20,
                                key: 'equipNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 180,
                                tooltip: 180,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'calendarNumDay',
                                key: 'calendarNumDay'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'A',
                                key: 'A',
                                children: [
                                    {
                                        title: '????????????',
                                        dataIndex: 'wellDays',
                                        key: 'wellDays',
                                        width: 130,
                                    },
                                    {
                                        title: '?????????',
                                        dataIndex: 'wellPercen',
                                        key: 'wellPercen',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'B',
                                key: 'B',
                                children: [
                                    {
                                        title: '????????????',
                                        dataIndex: 'runDay',
                                        key: 'runDay',
                                        width: 130,
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'runHour',
                                        key: 'runHour',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 130,
                                dataIndex: 'runDayPercen',
                                key: 'runDayPercen'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 130,
                                dataIndex: 'loadMiles',
                                key: 'loadMiles'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????????????????',
                                dataIndex: 'C',
                                key: 'C',
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'gasoline',
                                        key: 'gasoline',
                                        width: 130,
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'diesel',
                                        key: 'diesel',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 130,
                                dataIndex: 'consumption',
                                key: 'consumption'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 180,
                                dataIndex: 'remark',
                                key: 'remark'
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;