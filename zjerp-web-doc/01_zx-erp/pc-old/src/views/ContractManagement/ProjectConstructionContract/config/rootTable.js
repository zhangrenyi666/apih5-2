import React from "react"
import tabOne from "./tabOne"
import tabThree from "./tabThree"
import tabFour from "./tabFour"
import ListSubcontracts from "./ListSubcontracts"
import { HistoryOutlined, ImportOutlined, ExportOutlined } from '@ant-design/icons';
export default {
    antd: {
        rowKey: "ctContrApplyId",
        size: 'small'
    },
    fetchConfig: {
        apiName: "psGetZxGcsgCtContrApplyList",
        otherParams:"bind:getUserInfo"
    },
    drawerConfig: {
        width: '100%'
    },
    formItemLayout: {
        labelCol: {
            sm: { span: 9 }
        },
        wrapperCol: {
            sm: { span: 15 }
        }
    },
    actionBtns: [
        {
            name: 'add',//内置add del
            icon: 'plus',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '新增',
            formBtns: [
                {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                },
                {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '保存',//提交数据并且关闭右边抽屉 
                    fetchConfig: {//ajax配置
                        apiName: 'psAddZxGcsgCtContrApply',
                        otherParams:"bind:getUserInfo"
                    },
                    hide: (props) => {
                        return props.btnCallbackFn.getTabsIndex() !== "0"
                    },
                    // onClick: "bind:addCb"
                }
            ]
        },
        {
            name: 'exportData',//内置add del
            icon: <ExportOutlined />,//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '导出',
            onClick: "bind:exportData::psExportZxGcsgCtContrApplyExcel", 
        },
        {
            name: 'edit',//内置add del
            icon: 'edit',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '修改',
            willExecute: "bind:willOpenFlowFormCom",
            formBtns: [
                {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                },
                {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '保存',//提交数据并且关闭右边抽屉 
                    fetchConfig: {//ajax配置
                        apiName: 'psUpdateZxGcsgCtContrApply',
                    },
                    onClick: (obj) => {
                        obj.btnCallbackFn.clearSelectedRows();
                    }
                }
            ]
        },
        {
            name: 'Component',
            icon: "plus",
            type: 'primary',
            label: '评审申请',
            disabled: "bind:_actionBtnNoSelected",
            Component: "FlowFormCom",
            willExecute: "bind:willOpenFlowFormCom"
        },
        {
            name: 'qsjdcx',//内置add del
            icon: <HistoryOutlined />,//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '进度查询',
            onClick: "bind:qsjdcx",
            disabled: "bind:_actionBtnNoSelected"
        },
       
        {
            name: 'del',//内置add del
            icon: 'delete',//icon
            type: 'danger',//类型  默认 primary  [primary dashed danger]
            label: '删除',
            willExecute: "bind:willOpenFlowFormCom",
            fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZxGcsgCtContrApply',
            },
        }
    ],
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'ctContrApplyId',
                type: 'string',

            }
        },
        {
            table: {
                title: '合同编号',
                dataIndex: 'contractNo',
                filter: true,
                fixed: 'left',
                width: 260,
                onClick: 'detail'
            },
            form: {
                type: 'string',
                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '合同名称',
                dataIndex: 'contractName',
                filter: true,
                width: 200
            },
            form: {
                type: 'string',
                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '甲方单位',
                dataIndex: 'firstName',
                filter: true,
                width: 260
            },
            form: {
                type: 'string',
                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '乙方单位',
                dataIndex: 'secondName',
                filter: true,
                width: 260
            },
            form: {
                type: 'string',
                placeholder: '请输入'
            }
        },
        // {
        //     table: {
        //         title: '协作单位类型',
        //         dataIndex: 'secondOrgType',
        //         width: 150,
        //         render: (data) => ([
        //             { label: "公路市政事业部", value: "0" },
        //             { label: "铁路轨道事业部", value: "1" },
        //             { label: "城市房建事业部", value: "2" }
        //         ].filter(item => item.value === data)[0]?.label)
        //     },
        //     isInForm: false
        // },
        {
            table: {
                title: '合同签订人',
                dataIndex: 'agent',
                width: 150
            },
            isInForm: false
        },

        {
            table: {
                title: '含税合同额(含变更)',
                dataIndex: 'contractCost',
                width: 180
            },
            isInForm: false
        },
        {
            table: {
                title: '是否抵扣',
                dataIndex: 'isDeduct',
                width: 90,
                render: (data) => data === "1" ? "是" : "否"
            },
            isInForm: false
        },
        {
            table: {
                title: '开工日期',
                dataIndex: 'startDate',
                format: "YYYY/MM/DD",
                width: 150
            },
            isInForm: false
        },
        {
            table: {
                title: '竣工日期',
                dataIndex: 'endDate',
                format: "YYYY/MM/DD",
                width: 150
            },
            isInForm: false
        },
        {
            table: {
                title: '现场负责人',
                dataIndex: 'legalPerson',
                filter: true,
                width: 120
            },
            form: {
                type: 'string',

                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '委托代理人',
                dataIndex: 'agentPerson',

                filter: true,
                width: 120
            },
            form: {
                type: 'string',

                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '法定代表人',
                dataIndex: 'chargePerson',

                filter: true,
                width: 120
            },
            form: {
                type: 'string',

                placeholder: '请输入'
            }
        },
        {
            table: {
                title: '合同所属事业部',
                dataIndex: 'contractDepart',
                width: 120,
                render: (data) => ([
                    { label: "公路市政事业部", value: "glsz" },
                    { label: "铁路轨道事业部", value: "tlgd" },
                    { label: "城市房建事业部", value: "csfj" }
                ].filter(item => item.value === data)[0]?.label)
            },
            isInForm: false
        },
        {
            table: {
                title: '发起人',
                dataIndex: 'createUserName',
                width: 120
            },
            isInForm: false
        },

        {
            table: {
                title: '评审状态',
                dataIndex: 'apih5FlowStatus',
                width: 80,
                fixed: "right",
                align: "center",
                render: (data) => {
                    if (data === '0') {
                        return '待提交';
                    } else if (data === '1') {
                        return '审核中';
                    } else if (data === '2') {
                        return '审核完成';
                    } else if (data === '3') {
                        return '退回';
                    } else if (data === '-1') {
                        return '未审核';
                    } else {
                        return '待提交';
                    }
                }
            },
            isInForm: false
        },

        {
            isInForm: false,
            table: {
                title: '导入',
                dataIndex: 'action',
                key: "action",//操作列名称
                align: 'center',
                fixed: 'right',
                showType: "tile",
                width: 60,
                // isLeaf  
                //叶子节点才渲染挂接按钮
                btns: [
                    {
                        label: <span style={{ fontWeight: "800", fontSize: 18 }} title="导入"> <ImportOutlined /></span>,
                        icon: 'save',//icon
                        type: 'primary', //primary dashed danger 
                        onClick: "bind:rowImportClick"
                    },
                ]
            }
        }

    ],
    tabs: [
        {
            field: "baiseInfo",
            title: "基础信息",
            name: "qnnForm",
            content: {
                ...tabOne
            }
        },
        {
            field: "listSubcontracts",
            title: "分包合同清单",
            name: "listSubcontracts",
            content: ListSubcontracts,
            disabled: (args) => { 
                return args.clickCb.rowInfo.name === "add"; //新增直接不让编辑就行
                // return args.form.getFieldValue("ctContrApplyId") || args.clickCb.selectedRows[0]?.["ctContrApplyId"] || args.clickCb.rowData?.["ctContrApplyId"] ? false : true
            }
        },
        {
            field: "inventoryProcessLinkedLedger",
            title: "清单工序挂接台账",
            name: "qnnTable",
            disabled: (args) => {
                return args.clickCb.rowInfo.name === "add"; //新增直接不让编辑就行
                // return args.form.getFieldValue("ctContrApplyId") || args.clickCb.selectedRows[0]?.["ctContrApplyId"] || args.clickCb.rowData?.["ctContrApplyId"]  ? false : true
            },
            content: {
                // 表格
                ...tabThree
            }
        },
        {
            field: "contractAnalysis",
            title: "合同单价分析",
            name: "qnnTable",
            disabled: (args) => {
                return args.clickCb.rowInfo.name === "add"; //新增直接不让编辑就行
                // return args.form.getFieldValue("ctContrApplyId") || args.clickCb.selectedRows[0]?.["ctContrApplyId"] || args.clickCb.rowData?.["ctContrApplyId"]  ? false : true
            },
            content: {
                // 表格
                ...tabFour
            }
        }
    ]
}