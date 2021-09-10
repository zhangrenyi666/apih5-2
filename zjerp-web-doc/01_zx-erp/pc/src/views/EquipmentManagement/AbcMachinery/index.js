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
    paginationConfig: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
        }
    }
    render() {
        const { departmentId } = this.state;
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
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
                                                this.table.refresh();
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.purDate && filter.push('&purDate=' + moment(value.purDate).valueOf())
                                                            filter.push('&departmentParentId=9999999999')
                                                            filter.push('&useOrgID=' + departmentId)
                                                            var URL = `${ureport}excel?_u=minio:zxEqEquipListForMainABCDCase.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=主要机械设备ABC情况表`;
                                                            console.log(URL);
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
                    pageSize={9999}
                    fetchConfig={{
                        apiName: 'getZxEqEquipListForMainABCDCase',
                        otherParams: () => {
                            const purDate = this.formHasTicket?.form?.getFieldValue('purDate')
                            return {
                                useOrgID: departmentId,
                                purDate: moment(purDate).valueOf(),
                                departmentParentId: '9999999999'
                            }
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
                                    },
                                    {
                                        title: 'A类',
                                        dataIndex: 'numA',
                                        key: 'numA',
                                    },
                                    {
                                        title: 'B类',
                                        dataIndex: 'numB',
                                        key: 'numB',
                                    },
                                    {
                                        title: 'C类',
                                        dataIndex: 'numC',
                                        key: 'numC',
                                    },
                                    {
                                        title: 'D类',
                                        dataIndex: 'numD',
                                        key: 'numD',
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
                                    },
                                    {
                                        title: 'A类',
                                        dataIndex: 'orgA',
                                        key: 'orgA',
                                    },
                                    {
                                        title: 'B类',
                                        dataIndex: 'orgB',
                                        key: 'orgB',
                                    },
                                    {
                                        title: 'C类',
                                        dataIndex: 'orgC',
                                        key: 'orgC',
                                    },
                                    {
                                        title: 'D类',
                                        dataIndex: 'orgD',
                                        key: 'orgD',
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
                                    },
                                    {
                                        title: 'A类',
                                        dataIndex: 'leftA',
                                        key: 'leftA',
                                    },
                                    {
                                        title: 'B类',
                                        dataIndex: 'leftB',
                                        key: 'leftB',
                                    },
                                    {
                                        title: 'C类',
                                        dataIndex: 'leftC',
                                        key: 'leftC',
                                    },
                                    {
                                        title: 'D类',
                                        dataIndex: 'leftD',
                                        key: 'leftD',
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