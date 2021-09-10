import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import Apih5 from "qnn-apih5";
import QnnTable from "qnn-table";
import $ from "jquery"

class Readme extends Apih5 {
    state = {
        loading: false,
        tableHeight: (window.innerHeight - 64 - 44) / 2 / 2 - 19 - 16 - 16 - 16,

        //右边排名配置 + 数据
        paiMing2: [
            {
                table: {
                    title: '使用部位',
                    dataIndex: 'positionName',
                    width: 60,
                    align: "center",
                    tooltip: 8
                }
            },
            {
                table: {
                    title: '计划使用日期',
                    dataIndex: 'planUseDate',
                    width: 80,
                    align: "center",
                    // format: "YYYY/MM/DD",
                }
            },
            {
                table: {
                    title: '提交日期',
                    dataIndex: 'planReportDate',
                    width: 60,
                    align: "center",
                    // format: "YYYY/MM/DD",
                }
            },
            {
                table: {
                    title: '提交人',
                    dataIndex: 'planReportByName',
                    width: 50,
                    align: "center",
                }
            },
            {
                table: {
                    title: '配送状态',
                    dataIndex: 'auditStatusName',
                    width: 50,
                    align: "center",
                }
            },
            {
                table: {
                    title: '是否驳回',
                    dataIndex: 'rejectFlag',
                    width: 50,
                    align: "center",
                }
            },
            {
                table: {
                    title: '驳回日期',
                    dataIndex: 'rejectDate',
                    width: 60,
                    align: "center",
                    // format: "YYYY/MM/DD",
                }
            },
        ],
        paiMingData2: [
            // ...Array.from(new Array(10)).map((item, index) => {
            //     return {
            //         id: "0" + index,
            //         ranking: 4 + index,
            //         realName: '张' + (4 + index),
            //         averageScore: "100",
            //         foo: 1234567891234,
            //         "计划使用日期": 1234567891234,
            //         "提交日期": 1234567891234,
            //         "配送状态": "已发货",
            //         "是否驳回": "否",
            //     }
            // })
        ],

        topData: {
            one: "0",
            two: "0",
            three: "0",
            four: "0",
            five: "0",
        }

    };
    componentDidMount() {
        console.log('asdasdas')
        window.addEventListener("resize", this.resize, false);

        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
            })
        }

        this.scrollTableData();
    }
    refresh = async () => {
        await this.refreshTop();
        await this.refreshPaiMingRight();
        this.setState({ loading: false })
    };
    resize = () => {
        this._isMounted && this.setState({
            tableHeight: (window.innerHeight - 64 - 44) / 2 / 2 - 19 - 16 - 16 - 16
        })
    };
    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollPaiMingRightBottomTableDataTimer);
    }
    //滚动列表数据
    scrollTableData = () => {
        const timer = 1 * 1500; //动画间隔

        const aniFn = ($dom, dataName, tdHeight = 21) => {
            const dataList = this.state[dataName];
            $dom.animate({
                marginTop: 0 - tdHeight
            }, timer, () => {
                let delEle = dataList.shift();
                dataList.push(delEle);
                if (this._isMounted) {
                    this.table.setTableData(dataList)
                    this.table.refresh()
                }
                // this._isMounted && this.setState({
                //     [dataName]: dataList
                // }, () => {
                //     // $dom.css({
                //     //     marginTop: 0
                //     // })
                // })

            })
        }

        //执行两表格的动画  
        // clearInterval(this.scrollPaiMingTableDataTimer);
        clearInterval(this.scrollPaiMingRightBottomTableDataTimer);

        // this.scrollPaiMingTableDataTimer = setInterval(() => aniFn($(`.${s.paiMingLeft} .ant-table-body table`).eq(0),"paiMingData",45),timer * 2);
        this.scrollPaiMingRightBottomTableDataTimer = setInterval(() => aniFn($(`.${s.paiMingRightBottom} .qnn-table-row`).eq(0), "paiMingData2", 20), timer * 2);

    }
    //上面部分数据 
    refreshTop = async () => {
        return new Promise(async (resolve) => {
            const { myFetch, errMsg } = this.props;
            const { data = [], success, message, code } = await myFetch("getZjLzehRebarSupermarketList", {});
            const { rebarRequirementNumber, cumulativeUsageNumber, cumulativeHasBeenReceivingNumber, remainingStockNumber, cumulativeTotalWaste } = (data[0] || {})
            resolve();

            if (success) {
                //处理数据data
                this.setState({
                    topData: {
                        one: rebarRequirementNumber,
                        two: cumulativeUsageNumber,
                        three: cumulativeHasBeenReceivingNumber,
                        four: remainingStockNumber,
                        five: cumulativeTotalWaste,
                    }
                });
            } else {
                errMsg(message, code);
            }
        })
    };

    //get排名数据 
    refreshPaiMingRight = async () => {
        return new Promise(async (resolve) => {
            const { myFetch, errMsg } = this.props;
            const { data, success, message, code } = await myFetch("getZjLzehPageDataForView", {});

            resolve();
            if (success) {
                //处理数据data
                this._isMounted && this.setState({
                    paiMingData2: data,
                    loading: false,
                });
            } else {
                errMsg(message, code);
            }
        })
    };

    render() {
        const {
            loading,
            paiMing2, paiMingData2,
            tableHeight,
            topData: { one, two, three, four, five }
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: false,
            isShowRowSelect: false,
        }
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        钢筋超市
                    </div>
                    <div className={s.con}>
                        <div className={s.table}>
                            <div className={`${s.paiMingRightTop}`}>
                                <div className={`${s.row}`}>
                                    <div className={`${s.dataItem}`}>
                                        <div className={`${s.name} ${s.red}`}>钢筋总需用量(kg)</div>
                                        <div className={`${s.value}`}>{one}</div>
                                    </div>
                                    <div className={`${s.dataItem}`}>
                                        <div className={`${s.name} ${s.orange}`}>现场累计使用量(kg)</div>
                                        <div className={`${s.value}`}>{two}</div>
                                    </div>
                                </div>
                                <div className={`${s.row}`}>
                                    <div className={`${s.dataItem}`}>
                                        <div className={`${s.name} ${s.green}`}>累计已收料量(kg)</div>
                                        <div className={`${s.value}`}>{three}</div>
                                    </div>
                                    <div className={`${s.dataItem}`}>
                                        <div className={`${s.name} ${s.white}`}>剩余库存量(kg)</div>
                                        <div className={`${s.value}`}>{four}</div>
                                    </div>
                                    <div className={`${s.dataItem}`}>
                                        <div className={`${s.name} ${s.blue}`}>累计废料总量(kg)</div>
                                        <div className={`${s.value}`}>{five}</div>
                                    </div>
                                </div>
                            </div>
                            <div className={`${s.paiMingRightBottom}`}>
                                <QnnTable
                                    formConfig={paiMing2}
                                    data={this.state.paiMingData2}
                                    wrappedComponentRef={(me) => {
                                        this.table = me;
                                    }}
                                    diyTableRow={(reactDOM, props) => {
                                        const rowData = reactDOM.props.record;
                                        const style = {};
                                        if (rowData?.fontRed) {
                                            style.color = "red";
                                        }
                                        return React.cloneElement(reactDOM, {
                                            style: style
                                        })
                                    }}
                                    antd={{
                                        rowKey: "id",
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
export default Readme;
