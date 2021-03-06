import React from 'react';
import QnnTable from 'qnn-table';
import s from "./style.less"
import Tree from "../modules/tree";
import { message as Msg, Tabs, Modal } from 'antd';
import Apih5 from "qnn-apih5"
import PoliticalOutlook from './PoliticalOutlook'
import TableFileDownLoad from '../common/tableFileDownLoad';
import moment from "moment";
const { TabPane } = Tabs;
const { confirm } = Modal;
class Page extends Apih5 {
    constructor(props) {
        super(props);
        this.state = {
            changeBmValue: '',
            treeData: [],
            value: '',
            companyAnddepartment: '',
            oneItemLayout: {
                labelCol: {
                    sm: { span: 2 }
                },
                wrapperCol: {
                    sm: { span: 22 }
                }
            },
            oneFormItemWrapperStyle: -5,
            fourItemSpan: 6,
            fourItemLayout: {
                labelCol: {
                    sm: { span: 8 }
                },
                wrapperCol: {
                    sm: { span: 16 }
                }
            },
            fourItemLayout2: {
                labelCol: {
                    sm: { span: 12 }
                },
                wrapperCol: {
                    sm: { span: 12 }
                }
            },
            isNeedClassifyData: false,
            PoliticalOutlookVisible: false,
            zzmmAddOrUpdate: '',
            zzmmRowData: null,
            extensionId: null,
            politicalSelectCon: null
            // updateFetchConfigs: {
            //     heTongGuanLi: {
            //         tdEditFetchConfig: {
            //             apiName: "updateZjXmSalaryContractManagement",
            //         }
            //     },
            //     training: {
            //         tdEditFetchConfig: {
            //             apiName: "updateZjXmSalaryTrainingSituation",
            //         }
            //     },
            //     family: {
            //         tdEditFetchConfig: {
            //             apiName: "updateZjXmSalaryFamilyBackground",
            //         }
            //     },
            //     health: {
            //         tdEditFetchConfig: {
            //             apiName: "updateZjXmSalaryHealthCondition",
            //         }
            //     },
            //     certificate: {
            //         tdEditFetchConfig: {
            //             apiName: "updateZjXmSalaryCertificateManagement",
            //         }
            //     },
            //     countenance: {
            //         tdEditFetchConfig: {
            //             apiName: "updateZjXmSalaryTrainingSituation",
            //         }
            //     }
            // }
        }
    }

    setFieldlabelFunc = async (obj, name) => {
        const rowData = await obj.qnnTableInstance.getEditedRowData()
        await obj.qnnTableInstance.setEditedRowData({ ...rowData, [name]: obj.itemData.itemName })
    }

    componentDidMount() {
        this.refresh('0');
    }
    refresh(departmentParentId, key) {
        let tableData = null
        this.props.myFetch('getSysDepartmentCurrentTree', { departmentParentId }).then(
            ({ data, success, message }) => {
                if (departmentParentId !== '0') {
                    var loopFn = function (dataList) {
                        for (var i = 0; i < dataList.length; i++) {
                            if (dataList[i].value === key) {
                                dataList[i].children = data;
                            } else if (dataList[i].children) {
                                dataList[i].children = loopFn(dataList[i].children)
                            }
                        }
                        return dataList;
                    }

                    tableData = loopFn(this.state.treeData);
                } else {
                    tableData = data
                }

                if (success) {
                    this.setState({
                        treeData: tableData
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }

    currentExtensionId = ''
    tabsKey = '1'
    batchSaveFunc = async (index) => {
        const fetchMap = {
            jcxx: { // ????????????
                apiName: '',
                paramsTiele: ''
            },
            xlqk: { // ????????????
                apiName: 'batchAddZjXmSalaryEducationBackground',
                paramsTiele: 'educationList'
            },
            gzll: { // ????????????
                apiName: 'batchAddZjXmSalaryWorkExperience',
                paramsTiele: 'workingExperienceList'
            },
            zyjs: { // ????????????
                apiName: 'batchAddZjXmSalaryProfessionalTechnology',
                paramsTiele: 'professionalTechnologyList'
            },
            htgl: { // ????????????
                apiName: 'batchAddZjXmSalaryContractManagement',
                paramsTiele: 'contractManagementList'
            },
            pxqk: { // ????????????
                apiName: 'batchAddZjXmSalaryTrainingSituation',
                paramsTiele: 'trainingSituationList'
            },
            jtzk: { // ????????????
                apiName: 'batchAddZjXmSalaryFamilyBackground',
                paramsTiele: 'familyList'
            },
            jkqk: { // ????????????
                apiName: 'batchAddZjXmSalaryHealthCondition',
                paramsTiele: 'healthConditionList'
            },
            zsgl: { // ????????????
                apiName: 'batchAddZjXmSalaryCertificateManagement',
                paramsTiele: 'certificateManagementList'
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
                        pKey = 'educationId'
                        break
                    case 'gzll':
                        pKey = 'experienceId'
                        break
                    case 'htgl':
                        pKey = 'contractId'
                        break
                    case 'pxqk':
                        pKey = 'trainingId'
                        break
                    case 'jtzk':
                        pKey = 'familyId'
                        break
                    case 'zsgl':
                        pKey = 'certificateId'
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

        const { extensionId } = await this.qnnTable.getDeawerValues()

        if (this[key].getTableData().length) {
            const { message, success } = await this.props.myFetch(fetchMap[key]['apiName'],
                {
                    extensionId,
                    [fetchMap[key]['paramsTiele']]: this[key].getTableData().map(item => {
                        return {
                            ...item,
                            extensionId,
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
            if (item.extensionId) {
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
        const { oneItemLayout, oneFormItemWrapperStyle, fourItemSpan, fourItemLayout, fourItemLayout2, } = this.state;
        const orgId = this.apih5.getOrgId()
        return (
            <div className={s.page}>
                <div className={s.rootl}
                    ref={(me) => {
                        if (me) {
                            this.leftDom = me;
                        }
                    }}>
                    <div
                        className={s.hr}
                        ref={(me) => {
                            if (me) {
                                let _this = this;
                                function moveFn(e) {
                                    let conDomLeft = document.getElementsByClassName('ant-layout-content')[0].offsetLeft;
                                    _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                }
                                me.addEventListener('mousedown', (e) => {
                                    this.onDragStartPos = e.pageX;
                                    document.addEventListener('mousemove', moveFn, false)
                                }, false);
                                document.addEventListener('mouseup', (e) => {
                                    document.removeEventListener('mousemove', moveFn, false)
                                }, false)
                            }
                        }}
                    ></div>
                    {this.state.treeData.length ? <Tree
                        selectText={false}
                        modalType="common" //common  drawer  ?????????????????????????????????
                        visible
                        selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        btnShow={false} //????????????????????????
                        disabled={true}
                        draggable={false}
                        nodeRender={nodeData => {
                            return (
                                <span>
                                    {nodeData["label"]}
                                </span>
                            );
                        }}
                        treeProps={{
                            showLine: true
                        }}
                        rightMenuOption={[]}
                        nodeClick={(node, selectedKeys) => {
                            this.refresh(node.value, selectedKeys[0])
                            this.setState({
                                value: ''
                            }, async () => {

                                const { data } = await this.props.myFetch('getSysComDeptProById', { departmentId: node.value })

                                this.setState({
                                    value: node,
                                    companyAnddepartment: data
                                })
                            })
                        }}
                        data={this.state.treeData}
                        //???????????? ??????{value:value,label:label,children:children}
                        keys={{
                            label: "label",
                            value: "value",
                            children: "children"
                        }}
                    /> : null}
                </div>
                <div className={s.rootr}>
                    {Object.keys(this.state.value).length ? <div>
                        <Tabs defaultActiveKey={this.tabsKey} onChange={(key) => {
                            this.tabsKey = key
                            this.setState({
                                isNeedClassifyData: true
                            })
                        }}>
                            <TabPane tab="??????" key="1">
                            </TabPane>
                            <TabPane tab="??????" key="2">
                            </TabPane>
                        </Tabs>
                        <QnnTable
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            wrappedComponentRef={(me) => { this.qnnTable = me }}
                            antd={{
                                rowKey: "extensionId",
                                size: 'small'
                            }}
                            drawerConfig={{
                                width: "90%"
                            }}
                            fetchConfig={{
                                apiName: 'pcGetZjXmSalaryUserExtensionList',//??????function ???????????????string 
                                otherParams: () => {
                                    return {
                                        orgId: this.state.value.value,
                                        isQuit: this.tabsKey
                                    }
                                }
                            }}
                            method={{
                                renderFender: (data) => {
                                    return {
                                        "0": "???",
                                        "1": "???"
                                    }[data]
                                },
                                //????????????
                                pyjl: (obj) => {
                                    console.log('????????????', obj)

                                },
                                //????????????
                                jlsc: (obj) => {
                                    const { myPublic: { appInfo: { ureport } } } = this.props;
                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                    var URL = `${ureport}excel?_u=file:????????????.xml&access_token=${access_token}&extensionId=${obj.rowData.extensionId}`;
                                    obj.qnnTableInstance.tool.confirm({
                                        title: "???????????????????????????",
                                        onOk: () => {
                                            window.open(URL)
                                        },
                                        onCancel: () => { },

                                    })
                                },
                                tabItemDisabled: (obj) => {
                                    return !obj._formData()?.extensionId;
                                }
                            }}
                            // ?????????????????? true ????????????????????????????????????
                            isNeedClassifyData={this.state.isNeedClassifyData}
                            // ????????????????????????????????????????????????
                            isNeedClassifyDataCb={() => {
                                this.setState({
                                    isNeedClassifyData: false
                                })
                            }}

                            actionBtns={[
                                {
                                    name: "add", //??????add del
                                    icon: "plus", //icon
                                    type: "primary", //??????  ?????? primary  [primary dashed danger]
                                    label: "??????",
                                    hide: () => {
                                        return this.tabsKey === '2'
                                    },
                                    formBtns: [
                                        {
                                            name: "cancel", //??????????????????
                                            type: "dashed", //??????  ?????? primary
                                            label: "??????"
                                        },

                                        {
                                            field: "submit",
                                            name: "diySubmit",
                                            type: "primary",
                                            label: "??????",
                                            hide: (obj) => {
                                                let index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "10") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            onClick: async (obj) => {
                                                if (obj.qnnTableInstance.getTabsIndex() === '0') {
                                                    let params = null
                                                    const formData = await obj.qnnTableInstance.getDeawerValues()
                                                    const resData = await this.props.myFetch('getBaseCodeTree', { itemId: 'gangWeiGuanLi' })
                                                    if (resData.success) {
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
                                                        params = {
                                                            ...obj._formData,
                                                            nativePlace: obj._formData.nativePlace.toString(),
                                                            legalAddress: obj._formData.legalAddress.toString(),
                                                            position: obj._formData.position.toString(),
                                                            residenceAddress: obj._formData.residenceAddress.toString(),
                                                            presentAddress: obj._formData.presentAddress.toString(),
                                                            salaryInfo: {
                                                                ...obj._formData.salaryInfo,
                                                                levelSalaryId: obj._formData.salaryInfo.levelSalaryId.toString(),
                                                            },
                                                            positionName: arrList.join('/'),
                                                            workPersonnelFlag: 1
                                                        }
                                                    } else {
                                                        params = {
                                                            ...obj._formData,
                                                            nativePlace: obj._formData.nativePlace.toString(),
                                                            legalAddress: obj._formData.legalAddress.toString(),
                                                            position: obj._formData.position.toString(),
                                                            residenceAddress: obj._formData.residenceAddress.toString(),
                                                            presentAddress: obj._formData.presentAddress.toString(),
                                                            salaryInfo: {
                                                                ...obj._formData.salaryInfo,
                                                                levelSalaryId: obj._formData.salaryInfo.levelSalaryId.toString(),
                                                            },
                                                            workPersonnelFlag: 1
                                                        }
                                                    }

                                                    const { data, success, message } = await this.props.myFetch(params.extensionId ? 'updateZjXmSalaryUserExtension' : 'addZjXmSalaryUserExtension', params)
                                                    if (success) {
                                                        Msg.success('????????????!')
                                                        this.qnnTable.getQnnForm().setValues({ ...data })
                                                        this.qnnTable.getQnnForm().tabsBarForceUpdate()
                                                        this.qnnTable.getQnnForm().scrollYToStart()
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                } else {
                                                    this.batchSaveFunc(Number(obj.qnnTableInstance.getTabsIndex()))
                                                }

                                            },
                                            fetchConfig: {
                                                apiName: 'addZjXmSalaryUserExtension',
                                                params: {},
                                                otherParams: async (obj) => {
                                                    const formData = await obj.qnnTableInstance.getDeawerValues()
                                                    const resData = await this.props.myFetch('getBaseCodeTree', { itemId: 'gangWeiGuanLi' })
                                                    if (resData.success) {
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
                                                        return {
                                                            positionName: arrList.join('/'),
                                                            workPersonnelFlag: 1
                                                        }
                                                    } else {
                                                        return {
                                                            workPersonnelFlag: 1
                                                        }
                                                    }

                                                },
                                            },
                                        }
                                    ]
                                },
                                // {
                                //     field: 'submitsp',
                                //     name: 'del',
                                //     fetchConfig: {//ajax??????
                                //         apiName: '????????????',
                                //     },
                                //     type: 'primary',
                                //     label: '????????????',
                                //     hide: () => {
                                //         return this.tabsKey === '2'
                                //     },
                                // },
                                {
                                    field: 'submitsczh',
                                    name: 'del',
                                    fetchConfig: {//ajax??????
                                        apiName: '????????????',
                                    },
                                    type: 'primary',
                                    label: '????????????',
                                    hide: () => {
                                        return this.tabsKey === '2'
                                    },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax??????
                                        apiName: 'batchDeleteUpdateZjXmSalaryUserExtension',
                                    },
                                    type: 'danger',
                                    label: '??????',
                                    hide: () => {
                                        return this.tabsKey === '2'
                                    },
                                },
                            ]}

                            onTabsChange={(tabKey, qnnFormInstance, arg) => {
                                // console.log('??????tab:', tabKey)
                            }}

                            tabs={[
                                {
                                    field: "????????????",
                                    name: "qnnForm",
                                    title: "????????????",
                                    content: {
                                        fetchConfig: {
                                            apiName: 'pcGetZjXmSalaryUserExtensionDetails',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                            otherParams: {},
                                        },
                                        formConfig: [
                                            // zjXmSalaryPoliticalId
                                            {
                                                type: 'string',
                                                label: 'zjXmSalaryPoliticalId',
                                                field: 'zjXmSalaryPoliticalId',
                                                hide: true
                                            },
                                            {
                                                type: 'string',
                                                label: 'extensionId',
                                                field: 'extensionId',
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
                                                    right: "60px"
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
                                                type: 'cascader',
                                                label: '????????????',
                                                field: 'positionReserve',
                                                // required: true,
                                                span: 12,
                                                fetchConfig: {
                                                    apiName: "getBaseCodeTree",
                                                    otherParams: {
                                                        itemId: 'gangWeiGuanLi'
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
                                                initialValue: [{
                                                    label: this.state.value.label,
                                                    title: this.state.value.label,
                                                    type: this.state.value.type,
                                                    userDeptId: this.state.value.userDeptId,
                                                    value: this.state.value.value,
                                                    valuePid: this.state.value.valuePid,
                                                }],
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
                                            rowKey: "educationId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryEducationBackgroundList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            rowKey: "experienceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryWorkExperienceList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            rowKey: "technologyId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryProfessionalTechnologyList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            rowKey: "contractId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryContractManagementList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            rowKey: "trainingId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryTrainingSituationList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            rowKey: "familyId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryFamilyBackgroundList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            apiName: 'getZjXmSalaryHealthConditionList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            rowKey: "certificateId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryCertificateManagementList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                            rowKey: "zjXmSalaryPoliticalId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryPoliticalList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId"
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
                                                        extensionId: (await this.qnnTable.getDeawerValues()).extensionId,
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
                                                onClick: async (obj) => {
                                                    const basicInfo = obj.props.parentTableInfo.rowData
                                                    console.log(basicInfo, await this.qnnTable.getDeawerValues())

                                                    this.setState({
                                                        PoliticalOutlookVisible: true,
                                                        zzmmAddOrUpdate: 'update',
                                                        extensionId: basicInfo.extensionId,
                                                        politicalPK: obj.selectedRows[0].zjXmSalaryPoliticalId
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
                                {
                                    field: "??????????????????",
                                    title: "??????????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId",
                                            },
                                            otherParams: {
                                                approvalFlag: 'user'
                                            }
                                        },
                                        isShowRowSelect: false,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'zjXmSalaryLevelSalaryLogId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'extensionId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'positionName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'projectName',
                                                    key: 'projectName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'officeName',
                                                    key: 'officeName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'departmentName',
                                                    key: 'departmentName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            // ??????????????????
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'positionNameAfter',
                                                    key: 'positionNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'projectNameAfter',
                                                    key: 'projectNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'officeNameAfter',
                                                    key: 'officeNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'departmentNameAfter',
                                                    key: 'departmentNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'changeType',
                                                    key: 'changeType',
                                                    type: 'select'
                                                },
                                                form: {
                                                    type: 'select',
                                                    label: '????????????',
                                                    field: 'changeType',
                                                    placeholder: '?????????',
                                                    optionData: [
                                                        {
                                                            label: "????????????",
                                                            value: "1"
                                                        },
                                                        {
                                                            label: "????????????",
                                                            value: "2"
                                                        }
                                                    ],
                                                }
                                            },
                                            // {
                                            //     table: {
                                            //         title: '????????????',
                                            //         dataIndex: 'dgzt',
                                            //         key: 'dgzt',
                                            //     },
                                            //     isInForm: false
                                            // },
                                        ]
                                    }
                                },
                                {
                                    field: "????????????",
                                    title: "????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId",
                                            },
                                            otherParams: {
                                                approvalFlag: 'salary'
                                            }
                                        },
                                        isShowRowSelect: false,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'zjXmSalaryLevelSalaryLogId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'extensionId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'levelSalaryId',
                                                    fixed: 'left',
                                                    type: 'cascader',
                                                    fieldConfig: {
                                                        type: 'cascader',
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
                                                        },
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '?????????????????????',
                                                    dataIndex: 'levelSalaryIdAfter',
                                                    key: 'levelSalaryIdAfter',
                                                    fixed: 'left',
                                                    type: 'cascader',
                                                    fieldConfig: {
                                                        type: 'cascader',
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
                                                                "positionSalaryRecommend": "value",
                                                                "salaryRecommendId": "value"
                                                            }

                                                        },
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '?????????',
                                                    dataIndex: 'positionSalary',
                                                    key: 'positionSalary',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'positionSalaryAfter',
                                                    key: 'positionSalaryAfter',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "??????????????????",
                                    title: "??????????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId",
                                            },
                                            otherParams: {
                                                approvalFlag: 'report'
                                            }
                                        },
                                        isShowRowSelect: false,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'zjXmSalaryLevelSalaryLogId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'extensionId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '?????????',
                                                    dataIndex: 'positionName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'positionNameAfter',
                                                    key: 'positionNameAfter',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'levelSalaryId',
                                                    key: 'levelSalaryId',
                                                    fixed: 'left',
                                                    type: 'cascader',
                                                    fieldConfig: {
                                                        type: 'cascader',
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
                                                        },
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '?????????????????????',
                                                    dataIndex: 'levelSalaryIdAfter',
                                                    key: 'levelSalaryIdAfter',
                                                    fixed: 'left',
                                                    type: 'cascader',
                                                    fieldConfig: {
                                                        type: 'cascader',
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
                                                        },
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '?????????',
                                                    dataIndex: 'positionSalary',
                                                    key: 'positionSalary',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'positionSalaryAfter',
                                                    key: 'positionSalaryAfter',
                                                },
                                                isInForm: false
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "??????????????????",
                                    title: "??????????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId",
                                            },
                                            otherParams: {
                                                approvalFlag: 'userPosition'
                                            }
                                        },
                                        isShowRowSelect: false,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'zjXmSalaryLevelSalaryLogId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'extensionId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '?????????',
                                                    dataIndex: 'positionName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '????????????',
                                                    dataIndex: 'positionNameAfter',
                                                    key: 'positionNameAfter',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "??????????????????",
                                    title: "??????????????????",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//??????function ???????????????string
                                            params: {
                                                extensionId: "extensionId",
                                            },
                                            otherParams: {
                                                approvalFlag: 'reserveCadre'
                                            }
                                        },
                                        isShowRowSelect: false,
                                        formConfig: [
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'zjXmSalaryLevelSalaryLogId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                isInTable: false,
                                                form: {
                                                    type: 'string',
                                                    field: 'extensionId',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '???????????????',
                                                    dataIndex: 'positionReserve',
                                                    fixed: 'left',
                                                    type: 'cascader',
                                                    fieldConfig: {
                                                        type: 'cascader',
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeTree",
                                                            otherParams: {
                                                                itemId: 'gangWeiGuanLi'
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
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '??????????????????',
                                                    dataIndex: 'positionReserveAfter',
                                                    key: 'positionReserveAfter',
                                                    fixed: 'left',
                                                    type: 'cascader',
                                                    fieldConfig: {
                                                        type: 'cascader',
                                                        fetchConfig: {
                                                            apiName: "getBaseCodeTree",
                                                            otherParams: {
                                                                itemId: 'gangWeiGuanLi'
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
                                                    }
                                                },
                                                isInForm: false
                                            },
                                           
                                        ]
                                    }
                                },
                            ]}
                            formConfig={[

                                {
                                    // isInForm: false,
                                    isInSearch: false,
                                    table: {
                                        title: '??????',
                                        dataIndex: 'realName',
                                        fixed: "left",
                                        onClick: "detail",
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: false,
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '??????',
                                        dataIndex: 'gender',
                                        render: "bind:renderFender",
                                        fixed: "left"
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: false,
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'idNumber',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: false,
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '?????????',
                                        dataIndex: 'phoneNumber',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: false,
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '??????',
                                //         dataIndex: 'appliedPosition',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '????????????',
                                //         dataIndex: 'levelId',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '??????',
                                //         dataIndex: 'salaryId',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                {
                                    isInForm: false,
                                    table: {
                                        // width:120,
                                        title: '????????????',
                                        dataIndex: 'departmentName',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: false,
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         width: 140,
                                //         title: '????????????????????????',
                                //         dataIndex: 'wageOfProjectId',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                {
                                    isInForm: false,
                                    table: {
                                        // width:120,
                                        title: '????????????',
                                        dataIndex: 'officeName',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        required: false,
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         // width:120,
                                //         title: '??????/??????',
                                //         dataIndex: 'isQuit',
                                //         type: 'select'
                                //     }
                                //     , form:
                                //     {
                                //         type: 'select',
                                //         label: 'isQuit',
                                //         field: '??????/??????',
                                //         placeholder: '?????????',
                                //         optionData: [
                                //             {
                                //                 label: '??????',
                                //                 value: '1'
                                //             },
                                //             {
                                //                 label: '??????',
                                //                 value: '2'
                                //             },
                                //         ],

                                //     },

                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '????????????',
                                //         dataIndex: 'hiredate',
                                //         format: "YYYY-MM-DD",
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '????????????',
                                //         dataIndex: 'userStatus',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         width: 120,
                                //         title: '??????????????????',
                                //         dataIndex: '??????????????????',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '?????????',
                                //         dataIndex: 'createUser',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '????????????',
                                //         dataIndex: 'createTime',
                                //         format: "YYYY-MM-DD",
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '?????????',
                                //         dataIndex: 'modifyUser',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '????????????',
                                //         dataIndex: 'modifyTime',
                                //         format: "YYYY-MM-DD",
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '?????????',
                                //         required: false,
                                //     }
                                // },
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
                                                name: "editDiy", // ??????name??????add,  del, edit, detail, Component, form???
                                                label: "??????",
                                                hide: (obj) => {
                                                    return obj.rowData.isQuit === '2'
                                                },
                                                onClick: async (obj) => {
                                                    const { data } = await this.props.myFetch('checkUserStatus', { extensionId: obj.rowData.extensionId })
                                                    if (data === 'true') {
                                                        obj.btnCallbackFn.btnAction({
                                                            btnConfig: {
                                                                name: "edit",
                                                                drawerTitle: "",
                                                                formBtns: [
                                                                    {
                                                                        name: "cancel", //??????????????????
                                                                        type: "dashed", //??????  ?????? primary
                                                                        label: "??????"
                                                                    },
                                                                    {
                                                                        name: "submitDiy", //??????add del
                                                                        type: "primary", //??????  ?????? primary
                                                                        label: "??????", //????????????????????????????????????  
                                                                        hide: (obj) => {
                                                                            let index = obj.btnCallbackFn.getActiveKey();

                                                                            if (index === "9") {
                                                                                return true;
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        },
                                                                        onClick: async (obj) => {
                                                                            const index = Number(obj.qnnTableInstance.getTabsIndex())
                                                                            let params = {}
                                                                            if (index === 0) {
                                                                                const formData = obj._formData
                                                                                const resData = await this.props.myFetch('getBaseCodeTree', { itemId: 'gangWeiGuanLi' })
                                                                                if (resData.success) {
                                                                                    let arrList = []
                                                                                    let TemporaryData = resData.data
                                                                                    const positionArr = formData.position

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
                                                                                    params = {
                                                                                        ...obj.curFormData,
                                                                                        positionName: arrList.join('/'),
                                                                                        // workPersonnelFlag: 1
                                                                                    }
                                                                                } else {
                                                                                    params = {
                                                                                        ...obj.curFormData,
                                                                                        // workPersonnelFlag: 1
                                                                                    }
                                                                                }

                                                                                this.props.myFetch('updateZjXmSalaryUserExtension', params).then(({ data, success, message }) => {
                                                                                    if (success) {
                                                                                        Msg.success('????????????')
                                                                                    } else {
                                                                                        Msg.error(message)
                                                                                    }
                                                                                })
                                                                            } else {
                                                                                this.batchSaveFunc(index)
                                                                            }
                                                                        },
                                                                        // fetchConfig: {
                                                                        //     apiName: "updateZjXmSalaryUserExtension",
                                                                        //     delParams: [],
                                                                        // }
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
                                                        Msg.warning('??????????????????,????????????!')
                                                    }
                                                },

                                            },
                                            {
                                                name: 'diy',//??????add del
                                                label: "??????",
                                                btns: [
                                                    {
                                                        name: "jlsc", //??????????????????
                                                        label: "????????????",
                                                        onClick: "bind:jlsc"
                                                    },
                                                    // {
                                                    //     name: "pyjl", //??????????????????
                                                    //     label: "????????????",
                                                    //     onClick: "bind:pyjl"
                                                    // }
                                                ]
                                            },
                                        ]
                                    }
                                }


                            ]}
                        /> </div> : <div className={s.alert}>????????????????????????????????????</div>}
                </div>
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
                    basicInfo={this.qnnTable?.getQnnForm()}
                    id={this.state.extensionId}
                    isPerson={true}
                    pk={this.state.politicalPK} />
            </div>)
    }
}
export default Page;