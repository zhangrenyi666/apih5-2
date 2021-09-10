import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'zxSfAccessId',
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
            apiName: 'getZxSfAccessList',
            otherParams: {
              orgID2: departmentId
            }
          }}
          method={{
            willExecuteFunEdit: (obj) => {
              if (obj.selectedRows[0].outDate) {//退场的不能修改
                Msg.warn('已退场数据不能修改！');
                return false
              } else {

              }
              this.table.clearSelectedRows();
            },
            onClickFunDel: (obj) => {
              const { myFetch } = this.props;
              let arry = [];
              for (let m = 0; m < obj.selectedRows.length; m++) {
                if (obj.selectedRows[m].outDate) {
                  //存在已退场的数据
                  arry.push(obj.selectedRows[m].outDate);
                }
              }
              if (arry.length > 0) {
                Msg.warn('请选择未退场的数据！')
              } else {
                confirm({
                  content: '确定删除选中的数据吗?',
                  onOk: () => {
                    myFetch('batchDeleteUpdateZxSfAccess', obj.selectedRows).then(
                      ({ data, success, message }) => {
                        if (success) {
                          this.table.refresh();
                          this.table.clearSelectedRows();
                        } else {
                        }
                      }
                    );
                  }
                });
              }
            },
            willExecuteFunTuiChang: (obj) => {
              if (obj.selectedRows[0].outDate) {//退场的不能修改
                Msg.warn('请选择未退场的数据！');
                return false
              } else {

              }
              this.table.clearSelectedRows();
            },
            onClickFunCancelTuiChang: (obj) => {
              const { myFetch } = this.props;
              if (obj.selectedRows.length > 0) {
                if (obj.selectedRows[0].outDate) {
                  confirm({
                    content: '确定撤销选中的已退场数据吗?',
                    onOk: () => {
                      myFetch('updateZxSfAccessOutDateReturn', { zxSfAccessId: obj.selectedRows[0].zxSfAccessId }).then(
                        ({ data, success, message }) => {
                          if (success) {
                            Msg.success(message);
                            this.table.refresh();
                            this.table.clearSelectedRows();
                          } else {
                            Msg.error(message);
                          }
                        }
                      );
                    }
                  });
                } else {
                  Msg.warn('请选择已退场的数据!');
                  this.table.clearSelectedRows();
                }
              } else {
                Msg.warn('请选择一条数据!');
                this.table.clearSelectedRows();
              }
            },
            onClickfunPrint: (obj) => {

            }
          }}
          // actionBtns={[
          //   {
          //     name: 'add',
          //     icon: 'plus',
          //     type: 'primary',
          //     label: '新增',
          //     field: "add",
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
          //           apiName: 'addZxSfAccess'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'edit',
          //     icon: 'edit',
          //     type: 'primary',
          //     label: '修改',
          //     field: "edit",
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
          //           apiName: 'updateZxSfAccess'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'delDiy',
          //     icon: 'delete',
          //     type: 'danger',
          //     label: '删除',
          //     field: "del",
          //     disabled: "bind:_actionBtnNoSelected",
          //     onClick: 'bind:onClickFunDel'
          //   },
          //   {
          //     name: 'edit',
          //     icon: 'edit',
          //     type: 'primary',
          //     label: '退场',
          //     field: "tuichang",
          //     willExecute: 'bind:willExecuteFunTuiChang',
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
          //           apiName: 'updateZxSfAccessOutDate'
          //         }
          //       }
          //     ]
          //   },
          //   {
          //     name: 'cheXiaoTuiChang',
          //     type: 'primary',
          //     label: '撤销退场',
          //     field: "chexiao",
          //     disabled: "bind:_actionBtnNoSelected",
          //     onClick: 'bind:onClickFunCancelTuiChang'
          //   },
          //   {
          //     name: '打印',
          //     type: 'primary',
          //     label: '打印',
          //     field: "dayin",
          //     disabled: "bind:_actionBtnNoSelected",
          //     onClick: 'bind:onClickfunPrint'
          //   },
          // ]}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "SafetyAssessOfCooperationTeam"
              }
            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfAccessId',
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
                field: 'crmName',
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
                width: 150,
                tooltip: 15,
                filter: true,
                type: 'select',
                fieldsConfig: {
                  type: 'selectByPaging',
                  field: 'orgID',
                  optionConfig: {
                    label: 'departmentName',
                    value: 'departmentId'
                  },
                  fetchConfig: {
                    apiName: 'getSysCompanyProject',
                    otherParams: { departmentId: departmentId }
                  },
                }
              },
              form: {
                // type: 'select',
                type: 'selectByPaging',
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
                  otherParams: { departmentId: departmentId }
                },
                spanForm: 12,
              }
            },
            {
              table: {
                title: '协作队伍名称',
                dataIndex: 'crmName',
                key: 'crmName',
                width: 180,
                filter: true
              },
              form: {
                type: 'selectByPaging',
                field: 'crmID',
                spanForm: 12,
                required: true,
                allowClear: false,
                showSearch: true,
                optionConfig: {
                  label: 'customerName',
                  value: 'zxCrCustomerInfoId',
                  linkageFields: {
                    cardNo: 'licenceNO',
                    levs: 'qualifiLevel',
                    safeCardNo: 'safeCode',
                    crmName: 'customerName'
                  }
                },
                fetchConfig: {
                  apiName: 'getZxCrCustomerInfoListOne'
                },
                onChange: (val, rowData) => {
                  if (val && rowData.itemData.editTime && rowData.itemData.orgID) {
                    this.props.myFetch('getZxCtContractList', {
                      editTime: rowData.itemData.editTime,
                      orgID: rowData.itemData.orgID
                    }).then(
                      ({ data, success, message }) => {
                        if (success) {
                          this.table.qnnForm.form.setFieldsValue({
                            contractNo: data.contractNo
                          })
                        } else {

                        }
                      }
                    );
                  } else {
                  }

                }
              }
            },
            {
              table: {
                title: '营业执照号',
                dataIndex: 'cardNo',
                key: 'cardNo',
                width: 180,
                onClick: 'detail',
              },
              form: {
                type: 'string',
                field: 'cardNo',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '资质等级',
                dataIndex: 'levs',
                key: 'levs'
              },
              form: {
                type: 'string',
                field: 'levs',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '安全许可证',
                dataIndex: 'safeCardNo',
                key: 'safeCardNo',
                width: 160,
                tooltip: 18,
              },
              form: {
                type: 'string',
                field: 'safeCardNo',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '合同编号',
                dataIndex: 'contractNo',
                key: 'contractNo'
              },
              form: {
                type: 'string',
                field: 'contractNo',
                spanForm: 12,
                required: true
              }
            },
            {
              table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks'
              },
              form: {
                type: 'textarea',
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
                field: 'zxSfAccessItemList',
                incToForm: true,
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxSfAccessItemId',
                    size: 'small'
                  },
                  ...configItem,
                  // tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'zxSfAccessItemId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>安全状况记录<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'safeRecord',
                        key: 'safeRecord',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'safeRecord',
                        required: true
                      }
                    },
                    {
                      table: {
                        title: "违章记录",
                        dataIndex: 'wrongRecord',
                        key: 'wrongRecord',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'wrongRecord'
                      }
                    },
                    {
                      table: {
                        title: "整改情况",
                        dataIndex: 'changeInfo',
                        width: 200,
                        key: 'changeInfo',
                        tdEdit: true,
                      },
                      form: {
                        type: 'textarea',
                        field: 'changeInfo'
                      }
                    },
                    {
                      table: {
                        title: "<div>考核评价<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'examInfo',
                        key: 'examInfo',
                        tdEdit: true
                      },
                      form: {
                        type: 'string',
                        field: 'examInfo',
                        required: true,
                      }
                    },
                    {
                      table: {
                        title: "备注",
                        dataIndex: 'remarks',
                        key: 'remarks',
                        tdEdit: true
                      },
                      form: {
                        type: 'textarea',
                        field: 'remarks'
                      }
                    },
                    {
                      table: {
                        title: "日期",
                        dataIndex: 'bizDate',
                        key: 'bizDate',
                        format: 'YYYY-MM-DD',
                        tdEdit: true
                      },
                      form: {
                        type: 'date',
                        field: 'bizDate'
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
              table: {
                title: '进场日期',//新增、修改时候显示
                dataIndex: 'inDate',
                key: 'inDate',
                filter: true,
                format: 'YYYY-MM-DD'
              },
              form: {
                type: 'date',
                field: 'inDate',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '退场日期',//退场时候显示
                dataIndex: 'outDate',
                key: 'outDate',
                filter: true,
                format: 'YYYY-MM-DD',
                fieldsConfig: {
                  type: 'date',
                  field: 'outDate',
                  hide: false
                }
              },
              form: {
                type: 'date',
                field: 'outDate',
                required: true,
                spanForm: 12,
                hide: (val) => {
                  let formData = '';
                  if (val?.clickCb?.rowInfo) {
                    formData = val.clickCb.rowInfo.field;
                    if (val.clickCb.rowInfo.name === 'detail') {
                      return false
                    } else if (val.clickCb.rowInfo.name === 'add') {
                      return true
                    } else {
                      if (formData && formData === 'tuichang') {
                        return false
                      } else {
                        return true
                      }
                    }
                  } else {
                    return true
                  }
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