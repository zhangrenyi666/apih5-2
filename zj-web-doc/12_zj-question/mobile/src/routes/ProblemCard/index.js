import React,{ Component } from 'react';
import { Carousel,WhiteSpace,Modal,List,Flex,Toast } from 'antd-mobile'; //, Badge
import { domain } from '../../main';
import moment from 'moment';
import 'moment/locale/zh-cn';
import styles from './style.less';
import { SuperList,Icon } from '../../components';
import { problemReplyData as formData,problemAgree,problemAgrees,problemAgreet } from '../formConfig';
// import { withRouter } from 'react-router-dom';
import { myFetch } from '../../tools';
import { wx } from '../../main';

const replaySvg = require('../../svgs/replay.svg');

class ProblemCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            modal1: false,
            modal2: false,
            modal3: false,
            modal4: false,
            data: {},
            formData,
            oaManagerForWeChatList: [],//领导人员下拉数据
            pl: [],//问题回答追问列表   pl(评论)
            flag: this.props.match.params.flag || ''
        }
    }

    componentDidMount() {
        this.allFresh();
        // window.refreshChat = setInterval(this.refresh, 4000)
    }

    // componentWillUnmount(){
    //     clearInterval(window.refreshChat)
    // }

    componentWillUpdate(nextProps,nextState) {
        // let { formData } = this.state;
        let { oaManagerForWeChat,orgId,displayFlag } = nextProps.data;
        if (nextState.modal1) {
            //如果是回答状态的话需要显示项目经理选项 
            if (displayFlag === '1') {
                if (this.SuperList) {
                    this.SuperList.props.config.setFieldHide({
                        oaManagerForWeChat: false
                    })
                }
            }
        }
    }

    bannerPre = (imageList,index) => {
        wx.previewImage({
            current: imageList[index], // 
            urls: imageList // 
        });
    }

    allFresh = () => {
        const { recordid,personnelFlag } = this.props.match.params;
        myFetch('getQuestionDetailWechat',{//请求详情数据
            recordid,
            personnelFlag
        }).then(({ success,message,data }) => {
            let firstObj;
            const { answerContent,answerUser,answerTime,answerImageListForWechat,firstAnswerFlag,voiceList,displayFlag } = data;
            if (answerContent) {
                //第一次回答的数据另外保存
                firstObj = {
                    text: answerContent || '',
                    per: answerUser,
                    qAFlag: '1', //0：追问；1：回答
                    time: answerTime,
                    imageList: answerImageListForWechat || [],
                    voiceList
                }
            }


            this.setState({//设置总数据
                data: data,

                //设置state里的askId  当回复按钮点击时askId将被覆盖
                askId: data.askId,

                firstPl: firstObj ? [firstObj] : [],//第一条回答数据
            },() => {
                //再去请求聊天详情数据
                this.refresh()
            })
        })
    }

    refresh = () => {
        //刷新评论列表
        let { recordid } = this.state.data;
        let { firstPl = [] } = this.state;
        myFetch('getAskAndAnswerDetailWechat',{ recordid: recordid }).then(({ success,data,message }) => {
            if (success && data) {
                let pl = [...firstPl];//第一条回答是存在的 

                data.map((item) => {
                    const { askUser,askContent = '',answerContent = '',voiceList,askTime,answerTime = new Date(),answerImageListForWechat = [],qAFlag,askId } = item;

                    return pl.push({
                        text: answerContent || askContent,
                        per: askUser,
                        time: askTime || answerTime,
                        imageList: answerImageListForWechat,
                        qAFlag: qAFlag, //0：追问；1：回答 
                        askId,
                        voiceList
                    })
                })

                this.setState({
                    pl
                })
            }
        })

        //audio.pause监听语音是否播放完成

    }

    componentDidUpdate() {
        let { data,modal1,setOaManagerForWeChated,formData } = this.state;
        if (modal1 && this.SuperList) {
            let { setFieldsValue,form } = this.SuperList.props.config;

            //只在回答的时候才会出现的字段
            if (data.displayFlag === '1') {
                // form.getFieldInstance('oaManagerForWeChat').defaultValue = data.oaManagerForWeChat;
                // form.getFieldInstance('oaManagerForWeChat').setState({
                //     defaultValue: data.oaManagerForWeChat
                // })
                setFieldsValue({
                    displayFlag: data.displayFlag,
                    personnelFlag: data.personnelFlag,
                    recordid: data.recordid,
                    replyObject: data.replyObject,
                    oaManagerForWeChat: data.oaManagerForWeChat,
                    firstAnswerFlag: data.firstAnswerFlag,
                });
            } else {
                setFieldsValue({
                    displayFlag: data.displayFlag,
                    personnelFlag: data.personnelFlag,
                    recordid: data.recordid,
                    replyObject: data.replyObject,
                    firstAnswerFlag: data.firstAnswerFlag,
                });
            }
            //手动设置项目经理 为不可编辑并且设置默认值
            if (data.oaManagerForWeChat.length > 0 && !setOaManagerForWeChated) {
                formData.formConfig.map((item,index) => {
                    if (item.field === 'oaManagerForWeChat') {
                        item.isHide = false;
                        item.pullPersonOption.edit = false;
                    }
                    return item;
                })
                // console.log(formData);
                this.setState({
                    formData,
                    setOaManagerForWeChated: true
                })
            }

        }
    }

    replyBtn = (askId) => {
        this.setState({
            modal1: true,
            askId
        })
    }

    voiceList = (props) => {
        if (!props) {
            return ''
        }
        const { actionMp3 } = this.state;
        return <div className={styles.voiceListCon}>
            {
                props.map((item,index) => {
                    const { url } = item;
                    return <div key={index} className={actionMp3 === url ? styles.actiondiv : ''} onClick={() => {
                        this.setState({
                            actionMp3: actionMp3 === url ? '' : url
                        },() => {
                            if (this.state.actionMp3 && document.getElementById('audio')) {
                                document.getElementById('audio').play();
                                clearInterval(window.audioTimer)
                                window.audioTimer = setInterval(() => {
                                    if (document.getElementById('audio').ended) {
                                        clearInterval(window.audioTimer);
                                        this.setState({
                                            actionMp3: ''
                                        })
                                    }
                                },1000)
                            } else {
                                if (document.getElementById('audio')) {
                                    document.getElementById('audio').pause();
                                }
                            }
                        })
                    }}></div>
                })
            }
        </div>
    }


    replay() {//回复jlk
        Toast.loading();
        let { getAllFiledValus } = this.SuperList.props.config;
        let { askId } = this.state;
        // let { firstAnswerFlag } = this.state.data;
        getAllFiledValus(true,({ values }) => {
            let params = {
                askId: askId ? askId : values.askId,
                askContent: values.askContent,
                answerImageListForWechat: values.imageList,
                oaManagerForWeChat: values.oaManagerForWeChat,
                recordid: values.recordid,
                questionApprovalId: values.recordid,
                firstAnswerFlag: values.firstAnswerFlag,  //首次回答 0 需要刷新整个详情页数据 因为首次回答是单独的、
                voiceList: values.voiceList,
                oaProjectManagerId: values.oaProjectManagerId && values.oaProjectManagerId.length ? values.oaProjectManagerId[0].split(',').join('-') : '',
                departmentName: values.departmentName
            }
            let apiName = ''; //addZjQuestionAsk 追问   addZjQuestionAnswerWechat 回答
            if (values.displayFlag === '1') {//0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
                apiName = 'addZjQuestionAnswerWechat'
            } else {
                apiName = 'addZjQuestionAsk'
            }
            myFetch(apiName,params).then(({ success,message,data }) => {
                if (success) {
                    this.setState({
                        modal1: false
                    },() => {
                        if (values.firstAnswerFlag === '0') {
                            //直接刷新页面，因为这是两个superList还没做到子刷父
                            window.location.href = this.updateUrl(window.location.href);
                        } else {
                            this.refresh()
                        }
                        Toast.success('数据保存成功!');
                    })
                } else {
                    this.setState({
                        modal1: false
                    },() => {
                        Toast.fail(message);
                    })

                }

            })
        })
    }

    replays() {//回复jlk
        Toast.loading();
        let { getAllFiledValus } = this.SuperList.props.config;
        let { askId } = this.state;
        // let { firstAnswerFlag } = this.state.data;
        getAllFiledValus(true,({ values }) => {
            let params = {
                askId: askId ? askId : values.askId,
                checkUserForWechat: values.checkUserForWechat,
                recordid: values.recordid,
                firstAnswerFlag: values.firstAnswerFlag,  //首次回答 0 需要刷新整个详情页数据 因为首次回答是单独的、
                checkUserId: values.checkUserId && values.checkUserId.length ? values.checkUserId[0] : '',
                leaderOption: values.leaderOption
            }
            myFetch('leaderAgreeWechat',params).then(({ success,message,data }) => {
                if (success) {
                    this.setState({
                        modal2: false
                    },() => {
                        Toast.success('数据保存成功!');
                        window.location.href = window.location.origin + '/#/problemList';
                    })
                } else {
                    this.setState({
                        modal2: false
                    },() => {
                        Toast.fail(message);
                    })

                }

            })
        })
    }

    replayt() {//回复jlk
        Toast.loading();
        let { getAllFiledValus } = this.SuperList.props.config;
        let { askId } = this.state;
        // let { firstAnswerFlag } = this.state.data;
        getAllFiledValus(true,({ values }) => {
            let params = {
                askId: askId ? askId : values.askId,
                leaderOption: values.leaderOption,
                recordid: values.recordid,
                firstAnswerFlag: values.firstAnswerFlag,  //首次回答 0 需要刷新整个详情页数据 因为首次回答是单独的、
            }
            myFetch('leaderRejectWechat',params).then(({ success,message,data }) => {
                if (success) {
                    this.setState({
                        modal3: false
                    },() => {
                        Toast.success('数据保存成功!');
                        window.location.href = window.location.origin + '/#/problemList';
                    })
                } else {
                    this.setState({
                        modal3: false
                    },() => {
                        Toast.fail(message);
                    })

                }

            })
        })
    }

    replayq() {//转发jlk
        Toast.loading();
        let { getAllFiledValus } = this.SuperList.props.config;
        let { askId } = this.state;
        getAllFiledValus(true,({ values }) => {
            let params = {
                recordid: values.recordid,
                forwardUserForWechat: values.forwardUserForWechat,
                forwardUserId: values.forwardUserId && values.forwardUserId.length ? values.forwardUserId[0].split(',').join('-') : ''
            }
            myFetch('zjQuestionApprovalChooseForwardUserWechat',params).then(({ success,message,data }) => {
                if (success) {
                    this.setState({
                        modal4: false
                    },() => {
                        Toast.success('数据保存成功!');
                        window.location.href = this.updateUrl(window.location.href);
                    })
                } else {
                    this.setState({
                        modal4: false
                    },() => {
                        Toast.fail(message);
                    })

                }

            })
        })
    }
    updateUrl(url,key) {//刷新页面方法（防止微信缓存）
        var key = (key || 't') + '=';  //默认是"t"
        var reg = new RegExp(key + '\\d+');  //正则：t=1472286066028
        var timestamp = +new Date();
        if (url.indexOf(key) > -1) { //有时间戳，直接更新
            return url.replace(reg,key + timestamp);
        } else {  //没有时间戳，加上时间戳
            if (url.indexOf('\?') > -1) {
                let urlArr = url.split('\?');
                if (urlArr[1]) {
                    return urlArr[0] + '?' + key + timestamp + '&' + urlArr[1];
                } else {
                    return urlArr[0] + '?' + key + timestamp;
                }
            } else {
                if (url.indexOf('#') > -1) {
                    return url.split('#')[0] + '?' + key + timestamp + window.location.hash;
                } else {
                    return url + '?' + key + timestamp;
                }
            }
        }
    }

    render() {
        let {
            createUser,//创建者
            recordid,
            createUserName,
            imageList = [],//图片列表
            questionTitleName,//问题标题
            questionDescribe, //问题描述
            personnelFlag, //0：[同意/不同意]；1：[回答]；2：[追问/结束] 
            displayFlag,//0：[同意不同意]；1：[回答]；2：[追问]；3：[结束]  显示什么按钮的字段
            askVoiceList,
            leaderApprovalOpinion,
            leaderUserName,
            leaderApprovalDate,

            // 提问人的附件
            questionFile = [],

        } = this.state.data;
        let { pl,formData,actionMp3,flag } = this.state;//评论列表 
        // let data = {
        //     // problem: '网络不稳定，且网速很慢',
        //     // content: '经常掉经常掉线，慢经常掉线网速很慢线网速很慢eeee',
        //     // imageList: [
        //     //     {
        //     //         url: 'http://www.5iweb.com.cn/resource/5iweb2016050302/images/orange.png'
        //     //     },
        //     // ],
        //     pl: [
        //         {
        //             text: '第一次评论',
        //             per: '测试员',
        //             qAFlag:'1', //0：追问；1：回答
        //             time: 4548145125463,
        //             imageList: [
        //                 {
        //                     url: 'http://www.5iweb.com.cn/resource/5iweb2016050302/images/orange.png'
        //                 }, 
        //             ],
        //         }
        //     ]
        // }
        // imageList = [
        //     {
        //         url: 'https://publicdomainarchive.com/wp-content/uploads/2014/03/public-domain-images-archive-high-quality-resolution-free-download-splitshire-0009-1000x666.jpg'
        //     },
        // ];

        // questionFile = [
        //     {
        //         name: '第一个附件',
        //         url: 'http://aaa.com'
        //     },
        //     {
        //         name: '第二个附件',
        //         url: 'http://aaa.com'
        //     },
        // ];

        return <div className={styles.ProblemCard}>
            {
                actionMp3 ?
                    <audio autoPlay src={domain.api + actionMp3} id="audio"></audio>
                    : ''
            }

            <div className={styles.ProblemCardCon}>
                <div className={styles.badge}>问题详情</div>

                <div className={styles.title}>
                    {questionTitleName}
                </div>
                <div>
                    <div className={styles.desc}>
                        {questionDescribe}
                    </div>
                    <div className={styles.voiceList}>
                        {this.voiceList(askVoiceList)}
                    </div>
                </div>
                <div>
                    <Carousel
                        autoplay={true}
                        infinite
                        selectedIndex={0}
                    >
                        {
                            imageList
                                ?
                                imageList.map((val,i) => {

                                    let _u = val.fileUrl || val.url;
                                    let url = domain.api + _u;
                                    if (_u) {
                                        let reg = /^(http|https)/g;
                                        if (reg.test(_u)) {
                                            console.log(1111)
                                            url = _u;
                                        }
                                    }

                                    return <a
                                        key={i}
                                        style={{ display: 'inline-block',width: '100%',height: '300px',}}
                                        onClick={() => {
                                            //调用微信的图片预览
                                            let imgs = [];
                                            for (let i = 0,l = imageList.length; i < l; i++) {
                                                imgs.push(domain.api + imageList[i].fileUrl);
                                            }
                                            this.bannerPre(imgs,i);
                                        }}
                                    >
                                        <img
                                            src={url}
                                            alt=""
                                            style={{ width: '100%',height: '300px',verticalAlign: 'middle',display: 'block',margin: '0 auto' }}
                                        />
                                    </a>
                                })
                                :
                                ''
                        }
                    </Carousel>
                </div>
                {
                    questionFile.length ? <div style={{ fontSize: '14px',marginTop: '24px' }}>
                        {
                            questionFile.map((item,index) => {
                                return <div key={index}>
                                    <a href={item.url}>{item.name}</a>
                                </div>
                            })
                        }
                    </div> : null
                }

            </div>

            <div className={styles.comment}>
                <List>
                    {
                        pl
                            ?
                            pl.map((item,i) => {
                                //qAFlag 0：追问；1：回答 
                                let { imageList,time,per,qAFlag,askId,voiceList,changeFile = [] } = item;
                                // changeFile = [
                                //     {
                                //         name: '整改人第一个附件',
                                //         url: 'http://aaa.com'
                                //     },
                                //     {
                                //         name: '整改人第二个附件',
                                //         url: 'http://aaa.com'
                                //     },
                                // ]; 

                                // voiceList = [{url:'11', url:'555'}]
                                return <List.Item
                                    key={i}
                                    multipleLine={true}
                                    wrap={true}
                                    className={styles.specialBadge}>
                                    <WhiteSpace size="lg" />
                                    <div className={styles.commentCon}>
                                        <div style={{ width: '100%',wordWrap: 'nowrap',wordBreak: 'break-all' }}>{item.text || '-'}</div>
                                        <WhiteSpace size="lg" />
                                        {/* <div>语音列表</div> */}
                                        <div className={styles.voiceList}>
                                            {this.voiceList(voiceList)}
                                        </div>
                                        <div style={{ display: imageList && imageList.length > 0 ? 'flex' : 'none' }} className={styles.commentConImgCon}>
                                            <WhiteSpace size="lg" />
                                            {
                                                imageList ?
                                                    imageList.map((v,i) => {
                                                        return <div className={styles.commentConImg} key={i}>
                                                            <img
                                                                onClick={() => {
                                                                    //调用微信的图片预览
                                                                    let imgs = [];
                                                                    for (let i = 0,l = imageList.length; i < l; i++) {
                                                                        imgs.push(domain.api + imageList[i].fileUrl);
                                                                    }
                                                                    this.bannerPre(imgs,i);
                                                                }}
                                                                src={domain.api + v.fileUrl} alt="" />
                                                        </div>
                                                    })
                                                    : ''
                                            }
                                        </div>
                                        {
                                            changeFile.length ? <div style={{ fontSize: '14px',margin: '12px 0px' }}>
                                                {
                                                    changeFile.map((item,index) => {
                                                        return <div key={index}>
                                                            <a href={item.url}>{item.name}</a>
                                                        </div>
                                                    })
                                                }
                                            </div> : null
                                        }

                                        <div>

                                            <Flex justify="between">
                                                <span style={{ color: '#999' }}>来自：{per}的{qAFlag === '1' ? '回答' : '追问'}</span>
                                                {
                                                    (qAFlag === '0' && displayFlag === '1') ?
                                                        <span onClick={() => { this.replyBtn(askId) }} style={{ color: '#108ee9',display: 'flex',alignItems: 'center' }}><Icon type={replaySvg} />{flag === '1' ? null : <span>回复</span>}</span>
                                                        :
                                                        null
                                                }
                                            </Flex>
                                            <div style={{ color: '#999' }}>时间：{moment(time || new Date()).format('YYYY-MM-DD HH:mm:ss')}</div>
                                        </div>
                                    </div>
                                </List.Item>
                            })
                            :
                            ''
                    }
                </List>
            </div>
            {
                leaderApprovalOpinion != '' && displayFlag != 2 ? <div style={{ color: 'rgb(153, 153, 153)',marginTop: '5%',fontSize: '14px',fontWeight: 'bold',paddingLeft: '15px' }}>
                    <div>领导意见:</div>
                    <div style={{ textIndent: "10%",marginTop: '1%' }}>{leaderApprovalOpinion}</div>
                    <div style={{ marginTop: '1%' }}>领导：{leaderUserName}</div>
                    <div>时间：{moment(leaderApprovalDate || new Date()).format('YYYY-MM-DD HH:mm:ss')}</div>
                </div> : null
            }
            <Modal
                visible={this.state.modal1}
                transparent
                height="60vh"
                style={{ width: '90%',overflow: 'scroll' }}
                maskClosable={false}
                onClose={() => { this.setState({ modal1: false }) }}
                title={personnelFlag === '1' ? '回答' : '驳 回'}  //"追问"
                footer={[
                    { text: '取消',onPress: () => { this.setState({ modal1: false }) } },
                    { text: '确定',onPress: this.replay.bind(this) }
                ]}
                wrapProps={{ onTouchStart: this.onWrapTouchStart }}
            >
                <div style={{ height: '60vh',overflowY: 'scroll' }}>
                    <SuperList ref={el => {
                        if (el) {
                            this.SuperList = el
                        }
                    }} config={formData} />
                </div>
            </Modal>

            <Modal
                visible={this.state.modal2}
                transparent
                height="10vh"
                style={{ width: '90%',overflow: 'scroll' }}
                maskClosable={false}
                onClose={() => { this.setState({ modal2: false }) }}
                title={'问题发起人：' + createUserName}
                footer={[
                    { text: '取消',onPress: () => { this.setState({ modal2: false }) } },
                    { text: '确定',onPress: this.replays.bind(this) }
                ]}
                wrapProps={{ onTouchStart: this.onWrapTouchStart }}
            >
                <div style={{ height: '40vh',overflowY: 'scroll' }}>
                    <SuperList ref={el => {
                        if (el) {
                            this.SuperList = el
                        }
                    }} config={problemAgree} />
                </div>
            </Modal>

            <Modal
                visible={this.state.modal3}
                transparent
                height="10vh"
                style={{ width: '90%',overflow: 'scroll' }}
                maskClosable={false}
                onClose={() => { this.setState({ modal3: false }) }}
                title={'问题发起人：' + createUserName}
                footer={[
                    { text: '取消',onPress: () => { this.setState({ modal3: false }) } },
                    { text: '确定',onPress: this.replayt.bind(this) }
                ]}
                wrapProps={{ onTouchStart: this.onWrapTouchStart }}
            >
                <div style={{ height: '30vh',overflowY: 'scroll' }}>
                    <SuperList ref={el => {
                        if (el) {
                            this.SuperList = el
                        }
                    }} config={problemAgrees} />
                </div>
            </Modal>

            <Modal
                visible={this.state.modal4}
                transparent
                height="10vh"
                style={{ width: '90%',overflow: 'scroll' }}
                maskClosable={false}
                onClose={() => { this.setState({ modal4: false }) }}
                title='转发'
                footer={[
                    { text: '取消',onPress: () => { this.setState({ modal4: false }) } },
                    { text: '确定',onPress: this.replayq.bind(this) }
                ]}
                wrapProps={{ onTouchStart: this.onWrapTouchStart }}
            >
                <div style={{ height: '30vh',overflowY: 'scroll' }}>
                    <SuperList ref={el => {
                        if (el) {
                            this.SuperList = el
                        }
                    }} config={problemAgreet} />
                </div>
            </Modal>
        </div>

    }


}

export default ProblemCard;