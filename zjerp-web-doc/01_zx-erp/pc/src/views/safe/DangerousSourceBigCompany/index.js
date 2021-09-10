import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
  antd: {
    rowKey: 'zxSfHazardMainId',
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
      lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : '',
      lockProjectName: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectName : '',
    }
  }
  FloatMulThree(arg1, arg2, arg3) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString(), s3 = arg3.toString();
    try { m += s1.split(".")[1].length } catch (e) { }
    try { m += s2.split(".")[1].length } catch (e) { }
    try { m += s3.split(".")[1].length } catch (e) { }
    return (Number(s1.replace(".", "")) * Number(s2.replace(".", "")) * Number(s3.replace(".", ""))) / Math.pow(10, m)
  }
  render() {
    const { ext1, departmentId, departmentName, companyName, companyId, projectId, projectName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const { lockProjectId, lockProjectName } = this.state;
    let jurisdiction = departmentId;
    let jurisdictionName = departmentName;
    if (lockProjectId !== 'all' && lockProjectId !== '') {
      jurisdiction = lockProjectId;
      jurisdictionName = lockProjectName;
    } else {
      if (ext1 === '1' || ext1 === '2') {
        jurisdiction = companyId;
        jurisdictionName = companyName;
      } else if (ext1 === '3' || ext1 === '4') {
        jurisdiction = projectId;
        jurisdictionName = projectName;
      } else { }
    }
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
            apiName: 'getZxSfHazardMainList',
            otherParams: {
              type: 'org',
              orgID2: jurisdiction
            }
          }}
          method={{
            onClickFunEdit: (obj) => {
              this.table.clearSelectedRows();
            },
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "DangerousSourceBigCompany"
              }
            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'zxSfHazardMainId',
                hide: true
              }
            },
            {
              isInTable: false,
              form: {
                type: 'string',
                field: 'type',
                initialValue: 'org',
                hide: true
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
              form: {
                type: 'string',
                field: 'orgID',
                initialValue: () => {
                  return jurisdiction
                },
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

                tooltip: 15
              },
              form: {
                type: 'string',
                field: 'companyName',
                initialValue: companyName,
                hide: true,
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
                filter: true,
                onClick: 'detail',
                width: 150,
                tooltip: 15,
              },
              form: {
                type: 'string',
                field: 'orgName',
                initialValue: () => {
                  return jurisdictionName
                },
                addDisabled: true,
                editDisabled: true,
                spanForm: 12,
                required: true,
                showSearch: true,
                optionConfig: {
                  label: 'orgName',
                  value: 'orgID',
                  linkageFields: {
                    companyName: 'companyName',
                    companyId: 'companyId',
                    orgName: 'orgName'
                  }
                },
                fetchConfig: {
                  apiName: "getZxCtContractListByStatus"
                }
              }
            },
            {
              table: {
                title: '编制人',
                dataIndex: 'creator',
                key: 'creator',
                width: 100
              },
              form: {
                type: 'string',
                field: 'creator',
                initialValue: () => {
                  return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                },
                spanForm: 12
              }
            },
            {
              isInTable: false,
              form: {
                type: 'qnnTable',
                field: 'detailList',//???
                incToForm: true,
                qnnTableConfig: {
                  antd: {
                    rowKey: 'zxSfHazardId',
                    size: 'small'
                  },
                  ...configItem,
                  // tableTdEdit: true,
                  formConfig: [
                    {
                      isInTable: false,
                      form: {
                        label: '主键id',
                        field: 'zxSfHazardId',
                        hide: true
                      }
                    },
                    {
                      table: {
                        title: "<div>过程(区域)<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'proArea',
                        width: 200,
                        key: 'proArea',
                        // type: 'select',
                        tdEdit: true,
                      },
                      form: {
                        type: 'select',
                        field: 'proArea',
                        required: true,
                        optionConfig: {
                          label: 'proArea',
                          // value: 'zxSfHazardRoomAttId'
                          value: 'proArea'
                        },
                        fetchConfig: {
                          apiName: 'getZxSfHazardRoomAttProArea'
                        }
                      }
                    },
                    {
                      table: {
                        title: "行为(活动)或设备=环境",
                        dataIndex: 'doing',
                        width: 200,
                        key: 'doing',
                        tdEdit: true,
                        // type: 'select',
                      },
                      form: {
                        type: 'select',
                        field: 'doing',
                        dependenciesReRender: true,//多个依赖-配置
                        dependencies: ['proArea'],
                        optionConfig: {
                          label: 'doing',
                          value: 'doing'
                        },
                        fetchConfig: {
                          apiName: 'getZxSfHazardRoomAttDoing',
                          params: {
                            proArea: 'proArea'
                          }
                          // otherParams: async (obj) => {
                          //   const rowData2 = await obj.qnnTableInstance.getEditedRowData();
                          //   console.log(rowData2);
                          //   return {
                          //     proArea: rowData2.proArea,
                          //   }
                          // }
                        }
                      }
                    },
                    {
                      table: {
                        title: "危险因素",
                        dataIndex: 'riskFactors',
                        width: 200,
                        key: 'riskFactors',
                        tdEdit: true,
                        // type: 'select',
                      },
                      form: {
                        type: 'select',
                        field: 'riskFactors',
                        dependenciesReRender: true,//多个依赖-配置
                        dependencies: ['doing'],
                        optionConfig: {
                          label: 'riskFactors',
                          value: 'riskFactors'
                        },
                        fetchConfig: {
                          apiName: 'getZxSfHazardRoomAttRiskFactors',
                          params: {
                            doing: 'doing'
                          }
                          // otherParams: async (obj) => {
                          //   const rowData2 = await obj.qnnTableInstance.getEditedRowData();
                          //   return {
                          //     doing: rowData2.doing,
                          //   }
                          // }
                        },
                        onChange: (val, thisProps, btnCallbackFn) => {
                          if (val) {
                            btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                              editRowData.riskFactors = val;
                              const itemData = thisProps.itemData;
                              let total = 0;
                              let riskLevelwal = 0;
                              if (itemData.lee >= 0 && itemData.bee >= 0 && itemData.cee >= 0) {
                                // total = Number(itemData.lee) * Number(itemData.cee) * Number(itemData.bee);
                                total = this.FloatMulThree(itemData.lee, itemData.cee, itemData.bee);
                                riskLevelwal = (total < 100 && total >= 0) ? '一级' : ((total >= 100 && total < 400) ? '二级' : ((total >= 400 && total < 540) ? '三级' : ((total >= 540 && total < 720) ? '四级' : ((total >= 720) ? '五级' : ''))));
                              }
                              btnCallbackFn.setEditedRowData({
                                ...editRowData,
                                accident: itemData.accident,
                                lee: itemData.lee,
                                bee: itemData.bee,
                                cee: itemData.cee,
                                dee: total,
                                riskLevel: riskLevelwal
                              });
                            })
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "可能导致的伤害(事故)",
                        dataIndex: 'accident',
                        width: 200,
                        key: 'accident',
                        tdEdit: true,
                      },
                      form: {
                        type: 'string',
                        field: 'accident'
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
                        onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                          if (colVal >= 0) {
                            // 获取编辑行的值的 标准方案
                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                            const newRowData = {
                              ...rowData
                            }
                            if (rowData.bee >= 0 && rowData.cee >= 0) {
                              // newRowData.dee = Number(rowData.bee) * Number(colVal) * Number(rowData.cee);
                              newRowData.dee = this.FloatMulThree(colVal, rowData.cee, rowData.bee);
                              newRowData.riskLevel = (newRowData.dee < 100 && newRowData.dee >= 0) ? '一级' : ((newRowData.dee >= 100 && newRowData.dee < 400) ? '二级' : ((newRowData.dee >= 400 && newRowData.dee < 540) ? '三级' : ((newRowData.dee >= 540 && newRowData.dee < 720) ? '四级' : ((newRowData.dee >= 720) ? '五级' : ''))));
                            }
                            await tableBtnCallbackFn.setEditedRowData({
                              ...newRowData
                            });
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "<div>作业条件危险性评价(E)<span style='color: #ff4d4f'>*</span></div>",
                        dataIndex: 'bee',
                        width: 200,
                        key: 'bee',
                        tdEdit: true,
                      },
                      form: {
                        type: 'number',
                        field: 'bee',
                        required: true,
                        onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                          if (colVal >= 0) {
                            // 获取编辑行的值的 标准方案
                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                            const newRowData = {
                              ...rowData
                            }
                            if (rowData.lee >= 0 && rowData.cee >= 0) {
                              newRowData.dee = Number(rowData.lee) * Number(colVal) * Number(rowData.cee);
                              newRowData.dee = this.FloatMulThree(rowData.lee, rowData.cee, colVal);
                              newRowData.riskLevel = (newRowData.dee < 100 && newRowData.dee >= 0) ? '一级' : ((newRowData.dee >= 100 && newRowData.dee < 400) ? '二级' : ((newRowData.dee >= 400 && newRowData.dee < 540) ? '三级' : ((newRowData.dee >= 540 && newRowData.dee < 720) ? '四级' : ((newRowData.dee >= 720) ? '五级' : ''))));
                            }
                            await tableBtnCallbackFn.setEditedRowData({
                              ...newRowData
                            });
                          }
                        }
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
                        onChange: async (colVal, thisProps, tableBtnCallbackFn) => {
                          if (colVal >= 0) {
                            // 获取编辑行的值的 标准方案
                            const rowData = await tableBtnCallbackFn.getEditedRowData(false);
                            const newRowData = {
                              ...rowData
                            }
                            if (rowData.lee >= 0 && rowData.bee >= 0) {
                              newRowData.dee = Number(rowData.lee) * Number(colVal) * Number(rowData.bee);
                              newRowData.dee = this.FloatMulThree(rowData.lee, colVal, rowData.bee);
                              newRowData.riskLevel = (newRowData.dee < 100 && newRowData.dee >= 0) ? '一级' : ((newRowData.dee >= 100 && newRowData.dee < 400) ? '二级' : ((newRowData.dee >= 400 && newRowData.dee < 540) ? '三级' : ((newRowData.dee >= 540 && newRowData.dee < 720) ? '四级' : ((newRowData.dee >= 720) ? '五级' : ''))));
                            }
                            await tableBtnCallbackFn.setEditedRowData({
                              ...newRowData
                            });
                          }
                        }
                      }
                    },
                    {
                      table: {
                        title: "作业条件危险性评价(D)",
                        dataIndex: 'dee',
                        width: 200,
                        key: 'dee',
                      },
                      form: {
                        type: 'number',
                        field: 'dee',
                      }
                    },
                    {
                      table: {
                        title: "风险等级",
                        dataIndex: 'riskLevel',
                        key: 'riskLevel'
                      },
                      form: {
                        type: 'string',
                        field: 'riskLevel',
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
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 100
              },
              form: {
                type: 'textarea',
                field: 'remarks',
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
              isInTable: false,
              form: {
                label: '附件（项目危险源台账）',
                required: true,
                field: 'fileList',
                type: 'files',
                // accept: '.xls',
                fetchConfig: {
                  apiName: 'upload'
                },
                formItemLayout: {
                  labelCol: {
                    xs: { span: 8 },
                    sm: { span: 5 }
                  },
                  wrapperCol: {
                    xs: { span: 16 },
                    sm: { span: 19 }
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