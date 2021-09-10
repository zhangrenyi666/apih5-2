import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import { CloseOutlined } from '@ant-design/icons';
import { message as Msg, Drawer, Button } from 'antd';
import ReactEcharts from "echarts-for-react";
import Maximize from "../common/Maximize"
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            chartsShowStatus: false,
            monthScoreList: [],
            mounthList: [],
            defaultWidth: '70%',
            zjLzehTeamScoreId: '',
            scoreMonth: undefined
        }
    }
    onClose = () => {
        this.setState({
            visible: false,
            chartsShowStatus: false
        });
    }
    currentSelectScoreMonth = ''

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
        const { dataTow } = this.state;
        var option = {
            title: {
                text: '班组趋势图'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['月份分数', '月份平均分数']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
            },
            yAxis: {
                type: 'value',
                min: 0,
                max: 100,
                interval: 10,
            },
            series: [
                {
                    name: '月份分数',
                    type: 'line',
                    data: this.state.mounthList
                },
                {
                    name: '月份平均分数',
                    type: 'line',
                    data: this.state.monthScoreList
                },
            ]
        };
        return option;
    }
    render() {
        const { visible, defaultWidth, chartsShowStatus, zjLzehTeamScoreId, scoreMonth } = this.state
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
                                field: 'scoreMonth', //唯一的字段名 ***必传
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
                                                this.formOne.getValues().then(val => {
                                                    this.table1.clearSelectedRows();
                                                    setTimeout(() => {
                                                        this.setState({
                                                            scoreMonth: val.scoreMonth
                                                        })
                                                        // this.table.refresh()
                                                    })
                                                    // new common
                                                    // if (scoreMonth === val.scoreMonth && !this.table1.getTableData().length) {
                                                    //     Msg.warning('当前月份暂无数据')
                                                    // }

                                                    if (scoreMonth === val.scoreMonth && !this.table1.state.data.length) {
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
                        this.table1 = me;
                    }}
                    antd={
                        {
                            rowKey: 'zjLzehTeamScoreId',
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
                                        apiName: 'addZjLzehTeamScore'
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
                                        apiName: 'updateZjLzehTeamScore'
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
                                apiName: 'batchDeleteUpdateZjLzehTeamScore',
                            }
                        }
                    ]}

                    fetchConfig={
                        scoreMonth ?
                            {
                                apiName: 'getZjLzehTeamScoreList',
                                otherParams: {
                                    scoreMonth
                                },
                                success: (res) => {
                                    const { data } = res
                                    if (!data.length) {
                                        Msg.warning('当前月份暂无数据')
                                    }
                                }
                            } : {
                                apiName: 'getZjLzehTeamScoreList',
                            }
                    }

                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjLzehTeamScoreId',
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
                                dataIndex: 'scoreMonth',
                                key: 'scoreMonth',
                                format: 'YYYY-MM',
                                // onClick: 'detail',
                                width: 150,
                            },
                            form: {
                                field: 'scoreMonth',
                                type: 'month',
                                required: true,
                                editDisabled: true, //修改禁用
                            }
                        },
                        {
                            table: {
                                title: '班组数',
                                dataIndex: 'teamNum',
                                key: 'teamNum',
                                width: 150,
                            },
                            // isInForm: false
                            form: {
                                field: 'teamNum',
                                type: 'number',
                                hide: true
                                // required: true,
                            }
                        },
                        {
                            table: {
                                title: '班组月度评分',
                                dataIndex: 'bzs',
                                key: 'bzs',
                                width: 150,
                                render: () => {
                                    return '明细'
                                },
                                onClick: (obj) => {
                                    this.currentSelectScoreMonth = obj.rowData.scoreMonth
                                    this.setState({
                                        visible: true,
                                        zjLzehTeamScoreId: obj.rowData.zjLzehTeamScoreId
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
                    // title="月度评分"
                    title={<Maximize props={this} title={'月度评分'} minWidth={'70%'} />}
                    placement="right"
                    width={defaultWidth}
                    closeIcon={this.CloseIcon}
                    onClose={this.onClose}
                    visible={visible}
                    destroyOnClose={true}
                >
                    <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table2 = me;
                        }}

                        tableTdEdit={true}
                        unilineEditCancelCb={(obj, oldRowData, props) => {
                            const rowData = obj.rowData
                            rowData.buildSafe ? rowData.buildSafe = +rowData.buildSafe : rowData.buildSafe = 0
                            rowData.buildQuality ? rowData.buildQuality = +rowData.buildQuality : rowData.buildQuality = 0
                            rowData.buildCivilized ? rowData.buildCivilized = +rowData.buildCivilized : rowData.buildCivilized = 0
                            rowData.monthScore = rowData.buildSafe + rowData.buildQuality + rowData.buildCivilized + rowData.buildProgress

                            obj.qnnTableInstance.setEditedRowData({
                                ...rowData,
                            })
                        }}
                        unilineEditSaveCb={(newRowData, oldRowData, props) => {
                            const {
                                zjLzehTeamItemId,
                                zjLzehTeamScoreId,
                                teamId,
                                buildProgress,
                                buildSafe,
                                buildQuality,
                                buildCivilized,
                                monthScore,
                                monthRank
                            } = newRowData
                            this.props.myFetch('updateZjLzehTeamScoreItem', {
                                zjLzehTeamItemId,
                                zjLzehTeamScoreId,
                                teamId,
                                buildProgress,
                                buildSafe,
                                buildQuality,
                                buildCivilized,
                                monthScore,
                                monthRank
                            }).then(({ success, message, data }) => {
                                if (success) {
                                    this.table2.refresh()
                                }
                            })
                        }}

                        antd={
                            {
                                rowKey: 'zjLzehTeamItemId',
                                size: 'small'
                            }
                        }
                        isShowRowSelect={true}
                        fetchConfig={
                            {
                                apiName: 'getZjLzehTeamScoreItemByTeamScoreId',
                                otherParams: {
                                    zjLzehTeamScoreId
                                }
                            }
                        }

                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    field: 'zjLzehTeamItemId',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    field: 'zjLzehTeamScoreId',
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
                                    // onClick: 'detail',
                                    width: 150,
                                },
                                form: {
                                    field: 'companyName',
                                    type: 'string',
                                    required: true,
                                }
                            },
                            {
                                table: {
                                    title: '班组名称',
                                    dataIndex: 'teamName',
                                    // tooltip: 7,
                                    key: 'teamName',
                                    // onClick: 'detail',
                                    width: 150,
                                },
                                form: {
                                    field: 'teamName',
                                    type: 'string',
                                    required: true,
                                }
                            },
                            {
                                table: {
                                    title: '施工进度（20）',
                                    dataIndex: 'buildProgress',
                                    key: 'buildProgress',
                                    width: 150,
                                    tdEdit: true
                                },
                                form: {
                                    field: 'buildProgress',
                                    type: 'number',
                                    // required: true,
                                    max: 20,
                                    onChange: async (val, obj, btnCallbackFn) => {

                                        // let val = e.target.value;
                                        const editRowData = await btnCallbackFn.getEditedRowData()
                                        editRowData.buildSafe ? editRowData.buildSafe = +editRowData.buildSafe : editRowData.buildSafe = 0
                                        editRowData.buildQuality ? editRowData.buildQuality = +editRowData.buildQuality : editRowData.buildQuality = 0
                                        editRowData.buildCivilized ? editRowData.buildCivilized = +editRowData.buildCivilized : editRowData.buildCivilized = 0
                                        editRowData.monthScore = editRowData.buildSafe + editRowData.buildQuality + editRowData.buildCivilized + +val
                                        await btnCallbackFn.setEditedRowData(editRowData);

                                        // const editRowData = btnCallbackFn.qnnTableInstance.getEditedRowData()
                                        // editRowData.buildSafe ? editRowData.buildSafe = +editRowData.buildSafe : editRowData.buildSafe = 0
                                        // editRowData.buildQuality ? editRowData.buildQuality = +editRowData.buildQuality : editRowData.buildQuality = 0
                                        // editRowData.buildCivilized ? editRowData.buildCivilized = +editRowData.buildCivilized : editRowData.buildCivilized = 0
                                        // editRowData.monthScore = editRowData.buildSafe + editRowData.buildQuality + editRowData.buildCivilized + +val
                                        // btnCallbackFn.qnnTableInstance.setEditedRowData(editRowData);

                                    }
                                }
                            },
                            {
                                table: {
                                    title: '施工安全（30）',
                                    dataIndex: 'buildSafe',
                                    key: 'buildSafe',
                                    width: 150,
                                    tdEdit: true
                                },
                                form: {
                                    field: 'buildSafe',
                                    type: 'number',
                                    // required: true,
                                    max: 30,
                                    onChange: async (val, obj, btnCallbackFn) => {
                                        // let val = e.target.value;
                                        const editRowData = await btnCallbackFn.getEditedRowData()
                                        editRowData.buildProgress ? editRowData.buildProgress = +editRowData.buildProgress : editRowData.buildProgress = 0
                                        editRowData.buildQuality ? editRowData.buildQuality = +editRowData.buildQuality : editRowData.buildQuality = 0
                                        editRowData.buildCivilized ? editRowData.buildCivilized = +editRowData.buildCivilized : editRowData.buildCivilized = 0
                                        editRowData.monthScore = editRowData.buildProgress + editRowData.buildQuality + editRowData.buildCivilized + +val
                                        await btnCallbackFn.setEditedRowData(editRowData);

                                        // const editRowData = btnCallbackFn.qnnTableInstance.getEditedRowData()
                                        // editRowData.buildProgress ? editRowData.buildProgress = +editRowData.buildProgress : editRowData.buildProgress = 0
                                        // editRowData.buildQuality ? editRowData.buildQuality = +editRowData.buildQuality : editRowData.buildQuality = 0
                                        // editRowData.buildCivilized ? editRowData.buildCivilized = +editRowData.buildCivilized : editRowData.buildCivilized = 0
                                        // editRowData.monthScore = editRowData.buildProgress + editRowData.buildQuality + editRowData.buildCivilized + +val
                                        // btnCallbackFn.qnnTableInstance.setEditedRowData(editRowData);

                                    }
                                }
                            },
                            {
                                table: {
                                    title: '施工质量（30）',
                                    dataIndex: 'buildQuality',
                                    key: 'buildQuality',
                                    width: 150,
                                    tdEdit: true
                                },
                                form: {
                                    field: 'buildQuality',
                                    type: 'number',
                                    // required: true,

                                    max: 30,
                                    onChange: async (val, obj, btnCallbackFn) => {
                                        // let val = e.target.value;
                                        const editRowData = await btnCallbackFn.getEditedRowData()
                                        editRowData.buildProgress ? editRowData.buildProgress = +editRowData.buildProgress : editRowData.buildProgress = 0
                                        editRowData.buildSafe ? editRowData.buildSafe = +editRowData.buildSafe : editRowData.buildSafe = 0
                                        editRowData.buildCivilized ? editRowData.buildCivilized = +editRowData.buildCivilized : editRowData.buildCivilized = 0
                                        editRowData.monthScore = editRowData.buildProgress + editRowData.buildSafe + editRowData.buildCivilized + +val
                                        await btnCallbackFn.setEditedRowData(editRowData);

                                        // const editRowData = btnCallbackFn.qnnTableInstance.getEditedRowData()
                                        // editRowData.buildProgress ? editRowData.buildProgress = +editRowData.buildProgress : editRowData.buildProgress = 0
                                        // editRowData.buildSafe ? editRowData.buildSafe = +editRowData.buildSafe : editRowData.buildSafe = 0
                                        // editRowData.buildCivilized ? editRowData.buildCivilized = +editRowData.buildCivilized : editRowData.buildCivilized = 0
                                        // editRowData.monthScore = editRowData.buildProgress + editRowData.buildSafe + editRowData.buildCivilized + +val
                                        // btnCallbackFn.qnnTableInstance.setEditedRowData(editRowData);

                                    }
                                }
                            },
                            {
                                table: {
                                    title: '文明施工（20）',
                                    dataIndex: 'buildCivilized',
                                    key: 'buildCivilized',
                                    width: 150,
                                    tdEdit: true
                                },
                                form: {
                                    field: 'buildCivilized',
                                    type: 'number',
                                    // required: true,
                                    precision: 0,
                                    max: 20,

                                    onChange: async (val, obj, btnCallbackFn) => {
                                        // let val = e.target.value;
                                        const editRowData = await btnCallbackFn.getEditedRowData()
                                        editRowData.buildProgress ? editRowData.buildProgress = +editRowData.buildProgress : editRowData.buildProgress = 0
                                        editRowData.buildSafe ? editRowData.buildSafe = +editRowData.buildSafe : editRowData.buildSafe = 0
                                        editRowData.buildQuality ? editRowData.buildQuality = +editRowData.buildQuality : editRowData.buildQuality = 0
                                        editRowData.monthScore = editRowData.buildProgress + editRowData.buildSafe + editRowData.buildQuality + +val
                                        await btnCallbackFn.setEditedRowData(editRowData);

                                        // const editRowData = btnCallbackFn.qnnTableInstance.getEditedRowData()
                                        // editRowData.buildProgress ? editRowData.buildProgress = +editRowData.buildProgress : editRowData.buildProgress = 0
                                        // editRowData.buildSafe ? editRowData.buildSafe = +editRowData.buildSafe : editRowData.buildSafe = 0
                                        // editRowData.buildQuality ? editRowData.buildQuality = +editRowData.buildQuality : editRowData.buildQuality = 0
                                        // editRowData.monthScore = editRowData.buildProgress + editRowData.buildSafe + editRowData.buildQuality + +val
                                        // btnCallbackFn.qnnTableInstance.setEditedRowData(editRowData);


                                    }
                                }
                            },
                            {
                                table: {
                                    title: '当月总分',
                                    dataIndex: 'monthScore',
                                    key: 'monthScore',
                                    width: 150,
                                },
                                form: {
                                    field: 'monthScore',
                                    type: 'number',
                                    required: true,
                                }
                            },
                            {
                                table: {
                                    title: '当月排名',
                                    dataIndex: 'monthRank',
                                    key: 'monthRank',
                                    width: 150,
                                },
                                form: {
                                    field: 'monthRank',
                                    type: 'number',
                                    required: true,
                                }
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
                                        this.props.myFetch('getZjLzehTeamScoreItemChartList', {
                                            scoreMonth: this.currentSelectScoreMonth,
                                            teamId: obj.rowData.teamId
                                        }).then(({ success, message, data }) => {
                                            if (success) {
                                                const monthScore = []
                                                const avg = []

                                                data[0].map(item => {
                                                    const month = new Date(item.scoreMonth).getMonth()
                                                    monthScore[month] = (item.monthScore ? item.monthScore : 0)
                                                })
                                                data[1].map(item => {
                                                    const month = new Date(item.scoreMonth).getMonth()
                                                    avg[month] = (item.avgScore ? item.avgScore : 0)
                                                })

                                                setTimeout(() => {
                                                    this.setState({
                                                        monthScoreList: avg,
                                                        mounthList: monthScore,
                                                        chartsShowStatus: true
                                                    })
                                                }, 0)
                                            }
                                        })
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
                    ></QnnTable>
                    <div style={{ marginTop: '20px' }}>
                        {chartsShowStatus ? <ReactEcharts
                            option={this.getOptionTow()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"} /> : null}
                    </div>

                </Drawer>
            </div >
        )
    }
}

export default index
