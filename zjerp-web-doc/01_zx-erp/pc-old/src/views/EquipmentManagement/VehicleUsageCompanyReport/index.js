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
                                    label: '公司名称',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '开始时间',
                                    field: 'inDate',
                                    span: 8
                                },
                                {
                                    type: 'month',
                                    label: '结束时间',
                                    field: 'outDate',
                                    span: 8
                                },
                                {
                                    label: '设备分类',
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
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    label: '机械名称',
                                    type: 'string',
                                    field: 'equipName',
                                    placeholder: '请输入',
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
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.orgID && filter.push('&orgID=' + value.orgID)
                                                            value.resCatalogID && filter.push('&resCatalogID=' + value.resCatalogID)
                                                            value.equipName && filter.push('&equipName=' + value.equipName)
                                                            value.inDate && filter.push('&inDate=' + moment(value.inDate).valueOf())
                                                            value.outDate && filter.push('&outDate=' + moment(value.outDate).valueOf())
                                                            var URL = `${ureport}excel?_u=file:机械车辆使用情况报表.xml&url=${domain}&delFlag=0${filter.join('')}`;
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
                                title: '分类',
                                dataIndex: 'resCatalogName',
                                key: 'resCatalogName',
                                width: 150
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '管理编号',
                                dataIndex: 'equipNo',
                                width: 170,
                                tooltip: 20,
                                key: 'equipNo'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '机械名称',
                                width: 180,
                                tooltip: 180,
                                dataIndex: 'equipName',
                                key: 'equipName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '技术规格',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '日历台日',
                                width: 120,
                                dataIndex: 'calendarNumDay',
                                key: 'calendarNumDay'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '完好情况',
                                dataIndex: 'A',
                                key: 'A',
                                children: [
                                    {
                                        title: '完好台日',
                                        dataIndex: 'wellDays',
                                        key: 'wellDays',
                                        width: 130,
                                    },
                                    {
                                        title: '完好率',
                                        dataIndex: 'wellPercen',
                                        key: 'wellPercen',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '运转情况',
                                dataIndex: 'B',
                                key: 'B',
                                children: [
                                    {
                                        title: '运转台日',
                                        dataIndex: 'runDay',
                                        key: 'runDay',
                                        width: 130,
                                    },
                                    {
                                        title: '运转台时',
                                        dataIndex: 'runHour',
                                        key: 'runHour',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '利用率',
                                width: 130,
                                dataIndex: 'runDayPercen',
                                key: 'runDayPercen'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '行驶里程',
                                width: 130,
                                dataIndex: 'loadMiles',
                                key: 'loadMiles'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '油耗情况（升）',
                                dataIndex: 'C',
                                key: 'C',
                                children: [
                                    {
                                        title: '汽油',
                                        dataIndex: 'gasoline',
                                        key: 'gasoline',
                                        width: 130,
                                    },
                                    {
                                        title: '柴油',
                                        dataIndex: 'diesel',
                                        key: 'diesel',
                                        width: 130,
                                    },

                                ]
                            }
                        },
                        {
                            table: {
                                title: '电消耗（度）',
                                width: 130,
                                dataIndex: 'consumption',
                                key: 'consumption'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
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