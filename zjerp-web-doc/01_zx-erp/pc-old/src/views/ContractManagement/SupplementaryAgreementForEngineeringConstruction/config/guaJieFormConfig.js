 
import tabThree from "./tabThree"
import tabFour from "./tabFour"
import ListSubcontracts from "./ListSubcontracts" 

export default {
    tabs: [
        {
            field: "listSubcontracts",
            title: "分包合同清单",
            name: "listSubcontracts",
            content: ListSubcontracts, 
        },
        {
            field: "inventoryProcessLinkedLedger",
            title: "清单工序挂接台账",
            name: "qnnTable", 
            content: {
                // 表格
                ...tabThree
            }
        },
        {
            field: "danJiaFenXi",
            title: "合同单价分析",
            name: "qnnTable", 
            content: {
                // 表格
                ...tabFour
            }
        }
    ]
}