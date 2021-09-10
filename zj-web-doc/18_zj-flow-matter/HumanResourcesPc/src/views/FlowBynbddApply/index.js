import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Modal, Spin } from "antd";
import FlowByZjInHome from './form';
import FlowBywbddApply from '../FlowBywbddApply/form';
import FlowByCopynbddApply from '../FlowByCopynbddApply/form';
import FlowByjtdcApply from '../FlowByjtdcApply/form';
const confirm = Modal.confirm;
const flowConfig = {
	workFlowConfig: {
		title: ["","YearAndMonth",""],
		apiNameByAdd: "addXmFlowSeal",
		apiNameByUpdate: "updateXmFlowSeal",
		apiNameByGet: "getXmFlowSealDetail",
		flowId: "nbddApply",
		todo: "todoByTran",
		hasTodo: "hasTodoByTran"
	},
	mobileModel: "flow",
	isShowRowSelect: false,
	fetchConfig: {
		apiName: props => {
			var url = props.match.url;
			var myPublic = props.myPublic.appInfo.mainModule;
			if (url === `${myPublic}todoByTran`) {
				return "getTodoList";
			} else {
				return "getHasTodoList";
			}
		},
		otherParams: {
			flowId: "CopynbddApply,wbddApply,nbddApply,jtdcApply"
		}
	},
	antd: {
		rowKey: function (row) {
			return row.workId;
		},
		size: 'small'
	},
	drawerConfig: {
		width: "1400px",
		maskClosable: true
	},
	paginationConfig: {
		position: "bottom"
	},
	actionBtns: {
        apiName: "getSysMenuBtn",
        otherParams: function (obj) {
            var props = obj.Pprops;
            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
            return {
                menuParentId: curRouteData._value,
                tableField: "projectInfo"
            }
        }
    },
	isShowRowSelect: true,
	// actionBtns: [
	// 	{
	// 		name: 'diy',
	// 		icon: 'delete',
	// 		type: 'danger',
	// 		label: '删除',
	// 		disabled:"bind:_actionBtnNoSelected",
	// 		onClick: 'bind:delClick'
	// 	},
	// 	{
	// 		name: "Component",
	// 		Component: FlowByjtdcApply,
	// 		icon: "plus",
	// 		type: "primary",
	// 		label: "集团公司调出流程审批"
	// 	},
	// 	{
	// 		name: "Component",
	// 		Component: FlowByCopynbddApply,
	// 		icon: "plus",
	// 		type: "primary",
	// 		label: "集团公司调入流程审批"
	// 	},
	// 	{
	// 		name: "Component",
	// 		Component: FlowByZjInHome,
	// 		icon: "plus",
	// 		type: "primary",
	// 		label: "内部调动审批"
	// 	},
	// 	{
	// 		name: "Component",
	// 		Component: FlowBywbddApply,
	// 		icon: "plus",
	// 		type: "primary",
	// 		label: "外部调入（出）审批"
	// 	}
	// ],
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
						nbddApply: FlowByZjInHome,
						wbddApply:FlowBywbddApply,
						CopynbddApply: FlowByCopynbddApply,
						jtdcApply: FlowByjtdcApply
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
        this.state = {
            loadingSend: false
        }
    }
	render() {
		const { loadingSend } = this.state;
		return (
			<Spin spinning={loadingSend}><QnnTable
			{...this.props}
			fetch={this.props.myFetch} 
			 upload={this.props.myUpload}
			headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
			{...flowConfig}
			wrappedComponentRef={(me) => {
				this.table = me;
			}}
			componentsKey={{
				FlowByCopynbddApply: FlowByCopynbddApply,
				FlowByZjInHome: FlowByZjInHome,
				FlowBywbddApply: FlowBywbddApply,
				FlowByjtdcApply: FlowByjtdcApply
			}}
			method={{
				delClick: (obj) => {
					const { myFetch } = this.props;
					confirm({
						title: "确定删除么?",
						okText: "确认",
						cancelText: "取消",
						onOk: () => {
							this.setState({
								loadingSend:true
							 })
							myFetch('batchDeleteUpdateZjFlowTranInner',obj.selectedRows).then((success,message) => {
								if (success) {
									// Msg.success(message);
									
									this.table.qnnSetState({
										selectedRows: []
									}, () => {
										this.table.refresh();
										this.setState({
											loadingSend:false
										})
									})
									
								} else {
									this.table.qnnSetState({
										selectedRows: []
									}, () => {
										this.table.refresh();
										this.setState({
											loadingSend:false
										})
									})
									// Msg.error(message)
								}
							})
						},
						onCancel: () => {
							this.table.qnnSetState({
								selectedRows: []
							}, () => {
								this.table.refresh();
							})
						}
					})
					
				}
			}}
		/></Spin>
			
		);
	}
}

export default index;
