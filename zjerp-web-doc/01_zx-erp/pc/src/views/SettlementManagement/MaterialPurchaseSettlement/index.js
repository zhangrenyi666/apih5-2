import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Button, Drawer, Modal } from 'antd';
import MaterialPurchaseSettlementForm from './form';
import ManageView from './manageView';
import DetailView from './detail';
import moment from 'moment';
import Operation from './operation'
import QnnForm from "../../modules/qnn-form";
const config = {
  antd: {
    rowKey: 'zxCtSuppliesShopSettlementId',
    size: 'small'
  },
  desc: '本页面所有金额单位为: 元',
  drawerConfig: {
    width: '70%'
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
const drawercConfig = {
  antd: {
    rowKey: 'id',
    size: 'small'
  },
  paginationConfig: false,
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
  isShowRowSelect: true
};
class index extends Component {
  constructor(props) {
    super(props);
    const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
    this.state = {
      showModel: false,
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
    }
  }

  handleCancelBjdh = () => {
    this.setState({ showModel: false });
  }
  render() {
    const { departmentId, showModel } = this.state;
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
            apiName: 'getZxCtSuppliesShopSettlementListListByOrgId',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
            }
          }}
          desc='本页面所有金额单位为：元'
          tabsWillChange={(obj, canChange) => {
            if (this.tableFour) {
              this.tableFour.refresh();
            }
            canChange(true);
          }}
          componentsKey={{
            MaterialPurchaseSettlementForm: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <MaterialPurchaseSettlementForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={data[0]} />
            },
            ManageView: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <ManageView {...this.props} flowData={data[0]} />
            },
            DetailView: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <DetailView {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={data[0]} />
            },
            Operation: (obj) => {
              return <Operation apiName={'openFlowByReport'} {...this.props} btnCallbackFn={obj.btnCallbackFn} />
            },
          }}
          method={{
            //抽屉取消按钮是否显示
            hideForCancel: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index === '3') return true
              return false
            },
            //新增保存按钮
            hideForaddSubmit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCtSuppliesShopSettlementId')) return true;
              return false;
            },
            //修改保存按钮
            hideForEditSubmit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "0") return true;
              return false;
            },
            //修改按钮禁用
            disabledFunEdit: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                return true;
              } else {
                return false;
              }
            },
            //营改增按钮禁用
            disabledForYGZ: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1) return true
              return false
            },
            //新增保存按钮回调
            addSuccessCb: (obj) => {
              if (obj.response.success) {
                this.table.setDeawerValues({ ...obj.response.data });
                this.table.setTabsIndex('1');
              }
            },
            //修改保存按钮回调
            editSuccessCb: (obj) => {
              if (obj.response.success) {
                this.table.setDeawerValues({ ...obj.response.data });
                this.table.setTabsIndex('1');
              }
            },
            //清单保存按钮
            listSubmitHide: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "1") return true;
              return false;
            },
            //清单保存按钮配置
            listSubmitFetch: async (obj) => {
              let params = {}
              let shopResSettleItemList = obj.curFormData.shopResSettleItemList
              params.shopResSettleItemList = shopResSettleItemList
              params.zxCtSuppliesShopSettlementId = this.table.form.getFieldValue('zxCtSuppliesShopSettlementId')
              return {
                apiName: 'addZxCtSuppliesShopResSettleItemList',
                otherParams: params,
                success: ({ success, message }) => {
                  if (success) {
                    let params = {
                      zxCtSuppliesShopSettlementId: this.table.form.getFieldValue('zxCtSuppliesShopSettlementId'),
                      tabFlag: '1'
                    }
                    this.props.myFetch('getZxCtSuppliesShopSettlementListDetail', params)
                      .then(({ data, success }) => {
                        if (success) {
                          obj.qnnTableInstance.getQnnForm().form.setFieldsValue({
                            contractAmt: data.contractAmt,
                            changeAmt: data.changeAmt,
                            resThisAmt: data.resThisAmt,
                            resThisAmtNoTax: data.resThisAmtNoTax,
                            resThisAmtTax: data.resThisAmtTax,
                            resTotalAmt: data.resTotalAmt,
                            stockBillNos: data.stockBillNos,
                            shopResSettleItemList: data.shopResSettleItemList
                          })
                        }
                      })
                  } else {
                    Msg.error(message);
                  }
                }
              }
            },
            //支付项按钮是否隐藏
            hideForPaySubmit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "2") return true;
              return false;
            },
            //支付项按钮配置
            cbForPaySubmit: async (obj) => {
              let params = {}
              await obj.curFormData.shopPaySettleItemList.map((item) => {
                if (item.addFlag === '1') item.zxCtSuppliesShopPaySettlementItemId = null
                return item
              })
              params.shopPaySettlementList = obj.curFormData.shopPaySettleItemList
              params.zxCtSuppliesShopSettlementId = this.table?.form?.getFieldValue('zxCtSuppliesShopSettlementId')
              return {
                apiName: 'batchAddZxCtSuppliesShopPaySettlementItem',
                otherParams: params,
                success: ({ success, message }) => {
                  if (success) {
                    let params = {
                      zxCtSuppliesShopSettlementId: this.table.form.getFieldValue('zxCtSuppliesShopSettlementId'),
                      tabFlag: '2'
                    }
                    this.props.myFetch('getZxCtSuppliesShopSettlementListDetail', params)
                      .then(({ data, success }) => {
                        if (success) {
                          obj.qnnTableInstance.getQnnForm().form.setFieldsValue({
                            thisAmtByPay: data.thisAmtByPay,
                            thisAmtNoTaxByPay: data.thisAmtNoTaxByPay,
                            thisAmtTaxByPay: data.thisAmtTaxByPay,
                            totalAmtByPay: data.totalAmtByPay,
                            transportAmt: data.transportAmt,
                            padTariffAmt: data.padTariffAmt,
                            fineAmt: data.fineAmt,
                            otherAmt: data.otherAmt,
                            shopPaySettleItemList: data.shopPaySettleItemList
                          })
                        }
                      })
                  } else {
                    Msg.error(message);
                  }
                }
              }
            },
            //进度查询按钮禁用
            disabledFunComponent: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && data[0].workId) {
                return false;
              } else {
                return true;
              }
            },
            //删除按钮禁用
            disabledForDelete: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              let disabledLength = 0
              if (data.length > 0 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                disabledLength++
              }
              if (data.length !== 1 || disabledLength > 0) return true
            },
            //详细按钮禁用筛选
            disabledFunDetail: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                return false;
              } else {
                return true;
              }
            },
            //流程按钮禁用
            disabledForFlow: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1 || data[0].apih5FlowStatus !== '-1') {
                return true;
              } else {
                return false;
              }
            },
            //补录附件禁用
            disabledForSupplementFile: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && data[0].apih5FlowStatus === '1') {
                return false;
              } else {
                return true;
              }
            },
            //补录附件回调
            callBcForSupplementFile: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows()[0];
              if (data.flowBeginPersonId !== this.props.loginAndLogoutInfo.loginInfo.userInfo.userKey) {
                Msg.warn('只有项目拟稿人支持补录附件')
                return
              }
              this.setState({
                showModel: true,
              })
            }
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "MaterialPurchaseSettlement"
              }
            }
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
              }
            },
            {
              table: {
                title: '结算期次',
                dataIndex: 'periodDate',
                key: 'periodDate',
                width: 120,
                format: 'YYYY-MM',
                filter: true,
                fieldsConfig: {
                    type: 'month',
                    field: 'periodDate'
                },
              }
            },
            {
              table: {
                title: '结算类型',
                dataIndex: 'billType',
                key: 'billType',
                width: 120,
                filter: true,
                fieldsConfig: {
                    type: 'select',
                    optionData: [
                        { label: '中期', value: '0' },
                        { label: '最终', value: '1' },
                        { label: '返还保证金', value: '2' },
                    ],
                    optionConfig: {
                        label: 'label',
                        value: 'value',
                    },
                },
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
              }
            },
            {
              table: {
                title: '合同乙方',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
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
                    return '评审未通过';
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
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'period',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'apih5FlowStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtSuppliesShopSettlementId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'settleType',
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
                    field: 'finishStatus',
                    type: 'number',
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
                        comID: 'comID',
                        comName: 'comName',
                        finishStatus: 'finishStatus'
                      }
                    },
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
                          otherParams: { orgID: departmentId, settleTypeCode: 'P5' }
                        }
                      } else { return {} }
                    },
                  },
                  {
                    label: '合同编号',
                    field: "contractID",
                    type: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return 'selectByQnnTable'
                      }
                      return 'string'
                    },
                    showSearch: true,
                    span: 12,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    parent: 'orgID',
                    required: true,
                    editDisabled: true,
                    placeholder: '请选择',
                    optionConfig: {
                      label: 'contractNo',
                      value: 'zxCtSuppliesContractId',
                      linkageFields: {
                        contractNo: 'contractNo',
                        contractName: 'contractName',
                        secondID: 'secondID',
                        secondName: 'secondName',
                        isDeduct: 'isDeduct',
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
                        let initSerialNumber = Number(selectItem.initSerialNumber) < 9 ? '0' + (Number(selectItem.initSerialNumber) + 1) : '' + (Number(selectItem.initSerialNumber) + 1);
                        let isFirst = selectItem.initSerialNumber === 0 ? '1' : '0'
                        if (pageData.periodDate) {
                          PeriodDate = moment(pageData.periodDate).format('YY-MM').replace('-', '');
                          billNo = `${pageData.contractNo}-${PeriodDate}-${initSerialNumber}`;
                          signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${initSerialNumber}`;
                        }
                        obj.form.setFieldsValue({
                          billNo,
                          signedNo,
                          initSerialNumber,
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
                    dropdownMatchSelectWidth: 800,
                    qnnTableConfig: {
                      antd: {
                        rowKey: "zxCtSuppliesContractId"
                      },
                      firstRowIsSearch: false,
                      fetchConfig: {
                        apiName: 'getZxCtSuppliesContractListByOrgId',
                        otherParams: () => {
                          return {
                            orgID: this.table.form.getFieldValue('orgID'),
                            code7: 'WZ',
                            pp9: '1',
                            contractStatus: '1'
                          }
                        }
                      },
                      searchBtnsStyle: "inline",
                      formConfig: [
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "contractNo",
                            title: "合同编号",
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "contractName",
                            title: "合同名称",
                          }
                        },
                      ]
                    }
                  },
                  {
                    label: '结算单编号',
                    field: "billNo",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    required:true,
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
                    disabled: true,
                    required:true,
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
                    field: "periodDate",
                    type: 'month',
                    placeholder: '请选择',
                    required: true,
                    span: 12,
                    editDisabled: true,
                    onChange: (val, obj) => {
                      const pageData = obj.form.getFieldsValue();
                      const PeriodDate = moment(val).format('YY-MM').replace('-', '');
                      if (pageData.contractID) {
                        let initSerialNumber = pageData.initSerialNumber.toString().split('').length === 1 ? '0' + (pageData.initSerialNumber) : pageData.initSerialNumber;
                        const billNo = `${pageData.contractNo}-${PeriodDate}-${initSerialNumber}`;
                        const signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${initSerialNumber}`;
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
                    editDisabled: true,
                    dependencies: ['isFished', 'settleType', 'finishStatus'],
                    dependenciesReRender: true,
                    optionData: (obj) => {
                      let objData = obj?.form?.getFieldsValue()
                      if (objData.isFished === '1' || objData.settleType === '3' || objData.finishStatus === '1' || objData.billType === '2') {
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
                    },
                    onChange: (h, obj) => {
                        obj.form.setFieldsValue({
                            billType: null
                        })
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
                      if (pageData.contractID && pageData.periodDate) {
                        const PeriodDate = moment(pageData.periodDate).format('YY-MM').replace('-', '');
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
                    initialValue: () => {
                      let reportPerson = this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                      return reportPerson
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
                    label: '单据开始时间',
                    field: "startDate",
                    span: 12,
                    type: 'date',
                    required: true,
                  },
                  {
                    label: '单据结束时间',
                    field: "documentsEndTime",
                    span: 12,
                    type: 'date',
                    required: true,
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
                    onChange: (val, obj) => {
                      let formData = obj.form.getFieldsValue()
                      if (val) {
                        if (formData.endDate) {
                          let beginDate = Number(moment(val).format("YYYYMMDD"))
                          let endDate = Number(moment(formData.endDate).format("YYYYMMDD"))
                          if (beginDate > endDate) {
                            Msg.warn('开始时间不能早于结束时间')
                            obj.form.setFieldsValue({
                              beginDate: null
                            })
                          }
                        }
                      }
                    }
                  },
                  {
                    label: '结算期限结束时间',
                    field: "endDate",
                    span: 12,
                    type: 'date',
                    required: true,
                    onChange: (val, obj) => {
                      let formData = obj.form.getFieldsValue()
                      if (val) {
                        if (formData.beginDate) {
                          let beginDate = Number(moment(formData.beginDate).format("YYYYMMDD"))
                          let endDate = Number(moment(val).format("YYYYMMDD"))
                          console.log(beginDate, endDate);
                          if (beginDate > endDate) {
                            Msg.warn('开始时间不能早于结束时间')
                            obj.form.setFieldsValue({
                              endDate: null
                            })
                          }
                        }
                      }
                    }
                  },
                  {
                    label: '结算内容',
                    field: 'content',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                  },
                  {
                    label: '物资质量评价',
                    field: 'appraisal',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                  },
                  {
                    label: '说明',
                    field: 'cwStatusRemark',
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
                ]
              }
            },
            {
              field: "form2",
              name: "qnnForm",
              title: "清单结算",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn?.form?.getFieldValue("zxCtSuppliesShopSettlementId"))
              },
              content: {
                addIsGetData: true,
                fetchConfig: {
                  apiName: 'getZxCtSuppliesShopSettlementListDetail',
                  otherParams: () => {
                    return {
                      zxCtSuppliesShopSettlementId: this.table.form.getFieldValue('zxCtSuppliesShopSettlementId'),
                      tabFlag: '1'
                    }
                  }
                },
                formConfig: [
                  {
                    label: '含税合同金额(万元)',
                    field: "contractAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '变更后含税合同金额(万元)',
                    field: "changeAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '本期清单结算含税金额(元)',
                    field: "resThisAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '本期清单结算不含税金额(元)',
                    field: "resThisAmtNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '本期清单结算税额(元)',
                    field: "resThisAmtTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '累计清单结算含税金额(元)',
                    field: "resTotalAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '单据编号',
                    field: "stockBillNos",
                    type: 'textarea',
                    span: 20,
                    disabled: true,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 5 },
                        sm: { span: 5 }
                      },
                      wrapperCol: {
                        xs: { span: 20 },
                        sm: { span: 20 }
                      }
                    },
                  },
                  {
                    field: "diyButton",
                    type: 'Component',
                    Component: obj => {
                      return <Button
                        style={{
                          background: obj.clickCb.rowInfo.name === 'detail' || obj.btnCallbackFn?.form?.getFieldValue('billType') === '2' ? 'rgb(245,245,245)' : 'rgb(24,144,255)',
                          margin: '12px 5px', borderRadius: 3,
                          color: obj.clickCb.rowInfo.name === 'detail' || obj.btnCallbackFn?.form?.getFieldValue('billType') === '2' ? 'rgb(180,180,180)' : '#fff'
                        }}
                        onClick={() => {
                          if (obj.clickCb.rowInfo.name === 'detail' || obj.btnCallbackFn?.form?.getFieldValue('billType') === '2') {
                            return
                          } else {
                            this.setState({ showDrawer: true })
                            if (this.drawerOne) {
                              this.drawerOne.refresh()
                            }
                          }
                        }}
                      >请选择</Button>;
                    },
                    span: 4,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 0 },
                        sm: { span: 0 }
                      },
                      wrapperCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      }
                    },
                  },
                  {
                    type: 'qnnTable',
                    incToForm: true,
                    field: 'shopResSettleItemList',
                    qnnTableConfig: {
                      rowDisabledCondition: () => {
                        return this.table?.form.getFieldsValue().billType === '2'
                      },
                      antd: {
                        rowKey: 'zxCtSuppliesShopResSettleItemId',
                        size: 'small'
                      },
                      wrappedComponentRef: (me) => {
                        this.tableOne = me;
                      },
                      paginationConfig: false,
                      isShowRowSelect: false,
                      formConfig: [
                        {
                          isInTable: false,
                          form: {
                            label: '主键id',
                            field: 'zxCtSuppliesShopResSettleItemId',
                          }
                        },
                        {
                          isInTable: false,
                          form: {
                            label: '主键id',
                            field: 'thisPrice',
                          }
                        },
                        {
                          table: {
                            title: '收料单编号',
                            dataIndex: 'stockBillNo',
                            key: 'stockBillNo',
                            tdEdit: false,
                            align: 'center',
                            width: 180,
                          }
                        },
                        {
                          table: {
                            title: '物资编码',
                            dataIndex: 'resCode',
                            key: 'resCode',
                            tdEdit: false,
                            align: 'center',
                            width: 180
                          }
                        },
                        {
                          table: {
                            title: '物资名称',
                            dataIndex: 'resName',
                            key: 'resName',
                            tdEdit: false,
                            align: 'center',
                            width: 180
                          }
                        },
                        {
                          table: {
                            title: '规格型号',
                            dataIndex: 'spec',
                            key: 'spec',
                            tdEdit: false,
                            align: 'center',
                            width: 120
                          }
                        },
                        {
                          table: {
                            title: '计量单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            tdEdit: false,
                            align: 'center',
                            width: 100
                          }
                        },
                        {
                          table: {
                            title: '税率(%)',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            tdEdit: false,
                            width: 100,
                            align: 'center',
                            initialValue: '0',
                          },
                        },
                        {
                          table: {
                            title: '本期结算数量',
                            dataIndex: 'thisQty',
                            key: 'thisQty',
                            tdEdit: false,
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          isInTable: () => {
                            if (this.tableOne.props.qnnformData.tabsIndex === '1') {
                              return true
                            }
                            return false
                          },
                          table: {
                            title: '本期结算单价(元)',
                            dataIndex: 'resID',
                            key: 'resID',
                            tdEdit: true,
                            width: 150,
                            align: 'center',
                            type: 'selectByQnnTable'
                          },
                          form: {
                            required: true,
                            type: 'selectByQnnTable',
                            optionConfig: {
                              label: 'price',
                              value: 'zxCtSuppliesContrShopListId'
                            },
                            dropdownMatchSelectWidth: 800,
                            placeholder: '请选择',
                            qnnTableConfig: {
                              antd: {
                                rowKey: "zxCtSuppliesContrShopListId"
                              },
                              selectType: "radio",
                              fetchConfig: {
                                apiName: 'getZxCtSuppliesContrShopListByWorkID',
                                otherParams: () => {
                                  let tableData = this.tableOne?.getEditedRowDataSync()
                                  return {
                                    workNo: tableData.resCode,
                                    workName: tableData.resName,
                                    contractID: this.table?.form.getFieldValue('contractID')
                                  }
                                }
                              },
                              formConfig: [
                                {
                                  isInForm: false,
                                  table: {
                                    dataIndex: "workName",
                                    title: "清单名称",
                                  },
                                },
                                {
                                  isInForm: false,
                                  table: {
                                    dataIndex: "workNo",
                                    title: "物资编码",
                                  },
                                },
                                {
                                  isInForm: false,
                                  table: {
                                    dataIndex: "qty",
                                    title: "数量",
                                  },
                                },
                                {
                                  isInForm: false,
                                  table: {
                                    dataIndex: "price",
                                    title: "材料单价",
                                  },
                                },
                                {
                                  isInForm: false,
                                  table: {
                                    dataIndex: "surplusQty",
                                    title: "剩余结算量",
                                  },
                                },
                                {
                                  isInForm: false,
                                  table: {
                                    dataIndex: "taxRate",
                                    title: "税率",
                                  },
                                }
                              ]
                            },
                            onChange: (val, obj) => {
                              if (val) {
                                let rowData = obj.rowData
                                rowData.thisPrice = obj.itemData.price
                                rowData.thisAmt = obj.itemData.price * rowData.thisQty
                                rowData.thisAmtNoTax = ((rowData.thisQty ? rowData.thisQty : 0) * Number(obj.itemData.price)) / (1 + (obj.itemData.taxRate ? Number(obj.itemData.taxRate) : 0) / 100)  //本期结算不含税金额
                                rowData.thisAmtTax = (rowData.thisQty ? rowData.thisQty : 0) * Number(obj.itemData.price) - ((rowData.thisQty ? rowData.thisQty : 0) * Number(obj.itemData.price)) / (1 + (obj.itemData.taxRate ? Number(obj.itemData.taxRate) : 0) / 100)//本期结算税额
                                rowData.taxRate = obj.itemData.taxRate ? Number(obj.itemData.taxRate) : 0
                                rowData.resID = obj.itemData.zxCtSuppliesContrShopListId
                                obj.qnnTableInstance.setEditedRowData(rowData)
                              }

                            },
                          }
                        },
                        {
                          table: {
                            title: '本期结算含税金额(元)',
                            dataIndex: 'thisAmt',
                            key: 'thisAmt',
                            tdEdit: false,
                            width: 160,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.thisAmt ? Number(rowData.thisAmt).toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '本期结算不含税金额(元)',
                            dataIndex: 'thisAmtNoTax',
                            key: 'thisAmtNoTax',
                            tdEdit: false,
                            width: 170,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.thisAmtNoTax ? Number(rowData.thisAmtNoTax).toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '本期结算税额(元)',
                            dataIndex: 'thisAmtTax',
                            key: 'thisAmtTax',
                            tdEdit: false,
                            width: 150,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.thisAmtTax ? Number(rowData.thisAmtTax).toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '备注',
                            dataIndex: 'remarks',
                            key: 'remarks',
                            tdEdit: true,
                            width: 120,
                            align: 'center',
                          },
                          form: {
                            field: 'remarks',
                            type: 'string',
                            placeholder: '请输入',
                          },
                        }
                      ],
                    }
                  }
                ]
              }
            },
            {
              field: "form3",
              name: "qnnForm",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue("zxCtSuppliesShopSettlementId"))
              },
              title: "支付项结算",
              content: {
                fetchConfig: () => {
                  if (this.table.getTabsIndex() === '2') {
                    return {
                      apiName: 'getZxCtSuppliesShopSettlementListDetail',
                      otherParams: {
                        zxCtSuppliesShopSettlementId: this.table.form.getFieldValue('zxCtSuppliesShopSettlementId'),
                        tabFlag: '2'
                      }
                    }
                  }
                },
                formConfig: [
                  {
                    label: '本期支付项结算含税金额(元)',
                    field: "thisAmtByPay",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '本期支付项结算不含税金额(元)',
                    field: "thisAmtNoTaxByPay",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 9 },
                        sm: { span: 9 }
                      },
                      wrapperCol: {
                        xs: { span: 15 },
                        sm: { span: 15 }
                      }
                    }
                  },
                  {
                    label: '本期支付项结算税额(元)',
                    field: "thisAmtTaxByPay",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '累计支付项结算金额(元)',
                    field: "totalAmtByPay",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 9 },
                        sm: { span: 9 }
                      },
                      wrapperCol: {
                        xs: { span: 15 },
                        sm: { span: 15 }
                      }
                    }
                  },
                  {
                    label: '本期运杂费(元)',
                    field: "transportAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '本期垫资费(元)',
                    field: "padTariffAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 9 },
                        sm: { span: 9 }
                      },
                      wrapperCol: {
                        xs: { span: 15 },
                        sm: { span: 15 }
                      }
                    }
                  },
                  {
                    label: '本期奖罚金(元)',
                    field: "fineAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '本期其他款项(元)',
                    field: "otherAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 9 },
                        sm: { span: 9 }
                      },
                      wrapperCol: {
                        xs: { span: 15 },
                        sm: { span: 15 }
                      }
                    }
                  },
                  {
                    type: 'qnnTable',
                    field: 'shopPaySettleItemList',
                    incToForm: true,
                    qnnTableConfig: {
                      rowDisabledCondition: () => {
                        return this.table.form.getFieldsValue().billType === '2'
                      },
                      antd: {
                        rowKey: 'zxCtSuppliesShopPaySettlementItemId',
                        size: 'small'
                      },
                      paginationConfig: {
                        position: 'bottom'
                      },
                      wrappedComponentRef: (me) => {
                        this.tableTwo = me;
                      },
                      actionBtns: [
                        {
                          name: 'addRow',
                          icon: 'plus',
                          type: 'primary',
                          label: '新增行',
                          addRowDefaultData: { addFlag: '1' },
                          hide: () => {
                            return this.table?.form?.getFieldsValue?.()?.billType === '2'
                          }
                        },
                        {
                          name: 'delRow',
                          icon: 'delete',
                          type: 'danger',
                          label: '删除',
                          isRefreshTable: true,
                          hide: () => {
                            return this.table?.form?.getFieldsValue?.()?.billType === '2'
                          }
                        }
                      ],
                      formConfig: [
                        {
                          isInTable: false,
                          form: {
                            label: '主键id',
                            field: 'zxCtSuppliesShopPaySettlementItemId',
                          }
                        },
                        {
                          isInTable: false,
                          form: {
                            field: 'payName',
                            type: 'string',
                            hide: true
                          }
                        },
                        {
                          table: {
                            title: '支付项类型',
                            dataIndex: 'payType',
                            key: 'payType',
                            tdEdit: false,
                            fixed: 'left',
                            width: 120
                          },
                        },
                        {
                          table: {
                            title: '名称',
                            dataIndex: 'payID',
                            key: 'payID',
                            tdEdit: true,
                            fixed: 'left',
                            width: 150,
                            type: 'select',
                          },
                          form: {
                            required: true,
                            allowClear: false,//是否显示清除按钮
                            type: 'select',
                            showSearch: true,
                            placeholder: '请选择',
                            optionConfig: {
                              label: 'workName',
                              value: 'id',
                            },
                            fetchConfig: {
                              apiName: 'getZxSaProjectSetItemList',
                              otherParams: (obj) => {
                                return {
                                  orgID: obj?.form?.getFieldValue?.('orgID'),
                                  contrType: 'mater'
                                }
                              }
                            },
                            onChange: async (val, obj) => {
                              let tableOneData = obj.rowData
                              let allData = await this.tableTwo.getTableData()
                              for (var i = 0; i < allData.length; i++) {
                                if (obj.rowIndex === i) continue
                                if (allData[i].payID === obj.itemData.id) {
                                  tableOneData.payID = null
                                  tableOneData.payName = null
                                  tableOneData.payType = null
                                  tableOneData.unit = null
                                  await obj.qnnTableInstance.setEditedRowData(tableOneData)
                                  Msg.warn('已存在相同名称，请重试!')
                                  obj.qnnTableInstance.refresh()
                                  return
                                }
                              }
                              tableOneData.payID = val
                              tableOneData.payName = obj.itemData.workName
                              tableOneData.payType = obj.itemData.workType
                              tableOneData.unit = obj.itemData.unit
                              tableOneData.qty = 0
                              tableOneData.price = 0
                              tableOneData.taxRate = 0
                              tableOneData.thisAmt = 0
                              tableOneData.thisAmtNoTax = 0
                              tableOneData.thisAmtTax = 0
                              obj.qnnTableInstance.setEditedRowData(tableOneData)
                            },
                          },
                        },
                        {
                          table: {
                            title: '单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            tdEdit: false
                          },
                        },
                        {
                          table: {
                            title: '数量',
                            dataIndex: 'qty',
                            key: 'qty',
                            tdEdit: true
                          },
                          form: {
                            required: true,
                            precision: 3,
                            field: 'qty',
                            type: 'number',
                            placeholder: '请输入',
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.qty = val ? val : 0
                              if (val < 0) {
                                Msg.warn('数量必须大于0')
                                return
                              }
                              rowData.thisAmt = (rowData.price ? rowData.price : 0) * Number(val)  //本期结算金额
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.thisAmtNoTax = ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)  //本期结算不含税金额
                                rowData.thisAmtTax = (rowData.price ? rowData.price : 0) * Number(val) - ((rowData.price ? rowData.price : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '单价(元)',
                            dataIndex: 'price',
                            key: 'price',
                            tdEdit: true,
                            width: 120
                          },
                          form: {
                            required: true,
                            field: 'price',
                            type: 'number',
                            placeholder: '请输入',
                            precision: 6,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.price = val
                              rowData.thisAmt = (rowData.qty ? rowData.qty : 0) * val  //本期结算含税金额
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * val) / (1 + Number(rowData.taxRate) / 100) //本期结算不含税金额
                                rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * val - ((rowData.qty ? rowData.qty : 0) * val) / (1 + Number(rowData.taxRate) / 100)//本期结算税额
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '税率(%)',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            tdEdit: true,
                            width: 120
                          },
                          form: {
                            required: true,
                            field: 'taxRate',
                            type: 'select',
                            allowClear: false,
                            optionConfig: {
                              label: 'itemName',
                              value: 'itemId',
                            },
                            fetchConfig: {
                              apiName: 'getBaseCodeSelect',
                              otherParams: { itemId: 'shuiLv' }
                            },
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.taxRate = val       //税率
                              rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100)  //本期结算不含税金额
                              rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) - ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100) //本期结算税额
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '本期结算金额(元)',
                            dataIndex: 'thisAmt',
                            key: 'thisAmt',
                            tdEdit: false,
                            width: 150,
                            render: (val, rowData) => {
                              return rowData.thisAmt ? rowData.thisAmt.toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '本期结算不含税金额(元)',
                            dataIndex: 'thisAmtNoTax',
                            key: 'thisAmtNoTax',
                            tdEdit: false,
                            width: 170,
                            render: (val, rowData) => {
                              return rowData.thisAmtNoTax ? rowData.thisAmtNoTax.toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '本期结算税额(元)',
                            dataIndex: 'thisAmtTax',
                            key: 'thisAmtTax',
                            tdEdit: false,
                            width: 140,
                            render: (val, rowData) => {
                              return rowData.thisAmtTax ? rowData.thisAmtTax.toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '备注',
                            dataIndex: 'remarks',
                            key: 'remarks',
                            tdEdit: true,
                            width: 120,
                          },
                          form: {
                            field: 'remarks',
                            type: 'string',
                            placeholder: '请输入',
                          }
                        }
                      ],
                    }
                  }
                ]
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "统计项",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtSuppliesShopSettlementId"))
              },
              content: {
                antd: {
                  size: "small",
                  rowKey: "zxCtSuppliesShopSettlementItemId",
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
                  apiName: 'getZxCtSuppliesShopSettlementItemListByConID',
                  otherParams: (obj) => {
                    return {
                      contractID: obj.tableFns.form.getFieldValue('contractID'),
                      zxCtSuppliesShopSettlementId: obj.tableFns.form.getFieldValue('zxCtSuppliesShopSettlementId')
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
                    apiName: 'updateZxCtSuppliesShopSettlementItem',
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
                      field: 'zxCtSuppliesShopSettlementItemId',
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
                      }
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
                      }
                    }
                  }
                ]
              }
            }
          ]}
        />
        <Modal
          width='80%'
          style={{ top: '0' }}
          title=" 补传附件"
          visible={showModel}
          footer={null}
          onCancel={this.handleCancelBjdh}
          bodyStyle={{ width: '90%', height: '70%' }}
          centered={true}
          destroyOnClose={this.handleCancelBjdh}
          wrapClassName={'modals'}
        >
          <QnnForm
            fetch={this.props.myFetch}
            wrappedComponentRef={(me) => {
              this.FormOne = me;
            }}
            upload={this.props.myUpload}
            fetchConfig={{
              apiName: 'getZxCtSuppliesShopSettlementListDetail',
              otherParams: {
                zxCtSuppliesShopSettlementId: this.table?.getSelectedRows()[0]?.zxCtSuppliesShopSettlementId,
              }
            }}
            formItemLayout={{
              labelCol: {
                xs: { span: 9 },
                sm: { span: 9 }
              },
              wrapperCol: {
                xs: { span: 15 },
                sm: { span: 15 }
              }
            }}
            formConfig={[
              {
                type: 'component',
                field: 'diyButtonsssss',
                span: 24,
                Component: obj => {
                  return (
                    <Button
                      type={'primary'}
                      onClick={() => {
                        this.props.myFetch('zxCtSuppliesShopSettleSubmitFile', {
                          settlementFileList: this.FormOne.form?.getFieldValue('settlementFileList'),
                          zxCtSuppliesShopSettlementId: this.table?.getSelectedRows()[0]?.zxCtSuppliesShopSettlementId,
                        }).then(({ success, message }) => {
                          if (success) {
                            Msg.success(message)
                            this.setState({
                              showModel: false,
                            })
                          } else {
                            Msg.error(message)
                          }
                        })
                      }}
                    >
                      保存
                    </Button>
                  )
                }
              },
              {
                field: 'billNo',
                type: 'string',
                label: '结算单编号',
                span: 12,
                disabled: true,
              },
              {
                field: 'orgName',
                type: 'string',
                label: '项目名称',
                span: 12,
                disabled: true,
              },
              {
                field: 'period',
                type: 'string',
                label: '结算期次',
                span: 12,
                disabled: true,
              },
              {
                field: 'contractNo',
                type: 'string',
                disabled: true,
                label: '合同编号',
                span: 12,
              },
              {
                label: '合同名称',
                field: "contractName",
                type: 'string',
                span: 12,
                disabled: true,
              },
              {
                field: 'secondName',
                type: 'string',
                disabled: true,
                label: '合同乙方',
                span: 12,
              },
              {
                label: '结算类型',
                field: 'billType',
                type: 'select',
                span: 12,
                disabled: true,
                optionConfig: {
                  label: 'label',
                  value: 'value',
                },
                optionData: [
                  { label: '中期', value: '0' },
                  { label: '最终', value: '1' },
                  { label: '返还保证金', value: '2' }
                ],
              },
              {
                label: '是否抵扣',
                field: 'isDeduct',
                type: 'radio',
                optionData: [
                  { label: '是', value: '1' },
                  { label: '否', value: '0' }
                ],
                disabled: true,
                span: 12,
              },
              {
                label: '结算期限开始时间',
                field: "beginDate",
                span: 12,
                type: 'date',
                disabled: true,
              },
              {
                label: '结算期限结束时间',
                field: "endDate",
                span: 12,
                type: 'date',
                disabled: true,
              },
              {
                field: 'content',
                type: 'string',
                disabled: true,
                label: '结算内容及说明',
                span: 12,
              },
              {
                label: '业务日期',
                field: "businessDate",
                span: 12,
                type: 'date',
                disabled: true,
              },
              {
                label: '填报人',
                field: "reportPerson",
                type: 'string',
                span: 12,
                disabled: true,
              },
              {
                type: 'files',
                label: '附件',
                field: 'settlementFileList',
                fetchConfig: {
                  apiName: 'upload'
                },
                span: 12,
              },
              {
                type: 'qnnTable',
                field: 'table1',
                label: '统计项',
                span: 24,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 2 },
                    sm: { span: 2 }
                  },
                  wrapperCol: {
                    xs: { span: 22 },
                    sm: { span: 22 }
                  }
                },
                qnnTableConfig: {
                  rowKey: 'table1',
                  fetchConfig: {
                    apiName: 'getZxCtSuppliesShopSettlementItemListByConID',
                    otherParams: {
                      contractID: this.table?.getSelectedRows()[0]?.contractID,
                      zxCtSuppliesShopSettlementId:this.table?.getSelectedRows()[0]?.zxCtSuppliesShopSettlementId,
                    }
                  },
                  paginationConfig: false,
                  isShowRowSelect: false,
                  pageSize: 999,
                  formConfig: [
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
                  ],
                }
              }
            ]}
          />
        </Modal>
        <Drawer
          title="单据编号选择"
          placement="right"
          closable={true}
          onClose={() => { this.setState({ showDrawer: false }) }}
          visible={this.state.showDrawer}
          width={650}
        >
          <QnnTable
            fetch={this.props.myFetch}
            {...drawercConfig}
            wrappedComponentRef={(me) => {
              this.drawerOne = me;
            }}
            fetchConfig={{
              apiName: 'getZxCtSupReceivingAndReturnListByResID',
              otherParams: () => {
                let drawerData = this.table?.form?.getFieldsValue()
                return {
                  billNo: drawerData?.stockBillNos?.replaceAll('\r\n', ';'),
                  purchaseContractID: drawerData.contractID,
                  // busDate: moment(drawerData.periodDate).valueOf(),
                  mainID: drawerData.zxCtSuppliesShopSettlementId,
                  startTime: moment(drawerData.startDate).valueOf(),
                  endTime: moment(drawerData.documentsEndTime).valueOf(),
                  inOrgID: drawerData.orgID,
                  outOrgID: drawerData.secondID,
                }

              },
              success: (data) => {
                if (data.success === true && data.data && data.data.length > 0) {
                  let selectRowDataList = []
                  for (var i = 0; i < data.data.length; i++) {
                    if (data.data[i].isSelect === '1') selectRowDataList.push(data.data[i])
                  }
                  this.drawerOne.setSelectedRows(selectRowDataList)
                }
              }
            }}
            formConfig={[
              {
                table: {
                  title: '单据编号',
                  dataIndex: 'billNo',
                  key: 'billNo',
                  width: 170,
                }
              },
              {
                table: {
                  title: '业务日期',
                  dataIndex: 'busDate',
                  key: 'busDate',
                  width: 120,
                  format: 'YYYY-MM-DD',
                }
              },
              {
                table: {
                  title: '单据类型',
                  dataIndex: 'bizType',
                  key: 'bizType',
                  width: 120,
                }
              }
            ]}
            actionBtnsPosition={"top"}
            actionBtns={[
              {
                name: 'diy',//内置add del
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '保存',
                onClick: (obj) => {
                  let otherParams = {}
                  let zxCtSuppliesShopResSettleItemList = obj.selectedRows
                  if (zxCtSuppliesShopResSettleItemList && zxCtSuppliesShopResSettleItemList.length > 0) {
                    zxCtSuppliesShopResSettleItemList[0].zxCtSuppliesShopSettlementId = this.table.form?.getFieldValue('zxCtSuppliesShopSettlementId')
                    zxCtSuppliesShopResSettleItemList[0].contractID = this.table.form?.getFieldValue('contractID')
                  }
                  otherParams.zxCtSuppliesShopResSettleItemList = zxCtSuppliesShopResSettleItemList
                  otherParams.zxCtSuppliesShopSettlementId = this.table.form?.getFieldValue('zxCtSuppliesShopSettlementId')
                  otherParams.contractID = this.table.form?.getFieldValue('contractID')
                  this.props.myFetch('addZxCtSuppliesShopResSettleItemByStockId', otherParams).then(
                    ({ data, success, message }) => {
                      if (success) {
                        Msg.success(message);
                        this.setState({ showDrawer: false })
                        obj.btnCallbackFn.refresh()
                        this.table.form.setFieldsValue({
                          stockBillNos: data.stockBillNo,
                          shopResSettleItemList: data.shopResSettleItemList
                        })
                      } else {
                        this.table.form.setFieldsValue({
                          stockBillNos: ''
                        })
                        Msg.error(message);
                      }
                    }
                  );
                }
              }
            ]}
          />
        </Drawer>
      </div>
    )
  }
}

export default index;