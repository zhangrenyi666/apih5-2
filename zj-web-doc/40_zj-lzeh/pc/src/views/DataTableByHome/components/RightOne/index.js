import React from "react";
import { Spin,Carousel } from "antd";
import { LeftOutlined,RightOutlined } from "@ant-design/icons"
import s from "./style.less";

import Apih5 from "qnn-apih5"

class Com extends Apih5 {
    state = {
        loading: false,
        data: [
            // {
            //     url: "http://test.apih5.com:9095/web/upload/1EEU84KRR0078B01A8C00000B0BE7AD9.mp4"
            // },
            // {
            //     url: "http://test.apih5.com:9095/web/upload/1EEU84KRR0078B01A8C00000B0BE7AD9.mp4"
            // },
            // {
            //     url: "http://test.apih5.com:9095/web/upload/1EEU84KRR0078B01A8C00000B0BE7AD9.mp4"
            // },
            // {
            //     url: "http://test.apih5.com:9095/web/upload/1EEU84KRR0078B01A8C00000B0BE7AD9.mp4"
            // },
        ],
        curIndex: 0,
    };
    componentDidMount() {

        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
            })
        }

        window.addEventListener("message",this.receiveMessage,false);

    }
    componentWillUnmount() {
        window.removeEventListener("message",this.receiveMessage,false);
    }

    //当前显示的视频数据  可能是 1 或者 2
    curShowVideoNumber = 2;
    //当前加载完成的视频数据
    // curLoadedVideo = 0;

    receiveMessage = (event) => {
        // console.log(event.data.type)
        if (event.data.type === "handleSuccess") {
            let { data = [] } = this.state;
            //当前视频数量是否为 奇数
            let videoNumIsOdd = data.length % 2 === 0 ? false : true;
            //是否是最后一页
            //需要注意的是这块需要使用大于
            let isEndPage = (this.banner.innerSlider.state.currentSlide + 1) * 2 > data.length ? true : false;
            if (videoNumIsOdd) {
                this.curShowVideoNumber = isEndPage ? 1 : this.banner.innerSlider.state.currentSlide;
            }

            clearTimeout(this.timer);
            this.timer = setTimeout(() => {

                //奇数时候最后一页多滚动一次
                if (isEndPage && videoNumIsOdd) {
                    this._isMounted && this.banner.goTo(0);
                } else {
                    this._isMounted && this.banner.next();
                }
            },15000)
        }
    }

    refresh = async () => {
        const { myFetch,errMsg } = this.props;
        const { data,success,message,code } = await myFetch("getZjLzehAccessToken",{});
        if (success) {
            //处理数据data
            this.setState({
                data: data.map(item => {
                    return {
                        url: item.interfaceLink
                    }
                }),
                loading: false
            });
        } else {
            errMsg(message,code);
        }
    };

    onChange = (index) => {
        this.setState({
            curIndex: index
        })
    }

    render() {
        const {
            loading,
            data = [],
            curIndex = 0
        } = this.state; 
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        视频监控
                    </div>
                    <div className={s.con}>
                        <div className={s.bannerContainer}>
                            <Carousel
                                ref={(me) => {
                                    if (me) {
                                        this.banner = me;
                                    }
                                }}
                                afterChange={this.onChange}
                                slidesToShow={2}
                            // slidesToScroll={2}
                            >
                                {
                                    data.map((info,index) => {
                                        return <div key={index} className={s.sliderItem}>
                                            <div className={s.videoContainer}>
                                                <iframe
                                                    style={{ border: 0 }}
                                                    title="iframe"
                                                    // src={(!data[curIndex + 1] && curIndex === data.length - 1) ? data[0].url : ((curIndex === index || curIndex + 1 === index) ? info.url : null)}
                                                    src={((curIndex === index || curIndex + 1 === index) ? info.url : null)}
                                                    src={info.url}
                                                    width="100%"
                                                    height="100%"
                                                    allowFullScreen
                                                >
                                                </iframe>
                                            </div>
                                        </div>
                                    })
                                }
                            </Carousel>
                            <div onClick={(e) => {
                                e.stopPropagation();
                                this.banner.prev()
                            }} className={s.prve}><LeftOutlined /></div>
                            <div onClick={(e) => {
                                e.stopPropagation();
                                this.banner.next()
                            }} className={s.next}><RightOutlined /></div>

                        </div>
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Com;
