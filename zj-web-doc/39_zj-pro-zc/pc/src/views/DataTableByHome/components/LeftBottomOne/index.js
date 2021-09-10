import React from "react";
import { Spin, Carousel, Modal } from "antd";
import { LeftOutlined, RightOutlined } from "@ant-design/icons"
import s from "./style.less";
import Apih5 from "qnn-apih5"
class Com extends Apih5 {
    state = {
        loading: false,
        curIndex: 0,
        data: [
            // {
            //     url: require('./1.mp4'),

            // },
            // {

            //     url: require('./2.mp4'),
            // }
        ],

        videoUrl: false,
        visibleVideo: false
    };
    componentDidMount() {
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
            })
        }
    }
    handleCancelVideo = () => {
        this.setState({ visibleVideo: false, loadingVideo: false });
    }
    refresh = async () => {
        const { myFetch, errMsg } = this.props;
        const { data, success, message, code } = await myFetch("getZjProZcVideoList", {});
        if (success) {
            //处理数据data
            this.setState({
                data: data.map(item => {
                    return {
                        url: item.fileList[0].url
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

    //进入全屏
    FullScreen = (ele) => {
        if (ele.requestFullscreen) {
            ele.requestFullscreen();
        } else if (ele.mozRequestFullScreen) {
            ele.mozRequestFullScreen();
        } else if (ele.webkitRequestFullScreen) {
            ele.webkitRequestFullScreen();
        }
    }
    render() {
        const {
            loading,
            data,
            visibleVideo,
            videoUrl,
            curIndex
        } = this.state;
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        视频展示
                    </div>
                    <div className={s.con}>
                        <div className={s.bannerContainer}>
                            <Carousel
                                autoplay={false}
                                // autoplaySpeed={7000}
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
                                                {
                                                    curIndex === index ? <video
                                                        onClick={() => {
                                                            this.setState({
                                                                videoUrl: info.url,
                                                                visibleVideo: true,
                                                            })
                                                        }}
                                                        src={info.url}
                                                        // loop={true}
                                                        autoPlay={true}
                                                        // controls
                                                        onEnded={()=>{
                                                            console.log('onEnded')
                                                            this.banner.next()
                                                        }}
                                                        ref={(me) => {
                                                            if (me) {
                                                                setTimeout(() => {
                                                                    // 设置静音
                                                                    // 设置进度为0
                                                                    me.currentTime = 1;
                                                                    me.volume = 0;
                                                                    // me && me.play();
                                                                }, 100) 
                                                            }
                                                        }}> </video> : null
                                                }

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

                    <Modal
                        width='800px'
                        style={{ top: '0' }}
                        title="视频"
                        visible={visibleVideo}
                        footer={null}
                        onCancel={this.handleCancelVideo}
                        bodyStyle={{ width: '800px', textAlign: 'center' }}
                        centered={true}
                        destroyOnClose={this.handleCancelVideo}
                        wrapClassName={'modals'}
                    >
                        <video
                            loop={true}
                            autoPlay={true}
                            src={videoUrl} controls="controls" width="700" ></video>
                    </Modal>
                </div>
            </Spin>
        );
    }
}
export default Com;
