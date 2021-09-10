import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Button, Modal } from 'antd';
import OtherSettlementForm from './form';
import ManageView from './manageView';
import DetailView from './detail';
import moment from 'moment';
import Operation from './operation'
import QnnForm from "../../modules/qnn-form";
const config = {
  antd: {
    rowKey: 'zxSaOtherEquipSettleId',
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
class index extends Component {
  constructor(props) {
    super(props);
    const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
    this.state = {
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
            apiName: 'getZxSaOtherEquipSettleList',
            otherParams: { orgId: departmentId }
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
            OtherSettlementForm: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <OtherSettlementForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={data[0]} />
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
              if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxSaOtherEquipSettleId')) return true;
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
              let resSettleItemList = obj.curFormData.resSettleItemList
              params.zxSaOtherEquipResSettleItemList = resSettleItemList
              return {
                apiName: 'batchSaveupdateZxSaOtherEquipResSettleItem',
                otherParams: params,
                success: ({ success }) => {
                  if (success) {
                    let params = {
                      zxSaOtherEquipSettleId: this.table.form.getFieldValue('zxSaOtherEquipSettleId'),
                      resPayFlag: '1'
                    }
                    this.props.myFetch('getZxSaOtherEquipSettleDetail', params)
                      .then(({ data, success }) => {
                        if (success) {
                          obj.qnnTableInstance.getQnnForm().form.setFieldsValue({
                            resContractAmt: data.resContractAmt,
                            resChangeAmt: data.resChangeAmt,
                            resThisAmt: data.resThisAmt,
                            resThisAmtNoTax: data.resThisAmtNoTax,
                            resThisAmtTax: data.resThisAmtTax,
                            resTotalAmt: data.resTotalAmt,
                          })
                        }
                      })
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
              for (var i = 0; i < obj.curFormData.paySettleItemList.length; i++) {
                if (obj.curFormData.paySettleItemList[i].addFlag === '1') {
                  obj.curFormData.paySettleItemList[i].zxSaOtherEquipPaySettleItemId = null
                }
              }
              params.zxSaOtherEquipPaySettleItemList = obj.curFormData.paySettleItemList
              return {
                apiName: 'batchSaveUpdateZxSaOtherEquipPaySettleItem',
                otherParams: params,
                success: ({ success }) => {
                  if (success) {
                    let params = {
                      zxSaOtherEquipSettleId: this.table.form.getFieldValue('zxSaOtherEquipSettleId'),
                      resPayFlag: '2'
                    }
                    this.props.myFetch('getZxSaOtherEquipSettleDetail', params)
                      .then(({ data, success }) => {
                        if (success) {
                          obj.qnnTableInstance.getQnnForm().form.setFieldsValue({
                            payThisAmt: data.payThisAmt,
                            payThisAmtNoTax: data.payThisAmtNoTax,
                            payThisAmtTax: data.payThisAmtTax,
                            payTotalAmt: data.payTotalAmt,
                            paySettleItemList: data.paySettleItemList
                          })
                        }
                      })
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
                tableField: "OtherSettlement"
              }
            }
          }}
          formConfig={[
            {
              table: {
                title: '公司名称',
                dataIndex: 'companyName',
                key: 'companyName',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 150,
              }
            },
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
                title: '填报人',
                dataIndex: 'reportPerson',
                key: 'reportPerson',
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
              title: "信息登记",
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
                    field: 'zxSaOtherEquipSettleId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'settleType',
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
                    field: "orgId",
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
                        companyId: 'comID',
                        companyName: 'comName',
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
                          otherParams: { orgID: departmentId, settleTypeCode: '21' }
                        }
                      }
                      return {}
                    },
                  },
                  {
                    label: '合同编号',
                    field: "zxCtOtherManageId",
                    type: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return 'selectByQnnTable'
                      }
                      return 'string'
                    },
                    showSearch: true,
                    span: 12,
                    parent: 'orgId',
                    required: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    placeholder: '请选择',
                    optionConfig: {
                      label: 'contractNo',
                      value: 'zxCtOtherManageId',
                      linkageFields: {
                        contractName: 'contractName',
                        secondName: 'secondName',
                        isDeduct: 'isDeduct',
                        contractNo: 'contractNo',
                        settleType: 'settleType'
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        let params = {}
                        params.contractNo = obj.itemData.contractNo
                        this.props.myFetch('getZxSaOtherEquipSettleBillNo', params).then(
                          ({ data, success, message }) => {
                            if (success) {
                              let initSerialNumber = Number(data) < 9 ? '0' + (Number(data) + 1) : (Number(data) + 1) + ''
                              if (pageData.periodDate) {
                                const PeriodDate = moment(pageData.periodDate).format('YY-MM').replace('-', '');
                                let billNo = '';
                                let signedNo = '';
                                billNo = `${pageData.contractNo}-${PeriodDate}-${initSerialNumber}`;
                                signedNo = `${pageData.contractNo}-SL-${PeriodDate}-${initSerialNumber}`;
                                obj.form.setFieldsValue({
                                  billNo,
                                  signedNo
                                })
                              }
                              obj.form.setFieldsValue({
                                initSerialNumber,
                                isFirst: data === '0' ? '1' : '0',
                                billType: null
                              })
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
                      }
                    },
                    dropdownMatchSelectWidth: 800,
                    qnnTableConfig: {
                      antd: {
                        rowKey: "zxCtOtherManageId"
                      },
                      firstRowIsSearch: false,
                      fetchConfig: {
                        apiName: 'getZxSaOtherEquipSettleContractNo',
                        otherParams: (val) => {
                          return {
                            orgId: this.table.form.getFieldValue('orgId')
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
                    label: '结算签认单编号',
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
                      const periodDate = moment(val).format('YY-MM').replace('-', '');
                      if (val && pageData.zxCtOtherManageId && pageData.initSerialNumber) {
                        const billNo = `${pageData.contractNo}-${periodDate}-${pageData.initSerialNumber}`;
                        const signedNo = `${pageData.contractNo}-SL-${periodDate}-${pageData.initSerialNumber}`;
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
                      if (pageData.zxCtOtherManageId && pageData.periodDate) {
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
                    label: '填报人电话',
                    field: "reportPersonPhone",
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
                    field: 'remark',
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
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn?.form?.getFieldValue("zxSaOtherEquipSettleId"))
              },
              content: {
                addIsGetData: true,
                fetchConfig: {
                  apiName: 'getZxSaOtherEquipSettleDetail',
                  otherParams: () => {
                    return {
                      zxSaOtherEquipSettleId: this.table.form.getFieldValue('zxSaOtherEquipSettleId'),
                      resPayFlag: '1'
                    }
                  }
                },
                formConfig: [
                  {
                    label: '含税合同金额(万元)',
                    field: "resContractAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '变更后含税合同金额(万元)',
                    field: "resChangeAmt",
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
                    type: 'qnnTable',
                    incToForm: true,
                    field: 'resSettleItemList',
                    qnnTableConfig: {
                      rowDisabledCondition: () => {
                        return this.table?.form.getFieldsValue().billType === '2'
                      },
                      antd: {
                        rowKey: 'zxSaOtherEquipResSettleItemId',
                        size: 'small'
                      },
                      paginationConfig: {
                        position: 'bottom'
                      },
                      isShowRowSelect: false,
                      formConfig: [
                        {
                          isInTable: false,
                          form: {
                            label: '主键id',
                            field: 'zxSaOtherEquipResSettleItemId',
                          }
                        },
                        {
                          table: {
                            title: '清单编号',
                            dataIndex: 'equipCode',
                            key: 'equipCode',
                            tdEdit: false,
                            align: 'center',
                            fixed: 'left',
                            width: 150
                          }
                        },
                        {
                          table: {
                            title: '清单名称',
                            dataIndex: 'equipName',
                            key: 'equipName',
                            tdEdit: false,
                            align: 'center',
                            fixed: 'left',
                            width: 180
                          }
                        },
                        {
                          table: {
                            title: '计量单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            tdEdit: false,
                            align: 'center',
                            width: 140
                          }
                        },
                        {
                          table: {
                            title: '含税单价(元)',
                            dataIndex: 'contractPrice',
                            key: 'contractPrice',
                            tdEdit: false,
                            width: 150,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '合同数量',
                            dataIndex: 'contractQty',
                            key: 'contractQty',
                            tdEdit: false,
                            align: 'center',
                            width: 120
                          }
                        },
                        {
                          table: {
                            title: '合同含税金额',
                            dataIndex: 'contractAmt',
                            key: 'contractAmt',
                            tdEdit: false,
                            align: 'center',
                            width: 160
                          }
                        },
                        {
                          table: {
                            title: '变更后数量',
                            dataIndex: 'changedQty',
                            key: 'changedQty',
                            tdEdit: false,
                            align: 'center',
                            width: 150
                          }
                        },
                        {
                          table: {
                            title: '变更后含税金额',
                            dataIndex: 'changedAmt',
                            key: 'changedAmt',
                            tdEdit: false,
                            align: 'center',
                            width: 160
                          }
                        },
                        {
                          table: {
                            title: '税率(%)',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            initialValue: '0',
                            tdEdit: false,
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '本期结算数量',
                            dataIndex: 'thisQty',
                            key: 'thisQty',
                            tdEdit: true,
                            width: 150,
                            align: 'center',
                          },
                          form: {
                            precision: 3,
                            type: 'number',
                            required: true,
                            placeholder: '请输入',
                            onChange: async (val, obj) => {
                              let data = await obj.qnnTableInstance.getEditedRowData()
                              data.thisQty = val ? val : 0
                              if (data.contractPrice) {
                                data.thisAmt = Number(val) * (data.contractPrice ? data.contractPrice : 0)
                                if (data.taxRate && data.taxRate !== '0') {
                                  data.thisAmtNoTax = ((data.contractPrice ? data.contractPrice : 0) * Number(val)) / (1 + Number(data.taxRate) / 100)  //本期结算不含税金额
                                  data.thisAmtTax = (data.contractPrice ? data.contractPrice : 0) * Number(val) - ((data.contractPrice ? data.contractPrice : 0) * Number(val)) / (1 + Number(data.taxRate) / 100)//本期结算税额
                                }
                              }
                              data.totalQty = (data.upQty ? data.upQty : 0) + Number(val)  //至本期末累计结算数量
                              data.totalAmt = (data.upAmt ? data.upAmt : 0) + (data.contractPrice ? data.contractPrice : 0) * Number(val)  //至本期末累计结算金额
                              await obj.qnnTableInstance.setEditedRowData(data)
                            }
                          },
                        },
                        {
                          table: {
                            title: '本期结算含税金额',
                            dataIndex: 'thisAmt',
                            key: 'thisAmt',
                            tdEdit: false,
                            width: 160,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.thisAmt ? rowData.thisAmt.toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '本期结算不含税金额',
                            dataIndex: 'thisAmtNoTax',
                            key: 'thisAmtNoTax',
                            tdEdit: false,
                            width: 160,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.thisAmtNoTax ? rowData.thisAmtNoTax.toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '本期结算税额',
                            dataIndex: 'thisAmtTax',
                            key: 'thisAmtTax',
                            tdEdit: false,
                            width: 150,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.thisAmtTax ? rowData.thisAmtTax.toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '至上期末累计结算数量',
                            dataIndex: 'upQty',
                            key: 'upQty',
                            tdEdit: false,
                            width: 180,
                            align: 'center',
                            initialValue: 0,
                          }
                        },
                        {
                          table: {
                            title: '至上期末累计结算含税金额',
                            dataIndex: 'upAmt',
                            key: 'upAmt',
                            tdEdit: false,
                            width: 180,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '至本期末累计结算数量',
                            dataIndex: 'totalQty',
                            key: 'totalQty',
                            tdEdit: false,
                            width: 180,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '至本期末累计结算金额',
                            dataIndex: 'totalAmt',
                            key: 'totalAmt',
                            tdEdit: false,
                            width: 200,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.totalAmt ? rowData.totalAmt.toFixed(2) : 0
                            }
                          }
                        },
                        {
                          table: {
                            title: '计算式',
                            dataIndex: 'planning',
                            key: 'planning',
                            tdEdit: true,
                            width: 150,
                          },
                          form: {
                            field: 'planning',
                            type: 'string',
                            placeholder: '请输入',
                          }
                        },
                        {
                          table: {
                            title: '备注',
                            dataIndex: 'remark',
                            key: 'remark',
                            tdEdit: true,
                            width: 200,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            placeholder: '请输入',
                          }
                        },
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
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue("zxSaOtherEquipSettleId"))
              },
              title: "支付项",
              content: {
                addIsGetData: true,
                fetchConfig: {
                  apiName: 'getZxSaOtherEquipSettleDetail',
                  otherParams: () => {
                    return {
                      zxSaOtherEquipSettleId: this.table.form.getFieldValue('zxSaOtherEquipSettleId'),
                      resPayFlag: '2'
                    }
                  }
                },
                formConfig: [
                  {
                    field: "zxSaOtherEquipPaySettleId",
                    type: 'string',
                    hide: true
                  },
                  {
                    label: '本期支付项结算含税金额(元)',
                    field: "payThisAmt",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '本期支付项结算不含税金额(元)',
                    field: "payThisAmtNoTax",
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
                    field: "payThisAmtTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '累计支付项结算金额(元)',
                    field: "payTotalAmt",
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
                    field: 'paySettleItemList',
                    incToForm: true,
                    qnnTableConfig: {
                      rowDisabledCondition: () => {
                        return this.table.form.getFieldsValue().billType === '2'
                      },
                      antd: {
                        rowKey: 'zxSaOtherEquipPaySettleItemId',
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
                            field: 'zxSaOtherEquipPaySettleItemId',
                          }
                        },
                        {
                          isInTable: false,
                          form: {
                            field: 'version',
                            type: 'string',
                            hide: true
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
                            dataIndex: 'payId',
                            key: 'payId',
                            tdEdit: true,
                            fixed: 'left',
                            width: 150,
                            type: 'select',
                          },
                          form: {
                            required: true,
                            type: 'select',
                            showSearch: true,
                            placeholder: '请选择',
                            optionConfig: {
                              label: 'workName',
                              value: 'id',
                            },
                            allowClear: false,
                            fetchConfig: {
                              apiName: 'getZxSaProjectSetItemList',
                              otherParams: () => {
                                return {
                                  orgID: this.table?.form?.getFieldValue('orgId'),
                                  contrType: '21'
                                }
                              }
                            },
                            onChange: async (val, obj) => {
                              let tableOneData = obj.rowData
                              let allData = await this.tableTwo.getTableData()
                              for (var i = 0; i < allData.length; i++) {
                                if (obj.rowIndex === i) continue
                                if (allData[i].payId === obj.itemData.id) {
                                  tableOneData.payId = null
                                  tableOneData.payName = null
                                  tableOneData.payType = null
                                  tableOneData.unit = null
                                  await obj.qnnTableInstance.setEditedRowData(tableOneData)
                                  Msg.warn('已存在相同名称，请重试!')
                                  obj.qnnTableInstance.refresh()
                                  return
                                }
                              }
                              tableOneData.payId = val
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
                              rowData.taxRate = val
                              rowData.thisAmtNoTax = ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100)  //本期结算不含税金额
                              rowData.thisAmtTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) - ((rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(val) / 100) //本期结算税额
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '本期结算含税金额(元)',
                            dataIndex: 'thisAmt',
                            key: 'thisAmt',
                            tdEdit: false,
                            width: 170,
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
                            title: '本期结算税额',
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
                            dataIndex: 'remark',
                            key: 'remark',
                            tdEdit: true,
                            width: 180,
                          },
                          form: {
                            field: 'remark',
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
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj?.btnCallbackFn?.form?.getFieldValue("zxSaOtherEquipSettleId"))
              },
              content: {
                antd: {
                  size: "small",
                  rowKey: "zxSaOtherEquipSettleItemId",
                },
                tableTdEdit: true,
                rowDisabledCondition: (rowData) => {
                  return rowData.zxCtOtherManageAmtRateId === ''
                },
                wrappedComponentRef: (me) => {
                  this.tableFour = me;
                },
                paginationConfig: false,
                pageSize: 999999,
                isShowRowSelect: false,
                fetchConfig: {
                  apiName: 'getZxSaOtherEquipSettleItemList',
                  otherParams: (obj) => {
                    return {
                      zxSaOtherEquipSettleId: obj.tableFns.form.getFieldValue('zxSaOtherEquipSettleId')
                    }
                  }
                },
                tableTdEditFetchConfig: async (obj) => {
                  let body = await obj.qnnTableInstance.getEditedRowData()
                  if (!body.thisAmt || isNaN(body.thisAmt)) {
                    Msg.warn('请输入正确的金额')
                    return {}
                  }
                  body.zxCtFsContractId = this.table.form.getFieldValue('zxCtFsContractId')
                  return {
                    apiName: 'updateZxSaOtherEquipSettleItem',
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
                      field: 'zxSaOtherEquipSettleItemId',
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
              apiName: 'getZxSaOtherEquipSettleDetail',
              otherParams: {
                zxSaOtherEquipSettleId: this.table?.getSelectedRows()[0]?.zxSaOtherEquipSettleId,
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
                          zxErpFileList: this.FormOne.form?.getFieldValue('zxErpFileList'),
                          zxSaOtherEquipSettleId: this.table?.getSelectedRows()[0]?.zxSaOtherEquipSettleId,
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
                field: 'periodDate',
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
                field: 'zxErpFileList',
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
                    apiName: 'getZxSaOtherEquipSettleItemList',
                    otherParams: {
                      zxSaOtherEquipSettleId: this.table?.getSelectedRows()[0]?.zxSaOtherEquipSettleId
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
      </div>
    )
  }
}

export default index;