import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import { push } from "react-router-redux";
const config = {
    antd: {
        rowKey: 'extensionId',
        size: 'small'
    },
    isShowRowSelect: true,
    paginationConfig: {
        position: 'bottom'
    },
};
const configBh = {
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleBjdh: false,
            loadingBjdh: false
        }
    }
    handleCancelChange = () => {
        this.setState({ visibleBjdh: false, loadingBjdh: false });
    }
    render() {
        const { compensationPlanId, clickNodeId, planName, taxTypeId } = this.props;
        const { visibleBjdh, loadingBjdh } = this.state;
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
                        apiName: 'getZjXmSalaryCompensationPlanPersonnelList',
                        otherParams: {
                            compensationPlanId: compensationPlanId,//外层表格id
                            wageOfProjectId: clickNodeId,//树id
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
                                    push(`${mainModule}CompensationPlan`)
                                )
                            }
                        },
                        {
                            name: 'diyAdd',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
                            onClick: (obj) => {
                                this.setState({
                                    visibleBjdh: true
                                });
                            }
                        },
                        {
                            name: 'del',
                            icon: 'del',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: { apiName: 'batchDeleteUpdateZjXmSalaryCompensationPlanPersonnel' }
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'extensionId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'wageOfProjectId',//树id
                                type: 'string',
                                initialValue: clickNodeId,
                                hide: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'compensationPlanId',//外层表格id
                                type: 'string',
                                initialValue: compensationPlanId,
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
                            isInForm: false
                        },
                        {
                            table: {
                                title: '身份证号',
                                width: 180,
                                tooltip: 18,
                                dataIndex: 'idNum',
                                key: 'idNum'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发薪范围',
                                dataIndex: 'issueScope',
                                key: 'issueScope',
                                type: 'select',
                            },
                            form: {
                                field: 'issueScope',
                                type: 'select',
                                required: true,
                                placeholder: '请选择',
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'faXinFanWei'
                                    }
                                },
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId'
                                },
                            }
                        },
                        {
                            table: {
                                title: '所属项目',
                                dataIndex: 'projectName',
                                key: 'projectName'
                            },
                            form: {
                                field: 'projectName',
                                type: 'string'
                            }
                        },
                        {
                            table: {
                                title: '性别',
                                dataIndex: 'gender',
                                key: 'gender',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'gender',
                                optionConfig: {
                                    value: 'value',
                                    label: 'name'
                                },
                                optionData: [
                                    {
                                        value: '0',
                                        name: '男'
                                    },
                                    {
                                        value: '1',
                                        name: '女'
                                    }
                                ]
                            }
                        },
                        {
                            table: {
                                title: '岗位',
                                dataIndex: 'userPost',
                                key: 'userPost'
                            },
                            form: {
                                type: 'string',
                                field: 'userPost'
                            }
                        },
                        {
                            table: {
                                title: '岗薪等级',
                                dataIndex: 'postLevel',
                                key: 'postLevel',
                                width: 200,
                                type: 'cascader',
                            },
                            form: {
                                type: 'cascader',
                                field: 'postLevel',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value',
                                    children: 'showData',
                                },
                                fetchConfig: {
                                    apiName: "getZjXmSalaryPositionLevelSalarySelect",
                                    otherParams: {
                                        itemId: 'gongrenzhongzhong'
                                    }
                                },
                            }
                        },
                        {
                            table: {
                                title: '岗薪基数',
                                dataIndex: 'positionSalary',
                                key: 'positionSalary',
                                width: 200
                            },
                            form: {
                                type: 'string',
                                field: 'positionSalary'
                            }
                        },
                    ]}
                />
                {visibleBjdh ? <Modal
                    width='1200px'
                    style={{ top: '0' }}
                    title="人员信息"
                    visible={visibleBjdh}
                    footer={null}
                    destroyOnClose={this.handleCancelChange}
                    onCancel={this.handleCancelChange}
                    bodyStyle={{
                        width: '1200px'
                    }}
                    centered={true}
                    destroyOnClose={this.handleCancelChange}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingBjdh}>
                        <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.tableBh = me;
                            }}
                            {...configBh}
                            antd={{
                                rowKey: 'extensionId',
                                size: 'small',
                                scroll: {
                                    y: document.documentElement.clientHeight * 0.6
                                }
                            }}
                            fetchConfig={() => {
                                return {
                                    apiName: 'getZjXmSalaryCompensationPlanPersonnelListAdd',
                                    otherParams: {
                                        wageOfProjectId: clickNodeId,
                                        // compensationPlanId: compensationPlanId,
                                        // planName: planName
                                    }
                                }
                            }}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'extensionId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'planName',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '姓名',
                                        dataIndex: 'realName',
                                        key: 'realName',
                                        width: 200,
                                        tooltip: 20,
                                        align: 'center',
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '身份证号',
                                        width: 180,
                                        tooltip: 18,
                                        dataIndex: 'idNumber',
                                        key: 'idNumber'
                                    },
                                    isInForm: false
                                },
                                {
                                    table: {
                                        title: '发薪范围',
                                        dataIndex: 'userType',
                                        key: 'userType',
                                        type: 'select',
                                    },
                                    form: {
                                        field: 'userType',
                                        type: 'select',
                                        required: true,
                                        placeholder: '请选择',
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'faXinFanWei'
                                            }
                                        },
                                        optionConfig: {
                                            label: 'itemName',
                                            value: 'itemId'
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '所属项目',
                                        dataIndex: 'departmentName',
                                        key: 'departmentName'
                                    },
                                    form: {
                                        field: 'departmentName',
                                        type: 'string'
                                    }
                                },
                                {
                                    table: {
                                        title: '性别',
                                        dataIndex: 'gender',
                                        key: 'gender',
                                        type: 'select',
                                    },
                                    form: {
                                        type: 'select',
                                        field: 'gender',
                                        optionConfig: {
                                            value: 'value',
                                            label: 'name'
                                        },
                                        optionData: [
                                            {
                                                value: '0',
                                                name: '男'
                                            },
                                            {
                                                value: '1',
                                                name: '女'
                                            }
                                        ]
                                    }
                                },
                                {
                                    table: {
                                        title: '是否离职',
                                        dataIndex: 'isQuit',
                                        key: 'isQuit',
                                        type: 'select',
                                    },
                                    form: {
                                        type: 'select',
                                        field: 'isQuit',
                                        optionConfig: {
                                            value: 'value',
                                            label: 'name'
                                        },
                                        optionData: [
                                            {
                                                value: '1',
                                                name: '否'
                                            },
                                            {
                                                value: '2',
                                                name: '是'
                                            }
                                        ]
                                    }
                                },
                                {
                                    table: {
                                        title: '岗位',
                                        dataIndex: 'positionName',
                                        key: 'positionName'
                                    },
                                    form: {
                                        type: 'string',
                                        field: 'positionName'
                                    }
                                },
                                {
                                    table: {
                                        title: '岗薪等级',
                                        dataIndex: 'levelSalaryId',
                                        key: 'levelSalaryId',
                                        width: 200,
                                        type: 'cascader',
                                    },
                                    form: {
                                        type: 'cascader',
                                        field: 'levelSalaryId',
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                            children: 'showData',
                                        },
                                        fetchConfig: {
                                            apiName: "getZjXmSalaryPositionLevelSalarySelect",
                                            otherParams: {
                                                itemId: 'gongrenzhongzhong'
                                            }
                                        },
                                    }
                                },

                                {
                                    table: {
                                        title: '岗薪基数',
                                        dataIndex: 'positionSalary',
                                        key: 'positionSalary',
                                        width: 200
                                    },
                                    form: {
                                        type: 'string',
                                        field: 'positionSalary'
                                    }
                                },
                            ]}
                            actionBtns={[
                                {
                                    name: 'diyAddModal',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        let objVa = obj.btnCallbackFn.getSelectedRows();
                                        if (objVa.length > 0) {
                                            objVa.map(item => {
                                                item.compensationPlanId = compensationPlanId;
                                                item.wageOfProjectId = item.wageOfProjectId;
                                                item.deptName = item.departmentName;
                                                item.userKey = item.userKey;
                                                item.name = item.realName;
                                                item.idNum = item.idNumber;
                                                item.issueScope = item.userType;
                                                item.issueScopeName = item.userTypeName;
                                                item.userPost = item.positionName;
                                                item.postLevel = item.levelSalaryId;
                                                item.positionSalary = item.positionSalary;
                                                item.extensionId = item.extensionId;
                                                item.projectId = item.orgId;
                                                item.projectName = item.orgName;
                                                item.planName = planName;
                                                item.taxTypeId = taxTypeId;
                                                return item
                                            })
                                            this.props.myFetch('batchAddZjXmSalaryCompensationPlanPersonnel', objVa).then(
                                                ({ success, message, data }) => {
                                                    if (success) {
                                                        Msg.success(message)
                                                        this.setState({
                                                            loadingBjdh: false,
                                                            visibleBjdh: false
                                                        }, () => {
                                                            this.tableOne.refresh();
                                                        })
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        } else {
                                            Msg.warn('未选择数据');
                                        }

                                    }
                                },
                            ]}
                        />
                    </Spin>
                </Modal> : null}
            </div>
        );
    }
}

export default index;