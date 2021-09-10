import React, { Component } from 'react';
import { List, Flex, Badge } from 'antd-mobile';
import Voice from '../common/voice'
import Image from '../common/image'
import styles from './floorItem.less';
import moment from 'moment';
const fromNow = (preDate) => {
    const curDate = new Date().getTime();
    const difDate = Math.max(curDate - preDate, 0);
    let textDate = ""
    let count = 0;
    if (difDate < 60 * 1000) {//小于一分钟
        textDate = "刚刚"
    } else if (difDate < 60 * 60 * 1000) {//小于一小时
        count = parseInt(difDate / (60 * 1000), 10)
        textDate = count + "分钟前"
    } else if (difDate < 24 * 60 * 60 * 1000) {//小于一天
        count = parseInt(difDate / (60 * 60 * 1000), 10)
        textDate = count + "小时前"
    } else if (difDate < 7 * 24 * 60 * 60 * 1000) {//小于一周
        count = parseInt(difDate / (24 * 60 * 60 * 1000), 10)
        textDate = count + "天前"
    } else {
        if (new Date(preDate).getFullYear() !== new Date(curDate).getFullYear) {
            textDate = moment(preDate).format("YYYY-MM-DD")
        } else {
            textDate = moment(preDate).format("MM-DD")
        }
    }
    return textDate
}


class FloorItem extends Component {
    render() {
        const {
            index,
            options: {
                floorHost,
                playFn,
                keyNames: {
                    userId,
                    userName,
                    createTime,
                    contentText,
                    contentimageInfoList,
                    contentVoiceList,
                    otherAccessoryList
                }
            },
            data,
            footerRender,
        } = this.props
        return (
            <List.Item
                className={styles["lny-floorItem"]}
                thumb={
                    <div className={styles["lny-floorItem-thumb"]}>
                        <img className={styles["img"]} alt="头像" src={require(`../../images/zjlogo.png`)} />
                        <div className={styles["text"]}>{`${index + 1}楼`}</div>
                    </div>
                }
            >
                <Flex>
                    <Flex.Item className={styles["lny-floorItem-header"]}>
                        <div className={styles["title"]}>
                            {data[userName] || "匿名"}
                            {index !== 0 && floorHost && data[userId] && floorHost === data[userId] ?
                                <Badge text={"问询发起人"}
                                    style={{
                                        marginLeft: 12,
                                        padding: '0 .03rem',
                                        backgroundColor: 'transparent',
                                        borderRadius: '.02rem',
                                        color: '#0099dd',
                                        border: '.01rem solid #0099dd',
                                    }}
                                />
                                : ""
                            }
                        </div>
                        <div className={styles["time"]}>{fromNow(data[createTime])} </div >
                    </Flex.Item>
                </Flex>
                <div className={styles["lny-floorItem-body"]}>
                    {data[contentText] ? <div style={{ marginBottom: ".3rem", whiteSpace: "initial" }}>{data[contentText]}</div > : ""}
                    <Voice voiceInfo={data[contentVoiceList][0]} playFn={playFn} />
                    <Image {...this.props} imageInfoList={data[contentimageInfoList]} />
                    {data[otherAccessoryList] && data[otherAccessoryList].length ?
                        data[otherAccessoryList].map((item) => {
                            return (
                                <div key={item.url} style={{ overflow: "hidden", whiteSpace: "nowrap", textOverflow: "ellipsis" }}><a href={item.url}>{item.name || item.url}</a></div>
                            )
                        })
                        : null}
                </div>
                {typeof footerRender === "function" ? footerRender() : ""}
            </List.Item>
        )
    }
}

export default FloorItem;