import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import moment from 'moment';
import s from "./style.less";
const config = {
    antd: {
        rowKey: 'yearId',
        size: "small"
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};

class index extends Component {
    constructor() {
        super();
        this.state = {
        }
    }
    componentDidMount() {


    }
    render() {
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch} 
		            upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjGoodsYearList'
                    }}
                    {...config}
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
                                    fetchConfig: {
                                        apiName: 'addZjGoodsYear'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjGoodsYear'
                            },
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'yearId',
                                hide: true,
                                type: 'string'
                            }
                        },
                        {
                            table: {
                                title: '年份',
                                dataIndex: 'year',
                                key: 'year',
                                onClick: "detail"
                            },
                            form:{
                                type: 'year',
                                field: 'yearDate',
                                required: true,
                                editDisabled:true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                tooltip:50,
                                key: 'remarks'
                            },
                            form:{
                                type: 'textarea',
                                field: 'remarks'
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 80,
                                btns: [
                                    {
                                        name: 'edit',
                                        render: function (rowData) {
                                            return '<a>修改</a>';
                                        },
                                        formBtns: [
                                            {
                                                name: 'cancel',
                                                type: 'dashed',
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',
                                                type: 'primary',
                                                label: '提交',
                                                fetchConfig: {
                                                    apiName: 'updateZjGoodsYear'
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        },
                    ]}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 2 },
                            sm: { span: 2 }
                        },
                        wrapperCol: {
                            xs: { span: 22 },
                            sm: { span: 22 }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;