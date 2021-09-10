import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Modal, Spin } from "antd";
import FlowByhuKouApply from '../FlowByhuKouApply/form';
const confirm = Modal.confirm;
const flowConfig = {
	workFlowConfig: {
		title: ["","YearAndMonth",""],
		apiNameByAdd: "addXmFlowSeal",
		apiNameByUpdate: "updateXmFlowSeal",
		apiNameByGet: "getXmFlowSealDetail",
		flowId: "nbddApply",
		todo: "todoByhuKou",
		hasTodo: "hasTodoByhuKou"
	},
	mobileModel: "flow",
	isShowRowSelect: false,
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}todoByhuKou`) {
				return "getTodoList";
			} else {
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "huKouApply"
		}
	},
	antd: {
		rowKey: function (row) {
			return row.workId;
		},
		size: 'small'
	},
	drawerConfig: {
		width: "1000px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	// actionBtns: {
    //     apiName: "getSysMenuBtn",
    //     otherParams: function (obj) {
    //         var props = obj.Pprops;
    //         let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
    //         return {
    //             menuParentId: curRouteData._value,
    //             tableField: "projectInfo"
    //         }
    //     }
    // },
	isShowRowSelect: true,
	actionBtns: [
		{
			name: "Component",
			Component: FlowByhuKouApply,
			icon: "plus",
			type: "primary",
			label: "户口页/户口首页借用"
		},
	
	],
	formConfig: [
		{
			isInTable: false,
			form: {
				field: 'workId',
				type: 'string',
				hide:true,
			}
		},
		{
			isInForm: false,
			table: {
				title: "标题",
				dataIndex: "title",
				key: "title",
				onClick: "Component",
				fieldsConfig: {
                    type: "string",
                    field: "keyword"
                },
				Component: {
					flowId: {
						huKouApply:FlowByhuKouApply
					}
				}
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起人",
				width: 120,
				align:'center',
				noHaveSearchInput:true,
				dataIndex: "sendUserName",
				key: "sendUserName"
			}
		},
		{
			isInForm: false,
			table: {
				title: "发起时间",
				width:180,
				align:'center',
				noHaveSearchInput:true,
				dataIndex: "sendTime",
				key: "sendTime",
				format: "YYYY-MM-DD HH:mm:ss"
			}
		},
		{
			isInForm: false,
			table: {
				title: "流程状态",
				width: 120,
				align:'center',
				dataIndex: "flowStatus",
				key: "flowStatus",
				fieldsConfig: {
                    field:"flowStatus",
                    type: "select",
                    placeholder: "请选择...",
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'liuChengZhuangTai'
                        }
                    },
                    optionConfig: {
                        label: 'itemName',
                        value: 'itemId',
                    },
                },
			}
		}
	]
};

class index extends Component {
	constructor(props) {
        super(props);
        this.state = {}
    }
	render() {
		return (
			<QnnTable
			{...this.props}
			fetch={this.props.myFetch} 
			 upload={this.props.myUpload}
			headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
			{...flowConfig}
			wrappedComponentRef={(me) => {
				this.table = me;
			}}
			componentsKey={{
				FlowByhuKouApply:FlowByhuKouApply
			}}
			method={{
				
			}}
		/>
			
		);
	}
}

export default index;
