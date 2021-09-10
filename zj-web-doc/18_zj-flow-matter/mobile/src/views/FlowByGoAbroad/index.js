import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByGoAbroad from "./form";
import FlowFormByGoAbroadJg from "../FlowByGoAbroadJg/form";

const flowConfig = {
		//流程专属配置
		workFlowConfig: {
			//后台定的字段
			title: ["", "", "因私出国申请（各单位）"], //标题字段
			apiNameByAdd: "addZjFlowGoAbroadApply",
			apiNameByUpdate: "updateZjFlowGoAbroadApply",
			apiNameByGet: "getZjFlowGoAbroadApplyDetail",
			flowId: "zjGoAbroad",
			formLink: {
				zjGoAbroad: "FlowByzjGoAbroad",
				zjjgGoAbroad: "FlowByzjjgGoAbroad"
			},
			//待办已办切换路由
			todo: "todoByzjGoAbroad",
			hasTodo: "hasTodoByzjGoAbroad"
		},

		mobileModel: "flow",
		fetchConfig: {
			apiName: props => {
				var url = props.match.url;
				var myPublic = props.myPublic.appInfo.mainModule;
				if (url === `${myPublic}FlowByGoAbroadAwait`) {
					//待办
					return "getTodoList";
				} else {
					//已办
					//return "getHasTodoList";
					return "getTodoList";
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
		            	 Component: FlowFormByGoAbroad,
		            	 icon: "plus",
		            	 type: "primary",
		            	 label: "因私出国申请（各单位）"
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
