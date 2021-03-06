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
    super(props);
    const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
    this.state = {
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
    }
  }
  render() {
    const { departmentId } = this.state;
    const { companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
              projectId: departmentId
            }
          }}
          method={{
            onClickFunEdit: (obj) => {
              this.table.clearSelectedRows();
            },
            onClickFunPrint: (obj) => {

            }
          }}
          // actionBtns={[
          //   {
          //     name: 'add',
          //     icon: 'plus',
          //     type: 'primary',
          //     label: '??????',
          //     formBtns: [
          //       {
          //         name: 'cancel',
          //         type: 'dashed',
          //         label: '??????',
          //       },
          //       {
          //         name: 'submit',
          //         type: 'primary',
          //         label: '??????',
          //         field: 'addsubmit',
          //         fetchConfig: {
          //           apiName: 'addZxSfEquManage'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'edit',
          //     icon: 'edit',
          //     type: 'primary',
          //     label: '??????',
          //     onClick:'bind:onClickFunEdit',
          //     formBtns: [
          //       {
          //         name: 'cancel',
          //         type: 'dashed',
          //         label: '??????',
          //       },
          //       {
          //         name: 'submit',
          //         type: 'primary',
          //         label: '??????',
          //         fetchConfig: {
          //           apiName: 'updateZxSfEquManage'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'del',
          //     icon: 'delete',
          //     type: 'danger',
          //     label: '??????',
          //     field: "del",
          //     fetchConfig: {//ajax??????
          //       apiName: 'batchDeleteUpdateZxSfEquManage',
          //     },
          //   },
          //   {
          //     name: '??????',
          //     type: 'primary',
          //     label: '??????',
          //     field: "dayin",
          //     disabled: "bind:_actionBtnNoSelected",
          //     onClick:'bind:onClickFunPrint'
          //   },
          // ]}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "ProjectSpecialEquipmentAccount"
              }
            }
          }}
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
                initialValue: companyId,
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
                title: '????????????',
                dataIndex: 'orgName',
                key: 'orgName',
                width: 150,
                tooltip: 15,
                filter: true,
                onClick: 'detail',
                render: (data, rowData) => {
                  let color = '';
                  if (rowData.colorFlag === '0') {
                    color = '';
                  } else if (rowData.colorFlag === '1') {
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
                    departmentId
                  }
                },
                spanForm: 12,
              }
            },
            {
              table: {
                title: '???????????????',
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
                title: '?????????',
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
                title: '????????????',
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
                  // tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '??????id',
                        field: 'zxSfEquManageItemId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>??????????????????<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "??????????????????",
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
                        title: "????????????",
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
                        title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "<div>???????????????<span style='color: #ff4d4f'>*</span></div>",
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
                                const stamp = (value) => { //???????????????????????????????????????
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
                                  Msg.warn('???????????????????????????????????????');
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
                        title: "<div>???????????????<span style='color: #ff4d4f'>*</span></div>",
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
                                const stamp = (value) => { //???????????????????????????????????????
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
                                  Msg.warn('???????????????????????????????????????');
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
                        title: "????????????",
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
                        title: "????????????",
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
                        title: "??????????????????",
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
                        title: "??????????????????",
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
                        title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
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
                          { label: "??????", value: "0" },
                          { label: "??????", value: "1" },
                          { label: "??????????????????", value: "2" },
                        ],
                      }
                    },
                    {
                      table: {
                        title: "??????",
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
                      label: "??????",
                    },
                    {
                      name: "del",
                      icon: 'delete',
                      type: 'danger',
                      label: "??????",
                    }
                  ]
                }
              }
            },
            {
              table: {
                title: '????????????????????????',
                dataIndex: 'equipManager',
                key: 'equipManager',
                render: (data, rowData) => {
                  let color = '';
                  if (rowData.colorflag === '0') {//??????
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
                title: '?????????????????????',
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
                title: '????????????',
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
                label: '??????',
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