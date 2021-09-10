import React, { useState, useEffect } from 'react'
import { message as Msg, Modal } from "antd";
import QnnTable from 'qnn-table';
import QnnForm from 'qnn-form'
// import { getUserInfo } from "qnn-apih5"
import PersonColJson from './PersonColJson';
const { confirm } = Modal;
const PersonInterface = new PersonColJson()


const PersonInfo = (props) => {
    // propsData:父节点传来的props  modalShowStatus:弹窗的显示状态 closeCb:他弹窗关闭的回调函数 tabsDataFunc:返回tab的数据
    const { propsData, modalShowStatus, closeCb, primaryKey, type, extensionHistoryId } = props

    // const useInfo = getUserInfo(propsData);

    /*
        "approvalFlag":	"employ"(录聘用)
                        "salary"(岗薪)
                        "report"(报备)
                        "userPosition"(转岗)
                        "reserveCadre"(后备)
    */

    const apiMap = {
        queryFormApi: 'getWorkZjXmSalaryUserExtensionHistoryDetail',    // 人员详情 (岗薪,报备,转岗,后备)
        saveFormApi: 'addZjXmSalaryUserExtensionHistory',   // 报审新增 (录聘用,岗薪,报备,转岗,后备)
        updateFromApi: 'updateZjXmSalaryEmployApproval',    // 报审修改 (录聘用,岗薪,报备,转岗,后备)
        delFromApi: 'batchDeleteApproval',  // 报审 删除 (岗薪,报备,转岗,后备)
        personDelApi: 'batchDeleteZjXmSalaryUserExtensionHistory',   // 人员--删除	(岗薪,报备,转岗,后备)
        personAddApi: 'addZjXmSalaryUserExtensionHistory',  // 人员--保存  (岗薪,报备,转岗,后备)
        personUpdateApi: 'updateZjXmSalaryUserExtensionHistory',  // 人员--修改	(岗薪,报备,转岗,后备)
    }
    // let bottomTable = null
    // ------------------------------状态区------------------------------
    const [isModalVisible, setIsModalVisible] = useState(false);    // 弹窗显示状态
    // const [tabShow, setTtabShow] = useState(true);    // 后续tab得是否禁用
    const [isDetail, setIsDetail] = useState(true)
    const [extensionId, setExtensionId] = useState()

    // ------------------------------------------------------------------
    let refsMap = {
        salary: null,
        report: null,
        userPosition: null,
        reserveCadre: null,
    }

    useEffect(() => {
        if (modalShowStatus === 'add') {
            setIsDetail(false)
            setIsModalVisible(true)
        } else if (modalShowStatus === 'edit') {
            setIsDetail(false)
            setIsModalVisible(true)
        } else if (modalShowStatus === 'detail') {
            setIsDetail(true)
            setIsModalVisible(true)
        }
        else {
            setIsDetail(false)
            setIsModalVisible(false)
        }
    }, [modalShowStatus])


    useEffect(() => {
        if (!isModalVisible) {
            closeCb('')
        }
    }, [isModalVisible])

    return (
        <div>
            <Modal
                title="人员信息"
                visible={isModalVisible}
                width={'50%'}
                {
                ...isDetail ? { footer: null } : null
                }
                onOk={async () => {
                    // await refsMap[type].form.validateFields()   // 校验表单必填项

                    const modalData = await refsMap[type].getValues()
                    let params = {}
                    Object.keys(modalData).map(item => {
                        // if (item !== 'extensionId') {
                      return  params[item] = modalData[item]
                        // }
                    })
                    params['zjXmSalaryEmployApprovalId'] = primaryKey
                    params['extensionHistoryId'] = modalShowStatus === 'add' ? null : extensionHistoryId
                    params['approvalFlag'] = props.type
                    
                    const {  success, message } = await propsData.myFetch(modalShowStatus === 'add' ? apiMap.personAddApi : apiMap.personUpdateApi, params)
                    if (success) {
                        Msg.success('保存成功!')
                        closeCb('saveSuccess')
                    } else {
                        Msg.error(message)
                    }
                }}
                onCancel={() => {
                    confirm({
                        title: '温馨提示',
                        content: '确定要离开弹窗?',
                        onOk() {

                        },
                        onCancel() {
                            setIsModalVisible(false)
                        },
                        cancelText: '是',
                        okText: '否'
                    })
                }}
                destroyOnClose={true}
                okText={'保存'}
                confirmLoading={false}
            >
                <QnnForm
                    fetch={propsData.myFetch}
                    upload={propsData.myUpload}
                    headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { refsMap[type] = me }}
                    method={{
                        tabItemDisabled: (obj) => {
                            return !obj._formData()?.extensionId;
                        }
                    }}
                    fetchConfig={
                        modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                            apiName: 'getWorkZjXmSalaryUserExtensionHistoryDetail',//可为function 返回必须为string
                            otherParams: async () => {
                                return {
                                    extensionHistoryId: extensionHistoryId
                                }
                            },
                            success: ({ data, success, message }) => {
                                if (success) {
                                    setExtensionId(data.extensionId)
                                }
                            },
                        } : {}
                    }
                    formConfig={
                        PersonInterface.getColFunc(type, refsMap, isDetail, propsData, setIsModalVisible)
                    }

                />
                {props.isReview && extensionId ? <QnnTable
                    fetch={propsData.myFetch}
                    upload={propsData.myUpload}
                    headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                    // wrappedComponentRef={(me) => { bottomTable = me }}
                    antd={{
                        rowKey: "educationId",
                        size: "small"
                    }}
                    fetchConfig={{
                        apiName: 'getZjXmSalaryWorkExperienceList',
                        otherParams: {
                            extensionId: extensionId
                        }
                    }}
                    isShowRowSelect={false}
                    formConfig={[
                        {
                            isInForm: false,
                            table: {
                                title: '起讫时间',
                                dataIndex: 'startDate',
                                format: 'YYYY-MM-DD',
                                fieldConfig: {
                                    type: "date"
                                }
                            }
                        },
                        // endDate
                        // {
                        //     isInForm: false,
                        //     table: {
                        //         title: '截至时间',
                        //         dataIndex: 'endDate',
                        //         format: 'YYYY-MM-DD',
                        //       
                        //         fieldConfig: {
                        //             type: "date"
                        //         }
                        //     }
                        // },
                        {
                            isInForm: false,
                            table: {
                                title: '工作单位',
                                dataIndex: 'workUnit',
                                fieldConfig: {
                                    type: "string"
                                }
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '工作内容',
                                dataIndex: 'workContent',
                                fieldConfig: {
                                    type: "string"
                                }
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '担任职务',
                                dataIndex: 'position',
                                fieldConfig: {
                                    type: "string"
                                }
                            }
                        },
                    ]}
                /> : null}
            </Modal>
        </div >
    )
}

export default PersonInfo
