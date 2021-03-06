import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { message as Msg } from 'antd';
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
    const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
    this.state = {
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
    }
  }
  render() {
    const { departmentId } = this.state
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
          method={{
            onClickFunEdit: (obj) => {
              this.table.clearSelectedRows();
            },
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
          //           apiName: 'addZxSfProjectEmpMain'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'edit',
          //     icon: 'edit',
          //     type: 'primary',
          //     label: '??????',
          //     onClick: 'bind:onClickFunEdit',
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
          //           apiName: 'updateZxSfProjectEmpMain'
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
          //       apiName: 'batchDeleteUpdateZxSfProjectEmpMain',
          //     },
          //   }
          // ]}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "SafetyManagementPersonnel"
              }
            }
          }}
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
                title: '????????????',
                dataIndex: 'orgName',
                key: 'orgName',
                width: '65%',
                tooltip: 15,
                filter: true,
                onClick: 'detail',
                render: (data, rowData) => {
                  let color = 'black';
                  if (rowData.colorFlag === '0') {
                    color = 'black';
                  } else if (rowData.colorFlag === '1') {
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
                title: '?????????',
                dataIndex: 'reporter',
                key: 'reporter',
                width: '35%',
                render: (data, rowData) => {
                  let color = '';
                  if (rowData.colorFlag === '0') {
                    color = '';
                  } else if (rowData.colorFlag === '1') {
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
                  // tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '??????id',
                        field: 'zxSfProjectEmployeeId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
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
                        optionData: [{ label: '???', value: '1' }, { label: '???', value: '0' }],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
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
                          { label: '??????', value: '1' },
                          { label: '??????', value: '0' },
                          { label: '??????', value: '2' },
                          { label: '??????', value: '3' },
                          { label: '??????', value: '4' },
                          { label: '??????', value: '5' },
                          { label: '??????', value: '6' },
                          { label: '??????', value: '7' }
                        ],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
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
                          { label: '??????', value: '1' },
                          { label: '??????', value: '0' },
                          { label: '??????', value: '2' },
                        ],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "??????????????????",
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
                          { label: '???', value: '1' },
                          { label: '???', value: '0' }
                        ],
                        optionConfig: { label: 'label', value: 'value' },
                      }
                    },
                    {
                      table: {
                        title: "???????????????",
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
                        title: "<div>??????????????????????????????(???)<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "???????????????????????????",
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
                        title: "????????????",
                        width: 160,
                        dataIndex: 'sendCardDate',
                        key: 'sendCardDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'sendCardDate',
                        onChange: async (colVal, thisProps, btnCallbackFn) => {
                          if (colVal) {
                            const editRowData = await btnCallbackFn.getEditedRowData(false);
                            if (editRowData.useEndDate) {
                              const stamp = (value) => { //???????????????????????????????????????
                                const s_time = moment(value).format('YYYY-MM-DD');
                                const time = new Date(s_time).getTime();
                                return time;
                              }
                              const time1 = stamp(colVal);
                              const time2 = stamp(editRowData.useEndDate);
                              var daynew = Math.floor((time2 - time1) / 86400000) + 1;
                              if (daynew > 0) {

                              } else {
                                Msg.warn('????????????????????????????????????');
                                btnCallbackFn.setEditedRowData({
                                  ...editRowData,
                                  sendCardDate: undefined
                                });
                              }
                            }
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "????????????",
                        width: 160,
                        dataIndex: 'useEndDate',
                        key: 'useEndDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'useEndDate',
                        onChange: async (colVal, thisProps, btnCallbackFn) => {
                          if (colVal) {
                            const editRowData = await btnCallbackFn.getEditedRowData(false);
                            if (editRowData.sendCardDate) {
                              const stamp = (value) => { //???????????????????????????????????????
                                const s_time = moment(value).format('YYYY-MM-DD');
                                const time = new Date(s_time).getTime();
                                return time;
                              }
                              const time1 = stamp(colVal);
                              const time2 = stamp(editRowData.sendCardDate);
                              var daynew = Math.floor((time1 - time2) / 86400000) + 1;
                              if (daynew > 0) {

                              } else {
                                Msg.warn('???????????????????????????????????????');
                                btnCallbackFn.setEditedRowData({
                                  ...editRowData,
                                  useEndDate: undefined
                                });
                              }
                            }
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "???????????????????????????",
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
                        title: "????????????",
                        width: 160,
                        dataIndex: 'buildSendDate',
                        key: 'buildSendDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'buildSendDate',
                        onChange: async (colVal, thisProps, btnCallbackFn) => {
                          if (colVal) {
                            const editRowData = await btnCallbackFn.getEditedRowData(false);
                            if (editRowData.buildEndDate) {
                              const stamp = (value) => { //???????????????????????????????????????
                                const s_time = moment(value).format('YYYY-MM-DD');
                                const time = new Date(s_time).getTime();
                                return time;
                              }
                              const time1 = stamp(colVal);
                              const time2 = stamp(editRowData.buildEndDate);
                              var daynew = Math.floor((time2 - time1) / 86400000) + 1;
                              if (daynew > 0) {

                              } else {
                                Msg.warn('????????????????????????????????????');
                                btnCallbackFn.setEditedRowData({
                                  ...editRowData,
                                  buildSendDate: undefined
                                });
                              }
                            }
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "????????????",
                        width: 160,
                        dataIndex: 'buildEndDate',
                        key: 'buildEndDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'buildEndDate',
                        onChange: async (colVal, thisProps, btnCallbackFn) => {
                          if (colVal) {
                            const editRowData = await btnCallbackFn.getEditedRowData(false);
                            if (editRowData.buildSendDate) {
                              const stamp = (value) => { //???????????????????????????????????????
                                const s_time = moment(value).format('YYYY-MM-DD');
                                const time = new Date(s_time).getTime();
                                return time;
                              }
                              const time1 = stamp(colVal);
                              const time2 = stamp(editRowData.buildSendDate);
                              var daynew = Math.floor((time1 - time2) / 86400000) + 1;
                              if (daynew > 0) {

                              } else {
                                Msg.warn('???????????????????????????????????????');
                                btnCallbackFn.setEditedRowData({
                                  ...editRowData,
                                  buildEndDate: undefined
                                });
                              }
                            }
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "<div>????????????????????????<span style='color: #ff4d4f'>*</span></div>",
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
                          { label: '???', value: '1' },
                          { label: '???', value: '0' }
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