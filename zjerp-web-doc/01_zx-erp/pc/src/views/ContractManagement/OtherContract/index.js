import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import OtherContractForm from './form';
import Operation from './operation';
import DeetailForm from './detail';
const config = {
  antd: {
    rowKey: 'zxCtOtherId',
    size: 'small'
  },
  drawerConfig: {
    width: '70%'
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
            apiName: 'getZxCtOtherList',
            otherParams: { orgId: departmentId }
          }}
          paginationConfig={{
            position: 'bottom'
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
            }
          }}
          componentsKey={{
            OtherContractForm: (obj) => {
              return <OtherContractForm
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
              if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCtOtherId')) {
                return true;
              } else {
                return false;
              }
            },
            //????????????????????????
            addSuccessCb: (obj) => {
              if (obj.response.success) {
                obj.response.data.addDisabled = '1';
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
              let zxCtOtherWorksList = await this.tableOne.getTableData()
              let emptyData = []
              await zxCtOtherWorksList.map((item) => {
                if (!item.workNo) emptyData.push('????????????')
                if (!item.workName) emptyData.push('????????????')
                if (!item.qty) emptyData.push('??????')
                if (!item.price) emptyData.push('??????')
                if (!item.taxRate) emptyData.push('??????')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`?????????${emptyData.join()}`)
                return {}
              } else {
                await zxCtOtherWorksList.map((item) => {
                  item.zxCtOtherWorksId = null
                  return item
                })
                params.zxCtOtherWorksList = zxCtOtherWorksList
                params.zxCtOtherId = this.table.form.getFieldValue('zxCtOtherId')
                return {
                  apiName: 'batchAddUpdateZxCtOtherWorks',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtOtherId = this.table.form.getFieldValue('zxCtOtherId')
                      this.props.myFetch('getZxCtOtherDetail', params)
                        .then(({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              contractCost: data.contractCost,
                              contractCostNoTax: data.contractCostNoTax,
                              contractCostTax: data.contractCostTax,
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
              let zxCtOtherWorksList = await this.tableOne.getTableData()
              let emptyData = []
              await zxCtOtherWorksList.map((item) => {
                if (!item.workNo) emptyData.push('????????????')
                if (!item.workName) emptyData.push('????????????')
                if (!item.qty) emptyData.push('??????')
                if (!item.price) emptyData.push('??????')
                if (!item.taxRate) emptyData.push('??????')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`?????????${emptyData.join()}`)
                return {}
              } else {
                await zxCtOtherWorksList.map((item) => {
                  if (item.addFlag === '1') item.zxCtOtherWorksId = null
                  return item
                })
                params.zxCtOtherWorksList = zxCtOtherWorksList
                params.zxCtOtherId = obj.rowData.zxCtOtherId
                return {
                  apiName: 'batchAddUpdateZxCtOtherWorks',
                  otherParams: params,
                  success: ({ success, message }) => {
                    if (success) {
                      let params = {}
                      params.zxCtOtherId = obj.rowData.zxCtOtherId
                      this.props.myFetch('getZxCtOtherDetail', params)
                        .then(({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              contractCost: data.contractCost,
                              contractCostNoTax: data.contractCostNoTax,
                              contractCostTax: data.contractCostTax,
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
                tableField: "OtherContract"
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
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractName',
                key: 'contractName',
                align: "center",
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractType',
                key: 'contractType',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'firstName',
                key: 'firstName',
                align: "center",
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
                align: "center",
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
                align: "center",
                width: 100,
              }
            },
            {
              table: {
                title: '??????????????????(??????)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'isDeduct',
                key: 'isDeduct',
                align: "center",
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
                dataIndex: 'csTimeLimit',
                key: 'csTimeLimit',
                align: "center",
                width: 100,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                align: "center",
                width: 120,
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
                title: '?????????',
                dataIndex: 'beginPer',
                key: 'beginPer',
                align: "center",
                width: 120,
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
              content: {
                formConfig: [
                  {
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'zxCtOtherId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'apih5FlowStatus',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'secondName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'companyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'contractCode',
                    type: 'string',
                    hide: true,
                  },
                  {
                      field: 'addDisabled',
                      type: 'string',
                      hide: true,
                  },
                  {
                    field: 'companyName',
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
                    label: '????????????',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    required:true,
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "contractName",
                    type: 'string',
                    required: true,
                    placeholder: '?????????',
                    span: 12
                  },
                  {
                    label: '????????????',
                    field: "contractType",
                    initialValue: '?????????',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12,
                    disabled: true,
                  },
                  {
                    label: '????????????',
                    field: "firstName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '????????????',
                    field: "firstId",
                    type: 'select',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        firstName: 'orgName',
                        companyId: 'companyId',
                        companyName: 'companyName',
                        orgID: 'orgID',
                        orgName: 'orgName',
                        contractCode: 'contractNo',
                      }
                    },
                    fetchConfig: (obj) => {
                      if (obj.btnCallbackFn.getTabsIndex() === '0') {
                        return {
                          apiName: 'getZxCtContractListByStatus',
                          otherParams: {
                            orgID: departmentId
                          }
                        }
                      }
                    },
                    required: true,
                    placeholder: '?????????',
                    span: 12,
                    onChange: (val, obj) => {
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        if (pageData.contractCategory) {
                          let params = {}
                          params.firstId = obj.itemData.contractNo
                          params.contractCategory = pageData.contractCategory
                          this.props.myFetch('getZxCtOtherContractNo', params).then(
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
                        }
                      } else {
                        obj.form.setFieldsValue({
                          contractNo: ''
                        })
                      }
                    }
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
                    fetchConfig: {
                      apiName: 'getZxCrCustomerExtAttrList',
                      otherParams: { type: 'ots' },
                      searchKey: 'customerName'
                    },
                    allowClear: false,
                    showSearch: true,
                    required: true,
                    placeholder: '?????????',
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
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '?????????????????????(??????)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '?????????',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '????????????(??????)',
                    field: "contractCostTax",
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
                    condition: [
                        {
                            regex: {
                                addDisabled: '1'
                            },
                            action: 'disabled'
                        }
                    ],
                    span: 12
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
                    field: "contractCategory",
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '????????????', value: 'FW' },
                      { label: '????????????', value: 'JG' },
                      { label: '?????????????????????', value: 'JS' },
                      { label: '????????????', value: 'QT' },
                      { label: '????????????????????????', value: 'XZ' },
                      { label: '????????????', value: 'ZC' },
                    ],
                    placeholder: '?????????',
                    span: 12,
                    required: true,
                    editDisabled: true,
                    onChange: (val, obj) => {
                      let pageData = obj.form.getFieldsValue()
                      if (val) {
                        if (pageData.firstId) {
                          let params = {}
                          params.firstId = pageData.contractCode
                          params.contractCategory = val
                          this.props.myFetch('getZxCtOtherContractNo', params).then(
                            ({ data, success, message }) => {
                              if (success) {
                                let contractNo = '';
                                contractNo = data.contractNo
                                obj.form.setFieldsValue({
                                  contractNo,
                                })
                              } else {
                                Msg.error(message);
                              }
                            }
                          );
                        }

                      } else {
                        obj.form.setFieldsValue({
                          contractNo: ''
                        })
                      }
                    }
                  },
                  {
                    label: '????????????',
                    field: 'content',
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
                  }
                ]
              }
            },
            {
              field: "form2",
              name: "qnnForm",
              title: "??????",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtOtherId"))
              },
              content: {
                fetchConfig: {
                  apiName: 'getZxCtOtherDetail',
                  otherParams: () => {
                    return {
                      zxCtOtherId: this.table.form.getFieldValue('zxCtOtherId')
                    }
                  }
                },
                formConfig: [
                  {
                    type: 'qnnTable',
                    field: 'zxCtOtherWorksList',
                    incToForm: true,
                    qnnTableConfig: {
                      antd: {
                        size: "small",
                        rowKey: "zxCtOtherWorksId",
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
                            field: 'zxCtOtherWorksId',
                            type: 'string',
                            hide: true,
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'workNo',
                            key: 'workNo',
                            width: 150,
                            align: 'center',
                            tdEdit: true,
                          },
                          form: {
                            required: true,
                            type: 'string',
                            placeholder: '?????????',
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            width: 180,
                            tdEdit: true,
                            dataIndex: 'workName',
                            key: 'workName',
                            align: 'center',
                          },
                          form: {
                            required: true,
                            type: 'string',
                            placeholder: '?????????',
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 100,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            placeholder: '?????????',
                          }
                        },
                        {
                          table: {
                            title: '????????????',
                            dataIndex: 'qty',
                            key: 'qty',
                            width: 100,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            required: true,
                            precision: 3,
                            type: 'number',
                            placeholder: '?????????',
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.qty = val
                              rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)  //??????????????????
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(rowData.taxRate) / 100) //?????????????????????
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '??????????????????(???)',
                            dataIndex: 'price',
                            key: 'price',
                            width: 150,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            required: true,
                            type: 'number',
                            placeholder: '?????????',
                            precision: 6,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.price = val
                              rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)   //??????????????????
                              if (rowData.taxRate) {
                                let priceNoTax = (val ? Number(val) : 0) / (1 + Number(rowData.taxRate) / 100) //???????????????
                                rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) / (1 + Number(rowData.taxRate) / 100) //?????????????????????
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
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
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            required: true,
                            type: 'select',
                            optionConfig: {
                              label: 'itemName',
                              value: 'itemId',
                            },
                            fetchConfig: {
                              apiName: 'getBaseCodeSelect',
                              otherParams: { itemId: 'shuiLv' }
                            },
                            allowClear: false,
                            placeholder: '?????????',
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.taxRate = val
                              let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100) //?????????????????????
                              rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
                              rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) / (1 + (Number(val) ? Number(val) : 0) / 100)
                              await obj.qnnTableInstance.setEditedRowData(rowData)
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
                            render: () => {
                              let isDeduct = this.table.form.getFieldValue('isDeduct')
                              if (isDeduct === '1') {
                                return '???';
                              } else {
                                return '???';
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
                            render: (val, rowData) => {
                              return rowData.contractSum ? rowData.contractSum.toFixed(2) : 0
                            }
                          }
                        },

                        {
                          table: {
                            title: '?????????????????????',
                            dataIndex: 'contractSumNoTax',
                            key: 'contractSumNoTax',
                            width: 120,
                            align: 'center',
                            render: (val, rowData) => {
                              return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                            }
                          },
                        },
                        {
                          table: {
                            title: '??????',
                            dataIndex: 'remark',
                            key: 'remark',
                            width: 150,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
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