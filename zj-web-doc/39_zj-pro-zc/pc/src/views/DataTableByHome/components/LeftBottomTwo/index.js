import React from "react";
import { Spin, Carousel } from "antd";
import { LeftOutlined, RightOutlined } from "@ant-design/icons"
import s from "./style.less";

// import { ImgPreview } from "qnn-form"
import Apih5 from "qnn-apih5"

class Com extends Apih5 {
    state = {
        loading: false,
        data: [
            // {
            //     url: "https://open.ys7.com/ezopen/h5/iframe?url=ezopen://open.ys7.com/E46634186/1.live&autoplay=1&accessToken=at.4pes0y2q4xq33q5i8sop23o60dfbth08-8s1lgnbhv4-01vk1bp-qug1qn7w9"
            // }, 
        ],
        curIndex: 0,
    };
    componentDidMount() {
        this.refresh();
        const _this = this;
        window.addEventListener("message", receiveMessage, false);
        _this.timer = null;
        function receiveMessage(event) {
            if (event.data.type === "handleSuccess") {
                clearTimeout(_this.timer);
                _this.timer = setTimeout(() => {
                    _this._isMounted && _this.banner.next();
                }, 15000)
            }
        }
    }
    componentWillUnmount() {
        this.isDidMount = false;
    }
    refresh = async () => {
        const { myFetch, errMsg } = this.props;
        const { data, success, message, code } = await myFetch("getZjProZcAccessToken", {});
        if (success) {
            //处理数据data
            this._isMounted && this.setState({
                data: data.map(item => {
                    return {
                        url: item.interfaceLink
                    }
                }),
                loading: false
            });
        } else {
            errMsg(message, code);
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
                            >
                                {
                                    data.map((info, index) => {
                                        return <div key={index} className={s.sliderItem}>
                                            <div className={s.videoContainer}>
                                                <iframe
                                                    style={{ border: 0 }}
                                                    title="iframe"
                                                    src={curIndex === index ? info.url : null}
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
