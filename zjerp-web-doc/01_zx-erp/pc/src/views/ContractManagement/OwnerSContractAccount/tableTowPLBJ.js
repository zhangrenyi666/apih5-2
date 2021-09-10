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
            QDFlagData:props.QDFlagData || {},
            valuePid:props.valuePid || '',
            value:props.value || '',
            rowData:props.rowData || {}
        }
    }
    render() {
        const { QDFlagData, rowData, valuePid, value } = this.state;
        return (
            <div>
                {this.tableQD?.rowInfo?.name === 'PLBJ' ? <QnnForm
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                        token: this.props.loginAndLogoutInfo.loginInfo.token
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
                            field: 'delList',
                            type: 'qnnTable',
                            incToForm: true,
                            qnnTableConfig: {
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                fetchConfig:{}
                            },
                            hide: true,
                        },
                        {
                            field: "PLBJ",
                            type: "qnnTable",
                            incToForm: true,
                            initialValue: () => {
                                return this.tableQD?.getTableData?.()
                            },
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
                                getRowSelection: (obj) => {
                                    return {
                                        getCheckboxProps: record => ({
                                            name: record.name,
                                            disabled: record.isLeaf === 0 || record.parentID === '-1'
                                        }),
                                    }
                                },
                                actionBtnsPosition: "top",
                                antd: {
                                    rowKey: 'id',
                                    size: 'small'
                                },
                                paginationConfig: false,
                                pageSize:999999,
                                wrappedComponentRef: (me) => {
                                    this.tablePLBJ = me;
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
                                            title: '清单编号',
                                            dataIndex: 'workNo',
                                            key: 'workNo',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            disabled: (obj) => {
                                                if (obj.record.parentID === '-1' || QDFlagData?.status === '1') {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '清单名称',
                                            dataIndex: 'workName',
                                            key: 'workName',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            disabled: (obj) => {
                                                if (obj.record.parentID === '-1' || QDFlagData?.status === '1') {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '计量单位',
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
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '工程量',
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
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '合同单价',
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
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '核定数量',
                                            dataIndex: 'checkQty',
                                            key: 'checkQty',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'number',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            tdEdit: true
                                        },
                                        form: {
                                            type: 'string',
                                            placeholder: '请输入'
                                        }
                                    }
                                ],
                                actionBtns: [
                                    {
                                        name: "save",
                                        type: "primary",
                                        label: "保存",
                                        onClick: (obj) => {
                                            confirm({
                                                content: '确定保存数据吗?',
                                                onOk: () => {
                                                    let delData = obj.props.qnnFormProps.form.getFieldValue('delList');
                                                    let tableQDRowData = this.tableQD.getVTableData()[0];
                                                    let body = {
                                                        orgID: tableQDRowData.orgID,
                                                        treeNode: tableQDRowData.treeNode,
                                                        children: delData?.length ? obj.getVTableData().concat(delData) : obj.getVTableData()
                                                    }
                                                    this.props.myFetch('saveZxCtWorksList', body).then(
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
                                        label: '删除',
                                        hide: () => {
                                            if (QDFlagData?.status === '1') {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            let delLists = obj.props.qnnFormProps.form.getFieldValue('delList') || [];
                                            let delList = obj.selectedRows.map((item) => {
                                                item.addFlag = '2';
                                                return item;
                                            })
                                            delLists = delLists.concat(delList);
                                            obj.props.qnnFormProps.form.setFieldsValue({
                                                delList: delLists
                                            })
                                            let tablePLBJData = this.tablePLBJ.getVTableData();
                                            delLists = delLists.concat(tablePLBJData);                                                                                    //orgID: props.initialValues.orgID
                                            this.props.myFetch('getZxCtWorksTreeList', { parentID: valuePid === '-1' ? valuePid : value, orgID: rowData?.orgID }).then(
                                                ({ data, success, message }) => {
                                                    if (success) {
                                                        data = data.filter(item => !delLists.some(ele => ele.id === item.id));
                                                        tablePLBJData = tablePLBJData.map((item) => {
                                                            if (!data.filter((items) => item.id === items.parentID).length) {
                                                                item.isLeaf = 1;
                                                            }
                                                            return item;
                                                        })
                                                        this.tablePLBJ.setVTableData(tablePLBJData);
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
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