import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import Apih5 from "qnn-apih5";
import QnnTable from "qnn-table";
import $ from "jquery"

const QSIon = {
    0: require('../../../../imgs/one.png'),
    1: require('../../../../imgs/two.png'),
    2: require('../../../../imgs/three.png'),
}

class Readme extends Apih5 {
    state = {
        loading: true,
        data: Array.from(new Array(10)).map((item, index) => {
            return {
                label: index + 1 + "队",
                data: index + 1 * 10,
            }
        }),
        tableHeight: (window.innerHeight - 64 - 44) * 0.38 * 0.4 - 22 - 22,

        //右边排名配置 + 数据
        paiMing2: [
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
            {
                table: {
                    title: '姓名',
                    dataIndex: 'realName',
                    width: 50,
                    align: "center",
                }
            },
            {
                table: {
                    title: '所属班组',
                    dataIndex: 'teamId',
                    width: 50,
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
                    title: '安全积分',
                    dataIndex: 'nowScore',
                    width: 50,
                    align: "center"
                }
            },
        ],
        paiMingData2: [],
        // paiMingData2: Array.from(new Array(10)).map((item,index) => {
        //     return {
        //         id: "0",
        //         ranking: 4 + index,
        //         realName: '张' + (4 + index),
        //         averageScore: "100",
        //         imgUrl: require('../../../../imgs/logo.png'),
        //     }
        // }),

        //右边排名数据的前三名 因为数据一直会变 所以需要单独存
        paiMingData2QS: [
            // {
            //     id: "0",
            //     ranking: "1",
            //     realName: "张三",
            //     name: '泡面小组1',
            //     averageScore: "100",
            //     imgUrl: require('../../../../imgs/person.jpg'),
            //     icon: require('../../../../imgs/one.jpg'),
            // },
            // {
            //     id: "1",
            //     ranking: "2",
            //     realName: "张三",
            //     name: '泡面小组2',
            //     averageScore: "80",
            //     imgUrl: require('../../../../imgs/person.jpg'),
            //     icon: require('../../../../imgs/two.jpg'),
            // },
            // {
            //     id: "2",
            //     ranking: "3",
            //     realName: "张三",
            //     name: '泡面小组3',
            //     averageScore: "70",
            //     imgUrl: require('../../../../imgs/person.jpg'),
            //     icon: require('../../../../imgs/three.jpg'),
            // },
        ],


    };
    componentDidMount() {
        this.resize();
        window.addEventListener("resize", this.resize, false);

        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
            })
        }
    }
    refresh = async () => {
        await this.refreshPaiMingRight();
        // this.setState({ loading: false })
    };
    resize = () => {
        this.setState({
            tableHeight: (window.innerHeight - 64 - 44) * 0.38 * 0.4 - 22 - 22,
        })
    };
    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        // clearInterval(this.scrollPaiMingTableDataTimer);
        clearInterval(this.scrollPaiMingRightBottomTableDataTimer);
    }
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
        // clearInterval(this.scrollPaiMingTableDataTimer);
        clearInterval(this.scrollPaiMingRightBottomTableDataTimer);

        // this.scrollPaiMingTableDataTimer = setInterval(() => aniFn($(`.${s.paiMingLeft} .ant-table-body table`).eq(0),"paiMingData",45),timer * 2);
        this.scrollPaiMingRightBottomTableDataTimer = setInterval(() => aniFn($(`.${s.paiMingRightBottom} .ant-table-body table`).eq(0), "paiMingData2"), timer * 2);

    }
    //get排名数据 
    refreshPaiMingRight = async () => {
        return new Promise(async (resolve) => {
            const { myFetch, errMsg } = this.props;
            const { data, success, message, code } = await myFetch("getZjProZcSafeScoreListByNowScore", {
                limit: 999
            });
            //需要请求班组数据进行配置班组名称
            const banZhuData = await myFetch("getBaseCodeSelect", { itemId: "suoShuBanZu" });
            const banZhuDataOBj = {};
            if (banZhuData.success) {
                banZhuData.data.forEach(element => {
                    banZhuDataOBj[element.itemId] = element.itemName
                });
            }

            resolve();
            if (success) {
                //处理数据data
                this._isMounted && this.setState({
                    paiMingData2: data.filter((_, i) => i >= 3).map((item, i) => {
                        return {
                            ...item,
                            ranking: i + 4,
                            realName: item.personName,
                            // icon: QSIon[i]
                        }
                    }),
                    paiMingData2QS: data.filter((_, i) => i < 3).map((item, i) => {
                        return {
                            ...item,
                            imgUrl: item.headUrl,
                            ranking: i + 1,
                            realName: item.personName,
                            name: banZhuDataOBj[item.teamId],
                            averageScore: item.averageScore,
                            icon: QSIon[i]
                        }
                    }),
                    loading: false,
                    banZhuDataOBj
                }, () => {
                    this.scrollTableData();
                });
            } else {
                errMsg(message, code);
            }
        })
    };

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
            paiMing2, paiMingData2,
            paiMingData2QS,
            tableHeight,
            banZhuDataOBj = {}
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
                        人员安全积分排名
                    </div>
                    <div className={s.con} onClick={() => {
                        this.props.openWindow('DataTableDetailByZjProZcSafeScoreByPerson')
                    }}>
                        <div className={s.table}>
                            <div className={`${s.paiMingRightTop}`}>
                                {
                                    // 前三
                                    paiMingData2QS?.map?.((_item, index, arr) => {
                                        const item = index === 0 ? arr[1] : (index === 1 ? arr[0] : arr[2]);

                                        return <div key={index} className={s.item}>
                                            <div className={s.icon}>
                                                <img src={item.icon} alt="icon" />
                                            </div>
                                            <div className={s.itemImg} >
                                                <img src={item.imgUrl} alt="icon" />
                                            </div>
                                            <div>
                                                {/* {this.getPMText(item.ranking)} */}
                                                {item.realName} | {item.nowScore}
                                            </div>
                                            <div>
                                                {banZhuDataOBj[item.teamId]}
                                            </div>
                                        </div>
                                    })
                                }
                            </div>
                            <div className={`${s.paiMingRightBottom}`}>
                                <QnnTable
                                    formConfig={paiMing2}
                                    data={paiMingData2}
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
