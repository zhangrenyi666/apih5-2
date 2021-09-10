import React, { useRef, useEffect, memo, useCallback, useState } from "react";
import QnnTable from "qnn-table";
import QnnForm from "qnn-form";
import rootTableConfig from "./config/rootTable";
import { msg, getOrgId } from "qnn-apih5";
import FlowForm from "./FlowForm";
import { message as antdMsg, Modal } from "antd";
import { LoadingOutlined, ExportOutlined, HistoryOutlined } from "@ant-design/icons";
import importModa from "./config/importModa";
import Operation from "./config/operation";

//涉及到的小数点保留位数，数量3位，单价6位，金额(元)2位，金额(万元)6位
export default memo((props) => {
	const rootTable = useRef();
	const tabFourTable = useRef(); //单价分析
	const taiZhangTable = useRef(); //台账

	//导入modal是否弹出
	const [importModalShow, setImportModalShow] = useState(false);
	//当前点击导入的行
	const curImportRow = useRef({});

	//第三个tab中的导出
	const exportByThreeTab = useCallback(async () => {
		const { success, message, code, data } = await props.myFetch("exportClick");
		if (success) {
			return data;
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//第三个tab中的打印
	const printByThreeTab = useCallback(async () => {
		// args.selectedRows
		const { success, message, code, data } = await props.myFetch("printClick");
		if (success) {
			return data;
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	// 第三个tab中的表格数据格式化
	const tabThreeDataFormat = useCallback((data) => {
		const newData = [...data].reduce((preArr, curData, i) => {
			//需要计算 rowSpan 的数
			//每次map只能加一次rowSpan 然后需要等下次遍历在加
			let added = false;
			//改变 preArr、curData
			const newArr = [...preArr]
				.map((item) => {
					//说明列表中已经有这条清单了
					if (item.workNo === curData.workNo) {
						//第一个 workNo 需要加一  他的数据的 workNo 不需要加一
						const defaultRowSpan = item.rowSpan || 1;
						const rowSpan = !added ? defaultRowSpan + 1 : 0;
						//如果发现列表中已经有相同的数据时 一定需要将当前这条数据的 rowSpan设置为0
						curData.rowSpan = 0;
						added = true;
						return { ...item, rowSpan: rowSpan };
					}
					return { ...item };
				})
				.concat([{ ...curData }]);
			return newArr;
		}, []);
		return newData;
	}, []);

	//第三个tab中table列表请求的otherParams
	const getTabThreeTableOtherParams = useCallback(async () => {
		//获取主键id
		const { ctContrApplyId } = rootTable.current.form.getFieldsValue();
		return { ctContrApplyId };
	}, []);

	//第四个tab中 同步挂接数据按钮
	const synchronouslySuspendData = useCallback(async () => {
		const { ctContrApplyId } = rootTable.current.form.getFieldsValue();

		const { success, message, code } = await props.myFetch("psSyncHookDataZxGcsgCtPriceSys", {
			ctContrApplyId,
		});
		if (success) {
			msg.successMsg(message, code);
			//刷新下第四个tab就行
			tabFourTable.current.refresh();
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//第四个tab中的打印
	const printByFourTab = useCallback(async () => {
		const { success, message, code, data } = await props.myFetch("printClick");
		if (success) {
			return data;
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//第四个tab中table列表请求的otherParams
	const getTabFourTableOtherParams = useCallback(async () => {
		//获取主键id
		const { ctContrApplyId } = rootTable.current.form.getFieldsValue();
		return { ctContrApplyId };
	}, []);

	//单价分析中工序数据点击编辑完毕后点击保存按钮的回调
	//点保存按钮后将计算上方表单中的数据
	const tabFourGXSaveCb = () => {
		tabFourTable.current.qnnForm.getValues(false, (vals) => {
			const { ctPriceSysItemList = [], amt1, amt2, amt3, bhPriceNoTax } = vals;
			let amt4 = 0,
				amt5 = 0,
				amt6 = 0;
			ctPriceSysItemList.forEach(({ rgf, zzcl, jxsb }) => {
				amt4 += rgf;
				amt5 += zzcl;
				amt6 += jxsb;
			});
			//不含税单价
			const priceNoTax = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
			//对比（标后预算/限价-不含税合价）
			const dbPrice = bhPriceNoTax - priceNoTax;

			//所有行汇总计算表单中的人工费、周转材料、机械设备
			tabFourTable.current.qnnForm.setValues({
				amt4,
				amt5,
				amt6,
				priceNoTax,
				dbPrice,
			});
		});
	};

	//单价分析表单中某些字段失去焦点后需要计算一些字段
	const tabFourFormFieldOnBlur = () => {
		tabFourTable.current.qnnForm.getValues(false, (vals) => {
			const { amt1, amt2, amt3, amt4, amt5, amt6, bhPriceNoTax } = vals;
			//不含税单价
			const priceNoTax = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
			//对比（标后预算/限价-不含税合价）
			const dbPrice = bhPriceNoTax - priceNoTax;

			tabFourTable.current.qnnForm.setValues({
				priceNoTax,
				dbPrice,
			});
		});
	};

	const drawerShowToggle = useCallback(({ drawerIsShow }) => {
		if (!drawerIsShow) {
			rootTable.current.clearSelectedRows();
		}
	}, []);

	//获取甲方下拉的参数
	const firstIDOtherParams = useCallback(() => {
		return {
			// orgID: props.loginAndLogoutInfo.loginInfo.userInfo.curCompany?.value
			orgID: getOrgId(props),
			// jurisdictionID: getOrgId(props)
		};
	}, []);

	//甲方名称切换
	//需要设置 合同编码 contractNo
	//contractNo = 下拉选项数据.contractNo + psGetZxGcsgCtContrApplyContractNo 这个接口数据+1
	const firstIDChange = useCallback(async (val, args) => {
		const { itemData } = args;
		if(!itemData)return;
		const { data, success, message, code } = await props.myFetch("psGetZxGcsgCtContrApplyContractNo", { contractNo: itemData.contractNo, firstID: val });
		if (success) {
			rootTable.current.qnnForm.setValues({
				contractNo: data,
			});
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//包件编号切换
	const packageNoChange = useCallback(async (val, args) => {
		const { itemData } = args;
		//已经不让选择了
		// if(itemData?.isRela && +itemData?.isRela === 0){
		//     Modal.error({
		//         title:"该包件编号未完全匹配！请在oa系统中将该包件编号对应的投标单位全部进行入库。"
		//     })
		//     return;
		// }
		rootTable.current.qnnForm.setValues({
			cloudBidNo: itemData?.schemeNo,
			cloudEcoID: itemData?.id,
		});
	}, []);

	//新增后的回调
	const addCb = useCallback((args) => {
		//新增后直接关闭抽屉
		// rootTable.current.form.setFieldsValue({
		//     ctContrApplyId:args.response.data.ctContrApplyId
		// });
	}, []);

	//导入按钮
	const importSuccessCb = useRef();
	const rowImportClick = useCallback((args) => {
		//评审不通过的数据可以修改、清单导入 apih5FlowStatus === '3'
		if (args.rowData.workId && args.rowData.workId !== "" && args.rowData.apih5FlowStatus !== "3" && args.rowData.apih5FlowStatus !== "0") {
			args.btnCallbackFn.msg.error(`已发起审批的数据不可再导入清单！`);
			return false;
		}
		curImportRow.current = args.rowData;
		setImportModalShow(true);
		importSuccessCb.current = args.importSuccessCb;
	}, []);
	//导入
	const startImport = useCallback(async (args) => {
		const { success, message, code } = await props.myFetch("importZxGcsgCtContrApplyWorks", {
			ctContrApplyId: curImportRow.current["ctContrApplyId"],
			...args.values,
		});
		if (success) {
			msg.successMsg(message);
			setImportModalShow(false);
			rootTable.current.refresh();
			//需要刷新第二个tab页面
			importSuccessCb.current && importSuccessCb.current();
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//获取用户信息
	const getUserInfo = useCallback((args) => {
		let otherParams = {};
		if (args?.bindParams?.[0]) {
			otherParams.apih5FlowStatus = args.bindParams?.[0];
			// otherParams = JSON.parse(args.bindParams[0])
		}
		const { curCompany = {} } = props.loginAndLogoutInfo.loginInfo.userInfo;
		const { departmentName, companyName } = curCompany;
		return {
			// companyId: companyId,
			// projectId: departmentId,
			orgID: getOrgId(props),
			companyName: companyName,
			projectName: departmentName,
			...otherParams,
		};
	}, []);
	//列表导出
	const exportData = useCallback(async ({ btnCallbackFn: { download } }, params = []) => {
		const apiName = params[0];
		const { domain, apis } = props.myPublic;
		const { access_token } = props.loginAndLogoutInfo.loginInfo;
		download(
			`${domain}${apis[apiName]}`,
			{
				...(await getUserInfo()),
			},
			{ Authorization: `Bearer ${access_token}` }
		);
	}, []);

	//内部需要请求接口进行验证
	const FlowFormComFC = (obj) => {
		//是否正在请求check接口
		const [checkIng, setCheckIng] = useState(true);
		const selectRows = obj.qnnTableInstance.getSelectedRows();

		useEffect(() => {
			(async () => {
				const { success, message, code, data } = await props.myFetch("psCheckZxGcsgCtContrApplyBeforeFlow", {
					ctContrApplyId: selectRows[0]?.ctContrApplyId,
				});

				if (success) {
					if (!data) {
						antdMsg.error(message);
						obj.qnnTableInstance.closeDrawer();
					} else {
						setCheckIng(false);
					}
				} else {
					obj.qnnTableInstance.closeDrawer();
					msg.errMsg(message, code);
				}
			})();
		}, []);

		if (checkIng) {
			return (
				<div style={{ padding: 12, textAlign: "center" }}>
					<a>
						<LoadingOutlined /> loading...
					</a>
				</div>
			);
		}

		return <FlowForm {...props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowFormData={selectRows[0]} />;
	};

	const willOpenFlowFormCom = useCallback(async (obj, params) => {
		const selectRows = obj.qnnTableInstance.getSelectedRows();
		if (selectRows.length !== 1 && obj.rowInfo.name !== "del") {
			obj.btnCallbackFn.msg.error("请选择一条数据！");
			obj.btnCallbackFn.closeDrawer();
			return false;
		}
		//评审不通过的数据可以修改、清单导入 apih5FlowStatus === '3'
		const canStatus = selectRows[0].apih5FlowStatus === "3" || selectRows[0].apih5FlowStatus === "0";
		if (obj.rowInfo.name === "edit" && canStatus) {
			return true;
		}
		if (obj.rowInfo.name === "del") {
			let pass = true;
			selectRows.forEach(({ apih5FlowStatus }) => {
				const canStatus = apih5FlowStatus === "3" || apih5FlowStatus === "2" || apih5FlowStatus === "1";
				if (canStatus) {
					pass = false;
				}
			});
			if (!pass) {
				obj.btnCallbackFn.msg.error(`已发起审批的数据不可再${obj.rowInfo?.label}！`);
			}
			return pass;
		}
		if (selectRows[0].workId && obj.selectedRows[0].workId !== "") {
			obj.btnCallbackFn.msg.error(`已发起审批的数据不可再${obj.rowInfo?.label}！`);
			obj.btnCallbackFn.closeDrawer();
			return false;
		}
	});

	const Qsjdcx = (props) => {
		return (
			<Operation
				{...props}
				apiName={"openFlowByReport"}
				flowInfo={{
					apiName: "psGetZxGcsgCtContrApplyDetailsByFlow",
					apiType: "POST",
					flowId: "psZxGcsgCtContrApply",
				}}
			/>
		);
	};

	return (
		<>
			<QnnTable
				routerInfo={props.routerInfo}
				fetch={props.myFetch}
				upload={props.myUpload}
				wrappedComponentRef={(me) => {
					rootTable.current = me;
				}}
				getRootTable={() => rootTable.current}
				drawerShowToggle={drawerShowToggle}
				componentsKey={{
					FlowFormCom: FlowFormComFC,
					Qsjdcx: Qsjdcx,
					DetailForm: (obj) => {
						const selectRows = obj.qnnTableInstance.getSelectedRows();
						return <FlowForm {...props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowFormData={selectRows[0]} isDetail onlyLook />;
					},
					ExportOutlined: <ExportOutlined />,
					HistoryOutlined: <HistoryOutlined />,
				}}
				method={{
					exportByThreeTab: exportByThreeTab,
					printByThreeTab: printByThreeTab,
					getTabThreeTableOtherParams: getTabThreeTableOtherParams,
					tabThreeDataFormat: tabThreeDataFormat,
					firstIDOtherParams: firstIDOtherParams,

					synchronouslySuspendData: synchronouslySuspendData,
					printByFourTab: printByFourTab,
					getTabFourTableOtherParams: getTabFourTableOtherParams,
					tabFourGXSaveCb,
					tabFourFormFieldOnBlur,

					firstIDChange: firstIDChange,
					packageNoChange: packageNoChange,

					addCb: addCb,

					rowImportClick: rowImportClick,
					willOpenFlowFormCom,
					getUserInfo,
					exportData,
					disabledFunDetail: (obj) => {
						let data = obj.btnCallbackFn.getSelectedRows();
						if (data.length === 1 && data[0].ctContrApplyId.length <= 32 && (data[0].apih5FlowStatus === "1" || data[0].apih5FlowStatus === "2")) {
							return false;
						} else {
							return true;
						}
					},
					jinDuChaXunDisabled: (obj) => {
						let data = obj.btnCallbackFn.getSelectedRows();
						if (data.length === 1 && data[0].workId) {
							return false;
						} else {
							return true;
						}
					},
					addSaveHide: (props) => {
						return props.btnCallbackFn.getTabsIndex() !== "0";
					},
					temporaryBtnHide: (props) => {
						return props.btnCallbackFn.getTabsIndex() !== "0";
					},
					toOfficialBtnHide: (props) => {
						// 只有暂存数据才有这个按钮
						return props.rowData.apih5FlowStatus !== "-2";
					},
				}}
				{...rootTableConfig}
				//获取tab中的表格
				tabs={rootTableConfig.tabs.map((item) => {
					if (item.field === "contractAnalysis") {
						item.content.wrappedComponentRef = (qnnTable) => (tabFourTable.current = qnnTable);
					}
					if (item.field === "inventoryProcessLinkedLedger") {
						item.content.wrappedComponentRef = (qnnTable) => (taiZhangTable.current = qnnTable);
						// //每次出现都需要刷新台账数据，以前不重新渲染。现在会重新渲染了。所以无需刷新
						// item.onShow = () => {
						// 	setTimeout(() => {
						// 		taiZhangTable.current.refresh();
						// 	}, 1000);
						// };
						
					}

					return item;
				})}
			/>

			{/* 导入弹出层 */}
			<Modal title="EXCEL表格数据导入" visible={importModalShow} footer={null} onCancel={() => setImportModalShow(false)} destroyOnClose>
				<QnnForm
					fetch={props.myFetch}
					upload={props.myUpload}
					method={{
						startImport: startImport,
					}}
					loginAndLogoutInfo={props.loginAndLogoutInfo}
					{...importModa}
				/>
			</Modal>
		</>
	);
});
