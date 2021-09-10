import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Modal } from 'antd';
import { push } from "react-router-redux";
const config = {
    antd: {
        rowKey: 'providentFundManagementDetailId',
        size: 'small'
    },
    isShowRowSelect: true,
    paginationConfig: {
        position: 'bottom'
    },
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false
        }
    }
    handleCancelChange = () => {
        this.setState({ visibleBjdh: false });
    }
    accAdd(arg1, arg2) {
        var r1, r2, m;
        try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
        try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
        m = Math.pow(10, Math.max(r1, r2))

        return (arg1 * m + arg2 * m) / m
    }
    render() {
        const { clickNodeId, clickNodeName, tabId, tabName, zhujiandetail, dod } = this.props;//父子组件
        const { visibleBjdh } = this.state;
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.tableOne = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmSalaryProvidentFundManagementDetailList',
                        otherParams: {
                            wageOfProjectId: clickNodeId,
                            providentFundManagementId: zhujiandetail,//外层主键
                            programmeType: tabId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '返回',
                            isValidate: false,
                            onClick: (obj) => {
                                const { mainModule } = this.props.myPublic.appInfo;
                                this.props.dispatch(
                                    push(`${mainModule}ProvidentFundMaintenance`)
                                )
                            }
                        },
                        {
                            name: 'diy',
                            type: 'primary',
                            label: '导入',
                            onClick: () => {
                                this.setState({
                                    visibleBjdh: true
                                })
                            },
                            disabled: () => {
                                return dod === 'can' ? false : true
                            },
                        },
                        {
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            disabled: () => {
                                return dod === 'can' ? false : true
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
                                    field: 'addsubmit',
                                    fetchConfig: {
                                        apiName: 'addZjXmSalaryProvidentFundManagementDetail'
                                    }
                                }
                            ]
                        },

                        {
                            name: 'del',
                            icon: 'del',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: { apiName: 'batchDeleteUpdateZjXmSalaryProvidentFundManagementDetail' },
                            disabled: () => {
                                return dod === 'can' ? false : true
                            },
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'providentFundManagementDetailId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'name',
                                key: 'name',
                                width: 200,
                                tooltip: 20,
                                align: 'center',
                            },
                            form: {
                                label: '',
                                field: 'name',
                                required: true,
                                type: 'selectByQnnTable',
                                optionConfig: {
                                    value: 'realName',
                                    label: "realName"
                                },
                                dropdownMatchSelectWidth: 550,
                                qnnTableConfig: {
                                    antd: { rowKey: "wageOfProjectId" },
                                    fetchConfig: {
                                        apiName: "getZjXmSalaryProvidentFundManagementDetailListAdd",
                                        otherParams: {
                                            wageOfProjectId: clickNodeId
                                        }
                                    },
                                    // searchBtnsStyle: "inline",
                                    formConfig: [
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "realName",
                                                title: "姓名",
                                                width: 150,
                                                filter: true
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "idNumber",
                                                title: "身份证号",
                                                width: 220,
                                                filter: true
                                            },
                                            form: { type: 'string' }
                                        },
                                        {
                                            isInForm: false,
                                            isInSearch: true,
                                            table: {
                                                dataIndex: "userTypeName",
                                                title: "发放范围",
                                                width: 100
                                            },
                                            form: { type: 'string' }
                                        }
                                    ]
                                },
                                placeholder: '请选择',
                                onChange: (obj, rowData) => {
                                    if (obj) {
                                        // 校验一下
                                        let params = {
                                            extensionId: rowData.itemData.extensionId,
                                            providentFundManagementId: zhujiandetail
                                        };
                                        this.props.myFetch('checkAddZjXmSalaryProvidentFundManagementDetail', params).then(({ data, success, message }) => {
                                            if (success) {
                                                if (data?.check === false) {
                                                    Msg.warn(data.message);
                                                    rowData.form.setFieldsValue({
                                                        idNum: null,
                                                        issueScopeName: null,
                                                        issueScope: null,
                                                        extensionId: null,
                                                        userKey: null,
                                                        deptId: null,
                                                        deptName: null,
                                                        companyProvidentFundPayable: null,
                                                        personalProvidentFundPayable: null,
                                                        total: null
                                                    })
                                                } else {
                                                    rowData.form.setFieldsValue({
                                                        idNum: rowData.itemData.idNumber,
                                                        issueScopeName: rowData.itemData.userTypeName,
                                                        issueScope: rowData.itemData.userType,
                                                        extensionId: rowData.itemData.extensionId,
                                                        userKey: rowData.itemData.userKey,
                                                        // deptId: rowData.itemData.departmentId,//???
                                                        // deptId: rowData.itemData.wageOfProjectId,//???
                                                        wageOfProjectId: rowData.itemData.wageOfProjectId,
                                                        deptName: rowData.itemData.departmentName
                                                    })
                                                }
                                            } else {
                                                Msg.error(message)
                                            }
                                        })

                                    } else {
                                        rowData.form.setFieldsValue({
                                            idNum: null,
                                            issueScopeName: null
                                        })
                                    }

                                }
                            },
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'wageOfProjectId',//???
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'deptName',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'userKey',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'extensionId',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'issueScope',
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'providentFundManagementId',
                                initialValue: zhujiandetail,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'programmeType',
                                initialValue: tabId,
                                hide: true
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'string',
                                field: 'programmeTypeName',
                                initialValue: tabName,
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '身份证号',
                                width: 180,
                                tooltip: 18,
                                dataIndex: 'idNum',
                                key: 'idNum',
                                align: 'center',
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true
                            }
                        },
                        {
                            table: {
                                title: '发放范围',
                                dataIndex: 'issueScopeName',
                                key: 'issueScopeName',
                                width: 100,
                                align: 'center',
                            },
                            form: {
                                type: 'string',
                                required: true,
                                placeholder: '请输入',
                                addDisabled: true,
                                editDisabled: true
                            }
                        },
                        {
                            table: {
                                title: '公积金',
                                children: [
                                    {
                                        title: '单位应缴',
                                        dataIndex: 'companyProvidentFundPayable',
                                        key: 'companyProvidentFundPayable',
                                    },
                                    {
                                        title: '个人应缴',
                                        dataIndex: 'personalProvidentFundPayable',
                                        key: 'personalProvidentFundPayable',
                                    }
                                ]
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '单位应缴',
                                dataIndex: 'companyProvidentFundPayable',
                                key: 'companyProvidentFundPayable',
                            },
                            form: {
                                type: 'number',
                                required: true,
                                onBlur: (val, obj) => {
                                    const personal = obj.form.getFieldsValue().personalProvidentFundPayable;
                                    if (personal) {
                                        obj.form.setFieldsValue({
                                            total: Number(this.accAdd(obj.value, personal))
                                        })
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            table: {
                                title: '个人应缴',
                                dataIndex: 'personalProvidentFundPayable',
                                key: 'personalProvidentFundPayable',
                            },
                            form: {
                                type: 'number',
                                required: true,
                                onBlur: (val, obj) => {
                                    const personal = obj.form.getFieldsValue().companyProvidentFundPayable;
                                    if (personal) {
                                        obj.form.setFieldsValue({
                                            total: Number(this.accAdd(obj.value, personal))
                                        })
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '总计',
                                dataIndex: 'total',
                                key: 'total',
                            },
                            form: {
                                type: 'number',
                                required: true,
                                addDisabled: true,
                                editDisabled: true
                            }
                        },
                        {
                            isInForm: false,
                            table: {
                                showType: 'tile',
                                width: 80,
                                title: "操作",
                                key: "action",
                                fixed: 'right',
                                btns: [
                                    {
                                        name: "edit",
                                        render: function () {
                                            return "<a>修改</a>";
                                        },
                                        disabled: () => {
                                            return dod === 'can' ? false : true
                                        },
                                        formBtns: [
                                            {
                                                name: "cancel",
                                                type: "dashed",
                                                label: "取消"
                                            },
                                            {
                                                name: "submit",
                                                type: "primary",
                                                label: "提交",
                                                fetchConfig: {
                                                    apiName: "updateZjXmSalaryProvidentFundManagementDetail"
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    ]}
                />
                {visibleBjdh ? <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title={'导入'}
                    visible={visibleBjdh}
                    footer={null}
                    onCancel={this.handleCancelChange}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelChange}
                    wrapClassName={'modals'}
                >
                    {/* <div style={{ marginLeft: '15px' }} onClick={() => {
                        window.open(domain + 'upload/散货导入模板.xlsx')
                    }}>下载<a>模板</a></div> */}
                    <QnnForm
                        {...this.props}
                        match={this.props.match}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                label: '附件',
                                field: 'fileList',
                                required: true,
                                type: 'files',
                                fetchConfig: {
                                    apiName: "upload"
                                }
                            },

                        ]}
                        btns={[
                            {
                                name: 'cancel',
                                type: 'dashed',
                                label: '取消',
                                isValidate: false,
                                onClick: () => {
                                    this.setState({
                                        visibleBjdh: false
                                    })
                                }
                            },
                            {
                                name: 'submit',
                                type: 'primary',
                                label: '保存',
                                onClick: (obj) => {
                                    obj.values.providentFundManagementId = zhujiandetail;
                                    obj.values.programmeType = tabId;
                                    obj.values.programmeTypeName = tabName;
                                    this.props.myFetch('importZjXmSalaryProvidentFundManagementDetail', obj.values).then(
                                        ({ success, message, data }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({
                                                    visibleBjdh: false
                                                }, () => {
                                                    this.tableOne.refresh();
                                                })
                                            } else {
                                                Msg.error(message);
                                                this.setState({
                                                    visibleBjdh: false
                                                })
                                            }
                                        }
                                    );

                                }
                            }
                        ]}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 4 },
                                sm: { span: 4 }
                            },
                            wrapperCol: {
                                xs: { span: 20 },
                                sm: { span: 20 }
                            }
                        }}
                    />
                </Modal> : null}
            </div>
        );
    }
}

export default index;