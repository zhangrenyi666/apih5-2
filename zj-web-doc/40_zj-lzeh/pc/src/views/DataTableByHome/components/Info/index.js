import React,{ Component } from 'react'
import { Spin,message as Msg,Modal,Carousel,Icon, notification } from 'antd';
import s from './style.less'
import moment from 'moment'
// import $ from 'jquery'
const aniT = 400;
const scrollTime = 2500 - aniT;//切换时间
class Info extends Component {
    state = {
        loading: false,
        stop: false,//动画是否停止
        visible: false,
        detailData: {},
        data: [],
        // data: Array.from(new Array(20)).map((item,index) => {
        //     return {
        //         precess: index + '.',
        //         time: 123564797789,
        //         desc: '.',
        //         imgArr: [],
        //         classify: '.', // 隐患排查 质量检查   隐蔽工程
        //         img: 'https://images.pexels.com/photos/3356548/pexels-photo-3356548.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500'
        //     }
        // })
    }
    componentDidMount() {
        this.resize()
        window.addEventListener('resize',this.resize,false)
        this.refresh();
        // this.connectScoket(); 
    }
    componentWillUnmount() {
        window.removeEventListener('resize',this.resize)
        // this.disconnect();
    }
    // connectScoket = () => {
    //     //建立scoket连接  /customendpoint
    //     var socket = new window.SockJS(window.configs.domain + 'customendpoint');//连接SockJs的endpoint-/customendpoint
    //     this.stompClient = window.Stomp.over(socket)//使用STOMP子协议的WebSocket客户端
    //     const _this = this;
    //     this.stompClient.connect({}, function (frame) {
    //         _this.setState({
    //             connectScoket: true
    //         })
    //         if (_this.stompClient) {
    //             _this.stompClient.subscribe("/topic/getResponse", function (response) {
    //                 const body = JSON.parse(response.body);
    //                 _this.openNotificationWithIcon({
    //                     className: s.message,
    //                     message: '工序审核完成',
    //                     description: body.responseMessage,
    //                 })
    //             });
    //         }
    //     }, function () {
    //         console.log('连接失败')
    //     });
    // }

    // openNotificationWithIcon = (messageObj = {}, type = 'success') => {
    //     const { message, description } = messageObj;
    //     notification[type]({
    //         message,
    //         description,
    //     });
    // };
    // disconnect = () => { 
    //     const { connectScoket } = this.state;
    //     if (connectScoket) {
    //         this.stompClient.disconnect();
    //     } 
    // }

    resize = () => {
        document.getElementById('con').style.height = (window.innerHeight - 64 - 44 - 32 - 28 - 24) * 0.60 + 'px';
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

        myFetch('getZjLzehDataCenterDynamicInfo',{}).then(({ data,success,message }) => {
            console.log(data)
            if (success) {

                const { hiddenDangerList,processList,troubleList } = data;
                const joinData = [];//全部数组合到一起
                // for (let i = 0; i < hiddenDangerList.length; i++) {
                //     let item = hiddenDangerList[i];
                //     const { aqAttachmentList = [],modifyUserName,levelNameAll,finishTime } = item;
                //     joinData.push({
                //         precess: levelNameAll,
                //         time: finishTime ? moment(finishTime).format('YYYY-MM-DD HH:mm:ss') : '--',
                //         desc: `${modifyUserName}`,
                //         img: aqAttachmentList[0] ? aqAttachmentList[0].url : '',
                //         imgArr: aqAttachmentList,
                //         classify: '安全排查' // 隐患排查 质量检查   隐蔽工程
                //     })
                // }
                
                for (let i = 0; i < processList.length; i++) {
                    let item = processList[i];
                    const { gxAttachmentList = [],modifyUserName,levelNameAll,finishTime } = item;
                    joinData.push({
                        precess: levelNameAll,
                        time: finishTime ? moment(finishTime).format('YYYY-MM-DD') : '--',
                        desc: `${modifyUserName}`,
                        img: gxAttachmentList[0] ? gxAttachmentList[0].url : '',
                        imgArr: gxAttachmentList,
                        classify: '进度管理' // 隐患排查 质量检查   隐蔽工程
                    })
                }
                // for (let i = 0; i < troubleList.length; i++) {
                //     let item = troubleList[i];
                //     const { zlAttachmentList = [],modifyUserName,levelNameAll,finishTime } = item;
                //     joinData.push({
                //         precess: levelNameAll,
                //         time: finishTime ? moment(finishTime).format('YYYY-MM-DD HH:mm:ss') : '--',
                //         desc: `${modifyUserName}`,
                //         img: zlAttachmentList[0] ? zlAttachmentList[0].url : '',
                //         imgArr: zlAttachmentList,
                //         classify: '质量检查' // 隐患排查 质量检查   隐蔽工程
                //     })
                // }
        
                //处理数据data
                this.setState({
                    data: joinData,
                    loading: false
                },() => {
                    clearInterval(window.infoAutoPlayTime)
                    window.infoAutoPlayTime = setInterval(this.autoplay,scrollTime)
                    // clearInterval(window.infoAutoPlayTime)
                    // window.infoAutoPlayTime = setInterval(this.autoplay,scrollTime)
                })
            } else {
                Msg.error(message)
            }
        })
 
    }

    autoplay = () => {
        const { stop, data } = this.state;
        if (!stop && this.state.data.length > 3) {
            let _d = this.state.data;
            let data = [..._d];
            let _pop = data.shift();//删除第一个 
            data.push(_pop);
            this.setState({
                isAni: true
            },() => {
                setTimeout(() => {
                    this.setState({
                        data,
                        isAni: false
                    })
                },aniT)//根据css动画时间控制 
            })
        }

    }
    goMore = () => {
        // const { dispatch } = this.props;
        // dispatch(push(``))
        console.log('更多',this.props)
    }

    setStop = (stop) => {
        const { visible } = this.state;
        this.setState({
            stop: visible ? true : stop
        })
    }

    listClick = (data,index) => {
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
        const { loading,data = [],isAni,detailData: { imgArr = [],precess,time,desc } } = this.state;
 
        return (
            <Spin spinning={loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        实时信息 
                    </div>
                    <div className={s.con} id="con">
                        {
                            data.map((item,index) => {
                                const { time,desc,precess,img,classify } = item;
                                return <div key={index} onClick={this.listClick.bind(this,item,index)} onMouseEnter={this.setStop.bind(this,true)} onMouseLeave={this.setStop.bind(this,false)} className={`${s.precessList} ${index === 0 && isAni ? s.precessListAni : ''}`}>
                                    <div className={s.left}>
                                        <div><span style={{ color: 'orange' }}>【{classify}】</span>{precess}</div>
                                        <div className={s.timeAndDesc}>
                                            <div>
                                                {time}
                                            </div>
                                            <div>
                                                {desc}
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
                                    {time}
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
                                                imgArr.map((item = {},index) => {
                                                    return <div key={index} style={{ width: '300px',border: '1px solid red' }}>
                                                        <a target="_block" href={item.url} className={s.bannera}>
                                                            <img className={s.bannerImg} src={item.url} alt="" />
                                                        </a>
                                                    </div>
                                                })
                                            }
                                        </Carousel>
                                        <div onClick={this.bannerrl.bind(this,'left')} className={s.arrowLeft}><Icon type="left" theme="outlined" /></div>
                                        <div onClick={this.bannerrl.bind(this,'right')} className={s.arrowRight}><Icon type="right" theme="outlined" /></div>
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