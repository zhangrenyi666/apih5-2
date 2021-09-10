import React from "react";
import style from "./style.less";
import Apih5 from 'qnn-apih5'
import $ from 'jquery'

// import Info from '../Info'
class Readme extends Apih5 {

    state = {
        loading: true,
        blockOneData: [
            {
                name: "吊重",
                value: 5.75,
                unit: "吨",
                color: "#37adcb"
            },
            {
                name: "高度",
                value: 4.75,
                unit: "米",
                color: "#0f6fc6"
            },
            {
                name: "风速",
                value: 4.75,
                unit: "km/h",
                color: "#009dd9"
            },
            {
                name: "大车行程",
                value: "-5m/±8m",
                unit: "",
                color: "#0bd0d9"
            },
            {
                name: "水平倾斜度",
                value: "12°",
                unit: "",
                color: "#10cf9b"
            },
            {
                name: "大车行程",
                value: "-5m/±8m",
                unit: "",
                color: "#7cca62"
            },
        ],
        blockTwoData: [
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            }
        ],
        blockThreeData: [
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },{
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },{
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },{
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            },
            {
                text: "7月30日32分行程为-10m，超出最大行程",
            }
        ],
    }

    componentDidMount() {
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
        const { myFetch } = this.props;
        const { data,success,message,code } = await myFetch("getZjProZcQsListForBigScreen",{});
        if (success) {
            //处理数据data
            this._isMounted && this.setState({
                data: data.map(item => {
                    return {
                        ...item,
                        label: item.problemNature,
                        data1: item.finish,
                        data2: item.unFinish,
                        count: item.finish + item.unFinish,
                    }
                }),
                loading: false
            });
        } else {
            this.apih5.errMsg(message,code);
        }
    };


    //滚动列表数据
    scrollTableData = () => {
         
        const timer = 1 * 2000; //动画间隔

        const aniFn = ($dom, dataName, tdHeight = 17) => {
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
        clearInterval(this.scrollblockTwoDataTimer);
        clearInterval(this.scrollblockThreeDataTimer);
        this.scrollblockTwoDataTimer = setInterval(() => aniFn($(`.${style.blockTwoScroll}`).eq(0),"blockTwoData"),timer * 2);
        this.scrollblockThreeDataTimer = setInterval(() => aniFn($(`.${style.blockThreeScroll}`).eq(0),"blockThreeData"),timer * 2);

    }

    render() {
        const { blockOneData = [],blockTwoData = [],blockThreeData = [] } = this.state;
        return (
            <div className={style.info}>
                {/* <div className={style.title}>
                    架桥机监控
                    </div> */}
                <div className={style.con}>
                    {/* <Info {...this.props} />  */}
                    <div className={`${style.block} ${style.oneBlock}`}>
                        <div className={style.title}>架桥机监控</div>
                        <div className={style.content}>
                            {
                                blockOneData.map((item,index) => {
                                    return <div key={index} className={style.infoItem}>
                                        <div className={style.quan} style={{ borderColor: item.color }}></div>
                                        <div className={style.value}>{item.value}{item.unit}</div>
                                        <div className={style.name}>{item.name}</div>
                                    </div>
                                })
                            }
                        </div>
                    </div>
                    <div className={style.block}>
                        <div className={style.title}>大车行程预警信息</div>
                        <div className={`${style.content} ${style.contentList}`}>
                            <div className={`${style.blockTwoScroll}`}>
                                {
                                    blockTwoData.map(({ text },index) => {
                                        return <div key={index}>{text}</div>
                                    })
                                }
                            </div>

                        </div>
                    </div>
                    <div className={style.block}>
                        <div className={style.title}>小车行程预警信息</div>
                        <div className={`${style.content} ${style.contentList}`}>
                            <div className={`${style.blockThreeScroll}`}>
                                {
                                    blockThreeData.map(({ text },index) => {
                                        return <div key={index}>{text}</div>
                                    })
                                }
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default Readme;
