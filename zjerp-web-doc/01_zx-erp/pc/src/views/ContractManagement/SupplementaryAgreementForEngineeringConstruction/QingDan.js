import React from "react"
import QnnTable from "qnn-table"
import _config from "./config/xiMingTableConfig"


export default (props) => {
    const config = {
        ...props,
        rowKey: "zxCtOtherWorksId",
        isShowRowSelect: false,
        formConfig: _config.formConfig.map((item) => {
            const tableConfig = item.table;
            return {
                ...item,
                table: {
                    ...tableConfig,
                    dataIndex: tableConfig.dataIndex === "ccWorksId" ? "ccWorksName" : tableConfig.dataIndex, 
                    tdEdit: false,
                    type: tableConfig.dataIndex === "ccWorksId" ? "" : tableConfig.type,
                }
            }
        })
        
    };
    return <QnnTable {...config} />
}