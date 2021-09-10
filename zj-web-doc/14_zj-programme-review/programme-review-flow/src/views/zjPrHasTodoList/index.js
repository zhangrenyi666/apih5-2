import React, { Component } from "react";
import { basic } from '../modules/layouts';
import QnnTable from "../modules/qnn-table";
// import FlowFormByHYS from "./form";
// import FlowFormByDF from "../FlowByDF/form";
// import FlowFormByYY from "../FlowByYY/form";
// import FlowFormByWXYY from "../FlowByWXYY/form";

const flowConfig = {
  //流程专属配置
  workFlowConfig: {
    //流程特有配置
    title: ["meetingRoomTitle"], //标题字段
    apiNameByUpdate: "updateZjMeetingReservationsFlow",
    apiNameByGet: "getZjMeetingApplyFlowDetailByWorkId",
    flowId: "ZjMeetingRoom",
    formLink: {
      // zjPartyFeeUse: "FlowByzjPartyFeeUse",
      // zjYongYin: "FlowByzjYongYin",
      // zjYyOutSeal: "FlowByzjYyOutSeal",
      ZjMeetingRoom: "FlowByZjMeetingRoom"
    },
    //待办已办切换路由
    todo: "todoByZjMeetingRoom",
    hasTodo: "hasTodoByZjMeetingRoom"
  },
  drawerConfig: {
    width: '80%'
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
      flowId: "ZjMeetingRoom"
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
  actionBtns: [
    // {
    //   name: "Component",
    //   Component: FlowFormByDF,
    //   icon: "plus",
    //   type: "primary",
    //   label: "党费申请"
    // },
    // {
    //   name: "Component",
    //   Component: FlowFormByYY,
    //   icon: "plus",
    //   type: "primary",
    //   label: "用印申请"
    // },
    // {
    //   name: "Component",
    //   Component: FlowFormByWXYY,
    //   icon: "plus",
    //   type: "primary",
    //   label: "外协用印申请"
    // }
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
            // zjPartyFeeUse: FlowFormByDF,
            // zjYongYin: FlowFormByYY,
            // zjYyOutSeal: FlowFormByWXYY,
            // ZjMeetingRoom: FlowFormByHYS
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
      />
    );
  }
}

// export default index;
export default basic(index);
