import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import { Toast } from 'antd-mobile';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxCmGlobalCodeId',
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
    const { categoryID } = this.props
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
            apiName: 'getZxCmGlobalCodeList',
            otherParams: { categoryID }
          }}
          rowDisabledCondition={(rowData) => {
            return rowData.zxCmGlobalCodeId !== this.state.lineEdit
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
                    if (tableOneData[i].zxCmGlobalCodeId === this.state.lineEdit) {
                      Msg.warn('有未保存数据，请保存后重试。')
                      return
                    }
                  }
                }
                let newRowData = {}
                newRowData.zxCmGlobalCodeId = Date.parse(new Date())
                tableOneData.push(newRowData)
                this.tableOne.btnCallbackFn.setState({
                  data: tableOneData
                })
                this.setState({
                  lineEdit: newRowData.zxCmGlobalCodeId
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
                      if (data[i].zxCmGlobalCodeId === this.state.lineEdit) {
                        Msg.warn('有未保存数据，请保存后重试。')
                        return
                      }
                    }
                    let deleteData = obj.selectedRows
                    this.props.myFetch('batchDeleteUpdateZxCmGlobalCode', deleteData).then(
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
                field: 'zxCmGlobalCodeId',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '代码编号',
                dataIndex: 'globalID',
                key: 'globalID',
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
                title: '代码描述',
                width: 180,
                tooltip: 18,
                tdEdit: true,
                dataIndex: 'globalDesc',
                key: 'globalDesc',
                align: 'center',
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              isInTable: () => {
                if (categoryID !== 'categorysafe001009') {
                  return false
                } else {
                  return true
                }
              },
              table: {
                title: '比率%',
                dataIndex: 'percentage',
                key: 'percentage',
                width: 100,
                tdEdit: true,
                align: 'center',
              },
              form: {
                type: 'number',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '是否启用',
                dataIndex: 'enable',
                key: 'enable',
                width: 100,
                tdEdit: true,
                align: 'center',
                render: (h) => {
                  if (h === '1') {
                    return '是'
                  } else {
                    return '否'
                  }
                }
              },
              form: {
                type: 'select',
                placeholder: '请选择',
                optionData: [{ label: '是', value: '1' }, { label: '否', value: '0' }],
                optionConfig: { label: 'label', value: 'value' },
                initialValue: '0'
              }
            },
            {
              table: {
                title: '是否默认选项',
                dataIndex: 'selected',
                key: 'selected',
                width: 100,
                tdEdit: true,
                align: 'center',
                render: (h) => {
                  if (h === '1') {
                    return '是'
                  } else {
                    return '否'
                  }
                }
              },
              form: {
                type: 'select',
                placeholder: '请选择',
                optionData: [{ label: '是', value: '1' }, { label: '否', value: '0' }],
                optionConfig: { label: 'label', value: 'value' },
                initialValue: '0'
              }
            },
            {
              isInTable: () => {
                if (categoryID === 'categorysafe001009') {
                  return false
                } else {
                  return true
                }
              },
              table: {
                title: '地区',
                dataIndex: 'region',
                key: 'region',
                width: 120,
                align: 'center',
                tdEdit: true
              },
              form: {
                type: 'string',
                placeholder: '请输入',
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 150,
                tdEdit: true,
                align: 'center',
              },
              form: {
                type: 'string',
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
                  if (rowData.zxCmGlobalCodeId === this.state.lineEdit) {
                    return <a>保存</a>;
                  } else {
                    return <a>编辑</a>;
                  }
                },
                onClick: (obj) => {
                  if (this.state.lineEdit === obj.rowData.zxCmGlobalCodeId) {
                    Toast.loading('loading...');
                    setTimeout(() => {
                      obj.btnCallbackFn.getEditedRowData().then((data) => {
                        if (data.percentage && (data.percentage > 100 || data.percentage < 0)) {
                          Toast.hide()
                          Msg.warn('请输入正确的比率！')
                          return
                        }
                        let body = {
                          ...obj.rowData,
                          ...data,
                        };
                        body.categoryID = categoryID
                        this.props.myFetch(!isNaN(obj.rowData.zxCmGlobalCodeId) ? 'addZxCmGlobalCode' : 'updateZxCmGlobalCode', body).then(
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
                      if (data[i].zxCmGlobalCodeId === this.state.lineEdit) {
                        Msg.warn('有未保存数据，请保存后重试。')
                        return
                      }
                    }
                    this.setState({
                      lineEdit: obj.rowData.zxCmGlobalCodeId
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