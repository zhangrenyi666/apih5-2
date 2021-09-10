import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
  antd: {
    rowKey: 'zxSfOtherAddFileId',
    size: 'small'
  },
  drawerConfig: {
    width: '1200px'
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
  render() {
    const {departmentId, departmentName} = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany
    const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo
    console.log(ext1);
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
              orgID: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.departmentId,
              category : '3',
              orgName: ''
            }
          }}
          actionBtns={[
            {
              name: 'add',
              icon: 'plus',
              type: 'primary',
              label: '新增',
              hide: () => {
                if (ext1 === '1' || ext1 === '2') return true
              },
              formBtns: [
                {
                  name: 'cancel', 
                  type: 'dashed',
                  label: '取消',
                },
                {
                  name: 'submit',
                  type: 'primary',
                  label: '保存',
                  fetchConfig: {
                    apiName: 'addZxSfOtherAddFile',
                    otherParams:{ category : '3' }
                  }
                }
              ]
            },
            {
              name: "edit",
              label: "修改",
              field: "edit",
              type: "primary",
              hide: () => {
                if (ext1 === '1' || ext1 === '2') return true
              },
              onClick: (obj) => {
                obj.btnCallbackFn.setBtnsDisabled('remove', 'diySubmit');
                this.table.clearSelectedRows();
              },
              formBtns: [
                {
                  name: 'cancel', //关闭右边抽屉
                  type: 'dashed',//类型  默认 primary
                  label: '取消',
                },
                {
                  name: 'diySubmit',
                  field: 'diySubmit',
                  type: 'primary',
                  label: '保存',
                  onClick: (obj) => {
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
                  }
                }
              ]
            },
            {
              name: "del",
              label: "删除",
              icon: "delete",
              field: "del",
              type: "danger",
              disabled: (obj) => {
                let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                if (SelectedRowsData.length === 0) {
                  return true
                }
              },
              fetchConfig: {
                apiName: "batchDeleteUpdateZxSfOtherAddFile"
              }
            },
          ]}
          formConfig={[
            {
              isInTable:false,
              form:{
                field:'zxSfOtherAddFileId',
                type:'string',
                hide:true,
              }
            },
            {
              isInTable:false,
              form:{
                field:'orgID',
                type:'string',
                hide:true,
                initialValue:departmentId
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
                spanForm:12,
                required:true,
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
                spanForm:12,
                initialValue:departmentName,
                addDisabled:true,
                editDisabled:true
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
                spanForm:12,
                initialValue:this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
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
                spanForm:12,
                initialValue:new Date()
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
                label:'附件',
                spanForm:12,
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