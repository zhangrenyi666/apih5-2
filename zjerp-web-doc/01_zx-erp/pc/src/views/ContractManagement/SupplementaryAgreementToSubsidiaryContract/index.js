import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import SupplementaryAgreementToSubsidiaryContractForm from './form';
import Operation from './operation';
import DeetailForm from './detail';
import FilesForSupplementaryAgreements from '../filesForSupplementaryAgreements';
const config = {
  antd: {
    rowKey: 'zxCtFsSideAgreementId',
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
            apiName: 'getZxCtFsSideAgreementList',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
            }
          }}
          componentsKey={{
            SupplementaryAgreementToSubsidiaryContractForm: (obj) => {
              return <SupplementaryAgreementToSubsidiaryContractForm
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
            //????????????
            Operation: (obj) => {
              return <Operation
                {...this.props}
                btnCallbackFn={obj.btnCallbackFn}
                apiName={'openFlowByReport'}
              />
            },
          }}
          method={{
            //??????????????????
            addSubmitHide: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCtFsSideAgreementId')) {
                return true;
              } else {
                return false;
              }
            },
            //????????????????????????
            addSuccessCb: (obj) => {
              if (obj.response.success) {
                this.table.setDeawerValues({ ...obj.response.data });
                this.table.setTabsIndex('1');
              }
            },
            //??????????????????
            listSubmitHide: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "1") return true;
              return false;
            },
            //??????????????????????????????
            addListSubmitCb: async () => {
              let params = {}
              let inventoryList = await this.tableOne.getTableData()
              let emptyData = []
              await inventoryList.map((item) => {
                if (!item.alterType) emptyData.push('????????????')
                if (!item.conRevDetailId) emptyData.push('????????????')
                if (!item.workName) emptyData.push('????????????')
                if (!item.changeQty && item.changeQty !== 0) emptyData.push('???????????????')
                if (!item.taxRate) emptyData.push('??????')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`?????????${emptyData.join()}`)
                return {}
              } else {
                await inventoryList.map((item) => {
                  item.zxCtFsSideAgreementInventoryId = null
                  return item
                })
                params.inventoryList = inventoryList
                params.zxCtFsSideAgreementId = this.table.form.getFieldValue('zxCtFsSideAgreementId')
                return {
                  apiName: 'batchUpdateZxCtFsSideAgreementInventory',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtFsSideAgreementId = this.table.form.getFieldValue('zxCtFsSideAgreementId')
                      this.props.myFetch('getZxCtFsSideAgreementDetail', params)
                        .then(({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              alterContractSum: data.alterContractSum,
                              alterContractSumNoTax: data.alterContractSumNoTax,
                              alterContractSumTax: data.alterContractSumTax,
                              pp4NoTax: data.pp4NoTax,
                              pp4: data.pp4,
                              pp4Tax: data.pp4Tax,
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
            //????????????????????????
            disabledFunDetail: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                return false;
              } else {
                return true;
              }
            },
            //??????????????????
            disabledFunEdit: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                return true;
              } else {
                return false;
              }
            },
            //????????????????????????
            hideFunEdit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== '0') return true;
              return false
            },
            //????????????????????????
            editSuccessCb: (obj) => {
              if (obj.response.success) {
                this.table.setTabsIndex('1');
              }
            },
            //??????????????????????????????
            editListSubmitCb: async (obj) => {
              let params = {}
              let inventoryList = await this.tableOne.getTableData()
              let emptyData = []
              await inventoryList.map((item) => {
                if (!item.alterType) emptyData.push('????????????')
                if (!item.conRevDetailId) emptyData.push('????????????')
                if (!item.workName) emptyData.push('????????????')
                if (!item.changeQty && item.changeQty !== 0) emptyData.push('???????????????')
                if (!item.taxRate) emptyData.push('??????')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`?????????${emptyData.join()}`)
                return {}
              } else {
                await inventoryList.map((item) => {
                  if (item.addFlag === '1') item.zxCtFsSideAgreementInventoryId = null
                  return item
                })
                params.inventoryList = inventoryList
                params.zxCtFsSideAgreementId = obj.rowData.zxCtFsSideAgreementId
                return {
                  apiName: 'batchUpdateZxCtFsSideAgreementInventory',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtFsSideAgreementId = obj.rowData.zxCtFsSideAgreementId
                      this.props.myFetch('getZxCtFsSideAgreementDetail', params)
                        .then(({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              alterContractSum: data.alterContractSum,
                              alterContractSumNoTax: data.alterContractSumNoTax,
                              alterContractSumTax: data.alterContractSumTax,
                              pp4NoTax: data.pp4NoTax,
                              pp4: data.pp4,
                              pp4Tax: data.pp4Tax,
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
            //????????????????????????
            disabledFunComponent: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && data[0].workId) {
                return false;
              } else {
                return true;
              }
            },
            //??????????????????
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
            //??????????????????
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
                tableField: "SupplementaryAgreementToSubsidiaryContract"
              }
            }
          }}
          formConfig={[
            {
              table: {
                title: '??????????????????',
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
                title: '??????????????????',
                dataIndex: 'pp3',
                key: 'pp3',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractName',
                key: 'contractName',
                width: 180,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractType',
                key: 'contractType',
                width: 150,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'firstName',
                key: 'firstName',
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '???????????????',
                dataIndex: 'agent',
                key: 'agent',
                width: 150,
              }
            },
            {
              table: {
                title: '??????????????????(??????)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                width: 150,
              }
            },
            {
              table: {
                title: '????????????????????????(??????)',
                dataIndex: 'pp4',
                key: 'pp4',
                width: 180,
              }
            },
            {
              table: {
                title: '?????????????????????(??????)',
                dataIndex: 'alterContractSum',
                key: 'alterContractSum',
                width: 180,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'isDeduct',
                key: 'isDeduct',
                width: 100,
                render: (h) => {
                  if (h === '1') {
                    return '???'
                  } else {
                    return '???'
                  }
                },
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'startDate',
                key: 'startDate',
                width: 100,
                format: 'YYYY-MM-DD',
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'endDate',
                key: 'endDate',
                width: 100,
                format: 'YYYY-MM-DD',
              }
            },
            {
              table: {
                title: '?????????',
                dataIndex: 'createUserName',
                key: 'createUserName',
                width: 100
              }
            },
            {
              table: {
                title: '??????',
                dataIndex: 'zxErpFileList',
                key: 'zxErpFileList',
                width: 100,
                align:'center',
                render: (val,obj) => {
                  if (obj?.zxErpFileList?.length || obj?.zxZhengWenFileList?.length) {
                    return <FilesForSupplementaryAgreements dataList={obj?.zxErpFileList} ZWFiles={obj?.zxZhengWenFileList}/>
                  } else {
                    return '?????????'
                  }
                }
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                width: 100,
                fixed: 'right',
                render: (data) => {
                  if (data === '0') {
                    return '?????????';
                  } else if (data === '1') {
                    return '?????????';
                  } else if (data === '2') {
                    return '????????????';
                  } else if (data === '3') {
                    return '??????';
                  } else if (data === '-1') {
                    return '?????????';
                  }
                }
              }
            }
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "????????????",
              desc: '??????????????????????????????',
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
                    field: 'secondID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'firstID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtFsSideAgreementId',
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
                    field: 'comID',
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
                    field: 'comName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '??????????????????',
                    field: "contractNo",
                    type: 'string',
                    required:true,
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: "pp3",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '????????????',
                    field: "contractName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '????????????',
                    field: "zxCtFsContractId",
                    required: true,
                    type: 'selectByQnnTable',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    span: 12,
                    optionConfig: {
                      label: 'contractName',
                      value: 'zxCtFsContractId',
                      linkageFields: {
                        upAlterContractSumTax: 'alterContractSumTax',
                        upAlterContractSumNoTax: 'alterContractSumNoTax',
                        upAlterContractSum: 'alterContractSum',
                        firstName: 'firstName',
                        secondName: 'secondName',
                        firstID: 'firstId',
                        secondID: 'secondID',
                        contractNo: 'contractNo',
                        contractCost: 'contractCost',
                        contractCostNoTax: "contractCostNoTax",
                        contractCostTax: 'contractCostTax',
                        alterContractSum: 'alterContractSum',
                        alterContractSumNoTax: 'alterContractSumNoTax',
                        alterContractSumTax: 'alterContractSumTax',
                        isDeduct: 'isDeduct',
                        contractName: 'contractName',
                        orgID: 'orgID',
                        orgName: 'orgName',
                        comID: "comID",
                        comName: 'comName'
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
                        let params = {}
                        params.contractNo = obj.itemData.contractNo
                        this.props.myFetch('getContractNo', params).then(
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
                        rowKey: "zxCtFsContractId"
                      },
                      firstRowIsSearch: false,
                      fetchConfig: {
                        apiName: 'getZxCtFsContractListForAgrement',
                        otherParams: { orgID: departmentId }
                      },
                      searchBtnsStyle: "inline",
                      formConfig: [
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "contractNo",
                            title: "????????????",
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "contractName",
                            title: "????????????",
                          }
                        },
                      ]
                    },
                  },
                  {
                    label: '????????????',
                    field: "contractType",
                    span: 12,
                    disabled: true,
                    type: 'select',
                    initialValue: '?????????????????????',
                    optionData:[
                      {label:'?????????????????????',value:'?????????????????????'},
                      {label:'?????????????????????',value:'P9'},
                    ],
                    optionConfig:{
                       label: 'label', 
                       value: 'value'
                    },
                  },
                  {
                    label: '????????????',
                    field: "firstName",
                    type: 'string',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '????????????',
                    field: "secondName",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '???????????????',
                    field: "agent",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????(??????)',
                    field: "contractCost",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '????????????(??????)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '????????????????????????(??????)',
                    field: "pp4",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '???????????????????????????(??????)',
                    field: "pp4NoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '??????????????????(??????)',
                    field: "pp4Tax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: "alterContractSum",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '????????????????????????(??????)',
                    field: "alterContractSumNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '???????????????(??????)',
                    field: "alterContractSumTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '????????????',
                    field: "isDeduct",
                    type: 'radio',
                    optionData: [
                      { label: '???', value: '1' },
                      { label: '???', value: '0' }
                    ],
                    initialValue: '0',
                    disabled: true,
                    span: 12,
                  },
                  {
                    label: '????????????',
                    field: 'startDate',
                    type: 'date',
                    placeholder: '?????????',
                    span: 12,
                  },
                  {
                    label: '????????????',
                    field: 'endDate',
                    type: 'date',
                    placeholder: '?????????',
                    span: 12,
                  },
                  {
                    label: '????????????',
                    field: "csTimeLimit",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'pp1',
                    type: 'select',
                    placeholder: '?????????',
                    span: 12,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: '??????',
                        value: '??????',
                      },
                      {
                        label: '??????',
                        value: '??????',
                      },
                      {
                        label: '???',
                        value: '???',
                      }
                    ],
                  },
                  {
                    label: '????????????',
                    field: "replyDate",
                    span: 12,
                    type: 'date',
                    placeholder: '?????????',
                  },
                  {
                    label: '????????????',
                    field: 'alterContent',
                    type: 'textarea',
                    span: 12,
                    required: true,
                    placeholder: '?????????',
                  },
                  {
                    label: '????????????',
                    field: 'content',
                    type: 'textarea',
                    required: true,
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'alterReason',
                    span: 12,
                    type: 'textarea',
                    placeholder: '?????????',
                  },
                  {
                    label: '??????',
                    span: 12,
                    field: 'remarks',
                    type: 'textarea',
                    placeholder: '?????????',
                  }
                ]
              }
            },
            {
              field: "table1",
              name: "qnnForm",
              title: "??????",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtFsSideAgreementId"))
              },
              content: {
                fetchConfig: {
                  apiName: 'getZxCtFsSideAgreementDetail',
                  otherParams: () => {
                    return {
                      zxCtFsSideAgreementId: this.table.form.getFieldValue('zxCtFsSideAgreementId')
                    }
                  }
                },
                formConfig: [
                  {
                    type: 'qnnTable',
                    field: 'inventoryList',
                    incToForm: true,
                    qnnTableConfig: {
                      antd: {
                        size: "small",
                        rowKey: "zxCtFsSideAgreementInventoryId",
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
                          label: '?????????',
                          field: "addRow",
                          addRowDefaultData: { addFlag: '1' }
                        },
                        {
                          name: 'delRow',
                          icon: 'delete',
                          type: 'danger',
                          label: '??????',
                          field: "del",
                        }
                      ],
                      formConfig: [
                        {
                          isInTable: false,
                          form: {
                            field: 'upAlterContractSum', //????????????????????????
                            type: 'number',
                            hide: true,
                          }
                        },
                        {
                          isInTable: false,
                          form: {
                            field: 'upAlterContractSumNoTax', //?????????????????????????????????
                            type: 'number',
                            hide: true,
                          }
                        },

                        {
                          isInTable: false,
                          form: {
                            field: 'workNo',
                            type: 'number',
                            hide: true,
                          }
                        },
                        {
                          isInTable: false,
                          form: {
                            field: 'zxCtFsSideAgreementInventoryId',
                            type: 'string',
                            hide: true,
                          }
                        },
                        {
                          table: {
                            title: '????????????',
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
                            placeholder: '?????????',
                            type: 'select',
                            allowClear: false,//????????????????????????
                            optionConfig: {
                              label: 'label',
                              value: 'value',
                            },
                            optionData: [
                              { label: '??????', value: '1', },
                              { label: '??????', value: '2', }
                            ],
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
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
                              rowData.remarks = null
                              rowData.upAlterContractSum = null
                              rowData.upAlterContractSumNoTax = null
                              rowData.conRevDetailId = null
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'conRevDetailId',
                            key: 'conRevDetailId',
                            width: 140,
                            align: 'center',
                            tdEdit: true,
                            fixed: 'left',
                            type: (obj) => {
                              if (obj.rowData.alterType === '2') {
                                return 'selectByQnnTable'
                              }
                            },
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
                              value: 'conRevDetailId',
                            },
                            dropdownMatchSelectWidth: 800,
                            qnnTableConfig: {
                              antd: {
                                rowKey: "zxCtEqContrItemId"
                              },
                              fetchConfig: {
                                apiName: 'getAllReviewDetailListByContract',
                                otherParams: () => {
                                  return {
                                    zxCtFsContractId: this.table.form.getFieldValue('zxCtFsContractId')
                                  }

                                },
                              },
                              searchBtnsStyle: "inline",
                              formConfig: [
                                {
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "workNo",
                                    title: "????????????",
                                    width: 100
                                  }
                                },
                                {
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "workName",
                                    title: "????????????",
                                    width: 100
                                  }
                                },
                                {
                                  table: {
                                    dataIndex: "unit",
                                    title: "????????????",
                                    width: 100
                                  }
                                },
                                {
                                  table: {
                                    dataIndex: "qty",
                                    title: "??????",
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
                                  table: {
                                    dataIndex: "price",
                                    title: "????????????",
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
                              if (rowData?.alterType === '1') {
                                rowData.workNo = val
                                await obj.qnnTableInstance.setEditedRowData(rowData)
                              } else if (rowData?.alterType === '2') {
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
                                rowData.remarks = obj.itemData.remarks;
                                rowData.conRevDetailId = obj.itemData.conRevDetailId
                                rowData.changeContractSum = obj.itemData.changeContractSum
                                rowData.changeContractSumNoTax = obj.itemData.changeContractSumNoTax
                                rowData.changeContractSumTax = obj.itemData.changeContractSum - obj.itemData.changeContractSumNoTax
                                rowData.upAlterContractSum = obj.itemData.changeContractSum ? obj.itemData.changeContractSum : null;
                                rowData.upAlterContractSumNoTax = obj.itemData.changeContractSumNoTax ? obj.itemData.changeContractSumNoTax : null;
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
                            title: '????????????',
                            width: 150,
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                          },
                          form: {
                            field: 'workName',
                            required: true,
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            type: 'string',
                            placeholder: '?????????',
                          }
                        },
                        {
                          table: {
                            title: ' ??????',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 100,
                            align: 'center',
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                          },
                          form: {
                            field: 'unit',
                            type: "string",
                            placeholder: '?????????',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                          }
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'qty',
                            key: 'qty',
                            width: 100,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'price',
                            key: 'price',
                            width: 150,
                            align: 'center',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                          }
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'priceNoTax',
                            key: 'priceNoTax',
                            width: 150,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'contractSum',
                            key: 'contractSum',
                            width: 150,
                            align: 'center',
                          }
                        },

                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'contractSumNoTax',
                            key: 'contractSumNoTax',
                            width: 150,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'contractSumTax',
                            key: 'contractSumTax',
                            width: 120,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '??????(%)',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            width: 100,
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                            align: 'center',
                          },
                          form: {
                            placeholder: '?????????',
                            type: "select",
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
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
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.taxRate = val
                              rowData.changeContractSumNoTax = ((rowData.changePrice ? rowData.changePrice : 0) * (rowData.changeQty ? rowData.changeQty : 0)) / (1 + Number(val) / 100)
                              rowData.changeContractSumTax = (((rowData.changePrice ? rowData.changePrice : 0) * (rowData.changeQty ? rowData.changeQty : 0)) / (1 + Number(val) / 100)) * (Number(val) / 100)
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'changeQty',
                            key: 'changeQty',
                            width: 120,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            required: true,
                            precision: 3,
                            placeholder: '?????????',
                            type: 'number',
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.changeQty = val
                              rowData.changeContractSum = (rowData.changePrice ? rowData.changePrice : 0) * Number(val)
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.changeContractSumNoTax = ((rowData.changePrice ? rowData.changePrice : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)
                                rowData.changeContractSumTax = (((rowData.changePrice ? rowData.changePrice : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)) * (Number(rowData.taxRate) / 100)
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'changePrice',
                            key: 'changePrice',
                            width: 120,
                            tdEdit: (obj) => { return obj?.alterType === '1' ? true : false },
                            align: 'center',
                          },
                          form: {
                            type: "number",
                            placeholder: '?????????',
                            precision: 6,
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.changePrice = val
                              rowData.changeContractSum = (rowData.changeQty ? rowData.changeQty : 0) * Number(val)
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.changeContractSumNoTax = ((rowData.changeQty ? rowData.changeQty : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)
                                rowData.changeContractSumTax = (((rowData.changeQty ? rowData.changeQty : 0) * Number(val)) / (1 + Number(rowData.taxRate) / 100)) * (Number(rowData.taxRate) / 100)
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '?????????????????????',
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
                            title: '????????????????????????',
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
                            title: '???????????????',
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
                            title: '????????????',
                            dataIndex: 'isDeduct',
                            key: 'isDeduct',
                            width: 100,
                            align: 'center',
                            render: () => {
                              let isDeduct = this.table.btnCallbackFn.form.getFieldValue('isDeduct')
                              if (isDeduct === '1') {
                                return '???'
                              } else {
                                return '???'
                              }
                            },
                          }
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'remarks',
                            key: 'remarks',
                            width: 150,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: "string",
                            dependencies: ['alterType'],
                            dependenciesReRender: true,
                            placeholder: '?????????'
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