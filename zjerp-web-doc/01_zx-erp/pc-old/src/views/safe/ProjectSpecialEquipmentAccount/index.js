import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
import moment from 'moment';
const config = {
  antd: {
    rowKey: 'zxSfEquManageId',
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
    const {
      departmentId,
    } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
            apiName: 'getZxSfEquManageList',
            otherParams: {
              projectId:departmentId
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
                    apiName: 'addZxSfEquManage'
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
                    apiName: 'updateZxSfEquManage'
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
                apiName: 'batchDeleteUpdateZxSfEquManage',
              },
            },
            {
              name: '打印',
              type: 'primary',
              label: '打印',
              field: "dayin",
              disabled: "bind:_actionBtnNoSelected",
              onClick: (obj) => {

              }
            },
          ]}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfEquManageId',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'projectId',
                initialValue:departmentId,
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
                width: 150,
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
                    departmentId:departmentId
                  }
                },
                spanForm: 12,
              }
            },
            {
              table: {
                title: '设备所在地',
                dataIndex: 'equipAddress',
                key: 'equipAddress',
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
                field: 'equipAddress',
                required: true,
                spanForm: 12
              }
            },
            {
              table: {
                title: '联系人',
                dataIndex: 'relationMan',
                key: 'relationMan',
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
                field: 'relationMan',
                spanForm: 12
              }
            },
            {
              table: {
                title: '联系电话',
                dataIndex: 'relationTel',
                key: 'relationTel',
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
                type: 'phone',
                field: 'relationTel',
                spanForm: 12
              }
            },
            {
              isInTable: false,
              form: {
                type: 'qnnTable',
                field: 'zxSfEquManageItemList',
                incToForm: true,
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxSfEquManageItemId',
                    size: 'small'
                  },
                  ...configItem,
                  tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'zxSfEquManageItemId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>机械管理编号<span style='color: #ff4d4f'>*</span></div>",
                        width: 200,
                        dataIndex: 'resCode',
                        key: 'resCode',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'resCode',
                        required: true
                      }
                    },
                    {
                      table: {
                        title: "<div>设备名称<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'resName',
                        width: 200,
                        key: 'resName',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'resName',
                        required: true
                      }
                    },
                    {
                      table: {
                        title: "合格证书编号",
                        dataIndex: 'cardNo',
                        width: 200,
                        key: 'cardNo',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'cardNo'
                      }
                    },
                    {
                      table: {
                        title: "发证单位",
                        dataIndex: 'sendCardUnit',
                        width: 200,
                        key: 'sendCardUnit',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'sendCardUnit'
                      }
                    },
                    {
                      table: {
                        title: "<div>姓名<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'empName',
                        width: 200,
                        key: 'empName',
                        tdEdit: true
                      },
                      form: {
                        type: 'string',
                        field: 'empName',
                        required: true,
                      }
                    },
                    {
                      table: {
                        title: "<div>证书编号<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'empCardNo',
                        width: 200,
                        key: 'empCardNo',
                        tdEdit: true
                      },
                      form: {
                        type: 'string',
                        required: true,
                        field: 'empCardNo'
                      }
                    },
                    {
                      table: {
                        title: "<div>有效期：起<span style='color: #ff4d4f'>*</span></div>",
                        width: 160,
                        dataIndex: 'effStart',
                        key: 'effStart',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'effStart',
                        required: true,
                        onChange: (colVal, thisProps, btnCallbackFn) => {
                          if (colVal) {
                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                              editRowData.effStart = colVal;
                              if (editRowData.effEnd) {
                                const stamp = (value) => { //把获取到的数据转换为时间戳
                                  const s_time = moment(value).format('YYYY-MM-DD');
                                  const time = new Date(s_time).getTime();
                                  return time;
                                }
                                const time1 = stamp(colVal);
                                const time2 = stamp(editRowData.effEnd);
                                var daynew = Math.floor((time2 - time1) / 86400000) + 1;
                                if (daynew > 0) {
                                  btnCallbackFn.setEditedRowData({
                                    ...editRowData,
                                    totalDay: daynew
                                  });
                                } else {
                                  Msg.warn('结束时间应该大于起始时间！');
                                  btnCallbackFn.setEditedRowData({
                                    ...editRowData,
                                    effStart: undefined,
                                    totalDay: null
                                  });
                                }
                              } else {
                                btnCallbackFn.setEditedRowData({
                                  ...editRowData,
                                  totalDay: null
                                });
                              }
                            })
                          } else {
                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                              btnCallbackFn.setEditedRowData({
                                ...editRowData,
                                totalDay: null,
                                effStart: undefined,
                              });
                            })
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "<div>有效期：止<span style='color: #ff4d4f'>*</span></div>",
                        width: 160,
                        dataIndex: 'effEnd',
                        key: 'effEnd',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'effEnd',
                        required: true,
                        onChange: (colVal, thisProps, btnCallbackFn) => {
                          if (colVal) {
                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                              editRowData.effEnd = colVal;
                              if (editRowData.effStart) {
                                const stamp = (value) => { //把获取到的数据转换为时间戳
                                  const s_time = moment(value).format('YYYY-MM-DD');
                                  const time = new Date(s_time).getTime();
                                  return time;
                                }
                                const time1 = stamp(editRowData.effStart);
                                const time2 = stamp(colVal);
                                var daynew = Math.floor((time2 - time1) / 86400000) + 1;
                                if (daynew > 0) {
                                  btnCallbackFn.setEditedRowData({
                                    ...editRowData,
                                    totalDay: daynew
                                  });
                                } else {
                                  Msg.warn('结束时间应该大于起始时间！');
                                  btnCallbackFn.setEditedRowData({
                                    ...editRowData,
                                    effEnd: undefined,
                                    totalDay: null
                                  });
                                }
                              } else {
                                btnCallbackFn.setEditedRowData({
                                  ...editRowData,
                                  totalDay: null
                                });
                              }
                            })
                          } else {
                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                              btnCallbackFn.setEditedRowData({
                                ...editRowData,
                                totalDay: null,
                                effEnd: undefined,
                              });
                            })
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "退场时间",
                        width: 160,
                        dataIndex: 'outDate',
                        key: 'outDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'outDate'
                      }
                    },
                    {
                      table: {
                        title: "共计天数",
                        width: 120,
                        dataIndex: 'totalDay',
                        key: 'totalDay'
                      },
                      form: {
                        type: 'number',
                        field: 'totalDay'
                      }
                    },
                    {
                      table: {
                        title: "设备管理人员",
                        width: 160,
                        dataIndex: 'equipManager',
                        key: 'equipManager',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'equipManager'
                      }
                    },
                    {
                      table: {
                        title: "安全管理人员",
                        width: 160,
                        dataIndex: 'safeManager',
                        key: 'safeManager',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'safeManager'
                      }
                    },
                    {
                      table: {
                        title: "<div>设备来源<span style='color: #ff4d4f'>*</span></div>",
                        width: 160,
                        dataIndex: 'source',
                        key: 'source',
                        tdEdit: true,
                        type: 'select',
                      },
                      form: {
                        type: 'select',
                        field: 'source',
                        required: true,
                        allowClear: false,
                        optionConfig: {
                          label: "label",
                          value: "value",
                        },
                        optionData: [
                          { label: "自有", value: "0" },
                          { label: "外租", value: "1" },
                          { label: "协作队伍自带", value: "2" },
                        ],
                      }
                    },
                    {
                      table: {
                        title: "备注",
                        width: 160,
                        dataIndex: 'remarks',
                        key: 'remarks',
                        tdEdit: true,
                      },
                      form: {
                        type: 'textarea',
                        field: 'remarks',
                        autoSize: {
                          minRows: 1,
                          maxRows: 3
                        }
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
            {
              table: {
                title: '设备安全主管领导',
                dataIndex: 'equipManager',
                key: 'equipManager',
                render: (data, rowData) => {
                  let color = '';
                  if (rowData.colorflag === '0') {//黑色
                    color = '';
                  } else if (rowData.colorflag === '1') {
                    color = 'red';
                  }
                  return <div style={{ color: color }}>{data}</div>
                }
              },
              form: {
                type: 'string',
                field: 'equipManager',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '主管部门负责人',
                dataIndex: 'deManager',
                key: 'deManager',
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
                field: 'deManager',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '填报日期',
                dataIndex: 'createDate',
                key: 'createDate',
                filter: true,
                // format: 'YYYY-MM-DD',
                render: (data, rowData) => {
                  let color = '';
                  if (rowData.colorflag === '0') {
                    color = '';
                  } else if (rowData.colorflag === '1') {
                    color = 'red';
                  }
                  return <div style={{ color: color }}>{moment(data).format('YYYY-MM-DD')}</div>
                }
              },
              form: {
                type: 'date',
                field: 'createDate',
                initialValue: () => {
                  return moment(new Date()).format('YYYY-MM-DD')
                },
                spanForm: 12,
                required: true
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