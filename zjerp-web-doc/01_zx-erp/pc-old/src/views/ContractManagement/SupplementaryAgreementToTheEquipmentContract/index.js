import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import SupplementaryAgreementToTheEquipmentContractForm from './form';
import { Toast } from 'antd-mobile';
import Operation from './operation'
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxCtEqContrSupplyId',
    size: 'small'
  },
  drawerConfig: {
    width: window.screen.width * 0.8
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
            apiName: 'getZxCtEqContrSupplyList',
            otherParams: { orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
          }}
          componentsKey={{
            SupplementaryAgreementToTheEquipmentContractForm: (obj) => {
              this.table.clearSelectedRows();
              return <SupplementaryAgreementToTheEquipmentContractForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
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
                    this.props.myFetch('addZxCtEqContrSupply', obj._formData).then(
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
                    this.props.myFetch('updateZxCtEqContrSupply', obj._formData).then(
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
                apiName: 'batchDeleteUpdateZxCtEqContrSupply',
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
              Component: "SupplementaryAgreementToTheEquipmentContractForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "设备合同补充协议",
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
                dataIndex: 'supplyName',
                key: 'supplyName',
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
                dataIndex: 'thisAmount',
                key: 'thisAmount',
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
                    field: 'zxCtEqContrSupplyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'contrAlterID',
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
                    field: 'firstID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondID',
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
                    field: "supplyName",
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
                    field: "contractID",
                    required: true,
                    type: 'select',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    span: 12,
                    condition: [
                      {
                        regex: { zxCtEqContrSupplyId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    optionConfig: {
                      label: 'contractName',
                      value: 'contractID',
                      linkageFields: {
                        contractName: 'contractName',
                        firstName: 'firstName',
                        firstID: 'firstId',
                        secondID: 'secondID',
                        contractID: 'contractID',
                        secondName: 'secondName',
                        contractCost: 'contractCost',
                        contractCostNoTax: "contractCostNoTax",
                        contractCostTax: 'contractCostTax',
                        thisAmount: 'thisAmount',
                        thisAmountNoTax: 'thisAmountNoTax',
                        thisAmountTax: 'thisAmountTax',
                        alterContractSum: 'alterContractSum',
                        alterContractSumNoTax: 'alterContractSumNoTax',
                        alterContractSumTax: 'alterContractSumTax',
                        isDeduct: 'isDeduct',
                        orgName: 'orgName',
                        orgID: 'orgID',
                        comID: "comID",
                        comName: 'comName',
                        content: 'content',
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
                        let params = {}
                        params.contractID = val
                        this.props.myFetch('generateZxCtEqContrSupplyContractNo', params).then(
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
                          contractNo: '',
                        })
                      }
                    },
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0' && obj.clickCb.rowInfo.name === 'add') {
                        return {
                          apiName: 'getZxCtEqContractList',
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
                    initialValue: '机械管理类补充协议'
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
                    field: "thisAmount",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '本期不含税增减金额(万元)',
                    field: "thisAmountNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '本期增减税额(万元)',
                    field: "thisAmountTax",
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
                    field: 'remark',
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
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtEqContrSupplyId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContrAlterItemResoureList',
                    otherParams: {
                      zxCtEqContrSupplyId: obj.tableFns.form.getFieldValue('zxCtEqContrSupplyId'),
                      contrAlterID: obj.tableFns.form.getFieldValue('contrAlterID')
                    },
                    success: () => {
                      let params = {}
                      params.zxCtEqContrSupplyId = obj.tableFns.form.getFieldValue('zxCtEqContrSupplyId')
                      this.props.myFetch('getZxCtEqContrSupplyDetail', params).then(
                        ({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              contractCost: data.contractCost,
                              contractCostNoTax: data.contractCostNoTax,
                              contractCostTax: data.contractCostTax,
                              thisAmountNoTax: data.thisAmountNoTax,
                              thisAmount: data.thisAmount,
                              thisAmountTax: data.thisAmountTax,
                              alterContractSum: data.alterContractSum,
                              alterContractSumNoTax: data.alterContractSumNoTax,
                              alterContractSumTax: data.alterContractSumTax
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
                  rowKey: "zxCtEqContrAlterItemResoureId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtEqContrAlterItemResoureId !== this.state.lineEdit
                },
                desc: '本页面所有单位为：元',
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'addRow',
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
                    addCb: () => {
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.zxCtEqContrAlterItemResoureId = new Date().getTime()
                      newRowData.alterType = '1'
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.setState({
                        lineEdit: newRowData.zxCtEqContrAlterItemResoureId
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
                          let deleteData = obj.selectedRows
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.props.myFetch('batchDeleteUpdateZxCtEqContrAlterItemResoure', deleteData).then(
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
                      field: 'zxCtEqContrAlterItemResoureId',
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
                          if (rowData.zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                            rowData.alterType = val
                            rowData.catCode = null
                            rowData.catName = null
                            rowData.spec = null
                            rowData.unit = null
                            rowData.actualStartDate = undefined
                            rowData.actualEndDate = undefined
                            rowData.qty = null
                            rowData.price = null
                            rowData.contractSum = null
                            rowData.priceNoTax = null
                            rowData.contractSumNoTax = null
                            rowData.contractSumTax = null
                            rowData.taxRate = null
                            rowData.alterRentStartDate = undefined
                            rowData.alterRentEndDate = undefined
                            rowData.alterTrrm = null
                            rowData.alterPrice = null
                            rowData.alterPriceNoTax = null
                            rowData.alterAmt = null
                            rowData.alterAmtNoTax = null
                            rowData.alterAmtTax = null
                            rowData.remark = null
                            rowData.contrItemID = null
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
                      title: `<div>编码<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'catCode',
                      key: 'catCode',
                      width: 140,
                      align: 'center',
                      tdEdit: true,
                      fixed: 'left',
                    },
                    form: {
                      field: 'contrItemID',
                      type: (obj) => {
                        if (obj.alterType === '1') {
                          return 'selectByQnnTable'
                        } else if (obj.alterType === '2') {
                          return 'select'
                        }
                      },
                      optionConfig: {
                        label: 'catCode',
                        value: 'zxCtEqContrItemId',
                      },
                      fetchConfig: (obj) => {
                        if (obj.rowData.alterType === '2') {
                          return {
                            apiName: 'getZxCtEqContrItemListForContract',
                            otherParams: {
                              contractID: this.table.form.getFieldValue('contractID')
                            }
                          }
                        } else {
                          return {
                            apiName: 'getZxEqResCategoryItemList',
                            otherParams: {
                              contractID: this.table.form.getFieldValue('contractID')
                            }
                          };
                        }

                      },
                      dropdownMatchSelectWidth: 800,
                      allowClear: false,
                      showSearch: true,
                      qnnTableConfig: {
                        antd: {
                          rowKey: "zxCtEqContrItemId"
                        },
                        fetchConfig: {
                          apiName: 'getZxEqResCategoryItemList',
                          otherParams: () => {
                            return {
                              contractID: this.table.form.getFieldValue('contractID')
                            }
                          }
                        },
                        searchBtnsStyle: "inline",
                        formConfig: [
                          {
                            isInForm: false,
                            isInSearch: true,
                            table: {
                              dataIndex: "catCode",
                              title: "编码",
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
                              title: "设备名称",
                              width: 100
                            },
                            form: {
                              type: 'string'
                            }
                          }
                        ]
                      },
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          if (this.tableOne.state.data.filter(item => item.zxCtEqContrItemId === val).length) {
                            Msg.warn('已存在相同的清单，请重试!')
                          } else {
                            let tableOneData = this.tableOne.state.data.map((rowData) => {
                              if (rowData.zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                                if (data.alterType === '1' && obj?.itemData?.length) {
                                  rowData.contrItemID = obj.itemData[0].zxCtEqContrItemId
                                  rowData.catCode = obj.itemData[0].catCode;
                                  rowData.catName = obj.itemData[0].catName;
                                  rowData.spec = obj.itemData[0].spec
                                  rowData.unit = obj.itemData[0].unit
                                  rowData.parentID = obj.itemData[0].parentID
                                  rowData.catParentName = obj.itemData[0].parentName
                                  rowData.zxEqResCategoryId = obj.itemData[0].id
                                  rowData.actualStartDate = data.actualStartDate
                                  rowData.actualEndDate = data.actualEndDate
                                  rowData.alterRentStartDate = data.alterRentStartDate
                                  rowData.alterRentEndDate = data.alterRentEndDate
                                  rowData.remark = data.remark
                                } else if (data.alterType === '2') {
                                  rowData.contrItemID = obj.itemData.zxCtEqContrItemId
                                  rowData.catCode = obj.itemData.catCode;
                                  rowData.spec = obj.itemData.spec
                                  rowData.catName = obj.itemData.catName;
                                  rowData.unit = obj.itemData.unit
                                  rowData.qty = obj.itemData.qty
                                  rowData.price = obj.itemData.price
                                  rowData.contractSum = obj.itemData.contractSum ? obj.itemData.contractSum : 0
                                  rowData.priceNoTax = obj.itemData.priceNoTax
                                  rowData.contractSumNoTax = obj.itemData.contractSumNoTax ? obj.itemData.contractSumNoTax : 0
                                  rowData.contractSumTax = (obj.itemData.contractSum ? obj.itemData.contractSum : 0) - (obj.itemData.contractSumNoTax ? obj.itemData.contractSumNoTax : 0)
                                  rowData.taxRate = obj.itemData.taxRate
                                  rowData.alterPrice = obj.itemData.alterPrice ? obj.itemData.alterPrice : obj.itemData.price ? obj.itemData.price : 0
                                  rowData.alterPriceNoTax = obj.itemData.alterPriceNoTax ? obj.itemData.alterPriceNoTax : obj.itemData.priceNoTax ? obj.itemData.priceNoTax : 0
                                  rowData.actualStartDate = obj.itemData.actualStartDate
                                  rowData.actualEndDate = obj.itemData.actualEndDate
                                  rowData.alterRentStartDate = data.alterRentStartDate
                                  rowData.alterRentEndDate = data.alterRentEndDate
                                  rowData.parentID = obj.itemData.parentID
                                  rowData.catParentName = obj.itemData.catParentName
                                  rowData.zxEqResCategoryId = obj.itemData.zxEqResCategoryId
                                  rowData.alterAmt = obj.itemData.alterAmt ? obj.itemData.alterAmt : 0
                                  rowData.alterAmtNoTax = obj.itemData.alterAmtNoTax ? obj.itemData.alterAmtNoTax : 0
                                  rowData.alterAmtTax = obj.itemData.alterAmtTax ? obj.itemData.alterAmtTax : 0
                                  rowData.alterTrrm = obj.itemData.alterTrrm
                                  rowData.remark = obj.itemData.remark
                                }
                              }
                              return rowData;
                            })
                            this.tableOne.btnCallbackFn.setState({
                              data: tableOneData
                            })
                          }
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: `<div>设备名称<span style='color: #ff4d4f'>*</span></div>`,
                      width: 150,
                      type: 'string',
                      dataIndex: 'catName',
                      key: 'catName',
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '型号',
                      width: 150,
                      dataIndex: 'spec',
                      key: 'spec',
                      align: 'center',
                      tdEdit: true,
                      fieldConfig: {
                        field: 'spec',
                        type: 'string',
                        placeholder: '请输入',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                          return false;
                        }
                      }
                    }
                  },
                  {
                    table: {
                      title: ' 租赁单位',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      tdEdit: true,
                      fieldConfig: {
                        field: 'unit',
                        type: 'string',
                        placeholder: '请输入',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                          return false;
                        }
                      },
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      dataIndex: 'actualStartDate',
                      key: 'actualStartDate',
                      title: '租赁开始时间',
                      span: 12,
                      tdEdit: true,
                      width: 150,
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      field: "actualStartDate",
                      placeholder: '请选择',
                      type: 'date'
                    }

                  },
                  {
                    table: {
                      title: '租赁结束时间',
                      dataIndex: 'actualEndDate',
                      key: 'actualEndDate',
                      span: 12,
                      tdEdit: true,
                      width: 150,
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      field: 'actualEndDate',
                      type: 'date',
                      placeholder: '请选择',
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
                      title: '含税合同单价',
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '含税合同金额',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '不含税合同单价',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '不含税合同金额',
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
                      title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100,
                      tdEdit: true,
                      fieldConfig: {
                        type: 'select',
                        placeholder: '请选择',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                          return false;
                        }
                      },
                      align: 'center',
                    },
                    form: {
                      field: 'taxRate',
                      showSearch: true,
                      optionConfig: {
                        label: 'itemName',
                        value: 'itemId',
                      },
                      fetchConfig: {
                        apiName: 'getBaseCodeSelect',
                        otherParams: { itemId: 'shuiLv' }
                      },
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                              rowData.taxRate = val   //税率
                              let alterPriceNoTax = (rowData.alterPrice ? rowData.alterPrice : 0) / (1 + Number(val) / 100)//不含税单价
                              rowData.alterPriceNoTax = Math.round(alterPriceNoTax * 100) / 100
                              rowData.alterAmtNoTax = ((rowData.alterPrice ? rowData.alterPrice : 0) / (1 + Number(val) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0) //不含税金额
                              rowData.alterAmtTax = ((rowData.alterPrice ? rowData.alterPrice : 0) / (1 + Number(val) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0) * (Number(val) / 100)//税额
                              rowData.remark = data.remark
                              rowData.unit = data.unit
                              rowData.actualStartDate = data.actualStartDate
                              rowData.actualEndDate = data.actualEndDate
                              rowData.alterRentStartDate = data.alterRentStartDate
                              rowData.alterRentEndDate = data.alterRentEndDate
                              rowData.spec = data.spec
                            }
                            return rowData;
                          })
                          this.tableOne.btnCallbackFn.setState({
                            data: tableOneData
                          })
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
                      title: '变更后租赁开始时间',
                      dataIndex: 'alterRentStartDate',
                      key: 'alterRentStartDate',
                      span: 12,
                      tdEdit: true,
                      width: 150,
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      field: 'alterRentStartDate',
                      type: 'date',
                      placeholder: '请选择',
                    }
                  },
                  {
                    table: {
                      title: '变更后租赁结束时间',
                      dataIndex: 'alterRentEndDate',
                      key: 'alterRentEndDate',
                      span: 12,
                      tdEdit: true,
                      width: 150,
                      format: 'YYYY-MM-DD',
                    },
                    form: {
                      field: 'alterRentEndDate',
                      type: 'date',
                      placeholder: '请选择',
                    }
                  },
                  {
                    table: {
                      title: '变更后数量',
                      dataIndex: 'alterTrrm',
                      key: 'alterTrrm',
                      width: 120,
                      tdEdit: true,
                      align: 'center',
                      type: 'number',
                    },
                    form: {
                      precision: 3,
                      field: 'alterTrrm',
                      placeholder: '请输入',
                      type: 'number',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                              if (Number(data.alterTrrm) !== Number(rowData.alterTrrm)) {
                                rowData.alterTrrm = val ? Number(val) : 0;
                                rowData.alterAmt = (rowData.alterPrice ? rowData.alterPrice : 0) * Number(val) //变更后含税金额
                                rowData.alterAmtNoTax = (rowData.alterPriceNoTax ? rowData.alterPriceNoTax : 0) * Number(val)  //变更后不含税金额
                                rowData.alterAmtTax = (rowData.alterPriceNoTax ? rowData.alterPriceNoTax : 0) * Number(val) * ((rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //变更后税额
                                rowData.remark = data.remark
                                rowData.actualStartDate = data.actualStartDate
                                rowData.actualEndDate = data.actualEndDate
                                rowData.alterRentStartDate = data.alterRentStartDate
                                rowData.alterRentEndDate = data.alterRentEndDate
                                if (data.alterType === '1') {
                                  rowData.unit = data.unit
                                  rowData.spec = data.spec
                                }
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
                      title: '变更后含税单价',
                      dataIndex: 'alterPrice',
                      key: 'alterPrice',
                      width: 120,
                      type: 'number',
                      tdEdit: true,
                      fieldConfig: {
                        placeholder: '请输入',
                        type: 'number',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                          return false;
                        }
                      },
                      align: 'center',
                    },
                    form: {
                      field: 'alterPrice',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                              if (Number(data.alterPrice) !== Number(rowData.alterPrice)) {
                                rowData.alterPrice = val ? Number(val) : 0;
                                let alterPriceNoTax = Number(val) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)//变更后不含税单价
                                rowData.alterPriceNoTax = Math.round(alterPriceNoTax * 100) / 100
                                rowData.alterAmt = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val)         //变更后含税金额
                                rowData.alterAmtNoTax = (Number(val) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0) //变更后不含税金额
                                rowData.alterAmtTax = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val) - (Number(val) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0)   //变更后税额
                                rowData.remark = data.remark
                                rowData.unit = data.unit
                                rowData.actualStartDate = data.actualStartDate
                                rowData.actualEndDate = data.actualEndDate
                                rowData.alterRentStartDate = data.alterRentStartDate
                                rowData.alterRentEndDate = data.alterRentEndDate
                                rowData.spec = data.spec
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
                      title: '变更后不含税单价',
                      dataIndex: 'alterPriceNoTax',
                      key: 'alterPriceNoTax',
                      width: 150,
                      tdEdit: true,
                      fieldConfig: {
                        placeholder: '请输入',
                        type: 'number',
                        disabled: function (obj) {
                          if (obj.record.alterType === "2") return true
                          return false;
                        }
                      },
                      align: 'center',
                    },
                    form: {
                      field: 'alterPriceNoTax',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                              if (Number(data.alterPriceNoTax) !== Number(rowData.alterPriceNoTax)) {
                                rowData.alterPriceNoTax = val ? Number(val) : 0;
                                let alterPrice = Number(val) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //变更后含税单价
                                rowData.alterPrice = Math.round(alterPrice * 100) / 100
                                rowData.alterAmt = ((rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val)) * (1 + ((rowData.taxRate ? Number(rowData.taxRate) : 0) / 100))  //变更后含税金额
                                rowData.alterAmtNoTax = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val)    //变更后不含税金额
                                rowData.alterAmtTax = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val) * ((rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)   //变更后税额
                                rowData.remark = data.remark
                                rowData.unit = data.unit
                                rowData.actualStartDate = data.actualStartDate
                                rowData.actualEndDate = data.actualEndDate
                                rowData.alterRentStartDate = data.alterRentStartDate
                                rowData.alterRentEndDate = data.alterRentEndDate
                                rowData.spec = data.spec
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
                      dataIndex: 'alterAmt',
                      key: 'alterAmt',
                      width: 120,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.alterAmt ? rowData.alterAmt.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '变更后不含税金额',
                      dataIndex: 'alterAmtNoTax',
                      key: 'alterAmtNoTax',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.alterAmtNoTax ? rowData.alterAmtNoTax.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '变更后税额',
                      dataIndex: 'alterAmtTax',
                      key: 'alterAmtTax',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.alterAmtTax ? rowData.alterAmtTax.toFixed(2) : 0
                      }
                    }
                  },
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
                      field: 'remark',
                      type: 'string',
                      placeholder: '请输入',
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
                        if (rowData.zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxCtEqContrAlterItemResoureId) {
                          if (!obj.rowData.alterType) {
                            Msg.warn('请填写变更类型！')
                            return
                          } else if (!obj.rowData.catCode) {
                            Msg.warn('请填写编码！')
                            return
                          } else if (!obj.rowData.catName) {
                            Msg.warn('请填写设备名称！')
                            return
                          } else if (!obj.rowData.taxRate) {
                            Msg.warn('请填写税率！')
                            return
                          }
                          Toast.loading('loading...');
                          setTimeout(() => {
                            obj.btnCallbackFn.getEditedRowData().then((data) => {
                              let body = {
                                ...obj.rowData,
                                ...data
                              };
                              body.zxCtEqContrSupplyId = obj.props.tableFns.qnnForm.form.getFieldValue('zxCtEqContrSupplyId')
                              body.contrAlterID = obj.props.tableFns.qnnForm.form.getFieldValue('contrAlterID')
                              this.props.myFetch(!isNaN(obj.rowData.zxCtEqContrAlterItemResoureId) ? 'addZxCtEqContrAlterItemResoure' : 'updateZxCtEqContrAlterItemResoure', body).then(
                                ({ success, message }) => {
                                  if (success) {
                                    Msg.success(message);
                                    this.setState({
                                      lineEdit: ''
                                    })
                                    this.table.refresh()
                                    obj.btnCallbackFn.refresh();
                                    Toast.hide()
                                  } else {
                                    Msg.error(message);
                                    Toast.hide()
                                  }
                                }
                              );
                            })
                          }, 300)
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxCtEqContrAlterItemResoureId === this.state.lineEdit) {
                              Msg.error('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxCtEqContrAlterItemResoureId
                          })
                        }
                      }
                    }
                  }
                ],

              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;