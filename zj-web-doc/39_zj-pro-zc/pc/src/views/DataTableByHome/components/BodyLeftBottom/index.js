import React,{ Component } from "react";
import { Spin } from "antd";
import s from "./style.less";
import QnnTable from "qnn-table"
import $ from 'jquery'

class Com extends Component {
    state = {
        loading: true,

        leftTableData: [
            // {
            //     id: "0",
            //     tbeamNo: "10-1-zl",
            //     tbeamName: '官塘大桥',
            //     number: "43101",
            //     dwellTime: 1234567894556,
            //     "压浆时间": 1234567894556,
            //     huiSuoLiang: "5",
            //     fuheShiJian: "150",
            // },

        ],
        //表格配置
        leftTableConfig: [

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
                    title: '梁体编号',
                    dataIndex: 'tbeamId',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '预应力束编号',
                    dataIndex: 'preStressedNoId',
                    width: 60,
                    align: "center",
                    type: 'select',
                },
                form: {
                    type: 'select',
                    required: true,
                    field: 'preStressedNoId',
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: "yuYingLiShuBianHao"
                        }
                    },
                    optionConfig: {
                        label: "itemName",
                        value: "itemId"
                    },
                    // addShow:false,
                }
            },
            {
                table: {
                    title: '压浆时间',
                    dataIndex: 'mudjackDate',
                    width: 70,
                    format:'YYYY/MM/DD', 
                    align: "center"
                }
            },
            {
                table: {
                    title: '浆液配合比',
                    dataIndex: 'slurryMixRatio',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '浆液稠度(s)',
                    dataIndex: 'slurryConsistency',
                    width: 50,
                    align: "center"
                }
            },
            // {
            //     table: {
            //         title: '注浆时间(s)',
            //         dataIndex: 'groutingTime',
            //         width: 70, 
            //         align: "center"
            //     }
            // },
            {
                table: {
                    title: '注浆压力<br/>(Mpa)',
                    dataIndex: 'groutingPress',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '保压压力<br/>(Mpa)',
                    dataIndex: 'pulpPress',
                    flxed: 'left',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '保压时间(s)',
                    dataIndex: 'dwellTime',
                    width: 50, 
                    align: "center"
                }
            },
        ],

        //保护层信息表格
        baoHuCengTableConfig: [
            {
                table: {
                    title: '<div>梁板编号</div>',
                    dataIndex: 'beamPlateNo',
                    flxed: 'left',
                    width: 30,
                    align: "center"
                }
            },
            {
                table: {
                    title: '<div>第一条测线合格率</div>',
                    dataIndex: 'rate1',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '<div>第二条测线合格率</div>',
                    dataIndex: 'rate2',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '<div>第三条测线合格率</div>',
                    dataIndex: 'rate3',
                    width: 50,
                    // format: "HH:mm",
                    align: "center"
                }
            },
            {
                table: {
                    title: '<div>第四条测线合格率</div>',
                    dataIndex: 'rate4',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '<div>第五条测线合格率</div>',
                    dataIndex: 'rate5',
                    width: 50,
                    align: "center"
                }
            },
            {
                table: {
                    title: '<div>平均合格率</div>',
                    dataIndex: 'averageRate',
                    width: 30,
                    align: "center"
                }
            },
        ],
        baoHuCengTableData: [
            // {
            //     id: "0",
            //     name: "10-1-zl",
            //     name2: '官塘大桥',
            //     rate1: "43101",
            //     time: 1234567894556,
            //     shengZhangLiang: 1234567894556,
            //     huiSuoLiang: "5",
            //     fuheShiJian: "150",
            // },

        ],


        tableHeight: (window.innerHeight - 64 - 44) * 0.25 - 85
    };
    isDidMount = false;
    componentDidMount() {
        this.isDidMount = true;
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                data: `测试数据`,
            })
        }
        window.addEventListener("resize",this.resize,false);

    }
    componentWillUnmount() {
        this.isDidMount = false;
        window.removeEventListener("resize",this.resize);
        clearInterval(this.scrollLeftTableDataTimer);
    }
    resize = () => {
        this.isDidMount && this.setState({
            tableHeight: (window.innerHeight - 64 - 44) * 0.25- 85
        })
    };

    refresh = async () => {
        await this.refreshZNYJ();
        await this.refreshBaohuCeng();
        this.isDidMount && this.setState({ loading: false });
        //正常 需要 刷新 后在调用 
        this.scrollTableData();
    };

    //智能压浆监控 
    refreshZNYJ = async () => {
        return new Promise(async (resolve) => {
            const { myFetch,errMsg } = this.props;
            const { data,success,message,code } = await myFetch("getZjProZcMudjackListLimit",{});
            resolve();
            if (success) {
                //处理数据data
                this.isDidMount && this.setState({
                    leftTableData: data,
                });
            } else {
                errMsg(message,code);
            }
        })
    };

    //保护层 
    refreshBaohuCeng = async () => {
        return new Promise(async (resolve) => {
            const { myFetch,errMsg } = this.props;
            const { data,success,message,code } = await myFetch("getZjProZcProtectLayerListLimit",{});
            resolve();
            if (success) {
                //处理数据data
                this.isDidMount && this.setState({
                    baoHuCengTableData: data,
                });
            } else {
                errMsg(message,code);
            }
        })
    };
    //滚动列表数据
    scrollTableData = () => {
        const timer = 1 * 2000; //动画间隔

        const aniFn = ($dom, dataName, tdHeight = 21) => {
            const dataList = this.state[dataName];
            if (!dataList.length || dataList.length < 7) { 
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
        clearInterval(this.scrollLeftTableDataTimer2);
        this.scrollLeftTableDataTimer = setInterval(() => aniFn($(`.${s.tableContainer} .ant-table-body table`).eq(0),"leftTableData"),timer * 2);
        this.scrollLeftTableDataTimer2 = setInterval(() => aniFn($(`.${s.baoHuCengContent} .ant-table-body table`).eq(0),"baoHuCengTableData"),timer * 2);

    }

    getPMText = (num) => {
        let _num = num + "";
        switch (_num) {
            case "1":
                return "第一名"
            case "2":
                return "第二名"
            case "3":
                return "第三名"
            default:
                return;
        }
    }

    render() {
        const {
            loading,
            leftTableConfig,leftTableData,
            tableHeight,

            baoHuCengTableConfig,
            baoHuCengTableData
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: false,
            isShowRowSelect: false,
        }
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>

                    <div className={s.con}>
                        <div className={s.tableContainer}>
                            <div className={s.title}>
                                智能压浆监控
                            </div>
                            <div className={s.table} onClick={() => {
                                this.props.openWindow('DataTableDetailByPressMortar')
                            }}>
                                <QnnTable
                                    formConfig={leftTableConfig}
                                    data={leftTableData}
                                    antd={{
                                        rowKey: "mudjackId",
                                        size: "small",
                                        scroll: {
                                            y: tableHeight
                                        }
                                    }}
                                    {...qnnTableCommConfig}
                                />
                            </div>
                        </div>

                        <div className={`${s.baoHuCengContainer}`}>
                            <div className={`${s.baoHuCengContent}`}>
                                <div className={s.title}>
                                    保护层信息
                                </div>
                                <div className={s.table} onClick={() => {
                                    this.props.openWindow('DataTableDetailByBaoHuCeng')
                                }}>
                                    <QnnTable
                                        antd={{
                                            rowKey: "safeScoreId",
                                            size: "small",
                                            scroll: {
                                                y: tableHeight + 16
                                            },
                                            locInfo: {
                                                emptyText: "暂无数据1"
                                            }
                                        }}
                                        formConfig={baoHuCengTableConfig}
                                        data={baoHuCengTableData}
                                        {...qnnTableCommConfig}
                                    />
                                </div>

                            </div>
                        </div>

                    </div>
                </div>
            </Spin>
        );
    }
}
export default Com;
