import React, { Component } from "react";
import {  Button, Table } from 'antd';
import ReactEcharts from 'echarts-for-react';
import QnnForm from "../../modules/qnn-form";
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            loading: false,
            dataTow: [{
                value: 200,
                itemStyle: {
                    color: '#FF66FF'
                }
            }, {
                value: 200,
                itemStyle: {
                    color: '#3333FF'
                }
            },
            {
                value: 200,
                itemStyle: {
                    color: '#FFFF33'
                }
            },
            {
                value: 200,
                itemStyle: {
                    color: '#66CCFF'
                }
            },
            {
                value: 200,
                itemStyle: {
                    color: '#99FF99'
                }
            }],
            dataSource: [],
            season: undefined,
            projType: undefined
        }
    }
    // 点击展开图标的事件合集
    expandable = {
        onExpand: (ev, row) => {
            if (ev) {
                switch (row.type) {
                    case 'bureauLevel':
                        this.setBureauLevelSecondNodeFunc(row) // 局级设置二级节点
                        break
                    case 'company':
                        this.companyToProjectTypeSetNodeFunction(row) // 设置二级节点的四个类型节点的函数（公司级别）
                        break
                    case 0:
                        this.fourTypesGetNextNodeContentSetTreeFunction('GD', 'getZxSfFeeGuiDangList', row)
                        break
                    case 1:
                        this.fourTypesGetNextNodeContentSetTreeFunction('JG', 'getZxSfFeeJiaoGongList', row)
                        break
                    case 2:
                        this.fourTypesGetNextNodeContentSetTreeFunction('WG', 'getZxSfFeeWanGangList', row)
                        break
                    case 3:
                        this.fourTypesGetNextNodeContentSetTreeFunction('KG', 'getZxSfFeeKaiGongList', row)
                        break
                    default:
                        break
                }
            }
        }
    }

    // 四种类型获取下一节点内容 插入Tree
    fourTypesGetNextNodeContentSetTreeFunction(type, apiName, row) {
        this.props.myFetch(apiName, {
            season: this.state.season,
            projType: this.state.projType,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data&&res.data.length) {
                let statusDataList = []
                statusDataList = res.data.map((item, index) => {
                    return {
                        key: `${row.key}_${index}`,
                        companyName: item.orgName,
                        companyId: item.orgID,
                        zjCost: res.data.zjCost,
                        zjSfCost: res.data.zjSfCost,
                        endCost: res.data.endCost,
                        endSfCost:res.data.endSfCost,
                        cost: res.data.cost,
                        type: `${row.key}_${index}`,
                        children: [],
                    }
                })
                this.setDataSource(statusDataList, row.key)
            }
        })
    }

    // 设置局级的二级节点的函数
    setBureauLevelSecondNodeFunc(row) {
        this.props.myFetch('getZxSfFeeCompanyList', {
            season: this.state.season,
            projType: this.state.projType,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let statusDataList = []
                statusDataList = res.data.map(item => {
                    return {
                        key: `${row.key}_${index}`,
                        companyName: item.companyName,
                        companyId: item.companyId,
                        zjCost: res.data.zjCost,
                        zjSfCost: res.data.zjSfCost,
                        endCost: res.data.endCost,
                        endSfCost:res.data.endSfCost,
                        cost: res.data.cost,
                        type: 'company',
                        children: [],
                    }
                })
                this.setDataSource(statusDataList, row.key)
            }
        })
    }

    // 设置二级节点的四个类型节点的函数（公司级）
    companyToProjectTypeSetNodeFunction(row) {
        this.props.myFetch('getZxSfFeeOrgList', {
            season: this.state.season,
            projType: this.state.projType,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let statusDataList = []
                const projectTypeList = ['归档', '交工', '完工', '开工']
                // 查询项目后 返回四个数组 数组对应上方数组的顺序↑
                statusDataList = res.data.map((item, index) => {
                    return {
                        key: `${row.key}_${index}`,
                        companyName: projectTypeList[index],
                        companyId: row.companyId,
                        zjCost: res.data.zjCost,
                        zjSfCost: res.data.zjSfCost,
                        endCost: res.data.endCost,
                        endSfCost:res.data.endSfCost,
                        cost: res.data.cost,
                        type: index,
                        children: [],
                    }
                })
                this.setDataSource(statusDataList, row.key)
            }
        })
    }

    componentDidMount = () => {
        // 初始化页面 判断当前登录人的 权限 是局级 还是公司级 
        // 如果 当前登录人 为局级 => 把局级单位设置为根节点
        // 然后点击展开局级节点按钮 查询公司列表 把公司级内容插入二级节点中

        // 如果 当前登录人为公司级单位 => 把对应公司内容 作为根节点 
        this.initializationRootNodeFunction()

    }

    // 初始化树形结构的根节点的方法（查询时间的时候需要重置属性结构）
    initializationRootNodeFunction() {
        switch (this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1) {
            // 局级单位
            case '1':
                this.setRootNodeFunc('getZxSfFeeJuInfo', 'bureauLevel')
                break
            // 公司级单位
            case '2':
                this.setRootNodeFunc('getZxSfFeeCompany', 'company')
                break
            default:
                break
        }
    }

    // 设置根节点内容的函数
    setRootNodeFunc(apiName, type) {
        this.props.myFetch(apiName, {
            season: this.state.season,
            projType: this.state.projType,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let rootDodeData = {
                    key: 1,
                    companyName: res.data.companyName,
                    companyId: res.data.companyId,
                    zjCost: res.data.zjCost,
                    zjSfCost: res.data.zjSfCost,
                    endCost: res.data.endCost,
                    endSfCost:res.data.endSfCost,
                    cost: res.data.cost,
                    children: [],
                    type,
                }
                // 饼状图的对应数据list
                const pieChartData = [res.data.zjCost, res.data.zjSfCost,res.data.endCost,res.data.endSfCost,res.data.cost]

                // 根据根节点 初始化饼状图
                this.setState({
                    dataTow: this.state.dataTow.map((item, index) => {
                        return item = {
                            ...item,
                            value: pieChartData[index]
                        }
                    })
                })

                this.setDataSource([{ ...rootDodeData }])
            }
        })
    }

    // echats的依赖数据过滤
    getOptionTow = () => {
        const { dataTow } = this.state;
        var option = {
            title: {
                text: '安全费用检查统计',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '<br/>{b} : {c} '
            },
            xAxis: {
                type: 'category',
                data: ['在建合同额', '费用总预计', '已完成合同额', '已完成合同额安全给用预计', '实际支出',]
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: dataTow,
                type: 'bar'
            }]
        };
        return option;
    }

    // 递归函数 插入子节点
    getNodeFn = (data, key, val) => {
        let dataList = data
        for (let i = 0; i < dataList.length; i++) {
            if (dataList[i].key === key) {
                dataList[i].children = [...val]
                this.setState({
                    dataSource: [...this.nodeTreeDataList]
                })
                return
            } else {
                if (dataList[i].children && dataList[i].children.length) {
                    this.getNodeFn(dataList[i].children, key, val)
                } else {
                    return
                }
            }
        }
    }

    nodeTreeDataList = []
    setDataSource = (val, nodeKey) => {
        if (this.state.dataSource.length) {
            // 这个分支是添加子节点的
            // 递归查询当前节点
            this.nodeTreeDataList = [...this.state.dataSource]
            this.getNodeFn(this.nodeTreeDataList, nodeKey, val)
        } else {
            // 这个部分是页面初始化创建 树形结构根节点的if分支
            this.setState({
                dataSource: [...val]
            })
        }
    }

    // 获取查询的时间
    queryDataOfDateCompFunction() {
        setTimeout(() => {
            this.setState({
                season: this.formOne?.form?.getFieldValue('season'),
                projType: this.formOne?.form?.getFieldValue('projType')
            })

            // 这里获取了当前选中时间的值 需要重新获取树形控件的根节点
            this.setState({
                dataSource: []
            })
            this.initializationRootNodeFunction()
        }, 0)
    }
    render() {
        return (
            <div>
                {/* 饼图 */}
                <ReactEcharts
                    option={this.getOptionTow()}
                    notMerge={true}
                    lazyUpdate={true}
                    theme={"theme_name"}
                />
                {/* 日期 查询  */}
                <div>
                    <QnnForm
                        wrappedComponentRef={(me) => {
                            this.formOne = me;
                        }}
                        fetch={this.props.myFetch}
                        formConfig={
                            [
                                {
                                    type: 'quarter',
                                    label: '季度',
                                    field: 'season', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    span: 6,
                                },
                                {
                                    type: 'select',
                                    label: '工程类型',
                                    field: 'projType', //唯一的字段名 ***必传
                                    placeholder: '请选择',
                                    span: 6,
                                    optionData: [//默认选项数据//可为function (props)=>array

                                    ],

                                    optionConfig: {//下拉选项配置 可为func
                                        label: 'itemName',
                                        value: 'itemId'
                                    },
                                    fetchConfig: {//配置后将会去请求下拉选项数据  可为func
                                        apiName: 'getBaseCodeSelect',
                                        otherParams: {
                                            itemId: "gongChengLeiBie"
                                        },
                                    },
                                    onChange: (value, { itemData, ...other }) => {

                                    }
                                },
                                {
                                    type: 'component',
                                    field: 'diy',
                                    span: 6,
                                    //第一种，推荐定义方式 需要将componentsKey对象传到qnn-form
                                    // Component: "myDiyComponent",

                                    //第二种自定义组件方式
                                    Component: obj => {
                                        return (
                                            <div style={{ height: '100%', display: 'flex', alignItems: 'center' }}>
                                                <Button type="primary" onClick={() => {
                                                    this.queryDataOfDateCompFunction()
                                                }}>查询</Button>
                                            </div>
                                        );
                                    }
                                }
                            ]
                        }
                    ></QnnForm>
                </div>
                {/* 树形结构 */}
                <div>
                    {this.state.dataSource.length ? <Table
                        selections={false}
                        columns={[
                            {
                                title: '',
                                dataIndex: 'companyName',
                                key: 'companyName',
                            },
                            {
                                title: '在建合同额（万元）',
                                dataIndex: 'zjCost',
                                key: 'zjCost',
                            },
                            {
                                title: '安全费用总预计（万元）',
                                dataIndex: 'zjSfCost',
                                key: 'zjSfCost',
                            },
                            {
                                title: '已完合同额（万元）',
                                dataIndex: 'endCost',
                                key: 'endCost',
                            },
                            {
                                title: '已完合同额 安全费用预计（万元）',
                                dataIndex: ' endSfCost',
                                key: ' endSfCost',
                            },
                            {
                                title: '实际支出（万元）',
                                dataIndex: 'cost',
                                key: 'cost',
                            },
                        ]}
                        //   行选择的事件
                        // rowSelection={{ ...this.rowSelection }}
                        // 点击展开按钮的事件
                        expandable={{ ...this.expandable }}
                        dataSource={this.state.dataSource}
                    /> : null}
                </div>
            </div>
        )
    }
}
export default index