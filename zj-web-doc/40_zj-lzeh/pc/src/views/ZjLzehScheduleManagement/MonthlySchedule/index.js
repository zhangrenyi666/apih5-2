import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col } from "antd";
import SelectFilesDownLoad from '../common/SelectFilesDownLoad'
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            queryMonth: undefined
        }
    }
    render() {
        const { queryMonth } = this.state
        return (
            <div>
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
                                                            queryMonth: val.month
                                                        })
                                                        // this.table.refresh()
                                                    })
                                                    // if (queryMonth === val.month && !this.table.getTableData().length) {
                                                    //     Msg.warning('当前月份暂无数据')
                                                    // }

                                                    if (queryMonth === val.month && !this.table.state.data.length) {
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
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    fetchConfig={
                        queryMonth ?
                            {
                                apiName: 'getZjLzehMonthPlanProgressList',
                                otherParams: {
                                    month: queryMonth
                                },
                                success:(res)=>{
                                    // new common
                                    // const {data} = res.response
                                    const {data} = res
                                    if(!data.length){
                                        Msg.warning('当前月份暂无数据')
                                    }
                                }
                            } : {
                                apiName: 'getZjLzehMonthPlanProgressList',
                            }
                    }
                    antd={
                        {
                            rowKey: 'zjLzehMonthPlanProgressId',
                            size: 'small'
                        }
                    }
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
                                        apiName: 'addZjLzehMonthPlanProgress'
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
                                this.table.clearSelectedRows();
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
                                    fetchConfig: {
                                        apiName: 'updateZjLzehMonthPlanProgress'
                                    }
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
                                apiName: 'batchDeleteUpdateZjLzehMonthPlanProgress',
                            }
                        }
                    ]}
                    isShowRowSelect={true}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehMonthPlanProgressId',
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
                                title: '月份',
                                dataIndex: 'showMonth',
                                key: 'showMonth',
                                // format: 'YYYY-MM',
                                onClick: 'detail',
                            },
                            form: {
                                field: 'month',
                                editDisabled: true, //修改禁用
                                type: 'month',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '计划月产值（万元）',
                                dataIndex: 'planMonthOutValue',
                                key: 'planMonthOutValue',
                                width: 150,
                            },
                            form: {
                                field: 'planMonthOutValue',
                                type: 'number',
                                precision: 2,
                                required: true,
                                max: 99999999.99, //最大值
                                onChange: (val, obj) => {
                                    if (obj.form.getFieldValue('monthOutValue')) {
                                       obj.fns.setValues({
                                            completeRate: ((obj.form.getFieldValue('monthOutValue') / val) * 100).toFixed(2)
                                        })
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '实际月产值（万元）',
                                dataIndex: 'monthOutValue',
                                key: 'monthOutValue',
                                width: 150,
                            },
                            form: {
                                field: 'monthOutValue',
                                type: 'number',
                                addShow: false,
                                addDisabled: true,//新增禁用
                                editDisabled: true, //修改禁用
                            }
                        },
                        {
                            table: {
                                title: '任务完成率（%）',
                                dataIndex: 'completeRate',
                                key: 'completeRate',
                                width: 150,
                            },
                            form: {
                                field: 'completeRate',
                                type: 'number',
                                addShow: false,
                                addDisabled: true,//新增禁用
                                editDisabled: true, //修改禁用
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks',
                                width: 150,
                            },
                            form: {
                                field: 'remarks',
                                type: 'string',
                            }
                        },
                        {
                            table: {
                                title: '月计划进度附件',
                                dataIndex: 'fileList',
                                key: 'fileList',
                                width: 180,
                                render: (val) => {
                                    if (val.length) {
                                        return <SelectFilesDownLoad dataList={val} />
                                    } else {
                                        return '无附件'
                                    }
                                }
                            },
                            form: {
                                label: "月计划进度附件",
                                field: 'fileList',
                                type: 'files',
                            }
                        }
                    ]}
                ></QnnTable>
            </div>
        )
    }
}

export default index