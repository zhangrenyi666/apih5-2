import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import ReactEcharts from 'echarts-for-react';
import QnnTable from "qnn-table"
import $ from 'jquery'
import Apih5 from 'qnn-apih5'

class Com extends Apih5 {
    state = {
        loading: true,
        TLiangDataNames: ["已完成", "未完成"],
        //T梁预制情况数据
        TLiangData: [
            // {
            //     label: "预制T梁",
            //     data1: 270,
            //     data2: 66,
            //     count: 336,
            // },
            // {
            //     label: "预制T梁",
            //     data1: 280,
            //     data2: 56,
            //     count: 336,
            // },
        ],
        //台座使用情况数据
        // taiZuoDataNames: ["使用中", "占用中", "闲置中"],
        taiZuoDataNames: ["使用中", "未使用"],
        taiZuoData: [
            // {
            //     label: "预制T梁",
            //     data1: 6,
            //     data2: 12,
            //     count: 18,
            // },
            // {
            //     label: "存梁台座",
            //     data1: 3,
            //     data2: 7,
            //     count: 10,
            // }
        ],

        //护栏和铺装
        hulanData: [
            // {
            //     label: "护栏总量",
            //     data1: 0,
            //     data2: 5237,
            //     count: 5237,
            // },
            // {
            //     label: "铺装总量",
            //     data1: 0,
            //     data2: 2592,
            //     count: 2592,
            // },
        ],

        //左边表格配置
        leftTableConfig: [

            {
                table: {
                    title: '梁体编号',
                    dataIndex: 'tbeamNo',
                    width: 50,
                    align: "center",
                    render: (val, rowData) => {
                        return <span style={{ color: rowData[`tbeamNoColor`] }}>{val}</span>
                    }
                }
            },
            // {
            //     table: {
            //         title: 'T梁名称',
            //         dataIndex: 'tbeamName',
            //         width: 50,
            //         align: "center"
            //     }
            // },
            {
                table: {
                    title: '预应力束编号',
                    dataIndex: 'preStressedNoId',
                    width: 60,
                    align: "center",
                    render: (val, rowData) => {
                        return <span style={{ color: rowData[`tensionA1Color`] }}>{val}</span>
                    }
                }
            },
            {
                table: {
                    title: '张拉时间',
                    dataIndex: 'stretchDrawDate',
                    width: 70,
                    format: "YYYY-MM-DD HH:mm",
                    align: "center"
                }
            },
            {
                table: {
                    title: '设计力<br/>(KN)',
                    dataIndex: 'designForce',
                    width: 50,
                    align: "center",
                    render: (val, rowData) => {
                        return <span style={{ color: rowData[`tensionA1Color`] }}>{val}</span>
                    }
                }
            },
            {
                table: {
                    title: 'A1张拉力<br/>(KN)',
                    dataIndex: 'tensionA1',
                    width: 50,
                    align: "center",
                    render: (data, rows) => {
                        let designForce = rows.designForce;
                        if (designForce <= data && data <= parseFloat(designForce) + 10) {
                            return <span style={{ color: 'rgb(68 243 68)' }}>{data + '↑'}</span>
                        } else if (parseFloat(designForce) + 10 < data) {
                            return <span style={{ color: '#f72222' }}>{data + '↑'}</span>
                        } else if (designForce > data) {
                            return <span style={{ color: '#f72222' }}>{data + '↓'}</span>
                        }
                    }
                }
            },
            // {
            //     table: {
            //         title: 'A1油压读数<br/>(Mpa)',
            //         dataIndex: 'oilA1',
            //         width: 50,
            //         align: "center",
            //         render: (val, rowData) => {
            //             return <span style={{ color: rowData[`oilA1Color`] }}>{val}</span>
            //         }
            //     }
            // },
            {
                table: {
                    title: 'A2张拉力<br/>(KN)',
                    dataIndex: 'tensionA2',
                    width: 50,
                    align: "center",
                    render: (data, rows, index) => {
                        let designForce = rows.designForce;
                        if (designForce <= data && data <= parseFloat(designForce) + 10) {
                            return <span style={{ color: 'rgb(68 243 68)' }}>{data + '↑'}</span>
                        } else if (parseFloat(designForce) + 10 < data) {
                            return <span style={{ color: '#f72222' }}>{data + '↑'}</span>
                        } else if (designForce > data) {
                            return <span style={{ color: '#f72222' }}>{data + '↓'}</span>
                        }
                    }
                }
            },
            // {
            //     table: {
            //         title: 'A2油压读数<br/>(Mpa)',
            //         dataIndex: 'oilA2',
            //         width: 50,
            //         align: "center",
            //         render: (val, rowData) => {
            //             return <span style={{ color: rowData[`oilA2Color`] }}>{val}</span>
            //         }
            //     }
            // },


            {
                table: {
                    title: '设计引伸量<br/>(mm)',
                    dataIndex: 'designExpansion',
                    width: 50,
                    align: "center",
                    render: (val, rowData) => {
                        return <span style={{ color: rowData[`designExpansionColor`] }}>{val}</span>
                    }
                }
            },
            {
                table: {
                    title: '实际引伸量<br/>(mm)',
                    dataIndex: 'actualExpansion',
                    width: 50,
                    align: "center",
                    render: (val, rowData) => {
                        return <span style={{ color: rowData[`actualExpansionColor`] }}>{val}</span>
                    }
                }
            },
            {
                table: {
                    title: '偏差',
                    dataIndex: 'offset',
                    flxed: 'left',
                    width: 50,
                    align: "center",
                    render: (data, rows, index) => {
                        let designExpansion = rows.designExpansion;
                        let actualExpansion = rows.actualExpansion;
                        data = ((actualExpansion - designExpansion) / designExpansion) * 100;
                        if (-6 <= data && data <= 6) {
                            return <span style={{ color: 'rgb(68 243 68)' }}>{data.toFixed(2) + "%" + (data > 0 ? '↑' : '↓')}</span>
                        } else if (6 < data) {
                            return <span style={{ color: '#f72222' }}>{data.toFixed(2) + '%↑'}</span>
                        } else if (-6 > data) {
                            return <span style={{ color: '#f72222' }}>{data.toFixed(2) + '%↓'}</span>
                        }
                    }
                }
            },
        ],


        leftTableData: [
            // {
            //     id: "0",
            //     tbeamNo: "10-1-zl",
            //     name2: '官塘大桥',
            //     oilA2: "43101",
            //     "张拉时间": 1234567894556,
            //     designExpansion: 1234567894556,
            //     actualExpansion: "5",
            //     offset: "150↑",
            //     offsetColor: "red", //加Color后缀颜色
            // },  
        ],

        tableHeight: (window.innerHeight - 64 - 44) / 2 / 2 - 19 - 16 - 16 - 16 - 22
    };
    isDidMount = false;
    componentDidMount() {
        this.isDidMount = true;
        if (this.props.isNeedGetData) {
            // if (false) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                data: [],
            })
        }
        window.addEventListener("resize", this.resize, false);

        //正常 需要 刷新 后在调用 
        this.scrollTableData();
    }
    componentWillUnmount() {
        this.isDidMount = false;
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimer);
        //  clearInterval(this.scrollRightTableDataTimer);
    }
    resize = () => {
        this.isDidMount && this.setState({
            tableHeight: (window.innerHeight - 64 - 44) / 2 / 2 - 19 - 16 - 16 - 16 - 22
        })
    };

    refresh = async () => {
        await this.refreshLeftTable();
        await this.refreshEcOne();
        await this.refreshEcTwo();
        await this.refreshEcThree();

        this.isDidMount && this.setState({
            loading: false
        });
    };

    refreshLeftTable = async () => {
        return new Promise(async (resolve) => {
            const { myFetch, errMsg } = this.props; 
            const { data, success, message, code } = await myFetch("getZjProZcStretchDrawListLimit", {});
            resolve()
            if (success) {
                //处理数据data
                this.setState({
                    leftTableData: data,
                });
            } else {
                errMsg(message, code);
            }
        })
    };
    refreshEcOne = async () => {
        return new Promise(async (resolve) => {
            const { myFetch, errMsg } = this.props;
            const { data, success, message, code } = await myFetch("getZjProZcTbeamAdministrationPrefabricationAndErection", {});
            resolve()
            if (success) {
                //处理数据data
                this.setState({
                    TLiangData: data.map(item => {
                        return {
                            label: item.statisticalName,
                            data1: item.overNumber,
                            data2: item.difference,
                            count: item.allNumber,
                        }
                    }),

                });
            } else {
                errMsg(message, code);
            }
        })
    };
    refreshEcTwo = async () => {
        return new Promise(async (resolve) => {
            const { myFetch, errMsg } = this.props;
            const { data, success, message, code } = await myFetch("getZjProZcTbeamPedestalUseConditionListForBigScreen", {});
            resolve();
            // console.log(data)
            if (success) {
                //处理数据data
                this.setState({
                    taiZuoData: data.map(item => {
                        return {
                            label: item.typeFlag,
                            data1: item.state0 + item.state1, //使用中
                            // data2: item.state1, //占用中
                            data2: item.state2, //闲置中
                            count: item.state0 + item.state1 + item.state2,
                        }
                    }),
                });
            } else {
                errMsg(message, code);
            }
        })
    };
    refreshEcThree = async () => {
        return new Promise(async (resolve) => {
            const { myFetch, errMsg } = this.props;
            const { data, success, message, code } = await myFetch("getZjProZcTbeamAdministrationGuardrailAndPavement", {});
            resolve();
            if (success) {
                //处理数据data 
                this.setState({
                    hulanData: data.map(item => {
                        return {
                            label: item.statisticalName,
                            data1: item.overNumber,
                            data2: item.difference,
                            count: item.allNumber,
                        }
                    }),

                });
            } else {
                errMsg(message, code);
            }
        })
    };

    //滚动列表数据
    scrollTableData = () => {
        const timer = 1 * 2000; //动画间隔

        const aniFn = ($dom, dataName, tdHeight = 21) => {
            const dataList = this.state[dataName];
            if (!dataList.length) {
                return;
            }

            $dom.css({
                transition: `${timer / 1000}s`,
                transform: `translateY(-${tdHeight}px)`
            });
            setTimeout(() => {
                let delEle = dataList.shift();
                dataList.push(delEle);
                this.setState({
                    [dataName]: dataList
                }, () => {
                    $dom.css({
                        transition: `0s`,
                        transform: `translateY(-0px)`
                    })
                })
            }, timer)
        }

        //执行两表格的动画 
        clearInterval(this.scrollLeftTableDataTimer);
        this.scrollLeftTableDataTimer = setInterval(() => aniFn($(`.${s.tableContainer} .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    //获取柱状图 的配置
    getOptionOne = (data, TLiangDataNames = [], colors = ['#0f6fc6', '#f49100']) => {
        return {
            color: colors,
            legend: {
                right: -40,
                top: -5,
                data: data.legendData,
                itemGap: 5,
                itemWidth: 10,
                itemHeight: 8,
                textStyle: {
                    fontSize: 10,
                    color: 'white'
                },
                selected: data.map(item => {
                    return {
                        name: item.label,
                        // 强制设置图形为圆。
                        icon: 'circle',
                        // 设置文本为红色
                    }

                }),
            },
            grid: {
                left: '0%',
                right: '0%',
                top: '28%',
                bottom: '3%',
                containLabel: true,
            },
            xAxis: [
                {
                    type: 'category',
                    data: data.map(item => item.label),
                    axisLabel: {
                        color: "white",
                        fontSize: 12,
                    },
                    axisLine:{
                        show:true,
                        lineStyle: {
                            color: 'rgba(151, 151, 151, 0.48)' 
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: 'rgba(151, 151, 151, 0.48)' 
                        }
                    },
                    axisLine:{
                        show:true,
                        lineStyle: {
                            color: 'rgba(151, 151, 151, 0.48)' 
                        }
                    },
                    axisTick: {
                        color: "white",
                        show: true,

                    },
                    axisLabel: {
                        color: "white",
                        fontSize: 12,
                        // 使用函数模板，函数参数分别为刻度数值（类目），刻度的索引
                        formatter: function (value, index) {
                            return `${index * 25}%`;
                        }
                    },
                    // max: Math.max(...data.map(item => item.count)), //需要比100多一点用于放置总数
                    max: 101, //需要比100多一点用于放置总数
                    interval: 25,
                    splitNumber: 4
                }
            ],
            series: [
                {
                    name: TLiangDataNames[0],
                    type: 'bar',
                    stack: 'T梁',
                    data: data.map(item => item.data1 / item.count * 100),
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: ({ dataIndex }) => {
                            return data[dataIndex].data1
                        }
                    },
                    barWidth: 49,
                    zlevel: ((() => {
                        let _data = data.filter(item => item.data1 < 35);
                        return _data.length ? 3 : 1
                    })())
                },
                {
                    name: TLiangDataNames[1],
                    type: 'bar',
                    stack: 'T梁',
                    data: data.map(item => item.data2 / item.count * 100),
                    // data: data.map(item =>  100),
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: ({ dataIndex }) => {
                            return (data[dataIndex].data2)
                        }
                    },
                    barWidth: 49,
                    zlevel: ((() => {
                        let _data = data.filter(item => item.data2 < 35);
                        return _data.length ? 3 : 1
                    })()),

                },
                {
                    name: '总数',
                    type: 'bar',
                    stack: 'T梁',
                    data: data.map(item => 0),
                    itemStyle: {
                        normal: {
                            color: "transparent"
                        }
                    },
                    label: {
                        show: true,
                        // position: 'inside', 
                        offset: [0, -10],
                        formatter: ({ dataIndex }) => { 
                            return data[dataIndex].count
                        }
                    },
                    // barWidth: 49, 
                    zlevel: 999
                }
            ]

        }
    }


    render() {
        const {
            loading,
            TLiangData,
            TLiangDataNames,
            taiZuoData, taiZuoDataNames,
            // taiZuoData2,
            // puZhuangData,
            // rightTableConfig,rightTableData,
            hulanData,
            leftTableConfig,
            leftTableData,
            tableHeight
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: false,
            isShowRowSelect: false,
        }
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    {/* <div className={s.title}>
                        预制生产
                    </div> */}
                    <div className={s.con}>
                        <div className={s.echartsContainer}>
                            <div className={s.ectItem} onClick={() => {
                                this.props.openWindow('DataTableDetailByPrefabricate')
                            }}>
                                <div className={s.title} style={{ marginBottom: 3 }}>
                                    预制和架设完成情况
                                </div>
                                <ReactEcharts
                                    key="TLiangData"
                                    style={{ width: '100%', height: "calc(100% - 28px)" }}
                                    option={this.getOptionOne(TLiangData, TLiangDataNames)}
                                    notMerge={true}
                                    lazyUpdate={true}
                                    theme={"theme_name"}
                                />
                            </div>
                            <div className={s.ectItem} onClick={() => {
                                this.props.openWindow('DataTableDetailByUseOfPedestal')
                            }}>
                                <div className={s.title} style={{ marginBottom: 3 }}>
                                    台座使用情况
                                </div>
                                <ReactEcharts
                                    key="taiZuoData"
                                    style={{ width: '100%', height: "calc(100% - 28px)" }}
                                    option={this.getOptionOne(taiZuoData, taiZuoDataNames, ['rgb(2, 143, 194)', 'rgb(118,175,62)'])}
                                    notMerge={true}
                                    lazyUpdate={true}
                                    theme={"theme_name"}
                                />
                            </div>
                            <div className={s.ectItem}>
                                <div className={s.title} style={{ marginBottom: 3 }}>
                                    护栏工程量完成情况
                                </div>
                                <ReactEcharts
                                    key="hulanData"
                                    style={{ width: '100%', height: "calc(100% - 28px)" }}
                                    // option={this.getOptionTwo(hulanData)}
                                    option={this.getOptionOne(hulanData, TLiangDataNames, ['rgb(128, 0, 128)', 'rgb(255, 204, 0)'])}
                                    notMerge={true}
                                    lazyUpdate={true}
                                    theme={"theme_name"}
                                />
                            </div>
                        </div>
                        <div className={`${s.tableContainer}`}>
                            <div className={s.title} style={{ marginBottom: 3 }}>
                                预应力张拉监控
                            </div>
                            <div className={s.tableLeft} onClick={() => {
                                this.props.openWindow('DataTableDetailByTension')
                            }}>
                                <QnnTable
                                    formConfig={leftTableConfig}
                                    data={leftTableData}
                                    antd={{
                                        rowKey: "stretchDrawId",
                                        size: "small",
                                        scroll: {
                                            y: tableHeight
                                        }
                                    }}
                                    {...qnnTableCommConfig}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Com;
