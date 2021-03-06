import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import TabTwoList from './TabTwo';
import PrimalBuildForm from './form';
import PrimalBuildFormForPartCom from './formForPartCom';
import Operation from './operation'
import DetailFormForHQ from './detailHQ'
import DetailForm from './detail';
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
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxCtContrCsjzList',
            otherParams: { orgID: departmentId }
          }}
          componentsKey={{
            PrimalBuildForm: (obj) => {
              if (contractorType === '2' || contractorType === '3') {
                return <PrimalBuildFormForPartCom {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              } else {
                return <PrimalBuildForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
              }
            },
            //????????????
            Operation: (obj) => {
              return <Operation
                {...this.props}
                btnCallbackFn={obj.btnCallbackFn}
                flowData={{ flow: contractorType === '2' || contractorType === '3' ? 'zbzxCtContrCsjz' : 'zxCtContrCsjz' }}
                apiName={'openFlowByReport'}
              />
            },
            //??????
            DetailForm: (obj) => {
              if (contractorType === '2' || contractorType === '3') {
                return <DetailFormForHQ isInQnnTable={obj.isInQnnTable}
                  {...this.props}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />

              } else {
                return <DetailForm isInQnnTable={obj.isInQnnTable}
                  {...this.props}
                  flowData={obj.qnnTableInstance.getSelectedRows()[0]}
                />
              }
            },
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
              obj.btnCallbackFn.clearSelectedRows()
            }
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
            //????????????????????????
            disabledFunComponent: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && data[0].workId) {
                return false;
              } else {
                return true;
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
            disabledFunDetail: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
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
            //flowID??????
            selectForForm: (obj) => {
              this.props.myFetch('getSysProjectDetailKey', { departmentId: obj.selectedRows[0].orgID })
                .then(({ data, success }) => {
                  if (success) {
                    this.setState({ contractorType: data.contractorType })
                  }
                })
            }
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "PrimalBuildContract"
              }
            }
          }}
          formConfig={[
            {
              table: {
                title: '????????????',
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
                    apiName: 'getZxCtContrJzBaseList',
                    otherParams: {
                      type: '1',
                      orgID: departmentId
                    }
                  },
                },
              },
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'buildTime',
                key: 'buildTime',
                format: 'YYYY-MM-DD',
                width: 120
              },
            },
            {
              table: {
                title: '?????????',
                dataIndex: 'reporter',
                key: 'reporter',
                width: 100,
              },
            },
            {
              table: {
                title: '?????????????????????',
                dataIndex: 'csBudgetAmt',
                key: 'csBudgetAmt',
                width: 120,
              },
            },
            {
              table: {
                title: '????????????????????????',
                dataIndex: 'bhBudgetAmt',
                key: 'bhBudgetAmt',
                width: 120,
              },
            },
            {
              table: {
                title: '???????????????????????????',
                dataIndex: 'bhBudgetCost',
                key: 'bhBudgetCost',
                width: 120,
              },
            },
            {
              table: {
                title: '???????????????????????????',
                dataIndex: 'sgBudgetCost',
                key: 'sgBudgetCost',
                width: 120,
              },
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                width: 90,
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
                    return '?????????'
                  }
                }
              },
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
                    field: 'id',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: "confirmMethod",
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'workId',
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
                    label: '????????????',
                    field: "orgName",
                    type: 'string',
                    span: 12,
                    disabled: true,
                    addShow: false,
                    editShow: true,
                    detailShow: true,
                  },
                  {
                    label: '????????????',
                    field: 'orgID',
                    required: true,
                    type: 'select',
                    showSearch: true,
                    addShow: true,
                    editShow: false,
                    detailShow: false,
                    fetchConfig: {
                      apiName: 'getZxCtContrJzBaseList',
                      otherParams: {
                        type: '1',
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
                    placeholder: '?????????',
                    span: 12,
                  },
                  {
                    label: '????????????',
                    field: 'buildTime',
                    type: 'date',
                    initialValue: new Date(),
                    placeholder: '?????????',
                    span: 12,
                    required: true,
                  },
                  {
                    label: '?????????',
                    field: 'reporter',
                    type: 'string',
                    placeholder: '?????????',
                    span: 12,
                    required: true,
                    initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                  },
                  {
                    label: '?????????????????????',
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
                    label: '????????????????????????',
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
                    label: '???????????????????????????',
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
                    label: '???????????????????????????',
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
                    label: '??????',
                    type: 'textarea',
                    placeholder: '?????????',
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
              title: "????????????",
              content: obj => {
                let params = { ...this.props }
                let rowData = obj.btnCallbackFn.form.getFieldsValue()
                params.clickName = obj.clickCb.rowInfo.name
                params.id = rowData?.id //??????
                params.orgID = rowData?.orgID
                params.confirmMethod = rowData?.confirmMethod
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