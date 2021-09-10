import React, { Component } from "react";
// import QnnTable from '../modules/qnn-table';
// import QnnForm from '../modules/qnn-table/qnn-form';
// import { message as Msg, Modal } from 'antd';
// import s from './style.less';
// import moment from 'moment';
// const { confirm } = Modal;
// const config = {
//     antd: {
//         rowKey: function (row) {
//             return row.id
//         },
//         size: 'small',
//     },
//     drawerConfig: {
//         width: '900px'
//     },
//     paginationConfig: {
//         position: 'bottom'
//     },
//     isShowRowSelect: false
// }
class Index extends Component {
    // constructor() {
    //     super();
    //     this.state = {
    //         visibleRW: false,
    //         visibleZB: false,
    //         visibleXM: false,
    //         visibleZH: false,
    //         rowData: null
    //     }
    // }
    render() {
        // const { visibleRW, visibleZB, visibleXM, visibleZH, rowData } = this.state;
        return (
            <div>
                {/* <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    data={[{ id: '1', month: 1592361456398, textarea: '哈哈哈', zt: "0", modifyUserName: 'admin', modifyTime: 1592361456398, number: 99, userName: 'admin', bumen: '厦门公司' }]}
                    tabs={visibleRW ? [
                        {
                            field: "rwkh",
                            name: "qnnTable",
                            title: "任务考核",
                            content: {
                                fetchConfig: {
                                    apiName: '',
                                },
                                data:[{ id: '1', month: 1592361456398, textarea: '哈哈哈', zt: "0", modifyUserName: 'admin', modifyTime: 1592361456398, number: 99, userName: 'admin', bumen: '厦门公司' }],
                                formConfig: [
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'id',
                                            type: 'string',
                                            hide: true,
                                        },
                                    },
                                    {
                                        table: {
                                            title: '年月',
                                            dataIndex: 'month',
                                            key: 'month',
                                            width: 100,
                                            fixed: 'left',
                                            onClick: 'edit',
                                            btns: [
                                                {
                                                    name: 'cancel',
                                                    type: 'dashed',
                                                    label: '取消',
                                                },
                                                {
                                                    name: 'appeal',
                                                    type: 'primary',
                                                    label: '申诉',
                                                },
                                                {
                                                    name: 'affirm',
                                                    type: 'primary',
                                                    label: '确认',
                                                }
                                            ],
                                            render: (data) => {
                                                return moment(data).format('YYYY-MM');
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '考核标题',
                                            dataIndex: 'textarea',
                                            key: 'textarea',
                                            width: 200
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled:true,
                                            placeholder: "请选择",
                                            formItemLayout:{
                                                labelCol: {
                                                    xs: { span: 2 },
                                                    sm: { span: 2 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 22 },
                                                    sm: { span: 22 }
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '姓名',
                                            dataIndex: 'userName',
                                            key: 'userName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled:true,
                                            placeholder: "请选择",
                                            spanForm:12
                                        }
                                    },
                                    {
                                        table: {
                                            title: '部门',
                                            dataIndex: 'bumen',
                                            key: 'bumen',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled:true,
                                            placeholder: "请选择",
                                            spanForm:12
                                        }
                                    },
                                    {
                                        table: {
                                            title: '得分',
                                            dataIndex: 'number',
                                            key: 'number',
                                            width: 100
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '状态',
                                            dataIndex: 'zt',
                                            key: 'zt',
                                            width: 100,
                                            render: (data) => {
                                                if (data === '0') {
                                                    return '未评分';
                                                } else {
                                                    return '已评分';
                                                }
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        isInTable:false,
                                        form: {
                                            type: "qnnTable",
                                            field: "qnnTable",
                                            qnnTableConfig: {
                                                isShowRowSelect: false,
                                                formConfig: [
                                                    {
                                                        table: {
                                                            title: '成本责任指标',
                                                            dataIndex: '1',
                                                            key: '1',
                                                            width: 200,
                                                            fixed: 'left'
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '目标值',
                                                            dataIndex: '2',
                                                            key: '2',
                                                            width: 100
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '评价计分标准',
                                                            dataIndex: '3',
                                                            key: '3',
                                                            width: 200
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '权重',
                                                            dataIndex: '4',
                                                            key: '4',
                                                            width: 100
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '完成情况填写',
                                                            dataIndex: '5',
                                                            key: '5',
                                                            width: 200
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '分数',
                                                            dataIndex: '6',
                                                            key: '6',
                                                            width: 100
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: '意见',
                                                            dataIndex: '7',
                                                            key: '7',
                                                            width: 200
                                                        },
                                                        isInForm: false
                                                    }
                                                ]
                                            }
                                        }
                                    },
                                ]
                            }
                        }
                    ] : visibleZB ? [
                        {
                            field: "zbkh",
                            name: "qnnForm",
                            title: "周边考核",
                            content: {
                                fetchConfig: {
                                    apiName: '',
                                },
                                formConfig: [
                                    {
                                        field: 'id',
                                        label: '主键',
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        field: 'month',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'textarea',
                                        label: '通知内容',
                                        type: 'textarea',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'deptList',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            }
                                        },
                                        required: true
                                    },
                                    {
                                        field: 'textarea1',
                                        label: '备注',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    }
                                ]
                            }
                        }
                    ] : visibleXM ? [
                        {
                            field: "xmkh",
                            name: "qnnForm",
                            title: "项目正职",
                            content: {
                                fetchConfig: {
                                    apiName: '',
                                },
                                formConfig: [
                                    {
                                        field: 'id',
                                        label: '主键',
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        field: 'month',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'textarea',
                                        label: '通知内容',
                                        type: 'textarea',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        label: '被考核人',
                                        field: 'deptList',
                                        type: 'treeSelect',
                                        treeSelectOption: {
                                            selectType: "2",
                                            fetchConfig: {//配置后将会去请求下拉选项数据
                                                apiName: 'getSysDepartmentUserAllTree'
                                            }
                                        },
                                        required: true
                                    },
                                    {
                                        field: 'textarea1',
                                        label: '备注',
                                        type: 'textarea',
                                        placeholder: '请输入'
                                    }
                                ]
                            }
                        }
                    ] : visibleZH ? [
                        {
                            field: "zhpj",
                            name: "qnnForm",
                            title: "综合评价",
                            content: {
                                fetchConfig: {
                                    apiName: '',
                                },
                                formConfig: [
                                    {
                                        field: 'id',
                                        label: '主键',
                                        type: 'string',
                                        hide: true
                                    },
                                    {
                                        field: 'month',
                                        label: '年月',
                                        type: 'month',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        field: 'textarea',
                                        label: '考核标题',
                                        type: 'string',
                                        disabled: true,
                                        placeholder: '请输入'
                                    },
                                    {
                                        type: "qnnTable",
                                        field: "qnnTable",
                                        incToForm: true,
                                        qnnTableConfig: {
                                            antd: {
                                                size: "small",
                                                rowKey: "id",
                                            },
                                            paginationConfig: false,
                                            actionBtnsPosition: "top",
                                            actionBtns: [
                                                {
                                                    name: 'addRow',
                                                    icon: 'plus',
                                                    type: 'primary',
                                                    label: '新增',
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'del',
                                                    type: 'danger',
                                                    label: '删除',
                                                }
                                            ],
                                            formConfig: [
                                                {
                                                    table: {
                                                        title: '姓名',
                                                        dataIndex: '1',
                                                        key: '1',
                                                        tdEdit: true
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '部门',
                                                        dataIndex: '2',
                                                        key: '2',
                                                        tdEdit: true
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: '扣分',
                                                        dataIndex: '3',
                                                        key: '3',
                                                        tdEdit: true
                                                    },
                                                    isInForm: false
                                                }
                                            ]
                                        }
                                    }
                                ]
                            }
                        }
                    ] : []}
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
                                title: '年月',
                                dataIndex: 'month',
                                key: 'month',
                                filter: true,
                                width: 100,
                                render: (data) => {
                                    return moment(data).format('YYYY-MM');
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '考核标题',
                                dataIndex: 'textarea',
                                key: 'textarea',
                                filter: true,
                                width: 200
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '修改时间',
                                dataIndex: 'modifyTime',
                                key: 'modifyTime',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '任务考核',
                                dataIndex: 'rwkh',
                                key: 'rwkh',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleZB: false,
                                        visibleZH: false,
                                        visibleXM: false,
                                        visibleRW: true
                                    }, () => {
                                        obj.btnCallbackFn.setDrawerBtns([
                                            {
                                                field: 'rwkhCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                field: 'rwkhSubmit',
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '提交',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: ''
                                                }
                                            }
                                        ])
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: (data) => {
                                    if (data === '0') {
                                        return '未评分';
                                    } else {
                                        return '已评分';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '周边考核',
                                dataIndex: 'zbkh',
                                key: 'zbkh',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleRW: false,
                                        visibleZH: false,
                                        visibleXM: false,
                                        visibleZB: true
                                    }, () => {
                                        obj.btnCallbackFn.setDrawerBtns([
                                            {
                                                field: 'zbkhCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                field: 'zbkhSubmit',
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '提交',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: ''
                                                }
                                            }
                                        ])
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: (data) => {
                                    if (data === '0') {
                                        return '未评分';
                                    } else {
                                        return '已评分';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '项目正职',
                                dataIndex: 'xmzz',
                                key: 'xmzz',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleRW: false,
                                        visibleZH: false,
                                        visibleZB: false,
                                        visibleXM: true
                                    }, () => {
                                        obj.btnCallbackFn.setDrawerBtns([
                                            {
                                                field: 'xmzzCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                field: 'xmzzSubmit',
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '提交',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: ''
                                                }
                                            }
                                        ])
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: (data) => {
                                    if (data === '0') {
                                        return '未评分';
                                    } else {
                                        return '已评分';
                                    }
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '综合评价',
                                dataIndex: 'zhpj',
                                key: 'zhpj',
                                width: 100,
                                onClick: (obj) => {
                                    this.setState({
                                        visibleRW: false,
                                        visibleZB: false,
                                        visibleXM: false,
                                        visibleZH: true
                                    }, () => {
                                        obj.btnCallbackFn.setDrawerBtns([
                                            {
                                                field: 'zhpjCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                field: 'zhpjSubmit',
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '提交',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: ''
                                                }
                                            }
                                        ])
                                        obj.btnCallbackFn.closeDrawer(true);
                                    })
                                },
                                render: (data) => {
                                    if (data === '0') {
                                        return '未评分';
                                    } else {
                                        return '已评分';
                                    }
                                }
                            },
                            isInForm: false
                        }
                    ]}
                /> */}
            </div>
        );
    }
}
export default Index;