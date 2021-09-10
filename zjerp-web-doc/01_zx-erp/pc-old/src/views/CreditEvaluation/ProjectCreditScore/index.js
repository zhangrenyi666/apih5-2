import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import s from "./style.less";
import { message as Msg, Tabs } from "antd";
const { TabPane } = Tabs;
const config1 = {
  antd: {
    rowKey: function (row) {
      return row.id
    },
    size: 'small'
  },
  drawerConfig: {
    width: '1000px'
  },
  paginationConfig: {
    position: 'bottom'
  },
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
const config2 = {
  antd: {
    rowKey: function (row) {
      return row.id
    },
    size: 'small'
  },
  drawerConfig: {
    width: '1000px'
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 8 }
    },
    wrapperCol: {
      xs: { span: 16 },
      sm: { span: 16 }
    }
  }
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeKey: '1',
    }
  }
  tabsCallback = (activeKey) => {
    this.setState({
      activeKey,
    })
  }
  render() {
    // const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const { activeKey,id } = this.state;
    return (
      <div className={s.root}>
        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
          <TabPane tab="项目信用评价打分考核" key="1">
            <QnnTable
              {...this.props}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              wrappedComponentRef={(me) => {
                this.table1 = me;
              }}
              {...config1}
              fetchConfig={{
                apiName: 'getZxCrProjCreditScoreList'
              }}
              actionBtns={[
                {
                  name: 'add',//内置add del
                  icon: 'plus',//icon
                  type: 'primary',//类型  默认 primary  [primary dashed danger]
                  label: '新增',
                  formBtns: [
                    {
                      name: 'cancel', //关闭右边抽屉
                      type: 'dashed',//类型  默认 primary
                      label: '取消',
                    },
                    {
                      name: 'submit',//内置add del
                      type: 'primary',//类型  默认 primary
                      label: '保存',//提交数据并且关闭右边抽屉 
                      fetchConfig: {//ajax配置
                        apiName: 'addZxCrProjCreditScore',
                      },
                    }
                  ]
                },
                {
                  name: 'edit',//内置add del
                  icon: 'edit',//icon
                  type: 'primary',//类型  默认 primary  [primary dashed danger]
                  label: '修改',
                  formBtns: [
                    {
                      name: 'cancel', //关闭右边抽屉
                      type: 'dashed',//类型  默认 primary
                      label: '取消',
                    },
                    {
                      name: 'submit',//内置add del
                      type: 'primary',//类型  默认 primary
                      label: '保存',//提交数据并且关闭右边抽屉 
                      fetchConfig: {//ajax配置
                        apiName: 'updateZxCrProjCreditScore',
                      },
                      onClick: (obj) => {
                        obj.btnCallbackFn.clearSelectedRows();
                      }
                    }
                  ]
                },
                {
                  name: 'del',//内置add del
                  icon: 'delete',//icon
                  type: 'danger',//类型  默认 primary  [primary dashed danger]
                  label: '删除',
                  fetchConfig: {//ajax配置
                    apiName: 'batchDeleteUpdateZxCrProjCreditScore',
                  },
                }
              ]}
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
                    onClick: 'detail'
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
                    title: '评分细目',
                    dataIndex: 'scoreItem',
                    key: 'scoreItem',
                    width: 150,
                    tooltip:15
                  },
                  form: {
                    type: 'string',
                    required: true,
                    placeholder: '请输入',
                    spanForm: 12,
                  }
                },
                {
                  table: {
                    title: '标准分值',
                    dataIndex: 'standardScore',
                    key: 'standardScore',
                    width: 150
                  },
                  form: {
                    required: true,
                    type: 'number',
                    placeholder: '请输入',
                    spanForm: 12
                  }
                },
                {
                  table: {
                    title: '是否启用',
                    dataIndex: 'isUse',
                    key: 'isUse',
                    width: 120,
                    type: 'select'
                  },
                  form: {
                    required: true,
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: "否",
                        value: "0"
                      },
                      {
                        label: "是",
                        value: "1"
                      }
                    ],
                    allowClear: false,
                    showSearch: true,
                    placeholder: '请选择',
                    spanForm: 12
                  }
                },
                {
                  table: {
                    title: '最后编辑时间',
                    dataIndex: 'modifyTime',
                    key: 'modifyTime',
                    format:'YYYY-MM-DD HH:mm:ss',
                    width: 150
                  },
                  isInForm: false
                },
                {
                  table: {
                    title: '排序',
                    dataIndex: 'orderStr',
                    key: 'orderStr',
                    width: 150
                  },
                  form: {
                    type: 'string',
                    placeholder: '请输入',
                    spanForm: 12
                  }
                },
              ]}
            />
          </TabPane>
          <TabPane tab="项目信用评价不良行为" key="2">
            <QnnTable
              {...this.props}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              wrappedComponentRef={(me) => {
                this.table2 = me;
              }}
              {...config2}
              fetchConfig={{
                apiName: 'getZxCrProjCreditBadBehaList'
              }}
              actionBtns={[
                {
                  name: 'add',//内置add del
                  icon: 'plus',//icon
                  type: 'primary',//类型  默认 primary  [primary dashed danger]
                  label: '新增',
                  formBtns: [
                    {
                      name: 'cancel', //关闭右边抽屉
                      type: 'dashed',//类型  默认 primary
                      label: '取消',
                    },
                    {
                      name: 'submit',//内置add del
                      type: 'primary',//类型  默认 primary
                      label: '保存',//提交数据并且关闭右边抽屉 
                      fetchConfig: {//ajax配置
                        apiName: 'addZxCrProjCreditBadBeha',
                      },
                    }
                  ]
                },
                {
                  name: 'edit',//内置add del
                  icon: 'edit',//icon
                  type: 'primary',//类型  默认 primary  [primary dashed danger]
                  label: '修改',
                  formBtns: [
                    {
                      name: 'cancel', //关闭右边抽屉
                      type: 'dashed',//类型  默认 primary
                      label: '取消',
                    },
                    {
                      name: 'submit',//内置add del
                      type: 'primary',//类型  默认 primary
                      label: '保存',//提交数据并且关闭右边抽屉 
                      fetchConfig: {//ajax配置
                        apiName: 'updateZxCrProjCreditBadBeha',
                      },
                      onClick: (obj) => {
                        obj.btnCallbackFn.clearSelectedRows();
                      }
                    }
                  ]
                },
                {
                  name: 'del',//内置add del
                  icon: 'delete',//icon
                  type: 'danger',//类型  默认 primary  [primary dashed danger]
                  label: '删除',
                  fetchConfig: {//ajax配置
                    apiName: 'batchDeleteUpdateZxCrProjCreditBadBeha',
                  }
                }
              ]}
              formConfig={[
                {
                  isInTable: false,
                  form: {
                    field: 'id',
                    type: 'string',
                    initialValue: id,
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
                    onClick: 'detail'
                  },
                  form: {
                    required: true,
                    type: 'string',
                    placeholder: '请输入',
                    spanForm: 16
                  }
                },
                {
                  table: {
                    title: '是否启用',
                    dataIndex: 'isUse',
                    key: 'isUse',
                    width: 100,
                    type: 'select'
                  },
                  form: {
                    required: true,
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: "否",
                        value: "0"
                      },
                      {
                        label: "是",
                        value: "1"
                      }
                    ],
                    allowClear: false,
                    showSearch: true,
                    placeholder: '请选择',
                    spanForm: 16
                  }
                },
                {
                  table: {
                    title: '评分细目',
                    dataIndex: 'scoreItem',
                    key: 'scoreItem',
                  },
                  form: {
                    type: 'string',
                    placeholder: '请输入',
                    spanForm: 16,
                    required: true,
                  }
                },
                {
                  table: {
                    title: '最后编辑时间',
                    dataIndex: 'modifyTime',
                    key: 'modifyTime',
                    format:'YYYY-MM-DD HH:mm:ss',
                    width: 150
                  },
                  isInForm: false
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