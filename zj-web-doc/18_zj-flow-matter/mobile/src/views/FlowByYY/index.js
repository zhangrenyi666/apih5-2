import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByYY from "./form";
import FlowFormByDF from "../FlowByDF/form";
import FlowFormByWXYY from "../FlowByWXYY/form";
import FlowFormByHYS from "../FlowByHYS/form";

const flowConfig = {
  //流程专属配置
  workFlowConfig: {
    //后台定的字段
    title: ["applyUserId", "sendTime", "用印申请"], //标题字段
    apiNameByAdd: "addFlowSealInLaunch",
    apiNameByUpdate: "updateFlowSealAfterSubmit",
    apiNameByGet: "getZjYySealApplyDetailByFlowWorkId",
    flowId: "zjYongYin",
    formLink: {
      // zjPartyFeeUse: "FlowByzjPartyFeeUse",
      zjYongYin: "FlowByzjYongYin",
      zjYyOutSeal: "FlowByzjYyOutSeal"
      // ZjMeetingRoom: "FlowByZjMeetingRoom"
    },
    //待办已办切换路由
    todo: "todoByzjYongYin",
    hasTodo: "hasTodoByzjYongYin"
  },

  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}FlowByYYAwait`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "zjYongYin,zjYyOutSeal"
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
      Component: FlowFormByDF,
      icon: "plus",
      type: "primary",
      label: "党费申请"
    },
    {
      name: "Component",
      Component: FlowFormByYY,
      icon: "plus",
      type: "primary",
      label: "用印申请"
    },
    {
      name: "Component",
      Component: FlowFormByWXYY,
      icon: "plus",
      type: "primary",
      label: "外协用印申请"
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
        // Component:FlowFormByDF,
        Component: {
          flowId: {
            zjPartyFeeUse: FlowFormByDF,
            zjYongYin: FlowFormByYY,
            zjYyOutSeal: FlowFormByWXYY,
            ZjMeetingRoom: FlowFormByHYS
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
