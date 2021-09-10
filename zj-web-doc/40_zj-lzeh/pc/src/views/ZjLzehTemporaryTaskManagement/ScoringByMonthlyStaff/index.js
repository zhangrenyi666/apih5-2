import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { message as Msg, Tabs, Radio, Tree, Button, Modal, Drawer } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import Comment from '../../comment'
import ReactEcharts from "echarts-for-react";
import Maximize from "../common/Maximize"
import SelectFilesDownLoad from '../common/SelectFilesDownLoad'

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            visibleChildren: false,
            chartsShowStatus: false,
            monthScoreList: [],
            mounthList: [],
            isModalVisible: false,
            taskManageId: '',
            allqty: [],
            cqty: [],
            avgRate: [],
            cRate: [],
            defaultWidth: '70%',
            censusMonth: null
        }
    }
    onClose = () => {
        this.setState({
            visible: false,
        });
    }

    onCloseChildren = () => {
        this.setState({
            visibleChildren: false
        });
    }
    currentSelectScoreMonth = ''
    personName = ''
    zjLzehTaskCensusId = ''
    showModal = () => {
        this.setState({
            isModalVisible: true
        })
    };
    handleOk = () => {
        this.setState({
            isModalVisible: false
        })
    };

    handleCancel = () => {
        this.setState({
            isModalVisible: false
        })
    };

    CloseIcon = (<span title="关闭" onClick={() => { }}>
        <CloseOutlined style={{ fontSize: '20px', margin: '-2px' }} />
    </span>)

    changeMaxWidthFunc = (status, minWidth) => {
        if (status === 'max') {
            this.setState({
                defaultWidth: 100 + '%'
            })
        } else {
            this.setState({
                defaultWidth: minWidth
            })
        }

    }
    // echats的依赖数据过滤
    getOptionTow = () => {
        const { dataTow, allqty, cqty, avgRate, cRate } = this.state;
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    crossStyle: {
                        color: '#999'
                    }
                }
            },
            toolbox: {
                feature: {
                    dataView: {
                        show: true, readOnly: false, optionToContent: function (opt) {
                            var axisData = opt.xAxis[0].data;
                            var series = opt.series;
                            var table = '<table class="layui-table" style="width:100%;text-align:center"><tbody><tr>'
                                + '<td>人员姓名</td>'
                                + '<td>' + series[0].name + '</td>'
                                + '<td>' + series[1].name + '</td>'
                                + '<td>' + series[2].name + '</td>'
                                + '<td>' + series[3].name + '</td>'
                                + '</tr>';
                            for (var i = 0, l = axisData.length; i < l; i++) {
                                table += '<tr>'
                                    + '<td>' + axisData[i] + '</td>'
                                    + '<td>' + series[0].data[i] + '</td>'
                                    + '<td>' + series[1].data[i] + '</td>'
                                    + '<td>' + series[2].data[i] + '</td>'
                                    + '<td>' + series[3].data[i] + '</td>'
                                    + '</tr>';
                            }
                            table += '</tbody></table>';
                            return table;
                        }
                    },
                    magicType: { show: true, type: ['line', 'bar'] },
                    restore: { show: true },
                    saveAsImage: { show: true }
                }
            },
            legend: {
                data: ['任务数', '完成数', '完成比例', '平均比例']
            },
            xAxis: [
                {
                    type: 'category',
                    data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
                    axisPointer: {
                        type: 'shadow'
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '任务数',
                    min: 0,
                    max: 100,
                    interval: 50,
                    axisLabel: {
                        formatter: '{value} '
                    }
                },
                {
                    type: 'value',
                    name: '比例',
                    min: 0,
                    max: 100,
                    interval: 10,
                    axisLabel: {
                        formatter: '{value} %'
                    }
                }
            ],
            series: [
                {
                    name: '任务数',
                    type: 'bar',
                    data: allqty
                },
                {
                    name: '完成数',
                    type: 'bar',
                    data: cqty
                },
                {
                    name: '完成比例',
                    type: 'line',
                    yAxisIndex: 1,
                    data: cRate
                },
                {
                    name: '平均比例',
                    type: 'line',
                    yAxisIndex: 1,
                    data: avgRate
                }
            ]
        };
        return option;
    }
    render() {
        const { visible, visibleChildren, isModalVisible, taskManageId, defaultWidth, censusMonth } = this.state
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
                                type: 'month',
                                label: '评分月份',
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
                                                            censusMonth: val.month
                                                        })
                                                        // this.table.refresh()
                                                    })
                                                    // if (censusMonth === val.month && !this.table.getTableData().length) {
                                                    //     Msg.warning('当前月份暂无数据')
                                                    // }

                                                    if (censusMonth === val.month && !this.table.state.data.length) {
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
                    // isShowRowSelect={false}
                    antd={
                        {
                            rowKey: 'zjLzehTaskCensusId',
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
                                        apiName: 'addZjLzehTaskCensus'
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
                                        apiName: 'updateZjLzehTaskCensus'
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
                                apiName: 'batchDeleteUpdateZjLzehTaskCensus',
                            }
                        }
                    ]}
                    isShowRowSelect={true}
                    fetchConfig={
                        censusMonth ? {
                            apiName: 'getZjLzehTaskCensusList',
                            otherParams: {
                                censusMonth
                            },
                            success: (res) => {
                                const { data } = res
                                if (!data.length) {
                                    Msg.warning('当前月份暂无数据')
                                }
                            }
                        } : { apiName: 'getZjLzehTaskCensusList' }
                    }

                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehTaskCensusId',
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
                                title: '评分月份',
                                dataIndex: 'censusMonth',
                                key: 'censusMonth',
                                format: 'YYYY-MM',
                                // onClick: 'detail',
                                width: 150,
                                // addDisabled: true,//新增禁用
                            },
                            form: {
                                field: 'censusMonth',
                                type: 'month',
                                editDisabled: true, //修改禁用
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '人员数',
                                dataIndex: 'personQty',
                                key: 'personQty',
                                width: 150,
                            },
                            // isInForm: (val)=>{
                            //     console.log(val)
                            //     return false
                            // }
                            form: {
                                field: 'personQty',
                                type: 'number',
                                addShow: false,
                                editDisabled: true, //修改禁用
                                required: true,
                            }
                        },
                        {
                            table: {
                                title: '月度评分',
                                dataIndex: 'bzs',
                                key: 'bzs',
                                width: 150,
                                render: () => {
                                    return '明细'
                                },
                                onClick: (obj) => {
                                    this.currentSelectScoreMonth = obj.rowData.censusMonth
                                    this.zjLzehTaskCensusId = obj.rowData.zjLzehTaskCensusId
                                    this.setState({
                                        visible: true,
                                        chartsShowStatus: false
                                    })
                                }
                            },
                            isInForm: false,
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
                    ]}
                ></QnnTable>
                <Drawer
                    // title="任务明细"
                    title={<Maximize props={this} title={'任务明细'} minWidth={'70%'} />}
                    width={defaultWidth}
                    closeIcon={this.CloseIcon}
                    destroyOnClose={true}
                    placement="right"
                    onClose={this.onClose}
                    visible={visible}
                >
                    {visible ? <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}

                        isShowRowSelect={false}
                        antd={
                            {
                                rowKey: 'zjLzehTaskCensusItemId',
                                size: 'small'
                            }
                        }
                        fetchConfig={
                            {
                                apiName: 'getZjLzehTaskCensusItemList',
                                otherParams: {
                                    zjLzehTaskCensusId: this.zjLzehTaskCensusId
                                }
                            }
                        }

                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    field: 'zjLzehTaskCensusItemId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    field: 'zjLzehTaskCensusId',
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
                                    title: '人员姓名',
                                    dataIndex: 'personName',
                                    key: 'personName',
                                    // onClick: 'detail',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '生产目标任务',
                                    dataIndex: 'scmbrw',
                                    key: 'scmbrw',
                                    // onClick: 'detail',
                                    width: 150,
                                    children: [
                                        {
                                            title: '计划数',
                                            dataIndex: 'pallNum',
                                            key: 'pallNum',
                                            width: 130,
                                        },
                                        {
                                            title: '完成数',
                                            dataIndex: 'pcNum',
                                            key: 'pcNum',
                                            width: 130,
                                        },
                                        {
                                            title: '未完成数',
                                            dataIndex: 'pnum',
                                            key: 'pnum',
                                            width: 130,
                                        },
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '经营目标任务',
                                    dataIndex: 'jymbrw',
                                    key: 'jymbrw',
                                    width: 150,
                                    // tdEdit: true
                                    children: [
                                        {
                                            title: '计划数',
                                            dataIndex: 'mallNum',
                                            key: 'mallNum',
                                            width: 130,
                                        },
                                        {
                                            title: '完成数',
                                            dataIndex: 'mcNum',
                                            key: 'mcNum',
                                            width: 130,
                                        },
                                        {
                                            title: '未完成数',
                                            dataIndex: 'mnum',
                                            key: 'mnum',
                                            width: 130,
                                        },
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '临时任务',
                                    dataIndex: 'lsrw',
                                    key: 'lsrw',
                                    width: 150,
                                    // tdEdit: true
                                    children: [
                                        {
                                            title: '计划数',
                                            dataIndex: 'allNum',
                                            key: 'allNum',
                                            width: 130,
                                            render: (obj, data) => {
                                                return (<div style={{ color: '#1890ff', cursor: 'pointer' }} onClick={() => {
                                                    this.personName = data.personName
                                                    this.setState({
                                                        visibleChildren: true
                                                    })
                                                }}>{obj}</div>)
                                            }
                                        },
                                        {
                                            title: '完成数',
                                            dataIndex: 'cNum',
                                            key: 'cNum',
                                            width: 130,
                                            render: (obj, data) => {
                                                this.personName = data.personName
                                                // return (<div style={{ color: '#1890ff', cursor: 'pointer' }} onClick={() => {
                                                //     this.setState({
                                                //         visibleChildren: true
                                                //     })
                                                // }}>{obj}</div>)
                                                return <div>{obj}</div>
                                            }
                                        },
                                        {
                                            title: '未完成数',
                                            dataIndex: 'num',
                                            key: 'num',
                                            width: 130,
                                            render: (obj, data) => {
                                                this.personName = data.personName
                                                // return (<div style={{ color: '#1890ff', cursor: 'pointer' }} onClick={() => {
                                                //     this.setState({
                                                //         visibleChildren: true
                                                //     })
                                                // }}>{obj}</div>)
                                                return <div>{obj}</div>
                                            }
                                        },
                                    ]
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '计划任务总数',
                                    dataIndex: 'allqty',
                                    key: 'allqty',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '完成任务总数',
                                    dataIndex: 'cqty',
                                    key: 'cqty',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '未完成任务总数',
                                    dataIndex: 'qty',
                                    key: 'qty',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '任务完成比例',
                                    dataIndex: 'cRate',
                                    key: 'cRate',
                                    width: 150,
                                    render: (val) => {
                                        return !val ? '0%' : val + '%'
                                    }
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '本月排名',
                                    dataIndex: 'xuhao',
                                    key: 'xuhao',
                                    width: 150,
                                },
                                isInForm: false
                            },
                            {
                                table: {
                                    title: '本年趋势',
                                    dataIndex: 'bnqs',
                                    key: 'bnqs',
                                    width: 150,
                                    render: () => {
                                        return '>>>'
                                    },
                                    onClick: (obj) => {
                                        this.props.myFetch('getZjLzehTaskCensusItemChartList', {
                                            personName: obj.rowData.personName,
                                            cenMonth: this.currentSelectScoreMonth
                                        }).then(({ data, message, success }) => {
                                            const chartsDataList = {
                                                allqty: [],
                                                cqty: [],
                                                avgRate: [],
                                                cRate: []
                                            }
                                            data.map(item => {
                                                const month = new Date(item.cenMonth).getMonth()
                                                chartsDataList.allqty[month] = (item.allqty)
                                                chartsDataList.cqty[month] = (item.cqty)
                                                chartsDataList.avgRate[month] = (item.avgRate)
                                                chartsDataList.cRate[month] = (item.cRate)
                                            })
                                            setTimeout(() => {
                                                const { allqty, cqty, avgRate, cRate } = chartsDataList
                                                this.setState({
                                                    allqty, cqty, avgRate, cRate,
                                                    chartsShowStatus: true
                                                })
                                            })
                                        }, 0)
                                    }
                                },
                                isInForm: false
                                // form: {
                                //     field: 'remarks',
                                //     type: 'string',
                                //     required: true,
                                // }
                            },
                        ]}
                    ></QnnTable> : null}

                    <div style={{ marginTop: 150 }}>
                        {this.state.chartsShowStatus ? <ReactEcharts
                            option={this.getOptionTow()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"} /> : null}
                    </div>
                    <Drawer
                        // title="任务明细"
                        title={<Maximize props={this} title={'任务明细'} minWidth={'70%'} />}
                        width={defaultWidth}
                        closeIcon={this.CloseIcon}
                        destroyOnClose={true}
                        placement="right"
                        closable={true}
                        onClose={this.onCloseChildren}
                        visible={visibleChildren}
                    >
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
                                    rowKey: 'zjLzehTempTaskManageId',
                                    size: 'small'
                                }
                            }

                            fetchConfig={
                                {
                                    apiName: 'selectZjLzehTempTaskManageListByPersonMonth',
                                    otherParams: {
                                        beginDate: this.currentSelectScoreMonth,
                                        implementPerson: this.personName
                                    }
                                }
                            }

                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zjLzehTempTaskManageId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentId',
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'allotPersonId',
                                        type: 'string',
                                        hide: true,
                                        initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                                    }
                                },
                                {
                                    table: {
                                        title: '任务名称',
                                        dataIndex: 'taskName',
                                        key: 'taskName',
                                        width: 150,
                                        // onClick: 'detail'
                                    },
                                    form: {
                                        field: 'taskName',
                                        type: 'string',
                                        required: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '任务描述',
                                        dataIndex: 'taskDescribe',
                                        key: 'taskDescribe',
                                        width: 150,
                                    },
                                    form: {
                                        field: 'taskDescribe',
                                        type: 'string',
                                        required: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '创建人',
                                        dataIndex: 'allotPerson',
                                        key: 'allotPerson',
                                        width: 150,
                                    },
                                    form: {
                                        field: 'allotPerson',
                                        type: 'string',
                                        // required: true,
                                        addDisabled: true,//新增禁用
                                        editDisabled: true, //修改禁用
                                        initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                                    }
                                },
                                {
                                    table: {
                                        title: '开始日期',
                                        dataIndex: 'beginDate',
                                        key: 'beginDate',
                                        width: 150,
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        field: 'beginDate',
                                        type: 'date',
                                        required: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '要求完成日期',
                                        dataIndex: 'requireComplateDate',
                                        key: 'requireComplateDate',
                                        width: 150,
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        field: 'requireComplateDate',
                                        type: 'date',
                                        required: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '分配对象',
                                        dataIndex: 'implementPerson',
                                        key: 'implementPerson',
                                        width: 150,
                                        // type: 'select',
                                    },
                                    form: {
                                        type: 'select',
                                        required: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value',
                                        },
                                        fetchConfig: {
                                            apiName: 'getSysDepartmentUserAllTree'
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '实际完成日期',
                                        dataIndex: 'realCompalteDate',
                                        key: 'realCompalteDate',
                                        width: 150,
                                        format: 'YYYY-MM-DD'
                                    },
                                    form: {
                                        field: 'realCompalteDate',
                                        type: 'date',
                                        // required: true,
                                        addDisabled: true,//新增禁用
                                        editDisabled: true, //修改禁用
                                    }
                                },
                                {
                                    table: {
                                        title: '附件',
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

                                        label: '附件上传',
                                        field: "fileList",
                                        type: 'files',
                                    }
                                },
                                {
                                    table: {
                                        title: '完成情况说明',
                                        dataIndex: 'complateExplain',
                                        key: 'complateExplain',
                                        width: 150,
                                    },
                                    form: {
                                        field: 'complateExplain',
                                        type: 'string',
                                        required: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '状态',
                                        dataIndex: 'status',
                                        key: 'status',
                                        width: 150,
                                        render: (val) => {
                                            switch (val) {
                                                case '0':
                                                    return '未分配'
                                                case '1':
                                                    return '已分配'
                                                case '2':
                                                    return '收回'
                                                case '3':
                                                    return '已提交 '
                                                case '4':
                                                    return '已确认 '
                                            }
                                        }
                                    },
                                    form: {
                                        field: 'status',
                                        type: 'select',
                                        required: true,
                                        optionData: [
                                            {
                                                label: "未分配",
                                                value: '0'
                                            },
                                            {
                                                label: "已分配",
                                                value: '1'
                                            },
                                            {
                                                label: "收回",
                                                value: '2'
                                            },
                                            {
                                                label: "已提交",
                                                value: '3'
                                            },
                                            {
                                                label: "已确认",
                                                value: '4'
                                            },
                                        ],
                                        initialValue: '0',
                                        addDisabled: true,//新增禁用
                                        editDisabled: true, //修改禁用
                                    }
                                },
                                {
                                    table: {
                                        title: '沟通交流',
                                        dataIndex: 'gt',
                                        key: 'gt',
                                        width: 150,
                                        render: () => {
                                            return <span style={{ cursor: 'pointer ', color: '#1890ff' }} >{'>>>'}</span>
                                        },
                                        onClick: (obj) => {
                                            setTimeout(() => {
                                                this.setState({
                                                    taskManageId: obj.rowData.zjLzehTempTaskManageId
                                                })
                                                this.showModal()
                                            }, 0)
                                        }
                                    },
                                    isInForm: false
                                },
                            ]}
                        ></QnnTable>
                        <Modal title="沟通交流" width={'70%'} visible={isModalVisible} onOk={this.handleOk} onCancel={this.handleCancel} footer={null}>
                            {isModalVisible ? <Comment currentLogin={this.props.loginAndLogoutInfo.loginInfo.userInfo} propVal={this.props} taskManageId={taskManageId} /> : null}
                        </Modal>
                    </Drawer>
                </Drawer>
            </div >
        )
    }
}

export default index
