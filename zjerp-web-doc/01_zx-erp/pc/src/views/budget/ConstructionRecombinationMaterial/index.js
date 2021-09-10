import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import FormDone from "./formDone";
import FormNoDone from "./formNoDone";
// import moment from 'moment';
import { message as Msg, Modal } from 'antd';
// import $ from 'jquery'
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxBuStockPriceId',
        size: 'small'
    },
    drawerConfig: {
        width: window.innerWidth * 0.8
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 12 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 12 },
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
            zxBuStockPriceId: '',
            businessType: '4',
            orgName: '',
            orgID: '',
            QnnTableItemData: [],
            zjid: '',
            //砂浆
            shaJiangData: [],
            shaJiangDataid: '',
            shaJiangMixType: '25',
            //沥青混合
            lqhhlData: [],
            lqhhlId: '',
            lqhhlMixType: '22',
            //级配类混合料
            jplHhlData: [],
            jplHhlid: '',
            jplHhlMixType: '23',
            //灰土类
            htlData: [],
            htlid: '',
            htlMixType: '24',
            //混凝土
            hntData: [],
            hntid: '',
            hntMixType: '21',
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    FloatMulTwo(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try { m += s1.split(".")[1].length } catch (e) { }
        try { m += s2.split(".")[1].length } catch (e) { }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
    }
    render() {
        const { departmentId } = this.state
        return (
            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZxBuStockPriceList',
                        otherParams: {
                            orgID: departmentId,
                            businessType: this.state.businessType,
                            budgetType: '施工预算'
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '取消',
                                },
                                {
                                    name: 'submitDiy',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.props.myFetch('addZxBuStockPrice', {
                                            ...obj._formData,
                                            businessType: this.state.businessType,
                                            budgetType: '施工预算'
                                        }).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 新增接口，返回个主键id
                                                    this.setState({
                                                        zxBuStockPriceId: data.zxBuStockPriceId,
                                                        orgName: data.orgName,
                                                        orgID: data.orgID
                                                    })
                                                    obj.btnCallbackFn.form.setFieldsValue({ zxBuStockPriceId: data.zxBuStockPriceId });
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );
                                    },
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1" || index === "2") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                }
                            ]
                        },
                        {
                            name: 'edit',
                            icon: 'edit',
                            type: 'primary',
                            label: '修改',
                            onClick: (obj) => {
                                obj.btnCallbackFn.setActiveKey('0');
                                this.setState({
                                    zxBuStockPriceId: obj.selectedRows[0].zxBuStockPriceId,
                                    orgName: obj.selectedRows[0].orgName,
                                    orgID: obj.selectedRows[0].orgID
                                })
                                this.table.clearSelectedRows();
                            },
                            disabled: (obj) => {
                                const rowData = obj.qnnTableInstance.getSelectedRows()
                                if (rowData && rowData.length === 1) {
                                    if (rowData[0].status === '已审核') {
                                        return true
                                    } else {
                                        return false
                                    }
                                } else {
                                    return true
                                }
                            },
                            formBtns: [
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field: 'cancel',
                                    label: '取消'
                                },
                                {
                                    name: 'submitEdit',
                                    type: 'primary',
                                    field: 'submit',
                                    label: '保存',
                                    hide: (obj) => {
                                        var index = obj.btnCallbackFn.getActiveKey();
                                        if (index === "1" || index === "2") {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                        this.props.myFetch('updateZxBuStockPrice', obj._formData).then(
                                            ({ data, success, message }) => {
                                                if (success) {
                                                    // 获取树数据
                                                    obj.btnCallbackFn.setActiveKey('1');
                                                    this.table.refresh();
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );

                                    }
                                }
                            ]
                        },
                        {
                            name: 'diyDel',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            disabled: (obj) => {
                                let data = obj.qnnTableInstance.getSelectedRows();
                                if (data.length > 0) {
                                    for (let m = 0; m < data.length; m++) {
                                        if (data[m].status === '已审核') {
                                            return true;
                                        }
                                    }
                                    return false
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                const { myFetch } = this.props;
                                confirm({
                                    content: '确定删除选中的数据吗?',
                                    onOk: () => {
                                        myFetch('batchDeleteUpdateZxBuStockPrice', obj.selectedRows).then(
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
                    ]}
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.clearSelectedRows();
                        }
                    }}
                    formIsChangeedAlertTextContent={false}
                    tabs={[
                        {
                            field: "form1",
                            name: "qnnForm",
                            title: "基础信息",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: {
                                formConfig: [
                                    {
                                        type: "string",
                                        label: "主键ID",
                                        field: "zxBuStockPriceId",
                                        hide: true
                                    },
                                    {
                                        field: 'orgName',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        field: 'budgetType',
                                        type: 'string',
                                        hide: true,
                                        initialValue: '施工预算'
                                    },
                                    {
                                        field: 'businessType',
                                        type: 'string',
                                        hide: true,
                                        initialValue: this.state.businessType
                                    },
                                    {
                                        label: '项目名称',
                                        field: 'orgID',
                                        type: 'select',
                                        optionConfig: {
                                            label: 'orgName',
                                            value: 'orgID',
                                            linkageFields: {
                                                orgName: "orgName",
                                                comID: "companyId",
                                                comName: "companyName",
                                            },
                                        },
                                        editDisabled: true,
                                        fetchConfig: {
                                            apiName: 'getZxBuProjectTypeCheckOver',
                                            otherParams: {
                                                orgID: departmentId
                                            }
                                        },
                                        required: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        },
                                        onChange: (val, obj) => {
                                            const companyName = obj.itemData.companyName
                                            obj.form.setFieldsValue({
                                                reportOrgName: companyName
                                            })
                                        }
                                    },
                                    {
                                        type: "string",
                                        field: "comID",
                                        hide: true,
                                    },
                                    {
                                        type: "string",
                                        field: "comName",
                                        hide: true,
                                    },
                                    {
                                        label: '编制人',
                                        field: 'reporter',
                                        type: 'string',
                                        initialValue: "管理员",
                                        editDisabled: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        label: "期次",
                                        field: "periodDate",
                                        type: "month",
                                        editDisabled: true,
                                        span: 12,
                                        required: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 },
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 },
                                            },
                                        },
                                    },
                                    {
                                        label: '编制机构名称',
                                        field: 'reportOrgName',
                                        type: 'string',
                                        disabled: true,
                                        span: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        label: '编制日期',
                                        field: 'reportDate',
                                        type: 'date',
                                        span: 12,
                                        editDisabled: true,
                                        initialValue: new Date(),
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 8 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 16 },
                                                sm: { span: 16 }
                                            }
                                        }
                                    },
                                    {
                                        label: '备注',
                                        field: 'remarks',
                                        type: 'textarea',
                                        span: 24,
                                        editDisabled: true,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 9 },
                                                sm: { span: 4 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 24 },
                                                sm: { span: 24 }
                                            }
                                        }
                                    }
                                ]
                            }
                        },
                        //已完成
                        {
                            field: "formDone",
                            name: "formDone",
                            title: "已完工程",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
                                return <FormDone {...this.props}
                                    drawerDetailTitle={drawerTitile}
                                    orgID={this.state.orgID}
                                    orgName={this.state.orgName}
                                    stockPriceID={async () => {
                                        return (await this.table.getDeawerValues()).zxBuStockPriceId
                                    }}
                                />
                            }
                        },
                        //剩余工程
                        {
                            field: "formNoDone",
                            name: "formNoDone",
                            title: "剩余工程",
                            disabled: (obj) => {
                                return (obj.clickCb.rowInfo.name === "edit" || obj.clickCb.rowInfo.name === "detail") ? false : !obj.btnCallbackFn.form.getFieldValue("zxBuStockPriceId")
                            },
                            content: props => {
                                let drawerTitile = props.Pstate.drawerDetailTitle;
                                return <FormNoDone {...this.props}
                                    drawerDetailTitle={drawerTitile}
                                    orgID={this.state.orgID}
                                    orgName={this.state.orgName}
                                    stockPriceID={async () => {
                                        return (await this.table.getDeawerValues()).zxBuStockPriceId
                                    }}
                                />
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxBuStockPriceId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '项目名称',
                                width: 250,
                                dataIndex: 'orgName',
                                key: 'orgName',
                                onClick: (obj) => {
                                    this.setState({
                                        zxBuStockPriceId: obj.rowData.zxBuStockPriceId,
                                        orgName: obj.rowData.orgName,
                                        orgID: obj.rowData.orgID,
                                    }, () => {
                                        obj.btnCallbackFn.btnAction({
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
                                align: "center",
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '编制人',
                                width: 150,
                                dataIndex: 'reporter',
                                key: 'reporter',
                                align: "center",
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: "期次",
                                dataIndex: "period",
                                key: "period",
                                align: "center",
                            },
                        },
                        {
                            table: {
                                title: '编制机构名称',
                                width: 200,
                                dataIndex: 'reportOrgName',
                                key: 'reportOrgName',
                                align: "center",
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '编制日期',
                                width: 150,
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                format: 'YYYY-MM-DD',
                                align: "center",
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'status',
                                key: 'status',
                                align: "center",
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 200,
                                dataIndex: 'remarks',
                                key: 'remarks',
                                tooltip: 23,
                            },
                            isInForm: false
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;