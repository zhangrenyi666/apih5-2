import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import OtherContractForm from './form';
import { Toast } from 'antd-mobile';
import Operation from './operation';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxCtOtherId',
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
  }
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
            apiName: 'getZxCtOtherList',
            otherParams: { orgId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
          }}
          componentsKey={{
            OtherContractForm: (obj) => {
              this.table.clearSelectedRows();
              return <OtherContractForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
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
              label: "新增",
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
                  label: "取消",
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
                    let index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('addZxCtOther', obj._formData).then(
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
                    this.props.myFetch('updateZxCtOther', obj._formData).then(
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
              label: '进度查询',
              drawerTitle: '进度查询',
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
              label: "删除",
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
                apiName: 'batchDeleteUpdateZxCtOther',
              }
            },
            {
              Component: "OtherContractForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "其他合同管理",
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
                title: '合同编号',
                dataIndex: 'contractNo',
                key: 'contractNo',
                align: "center",
                width: 150,
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                onClick: 'detail',
                tooltip: 15
              }
            },
            {
              table: {
                title: '合同名称',
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
                title: '合同类型',
                dataIndex: 'contractType',
                key: 'contractType',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '甲方名称',
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
                title: '乙方名称',
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
                title: '协作单位类型',
                dataIndex: 'secondOrgType',
                key: 'secondOrgType',
                width: 150,
                render: (data) => {
                  if (data === 'ots') {
                    return '其他协作单位';
                  }
                }
              }
            },
            {
              table: {
                title: '合同签订人',
                dataIndex: 'agent',
                key: 'agent',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '含税合同金额(万元)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '是否抵扣',
                dataIndex: 'isDeduct',
                key: 'isDeduct',
                align: "center",
                width: 100,
                render: (h) => {
                  if (h === '1') {
                    return '是'
                  } else {
                    return '否'
                  }
                },
              }
            },
            {
              table: {
                title: '合同工期',
                dataIndex: 'csTimeLimit',
                key: 'csTimeLimit',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '合同类别',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                align: "center",
                width: 120,
                render: (h) => {
                  if (h === 'FW') {
                    return '房屋租赁'
                  } else if (h === 'JG') {
                    return '加工承揽'
                  } else if (h === 'JS') {
                    return '技术咨询或服务'
                  } else if (h === 'QT') {
                    return '其他合同'
                  } else if (h === 'XZ') {
                    return '行政办公用品采购'
                  } else if (h === 'ZC') {
                    return '征地拆迁'
                  }
                }
              }
            },
            {
              table: {
                title: '发起人',
                dataIndex: 'beginPer',
                key: 'beginPer',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '评审状态',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                width: 100,
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
                    return '未审核';
                  }
                }
              }
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
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtOtherId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'apih5FlowStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondOrgType',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'contractCode',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyName',
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
                    label: '合同编号',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '合同名称',
                    field: "contractName",
                    type: 'string',
                    required: true,
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同类型',
                    field: "contractType",
                    initialValue: '其他类',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                    disabled: true,
                  },
                  {
                    label: '甲方名称',
                    field: "firstName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '甲方名称',
                    field: "firstId",
                    type: 'select',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        firstName: 'orgName',
                        companyId: 'companyId',
                        companyName: 'companyName',
                        orgID: 'orgID',
                        orgName: 'orgName',
                        contractCode: 'contractNo',
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
                    placeholder: '请选择',
                    span: 12,
                    condition: [
                      {
                        regex: { zxCtOtherId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      console.log(obj);
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        if (pageData.contractCategory) {
                          let params = {}
                          params.firstId = obj.itemData.contractNo
                          params.contractCategory = pageData.contractCategory
                          this.props.myFetch('getZxCtOtherContractNo', params).then(
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
                    label: '乙方名称',
                    field: "secondId",
                    type: 'selectByPaging',
                    optionConfig: {
                      label: 'customerName',
                      value: 'zxCrCustomerExtAttrId',
                      linkageFields: {
                        secondOrgType: 'type',
                        secondName: 'customerName'
                      }
                    },
                    fetchConfig: {
                      apiName: 'getZxCrCustomerExtAttrList',
                      otherParams: { type: 'ots' },
                      searchKey: 'customerName'
                    },
                    allowClear: false,
                    showSearch: true,
                    required: true,
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '合同签订人',
                    field: "agent",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '含税合同金额(万元)',
                    field: "contractCost",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '不含税合同金额(万元)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '合同税额(万元)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '是否抵扣',
                    field: "isDeduct",
                    type: 'radio',
                    optionData: [
                      { label: '是', value: '1' },
                      { label: '否', value: '0' }
                    ],
                    initialValue: '0',
                    span: 12
                  },
                  {
                    label: '合同工期',
                    field: "csTimeLimit",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同类别',
                    field: "contractCategory",
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '房屋租赁', value: 'FW' },
                      { label: '加工承揽', value: 'JG' },
                      { label: '技术咨询或服务', value: 'JS' },
                      { label: '其他合同', value: 'QT' },
                      { label: '行政办公用品采购', value: 'XZ' },
                      { label: '征地拆迁', value: 'ZC' },
                    ],
                    placeholder: '请选择',
                    span: 12,
                    required: true,
                    condition: [
                      {
                        regex: { zxCtOtherId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      let pageData = obj.form.getFieldsValue()
                      console.log(pageData);
                      if (val) {
                        if (pageData.firstId) {
                          let params = {}
                          params.firstId = pageData.contractCode
                          params.contractCategory = val
                          this.props.myFetch('getZxCtOtherContractNo', params).then(
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
                    label: '合同内容',
                    field: 'content',
                    type: 'textarea',
                    placeholder: '请输入',
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
                    label: '备注',
                    field: 'remark',
                    type: 'textarea',
                    placeholder: '请输入',
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
                    label: '上传附件',
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
              title: "清单",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtOtherId"))
              },
              content: {
                drawerConfig: {
                  width: '1000px'
                },
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherWorksList',
                    otherParams: { zxCtOtherId: obj.tableFns.form.getFieldValue('zxCtOtherId') },
                    success: (obj) => {
                      let data = obj.data
                      if (data && data.length > 0) {
                        let params = {}
                        params.zxCtOtherId = data[0].zxCtOtherId
                        this.props.myFetch('getZxCtOtherDetail', params).then(
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
                  rowKey: "zxCtOtherWorksId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtOtherWorksId !== this.state.lineEdit
                },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'diyaddRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
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
                          if (tableOneData[i].zxCtOtherWorksId === this.state.lineEdit) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtOtherWorksId = Date.parse(new Date())
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.setState({
                        lineEdit: newRowData.zxCtOtherWorksId
                      })
                    },
                  },
                  {
                    name: 'diydel',
                    icon: 'del',
                    type: 'danger',
                    label: '删除',
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
                        content: '确定删除选中的数据吗?',
                        onOk: () => {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtOtherWorksId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxCtOtherWorks', deleteData).then(
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
                    label: '导入',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    addCb: (obj) => {
                      console.log('导入', obj);
                    },
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtOtherWorksId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: `<div>清单编号<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 100,
                      type: 'string',
                      align: 'center',
                      tdEdit: true,
                    },
                  },
                  {
                    table: {
                      title: `<div>清单名称<span style='color: #ff4d4f'>*</span></div>`,
                      width: 150,
                      type: 'string',
                      tdEdit: true,
                      dataIndex: 'workName',
                      key: 'workName',
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '计量单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      tdEdit: true,
                      type: 'string',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '合同数量',
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
                            if (rowData.zxCtOtherWorksId === this.state.lineEdit) {
                              if (Number(data.qty) !== Number(rowData.qty)) {
                                rowData.qty = val ? Number(val) : 0;
                                rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)
                                rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税合同金额
                                rowData.unit = data.unit
                                rowData.workName = data.workName
                                rowData.workNo = data.workNo
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
                      placeholder: '请输入'
                    }
                  },
                  {
                    table: {
                      title: '含税合同单价',
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtOtherWorksId === this.state.lineEdit) {
                              if (Number(data.price) !== Number(rowData.price)) {
                                rowData.price = val ? Number(val) : 0;
                                let priceNoTax = (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税单价
                                rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)//含税合同金额
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.priceNoTax ? rowData.priceNoTax : 0) //不含税合同金额
                                rowData.unit = data.unit
                                rowData.workName = data.workName
                                rowData.workNo = data.workNo
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
                      title: '不含税合同单价(元)',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtOtherWorksId === this.state.lineEdit) {
                              if (Number(data.priceNoTax) !== Number(rowData.priceNoTax)) {
                                rowData.priceNoTax = val ? Number(val) : 0;
                                let price = (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //含税合同单价
                                rowData.price = Math.round(price * 100) / 100
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) //含税合同金额
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) //不含税合同金额
                                rowData.unit = data.unit
                                rowData.workName = data.workName
                                rowData.workNo = data.workNo
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
                      title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100,
                      tdEdit: true,
                      align: 'center',
                      initialValue: 0
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
                            if (rowData.zxCtOtherWorksId === this.state.lineEdit) {
                              rowData.taxRate = val;
                              let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100) //不含税单价
                              rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
                              rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.priceNoTax ? rowData.priceNoTax : 0)
                              rowData.unit = data.unit
                              rowData.workName = data.workName
                              rowData.remark = data.remark
                              rowData.workNo = data.workNo
                            }
                            return rowData;
                          })
                          this.tableOne.btnCallbackFn.setState({
                            data: tableOneData
                          })
                        })
                      },
                      placeholder: '请选择'
                    }
                  },
                  {
                    table: {
                      title: '是否抵扣',
                      dataIndex: 'isDeduct',
                      key: 'isDeduct',
                      width: 100,
                      align: 'center',
                      tdEdit: false,
                      render: () => {
                        let isDeduct = this.table.btnCallbackFn.form.getFieldValue('isDeduct')
                        if (isDeduct === '1') {
                          return '是';
                        } else {
                          return '否';
                        }
                      }
                    }
                  },
                  {
                    table: {
                      title: '含税合同金额',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 120,
                      tdEdit: false,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                      }
                    }
                  },

                  {
                    table: {
                      title: '不含税合同金额',
                      dataIndex: 'contractSumNoTax',
                      key: 'contractSumNoTax',
                      width: 120,
                      tdEdit: false,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                      }
                    },
                  },
                  // {
                  //   table: {
                  //     title: '变更后数量',
                  //     dataIndex: 'changeQty',
                  //     key: 'changeQty',
                  //     width: 120,
                  //     tdEdit: false,
                  //     align: 'center',
                  //   }
                  // },
                  // {
                  //   table: {
                  //     title: '变更后含税金额',
                  //     dataIndex: 'changeContractSum',
                  //     key: 'changeContractSum',
                  //     width: 120,
                  //     tdEdit: false,
                  //     align: 'center',

                  //   }
                  // },
                  // {
                  //   table: {
                  //     title: '变更后不含税金额',
                  //     dataIndex: 'changeContractSumNoTax',
                  //     key: 'changeContractSumNoTax',
                  //     width: 150,
                  //     tdEdit: false,
                  //     align: 'center',
                  //   }
                  // },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remark',
                      key: 'remark',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入'
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
                      title: "操作",
                      key: "action",//操作列名称
                      fixed: 'right',//固定到右边
                      align: 'center',
                      render: (data, rowData) => {
                        if (rowData.zxCtOtherWorksId === this.state.lineEdit) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxCtOtherWorksId) {
                          Toast.loading('loading...');
                          setTimeout(() => {
                            obj.btnCallbackFn.getEditedRowData().then((data) => {
                              if (!data.workName) {
                                Msg.warn('请填写清单名称！')
                                return
                              } else if (!data.taxRate) {
                                Msg.warn('请填写税率！')
                                return
                              }
                              let body = {
                                ...obj.rowData,
                                ...data
                              };
                              body.zxCtOtherId = obj.props.tableFns.qnnForm.form.getFieldValue('zxCtOtherId')
                              this.props.myFetch(!isNaN(obj.rowData.zxCtOtherWorksId) ? 'addZxCtOtherWorks' : 'updateZxCtOtherWorks', body).then(
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
                            if (data[i].zxCtOtherWorksId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxCtOtherWorksId
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