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
                                title: '调整编号',
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
                                placeholder: '请输入',
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
                                title: '公司名称',
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
                                title: '项目名称',
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
                                        "comName": "companyName",//公司
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
                                placeholder: '请选择',
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
                                title: '限价编号',
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
                                placeholder: '请输入',
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
                                title: '项目所属省份',
                                dataIndex: 'province',
                                key: 'province'
                            },
                            form: {
                                field: 'province',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                title: '工程类型',
                                dataIndex: 'projectType',
                                key: 'projectType'
                            },
                            form: {
                                field: 'projectType',
                                type: 'string',
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                title: '数据期次',
                                dataIndex: 'periodDate',
                                key: 'periodDate',
                                render: (data) => {
                                    return data ? (moment(data).format('YYYY') + '/' + (moment(data).format('MM') === '12' ? '下半年' : '上半年')) : ''
                                },
                                filter: true
                            },
                            form: {
                                field: 'periodDate',
                                type: 'halfYear',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                placeholder: '请输入',
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
                                title: '填报人',
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
                                title: '填报日期',
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
                                title: '审核',
                                width: 100,
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                render: (data) => {
                                    if (data === '0') {
                                        return '待提交';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '评审通过';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '-1') {
                                        return '未审核';
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
                                                label: '主键id',
                                                field: 'id',
                                                hide: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '调整类型',
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
                                                        label: "新增",//1新增  2修改---2021/2/1修改
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "修改",
                                                        value: "2"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            table: {
                                                title: "<div>物资大类<span style='color: #ff4d4f'>*</span></div>",
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
                                                title: '物资分类编码',
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
                                                title: "<div>物资编码<span style='color: #ff4d4f'>*</span></div>",
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
                                                dependenciesReRender: true,//多个依赖-配置
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
                                                                title: "编号",
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
                                                                title: "名称",
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
                                                                title: "规格型号"
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
                                                                title: "单位"
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
                                                                title: "计价方式"
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
                                                title: '物资名称',
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
                                                title: '规格型号',
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
                                                title: '单位',
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
                                                title: '当期采集单价',
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
                                                title: "<div>调整采集单价<span style='color: #ff4d4f'>*</span></div>",
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
                                                title: '填报日期',
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
                                            label: "新增",
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
                                        //     label: "删除",
                                        // },
                                        {
                                            name: "Diydel",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
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
                                                    Msg.warn('调整类型为“修改”的数据，不允许删除！');
                                                    this.tableItem.clearSelectedRows();
                                                } else {
                                                    confirm({
                                                        content: '确定删除选中的数据吗?',
                                                        onOk: () => {
                                                            let obj = {};
                                                            selectDa.forEach(function (item, index) {
                                                                obj[item.id] = true //将数组arr2中的元素值作为obj 中的键，值为true；
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
                                label: '附件',
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
                                Msg.warn('请选择未审核的数据!');
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
                            //前端用列表页面数据主键长度判断，大于32的“详细”按钮置灰，小等于32位的可以点击“详细”
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
                                    //存在已审核的数据
                                    arry.push(obj.selectedRows[m].apih5FlowStatus);
                                }
                            }
                            if (arry.length > 0) {
                                Msg.warn('请选择未审核的数据！')
                            } else {
                                confirm({
                                    content: '确定删除选中的数据吗?',
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
                    //         label: '返回',
                    //         isValidate: false,
                    //         onClick: 'bind:onClickFunBack',
                    //         hide: 'bind:hideFunBack'
                    //     },
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '新增',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
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
                    //         label: '修改',
                    //         disabled: 'bind:disabledFunEdit',
                    //         willExecute: 'bind:willExecuteFunEdit',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZxSkLimitPriceAdjust'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         drawerTitle: "申请",
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '评审申请',
                    //         disabled: 'bind:disabledFunCom',
                    //         Component: 'ProgressCheckChange'
                    //     },
                    //     {
                    //         drawerTitle: "详情",
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '详情',
                    //         disabled: 'bind:disabledFunDetail',
                    //         Component: 'DeetailFormChange'
                    //     },
                    //     {
                    //         name: 'Component',
                    //         type: 'primary',
                    //         label: '进度查询',
                    //         drawerTitle: '进度查询',
                    //         disabled: 'bind:disabledFunJindu',
                    //         Component: 'OperationJindu'
                    //     },
                    //     {
                    //         name: 'diyDel',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '删除',
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