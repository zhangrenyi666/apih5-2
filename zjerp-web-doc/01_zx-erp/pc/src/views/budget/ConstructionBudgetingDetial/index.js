import React, { Component } from 'react'
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Modal } from 'antd';
// import TablePage from './tablePage'
import { push } from "react-router-redux";
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: 'zxBuBudgetBookId',
        size: 'small',
        scroll: {
            y: document.documentElement.clientHeight * 0.6
        }
    },
    drawerConfig: {
        width: '1200px'
    },
    // paginationConfig: {
    //     position: 'bottom'
    // },
    paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: true,

}
class index extends Component {
    constructor(props) {
        super(props);
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;

        this.state = {
            isModalVisible: false,
            copyOrTargetStatus: null,
            firstBzOrSecondBzStatus: 'first',
            childrenData: {
                orgID: props.router.location.query.orgID,
                zxBuBudgetBookId: props.router.location.query.zxBuBudgetBookId,
            },
            confirmLoading: false,
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId
        }
    }
    showModal = () => {
        this.setState({
            isModalVisible: true
        })
    };

    handleOk = () => {
        this.setState({
            confirmLoading: true
        }, async () => {
            let params = null
            if (this.state.copyOrTargetStatus === 'copySgys') {
                params = {
                    ...await this.form1.getValues(),
                    type: 2
                }
            } else {
                params = {
                    ...await this.form2.getValues(),
                    type: 1
                }
            }
            const { success, message } = await this.props.myFetch('copyZxBuBudgetBook', params)

            if (success) {
                this.form.refresh()
                Msg.success('复制成功!')
            } else {
                Msg.error(message)
            }

            this.setState({
                confirmLoading: false,
                isModalVisible: false
            })
        })

    };

    handleCancel = () => {
        this.setState({
            isModalVisible: false
        })
    };

    childGoBack = () => {
        this.setState({
            firstBzOrSecondBzStatus: 'first'
        })
    }

    falseData = [
        {
            orgID: '1ESI040E2000040012AC0000C30F8629',
            zxBuBudgetBookId: '1387339153825468416',
            month: '期次',
            price: '预算金额',
            bz: '编制',
            zt: '状态',

        }
    ]

    render() {
        // const { companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { departmentId } = this.state
        const { childrenData } = this.state
        return (
            <div>
                {
                    // this.state.firstBzOrSecondBzStatus === 'first' ?
                    <div>
                        <QnnTable
                            fetch={this.props.myFetch}
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { this.form = me }}
                            method={{}}
                            fetchConfig={{
                                apiName: 'getZxBuBudgetBookList',
                                otherParams: {
                                    budgetType: '200',
                                    // budgetBookID: childrenData.zxBuBudgetBookId,
                                    orgID2: childrenData.orgID
                                }
                            }}
                            // data={this.falseData}
                            {...config}
                            componentsKey={{}}
                            formConfig={[
                                {
                                    isInTable: false,
                                    isInForm: false,
                                    form: {
                                        field: 'zxBuBudgetBookId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '项目',
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
                                                            projFea: data.provinceAbbreviation
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
                                    table: {
                                        title: '期次',
                                        dataIndex: 'period',
                                        key: 'period',
                                        align: 'center',
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '预算金额',
                                        dataIndex: 'budgetAmt',
                                        key: 'budgetAmt',
                                        // type: 'number',
                                        align: 'center',
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '状态',
                                        dataIndex: 'status',
                                        key: 'status',
                                        align: 'center',
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '编制',
                                        dataIndex: 'bz',
                                        key: 'bz',
                                        align: 'center',
                                        render: (val, rowData) => {
                                            return <a onClick={() => {
                                                const { mainModule } = this.props.myPublic.appInfo;
                                                this.props.dispatch(
                                                    push(`${mainModule}ConstructionBudgetingDetialList?orgID=${rowData.orgID}&zxBuBudgetBookId=${rowData.zxBuBudgetBookId}&period=${rowData.period}&status=${rowData.status === '已审核' ? '1' : '0'}`)
                                                )
                                            }
                                            } >编制</a>
                                        }
                                    },
                                    isInForm: false
                                },
                                // {
                                //     table: {
                                //         title: '状态',
                                //         dataIndex: 'zt',
                                //         key: 'zt',
                                //         type: 'string',
                                //         align: 'center',
                                //     },
                                //     isInForm: false
                                // },
                            ]}
                            actionBtns={[
                                {
                                    name: 'ditbutton',//内置add del
                                    type: 'primary',
                                    label: '复制施工预算',
                                    field: "copySgys",
                                    disabled: (obj) => {
                                        return false
                                    },
                                    onClick: (obj) => {
                                        this.setState({
                                            isModalVisible: true,
                                            copyOrTargetStatus: 'copySgys'
                                        })
                                    }
                                },
                                {
                                    name: 'ditbutton',//内置add del
                                    type: 'primary',
                                    label: '复制标后预算',
                                    field: "copyBhys",
                                    disabled: (obj) => {
                                        return false
                                    },
                                    onClick: (obj) => {
                                        this.setState({
                                            isModalVisible: true,
                                            copyOrTargetStatus: 'copyBhys'
                                        })
                                    }
                                },
                                {
                                    name: 'diydel',//内置add del
                                    icon: 'delete',
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]                                
                                    label: '删除',
                                    disabled: (obj) => {
                                        let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                                        if (SelectedRowsData.length !== 1 || SelectedRowsData[0].status === '已审核') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定删除此数据吗?',
                                            onOk: async () => {
                                                // const params = obj.qnnTableInstance.getSelectedRows()[0]
                                                const { success, message } = await this.props.myFetch('batchDeleteUpdateZxBuBudgetBook', obj.selectedRows)
                                                if (success) {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                    obj.btnCallbackFn.refresh();
                                                    Msg.success('删除成功')
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        });
                                    }
                                },
                                {
                                    name: 'ditbutton',//内置add del
                                    type: 'primary',
                                    label: '确认',
                                    field: "ok",
                                    disabled: (obj) => {
                                        let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                                        if (SelectedRowsData.length !== 1 || SelectedRowsData[0].status === '已审核') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定审核此条数据吗?',
                                            onOk: () => {
                                                this.props.myFetch('auditZxBuBudgetBook', obj.selectedRows[0]).then(({ success, message, data }) => {
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
                                },
                                {
                                    name: 'ditbutton',//内置add del
                                    type: 'dashed',
                                    label: '返回',
                                    field: "back",
                                    disabled: (obj) => {
                                        return false
                                    },
                                    onClick: (obj) => {
                                        const { mainModule } = this.props.myPublic.appInfo;
                                        this.props.dispatch(
                                            push(`${mainModule}ConstructionBudgeting`)
                                        )
                                    }
                                },
                                {
                                    name: 'ditbutton',//内置add del
                                    label: '数据更新',
                                    disabled: (obj) => {
                                        let SelectedRowsData = obj.btnCallbackFn.getSelectedRows()
                                        if (SelectedRowsData.length !== 1 || SelectedRowsData[0].status === '已审核') {
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    },
                                    onClick: (obj) => {
                                        confirm({
                                            content: '确定更新此条数据吗?',
                                            onOk: () => {
                                                this.props.myFetch('dataUpdateZxBuBudgetBookConstruction', obj.selectedRows[0]).then(({ success, message, data }) => {
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
                        <Modal title={this.state.copyOrTargetStatus === 'copySgys' ? '复制施工预算' : '复制标后预算'} confirmLoading={this.state.confirmLoading} visible={this.state.isModalVisible} onOk={this.handleOk} onCancel={this.handleCancel}>
                            {this.state.copyOrTargetStatus ? this.state.copyOrTargetStatus === 'copySgys' ? <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => { this.form1 = me }}
                                method={{}}
                                componentsKey={{}}
                                formConfig={[
                                    {
                                        label: "项目名称",
                                        field: "orgID",
                                        type: "select",
                                        disabled: true,
                                        initialValue: () => {
                                            return this.state.childrenData.orgID
                                        },
                                        optionConfig: {
                                            label: "orgName",
                                            value: "orgID",
                                            linkageFields: {
                                                orgName: "orgName",
                                                comID: "companyId",
                                                comName: "companyName",
                                            },
                                        },
                                        fetchConfig: {
                                            apiName: "getZxBuProjectTypeCheckOver",
                                            otherParams: {
                                                orgID: departmentId,
                                            },
                                        },
                                    },
                                    {
                                        type: 'quarter',
                                        label: '复制期次',
                                        field: 'copyPeriod',
                                        placeholder: '请选择',
                                        required: false,
                                    },
                                    {
                                        type: 'quarter',
                                        label: '目标期次',
                                        field: 'periodDate',
                                        placeholder: '请选择',
                                        required: false,
                                    },
                                ]}
                            /> :
                                <QnnForm
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => { this.form2 = me }}
                                    method={{}}
                                    componentsKey={{}}
                                    formConfig={[
                                        {
                                            label: "项目名称",
                                            field: "orgID",
                                            type: "select",
                                            disabled: true,
                                            initialValue: () => {
                                                return this.state.childrenData.orgID
                                            },
                                            optionConfig: {
                                                label: "orgName",
                                                value: "orgID",
                                                linkageFields: {
                                                    orgName: "orgName",
                                                    comID: "companyId",
                                                    comName: "companyName",
                                                },
                                            },
                                            fetchConfig: {
                                                apiName: "getZxBuProjectTypeCheckOver",
                                                otherParams: {
                                                    orgID: departmentId,
                                                },
                                            },
                                        },
                                        {
                                            type: 'quarter',
                                            label: '期次',
                                            field: 'periodDate',
                                            placeholder: '请选择',
                                            required: false,
                                        },
                                    ]}
                                /> : null}
                        </Modal>
                    </div>
                    // <TablePage propsData={this.props} childGoBack={this.childGoBack} childrenData={childrenData} />
                }
                {/* <div>
                    <TablePage propsData={this.props} childGoBack={this.childGoBack} />
                </div> */}
            </div>
        )
    }
}
export default index