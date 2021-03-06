import React, { Component } from "react";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
class index extends Component {
    constructor(props) {
        super(props);
        this.tableQD = props.tableQD;
        this.refresh = props.refreshs;
        this.state = {
            QDFlagData:props.QDFlagData || {}
        }
    }
    render() {
        const { QDFlagData } = this.state;
        return (
            <div>
                {this.tableQD?.rowInfo?.name === 'BJXJ' ? <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    wrappedComponentRef={(me) => {
                        this.form = me;
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 3 },
                            sm: { span: 3 }
                        },
                        wrapperCol: {
                            xs: { span: 21 },
                            sm: { span: 21 }
                        }
                    }}
                    formConfig={[
                        {
                            field: "BJXJ",
                            type: "qnnTable",
                            incToForm: true,
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
                                    if (rowData?.orgID && rowData?.id) {
                                        return {
                                            apiName: 'getZxCtWorksTreeList',
                                            otherParams: {
                                                parentID: rowData.id,
                                                orgID: rowData.orgID
                                            }
                                        }
                                    }
                                },
                                actionBtnsPosition: "top",
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: false,
                                pageSize: 999999,
                                // paginationConfig: {
                                //     position: 'bottom'
                                // },
                                wrappedComponentRef: (me) => {
                                    this.tableBJXJ = me;
                                },
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
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            disabled: () => {
                                                if (QDFlagData?.status === '1') {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            disabled: () => {
                                                if (QDFlagData?.status === '1') {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'unit',
                                            key: 'unit',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            disabled: () => {
                                                if (QDFlagData?.status === '1') {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '?????????',
                                            dataIndex: 'contractQty',
                                            key: 'contractQty',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'number',
                                            disabled: () => {
                                                if (QDFlagData?.status === '1') {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'contractPrice',
                                            key: 'contractPrice',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'number',
                                            disabled: () => {
                                                if (QDFlagData?.status === '1') {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '????????????',
                                            dataIndex: 'checkQty',
                                            key: 'checkQty',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '?????????'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '??????',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '?????????'
                                        }
                                    }
                                ],
                                actionBtns: [
                                    {
                                        name: "addRow",
                                        icon: "plus",
                                        type: "primary",
                                        label: "?????????",
                                        hide: () => {
                                            if (this.state.QDFlagData?.status === '1') {
                                                return true;
                                            }
                                        }
                                    },
                                    {
                                        name: "save",
                                        type: "primary",
                                        label: "??????",
                                        onClick: (obj) => {
                                            confirm({
                                                content: '??????????????????????',
                                                onOk: () => {
                                                    let tableQDRowData = this.tableQD?.rowData;
                                                    let childrenData = obj.getVTableData().map((item) => {
                                                        if (!item.addFlag) {
                                                            item.parentID = tableQDRowData.id;
                                                            item.addFlag = '1';
                                                            item.orgID = tableQDRowData.orgID;
                                                            item.treeNode = tableQDRowData.treeNode;
                                                            item.workBookID = tableQDRowData.workBookID;
                                                        }
                                                        return item;
                                                    })
                                                    let body = {
                                                        parentID: tableQDRowData.id,
                                                        treeNode: tableQDRowData.treeNode,
                                                        children: childrenData
                                                    }
                                                    this.props.myFetch('updateZxCtWorksList', body).then(
                                                        (treeObj) => {
                                                            if (treeObj.success) {
                                                                Msg.success(treeObj.message);
                                                                this.tableQD.rowInfo = null;
                                                                this.tableQD.closeDrawer(false);
                                                                this.refresh();
                                                            } else {
                                                                Msg.error(treeObj.message);
                                                            }
                                                        }
                                                    );
                                                }
                                            });
                                        }
                                    },
                                    {
                                        name: 'del',
                                        icon: 'delete',
                                        type: 'danger',
                                        label: '??????',
                                        hide: () => {
                                            if (this.state.QDFlagData?.status === '1') {
                                                return true;
                                            }
                                        }
                                    }
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