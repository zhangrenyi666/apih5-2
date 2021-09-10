import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
  antd: {
    rowKey: 'zxSfOtherAddFileId',
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
      selectDepartmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
      isLocked:((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? true : false) : false,
      companyName: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectName : curCompany?.companyName) : curCompany?.projectName,
    }
  }
  render() {
    const {  ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany
    const { selectDepartmentId, isLocked,companyName } = this.state;
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
            apiName: 'getZxSfOtherAddFileList',
            otherParams: {
              orgID: selectDepartmentId,
              category: '3',
            }
          }}
          method={{
            hideAdd: () => {
              console.log(isLocked);
              if (ext1 === '3' || ext1 === '4' || isLocked) return false
              return true
            },
            hideEdit: () => {
              if (ext1 === '3' || ext1 === '4' || isLocked) return false
              return true
            },
            onClickFunEdit: (obj) => {
              obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
              this.table.clearSelectedRows();
            },
            onClickFunEditSave: (obj) => {
              obj.btnCallbackFn.clearSelectedRows();
              obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
              this.props.myFetch('updateZxSfOtherAddFile', obj._formData).then(
                ({ data, success, message }) => {
                  if (success) {
                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                    Msg.success(message);
                    obj.btnCallbackFn.refresh();
                    obj.btnCallbackFn.closeDrawer();
                  } else {
                    obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                    Msg.error(message);
                  }
                }
              );
            },
            disabledFunDel: (obj) => {
              let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
              if (SelectedRowsData.length === 0) {
                return true
              }
            },
          }}
          // actionBtns={[
          //   {
          //     name: 'add',
          //     icon: 'plus',
          //     type: 'primary',
          //     label: '新增',
          //     hide: 'bind:hideAdd',
          //     formBtns: [
          //       {
          //         name: 'cancel',
          //         type: 'dashed',
          //         label: '取消',
          //       },
          //       {
          //         name: 'submit',
          //         type: 'primary',
          //         label: '保存',
          //         fetchConfig: {
          //           apiName: 'addZxSfOtherAddFile',
          //           otherParams: { category: '3' }
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: "edit",
          //     label: "修改",
          //     field: "edit",
          //     type: "primary",
          //     hide: 'bind:hideEdit',
          //     onClick: 'bind:onClickFunEdit',
          //     formBtns: [
          //       {
          //         name: 'cancel',
          //         type: 'dashed',
          //         label: '取消',
          //       },
          //       {
          //         name: 'diySubmit',
          //         field: 'diySubmit',
          //         type: 'primary',
          //         label: '保存',
          //         onClick: 'bind:onClickFunEditSave'
          //       }
          //     ]
          //   },
          //   {
          //     name: "del",
          //     label: "删除",
          //     icon: "delete",
          //     field: "del",
          //     type: "danger",
          //     disabled: 'bind:disabledFunDel',
          //     fetchConfig: {
          //       apiName: "batchDeleteUpdateZxSfOtherAddFile"
          //     }
          //   },
          // ]}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "ContingencyPlan"
              }
            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxSfOtherAddFileId',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'orgID',
                type: 'string',
                hide: true,
                initialValue: selectDepartmentId
              }
            },
            {
              table: {
                title: '名称',
                dataIndex: 'title',
                key: 'title',
                align: "center",
                width: 150,
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                onClick: 'detail',
                tooltip: 15,
              },
              form: {
                field: 'title',
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
                required: true,
              }
            },
            {
              table: {
                title: '机构名称',
                dataIndex: 'orgName',
                key: 'orgName',
                align: "center",
                width: 160,
                filter: true,
                fieldsConfig: { type: 'string' },
                fixed: 'left',
                tooltip: 15
              },
              form: {
                field: 'orgName',
                type: 'string',
                spanForm: 12,
                initialValue: companyName,
                addDisabled: true,
                editDisabled: true
              }
            },
            {
              table: {
                title: '填报人',
                dataIndex: 'creator',
                key: 'creator',
                align: "center",
                width: 120,
              },
              form: {
                field: 'creator',
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
                initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
              }
            },
            {
              table: {
                title: '填报日期',
                dataIndex: 'createDate',
                key: 'createDate',
                align: "center",
                width: 150,
                format: 'YYYY-MM-DD',
              },
              form: {
                field: 'createDate',
                type: 'date',
                placeholder: '请选择',
                spanForm: 12,
                initialValue: new Date()
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'notes',
                key: 'notes',
                align: "center",
                width: 120,
              },
              form: {
                field: 'notes',
                type: 'textarea',
                placeholder: '请输入',
                spanForm: 12,
                autoSize: {
                  minRows: 1,
                  maxRows: 3
                },
              }
            },
            {
              isInTable: false,
              form: {
                label: '附件',
                spanForm: 12,
                field: 'fileList',
                type: 'files',
                fetchConfig: {
                  apiName: 'upload'
                },
                formItemLayout: {
                  labelCol: {
                    xs: { span: 8 },
                    sm: { span: 8 }
                  },
                  wrapperCol: {
                    xs: { span: 12 },
                    sm: { span: 12 }
                  }
                },
              }
            },
          ]}
        />
      </div>
    );
  }
}

export default index;