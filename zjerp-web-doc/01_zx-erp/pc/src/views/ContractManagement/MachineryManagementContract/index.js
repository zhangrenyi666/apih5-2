import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import MachineryManagementContractLeaseForm from './formLease';
import MachineryManagementContractPayForm from './formPay';
import Operation from './operation'
import DeetailFormForRent from './detail';
import DeetailFormForPay from './detailForPay';
const config = {
  antd: {
    rowKey: 'zxCtEqContrApplyId',
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
  },
  firstRowIsSearch: false,
  isShowRowSelect: true
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
            apiName: 'getZxCtEqContrApplyList',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              this.table.refresh();
            }
          }}
          componentsKey={{
            //发起评审
            MachineryManagementContractForm: (obj) => {
              if (obj.qnnTableInstance.getSelectedRows()?.[0]?.contractCategory === '0') {
                return <MachineryManagementContractLeaseForm
                  {...this.props}
                  isInQnnTable={obj.isInQnnTable}
                  btnCallbackFn={obj.btnCallbackFn}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              } else {
                return <MachineryManagementContractPayForm
                  {...this.props}
                  isInQnnTable={obj.isInQnnTable}
                  btnCallbackFn={obj.btnCallbackFn}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              }

            },
            //详细
            DeetailForm: (obj) => {
              if (obj.qnnTableInstance.getSelectedRows()?.[0]?.contractCategory === '0') {
                return <DeetailFormForRent isInQnnTable={obj.isInQnnTable}
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
              if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCtEqContrApplyId')) {
                return true;
              } else {
                return false;
              }
            },
            //新增保存按钮回调
            addSuccessCb: (obj) => {
              if (obj.response.success) {
                obj.response.data.addDisabled = '1';
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
              let itemList = await this.tableOne.getTableData()
              await itemList.map((item) => {
                item.conRevDetailId = null
                return item
              })
              params.itemList = itemList
              params.addFlag = '1'
              params.zxCtEqContrApplyId = this.table.form.getFieldValue('zxCtEqContrApplyId')
              return {
                apiName: 'batchSaveUpdateZxCtEqContrItem',
                otherParams: params,
                success: ({ success, message }) => {
                  if (success) {
                    let params = {}
                    params.zxCtEqContrApplyId = this.table.form.getFieldValue('zxCtEqContrApplyId')
                    this.props.myFetch('getZxCtEqContrApplyDetail', params)
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
              let itemList = await this.tableOne.getTableData()
              await itemList.map((item) => {
                if (item.addFlag === '1') item.conRevDetailId = null
                return item
              })
              params.itemList = itemList
              params.zxCtEqContrApplyId = obj.rowData.zxCtEqContrApplyId
              return {
                apiName: 'batchSaveUpdateZxCtEqContrItem',
                otherParams: params,
                success: ({ success, message }) => {
                  if (success) {
                    let params = {}
                    params.zxCtEqContrApplyId = obj.rowData.zxCtEqContrApplyId
                    this.props.myFetch('getZxCtEqContrApplyDetail', params)
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
            }
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "MachineryManagementContract"
              }
            }
          }}
          desc="注意：评审附加需包括技术规格书、图纸、随机工具、易损件清单、随机配件"
          formConfig={[
            {
              table: {
                title: '合同编号',
                dataIndex: 'contractNo',
                key: 'contractNo',
                align: "center",
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
                onClick: 'detail',
              }
            },
            {
              table: {
                title: '合同名称',
                dataIndex: 'contractName',
                key: 'contractName',
                align: "center",
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
              }
            },
            {
              table: {
                title: '合同类别',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                align: "center",
                width: 100,
                render: (data) => {
                  if (data === '0') {
                    return '设备租赁';
                  } else if (data === '1') {
                    return '设备采购';
                  }
                }
              }
            },
            {
              table: {
                title: '甲方名称',
                dataIndex: 'firstName',
                key: 'firstName',
                align: "center",
                width: 180,
                filter: true,
                fieldsConfig: { type: 'string' }
              }
            },
            {
              table: {
                title: '乙方名称',
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
                title: '合同签订人',
                dataIndex: 'agent',
                key: 'agent',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '含税合同金额(万元)',
                dataIndex: 'contractCost',
                key: 'contractCost',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '是否抵扣',
                dataIndex: 'isDeduct',
                key: 'isDeduct',
                align: "center",
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
                title: '合同工期',
                dataIndex: 'csTimeLimit',
                key: 'csTimeLimit',
                align: "center",
                width: 100,
              }
            },
            {
              table: {
                title: '合同类型',
                dataIndex: 'pp3',
                key: 'pp3',
                align: "center",
                width: 150,
              }
            },
            {
              table: {
                title: '发起人',
                dataIndex: 'createUserName',
                key: 'createUserName',
                align: "center",
                width: 120,
              }
            },
            {
              table: {
                title: '评审状态',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                align: "center",
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
                    field: 'zxCtEqContrApplyId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'workId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'addDisabled',
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
                    field: 'comName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'id',
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
                    label: '合同编号',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    span: 12,
                    required: true
                  },
                  {
                    label: '合同名称',
                    field: "contractName",
                    type: 'string',
                    required: true,
                    placeholder: '请输入',
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
                    label: '甲方名称',
                    field: "firstName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '甲方名称',
                    field: "firstID",
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
                        comID: 'companyId',
                        comName: 'companyName',
                        orgID: 'orgID',
                        orgName: 'orgName',
                        id: 'id'
                      }
                    },
                    fetchConfig: (obj) => {
                      if (obj.btnCallbackFn.getTabsIndex() === '0') {
                        return {
                          apiName: 'getZxCtContractListByStatus',
                          otherParams: { orgID: departmentId }
                        }
                      }
                      return {}
                    },
                    required: true,
                    placeholder: '请选择',
                    span: 12,
                    onChange: (val, obj) => {
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        if (pageData.contractCategory) {
                          let params = {}
                          params.zxCtContractId = obj.itemData.id
                          params.contractCategory = pageData.contractCategory
                          params.orgID = obj.itemData.orgID
                          this.props.myFetch('generateZxCtEqContrApplyContractNo', params).then(
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
                    label: '乙方名称',
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
                    placeholder: '请选择',
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
                    label: '是否抵扣',
                    field: "isDeduct",
                    type: "radio",
                    initialValue: '0',
                    optionData: [
                      { label: "是", value: "1" },
                      { label: "否", value: "0" }
                    ],
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
                    label: '合同工期',
                    field: "csTimeLimit",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同类别',
                    field: "contractCategory",
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '设备租赁', value: '0' },
                      { label: '设备采购', value: '1' },
                    ],
                    placeholder: '请选择',
                    span: 12,
                    required: true,
                    condition: [
                      {
                        regex: { zxCtEqContrApplyId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    onChange: (val, obj) => {
                      let pageData = obj.form.getFieldsValue()
                      if (val) {
                        if (pageData.firstID) {
                          let params = {}
                          params.zxCtContractId = pageData.id
                          params.contractCategory = val
                          params.orgID = pageData.orgID
                          this.props.myFetch('generateZxCtEqContrApplyContractNo', params).then(
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
                    label: '合同类型',
                    field: "pp3",
                    type: 'string',
                    initialValue: '机械管理类',
                    span: 12,
                    disabled: true
                  },
                  {
                    label: '合同内容',
                    field: 'content',
                    type: 'textarea',
                    required: true,
                    placeholder: '请输入',
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
                    label: '备注',
                    field: 'remark',
                    type: 'textarea',
                    placeholder: '请输入',
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
              field: "table1",
              name: "qnnForm",
              title: "清单",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxCtEqContrApplyId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtEqContrItemList',
                    otherParams: { zxCtEqContrApplyId: obj.tableFns.form.getFieldValue('zxCtEqContrApplyId') },
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
                        rowKey: "zxCtEqContrItemId",
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
                            title: '编号',
                            dataIndex: 'catCode',
                            key: 'catCode',
                            width: 150,
                            disabled: true,
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
                            title: '编号',
                            width: 150,
                            tdEdit: true,
                            fixed: 'left',
                            dataIndex: 'catCode',
                            align: 'center',
                            key: 'catCode',
                          },
                          form: {
                            required: true,
                            type: 'selectByQnnTable',
                            optionConfig: {
                              label: 'catCode',
                              value: 'id',
                            },
                            dropdownMatchSelectWidth: 800,
                            qnnTableConfig: {
                              antd: {
                                rowKey: "id"
                              },
                              fetchConfig: {
                                apiName: "getZxEqResCategoryItemList",
                                otherParams: { isGroup: '1' }
                              },
                              searchBtnsStyle: "inline",
                              formConfig: [
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "catCode",
                                    title: "编号",
                                    width: 100
                                  },
                                  form: {
                                    type: 'string'
                                  }
                                },
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "catName",
                                    title: "名称",
                                    width: 100
                                  },
                                  form: {
                                    type: 'string'
                                  }
                                }
                              ]
                            },
                            allowClear: false,
                            showSearch: true,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.catParentName = obj.itemData.parentName
                              rowData.catName = obj.itemData.catName
                              rowData.catCode = obj.itemData.catCode
                              rowData.spec = obj.itemData.spec
                              rowData.unit = obj.itemData.unit
                              rowData.parentID = obj.itemData.parentID
                              rowData.zxEqResCategoryId = obj.itemData.id
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                            placeholder: '请选择'
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
                            title: '设备分类',
                            dataIndex: 'catParentName',
                            key: 'catParentName',
                            width: 150,
                            disabled: true,
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
                            title: '设备名称',
                            width: 150,
                            tdEdit: true,
                            fixed: 'left',
                            dataIndex: 'catName',
                            align: 'center',
                            key: 'catName',
                          },
                          form: {
                            required: true,
                            type: 'selectByQnnTable',
                            optionConfig: {
                              label: 'catCode',
                              value: 'id',
                            },
                            allowClear: false,
                            dropdownMatchSelectWidth: 800,
                            qnnTableConfig: {
                              antd: { rowKey: "id" },
                              fetchConfig: {
                                apiName: "getZxEqResCategoryItemList",
                                otherParams: { isGroup: '1' }
                              },
                              searchBtnsStyle: "inline",
                              formConfig: [
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "catCode",
                                    title: "编号",
                                    width: 100
                                  },
                                  form: {
                                    type: 'string'
                                  }
                                },
                                {
                                  isInForm: false,
                                  isInSearch: true,
                                  table: {
                                    dataIndex: "catName",
                                    title: "名称",
                                    width: 100
                                  },
                                  form: {
                                    type: 'string'
                                  }
                                }
                              ]
                            },
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.catCode = obj.itemData.catCode
                              rowData.catName = obj.itemData.catName
                              rowData.spec = obj.itemData.spec
                              rowData.rentUnit = obj.itemData.unit
                              rowData.parentID = obj.itemData.parentID
                              rowData.zxEqResCategoryId = obj.itemData.id
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                            placeholder: '请选择'
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
                            title: '名称',
                            dataIndex: 'catName',
                            key: 'catName',
                            width: 150,
                            disabled: true,
                            align: 'center',
                          },
                        },
                        {
                          table: {
                            title: '型号',
                            dataIndex: 'spec',
                            key: 'spec',
                            width: 120,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            placeholder: '请输入'
                          }
                        },
                        {
                          isInTable: (obj) => {
                            if (this.table?.form?.getFieldValue('contractCategory') === '0') {
                              return false
                            }
                            return true
                          },
                          table: {
                            title: '单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 100,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            placeholder: '请输入'
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
                            title: '租赁单位',
                            dataIndex: 'rentUnit',
                            key: 'rentUnit',
                            width: 100,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            placeholder: '请输入'
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
                            title: '租赁开始时间',
                            dataIndex: 'actualStartDate',
                            key: 'actualStartDate',
                            format: 'YYYY-MM-DD',
                            width: 150,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'date',
                            placeholder: '请选择'
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
                            title: '租赁结束时间',
                            dataIndex: 'actualEndDate',
                            format: 'YYYY-MM-DD',
                            key: 'actualEndDate',
                            width: 150,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'date',
                            placeholder: '请选择'
                          }
                        },
                        {
                          table: {
                            title: '合同数量',
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
                            placeholder: '请输入',
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.qty = val
                              rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(rowData.taxRate) / 100)
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '含税合同单价(元/台)',
                            dataIndex: 'price',
                            key: 'price',
                            width: 180,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            required: true,
                            type: 'number',
                            placeholder: '请输入',
                            precision: 6,
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.price = val
                              let priceNoTax = (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税单价
                              rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
                              rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)
                              rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税合同金额
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '含税合同金额',
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
                            render: (val, rowData) => {
                              return rowData.contractSumNoTax ? rowData.contractSumNoTax.toFixed(2) : 0
                            }
                          },
                        },
                        {
                          table: {
                            title: '税率(%)',
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
                            placeholder: '请选择',
                            onChange: async (val, obj) => {
                              let rowData = await obj.qnnTableInstance.getEditedRowData()
                              rowData.taxRate = val
                              let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100)
                              rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
                              rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0)
                              rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) / (1 + (Number(val) ? Number(val) : 0) / 100)
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
                              let isDeduct = this.table.form.getFieldValue('isDeduct')
                              if (isDeduct === '1') {
                                return '是'
                              } else {
                                return '否'
                              }
                            }
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
                            type: 'string',
                            placeholder: '请输入'
                          }
                        },
                      ]
                    }
                  }
                ],
              }
            },
          ]}
        />
      </div>
    );
  }
}

export default index;