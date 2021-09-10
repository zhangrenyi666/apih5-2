import React, { Component } from 'react';
import { Toast } from 'antd-mobile';
import styles from './GuidanceDetail.less';
const zymedia = window.zymedia
class DatumDetail extends Component {
    constructor(props) {
        super(props);
        this.infoId = this.props.match.params.infoId;// 指导资料ID
        this.state = {
            infoTitle: '标题',// 标题
            infoContent: '内容或描述',// 工法内容或描述
            imgAccessoryList: [],// pdf列表
            videoAccessoryList: [],// video列表
            pdfAccessoryList: [],// 附件列表 
        }
    }
    componentDidMount() {
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]
        Toast.loading('Loading...', 0);
        const body = {
            infoId: this.infoId
        }
        myFetch('getInformationDetail', body).then(({ success, data, code }) => {
            if (success) {
                Toast.hide()
                const { infoTitle, infoContent, imgAccessoryList, videoAccessoryList, pdfAccessoryList } = data
                this.setState({ infoTitle, infoContent, imgAccessoryList: imgAccessoryList || [], videoAccessoryList: videoAccessoryList || [], pdfAccessoryList: pdfAccessoryList || [] });
            } else if (code === "3003" || code === "3004") {
                dispatch(logout(pathName))
            }
        })
    }
    componentDidUpdate() {
        zymedia('video', { autoplay: true });
        window.jQuery('video')
            .attr("webkit-playsinline", "true")
            .attr("playsinline", "true")
            .attr("x-webkit-airplay", "true")
            // .attr("x5-video-player-type", "h5")
            .attr("x5-video-player-fullscreen", "true")
    }
    render() {
        var me = this;
        const { imgAccessoryList, videoAccessoryList, pdfAccessoryList } = me.state
        return (
            <div className={styles["lny-DatumDetail"]}>
                <div className={styles["lny-DatumDetail-modelBox"]}>
                    <div className={styles["lny-DatumDetail-modelBox-hd"]}>
                        <div className={styles["lny-DatumDetail-modelBox-hd-title"]}>详情</div>
                    </div>
                    <div className={styles["lny-DatumDetail-modelBox-bd"]}>
                        <div className={styles["lny-DatumDetail-modelBox-bd-title"]}>{me.state.infoTitle}</div>
                        <div className={styles["lny-DatumDetail-modelBox-bd-content"]}>
                            {me.state.infoContent}
                            {imgAccessoryList.map((rowData, rowID) => <MImage key={rowID} data={rowData} />)}
                            {videoAccessoryList.map((rowData, rowID) => <MVideo key={rowID} data={rowData} />)}
                        </div>
                    </div>

                </div>
                {pdfAccessoryList && pdfAccessoryList.length > 0 ?
                    <div className={styles["lny-DatumDetail-modelBox"]}>
                        <div className={styles["lny-DatumDetail-modelBox-hd"]}>
                            <div className={styles["lny-DatumDetail-modelBox-hd-title"]}>附件</div>
                        </div>
                        {pdfAccessoryList.map((rowData, rowID) => <MAccessory key={rowID} data={rowData} />)}
                    </div> : ""
                }
            </div>
        );
    }
}
class MAccessory extends Component {
    render() {
        const { data: { accessoryName: name, accessoryAddress: src, accessorySize: size } } = this.props;
        return (
            <div className={`${styles["accessoryItem"]}`} onClick={() => { document.location = src }}>
                <div className={`${styles["title"]}`}>{name}</div>
                <div className={styles["size"]}>{size}M</div>
                <a href={src} className={styles["btn"]} >{" "}</a>
            </div>
        )
    }
}
class MImage extends Component {
    render() {
        const { data: { accessoryName: name, accessoryAddress: src } } = this.props;
        return (
            <div style={{ marginTop: ".1rem" }}>
                <img style={{ display: "block", width: "100%" }} alt={name} src={src} />
            </div>
        )
    }
}
class MVideo extends Component {
    render() {
        const { data: { accessoryName: name, accessoryAddress: src, imageAddress: poster, accessorySize: size, accessoryTime: time } } = this.props;
        return (
            <div style={{ marginTop: ".2rem" }}>
                <div className={`${styles["videoTitle"]}`}>
                    {name}
                </div>
                <div className={`zy_media`} >
                    <video style={{ objectFit: "fill" }} poster={poster}>
                        <source src={src} type="video/mp4" />
                        {"您的浏览器不支持HTML5视频"}
                    </video>
                </div>
                <div className={`${styles["videoBottom"]}`}>
                    <div className={`${styles["size"]}`}>大小：{size}mb</div>
                    <div className={`${styles["time"]}`}>播放时长：{time}分</div>
                </div>
            </div>
        )
    }
}
export default DatumDetail;
