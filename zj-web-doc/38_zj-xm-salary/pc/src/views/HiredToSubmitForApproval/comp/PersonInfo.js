import React, { useState, useEffect } from 'react'
import { message as Msg, Modal, Button, Tabs } from "antd";
import QnnTable from 'qnn-table';
import QnnForm from 'qnn-form'
import resolve from 'resolve';
const { TabPane } = Tabs;
const { confirm } = Modal;

const PersonInfo = (props) => {
    // propsData:父节点传来的props  modalShowStatus:弹窗的显示状态 closeCb:他弹窗关闭的回调函数 tabsDataFunc:返回tab的数据
    const { propsData, modalShowStatus, closeCb, tabsDataFunc } = props
    // ------------------------------状态区------------------------------
    const [isModalVisible, setIsModalVisible] = useState(false);    // 弹窗显示状态
    const [tabShow, setTtabShow] = useState(true);    // 后续tab得是否禁用

    const fourItemLayout = {
        labelCol: {
            sm: { span: 8 }
        },
        wrapperCol: {
            sm: { span: 16 }
        }
    }

    const fourItemLayout2 = {
        labelCol: {
            sm: { span: 12 }
        },
        wrapperCol: {
            sm: { span: 12 }
        }
    }
    const oneItemLayout = {
        labelCol: {
            sm: { span: 2 }
        },
        wrapperCol: {
            sm: { span: 22 }
        }
    }
    const oneFormItemWrapperStyle = -5

    const fourItemSpan = 6

    let tabOne = null
    let tabTwo = null
    let tabThree = null
    let tabFour = null
    let tabFive = null
    let tabSix = null
    let tabSeven = null
    let tabEight = null
    let tabNine = null
    let tabTen = null

    let currentTabIndex = '1'
    // ------------------------------------------------------------------

    useEffect(() => {
        if (modalShowStatus === 'add') {
            setTtabShow(true)
            setIsModalVisible(true)
        } else if (modalShowStatus === 'edit') {
            setTtabShow(false)
            setIsModalVisible(true)
        } else {
            setIsModalVisible(false)
        }
    }, [modalShowStatus])

    useEffect(() => {
        if (!isModalVisible) {
            closeCb('')
        }
    }, [isModalVisible])

    const callback = (activeKey) => {
        currentTabIndex = activeKey
    }

    const batchSaveFunc = (index) => {
        return new Promise((resolve, reject) => {
            // 接口的 map
            const fetchMap = {
                tabOne: '',
                tabTwo: '',
                tabThree: '',
                tabFour: '',
                tabFive: '',
                tabSix: '',
                tabSeven: '',
                tabEight: '',
                tabNine: '',
                tabTen: '',
            }

            let key = ''
            Object.keys(fetchMap).map((item, objIndex) => {
                if (objIndex === index) {
                    key = item
                }
            })

            propsData.myFetch(fetchMap[key], key.getTableData()).then(({ data, message, success }) => {
                if (success) {
                    resolve()
                    Msg.success('保存成功')
                } else {
                    reject()
                    Msg.error(message)
                }
            })
        })
    }

    return (
        <div>
            <Modal
                title="人员信息"
                visible={isModalVisible}
                width={'80%'}
                onOk={async () => {
                    tabOne.form.validateFields().then(val => {
                        batchSaveFunc(+currentTabIndex - 1).then(val => {
                            currentTabIndex = '1'
                            setIsModalVisible(false)
                        })
                    }).catch(err => {

                    })

                    // if (modalShowStatus === 'add') {
                    //     const dataList = await tabOne.getValues()
                    //     // 新增 保存接口
                    //     tabOne.form.validateFields().then(val => {
                    //         propsData.myFetch('', dataList).then(res => {
                    //             currentTabIndex = '1'
                    //             setIsModalVisible(false)
                    //         })
                    //     }).catch(err => {

                    //     })
                    // } else if (modalShowStatus === 'edit') {

                    //     // 编辑 保存接口
                    //     tabOne.form.validateFields().then(val => {
                    //         batchSaveFunc(+currentTabIndex - 1).then(val => {
                    //             currentTabIndex = '1'
                    //             setIsModalVisible(false)
                    //         })
                    //     }).catch(err => {

                    //     })
                    // }
                }}
                onCancel={() => {
                    confirm({
                        title: '温馨提示',
                        content: '确定要离开弹窗?',
                        onOk() {
                            currentTabIndex = '1'
                            setIsModalVisible(false)
                        },
                        onCancel() { },
                    })
                }}
                destroyOnClose={true}
                okText={'保存'}
                confirmLoading={false}
            >
                <Tabs defaultActiveKey="1" onChange={callback}>
                    <TabPane tab="基础信息" key="1" >
                        <QnnForm
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabOne = me }}
                            method={{}}
                            formConfig={[
                                {
                                    type: 'string',
                                    label: 'extensionId',
                                    field: 'extensionId',
                                    hide: true
                                },
                                {
                                    type: 'string',
                                    label: '姓名',
                                    field: 'realName',
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "gender", //表格里面的字段 
                                    label: "性别",
                                    required: true,
                                    type: "radio",
                                    optionData: [
                                        //默认选项数据
                                        {
                                            label: "男",
                                            value: "0"
                                        },
                                        {
                                            label: "女",
                                            value: "1"
                                        }
                                    ],
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "nation", //表格的唯一key
                                    label: "民族",
                                    required: true,
                                    type: "select",
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'minzhu'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',// 
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,

                                    formItemWrapperStyle: {
                                        marginRight: '10px', //这样就可以把图片多占的一列补起来
                                    }
                                },
                                {
                                    type: 'date',
                                    label: '出生年月',
                                    field: 'birthday',
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
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
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "politicCountenance", //表格的唯一key
                                    label: "政治面貌",
                                    type: "select",
                                    placeholder: "请输入",
                                    required: true,
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
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    formItemWrapperStyle: {
                                        marginRight: '10px', //这样就可以把图片多占的一列补起来
                                    }
                                },
                                {
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
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "idNumber", //表格里面的字段 
                                    label: "证件号",
                                    required: true,
                                    type: "string",
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "userStatus", //表格的唯一key
                                    label: "人员状态",
                                    type: "select",
                                    placeholder: "请输入",
                                    required: true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'renYuanZhuangTai'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    formItemWrapperStyle: {
                                        marginRight: '10px', //这样就可以把图片多占的一列补起来
                                    }
                                },


                                {
                                    label: '现居住地', //表头标题
                                    field: 'presentAddress', //表格里面的字段
                                    required: true,
                                    type: 'cascader',
                                    placeholder: '请选择',
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
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    // label: '详细地址', //表头标题
                                    field: 'presentDetailedAddress', //表格里面的字段
                                    type: 'string',
                                    placeholder: '请输入详细地址',
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    formItemStyle: {
                                        marginLeft: 0
                                    },
                                    required: true,
                                },
                                {
                                    label: '邮编', //表头标题
                                    field: 'postalCode', //表格里面的字段
                                    type: 'postalCode',
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    required: true,
                                    formItemWrapperStyle: {
                                        marginRight: '10px', //这样就可以把图片多占的一列补起来
                                    }
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
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    formItemWrapperStyle: {
                                        position: "absolute",
                                        right: "60px"
                                    }
                                },
                                {
                                    label: '户口所在地', //表头标题
                                    field: 'residenceAddress', //表格里面的字段
                                    required: true,
                                    type: 'cascader',
                                    placeholder: '请选择',
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
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: 'residenceDetailedAddress', //表格里面的字段
                                    type: 'string',
                                    placeholder: '请输入详细地址',
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    formItemStyle: {
                                        marginLeft: 0
                                    },
                                    required: true,
                                },
                                {
                                    field: "phoneNumber", //表格的唯一key
                                    label: "联系电话",
                                    type: "phone",
                                    placeholder: "请输入",
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "maritalStatus", //表格的唯一key
                                    label: "婚姻状况",
                                    type: "select",
                                    placeholder: "请输入",
                                    required: true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'hunYinZhuangKuang'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },


                                {
                                    label: '法律文书送达地址', //表头标题
                                    field: 'legalAddress', //表格里面的字段
                                    required: true,
                                    type: 'cascader',
                                    placeholder: '请选择',
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
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout2,
                                },
                                {
                                    field: 'legalDetailedAddress', //表格里面的字段
                                    type: 'string',
                                    placeholder: '请输入详细地址',
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    formItemStyle: {
                                        marginLeft: 0
                                    },
                                    required: true,
                                },
                                {
                                    type: 'date',
                                    label: '参加工作时间',
                                    field: 'workFirstDate',
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout2,
                                },
                                {
                                    type: 'date',
                                    label: '入职时间',
                                    field: 'hiredate',
                                    placeholder: '请选择',
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },


                                {
                                    field: "title", //表格的唯一key
                                    label: "职称",
                                    type: "select",
                                    disabled: true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'minzhu'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "certificateName", //表格的唯一key
                                    label: "职业资格",
                                    type: "string",
                                    disabled: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "userType", //表格的唯一key
                                    label: "人员类别",
                                    type: "select",
                                    required: true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'pinYongLeiBie'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout2,
                                },
                                {
                                    field: "position", //表格的唯一key
                                    label: "岗位",
                                    type: "cascader",
                                    required: true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeTree",
                                        otherParams: {
                                            itemId: 'gangWeiGuanLi'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },


                                {
                                    field: "positionType", //表格的唯一key
                                    label: "兼职情况",
                                    type: "select",
                                    disabled: true,
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'minzhu'
                                        }
                                    },
                                    optionConfig: {//下拉选项配置
                                        label: 'itemName', //默认 label
                                        value: 'itemId',//
                                        children: 'children'
                                    },
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                },
                                {
                                    field: "projectTree",
                                    label: "单位/项目",
                                    type: "treeSelect",
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    treeSelectOption: {
                                        selectType: "1",
                                        maxNumber: 1,
                                        fetchConfig: {
                                            //配置后将会去请求下拉选项数据
                                            apiName: "getSysDepartmentCurrentTree",
                                            paramsKey: "departmentParentId",
                                        }
                                    }
                                },
                                // {
                                //     field: "projectTree",
                                //     label: "单位/项目",
                                //     type: "select",
                                //     required: true,
                                //     span: fourItemSpan,
                                //     formItemLayout: fourItemLayout,
                                //     fetchConfig: {
                                //         apiName: "getSysCompanyProject",                                                
                                //     },
                                //     optionConfig: {//下拉选项配置
                                //         label: 'companyName', //默认 label
                                //         value: 'companyId'
                                //     },
                                // },
                                {
                                    field: "departmentTree",
                                    label: "所属部门",
                                    type: "treeSelect",
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    treeSelectOption: {
                                        selectType: "1",
                                        maxNumber: 1,
                                        fetchConfig: {
                                            //配置后将会去请求下拉选项数据
                                            apiName: "getSysDepartmentCurrentTree",
                                            paramsKey: "departmentParentId",
                                        }
                                    }
                                },
                                {
                                    field: "officeTree",
                                    label: "所属科室",
                                    type: "treeSelect",
                                    required: true,
                                    span: fourItemSpan,
                                    formItemLayout: fourItemLayout,
                                    treeSelectOption: {
                                        selectType: "1",
                                        maxNumber: 1,
                                        fetchConfig: {
                                            //配置后将会去请求下拉选项数据
                                            apiName: "getSysDepartmentCurrentTree",
                                            paramsKey: "departmentParentId",
                                        }
                                    }
                                },



                                {
                                    field: "hobby", //表格的唯一key
                                    label: "爱好及特长",
                                    type: "textarea",
                                    formItemLayout: oneItemLayout,
                                    formItemWrapperStyle: oneFormItemWrapperStyle
                                },


                                {
                                    type: 'files',
                                    label: '身份证',
                                    field: 'idAttachmentList',
                                    required: false,
                                    fetchConfig: { apiName: 'upload' },
                                    max: 2,
                                    formItemLayout: oneItemLayout,
                                    formItemWrapperStyle: oneFormItemWrapperStyle
                                },

                                {
                                    type: "qnnForm",
                                    field: "healthInfo",
                                    label: "健康情况",
                                    formFields: [
                                        // {
                                        //     field: "健康情况Id",
                                        //     hide: true,
                                        //     type: "string"
                                        // },
                                        {
                                            label: "体检类型",
                                            field: "physicalType",
                                            type: "select",
                                            disabled: true,
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'gongrenzhongzhong'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',//
                                                children: 'children'
                                            },
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                        {
                                            label: "健康状况",
                                            field: "healthCondition",
                                            type: "select",
                                            disabled: true,
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'gongrenzhongzhong'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',//
                                                children: 'children'
                                            },
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                        {
                                            label: "职业病情况",
                                            field: "occupationalDisease",
                                            type: "select",
                                            disabled: true,
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'gongrenzhongzhong'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',//
                                                children: 'children'
                                            },
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },



                                        {
                                            field: "height",
                                            label: "身高/cm",
                                            type: "number",
                                            required: true,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                            placeholder: "请输入...",
                                        },
                                        {
                                            field: "weight",
                                            label: "体重/kg",
                                            type: "number",
                                            required: true,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                            placeholder: "请输入..."
                                        },


                                        {
                                            label: "血型",
                                            field: "bloodType",
                                            type: "select",
                                            optionData: [
                                                {
                                                    label: "A",
                                                    value: "0"
                                                },
                                                {
                                                    label: "B",
                                                    value: "1"
                                                },
                                                {
                                                    label: "AB",
                                                    value: "2"
                                                },
                                                {
                                                    label: "O",
                                                    value: "3"
                                                }
                                            ],
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                    ]
                                },



                                {
                                    type: "qnnForm",
                                    field: "salaryInfo",
                                    label: "薪酬情况",
                                    qnnFormConfig: {

                                        formConfig: [
                                            {
                                                label: "岗位等级",
                                                field: "levelSalaryId",
                                                type: "cascader",
                                                required: true,
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
                                                        "salaryInfo.salaryId": 'value',
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                //需要组件在 onChange 中返回当前所选的数据
                                                // onChange:(val, o)=>{
                                                //     console.log(val, o) 
                                                // }
                                            },

                                            {
                                                type: 'string',
                                                label: '岗薪',
                                                field: 'salaryId',
                                                disabled: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                label: "会计分类",
                                                field: "accountingType",
                                                type: "select",
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'kuaiJiFenLei'
                                                    }
                                                },
                                                optionConfig: {//下拉选项配置
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                label: '社保参保地', //表头标题
                                                field: 'socialInsuranceArea', //表格里面的字段
                                                required: true,
                                                type: 'select',
                                                placeholder: '请选择',
                                                optionConfig: {//下拉选项配置
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                fetchConfig: {//配置后将会去请求下拉选项数据
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                label: '公积金参保地', //表头标题
                                                field: 'providentFundArea', //表格里面的字段
                                                required: true,
                                                type: 'select',
                                                placeholder: '请选择',
                                                optionConfig: {//下拉选项配置
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                fetchConfig: {//配置后将会去请求下拉选项数据
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: "wageProjectTree",
                                                label: "工资关系所在项目",
                                                type: "treeSelect",
                                                required: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout2,
                                                treeSelectOption: {
                                                    selectType: "1",
                                                    maxNumber: 1,
                                                    fetchConfig: {
                                                        //配置后将会去请求下拉选项数据
                                                        apiName: "getSysDepartmentCurrentTree",
                                                        paramsKey: "departmentParentId",
                                                    }
                                                }
                                            },
                                            {
                                                label: '外在单位',
                                                field: 'externalUnit', //表格里面的字段
                                                required: true,
                                                type: 'select',
                                                placeholder: '请选择',
                                                optionConfig: {//下拉选项配置
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                fetchConfig: {//配置后将会去请求下拉选项数据
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'waiZaiDanWei'
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                label: '参与课题', //表头标题
                                                field: 'involvedTopic', //表格里面的字段
                                                required: true,
                                                type: 'select',
                                                placeholder: '请选择',
                                                optionConfig: {//下拉选项配置
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                fetchConfig: {//配置后将会去请求下拉选项数据
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'canYuKeTi'
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                        ],
                                    },
                                },
                                {
                                    type: "qnnForm",
                                    field: "contractInfo",
                                    label: "合同情况",
                                    formFields: [
                                        // {
                                        //     field: "劳动合同期限Id",
                                        //     hide: true,
                                        //     type: "string"
                                        // },
                                        {
                                            type: 'string',
                                            label: '合同编号',
                                            field: 'contractNo',
                                            required: false,
                                            disabled: true,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                        {
                                            type: 'date',
                                            label: '签订时间',
                                            field: 'signingDate',
                                            required: false,
                                            disabled: true,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                        {
                                            label: "合同类型",
                                            field: "contractType",
                                            type: "select",
                                            disabled: true,
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'gongrenzhongzhong'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',//
                                                children: 'children'
                                            },
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },

                                        {
                                            type: 'date',
                                            label: '合同起始时间',
                                            field: 'contractStartDate',
                                            placeholder: '请选择',
                                            disabled: true,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                        {
                                            type: 'date',
                                            label: '合同截止时间',
                                            field: 'contractEndDate',
                                            placeholder: '请选择',
                                            disabled: true,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                    ],
                                },

                            ]}
                        />
                    </TabPane>

                    <TabPane tab="学历情况" key="2" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabTwo = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[
                                {
                                    isInForm: false,
                                    table: {
                                        title: "入学时间",
                                        dataIndex: "graduateDate",
                                        key: "graduateDate",
                                        format: 'YYYY-MM-DD',
                                        align: "center",
                                        width: 150,
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "date",
                                        },
                                    },
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: "入学时间",
                                        dataIndex: "graduateSchool",
                                        key: "graduateSchool",
                                        align: "center",
                                        width: 150,
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "date",
                                        },
                                    },
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '学历',
                                        dataIndex: 'education',
                                        tdEdit: true,
                                        width: 150,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '学位',
                                        dataIndex: 'degree',
                                        tdEdit: true,
                                        width: 150,
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '专业',
                                        dataIndex: 'major',
                                        tdEdit: true,
                                        width: 150,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '学位授予时间',
                                        dataIndex: 'degreeAwardDate',
                                        format: 'YYYY-MM-DD',
                                        tdEdit: true,
                                        width: 150,
                                        fieldConfig: {
                                            type: "date"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '是否第一学历',
                                        dataIndex: 'isFirstEducation',
                                        tdEdit: true,
                                        width: 150,
                                        fieldConfig: {
                                            type: "radio",
                                            optionData: [
                                                {
                                                    label: "否",
                                                    value: "0"
                                                },
                                                {
                                                    label: "是",
                                                    value: "1"
                                                }
                                            ]
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '是否最高学历',
                                        dataIndex: 'isHighestEducation',
                                        tdEdit: true,
                                        width: 150,
                                        fieldConfig: {
                                            type: "radio",
                                            optionData: [
                                                {
                                                    label: "否",
                                                    value: "0"
                                                },
                                                {
                                                    label: "是",
                                                    value: "1"
                                                }
                                            ]
                                        }
                                    }
                                },
                            ]}
                        />
                    </TabPane>

                    <TabPane tab="工作履历" key="3" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabThree = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[

                                {
                                    isInForm: false,
                                    table: {
                                        title: '起始日期',
                                        dataIndex: 'startDate',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: 'date',
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '截止日期',
                                        dataIndex: 'endDate',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: 'date',
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '工作单位',
                                        dataIndex: 'workUnit',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '所在部门',
                                        dataIndex: 'department',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '岗位',
                                        dataIndex: 'position',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '主要工作内容',
                                        dataIndex: 'workContent',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '证明人',
                                        dataIndex: 'certifier',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '联系电话',
                                        dataIndex: 'phoneNumber',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: "phone"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '主、兼职',
                                        dataIndex: 'positionType',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryWorkExperience"
                                        },
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        }
                                    }
                                },

                            ]}
                        />
                    </TabPane>

                    <TabPane tab="专业技术" key="4" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabFour = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[

                                {
                                    isInForm: false,
                                    table: {
                                        title: '职称名称',
                                        dataIndex: 'title',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '职称专业',
                                        dataIndex: 'specialty',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '职称级别',
                                        dataIndex: 'level',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '职称取得途径',
                                        dataIndex: 'access',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '取得资格文号',
                                        dataIndex: 'qualificationNo',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },

                                {
                                    isInForm: false,
                                    table: {
                                        title: '取得资格时间',
                                        dataIndex: 'acquisitionDate',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: 'date',
                                            placeholder: '请选择',
                                            required: false,

                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '证书编号',
                                        dataIndex: 'certificateNo',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '发证单位',
                                        dataIndex: 'issueUnit',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '附件',
                                        dataIndex: 'technologyAttachmentList',
                                        tdEdit: true,
                                        tdEditFetchConfig: {
                                            apiName: "updateZjXmSalaryProfessionalTechnology"
                                        },
                                        fieldConfig: {
                                            type: "files",
                                            max: 1,
                                            fetchConfig: {
                                                //配置后将会去请求下拉选项数据
                                                apiName: "upload"
                                            },
                                        }
                                    }
                                },





                            ]}
                        />
                    </TabPane>

                    <TabPane tab="合同管理" key="5" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabFive = me }}
                            method={{}}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[
                                {
                                    isInForm: false,
                                    table: {
                                        title: '合同编号',
                                        dataIndex: 'contractNo',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 150,
                                        title: '合同签订时间',
                                        dataIndex: 'signingDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '合同类型',
                                        dataIndex: 'contractType',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 150,
                                        title: '劳动合同期限·始',
                                        dataIndex: 'startDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 150,
                                        title: '劳动合同期限·止',
                                        dataIndex: 'endDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 150,
                                        title: '合同期限（月）',
                                        dataIndex: 'contractPeriod',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "number",
                                            disabled: true
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '试用期',
                                        dataIndex: 'probation',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            }
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '签订类型',
                                        dataIndex: 'signingType',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 150,
                                        title: '原单位离职证明',
                                        dataIndex: 'quitAttachmentList',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "files",
                                            max: 1,
                                            fetchConfig: {
                                                //配置后将会去请求下拉选项数据
                                                apiName: "upload"
                                            },
                                        },
                                    }
                                },

                                {
                                    isInForm: false,
                                    table: {
                                        title: '合同附件',
                                        dataIndex: 'contractAttachmentList',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "files",
                                            max: 1,
                                            fetchConfig: {
                                                //配置后将会去请求下拉选项数据
                                                apiName: "upload"
                                            },
                                        },
                                    }
                                },

                            ]}
                        />
                    </TabPane>

                    <TabPane tab="培训情况" key="6" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabSix = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[

                                {
                                    isInForm: false,
                                    table: {
                                        title: '起始日期',
                                        dataIndex: 'startDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '截止日期',
                                        dataIndex: 'endDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '培训名称',
                                        dataIndex: 'trainingName',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '培训类型',
                                        dataIndex: 'trainingType',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',//
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '培训学习方式',
                                        dataIndex: 'trainingMode',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',//
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '培训学时',
                                        dataIndex: 'trainingHours',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },

                                {
                                    isInForm: false,
                                    table: {
                                        title: '培训附件',
                                        dataIndex: 'trainingAttachmentList',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "files",
                                            max: 1,
                                            fetchConfig: {
                                                //配置后将会去请求下拉选项数据
                                                apiName: "upload"
                                            },
                                        },
                                    }
                                },

                            ]}
                        />
                    </TabPane>

                    <TabPane tab="家庭状况" key="7" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabSeven = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[
                                {
                                    isInForm: false,
                                    table: {
                                        title: '与本人关系',
                                        dataIndex: 'relationship',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },

                                {
                                    isInForm: false,
                                    table: {
                                        title: '姓名',
                                        dataIndex: 'name',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '工作单位及职务',
                                        dataIndex: 'unitPosition',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '住址',
                                        dataIndex: 'address',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '联系电话',
                                        dataIndex: 'phoneNumber',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "phone"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '是否紧急联系人',
                                        dataIndex: 'isUrgentLinkMan',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "radio",
                                            optionData: [
                                                {
                                                    label: "否",
                                                    value: "0"
                                                },
                                                {
                                                    label: "是",
                                                    value: "1"
                                                }
                                            ]
                                        },
                                    }
                                },


                            ]}
                        />
                    </TabPane>

                    <TabPane tab="健康情况" key="8" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabEight = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[
                                {
                                    isInForm: false,
                                    table: {
                                        title: '体检类型',
                                        dataIndex: 'physicalType',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },

                                {
                                    isInForm: false,
                                    table: {
                                        title: '体检机构',
                                        dataIndex: 'physicalInstitution',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '健康情况',
                                        dataIndex: 'healthCondition',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '职业病情况',
                                        dataIndex: 'occupationalDisease',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '附件',
                                        dataIndex: 'healthAttachmentList',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "files",
                                            max: 1,
                                            fetchConfig: {
                                                //配置后将会去请求下拉选项数据
                                                apiName: "upload"
                                            },
                                        },
                                    }
                                },

                            ]}
                        />
                    </TabPane>

                    <TabPane tab="证书管理" key="9" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabNine = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                    // addRowFetchConfig: {
                                    //     apiName: "addZjXmSalaryEducationBackground",
                                    //     params: {
                                    //         extensionId: "extensionId"
                                    //     },
                                    // },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[
                                {
                                    isInForm: false,
                                    table: {
                                        width: 140,
                                        fixed: "left",
                                        title: '证书类别',
                                        dataIndex: 'certificateType',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },

                                {
                                    isInForm: false,
                                    table: {
                                        width: 120,
                                        fixed: "left",
                                        title: '证书名称',
                                        dataIndex: 'certificateName',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 120,
                                        title: '证书专业',
                                        dataIndex: 'certificateMajor',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 120,
                                        title: '证书编号',
                                        dataIndex: 'certificateNo',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '签发日期',
                                        width: 140,
                                        dataIndex: 'issueDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 150,
                                        title: '证书履约所在项目',
                                        dataIndex: 'projectId',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        }
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 130,
                                        title: '一次性奖励标准',
                                        dataIndex: 'rewardStandard',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "string"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '分摊年度',
                                        dataIndex: 'apportionYear',
                                        tdEdit: true,

                                        width: 300,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                            multiple: true,
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '已发放年度',
                                        dataIndex: 'paidYear',
                                        tdEdit: true,
                                        width: 300,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                            multiple: true
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 120,
                                        title: '月度补贴标准',
                                        dataIndex: 'subsidyStandard',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "money"
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 140,
                                        title: '发放开始时间',
                                        dataIndex: 'startDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                            required: false,
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        width: 140,
                                        title: '发放截止时间',
                                        dataIndex: 'endDate',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },


                                {
                                    isInForm: false,
                                    table: {
                                        width: 130,
                                        title: '附件',
                                        dataIndex: 'certificateAttachmentList',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "files",
                                            max: 1,
                                            fetchConfig: {
                                                //配置后将会去请求下拉选项数据
                                                apiName: "upload"
                                            },
                                        },
                                    }
                                },

                            ]}
                        />
                    </TabPane>

                    <TabPane tab="政治面貌" key="10" disabled={tabShow} >
                        <QnnTable
                            fetch={propsData.myFetch}
                            upload={propsData.myUpload}
                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { tabTen = me }}
                            method={{}}
                            antd={{
                                rowKey: "educationId",
                                size: "small"
                            }}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                params: {
                                    extensionId: "extensionId"
                                },
                            }}
                            actionBtns={[
                                {
                                    name: "addRow", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    addRowDefaultData: {}
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryEducationBackground',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                },
                            ]}
                            formConfig={[
                                {
                                    isInForm: false,
                                    table: {
                                        fixed: "left",
                                        title: '政治面貌',
                                        dataIndex: '政治面貌',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: "select",
                                            fetchConfig: {
                                                apiName: "getBaseCodeSelect",
                                                otherParams: {
                                                    itemId: 'minzhu'
                                                }
                                            },
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',// 
                                            },
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '参加时间',
                                        dataIndex: '参加时间',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                            required: false,
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '所在党支部',
                                        dataIndex: '所在党支部',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'string',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '参加时所在单位',
                                        dataIndex: '参加时所在单位',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'string',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '介绍人',
                                        dataIndex: '介绍人',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'string',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '转正时间',
                                        dataIndex: '转正时间',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'date',
                                        },
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '状态',
                                        dataIndex: '状态',
                                        tdEdit: true,
                                        fieldConfig: {
                                            type: 'string',
                                        },
                                    }
                                },
                            ]}
                        />
                    </TabPane>
                </Tabs>
            </Modal>
        </div>
    )
}

export default PersonInfo
