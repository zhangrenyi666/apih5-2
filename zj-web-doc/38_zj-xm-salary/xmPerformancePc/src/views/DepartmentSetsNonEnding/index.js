import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
const config = {
    antd: {
        rowKey: function (row) {
            return row.deptId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
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
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    fetchConfig={{
                        apiName: 'getZjXmJxQuarterlyAssessmentDeptList',
                        otherParams: {
                            isClosed:'0'
                        }
                    }}
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
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'addZjXmJxQuarterlyAssessmentDeptToWeight',
                                    }
                                }
                            ]
                        },
                        {
                            name: 'edit',//内置add del
                            icon: 'edit',//icon
                            type: 'primary',//类型  默认 primary  [primary dashed danger]
                            label: '修改',
                            formBtns: [
                                {
                                    name: 'cancel', //关闭右边抽屉
                                    type: 'dashed',//类型  默认 primary
                                    label: '取消',
                                },
                                {
                                    name: 'submit',//内置add del
                                    type: 'primary',//类型  默认 primary
                                    label: '保存',//提交数据并且关闭右边抽屉 
                                    fetchConfig: {//ajax配置
                                        apiName: 'updateZjXmJxQuarterlyAssessmentDeptToWeight',
                                    },
                                    onClick: (obj) => {
                                        obj.btnCallbackFn.clearSelectedRows();
                                    }
                                }
                            ]
                        },
                        {
                            name: 'del',//内置add del
                            icon: 'delete',//icon
                            type: 'danger',//类型  默认 primary  [primary dashed danger]
                            label: '删除',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight',
                            },
                        }
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'deptId',
                                type: 'string',
                                hide: true,
                            }
                        },
                        {
                            isInTable:false,
                            form: {
                                field:'isClosed',
                                type: 'string',
                                initialValue:'0',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '部门名称',
                                dataIndex: 'deptName',
                                key: 'deptName',
                                filter: true
                            },
                            form: {
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData: [
                                    {
                                        label: '路桥事业部',
                                        value: '路桥事业部'
                                    },
                                    {
                                        label: '城市房建事业部',
                                        value: '城市房建事业部'
                                    },
                                    {
                                        label: '铁路轨道事业部',
                                        value: '铁路轨道事业部'
                                    },
                                    {
                                        label: '技术质量部',
                                        value: '技术质量部'
                                    },
                                    {
                                        label: '安全监督部',
                                        value: '安全监督部'
                                    },
                                    {
                                        label: '经营考核部',
                                        value: '经营考核部'
                                    },
                                    {
                                        label: '财务部',
                                        value: '财务部'
                                    },
                                    {
                                        label: '物资设备部',
                                        value: '物资设备部'
                                    },
                                    {
                                        label: '人力资源部',
                                        value: '人力资源部'
                                    },
                                    {
                                        label: '法律部',
                                        value: '法律部'
                                    },
                                    {
                                        label: '办公室',
                                        value: '办公室'
                                    },
                                    {
                                        label: '经营考核部供应链管理部',
                                        value: '经营考核部供应链管理部'
                                    },
                                    {
                                        label: '经营考核部收尾中心',
                                        value: '经营考核部收尾中心'
                                    }
                                ],
                                required: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '系统部门',
                                dataIndex: 'departmentName',
                                key: 'departmentName',
                                onClick: 'detail'
                            },
                            form: {
                                field: 'sysDeptArray',
                                type: 'treeSelect',
                                treeSelectOption: {
                                    selectType: "1",
                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                        apiName: 'getSysDepartmentAllTree'
                                    },
                                    maxNumber: 1,
                                    search: true,
                                    searchPlaceholder: '部门',
                                    // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                    searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                    searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                },
                                required: true
                            }
                        },
                        {
                            table: {
                                title: '项目状态',
                                dataIndex: 'projectStatusName',
                                key: 'projectStatusName'
                            },
                            form: {
                                field:'projectStatus',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData:[
                                    {
                                        label: '全部状态',
                                        value: '0'
                                    },
                                    {
                                        label: '在建',
                                        value: '1'
                                    },
                                    {
                                        label: '主体完工',
                                        value: '2'
                                    }
                                ],
                                required: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '项目类型',
                                dataIndex: 'projectTypeName',
                                key: 'projectTypeName'
                            },
                            form: {
                                field:'projectType',
                                type: 'select',
                                optionConfig: {
                                    label: 'label',
                                    value: 'value'
                                },
                                optionData:[
                                    {
                                        label: '全部类型',
                                        value: '0'
                                    },
                                    {
                                        label: '路桥类型',
                                        value: '1'
                                    },
                                    {
                                        label: '房建类型',
                                        value: '2'
                                    },
                                    {
                                        label: '轨道类型',
                                        value: '3'
                                    }
                                ],
                                required: true,
                                placeholder: '请选择'
                            }
                        },
                        {
                            table: {
                                title: '上线分数',
                                dataIndex: 'upperLimitScore',
                                key: 'upperLimitScore'
                            },
                            form: {
                                type: 'number',
                                required: true,
                                placeholder: '请输入'
                            }
                        },
                        {
                            table: {
                                title: '备注',
                                dataIndex: 'remarks',
                                key: 'remarks'
                            },
                            form: {
                                type: 'textarea',
                                placeholder: '请输入'
                            }
                        }
                    ]}
                />
            </div>
        );
    }
}

export default index;