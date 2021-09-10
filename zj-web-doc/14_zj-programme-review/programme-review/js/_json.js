var json = [
    {
        name: "schemeNumber",
        label: "方案编号",
        type: "text",
        immutableAdd: true,
        lineNum: 0
    },
    {
        name: "schemeName",
        label: "方案名称",
        type: "text",
        immutableAdd: true,
        lineNum: 0
    },
    {
        name: "projectName",
        label: "项目名称",
        type: "text",
        immutableAdd: true,
        lineNum: 1
    },
    {
        name: "unitName",
        label: "实施单位",
        type: "text",
        immutableAdd: true,
        lineNum: 1
    },
    {
        name: "schemeLevel",
        label: "方案等级",
        type: "text",
        immutableAdd: true,
        lineNum: 2
    },
    {
        name: "implementationTime",
        label: "方案计划实施时间",
        type: "date",
        immutableAdd: true,
        lineNum: 2
    },
    {
        name: "projectStartDate",
        label: "项目开工日期",
        type: "date",
        immutableAdd: true,
        lineNum: 3
    },
    {
        name: "projectEndDate",
        label: "项目交工日期",
        type: "date",
        immutableAdd: true,
        lineNum: 3
    },
    {
        name: "programmingPerson",
        label: "方案编制人",
        type: "text",
        immutableAdd: true,
        lineNum: 4
    },
    {
        name: "projectGeneralUser",
        label: "项目总工",
        type: "text",
        immutableAdd: true,
        lineNum: 4
    },
    {
        name: "programmingPersonTel",
        label: "方案编制人联系方式",
        type: "text",
        immutableAdd: true,
        lineNum: 5
    },
    {
        name: "projectGeneralUserTel",
        label: "项目总工联系方式",
        type: "text",
        immutableAdd: true,
        lineNum: 5
    },
    {
        name: "programmingPreliminaryTrial",
        label: "方案初评情况",
        type: "textarea",
        immutableAdd: true,
        lineNum: 6
    },
    {
        name: "note2OpinionContent",
        label: "一、二级专家审批意见",
        type: "textarea",
        immutableAdd: true,
        lineNum: 7
    },
    {
        name: "note3OpinionContent",
        label: "单位总工审批意见",
        type: "textarea",
        immutableAdd: true,
        lineNum: 8
    },
    {
        name: "note4OpinionContent",
        label: "相关部门审批意见",
        type: "textarea",
        immutableAdd: true,
        lineNum: 9
    }
]



var sss = [
    {
        type: "hidden",//五种形式：text,select,date,hidden,textarea,
        name: "projectName",//输入字段名
        label: "项目名称",
        must: true,
        immutableAdd: true
    },
    {
        type: "hidden",//五种形式：text,select,date,hidden,textarea,
        name: "contractNo",//输入字段名
        label: "合同编号",
        must: true,
        immutableAdd: true
    },
    {
        type: "hidden",
        name: "unitName",
        label: "单位名称",
        immutableAdd: true,
    },
    {
        type: "text",
        name: "engineeringType",
        label: "工程类别",
        immutableAdd: true,
    },
    {
        type: "text",
        name: "province",
        label: "所属省份",
        immutableAdd: true
    },
    {
        type: "date",//text,select,date,
        name: "projectStartDate",
        id: "projectStartDate",
        label: "项目开工日期",
        immutableAdd: true
    },
    {
        type: "date",//text,select,date,
        name: "projectEndDate",
        id: "projectEndDate",
        label: "项目竣工日期",
        immutableAdd: true
    },
    {
        type: "text",//
        name: "projectContractAmount",//
        label: "项目合同金额",//
        placeholder: "请输入内容",
        immutableAdd: true
    },
    {
        type: "textarea",//
        name: "chiefEngineerExamineOpinion",//
        label: "总工审批意见",//
        placeholder: "请输入内容",
        immutableAdd: true
    },
    {
        type: "textarea",//
        name: "bureauExamineOpinion",//
        label: "局审批意见",//
        placeholder: "请输入内容",
        immutableAdd: true
    }
]
