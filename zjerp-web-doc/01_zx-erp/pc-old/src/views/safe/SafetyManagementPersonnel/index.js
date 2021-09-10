import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
  antd: {
    rowKey: 'zxSfProjectEmpMainId',
    size: 'small'
  },
  drawerConfig: {
    width: '1200px'
  },
  paginationConfig: {
    position: 'bottom'
  },
  firstRowIsSearch: false,
  isShowRowSelect: true,
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 6 }
    },
    wrapperCol: {
      xs: { span: 16 },
      sm: { span: 18 }
    }
  },
};
const configItem = {
  drawerConfig: {
    width: '1100px'
  },
  paginationConfig: false,
  actionBtnsPosition: "top",
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
    const { projectId, projectName, companyId, companyName, departmentName, departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
            apiName: 'getZxSfProjectEmpMainList',
            otherParams: {
              orgID2: departmentId
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
                    apiName: 'addZxSfProjectEmpMain'
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
                    apiName: 'updateZxSfProjectEmpMain'
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
              fetchConfig: {//ajax配置
                apiName: 'batchDeleteUpdateZxSfProjectEmpMain',
              },
            }
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfProjectEmpMainId',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'orgName',
                hide: true
              }
            },
            {
              table: {
                title: '机构名称',
                dataIndex: 'orgName',
                key: 'orgName',
                width: '65%',
                tooltip: 15,
                filter: true,
                onClick: 'detail',
                render: (data, rowData) => {
                  let color = '';
                  if (rowData.colorflag === '0') {
                    color = '';
                  } else if (rowData.colorflag === '1') {
                    color = 'red';
                  }
                  return <a style={{ color: color }}>{data}</a>
                },
                fieldsConfig: {
                  type: 'select',
                  field: 'orgID',
                  optionConfig: {
                    label: 'departmentName',
                    value: 'departmentId',
                  },
                  fetchConfig: {
                    apiName: 'getSysCompanyProject',
                    otherParams: {
                      departmentId: departmentId
                    }
                  }
                }
              },
              form: {
                type: 'select',
                field: 'orgID',
                required: true,
                showSearch: true,
                optionConfig: {
                  label: 'departmentName',
                  value: 'departmentId',
                  linkageFields: {
                    orgName: 'departmentName'
                  }
                },
                fetchConfig: {
                  apiName: 'getSysCompanyProject',
                  otherParams: {
                    departmentId: departmentId
                  }
                },
                spanForm: 12,
              }
            },
            {
              table: {
                title: '编制人',
                dataIndex: 'reporter',
                key: 'reporter',
                width: '35%',
                render: (data, rowData) => {
                  let color = '';
                  if (rowData.colorflag === '0') {
                    color = '';
                  } else if (rowData.colorflag === '1') {
                    color = 'red';
                  }
                  return <div style={{ color: color }}>{data}</div>
                }
              },
              form: {
                type: 'string',
                field: 'reporter',
                spanForm: 12
              }
            },
            {
              isInTable: false,
              form: {
                type: 'qnnTable',
                field: 'zxSfProjectEmployeeList',
                incToForm: true,
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxSfProjectEmployeeId',
                    size: 'small'
                  },
                  ...configItem,
                  tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'zxSfProjectEmployeeId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>姓名<span style='color: #ff4d4f'>*</span></div>",
                        width: 160,
                        dataIndex: 'name',
                        key: 'name',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'name',
                        required: true
                      }
                    },
                    {
                      table: {
                        title: "<div>性别<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'sex',
                        width: 160,
                        key: 'sex',
                        type: 'select',
                        tdEdit: true,
                      },
                      form: {
                        type: 'select',
                        field: 'sex',
                        required: true,
                        optionData: [{ label: '女', value: '1' }, { label: '男', value: '0' }],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "<div>年龄<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'age',
                        width: 160,
                        key: 'age',
                        tdEdit: true,
                      },
                      form: {
                        type: 'number',
                        field: 'age',
                        required: true,
                      }
                    },
                    {
                      table: {
                        title: "<div>学历<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'eduLevel',
                        width: 160,
                        key: 'eduLevel',
                        type: 'select',
                        tdEdit: true,
                      },
                      form: {
                        type: 'select',
                        field: 'eduLevel',
                        required: true,
                        optionData: [
                          { label: '博士', value: '1' },
                          { label: '本科', value: '0' },
                          { label: '初中', value: '2' },
                          { label: '大专', value: '3' },
                          { label: '高中', value: '4' },
                          { label: '硕士', value: '5' },
                          { label: '小学', value: '6' },
                          { label: '中专', value: '7' }
                        ],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "<div>职称<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'title',
                        width: 160,
                        key: 'title',
                        type: 'select',
                        tdEdit: true
                      },
                      form: {
                        type: 'select',
                        field: 'title',
                        required: true,
                        optionData: [
                          { label: '中级', value: '1' },
                          { label: '初级', value: '0' },
                          { label: '高级', value: '2' },
                        ],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "是否为注安师",
                        dataIndex: 'isExpert',
                        width: 160,
                        key: 'isExpert',
                        type: 'select',
                        tdEdit: true
                      },
                      form: {
                        type: 'select',
                        field: 'isExpert',
                        optionData: [
                          { label: '是', value: '1' },
                          { label: '否', value: '0' }
                        ],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "注安师证号",
                        dataIndex: 'cardNo',
                        width: 160,
                        key: 'cardNo',
                        tdEdit: true
                      },
                      form: {
                        type: 'string',
                        field: 'cardNo'
                      }
                    },
                    {
                      table: {
                        title: "<div>从事安全工作累计时间(年)<span style='color: #ff4d4f'>*</span></div>",
                        width: 190,
                        dataIndex: 'workAge',
                        key: 'workAge',
                        tdEdit: true
                      },
                      form: {
                        type: 'number',
                        required: true,
                        field: 'workAge',
                      }
                    },
                    {
                      table: {
                        title: "交通部安全证书编号",
                        width: 160,
                        dataIndex: 'safeCardNo',
                        key: 'safeCardNo',
                        tdEdit: true
                      },
                      form: {
                        type: 'string',
                        field: 'safeCardNo'
                      }
                    },
                    {
                      table: {
                        title: "发证日期",
                        width: 160,
                        dataIndex: 'sendCardDate',
                        key: 'sendCardDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'sendCardDate'
                      }
                    },
                    {
                      table: {
                        title: "到期日期",
                        width: 160,
                        dataIndex: 'useEndDate',
                        key: 'useEndDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'useEndDate'
                      }
                    },
                    {
                      table: {
                        title: "建设部安全证书编号",
                        width: 160,
                        dataIndex: 'buildCardNo',
                        key: 'buildCardNo',
                        tdEdit: true
                      },
                      form: {
                        type: 'string',
                        field: 'buildCardNo'
                      }
                    },
                    {
                      table: {
                        title: "发证日期",
                        width: 160,
                        dataIndex: 'buildSendDate',
                        key: 'buildSendDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'buildSendDate'
                      }
                    },
                    {
                      table: {
                        title: "到期日期",
                        width: 160,
                        dataIndex: 'buildEndDate',
                        key: 'buildEndDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'buildEndDate'
                      }
                    },
                    {
                      table: {
                        title: "<div>正在从事安全工作<span style='color: #ff4d4f'>*</span></div>",
                        width: 160,
                        dataIndex: 'isWorking',
                        key: 'isWorking',
                        type: 'select',
                        tdEdit: true,
                      },
                      form: {
                        type: 'select',
                        required: true,
                        field: 'isWorking',
                        optionData: [
                          { label: '是', value: '1' },
                          { label: '否', value: '0' }
                        ],
                        optionConfig: { label: 'label', value: 'value' }
                      }
                    }
                  ],
                  actionBtns: [
                    {
                      name: "addRow",
                      icon: "plus",
                      type: "primary",
                      label: "新增",
                    },
                    {
                      name: "del",
                      icon: 'delete',
                      type: 'danger',
                      label: "删除",
                    }
                  ]
                }
              }
            },
            {
              isInTable: false,
              form: {
                label: '附件',
                field: 'fileList',
                type: 'files',
                fetchConfig: {
                  apiName: 'upload'
                },
                spanForm: 12
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;