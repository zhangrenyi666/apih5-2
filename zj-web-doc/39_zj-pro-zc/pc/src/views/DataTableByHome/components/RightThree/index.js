import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import QnnTable from "qnn-table"
import $ from 'jquery'
import Apih5 from 'qnn-apih5'
const QSIon = {
    0: require('../../../../imgs/1.png'),
    1: require('../../../../imgs/2.png'),
    2: require('../../../../imgs/3.png'),
    3: require('../../../../imgs/4.png'),
    4: require('../../../../imgs/5.png'),
    5: require('../../../../imgs/6.png'),
}

class Readme extends Apih5 {
    state = {
        loading: false,

        //表格配置
        leftTableConfig: [

            {
                table: {
                    title: '',
                    dataIndex: 'img',
                    width: 20,
                    align: "center",
                    render: (rowData) => {
                        return <img style={{ width: "18px",height: "18px" }} src={rowData} alt="icon" />
                    }
                }
            },
            {
                table: {
                    title: '排名',
                    dataIndex: 'ranking',
                    width: 50,
                    align: "center",
                    render: (rowData) => {
                        return rowData
                    }
                }
            },
            // {
            //     table: {
            //         title: '姓名',
            //         dataIndex: 'realName',
            //         width: 50,
            //         align: "center",
            //     }
            // },
            {
                table: {
                    title: '所属班组',
                    dataIndex: 'teamId',
                    width: 80,
                    align: "center",
                    type: "select",
                    fetchConfig: {
                        apiName: "getBaseCodeSelect",
                        otherParams: {
                            itemId: "suoShuBanZu"
                        }
                    },
                    optionConfig: {
                        label: "itemName",
                        value: "itemId",
                    }
                }
            },
            {
                table: {
                    title: '平均安全积分',
                    dataIndex: 'averageScore',
                    width: 50,
                    align: "center"
                }
            },
        ],
        leftTableData: [],
        // leftTableData: Array.from(new Array(6)).map((item,index) => {
        //     return {
        //         id: "0",
        //         ranking: 1 + index,
        //         realName: '张' + (4 + index),
        //         averageScore: "100",
        //         imgUrl: require('../../../../imgs/logo.png'),
        //     }
        // }),
        tableHeight: (window.innerHeight - 64 - 44) * 0.17 - 22

    };
    componentDidMount() {
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                // data: `测试数据`,
            })
        }
        //正常 需要 刷新 后在调用 
        // this.scrollTableData();


        this.resize();
        window.addEventListener("resize",this.resize,false);
    }

    //滚动列表数据
    scrollTableData = () => {
        const timer = 1 * 1500; //动画间隔

        const aniFn = ($dom,dataName,tdHeight = 21) => {
            const dataList = this.state[dataName];
            $dom.animate({
                marginTop: 0 - tdHeight
            },timer,() => {

                let delEle = dataList.shift();
                dataList.push(delEle);

                this._isMounted && this.setState({
                    [dataName]: dataList
                },() => {
                    $dom.css({
                        marginTop: 0
                    })
                })

            })
        }

        //执行两表格的动画 
        clearInterval(this.scrollLeftTableDataTimer);
        this.scrollLeftTableDataTimer = setInterval(() => aniFn($(`.${s.table} .ant-table-body table`).eq(0),"leftTableData"),timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize",this.resize);
        clearInterval(this.scrollLeftTableDataTimer);
    }
    resize = () => {
        this.setState({
            tableHeight: (window.innerHeight - 64 - 44) * 0.26 - 22
        })
    };

    refresh = async () => {
        await this.refreshPaiMingLeft();
        this._isMounted && this.setState({ loading: false })
    };


    //get排名数据 
    refreshPaiMingLeft = async () => {
        return new Promise(async (resolve) => {
            const { myFetch,errMsg } = this.props; 
            const { data,success,message,code } = await myFetch("getZjProZcSafeScoreListByTeamId",{});
            resolve();
            if (success) {
                //处理数据data
                this._isMounted && this.setState({
                    leftTableData: data.map((item,index) => {
                        return {
                            ...item,
                            img: QSIon[index],
                            ranking: index + 1
                        }
                    }),
                });
            } else {
                errMsg(message,code);
            }
        })
    };
    render() {
        const {
            loading,
            leftTableData,leftTableConfig,tableHeight
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
                        班组平均安全积分排名
                    </div>
                    <div className={s.con}>
                        <div className={s.tableContainer}>
                            <div className={s.table}>
                                <QnnTable
                                    formConfig={leftTableConfig}
                                    data={leftTableData}
                                    antd={{
                                        rowKey: "safeScoreId",
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
