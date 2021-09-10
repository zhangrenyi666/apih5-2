import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { Form } from '@ant-design/compatible';
import '@ant-design/compatible/assets/index.css';
import { message as Msg } from 'antd';
const config = {
    antd: {
        rowKey: 'depositDetailId',
        size: "small"
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 18 }
        }
    },
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: '900px'
    },
    firstRowIsSearch: true,
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'depositDetailId',
                hide: true,
                type: 'string',
            }
        },
        {
            table: {
                title: '公司名称',
                dataIndex: 'companyName',
                key: 'companyName',
                fieldsConfig: {
                    field: "companyName",
                    type: "string",
                    placeholder: "请输入",
                },
            },
            isInForm: false
        },
        {
            table: {
                title: '工程名称',
                dataIndex: 'projectName',
                key: 'projectName',
                fieldsConfig: {
                    field: "projectName",
                    type: "string",
                    placeholder: "请输入",
                },
            },
            isInForm: false
        },
        {
            table: {
                title: '中标单位名称',
                dataIndex: 'winUnitName',
                key: 'winUnitName',
                fieldsConfig: {
                    field: "winUnitName",
                    type: "string",
                    placeholder: "请输入",
                },
            },
            isInForm: false
        },
        {
            table: {
                title: '业主名称',
                dataIndex: 'ownerName',
                key: 'ownerName',
                fieldsConfig: {
                    field: "ownerName",
                    type: "string",
                    placeholder: "请输入",
                },
            },
            isInForm: false
        },
        {
            table: {
                title: '保证金类别',
                dataIndex: 'depositTypeId',
                key: 'depositTypeId',
                type: "select",
                optionConfig: {
                    label: "label",
                    value: "value"
                },
                optionData: [
                    {
                        value: "0",
                        label: "投标保证金"
                    },
                    {
                        value: "1",
                        label: "履约保证金"
                    },
                    {
                        value: "2",
                        label: "农民工工资保证金"
                    },
                    {
                        value: "3",
                        label: "其他保证金"
                    }
                ],
                fieldsConfig: {
                    field: "depositTypeId",
                    type: "select",
                    placeholder: "请选择",
                    optionConfig: {
                        label: "label",
                        value: "value"
                    },
                    optionData: [
                        {
                            value: "0",
                            label: "投标保证金"
                        },
                        {
                            value: "1",
                            label: "履约保证金"
                        },
                        {
                            value: "2",
                            label: "农民工工资保证金"
                        },
                        {
                            value: "3",
                            label: "其他保证金"
                        }
                    ]
                },
            },
            isInForm: false
        },
        {
            table: {
                noHaveSearchInput: true,
                title: '<center>保证金缴纳时间</center>',
                dataIndex: 'startTime',
                key: 'startTime',
                format: "YYYY-MM-DD",
            },
            isInForm: false
        },
        {
            table: {
                noHaveSearchInput: true,
                title: '<center>保证金到期时间</center>',
                dataIndex: 'endTime',
                key: 'endTime',
                format: "YYYY-MM-DD",
            },
            isInForm: false
        },
        {
            table: {
                noHaveSearchInput: true,
                title: '<center>保证金金额</center>',
                dataIndex: 'depositMoney',
                key: 'depositMoney'
            },
            isInForm: false
        }
    ]
};

class index extends Component {
    render() {
        let { myPublic: { domain, appInfo: { ureport } } } = this.props;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    firstRowIsSearch={true}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjFlowDepositStandingBookList'
                    }}
                    {...config}
                    actionBtns={[
                        {
                            name: "diy",
                            type: "primary",
                            icon: "export",
                            label: "导出",
                            onClick: (obj) => {
                                if (obj.selectedRows.length) {
                                    this.props.myFetch('exportZjFlowDepositStandingBookList', obj.selectedRows).then(({ success, data, message }) => {
                                        if (success) {
                                            Msg.success(message);
                                            window.location.href = data;
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }else{
                                    obj._formData.searchParams.chooseFlag = '1';
                                    this.props.myFetch('exportZjFlowDepositStandingBookList', [obj._formData.searchParams]).then(({ success, data, message }) => {
                                        if (success) {
                                            Msg.success(message);
                                            window.location.href = data;
                                        } else {
                                            Msg.error(message);
                                        }
                                    });
                                }
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default Form.create()(index);

