import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import moment from 'moment';
import { Button, Row, Col, Modal } from "antd";
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
            comID: '',
            inDate: null,
            outDate: null
        }
    }
    render() {
        const { orgID, comID, inDate, outDate } = this.state;
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
                                    label: '公司名称',
                                    field: 'comID',
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
                                    type: 'month',
                                    label: '统计开始期次',//缺少字段
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '统计结束期次',//缺少字段
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
                                                    comID: '',
                                                    resCode: '',
                                                    inDate: null,
                                                    outDate: null
                                                }, () => {
                                                    this.setState({
                                                        orgID: value.orgID ? value.orgID : null,
                                                        comID: value.comID ? value.comID : null,
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
                                                            value.comID && filter.push('&comID=' + value.comID)
                                                            value.inDate && filter.push('&inDate=' + moment(value.inDate).valueOf())
                                                            value.outDate && filter.push('&outDate=' + moment(value.outDate).valueOf())
                                                            var URL = `${ureport}excel?_u=file:租用外部机械设备使用情况表.xml&url=${domain}&delFlag=0${filter.join('')}`;
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
                        otherParams: {
                            orgID: orgID,
                            comID: comID,
                            inDate: inDate,
                            outDate: outDate
                        }
                    }}
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
                                title: '公司名称',
                                dataIndex: 'comName',
                                key: 'comName   ',
                                width: 200
                            },
                            isInForm: false
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
                                title: '功率KW',
                                width: 180,
                                tooltip: 23,
                                dataIndex: 'power',
                                key: 'power'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '生产厂家',
                                width: 120,
                                dataIndex: 'outfactory',
                                key: 'outfactory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出厂日期',
                                width: 120,
                                dataIndex: 'outfactoryDate',
                                key: 'outfactoryDate',
                                format: 'YYYY-MM-DD',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '原值（元）',
                                width: 120,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
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
                                    label: 'itemName', //默认 label
                                    value: 'itemId',
                                    linkageFields: {
                                        placeName: 'itemName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'xingzhengquhuadaima'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '租赁期限（月）',
                                width: 120,
                                dataIndex: 'leaseLimit',
                                key: 'leaseLimit'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '使用时间',
                                dataIndex: 'A',
                                key: 'A',
                                children: [
                                    {
                                        title: '起始时间',
                                        dataIndex: 'inDateStr',
                                        key: 'inDateStr',
                                        width: 120,
                                    },
                                    {
                                        title: '结束时间',
                                        dataIndex: 'outDateStr',
                                        key: 'outDateStr',
                                        width: 120,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '租赁价格（元/月）',
                                width: 160,
                                dataIndex: 'leaseprice',
                                key: 'leaseprice'
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
                                title: '备注',
                                width: 150,
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