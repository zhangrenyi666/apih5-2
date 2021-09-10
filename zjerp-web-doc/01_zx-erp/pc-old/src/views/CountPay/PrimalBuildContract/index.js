import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import TabTwoList from './TabTwo';
import PrimalBuildForm from './form';
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
  isShowRowSelect: true,
  formItemLayout: {
    labelCol: {
      xs: { span: 5 },
      sm: { span: 5 }
    },
    wrapperCol: {
      xs: { span: 18 },
      sm: { span: 18 }
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
            apiName: 'getZxCtContrCsjzList',
            otherParams: { orgID: departmentId }
          }}
          componentsKey={{
            PrimalBuildForm: (obj) => {
              this.table.clearSelectedRows();
              return <PrimalBuildForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
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
                    const { myFetch } = this.props;
                    val.btnCallbackFn.setBtnsLoading('add', 'addsubmit');
                    myFetch('addZxCtContrCsjz', val._formData).then(
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
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus === '1' || SelectedRowsData[0].apih5FlowStatus === '2') {
                  return true;
                } else {
                  return false;
                }
              },
              label: '修改',
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
                    myFetch('updateZxCtContrCsjz', val._formData).then(
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
              name: 'del',
              icon: 'delete',
              type: 'danger',
              label: '删除',
              field: "del",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length > 0) {
                  for (var i = 0; i < SelectedRowsData.length; i++) {
                    if (SelectedRowsData[i].apih5FlowStatus === '1' || SelectedRowsData[i].apih5FlowStatus === '2') {
                      return true
                    }
                  }
                }
              },
              fetchConfig: {
                apiName: 'batchDeleteUpdateZxCtContrCsjz',
              },
            },
            {
              Component: "PrimalBuildForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "初始建造合同",
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus !== '-1') {
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
                title: '项目名称',
                dataIndex: 'orgName',
                key: 'orgName',
                fixed: 'left',
                width: 200,
                onClick: 'detail',
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 20,
              },
            },
            {
              table: {
                title: '建立时间',
                dataIndex: 'buildTime',
                key: 'buildTime',
                format: 'YYYY-MM-DD',
                width: 120
              },
            },
            {
              table: {
                title: '发起人',
                dataIndex: 'reporter',
                key: 'reporter',
                width: 100,
              },
            },
            {
              table: {
                title: '初始预计总收入',
                dataIndex: 'csBudgetAmt',
                key: 'csBudgetAmt',
                width: 120,
              },
            },
            {
              table: {
                title: '标后预算费用总额',
                dataIndex: 'bhBudgetAmt',
                key: 'bhBudgetAmt',
                width: 120,
              },
            },
            {
              table: {
                title: '标后预算预计总成本',
                dataIndex: 'bhBudgetCost',
                key: 'bhBudgetCost',
                width: 120,
              },
            },
            {
              table: {
                title: '施工预算预计总成本',
                dataIndex: 'sgBudgetCost',
                key: 'sgBudgetCost',
                width: 120,
              },
            },
            {
              table: {
                title: '评审状态',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                width: 90,
                fixed: 'right',
                render: (data) => {
                  if (data === '0') {
                    return '待提交';
                  } else if (data === '1') {
                    return '审核中';
                  } else if (data === '2') {
                    return '审核完成';
                  } else if (data === '3') {
                    return '退回';
                  } else if (data === '-1') {
                    return '未审核'
                  }
                }
              },
            }
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "基础信息",
              content: {
                formConfig: [
                  {
                    field: 'id',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: "editLineName",
                    type: 'string',
                    hide: true,
                    initialValue: ''
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
                      apiName: 'getZxCtContrJzBaseList',
                      otherParams: {
                        type: '1',
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
                    editDisabled: true,
                    placeholder: '请选择',
                    span: 12,
                  },
                  {
                    label: '建立时间',
                    field: 'buildTime',
                    type: 'date',
                    initialValue: new Date(),
                    placeholder: '请选择',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '发起人',
                    field: 'reporter',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                    required: true,
                    initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                  },
                  {
                    label: '初始预计总收入',
                    field: 'csBudgetAmt',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    label: '标后预算费用总额',
                    field: 'bhBudgetAmt',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    label: '标后预算预计总成本',
                    field: 'bhBudgetCost',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    label: '施工预算预计总成本',
                    field: 'sgBudgetCost',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    field: 'remarks',
                    label: '说明',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                  },
                ]
              }
            },
            {
              name: "diyComponent",
              field: "TabTwoList",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("id"))
              },
              title: "清单结算",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                params.clickName = obj.clickCb.rowInfo.name
                params.tabIndex = obj.tableFns.getActiveKey()
                params.id = rowData?.id //主键
                params.orgID = rowData?.orgID
                return <TabTwoList {...params} btnCallbackFn={obj.btnCallbackFn}/>;
              },
            },
          ]}
        />
      </div>
    );
  }
}

export default index;