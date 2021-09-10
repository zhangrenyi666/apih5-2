import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import moment from 'moment';
import { Button, Row, Col, Modal } from "antd";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'id',
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
        let { myPublic: { appInfo: { ureport } } } = this.props;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        const { departmentId } = this.state;
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
                                    field: 'orgIDSearch',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyBySelect',
                                        otherParams: { departmentId }
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
                                            itemId: 'shengfenjianchengdaima'
                                        }
                                    },
                                    showSearch: true,
                                    placeholder: '请选择',
                                    span: 8
                                },
                                {
                                    type: 'halfYear',
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
                                                this.table.refresh();
                                            }}>查询</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '确定导出数据吗?',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.province && filter.push('&province=' + value.province)
                                                            value.orgIDSearch && filter.push('&orgIDSearch=' + value.orgIDSearch)
                                                            value.periodDate && filter.push('&periodDate=' + moment(value.periodDate).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:zxEqEquipLimitPriceVO.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=设备租赁限价采集报表`;
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
                        apiName: 'ureportZxEqEquipLimitPriceVOIdle',
                        otherParams: () => {
                            let selectData = this.formHasTicket?.form?.getFieldsValue()
                            return {
                                province: selectData?.province,
                                orgIDSearch: selectData?.orgIDSearch,
                                periodDate: selectData?.periodDate ? moment(selectData.periodDate).valueOf() : null,
                                orgID: departmentId
                            }
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
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '设备名称',
                                dataIndex: 'equipName',
                                width: 200,
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所在省份',
                                width: 150,
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
                                        itemId: 'shengfenjianchengdaima'
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '厂家',
                                dataIndex: 'factory',
                                width: 150,
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