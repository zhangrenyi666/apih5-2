import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg, Modal } from 'antd';
import ProgressCheck from './form';
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
    paginationConfig: false,
    actionBtnsPosition: "top",
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: 0,
            limitNo: props.match.params.limitNo
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

    getTzNumber(val) {
        const { myFetch } = this.props;
        myFetch('getZxSkLimitPriceAdjustNo', { limitNo: val }).then(
            ({ data, success, message }) => {
                if (success) {
                    Msg.success(message);
                    if (this.table?.qnnForm?.form) {
                        this.table.qnnForm.form.setFieldsValue({
                            adjustNo: data
                        })
                    }
                } else {
                    Msg.warn(message);
                    if (this.table?.qnnForm?.form) {
                        this.table.qnnForm.form.setFieldsValue({
                            adjustNo: null
                        })
                    }
                }
            }
        );
    }
    render() {
        const { limitNo } = this.state;
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                            projectId: departmentId
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
                                            departmentId: departmentId
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
                                        orgID: 'companyId'
                                    }
                                },
                                fetchConfig: {
                                    apiName: 'getZxCtContractList',
                                    otherParams: {
                                        contrStatus: "1",
                                        orgID: departmentId
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
                                    this.getTzNumber(val);
                                    if (rowData.itemData.zxSkLimitPriceItemList) {
                                        let selectData = rowData.itemData.zxSkLimitPriceItemList;
                                        if (this.table?.qnnForm?.form) {
                                            this.table.qnnForm.clearValues(['zxSkLimitPriceAdjustItemList']);
                                            this.table.qnnForm.form.setFieldsValue({
                                                zxSkLimitPriceAdjustItemList: selectData.map((item) => {
                                                    item.adjustType = '2';
                                                    return item
                                                }),
                                            })
                                        }
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
                                    tableTdEdit: true,
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
                                                tdEdit: true
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
                                                    otherParams: async () => {
                                                        const vals = await this.table.btnCallbackFn.qnnForm.getValues(false);
                                                        return {
                                                            parentOrgID: vals.projectId,
                                                        }
                                                    }
                                                },
                                                disabled: ({ record }) => {
                                                    if (record.adjustType === "2") {
                                                        return true
                                                    }
                                                    return false;
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
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'selectByQnnTable',
                                                label: '',
                                                allowClear: false,
                                                required: true,
                                                field: 'workNo',
                                                parent: 'resourceId',
                                                optionConfig: {
                                                    label: 'resCode',
                                                    value: 'id'
                                                },
                                                disabled: ({ record }) => {
                                                    if (record.adjustType === "2") {
                                                        return true
                                                    }
                                                    return false;
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
                                                            return {
                                                                id: obj.qnnFormProps?.qnnformData?.qnnFormProps?.rowData?.resourceId,
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
                                                                workNo: itemData.itemData[0].resCode,
                                                                workName: itemData.itemData[0].resName,
                                                                unit: itemData.itemData[0].unit,
                                                                spec: itemData.itemData[0].spec,
                                                                price: itemData.itemData[0].price
                                                            });
                                                        })
                                                    } else {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                workName: '',
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
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '返回',
                            isValidate: false,
                            onClick: (obj) => {
                                const { mainModule } = obj.props.myPublic.appInfo;
                                obj.props.dispatch(
                                    push(`${mainModule}ImportPriceLimitManage`)
                                )
                            },
                            hide: (val) => {
                                if (limitNo === '0') {
                                    return true
                                } else {
                                    return false
                                }
                            }
                        },
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZxSkLimitPriceAdjust'
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                if (obj.selectedRows[0].apih5FlowStatus === '1' || obj.selectedRows[0].apih5FlowStatus === '2') {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('请选择未审核的数据!');
                                } else {

                                }
                                this.table.clearSelectedRows();
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    fetchConfig: {
                                        apiName: 'updateZxSkLimitPriceAdjust'
                                    }
                                }
                            ]
                        },
                        {
                            drawerTitle: "申请",
                            name: 'Component',
                            type: 'primary',
                            label: '评审申请',
                            disabled: "bind:_actionBtnNoSelected",
                            Component: (obj) => {
                                this.table.clearSelectedRows();
                                if (obj.selectedRows[0].workId != "") {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                                    return <div />
                                }
                                if (obj.selectedRows.length != 1) {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('请选择一条数据！');
                                    return <div />
                                }
                                return <ProgressCheck {...obj} flowData={obj.selectedRows[0]} />
                            }
                        },
                        {
                            name: 'Component',
                            type: 'primary',
                            label: '进度查询',
                            drawerTitle: '进度查询',
                            disabled: (obj) => {
                                let data = obj.btnCallbackFn.getSelectedRows();
                                if (data.length === 1 && data[0].workId) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            Component: (props) => {
                                return <Operation apiName={'openFlowByReport'} detailApiName={'getZxSkLimitPriceAdjustDetails'} flowId={'zxSkLimitPriceAdjustWorkId'} {...props} />
                            }
                        },

                        // {
                        //     name: 'diy',
                        //     type: 'primary',
                        //     label: '预览',
                        //     disabled: "bind:_actionBtnNoSelected",
                        //     onClick: (obj) => {

                        //     }
                        // },
                        {
                            name: 'diyDel',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            disabled: "bind:_actionBtnNoSelected",
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                let arry = [];
                                for (let m = 0; m < obj.selectedRows.length; m++) {
                                    if (obj.selectedRows[m].apih5FlowStatus === '1' || obj.selectedRows[m].apih5FlowStatus === '2') {
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
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            }
                        }
                    ]}

                />
            </div>
        );
    }
}

export default index;