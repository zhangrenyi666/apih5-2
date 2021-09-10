import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import CollateralContractForm from './form';
import { Toast } from 'antd-mobile';
import Operation from './operation';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'contractReviewId',
    size: 'small'
  },
  drawerConfig: {
    width: window.screen.width * 0.7
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
    this.state = {

    }
  }
  render() {
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
            apiName: 'getZxCtFsContractReviewList',
            otherParams: { orgId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
          }}
          componentsKey={{
            CollateralContractForm: (obj) => {
              this.table.clearSelectedRows();
              return <CollateralContractForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            }
          }}
          actionBtns={[
            {
              name: 'add',//内置add del
              icon: 'plus',//icon
              type: 'primary',//类型  默认 primary  [primary dashed danger]
              label: '新增',
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.setState({
                  lineEdit: ''
                })
              },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                },
                {
                  name: 'diySubmit',//内置add del
                  field: 'diySubmit',
                  label: '保存',//提交数据并且关闭右边抽屉 
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                    this.props.myFetch('addZxCtFsContractReview', obj._formData).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsDisabled('add', 'diySubmit');
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                          obj.btnCallbackFn.form.setFieldsValue(data);
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
            },
            {
              name: "edit",
              label: "修改",
              field: "edit",
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus === '1' || SelectedRowsData[0].apih5FlowStatus === '2') {
                  return true;
                } else {
                  return false;
                }
              },
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.table.clearSelectedRows();
                this.setState({
                  lineEdit: ''
                })
              },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                },
                {
                  name: 'diySubmit',
                  field: 'diySubmit',
                  type: 'primary',
                  label: '保存',
                  hide: (obj) => {
                    var index = obj.btnCallbackFn.getActiveKey();
                    if (index === "1") {
                      return true;
                    } else {
                      return false;
                    }
                  },
                  onClick: (obj) => {
                    obj.btnCallbackFn.clearSelectedRows();
                    obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
                    this.props.myFetch('updateZxCtFsContractReview', obj._formData).then(
                      ({ data, success, message }) => {
                        if (success) {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.success(message);
                          obj.btnCallbackFn.refresh();
                          obj.btnCallbackFn.setActiveKey('1');
                          if (this.tableOne) {
                            this.tableOne.refresh()
                          }
                        } else {
                          obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                          Msg.error(message);
                        }
                      }
                    );
                  }
                }
              ]
            },
            {
              name: 'Component',
              type: 'primary',
              label: '进度查询',
              drawerTitle: '进度查询',
              disabled: (obj) => {
                let data = obj.btnCallbackFn.getSelectedRows();
                if (data.length === 1 && data[0].workId) {
                  return false;
                } else {
                  return true;
                }
              },
              Component: (props) => {
                return <Operation apiName={'openFlowByReport'} {...props} />
              }
            },
            {
              name: 'del',//内置add del
              icon: 'delete',//icon
              type: 'danger',//类型  默认 primary  [primary dashed danger]
              label: '删除',
              field: "del",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length > 0) {
                  for (var i = 0; i < SelectedRowsData.length; i++) {
                    if (SelectedRowsData[i].apih5FlowStatus === '1' || SelectedRowsData[i].apih5FlowStatus === '2') {
                      return true
                    }
                  }
                }
              },
              fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZxCtFsContractReview',
              },
            },
            {
              Component: "CollateralContractForm",
              disabled: "bind:_actionBtnNoSelected",
              drawerTitle: "发起",
              icon: "plus",
              label: "审核",
              name: "Component",
              tableField: "projectInfo",
              tableName: "附属合同",
              type: "primary",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length !== 1 || SelectedRowsData[0].apih5FlowStatus !== '-1') {
                  return true;
                } else {
                  return false;
                }
              },
            },
          ]}
          formConfig={[
            {
              table: {
                title: '附属合同编号',
                dataIndex: 'contractNo',
                key: 'contractNo',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
                onClick: 'detail',
                tooltip: 22,
              }
            },
            {
              table: {
                title: '附属合同名称',
                dataIndex: 'contractName',
                key: 'contractName',
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                width: 180,
                tooltip: 18,
              }
            },
            {
              table: {
                title: '合同类型',
                dataIndex: 'contractType',
                key: 'contractType',
                width: 120
              }
            },
            {
              table: {
                title: '原合同编号',
                dataIndex: 'fromContractNo',
                key: 'fromContractNo',
                width: 150,
                filter: true,
                fieldsConfig: { type: 'string' },
              }
            },
            {
              table: {
                title: '合同类别',
                dataIndex: 'code7',
                key: 'code7',
                width: 100,
                render: (h) => {
                  if (h === '0') {
                    return '物资其他类'
                  } else if (h === '1') {
                    return '物资运输类'
                  }
                }
              }
            },
            {
              table: {
                title: '甲方名称',
                dataIndex: 'firstName',
                key: 'firstName',
                width: 150,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 15,
              }
            },
            {
              table: {
                title: '乙方名称',
                dataIndex: 'secondName',
                key: 'secondName',
                width: 150,
                filter: true,
                fieldsConfig: { type: 'string' },
                tooltip: 15,
              }
            },
            {
              table: {
                title: '合同签订人',
                dataIndex: 'agent',
                key: 'agent',
                width: 100,
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
                title: '合同工期',
                dataIndex: 'csTimeLimit',
                key: 'csTimeLimit',
                width: 100
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
                    field: 'contractReviewId',
                    type: 'string',
                    hide: true,
                  },
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
                    field: 'comId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '附属合同编号',
                    field: "contractNo",
                    type: 'string',
                    disabled: true,
                    span: 12
                  },
                  {
                    label: '附属合同名称',
                    field: "contractName",
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '合同类型',
                    field: "contractType",
                    initialValue: '附属类',
                    type: 'string',
                    required: true,
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
                    addShow:false,
                    editShow:true,
                    detailShow:true,
                  },
                  {
                    label: '原合同编号',
                    field: "fromContractNo",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow:false,
                    editShow:true,
                    detailShow:true,
                  },
                  {
                    label: '甲方名称',
                    field: "firstId",
                    type: 'select',
                    showSearch: true,
                    addShow:true,
                    editShow:false,
                    detailShow:false,
                    onChange: (val, obj) => {
                      this.setState({
                        firstId: null
                      })
                      obj.form.setFieldsValue({
                        zxCtFsContractId: null
                      })
                    },
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        firstName: 'orgName',
                        comId: "companyId",
                        comName: 'companyName',
                        orgId: 'orgID',
                        orgName: 'orgName'
                      }
                    },
                    condition: [
                      {
                        regex: { contractReviewId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    fetchConfig: (obj) => {
                      if (obj.tabsActiveKey === '0' && obj.clickCb.rowInfo.name === 'add') {
                        return {
                          apiName: 'getZxCtContractListByStatus',
                          otherParams: { orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId }
                        }
                      }
                    },
                    required: true,
                    placeholder: '请选择',
                    span: 12
                  },
                  {
                    label: '原合同编号',
                    field: "zxCtFsContractId",
                    span: 12,
                    required: true,
                    type: 'select',
                    showSearch: true,
                    addShow:true,
                    editShow:false,
                    detailShow:false,
                    optionConfig: {
                      label: 'contractNo',
                      value: 'zxCtFsContractId',
                      linkageFields: {
                        fromContractNo: 'contractNo',
                      }
                    },
                    condition: [
                      {
                        regex: { contractReviewId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
                    fetchConfig: (obj) => {
                      let rowData = obj.btnCallbackFn.form.getFieldsValue();
                      if ((obj?.initialValues?.firstId && obj.tabsActiveKey === '0' && obj.clickCb.rowInfo.name === 'add') || (rowData?.firstId && obj.tabsActiveKey === '0' && obj.clickCb.rowInfo.name === 'add')) {
                        return {
                          apiName: 'getZxCtSuppliesContractListByOrgId',
                          otherParams: {
                            orgID: rowData?.firstId ? rowData?.firstId : obj?.initialValues?.firstId,
                            pp9: '1',
                            contractStatus: '1'
                          },
                        }
                      }
                    },
                    onChange: (val, obj) => {
                      if (val) {
                        let pageData = obj.form.getFieldsValue()
                        let params = {}
                        params.fromContractNo = pageData.fromContractNo
                        this.props.myFetch('getZxCtFsContractNo', params).then(
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
                      } else {
                        obj.form.setFieldsValue({
                          contractNo: ''
                        })
                      }
                    },
                    placeholder: '请选择',
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
                    label: '合同类别',
                    field: "code7",
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: '物资其他类', value: '0' },
                      { label: '物资运输类', value: '1' },
                    ],
                    placeholder: '请选择',
                    span: 12,
                    required: true,
                    condition: [
                      {
                        regex: { contractReviewId: ['!', /(''|null|undefined)/ig] },
                        action: 'disabled',
                      }
                    ],
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
                    span: 12
                  },
                  {
                    label: '不含税合同金额(万元)',
                    field: "contractCostNoTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '合同税额(万元)',
                    field: "contractCostTax",
                    type: 'number',
                    disabled: true,
                    initialValue: 0,
                    placeholder: '请输入',
                    span: 12
                  },
                  {
                    label: '是否抵扣',
                    field: "isDeduct",
                    type: "radio",
                    initialValue: '0',
                    optionData: [
                      {
                        label: "是",
                        value: "1"
                      },
                      {
                        label: "否",
                        value: "0"
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
                    field: 'remarks',
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
                    label: '上传附件',
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
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "清单",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("contractReviewId"))
              },
              content: {
                drawerConfig: {
                  width: '1000px'
                },
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtFsContractReviewDetailList',
                    otherParams: { contractReviewId: obj.tableFns.form.getFieldValue('contractReviewId') },
                    success: () => {
                      let params = {}
                      params.contractReviewId = obj.tableFns.form.getFieldValue('contractReviewId')
                      this.props.myFetch('getZxCtFsContractReviewDetail', params).then(
                        ({ data, success, message }) => {
                          if (success) {
                            this.table.form.setFieldsValue({
                              contractCost: data.contractCost,
                              contractCostNoTax: data.contractCostNoTax,
                              contractCostTax: data.contractCostTax,
                            })
                          } else {
                            Msg.error(message);
                          }
                        }
                      );
                    }
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "conRevDetailId",
                },
                rowDisabledCondition: (rowData) => {
                  return rowData.conRevDetailId !== this.state.lineEdit
                },
                paginationConfig: false,
                isShowRowSelect: true,
                actionBtns: [
                  {
                    name: 'diyaddRow',
                    icon: 'plus',
                    type: 'primary',
                    label: '新增行',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    onClick: () => {
                      let tableOneData = this.tableOne.state.data ? this.tableOne.state.data : [];
                      if (tableOneData && tableOneData.length > 0) {
                        for (var i = 0; i < tableOneData.length; i++) {
                          if (tableOneData[i].conRevDetailId === this.state.lineEdit) {
                            Msg.warn('有未保存数据，请保存后重试。')
                            return
                          }
                        }
                      }
                      let newRowData = {}
                      newRowData.conRevDetailId = Date.parse(new Date())
                      tableOneData.push(newRowData)
                      this.tableOne.btnCallbackFn.setState({
                        data: tableOneData
                      })
                      this.setState({
                        lineEdit: newRowData.conRevDetailId
                      })
                    },
                  },
                  {
                    name: 'diydel',
                    icon: 'del',
                    type: 'danger',
                    label: '删除',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    disabled: (obj) => {
                      if (obj.btnCallbackFn.getSelectedRows().length) {
                        return false;
                      } else {
                        return true;
                      }
                    },
                    onClick: (obj) => {
                      confirm({
                        content: '确定删除选中的数据吗?',
                        onOk: () => {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].conRevDetailId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          let deleteData = obj.selectedRows
                          this.props.myFetch('batchDeleteUpdateZxCtFsContractReviewDetail', deleteData).then(
                            ({ success, message }) => {
                              if (success) {
                                Msg.success(message);
                                obj.btnCallbackFn.refresh();
                                this.table.refresh()
                              } else {
                                Msg.error(message);
                              }
                            }
                          );
                        }
                      })
                    }
                  },
                  {
                    name: 'diydel',
                    icon: 'plus',
                    type: 'primary',
                    label: '导入',
                    hide: (obj) => {
                      if (obj.props.clickCb.rowInfo.name === 'detail') {
                        return true;
                      } else {
                        return false;
                      }
                    },
                    addCb: (obj) => {
                      console.log('导入', obj);
                    },
                  },
                ],
                formConfig: [
                  {
                    isInTable: false,
                    form: {
                      field: 'conRevDetailId',
                      type: 'string',
                      hide: true,
                    }
                  },
                  {
                    table: {
                      title: `<div>清单编号<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'workNo',
                      key: 'workNo',
                      width: 100,
                      align: 'center',
                      tdEdit: true,
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                    }
                  },
                  {
                    table: {
                      title: `<div>清单名称<span style='color: #ff4d4f'>*</span></div>`,
                      width: 150,
                      tdEdit: true,
                      dataIndex: 'workName',
                      key: 'workName',
                      align: 'center',
                    },
                    form: {
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
                      type: 'number',
                      align: 'center',
                    },
                    form: {
                      precision: 3,
                      type: 'number',
                      placeholder: '请输入',
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.conRevDetailId === this.state.lineEdit) {
                              if (Number(data.qty) !== Number(rowData.qty)) {
                                rowData.qty = val ? Number(val) : 0;
                                rowData.contractSum = (val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)  //含税合同金额
                                rowData.contractSumNoTax = ((val ? Number(val) : 0) * (rowData.price ? rowData.price : 0)) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税合同金额
                                rowData.unit = data.unit
                                rowData.workName = data.workName
                                rowData.workNo = data.workNo
                                rowData.remarks = data.remarks
                              } else {
                                rowData = null;
                              }
                            }
                            return rowData;
                          })
                          if (!tableOneData.filter(item => item === null).length) {
                            setTimeout(() => {
                              this.tableOne.btnCallbackFn.setState({
                                data: tableOneData
                              })
                            }, 0)
                          }
                        })
                      }
                    }
                  },
                  {
                    table: {
                      title: `<div>含税合同单价(元)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'price',
                      key: 'price',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.conRevDetailId === this.state.lineEdit) {
                              if (Number(data.price) !== Number(rowData.price)) {
                                rowData.price = val ? Number(val) : 0;
                                let priceNoTax = (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税单价
                                rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0)   //含税合同金额
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) / (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100) //不含税合同金额
                                rowData.unit = data.unit
                                rowData.workName = data.workName
                                rowData.workNo = data.workNo
                                rowData.remarks = data.remarks
                              } else {
                                rowData = null;
                              }
                            }
                            return rowData;
                          })
                          if (!tableOneData.filter(item => item === null).length) {
                            setTimeout(() => {
                              this.tableOne.btnCallbackFn.setState({
                                data: tableOneData
                              })
                            }, 0)
                          }
                        })
                      }
                    }
                  },
                  {
                    table: {
                      title: '不含税合同单价(元)',
                      dataIndex: 'priceNoTax',
                      key: 'priceNoTax',
                      width: 150,
                      tdEdit: true,
                      align: 'center',
                    },
                    form: {
                      type: 'number',
                      placeholder: '请输入',
                      precision: 6,
                      onBlur: (obj) => {
                        let val = obj.target.value;
                        this.tableOne.getEditedRowData(false).then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.conRevDetailId === this.state.lineEdit) {
                              if (Number(data.priceNoTax) !== Number(rowData.priceNoTax)) {
                                rowData.priceNoTax = val ? Number(val) : 0;
                                let price = (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //含税合同单价
                                rowData.price = Math.round(price * 100) / 100
                                rowData.contractSum = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) * (1 + (rowData.taxRate ? Number(rowData.taxRate) : 0) / 100)  //含税合同金额
                                rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (val ? Number(val) : 0) //不含税合同金额
                                rowData.unit = data.unit
                                rowData.workName = data.workName
                                rowData.workNo = data.workNo
                                rowData.remarks = data.remarks
                              } else {
                                rowData = null;
                              }
                            }
                            return rowData;
                          })
                          if (!tableOneData.filter(item => item === null).length) {
                            setTimeout(() => {
                              this.tableOne.btnCallbackFn.setState({
                                data: tableOneData
                              })
                            }, 0)
                          }
                        })
                      },
                    }
                  },
                  {
                    table: {
                      title: `<div>税率(%)<span style='color: #ff4d4f'>*</span></div>`,
                      dataIndex: 'taxRate',
                      key: 'taxRate',
                      width: 100,
                      tdEdit: true,
                      type: 'select',
                      align: 'center',
                      initialValue: 0
                    },
                    form: {
                      field: "taxRate",
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
                      onChange: (val, obj, btnCallbackFn) => {
                        btnCallbackFn.getEditedRowData().then((data) => {
                          let tableOneData = this.tableOne.state.data.map((rowData) => {
                            if (rowData.conRevDetailId === this.state.lineEdit) {
                              rowData.taxRate = val;
                              let priceNoTax = (rowData.price ? rowData.price : 0) / (1 + (val ? Number(val) : 0) / 100) //不含税合同单价
                              rowData.priceNoTax = Math.round(priceNoTax * 100) / 100
                              rowData.contractSumNoTax = (rowData.qty ? rowData.qty : 0) * (rowData.price ? rowData.price : 0) / (1 + (Number(val) ? Number(val) : 0) / 100)
                              rowData.unit = data.unit
                              rowData.workName = data.workName
                              rowData.remarks = data.remarks
                              rowData.workNo = data.workNo
                            }
                            return rowData;
                          })
                          this.tableOne.btnCallbackFn.setState({
                            data: tableOneData
                          })
                        })
                      },
                      placeholder: '请选择'
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
                  // {
                  //   table: {
                  //     title: '变更后数量',
                  //     dataIndex: 'changeQty',
                  //     key: 'changeQty',
                  //     width: 120,
                  //     align: 'center',
                  //   }
                  // },
                  // {
                  //   table: {
                  //     title: '变更后含税单价',
                  //     dataIndex: 'changePrice',
                  //     key: 'changePrice',
                  //     width: 120,
                  //     align: 'center',
                  //   }
                  // },
                  // {
                  //   table: {
                  //     title: '变更后不含税单价',
                  //     dataIndex: 'changePriceNoTax',
                  //     key: 'changePriceNoTax',
                  //     width: 150,
                  //     align: 'center',
                  //   }
                  // },
                  // {
                  //   table: {
                  //     title: '变更后含税金额',
                  //     dataIndex: 'changeContractSum',
                  //     key: 'changeContractSum',
                  //     width: 170,
                  //     align: 'center',
                  //   }
                  // },
                  // {
                  //   table: {
                  //     title: '变更后不含税金额',
                  //     dataIndex: 'changeContractSumNoTax',
                  //     key: 'changeContractSumNoTax',
                  //     width: 170,
                  //     align: 'center',
                  //   }
                  // },
                  {
                    table: {
                      title: '备注',
                      dataIndex: 'remarks',
                      key: 'remarks',
                      width: 150,
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
                      if (obj.clickCb.rowInfo.name === 'detail') {
                        return false
                      } else {
                        return true
                      }
                    },
                    isInForm: false,
                    table: {
                      showType: 'tile',
                      width: 120,
                      title: "操作",
                      key: "action",//操作列名称
                      fixed: 'right',//固定到右边
                      align: 'center',
                      render: (data, rowData) => {
                        if (rowData.conRevDetailId === this.state.lineEdit) {
                          return <a>保存</a>;
                        } else {
                          return <a>编辑</a>;
                        }
                      },
                      onClick: (obj) => {
                        if (this.state.lineEdit === obj.rowData.conRevDetailId) {
                          Toast.loading('loading...');
                          setTimeout(() => {
                            obj.btnCallbackFn.getEditedRowData().then((data) => {
                              if (!data.workNo) {//
                                Msg.warn('请填写清单编号！')
                                return
                              } else if (!data.workName) {//
                                Msg.warn('请填写清单名称！')
                                return
                              } else if (!data.taxRate) {
                                Msg.warn('请填写税率！')
                                return
                              } else if (!data.price) {
                                Msg.warn('请填写含税合同单价！')
                                return
                              }
                              let body = {
                                ...obj.rowData,
                                ...data,
                              };
                              body.contractReviewId = obj.props.tableFns.qnnForm.form.getFieldValue('contractReviewId')
                              this.props.myFetch(!isNaN(obj.rowData.conRevDetailId) ? 'addZxCtFsContractReviewDetail' : 'updateZxCtFsContractReviewDetail', body).then(
                                ({ success, message }) => {
                                  if (success) {
                                    Msg.success(message);
                                    this.setState({
                                      lineEdit: ''
                                    })
                                    obj.btnCallbackFn.refresh();
                                    this.table.refresh()
                                    Toast.hide()
                                  } else {
                                    Toast.hide()
                                    Msg.error(message);
                                  }
                                }
                              );
                            })
                          }, 300)
                        } else {
                          let data = this.tableOne.state ? this.tableOne.state.data : []
                          for (var i = 0; i < data.length; i++) {
                            if (data[i].conRevDetailId === this.state.lineEdit) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                          }
                          this.setState({
                            lineEdit: obj.rowData.conRevDetailId
                          })
                        }
                      }
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