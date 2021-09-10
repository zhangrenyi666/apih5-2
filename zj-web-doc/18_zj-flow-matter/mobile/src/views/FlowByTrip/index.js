import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import FlowFormByTrip from "./form";
import FlowFormByLeave from "../FlowByLeave/form";
import FlowFormByOvertime from "../FlowByOvertime/form";

const flowConfig = {
		//流程专属配置
		workFlowConfig: {
			//后台定的字段
			title: ["", "overtimeTitle", "加班申请"], //标题字段
			apiNameByAdd: "addZjFlowTripApply",
			apiNameByUpdate: "updateZjFlowTripApply",
			apiNameByGet: "getZjFlowTripApplyDetail",
			flowId: "zjTripApply",
			formLink: {
				zjLeaveApply: "FlowByzjLeaveApply",
				zjWorkApply: "FlowByzjWorkApply",
				zjTripApply: "FlowByzjTripApply"
			},
			//待办已办切换路由
			todo: "todoByzjTripApply",
			hasTodo: "hasTodoByzjTripApply"
		},

		mobileModel: "flow",
		fetchConfig: {
			apiName: props => {
				var url = props.match.url;
				var myPublic = props.myPublic.appInfo.mainModule;
				if (url === `${myPublic}FlowByTripAwait`) {
					//待办
					return "getTodoList";
				} else {
					//已办
					return "getHasTodoList";
				}
			},
			otherParams: {
				flowId: "zjLeaveApply,zjWorkApply,zjTripApply"
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
		            	 Component: FlowFormByTrip,
		            	 icon: "plus",
		            	 type: "primary",
		            	 label: "出差申请"
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
		                        				  zjLeaveApply: FlowFormByLeave,
		                        				  zjWorkApply: FlowFormByOvertime,
		                        				  zjTripApply: FlowFormByTrip
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
