import React, { Component } from "react";
import { basic } from '../modules/layouts';
import QnnTable from "../modules/qnn-table";
import FlowByThree from "../launch/flowByThree";
import FlowByTwo from "../launch/flowByTwo";
import FlowByOne from "../launch/flowByOne";
import ExtemalLaunchFlowApproval from "../externalLaunch/extemalLaunchFlowApproval";
// import FlowFormByWXYY from "../FlowByWXYY/form";

const flowConfig = {
  //流程专属配置
  workFlowConfig: {
    //流程特有配置
    title: ["applyUserId", "sendTime", "用印申请"], //标题字段
    apiNameByUpdate: "updateZjPrProgrammeLaunchFlow",
    apiNameByGet: "getZjPrProgrammeLaunchFlowDetailByWorkId",
    // flowId: "ZjMeetingRoom",
    // formLink: {
    //   // zjPartyFeeUse: "FlowByzjPartyFeeUse",
    //   // zjYongYin: "FlowByzjYongYin",
    //   // zjYyOutSeal: "FlowByzjYyOutSeal",
    //   ZjMeetingRoom: "FlowByZjMeetingRoom"
    // },
    // //待办已办切换路由
    // todo: "todoByZjMeetingRoom",
    // hasTodo: "hasTodoByZjMeetingRoom"
  },
  
  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}zjPrTodoList`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "zjProLaunchFlowByOne,zjProLaunchFlowByTwo,zjProLaunchFlowByThree,zjProExternalLaunchFlow"
    }
  },
  antd: {
    rowKey: function(row) {
      //---row.主键id
      return row.workId;
    },
    size:'small'
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
            zjProLaunchFlowByThree: FlowByThree,
            zjProLaunchFlowByTwo: FlowByTwo,
            zjProLaunchFlowByOne: FlowByOne,
            zjProExternalLaunchFlow: ExtemalLaunchFlowApproval,
          }
        }
      }
    },
    // {
    //   isInForm: false,
    //   table: {
    //     title: "发起人",
    //     dataIndex: "sendUserName",
    //     key: "sendUserName"
    //   }
    // },
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
        dataIndex: "flowStatus",
        key: "flowStatus"
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
        drawerConfig= {{
          width: '80%'
      }}
      />
    );
  }
}

// export default index;
export default basic(index);
