import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
const config = {
  antd: {
    rowKey: 'zxSfEduId',
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
            apiName: 'getZxSfEduList',
            otherParams: {
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
                    apiName: 'addZxSfEdu'
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
                    apiName: 'updateZxSfEdu'
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
                apiName: 'batchDeleteUpdateZxSfEdu',
              },
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfEduId',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'companyId',
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
                dataIndex: 'orgID',
                key: 'orgID',
                filter: true,
                width: 250,
                tooltip: 20,
                type: 'select',
                fieldsConfig: {
                  type: 'select',
                  field: 'orgID2',
                  optionConfig: {
                    label: 'departmentName',
                    value: 'departmentId',
                    linkageFields: {
                      companyId: 'companyId',
                      companyName: 'companyName',
                      orgName: 'departmentName'
                    }
                  },
                  fetchConfig: {
                    apiName: 'getSysCompanyProject',
                    otherParams: {departmentId:departmentId}
                  },
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
                    companyId: 'companyId',
                    companyName: 'companyName',
                    orgName: 'departmentName'
                  }
                },
                fetchConfig: {
                  apiName: 'getSysCompanyProject',
                  otherParams: {departmentId:departmentId}
                },
                spanForm: 12,
              }
            },
            {
              table: {
                title: '培训名称',
                dataIndex: 'tranName',
                key: 'tranName',
                onClick:'detail'
              },
              form: {
                type: 'string',
                field: 'tranName',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '主办单位',
                dataIndex: 'mainUnit',
                key: 'mainUnit'
              },
              form: {
                type: 'string',
                field: 'mainUnit',
                spanForm: 12
              }
            },
            {
              table: {
                title: '办班地点',
                dataIndex: 'address',
                key: 'address'
              },
              form: {
                type: 'string',
                field: 'address',
                spanForm: 12
              }
            },
            {
              table: {
                title: '采用教材',
                dataIndex: 'materials',
                key: 'materials'
              },
              form: {
                type: 'string',
                field: 'materials',
                spanForm: 12
              }
            },
            {
              table: {
                title: '教员',
                dataIndex: 'teacher',
                key: 'teacher'
              },
              form: {
                type: 'string',
                field: 'teacher',
                spanForm: 12
              }
            },
            {
              table: {
                title: '日期',
                dataIndex: 'bizDate',
                key: 'bizDate',
                filter: true,
                format: 'YYYY-MM-DD'
              },
              form: {
                type: 'date',
                field: 'bizDate',
                spanForm: 12,
                required: true,
                initialValue: () => {
                  return moment(new Date()).format('YYYY-MM-DD')
              },
              }
            },
            {
              table: {
                title: '学时',
                dataIndex: 'tranTime',
                key: 'tranTime'
              },
              form: {
                type: 'string',
                field: 'tranTime',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '培训内容',
                dataIndex: 'tranContext',
                key: 'tranContext',
                width: 160,
                tooltip: 18,
              },
              form: {
                type: 'textarea',
                required: true,
                field: 'tranContext',
                formItemLayoutForm: {
                  labelCol: {
                    xs: { span: 24 },
                    sm: { span: 3 }
                  },
                  wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 21 }
                  }
                }
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 160,
                tooltip: 18,
              },
              form: {
                type: 'textarea',
                // required: true,
                field: 'remarks',
                formItemLayoutForm: {
                  labelCol: {
                    xs: { span: 24 },
                    sm: { span: 3 }
                  },
                  wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 21 }
                  }
                }
              }
            },
            {
              isInTable: false,
              form: {
                type: 'qnnTable',
                field: 'zxSfEduItemList',
                incToForm: true,
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxSfEduItemId',
                    size: 'small'
                  },
                  ...configItem,
                  tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'zxSfEduItemId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>姓名<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "<div>工种<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'empType',
                        key: 'empType',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'empType',
                        required: true
                      }
                    },
                    {
                      table: {
                        title: "<div>身份证号<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'idCard',
                        width: 200,
                        key: 'idCard',
                        tdEdit: true,
                      },
                      form: {
                        type: 'identity',
                        field: 'idCard',
                        required: true
                      }
                    },
                    {
                      table: {
                        title: "<div>教育类型<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'tranContext',
                        key: 'tranContext',
                        tdEdit: true,
                        type: 'select'
                      },
                      form: {
                        type: 'select',
                        field: 'tranContext',
                        required: true,
                        allowClear: false,
                        optionConfig: {
                          label: 'itemName',
                          value: 'itemId'
                        },
                        fetchConfig: {
                          apiName: "getBaseCodeSelect",
                          otherParams: {
                            itemId: 'jiaoYuLeiXing'
                          }
                        },
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
                formItemLayout: {
                  labelCol: {
                    xs: { span: 8 },
                    sm: { span: 3 }
                  },
                  wrapperCol: {
                    xs: { span: 16 },
                    sm: { span: 21 }
                  }
                },
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;