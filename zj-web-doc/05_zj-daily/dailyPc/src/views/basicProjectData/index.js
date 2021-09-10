import React,{ Component } from 'react';
import QnnTable from '../modules/qnn-table'; 
import { push } from "react-router-redux";
const config = {
    fetchConfig: {
        apiName: "getZjDailyProInfoList"
    },
    antd: {
        rowKey: "proInfoId",
        size: "small"
    },
    actionBtns: [
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
                        apiName: 'addZjDailyProInfo'
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
                apiName: 'batchDeleteUpdateZjDailyProInfo'
            }
        },
    ],
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
            table: {
                title: '项目名称',
                dataIndex: 'proName',
                key: 'proName',
                onClick: 'detail',
                tooltip:30
            },
            form: {
                type: 'string',
                placeholder: '请输入',
                required: true,
            }
        },
        {
            table: {
                title: '<center>通车日期</center>',
                dataIndex: 'openTime',
                key: 'openTime',
                noHaveSearchInput: true,
                format: "YYYY-MM-DD",
                // fieldsConfig: {
                //     field: "openTime",
                //     type: "datetime", 
                // }
            },
            form: {
                label:'通车日期',
                type: 'date',
                placeholder: '请选择',
                required: true,
            }
        },
        {
            table: {
                title: '<center>股权比例(%)</center>',
                dataIndex: 'equityRatio',
                key: 'equityRatio',
                noHaveSearchInput: true,
            },
            form: {
                label:'股权比例',
                type: 'number',
                min:1,
                max:100,
                placeholder: '请输入',
                required: true,
            }
        },
        {
            isInTable: false,
            form: {
                label:'填报人员',
                field: 'personList',
                type: 'treeSelect',
                // required: true,
                treeSelectOption: {
                    selectType: "2",
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getSysDepartmentUserAllTree'
                    }
                }
            }
        },
        {
            table: {
                title: '<center>备注</center>',
                dataIndex: 'remarks',
                key: 'remarks',
                noHaveSearchInput: true,
                tooltip:30
            },
            form: {
                label:'备注',
                type: 'textarea',
                placeholder: '请输入'
            }
        },
        {
            isInForm: false,
            table: {
                fixed: "right",
                width: 120,
                title: '<center>操作</center>',
                showType: "tile",
                dataIndex: 'action',
                noHaveSearchInput: true,
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
                                    apiName: 'updateZjDailyProInfo',
                                }
                            }
                        ],
                    },
                    {
                        name: 'diy',
                        render: function (rowData) {
                            return '<a>明细维护</a>';
                        },
                        onClick: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            const { proInfoId } = obj.rowData;
                            obj.props.dispatch(
                                push(`${mainModule}BasicProjectDataDetail/${proInfoId}`)
                            )
                        }
                    }
                ]
            }
        }
    ]
}
class Index extends Component {
    render() { 
        return (
            <div style={{ height: '100%',overflow: "hidden" }}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                />
            </div>)
    }
} 
export default Index