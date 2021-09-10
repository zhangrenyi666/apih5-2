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
                                    label: '单位名称',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '项目名称',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '设备名称',
                                    field: 'equipName',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '型号',
                                    field: 'spec',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    type: 'string',
                                    label: '使用地点',
                                    field: 'place',
                                    placeholder: '请输入',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '开始时间',
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'date',
                                    label: '结束时间',
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
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
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
                                                            var URL = `${ureport}excel?_u=file:租赁车辆配备情况统计表(月报).xml&url=${domain}&delFlag=0&ureportFlag=1&${filter.join('')}`;
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
                                title: '项目名称',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                            },
                        },
                        {
                            table: {
                                title: '开竣工日期',
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
                                title: '设备名称',
                                width: 200,
                                tooltip: 23,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '型号',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格',
                                width: 120,
                                dataIndex: 'model',
                                key: 'model'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '功率',
                                width: 120,
                                dataIndex: 'power',
                                key: 'power'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '生产厂家',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'outfactory',
                                key: 'outfactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出厂日期',
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
                                title: '原值（元）',
                                width: 120,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue',
                                type:'number'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '使用地点',
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
                                title: '租赁起始时间',
                                width: 120,
                                dataIndex: 'inDate',
                                key: 'inDate',
                                format:'YYYY-MM-DD'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '租赁结束时间',
                                width: 120,
                                dataIndex: 'outDate',
                                key: 'outDate',
                                format:'YYYY-MM-DD'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '租赁价格（元/月）',
                                width: 150,
                                dataIndex: 'leaseprice',
                                key: 'leaseprice',
                                type:'number'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '租赁公司/机主姓名',
                                width: 180,
                                dataIndex: 'supOperator',
                                key: 'supOperator'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '牌照号',
                                width: 150,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
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