//班组信息
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
const config = {
    fetchConfig: {
        apiName: 'getZjLzehTeamInformationList'
    },
    antd: {
        rowKey: function (row) {
            return row.teamInformationId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1100px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    firstRowIsSearch: false,
    isShowRowSelect: true,
    paginationConfig: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleSend: false,
            loadingSend: false,
            teamInformationId: '',
            teamName: '',
            teamList: [],
            section: '',
        }
    }
    componentDidMount() { }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { visibleSend, loadingSend, teamInformationId, teamName, teamList, section } = this.state;
        return (
            <div>
                <QnnTable
                     {...this.props}
                     fetch={this.props.myFetch} 
                     headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    actionBtns={[
                        {
                            field: 'add',
                            name: 'add',
                            icon: 'plus',
                            type: 'primary',
                            label: '新增',
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
                                        apiName: 'addZjLzehTeamInformation'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'edit',
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
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
                                    fetchConfig: {
                                        apiName:'updateZjLzehTeamInformation'
                                    }
                                }
                            ]
                        },
                        {
                            field: 'del',
                            name: 'del',
                            icon: 'delete',
                            type: 'danger',
                            label: '删除',
                            fetchConfig: {
                                apiName: 'batchDeleteUpdateZjLzehTeamInformation',
                            }
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label:'主键id',
                                field: 'teamInformationId',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '排序',
                                dataIndex: 'orderFlag',
                                key: 'orderFlag',
                                align: 'center',
                                width:100,
                            },
                            form: {
                                label: '排序',
                                field: 'orderFlag',
                                type:'number'
                            },
                        },
                        {
                            table: {
                                title: '班组名称',
                                dataIndex: 'teamName',
                                // tooltip:7,
                                key: 'teamName',
                                align: 'center',
                                
                            },
                            form: {
                                label: '班组名称',
                                field: 'teamName',
                                type:'string'
                            },
                        },
                        {
                            table: {
                                title: 'A段人数',
                                dataIndex: 'aSectionPeopleNumber',
                                key: 'aSectionPeopleNumber',
                                align: 'center',
                                width:100,
                            },
                            form: {
                                label: 'A段人数',
                                field: 'aSectionPeopleNumber',
                                type:'number'
                            },
                        },
                        {
                            table: {
                                title: 'B段人数',
                                dataIndex: 'bSectionPeopleNumber',
                                key: 'bSectionPeopleNumber',
                                align: 'center',
                                width:100,
                            },
                            form: {
                                label: 'B段人数',
                                field: 'bSectionPeopleNumber',
                                type:'number'
                            },
                        },
                    ]}
                    // actionBtns={[
                    //     {
                    //         name: 'add',
                    //         icon: 'plus',
                    //         type: 'primary',
                    //         label: '新增',
                    //         field: 'addOutBtn',
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel',
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 fetchConfig: {
                    //                     apiName: 'addZjLzehTeamInformation'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'edit',
                    //         type: 'primary',
                    //         label: '修改',
                    //         editDisabled:false,
                    //         onClick: (obj) => {
                    //             this.table.clearSelectedRows();
                    //         },
                    //         formBtns: [
                    //             {
                    //                 name: 'cancel', 
                    //                 type: 'dashed',
                    //                 label: '取消',
                    //             },
                    //             {
                    //                 name: 'submit',
                    //                 type: 'primary',
                    //                 label: '保存',
                    //                 fetchConfig: {
                    //                     apiName: 'updateZjLzehTeamInformation'
                    //                 }
                    //             }
                    //         ]
                    //     },
                    //     {
                    //         name: 'del',
                    //         icon: 'delete',
                    //         type: 'danger',
                    //         label: '删除',
                    //         fetchConfig: {
                    //             apiName: 'batchDeleteUpdateZjLzehTeamInformation'
                    //         },
                    //     }
                    // ]}
                />
                {/* <Modal
                    width='600px'
                    style={{ top: '0' }}
                    title="选择班组"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '600px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSend}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    field: "teamInformationId",
                                    hide: true,
                                    initialValue: teamInformationId,
                                },
                                {
                                    field: "section",
                                    hide: true,
                                    initialValue: section
                                },
                                {
                                    label: "班组名称",
                                    field: 'teamName',
                                    type: 'string',
                                    disabled: true,
                                    initialValue: teamName,
                                },
                                {
                                    label: '已选择班组',
                                    field: 'teamList',
                                    type: 'treeSelect',
                                    initialValue: teamList,
                                    treeSelectOption: {
                                        fetchConfig: {
                                            apiName: 'getZjLzehTeamList',
                                        },
                                        k: {
                                            value: 'teamId',
                                            label: 'teamName',
                                        },
                                        
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    field:'no',
                                    label: '取消',
                                    isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleSend: false,
                                            loadingSend: false,
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    field:'no',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch('updateZjLzehTeamInformationTeam', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false,
                                                    })
                                                } else {
                                                    Msg.error(message)
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
                    </Spin>
                </Modal> */}
            </div>
        );
    }
}

export default index;