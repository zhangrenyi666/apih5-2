import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, } from 'antd';
class index extends Component {
   countAmt(tableOneData) {
      return tableOneData.map((item) => {
         if (item.hangCode && item.subDetail !== '初始预计总收入' && item.subDetail !== '初始预计总成本') {
            let count = 0
            for (var i = 0; i < tableOneData.length; i++) {
               if (tableOneData[i].huizongCode) {
                  let huizongCodeArr = tableOneData[i].huizongCode.split(';')
                  for (var j = 0; j < huizongCodeArr.length; j++) {
                     if (huizongCodeArr[j] === item.hangCode) {
                        if (tableOneData[i].isReduce === '1') {
                           count -= tableOneData[i].amt ? tableOneData[i].amt : 0
                        } else {
                           count += tableOneData[i].amt ? tableOneData[i].amt : 0
                        }
                     }
                  }
               }
            }
            item.amt = Number(count.toFixed(2))
         } else if (item.amt) {
            item.amt = Number(item.amt.toFixed(2))
         }
         return item
      })
   }
   render() {
      let params = this.props
      return (
         <div style={{ padding: '10px' }}>
            <QnnForm
               fetch={this.props.myFetch}
               wrappedComponentRef={(me) => {
                  this.table = me;
               }}
               fetchConfig={{
                  apiName: 'getZxCtContrDqjzItemList',
                  otherParams: { id: params.id },
               }}
               addIsGetData={true}
               formConfig={[
                  {
                     field: "subType2",
                     type: 'string',
                     hide: true,
                  },
                  {
                     type: 'qnnTable',
                     field: 'itemBaseList',
                     hide: (obj) => {
                        if (this.table?.form?.getFieldValue('itemBaseList')?.length > 0) {
                           return false
                        } else {
                           return true
                        }
                     },
                     actionBtns: [
                        {
                           name: "save",
                           type: "primary",
                           label: "保存",
                           hide: params.clickName === 'detail',
                           onClick: () => {
                              let tableData = this.table.form.getFieldsValue()
                              var formData = {}
                              formData.mainID = params.id
                              // if (params.clickName === 'add') {
                              for (var i = 0; i < tableData.itemBaseList.length; i++) {
                                 tableData.itemBaseList[i].id = null
                              }
                              // }
                              formData.zxCtContrJzItemList = tableData.itemBaseList
                              this.props.myFetch('addZxCtContrJzItemForDq', formData).then(
                                 ({ success, message }) => {
                                    if (success) {
                                       Msg.success(message)
                                       params.TabOneRef.closeDrawer()
                                    } else {
                                       Msg.error(message)
                                    }
                                 }
                              );
                           }
                        },
                     ],
                     incToForm: true,
                     qnnTableConfig: {
                        rowDisabledCondition: params.clickName === 'detail',
                        antd: {
                           rowKey: 'id',
                           size: 'small'
                        },
                        paginationConfig: false,
                        pageSize: 999,
                        wrappedComponentRef: (me) => {
                           this.tableOne = me;
                        },
                        isShowRowSelect: false,
                        formConfig: [
                           {
                              isInTable: false,
                              form: {
                                 field: 'id',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'hangCode',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'isReduce',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'huizongCode',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'isHuizong',
                                 hide: true
                              }
                           },
                           {
                              table: {
                                 title: '项目',
                                 dataIndex: 'subType',
                                 key: 'subType',
                                 width: 150,
                                 render: (h) => {
                                    if (h === '0') {
                                       return '预计总收入'
                                    } else if (h === '1') {
                                       return '预计总成本'
                                    }
                                 }
                              },
                           },
                           {
                              table: {
                                 title: '项目内容',
                                 dataIndex: 'subDetail',
                                 key: 'subDetail',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '项目内容明细',
                                 dataIndex: 'subDetail2',
                                 key: 'subDetail2',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '金额',
                                 dataIndex: 'amt',
                                 key: 'amt',
                                 tdEdit: (obj) => {
                                    if (
                                       obj.subDetail === '初始预计总收入' || obj.subDetail === '当前预计总收入' ||
                                       obj.subDetail === '初始预计总成本' || obj.subDetail === '当前预计总成本' ||
                                       obj.subDetail === '上期预计总收入' || obj.subDetail === '上期预计总成本') {
                                       return false
                                    } else if (obj.isHuizong !== '0') {
                                       return true
                                    }
                                    return false
                                 },
                                 width: 120,
                              },
                              form: {
                                 type: 'number',
                                 field: 'amt',
                                 placeholder: '请输入',
                                 onBlur: async (val, obj) => {
                                    let tableOneData = await this.tableOne.getTableData()
                                    const data = await this.countAmt(tableOneData)
                                    obj.qnnTableInstance.setTableData(data)
                                 },
                              },
                           },
                           {
                              table: {
                                 title: '说明',
                                 dataIndex: 'remark',
                                 key: 'remark',
                                 tdEdit: true,
                                 width: 150,
                              },
                              form: {
                                 type: 'string',
                                 placeholder: '请输入',
                              }
                           }
                        ]
                     }
                  },
                  {
                     type: 'qnnTable',
                     field: 'itemList',
                     hide: (obj) => {
                        if (this.table?.form?.getFieldValue('itemList')?.length > 0) {
                           return false
                        } else {
                           return true
                        }
                     },
                     actionBtns: [
                        {
                           name: "save",
                           type: "primary",
                           label: "保存",
                           hide: params.clickName === 'detail',
                           onClick: () => {
                              let tableData = this.table.form.getFieldsValue()
                              var formData = {}
                              formData.mainID = params.id
                              if (params.clickName === 'add') {
                                 for (var i = 0; i < tableData.itemList.length; i++) {
                                    tableData.itemList[i].id = null
                                 }
                              }
                              formData.zxCtContrJzItemList = tableData.itemList
                              this.props.myFetch('addZxCtContrJzItemForDq', formData).then(
                                 ({ success, message }) => {
                                    if (success) {
                                       Msg.success(message)
                                       params.TabOneRef.closeDrawer()
                                    } else {
                                       Msg.error(message)
                                    }
                                 }
                              );
                           }
                        },
                     ],
                     incToForm: true,
                     qnnTableConfig: {
                        rowDisabledCondition: params.clickName === 'detail',
                        antd: {
                           rowKey: 'id',
                           size: 'small'
                        },
                        paginationConfig: false,
                        pageSize: 999,
                        wrappedComponentRef: (me) => {
                           this.tableTwo = me;
                        },
                        isShowRowSelect: false,
                        formConfig: [
                           {
                              isInTable: false,
                              form: {
                                 field: 'id',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'hangCode',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'isHuizong',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'isReduce',
                                 hide: true
                              }
                           },
                           {
                              isInTable: false,
                              form: {
                                 field: 'huizongCode',
                                 hide: true
                              }
                           },
                           {
                              table: {
                                 title: '项目',
                                 dataIndex: 'subType',
                                 key: 'subType',
                                 width: 150,
                                 render: (h) => {
                                    if (h === '0') {
                                       return '预计总收入'
                                    } else if (h === '1') {
                                       return '预计总成本'
                                    }
                                 }
                              },
                           },
                           {
                              table: {
                                 title: '项目内容',
                                 dataIndex: 'subDetail',
                                 key: 'subDetail',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '项目内容明细',
                                 dataIndex: 'subDetail2',
                                 key: 'subDetail2',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '金额',
                                 dataIndex: 'amt',
                                 key: 'amt',
                                 tdEdit: (obj) => {
                                    if (
                                       obj.subDetail === '初始预计总收入' || obj.subDetail === '当前预计总收入' ||
                                       obj.subDetail === '初始预计总成本' || obj.subDetail === '当前预计总成本' ||
                                       obj.subDetail === '上期预计总收入' || obj.subDetail === '上期预计总成本') {
                                       return false
                                    } else if (obj.isHuizong !== '0') {
                                       return true
                                    }
                                    return false
                                 },
                                 width: 120,
                              },
                              form: {
                                 type: 'number',
                                 placeholder: '请输入',
                                 field: 'amt',
                                 onBlur: async (val, obj) => {
                                    let tableOneData = await this.tableTwo.getTableData()
                                    const data = await this.countAmt(tableOneData)
                                    obj.qnnTableInstance.setTableData(data)
                                 },
                              },
                           },
                           {
                              table: {
                                 title: '说明',
                                 dataIndex: 'remark',
                                 key: 'remark',
                                 tdEdit: true,
                                 width: 150,
                              },
                              form: {
                                 type: 'string',
                                 placeholder: '请输入',
                              }
                           }
                        ]
                     }
                  }
               ]}
            />
         </div>
      )
   }
}
export default index;
