import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Button, Drawer, Tabs } from 'antd';
import QnnForm from "../../modules/qnn-table/qnn-form";
import Operation from './operation';
import DeetailForm from './detail';
import SelectFilesDownLoad from '../SelectFilesDownLoad';
const { TabPane } = Tabs;
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxCtOtherManageId',
    size: 'small'
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
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
      visible: false,
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
            apiName: 'getZxCtOtherManageList',
            otherParams: { orgId: departmentId }
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
              if (index === "1" || index === '2' || index === '4') return true;
              return false
            },
            //??????1????????????????????????
            hideForNo_1SaveBtn: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
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
              let zxCtOtherManageAmtRateList = await this.tableOne.getTableData()
              let emptyData = []
              await zxCtOtherManageAmtRateList.map((item) => {
                if (!item.statisticsName) emptyData.push('?????????')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`?????????${emptyData.join()}`)
                return {}
              } else {
                await zxCtOtherManageAmtRateList.map((item) => {
                  if (item.addFlag === '1') item.zxCtOtherManageAmtRateId = null
                  return item
                })
                params.zxCtOtherManageId = obj.rowData.zxCtOtherManageId
                params.zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateList
                return {
                  apiName: 'batchSaveUpdateZxCtOtherManageAmtRate',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      obj.qnnTableInstance.refresh()
                      this.tableOne.refresh()
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
              if (index !== "5") {
                return true
              }
              return false
            },
            //??????????????????????????????
            cbForNo_4EditSave: async (obj) => {
              let params = {}
              params.alterContractSum = obj.rowData.alterContractSum
              params.contractNo = obj.rowData.contractNo
              params.contractName = obj.rowData.contractName
              params.secondName = obj.rowData.secondName
              params.zxCtOtherManageId = obj.rowData.zxCtOtherManageId
              return {
                apiName: 'updateZxCtOtherManageById',
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
              if (disabledLength > 0 || data.length === 0) return true
            },
            //?????????????????????
            cbForDiyBtn: (obj) => {
              confirm({
                content: obj.btnCallbackFn.getSelectedRows()?.[0].contractStatus === '3' ? '??????????????????????' : '???????????????',
                onOk: () => {
                  let params = obj.btnCallbackFn.getSelectedRows()[0]
                  params.contractStatus = obj.btnCallbackFn.getSelectedRows()[0].contractStatus === '3' ? '1' : '3'
                  this.props.myFetch('updateZxCtFsContract', params).then(
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
            cbForDiyView: (obj) => {
              this.props.myFetch('openFlowByReport', { apiName: 'getZxCtOtherDetail', apiType: 'POST', flowId: 'zxCtOtherReviewApply', workId: obj.btnCallbackFn.getSelectedRows()[0].workId }).then(
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
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.clearSelectedRows();
              this.table.refresh()
            }
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "OtherContractManagement"
              }
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
                width: 170,
                onClick: 'detail',
              }
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
                dataIndex: 'orgName',
                key: 'orgName',
                width: 150,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractType',
                key: 'contractType',
                width: 100
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
                title: '??????????????????',
                dataIndex: 'auditStatus',
                key: 'auditStatus',
                width: 150,
                render: (h) => {
                  if (h === 'directInput') {
                    return '????????????'
                  } else if (h === 'auditPassed') {
                    return '????????????'
                  }
                }
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
                  field: 'signatureDate'
                },
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
                title: '???????????????????????????(??????)',
                dataIndex: 'alterContractSum',
                key: 'alterContractSum',
                width: 200,
              }
            },
            {
              table: {
                title: '??????????????????',
                dataIndex: 'totalSettleAmount',
                key: 'totalSettleAmount',
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
                dataIndex: 'settleType',
                key: 'settleType',
                width: 100
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'consultative',
                key: 'consultative',
                width: 100
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                width: 100,
                render: (h) => {
                  if (h === 'FW') {
                    return '????????????'
                  } else if (h === 'JG') {
                    return '????????????'
                  } else if (h === 'JS') {
                    return '?????????????????????'
                  } else if (h === 'QT') {
                    return '????????????'
                  } else if (h === 'XZ') {
                    return '????????????????????????'
                  } else if (h === 'ZC') {
                    return '????????????'
                  }
                }
              }
            },
            {
              table: {
                title: '??????',
                dataIndex: 'remark',
                key: 'remark',
                width: 100
              }
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
                    field: 'zxCtOtherId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtOtherManageId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '????????????',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "signatureDate",
                    span: 12,
                    type: 'date',
                    required: true,
                  },
                  {
                    label: '????????????',
                    field: "contractName",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '????????????',
                    field: "orgName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                  },
                  {
                    label: '????????????',
                    field: "contractType",
                    initialValue: '?????????',
                    type: 'string',
                    span: 12,
                    disabled: true,
                  },
                  {
                    label: '????????????',
                    field: "firstName",
                    span: 12,
                    type: 'string',
                    placeholder: '?????????',
                  },
                  {
                    label: '????????????',
                    field: "secondId",
                    type: 'selectByPaging',
                    optionConfig: {
                      label: 'customerName',
                      value: 'zxCrCustomerExtAttrId',
                      linkageFields: {
                        secondName: 'customerName',
                      }
                    },
                    fetchConfig: (obj) => {
                      return {
                        apiName: 'getZxCrCustomerExtAttrList',
                        otherParams: { type: 'ots' },
                        searchKey: 'customerName'
                      }
                    },
                    allowClear: false,
                    showSearch: true,
                    required: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "projectManager",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "secondPrincipal",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '?????????????????????',
                    field: "firstPrincipalIdCard",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '?????????????????????',
                    field: "secondPrincipalIdCard",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: "firstUnitTel",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????(??????)',
                    field: "secondUnitTel",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????(??????)',
                    field: "secondstring",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????(??????)',
                    field: "secondFax",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "agent",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: "auditStatus",
                    type: 'select',
                    span: 12,
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: '????????????',
                        value: 'directInput'
                      },
                      {
                        label: '????????????',
                        value: 'auditPassed'
                      }
                    ],
                  },
                  {
                    label: '????????????',
                    field: "contractStatus",
                    type: 'select',
                    span: 12,
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
                  },
                  {
                    label: '??????????????????(??????)',
                    field: "contractCost",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '????????????(??????)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '???????????????????????????(??????)',
                    field: "alterContractSum",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '??????????????????????????????(??????)',
                    field: "alterContractSumNoTax",
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
                    label: '?????????????????????(??????)',
                    field: "alterContractSumTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "isDeduct",
                    disabled: true,
                    type: 'radio',
                    optionData: [
                      { label: '???', value: '1' },
                      { label: '???', value: '0' }
                    ],
                    initialValue: '0',
                    span: 12,
                  },
                  {
                    label: '????????????',
                    field: "settleType",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '??????????????????',
                    field: "planStartDate",
                    span: 12,
                    type: 'date',
                  },

                  {
                    label: '??????????????????',
                    field: "planEndDate",
                    span: 12,
                    type: 'date',
                  },
                  {
                    label: '??????????????????',
                    field: "actualStartDate",
                    span: 12,
                    type: 'date',
                  },
                  {
                    label: '??????????????????',
                    field: "actualEndDate",
                    span: 12,
                    type: 'date',
                  },
                  {
                    label: '????????????',
                    field: "consultative",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "contractCategory",
                    type: 'select',
                    span: 12,
                    disabled: true,
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: '????????????', value: 'FW' },
                      { label: '????????????', value: 'JG' },
                      { label: '?????????????????????', value: 'JS' },
                      { label: '????????????', value: 'QT' },
                      { label: '????????????????????????', value: 'XZ' },
                      { label: '????????????', value: 'ZC' },
                    ],
                  },
                  {
                    label: '????????????',
                    field: 'content',
                    type: 'textarea',
                    required: true,
                    placeholder: '?????????',
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
                  },
                  {
                    label: '??????',
                    field: 'remark',
                    type: 'textarea',
                    placeholder: '?????????',
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
                  },
                  {
                    label: '????????????',
                    field: 'zxErpFileList',
                    type: 'files',
                    required: true,
                    fetchConfig: {
                      apiName: 'upload'
                    },
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 24 }
                      }
                    }
                  }
                ]
              },

            },
            {
              field: "table1",
              name: "qnnTable",
              title: "??????????????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherWorksList',
                    otherParams: {
                      zxCtOtherManageId: this.table.qnnForm.form.getFieldValue('zxCtOtherManageId')
                    }
                  }
                },
                antd: {
                  size: "small",
                  rowKey: "conRevDetailId",
                },
                paginationConfig: { position: 'bottom' },
                isShowRowSelect: false,
                formConfig: [
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 180,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '????????????',
                      width: 170,
                      dataIndex: 'workName',
                      key: 'workName',
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '????????????',
                      dataIndex: 'unit',
                      key: 'unit',
                      width: 100,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '????????????',
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
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '??????%',
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
                      title: '??????????????????',
                      dataIndex: 'contractSum',
                      key: 'contractSum',
                      width: 120,
                      align: 'center',
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
                  },
                  {
                    table: {
                      title: '???????????????',
                      dataIndex: 'changeQty',
                      key: 'changeQty',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'changePrice',
                      key: 'changePrice',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'changePriceNoTax',
                      key: 'changePriceNoTax',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'changeContractSum',
                      key: 'changeContractSum',
                      width: 120,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'changeContractSumNoTax',
                      key: 'changeContractSumNoTax',
                      width: 150,
                      align: 'center',
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
              field: "table3",
              name: "qnnTable",
              title: "????????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherSupplyAgreementList',
                    otherParams: {
                      zxCtOtherManageId: obj.tableFns.form.getFieldValue('zxCtOtherManageId'),
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
                  rowKey: 'zxCtOtherSupplyAgreementId',
                  size: 'small'
                },
                paginationConfig: {
                  position: "bottom"
                },
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'edit',//??????add del
                    icon: 'edit',//icon
                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                    label: '??????',
                    formBtns: [
                      {
                        name: 'cancel', //??????????????????
                        type: 'dashed',//??????  ?????? primary
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
                          obj._formData.zxCtOtherSupplyAgreementId = obj.selectedRows[0].zxCtOtherSupplyAgreementId
                          obj.btnCallbackFn.clearSelectedRows();
                          obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                          this.props.myFetch('updateZxCtOtherSupplyAgreement', obj._formData).then(
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
                      onClick: 'detail',
                    }
                  },
                  {
                    table: {
                      title: '??????????????????',
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
                      width: 100,
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
                      dataIndex: 'applyAmount',
                      key: 'applyAmount',
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
                      dataIndex: 'beginPer',
                      key: 'beginPer',
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
                    content: {
                      formConfig: [
                        {
                          field: 'zxCtOtherSupplyAgreementId',
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
                          field: "proposer",
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
                          label: '??????????????????(??????)',
                          field: "contractCost",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '?????????????????????(??????)',
                          field: "contractCostNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '????????????(??????)',
                          field: "contractCostTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '????????????????????????(??????)',
                          field: "applyAmount",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '???????????????????????????(??????)',
                          field: "applyAmountNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '??????????????????(??????)',
                          field: "applyAmountTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '?????????????????????(??????)',
                          field: "alterContractSum",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '????????????????????????(??????)',
                          field: "alterContractSumNoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '???????????????(??????)',
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
                          type: 'radio',
                          initialValue: '0',
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
                          label: '?????????',
                          field: "beginPer",
                          type: 'string',
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '????????????',
                          field: 'replyUnit',
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
                        console.log(obj, this.TableTwo);
                        const zxCtOtherSupplyAgreementId = this.TableTwo.form.getFieldValue('zxCtOtherSupplyAgreementId')
                        return {
                          apiName: 'getZxCtOtherSupplyAgreementWorksList',
                          otherParams: { zxCtOtherSupplyAgreementId }
                        }
                      },
                      antd: {
                        size: "small",
                        rowKey: "zxCtOtherSupplyAgreementWorksId",
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
                            title: '????????????',
                            dataIndex: 'workNo',
                            key: 'workNo',
                            width: 100,
                            align: 'center',
                            fixed: 'left',
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            width: 150,
                            dataIndex: 'workName',
                            key: 'workName',
                            align: 'center',
                            fixed: 'left',
                          }
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 100,
                            align: 'center',
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
                            width: 120,
                            align: 'center',
                          }
                        },

                        {
                          table: {
                            title: '???????????????',
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
                            title: '???????????????',
                            dataIndex: 'changeQty',
                            key: 'changeQty',
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'changePrice',
                            key: 'changePrice',
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '?????????????????????',
                            dataIndex: 'changeContractSum',
                            key: 'changeContractSum',
                            width: 120,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '????????????????????????',
                            dataIndex: 'changeContractSumNoTax',
                            key: 'changeContractSumNoTax',
                            width: 150,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '???????????????',
                            dataIndex: 'changeContractSumTax',
                            key: 'changeContractSumTax',
                            width: 150,
                            align: 'center',
                          }
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'taxRate',
                            key: 'taxRate',
                            width: 100,
                            align: 'center',
                            type: 'select'
                          },
                          form: {
                            type: "select",
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
                            title: '????????????',
                            dataIndex: 'isDeduct',
                            key: 'isDeduct',
                            width: 100,
                            align: 'center',
                            render: (h) => {
                              if (h === '1') {
                                return '???'
                              } else {
                                return '???'
                              }
                            }
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
                        }
                      ]
                    }
                  }
                ]
              }
            },
            {
              field: "table4",
              name: "qnnTable",
              title: "?????????????????????",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtOtherManageId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherManageAmtRateList',
                    otherParams: { zxCtOtherManageId: obj.tableFns.form.getFieldValue('zxCtOtherManageId') }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtOtherManageAmtRateId",
                },
                // rowDisabledCondition: (rowData) => {
                //   return rowData.allowDelete === 'N'
                // },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'addRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '?????????',
                    addRowDefaultData: { addFlag: '1', useCount: 0 }
                  },
                  {
                    name: 'delRow',
                    icon: 'delete',
                    type: 'danger',
                    label: '??????',
                    disabled: (obj) => {
                      let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                      let disabledLength = 0
                      for (var i = 0; i < SelectedRowsData.length; i++) {
                        if (SelectedRowsData[i].useCount !== 0 || SelectedRowsData[i].allowDelete === 'N') {
                          disabledLength++
                        }
                      }
                      if (disabledLength > 0 || SelectedRowsData.length === 0) return true
                    }
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'zxCtOtherManageAmtRateId',
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
                      tdEdit: (obj) => {
                        if (obj.allowDelete === 'N') return false
                        return true
                      },
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
                      tdEdit: true,
                      width: 100,
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
                      placeholder: '?????????',
                    }
                  },
                ]
              }
            },
            {
              field: "table6",
              name: "qnnTable",
              title: "?????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxSaOtherEquipSettleList',
                    otherParams: {
                      orgID: departmentId,
                      zxCtOtherManageId: obj.tableFns.form.getFieldValue('zxCtOtherManageId'),
                      apih5FlowStatus: '2',
                    }
                  }

                },
                antd: {
                  size: "small",
                  rowKey: "zxSaOtherEquipSettleId",
                },
                formConfig: [
                  {
                    table: {
                      title: '????????????',
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
                      width: 150,
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
                      width: 150,
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
                      dataIndex: 'reportPerson',
                      key: 'reportPerson',
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
                          field: 'zxSaOtherEquipSettleId',
                          type: 'string',
                          hide: true,
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
                          field: "signedNo",
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
                          field: "initSerialNumber",
                          span: 12,
                          type: 'string',
                        },
                        {
                          label: '?????????',
                          field: "reportPerson",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '???????????????',
                          field: "reportPersonstring",
                          type: 'string',
                          span: 12,
                        },
                        {
                          label: '?????????',
                          field: "reCountPerson",
                          type: 'string',
                          span: 12,
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
                          label: '????????????',
                          field: 'appraisal',
                          type: 'textarea',
                          span: 12,
                        },
                        {
                          label: '?????????????????????',
                          field: 'content',
                          type: 'textarea',
                          span: 12,
                        },
                        {
                          label: '????????????',
                          field: 'remark',
                          type: 'textarea',
                          span: 12,
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
                          apiName: 'getZxSaOtherEquipResSettleList',
                          otherParams: (obj) => {
                            return {
                              zxSaOtherEquipSettleId: obj.form.getFieldValue('zxSaOtherEquipSettleId')
                            }
                          }
                        }
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
                          field: "zxSaFsInventorySettlementId",
                          type: 'string',
                          hide: true,
                        },
                        {
                          label: '???????????????????????????(??????)',
                          field: "changeAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '??????????????????????????????(???)',
                          field: "thisAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '?????????????????????????????????(???)',
                          field: "thisAmtNoTax",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '????????????????????????(???)',
                          field: "thisAmtTax",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '??????????????????????????????(???)',
                          field: "totalAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          type: 'qnnTable',
                          field: 'zxSaOtherEquipResSettleItemList',
                          incToForm: true,
                          qnnTableConfig: {
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
                                table: {
                                  title: '????????????',
                                  dataIndex: 'equipCode',
                                  key: 'equipCode',
                                  align: 'center',
                                  fixed: 'left',
                                }
                              },
                              {
                                table: {
                                  title: '????????????',
                                  dataIndex: 'equipName',
                                  key: 'equipName',
                                  align: 'center',
                                  fixed: 'left',
                                }
                              },
                              {
                                table: {
                                  title: '????????????',
                                  dataIndex: 'unit',
                                  key: 'unit',
                                  align: 'center',
                                  tdEdit: false
                                },
                              },
                              {
                                table: {
                                  title: '????????????(???)',
                                  dataIndex: 'contractPrice',
                                  key: 'contractPrice',
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '????????????',
                                  dataIndex: 'contractQty',
                                  key: 'contractQty',
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????',
                                  dataIndex: 'contractAmt',
                                  key: 'contractAmt',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '???????????????',
                                  dataIndex: 'changedQty',
                                  key: 'changedQty',
                                  align: 'center',
                                  width: 150
                                }
                              },
                              {
                                table: {
                                  title: '?????????????????????',
                                  dataIndex: 'changedAmt',
                                  key: 'changedAmt',
                                  width: 150,
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
                                },
                              },
                              {
                                table: {
                                  title: '????????????????????????',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 140,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '???????????????????????????',
                                  dataIndex: 'thisAmtNoTax',
                                  key: 'thisAmtNoTax',
                                  width: 150,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '??????????????????',
                                  dataIndex: 'thisAmtTax',
                                  key: 'thisAmtTax',
                                  width: 120,
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
                                  initialValue: 0,
                                }
                              },
                              {
                                table: {
                                  title: '????????????????????????????????????',
                                  dataIndex: 'upAmt',
                                  key: 'upAmt',
                                  width: 200,
                                  align: 'center',
                                },
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
                                  width: 200,
                                  align: 'center',
                                }
                              },
                              {
                                table: {
                                  title: '?????????',
                                  dataIndex: 'planning',
                                  key: 'planning',
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
                                },
                              },
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
                          apiName: 'getZxSaOtherEquipPaySettleList',
                          otherParams: (obj) => {
                            return {
                              zxSaOtherEquipSettleId: obj.form.getFieldValue('zxSaOtherEquipSettleId')
                            }
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
                          label: '?????????????????????????????????(???)',
                          field: "thisAmt",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '????????????????????????????????????(???)',
                          field: "thisAmtNoTax",
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
                          field: "thisAmtTax",
                          type: 'number',
                          span: 12
                        },
                        {
                          label: '???????????????????????????(???)',
                          field: "totalAmt",
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
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '2' && obj.qnnFormProps.form.getFieldValue('zxSaOtherEquipPaySettleId')) {
                                return {
                                  apiName: 'getZxSaOtherEquipPaySettleItemList',
                                  otherParams: { zxSaOtherEquipPaySettleId: obj.qnnFormProps.form.getFieldValue('zxSaOtherEquipPaySettleId') }
                                }
                              } else {
                                return {}
                              }
                            },
                            antd: {
                              rowKey: 'zxSaOtherEquipPaySettleItemId',
                              size: 'small'
                            },
                            paginationConfig: {
                              position: 'bottom'
                            },
                            isShowRowSelect: false,
                            formConfig: [
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
                                },
                              },
                              {
                                table: {
                                  title: '??????(???)',
                                  dataIndex: 'price',
                                  key: 'price',
                                  width: 120
                                },
                              },
                              {
                                table: {
                                  title: '??????(%)',
                                  dataIndex: 'taxRate',
                                  key: 'taxRate',
                                  width: 80
                                },
                                form: {
                                  field: 'taxRate',
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
                                  title: '????????????????????????(???)',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 170,
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
                                  title: '??????????????????',
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
                                },
                              },
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
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '3') {
                                return {
                                  apiName: 'getZxSaOtherEquipSettleItemList',
                                  otherParams: () => {
                                    return {
                                      zxSaOtherEquipSettleId: obj.qnnFormProps.form.getFieldValue('zxSaOtherEquipSettleId')
                                    }
                                  }
                                }
                              } else {
                                return {}
                              }
                            },
                            paginationConfig: false,
                            isShowRowSelect: false,
                            pageSize: 999999,
                            antd: {
                              size: "small",
                              rowKey: "zxSaOtherEquipSettleItemId",
                            },
                            formConfig: [
                              {
                                table: {
                                  title: '?????????',
                                  dataIndex: 'statisticsName',
                                  key: 'statisticsName',
                                  width: 100,
                                  type: 'string',
                                  align: 'center',
                                },
                              },
                              {
                                table: {
                                  title: '???????????????',
                                  dataIndex: 'thisAmt',
                                  key: 'thisAmt',
                                  width: 150,
                                  type: 'string',
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
                                  type: 'string',
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
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtOtherManageDetail',
                    otherParams: { zxCtOtherManageId: this.table?.form?.getFieldValue('zxCtOtherManageId') },
                    success: ({ data, success }) => {
                      if (success) {
                        obj.form.setFieldsValue({
                          pp4_pp5: (data.totalSettleAmount ? data.totalSettleAmount : 0) - (data?.totalPayAmount ? Number(data.totalPayAmount) : 0)
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
                    field: 'secondIdCodeBh',
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
                          totalPayAmount: null,
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
                          ZJGCXMXMMC: null,
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
                    dependencies: ['secondIdCodeBh'],
                    dependenciesReRender: true,
                    parent: 'secondIdCodeBh',
                    allowClear: false,
                    optionConfig: {
                      label: 'zjxmhtbHtmc',
                      value: 'zjxmhtbHtmc',
                      linkageFields: {
                        zjxmhtbBh: 'zjxmhtbBh',//??????????????????
                        totalPayAmount: 'klzfje',//??????????????????
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
                        ZJGCXMXMMC: 'zjgcxmXmmc',//????????????
                        taxCountWay: 'jsff'//????????????
                      }
                    },
                    onChange: (val, obj) => {
                      let DrawerData = obj.form.getFieldsValue()
                      obj.form.setFieldsValue({
                        pp4_pp5: DrawerData.totalSettleAmount - DrawerData.totalPayAmount >= 0 ? DrawerData.totalSettleAmount - DrawerData.totalPayAmount : 0
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
                          apiName: "getZxSaFiZjxmhtbInOtherHookup",
                          otherParams: {
                            zjxmhtbKhnm: pageData?.secondIdCodeBh,
                            zjxmhtbBh: pageData?.contractNo,
                            contractID: pageData?.zxCtOtherManageId,
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
                    field: 'totalSettleAmount',
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
                    field: 'totalPayAmount',
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
                    field: 'ZJGCXMXMMC',
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
                      field: "contractType",
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
                      field: "firstName",
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
                      field: "secondName",
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
                      field: 'contractCategory',
                      type: 'select',
                      disabled: true,
                      label: '????????????',
                      span: 12,
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                      optionData: [
                        { label: '????????????', value: 'FW' },
                        { label: '????????????', value: 'JG' },
                        { label: '?????????????????????', value: 'JS' },
                        { label: '????????????', value: 'QT' },
                        { label: '????????????????????????', value: 'XZ' },
                        { label: '????????????', value: 'ZC' },
                      ],
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
                            apiName: 'getZxCtOtherWorksList',
                            otherParams: {
                              zxCtOtherId: obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.zxCtOtherId
                            }
                          }
                        },
                        antd: {
                          size: "small",
                          rowKey: 'zxCtOtherWorksId'
                        },
                        paginationConfig: {
                          position: 'bottom'
                        },
                        isShowRowSelect: false,
                        formConfig: [
                          {
                            table: {
                              title: '????????????',
                              dataIndex: 'workNo',
                              key: 'workNo',
                              width: 150,
                              fixed: 'left',
                              align: 'center',
                            },
                          },
                          {
                            table: {
                              title: '????????????',
                              dataIndex: 'workName',
                              key: 'workName',
                              width: 150,
                              fixed: 'left',
                              align: 'center',
                            }
                          },
                          {
                            table: {
                              title: '????????????',
                              dataIndex: 'unit',
                              key: 'unit',
                              width: 150,
                              fixed: 'left',
                              align: 'center',
                            },
                          },
                          {
                            table: {
                              title: '????????????',
                              dataIndex: 'qty',
                              key: 'qty',
                              width: 120,
                              align: 'center',
                            }
                          },
                          {
                            table: {
                              title: '??????????????????(???)',
                              dataIndex: 'price',
                              key: 'price',
                              width: 150,
                              align: 'center',
                            }
                          },
                          {
                            table: {
                              title: '?????????????????????(???)',
                              dataIndex: 'priceNoTax',
                              key: 'priceNoTax',
                              width: 150,
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
                            }
                          },
                          {
                            table: {
                              title: '??????????????????',
                              dataIndex: 'contractSum',
                              key: 'contractSum',
                              width: 120,
                              align: 'center',
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
                          },
                          {
                            table: {
                              title: '???????????????',
                              dataIndex: 'changeQty',
                              key: 'changeQty',
                              width: 120,
                              align: 'center',
                            }
                          },
                          {
                            table: {
                              title: '?????????????????????',
                              dataIndex: 'changeContractSum',
                              key: 'changeContractSum',
                              width: 120,
                              align: 'center',
                            }
                          },
                          {
                            table: {
                              title: '????????????????????????',
                              dataIndex: 'changeContractSumNoTax',
                              key: 'changeContractSumNoTax',
                              width: 150,
                              align: 'center',
                            }
                          },
                          {
                            table: {
                              title: '??????',
                              dataIndex: 'remark',
                              key: 'remark',
                              width: 180,
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