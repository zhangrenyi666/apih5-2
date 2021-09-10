import React, { Component } from 'react';
import { List, Button, Toast, Modal, Flex, WhiteSpace } from 'antd-mobile';
import EnquiryReplyDialog from './EnquiryReplyDialog'
import FloorItem from './floorItem'
import styles from './EnquiryDetail.less';
import { goBack } from 'connected-react-router';
const alert = Modal.alert;
class EnquiryDetail extends Component {
	constructor(props) {
		super(props);
		const { history, match: { params: { enquiryId } } } = this.props
		this.enquiryId = enquiryId;
		this.history = history;
		this.modal = {}
		this.state = {
			stateFlag: this.props.match.params.stateFlag,
			visible: false,
			levelDiff: "", //问题优先级
			enquiryDiff: "",//问题类型
			enquiryTitle: "", //问题标题
			answerUserName: "",
			data: {
				enquiryUserId: "",
				enquiryUserName: "", //问题提问者
				createTime: 0, //问题提问时间
				enquiryContent: "", //问题内容
				imageInfoList: [], //图片组
				voiceInfoList: [], //问题语音
				otherAccessoryList: []//其他附件
			},
			answerList: [], //问题回复列表
		}
	}
	playFn = (newVoice) => {
		const me = this;
		if (!me.voiceList) { me.voiceList = [] }
		if (!me.audio) {
			me.audio = new Audio()
			me.audio.onplay = () => {
				for (let i = 0; i < me.voiceList.length; i++) {
					if (me.voiceList[i].src === me.audio.src) {
						me.voiceList[i].setState({ state: 2 })
						break;
					}
				}
			};
			me.audio.onended = () => {
				for (let i = 0; i < me.voiceList.length; i++) {
					if (me.voiceList[i].src === me.audio.src) {
						me.voiceList[i].setState({ state: 1 })
						break;
					}
				}
			};
			me.audio.onerror = () => {
				Toast.offline("未发现该音频文件", 2)
				for (let i = 0; i < me.voiceList.length; i++) {
					if (me.voiceList[i].src === me.audio.src) {
						me.audio.pause()
						me.voiceList[i].setState({ state: 1 })
						break;
					}
				}
			}
		}
		let newIndex = -1;
		for (let i = 0; i < me.voiceList.length; i++) {
			if (newVoice.src && me.voiceList[i].src === newVoice.src) {
				newIndex = i;
				break;
			}
		}
		if (newIndex === -1) {
			newIndex = me.voiceList.length
			me.voiceList.push(newVoice)
		}
		if (!me.audio.paused) {
			me.audio.pause()
			let curIndex = -1;
			for (let i = 0; i < me.voiceList.length; i++) {
				if (me.voiceList[i].src === me.audio.currentSrc) {
					curIndex = i
					me.voiceList[i].setState({ state: 1 })
					break;
				}
			}
			if (me.voiceList[curIndex] && me.voiceList[curIndex].src === newVoice.src) {
				return
			}
		}
		me.audio.src = newVoice.src;
		me.audio.play()
	}
	componentWillUnmount() {
		const me = this;
		if (me.audio) {
			me.audio.onplay = null
			me.audio.onended = null
			me.audio = null
		}
		for (var key in this.modal) {
			if (this.modal.hasOwnProperty(key)) {
				this.modal[key].close()
			}
		}
	}
	componentDidMount() {
		this.refresh()
	}
	refresh = () => {
		const me = this;
		const { myFetch, dispatch, actions: { logout }, routerInfo: { routeData, curKey } } = me.props
		const { pathName } = routeData[curKey]
		Toast.loading('Loading...', 0);
		const body = {
			enquiryId: me.enquiryId
		}
		myFetch('getEnquiryDetail', body).then(({ success, data, code, message }) => {
			Toast.hide()
			if (success) {
				const {
					endUserId,
					enquiryUserId,
					enquiryDiff,
					enquiryUserName,
					answerUserName,
					levelDiff,
					createTime,
					enquiryTitle,
					enquiryContent,
					imageInfoList,
					voiceInfoList,
					answerList,
					otherAccessoryList } = data
				var _data = {};
				switch (me.state.stateFlag) {
					case "0":
					case "1":
					case "2":
					case "3":
					case "4":
					case "5":
						_data = {
							stateFlag: endUserId ? "5" : me.state.stateFlag,
							answerUserName,
							levelDiff,
							enquiryDiff,
							enquiryTitle,
							data: {
								enquiryUserId,
								enquiryUserName,
								createTime,
								enquiryContent,
								imageInfoList,
								voiceInfoList,
								otherAccessoryList
							},
							answerList
						}

						me.setState({ ..._data })
						break;
					default:
						break;
				}
			} else if (code === "3003" || code === "3004") {
				dispatch(logout(pathName))
			} else {
				Toast.offline(message, 2)
			}
		})

	}
	//有操作的
	replyShow = () => {
		this.setState({ visible: true })
		var root = document.getElementById("root");
		root.style.overflow = 'hidden';
	}
	replyHide = () => {
		this.setState({ visible: false })
		var root = document.getElementById("root");
		root.style.overflow = 'auto';
	}
	save = () => {
		this.refresh();
	}
	end = () => { //结束
		const me = this;
		const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = me.props
		const { pathName } = routeData[curKey]
		this.modal.alert = alert('结束问询', '确定结束么?', [
			{ text: '取消', onPress: () => console.log('cancel') },
			{
				text: '确定',
				onPress: () => new Promise((resolve) => {
					Toast.loading('Loading...', 0);
					var body = {
						enquiryId: me.enquiryId, //回复列表的id
					}
					myFetch('endEnquiry', body).then(({ success, message, data, code }) => {
						Toast.hide()
						resolve()
						if (success) {
							Toast.success('结束成功!', 1, () => {

								dispatch(goBack())
							});
						} else if (code === "3003" || code === "3004") {
							dispatch(logout(pathName))
						} else {
							Toast.offline(message, 2)
						}
					})
				}),
			},
		])
	}
	back = () => {
		this.history.go(-1)
	}
	render() {
		const me = this;
		const { answerUserName, enquiryDiff, stateFlag, answerList, visible, enquiryTitle, levelDiff, data } = this.state
		const firstFloorOptions = {
			floorHost: data.enquiryUserId,
			playFn: me.playFn,
			keyNames: {
				userId: "enquiryUserId",
				userName: "enquiryUserName",
				createTime: "createTime",
				contentText: "enquiryContent",
				contentVoiceList: "voiceInfoList",
				contentimageInfoList: "imageInfoList",
				otherAccessoryList: "otherAccessoryList"
			}
		}
		const floorOptions = {
			floorHost: data.enquiryUserId,
			playFn: me.playFn,
			keyNames: {
				userId: "answerUserId",
				userName: "answerUserName",
				createTime: "createTime",
				contentText: "answerContent",
				contentVoiceList: "voiceInfoList",
				contentimageInfoList: "imageInfoList",
				otherAccessoryList: "otherAccessoryList"
			}
		}
		const enquiryDiffToText = (enquiryDiff) => {
			let text = "";
			switch (enquiryDiff) {
				case "0":
					text = "技术交底"
					break;
				case "1":
					text = "关键工序管控"
					break;
				case "2":
					text = "非关键工序管控"
					break;
				case "3":
					text = "疑难问题"
					break;
				case "4":
					text = "互动"
					break;
				default:
					text = '未知类型'
					break;
			}
			return text
		}
		const levelDiffToText = (levelDiff) => {
			let text = "";
			switch (levelDiff) {
				case "1":
					text = "紧急"
					break;
				default:
					text = ""
					break;
			}
			return text

		}
		return (
			<div className={`${styles['lny-page']} Answer`} >
				<div className={styles['lny-body']}>
					<List
						className={`${styles['lny-color-white-bg']} ${styles['lny-boxShadow']}`}
						renderHeader={
							() => {
								return (
									<div style={{ color: "#333", lineHeight: '1.5', fontSize: "16px" }} >
										{levelDiff === "1" ? <span style={{ color: "#ff4000" }}>{`${levelDiffToText(levelDiff)} · `}</span> : ""}
										<span style={{ color: "#0099dd" }}>{`${enquiryDiffToText(enquiryDiff)} · `}</span>
										{`${enquiryTitle}`}
									</div>
								)
							}
						}>
						<FloorItem
							{...this.props}
							index={0}
							data={data}
							options={firstFloorOptions}
							footerRender={() => {
								return (
									<div className={styles['lny-floorItem-footer']}>
										{`指定回答人：${answerUserName}`}
									</div>
								)
							}}
						/>
					</List>
					<WhiteSpace />
					<List
						className={`${styles['lny-color-white-bg']} ${styles['lny-boxShadow']}`}
						renderHeader={
							() => {
								return (
									<div style={{ fontWeight: "bold", color: "#333" }}>{"全部回答"}</div>
								)
							}
						}
					>
						{
							answerList && answerList.length > 0 ?
								answerList.map((item, index) => {
									return <FloorItem {...this.props} key={index} index={index + 1} data={item} options={floorOptions} />
								})
								: <div className={styles['lny-list-footer']}> {"尚未回复"}</div>
						}
					</List >
				</div >
				<Flex className={styles['lny-footer']} >
					{
						stateFlag === "0" || stateFlag === "1" || stateFlag === "2" || stateFlag === "3" || stateFlag === "4" ?
							<Flex.Item>
								<Button type="warning" onClick={this.end} >{"结束"}</Button>
							</Flex.Item>
							: ""
					}
					{
						stateFlag === "0" || stateFlag === "1" || stateFlag === "2" ?
							<Flex.Item>
								<Button type="primary" onClick={this.replyShow} >{"追问"}</Button>
							</Flex.Item>
							: ""
					}
					{
						stateFlag === "3" ?
							<Flex.Item>
								<Button type="primary" onClick={this.replyShow} >{"回答"}</Button>
							</Flex.Item>
							: ""
					}
					{
						stateFlag === "4" ?
							<Flex.Item>
								<Button type="primary" onClick={this.replyShow} >{"追答"}</Button>
							</Flex.Item>
							: ""
					}
					{
						stateFlag === "5" ?
							<Flex.Item>
								<Button type="warning" disabled  >{"已结束"}</Button>
							</Flex.Item>
							: ""
					}
					{/* {stateFlag === "0" || stateFlag === "1" || stateFlag === "2" || stateFlag === "3" || stateFlag === "4" || stateFlag === "5" ?
						<Flex.Item>
							<Button type="ghost" onClick={this.back} >{"返回"}</Button>
						</Flex.Item>
						: ""} */}
				</Flex >
				<EnquiryReplyDialog
					{...this.props}
					title={stateFlag === "0" || stateFlag === "1" || stateFlag === "2" ? "追问" : (stateFlag === "3" ? "回答" : (stateFlag === "4" ? "追答" : ""))}
					enquiryId={this.enquiryId}
					save={this.save}
					visible={visible}
					replyHide={this.replyHide}
				/>
			</div >
		)
	}
}
export default EnquiryDetail;