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
    rowKey: 'zxCtFsContractId',
    size: 'small',
  },
  drawerConfig: {
    width: '75%'
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
      activeKey: '1'
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
    const { flowData, visible, activeKey, departmentId } = this.state;
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
            apiName: 'getZxCtFsContractList',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh()
              obj.btnCallbackFn.clearSelectedRows();
            }
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
              if (index === "0" || index === '3') {
                return false;
              } else {
                return true;
              }
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
              let bondList = await this.tableOne.getTableData()
              let emptyData = []
              bondList.map((item) => {
                if (!item.statisticsName) emptyData.push('?????????')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`?????????${emptyData.join()}`)
                return {}
              } else {
                bondList.map((item) => {
                  item.zxCtFsContractId = obj.rowData.zxCtFsContractId
                  if (item.addFlag === '1') item.zxCtFsContractBondId = null
                  return item
                })
                params.zxCtFsContractId = obj.rowData.zxCtFsContractId
                params.bondList = bondList
                return {
                  apiName: 'batchUpdateContractBond',
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
              this.props.myFetch('openFlowByReport', { apiName: 'getZxCtFsContractReviewDetail', apiType: 'POST', flowId: 'ZxFsReviewWorkFlow', workId: obj.btnCallbackFn.getSelectedRows()[0].workId }).then(
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
                tableField: "CollateralContractManagement"
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
                dataIndex: 'contractType',
                key: 'contractType',
                width: 100,
                render: (h) => {
                  if (h === 'P9' || h === '?????????') {
                    return '?????????'
                  }
                }
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
                title: '??????????????????',
                dataIndex: 'auditType',
                key: 'auditType',
                width: 120,
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
                dataIndex: 'code7',
                key: 'code7',
                width: 100,
                render: (h) => {
                  if (h === '0') {
                    return '???????????????'
                  } else if (h === '1') {
                    return '???????????????'
                  }
                }
              }
            },
            {
              table: {
                title: '??????',
                dataIndex: 'remarks',
                key: 'remarks',
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
                    field: 'orgID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtFsContractId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'contractReviewId',
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
                    field: "contractType",
                    initialValue: '?????????',
                    type: 'select',
                    optionData: [
                      { label: '?????????', value: '?????????' },
                      { label: '?????????', value: 'P9' },
                    ],
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
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
                    field: "secondID",
                    type: 'selectByPaging',
                    optionConfig: {
                      label: 'customerName',
                      value: 'zxCrCustomerNewId',
                      linkageFields: {
                        secondName: 'customerName',
                      }
                    },
                    fetchConfig: (obj) => {
                      if (obj.btnCallbackFn.getTabsIndex() === '0') {
                        return {
                          apiName: 'getZxCrCustomerNewList',
                          searchKey: 'customerName'
                        }
                      }
                      return {}
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
                    field: "firstPrincipalIDCard",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '?????????????????????',
                    field: "secondPrincipalIDCard",
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
                    field: "pp1",
                    type: 'string',
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '??????(??????)',
                    field: "pp2",
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
                    field: "auditType",
                    type: 'string',
                    span: 12,
                    disabled: true,
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
                    span: 12
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '????????????(??????)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '???????????????????????????(??????)',
                    field: "alterContractSum",
                    type: 'number',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '??????????????????????????????(??????)',
                    field: "alterContractSumNoTax",
                    type: 'number',
                    disabled: true,
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
                    span: 12
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
                    field: "code7",
                    span: 12,
                    disabled: true,
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '???????????????', value: '0' },
                      { label: '???????????????', value: '1' },
                    ],
                    placeholder: '?????????',
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
                    field: 'remarks',
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
                fetchConfig: {
                  apiName: 'getAllZxCtFsReviewDetailList',
                  otherParams: (obj) => {
                    console.log(obj);
                    return {
                      zxCtFsContractId: this.table?.form?.getFieldValue('zxCtFsContractId'),
                      contractReviewId: this.table?.form?.getFieldValue('contractReviewId')
                    }
                  }
                },
                antd: {
                  size: "small",
                  rowKey: "conRevDetailId",
                },
                isShowRowSelect: false,
                paginationConfig: {
                  position: 'bottom'
                },
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
                      render: () => {
                        let h = this.table?.form?.getFieldValue('isDeduct')
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
                      width: 150,
                      align: 'center',
                    },
                  },
                  {
                    table: {
                      title: '???????????????',
                      dataIndex: 'changeQty',
                      key: 'changeQty',
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
                      dataIndex: 'changePrice',
                      key: 'changePrice',
                      width: 150,
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
                      width: 150,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'changeContractSumNoTax',
                      key: 'changeContractSumNoTax',
                      width: 180,
                      align: 'center',
                    }
                  },
                  {
                    table: {
                      title: '??????',
                      dataIndex: 'remarks',
                      key: 'remarks',
                      width: 150,
                      align: 'center',
                    }
                  },
                ]
              }
            },
            {
              field: "table2",
              name: "qnnTable",
              title: "????????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtFsSideAgreementList',
                    otherParams: {
                      zxCtFsContractId: obj.tableFns.form.getFieldValue('zxCtFsContractId'),
                      contractReviewId: obj.tableFns.form.getFieldValue('contractReviewId'),
                      apih5FlowStatus: '2'
                    }
                  }
                },
                drawerConfig: {
                  width: window.screen.width * 0.65
                },
                wrappedComponentRef: (me) => {
                  this.TableTwo = me
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtFsSideAgreementId",
                },
                paginationConfig: {
                  position: "bottom"
                },
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: "edit",
                    label: "??????",
                    field: "edit",
                    type: "primary",
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
                          obj._formData.zxCtFsContractId = obj.selectedRows[0].zxCtFsContractId
                          obj.btnCallbackFn.clearSelectedRows();
                          obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                          this.props.myFetch('updateZxCtFsSideAgreement', obj._formData).then(
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
                      render: (h) => {
                        if (h === 'P9' || h === '?????????????????????') {
                          return '?????????????????????'
                        }
                      }
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
                      title: '??????????????????',
                      dataIndex: 'contractCost',
                      key: 'contractCost',
                      width: 150,
                    }
                  },
                  {
                    table: {
                      title: '????????????????????????',
                      dataIndex: 'pp4',
                      key: 'pp4',
                      width: 180,
                    }
                  },
                  {
                    table: {
                      title: '?????????????????????',
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
                          field: 'zxCtFsSideAgreementId',
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
                          field: "pp3",
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
                          span: 12,
                          disabled: true,
                          type: 'select',
                          initialValue: '?????????????????????',
                          optionData: [
                            { label: '?????????????????????', value: '?????????????????????' },
                            { label: '?????????????????????', value: 'P9' },
                          ],
                          optionConfig: {
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
                          field: "pp4",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          span: 12
                        },
                        {
                          label: '???????????????????????????(??????)',
                          field: "pp4NoTax",
                          type: 'number',
                          disabled: true,
                          initialValue: 0,
                          placeholder: '?????????',
                          span: 12
                        },
                        {
                          label: '??????????????????(??????)',
                          field: "pp4Tax",
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
                          field: 'remarks',
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
                      fetchConfig: () => {
                        const zxCtFsSideAgreementId = this.TableTwo.form.getFieldValue('zxCtFsSideAgreementId')
                        return {
                          apiName: 'getZxCtFsSideAgreementInventoryList',
                          otherParams: { zxCtFsSideAgreementId }
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
                            width: 180,
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
                            dataIndex: 'remarks',
                            key: 'remarks',
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
              title: "?????????????????????",
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtFsContractBondList',
                    otherParams: { zxCtFsContractId: obj.tableFns.form.getFieldValue('zxCtFsContractId') }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "zxCtFsContractBondId",
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
                      field: 'zxCtFsContractBondId',
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
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxSaFsSettlementList',
                    otherParams: {
                      zxCtFsContractId: obj.tableFns.form.getFieldValue('zxCtFsContractId'),
                      apih5FlowStatus: '2',
                    }
                  }

                },
                antd: {
                  size: "small",
                  rowKey: "zxSaFsSettlementId",
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
                      dataIndex: 'periodTime',
                      key: 'periodTime',
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
                          field: 'zxSaFsSettlementId',
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
                          field: "periodTime",
                          type: 'month',
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
                          label: '?????????',
                          field: "countPerson",
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
                          field: 'remarks',
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
                          apiName: 'getZxSaFsInventory',
                          otherParams: (obj) => {
                            return {
                              zxSaFsSettlementId: obj.form.getFieldValue('zxSaFsSettlementId')
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
                          field: 'table2',
                          qnnTableConfig: {
                            fetchConfig: (obj) => {
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '1' && obj.qnnFormProps.form.getFieldValue('zxSaFsInventorySettlementId')) {
                                return {
                                  apiName: 'getZxSaFsInventorySettlementDetailList',
                                  otherParams: { zxSaFsInventorySettlementId: obj.qnnFormProps.form.getFieldValue('zxSaFsInventorySettlementId') },
                                }
                              }
                              return {}
                            },
                            antd: {
                              rowKey: 'zxSaFsEnumerationSettlementDetailedId',
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
                                  title: '??????',
                                  dataIndex: 'remarks',
                                  key: 'remarks',
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
                          apiName: 'getZxSaFsPaySettlement',
                          otherParams: (obj) => {
                            return {
                              zxSaFsSettlementId: obj.form.getFieldValue('zxSaFsSettlementId')
                            }
                          }
                        }
                      },
                      formConfig: [
                        {
                          field: "zxSaFsPaySettlementId",
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
                              if (obj.qnnFormProps.qnnformData.tabsIndex === '2' && obj.qnnFormProps.form.getFieldValue('zxSaFsPaySettlementId')) {
                                return {
                                  apiName: 'getZxSaFsPaySettlementDetailList',
                                  otherParams: { zxSaFsPaySettlementId: obj.qnnFormProps.form.getFieldValue('zxSaFsPaySettlementId') }
                                }
                              } else {
                                return {}
                              }
                            },
                            antd: {
                              rowKey: 'zxSaFsPaySettlementDetailId',
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
                                  dataIndex: 'remarks',
                                  key: 'remarks',
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
                                  apiName: 'getZxSaFsStatisticsDetailList',
                                  otherParams: () => {
                                    return {
                                      zxSaFsSettlementId: obj.qnnFormProps.form.getFieldValue('zxSaFsSettlementId')
                                    }
                                  }
                                }
                              } else {
                                return {}
                              }
                            },
                            antd: {
                              rowKey: 'zxSaFsStatisticsDetailId',
                              size: 'small'
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
            }
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
                  data={flowData?.apiData ? JSON.parse(flowData.apiData) : null}
                  formConfig={[
                    {
                      label: '??????????????????',
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
                      label: '??????????????????',
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
                      type: 'select',
                      optionData: [
                        { label: '?????????', value: '?????????' },
                        { label: '?????????', value: 'P9' },
                      ],
                      optionConfig: {
                        label: 'label',
                        value: 'value'
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
                      label: '???????????????',
                      field: "fromContractNo",
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
                      field: "code7",
                      type: 'select',
                      optionConfig: {
                        label: 'label',
                        value: 'value'
                      },
                      optionData: [
                        { label: '???????????????', value: '0' },
                        { label: '???????????????', value: '1' },
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
                      field: 'secondOrgType',
                      type: 'select',
                      label: '??????????????????',
                      disabled: true,
                      span: 12,
                      optionConfig: {
                        label: 'label',
                        value: 'value',
                      },
                      optionData: [
                        { label: '??????????????????', value: '0' },
                        { label: '???????????????', value: '2' },
                        { label: '???????????????', value: '3' },
                        { label: '???????????????', value: '4' },
                        { label: '???????????????', value: '5' },
                        { label: '???????????????', value: '6' },
                        { label: '???????????????', value: '7' },
                        { label: '??????????????????', value: '8' },
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
                      field: 'remarks',
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
                            apiName: 'getZxCtFsContractReviewDetailList',
                            otherParams: {
                              contractReviewId: obj?.qnnFormProps?.qnnformData?.qnnFormProps?.data?.contractReviewId
                            }
                          }
                        },
                        antd: {
                          size: "small",
                          rowKey: 'conRevDetailId'
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
                              width: 120,
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
                              dataIndex: 'remarks',
                              key: 'remarks',
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