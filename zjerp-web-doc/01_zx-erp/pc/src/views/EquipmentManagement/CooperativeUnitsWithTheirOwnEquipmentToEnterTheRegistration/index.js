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
                                content: '确定下载二维码吗?',
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
                                title: '公司名称',
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
                                title: '编制单位',
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
                                label: '编制单位',
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
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '合同编号',
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
                                                title: "合同编号",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            table: {
                                                dataIndex: "contractName",
                                                title: "合同名称",
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
                                title: '合同名称',
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
                                                title: "合同编号",
                                            },
                                            form: {
                                                type: "string"
                                            }
                                        },
                                        {
                                            table: {
                                                dataIndex: "contractName",
                                                title: "合同名称",
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
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '劳务队伍',
                                dataIndex: 'subUnitName',
                                key: 'subUnitName',
                                width: 100,
                                filter: true
                            },
                            form: {
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                title: '编制人',
                                dataIndex: 'editMan',
                                key: 'editMan',
                                width: 100,
                            },
                            form: {
                                type: 'string',
                                initialValue: realName,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '数量',
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
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '设备名称',
                                                field: 'equipID',
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '设备编号',
                                                field: 'equipNo',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '序号',
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
                                                title: "<div>设备名称<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'equipName',
                                                key: 'equipName',
                                                width: 200,
                                                fixed: 'left',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                optionConfig: {//下拉选项配置
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
                                                                title: "编号",
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
                                                                title: "名称",
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
                                                placeholder: '请选择',
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>型号<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'model',
                                                key: 'model',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>规格<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'spec',
                                                key: 'spec',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>功率(KW)<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'power',
                                                key: 'power',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>生产厂家<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'outfactory',
                                                key: 'outfactory',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>出产日期<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'outfactoryDate',
                                                key: 'outfactoryDate',
                                                width: 150,
                                                tdEdit: true,
                                                format: 'YYYY-MM-DD'
                                            },
                                            form: {
                                                type: 'date',
                                                required: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '验收总体评价',
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
                                                        label: "不合格",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "合格",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '是否退场',
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
                                                        label: "否",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '是否特种设备',
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
                                                        label: "否",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '特种设备检验报告',
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
                                                        label: "无",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "有",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '特种设备使用登记证',
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
                                                        label: "无",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "有",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '操作人员证',
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
                                                        label: "无",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "有",
                                                        value: "1"
                                                    }
                                                ],
                                                allowClear: false,
                                                showSearch: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>原值(元)<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'orginalValue',
                                                key: 'orginalValue',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                min: 0,
                                                required: true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>新旧程度<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'oldrate',
                                                key: 'oldrate',
                                                width: 100,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                placeholder: '请输入',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>使用开始时间<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'beginDate',
                                                key: 'beginDate',
                                                width: 150,
                                                tdEdit: true,
                                                format: 'YYYY-MM-DD'
                                            },
                                            form: {
                                                type: 'date',
                                                required: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: "<div>使用结束时间<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'endDate',
                                                key: 'endDate',
                                                width: 150,
                                                tdEdit: true,
                                                format: 'YYYY-MM-DD'
                                            },
                                            form: {
                                                type: 'date',
                                                required: true,
                                                placeholder: '请选择',
                                            },
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remark',
                                                key: 'remark',
                                                width: 150,
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                            },
                                        },
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增行"
                                        },
                                        {
                                            name: 'del',
                                            icon: 'delete',
                                            type: 'danger',
                                            label: '删除'
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark',
                                width: 150,
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入',
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
                        title="导入"
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
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
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
                                    label: '导入数据文件',
                                    field: 'fileList',
                                    type: 'files',
                                    required: true,
                                    fetchConfig: {
                                        apiName: 'upload'
                                    },
                                    accept: '.xls,.xlsx'
                                },
                                {
                                    label: '公司名称',
                                    field: 'comName',
                                    type: 'string',
                                    hide: true,
                                    placeholder: '请输入'
                                },
                                {
                                    label: '编制人',
                                    field: 'editMan',
                                    type: 'string',
                                    hide: true,
                                    initialValue: '测试',
                                    placeholder: '请输入'
                                },
                                {
                                    label: '合同ID',
                                    field: 'contractID',
                                    type: 'string',
                                    hide: true,
                                    placeholder: '请输入'
                                },
                                {
                                    label: '编制单位',
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
                                    placeholder: '请选择',
                                    spanForm: 8
                                },
                                {
                                    label: '合同编号',
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
                                    placeholder: '请选择'
                                },
                                {
                                    label: '合同名称',
                                    field: 'contractName',
                                    type: 'string',
                                    disabled: true,
                                    required: true,
                                    placeholder: '请输入'
                                },
                                {
                                    label: '劳务队伍',
                                    field: 'subUnitName',
                                    type: 'string',
                                    disabled: true,
                                    required: true,
                                    placeholder: '请输入'
                                },
                                {
                                    field: 'component',
                                    type: 'component',
                                    Component: () => {
                                        return <div style={{ textAlign: 'center' }}><Button type="primary" onClick={() => {
                                            confirm({
                                                content: '确定下载模板吗?',
                                                onOk: () => {
                                                    window.open(`${domain}system-server/downloadFile?filePath=import/【协作队伍自带设备】模块导入模板.xls&downName=【协作队伍自带设备】模块导入模板.xls`);
                                                }
                                            });
                                        }}>导入模板下载</Button></div>
                                    }
                                },
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
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