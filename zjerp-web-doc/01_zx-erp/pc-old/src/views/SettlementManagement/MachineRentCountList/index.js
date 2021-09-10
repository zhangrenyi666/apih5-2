import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import { Toast } from 'antd-mobile';
import MachineRentCountListForm from './form';
import ManageView from './manageView';
import TabThreeList from './TabThree';
import TabTwoList from './TabTwo';
import DetailView from './detail';
import moment from 'moment';
import Operation from './operation'
const config = {
  antd: {
    rowKey: 'zxSaEquipSettleAuditId',
    size: 'small'
  },
  desc: '本页面所有金额单位为: 元',
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
            apiName: 'getZxSaEquipSettleAuditList',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              console.log('11111');
              this.table.refresh()
            }
          }}
          componentsKey={{
            MachineRentCountListForm: (obj) => {
              this.table.clearSelectedRows();
              return <MachineRentCountListForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            },
            DetailView: (obj) => {
              return <DetailView {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            },
            ManageView: (obj) => {
              return <ManageView {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            },
          }}
          tabsWillChange={(obj, canChange) => {
            if (this.tableFour) {
              this.tableFour.refresh();
            }
            canChange(true);
          }}
          actionBtns={[
            {
              name: 'add',//内置add del
              icon: 'plus',//icon
              type: 'primary',//类型  默认 primary  [primary dashed danger]
              label: '新增',
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
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
                    this.props.myFetch('addZxSaEquipSettleAudit', obj._formData).then(
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
                obj.btnCallbackFn.clearSelectedRows();
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
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('updateZxSaEquipSettleAudit', obj._formData).then(
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
                apiName: 'batchDeleteUpdateZxSaEquipSettleAudit',
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
              Component: "MachineRentCountListForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "机械租赁结算单",
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
            {
              Component: "DetailView",
              icon: "plus",
              label: "详细",
              name: "Component",
              tableField: "DetailView",
              drawerTitle: "设备租赁类结算会签单预览",
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus !== '2') {
                  return true;
                } else {
                  return false;
                }
              },
            },
            {
              Component: "ManageView",
              icon: "plus",
              label: "营改增一览",
              name: "Component",
              tableField: "ManageView",
              drawerTitle: "营改增一览",
              type: "primary",
              disabled: (obj) => {
                if (obj.btnCallbackFn.getSelectedRows().length !== 1) return true
              },
            },
          ]}
          formConfig={[
            {
              table: {
                title: '结算单编号',
                dataIndex: 'billNo',
                key: 'billNo',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
                tooltip: 22,
                onClick: 'detail',
              }
            },
            {
              table: {
                title: '项目名称',
                dataIndex: 'orgName',
                key: 'orgName',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
                tooltip: 18,
              }
            },
            {
              table: {
                title: '结算期次',
                dataIndex: 'periodDate',//未确定字段
                key: 'periodDate',
                width: 120,
                format: 'YYYY-MM'
              }
            },
            {
              table: {
                title: '结算类型',
                dataIndex: 'billType',
                key: 'billType',
                width: 120,
                render: (h) => {
                  if (h === '0') {
                    return '中期'
                  } else if (h === '1') {
                    return '最终'
                  } else if (h === '2') {
                    return '返还保证金'
                  }
                }
              }
            },
            {
              table: {
                title: '合同名称',
                dataIndex: 'contractName',
                key: 'contractName',
                filter: true,
                fieldsConfig: { type: 'string' },
                width: 150,
                tooltip: 15,
              }
            },
            {
              table: {
                title: '合同乙方',
                dataIndex: 'secondName',
                key: 'secondName',
                filter: true,
                fieldsConfig: { type: 'string' },
                width: 150,
                tooltip: 15,
              }
            },
            {
              table: {
                title: '本期结算含税金额',
                dataIndex: 'thisAmt',
                key: 'thisAmt',
                width: 150,
              }
            },
            {
              table: {
                title: '开累结算含税金额',
                dataIndex: 'totalAmt',
                key: 'totalAmt',
                width: 150,
              }
            },
            {
              table: {
                title: '本期应支付含税金额',
                dataIndex: 'thisPayAmt',
                key: 'thisPayAmt',
                width: 150,
              }
            },
            {
              table: {
                title: '开累应支付含税金额',
                dataIndex: 'totalPayAmt',
                key: 'totalPayAmt',
                width: 150,
              }
            },
            {
              table: {
                title: '重新评审次数',
                dataIndex: 'notPassNum',
                key: 'notPassNum',
                width: 150,
              }
            },
            {
              table: {
                title: '结算期限开始时间',
                dataIndex: 'beginDate',
                key: 'beginDate',
                width: 150,
                format: "YYYY-MM-DD"
              }
            },
            {
              table: {
                title: '结算期限结束时间',
                dataIndex: 'endDate',
                key: 'endDate',
                width: 150,
                format: "YYYY-MM-DD"
              }
            },
            {
              table: {
                title: '业务日期',
                dataIndex: 'businessDate',
                key: 'businessDate',
                width: 100,
                format: "YYYY-MM-DD"
              }
            },
            {
              table: {
                title: '发起人',
                dataIndex: 'createUserName',
                key: 'createUserName',
                width: 100,
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
            },
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "信息登记",
              content: {
                formConfig: [
                  {
                    field: 'apih5FlowStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxSaEquipSettleAuditId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxSaEquipResSettleId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxSaEquipPaySettleId',
                    type: 'string',
                    hide: true
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
                    field: 'finishStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'settleTypeFlag',
                    type: 'string',
                    hide: true,
                    initialValue: 'false',
                  },
                  {
                    field: 'secondID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '项目名称',
                    field: "orgName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow:false,
                    editShow:true,
                    detailShow:true,
                  },
                  {
                    label: '合同编号',
                    field: "contractNo",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow:false,
                    editShow:true,
                    detailShow:true,
                  },
                  {
                    label: '项目名称',
                    field: "orgID",
                    type: 'select',
                    placeholder: '请选择',
                    showSearch: true,
                    span: 12,
                    required: true,
                    addShow:true,
                    editShow:false,
                    detailShow:false,
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        orgName: 'orgName',
                        comID: 'comID',
                        comName: 'comName',
                        finishStatus: 'finishStatus'
                      }
                    },
                    condition: [
                      {
                        regex: { zxSaEquipSettleAuditId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      obj.funcCallBackParams.refresh();
                      obj.form.setFieldsValue({
                        contractID: null
                      })
                    },
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0') {
                        return {
                          apiName: 'getZxSaProjectUnFinishList',
                          otherParams: { orgID: departmentId, settleTypeCode: 'P6' }
                        }
                      }
                    },
                  },
                  {
                    label: '合同编号',
                    field: "contractID",
                    type: 'select',
                    showSearch: true,
                    span: 12,
                    required: true,
                    addShow:true,
                    editShow:false,
                    detailShow:false,
                    placeholder: '请选择',
                    optionConfig: {
                      label: 'contractNo',
                      value: 'contractID',
                      linkageFields: {
                        contractName: 'contractName',
                        secondName: 'secondName',
                        secondID: 'secondID',
                        isDeduct: 'isDeduct',
                        contractNo: 'contractNo'
                      }
                    },
                    onChange: (val, obj) => {
                      if (obj?.itemData?.contractID) {
                        let pageData = obj.form.getFieldsValue()
                        let params = {}
                        params.orgID = pageData.orgID
                        params.contractID = val
                        this.props.myFetch('generateZxSaEquipSettleAuditAutoNum', params).then(
                          ({ data, success, message }) => {
                            if (success) {
                              if (pageData.periodDate) {
                                const PeriodDate = moment(pageData.periodDate).format('YY-MM').replace('-', '');
                                let billNo = '';
                                let signedNos = '';
                                let autoNumLength = data.autoNum.toString().split('').length;
                                let autoNum = autoNumLength === 1 ? '0' + (data.autoNum + 1) : data.autoNum + 1
                                billNo = `${pageData.contractNo}-${PeriodDate}-${autoNum}`;
                                signedNos = `${pageData.contractNo}-SL-${PeriodDate}-${autoNum}`;
                                obj.form.setFieldsValue({
                                  billNo,
                                  signedNos
                                })
                              }
                              obj.form.setFieldsValue({
                                autoNum: data.autoNum + 1,
                                settleTypeFlag: data.settleTypeFlag,
                                isFirst: data.autoNum === 0 ? '1' : '0'
                              })
                              obj.funcCallBackParams.refresh();
                              obj.form.setFieldsValue({ billType: null })
                            } else {
                              Msg.error(message);
                            }
                          }
                        );
                      } else {
                        obj.form.setFieldsValue({
                          billNo: '',
                          signedNos: "",
                          autoNum: 1,
                          billType: null,
                          settleTypeFlag: "false"
                        })
                        obj.funcCallBackParams.refresh();
                      }
                    },
                    condition: [
                      {
                        regex: { zxSaEquipSettleAuditId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    parent: 'orgID',
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0') {
                        let rowData = obj.form.getFieldsValue();
                        if (rowData?.orgID) {
                          return {
                            apiName: 'getZxCtEqContractListByOrgId',
                            otherParams: {
                              orgID: rowData.orgID
                            }
                          }
                        }
                      }
                    },
                  },
                  {
                    label: '结算单编号',
                    field: "billNo",
                    type: 'string',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '合同名称',
                    field: "contractName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                  },
                  {
                    label: '结算签认单编号',
                    field: "signedNos",
                    type: 'string',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '合同乙方',
                    field: "secondName",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '结算期次',
                    field: "periodDate",
                    type: 'month',
                    placeholder: '请选择',
                    required: true,
                    span: 12,
                    condition: [
                      {
                        regex: { zxSaEquipSettleAuditId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      const pageData = obj.form.getFieldsValue();
                      const PeriodDate = moment(val).format('YY-MM').replace('-', '');
                      if (pageData.autoNum && pageData.contractID) {
                        let autoNumLength = pageData.autoNum.toString().split('').length;
                        let autoNum = autoNumLength === 1 ? '0' + (pageData.autoNum) : pageData.autoNum
                        const billNo = `${pageData.contractNo}-${PeriodDate}-${autoNum}`;
                        const signedNos = `${pageData.contractNo}-SL-${PeriodDate}-${autoNum}`;
                        obj.form.setFieldsValue({
                          billNo,
                          signedNos,
                        })
                      }
                    }
                  },
                  {
                    label: '结算类型',
                    field: "billType",
                    type: 'select',
                    required: true,
                    placeholder: '请选择',
                    span: 12,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    condition: [
                      {
                        regex: { zxSaEquipSettleAuditId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    optionData: (obj) => {
                      let objData = obj?.form?.getFieldsValue()
                      if (objData.isFished === '1' || objData.settleTypeFlag === 'true' || objData.finishStatus === '1' || objData.billType === '2') {
                        return [
                          { label: '返还保证金', value: '2' }
                        ]
                      } else {
                        return [
                          { label: '中期', value: '0' },
                          { label: '最终', value: '1' },
                        ]
                      }
                    },
                  },
                  {
                    label: '是否抵扣',
                    field: "isDeduct",
                    type: 'radio',
                    disabled: true,
                    optionData: [
                      { label: '是', value: '1' },
                      { label: '否', value: '0' }
                    ],
                    initialValue: '0',
                    span: 8,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 12 },
                        sm: { span: 12 }
                      },
                      wrapperCol: {
                        xs: { span: 8 },
                        sm: { span: 8 }
                      }
                    }
                  },
                  {
                    label: '是否完工后结算',
                    field: "isFished",
                    type: 'radio',
                    optionData: [
                      { label: '是', value: '1' },
                      { label: '否', value: '0' }
                    ],
                    initialValue: '0',
                    span: 8,
                    editDisabled:true,
                    onChange: (val, obj) => {
                      obj.funcCallBackParams.refresh();
                      obj.form.setFieldsValue({ billType: null })
                    },
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 12 },
                        sm: { span: 12 }
                      },
                      wrapperCol: {
                        xs: { span: 8 },
                        sm: { span: 8 }
                      }
                    }
                  },
                  {
                    label: '是否首次结算',
                    field: "isFirst",
                    type: 'radio',
                    optionData: [
                      { label: '是', value: '1' },
                      { label: '否', value: '0' }
                    ],
                    initialValue: '0',
                    disabled: true,
                    span: 8,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 12 },
                        sm: { span: 12 }
                      },
                      wrapperCol: {
                        xs: { span: 8 },
                        sm: { span: 8 }
                      }
                    }
                  },
                  {
                    label: '结算单初始化顺序号',
                    field: "autoNum",
                    required: true,
                    editDisabled:true,
                    condition: [
                      {
                        regex: { isFirst: '0' },
                        action: 'disabled',
                      },
                    ],
                    span: 12,
                    placeholder: '请输入',
                    type: 'number',
                    min: 1,
                    precision: 0,
                    formatter: (value) => {
                      return Number(value) < 10 ? `0${Number(value)}` : value;
                    },
                    parser: (value) => {
                      return Number(value) < 10 ? value.replace('0', '') : value;
                    },
                    onChange: (val, obj) => {
                      const pageData = obj.form.getFieldsValue();
                      if (pageData.contractID && pageData.periodDate) {
                        const PeriodDate = moment(pageData.periodDate).format('YY-MM').replace('-', '');
                        const billNo = `${pageData.contractNo}-${PeriodDate}-${val}`;
                        const signedNos = `${pageData.contractNo}-SL-${PeriodDate}-${val}`;
                        obj.form.setFieldsValue({
                          billNo,
                          signedNos,
                        })
                      }
                    }
                  },
                  {
                    label: '填报人',
                    field: "reportPerson",
                    type: 'string',
                    span: 12,
                    placeholder: '请输入',
                    initialValue: () => {
                      let reportPerson = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                      return reportPerson
                    }
                  },
                  {
                    label: '计算人',
                    field: "countPerson",
                    type: 'string',
                    span: 12,
                    placeholder: '请输入',
                  },
                  {
                    label: '复核人',
                    field: "reCountPerson",
                    type: 'string',
                    span: 12,
                    placeholder: '请输入',
                  },
                  {
                    label: '填报日期',
                    field: "reportDate",
                    span: 12,
                    type: 'date',
                    required: true,
                  },
                  {
                    label: '业务日期',
                    field: "businessDate",
                    span: 12,
                    type: 'date',
                  },
                  {
                    label: '结算期限开始时间',
                    field: "beginDate",
                    span: 12,
                    type: 'date',
                    required: true,
                  },
                  {
                    label: '结算期限结束时间',
                    field: "endDate",
                    span: 12,
                    type: 'date',
                    required: true,
                  },
                  {
                    label: '机械性能及运转情况评价',
                    field: 'appraisal',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                  },
                  {
                    label: '结算内容及说明',
                    field: 'content',
                    type: 'textarea',
                    span: 12,
                    placeholder: '请输入',
                  },
                  {
                    label: '备注信息',
                    field: 'remark',
                    type: 'textarea',
                    span: 12,
                    placeholder: '请输入',
                  },
                  {
                    label: '附件',
                    field: 'zxErpFileList',
                    type: 'files',
                    span: 12,
                    fetchConfig: {
                      apiName: 'upload'
                    },
                  },
                ]
              }
            },
            {
              name: "diyComponent",
              field: "TabTwoList",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSaEquipSettleAuditId"))
              },
              title: "清单结算",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                let paramsData = {}
                paramsData.clickName = obj.clickCb.rowInfo.name
                paramsData.contractID = rowData.contractID ? rowData.contractID : ''
                paramsData.zxSaEquipResSettleId = rowData.zxSaEquipResSettleId ? rowData.zxSaEquipResSettleId : ''
                paramsData.zxSaEquipSettleAuditId = rowData.zxSaEquipSettleAuditId ? rowData.zxSaEquipSettleAuditId : ''
                paramsData.tabIndex = obj.tableFns.getActiveKey()
                paramsData.billType = rowData.billType ? rowData.billType : '';                               //结算类型
                params.paramsData = paramsData
                return <TabTwoList {...params} />;

              }
            },
            {
              name: "diyComponent2",
              field: "TabThreeList",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSaEquipSettleAuditId"))
              },
              title: "支付项结算",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                let paramsData = {}
                paramsData.zxSaEquipPaySettleId = rowData.zxSaEquipPaySettleId ? rowData.zxSaEquipPaySettleId : ''
                paramsData.zxSaEquipSettleAuditId = rowData.zxSaEquipSettleAuditId ? rowData.zxSaEquipSettleAuditId : ''
                paramsData.totalAmtPay = rowData.totalAmtPay ? rowData.totalAmtPay : 0
                paramsData.orgID = rowData.orgID ? rowData.orgID : ''
                paramsData.period = moment(rowData?.periodDate).format('YYYY-MM').replace('-', '')
                paramsData.billNo = rowData?.billNo
                paramsData.contractID = rowData?.contractID
                paramsData.clickName = obj.clickCb.rowInfo.name
                paramsData.tabIndex = obj.tableFns.getActiveKey()
                paramsData.billType = rowData.billType ? rowData.billType : '';     
                params.paramsData = paramsData
                return <TabThreeList {...params} />;
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "统计项",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSaEquipSettleAuditId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxSaEquipSettleAuditItemList',
                    otherParams: (obj) => {
                      return {
                        zxSaEquipSettleAuditId: obj.tableFns.form.getFieldValue('zxSaEquipSettleAuditId')
                      }
                    }
                  }
                },
                antd: {
                  size: "small",
                  rowKey: "zxSaEquipSettleAuditItemId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxSaEquipSettleAuditItemId !== this.state.lineEdit
                },
                wrappedComponentRef: (me) => {
                  this.tableFour = me;
                },
                paginationConfig: false,
                pageSize: 999999,
                isShowRowSelect: false,
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxSaEquipSettleAuditItemId',
                      type: 'string',
                      hide: true,
                    }
                  },

                  {
                    table: {
                      title: '统计项',
                      dataIndex: 'statisticsName',
                      key: 'statisticsName',
                      width: 100,
                      type: 'string',
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '本期（元）',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      width: 150,
                      type: 'string',
                      tdEdit: true,
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
                    },
                    form: {
                      field: 'thisAmt',
                      type: 'string',
                      placeholder: '请输入',
                      precision: 2,

                    }
                  },
                  {
                    table: {
                      title: '开累（元）',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      width: 100,
                      type: 'string',
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
                    },
                    form: {
                      type:'string'
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
                      width: 80,
                      title: "操作",
                      key: "action",//操作列名称
                      fixed: 'right',//固定到右边
                      align: 'center',
                      render: (data, rowData) => {
                        if ((rowData.zxSaEquipSettleAuditItemId === this.state.lineEdit) && rowData.baseFlag === 'true') {
                          return <a>保存</a>;
                        } else if (rowData.baseFlag === 'true') {
                          return <a>编辑</a>;
                        } else {
                          return null
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxSaEquipSettleAuditItemId) {
                          Toast.loading('loading...');
                          obj.btnCallbackFn.getEditedRowData().then((data) => {
                            let body = {
                              ...obj.rowData,
                              ...data
                            };
                            if (!data.thisAmt || isNaN(data.thisAmt)) {
                              Msg.warn('请输入正确的金额')
                              Toast.hide();
                              return
                            }
                            this.props.myFetch('updateZxSaEquipSettleAuditItem', body).then(
                              ({ success, message }) => {
                                if (success) {
                                  Msg.success(message);
                                  Toast.hide();
                                  this.setState({
                                    lineEdit: ''
                                  })
                                  obj.btnCallbackFn.refresh();
                                } else {
                                  Toast.hide();
                                  Msg.error(message);
                                }
                              }
                            );
                          })
                        } else {
                          let data = this.tableFour.state ? this.tableFour.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].zxSaEquipSettleAuditItemId === this.state.lineEdit) {
                              Msg.error('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxSaEquipSettleAuditItemId
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
    )
  }
}

export default index;