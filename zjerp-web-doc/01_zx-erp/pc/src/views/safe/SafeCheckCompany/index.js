import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
import moment from 'moment';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxSfCheckId',
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
      sm: { span: 6 }
    },
    wrapperCol: {
      xs: { span: 16 },
      sm: { span: 18 }
    }
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
    const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;

    this.state = {
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
    }
  }
  render() {
    const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
            apiName: 'getZxSfCheckListAllCompany',
            otherParams: {
              checkType: '2',
              projectId: departmentId
            }
          }}
          method={{
            willExecuteFunEdit: (obj) => {
              if (obj.selectedRows[0].isReported === '3' || (obj.selectedRows[0].isSend === '1' || obj.selectedRows[0].isSend === '2')) {
                Msg.warn('??????????????????????????????????????????');
                return false
              } else {
                if (obj.selectedRows[0].checkAgainStatus) {//???????????????????????????-????????????????????????
                  Msg.warn('??????????????????????????????');
                  return false
                } else {
                  let selectRow = obj.selectedRows[0].zxSfCheckItemList;
                  obj.qnnTableInstance.setDeawerValues({
                    zxSfCheckItemList: selectRow.map((item) => {
                      item.addType = '1';
                      return item
                    })
                  });
                  // if (this.table && this.table.qnnForm) {
                  //   this.table.qnnForm.form.setFieldsValue({
                  //     zxSfCheckItemList: selectRow.map((item) => {
                  //       item.addType = '1';
                  //       return item
                  //     })
                  //   })
                  // }
                }
              }
              this.table.clearSelectedRows();
            },
            onClickFunDel: (obj) => {
              // ?????????????????????????????????????????????
              const { myFetch } = this.props;
              let arry = [];
              for (let m = 0; m < obj.selectedRows.length; m++) {
                if (obj.selectedRows[m].isSend === '1' || obj.selectedRows[m].isSend === '2' || obj.selectedRows[m].checkAgainStatus || obj.selectedRows[m].isReported === '3') {
                  //???????????????????????????????????????????????????
                  arry.push(obj.selectedRows[m].apih5FlowStatus);
                }
              }

              if (arry.length > 0) {
                Msg.warn('??????????????????????????????????????????');
                this.table.clearSelectedRows();
              } else {
                confirm({
                  content: '???????????????????????????????',
                  onOk: () => {
                    myFetch('batchDeleteUpdateZxSfCheck', obj.selectedRows).then(
                      ({ data, success, message }) => {
                        if (success) {

                        } else {
                        }
                      }
                    );
                    this.table.refresh();
                    this.table.clearSelectedRows();
                  }
                });
              }
            },
            onClickFunFuCha: (obj) => {//??????????????????
              let selectRow = obj.selectedRows[0].zxSfCheckItemList;
              if (this.table && this.table.qnnForm) {
                this.table.qnnForm.form.setFieldsValue({
                  zxSfCheckItemList: selectRow.map((item) => {
                    item.addType = '0';
                    return item
                  })
                })
              }
              this.table.clearSelectedRows();
            },
            onClickFunUp: (obj) => {
              // ????????????????????????????????????????????????????????????????????????????????????????????????
              const { myFetch } = this.props;
              if (obj.selectedRows.length === 1) {
                if (obj.selectedRows[0].isSend === '1') {//???????????????????????????????????????
                  if (obj.selectedRows[0].checkAgainStatus === '2') {
                    confirm({
                      content: '???????????????????????????????',
                      onOk: () => {
                        myFetch('updateZxSfCheckIsReportedCompany', obj.selectedRows[0]).then(
                          ({ data, success, message }) => {
                            if (success) {
                              Msg.success(message)
                              this.table.refresh();
                              this.table.clearSelectedRows();
                            } else {
                              Msg.error(message)
                            }
                          }
                        );
                      }
                    });
                  } else {
                    Msg.warn('????????????????????????????????????');
                    obj.btnCallbackFn.closeDrawer();
                    this.table.clearSelectedRows();
                  }
                } else {
                  obj.btnCallbackFn.closeDrawer();
                  this.table.clearSelectedRows();
                  Msg.warn('??????????????????????????????????????????????????????????????????');
                }
              } else {
                Msg.warn('???????????????????????????')
              }
            },
            onClickFunXiaDa: (obj) => {//?????????????????????????????????????????????
              const { myFetch } = this.props;
              if (obj.selectedRows.length === 1) {
                if (obj.selectedRows[0].isSend === '1') {
                  obj.btnCallbackFn.closeDrawer();
                  this.table.clearSelectedRows();
                  Msg.warn('??????????????????????????????!');
                } else {
                  // if (obj.selectedRows[0].checkAgainStatus ==='2') {
                  confirm({
                    content: '???????????????????????????????',
                    onOk: () => {
                      myFetch('updateZxSfCheckIsSendCompany', obj.selectedRows[0]).then(
                        ({ data, success, message }) => {
                          if (success) {
                            Msg.success(message)
                            this.table.refresh();
                            this.table.clearSelectedRows();
                          } else {
                            Msg.error(message)
                          }
                        }
                      );
                    }
                  });
                  // } else {
                  //   obj.btnCallbackFn.closeDrawer();
                  //   this.table.clearSelectedRows();
                  //   Msg.warn('?????????????????????????????????!');
                  // }

                }
              } else {
                Msg.warn('???????????????????????????')
              }
            }
          }}
          // actionBtns={[
          //   {
          //     name: 'add',
          //     icon: 'plus',
          //     type: 'primary',
          //     label: '??????',
          //     field: 'addBtn',
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
          //           apiName: 'addZxSfCheck'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'edit',
          //     icon: 'edit',
          //     type: 'primary',
          //     label: '??????',
          //     field: 'editBtn',
          //     willExecute: 'bind:willExecuteFunEdit',
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
          //           apiName: 'updateZxSfCheck'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'delDiy',
          //     icon: 'delete',
          //     type: 'danger',
          //     label: '??????',
          //     disabled: "bind:_actionBtnNoSelected",
          //     field: "delBtn",
          //     onClick: 'bind:onClickFunDel'
          //   },
          //   {
          //     name: 'edit',
          //     icon: 'edit',
          //     type: 'primary',
          //     label: '??????',
          //     field: 'fuchaBtn',
          //     onClick: 'bind:onClickFunFuCha',
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
          //           apiName: 'updateZxSfCheckCheckAgainStatusCompany'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'diy',
          //     type: 'primary',
          //     label: '??????',
          //     field: 'shangbaoBtn',
          //     disabled: "bind:_actionBtnNoSelected",
          //     onClick: 'bind:onClickFunUp'
          //   },
          //   {
          //     name: 'diy',
          //     type: 'primary',
          //     label: '??????',
          //     field: 'xiadaBtn',
          //     disabled: "bind:_actionBtnNoSelected",
          //     onClick: 'bind:onClickFunXiaDa'
          //   }
          // ]}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "SafeCheckCompany"
              }
            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfCheckId',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'checkType',
                initialValue: '2',
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
              isInTable: false,
              form: {
                type: 'string',
                field: 'projName',
                hide: true,
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
              table: {
                title: '????????????',
                dataIndex: 'companyName',
                key: 'companyName',
                width: 150,
                tooltip: 15,
              },
              form: {
                type: 'string',
                field: 'companyName',
                hide: true,
                initialValue: () => {
                  return companyName
                },
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'projName',
                key: 'projName',
                filter: true,
                onClick: 'detail',
                width: 150,
                tooltip: 15
              },
              form: {
                type: 'select',
                field: 'projID',
                required: true,
                showSearch: true,
                optionConfig: {
                  label: 'departmentName',
                  value: 'departmentId',
                  linkageFields: {
                    projName: 'departmentName'
                  }
                },
                fetchConfig: {
                  apiName: 'getSysCompanyProject',
                  otherParams: {
                    departmentId: departmentId
                  },
                },
                spanForm: 12,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'checkDate',
                key: 'checkDate',
                filter: true,
                format: 'YYYY-MM-DD',
              },
              form: {
                type: 'date',
                field: 'checkDate',
                required: true,
                initialValue: () => {
                  return moment(new Date()).format('YYYY-MM-DD')
                },
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                label: '????????????',
                type: 'textarea',
                field: 'checkContext',
                autoSize: {
                  minRows: 1,
                  maxRows: 3
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
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'checkGroup',
                key: 'checkGroup',
                width: 100
              },
              form: {
                type: 'string',
                field: 'checkGroup',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'checkEmployee',
                key: 'checkEmployee',
                width: 100
              },
              form: {
                type: 'string',
                field: 'checkEmployee',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                type: 'qnnTable',
                field: 'zxSfCheckItemList',
                incToForm: true,
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxSfCheckItemId',
                    size: 'small'
                  },
                  ...configItem,
                  // tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '??????id',
                        field: 'zxSfCheckItemId',
                        hide: true
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        label: '??????id',
                        field: 'addType',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'riskInfo',
                        width: 200,
                        key: 'riskInfo',
                        tdEdit: true,
                        fieldConfig: {
                          disabled: ({ record }) => {
                            return record.addType === '0' ? true : false
                          }
                        }
                      },
                      form: {
                        type: 'string',
                        field: 'riskInfo',
                        required: true,

                      }
                    },
                    {
                      table: {
                        title: "????????????",
                        dataIndex: 'lastCheckDate',
                        width: 200,
                        key: 'lastCheckDate',
                        tdEdit: true,
                        format: 'YYYY-MM-DD',
                        fieldConfig: {
                          disabled: ({ record }) => {
                            return record.addType === '0' ? true : false
                          }
                        }
                      },
                      form: {
                        type: 'date',
                        field: 'lastCheckDate'
                      }
                    },
                    {
                      table: {
                        title: "???????????????",
                        dataIndex: 'changeInfo',
                        width: 200,
                        key: 'changeInfo',
                        tdEdit: true,
                      },
                      form: {
                        type: 'textarea',
                        field: 'changeInfo',
                        autoSize: {
                          minRows: 1,
                          maxRows: 3
                        },
                      }
                    },
                    {
                      table: {
                        title: "????????????",
                        dataIndex: 'checkDate',
                        width: 200,
                        key: 'checkDate',
                        tdEdit: true,
                        format: 'YYYY-MM-DD',
                        fieldConfig: {
                          disabled: ({ record }) => {
                            return record.addType === '1' ? true : false
                          }
                        }
                      },
                      form: {
                        type: 'date',
                        field: 'checkDate',
                        
                      }
                    },
                    {
                      table: {
                        title: "????????????",
                        dataIndex: 'checker',
                        width: 200,
                        key: 'checker',
                        tdEdit: true,
                        fieldConfig: {
                          disabled: ({ record }) => {
                            return record.addType === '1' ? true : false
                          }
                        }
                      },
                      form: {
                        type: 'string',
                        field: 'checker',
                        
                      }
                    },
                    {
                      table: {
                        title: "????????????",
                        width: 120,
                        dataIndex: 'checkResult',
                        key: 'checkResult',
                        tdEdit: true,
                        type: 'select',
                        fieldConfig: {
                          disabled: ({ record }) => {
                            return record.addType === '1' ? true : false
                          }
                        }
                      },
                      form: {
                        type: 'select',
                        field: 'checkResult',
                        optionData: [{ label: '??????', value: '1' }, { label: '?????????', value: '0' }],
                        optionConfig: { label: 'label', value: 'value' },
                        
                      }
                    },
                    {
                      table: {
                        title: "??????",
                        width: 120,
                        tooltip: 10,
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
                        },
                      }
                    },
                  ],
                  actionBtns: [
                    {
                      name: "addRow",
                      icon: "plus",
                      type: "primary",
                      label: "??????",
                      addRowDefaultData: (obj) => {

                        return {
                          addType: '1'
                        }
                      },
                      hide: (val) => {
                        let formData = this.table.qnnForm.props.clickCb.rowInfo.field;
                        if (formData === 'fuchaBtn') {
                          return true
                        } else {
                          return false
                        }
                      }
                    },
                    {
                      name: "del",
                      icon: 'delete',
                      type: 'danger',
                      label: "??????",
                      hide: (val) => {
                        let formData = this.table.qnnForm.props.clickCb.rowInfo.field;
                        if (formData === 'fuchaBtn') {
                          return true
                        } else {
                          return false
                        }
                      }
                    }
                  ]
                }
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'isSend',
                key: 'isSend',
                width: 100,
                type: 'select'
              },
              form: {
                type: 'radio',
                addDisabled: true,
                editDisabled: true,
                spanForm: 12,
                optionData: [
                  {
                    label: '?????????',
                    value: '1'
                  },
                  {
                    label: '????????????',
                    value: '2'
                  }
                ]
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'isReported',
                key: 'isReported',
                width: 100,
                type: 'select'
              },
              isInForm: false,
              form: {
                type: 'radio',
                // hide: true,
                optionData: [
                  {
                    label: '????????????',
                    value: '3'
                  },
                  {
                    label: '????????????',
                    value: '2'
                  }
                ]
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'checkAgainStatus',
                key: 'checkAgainStatus',
                filter: true,
                width: 100,
                type: 'select'
              },
              isInForm: false,
              form: {
                type: 'radio',
                // hide: true,
                optionData: [
                  {
                    label: '?????????',
                    value: '1'
                  },
                  {
                    label: '????????????',
                    value: '2'
                  },
                  {
                    label: '????????????',
                    value: '3'
                  }
                ]
              }
            },
            {
              isInTable: false,
              form: {
                label: '??????',
                field: 'fileList',
                type: 'files',
                // accept: '.xls',
                fetchConfig: {
                  apiName: 'upload'
                },
                spanForm: 12,
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;