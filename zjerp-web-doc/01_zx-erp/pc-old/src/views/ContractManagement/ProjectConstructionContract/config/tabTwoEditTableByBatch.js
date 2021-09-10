//批量编辑

import React from "react"
import { RollbackOutlined, SaveOutlined, DeleteOutlined } from '@ant-design/icons';

//根据税率 和 不含税 计算含税
//根据税率 和 含税   计算不含税
// const getHS = (sl = 0, bhs = 0) => Number((bhs * (1 + sl / 100)).toFixed(2));
// const getBHS = (sl = 0, hs = 0) => Number((hs / (1 + (sl / 100))).toFixed(2));

//编辑下级节点的配置 
//@otherQnnTableConfig qnnTable块的配置
export default (otherQnnTableConfig) => {
   return {
      formConfig: [
         {
            type: 'qnnTable',
            label: '',
            field: 'lowerList',
            formItemStyle: { margin: 0 },
            qnnTableConfig: {
               ...otherQnnTableConfig,
               paginationConfig: false,
               isShowRowSelect: false,
               antd: {
                  rowKey: "ctContrApplyWorksId",
                  size: 'small',
                  scroll: {
                     y: window.innerHeight - 340
                  }
               },
               fetchConfig: {
                  apiName: "getZxGcsgCtContrApplyWorksEditAllList",
                  otherParams: "bind:getTabTwoEditTableByBatchOtherParams",
                  success: "bind:getTabTwoEditTableByBatchSuccess"
               },
               actionBtnsContainerStyle: { textAlign: 'right' },
               actionBtns: [
                  {
                     field: 'desc',
                     name: 'desc',
                     type: 'link',
                     label: "当前位于：批量编辑下级清单列表",
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
                  // {
                  //    field: 'del',
                  //    name: 'del',
                  //    type: 'danger',
                  //    label: "删除",
                  //    icon: "delete",
                  //    // onClick: "bind:tabTwoEditTableByBatchtableTdEditSaveCb",
                  // },
                  {
                     field: 'save',
                     name: 'save',
                     type: 'primary',
                     label: "保存",
                     icon: <SaveOutlined />,
                     onClick: "bind:tabTwoEditTableByBatchSave"
                  },
               ],

               //编辑表格后的回调
               // tableTdEditSaveCb: "bind:tabTwoEditTableByBatchtableTdEditSaveCb",
               formConfig: [
                  // {
                  //    isInForm: false,
                  //    table: {
                  //       width: 70,
                  //       dataIndex: "diySort",
                  //       title: "顺序",
                  //       fixed: 'left',
                  //       tdEdit: false,
                  //       render: "bind:sortRender"
                  //    }
                  // },

                  {
                     table: {
                        title: '清单类型',
                        dataIndex: 'workType',
                        width: 120,
                        render: () => "合同清单",
                        fieldConfig: {
                           type: 'select',
                           optionData: [{ label: "合同清单", value: 10 }],
                        },
                     }
                     , form: {
                        type: 'select',
                        initialValue: 10,
                     }
                  },
                  {
                     table: {
                        title: '<div>清单编码<span style="color:red">*</span></div>',
                        dataIndex: 'workNo',
                        width: 120,
                        tdEdit: true,
                        fieldConfig: {
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                           disabled: ({ rowIndex }) => rowIndex === 0
                        }
                     }
                     , form: {
                        type: 'string',
                        required: true
                     }
                  },
                  {
                     table: {
                        title: '<div>清单名称<span style="color:red">*</span></div>',
                        dataIndex: 'workName',
                        width: 120,
                        required: true,
                        tdEdit: true,
                        fieldConfig: {
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                           disabled: ({ rowIndex }) => rowIndex === 0
                        }
                     }
                     , form: {
                        type: 'string',
                     }
                  },
                  {
                     table: {
                        title: '计量单位',
                        dataIndex: 'unit',
                        tdEdit: true,
                        fieldConfig: {
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                        }
                     }
                     , form: {
                        type: 'string',
                     }
                  },
                  {
                     table: {
                        title: '工程量',
                        dataIndex: 'quantity',
                        tdEdit: true,
                        fieldConfig: {
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                        }
                     }
                     , form: {
                        type: 'number',
                     }
                  },
                  {
                     table: {
                        title: '税率',
                        dataIndex: 'taxRate',
                        tdEdit: true,
                        fieldConfig: {
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                        }
                     }
                     , form: {
                        type: 'select',
                        // type: 'number',
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
                        tdEdit: true,
                        width: 120,
                        fieldConfig: {
                           dependencies: ['taxRate', "contractPriceNoTax"],
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                           //需要对这个进行支持
                           // condition: [
                           //    {
                           //       regex: { taxRate: 1, },
                           //       action: 'disabled', //disabled, show, hide, function(){}
                           //    },
                           // ],
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
                        tdEdit: true,
                        width: 120,
                        fieldConfig: {
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                           dependencies: ['taxRate', "contractPrice"],
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
                  {
                     table: {
                        title: '要求修改',
                        dataIndex: '要求修改',
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
                  {
                     table: {
                        title: '修改人',
                        dataIndex: '修改人',
                        width: 120,
                        tdEdit: false,
                     },
                     form: {
                        type: 'string',
                     }
                  },
                  {
                     table: {
                        title: '修改日期',
                        dataIndex: '修改日期',
                        width: 120,
                        tdEdit: false,
                        format: "YYYY/MM/DD"
                     },
                     form: {
                        type: 'date',
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
                        width: 300,
                        tdEdit: true,
                        fieldConfig: {
                           onChange: "bind:tabTwoEditTableByBatchtableTdEditCb",
                        }
                     },
                     form: {
                        type: 'string',
                     }
                  },
                  {
                     isInForm: false,
                     isInTable: (args) => {
                        return args.curEnv !== 'detail';
                     },
                     table: {
                        title: '',
                        dataIndex: 'action',
                        key: "action",//操作列名称
                        align: 'center',
                        fixed: 'right',
                        showType: "tile",
                        width: 60,
                        //叶子节点才渲染挂接按钮
                        btns: [
                           {
                              label: <span style={{ color: "red" }}><DeleteOutlined /></span>,
                              isValidate: false,//点击后是否验证表单 默认true  
                              onClick: "bind:tabTwoEditTableByBatchDelRow"
                           }
                        ]
                     }
                  },

               ]
            }
         }
      ]
   }
}