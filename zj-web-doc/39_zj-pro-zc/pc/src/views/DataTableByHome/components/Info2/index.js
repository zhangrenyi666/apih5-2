import React from 'react'
import { Spin, Modal, Carousel, Icon } from 'antd';
import s from './style.less'
import moment from 'moment'
import Apih5 from 'qnn-apih5'
import {
    LeftOutlined,
    RightOutlined
} from '@ant-design/icons';

const aniT = 400;
const scrollTime = 10000 - aniT;//切换时间
class Info extends Apih5 {
    state = {
        loading: true,
        stop: false,//动画是否停止
        visible: false,
        detailData: {},
        data: [],
        // data: Array.from(new Array(20)).map((item, index) => {
        //     return {
        //         precess: index + '分部→桥梁工程→桥梁工程→桥梁工程→桥梁工程→桥梁工程→桥梁工程→桥梁工程→桥梁工程→桥梁工程',
        //         time: 123564797789,
        //         desc: '王永安审核完成',
        //         imgArr: [],
        //         classify: '隐蔽工程', // 隐患排查 质量检查   隐蔽工程
        //         img: 'https://images.pexels.com/photos/3356548/pexels-photo-3356548.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500',
        //         imgArr: [
        //             {
        //                 url: "https://images.pexels.com/photos/3356548/pexels-photo-3356548.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        //             },
        //             {
        //                 url: "https://images.pexels.com/photos/3356548/pexels-photo-3356548.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        //             }
        //         ]
        //     }
        // })
    }
    componentDidMount() {
        this.resize()
        window.addEventListener('resize', this.resize, false)
        this.refresh();
        // clearInterval(window.infoAutoPlayTime)
        // window.infoAutoPlayTime = setInterval(this.autoplay, scrollTime)
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.resize)
    }
    resize = () => {
        document.getElementById('con').style.height = (window.innerHeight - 64 - 44 - 12 - 28 - 24) * 0.22 + 'px';
    }
    showModal = () => {
        this.setState({
            visible: true,
        });
    }
    handleCancel = (e) => {
        this.setState({
            visible: false,
            stop: false
        });
    }

    refresh = () => {
        const { myFetch } = this.props;

        myFetch('getZjProZcQsList', {
            limit: 9999
        }).then(async ({ data, success, message, code }) => {
            if (success) {
                //获取问题性质下拉
                const problemNatureRes = await myFetch('getBaseCodeSelect', {
                    itemId: "wenTiXingZhi"
                });
                if (!problemNatureRes.success) {
                    this.apih5.errMsg(problemNatureRes.message, problemNatureRes.code);
                    return;
                }
                const problemNatureList = problemNatureRes.data;

                let newData = [...data];
                if (problemNatureList) {
                    newData = newData.map(item => {
                        return {
                            finishFlag: item.finishFlag,
                            finishName: item.finishFlag === "1" ? "已解决" : "未解决",
                            precess: item.problemDesc,
                            time: item.finishFlag === "1" ? item.findTime : item.reformFinishTime,
                            desc: item.problemDesc,
                            classify: problemNatureList.filter(nItem => nItem.itemId === item.problemNature)?.[0]?.itemName, // 隐患排查 质量检查   隐蔽工程
                            img: item.beforeFileList?.[0]?.url,
                            imgArr: [
                                {
                                    url: item.beforeFileList?.[0]?.url,
                                },
                                {
                                    url: item.afterFileList?.[0]?.url,
                                }
                            ]
                        }
                    })
                }

                //处理数据data
                this.setState({
                    data: newData,
                    loading: false
                }, () => {
                    clearInterval(window.infoAutoPlayTime)
                    window.infoAutoPlayTime = setInterval(this.autoplay, scrollTime)
                })
            } else {
                this.apih5.errMsg(message, code);
            }
        })

    }

    autoplay = () => {
        const { stop } = this.state;
        if (!stop) {
            let _d = this.state.data;
            let data = [..._d];
            let _pop = data.shift();//删除第一个 
            data.push(_pop);
            this.setState({
                isAni: true
            }, () => {
                setTimeout(() => {
                    this.setState({
                        data,
                        isAni: false
                    })
                }, aniT)//根据css动画时间控制 
            })
        }

    }
    goMore = () => {
        // const { dispatch } = this.props;
        // dispatch(push(``))
        console.log('更多', this.props)
    }

    setStop = (stop) => {
        const { visible } = this.state;
        this.setState({
            stop: visible ? true : stop
        })
    }

    listClick = (data, index) => {
        this.setState({
            detailData: data,
            stop: true,
            visible: true
        })
    }

    bannerrl = (params) => {
        if (params === 'left') {
            this.refs.Carousel.prev()
        } else {
            this.refs.Carousel.next()
        }
    }

    render() {
        const { loading, data = [], isAni, detailData: { imgArr = [], precess, time, desc, finishName } } = this.state;

        return (
            <Spin spinning={loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        实时信息
                    </div>
                    <div className={s.con} id="con">
                        {
                            data.map((item, index) => {
                                const { time, finishName, precess, img, classify } = item;
                                return <div key={index} onClick={this.listClick.bind(this, item, index)} onMouseEnter={this.setStop.bind(this, true)} onMouseLeave={this.setStop.bind(this, false)} className={`${s.precessList} ${index === 0 && isAni ? s.precessListAni : ''}`}>
                                    <div className={s.left}>
                                        <div><span style={{ color: 'orange' }}>【{finishName}】【{classify}】</span>{precess}</div>
                                        <div className={s.timeAndDesc}>
                                            <div>
                                                {time ? moment(time).format('YYYY-MM-DD HH:mm:ss') : '--'}
                                            </div>
                                        </div>
                                    </div>
                                    {
                                        img ? <div className={s.right}>
                                            <img src={img} alt="" />
                                        </div> : null
                                    }
                                </div>
                            })
                        }
                    </div>
                    <div>
                        <Modal
                            title="详情"
                            visible={this.state.visible}
                            onCancel={this.handleCancel}
                            footer={[]}
                            destroyOnClose
                        >
                            <p>{precess}</p>
                            <div>
                                <span>
                                    【{finishName}】{time ? moment(time).format('YYYY-MM-DD HH:mm:ss') : '--'}
                                </span>
                                <span style={{ marginLeft: '18px' }}>
                                    {desc}
                                </span>
                            </div>
                            <div className={s.bannerContainer}>
                                {
                                    imgArr.length ? <div>
                                        <Carousel ref="Carousel" autoplay={true}>
                                            {
                                                imgArr.map((item = {}, index) => {
                                                    return <div key={index} style={{ width: '300px', border: '1px solid red' }}>
                                                        <a target="_block" href={item.url} className={s.bannera}>
                                                            <img className={s.bannerImg} src={item.url} alt="" />
                                                        </a>
                                                    </div>
                                                })
                                            }
                                        </Carousel>
                                        <div onClick={this.bannerrl.bind(this, 'left')} className={s.arrowLeft}>
                                            <LeftOutlined />
                                        </div>
                                        <div onClick={this.bannerrl.bind(this, 'right')} className={s.arrowRight}>
                                            <RightOutlined />
                                        </div>
                                    </div> : null
                                }
                            </div>

                        </Modal>
                    </div>
                </div>
            </Spin>
        )
    }
}
export default Info