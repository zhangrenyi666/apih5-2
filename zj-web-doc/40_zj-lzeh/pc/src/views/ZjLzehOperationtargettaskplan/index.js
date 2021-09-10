//安全检查管理
import React, {
    Component
} from "react";
import QnnTable from "../modules/qnn-table";
import { push } from "react-router-redux";
import { message as Msg, Button, Modal } from 'antd';
import QnnForm from "../modules/qnn-table/qnn-form";
import ImportCom from '../ZjLzehOperationtargettaskplanimport'
const confirm = Modal.confirm;
const config = {
    antd: {
        rowKey: "zjLzehManageTaskPlanId",
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
    // paginationConfig: true
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            importOrAdd: true,
            cenMonth: null
        }
    }
    getChildrenComShowStatus = (result, data) => {
        this.setState({
            importOrAdd: data
        })
    }
    primaryKey = ''
    render() {
        const {
            mainModule
        } = this.props.myPublic.appInfo;
        const { importOrAdd, cenMonth } = this.state
        return (
            <div>

                {
                    importOrAdd ? <div>
                        <QnnForm
                            wrappedComponentRef={(me) => {
                                this.formOne = me;
                            }}
                            formConfig={
                                [
                                    {
                                        type: 'month',
                                        label: '月份',
                                        field: 'month', //唯一的字段名 ***必传
                                        placeholder: '请选择',
                                        required: false,
                                        format: "YYYY-MM",
                                        showTime: false, //不显示时间
                                        scope: false, //是否可选择范围
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
                                                                    cenMonth: val.month
                                                                })
                                                                // this.table.refresh()
                                                            })
                                                            // if (cenMonth === val.month && !this.table.getTableData().length) {
                                                            //     Msg.warning('当前月份暂无数据')
                                                            // }

                                                            if (cenMonth === val.month && !this.table.state.data.length) {
                                                                Msg.warning('当前月份暂无数据')
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
                        <QnnTable {
                            ...this.props
                        }
                            fetch={
                                this.props.myFetch
                            }
                            headers={
                                {
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }
                            }
                            wrappedComponentRef={
                                (me) => {
                                    this.table = me;
                                }
                            } {
                            ...config
                            }
                            fetchConfig={
                                cenMonth ?
                                    {
                                        apiName: 'getZjLzehManageTaskPlanList',
                                        otherParams: {
                                            month: cenMonth
                                        },
                                        success: (res) => {
                                            const { data } = res
                                            if (!data.length) {
                                                Msg.warning('当前月份暂无数据')
                                            }
                                        }
                                    } : { apiName: 'getZjLzehManageTaskPlanList' }
                            }
                            formConfig={
                                [
                                    // {
                                    //     table: {
                                    //         title: '序号',
                                    //         dataIndex: 'sort',
                                    //         key: 'sort',
                                    //         align: 'center',
                                    //         width: 100,
                                    //     },
                                    //     isInForm: false
                                    // },
                                    {
                                        table: {
                                            title: '月份',
                                            dataIndex: 'month',
                                            key: 'month',
                                            align: 'center',
                                            format: 'YYYY-MM',
                                        },
                                        form: {
                                            label: '月份',
                                            required: true,
                                            field: "month",
                                            type: 'month',
                                            placeholder: '请选择',
                                            spanFrom: 12,
                                            initialValue: new Date(),
                                            editDisabled: true, //修改禁用
                                        },
                                    },
                                    {
                                        table: {
                                            title: '任务数',
                                            dataIndex: 'taskQty',
                                            key: 'taskQty',
                                            align: 'center',
                                            // width: 300,
                                        },
                                        // isInForm: false
                                        form: {
                                            label: '任务数',
                                            field: 'taskQty',
                                            type: 'number',
                                            hide: true,
                                            required: true,
                                        },
                                    },
                                    {
                                        table: {
                                            title: '目标任务计划',
                                            dataIndex: '1',
                                            key: '1',
                                            align: 'center',
                                            // width: 100,
                                            render: () => {
                                                return <div style={{ color: '#1890ff', cursor: 'pointer' }}>明细</div>
                                            },
                                            onClick: (obj) => {
                                                this.primaryKey = obj.rowData.zjLzehManageTaskPlanId
                                                this.setState({
                                                    importOrAdd: false
                                                })
                                            },
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            dataIndex: 'remarks',
                                            key: 'remarks',
                                            align: 'center',
                                            // width: 300,
                                        },
                                        form: {
                                            label: '备注',
                                            field: 'remarks',
                                            type: 'string',
                                            // required: true,
                                        },
                                    },
                                ]
                            }
                            method={
                                {
                                    updateZjLzehManageTaskPlanFunc: (obj) => {
                                        const zjLzehManageTaskPlanId = obj.btnCallbackFn.getSelectedRows()[0].zjLzehManageTaskPlanId
                                        return {
                                            apiName: 'updateZjLzehManageTaskPlan',
                                            otherParams: {
                                                zjLzehManageTaskPlanId
                                            }
                                        }
                                    }
                                }
                            }
                            actionBtns={
                                [{
                                    field: 'add',
                                    name: 'add',
                                    type: 'primary',
                                    icon: 'plus',
                                    label: '新增',
                                    formBtns: [{
                                        name: 'cancel',
                                        type: 'dashed',
                                        label: '取消',
                                    },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '保存',
                                        fetchConfig: {
                                            apiName: 'addZjLzehManageTaskPlan'
                                        }
                                    }
                                    ]
                                },
                                {
                                    field: 'edit',
                                    name: 'edit',
                                    type: 'primary',
                                    label: '修改',
                                    formBtns: [{
                                        name: 'cancel',
                                        type: 'dashed',
                                        label: '取消',
                                    },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '保存',
                                        fetchConfig: 'bind:updateZjLzehManageTaskPlanFunc'
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
                                        apiName: 'batchDeleteUpdateZjLzehManageTaskPlan'
                                    },
                                }
                                ]
                            }
                        /> </div> : <ImportCom parent={this}></ImportCom>}
            </div>
        );
    }
}

export default index;