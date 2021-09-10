import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import TabTwoList from './TabTwo';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxSfExamId',
    size: 'small'
  },
  drawerConfig: {
    width: '1200px'
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
  },
  firstRowIsSearch: false,
  isShowRowSelect: true
};
class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
    }
  }
  render() {
    const { departmentId, departmentName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany
    const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo
    console.log(ext1);
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
            apiName: 'getZxSfExamList',
            otherParams: {
              orgID: departmentId,
            }
          }}
          actionBtns={[
            {
              name: 'add',
              icon: 'plus',
              type: 'primary',
              label: '新增',
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.setState({
                  lineEdit: ''
                })
              },
              hide: () => {
                if (ext1 === '1') return true
              },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                },
                {
                  name: 'diySubmit',//内置add del
                  field: 'diySubmit',
                  label: '保存',//提交数据并且关闭右边抽屉 
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('addZxSfExam', obj._formData).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                          obj.btnCallbackFn.form.setFieldsValue(data);
                          obj.btnCallbackFn.setActiveKey('1');
                        } else {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.error(message);
                        }
                      }
                    );
                  }
                }
              ]
            },
            {
              name: "edit",
              label: "修改",
              field: "edit",
              type: "primary",
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.table.clearSelectedRows();
                this.setState({
                  lineEdit: ''
                })
              },
              // disabled: (obj) => {
              //   let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
              //   if (
              //     SelectedRowsData.length === 1 && (
              //       (ext1 === '1' && SelectedRowsData[0].status === '2') ||
              //       (ext1 === '2' && SelectedRowsData[0].status !== '2') ||
              //       ((ext1 === '3' || ext1 === '4') && SelectedRowsData[0].status === '0')
              //     )) {
              //     return false;
              //   }
              //   return true;
              // },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                },
                {
                  name: 'diySubmit',
                  field: 'diySubmit',
                  type: 'primary',
                  label: '保存',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.clearSelectedRows();
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('updateZxSfExam', obj._formData).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                          obj.btnCallbackFn.setActiveKey('1');
                          if (this.tableOne) {
                            this.tableOne.refresh()
                          }
                        } else {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.error(message);
                        }
                      }
                    );
                  }
                }
              ]
            },
            {
              name: "diyUpdata",
              label: "上报",
              field: "diyUpdata",
              icon: 'plus',
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].status !== '0') {
                  return true;
                } else {
                  return false;
                }
              },
              hide: () => { if (ext1 === '1') return true },
              onClick: (obj) => {
                confirm({
                  content: '确定上报吗?',
                  onOk: () => {
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    let params = {}
                    params.zxSfExamId = obj.selectedRows[0].zxSfExamId
                    this.table.clearSelectedRows();
                    this.props.myFetch(ext1 === '2' ? 'updateZxSfExamStatusju' : 'updateZxSfExamStatus', params).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                        } else {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.error(message);
                        }
                      }
                    );
                  }
                })
              },
            },
            {
              name: 'del',//内置add del
              icon: 'delete',//icon
              type: 'danger',//类型  默认 primary  [primary dashed danger]
              label: '删除',
              field: "del",
              fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZxSfExam',
              },
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].status === '1') {
                  return true;
                } else {
                  return false;
                }
              },
            },
          ]}
          formConfig={[
            {
              table: {
                title: '单位名称',
                dataIndex: 'examName',
                key: 'examName',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 150,
                onClick: 'detail',
                tooltip: 15,
              }
            },
            {
              table: {
                title: '被考核单位领导',
                dataIndex: 'unitManage',
                key: 'unitManage',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 150,
                tooltip: 15,
              }
            },
            {
              table: {
                title: '填报人',
                dataIndex: 'creator',
                key: 'creator',
                width: 100
              }
            },
            {
              table: {
                title: '状态',
                dataIndex: 'status',
                key: 'status',
                width: 100,
                render: (h) => {
                  if (h === '0') {
                    return '未上报'
                  } else if (h === '1') {
                    return '已上报公司'
                  } else if (h === '2') {
                    return '已上报局'
                  }
                }
              }
            },
            {
              table: {
                title: '考核日期',
                dataIndex: 'examDate',
                key: 'examDate',
                format: 'YYYY-MM-DD',
                filter: true,
                fieldsConfig: {
                  type: 'rangeDate',
                  field: 'examDateList'
                },
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
                    field: 'zxSfExamId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'status',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'examID',
                    type: 'string',
                    hide: true,
                    initialValue: departmentId
                  },
                  {
                    label: '单位名称',
                    field: "examName",
                    type: 'string',
                    span: 12,
                    initialValue: departmentName,
                    disabled:true
                  },
                  {
                    label: '填报人',
                    field: "creator",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                  },
                  {
                    label: '被考核单位领导',
                    field: "unitManage",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                  },
                  {
                    label: '考核日期',
                    field: "examDate",
                    type: 'date',
                    required: true,
                    placeholder: '请选择',
                    span: 12,
                    initialValue: new Date()
                  },
                  {
                    label: '备注',
                    field: 'remarks',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                    autoSize: {
                      minRows: 1,
                      maxRows: 3
                    },
                  },
                  {
                    label: '附件',
                    spanForm: 12,
                    field: 'fileList',
                    type: 'files',
                    fetchConfig: {
                      apiName: 'upload'
                    },
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 12 },
                        sm: { span: 12 }
                      }
                    },
                  }
                ]
              }
            },
            {
              name: "diyComponent",
              field: "TabTwoList",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSfExamId"))
              },
              title: "考核项目",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                let paramsData = {}
                paramsData.clickName = obj.clickCb.rowInfo.name
                paramsData.zxSfExamId = rowData.zxSfExamId ? rowData.zxSfExamId : ''
                paramsData.status = rowData.status ? rowData.status : ''
                paramsData.tabIndex = obj.tableFns.getActiveKey()
                params.paramsData = paramsData
                return <TabTwoList {...params} />;
              }
            },
          ]}
        />
      </div>
    );
  }
}

export default index;