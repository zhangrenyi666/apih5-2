import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
const config = {
  antd: {
    rowKey: 'zxSfHazardRoomId',
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
    const { departmentId,departmentName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
   
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
            apiName: 'getZxSfHazardRoomList'
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
                    apiName: 'addZxSfHazardRoom'
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
                    apiName: 'updateZxSfHazardRoom'
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
                apiName: 'batchDeleteUpdateZxSfHazardRoom',
              },
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfHazardRoomId',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'orgID',
                initialValue:departmentId,
                hide: true
              }
            },
            {
              table: {
                title: '名称',
                dataIndex: 'roomName',
                key: 'roomName',
                filter: true,
                width: 150,
                onClick: 'detail',
                tooltip: 15
              },
              form: {
                type: 'string',
                field: 'roomName',
                required: true,
                spanForm: 12,
                formItemLayoutForm: {
                  labelCol: {
                    xs: { span: 24 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 24 },
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
                width: 150,
                tooltip: 15,
              },
              form: {
                type: 'string',
                field: 'orgName',
                hide: true,
                initialValue: () => {
                  return departmentName
                },
              }
            },
            {
              table: {
                title: '填报日期',
                dataIndex: 'createDate',
                key: 'createDate',
                format: 'YYYY-MM-DD',
              },
              form: {
                type: 'date',
                field: 'createDate',
                hide: true,
                initialValue: () => {
                  return moment(new Date()).format('YYYY-MM-DD')
                },
              }
            },
            {
              table: {
                title: '填报人',
                dataIndex: 'creator',
                key: 'creator',
                width: 100
              },
              form: {
                type: 'string',
                field: 'creator',
                hide: true,
                initialValue: () => {
                  return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                }
              }
            },
            {
              isInTable: false,
              form: {
                type: 'qnnTable',
                field: 'zxSfHazardRoomAttList',
                incToForm: true,
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxSfHazardRoomAttId',
                    size: 'small'
                  },
                  ...configItem,
                  tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'zxSfHazardRoomAttId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "过程(区域)",
                        dataIndex: 'proArea',
                        width: 200,
                        key: 'proArea',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'proArea'
                      }
                    },
                    {
                      table: {
                        title: "行为(活动)或设备=环境",
                        dataIndex: 'doing',
                        width: 200,
                        key: 'doing',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'doing'
                      }
                    },
                    {
                      table: {
                        title: "危险因素",
                        dataIndex: 'riskFactors',
                        width: 200,
                        key: 'riskFactors',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'riskFactors'
                      }
                    },
                    {
                      table: {
                        title: "<div>可能导致的伤害(事故)<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'accident',
                        width: 200,
                        key: 'accident',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'accident',
                        required: true,
                      }
                    },
                    {
                      table: {
                        title: "<div>作业条件危险性评价(L)<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'lee',
                        width: 200,
                        key: 'lee',
                        tdEdit: true,
                      },
                      form: {
                        type: 'number',
                        field: 'lee',
                        required: true,
                      }
                    },
                    {
                      table: {
                        title: "<div>作业条件危险性评价(B)<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'bee',
                        width: 200,
                        key: 'bee',
                        tdEdit: true,
                      },
                      form: {
                        type: 'number',
                        field: 'bee',
                        required: true,
                      }
                    },
                    {
                      table: {
                        title: "<div>作业条件危险性评价(C)<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'cee',
                        width: 200,
                        key: 'cee',
                        tdEdit: true,
                      },
                      form: {
                        type: 'number',
                        field: 'cee',
                        required: true,
                      }
                    },
                    {
                      table: {
                        title: "作业条件危险性评价(D)",
                        dataIndex: 'dee',
                        width: 200,
                        key: 'dee',
                      }
                    },
                    {
                      table: {
                        title: "风险等级",
                        dataIndex: 'riskLevel',
                        key: 'riskLevel'
                      }
                    },
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
          ]}
        />
      </div>
    );
  }
}

export default index;