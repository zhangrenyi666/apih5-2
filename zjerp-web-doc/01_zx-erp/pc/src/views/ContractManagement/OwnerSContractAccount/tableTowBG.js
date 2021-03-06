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
                            label: '????????????',
                            field: 'workNo',
                            type: 'string',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'workName',
                            type: 'string',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'unit',
                            type: 'string',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'contractQty',
                            type: 'number',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'contractPrice',
                            type: 'number',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '????????????',
                            field: 'contractAmt',
                            type: 'number',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '???????????????',
                            field: 'quantity',
                            type: 'number',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '???????????????',
                            field: 'price',
                            type: 'number',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '???????????????',
                            field: 'changeAmt',
                            type: 'number',
                            disabled: true,
                            placeholder: '?????????',
                            span: 8
                        },
                        {
                            label: '??????',
                            field: 'remarks',
                            type: 'string',
                            disabled: true,
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
                                desc: <div style={{ color: "red" }}>????????????</div>,
                                actionBtns: [
                                    {
                                        name: 'add',
                                        type: 'primary',
                                        label: '????????????',
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
                                                label: "??????"
                                            },
                                            {
                                                field: 'xzqdSubmit',
                                                name: 'submit',//??????add del
                                                type: 'primary',//??????  ?????? primary
                                                label: '??????',//???????????????????????????????????? 
                                                fetchConfig: {//ajax??????
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
                                        label: '????????????',
                                        field: 'addBG',
                                        formBtns: [
                                            {
                                                field: 'qdbgCancel',
                                                name: "cancel",
                                                type: "dashed",
                                                label: "??????"
                                            },
                                            {
                                                field: 'qdbgSubmit',
                                                name: 'submit',//??????add del
                                                type: 'primary',//??????  ?????? primary
                                                label: '??????',//???????????????????????????????????? 
                                                fetchConfig: {//ajax??????
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
                                        label: '????????????',
                                        onClick: () => {
                                            confirm({
                                                content: '???????????????????????????????',
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
                                        title: "????????????",
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
                                                    label: '????????????',
                                                    field: 'workNo',
                                                    type: 'string',
                                                    placeholder: '?????????',
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
                                                    label: '????????????',
                                                    field: 'workName',
                                                    type: 'string',
                                                    required: true,
                                                    placeholder: '?????????',
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
                                                    label: '????????????',
                                                    field: 'unit',
                                                    type: 'string',
                                                    placeholder: '?????????',
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
                                                    label: '???????????????',
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
                                                    placeholder: '?????????',
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
                                                    label: '???????????????',
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
                                                    placeholder: '?????????',
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
                                                    label: '???????????????',
                                                    field: 'changeAmt',
                                                    type: 'number',
                                                    disabled: true,
                                                    placeholder: '?????????',
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
                                                    label: '?????????',
                                                    field: 'alterPerson',
                                                    type: 'string',
                                                    disabled: true,
                                                    initialValue: realName,
                                                    placeholder: '?????????',
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
                                                    label: '????????????',
                                                    field: 'alterDate',
                                                    type: 'date',
                                                    initialValue: new Date(),
                                                    placeholder: '?????????',
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
                                                    label: '??????????????????',
                                                    field: 'isLeaf',
                                                    type: 'radio',
                                                    optionData: [
                                                        {
                                                            label: "???",
                                                            value: 0
                                                        },
                                                        {
                                                            label: "???",
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
                                                    label: '??????',
                                                    field: 'information',
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
                                                },
                                                {
                                                    type: 'component',
                                                    field: 'component',
                                                    Component: obj => {
                                                        return (
                                                            <div style={{ color: 'red', textAlign: 'center' }}>??????????????????????????????????????????????????????????????????????????????????????????????????????????????????</div>
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
                                            title: '??????',
                                            dataIndex: 'alterType',
                                            key: 'alterType'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'workNo',
                                            key: 'workNo'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'workName',
                                            key: 'workName'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'originalQuantity',
                                            key: 'originalQuantity'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'originalPrice',
                                            key: 'originalPrice'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'quantity',
                                            key: 'quantity'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'price',
                                            key: 'price'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'alterPerson',
                                            key: 'alterPerson',
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'alterDate',
                                            key: 'alterDate',
                                            format: 'YYYY-MM-DD'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '??????',
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