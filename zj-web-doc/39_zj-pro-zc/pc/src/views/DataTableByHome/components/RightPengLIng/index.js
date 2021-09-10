import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import $ from "jquery"
import moment from "moment"
import Apih5 from "qnn-apih5"
class Readme extends Apih5 {
    state = {
        loading: true,
        data: []
        // data: Array.from(new Array(15)).map((item,index) => {
        //     return {
        //         label: `#${index}${index}${index}台座5月12日20时56分喷淋养护${index}分钟`,
        //     }
        // })
    };
    componentDidMount() {
        this.isDidMount = true;
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                // data: `测试数据`,
            })
        }
    }
    componentWillUnmount() {
        this.isDidMount = false;
        clearTimeout(this.hoverInTimer)
    }

    refresh = async () => {
        const { myFetch, errMsg } = this.props;
        const { data, success, message, code } = await myFetch("getZjProZcSmartSprayListLimit", {});
        if (success) {
            //处理数据data
            this.setState({
                data: data.map(item => {
                    return {
                        ...item,
                        label: `${item.pedestalNo}台座在${moment(item.dateDate).format('YYYY-MM-DD')} ${item.timeStr}喷淋养护${item.curingTime}分钟`
                    }
                }),
                loading: false
            });
        } else {
            errMsg(message, code);
        }
    };


    info = () => { 
        //复制第一个li 
        let li = $(".latestInformationListItem").eq(0).clone();
        //使用animate对li的外边距进行调整从而达到向上滚动的效果
        $(".latestInformationListItem:eq(0)").animate({ marginTop: "-30px" }, 1500, function () {
            //对已经消失的li进行删除
            $(".latestInformationListItem").eq(0).remove();

            //把复制后的li插入到最后
            $(".latestInformationListContainer").append(li);
        })
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
                        智能喷淋信息
                    </div>
                    <div className={s.con}>
                        <div className={`${s.listContainer} latestInformationListContainer`}
                            onClick={() => {
                                this.props.openWindow('DataTableDetailByZjProZcSmartSpray')
                            }}
                            ref={(dom) => {
                                if (dom && data.length > 7) {
                                    clearInterval(this.timing);
                                    this.timing = setInterval(() => {
                                        this.info()
                                    }, 1500);
                                }
                            }}>
                            {
                                data.map(({ label }, index) => {
                                    return <div
                                        onMouseEnter={(e) => {
                                            e.stopPropagation();
                                            clearInterval(this.timing);
                                        }}
                                        onMouseLeave={(e) => {
                                            e.stopPropagation();
                                            if (data.length > 7) {
                                                this.timing = setInterval(this.info, 1500);
                                            }
                                        }}
                                        className={`${s.listItem} latestInformationListItem`} key={index}>
                                        {label}
                                    </div>
                                })
                            }
                        </div>

                    </div>
                </div>
            </Spin>
        );
    }
}
export default Readme;
