import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByZXQK from "./form";
import FlowFormByJDJH from "../FlowByJDJH/form";
let FlowConfig = {
  //流程专属配置
  workFlowConfig: {
    //后台定的字段
    title: ["applyUnitId", "title_sendTime", "执行情况回复"], //标题字段
    apiNameByUpdate: "updateZjJdjhSupPlanExecutive",
    apiNameByGet: "getZjJdjhSupPlanExecutiveDetail",
    flowId: "yearSupPlanExecutive",
    formLink: {
      yearSupPlanExecutive: "FlowByyearSupPlanExecutive",
      yearSupPlan: "FlowByyearSupPlan",
    },
    //待办已办切换路由
    todo: "todoByyearSupPlanExecutive",
    hasTodo: "hasTodoByyearSupPlanExecutive"
  },

  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}FlowByZXQKAwait`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "yearSupPlanExecutive"
    }
  },
  antd: {
    rowKey: function(row) {
      //---row.主键id
      return row.workId;
    }
  },
  drawerConfig: {
    width: "800px",
    maskClosable: true
  },
  infoAlert: function(selectedRows) {
    return "已选择 " + selectedRows.length + "项";
  },
  // labelConfig: {
  //   detail: false //抽屉详情左上角文字
  // },
  paginationConfig: {
    position: "bottom"
  },
  actionBtns: [
  ],
  formConfig: [
    {
      isInForm: false,
      isInSearch: true,
      table: {
        type: "string",
        title: "标题",
        dataIndex: "title",
        key: "title",
        drawerTitle: "审批处理",
        onClick: "Component",
        Component: {
          flowId: {
            yearSupPlanExecutive:FlowFormByZXQK,
            yearSupPlan:FlowFormByJDJH,
          }
        }
      },
      form: {
        type: "string",
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
      isInForm: false,
      table: {
        title: "发起时间",
        dataIndex: "sendTime",
        key: "sendTime",
        format: "YYYY-MM-DD HH:mm:ss"
      }
    },
    {
      isInForm: false,
      table: {
        title: "流程状态",
        dataIndex: "trackStatus",
        key: "trackStatus"
      }
    }
    // {
    //   isInForm: false,
    //   table: {
    //     title: "操作",
    //     btns:[
    //       {
    //         label:"处理",
    //         name:"Component",
    //         Component:FlowFormByYY
    //       }
    //     ]
    //   }
    // }
  ]
};

class index extends Component {
  render() {
    return (
      <QnnTable
        {...this.props}
        fetch={this.props.myFetch} 		 upload={this.props.myUpload}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...FlowConfig}
      />
      
    );
  }
}

export default index;
