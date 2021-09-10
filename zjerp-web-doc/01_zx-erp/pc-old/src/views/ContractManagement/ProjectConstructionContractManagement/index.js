import React, { useCallback, useRef, useState } from "react";
import QnnTable from "qnn-table";
import QnnForm from "qnn-form";
import rootTableConfig from "./config/rootTable"
import { Button, Modal } from 'antd';
import importModa from "./config/importModa"
import { msg } from "qnn-apih5"

import ProjectConstructionContractForm from '../ProjectConstructionContract/FlowForm';

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

    //导入按钮
    const rowImportClick = useCallback((args) => {
        curImportRow.current = args.rowData;
        setImportModalShow(true);
    }, [])
    //导入
    const startImport = useCallback(async (args) => {
        const { success, message, code } = await props.myFetch("importZxGcsgCcWorks", {
            ctContractId: curImportRow.current["ctContractId"],
            ...args.values
        });
        if (success) {
            msg.successMsg(message)
            setImportModalShow(false);
            rootTable.current.refresh()
        } else {
            msg.errMsg(message, code)
        }
    }, [])
    //回复执行
    const huifuzhixing = useCallback(async (args) => {
        //获取主键id
        const { selectedRows } = args;

        if (selectedRows.length > 1) {
            Modal.warn({
                title: "只能选择一条数据"
            })
            return;
        }

        const { success, message, code } = await props.myFetch("resumeZxGcsgCtContractStatus", {
            ctContractId: selectedRows[0]?.ctContractId,
            contractStatus: selectedRows[0]?.contractStatus,
        });
        if (success) {
            msg.successMsg(message)
        } else {
            msg.errMsg(message, code)
        }
    }, [])


    // 第一个tab中的方法 ======

    //甲方名称切换
    //需要设置 合同编码 contractNo
    //contractNo = 下拉选项数据.contractNo + psGetZxGcsgCtContrApplyContractNo 这个接口数据+1
    const firstIDChange = useCallback(async (val, args) => {
        const { itemData } = args;
        const { data, success, message, code } = await props.myFetch("psGetZxGcsgCtContrApplyContractNo", { contractNo: itemData.contractNo, firstID: val });
        if (success) {
            rootTable.current.qnnForm.setValues({
                contractNo: data
            })
        } else {
            msg.errMsg(message, code)
        }
    }, [])


    // 第三个tab中的方法 ======

    //第三个tab中的表格数据格式化
    const tabThreeDataFormat = useCallback((data) => {
        const newData = [...data].reduce((preArr, curData, i) => {
            //需要计算 rowSpan 的数 
            //每次map只能加一次rowSpan 然后需要等下次遍历在加
            let added = false;
            //改变 preArr、curData
            const newArr = [...preArr].map(item => {
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
            }).concat([{ ...curData }]);
            return newArr;
        }, []);
        return newData
    }, [])

    //第三个tab中table列表请求的otherParams
    const getTabThreeTableOtherParams = useCallback(async (args) => {
        //获取主键id
        const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { ctContractId }
    }, []);

    // 第四个tab中的方法 ======

    //第四\五个tab中table列表请求的otherParams
    const getTabFourTableOtherParams = useCallback(async (args) => {
        //获取主键id
        const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { ctContractId }
    }, []);

    //结算单请求列表接口的参数
    const getFinalStatementOtherParams = useCallback(async (args) => {
        //获取主键id
        const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { contractID: ctContractId }
    }, []);

    //第四个tab中 同步挂接数据按钮
    const synchronouslySuspendData = useCallback(async (args) => {
        const { ctContractId } = rootTable.current.form?.getFieldsValue?.() || args.clickCb?.rowData || {};

        const { success, message, code } = await props.myFetch("glSyncHookDataZxGcsgCtPriceSys", {
            ctContractId
        });
        if (success) {
            msg.successMsg(message, code);
            //刷新下第四个tab就行
            tabFourTable.current.refresh();
        } else {
            msg.errMsg(message, code)
        }
    }, []);

    //第四个tab中的打印
    const printByFourTab = useCallback(async () => {
        const { success, message, code, data } = await props.myFetch("printClick");
        if (success) {
            return data
        } else {
            msg.errMsg(message, code)
        }
    }, []);

    //获取用户信息
    const getUserInfo = useCallback(async () => {
        const { departmentId, departmentName, companyId, companyName } = props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return {
            companyId: companyId,
            companyName: companyName,
            projectId: departmentId,
            projectName: departmentName
        }
    }, []);
    //列表导出 
    const exportData = useCallback(async ({ btnCallbackFn: { download } }, params = []) => {
        const apiName = params[0];
        const { domain } = props.myPublic;
        const { token } = props.loginAndLogoutInfo.loginInfo;
        download(`${domain}${apiName}`, {
            ...await getUserInfo()
        }, { token });
    }, []);

    const rowLookClick = useCallback((args) => {
        args.btnCallbackFn.setTabsIndex("1")
    }, [])

    return <div>
        <QnnTable
            fetch={props.myFetch}
            upload={props.myUpload}
            wrappedComponentRef={(me) => rootTable.current = me}
            getRootTable={() => rootTable.current}
            loginInfo={props.loginAndLogoutInfo.loginInfo}
            componentsKey={{
                PsjlckBtn: () => {
                    return <div style={{ margin: "12px 12px 0px 12px", textAlign: "right" }}>
                        <Button type="primary" onClick={() => setHtpsModalShow(true)}>合同评审记录查看</Button>
                    </div>
                }
            }}
            method={{
                rowImportClick, rowLookClick,
                firstIDChange,
                tabThreeDataFormat, getTabThreeTableOtherParams,
                getTabFourTableOtherParams, synchronouslySuspendData, printByFourTab, getFinalStatementOtherParams,
                huifuzhixing,
                getUserInfo, exportData
            }}
            {...rootTableConfig}
            //获取tab中的表格 
            tabs={rootTableConfig.tabs.map(item => {
                if (item.field === "contractAnalysis") {
                    item.content.wrappedComponentRef = (qnnTable) => tabFourTable.current = qnnTable
                }
                if (item.field === "inventoryProcessLinkedLedger") {
                    item.content.wrappedComponentRef = (qnnTable) => taiZhangTable.current = qnnTable;
                    //每次出现都需要刷新台账数据
                    item.onShow = () => {
                        setTimeout(() => {
                            console.log(taiZhangTable.current)
                            taiZhangTable.current && taiZhangTable.current.refresh()
                        }, 1000)
                    }
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
                {...importModa}
            />
        </Modal>

        {/* 评审记录查看弹出层 */}
        <Modal title="合同评审记录查看" width={"100%"} centered visible={htpsModalShow} footer={null} onCancel={() => setHtpsModalShow(false)} destroyOnClose>
            <ProjectConstructionContractForm
                {...props}
                flowFormData={{}}
            />
        </Modal>
    </div>
};