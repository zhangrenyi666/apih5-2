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
            purDate: '',
        }
    }
    componentDidMount() {

    }
    render() {
        const { purDate } = this.state;
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
                                                            value.purDate && filter.push('&purDate=' + moment(value.purDate).valueOf())
                                                            var URL = `${ureport}preview?_u=file:zxEqEquipListForMainABCDCase.xml&url=${domain}&delFlag=0${filter.join('')}`;
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
                        apiName: 'getZxEqEquipListForMainABCDCase',
                        otherParams: {
                            departmentId,
                            purDate: purDate,
                            departmentParentId: '9999999999'
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '单位名称',
                                dataIndex: 'departmentName',
                                key: 'departmentName',
                                width: 200,
                                fixed: 'left',
                            },
                        },
                        {
                            table: {
                                title: '期末机械台数',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 450,
                                children: [
                                    {
                                        title: '合计',
                                        dataIndex: 'numTotal',
                                        key: 'numTotal',
                                        filter: true,
                                    },
                                    {
                                        title: 'A类',
                                        dataIndex: 'numA',
                                        key: 'numA',
                                        filter: true,
                                    },
                                    {
                                        title: 'B类',
                                        dataIndex: 'numB',
                                        key: 'numB',
                                        filter: true,
                                    },
                                    {
                                        title: 'C类',
                                        dataIndex: 'numC',
                                        key: 'numC',
                                        filter: true,
                                    },
                                    {
                                        title: 'D类',
                                        dataIndex: 'numD',
                                        key: 'numD',
                                        filter: true,
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '期末原值(万元)',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 450,
                                children: [
                                    {
                                        title: '合计',
                                        dataIndex: 'orgTotal',
                                        key: 'orgTotal',
                                        filter: true,
                                    },
                                    {
                                        title: 'A类',
                                        dataIndex: 'orgA',
                                        key: 'orgA',
                                        filter: true,
                                    },
                                    {
                                        title: 'B类',
                                        dataIndex: 'orgB',
                                        key: 'orgB',
                                        filter: true,
                                    },
                                    {
                                        title: 'C类',
                                        dataIndex: 'orgC',
                                        key: 'orgC',
                                        filter: true,
                                    },
                                    {
                                        title: 'D类',
                                        dataIndex: 'orgD',
                                        key: 'orgD',
                                        filter: true,
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '期末净值(万元)',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 450,
                                children: [
                                    {
                                        title: '合计',
                                        dataIndex: 'leftTotal',
                                        key: 'leftTotal',
                                        filter: true,
                                    },
                                    {
                                        title: 'A类',
                                        dataIndex: 'leftA',
                                        key: 'leftA',
                                        filter: true,
                                    },
                                    {
                                        title: 'B类',
                                        dataIndex: 'leftB',
                                        key: 'leftB',
                                        filter: true,
                                    },
                                    {
                                        title: 'C类',
                                        dataIndex: 'leftC',
                                        key: 'leftC',
                                        filter: true,
                                    },
                                    {
                                        title: 'D类',
                                        dataIndex: 'leftD',
                                        key: 'leftD',
                                        filter: true,
                                    }
                                ]
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;