window.others = {
    "flowFormJson": {
        "zjYongYin": {
            "apiName": {
                "create": "addZxHwZlTrouble",
                "update": "updateZxHwZlTrouble",
                "get": "getZxHwZlTrouble"
            },
            "name": "用印申请",
            "titleName": "sealApplyName",
            "tabs": [
                {
                    "name": "基本信息",
                    "type": "1",
                    "isMain": "1",
                    "tbName": "zjYySealApply",
                    "tbIdName": "sealApplyId",
                    "conditions": [
                        {
                            "type": "hidden",
                            "name": "sealApplyId",
                            "label": "主键id",
                            "placeholder": "",
                        },
                        {
                            "type": "select",
                            "name": "sealType",
                            "label": "印别",
                            "selectList": [
                                {
                                    "value": "",
                                    "text": "全部"
                                },
                                {
                                    "value": "0",
                                    "text": "公章"
                                },
                                {
                                    "value": "1",
                                    "text": "手签章"
                                },
                                {
                                    "value": "2",
                                    "text": "人名章"
                                }
                            ],
                            "must": true
                        },
                        {
                            "type": "number",
                            "name": "useNumber",
                            "label": "用印次数",
                            "placeholder": "请输入内容",
                            "must": true
                        },
                        {
                            "type": "text",
                            "name": "sealApplyName",
                            "label": "用印申请名称",
                            "placeholder": "用印申请名称",
                            "must": true
                        },
                        {
                            "type": "text",
                            "name": "sendUnitId",
                            "label": "用印单位",
                            "placeholder": "用印单位",
                            "must": true
                        },
                        {
                            "type": "text",
                            "name": "sealUnitId",
                            "label": "发往单位",
                            "placeholder": "发往单位",
                            "must": true
                        },
                        {
                            "type": "textarea",
                            "name": "content",
                            "label": "用印申请内容",
                            "placeholder": "用印申请内容",
                            "must": true
                        },
                        {
                            "type": "textarea",
                            "name": "remark",
                            "label": "备注",
                            "placeholder": "请输入备注",
                        }
                    ]
                },
                {
                    "name": "附件信息",
                    "type": "2",
                    "tbName": "",
                    "tbIdName": "fileId",
                    "conditions": [
                        {
                            "type": "upload",
                            "name": "zjYySealFile",
                            "label": "附件1",
                            "btnName": "添加附件",
                            "projectName": "zjSeal",
                            "fileType": [
                                "jpg",
                                "jpeg",
                                "png",
                                "gif",
                                "docx",
                                "xls"
                            ]
                        }
                    ]
                }
            ]
        },
        "zxHwZlTrouble": {
            "apiName": {
                "create": "addZxHwZlTrouble",
                "update": "updateZxHwZlTrouble",
                "get": "getZxHwZlTrouble"
            },
            "name": "质量巡检",
            "titleName": "sealApplyName",
            "tabs": [
                {
                    "name": "基本信息",
                    "type": "1",
                    "isMain": "1",
                    "tbName": "zxHwZlTrouble",
                    "tbIdName": "troubleId",
                    conditions: [
                        {
                            type: "hidden",//
                            name: "troubleId",//
                            label: "主键id",// 
                        },
                        {
                            type: "text",//
                            name: "troubleTitle",//
                            label: "标题",//
                            placeholder: "",
                            immutableAdd: true,
                        }
                    ]
                },
                {
                    "name": "附件信息",
                    "type": "2",
                    "tbName": "",
                    "tbIdName": "uid",
                    "conditions": [
                        {
                            "type": "upload",
                            "name": "zxHwZlAttachment",
                            "label": "附件1",
                            "btnName": "添加附件",
                            "projectName": "zxHighwayAssistant",
                            "fileType": [
                                "jpg",
                                "jpeg",
                                "png",
                                "gif",
                                "docx",
                                "xls"
                            ]
                        }
                    ]
                }
            ]
        },
        "zxHwGxProcess": {
            "apiName": {
                "create": "addZxHwZlTrouble",
                "update": "updateZxHwZlTrouble",
                "get": "getZxHwZlTrouble"
            },
            "name": "质量巡检",
            "titleName": "sealApplyName",
            "tabs": [
                {
                    "name": "基本信息",
                    "type": "1",
                    "isMain": "1",
                    "tbName": "zxHwZlTrouble",
                    "tbIdName": "troubleId",
                    conditions: [
                        {
                            type: "hidden",//
                            name: "troubleId",//
                            label: "主键id",// 
                        },
                        {
                            type: "text",//
                            name: "troubleTitle",//
                            label: "标题",//
                            placeholder: "",
                            immutableAdd: true,
                        }
                    ]
                },
                {
                    "name": "附件信息",
                    "type": "2",
                    "tbName": "",
                    "tbIdName": "uid",
                    "conditions": [
                        {
                            "type": "upload",
                            "name": "zxHwZlAttachment",
                            "label": "附件1",
                            "btnName": "添加附件",
                            "projectName": "zxHighwayAssistant",
                            "fileType": [
                                "jpg",
                                "jpeg",
                                "png",
                                "gif",
                                "docx",
                                "xls"
                            ]
                        }
                    ]
                }
            ]
        }
    },
    "workFormJson": {
        "zjYongYin": {
            "apiName": {
                "create": "addZxHwZlTrouble",
                "update": "update",
                "get": "get"
            },
            "name": "用印申请",
            "titleName": "sealApplyName",
            "tabs": [
                {
                    "name": "基本信息",
                    "type": "1",
                    "isMain": "1",
                    "tbName": "zjYySealApply",
                    "tbIdName": "sealApplyId",
                    conditions: [
                        {
                            type: "hidden",//
                            name: "sealApplyId",//
                            label: "主键id",// 
                        },
                        {
                            type: "text",//
                            name: "sealApplyName",//
                            label: "用印申请名称",//
                            placeholder: "",
                            immutableAdd: true,
                        },
                        {
                            type: "select",
                            name: "sealType",
                            label: "印别",
                            immutableAdd: true,
                            selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
                                {
                                    value: "",
                                    text: "全部"
                                },
                                {
                                    value: "0",
                                    text: "公章"
                                },
                                {
                                    value: "1",
                                    text: "手签章"
                                },
                                {
                                    value: "2",
                                    text: "人名章"
                                }
                            ],
                        },
                        {
                            type: "textarea",//
                            name: "content",//
                            label: "用印申请内容",//
                            placeholder: "",
                            immutableAdd: true,
                        },
                        {
                            type: "number",//
                            name: "useNumber",//
                            label: "用印次数",//
                            placeholder: "",
                            immutableAdd: true,
                        },
                        {
                            type: "text",//
                            name: "sealUnitId",//
                            label: "发往单位",//
                            placeholder: "",
                            immutableAdd: true
                        },
                        {
                            type: "text",//
                            name: "sendUnitId",//
                            label: "用印单位",//
                            placeholder: "",
                            immutableAdd: true
                        },
                        {
                            type: "textarea",//
                            name: "remark",//
                            label: "备注",//
                            immutableAdd: true,
                            placeholder: ""
                        },
                        {
                            type: "textarea",//
                            name: "legalOpinion",//
                            label: "合法合规人员审批意见",//
                            placeholder: "",
                            immutableAdd: true,
                        },
                        {
                            type: "textarea",//
                            name: "depHeadOpinion",//
                            label: "部门负责人审批意见",//
                            placeholder: "",
                            immutableAdd: true,
                        },
                        {
                            type: "textarea",//
                            name: "leaderOpinion",//
                            label: "分管公司领导审批意见",//
                            placeholder: "",
                            immutableAdd: true,
                        }
                    ]
                },
                {
                    "name": "附件信息",
                    "type": "2",
                    "tbName": "",
                    "tbIdName": "fileId",
                    "conditions": [
                        {
                            "type": "upload",
                            "name": "zjYySealFile",
                            "label": "附件1",
                            "btnName": "添加附件",
                            "projectName": "zjSeal",
                            "fileType": [
                                "jpg",
                                "jpeg",
                                "png",
                                "gif",
                                "docx",
                                "xls"
                            ]
                        }
                    ]
                }
            ]
        },
        "zxHwZlTrouble": {
            "apiName": {
                "create": "addZxHwZlTrouble",
                "update": "updateZxHwZlTrouble",
                "get": "getZxHwZlTrouble"
            },
            "name": "质量巡检",
            "titleName": "sealApplyName",
            "tabs": [
                {
                    "name": "基本信息",
                    "type": "1",
                    "isMain": "1",
                    "tbName": "zxHwZlTrouble",
                    "tbIdName": "troubleId",
                    conditions: [
                        {
                            type: "hidden",//
                            name: "troubleId",//
                            label: "主键id",// 
                        },
                        {
                            type: "text",//
                            name: "troubleTitle",//
                            label: "标题",//
                            placeholder: "",
                            immutableAdd: true,
                        }
                    ]
                },
                {
                    "name": "附件信息",
                    "type": "2",
                    "tbName": "",
                    "tbIdName": "uid",
                    "conditions": [
                        {
                            "type": "upload",
                            "name": "zxHwZlAttachment",
                            "label": "附件1",
                            "btnName": "添加附件",
                            "projectName": "zxHighwayAssistant",
                            "fileType": [
                                "jpg",
                                "jpeg",
                                "png",
                                "gif",
                                "docx",
                                "xls"
                            ]
                        }
                    ]
                }
            ]
        },
        "zxHwGxProcess": {
            "apiName": {
                "create": "addZxHwZlTrouble",
                "update": "updateZxHwZlTrouble",
                "get": "getZxHwZlTrouble"
            },
            "name": "质量巡检",
            "titleName": "sealApplyName",
            "tabs": [
                {
                    "name": "基本信息",
                    "type": "1",
                    "isMain": "1",
                    "tbName": "zxHwZlTrouble",
                    "tbIdName": "troubleId",
                    conditions: [
                        {
                            type: "hidden",//
                            name: "troubleId",//
                            label: "主键id",// 
                        },
                        {
                            type: "text",//
                            name: "troubleTitle",//
                            label: "标题",//
                            placeholder: "",
                            immutableAdd: true,
                        }
                    ]
                },
                {
                    "name": "附件信息",
                    "type": "2",
                    "tbName": "",
                    "tbIdName": "uid",
                    "conditions": [
                        {
                            "type": "upload",
                            "name": "zxHwZlAttachment",
                            "label": "附件1",
                            "btnName": "添加附件",
                            "projectName": "zxHighwayAssistant",
                            "fileType": [
                                "jpg",
                                "jpeg",
                                "png",
                                "gif",
                                "docx",
                                "xls"
                            ]
                        }
                    ]
                }
            ]
        }
    }
}