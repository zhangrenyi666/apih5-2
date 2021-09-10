import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
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
               fetchConfig={() => {
                  if (params.tabIndex === '1') {
                     return {
                        apiName: 'getZxCtContrDqjzItemList',
                        otherParams: { id: params.id },
                     }
                  } else { return {} }
               }}

               formConfig={[
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
                           onClick: () => {
                              var formData = {}
                              formData.mainID = params.id
                              formData.zxCtContrJzItemList = this.tableOne.state.data
                              this.props.myFetch('addZxCtContrJzItemForDq', formData).then(
                                 ({ success, message }) => {
                                    if (success) {
                                       Msg.success(message);
                                       this.props.btnCallbackFn.props.tableFns.closeDrawer()
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
                        tableTdEdit: true,
                        antd: {
                           rowKey: 'id',
                           size: 'small'
                        },
                        paginationConfig: false,
                        pageSize:999,
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
                                 title: '行标识',
                                 dataIndex: 'hangCode',
                                 key: 'hangCode',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '汇总到',
                                 dataIndex: 'huizongCode',
                                 key: 'huizongCode',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '金额',
                                 dataIndex: 'amt',
                                 key: 'amt',
                                 tdEdit: true,
                                 width: 120,
                              },
                              form: {
                                 type: 'number',
                                 placeholder: '请输入',
                                 disabled: (obj) => {
                                    if (obj.record.isHuizong === '0') {
                                       return true
                                    } else { return false }
                                 },
                                 onBlur: (obj, obj2) => {
                                    let val = obj.target.value;
                                    this.tableOne.getEditedRowData(false).then((data) => {
                                       data.amt = Number(val)
                                       this.tableOne.setEditedRowData({ ...data })
                                          .then((datas) => {
                                             if (datas && datas.length > 0) {
                                                let huizongCodeArr = obj2.record.huizongCode.split(';');
                                                huizongCodeArr.map((item) => {
                                                   let arr = []
                                                   let count = 0
                                                   for (var i = 0; i < datas.length; i++) {
                                                      arr.push(datas[i].hangCode)                     //获取所有汇总行
                                                      let huizongCodeArr = datas[i].huizongCode.split(';');
                                                      for (var j = 0; j < huizongCodeArr.length; j++) {
                                                         if (huizongCodeArr[j] === item) {
                                                            if (datas[i].isReduce === '1') {
                                                               count -= datas[i].amt ? datas[i].amt : 0
                                                            } else {
                                                               count += datas[i].amt ? datas[i].amt : 0
                                                            }
                                                         }
                                                      }
                                                   }
                                                   const huizongHangIndex = arr.indexOf(item)
                                                   datas[huizongHangIndex].amt = count
                                                })
                                                this.tableOne.btnCallbackFn.setState({ data: datas })
                                             }
                                          })
                                    })
                                 }
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
                                 onBlur: (obj) => {
                                    let val = obj.target.value;
                                    this.tableOne.getEditedRowData(false).then((data) => {
                                       data.remark = val
                                       this.tableOne.setEditedRowData({ ...data })
                                    })
                                 }
                              },
                           },
                        ],
                     },
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
                           onClick: () => {
                              var formData = {}
                              formData.mainID = params.id
                              formData.zxCtContrJzItemList = this.tableOne.state.data
                              this.props.myFetch('addZxCtContrJzItemForDq', formData).then(
                                 ({ success, message }) => {
                                    if (success) {
                                       Msg.success(message);
                                       this.props.btnCallbackFn.props.tableFns.closeDrawer()
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
                        tableTdEdit: true,
                        antd: {
                           rowKey: 'id',
                           size: 'small'
                        },
                        paginationConfig: {
                           position: 'bottom'
                        },
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
                                 title: '行标识',
                                 dataIndex: 'hangCode',
                                 key: 'hangCode',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '汇总到',
                                 dataIndex: 'huizongCode',
                                 key: 'huizongCode',
                                 width: 150,
                              }
                           },
                           {
                              table: {
                                 title: '金额',
                                 dataIndex: 'amt',
                                 key: 'amt',
                                 tdEdit: true,
                                 width: 120,
                              },
                              form: {
                                 type: 'number',
                                 placeholder: '请输入',
                                 disabled: (obj) => {
                                    if (obj.record.isHuizong === '0') {
                                       return true
                                    } else { return false }
                                 },
                                 onBlur: (obj, obj2) => {
                                    let val = obj.target.value;
                                    this.tableOne.getEditedRowData(false).then((data) => {
                                       data.amt = Number(val)
                                       this.tableOne.setEditedRowData({ ...data })
                                          .then((datas) => {
                                             if (datas && datas.length > 0) {
                                                let huizongCodeArr = obj2.record.huizongCode.split(';');
                                                huizongCodeArr.map((item) => {
                                                   let arr = []
                                                   let count = 0
                                                   for (var i = 0; i < datas.length; i++) {
                                                      arr.push(datas[i].hangCode)                     //获取所有汇总行
                                                      let huizongCodeArr = datas[i].huizongCode.split(';');
                                                      for (var j = 0; j < huizongCodeArr.length; j++) {
                                                         if (huizongCodeArr[j] === item) {
                                                            if (datas[i].isReduce === '1') {
                                                               count -= datas[i].amt ? datas[i].amt : 0
                                                            } else {
                                                               count += datas[i].amt ? datas[i].amt : 0
                                                            }
                                                         }
                                                      }
                                                   }
                                                   const huizongHangIndex = arr.indexOf(item)
                                                   datas[huizongHangIndex].amt = count
                                                })
                                                this.tableOne.btnCallbackFn.setState({ data: datas })
                                             }
                                          })
                                    })
                                 }
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
                                 onBlur: (obj) => {
                                    let val = obj.target.value;
                                    this.tableOne.getEditedRowData(false).then((data) => {
                                       data.remark = val
                                       this.tableOne.setEditedRowData({ ...data })
                                    })
                                 }
                              },
                           },
                        ],
                     },
                  },

               ]}
            />
         </div>
      );
   }
}
export default index;
