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
            jcxx: { // 基础信息
                apiName: '',
                paramsTiele: ''
            },
            xlqk: { // 学历情况
                apiName: 'batchAddZjXmSalaryEducationBackground',
                paramsTiele: 'educationList'
            },
            gzll: { // 工作履历
                apiName: 'batchAddZjXmSalaryWorkExperience',
                paramsTiele: 'workingExperienceList'
            },
            zyjs: { // 专业技术
                apiName: 'batchAddZjXmSalaryProfessionalTechnology',
                paramsTiele: 'professionalTechnologyList'
            },
            htgl: { // 合同管理
                apiName: 'batchAddZjXmSalaryContractManagement',
                paramsTiele: 'contractManagementList'
            },
            pxqk: { // 培训情况
                apiName: 'batchAddZjXmSalaryTrainingSituation',
                paramsTiele: 'trainingSituationList'
            },
            jtzk: { // 家庭状况
                apiName: 'batchAddZjXmSalaryFamilyBackground',
                paramsTiele: 'familyList'
            },
            jkqk: { // 健康情况
                apiName: 'batchAddZjXmSalaryHealthCondition',
                paramsTiele: 'healthConditionList'
            },
            zsgl: { // 证书管理
                apiName: 'batchAddZjXmSalaryCertificateManagement',
                paramsTiele: 'certificateManagementList'
            },
            zzmm: { //政治面貌
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
            字段名       enrollmentDateGraduateDate           startDateEndDate     startDateEndDate      startDateEndDate     startDateEndDate
            中文名               学历情况                          工作履历             合同管理              培训情况              证书管理  
            key                   xlqk                              gzll                htgl                  pxqk                 zsgl
            主键               educationId                      experienceId         contractId            trainingId           certificateId
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
                        item.setErrorAlert('请填写正确的手机号！')
                    }
                    return true
                })
            } else {
                tdRefsMap[key === 'xlqk' ? 'enrollmentDateGraduateDate' : 'startDateEndDate'].map(item => {
                    if (!item.getTdData()) {
                        IsVerificationSuccess = false
                        item.setErrorAlert('不能为空')
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
                Msg.success('保存成功')
            } else {
                Msg.error(message)
            }
        } else {
            Msg.warning('请填写内容再点击保存!')
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
                title: '温馨提示',
                content: '删除后不可恢复,确定要删除吗?',
                onOk: async () => {
                    const { success, message } = await this.props.myFetch(url, params)
                    if (success) {
                        refresh()
                        clearSelectedRows()
                        Msg.success('删除成功！')
                    } else {
                        Msg.error(message)
                    }
                }
            })
        } else {
            refresh()
            clearSelectedRows()
            Msg.success('删除成功！')
        }
    }

    highestDegreeOnly = (obj, row, key) => {
        const tableData = obj.btnCallbackFn.getTableData()
        let newTableData = []

        // 遍历table的数据
        tableData.map((item, index) => {
            if (item.educationId === row.rowData.educationId) {
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
                        modalType="common" //common  drawer  抽屉出现方式或者普通的
                        visible
                        selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        btnShow={false} //是否显示底部按钮
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
                        //键值配置 默认{value:value,label:label,children:children}
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
                            <TabPane tab="在职" key="1">
                            </TabPane>
                            <TabPane tab="离职" key="2">
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
                                apiName: 'pcGetZjXmSalaryUserExtensionList',//可为function 返回必须为string 
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
                                        "0": "男",
                                        "1": "女"
                                    }[data]
                                },
                                //聘用记录
                                pyjl: (obj) => {
                                    console.log('聘用记录', obj)

                                },
                                //简历生成
                                jlsc: (obj) => {
                                    const { myPublic: { appInfo: { ureport } } } = this.props;
                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                    var URL = `${ureport}excel?_u=file:简历模板.xml&access_token=${access_token}&extensionId=${obj.rowData.extensionId}`;
                                    obj.qnnTableInstance.tool.confirm({
                                        title: "确认要生成简历吗？",
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
                            // 组件接收到为 true 的值时，将会完全重新渲染
                            isNeedClassifyData={this.state.isNeedClassifyData}
                            // 组件内部渲染完毕后会调用这个回调
                            isNeedClassifyDataCb={() => {
                                this.setState({
                                    isNeedClassifyData: false
                                })
                            }}

                            actionBtns={[
                                {
                                    name: "add", //内置add del
                                    icon: "plus", //icon
                                    type: "primary", //类型  默认 primary  [primary dashed danger]
                                    label: "新增",
                                    hide: () => {
                                        return this.tabsKey === '2'
                                    },
                                    formBtns: [
                                        {
                                            name: "cancel", //关闭右边抽屉
                                            type: "dashed", //类型  默认 primary
                                            label: "取消"
                                        },

                                        {
                                            field: "submit",
                                            name: "diySubmit",
                                            type: "primary",
                                            label: "提交",
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
                                                        Msg.success('保存成功!')
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
                                //     fetchConfig: {//ajax配置
                                //         apiName: '提交审批',
                                //     },
                                //     type: 'primary',
                                //     label: '提交审批',
                                //     hide: () => {
                                //         return this.tabsKey === '2'
                                //     },
                                // },
                                {
                                    field: 'submitsczh',
                                    name: 'del',
                                    fetchConfig: {//ajax配置
                                        apiName: '生成账号',
                                    },
                                    type: 'primary',
                                    label: '生成账号',
                                    hide: () => {
                                        return this.tabsKey === '2'
                                    },
                                },
                                {
                                    field: 'delBtn',
                                    name: 'del',
                                    icon: 'delete',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateZjXmSalaryUserExtension',
                                    },
                                    type: 'danger',
                                    label: '删除',
                                    hide: () => {
                                        return this.tabsKey === '2'
                                    },
                                },
                            ]}

                            onTabsChange={(tabKey, qnnFormInstance, arg) => {
                                // console.log('当前tab:', tabKey)
                            }}

                            tabs={[
                                {
                                    field: "基础信息",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    content: {
                                        fetchConfig: {
                                            apiName: 'pcGetZjXmSalaryUserExtensionDetails',//可为function 返回必须为string
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
                                                    children: 'children',
                                                    linkageFields: {
                                                        "nativePlaceName": "itemName"
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
                                                        "politicCountenanceName": "itemName"
                                                    }
                                                },
                                                initialValue: "13",
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
                                                initialValue: '群众',
                                                hide: true
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
                                                field: "idNumber", //表格里面的字段 
                                                label: "证件号",
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
                                                required: false,
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
                                                        "userTypeName": 'itemName',
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
                                                type: 'cascader',
                                                label: '后备岗位',
                                                field: 'positionReserve',
                                                // required: true,
                                                span: 12,
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
                                                disabled: true,

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
                                                label: "单位/项目",
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
                                                        //配置后将会去请求下拉选项数据
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
                                                label: "所属科室",
                                                type: "treeSelect",
                                                required: true,
                                                span: 8,
                                                formItemLayout: fourItemLayout,
                                                treeSelectOption: {
                                                    selectType: "1",
                                                    maxNumber: 1,
                                                    fetchConfig: {
                                                        //配置后将会去请求下拉选项数据
                                                        apiName: "getSysDepartmentCurrentTree",
                                                        paramsKey: "departmentParentId",
                                                    },
                                                },
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
                                                                itemId: 'tiJianLeiXing'
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

                                        ]
                                    }
                                },
                                {
                                    field: "学历情况",
                                    name: "qnnTable",
                                    title: "学历情况",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.xlqk = me },
                                        antd: {
                                            rowKey: "educationId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryEducationBackgroundList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
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
                                                label: '删除',
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
                                                    title: '入学时间 - 毕/肆业时间',
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
                                            //         title: '毕/肆业时间',
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
                                                    title: '学历',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'itemName', //默认 label
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
                                                    title: '学位',
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
                                                    title: '专业',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'itemName', //默认 label
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
                                                    title: '学位授予时间',
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
                                                    title: '是否第一学历',
                                                    dataIndex: 'isFirstEducation',
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryEducationBackground"
                                                    // },
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
                                                            this.highestDegreeOnly(obj, row, 'isFirstEducation')
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '是否最高学历',
                                                    dataIndex: 'isHighestEducation',
                                                    tdEdit: true,
                                                    // tdEditFetchConfig: {
                                                    //     apiName: "updateZjXmSalaryEducationBackground"
                                                    // },
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
                                                        onChange: (val, obj, row) => {
                                                            this.highestDegreeOnly(obj, row, 'isHighestEducation')
                                                        }
                                                    },

                                                }
                                            },
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '附件',
                                                    dataIndex: 'fileEducationList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //配置后将会去请求下拉选项数据
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
                                    field: "工作履历",
                                    name: "qnnTable",
                                    title: "工作履历",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gzll = me },
                                        antd: {
                                            rowKey: "experienceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryWorkExperienceList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //内置add del
                                                icon: "plus", //icon
                                                type: "primary", //类型  默认 primary  [primary dashed danger]
                                                label: "新增",
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
                                                fetchConfig: {//ajax配置
                                                    apiName: 'batchDeleteUpdateZjXmSalaryWorkExperience',
                                                },
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryWorkExperience')
                                                },
                                                type: 'danger',
                                                label: '删除',
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
                                                    title: '起始日期 - 截止日期',
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
                                            //         title: '截止日期',
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
                                                    title: '所在部门',
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
                                                    title: '岗位',
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
                                                    title: '主要工作内容',
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
                                                    title: '证明人',
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
                                                    title: '联系电话',
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
                                                    title: '主、兼职',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'itemName', //默认 label
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
                                    field: "专业技术",
                                    title: "专业技术",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.zyjs = me },
                                        antd: {
                                            rowKey: "technologyId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryProfessionalTechnologyList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addRow", //内置add del
                                                icon: "plus", //icon
                                                type: "primary", //类型  默认 primary  [primary dashed danger]
                                                label: "新增",
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
                                                label: '删除',
                                            },
                                        ],
                                        formConfig: [

                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '职称名称',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'itemName', //默认 label
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
                                                    title: '职称专业',
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
                                                    title: '职称级别',
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
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //配置后将会去请求下拉选项数据
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
                                    field: "合同管理",
                                    title: "合同管理",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.htgl = me },
                                        antd: {
                                            rowKey: "contractId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryContractManagementList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
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
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryContractManagement')
                                                },
                                                type: 'danger',
                                                label: '删除',
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
                                            //         title: '劳动合同期限·止',
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
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //配置后将会去请求下拉选项数据
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
                                                    title: '合同附件',
                                                    dataIndex: 'fileContractList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //配置后将会去请求下拉选项数据
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
                                    field: "培训情况",
                                    title: "培训情况",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.pxqk = me },
                                        antd: {
                                            rowKey: "trainingId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryTrainingSituationList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
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
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryTrainingSituation')
                                                },
                                                type: 'danger',
                                                label: '删除',
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
                                                    title: '起始日期 - 截止日期',
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
                                            //         title: '截止日期',
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
                                                    dataIndex: 'fileList',
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //配置后将会去请求下拉选项数据
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
                                    field: "家庭状况",
                                    title: "家庭状况",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.jtzk = me },
                                        antd: {
                                            rowKey: "familyId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryFamilyBackgroundList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
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
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryFamilyBackground')
                                                },
                                                type: 'danger',
                                                label: '删除',
                                            },
                                        ],
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '与本人关系',
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


                                        ]
                                    }
                                },
                                {
                                    field: "健康情况",
                                    title: "健康情况",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.jkqk = me },
                                        antd: {
                                            rowKey: "healthId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryHealthConditionList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
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
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryHealthCondition')
                                                },
                                                type: 'danger',
                                                label: '删除',
                                            },
                                        ],
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    title: '体检类型',
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
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //配置后将会去请求下拉选项数据
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
                                    field: "证书管理",
                                    title: "证书管理",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.zsgl = me },
                                        antd: {
                                            rowKey: "certificateId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryCertificateManagementList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
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
                                                disabled: "bind:_actionBtnNoSelected",
                                                onClick: (obj) => {
                                                    this.filterDelDataFunc(obj.selectedRows, obj.btnCallbackFn.refresh, obj.btnCallbackFn.clearSelectedRows, 'batchDeleteUpdateZjXmSalaryCertificateManagement')
                                                },
                                                type: 'danger',
                                                label: '删除',
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
                                                    title: '证书类别',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'itemName', //默认 label
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
                                                    title: '已发放年度',
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
                                                    title: '发放开始时间 - 发放截止时间',
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
                                            //         title: '发放截止时间',
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
                                                    tdEdit: true,
                                                    fieldConfig: {
                                                        type: "files",
                                                        max: 1,
                                                        fetchConfig: {
                                                            //配置后将会去请求下拉选项数据
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
                                    field: "政治面貌",
                                    title: "政治面貌",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.zzmm = me },
                                        antd: {
                                            rowKey: "zjXmSalaryPoliticalId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryPoliticalList',//可为function 返回必须为string
                                            params: {
                                                extensionId: "extensionId"
                                            },
                                        },
                                        pagination: false,
                                        actionBtns: [
                                            {
                                                name: "addDiy", //内置add del
                                                icon: "plus", //icon
                                                type: "primary", //类型  默认 primary  [primary dashed danger]
                                                label: "新增",
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
                                                fetchConfig: {//ajax配置
                                                    apiName: 'batchDeleteUpdateZjXmSalaryPolitical',
                                                },
                                                type: 'danger',
                                                label: '删除',
                                            },
                                        ],
                                        formConfig: [
                                            {
                                                isInForm: false,
                                                table: {
                                                    fixed: "left",
                                                    title: '政治面貌',
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
                                    field: "人员异动记录",
                                    title: "人员异动记录",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//可为function 返回必须为string
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
                                                    title: '岗位',
                                                    dataIndex: 'positionName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '所属项目',
                                                    dataIndex: 'projectName',
                                                    key: 'projectName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '所属科室',
                                                    dataIndex: 'officeName',
                                                    key: 'officeName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '所属部门',
                                                    dataIndex: 'departmentName',
                                                    key: 'departmentName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            // ↑↑↑↑↑↑
                                            {
                                                table: {
                                                    title: '异动岗位',
                                                    dataIndex: 'positionNameAfter',
                                                    key: 'positionNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '异动项目',
                                                    dataIndex: 'projectNameAfter',
                                                    key: 'projectNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '异动科室',
                                                    dataIndex: 'officeNameAfter',
                                                    key: 'officeNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '异动部门',
                                                    dataIndex: 'departmentNameAfter',
                                                    key: 'departmentNameAfter',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '异动类型',
                                                    dataIndex: 'changeType',
                                                    key: 'changeType',
                                                    type: 'select'
                                                },
                                                form: {
                                                    type: 'select',
                                                    label: '异动类型',
                                                    field: 'changeType',
                                                    placeholder: '请选择',
                                                    optionData: [
                                                        {
                                                            label: "正常异动",
                                                            value: "1"
                                                        },
                                                        {
                                                            label: "离职异动",
                                                            value: "2"
                                                        }
                                                    ],
                                                }
                                            },
                                            // {
                                            //     table: {
                                            //         title: '到岗状态',
                                            //         dataIndex: 'dgzt',
                                            //         key: 'dgzt',
                                            //     },
                                            //     isInForm: false
                                            // },
                                        ]
                                    }
                                },
                                {
                                    field: "岗薪记录",
                                    title: "岗薪记录",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//可为function 返回必须为string
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
                                                    title: '原岗薪等级',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'label', //默认 label
                                                            value: 'value',//
                                                            children: 'showData',
                                                        },
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '变更后岗薪等级',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'label', //默认 label
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
                                                    title: '原岗薪',
                                                    dataIndex: 'positionSalary',
                                                    key: 'positionSalary',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '变更后岗薪',
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
                                    field: "报备报审记录",
                                    title: "报备报审记录",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//可为function 返回必须为string
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
                                                    title: '原岗位',
                                                    dataIndex: 'positionName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '变更岗位',
                                                    dataIndex: 'positionNameAfter',
                                                    key: 'positionNameAfter',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '原岗薪等级',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'label', //默认 label
                                                            value: 'value',//
                                                            children: 'showData',
                                                        },
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '变更后岗薪等级',
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
                                                        optionConfig: {//下拉选项配置
                                                            label: 'label', //默认 label
                                                            value: 'value',//
                                                            children: 'showData',
                                                        },
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '原岗薪',
                                                    dataIndex: 'positionSalary',
                                                    key: 'positionSalary',
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '变更后岗薪',
                                                    dataIndex: 'positionSalaryAfter',
                                                    key: 'positionSalaryAfter',
                                                },
                                                isInForm: false
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "员工转岗记录",
                                    title: "员工转岗记录",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//可为function 返回必须为string
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
                                                    title: '原岗位',
                                                    dataIndex: 'positionName',
                                                    fixed: 'left'
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '变更岗位',
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
                                    field: "后备干部记录",
                                    title: "后备干部记录",
                                    name: "qnnTable",
                                    disabled: "bind:tabItemDisabled",
                                    content: {
                                        wrappedComponentRef: (me) => { this.gxjl = me },
                                        antd: {
                                            rowKey: "countenanceId",
                                            size: "small"
                                        },
                                        fetchConfig: {
                                            apiName: 'getZjXmSalaryLevelSalaryLogList',//可为function 返回必须为string
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
                                                    title: '原后备岗位',
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
                                                    }
                                                },
                                                isInForm: false
                                            },
                                            {
                                                table: {
                                                    title: '变更后备岗位',
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
                                        title: '姓名',
                                        dataIndex: 'realName',
                                        fixed: "left",
                                        onClick: "detail",
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: false,
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '性别',
                                        dataIndex: 'gender',
                                        render: "bind:renderFender",
                                        fixed: "left"
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: false,
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '证件号码',
                                        dataIndex: 'idNumber',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: false,
                                    }
                                },
                                {
                                    isInForm: false,
                                    table: {
                                        title: '手机号',
                                        dataIndex: 'phoneNumber',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: false,
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '岗位',
                                //         dataIndex: 'appliedPosition',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '岗位等级',
                                //         dataIndex: 'levelId',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '岗薪',
                                //         dataIndex: 'salaryId',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                {
                                    isInForm: false,
                                    table: {
                                        // width:120,
                                        title: '所属机构',
                                        dataIndex: 'departmentName',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: false,
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         width: 140,
                                //         title: '工资关系所在项目',
                                //         dataIndex: 'wageOfProjectId',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                {
                                    isInForm: false,
                                    table: {
                                        // width:120,
                                        title: '所属科室',
                                        dataIndex: 'officeName',
                                    }
                                    , form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: false,
                                    }
                                },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         // width:120,
                                //         title: '在职/离职',
                                //         dataIndex: 'isQuit',
                                //         type: 'select'
                                //     }
                                //     , form:
                                //     {
                                //         type: 'select',
                                //         label: 'isQuit',
                                //         field: '在职/离职',
                                //         placeholder: '请选择',
                                //         optionData: [
                                //             {
                                //                 label: '在职',
                                //                 value: '1'
                                //             },
                                //             {
                                //                 label: '离职',
                                //                 value: '2'
                                //             },
                                //         ],

                                //     },

                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '入职日期',
                                //         dataIndex: 'hiredate',
                                //         format: "YYYY-MM-DD",
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '人员状态',
                                //         dataIndex: 'userStatus',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         width: 120,
                                //         title: '是否创建用户',
                                //         dataIndex: '是否创建用户',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '创建者',
                                //         dataIndex: 'createUser',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '创建时间',
                                //         dataIndex: 'createTime',
                                //         format: "YYYY-MM-DD",
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '修改者',
                                //         dataIndex: 'modifyUser',
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                // {
                                //     isInForm: false,
                                //     table: {
                                //         title: '修改时间',
                                //         dataIndex: 'modifyTime',
                                //         format: "YYYY-MM-DD",
                                //     }
                                //     ,form: {
                                //         type: 'string',
                                //         placeholder: '请输入',
                                //         required: false,
                                //     }
                                // },
                                {
                                    isInForm: false,
                                    table: {
                                        showType: "tile", //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                                        width: 110,
                                        title: "操作",
                                        key: "action", //操作列名称
                                        fixed: "right", //固定到右边
                                        align: "center",

                                        btns: [
                                            {
                                                name: "editDiy", // 内置name有【add,  del, edit, detail, Component, form】
                                                label: "修改",
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
                                                                        name: "cancel", //关闭右边抽屉
                                                                        type: "dashed", //类型  默认 primary
                                                                        label: "取消"
                                                                    },
                                                                    {
                                                                        name: "submitDiy", //内置add del
                                                                        type: "primary", //类型  默认 primary
                                                                        label: "保存", //提交数据并且关闭右边抽屉  
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
                                                                                        Msg.success('修改成功')
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
                                                        Msg.warning('数据已被使用,不可修改!')
                                                    }
                                                },

                                            },
                                            {
                                                name: 'diy',//内置add del
                                                label: "更多",
                                                btns: [
                                                    {
                                                        name: "jlsc", //关闭右边抽屉
                                                        label: "简历生成",
                                                        onClick: "bind:jlsc"
                                                    },
                                                    // {
                                                    //     name: "pyjl", //关闭右边抽屉
                                                    //     label: "聘用记录",
                                                    //     onClick: "bind:pyjl"
                                                    // }
                                                ]
                                            },
                                        ]
                                    }
                                }


                            ]}
                        /> </div> : <div className={s.alert}>点击左侧节点即可查看数据</div>}
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