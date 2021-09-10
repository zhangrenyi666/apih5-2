import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
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
  firstRowIsSearch: false,
  isShowRowSelect: true
};

class index extends Component {
  constructor(props) {
    super(props)
    this.state = {
    }
  }
  render() {
    const { projectId, projectName, companyId, companyName,departmentName,departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
              category: '2',
              orgID:departmentId
            }
          }}
          actionBtns={[
            {
              name: 'add',
              icon: 'plus',
              type: 'primary',
              label: '新增',
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
                  field: 'addsubmit',
                  fetchConfig: {
                    apiName: 'addZxSfOtherAddFile'
                  }
                }
              ]
            },
            {
              name: 'edit',
              icon: 'edit',
              type: 'primary',
              label: '修改',
              onClick: (obj) => {
                this.table.clearSelectedRows();
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
                    apiName: 'updateZxSfOtherAddFile'
                  }
                }
              ]
            },
            {
              name: 'del',
              icon: 'delete',
              type: 'danger',
              label: '删除',
              field: "del",
              fetchConfig: {
                apiName: 'batchDeleteUpdateZxSfOtherAddFile',
              },
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfOtherAddFileId',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'category',
                initialValue:'2',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'companyId',
                initialValue: companyId,
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'orgID',
                initialValue: departmentId,
                hide: true
              }
            },
            {
              table: {
                title: '名称',
                dataIndex: 'title',
                key: 'title',
                filter: true,
                width: 150,
                onClick: 'detail',
                tooltip: 15
              },
              form: {
                type: 'string',
                field: 'title',
                required: true,
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 3 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 21 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              isInTable:false,
              form: {
                type: 'string',
                field: 'companyName',
                hide: true,
                initialValue: companyName,
                required: true,
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 3 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 21 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '机构名称',
                dataIndex: 'orgName',
                key: 'orgName',
                filter: true,
                width: 150,
                tooltip: 15,
                fieldsConfig: {
                  type: 'string',
                  field: 'orgName',
                }
              },
              form: {
                type: 'string',
                field: 'orgName',
                addDisabled:true,
                editDisabled: true,
                initialValue: () => {
                  return departmentName
                },
                required: true,
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 3 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 21 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '填报人',
                dataIndex: 'creator',
                key: 'creator'
              },
              form: {
                type: 'string',
                field: 'creator',
                // addDisabled: true,
                // editDisabled: true,
                initialValue: () => {
                  return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                },
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 3 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 21 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '填报时间',
                dataIndex: 'createDate',
                key: 'createDate',
                format: 'YYYY-MM-DD'
              },
              form: {
                type: 'date',
                // addDisabled: true,
                // editDisabled: true,
                field: 'createDate',
                initialValue: () => {
                  return moment(new Date()).format('YYYY-MM-DD')
                },
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 3 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 21 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
              },
              form: {
                type: 'textarea',
                field: 'remarks',
                autoSize: {
                  minRows: 1,
                  maxRows: 3
                },
                formItemLayout: {
                  labelCol: {
                    xs: { span: 3 },
                    sm: { span: 3 }
                  },
                  wrapperCol: {
                    xs: { span: 21 },
                    sm: { span: 21 }
                  }
                }
              }
            },
            {
              isInTable: false,
              form: {
                label: '附件',
                field: 'fileList',
                type: "files",
                initialValue: [],
                fetchConfig: {
                  apiName: "upload"
                },
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 3 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 21 },
                    sm: { span: 18 }
                  }
                }
              }
            },
          ]}
        />
      </div>
    );
  }
}

export default index;