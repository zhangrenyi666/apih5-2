/* alreadyAnswered.js */
import React, { Component } from 'react';
import { SearchBar, WingBlank, WhiteSpace, ListView, PullToRefresh, Badge } from 'antd-mobile';
import styles from './EnquiryList.less';
import moment from 'moment';
import { push } from 'connected-react-router';

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



class EnquiryList extends Component {
	constructor(props) {
		super(props);
		const dataSource = new ListView.DataSource({
			rowHasChanged: (row1, row2) => row1 !== row2,
		});
		this.stateFlag = this.props.match.params.stateFlag;
		this.toSearchKeywords = "";//将要搜索的Keywords
		this.initData = [];//初始化数据
		this.state = {
			refreshing: false,
			searchKeywords: this.toSearchKeywords,// 搜索关键词
			dataSource: dataSource.cloneWithRows(this.initData),
		};
	}
	manuallyRefreshFun = (otherObj) => {//手动刷新方法
		if (!otherObj) { otherObj = {} }
		this.manuallyRefresh = true;
		this.setState({ ...otherObj, refreshing: true });
	}
	componentWillUpdate(nextProps, nextState) {//判断是否为手动刷新   
		if (nextState.refreshing && this.manuallyRefresh) {
			this.manuallyRefresh = false
			this.onRefresh()
		}
	}
	// 搜索组件改变回调
	onSearchChange = (keywords) => {
		if (this.toSearchKeywords !== keywords) {
			this.toSearchKeywords = keywords;
			this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
		}
	};
	// 第一次渲染后调用，只在客户端
	componentDidMount() {
		this.manuallyRefreshFun({ searchKeywords: this.toSearchKeywords })
	}
	// 刷新列表
	onRefresh = (value) => {
		const me = this;
		const { stateFlag } = me;
		const { myFetch, actions: { logout }, routerInfo: { routeData, curKey }, dispatch } = me.props
		const { pathName } = routeData[curKey]
		const body = {
			stateFlag,// 问询状态
			enquiryTitle: this.toSearchKeywords,// 搜索条件-问询标题
			pageSize: '99999'
		}

		myFetch('getUserStateList', body).then(({ success, data, code }) => {
			if (success) {
				this.initData = data || [];
				me.setState({
					dataSource: this.state.dataSource.cloneWithRows(this.initData),
					refreshing: false,
				});
			} else if (code === "3003" || code === "3004") {
				dispatch(logout(pathName))
			}

		})
	};
	componentWillUnmount = () => {
		this.setState = (state, callback) => {
			return;
		};
	}
	render() {
		const me = this;
		const { refreshing, searchKeywords, dataSource } = this.state
		const { dispatch, routerInfo: { routeData, curKey } } = this.props
		const { moduleName } = routeData[curKey]
		const row = (rowData, sectionID, rowID) => {
			if (rowData.errMsg) {
				return <div key={rowID} style={{ textAlign: "center", lineHeight: "1rem", color: "#999" }}>{rowData.errMsg}</div>
			} else {
				return (
					<div key={rowID} onClick={() => {
						dispatch(push(`${moduleName}enquiry/detail/${rowData.enquiryId}/${me.stateFlag}`))
					}}>
						<WingBlank size="sm">
							<WhiteSpace size="sm" />
							<div className="view" style={{ borderColor: (rowID % 2 === 0) ? "#FF3F01" : "#FFAF02" }}>
								<div className="title">
									{rowData.levelDiff === "1" ?
										<Badge text={"紧急"}
											style={{
												marginLeft: 12,
												padding: '0 .03rem',
												backgroundColor: '#ff4000',
												borderRadius: '.02rem',
												color: '#fff',
												border: '.01rem solid #ff4000',
											}}
										/> : ""}
									{`【${rowData.enquiryTitle}】`}
									{(m => {
										var m_title
										switch (m) {
											case "0":
												m_title = "技术交底"
												break;
											case "1":
												m_title = "关键工序管控"
												break;
											case "2":
												m_title = "非关键工序管控"
												break;
											case "3":
												m_title = "疑难问题"
												break;
											case "4":
												m_title = "互动"
												break;
											default:
												m_title = '未知类型'
												break;
										}
										return (
											<Badge text={m_title}
												style={{
													marginLeft: 12,
													padding: '0 .03rem',
													backgroundColor: 'transparent',
													borderRadius: '.02rem',
													color: '#0099dd',
													border: '.01rem solid #0099dd',
												}}
											/>
										)
									})(rowData.enquiryDiff)}
								</div>
								<div className="tool">
									<div className="span1">{"发起人：" + (rowData.enquiryUserName || "匿名")}</div>
									<div className="span2">{fromNow(rowData.createTime)}</div>
								</div>
							</div>
						</WingBlank>
					</div>
				)
			}
		}
		return (
			<div className={`EnquiryList ${styles["lny-page"]}`}>
				<SearchBar value={searchKeywords} placeholder="搜索标题" autoFocus onChange={this.onSearchChange} />
				<ListView
					className={styles["flex"]}
					pageSize={5}
					dataSource={dataSource}
					renderRow={row}
					pullToRefresh={
						<PullToRefresh
							distanceToRefresh={window.devicePixelRatio * 10}
							refreshing={refreshing}
							onRefresh={this.onRefresh}
							indicator={{ deactivate: '下拉刷新' }}
						/>
					}
					renderFooter={() => {
						return (
							<div className={styles["lny-list-footer"]}>
								{"没有更多了"}
							</div>)
					}}
				/>
			</div>
		);
	}
}

export default EnquiryList;