//细明表格的配置
import React from "react"


/* 
    
    前端 禁用状态、下拉字段切换赋值、细明计算 规则

        alterType === "1" (变更类型为新增时)    

            非禁用字段有：
                归属主合同清单编号( ccWorksParentNo )
                清单编号( ccWorksNo )
                清单名称( ccWorksName )[类型为string]
                是否叶子节点( isLeaf )
                单位( ccWorksUnit )
                变更增减数量( changeQty ) 【isLeaf存在即解除禁用】
                增减单价( changePrice )  【isLeaf存在即解除禁用】
                税率( taxRate )
                备注( remarks ) 
            
            变更增减数量、增减单价、税率（切换时） 失去焦点时：(细明计算)
            
                变更后数量[afterChangeQty] = 变更增减数量[changeQty]
                变更后单价[afterChangePrice] = 增减单价[changePrice]
                变更后含税金额[afterAmt] = 变更增减数量[changeQty] * 增减单价[changePrice]
                变更后不含税金额[afterAmtNoTax] = getBHS(税率[taxRate], 变更增减数量[changeQty] * 增减单价[changePrice])
                变更后税额[afterAmtTax] = 变更后含税金额[afterAmt] - 变更后不含税金额[afterAmtNoTax] 

                *下面[n]单元格数 指代所有行数据中的单元格相加的数

                本期含税变更增减金额（万元）[applyAmount] = 变更后含税金额[afterAmt] / 10000
                变更后含税合同金额（万元）[replyAmount]  = 原含税合同金额（万元）[originalTaxAmount] + 本期含税变更增减金额（万元）[applyAmount]
                 
                本期不含税变更增减金额（万元）[applyAmountNoTax] = 变更后不含税金额[afterAmtNoTax] / 10000
                变更后不含税合同金额（万元）[replyAmountNoTax] = 原不含税合同金额（万元）[pp2NoTax] + 本期不含税变更增减金额（万元）[applyAmountNoTax]
                
                本期变更增减税额（万元）[applyAmountTax] = 变更后税额[afterAmtTax] / 10000
                变更后合同税额（万元）[replyAmountTax] = 原合同税额（万元）[pp2Tax] + 本期变更增减税额（万元）[applyAmountTax]
 
                    
        alterType === "2" (变更类型为编辑时)    
 
             非禁用字段有： 
                清单编号( ccWorksNo )
                清单名称( ccWorksName )[类型为 selectByQnnTable] 
                单位( ccWorksUnit )
                变更增减数量( changeQty ) 【isLeaf存在即解除禁用】  
                备注( remarks )

            清单名称 字段切换时赋值：
                除了 归属主合同清单编号外，整行数据赋值

            变更增减数量  失去焦点时：(细明计算) [单元格全部使用 变更后数量 计算， 表单中字段全部使用 变更增减数量 字段计算]
 
                变更后数量[afterChangeQty] =  [用户没选清单时候就用0+增减数量]
                                            [选了清单时候就用quantity+增减数量]
                                            [回显时候，未切换清单之前用quantity（后台返回的）+增减数量]
                                            [回显时候，切换清单之后用quantity（下拉数据中的）+增减数量]

                变更后含税金额[afterAmt] = 变更后数量[afterChangeQty] * 变更后单价[afterChangePrice]
                变更后不含税金额[afterAmtNoTax] = getBHS(税率[taxRate], 变更后数量[afterChangeQty] * 变更后单价[afterChangePrice])
                变更后税额[afterAmtTax] = 变更后含税金额[afterAmt] - 变更后不含税金额[afterAmtNoTax] 

                *下面[n]单元格数 指代所有行数据中的单元格相加的数 

                本期含税变更增减金额（万元）[applyAmount] = 变更增减数量[changeQty] * 变更后单价[afterChangePrice] / 10000
                变更后含税合同金额（万元）[replyAmount]  = 原含税合同金额（万元）[originalTaxAmount] + 本期含税变更增减金额（万元）[applyAmount]
                
                本期不含税变更增减金额（万元）[applyAmountNoTax] = getBHS(税率[taxRate], 变更增减数量[changeQty] * 变更后单价[afterChangePrice] / 10000)
                变更后不含税合同金额（万元）[replyAmountNoTax] = 原不含税合同金额（万元）[pp2NoTax] + 本期不含税变更增减金额（万元）[applyAmountNoTax]
                
                本期变更增减税额（万元）[applyAmountTax] = (变更增减数量[changeQty] * 变更后单价[afterChangePrice] - getBHS(税率[taxRate], 变更增减数量[changeQty] * 变更后单价[afterChangePrice])) / 10000
                变更后合同税额（万元）[replyAmountTax] = 原合同税额（万元）[pp2Tax] + 本期变更增减税额（万元）[applyAmountTax]
 
*/

export default {
    // tableTdEdit: true,
    antd: {
        rowKey: "ccCoAlterWorkId",
        size: 'small',
    },
    actionBtns: [
        {
            field: 'addRow',
            name: 'addRow',//【add, addRow,  del, edit, detail, Component, form】
            icon: 'plus',
            type: 'primary',
            label: '新增',
            addRowDefaultData: {
                alterType: "1",

                afterChangeQty: 0,
                afterChangePrice: 0,
                afterAmt: 0,
                afterAmtNoTax: 0,
                afterAmtTax: 0,
                isLeaf: 0

            }
        },
        {
            field: 'del',
            name: 'del',//【add, addRow,  del, edit, detail, Component, form】
            icon: 'del',
            type: 'danger',
            label: '删除',
            //删除后也需要计算一遍表单上面的数据
            onClick: "bind:tableDatachangeJS"
        },
    ],
    desc: <div style={{ color: "red" }} key="desc">
        注意：没有勾选“是否叶子节点”则可以在该节点下继续新增子节点，反之则不能！
    </div>,
    formConfig: [
        {
            //修改时：
            //  禁用：归属主合同清单编号、增减单价、是否为叶子节点、税率 
            //  类型：清单名称 类型为下拉表格, 反之为输入框 
            table: {
                title: '变更类型',
                dataIndex: 'alterType',
                width: 100,
                tdEdit: true,
                type: "select",
                fixed: "left",
                fieldConfig: {
                    onChange: async (colVal, formBtnCallbackFn, tableBtnCallbackFn) => {
                        //切换时： 设置 原合同数量、原含税合同单价、原不含税合同单价、原含税合同金额、原不含税合同金额 赋值 

                        const restData = {
                            //数据清除 全部清除
                            ccWorksParentNo: undefined,
                            ccWorksParentId: undefined,
                            ccWorksId: undefined,
                            ccWorksName: undefined,
                            ccWorksNo: undefined,

                            "isLeaf": undefined,
                            "ccWorksUnit": undefined,
                            "originQty": 0,
                            "originPrice": 0,
                            "originPriceNoTax": 0,
                            "contractPrice": 0,
                            "contractCostNoTax": 0,

                            "afterChangeQty": 0,
                            "afterChangePrice": 0,
                            "afterAmt": 0,
                            "afterAmtNoTax": 0,
                            "afterAmtTax": 0,
                            "taxRate": undefined,

                            "changeQty": 0,


                        }

                        if (colVal === "1") {
                            restData["isLeaf"] = 0;
                        }

                        if (colVal === "2") {
                            restData["changePrice"] = 0;
                        }

                        tableBtnCallbackFn.qnnTableInstance.setEditedRowData(restData)
                    }
                }
            }
            , form: {
                type: 'select',
                optionData: [
                    { label: "新增", value: "1" },
                    { label: "修改", value: "2" },
                ]
            }
        },
        {
            table: {
                title: '归属主合同清单编号',
                dataIndex: 'ccWorksParentNo',
                width: 150,
                tdEdit: true,
                type: "selectByQnnTable",
                fieldConfig: {
                    dependencies: ['alterType'],
                    disabled: (args) => {
                        return args.record?.alterType === "2";
                    },
                    onChange: async (colVal, formBtnCallbackFn, tableBtnCallbackFn) => {
                        //切换时： 设置 原合同数量、原含税合同单价、原不含税合同单价、原含税合同金额、原不含税合同金额 赋值
                        const itemData = formBtnCallbackFn.itemData || {};
                        tableBtnCallbackFn.qnnTableInstance.setEditedRowData({
                            "originQty": 0,
                            "originPrice": 0,
                            "originPriceNoTax": 0,
                            "contractPrice": 0,
                            "contractCostNoTax": 0,

                            //后端要的数据
                            ccWorksParentNo: itemData.workNo,
                            ccWorksParentId: itemData.ccWorksId,
                            ccWorksId: undefined,
                            // ccWorksName: itemData.ccWorksName,
                        })


                    }
                }
            }
            , form: {
                type: "selectByQnnTable",
                formItemLayout: {
                    labelCol: {
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        sm: { span: 18 }
                    }
                },
                optionConfig: {
                    value: 'ccWorksId',
                    label: "workNo"
                },
                dropdownMatchSelectWidth: 800,
                qnnTableConfig: {
                    antd: {
                        rowKey: "ccWorksId",
                        scroll: {
                            x: "200px"
                        }
                    },

                    selectType: "radio", //默认radio单选  值为 string 
                    searchBtnsStyle: "inline",
                    fetchConfig: {
                        apiName: "getZxGcsgCcWorksSelect",
                        otherParams: (args) => {
                            return args.qnnFormProps?.qnnformData?.method?.getZxGcsgCcWorksSelectOtherParams?.("0")
                        }
                    },
                    formConfig: [
                        {
                            isInSearch: true,
                            table: {
                                dataIndex: "workNo",
                                title: "清单编号",
                            },
                            form: {
                                type: "string"
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                dataIndex: "workName",
                                title: "清单名称",
                            },
                            form: {
                                type: "string"
                            }
                        }
                    ]
                }
            }
        },
        {
            table: {
                title: '清单编号',
                dataIndex: 'ccWorksNo',
                width: 80,
                tdEdit: true,
                fieldConfig: {
                    dependencies: ['alterType'],
                    disabled: (args) => {
                        return args.record?.alterType === "2";
                    },
                }
            }
            , form: {
                type: 'string',

            }
        },
        {
            //变更类型修改时：类型为下拉表格, 反之为输入框
            table: {
                title: '清单名称',
                dataIndex: 'ccWorksId',
                width: 120,
                tdEdit: true,
                type: "selectByQnnTable",
                fieldConfig: {
                    dependencies: ['alterType'],
                    field: 'ccWorksId',
                    onBlur: async (colVal, formBtnCallbackFn, tableBtnCallbackFn) => {
                        const { alterType } = formBtnCallbackFn?.record || {}; //, ccCoAlterWorkId 
                        //获取表单对象 
                        if (alterType === "1") {
                            const realColVal = colVal?.target?.value;
                            // 没有说明是个输入框
                            // 这时候需要吧ccWorksId 字段变成ccWorksName 
                            //获取到变更细明表格数据 然后修改后重新赋值
                            tableBtnCallbackFn.qnnTableInstance.setEditedRowData({
                                //其实这时候接口只要 ccWorksName， 但是ccWorksId也不能删除 需要显示出来
                                ccWorksId: realColVal,
                                ccWorksName: realColVal,
                            })

                        }
                    },
                    onChange: async (colVal, formBtnCallbackFn, tableBtnCallbackFn) => {
                        //切换时： 设置 原合同数量、原含税合同单价、原不含税合同单价、原含税合同金额、原不含税合同金额 赋值
                        const itemData = formBtnCallbackFn.itemData;

                        if (!itemData) {
                            //在失去焦点中进行计算... 
                        } else {
                            tableBtnCallbackFn.qnnTableInstance.setEditedRowData({
                                ccWorksName: itemData.workName,

                                //后端要得数据
                                ccWorksParentId: itemData.parentID,

                                "ccWorksNo": itemData.workNo,

                                "isLeaf": itemData.isLeaf,
                                "ccWorksUnit": itemData.unit,
                                "originQty": itemData.contractQty || 0,
                                "originPrice": itemData.contractPrice || 0,
                                "originPriceNoTax": itemData.contractPriceNoTax || 0,
                                "contractPrice": itemData.contractAmt || 0,
                                "contractCostNoTax": itemData.contractAmtNoTax || 0,

                                "afterChangeQty": itemData.quantity || 0,
                                "afterChangePrice": itemData.price || 0,
                                "afterAmt": itemData.amt || 0,
                                "afterAmtNoTax": itemData.amtNoTax || 0,
                                "afterAmtTax": isNaN(itemData.amt - itemData.amtNoTax) ? 0 : itemData.amt - itemData.amtNoTax,
                                "taxRate": itemData.taxRate,

                                //变更类型为修改时2 这里需要记录用户手动选中的清单中的quantity(也算覆盖掉数据中的quantity)
                                quantity: itemData.quantity || 0,
                                //同时需要计算整行数据的其他单元格数据
                            })


                            //这里需要延时设置
                            setTimeout(() => {
                                //设置完毕后需要继续计算各种单元格中的数量
                                formBtnCallbackFn.funcCallBackParams.method.changeJS(colVal, formBtnCallbackFn, tableBtnCallbackFn);
                            }, 10)

                        }

                    },
                }
            }
            , form: {
                type: ({ rowData }) => {
                    return rowData.alterType === "1" ? 'string' : "selectByQnnTable"
                },
                formItemLayout: {
                    labelCol: {
                        sm: { span: 6 }
                    },
                    wrapperCol: {
                        sm: { span: 18 }
                    }
                },
                optionConfig: {
                    value: 'ccWorksId',
                    label: "workName"
                },
                dropdownMatchSelectWidth: 800,
                qnnTableConfig: {
                    antd: {
                        rowKey: "ccWorksId",
                        scroll: {
                            x: "200px"
                        }
                    },
                    //为下拉表格时需要用到的参数
                    selectType: "radio",
                    searchBtnsStyle: "inline",
                    fetchConfig: {
                        apiName: "getZxGcsgCcWorksSelect",
                        otherParams: (args) => {
                            return args.qnnFormProps?.qnnformData?.method?.getZxGcsgCcWorksSelectOtherParams?.("1")
                        }
                    },
                    formConfig: [
                        {
                            isInSearch: true,
                            table: {
                                dataIndex: "workNo",
                                title: "清单编号",
                            },
                            form: {
                                type: "string"
                            }
                        },
                        {
                            isInSearch: true,
                            table: {
                                dataIndex: "workName",
                                title: "清单名称",
                            },
                            form: {
                                type: "string"
                            }
                        }
                    ]
                }
            }
        },
        {
            //未选中 禁用： 变更递减数量、递减单价、税率， 反之不禁用
            table: {
                title: '是否叶子节点',
                dataIndex: 'isLeaf',
                width: 120,
                tdEdit: true,
                align: 'center',
                render: (data) => {
                    return data === 1 ? '是' : '否'
                },
                fieldConfig: {
                    dependencies: ['alterType'],
                    disabled: (args) => {
                        return args.record?.alterType === "2";
                    },

                    onChange: async (colVal, formBtnCallbackFn, tableBtnCallbackFn) => {
                        //colVal === 0 时： 清空 变更增减数量 到 变更后税额字段值
                        if (colVal === 1) return;

                        tableBtnCallbackFn.qnnTableInstance.setEditedRowData({
                            "changeQty": 0,
                            "changePrice": 0,
                            "afterChangeQty": 0,
                            "afterChangePrice": 0,
                            "afterAmt": 0,
                            "afterAmtNoTax": 0,
                            "afterAmtTax": 0,
                        })

                        //这里需要延时设置
                        setTimeout(() => {
                            //设置完毕后需要继续计算各种单元格中的数量
                            formBtnCallbackFn.funcCallBackParams.method.changeJS(colVal, formBtnCallbackFn, tableBtnCallbackFn);
                        }, 10)
                    }
                }
            }
            , form: {
                type: 'radio',
                optionData: [
                    { label: "是", value: 1 },
                    { label: "否", value: 0 }
                ],
            }
        },
        {
            table: {
                title: '单位',
                dataIndex: 'ccWorksUnit',
                width: 80,
                tdEdit: true
            }
            , form: {
                type: 'string',
            }
        },
        {
            table: {
                title: '原合同数量',
                dataIndex: 'originQty',
                width: 100,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '原含税合同单价',
                dataIndex: 'originPrice',
                width: 120,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '原不含税合同单价',
                dataIndex: 'originPriceNoTax',
                width: 130,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '原含税合同金额',
                dataIndex: 'contractPrice',
                width: 120,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '原不含税合同金额',
                dataIndex: 'contractCostNoTax',
                width: 130,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },

        {
            table: {
                title: '变更增减数量',
                dataIndex: 'changeQty',
                width: 120,
                tdEdit: true,
                fieldConfig: {
                    dependencies: ['alterType', 'isLeaf'],
                    disabled: (args) => {
                        if (args.record?.alterType === "2") {
                            return false;
                        } else {
                            return Number(args.record?.isLeaf) !== 1
                        }
                    },
                    onBlur: "bind:changeJS::changeQty"
                }
            }
            , form: {
                type: 'number',

            }
        },
        {
            table: {
                title: '增减单价',
                dataIndex: 'changePrice',
                width: 100,
                tdEdit: true,
                fieldConfig: {
                    dependencies: ['alterType', 'isLeaf'],
                    disabled: (args) => {
                        if (args.record?.alterType === "2") {
                            return true
                        }
                        return Number(args.record?.isLeaf) !== 1
                    },
                    onBlur: "bind:changeJS::changePrice"
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '变更后数量',
                dataIndex: 'afterChangeQty',
                width: 100,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '变更后单价',
                dataIndex: 'afterChangePrice',
                width: 100,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '变更后含税金额',
                dataIndex: 'afterAmt',
                width: 120,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '变更后不含税金额',
                dataIndex: 'afterAmtNoTax',
                width: 130,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '变更后税额',
                dataIndex: 'afterAmtTax',
                width: 100,
                tdEdit: true,
                fieldConfig: {
                    disabled: true
                }
            }
            , form: {
                type: 'number',
            }
        },
        {
            table: {
                title: '税率（%）',
                dataIndex: 'taxRate',
                width: 90,
                tdEdit: true,
                fieldConfig: {
                    dependencies: ['alterType'],
                    // required: true,
                    disabled: (args) => {
                        return args.record?.alterType === "2";
                    },
                    onChange: "bind:changeJS::taxRate"
                }
            }
            , form: {
                type: 'select',
                optionData: [
                    { value: '0', label: "0" },
                    { value: '1', label: 1 },
                    { value: '1.5', label: 1.5 },
                    { value: '2', label: 2 },
                    { value: '2.5', label: 2.5 },
                    { value: '3', label: 3 },
                    { value: '5', label: 5 },
                    { value: '6', label: 6 },
                    { value: '9', label: 9 },
                    { value: '10', label: 10 },
                    { value: '11', label: 11 },
                    { value: '13', label: 13 },
                    { value: '16', label: 16 },
                    { value: '17', label: 17 },
                ]
            }
        },
        {
            table: {
                title: '是否抵扣',
                dataIndex: 'isDeduct',
                width: 80,
                tdEdit: true,
                align: 'center',
                render: (data) => {
                    return data === "1" ? "是" : "否"
                },
                fieldConfig: {
                    disabled: true,
                    ov: "1", //打开开关的值 默认为true
                    cv: "0", //关闭开关的值 默认为false     
                }
            }
            , form: {
                type: 'switch',

            }
        },
        {
            table: {
                title: '备注',
                dataIndex: 'remarks',
                width: 150,
                tdEdit: true,
            }
            , form: {
                type: 'string',
            }
        },
    ]
}