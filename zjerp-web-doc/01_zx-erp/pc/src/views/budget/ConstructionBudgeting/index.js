import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import moment from 'moment';
import { push } from "react-router-redux";
import { message as Msg, Modal } from 'antd';
const confirm = Modal.confirm;


class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;

        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
        this.config = {
            fetchConfig: {
                apiName: 'getZxBuBudgetBookList',
                otherParams: {
                    orgID: this.state.departmentId,
                    budgetType: '20'
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
    }

    domRenderFunc = () => {
        // const rowData = {
        //     budgetElement: 0,
        //     budgetElementFirstFree: 0
        // }
        // const { budgetElement, budgetElementFirstFree } = rowData
        return (
            <div>
                <div>123</div>

            </div>
        )
    }


    render() {
        const { realName } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        const { companyName, companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
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
                    {...this.config}
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
                                initialValue: '20'
                            }
                        },
                        {
                            table: {
                                title: '项目',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                align: 'center',
                                filter: true
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
                                    this.props.myFetch('getZxCtContractDetailByOrgID', { orgID: val }).then(
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
                                placeholder: '请输入',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '编制机构',
                                dataIndex: 'reportOrgName',
                                key: 'reportOrgName',
                                align: 'center'
                            },
                            form: {
                                field: 'reportOrgName',
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
                            isInTable: false,
                            form: {
                                field: 'reportOrgID',
                                type: 'string',
                                initialValue: companyId,
                                spanForm: 12,
                                addDisabled: true,
                                editDisabled: true,
                                hide: true,
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
                                title: '项目所属省份',
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
                                title: '项目所属区域',
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
                                placeholder: "请选择"
                            }
                        },
                        {
                            //预算金额
                            table: {
                                title: '预算金额',
                                dataIndex: 'budgetAmt',
                                key: 'budgetAmt',
                                align: 'center'
                            },
                            isInForm: false,
                        },
                        {
                            table: {
                                title: '编制',
                                dataIndex: 'compile',
                                key: 'compile',
                                align: 'center',
                                render: (rowData) => {
                                    return '<a>编制</a>';
                                },
                                onClick: (obj) => {
                                    const { mainModule } = obj.props.myPublic.appInfo;
                                    const { orgID, zxBuBudgetBookId } = obj.rowData;
                                    obj.props.dispatch(
                                        push(`${mainModule}ConstructionBudgetingDetial?orgID=${orgID}&zxBuBudgetBookId=${zxBuBudgetBookId}`)
                                    )
                                },
                            },
                            isInForm: false,
                            //编制
                        },
                        {
                            table: {
                                title: '编制人',
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
                                title: '编制日期',
                                dataIndex: 'reportDate',
                                key: 'reportDate',
                                align: 'center',
                                filter: true,
                                fieldsConfig: {
                                    type: 'rangeDate',
                                    field: 'timeList'
                                },
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
                                label: '附件',
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
                                    name: 'diysubmit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    field: 'addsubmit',
                                    onClick: (obj) => {
                                        const { myFetch } = this.props;
                                        // saveZxBuBudgetBook
                                        obj.btnCallbackFn.setBtnsLoading('add', 'addsubmit');
                                        myFetch('addZxBuBudgetBook', obj._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.closeDrawer();
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                                                } else {
                                                    Msg.error(message)
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'addsubmit');
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'diySubmit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    field: 'editsubmit',
                                    onClick: (obj) => {
                                        const { myFetch } = this.props;
                                        obj.btnCallbackFn.setBtnsLoading('add', 'editsubmit');
                                        myFetch('updateZxBuBudgetBook', obj._formData).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'editsubmit');
                                                } else {
                                                    Msg.error(message)
                                                    obj.btnCallbackFn.setBtnsLoading('remove', 'editsubmit');
                                                }
                                            }
                                        );
                                    }
                                }
                            ]
                        },
                        {
                            name: 'diydel',//内置add del
                            icon: 'delete',
                            type: 'danger',//类型  默认 primary  [primary dashed danger]                                
                            label: '删除',
                            disabled: (obj) => {
                                if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                    return false;
                                } else {
                                    return true;
                                }
                            },
                            onClick: (obj) => {
                                confirm({
                                    content: '确定删除此数据吗?',
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