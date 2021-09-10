import React, { Component } from 'react';
import { Modal } from 'antd-mobile';
class Record extends Component {
    constructor(props) {
        super(props);
        const { recordType } = this.props
        this.recordType = recordType;
        this.state = {
            recordModalVisible: false,//录音模态层
            count: 60,
            identifyModalVisible: false,//识别模态层
            identifyContent: "",//识别模态层的内容			
            identifyTitle: "",//识别模态层的标题
        }
    }
    componentWillReceiveProps(nextProps) {
        const { recordType } = nextProps
        if (recordType === "上传" || recordType === "识别") {
            this.recordType = recordType
            this.startRecord();
        }
    }
    startRecord = () => {//开始录音
        const me = this;
        const { myPublic: { wx } } = this.props
        wx.startRecord();
        me.startRecordModal()
        wx.onVoiceRecordEnd({
            fail: function () {
                me.props.recordAbort()
            },
            success: function (res) {
                me.endRecord(res.localId)
            }
        });
    }
    startRecordModal = () => {//启动录音Modal
        this.timer = setInterval(() => {
            const { count } = this.state
            if (count > 0) {
                this.setState({ count: count - 1 });
            } else {
                this.onStopRecord();
            }
        }, 1000);
        this.setState({ recordModalVisible: true, });
    }
    resetRecordModal = () => {//重置录音Modal
        const count = 60;
        clearInterval(this.timer);
        this.setState({ count, recordModalVisible: false });
    }
    onStopRecord = () => {//手动结束录音
        const me = this;
        const { myPublic: { wx } } = this.props
        wx.stopRecord({
            fail: function () {
                me.props.recordAbort()
            },
            success: function (res) {
                me.endRecord(res.localId)
            }
        });
    }
    endRecord = (localId) => {//录音正常结束
        const me = this;
        me.resetRecordModal()
        const { myPublic: { wx } } = this.props
        if (me.recordType === "上传") {//上传录音
            wx.uploadVoice({
                localId,
                isShowProgressTips: 1,
                fail: function () {
                    me.props.recordAbort()
                },
                success: function (res) {
                    me.props.uploadRecordEnd(localId, res.serverId)
                }
            });
        } else if (me.recordType === "识别") {//识别录音
            wx.translateVoice({
                localId,
                isShowProgressTips: 1,
                fail: function () {
                    me.setState({
                        identifyModalVisible: true,
                        identifyTitle: "识别失败",
                        identifyContent: "",
                    });
                },
                success: function (res) {
                    me.setState({
                        identifyModalVisible: true,
                        identifyTitle: "识别成功",
                        identifyContent: res.translateResult,
                    });
                }
            });
        }
    }

    onAbortRecord = () => {//手动终止录音
        const me = this;
        const { recordAbort, myPublic: { wx } } = me.props
        me.resetRecordModal()
        recordAbort()
        wx.stopRecord()
    }
    onIdentifyAppend = () => {//识别添加
        const { identifyContent } = this.state
        this.setState({
            identifyModalVisible: false,
        });
        this.props.identifyRecordEnd(identifyContent)
    }
    onIdentifyCancel = () => {//识别取消
        const identifyContent = ""
        this.setState({
            identifyModalVisible: false,
        });
        this.props.identifyRecordEnd(identifyContent)
    }
    componentDidMount() {
    }
    render() {
        return (
            <div>
                <Modal
                    title={"正在录音"}
                    platform={"ios"}
                    transparent={true}
                    maskClosable={false}
                    visible={this.state.recordModalVisible}
                    onClose={this.onAbortRecord}
                    footer={[
                        { text: '取消', onPress: this.onAbortRecord, style: 'default', },
                        { text: '说完了', onPress: this.onStopRecord, style: { fontWeight: 'bold', flex: '1' } }]}
                >
                    {this.state.count}
                </Modal>
                <Modal
                    title={this.state.identifyTitle}
                    platform={"ios"}
                    transparent={true}
                    maskClosable={false}
                    visible={this.state.identifyModalVisible}
                    onClose={this.onIdentifyCancel}
                    footer={[
                        { text: '取消', onPress: this.onIdentifyCancel, style: 'default' },
                        { text: '确定', onPress: this.onIdentifyAppend, style: { fontWeight: 'bold', flex: '1' } }]}
                >
                    {this.state.identifyContent}
                </Modal>

            </div>
        )
    }
}
export default Record;