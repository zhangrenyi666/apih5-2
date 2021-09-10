import React, { Component } from "react";
import { Table } from 'antd';
import QnnForm from "../../modules/qnn-form";
class index extends Component {
    constructor(props) {
        super(props)
        this.state = {
            loading: false,
            dataSource: [],
            year: undefined,
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
                        this.fourTypesGetNextNodeContentSetTreeFunction('GD', 'getZxSfPlanTargetGuiDangList', row)
                        break
                    case 1:
                        this.fourTypesGetNextNodeContentSetTreeFunction('JG', 'getZxSfPlanTargetJiaoGongList', row)
                        break
                    case 2:
                        this.fourTypesGetNextNodeContentSetTreeFunction('WG', 'getZxSfPlanTargetWanGongList', row)
                        break
                    case 3:
                        this.fourTypesGetNextNodeContentSetTreeFunction('KG', 'getZxSfPlanTargetKaiGongList', row)
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
            year: this.state.year,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let statusDataList = []
                statusDataList = res.data.map((item, index) => {
                    return {
                        key: `${row.key}_${index}`,
                        companyName: item.orgName,
                        companyId: item.orgID,
                        deadRate: res.data.deadRate,
                        injuresRate: res.data.injuresRate,
                        slightlyRate: res.data.slightlyRate,
                        hidChageRate: res.data.hidChageRate,
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
        this.props.myFetch('getZxSfPlanTargetComList', {
            year: this.state.year,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let statusDataList = []
                statusDataList = res.data.map(item => {
                    return {
                        key: `${row.key}_${index}`,
                        companyName: item.companyName,
                        companyId: item.companyId,
                        deadRate: res.data.deadRate,
                        injuresRate: res.data.injuresRate,
                        slightlyRate: res.data.slightlyRate,
                        hidChageRate: res.data.hidChageRate,
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
        this.props.myFetch('getZxSfPlanTargetOrgList', {
            year: this.state.year,
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
                        deadRate: res.data.deadRate,
                        injuresRate: res.data.injuresRate,
                        slightlyRate: res.data.slightlyRate,
                        hidChageRate: res.data.hidChageRate,
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
        console.log(this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1)
        switch (this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1) {
            // 局级单位
            case '1':
                this.setRootNodeFunc('getZxSfProEmpMainZhiChengJuInfo', 'bureauLevel')
                break
            // 公司级单位
            case '2':
                this.setRootNodeFunc('getZxSfPlanTargetComInfo', 'company')
                break
            default:
                break
        }
    }

    // 设置根节点内容的函数
    setRootNodeFunc(apiName, type) {
        this.props.myFetch(apiName, {
            year: this.state.year,
            companyId: this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.companyId
        }).then(res => {
            if (res.data) {
                let rootDodeData = {
                    key: 1,
                    companyName: res.data.companyName,
                    companyId: res.data.companyId,
                    deadRate: res.data.deadRate,
                    injuresRate: res.data.injuresRate,
                    slightlyRate: res.data.slightlyRate,
                    hidChageRate: res.data.hidChageRate,
                    children: [],
                    type,
                }
                // // 饼状图的对应数据list
                // const pieChartData = [res.data.deadRate, res.data.injuresRate,res.data.slightlyRate, res.data.hidChageRate]

                // // 根据根节点 初始化饼状图
                // this.setState({
                //     dataTow: this.state.dataTow.map((item, index) => {
                //         return item = {
                //             ...item,
                //             value: pieChartData[index]
                //         }
                //     })
                // })

                this.setDataSource([{ ...rootDodeData }])
            }
        })
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
                year: this.formOne?.form?.getFieldValue('year'),
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
                {/* 日期 查询  */}
                <div>
                    <QnnForm
                        wrappedComponentRef={(me) => {
                            this.formOne = me;
                        }}
                        formConfig={
                            [
                                {
                                    type: "year",
                                    label: "请选择年份",
                                    field: "year", //唯一的字段名 ***必传
                                    scope: false, //是否可选择范围
                                    span: 8,
                                    onChange: (val, obj) => {
                                        this.queryDataOfDateCompFunction()
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
                                title: '因公死亡率（%）',
                                dataIndex: 'deadRate',
                                key: 'deadRate',
                            },
                            {
                                title: '重伤率（%）',
                                dataIndex: 'injuresRate',
                                key: 'injuresRate',
                            },
                            {
                                title: '轻伤率（%）',
                                dataIndex: 'slightlyRate',
                                key: 'slightlyRate',
                            },
                            {
                                title: '隐患整改率（%）',
                                dataIndex: 'hidChageRate',
                                key: 'hidChageRate',
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