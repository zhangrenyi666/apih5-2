import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByZjHwzcZCGL from "../FlowByZjHwzcZCGL/form";
import FlowFormByZjHwzcZCGLForPro from "../FlowByZjHwzcZCGLForPro/form";
import FlowFormByZjHwzcZCYS from "../FlowByZjHwzcZCYS/form";
import FlowFormByZjHwzcZCDBForPro from "../FlowByZjHwzcZCDBForPro/form";
import FlowFormByZjHwzcZCDBForCom from "../FlowByZjHwzcZCDBForCom/form";
import FlowFormByZjHwzcZCBFForPro from "../FlowByZjHwzcZCBFForPro/form";
import FlowFormByZjHwzcZCBFForCom from "../FlowByZjHwzcZCBFForCom/form";


const flowConfig = {
  //流程专属配置
   workFlowConfig: {
    //后台定的字段
    apiNameByAdd: "addFlowAssetsInLaunch",
    apiNameByUpdate: "updateFlowAssetsAfterSubmit",
    apiNameByGet: "getAssetsDetails",
    apiTitle: "getZjHwAssetsFlowTitle",
    flowId: "cropPurchasingProcess",
    formLink: {
      cropPurchasingProcess: "FlowBycropPurchasingProcess",
	  projectDepPurchasingProcess: "FlowByprojectDepPurchasingProcess",
	  purchasingSystem: "FlowBypurchasingSystem",
	   projectTransfer: "FlowByprojectTransfer",
	   	  cropTransfers: "FlowBycropTransfers",
		  projectScrap: "FlowByprojectScrap",
		  cropScrap: "FlowBycropScrap"
    },
    //待办已办切换路由
    todo: "todoBycropPurchasingProcess",
    hasTodo: "hasTodoBycropPurchasingProcess"
    // ...window.configs.workFlowConfig,
  },

  mobileModel: "flow",
  fetchConfig: {
    apiName: props => {
      var url = props.match.url;
      var myPublic = props.myPublic.appInfo.mainModule;
      if (url === `${myPublic}FlowByZjHwzcZCGLAwait`) {
        //待办
        return "getTodoList";
      } else {
        //已办
        return "getHasTodoList";
      }
    },
    otherParams: {
      flowId: "cropPurchasingProcess,projectDepPurchasingProcess,purchasingSystem,projectTransfer,cropTransfers,projectScrap,cropScrap"
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
      Component: FlowFormByZjHwzcZCGL,
      icon: "plus",
      type: "primary",
      label: "海威资产购置申请"
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
			cropPurchasingProcess:FlowFormByZjHwzcZCGL
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
        fetch={this.props.myFetch} 		 upload={this.props.myUpload}
        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
        {...flowConfig}
      />
    );
  }
}

export default index;
