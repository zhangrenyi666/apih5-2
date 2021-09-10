import React, { Component } from "react";
import { basic } from '../modules/layouts';
import QnnTable from "../modules/qnn-table";
import FlowByThree from "../launch/flowByThree";
import FlowByTwo from "../launch/flowByTwo";
import FlowByOne from "../launch/flowByOne";
import { Modal, Spin } from 'antd';
import IndexFlow from './indexFlow';
const flowConfig = {
  //流程专属配置
  workFlowConfig: {
    //流程特有配置
    title: ["applyUserId", "sendTime", "方案评审"], //标题字段
    apiNameByUpdate: "updateZjPrProgrammeLaunchFlow",
    apiNameByGet: "getZjPrProgrammeLaunchFlowDetailByWorkId",
  },
  isShowRowSelect: true,
  actionBtns: [
    {
      name: 'diydel',//内置add del
      icon: 'delete',//icon
      type: 'danger',//类型  默认 primary  [primary dashed danger]
      label: '方案流程删除',
      // fetchConfig: {//ajax配置
      // 	apiName: 'getTodoListByReport',
      // },
      onClick: (obj) => {
        const { fetch, msg } = obj.btnCallbackFn;
        if (obj.selectedRows.length) {
          var selectedRows = obj.selectedRows;
          fetch('batchDeleteZjPrProgrammeLaunchFlow', [...selectedRows], function ({ data, success, message }) {
            if (success) {
              msg.success(message)
              obj.btnCallbackFn.refresh();
            } else {
              msg.error(message);
            }
          })
        } else {
          msg.error('未选择任何项！');
        }


      }
    }
  ],
  mobileModel: "flow",
  fetchConfig: {//表格的ajax配置
    apiName: 'getTodoListByReport',
  },
  antd: {
    rowKey: function (row) {
      //---row.主键id
      return row.workId;
    },
    size: 'small'
  },
  drawerConfig: {
    width: "800px",
    maskClosable: true
  },
  //   infoAlert: function(selectedRows) {
  //     return "已选择 " + selectedRows.length + "项";
  //   },
  paginationConfig: {
    position: "bottom"
  },

};

class index extends Component {
  constructor() {
    super();
    this.state = {
      visible: false,
      clickCb: null
    }
  }
  handleCancel = () => {
    this.setState({ visible: false });
  }
  render() {
    const { visible } = this.state;
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          {...flowConfig}
          drawerConfig={{
            width: '80%'
          }}
          formConfig={[
            {
              isInForm: false,
              table: {
                title: "标题",
                dataIndex: "title",
                key: "title",
                onClick: "Component",
                Component: {
                  flowId: {
                    zjProLaunchFlowByThree: FlowByThree,
                    zjProLaunchFlowByTwo: FlowByTwo,
                    zjProLaunchFlowByOne: FlowByOne,
                  }
                }
              }
            },
            {
              isInForm: false,
              table: {
                title: "方案分级",
                dataIndex: "flowName",
                key: "flowName"
              }
            },
            {
              isInForm: false,
              table: {
                title: "流程状态",
                dataIndex: "trackStatus",
                key: "trackStatus"
              }
            },
            {
              table: {
                title: '标题',
                dataIndex: 'keyword',
                key: 'keyword',
              },
              isInTable: false,
              isInSearch: true,
              form: {
                type: 'string',
                placeholder: "请输入"
              }
            },
            {
              isInForm: false,
              table: {
                title: "发起人",
                dataIndex: "sendUserName",
                key: "sendUserName"
              }
            },
            {
              table: {
                title: '方案分级',
                dataIndex: 'flowIda',
                key: 'flowIda',
              },
              isInTable: false,
              isInSearch: true,
              form: {
                type: 'select',
                label: '方案分级',
                field: 'flowId', //唯一的字段名 ***必传
                placeholder: '请选择',
                required: true,
                multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                optionData: [//默认选项数据//可为function (props)=>array
                  {
                    value: 'zjProLaunchFlowByOne',
                    text: 'I 级施工方案'
                  },
                  {
                    value: 'zjProLaunchFlowByTwo',
                    text: 'Ⅱ级施工方案'
                  },
                  {
                    value: 'zjProLaunchFlowByThree',
                    text: 'Ⅲ级施工方案'
                  },

                ],
                optionConfig: {//下拉选项配置
                  label: 'text', //默认 label
                  value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
                },
              }
            },
            {
              table: {
                width: 100,
                title: ' ', //表头标题
                dataIndex: 'sss', //表格里面的字段
                key: 'sssss',//表格的唯一key 
                align: 'center',
                render: function (rowData) {
                  return '查看操作历史';
                },
                onClick: (obj) => {
                  console.log(obj);
                  this.setState({
                    visible: true,
                    clickCb: obj
                  })
                }
              },
              isInForm: false
            },
            // {
            //   isInForm: false,
            //   table: {
            //     title: "发起时间",
            //     dataIndex: "sendTime",
            //     key: "sendTime",
            //     format: "YYYY-MM-DD HH:mm:ss"
            //   },     
            // } 
          ]}
        />
        {visible ? <div>
          <Modal
            width={'90%'}
            style={{ paddingBottom: '0', top: '0' }}
            title="已审批方案"
            visible={visible}
            footer={null}
            onCancel={this.handleCancel}
            bodyStyle={{ padding: '10px', height: '90vh', overflow: 'auto' }}
            centered={true}
            wrapClassName={'modals'}
          >
            <IndexFlow clickCb={this.state.clickCb} {...this.props}/>
          </Modal>
        </div> : null}
      </div>

    );
  }
}

// export default index;
export default basic(index);
