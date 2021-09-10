import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
  antd: {
    rowKey: 'id',
    size: 'small'
  },
  drawerConfig: {
    width: '70%'
  },
  paginationConfig: {
    position: 'bottom'
  },
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
            apiName: 'getZxCtContrJzBaseList',
            otherParams: {
              type: '2',
              orgID: departmentId
            }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
            }
          }}
          method={{
            hideForAdd: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "0" || obj?.btnCallbackFn?.form?.getFieldValue?.('id')) {
                return true;
              } else {
                return false;
              }
            },
            hideForEdit: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "0") return true;
              return false;
            },
            hideForList: (obj) => {
              let index = obj.btnCallbackFn.getActiveKey();
              if (index !== "1") return true;
              return false;
            },
            fetchConfigForAdd: (obj) => {
              let params = {}
              params.type = '2'
              return {
                apiName: 'addZxCtContrJzBase',
                otherParams: params,
                success: async ({ data, success, message }) => {
                  if (success) {
                    await this.table.setDeawerValues(data)
                    obj.btnCallbackFn.setActiveKey('1');
                  } else {
                    Msg.error(message);
                  }
                }
              }
            },
            fetchConfigForAddList: async (obj) => {
              let params = {}
              let zxCtContrJzItemBaseList = await this.tableOne.getTableData().map((item) => {
                if (item.addFlag === '1') item.id = null
                return item
              })
              params.zxCtContrJzItemBaseList = zxCtContrJzItemBaseList
              params.id = obj._formData.id
              return {
                apiName: 'batchSaveUpdateZxCtContrJzItemBase',
                otherParams: params,
                success: async ({ data, success, message }) => {
                  if (success) {
                    await this.table.setDeawerValues(data)
                    obj.btnCallbackFn.setActiveKey('1');
                  } else {
                    Msg.error(message);
                  }
                }
              }
            },
            fetchConfigForEdit: (obj) => {
              return {
                apiName: 'updateZxCtContrJzBase',
                success: async ({ data, success }) => {
                  if (success) {
                    await this.table.setDeawerValues(data)
                    obj.btnCallbackFn.setActiveKey('1');
                  }
                }
              }
            },
            fetchConfigForEditList: async (obj) => {
              let params = {}
              let zxCtContrJzItemBaseList = await this.tableOne.getTableData().map((item) => {
                if (item.addFlag === '1') item.id = null
                return item
              })
              params.zxCtContrJzItemBaseList = zxCtContrJzItemBaseList
              params.id = obj._formData.id
              return {
                apiName: 'batchSaveUpdateZxCtContrJzItemBase',
                otherParams: params,
              }
            },
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "BuildCurrentProjectList"
              }
            }
          }}
          formConfig={[
            {
              table: {
                title: '项目名称',
                dataIndex: 'orgName',
                key: 'orgName',
                filter: true,
                fieldsConfig: {
                    field: 'orgID',
                    type: 'select',
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                    },
                    showSearch: true,
                    fetchConfig: {
                        apiName: 'getZxCtContractListByStatus',
                        otherParams: {
                          orgID: departmentId
                        }
                    },
                },
                onClick: 'detail',
              },
            },
            {
              table: {
                title: '说明',
                dataIndex: 'remarks',
                key: 'remarks',
              }
            },
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "基础信息",
              content: {
                formConfig: [
                  {
                    field: "editLineName",
                    type: 'string',
                    hide: true,
                    initialValue: ''
                  },
                  {
                    field: 'id',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'orgName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comID',
                    type: 'string',
                    hide: true,
                  },
                  {
                    field: 'comName',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '项目名称',
                    field: 'orgID',
                    required: true,
                    type: 'select',
                    showSearch: true,
                    fetchConfig: {
                      apiName: 'getZxCtContractListByStatus',
                      otherParams: {
                        orgID: departmentId
                      }
                    },
                    optionConfig: {
                      label: 'orgName',
                      value: 'orgID',
                      linkageFields: {
                        orgName: 'orgName',
                        comID: 'companyId',
                        comName: 'companyName'
                      }
                    },
                    editDisabled: true,   //编辑禁用
                    placeholder: '请选择',
                    span: 12,
                    formItemLayoutForm: {
                      labelCol: {
                        xs: { span: 3 },
                        sm: { span: 3 }
                      },
                      wrapperCol: {
                        xs: { span: 21 },
                        sm: { span: 21 }
                      }
                    },
                  },

                  {
                    field: 'remarks',
                    label: '说明',
                    type: 'textarea',
                    placeholder: '请输入',
                    span: 12,
                    formItemLayout: {
                      labelCol: {
                        xs: { span: 4 },
                        sm: { span: 4 }
                      },
                      wrapperCol: {
                        xs: { span: 20 },
                        sm: { span: 20 }
                      }
                    }
                  },
                ]
              }
            },
            {
              field: "table1",
              name: "qnnTable",
              title: "清单",
              disabled: function (obj) {
                return ((obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("contractReviewId"))
              },
              content: {
                fetchConfig: (obj) => {
                  return {
                    apiName: 'getZxCtContrJzItemBaseList',
                    otherParams: {
                      jzType: '2',
                      mainID: this.table.form.getFieldValue('id')
                    },
                  }
                },
                wrappedComponentRef: (me) => {
                  this.tableOne = me;
                },
                antd: {
                  size: "small",
                  rowKey: "id",
                },
                paginationConfig: false,
                pageSize: 99,
                isShowRowSelect: false,
                formConfig: [
                  {
                    table: {
                      title: '项目',
                      dataIndex: 'subType',
                      key: 'subType',
                      width: 150,
                      render: (h) => {
                        if (h === '0') {
                          return '预计总收入'
                        } else if (h === '1') {
                          return '预计总成本'
                        }
                      }
                    },
                  },
                  {
                    table: {
                      title: '项目内容',
                      dataIndex: 'subDetail',
                      key: 'subDetail',
                      width: 150,
                    },
                  },
                  {
                    table: {
                      title: '项目内容明细',
                      dataIndex: 'subDetail2',
                      key: 'subDetail2',
                      width: 150,
                      tdEdit: true,
                    },
                    form: {
                      type: 'string',
                      placeholder: '请输入',
                      field: 'subDetail2',
                    },
                  },
                  {
                    table: {
                      title: '说明',
                      width: 150,
                      tooltip: 15,
                      dataIndex: 'remarks',
                      key: 'remarks',
                      tdEdit: true,
                    },
                    form: {
                      type: 'string',
                      field: 'remarks',
                      placeholder: '请输入',
                    }
                  },
                  {
                    isInTable: (obj) => {
                      if (obj.clickCb.rowInfo.name === 'detail') {
                        return false
                      }
                      return true
                    },
                    table: {
                      showType: 'tile',
                      width: 80,
                      title: "操作",
                      key: "action",
                      fixed: 'right',
                      btns: [
                        {
                          render: function (data, rows) {
                            const type = rows.isHuizong === '0' ? '' : rows.isHuizong === '1' ? '新增' : '删除'
                            return '<a>' + type + '</a>';
                          },
                          onClick: async (obj) => {
                            if (obj.qnnTableInstance.getCurEditRowId()) {
                              Msg.warn('有未保存数据，请保存后重试。')
                              return
                            }
                            let data = await this.tableOne.getTableData()
                            let arr = [] //所有数据的id数组
                            let id = obj.rowId   //当前选中元素id
                            for (var i = 0; i < data.length; i++) { arr.push(data[i].id) }
                            const index = arr.indexOf(id)   //获取选中元素位置
                            if (obj.rowData.isHuizong === '1') {
                              let newData = { ...data[index] }
                              newData.isHuizong = '2'
                              newData.jzType = '2'
                              newData.orderStr = data[index].orderStr + 1
                              newData.remarks = null
                              newData.subDetail2 = null
                              newData.id = new Date().getTime()
                              newData.addFlag = '1'
                              data.splice(index + 1, 0, newData)
                              await obj.qnnTableInstance.setTableData(data)
                            } else {
                              confirm({
                                content: '确定删除选中的数据吗?',
                                onOk: () => {
                                  data.splice(index, 1)
                                  obj.qnnTableInstance.setTableData(data)
                                }
                              });
                            }
                          }
                        }
                      ]
                    }
                  },
                ]
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;