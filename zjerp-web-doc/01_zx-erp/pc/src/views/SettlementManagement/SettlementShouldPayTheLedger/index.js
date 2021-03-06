import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, Modal, message as Msg } from "antd";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'costRatioId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    pageSize: 99999999,
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
            formData: {
                departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : '') : curCompany?.projectId,
                periodTime: moment(new Date()).valueOf()
            }
        }
    }
    render () {
        const { departmentId, formData } = this.state;
        return (
            <div>
                <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    data={formData}
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
                            field: 'departmentId',
                            type: 'select',
                            showSearch: true,
                            allowClear: formData?.departmentId ? false : true,
                            required: true,
                            optionConfig: {
                                label: 'departmentName',
                                value: 'departmentId',
                                linkageFields: {
                                    departmentName: 'departmentName'
                                }
                            },
                            fetchConfig: {
                                apiName: 'getSysProjectBySelect',
                                otherParams: {
                                    departmentId: departmentId
                                }
                            },
                            span: 8
                        },
                        {
                            field: 'departmentName',
                            type: 'string',
                            hide: true
                        },
                        {
                            type: 'month',
                            label: '??????',
                            allowClear: false,
                            required: true,
                            field: 'periodTime',
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'contractType',
                            type: 'select',
                            optionConfig: {
                                label: 'itemName',
                                value: 'itemId'
                            },
                            fetchConfig: {
                                apiName: 'getBaseCodeSelect',
                                otherParams: {
                                    itemId: 'categorycm1001500'
                                }
                            },
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'contractNo',
                            type: 'string',
                            span: 8
                        },
                        {
                            type: 'component',
                            field: 'component',
                            Component: obj => {
                                return (
                                    <div style={{ padding: '10px' }}><Button type="primary" onClick={() => {
                                        this.form.form.validateFields().then((value) => {
                                            value.periodTime = moment(value.periodTime).valueOf();
                                            this.setState({
                                                formData: value
                                            })
                                        })
                                    }}>??????</Button>
                                        <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                            confirm({
                                                content: '??????????????????????',
                                                onOk: () => {
                                                    let value = this.form.form.getFieldsValue();
                                                    value.periodTime = moment(value.periodTime).valueOf();
                                                    let { myPublic: { appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                                    value.reportUrl = ureport;
                                                    value.accessToken = access_token;
                                                    this.props.myFetch('exportZxProjectSettleAccountsPayable', value).then(
                                                        ({ success, message, data }) => {
                                                            if (success) {
                                                                window.open(data);
                                                            } else {
                                                                Msg.error(message);
                                                            }
                                                        }
                                                    );
                                                }
                                            })
                                        }}>??????</Button></div>
                                );
                            },
                            span: 8
                        }
                    ]}
                />
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
                        apiName: 'getZxProjectSettleAccountsPayable',
                        otherParams: { ...formData }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'costRatioId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractNo',
                                width: 150,
                                key: 'contractNo',
                                fixed: 'left'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractType',
                                width: 120,
                                key: 'contractType',
                                fixed: 'left',
                                // type:'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeList',
                                    otherParams: {
                                        departmentId: 'categorycm1001500'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'secondName',
                                width: 150,
                                key: 'secondName'
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'secondPrincipal',
                                width: 100,
                                key: 'secondPrincipal'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'content',
                                width: 150,
                                key: 'content'
                            }
                        },
                        {
                            table: {
                                title: '????????????(???)',
                                dataIndex: 'contractCost',
                                width: 120,
                                key: 'contractCost'
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'billType',
                                width: 120,
                                key: 'billType',
                                // type:'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeList',
                                    otherParams: {
                                        departmentId: 'category_settle01'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '????????????????????????',
                                dataIndex: 'upBillNo',
                                width: 150,
                                key: 'upBillNo'
                            }
                        },
                        {
                            table: {
                                title: '???????????????',
                                dataIndex: 'billNo',
                                width: 150,
                                key: 'billNo'
                            }
                        },
                        {
                            table: {
                                title: '????????????(???)',
                                dataIndex: 'title_1',
                                key: 'title_1',
                                width: 600,
                                children: [
                                    {
                                        title: '???????????????',
                                        dataIndex: 'thisAmtNoTax',
                                        key: 'thisAmtNoTax',
                                        width: 100
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'thisAmtTax',
                                        key: 'thisAmtTax',
                                        width: 100
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'thisAmt',
                                        key: 'thisAmt',
                                        width: 100
                                    },
                                    {
                                        title: '???????????????',
                                        dataIndex: 'totalAmtNoTax',
                                        key: 'totalAmtNoTax',
                                        width: 100
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'totalAmtTax',
                                        key: 'totalAmtTax',
                                        width: 100
                                    },
                                    {
                                        title: '????????????',
                                        dataIndex: 'totalAmt',
                                        key: 'totalAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '???????????????(???)',
                                dataIndex: 'title_2',
                                key: 'title_2',
                                width: 200,
                                children: [
                                    {
                                        title: '???????????????',
                                        dataIndex: 'title_21',
                                        key: 'title_21',
                                        width: 200,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'thisKcAmt',
                                                key: 'thisKcAmt',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'totalKcAmt',
                                                key: 'totalKcAmt',
                                                width: 100
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '???????????????(???)',
                                dataIndex: 'title_3',
                                key: 'title_3',
                                width: 200,
                                children: [
                                    {
                                        title: '???????????????',
                                        dataIndex: 'title_31',
                                        key: 'title_31',
                                        width: 200,
                                        children: [
                                            {
                                                title: '??????',
                                                dataIndex: 'thisFhAmt',
                                                key: 'thisFhAmt',
                                                width: 100
                                            },
                                            {
                                                title: '??????',
                                                dataIndex: 'totalFhAmt',
                                                key: 'totalFhAmt',
                                                width: 100
                                            }
                                        ]
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '?????????(???)',
                                dataIndex: 'title_5',
                                key: 'title_5',
                                width: 200,
                                children: [
                                    {
                                        title: '??????',
                                        dataIndex: 'thisYfgckAmt',
                                        key: 'thisYfgckAmt',
                                        width: 100
                                    },
                                    {
                                        title: '??????',
                                        dataIndex: 'totalYfgckAmt',
                                        key: 'totalYfgckAmt',
                                        width: 100
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'remark',
                                key: 'remark',
                                width: 150
                            },
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;