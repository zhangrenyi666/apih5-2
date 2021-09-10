import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import ReactEcharts from 'echarts-for-react';
import Apih5 from 'qnn-apih5';
class Readme extends Apih5 {
    state = {
        loading: false,
        data: [],
        // data: Array.from(new Array(16)).map((item,index) => {
        //     return {
        //         label: index + 1 + "队",
        //         data: index + 1 * 10,
        //     }
        // }) 
    };

    //这样的IO操作放在componentDidMount里更合适。
    //组件挂载到DOM后调用，且只会被调用一次
    componentDidMount() {
        this.refresh();
        this.refreshTimer = setInterval(this.refresh,1000 * 60)
    }

    //卸载阶段
    componentWillUnmount() {
        clearInterval(this.refreshTimer)
    }

    //刷新时异步获取数据
    refresh = async () => {
        const { myFetch,errMsg } = this.props;
        const { data,success,message,code } = await myFetch("getTeamPresentNumber",{});
        if (success) { 
            //处理数据data
            this.setState({
                data: data.map(({ groupName,presentNumber }) => {
                    return {
                        label: groupName,
                        data: presentNumber,
                    }
                }),
                loading: false
            });
        } else {
            errMsg(message,code);
        }
    };



    //获取柱状图 的配置
    getOptionOne = (data) => {
        return {
            color: ['rgb(0, 204, 153)'],
            grid: {
                top: "3%",
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value',
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: 'rgba(57, 62, 99, 0.48)'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    color: "white",
                    fontSize: 14,
                },
            },
            yAxis: {
                type: 'category',
                data: data.map(item => item.label),
                axisLabel: {
                    color: "white",
                    fontSize: 14,
                },
            },
            series: [
                {
                    name: '直接访问',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        show: true,
                        position: 'insideRight'
                    },
                    data: data.map(item => item.data),
                },
            ]

        }
    }

    render() {
        const {
            loading,
            data,
        } = this.state;
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        在场作业人数
                    </div>
                    <div className={s.con}
                        onClick={() => {
                            this.props.openWindow('DataTableDetailByNumberOfWorkersOnSite')
                        }}
                    >
                        <ReactEcharts
                            style={{ width: '100%',height: "100%" }}
                            option={this.getOptionOne(data)}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        />
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Readme;
