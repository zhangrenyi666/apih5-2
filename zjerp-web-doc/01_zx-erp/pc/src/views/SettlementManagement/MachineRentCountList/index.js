import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Button, Modal } from 'antd';
import MachineRentCountListForm from './form';
import ManageView from './manageView';
import DetailView from './detail';
import TabThreeList from './TabThree';
import TabTwoList from './TabTwo';
import moment from 'moment';
import Operation from './operation'
import QnnForm from "../../modules/qnn-form";
const config = {
  antd: {
    rowKey: 'zxSaEquipSettleAuditId',
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
    console.log(this.props.loginAndLogoutInfo);
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
            apiName: 'getZxSaEquipSettleAuditList',
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
            MachineRentCountListForm: (obj) => {
              let data = obj.qnnTableInstance.getSelectedRows()
              return <MachineRentCountListForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={data[0]} />
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
              if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxSaEquipSettleAuditId')) return true;
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
              this.props.myFetch('batchUpdateZxSaEquipResSettleItem', params.zxSaEquipResSettleItemList)
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
              let params = {}
              let emptyData = []
              params.itemList = await this.childpay.table.form.getFieldsValue().zxSaEquipPaySettleItemList
              await params.itemList.map((item) => {
                if (!item.payID) emptyData.push('名称')
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
                  apiName: 'batchSaveUpdateZxSaEquipPaySettleItem',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      this.childpay.table.refresh()
                    } else {
                      Msg.error(message);
                    }
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
              if (data.oldWorkItemID !== this.props.loginAndLogoutInfo.loginInfo.userInfo.userKey) {
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
                tableField: "MachineRentCountList"
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
                filter: true,
                fieldsConfig: { type: 'string' },
                width: 180
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
                        autoNum: '01',
                        billType: null
                      })
                    },
                    fetchConfig: (obj) => {
                      if (obj.btnCallbackFn.getActiveKey() === '0') {
                        return {
                          apiName: 'getZxSaProjectUnFinishList',
                          otherParams: { orgID: departmentId, settleTypeCode: 'P6' }
                        }
                      }
                      return {}
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
                        secondName: 'secondName',
                        secondID: 'secondID',
                        isDeduct: 'isDeduct',
                        contractNo: 'contractNo'
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
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
                                let autoNum = Number(data.autoNum) < 9 ? '0' + (Number(data.autoNum) + 1) : Number(data.autoNum) + 1
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
                                isFirst: data.autoNum === 0 ? '1' : '0',
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
                          signedNos: "",
                          autoNum: 1,
                          billType: null,
                          settleTypeFlag: "false"
                        })

                      }
                    },
                    dropdownMatchSelectWidth: 800,
                    qnnTableConfig: {
                      antd: {
                        rowKey: "contractID"
                      },
                      firstRowIsSearch: false,
                      fetchConfig: {
                        apiName: 'getZxCtEqContractListByOrgId',
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
                    }
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
                    field: "signedNos",
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
                    field: "periodDate",
                    type: 'month',
                    placeholder: '请选择',
                    required: true,
                    span: 12,
                    editDisabled: true,
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
                    editDisabled: true,
                    dependencies: ['isFished', 'finishStatus', 'settleTypeFlag'],
                    dependenciesReRender: true,
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
                    field: "autoNum",
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
                      if (val) {
                        let value = val < 10 ? `0${Number(val)}` : val
                        if (pageData.contractID && pageData.periodDate) {
                          const PeriodDate = moment(pageData.periodDate).format('YY-MM').replace('-', '');
                          const billNo = `${pageData.contractNo}-${PeriodDate}-${value}`;
                          const signedNos = `${pageData.contractNo}-SL-${PeriodDate}-${value}`;
                          obj.form.setFieldsValue({
                            billNo,
                            signedNos,
                          })
                        }
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
                let rowData = obj.tableFns.qnnForm.form.getFieldsValue()
                let paramsData = {}
                paramsData.clickName = obj.clickCb.rowInfo.name
                paramsData.contractID = rowData.contractID ? rowData.contractID : ''
                paramsData.zxSaEquipResSettleId = rowData.zxSaEquipResSettleId ? rowData.zxSaEquipResSettleId : ''
                paramsData.zxSaEquipSettleAuditId = rowData.zxSaEquipSettleAuditId ? rowData.zxSaEquipSettleAuditId : ''
                paramsData.tabIndex = obj.state.tabsIndex
                paramsData.billType = rowData.billType ? rowData.billType : '';                               //结算类型
                params.paramsData = paramsData
                return <TabTwoList {...params} onRef={this.onRef} />;
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
                let rowData = obj.tableFns.qnnForm.form.getFieldsValue()
                let paramsData = {}
                paramsData.zxSaEquipSettleAuditId = rowData.zxSaEquipSettleAuditId ? rowData.zxSaEquipSettleAuditId : ''
                paramsData.totalAmtPay = rowData.totalAmtPay ? rowData.totalAmtPay : 0
                paramsData.orgID = rowData.orgID ? rowData.orgID : ''
                paramsData.clickName = obj.clickCb.rowInfo.name
                paramsData.tabIndex = obj.state.tabsIndex
                paramsData.billType = rowData.billType ? rowData.billType : '';
                params.paramsData = paramsData
                return <TabThreeList {...params} onRef={this.onRefPay} />;
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
                antd: {
                  size: "small",
                  rowKey: "zxSaEquipSettleAuditItemId",
                },
                tableTdEdit: true,
                rowDisabledCondition: (rowData) => {
                  return rowData.baseFlag === 'false'
                },
                wrappedComponentRef: (me) => {
                  this.tableFour = me;
                },
                pageSize: 999,
                paginationConfig: false,
                isShowRowSelect: false,
                fetchConfig: {
                  apiName: 'getZxSaEquipSettleAuditItemList',
                  otherParams: (obj) => {
                    return {
                      zxSaEquipSettleAuditId: obj.tableFns.form.getFieldValue('zxSaEquipSettleAuditId')
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
                    apiName: 'updateZxSaEquipSettleAuditItem',
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
              apiName: 'getZxSaEquipSettleAuditDetail',
              otherParams: {
                zxSaEquipSettleAuditId: this.table?.getSelectedRows()[0]?.zxSaEquipSettleAuditId,
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
                        this.props.myFetch('updateZxSaEquipSettleAuditForFile', {
                          zxErpFileList: this.FormOne.form?.getFieldValue('zxErpFileList'),
                          zxSaEquipSettleAuditId: this.table?.getSelectedRows()[0]?.zxSaEquipSettleAuditId,
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
                    apiName: 'getZxSaEquipSettleAuditItemList',
                    otherParams: {
                      zxSaEquipSettleAuditId: this.table?.getSelectedRows()[0]?.zxSaEquipSettleAuditId
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