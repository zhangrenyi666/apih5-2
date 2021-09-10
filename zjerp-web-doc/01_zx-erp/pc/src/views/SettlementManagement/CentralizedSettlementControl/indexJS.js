import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import ManageView from './manageView';
import TabThreeList from './tabThree';
import TabTwoList from './tabTwo';
import DetailView from './detail';
import moment from 'moment';
const config = {
  antd: {
    rowKey: 'id',
    size: 'small'
  },
  drawerConfig: {
    width: '70%'
  },
  paginationConfig: {
    position: 'bottom'
  },
  isShowRowSelect:false,
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
  render() {
    const { departmentId, period, remark } = this.props;
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
            apiName: 'getZxSaSettleControlLedgerGcsgList',
            otherParams: { orgID: departmentId, period: period, remark:remark }
          }}
          componentsKey={{
            ManageView: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <ManageView {...this.props} flowData={data[0]} />
            },
            DetailView: (obj) => { 
              let data = obj.qnnTableInstance.getSelectedRows()
              return <DetailView {...this.props} flowData={data[0]} btnCallbackFn={obj.btnCallbackFn} />
            },
          }}
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
                tooltip: 18,
              }
            },
            {
              table: {
                title: '结算期次',
                dataIndex: 'periodTimeWasted',
                key: 'periodTimeWasted',
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
                width: 180,
                tooltip: 10,
              }
            },
            {
              table: {
                title: '合同乙方',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 180,
                tooltip: 10,
                filter: true,
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
              title: "基础信息",
              content: {
                formConfig: [
                  {
                    field: 'period',
                    type: 'number',
                    hide: true,
                  },
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
                    field: 'settleType',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'id',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyName',
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
                        companyId: 'comID',
                        companyName: 'comName',
                        finishStatus: 'finishStatus'
                      }
                    },
                    editDisabled: true,
                    onChange: (val, obj) => {
                      obj.form.setFieldsValue({
                        billNo: '',
                        signedNo: "",
                        initSerialNumber: '01',
                        billType: null
                      })
                    },
                    fetchConfig: (obj) => {
                      if (obj.btnCallbackFn.getActiveKey() === '0') {
                        return {
                          apiName: 'getZxSaProjectUnFinishList',
                          otherParams: { orgID: departmentId, settleTypeCode: 'P2' }
                        }
                      }
                      return {}
                    },
                  },
                  {
                    label: '合同编号',
                    field: "contractID",
                    type: 'select',
                    showSearch: true,
                    span: 12,
                    parent: 'orgID',
                    required: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    placeholder: '请选择',
                    optionConfig: {
                      label: 'contractNo',
                      value: 'contractID',
                      linkageFields: {
                        contractName: 'contractName',
                        secondID: 'secondID',
                        secondName: 'secondName',
                        isDeduct: 'isDeduct',
                        contractNo: 'contractNo',
                        contractAmt: 'contractCost',
                        changeAmt: 'alterContractSumTax',
                        settleType: 'settleType'
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        const selectItem = obj.itemData
                        let PeriodDate = ''
                        let billNo = ''
                        let signedNo = ''
                        let statementNo = selectItem.statementNo.toString().split('').length === 1 ? '0' + (selectItem.statementNo + 1) : '' + (selectItem.statementNo + 1);
                        let isFirst = selectItem.statementNo === 0 ? '1' : '0'
                        if (pageData.periodTimeWasted) {
                          PeriodDate = moment(pageData.periodTimeWasted).format('YY-MM').replace('-', '');
                          billNo = `${pageData.contractNo}-${PeriodDate}-${statementNo}`;
                          signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${statementNo}`;
                        }
                        obj.form.setFieldsValue({
                          billNo,
                          signedNo,
                          initSerialNumber: statementNo,
                          isFirst,
                          billType: null
                        })
                      } else {
                        obj.form.setFieldsValue({
                          billNo: '',
                          signedNo: "",
                          initSerialNumber: '01',
                          billType: null
                        })
                      }

                    },
                    fetchConfig: (obj) => {
                      if (obj.btnCallbackFn.getActiveKey() === '0') {
                        return {
                          apiName: 'getZxSaProjectSettleAuditContractNoList',
                          otherParams: (val) => {
                            return {
                              orgID: val.form.getFieldValue('orgID'),
                            }
                          }
                        }
                      }
                      return {}
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
                    label: '签认单编号',
                    field: "signedNo",
                    type: 'string',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '合同乙方',
                    field: "secondName",
                    disabled: true,
                    span: 12,
                    type: 'string',
                  },
                  {
                    label: '结算期次',
                    field: "periodTimeWasted",
                    type: 'month',
                    placeholder: '请选择',
                    required: true,
                    span: 12,
                    editDisabled: true,
                    onChange: (val, obj) => {
                      const pageData = obj.form.getFieldsValue();
                      const PeriodDate = moment(val).format('YY-MM').replace('-', '');
                      if (pageData.contractID) {
                        let statementNo = pageData.initSerialNumber.toString().split('').length === 1 ? '0' + (pageData.initSerialNumber) : pageData.initSerialNumber;
                        const billNo = `${pageData.contractNo}-${PeriodDate}-${statementNo}`;
                        const signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${statementNo}`;
                        obj.form.setFieldsValue({
                          billNo,
                          signedNo,
                          period: moment(val).valueOf()
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
                    editDisabled: true,
                    dependencies: ['isFished', 'settleType', 'finishStatus'],
                    dependenciesReRender: true,
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
                    editDisabled: true,
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
                    editDisabled: true,
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
                      if (pageData.contractID && pageData.periodTimeWasted) {
                        const PeriodDate = moment(pageData.periodTimeWasted).format('YY-MM').replace('-', '');
                        const billNo = `${pageData.contractNo}-${PeriodDate}-${val}`;
                        const signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${val}`;
                        this.table.qnnForm.form.setFieldsValue({
                          billNo,
                          signedNo,
                        })
                      }
                    }
                  },
                  {
                    label: '复核人',
                    field: "reCountPerson",
                    type: 'string',
                    span: 12,
                    placeholder: '请输入',
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
                    label: '填报人电话',
                    field: "reportPersonTel",
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
                    label: '施工内容',
                    field: 'content',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                  },
                  {
                    label: '工程质量评价',
                    field: 'appraisal',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
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
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("id"))
              },
              title: "清单结算",
              content: obj => {
                console.log(obj);
                let params = { ...this.props }
                let rowData = obj.tableFns.qnnForm.form.getFieldsValue()
                let paramsData = {}
                paramsData.billNo = rowData.billNo ? rowData.billNo : ''
                paramsData.billType = rowData?.billType
                paramsData.clickName = obj.clickCb.rowInfo.name
                paramsData.contractID = rowData.contractID
                paramsData.id = rowData.id
                paramsData.tabIndex = obj.state.tabsIndex
                params.paramsData = paramsData
                return <TabTwoList {...params} />;
              }
            },
            {
              name: "diyComponent2",
              field: "TabThreeList",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("id"))
              },
              title: "支付项结算",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.tableFns.qnnForm.form.getFieldsValue()
                let paramsData = {}
                paramsData.clickName = obj.clickCb.rowInfo.name
                paramsData.id = rowData?.id
                paramsData.orgID = rowData?.orgID
                paramsData.billType = rowData?.billType
                paramsData.tabIndex = obj.state.tabsIndex
                params.paramsData = paramsData
                return <TabThreeList {...params} />;
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "统计项",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("id"))
              },
              content: {
                antd: {
                  size: "small",
                  rowKey: "projectSettleAuditItemId",
                },
                tableTdEdit: true,
                rowDisabledCondition: (rowData) => {
                  return rowData.sort !== 2
                },
                wrappedComponentRef: (me) => {
                  this.tableFour = me;
                },
                paginationConfig: false,
                pageSize: 999999,
                isShowRowSelect: false,
                fetchConfig: {
                  apiName: 'getZxSaProjectSettleAuditItemList',
                  otherParams: (obj) => {
                    return {
                      projectSettleAuditId: obj.tableFns.form.getFieldValue('id')
                    }
                  }
                },
                tableTdEditFetchConfig: async (obj) => {
                  let body = await obj.qnnTableInstance.getEditedRowData()
                  if (!body.thisAmt || isNaN(body.thisAmt)) {
                    Msg.warn('请输入正确的金额')
                    return {}
                  }
                  return {
                    apiName: 'updateZxSaProjectSettleAuditItem',
                    otherParams: { ...body },
                    success: ({ success, message }) => {
                      if (success) {
                        this.tableFour.refresh()
                        Msg.success(message)
                      } else {
                        Msg.error(message)
                      }
                    }
                  }
                },
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'projectSettleAuditItemId',
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
                    },
                    form: {
                      field: 'thisAmt',
                      type: 'string',
                      placeholder: '请输入',
                      precision: 2
                    }
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