import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg } from 'antd';
const config = {
    antd: {
        rowKey: function (row) {
            return row.id
        },
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
    },
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {

        }
    }
    render() {
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
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxEqUseEquipList',
                        otherParams:{
                            useOrgId:departmentId,
                            statusFlag:'0'
                        }
                    }}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            content: {
                                fetchConfig: (obj) => {
                                    if (obj.clickCb.rowData) {
                                        return {
                                            apiName: 'getZxEqUseEquipDetails',
                                            otherParams: {
                                                id: obj.clickCb.rowData.id
                                            }
                                        }
                                    } else {
                                        return {}
                                    }
                                },
                                wrappedComponentRef: (me) => {
                                    this.qnnForm = me;
                                },
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "id", //唯一的字段名 ***必传
                                        hide: true
                                    },
                                    {
                                        type: "string",
                                        label: "机械管理编号",
                                        field: "equipNo",
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "机械名称",
                                        field: "equipName",
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "计划批文号",
                                        field: "planNo",
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "所属单位",
                                        field: "ownerOrg",
                                        // optionConfig: {
                                        //     label: 'departmentName',
                                        //     value: 'departmentId'
                                        // },
                                        // fetchConfig: {
                                        //     apiName: 'getSysCompanyProject',
                                        //     otherParams:{
                                        //         departmentId:departmentId
                                        //     }
                                        // },
                                        // allowClear: false,
                                        // showSearch: true,
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        label: "使用单位",
                                        field: "useOrgId",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'departmentName',
                                            value: 'departmentId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysCompanyProject',
                                            otherParams:{
                                                departmentId:departmentId
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: "请选择",
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "所在地点",
                                        field: "locality",
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        label: "资源分类",
                                        field: "resCatalogID",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'catName',
                                            value: 'id'
                                        },
                                        fetchConfig: {
                                            apiName: "getZxEqResCategoryList",
                                            otherParams: {
                                                parentID: '0003'
                                            }
                                        },
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "规格",
                                        field: "spec",
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "型号",
                                        field: "model",
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        label: "设备来源",
                                        field: "origin",
                                        type: 'select',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        optionData: [
                                            {
                                                label: "局批自购",
                                                value: "0"
                                            },
                                            {
                                                label: "海外调拨",
                                                value: "1"
                                            }
                                        ],
                                        allowClear: false,
                                        showSearch: true,
                                        placeholder: '请选择',
                                        span: 8
                                    },
                                    {
                                        type: "number",
                                        label: "功率",
                                        field: "powerValue",
                                        min:0,
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "功率单位",
                                        field: "powerUnit",
                                        initialValue:'KW',
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "number",
                                        label: "单位油耗",
                                        field: "unitFuelExpend",
                                        min:0,
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "date",
                                        label: "进场日期",
                                        field: "inDate",
                                        placeholder: "请选择",
                                        span: 8
                                    },
                                    {
                                        type: "date",
                                        label: "入账日期",
                                        field: "regdate",
                                        placeholder: "请选择",
                                        span: 8
                                    },
                                    {
                                        type: "date",
                                        label: "出厂日期",
                                        field: "outFactoryDate",
                                        placeholder: "请选择",
                                        span: 8
                                    },
                                    {
                                        type: "string",
                                        label: "厂家",
                                        field: "factory",
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "number",
                                        label: "原值",
                                        field: "orginalValue",
                                        min:0,
                                        placeholder: "请输入",
                                        span: 8
                                    },
                                    {
                                        type: "textarea",
                                        label: "备注",
                                        field: "remark",
                                        placeholder: "请输入",
                                        span: 21,
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
                                    },
                                    {
                                        type: "files",
                                        label: "附件",
                                        field: "fileList",
                                        initialValue: [],
                                        fetchConfig: {
                                            apiName: "upload"
                                        },
                                        span: 21,
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
                                    },
                                ]
                            }
                        },
                        {
                            field: "table1",
                            name: "qnnTable",
                            title: "运转记录",
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqEWorkList',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { equipID: obj.clickCb.rowData.refEquipID };
                                        }
                                    },
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableYZ = me;
                                },
                                drawerConfig: {
                                    width: "1200px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect:true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 8 },
                                        sm: { span: 8 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 16 },
                                        sm: { span: 16 }
                                    }
                                },
                                actionBtns: [
                                    {
                                        name: 'add',//内置add del
                                        icon: 'plus',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '新增',
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'addZxEqEWork',
                                                    // otherParams: (obj) => {
                                                    //     let rowData = obj.clickCb.rowData;
                                                    //     if (rowData) {
                                                    //         return { equipID: obj.clickCb.rowData.refEquipID };
                                                    //     }
                                                    // },
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'edit',//内置add del
                                        icon: 'edit',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '修改',
                                        disabled:(obj) => {
                                            let selectedData = obj.btnCallbackFn.getSelectedRows();
                                            if(selectedData.length === 1 && selectedData[0].comID === obj.props.initialValues.comID){
                                                return false;
                                            }else{
                                                return true;
                                            }
                                        },
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'updateZxEqEWork',
                                                    // otherParams: (obj) => {
                                                    //     let rowData = obj.clickCb.rowData;
                                                    //     if (rowData) {
                                                    //         return { equipID: obj.clickCb.rowData.refEquipID };
                                                    //     }
                                                    // },
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZxEqEWork',
                                        },
                                    }
                                ],
                                formConfig: [
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
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.comID;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'comName',
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.comName;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'orgID',
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.useOrgId;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'equipID',
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.refEquipID;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'resCatalogID',
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.resCatalogID;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '使用单位',
                                            dataIndex: 'orgName',
                                            key: 'orgName',
                                            filter: true
                                        },
                                        form: {
                                            type: 'string',
                                            required:true,
                                            initialValue:(obj) => {
                                                return obj?.clickCb?.rowData?.useOrg;
                                            },
                                            addDisabled:true,
                                            editDisabled:true,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '机械编码',
                                            dataIndex: 'equipNo',
                                            key: 'equipNo',
                                            onClick: 'detail'
                                        },
                                        form: {
                                            type: 'string',
                                            required:true,
                                            addDisabled:true,
                                            editDisabled:true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.equipNo;
                                            },
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '机械分类',
                                            dataIndex: 'resCatalogName',
                                            key: 'resCatalogName',
                                            type:'select'
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.resCatalog;
                                            },
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '机械名称',
                                            dataIndex: 'equipName',
                                            key: 'equipName',
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.equipName;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '技术规格',
                                            dataIndex: 'spec',
                                            key: 'spec'
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            addDisabled: true,
                                            editDisabled: true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.spec;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '填写日期',
                                            dataIndex: 'bizDate',
                                            key: 'bizDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        form: {
                                            type: 'date',
                                            required: true,
                                            initialValue:new Date(),
                                            placeholder: '请选择',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '状态',
                                            dataIndex: 'status',
                                            key: 'status',
                                            render:(data) => {
                                                if(!data){
                                                    return '运行中';
                                                }else{
                                                    return data;
                                                }
                                            }
                                        },
                                        form: {
                                            hide: true,
                                            type:'string',
                                            placeholder: '请输入',
                                            spanForm: 8
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
                                                wrappedComponentRef: (me) => {
                                                    this.tableOne = me;
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
                                                            title: '序号',
                                                            dataIndex: 'index',
                                                            key: 'index',
                                                            width: 50,
                                                            fixed: 'left',
                                                            render: (data, rowData, index) => {
                                                                return index + 1;
                                                            }
                                                        },
                                                        isInForm: false
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>日期<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'workDate',
                                                            key: 'workDate',
                                                            width: 150,
                                                            fixed: 'left',
                                                            tdEdit: true,
                                                            format:'YYYY-MM-DD'
                                                        },
                                                        form: {
                                                            type: 'date',
                                                            required:true,
                                                            placeholder: '请选择',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>运转台时<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'runHour',
                                                            key: 'runHour',
                                                            width: 100,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>停滞台时<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'stopHour',
                                                            key: 'stopHour',
                                                            width: 100,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>日历天数<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'calendarNumDay',
                                                            key: 'calendarNumDay',
                                                            width: 100,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>完好台日<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'wellDays',
                                                            key: 'wellDays',
                                                            width: 100,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>运转台日<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'runDay',
                                                            key: 'runDay',
                                                            width: 100,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>行驶里程(千米)<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'loadMiles',
                                                            key: 'loadMiles',
                                                            width: 150,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>汽油消耗(升)<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'gasoline',
                                                            key: 'gasoline',
                                                            width: 150,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>柴油消耗(升)<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'diesel',
                                                            key: 'diesel',
                                                            width: 150,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
                                                            placeholder: '请输入',
                                                        },
                                                    },
                                                    {
                                                        table: {
                                                            title: "<div>电消耗(度)<span style='color: #ff4d4f'>*</span></div>",
                                                            dataIndex: 'consumption',
                                                            key: 'consumption',
                                                            width: 150,
                                                            tdEdit: true
                                                        },
                                                        form: {
                                                            type: 'number',
                                                            min:0,
                                                            required:true,
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
                                            label: "附件",
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
                                ]
                            }
                        },
                        {
                            field: "table2",
                            name: "qnnTable",
                            title: "入场信息登记",
                            content: {
                                tabs: [],
                                fetchConfig: {
                                    apiName: 'getZxEqUseEquipInList',
                                    otherParams: (obj) => {
                                        let rowData = obj.clickCb.rowData;
                                        if (rowData) {
                                            return { useEquipID: obj.clickCb.rowData.id };
                                        }
                                    },
                                    success: () => {
                                        this.props.myFetch('getZxEqUseEquipDetails', { id: this.table.btnCallbackFn.form.getFieldValue('id') }).then(
                                            (editObj) => {
                                                if (editObj.success) {
                                                    this.table.btnCallbackFn.form.setFieldsValue(editObj.data);
                                                } else {
                                                    Msg.error(editObj.message);
                                                }
                                            }
                                        );
                                    }
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableRC = me;
                                },
                                drawerConfig: {
                                    width: "1200px"
                                },
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: {
                                    position: "bottom"
                                },
                                isShowRowSelect:true,
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 10 },
                                        sm: { span: 10 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 14 },
                                        sm: { span: 14 }
                                    }
                                },
                                actionBtns: [
                                    {
                                        name: 'add',//内置add del
                                        icon: 'plus',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '新增',
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                field: 'cancel',
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                field: 'submit',
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'addZxEqUseEquipIn'
                                                }
                                            }
                                        ],
                                    },
                                    {
                                        name: 'edit',//内置add del
                                        icon: 'edit',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '修改',
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'updateZxEqUseEquipIn'
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZxEqUseEquipIn',
                                        }
                                    }
                                ],
                                formConfig: [
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
                                            field: 'ownerOrgID',
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.ownerOrgID;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'useOrgId',
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.useOrgId;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'useEquipID',
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.id;
                                            },
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '所属单位',
                                            dataIndex: 'ownerOrg',
                                            key: 'ownerOrg',
                                            filter: true,
                                            fixed: 'left',
                                            width: 150,
                                            onClick:"detail"
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled:true,
                                            initialValue:(obj) => {
                                                return obj?.clickCb?.rowData?.ownerOrg;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    }, {
                                        table: {
                                            title: '使用单位',
                                            dataIndex: 'useOrg',
                                            key: 'useOrg',
                                            filter: true,
                                            fixed: 'left',
                                            width: 150
                                        },
                                        form: {
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj?.clickCb?.rowData?.useOrg;
                                            },
                                            addDisabled: true,
                                            editDisabled:true,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '机械名称',
                                            dataIndex: 'equipName',
                                            key: 'equipName',
                                            width: 150,
                                        },
                                        form: {
                                            type: 'string',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.equipName;
                                            },
                                            addDisabled: true,
                                            editDisabled:true,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '规格',
                                            dataIndex: 'spec',
                                            key: 'spec',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled:true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.spec;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '型号',
                                            dataIndex: 'model',
                                            key: 'model',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled:true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.model;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '厂家',
                                            dataIndex: 'outfactory',
                                            key: 'outfactory',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'string',
                                            addDisabled: true,
                                            editDisabled:true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.factory;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '出厂日期',
                                            dataIndex: 'mainoutfactory',
                                            key: 'mainoutfactory',
                                            format: 'YYYY-MM-DD',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'date',
                                            addDisabled: true,
                                            editDisabled:true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.outFactoryDate;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '进场日期',
                                            dataIndex: 'inDate',
                                            key: 'inDate',
                                            format: 'YYYY-MM-DD',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'date',
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.inDate;
                                            },
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '功率',
                                            dataIndex: 'powerValue',
                                            key: 'powerValue',
                                            width: 100,
                                        },
                                        form: {
                                            type: 'number',
                                            addDisabled: true,
                                            editDisabled:true,
                                            initialValue:(obj) => {
                                                return obj.clickCb.rowData.powerValue;
                                            },
                                            min:0,
                                            placeholder: '请输入',
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '验收总体评价',
                                            dataIndex: 'acceptance',
                                            key: 'acceptance',
                                            width: 150,
                                            type:'select'
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
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否特种设备',
                                            dataIndex: 'isSepcial',
                                            key: 'isSepcial',
                                            width: 150,
                                            type:'select'
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
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '特种设备检验报告',
                                            dataIndex: 'inspReport',
                                            key: 'inspReport',
                                            width: 150,
                                            type:'select'
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
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '特种设备使用登记证',
                                            dataIndex: 'inspCert',
                                            key: 'inspCert',
                                            width: 150,
                                            type:'select'
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
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        table: {
                                            title: '操作人员证',
                                            dataIndex: 'opCert',
                                            key: 'opCert',
                                            width: 150,
                                            type:'select'
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
                                            spanForm: 8
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: "附件",
                                            field: "fileList",
                                            type: "files",
                                            initialValue: [],
                                            fetchConfig: {
                                                apiName: "upload"
                                            },
                                            spanForm: 20,
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
                                ]
                            }
                        },
                    ]}
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
                            table: {
                                title: '机械管理编号',
                                dataIndex: 'equipNo',
                                key: 'equipNo',
                                filter: true,
                                fixed: 'left',
                                width: 150,
                                onClick: 'detail',
                                willExecute: (obj) => {
                                    obj.btnCallbackFn.setActiveKey('0');
                                }
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '机械名称',
                                dataIndex: 'equipName',
                                key: 'equipName',
                                filter: true,
                                fixed: 'left',
                                width: 150
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '计划批文号',
                                dataIndex: 'planNo',
                                key: 'planNo',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '所属单位',
                                dataIndex: 'ownerOrg',
                                key: 'ownerOrg',
                                width: 100,
                                // type:'select'
                            },
                            form: {
                                type: 'string',
                                // optionConfig: {
                                //     label: 'departmentName',
                                //     value: 'departmentId'
                                // },
                                // fetchConfig: {
                                //     apiName: 'getSysCompanyProject',
                                //     otherParams:{
                                //         departmentId:departmentId
                                //     }
                                // },
                                // allowClear: false,
                                // showSearch: true,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '使用单位',
                                dataIndex: 'useOrgId',
                                key: 'useOrgId',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'departmentName',
                                    value: 'departmentId'
                                },
                                fetchConfig: {
                                    apiName: 'getSysCompanyProject',
                                    otherParams:{
                                        departmentId:departmentId
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '所在地点',
                                dataIndex: 'locality',
                                key: 'locality',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '资源分类',
                                dataIndex: 'resCatalogID',
                                key: 'resCatalogID',
                                width: 100,
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'catName',
                                    value: 'id'
                                },
                                fetchConfig: {
                                    apiName: "getZxEqResCategoryList",
                                    otherParams: {
                                        parentID: '0003'
                                    }
                                },
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '设备来源',
                                dataIndex: 'origin',
                                key: 'origin',
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
                                        label: "局批自购",
                                        value: "0"
                                    },
                                    {
                                        label: "海外调拨",
                                        value: "1"
                                    }
                                ],
                                allowClear: false,
                                showSearch: true,
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '入账日期',
                                dataIndex: 'regdate',
                                key: 'regdate',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '出厂日期',
                                dataIndex: 'outFactoryDate',
                                key: 'outFactoryDate',
                                format: 'YYYY-MM-DD',
                                width: 100
                            },
                            form: {
                                type: 'date',
                                placeholder: '请选择',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '厂家',
                                dataIndex: 'factory',
                                key: 'factory',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '原值',
                                dataIndex: 'orginalValue',
                                key: 'orginalValue',
                                width: 100
                            },
                            form: {
                                type: 'number',
                                min:0,
                                placeholder: '请输入',
                                spanForm: 8
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remark',
                                key: 'remark',
                                width: 100
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
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
                    ]}
                />
            </div>
        );
    }
}

export default index;