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
            province: '',
            orgID: '',
            periodDate: null
        }
    }
    render() {
        const { province, orgID, periodDate } = this.state;
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
                                    field: 'orgID',
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
                                    label: '省份',
                                    field: 'province',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId',
                                    },
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'xingzhengquhuadaima'
                                        }
                                    },
                                    showSearch: true,
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '期次',
                                    field: 'periodDate',
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
                                                    province: '',
                                                    orgID: '',
                                                    periodDate: null
                                                }, () => {
                                                    this.setState({
                                                        province: value.province ? value.province : null,
                                                        orgID: value.orgID ? value.orgID : null,
                                                        periodDate: value.periodDate ? moment(value.periodDate).valueOf() : null,
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
                                                            value.province && filter.push('&province=' + value.province)
                                                            value.orgID && filter.push('&orgID=' + value.orgID)
                                                            value.periodDate && filter.push('&periodDate=' + moment(value.periodDate).valueOf())
                                                            var URL = `${ureport}excel?_u=file:zxEqEquipLimitPriceVO.xml&url=${domain}&delFlag=0${filter.join('')}`;
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
                        apiName: 'ureportZxEqEquipLimitPriceVOIdle',
                        otherParams: { province, orgID, periodDate }
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
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                                tooltip: 20,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '设备名称',
                                dataIndex: 'equipName',
                                width: 200,
                                tooltip: 20,
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所在省份',
                                width: 150,
                                tooltip: 12,
                                dataIndex: 'province',
                                key: 'province',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: 'xingzhengquhuadaima'
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '厂家',
                                dataIndex: 'factory',
                                width: 150,
                                tooltip: 15,
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格型号',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '工作时间',
                                width: 120,
                                dataIndex: 'workTime',
                                key: 'workTime',
                                render: (h) => {
                                    if (h === '0') {
                                        return '单班'
                                    } else if (h === '1') {
                                        return '双班'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '租赁时间',
                                width: 120,
                                dataIndex: 'rentContent',
                                key: 'rentContent',
                                render: (h) => {
                                    if (h === '0') {
                                        return '六个月以下'
                                    } else if (h === '1') {
                                        return '六个月以上'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '燃油情况',
                                width: 150,
                                dataIndex: 'ranyouQingkuang',
                                key: 'ranyouQingkuang',
                                render: (h) => {
                                    if (h === '0') {
                                        return '甲方承担'
                                    } else if (h === '1') {
                                        return '乙方承担'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '租赁限价（元/台.月）',
                                width: 170,
                                dataIndex: 'rentPrice',
                                key: 'rentPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '是否含司机',
                                width: 170,
                                dataIndex: 'isDriver',
                                key: 'isDriver',
                                render: (h) => {
                                    if (h === '0') {
                                        return '否'
                                    } else if (h === '1') {
                                        return '是'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 150,
                                dataIndex: 'remarks',
                                key: 'remarks'
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