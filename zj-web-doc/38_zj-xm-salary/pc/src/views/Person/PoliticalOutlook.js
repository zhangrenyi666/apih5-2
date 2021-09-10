import React, { useState, useEffect } from 'react'
import { message as Msg, Modal } from "antd";
import QnnForm from 'qnn-form'

const PoliticalOutlook = (props) => {
    let form = null
    const [isModalVisible, setIsModalVisible] = useState(false)

    const fourItemLayout = {
        labelCol: {
            sm: { span: 8 }
        },
        wrapperCol: {
            sm: { span: 16 }
        }
    }
    let nameOrPolitic = {
        name: props.basicInfo?.form.getFieldsValue().realName,
        politicCountenance: props.basicInfo?.form.getFieldsValue().politicCountenance
    }
    useEffect(() => {
        setIsModalVisible(props.visible)
    }, [props.visible])

    useEffect(() => {
    }, [])

    const colseModal = (isRefresh) => {
        setIsModalVisible(false)
        props.reSetVisible(isRefresh)
    }
    return (
        // 政治面貌通用组件
        <div>
            <Modal
                title="政治面貌"
                width={'50%'}
                visible={isModalVisible}
                destroyOnClose={true}
                onOk={async () => {
                    console.log(props)
                    const url = props.status === 'add' ? props.isPerson ? 'addZjXmSalaryPolitical' : 'addZjXmSalaryPoliticalHistory' : props.isPerson ? 'updateZjXmSalaryPolitical' : 'updateZjXmSalaryPoliticalHistory'

                    const isPersonParams = props.isPerson ? {
                        extensionId: props.id,
                        zjXmsSalaryPoliticalId: props.pk ? props.pk : null,
                    } : {
                        extensionHistoryId: props.id,
                        zjXmSalaryPoliticalHistoryId: props.pk ? props.pk : null,
                    }

                    const params = {
                        ...form.form.getFieldsValue(),
                        ...isPersonParams
                    }

                    const { success, message } = await props.myFetch(url, params)
                    if (success) {
                        Msg.success('保存成功！')
                        colseModal()
                    } else {
                        Msg.error(message)
                    }

                    colseModal(true)
                }}
                onCancel={() => {
                    colseModal()
                }}>

                <QnnForm
                    fetch={props.myFetch}
                    upload={props.myUpload}
                    headers={{ token: props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { form = me }}
                    method={{}}
                    componentsKey={{}}
                    fetchConfig={
                        () => {
                            return {
                                apiName: props.isPerson ? 'getZjXmSalaryPoliticalDetail' : 'getZjXmSalaryPoliticalHistoryDetail',
                                otherParams: props.isPerson ? {
                                    zjXmSalaryPoliticalId: props.pk
                                } : {
                                    zjXmSalaryPoliticalHistoryId: props.pk
                                }
                            }
                        }
                    }
                    formConfig={[
                        {
                            type: 'string',
                            label: '姓名',
                            field: 'name',
                            placeholder: '请输入',
                            required: false,
                            formItemLayout: fourItemLayout,
                            initialValue: nameOrPolitic.name,
                            disabled: true,
                            span: 12
                        },
                        {
                            field: "policitalStatus", //表格的唯一key
                            label: "政治面貌",
                            type: "select",
                            placeholder: "请输入",
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'zhengzhimianmao'
                                }
                            },
                            initialValue: nameOrPolitic.politicCountenance,
                            disabled: true,
                            optionConfig: {//下拉选项配置
                                label: 'itemName', //默认 label
                                value: 'itemId',//
                                children: 'children',
                                linkageFields: {
                                    "politicCountenanceName": "itemName"
                                }
                            },
                            span: 12,
                            formItemLayout: fourItemLayout,

                        },
                        {
                            type: 'select',
                            label: '党员组织关系所在地',
                            field: 'relationshipLoc',
                            placeholder: '请选择',
                            multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                            showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                            fetchConfig: {//配置后将会去请求下拉选项数据
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'dangYuanZuZhiGuanXiSuoZaiDi'
                                }
                            },
                            optionConfig: {//下拉选项配置
                                label: 'itemName', //默认 label
                                value: 'itemId',//
                                children: 'children'
                            },
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        {
                            type: 'string',
                            // type: 'treeSelect',
                            label: '所在党支部',
                            field: 'branchName',
                            // treeSelectOption: {
                            //     //配置参照oa拉人组件配置
                            //     fetchConfig: { apiName: '', otherParams: {} },
                            // },
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        {
                            type: 'date',
                            label: '加入党组织时间',
                            field: 'joinTime',
                            placeholder: '请选择',
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        {
                            type: 'string',
                            label: '介绍人',
                            field: 'introducePerson',
                            placeholder: '请输入',
                            span: 12,
                            required: false,
                        },
                        {
                            type: 'date',
                            label: '转为正式党员日期',
                            field: 'formalTime',
                            placeholder: '请选择',
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        // {
                        //     type: 'select',
                        //     label: '是否持一建证',
                        //     field: 'isHoldLicense',
                        //     placeholder: '请选择',
                        //     optionData: [
                        //         {
                        //             value: '0',
                        //             label: '否'
                        //         },
                        //         {
                        //             value: '1',
                        //             label: '是'
                        //         },
                        //     ],
                        //     required: false,
                        //     formItemLayout: fourItemLayout,
                        //     span: 12
                        // },
                        {
                            type: 'select',
                            label: '党籍状态',
                            field: 'type',
                            placeholder: '请选择',
                            multiple: false, //是否开启多选功能 开启后自动开启搜索功能
                            showSearch: false, //是否开启搜索功能 (移动端不建议开启)
                            fetchConfig: {//配置后将会去请求下拉选项数据
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: 'dangJiZhuangTai'
                                }
                            },
                            optionConfig: {//下拉选项配置
                                label: 'itemName', //默认 label
                                value: 'itemId',//
                                children: 'children'
                            },
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        {
                            type: 'select',
                            label: '是否失联党员',
                            field: 'isLost',
                            placeholder: '请选择',
                            optionData: [
                                {
                                    value: '0',
                                    label: '否'
                                },
                                {
                                    value: '1',
                                    label: '是'
                                },
                            ],
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        {
                            type: 'date',
                            label: '失去联系日期',
                            field: 'lostTime',
                            placeholder: '请选择',
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        {
                            type: 'select',
                            label: '是否流动党员',
                            field: 'isMobile',
                            placeholder: '请选择',
                            optionData: [
                                {
                                    value: '0',
                                    label: '否'
                                },
                                {
                                    value: '1',
                                    label: '是'
                                },
                            ],
                            required: false,
                            formItemLayout: fourItemLayout,
                            span: 12
                        },
                        {
                            type: 'textarea',
                            label: '外出流向',
                            field: 'mobileLoc',
                            placeholder: '请输入',
                            required: false,
                            formItemLayout: {
                                labelCol: {
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    sm: { span: 20 }
                                }
                            },
                            span: 24
                        },
                    ]}
                />
            </Modal>

        </div>
    )
}

export default PoliticalOutlook