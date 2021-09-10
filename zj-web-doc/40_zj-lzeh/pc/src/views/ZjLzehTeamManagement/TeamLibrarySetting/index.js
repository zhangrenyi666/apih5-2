import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Checkbox, Button, Table } from 'antd';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            teamNameQuery: null
        }
    }
    render() {
        const { teamNameQuery } = this.state
        return (
            <div>
                <QnnForm
                    wrappedComponentRef={(me) => {
                        this.formOne = me;
                    }}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 12 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 12 }
                        }
                    }}
                    formConfig={
                        [
                            {
                                type: 'string',
                                label: '班组名称',
                                // tooltip: 7,
                                field: 'teamName', //唯一的字段名 ***必传
                                placeholder: '请选择',
                                required: false,
                                // format: "YYYY-MM",
                                // showTime: false, //不显示时间
                                // scope: false, //是否可选择范围
                                span: 8
                            },
                            {
                                type: 'component',
                                field: 'diy',
                                span: 6,
                                //第一种，推荐定义方式 需要将componentsKey对象传到qnn-form
                                Component: "myDiyComponent",

                                //第二种自定义组件方式
                                Component: obj => {
                                    return (
                                        <div style={{ height: '100%', display: 'flex', alignItems: 'center' }}>
                                            <Button type="primary" onClick={() => {
                                                this.table.clearSelectedRows();
                                                this.formOne.getValues().then(val => {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            teamNameQuery: val.teamName
                                                        })

                                                        // this.table.refresh()
                                                    }, 0)

                                                    // if (teamNameQuery === val.teamName && !this.table.getTableData().length) {
                                                    //     Msg.warning('没有此班组')
                                                    // }

                                                    if (teamNameQuery === val.teamName && !this.table.state.data.length) {
                                                        Msg.warning('没有此班组')
                                                    }
                                                })
                                            }}>查询</Button>
                                        </div>
                                    );
                                }
                            }
                        ]
                    }
                ></QnnForm>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    antd={
                        {
                            rowKey: 'zjLzehTeamManagementId',
                            size: 'small'
                        }
                    }
                    isShowRowSelect={true}
                    actionBtns={[
                        {
                            field: 'add',
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
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
                                        apiName: 'addZjLzehTeamManagement'
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
                            onClick: (obj) => {
                                

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
                                    fetchConfig: 'bind:updateFunc'
                                }
                            ]
                        },
                        {
                            field: 'del',
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjLzehTeamManagement',
                            }
                        }
                    ]}

                    fetchConfig={
                        teamNameQuery ? {
                            apiName: 'getZjLzehTeamManagementList',
                            otherParams: {
                                teamName: teamNameQuery
                            },
                            success: (res) => {
                                const { data } = res
                                if (!data.length) {
                                    Msg.warning('没有此班组')
                                }
                            }
                        } : { apiName: 'getZjLzehTeamManagementList', }
                    }
                    method={{
                        updateFunc: (obj) => {
                            const isScore = obj.btnCallbackFn.getSelectedRows()[0].isScore
                            return {
                                apiName: 'updateZjLzehTeamManagement',
                                otherParams: {
                                    isScore
                                }
                            }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehTeamManagementId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            table: {
                                title: '序号',
                                dataIndex: 'index',
                                key: 'index',
                                align: 'center',
                                width: 50,
                                fixed: 'left',
                                render: (data, rowData, index) => {
                                    return index + 1;
                                }
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '公司名称',
                                dataIndex: 'companyName',
                                key: 'companyName',
                                onClick: 'detail',
                                width: 150,
                            },
                            form: {
                                field: 'companyName',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'companyAbbreviation',
                                label: '公司简称',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '班组名称',
                                dataIndex: 'teamName',
                                key: 'teamName',
                                width: 150,
                            },
                            form: {
                                field: 'teamName',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                field: 'teamAbbreviation',
                                label: '班组简称',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '班组组长',
                                dataIndex: 'teamLeader',
                                key: 'teamLeader',
                                width: 150,
                            },
                            form: {
                                field: 'teamLeader',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '班组组长联系方式',
                                dataIndex: 'phone',
                                key: 'phone',
                                width: 150,
                            },
                            form: {
                                field: 'phone',
                                type: 'string',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '班组人数',
                                dataIndex: 'teamPerson',
                                key: 'teamPerson',
                                width: 150,
                            },
                            form: {
                                field: 'teamPerson',
                                type: 'number',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '是否评分',
                                dataIndex: 'isScore',
                                key: 'isScore',
                                width: 150,
                                render: (data, rowData, index) => {
                                    return (
                                        <Checkbox defaultChecked={rowData.isScore === '1' ? true : false} onChange={(e) => {
                                            const chenckedStatus = e.target.checked
                                            const { companyName, companyAbbreviation, teamName, teamAbbreviation, phone, teamLeader, teamPerson, zjLzehTeamManagementId } = rowData
                                            let isScore = '0'
                                            if (chenckedStatus) {
                                                isScore = '1'
                                            } else {
                                                isScore = '0'
                                            }
                                            this.props.myFetch('updateZjLzehTeamManagement', {
                                                companyName, companyAbbreviation, teamName, teamAbbreviation, phone, teamLeader, teamPerson, zjLzehTeamManagementId, isScore
                                            }).then(
                                                ({ success, message, data }) => {
                                                    if (success) {
                                                        this.table.refresh()
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            )
                                        }
                                        } ></Checkbox>
                                    )
                                }
                            },
                            isInForm: false
                        },
                    ]}
                ></QnnTable >
            </div>
        )
    }
}

export default index
