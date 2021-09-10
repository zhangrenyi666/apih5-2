import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import TabTwoList from './TabTwo';
// import CurrentBuildForm from './form';
import CurrentBuildFormForHQ from './formHQ';
import CurrentBuildFormForPart from './formPart';
import Operation from './operation'
import DetailFormForHQ from './detailHQ'
// import DetailForm from './detail';
import DetailFormForPart from './detailPart';
import { Modal, message as Msg, } from 'antd';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'id',
    size: 'small'
  },
  drawerConfig: {
    width: '80%'
  },
  paginationConfig: {
    position: 'bottom'
  },
  firstRowIsSearch: false,
  isShowRowSelect: true,
  formItemLayout: {
    labelCol: {
      xs: { span: 5 },
      sm: { span: 5 }
    },
    wrapperCol: {
      xs: { span: 18 },
      sm: { span: 18 }
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
      contractorType: null
    }
  }
  render() {
    const { departmentId, contractorType } = this.state;
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxCtContrDqjzList',
            otherParams: { orgID: departmentId }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
              obj.btnCallbackFn.clearSelectedRows()
            }
          }}
          componentsKey={{
            CurrentBuildForm: (obj) => {
              // if (contractorType === '报审') {
              //   return <CurrentBuildForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              // } else if (contractorType === '2' || contractorType === '3') {
              //   return <CurrentBuildFormForHQ {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              // } else if(contractorType !== '报审' && contractorType !== '2' && contractorType !== '3') {
              //   return <CurrentBuildFormForPart {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              // }
              if (contractorType === '2' || contractorType === '3') {
                return <CurrentBuildFormForHQ {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              } else if (contractorType !== '报审' && contractorType !== '2' && contractorType !== '3') {
                return <CurrentBuildFormForPart {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              }
            },
            //进度查询
            Operation: (obj) => {
              return <Operation
                {...this.props}
                btnCallbackFn={obj.btnCallbackFn}
                flowData={{ flow: contractorType === '报审' ? 'zxCtContrDqjz' : contractorType === '2' || contractorType === '3' ? 'zbzxCtContrCsdqjz' : 'zxCtContrCsgsdqjz' }}
                apiName={'openFlowByReport'}
              />
            },
            //详细
            DetailForm: (obj) => {
              // if (contractorType === '报审') {
              //   return <DetailForm
              //     {...this.props}
              //     flowData={obj.qnnTableInstance.getSelectedRows()[0]}
              //   />
              // } else if (contractorType === '2' || contractorType === '3') {
              //   return <DetailFormForHQ
              //     {...this.props}
              //     flowData={obj.qnnTableInstance.getSelectedRows()[0]}
              //   />

              // } else {
              //   return <DetailFormForPart
              //     {...this.props}
              //     flowData={obj.qnnTableInstance.getSelectedRows()[0]}
              //   />
              // }
              if (contractorType === '2' || contractorType === '3') {
                return <DetailFormForHQ isInQnnTable={obj.isInQnnTable}
                  {...this.props}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />

              } else {
                return <DetailFormForPart isInQnnTable={obj.isInQnnTable}
                  {...this.props}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              }
            },
          }}
          method={{
            hideForAdd: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('id')) {
                return true;
              } else {
                return false;
              }
            },
            hideForEdit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "0") {
                return true;
              } else {
                return false;
              }
            },
            cbForAdd: (obj) => {
              if (obj.response.success) {
                this.table.setDeawerValues({ ...obj.response.data });
                this.table.setTabsIndex('1');
              }
            },
            cbForEdit: (obj) => {
              if (obj.response.success) {
                this.table.setTabsIndex('1');
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
            cbForDelete: (obj) => {
              confirm({
                content: '确定删除吗?',
                onOk: () => {
                  let data = obj.btnCallbackFn.getSelectedRows();
                  this.props.myFetch('batchDeleteUpdateZxCtContrDqjz', data)
                    .then(({ success, message }) => {
                      if (success) {
                        obj.btnCallbackFn.refresh()
                      } else {
                        Msg.error(message)
                      }
                    })
                }
              })
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
            //flowID筛选
            selectForForm: (obj) => {
              // if (obj.selectedRows[0].contractAudit === '报审') {
              //   this.setState({ contractorType: '报审' })
              // } else {
              //   this.props.myFetch('getSysProjectDetailKey', { departmentId: obj.selectedRows[0].orgID })
              //     .then(({ data, success }) => {
              //       if (success) {
              //         this.setState({ contractorType: data.contractorType })
              //       }
              //     })
              // }
              this.props.myFetch('getSysProjectDetailKey', { departmentId: obj.selectedRows[0].orgID })
                .then(({ data, success }) => {
                  if (success) {
                    this.setState({ contractorType: data.contractorType })
                  }
                })
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
            //详细按钮禁用筛选
            disabledFunDetail: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                return false;
              } else {
                return true;
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
                tableField: "CurrentBuildContract"
              }
            }
          }}
          formConfig={[
            {
              table: {
                title: '项目名称',
                dataIndex: 'orgName',
                key: 'orgName',
                fixed: 'left',
                width: 200,
                onClick: 'detail',
                filter: true,
                fieldsConfig: {
                  field: 'orgID',
                  type: 'select',
                  optionConfig: {
                    label: 'orgName',
                    value: 'orgID',
                  },
                  showSearch: true,
                  fetchConfig: {
                    apiName: 'getZxCtContrJzBaseDropDownList',
                    otherParams: {
                      type: '2',
                      orgID: departmentId
                    }
                  },
                },
              },
            },
            {
              table: {
                title: '建立时间',
                dataIndex: 'buildTime',
                key: 'buildTime',
                format: 'YYYY-MM-DD',
                width: 120
              },
            },
            {
              table: {
                title: '发起人',
                dataIndex: 'reporter',
                key: 'reporter',
                width: 100,
              },
            },
            {
              table: {
                title: '合同状态',
                dataIndex: 'contractAudit',
                key: 'contractAudit',
                width: 100,
              }
            },
            {
              table: {
                title: '初始预计总收入',
                dataIndex: 'csBudgetAmt',
                key: 'csBudgetAmt',
                width: 120,
              },
            },
            {
              table: {
                title: '当前预计总收入',
                dataIndex: 'bhBudgetAmt',
                key: 'bhBudgetAmt',
                width: 120,
              },
            },
            {
              table: {
                title: '初始预计总成本',
                dataIndex: 'bhBudgetCost',
                key: 'bhBudgetCost',
                width: 120,
              },
            },
            {
              table: {
                title: '当前预计总成本',
                dataIndex: 'sgBudgetCost',
                key: 'sgBudgetCost',
                width: 120,
              },
            },
            {
              table: {
                title: '评审状态',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                width: 90,
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
                    return '未审核'
                  }
                }
              },
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
                    field: 'id',
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
                    label: '项目名称',
                    field: "orgName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '项目名称',
                    field: 'orgID',
                    required: true,
                    type: 'select',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    fetchConfig: {
                      apiName: 'getZxCtContrJzBaseDropDownList',
                      otherParams: {
                        type: '2',
                        orgID: departmentId
                      }
                    },
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        orgName: 'orgName',
                        comID: 'comID',
                        comName: 'comName'
                      }
                    },
                    editDisabled: true,
                    placeholder: '请选择',
                    span: 12,
                  },
                  {
                    label: '建立时间',
                    field: 'buildTime',
                    type: 'date',
                    initialValue: new Date(),
                    placeholder: '请选择',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '发起人',
                    field: 'reporter',
                    type: 'string',
                    placeholder: '请输入',
                    span: 12,
                    required: true,
                    initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                  },
                  {
                    label: '合同状态',
                    field: 'contractAudit',
                    required: true,
                    type: 'select',
                    optionConfig: {
                      label: 'contractAudit',
                      value: 'contractAudit'
                    },
                    optionData: [
                      { contractAudit: '报备' },
                      { contractAudit: '报审' },
                    ],
                    span: 12,
                    placeholder: '请选择',
                  },
                  {
                    label: '初始预计总收入',
                    field: 'csBudgetAmt',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    label: '当前预计总收入',
                    field: 'bhBudgetAmt',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    label: '初始预计总成本',
                    field: 'bhBudgetCost',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    label: '当前预计总成本',
                    field: 'sgBudgetCost',
                    type: 'number',
                    disabled: true,
                    span: 12,
                    hide: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'add') {
                        return true;
                      }
                    }
                  },
                  {
                    field: 'remarks',
                    label: '说明',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                  },
                ]
              }
            },
            {
              name: "diyComponent",
              field: "TabTwoList",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("id"))
              },
              title: "清单编辑",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                params.clickName = obj.clickCb.rowInfo.name
                params.id = rowData?.id //主键
                params.orgID = rowData?.orgID
                params.TabOneRef = this.table
                return <TabTwoList {...params} btnCallbackFn={obj.btnCallbackFn} />;
              },
            },
          ]}
        />
      </div>
    );
  }
}

export default index;