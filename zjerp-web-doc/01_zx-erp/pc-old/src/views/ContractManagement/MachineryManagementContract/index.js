import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import MachineryManagementContractLeaseForm from './formLease';
import MachineryManagementContractPurchaseForm from './formPurchase';
import MachineryManagementContractDetail from './detail';
import { Toast } from 'antd-mobile';
import Operation from './operation'
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxCtEqContrApplyId',
    size: 'small'
  },
  drawerConfig: {
    width: window.screen.width * 0.7
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
    super(props);
    this.state = {
      lineEdit: '',
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
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxCtEqContrApplyList',
            otherParams: { orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
          }}
          componentsKey={{
            MachineryManagementContractForm: (obj) => {
              this.table.clearSelectedRows();
              if (obj.selectedRows[0].contractCategory === '0') {
                return <MachineryManagementContractLeaseForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              } else {
                return <MachineryManagementContractPurchaseForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              }
            }
          }}
          tabsWillChange={(obj, canChange) => {
            if (this.tableOne) {
              this.tableOne.refresh();
            }
            canChange(true);
          }}

          actionBtns={[
            {
              name: "add",
              label: "??????",
              icon: "plus",
              field: "add",
              type: "primary",
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.setState({
                  lineEdit: ''
                })
              },
              formBtns: [
                {
                  name: "cancel",
                  field: "cancel",
                  type: "dashed",
                  label: "??????",
                  parentField: "add",
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
                  name: 'diySubmit',//??????add del
                  field: 'diySubmit',
                  type: 'primary',//??????  ?????? primary
                  label: '??????',//????????????????????????????????????
                  hide: (obj) => {
                    let index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('addZxCtEqContrApply', obj._formData).then(
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
              label: "??????",
              field: "edit",
              type: "primary",
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.table.clearSelectedRows();
                this.setState({
                  lineEdit: ''
                })
              },
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus === '1' || SelectedRowsData[0].apih5FlowStatus === '2') {
                  return true;
                } else {
                  return false;
                }
              },
              formBtns: [
                {
                  name: 'cancel', //??????????????????
                  type: 'dashed',//??????  ?????? primary
                  label: '??????',
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
                  label: '??????',
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
                    this.props.myFetch('updateZxCtEqContrApply', obj._formData).then(
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
              name: 'Component',
              type: 'primary',
              label: '????????????',
              drawerTitle: '????????????',
              disabled: (obj) => {
                let data = obj.btnCallbackFn.getSelectedRows();
                if (data.length === 1 && data[0].workId) {
                  return false;
                } else {
                  return true;
                }
              },
              Component: (props) => {
                return <Operation apiName={'openFlowByReport'} {...props} />
              }
            },
            {
              name: "del",
              label: "??????",
              icon: "delete",
              field: "del",
              type: "danger",
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
                apiName: "batchDeleteUpdateZxCtEqContrApply"
              }
            },
            {
              Component: "MachineryManagementContractForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "??????",
              icon: "plus",
              label: "??????",
              name: "Component",
              tableField: "projectInfo",
              tableName: "??????????????????",
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus !== '-1') {
                  return true;
                } else {
                  return false;
                }
              }
            },
          ]}
          desc="??????????????????????????????????????????????????????????????????????????????????????????????????????"
          formConfig={[
            {
              table: {
                title: '????????????',
                dataIndex: 'contractNo',
                key: 'contractNo',
                align: "center",
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                onClick: 'detail',
                tooltip: 20
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractName',
                key: 'contractName',
                align: "center",
                width: 160,
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                tooltip: 15
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                align: "center",
                width: 120,
                render: (data) => {
                  if (data === '0') {
                    return '????????????';
                  } else if (data === '1') {
                    return '????????????';
                  }
                }
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'firstName',
                key: 'firstName',
                align: "center",
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 15
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'secondName',
                key: 'secondName',
                align: "center",
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 15
              }
            },
            {
              table: {
                title: '???????????????',
                dataIndex: 'agent',
                key: 'agent',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '??????????????????(??????)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'isDeduct',
                key: 'isDeduct',
                align: "center",
                width: 100,
                render: (h) => {
                  if (h === '1') {
                    return '???'
                  } else {
                    return '???'
                  }
                },
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'csTimeLimit',
                key: 'csTimeLimit',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'pp3',
                key: 'pp3',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '?????????',
                dataIndex: 'createUserName',
                key: 'createUserName',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                align: "center",
                width: 100,
                fixed: 'right',
                render: (data) => {
                  if (data === '0') {
                    return '?????????';
                  } else if (data === '1') {
                    return '?????????';
                  } else if (data === '2') {
                    return '????????????';
                  } else if (data === '3') {
                    return '??????';
                  } else if (data === '-1') {
                    return '?????????';
                  }
                }
              }
            }
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "????????????",
              content: {
                formConfig: [
                  {
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'apih5FlowStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtEqContrApplyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondName',
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
                    field: 'id',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '????????????',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    span: 12,
                    required: true
                  },
                  {
                    label: '????????????',
                    field: "contractName",
                    type: 'string',
                    required: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '???????????????',
                    field: "agent",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "firstName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow:false,
                    editShow:true,
                    detailShow:true,
                  },
                  {
                    label: '????????????',
                    field: "firstID",
                    type: 'select',
                    showSearch: true,
                    addShow:true,
                    editShow:false,
                    detailShow:false,
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        firstName: 'orgName',
                        comID: 'companyId',
                        comName: 'companyName',
                        orgID: 'orgID',
                        orgName: 'orgName',
                        id: 'id'
                      }
                    },
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0' && obj.clickCb.rowInfo.name === 'add') {
                        return {
                          apiName: 'getZxCtContractListByStatus',
                          otherParams: {
                            orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId
                          }
                        }
                      }
                    },
                    required: true,
                    placeholder: '?????????',
                    span: 12,
                    condition: [
                      {
                        regex: { zxCtEqContrApplyId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        if (pageData.contractCategory) {
                          let params = {}
                          params.zxCtContractId = obj.itemData.id
                          params.contractCategory = pageData.contractCategory
                          params.orgID = obj.itemData.orgID
                          this.props.myFetch('generateZxCtEqContrApplyContractNo', params).then(
                            ({ data, success, message }) => {
                              if (success) {
                                let contractNo = '';
                                contractNo = data.contractNo
                                obj.form.setFieldsValue({
                                  contractNo,
                                })
                              } else {
                                Msg.error(message);
                              }
                            }
                          );
                        }
                      } else {
                        obj.form.setFieldsValue({
                          contractNo: ''
                        })
                      }
                    }
                  },
                  {
                    label: '????????????',
                    field: "secondID",
                    type: 'selectByPaging',
                    optionConfig: {
                      label: 'customerName',
                      value: 'zxCrCustomerNewId',
                      linkageFields: {
                        secondName: 'customerName',
                      }
                    },
                    fetchConfig: {
                      apiName: 'getZxCrCustomerNewList',
                      searchKey: 'customerName'
                    },
                    allowClear: false,
                    showSearch: true,
                    required: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????(??????)',
                    field: "contractCost",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '????????????(??????)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '????????????',
                    field: "isDeduct",
                    type: 'radio',
                    optionData: [
                      { label: '???', value: '1' },
                      { label: '???', value: '0' }
                    ],
                    initialValue: '0',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "csTimeLimit",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "contractCategory",
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '????????????', value: '0' },
                      { label: '????????????', value: '1' },
                    ],
                    placeholder: '?????????',
                    span: 12,
                    required: true,
                    condition: [
                      {
                        regex: { zxCtEqContrApplyId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      let pageData = obj.form.getFieldsValue()
                      if (val) {
                        if (pageData.firstID) {
                          let params = {}
                          params.zxCtContractId = pageData.id
                          params.contractCategory = val
                          params.orgID = pageData.orgID
                          this.props.myFetch('generateZxCtEqContrApplyContractNo', params).then(
                            ({ data, success, message }) => {
                              if (success) {
                                let contractNo = '';
                                contractNo = data.contractNo
                                obj.form.setFieldsValue({
                                  contractNo,
                                })
                              } else {
                                Msg.error(message);
                              }
                            }
                          );
                        }

                      } else {
                        obj.form.setFieldsValue({
                          contractNo: ''
                        })
                      }
                    }
                  },
                  {
                    label: '????????????',
                    field: "pp3",
                    type: 'string',
                    initialValue: '???????????????',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '????????????',
                    field: 'content',
                    type: 'textarea',
                    required:true,
                    placeholder: '?????????',
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
                  {
                    label: '??????',
                    field: 'remark',
                    type: 'textarea',
                    placeholder: '?????????',
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
                  {
                    label: '????????????',
                    field: 'zxErpFileList',
                    type: 'files',
                    required: true,
                    fetchConfig: {
                      apiName: 'upload'
                    },
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 24 }
                      }
                    }
                  }
                ]
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "??????",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtEqContrApplyId"))
              },
              hide: (obj) => {
                let contractCategory = obj.form.getFieldValue('contractCategory')
                if (contractCategory !== '0') {
                  return true
                } else {
                  return false
                }
              },
              content: {
                drawerConfig: {
                  width: '1100px'
                },
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContrItemList',
                    otherParams: { zxCtEqContrApplyId: obj.tableFns.form.getFieldValue('zxCtEqContrApplyId') },
                    success: () => {
                      let params = {}
                      params.zxCtEqContrApplyId = obj.tableFns.form.getFieldValue('zxCtEqContrApplyId')
                      this.props.myFetch('getZxCtEqContrApplyDetail', params).then(
                        ({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              contractCost: data.contractCost,
                              contractCostNoTax: data.contractCostNoTax,
                              contractCostTax: data.contractCostTax,
                            })
                          } else {
                            Msg.error(message);
                          }
                        }
                      );
                    }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtEqContrItemId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtEqContrItemId !== this.state.lineEdit
                },
                componentsKey: {
                  MachineryManagementContractDetail: () => {
                    return <MachineryManagementContractDetail {...this.props} />
                  }
                },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'diyaddRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '?????????',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    onClick: () => {
                      console.log(this.table.form.getFieldsValue());
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].zxCtEqContrItemId === this.state.lineEdit) {
                            Msg.warn('??????????????????????????????????????????')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtEqContrItemId = Date.parse(new Date())
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.setState({
                        lineEdit: newRowData.zxCtEqContrItemId
                      })
                    },
                  },
                  {
                    name: 'diydel',
                    icon: 'del',
                    type: 'danger',
                    label: '??????',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    disabled: (obj) => {
                      if (obj.btnCallbackFn.getSelectedRows().length) {
                        return false;
                      } else {
                        return true;
                      }
                    },
                    onClick: (obj) => {
                      confirm({
                        content: '???????????????????????????????',
                        onOk: () => {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtEqContrItemId === this.state.lineEdit) {
                              Msg.warn('??????????????????????????????????????????')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxCtEqContrItem', deleteData).then(
                            ({ success, message }) => {
                              if (success) {
                                Msg.success(message);
                                obj.btnCallbackFn.clearSelectedRows();
                                obj.btnCallbackFn.refresh();
                                this.table.refresh()
                              } else {
                                Msg.error(message);
                              }
                            }
                          );
                        }
                      })
                    }
                  },
                  {
                    name: 'diydel',
                    icon: 'plus',
                    type: 'primary',
                    label: '??????',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    addCb: (obj) => {
                      console.log('??????', obj);
                    },
                  },
                ],

                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtEqContrItemId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'parentID',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'catCode',
                      key: 'catCode',
                      width: 150,
                      disabled: true,
                      fixed: 'left',
                      type: 'string',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: `<div>????????????<span style='color: #ff4d4f'>*</span></div>`,
                      width: 150,
                      required: true,
                      tdEdit: true,
                      fixed: 'left',
                      dataIndex: 'catName',
                      align: 'center',
                      key: 'catName',
                    },
                    form: {
                      type: 'selectByQnnTable',
                      optionConfig: {
                        label: 'catCode',
                        value: 'id',
                      },
                      dropdownMatchSelectWidth: 800,
                      qnnTableConfig: {
                        antd: {
                          rowKey: "id"
                        },
                        fetchConfig: {
                          apiName: "getZxEqResCategoryItemList"
                        },
                        searchBtnsStyle: "inline",
                        formConfig: [
                          {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                              dataIndex: "catCode",
                              title: "??????",
                              width: 100
                            },
                            form: {
                              type: 'string'
                            }
                          },
                          {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                              dataIndex: "catName",
                              title: "??????",
                              width: 100
                            },
                            form: {
                              type: 'string'
                            }
                          }
                        ]
                      },
                      allowClear: false,
                      showSearch: true,
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit && obj?.itemData?.length) {
                              rowData.catCode = obj.itemData && obj.itemData[0].catCode
                              rowData.catName = obj.itemData && obj.itemData[0].catName
                              rowData.spec = obj.itemData && obj.itemData[0].spec
                              rowData.unit = obj.itemData && obj.itemData[0].unit
                              rowData.parentID = obj.itemData && obj.itemData[0].parentID
                              rowData.zxEqResCategoryId = obj.itemData && obj.itemData[0].id
                              rowData.actualStartDate = data.actualStartDate
                              rowData.actualEndDate = data.actualEndDate
                              rowData.remark = data.remark
                            }
                            return rowData;
                          })
                          this.tableOne.btnCallbackFn.setState({
                            data: tableOneData
                          })
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 120,
                      tdEdit: true,
                      type: 'string',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      tdEdit: true,
                      type: 'string',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'actualStartDate',
                      key: 'actualStartDate',
                      format: 'YYYY-MM-DD',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'actualEndDate',
                      format: 'YYYY-MM-DD',
                      key: 'actualEndDate',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'qty',
                      key: 'qty',
                      width: 120,
                      tdEdit: true,
                      type: 'number',
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      type: 'number',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              if (Number(data.qty) !== Number(rowData.qty)) {
                                rowData.qty = val ? Number(val) : 0;
                                rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)
                                rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //?????????????????????
                                rowData.spec = data.spec
                                rowData.unit = data.unit
                                rowData.actualStartDate = data.actualStartDate
                                rowData.actualEndDate = data.actualEndDate
                                rowData.remark = data.remark
                              } else {
                                rowData = null;
                              }
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
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????????????????(???/???)',
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      precision: 6,
                      type: 'number',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              if (Number(data.price) !== Number(rowData.price)) {
                                rowData.price = val ? Number(val) : 0;
                                let priceNoTax = (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //???????????????
                                rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //?????????????????????
                                rowData.spec = data.spec
                                rowData.unit = data.unit
                                rowData.actualStartDate = data.actualStartDate
                                rowData.actualEndDate = data.actualEndDate
                                rowData.remark = data.remark
                              } else {
                                rowData = null;
                              }
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
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      precision: 6,
                      placeholder: '?????????',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              if (Number(data.priceNoTax) !== Number(rowData.priceNoTax)) {
                                rowData.priceNoTax = val ? Number(val) : 0;
                                let price = (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //??????????????????
                                rowData.price = Math.round(price * 100) / 100
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) //?????????????????????
                                rowData.spec = data.spec
                                rowData.unit = data.unit
                                rowData.actualStartDate = data.actualStartDate
                                rowData.actualEndDate = data.actualEndDate
                                rowData.remark = data.remark
                              } else {
                                rowData = null;
                              }
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
                        })
                      },
                    }
                  },

                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'contractSumNoTax',
                      key: 'contractSumNoTax',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                      }
                    },
                  },
                  {
                    table: {
                      title: `<div>??????(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'select',
                      optionConfig: {
                        label: 'itemName',
                        value: 'itemId',
                      },
                      fetchConfig: {
                        apiName: 'getBaseCodeSelect',
                        otherParams: { itemId: 'shuiLv' }
                      },
                      allowClear: false,
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              rowData.taxRate = val;
                              let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100)
                              rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                              rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)
                              rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) / (1 + (Number(val) ? Number(val) : 0) / 100)
                              rowData.spec = data.spec
                              rowData.unit = data.unit
                              rowData.actualStartDate = data.actualStartDate
                              rowData.actualEndDate = data.actualEndDate
                              rowData.remark = data.remark
                            }
                            return rowData;
                          })
                          this.tableOne.btnCallbackFn.setState({
                            data: tableOneData
                          })
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'isDeduct',
                      key: 'isDeduct',
                      width: 100,
                      align: 'center',
                      render: () => {
                        let isDeduct = this.table.btnCallbackFn.form.getFieldValue('isDeduct')
                        if (isDeduct === '1') {
                          return '???'
                        } else {
                          return '???'
                        }
                      },
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'remark',
                      key: 'remark',
                      width: 150,
                      tooltip: 15,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'action',
                      key: 'action',
                      align: "center",
                      showType: "tile",
                      fixed: 'right',
                      width: 120,
                      render: (data, rowData) => {
                        if (rowData.zxCtEqContrItemId !== this.state.lineEdit) {
                          return '<a>??????</a>';
                        } else {
                          return null
                        }

                      },
                      drawerTitle: "??????",
                      onClick: 'Component',
                      Component: MachineryManagementContractDetail
                    },
                  },
                  {
                    isInTable: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'detail') {
                        return false
                      } else {
                        return true
                      }
                    },
                    isInForm: false,
                    table: {
                      showType: 'tile',
                      width: 120,
                      title: "??????",
                      key: "action",//???????????????
                      fixed: 'right',//???????????????
                      align: 'center',
                      render: (data, rowData) => {
                        if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                          return <a>??????</a>;
                        } else {
                          return <a>??????</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxCtEqContrItemId) {
                          if (!obj.rowData.catName) {
                            Msg.warn("????????????????????????")
                            return
                          } else if (!obj.rowData.taxRate) {
                            Msg.warn("??????????????????")
                            return
                          }
                          Toast.loading('loading...');
                          setTimeout(() => {
                            obj.btnCallbackFn.getEditedRowData().then((data) => {
                              let body = {
                                ...obj.rowData,
                                ...data
                              };
                              body.zxCtEqContrApplyId = obj.props.tableFns.qnnForm.form.getFieldValue('zxCtEqContrApplyId')
                              this.props.myFetch(!isNaN(obj.rowData.zxCtEqContrItemId) ? 'addZxCtEqContrItem' : 'updateZxCtEqContrItem', body).then(
                                ({ success, message }) => {
                                  if (success) {
                                    Msg.success(message);
                                    this.setState({
                                      lineEdit: ''
                                    })
                                    obj.btnCallbackFn.refresh();
                                    this.table.refresh()
                                    Toast.hide();
                                  } else {
                                    Toast.hide();
                                    Msg.error(message);
                                  }
                                }
                              );
                            })
                          }, 300)
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtEqContrItemId === this.state.lineEdit) {
                              Msg.warn('??????????????????????????????????????????')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxCtEqContrItemId
                          })
                        }
                      }
                    }
                  }
                ]
              }
            },
            {
              field: "table2",
              name: "qnnTable",
              title: "??????",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtEqContrApplyId"))
              },
              hide: (obj) => {
                let contractCategory = obj.form.getFieldValue('contractCategory')
                if (contractCategory === '0') {
                  return true
                } else {
                  return false
                }
              },
              content: {
                drawerConfig: {
                  width: '1100px'
                },
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContrItemList',
                    otherParams: { zxCtEqContrApplyId: obj.tableFns.form.getFieldValue('zxCtEqContrApplyId') },
                    success: (obj) => {
                      let data = obj.data
                      if (data && data.length > 0) {
                        let params = {}
                        params.zxCtEqContrApplyId = data[0].zxCtEqContrApplyId
                        this.props.myFetch('getZxCtEqContrApplyDetail', params).then(
                          ({ data, success, message }) => {
                            if (success) {
                              this.table.form.setFieldsValue({
                                contractCost: data.contractCost,
                                contractCostNoTax: data.contractCostNoTax,
                                contractCostTax: data.contractCostTax,
                              })
                            } else {
                              Msg.error(message);
                            }
                          }
                        );
                      }
                    }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtEqContrItemId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtEqContrItemId !== this.state.lineEdit
                },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'diyAddRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '?????????',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    onClick: (obj) => {
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].zxCtEqContrItemId === this.state.lineEdit) {
                            Msg.warn('??????????????????????????????????????????')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtEqContrItemId = Date.parse(new Date())
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.setState({
                        lineEdit: newRowData.zxCtEqContrItemId
                      })
                    },
                  },
                  {
                    name: 'diydel',
                    icon: 'del',
                    type: 'danger',
                    label: '??????',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    disabled: (obj) => {
                      if (obj.btnCallbackFn.getSelectedRows().length) {
                        return false;
                      } else {
                        return true;
                      }
                    },
                    onClick: (obj) => {
                      confirm({
                        content: '???????????????????????????????',
                        onOk: () => {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtEqContrItemId === this.state.lineEdit) {
                              Msg.warn('??????????????????????????????????????????')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxCtEqContrItem', deleteData).then(
                            ({ success, message }) => {
                              if (success) {
                                Msg.success(message);
                                obj.btnCallbackFn.clearSelectedRows();
                                obj.btnCallbackFn.refresh();
                                this.table.refresh()
                              } else {
                                Msg.error(message);
                              }
                            }
                          );
                        }
                      })
                    }
                  },
                  {
                    name: 'diydel',
                    icon: 'plus',
                    type: 'primary',
                    label: '??????',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    addCb: (obj) => {
                      console.log('??????', obj);
                    },
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtEqContrItemId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'parentID',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: `<div>??????<span style='color: #ff4d4f'>*</span></div>`,
                      width: 150,
                      required: true,
                      tdEdit: true,
                      fixed: 'left',
                      dataIndex: 'catCode',
                      align: 'center',
                      key: 'catCode',
                    },
                    form: {
                      type: 'selectByQnnTable',
                      optionConfig: {
                        label: 'catCode',
                        value: 'id',
                      },
                      dropdownMatchSelectWidth: 800,
                      qnnTableConfig: {
                        antd: {
                          rowKey: "id"
                        },
                        fetchConfig: {
                          apiName: "getZxEqResCategoryItemList"
                        },
                        searchBtnsStyle: "inline",
                        formConfig: [
                          {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                              dataIndex: "catCode",
                              title: "??????",
                              width: 100
                            },
                            form: {
                              type: 'string'
                            }
                          },
                          {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                              dataIndex: "catName",
                              title: "??????",
                              width: 100
                            },
                            form: {
                              type: 'string'
                            }
                          }
                        ]
                      },
                      allowClear: false,
                      showSearch: true,
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              rowData.catParentName = obj.itemData && obj.itemData[0].parentName
                              rowData.catName = obj.itemData && obj.itemData[0].catName
                              rowData.catCode = obj.itemData && obj.itemData[0].catCode
                              rowData.spec = obj.itemData && obj.itemData[0].spec
                              rowData.unit = obj.itemData && obj.itemData[0].unit
                              rowData.parentID = obj.itemData && obj.itemData[0].parentID
                              rowData.zxEqResCategoryId = obj.itemData && obj.itemData[0].id
                              rowData.remark = data.remark
                            }
                            return rowData;
                          })
                          this.tableOne.btnCallbackFn.setState({
                            data: tableOneData
                          })
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'catParentName',
                      key: 'catParentName',
                      width: 150,
                      tooltip: 15,
                      disabled: true,
                      fixed: 'left',
                      type: 'string',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'catName',
                      key: 'catName',
                      width: 150,
                      tooltip: 15,
                      disabled: true,
                      type: 'string',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 120,
                      tdEdit: true,
                      type: 'string',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      tdEdit: true,
                      type: 'string',
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'qty',
                      key: 'qty',
                      width: 100,
                      tdEdit: true,
                      type: 'number',
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      type: 'number',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              if (Number(data.qty) !== Number(rowData.qty)) {
                                rowData.qty = val ? Number(val) : 0;
                                rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)
                                rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //?????????????????????
                                rowData.spec = data.spec
                                rowData.unit = data.unit
                                rowData.remark = data.remark
                                rowData.catParentName = data.catParentName
                              } else {
                                rowData = null;
                              }
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
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????????????????(???/???)',
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              if (Number(data.price) !== Number(rowData.price)) {
                                rowData.price = val ? Number(val) : 0;
                                let priceNoTax = (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //???????????????
                                rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //?????????????????????
                                rowData.spec = data.spec
                                rowData.unit = data.unit
                                rowData.catParentName = data.catParentName
                                rowData.remark = data.remark
                              } else {
                                rowData = null;
                              }
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
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '?????????',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              if (Number(data.priceNoTax) !== Number(rowData.priceNoTax)) {
                                rowData.priceNoTax = val ? Number(val) : 0;
                                let price = (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //??????????????????
                                rowData.price = Math.round(price * 100) / 100
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) //?????????????????????
                                rowData.spec = data.spec
                                rowData.unit = data.unit
                                rowData.catParentName = data.catParentName
                                rowData.remark = data.remark
                              } else {
                                rowData = null;
                              }
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
                        })
                      },
                    }
                  },

                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'contractSumNoTax',
                      key: 'contractSumNoTax',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                      }
                    },
                  },
                  {
                    table: {
                      title: `<div>??????(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 120,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'select',
                      optionConfig: {
                        label: 'itemName',
                        value: 'itemId',
                      },
                      fetchConfig: {
                        apiName: 'getBaseCodeSelect',
                        otherParams: { itemId: 'shuiLv' }
                      },
                      allowClear: false,
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                              rowData.taxRate = val;
                              let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100)
                              rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                              rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)
                              rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) / (1 + (Number(val) ? Number(val) : 0) / 100)
                              rowData.spec = data.spec
                              rowData.unit = data.unit
                              rowData.catParentName = data.catParentName
                              rowData.remark = data.remark
                            }
                            return rowData;
                          })
                          this.tableOne.btnCallbackFn.setState({
                            data: tableOneData
                          })
                        })
                      },
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'isDeduct',
                      key: 'isDeduct',
                      width: 100,
                      align: 'center',
                      render: () => {
                        let isDeduct = this.table.btnCallbackFn.form.getFieldValue('isDeduct')
                        if (isDeduct === '1') {
                          return '???'
                        } else {
                          return '???'
                        }
                      },
                    },
                    form: {
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'remark',
                      key: 'remark',
                      width: 150,
                      tdEdit: true,
                      tooltip: 15,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '?????????'
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
                    isInForm: false,
                    table: {
                      showType: 'tile',
                      width: 120,
                      title: "??????",
                      key: "action",//???????????????
                      fixed: 'right',//???????????????
                      align: 'center',
                      render: (data, rowData) => {
                        if (rowData.zxCtEqContrItemId === this.state.lineEdit) {
                          return <a>??????</a>;
                        } else {
                          return <a>??????</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxCtEqContrItemId) {
                          if (!obj.rowData.catCode) {
                            Msg.warn("??????????????????")
                            return
                          } else if (!obj.rowData.taxRate) {
                            Msg.warn("??????????????????")
                            return
                          }
                          Toast.loading('loading...');
                          setTimeout(() => {
                            obj.btnCallbackFn.getEditedRowData().then((data) => {
                              let body = {
                                ...obj.rowData,
                                ...data
                              };
                              body.zxCtEqContrApplyId = obj.props.tableFns.qnnForm.form.getFieldValue('zxCtEqContrApplyId')
                              this.props.myFetch(!isNaN(obj.rowData.zxCtEqContrItemId) ? 'addZxCtEqContrItem' : 'updateZxCtEqContrItem', body).then(
                                ({ success, message }) => {
                                  if (success) {
                                    Msg.success(message);
                                    this.setState({
                                      lineEdit: ''
                                    })
                                    obj.btnCallbackFn.refresh();
                                    this.table.refresh()
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
                            if (data[i].zxCtEqContrItemId === this.state.lineEdit) {
                              Msg.warn('??????????????????????????????????????????')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxCtEqContrItemId
                          })
                        }
                      }
                    }
                  }
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