/*

    配置非qnn-form和qnn-table組件配置
    均为特殊配置

    内置关键词  
    selectList: "gwData",
    treeData: "gData",

*/

var language = window.configs.language || "zh_CN";
var isEn = language === "en_US"; //是否为英语
var lanObj = {
    edit: isEn ? "Edit" : "编辑",
    rename: isEn ? "Rename" : "重命名",
    goBack: isEn ? "go back" : "返回",
    confirm: isEn ? "Confirm" : "确认",
    cancel: isEn ? "Cancel" : "取消",
    add: isEn ? "Add" : "新增",
    save: isEn ? "Save" : "保存",
    placeholder: isEn ? "please enter..." : "请输入...",
    delete: isEn ? "Delete" : "批量删除",
    "无结果": isEn ? "no data" : "无结果",
    "保存并继续添加": isEn ? "Save and continue to add" : "保存并继续添加",
    "点击或者拖动上传": isEn ? "Click or drag to upload" : "点击或者拖动上传",
    "姓名": isEn ? "name" : "姓名",
    "账号": isEn ? "Account" : "账号",
    "手机": isEn ? "mobile" : "手机",
    "姓名": isEn ? "name" : "姓名",
    "部门": isEn ? "department" : "部门",
    "岗位": isEn ? "post" : "岗位",
}

window.contactsPage = {
    //表格
    tableColumns: [
        {
            title: lanObj["姓名"],
            dataIndex: "realName",
            width: 80,
            render: "realNameRender"
        },
        {
            title: lanObj["账号"],
            dataIndex: "userId"
        },
        {
            title: lanObj["手机"],
            dataIndex: "mobile"
        },
        {
            title: lanObj["岗位"],
            width: 80,
            dataIndex: "ext1",
            render: "gwNameRender"
        },
        {
            title: lanObj["部门"],
            dataIndex: "sysDepartmentShowList",
            render: "sysDepartmentShowListRender"
        },
    ],
    //详情
    detailColumns: [
        {
            label: lanObj["姓名"],
            dataIndex: "realName",
        },
        {
            label: lanObj["账号"],
            dataIndex: "userId"
        },
        {
            label: lanObj["手机"],
            dataIndex: "mobile"
        },
        {
            label: lanObj["部门"],
            dataIndex: "sysDepartmentShowList",
            render: "sysDepartmentShowListRender"
        },
        {
            label: lanObj["岗位"],
            type: "select",
            dataIndex: "ext1",
            placeholder: '请选择',
            selectList: "gwData",
            render: "gwNameRender"
        },
    ],
    //新增
    addColumns: [
        {
            label: "userType",
            type: "hidden",
            dataIndex: "userType"
        },
        {
            label: "userStatus",
            type: "hidden",
            dataIndex: "userStatus"
        },
        {
            label: lanObj["姓名"],
            dataIndex: "realName"
        },
        {
            label: lanObj["账号"],
            dataIndex: "userId"
        },
        {
            label: lanObj["手机"],
            dataIndex: "mobile"
        },

        {
            label: lanObj["部门"],
            dataIndex: "sysDepartmentList",
            type: "PullPersion",
            treeData: "gData",
            k: {
                label: "departmentName",
                value: "departmentId",
                type: "type",
                children: "children"
            }
        },
        {
            label: lanObj["岗位"],
            type: "select",
            dataIndex: "ext1",
            placeholder: '请选择',
            selectList: "gwData",
            required: false
        },
    ],
    //编辑
    editColumns: [
        {
            label: "userKey",
            type: "hidden",
            dataIndex: "userKey"
        },
        {
            label: lanObj["姓名"],
            dataIndex: "realName"
        },
        {
            label: lanObj["账号"],
            dataIndex: "userId",
            disabled: true
        },
        {
            label: lanObj["手机"],
            dataIndex: "mobile"
        },

        {
            label: lanObj["部门"],
            dataIndex: "sysDepartmentList",
            type: "PullPersion",
            treeData: "gData",
            k: {
                label: "departmentName",
                value: "departmentId",
                type: "type",
                children: "children"
            }
        },
        {
            label: lanObj["岗位"],
            type: "select",
            dataIndex: "ext1",
            placeholder: '请选择',
            selectList: "gwData",
            required: false
        },

    ]
};

