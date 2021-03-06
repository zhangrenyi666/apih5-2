import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg, Modal } from 'antd';
import ProgressCheck from './form';
import DeetailForm from './deetailForm';
import Operation from './operation';
const confirm = Modal.confirm;
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
    firstRowIsSearch: false,
    isShowRowSelect: true
};
const configItem = {
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: 0,
            limitNo: props.match.params.limitNo,
            lockProjectId: props.loginAndLogoutInfo.loginInfo.userInfo.lockProject && props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId ? props.loginAndLogoutInfo.loginInfo.userInfo.lockProject.projectId : ''
        }
    }
    componentDidMount() {
    }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }

    getTzNumber(val, rowData) {
        const { myFetch } = this.props;
        myFetch('getZxSkLimitPriceAdjustNo', { limitNo: val }).then(
            ({ data, success, message }) => {
                if (success) {
                    Msg.success(message);
                    // if (this.table?.qnnForm?.form) {
                    rowData.fns.setValues({
                        adjustNo: data
                    })
                    // }
                } else {
                    Msg.warn(message);
                    // if (this.table?.qnnForm?.form) {
                    rowData.fns.setValues({
                        adjustNo: null
                    })
                    // }
                }
            }
        );
    }
    render() {
        const { ext1, departmentId, companyId, projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { lockProjectId, limitNo } = this.state;
        let jurisdiction = departmentId;
        if (lockProjectId !== 'all' && lockProjectId !== '') {
            jurisdiction = lockProjectId;
        } else {
            if (ext1 === '1' || ext1 === '2') {
                jurisdiction = companyId;
            } else if (ext1 === '3' || ext1 === '4') {
                jurisdiction = projectId;
            } else {

            }
        }
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZxSkLimitPriceAdjustList',
                        otherParams: {
                            limitNo: limitNo === '0' ? '' : limitNo,
                            projectId: jurisdiction
                        }
                    }}
                    {...config}
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
                                field: 'orgID',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'workId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'comName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                // tooltip: 23,
                                onClick: 'detail',
                                dataIndex: 'adjustNo',
                                key: 'adjustNo',
                                filter: true
                            },
                            form: {
                                field: 'adjustNo',
                                type: 'string',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'orgName',
                                key: 'orgName',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'orgName',
                                addDisabled: true,
                                editDisabled: true,
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                width: 200,
                                dataIndex: 'projectId',
                                key: 'projectId',
                                type: 'select',
                                filter: true,
                                fieldsConfig: {
                                    field: 'orgID2',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'departmentName',
                                        value: 'departmentId'
                                    },
                                    fetchConfig: {
                                        apiName: 'getSysProjectBySelect',
                                        otherParams: {
                                            departmentId: jurisdiction
                                        }
                                    },
                                }
                            },
                            form: {
                                field: 'projectId',
                                required: true,
                                type: 'select',
                                showSearch: true,
                                editDisabled: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields: {
                                        "comName": "companyName",//??????
                                        comID: 'companyId',
                                        orgName: 'companyName',
                                        orgID: 'companyId',
                                        projectName:'orgName'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractListByOrgId',
                                    otherParams: {
                                        contrStatus: "1",
                                        orgID: jurisdiction
                                    }
                                },
                                placeholder: '?????????',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                },
                                onChange: (val, obj) => {

                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.clearValues(['zxSkLimitPriceAdjustItemList']);
                                        this.table.qnnForm.form.setFieldsValue({
                                            limitNo: '',
                                            province: '',
                                            projectType: '',
                                            periodDate: ''
                                        })

                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '????????????',
                                width: 200,
                                // tooltip: 23,
                                dataIndex: 'limitNo',
                                key: 'limitNo',
                                filter: true
                            },
                            form: {
                                field: 'limitNo',
                                type: 'select',
                                required:true,
                                editDisabled: true,
                                allowClear: false,
                                optionConfig: {
                                    label: 'limitNo',
                                    value: 'limitNo',
                                    linkageFields: {
                                        "province": "province",
                                        "projectType": "projectType",
                                        "periodDate": "periodDate"
                                    }
                                },
                                parent: 'projectId',
                                fetchConfig: {
                                    apiName: 'getZxSkLimitPriceByLimitNoList',
                                    otherParams: {
                                        apih5FlowStatus: "2"
                                    },
                                    params: {
                                        projectId: 'projectId',
                                    }
                                },
                                placeholder: '?????????',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                },
                                onChange: (val, rowData) => {
                                    this.getTzNumber(val, rowData);
                                    if (rowData.itemData.zxSkLimitPriceItemList) {
                                        let selectData = rowData.itemData.zxSkLimitPriceItemList;
                                        rowData.fns.setValues(
                                            {
                                                zxSkLimitPriceAdjustItemList: selectData.map((item) => {
                                                    item.adjustType = '2';
                                                    return item
                                                }),
                                            }
                                        )
                                        // if (this.table?.qnnForm?.form) {
                                        //     this.table.qnnForm.clearValues(['zxSkLimitPriceAdjustItemList']);
                                        //     this.table.qnnForm.form.setFieldsValue({
                                        //         zxSkLimitPriceAdjustItemList: selectData.map((item) => {
                                        //             item.adjustType = '2';
                                        //             return item
                                        //         }),
                                        //     })
                                        // }
                                    }
                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'projectName',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'province',
                                key: 'province'
                            },
                            form: {
                                field: 'province',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'projectType',
                                key: 'projectType'
                            },
                            form: {
                                field: 'projectType',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                render: (data) => {
                                    return data ? (moment(data).format('YYYY') + '/' + (moment(data).format('MM') === '12' ? '?????????' : '?????????')) : ''
                                },
                                filter: true
                            },
                            form: {
                                field: 'periodDate',
                                type: 'halfYear',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '?????????',
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            },
                        },
                        {
                            table: {
                                title: '?????????',
                                width: 120,
                                dataIndex: 'perpare',
                                key: 'perpare'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                field: 'perpare',
                                initialValue: () => {
                                    return this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '????????????',
                                width: 120,
                                dataIndex: 'prepareDate',
                                format: 'YYYY-MM-DD',
                                key: 'prepareDate',
                                filter: true
                            },
                            form: {
                                type: 'date',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                field: 'prepareDate',
                                initialValue: () => {
                                    return moment(new Date()).format('YYYY-MM-DD')
                                },
                                spanForm: 8,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 16 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                width: 100,
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                render: (data) => {
                                    if (data === '0') {
                                        return '?????????';
                                    } else if (data === '1') {
                                        return '?????????';
                                    } else if (data === '2') {
                                        return '????????????';
                                    } else if (data === '3') {
                                        return '??????';
                                    } else if (data === '-1') {
                                        return '?????????';
                                    } else {
                                        return '--';
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                field: 'apih5FlowStatus',
                                hide: true
                            }
                        },


                        {
                            isInTable: false,
                            form: {
                                type: 'qnnTable',
                                field: 'zxSkLimitPriceAdjustItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
                                    wrappedComponentRef: (me) => {
                                        this.tableItem = me;
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
                                                title: '????????????',
                                                dataIndex: 'adjustType',
                                                width: 120,
                                                key: 'adjustType',
                                                type: 'select',
                                            },
                                            form: {
                                                field: 'adjustType',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'label',
                                                    value: 'value',
                                                },
                                                optionData: [
                                                    {
                                                        label: "??????",//1??????  2??????---2021/2/1??????
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "??????",
                                                        value: "2"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'resourceId',
                                                width: 200,
                                                key: 'resourceId',
                                                type: 'select',
                                                tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.adjustType === "2") {
                                                            return true
                                                        }
                                                        return false;
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'selectByPaging',
                                                field: 'resourceId',
                                                required: true,
                                                optionConfig: {
                                                    label: 'catName',
                                                    value: 'id'
                                                },
                                                fetchConfig: {
                                                    apiName: "getZxSkResCategoryMaterialsListResource",
                                                    otherParams: (val) => {
                                                        let vals = val.form.getFieldsValue();
                                                        return {
                                                            parentOrgID: vals.projectId,
                                                        }
                                                    }
                                                },
                                                onChange: (val, thisProps, btnCallbackFn) => {
                                                    if (val) {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            editRowData.resourceId = val;
                                                            const itemData = thisProps;
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resourceNo: itemData.itemData.catCode,
                                                                resourceName: itemData.itemData.catName,
                                                                workNo: '',
                                                                workName: '',
                                                                spec: '',
                                                                unit: '',
                                                                price: null,
                                                                adjustPrice: null,
                                                                prepareDate: undefined
                                                            });
                                                        })
                                                    } else {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resourceNo: '',
                                                                resourceName: '',
                                                                workNo: '',
                                                                workName: '',
                                                                spec: '',
                                                                unit: '',
                                                                price: null,
                                                                adjustPrice: null,
                                                                prepareDate: undefined
                                                            });
                                                        })
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                width: 150,
                                                tooltip: 23,
                                                dataIndex: 'resourceNo',
                                                key: 'resourceNo'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'resourceNo'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'resourceName'
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'workNo',
                                                width: 180,
                                                key: 'workNo',
                                                tdEdit: true,
                                                fieldConfig: {
                                                    disabled: ({ record }) => {
                                                        if (record.adjustType === "2") {
                                                            return true
                                                        }
                                                        return false;
                                                    },
                                                }
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                allowClear: false,
                                                required: true,
                                                field: 'workNo',
                                                dependenciesReRender: true,//????????????-??????
                                                dependencies: ['resourceId'],
                                                // parent: 'resourceId',
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },

                                                dropdownMatchSelectWidth: 900,
                                                qnnTableConfig: {
                                                    antd: {
                                                        rowKey: "id"
                                                    },
                                                    firstRowIsSearch: false,
                                                    fetchConfig: {
                                                        apiName: "getZxSkResourceMaterialsListNameJoinResource",
                                                        otherParams: (obj) => {
                                                            const rowData2 = obj.qnnFormProps?.qnnformData?.qnnFormProps?.qnnTableInstance?.getEditedRowDataSync?.();
                                                            return {
                                                                id: rowData2 ? rowData2.resourceId : '',
                                                            }
                                                        }
                                                    },
                                                    searchBtnsStyle: "inline",
                                                    formConfig: [
                                                        {
                                                            isInForm: false,
                                                            isInTable: false,
                                                            form: {
                                                                field: 'id',
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resCode",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "resName",
                                                                title: "??????",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "spec",
                                                                title: "????????????"
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            isInSearch: true,
                                                            table: {
                                                                dataIndex: "unit",
                                                                title: "??????"
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        },
                                                        {
                                                            isInForm: false,
                                                            // isInSearch: true,
                                                            table: {
                                                                dataIndex: "refpriceType",
                                                                title: "????????????"
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (val, thisProps, btnCallbackFn) => {
                                                    if (val) {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            const itemData = thisProps;
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                workNo: itemData.itemData.resCode,
                                                                workName: itemData.itemData.resName,
                                                                workId: itemData.itemData.id,
                                                                unit: itemData.itemData.unit,
                                                                spec: itemData.itemData.spec,
                                                                price: itemData.itemData.price
                                                            });
                                                        })
                                                    } else {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                workName: '',
                                                                workId: '',
                                                                unit: '',
                                                                spec: '',
                                                                price: 0
                                                            });
                                                        })
                                                    }

                                                },
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 150,
                                                tooltip: 23,
                                                dataIndex: 'workName',
                                                key: 'workName'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'workName'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 130,
                                                dataIndex: 'spec',
                                                key: 'spec'
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'spec'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????',
                                                dataIndex: 'unit',
                                                key: 'unit',
                                                width: 80,
                                            },
                                            form: {
                                                type: 'string',
                                                field: 'unit'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '??????????????????',
                                                dataIndex: 'price',
                                                width: 120,
                                                key: 'price'
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'price',
                                                precision: 6
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>??????????????????<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'adjustPrice',
                                                width: 120,
                                                key: 'adjustPrice',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'adjustPrice',
                                                precision: 6,
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '????????????',
                                                width: 120,
                                                dataIndex: 'prepareDate',
                                                format: 'YYYY-MM-DD',
                                                key: 'prepareDate',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'date',
                                                field: 'prepareDate'
                                            }
                                        },

                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "??????",
                                            addRowDefaultData: () => {
                                                return {
                                                    adjustType: '1'
                                                }
                                            }

                                        },
                                        // {
                                        //     name: "del",
                                        //     icon: 'delete',
                                        //     type: 'danger',
                                        //     label: "??????",
                                        // },
                                        {
                                            name: "Diydel",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "??????",
                                            disabled: "bind:_actionBtnNoSelected",
                                            onClick: (obj) => {
                                                let allData = this.table.qnnForm.form.getFieldsValue().zxSkLimitPriceAdjustItemList;
                                                let selectDa = obj.selectedRows;
                                                let undelDta = [];
                                                let selectRowData = [];
                                                for (let m = 0; m < selectDa.length; m++) {
                                                    if (selectDa[m].adjustType === '2') {
                                                        undelDta.push(selectDa[m].adjustType);
                                                    }
                                                }
                                                if (undelDta.length > 0) {
                                                    Msg.warn('?????????????????????????????????????????????????????????');
                                                    this.tableItem.clearSelectedRows();
                                                } else {
                                                    confirm({
                                                        content: '???????????????????????????????',
                                                        onOk: () => {
                                                            let obj = {};
                                                            selectDa.forEach(function (item, index) {
                                                                obj[item.id] = true //?????????arr2?????????????????????obj ??????????????????true???
                                                            })
                                                            allData.forEach(function (item, index) {
                                                                if (!obj[item.id]) {
                                                                    selectRowData.push(item)
                                                                } else {
                                                                }
                                                            })
                                                            if (this.table && this.table.qnnForm) {
                                                                this.table.qnnForm.form.setFieldsValue({
                                                                    zxSkLimitPriceAdjustItemList: selectRowData
                                                                });
                                                                this.tableItem.clearSelectedRows();
                                                            }
                                                        }
                                                    });

                                                }

                                            }
                                        }
                                    ]
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'fileList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: 'upload'
                                },
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 3 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 21 }
                                    }
                                }
                            }
                        }
                    ]}
                    method={{
                        onClickFunBack: (obj) => {
                            const { mainModule } = obj.props.myPublic.appInfo;
                            obj.props.dispatch(
                                push(`${mainModule}ImportPriceLimitManage`)
                            )
                        },
                        hideFunBack: (val) => {
                            if (limitNo === '0') {
                                return true
                            } else {
                                return false
                            }
                        },
                        disabledFunEdit: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                                return true;
                            } else {
                                return false;
                            }
                        },
                        willExecuteFunEdit: (obj) => {
                            if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                obj.btnCallbackFn.clearSelectedRows();
                                Msg.warn('???????????????????????????!');
                                return false
                            } else {

                            }
                            obj.btnCallbackFn.clearSelectedRows();
                        },
                        disabledFunCom: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && data[0].apih5FlowStatus === '-1') {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunDetail: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length === 1 && (data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2')) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunJindu: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            //??????????????????????????????????????????????????????32???????????????????????????????????????32??????????????????????????????
                            if (data.length === 1 && (data[0].apih5FlowStatus !== '-1' && data[0].id.length <= 32)) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        disabledFunDel: (obj) => {
                            let data = obj.btnCallbackFn.getSelectedRows();
                            if (data.length > 0) {
                                for (let m = 0; m < data.length; m++) {
                                    if (data[m].apih5FlowStatus === '1' || data[m].apih5FlowStatus === '2' || data[m].id.length > 32) {
                                        return true;
                                    }
                                }
                                return false
                            } else {
                                return true;
                            }
                        },
                        onClickFunDel: (obj) => {
                            const { myFetch } = this.props;
                            let arry = [];
                            for (let m = 0; m < obj.selectedRows.length; m++) {
                                if (obj.selectedRows[m].apih5FlowStatus === '1' || obj.selectedRows[m].apih5FlowStatus === '2' || obj.selectedRows[m].id.length > 32) {
                                    //????????????????????????
                                    arry.push(obj.selectedRows[m].apih5FlowStatus);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('??????????????????????????????')
                            } else {
                                confirm({
                                    content: '???????????????????????????????',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxSkLimitPriceAdjust', obj.selectedRows).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    this.table.refresh();
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                } else {
                                                }
                                            }
                                        );
                                    }
                                });
                            }
                        }
                    }}
                    componentsKey={{
                        ProgressCheckChange: (obj) => {
                            return <ProgressCheck {...obj} flowData={obj.selectedRows[0]} />
                        },
                        DeetailFormChange: (obj) => {
                            return <DeetailForm {...obj} flowData={obj.selectedRows[0]} />
                        },
                        OperationJindu: (props) => {
                            return <Operation apiName={'openFlowByReport'} detailApiName={'getZxSkLimitPriceAdjustDetails'} flowId={'zxSkLimitPriceAdjustWorkId'} {...props} />
                        },

                    }}
                    // actionBtns={[
                    //     {
                    //         name: 'goback',
                    //         type: 'dashed',
                    //         label: '??????',
                    //         isValidate: false,
                    //         onClick: 'bind:onClickFunBack',
                    //         hide: 'bind:hideFunBack'
                    //     },
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '??????',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 field: 'addsubmit',
                    //                 fetchConfig: {
                    //                     apiName: 'addZxSkLimitPriceAdjust'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         icon: 'edit',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: 'bind:disabledFunEdit',
                    //         willExecute: 'bind:willExecuteFunEdit',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '??????',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '??????',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxSkLimitPriceAdjust'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         drawerTitle: "??????",
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '????????????',
                    //         disabled: 'bind:disabledFunCom',
                    //         Component: 'ProgressCheckChange'
                    //     },
                    //     {
                    //         drawerTitle: "??????",
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '??????',
                    //         disabled: 'bind:disabledFunDetail',
                    //         Component: 'DeetailFormChange'
                    //     },
                    //     {
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '????????????',
                    //         drawerTitle: '????????????',
                    //         disabled: 'bind:disabledFunJindu',
                    //         Component: 'OperationJindu'
                    //     },
                    //     {
                    //         name: 'diyDel',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '??????',
                    //         disabled: 'bind:disabledFunDel',
                    //         onClick: 'bind:onClickFunDel'
                    //     }
                    // ]}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: (obj) => {
                            var props = obj.props;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "ImportPriceLimitChange"
                            }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;