import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.tableQD = props.tableQD;
        this.state = {

        }
    }
    render () {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div>
                {this.tableQD?.rowInfo?.name === 'BG' ? <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    fetchConfig={{
                        apiName: 'getZxCtWorksDetail',
                        otherParams: {
                            id: this.tableQD?.rowData?.id
                        }
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 10 },
                            sm: { span: 10 }
                        },
                        wrapperCol: {
                            xs: { span: 14 },
                            sm: { span: 14 }
                        }
                    }}
                    formConfig={[
                        {
                            field: 'id',
                            type: 'string',
                            hide: true,
                        },
                        {
                            field: 'workBookID',
                            type: 'string',
                            hide: true,
                        },
                        {
                            field: 'orgID',
                            type: 'string',
                            hide: true,
                        },
                        {
                            field: 'treeNode',
                            type: 'string',
                            hide: true,
                        },
                        {
                            field: 'workID',
                            type: 'string',
                            hide: true,
                        },
                        {
                            field: 'isLeaf',
                            type: 'string',
                            hide: true,
                        },
                        {
                            label: '清单编号',
                            field: 'workNo',
                            type: 'string',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '清单名称',
                            field: 'workName',
                            type: 'string',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '计量单位',
                            field: 'unit',
                            type: 'string',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '合同数量',
                            field: 'contractQty',
                            type: 'number',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '合同单价',
                            field: 'contractPrice',
                            type: 'number',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '合同金额',
                            field: 'contractAmt',
                            type: 'number',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '变更后数量',
                            field: 'quantity',
                            type: 'number',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '变更后单价',
                            field: 'price',
                            type: 'number',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '变更后金额',
                            field: 'changeAmt',
                            type: 'number',
                            disabled: true,
                            placeholder: '请输入',
                            span: 8
                        },
                        {
                            label: '备注',
                            field: 'remarks',
                            type: 'string',
                            disabled: true,
                            placeholder: '请输入',
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
                            field: "BG",
                            type: "qnnTable",
                            formItemLayout: {
                                labelCol: {
                                    xs: { span: 0 },
                                    sm: { span: 0 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 24 }
                                }
                            },
                            qnnTableConfig: {
                                fetchConfig: () => {
                                    let rowData = this.tableQD?.rowData;
                                    if (rowData?.id) {
                                        return {
                                            apiName: 'getZxCtWorkAlterSingleList',
                                            otherParams: {
                                                workID: rowData?.id
                                            }
                                        }
                                    }
                                },
                                actionBtnsPosition: "top",
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                drawerConfig: {
                                    width: '1000px'
                                },
                                paginationConfig: {
                                    position: 'bottom'
                                },
                                wrappedComponentRef: (me) => {
                                    this.tableBG = me;
                                },
                                isShowRowSelect: false,
                                desc: <div style={{ color: "red" }}>变更历史</div>,
                                actionBtns: [
                                    {
                                        name: 'add',
                                        type: 'primary',
                                        label: '新增清单',
                                        field: 'addXZ',
                                        disabled: () => {
                                            let XZQDRowData = this.tableQD?.rowData;
                                            if (XZQDRowData?.isLeaf === 1) {
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        },
                                        formBtns: [
                                            {
                                                field: 'xzqdCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                field: 'xzqdSubmit',
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'addZxCtWorksChange',
                                                    success: (obj) => {
                                                        if (obj.response.success) {
                                                            this.tableQD.rowInfo = null;
                                                            this.form.refresh();
                                                            this.tableQD.refresh();
                                                        }
                                                    }
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'add',
                                        type: 'primary',
                                        label: '清单变更',
                                        field: 'addBG',
                                        formBtns: [
                                            {
                                                field: 'qdbgCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                field: 'qdbgSubmit',
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'addZxCtWorkAlterSingle',
                                                    success: (obj) => {
                                                        if (obj.response.success) {
                                                            this.tableQD.rowInfo = null;
                                                            // this.form.form.setFieldsValue({
                                                            //     price: obj.response?.data?.price,
                                                            //     quantity: obj.response?.data?.quantity,
                                                            //     changeAmt: (obj.response?.data?.price || 0) * (obj.response?.data?.quantity || 0)
                                                            // });
                                                            this.form.refresh();
                                                            this.tableQD.refresh();
                                                        }
                                                    }
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'QXBG',
                                        type: 'primary',
                                        label: '取消变更',
                                        onClick: () => {
                                            confirm({
                                                content: '确认取消上一次变更吗?',
                                                onOk: () => {
                                                    let QXBGRowData = this.tableQD?.rowData;
                                                    this.props.myFetch('zxCtCancelWorkAlterSingle', { workID: QXBGRowData.id }).then(
                                                        (QXBGObj) => {
                                                            if (QXBGObj.success) {
                                                                Msg.success(QXBGObj.message);
                                                                this.tableQD.rowInfo = null;
                                                                this.form.refresh();
                                                                this.tableQD.refresh();
                                                            } else {
                                                                Msg.error(QXBGObj.message);
                                                            }
                                                        }
                                                    );
                                                }
                                            })
                                        }
                                    }
                                ],
                                tabs: [
                                    {
                                        field: "czQnnForm",
                                        name: "qnnForm",
                                        title: "操作清单",
                                        content: {
                                            formConfig: [
                                                {
                                                    field: 'id',
                                                    type: 'string',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addXZ') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.id
                                                        }
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    field: 'workBookID',
                                                    type: 'string',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addXZ') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.workBookID
                                                        }
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    field: 'orgID',
                                                    type: 'string',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addXZ') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.orgID
                                                        }
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    field: 'treeNode',
                                                    type: 'string',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addXZ') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.treeNode
                                                        }
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    field: 'workID',
                                                    type: 'string',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.id
                                                        }
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    field: 'originalQuantity',
                                                    type: 'number',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.contractQty
                                                        }
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    field: 'originalPrice',
                                                    type: 'number',
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.contractPrice
                                                        }
                                                    },
                                                    hide: true,
                                                },
                                                {
                                                    label: '清单编号',
                                                    field: 'workNo',
                                                    type: 'string',
                                                    placeholder: '请输入',
                                                    required: true,
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
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.workNo
                                                        }
                                                    },
                                                    span: 8
                                                },
                                                {
                                                    label: '清单名称',
                                                    field: 'workName',
                                                    type: 'string',
                                                    required: true,
                                                    placeholder: '请输入',
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
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.workName
                                                        }
                                                    },
                                                    span: 8
                                                },
                                                {
                                                    label: '计量单位',
                                                    field: 'unit',
                                                    type: 'string',
                                                    placeholder: '请输入',
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
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.unit
                                                        }
                                                    },
                                                    span: 8
                                                },
                                                {
                                                    label: '变更后数量',
                                                    field: 'quantity',
                                                    type: 'number',
                                                    dependencies: ["isLeaf"],
                                                    dependenciesReRender: true,
                                                    disabled: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addXZ') {
                                                            if (obj.form.getFieldValue("isLeaf") === 1) {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        }
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            if (qdxzRowData?.isLeaf === 1) {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        }
                                                    },
                                                    onChange: (val, obj) => {
                                                        let changeData = obj.form.getFieldsValue();
                                                        obj.form.setFieldsValue({
                                                            changeAmt: (val ? val : 0) * (changeData.price ? changeData.price : 0)
                                                        })
                                                    },
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.quantity
                                                        } else {
                                                            return 0;
                                                        }
                                                    },
                                                    placeholder: '请输入',
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
                                                    span: 8
                                                },
                                                {
                                                    label: '变更后单价',
                                                    field: 'price',
                                                    type: 'number',
                                                    dependencies: ["isLeaf"],
                                                    dependenciesReRender: true,
                                                    disabled: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addXZ') {
                                                            if (obj.form.getFieldValue("isLeaf") === 1) {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        }
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            if (qdxzRowData?.isLeaf === 1) {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        }
                                                    },
                                                    onChange: (val, obj) => {
                                                        let changeData = obj.form.getFieldsValue();
                                                        obj.form.setFieldsValue({
                                                            changeAmt: (val ? val : 0) * (changeData.quantity ? changeData.quantity : 0)
                                                        })
                                                    },
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.price
                                                        } else {
                                                            return 0;
                                                        }
                                                    },
                                                    placeholder: '请输入',
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
                                                    span: 8
                                                },
                                                {
                                                    label: '变更后金额',
                                                    field: 'changeAmt',
                                                    type: 'number',
                                                    disabled: true,
                                                    placeholder: '请输入',
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
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.changeAmt;
                                                        } else {
                                                            return 0;
                                                        }
                                                    },
                                                    span: 8
                                                },
                                                {
                                                    label: '变更人',
                                                    field: 'alterPerson',
                                                    type: 'string',
                                                    disabled: true,
                                                    initialValue: realName,
                                                    placeholder: '请输入',
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
                                                    span: 8
                                                },
                                                {
                                                    label: '变更时间',
                                                    field: 'alterDate',
                                                    type: 'date',
                                                    initialValue: new Date(),
                                                    placeholder: '请选择',
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
                                                    span: 8
                                                },
                                                {
                                                    label: '是否叶子节点',
                                                    field: 'isLeaf',
                                                    type: 'radio',
                                                    optionData: [
                                                        {
                                                            label: "否",
                                                            value: 0
                                                        },
                                                        {
                                                            label: "是",
                                                            value: 1
                                                        }
                                                    ],
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
                                                    onChange: (val, obj) => {
                                                        if (!val.length) {
                                                            obj.form.setFieldsValue({
                                                                quantity: 0,
                                                                price: 0,
                                                                changeAmt: 0
                                                            })
                                                        }
                                                    },
                                                    hide: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            return true
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                    initialValue: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            let qdxzRowData = this.form.form.getFieldsValue();
                                                            return qdxzRowData?.isLeaf
                                                        }
                                                    },
                                                    span: 8
                                                },
                                                {
                                                    label: '说明',
                                                    field: 'information',
                                                    type: 'textarea',
                                                    placeholder: '请输入',
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
                                                    type: 'component',
                                                    field: 'component',
                                                    Component: obj => {
                                                        return (
                                                            <div style={{ color: 'red', textAlign: 'center' }}>请注意：没有勾选“是否叶子节点”则可以在该节点下继续新增子节点，反之则不能！</div>
                                                        );
                                                    },
                                                    hide: (obj) => {
                                                        if (obj.clickCb?.rowInfo?.field === 'addBG') {
                                                            return true
                                                        } else {
                                                            return false;
                                                        }
                                                    },
                                                }
                                            ]
                                        }
                                    }
                                ],
                                formConfig: [
                                    {
                                        table: {
                                            title: '类型',
                                            dataIndex: 'alterType',
                                            key: 'alterType'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '清单编号',
                                            dataIndex: 'workNo',
                                            key: 'workNo'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '清单名称',
                                            dataIndex: 'workName',
                                            key: 'workName'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '原来数量',
                                            dataIndex: 'originalQuantity',
                                            key: 'originalQuantity'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '原来单价',
                                            dataIndex: 'originalPrice',
                                            key: 'originalPrice'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '变更后数量',
                                            dataIndex: 'quantity',
                                            key: 'quantity'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '变更后单价',
                                            dataIndex: 'price',
                                            key: 'price'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '变更人',
                                            dataIndex: 'alterPerson',
                                            key: 'alterPerson',
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '变更时间',
                                            dataIndex: 'alterDate',
                                            key: 'alterDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '说明',
                                            dataIndex: 'information',
                                            key: 'information',
                                        },
                                        isInForm: false
                                    },
                                ]
                            }
                        }
                    ]}
                /> : null}
            </div>
        );
    }
}

export default index;