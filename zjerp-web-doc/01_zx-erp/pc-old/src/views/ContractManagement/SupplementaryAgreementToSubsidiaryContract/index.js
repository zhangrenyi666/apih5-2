import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import SupplementaryAgreementToSubsidiaryContractForm from './form';
import { Toast } from 'antd-mobile';
import Operation from './operation'
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxCtFsSideAgreementId',
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
            apiName: 'getZxCtFsSideAgreementList',
            otherParams: { orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
          }}
          componentsKey={{
            SupplementaryAgreementToSubsidiaryContractForm: (obj) => {
              this.table.clearSelectedRows();
              return <SupplementaryAgreementToSubsidiaryContractForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            }
          }}
          actionBtns={[
            {
              name: 'add',//内置add del
              icon: 'plus',//icon
              type: 'primary',//类型  默认 primary  [primary dashed danger]
              label: '新增',
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.setState({
                  lineEdit: ''
                })
              },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
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
                  name: 'diySubmit',//内置add del
                  field: 'diySubmit',
                  label: '保存',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('addZxCtFsSideAgreement', obj._formData).then(
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
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
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
                    if (index === "0") {
                      return false;
                    } else {
                      return true;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.clearSelectedRows();
                    obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                    this.props.myFetch('updateZxCtFsSideAgreement', obj._formData).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
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
              name: 'del',//内置add del
              icon: 'delete',//icon
              type: 'danger',//类型  默认 primary  [primary dashed danger]
              label: '删除',
              field: "del",
              fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZxCtFsSideAgreement',
              },
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
            },
            {
              Component: "SupplementaryAgreementToSubsidiaryContractForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "附属合同补充协议",
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus !== '-1') {
                  return true;
                } else {
                  return false;
                }
              },
            }
          ]}
          formConfig={[
            {
              table: {
                title: '补充协议编号',
                dataIndex: 'contractNo',
                key: 'contractNo',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 170,
                onClick: 'detail',
                tooltip: 20,
              }
            },
            {
              table: {
                title: '补充协议名称',
                dataIndex: 'pp3',
                key: 'pp3',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 170,
                tooltip: 17,
              }
            },
            {
              table: {
                title: '合同名称',
                dataIndex: 'contractName',
                key: 'contractName',
                width: 170,
                tooltip: 17,
              }
            },
            {
              table: {
                title: '合同类型',
                dataIndex: 'contractType',
                key: 'contractType',
                width: 150,
              }
            },
            {
              table: {
                title: '甲方名称',
                dataIndex: 'firstName',
                key: 'firstName',
                width: 170,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 17,
              }
            },
            {
              table: {
                title: '乙方名称',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 170,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 17,
              }
            },
            {
              table: {
                title: '合同签订人',
                dataIndex: 'agent',
                key: 'agent',
                width: 100,
              }
            },
            {
              table: {
                title: '含税合同金额(万元)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                width: 150,
              }
            },
            {
              table: {
                title: '本期含税增减金额(万元)',
                dataIndex: 'pp4',
                key: 'pp4',
                width: 150,
              }
            },
            {
              table: {
                title: '变更后含税金额(万元)',
                dataIndex: 'alterContractSum',
                key: 'alterContractSum',
                width: 150,
              }
            },
            {
              table: {
                title: '是否抵扣',
                dataIndex: 'isDeduct',
                key: 'isDeduct',
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
                title: '开工日期',
                dataIndex: 'startDate',
                key: 'startDate',
                width: 100,
                format: 'YYYY-MM-DD',
              }
            },
            {
              table: {
                title: '竣工日期',
                dataIndex: 'endDate',
                key: 'endDate',
                width: 100,
                format: 'YYYY-MM-DD',
              }
            },
            {
              table: {
                title: '发起人',
                dataIndex: 'createUserName',
                key: 'createUserName',
                width: 100
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
              desc: '请上传变更申请的资料',
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
                    field: 'secondID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'firstID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtFsSideAgreementId',
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
                    field: 'comID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'upAlterContractSum',
                    type: 'number',
                    hide: true,
                  },
                  {
                    field: 'upAlterContractSumNoTax',
                    type: 'number',
                    hide: true,
                  },
                  {
                    field: 'upAlterContractSumTax',
                    type: 'number',
                    hide: true,
                  },
                  {
                    field: 'comName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '补充协议编号',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '补充协议名称',
                    field: "pp3",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '合同名称',
                    field: "contractName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '合同名称',
                    field: "zxCtFsContractId",
                    required: true,
                    type: 'select',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    span: 12,
                    condition: [
                      {
                        regex: { zxCtFsSideAgreementId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    optionConfig: {
                      label: 'contractName',
                      value: 'zxCtFsContractId',
                      linkageFields: {
                        upAlterContractSumTax: 'alterContractSumTax',
                        upAlterContractSumNoTax: 'alterContractSumNoTax',
                        upAlterContractSum: 'alterContractSum',
                        firstName: 'firstName',
                        secondName: 'secondName',
                        firstID: 'firstId',
                        secondID: 'secondID',
                        contractNo: 'contractNo',
                        contractCost: 'contractCost',
                        contractCostNoTax: "contractCostNoTax",
                        contractCostTax: 'contractCostTax',
                        alterContractSum: 'alterContractSum',
                        alterContractSumNoTax: 'alterContractSumNoTax',
                        alterContractSumTax: 'alterContractSumTax',
                        isDeduct: 'isDeduct',
                        contractName: 'contractName',
                        orgID: 'orgID',
                        orgName: 'orgName',
                        comID: "comID",
                        comName: 'comName'
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
                        let params = {}
                        params.contractNo = obj.itemData.contractNo
                        this.props.myFetch('getContractNo', params).then(
                          ({ data, success, message }) => {
                            if (success) {
                              obj.form.setFieldsValue({
                                contractNo: data.contractNo,
                              })
                            } else {
                              Msg.error(message);
                            }
                          }
                        );
                      } else {
                        obj.form.setFieldsValue({
                          contractNo: ''
                        })
                      }
                    },
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0' && obj.clickCb.rowInfo.name === 'add') {
                        return {
                          apiName: 'getZxCtFsContractList',
                          otherParams: { orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
                        }
                      }
                    },
                  },
                  {
                    label: '合同类型',
                    field: "contractType",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    initialValue: '附属类补充协议'
                  },
                  {
                    label: '甲方名称',
                    field: "firstName",
                    type: 'string',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '乙方名称',
                    field: "secondName",
                    type: 'string',
                    disabled: true,
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
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '不含税合同金额(万元)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '合同税额(万元)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '本期含税增减金额(万元)',
                    field: "pp4",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '本期不含税增减金额(万元)',
                    field: "pp4NoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '本期增减税额(万元)',
                    field: "pp4Tax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '变更后含税金额(万元)',
                    field: "alterContractSum",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '变更后不含税金额(万元)',
                    field: "alterContractSumNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '变更后税额(万元)',
                    field: "alterContractSumTax",
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
                    disabled: true,
                    span: 12,
                  },
                  {
                    label: '开工日期',
                    field: 'startDate',
                    type: 'date',
                    placeholder: '请选择',
                    span: 12,
                  },
                  {
                    label: '竣工日期',
                    field: 'endDate',
                    type: 'date',
                    placeholder: '请选择',
                    span: 12,
                  },
                  {
                    label: '合同工期',
                    field: "csTimeLimit",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '批复单位',
                    field: 'pp1',
                    type: 'select',
                    placeholder: '请选择',
                    span: 12,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: '项目',
                        value: '项目',
                      },
                      {
                        label: '公司',
                        value: '公司',
                      },
                      {
                        label: '局',
                        value: '局',
                      }
                    ],
                  },
                  {
                    label: '批复日期',
                    field: "replyDate",
                    span: 12,
                    type: 'date',
                    placeholder: '请选择',
                  },
                  {
                    label: '变更内容',
                    field: 'alterContent',
                    type: 'textarea',
                    span: 12,
                    required: true,
                    placeholder: '请输入',
                  },
                  {
                    label: '合同内容',
                    field: 'content',
                    type: 'textarea',
                    required: true,
                    span: 12
                  },
                  {
                    label: '变更原因',
                    field: 'alterReason',
                    span: 12,
                    type: 'textarea',
                    placeholder: '请输入',
                  },
                  {
                    label: '备注',
                    span: 12,
                    field: 'remarks',
                    type: 'textarea',
                    placeholder: '请输入',
                  },
                  {
                    label: '上传附件',
                    field: 'zxErpFileList',
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
              actionBtnsPosition: "top",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtFsSideAgreementId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtFsSideAgreementInventoryList',
                    otherParams: { zxCtFsSideAgreementId: obj.tableFns.form.getFieldValue('zxCtFsSideAgreementId') },
                    success: () => {
                      let params = {}
                      params.zxCtFsSideAgreementId = obj.tableFns.form.getFieldValue('zxCtFsSideAgreementId')
                      this.props.myFetch('getZxCtFsSideAgreementDetail', params).then(
                        ({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              alterContractSum: data.alterContractSum,
                              alterContractSumNoTax: data.alterContractSumNoTax,
                              alterContractSumTax: data.alterContractSumTax,
                              pp4NoTax: data.pp4NoTax,
                              pp4: data.pp4,
                              pp4Tax: data.pp4Tax,
                            })
                          } else {
                            Msg.error(message);
                          }
                        }
                      );
                    }
                  }
                },
                desc: '本页面所有金额单位为：元',
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtFsSideAgreementInventoryId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtFsSideAgreementInventoryId !== this.state.lineEdit
                },
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
                    onClick: () => {
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtFsSideAgreementInventoryId = new Date().getTime()
                      newRowData.alterType = '1'
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.setState({
                        lineEdit: newRowData.zxCtFsSideAgreementInventoryId
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
                            if (data[i].zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                              Msg.error('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxCtFsSideAgreementInventory', deleteData).then(
                            ({ success, message }) => {
                              if (success) {
                                Msg.success(message);
                                obj.btnCallbackFn.clearSelectedRows();
                                this.table.refresh()
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
                      field: 'upAlterContractSum', //上期末变更后金额
                      type: 'number',
                      hide: true,
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'upAlterContractSumNoTax', //上期末变更后金额不含税
                      type: 'number',
                      hide: true,
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtFsSideAgreementInventoryId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: `<div>变更类型<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'alterType',
                      key: 'alterType',
                      width: 120,
                      align: 'center',
                      tdEdit: true,
                      fixed: 'left',
                      render: (h) => {
                        if (h === '1') {
                          return '新增'
                        } else if (h === '2') {
                          return '修改'
                        }
                      }
                    },
                    form: {
                      field: 'alterType',
                      placeholder: '请选择',
                      type: 'select',
                      allowClear: false,//是否显示清除按钮
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                      optionData: [
                        {
                          label: '新增',
                          value: '1',
                        },
                        {
                          label: '修改',
                          value: '2',
                        }
                      ],
                      onChange: (val) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            rowData.alterType = val
                            rowData.workNo = null
                            rowData.workName = null
                            rowData.unit = null
                            rowData.qty = null
                            rowData.price = null
                            rowData.priceNoTax = null
                            rowData.contractSum = null
                            rowData.contractSumNoTax = null
                            rowData.contractSumTax = null
                            rowData.changeQty = null
                            rowData.changePrice = null
                            rowData.changeContractSum = null
                            rowData.changeContractSumNoTax = null
                            rowData.changeContractSumTax = null
                            rowData.taxRate = null
                            rowData.remarks = null
                            rowData.upAlterContractSum = null
                            rowData.upAlterContractSumNoTax = null
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
                      title: `<div>清单编号<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 140,
                      align: 'center',
                      tdEdit: true,
                      fixed: 'left',
                    },
                    form: {
                      field: 'workNo',
                      type: (obj) => {
                        if (obj.alterType === '1') {
                          return 'string'
                        } else if (obj.alterType === '2') {
                          return 'select'
                        }
                      },
                      allowClear: false,//是否显示清除按钮
                      optionConfig: {
                        label: 'workName',
                        value: 'workNo',
                      },
                      fetchConfig: () => {
                        return {
                          apiName: 'getAllReviewDetailListByContract',
                          otherParams: {
                            zxCtFsContractId: this.table.form.getFieldValue('zxCtFsContractId')
                          }
                        }
                      },
                      onChange: (val, obj) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            rowData.workNo = val;
                            if (obj.itemData) {
                              rowData.workName = obj.itemData.workName;
                              rowData.unit = obj.itemData.unit;
                              rowData.qty = obj.itemData.qty;
                              rowData.price = obj.itemData.price;
                              rowData.priceNoTax = obj.itemData.priceNoTax;
                              rowData.contractSum = obj.itemData.contractSum;
                              rowData.contractSumNoTax = obj.itemData.contractSumNoTax;
                              rowData.contractSumTax = obj.itemData.contractSumTax;
                              rowData.changeQty = obj.itemData.changeQty;
                              rowData.changePrice = obj.itemData.changePrice ? obj.itemData.changePrice : obj.itemData.price ? obj.itemData.price : 0;
                              rowData.taxRate = obj.itemData.taxRate;
                              rowData.isDeduct = obj.itemData.isDeduct;
                              rowData.remarks = obj.itemData.remarks;
                              rowData.conRevDetailId = obj.itemData.conRevDetailId
                              rowData.changeContractSum = obj.itemData.changeContractSum
                              rowData.changeContractSumNoTax = obj.itemData.changeContractSumNoTax
                              rowData.changeContractSumTax = obj.itemData.changeContractSum - obj.itemData.changeContractSumNoTax
                              rowData.upAlterContractSum = obj.itemData.changeContractSum ? obj.itemData.changeContractSum : null;
                              rowData.upAlterContractSumNoTax = obj.itemData.changeContractSumNoTax ? obj.itemData.changeContractSumNoTax : null;
                            }
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
                      dataIndex: 'workName',
                      key: 'workName',
                      align: 'center',
                      title: `<div>清单名称<span style='color: #ff4d4f'>*</span></div>`,
                      width: 150,
                      tdEdit: true,
                      fieldConfig: {
                        field: 'workName',
                        type: "string",
                        placeholder: '请输入',
                        disabled: (obj) => {
                          if (obj.record.alterType === '2') return true
                        }
                      },
                    },
                    form: {
                      onBlur: (e) => {
                        let val = e.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            rowData.workName = val
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
                      title: ' 单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      align: 'center',
                      tdEdit: true,
                      fieldConfig: {
                        field: 'unit',
                        type: "string",
                        placeholder: '请输入',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                        }
                      }
                    },
                    form: {
                      onBlur: (e) => {
                        let val = e.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            rowData.unit = val
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
                      title: '数量',
                      dataIndex: 'qty',
                      key: 'qty',
                      width: 100,
                      type: 'number',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '含税单价',
                      dataIndex: 'price',
                      key: 'price',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '不含税单价',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '含税金额',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 120,
                      align: 'center',
                    }
                  },

                  {
                    table: {
                      title: '不含税金额',
                      dataIndex: 'contractSumNoTax',
                      key: 'contractSumNoTax',
                      width: 120,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '不含税税额',
                      dataIndex: 'contractSumTax',
                      key: 'contractSumTax',
                      width: 120,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '变更后数量',
                      dataIndex: 'changeQty',
                      key: 'changeQty',
                      width: 120,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      field: 'changeQty',
                      placeholder: '请输入',
                      type: 'number',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                              if (Number(data.changeQty) !== Number(rowData.changeQty)) {
                                rowData.changeQty = val ? Number(val) : 0;
                                rowData.changeContractSum = (rowData.changePrice ? rowData.changePrice : 0) * Number(val)
                                rowData.changeContractSumNoTax = ((rowData.changePrice ? rowData.changePrice : 0) * Number(val)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)
                                rowData.changeContractSumTax = (((rowData.changePrice ? rowData.changePrice : 0) * Number(val)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)) * ((rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)
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
                      title: '变更后单价',
                      dataIndex: 'changePrice',
                      key: 'changePrice',
                      width: 120,
                      tdEdit: true,
                      fieldConfig: {
                        type: "number",
                        placeholder: '请输入',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                          return false;
                        }
                      },
                      align: 'center',
                    },
                    form: {
                      field: 'changePrice',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                              if (Number(data.changePrice) !== Number(rowData.changePrice)) {
                                rowData.changePrice = val ? Number(val) : 0;
                                rowData.changeContractSum = (rowData.changeQty ? rowData.changeQty : 0) * Number(val)
                                rowData.changeContractSumNoTax = ((rowData.changeQty ? rowData.changeQty : 0) * Number(val)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)
                                rowData.changeContractSumTax = (((rowData.changeQty ? rowData.changeQty : 0) * Number(val)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)) * ((rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)
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
                      title: '变更后含税金额',
                      dataIndex: 'changeContractSum',
                      key: 'changeContractSum',
                      width: 120,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.changeContractSum ? rowData.changeContractSum.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '变更后不含税金额',
                      dataIndex: 'changeContractSumNoTax',
                      key: 'changeContractSumNoTax',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.changeContractSumNoTax ? rowData.changeContractSumNoTax.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '变更后税额',
                      dataIndex: 'changeContractSumTax',
                      key: 'changeContractSumTax',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.changeContractSumTax ? rowData.changeContractSumTax.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100,
                      tdEdit: true,
                      fieldConfig: {
                        field: 'taxRate',
                        type: "select",
                        placeholder: '请选择',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                          return false;
                        }
                      },
                      align: 'center',
                    },
                    form: {
                      optionConfig: {
                        label: 'itemName',
                        value: 'itemId',
                      },
                      fetchConfig: {
                        apiName: 'getBaseCodeSelect',
                        otherParams: { itemId: 'shuiLv' }
                      },
                      onChange: (val) => {
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            rowData.taxRate = val
                            rowData.changeContractSumNoTax = ((rowData.changePrice ? rowData.changePrice : 0) * (rowData.changeQty ? rowData.changeQty : 0)) / (1 + Number(val) / 100)
                            rowData.changeContractSumTax = (((rowData.changePrice ? rowData.changePrice : 0) * (rowData.changeQty ? rowData.changeQty : 0)) / (1 + Number(val) / 100)) * (Number(val) / 100)
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
                      title: '是否抵扣',
                      dataIndex: 'isDeduct',
                      key: 'isDeduct',
                      width: 100,
                      align: 'center',
                      render: () => {
                        let isDeduct = this.table.btnCallbackFn.form.getFieldValue('isDeduct')
                        if (isDeduct === '1') {
                          return '是'
                        } else {
                          return '否'
                        }
                      },
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
                      field: 'remarks',
                      type: "string",
                      placeholder: '请输入',
                      onBlur: (e) => {
                        let val = e.target.value;
                        let tableOneData = this.tableOne.state.data.map((rowData) => {
                          if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                            rowData.remarks = val
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
                        if (rowData.zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxCtFsSideAgreementInventoryId) {
                          if (!obj.rowData.alterType) {
                            Msg.warn('请填写变更类型！')
                            return
                          } else if (!obj.rowData.workNo) {
                            Msg.warn('请填写清单编号！')
                            return
                          } else if (!obj.rowData.workName) {
                            Msg.warn('请填写清单名称！')
                            return
                          } else if (!obj.rowData.taxRate) {
                            Msg.warn('请填写税率！')
                            return
                          }
                          Toast.loading('loading...');
                          setTimeout(() => {
                            let body = {
                              ...obj.rowData,
                            };
                            body.zxCtFsSideAgreementId = obj.props.tableFns.qnnForm.form.getFieldValue('zxCtFsSideAgreementId')
                            this.props.myFetch(!isNaN(obj.rowData.zxCtFsSideAgreementInventoryId) ? 'addZxCtFsSideAgreementInventory' : 'updateZxCtFsSideAgreementInventory', body).then(
                              ({ success, message }) => {
                                if (success) {
                                  Msg.success(message);
                                  Toast.hide();
                                  this.setState({
                                    lineEdit: ''
                                  })
                                  obj.btnCallbackFn.refresh();
                                  this.table.refresh()
                                } else {
                                  Toast.hide();
                                  Msg.error(message);
                                }
                              }
                            );
                          }, 300)
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtFsSideAgreementInventoryId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxCtFsSideAgreementInventoryId
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