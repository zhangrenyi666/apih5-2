import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import s from "./style.less";
import { message as Msg, Tabs } from "antd";
const { TabPane } = Tabs;
const config = {
  pageSize: 999,
  formItemLayout: {
    labelCol: {
      xs: { span: 6 },
      sm: { span: 6 }
    },
    wrapperCol: {
      xs: { span: 18 },
      sm: { span: 18 }
    }
  }
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeKey: '1',
      dataListTabOne: [],
      dataListTabTwo: []
    }
  }
  tabsCallback = (activeKey) => {
    this.setState({
      activeKey,
    })
  }
  render() {
    const { activeKey } = this.state;
    return (
      <div className={s.root}>
        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
          <TabPane tab="项目信用评价打分考核" key="1">
            <QnnForm
              fetch={this.props.myFetch}
              {...config}
              fetchConfig={() => {
                return {
                  apiName: 'getScoreStatistics',
                }
              }}
              wrappedComponentRef={(me) => {
                this.table = me;
              }}
              formConfig={[
                {
                  label: '标准总分',
                  field: "standardScore",
                  disabled: true,
                  initialValue: 0,
                  type: 'number',
                  span: 8
                },
                {
                  label: '加项总分',
                  field: "reduceScore",
                  type: 'number',
                  disabled: true,
                  initialValue: 0,
                  span: 8
                },
                {
                  label: '合计总分',
                  field: "totalScore",
                  disabled: true,
                  type: 'number',
                  initialValue: 0,
                  span: 8
                },
                {
                  type: 'qnnTable',
                  field: 'table1',
                  qnnTableConfig: {
                    fetchConfig: {
                      apiName: 'getZxCrProjCreditScoreList'
                    },
                    tableTdEdit: true,
                    antd: {
                      rowKey: 'id',
                      size: 'small'
                    },
                    rowDisabledCondition: (rowData) => {
                      return rowData.editOrDelete === '0'
                    },
                    paginationConfig: false,
                    pageSize: 999,
                    wrappedComponentRef: (me) => {
                      this.tableOne = me;
                    },
                    actionBtns: [
                      {
                        name: 'addRow',
                        icon: 'plus',
                        type: 'primary',
                        label: '新增',
                        addRowDefaultData: { addFlag: '1' }
                      },
                      {
                        name: 'del',//内置add del
                        icon: 'delete',//icon
                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                        label: '删除',
                        disabled: (obj) => {
                          let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                          let disabledLength = 0
                          for (var i = 0; i < SelectedRowsData.length; i++) {
                            if (SelectedRowsData[i].editOrDelete === '0') {
                              disabledLength++
                            }
                          }
                          if (disabledLength > 0 || SelectedRowsData.length === 0) return true
                        },
                        isRefreshTable: true,
                        fetchConfig: {//ajax配置
                          apiName: 'batchDeleteUpdateZxCrProjCreditScore',
                        },
                      }
                    ],
                    tableTdEditFetchConfig: async (obj) => {
                      let body = await obj.qnnTableInstance.getEditedRowData()
                      let isAdd = body.addFlag === '1'
                      if (isAdd) body.id = null
                      return {
                        apiName: isAdd ? 'addZxCrProjCreditScore' : 'updateZxCrProjCreditScore',
                        otherParams: { ...body },
                        success: ({ success, message }) => {
                          if (success) {
                            this.table.refresh()
                            Msg.success(message)
                          }
                        }
                      }
                    },
                    formConfig: [
                      {
                        isInTable: false,
                        form: {
                          field: 'id',
                          type: 'string',
                          hide: true,
                        }
                      },
                      {
                        table: {
                          title: '类别',
                          dataIndex: 'category',
                          key: 'category',
                          width: 150,
                          fixed: 'left',
                          type: 'select',
                          tdEdit: true,
                        },
                        form: {
                          type: 'select',
                          required: true,
                          optionConfig: {
                            label: 'label',
                            value: 'value',
                          },
                          optionData: [
                            { label: "标准项", value: "1" },
                            { label: "加分项", value: "2" }
                          ],
                          placeholder: '请选择',
                        }
                      },
                      {
                        table: {
                          title: '评价内容',
                          dataIndex: 'evalContent',
                          key: 'evalContent',
                          width: 150,
                          fixed: 'left',
                          tdEdit: true,
                        },
                        form: {
                          required: true,
                          type: 'string',
                          placeholder: '请输入',
                        }
                      },
                      {
                        table: {
                          title: '评分细目',
                          dataIndex: 'scoreItem',
                          key: 'scoreItem',
                          width: 250,
                          tdEdit: true,
                          tooltip: 15
                        },
                        form: {
                          type: 'string',
                          required: true,
                          placeholder: '请输入',
                        }
                      },
                      {
                        table: {
                          title: '标准分值',
                          dataIndex: 'standardScore',
                          key: 'standardScore',
                          tdEdit: true,
                          width: 150
                        },
                        form: {
                          required: true,
                          type: 'number',
                          placeholder: '请输入',
                        }
                      },
                      {
                        table: {
                          title: '是否启用',
                          dataIndex: 'isUse',
                          key: 'isUse',
                          width: 120,
                          type: 'select',
                          tdEdit: true,
                        },
                        form: {
                          required: true,
                          type: 'select',
                          optionConfig: {
                            label: 'label',
                            value: 'value',
                          },
                          optionData: [
                            { label: "否", value: "0" },
                            { label: "是", value: "1" }
                          ],
                          allowClear: false,
                          placeholder: '请选择',
                        }
                      },
                      {
                        table: {
                          title: '责任部门',
                          dataIndex: 'department',
                          key: 'department',
                          width: 150,
                          tdEdit: true,
                        },

                        form: {
                          required: true,
                          type: 'string',
                        }
                      },
                    ]

                  }
                }
              ]}
            />
          </TabPane>
          <TabPane tab="项目信用评价不良行为" key="2">
            <QnnTable
              {...this.props}
              fetch={this.props.myFetch}
              paginationConfig={false}
              fetchConfig={{
                apiName: 'getZxCrProjCreditBadBehaList'
              }}
              tableTdEdit={true}
              antd={{
                rowKey: 'id',
                size: 'small'
              }}
              rowDisabledCondition={(rowData) => {
                return rowData.editOrDelete === '0'
              }}
              pageSize={999} 
              wrappedComponentRef={(me) => {
                this.tableTwo = me;
              }}
              actionBtns={[
                {
                  name: 'addRow',//内置add del
                  icon: 'plus',//icon
                  type: 'primary',
                  label: '新增',
                  addRowDefaultData: { addFlag: '1' }
                },
                {
                  name: 'del',//内置add del
                  icon: 'delete',//icon
                  type: 'danger',//类型  默认 primary  [primary dashed danger]
                  label: '删除',
                  disabled: (obj) => {
                    let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                    let disabledLength = 0
                    for (var i = 0; i < SelectedRowsData.length; i++) {
                      if (SelectedRowsData[i].editOrDelete === '0') {
                        disabledLength++
                      }
                    }
                    if (disabledLength > 0 || SelectedRowsData.length === 0) return true
                  },
                  fetchConfig: {//ajax配置
                    apiName: 'batchDeleteUpdateZxCrProjCreditBadBeha',
                  }
                }
              ]}
              tableTdEditFetchConfig={async (obj) => {
                let body = await obj.qnnTableInstance.getEditedRowData()
                console.log(body);
                return {
                  apiName: body.addFlag === '1' ? 'addZxCrProjCreditBadBeha' : 'updateZxCrProjCreditBadBeha',
                  otherParams: { ...body },
                  success: ({ success, message }) => {
                    if (success) {
                      this.tableTwo.refresh()
                      Msg.success(message)
                    }
                  }
                }
              }}
              formConfig={[
                {
                  isInTable: false,
                  form: {
                    field: 'id',
                    type: 'string',
                    hide: true,
                  }
                },
                {
                  table: {
                    title: '评价内容',
                    dataIndex: 'evalContent',
                    key: 'evalContent',
                    width: 150,
                    fixed: 'left',
                    tdEdit: true
                  },
                  form: {
                    required: true,
                    type: 'string',
                    placeholder: '请输入',
                    spanForm: 12
                  }
                },
                {
                  table: {
                    title: '是否启用',
                    dataIndex: 'isUse',
                    key: 'isUse',
                    width: 100,
                    type: 'select',
                    tdEdit: true
                  },
                  form: {
                    required: true,
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      { label: "否", value: "0" },
                      { label: "是", value: "1" }
                    ],
                    allowClear: false,
                    placeholder: '请选择',
                    spanForm: 12
                  }
                },
                {
                  table: {
                    title: '评分细目',
                    dataIndex: 'scoreItem',
                    key: 'scoreItem',
                    tooltip: 30,
                    width: 300,
                    tdEdit: true
                  },
                  form: {
                    type: 'string',
                    placeholder: '请输入',
                    spanForm: 12,
                    required: true,
                  }
                },
              ]}
            />
          </TabPane>
        </Tabs>
      </div>
    );
  }
}

export default index;