// import React from "react"
// import { PullRequestOutlined } from '@ant-design/icons';

//下级清单列表
export default {
   antd: { 
      rowKey: "ctContrApplyWorksId",
      size: 'small',
      expandable: {
         //不可以展开和收缩
         onExpand: () => { },
         expandIcon: () => ""
      },
   },
   formConfig: [
     
      {
         table: {
            title: '清单编号',
            dataIndex: 'workNo', 
            width: 200
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '清单名称',
            dataIndex: 'workName', 
            width: 160
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
            title: '合同数量',
            dataIndex: 'contractQty',
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '含税合同单价',
            dataIndex: 'contractPrice',
            width: 120
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '不含税合同单价',
            dataIndex: 'contractPriceNoTax',
            width: 120
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '含税合同金额',
            dataIndex: 'contractAmt',
            width: 120
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '不含税合同金额',
            dataIndex: 'amtNoTax',
            width: 120
         }
         , form: {
            type: 'string',
         }
      },
      
      {
         table: {
            title: '税率（%）',
            dataIndex: 'taxRate',
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '是否抵扣',
            dataIndex: 'isDeduct',
            align: 'center',
            render: (data) => data === "1" ? "√" : "×"
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '备注',
            dataIndex: 'remarks',
            width: 180,
            tooltip: 14
         }
         , form: {
            type: 'string',
         }
      },
     
      // {
      //    isInForm: false,
      //    isInTable:(args)=>{ 
      //       return args.curEnv !== 'detail';
      //    },
      //    table: {
      //       title: '挂接',
      //       dataIndex: 'action',
      //       key: "action",//操作列名称
      //       align: 'center',
      //       fixed: 'right',
      //       showType: "tile",
      //       width: 60,
      //       // isLeaf  
      //       //叶子节点才渲染挂接按钮
      //       btns: (args) => { 
      //          return [
      //             {
      //                label: <span style={{ fontWeight: "800", fontSize: 18 }} title="挂接"><PullRequestOutlined /></span>,
      //                isValidate: true,//点击后是否验证表单 默认true
      //                icon: 'save',//icon
      //                type: 'primary', //primary dashed danger 
      //                onClick: "bind:rowGJClick"
      //             },
      //          ]
      //       }
      //    }
      // },
   ],
}