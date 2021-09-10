import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import Apih5 from "qnn-apih5"
const config = {
    antd: {
        rowKey: 'enterpriseAnnualSalaryUpLimitId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,

};
class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {}
    }
    componentDidMount() { }
    render() {
        return (
            <div>
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
                        apiName: 'getZjXmSalaryEnterpriseAnnualSalaryUpLimitList',
                        otherParams: {}
                    }}
                    method={{}}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'enterpriseAnnualSalaryUpLimitId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目所在地',
                                width: 120,
                                dataIndex: 'areaName',
                                key: 'areaName'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                field: 'areaName'
                            }
                        },
                        {
                            table: {
                                title: '抵扣上限金额',
                                onClick: 'detail',
                                dataIndex: 'upLimitAmt',
                                key: 'upLimitAmt'
                            },
                            form: {
                                field: 'upLimitAmt',
                                required: true,
                                type: 'number',
                                placeholder: '请输入'

                            },
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks'
                            },
                            form: {
                                type: 'textarea',
                                field: 'remarks'
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                showType: 'tile',
                                width: 80,
                                title: "操作",
                                key: "action",
                                fixed: 'right',
                                btns: [
                                    {
                                        name: "edit",
                                        render: function () {
                                            return "<a>修改</a>";
                                        },
                                        formBtns: [
                                            {
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                name: "submit",
                                                type: "primary",
                                                label: "提交",
                                                fetchConfig: {
                                                    apiName: "updateZjXmSalaryEnterpriseAnnualSalaryUpLimit"
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }

                    ]}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZjXmSalaryEnterpriseAnnualSalaryUpLimit'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjXmSalaryEnterpriseAnnualSalaryUpLimit',
                            },
                            type: 'danger',
                            label: '删除',
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;