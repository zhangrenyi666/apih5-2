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
        // name sex opProjName sendName cardNo checkDate orgName
        this.state = {
            orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId,
            name:'',
            sex:'',
            opProjName:'',
            sendName:'',
            cardNo:'',
            checkDate:'',
            orgName:'',
            endDate:null
        }
    }
    componentDidMount() {

    }
    render() {
        const { orgID, opProjName, beginDate, endDate } = this.state;
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
                                    label: '????????????',
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
                                    placeholder: '?????????',
                                    span: 6
                                },
                                {
                                    type: 'date',
                                    label: '???????????? ',
                                    field: 'endDate', //?????????????????? ***??????
                                    placeholder: '?????????',
                                    required: false,
                                    format: "YYYY-MM-DD",
                                    showTime: false, //???????????????
                                    scope: false, //?????????????????????
                                    span: 6
                                },
                                {
                                    type: 'select',
                                    label: '???????????????',
                                    field: 'opProjName', //?????????????????? ***??????
                                    span:6,
                                    multiple: true,
                                    placeholder: '?????????',
                                    optionData: [
                                      { label: '????????????', value: '0' },
                                      { label: "????????????????????????", value: '1' },
                                      { label: '???????????????????????????', value: '2' },
                                      { label: "???????????????????????????", value: '3' },
                                      { label: '??????????????????', value: '4' },
                                      { label: "????????????", value: '5' },
                                      { label: '??????????????????', value: '6' },
                                      { label: "????????????", value: '7' },
                                      { label: '??????', value: '8' },
                                    ],
                                    optionConfig: {
                                      label: 'label',
                                      value: 'value',
                                    },
                                    placeholder: '?????????',
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
                                                            opProjName: value.opProjName.length ? value.opProjName.join(',') : '',
                                                            endDate:value.endDate? value.endDate :''
                                                        })
                                                        this.table.refresh()
                                                    }, 0)
                                                } else {
                                                    Msg.warn('????????????????????????')
                                                }
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    let value = this.formHasTicket.form.getFieldsValue();
                                                    var URL = `${ureport}excel?_u=file:ZxSkResInOutStockAllAmt.xml&url=${domain}&delFlag=0&orgID=${value.orgID ? value.orgID : null}&opProjName=${value.riskLevel ? value.riskLevel : ''}&endDate=${value.endDate? value.endDate :''}`;
                                                    if (value.orgID) {
                                                            window.open(URL);
                                                    } else {
                                                        Msg.warn('????????????????????????')
                                                    }
                                                }}>??????</Button></div>
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
                    fetchConfig={ {
                        apiName: 'getZxSfSpecialEmpItemDetailFormComList',
                        otherParams: {
                            orgID: orgID,
                            opProjName: opProjName,
                            beginDate: beginDate,
                            endDate: endDate,
                        }
                    } }
                    actionBtns={[]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxSfSpecialEmpItemId',
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
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'name',
                                key: 'name'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'sex',
                                key: 'sex',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                dataIndex: 'opProjName',
                                key: 'opProjName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'sendName',
                                key: 'sendName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'cardNo',
                                key: 'cardNo',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'checkDate',
                                key: 'checkDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'orgName',
                                key: 'orgName'
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