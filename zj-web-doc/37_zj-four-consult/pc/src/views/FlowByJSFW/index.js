import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByJSFW from "./form";

const flowConfig = {
  //流程专属配置
  workFlowConfig: {
    //后台定的字段
    title: ["title"], //标题字段
    apiNameByAdd: "updateZjSjConsultTechnicalServiceFlow",
    apiNameByUpdate: "updateZjSjConsultTechnicalServiceFlow",
    apiNameByGet: "getZjSjConsultTechnicalServiceFlowDetails",
    flowId: "zjSjTechnicalService",

    todo: "todoByzjSjTechnicalService",
    hasTodo: "hasTodoByzjSjTechnicalService"
  },

  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}todoByzjSjTechnicalService`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "zjSjTechnicalService,CopyzjSjTechnicalService"
    }
  },
  antd: {
    rowKey: function (row) {
      //---row.主键id
      return row.workId;
    },
    size:'small'
  },
  drawerConfig: {
    width: "800px",
    maskClosable: true
  },
  paginationConfig: {
    position: "bottom"
  },
  isShowRowSelect:false,
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
            zjSjTechnicalService: FlowFormByJSFW,
            CopyzjSjTechnicalService: FlowFormByJSFW
          }
        }
      }
    },
    {
      isInForm: false,
      table: {
        title: "发起人",
        dataIndex: "sendUserName",
        key: "sendUserName",
        width:100,
      }
    },
    {
      isInForm: false,
      table: {
        title: "发起时间",
        dataIndex: "sendTime",
        key: "sendTime",
        format: "YYYY-MM-DD HH:mm:ss",
        width:150,
      }
    },
    {
      isInForm: false,
      table: {
        title: "流程状态",
        dataIndex: "flowStatus",
        key: "flowStatus",
        width:100,
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
