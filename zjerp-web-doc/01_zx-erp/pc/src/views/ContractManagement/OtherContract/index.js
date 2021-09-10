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
              if (index === "1" || obj?.btnCallbackFn?.form?.getFieldValue?.('zxCtOtherId')) {
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
              let zxCtOtherWorksList = await this.tableOne.getTableData()
              let emptyData = []
              await zxCtOtherWorksList.map((item) => {
                if (!item.workNo) emptyData.push('清单编号')
                if (!item.workName) emptyData.push('清单名称')
                if (!item.qty) emptyData.push('数量')
                if (!item.price) emptyData.push('金额')
                if (!item.taxRate) emptyData.push('税率')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`请填写${emptyData.join()}`)
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
              let zxCtOtherWorksList = await this.tableOne.getTableData()
              let emptyData = []
              await zxCtOtherWorksList.map((item) => {
                if (!item.workNo) emptyData.push('清单编号')
                if (!item.workName) emptyData.push('清单名称')
                if (!item.qty) emptyData.push('数量')
                if (!item.price) emptyData.push('金额')
                if (!item.taxRate) emptyData.push('税率')
                return item
              })
              if (emptyData.length > 0) {
                Msg.warn(`请填写${emptyData.join()}`)
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
                tableField: "OtherContract"
              }
            }
          }}
          formConfig={[
            {
              table: {
                title: '合同编号',
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
                title: '合同名称',
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
                title: '合同类型',
                dataIndex: 'contractType',
                key: 'contractType',
                align: "center",
                width: 120,
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
                fieldsConfig: { type: 'string' },
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
                width: 100,
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
                title: '合同类别',
                dataIndex: 'contractCategory',
                key: 'contractCategory',
                align: "center",
                width: 120,
                render: (h) => {
                  if (h === 'FW') {
                    return '房屋租赁'
                  } else if (h === 'JG') {
                    return '加工承揽'
                  } else if (h === 'JS') {
                    return '技术咨询或服务'
                  } else if (h === 'QT') {
                    return '其他合同'
                  } else if (h === 'XZ') {
                    return '行政办公用品采购'
                  } else if (h === 'ZC') {
                    return '征地拆迁'
                  }
                }
              }
            },
            {
              table: {
                title: '发起人',
                dataIndex: 'beginPer',
                key: 'beginPer',
                align: "center",
                width: 120,
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
                    label: '合同编号',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    required:true,
                    span: 12
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
                    label: '合同类型',
                    field: "contractType",
                    initialValue: '其它类',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                    disabled: true,
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
                    placeholder: '请选择',
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
                    label: '乙方名称',
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
                    placeholder: '请选择',
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
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '不含税合同金额(万元)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12,
                    precision: 6
                  },
                  {
                    label: '合同税额(万元)',
                    field: "contractCostTax",
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
                      { label: '房屋租赁', value: 'FW' },
                      { label: '加工承揽', value: 'JG' },
                      { label: '技术咨询或服务', value: 'JS' },
                      { label: '其他合同', value: 'QT' },
                      { label: '行政办公用品采购', value: 'XZ' },
                      { label: '征地拆迁', value: 'ZC' },
                    ],
                    placeholder: '请选择',
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
                    label: '合同内容',
                    field: 'content',
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
              field: "form2",
              name: "qnnForm",
              title: "清单",
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
                            field: 'zxCtOtherWorksId',
                            type: 'string',
                            hide: true,
                          }
                        },
                        {
                          table: {
                            title: '清单编号',
                            dataIndex: 'workNo',
                            key: 'workNo',
                            width: 150,
                            align: 'center',
                            tdEdit: true,
                          },
                          form: {
                            required: true,
                            type: 'string',
                            placeholder: '请输入',
                          }
                        },
                        {
                          table: {
                            title: '清单名称',
                            width: 180,
                            tdEdit: true,
                            dataIndex: 'workName',
                            key: 'workName',
                            align: 'center',
                          },
                          form: {
                            required: true,
                            type: 'string',
                            placeholder: '请输入',
                          }
                        },
                        {
                          table: {
                            title: '计量单位',
                            dataIndex: 'unit',
                            key: 'unit',
                            width: 100,
                            tdEdit: true,
                            align: 'center',
                          },
                          form: {
                            type: 'string',
                            placeholder: '请输入',
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
                              rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)  //含税合同金额
                              if (rowData.taxRate && rowData.taxRate !== '0') {
                                rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + Number(rowData.taxRate) / 100) //不含税合同金额
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            },
                          }
                        },
                        {
                          table: {
                            title: '含税合同单价(元)',
                            dataIndex: 'price',
                            key: 'price',
                            width: 150,
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
                              rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)   //含税合同金额
                              if (rowData.taxRate) {
                                let priceNoTax = (val ? Number(val) : 0) / (1 + Number(rowData.taxRate) / 100) //不含税单价
                                rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) / (1 + Number(rowData.taxRate) / 100) //不含税合同金额
                              }
                              await obj.qnnTableInstance.setEditedRowData(rowData)
                            }
                          }
                        },
                        {
                          table: {
                            title: '不含税合同单价(元)',
                            dataIndex: 'priceNoTax',
                            key: 'priceNoTax',
                            width: 150,
                            align: 'center',
                          }
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
                              let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100) //不含税合同单价
                              rowData.priceNoTax = Math.round(priceNoTax * 1000000) / 1000000
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
                                return '是';
                              } else {
                                return '否';
                              }
                            }
                          }
                        },
                        {
                          table: {
                            title: '含税合同金额',
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
                            title: '不含税合同金额',
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