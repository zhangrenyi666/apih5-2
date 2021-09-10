import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import ConstructionSchemeForm from './form';
import DeetailForm from './deetailForm';
import Operation from './operation';
const config = {
  antd: {
    rowKey: function (row) {
      return row.zxCtSubContractLpriceReviewId
    },
    size: 'small'
  },
  drawerConfig: {
    width: '1000px'
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
      xs: { span: 18 },
      sm: { span: 18 }
    }
  },
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
    }
  }

  render() {
    const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const { lockProjectId } = this.state;
    let jurisdiction = departmentId;
    if (lockProjectId !== 'all' && lockProjectId !== '') {
      jurisdiction = lockProjectId;
    } else {
      if (ext1 === '1' || ext1 === '2') {
        jurisdiction = companyId;
      } else if (ext1 === '3' || ext1 === '4') {
        jurisdiction = projectId;
      } else { }
    }
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
            apiName: 'getZxCtSubContractLpriceReviewList',
            otherParams: {
              projectId: jurisdiction,
            }
          }}
          method={{
            disabledFunDel: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length > 0) {
                for (let m = 0; m < data.length; m++) {
                  if (data[m].apih5FlowStatus === '1' || data[m].apih5FlowStatus === '2' || data[m].zxCtSubContractLpriceReviewId.length > 32) {
                    return true;
                  }
                }
                return false
              } else {
                return true;
              }
            },
            disabledFunDetail: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                return false;
              } else {
                return true;
              }
            },
            disabledFunJindu: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              //前端用列表页面数据主键长度判断，大于32的“详细”按钮置灰，小等于32位的可以点击“详细”
              if (data.length === 1 && (data[0].apih5FlowStatus !== '-1' && data[0].zxCtSubContractLpriceReviewId.length <= 32)) {
                return false;
              } else {
                return true;
              }
            },
          }}
          componentsKey={{
            ConstructionScheme: (obj) => {
              let arry = obj.selectedRows[0];
              arry = {};
              return <ConstructionSchemeForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={arry} />
            },
            DeetailFormChange: (obj) => {
              return <DeetailForm {...obj} flowData={obj.selectedRows[0]} />
            },
            OperationJindu: (props) => {
              return <Operation apiName={'openFlowByReport'} detailApiName={'getZxCtSubContractLpriceReviewDetail'} flowId={'ZxCtSubContractLprice'} {...props} />
          },
          }}

          // actionBtns={[
          //   {
          //     drawerTitle: "详情",
          //     name: 'Component',
          //     type: 'primary',
          //     label: '详情',
          //     disabled: 'bind:disabledFunDetail',
          //     Component: 'DeetailFormChange'
          //   },
          //   {
          //     name: 'Component',
          //     type: 'primary',
          //     label: '进度查询',
          //     drawerTitle: '进度查询',
          //     disabled: 'bind:disabledFunJindu',
          //     Component: 'OperationJindu'
          //   },
          // ]}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "SubcontractingPriceLimit"
              }
            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxCtSubContractLpriceReviewId',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '项目名称',
                dataIndex: 'projectName',
                key: 'projectName',
                width: 280,
                filter: true,
                fieldsConfig: {
                  field: 'projectName',
                  type: 'string',
                }
              },
              isInForm: false,
              form: {
                field: 'projectId',
                type: 'select',
                label: '项目名称',
                required: true,
                optionConfig: {
                  label: 'orgName',
                  value: 'orgID'
                },
                fetchConfig: {
                  apiName: 'getZxCtContractListByStatus',
                  otherParams: {
                    orgID: jurisdiction
                  }
                }
              }
            },
            {
              table: {
                title: '标题',
                dataIndex: 'subTitle',
                key: 'title',
                width: 280,
                filter: true
              },
              isInForm: false,
              form: {
                type: 'string',

              }
            },
            {
              table: {
                title: '拟稿人',
                dataIndex: 'drafterName',
                key: 'drafterName'
              },
              isInForm: false
            },
            {
              table: {
                title: '业务日期',
                dataIndex: 'businessDate',
                key: 'businessDate',
                format: 'YYYY-MM-DD'
              },
              isInForm: false
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
              },
              isInForm: false,
            }
          ]}
        />
      </div>
    );
  }
}

export default index;