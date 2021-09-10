import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxSfAccessRoomId',
    size: 'small'
  },
  isShowRowSelect: true,
  drawerConfig: {
    width: '1100px'
  },
  paginationConfig: {
    position: 'bottom'
  },
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
  constructor(props) {
    super(props);
    this.state = {

    }
  }
  render() {
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          wrappedComponentRef={(me) => {
            this.tableOne = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxSfAccessRoomList',
            otherParams: { categoryID: 'categorysafe001003' }
          }}
          rowDisabledCondition={(rowData) => {
            return rowData.zxSfAccessRoomId !== this.state.lineEdit
          }}
          actionBtns={[
            {
              name: 'addRow',
              icon: 'plus',
              type: 'primary',
              label: '新增行',
              addCb: (obj) => {
                let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                if (tableOneData && tableOneData.length > 0) {
                  for (var i = 0; i < tableOneData.length; i++) {
                    if (tableOneData[i].zxSfAccessRoomId === this.state.lineEdit) {
                      Msg.warn('有未保存数据，请保存后重试。')
                      return
                    }
                  }
                }
                let newRowData = {}
                newRowData.zxSfAccessRoomId = Date.parse(new Date())
                tableOneData.push(newRowData)
                this.tableOne.btnCallbackFn.setState({
                  data: tableOneData
                })
                this.setState({
                  lineEdit: newRowData.zxSfAccessRoomId
                })
              },
            },
            {
              name: 'diydel',
              icon: 'del',
              type: 'danger',
              label: '删除',
              disabled: (obj) => {
                if (obj.btnCallbackFn.getSelectedRows().length) {
                  return false;
                } else {
                  return true;
                }
              },
              onClick: (obj) => {
                confirm({
                  content: '确定删除选中的数据吗?',
                  onOk: () => {
                    let data = this.tableOne.state ? this.tableOne.state.data : []
                    for (var i = 0; i < data.length; i++) {
                      if (data[i].zxSfAccessRoomId === this.state.lineEdit) {
                        Msg.warn('有未保存数据，请保存后重试。')
                        return
                      }
                    }
                    let deleteData = obj.selectedRows
                    this.props.myFetch('batchDeleteUpdateZxSfAccessRoom', deleteData).then(
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
                })
              }
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxSfAccessRoomId',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '考核项目',
                dataIndex: 'examName',
                key: 'examName',
                width: 200,
                tooltip: 20,
                align: 'center',
                tdEdit: true,
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '考核内容',
                width: 180,
                tooltip: 18,
                tdEdit: true,
                dataIndex: 'examContext',
                key: 'examContext',
                align: 'center',
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '满分',
                dataIndex: 'allScore',
                key: 'allScore',
                width: 120,
                align: 'center',
                tdEdit: true
              },
              form: {
                type: 'number',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '排序',
                dataIndex: 'orderNo',
                key: 'orderNo',
                width: 150,
                tdEdit: true,
                align: 'center',
              },
              form: {
                type: 'number',
                placeholder: '请输入',
              }
            },
            {
              isInForm: false,
              table: {
                showType: 'tile',
                width: 120,
                title: "操作",
                key: "action",//操作列名称
                fixed: 'right',//固定到右边
                align: 'center',
                render: (data, rowData) => {
                  if (rowData.zxSfAccessRoomId === this.state.lineEdit) {
                    return <a>保存</a>;
                  } else {
                    return <a>编辑</a>;
                  }
                },
                onClick: (obj) => {
                  if (this.state.lineEdit === obj.rowData.zxSfAccessRoomId) {
                    Toast.loading('loading...');
                    setTimeout(() => {
                      obj.btnCallbackFn.getEditedRowData().then((data) => {
                        if (data.allScore > 100 || data.allScore < 0) {
                          Toast.hide()
                          Msg.warn('请输入正确的满分！')
                          return
                        }
                        if (data.orderNo < 0) {
                          Toast.hide()
                          Msg.warn('请输入正确的排序！')
                          return
                        }
                        let body = {
                          ...obj.rowData,
                          ...data,
                        };
                        body.categoryID = 'categorysafe001003'
                        this.props.myFetch(!isNaN(obj.rowData.zxSfAccessRoomId) ? 'addZxSfAccessRoom' : 'updateZxSfAccessRoom', body).then(
                          ({ success, message }) => {
                            if (success) {
                              Msg.success(message);
                              this.setState({
                                lineEdit: ''
                              })
                              obj.btnCallbackFn.refresh();
                              Toast.hide()
                            } else {
                              Toast.hide()
                              Msg.error(message);
                            }
                          }
                        );
                      })
                    }, 300)
                  } else {
                    let data = this.tableOne.state ? this.tableOne.state.data : []
                    for (var i = 0; i < data.length; i++) {
                      if (data[i].zxSfAccessRoomId === this.state.lineEdit) {
                        Msg.warn('有未保存数据，请保存后重试。')
                        return
                      }
                    }
                    this.setState({
                      lineEdit: obj.rowData.zxSfAccessRoomId
                    })
                  }
                }
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;