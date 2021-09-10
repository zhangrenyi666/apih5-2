import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import Apih5 from "qnn-apih5";
class Readme extends Apih5 {
    state = {
        loading: true,
        //质量安全
        data: {
            overNum: 0,
            countNum: 0,
            //需要用到里面的icon
            items: [
                {
                    // label: "安全管理",
                    // overNum: 32,
                    // countNum: 92,
                    icon: require('../.././../../imgs/0.jpg'),
                },
                {
                    icon: require('../.././../../imgs/1.jpg'),
                },
                {
                    icon: require('../.././../../imgs/2.jpg'),
                },
                {
                    icon: require('../.././../../imgs/3.jpg'),
                },
                {
                    icon: require('../.././../../imgs/4.jpg'),
                },
                {
                    icon: require('../.././../../imgs/5.jpg'),
                },
                {
                    icon: require('../.././../../imgs/6.jpg'),
                },
                {
                    icon: require('../.././../../imgs/7.jpg'),
                },
                {
                    icon: require('../.././../../imgs/8.jpg'),
                },
                {
                    icon: require('../.././../../imgs/9.jpg'),
                },
                {
                    icon: require('../.././../../imgs/10.jpg'),
                },
                {
                    icon: require('../.././../../imgs/11.jpg'),
                },
                {
                    icon: require('../.././../../imgs/11.jpg'),
                }
            ]
        },
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
    }
    refresh = async () => {
        const { data: { items } } = this.state;
        const { myFetch,errMsg } = this.props;
        const { data,success,message,code } = await myFetch("getZjLzehSafetyInspectionManagementBySumDangerType",{});
 
        if (success) {
            let overNum = 0;
            let countNum = 0;
            let newData = data.map((item,index) => {
                overNum = overNum + item.count;
                countNum = countNum + item.total;
                return {
                    ...item,
                    label: item.content,
                    overNum: item.count,
                    countNum: item.total,
                    icon: items[index]?.icon,
                }
            });

            //处理数据data
            this.setState({
                data: {
                    overNum: overNum,
                    countNum: countNum,
                    items: newData
                },
                loading: false
            });
        } else {
            errMsg(message,code);
        }
    };


    render() {
        const {
            loading,
            data: { items = [],overNum,countNum },
        } = this.state;
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        <span>安全检查管理</span>
                        <span className={s.right}>完成数/总数：{overNum}/{countNum}</span>
                    </div>
                    <div className={s.con}>
                        {
                            items.map(({ icon,label,countNum,overNum },index) => {
                                return <div key={index} className={s.dataItem}>
                                    <div className={s.left}>
                                        <img src={icon} alt="icon" />
                                    </div>
                                    <div className={s.right}>
                                        <div>{label}</div>
                                        <div className={s.value}>
                                            <span>{overNum}</span>/
                                            <span>{countNum}</span>
                                        </div>

                                    </div>
                                </div>
                            })
                        }
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Readme;
