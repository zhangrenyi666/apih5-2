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
                                    label: '????????????',
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
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    label: '??????',
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
                                    placeholder: '?????????',
                                    span: 8
                                },
                                {
                                    type: 'halfYear',
                                    label: '??????',
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
                                            }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    confirm({
                                                        content: '??????????????????????',
                                                        onOk: () => {
                                                            let value = this.formHasTicket.form.getFieldsValue();
                                                            let filter = []
                                                            value.province && filter.push('&province=' + value.province)
                                                            value.orgIDSearch && filter.push('&orgIDSearch=' + value.orgIDSearch)
                                                            value.periodDate && filter.push('&periodDate=' + moment(value.periodDate).valueOf())
                                                            var URL = `${ureport}excel?_u=minio:zxEqEquipLimitPriceVO.xml&access_token=${access_token}&delFlag=0${filter.join('')}&_n=??????????????????????????????`;
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
                                title: '????????????',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                width: 200,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'equipName',
                                width: 200,
                                key: 'orgName'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
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
                                title: '??????',
                                dataIndex: 'factory',
                                width: 150,
                                key: 'factory'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'spec',
                                key: 'spec',
                                width: 120,
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'workTime',
                                key: 'workTime',
                                render: (h) => {
                                    if (h === '0') {
                                        return '??????'
                                    } else if (h === '1') {
                                        return '??????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'rentContent',
                                key: 'rentContent',
                                render: (h) => {
                                    if (h === '0') {
                                        return '???????????????'
                                    } else if (h === '1') {
                                        return '???????????????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 150,
                                dataIndex: 'ranyouQingkuang',
                                key: 'ranyouQingkuang',
                                render: (h) => {
                                    if (h === '0') {
                                        return '????????????'
                                    } else if (h === '1') {
                                        return '????????????'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????????????????/???.??????',
                                width: 170,
                                dataIndex: 'rentPrice',
                                key: 'rentPrice'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '???????????????',
                                width: 170,
                                dataIndex: 'isDriver',
                                key: 'isDriver',
                                render: (h) => {
                                    if (h === '0') {
                                        return '???'
                                    } else if (h === '1') {
                                        return '???'
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '??????',
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