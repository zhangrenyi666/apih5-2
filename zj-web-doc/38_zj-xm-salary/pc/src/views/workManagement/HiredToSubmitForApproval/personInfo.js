import React, { useState, useEffect } from 'react'
import { message as Msg, Modal, Button, Tabs, Divider, Row, Col } from "antd";
import QnnTable from 'qnn-table';
import QnnForm from 'qnn-form'
import { getOrgId, getUserInfo } from "qnn-apih5"
import PoliticalOutlook from '../../Person/PoliticalOutlook'
import TableFileDownLoad from '../../common/tableFileDownLoad';
const { confirm } = Modal;
const { TabPane } = Tabs;

let refMap = {
    tabOne: null,
    tabTwo: null,
    tabThree: null,
    tabFour: null,
    tabFive: null,
    tabSix: null,
    tabSeven: null,
    tabEight: null,
    tabNine: null,
    tabTen: null,
}
const PersonInfo = (props) => {
    const { propsData, modalShowStatus, closeCb, primaryKey } = props
    const [isModalVisible, setIsModalVisible] = useState(false);    // 弹窗显示状态
    const [isModalVisibleToLz, setIsModalVisibleToLz] = useState(false);    // 弹窗显示状态 -- 离职人员
    const [tabShow, setTabShow] = useState(true);    // 后续tab得是否禁用
    const [extensionHistoryId, setExtensionId] = useState('')
    const [isDetail, setIsDetail] = useState(true)
    const [companyAnddepartment, setCompanyAnddepartment] = useState(null)
    const [currentTabIndex, setCurrentTabIndex] = useState('0')

    const { projectId, projectName } = getUserInfo(propsData).curCompany
    const orgId = getOrgId(propsData)
    const [politicalOutlookVisible, setpoliticalOutlookVisible] = useState(false)
    const [zzmmAddOrUpdate, setzzmmAddOrUpdate] = useState(null)
    const [extensionId, setextensionId] = useState(null)
    const [politicalPK, setpoliticalPK] = useState(null)
    useEffect(() => {
        if (modalShowStatus === 'add') {
            setTabShow(true)
            setIsDetail(false)
            setIsModalVisible(true)
        } else if (modalShowStatus === 'edit') {
            setTabShow(false)
            setIsDetail(false)
            setIsModalVisible(true)
        } else if (modalShowStatus === 'detail') {
            setTabShow(false)
            setIsDetail(true)
            setIsModalVisible(true)
        } else {
            setIsDetail(false)
            setIsModalVisible(false)
        }
    }, [modalShowStatus])

    useEffect(() => {
        if (!isModalVisible) {
            closeCb('')
        }
    }, [isModalVisible])

    useEffect(() => {
        setExtensionId(props.extensionHistoryId)
    }, [props.extensionHistoryId])

    useEffect(async () => {
        if (projectId) {
            const { data } = await propsData.myFetch('getSysComDeptProById', { departmentId: projectId })
            setCompanyAnddepartment({
                label: data.projectName ? data.projectName : data.companyName,
                value: data.projectId ? data.projectId : data.companyId,
            })
        }
    }, [])


    const callback = (index) => {
        setCurrentTabIndex(index)
    }
    // let modalTableRefs = null
    let selectedKeysForModal = null


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


    const setFieldlabelFunc = async (obj, name) => {
        const rowData = await obj.qnnTableInstance.getEditedRowData()
        await obj.qnnTableInstance.setEditedRowData({ ...rowData, [name]: obj.itemData.itemName })
    }

    const listAddUpdateFunc = async (curIndex) => {

        let key = ''
        let params = null
        // 
        const fetchMap = {
            tabOne: { // 基础信息
                apiName: modalShowStatus === 'add' ? 'addEmployUserExtensionHistory' : 'updateEmployUserExtensionHistory',
                paramsTiele: ''
            },
            tabTwo: { // 学历情况
                apiName: 'batchEducationHistory',
                paramsTiele: 'educationHistoryList'
            },
            tabThree: { // 工作履历
                apiName: 'batchWorkExperienceHistory',
                paramsTiele: 'workExperienceHistoryList'
            },
            tabFour: { // 专业技术
                apiName: 'batchAddUpdateZjXmSalaryProfessionalTechnologyHistory',
                paramsTiele: 'professionalTechnologyHistoryList'
            },
            tabFive: { // 合同管理
                apiName: 'batchAddUpdateZjXmSalaryContractManagementHistory',
                paramsTiele: 'contractManagementHistoryList'
            },
            tabSix: { // 培训情况
                apiName: 'batchAddUpdateZjXmSalaryTrainingSituationHistory',
                paramsTiele: 'trainingSituationHistoryList'
            },
            tabSeven: { // 家庭状况
                apiName: 'batchAddUpdateZjXmSalaryFamilyBackgroundHistory',
                paramsTiele: 'familyBackgroundHistoryList'
            },
            tabEight: { // 健康情况
                apiName: 'batchAddUpdateZjXmSalaryHealthConditionHistory',
                paramsTiele: 'healthConditionHistoryList'
            },
            tabNine: { // 证书管理
                apiName: 'batchAddUpdateZjXmSalaryCertificateManagementHistory',
                paramsTiele: 'certificateManagementHistoryList'
            },
            tabTen: { //政治面貌
                apiName: 'zzmm',
                paramsTiele: ''
            }
        }
        Object.keys(fetchMap).map((item, index) => {
            if (curIndex === index) {
                key = item
            }
            return true
        })

        if (key === 'tabOne') {
            refMap[key].form.validateFields().then(async () => {
                const { companyId, companyName, projectId, projectName } = getUserInfo(propsData).curCompany
                const formData = await refMap[key].getValues()
                const resData = await propsData.myFetch('getBaseCodeTree', { itemId: 'gangWeiGuanLi' })

                if (resData.success) {
                    let arrList = []
                    let TemporaryData = resData.data
                    const positionArr = formData.position ? formData.position.split(',') : []

                    const outerLoopFunc = (aouterData, targetData, i) => {
                        targetData.map(item => {
                            if (aouterData === item.itemId) {
                                arrList[i] = item.itemName
                                TemporaryData = item.children
                            }
                            return true
                        })
                    }

                    if (positionArr.length) {
                        for (let i = 0; i < positionArr.length; i++) {
                            outerLoopFunc(positionArr[i], TemporaryData, i)
                        }

                        params = {
                            ...await refMap[key].getValues(),
                            extensionHistoryId,
                            zjXmSalaryEmployApprovalId: primaryKey,
                            positionName: arrList.join('/'),
                            companyId,
                            companyName,
                            projectId,
                            projectName,
                            workPersonnelFlag: 0,
                        }
                        if (modalShowStatus === 'add') {
                            params['extensionHistoryId'] = null   // extensionHistoryId
                        }
                    }

                }

                const { data, success, message } = await propsData.myFetch(fetchMap[key].apiName, params)

                if (success) {
                    Msg.success('保存成功')
                    if (modalShowStatus === 'add') {
                        closeCb('edit', data)
                    } else if (modalShowStatus === 'edit') {
                        closeCb('edit')
                    }
                    document.querySelector(".ant-modal-wrap").scrollTo({ top: 0, behavior: "smooth" })
                    refMap[key] && refMap[key].refresh()
                } else {
                    Msg.error(message)
                }

            })
        } else {

            /*
                字段名       enrollmentDateGraduateDate           startDateEndDate     startDateEndDate      startDateEndDate     startDateEndDate
                中文名               学历情况                          工作履历             合同管理              培训情况              证书管理  
                key                  tabTwo                           tabThree            tabFive               tabSix               tabNine
                主键           educationHistoryId                experienceHistoryId   contractHistoryId     trainingHistoryId     certificateHistoryId
            */

            if (refMap[key].getTableData().length) {
                if (key === 'tabTwo' || key === 'tabThree' || key === 'tabFive' || key === 'tabSix' || key === 'tabSeven' || key === 'tabNine') {
                    let tdRefsMap = {
                        startDateEndDate: [],
                        enrollmentDateGraduateDate: [],
                        phoneNumber: []
                    }
                    const tableData = await refMap[key].getTableData()
                    tableData.map(async (item, i) => {
                        let pKey = ''
                        switch (key) {
                            case 'tabTwo':
                                pKey = 'educationHistoryId'
                                break
                            case 'tabThree':
                                pKey = 'experienceHistoryId'
                                break
                            case 'tabFive':
                                pKey = 'contractHistoryId'
                                break
                            case 'tabSix':
                                pKey = 'trainingHistoryId'
                                break
                            case 'tabSeven':
                                pKey = 'familyHistoryId'
                                break
                            case 'tabNine':
                                pKey = 'certificateHistoryId'
                                break
                            default:
                                break
                        }

                        if (key === 'tabSeven') {
                            tdRefsMap['phoneNumber'][i] = refMap[key].getTdRef({
                                rowId: item[pKey],
                                field: 'phoneNumber'
                            })
                        } else {
                            tdRefsMap[key === 'tabTwo' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'][i] = refMap[key].getTdRef({
                                rowId: item[pKey],
                                field: key === 'tabTwo' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'
                            })
                        }
                    })

                    let IsVerificationSuccess = true

                    if (key === 'tabSeven') {
                        tdRefsMap['phoneNumber'].map(item => {
                            const isPhoneNumber = /^1[3456789]\d{9}$/

                            if (!isPhoneNumber.test(item.getTdData())) {
                                IsVerificationSuccess = false
                                item.setErrorAlert('请填写正确的手机号！')
                            }
                            return true
                        })
                    } else {
                        tdRefsMap[key === 'tabTwo' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'].map(item => {
                            if (!item.getTdData()) {
                                IsVerificationSuccess = false
                                item.setErrorAlert('不能为空')
                            }
                            return true
                        })
                    }

                    tdRefsMap[key === 'tabTwo' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'].map(item => {
                        if (!item.getTdData()) {
                            IsVerificationSuccess = false
                            item.setErrorAlert('不能为空')
                        }
                        return true
                    })

                    if (!IsVerificationSuccess) {
                        return false
                    }

                }

                params = {
                    extensionHistoryId,
                    approvalFlag: 'employ'
                }

                params[fetchMap[key].paramsTiele] = [
                    ...(await refMap[key].getTableData()).map(item => {
                        return {
                            ...item,
                            extensionHistoryId,
                            zjXmSalaryEmployApprovalId: primaryKey,
                            workPersonnelFlag: 0,
                            approvalFlag: 'employ'
                        }
                    }),
                ]
                const { data, success, message } = await propsData.myFetch(fetchMap[key].apiName, params)

                if (success) {
                    Msg.success('保存成功')
                    if (modalShowStatus === 'add') {
                        closeCb('edit', data)
                    } else if (modalShowStatus === 'edit') {
                        closeCb('edit')
                    }

                    refMap[key] && refMap[key].refresh()
                } else {
                    Msg.error(message)
                }
            } else {
                Msg.warning('请填写内容再点击保存!')
            }

        }
    }

    const listDelFunc = (obj) => {

        let key = ''
        const fetchMap = {
            tabOne: { // 基础信息
                apiName: '',
                paramsTiele: ''
            },
            tabTwo: { // 学历情况
                apiName: 'batchDeleteZjXmSalaryEducationBackgroundHistory',
                paramsTiele: ''
            },
            tabThree: { // 工作履历
                apiName: 'batchDeleteZjXmSalaryWorkExperienceHistory',
                paramsTiele: ''
            },
            tabFour: { // 专业技术
                apiName: 'batchDeleteZjXmSalaryProfessionalTechnologyHistory',
                paramsTiele: ''
            },
            tabFive: { // 合同管理
                apiName: 'batchDeleteZjXmSalaryContractManagementHistory',
                paramsTiele: ''
            },
            tabSix: { // 培训情况
                apiName: 'batchDeleteZjXmSalaryTrainingSituationHistory',
                paramsTiele: ''
            },
            tabSeven: { // 家庭状况
                apiName: 'batchDeleteUpdateZjXmSalaryFamilyBackgroundHistory',
                paramsTiele: ''
            },
            tabEight: { // 健康情况
                apiName: 'batchDeleteZjXmSalaryHealthConditionHistory',
                paramsTiele: ''
            },
            tabNine: { // 证书管理
                apiName: 'batchDeleteZjXmSalaryCertificateManagementHistory',
                paramsTiele: ''
            },
            tabTen: { //政治面貌
                apiName: 'batchDeleteUpdateZjXmSalaryPoliticalHistory',
                paramsTiele: ''
            }
        }

        Object.keys(fetchMap).map((item, index) => {
            if (+currentTabIndex === index) {
                key = item
            }
            return true
        })

        let params = []
        obj.selectedRows.length && obj.selectedRows.map(item => {
            if (item.extensionHistoryId) {
                params.push(item)
            }
            return true
        })
        if (params.length) {
            confirm({
                title: '温馨提示',
                content: '删除后不可恢复,确定要删除吗?',
                onOk: async () => {
                    const { success, message } = await propsData.myFetch(fetchMap[key].apiName, params)
                    if (success) {
                        refMap[key].refresh()
                        refMap[key].clearSelectedRows()
                        Msg.success('删除成功!')
                    } else {
                        Msg.error(message)
                    }
                }
            })

        } else {
            refMap[key].refresh()
            refMap[key].clearSelectedRows()
            Msg.success('删除成功!')
        }
    }

    const highestDegreeOnly = (obj, row, key) => {
        const tableData = obj.btnCallbackFn.getTableData()
        let newTableData = []

        // 遍历table的数据
        tableData.map((item, index) => {
            if (item.educationHistoryId === row.rowData.educationHistoryId) {
                // 当前行
                newTableData[index] = { ...item }
            } else {
                // 如果是其他行, 检测是否最高学历 是否为 是 
                // 如果为 是 的话, 需要把此行设置为否

                if (item[key] === '1') {
                    newTableData[index] = { ...item, [key]: '0' }
                } else {
                    newTableData[index] = { ...item }
                }
            }
            return true
        })
        obj.btnCallbackFn.setTableData(newTableData)
    }
    return (
        <div>
            {
                companyAnddepartment ?
                    <Modal
                        title="人员信息"
                        visible={isModalVisible}
                        width={'80%'}
                        onOk={async (a, b, c) => {
                            await listAddUpdateFunc(+currentTabIndex)
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
                        {
                        ...isDetail ? { footer: null } : null
                        }
                        destroyOnClose={true}
                        okText={'保存'}
                        confirmLoading={false}
                    >
                        <Tabs defaultActiveKey="0" onChange={callback}>
                            {/* 基础信息
                        学历情况
                        工作履历
                        专业技术
                        合同管理
                        培训情况
                        家庭状况
                        健康情况
                        证书管理
                        政治面貌 */}
                            <TabPane tab="基础信息" key="0">
                                {modalShowStatus === 'add' ? <div >
                                    <Row align='middle'>
                                        <Col span={4}>
                                            <Button type="primary" onClick={() => {
                                                setIsModalVisibleToLz(true)
                                            }}>离职人员信息</Button>
                                        </Col>
                                        <Col span={20}>
                                            <div style={{ color: '#ff4d4f' }}>点击离职人员信息按钮, 选中人员信息后会将此条信息带入下方表单</div>
                                        </Col>
                                    </Row>
                                    <Divider />
                                </div> : null}
                                <QnnForm
                                    fetch={propsData.myFetch}
                                    upload={propsData.myUpload}
                                    headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => { refMap.tabOne = me }}
                                    method={{}}
                                    fetchConfig={
                                        modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                            apiName: 'getZjXmSalaryUserExtensionHistoryDetail',//可为function 返回必须为string
                                            otherParams: {
                                                extensionHistoryId: extensionHistoryId,
                                                approvalFlag: 'employ'
                                            },
                                        } : {}}
                                    componentsKey={{}}
                                    formConfig={[
                                        // reRecruit
                                        // extensionId
                                        {
                                            type: 'string',
                                            label: 'extensionId',
                                            field: 'extensionId',
                                            hide: true
                                        },
                                        {
                                            type: 'string',
                                            label: 'approvalFlag',
                                            field: 'approvalFlag',
                                            hide: true
                                        },
                                        {
                                            type: 'number',
                                            label: 'reRecruit',
                                            field: 'reRecruit',
                                            initialValue: 1,
                                            hide: true
                                        },
                                        {
                                            type: 'string',
                                            label: 'extensionHistoryId',
                                            field: 'extensionHistoryId',
                                            hide: true
                                        },
                                        {
                                            type: 'string',
                                            label: '姓名',
                                            field: 'realName',
                                            required: true,
                                            span: fourItemSpan,
                                            disabled: isDetail,
                                            formItemLayout: fourItemLayout,
                                        },
                                        {
                                            field: "gender", //表格里面的字段 
                                            label: "性别",
                                            required: true,
                                            type: "radio",
                                            disabled: isDetail,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                        },
                                        {
                                            label: '籍贯', //表头标题
                                            field: 'nativePlace', //表格里面的字段
                                            required: true,
                                            type: 'cascader',
                                            disabled: isDetail,
                                            optionConfig: {//下拉选项配置
                                                label: 'itemName', //默认 label
                                                value: 'itemId',//
                                                children: 'children',
                                                linkageFields: {
                                                    "nativePlaceName": "itemName",
                                                }
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
                                            type: 'string',
                                            label: '籍贯名字',
                                            field: 'nativePlaceName',
                                            hide: true
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
                                                children: 'children',
                                                linkageFields: {
                                                    "politicCountenanceName": "itemName",
                                                }
                                            },
                                            initialValue: "13",
                                            disabled: isDetail,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                            formItemWrapperStyle: {
                                                marginRight: '10px', //这样就可以把图片多占的一列补起来
                                            }
                                        },
                                        {
                                            type: 'string',
                                            label: '政治面貌名字',
                                            field: 'politicCountenanceName',
                                            initialValue: "群众",
                                            hide: true
                                        },
                                        {
                                            field: "idType", //表格里面的字段 
                                            label: "证件类型",
                                            required: true,
                                            type: "select",
                                            disabled: isDetail,
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
                                            onChange: async (val, obj) => {
                                                const idType = obj.form.getFieldsValue().idType
                                                const idNumber = obj.form.getFieldsValue().idNumber
                                                const parmas = {
                                                    idType,
                                                    idNumber,
                                                    orgId
                                                }
                                                if ((idType === '0' || idType) && idNumber) {
                                                    const { success, message } = await propsData.myFetch('checkIdNumber', parmas)
                                                    if (!success) {
                                                        obj.form.setFieldsValue({
                                                            idType: '',
                                                            idNumber: ''
                                                        })
                                                        Msg.error(message)
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            field: "idNumber", //表格里面的字段 
                                            label: "证件号",
                                            required: true,
                                            type: "string",
                                            disabled: isDetail,
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout,
                                            onBlur: async (val, obj) => {
                                                const idType = obj.form.getFieldsValue().idType
                                                const idNumber = obj.form.getFieldsValue().idNumber
                                                const parmas = {
                                                    idType,
                                                    idNumber,
                                                    orgId
                                                }
                                                if ((idType === '0' || idType) && idNumber) {
                                                    const { success, message } = await propsData.myFetch('checkIdNumber', parmas)
                                                    if (!success) {
                                                        obj.form.setFieldsValue({
                                                            idType: '',
                                                            idNumber: ''
                                                        })
                                                        Msg.error(message)
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            field: "userStatus", //表格的唯一key
                                            label: "人员状态",
                                            type: "select",
                                            placeholder: "请输入",
                                            required: true,
                                            disabled: isDetail,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
                                            field: "latestAttachmentList", //唯一的字段名 ***必传
                                            fetchConfig: {
                                                apiName: "upload"
                                            },
                                            max: 1,
                                            className: "Upload-photo",
                                            // required: true,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
                                            formItemLayout: fourItemLayout2,
                                        },
                                        {
                                            field: 'legalDetailedAddress', //表格里面的字段
                                            type: 'string',
                                            placeholder: '请输入详细地址',
                                            span: fourItemSpan,
                                            disabled: isDetail,
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
                                            disabled: isDetail,
                                            formItemLayout: fourItemLayout2,
                                        },
                                        {
                                            type: 'date',
                                            label: '入职时间',
                                            field: 'hiredate',
                                            placeholder: '请选择',
                                            required: true,
                                            span: fourItemSpan,
                                            disabled: isDetail,
                                            formItemLayout: fourItemLayout,
                                        },

                                        {
                                            field: 'title',
                                            hide: true
                                        },
                                        {
                                            field: "employTitle", //表格的唯一key
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
                                            disabled: isDetail,
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
                                                linkageFields: {
                                                    "userTypeName": "itemName",
                                                }
                                            },
                                            span: fourItemSpan,
                                            formItemLayout: fourItemLayout2,
                                        },
                                        {
                                            type: 'string',
                                            label: '人员类别名字',
                                            field: 'userTypeName',
                                            hide: true
                                        },
                                        {
                                            field: "position", //表格的唯一key
                                            label: "岗位",
                                            type: "cascader",
                                            required: true,
                                            disabled: isDetail,
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
                                                    itemId: 'zhuJianZhi'
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
                                            initialValue: [companyAnddepartment],
                                            formItemLayout: fourItemLayout,
                                            disabled: true,
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
                                            initialValue: [{ label: projectName, value: projectId }],
                                            disabled: true,
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
                                            disabled: isDetail,
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
                                            disabled: isDetail,
                                            formItemLayout: oneItemLayout,
                                            formItemWrapperStyle: oneFormItemWrapperStyle
                                        },


                                        {
                                            type: 'files',
                                            label: '身份证',
                                            field: 'idAttachmentList',
                                            required: false,
                                            disabled: isDetail,
                                            fetchConfig: { apiName: 'upload' },
                                            max: 2,
                                            formItemLayout: oneItemLayout,
                                            formItemWrapperStyle: oneFormItemWrapperStyle
                                        },

                                        {
                                            type: "qnnForm",
                                            field: "healthInfo",
                                            label: "健康情况",
                                            disabled: isDetail,
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
                                                    fetchConfig: {
                                                        apiName: "getBaseCodeSelect",
                                                        otherParams: {
                                                            itemId: 'tiJianLeiXing'
                                                        }
                                                    },
                                                    optionConfig: {//下拉选项配置
                                                        label: 'itemName', //默认 label
                                                        value: 'itemId',//
                                                        children: 'children'
                                                    },
                                                    span: fourItemSpan,
                                                    disabled: isDetail,
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
                                                            itemId: 'jianKangQingKuang'
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
                                                            itemId: 'zhiYeBingQingKuang'
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
                                                    disabled: isDetail,
                                                    formItemLayout: fourItemLayout,
                                                    placeholder: "请输入...",
                                                },
                                                {
                                                    field: "weight",
                                                    label: "体重/kg",
                                                    type: "number",
                                                    required: true,
                                                    span: fourItemSpan,
                                                    disabled: isDetail,
                                                    formItemLayout: fourItemLayout,
                                                    placeholder: "请输入..."
                                                },


                                                {
                                                    label: "血型",
                                                    field: "bloodType",
                                                    type: "select",
                                                    disabled: isDetail,
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
                                                            disabled: isDetail,
                                                            children: 'showData',
                                                            linkageFields: {
                                                                "salaryInfo.salaryId": 'value',
                                                            }
                                                        },
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                        onChange: async (val, obj) => {
                                                            const salaryId = val[val.length - 1]
                                                            const { data, success } = await props.propsData.myFetch('getPositionLevelSalaryDetails', { levelSalaryId: salaryId })
                                                            if (success) {
                                                                const formData = obj.form.getFieldsValue()
                                                                obj.form.setFieldsValue({
                                                                    salaryInfo: {
                                                                        ...formData.salaryInfo,
                                                                        salaryId,
                                                                        positionSalary: data[0].positionSalary
                                                                    }
                                                                })
                                                            }
                                                        }
                                                    },
                                                    {
                                                        type: 'string',
                                                        label: '岗薪',
                                                        field: 'positionSalary',
                                                        type: "number",
                                                        required: true,
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                        disabled: true,
                                                    },
                                                    {
                                                        field: 'salaryId',
                                                        hide: true,
                                                    },
                                                    {
                                                        label: "会计分类",
                                                        field: "accountingType",
                                                        type: "select",
                                                        disabled: isDetail,
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
                                                        disabled: isDetail,
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
                                                        disabled: isDetail,
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
                                                        disabled: isDetail,
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
                                                        disabled: isDetail,
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
                                                        disabled: isDetail,
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
                                                    span: fourItemSpan,
                                                    disabled: true,
                                                    formItemLayout: fourItemLayout,
                                                },
                                                {
                                                    type: 'date',
                                                    label: '签订时间',
                                                    field: 'signingDate',
                                                    required: false,
                                                    span: fourItemSpan,
                                                    disabled: true,
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
                                                            itemId: 'heTongLeiXing'
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
                            <TabPane tab="学历情况" key="1" disabled={tabShow}>
                                <QnnTable
                                    fetch={propsData.myFetch}
                                    myFetch={propsData.myFetch}
                                    upload={propsData.myUpload}
                                    headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => { refMap.tabTwo = me }}
                                    pagination={false}
                                    fetchConfig={
                                        modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                            apiName: 'getZjXmSalaryEducationBackgroundHistoryList',//可为function 返回必须为string
                                            otherParams: async () => {
                                                return {
                                                    extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                }
                                            },
                                        } : {}}
                                    actionBtns={!isDetail ? [
                                        {
                                            name: "addRow", //内置add del
                                            icon: "plus", //icon
                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                            label: "新增",
                                            addRowDefaultData: {
                                                isFirstEducation: '0',
                                                isHighestEducation: '0'
                                            }
                                            // addRowFetchConfig: {
                                            //     apiName: "addZjXmSalaryEducationBackground",
                                            //     params: {
                                            //         extensionHistoryId: "extensionHistoryId"
                                            //     },
                                            // },
                                        },
                                        {
                                            field: 'delBtn',
                                            name: 'delDiy',
                                            icon: 'delete',
                                            disabled: 'bind:_actionBtnNoSelected',
                                            onClick: async (obj) => {
                                                listDelFunc(obj)
                                            },
                                            type: 'danger',
                                            label: '删除',
                                        },
                                    ] : []}
                                    antd={{
                                        rowKey: "educationHistoryId",
                                        size: "small"
                                    }}
                                    method={{}}
                                    componentsKey={{}}
                                    dataFormat={
                                        (tableData) => {
                                            let newTableData = tableData.map(item => {
                                                return {
                                                    ...item,
                                                    enrollmentDateGraduateDate: [item.enrollmentDate, item.graduateDate]
                                                }
                                            })
                                            return newTableData
                                        }
                                    }
                                    formConfig={[
                                        {
                                            isInForm: false,
                                            table: {
                                                title: '入学时间',
                                                dataIndex: 'enrollmentDateGraduateDate',
                                                format: 'YYYY-MM-DD',
                                                tdEdit: !isDetail,
                                                width: '250',
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
                                                fieldConfig: {
                                                    type: "rangeDate",
                                                    onChange: (val, obj) => {
                                                        obj.setEditedRowData({
                                                            enrollmentDate: val[0],
                                                            graduateDate: val[1]
                                                        })
                                                    }
                                                }
                                            }
                                        },
                                        // {
                                        //     isInForm: false,
                                        //     table: {
                                        //         title: '毕/肆业时间',
                                        //         dataIndex: 'graduateDate',
                                        //         format: 'YYYY-MM-DD',
                                        //         tdEdit: !isDetail,
                                        //         // tdEditFetchConfig: {
                                        //         //     apiName: "updateZjXmSalaryEducationBackground"
                                        //         // },
                                        //         fieldConfig: {
                                        //             type: "date"
                                        //         }
                                        //     }
                                        // },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'date',
                                                field: 'enrollmentDate',
                                                placeholder: '请选择',
                                                required: false,
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'date',
                                                field: 'graduateDate',
                                                placeholder: '请选择',
                                                required: false,
                                                hide: true
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                title: '毕/肆业院校',
                                                dataIndex: 'graduateSchool',
                                                tdEdit: !isDetail,
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
                                                fieldConfig: {
                                                    type: "string"
                                                }
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                title: '学历',
                                                dataIndex: 'education',
                                                tdEdit: !isDetail,
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
                                                type: "select",
                                                fieldConfig: {
                                                    type: "select",
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
                                                    onChange: (val, obj) => {
                                                        setFieldlabelFunc(obj, 'educationName')
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'educationName',
                                                type: 'string',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                title: '学位',
                                                dataIndex: 'degree',
                                                tdEdit: !isDetail,
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
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
                                                tdEdit: !isDetail,
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
                                                type: "select",
                                                fieldConfig: {
                                                    type: "select",
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
                                                    onChange: (val, obj) => {
                                                        setFieldlabelFunc(obj, 'majorName')
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'majorName',
                                                type: 'string',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                title: '学位授予时间',
                                                dataIndex: 'degreeAwardDate',
                                                format: 'YYYY-MM-DD',
                                                tdEdit: !isDetail,
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
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
                                                tdEdit: !isDetail,
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
                                                type: "radio",
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
                                                    ],
                                                    initialValue: '0',
                                                    onChange: (val, obj, row) => {
                                                        highestDegreeOnly(obj, row, 'isFirstEducation')
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                title: '是否最高学历',
                                                dataIndex: 'isHighestEducation',
                                                tdEdit: !isDetail,
                                                // tdEditFetchConfig: {
                                                //     apiName: "updateZjXmSalaryEducationBackground"
                                                // },
                                                type: "radio",
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
                                                    ],
                                                    initialValue: '0',
                                                    onChange: (val, obj, row) => {
                                                        highestDegreeOnly(obj, row, 'isHighestEducation')
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInForm: false,
                                            table: {
                                                title: '附件',
                                                dataIndex: 'fileEducationList',
                                                tdEdit: !isDetail,
                                                fieldConfig: {
                                                    type: "files",
                                                    max: 1,
                                                    fetchConfig: {
                                                        //配置后将会去请求下拉选项数据
                                                        apiName: "upload"
                                                    },
                                                },
                                                render: (val) => {
                                                    return val ? <TableFileDownLoad info={val[0]} {...props} /> : <div></div>
                                                }
                                            }
                                        },
                                    ]}
                                />
                            </TabPane>
                            <TabPane tab="工作履历" key="2" disabled={tabShow}>
                                {
                                    currentTabIndex === '2' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabThree = me }}
                                            pagination={false}
                                            method={{
                                                haveSelectedRows: (obj) => {
                                                    return !(obj.qnnTableInstance.getSelectedRows().length > 0)
                                                }
                                            }}
                                            antd={{
                                                rowKey: "experienceHistoryId",
                                                size: "small"
                                            }}
                                            fetchConfig={
                                                modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                                    apiName: 'getZjXmSalaryWorkExperienceHistoryList',//可为function 返回必须为string
                                                    otherParams: async () => {
                                                        return {
                                                            extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                        }
                                                    },
                                                } : {}
                                            }
                                            actionBtns={!isDetail ? [
                                                {
                                                    name: "addRow", //内置add del
                                                    icon: "plus", //icon
                                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                                    label: "新增",
                                                    // addRowFetchConfig: {
                                                    //     apiName: "addZjXmSalaryWorkExperience",
                                                    //     params: {
                                                    //         extensionHistoryId: "extensionHistoryId"
                                                    //     },
                                                    // },
                                                },
                                                {
                                                    field: 'delBtn',
                                                    name: 'delDiy',
                                                    icon: 'delete',
                                                    disabled: 'bind:_actionBtnNoSelected',
                                                    onClick: async (obj) => {
                                                        listDelFunc(obj)
                                                    },
                                                    type: 'danger',
                                                    label: '删除',
                                                },
                                            ] : []}
                                            componentsKey={{}}
                                            dataFormat={
                                                (tableData) => {
                                                    let newTableData = tableData.map(item => {
                                                        return {
                                                            ...item,
                                                            startDateEndDate: [item.startDate, item.endDate]
                                                        }
                                                    })
                                                    return newTableData
                                                }
                                            }
                                            formConfig={[

                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '起始日期 - 截止日期',
                                                        dataIndex: 'startDateEndDate',
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
                                                        required: true,
                                                        format: 'YYYY-MM-DD',
                                                        width: '250',
                                                        fieldConfig: {
                                                            type: 'rangeDate',
                                                            required: true,
                                                            onChange: (val, obj) => {
                                                                obj.setEditedRowData({
                                                                    startDate: val[0],
                                                                    endDate: val[1]
                                                                })
                                                            }
                                                        }
                                                    }
                                                },
                                                // {
                                                //     isInForm: false,
                                                //     table: {
                                                //         title: '截止日期',
                                                //         dataIndex: 'endDate',
                                                //         tdEdit: !isDetail,
                                                //         // tdEditFetchConfig: {
                                                //         //     apiName: "updateZjXmSalaryWorkExperience"
                                                //         // },
                                                //         required: true,
                                                //         format: 'YYYY-MM-DD',
                                                //         fieldConfig: {
                                                //             type: 'date',
                                                //             required: true,
                                                //         }
                                                //     }
                                                // },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'startDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'endDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '工作单位',
                                                        dataIndex: 'workUnit',
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
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
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
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
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
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
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
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
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
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
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
                                                        fieldConfig: {
                                                            type: "number"
                                                        }
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '主、兼职',
                                                        dataIndex: 'positionType',
                                                        tdEdit: !isDetail,
                                                        // tdEditFetchConfig: {
                                                        //     apiName: "updateZjXmSalaryWorkExperience"
                                                        // },
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'zhuJianZhi'
                                                                }
                                                            },
                                                            optionConfig: {//下拉选项配置
                                                                label: 'itemName', //默认 label
                                                                value: 'itemId',// 
                                                            },
                                                            onChange: (val, obj) => {
                                                                setFieldlabelFunc(obj, 'positionTypeName')
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'positionTypeName',
                                                        type: 'string',
                                                        hide: true
                                                    }
                                                }
                                            ]}
                                        /> : null
                                }
                            </TabPane>
                            <TabPane tab="专业技术" key="3" disabled={tabShow}>
                                {
                                    currentTabIndex === '3' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabFour = me }}
                                            pagination={false}
                                            method={{}}
                                            antd={
                                                {
                                                    rowKey: "technologyHistoryId",
                                                    size: "small"
                                                }
                                            }
                                            fetchConfig={
                                                modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                                    apiName: 'getZjXmSalaryProfessionalTechnologyHistoryList',//可为function 返回必须为string
                                                    otherParams: async () => {
                                                        return {
                                                            extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                        }
                                                    },
                                                } : {}
                                            }
                                            actionBtns={
                                                !isDetail ?
                                                    [
                                                        {
                                                            name: "addRow", //内置add del
                                                            icon: "plus", //icon
                                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                                            label: "新增",
                                                            // addRowFetchConfig: {
                                                            //     apiName: "addZjXmSalaryProfessionalTechnology",
                                                            //     params: {
                                                            //         extensionHistoryId: "extensionHistoryId"
                                                            //     },
                                                            // },
                                                        },
                                                        {
                                                            field: 'delBtn',
                                                            name: 'del',
                                                            icon: 'delete',
                                                            fetchConfig: {//ajax配置
                                                                apiName: 'batchDeleteZjXmSalaryProfessionalTechnologyHistory',
                                                            },
                                                            type: 'danger',
                                                            label: '删除',
                                                        },
                                                    ] : []
                                            }
                                            componentsKey={{}}
                                            formConfig={[
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '职称名称',
                                                        dataIndex: 'title',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "select",
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
                                                            onChange: (val, obj) => {
                                                                setFieldlabelFunc(obj, 'titleName')
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        field: 'titleName',
                                                        type: 'string',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '职称专业',
                                                        dataIndex: 'specialty',
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'zhiChengJiBie'
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'zhiChengHuoQuTuJing'
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        format: 'YYYY-MM-DD',
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "string"
                                                        }
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '附件',
                                                        dataIndex: 'fileList',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "files",
                                                            max: 1,
                                                            fetchConfig: {
                                                                //配置后将会去请求下拉选项数据
                                                                apiName: "upload"
                                                            },
                                                        },
                                                        render: (val) => {
                                                            return val ? <TableFileDownLoad info={val[0]} {...props} /> : <div></div>
                                                        }
                                                    }
                                                },
                                            ]}
                                        /> : null
                                }
                            </TabPane>
                            <TabPane tab="合同管理" key="4" disabled={tabShow}>
                                {
                                    currentTabIndex === '4' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabFive = me }}
                                            pagination={false}
                                            method={{}}
                                            antd={
                                                {
                                                    rowKey: "contractHistoryId",
                                                    size: "small"
                                                }
                                            }
                                            fetchConfig={
                                                modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                                    apiName: 'getZjXmSalaryContractManagementHistoryList',//可为function 返回必须为string
                                                    otherParams: async () => {
                                                        return {
                                                            extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                        }
                                                    },
                                                } : {}
                                            }
                                            actionBtns={
                                                !isDetail ?
                                                    [
                                                        {
                                                            name: "addRow", //内置add del
                                                            icon: "plus", //icon
                                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                                            label: "新增",
                                                        },
                                                        {
                                                            field: 'delBtn',
                                                            name: 'delDiy',
                                                            icon: 'delete',
                                                            disabled: 'bind:_actionBtnNoSelected',
                                                            onClick: async (obj) => {
                                                                listDelFunc(obj)
                                                            },
                                                            type: 'danger',
                                                            label: '删除',
                                                        },
                                                    ] : []
                                            }
                                            componentsKey={{}}
                                            dataFormat={
                                                (tableData) => {
                                                    let newTableData = tableData.map(item => {
                                                        return {
                                                            ...item,
                                                            startDateEndDate: [item.startDate, item.endDate]
                                                        }
                                                    })
                                                    return newTableData
                                                }
                                            }
                                            formConfig={[
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '合同编号',
                                                        dataIndex: 'contractNo',
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        format: 'YYYY-MM-DD',
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'heTongLeiXing'
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
                                                        title: '劳动合同期限·始 - 劳动合同期限·止',
                                                        dataIndex: 'startDateEndDate',
                                                        tdEdit: !isDetail,
                                                        format: 'YYYY-MM-DD',
                                                        width: '250',
                                                        fieldConfig: {
                                                            type: 'rangeDate',
                                                            onChange: (val, obj) => {
                                                                obj.setEditedRowData({
                                                                    startDate: val[0],
                                                                    endDate: val[1]
                                                                })
                                                            }
                                                        },
                                                    }
                                                },
                                                // {
                                                //     isInForm: false,
                                                //     table: {
                                                //         width: 150,
                                                //         title: '劳动合同期限·止',
                                                //         dataIndex: 'endDate',
                                                //         tdEdit: !isDetail,
                                                //         format: 'YYYY-MM-DD',
                                                //         fieldConfig: {
                                                //             type: 'date',
                                                //         },
                                                //     }
                                                // },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'startDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'endDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 150,
                                                        title: '合同期限（月）',
                                                        dataIndex: 'contractPeriod',
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'shiYongQi'
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'qianDingLeiXing'
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
                                                        dataIndex: 'fileQuitList',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "files",
                                                            max: 1,
                                                            fetchConfig: {
                                                                //配置后将会去请求下拉选项数据
                                                                apiName: "upload"
                                                            },
                                                        },
                                                        render: (val) => {
                                                            return val ? <TableFileDownLoad info={val[0]} {...props} /> : <div></div>
                                                        }
                                                    }
                                                },

                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '合同附件',
                                                        dataIndex: 'fileContractList',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "files",
                                                            max: 1,
                                                            fetchConfig: {
                                                                //配置后将会去请求下拉选项数据
                                                                apiName: "upload"
                                                            },
                                                        },
                                                        render: (val) => {
                                                            return val ? <TableFileDownLoad info={val[0]} {...props} /> : <div></div>
                                                        }
                                                    }
                                                },

                                            ]}
                                        /> : null
                                }
                            </TabPane>
                            <TabPane tab="培训情况" key="5" disabled={tabShow}>
                                {
                                    currentTabIndex === '5' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabSix = me }}
                                            pagination={false}
                                            method={{}}
                                            antd={
                                                {
                                                    rowKey: "trainingHistoryId",
                                                    size: "small"
                                                }
                                            }
                                            fetchConfig={
                                                modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                                    apiName: 'getZjXmSalaryTrainingSituationHistoryList',//可为function 返回必须为string
                                                    otherParams: async () => {
                                                        return {
                                                            extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                        }
                                                    },
                                                } : {}
                                            }
                                            actionBtns={
                                                !isDetail ?
                                                    [
                                                        {
                                                            name: "addRow", //内置add del
                                                            icon: "plus", //icon
                                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                                            label: "新增",
                                                        },
                                                        {
                                                            field: 'delBtn',
                                                            name: 'delDiy',
                                                            icon: 'delete',
                                                            disabled: 'bind:_actionBtnNoSelected',
                                                            onClick: async (obj) => {
                                                                listDelFunc(obj)
                                                            },
                                                            type: 'danger',
                                                            label: '删除',
                                                        },
                                                    ] : []
                                            }
                                            componentsKey={{}}
                                            dataFormat={
                                                (tableData) => {
                                                    let newTableData = tableData.map(item => {
                                                        return {
                                                            ...item,
                                                            startDateEndDate: [item.startDate, item.endDate]
                                                        }
                                                    })
                                                    return newTableData
                                                }
                                            }
                                            formConfig={[
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '起始日期 - 截止日期',
                                                        dataIndex: 'startDateEndDate',
                                                        tdEdit: !isDetail,
                                                        format: 'YYYY-MM-DD',
                                                        width: '250',
                                                        fieldConfig: {
                                                            type: 'rangeDate',
                                                            onChange: (val, obj) => {
                                                                obj.setEditedRowData({
                                                                    startDate: val[0],
                                                                    endDate: val[1]
                                                                })
                                                            }
                                                        },
                                                    }
                                                },
                                                // {
                                                //     isInForm: false,
                                                //     table: {
                                                //         title: '截止日期',
                                                //         dataIndex: 'endDate',
                                                //         tdEdit: !isDetail,
                                                //         format: 'YYYY-MM-DD',
                                                //         fieldConfig: {
                                                //             type: 'date',
                                                //         },
                                                //     }
                                                // },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'startDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'endDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '培训名称',
                                                        dataIndex: 'trainingName',
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'peiXunLeiXing'
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'peiXunXueXiFangShi'
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
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "string"
                                                        },
                                                    }
                                                },

                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '培训附件',
                                                        dataIndex: 'fileList',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "files",
                                                            max: 1,
                                                            fetchConfig: {
                                                                //配置后将会去请求下拉选项数据
                                                                apiName: "upload"
                                                            },
                                                        },
                                                        render: (val) => {
                                                            return val ? <TableFileDownLoad info={val[0]} {...props} /> : <div></div>
                                                        }
                                                    }
                                                },

                                            ]}
                                        /> : null
                                }
                            </TabPane>
                            <TabPane tab="家庭状况" key="6" disabled={tabShow}>
                                {
                                    currentTabIndex === '6' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabSeven = me }}
                                            pagination={false}
                                            method={{}}
                                            antd={
                                                {
                                                    rowKey: "familyHistoryId",
                                                    size: "small"
                                                }
                                            }
                                            fetchConfig={
                                                modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                                    apiName: 'getZjXmSalaryFamilyBackgroundHistoryList',//可为function 返回必须为string
                                                    otherParams: async () => {
                                                        return {
                                                            extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                        }
                                                    },
                                                } : {}
                                            }
                                            actionBtns={
                                                !isDetail ?
                                                    [
                                                        {
                                                            name: "addRow", //内置add del
                                                            icon: "plus", //icon
                                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                                            label: "新增",
                                                            addRowDefaultData: {
                                                                isUrgentLinkMan: '0'
                                                            }
                                                        },
                                                        {
                                                            field: 'delBtn',
                                                            name: 'delDiy',
                                                            icon: 'delete',
                                                            disabled: 'bind:_actionBtnNoSelected',
                                                            onClick: async (obj) => {
                                                                listDelFunc(obj)
                                                            },
                                                            type: 'danger',
                                                            label: '删除',
                                                        },
                                                    ] : []
                                            }
                                            componentsKey={{}}
                                            formConfig={[
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '与本人关系',
                                                        dataIndex: 'relationship',
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'yuBenRenGuanXi'
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "number"
                                                        },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '是否紧急联系人',
                                                        dataIndex: 'isUrgentLinkMan',
                                                        tdEdit: !isDetail,
                                                        type: "radio",
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
                                        /> : null
                                }
                            </TabPane>
                            <TabPane tab="健康情况" key="7" disabled={tabShow}>
                                {
                                    currentTabIndex === '7' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabEight = me }}
                                            pagination={false}
                                            method={{}}
                                            antd={
                                                {
                                                    rowKey: "healthId",
                                                    size: "small"
                                                }
                                            }
                                            fetchConfig={
                                                modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                                    apiName: 'getZjXmSalaryHealthConditionHistoryList',//可为function 返回必须为string
                                                    otherParams: async () => {
                                                        return {
                                                            extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                        }
                                                    },
                                                } : {}
                                            }
                                            actionBtns={
                                                !isDetail ?
                                                    [
                                                        {
                                                            name: "addRow", //内置add del
                                                            icon: "plus", //icon
                                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                                            label: "新增",
                                                        },
                                                        {
                                                            field: 'delBtn',
                                                            name: 'delDiy',
                                                            icon: 'delete',
                                                            disabled: 'bind:_actionBtnNoSelected',
                                                            onClick: async (obj) => {
                                                                listDelFunc(obj)
                                                            },
                                                            type: 'danger',
                                                            label: '删除',
                                                        },
                                                    ] : []
                                            }
                                            componentsKey={{}}
                                            formConfig={[
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '体检类型',
                                                        dataIndex: 'physicalType',
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'tiJianLeiXing'
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'jianKangQingKuang'
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'zhiYeBingQingKuang'
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
                                                        dataIndex: 'fileList',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "files",
                                                            max: 1,
                                                            fetchConfig: {
                                                                //配置后将会去请求下拉选项数据
                                                                apiName: "upload"
                                                            },
                                                        },
                                                        render: (val) => {
                                                            return val ? <TableFileDownLoad info={val[0]} {...props} /> : <div></div>
                                                        }
                                                    }
                                                },

                                            ]}
                                        /> : null
                                }
                            </TabPane>
                            <TabPane tab="证书管理" key="8" disabled={tabShow}>
                                {
                                    currentTabIndex === '8' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabNine = me }}
                                            pagination={false}
                                            method={{}}
                                            antd={
                                                {
                                                    rowKey: "certificateHistoryId",
                                                    size: "small"
                                                }
                                            }
                                            fetchConfig={
                                                modalShowStatus === 'edit' || modalShowStatus === 'detail' ? {
                                                    apiName: 'getZjXmSalaryCertificateManagementHistoryList',//可为function 返回必须为string
                                                    otherParams: async () => {
                                                        return {
                                                            extensionHistoryId: (await refMap.tabOne.getValues()).extensionHistoryId
                                                        }
                                                    },
                                                } : {}
                                            }
                                            actionBtns={
                                                !isDetail ?
                                                    [
                                                        {
                                                            name: "addRow", //内置add del
                                                            icon: "plus", //icon
                                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                                            label: "新增",
                                                        },
                                                        {
                                                            field: 'delBtn',
                                                            name: 'delDiy',
                                                            icon: 'delete',
                                                            disabled: 'bind:_actionBtnNoSelected',
                                                            onClick: async (obj) => {
                                                                listDelFunc(obj)
                                                            },
                                                            type: 'danger',
                                                            label: '删除',
                                                        },
                                                    ] : []
                                            }
                                            componentsKey={{}}
                                            dataFormat={
                                                (tableData) => {
                                                    let newTableData = tableData.map(item => {
                                                        return {
                                                            ...item,
                                                            startDateEndDate: [item.startDate, item.endDate]
                                                        }
                                                    })
                                                    return newTableData
                                                }
                                            }
                                            formConfig={[
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 140,
                                                        fixed: "left",
                                                        title: '证书类别',
                                                        dataIndex: 'certificateType',
                                                        tdEdit: !isDetail,
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'zhengShuLeiXing'
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
                                                        tdEdit: !isDetail,
                                                        type: "select",
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
                                                        tdEdit: !isDetail,
                                                        // fieldConfig: {
                                                        //     type: "select",
                                                        //     fetchConfig: {
                                                        //         apiName: "getBaseCodeSelect",
                                                        //         otherParams: {
                                                        //             itemId: 'zhengShuZhuanYe'
                                                        //         }
                                                        //     },
                                                        //     optionConfig: {//下拉选项配置
                                                        //         label: 'itemName', //默认 label
                                                        //         value: 'itemId',// 
                                                        //     },
                                                        // },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 120,
                                                        title: '证书编号',
                                                        dataIndex: 'certificateNo',
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        format: 'YYYY-MM-DD',
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
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
                                                        tdEdit: !isDetail,
                                                        width: 300,
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'fenTanNianDu'
                                                                }
                                                            },
                                                            optionConfig: {//下拉选项配置
                                                                label: 'itemName', //默认 label
                                                                value: 'itemId',// 
                                                            },
                                                            multiple: true,
                                                            pushJoin: true,
                                                        }
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '已发放年度',
                                                        dataIndex: 'paidYear',
                                                        tdEdit: !isDetail,
                                                        width: 300,
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'yiFaFangNianDu'
                                                                }
                                                            },
                                                            optionConfig: {//下拉选项配置
                                                                label: 'itemName', //默认 label
                                                                value: 'itemId',// 
                                                            },
                                                            multiple: true,
                                                            pushJoin: true,
                                                        }
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 120,
                                                        title: '月度补贴标准',
                                                        dataIndex: 'subsidyStandard',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "money"
                                                        },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '发放开始时间 - 发放截止时间',
                                                        dataIndex: 'startDateEndDate',
                                                        tdEdit: !isDetail,
                                                        format: 'YYYY-MM-DD',
                                                        width: '250',
                                                        fieldConfig: {
                                                            type: 'rangeDate',
                                                            required: false,
                                                            onChange: (val, obj) => {
                                                                obj.setEditedRowData({
                                                                    startDate: val[0],
                                                                    endDate: val[1]
                                                                })
                                                            }
                                                        },
                                                    }
                                                },
                                                // {
                                                //     isInForm: false,
                                                //     table: {
                                                //         width: 140,
                                                //         title: '发放截止时间',
                                                //         dataIndex: 'endDate',
                                                //         tdEdit: !isDetail,
                                                //         format: 'YYYY-MM-DD',
                                                //         fieldConfig: {
                                                //             type: 'date',
                                                //         },
                                                //     }
                                                // },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'startDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        type: 'date',
                                                        field: 'endDate',
                                                        placeholder: '请选择',
                                                        required: false,
                                                        hide: true
                                                    }
                                                },

                                                {
                                                    isInForm: false,
                                                    table: {
                                                        width: 130,
                                                        title: '附件',
                                                        dataIndex: 'fileList',
                                                        tdEdit: !isDetail,
                                                        fieldConfig: {
                                                            type: "files",
                                                            max: 1,
                                                            fetchConfig: {
                                                                //配置后将会去请求下拉选项数据
                                                                apiName: "upload"
                                                            },
                                                        },
                                                        render: (val) => {
                                                            return val ? <TableFileDownLoad info={val[0]} {...props} /> : <div></div>
                                                        }
                                                    }
                                                },

                                            ]}
                                        /> : null
                                }
                            </TabPane>
                            <TabPane tab="政治面貌" key="9" disabled={tabShow}>
                                {
                                    currentTabIndex === '9' ?
                                        <QnnTable
                                            fetch={propsData.myFetch}
                                            myFetch={propsData.myFetch}
                                            upload={propsData.myUpload}
                                            headers={{ token: propsData.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => { refMap.tabTen = me }}
                                            pagination={false}
                                            method={{}}
                                            antd={
                                                {
                                                    rowKey: "zjXmSalaryPoliticalHistoryId",
                                                    size: "small"
                                                }
                                            }
                                            fetchConfig={
                                                () => ({
                                                    apiName: 'getZjXmSalaryPoliticalHistoryList',
                                                    otherParams: {
                                                        extensionHistoryId
                                                    }
                                                })
                                            }
                                            actionBtns={
                                                !isDetail ?
                                                    [
                                                        {
                                                            name: "addDiy", //内置add del
                                                            icon: "plus", //icon
                                                            type: "primary", //类型  默认 primary  [primary dashed danger]
                                                            label: "新增",
                                                            onClick: (obj) => {
                                                                setpoliticalOutlookVisible(true)
                                                                setzzmmAddOrUpdate('add')
                                                                setextensionId(extensionHistoryId)
                                                            },
                                                        },
                                                        {
                                                            field: 'editDiy',
                                                            label: '编辑',
                                                            isValidate: true,//点击后是否验证表单 默认true
                                                            type: 'primary', //primary dashed danger
                                                            disabled: (obj) => {
                                                                if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            onClick: (obj) => {
                                                                setpoliticalOutlookVisible(true)
                                                                setzzmmAddOrUpdate('update')
                                                                setextensionId(extensionHistoryId)
                                                                setpoliticalPK(obj.selectedRows[0]['zjXmSalaryPoliticalHistoryId'])
                                                            }
                                                        },
                                                        {
                                                            field: 'delBtn',
                                                            name: 'del',
                                                            icon: 'delete',
                                                            fetchConfig: {//ajax配置
                                                                apiName: 'batchDeleteUpdateZjXmSalaryPolitical',
                                                            },
                                                            disabled: (obj) => {
                                                                if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            type: 'danger',
                                                            label: '删除',
                                                        },
                                                    ] : []
                                            }
                                            componentsKey={{}}
                                            formConfig={[
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        fixed: "left",
                                                        title: '政治面貌',
                                                        dataIndex: 'policitalStatus',
                                                        field: 'policitalStatus',
                                                        type: "select",
                                                        fieldConfig: {
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'zhengzhimianmao'
                                                                }
                                                            },
                                                            optionConfig: {//下拉选项配置
                                                                label: 'itemName', //默认 label
                                                                value: 'itemId',//
                                                                children: 'children',
                                                                linkageFields: {
                                                                    "politicCountenanceName": "itemName"
                                                                }
                                                            },
                                                        },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '参加时间',
                                                        field: 'joinTime',
                                                        dataIndex: 'joinTime',
                                                        format: 'YYYY-MM-DD',
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
                                                        dataIndex: 'branchName',
                                                        field: 'branchName',
                                                        fieldConfig: {
                                                            type: 'string',
                                                        },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '党组织关系所在地',
                                                        dataIndex: 'relationshipLoc',
                                                        field: 'relationshipLoc',
                                                        type: 'select',
                                                        fieldConfig: {
                                                            type: 'select',
                                                            field: 'relationshipLoc',
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
                                                        },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '介绍人',
                                                        dataIndex: 'introducePerson',
                                                        field: 'introducePerson',
                                                        fieldConfig: {
                                                            type: 'string',
                                                        },
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    table: {
                                                        title: '转正时间',
                                                        dataIndex: 'formalTime',
                                                        field: 'formalTime',
                                                        format: 'YYYY-MM-DD',
                                                        fieldConfig: {
                                                            type: 'date',
                                                        },
                                                    }
                                                },
                                            ]}
                                        /> : null
                                }
                            </TabPane>
                        </Tabs>
                    </Modal> : null
            }
            <Modal
                title="离职人员信息"
                visible={isModalVisibleToLz}
                width={'60%'}
                onOk={async () => {
                    const res = await propsData.myFetch('checkQuitReRecruit', { extensionId: selectedKeysForModal, orgId })
                    if (res.success) {
                        // 选中的数据, 把这个数据set到 modalTableRefs 的表格
                        // this.selectedDataForModal 
                        const { data, success, message } = await propsData.myFetch('pcGetZjXmSalaryUserExtensionDetails', {
                            extensionId: selectedKeysForModal,
                            // approvalFlag: 'employ'
                        })
                        if (success) {
                            refMap.tabOne.setValues({ ...data, reRecruit: 2, extensionId: selectedKeysForModal, approvalFlag: 'employ' })
                            setIsModalVisibleToLz(false)
                        } else {
                            Msg.error(message)
                        }
                    } else {
                        Msg.error(res.message)
                    }
                }}
                onCancel={() => {
                    confirm({
                        title: '温馨提示',
                        content: '确定要离开弹窗?',
                        onOk() {

                        },
                        onCancel() {
                            setIsModalVisibleToLz(false)
                        },
                        cancelText: '是',
                        okText: '否'
                    })
                }}
                {
                ...isDetail ? { footer: null } : null
                }
                destroyOnClose={true}
                okText={'保存'}
                confirmLoading={false}
            >
                <QnnTable
                    fetch={propsData.myFetch}
                    upload={propsData.myUpload}
                    // wrappedComponentRef={(me) => { modalTableRefs = me }}
                    method={{}}
                    componentsKey={{}}
                    antd={{
                        rowKey: "extensionId",
                        size: 'small'
                    }}
                    fetchConfig={{
                        apiName: 'getQuitUserExtensionList',
                        otherParams: () => {
                            return {
                                orgId: orgId,
                            }
                        }
                    }}
                    rowSelection={{
                        hideSelectAll: true,
                        type: 'radio',
                        onChange: async (selectedRowKey, selectedData, delKey) => {
                            selectedKeysForModal = selectedRowKey[0]
                        }
                    }}
                    formConfig={[
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'realName',
                                filter: true,
                            }
                            , form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: false,
                            }
                        },
                        {

                            table: {
                                title: '性别',
                                dataIndex: 'gender',
                                type: "radio",
                            }
                            , form: {
                                field: "gender", //表格里面的字段 
                                label: "性别",
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
                            },
                        },
                        {
                            table: {
                                title: '出生年月',
                                dataIndex: 'birthday',
                                type: 'date',
                            },
                            form: {
                                type: 'date',
                                label: '出生年月',
                                field: 'birthday',
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
                            },
                        },
                        {
                            table: {
                                title: '证件号',
                                dataIndex: 'idNumber',
                                filter: true,
                            }
                            , form: {
                                field: "idNumber", //表格里面的字段 
                                label: "证件号",
                                type: "string",
                            },
                        },

                    ]}
                />
            </Modal>

            <PoliticalOutlook {...props} {...propsData}
                visible={politicalOutlookVisible}
                status={zzmmAddOrUpdate}
                reSetVisible={(isRefresh) => {
                    setpoliticalOutlookVisible(false)
                    if (isRefresh) {
                        refMap.tabTen.refresh()
                    }
                    refMap.tabTen.clearSelectedRows()
                }}
                basicInfo={refMap.tabOne}
                id={extensionId}
                isPerson={false}
                pk={politicalPK}
            />
        </div>
    )
}
export default PersonInfo