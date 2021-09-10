import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import OtherSupplementaryAgreementsForm from './form';
import Operation from './operation';
import DeetailForm from './detail';
import FilesForSupplementaryAgreements from '../filesForSupplementaryAgreements';
const config = {
  antd: {
    rowKey: 'zxCtOtherSupplyAgreementId',
    size: 'small'
  },
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
  render() {
    const { departmentId } = this.state;
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
            apiName: 'getZxCtOtherSupplyAgreementList',
            otherParams: { orgId: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
            }
          }}
          componentsKey={{
            OtherSupplementaryAgreementsForm: (obj) => {
              return <OtherSupplementaryAgreementsForm
                {...this.props}
                isInQnnTable={obj.isInQnnTable}
                btnCallbackFn={obj.btnCallbackFn}
                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
              />
            },
            DeetailForm: (obj) => {
              return <DeetailForm isInQnnTable={obj.isInQnnTable}
                {...this.props}
                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
              />
            },
            //进度查询
            Operation: (obj) => {
              return <Operation
                {...this.props}
                btnCallbackFn={obj.btnCallbackFn}
                apiName={'openFlowByReport'}
              />
            },
          }}
          method={{
            //新增保存按钮
            addSubmitHide: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCtOtherSupplyAgreementId')) {
                return true;
              } else {
                return false;
              }
            },
            //新增保存按钮回调
            addSuccessCb: (obj) => {
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
            //新增清单保存按钮回调
            addListSubmitCb: async () => {
              let params = {}
              let zxCtOtherSupplyAgreementWorksList = await this.tableOne.getTableData()
              let emptyData = []
              await zxCtOtherSupplyAgreementWorksList.map((item) => {
                if (!item.alterType) emptyData.push('变更类型')
                if (item.alterType && item.alterType === '1') {
                  if (!item.workName) emptyData.push('清单名称')
                  if (!item.changePrice && item.changePrice !== 0) emptyData.push('变更后单价')
                  if (!item.taxRate) emptyData.push('税率')
                }
                if (!item.workNo) emptyData.push('清单编号')
                if (!item.changeQty && item.changeQty !== 0) emptyData.push('变更后数量')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`请填写${emptyData.join()}`)
                return {}
              } else {
                await zxCtOtherSupplyAgreementWorksList.map((item) => {
                  item.zxCtOtherSupplyAgreementWorksId = null
                  return item
                })
                params.zxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksList
                params.zxCtOtherSupplyAgreementId = this.table.form.getFieldValue('zxCtOtherSupplyAgreementId')
                return {
                  apiName: 'batchSaveUpdateZxCtOtherSupplyAgreementWorks',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtOtherSupplyAgreementId = this.table.form.getFieldValue('zxCtOtherSupplyAgreementId')
                      this.props.myFetch('getZxCtOtherSupplyAgreementDetail', params)
                        .then(({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              alterContractSum: data.alterContractSum,
                              alterContractSumNoTax: data.alterContractSumNoTax,
                              alterContractSumTax: data.alterContractSumTax,
                              applyAmountNoTax: data.applyAmountNoTax,
                              applyAmount: data.applyAmount,
                              applyAmountTax: data.applyAmountTax,
                            })
                          } else {
                            Msg.error(message);
                          }
                        })
                    } else {
                      Msg.error(message);
                    }
                  }
                }
              }
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
            //修改按钮禁用
            disabledFunEdit: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                return true;
              } else {
                return false;
              }
            },
            //修改保存按钮隐藏
            hideFunEdit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== '0') return true;
              return false
            },
            //修改保存按钮回调
            editSuccessCb: (obj) => {
              if (obj.response.success) {
                this.table.setTabsIndex('1');
              }
            },
            //修改清单保存按钮回调
            editListSubmitCb: async (obj) => {
              let params = {}
              let zxCtOtherSupplyAgreementWorksList = await this.tableOne.getTableData()
              let emptyData = []
              await zxCtOtherSupplyAgreementWorksList.map((item) => {
                if (!item.alterType) emptyData.push('变更类型')
                if (item.alterType && item.alterType === '1') {
                  if (!item.workName) emptyData.push('清单名称')
                  if (!item.changePrice && item.changePrice !== 0) emptyData.push('变更后单价')
                  if (!item.taxRate) emptyData.push('税率')
                }
                if (!item.workNo) emptyData.push('清单编号')
                if (!item.changeQty && item.changeQty !== 0) emptyData.push('变更后数量')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`请填写${emptyData.join()}`)
                return {}
              } else {
                await zxCtOtherSupplyAgreementWorksList.map((item) => {
                  if (item.addFlag === '1') item.zxCtOtherSupplyAgreementWorksId = null
                  return item
                })
                params.zxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksList
                params.zxCtOtherSupplyAgreementId = obj.rowData.zxCtOtherSupplyAgreementId
                return {
                  apiName: 'batchSaveUpdateZxCtOtherSupplyAgreementWorks',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtOtherSupplyAgreementId = obj.rowData.zxCtOtherSupplyAgreementId
                      this.props.myFetch('getZxCtOtherSupplyAgreementDetail', params)
                        .then(({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              alterContractSum: data.alterContractSum,
                              alterContractSumNoTax: data.alterContractSumNoTax,
                              alterContractSumTax: data.alterContractSumTax,
                              applyAmountNoTax: data.applyAmountNoTax,
                              applyAmount: data.applyAmount,
                              applyAmountTax: data.applyAmountTax,
                            })
                          } else {
                            Msg.error(message);
                          }
                        })
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
              let data = obj.btnCallbackFn.getSelectedRows();
              let disabledLength = 0
              for (var i = 0; i < data.length; i++) {
                if (data[i].apih5FlowStatus === '1' || data[i].apih5FlowStatus === '2') {
                  disabledLength++
                }
              }
              if (disabledLength > 0 || data.length === 0) return true
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
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "OtherSupplementaryAgreements"
              }
            }
          }}
          formConfig={[
            {
              table: {
                title: '补充协议编号',
                dataIndex: 'contractNo',
                key: 'contractNo',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
                onClick: 'detail',
              }
            },
            {
              table: {
                title: '补充协议名称',
                dataIndex: 'proposer',
                key: 'proposer',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
              }
            },
            {
              table: {
                title: '合同名称',
                dataIndex: 'contractName',
                key: 'contractName',
                width: 180,
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
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '乙方名称',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '合同签订人',
                dataIndex: 'agent',
                key: 'agent',
                width: 150,
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
                dataIndex: 'applyAmount',
                key: 'applyAmount',
                width: 180,
              }
            },
            {
              table: {
                title: '变更后含税金额(万元)',
                dataIndex: 'alterContractSum',
                key: 'alterContractSum',
                width: 180,
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
                dataIndex: 'beginPer',
                key: 'beginPer',
                width: 100
              }
            },
            {
              table: {
                title: '附件',
                dataIndex: 'zxErpFileList',
                key: 'zxErpFileList',
                width: 100,
                align: 'center',
                render: (val, obj) => {
                  if (obj?.zxErpFileList?.length || obj?.zxZhengWenFileList?.length) {
                    return <FilesForSupplementaryAgreements dataList={obj?.zxErpFileList} ZWFiles={obj?.zxZhengWenFileList} />
                  } else {
                    return '无附件'
                  }
                }
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
                    field: 'apih5FlowStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtOtherSupplyAgreementId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'firstId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgName',
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
                    label: '补充协议编号',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    required:true,
                    span: 12
                  },
                  {
                    label: '补充协议名称',
                    field: "proposer",
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
                    field: "zxCtOtherManageId",
                    required: true,
                    type: 'selectByQnnTable',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    span: 12,
                    optionConfig: {
                      label: 'contractName',
                      value: 'zxCtOtherManageId',
                      linkageFields: {
                        firstName: 'firstName',
                        secondName: 'secondName',
                        contractCost: 'contractCost',
                        contractCostNoTax: "contractCostNoTax",
                        contractCostTax: 'contractCostTax',
                        addOrSubtractAmountTax: 'addOrSubtractAmountTax',
                        addOrSubtractAmountNoTax: 'addOrSubtractAmountNoTax',
                        addOrSubtractTax: 'addOrSubtractTax',
                        alterContractSum: 'alterContractSum',
                        alterContractSumNoTax: 'alterContractSumNoTax',
                        alterContractSumTax: 'alterContractSumTax',
                        isDeduct: 'isDeduct',
                        contractName: 'contractName',
                        secondId: 'secondId',
                        firstId: 'firstId',
                        orgId: 'orgId',
                        orgName: 'orgName',
                        companyId: "companyId",
                        companyName: 'companyName'
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
                        let params = {}
                        params.contractNo = obj.itemData.contractNo
                        this.props.myFetch('getZxCtOtherSupplyAgreementContractNo', params).then(
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
                    dropdownMatchSelectWidth: 800,
                    qnnTableConfig: {
                      antd: {
                        rowKey: "zxCtOtherManageId"
                      },
                      firstRowIsSearch: false,
                      fetchConfig: {
                        apiName: 'getZxCtOtherManageList',
                        otherParams: { orgId: departmentId }
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
                    label: '合同类型',
                    field: "contractType",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    initialValue: '其它类补充协议'
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
                    field: "applyAmount",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '本期不含税增减金额(万元)',
                    field: "applyAmountNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '本期增减税额(万元)',
                    field: "applyAmountTax",
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
                    field: 'replyUnit',
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
                ]
              }
            },
            {
              field: "table1",
              name: "qnnForm",
              title: "清单",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtOtherSupplyAgreementId"))
              },
              content: {
                fetchConfig: {
                  apiName: 'getZxCtOtherSupplyAgreementDetail',
                  otherParams: () => {
                    return {
                      zxCtOtherSupplyAgreementId: this.table.form.getFieldValue('zxCtOtherSupplyAgreementId')
                    }
                  }
                },
                formConfig: [
                  {
                    type: 'qnnTable',
                    field: 'zxCtOtherSupplyAgreementWorksList',
                    incToForm: true,
                    qnnTableConfig: {
                      antd: {
                        size: "small",
                        rowKey: "zxCtOtherSupplyAgreementWorksId",
                      },
                      paginationConfig: { position: 'bottom' },
                      isShowRowSelect: true,
                      wrappedComponentRef: (me) => {
                        this.tableOne = me;
                      },
                      actionBtns: [
                        {
                          name: 'addRow',
                          icon: 'plus',
                          type: 'primary',
                          label: '新增行',
                          field: "addRow",
                          addRowDefaultData: { addFlag: '1' }
                        },
                        {
                          name: 'delRow',
                          icon: 'delete',
                          type: 'danger',
                          label: '删除',
                          field: "del",
                        }
                      ],
                      formConfig: [
                        {
                          isInTable: false,
                          form: {
                            field: 'zxCtOtherSupplyAgreementWorksId',
                            type: 'string',
                            hide: true,
                          }
                        },
                        {
                          table: {
                            title: '变更类型',
                            dataIndex: 'alterType',
                            key: 'alterType',
                            width: 120,
                            align: 'center',
                            tdEdit: true,
                            fixed: 'left',
                            type: 'select'
                          },
                          form: {
                            required: true,
                            field: 'alterType',
                            placeholder: '请选择',
                            type: 'select',
                            allowClear: false,//是否显示清除按钮
                            optionConfig: {
                              label: 'label',
                              value: 'value',
                            },
                            optionData: [
                              { label: '新增', value: '1', },
                              { label: '修改', value: '2', }
                            ],
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance?.getEditedRowData()
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
                              rowData.remark = null
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '清单编号',
                            dataIndex: 'workNo',
                            key: 'workNo',
                            width: 140,
                            align: 'center',
                            tdEdit: true,
                            fixed: 'left',
                          },
                          form: {
                            required: true,
                            type: (obj) => {
                              if (obj.rowData.alterType === '2') {
                                return 'selectByQnnTable'
                              }
                              return 'string'
                            },
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            optionConfig: {
                              label: 'workNo',
                              value: 'workNo',
                            },
                            dropdownMatchSelectWidth: 800,
                            qnnTableConfig: {
                              antd: {
                                rowKey: "zxCtOtherWorksId"
                              },
                              fetchConfig: () => {
                                return {
                                  apiName: 'getZxCtOtherWorksList',
                                  otherParams: {
                                    zxCtOtherManageId: this.table.form.getFieldValue('zxCtOtherManageId')
                                  }
                                }
                              },
                              searchBtnsStyle: "inline",
                              formConfig: [
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "workNo",
                                    title: "清单编号",
                                    width: 100
                                  },
                                },
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "workName",
                                    title: "清单名称",
                                    width: 100
                                  }
                                },
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "unit",
                                    title: "计量单位",
                                    width: 100
                                  }
                                },
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "qty",
                                    title: "数量",
                                    width: 100,
                                    render: (h, obj) => {
                                      if (obj.changeQty) {
                                        return obj.changeQty
                                      }
                                      return h
                                    }
                                  }
                                },
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "price",
                                    title: "含税单价",
                                    width: 100,
                                    render: (h, obj) => {
                                      if (obj.changePrice) {
                                        return obj.changePrice
                                      }
                                      return h
                                    }
                                  }
                                }
                              ]
                            },

                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance?.getEditedRowData()
                              if (rowData?.alterType === '2') {
                                rowData.workNo = obj.itemData.workNo;
                                rowData.workName = obj.itemData.workName;
                                rowData.unit = obj.itemData.unit;
                                rowData.qty = obj.itemData.qty;
                                rowData.price = obj.itemData.price;
                                rowData.priceNoTax = obj.itemData.priceNoTax;
                                rowData.contractSum = obj.itemData.contractSum;
                                rowData.contractSumNoTax = obj.itemData.contractSumNoTax;
                                rowData.contractSumTax = obj.itemData.contractSumTax;
                                rowData.changeQty = obj.itemData?.changeQty;
                                rowData.changePrice = obj.itemData.changePrice ? obj.itemData.changePrice : obj.itemData.price ? obj.itemData.price : 0;
                                rowData.taxRate = obj.itemData.taxRate;
                                rowData.isDeduct = obj.itemData.isDeduct;
                                rowData.remark = obj.itemData.remark;
                                rowData.zxCtOtherWorksId = obj.itemData.zxCtOtherWorksId
                                rowData.changeContractSum = obj.itemData.changeContractSum
                                rowData.changeContractSumNoTax = obj.itemData.changeContractSumNoTax
                                rowData.changeContractSumTax = obj.itemData.changeContractSumTax;
                                await obj.qnnTableInstance.setEditedRowData(rowData)
                              }
                            },
                          }
                        },
                        {
                          table: {
                            dataIndex: 'workName',
                            key: 'workName',
                            align: 'center',
                            title: '清单名称',
                            width: 150,
                            tdEdit: (obj) => { return obj.alterType === '1' ? true : false },
                          },
                          form: {
                            required: true,
                            type: 'string',
                            placeholder: '请输入',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                          }
                        },
                        {
                          table: {
                            title: ' 单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 100,
                            align: 'center',
                            tdEdit: (obj) => { return obj.alterType === '1' ? true : false },
                          },
                          form: {
                            type: "string",
                            placeholder: '请输入',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                          }
                        },
                        {
                          table: {
                            title: '数量',
                            dataIndex: 'qty',
                            key: 'qty',
                            width: 100,
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
                            required: true,
                            precision: 3,
                            field: 'changeQty',
                            placeholder: '请输入',
                            type: 'number',
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance?.getEditedRowData()
                              rowData.changeQty = val
                              rowData.changeContractSum = (rowData.changePrice ? rowData.changePrice : 0) * Number(val)
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.changeContractSumNoTax = ((rowData.changePrice ? rowData.changePrice : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)
                                rowData.changeContractSumTax = (rowData.changePrice ? rowData.changePrice : 0) * Number(val) - ((rowData.changePrice ? rowData.changePrice : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '变更后单价',
                            dataIndex: 'changePrice',
                            key: 'changePrice',
                            width: 120,
                            tdEdit: (obj) => { return obj.alterType === '1' ? true : false },
                            align: 'center',
                          },
                          form: {
                            required: true,
                            type: "number",
                            placeholder: '请输入',
                            precision: 6,
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance?.getEditedRowData()
                              rowData.changePrice = val
                              rowData.changeContractSum = (rowData.changeQty ? rowData.changeQty : 0) * Number(val)
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.changeContractSumNoTax = ((rowData.changeQty ? rowData.changeQty : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)
                                rowData.changeContractSumTax = (rowData.changeQty ? rowData.changeQty : 0) * Number(val) - ((rowData.changeQty ? rowData.changeQty : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
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
                            title: '税率(%)',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            width: 100,
                            tdEdit: (obj) => { return obj.alterType === '1' ? true : false },
                            align: 'center',
                          },
                          form: {
                            placeholder: '请选择',
                            type: "select",
                            required: true,
                            optionConfig: {
                              label: 'itemName',
                              value: 'itemId',
                            },
                            fetchConfig: {
                              apiName: 'getBaseCodeSelect',
                              otherParams: { itemId: 'shuiLv' }
                            },
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance?.getEditedRowData()
                              rowData.taxRate = val
                              rowData.changeContractSumNoTax = ((rowData.changePrice ? rowData.changePrice : 0) * (rowData.changeQty ? rowData.changeQty : 0)) / (1 + Number(val) / 100)
                              rowData.changeContractSumTax = (((rowData.changePrice ? rowData.changePrice : 0) * (rowData.changeQty ? rowData.changeQty : 0)) / (1 + Number(val) / 100)) * (Number(val) / 100)
                              await obj.qnnTableInstance.setEditedRowData(rowData)
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
                            dataIndex: 'remark',
                            key: 'remark',
                            width: 150,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            field: 'remarks',
                            type: "string",
                            placeholder: '请输入'
                          }
                        }
                      ]
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