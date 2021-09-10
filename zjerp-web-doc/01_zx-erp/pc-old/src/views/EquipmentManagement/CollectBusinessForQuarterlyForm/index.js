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
                                    initialValue: new Date()
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
                                title: '指标',
                                dataIndex: 'abcTypeName3',
                                key: 'abcTypeName',
                                width: 80,
                                colSpan:2,
                                render: function (data, rowData, index) {
                                    const obj = {
                                        children: '自有施工机械设备状况',
                                        props: {},
                                    };
                                    if (index === 0) {
                                        obj.props.rowSpan = 4;
                                    } else {
                                        obj.props.rowSpan = 0;
                                    }
                                    return obj;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                dataIndex: 'abcTypeName1',
                                key: 'abcTypeName1',
                                width: 100,
                                colSpan:0,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '数值',
                                dataIndex: 'swrs',
                                align: 'center',
                                key: 'swrs',
                                children: [
                                    {
                                        title: '合计',
                                        dataIndex: 'abcTypeName3',
                                        key: 'abcTypeName',
                                        width: 100,
                                    },
                                    {
                                        title: '境内',
                                        dataIndex: 'abcTypeName1',
                                        key: 'abcTypeName',
                                        width: 100,
                                    },
                                    {
                                        title: '境外',
                                        dataIndex: 'abcTypeName2',
                                        key: 'abcTypeName',
                                        width: 100,
                                    },
                                ]
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