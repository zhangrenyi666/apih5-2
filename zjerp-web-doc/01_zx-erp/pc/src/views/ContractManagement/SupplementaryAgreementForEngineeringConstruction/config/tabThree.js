// import React from "react"
// import { ExportOutlined, PrinterOutlined } from '@ant-design/icons';

//清单工序挂接台账
export default {
   isShowRowSelect:false,
   antd: { 
      rowKey:"processID",
      size: "small"
   },

   fetchConfig: {
      apiName: "getZxGcsgCcCoAlterWorkListProcess",
      otherParams: "bind:getTabThreeTableOtherParams"
   },

   dataFormat: "bind:tabThreeDataFormat",

   actionBtns: [
      // {
      //    name: 'export',
      //    icon: <ExportOutlined />,//icon
      //    type: 'primary',//类型  默认 primary  [primary dashed danger]
      //    label: '导出',
      //    onClick: "bind:exportByThreeTab"
      // },
      // {
      //    name: 'print',
      //    icon: <PrinterOutlined />,//icon
      //    type: 'primary',//类型  默认 primary  [primary dashed danger]
      //    label: '打印',
      //    onClick: "bind:printByThreeTab"
      // }
   ],
   formConfig: [
      {
         table: {
            title: '清单编号',
            dataIndex: 'workNo',
            render: (data, rowData) => {
               return {
                  children: data,
                  props: {
                     rowSpan: rowData.rowSpan
                  },
               };
            }
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '清单名称',
            dataIndex: 'workName',
            render: (data, rowData) => {
               return {
                  children: data,
                  props: {
                     rowSpan: rowData.rowSpan
                  },
               };
            }
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '工序名称',
            dataIndex: 'processName',
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '施工内容',
            dataIndex: 'content',
            width:300,
            tooltip:28
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '工序编码',
            dataIndex: 'processNo',
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '计价规则',
            dataIndex: 'ruleName',
            textOverflow:"lineFeed",
            render: (data, rowData) => {
               return {
                  children: data,
                  props: {
                     rowSpan: rowData.rowSpan
                  },
               };
            }
         }
         , form: {
            type: 'string',
         }
      },
   ]
}