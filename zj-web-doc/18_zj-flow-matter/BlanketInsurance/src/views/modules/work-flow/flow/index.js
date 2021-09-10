import React, { Component } from "react";
import { createForm, createFormField } from "rc-form";
import { goBack, push } from "connected-react-router"; //, push
import { Modal, Toast, List, ActivityIndicator, Checkbox as Checkbox2, SegmentedControl } from "antd-mobile";
import { Form } from "@ant-design/compatible";
import "@ant-design/compatible/assets/index.css";
import { Button, notification } from "antd";
import moment from "moment";
// import PullPersionMobile from "../../pullPersionMobile";
import PullPersionMobile from "pull-person-mobile";
// import PullPersion from "../../pullPersion";
import PullPersion from "pull-person";
import styles from "./style/index.less";
// import QnnForm from "../../qnn-table/qnn-form";
import QnnForm from "qnn-form";
// moment.locale('zh-cn');

new SegmentedControl({ selectedIndex: 0 });
const CheckboxItem = Checkbox2.CheckboxItem;
const alert = Modal.alert;

const FormItem = Form.Item;

//正常使用时的按钮栅格比例
let formTailLayout = {
	labelCol: { span: 5 },
	wrapperCol: { span: 8, offset: 6 },
};
const isMobile = () => {
	if (navigator.userAgent.match(/Android/i) || navigator.userAgent.match(/webOS/i) || navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/BlackBerry/i) || navigator.userAgent.match(/Windows Phone/i)) {
		return true;
	} else {
		return false;
	}
};

class FlowForm extends Component {
	render() {
		const { getFieldsValue } = this.props.form;
		let {
			updateRootBody,
			isInQnnTable,
			flowFormColumn = [],
			// flowHistoryList = [],
			// delApiBody,
			getQnnForm,
			parentQnnForm,
			// isHaveDoc
			qnnFormInitialValues = {},
		} = this.props;
		let apiBody = getFieldsValue()["apiBody"] || {};
		const setLinkageField = (obj) => {
			let { children, form } = obj;
			const { field } = form;
			let fieldName = "";
			if (field.indexOf("apiBody.") !== -1) {
				fieldName = field;
			} else {
				fieldName = field ? "apiBody." + field.toString() : "";
			}
			form.field = fieldName;
			if (children) {
				setLinkageField(children);
			}
			return obj;
		};

		//处理数据
		flowFormColumn = flowFormColumn.map((item = {}, i) => {
			const { field, type } = item;
			// const _item = { ...item };
			if (apiBody[field]) {
				//将moment时间格式处理为时间戳  下拉值为数组改为string
				if (type === "datetime") {
					apiBody[field] = moment(apiBody[field]).valueOf();
				} else if (type === "Cascader" || type === "cascader") {
					apiBody[field] = apiBody[field].join(",");
				}
			}

			//给所有字段名加上apiBody.
			let fieldName = "";
			if (field && field.indexOf("apiBody.") !== -1) {
				fieldName = field;
			} else {
				fieldName = field ? `apiBody.${field}` : "";
			}
			item.field = fieldName;

			if (type === "linkage") {
				//联动
				item.children = setLinkageField(item.children);
			} else if (type === "qnnForm") {
				apiBody[`${field.replace(/(apiBody.)/g, "")}`] = apiBody[`${field.replace(/(apiBody.)/g, "")}_Block`];
				delete apiBody[`${field.replace(/(apiBody.)/g, "")}_Block`];
			}
			if (!item.formItemLayout) {
				item.formItemLayout = {
					labelCol: {
						xs: { span: 7 },
						sm: { span: 6 },
					},
					wrapperCol: {
						xs: { span: 17 },
						sm: { span: 18 },
					},
				};
			}
			return item;
		});

		let rootBody = { apiBody: JSON.stringify(apiBody) };
		if (apiBody["opinionContent"]) {
			rootBody["opinionContent"] = apiBody["opinionContent"];
		}
		updateRootBody({ ...rootBody });
		const QnnFormConfig = {
			...this.props,
			initialValues: qnnFormInitialValues,
			//清空一些参数
			children: null,
			data: {},
			form: null,

			// form: this.props.form,
			fetch: this.props.myFetch,
			headers: { token: this.props.loginAndLogoutInfo.loginInfo.token },
			formConfig: flowFormColumn.map((item) => {
				//删除掉form组件不需要的标识
				let { opinionField, qnnDisabled, addShow, ...fieldConfig } = item;
				return {
					...fieldConfig,
				};
			}),
			style: {
				// paddingBottom: isHaveDoc ? "0px" : '52px'
				// padding:this.props.tabs.length ? '0px' : '24px',
				paddingBottom: "52px",
				paddingTop: "0px",
			},
			fetchConfig: {},
			wrappedComponentRef: (me) => {
				if (me) {
					this.qnnForm = me;
					getQnnForm && getQnnForm(me);
					parentQnnForm && parentQnnForm(me);
				}
			},
			drawerConfig: {
				bodyStyle: {
					paddingTop: "24px",
				},
			},
		};
		// console.log('表单配置：', QnnFormConfig)
		if (flowFormColumn.length > 0) {
			return (
				<div className={isInQnnTable || isMobile() ? styles.containerByQnnTable : styles.container}>
					{this.props.children}
					<QnnForm {...QnnFormConfig} />
				</div>
			);
		} else {
			return <div />;
		}
	}
}

const FlowButtonGroup = (props) => {
	let { flowButtons, clickFlowButton = () => {}, backTop = () => {}, createOpenFlow = () => {}, refresh = () => {}, isInQnnTable } = props;
	flowButtons = flowButtons || [];
	const len = flowButtons.length || 0;
	const btns = flowButtons.map((item, index) => {
		let style = { right: 64 * (len - 1 - index) + 20 + "px" },
			// label = "...",
			pcLabel = "...";
		const { buttonClass } = item;
		switch (buttonClass) {
			case "backTop":
				style["background"] = "#fff";
				style["color"] = "#108ee9";
				// label = (
				//   <div
				//     style={{
				//       width: "28px",
				//       height: "28px",
				//       background:
				//         "url(http://cdn.apih5.com/icon/arrowup.svg) center center /  28px 28px no-repeat"
				//     }}
				//   />
				// );
				break;
			case "refresh":
				style["background"] = "#fff";
				style["color"] = "#108ee9";
				// label = "刷";
				pcLabel = "刷新";
				break;
			case "goBack":
				style["background"] = "#fff";
				style["color"] = "greenyellow";
				// label = "返";
				pcLabel = "返回";
				break;
			case "createOpenFlow":
				style["background"] = "green";
				// label = "创";
				pcLabel = "发起";
				break;
			case "com.horizon.wf.action.ActionReaded":
				// label = "阅";
				pcLabel = "已阅";
				break;
			case "com.horizon.wf.action.ActionReject":
				style["background"] = "red";
				// label = "退";
				pcLabel = "退回";
				break;
			case "com.horizon.wf.action.ActionSubmitForReject":
				style["background"] = "greenyellow";
				// label = "回";
				pcLabel = "退回提交";
				break;

			case "com.horizon.wf.action.ActionRejectByJump":
				style["background"] = "red";
				pcLabel = "任意退回";
				break;
			case "com.horizon.wf.action.ActionStop":
				style["background"] = "red";
				pcLabel = "删除";
				break;

			case "com.horizon.wf.action.ActionSubmit":
				style["background"] = "orange";
				// label = "提";
				pcLabel = "提交";
				break;
			case "com.horizon.wf.action.ActionSave":
				style["background"] = "#108ee9";
				pcLabel = "保存";
				break;
			case "preFlow":
				style["background"] = "#108ee9";
				pcLabel = "在线预览";
				break;
			case "exprot":
				style["background"] = "#108ee9";
				pcLabel = "导出";
				break;
			default:
				style["background"] = "darkgrey";
				// label = "未";
				pcLabel = "取消";
				break;
		}
		return (
			<Button
				key={index}
				style={{ marginLeft: index === 0 ? "0px" : "8px" }}
				type="primary"
				onClick={(e) => {
					e.preventDefault();
					clickFlowButton(item, {
						backTop,
						createOpenFlow,
						refresh,
					});
				}}
			>
				{pcLabel}
			</Button>
		);
	});
	//按钮布局在移动的 pc端 和qnn-table中都不一样
	formTailLayout = props.formTailLayout || formTailLayout;
	if (isInQnnTable) {
		return (
			<FormItem {...formTailLayout} className={styles.qnnTableFlowBtn}>
				{btns}
			</FormItem>
		);
	}

	if (isMobile()) {
		return <div className={styles.mobileBtnContainer}>{btns}</div>;
	} else {
		return (
			<FormItem {...formTailLayout} className={styles.pcBtnsContainer}>
				{btns}
			</FormItem>
		);
	}
};
const clickFlowButton = function(item = {}, option = {}) {
	const { dispatch, isInQnnTable, btnCallbackFn = {} } = this.props;
	const { closeDrawer } = btnCallbackFn;
	const { buttonId, buttonClass, buttonName, printUrl, buttonFun = () => {} } = item;
	const { backTop = () => {}, createOpenFlow = () => {}, refresh = () => {} } = option;
	const operateObj = {
		operate: buttonId,
		operateClazz: buttonClass,
		operateText: buttonName,
		operateFlag: 1,
	};
	switch (buttonClass) {
		case "backTop":
			backTop();
			break;
		case "refresh":
			refresh();
			break;
		case "goBack":
			dispatch(goBack());
			break;
		case "createOpenFlow":
			createOpenFlow();
			break;
		case "com.horizon.wf.action.ActionSave":
			alert("暂存", "", [
				{ text: "取消", onPress: () => console.log("cancel") },
				{
					text: "确定",
					onPress: () => {
						actionFlow.bind(this)({
							operateObj,
							visible: false,
							close: true,
						});
					},
				},
			]);
			break;
		case "com.horizon.wf.action.ActionReaded":
			alert("您确认已阅该信息？", "", [
				{ text: "取消", onPress: () => console.log("cancel") },
				{
					text: "确定",
					onPress: () => {
						actionFlow.bind(this)({
							operateObj,
							visible: false,
							close: true,
						});
					},
				},
			]);
			break;
		case "com.horizon.wf.action.ActionReject":
			alert("退回", "确定退回么？", [
				{ text: "取消", onPress: () => console.log("cancel") },
				{
					text: "确定",
					onPress: () => {
						actionFlow.bind(this)({
							noVerifyField: true, //不验证表单字段
							operateObj,
							visible: false,
							close: true,
						});
					},
				},
			]);
			break;
		case "com.horizon.wf.action.ActionRejectByJump":
			actionFlow.bind(this)({ operateObj, visible: true });
			break;
		case "com.horizon.wf.action.ActionStop":
			alert("删除", "确定删除么？", [
				{ text: "取消", onPress: () => console.log("cancel") },
				{
					text: "确定",
					onPress: () => {
						actionFlow.bind(this)({
							noVerifyField: true, //不验证表单字段
							operateObj,
							visible: false,
							close: true,
						});
					},
				},
			]);
			break;
		case "com.horizon.wf.action.ActionSubmitForReject":
			alert("退回提交", "确定退回提交么？", [
				{
					text: "取消",
					onPress: () => {},
				},
				{
					text: "确定",
					onPress: () => {
						actionFlow.bind(this)({
							operateObj,
							visible: false,
							close: true,
						});
					},
				},
			]);
			break;
		case "com.horizon.wf.action.ActionSubmit":
			actionFlow.bind(this)({ operateObj, visible: true });
			break;

		case "preFlow":
			window.location.href = printUrl;
			break;

		case "exprot":
			if (printUrl) {
				window.location.href = printUrl;
			} else {
				buttonFun();
				if (isInQnnTable) {
					closeDrawer(false, () => {
						refresh();
					});
				} else {
					dispatch(goBack());
				}
			}
			break;
		default:
			alert("未知");
			break;
	}
};
const actionFlow = function(actionData, callback) {
	const { form, dispatch, match, myFetch, isInQnnTable, myPublic = {}, btnCallbackFn = {}, actionParamsFormat = (obj) => obj, workFlowConfig = {}, noForm, closeFn } = this.props;
	const { pushNode, closeedToUrl } = workFlowConfig;
	const { closeDrawer, refresh } = btnCallbackFn;
	const { androidApi } = myPublic;
	const { setFieldsValue, getFieldValue, validateFieldsAndScroll } = form;
	const { pushNodeData = [], startSettingFlag, uploadRes, isHaveDoc, docFieldName } = this.state;
	const operateObj = getFieldValue("operateObj");
	let curNextNodes = getFieldValue("curNextNodes") || [];
	const {
		params: { flag = "" },
	} = match;
	this.qnnForm.getValues(!actionData.noVerifyField).then((values) => {
		const { operateObj: newOperateObj = {}, selectId, visible = true, close = false } = actionData;

		let { actionNextNodes = [] } = actionData;

		//需要删除掉追加的按钮不然后台报系统异常
		if (pushNodeData.length) {
			actionNextNodes.splice(pushNodeData.length, actionNextNodes.length);
		}

		const _actionData = {
			...operateObj,
			...newOperateObj,
			nextNodes: actionNextNodes,
		};

		if (_actionData.nextNodes && _actionData.nextNodes.length) {
			for (let i = 0; i < _actionData.nextNodes.length; i++) {
				if (_actionData.nextNodes[i]?.authors) {
					if (_actionData.nextNodes[i].authors.Author) {
						_actionData.nextNodes[i].authors.Author.initAuthorApih5 = [];
					}
					if (_actionData.nextNodes[i].authors.Reader) {
						_actionData.nextNodes[i].authors.Reader.initAuthorApih5 = [];
					}
					if (_actionData.nextNodes[i].authors.SecondAuthor) {
						_actionData.nextNodes[i].authors.SecondAuthor.initAuthorApih5 = [];
					}
				}
			}
		}

		const actionBody = {
			opinionContent: values?.apiBody?.opinionContent,
			...this.rootBody,
			apiBody: JSON.stringify(values.apiBody),
			actionData: JSON.stringify(_actionData),
		};

		Toast.loading("loading...", 0);
		const newBody = actionParamsFormat(actionBody, { ...this.props });

		//红头文件
		if (isHaveDoc && newBody.apiBody) {
			newBody.apiBody = JSON.parse(newBody.apiBody);
			newBody.apiBody[docFieldName] = uploadRes;
			newBody.apiBody = JSON.stringify(newBody.apiBody);
		}

		myFetch("actionFlow", newBody).then(({ success, message, data }) => {
			Toast.hide();
			if (success) {
				const {
					operate,
					operateClazz,
					operateText,
					// operateFlag,
					nextNodes: callbackNextNodes,
					// apih5FlowStatus
				} = data;

				if (!selectId) {
					curNextNodes = callbackNextNodes;
				} else {
					curNextNodes = actionNextNodes.map((item, index) => {
						if (item.selectId === selectId) {
							for (let i = 0; i < callbackNextNodes.length; i++) {
								if (callbackNextNodes[i].selectId === selectId) {
									item.authors = callbackNextNodes[i].authors;
									break;
								}
							}
						}
						return item;
					});
				}

				if (close) {
					if (noForm) {
						closeFn();
						return;
					}

					if (flag) {
						window.location.href = "http://weixin.fheb.cn/ZJOA/initDocWorkListMsg.do?docType=1&flg=1";
						return;
					}
					//需要关闭的话必须先执行回调不然关闭后回调没法执行了
					//回调
					if (callback) {
						callback(data);
					}
					if (androidApi) {
						androidApi.closeStartActivity();
					} else if (isInQnnTable) {
						closeDrawer(false, () => {
							refresh();
						});
					} else {
						dispatch(goBack());
					}

					//有指定地址直接跳转
					if (closeedToUrl) {
						window.location.href = closeedToUrl;
						return;
					}
				} else {
					const _value = {
						operateObj: {
							operate,
							operateClazz,
							operateText,
							operateFlag: "1",
						},
						curNextNodes: curNextNodes.concat(pushNodeData),
						visible,
					};
					setFieldsValue({ ..._value });

					//正常回调
					if (callback) {
						callback(data);
					}
				}

				if (pushNode && !pushNodeData.length && startSettingFlag) {
					//请求追加数据
					this.getPushNodeData.bind(this)(data);
				}
			} else {
				Toast.fail(message, 2, () => {
					androidApi && androidApi.closeStartActivity();
				});
			}
		});
	});
	// });
};

class FlowSelectModal extends Component {
	// UNSAFE_componentWillMount() {
	// const { getFieldDecorator } = this.props.form;
	// getFieldDecorator("visible", { initialValue: false })(<Input hidden />)
	// getFieldDecorator("operateObj", { initialValue: {} })
	// getFieldDecorator("curNextNodes", { initialValue: [] })
	// }
	onClose = () => {
		if (this.props.onCancel) {
			this.props.onCancel();
		} else {
			const { setFieldsValue } = this.props.form;
			setFieldsValue({ visible: false });
		}
	};
	// onWrapTouchStart = e => {
	//   if (!/iPhone|iPod|iPad/i.test(navigator.userAgent)) {
	//     return;
	//   }
	//   const pNode = closest(e.target, ".am-modal-content");
	//   if (!pNode) {
	//     e.preventDefault();
	//   }
	// };

	render() {
		const {
			actionFlow: _actionFlow,
			myFetch,
			form = {},
			workFlowConfig: { pushNode },
			pushNodeData, 
			flowId,
			workId,
			noForm,
			closeFn,
			dispatch,
			// formLink,
            qnnFormInitialValues,
            parameterName = '',
            loginAndLogoutInfo,
            rootBody,
			myPublic: {
				appInfo: { mainModule },
			},
		} = this.props;
		const { getFieldValue, validateFields, setFieldsValue } = form;
		const visible = getFieldValue("visible");
		let curNextNodes = getFieldValue("curNextNodes") || [];
		const operateObj = getFieldValue("operateObj") || {};

        const topId = qnnFormInitialValues?.apiBody?.[parameterName] || loginAndLogoutInfo?.loginInfo?.userInfo?.curCompany?.departmentId || "";
		// console.log(workId)
		return (
			<Modal
				wrapClassName={"w-flowModal"}
				className="workFlow-myModal"
				visible={visible}
				maskClosable={false}
				title="请选择流程"
				footer={[
					{
						text: "取消",
						onPress: () => {
							this.onClose();
							if (noForm) {
								closeFn();
							}

							if (workId === "add" && isMobile()) {
								//新增点击取消直接回列表页面
								dispatch(push(`${mainModule}${this.props.todo}`));
							}
						},
					},
					{
						text: "确定",
						onPress: () => {
							const { setFieldsValue } = this.props.form;
							//判断是否选人
							let selectPersoned = false;
							for (let i = 0; i < curNextNodes.length; i++) {
								let { Author = {}, SecondAuthor = {}, Reader = {} } = curNextNodes[i].authors;
								let isSelectA = Author.selectAuthorNewApih5 && Author.selectAuthorNewApih5.length;
								let isSelectS = SecondAuthor.selectAuthorNewApih5 && SecondAuthor.selectAuthorNewApih5.length;
								let isSelectR = Reader.selectAuthorNewApih5 && Reader.selectAuthorNewApih5.length;
								if (isSelectA || isSelectS || isSelectR) {
									//选择了人
									selectPersoned = true;
								}
							}
							//结束时nodeId = End(n)不需要选择人也行
							const endExg = /^End(\d{1,9})$/;
							const foo = curNextNodes.filter((item) => {
								return item.isSelect === "true" || item.isSelect === true;
							});
							let nodeId = null;
							if (foo.length) {
								nodeId = foo[0].nodeId;
							}
							if (!selectPersoned && !endExg.test(nodeId) && operateObj.operateClazz !== "com.horizon.wf.action.ActionRejectByJump") {
								Toast.info("请选择审批人员");
								return;
							}
							if (operateObj.operateClazz === "com.horizon.wf.action.ActionRejectByJump" && !nodeId) {
								Toast.info("请选择节点！");
								return;
							}
							setFieldsValue({ visible: false });
							alert("提交", "确定么？", [
								{
									text: "取消",
									onPress: () => {
										setFieldsValue({ visible: true });
									},
								},
								{
									text: "确定",
									onPress: () => {
										let _pushNodeData = [];
										let _curNextNodes = [...curNextNodes];
										if (pushNode) {
											//只提交原本的节点
											_curNextNodes = curNextNodes.filter((_, i) => i < curNextNodes.length - pushNodeData.length);

											//只提交追加的节点
											_pushNodeData = curNextNodes.filter((_, i) => i >= curNextNodes.length - pushNodeData.length);
										}
										_actionFlow(
											{
												actionNextNodes: _curNextNodes,
												visible: false,
												close: true,
												operateObj: {
													operateFlag: "0",
												},
											},
											(res) => {
												// console.log(workId)
												if (pushNode && workId === "add" && _pushNodeData.length) {
													//需要去请求别的接口把追加的节点选中的人提交给后台
													const params = {
														apih5FlowId: flowId,
														apih5WorkId: res.workId,
														apih5FlowName: res.flowName,
														nextNodes: _pushNodeData,
													};

													myFetch("addBaseFlowStartSettingByFlow", {
														...params,
													}).then(({ success, message, data, code }) => {
														if (success) {
															let curNextNodes = getFieldValue("curNextNodes");
															curNextNodes = curNextNodes.concat(data);
															setFieldsValue({
																curNextNodes,
															});
															this.setState({
																pushNodeData: data,
															});
														} else {
															if (code === "-1") {
																notification.error({
																	message: "系统遇到问题，请联系运维人员",
																	description: message,
																	duration: 3,
																});
															} else {
																notification.warn({
																	message: "提示",
																	description: message,
																	duration: 3,
																});
															}
														}
													});
												}
											}
										);
									},
								},
							]);
						},
					},
				]}
				// wrapProps={{ onTouchStart: this.onWrapTouchStart }}
				style={{
					maxWidth: "500px",
				}}
			>
				<List>
					{curNextNodes.map((item, index) => {
						const { selectId, selectName, isSelect, authors, isDone, nodeGroup } = item;
						const checked = isSelect === "true";
						return (
							<CheckboxItem
								checked={checked}
								onChange={() => {
									if (JSON.stringify(authors) === "{}" && operateObj.operateClazz !== "com.horizon.wf.action.ActionRejectByJump") {
										validateFields((err) => {
											if (!err) {
												let actionNextNodes = curNextNodes.map((item3) => {
													if (item3.selectId === selectId) {
														if (nodeGroup) {
															item3.isSelect = "true";
														} else {
															item3.isSelect = item3.isSelect === "true" ? "false" : "true";
														}
													} else {
														if (nodeGroup) {
															item3.isSelect = "false";
														}
													}
													return item3;
												});
												_actionFlow({
													actionNextNodes,
													selectId,
												});
											}
										});
									} else {
										validateFields((err) => {
											if (!err) {
												let actionNextNodes = curNextNodes.map((item3) => {
													if (item3.selectId === selectId) {
														if (nodeGroup) {
															item3.isSelect = "true";
														} else {
															item3.isSelect = item3.isSelect !== "true" || curNextNodes.length === 1 ? "true" : "false";
														}
													} else {
														if (nodeGroup) {
															item3.isSelect = "false";
														}
													}
													return item3;
												});
												setFieldsValue({
													curNextNodes: actionNextNodes,
												});
											}
										});
									}
								}}
								key={selectId}
							>
								<span>
									{isDone === "true" ? <span style={{ color: "#108ee9" }}>{"[已]"}</span> : ""}
									{selectName}
									{}
								</span>
								{checked && authors && JSON.stringify(authors) !== "{}" ? (
									<List.Item.Brief>
										<WarpSegmentedPull
											myFetch={myFetch}
											value={authors}
                                            topId={topId}
                                            workId={workId}
                                            rootBody={rootBody}
											onChange={(value) => {
												validateFields((err) => {
													if (!err) {
														let actionNextNodes = curNextNodes.map((item3, index3) => {
															if (item3.selectId === selectId) {
																for (const key in authors) {
																	item3.authors[key] = {
																		...item3.authors[key],
																		selectAuthorNewApih5: value[key].selectAuthorNewApih5,
																	};
																}
															}
															return item3;
														});
														setFieldsValue({
															curNextNodes: actionNextNodes,
														});
													}
												});
											}}
										/>
									</List.Item.Brief>
								) : null}
							</CheckboxItem>
						);
					})}
				</List>
			</Modal>
		);
	}
}
class SegmentedPull extends Component {
	constructor(props) {
		super(props);
		const { value } = this.props;
		this.values = [
			{ authorId: "Author", authorName: "办理人" },
			{ authorId: "SecondAuthor", authorName: "代理人" },
			{ authorId: "Reader", authorName: "传阅" },
		];
		let selectedIndex = 0;
		for (let i = 0; i < this.values.length; i++) {
			let { authorId } = this.values[i];
			if (value[authorId] && value[authorId].isSelect === "true") {
				selectedIndex = i;
				break;
			}
		}
		this.state = {
			selectedIndex,
			treeData: [],
			loading: false,
		};
	}
	onChange = (selectedIndex) => {
		this.setState({
			selectedIndex,
		});
	};
	componentDidMount() {
		if (this.props.onChange) {
			this.props.onChange(this.props.value);
		}
		// this.loadTree()
	}
	loadTree = () => {
		//加载树形结构
		const { myFetch } = this.props;
		myFetch("getSysDepartmentUserAllTree").then(({ success, code, message, data }) => {
			if (success) {
				this.setState({ treeData: [data], loading: false });
			}
		});
	};
	render() {
		const { selectedIndex, loading } = this.state;
		const { getFieldDecorator } = this.props.form;
        const { value, myFetch, topId, workId, rootBody = {} } = this.props;
		return loading ? (
			<ActivityIndicator text="Loading..." />
		) : (
			<div>
				<SegmentedControl2 onChange={this.onChange} value={value} selectedIndex={selectedIndex} />
				{this.values.map(({ authorId }, index) => {
					if (value[authorId]) {
						//拉人组件动态判断的属性
						let otherProps = {};
						const fetchConfigParams = value[authorId]["initAuthorApiPar"] || {};
						if (value[authorId]["initAuthorApiUrl"]) {
							// config 未配置该接口时直接用接口返回的就行
							if (!window.configs.apis[value[authorId]["initAuthorApiUrl"]]) {
								// window.configs.apis[value[authorId]["initAuthorApiUrl"]] = `system-server/${value[authorId]["initAuthorApiUrl"]}`;
								window.configs.apis[value[authorId]["initAuthorApiUrl"]] = `${value[authorId]["initAuthorApiUrl"]}`;
							}
							//按需加载
							otherProps = {
								fetchConfig: {
									apiName: value[authorId]["initAuthorApiUrl"],
									paramsKey: "departmentParentId",
									params: {
										departmentParentId: "0",
										topId: topId,
										workId: !workId || workId === "add" ? rootBody["workId"] : workId,
										...fetchConfigParams,
									},
								},
							};
						} else {
							//使用死数据
							otherProps = {
								treeData: value[authorId].initAuthorApih5,
							};
						}

						return (
							<div
								key={index}
								style={{
									display: selectedIndex === index ? "block" : "none",
								}}
							>
								{isMobile()
									? getFieldDecorator(authorId + "[selectAuthorNewApih5]", {
											valuePropName: "defaultValue",
											initialValue: value[authorId]["selectAuthorNewApih5"] || [],
									  })(
											<PullPersionMobile
												search={value[authorId].initAuthorApih5SearchApi}
												searchParamsKey={"search"}
												searchApi={value[authorId].initAuthorApih5SearchApi ? value[authorId].initAuthorApih5SearchApi : "getSysDepartmentUserAllTree"}
												myFetch={myFetch}
												selectType={"2"}
												edit={value[authorId].isSelect === "true"}
												{...otherProps}
												useCollect //开启收藏功能
												collectApi="appGetSysFrequentContactsList" //查询收藏人员  接受后台参数[{xx:xxx,...}]
												collectApiByAdd="appAddSysFrequentContacts" //新增收藏人员  传给后台的参数[{xx:xxx,...}]
												collectApiByDel="appRemoveSysFrequentContacts" //删除收藏人员  传给后台的参数[{xx:xxx,...}]
											/>
									  )
									: getFieldDecorator(authorId + "[selectAuthorNewApih5]", {
											valuePropName: "defaultValue",
											initialValue: value[authorId]["selectAuthorNewApih5"] || [],
									  })(
											<PullPersion
												search={value[authorId].initAuthorApih5SearchApi}
												searchParamsKey={"search"}
												searchApi={value[authorId].initAuthorApih5SearchApi ? value[authorId].initAuthorApih5SearchApi : "getSysDepartmentUserAllTree"}
												myFetch={myFetch}
												selectType={"2"}
												edit={value[authorId].isSelect === "true"}
												{...otherProps}
												useCollect //开启收藏功能
												collectApi="appGetSysFrequentContactsList" //查询收藏人员  接受后台参数[{xx:xxx,...}]
												collectApiByAdd="appAddSysFrequentContacts" //新增收藏人员  传给后台的参数[{xx:xxx,...}]
												collectApiByDel="appRemoveSysFrequentContacts" //删除收藏人员  传给后台的参数[{xx:xxx,...}]
											/>
									  )}
							</div>
						);
					} else {
						return null;
					}
				})}
			</div>
		);
	}
}
const WarpSegmentedPull = createForm({
	mapPropsToFields: (props) => {
		return {
			Author: createFormField(props.value.Author),
			SecondAuthor: createFormField(props.value.SecondAuthor),
			Reader: createFormField(props.value.Reader),
		};
	},
	onValuesChange: (props, changedValues, allValues) => {
		if (props.onChange) {
			props.onChange(allValues);
		}
	},
})(SegmentedPull);

class SegmentedControl2 extends Component {
	constructor(props) {
		super(props);
		this.state = {
			selectedIndex: 0,
			treeData: [],
			loading: false,
		};
	}
	onChange = (selectedIndex) => {
		this.props.onChange(selectedIndex);
	};
	render() {
		const { value, selectedIndex } = this.props;
		const _values = [
			{ authorId: "Author", authorName: "办理人" },
			{ authorId: "SecondAuthor", authorName: "代理人" },
			{ authorId: "Reader", authorName: "传阅" },
		];
		return (
			<div className="am-segment" role="tablist">
				{_values.map(({ authorId, authorName }, index) => {
					if (value[authorId]) {
						const selected = selectedIndex === index;
						const { isFree } = value[authorId];
						const disabled = isFree !== "true";
						return (
							<div
								key={index}
								onClick={(e) => {
									e.preventDefault();
									!disabled && this.onChange(index);
								}}
								style={disabled ? { opacity: 0.5 } : {}}
								className={selected ? "am-segment-item am-segment-item-selected" : "am-segment-item"}
							>
								<div className="am-segment-item-inner" />
								{authorName}
							</div>
						);
					} else {
						return null;
					}
				})}
			</div>
		);
	}
}
// const closest = function(el, selector) {
//   const matchesSelector =
//     el.matches ||
//     el.webkitMatchesSelector ||
//     el.mozMatchesSelector ||
//     el.msMatchesSelector;
//   while (el) {
//     if (matchesSelector.call(el, selector)) {
//       return el;
//     }
//     el = el.parentElement;
//   }
//   return null;
// };

export { actionFlow, clickFlowButton, FlowButtonGroup, FlowSelectModal, FlowForm };
