import { getOrgId } from "qnn-apih5"
import { message as Msg} from "antd";
const PersonColJson = function () {
    PersonColJson.prototype.getColFunc = (type, refsMap, isDetail, propsData, setIsModalVisible) => {
        /*
            "employ"(录聘用)
            "salary"(岗薪)
            "report"(报备)
            "userPosition"(转岗)
            "reserveCadre"(后备)
        */
        const orgID = getOrgId(propsData)
        // const { companyId, companyName, projectId, projectName } = getUserInfo(propsData).curCompany

        ChildPropsData = propsData
        return columnsFunc(type, refsMap, isDetail, orgID, setIsModalVisible)
    }

    let ChildPropsData = null

    const isRequiredStatus = false

    const fourItemLayout = {
        labelCol: {
            sm: { span: 8 }
        },
        wrapperCol: {
            sm: { span: 16 }
        }
    }

    const columnsFunc = (type, refsMap, isDetail, orgID, setIsModalVisible) => {

        const columnsMap = {
            salary: [
                {
                    type: 'string',
                    label: 'extensionId',
                    field: 'extensionId',
                    hide: true
                },
                {
                    label: '姓名',
                    field: 'realName',
                    required: isRequiredStatus,
                    span: 12,
                    disabled: isDetail,
                    type: 'selectByQnnTable',
                    optionConfig: {
                        label: 'realName',
                        value: 'realName',
                        searchKey: "realName"
                    },
                    allowClear: false,
                    onChange: async (val, obj) => {
                        let newData = null
                        const { data, success, message } = await ChildPropsData.myFetch('getWorkManagementUserExtensionDetails', { extensionId: val })
                        if (success) {
                            newData = {
                                ...data,
                                positionSalary: data.levelSalaryId ? data.levelSalaryId.split(',')[data.levelSalaryId.split(',').length - 1] : '',
                                extensionId: val
                            }

                            refsMap[type].setValues(newData)
                        } else {
                            Msg.error(message)
                            refsMap[type].setValues({ realName: '' })
                            // setIsModalVisible(false)
                            return
                        }
                    },
                    dropdownMatchSelectWidth: 900,
                    qnnTableConfig: {
                        antd: {
                            rowKey: "extensionId"
                        },
                        rowSelection: {
                            hideSelectAll: true,
                        },
                        fetchConfig: (obj) => {
                            return {
                                apiName: "getWorkManagementUserExtensionList",
                                otherParams: {
                                    orgId: orgID
                                }
                            }

                        },
                        searchBtnsStyle: "inline",
                        formConfig: [
                            {
                                isInTable: false,
                                form: {
                                    type: 'string',
                                    field: 'extensionHistoryId',
                                    hide: true
                                }
                            },
                            {

                                table: {
                                    dataIndex: "realName",
                                    title: "姓名",
                                    filter: true
                                },
                                form: {
                                    field: "realName", //表格里面的字段 
                                    label: "姓名",
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '性别',
                                    dataIndex: 'gender',
                                    type: 'select',
                                }
                                , form: {
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '男',
                                            value: '0'
                                        },
                                        {
                                            label: '女',
                                            value: '1'
                                        }
                                    ],
                                    placeholder: '请选择',
                                },
                            },
                            {
                                table: {
                                    title: '证件类型',
                                    dataIndex: 'idType',
                                    type: "select",
                                }
                                , form: {
                                    field: "idType", //表格里面的字段 
                                    label: "证件类型",
                                    required: true,
                                    type: "select",
                                    optionData: [
                                        //默认选项数据
                                        {
                                            label: "身份证",
                                            value: "0"
                                        },
                                        {
                                            label: "其他",
                                            value: "1"
                                        }
                                    ],
                                }
                            },
                            {
                                table: {
                                    title: '证件号',
                                    dataIndex: 'idNumber',
                                    filter: true
                                }
                                , form: {
                                    field: "idNumber", //表格里面的字段 
                                    label: "证件号",
                                    required: true,
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '政治面貌名字',
                                    dataIndex: 'politicCountenanceName',
                                }
                                , form: {
                                    type: 'string',
                                    label: '政治面貌名字',
                                    field: 'politicCountenanceName',
                                }
                            },
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "userType",
                                    title: "聘用类型",
                                    width: 120,
                                    type: 'select',
                                },
                                form: {
                                    type: 'select',
                                    field: 'userType',
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'pinYongLeiBie'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children',
                                    },
                                }
                            },
                        ],
                    },
                },
                {
                    type: "images",
                    desc: "上传近照",
                    label: " ",
                    colon: false,
                    field: "latestAttachmentList", //唯一的字段名 ***必传
                    fetchConfig: {
                        apiName: "upload"
                    },
                    max: 1,
                    className: "Upload-photo",
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                },
                {
                    type: 'date',
                    label: '出生年月',
                    field: 'birthday',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "userType", //表格里面的字段 
                    label: "聘用类型",
                    required: isRequiredStatus,
                    type: "select",
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'pinYongLeiBie'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children',
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "title", //表格的唯一key
                    label: "职称",
                    type: "select",
                    disabled: true,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhiChengMingCheng'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                },
                {
                    type: "select",
                    label: '所学专业',
                    field: 'major',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhuanye'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',// 
                    },

                },
                {
                    type: 'date',
                    label: '毕业时间',
                    field: 'graduateDate',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    label: '毕业学校',
                    field: 'graduateSchool',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: "string",
                    label: '所属单位/项目',
                    field: 'orgName',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                {
                    // projectId
                    type: "string",
                    field: 'orgId',
                    hide: true
                },
                {
                    field: "position", //表格的唯一key
                    label: "已审批岗位",
                    type: "cascader",
                    required: isRequiredStatus,
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: 'gangWeiGuanLi'
                        }
                    },
                    disabled: true,
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                },
                {
                    type: 'cascader',
                    label: '已审批岗位等级',
                    field: 'levelSalaryId',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                    fetchConfig: {
                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                        otherParams: {
                            itemId: 'gongrenzhongzhong'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'label', //默认 label
                        value: 'value',//
                        children: 'showData',
                    },
                },
                {
                    type: 'string',
                    label: '已审批岗薪',
                    field: 'positionSalary',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                {
                    field: "levelSalaryRecommendId", //表格里面的字段 
                    label: "推岗薪等级",
                    required: true,
                    type: "cascader",
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                    fetchConfig: {
                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                        otherParams: {
                            itemId: 'gongrenzhongzhong'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'label', //默认 label
                        value: 'value',//
                        children: 'showData',
                        linkageFields: {
                            "positionSalaryRecommend": "value",
                            "salaryRecommendId": "value"
                        }

                    },
                },
                {
                    type: 'string',
                    label: '推荐岗薪',
                    field: 'positionSalaryRecommend',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    field: 'salaryRecommendId',
                    hide: true
                },
                {
                    type: 'textarea',
                    label: '推荐原因',
                    field: 'levelSalaryRecommendRemarks',
                    required: isRequiredStatus,
                    disabled: isDetail,
                    span: 24,
                },
                {
                    type: 'cascader',
                    label: '公司核准岗薪等级',
                    field: 'levelSalaryCompanyApprovalId',
                    required: true,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                    fetchConfig: {
                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                        otherParams: {
                            itemId: 'gongrenzhongzhong'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'label', //默认 label
                        value: 'value',//
                        children: 'showData',
                        linkageFields: {
                            "positionSalaryCompanyApproval": "value",
                            "salaryCompanyApprovalId": "value"
                        }
                    },
                },
                {
                    type: 'string',
                    label: '公司核准岗薪',
                    field: 'positionSalaryCompanyApproval',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    field: 'salaryCompanyApprovalId',
                    hide: true
                },
            ],
            report: [
                {
                    type: 'string',
                    label: 'extensionId',
                    field: 'extensionId',
                    hide: true
                },
                {
                    label: '姓名',
                    field: 'realName',
                    required: true,
                    span: 12,
                    disabled: isDetail,
                    type: 'selectByQnnTable',
                    optionConfig: {
                        label: 'realName',
                        value: 'realName',
                        searchKey: "realName"
                    },
                    allowClear: false,
                    onChange: async (val, obj) => {
                        let newData = null

                        const { data, success, message } = await ChildPropsData.myFetch('getWorkManagementUserExtensionDetails', { extensionId: val })

                        if (success) {
                            newData = {
                                ...data,
                                positionSalary: data.levelSalaryId ? data.levelSalaryId.split(',')[data.levelSalaryId.split(',').length - 1] : '',
                                extensionId: val
                            }

                            refsMap[type].setValues(newData)
                        } else {
                            Msg.error(message)
                            refsMap[type].setValues({ realName: '' })
                            // setIsModalVisible(false)
                            return
                        }
                    },
                    dropdownMatchSelectWidth: 900,
                    qnnTableConfig: {
                        antd: {
                            rowKey: "extensionId"
                        },
                        rowSelection: {
                            hideSelectAll: true,
                        },
                        fetchConfig: (obj) => {
                            return {
                                apiName: "getWorkManagementUserExtensionList",
                                otherParams: {
                                    orgId: orgID
                                }
                            }

                        },
                        searchBtnsStyle: "inline",
                        formConfig: [
                            {
                                isInTable: false,
                                form: {
                                    type: 'string',
                                    field: 'extensionHistoryId',
                                    hide: true
                                }
                            },
                            {

                                table: {
                                    dataIndex: "realName",
                                    title: "姓名",
                                    filter: true
                                },
                                form: {
                                    field: "realName", //表格里面的字段 
                                    label: "姓名",
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '性别',
                                    dataIndex: 'gender',
                                    type: 'select',
                                }
                                , form: {
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '男',
                                            value: '0'
                                        },
                                        {
                                            label: '女',
                                            value: '1'
                                        }
                                    ],
                                    placeholder: '请选择',
                                },
                            },
                            {
                                table: {
                                    title: '证件类型',
                                    dataIndex: 'idType',
                                    type: "select",
                                }
                                , form: {
                                    field: "idType", //表格里面的字段 
                                    label: "证件类型",
                                    required: true,
                                    type: "select",
                                    optionData: [
                                        //默认选项数据
                                        {
                                            label: "身份证",
                                            value: "0"
                                        },
                                        {
                                            label: "其他",
                                            value: "1"
                                        }
                                    ],
                                }
                            },
                            {
                                table: {
                                    title: '证件号',
                                    dataIndex: 'idNumber',
                                    filter: true
                                }
                                , form: {
                                    field: "idNumber", //表格里面的字段 
                                    label: "证件号",
                                    required: true,
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '政治面貌名字',
                                    dataIndex: 'politicCountenanceName',
                                }
                                , form: {
                                    type: 'string',
                                    label: '政治面貌名字',
                                    field: 'politicCountenanceName',
                                }
                            },
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "userType",
                                    title: "聘用类型",
                                    width: 120,
                                    type: 'select',
                                },
                                form: {
                                    type: 'select',
                                    field: 'userType',
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'pinYongLeiBie'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children',
                                    },
                                }
                            },
                        ],
                    },
                },
                {
                    type: "images",
                    desc: "上传近照",
                    label: " ",
                    colon: false,
                    field: "latestAttachmentList", //唯一的字段名 ***必传
                    fetchConfig: {
                        apiName: "upload"
                    },
                    max: 1,
                    className: "Upload-photo",
                    required: true,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                },
                {
                    type: 'date',
                    label: '出生年月',
                    field: 'birthday',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "userType", //表格里面的字段 
                    label: "聘用类型",
                    required: isRequiredStatus,
                    type: "select",
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'pinYongLeiBie'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children',
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "title", //表格的唯一key
                    label: "职称",
                    type: "select",
                    disabled: true,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhiChengMingCheng'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                },
                {
                    type: "select",
                    label: '所学专业',
                    field: 'major',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhuanye'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',// 
                    },

                },
                {
                    type: 'date',
                    label: '毕业时间',
                    field: 'graduateDate',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    label: '毕业学校',
                    field: 'graduateSchool',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: "string",
                    label: '所属单位/项目',
                    field: 'orgName',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                {
                    // projectId
                    type: "string",
                    field: 'orgId',
                    hide: true
                },
                {
                    field: "position", //表格的唯一key
                    label: "已审批岗位",
                    type: "cascader",
                    required: isRequiredStatus,
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: 'gangWeiGuanLi'
                        }
                    },
                    disabled: true,
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                },
                {
                    type: 'cascader',
                    label: '已审批岗位等级',
                    field: 'levelSalaryId',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                    fetchConfig: {
                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                        otherParams: {
                            itemId: 'gongrenzhongzhong'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'label', //默认 label
                        value: 'value',//
                        children: 'showData',
                    },
                },
                {
                    type: 'string',
                    label: '已审批岗薪',
                    field: 'positionSalary',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                // 推荐岗位
                {
                    type: 'cascader',
                    label: '推荐岗位',
                    field: 'positionAfter',
                    required: true,
                    span: 24,
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: 'gangWeiGuanLi'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children',
                    },
                    formItemLayout: {
                        labelCol: {
                            sm: { span: 4 }
                        },
                        wrapperCol: {
                            sm: { span: 20 }
                        }
                    },
                    disabled: isDetail
                },
                {
                    field: "levelSalaryRecommendId", //表格里面的字段 
                    label: "推岗薪等级",
                    required: isRequiredStatus,
                    type: "cascader",
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                    fetchConfig: {
                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                        otherParams: {
                            itemId: 'gongrenzhongzhong'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'label', //默认 label
                        value: 'value',//
                        children: 'showData',
                        linkageFields: {
                            "positionSalaryRecommend": "value",
                            "salaryRecommendId": "value"
                        }

                    },
                },
                {
                    type: 'string',
                    label: '推荐岗薪',
                    field: 'positionSalaryRecommend',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    field: 'salaryRecommendId',
                    hide: true
                },
                {
                    type: 'textarea',
                    label: '推荐原因',
                    field: 'levelSalaryRecommendRemarks',
                    required: isRequiredStatus,
                    disabled: isDetail,
                    span: 24,
                },
                {
                    type: 'cascader',
                    label: '公司核准岗薪等级',
                    field: 'levelSalaryCompanyApprovalId',
                    required: true,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                    fetchConfig: {
                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                        otherParams: {
                            itemId: 'gongrenzhongzhong'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'label', //默认 label
                        value: 'value',//
                        children: 'showData',
                        linkageFields: {
                            "positionSalaryCompanyApproval": "value",
                            "salaryCompanyApprovalId": "value"
                        }
                    },
                },
                {
                    type: 'string',
                    label: '公司核准岗薪',
                    field: 'positionSalaryCompanyApproval',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    field: 'salaryCompanyApprovalId',
                    hide: true
                },
            ],
            userPosition: [
                {
                    type: 'string',
                    label: 'extensionId',
                    field: 'extensionId',
                    hide: true
                },
                {
                    label: '姓名',
                    field: 'realName',
                    required: true,
                    span: 12,
                    disabled: isDetail,
                    type: 'selectByQnnTable',
                    optionConfig: {
                        label: 'realName',
                        value: 'realName',
                        searchKey: "realName"
                    },
                    allowClear: false,
                    onChange: async (val, obj) => {
                        let newData = null
                        const { data, success, message } = await ChildPropsData.myFetch('getWorkManagementUserExtensionDetails', { extensionId: val })
                        if (success) {
                            newData = {
                                ...data,
                                positionSalary: data.levelSalaryId ? data.levelSalaryId.split(',')[data.levelSalaryId.split(',').length - 1] : '',
                                extensionId: val
                            }

                            refsMap[type].setValues(newData)
                        } else {
                            Msg.error(message)
                            refsMap[type].setValues({ realName: '' })
                            // setIsModalVisible(false)
                            return
                        }
                    },
                    dropdownMatchSelectWidth: 900,
                    qnnTableConfig: {
                        antd: {
                            rowKey: "extensionId"
                        },
                        rowSelection: {
                            hideSelectAll: true,
                        },
                        fetchConfig: (obj) => {
                            return {
                                apiName: "getWorkManagementUserExtensionList",
                                otherParams: {
                                    orgId: orgID
                                }
                            }

                        },
                        searchBtnsStyle: "inline",
                        formConfig: [
                            {
                                isInTable: false,
                                form: {
                                    type: 'string',
                                    field: 'extensionHistoryId',
                                    hide: true
                                }
                            },
                            {

                                table: {
                                    dataIndex: "realName",
                                    title: "姓名",
                                    filter: true
                                },
                                form: {
                                    field: "realName", //表格里面的字段 
                                    label: "姓名",
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '性别',
                                    dataIndex: 'gender',
                                    type: 'select',
                                }
                                , form: {
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '男',
                                            value: '0'
                                        },
                                        {
                                            label: '女',
                                            value: '1'
                                        }
                                    ],
                                    placeholder: '请选择',
                                },
                            },
                            {
                                table: {
                                    title: '证件类型',
                                    dataIndex: 'idType',
                                    type: "select",
                                }
                                , form: {
                                    field: "idType", //表格里面的字段 
                                    label: "证件类型",
                                    required: true,
                                    type: "select",
                                    optionData: [
                                        //默认选项数据
                                        {
                                            label: "身份证",
                                            value: "0"
                                        },
                                        {
                                            label: "其他",
                                            value: "1"
                                        }
                                    ],
                                }
                            },
                            {
                                table: {
                                    title: '证件号',
                                    dataIndex: 'idNumber',
                                    filter: true
                                }
                                , form: {
                                    field: "idNumber", //表格里面的字段 
                                    label: "证件号",
                                    required: true,
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '政治面貌名字',
                                    dataIndex: 'politicCountenanceName',
                                }
                                , form: {
                                    type: 'string',
                                    label: '政治面貌名字',
                                    field: 'politicCountenanceName',
                                }
                            },
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "userType",
                                    title: "聘用类型",
                                    width: 120,
                                    type: 'select',
                                },
                                form: {
                                    type: 'select',
                                    field: 'userType',
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'pinYongLeiBie'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children',
                                    },
                                }
                            },
                        ],
                    },
                },
                {
                    type: "images",
                    desc: "上传近照",
                    label: " ",
                    colon: false,
                    field: "latestAttachmentList", //唯一的字段名 ***必传
                    fetchConfig: {
                        apiName: "upload"
                    },
                    max: 1,
                    className: "Upload-photo",
                    required: true,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                },
                {
                    type: 'date',
                    label: '出生年月',
                    field: 'birthday',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "userType", //表格里面的字段 
                    label: "聘用类型",
                    required: isRequiredStatus,
                    type: "select",
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'pinYongLeiBie'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children',
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "title", //表格的唯一key
                    label: "职称",
                    type: "select",
                    disabled: true,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhiChengMingCheng'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                },
                {
                    type: "select",
                    label: '所学专业',
                    field: 'major',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhuanye'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',// 
                    },

                },
                {
                    type: 'date',
                    label: '毕业时间',
                    field: 'graduateDate',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    label: '毕业学校',
                    field: 'graduateSchool',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: "string",
                    label: '所属单位/项目',
                    field: 'orgName',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                {
                    // projectId
                    type: "string",
                    field: 'orgId',
                    hide: true
                },
                {
                    field: "position", //表格的唯一key
                    label: "已审批岗位",
                    type: "cascader",
                    required: isRequiredStatus,
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: 'gangWeiGuanLi'
                        }
                    },
                    disabled: true,
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                },
                {
                    type: 'cascader',
                    label: '已审批岗位等级',
                    field: 'levelSalaryId',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                    fetchConfig: {
                        apiName: "getZjXmSalaryPositionLevelSalarySelect",
                        otherParams: {
                            itemId: 'gongrenzhongzhong'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'label', //默认 label
                        value: 'value',//
                        children: 'showData',
                    },
                },
                {
                    type: 'string',
                    label: '已审批岗薪',
                    field: 'positionSalary',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                // 推荐岗位
                {
                    type: 'cascader',
                    label: '推荐岗位',
                    field: 'positionAfter',
                    required: true,
                    span: 24,
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: 'gangWeiGuanLi'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children',
                    },
                    formItemLayout: {
                        labelCol: {
                            sm: { span: 4 }
                        },
                        wrapperCol: {
                            sm: { span: 20 }
                        }
                    },
                    disabled: isDetail
                },
            ],
            reserveCadre: [
                {
                    type: 'string',
                    label: 'extensionId',
                    field: 'extensionId',
                    hide: true
                },
                {
                    label: '姓名',
                    field: 'realName',
                    required: true,
                    span: 12,
                    disabled: isDetail,
                    type: 'selectByQnnTable',
                    optionConfig: {
                        label: 'realName',
                        value: 'realName',
                        searchKey: "realName"
                    },
                    allowClear: false,
                    onChange: async (val, obj) => {
                        let newData = null
                        const { data, success, message } = await ChildPropsData.myFetch('getWorkManagementUserExtensionDetails', { extensionId: val })
                        if (success) {
                            newData = {
                                ...data,
                                positionSalary: data.levelSalaryId ? data.levelSalaryId.split(',')[data.levelSalaryId.split(',').length - 1] : '',
                                extensionId: val
                            }

                            refsMap[type].setValues(newData)
                        } else {
                            Msg.error(message)
                            refsMap[type].setValues({ realName: '' })
                            // setIsModalVisible(false)
                            return
                        }
                    },
                    dropdownMatchSelectWidth: 900,
                    qnnTableConfig: {
                        antd: {
                            rowKey: "extensionId"
                        },
                        rowSelection: {
                            hideSelectAll: true,
                        },
                        fetchConfig: (obj) => {
                            return {
                                apiName: "getWorkManagementUserExtensionList",
                                otherParams: {
                                    orgId: orgID
                                }
                            }

                        },
                        searchBtnsStyle: "inline",
                        formConfig: [
                            {
                                isInTable: false,
                                form: {
                                    type: 'string',
                                    field: 'extensionHistoryId',
                                    hide: true
                                }
                            },
                            {

                                table: {
                                    dataIndex: "realName",
                                    title: "姓名",
                                    filter: true
                                },
                                form: {
                                    field: "realName", //表格里面的字段 
                                    label: "姓名",
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '性别',
                                    dataIndex: 'gender',
                                    type: 'select',
                                }
                                , form: {
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: '男',
                                            value: '0'
                                        },
                                        {
                                            label: '女',
                                            value: '1'
                                        }
                                    ],
                                    placeholder: '请选择',
                                },
                            },
                            {
                                table: {
                                    title: '证件类型',
                                    dataIndex: 'idType',
                                    type: "select",
                                }
                                , form: {
                                    field: "idType", //表格里面的字段 
                                    label: "证件类型",
                                    required: true,
                                    type: "select",
                                    optionData: [
                                        //默认选项数据
                                        {
                                            label: "身份证",
                                            value: "0"
                                        },
                                        {
                                            label: "其他",
                                            value: "1"
                                        }
                                    ],
                                }
                            },
                            {
                                table: {
                                    title: '证件号',
                                    dataIndex: 'idNumber',
                                    filter: true
                                }
                                , form: {
                                    field: "idNumber", //表格里面的字段 
                                    label: "证件号",
                                    required: true,
                                    type: "string",
                                }
                            },
                            {
                                table: {
                                    title: '政治面貌名字',
                                    dataIndex: 'politicCountenanceName',
                                }
                                , form: {
                                    type: 'string',
                                    label: '政治面貌名字',
                                    field: 'politicCountenanceName',
                                }
                            },
                            {
                                isInForm: false,
                                table: {
                                    dataIndex: "userType",
                                    title: "聘用类型",
                                    width: 120,
                                    type: 'select',
                                },
                                form: {
                                    type: 'select',
                                    field: 'userType',
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'pinYongLeiBie'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children',
                                    },
                                }
                            },
                        ],
                    },
                },
                {
                    type: "images",
                    desc: "上传近照",
                    label: " ",
                    colon: false,
                    field: "latestAttachmentList", //唯一的字段名 ***必传
                    fetchConfig: {
                        apiName: "upload"
                    },
                    max: 1,
                    className: "Upload-photo",
                    required: true,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: isDetail,
                },
                {
                    label: '性别',
                    field: 'gender',
                    type: 'select',
                    optionConfig: {
                        label: 'label',
                        value: 'value'
                    },
                    span: 12,
                    optionData: [
                        {
                            label: '男',
                            value: '0'
                        },
                        {
                            label: '女',
                            value: '1'
                        }
                    ],
                    formItemLayout: fourItemLayout,
                    placeholder: '请选择',
                    disabled: true
                },
                {
                    type: 'date',
                    label: '出生年月',
                    field: 'birthday',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    label: '籍贯', //表头标题
                    field: 'nativePlace', //表格里面的字段
                    required: true,
                    type: 'cascader',
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    fetchConfig: {//配置后将会去请求下拉选项数据
                        apiName: 'getBaseCodeTree',
                        otherParams: {
                            itemId: 'xingzhengquhuadaima'
                        }
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "politicCountenance", //表格的唯一key
                    label: "政治面貌",
                    type: "select",
                    placeholder: "请输入",
                    required: isRequiredStatus,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhengzhimianmao'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    initialValue: "13",
                    span: 12,
                    disabled: true,
                    formItemLayout: fourItemLayout,
                },
                {
                    type: 'select',
                    label: '学历',
                    field: 'education',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'xueli'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',// 
                    },
                    disabled: true
                },
                {
                    type: 'date',
                    label: '毕业时间',
                    field: 'graduateDate',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'string',
                    label: '毕业学校',
                    field: 'graduateSchool',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'select',
                    label: '毕业专业',
                    field: 'major',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhuanye'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',// 
                    },
                    disabled: true
                },
                {
                    type: 'select',
                    label: '专业技术职务',
                    field: 'title',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'zhiChengMingCheng'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',// 
                    },
                    disabled: true
                },
                {
                    type: 'date',
                    label: '参加工作时间',
                    field: 'workFirstDate',
                    required: isRequiredStatus,
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    field: "userType", //表格里面的字段 
                    label: "聘用类型",
                    required: isRequiredStatus,
                    type: "select",
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: 'pinYongLeiBie'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children',
                    },
                    span: 12,
                    formItemLayout: fourItemLayout,
                    disabled: true
                },
                {
                    type: 'select',
                    label: '后备岗位',
                    field: 'positionAfter',
                    required: true,
                    span: 24,
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: 'tuijiangangwei'
                        }
                    },
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children',
                    },
                    formItemLayout: {
                        labelCol: {
                            sm: { span: 4 }
                        },
                        wrapperCol: {
                            sm: { span: 20 }
                        }
                    },
                    disabled: isDetail
                },
                {
                    type: "string",
                    label: '现任职务',
                    field: 'orgName',
                    required: isRequiredStatus,
                    span: 8,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                {
                    // projectId
                    type: "string",
                    field: 'orgId',
                    hide: true
                },
                {
                    type: "string",
                    label: '',
                    field: 'departmentName',
                    required: isRequiredStatus,
                    span: 8,
                    formItemLayout: fourItemLayout,
                    disabled: true,
                },
                {
                    // projectId
                    type: "string",
                    field: 'departmentId',
                    hide: true
                },
                {
                    field: "position", //表格的唯一key
                    label: "",
                    type: "cascader",
                    required: isRequiredStatus,
                    fetchConfig: {
                        apiName: "getBaseCodeTree",
                        otherParams: {
                            itemId: 'gangWeiGuanLi'
                        }
                    },
                    disabled: true,
                    optionConfig: {//下拉选项配置
                        label: 'itemName', //默认 label
                        value: 'itemId',//
                        children: 'children'
                    },
                    span: 8,
                    formItemLayout: fourItemLayout,
                },
            ]
        }

        return columnsMap[type]
    }
}

export default PersonColJson