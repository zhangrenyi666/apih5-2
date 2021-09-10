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
                Msg.warn('请选择未上报、未下达的数据！');
                return false
              } else {
                if (obj.selectedRows[0].checkAgainStatus) {//公司自己建立的数据-复查后不可以修改
                  Msg.warn('请选择未复查的数据！');
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
              // 公司下达、局下达的数据不能删除
              const { myFetch } = this.props;
              let arry = [];
              for (let m = 0; m < obj.selectedRows.length; m++) {
                if (obj.selectedRows[m].isSend === '1' || obj.selectedRows[m].isSend === '2' || obj.selectedRows[m].checkAgainStatus || obj.selectedRows[m].isReported === '3') {
                  //存在公司下达、局下达、复审后的数据
                  arry.push(obj.selectedRows[m].apih5FlowStatus);
                }
              }

              if (arry.length > 0) {
                Msg.warn('请选择未下达、未复审的数据！');
                this.table.clearSelectedRows();
              } else {
                confirm({
                  content: '确定删除选中的数据吗?',
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
            onClickFunFuCha: (obj) => {//复查不做控制
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
              // 项目自己建立的数据不能上报给公司或局。其他的，复审后才能上报公司
              const { myFetch } = this.props;
              if (obj.selectedRows.length === 1) {
                if (obj.selectedRows[0].isSend === '1') {//局下达、公司复审后可以上报
                  if (obj.selectedRows[0].checkAgainStatus === '2') {
                    confirm({
                      content: '确定上报选中的数据吗?',
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
                    Msg.warn('请选择公司复查后的数据！');
                    obj.btnCallbackFn.closeDrawer();
                    this.table.clearSelectedRows();
                  }
                } else {
                  obj.btnCallbackFn.closeDrawer();
                  this.table.clearSelectedRows();
                  Msg.warn('公司自建的数据（或公司下达）的数据不能上报！');
                }
              } else {
                Msg.warn('只能上报一条数据！')
              }
            },
            onClickFunXiaDa: (obj) => {//局下达的数据不能由公司再次下达
              const { myFetch } = this.props;
              if (obj.selectedRows.length === 1) {
                if (obj.selectedRows[0].isSend === '1') {
                  obj.btnCallbackFn.closeDrawer();
                  this.table.clearSelectedRows();
                  Msg.warn('局下达的数据不能下达!');
                } else {
                  // if (obj.selectedRows[0].checkAgainStatus ==='2') {
                  confirm({
                    content: '确定下达选中的数据吗?',
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
                  //   Msg.warn('公司复查的数据才能下达!');
                  // }

                }
              } else {
                Msg.warn('只能下达一条数据！')
              }
            }
          }}
          // actionBtns={[
          //   {
          //     name: 'add',
          //     icon: 'plus',
          //     type: 'primary',
          //     label: '新增',
          //     field: 'addBtn',
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
          //     label: '修改',
          //     field: 'editBtn',
          //     willExecute: 'bind:willExecuteFunEdit',
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
          //           apiName: 'updateZxSfCheck'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'delDiy',
          //     icon: 'delete',
          //     type: 'danger',
          //     label: '删除',
          //     disabled: "bind:_actionBtnNoSelected",
          //     field: "delBtn",
          //     onClick: 'bind:onClickFunDel'
          //   },
          //   {
          //     name: 'edit',
          //     icon: 'edit',
          //     type: 'primary',
          //     label: '复查',
          //     field: 'fuchaBtn',
          //     onClick: 'bind:onClickFunFuCha',
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
          //           apiName: 'updateZxSfCheckCheckAgainStatusCompany'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'diy',
          //     type: 'primary',
          //     label: '上报',
          //     field: 'shangbaoBtn',
          //     disabled: "bind:_actionBtnNoSelected",
          //     onClick: 'bind:onClickFunUp'
          //   },
          //   {
          //     name: 'diy',
          //     type: 'primary',
          //     label: '下达',
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
                title: '公司名称',
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
                title: '项目名称',
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
                title: '检查日期',
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
                label: '检查内容',
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
                title: '检查组长',
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
                title: '检查成员',
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
                        label: '主键id',
                        field: 'zxSfCheckItemId',
                        hide: true
                      }
                    },
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'addType',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>存在隐患<span style='color: #ff4d4f'>*</span></div>",
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
                        title: "整改期限",
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
                        title: "拟整改措施",
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
                        title: "复查日期",
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
                        title: "复查人员",
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
                        title: "复查结论",
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
                        optionData: [{ label: '合格', value: '1' }, { label: '不合格', value: '0' }],
                        optionConfig: { label: 'label', value: 'value' },
                        
                      }
                    },
                    {
                      table: {
                        title: "备注",
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
                      label: "新增",
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
                      label: "删除",
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
                title: '是否下达',
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
                    label: '局下达',
                    value: '1'
                  },
                  {
                    label: '公司下达',
                    value: '2'
                  }
                ]
              }
            },
            {
              table: {
                title: '是否上报',
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
                    label: '项目上报',
                    value: '3'
                  },
                  {
                    label: '公司上报',
                    value: '2'
                  }
                ]
              }
            },
            {
              table: {
                title: '复查状态',
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
                    label: '局复查',
                    value: '1'
                  },
                  {
                    label: '公司复查',
                    value: '2'
                  },
                  {
                    label: '项目复查',
                    value: '3'
                  }
                ]
              }
            },
            {
              isInTable: false,
              form: {
                label: '附件',
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