import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByHYSPlan from "./form";
const flowConfig = {
  //流程专属配置
  workFlowConfig: {
    //流程特有配置
    title: ["meetingRoomTitle"], //标题字段
    apiNameByUpdate: "updateZjMeetingReservationsFlow",
    apiNameByGet: "getZjMeetingApplyFlowDetailByWorkId",
    flowId: "ZjMeetingPlanRoom",
    formLink: {
      ZjMeetingPlanRoom: "FlowByZjMeetingPlanRoom"
    },
    //待办已办切换路由
    todo: "todoByZjMeetingPlanRoom",
    hasTodo: "hasTodoByZjMeetingPlanRoom"
  },

  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}FlowByHYSAwait`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "ZjMeetingRoom,ZjMeetingPlanSummary,ZjMeetingPlanFallInSummary,ZjMeetingPlanRoom"
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
  formConfig: [
    {
      isInForm: false,
      table: {
        title: "标题",
        dataIndex: "title",
        key: "title",
        onClick: "Component",
        // Component:FlowFormByDF,
        Component: {
          flowId: {
            ZjMeetingPlanRoom: FlowFormByHYSPlan
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
        upload={this.props.myUpload}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...flowConfig}
      />
    );
  }
}

export default index;
