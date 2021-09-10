import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg, Modal } from 'antd';
import ProgressCheck from './form';
import Operation from '../ImportPriceLimitChange/operation';
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
        this.state = {}
    }
    componentDidMount() {


    }
    render() {
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { myFetch } = this.props;
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxSkLimitPriceList',
                        otherParams: {
                            projectId: departmentId
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
                                field: 'orgId',
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
                                field: 'projectName',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '限价编号',
                                width: 200,
                                // tooltip: 23,
                                onClick: 'detail',
                                dataIndex: 'limitNo',
                                key: 'limitNo',
                                filter: true
                            },
                            form: {
                                field: 'limitNo',
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
                                title: '公司名称',
                                width: 150,
                                dataIndex: 'comName',
                                key: 'comName',
                                filter: true
                            },
                            form: {
                                type: 'string',
                                field: 'comName',
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
                                    // optionConfig: {
                                    //     label: 'orgName',
                                    //     value: 'orgID'
                                    // },
                                    // fetchConfig: {
                                    //     apiName: 'getZxCtContractList',
                                    //     otherParams: {
                                    //         contrStatus: "1",
                                    //         orgID: departmentId
                                    //     }
                                    // },
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
                                editDisabled: true,
                                showSearch: true,
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID',
                                    linkageFields: {
                                        "comName": "companyName",//公司
                                        comID: 'companyId',
                                        orgName: 'companyName',
                                        orgId: 'companyId',
                                        projectName:'orgName'
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
                                onChange: (val) => {
                                    
                                    if (this.table && this.table.qnnForm) {
                                        this.table.qnnForm.form.setFieldsValue({
                                            limitNo: ''
                                        })
                                        this.table.qnnForm.clearValues(['zxSkLimitPriceItemList']);
                                        myFetch('getZxSkLimitPriceBase', { orgId: val }).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    if (data && data.limitNo) {
                                                        this.table.qnnForm.form.setFieldsValue({
                                                            limitNo: data.limitNo,
                                                            province: data.province,
                                                            projectType: data.projectType
                                                        })
                                                    }
                                                } else {
                                                }
                                            }
                                        );
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
                                type: 'halfYear',
                                required: true,
                                field: 'periodDate',
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
                            form: {
                                field: 'workId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '填报人',
                                width: 120,
                                dataIndex: 'preparer',
                                key: 'preparer'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                addDisabled: true,
                                editDisabled: true,
                                field: 'preparer',
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
                            isInTable: false,
                            table: {
                                title: '是否本期入场',
                                dataIndex: 'isThisIn',
                                key: 'isThisIn',
                                filter: true
                            },
                            form: {
                                type: 'select',
                                field: 'isThisIn',
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
                                title: '评审状态',
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
                                field: 'zxSkLimitPriceItemList',
                                incToForm: true,
                                qnnTableConfig: {
                                    antd: {
                                        rowKey: 'id',
                                        size: 'small'
                                    },
                                    ...configItem,
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
                                                showSearch: true,
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
                                                onChange: (val, thisProps, btnCallbackFn) => {
                                                    if (val) {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            editRowData.resourceId = val;
                                                            const itemData = thisProps;
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resourceNo: itemData.itemData.catCode,
                                                                workId: itemData.itemData.id,
                                                                resourceName: itemData.itemData.catName,
                                                                workNo: '',
                                                                workName: '',
                                                                spec: '',
                                                                unit: '',
                                                                price: null
                                                            });
                                                        })
                                                    } else {
                                                        btnCallbackFn.getEditedRowData(false).then((editRowData) => {
                                                            btnCallbackFn.setEditedRowData({
                                                                ...editRowData,
                                                                resourceName: '',
                                                                resourceNo: '',
                                                                workId: '',
                                                                workNo: '',
                                                                workName: '',
                                                                spec: '',
                                                                unit: '',
                                                                price: null
                                                            });
                                                        })
                                                    }
                                                }
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                type: 'string',
                                                field: 'workId'
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
                                                title: '物资分类编码',
                                                width: 150,
                                                tooltip: 23,
                                                dataIndex: 'resourceNo',
                                                key: 'resourceNo'
                                            },
                                            form: {
                                                type: 'string',
                                                required: true,
                                                field: 'resourceNo'
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
                                                required: true,
                                                field: 'workNo',
                                                allowClear: false,
                                                parent: 'resourceId',
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
                                                                title: "规格型号",
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
                                                                title: "单位",
                                                            },
                                                            form: {
                                                                type: "string"
                                                            }
                                                        }
                                                    ]
                                                },
                                                onChange: (colVal, thisProps, tableBtnCallbackFn) => {
                                                    if (colVal) {
                                                        const itemData = thisProps;
                                                        // itemData.itemData[0].workNo = colVal;
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            workNo: itemData.itemData[0].resCode,
                                                            workName: itemData.itemData[0].resName,
                                                            unit: itemData.itemData[0].unit,
                                                            spec: itemData.itemData[0].spec,
                                                            price: null
                                                        });
                                                    } else {
                                                        tableBtnCallbackFn.setEditedRowData({
                                                            workName: '',
                                                            unit: '',
                                                            spec: '',
                                                            price: null
                                                        });
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
                                                width: 100,
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
                                                title: "<div>当期采集单价<span style='color: #ff4d4f'>*</span></div>",
                                                dataIndex: 'price',
                                                width: 120,
                                                key: 'price',
                                                tdEdit: true
                                            },
                                            form: {
                                                type: 'number',
                                                field: 'price',
                                                precision: 6,
                                                required: true,
                                            }
                                        }
                                    ],
                                    actionBtns: [
                                        {
                                            name: "addRow",
                                            icon: "plus",
                                            type: "primary",
                                            label: "新增",
                                        },
                                        {
                                            name: "del",
                                            icon: 'delete',
                                            type: 'danger',
                                            label: "删除",
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
                        },
                        {
                            isInForm: false,
                            table: {
                                title: "调整历史",
                                fixed: 'right',
                                dataIndex: 'adjustHistory',
                                key: 'adjustHistory',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "tile",
                                width: 80,
                                btns: [
                                    {
                                        name: 'PolicyDetail',
                                        render: (rowData) => {
                                            return '<a>查看</a>';
                                        },
                                        onClick: (obj) => {
                                            const { mainModule } = obj.props.myPublic.appInfo;
                                            const { limitNo } = obj.rowData;
                                            obj.props.dispatch(
                                                push(`${mainModule}ImportPriceLimitChange/${limitNo}`)
                                            )
                                        },
                                    }
                                ]
                            }
                        }
                    ]}
                    actionBtns={[
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
                                        apiName: 'addZxSkLimitPrice'
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
                                        apiName: 'updateZxSkLimitPrice'
                                    }
                                }
                            ]
                        },
                        {
                            drawerTitle: "限价数据采集申请",
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
                                return <Operation apiName={'openFlowByReport'} detailApiName={'getZxSkLimitPriceDetails'} flowId={'zxSkLimitPriceWorkId'} {...props} />
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
                                            myFetch('batchDeleteUpdateZxSkLimitPrice', obj.selectedRows).then(
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