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
            period: new Date().getFullYear() + '', // 年份
            djType: 'all', // 单价类型
            treeNode: '', // 选择章节
            staticsType: 'com', // 选择模式 按公司：com;按省份：provice；按区域：area
            dataType: '1',  // 数据类型 无剔除数据：1；剔除数据：2；差值：3

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
            //需要计算 rowSpan 的数 
            //每次map只能加一次rowSpan 然后需要等下次遍历在加
            let added = false;
            //改变 preArr、curData
            const newArr = [...preArr].map(item => {
                //说明列表中已经有这条清单了
                if (item.workNo === curData.workNo) {
                    //第一个 workNo 需要加一  他的数据的 workNo 不需要加一 
                    const defaultRowSpan = item.rowSpan || 1;
                    const rowSpan = !added ? defaultRowSpan + 1 : 0;
                    //如果发现列表中已经有相同的数据时 一定需要将当前这条数据的 rowSpan设置为0
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
        //获取主键id
        const { ctContractId } = this.tableSK?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { ctContractId }
    }
    getTabFourTableOtherParams = (args) => {
        //获取主键id
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
            //刷新下第四个tab就行
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
        //获取主键id
        const { ctContractId } = this.tableSK?.getFieldsValue?.() || args.clickCb?.rowData || {};
        return { contractID: ctContractId }
    }
    huifuzhixing = (args) => {
        //获取主键id
        const { selectedRows } = args;

        if (selectedRows.length > 1) {
            Modal.warn({
                title: "只能选择一条数据"
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
            //不含税单价
            const priceNoTax = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
            //对比（标后预算/限价-不含税合价）
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
            //不含税单价
            const priceNoTax = amt1 + amt2 + amt3 + amt4 + amt5 + amt6;
            //对比（标后预算/限价-不含税合价）
            const dbPrice = bhPriceNoTax - priceNoTax;

            //所有行汇总计算表单中的人工费、周转材料、机械设备
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
                            //     <Button type="primary" onClick={() => setHtpsModalShow(true)}>合同评审记录查看</Button>
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
                                label: '主键id',
                                field: 'zxCrJYearCreditEvaItemId',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '所属公司',
                                width: '100',
                                dataIndex: 'comName',
                                key: 'comName'
                            },
                        },
                        {
                            table: {
                                title: '所属项目',
                                width: '150',
                                tooltip: 5,
                                dataIndex: 'orgName',
                                key: 'orgName',
                            },
                        },
                        {
                            table: {
                                title: '省份',
                                tooltip: 23,
                                dataIndex: 'proviceName',
                                key: 'proviceName'
                            },
                        },
                        {
                            table: {
                                title: '区域',
                                tooltip: 23,
                                dataIndex: 'area',
                                key: 'area'
                            },
                        },
                        {
                            table: {
                                title: '合同名称',
                                tooltip: 10,
                                width: '180',
                                dataIndex: 'contractName',
                                key: 'contractName'
                            },
                        },
                        {
                            table: {
                                title: '合同编号',
                                tooltip: 16,
                                width: '180',
                                dataIndex: 'contractNo',
                                key: 'contractNo',
                                onClick: "detail"
                            },
                        },
                        {
                            table: {
                                title: '清单名称',
                                tooltip: 10,
                                width: '180',
                                dataIndex: 'workName',
                                key: 'workName'
                            },
                        },
                        {
                            table: {
                                title: '清单编号',
                                tooltip: 23,
                                dataIndex: 'workNo',
                                key: 'workNo'
                            },
                        },
                        {
                            table: {
                                title: '工序编号',
                                tooltip: 23,
                                dataIndex: 'processNo',
                                key: 'processNo'
                            },
                        },
                        {
                            table: {
                                title: '工序名称',
                                tooltip: 10,
                                width: '180',
                                dataIndex: 'processName',
                                key: 'processName'
                            },
                        },
                        {
                            table: {
                                title: '人工费',
                                tooltip: 23,
                                dataIndex: 'rgf',
                                key: 'rgf'
                            },
                        },
                        {
                            table: {
                                title: '周转材料',
                                tooltip: 23,
                                dataIndex: 'zzcl',
                                key: 'zzcl'
                            },
                        },
                        {
                            table: {
                                title: '机械设备',
                                tooltip: 23,
                                dataIndex: 'jxsb',
                                key: 'jxsb'
                            },
                        },
                        {
                            table: {
                                title: '工序单价',
                                tooltip: 23,
                                dataIndex: 'price',
                                key: 'price'
                            },
                        },
                        {
                            table: {
                                title: '所属年份',
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
                            title: "合同信息",
                            name: "qnnForm",
                            //这里使用禁用是因为主表单必须存在 否则组件内部会报错
                            // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: {
                                ...tabOne,
                            }
                        },

                        //以下两个清单只是名字不同
                        {
                            field: "listSubcontracts",
                            title: "清单",
                            name: "listSubcontracts",
                            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: TabTwo,
                        },
                        {
                            field: "listSubcontractsByDetail",
                            title: "工程分包合同管理清单",
                            name: "listSubcontractsByDetail",
                            content: TabTwo,
                            hide: (args) => args.clickCb?.rowInfo?.name !== "detail",
                        },

                        {
                            field: "inventoryProcessLinkedLedger",
                            title: "清单工序挂接台账",
                            name: "qnnTable",
                            content: {
                                // 表格
                                ...tabThree
                            }
                        },
                        {
                            field: "contractAnalysis",
                            title: "合同单价分析",
                            name: "qnnTable",
                            content: {
                                // 表格
                                ...tabFour,
                                wrappedComponentRef: (me) => {
                                    this.tabFourTable = me
                                }
                            }
                        },

                        {
                            field: "buchognxieyi",
                            title: "补充协议",
                            name: "qnnTable",
                            // hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: {
                                // 表格
                                ...tabFive
                            }
                        },

                        {
                            field: "FenBaoYuYeZhu",
                            title: "分包清单与业主合同清单的关联",
                            name: "FenBaoYuYeZhu",
                            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: FenBaoYuYeZhu,
                        },
                        {
                            field: "TabSix",
                            title: "分包合同清单",
                            name: "TabSix",
                            hide: (args) => args.clickCb?.rowInfo?.name === "detail",
                            content: TabSix,
                        },
                        {
                            field: "finalStatement",
                            title: "结算单",
                            name: "qnnTable",
                            content: {
                                // 表格
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
    // 获取树-数据
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
        //收起
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
                    Msg.warn("该节点没有子集数据")
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
        const {  visibleBjdh, loadingBjdh, period, // 年份
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
                                            label: '选择年份',
                                            field: 'period',
                                            type: 'year',
                                            // optionConfig: {
                                            //     label: 'periodName', //默认 label
                                            //     value: 'period'
                                            // },
                                            // fetchConfig: {
                                            //     apiName: 'getZxCtCpStatisticsFinHighwayYearAllList',
                                            // },

                                            initialValue: new Date().getFullYear() + '',
                                            onChange: (val, rowData) => {
                                                let parentStr = []

                                                this.state.parentID.split('_').map((item, index) => {
                                                    // 0 -> 根节点 默认 -1
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
                                            label: '单价类型',
                                            field: 'djType',
                                            type: 'checkbox',
                                            initialValue: ['1', '2', '3'],
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '人工费',
                                                    value: '1'
                                                },
                                                {
                                                    label: '周转材料',
                                                    value: '2'
                                                },
                                                {
                                                    label: '机械设备',
                                                    value: '3'
                                                }
                                            ],
                                            placeholder: '请选择',
                                            span: 6
                                        },
                                        {
                                            label: '选择模式',
                                            field: 'staticsType',
                                            type: 'select',
                                            initialValue: 'com',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '按公司',
                                                    value: 'com'
                                                },
                                                {
                                                    label: '按省份',
                                                    value: 'provice'
                                                },
                                                {
                                                    label: '按区域',
                                                    value: 'area'
                                                }
                                            ],
                                            placeholder: '请选择',
                                            span: 6
                                        },
                                        {
                                            label: '数据类型',
                                            field: 'dataType',
                                            type: 'select',
                                            initialValue: '1',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value',
                                            },
                                            optionData: [
                                                {
                                                    label: '无剔除数据',
                                                    value: '1'
                                                },
                                                {
                                                    label: '剔除数据',
                                                    value: '2'
                                                },
                                                {
                                                    label: '差值',
                                                    value: '3'
                                                }
                                            ],
                                            placeholder: '请选择',
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
                                                        <div>选择章节</div>
                                                    </label>
                                                </div>
                                                <div className={'ant-col ant-form-item-control ant-col-xs-17 ant-col-sm-17'}>
                                                    <div className={"ant-form-item-control-input"}>
                                                        <Search
                                                            placeholder=""
                                                            value={searchLabel}
                                                            allowClear
                                                            onSearch={(val, ev) => {
                                                                // 根据当前元素的type区分 点击的是 搜索按钮 还是 x（清空按钮）
                                                                if (ev.currentTarget.type === 'button') {
                                                                    this.setState({
                                                                        visibleBjdh: true,
                                                                    })
                                                                } else {
                                                                    // 清空需要重置 tree选中的数据 和 table展示的数据
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
                                                        // 0 -> 根节点 默认 -1
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
                                                        Msg.warn('请选择模式！')
                                                    }
                                                }}>查询</Button>
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
                                                        // 0 -> 根节点 默认 -1
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
                                                            title: '成本分析-公司',
                                                            value: 'zxCtCostAnalysisCom'
                                                        },
                                                        provice: {
                                                            title: '成本分析-省份',
                                                            value: 'zxCtCostAnalysisProvince'
                                                        },
                                                        area: {
                                                            title: '成本分析-区域',
                                                            value: 'zxCtCostAnalysisArea'
                                                        },
                                                    }

                                                    var URL = `${ureport}excel?_u=minio:${nameList[staticsType]['value']}.xml&access_token=${access_token}&_n=${nameList[staticsType]['title']}.xlsx&staticsType=${staticsType ? staticsType : null}&dataType=${dataType ? dataType : ''}&djType=${djType ? djType.length === 3 ? 'all' : djType.join(',') : ''}&period=${period ? this.state.period : ''}&treeNode=${treeNode ? treeNode : ''}&parentID=${parentID ? parentStr.join('_') : ''}`;

                                                    window.open(URL);
                                                }}>报表</Button>
                                                <Button type="primary" style={{ marginLeft: '10px' }} onClick={() => {

                                                    Msg.warning('当前期次的生成数据请求已发送,请稍等后在查询!')
                                                    const {
                                                        staticsType,
                                                        dataType,
                                                        djType,
                                                    } = this.formHasTicket.form.getFieldsValue();
                                                    let parentStr = []
                                                    this.state.parentID.split('_').map((item, index) => {
                                                        // 0 -> 根节点 默认 -1
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
                                                            Msg.success('数据更新成功')
                                                            this.table.refresh()
                                                        } else {
                                                            Msg.error('数据更新失败!')
                                                        }
                                                        // console.log('更新当期数据 成功')
                                                    })
                                                }}>更新当期数据</Button>
                                            </div>
                                        </div>
                                    </Row>
                                </div>
                            </Col>
                        </Row>
                    </Spin>
                    {staticsType === 'com' ? <QnnTable //按公司
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
                                period, // 年份
                                djType, // 单价类型
                                treeNode, // 选择章节
                                staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
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
                                            period, // 年份
                                            djType, // 单价类型
                                            treeNode, // 选择章节
                                            staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                            dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
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
                                    title: '编码',
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
                                                period: this.state.period, // 年份
                                                djType: this.state.djType, // 单价类型
                                                treeNode: this.state.treeNode, // 选择章节
                                                staticsType: this.state.staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                                dataType: this.state.dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
                                                parentID: rowData.zxCtCpStatisticsFinId,
                                                workno: rowData.workno
                                            }
                                        })
                                    }
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '标准工序名称',
                                    width: 260,
                                    dataIndex: 'processName',
                                    key: 'processName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'processUnit',
                                    key: 'processUnit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '施工内容',
                                    dataIndex: 'content',
                                    key: 'content',
                                    width: '180',
                                    tooltip: 10
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '局平均单价',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '一公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '二公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '三公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '四公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '五公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '六公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '七公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '桥隧公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '厦门公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '海威公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '总承包公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '建筑分公司',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '世通重工',
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
                                    placeholder: '请输入'
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
                                    //这里使用禁用是因为主表单必须存在 否则组件内部会报错
                                    // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
                                    content: {
                                        formConfig: this.formConfig()
                                    }
                                },
                            ]
                        }
                    /> : null}
                    {staticsType === 'provice' ? <QnnTable //按省份
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
                                period, // 年份
                                djType, // 单价类型
                                treeNode, // 选择章节
                                staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
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
                                            period, // 年份
                                            djType, // 单价类型
                                            treeNode, // 选择章节
                                            staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                            dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
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
                                    title: '编码',
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
                                                period: this.state.period, // 年份
                                                djType: this.state.djType, // 单价类型
                                                treeNode: this.state.treeNode, // 选择章节
                                                staticsType: this.state.staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                                dataType: this.state.dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
                                                parentID: rowData.zxCtCpStatisticsFinId,
                                                workno: rowData.workno
                                            }
                                        })
                                    }
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '标准工序名称',
                                    width: 260,
                                    dataIndex: 'processName',
                                    key: 'processName',
                                    filter: true,

                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'processUnit',
                                    key: 'processUnit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '施工内容',
                                    dataIndex: 'content',
                                    key: 'content',
                                    // tooltip: '5',
                                    width: '180',
                                    tooltip: 10
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '局平均单价',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '北京',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '天津',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '河北',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '山西',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '内蒙',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '辽宁',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '吉林',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '黑龙江',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '上海',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '江苏',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '浙江',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '安徽',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '福建',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '山东',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '江西',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '湖北',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '湖南',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '河南',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '广东',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '广西',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '海南',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '重庆',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '四川',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '贵州',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '云南',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '西藏',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '宁夏',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '新疆',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '青海',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '陕西',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '甘肃',
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
                                    placeholder: '请输入'
                                }
                            },
                        ]}

                        tabs={
                            [
                                {
                                    field: "baiseInfo",
                                    title: "",
                                    name: "qnnForm",
                                    //这里使用禁用是因为主表单必须存在 否则组件内部会报错
                                    // disabled: (args) => args.clickCb?.rowInfo?.name === "detail",
                                    content: {
                                        formConfig: this.formConfig()
                                    }
                                },
                            ]
                        }
                    /> : null}
                    {staticsType === 'area' ? <QnnTable //按区域
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
                                period, // 年份
                                djType, // 单价类型
                                treeNode, // 选择章节
                                staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
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
                                            period, // 年份
                                            djType, // 单价类型
                                            treeNode, // 选择章节
                                            staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                            dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
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
                                    title: '编码',
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
                                                period: this.state.period, // 年份
                                                djType: this.state.djType, // 单价类型
                                                treeNode: this.state.treeNode, // 选择章节
                                                staticsType: this.state.staticsType, // 选择模式 按公司：com;按省份：provice；按区域：area
                                                dataType: this.state.dataType,// 数据类型 无剔除数据：1；剔除数据：2；差值：3
                                                parentID: rowData.zxCtCpStatisticsFinId,
                                                workno: rowData.workno
                                            }
                                        })
                                    }
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '标准工序名称',
                                    width: 260,
                                    dataIndex: 'processName',
                                    key: 'processName',
                                    filter: true,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '单位',
                                    dataIndex: 'processUnit',
                                    key: 'processUnit'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '施工内容',
                                    dataIndex: 'content',
                                    width: '180',
                                    tooltip: 10,
                                    key: 'content'
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '局平均单价',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '华北',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '东北',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '华东',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '华中',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '华南',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '西南',
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
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '西北',
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
                                    placeholder: '请输入'
                                }
                            },
                        ]}

                        tabs={
                            [
                                {
                                    field: "baiseInfo",
                                    title: "",
                                    name: "qnnForm",
                                    //这里使用禁用是因为主表单必须存在 否则组件内部会报错
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
                    title="选择章节"
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
                                            modalType="common" //common  drawer  抽屉出现方式或者普通的
                                            visible
                                            selectModal="2" //0不可选  1单选(默认)  2多选（暂不可用）
                                            myFetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            btnShow={false} //是否显示底部按钮
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
                                            //键值配置 默认{value:value,label:label,children:children}
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
                                                    label: '主键id',
                                                    field: 'key',
                                                    hide: true
                                                }
                                            },
                                            {
                                                table: {
                                                    title: '编号',
                                                    dataIndex: 'processNo',
                                                    key: 'processNo'
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '名称',
                                                    dataIndex: 'processName',
                                                    key: 'processName'
                                                },
                                            },
                                            {
                                                table: {
                                                    title: '操作',
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
                                                        }}>{'删除'}</div>
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