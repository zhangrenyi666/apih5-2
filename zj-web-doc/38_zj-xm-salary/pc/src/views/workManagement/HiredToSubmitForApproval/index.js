import React from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Button, Divider, Row, Col } from "antd";
import Apih5 from "qnn-apih5"
import WillExecute from './willExecute'
import PoliticalOutlook from '../../Person/PoliticalOutlook'
import TableFileDownLoad from '../../common/tableFileDownLoad';
import moment from "moment";
const { confirm } = Modal;
const config = {
    antd: {
        rowKey: 'extensionHistoryId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
};
const oneFormItemWrapperStyle = -5
const fourItemSpan = 6
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
class index extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {
            // modalShowStatus: false,
            extensionHistoryId: '',
            companyAnddepartment: null,
            isModalVisibleToLz: false
        }
        const { projectId } = this.apih5.getUserInfo('curCompany')
        this.props.myFetch('getSysComDeptProById', { departmentId: projectId }).then(({ data }) => {
            this.setState({
                companyAnddepartment: data
            })
        })
    }
    selectedKeysForModal = ''
    setFieldlabelFunc = async (obj, name) => {
        const rowData = await obj.qnnTableInstance.getEditedRowData()
        await obj.qnnTableInstance.setEditedRowData({ ...rowData, [name]: obj.itemData.itemName })
    }
    batchSaveFunc = async (index) => {
        const fetchMap = {
            jcxx: { // ????????????
                apiName: '',
                paramsTiele: ''
            },
            xlqk: { // ????????????
                apiName: 'batchEducationHistory',
                paramsTiele: 'educationHistoryList'
            },
            gzll: { // ????????????
                apiName: 'batchWorkExperienceHistory',
                paramsTiele: 'workExperienceHistoryList'
            },
            zyjs: { // ????????????
                apiName: 'batchAddUpdateZjXmSalaryProfessionalTechnologyHistory',
                paramsTiele: 'professionalTechnologyHistoryList'
            },
            htgl: { // ????????????
                apiName: 'batchAddUpdateZjXmSalaryContractManagementHistory',
                paramsTiele: 'contractManagementHistoryList'
            },
            pxqk: { // ????????????
                apiName: 'batchAddUpdateZjXmSalaryTrainingSituationHistory',
                paramsTiele: 'trainingSituationHistoryList'
            },
            jtzk: { // ????????????
                apiName: 'batchAddUpdateZjXmSalaryFamilyBackgroundHistory',
                paramsTiele: 'familyBackgroundHistoryList'
            },
            jkqk: { // ????????????
                apiName: 'batchAddUpdateZjXmSalaryHealthConditionHistory',
                paramsTiele: 'healthConditionHistoryList'
            },
            zsgl: { // ????????????
                apiName: 'batchAddUpdateZjXmSalaryCertificateManagementHistory',
                paramsTiele: 'certificateManagementHistoryList'
            },
            zzmm: { //????????????
                apiName: 'zzmm',
                paramsTiele: ''
            }
        }

        let key = ''
        Object.keys(fetchMap).map((item, objIndex) => {
            if (objIndex === index) {
                key = item
            }
            return true
        })

        /*
            ?????????       enrollmentDateGraduateDate           startDateEndDate     startDateEndDate      startDateEndDate     startDateEndDate
            ?????????               ????????????                          ????????????             ????????????              ????????????              ????????????  
            key                   xlqk                              gzll                htgl                  pxqk                 zsgl
            ??????               educationId                      experienceId         contractId            trainingId           certificateId
        */

        if (key === 'xlqk' || key === 'gzll' || key === 'htgl' || key === 'jtzk' || key === 'pxqk' || key === 'zsgl') {
            let tdRefsMap = {
                startDateEndDate: [],
                enrollmentDateGraduateDate: [],
                phoneNumber: []
            }
            const tableData = await this[key].getTableData()
            tableData.map(async (item, i) => {
                let pKey = ''
                switch (key) {
                    case 'xlqk':
                        pKey = 'educationHistoryId'
                        break
                    case 'gzll':
                        pKey = 'experienceHistoryId'
                        break
                    case 'htgl':
                        pKey = 'contractHistoryId'
                        break
                    case 'pxqk':
                        pKey = 'trainingHistoryId'
                        break
                    case 'jtzk':
                        pKey = 'familyHistoryId'
                        break
                    case 'zsgl':
                        pKey = 'certificateHistoryId'
                        break
                    default:
                        break
                }
                if (key === 'jtzk') {
                    tdRefsMap['phoneNumber'][i] = this[key].getTdRef({
                        rowId: item[pKey],
                        field: 'phoneNumber'
                    })
                } else {
                    tdRefsMap[key === 'xlqk' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'][i] = this[key].getTdRef({
                        rowId: item[pKey],
                        field: key === 'xlqk' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'
                    })
                }
            })

            let IsVerificationSuccess = true


            if (key === 'jtzk') {
                tdRefsMap['phoneNumber'].map(item => {
                    const isPhoneNumber = /^1[3456789]\d{9}$/

                    if (!isPhoneNumber.test(item.getTdData())) {
                        IsVerificationSuccess = false
                        item.setErrorAlert('??????????????????????????????')
                    }
                    return true
                })
            } else {
                tdRefsMap[key === 'xlqk' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'].map(item => {
                    if (!item.getTdData()) {
                        IsVerificationSuccess = false
                        item.setErrorAlert('????????????')
                    }
                    return true
                })
            }

            if (!IsVerificationSuccess) {
                return false
            }

        }

        const { extensionHistoryId } = await this.table.getDeawerValues()

        if (this[key].getTableData().length) {
            const { message, success } = await this.props.myFetch(fetchMap[key]['apiName'],
                {
                    extensionHistoryId,
                    [fetchMap[key]['paramsTiele']]: this[key].getTableData().map(item => {
                        return {
                            ...item,
                            extensionHistoryId,
                            workPersonnelFlag: 1
                        }
                    })
                }
            )

            if (success) {
                this[key].refresh()
                Msg.success('????????????')
            } else {
                Msg.error(message)
            }
        } else {
            Msg.warning('??????????????????????????????!')
        }
    }

    filterDelDataFunc = async (selectData, refresh, clearSelectedRows, url) => {
        let params = []
        selectData.map(item => {
            if (item.extensionHistoryId) {
                params.push(item)
            }
            return true
        })

        if (params.length) {
            confirm({
                title: '????????????',
                content: '?????????????????????,???????????????????',
                onOk: async () => {
                    const { success, message } = await this.props.myFetch(url, params)
                    if (success) {
                        refresh()
                        clearSelectedRows()
                        Msg.success('???????????????')
                    } else {
                        Msg.error(message)
                    }
                }
            })
        } else {
            refresh()
            clearSelectedRows()
            Msg.success('???????????????')
        }
    }

    highestDegreeOnly = (obj, row, key) => {
        const tableData = obj.btnCallbackFn.getTableData()
        let newTableData = []

        // ??????table?????????
        tableData.map((item, index) => {
            if (item.educationId === row.rowData.educationId) {
                // ?????????
                newTableData[index] = { ...item }
            } else {
                // ??????????????????, ???????????????????????? ????????? ??? 
                // ????????? ??? ??????, ???????????????????????????

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

    dateToStringFunc = (array) => {
        return array ? ` ${moment(array[0]).format('YYYY-MM-DD')} - ${moment(array[1]).format('YYYY-MM-DD')}` : ''
    }

    render() {
        const { companyId, companyName, projectId, projectName, departmentName, departmentId } = this.apih5.getUserInfo('curCompany')
        const { realName } = this.apih5.getUserInfo()
        const orgId = this.apih5.getOrgId()
        return (
            <div>
                {
                    this.state.companyAnddepartment ?
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            fetchConfig={{
                                apiName: 'getZjXmSalaryUserExtensionHistoryList',
                                otherParams: {
                                    orgId,
                                    approvalFlag: 'employ'
                                }
                            }}
                            actionBtns={[
                                {
                                    name: 'addDiy',//??????add del
                                    icon: 'plus',//icon
                                    type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    onClick: (obj) => {
                                        if (projectId) {
                                            obj.qnnTableInstance.btnAction({
                                                btnConfig: {
                                                    name: "add",
                                                    drawerTitle: "",
                                                    formBtns: [
                                                        {
                                                            name: 'cancel', //??????????????????
                                                            type: 'dashed',//??????  ?????? primary
                                                            label: '??????',
                                                        },
                                                        {
                                                            name: 'diySubmit',//??????add del
                                                            type: 'primary',//??????  ?????? primary
                                                            label: '??????',//???????????????????????????????????? 
                                                            onClick: async (obj) => {
                                                                if (obj.qnnTableInstance.getTabsIndex() === '0') {
                                                                    const formData = await obj.qnnTableInstance.getDeawerValues()
                                                                    const resData = await this.props.myFetch('getBaseCodeTree', { itemId: 'gangWeiGuanLi' })
                                                                    let arrList = []
                                                                    let TemporaryData = resData.data
                                                                    const positionArr = formData.position.split(',')

                                                                    const outerLoopFunc = (aouterData, targetData, i) => {
                                                                        targetData.map(item => {
                                                                            if (aouterData === item.itemId) {
                                                                                arrList[i] = item.itemName
                                                                                TemporaryData = item.children
                                                                            }
                                                                            return true
                                                                        })
                                                                    }

                                                                    for (let i = 0; i < positionArr.length; i++) {
                                                                        outerLoopFunc(positionArr[i], TemporaryData, i)
                                                                    }

                                                                    const { data, success, message } = await this.props.myFetch(obj._formData.extensionHistoryId ? 'updateEmployUserExtensionHistory' : 'addEmployUserExtensionHistory', {
                                                                        ...obj._formData,
                                                                        nativePlace: obj._formData.nativePlace.toString(),
                                                                        legalAddress: obj._formData.legalAddress.toString(),
                                                                        position: obj._formData.position.toString(),
                                                                        positionName: arrList.join('/'),
                                                                        residenceAddress: obj._formData.residenceAddress.toString(),
                                                                        presentAddress: obj._formData.presentAddress.toString(),
                                                                        salaryInfo: {
                                                                            ...obj._formData.salaryInfo,
                                                                            levelSalaryId: obj._formData.salaryInfo.levelSalaryId.toString(),
                                                                        },
                                                                        companyId: companyId || null,
                                                                        companyName: companyName || null,
                                                                        projectId: projectId || null,
                                                                        projectName: projectName || null,
                                                                        departmentName: departmentName || null,
                                                                        departmentId: departmentId || null,
                                                                        orgId: orgId,
                                                                        approvalFlag: 'employ'
                                                                    })

                                                                    if (success) {
                                                                        Msg.success('????????????!')
                                                                        this.setState({
                                                                            primaryKey: data.zjXmSalaryEmployApprovalId
                                                                        }, () => {
                                                                            this.table.getQnnForm().setValues({ ...data })
                                                                            this.table.getQnnForm().tabsBarForceUpdate()
                                                                            this.table.getQnnForm().scrollYToStart()
                                                                        })
                                                                    } else {
                                                                        Msg.error(message)
                                                                    }
                                                                } else {
                                                                    this.batchSaveFunc(Number(obj.qnnTableInstance.getTabsIndex()))
                                                                }
                                                            },
                                                            hide: (obj) => {
                                                                let index = obj.btnCallbackFn.getActiveKey();
                                                                if (index === "10") {
                                                                    return true;
                                                                } else {
                                                                    return false;
                                                                }
                                                            },
                                                        }
                                                    ]
                                                },
                                                attrBindInfo: {
                                                    rowData: {
                                                        ...obj.rowData
                                                    }
                                                }
                                            })
                                        } else {
                                            Msg.warning('???????????????????????????????????????????????????!')
                                        }
                                    },

                                },
                                {
                                    name: 'Component',
                                    type: 'primary',
                                    label: '????????????',
                                    drawerTitle: '????????????',
                                    disabled: (obj) => {
                                        let data = obj.btnCallbackFn.getSelectedRows();
                                        if (data.length === 1 && !data[0].workId) {
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    },
                                    Component: (props) => {
                                        let flowData = props?.btnCallbackFn?.getSelectedRows?.()?.[0];

                                        return <WillExecute {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />
                                        // if (flowData && flowData.contentLength > 0) {
                                        //     return <FlowFormByMaterialManagementContract  {...this.props} type={'salary'} btnCallbackFn={props.qnnTableInstance} isInQnnTable={true} flowData={{ ...flowData }} />;
                                        // } else {
                                        //     return <div style={{ fontSize: '20px', padding: '10px' }}>?????????????????????!</div>
                                        // }
                                    }
                                },
                                {
                                    name: 'del',//??????add del
                                    icon: 'delete',//icon
                                    type: 'danger',//??????  ?????? primary  [primary dashed danger]
                                    label: '??????',
                                    fetchConfig: {//ajax??????
                                        apiName: 'batchDeleteZjXmSalaryUserExtensionHistoryEmploy',
                                    }
                                }
                            ]}
                            rowSelection={{
                                type: 'check',
                                getCheckboxProps: record => ({
                                    // name:record.name,
                                    disabled: record.apih5FlowStatus === '1' || record.apih5FlowStatus === '2',
                                }),
                            }}
                            drawerShowToggle={(args) => {
                                if (!args.drawerIsShow) {
                                    this.table.refresh()
                                }
                            }}
                            formConfig={[

                                {
                                    isInTable: false,
                                    form: {
                                        field: 'id',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'extensionHistoryId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'titleName',
                                        key: 'titleName',
                                        filter: true,
                                        width: 200,
                                        onClick: 'detail'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'declareTime',
                                        key: 'declareTime',
                                        width: 100,
                                        format: 'YYYY-MM-DD'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        width: 100
                                    },
                                    isInForm: false
                                },

                                {
                                    table: {
                                        title: '??????????????????',
                                        dataIndex: 'approvalLocation',
                                        key: 'approvalLocation',
                                        width: 120
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'apih5FlowStatus',
                                        key: 'apih5FlowStatus',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        field: "apih5FlowStatus",
                                        type: "select",
                                        placeholder: "?????????...",
                                        fetchConfig: {
                                            apiName: "getBaseCodeSelect",
                                            otherParams: {
                                                itemId: 'liuChengZhuangTai'
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId',
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'approvalTime',
                                        key: 'approvalTime',
                                        width: 100,
                                        format: 'YYYY-MM-DD'
                                    },
                                    isInForm: false
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        showType: "tile", //??????????????? bubble????????????  tile???????????? ??????bubble  ???0.6.15??????????????????????????????table????????????????????????table?????????
                                        width: 110,
                                        title: "??????",
                                        key: "action", //???????????????
                                        fixed: "right", //???????????????
                                        align: "center",
                                        btns: [
                                            {
                                                name: "edit", // ??????name??????add,  del, edit, detail, Component, form???
                                                label: "??????",
                                                onClick: (obj) => {
                                                    this.setState({
                                                        primaryKey: obj.rowData.zjXmSalaryEmployApprovalId
                                                    })
                                                },
                                                disabled: (args) => {
                                                    return args.rowData.apih5FlowStatus === '1' || args.rowData.apih5FlowStatus === '2'
                                                },
                                                formBtns: [
                                                    {
                                                        name: 'cancel', //??????????????????
                                                        type: 'dashed',//??????  ?????? primary
                                                        label: '??????',
                                                    },
                                                    {
                                                        name: 'submitDiy',//??????add del
                                                        type: 'primary',//??????  ?????? primary
                                                        label: '??????',//???????????????????????????????????? 
                                                        // fetchConfig: {//ajax??????
                                                        //     apiName: 'updateEmployUserExtensionHistory',
                                                        //     otherParams: (obj) => {
                                                        //         return {
                                                        //             approvalFlag: "employ",
                                                        //             zjXmSalaryEmployApprovalId: this.state.primaryKey
                                                        //         }
                                                        //     }
                                                        // },
                                                        onClick: async (obj) => {
                                                            if (obj.qnnTableInstance.getTabsIndex() === '0') {
                                                                const formData = await obj.qnnTableInstance.getDeawerValues()
                                                                const resData = await this.props.myFetch('getBaseCodeTree', { itemId: 'gangWeiGuanLi' })
                                                                let arrList = []
                                                                let TemporaryData = resData.data
                                                                const positionArr = formData.position.split(',')

                                                                const outerLoopFunc = (aouterData, targetData, i) => {
                                                                    targetData.map(item => {
                                                                        if (aouterData === item.itemId) {
                                                                            arrList[i] = item.itemName
                                                                            TemporaryData = item.children
                                                                        }
                                                                        return true
                                                                    })
                                                                }

                                                                for (let i = 0; i < positionArr.length; i++) {
                                                                    outerLoopFunc(positionArr[i], TemporaryData, i)
                                                                }

                                                                const { data, success, message } = await this.props.myFetch('updateEmployUserExtensionHistory', {
                                                                    ...obj._formData,
                                                                    nativePlace: obj._formData.nativePlace.toString(),
                                                                    legalAddress: obj._formData.legalAddress.toString(),
                                                                    position: obj._formData.position.toString(),
                                                                    positionName: arrList.join('/'),
                                                                    residenceAddress: obj._formData.residenceAddress.toString(),
                                                                    presentAddress: obj._formData.presentAddress.toString(),
                                                                    salaryInfo: {
                                                                        ...obj._formData.salaryInfo,
                                                                        levelSalaryId: obj._formData.salaryInfo.levelSalaryId.toString(),
                                                                    },
                                                                    companyId: companyId || null,
                                                                    companyName: companyName || null,
                                                                    projectId: projectId || null,
                                                                    projectName: projectName || null,
                                                                    departmentName: departmentName || null,
                                                                    departmentId: departmentId || null,
                                                                    orgId: orgId,
                                                                    approvalFlag: 'employ'
                                                                })

                                                                if (success) {
                                                                    Msg.success('????????????!')
                                                                    this.setState({
                                                                        primaryKey: data.zjXmSalaryEmployApprovalId
                                                                    }, () => {
                                                                        obj.btnCallbackFn.refresh();
                                                                        obj.btnCallbackFn.setActiveKey('1');
                                                                    })
                                                                } else {
                                                                    Msg.error(message)
                                                                }
                                                            } else {
                                                                this.batchSaveFunc(Number(obj.qnnTableInstance.getTabsIndex()))
                                                            }
                                                        },
                                                        //??????tab?????????????????????,???????????????
                                                        hide: (obj) => {
                                                            let index = obj.btnCallbackFn.getActiveKey();
                                                            if (index === "10") {
                                                                return true;
                                                            } else {
                                                                return false;
                                                            }
                                                        },
                                                    }
                                                ]
                                            },
                                        ]
                                    }
                                },
                            ]}
                            onTabsChange={(tabKey, qnnFormInstance, arg) => {
                                if (tabKey === '1' && qnnFormInstance.clickCb.rowInfo.name !== 'add') {
                                    this.setState({
                                        primaryKey: arg.rowData.zjXmSalaryEmployApprovalId
                                    })
                                }
                            }}
                            method={{
                                renderFender: (data) => {
                                    return {
                                        "0": "???",
                                        "1": "???"
                                    }[data]
                                },
                                tabItemDisabled: (obj) => {
                                    return !obj._formData()?.extensionHistoryId;
                                }
                            }}
                            tabs={[
                                {
                                    field: "????????????",
                                    name: "qnnForm",
                                    title: "????????????",
                                    content: {
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryUserExtensionHistoryDetail',//??????function ???????????????string
                                            params: {
                                                extensionHistoryId: "extensionHistoryId"
                                            },
                                            otherParams: {},
                                        },
                                        formConfig: [
                                            {
                                                type: 'Component',
                                                field: 'component',
                                                hide: (obj) => {
                                                    return this.state.isModalVisibleToLz || obj.clickCb.rowInfo.name === 'detail' || obj.clickCb.rowInfo.name === 'edit'
                                                },
                                                Component: () => {
                                                    return (
                                                        <div style={{ padding: '10px 0' }}>
                                                            <Row align='middle'>
                                                                <Col span={4}>
                                                                    <Button type="primary" onClick={() => {
                                                                        this.setState({
                                                                            isModalVisibleToLz: true
                                                                        })
                                                                    }}>??????????????????</Button>
                                                                </Col>
                                                                <Col span={20}>
                                                                    <div style={{ color: '#ff4d4f' }}>??????????????????????????????, ?????????????????????????????????????????????????????????</div>
                                                                </Col>
                                                            </Row>
                                                            <Divider />
                                                        </div>
                                                    )
                                                }
                                            },
                                            {
                                                type: 'string',
                                                label: 'extensionId',
                                                field: 'extensionId',
                                                hide: true
                                            },
                                            {
                                                type: 'string',
                                                label: 'extensionHistoryId',
                                                field: 'extensionHistoryId',
                                                hide: true
                                            },
                                            // reRecruit
                                            {
                                                type: 'number',
                                                label: 'reRecruit',
                                                field: 'reRecruit',
                                                hide: true
                                            },
                                            {
                                                type: 'string',
                                                label: '??????',
                                                field: 'realName',
                                                required: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: "gender", //????????????????????? 
                                                label: "??????",
                                                required: true,
                                                type: "radio",
                                                optionData: [
                                                    //??????????????????
                                                    {
                                                        label: "???",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "???",
                                                        value: "1"
                                                    }
                                                ],
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: "nation", //???????????????key
                                                label: "??????",
                                                required: true,
                                                type: "select",
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'minzhu'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',// 
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,

                                                formItemWrapperStyle: {
                                                    marginRight: '10px', //????????????????????????????????????????????????
                                                }
                                            },
                                            {
                                                type: 'date',
                                                label: '????????????',
                                                field: 'birthday',
                                                required: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                label: '??????', //????????????
                                                field: 'nativePlace', //?????????????????????
                                                required: true,
                                                type: 'cascader',
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children',
                                                    linkageFields: {
                                                        "nativePlaceName": "itemName"
                                                    }
                                                },
                                                fetchConfig: {//??????????????????????????????????????????
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
                                                label: '????????????',
                                                field: 'nativePlaceName',
                                                hide: true
                                            },
                                            {
                                                field: "politicCountenance", //???????????????key
                                                label: "????????????",
                                                type: "select",
                                                placeholder: "?????????",
                                                required: true,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'zhengzhimianmao'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children',
                                                    linkageFields: {
                                                        "politicCountenanceName": "itemName"
                                                    }
                                                },
                                                initialValue: "13",
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                formItemWrapperStyle: {
                                                    marginRight: '10px', //????????????????????????????????????????????????
                                                }
                                            },
                                            {
                                                type: 'string',
                                                label: '??????????????????',
                                                field: 'politicCountenanceName',
                                                initialValue: '??????',
                                                hide: true
                                            },
                                            {
                                                field: "idType", //????????????????????? 
                                                label: "????????????",
                                                required: true,
                                                type: "select",
                                                optionData: [
                                                    //??????????????????
                                                    {
                                                        label: "?????????",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "??????",
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
                                                        const { success, message } = await this.props.myFetch('checkIdNumber', parmas)
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
                                                field: "idNumber", //????????????????????? 
                                                label: "?????????",
                                                required: true,
                                                type: "string",
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
                                                        const { success, message } = await this.props.myFetch('checkIdNumber', parmas)
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
                                                field: "userStatus", //???????????????key
                                                label: "????????????",
                                                type: "select",
                                                placeholder: "?????????",
                                                required: true,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'renYuanZhuangTai'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                formItemWrapperStyle: {
                                                    marginRight: '10px', //????????????????????????????????????????????????
                                                }
                                            },


                                            {
                                                label: '????????????', //????????????
                                                field: 'presentAddress', //?????????????????????
                                                required: true,
                                                type: 'cascader',
                                                placeholder: '?????????',
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                fetchConfig: {//??????????????????????????????????????????
                                                    apiName: 'getBaseCodeTree',
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                // label: '????????????', //????????????
                                                field: 'presentDetailedAddress', //?????????????????????
                                                type: 'string',
                                                placeholder: '?????????????????????',
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                formItemStyle: {
                                                    marginLeft: 0
                                                },
                                                required: true,
                                            },
                                            {
                                                label: '??????', //????????????
                                                field: 'postalCode', //?????????????????????
                                                type: 'postalCode',
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                required: true,
                                                formItemWrapperStyle: {
                                                    marginRight: '10px', //????????????????????????????????????????????????
                                                }
                                            },
                                            {
                                                type: "images",
                                                desc: "????????????",
                                                label: " ",
                                                colon: false,
                                                field: "latestAttachmentList", //?????????????????? ***??????
                                                fetchConfig: {
                                                    apiName: "upload"
                                                },
                                                max: 1,
                                                className: "Upload-photo",
                                                required: false,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                formItemWrapperStyle: {
                                                    position: "absolute",
                                                    right: "60px",
                                                    top: '60px'
                                                }
                                            },
                                            {
                                                label: '???????????????', //????????????
                                                field: 'residenceAddress', //?????????????????????
                                                required: true,
                                                type: 'cascader',
                                                placeholder: '?????????',
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                fetchConfig: {//??????????????????????????????????????????
                                                    apiName: 'getBaseCodeTree',
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: 'residenceDetailedAddress', //?????????????????????
                                                type: 'string',
                                                placeholder: '?????????????????????',
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                formItemStyle: {
                                                    marginLeft: 0
                                                },
                                                required: true,
                                            },
                                            {
                                                field: "phoneNumber", //???????????????key
                                                label: "????????????",
                                                type: "phone",
                                                placeholder: "?????????",
                                                required: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: "maritalStatus", //???????????????key
                                                label: "????????????",
                                                type: "select",
                                                placeholder: "?????????",
                                                required: true,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'hunYinZhuangKuang'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },


                                            {
                                                label: '????????????????????????', //????????????
                                                field: 'legalAddress', //?????????????????????
                                                required: true,
                                                type: 'cascader',
                                                placeholder: '?????????',
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                fetchConfig: {//??????????????????????????????????????????
                                                    apiName: 'getBaseCodeTree',
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout2,
                                            },
                                            {
                                                field: 'legalDetailedAddress', //?????????????????????
                                                type: 'string',
                                                placeholder: '?????????????????????',
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                                formItemStyle: {
                                                    marginLeft: 0
                                                },
                                                required: true,
                                            },
                                            {
                                                type: 'date',
                                                label: '??????????????????',
                                                field: 'workFirstDate',
                                                required: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout2,
                                            },
                                            {
                                                type: 'date',
                                                label: '????????????',
                                                field: 'hiredate',
                                                placeholder: '?????????',
                                                required: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: "title", //???????????????key
                                                label: "??????",
                                                type: "select",
                                                disabled: true,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'zhiChengMingCheng'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: "certificateName", //???????????????key
                                                label: "????????????",
                                                type: "string",
                                                disabled: true,
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                field: "userType", //???????????????key
                                                label: "????????????",
                                                type: "select",
                                                required: true,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'pinYongLeiBie'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children',
                                                    linkageFields: {
                                                        "userTypeName": 'itemName',
                                                    }
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout2,
                                            },
                                            {
                                                type: 'string',
                                                label: '??????????????????',
                                                field: 'userTypeName',
                                                hide: true
                                            },
                                            {
                                                field: "position", //???????????????key
                                                label: "??????",
                                                type: "cascader",
                                                required: true,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeTree",
                                                    otherParams: {
                                                        itemId: 'gangWeiGuanLi'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                span: fourItemSpan,
                                                formItemLayout: fourItemLayout,
                                            },
                                            {
                                                type: 'select',
                                                label: '????????????',
                                                field: 'positionReserve',
                                                // required: true,
                                                span: 12,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeTree",
                                                    otherParams: {
                                                        itemId: 'tuijiangangwei'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
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
                                                disabled: true,

                                            },
                                            {
                                                field: "positionType", //???????????????key
                                                label: "????????????",
                                                type: "select",
                                                disabled: true,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeSelect",
                                                    otherParams: {
                                                        itemId: 'zhuJianZhi'
                                                    }
                                                },
                                                optionConfig: {//??????????????????
                                                    label: 'itemName', //?????? label
                                                    value: 'itemId',//
                                                    children: 'children'
                                                },
                                                span: 12,
                                                formItemLayout: {
                                                    labelCol: {
                                                        sm: { span: 4 }
                                                    },
                                                    wrapperCol: {
                                                        sm: { span: 20 }
                                                    }
                                                },
                                            },
                                            {
                                                field: "projectTree",
                                                label: "??????/??????",
                                                type: "treeSelect",
                                                required: true,
                                                span: 8,
                                                formItemLayout: fourItemLayout,
                                                initialValue: [{
                                                    label: this.state.companyAnddepartment.projectName ? this.state.companyAnddepartment.projectName : this.state.companyAnddepartment.companyName,
                                                    value: this.state.companyAnddepartment.projectId ? this.state.companyAnddepartment.projectId : this.state.companyAnddepartment.companyId,
                                                }],
                                                disabled: true,
                                                treeSelectOption: {
                                                    selectType: "1",
                                                    maxNumber: 1,
                                                    fetchConfig: {
                                                        //??????????????????????????????????????????
                                                        apiName: "getSysDepartmentCurrentTree",
                                                        paramsKey: "departmentParentId",
                                                    }
                                                }
                                            },
                                            // {
                                            //     field: "projectTree",
                                            //     label: "??????/??????",
                                            //     type: "select",
                                            //     required: true,
                                            //     span: fourItemSpan,
                                            //     formItemLayout: fourItemLayout,
                                            //     fetchConfig: {
                                            //         apiName: "getSysCompanyProject",                                                
                                            //     },
                                            //     optionConfig: {//??????????????????
                                            //         label: 'companyName', //?????? label
                                            //         value: 'companyId'
                                            //     },
                                            // },
                                            {
                                                field: "departmentTree",
                                                label: "????????????",
                                                type: "treeSelect",
                                                required: true,
                                                span: 8,
                                                formItemLayout: fourItemLayout,
                                                // initialValue: [{
                                                //     label: this.state.value.label,
                                                //     title: this.state.value.label,
                                                //     type: this.state.value.type,
                                                //     userDeptId: this.state.value.userDeptId,
                                                //     value: this.state.value.value,
                                                //     valuePid: this.state.value.valuePid,
                                                // }],
                                                initialValue: [{ label: projectName, value: projectId }],
                                                treeSelectOption: {
                                                    selectType: "1",
                                                    maxNumber: 1,
                                                    fetchConfig: {
                                                        //??????????????????????????????????????????
                                                        apiName: "getSysDepartmentCurrentTree",
                                                        paramsKey: "departmentParentId",
                                                    },
                                                    // nodeClick: (node) => {
                                                    //     this.setState({
                                                    //         changeBmValue: node
                                                    //     })
                                                    // }
                                                },
                                                onChange: async (val, obj) => {
                                                    if (val.length) {
                                                        const { data } = await this.props.myFetch('getSysComDeptProById', { departmentId: val[0].value })
                                                        obj.form.setFieldsValue({
                                                            ...obj.form.getFieldsValue(),
                                                            projectTree: [
                                                                {
                                                                    label: data.projectName ? data.projectName : data.companyName,
                                                                    value: data.projectId ? data.projectId : data.companyId,
                                                                }
                                                            ]
                                                        })
                                                    }
                                                }
                                            },
                                            {
                                                field: "officeTree",
                                                label: "????????????",
                                                type: "treeSelect",
                                                required: true,
                                                span: 8,
                                                formItemLayout: fourItemLayout,
                                                treeSelectOption: {
                                                    selectType: "1",
                                                    maxNumber: 1,
                                                    fetchConfig: {
                                                        //??????????????????????????????????????????
                                                        apiName: "getSysDepartmentCurrentTree",
                                                        paramsKey: "departmentParentId",
                                                    },
                                                },
                                            },



                                            {
                                                field: "hobby", //???????????????key
                                                label: "???????????????",
                                                type: "textarea",
                                                formItemLayout: oneItemLayout,
                                                formItemWrapperStyle: oneFormItemWrapperStyle
                                            },


                                            {
                                                type: 'files',
                                                label: '?????????',
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
                                                label: "????????????",
                                                formFields: [
                                                    // {
                                                    //     field: "????????????Id",
                                                    //     hide: true,
                                                    //     type: "string"
                                                    // },
                                                    {
                                                        label: "????????????",
                                                        field: "physicalType",
                                                        type: "select",
                                                        disabled: true,
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'tiJianLeiXing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',//
                                                            children: 'children'
                                                        },
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },
                                                    {
                                                        label: "????????????",
                                                        field: "healthCondition",
                                                        type: "select",
                                                        disabled: true,
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'jianKangQingKuang'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',//
                                                            children: 'children'
                                                        },
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },
                                                    {
                                                        label: "???????????????",
                                                        field: "occupationalDisease",
                                                        type: "select",
                                                        disabled: true,
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhiYeBingQingKuang'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',//
                                                            children: 'children'
                                                        },
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },



                                                    {
                                                        field: "height",
                                                        label: "??????/cm",
                                                        type: "number",
                                                        required: true,
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                        placeholder: "?????????...",
                                                    },
                                                    {
                                                        field: "weight",
                                                        label: "??????/kg",
                                                        type: "number",
                                                        required: true,
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                        placeholder: "?????????..."
                                                    },


                                                    {
                                                        label: "??????",
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
                                                label: "????????????",
                                                qnnFormConfig: {
                                                    formConfig: [
                                                        {
                                                            label: "????????????",
                                                            field: "levelSalaryId",
                                                            type: "cascader",
                                                            required: true,
                                                            fetchConfig: {
                                                                apiName: "getZjXmSalaryPositionLevelSalarySelect",
                                                                otherParams: {
                                                                    itemId: 'gongrenzhongzhong'
                                                                }
                                                            },
                                                            optionConfig: {//??????????????????
                                                                label: 'label', //?????? label
                                                                value: 'value',//
                                                                children: 'showData',
                                                                linkageFields: {
                                                                    "salaryInfo.salaryId": 'value',
                                                                }
                                                            },
                                                            span: fourItemSpan,
                                                            formItemLayout: fourItemLayout,
                                                            //??????????????? onChange ??????????????????????????????
                                                            onChange: async (val, obj) => {
                                                                const salaryId = val[val.length - 1]
                                                                const { data, success } = await this.props.myFetch('getPositionLevelSalaryDetails', { levelSalaryId: salaryId })
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
                                                            label: '??????',
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
                                                            label: "????????????",
                                                            field: "accountingType",
                                                            type: "select",
                                                            fetchConfig: {
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'kuaiJiFenLei'
                                                                }
                                                            },
                                                            optionConfig: {//??????????????????
                                                                label: 'itemName', //?????? label
                                                                value: 'itemId',//
                                                                children: 'children'
                                                            },
                                                            span: fourItemSpan,
                                                            formItemLayout: fourItemLayout,
                                                        },
                                                        {
                                                            label: '???????????????', //????????????
                                                            field: 'socialInsuranceArea', //?????????????????????
                                                            required: true,
                                                            type: 'select',
                                                            placeholder: '?????????',
                                                            optionConfig: {//??????????????????
                                                                label: 'itemName', //?????? label
                                                                value: 'itemId',//
                                                                children: 'children'
                                                            },
                                                            fetchConfig: {//??????????????????????????????????????????
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'xingzhengquhuadaima'
                                                                }
                                                            },
                                                            span: fourItemSpan,
                                                            formItemLayout: fourItemLayout,
                                                        },
                                                        {
                                                            label: '??????????????????', //????????????
                                                            field: 'providentFundArea', //?????????????????????
                                                            required: true,
                                                            type: 'select',
                                                            placeholder: '?????????',
                                                            optionConfig: {//??????????????????
                                                                label: 'itemName', //?????? label
                                                                value: 'itemId',//
                                                                children: 'children'
                                                            },
                                                            fetchConfig: {//??????????????????????????????????????????
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
                                                            label: "????????????????????????",
                                                            type: "treeSelect",
                                                            required: true,
                                                            span: fourItemSpan,
                                                            formItemLayout: fourItemLayout2,
                                                            treeSelectOption: {
                                                                selectType: "1",
                                                                maxNumber: 1,
                                                                fetchConfig: {
                                                                    //??????????????????????????????????????????
                                                                    apiName: "getSysDepartmentCurrentTree",
                                                                    paramsKey: "departmentParentId",
                                                                }
                                                            }
                                                        },
                                                        {
                                                            label: '????????????',
                                                            field: 'externalUnit', //?????????????????????
                                                            required: true,
                                                            type: 'select',
                                                            placeholder: '?????????',
                                                            optionConfig: {//??????????????????
                                                                label: 'itemName', //?????? label
                                                                value: 'itemId',//
                                                                children: 'children'
                                                            },
                                                            fetchConfig: {//??????????????????????????????????????????
                                                                apiName: "getBaseCodeSelect",
                                                                otherParams: {
                                                                    itemId: 'waiZaiDanWei'
                                                                }
                                                            },
                                                            span: fourItemSpan,
                                                            formItemLayout: fourItemLayout,
                                                        },
                                                        {
                                                            label: '????????????', //????????????
                                                            field: 'involvedTopic', //?????????????????????
                                                            required: true,
                                                            type: 'select',
                                                            placeholder: '?????????',
                                                            optionConfig: {//??????????????????
                                                                label: 'itemName', //?????? label
                                                                value: 'itemId',//
                                                                children: 'children'
                                                            },
                                                            fetchConfig: {//??????????????????????????????????????????
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
                                                label: "????????????",
                                                formFields: [
                                                    // {
                                                    //     field: "??????????????????Id",
                                                    //     hide: true,
                                                    //     type: "string"
                                                    // },
                                                    {
                                                        type: 'string',
                                                        label: '????????????',
                                                        field: 'contractNo',
                                                        required: false,
                                                        disabled: true,
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },
                                                    {
                                                        type: 'date',
                                                        label: '????????????',
                                                        field: 'signingDate',
                                                        required: false,
                                                        disabled: true,
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },
                                                    {
                                                        label: "????????????",
                                                        field: "contractType",
                                                        type: "select",
                                                        disabled: true,
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'heTongLeiXing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',//
                                                            children: 'children'
                                                        },
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },

                                                    {
                                                        type: 'date',
                                                        label: '??????????????????',
                                                        field: 'contractStartDate',
                                                        placeholder: '?????????',
                                                        disabled: true,
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },
                                                    {
                                                        type: 'date',
                                                        label: '??????????????????',
                                                        field: 'contractEndDate',
                                                        placeholder: '?????????',
                                                        disabled: true,
                                                        span: fourItemSpan,
                                                        formItemLayout: fourItemLayout,
                                                    },
                                                ],
                                            },

                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    name: "qnnTable",
                                    title: "????????????",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.xlqk = me },
                                        antd: {
                                            rowKey: "educationHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryEducationBackgroundHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                                addRowDefaultData: {
                                                    isFirstEducation: '0',
                                                    isHighestEducation: '0'
                                                }
                                                // addRowFetchConfig: {
                                                //     apiName: "addZjXmSalaryEducationBackground",
                                                //     params: {
                                                //         extensionId: "extensionId"
                                                //     },
                                                // },
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryEducationBackground')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        dataFormat: (tableData) => {
                                            let newTableData = tableData.map(item => {
                                                return {
                                                    ...item,
                                                    enrollmentDateGraduateDate: [item.enrollmentDate, item.graduateDate]
                                                }
                                            })
                                            return newTableData
                                        },
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '???????????? - ???/????????????',
                                                    dataIndex: 'enrollmentDateGraduateDate',
                                                    format: 'YYYY-MM-DD',
                                                    tdEdit: true,
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
                                                    },
                                                    render: (val) => {
                                                        return (
                                                            <div>{this.dateToStringFunc(val)}</div>
                                                        )
                                                    }
                                                }
                                            },
                                            // {
                                            //     isInForm: false,
                                            //     table: {
                                            //         title: '???/????????????',
                                            //         dataIndex: 'graduateDate',
                                            //         format: 'YYYY-MM-DD',
                                            //         tdEdit: true,
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
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'date',
                                                    field: 'graduateDate',
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '???/????????????',
                                                    dataIndex: 'graduateSchool',
                                                    tdEdit: true,
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
                                                    title: '??????',
                                                    dataIndex: 'education',
                                                    type: "select",
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryEducationBackground"
                                                    // },
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'xueli'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                        onChange: (val, obj) => {
                                                            this.setFieldlabelFunc(obj, 'educationName')
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
                                                    title: '??????',
                                                    dataIndex: 'degree',
                                                    tdEdit: true,
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
                                                    title: '??????',
                                                    dataIndex: 'major',
                                                    type: "select",
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryEducationBackground"
                                                    // },
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhuanye'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                        onChange: (val, obj) => {
                                                            this.setFieldlabelFunc(obj, 'majorName')
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
                                                    title: '??????????????????',
                                                    dataIndex: 'degreeAwardDate',
                                                    format: 'YYYY-MM-DD',
                                                    tdEdit: true,
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
                                                    title: '??????????????????',
                                                    dataIndex: 'isFirstEducation',
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryEducationBackground"
                                                    // },
                                                    fieldConfig: {
                                                        type: "radio",
                                                        optionData: [
                                                            {
                                                                label: "???",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "???",
                                                                value: "1"
                                                            }
                                                        ],
                                                        initialValue: '0',
                                                        onChange: (val, obj, row) => {
                                                            this.highestDegreeOnly(obj, row, 'isFirstEducation')
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????????????????',
                                                    dataIndex: 'isHighestEducation',
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryEducationBackground"
                                                    // },
                                                    fieldConfig: {
                                                        type: "radio",
                                                        optionData: [
                                                            {
                                                                label: "???",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "???",
                                                                value: "1"
                                                            }
                                                        ],
                                                        onChange: (val, obj, row) => {
                                                            this.highestDegreeOnly(obj, row, 'isHighestEducation')
                                                        }
                                                    },

                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'fileEducationList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //??????????????????????????????????????????
                                                            apiName: "upload"
                                                        },
                                                    },
                                                    render: (val) => {
                                                        return val ? <TableFileDownLoad info={val[0]} {...this.props} /> : <div></div>
                                                    }
                                                }
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    name: "qnnTable",
                                    title: "????????????",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gzll = me },
                                        antd: {
                                            rowKey: "experienceHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryWorkExperienceHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                                // addRowFetchConfig: {
                                                //     apiName: "addZjXmSalaryWorkExperience",
                                                //     params: {
                                                //         extensionId: "extensionId"
                                                //     },
                                                // },
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                fetchConfig: {//ajax??????
                                                    apiName: 'batchDeleteUpdateZjXmSalaryWorkExperience',
                                                },
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryWorkExperience')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        dataFormat: (tableData) => {
                                            let newTableData = tableData.map(item => {
                                                return {
                                                    ...item,
                                                    startDateEndDate: [item.startDate, item.endDate]
                                                }
                                            })
                                            return newTableData
                                        },
                                        formConfig: [

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '???????????? - ????????????',
                                                    dataIndex: 'startDateEndDate',
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryWorkExperience"
                                                    // },
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
                                                    render: (val) => {
                                                        return (
                                                            <div>{this.dateToStringFunc(val)}</div>
                                                        )
                                                    }
                                                }
                                            },
                                            // {
                                            //     isInForm: false,
                                            //     table: {
                                            //         title: '????????????',
                                            //         dataIndex: 'endDate',
                                            //         tdEdit: true,
                                            //         // tdEditFetchConfig: {
                                            //         //     apiName: "updateZjXmSalaryWorkExperience"
                                            //         // },
                                            //         fieldConfig: {
                                            //             type: 'date',
                                            //         }
                                            //     }
                                            // },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'date',
                                                    field: 'startDate',
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'date',
                                                    field: 'endDate',
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'workUnit',
                                                    tdEdit: true,
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
                                                    title: '????????????',
                                                    dataIndex: 'department',
                                                    tdEdit: true,
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
                                                    title: '??????',
                                                    dataIndex: 'position',
                                                    tdEdit: true,
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
                                                    title: '??????????????????',
                                                    dataIndex: 'workContent',
                                                    tdEdit: true,
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
                                                    title: '?????????',
                                                    dataIndex: 'certifier',
                                                    tdEdit: true,
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
                                                    title: '????????????',
                                                    dataIndex: 'phoneNumber',
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryWorkExperience"
                                                    // },
                                                    fieldConfig: {
                                                        type: "phone"
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'positionType',
                                                    type: "select",
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryWorkExperience"
                                                    // },
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhuJianZhi'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                        onChange: (val, obj) => {
                                                            this.setFieldlabelFunc(obj, 'positionTypeName')
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
                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.zyjs = me },
                                        antd: {
                                            rowKey: "technologyHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryProfessionalTechnologyHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                                // addRowFetchConfig: {
                                                //     apiName: "addZjXmSalaryProfessionalTechnology",
                                                //     params: {
                                                //         extensionId: "extensionId"
                                                //     },
                                                // },
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryProfessionalTechnology')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        formConfig: [

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'title',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhiChengMingCheng'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                        onChange: (val, obj) => {
                                                            this.setFieldlabelFunc(obj, 'titleName')
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
                                                    title: '????????????',
                                                    dataIndex: 'specialty',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "string"
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'level',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhiChengJiBie'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????????????????',
                                                    dataIndex: 'access',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhiChengHuoQuTuJing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????????????????',
                                                    dataIndex: 'qualificationNo',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "string"
                                                    }
                                                }
                                            },

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????????????????',
                                                    dataIndex: 'acquisitionDate',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: 'date',
                                                        placeholder: '?????????',
                                                        required: false,

                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'certificateNo',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "string"
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'issueUnit',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "string"
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'fileList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //??????????????????????????????????????????
                                                            apiName: "upload"
                                                        },
                                                    },
                                                    render: (val) => {
                                                        return val ? <TableFileDownLoad info={val[0]} {...this.props} /> : <div></div>
                                                    }
                                                }
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.htgl = me },
                                        antd: {
                                            rowKey: "contractHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryContractManagementHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryContractManagement')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        dataFormat: (tableData) => {
                                            let newTableData = tableData.map(item => {
                                                return {
                                                    ...item,
                                                    startDateEndDate: [item.startDate, item.endDate]
                                                }
                                            })
                                            return newTableData
                                        },
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
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
                                                    title: '??????????????????',
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
                                                    title: '????????????',
                                                    dataIndex: 'contractType',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'heTongLeiXing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????????????????????? - ???????????????????????',
                                                    dataIndex: 'startDateEndDate',
                                                    tdEdit: true,
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
                                                    render: (val) => {
                                                        return (
                                                            <div>{this.dateToStringFunc(val)}</div>
                                                        )
                                                    }
                                                }
                                            },
                                            // {
                                            //     isInForm: false,
                                            //     table: {
                                            //         width: 150,
                                            //         title: '???????????????????????',
                                            //         dataIndex: 'endDate',
                                            //         tdEdit: true,
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
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'date',
                                                    field: 'endDate',
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    width: 150,
                                                    title: '?????????????????????',
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
                                                    title: '?????????',
                                                    dataIndex: 'probation',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'shiYongQi'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        }
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'signingType',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'qianDingLeiXing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    width: 150,
                                                    title: '?????????????????????',
                                                    dataIndex: 'fileQuitList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //??????????????????????????????????????????
                                                            apiName: "upload"
                                                        },
                                                    },
                                                    render: (val) => {
                                                        return val ? <TableFileDownLoad info={val[0]} {...this.props} /> : <div></div>
                                                    }
                                                }
                                            },

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'fileContractList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //??????????????????????????????????????????
                                                            apiName: "upload"
                                                        },
                                                    },
                                                    render: (val) => {
                                                        return val ? <TableFileDownLoad info={val[0]} {...this.props} /> : <div></div>
                                                    }
                                                }
                                            },

                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.pxqk = me },
                                        antd: {
                                            rowKey: "trainingHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryTrainingSituationHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryTrainingSituation')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        dataFormat: (tableData) => {
                                            let newTableData = tableData.map(item => {
                                                return {
                                                    ...item,
                                                    startDateEndDate: [item.startDate, item.endDate]
                                                }
                                            })
                                            return newTableData
                                        },
                                        formConfig: [

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '???????????? - ????????????',
                                                    dataIndex: 'startDateEndDate',
                                                    tdEdit: true,
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
                                                    render: (val) => {
                                                        return (
                                                            <div>{this.dateToStringFunc(val)}</div>
                                                        )
                                                    }
                                                }
                                            },
                                            // {
                                            //     isInForm: false,
                                            //     table: {
                                            //         title: '????????????',
                                            //         dataIndex: 'endDate',
                                            //         tdEdit: true,
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
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'date',
                                                    field: 'endDate',
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
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
                                                    title: '????????????',
                                                    dataIndex: 'trainingType',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'peiXunLeiXing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????????????????',
                                                    dataIndex: 'trainingMode',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'peiXunXueXiFangShi'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
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
                                                    title: '????????????',
                                                    dataIndex: 'fileList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //??????????????????????????????????????????
                                                            apiName: "upload"
                                                        },
                                                    },
                                                    render: (val) => {
                                                        return val ? <TableFileDownLoad info={val[0]} {...this.props} /> : <div></div>
                                                    }
                                                }
                                            },

                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.jtzk = me },
                                        antd: {
                                            rowKey: "familyHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryFamilyBackgroundHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                                addRowDefaultData: {
                                                    isUrgentLinkMan: '0'
                                                }
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryFamilyBackground')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'relationship',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'yuBenRenGuanXi'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????',
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
                                                    title: '?????????????????????',
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
                                                    title: '??????',
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
                                                    title: '????????????',
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
                                                    title: '?????????????????????',
                                                    dataIndex: 'isUrgentLinkMan',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "radio",
                                                        optionData: [
                                                            {
                                                                label: "???",
                                                                value: "0"
                                                            },
                                                            {
                                                                label: "???",
                                                                value: "1"
                                                            }
                                                        ]
                                                    },
                                                }
                                            },


                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.jkqk = me },
                                        antd: {
                                            rowKey: "healthId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryHealthConditionHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryHealthCondition')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'physicalType',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'tiJianLeiXing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
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
                                                    title: '????????????',
                                                    dataIndex: 'healthCondition',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'jianKangQingKuang'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'occupationalDisease',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhiYeBingQingKuang'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'fileList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //??????????????????????????????????????????
                                                            apiName: "upload"
                                                        },
                                                    },
                                                    render: (val) => {
                                                        return val ? <TableFileDownLoad info={val[0]} {...this.props} /> : <div></div>
                                                    }
                                                }
                                            },

                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.zsgl = me },
                                        antd: {
                                            rowKey: "certificateHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryCertificateManagementHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'delDiy',
                                                icon: 'delete',
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryCertificateManagement')
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        dataFormat: (tableData) => {
                                            let newTableData = tableData.map(item => {
                                                return {
                                                    ...item,
                                                    startDateEndDate: [item.startDate, item.endDate]
                                                }
                                            })
                                            return newTableData
                                        },
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    width: 140,
                                                    fixed: "left",
                                                    title: '????????????',
                                                    dataIndex: 'certificateType',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhengShuLeiXing'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
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
                                                    title: '????????????',
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
                                                    title: '????????????',
                                                    dataIndex: 'certificateMajor',
                                                    type: "select",
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhengShuZhuanYe'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    width: 120,
                                                    title: '????????????',
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
                                                    title: '????????????',
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
                                                    title: '????????????????????????',
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
                                                    title: '?????????????????????',
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
                                                    title: '????????????',
                                                    dataIndex: 'apportionYear',
                                                    type: "select",
                                                    tdEdit: true,
                                                    width: 300,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'fenTanNianDu'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',// 
                                                        },
                                                        multiple: true,
                                                        pushJoin: true,
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'paidYear',
                                                    type: "select",
                                                    tdEdit: true,
                                                    width: 300,
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'yiFaFangNianDu'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
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
                                                    title: '??????????????????',
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
                                                    title: '?????????????????? - ??????????????????',
                                                    dataIndex: 'startDateEndDate',
                                                    tdEdit: true,
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
                                                    render: (val) => {
                                                        return (
                                                            <div>{this.dateToStringFunc(val)}</div>
                                                        )
                                                    }
                                                }
                                            },
                                            // {
                                            //     isInForm: false,
                                            //     table: {
                                            //         width: 140,
                                            //         title: '??????????????????',
                                            //         dataIndex: 'endDate',
                                            //         tdEdit: true,
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
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'date',
                                                    field: 'endDate',
                                                    placeholder: '?????????',
                                                    required: false,
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    width: 130,
                                                    title: '??????',
                                                    dataIndex: 'fileList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //??????????????????????????????????????????
                                                            apiName: "upload"
                                                        },
                                                    },
                                                    render: (val) => {
                                                        return val ? <TableFileDownLoad info={val[0]} {...this.props} /> : <div></div>
                                                    }
                                                }
                                            },

                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.zzmm = me },
                                        antd: {
                                            rowKey: "zjXmSalaryPoliticalHistoryId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryPoliticalHistoryList',//??????function ???????????????string
                                            otherParams: async (obj) => {
                                                return {
                                                    extensionHistoryId: this.table.getDeawerValuesSync().extensionHistoryId
                                                }
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addDiy", //??????add del
                                                icon: "plus", //icon
                                                type: "primary", //??????  ?????? primary  [primary dashed danger]
                                                label: "??????",
                                                onClick: async (obj, row, a) => {
                                                    const basicInfo = obj.props.parentTableInfo.rowData


                                                    this.setState({
                                                        PoliticalOutlookVisible: true,
                                                        zzmmAddOrUpdate: 'add',
                                                        extensionHistoryId: (await this.table.getDeawerValues()).extensionHistoryId
                                                    })
                                                }
                                            },
                                            {
                                                field: 'editDiy',
                                                label: '??????',
                                                isValidate: true,//??????????????????????????? ??????true
                                                type: 'primary', //primary dashed danger
                                                disabled: (obj) => {
                                                    if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                onClick: (obj) => {
                                                    const basicInfo = obj.props.parentTableInfo.rowData
                                                    this.setState({
                                                        PoliticalOutlookVisible: true,
                                                        zzmmAddOrUpdate: 'update',
                                                        extensionHistoryId: basicInfo.extensionHistoryId,
                                                        politicalPK: obj.selectedRows[0].zjXmSalaryPoliticalHistoryId
                                                    })
                                                }
                                            },
                                            {
                                                field: 'delBtn',
                                                name: 'del',
                                                icon: 'delete',
                                                fetchConfig: {//ajax??????
                                                    apiName: 'batchDeleteUpdateZjXmSalaryPolitical',
                                                },
                                                type: 'danger',
                                                label: '??????',
                                            },
                                        ],
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    fixed: "left",
                                                    title: '????????????',
                                                    dataIndex: 'policitalStatus',
                                                    type: "select",
                                                    fieldConfig: {
                                                        type: "select",
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'zhengzhimianmao'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
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
                                                    title: '????????????',
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
                                                    title: '???????????????',
                                                    dataIndex: 'branchName',
                                                    fieldConfig: {
                                                        type: 'string',
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????????????????',
                                                    dataIndex: 'relationshipLoc',
                                                    field: 'relationshipLoc',
                                                    type: 'select',
                                                    fieldConfig: {
                                                        type: 'select',
                                                        field: 'relationshipLoc',
                                                        fetchConfig: {//??????????????????????????????????????????
                                                            apiName: "getBaseCodeSelect",
                                                            otherParams: {
                                                                itemId: 'dangYuanZuZhiGuanXiSuoZaiDi'
                                                            }
                                                        },
                                                        optionConfig: {//??????????????????
                                                            label: 'itemName', //?????? label
                                                            value: 'itemId',//
                                                            children: 'children'
                                                        },
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '?????????',
                                                    dataIndex: 'introducePerson',
                                                    fieldConfig: {
                                                        type: 'string',
                                                    },
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'formalTime',
                                                    format: 'YYYY-MM-DD',
                                                    fieldConfig: {
                                                        type: 'date',
                                                    },
                                                }
                                            },
                                        ]
                                    }
                                },
                            ]}
                        /> : null
                }
                <PoliticalOutlook
                    {...this.props}
                    visible={this.state.PoliticalOutlookVisible}
                    status={this.state.zzmmAddOrUpdate}
                    reSetVisible={(isRefresh) => {
                        this.setState({
                            PoliticalOutlookVisible: false
                        })

                        if (isRefresh) {
                            this.zzmm.refresh()
                        }
                        this.zzmm.clearSelectedRows()
                    }}
                    basicInfo={this.table?.getQnnForm()}
                    id={this.state.extensionHistoryId}
                    isPerson={false}
                    pk={this.state.politicalPK} />

                <Modal
                    title="??????????????????"
                    visible={this.state.isModalVisibleToLz}
                    width={'60%'}
                    onOk={async () => {
                        if (this.selectedKeysForModal) {
                            const res = await this.props.myFetch('checkQuitReRecruit', { extensionId: this.selectedKeysForModal, orgId })
                            if (res.success) {
                                // ???????????????, ???????????????set??? modalTableRefs ?????????
                                // this.selectedDataForModal 
                                const { data, success, message } = await this.props.myFetch('pcGetZjXmSalaryUserExtensionDetails', {
                                    extensionId: this.selectedKeysForModal,
                                    // approvalFlag: 'employ'
                                })
                                if (success) {
                                    this.table.setDeawerValues({ ...data, reRecruit: 2, extensionId: this.selectedKeysForModal, approvalFlag: 'employ' })
                                    this.setState({
                                        isModalVisibleToLz: false
                                    })
                                } else {
                                    Msg.error(message)
                                }
                            } else {
                                Msg.error(res.message)
                            }
                        } else {
                            Msg.warning('??????????????????')
                        }

                    }}
                    onCancel={() => {
                        confirm({
                            title: '????????????',
                            content: '??????????????????????',
                            onOk() {

                            },
                            onCancel: () => {
                                this.setState({
                                    isModalVisibleToLz: false
                                })
                            },
                            cancelText: '???',
                            okText: '???'
                        })
                    }}
                    // {
                    // ...isDetail ? { footer: null } : null
                    // }
                    destroyOnClose={true}
                    okText={'??????'}
                    confirmLoading={false}
                >
                    <QnnTable
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
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
                                this.selectedKeysForModal = selectedRowKey[0]
                            }
                        }}
                        formConfig={[
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'realName',
                                    filter: true,
                                }
                                , form: {
                                    type: 'string',
                                    placeholder: '?????????',
                                    required: false,
                                }
                            },
                            {

                                table: {
                                    title: '??????',
                                    dataIndex: 'gender',
                                    type: "radio",
                                }
                                , form: {
                                    field: "gender", //????????????????????? 
                                    label: "??????",
                                    type: "radio",
                                    optionData: [
                                        //??????????????????
                                        {
                                            label: "???",
                                            value: "0"
                                        },
                                        {
                                            label: "???",
                                            value: "1"
                                        }
                                    ],
                                },
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'birthday',
                                    type: 'date',
                                },
                                form: {
                                    type: 'date',
                                    label: '????????????',
                                    field: 'birthday',
                                },
                            },
                            {

                                table: {
                                    title: '????????????',
                                    dataIndex: 'idType',
                                    type: "select",
                                }
                                , form: {
                                    field: "idType", //????????????????????? 
                                    label: "????????????",
                                    type: "select",
                                    optionData: [
                                        //??????????????????
                                        {
                                            label: "?????????",
                                            value: "0"
                                        },
                                        {
                                            label: "??????",
                                            value: "1"
                                        }
                                    ],
                                },
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'idNumber',
                                    filter: true,
                                }
                                , form: {
                                    field: "idNumber", //????????????????????? 
                                    label: "?????????",
                                    type: "string",
                                },
                            },

                        ]}
                    />
                </Modal>
            </div>
        );
    }
}

export default index;