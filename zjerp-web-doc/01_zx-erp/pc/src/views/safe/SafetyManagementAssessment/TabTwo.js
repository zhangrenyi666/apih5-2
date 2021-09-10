import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg } from 'antd';
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
    const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany
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
          formConfig={[
            {
              label: '满分合计',
              field: "totalAllScore",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 8
            },
            {
              label: '自评合计',
              field: "totalSelfScore",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 8
            },
            {
              label: '考评合计',
              field: "totalExamScore",
              type: 'number',
              disabled: true,
              initialValue: 0,
              span: 8
            },
            {
              type: 'qnnTable',
              field: 'table1',
              qnnTableConfig: {
                fetchConfig: () => {
                  if (params.paramsData.tabIndex === '1') {
                    return {
                      apiName: 'getZxSfExamItemList',
                      otherParams: { examID: params.paramsData.zxSfExamId },
                      success: (data) => {
                        if (data.success && data.data.length > 0) {
                          let totalAllScore = 0, totalSelfScore = 0, totalExamScore = 0
                          for (var i = 0; i < data.data.length; i++) {
                            totalAllScore += data.data[i].allScore ? Number(data.data[i].allScore) : 0
                            totalSelfScore += data.data[i].selfScore ? Number(data.data[i].selfScore) : 0
                            totalExamScore += data.data[i].examScore ? Number(data.data[i].examScore) : 0
                          }
                          this.table.form.setFieldsValue({
                            totalAllScore, totalSelfScore, totalExamScore
                          })
                        }
                      }
                    }
                  } else { return {} }
                },
                actionBtns: [
                  {
                    name: 'diyButton',
                    type: 'primary',
                    label: '保存',
                    field: "diyButton",
                    hide: () => { if (params.paramsData.clickName === 'detail') return true },
                    onClick: async (obj) => {
                      let costaccountList = await obj.qnnTableInstance.getTableData()
                      obj.btnCallbackFn.setBtnsLoading('add', 'diyButton');
                      this.props.myFetch('batchUpdateZxSfExamItem', costaccountList)
                        .then(({ success, message }) => {
                          if (success) {
                            obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                            this.tableOne.refresh()
                            Msg.success(message)
                          } else {
                            obj.btnCallbackFn.setBtnsLoading('remove', 'diyButton');
                            Msg.error(message)
                          }
                        })
                    }
                  }
                ],
                antd: {
                  rowKey: 'zxSfExamItemId',
                  size: 'small'
                },
                isShowRowSelect: false,
                paginationConfig: false,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxSfExamItemId',
                      hide: true
                    }
                  },
                  {
                    table: {
                      title: '考核项目',
                      dataIndex: 'examName',
                      key: 'examName',
                      width: 120,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '考核内容',
                      width: 400,
                      tooltip: 30,
                      dataIndex: 'examContext',
                      key: 'examContext',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '满分',
                      dataIndex: 'allScore',
                      key: 'allScore',
                      width: 100,
                      tdEdit: false,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '自评',
                      dataIndex: 'selfScore',
                      key: 'selfScore',
                      width: 100,
                      tdEdit: () => {
                        if (params.paramsData.clickName === 'detail') {
                          return false
                        } else if (params.paramsData.status === '0' && ext1 !== '1') {
                          return true
                        } else {
                          return false
                        }
                      },
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        let value = rowData.selfScore
                        if (rowData.allScore && rowData.allScore < value) {
                          Msg.warn('自评分数不能大于满分！')
                          rowData.selfScore = null
                          await obj.qnnTableInstance.setEditedRowData(rowData)
                        } else {
                          rowData.selfScore = value
                          await obj.qnnTableInstance.setEditedRowData(rowData)
                        }
                      }
                    }
                  },
                  {
                    table: {
                      title: '考评',
                      dataIndex: 'examScore',
                      key: 'examScore',
                      width: 100,
                      tdEdit: () => {
                        if (params.paramsData.clickName === 'detail') {
                          return false
                        } else if (params.paramsData.status === '1' && ext1 === '2') {
                          return true
                        } else if (params.paramsData.status === '2' && ext1 === '1') {
                          return true
                        } else {
                          return false
                        }
                      },
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: async (val, obj) => {
                        let rowData = await obj.qnnTableInstance.getEditedRowData()
                        let value = rowData.examScore
                        if (rowData.allScore && rowData.allScore < value) {
                          Msg.warn('自评分数不能大于满分！')
                          rowData.examScore = null
                          await obj.qnnTableInstance.setEditedRowData(rowData)
                        } else {
                          rowData.examScore = value
                          await obj.qnnTableInstance.setEditedRowData(rowData)
                        }
                      }
                    }
                  },
                ],
              },
            }
          ]}
        />
      </div>
    );
  }
}
export default index;
