import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZxBuBudgetBookList',
        otherParams: {
            budgetType: 200
        }
    },
    antd: {
        rowKey: 'zxBuBudgetBookId',
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

class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    render() {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId } = this.state
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
                    drawerShowToggle={(obj) => {
                        let { drawerIsShow } = obj;
                        if (!drawerIsShow) {
                            obj.btnCallbackFn.refresh();
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zxBuBudgetBookId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'budgetType',
                                type: 'string',
                                hide: true,
                                initialValue: 20
                            }
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'orgID',
                                key: 'orgID',
                                type: 'select',
                                align: 'center',
                            },
                            form: {
                                field: 'orgID',
                                required: true,
                                type: 'select',
                                optionConfig: {
                                    label: 'orgName',
                                    value: 'orgID'
                                },
                                editDisabled: true,
                                fetchConfig: {
                                    apiName: 'getZxBuProjectTypeCheckOver',
                                    otherParams: {
                                        orgID: departmentId,
                                    }
                                },
                                spanForm: 12,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                                onChange: (val, rowData) => {
                                    this.props.myFetch('getSysProjectDetail', { departmentId: val }).then(
                                        ({ data, success, message }) => {
                                            if (success) {
                                                this.table.qnnForm.form.setFieldsValue({
                                                    area: data.subArea,
                                                    projFea: data.provinceAbbreviation,
                                                    orgName: rowData.children
                                                })
                                            } else {
                                                this.table.qnnForm.form.setFieldsValue({
                                                    area: '',
                                                    projFea: ''
                                                })
                                                Msg.error(message)
                                            }
                                        }
                                    );

                                }
                            }
                        },
                        {
                            isInTable: false
                            , form: {
                                type: 'string',
                                field: 'orgName',
                                placeholder: '?????????',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'reportOrgID',
                                key: 'reportOrgID',
                                align: 'center'
                            },
                            form: {
                                field: 'reportOrgID',
                                type: 'string',
                                initialValue: companyName,
                                spanForm: 12,
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'projFea',
                                key: 'projFea',
                                align: 'center'
                            },
                            form: {
                                field: 'projFea',
                                type: 'string',
                                spanForm: 12,
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '??????????????????',
                                dataIndex: 'area',
                                key: 'area',
                                type: 'select',
                                align: 'center'
                            },
                            form: {
                                field: 'area',
                                type: 'select',
                                spanForm: 12,
                                addDisabled: true,
                                editDisabled: true,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId",
                                },
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",
                                    otherParams: {
                                        itemId: "suoShuQuYu",
                                    },
                                },
                                placeholder: "?????????"
                            }
                        },
                        {
                            //????????????
                            table: {
                                title: '????????????',
                                dataIndex: 'budgetAmt',
                                key: 'budgetAmt',
                                align: 'center'
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: '??????',
                                dataIndex: 'compile',
                                key: 'compile',
                                align: 'center',
                                render: (rowData) => {
                                    return '<a>??????</a>';
                                },
                                onClick: (obj) => {
                                    const { mainModule } = obj.props.myPublic.appInfo;
                                    const { orgID, zxBuBudgetBookId } = obj.rowData;
                                    obj.props.dispatch(
                                        push(`${mainModule}AfterBudgetingDetial/${orgID}/${zxBuBudgetBookId}`)
                                    )
                                },
                            },
                            isInForm: false,
                            //??????
                        },
                        {
                            table: {
                                title: '?????????',
                                dataIndex: 'reporter',
                                key: 'reporter',
                                align: 'center'
                            },
                            form: {
                                field: 'reporter',
                                type: 'string',
                                spanForm: 12,
                                initialValue: realName,
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '????????????',
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                align: 'center',
                                render: (data) => {
                                    return moment(data).format('YYYY-MM-DD')
                                }
                            },
                            form: {
                                field: 'reportDate',
                                type: 'date',
                                spanForm: 12,
                                initialValue: new Date(),
                                formItemLayoutForm: {
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 4 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 24 }
                                    }
                                },
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                label: '??????',
                                field: 'fileList',
                                type: 'files',
                                // required: true,
                                fetchConfig: {
                                    apiName: 'upload'
                                }
                            }
                        },
                    ]}
                    actionBtns={[
                        {
                            name: 'add',//??????add del
                            icon: 'plus',//icon
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            formBtns: [
                                {
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
                                },
                                {
                                    name: 'diysubmit',//??????add del
                                    type: 'primary',//??????  ?????? primary
                                    label: '??????',//???????????????????????????????????? 
                                    field: 'addsubmit',
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        myFetch('addZxBuBudgetBook', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.closeDrawer();
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
                            name: 'edit',//??????add del
                            icon: 'edit',
                            type: 'primary',//??????  ?????? primary  [primary dashed danger]
                            label: '??????',
                            formBtns: [
                                {
                                    name: 'cancel', //??????????????????
                                    type: 'dashed',//??????  ?????? primary
                                    label: '??????',
                                },
                                {
                                    name: 'diySubmit',//??????add del
                                    type: 'primary',//??????  ?????? primary
                                    label: '??????',//???????????????????????????????????? 
                                    onClick: (val) => {
                                        const { myFetch } = this.props;
                                        myFetch('updateZxBuBudgetBook', val._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
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
                            name: 'diydel',//??????add del
                            icon: 'delete',
                            type: 'danger',//??????  ?????? primary  [primary dashed danger]                                
                            label: '??????',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '?????????????????????????',
                                    onOk: () => {
                                        this.props.myFetch('batchDeleteUpdateZxBuBudgetBook', obj.selectedRows).then(({ success, message, data }) => {
                                            if (success) {
                                                obj.btnCallbackFn.clearSelectedRows();
                                                obj.btnCallbackFn.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;