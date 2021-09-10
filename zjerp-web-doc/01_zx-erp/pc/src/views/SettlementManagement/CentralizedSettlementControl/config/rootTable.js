import tabOne from "./tabOne"
import TabTwo from "./ListSubcontracts"
import tabThree from "./tabThree"
import tabFour from "./tabFour"
import tabFive from "./tabFive"
import TabSix from "./tabSix"
import FenBaoYuYeZhu from "./FenBaoYuYeZhu"
import finalStatement from "./finalStatement"

export default {
    // DrawerShow: true,
    antd: {
        rowKey: "ctContrApplyId",
        size: 'small'
    },
    fetchConfig: {
        apiName: "getZxSaSettleControlLedgerGcsgYzxList",
        otherParams: "bind:getUserInfo"
    },
    drawerConfig: {
        width: '75%'
    },
    formItemLayout: {
        labelCol: {
            sm: { span: 9 }
        },
        wrapperCol: {
            sm: { span: 15 }
        }
    },
    isShowRowSelect:false,
    formConfig: [
        {
            isInTable: false,
            form: {
                field: 'ctContrApplyId',
                type: 'string',
                hide: true,
            }
        },

        //仅仅用于搜索的两个字段  ----暂时不做
        // {
        //     isInTable: false,
        //     isInTable: false,
        //     isInSearch: true,
        //     table: {
        //         title: '单位名称',
        //         dataIndex: '单位名称', 
        //     }
        //     , form: {
        //         type: "selectByQnnTable",
        //         formItemLayout: {
        //             labelCol: {
        //                 sm: { span: 6 }
        //             },
        //             wrapperCol: {
        //                 sm: { span: 18 }
        //             }
        //         },
        //         optionConfig: {
        //             value: 'itemId',
        //             label: "itemName"
        //         },
        //         dropdownMatchSelectWidth: 800,
        //         qnnTableConfig: {
        //             antd: {
        //                 rowKey: "itemId",
        //                 scroll: {
        //                     x: "200px"
        //                 }
        //             },

        //             selectType: "radio", //默认radio单选  值为 string 
        //             searchBtnsStyle: "inline",
        //             fetchConfig: {
        //                 apiName: "getBaseCodeSelect",
        //                 otherParams: {
        //                     itemId: "chengJianDanWeiJianCheng"
        //                 }
        //             },
        //             formConfig: [
        //                 {
        //                     isInSearch: true,
        //                     table: {
        //                         dataIndex: "itemId",
        //                         title: "名称",
        //                     },
        //                     form: {
        //                         type: "string"
        //                     }
        //                 },
        //                 {
        //                     isInSearch: true,
        //                     table: {
        //                         dataIndex: "itemName",
        //                         title: "上级机构",
        //                     },
        //                     form: {
        //                         type: "string"
        //                     }
        //                 }
        //             ]
        //         }
        //     }
        // },


        {
            table: {
                title: '合同编号',
                dataIndex: 'contractNo',
                filter: true,
                fixed: 'left',
                width: 240,
                onClick: 'detail',
                // onClick: 'edit',
                // btns: [
                //     {
                //         name: 'cancel', //关闭右边抽屉
                //         type: 'dashed',//类型  默认 primary
                //         label: '取消',
                //         hide: (args) => args.btnCallbackFn.getTabsIndex() !== "0"
                //     },
                //     {
                //         name: 'submit',//内置add del
                //         type: 'primary',//类型  默认 primary
                //         label: '保存',//提交数据并且关闭右边抽屉 
                //         fetchConfig: {//ajax配置
                //             apiName: 'saveZxGcsgCtContractInfo',
                //         },
                //         hide: (args) => args.btnCallbackFn.getTabsIndex() !== "0"
                //     }
                // ]
            },
            form: {
                // type: 'string',
                type: 'string',
                addDisabled: true,
                editDisabled: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            table: {
                title: '合同名称',
                dataIndex: 'contractName',
                filter: true,
                fixed: 'left',
                width: 150,
            },
            form: {
                type: 'string',
                required: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            table: {
                title: '合同类型',
                dataIndex: 'contractManageType',
                width: 100,
                type: "select",
                optionData: [
                    { label: "内部分包", value: "0" },
                    { label: "专业分包", value: "1" },
                    { label: "劳务分包", value: "2" },
                    { label: "总体分包", value: "3" },
                ],
            },
            form: {
                type: 'string',
                addDisabled: true,
                editDisabled: true,
                initialValue: '工程施工类',
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            table: {
                title: '合同甲方',
                dataIndex: 'firstName',
                width: 230,
            },
            form: {
                type: 'string', 
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            table: {
                title: '合同乙方',
                dataIndex: 'secondIDFormula',
                width: 230,
            },
            form: {
                type: 'string',
                required: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            table: {
                title: '含税合同总额(万元)',
                dataIndex: 'contractCost',
                width: 150,
            },
            form: {
                type: 'number',
                addDisabled: true,
                editDisabled: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            isInTable: false,
            form: {
                label: '变更后含税合同总额(万元)',
                field: 'alterContractSum',
                type: 'number',
                addDisabled: true,
                editDisabled: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },

        {
            table: {
                title: '是否抵扣',
                dataIndex: 'isDeduct',
                width: 100,
                type: 'select'
            },
            form: {
                type: 'radio',
                optionData: [
                    {
                        label: '否',
                        value: '0'
                    },
                    {
                        label: '是',
                        value: '1'
                    }
                ],
                spanForm: 12
            }
        },
        {
            table: {
                title: '签订日期',
                dataIndex: 'signatureDate',
                width: 100,
                format: 'YYYY-MM-DD'
            },
            form: {
                type: 'date',
                placeholder: '请选择',
                spanForm: 12
            }
        },

        {
            table: {
                title: '乙方代表',
                dataIndex: 'secondPrincipal',
                filter: true,
                width: 100
            },
            form: {
                type: 'string',
                required: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            table: {
                title: '结算情况',
                dataIndex: 'settleType',
                filter: true,
                width: 100
            },
            form: {
                type: 'string',
                required: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },
        {
            table: {
                title: '乙方名称',
                dataIndex: 'secondName',
                filter: true,
                width: 230,
            },
            form: {
                type: 'string',
                required: true,
                placeholder: '请输入',
                spanForm: 12
            }
        },
        //暂时屏蔽 000004472
        // {
        //     isInForm: false,
        //     table: {
        //         title: '清单',
        //         dataIndex: 'action',
        //         key: "action",//操作列名称
        //         align: 'center',
        //         fixed: 'right',
        //         showType: "tile",
        //         width: 100,
        //         btns: [
        //             {
        //                 label: <span style={{ fontWeight: "800", fontSize: 18 }} title="导入"> <ImportOutlined /></span>,
        //                 icon: 'save',//icon
        //                 type: 'primary', //primary dashed danger 
        //                 onClick: "bind:rowImportClick"
        //             },
        //             {
        //                 label: <span style={{ fontWeight: "800", fontSize: 18 }} title="查看"> <SearchOutlined /></span>,
        //                 icon: 'save',//icon
        //                 type: 'primary',
        //                 name: "detail",
        //                 onClick: "bind:rowLookClick"
        //             },
        //         ]
        //     }
        // }
    ],
    tabs: [
        {
            field: "baiseInfo",
            title: "合同信息",
            name: "qnnForm",
            //这里使用禁用是因为主表单必须存在 否则组件内部会报错
            // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
            content: {
                ...tabOne,
            }
        },

        //以下两个清单只是名字不同
        {
            field: "listSubcontracts",
            title: "清单",
            name: "listSubcontracts",
            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
            content: TabTwo,
        },
        {
            field: "listSubcontractsByDetail",
            title: "工程分包合同管理清单",
            name: "listSubcontractsByDetail",
            content: TabTwo,
            hide: (args) => args.clickCb?.rowInfo?.name !== "detail",
        },

        {
            field: "inventoryProcessLinkedLedger",
            title: "清单工序挂接台账",
            name: "qnnTable",
            content: {
                // 表格
                ...tabThree
            }
        },
        {
            field: "contractAnalysis",
            title: "合同单价分析",
            name: "qnnTable",
            content: {
                // 表格
                ...tabFour
            }
        },

        {
            field: "buchognxieyi",
            title: "补充协议",
            name: "qnnTable",
            // hide: (args) => args.clickCb?.rowInfo?.name === "detail",
            content: {
                // 表格
                ...tabFive
            }
        },

        {
            field: "FenBaoYuYeZhu",
            title: "分包清单与业主合同清单的关联",
            name: "FenBaoYuYeZhu",
            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
            content: FenBaoYuYeZhu,
        },
        {
            field: "TabSix",
            title: "分包合同清单",
            name: "TabSix",
            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
            content: TabSix,
        },
        {
            field: "finalStatement",
            title: "结算单",
            name: "qnnTable", 
            content: {
                // 表格
                ...finalStatement
            }
        },
    ]
}