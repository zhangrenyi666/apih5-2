
import React from "react"
import { PullRequestOutlined } from '@ant-design/icons'; //, PrinterOutlined

//合同单价分析
const logLabel = {
   labelCol: {
      sm: { span: 14 }
   },
   wrapperCol: {
      sm: { span: 10 }
   }
}

const shortLabel = {
   labelCol: {
      sm: { span: 3 }
   },
   wrapperCol: {
      sm: { span: 21 }
   }
}

export default {
   antd: {
      rowKey: "ctPriceSysId",
      size: "small"
   },
   fetchConfig: {
      apiName: "getZxGcsgCtPriceSysList",
      otherParams: "bind:getTabFourTableOtherParams"
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
      // {
      //    name: 'print',
      //    icon: <PrinterOutlined />,//icon
      //    type: 'primary',//类型  默认 primary  [primary dashed danger]
      //    label: '打印',
      //    onClick: "bind:printByFourTab"
      // },
      {
         name: 'export',
         icon: <PullRequestOutlined />,//icon
         type: 'primary',//类型  默认 primary  [primary dashed danger]
         label: '同步挂接数据',
         onClick: "bind:synchronouslySuspendData"
      },
   ],
   formConfig: [
      {
         isInTable:false, 
         form: {
            field:"ctPriceSysId",
            type: 'string', 
            hide: true
         }
      },
      {
         table: {
            title: '清单编号',
            dataIndex: 'workNo',
            fixed: 'left',//固定到右边
         },
         form: {
            type: 'string',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8
         }
      },
      {
         table: {
            title: '清单名称',
            dataIndex: 'workName',
            width:200
         },
         form: {
            type: 'string',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8
         }
      },
      {
         table: {
            title: '单位',
            dataIndex: 'workUnit',
         },
         form: {
            type: 'string',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8
         }
      },
      {
         table: {
            title: '不含税单价',
            dataIndex: 'priceNoTax',
         },
         form: {
            type: 'number',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '不含税标后预算单价/限价',
            dataIndex: 'bhPriceNoTax',
            width: 200,
         },
         form: {
            type: 'number',
            spanForm: 8,
            formItemLayout: logLabel,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '业主清单单价',
            dataIndex: 'yzPriceNoTax',
            width: 150
         },
         form: {
            type: 'number',
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '对比（标后预算/限价-不含税合价）',
            dataIndex: 'dbPrice',
            width: 240
         },
         form: {
            type: 'number',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8,
            formItemLayout: logLabel
         }
      },
      {
         table: {
            title: '主材',
            dataIndex: 'amt1',
         },
         form: {
            type: 'number',
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '管理费',
            dataIndex: 'amt2',
         },
         form: {
            type: 'number',
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '利润',
            dataIndex: 'amt3',
         },
         form: {
            type: 'number',
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '人工费',
            dataIndex: 'amt4',
         },
         form: {
            type: 'number',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '周转材料',
            dataIndex: 'amt5',
         },
         form: {
            type: 'number',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },
      {
         table: {
            title: '机械设备',
            dataIndex: 'amt6',
         },
         form: {
            type: 'number',
            addDisabled: true,
            editDisabled: true,
            spanForm: 8,
            precision: 6, //数值精度 
         }
      },

      //工序表格
      {
         isInTable: false,
         form: {
            type: 'qnnTable',
            label: '工序数据',
            field: 'ctPriceSysItemList',
            incToForm: true,
            formItemStyle: {
               marginLeft: 4
            },
            formItemLayout: shortLabel,
            qnnTableConfig: {
               tableTdEdit: true,
               antd: {
                  rowKey: "ctPriceSysItemId",
                  size: 'small',
               }, 
               formConfig: [
                  {
                     table: {
                        title: '工序编码',
                        dataIndex: 'processNo',
                        width: 120,
                        tdEdit: false
                     }
                     , form: {
                        type: 'string',
                     }
                  },
                  {
                     table: {
                        title: '标准工序名称',
                        dataIndex: 'processName',
                        width: 300,
                        tdEdit: false
                     }
                     , form: {
                        type: 'string',
                     }
                  },
                  {
                     table: {
                        title: '人工费',
                        dataIndex: 'rgf',
                        fieldConfig: {
                           onBlur: (event, formBtnCallbackFn, tableBtnCallbackFn) => {
                              //计算单价
                              setTimeout(async () => {
                                 const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                 if (!rowData["rgf"]) rowData["rgf"] = 0;
                                 if (!rowData["zzcl"]) rowData["zzcl"] = 0;
                                 if (!rowData["jxsb"]) rowData["jxsb"] = 0;
                                 // console.log('人工费获取的行数据：', rowData)
                                 await tableBtnCallbackFn.setEditedRowData({
                                    "price": rowData["rgf"] + rowData["zzcl"] + rowData["jxsb"]
                                 });
                              }, 10)
                           }
                        }
                     }
                     , form: {
                        type: 'number',
                        precision: 6, //数值精度 
                     }
                  },
                  {
                     table: {
                        title: '周转材料',
                        dataIndex: 'zzcl',
                        fieldConfig: {
                           onBlur: (event, formBtnCallbackFn, tableBtnCallbackFn) => {
                              //计算单价
                              setTimeout(async () => {
                                 const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                 if (!rowData["rgf"]) rowData["rgf"] = 0;
                                 if (!rowData["zzcl"]) rowData["zzcl"] = 0;
                                 if (!rowData["jxsb"]) rowData["jxsb"] = 0;
                                 await tableBtnCallbackFn.setEditedRowData({
                                    "price": rowData["rgf"] + rowData["zzcl"] + rowData["jxsb"]
                                 });
                              }, 10)
                           }
                        }
                     }
                     , form: {
                        type: 'number',
                        precision: 6, //数值精度 
                     }
                  },
                  {
                     table: {
                        title: '机械设备',
                        dataIndex: 'jxsb',
                        fieldConfig: {
                           onBlur: (event, formBtnCallbackFn, tableBtnCallbackFn) => {
                              //计算单价
                              setTimeout(async () => {
                                 const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                                 if (!rowData["rgf"]) rowData["rgf"] = 0;
                                 if (!rowData["zzcl"]) rowData["zzcl"] = 0;
                                 if (!rowData["jxsb"]) rowData["jxsb"] = 0;
                                 await tableBtnCallbackFn.setEditedRowData({
                                    "price": rowData["rgf"] + rowData["zzcl"] + rowData["jxsb"]
                                 });
                              }, 10)
                           }
                        }
                     }
                     , form: {
                        type: 'number',
                        precision: 6, //数值精度 
                     }
                  },
                  {
                     table: {
                        title: '单价',
                        dataIndex: 'price',
                        tdEdit: false
                     }
                     , form: {
                        type: 'number',
                        precision: 6, //数值精度 
                     }
                  },

               ].map(item => {
                  return { ...item, table: { tdEdit: true, ...item.table } }
               }),
            }
         }
      },

      {
         table: {
            title: '备注',
            dataIndex: 'remarks',
         },
         form: {
            type: 'textarea',
            formItemStyle: {
               marginLeft: 4
            }, 
            formItemLayout: shortLabel,
         }
      },

      {
         isInForm: false,
         table: {
            showType: 'tile',
            width: 80,
            // title: "操作",
            key: "action",//操作列名称
            fixed: 'right',//固定到右边
            btns: [
               {
                  label: "编辑",
                  name: 'edit',
                  fetchConfig: {
                     apiName: "psglbxGetZxGcsgCtPriceSysDetails",
                     params: {
                        ctPriceSysId: "ctPriceSysId"
                     }
                  },
                  formBtns: [
                     {
                        name: 'cancel', //关闭右边抽屉
                        type: 'dashed',//类型  默认 primary
                        label: '取消',
                     },
                     {
                        //自定义按钮key值 必须配置
                        field: "addCancelBtn",
                        name: 'submit',//内置add del
                        type: 'primary',//类型  默认 primary
                        label: '保存',//提交数据并且关闭右边抽屉
                        fetchConfig: {//ajax配置  ---可为func
                           apiName: 'psglbxUpdateZxGcsgCtPriceSysAndItem',
                        }
                     }
                  ],
               }
            ]
         }
      }
   ]
}