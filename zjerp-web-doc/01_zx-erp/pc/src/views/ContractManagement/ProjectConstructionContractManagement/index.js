import React, { useCallback, useRef, useState } from "react";
import QnnTable from "qnn-table";
import QnnForm from "qnn-form";
import rootTableConfig from "./config/rootTable";
import { Button, Modal } from "antd";
import importModa from "./config/importModa";
import { ExportOutlined } from "@ant-design/icons";

import { msg, getOrgId } from "qnn-apih5";
import style from "./style.less";

import ProjectConstructionContractForm from "../ProjectConstructionContract/FlowForm";

export default (props) => {
	const rootTable = useRef();
	const tabFourTable = useRef();
	const taiZhangTable = useRef(); //台账

	//导入modal是否弹出
	const [importModalShow, setImportModalShow] = useState(false);
	//当前点击导入的行
	const curImportRow = useRef({});

	// 合同评审记录 是否弹出
	const [htpsModalShow, setHtpsModalShow] = useState(false);
	// 合同评审记录数据
	const [htpsLogData, setHtpsLogData] = useState({});

	//导入按钮
	const rowImportClick = useCallback((args) => {
		curImportRow.current = args.rowData;
		setImportModalShow(true);
	}, []);
	//导入
	const startImport = useCallback(async (args) => {
		const { success, message, code } = await props.myFetch("importZxGcsgCcWorks", {
			ctContractId: curImportRow.current["ctContractId"],
			...args.values,
		});
		if (success) {
			msg.successMsg(message);
			setImportModalShow(false);
			rootTable.current.refresh();
		} else {
			msg.errMsg(message, code);
		}
	}, []);
	//回复执行
	const huifuzhixing = useCallback(async (args) => {
		//获取主键id
		const { selectedRows } = args;

		if (selectedRows.length > 1) {
			Modal.warn({
				title: "只能选择一条数据",
			});
			return;
		}

		const { success, message, code } = await props.myFetch("resumeZxGcsgCtContractStatus", {
			ctContractId: selectedRows[0]?.ctContractId,
			// contractStatus: selectedRows[0]?.contractStatus,
			// contractStatus: selectedRows[0].contractStatus === "3" ? "1" : "3",
		});
		if (success) {
			msg.successMsg(message);
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	// 第一个tab中的方法 ======

	//甲方名称切换
	//需要设置 合同编码 contractNo
	//contractNo = 下拉选项数据.contractNo + psGetZxGcsgCtContrApplyContractNo 这个接口数据+1
	const firstIDChange = useCallback(async (val, args) => {
		const { itemData } = args;
		const { data, success, message, code } = await props.myFetch("psGetZxGcsgCtContrApplyContractNo", { contractNo: itemData.contractNo, firstID: val });
		if (success) {
			rootTable.current.qnnForm.setValues({
				contractNo: data,
			});
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	// 第三个tab中的方法 ======

	//第三个tab中的表格数据格式化
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
	const getTabThreeTableOtherParams = useCallback(async (args) => {
		//获取主键id
		const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
		return { ctContractId };
	}, []);

	const getZxSaFiWldwListParams = useCallback(async (args) => {
		const { secondName } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
		return { lswldwDwmc: secondName };
	}, []);

	// 第四个tab中的方法 ======

	//第四\五个tab中table列表请求的otherParams
	const getTabFourTableOtherParams = useCallback(async (args) => {
		//获取主键id
		const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
		return { ctContractId };
	}, []);

	//结算单请求列表接口的参数
	const getFinalStatementOtherParams = useCallback(async (args) => {
		//获取主键id
		const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
		return { contractID: ctContractId };
	}, []);

	//第四个tab中 同步挂接数据按钮
	const synchronouslySuspendData = useCallback(async (args) => {
		const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};

		const { success, message, code } = await props.myFetch("glSyncHookDataZxGcsgCtPriceSys", {
			ctContractId,
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

	//获取用户信息
	const getUserInfo = useCallback(async () => {
		const { departmentName, companyName } = props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
		return {
			orgID: getOrgId(props),
			// companyId: companyId,
			companyName: companyName,
			// projectId: departmentId,
			projectName: departmentName,
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

	const rowLookClick = useCallback((args) => {
		args.btnCallbackFn.setTabsIndex("1");
	}, []);

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

	//记录第一个tab被更改的字段
	const tabOneFieldChange = useRef();

	// 合同评审记录查看 按钮被点击
	const htpsLookClick = async (arg) => {
		const { ctContrApplyId } = arg.parentTableInfo.rowData;

		setHtpsModalShow(true);
		setHtpsLogData({
			apiName: "psGetZxGcsgCtContrApplyDetailsByFlow",
			otherParams: { ctContrApplyId }, //可为函数
		});
	};

	//第三个tab中table列表请求的otherParams
	const cbForCWXXFetch = useCallback(async (args) => {
		//获取主键id
		const { ctContractId, alterContractSum, contractNo, contractName, secondName } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
		let params = {};
		params.alterContractSum = alterContractSum;
		params.contractNo = contractNo;
		params.contractName = contractName;
		params.secondName = secondName;
		params.ctContractId = ctContractId;
		return {
			apiName: "updateZxGcsgCtContract",
			otherParams: params,
		};
	}, []);

	const delWillExecute = useCallback(async (obj, params) => {
		const selectRows = obj.qnnTableInstance.getSelectedRows();
		const { data, success, message, code } = await props.myFetch("checkBatchDeleteZxGcsgCtContract", selectRows);
		if (success) {
			if (data.deleteFlag !== "ok") { 
				return false;
			}
		} else {
			msg.errMsg(message, code);
			return false;
		}

		return true;
	});

	return (
		<div>
			<QnnTable
				routerInfo={props.routerInfo}
				fetch={props.myFetch}
				upload={props.myUpload}
				wrappedComponentRef={(me) => (rootTable.current = me)}
				getRootTable={() => rootTable.current}
				loginInfo={props.loginAndLogoutInfo.loginInfo}
				componentsKey={{
					PsjlckBtn: (props) => {
						const { ctContrApplyId } = props.parentTableInfo.rowData; 
						return (
							<div style={{ margin: "12px 12px 0px 12px", textAlign: "right" }}>
								<Button type="primary" onClick={() => htpsLookClick(props)} disabled={ctContrApplyId.length > 32}>
									合同评审记录查看
								</Button>
							</div>
						);
					},
					ExportOutlined: <ExportOutlined />,
					DetailForm: (obj) => {
						const selectRows = obj.qnnTableInstance.getSelectedRows();
						return (
							<ProjectConstructionContractForm
								{...props}
								onlyLook
								flowFormData={{}}
								fetchConfigByinitialValues={{
									apiName: "psGetZxGcsgCtContrApplyDetailsByFlow",
									otherParams: { ctContrApplyId: selectRows[0]?.ctContrApplyId }, //可为函数
								}}
								isInQnnTable
							/>
						);
					},
				}}
				method={{
					rowImportClick,
					rowLookClick,
					firstIDChange,
					tabThreeDataFormat,
					getTabThreeTableOtherParams,
					getTabFourTableOtherParams,
					synchronouslySuspendData,
					printByFourTab,
					getFinalStatementOtherParams,
					huifuzhixing,
					getUserInfo,
					exportData,
					tabFourFormFieldOnBlur,
					tabFourGXSaveCb,
					editCancelBtnHide: (args) => args.btnCallbackFn.getTabsIndex() !== "0" && args.btnCallbackFn.getTabsIndex() !== "8",
					editSaveBtnHide: (args) => args.btnCallbackFn.getTabsIndex() !== "0",
					editSaveBtnHideByCaiWuXinXi: (args) => args.btnCallbackFn.getTabsIndex() !== "8",
					disabledFunDetail: (obj) => {
						let data = obj.btnCallbackFn.getSelectedRows();
						if (data.length === 1 && data[0].ctContrApplyId.length <= 32 ) {
							return false;
						} else {
							return true;
						}
					},
					getZxSaFiWldwListParams,
					cbForCWXXFetch,
                    delWillExecute,
					getWriter: () => {
						return props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.realLabel;
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
						//每次出现都需要刷新台账数据
						item.onShow = () => {
							setTimeout(() => {
								// console.log(taiZhangTable.current)
								taiZhangTable.current && taiZhangTable.current.refresh();
							}, 1000);
						};
					}
					return item;
				})}
				onTabsChange={() => (tabOneFieldChange.current = undefined)}
				fieldsValueChange={(_, cf) => (tabOneFieldChange.current = cf)}
				// tabsWillChange={(args, canChange) => {
				//     if (tabOneFieldChange.current) {
				//         Modal.confirm({
				//             title: '当前页的最新录入内容未做保存，是否仍要切换?',
				//             icon: <ExclamationCircleOutlined />,
				//             onOk() {
				//                 canChange(true);
				//             },
				//             onCancel() { },
				//         });
				//         return;
				//     }
				//     canChange(true);
				// }}
			/>

			{/* 导入弹出层 */}
			<Modal title="EXCEL表格数据导入" visible={importModalShow} footer={null} onCancel={() => setImportModalShow(false)} destroyOnClose>
				<QnnForm
					fetch={props.myFetch}
					upload={props.myUpload}
					method={{
						startImport: startImport,
					}}
					{...importModa}
				/>
			</Modal>

			{/* 评审记录查看弹出层 */}
			<Modal title="合同评审记录查看" width={"100%"} centered visible={htpsModalShow} footer={null} onCancel={() => setHtpsModalShow(false)} destroyOnClose wrapClassName={style.htpsModal}>
				<ProjectConstructionContractForm
					{...props}
					// flowFormData={htpsLogData}
					flowFormData={{}}
					fetchConfigByinitialValues={htpsLogData}
					isInQnnTable
					onlyLook
				/>
			</Modal>
		</div>
	);
};
