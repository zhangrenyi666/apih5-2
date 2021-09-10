import React, { Component } from 'react';
import { List, TextareaItem, ImagePicker, Picker, Icon, Modal, Toast } from 'antd-mobile';
import SvgIcon from '../common/svgIcon';
import Record from './record'
import Upload from 'rc-upload';
import MyStorage from "../../lny_modules/Storage"
import './EnquiryReplyDialog.less';
const alert = Modal.alert;


class EnquiryReplyDialog extends Component {
    constructor(props) {
        super(props);
        this.enquiryId = this.props.enquiryId
        this.timer = null;//倒计时的时间
        this.serverId = "";//服务器的上传语音id
        this.timer = null;//语音定时
        this.modal = {}
        this.yuyinIcons = [
            'yuyin3',
            'yuyin2',
            'yuyin3'
        ]
        this.storageName = 'answer'
        let storageName_otherAccessoryList = MyStorage.getItem(`${this.storageName}_otherAccessoryList`)
        let otherAccessoryList = storageName_otherAccessoryList ? storageName_otherAccessoryList.data || [] : []

        this.state = {
            recordType: null,
            content: "",//最终的内容
            files: [],//临时图片
            localIds: null, //图片id
            localId: "",//语音id
            selectPerson: [],//问询对象
            person: [],//问询对象
            yuyinIcon: this.yuyinIcons[2],//语音图标  
            otherAccessoryList
        }

    }
    replyHide = () => { //消失回复
        this.setState({ recordType: null });
        this.props.replyHide();
    }
    onChangeContent = (content) => {//改变内容
        this.setState({ content });
    }
    onChangeImages = (files, type, index) => {
        this.setState({
            files,
        });
    };
    onAddImageClick = (e) => {//改变图片
        e.preventDefault();
        const me = this;
        const { myPublic: { wx } } = me.props
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                const localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                wx.uploadImage({
                    localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res2) {
                        const serverId = res2.serverId; // 返回图片的服务器端ID
                        var files = [];
                        for (const item of me.state.files) {
                            files.push(item)
                        }
                        if (window.__wxjs_is_wkwebview) {
                            wx.getLocalImgData({
                                localId: localIds[0], // 图片的localID
                                success: function (res3) {
                                    const localData = res3.localData;// localData是图片的base64数据，可以用img标签显示
                                    files.push({
                                        url: localData,
                                        serverId
                                    })
                                    me.setState({ files });
                                }
                            });
                        } else {
                            files.push({
                                url: localIds[0],
                                serverId
                            })
                            me.setState({ files });
                        }
                    }
                });
            }
        });
    };
    onImageClick = (index, files) => {//查看图片
        var fileImg = [];
        const { myPublic: { wx } } = this.props
        for (const item of files) {
            fileImg.push(item.url);
        }
        wx.previewImage({
            current: fileImg[index], // 
            urls: fileImg // 
        });
    }

    onChangePerson = (person) => {//改变问询对象
        this.setState({ person });
    }

    onOpenRecord = (recordType) => {//开启录音
        this.setState({ recordType })
    }
    recordAbort = () => {//录音终止
        this.setState({ recordType: null })
    }
    identifyRecordEnd = (identifyContent) => {//识别录音结束
        const { content } = this.state
        this.setState({ content: (content.toString() + identifyContent.toString()), recordType: null });
    }
    uploadRecordEnd = (localId, serverId) => {//上传录音结束
        this.serverId = serverId;
        this.setState({ localId, recordType: null });
    }
    onRemoveVoice = () => {//删除录音
        const me = this;
        me.playVoiceAni(false);
        me.setState({ localId: null })
    }
    onPlayVoice = () => {//播放录音
        const me = this;
        const { state: { localId } } = me
        const { myPublic: { wx } } = me.props
        me.playVoiceAni(true);
        wx.playVoice({ localId });
    }
    //语音动画
    playVoiceAni = (start) => {
        const me = this;
        clearInterval(me.playVoiceAniTimer);
        if (!start) {
            me.playVoiceAniTimer = null
            return;
        } else {
            me.playVoiceAniTimer = setInterval(function () {
                setTimeout(function () {
                    me.setState({
                        yuyinIcon: me.yuyinIcons[0]
                    })
                }, 0)
                setTimeout(function () {
                    me.setState({
                        yuyinIcon: me.yuyinIcons[1]
                    })
                }, 150)
                setTimeout(function () {
                    me.setState({
                        yuyinIcon: me.yuyinIcons[2]
                    })
                }, 300)
            }, 900)
        }
    }
    otherChange = (otherAccessoryList) => {
        this.setState({ otherAccessoryList })
    }
    componentDidUpdate(prevProps, prevState) {
        const me = this;
        const { myFetch, myPublic: { wx }, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = me.props
        const { pathName } = routeData[curKey]
        if (!prevProps.visible && me.props.visible) {
            wx.onVoicePlayEnd({
                success: function (res) {
                    const { localId } = res
                    if (me.state.localId === localId) {
                        me.playVoiceAni(false);
                    }
                }
            });
            Toast.loading('Loading...', 0);
            myFetch('getOtherOrgMemberList').then(({ data, success, code, message }) => {
                Toast.hide()
                if (success) {
                    const selectPerson = data.map((item, index) => {
                        return {
                            value: item.departmentId,
                            label: item.departmentName,
                            children: item.sysUserList.map((item, index) => {
                                return {
                                    value: item.userId,
                                    label: item.realName,
                                }
                            })
                        }
                    })
                    me.setState({ selectPerson })
                } else if (code === "3003" || code === "3004") {
                    dispatch(logout(pathName))
                }
            })

        }
    }
    componentWillUnmount() {
        for (var key in this.modal) {
            if (this.modal.hasOwnProperty(key)) {
                this.modal[key].close()
            }
        }
    }
    render() {
        return (
            <Modal
                style={{ width: "90%" }}
                title={this.props.title}
                platform={"ios"}
                transparent={true}
                maskClosable={false}
                visible={this.props.visible}
                onClose={this.replyHide}
                footer={[
                    { text: '取消', onPress: this.replyHide, style: 'default' },
                    { text: '提交', onPress: this.save, style: { fontWeight: 'bold', flex: '1' } }]}
            >
                <div className="ReplyDialog">
                    <List>
                        <div className="textareaBox">
                            <TextareaItem count={1000} value={this.state.content} onChange={this.onChangeContent} rows={5} placeholder="请输入问题描述" />
                            <div className="vocieIcon">
                                <SvgIcon type={'yuyin'} onClick={() => { this.onOpenRecord("识别") }} />
                            </div>
                        </div>
                        <div className="voiceBox">
                            <List.Item >
                                <div className="voice_img" >
                                    <div className="voice_img_babel">语音：</div>
                                    <div className="voice_img_svgBox" onClick={this.onPlayVoice} style={{ display: this.state.localId ? "block" : "none" }}>
                                        <SvgIcon type={this.state.yuyinIcon}
                                            className="voice_img_svg"
                                        />
                                    </div>
                                </div>
                            </List.Item>
                            <div className="vocieIcon">
                                {(!this.state.localId ? <SvgIcon type={'yuyin'} onClick={() => { this.onOpenRecord("上传") }} /> : <SvgIcon type={'cancel'} onClick={this.onRemoveVoice} />)}
                            </div>
                        </div>
                        <div className="imgBox">
                            <ImagePicker
                                files={this.state.files}
                                onChange={this.onChangeImages}
                                onImageClick={this.onImageClick}
                                selectable={this.state.files.length < 8}
                                onAddImageClick={this.onAddImageClick}
                            />
                        </div>
                        <div>
                            <Picker data={this.state.selectPerson} cols={2} value={this.state.person} onChange={this.onChangePerson}>
                                <List.Item arrow="horizontal">指定人：</List.Item>
                            </Picker>
                            <UploadTest {...this.props} storageName={this.storageName} fileList={this.state.otherAccessoryList} onChange={this.otherChange} >
                                <List.Item arrow="horizontal" extra={"请选择"}>其他附件：</List.Item>
                            </UploadTest>
                        </div>
                    </List>

                    {/*按钮*/}
                    <Record {...this.props} recordType={this.state.recordType} recordAbort={this.recordAbort} uploadRecordEnd={this.uploadRecordEnd} identifyRecordEnd={this.identifyRecordEnd} />
                </div>
            </Modal>
        )
    }

    save = () => {//回复
        const me = this;
        const { enquiryId, serverId, storageName } = me;
        const { content, files, person, otherAccessoryList } = me.state
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = me.props
        const { pathName } = routeData[curKey]
        let isClick = true;
        otherAccessoryList.forEach(item => {
            if (!item.url) {
                isClick = false
            }
        });
        if (!isClick) {
            return this.modal.alert = alert("还有文件正在上传。", "");
        }
        if (!person[1]) {
            return this.modal.alert = alert("请选择指定回答人。", "");
        }
        if (!content && !serverId && files.length === 0) {
            return this.modal.alert = alert("您没有任何问题信息可以提交。", "");
        }
        this.modal.alert = alert('提交回复', '确定提交么?', [
            { text: '取消', style: { color: "#333" } },
            {
                text: '确定',
                onPress: () => new Promise((resolve, reject) => {
                    Toast.loading('Loading...', 0);
                    var imageInfoList = [];
                    for (const item of files) {
                        imageInfoList.push({
                            isWeChatId: 1,
                            mediaId: item.serverId
                        })
                    }
                    var body = {
                        enquiryId,//回复列表的id
                        flowUserId: person[1],
                        answerContent: content,//回复内容
                        imageInfoList,//回复图片
                        voiceInfoList: serverId ? [{ isWeChatId: 1, mediaId: serverId }] : [],//语音 
                        otherAccessoryList
                    }
                    myFetch('addAnswer', body).then(({ success, data, code }) => {
                        if (success) {
                            Toast.success('操作成功!', 1, () => {
                                MyStorage.removeItem(`${storageName}_otherAccessoryList`).then(() => {
                                    resolve()
                                    me.props.save();
                                    me.replyHide();
                                })
                            });
                        } else if (code === "3003" || code === "3004") {
                            dispatch(logout(pathName))
                        }
                    })
                }),
            },
        ])
    }
}

class UploadTest extends Component {
    constructor(props) {
        super(props);
        const {
            dispatch,
            actions: { logout },
            routerInfo: { routeData, curKey },
            myPublic: { domain, appInfo: { name: projectName } },
            loginAndLogoutInfo: { loginInfo: { token } },
            onChange
        } = this.props
        const { pathName } = routeData[curKey]

        const me = this;
        this.uploaderProps = {
            action: `${domain}upload`,
            data: { projectName },
            headers: { token },
            multiple: true,
            beforeUpload(file) {
                // console.log('beforeUpload', file.name);
            },
            onStart: (file) => {
                // this.refs.inner.abort(file);
                let { uid, name } = file
                let _fileList = [...me.props.fileList]
                _fileList.push({ uid, name, curStep: 0 })
                onChange(_fileList)
            },
            onSuccess(file) {
                // console.log('onSuccess', file);
                const { success, data, code, message } = file
                if (success) {
                    let _fileList = [...me.props.fileList]
                    _fileList.push({ ...data })
                    onChange(_fileList)

                } else if (code === "3003" || code === "3004") {
                    dispatch(logout(pathName))
                } else {
                    Toast.offline(message, 2)
                }
            },
            onProgress(step, file) {
                let curStep = Math.round(step.percent)
                // console.log('onProgress', curStep, file.name);
                let _fileList = [...me.props.fileList]
                _fileList.forEach((item, index, arr) => {
                    let { uid } = item
                    if (uid === file.uid) {
                        if (curStep === 100) {
                            arr.splice(index, 1)
                        } else {
                            _fileList[index].curStep = curStep
                        }
                    }
                })
                onChange(_fileList)
            },
            onError(err) {
                // console.log('onError', err);
            },
        };
    }
    removeItem = (file) => {
        const { onChange, fileList, storageName } = this.props
        let _fileList = [...fileList]
        _fileList.forEach((item, index, arr) => {
            let { uid } = item
            if (uid === file.uid) {
                arr.splice(index, 1)
            }
        })
        MyStorage.setItem(`${storageName}_otherAccessoryList`, _fileList).then(() => {
            onChange(_fileList)
        })
    }
    onClick = (url) => {
        const { fileList, storageName } = this.props
        let isClick = true;
        fileList.forEach(item => {
            if (!item.url) {
                isClick = false
            }
        });
        if (isClick) {
            MyStorage.setItem(`${storageName}_otherAccessoryList`, fileList).then(() => {
                window.location.href = url
            })
        } else {
            Toast.info("还有文件正在上传不可查看！", 2)
        }
    }
    render() {
        const { fileList = [], children } = this.props
        return (
            <div>
                <Upload {...this.uploaderProps} ref="inner">
                    {

                        children
                    }
                </Upload>
                <div>
                    {
                        fileList.map((item, key) => {
                            let { curStep, url } = item
                            return (
                                <List.Item key={key} extra={curStep ? curStep + "%" : <Icon onClick={() => { this.removeItem(item) }} type={'cross'} />}>
                                    <a style={url ? { color: "blue" } : {}} onClick={() => { if (url) { this.onClick(url) } }}>{item.name}</a>
                                </List.Item>
                            )
                        })
                    }

                </div>
            </div>
        )

    }
}


export default EnquiryReplyDialog;