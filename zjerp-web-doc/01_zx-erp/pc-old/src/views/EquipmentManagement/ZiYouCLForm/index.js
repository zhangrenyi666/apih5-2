import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button,  Row, Col, Modal } from "antd";
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
        width: window.screen.width * 0.7
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
            ownerOrgId: '',
            purDateStart: null,
            purDateEnd: null,
            ownerOrgName: '',
            manageOrgID: '',
            equipName: ''
        }
    }
    componentDidMount() {

    }
    render() {
        const { ownerOrgId, purDateStart, purDateEnd, ownerOrgName, manageOrgID, equipName} = this.state;
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
                                    field: 'ownerOrgId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject'
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '????????????',
                                    field: 'purDateStart',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '????????????',
                                    field: 'purDateEnd',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'ownerOrgName',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect'
                                    },
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'manageOrgID',
                                    type: 'string',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'equipName',
                                    type: 'string',
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
                                                    ownerOrgId: '',
                                                    purDateStart: null,
                                                    purDateEnd: null,
                                                    ownerOrgName: '',
                                                    manageOrgID: '',
                                                    equipName: ''
                                                }, () => {
                                                    this.setState({
                                                        ownerOrgId: value.ownerOrgId ? value.ownerOrgId : null,
                                                        purDateStart: value.purDateStart ? moment(value.purDateStart).valueOf() : null,
                                                        purDateEnd: value.purDateEnd ? moment(value.purDateEnd).valueOf() : null,
                                                        ownerOrgName: value.ownerOrgName ? value.ownerOrgName : '',
                                                        manageOrgID: value.manageOrgID ? value.manageOrgID : '',
                                                        equipName: value.equipName ? value.equipName : '',
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
                                                            value.ownerOrgId && filter.push('&ownerOrgId=' + value.ownerOrgId)
                                                            value.purDateStart && filter.push('&purDateStart=' + moment(value.purDateStart).valueOf())
                                                            value.purDateEnd && filter.push('&purDateEnd=' + moment(value.purDateEnd).valueOf())
                                                            value.purDate && filter.push('&purDate=' + moment(value.purDate).valueOf())
                                                            value.ownerOrgName && filter.push('&ownerOrgName=' + value.ownerOrgName)
                                                            value.manageOrgID && filter.push('&manageOrgID=' + value.manageOrgID)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            var URL = `${ureport}excel?_u=file:zxEqEquipTotalListForCar.xml&url=${domain}&delFlag=0&selectTypeFlag=car${filter.join('')}`;
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
                        apiName: 'getZxEqEquipTotalListForCar',
                        otherParams: {
                            ownerOrgId: ownerOrgId,
                            purDateStart: purDateStart,
                            purDateEnd: purDateEnd,
                            ownerOrgName: ownerOrgName,
                            manageOrgID: manageOrgID,
                            equipName: equipName
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
                                title: '????????????',
                                dataIndex: 'ownerOrgName',
                                key: 'ownerOrgName',
                                width: 200,
                                tooltip: 20,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'manageOrgID',
                                width: 200,
                                tooltip: 23,
                                key: 'manageOrgID'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                tooltip: 15,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 120,
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 180,
                                tooltip: 18,
                                dataIndex: 'factory',
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'outFactoryDate',
                                key: 'outFactoryDate'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'acceptanceDate',
                                key: 'acceptanceDate'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                dataIndex: 'originvalue',
                                key: 'originvalue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 120,
                                dataIndex: 'leftValue',
                                key: 'leftValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width:200,
                                tooltip:20,
                                dataIndex: 'remark',
                                key: 'remark'
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