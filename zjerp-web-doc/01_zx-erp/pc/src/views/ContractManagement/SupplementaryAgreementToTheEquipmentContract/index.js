import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import SupplementaryAgreementToTheEquipmentContractForm from './form';
import SupplementaryAgreementToTheEquipmentContractFormForPay from './formPay';
import Operation from './operation';
import DeetailForm from './detail';
import DeetailFormForPay from './detailForPay';
import FilesForSupplementaryAgreements from '../filesForSupplementaryAgreements';
const config = {
  antd: {
    rowKey: 'zxCtEqContrSupplyId',
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
            apiName: 'getZxCtEqContrSupplyList',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
            }
          }}
          componentsKey={{
            SupplementaryAgreementToTheEquipmentContractForm: (obj) => {
              if (obj.qnnTableInstance.getSelectedRows()?.[0]?.contractCategory === '0') {
                return <SupplementaryAgreementToTheEquipmentContractForm
                  {...this.props}
                  isInQnnTable={obj.isInQnnTable}
                  btnCallbackFn={obj.btnCallbackFn}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              } else {
                return <SupplementaryAgreementToTheEquipmentContractFormForPay
                  {...this.props}
                  isInQnnTable={obj.isInQnnTable}
                  btnCallbackFn={obj.btnCallbackFn}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              }

            },
            DeetailForm: (obj) => {
              if (obj.qnnTableInstance.getSelectedRows()?.[0]?.contractCategory === '0') {
                return <DeetailForm isInQnnTable={obj.isInQnnTable}
                  {...this.props}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              } else {
                return <DeetailFormForPay isInQnnTable={obj.isInQnnTable}
                  {...this.props}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              }

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
              if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCtEqContrSupplyId')) {
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
              let itemResoureList = await this.tableOne.getTableData()
              let emptyData = []
              await itemResoureList.map((item) => {
                if (!item.catCode) emptyData.push('编码')
                if (!item.alterTrrm && item.alterTrrm !== 0) emptyData.push('变更后数量')
                if (!item.alterPrice && item.alterPrice !== 0) emptyData.push('变更后单价')
                if (!item.taxRate) emptyData.push('税率')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`请填写${emptyData.join()}`)
                return {}
              } else {
                await itemResoureList.map((item) => {
                  item.zxCtEqContrAlterItemResoureId = null
                  item.contrAlterID = this.table.form.getFieldValue('contrAlterID')
                  item.contrSupplyId = this.table.form.getFieldValue('contrSupplyId')
                  return item
                })
                params.itemResoureList = itemResoureList
                params.zxCtEqContrSupplyId = this.table.form.getFieldValue('zxCtEqContrSupplyId')
                return {
                  apiName: 'batchSaveUpdateZxCtEqContrAlterItemResoure',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtEqContrSupplyId = this.table.form.getFieldValue('zxCtEqContrSupplyId')
                      this.props.myFetch('getZxCtEqContrSupplyDetail', params)
                        .then(({ data, success, message }) => {
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
              let itemResoureList = await this.tableOne.getTableData()
              let emptyData = []
              await itemResoureList.map((item) => {
                if (!item.catCode) emptyData.push('编码')
                if (!item.alterTrrm && item.alterTrrm !== 0) emptyData.push('变更后数量')
                if (!item.alterPrice && item.alterPrice !== 0) emptyData.push('变更后单价')
                if (!item.taxRate) emptyData.push('税率')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`请填写${emptyData.join()}`)
                return {}
              } else {
                await itemResoureList.map((item) => {
                  if (item.addFlag === '1') item.zxCtEqContrAlterItemResoureId = null
                  item.contrAlterID = obj.rowData.contrAlterID
                  item.contrSupplyId = obj.rowData.zxCtEqContrSupplyId
                  return item
                })
                params.itemResoureList = itemResoureList
                params.zxCtEqContrSupplyId = obj.rowData.zxCtEqContrSupplyId
                return {
                  apiName: 'batchSaveUpdateZxCtEqContrAlterItemResoure',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtEqContrSupplyId = obj.rowData.zxCtEqContrSupplyId
                      this.props.myFetch('getZxCtEqContrSupplyDetail', params)
                        .then(({ data, success, message }) => {
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
              if (data.length === 1 && data[0]?.workId) {
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
                tableField: "SupplementaryAgreementToTheEquipmentContract"
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
                dataIndex: 'supplyName',
                key: 'supplyName',
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
                dataIndex: 'thisAmount',
                key: 'thisAmount',
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
                dataIndex: 'createUserName',
                key: 'createUserName',
                width: 100
              }
            },
            {
              table: {
                title: '附件',
                dataIndex: 'zxErpFileList',
                key: 'zxErpFileList',
                width: 100,
                align:'center',
                render: (val,obj) => {
                  if (obj?.zxErpFileList?.length || obj?.zxErpMainFileList?.length) {
                    return <FilesForSupplementaryAgreements dataList={obj?.zxErpFileList} ZWFiles={obj?.zxErpMainFileList}/>
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
                    required:true,
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
                    type: 'selectByQnnTable',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    span: 12,
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
                        upAlterContractSumTax: 'alterContractSumTax',
                        upAlterContractSumNoTax: 'alterContractSumNoTax',
                        upAlterContractSum: 'alterContractSum',
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
                          contractNo: ''
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
                        apiName: 'getZxCtEqContractList',
                        otherParams: { orgID: departmentId }
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
                  }
                ]
              }
            },
            {
              field: "table1",
              name: "qnnForm",
              title: "清单",
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
                    success: ({ data, success }) => {
                      if (success) {
                        this.tableOne.setTableData(data)
                      }
                    }
                  }
                },
                formConfig: [
                  {
                    type: 'qnnTable',
                    field: 'detailList',
                    incToForm: true,
                    qnnTableConfig: {
                      antd: {
                        size: "small",
                        rowKey: "zxCtEqContrAlterItemResoureId",
                      },
                      paginationConfig: { position: 'bottom' },
                      isShowRowSelect: true,
                      wrappedComponentRef: (me) => {
                        this.tableOne = me;
                      },
                      desc: '本页面所有金额单位为：元',
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
                          icon: 'del',
                          type: 'danger',
                          label: '删除',
                          field: "del",
                        }
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
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.alterType = val
                              rowData.catCode = null
                              rowData.catName = null
                              rowData.spec = null
                              rowData.rentUnit = null
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
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '编码',
                            dataIndex: 'catCode',
                            key: 'catCode',
                            width: 140,
                            align: 'center',
                            tdEdit: true,
                            fixed: 'left',
                          },
                          form: {
                            required: true,
                            type: 'selectByQnnTable',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            optionConfig: {
                              label: 'catCode',
                              value: 'catCode',
                            },
                            onChange: async (val, obj) => {
                              let rowData = await obj.rowData
                              let allData = this.tableOne.getTableData()
                              for (var i = 0; i < allData.length; i++) {
                                if (allData[i].contrItemID === obj.itemData.zxCtEqContrItemId) {
                                  Msg.warn('已存在相同的清单，请重试!')
                                  rowData.catCode = null
                                  await obj.qnnTableInstance.setEditedRowData(rowData)
                                  return
                                }
                              }
                              if (rowData.alterType === '2') {
                                rowData.contrItemID = obj.itemData.zxCtEqContrItemId
                                rowData.catCode = obj.itemData.catCode;
                                rowData.spec = obj.itemData.spec
                                rowData.catName = obj.itemData.catName;
                                rowData.rentUnit = obj.itemData.rentUnit
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
                                rowData.parentID = obj.itemData.parentID
                                rowData.catParentName = obj.itemData.catParentName
                                rowData.zxEqResCategoryId = obj.itemData.zxEqResCategoryId
                                rowData.alterAmt = obj.itemData.alterAmt ? obj.itemData.alterAmt : 0
                                rowData.alterAmtNoTax = obj.itemData.alterAmtNoTax ? obj.itemData.alterAmtNoTax : 0
                                rowData.alterAmtTax = obj.itemData.alterAmtTax ? obj.itemData.alterAmtTax : 0
                                rowData.alterTrrm = obj.itemData?.alterTrrm
                                rowData.remark = obj.itemData.remark
                                await obj.qnnTableInstance.setEditedRowData(rowData)
                              } else if (rowData.alterType === '1' && obj.itemData) {
                                rowData.contrItemID = obj.itemData.zxCtEqContrItemId
                                rowData.catCode = obj.itemData.catCode;
                                rowData.catName = obj.itemData.catName;
                                rowData.spec = obj.itemData.spec
                                rowData.rentUnit = obj.itemData.unit
                                rowData.parentID = obj.itemData.parentID
                                rowData.catParentName = obj.itemData.parentName
                                rowData.zxEqResCategoryId = obj.itemData.id
                                await obj.qnnTableInstance.setEditedRowData(rowData)
                              }
                            },
                            dropdownMatchSelectWidth: 800,
                            qnnTableConfig: {
                              antd: {
                                rowKey: "zxCtEqContrItemId"
                              },
                              fetchConfig: (obj) => {
                                return {
                                  apiName: obj.props.rowData.alterType === '1' ? 'getZxEqResCategoryItemList' : 'getZxCtEqContrItemListForContract',
                                  otherParams: {
                                    contractID: this.table.form.getFieldValue('contractID'),
                                    isGroup: obj.props.rowData.alterType === '1' ? "1" : null
                                  }
                                }
                              },
                              searchBtnsStyle: "inline",
                              formConfig: [
                                {
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "catCode",
                                    title: "编码",
                                    width: 100,
                                  },
                                  form: { type: 'string' }
                                },
                                {
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "catName",
                                    title: "设备名称",
                                    width: 100,
                                  },
                                  form: { type: 'string' }
                                },
                                {
                                  isInTable: (obj) => {
                                    return obj.props.props.rowData.alterType === '1'
                                  },
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "unit",
                                    title: "计量单位",
                                    width: 100
                                  }
                                },
                                {
                                  isInTable: (obj) => {
                                    return obj.props.props.rowData.alterType === '2'
                                  },
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "rentUnit",
                                    title: "计量单位",
                                    width: 100
                                  }
                                },
                                {
                                  isInSearch: true,
                                  isInTable: (obj) => {
                                    if (obj.props.rowData.alterType === '2') return true
                                    return false
                                  },
                                  table: {
                                    dataIndex: "spec",
                                    title: "型号",
                                    width: 100
                                  }
                                },
                                {
                                  isInSearch: true,
                                  isInTable: (obj) => {
                                    if (obj.props.rowData.alterType === '2') return true
                                    return false
                                  },
                                  table: {
                                    dataIndex: "qty",
                                    title: "数量",
                                    width: 100,
                                    render: (h, obj) => {
                                      if (obj.alterTrrm) {
                                        return obj.alterTrrm
                                      }
                                      return h
                                    }
                                  }
                                },
                                {
                                  isInSearch: true,
                                  isInTable: (obj) => {
                                    if (obj.props.rowData.alterType === '2') return true
                                    return false
                                  },
                                  table: {
                                    dataIndex: "price",
                                    title: "含税单价",
                                    width: 100,
                                    render: (h, obj) => {
                                      if (obj.alterPrice) {
                                        return obj.alterPrice
                                      }
                                      return h
                                    }
                                  }
                                }
                              ]
                            },

                          }
                        },
                        {
                          table: {
                            title: '设备名称',
                            width: 150,
                            dataIndex: 'catName',
                            key: 'catName',
                            align: 'center',
                            tdEdit: false
                          },
                          form: {
                            field: 'catName',
                            type: 'string'
                          }
                        },
                        {
                          table: {
                            title: '型号',
                            width: 150,
                            dataIndex: 'spec',
                            key: 'spec',
                            align: 'center',
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                          },
                          form: {
                            field: 'spec',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            type: 'string',
                            placeholder: '请输入',
                          }
                        },
                        {
                          table: {
                            title: ' 租赁单位',
                            dataIndex: 'rentUnit',
                            key: 'rentUnit',
                            width: 100,
                            align: 'center',
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                          },
                          form: {
                            field: 'rentUnit',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            type: 'string',
                            placeholder: '请输入',
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
                            width: 150,
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
                            width: 150,
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
                            title: '税率(%)',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            width: 100,
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                            align: 'center',
                          },
                          form: {
                            placeholder: '请选择',
                            type: "select",
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            required: true,
                            field: 'taxRate',
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
                              let alterPriceNoTax = (rowData.alterPrice ? rowData.alterPrice : 0) / (1 + Number(val) / 100)//不含税单价
                              rowData.alterPriceNoTax = Math.round(alterPriceNoTax * 100) / 100
                              rowData.alterAmtNoTax = ((rowData.alterPrice ? rowData.alterPrice : 0) / (1 + Number(val) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0) //不含税金额
                              rowData.alterAmtTax = ((rowData.alterPrice ? rowData.alterPrice : 0) / (1 + Number(val) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0) * (Number(val) / 100)//税额
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '变更后租赁开始时间',
                            dataIndex: 'alterRentStartDate',
                            key: 'alterRentStartDate',
                            tdEdit: true,
                            width: 180,
                            format: 'YYYY-MM-DD',
                          },
                          form: {
                            type: 'date',
                            placeholder: '请选择',
                          }
                        },
                        {
                          table: {
                            title: '变更后租赁结束时间',
                            dataIndex: 'alterRentEndDate',
                            key: 'alterRentEndDate',
                            tdEdit: true,
                            width: 180,
                            format: 'YYYY-MM-DD',
                          },
                          form: {
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
                          },
                          form: {
                            required: true,
                            precision: 3,
                            placeholder: '请输入',
                            type: 'number',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.alterTrrm = val
                              rowData.alterAmt = (rowData.alterPrice ? rowData.alterPrice : 0) * Number(val) //变更后含税金额
                              rowData.alterAmtNoTax = (rowData.alterPriceNoTax ? rowData.alterPriceNoTax : 0) * Number(val)  //变更后不含税金额
                              rowData.alterAmtTax = (rowData.alterPriceNoTax ? rowData.alterPriceNoTax : 0) * Number(val) * ((rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //变更后税额
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '变更后含税单价',
                            dataIndex: 'alterPrice',
                            key: 'alterPrice',
                            width: 150,
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
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
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.alterPrice = val
                              rowData.alterAmt = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val)
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                let alterPriceNoTax = Number(val) / (1 + Number(rowData.taxRate) / 100)//变更后不含税单价
                                rowData.alterPriceNoTax = Math.round(alterPriceNoTax * 100) / 100        //变更后含税金额
                                rowData.alterAmtNoTax = (Number(val) / (1 + Number(rowData.taxRate) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0) //变更后不含税金额
                                rowData.alterAmtTax = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val) - (Number(val) / (1 + Number(rowData.taxRate) / 100)) * (rowData.alterTrrm ? rowData.alterTrrm : 0)   //变更后税额
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '变更后不含税单价',
                            dataIndex: 'alterPriceNoTax',
                            key: 'alterPriceNoTax',
                            width: 150,
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                            align: 'center',
                          },
                          form: {
                            type: 'number',
                            placeholder: '请输入',
                            field: 'alterPriceNoTax',
                            precision: 6,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.alterPriceNoTax = val
                              rowData.alterAmtNoTax = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val)    //变更后不含税金额
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                let alterPrice = Number(val) * (1 + Number(rowData.taxRate) / 100)  //变更后含税单价
                                rowData.alterPrice = Math.round(alterPrice * 100) / 100
                                rowData.alterAmt = ((rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val)) * (1 + (Number(rowData.taxRate) / 100))  //变更后含税金额
                                rowData.alterAmtTax = (rowData.alterTrrm ? rowData.alterTrrm : 0) * Number(val) * (Number(rowData.taxRate) / 100)   //变更后税额
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '变更后含税金额',
                            dataIndex: 'alterAmt',
                            key: 'alterAmt',
                            width: 150,
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
                            width: 180,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
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