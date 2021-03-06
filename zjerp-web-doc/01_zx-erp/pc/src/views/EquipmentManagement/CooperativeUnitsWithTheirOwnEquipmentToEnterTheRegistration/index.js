import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import s from "./style.less";
import { message as Msg, Modal, Button, Avatar } from "antd";
const { confirm, info } = Modal;
const config = {
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
            xs: { span: 11 },
            sm: { span: 11 }
        },
        wrapperCol: {
            xs: { span: 13 },
            sm: { span: 13 }
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
            visible: false
        }
    }
    render () {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { visible, departmentId } = this.state;
        let { myPublic: { domain } } = this.props;
        return (
            <div className={s.root}>
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
                        apiName: 'getZxEqCooEquipList',
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
                                tableField: "CooperativeUnitsWithTheirOwnEquipmentToEnterTheRegistration"
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
                                field: 'orgName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'contractName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'comName',
                                key: 'comName',
                                filter: true,
                                width: 150,
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '????????????',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                width: 150,
                                tooltip: 15,
                                fixed: 'left',
                                filter: true,
                                type:'select',
                                fieldsConfig: {
                                    type: 'select',
                                    field: 'selectOrgID',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '????????????',
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
                                    apiName: 'getSysProjectBySelect',
                                    otherParams: {
                                        departmentId: departmentId
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractID',
                                key: 'contractID',
                                width: 100,
                                filter: true,
                                type: 'selectByQnnTable',
                            },
                            form: {
                                type: 'selectByQnnTable',
                                optionConfig: {
                                    label: 'contractNo',
                                    value: 'ctContractId'
                                },
                                dropdownMatchSelectWidth: 600,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: "ctContractId"
                                    },
                                    fetchConfig: {
                                        apiName: 'sbglGetZxGcsgCtContractSelect',
                                        otherParams: (obj) => {
                                            let rowData = obj?.props?.qnnFormProps?.form?.getFieldsValue?.();
                                            return {
                                                projectId: rowData?.orgID,
                                                companyId: rowData?.comID
                                            }
                                        }
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig: [
                                        {
                                            table: {
                                                dataIndex: "contractNo",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            table: {
                                                dataIndex: "contractName",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        }
                                    ]
                                },
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'contractID',
                                key: 'contractID',
                                width: 100,
                                filter: true,
                                type:'selectByQnnTable'
                            },
                            form: {
                                type: 'selectByQnnTable',
                                optionConfig: {
                                    label: 'contractName',
                                    value: 'ctContractId',
                                    linkageFields: {
                                        contractName: 'contractName',
                                        contractNo: 'contractNo',
                                        subUnitID: "secondID",
                                        subUnitName: 'secondName'
                                    }
                                },
                                dropdownMatchSelectWidth: 600,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: "ctContractId"
                                    },
                                    fetchConfig: {
                                        apiName: 'sbglGetZxGcsgCtContractSelect',
                                        otherParams: (obj) => {
                                            let rowData = obj?.props?.qnnFormProps?.form?.getFieldsValue?.();
                                            return {
                                                projectId: rowData?.orgID,
                                                companyId: rowData?.comID
                                            }
                                        }
                                    },
                                    searchBtnsStyle: "inline",
                                    formConfig: [
                                        {
                                            table: {
                                                dataIndex: "contractNo",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            table: {
                                                dataIndex: "contractName",
                                                title: "????????????",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        }
                                    ]
                                },
                                parent: "orgID",
                                condition: [
                                    {
                                        regex: { orgID: ['=', /(''|null|undefined)/ig] },
                                        action: 'disabled',
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                required: true,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'subUnitName',
                                key: 'subUnitName',
                                width: 100,
                                filter: true
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
                            isInTable: false,
                            form: {
                                field: 'subUnitID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'editMan',
                                key: 'editMan',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                initialValue: realName,
                                placeholder: '?????????',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'limit',
                                key: 'limit',
                                width: 100,
                                render: (data, rowData) => {
                                    if (rowData?.itemList?.length) {
                                        return rowData.itemList.length;
                                    } else {
                                        return '0';
                                    }
                                }
                            },
                            isInForm: false
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
                                    wrappedComponentRef: (me) => {
                                        this.tableOne = me;
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
                                            isInTable: false,
                                            form: {
                                                label: '????????????',
                                                field: 'equipID',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '????????????',
                                                field: 'equipNo',
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
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'equipName',
                                                key: 'equipName',
                                                width: 200,
                                                fixed: 'left',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                optionConfig: {//??????????????????
                                                    label: 'catName',
                                                    value: 'catName'
                                                },
                                                dropdownMatchSelectWidth: 800,
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    fetchConfig: {
                                                        apiName: "getZxEqResCategoryItemList",
                                                        otherParams: {
                                                            isGroup: '1'
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "catCode",
                                                                title: "??????",
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
                                                                title: "??????",
                                                                width: 100
                                                            },
                                                            form: {
                                                                type: 'string'
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (val, obj, btnCallbackFn) => {
                                                    btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                        editRowData.equipID = obj.itemData.id;
                                                        editRowData.equipNo = obj.itemData.catCode;
                                                        editRowData.equipName = obj.itemData.catName;
                                                        editRowData.spec = obj.itemData.spec;
                                                        editRowData.model = obj.itemData.unit;
                                                        btnCallbackFn.setEditedRowData({ ...editRowData });
                                                    });
                                                },
                                                allowClear: false,
                                                showSearch: true,
                                                required: true,
                                                placeholder: '?????????',
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'model',
                                                key: 'model',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>??????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>??????(KW)<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'power',
                                                key: 'power',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'outfactory',
                                                key: 'outfactory',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'outfactoryDate',
                                                key: 'outfactoryDate',
                                                width: 150,
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
                                                title: '??????????????????',
                                                dataIndex: 'acceptance',
                                                key: 'acceptance',
                                                width: 150,
                                                tdEdit: true,
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
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                dataIndex: 'isOut',
                                                key: 'isOut',
                                                width: 150,
                                                tdEdit: true,
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
                                            },
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'isSepcial',
                                                key: 'isSepcial',
                                                width: 150,
                                                tdEdit: true,
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
                                            },
                                        },
                                        {
                                            table: {
                                                title: '????????????????????????',
                                                dataIndex: 'inspReport',
                                                key: 'inspReport',
                                                width: 150,
                                                tdEdit: true,
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
                                            },
                                        },
                                        {
                                            table: {
                                                title: '???????????????????????????',
                                                dataIndex: 'inspCert',
                                                key: 'inspCert',
                                                width: 150,
                                                tdEdit: true,
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
                                            },
                                        },
                                        {
                                            table: {
                                                title: '???????????????',
                                                dataIndex: 'opCert',
                                                key: 'opCert',
                                                width: 150,
                                                tdEdit: true,
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
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>??????(???)<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'orginalValue',
                                                key: 'orginalValue',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                min: 0,
                                                required: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'oldrate',
                                                key: 'oldrate',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '?????????',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>??????????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'beginDate',
                                                key: 'beginDate',
                                                width: 150,
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
                                                title: "<div>??????????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'endDate',
                                                key: 'endDate',
                                                width: 150,
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
                                                title: '??????',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                width: 150,
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
                                        xs: { span: 4 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 20 },
                                        sm: { span: 20 }
                                    }
                                }
                            }
                        }
                    ]}
                />
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
                                    field: 'comID',
                                    type: 'string',
                                    hide: true
                                },
                                {
                                    field: 'orgName',
                                    type: 'string',
                                    hide: true
                                },
                                {
                                    field: 'subUnitID',
                                    type: 'string',
                                    hide: true
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
                                    field: 'comName',
                                    type: 'string',
                                    hide: true,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '?????????',
                                    field: 'editMan',
                                    type: 'string',
                                    hide: true,
                                    initialValue: '??????',
                                    placeholder: '?????????'
                                },
                                {
                                    label: '??????ID',
                                    field: 'contractID',
                                    type: 'string',
                                    hide: true,
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
                                            comID: 'companyId',
                                            comName: 'companyName'
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: departmentId
                                        }
                                    },
                                    allowClear: false,
                                    showSearch: true,
                                    required: true,
                                    placeholder: '?????????',
                                    spanForm: 8
                                },
                                {
                                    label: '????????????',
                                    field: 'contractNo',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'contractNo',
                                        value: 'contractNo',
                                        linkageFields: {
                                            contractName: 'contractName',
                                            contractID: 'ctContractId',
                                            subUnitID: "secondID",
                                            subUnitName: 'secondName'
                                        }
                                    },
                                    fetchConfig: {
                                        apiName: 'sbglGetZxGcsgCtContractSelect',
                                        otherParams: (obj) => {
                                            let rowData = obj.form.getFieldsValue();
                                            return {
                                                projectId: rowData.orgID,
                                                companyId: rowData.comID
                                            }
                                        }
                                    },
                                    parent: "orgID",
                                    condition: [
                                        {
                                            regex: { orgID: ['=', /(''|null|undefined)/ig] },
                                            action: 'disabled',
                                        }
                                    ],
                                    allowClear: false,
                                    showSearch: true,
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '????????????',
                                    field: 'contractName',
                                    type: 'string',
                                    disabled: true,
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    label: '????????????',
                                    field: 'subUnitName',
                                    type: 'string',
                                    disabled: true,
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    field: 'component',
                                    type: 'component',
                                    Component: () => {
                                        return <div style={{ textAlign: 'center' }}><Button type="primary" onClick={() => {
                                            confirm({
                                                content: '??????????????????????',
                                                onOk: () => {
                                                    window.open(`${domain}system-server/downloadFile?filePath=import/????????????????????????????????????????????????.xls&downName=????????????????????????????????????????????????.xls`);
                                                }
                                            });
                                        }}>??????????????????</Button></div>
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
                                        obj.btnfns.fetchByCb('importZxEqCooEquipList', obj.values, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.table.refresh();
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