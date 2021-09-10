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
                                modalType="common" //common  drawer  抽屉出现方式或者普通的
                                visible
                                selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                btnShow={false} //是否显示底部按钮
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
                                //键值配置 默认{value:value,label:label,children:children}
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
                                desc={<div><span style={{ color: "black" }}>提示：黑色字体表示未变更</span> <span style={{ color: "#ffd400" }}>黄色字体表示变更中</span> <span style={{ color: "red" }}>红色字体表示变更后</span></div>}
                                actionBtns={drawerDetailTitle === '详情' ? [] : [
                                    {
                                        name: 'export',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        disabled: 'bind:exportDisabled',
                                        label: '清单导入',
                                        field: 'export',
                                        onClick: 'bind:exportOnclick'
                                    },
                                    {
                                        name: 'BJXJ',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        disabled: 'bind:BJXJDisabled',
                                        label: '编辑下级清单',
                                        field: 'BJXJ',
                                        onClick: 'bind:BJXJOnclick'
                                    },
                                    {
                                        name: 'PLBJ',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        disabled: 'bind:PLBJDisabled',
                                        label: '批量编辑',
                                        field: 'PLBJ',
                                        onClick: 'bind:PLBJOnclick'
                                    },
                                    {
                                        name: 'HTSLQR',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        disabled: 'bind:HTSLQRDisabled',
                                        label: '合同数量确认',
                                        field: 'HTSLQR',
                                        onClick: 'bind:HTSLQROnclick'
                                    },
                                    {
                                        name: 'HDSLQR',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        disabled: 'bind:HDSLQRDisabled',
                                        label: '核定数量确认',
                                        field: 'HDSLQR',
                                        onClick: 'bind:HDSLQROnclick'
                                    },
                                    {
                                        name: 'BG',//内置add del
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        disabled: 'bind:BGDisabled',
                                        label: '变更',
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
                                            Msg.error('请选择一条数据！');
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
                                            content: '确认后不可修改,确定要确认吗?',
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
                                            content: '确认后不可修改,确定要确认吗?',
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
                                            Msg.error('请选择一条数据！');
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
                                            title: '清单编号',
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
                                            title: '清单名称',
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            width: 150,
                                            fixed: 'left'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '计量单位',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '合同数量',
                                            dataIndex: 'contractQty',
                                            key: 'contractQty',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '合同单价',
                                            dataIndex: 'contractPrice',
                                            key: 'contractPrice',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '合同金额',
                                            dataIndex: 'contractAmt',
                                            key: 'contractAmt',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '核定数量',
                                            dataIndex: 'checkQty',
                                            key: 'checkQty',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '核定金额',
                                            dataIndex: 'checkAmt',
                                            key: 'checkAmt',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '变更后数量',
                                            dataIndex: 'quantity',
                                            key: 'quantity',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '变更后单价',
                                            dataIndex: 'price',
                                            key: 'price',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '变更后金额',
                                            dataIndex: 'changeAmt',
                                            key: 'changeAmt',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '备注',
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
                        title="清单导入"
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
                                    placeholder: '请输入',
                                    hide: true,
                                },
                                {
                                    label: '清单类型',
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
                                            label: '公路清单',
                                            value: '0'
                                        },
                                        {
                                            label: '铁路清单',
                                            value: '1'
                                        },
                                        {
                                            label: '市政清单',
                                            value: '2'
                                        },
                                        {
                                            label: '房建清单',
                                            value: '3'
                                        }
                                    ],
                                    placeholder: '请选择',
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
                                                        content: '确定下载公路清单模板吗?',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}【业主合同台账】模块清单导入模板-公路清单.xls&access_token=${access_token}&downName=【业主合同台账】模块清单导入模板-公路清单.xls`);
                                                        }
                                                    });
                                                } else if (formData.inputWorkType === '1') {
                                                    confirm({
                                                        content: '确定下载铁路清单模板吗?',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}【业主合同台账】模块清单导入模板-铁路清单.xls&access_token=${access_token}&downName=【业主合同台账】模块清单导入模板-铁路清单.xls`);
                                                        }
                                                    });
                                                } else if (formData.inputWorkType === '2') {
                                                    confirm({
                                                        content: '确定下载市政清单模板吗?',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}【业主合同台账】模块清单导入模板-市政清单.xls&access_token=${access_token}&downName=【业主合同台账】模块清单导入模板-市政清单.xls`);
                                                        }
                                                    });
                                                } else if (formData.inputWorkType === '3') {
                                                    confirm({
                                                        content: '确定下载房建清单模板吗?',
                                                        onOk: () => {
                                                            window.open(`${templateAddress}【业主合同台账】模块清单导入模板-房建清单.xls&access_token=${access_token}&downName=【业主合同台账】模块清单导入模板-房建清单.xls`);
                                                        }
                                                    });
                                                }
                                            }}>导入模板下载</Button></div>
                                        );
                                    }
                                },
                                {
                                    label: '附件',
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
                                        confirm({
                                            centered: true,
                                            content: '确定导入吗?',
                                            onOk: () => {
                                                this.setState({
                                                    tip:'数据导入中...',
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