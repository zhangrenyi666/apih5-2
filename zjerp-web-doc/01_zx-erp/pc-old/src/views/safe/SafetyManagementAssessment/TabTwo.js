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
    const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo
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
              field: "editLineName",
              type: 'string',
              hide: true
            },
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
                antd: {
                  rowKey: 'zxSfExamItemId',
                  size: 'small'
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxSfExamItemId !== this.table.form.getFieldValue('editLineName')
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
                      width: 300,
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
                      width: 150,
                      tdEdit: ext1 !== '1' ? true : false,
                      tdEdit: () => {
                        if (params.paramsData.status === '0' && ext1 !== '1') {
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
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfExamItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.selfScore = val ? val : 0;
                          }
                          return rowData;
                        })
                        if (!tableOneData.filter(item => item === null).length) {
                          setTimeout(() => {
                            this.tableOne.btnCallbackFn.setState({
                              data: tableOneData
                            })
                          }, 0)
                        }
                      }
                    }
                  },
                  {
                    table: {
                      title: '考评',
                      dataIndex: 'examScore',
                      key: 'examScore',
                      width: 150,
                      tdEdit: () => {
                        if (params.paramsData.status === '1' && ext1 === '2') {
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
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfExamItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.examScore = val ? val : 0;
                          }
                          return rowData;
                        })
                        if (!tableOneData.filter(item => item === null).length) {
                          setTimeout(() => {
                            this.tableOne.btnCallbackFn.setState({
                              data: tableOneData
                            })
                          }, 0)
                        }
                      }
                    }
                  },
                  {
                    isInTable: () => {
                      if (params.paramsData.clickName === 'detail') {
                        return false
                      } else {
                        return true
                      }
                    },
                    isInForm: false,
                    table: {
                      showType: 'tile',
                      width: 120,
                      title: "操作",
                      key: "action",//操作列名称
                      fixed: 'right',//固定到右边
                      align: 'center',
                      render: (data, rowData) => {
                        if (rowData.zxSfExamItemId === this.table.form.getFieldValue('editLineName')) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (obj.rowData.zxSfExamItemId === this.table.form.getFieldValue('editLineName')) {
                          Toast.loading('loading...');
                          setTimeout(() => {
                            if (obj.rowData.examScore > obj.rowData.allScore) {
                              Msg.warn('考评分数不能大于满分！')
                              Toast.hide()
                              return
                            }
                            if (obj.rowData.selfScore > obj.rowData.allScore) {
                              Msg.warn('自评分数不能大于满分！')
                              Toast.hide()
                              return
                            }
                            let body = { ...obj.rowData };
                            body.zxSfExamId = params.paramsData.zxSfExamId
                            this.props.myFetch(!isNaN(obj.rowData.zxSfExamItemId) ? 'addZxSfExamItem' : 'updateZxSfExamItem', body).then(
                              ({ success, message }) => {
                                if (success) {
                                  Msg.success(message);
                                  this.table.form.setFieldsValue({
                                    editLineName: ''
                                  })
                                  this.table.refresh()
                                  obj.btnCallbackFn.refresh();
                                  Toast.hide()
                                } else {
                                  Toast.hide()
                                  Msg.error(message);
                                }
                              }
                            );
                          }, 300)
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxSfExamItemId === this.table.form.getFieldValue('editLineName')) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.table.form.setFieldsValue({
                            editLineName: obj.rowData.zxSfExamItemId
                          })
                          obj.btnCallbackFn.refresh();
                        }
                      }
                    }
                  }
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
