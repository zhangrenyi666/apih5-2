import React, { Component } from 'react';
import QnnTable from '../modules/qnn-table';
import { push } from "react-router-redux";
const config = {
    antd: {
        rowKey: "proDetailId",
        size: "small"
    },
    // drawerConfig: {
    //     width:1000
    // },
    firstRowIsSearch: false,
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'proInfoId',
                type: 'string',
                placeholder: '请输入',
                hide: true
            }
        },
        {
            isInTable: false,
            form: {
                field: 'proDetailId',
                type: 'string',
                placeholder: '请输入',
                hide: true
            }
        },
        {
            table: {
                title: '收费站',
                dataIndex: 'tollStation',
                key: 'tollStation',
                onClick: 'detail',
                tooltip: 30
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
            }
        },
        {
            table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                tooltip: 30
            },
            form: {
                type: 'textarea',
                placeholder: '请输入'
            }
        },
        {
            isInForm:false,
            table: {
                title: '修改者',
                dataIndex: 'modifyUserName',
                key: 'modifyUserName'
            }
        },
        {
            isInForm:false,
            table: {
                title: '修改时间',
                dataIndex: 'modifyTime',
                key: 'modifyTime',
                format: "YYYY-MM-DD HH:mm:ss",
                // fieldsConfig: {
                //     field: "modifyTime",
                //     type: "datetime",
                // }
            }
        },
        {
            isInForm: false,
            table: {
                fixed: "right",
                width: 80,
                title: '操作',
                showType: "tile",
                dataIndex: 'action',
                btns: [
                    {
                        label: "修改",
                        name: "edit",
                        formBtns: [
                            {
                                name: 'cancel', //关闭右边抽屉
                                type: 'dashed',//类型  默认 primary
                                label: '取消',
                            },
                            {
                                name: 'submit',//内置add del
                                type: 'primary',//类型  默认 primary
                                label: '提交',//提交数据并且关闭右边抽屉
                                fetchConfig: {//ajax配置  ---可为func
                                    apiName: 'updateZjDailyProDetail',
                                }
                            }
                        ],
                    }
                ]
            }
        }
    ]
}
class Index extends Component {
    constructor(props){
        super(props);
        this.state = {
            proInfoId:props.match.params.proInfoId
        }
    }
    render() {
        const { proInfoId } = this.state;
        return (
            <div style={{ height: '100%', overflow: "hidden" }}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig= {{
                        apiName: "getZjDailyProDetailList",
                        otherParams:{
                            proInfoId:proInfoId
                        }
                    }}
                    actionBtns = {[
                        {
                            name: 'goBack',
                            type: 'default',
                            label: '返回',
                            onClick: (obj) => {
                                const { mainModule } = obj.props.myPublic.appInfo;
                                obj.props.dispatch(
                                    push(`${mainModule}BasicProjectData`)
                                )
                            }
                        },
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            drawerTitle: '新增',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '提交',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZjDailyProDetail',
                                        otherParams:{
                                            proInfoId:proInfoId
                                        }
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjDailyProDetail'
                            }
                        },
                    ]}
                    {...config}
                />
            </div>)
    }
}
export default Index