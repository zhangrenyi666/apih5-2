import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { Button, message as Msg, Row, Col, Select } from "antd";
import SelectFilesDownLoad from '../common/SelectFilesDownLoad'

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            queryYear: undefined
        }
    }
    render() {
        const { queryYear } = this.state
        return (
            <div>
                <QnnForm
                    wrappedComponentRef={(me) => {
                        this.formOne = me;
                    }}
                    formConfig={
                        [
                            {
                                type: 'year',
                                label: '年份',
                                field: 'year', //唯一的字段名 ***必传
                                placeholder: '请选择',
                                required: false,
                                format: "YYYY",
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
                                                            queryYear: val.year
                                                        })
                                                        // this.table.refresh()
                                                    })
                                                    // if (queryYear === val.year && !this.table.getTableData().length) {
                                                    //     Msg.warning('当前年份暂无数据')
                                                    // }

                                                    if (queryYear === val.year && !this.table.state.data.length) {
                                                        Msg.warning('当前年份暂无数据')
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
                        queryYear ?
                            {
                                apiName: 'getZjLzehYearPlanProgressList',
                                otherParams: {
                                    year: queryYear
                                },
                                success: (res) => {
                                    const { data } = res
                                    if (!data.length) {
                                        Msg.warning('当前月份暂无数据')
                                    }
                                }
                            } : { apiName: 'getZjLzehYearPlanProgressList', }
                    }
                    antd={
                        {
                            rowKey: 'zjLzehYearPlanProgressId',
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
                                        apiName: 'addZjLzehYearPlanProgress'
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
                                        apiName: 'updateZjLzehYearPlanProgress'
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
                                apiName: 'batchDeleteUpdateZjLzehYearPlanProgress',
                            }
                        }
                    ]}

                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehYearPlanProgressId',
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
                                title: '年度',
                                dataIndex: 'showYear',
                                key: 'showYear',
                                // format: 'YYYY',
                                onClick: 'detail',
                            },
                            form: {
                                field: 'year',
                                type: 'year',
                                editDisabled: true, //修改禁用
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '计划年产值（万元）',
                                dataIndex: 'planYearOutValue',
                                key: 'planYearOutValue',
                                width: 150,
                            },
                            form: {
                                field: 'planYearOutValue',
                                type: 'number',
                                precision: 2,
                                required: true,
                                max: 99999999.99, //最大值
                                onChange: (val, obj) => {
                                    if (obj.form.getFieldValue('yearOutValue')) {
                                        obj.fns.setValues({
                                            completeRate: ((obj.form.getFieldValue('yearOutValue') / val) * 100).toFixed(2)
                                        })
                                    }
                                }
                            }
                        },
                        {
                            table: {
                                title: '实际年产值（万元）',
                                dataIndex: 'yearOutValue',
                                key: 'yearOutValue',
                                width: 150,
                            },
                            form: {
                                field: 'yearOutValue',
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
                                title: '年计划进度附件',
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
                                label: "年计划进度附件",
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