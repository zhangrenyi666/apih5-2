import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Row, Col, Modal} from "antd";
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
            ownerOrgId: '',
            equipName: '',
            spec: '',
            place: '',
            inDate: null,
            outDate: null
        }
    }
    componentDidMount() {

    }
    render() {
        const { orgID, ownerOrgId, equipName, spec, place, inDate, outDate } = this.state;
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
                                    label: '????????????',
                                    field: 'orgID',
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
                                    type: 'string',
                                    label: '????????????',
                                    field: 'equipName',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '??????',
                                    field: 'spec',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '????????????',
                                    field: 'place',
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '????????????',
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '????????????',
                                    field: 'outDate',
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
                                                    ownerOrgId: '',
                                                    equipName: '',
                                                    spec: '',
                                                    place: '',
                                                    inDate: null,
                                                    outDate: null
                                                }, () => {
                                                    this.setState({
                                                        orgID: value.orgID ? value.orgID : null,
                                                        ownerOrgId: value.ownerOrgId ? value.ownerOrgId : null,
                                                        equipName: value.equipName ? value.equipName : null,
                                                        spec: value.spec ? value.spec : null,
                                                        place: value.place ? value.place : null,
                                                        inDate: value.inDate ? moment(value.inDate).valueOf() : null,
                                                        outDate: value.outDate ? moment(value.outDate).valueOf() : null,
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
                                                            value.ownerOrgId && filter.push('&ownerOrgId=' + value.ownerOrgId)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            value.spec && filter.push('&spec=' + value.spec)
                                                            value.place && filter.push('&place=' + value.place)
                                                            value.inDate && filter.push('&inDate=' + moment(value.inDate).valueOf())
                                                            value.outDate && filter.push('&outDate=' + moment(value.outDate).valueOf())
                                                            var URL = `${ureport}excel?_u=file:?????????????????????????????????(??????).xml&url=${domain}&delFlag=0&ureportFlag=1&${filter.join('')}`;
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
                        apiName: 'getZxEqOuterEquipListForCar',
                        otherParams: { orgID, ownerOrgId, equipName, spec, place, inDate, outDate,ureportFlag :'1' }
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
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                            },
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'startEndDate',
                                width: 200,
                                tooltip: 23,
                                key: 'startEndDate',
                                format:'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                tooltip: 23,
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
                                title: '??????',
                                width: 120,
                                dataIndex: 'power',
                                key: 'power'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'outfactory',
                                key: 'outfactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                tooltip: 23,
                                dataIndex: 'outfactoryDate',
                                key: 'outfactoryDate',
                                format:'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 120,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue',
                                type:'number'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'place',
                                key: 'place',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                  label: 'itemName', 
                                  value: 'itemId'
                                },
                                fetchConfig: {
                                  apiName: 'getBaseCodeSelect',
                                  otherParams: {
                                    itemId: 'xingzhengquhuadaima'
                                  }
                                },
                              },
                        },
                        {
                            table: {
                                title: '??????????????????',
                                width: 120,
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format:'YYYY-MM-DD'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '??????????????????',
                                width: 120,
                                dataIndex: 'outDate',
                                key: 'outDate',
                                format:'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????/??????',
                                width: 150,
                                dataIndex: 'leaseprice',
                                key: 'leaseprice',
                                type:'number'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????/????????????',
                                width: 180,
                                dataIndex: 'supOperator',
                                key: 'supOperator'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 150,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
                                width: 150,
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