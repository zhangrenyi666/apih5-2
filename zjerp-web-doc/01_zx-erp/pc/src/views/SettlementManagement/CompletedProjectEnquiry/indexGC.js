import React, { useCallback, useRef } from "react";
import QnnTable from "qnn-table";
import rootTableConfig from "./config/rootTable"
import { getOrgId } from "qnn-apih5"
import s from './style.less'
export default (props) => {
    const rootTable = useRef();
    const tabFourTable = useRef();
    const taiZhangTable = useRef(); //台账
    const tabOneFieldChange = useRef();
    // 合同评审记录 是否弹出
    // 合同评审记录数据
    const getUserInfo = useCallback(async () => {
        const { departmentName, companyName } = props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return {
            orgID: getOrgId(props),
            // companyId: companyId,
            companyName: companyName,
            // projectId: departmentId,
            projectName: departmentName
        }
    }, []);
    return <div className={s.root}>
        <QnnTable
            {...props}
            fetch={props.myFetch}
            upload={props.myUpload}
            wrappedComponentRef={(me) => rootTable.current = me}
            getRootTable={() => rootTable.current}
            loginInfo={props.loginAndLogoutInfo.loginInfo}
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
                            // console.log(taiZhangTable.current)
                            taiZhangTable.current && taiZhangTable.current.refresh()
                        }, 1000)
                    }
                }
                return item;
            })}
            method={{
                getUserInfo
            }}
            onTabsChange={() => tabOneFieldChange.current = undefined}
            fieldsValueChange={(_, cf) => tabOneFieldChange.current = cf}
        />
    </div>
};