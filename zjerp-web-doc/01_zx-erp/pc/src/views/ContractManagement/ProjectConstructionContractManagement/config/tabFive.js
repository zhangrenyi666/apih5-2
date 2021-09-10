
// import React from "react"
// import { PullRequestOutlined } from '@ant-design/icons'; //, PrinterOutlined

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
      rowKey: "ctContrApplyId",
      size: "small"
   },
   fetchConfig: {
      apiName: "bxGetZxGcsgCtContrApplyList",
      otherParams: "bind:getTabFourTableOtherParams"
   },
   // data: [
   //    { ctPriceSysId: "001", workNo: "001" }
   // ],
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
      // {
      //    name: 'export',
      //    icon: <PullRequestOutlined />,//icon
      //    type: 'primary',//类型  默认 primary  [primary dashed danger]
      //    label: '同步挂接数据',
      //    onClick: "bind:synchronouslySuspendData"
      // },
   ],
   formConfig: [
      // {
      //    isInTable: false,
      //    form: {
      //       field: "ctPriceSysId",
      //       type: 'string',
      //       hide: true
      //    }
      // },
      {
			isInTable: false,
			form: {
				field: "ctContrApplyId",
				type: "string",
				hide: true,
			},
		},
      {
         table: {
            title: '补充协议编号',
            dataIndex: 'contractNo',
            fixed: 'left',//固定到右边
            width: 300,
            onClick:"detail"
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
            title: '补充协议名称',
            dataIndex: 'agreementName',
            width: 200
         },
         form: {
            type: 'string',
            required: true,
            spanForm: 8
         }
      },
      {
         table: {
            title: '合同名称',
            dataIndex: 'contractName',
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
            title: '合同类型',
            dataIndex: 'contractType',
            type:"select",
            width: 160
         },
         form: {
            // type: 'string',
            // initialValue: "工程施工类补充协议"
            addDisabled: true,
            editDisabled: true,
            spanForm: 8,
            type:"select",
				optionData: [{ label: "工程施工类补充协议", value: "P2C" }],
         }
      },

      {
         table: {
            title: '甲方名称',
            dataIndex: 'firstName',
            width: 200
         }
         , form: {
            type: 'string',
            spanForm: 8,
            addDisabled: true,
            editDisabled: true,
         }
      },
      {
         table: {
            title: '乙方名称',
            dataIndex: 'secondName',
            width: 200
         }
         , form: {
            // field: 'secondID',
            field: 'secondName',
            type: 'string',
            spanForm: 8,
            addDisabled: true,
            editDisabled: true,
         }
      },
      {
         table: {
            title: '合同签订人',
            dataIndex: 'agent',
         }
         , form: {
            type: 'string',
            spanForm: 8,
         }
      },

      {
         table: {
            title: '合同含税金额（万元）',
            dataIndex: 'contractCost',
            width: 200,
         },
         form: {
            type: 'number',
            spanForm: 8,
            formItemLayout: logLabel,
            precision: 6, //数值精度 
            addDisabled: true,
            editDisabled: true,
         }
      },
      {
         table: {
            title: '本期含税增减金额（万元）',
            dataIndex: 'currentTaxAmount',
            width: 200,
         },
         form: {
            type: 'number',
            spanForm: 8,
            formItemLayout: logLabel,
            precision: 6, //数值精度 
            addDisabled: true,
            editDisabled: true,
         }
      },
      {
         table: {
            title: '变更后含税金额（万元）',
            dataIndex: 'alterContractSum',
            width: 200,
         },
         form: {
            type: 'number',
            spanForm: 8,
            formItemLayout: logLabel,
            precision: 6, //数值精度 
            addDisabled: true,
            editDisabled: true,
         }
      },
      {
         table: {
            title: '开工日期',
            dataIndex: 'startDate',
            format: 'YYYY-MM-DD',
         }
         , form: {
            type: 'date',
            placeholder: '请选择',
            required: false,
            spanForm: 8,
         }
      },
      {
         table: {
            title: '竣工日期',
            dataIndex: 'endDate',
            format: 'YYYY-MM-DD',
         }
         , form: {
            type: 'date',
            placeholder: '请选择',
            required: false,
            spanForm: 8,
         }
      },
      {
         table: {
            title: '合同内容',
            dataIndex: 'content',
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
      // {
      //    isInTable: false
      //    , form: {
      //       type: 'files',
      //       label: '附件',
      //       field: 'commonAttachmentList',
      //       required: false,
      //       fetchConfig: { apiName: 'upload' },
      //       max: 999,
      //       formItemStyle: {
      //          marginLeft: 4
      //       },
      //       formItemLayout: shortLabel,
      //    }
      // },

      {
         isInForm: false,
         isInTable:(obj)=>{
            const { name } = obj.clickCb.rowInfo;
            if(name === 'detail'){
               return false
            }  
            return true;
         },
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
                  // fetchConfig: {
                  //    apiName: "psglbxGetZxGcsgCtPriceSysDetails",
                  //    params: {
                  //       ctPriceSysId: "ctPriceSysId"
                  //    }
                  // },
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
                           // apiName: 'psglbxUpdateZxGcsgCtPriceSysAndItem',
                           apiName: 'bxUpdateZxGcsgCtContrApply',
                           
                        }
                     }
                  ],
               }
            ]
         }
      }
   ]
}