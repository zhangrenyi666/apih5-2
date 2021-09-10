import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import { Toast } from 'antd-mobile';
import AffiliatFinalStatementForm from './form';
import ManageView from './manageView';
import DetailView from './detail';
import TabThreeList from './TabThree';
import TabTwoList from './TabTwo';
import Operation from './operation'
import moment from 'moment';
const config = {
  antd: {
    rowKey: 'zxSaFsSettlementId',
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
      xs: { span: 7 },
      sm: { span: 7 }
    },
    wrapperCol: {
      xs: { span: 17 },
      sm: { span: 17 }
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
            apiName: 'getZxSaFsSettlementList',
            otherParams: { orgID: departmentId }
          }}

          componentsKey={{
            AffiliatFinalStatementForm: (obj) => {
              return <AffiliatFinalStatementForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            },
            ManageView: (obj) => {
              return <ManageView {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            },
            DetailView: (obj) => {
              return <DetailView {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            },
          }}
          tabsWillChange={(obj, canChange) => {
            if (this.tableFour) {
              this.tableFour.refresh();
            }
            canChange(true);
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              console.log('11111');
              this.table.refresh()
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
                    this.props.myFetch('addZxSaFsSettlement', obj._formData).then(
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
                    this.props.myFetch('updateZxSaFsSettlement', obj._formData).then(
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
                apiName: 'batchDeleteUpdateZxSaFsSettlement',
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
              Component: "AffiliatFinalStatementForm",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "附属类结算单",
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
              drawerTitle: "附属类结算会签单预览",
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
                tooltip: 20,
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
                tooltip: 15,
              }
            },
            {
              table: {
                title: '结算期次',
                dataIndex: 'periodTime',
                key: 'periodTime',
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
                width: 180,
                tooltip: 18,
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
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'thisAmt',
                    type: 'number',
                    hide: true,
                    initialValue: 0
                  },
                  {
                    field: 'totalAmt',
                    type: 'number',
                    hide: true,
                    initialValue: 0
                  },
                  {
                    field: 'thisPayAmt',
                    type: 'number',
                    hide: true,
                    initialValue: 0
                  },
                  {
                    field: 'totalPayAmt',
                    type: 'number',
                    hide: true,
                    initialValue: 0
                  },
                  {
                    field: 'apih5FlowStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxSaFsSettlementId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'settleType',
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
                    label: '项目名称',
                    field: "orgName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '合同编号',
                    field: "contractNo",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '项目名称',
                    field: "orgID",
                    type: 'select',
                    placeholder: '请选择',
                    showSearch: true,
                    span: 12,
                    required: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
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
                    onChange: (val, obj) => {
                      obj.funcCallBackParams.refresh();
                      obj.form.setFieldsValue({
                        zxCtFsContractId: null
                      })
                    },
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0') {
                        return {
                          apiName: 'getZxSaProjectUnFinishList',
                          otherParams: { orgID: departmentId, settleTypeCode: 'P5' }
                        }
                      }
                    },
                  },
                  {
                    label: '合同编号',
                    field: "zxCtFsContractId",
                    type: 'select',
                    showSearch: true,
                    span: 12,
                    required: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    placeholder: '请选择',
                    optionConfig: {
                      label: 'contractNo',
                      value: 'zxCtFsContractId',
                      linkageFields: {
                        contractName: 'contractName',
                        secondName: 'secondName',
                        isDeduct: 'isDeduct',
                        contractNo: 'contractNo',
                        settleType: 'settleType'
                      }
                    },
                    parent: 'orgID',
                    onChange: (val, obj) => {
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        let params = {}
                        params.zxCtFsContractId = obj.itemData.zxCtFsContractId
                        this.props.myFetch('getZxSaFsSettlementCount', params).then(
                          ({ data, success, message }) => {
                            if (success) {
                              let initSerialNumberL = Number(data.initSerialNumber).toString().split('').length
                              let initSerialNumber = initSerialNumberL === 1 ? '0' + (Number(data.initSerialNumber) + 1) : (Number(data.initSerialNumber) + 1) + ''
                              if (pageData.periodTime) {
                                const PeriodTime = moment(pageData.periodTime).format('YY-MM').replace('-', '');
                                let billNo = '';
                                let signedNo = '';
                                billNo = `${pageData.contractNo}-${PeriodTime}-${initSerialNumber}`;
                                signedNo = `${pageData.contractNo}-SL-${PeriodTime}-${initSerialNumber}`;
                                obj.form.setFieldsValue({
                                  billNo,
                                  signedNo
                                })
                              }
                              obj.form.setFieldsValue({
                                initSerialNumber,
                                isFirst: Number(data.initSerialNumber) === 0 ? '1' : '0'
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
                          signedNo: "",
                          initSerialNumber: '01',
                          billType: null
                        })
                        obj.funcCallBackParams.refresh();
                      }
                    },
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0') {
                        let rowData = obj.form.getFieldsValue();
                        if (rowData?.orgID) {
                          return {
                            apiName: 'getZxCtFsContractXlList',
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
                    placeholder: '请选择',
                  },
                  {
                    label: '结算签认单编号',
                    field: "signedNo",
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
                    field: "periodTime",
                    type: 'month',
                    placeholder: '请选择',
                    required: true,
                    span: 12,
                    condition: [
                      {
                        regex: { zxSaFsSettlementId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      const pageData = obj.form.getFieldsValue();
                      const PeriodTime = moment(val).format('YY-MM').replace('-', '');
                      if (val && pageData.zxCtFsContractId && pageData.initSerialNumber) {
                        const billNo = `${pageData.contractNo}-${PeriodTime}-${pageData.initSerialNumber}`;
                        const signedNo = `${pageData.contractNo}-SL-${PeriodTime}-${pageData.initSerialNumber}`;
                        obj.form.setFieldsValue({
                          billNo,
                          signedNo,
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
                        regex: { zxSaFsSettlementId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    optionData: (obj) => {
                      let objData = obj?.form?.getFieldsValue()
                      if (objData.isFished === '1' || objData.settleType === '已最终结算' || objData.finishStatus === '1' || objData.billType === '2') {
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
                    condition: [
                      {
                        regex: { zxSaFsSettlementId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
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
                    field: "initSerialNumber",
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
                    type: 'string',
                    onBlur: (obj) => {
                      const pageData = this.table.qnnForm.form.getFieldsValue();
                      let val = obj.target.defaultValue;
                      if (isNaN(val) || !(/(^[0-9]\d*$)/.test(val)) || val.split('').length > 2 || Number(val) < 1) {
                        Msg.warn('请输入正确的结算单初始化顺序号!')
                        this.table.qnnForm.form.setFieldsValue({
                          initSerialNumber: '',
                          billNo: '',
                          signedNo: '',
                        })
                        return
                      }
                      let initSerialNumberL = val.toString().split('').length
                      val = initSerialNumberL === 1 && val !== '0' ? '0' + val : val
                      this.table.qnnForm.form.setFieldsValue({
                        initSerialNumber: val
                      })
                      if (pageData.zxCtFsContractId && pageData.periodTime) {
                        const PeriodTime = moment(pageData.periodTime).format('YY-MM').replace('-', '');
                        const billNo = `${pageData.contractNo}-${PeriodTime}-${val}`;
                        const signedNo = `${pageData.contractNo}-SL-${PeriodTime}-${val}`;
                        this.table.qnnForm.form.setFieldsValue({
                          billNo,
                          signedNo,
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
                    label: '验收情况',
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
                    field: 'remarks',
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
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSaFsSettlementId"))
              },
              title: "清单结算",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                params.clickName = obj.clickCb.rowInfo.name
                params.tabIndex = obj.tableFns.getActiveKey()
                params.zxSaFsSettlementId = rowData?.zxSaFsSettlementId //主键
                params.billType = rowData?.billType;                               //结算类型
                return <TabTwoList {...params} />;
              }
            },
            {
              name: "diyComponent2",
              field: "TabThreeList",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSaFsSettlementId"))
              },
              title: "支付项结算",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                params.clickName = obj.clickCb.rowInfo.name
                params.tabIndex = obj.tableFns.getActiveKey()
                params.zxSaFsSettlementId = rowData?.zxSaFsSettlementId
                params.billType = rowData?.billType;  
                params.orgID = rowData?.orgID
                return <TabThreeList {...params} />;
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "统计项",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxSaFsSettlementId"))
              },
              content: {
                fetchConfig: () => {
                  return {
                    apiName: 'getZxSaFsStatisticsDetailList',
                    otherParams: (obj) => {
                      return {
                        zxSaFsSettlementId: obj.tableFns.form.getFieldValue('zxSaFsSettlementId')
                      }
                    }
                  }
                },
                antd: {
                  size: "small",
                  rowKey: "zxSaFsStatisticsDetailId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.zxSaFsStatisticsDetailId !== this.state.lineEdit
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
                      field: 'zxSaFsStatisticsDetailId',
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
                      tdEdit: true,
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
                      form: {
                        type: 'string',
                        placeholder: '请输入',
                        precision: 2,
                      }
                    },
                  },
                  {
                    table: {
                      title: '开累（元）',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      width: 100,
                      align: 'center',
                      render: (val) => {
                        if (val && !isNaN(val)) {
                          return Number(val).toFixed(2)
                        } else {
                          return val
                        }
                      },
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
                      width: 80,
                      title: "操作",
                      key: "action",//操作列名称
                      fixed: 'right',//固定到右边
                      align: 'center',
                      render: (data, rowData) => {
                        if ((rowData.zxSaFsStatisticsDetailId === this.state.lineEdit) && rowData.sort === 2) {
                          return <a>保存</a>;
                        } else if (rowData.sort === 2) {
                          return <a>编辑</a>;
                        } else {
                          return null
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.zxSaFsStatisticsDetailId) {
                          Toast.loading('loading...');
                          obj.btnCallbackFn.getEditedRowData().then((data) => {
                            if (!data.thisAmt || isNaN(data.thisAmt)) {
                              Msg.warn('请输入正确的金额')
                              Toast.hide();
                              return
                            }
                            let body = {
                              ...obj.rowData,
                              ...data
                            };
                            body.zxCtFsContractId = obj.props.tableFns.form.getFieldValue('zxCtFsContractId')
                            this.props.myFetch('updateZxSaFsStatisticsDetail', body).then(
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
                            if (data[i].zxSaFsStatisticsDetailId === this.state.lineEdit) {
                              Msg.error('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.zxSaFsStatisticsDetailId
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