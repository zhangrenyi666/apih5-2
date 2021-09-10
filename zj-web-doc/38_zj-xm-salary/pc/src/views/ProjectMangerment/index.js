import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import TableTow from "./tableTow";
import s from "./style.less";
import { Tabs } from 'antd';
const { TabPane } = Tabs;
const baidu_zuobiao = () => {
    return <div
        style={{
            marginTop: '-13px'
        }}
    >
        <a
            style={{
                paddingLeft: '810px'
            }}
            onClick={() => {
                window.open('http://api.map.baidu.com/lbsapi/getpoint/index.html', '', 'width=800,height=500,top=100,left=100');
            }}>百度地图坐标拾取 </a>
    </div>
}
const config = {
    antd: {
        rowKey: 'departmentId',
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 8 },
            sm: { span: 8 }
        },
        wrapperCol: {
            xs: { span: 16 },
            sm: { span: 16 }
        }
    }
};
class index extends Component {
    state = {
        tabsKey: '1'
    }
    tabsCallback = (activeKey) => {
        this.setState({
            tabsKey: activeKey
        })
    }
    render () {
        const { tabsKey } = this.state;
        const { departmentId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <div className={s.root}>
                <Tabs activeKey={tabsKey} onChange={this.tabsCallback} >
                    <TabPane tab="在建项目" key="1">
                        {tabsKey === '1' ? <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            fetchConfig={{
                                apiName: 'getSysProjectList',
                                otherParams: {
                                    departmentId: departmentId,
                                    proState:'1'
                                }
                            }}
                            actionBtns={[
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
                                            hide: (obj) => {
                                                let index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateSysProjectByRelation',
                                                success: (obj) => {
                                                    if (obj.response.success) {
                                                        this.table.setTabsIndex('1');
                                                        this.table.refresh();
                                                    }
                                                }
                                            },
                                            isCloseDrawer: false,
                                            hide: (obj) => {
                                                let index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateSysProject',
                                    }
                                }
                            ]}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    content: {
                                        formConfig: [
                                            {
                                                field: 'departmentId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'companyId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'companyName',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'projectId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentFlag',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentFlagName',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentParentId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentPath',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentPathName',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                label: '项目名称',
                                                field: 'projectName',
                                                type: 'string',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目简称',
                                                field: 'departmentName',
                                                type: 'string',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '工程类型',
                                                field: 'projType',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'gongChengLeiXing'
                                                    }
                                                },
                                                editDisabled: true,
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '机构类型',
                                                field: 'companyType',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'jiGouLeiXing'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目级别',
                                                field: 'proLevel',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xiangMuJiBie'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '薪酬类型',
                                                field: 'jobType',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xiangMuXinChouLeiXing'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目限定总人数',
                                                field: 'departmentNum',
                                                type: 'number',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '实际总人数',
                                                field: 'projectUserNum',
                                                type: 'number',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '所属地区',
                                                field: 'projectLocation',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                field: 'mapXY', //表格里面的字段
                                                label: '经纬度', //表头标题
                                                type: 'string',
                                                placeholder: '请输入经纬度',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '百度地图坐标拾取', //表头标题
                                                field: 'baidu_zuobiao', //表格里面的字段
                                                type: 'Component',
                                                Component: "baidu_zuobiao"
                                            },
                                            {
                                                label: '折算总合同金额(万元)',
                                                field: 'contractAmount',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '折算平均年产值(万元)',
                                                field: 'outputValue',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '工程造价(万元)',
                                                field: 'proCost',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '年度计量金额(万元)',
                                                field: 'meteringAmount',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '合同工期(月)',
                                                field: 'contractPeriod',
                                                type: 'number',
                                                required: true,
                                                formItemLayout: {
                                                    labelCol: {
                                                        xs: { span: 4 },
                                                        sm: { span: 4 }
                                                    },
                                                    wrapperCol: {
                                                        xs: { span: 20 },
                                                        sm: { span: 20 }
                                                    }
                                                }
                                            },
                                            {
                                                label: '实际开工日期',
                                                field: 'actualStartDate',
                                                type: 'date',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '实际竣工日期',
                                                field: 'actualEndDate',
                                                type: 'date',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目状态',
                                                field: 'proState',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xiangMuZhuangTai'
                                                    }
                                                },
                                                required: true,
                                                formItemLayout: {
                                                    labelCol: {
                                                        xs: { span: 4 },
                                                        sm: { span: 4 }
                                                    },
                                                    wrapperCol: {
                                                        xs: { span: 20 },
                                                        sm: { span: 20 }
                                                    }
                                                }
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "table1",
                                    name: "qnnTable1",
                                    title: "人员配置",
                                    content: props => {
                                        return <TableTow {...props} />;
                                    }
                                }
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'departmentId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '项目名称',
                                        dataIndex: 'departmentName',
                                        key: 'departmentName',
                                        filter: true,
                                        fixed: 'left',
                                        width: 150,
                                        onClick: 'detail'
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '工程类型',
                                        dataIndex: 'projType',
                                        key: 'projType',
                                        filter: true,
                                        fixed: 'left',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'gongChengLeiXing'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '公司类型',
                                        dataIndex: 'companyType',
                                        key: 'companyType',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'departmentType'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '项目级别',
                                        dataIndex: 'proLevel',
                                        key: 'proLevel',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xiangMuJiBie'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '项目状态',
                                        dataIndex: 'proStateName',
                                        key: 'proStateName',
                                        width: 100
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xiangMuZhuangTai'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '项目限定总人数',
                                        dataIndex: 'departmentNum',
                                        key: 'departmentNum',
                                        width: 120
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '实际总人数',
                                        dataIndex: 'projectUserNum',
                                        key: 'projectUserNum',
                                        width: 100
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                // {
                                //     table: {
                                //         title: '营业额(万元)',
                                //         dataIndex: '6',
                                //         key: '6',
                                //         width: 120
                                //     },
                                //     form: {
                                //         type: 'number',
                                //         placeholder: '请输入'
                                //     }
                                // },
                                {
                                    table: {
                                        title: '折算总合同金额(万元)',
                                        dataIndex: 'contractAmount',
                                        key: 'contractAmount',
                                        width: 160,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '折算平均年产值(万元)',
                                        dataIndex: 'outputValue',
                                        key: 'outputValue',
                                        width: 160,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '工程造价(万元)',
                                        dataIndex: 'proCost',
                                        key: 'proCost',
                                        width: 120,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '年度计量金额(万元)',
                                        dataIndex: 'meteringAmount',
                                        key: 'meteringAmount',
                                        width: 150,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '合同工期(月)',
                                        dataIndex: 'contractPeriod',
                                        key: 'contractPeriod',
                                        width: 120,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '实际开工日期',
                                        dataIndex: 'actualStartDate',
                                        key: 'actualStartDate',
                                        width: 120,
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        placeholder: '请选择'
                                    }
                                },
                                {
                                    table: {
                                        title: '实际竣工日期',
                                        dataIndex: 'actualEndDate',
                                        key: 'actualEndDate',
                                        width: 120,
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        placeholder: '请选择'
                                    }
                                },
                                {
                                    table: {
                                        title: '所属地区',
                                        dataIndex: 'projectLocation',
                                        key: 'projectLocation',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xingzhengquhuadaima'
                                            }
                                        },
                                    }
                                }
                            ]}
                            componentsKey={{
                                baidu_zuobiao: baidu_zuobiao
                            }}
                        /> : null}
                    </TabPane>
                    <TabPane tab="已完工" key="2">
                        {tabsKey === '2' ? <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            {...config}
                            fetchConfig={{
                                apiName: 'getSysProjectList',
                                otherParams: {
                                    departmentId: departmentId,
                                    proState:'2'
                                }
                            }}
                            actionBtns={[
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
                                            hide: (obj) => {
                                                let index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        },
                                        {
                                            name: 'submit',//内置add del
                                            type: 'primary',//类型  默认 primary
                                            label: '保存',//提交数据并且关闭右边抽屉 
                                            fetchConfig: {//ajax配置
                                                apiName: 'updateSysProjectByRelation',
                                                success: (obj) => {
                                                    if (obj.response.success) {
                                                        this.table.setTabsIndex('1');
                                                        this.table.refresh();
                                                    }
                                                }
                                            },
                                            isCloseDrawer: false,
                                            hide: (obj) => {
                                                let index = obj.btnCallbackFn.getActiveKey();
                                                if (index === "1") {
                                                    return true;
                                                } else {
                                                    return false;
                                                }
                                            },
                                        }
                                    ]
                                },
                                {
                                    name: 'del',//内置add del
                                    icon: 'delete',//icon
                                    type: 'danger',//类型  默认 primary  [primary dashed danger]
                                    label: '删除',
                                    fetchConfig: {//ajax配置
                                        apiName: 'batchDeleteUpdateSysProject',
                                    }
                                }
                            ]}
                            tabs={[
                                {
                                    field: "form1",
                                    name: "qnnForm",
                                    title: "基础信息",
                                    content: {
                                        formConfig: [
                                            {
                                                field: 'departmentId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'companyId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'companyName',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'projectId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentFlag',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentFlagName',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentParentId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentPath',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                field: 'departmentPathName',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                label: '项目名称',
                                                field: 'projectName',
                                                type: 'string',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目简称',
                                                field: 'departmentName',
                                                type: 'string',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '工程类型',
                                                field: 'projType',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'gongChengLeiXing'
                                                    }
                                                },
                                                editDisabled: true,
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目类型',
                                                field: 'contractorType',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'jiGouLeiXing'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目级别',
                                                field: 'proLevel',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xiangMuJiBie'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '薪酬类型',
                                                field: 'jobType',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xiangMuXinChouLeiXing'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目限定总人数',
                                                field: 'departmentNum',
                                                type: 'number',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '实际总人数',
                                                field: 'projectUserNum',
                                                type: 'number',
                                                disabled: true,
                                                span: 12
                                            },
                                            {
                                                label: '所属地区',
                                                field: 'projectLocation',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xingzhengquhuadaima'
                                                    }
                                                },
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                field: 'mapXY', //表格里面的字段
                                                label: '经纬度', //表头标题
                                                type: 'string',
                                                placeholder: '请输入经纬度',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '百度地图坐标拾取', //表头标题
                                                field: 'baidu_zuobiao', //表格里面的字段
                                                type: 'Component',
                                                Component: "baidu_zuobiao"
                                            },
                                            {
                                                label: '折算总合同金额(万元)',
                                                field: 'contractAmount',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '折算平均年产值(万元)',
                                                field: 'outputValue',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '工程造价(万元)',
                                                field: 'proCost',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '年度计量金额(万元)',
                                                field: 'meteringAmount',
                                                type: 'number',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '合同工期(月)',
                                                field: 'contractPeriod',
                                                type: 'number',
                                                required: true,
                                                formItemLayout: {
                                                    labelCol: {
                                                        xs: { span: 4 },
                                                        sm: { span: 4 }
                                                    },
                                                    wrapperCol: {
                                                        xs: { span: 20 },
                                                        sm: { span: 20 }
                                                    }
                                                }
                                            },
                                            {
                                                label: '实际开工日期',
                                                field: 'actualStartDate',
                                                type: 'date',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '实际竣工日期',
                                                field: 'actualEndDate',
                                                type: 'date',
                                                required: true,
                                                span: 12
                                            },
                                            {
                                                label: '项目状态',
                                                field: 'proState',
                                                type: 'select',
                                                optionConfig: {
                                                    label: 'itemName', //默认 label
                                                    value: 'itemId'
                                                },
                                                fetchConfig: {
                                                    apiName: 'getBaseCodeSelect',
                                                    otherParams: {
                                                        itemId: 'xiangMuZhuangTai'
                                                    }
                                                },
                                                required: true,
                                                formItemLayout: {
                                                    labelCol: {
                                                        xs: { span: 4 },
                                                        sm: { span: 4 }
                                                    },
                                                    wrapperCol: {
                                                        xs: { span: 20 },
                                                        sm: { span: 20 }
                                                    }
                                                }
                                            },
                                        ]
                                    }
                                },
                                {
                                    field: "table1",
                                    name: "qnnTable1",
                                    title: "人员配置",
                                    content: props => {
                                        return <TableTow {...props} />;
                                    }
                                }
                            ]}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'departmentId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '项目名称',
                                        dataIndex: 'departmentName',
                                        key: 'departmentName',
                                        filter: true,
                                        fixed: 'left',
                                        width: 150,
                                        onClick: 'detail'
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '工程类型',
                                        dataIndex: 'projType',
                                        key: 'projType',
                                        filter: true,
                                        fixed: 'left',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'gongChengLeiXing'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '公司类型',
                                        dataIndex: 'companyType',
                                        key: 'companyType',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'departmentType'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '项目级别',
                                        dataIndex: 'proLevel',
                                        key: 'proLevel',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xiangMuJiBie'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '项目状态',
                                        dataIndex: 'proStateName',
                                        key: 'proStateName',
                                        width: 100
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xiangMuZhuangTai'
                                            }
                                        },
                                    }
                                },
                                {
                                    table: {
                                        title: '项目限定总人数',
                                        dataIndex: 'departmentNum',
                                        key: 'departmentNum',
                                        width: 120
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '实际总人数',
                                        dataIndex: 'projectUserNum',
                                        key: 'projectUserNum',
                                        width: 100
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                // {
                                //     table: {
                                //         title: '营业额(万元)',
                                //         dataIndex: '6',
                                //         key: '6',
                                //         width: 120
                                //     },
                                //     form: {
                                //         type: 'number',
                                //         placeholder: '请输入'
                                //     }
                                // },
                                {
                                    table: {
                                        title: '折算总合同金额(万元)',
                                        dataIndex: 'contractAmount',
                                        key: 'contractAmount',
                                        width: 160,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '折算平均年产值(万元)',
                                        dataIndex: 'outputValue',
                                        key: 'outputValue',
                                        width: 160,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '工程造价(万元)',
                                        dataIndex: 'proCost',
                                        key: 'proCost',
                                        width: 120,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '年度计量金额(万元)',
                                        dataIndex: 'meteringAmount',
                                        key: 'meteringAmount',
                                        width: 150,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '合同工期(月)',
                                        dataIndex: 'contractPeriod',
                                        key: 'contractPeriod',
                                        width: 120,
                                    },
                                    form: {
                                        type: 'number',
                                        placeholder: '请输入'
                                    }
                                },
                                {
                                    table: {
                                        title: '实际开工日期',
                                        dataIndex: 'actualStartDate',
                                        key: 'actualStartDate',
                                        width: 120,
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        placeholder: '请选择'
                                    }
                                },
                                {
                                    table: {
                                        title: '实际竣工日期',
                                        dataIndex: 'actualEndDate',
                                        key: 'actualEndDate',
                                        width: 120,
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        type: 'date',
                                        placeholder: '请选择'
                                    }
                                },
                                {
                                    table: {
                                        title: '所属地区',
                                        dataIndex: 'projectLocation',
                                        key: 'projectLocation',
                                        width: 100,
                                        type: 'select'
                                    },
                                    form: {
                                        type: 'select',
                                        optionConfig: {
                                            label: 'itemName', //默认 label
                                            value: 'itemId'
                                        },
                                        fetchConfig: {
                                            apiName: 'getBaseCodeSelect',
                                            otherParams: {
                                                itemId: 'xingzhengquhuadaima'
                                            }
                                        },
                                    }
                                }
                            ]}
                            componentsKey={{
                                baidu_zuobiao: baidu_zuobiao
                            }}
                        /> : null}
                    </TabPane>
                </Tabs>
            </div>
        );
    }
}

export default index;