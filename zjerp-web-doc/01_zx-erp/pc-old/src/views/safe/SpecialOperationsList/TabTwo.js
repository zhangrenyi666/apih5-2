import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
const confirm = Modal.confirm;
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
  isNumber(obj) {
    var t1 = /^\d+(\.\d+)?$/; //非负浮点数
    var t2 = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if (t1.test(obj) || t2.test(obj)) {
      return true;
    } else {
      return false;
    }

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
          formConfig={[
            {
              field: "editLineName",
              type: 'string',
              hide: true
            },
            {
              type: 'qnnTable',
              field: 'table1',
              qnnTableConfig: {
                fetchConfig: () => {
                  if (params.paramsData.tabIndex === '1') {
                    return {
                      apiName: 'getZxSfSpecialEmpItemList',
                      otherParams: { zxSfSpecialEmpId: params.paramsData.zxSfSpecialEmpId },
                    }
                  } else { return {} }
                },
                antd: {
                  rowKey: 'zxSfSpecialEmpItemId',
                  size: 'small'
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxSfSpecialEmpItemId !== this.table.form.getFieldValue('editLineName')
                },
                paginationConfig: false,
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                actionBtns: [
                  {
                    name: 'diyaddRow',//内置add del
                    icon: 'plus',//icon
                    type: 'primary',//类型  默认 primary  [primary dashed danger]
                    label: '新增行',
                    field: "add",
                    hide: () => {
                      if (params.paramsData.clickName === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    onClick: () => {
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxSfSpecialEmpItemId = new Date().getTime().toString();
                      newRowData.orgName = params.paramsData.orgName;
                      newRowData.orgID = params.paramsData.orgID;
                      newRowData.opProjName = [];
                      tableOneData.push(newRowData);
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.table.form.setFieldsValue({
                        editLineName: newRowData.zxSfSpecialEmpItemId
                      })
                    },
                  },
                  {
                    name: 'diydel',
                    icon: 'del',
                    type: 'danger',
                    label: '删除',
                    field: "del",
                    hide: () => {
                      if (params.paramsData.clickName === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    disabled: (obj) => {
                      if (obj.btnCallbackFn.getSelectedRows().length < 1) {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    onClick: (obj) => {
                      confirm({
                        content: '确定删除选中的数据吗?',
                        onOk: () => {
                          Toast.loading('loading...');
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var j = 0; j < data.length; j++) {
                            if (data[j].zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              Toast.hide()
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxSfSpecialEmpItem', deleteData).then(
                            ({ success, message }) => {
                              if (success) {
                                Toast.hide()
                                Msg.success(message);
                                obj.btnCallbackFn.clearSelectedRows();
                                obj.btnCallbackFn.refresh();
                              } else {
                                Toast.hide()
                                Msg.error(message);
                              }
                            }
                          );
                        }
                      })
                    }
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      label: '主键id',
                      field: 'zxSfSpecialEmpItemId',
                      hide: true
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'orgID',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: '所在项目',
                      dataIndex: 'orgName',
                      key: 'orgName',
                      width: 200,
                      tdEdit: false,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: `<div>姓名<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'name',
                      key: 'name',
                      width: 120,
                      align: 'center',
                      tdEdit: true,
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.name = val ? val : '';
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
                      title: '性别',
                      width: 120,
                      tdEdit: true,
                      dataIndex: 'sex',
                      key: 'sex',
                      align: 'center',
                      render: (h) => {
                        if (h === '0') {
                          return '男'
                        } else if (h === '1') {
                          return '女'
                        }
                      }
                    },
                    form: {
                      type: 'select',
                      placeholder: '请选择',
                      optionData: [
                        { label: '男', value: '0' },
                        { label: "女", value: '1' }
                      ],
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                      onChange: (val) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.sex = val ? val : null;
                          }
                          return rowData;
                        })
                        this.tableOne.btnCallbackFn.setState({
                          data: tableOneData
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: `<div>准操作项目<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'opProjName',
                      key: 'opProjName',
                      width: 250,
                      tdEdit: true,
                      align: 'center',
                      type: 'select'
                    },
                    form: {
                      type: 'select',
                      multiple: true,
                      placeholder: '请选择',
                      optionData: [
                        { label: '电工作业', value: '0' },
                        { label: "金属焊接切割作业", value: '1' },
                        { label: '起重机械含电梯作业', value: '2' },
                        { label: "企业内机动车辆驾驶", value: '3' },
                        { label: '登高架设作业', value: '4' },
                        { label: "锅炉作业", value: '5' },
                        { label: '压力容器操作', value: '6' },
                        { label: "爆破作业", value: '7' },
                        { label: '其他', value: '8' },
                      ],
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                      onChange: (val) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.opProjName = val ? val : null;
                          }
                          return rowData;
                        })
                        this.tableOne.btnCallbackFn.setState({
                          data: tableOneData
                        })
                      },
                      placeholder: '请选择',
                    }
                  },
                  {
                    table: {
                      title: '发证机关',
                      dataIndex: 'sendName',
                      key: 'sendName',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.sendName = val ? val : '';
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
                      title: '发证日期',
                      dataIndex: 'sendDate',
                      key: 'sendDate',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                      onChange: (val) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.sendDate = val ? val : null;
                          }
                          return rowData;
                        })
                        this.tableOne.btnCallbackFn.setState({
                          data: tableOneData
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: '证件号码',
                      dataIndex: 'cardNo',
                      key: 'cardNo',
                      width: 200,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.cardNo = val ? val : '';
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
                      title: '复审日期',
                      dataIndex: 'checkDate',
                      key: 'checkDate',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                      onChange: (val) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.checkDate = val ? val : null;
                          }
                          return rowData;
                        })
                        this.tableOne.btnCallbackFn.setState({
                          data: tableOneData
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: '退场时间',
                      dataIndex: 'outDate',
                      key: 'outDate',
                      width: 150,
                      tdEdit: true,
                      format: 'YYYY-MM-DD',
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      placeholder: '请选择',
                      onChange: (val) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                            rowData.outDate = val ? val : null;
                          }
                          return rowData;
                        })
                        this.tableOne.btnCallbackFn.setState({
                          data: tableOneData
                        })
                      },
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
                        if (rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (obj.rowData.zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                          Toast.loading('loading...');
                          setTimeout(() => {
                            if (!obj.rowData.name) {
                              Toast.hide()
                              Msg.warn('请输入姓名')
                              return
                            }
                            if (obj.rowData.opProjName.length === 0) {
                              Toast.hide()
                              Msg.warn('请输入准操作项目')
                              return
                            }
                            let body = { ...obj.rowData, };
                            body.zxSfSpecialEmpId = params.paramsData.zxSfSpecialEmpId
                            this.props.myFetch(this.isNumber(obj.rowData.zxSfSpecialEmpItemId) ? 'addZxSfSpecialEmpItem' : 'updateZxSfSpecialEmpItem', body).then(
                              ({ success, message }) => {
                                if (success) {
                                  Msg.success(message);
                                  this.table.form.setFieldsValue({
                                    editLineName: ''
                                  })
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
                            if (data[i].zxSfSpecialEmpItemId === this.table.form.getFieldValue('editLineName')) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.table.form.setFieldsValue({
                            editLineName: obj.rowData.zxSfSpecialEmpItemId
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
