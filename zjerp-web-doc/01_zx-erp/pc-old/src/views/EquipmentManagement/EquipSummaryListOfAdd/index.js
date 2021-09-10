import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, Row, Col } from "antd";
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxEqEquipLimitPriceId',
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
            purDate: '',
        }
    }
    componentDidMount() {

    }
    render() {
        const { orgID, purDate } = this.state;
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
                                    label: '公司',
                                    field: 'orgID',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject',
                                        otherParams: {
                                            departmentId
                                        }
                                    },
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '年度',
                                    field: 'purDate',
                                    type: 'year',
                                    placeholder: '请选择',
                                    span: 8,
                                    initialValue:new Date()
                                },
                                {
                                    type: 'component',
                                    field: 'aa',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                                                let value = this.formHasTicket.form.getFieldsValue();
                                                this.setState({
                                                    orgID: value.orgID ? value.orgID : null,
                                                    purDate: value.purDate ? moment(value.purDate).valueOf() : null,
                                                }, () => {
                                                    this.table.refresh();
                                                })
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                    let filter = []
                                                    value.orgID && filter.push('&orgID=' + value.orgID)
                                                    value.purDate && filter.push('&purDate=' + moment(value.purDate).valueOf())
                                                    var URL = `${ureport}excel?_u=file:addZxEqEquipTotalList.xml&url=${domain}&delFlag=0${filter.join('')}`;
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
                        apiName: 'getAddZxEqEquipTotalList',
                        otherParams: {
                            companyId: orgID,
                            purDate: purDate,
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
                                title: '类',
                                dataIndex: 'abcTypeName',
                                key: 'abcTypeName',
                                width: 100,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '管理编号',
                                dataIndex: 'financeNo',
                                key: 'financeNo',
                                width: 170,
                                tooltip: 17
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '机械名称',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                width: 150,
                                tooltip: 15
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '型号',
                                dataIndex: 'model',
                                key: 'model',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '规格',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 150,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '生产厂家',
                                dataIndex: 'factory',
                                key: 'factory',
                                width: 170,
                                tooltip: 17
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '出厂日期',
                                dataIndex: 'outFactoryDate',
                                key: 'outFactoryDate',
                                width: 120,
                                format: 'YYYY-MM-DD',

                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '主机功率',
                                dataIndex: 'mainpower',
                                key: 'mainpower',
                                width: 120,
                                type: 'number'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '国产总价(元)',
                                width: 150,
                                dataIndex: 'orginalValue',
                                key: 'orginalValue'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '使用单位',
                                width: 150,
                                dataIndex: 'useOrgName',
                                key: 'useOrgName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '购置日期',
                                width: 120,
                                dataIndex: 'purDate',
                                key: 'purDate',
                                format: 'YYYY-MM-DD',

                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '车辆牌照号',
                                width: 150,
                                dataIndex: 'passNo',
                                key: 'passNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '折旧年限',
                                width: 200,
                                dataIndex: 'depreciation',
                                key: 'depreciation',
                                render: (h) => {
                                    if (h === '5') {
                                        return '5年'
                                    } else if (h === '10') {
                                        return '10年'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 200,
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