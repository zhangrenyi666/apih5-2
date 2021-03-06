import React, { Component } from "react";
import QnnTable from "../../../modules/qnn-table";
import QnnForm from "../../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Spin, Modal, Input,  } from "antd";
import Tree from "../../../modules/tree";
import s from './style.less';
import { msg } from "qnn-apih5"

import tabOne from "../../../ContractManagement/ProjectConstructionContractManagement/config/tabOne"
import TabTwo from "../../../ContractManagement/ProjectConstructionContractManagement/config/ListSubcontracts"
import tabThree from "../../../ContractManagement/ProjectConstructionContractManagement/config/tabThree"
import tabFour from "../../../ContractManagement/ProjectConstructionContractManagement/config/tabFour"
import tabFive from "../../../ContractManagement/ProjectConstructionContractManagement/config/tabFive"
import TabSix from "../../../ContractManagement/ProjectConstructionContractManagement/config/tabSix"
import FenBaoYuYeZhu from "../../../ContractManagement/ProjectConstructionContractManagement/config//FenBaoYuYeZhu"
import finalStatement from "../../../ContractManagement/ProjectConstructionContractManagement/config/finalStatement"

const { Search } = Input;
const config = {
    antd: {
        // rowKey: function (row) {
        //     return row.id
        // },
        rowKey: "zxCtCpStatisticsFinId",
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false
};
const configItem = {
    antd: {
        rowKey: 'key',//???
        size: 'small'
    },
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;

        this.state = {
            orgID: '',
            whOrgID: '',
            loading: false,
            loadingSearch: false,
            resourceId: '',
            visibleBjdh: '',
            loadingBjdh: '',
            kkkData: '0',
            defaultExpandedKeys: ['1000'],
            catName: '',
            treeData: [],
            QnnTableItemData: [],
            purDate: null,
            abcType: null,

            visibleTwo: '',
            loadingTwo: '',
            period: new Date().getFullYear() + '', // ??????
            djType: 'all', // ????????????
            treeNode: '', // ????????????
            staticsType: 'com', // ???????????? ????????????com;????????????provice???????????????area
            dataType: '1',  // ???????????? ??????????????????1??????????????????2????????????3

            parentID: `-1_1_com_all_${new Date().getFullYear() + ''}`,

            treeNodeValue: '',
            searchVal: '',
            searchLabel: '',
            treeNodeTableData: [],
            valueByTree: [],
            treeListToTableParams: null,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    rowImportClick = (args) => {
        args.btnCallbackFn.setTabsIndex("1")
    }
    rowLookClick = (args) => {
        args.btnCallbackFn.setTabsIndex("1")
    }
    firstIDChange = (val, args) => {
        const { itemData } = args;
        const { data, success, message, code } = this.props.myFetch("psGetZxGcsgCtContrApplyContractNo", { contractNo: itemData.contractNo, firstID: val });
        if (success) {
            this.tableSK.setValues({
                contractNo: data
            })
        } else {
            msg.errMsg(message, code)
        }
    }
    tabThreeDataFormat = (data) => {
        const newData = [...data].reduce((preArr, curData, i) => {
            //???????????? rowSpan ?????? 
            //??????map???????????????rowSpan ?????????????????????????????????
            let added = false;
            //?????? preArr???curData
            const newArr = [...preArr].map(item => {
                //???????????????????????????????????????
                if (item.workNo === curData.workNo) {
                    //????????? workNo ????????????  ??????????????? workNo ??????????????? 
                    const defaultRowSpan = item.rowSpan || 1;
                    const rowSpan = !added ? defaultRowSpan + 1 : 0;
                    //???????????????????????????????????????????????? ???????????????????????????????????? rowSpan?????????0
                    curData.rowSpan = 0;
                    added = true;
                    return { ...item, rowSpan: rowSpan };
                }
                return { ...item };
            }).concat([{ ...curData }]);
            return newArr;
        }, []);
        return newData
    }
    getTabThreeTableOtherParams = (args) => {
        //????????????id
        const { ctContractId } = this.tableSK?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { ctContractId }
    }
    getTabFourTableOtherParams = (args) => {
        //????????????id
        const { ctContractId } = this.tableSK?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { ctContractId }
    }
    synchronouslySuspendData = () => {
        const { ctContrApplyId } = this.tableSK.getFieldsValue();

        const { success, message, code } = this.props.myFetch("psSyncHookDataZxGcsgCtPriceSys", {
            ctContrApplyId
        });
        if (success) {
            msg.successMsg(message, code)
            //??????????????????tab??????
            this.tabFourTable.current.refresh();
        } else {
            msg.errMsg(message, code)
        }
    }
    printByFourTab = () => {
        const { success, message, code, data } = this.props.myFetch("printClick");
        if (success) {
            return data
        } else {
            msg.errMsg(message, code)
        }
    }
    getFinalStatementOtherParams = (args) => {
        //????????????id
        const { ctContractId } = this.tableSK?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { contractID: ctContractId }
    }
    huifuzhixing = (args) => {
        //????????????id
        const { selectedRows } = args;

        if (selectedRows.length > 1) {
            Modal.warn({
                title: "????????????????????????"
            })
            return;
        }

        const { success, message, code } = this.props.myFetch("resumeZxGcsgCtContractStatus", {
            ctContractId: selectedRows[0]?.ctContractId,
            // contractStatus: selectedRows[0]?.contractStatus,
            contractStatus: selectedRows[0].contractStatus === '3' ? '1' : '3'
        });
        if (success) {
            msg.successMsg(message)
        } else {
            msg.errMsg(message, code)
        }
    }
    getUserInfo = () => {
        const { departmentName, companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId } = this.state
        return {
            companyId: companyId,
            companyName: companyName,
            projectId: departmentId,
            projectName: departmentName
        }
    }
    exportData = ({ btnCallbackFn: { download } }, params = []) => {
        const apiName = params[0];
        const { domain, apis } = this.props.myPublic;
        const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
        download(`${domain}${apis[apiName]}`, {
            ...this.getUserInfo()
        }, { Authorization: `Bearer ${access_token}` });
    }
    tabFourFormFieldOnBlur = () => {
        this.tabFourTable.getValues(false, (vals) => {
            const { amt1, amt2, amt3, amt4, amt5, amt6, bhPriceNoTax } = vals;
            //???????????????
            const priceNoTax = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
            //?????????????????????/??????-??????????????????
            const dbPrice = bhPriceNoTax - priceNoTax;

            this.tabFourTable.setValues({
                priceNoTax,
                dbPrice
            })
        })
    }
    tabFourGXSaveCb = () => {
        this.tabFourTable.getValues(false, (vals) => {
            const { ctPriceSysItemList = [], amt1, amt2, amt3, bhPriceNoTax } = vals;
            let amt4 = 0, amt5 = 0, amt6 = 0;
            ctPriceSysItemList.forEach(({ rgf, zzcl, jxsb }) => {
                amt4 += rgf;
                amt5 += zzcl;
                amt6 += jxsb;
            })
            //???????????????
            const priceNoTax = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
            //?????????????????????/??????-??????????????????
            const dbPrice = bhPriceNoTax - priceNoTax;

            //????????????????????????????????????????????????????????????????????????
            this.tabFourTable.setValues({
                amt4, amt5, amt6,
                priceNoTax,
                dbPrice
            })
        })
    }


    formConfig = () => {
        return [
            {
                type: 'qnnTable',
                field: 'table2',
                qnnTableConfig: {
                    data: this.state?.QnnTableItemData || [],
                    ...configItem,
                    wrappedComponentRef: (me) => {
                        this.tableSK = me;
                    },
                    componentsKey: {
                        PsjlckBtn: () => {
                            // return <div style={{ margin: "12px 12px 0px 12px", textAlign: "right" }}>
                            //     <Button type="primary" onClick={() => setHtpsModalShow(true)}>????????????????????????</Button>
                            // </div>
                            return ''
                        }
                    },
                    getRootTable: () => this.tableSK,
                    method: {
                        rowImportClick: this.rowImportClick,
                        rowLookClick: this.rowLookClick,
                        firstIDChange: this.firstIDChange,
                        tabThreeDataFormat: this.tabThreeDataFormat,
                        getTabThreeTableOtherParams: this.getTabThreeTableOtherParams,
                        getTabFourTableOtherParams: this.getTabFourTableOtherParams,
                        synchronouslySuspendData: this.synchronouslySuspendData,
                        printByFourTab: this.printByFourTab,
                        getFinalStatementOtherParams: this.getFinalStatementOtherParams,
                        huifuzhixing: this.huifuzhixing,
                        getUserInfo: this.getUserInfo,
                        exportData: this.exportData,
                        tabFourFormFieldOnBlur: this.tabFourFormFieldOnBlur,
                        tabFourGXSaveCb: this.tabFourGXSaveCb
                    },
                    fetchConfig: {
                        apiName: 'getZxCtPriceSysItemDataListByItemData',
                        otherParams: () => {
                            return {
                                ...this.state?.treeListToTableParams || null,
                            }
                        },
                    },
                    isShowRowSelect: false,
                    formConfig: [
                        {
                            isInTable: false,
                            form: {
                                label: '??????id',
                                field: 'zxCrJYearCreditEvaItemId',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: '100',
                                dataIndex: 'comName',
                                key: 'comName'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                width: '150',
                                tooltip: 5,
                                dataIndex: 'orgName',
                                key: 'orgName',
                            },
                        },
                        {
                            table: {
                                title: '??????',
                                tooltip: 23,
                                dataIndex: 'proviceName',
                                key: 'proviceName'
                            },
                        },
                        {
                            table: {
                                title: '??????',
                                tooltip: 23,
                                dataIndex: 'area',
                                key: 'area'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 10,
                                width: '180',
                                dataIndex: 'contractName',
                                key: 'contractName'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 16,
                                width: '180',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                onClick: "detail"
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 10,
                                width: '180',
                                dataIndex: 'workName',
                                key: 'workName'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 23,
                                dataIndex: 'workNo',
                                key: 'workNo'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 23,
                                dataIndex: 'processNo',
                                key: 'processNo'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 10,
                                width: '180',
                                dataIndex: 'processName',
                                key: 'processName'
                            },
                        },
                        {
                            table: {
                                title: '?????????',
                                tooltip: 23,
                                dataIndex: 'rgf',
                                key: 'rgf'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 23,
                                dataIndex: 'zzcl',
                                key: 'zzcl'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 23,
                                dataIndex: 'jxsb',
                                key: 'jxsb'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 23,
                                dataIndex: 'price',
                                key: 'price'
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                tooltip: 23,
                                dataIndex: 'period',
                                key: 'period'
                            },
                        },
                    ],
                    drawerConfig: {
                        width: '100%',
                    },
                    tabs: [
                        {
                            field: "baiseInfo",
                            title: "????????????",
                            name: "qnnForm",
                            //???????????????????????????????????????????????? ???????????????????????????
                            // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: {
                                ...tabOne,
                            }
                        },

                        //????????????????????????????????????
                        {
                            field: "listSubcontracts",
                            title: "??????",
                            name: "listSubcontracts",
                            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: TabTwo,
                        },
                        {
                            field: "listSubcontractsByDetail",
                            title: "??????????????????????????????",
                            name: "listSubcontractsByDetail",
                            content: TabTwo,
                            hide: (args) => args.clickCb?.rowInfo?.name !== "detail",
                        },

                        {
                            field: "inventoryProcessLinkedLedger",
                            title: "????????????????????????",
                            name: "qnnTable",
                            content: {
                                // ??????
                                ...tabThree
                            }
                        },
                        {
                            field: "contractAnalysis",
                            title: "??????????????????",
                            name: "qnnTable",
                            content: {
                                // ??????
                                ...tabFour,
                                wrappedComponentRef: (me) => {
                                    this.tabFourTable = me
                                }
                            }
                        },

                        {
                            field: "buchognxieyi",
                            title: "????????????",
                            name: "qnnTable",
                            // hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: {
                                // ??????
                                ...tabFive
                            }
                        },

                        {
                            field: "FenBaoYuYeZhu",
                            title: "??????????????????????????????????????????",
                            name: "FenBaoYuYeZhu",
                            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: FenBaoYuYeZhu,
                        },
                        {
                            field: "TabSix",
                            title: "??????????????????",
                            name: "TabSix",
                            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: TabSix,
                        },
                        {
                            field: "finalStatement",
                            title: "?????????",
                            name: "qnnTable",
                            content: {
                                // ??????
                                ...finalStatement
                            }
                        },
                    ]
                }
            }
        ]
    }
    //--------------------------------------------------------------------------------------


    formRef = React.createRef()
    componentDidMount() {
        // this.getTreeData();
        this.refresh();
    }
    // ?????????-??????
    getTreeData(val) {
        const { myFetch } = this.props;
        myFetch('getZxCrColCategoryTreeShu', {}).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data
                    })
                } else {
                    Msg.warn(message)
                }
            }
        );
    }

    otherClick = (obj) => {
        const { rowData: { processNo, workno, processName, djType, period }, colConfig: { title } } = obj
        const { staticsType, } = this.formHasTicket.form.getFieldsValue();
        // const parentRowData = obj.qnnTableInstance.getRowDataById(parentID)
        this.setState({
            treeListToTableParams: {
                processNo,
                // processNo: processNo ? processNo : parentRowData.processNo,
                workNo: workno,
                processName,
                // processName: processNo ? processName : parentRowData.processName,
                djType,
                comName: title,
                staticsType: staticsType,
                period
            }
        }, () => {
            obj.qnnTableInstance.btnAction({
                btnConfig: {
                    name: "detail",
                    drawerTitle: ""

                },
                attrBindInfo: {
                    rowData: {
                        ...obj.rowData
                    }
                }
            })
        })
    }

    refresh = () => {
        this.setState({
            loading: true
        })

        this.props.myFetch('getZxCtContrProcessTree', {

        }).then(
            ({ data, success, message }) => {
                if (success) {
                    let dataList = data
                    let filterDataList = (data) => {
                        for (let i = 0; i < data.length; i++) {
                            if (!data.childrenList.length) {
                                data[i].childrenList = data[i].children
                            } else {
                                filterDataList(data[i])
                            }
                        }
                        return data
                    }
                    dataList = filterDataList(dataList)

                    dataList = dataList.filter(ele => ele.id === '1000')

                    this.setState({
                        treeData: dataList,
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }

    handleCancelHight = () => {
        this.setState({ visibleBjdh: false, loadingBjdh: false });
    }

    handleCancelTwo = () => {
        this.setState({ visibleTwo: false, loadingTwo: false });
    }

    setTableTreeNode = (
        {
            rowData,
            setTableData,
            getTableData,
            expandNode,
            getExpandedRowsKey,
            getVTableData,
            message,
            apiName,
            params

        }
    ) => {
        let expandedRowsKey = getExpandedRowsKey();
        let key = rowData.zxCtCpStatisticsFinId;
        let tableData = getVTableData()
        //??????
        if (expandedRowsKey.includes(key)) {

            expandNode(key, 'close')
            return
        }

        message.loading('loading', 1)
        this.props.myFetch(apiName, params).then((res) => {
            message.destroy()
            var success = res.success;
            var childrenData = res.data;

            if (success) {
                if (!childrenData.length) {
                    Msg.warn("???????????????????????????")
                    return;
                }

                var loopFn = function (data) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].zxCtCpStatisticsFinId === key) {
                            data[i].children = childrenData;
                        } else if (data[i].children) {
                            data[i].children = loopFn(data[i].children)
                        }
                    }
                    return data;
                }

                tableData = loopFn([...tableData]);

                setTableData(tableData);
                expandNode(key, 'expand')

                // this.table.scrollToRowByIndex(key, "center")
            } else {
                Msg.error(res.message)
            }
        })
    }

    render() {
        const {  visibleBjdh, loadingBjdh, period, // ??????
            djType,
            treeNode,
            staticsType,
            dataType,
            parentID,
            // searchVal,
            searchLabel,
            defaultExpandedKeys,
            treeData,
            valueByTree
        } = this.state;
        return (
            <div>
                <Spin tip="Loading..." spinning={this.state.loading}>
                    <Spin spinning={this.state.loadingSearch}>
                        <Row>
                            <Col span={24}>
                                <QnnForm
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{
                                        token: this.props.loginAndLogoutInfo.loginInfo.token
                                    }}
                                    wrappedComponentRef={(me) => {
                                        this.formHasTicket = me;
                                    }}
                                    formItemLayout={{
                                        labelCol: {
                                            xs: { span: 7 },
                                            sm: { span: 6 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 18 },
                                            sm: { span: 18 }
                                        }
                                    }}
                                    formConfig={[
                                        {
                                            label: '????????????',
                                            field: 'period',
                                            type: 'year',
                                            // optionConfig: {
                                            //     label: 'periodName', //?????? label
                                            //     value: 'period'
                                            // },
                                            // fetchConfig: {
                                            //     apiName: 'getZxCtCpStatisticsFinHighwayYearAllList',
                                            // },

                                            initialValue: new Date().getFullYear() + '',
                                            onChange: (val, rowData) => {
                                                let parentStr = []

                                                this.state.parentID.split('_').map((item, index) => {
                                                    // 0 -> ????????? ?????? -1
                                                    // 1 -> dataType
                                                    // 2 -> staticsType
                                                    // 3 -> djType
                                                    // 4 -> period
                                                    switch (index) {
                                                        case 0:
                                                            parentStr[index] = '-1'
                                                            break
                                                        case 1:
                                                            parentStr[index] = this.state.dataType
                                                            break
                                                        case 2:
                                                            parentStr[index] = this.state.staticsType
                                                            break
                                                        case 3:
                                                            parentStr[index] = this.state.djType.length === 3 ? 'all' : this.state.djType.join('')
                                                            break
                                                        case 4:
                                                            parentStr[index] = val._d.toString().split(' ')[3]
                                                            break
                                                        default:
                                                            break
                                                    }
                                                    return true
                                                })

                                                this.setState({
                                                    staticsType,
                                                    dataType,
                                                    djType: djType.length === 3 ? 'all' : djType.join(','),
                                                    treeNode: this.state.searchVal,
                                                    parentID: parentStr.join('_'),
                                                    period: val._d.toString().split(' ')[3]
                                                })
                                            },
                                            span: 6,
                                            formItemLayout: {
                                                labelCol: {
                                                    xs: { span: 4 },
                                                    sm: { span: 4 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 17 },
                                                    sm: { span: 17 }
                                                }
                                            }
                                        },
                                        {
                                            label: '????????????',
                                            field: 'djType',
                                            type: 'checkbox',
                                            initialValue: ['1', '2', '3'],
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '?????????',
                                                    value: '1'
                                                },
                                                {
                                                    label: '????????????',
                                                    value: '2'
                                                },
                                                {
                                                    label: '????????????',
                                                    value: '3'
                                                }
                                            ],
                                            placeholder: '?????????',
                                            span: 6
                                        },
                                        {
                                            label: '????????????',
                                            field: 'staticsType',
                                            type: 'select',
                                            initialValue: 'com',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '?????????',
                                                    value: 'com'
                                                },
                                                {
                                                    label: '?????????',
                                                    value: 'provice'
                                                },
                                                {
                                                    label: '?????????',
                                                    value: 'area'
                                                }
                                            ],
                                            placeholder: '?????????',
                                            span: 6
                                        },
                                        {
                                            label: '????????????',
                                            field: 'dataType',
                                            type: 'select',
                                            initialValue: '1',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '???????????????',
                                                    value: '1'
                                                },
                                                {
                                                    label: '????????????',
                                                    value: '2'
                                                },
                                                {
                                                    label: '??????',
                                                    value: '3'
                                                }
                                            ],
                                            placeholder: '?????????',
                                            span: 6
                                        },
                                    ]}
                                />
                            </Col>

                        </Row>
                        <Row>
                            <Col span={24}>
                                <div style={{
                                    height: '100%',
                                    boxSizing: 'border-box',
                                    overflow: 'hidden',
                                    background: 'white',
                                    position: ' relative',
                                    paddingBottom: '12px',
                                }}>
                                    <Row>
                                        <div className={"ant-col ant-col-6 undefined qnn-form-formItemCol null"} style={{ padding: '12px' }}>
                                            <div className={'ant-row ant-form-item null qnnFormItem default_qnnFormItem__24_PO null'}>
                                                <div className={'ant-col ant-form-item-label ant-col-xs-4 ant-col-sm-4'}>
                                                    <label>
                                                        <div>????????????</div>
                                                    </label>
                                                </div>
                                                <div className={'ant-col ant-form-item-control ant-col-xs-17 ant-col-sm-17'}>
                                                    <div className={"ant-form-item-control-input"}>
                                                        <Search
                                                            placeholder=""
                                                            value={searchLabel}
                                                            allowClear
                                                            onSearch={(val, ev) => {
                                                                // ?????????????????????type?????? ???????????? ???????????? ?????? x??????????????????
                                                                if (ev.currentTarget.type === 'button') {
                                                                    this.setState({
                                                                        visibleBjdh: true,
                                                                    })
                                                                } else {
                                                                    // ?????????????????? tree??????????????? ??? table???????????????
                                                                    this.setState({
                                                                        treeNodeTableData: [],
                                                                        valueByTree: []
                                                                    }, () => {
                                                                        this.setState({
                                                                            // searchVal: this.state.valueByTree.join(';'),
                                                                            searchVal: '',
                                                                            // searchLabel: (this.state.treeNodeTableData.map(item => {
                                                                            //     return item.processNo
                                                                            // })).join(';'),
                                                                            searchLabel: ''
                                                                        })
                                                                    })
                                                                }
                                                            }}
                                                            onChange={(e, value) => {
                                                                this.setState({
                                                                    searchVal: e.target.value
                                                                })
                                                            }}
                                                            style={{ width: 300 }}
                                                            enterButton
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className={"ant-col ant-col-6 undefined qnn-form-formItemCol null"} style={{ padding: '12px' }}>
                                            <div >
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    const {
                                                        staticsType,
                                                        dataType,
                                                        djType,
                                                    } = this.formHasTicket.form.getFieldsValue();
                                                    let parentStr = []

                                                    this.state.parentID.split('_').map((item, index) => {
                                                        // 0 -> ????????? ?????? -1
                                                        // 1 -> dataType
                                                        // 2 -> staticsType
                                                        // 3 -> djType
                                                        // 4 -> period
                                                        switch (index) {
                                                            case 0:
                                                                parentStr[index] = '-1'
                                                                break
                                                            case 1:
                                                                parentStr[index] = dataType
                                                                break
                                                            case 2:
                                                                parentStr[index] = staticsType
                                                                break
                                                            case 3:
                                                                parentStr[index] = djType.length === 3 ? 'all' : djType.join('')
                                                                break
                                                            case 4:
                                                                parentStr[index] = this.state.period
                                                                break
                                                            default:
                                                                break
                                                        }
                                                        return true
                                                    })

                                                    if (staticsType) {
                                                        this.setState({
                                                            staticsType,
                                                            dataType,
                                                            djType: djType.length === 3 ? 'all' : djType.join(','),
                                                            period: this.state.period,
                                                            treeNode: this.state.searchVal,
                                                            parentID: parentStr.join('_')
                                                        }, () => {
                                                            this.table.refresh();
                                                        })
                                                    } else {
                                                        Msg.warn('??????????????????')
                                                    }
                                                }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {
                                                    const {
                                                        staticsType,
                                                        dataType,
                                                        djType,
                                                        period,
                                                        treeNode
                                                    } = this.formHasTicket.form.getFieldsValue();
                                                    let parentStr = []

                                                    this.state.parentID.split('_').map((item, index) => {
                                                        // 0 -> ????????? ?????? -1
                                                        // 1 -> dataType
                                                        // 2 -> staticsType
                                                        // 3 -> djType
                                                        // 4 -> period
                                                        switch (index) {
                                                            case 0:
                                                                parentStr[index] = '-1'
                                                                break
                                                            case 1:
                                                                parentStr[index] = dataType
                                                                break
                                                            case 2:
                                                                parentStr[index] = staticsType
                                                                break
                                                            case 3:
                                                                parentStr[index] = djType.length === 3 ? 'all' : djType.join('')
                                                                break
                                                            case 4:
                                                                parentStr[index] = this.state.period
                                                                break
                                                            default:
                                                                break
                                                        }
                                                        return true
                                                    })
                                                    let { myPublic: {  appInfo: { ureport } } } = this.props;
                                                    const { access_token } = this.props.loginAndLogoutInfo.loginInfo;

                                                    const nameList = {
                                                        com: {
                                                            title: '????????????-??????',
                                                            value: 'zxCtCostAnalysisCom'
                                                        },
                                                        provice: {
                                                            title: '????????????-??????',
                                                            value: 'zxCtCostAnalysisProvince'
                                                        },
                                                        area: {
                                                            title: '????????????-??????',
                                                            value: 'zxCtCostAnalysisArea'
                                                        },
                                                    }

                                                    var URL = `${ureport}excel?_u=minio:${nameList[staticsType]['value']}.xml&access_token=${access_token}&_n=${nameList[staticsType]['title']}.xlsx&staticsType=${staticsType ? staticsType : null}&dataType=${dataType ? dataType : ''}&djType=${djType ? djType.length === 3 ? 'all' : djType.join(',') : ''}&period=${period ? this.state.period : ''}&treeNode=${treeNode ? treeNode : ''}&parentID=${parentID ? parentStr.join('_') : ''}`;

                                                    window.open(URL);
                                                }}>??????</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {

                                                    Msg.warning('??????????????????????????????????????????,?????????????????????!')
                                                    const {
                                                        staticsType,
                                                        dataType,
                                                        djType,
                                                    } = this.formHasTicket.form.getFieldsValue();
                                                    let parentStr = []
                                                    this.state.parentID.split('_').map((item, index) => {
                                                        // 0 -> ????????? ?????? -1
                                                        // 1 -> dataType
                                                        // 2 -> staticsType
                                                        // 3 -> djType
                                                        // 4 -> period
                                                        switch (index) {
                                                            case 0:
                                                                parentStr[index] = '-1'
                                                                break
                                                            case 1:
                                                                parentStr[index] = dataType
                                                                break
                                                            case 2:
                                                                parentStr[index] = staticsType
                                                                break
                                                            case 3:
                                                                parentStr[index] = djType.length === 3 ? 'all' : djType.join('')
                                                                break
                                                            case 4:
                                                                parentStr[index] = this.state.period
                                                                break
                                                            default:
                                                                break
                                                        }
                                                        return true
                                                    })

                                                    this.props.myFetch('batchUpdateZxCtCpStatisticsFinThisPeriodData', {
                                                        staticsType,
                                                        dataType,
                                                        djType: djType.length === 3 ? 'all' : djType.join(','),
                                                        period: this.state.period,
                                                        treeNode: this.state.searchVal,
                                                        parentID: parentStr.join('_')
                                                    }).then(({ data, success, message }) => {
                                                        if (success) {
                                                            Msg.success('??????????????????')
                                                            this.table.refresh()
                                                        } else {
                                                            Msg.error('??????????????????!')
                                                        }
                                                        // console.log('?????????????????? ??????')
                                                    })
                                                }}>??????????????????</Button>
                                            </div>
                                        </div>
                                    </Row>
                                </div>
                            </Col>
                        </Row>
                    </Spin>
                    {staticsType === 'com' ? <QnnTable //?????????
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={{
                            apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                            otherParams: {
                                period, // ??????
                                djType, // ????????????
                                treeNode, // ????????????
                                staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                parentID
                            },
                            success: (res, obj, val) => {
                                const { data } = res.response
                                const {
                                    setTableData, getTableData, expandNode, getExpandedRowsKey, 
                                    tool: { message }
                                } = obj.qnnTableInstance

                                if (data.length && data[0].treeNode === '1000') {
                                    this.setTableTreeNode({
                                        rowData: data[0],
                                        setTableData,
                                        getTableData,
                                        expandNode,
                                        getExpandedRowsKey,
                                        getVTableData: () => {
                                            return data
                                        },
                                        message,
                                        apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                                        params: {
                                            period, // ??????
                                            djType, // ????????????
                                            treeNode, // ????????????
                                            staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                            dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                            parentID: data[0].zxCtCpStatisticsFinId,
                                            workno: data[0].workno
                                        }
                                    })
                                }
                            }
                        }}
                        actionBtns={[]}
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
                                    field: 'zxCtCpStatisticsFinId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    field: 'workno',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            // workno
                            {
                                table: {
                                    title: '??????',
                                    width: 260,
                                    dataIndex: 'processNo',
                                    key: 'processNo',
                                    // onClick: this.setTableTreeNode,
                                    onClick: ({
                                        rowData,
                                        qnnTableInstance: {
                                            setTableData, getTableData, expandNode, getExpandedRowsKey, getVTableData,
                                            tool: { message }
                                        }
                                    }) => {
                                        this.setTableTreeNode({
                                            rowData,
                                            setTableData,
                                            getTableData,
                                            expandNode,
                                            getExpandedRowsKey,
                                            getVTableData,
                                            message,
                                            apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                                            params: {
                                                period: this.state.period, // ??????
                                                djType: this.state.djType, // ????????????
                                                treeNode: this.state.treeNode, // ????????????
                                                staticsType: this.state.staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                                dataType: this.state.dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                                parentID: rowData.zxCtCpStatisticsFinId,
                                                workno: rowData.workno
                                            }
                                        })
                                    }
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    width: 260,
                                    dataIndex: 'processName',
                                    key: 'processName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'processUnit',
                                    key: 'processUnit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'content',
                                    key: 'content',
                                    width: '180',
                                    tooltip: 10
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '???????????????',
                                    dataIndex: 'avgprice0',
                                    key: 'avgprice0',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                console.log(obj)
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice1',
                                    key: 'avgprice1',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice2',
                                    key: 'avgprice2',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice3',
                                    key: 'avgprice3',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice4',
                                    key: 'avgprice4',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice5',
                                    key: 'avgprice5',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice6',
                                    key: 'avgprice6',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice7',
                                    key: 'avgprice7',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'avgprice8',
                                    key: 'avgprice8',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'avgprice9',
                                    key: 'avgprice9',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'avgprice10',
                                    key: 'avgprice10',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '???????????????',
                                    dataIndex: 'avgprice11',
                                    key: 'avgprice11',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '???????????????',
                                    dataIndex: 'avgprice12',
                                    key: 'avgprice12',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'avgprice13',
                                    key: 'avgprice13',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                        ]}
                        rowClassName={(record, index) => {
                            return record.isleaf === '0' ? s.backgrounde6 : ''
                        }}

                        drawerConfig={{
                            width: '100%'
                        }}
                        tabs={
                            [
                                {
                                    field: "baiseInfo",
                                    title: "",
                                    name: "qnnForm",
                                    //???????????????????????????????????????????????? ???????????????????????????
                                    // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
                                    content: {
                                        formConfig: this.formConfig()
                                    }
                                },
                            ]
                        }
                    /> : null}
                    {staticsType === 'provice' ? <QnnTable //?????????
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={{
                            apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                            otherParams: {
                                period, // ??????
                                djType, // ????????????
                                treeNode, // ????????????
                                staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                parentID
                            },
                            success: (res, obj, val) => {
                                const { data } = res.response
                                const {
                                    setTableData, getTableData, expandNode, getExpandedRowsKey, 
                                    tool: { message }
                                } = obj.qnnTableInstance
                                if (data.length && data[0].treeNode === '1000') {
                                    this.setTableTreeNode({
                                        rowData: data[0],
                                        setTableData,
                                        getTableData,
                                        expandNode,
                                        getExpandedRowsKey,
                                        getVTableData: () => {
                                            return data
                                        },
                                        message,
                                        apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                                        params: {
                                            period, // ??????
                                            djType, // ????????????
                                            treeNode, // ????????????
                                            staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                            dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                            parentID: data[0].zxCtCpStatisticsFinId,
                                            workno: data[0].workno
                                        }
                                    })
                                }
                            }
                        }}
                        actionBtns={[]}
                        rowClassName={(record, index) => {
                            return record.isleaf === '0' ? s.backgrounde6 : ''
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
                                    field: 'zxCtCpStatisticsFinId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            // 
                            {
                                table: {
                                    title: '??????',
                                    width: 260,
                                    dataIndex: 'processNo',
                                    key: 'processNo',
                                    // onClick: this.setTableTreeNode,
                                    onClick: ({
                                        rowData,
                                        qnnTableInstance: {
                                            setTableData, getTableData, expandNode, getExpandedRowsKey, getVTableData,
                                            tool: { message }
                                        }
                                    }) => {
                                        this.setTableTreeNode({
                                            rowData,
                                            setTableData,
                                            getTableData,
                                            expandNode,
                                            getExpandedRowsKey,
                                            getVTableData,
                                            message,
                                            apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                                            params: {
                                                period: this.state.period, // ??????
                                                djType: this.state.djType, // ????????????
                                                treeNode: this.state.treeNode, // ????????????
                                                staticsType: this.state.staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                                dataType: this.state.dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                                parentID: rowData.zxCtCpStatisticsFinId,
                                                workno: rowData.workno
                                            }
                                        })
                                    }
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    width: 260,
                                    dataIndex: 'processName',
                                    key: 'processName',
                                    filter: true,

                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'processUnit',
                                    key: 'processUnit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'content',
                                    key: 'content',
                                    // tooltip: '5',
                                    width: '180',
                                    tooltip: 10
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '???????????????',
                                    dataIndex: 'avgprice0',
                                    key: 'avgprice0',
                                    onClick: (obj, val) => {
                                        const { processNo, workno, processName, djType } = obj.rowData
                                        const { staticsType, } = this.formHasTicket.form.getFieldsValue();
                                        this.setState({
                                            treeListToTableParams: {
                                                processNo,
                                                workNo: workno,
                                                processName,
                                                djType,
                                                staticsType: staticsType,
                                                period
                                            }
                                        }, () => {
                                            obj.qnnTableInstance.btnAction({
                                                btnConfig: {
                                                    name: "detail",
                                                    drawerTitle: ""

                                                },
                                                attrBindInfo: {
                                                    rowData: {
                                                        ...obj.rowData
                                                    }
                                                }
                                            })
                                        })
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice1',
                                    key: 'avgprice1',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice2',
                                    key: 'avgprice2',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice3',
                                    key: 'avgprice3',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice4',
                                    key: 'avgprice4',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice5',
                                    key: 'avgprice5',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice6',
                                    key: 'avgprice6',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice7',
                                    key: 'avgprice7',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'avgprice8',
                                    key: 'avgprice8',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice9',
                                    key: 'avgprice9',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice10',
                                    key: 'avgprice10',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice11',
                                    key: 'avgprice11',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice12',
                                    key: 'avgprice12',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice13',
                                    key: 'avgprice13',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice14',
                                    key: 'avgprice14',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice15',
                                    key: 'avgprice15',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice16',
                                    key: 'avgprice16',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice17',
                                    key: 'avgprice17',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice18',
                                    key: 'avgprice18',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice19',
                                    key: 'avgprice19',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice20',
                                    key: 'avgprice20',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice21',
                                    key: 'avgprice21',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice22',
                                    key: 'avgprice22',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice23',
                                    key: 'avgprice23',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice24',
                                    key: 'avgprice24',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice25',
                                    key: 'avgprice25',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice26',
                                    key: 'avgprice26',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice27',
                                    key: 'avgprice27',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice28',
                                    key: 'avgprice28',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice29',
                                    key: 'avgprice29',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice30',
                                    key: 'avgprice30',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice31',
                                    key: 'avgprice31',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                        ]}

                        tabs={
                            [
                                {
                                    field: "baiseInfo",
                                    title: "",
                                    name: "qnnForm",
                                    //???????????????????????????????????????????????? ???????????????????????????
                                    // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
                                    content: {
                                        formConfig: this.formConfig()
                                    }
                                },
                            ]
                        }
                    /> : null}
                    {staticsType === 'area' ? <QnnTable //?????????
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        {...config}
                        fetchConfig={{
                            apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                            otherParams: {
                                period, // ??????
                                djType, // ????????????
                                treeNode, // ????????????
                                staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                parentID
                            },
                            success: (res, obj, val) => {
                                const { data } = res.response
                                const {
                                    setTableData, getTableData, expandNode, getExpandedRowsKey,
                                    tool: { message }
                                } = obj.qnnTableInstance

                                if (data.length && data[0].treeNode === '1000') {
                                    this.setTableTreeNode({
                                        rowData: data[0],
                                        setTableData,
                                        getTableData,
                                        expandNode,
                                        getExpandedRowsKey,
                                        getVTableData: () => {
                                            return data
                                        },
                                        message,
                                        apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                                        params: {
                                            period, // ??????
                                            djType, // ????????????
                                            treeNode, // ????????????
                                            staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                            dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                            parentID: data[0].zxCtCpStatisticsFinId,
                                            workno: data[0].workno
                                        }
                                    })
                                }
                            }
                        }}
                        actionBtns={[]}
                        rowClassName={(record, index) => {
                            return record.isleaf === '0' ? s.backgrounde6 : ''
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
                                    field: 'zxCtCpStatisticsFinId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            // 
                            {
                                table: {
                                    title: '??????',
                                    width: 260,
                                    dataIndex: 'processNo',
                                    key: 'processNo',
                                    // onClick: this.setTableTreeNode,
                                    onClick: ({
                                        rowData,
                                        qnnTableInstance: {
                                            setTableData, getTableData, expandNode, getExpandedRowsKey, getVTableData,
                                            tool: { message }
                                        }
                                    }) => {
                                        this.setTableTreeNode({
                                            rowData,
                                            setTableData,
                                            getTableData,
                                            expandNode,
                                            getExpandedRowsKey,
                                            getVTableData,
                                            message,
                                            apiName: 'getZxCtCpStatisticsFinHighwayEngList',
                                            params: {
                                                period: this.state.period, // ??????
                                                djType: this.state.djType, // ????????????
                                                treeNode: this.state.treeNode, // ????????????
                                                staticsType: this.state.staticsType, // ???????????? ????????????com;????????????provice???????????????area
                                                dataType: this.state.dataType,// ???????????? ??????????????????1??????????????????2????????????3
                                                parentID: rowData.zxCtCpStatisticsFinId,
                                                workno: rowData.workno
                                            }
                                        })
                                    }
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    width: 260,
                                    dataIndex: 'processName',
                                    key: 'processName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'processUnit',
                                    key: 'processUnit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'content',
                                    width: '180',
                                    tooltip: 10,
                                    key: 'content'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '???????????????',
                                    dataIndex: 'avgprice0',
                                    key: 'avgprice0',
                                    onClick: (obj, val) => {
                                        const { processNo, workno, processName, djType } = obj.rowData
                                        const { staticsType, } = this.formHasTicket.form.getFieldsValue();
                                        this.setState({
                                            treeListToTableParams: {
                                                processNo,
                                                workNo: workno,
                                                processName,
                                                djType,
                                                staticsType: staticsType,
                                                period
                                            }
                                        }, () => {
                                            obj.qnnTableInstance.btnAction({
                                                btnConfig: {
                                                    name: "detail",
                                                    drawerTitle: ""

                                                },
                                                attrBindInfo: {
                                                    rowData: {
                                                        ...obj.rowData
                                                    }
                                                }
                                            })
                                        })
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice1',
                                    key: 'avgprice1',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice2',
                                    key: 'avgprice2',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice3',
                                    key: 'avgprice3',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice4',
                                    key: 'avgprice4',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice5',
                                    key: 'avgprice5',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice6',
                                    key: 'avgprice6',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'avgprice7',
                                    key: 'avgprice7',
                                    render: (val, _, __, obj) => {
                                        return <div
                                            style={{
                                                color: '#1890ff',
                                                cursor: 'pointer',
                                            }}
                                            onClick={() => {
                                                this.otherClick(obj)
                                            }}
                                        >{val}</div>
                                    },
                                },
                                form: {
                                    type: 'number',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                        ]}

                        tabs={
                            [
                                {
                                    field: "baiseInfo",
                                    title: "",
                                    name: "qnnForm",
                                    //???????????????????????????????????????????????? ???????????????????????????
                                    // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
                                    content: {
                                        formConfig: this.formConfig()
                                    }
                                },
                            ]
                        }
                    /> : null}
                </Spin>
                <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="????????????"
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelHight}
                    bodyStyle={{ width: '1200px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelHight}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingBjdh}>
                        <div style={{ height: document.documentElement.clientHeight * 0.75 }}>
                            <div className={s.root}>
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
                                                    let conDomLeft = document.getElementsByClassName('ant-drawer-content-wrapper')[0].offsetLeft;
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
                                    {this.state.treeData ?
                                        <Tree
                                            selectText={false}
                                            modalType="common" //common  drawer  ?????????????????????????????????
                                            visible
                                            selectModal="2" //0?????????  1??????(??????)  2????????????????????????
                                            myFetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            btnShow={false} //????????????????????????
                                            disabled={true}
                                            draggable={false}
                                            nodeRender={nodeData => {
                                                return (
                                                    <span>
                                                        {nodeData["processName"]}
                                                    </span>
                                                );
                                            }}
                                            treeProps={{
                                                showLine: true
                                            }}
                                            defaultExpandedKeys={defaultExpandedKeys}
                                            rightMenuOption={[]}
                                            nodeClick={(node) => {

                                            }}
                                            valueByTree={valueByTree}
                                            onChange={(value, checkedKeys, event) => {
                                                const valueList = value.map(item => {
                                                    return {
                                                        key: item.key,
                                                        processNo: item.processNo,
                                                        processName: item.processName,
                                                        treeNode: item.treeNode
                                                    }
                                                })
                                                const keyList = value.map(item => {
                                                    return item.treeNode
                                                })

                                                this.setState({
                                                    treeNodeTableData: valueList,
                                                    valueByTree: keyList
                                                }, () => {

                                                    this.setState({
                                                        searchVal: this.state.valueByTree.join(';'),
                                                        searchLabel: (this.state.treeNodeTableData.map(item => {
                                                            return item.processNo
                                                        })).join(';'),
                                                    })
                                                })
                                            }}

                                            data={treeData}
                                            //???????????? ??????{value:value,label:label,children:children}
                                            keys={{
                                                label: "processName",
                                                value: "treeNode",
                                                children: "childrenList"
                                            }}
                                        /> : null}
                                </div>
                                <div className={s.rootr}>
                                    <QnnTable
                                        data={this.state.treeNodeTableData}
                                        {...configItem}
                                        wrappedComponentRef={(me) => {
                                            this.tableSK = me;
                                        }}
                                        isShowRowSelect={false}
                                        formConfig={[
                                            {
                                                isInTable: false,
                                                form: {
                                                    label: '??????id',
                                                    field: 'key',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'processNo',
                                                    key: 'processNo'
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'processName',
                                                    key: 'processName'
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '??????',
                                                    dataIndex: 'del',
                                                    align: 'center',
                                                    key: 'del',
                                                    render: (value, rowData) => {
                                                        return <div style={{ color: '#1890ff', cursor: 'pointer' }} onClick={() => {
                                                            this.setState({
                                                                valueByTree: this.state.valueByTree.filter(ele => ele !== rowData.key),
                                                                treeNodeTableData: this.state.treeNodeTableData.filter(ele => ele.key !== rowData.key),
                                                            }, () => {
                                                                this.setState({
                                                                    searchVal: this.state.valueByTree.join(';'),
                                                                    searchLabel: (this.state.treeNodeTableData.map(item => {
                                                                        return item.processNo
                                                                    })).join(';'),
                                                                })
                                                            })
                                                        }}>{'??????'}</div>
                                                    }
                                                },
                                            }
                                        ]}
                                    // actionBtns={[

                                    // ]}
                                    />

                                </div>
                            </div>;
                        </div>
                    </Spin>
                </Modal>
            </div >
        );
    }
}

export default index;