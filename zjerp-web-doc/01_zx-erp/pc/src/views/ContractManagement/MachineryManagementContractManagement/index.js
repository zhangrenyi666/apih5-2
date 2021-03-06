import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Button, Drawer, Tabs } from 'antd';
import QnnForm from "../../modules/qnn-table/qnn-form";
import Operation from './operation';
import DeetailForm from '../MachineryManagementContract/detail';
import SelectFilesDownLoad from '../SelectFilesDownLoad';
const { TabPane } = Tabs;
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'contractID',
    size: 'small',
  },
  drawerConfig: {
    width: '80%'
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
      flowData: null,
      visible: false,
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
      activeKey: '1',
      loginUser: curCompany.realLabel
    }
  }
  onClose = () => {
    this.setState({
      visible: false,
    });
  };
  tabsCallback = (activeKey) => {
    this.setState({ activeKey })
  }
  render() {
    const { flowData, visible, activeKey, departmentId, loginUser } = this.state;
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
            apiName: 'getZxCtEqContractList',
            otherParams: { orgID: departmentId }
          }}
          componentsKey={{
            DeetailForm: (obj) => {
              return <DeetailForm isInQnnTable={obj.isInQnnTable}
                {...this.props}
                flowData={obj.qnnTableInstance.getSelectedRows()[0]}
              />
            },
          }}
          method={{
            //??????????????????????????????
            hideForEditCancel: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (obj.rowData.contractCategory === '0') {
                if (index === "0" || index === '3' || index === '5') {
                  return false;
                }
              }
              if (obj.rowData.contractCategory === '1') {
                if (index === "0" || index === '3' || index === '4') {
                  return false;
                }
              }
              return true;
            },
            //??????1????????????????????????
            hideForNo_1SaveBtn: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              console.log(index);
              if (index === "0") {
                return false;
              } else {
                return true;
              }
            },
            //????????????????????????
            cbForNo_1EditSave: (obj) => {
              if (obj.response.success) {
                this.table.setTabsIndex('1');
              }
            },
            //??????3????????????????????????
            hideForNo_3SaveBtn: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index === "3") {
                return false;
              } else {
                return true;
              }
            },
            //???????????????????????????
            cbForNo_3EditSave: async (obj) => {
              let params = {}
              let zxCtEqCoContractAmtRateList = await this.tableFour.getTableData()
              let emptyData = []
              await zxCtEqCoContractAmtRateList.map((item) => {
                if (!item.statisticsName) emptyData.push('?????????')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`?????????${emptyData.join()}`)
                return {}
              } else {
                await zxCtEqCoContractAmtRateList.map((item) => {
                  if (item.addFlag === '1') item.zxCtEqCoContractAmtRateId = null
                  return item
                })
                params.zxCtEqCoContractAmtRateList = zxCtEqCoContractAmtRateList
                params.contractID = obj.rowData.contractID
                return {
                  apiName: 'batchSaveUpdateZxCtEqCoContractAmtRate',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      obj.qnnTableInstance.refresh()
                      this.tableFour.refresh()
                    } else {
                      Msg.error(message);
                    }
                  }
                }
              }
            },
            //??????4????????????????????????
            hideForNo_4SaveBtn: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (obj.rowData.contractCategory === '0' && index !== "5") {
                return true
              }
              if (obj.rowData.contractCategory === '1' && index !== "4") {
                return true
              }
              return false
            },
            //??????4??????????????????
            hideForNo_4Fetch: (obj) => {
              let params = {}
              params.alterContractSum = obj.rowData.alterContractSum
              params.contractNo = obj.rowData.contractNo
              params.contractName = obj.rowData.contractName
              params.secondName = obj.rowData.secondName
              params.contractID = obj.rowData.contractID
              return {
                apiName: 'updateZxCtEqContract',
                otherParams: params
              }
            },
            //???????????????????????????
            disabledForDiyBtn: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1) {
                return true;
              } else {
                return false;
              }
            },
            //??????????????????
            disabledForDelete: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              let disabledLength = 0
              for (var i = 0; i < data.length; i++) {
                if (data[0].settleType !== '???????????????') {
                  disabledLength++
                }
              }
              if (data.length === 0 || disabledLength > 0) return true
            },
            //?????????????????????
            cbForDiyBtn: (obj) => {
              confirm({
                content: obj.btnCallbackFn.getSelectedRows()?.[0].contractStatus === '3' ? '??????????????????????' : '???????????????',
                onOk: () => {
                  let params = {}
                  params.contractID = obj.selectedRows[0].contractID
                  this.props.myFetch('recoverZxCtEqContract', params).then(
                    ({ success, message }) => {
                      if (success) {
                        Msg.success(message);
                        this.table.clearSelectedRows();
                        obj.btnCallbackFn.refresh();
                      } else {
                        Msg.error(message);
                      }
                    }
                  );
                }
              })
            },
            //???????????????????????????
            cbForDiyView: (obj) => {
              this.props.myFetch('openFlowByReport', { apiName: 'getZxCtEqContrApplyDetail', apiType: 'POST', flowId: 'zxCtEqContrApply', workId: obj.btnCallbackFn.getSelectedRows()[0].workId }).then(
                ({ success, message, data }) => {
                  if (success) {
                    this.setState({
                      activeKey: '1',
                      flowData: data,
                      visible: true
                    })
                  } else {
                    Msg.error(message)
                  }
                }
              );
            }
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "MachineryManagementContractManagement"
              }
            }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.clearSelectedRows();
              this.table.refresh();
            }
          }}
          formConfig={[
            {
              table: {
                title: '????????????',
                dataIndex: 'contractNo',
                key: 'contractNo',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
                onClick: 'detail',
              },
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractName',
                key: 'contractName',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'pp3',
                key: 'pp3',
                width: 100,
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
                title: '????????????',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                width: 150,
                render: (data) => {
                  if (data === '0') {
                    return '????????????';
                  } else if (data === '1') {
                    return '????????????';
                  }
                }
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'settleType',
                key: 'settleType',
                width: 150,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'signatureDate',
                key: 'signatureDate',
                width: 120,
                filter: true,
                format: 'YYYY-MM-DD',
                fieldsConfig: {
                  type: 'rangeDate',
                  field: 'signatureDateSearch'
                },
              }
            },
            {
              table: {
                title: '??????????????????(??????)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                width: 150
              }
            },
            {
              table: {
                title: '???????????????????????????(??????)',
                dataIndex: 'alterContractSum',
                key: 'alterContractSum',
                width: 200,
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
                dataIndex: 'contractStatus',
                key: 'contractStatus',
                width: 100,
                render: (h) => {
                  if (h === '1') {
                    return '?????????'
                  } else if (h === '3') {
                    return '??????'
                  }
                }
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'csTimeLimit',
                key: 'csTimeLimit',
                width: 150,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '??????????????????',
                dataIndex: 'auditStatus',
                key: 'auditStatus',
                width: 150,
                filter: true,
              },
              form: {
                type: 'string',
              }
            },
            {
              table: {
                title: '??????????????????',
                dataIndex: 'planStartDate',
                key: 'planStartDate',
                width: 120,
                format: "YYYY-MM-DD"
              },
              isInForm: false
            },
            {
              table: {
                title: '??????',
                dataIndex: 'zxErpFileList',
                key: 'zxErpFileList',
                width: 100,
                fixed:'right',
                align:'center',
                render: (val,obj) => {
                  if (obj?.zxErpFileList.length) {
                    return <SelectFilesDownLoad dataList={obj?.zxErpFileList} />
                  } else {
                    return '?????????'
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
              content: {
                formConfig: [
                  {
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    type: "string",
                    field: "contractID",
                    hide: true
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comName',
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
                    field: 'orgID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'firstId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '????????????',
                    field: 'contractNo',
                    type: 'string',
                    disabled: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'signatureDate',
                    type: 'date',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'contractName',
                    type: 'string',
                    required: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'pp3',
                    type: 'string',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '????????????',
                    field: 'firstName',
                    type: 'string',
                    disabled: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "secondID",
                    type: 'selectByPaging',
                    optionConfig: {
                      label: 'customerName',
                      value: 'zxCrCustomerNewId',
                      linkageFields: {
                        secondName: 'customerName',
                      }
                    },
                    fetchConfig: {
                      apiName: 'getZxCrCustomerNewList',
                      searchKey: 'customerName'
                    },
                    allowClear: false,
                    showSearch: true,
                    required: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'agent',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'registerDate',
                    type: 'date',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'projectManager',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'secondPrincipal',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: 'planEndDate',
                    type: 'date',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: 'firstUnitTel',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????(??????)',
                    field: 'secondUnitTel',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'contractCategory',
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '????????????', value: '0' },
                      { label: '????????????', value: '1' },
                    ],
                    placeholder: '?????????',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'settleType',
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '??????????????????(??????)',
                    field: 'contractCost',
                    type: 'number',
                    disabled: true,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: 'contractCostNoTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '????????????(??????)',
                    field: 'contractCostTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '???????????????????????????(??????)',
                    field: 'alterContractSum',
                    type: 'number',
                    placeholder: '?????????',
                    disabled: true,
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '??????????????????????????????(??????)',
                    field: 'alterContractSumNoTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6,
                    initialValue: 0
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: 'alterContractSumTax',
                    type: 'number',
                    disabled: true,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    label: '????????????',
                    field: "isDeduct",
                    span: 12,
                    type: 'radio',
                    optionData: [
                      { label: '???', value: '1' },
                      { label: '???', value: '0' }
                    ],
                    initialValue: '0',
                    disabled: true,
                  },
                  {
                    label: '??????(??????)',
                    field: 'pp1',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },

                  {
                    label: '????????????',
                    field: 'pp2',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'csTimeLimit',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: 'auditStatus',
                    type: 'string',
                    disabled: true,
                    span: 12,
                  },
                  {
                    label: '????????????',
                    field: 'contractStatus',
                    type: 'select',
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: '?????????',
                        value: '1'
                      },
                      {
                        label: '??????',
                        value: '3'
                      }
                    ],
                    allowClear: false,
                    showSearch: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: 'planStartDate',
                    type: 'date',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'rentType',
                    type: 'select',
                    optionConfig: {
                      label: 'itemName', //?????? label
                      value: 'itemName'
                    },
                    optionData: [
                      {
                        itemName: '????????????'
                      },
                      {
                        itemName: '???????????????'
                      }
                    ],
                    allowClear: false,
                    showSearch: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'content',
                    type: 'textarea',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: 'zxErpFileList',
                    type: 'files',
                    required: true,
                    fetchConfig: {
                      apiName: 'upload'
                    },
                    span: 12
                  }
                ]
              }
            },
            {
              field: "form2",
              name: "qnnTable",
              title: "????????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContrItemList',
                    otherParams: { contractID: obj.tableFns.form.getFieldValue('contractID') },
                  }
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtEqContrItemId",
                },
                paginationConfig: { position: 'bottom' },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                isShowRowSelect: true,
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxEqResCategoryId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtEqContrItemId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    isInTable: false,
                    form: {
                      field: 'parentID',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '1') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '??????',
                      dataIndex: 'catCode',
                      key: 'catCode',
                      width: 150,
                      fixed: 'left',
                      align: 'center',
                    },
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '0') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '??????',
                      width: 150,
                      fixed: 'left',
                      dataIndex: 'catCode',
                      align: 'center',
                      key: 'catCode',
                    },
                  },

                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '0') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '????????????',
                      dataIndex: 'catParentName',
                      key: 'catParentName',
                      width: 150,
                      fixed: 'left',
                      align: 'center',
                    },
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '1') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '????????????',
                      width: 150,
                      fixed: 'left',
                      dataIndex: 'catName',
                      align: 'center',
                      key: 'catName',
                    }
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '0') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '??????',
                      dataIndex: 'catName',
                      key: 'catName',
                      width: 150,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'spec',
                      key: 'spec',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '0') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '??????',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      align: 'center',
                    }
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '1') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '????????????',
                      dataIndex: 'rentUnit',
                      key: 'rentUnit',
                      width: 100,
                      align: 'center',
                    }
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '1') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '??????????????????',
                      dataIndex: 'actualStartDate',
                      key: 'actualStartDate',
                      format: 'YYYY-MM-DD',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      placeholder: '?????????'
                    }
                  },
                  {
                    isInTable: () => {
                      if (this.table?.form?.getFieldValue('contractCategory') === '1') {
                        return false
                      }
                      return true
                    },
                    table: {
                      title: '??????????????????',
                      dataIndex: 'actualEndDate',
                      format: 'YYYY-MM-DD',
                      key: 'actualEndDate',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'date',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'qty',
                      key: 'qty',
                      width: 120,
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      type: 'number',
                    }
                  },
                  {
                    table: {
                      title: '??????????????????(???/???)',
                      dataIndex: 'price',
                      key: 'price',
                      width: 180,
                      align: 'center',
                    },
                    form: {
                      precision: 6,
                      type: 'number',
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                      }
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      precision: 6,
                    }
                  },

                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'contractSumNoTax',
                      key: 'contractSumNoTax',
                      width: 150,
                      align: 'center',
                      render: (val, rowData) => {
                        return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                      }
                    },
                  },
                  {
                    table: {
                      title: '??????(%)',
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100,
                      align: 'center',
                    },
                    form: {
                      type: 'select',
                      optionConfig: {
                        label: 'itemName',
                        value: 'itemId',
                      },
                      fetchConfig: {
                        apiName: 'getBaseCodeSelect',
                        otherParams: { itemId: 'shuiLv' }
                      },
                    }
                  },
                  {
                    table: {
                      title: '???????????????',
                      dataIndex: 'alterTrrm',
                      key: 'alterTrrm',
                      width: 120,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'alterPrice',
                      key: 'alterPrice',
                      width: 150,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'alterPriceNoTax',
                      key: 'alterPriceNoTax',
                      width: 150,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '?????????????????????',
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
                      title: '????????????????????????',
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
                      title: '???????????????',
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
                      dataIndex: 'remark',
                      key: 'remark',
                      width: 150,
                      align: 'center',
                    }
                  },
                ]
              }
            },
            {
              field: "table4",
              name: "qnnTable",
              title: "????????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContrSupplyListBycontractID',
                    otherParams: {
                      contractID: obj.tableFns.form.getFieldValue('contractID'),
                      apih5FlowStatus: '2'
                    },
                  }
                },
                drawerConfig: {
                  width: window.screen.width * 0.65
                },
                wrappedComponentRef: (me) => {
                  this.TableTwo = me
                },
                antd: {
                  rowKey: 'zxCtEqContrSupplyId',
                  size: 'small'
                },
                paginationConfig: {
                  position: "bottom"
                },
                desc: '???????????????????????????????????????',
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'edit',
                    icon: 'edit',
                    type: 'primary',
                    label: '??????',
                    formBtns: [
                      {
                        name: 'cancel',
                        type: 'dashed',
                        label: '??????',
                        hide: (obj) => {
                          var index = obj.btnCallbackFn.getTabsIndex();
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
                        label: '??????',
                        hide: (obj) => {
                          var index = obj.btnCallbackFn.getTabsIndex();
                          if (index === "0") {
                            return false;
                          } else {
                            return true;
                          }
                        },
                        onClick: (obj) => {
                          obj.btnCallbackFn.clearSelectedRows();
                          obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                          this.props.myFetch('updateZxCtEqContrSupplyForContractTab', obj._formData).then(
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
                  }
                ],
                formConfig: [
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'contractNo',
                      key: 'contractNo',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 180,
                      onClick: 'detail'
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
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
                      initialValue: '???????????????????????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'firstName',
                      key: 'firstName',
                      width: 150,
                      filter: true,
                      fieldsConfig: { type: 'string' },
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'secondName',
                      key: 'secondName',
                      width: 150,
                      filter: true,
                      fieldsConfig: { type: 'string' },
                    }
                  },
                  {
                    table: {
                      title: '???????????????',
                      dataIndex: 'agent',
                      key: 'agent',
                      width: 100,
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'contractCost',
                      key: 'contractCost',
                      width: 120,
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'thisAmount',
                      key: 'thisAmount',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'alterContractSum',
                      key: 'alterContractSum',
                      width: 150,
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
                      title: '????????????',
                      dataIndex: 'csTimeLimit',
                      key: 'csTimeLimit',
                      width: 100
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
                  },
                ],
                tabs: [
                  {
                    field: "form1",
                    name: "qnnForm",
                    title: "????????????????????????",
                    desc: '???????????????????????????????????????',
                    content: {
                      formConfig: [
                        {
                          field: "contrAlterID",
                          type: 'string',
                          hide: true,
                        },
                        {
                          field: "apih5FlowStatus",
                          type: 'string',
                          hide: true,
                        },
                        {
                          field: "zxCtEqContrSupplyId",
                          type: 'string',
                          hide: true,
                        },
                        {
                          label: '??????????????????',
                          field: "contractNo",
                          type: 'string',
                          disabled: true,
                          span: 12
                        },
                        {
                          label: '??????????????????',
                          field: "supplyName",
                          type: 'string',
                          placeholder: '?????????',
                          span: 12,
                          required: true,
                        },
                        {
                          label: '????????????',
                          field: "contractName",
                          disabled: true,
                          span: 12,
                          type: 'string',
                        },
                        {
                          label: '????????????',
                          field: "contractType",
                          type: 'string',
                          span: 12,
                          disabled: true,
                          initialValue: '???????????????????????????'
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
                          label: '??????????????????',
                          field: "contractCost",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '?????????????????????',
                          field: "contractCostNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '????????????',
                          field: "contractCostTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '????????????????????????',
                          field: "thisAmount",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '???????????????????????????',
                          field: "thisAmountNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '??????????????????',
                          field: "thisAmountTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '?????????????????????',
                          field: "alterContractSum",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '????????????????????????',
                          field: "alterContractSumNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '???????????????',
                          field: "alterContractSumTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '????????????',
                          field: "isDeduct",
                          disabled: true,
                          span: 12,
                          initialValue: '0',
                          type: 'radio',
                          optionData: [
                            { label: '???', value: '1' },
                            { label: '???', value: '0' }
                          ],
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
                          type: 'string',
                          span: 12,
                          disabled: true,
                        },
                        {
                          label: '????????????',
                          field: "replyDate",
                          span: 12,
                          type: 'date',
                          disabled: true,
                          format: 'YYYY-MM-DD',
                        },
                        {
                          label: '????????????',
                          field: 'content',
                          type: 'textarea',
                          span: 12
                        },
                        {
                          label: '????????????',
                          field: 'alterContent',
                          type: 'textarea',
                          span: 12,
                          disabled: true,
                        },
                        {
                          label: '????????????',
                          field: 'alterReason',
                          span: 12,
                          type: 'textarea',
                          disabled: true,
                        },
                        {
                          label: '??????',
                          span: 12,
                          field: 'remark',
                          type: 'textarea',
                          placeholder: '?????????',
                        }
                      ]
                    }
                  },
                  {
                    field: "table1",
                    name: "qnnTable",
                    title: "??????????????????",
                    actionBtnsPosition: "top",
                    disabled: function (obj) {
                      return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : true)
                    },
                    content: {
                      fetchConfig: (obj) => {
                        let contrAlterID = this.TableTwo.form.getFieldValue('contrAlterID')
                        return {
                          apiName: 'getZxCtEqContrAlterItemResoureList',
                          otherParams: { contrAlterID }
                        }
                      },
                      antd: {
                        size: "small",
                        rowKey: "zxCtFsSideAgreementInventoryId",
                      },
                      formConfig: [
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'alterType',
                            key: 'alterType',
                            width: 120,
                            align: 'center',
                            fixed: 'left',
                            type: 'select',
                          },
                          form: {
                            type: 'select',
                            optionConfig: {
                              label: 'label',
                              value: 'value',
                            },
                            optionData: [
                              { label: '??????', value: '1', },
                              { label: '??????', value: '2', }
                            ]
                          }
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'catCode',
                            key: 'catCode',
                            width: 100,
                            align: 'center',
                            fixed: 'left',
                          },
                        },
                        {
                          table: {
                            title: '????????????',
                            width: 180,
                            dataIndex: 'catName',
                            key: 'catName',
                            align: 'center',
                            fixed: 'left',
                          }
                        },
                        {
                          table: {
                            title: '??????',
                            width: 120,
                            dataIndex: 'spec',
                            key: 'spec',
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'rentUnit',
                            key: 'rentUnit',
                            width: 100,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '??????????????????',
                            dataIndex: 'actualStartDate',
                            key: 'actualStartDate',
                            width: 100,
                            align: 'center',
                            format: 'YYYY-MM-DD',
                          }
                        },
                        {
                          table: {
                            title: '??????????????????',
                            dataIndex: 'actualEndDate',
                            key: 'actualEndDate',
                            width: 100,
                            align: 'center',
                            format: 'YYYY-MM-DD',
                          }
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'qty',
                            key: 'qty',
                            width: 100,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '??????????????????',
                            dataIndex: 'price',
                            key: 'price',
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '??????????????????',
                            dataIndex: 'contractSum',
                            key: 'contractSum',
                            width: 120,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '?????????????????????',
                            dataIndex: 'priceNoTax',
                            key: 'priceNoTax',
                            width: 120,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '?????????????????????',
                            dataIndex: 'contractSumNoTax',
                            key: 'contractSumNoTax',
                            width: 120,
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
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'isDeduct',
                            key: 'isDeduct',
                            width: 100,
                            align: 'center',
                            render: (data) => {
                              if (data === '1') {
                                return '???';
                              } else {
                                return '???';
                              }
                            }
                          }
                        },
                        {
                          table: {
                            title: '???????????????????????????',
                            dataIndex: 'alterRentStartDate',
                            key: 'alterRentStartDate',
                            span: 12,
                            width: 150,
                            format: 'YYYY-MM-DD',
                          }
                        },
                        {
                          table: {
                            title: '???????????????????????????',
                            dataIndex: 'alterRentEndDate',
                            key: 'alterRentEndDate',
                            span: 12,
                            width: 150,
                            format: 'YYYY-MM-DD',
                          }
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'alterTrrm',
                            key: 'alterTrrm',
                            width: 120,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '?????????????????????',
                            dataIndex: 'alterPrice',
                            key: 'alterPrice',
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '????????????????????????',
                            dataIndex: 'alterPriceNoTax',
                            key: 'alterPriceNoTax',
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '?????????????????????',
                            dataIndex: 'alterAmt',
                            key: 'alterAmt',
                            width: 120,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '????????????????????????',
                            dataIndex: 'alterAmtNoTax',
                            key: 'alterAmtNoTax',
                            width: 150,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'alterAmtTax',
                            key: 'alterAmtTax',
                            width: 150,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'remark',
                            key: 'remark',
                            width: 150,
                            align: 'center',
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            },
            {
              field: "table5",
              name: "qnnTable",
              hide: (obj) => { return obj.clickCb?.rowData.contractCategory === '1' },
              title: "?????????????????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqCoContractAmtRateList',
                    otherParams: { contractID: obj.tableFns.form.getFieldValue('contractID') }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableFour = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtEqCoContractAmtRateId",
                },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'addRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '?????????',
                    addRowDefaultData: { addFlag: '1' }
                  },
                  {
                    name: 'delRow',
                    icon: 'del',
                    type: 'danger',
                    label: '??????',
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtEqCoContractAmtRateId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: '?????????',
                      dataIndex: 'statisticsName',
                      key: 'statisticsName',
                      width: 150,
                      tdEdit: true,
                      initialValue: 0
                    },
                    form: {
                      required: true,
                      field: 'statisticsName',
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????%',
                      dataIndex: 'statisticsRate',
                      key: 'statisticsRate',
                      width: 100,
                      tdEdit: true,
                      initialValue: 0
                    },
                    form: {
                      field: 'statisticsRate',
                      type: 'number',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'timeLimit',
                      key: 'timeLimit',
                      width: 150,
                      tdEdit: true
                    },
                    form: {
                      field: 'timeLimit',
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'backCondition',
                      key: 'backCondition',
                      width: 150,
                      tdEdit: true,
                    },
                    form: {
                      field: 'backCondition',
                      type: 'string',
                      placeholder: '?????????'
                    }
                  },
                ]
              }
            },
            {
              field: "table6",
              name: "qnnTable",
              title: "?????????",
              hide: (obj) => {
                let contractCategory = obj.clickCb?.rowData?.contractCategory
                if (contractCategory !== '0') {
                  return true
                } else {
                  return false
                }
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxSaEquipSettleAuditList',
                    otherParams: {
                      contractID: this.table?.form?.getFieldValue('contractID'),
                      apih5FlowStatus: '2',
                      orgID: departmentId,
                    }
                  }

                },
                antd: {
                  size: "small",
                  rowKey: "zxSaEquipSettleAuditId",
                },
                formConfig: [
                  {
                    table: {
                      title: '???????????????',
                      dataIndex: 'billNo',
                      key: 'billNo',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      fixed: 'left',
                      width: 170,
                      onClick: 'detail',
                    }
                  },
                  {
                    table: {
                      title: '????????????',
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
                      title: '????????????',
                      dataIndex: 'periodDate',
                      key: 'periodDate',
                      width: 120,
                      format: 'YYYY-MM'
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'billType',
                      key: 'billType',
                      width: 120,
                      render: (h) => {
                        if (h === '0') {
                          return '??????'
                        } else if (h === '1') {
                          return '??????'
                        } else if (h === '2') {
                          return '???????????????'
                        }
                      }
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'contractName',
                      key: 'contractName',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'secondName',
                      key: 'secondName',
                      filter: true,
                      fieldsConfig: { type: 'string' },
                      width: 180,
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'thisAmt',
                      key: 'thisAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'totalAmt',
                      key: 'totalAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '???????????????????????????',
                      dataIndex: 'thisPayAmt',
                      key: 'thisPayAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '???????????????????????????',
                      dataIndex: 'totalPayAmt',
                      key: 'totalPayAmt',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
                      dataIndex: 'notPassNum',
                      key: 'notPassNum',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'beginDate',
                      key: 'beginDate',
                      width: 150,
                      format: "YYYY-MM-DD"
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'endDate',
                      key: 'endDate',
                      width: 150,
                      format: "YYYY-MM-DD"
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'businessDate',
                      key: 'businessDate',
                      width: 100,
                      format: "YYYY-MM-DD"
                    }
                  },
                  {
                    table: {
                      title: '?????????',
                      dataIndex: 'createUserName',
                      key: 'createUserName',
                      width: 100,
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
                  },
                ],
                drawerConfig: {
                  width: '1100px'
                },
                tabs: [
                  {
                    field: "form1",
                    name: "qnnForm",
                    title: "????????????",
                    content: {
                      formConfig: [
                        {
                          hide: true,
                          field: 'zxSaEquipSettleAuditId',
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
                          label: '????????????',
                          field: "orgName",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '????????????',
                          field: "contractNo",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '???????????????',
                          field: "billNo",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '????????????',
                          field: "contractName",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '?????????????????????',
                          field: "signedNos",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '????????????',
                          field: "secondName",
                          type: 'string',
                          span: 12
                        },
                        {
                          label: '????????????',
                          field: "period",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '????????????',
                          field: "billType",
                          type: 'select',
                          span: 12,
                          optionConfig: {
                            label: 'label',
                            value: 'value',
                          },
                          optionData: [
                            { label: '??????', value: '0' },
                            { label: '??????', value: '1' },
                            { label: '???????????????', value: '2' }
                          ],
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
                          label: '?????????????????????',
                          field: "isFished",
                          type: 'radio',
                          optionData: [
                            { label: '???', value: '1' },
                            { label: '???', value: '0' }
                          ],
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
                          label: '??????????????????',
                          field: "isFirst",
                          type: 'radio',
                          optionData: [
                            { label: '???', value: '1' },
                            { label: '???', value: '0' }
                          ],
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
                          label: '???????????????????????????',
                          field: "autoNum",
                          span: 12,
                          placeholder: '?????????',
                          type: 'number',
                        },
                        {
                          label: '?????????',
                          field: "reportPerson",
                          type: 'string',
                          span: 12,
                          placeholder: '?????????',
                        },
                        {
                          label: '?????????',
                          field: "countPerson",
                          type: 'string',
                          span: 12,
                          placeholder: '?????????',
                        },
                        {
                          label: '?????????',
                          field: "reCountPerson",
                          type: 'string',
                          span: 12,
                          placeholder: '?????????',
                        },
                        {
                          label: '????????????',
                          field: "reportDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '????????????',
                          field: "businessDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '????????????????????????',
                          field: "beginDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '????????????????????????',
                          field: "endDate",
                          span: 12,
                          type: 'date',
                        },
                        {
                          label: '?????????????????????????????????',
                          field: 'appraisal',
                          type: 'textarea',
                          span: 12,
                        },
                        {
                          label: '?????????????????????',
                          field: 'content',
                          type: 'textarea',
                          span: 12,
                          placeholder: '?????????',
                        },
                        {
                          label: '????????????',
                          field: 'remark',
                          type: 'textarea',
                          span: 12,
                          placeholder: '?????????',
                        },
                        {
                          label: '??????',
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
                    field: "list1",
                    name: "qnnForm",
                    title: "????????????",
                    content: {
                      fetchConfig: () => {
                        return {
                          apiName: 'getZxSaEquipSettleAuditDetail',
                          otherParams: (obj) => {
                            return {
                              zxSaEquipSettleAuditId: obj.form.getFieldValue('zxSaEquipSettleAuditId')
                            }
                          }
                        }
                      },
                      antd: {
                        rowKey: 'zxSaEquipResSettleItemId',
                        size: 'small'
                      },
                      formConfig: [
                        {
                          label: '??????????????????(??????)',
                          field: "contractAmt",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '???????????????????????????(??????)',
                          field: "changeAmt",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '??????????????????????????????(???)',
                          field: "thisAmtRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '?????????????????????????????????(???)',
                          field: "thisAmtNoTaxRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '????????????????????????(???)',
                          field: "thisAmtTaxRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '??????????????????????????????(???)',
                          field: "totalAmtRes",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          type: 'qnnTable',
                          field: 'table2',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '1') {
                                return {
                                  apiName: 'getZxSaEquipResSettleItemList',
                                  otherParams: { zxSaEquipResSettleId: obj.qnnFormProps.form.getFieldValue('zxSaEquipResSettleId') },
                                }
                              } else {
                                return {}
                              }
                            },
                            antd: {
                              rowKey: 'zxSaEquipResSettleItemId',
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
                                  label: '??????id',
                                  field: 'zxSaEquipResSettleItemId',
                                }
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'equipCode',
                                  key: 'equipCode',
                                  align: 'center',
                                  fixed: 'left',
                                  width: 150,
                                }
                              },
                              {
                                table: {
                                  title: '????????????',
                                  dataIndex: 'equipName',
                                  key: 'equipName',
                                  align: 'center',
                                  fixed: 'left',
                                  width: 150,
                                }
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'spec',
                                  key: 'spec',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'unit',
                                  key: 'unit',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '????????????',
                                  dataIndex: 'startDate',
                                  key: 'startDate',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '??????(???)',
                                  dataIndex: 'contractPrice',
                                  key: 'contractPrice',
                                  align: 'center',
                                  width: 100,
                                }
                              },
                              {
                                table: {
                                  title: '????????????',
                                  dataIndex: 'contractQty',
                                  key: 'contractQty',
                                  width: 100,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????(???)',
                                  dataIndex: 'contractAmt',
                                  key: 'contractAmt',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '?????????????????????(???)',
                                  dataIndex: 'changedAmt',
                                  key: 'changedAmt',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '???????????????',
                                  dataIndex: 'changedQty',
                                  key: 'changedQty',
                                  width: 120,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????(%)',
                                  dataIndex: 'taxRate',
                                  key: 'taxRate',
                                  width: 100,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????',
                                  dataIndex: 'thisQty',
                                  key: 'thisQty',
                                  width: 120,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????(??????)',
                                  dataIndex: 'runHour',
                                  key: 'runHour',
                                  width: 150,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '????????????(L)',
                                  dataIndex: 'useOil',
                                  key: 'useOil',
                                  width: 120,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '????????????????????????(???)',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '???????????????????????????(???)',
                                  dataIndex: 'thisAmtNoTax',
                                  key: 'thisAmtNoTax',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????(???)',
                                  dataIndex: 'thisAmtTax',
                                  key: 'thisAmtTax',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????????????????',
                                  dataIndex: 'upQty',
                                  key: 'upQty',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '????????????????????????????????????(???)',
                                  dataIndex: 'upAmt',
                                  key: 'upAmt',
                                  width: 250,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????????????????',
                                  dataIndex: 'totalQty',
                                  key: 'totalQty',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????????????????',
                                  dataIndex: 'totalAmt',
                                  key: 'totalAmt',
                                  width: 180,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'remark',
                                  key: 'remark',
                                  width: 120,
                                  align: 'center',
                                }
                              }
                            ],
                          }
                        }
                      ]
                    }
                  },
                  {
                    field: "list2",
                    name: "qnnForm",
                    title: "???????????????",
                    content: {
                      fetchConfig: () => {
                        return {
                          apiName: 'getZxSaEquipSettleAuditDetail',
                          otherParams: (obj) => {
                            return {
                              zxSaEquipSettleAuditId: obj.form.getFieldValue('zxSaEquipSettleAuditId')
                            }
                          }
                        }
                      },
                      formConfig: [
                        {
                          label: '?????????????????????????????????(???)',
                          field: "thisAmtPay",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '????????????????????????????????????(???)',
                          field: "thisAmtNoTaxPay",
                          type: 'number',
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
                          label: '???????????????????????????(???)',
                          field: "thisAmtTaxPay",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '???????????????????????????(???)',
                          field: "totalAmtPay",
                          type: 'number',
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
                          label: '??????????????????',
                          field: "inOutAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '????????????',
                          field: "fineAmt",
                          type: 'number',
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
                          label: '???????????????',
                          field: "foodAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '??????????????????',
                          field: "otherAmt",
                          type: 'number',
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
                          field: 'table3',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '2') {
                                return {
                                  apiName: 'getZxSaEquipPaySettleItemList',
                                  otherParams: { zxSaEquipPaySettleId: obj.qnnFormProps.form.getFieldValue('zxSaEquipPaySettleId') },
                                }
                              } else {
                                return {}
                              }
                            },
                            antd: {
                              rowKey: 'zxSaEquipPaySettleItemId',
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
                                  label: '??????id',
                                  field: 'zxSaEquipPaySettleItemId',
                                  hide: true
                                }
                              },
                              {
                                table: {
                                  title: '???????????????',
                                  dataIndex: 'payType',
                                  key: 'payType',
                                  fixed: 'left',
                                  width: 120
                                },
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'payName',
                                  key: 'payName',
                                  fixed: 'left',
                                  width: 150,
                                }
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'unit',
                                  key: 'unit',
                                },
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'qty',
                                  key: 'qty',
                                }
                              },
                              {
                                table: {
                                  title: '??????(???)',
                                  dataIndex: 'price',
                                  key: 'price',
                                  width: 120
                                }
                              },
                              {
                                table: {
                                  title: '??????(%)',
                                  dataIndex: 'taxRate',
                                  key: 'taxRate',
                                  width: 100
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????(???)',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 150,
                                }
                              },

                              {
                                table: {
                                  title: '???????????????????????????(???)',
                                  dataIndex: 'thisAmtNoTax',
                                  key: 'thisAmtNoTax',
                                  width: 170,
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????(???)',
                                  dataIndex: 'thisAmtTax',
                                  key: 'thisAmtTax',
                                  width: 140,
                                }
                              },
                              {
                                table: {
                                  title: '??????',
                                  dataIndex: 'remark',
                                  key: 'remark',
                                  width: 120,
                                }
                              }
                            ],
                          }
                        }
                      ]
                    }
                  },
                  {
                    field: "table4",
                    name: "qnnForm",
                    title: "?????????",
                    content: {
                      formConfig: [
                        {
                          type: 'qnnTable',
                          field: 'table4_1',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              return {
                                apiName: 'getZxSaEquipSettleAuditItemList',
                                otherParams: () => {
                                  return {
                                    zxSaEquipSettleAuditId: obj.qnnFormProps.form.getFieldValue('zxSaEquipSettleAuditId')
                                  }
                                }
                              }
                            },
                            antd: {
                              size: "small",
                              rowKey: "zxSaEquipSettleAuditItemId",
                            },
                            paginationConfig: false,
                            isShowRowSelect: false,
                            pageSize: 999999,
                            formConfig: [
                              {
                                table: {
                                  title: '?????????',
                                  dataIndex: 'statisticsName',
                                  key: 'statisticsName',
                                  width: 100,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '???????????????',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 150,
                                  tdEdit: true,
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '???????????????',
                                  dataIndex: 'totalAmt',
                                  key: 'totalAmt',
                                  width: 100,
                                  align: 'center',
                                },
                              }
                            ],
                          }
                        },
                      ]
                    }
                  }
                ]
              }
            },
            {
              field: "form1",
              name: "qnnForm",
              title: "????????????",
              hide: (obj) => { return obj.clickCb?.rowData.contractCategory === '1' },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContractDetail',
                    otherParams: { contractID: this.table?.form?.getFieldValue('contractID') },
                    success: ({ data, success }) => {
                      let datas = data
                      if (success) {
                        this.props.myFetch('getZxSaEquipSettleAuditTotalAmt', { contractID: this.table?.form?.getFieldValue('contractID') })
                          .then(({ data, success }) => {
                            if (success) {
                              obj.form.setFieldsValue({
                                pp4: data.totalAmt,
                                pp4_pp5: Number(((data.totalAmt ? data.totalAmt : 0) - (datas?.pp5 ? Number(datas.pp5) : 0)).toFixed(2))
                              })
                            }
                          })
                      }
                    }
                  }
                },
                formConfig: [
                  {
                    type: "component",
                    field: "component2",
                    Component: obj => {
                      return (
                        <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                          ????????????
                        </div>
                      );
                    }
                  },
                  {
                    label: '????????????',
                    field: 'contractNames',
                    type: 'string',
                    disabled: true,
                    placeholder: '?????????',
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: 'contractNos',
                    type: 'string',
                    disabled: true,
                    span: 8,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 6 },
                        sm: { span: 6 }
                      },
                      wrapperCol: {
                        xs: { span: 18 },
                        sm: { span: 18 }
                      }
                    }
                  },
                  {
                    label: '????????????',
                    field: "secondNames",
                    type: 'string',
                    span: 8,
                    disabled: true,
                  },
                  {
                    label: '???????????????????????????',
                    field: 'alterContractSums',
                    type: 'number',
                    placeholder: '?????????',
                    disabled: true,
                    span: 8,
                    precision: 6,
                    initialValue: 0,
                  },
                  {
                    type: "component",
                    field: "component3",
                    Component: obj => {
                      return (
                        <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                          ????????????
                        </div>
                      );
                    }
                  },
                  {
                    label: '??????????????????',
                    field: 'secondIDCode',
                    type: 'selectByQnnTable',
                    optionConfig: {
                      label: 'lswldwWldwbh',
                      value: 'lswldwWldwbh',
                      linkageFields: {
                        secondIDCodeName: 'lswldwDwmc',
                        orgCertificate: 'lswldwSh',
                      }
                    },
                    onChange: (val, obj) => {
                      if (!val) {
                        obj.form.setFieldsValue({
                          secondIDCodeName: null,
                          orgCertificate: null,
                        })
                      } else {
                        obj.form.setFieldsValue({
                          zjxmhtbBh: null,
                          pp5: null,
                          pp4_pp5: null,
                          accountUnitName: null,
                          accountUnitCode: null,
                          fiCtrState: null,
                          invoiceType: null,
                          ctrNature: null,
                          revenueDir: null,
                          keyCtr: null,
                          staCtr: null,
                          advanceRate: null,
                          progressRate: null,
                          completionRate: null,
                          zjgcxmXmmc: null,
                          taxCountWay: null,
                        })
                      }
                    },
                    required: true,
                    dropdownMatchSelectWidth: 800,
                    qnnTableConfig: {
                      antd: {
                        rowKey: "lswldwWldwbh"
                      },
                      fetchConfig: {
                        apiName: "getZxSaFiWldwList",
                        otherParams: () => {
                          return {
                            lswldwDwmc: this.table?.form?.getFieldValue('secondName')
                          }
                        }
                      },
                      searchBtnsStyle: "inline",
                      formConfig: [
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "lswldwDwmc",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "lswldwSh",
                            title: "????????????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "lswldwWldwbh",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                      ]
                    },
                    placeholder: '?????????',
                    span: 8
                  },
                  {
                    label: '??????????????????',
                    field: 'secondIDCodeName',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '????????????????????????',
                    field: 'orgCertificate',
                    type: 'string',
                    placeholder: '?????????',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '??????????????????',
                    field: "zjxmhtbMc",
                    type: 'selectByQnnTable',
                    span: 8,
                    required: true,
                    dependencies: ['secondIDCode'],
                    dependenciesReRender: true,
                    parent: 'secondIDCode',
                    allowClear: false,
                    optionConfig: {
                      label: 'zjxmhtbHtmc',
                      value: 'zjxmhtbHtmc',
                      linkageFields: {
                        zjxmhtbBh: 'zjxmhtbBh',//??????????????????
                        pp5: 'klzfje',//??????????????????
                        accountUnitName: 'zjxmhtbDwmc',//??????????????????
                        accountUnitCode: 'zjxmhtbDwbh',//??????????????????
                        fiCtrState: 'htzt',//??????????????????
                        invoiceType: 'fplx',//????????????
                        ctrNature: 'htxz',//????????????
                        revenueDir: 'szfx',//????????????
                        keyCtr: 'sfzd',//????????????
                        staCtr: 'sfzs',//????????????
                        advanceRate: 'yfkbl',//?????????
                        progressRate: 'jdkbl',//??????
                        completionRate: 'jgkbl',//??????
                        zjgcxmXmmc: 'zjgcxmXmmc',//????????????
                        taxCountWay: 'jsff'//????????????
                      }
                    },
                    onChange: (val, obj) => {
                      let DrawerData = obj.form.getFieldsValue()
                      obj.form.setFieldsValue({
                        pp4_pp5: DrawerData.pp4 - DrawerData.pp5 >= 0 ? DrawerData.pp4 - DrawerData.pp5 : 0
                      })
                    },
                    dropdownMatchSelectWidth: 800,
                    qnnTableConfig: {
                      antd: {
                        rowKey: "zjxmhtbNm"
                      },
                      fetchConfig: (obj) => {
                        let pageData = obj.props.qnnFormProps?.form?.getFieldsValue()
                        return {
                          apiName: "getZxSaFiZjxmhtbInHookup",
                          otherParams: {
                            zjxmhtbKhnm: pageData?.secondIDCode,
                            zjxmhtbBh: pageData?.contractNo,
                            contractID: pageData?.contractID,
                          }
                        }

                      },
                      searchBtnsStyle: "inline",
                      formConfig: [
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "zjxmhtbBh",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "zjxmhtbHtmc",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "klzfje",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "zjxmhtbDwmc",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                      ]
                    },
                  },
                  {
                    label: '??????????????????',
                    field: 'zjxmhtbBh',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '??????????????????(PM)',
                    field: 'pp4',
                    type: 'number',
                    disabled: true,
                    span: 8,
                  },
                  {
                    label: '????????????',
                    field: 'accountUnitName',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '??????????????????',
                    field: 'accountUnitCode',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '??????????????????(GS)',
                    field: 'pp5',
                    type: 'number',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: "accountDepName",
                    type: 'selectByQnnTable',
                    span: 8,
                    required: true,
                    dependencies: ['zjxmhtbMc'],
                    dependenciesReRender: true,
                    parent: 'zjxmhtbMc',
                    optionConfig: {
                      label: 'lsbmzdDwmc',
                      value: 'lsbmzdDwmc',
                      linkageFields: {
                        accountDepCode: 'lsbmzdBmbh'//??????????????????
                      }
                    },
                    onChange: (val, obj) => {
                      if (!val) {
                        obj.form.setFieldsValue({
                          accountDepCode: null,
                        })
                      }
                    },
                    dropdownMatchSelectWidth: 800,
                    qnnTableConfig: {
                      antd: {
                        rowKey: "lsbmzdDwmc"
                      },
                      fetchConfig: (obj) => {
                        let pageData = obj.props.qnnFormProps?.form?.getFieldsValue()
                        return {
                          apiName: "getZxSaFiBmzdList",
                          otherParams: {
                            lsbmzdDwbh: pageData?.accountUnitCode,
                          }
                        }

                      },
                      searchBtnsStyle: "inline",
                      formConfig: [
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "lsbmzdDwmc",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                        {
                          isInForm: false,
                          table: {
                            dataIndex: "lsbmzdBmbh",
                            title: "??????????????????",
                          },
                          form: {
                            type: "string"
                          }
                        },
                      ]
                    },
                  },
                  {
                    label: '??????????????????',
                    field: 'accountDepCode',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '?????????????????????',
                    field: 'pp4_pp5',
                    type: 'number',
                    disabled: true,
                    span: 8,
                  },
                  {
                    label: '????????????',
                    field: 'zjgcxmXmmc',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: 'taxCountWay',
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    required: true,
                    optionData: [
                      { label: '??????????????????', value: '1' },
                      { label: '??????????????????', value: '2' }
                    ],
                    placeholder: '?????????',
                    onChange: (val, obj) => {
                      if (val) {
                        obj.form.setFieldsValue({
                          taxRate: null,
                        })
                      }
                    },
                    span: 8
                  },
                  {
                    label: '??????%',
                    field: 'taxRate',
                    type: 'select',
                    required: true,
                    placeholder: '?????????',
                    span: 8,
                    optionConfig: {
                      label: 'value',
                      value: 'value',
                    },
                    dependencies: ['taxCountWay', 'invoiceType'],
                    dependenciesReRender: true,
                    disabled: (obj) => {
                      let objData = obj?.form?.getFieldsValue()
                      if (!objData.taxCountWay || !objData.invoiceType) return true
                      return false
                    },
                    optionData: (obj) => {
                      let objData = obj?.form?.getFieldsValue()
                      if (objData?.invoiceType === '1') {
                        return [{ value: '0' }]
                      }
                      if (objData.taxCountWay === '2') {
                        return [{ value: '0' }, { value: '3' }, { value: '5' }]
                      }
                      if (objData.taxCountWay === '1') {
                        return [
                          { value: '0' }, { value: '1' }, { value: '1.5' },
                          { value: '3' }, { value: '5' }, { value: '6' },
                          { value: '9' }, { value: '10' }, { value: '11' },
                          { value: '13' }, { value: '16' }, { value: '17' },
                        ]
                      }
                    },
                  },
                  {
                    label: '??????????????????',
                    field: 'fiCtrState',
                    type: 'select',
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: '??????', value: '1' },
                      { label: '??????', value: '2' },
                      { label: '??????', value: '3' },
                    ],
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: 'invoiceType',
                    type: 'select',
                    required: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: '?????????????????????', value: '0' },
                      { label: '?????????????????????', value: '1' }
                    ],
                    span: 8,
                    onChange: (val, obj) => {
                      if (val) {
                        obj.form.setFieldsValue({
                          taxRate: null,
                        })
                      }
                    },
                  },
                  {
                    label: '??????',
                    field: 'bz',
                    type: 'select',
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [{
                      label: '?????????',
                      value: '1',
                    }],
                    initialValue: '1',
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: 'ctrNature',
                    type: 'select',
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: '????????????', value: '1' },
                      { label: '??????', value: '2' },
                      { label: '??????', value: '3' },
                      { label: '??????', value: '4' },
                      { label: '??????', value: '5' },
                      { label: '??????', value: '6' },
                      { label: '??????', value: '4' },
                      { label: '??????', value: '5' },
                      { label: '????????????', value: '6' },
                    ],
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: 'revenueDir',
                    type: 'select',
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: '??????', value: '01' },
                      { label: '??????', value: '02' },
                    ],
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: 'keyCtr',
                    type: 'select',
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: '???', value: '0' },
                      { label: '???', value: '1' }
                    ],
                    span: 4
                  },
                  {
                    label: '????????????',
                    field: 'staCtr',
                    type: 'select',
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: '???', value: '0' },
                      { label: '???', value: '1' }
                    ],
                    span: 4
                  },
                  {
                    label: '???????????????(%)',
                    field: 'advanceRate',
                    type: 'number',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '???????????????(%)',
                    field: 'progressRate',
                    type: 'number',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '???????????????(%)',
                    field: 'completionRate',
                    type: 'number',
                    disabled: true,
                    span: 8
                  },

                  {
                    type: "component",
                    field: "component4",
                    Component: obj => {
                      return (
                        <div style={{ background: '#f0f2f5', padding: '8px 12px' }}>
                          ??????
                        </div>
                      );
                    }
                  },
                  {
                    label: '?????????',
                    field: 'writer',
                    type: 'string',
                    placeholder: '?????????',
                    required: true,
                    span: 6,
                    initialValue: loginUser
                  },
                  {
                    label: '????????????',
                    field: 'writeDate',
                    type: 'date',
                    required: true,
                    span: 6
                  },
                  {
                    label: '?????????',
                    field: 'doubleCheckPerson',
                    type: 'string',
                    placeholder: '?????????',
                    required: true,
                    span: 6
                  },
                  {
                    label: '????????????',
                    field: 'doubleCheckDate',
                    type: 'date',
                    required: true,
                    span: 6
                  }
                ]
              }
            },
          ]}
        />
        {
          visible ? <Drawer
            title={'??????????????????'}
            placement="right"
            onClose={this.onClose}
            visible={visible}
            width={window.screen.width * 0.75}
            className={'drawerClass'}
            footer={
              <div
                style={{
                  textAlign: 'right',
                }}
              >
                <Button onClick={this.onClose}> ?????? </Button>
              </div>
            }
          >
            <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
              <TabPane tab="????????????" key="1">
                <QnnForm
                  {...this.props}
                  match={this.props.match}
                  fetch={this.props.myFetch}
                  upload={this.props.myUpload}
                  headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                  data={flowData?.apiData ? JSON.parse(flowData.apiData) : null}
                  formConfig={[
                    {
                      label: '????????????',
                      field: "contractNo",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '????????????',
                      field: "contractName",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '????????????',
                      field: "firstID",
                      type: 'select',
                      optionConfig: {
                        label: 'orgName',
                        value: 'orgID',
                      },
                      fetchConfig: {
                        apiName: 'getZxCtContractListByStatus'
                      },
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '????????????',
                      field: "secondID",
                      type: 'selectByQnnTable',
                      optionConfig: {
                        label: 'customerName',
                        value: 'zxCrCustomerNewId',
                      },
                      qnnTableConfig: {
                        antd: { rowKey: "zxCrCustomerNewId" },
                        fetchConfig: { apiName: "getZxCrCustomerNewList" },
                        searchBtnsStyle: "inline",
                      },
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      field: 'secondOrgType',
                      type: 'string',
                      label: '??????????????????',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '???????????????',
                      field: "agent",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '??????????????????(??????)',
                      field: "contractCost",
                      type: 'number',
                      disabled: true,
                      span: 12,
                      precision: 6,
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
                    },
                    {
                      label: '?????????????????????(??????)',
                      field: "contractCostNoTax",
                      type: 'number',
                      disabled: true,
                      span: 12,
                      precision: 6,
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
                    },
                    {
                      label: '????????????(??????)',
                      field: "contractCostTax",
                      type: 'number',
                      disabled: true,
                      span: 12,
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
                      precision: 6,
                    },
                    {
                      label: '????????????',
                      field: "isDeduct",
                      type: 'radio',
                      optionData: [
                        { label: '???', value: '1' },
                        { label: '???', value: '0' }
                      ],
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '????????????',
                      field: "csTimeLimit",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '????????????',
                      field: "contractCategory",
                      type: 'select',
                      optionConfig: {
                        label: 'label',
                        value: 'value'
                      },
                      optionData: [
                        { label: '????????????', value: '0' },
                        { label: '????????????', value: '1' },
                      ],
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '????????????',
                      field: "pp3",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '?????????',
                      field: "createUserName",
                      type: 'string',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '????????????',
                      field: 'content',
                      type: 'textarea',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: '??????',
                      field: 'remark',
                      type: 'textarea',
                      disabled: true,
                      span: 12,
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
                    },
                    {
                      label: "????????????",
                      type: 'qnnTable',
                      field: 'qnnTableList',
                      qnnTableConfig: {
                        fetchConfig: (obj) => {
                          return {
                            apiName: 'getZxCtEqContrItemList',
                            otherParams: {
                              zxCtEqContrApplyId: obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.zxCtEqContrApplyId
                            }
                          }
                        },
                        antd: {
                          size: "small",
                          rowKey: 'zxCtEqContrItemId'
                        },
                        paginationConfig: {
                          position: 'bottom'
                        },
                        isShowRowSelect: false,
                        formConfig: [
                          {
                            table: {
                              title: '??????',
                              dataIndex: 'catCode',
                              key: 'catCode',
                              width: 150,
                              fixed: 'left',
                              align: 'center',
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '0') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '????????????',
                              dataIndex: 'catName',
                              key: 'catName',
                              width: 150,
                              fixed: 'left',
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '1') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '????????????',
                              dataIndex: 'catParentName',
                              key: 'catParentName',
                              width: 150,
                              fixed: 'left',
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '0') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '??????',
                              dataIndex: 'spec',
                              key: 'spec',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '1') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '??????',
                              dataIndex: 'catName',
                              key: 'catName',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '0') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '????????????',
                              dataIndex: 'rentUnit',
                              key: 'rentUnit',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '1') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '????????????',
                              dataIndex: 'spec',
                              key: 'spec',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '1') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '??????',
                              dataIndex: 'unit',
                              key: 'unit',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '0') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '??????????????????',
                              dataIndex: 'actualStartDate',
                              key: 'actualStartDate',
                              format: 'YYYY-MM-DD',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'date',
                              spanForm: 12
                            }
                          },
                          {
                            isInTable: (obj) => {
                              let rowData = obj.qnnFormProps.form.getFieldsValue();
                              if (rowData.contractCategory === '0') {
                                return true;
                              } else {
                                return false
                              }
                            },
                            table: {
                              title: '??????????????????',
                              dataIndex: 'actualEndDate',
                              format: 'YYYY-MM-DD',
                              key: 'actualEndDate',
                              width: 150,
                              align: 'center',
                            },
                            form: {
                              type: 'date',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '??????',
                              dataIndex: 'qty',
                              key: 'qty',
                              width: 120,
                            },
                            form: {
                              type: 'number',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '??????????????????(???/???)',
                              dataIndex: 'price',
                              key: 'price',
                              width: 150,
                            },
                            form: {
                              type: 'number',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '??????????????????',
                              dataIndex: 'contractSum',
                              key: 'contractSum',
                              width: 150,
                            },
                            form: {
                              type: 'number',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '?????????????????????',
                              dataIndex: 'priceNoTax',
                              key: 'priceNoTax',
                              width: 120,
                              align: 'center',
                            },
                            form: {
                              type: 'number',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '?????????????????????',
                              dataIndex: 'contractSumNoTax',
                              key: 'contractSumNoTax',
                              width: 120,
                              align: 'center',
                            },
                            form: {
                              type: 'number',
                              spanForm: 12
                            }
                          },
                          {
                            table: {
                              title: '??????%',
                              dataIndex: 'taxRate',
                              key: 'taxRate',
                              width: 120,
                              align: 'center',
                            },
                            form: {
                              type: 'string',
                              spanForm: 12
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
                                let h = this.table.getSelectedRows()[0]?.isDeduct
                                if (h === '1') {
                                  return '???'
                                } else {
                                  return '???'
                                }
                              }
                            },
                            form: {
                              spanForm: 12,
                              type: 'radio',
                              optionData: [
                                { label: '???', value: '1' },
                                { label: '???', value: '0' }
                              ],
                              initialValue: '0',
                            }
                          },
                          {
                            table: {
                              title: '??????',
                              dataIndex: 'remark',
                              key: 'remark',
                              width: 150,
                            },
                            form: {
                              type: 'string',
                              formItemLayout: {
                                labelCol: {
                                  xs: { span: 4 },
                                  sm: { span: 4 }
                                },
                                wrapperCol: {
                                  xs: { span: 18 },
                                  sm: { span: 18 }
                                }
                              }
                            }
                          },
                        ]
                      }
                    },
                    {
                      type: "textarea",
                      label: "????????????",
                      field: "opinionField1",
                      formatter: (val) => {
                        if (val) {
                          if (val.indexOf('<br/>') !== -1) {
                            return val.replace("<br/>", "\r\n");
                          }
                        }
                      },
                      disabled: true,
                      formItemLayout: {
                        labelCol: {
                          xs: { span: 4 },
                          sm: { span: 4 }
                        },
                        wrapperCol: {
                          xs: { span: 20 },
                          sm: { span: 20 }
                        }
                      }
                    }
                  ]}
                />
              </TabPane>
              <TabPane tab="????????????" key="2">
                <Operation {...this.props} flowData={flowData} />
              </TabPane>
            </Tabs>
          </Drawer> : null
        }
      </div>
    );
  }
}

export default index;