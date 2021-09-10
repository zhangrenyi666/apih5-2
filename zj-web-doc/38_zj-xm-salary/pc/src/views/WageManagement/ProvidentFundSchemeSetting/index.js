import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg } from 'antd';
import Apih5 from "qnn-apih5"
const config = {
    antd: {
        rowKey: 'zjXmSalaryProvidentFundSchemeId',
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
        const orgId = this.apih5.getOrgId();
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
                        apiName: 'getZjXmSalaryProvidentFundSchemeList',
                        otherParams: {
                            orgId: orgId
                        }
                    }}
                    rowSelection={{
                        type: 'radio',
                        // selectedRowsStyle:{backgroundColor:'#ccc'}
                    }}
                    method={{}}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjXmSalaryProvidentFundSchemeId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'orgId',
                                type: 'string',
                                initialValue: orgId,
                                hide: true,
                            }
                        },

                        {
                            table: {
                                title: '名称',
                                dataIndex: 'name',
                                key: 'name',
                            },
                            form: {
                                type: 'string',
                                required: true,
                                field: 'name'
                            }
                        },
                        {
                            table: {
                                title: '地区',
                                width: 120,
                                dataIndex: 'area',
                                key: 'area',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                required: true,
                                field: 'area',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    linkageFields: {
                                        areaName: 'itemName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeTree',
                                    otherParams: {
                                        itemId: 'xingzhengquhuadaima'
                                    }
                                },
                                onChange: (val, rowData) => {
                                    this.props.myFetch('checkZjXmSalaryProvidentFundSchemeArea', rowData.form.getFieldsValue()).then(({ data, success, message }) => {
                                        if (success) {
                                            if (data?.check === false) {
                                                Msg.warn(data.checkMessage + '  请重新选择！');
                                                setTimeout(() => {
                                                    rowData.form.setFieldsValue({
                                                        area: null
                                                    })
                                                }, 200)
                                            } else {

                                            }
                                        } else {
                                            Msg.error(message)
                                        }
                                    })
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'areaName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'status',
                                key: 'status',
                                type: 'select',
                                tdEdit: true,
                                tdEditCb: (obj) => {
                                    let ovj = {
                                        zjXmSalaryProvidentFundSchemeId: obj.newRowData.zjXmSalaryProvidentFundSchemeId,
                                        status: obj.newRowData.status
                                    }
                                    this.props.myFetch('updateZjXmSalaryProvidentFundSchemeStatus', ovj).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                obj.qnnTableInstance.setEditedRowData({
                                                    status:obj.newRowData.status
                                                })
                                            } else {
                                                Msg.error(message);
                                                obj.qnnTableInstance.setEditedRowData({
                                                    status:obj.oldRowData.status
                                                })
                                            }
                                            this.table.refresh();
                                        }
                                    );
                                },
                            },
                            form: {
                                field: 'status',
                                type: 'select',
                                addShow: true,
                                allowClear: false,
                                detailShow: false,
                                editShow: true,
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'qiYongZhuangTai'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '修改者',
                                dataIndex: 'modifyUserName',
                                key: 'modifyUserName'
                            },
                            form: {
                                type: 'string',
                                field: 'modifyUserName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '公积金抵扣上限金额',
                                dataIndex: 'upLimitAmt',
                                key: 'upLimitAmt'
                            },
                            form: {
                                type: 'number',
                                field: 'upLimitAmt'
                            }
                        },
                        {
                            table: {
                                title: '修改时间',
                                dataIndex: 'createTime',
                                key: 'createTime',
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                type: 'date',
                                field: 'createTime',
                                hide: true
                            }
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
                                        willExecute: 'bind:willExecuteFunEdit',
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
                                                    apiName: "updateZjXmSalaryProvidentFundScheme"
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }

                    ]}
                    method={{
                        willExecuteFunEdit: async (obj) => {
                            const { data, success, message } = await this.props.myFetch('checkZjXmSalaryProvidentFundSchemeDeleteUpdate', { zjXmSalaryProvidentFundSchemeId: obj.rowData.zjXmSalaryProvidentFundSchemeId });
                            console.log(success);
                            if (success) {
                                return true
                            } else {
                                Msg.warn(message);
                                return false
                            }
                        },
                    }}
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
                                        apiName: 'addZjXmSalaryProvidentFundScheme'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {
                                apiName: 'deleteZjXmSalaryProvidentFundScheme',
                            },
                            type: 'danger',
                            label: '删除',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                        },
                    ]}
                />
            </div>
        );
    }
}

export default index;