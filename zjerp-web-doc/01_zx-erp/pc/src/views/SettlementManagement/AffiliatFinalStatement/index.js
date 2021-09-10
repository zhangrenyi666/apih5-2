import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Button, Modal } from 'antd';
import AffiliatFinalStatementForm from './form';
import ManageView from './manageView';
import DetailView from './detail';
import TabThreeList from './TabThree';
import TabTwoList from './TabTwo';
import moment from 'moment';
import Operation from './operation'
import QnnForm from "../../modules/qnn-form";
const config = {
  antd: {
    rowKey: 'zxSaFsSettlementId',
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
      showModel: false,
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
    }
  }
  onRef = (ref) => {
    this.child = ref
  }
  onRefPay = (ref) => {
    this.childpay = ref
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
            apiName: 'getZxSaFsSettlementList',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
            }
          }}
          tabsWillChange={(obj, canChange) => {
            if (this.tableFour) {
              this.tableFour.refresh();
            }
            canChange(true);
          }}
          componentsKey={{
            AffiliatFinalStatementForm: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <AffiliatFinalStatementForm {...this.props} btnCallbackFn={obj.qnnTableInstance} isInQnnTable={true} flowData={data[0]} />
            },
            ManageView: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <ManageView {...this.props} flowData={data[0]} />
            },
            DetailView: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <DetailView {...this.props} flowData={data[0]} isInQnnTable={obj.isInQnnTable} btnCallbackFn={obj.btnCallbackFn} />
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
              if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxSaFsSettlementId')) return true;
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
              obj.btnCallbackFn.setBtnsLoading('add', 'diySubmitss');
              let params = await this.child.table.form.getFieldsValue()
              this.props.myFetch('batchUpdateInventorySettlementDetailList', params.detailList)
                .then(({ success, message }) => {
                  if (success) {
                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmitss');
                    this.child.table.refresh()
                    Msg.success(message)
                  } else {
                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmitss');
                    Msg.error(message)
                  }
                })
            },
            //支付项按钮是否隐藏
            hideForPaySubmit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "2") return true;
              return false;
            },
            //支付项按钮配置
            cbForPaySubmit: async () => {
              let emptyData = []
              let params = await this.childpay.table.form.getFieldsValue()
              await params.payList.map((item) => {
                if (!item.payName) emptyData.push('名称')
                if (!item.payType) emptyData.push('支付项类型')
                if (!item.qty && item.qty !== 0) emptyData.push('数量')
                if (!item.price && item.price !== 0) emptyData.push('单价')
                if (!item.taxRate) emptyData.push('税率')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`请填写${emptyData.join()}`)
                return {}
              } else {
                return {
                  apiName: 'batchUpdatePayDetailAndPay',
                  otherParams: params,
                  success: () => { this.childpay.table.refresh() }
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
                tableField: "AffiliatFinalStatement"
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
                dataIndex: 'periodTime',
                key: 'periodTime',
                width: 120,
                format: 'YYYY-MM',
                filter: true,
                fieldsConfig: {
                    type: 'month',
                    field: 'periodTime'
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
                  // {
                  //   field: 'billID',
                  //   type: 'number',
                  //   hide: true,
                  // },
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
                      }
                      return {}
                    },
                  },
                  {
                    label: '合同编号',
                    field: "zxCtFsContractId",
                    type: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return 'selectByQnnTable'
                      }
                      return 'string'
                    },
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
                      value: 'zxCtFsContractId',
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
                        params.zxCtFsContractId = obj.itemData.zxCtFsContractId
                        this.props.myFetch('getZxSaFsSettlementCount', params).then(
                          ({ data, success, message }) => {
                            if (success) {
                              let initSerialNumber = Number(data.initSerialNumber) < 9 ? '0' + (Number(data.initSerialNumber) + 1) : (Number(data.initSerialNumber) + 1) + ''
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
                                isFirst: Number(data.initSerialNumber) === 0 ? '1' : '0',
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
                        rowKey: "zxCtFsContractId"
                      },
                      firstRowIsSearch: false,
                      fetchConfig: {
                        apiName: 'getZxCtFsContractXlList',
                        otherParams: () => {
                          return {
                            orgID: this.table.form.getFieldValue('orgID')
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
                    },
                  },
                  {
                    label: '结算单编号',
                    field: "billNo",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    required: true,
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
                    required: true,
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
                    editDisabled: true,
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
                    field: 'remarks',
                    type: 'textarea',
                    span: 12,
                    placeholder: '请输入',
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
                let rowData = obj.tableFns.qnnForm.form.getFieldsValue()
                params.clickName = obj.clickCb.rowInfo.name
                params.tabIndex = obj.state.tabsIndex
                params.zxSaFsSettlementId = obj.tableFns.qnnForm.form.getFieldValue('zxSaFsSettlementId') //主键
                params.billType = rowData?.billType;                               //结算类型
                // params.billID = rowData?.billID;                               //结算类型
                return <TabTwoList {...params} onRef={this.onRef} />;
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
                let rowData = obj.tableFns.qnnForm.form.getFieldsValue()
                params.clickName = obj.clickCb.rowInfo.name
                params.tabIndex = obj.state.tabsIndex
                params.zxSaFsSettlementId = rowData?.zxSaFsSettlementId
                params.billType = rowData?.billType;
                params.orgID = rowData?.orgID
                return <TabThreeList {...params} onRef={this.onRefPay} />;
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
                antd: {
                  size: "small",
                  rowKey: "zxSaFsStatisticsDetailId",
                },
                pageSize: 999,
                tableTdEdit: true,
                rowDisabledCondition: (rowData) => {
                  return rowData.sort !== 2
                },
                wrappedComponentRef: (me) => {
                  this.tableFour = me;
                },
                paginationConfig: false,
                isShowRowSelect: false,
                fetchConfig: {
                  apiName: 'getZxSaFsStatisticsDetailList',
                  otherParams: (obj) => {
                    return {
                      zxSaFsSettlementId: obj.tableFns.form.getFieldValue('zxSaFsSettlementId')
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
                    apiName: 'updateZxSaFsStatisticsDetail',
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
              apiName: 'getZxSaFsSettlementListDetail',
              otherParams: {
                zxSaFsSettlementId: this.table?.getSelectedRows()[0]?.zxSaFsSettlementId,
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
                        this.props.myFetch('zxSaFsSettlementSubmitFile', {
                          zxErpFileList: this.FormOne.form?.getFieldValue('zxErpFileList'),
                          zxSaFsSettlementId: this.table?.getSelectedRows()[0]?.zxSaFsSettlementId,
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
                field: 'periodTime',
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
                    apiName: 'getZxSaFsStatisticsDetailList',
                    otherParams: {
                      zxSaFsSettlementId: this.table?.getSelectedRows()[0]?.zxSaFsSettlementId
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