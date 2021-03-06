import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin, Button } from 'antd';
import s from "./tableTow.less";
import Tree from "../../modules/tree";
import ComponentBG from './tableTowBG';
// import ComponentDR from './tableTowDR';
import ComponentBJXJ from './tableTowBJXJ';
import ComponentPLBJ from './tableTowPLBJ';
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
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    },
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.refresh = this.refresh.bind(this);
        this.state = {
            valuePid: '',
            value: '',
            loading: false,
            QDFlagData: null,
            rowData: props?.selectedRows?.[0] || props?.rowData || {},
            drawerDetailTitle: props?.drawerDetailTitle || '',
            visible: false,
            tip:'Loading...'
        }
    }
    componentDidMount () {
        this.refresh();
    }
    refresh () {
        const { rowData } = this.state;
        this.setState({
            QDFlagData: null,
            loading: true
        })
        this.props.myFetch('getZxCtWorkBookList', { orgID: rowData?.orgID }).then(
            ({ data, success, message }) => {
                if (success) {
                    if (data.length) {
                        this.setState({
                            QDFlagData: data[0],
                            loading: false
                        })
                    }
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render () {
        const { rowData, valuePid, value, QDFlagData, loading, drawerDetailTitle, visible, tip } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={loading}  tip={tip}>
                    {QDFlagData ? <div>
                        <div className={s.rootl}
                            ref={(me) => {
                                if (me) {
                                    this.leftDom = me;
                                }
                            }}>
                            <div
                                className={s.hr}
                                ref={(me) => {
                                    if (me) {
                                        let _this = this;
                                        function moveFn (e) {
                                            let conDomLeft = document.getElementsByClassName('ant-drawer-content-wrapper')[0].offsetLeft;
                                            _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                        }
                                        me.addEventListener('mousedown', (e) => {
                                            this.onDragStartPos = e.pageX;
                                            document.addEventListener('mousemove', moveFn, false)
                                        }, false);
                                        document.addEventListener('mouseup', (e) => {
                                            document.removeEventListener('mousemove', moveFn, false)
                                        }, false)
                                    }
                                }}
                            ></div>
                            <Tree
                                selectText={false}
                                modalType="common" //common  drawer  ?????????????????????????????????
                                visible
                                selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                btnShow={false} //????????????????????????
                                disabled={true}
                                draggable={false}
                                nodeRender={nodeData => {
                                    return (
                                        <span>
                                            {nodeData["workName"]}
                                        </span>
                                    );
                                }}
                                rightMenuOption={[]}
                                nodeClick={(node) => {
                                    if (node.value !== value) {
                                        this.setState({
                                            valuePid: ''
                                        }, () => {
                                            this.setState({
                                                value: node.id,
                                                valuePid: node.parentID
                                            })
                                        })
                                    }
                                }}
                                treeProps={{
                                    showLine: true
                                }}
                                fetchConfig={{
                                    parmasKey: "parentID",
                                    apiName: "getZxCtWorksWorkNameTree",
                                    params: {
                                        parentID: "-1",
                                        orgID: rowData?.orgID
                                    },
                                    success: (obj) => {
                                        if (!valuePid) {
                                            this.setState({
                                                valuePid: obj?.[0]?.parentID,
                                                value: obj?.[0]?.id
                                            })
                                        }
                                    }
                                }}
                                //???????????? ??????{value:value,label:label,children:children}
                                keys={{
                                    label: "workName",
                                    value: "id",
                                    children: "children"
                                }}
                            />
                        </div>
                        <div className={s.rootr}>
                            {valuePid ? <QnnTable
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.tableQD = me;
                                }}
                                fetchConfig={() => {
                                    if (valuePid) {
                                        return {
                                            apiName: 'getZxCtWorksTreeList',
                                            otherParams: {
                                                parentID: valuePid === '-1' ? valuePid : value,
                                                orgID: rowData?.orgID
                                            }
                                        }
                                    }
                                }}
                                desc={<div><span style={{ color: "black" }}>????????????????????????????????????</span> <span style={{ color: "#ffd400" }}>???????????????????????????</span> <span style={{ color: "red" }}>???????????????????????????</span></div>}
                                actionBtns={drawerDetailTitle === '??????' ? [] : [
                                    {
                                        name: 'export',//??????add del
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        disabled: 'bind:exportDisabled',
                                        label: '????????????',
                                        field: 'export',
                                        onClick: 'bind:exportOnclick'
                                    },
                                    {
                                        name: 'BJXJ',//??????add del
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        disabled: 'bind:BJXJDisabled',
                                        label: '??????????????????',
                                        field: 'BJXJ',
                                        onClick: 'bind:BJXJOnclick'
                                    },
                                    {
                                        name: 'PLBJ',//??????add del
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        disabled: 'bind:PLBJDisabled',
                                        label: '????????????',
                                        field: 'PLBJ',
                                        onClick: 'bind:PLBJOnclick'
                                    },
                                    {
                                        name: 'HTSLQR',//??????add del
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        disabled: 'bind:HTSLQRDisabled',
                                        label: '??????????????????',
                                        field: 'HTSLQR',
                                        onClick: 'bind:HTSLQROnclick'
                                    },
                                    {
                                        name: 'HDSLQR',//??????add del
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        disabled: 'bind:HDSLQRDisabled',
                                        label: '??????????????????',
                                        field: 'HDSLQR',
                                        onClick: 'bind:HDSLQROnclick'
                                    },
                                    {
                                        name: 'BG',//??????add del
                                        type: 'primary',//??????  ?????? primary  [primary dashed danger]
                                        disabled: 'bind:BGDisabled',
                                        label: '??????',
                                        field: 'BG',
                                        onClick: 'bind:BGOnclick'
                                    },
                                ]}
                                method={{
                                    exportDisabled: () => {
                                        if (this.state?.QDFlagData?.pp1 === '1' || this.state?.QDFlagData?.status === '1') {
                                            return true;
                                        }
                                        return false;
                                    },
                                    exportOnclick: (obj) => {
                                        // this.tableQD.rowInfo = obj.rowInfo;
                                        // obj.btnCallbackFn.closeDrawer(true);
                                        this.setState({
                                            visible: true
                                        })
                                    },
                                    BJXJDisabled: () => {
                                        if (this.state?.QDFlagData?.pp1 === '1') {
                                            return true;
                                        }
                                        return false;
                                    },
                                    BJXJOnclick: (obj) => {
                                        if (obj.selectedRows.length === 1) {
                                            this.tableQD.rowInfo = obj.rowInfo;
                                            this.tableQD.rowData = obj.selectedRows[0];
                                            obj.btnCallbackFn.closeDrawer(true);
                                        } else {
                                            Msg.error('????????????????????????');
                                        }
                                    },
                                    PLBJDisabled: () => {
                                        if (this.state?.QDFlagData?.pp1 === '1') {
                                            return true;
                                        }
                                        return false;
                                    },
                                    PLBJOnclick: (obj) => {
                                        this.tableQD.rowInfo = obj.rowInfo;
                                        obj.btnCallbackFn.closeDrawer(true);
                                    },
                                    HTSLQRDisabled: () => {
                                        if (this.state?.QDFlagData?.pp1 === '1' || this.state?.QDFlagData?.status === '1') {
                                            return true;
                                        }
                                        return false;
                                    },
                                    HTSLQROnclick: () => {
                                        confirm({
                                            content: '?????????????????????,???????????????????',
                                            onOk: () => {
                                                let HTSLQRData = this.state?.QDFlagData;
                                                HTSLQRData.status = '1';
                                                this.setState({
                                                    QDFlagData: null,
                                                    loading: true
                                                }, () => {
                                                    this.props.myFetch('zxCtContractQuantity', HTSLQRData).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.setState({
                                                                    loading: false,
                                                                    QDFlagData: HTSLQRData
                                                                })
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );
                                                })
                                            }
                                        })
                                    },
                                    HDSLQRDisabled: () => {
                                        if (this.state?.QDFlagData?.pp1 === '1' || this.state?.QDFlagData?.status === '0') {
                                            return true;
                                        }
                                        return false;
                                    },
                                    HDSLQROnclick: () => {
                                        confirm({
                                            content: '?????????????????????,???????????????????',
                                            onOk: () => {
                                                let HTSLQRData = this.state?.QDFlagData;
                                                HTSLQRData.pp1 = '1';
                                                this.setState({
                                                    QDFlagData: null,
                                                    loading: true
                                                }, () => {
                                                    this.props.myFetch('zxCtVerificationQuantity', HTSLQRData).then(
                                                        ({ data, success, message }) => {
                                                            if (success) {
                                                                this.setState({
                                                                    loading: false,
                                                                    QDFlagData: HTSLQRData
                                                                })
                                                            } else {
                                                                Msg.error(message)
                                                            }
                                                        }
                                                    );
                                                })
                                            }
                                        })
                                    },
                                    BGDisabled: () => {
                                        if (this.state?.QDFlagData?.status === '0') {
                                            return true;
                                        }
                                        return false;
                                    },
                                    BGOnclick: (obj) => {
                                        if (obj.selectedRows.length === 1) {
                                            this.tableQD.rowInfo = obj.rowInfo;
                                            this.tableQD.rowData = obj.selectedRows[0];
                                            obj.btnCallbackFn.closeDrawer(true);
                                        } else {
                                            Msg.error('????????????????????????');
                                        }
                                    }
                                }}
                                componentsKey={{
                                    componentBG: () => {
                                        return <ComponentBG {...this.props} tableQD={this.tableQD} />;
                                    },
                                    // componentDR: () => {
                                    //     return <ComponentDR {...this.props} tableQD={this.tableQD} QDFlagData={QDFlagData} refreshs={this.refresh} />;
                                    // },
                                    componentBJXJ: () => {
                                        return <ComponentBJXJ {...this.props} tableQD={this.tableQD} QDFlagData={QDFlagData} refreshs={this.refresh} />;
                                    },
                                    componentPLBJ: () => {
                                        return <ComponentPLBJ {...this.props} tableQD={this.tableQD} QDFlagData={QDFlagData} refreshs={this.refresh} rowData={rowData} value={value} valuePid={valuePid} />;
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
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            width: 150,
                                            fixed: 'left',
                                            render: (data, rowData) => {
                                                return <div style={{ textIndent: (rowData.treeNode.length - 4) * 2 + 'px' }}>{data}</div>;
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            width: 150,
                                            fixed: 'left'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'contractQty',
                                            key: 'contractQty',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'contractPrice',
                                            key: 'contractPrice',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'contractAmt',
                                            key: 'contractAmt',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'checkQty',
                                            key: 'checkQty',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'checkAmt',
                                            key: 'checkAmt',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'quantity',
                                            key: 'quantity',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'price',
                                            key: 'price',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '???????????????',
                                            dataIndex: 'changeAmt',
                                            key: 'changeAmt',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    // {
                                    //     isInTable: false,
                                    //     form: {
                                    //         field: "componentDR",
                                    //         type: "component",
                                    //         hide: () => {
                                    //             if (this.tableQD?.rowInfo?.name === 'export') {
                                    //                 return false;
                                    //             } else {
                                    //                 return true;
                                    //             }
                                    //         },
                                    //         Component: 'componentDR'
                                    //     }
                                    // },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: "componentBJXJ",
                                            type: "component",
                                            hide: () => {
                                                if (this.tableQD?.rowInfo?.name === 'BJXJ') {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            },
                                            Component: 'componentBJXJ'
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: "componentPLBJ",
                                            type: "component",
                                            hide: () => {
                                                if (this.tableQD?.rowInfo?.name === 'PLBJ') {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            },
                                            Component: 'componentPLBJ'
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: "componentBG",
                                            type: "component",
                                            hide: () => {
                                                if (this.tableQD?.rowInfo?.name === 'BG') {
                                                    return false;
                                                } else {
                                                    return true;
                                                }
                                            },
                                            Component: 'componentBG'
                                        }
                                    }
                                ]}
                            /> : null}
                        </div>
                    </div> : null}
                </Spin>
                {visible ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="????????????"
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
                            wrappedComponentRef={(me) => {
                                this.form = me;
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
                                    field: 'workBookID',
                                    type: 'string',
                                    initialValue: this.state.QDFlagData?.id,
                                    placeholder: '?????????',
                                    hide: true,
                                },
                                {
                                    label: '????????????',
                                    field: 'inputWorkType',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    allowClear: false,
                                    initialValue: '0',
                                    optionData: [
                                        {
                                            label: '????????????',
                                            value: '0'
                                        },
                                        {
                                            label: '????????????',
                                            value: '1'
                                        },
                                        {
                                            label: '????????????',
                                            value: '2'
                                        },
                                        {
                                            label: '????????????',
                                            value: '3'
                                        }
                                    ],
                                    placeholder: '?????????',
                                    required: true
                                },
                                {
                                    type: 'component',
                                    field: 'component',
                                    Component: obj => {
                                        return (
                                            <div style={{ textAlign: 'center' }}><Button type="primary" onClick={() => {
                                                const access_token = this.props.loginAndLogoutInfo?.loginInfo?.access_token; 
                                                const templateAddress = window.configs?.templateAddress;
                                                let formData = obj.form.getFieldsValue();
                                                if (formData.inputWorkType === '0') {
                                                    confirm({
                                                        content: '??????????????????????????????????',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}????????????????????????????????????????????????-????????????.xls&access_token=${access_token}&downName=????????????????????????????????????????????????-????????????.xls`);
                                                        }
                                                    });
                                                } else if (formData.inputWorkType === '1') {
                                                    confirm({
                                                        content: '??????????????????????????????????',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}????????????????????????????????????????????????-????????????.xls&access_token=${access_token}&downName=????????????????????????????????????????????????-????????????.xls`);
                                                        }
                                                    });
                                                } else if (formData.inputWorkType === '2') {
                                                    confirm({
                                                        content: '??????????????????????????????????',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}????????????????????????????????????????????????-????????????.xls&access_token=${access_token}&downName=????????????????????????????????????????????????-????????????.xls`);
                                                        }
                                                    });
                                                } else if (formData.inputWorkType === '3') {
                                                    confirm({
                                                        content: '??????????????????????????????????',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}????????????????????????????????????????????????-????????????.xls&access_token=${access_token}&downName=????????????????????????????????????????????????-????????????.xls`);
                                                        }
                                                    });
                                                }
                                            }}>??????????????????</Button></div>
                                        );
                                    }
                                },
                                {
                                    label: '??????',
                                    field: 'attachment',
                                    type: 'files',
                                    required: true,
                                    max: 1,
                                    fetchConfig: {
                                        apiName: 'upload'
                                    }
                                }
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
                                        confirm({
                                            centered: true,
                                            content: '????????????????',
                                            onOk: () => {
                                                this.setState({
                                                    tip:'???????????????...',
                                                    loading:true,
                                                    visible:false
                                                })
                                                obj.btnfns.fetchByCb('importZxCtWorks', obj.values, (exportObj) => {
                                                    if (exportObj.success) {
                                                        Msg.success(exportObj.message);
                                                        this.setState({ tip:'Loading...' ,loading:false });
                                                        this.refresh();
                                                    } else {
                                                        this.setState({ tip:'Loading...' ,loading:false });
                                                        Msg.error(exportObj.message);
                                                    }
                                                });
                                            }
                                        })
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