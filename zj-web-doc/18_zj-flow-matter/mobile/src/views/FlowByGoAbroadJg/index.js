import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByGoAbroadJg from "./form";
import FlowFormByGoAbroad from "../FlowByGoAbroad/form";

const flowConfig = {
		//流程专属配置
		workFlowConfig: {
			//后台定的字段
			title: ["", "", "因私出国申请（各单位）"], //标题字段
			apiNameByAdd: "addZjFlowGoAbroadApply",
			apiNameByUpdate: "updateZjFlowGoAbroadApply",
			apiNameByGet: "getZjFlowGoAbroadApplyDetail",
			flowId: "zjjgGoAbroad",
			formLink: {
				zjGoAbroad: "FlowByzjGoAbroad",
				zjjgGoAbroad: "FlowByzjjgGoAbroad"
			},
			//待办已办切换路由
			todo: "todoByzjjgGoAbroad",
			hasTodo: "hasTodoByzjjgGoAbroad"
		},

		mobileModel: "flow",
		fetchConfig: {
			apiName: props => {
				var url = props.match.url;
				var myPublic = props.myPublic.appInfo.mainModule;
				if (url === `${myPublic}FlowByGoAbroadJgAwait`) {
					//待办
					return "getTodoList";
				} else {
					//已办
					return "getHasTodoList";
				}
			},
			otherParams: {
				flowId: "zjGoAbroad,zjjgGoAbroad"
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
		            	 Component: FlowFormByGoAbroadJg,
		            	 icon: "plus",
		            	 type: "primary",
		            	 label: "因私出国申请（机关）"
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
		                        				   zjGoAbroad: FlowFormByGoAbroad,
		                        				   zjjgGoAbroad: FlowFormByGoAbroadJg
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
