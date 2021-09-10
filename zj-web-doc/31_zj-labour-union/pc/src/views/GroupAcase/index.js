//劳务人员列表
import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-form";
import FlowFormByApplication from './form';
import { push } from 'react-router-redux';
import { message as Msg } from 'antd';
const config = {
    fetchConfig: {//表格的ajax配置
        apiName: 'getZjLabourUnionFlowApplicationList',
    },
    antd: { //同步antd table组件配置 ***必传
        rowKey: function (row) {// ***必传
            return row.applicationId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {// 同步antd的分页组件配置   
        position: 'bottom'
    },
};

class index extends Component {
    render() {
        return (
            <div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...config}
                    wrappedComponentRef={(me) => {
                        this.tables = me;
                    }}
                    actionBtns = {[
                        {
                            name: 'add',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '立案申请',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '提交',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZjLabourUnionFlowApplication',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'Component',//内置add del
                            icon: 'plus',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '立案发起',
                            Component: (obj) => {
                                this.tables.clearSelectedRows();
                                let delAttr = ["id"];
                                //判断是否选中数据
                                let selectedRows = obj.selectedRows;
                                if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('请选择一条数据！');
                                    return <div />
                                }
                                if (obj.selectedRows[0].apih5FlowStatus != "") {
                                    obj.btnCallbackFn.closeDrawer();
                                    obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                                    return <div />
                                }
                                obj.btnCallbackFn.fetch('getZjLabourUnionProposalList', { applicationId:obj.selectedRows[0].applicationId }, ({ data, success, message }) => {
                                    if (success) {
                                        if (!data.length) {
                                            obj.btnCallbackFn.closeDrawer();
                                            obj.btnCallbackFn.msg.error('立案明细不可为空!');
                                            return <div />;
                                        }
                                    } else {
                                        Msg.error(message);
                                    }
                                });
                                return <FlowFormByApplication
                                    wrappedComponentRef={(me) => {
                                        if (me) {
                                            let flowData = {};
                                            for (const key in selectedRows[0]) {
                                                if (selectedRows[0].hasOwnProperty(key)) {
                                                    if (!delAttr.includes(key)) {
                                                        const element = selectedRows[0][key];
                                                        flowData[`apiBody.${key.replace('apiBody.')}`] = element;
                                                    }
                                                }
                                            }
                                            setTimeout(function () {
                                                let vals = QnnForm.sFormatData({ ...flowData }, me.props.formConfig, 'set');
                                                me.props.form.setFieldsValue({
                                                    ...vals
                                                })
                                            }, 1)
                                        }
                                    }}
                                    {...obj} tabs={[]} />
                            }
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            isCanSubmit: ({params},callback) => {
                                for(let i = 0; i < params.length; i++){
                                    if(params[i].apih5FlowStatus && params[i].apih5FlowStatus !== '4' && params[i].apih5FlowStatus !== '0' && params[i].apih5FlowStatus !== '3'){
                                        Msg.error('审核中的数据不允许删除！');
                                        callback(false);
                                        break;
                                    }else if(i === (params.length - 1)){
                                        callback(true);
                                    }
                                }
                            },
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjLabourUnionFlowApplication',
                            },
                        }
                    ]}
                    formConfig = {[
                        {
                            isInTable: false,
                            form: {
                                field: 'applicationId',
                                type: 'string',
                                placeholder: '请输入',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '立案审批申请名称', //表头标题
                                dataIndex: 'applicationName', //表格里面的字段
                                key: 'applicationName',//表格的唯一key
                                onClick:'detail',
                                tooltip: 15
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '申请内容', //表头标题
                                dataIndex: 'applicationContent', //表格里面的字段
                                key: 'applicationContent',//表格的唯一key
                                tooltip: 15
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '创建者', //表头标题
                                dataIndex: 'modifyUserName', //表格里面的字段
                                key: 'modifyUserName',//表格的唯一key
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '发起时间', //表头标题
                                dataIndex: 'modifyTime', //表格里面的字段
                                key: 'modifyTime',//表格的唯一key
                                format:"YYYY-MM-DD",
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '流程状态',
                                dataIndex: 'apih5FlowStatus',
                                key: 'apih5FlowStatus',
                                render: (data) => {
                                    if (data === '4') {
                                        return '未发起';
                                    } else if (data === '0') {
                                        return '已发起';
                                    } else if (data === '1') {
                                        return '审核中';
                                    } else if (data === '2') {
                                        return '审核结束';
                                    } else if (data === '3') {
                                        return '退回';
                                    } else if (data === '') {
                                        return '未发起';
                                    } else {
                                        return '--';
                                    }
                                }
                            },
                            isInForm:false
                        },
                        {
                            isInForm: false,
                            table: {
                                title: '操作',
                                dataIndex: 'action', //表格里面的字段
                                key: 'action',//表格的唯一key  
                                align: "center",
                                showType: "tile",
                                btns: [
                                    {
                                        name: 'CaseListOfTheGroup',
                                        render: function (rowData) {
                                            return '<a>立案明细</a>';
                                        },
                                        onClick:(obj) => {
                                            const { applicationId, apih5FlowStatus } = obj.rowData;
                                            const { dispatch, myPublic: { appInfo: { mainModule } } } = obj.props;
                                            dispatch(push(`${mainModule}CaseListOfTheGroup/${applicationId}/0/${apih5FlowStatus ? apih5FlowStatus : 0}`));
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;