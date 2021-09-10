import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, } from 'antd';
const config = {
   formItemLayout: {
      labelCol: {
         xs: { span: 8 },
         sm: { span: 8 }
      },
      wrapperCol: {
         xs: { span: 16 },
         sm: { span: 16 }
      }
   }
};
class index extends Component {
   //计算金额
   countAmt(tableOneData) {
      return tableOneData.map((item) => {
         if (item.hangCode) {
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

   //改变确认法
   async changeConfirmMethod(data) {
      await data.map((item) => {
         if (item.subDetail !== '中标合同总价（不含税）' && item.subDetail !== '项目标后预算费用总额') {
            item.amt = null
            item.remark = null
         }
         return item
      })
      return this.countAmt(data)
   }

   //获取项目标后预算费用总额+中标合同总价的默认值
   getAmt(data, apiName, type) {
      let params = this.props
      let pageData = data  //页面当前数据
      let subDetailName = type === '1' ? '项目标后预算费用总额' : '中标合同总价（不含税）'
      this.props.myFetch(apiName, { orgID: params.orgID })
         .then(({ data, success }) => {
            if (success) {
               if (data) {
                  if (type === '1') {
                     this.table.form.setFieldsValue({ AmtForBH: Number(data) })
                  } else {
                     this.table.form.setFieldsValue({ AmtForZB: Number(data) })
                  }
                  if (pageData.itemBaseList.length > 0) {
                     pageData.itemBaseList.map((item) => {
                        if (item.subDetail === subDetailName) {
                           item.amt = Number(data)
                        }
                        return item
                     })
                     this.table.form.setFieldsValue({ itemBaseList: pageData.itemBaseList })
                  } else if (pageData.itemList.length > 0) {
                     pageData.itemList.map((item) => {
                        if (item.subDetail === subDetailName) {
                           item.amt = Number(data)
                        }
                        return item
                     })
                     this.table.form.setFieldsValue({ itemList: pageData.itemList })
                  }
               }
            }
         })
   }
   render() {
      let params = this.props
      return (
         <div style={{ padding: '10px' }}>
            <QnnForm
               {...this.props}
               fetch={this.props.myFetch}
               upload={this.props.myUpload}
               headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
               {...config}
               wrappedComponentRef={(me) => {
                  this.table = me;
               }}
               fetchConfig={{
                  apiName: 'getZxCtContrCsjzItemList',
                  otherParams: { id: params.id },
                  success: ({ data }) => {
                     this.getAmt(data, 'getZxBuBudgetBookBudgetAmt', '1')
                     this.getAmt(data, 'getZxBuBudgetBookDifference', '2')
                  }
               }}

               formConfig={[
                  {
                     field: "AmtForZB",
                     type: 'number',
                     hide: true,
                  },
                  {
                     field: "AmtForBH",
                     type: 'number',
                     hide: true,
                  },
                  {
                     label: '确认法',
                     field: "confirmMethod",
                     type: 'select',
                     span: 12,
                     required: true,
                     allowClear: false,
                     optionConfig: { label: 'label', value: 'value' },
                     optionData: [
                        { label: '施工预算确认法', value: '1' },
                        { label: '标后预算调整确认法', value: '0' }
                     ],
                     initialValue: params.confirmMethod,
                     placeholder: '请选择',
                     disabled: params.clickName === 'detail' ? true : false,
                     onChange: async (val, obj) => {
                        let data = obj.form.getFieldsValue()
                        if (data?.itemList?.length > 0) {
                           this.changeConfirmMethod(data.itemList)
                           obj.form.setFieldsValue(data)
                        }
                        if (data?.itemBaseList?.length > 0) {
                           this.changeConfirmMethod(data.itemBaseList)
                           obj.form.setFieldsValue(data)
                        }
                     }
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
                              if (!tableData.confirmMethod) {
                                 Msg.warn('请选择确认法!')
                                 return
                              }
                              var formData = {}
                              formData.mainID = params.id
                              formData.confirmMethod = tableData.confirmMethod
                              formData.zxCtContrJzItemList = tableData.itemBaseList
                              this.props.myFetch('addZxCtContrJzItemForCs', formData)
                                 .then(({ success, message }) => {
                                    if (success) {
                                       Msg.success(message)
                                       params.TabOneRef.closeDrawer()
                                    } else {
                                       Msg.error(message)
                                    }
                                 }
                                 )
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
                                       return '初始预计总收入'
                                    } else if (h === '1') {
                                       return '初始预计总成本'
                                    }
                                 }
                              },
                           },
                           {
                              table: {
                                 title: '项目子类',
                                 dataIndex: 'subType2',
                                 key: 'subType2',
                                 width: 150,
                                 render: (h) => {
                                    if (h === '0') {
                                       return '标后预算调整确认法'
                                    } else if (h === '1') {
                                       return '施工预算确认法'
                                    }
                                 }
                              }
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
                                    let { AmtForZB, AmtForBH } = this.table.form.getFieldsValue()
                                    if (
                                       (obj.subDetail === '中标合同总价（不含税）' && (AmtForZB || AmtForZB === 0)) ||
                                       (obj.subDetail === '项目标后预算费用总额' && (AmtForBH || AmtForBH === 0)) ||
                                       obj.subDetail === '初始预计总收入' || obj.subDetail === '当前预计总收入' ||
                                       obj.subDetail === '初始预计总成本' || obj.subDetail === '当前预计总成本') {
                                       return false
                                    } else if ((obj.subType2 === this.table.form.getFieldValue('confirmMethod') || !obj.subType2) && obj.isHuizong !== '0') {
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
                                 tdEdit: (obj) => {
                                    if (obj.subType2 === this.table.form.getFieldValue('confirmMethod') || obj.subType2 === '') {
                                       return true
                                    }
                                    return false
                                 },
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
                              if (!tableData.confirmMethod) {
                                 Msg.warn('请选择确认法!')
                                 return
                              }
                              var formData = {}
                              formData.mainID = params.id
                              formData.confirmMethod = tableData.confirmMethod
                              formData.zxCtContrJzItemList = tableData.itemList
                              this.props.myFetch('addZxCtContrJzItemForCs', formData).then(
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
                                       return '初始预计总收入'
                                    } else if (h === '1') {
                                       return '初始预计总成本'
                                    }
                                 }
                              },
                           },
                           {
                              table: {
                                 title: '项目子类',
                                 dataIndex: 'subType2',
                                 key: 'subType2',
                                 width: 150,
                                 render: (h) => {
                                    if (h === '0') {
                                       return '标后预算调整确认法'
                                    } else if (h === '1') {
                                       return '施工预算确认法'
                                    }
                                 }
                              }
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
                                    let { AmtForZB, AmtForBH } = this.table.form.getFieldsValue()
                                    if (
                                       (obj.subDetail === '中标合同总价（不含税）' && (AmtForZB || AmtForZB === 0)) ||
                                       (obj.subDetail === '项目标后预算费用总额' && (AmtForBH || AmtForBH === 0)) ||
                                       obj.subDetail === '初始预计总收入' ||
                                       obj.subDetail === '当前预计总收入' ||
                                       obj.subDetail === '初始预计总成本' ||
                                       obj.subDetail === '当前预计总成本') {
                                       return false
                                    } else if ((obj.subType2 === this.table.form.getFieldValue('confirmMethod') || !obj.subType2) && obj.isHuizong !== '0') {
                                       return true
                                    } else {
                                       return false
                                    }
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
                                 tdEdit: (obj) => {
                                    if (obj.subType2 === this.table.form.getFieldValue('confirmMethod') || obj.subType2 === '') {
                                       return true
                                    }
                                    return false
                                 },
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
