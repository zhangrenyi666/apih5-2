import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByDF from "./form";
import FlowFormByYY from "../FlowByYY/form";
import FlowFormByWXYY from "../FlowByWXYY/form";
import FlowFormByHYS from "../FlowByHYS/form";

let FlowConfig = {
  //流程专属配置
  workFlowConfig: {
    //后台定的字段
    title: ["applyUnitId", "title_sendTime", "党费使用申请"], //标题字段
    apiNameByAdd: "addZjFlowPartyFeeUse",
    apiNameByUpdate: "updateZjFlowPartyFeeUse",
    apiNameByGet: "getZjFlowPartyFeeUseDetail",
    apiTitle: "getZjFlowPartyFeeUseTitle",
    flowId: "zjPartyFeeUse",
    formLink: {
      zjPartyFeeUse: "FlowByzjPartyFeeUse"
      // zjYongYin: "FlowByzjYongYin",
      // zjYyOutSeal: "FlowByzjYyOutSeal",
      // ZjMeetingRoom: "FlowByZjMeetingRoom"
    },
    //待办已办切换路由
    todo: "todoByzjPartyFeeUse",
    hasTodo: "hasTodoByzjPartyFeeUse"
  },

  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}FlowByDFAwait`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "zjPartyFeeUse"
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
    {
      name: "Component",
      Component: FlowFormByDF,
      icon: "plus",
      type: "primary",
      label: "党费申请",
      drawerTitle: "党费申请"
    },
    {
      name: "Component",
      Component: FlowFormByYY,
      icon: "plus",
      type: "primary",
      label: "用印申请",
      drawerTitle: "用印申请"
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
            zjPartyFeeUse: FlowFormByDF,
            zjYongYin: FlowFormByYY,
            zjYyOutSeal: FlowFormByWXYY,
            ZjMeetingRoom: FlowFormByHYS
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
        fetch={this.props.myFetch}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...FlowConfig}
      />
    );
  }
}

export default index;
