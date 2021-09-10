import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
// import FlowFormByDF from "./form";
import FlowFormByYY from "../FlowByYY/form";
// import FlowFormByWXYY from "../FlowByWXYY/form";
// import FlowFormByHYS from "../FlowByHYS/form";
import FlowFormByXQQR from "../FlowByXQQR/form";

const flowConfig = {
  //流程专属配置
  workFlowConfig: {
    //流程特有配置
    title: ["信息化需求确认"], //标题字段
    apiNameByUpdate: "updateFlowNeedsConfirmAfterSubmit",
    apiNameByGet: "getFlowNeedsConfirmDetailByWorkId",
    flowId: "flowIdZj1",
    formLink: {
      flowIdZj1: "FlowByflowIdZj1"
    },
    //待办已办切换路由
    todo: "todoByflowIdZj1",
    hasTodo: "hasTodoByflowIdZj1"
  },

  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}FlowByXQQRAwait`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "flowIdZj1"
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
  paginationConfig: {
    position: "bottom"
  },
  actionBtns: [
    {
      name: "Component",
      Component: FlowFormByYY,
      icon: "plus",
      type: "primary",
      label: "用印申请"
    } 
  ],
  formConfig: [
    {
      isInForm: false,
      table: {
        title: "标题",
        dataIndex: "title",
        key: "title",
        onClick: "Component",
        Component: {
          flowId: {
            flowIdZj1: FlowFormByXQQR
          }
        }
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
  ]
};

class index extends Component {
  render() {
    return (
      <QnnTable
        {...this.props}
        fetch={this.props.myFetch}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...flowConfig}
      />
    );
  }
}

export default index;
