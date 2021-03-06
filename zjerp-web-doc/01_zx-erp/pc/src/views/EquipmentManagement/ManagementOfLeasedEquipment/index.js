import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import s from "./style.less";
import { message as Msg, Modal, Tabs, Button, Avatar } from "antd";
const { confirm, info } = Modal;
const { TabPane } = Tabs;
const config1 = {
    antd: {
        rowKey: 'id',
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
            xs: { span: 10 },
            sm: { span: 10 }
        },
        wrapperCol: {
            xs: { span: 14 },
            sm: { span: 14 }
        }
    }
};
const config2 = {
    antd: {
        rowKey: 'id',
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
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            companyId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyId : curCompany?.companyId) : curCompany?.companyId,
            companyName: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.companyName : curCompany?.companyName) : curCompany?.companyName,
            activeKey: '1',
            visible: false
        }
    }
    tabsCallback = (activeKey) => {
        this.setState({
            activeKey
        })
    }
    render () {
        const { departmentId, companyId, companyName, activeKey, visible } = this.state;
        return (
            <div className={s.root}>
                <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                    <TabPane tab="????????????????????????????????????" key="1">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table1 = me;
                            }}
                            {...config1}
                            fetchConfig={{
                                apiName: 'getZxEqOuterEquipList',
                                otherParams: {
                                    orgID: departmentId
                                }
                            }}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    let props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "ManagementOfLeasedEquipmentWB"
                                    }
                                }
                            }}
                            method={{
                                editDisabled: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                QRCodeDisabled: (obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                QRCodeOnClick: (obj) => {
                                    let { domain } = obj.props.myPublic;
                                    let qrcodeUrl = obj.selectedRows[0].qrcodeUrl;
                                    let qrcodeName = obj.selectedRows[0].qrcodeName;
                                    info({
                                        title: qrcodeName,
                                        content: (
                                            <>
                                                <Avatar shape="square" size={150} src={domain + qrcodeUrl} />
                                            </>
                                        ),
                                        centered: true
                                    });
                                },
                                QRCodeDownloadDisabled: (obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length === 1) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                QRCodeDownloadOnClick: (obj) => {
                                    confirm({
                                        content: '?????????????????????????',
                                        onOk: () => {
                                            let { domain } = obj.props.myPublic;
                                            let qrcodeDownUrl = obj.selectedRows[0].qrcodeDownUrl;
                                            window.location.href = domain + qrcodeDownUrl;
                                        }
                                    });
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
                                        field: 'comID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'secondID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'supplierID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'placeName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'comName',
                                        key: 'comName',
                                        width: 150,
                                        fixed: 'left',
                                        onClick: 'detail',
                                        filter: true,
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        width: 150,
                                        filter: true,
                                        fieldsConfig: {
                                            type: 'select',
                                            field: 'orgIDSearch',
                                            optionConfig: {
                                                label: 'departmentName',
                                                value: 'departmentId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getSysCompanyProject',
                                                otherParams: {
                                                    departmentId: departmentId
                                                }
                                            },
                                        }
                                    },
                                    form: {
                                        field: 'orgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                orgName: 'departmentName',
                                                comID: 'companyId',
                                                comName: 'companyName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        addShow: true,
                                        editShow: true,
                                        detailShow: false,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'orgName',
                                        type: 'string',
                                        addShow: false,
                                        editShow: false,
                                        detailShow: true,
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'contrItem',
                                        key: 'contrItem',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'contrID',
                                        key: 'contrID',
                                        width: 150,
                                        type: 'selectByQnnTable'
                                    },
                                    form: {
                                        type: 'selectByQnnTable',
                                        optionConfig: {
                                            label: 'contractName',
                                            value: 'contractID',
                                            linkageFields: {
                                                contrItem: 'contractNo',
                                                supplierID: "secondID",
                                                secondID: 'secondID',
                                                secondName: 'secondName'
                                            }
                                        },
                                        parent: 'orgID',
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "contrID"
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCtEqContractListForOuterEquip',
                                                // otherParams: {
                                                //     contractStatus: '1'
                                                // },
                                                params: {
                                                    firstId: 'orgID'
                                                }
                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "contractNo",
                                                        title: "????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "contractName",
                                                        title: "????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                }
                                            ]
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    orgID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'equipName',
                                        key: 'equipName',
                                        width: 100,
                                        filter: true,
                                        fieldsConfig: {
                                            field:'equipName',
                                            type: 'string'
                                        }
                                    },
                                    form: {
                                        field: 'equipID',
                                        type: 'selectByQnnTable',
                                        optionConfig: {
                                            label: 'catName',
                                            value: 'zxEqResCategoryId',
                                            linkageFields: {
                                                equipName: 'catName',
                                                equipNo: 'catCode',
                                                spec: 'spec',
                                                parentID: 'parentID',
                                                parentName: 'catParentName'
                                            }
                                        },
                                        parent: "contrID",
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "zxEqResCategoryId"
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxCtEqContrItemListForContractID',
                                                params: {
                                                    contractID: 'contrID'
                                                }
                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "catCode",
                                                        title: "????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "catName",
                                                        title: "????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                }
                                            ]
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    contrID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        addShow: true,
                                        editShow: true,
                                        detailShow: false,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'equipName',
                                        type: 'string',
                                        addShow: false,
                                        editShow: false,
                                        detailShow: true,
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'equipNo',
                                        key: 'equipNo',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'spec',
                                        key: 'spec',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'model',
                                        key: 'model',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '??????(KW)',
                                        field: 'power',
                                        type: 'string',
                                        // min: 0,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'outfactory',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'outfactoryDate',
                                        type: 'date',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '??????',
                                        field: 'orginalValue',
                                        type: 'number',
                                        min: 0,
                                        precision: 2,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'placeName',
                                        key: 'placeName',
                                        width: 100
                                    },
                                    form: {
                                        field: 'place',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //?????? label
                                            value: 'itemId',
                                            linkageFields: {
                                                placeName: 'itemName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'shengfenjianchengdaima'
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????(???)',
                                        dataIndex: 'leaseLimit',
                                        key: 'leaseLimit',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        // min: 0,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????(???/???)',
                                        dataIndex: 'leaseprice',
                                        key: 'leaseprice',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        // min: 0,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'secondName',
                                        key: 'secondName',
                                        width: 100
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '?????????????????????',
                                        dataIndex: 'supplierMaster',
                                        key: 'supplierMaster',
                                        width: 150
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'operator',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'inDate',
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'outDate',
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '?????????',
                                        field: 'passNo',
                                        type: 'string',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '???????????????',
                                        field: 'startEndDate',
                                        type: 'date',
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????????????????',
                                        dataIndex: 'acceptance',
                                        key: 'acceptance',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "?????????",
                                                value: "0"
                                            },
                                            {
                                                label: "??????",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????????????????',
                                        dataIndex: 'isSepcial',
                                        key: 'isSepcial',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
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
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????????????????',
                                        dataIndex: 'inspReport',
                                        key: 'inspReport',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
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
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '???????????????????????????',
                                        dataIndex: 'inspCert',
                                        key: 'inspCert',
                                        width: 150,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
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
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '???????????????',
                                        dataIndex: 'opCert',
                                        key: 'opCert',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
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
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'isOut',
                                        key: 'isOut',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
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
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '??????',
                                        field: 'remark',
                                        type: 'textarea',
                                        placeholder: '?????????',
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
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '??????',
                                        field: 'fileList',
                                        type: 'files',
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: "upload"
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
                                    }
                                },
                            ]}
                        />
                    </TabPane>
                    <TabPane tab="??????????????????????????????" key="2">
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table2 = me;
                            }}
                            {...config2}
                            fetchConfig={{
                                apiName: 'getZxEqRentOutEqRecordList',
                                otherParams: {
                                    orgID: departmentId
                                }
                            }}
                            actionBtns={{
                                apiName: "getSysMenuBtn",
                                otherParams: (obj) => {
                                    let props = obj.props;
                                    let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                                    return {
                                        menuParentId: curRouteData._value,
                                        tableField: "ManagementOfLeasedEquipmentWZ"
                                    }
                                }
                            }}
                            method={{
                                editDisabled: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1 && (data[0].status === '' || data[0].status === '0')) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                auditDisabled: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1 && (data[0].status === '' || data[0].status === '0')) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                auditOnClick: (obj) => {
                                    confirm({
                                        content: '????????????????????????????',
                                        onOk: () => {
                                            obj.selectedRows[0].status = '1';
                                            this.props.myFetch('auditZxEqRentOutEqRecord', ...obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        obj.btnCallbackFn.refresh();
                                                        obj.btnCallbackFn.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                }
                                            );
                                        }
                                    });
                                },
                                auditeeDisabled: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length === 1 && data[0].status === '1') {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                auditeeOnClick: (obj) => {
                                    confirm({
                                        content: '???????????????????????????????',
                                        onOk: () => {
                                            obj.selectedRows[0].status = '0';
                                            this.props.myFetch('auditZxEqRentOutEqRecord', ...obj.selectedRows).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        obj.btnCallbackFn.refresh();
                                                        obj.btnCallbackFn.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message);
                                                    }
                                                }
                                            );
                                        }
                                    });
                                },
                                delDisabled: (obj) => {
                                    let data = obj.btnCallbackFn.getSelectedRows();
                                    if (data.length && !data.filter(item => item.status === '1').length) {
                                        return false;
                                    } else {
                                        return true;
                                    }
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
                                        field: 'rentOrgName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'companyId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'companyName',
                                        key: 'companyName',
                                        onClick: 'detail',
                                        filter: true,
                                    },
                                    form: {
                                        type: 'string',
                                        addDisabled: true,
                                        editDisabled: true,
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'billNo',
                                        key: 'billNo',
                                        filter: true
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'orgName',
                                        key: 'orgName',
                                        filter: true,
                                        fieldsConfig: {
                                            type: 'select',
                                            field: 'orgIDSearch',
                                            optionConfig: {
                                                label: 'departmentName',
                                                value: 'departmentId'
                                            },
                                            fetchConfig: {
                                                apiName: 'getSysCompanyProject',
                                                otherParams: {
                                                    departmentId: departmentId
                                                }
                                            },
                                        }
                                    },
                                    form: {
                                        field: 'orgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId',
                                            linkageFields: {
                                                orgName: 'departmentName',
                                                companyId: 'companyId',
                                                companyName: 'companyName'
                                            }
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams: {
                                                departmentId: departmentId
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        required: true,
                                        addShow: true,
                                        editShow: true,
                                        detailShow: false,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: '????????????',
                                        field: 'orgName',
                                        type: 'string',
                                        addShow: false,
                                        editShow: false,
                                        detailShow: true,
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'rentOrgName',
                                        key: 'rentOrgName',
                                        filter: true,
                                        fieldsConfig: {
                                            type: 'string'
                                        }
                                    },
                                    form: {
                                        field: 'rentOrgID',
                                        type: 'selectByQnnTable',
                                        optionConfig: {
                                            label: 'secondName',
                                            value: 'secondID',
                                            linkageFields: {
                                                rentOrgName: 'secondName',
                                            }
                                        },
                                        parent: 'orgID',
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "secondID"
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxEqOuterEquipListForRecord',
                                                params: {
                                                    orgID: 'orgID'
                                                },
                                                otherParams: {
                                                    workRecordFlag: "outWorkRecord"
                                                }
                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "contrItem",
                                                        title: "???????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "secondName",
                                                        title: "???????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                }
                                            ]
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    orgID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'outerEquipID',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'eqName',
                                        key: 'eqName'
                                    },
                                    form: {
                                        type: 'selectByQnnTable',
                                        optionConfig: {
                                            label: 'equipName',
                                            value: 'equipName',
                                            linkageFields:{
                                                outerEquipID:'id'
                                            }
                                        },
                                        parent: 'rentOrgID',
                                        dropdownMatchSelectWidth: 800,
                                        qnnTableConfig: {
                                            antd: {
                                                rowKey: "id"
                                            },
                                            fetchConfig: {
                                                apiName: 'getZxEqOuterEquipListForRecord',
                                                otherParams: (obj) => {
                                                    let orgID = obj?.props?.qnnFormProps?.form?.getFieldValue?.('orgID');
                                                    let rentOrgName = obj?.props?.qnnFormProps?.form?.getFieldValue?.('rentOrgName');
                                                    return {
                                                        orgID: orgID,
                                                        supplierID: rentOrgName
                                                    }
                                                }
                                            },
                                            searchBtnsStyle: "inline",
                                            formConfig: [
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "equipName",
                                                        title: "????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                },
                                                {
                                                    isInForm: false,
                                                    isInSearch: true,
                                                    table: {
                                                        dataIndex: "equipNo",
                                                        title: "????????????",
                                                        width: 100
                                                    },
                                                    form: {
                                                        type: 'string'
                                                    }
                                                }
                                            ]
                                        },
                                        condition: [
                                            {
                                                regex: {
                                                    rentOrgID: ['=', /(''|null|undefined)/ig]
                                                },
                                                action: 'disabled'
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'spec',
                                        key: 'spec'
                                    },
                                    form: {
                                        type: 'string',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '????????????',
                                        dataIndex: 'busDate',
                                        key: 'busDate',
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        required: true,
                                        placeholder: '?????????',
                                        spanForm: 8
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'status',
                                        key: 'status',
                                        render: (data) => {
                                            if (data === '' || data === '0') {
                                                return '?????????';
                                            } else if (data === '1') {
                                                return '?????????';
                                            }
                                        }
                                    },
                                    form: {
                                        type: 'string',
                                        hide: true,
                                        placeholder: '?????????'
                                    }
                                },
                                {
                                    table: {
                                        title: '??????',
                                        dataIndex: 'remark',
                                        key: 'remark',
                                        width: 150,
                                    },
                                    form: {
                                        type: 'textarea',
                                        placeholder: '?????????',
                                        spanForm: 21,
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
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        type: 'qnnTable',
                                        field: 'itemList',
                                        incToForm: true,
                                        qnnTableConfig: {
                                            actionBtnsPosition: "top",
                                            antd: {
                                                rowKey: 'id',
                                                size: 'small'
                                            },
                                            paginationConfig: {
                                                position: 'bottom'
                                            },
                                            formConfig: [
                                                {
                                                    isInTable: false,
                                                    form: {
                                                        label: '??????id',
                                                        field: 'id',
                                                        hide: true
                                                    }
                                                },
                                                {
                                                    table: {
                                                        title: '??????',
                                                        dataIndex: 'index',
                                                        key: 'index',
                                                        width: 80,
                                                        fixed: 'left',
                                                        render: (data, rowData, index) => {
                                                            return index + 1;
                                                        }
                                                    },
                                                    isInForm: false
                                                },
                                                {
                                                    table: {
                                                        title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
                                                        dataIndex: 'useDate',
                                                        key: 'useDate',
                                                        width: 150,
                                                        fixed: 'left',
                                                        tdEdit: true,
                                                        format: 'YYYY-MM-DD'
                                                    },
                                                    form: {
                                                        type: 'date',
                                                        required: true,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'content',
                                                        key: 'content',
                                                        width: 150,
                                                        fixed: 'left',
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'place',
                                                        key: 'place',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'unit',
                                                        key: 'unit',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '?????????????????????',
                                                        dataIndex: 'qty',
                                                        key: 'qty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '?????????',
                                                        dataIndex: 'mile',
                                                        key: 'mile',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'calendarNumDay',
                                                        key: 'calendarNumDay',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'wellDays',
                                                        key: 'wellDays',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'runDay',
                                                        key: 'runDay',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'actualQty',
                                                        key: 'actualQty',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'waitQty',
                                                        key: 'waitQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'weatherQty',
                                                        key: 'weatherQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '??????????????????',
                                                        dataIndex: 'problemQty',
                                                        key: 'problemQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????(???)',
                                                        dataIndex: 'gasQty',
                                                        key: 'gasQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????(???)',
                                                        dataIndex: 'dervQty',
                                                        key: 'dervQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '?????????(??????)',
                                                        dataIndex: 'coalQty',
                                                        key: 'coalQty',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '?????????(???)',
                                                        dataIndex: 'consumption',
                                                        key: 'consumption',
                                                        width: 150,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'number',
                                                        min: 0,
                                                        placeholder: '?????????',
                                                    },
                                                },
                                                {
                                                    table: {
                                                        title: '????????????',
                                                        dataIndex: 'driverName',
                                                        key: 'driverName',
                                                        width: 100,
                                                        tdEdit: true
                                                    },
                                                    form: {
                                                        type: 'string',
                                                        placeholder: '?????????',
                                                    },
                                                },
                                            ],
                                            actionBtns: [
                                                {
                                                    name: "addRow",
                                                    icon: "plus",
                                                    type: "primary",
                                                    label: "?????????"
                                                },
                                                {
                                                    name: 'del',
                                                    icon: 'delete',
                                                    type: 'danger',
                                                    label: '??????'
                                                }
                                            ]
                                        }
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        label: "??????",
                                        field: "fileList",
                                        type: "files",
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: "upload"
                                        },
                                        spanForm: 21,
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
                                    }
                                }
                            ]}
                        />
                    </TabPane>
                </Tabs>
                {visible ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="??????"
                        visible={visible}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 6 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 18 },
                                    sm: { span: 18 }
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'companyId',
                                    type: 'string',
                                    initialValue: companyId,
                                    hide: true,
                                },
                                {
                                    field: 'orgName',
                                    type: 'string',
                                    hide: true,
                                },
                                {
                                    field: 'rentOrgName',
                                    type: 'string',
                                    hide: true,
                                },
                                {
                                    label: '??????????????????',
                                    field: 'fileList',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: 'upload'
                                    },
                                    accept: '.xls,.xlsx'
                                },
                                {
                                    label: '????????????',
                                    field: 'companyName',
                                    type: 'string',
                                    hide: true,
                                    initialValue: companyName,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '????????????',
                                    field: 'billNo',
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '????????????',
                                    field: 'orgID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId',
                                        linkageFields: {
                                            orgName: 'departmentName',
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysCompanyProject',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                    allowClear: false,
                                    showSearch: true,
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '????????????',
                                    field: 'rentOrgID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'secondName',
                                        value: 'secondID',
                                        linkageFields: {
                                            rentOrgName: 'secondName',
                                        }
                                    },
                                    parent: 'orgID',
                                    fetchConfig: {
                                        apiName: 'getZxEqOuterEquipListForRecord',
                                        params: {
                                            orgID: 'orgID'
                                        }
                                    },
                                    condition: [
                                        {
                                            regex: {
                                                orgID: ['=', /(''|null|undefined)/ig]
                                            },
                                            action: 'disabled'
                                        }
                                    ],
                                    allowClear: false,
                                    showSearch: true,
                                    placeholder: '?????????',
                                    spanForm: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'outerEquipID',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'equipName',
                                        value: 'id'
                                    },
                                    parent: 'rentOrgID',
                                    fetchConfig: {
                                        apiName: 'getZxEqOuterEquipListForRecord',
                                        otherParams: (obj) => {
                                            let orgID = obj?.form?.getFieldValue?.('orgID');
                                            let rentOrgName = obj?.form?.getFieldValue?.('rentOrgName');
                                            return {
                                                orgID: orgID,
                                                supplierID: rentOrgName
                                            }
                                        }
                                    },
                                    condition: [
                                        {
                                            regex: {
                                                rentOrgID: ['=', /(''|null|undefined)/ig]
                                            },
                                            action: 'disabled'
                                        }
                                    ],
                                    allowClear: false,
                                    showSearch: true,
                                    placeholder: '?????????',
                                    spanForm: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'spec',
                                    type: 'string',
                                    placeholder: '?????????'
                                },
                                {
                                    label: '????????????',
                                    field: 'busDate',
                                    type: 'date',
                                    placeholder: '?????????'
                                },
                                {
                                    label: '??????',
                                    field: 'remark',
                                    type: 'textarea',
                                    placeholder: '?????????'
                                },
                                {
                                    field: 'component',
                                    type: 'component',
                                    Component: () => {
                                        return <div style={{ textAlign: 'center' }}><Button type="primary">??????????????????</Button></div>
                                    }
                                },
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
                                    field: 'submit',
                                    onClick: (obj) => {
                                        obj.btnfns.fetchByCb('importZxEqRentOutEqRecordList', obj.values, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.table2.refresh();
                                                this.setState({ visible: false });
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;