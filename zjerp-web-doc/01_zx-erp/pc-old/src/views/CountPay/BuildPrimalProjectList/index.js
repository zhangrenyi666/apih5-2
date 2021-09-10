import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'id',
    size: 'small'
  },
  drawerConfig: {
    width: window.screen.width * 0.7
  },
  paginationConfig: {
    position: 'bottom'
  },
  firstRowIsSearch: false,
  isShowRowSelect: true
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: 0
    }
  }
  render() {
    const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxCtContrJzBaseList',
            otherParams: {
              type: '1',
              orgID: departmentId
            }
          }}
          actionBtns={[
            {
              name: 'add',
              icon: 'plus',
              type: 'primary',
              label: '新增',
              formBtns: [
                {
                  name: 'cancel',
                  type: 'dashed',
                  label: '取消',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                },
                {
                  name: 'diysubmit',
                  type: 'primary',
                  label: '保存',
                  field: 'addsubmit',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                  onClick: (val) => {
                    val._formData.type = "1";
                    const { myFetch } = this.props;
                    val.btnCallbackFn.setBtnsLoading('add', 'addsubmit');
                    myFetch('addZxCtContrJzBase', val._formData).then(
                      ({ data, success, message }) => {
                        if (success) {
                          val.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                          Msg.success(message);
                          val.btnCallbackFn.refresh();
                          val.btnCallbackFn.form.setFieldsValue(data);
                          val.btnCallbackFn.setActiveKey('1');
                        } else {
                          val.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                          Msg.error(message)
                        }
                      }
                    );
                  }
                }
              ]
            },
            {
              name: 'edit',
              icon: 'edit',
              type: 'primary',
              label: '编辑',
              onClick: () => {
                this.table.clearSelectedRows();
              },
              formBtns: [
                {
                  name: 'cancel',
                  type: 'dashed',
                  label: '取消',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                },
                {
                  name: 'diySubmit',
                  type: 'primary',
                  label: '保存',
                  field: 'editsubmit',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                  onClick: (val) => {
                    const { myFetch } = this.props;
                    val.btnCallbackFn.setBtnsLoading('add', 'editsubmit');
                    myFetch('updateZxCtContrJzBase', val._formData).then(
                      ({ success, message }) => {
                        if (success) {
                          val.btnCallbackFn.setBtnsLoading('remove', 'editsubmit');
                          Msg.success(message);
                          val.btnCallbackFn.refresh();
                          val.btnCallbackFn.setActiveKey('1');
                        } else {
                          val.btnCallbackFn.setBtnsLoading('remove', 'editsubmit');
                          Msg.error(message)
                        }
                      }
                    );
                  }
                }
              ]
            },
            {
              name: 'del',//内置add del
              icon: 'delete',//icon
              type: 'danger',//类型  默认 primary  [primary dashed danger]
              label: '删除',
              fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZxCtContrJzBase',
              },
            }
          ]}
          formConfig={[
            {
              table: {
                title: '项目名称',
                dataIndex: 'orgName',
                key: 'orgName',
                filter: true,
                onClick: 'detail',
              },
            },
            {
              table: {
                title: '说明',
                dataIndex: 'remarks',
                key: 'remarks',
              }
            },
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "基础信息",
              content: {
                formConfig: [
                  {
                    field: "editLineName",
                    type: 'string',
                    hide: true,
                    initialValue: ''
                  },
                  {
                    field: 'id',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '项目名称',
                    field: 'orgID',
                    required: true,
                    type: 'select',
                    showSearch: true,
                    fetchConfig: {
                      apiName: 'getZxCtContractListByStatus',
                      otherParams: {
                        orgID: departmentId
                      }
                    },
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        orgName: 'orgName',
                        comID: 'companyId',
                        comName: 'companyName'
                      }
                    },
                    editDisabled: true,   //编辑禁用
                    // addDisabled:true,    //新增禁用
                    placeholder: '请选择',
                    span: 16,
                    formItemLayoutForm: {
                      labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                      },
                      wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                      }
                    },
                  },

                  {
                    field: 'remarks',
                    label: '说明',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 16,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 20 },
                        sm: { span: 20 }
                      }
                    }
                  },
                ]
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "清单",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("contractReviewId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtContrJzItemBaseList',
                    otherParams: { jzType: '1', mainID: this.table.form.getFieldValue('id') },
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "id",
                },
                paginationConfig: false,
                pageSize: 99,
                rowDisabledCondition: (rowData) => {
                  return rowData.id !== this.table.form.getFieldValue('editLineName')
                },
                firstRowIsSearch: false,
                isShowRowSelect: false,
                formConfig: [
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
                    },
                  },
                  {
                    table: {
                      title: '项目内容',
                      dataIndex: 'subDetail',
                      key: 'subDetail',
                      width: 150,
                    },
                  },
                  {
                    table: {
                      title: '项目内容明细',
                      dataIndex: 'subDetail2',
                      key: 'subDetail2',
                      width: 150,
                      tdEdit: true,
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.id === this.table.form.getFieldValue('editLineName')) {
                            rowData.subDetail2 = val
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
                    },
                  },
                  {
                    table: {
                      title: '说明',
                      width: 150,
                      tooltip: 15,
                      dataIndex: 'remarks',
                      key: 'remarks',
                      tdEdit: true,
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.id === this.table.form.getFieldValue('editLineName')) {
                            rowData.remarks = val
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
                    isInTable: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'detail') {
                        return false
                      } else {
                        return true
                      }
                    },
                    table: {
                      showType: 'tile',
                      width: 80,
                      title: "操作",
                      key: "action",
                      fixed: 'right',
                      btns: [
                        {
                          render: (data, rowData) => {
                            if (rowData.id === this.table.form.getFieldValue('editLineName')) {
                              return <a>保存</a>;
                            } else {
                              return <a>编辑</a>;
                            }
                          },
                          onClick: (obj) => {
                            if (obj.rowData.id === this.table.form.getFieldValue('editLineName')) {
                              Toast.loading('loading...');
                              setTimeout(() => {
                                let body = { ...obj.rowData };
                                body.mainID = this.table.form.getFieldValue('id')
                                this.props.myFetch(!isNaN(obj.rowData.id) ? 'addZxCtContrJzItemBase' : 'updateZxCtContrJzItemBase', body).then(
                                  ({ success, message }) => {
                                    if (success) {
                                      Msg.success(message);
                                      this.table.form.setFieldsValue({
                                        editLineName: ''
                                      })
                                      Toast.hide()
                                      obj.btnCallbackFn.refresh();
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
                                if (data[i].id === this.table.form.getFieldValue('editLineName')) {
                                  Msg.warn('有未保存数据，请保存后重试。')
                                  return
                                }
                              }
                              this.table.form.setFieldsValue({
                                editLineName: obj.rowData.id
                              })
                              obj.btnCallbackFn.refresh();
                            }
                          }
                        },
                        {
                          render: function (data, rows) {
                            const type = rows.isHuizong === '0' ? '' : rows.isHuizong === '1' ? '新增' : '删除'
                            return '<a>' + type + '</a>';
                          },
                          onClick: (obj) => {
                            let data = this.tableOne.state ? this.tableOne.state.data : []
                            for (var i = 0; i < data.length; i++) {
                              if (data[i].id === this.table.form.getFieldValue('editLineName')) {
                                Msg.warn('有未保存数据，请保存后重试。')
                                return
                              }
                            }
                            let arr = []
                            let id = obj.rowData.id     //当前选中元素id
                            let datas = []
                            datas = obj.state.data.concat()
                            for (var i = 0; i < datas.length; i++) { arr.push(datas[i].id) }
                            const index = arr.indexOf(id)   //获取选中元素位置
                            if (obj.rowData.isHuizong === '1') {
                              let newData = { ...datas[index] }
                              newData.isHuizong = '2'
                              newData.jzType = '1'
                              newData.orderStr = datas[index].orderStr + 1
                              newData.remarks = ''
                              newData.subDetail2 = ''
                              newData.id = new Date().getTime()
                              datas.splice(index + 1, 0, newData)
                              this.tableOne.btnCallbackFn.setState({
                                data: datas
                              })
                              this.table.form.setFieldsValue({
                                editLineName: newData.id
                              })
                            } else {
                              confirm({
                                content: '确定删除选中的数据吗?',
                                onOk: () => {
                                  let deleteData = []
                                  deleteData.push({ id: obj.rowData.id })
                                  this.props.myFetch('batchDeleteUpdateZxCtContrJzItemBase', deleteData).then(
                                    ({ success, message }) => {
                                      if (success) {
                                        Msg.success(message);
                                        obj.btnCallbackFn.refresh();
                                      } else {
                                        Msg.error(message);
                                      }
                                    }
                                  );
                                }
                              });
                            }
                          }
                        }
                      ]
                    }
                  },
                ]
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;