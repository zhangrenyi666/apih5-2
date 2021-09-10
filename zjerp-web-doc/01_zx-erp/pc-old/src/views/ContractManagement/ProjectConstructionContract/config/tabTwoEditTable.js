import React from "react"
// import { CloseOutlined, SaveOutlined } from '@ant-design/icons';
import { RollbackOutlined } from '@ant-design/icons'; //, ColumnHeightOutlined
// import jjgzFieldConfig from "./jjgzFieldConfig"
// import { msg } from "qnn-apih5"
// import styles from "./ListSubcontracts.less"

//根据税率 和 不含税 计算含税
//根据税率 和 含税   计算不含税
const getHS = (sl = 0, bhs = 0) => Number((bhs * (1 + sl / 100)).toFixed(6));
const getBHS = (sl = 0, hs = 0) => Number((hs / (1 + (sl / 100))).toFixed(6));

//编辑下级节点的配置 
export default (otherQnnFormConfig) => {
   return { 
      formConfig: [
         {
            type: 'qnnTable',
            label: '',
            field: 'lowerList',
            incToForm: true,
            formItemStyle: { margin: 0 },
            qnnTableConfig: {
               ...otherQnnFormConfig,
               tableTdEdit: true,
               antd: {
                  rowKey: "ctContrApplyWorksId",
                  size: 'small',
               },
               fetchConfig: {
                  apiName: "getZxGcsgCtContrApplyWorksEditSubList",
                  otherParams: "bind:getTabTwoEditTableOtherParams"
               },
               actionBtnsContainerStyle: { textAlign: 'right' },
               actionBtns: [
                  {
                     field: 'desc',
                     name: 'desc',
                     type: 'link',
                     label: "当前位于：编辑下级清单列表",
                     style: {
                        float: "left",
                        padding: 0
                     }
                  },
                  {
                     field: 'cancel',
                     name: 'diycancel',
                     type: 'primary',
                     ghost: true,
                     icon: <RollbackOutlined />,
                     label: "返回",
                     onClick: ({ props: { qnnFormProps: { qnnformData: { qnnFormProps: { goLowerList } } } } }) => {
                        goLowerList();
                     }
                  },
                  {
                     field: 'del',
                     name: 'del',
                     type: 'danger',
                     label: "删除",
                     icon: "delete",
                     onClick: "bind:tabTwoEditTabletableTdEditSaveCb",

                     // onClick: async (args) => {
                     //    const { selectedRows, props: { fetch } } = args;
                     //    console.log('删除：', args)
                     //    const { code, message, success } = await fetch("del", selectedRows);
                     //    if (success) {
                     //       Msg.success(message)
                     //    } else {
                     //       msg.errMsg(message, code)
                     //    }
                     // },
                  },
                  {
                     field: 'addRow',
                     name: 'addRow',
                     type: 'primary',
                     label: "新增",
                     icon: "plus",
                     addRowDefaultData: {
                        workType: 10,
                     }
                  },
               ],

               //编辑表格后的回调
               tableTdEditSaveCb: "bind:tabTwoEditTabletableTdEditSaveCb",
               formConfig: [
                  {
                     isInForm: false,
                     table: {
                        width: 70,
                        dataIndex: "diySort",
                        title: "顺序",
                        fixed: 'left',
                        tdEdit: false,
                        render: "bind:sortRender"
                     }
                  },

                  {
                     table: {
                        title: '清单类型',
                        dataIndex: 'workType',
                        width: 120,
                        render: () => "合同清单",
                        fieldConfig: {
                           type: 'select',
                           optionData: [{ label: "合同清单", value: 10 }],
                        }
                     }
                     , form: {
                        type: 'select',
                        initialValue: 10,
                        // optionConfig: {
                        //    label: "itemName",
                        //    value: "itemId",
                        // },
                        // fetchConfig: {
                        //    apiName: "getBaseCodeSelect",
                        //    otherParams: {
                        //       itemId: "minzhu"
                        //    }
                        // }
                     }
                  },
                  {
                     table: {
                        title: '清单编码',
                        dataIndex: 'workNo',
                        width: 120,
                     }
                     , form: {
                        type: 'string',
                        required: true
                     }
                  },
                  {
                     table: {
                        title: '清单名称',
                        dataIndex: 'workName',
                        width: 120,
                        required: true
                     }
                     , form: {
                        type: 'string',
                     }
                  },
                  {
                     table: {
                        title: '计量单位',
                        dataIndex: 'unit',
                     }
                     , form: {
                        type: 'string',
                     }
                  },
                  {
                     table: {
                        title: '工程量',
                        dataIndex: 'quantity',
                     }
                     , form: {
                        type: 'number',
                     }
                  },
                  {
                     table: {
                        title: '税率',
                        dataIndex: 'taxRate',
                        fieldConfig: {
                           onChange: async (colVal, formBtnCallbackFn, tableBtnCallbackFn) => {
                              const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                              const vals = {};
                              // 如果没有 含税合同单价 并且 不含税合同单价存在
                              //用 不含税合同单价 来计算 含税合同单价
                              if (!rowData.contractPrice && rowData.contractPriceNoTax) {
                                 vals.contractPrice = getHS(Number(colVal), rowData.contractPriceNoTax)
                              } else {
                                 //其他情况一律使用含税合同单价 来计算 不含税合同单价
                                 vals.contractPriceNoTax = getBHS(Number(colVal), rowData.contractPrice)
                              }

                              // 如果 没有不含税合同单价 并且 含税合同单价存在
                              //用 含税合同单价 来计算 不含税合同单价
                              tableBtnCallbackFn.setEditedRowData(vals)
                           },
                        }
                     }
                     , form: {
                        type: 'select',
                        optionData: [
                           { value: "0", label: "0" },
                           { value: 1, label: 1 },
                           { value: 1.5, label: 1.5 },
                           { value: 2, label: 2 },
                           { value: 2.5, label: 2.5 },
                           { value: 3, label: 3 },
                           { value: 5, label: 5 },
                           { value: 6, label: 6 },
                           { value: 9, label: 9 },
                           { value: 10, label: 10 },
                           { value: 11, label: 11 },
                           { value: 13, label: 13 },
                           { value: 16, label: 16 },
                           { value: 17, label: 17 },
                        ]
                     }
                  },
                  {
                     table: {
                        title: '含税合同单价',
                        dataIndex: 'contractPrice',
                        width: 120,
                        fieldConfig: {
                           onBlur: (event, formBtnCallbackFn, tableBtnCallbackFn) => {

                              //用 税率 和 含税合同单价 来计算 不含税合同单价
                              setTimeout(async () => {
                                 const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                 tableBtnCallbackFn.setEditedRowData({
                                    "contractPriceNoTax": getBHS(Number(rowData.taxRate), event.target.value),
                                 });
                              }, 10)
                           }
                        }
                     }
                     , form: {
                        precision: 6, //数值精度 
                        type: 'number',
                     }
                  },
                  {
                     table: {
                        title: '不含税合同单价',
                        dataIndex: 'contractPriceNoTax',
                        width: 120,
                        fieldConfig: {
                           onBlur: (event, formBtnCallbackFn, tableBtnCallbackFn) => {
                              //用 税率 和 不含税合同单价 来计算 含税合同单价
                              setTimeout(async () => {
                                 const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                 await tableBtnCallbackFn.setEditedRowData({
                                    "contractPrice": getHS(Number(rowData.taxRate), event.target.value),
                                 });
                              }, 10)
                           }
                        }
                     }
                     , form: {
                        precision: 6, //数值精度 
                        type: 'number',
                     }
                  },

                  {
                     table: {
                        title: '是否抵扣',
                        dataIndex: 'isDeduct',
                        width: 120,
                        tdEdit: false,
                        render: (data) => data === "1" ? "是" : "否"
                     },
                     form: {
                        type: 'radio',
                        optionData: [
                           { label: "否", value: "0" },
                           { label: "是", value: "1" }
                        ]
                     }
                  },
                  // {
                  //    table: {
                  //       title: '计价规则',
                  //       dataIndex: 'ruleName',
                  //       width: 300
                  //    },
                  //    form: {
                  //       ...jjgzFieldConfig
                  //    }
                  // },
                  {
                     table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        width: 300
                     },
                     form: {
                        type: 'string',
                     }
                  },


               ].map(item => {
                  return { ...item, table: { tdEdit: true, ...item.table } }
               }),
            }
         }
      ]
   }
}