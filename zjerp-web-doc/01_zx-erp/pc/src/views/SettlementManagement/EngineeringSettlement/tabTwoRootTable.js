
export default {
   antd: {
      rowKey: "ccWorksId",
      size: 'small',
   },
   formConfig: [
      {
         table: {
            title: '业主清单编号',
            dataIndex: 'yzWorkNo',
            fixed: "left",
            width: 180,
         }
      },
      {
         table: {
            title: '业主清单名称',
            dataIndex: 'yzWorkName',
            fixed: "left",
            width: 160,
         }
      },
      {
         table: {
            title: '清单编号',
            dataIndex: 'workNo', 
            width: 200
         }
      },
      {
         table: {
            title: '清单名称',
            dataIndex: 'workName', 
            width: 160
         }
      },
      {
         table: {
            title: '计量单位',
            dataIndex: 'unit',
         }
      },
      {
         table: {
            title: '合同数量',
            dataIndex: 'contractQty',
         }
      },
      {
         table: {
            title: '含税合同单价',
            dataIndex: 'contractPrice',
            width: 120
         }
      },
      {
         table: {
            title: '不含税合同单价',
            dataIndex: 'contractPriceNoTax',
            width: 120
         }
      },
      {
         table: {
            title: '含税合同金额',
            dataIndex: 'contractAmt',
            width: 120
         }
      },
      {
         table: {
            title: '不含税合同金额',
            dataIndex: 'contractAmtNoTax',
            width: 120
         }
      },
      {
         table: {
            title: '变更后数量',
            dataIndex: 'quantity',
            width: 120
         }
      },
      {
         table: {
            title: '变更后含税单价',
            dataIndex: 'price',
            width: 120
         }
      },
      {
         table: {
            title: '变更后含税金额',
            dataIndex: 'amt',
            width: 120
         }
      },
      {
         table: {
            title: '变更后不含税单价',
            dataIndex: 'priceNoTax',
            width: 120
         }
      },
      {
         table: {
            title: '变更后不含税金额',
            dataIndex: 'amtNoTax',
            width: 120
         }
      },

      {
         table: {
            title: '税率（%）',
            dataIndex: 'taxRate',
         }
      },
      {
         table: {
            title: '是否抵扣',
            dataIndex: 'isDeduct',
            align: 'center',
            render: (data) => data === "1" ? "√" : "×"
         }
      },
      {
         table: {
            title: '备注',
            dataIndex: 'remarks',
            width: 180,
         }
      },
   ],
}