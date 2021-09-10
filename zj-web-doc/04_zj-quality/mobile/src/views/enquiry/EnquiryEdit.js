import React, { Component } from 'react';
import { List, Picker, InputItem, Icon, TextareaItem, Modal, ImagePicker, Toast, Button, Flex } from 'antd-mobile';
import SvgIcon from '../common/svgIcon';
import Record from './record'
import styles from './EnquiryEdit.less';
import Upload from 'rc-upload';
import MyStorage from "../../lny_modules/Storage"
import { goBack } from 'connected-react-router';
const alert = Modal.alert;
class EnquiryEdit extends Component {
	constructor(props) {
		super(props);
		this.serverId = "";//服务上的id
		this.enquiryState = 0
		this.modal = {}
		let { enquiryDiff } = this.props.match.params

		this.storageName = "enquiryDiff" + enquiryDiff
		let storageName_otherAccessoryList = MyStorage.getItem(`${this.storageName}_otherAccessoryList`)
		let otherAccessoryList = storageName_otherAccessoryList ? storageName_otherAccessoryList.data || [] : []
		this.enquiryDiff = enquiryDiff
		this.yuyinIcons = [
			'yuyin1',
			"yuyin2",
			"yuyin3"
		]
		this.state = {
			recordType: null,
			yuyinIcon: this.yuyinIcons[2],//语音图标   
			enquiryTitle: "", //标题
			enquiryContent: "", //识别后最终的内容
			recordTitle: "",
			files: [],//临时图片
			localIds: null, //图片id
			localId: null,//语音id
			selectPerson: [],//问询对象
			person: [],//问询对象
			selectPriority: [
				{ value: '2', label: '普通' },//储存优先级别
				{ value: '1', label: '紧急' }],
			levelDiff: [],//优先级
			otherAccessoryList
		}
	}
	onChangeTitle = (enquiryTitle) => {//改变标题
		this.setState({ enquiryTitle });
	}
	onChangeContent = (enquiryContent) => {//改变内容
		this.setState({ enquiryContent });
	}

	onChangePriority = (levelDiff) => {//改变优先级
		this.setState({ levelDiff });//要传对象
	}
	onChangePerson = (person) => {//改变问询对象
		this.setState({ person });
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
		const { myPublic: { wx } } = this.props
		var fileImg = [];
		for (const item of files) {
			fileImg.push(item.url);
		}
		wx.previewImage({
			current: fileImg[index], // 
			urls: fileImg // 
		});
	}
	onOpenRecord = (recordType) => {//开启录音
		const me = this
		const { myPublic: { wx } } = this.props
		wx.checkJsApi({
			jsApiList: ['startRecord', 'onVoiceRecordEnd', 'stopRecord', 'uploadVoice', 'translateVoice'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
			success: function (res) {
				if (res.errMsg === "checkJsApi:ok") {
					me.setState({ recordType })
				} else {
					this.modal.alert = alert(res.errMsg, "")
				}

				// 
				// 以键值对的形式返回，可用的api值true，不可用为false
				// 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
			}
		})
	}
	recordAbort = () => {//录音终止
		this.setState({ recordType: null })
	}
	identifyRecordEnd = (identifyContent) => {//识别录音结束
		const { enquiryContent } = this.state
		this.setState({ enquiryContent: (enquiryContent.toString() + identifyContent.toString()), recordType: null });
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
	componentDidMount() {
		const me = this;
		const { myFetch, myPublic: { wx }, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = me.props
		const { pathName } = routeData[curKey]
		wx.onVoicePlayEnd({
			success: function (res) {
				const { localId } = res
				if (me.state.localId === localId) {
					me.playVoiceAni(false);
				}
			}
		});
		myFetch('getOtherOrgMemberList').then(({ data, success, code, message }) => {
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
			} else {
				Toast.offline(message, 2)
			}
		})
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
			<div className={`${styles['lny-page']} QuestionSubmit`}>
				<div className={styles["lny-body"]}>
					<List className={styles["lny-boxShadow"]}>
						<InputItem labelNumber={3} value={this.state.enquiryTitle} onChange={this.onChangeTitle} placeholder="请输入标题"><span style={{ color: "red" }}>*</span>标题：</InputItem>
						<div className="textareaBox">
							<TextareaItem labelNumber={3} title={<span><span style={{ color: "red" }}>*</span>描述：</span>} count={1000} value={this.state.enquiryContent} onChange={this.onChangeContent} rows={5} placeholder="请输入问题描述" />
							<div className="vocieIcon" onClick={() => {
								this.onOpenRecord("识别")
							}}>
								<SvgIcon type={'yuyin'} />
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
							<div className="vocieIcon"
								onClick={() => {
									if (this.state.localId) {
										this.onRemoveVoice()
									} else {
										this.onOpenRecord("上传")
									}

								}}>
								{(!this.state.localId ?
									<SvgIcon type={'yuyin'} />
									: <SvgIcon type={'cancel'} />)}
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
						<Picker data={this.state.selectPriority} cols={1} value={this.state.levelDiff} onChange={this.onChangePriority}>
							<List.Item arrow="horizontal"><span style={{ color: "red" }}>*</span>优先级：</List.Item>
						</Picker>
						<Picker data={this.state.selectPerson} cols={2} value={this.state.person} onChange={this.onChangePerson}>
							<List.Item arrow="horizontal"><span style={{ color: "red" }}>*</span>指定人：</List.Item>
						</Picker>
						<UploadTest {...this.props} storageName={this.storageName} fileList={this.state.otherAccessoryList} onChange={this.otherChange} >
							<List.Item arrow="horizontal" extra={"请选择"}>其他附件：</List.Item>
						</UploadTest>
					</List>
				</div>
				<Flex className={styles["lny-footer"]}>
					<Flex.Item><Button type="ghost" onClick={this.abort} >{"放弃提问"}</Button></Flex.Item>
					<Flex.Item><Button type="primary" onClick={this.submit}>{"提交问题"}</Button></Flex.Item>
				</Flex>
				<Record {...this.props} recordType={this.state.recordType} recordAbort={this.recordAbort} uploadRecordEnd={this.uploadRecordEnd} identifyRecordEnd={this.identifyRecordEnd} />
			</div>
		)
	}
	abort = () => {
		const { dispatch } = this.props
		this.modal.alert = alert('您要放弃提问么？', "已填写的数据不做保留", [
			{ text: '取消', style: { color: "#333" } },
			{
				text: '放弃', onPress: () => {
					MyStorage.removeItem(`${this.storageName}_otherAccessoryList`).then(() => {
						dispatch(goBack())
					})
				}
			},
		])
	}
	submit = () => {//提交
		const me = this;
		const { serverId, enquiryDiff, storageName } = me;
		const { myFetch, dispatch, myPublic: { wx }, actions: { logout }, routerInfo: { routeData, curKey } } = me.props
		const { pathName } = routeData[curKey]
		const { enquiryTitle, enquiryContent, levelDiff, person, files, otherAccessoryList } = me.state;

		let isClick = true;
		otherAccessoryList.forEach(item => {
			if (!item.url) {
				isClick = false
			}
		});
		if (!isClick) {
			return this.modal.alert = alert("还有文件正在上传。", "");
		}
		if (!enquiryTitle) {
			return this.modal.alert = alert("请填写标题。", "");
		}
		if (!levelDiff[0]) {
			return this.modal.alert = alert("请选择优先级。", "");
		}
		if (!person[1]) {
			return this.modal.alert = alert("请选择指定回答人。", "");
		}
		if (!enquiryContent && !serverId && files.length === 0) {
			return this.modal.alert = alert("您没有任何问题信息可以提交。", "");
		}

		this.modal.alert = alert('提交问询', '确定提交么?', [
			{ text: '取消', style: { color: "#333" } },
			{
				text: '确定',
				onPress: () => new Promise((resolve, reject) => {
					wx.getLocation({
						type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
						success: function (res) {
							me.hasLocation = true;
							me.latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
							me.longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。

							var imageInfoList = [];
							for (const item of files) {
								imageInfoList.push({
									isWeChatId: 1,
									mediaId: item.serverId
								})
							}
							const body = {
								latitude: me.latitude,
								longitude: me.longitude,
								draftFlag: 0,
								enquiryTitle,//问询标题
								enquiryContent,//问询内容
								answerOrgId: person[0],//指定回答者部门Id
								answerUserId: person[1],//指定回答者Id
								enquiryDiff,//问询类型区分
								levelDiff: levelDiff[0],//问询优先级别
								imageInfoList,//问询图片
								voiceInfoList: serverId ? [{ isWeChatId: 1, mediaId: serverId }] : [],//问询语音
								otherAccessoryList,
							}
							Toast.loading('Loading...', 0);
							myFetch('addEnquiry', body).then(({ success, data, message, code }) => {
								if (success) {
									Toast.success('提交成功!', 1, () => {
										MyStorage.removeItem(`${storageName}_otherAccessoryList`).then(() => {
											resolve()
											dispatch(goBack())
										})
									});
								} else if (code === "3003" || code === "3004") {
									dispatch(logout(pathName))
								} else {
									Toast.offline(message, 2)
								}
							})
						},
						fail: function (res) {
							this.modal.alert = alert('获取地理位置信息失败！', "")
						}
					});
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





export default EnquiryEdit;