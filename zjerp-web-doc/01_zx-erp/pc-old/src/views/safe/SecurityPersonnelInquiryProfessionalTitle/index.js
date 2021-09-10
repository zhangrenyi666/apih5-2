import React, { Component } from "react";
import { message as Msg, Button, Table } from 'antd';
import ReactEcharts from 'echarts-for-react';
import QnnForm from "../../modules/qnn-form";
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            loading: false,
            dataTow: [{ value: 85, name: '注安师' },{ value: 85, name: '高级' },{ value: 85, name: '中级' },{ value: 85, name: '初级' },{ value: 85, name: '无' }],
            dataSource: [],
            startDate: undefined,
            endDate: undefined
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
                        this.fourTypesGetNextNodeContentSetTreeFunction('GD', 'getZxSfProEmpMainZhiChengGuiDangList', row)
                        break
                    case 1:
                        this.fourTypesGetNextNodeContentSetTreeFunction('JG', 'getZxSfProEmpMainZhiChengJiaoGongList', row)
                        break
                    case 2:
                        this.fourTypesGetNextNodeContentSetTreeFunction('WG', 'getZxSfProEmpMainZhiChengWanGongList', row)
                        break
                    case 3:
                        this.fourTypesGetNextNodeContentSetTreeFunction('KG', 'getZxSfProEmpMainZhiChengKaiGongList', row)
                        break
                }
            }
        }
    }

    // 四种类型获取下一节点内容 插入Tree
    fourTypesGetNextNodeContentSetTreeFunction(type, apiName, row) {
        this.props.myFetch(apiName, {
            startDate: this.state.startDate,
            endDate: this.state.endDate,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let statusDataList = []
                statusDataList = res.data.map((item, index) => {
                    return {
                        key: `${row.key}_${index}`,
                        companyName: item.orgName,
                        companyId: item.orgID,
                        yes: res.data.yes,
                        gaoji: res.data.gaoji,
                        zhongji: res.data.zhongji,
                        chuji: res.data.chuji,
                        wu: res.data.wu,
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
        this.props.myFetch('getZxSfProEmpMainZhiChengComList', {
            startDate: this.state.startDate,
            endDate: this.state.endDate,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let statusDataList = []
                statusDataList = res.data.map(item => {
                    return {
                        key: `${row.key}_${index}`,
                        companyName: item.companyName,
                        companyId: item.companyId,
                        yes: res.data.yes,
                        gaoji: res.data.gaoji,
                        zhongji: res.data.zhongji,
                        chuji: res.data.chuji,
                        wu: res.data.wu,
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
        this.props.myFetch('getZxSfProEmpMainZhiChengOrgList', {
            startDate: this.state.startDate,
            endDate: this.state.endDate,
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
                        yes: res.data.yes,
                        gaoji: res.data.gaoji,
                        zhongji: res.data.zhongji,
                        chuji: res.data.chuji,
                        wu: res.data.wu,
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
                this.setRootNodeFunc('getZxSfProEmpMainZhiChengJuInfo', 'bureauLevel')
                break
            // 公司级单位
            case '2':
                this.setRootNodeFunc('getZxSfProEmpMainZhiChengComInfo', 'company')
                break
        }
    }

    // 设置根节点内容的函数
    setRootNodeFunc(apiName, type) {
        this.props.myFetch(apiName, {
            startDate: this.state.startDate,
            endDate: this.state.endDate,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let rootDodeData = {
                    key: 1,
                    companyName: res.data.companyName,
                    companyId: res.data.companyId,
                    yes: res.data.yes,
                    gaoji: res.data.gaoji,
                    zhongji: res.data.zhongji,
                    chuji: res.data.chuji,
                    wu: res.data.wu,
                    children: [],
                    type,
                }
                // 饼状图的对应数据list
                const pieChartData = [res.data.yes, res.data.gaoji,res.data.zhongji, res.data.chuji,res.data.wu]

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
              text: '安全人员按职称统计',
              left: 'center'
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
              orient: 'vertical',
              left: 'left',
              data: ['注安市', '高级','中级','初级','无']
            },
            color: ['#9933CC', '#FFCC00','#66FF66','#FF66FF','#9966CC'],
            series: [
              {
                name: '安全人员按职称统计',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: dataTow,
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
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
                startDate: this.formOne?.form?.getFieldValue('startDate'),
                endDate: this.formOne?.form?.getFieldValue('endDate')
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
                                title: '注安师',
                                dataIndex: 'yes',
                                key: 'yes',
                            },
                            {
                                title: '高级',
                                dataIndex: 'gaoji',
                                key: 'gaoji',
                            },
                            {
                                title: '中级',
                                dataIndex: 'zhongji',
                                key: 'zhongji',
                            },
                            {
                                title: '初级',
                                dataIndex: 'chuji',
                                key: 'chuji',
                            },
                            {
                                title: '无',
                                dataIndex: 'wu',
                                key: 'wu',
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