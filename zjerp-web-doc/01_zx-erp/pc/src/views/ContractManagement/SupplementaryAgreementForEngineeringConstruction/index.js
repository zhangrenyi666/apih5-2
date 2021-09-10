import React, { useRef, memo, useCallback, useEffect, useState } from "react"; //
import QnnTable from "qnn-table";
import QnnForm from "qnn-form";
import { message as antdMsg } from "antd";
import { msg, getOrgId } from "qnn-apih5";
import FlowForm from "./FlowForm";
import { LoadingOutlined, ExportOutlined, HistoryOutlined } from "@ant-design/icons";
import rootTableConfig from "./config/curdTableAndFormConfig";
import qingDanFormConfig from "./config/qingDanFormConfig";
import guaJieFormConfig from "./config/guaJieFormConfig";
import Operation from "../ProjectConstructionContract/config/operation";

//涉及到的小数点保留位数，数量3位，单价6位，金额(元)2位，金额(万元)6位
export default memo((props) => {
	const rootTable = useRef();
	//清单表单ref
	const qingdanRef = useRef();
	//挂接表单ref
	const guajieRef = useRef();
	//挂接tab中单价分析的ref
	const danJiaFenXiRef = useRef();
	const taiZhangTable = useRef(); //台账

	const drawerShowToggle = useCallback(({ drawerIsShow }) => {
		if (!drawerIsShow) {
			rootTable.current.clearSelectedRows();
		}
	}, []);

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

	//表单中合同名称字段切换
	const contractNameChange = useCallback(async (val, args) => {
		//需要去请求 补充协议编号
		if(!args.itemData)return;
		const { data, success, message, code } = await props.myFetch("bxGetZxGcsgCtContrApplyContractNo", {
			contractNo: args.itemData?.contractNo,
			ctContractId: args.itemData?.ctContractId,
		});
		if (success) {
			rootTable.current.qnnForm.setValues({
				...args.itemData,
				firstID: args.itemData?.firstId,
				// contractName: val,
				contractNo: data,
				contractType: "P2C",
			});
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//进度查询
	// const qsjdcx = useCallback(() => {
	//     console.log('进度查询')
	// }, [])
	const Qsjdcx = (props) => {
		return (
			<Operation
				{...props}
				apiName={"openFlowByReport"}
				flowInfo={{
					apiName: "bxGetZxGcsgCtContrApplyDetailsByFlow",
					apiType: "POST",
					flowId: "bxZxGcsgCtContrApply",
				}}
			/>
		);
	};

	//流程组件
	//内部需要请求接口进行验证
	const FlowFormComFC = (obj) => {
		//是否正在请求check接口
		const [checkIng, setCheckIng] = useState(true);
		const selectRows = obj.qnnTableInstance.getSelectedRows();

		useEffect(() => {
			(async () => {
				const { success, message, code, data } = await props.myFetch("bxCheckZxGcsgCtContrApplyBeforeFlow", {
					ctContrApplyId: selectRows[0]?.ctContrApplyId,
				});

				if (success) {
					if (!data) {
						antdMsg.error(message);
						obj.btnCallbackFn.closeDrawer();
					} else {
						setCheckIng(false);
					}
				} else {
					obj.btnCallbackFn.closeDrawer();
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

	const willOpenFlowFormCom = useCallback((obj) => {
		const selectRows = obj.qnnTableInstance.getSelectedRows();
		if (selectRows.length !== 1) {
			obj.btnCallbackFn.msg.error("请选择一条数据！");
			obj.btnCallbackFn.closeDrawer();
			return false;
		}

		if (selectRows[0].workId && selectRows[0].workId !== "") {
			obj.btnCallbackFn.msg.error("已发起审批的不可再发起！");
			obj.btnCallbackFn.closeDrawer();
			return false;
		}
	});

	//编辑清单后的保存
	const qingDanEditSave = useCallback(async (args) => {
		const { values, rowData } = args;
		const params = {
			...values,
			ctContrApplyId: rowData["ctContrApplyId"],
			alterWorkList: values["alterWorkList"].map((item) => {
				return {
					...item,
					// isLeaf: item.isLeaf ? 1 : 0
				};
			}),
		};
		const { success, message, code } = await props.myFetch("saveZxGcsgCcCoAlterDetailsWorkList", params);
		if (success) {
			msg.successMsg(message);
			rootTable.current.closeDrawer();
			rootTable.current.refresh();
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//第二个tab中的表格数据格式化
	//这个方法有会创建一个组件 因为qnnForm的qnnTable Tab页面不支持主键写为func
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
				.concat([{ ...curData, processID: `${curData.processID}_${Math.random()}` }]);
			return newArr;
		}, []);
		return newData;
	}, []);

	//第二个tab中table列表请求的otherParams  台账页面
	const getTabThreeTableOtherParams = useCallback(async () => {
		//获取主键id
		const { ctContrApplyId } = guajieRef.current.props.rowData;
		return { ctContrApplyId };
	}, []);

	// 第三个tab中的方法 ======

	//第四个tab中table列表请求的otherParams
	const getTabFourTableOtherParams = useCallback(async () => {
		//获取主键id
		const { ctContrApplyId } = guajieRef.current.props.rowData;
		return { ctContrApplyId };
	}, []);

	//第四个tab中 同步挂接数据按钮  合同单价分析
	const synchronouslySuspendData = useCallback(async () => {
		const { ctContrApplyId } = guajieRef.current.props.rowData;

		const { success, message, code } = await props.myFetch("bxSyncHookDataZxGcsgCtPriceSys", {
			ctContrApplyId,
		});
		if (success) {
			msg.successMsg(message, code);
			//刷新下tab就行
			danJiaFenXiRef.current.refresh();
		} else {
			msg.errMsg(message, code);
		}
	}, []);

	//遍历列表数据返回一个新的数据
	//@data 表格数据
	//@dataItemOperFn 对每条数据进行操作
	const loopTableData = (data = [], dataItemOperFn) => {
		return data.map((item) => {
			return {
				...dataItemOperFn(item),
			};
		});
	};

	//根据税率 和 不含税 计算含税
	//根据税率 和 含税   计算不含税
	// const getHS = (sl = 0, bhs = 0) => Number((bhs * (1 + Number(sl) / 100)).toFixed(6));
	const getBHS = (sl = 0, hs = 0) => Number((hs / (1 + Number(sl) / 100)).toFixed(6));

	//判断数字字段是否存在
	const numNoNone = (num) => num || num === 0;

	//函数计算规则见 xiMingTableConfig.js 中的注释
	//字段 失去焦点、切换值时的计算
	const changeJS = async (event, formBtnCallbackFn, tableBtnCallbackFn, params = []) => {
		const { setEditedRowData, getEditedRowData } = tableBtnCallbackFn;

		//获取正在编辑的行数据 然后修改整个表格数据  然后设置值进form
		const curEditRow = (await getEditedRowData()) || {};
		//这个字段是当前正在编辑的 curEditRow 中的数据不是最新的 需要用这个
		const curEditField = params[0];
		curEditRow[curEditField] = curEditField === "taxRate" ? event : event?.target?.value; //下拉时event 就是值
		if (!qingdanRef.current?.form) return; //可能已经关闭抽屉了
		const { alterType } = curEditRow;
		//税率字段特殊 因为下拉需要从 itemData(只有下拉类型有 itemData) 中取
		const taxRate = Number(formBtnCallbackFn.itemData?.["value"]) || Number(curEditRow["taxRate"]) || 0;

		//需要手动改变表格数据
		const newAttr = {};
		if (alterType === "1") {
			//新增
			newAttr["afterChangeQty"] = curEditRow["changeQty"];
			newAttr["afterChangePrice"] = curEditRow["changePrice"];

			if (numNoNone(curEditRow["changeQty"]) && numNoNone(curEditRow["changePrice"])) {
				newAttr["afterAmt"] = curEditRow["changeQty"] * curEditRow["changePrice"];
			}
			if (numNoNone(taxRate) && numNoNone(curEditRow["changeQty"]) && numNoNone(curEditRow["changePrice"])) {
				newAttr["afterAmtNoTax"] = getBHS(taxRate, curEditRow["changeQty"] * curEditRow["changePrice"]);
			}

			//注意：有些计算需要用 newAttr、newFormValues
			if (numNoNone(taxRate) && numNoNone(curEditRow["changeQty"]) && numNoNone(curEditRow["changePrice"])) {
				newAttr["afterAmtTax"] = newAttr["afterAmt"] - newAttr["afterAmtNoTax"];
			}
		} else {
			//编辑
			// 这里注意一下，修改类型的时候：变更后数量=下拉选中清单的变更后数量+增减数量
			// 用下拉里面的这个字段quantity算，不要用contractQty算
			if (curEditRow["changeQty"]) {
				//这里只能用备份的字段来操作
				newAttr["afterChangeQty"] = Number(curEditRow["quantity"]) + Number(curEditRow["changeQty"]);
			}

			newAttr["afterAmt"] = (newAttr["afterChangeQty"] || 0) * curEditRow["afterChangePrice"];

			if (taxRate && numNoNone(newAttr["afterChangeQty"]) && curEditRow["afterChangePrice"]) {
				newAttr["afterAmtNoTax"] = getBHS(taxRate, newAttr["afterChangeQty"] * curEditRow["afterChangePrice"]);
			}
			if (taxRate && numNoNone(newAttr["afterAmt"]) && numNoNone(newAttr["afterAmtNoTax"])) {
				newAttr["afterAmtTax"] = newAttr["afterAmt"] - newAttr["afterAmtNoTax"];
			}
		}
		setEditedRowData({ ...newAttr });

		//计算完表格数据后才可以计算表单
		setTimeout(() => {
			tableDatachangeJS();
		}, 300);
	};

	//表格删除按钮点击后也需要进行计算
	//只需要计算上面表单的数据即可
	//注意这里算法和上面的新增和修改算法规则都不完全一样
	const tableDatachangeJS = () => {
		if (!qingdanRef.current && !qingdanRef.current.form) return;
		const { upAlterContractSumNoTax = 0, upAlterContractSum = 0, upAlterContractSumTax = 0 } = qingdanRef.current.props.rowData;

		const { alterWorkList = [] } = qingdanRef.current.form.getFieldsValue();

		let applyAmount = 0.0;
		let applyAmountNoTax = 0.0;
		let applyAmountTax = 0.0;
		let replyAmount = upAlterContractSum;
		let replyAmountNoTax = upAlterContractSumNoTax;
		let replyAmountTax = upAlterContractSumTax;

		if (!alterWorkList.length) {
			qingdanRef.current.setValues({
				applyAmount,
				applyAmountNoTax,
				applyAmountTax,
				replyAmount,
				replyAmountNoTax,
				replyAmountTax,
			});
			return;
		}

		const newFormValues = {
			applyAmount,
			applyAmountNoTax,
			applyAmountTax,
			replyAmount,
			replyAmountNoTax,
			replyAmountTax,
		};

		loopTableData(alterWorkList, (item) => {
			//新增
			if (item["alterType"] === "1") {
				if (item.afterAmt) {
					newFormValues["applyAmount"] += item.afterAmt / 10000;
				}

				const replyAmount = upAlterContractSum + newFormValues["applyAmount"];
				if (replyAmount) {
					newFormValues["replyAmount"] = replyAmount;
				}

				if (item.afterAmtNoTax) {
					newFormValues["applyAmountNoTax"] += item.afterAmtNoTax / 10000;
				}

				const replyAmountNoTax = upAlterContractSumNoTax + newFormValues["applyAmountNoTax"];
				if (numNoNone(replyAmountNoTax)) {
					newFormValues["replyAmountNoTax"] = replyAmountNoTax;
				}

				if (item.afterAmtTax) {
					newFormValues["applyAmountTax"] += item.afterAmtTax / 10000;
				}

				const replyAmountTax = upAlterContractSumTax + newFormValues["applyAmountTax"];
				if (numNoNone(replyAmountTax)) {
					newFormValues["replyAmountTax"] = replyAmountTax;
				}
			} else {
				//编辑

				//只能循环计算每一个行的
				newFormValues["applyAmount"] += (item["changeQty"] * item["afterChangePrice"]) / 10000;

				const replyAmount = upAlterContractSum + newFormValues["applyAmount"];
				if (replyAmount) {
					newFormValues["replyAmount"] = replyAmount;
				}

				//只能循环计算每一个行的 不含税增减金额
				newFormValues["applyAmountNoTax"] += getBHS(item["taxRate"], (item["changeQty"] * item["afterChangePrice"]) / 10000);

				const replyAmountNoTax = upAlterContractSumNoTax + newFormValues["applyAmountNoTax"];
				if (numNoNone(replyAmountNoTax)) {
					newFormValues["replyAmountNoTax"] = replyAmountNoTax;
				}

				//只能循环计算每一个行的 本期变更增减税额（万元）
				newFormValues["applyAmountTax"] += (item["changeQty"] * item["afterChangePrice"] - getBHS(item["taxRate"], item["changeQty"] * item["afterChangePrice"])) / 10000;

				const replyAmountTax = upAlterContractSumTax + newFormValues["applyAmountTax"];
				if (numNoNone(replyAmountTax)) {
					newFormValues["replyAmountTax"] = replyAmountTax;
				}
			}
		});

		// console.log('继续设置值：', newFormValues)
		qingdanRef.current.setValues({
			...newFormValues,
		});
	};

	//获取清单表单内容的参数
	const getQingDanOtherParams = useCallback(async (args) => {
		//获取主键id
		const { ccCoAlterId } = args.props?.rowData || {};
		return { ccCoAlterId };
	}, []);

	//获归属主合同清单编号的参数
	const getZxGcsgCcWorksSelectOtherParams = useCallback(async (isLeaf) => {
		//获取主键id
		const { ctContractId } = qingdanRef.current.props.rowData;
		return { ctContractId, isLeaf };
	}, []);

	//获取用户信息
	const getUserInfo = useCallback(async (args) => {
		let otherParams = {};
		if (args?.bindParams?.[0]) {
			// otherParams = JSON.parse(args.bindParams[0])
			otherParams.apih5FlowStatus = args.bindParams?.[0];
		}
		const { departmentName, companyName } = props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
		return {
			orgID: getOrgId(props),
			// companyId: companyId,
			companyName: companyName,
			// projectId: departmentId,
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

	//单价分析表单中某些字段失去焦点后需要计算一些字段
	const tabFourFormFieldOnBlur = () => {
		danJiaFenXiRef.current.qnnForm.getValues(false, (vals) => {
			const { amt1, amt2, amt3, amt4, amt5, amt6, bhPriceNoTax } = vals;
			//不含税单价
			const priceNoTax = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
			//对比（标后预算/限价-不含税合价）
			const dbPrice = bhPriceNoTax - priceNoTax;

			danJiaFenXiRef.current.qnnForm.setValues({
				priceNoTax,
				dbPrice,
			});
		});
	};

	//单价分析中工序数据点击编辑完毕后点击保存按钮的回调
	//点保存按钮后将计算上方表单中的数据
	const tabFourGXSaveCb = () => {
		danJiaFenXiRef.current.qnnForm.getValues(false, (vals) => {
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
			danJiaFenXiRef.current.qnnForm.setValues({
				amt4,
				amt5,
				amt6,
				priceNoTax,
				dbPrice,
			});
		});
	};

	//清单编辑组件
	const QingDanEdit = (tableProps) => {
		let formConfig = [...qingDanFormConfig.formConfig]; 
		// 完成后的数据不让编辑
		const isOver = tableProps.rowData?.apih5FlowStatus === "2";
		// const isOver = true;
		//细明编辑只要表格
		return (
			<div style={{ padding: 12 }}>
				<QnnForm
					// isOver
					fetch={props.myFetch}
					upload={props.myUpload}
					method={{
						qingDanEditSave,
						changeJS,
						tableDatachangeJS,
						getZxGcsgCcWorksSelectOtherParams,
					}}
					rowData={tableProps.rowData}
					wrappedComponentRef={(qnnForm) => (qingdanRef.current = qnnForm)}
					getQingdanRef={() => qingdanRef.current}
					fetchConfig={{
						apiName: "getZxGcsgCcCoAlterDetailsWorkList",
						otherParams: getQingDanOtherParams,
					}}
					{...qingDanFormConfig}
					btns={isOver ? [] : qingDanFormConfig.btns}
					formConfig={
						isOver
							? formConfig.map((item) => ({
									...item,
									disabled: true
							  }))
							: formConfig
					}
				/>
			</div>
		);
	};

	//挂接组件
	const GuaJia = (tableProps) => {
		// 完成后的数据不让编辑
		const isOver = tableProps.rowData?.apih5FlowStatus === "2";

		// style={{ padding: 12 }}
		return (
			<div>
				<QnnForm
					fetch={props.myFetch}
					upload={props.myUpload}
					method={{
						getTabThreeTableOtherParams,
						tabThreeDataFormat,
						getTabFourTableOtherParams,
						synchronouslySuspendData,
						tabFourFormFieldOnBlur,
						tabFourGXSaveCb,
					}}
					rowData={tableProps.rowData}
					wrappedComponentRef={(qnnForm) => (guajieRef.current = qnnForm)}
					getQingdanRef={() => guajieRef.current}
					{...guaJieFormConfig}
					tabs={guaJieFormConfig.tabs.map((item) => {
						if (item.field === "danJiaFenXi") {
							item.content.wrappedComponentRef = (qnnForm) => (danJiaFenXiRef.current = qnnForm);
						}
						if (item.field === "inventoryProcessLinkedLedger") {
							item.content.wrappedComponentRef = (qnnTable) => (taiZhangTable.current = qnnTable);
							//每次出现都需要刷新台账数据
							item.onShow = () => {
								setTimeout(() => {
									taiZhangTable.current.refresh();
								}, 1000);
							};
						}
						const otherAttr = {
							content: {
								...item.content,
							},
						};
						if (item.name === "qnnTable" && isOver) {
							otherAttr.content.disabled = true;
							return {
								...item,
								...otherAttr,
							};
						}
						return { ...item };
					})}
				/>
			</div>
		);
	};

	return (
		<>
			<QnnTable
				routerInfo={props.routerInfo}
				fetch={props.myFetch}
				upload={props.myUpload}
				wrappedComponentRef={(me) => (rootTable.current = me)}
				getRootTable={() => rootTable.current}
				drawerShowToggle={drawerShowToggle}
				componentsKey={{
					Qsjdcx: Qsjdcx,
					FlowFormCom: FlowFormComFC,
					QingDanEdit,
					GuaJia,
					DetailForm: (obj) => {
						const selectRows = obj.qnnTableInstance.getSelectedRows();
						return <FlowForm {...props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowFormData={selectRows[0]} isDetail onlyLook />;
					},
					ExportOutlined: <ExportOutlined />,
					HistoryOutlined: <HistoryOutlined />,
				}}
				method={{
					// qsjdcx: qsjdcx,
					firstIDChange: firstIDChange,
					contractNameChange,
					willOpenFlowFormCom,
					getUserInfo,
					exportData,
					disabledFunDetail: (obj) => {
						let data = obj.btnCallbackFn.getSelectedRows();
						if (data.length === 1 && data[0].ctContrApplyId.length <= 32  && (data[0].apih5FlowStatus === "1" || data[0].apih5FlowStatus === "2")) {
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
				getOrgId={() => getOrgId(props)}
				{...rootTableConfig}
			/>
		</>
	);
});
