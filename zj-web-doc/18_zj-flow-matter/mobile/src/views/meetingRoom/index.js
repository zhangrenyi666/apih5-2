import React, { Component } from 'react';
import s from './style.less';
import { Toast, NavBar, PickerView, Modal, Icon } from 'antd-mobile';
import { Spin, Calendar } from 'antd'
import { push } from 'react-router-redux';
import { createForm } from 'rc-form';
import moment from 'moment';
import 'moment/locale/zh-cn';
moment.locale('zh-cn');
function closest(el, selector) {
    const matchesSelector = el.matches || el.webkitMatchesSelector || el.mozMatchesSelector || el.msMatchesSelector;
    while (el) {
        if (matchesSelector.call(el, selector)) {
            return el;
        }
        el = el.parentElement;
    }
    return null;
}
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            season: [],
            recordid: [''],
            loading: false,
            show: false,
            value: ['选择会议室'],
            date: moment(new Date()).format('YYYY-MM-DD')
        }
    }
    componentDidMount() {
        this.loadTherrs();
    }
    loadTherr() {
        this.setState({
            loading: true
        })
        const { myFetch } = this.props
        let body = {
            recordid: this.state.recordid[0],
            years: this.state.date
        }
        myFetch("getZjMeetingRoomSituationListByDate", body).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data: data,
                    loading: false,
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    loadTherrs() {
        const { myFetch } = this.props
        myFetch("getMeetingRoomAllList", {}).then(({ success, message, data }) => {
            if (success) {
                let season = [];
                data.map((item, index) => {
                    season.push({
                        label: item.meetingRoomName,
                        value: item.recordid,
                        key: index
                    })
                    return item;
                })
                this.setState({
                    season
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    onPanelChange = (value) => {
        this.setState({
            date: moment(value).format('YYYY-MM-DD')
        }, () => {
            this.loadTherr();
        })
    }
    handleClick = () => {
        this.setState({
            show: true
        })
    }
    onChange = (recordid) => {
        this.setState({
            recordid
        })
    }
    onWrapTouchStart = (e) => {
        if (!/iPhone|iPod|iPad/i.test(navigator.userAgent)) {
            return;
        }
        const pNode = closest(e.target, '.am-modal-content');
        if (!pNode) {
            e.preventDefault();
        }
    }
    render() {
        const { value, recordid, season, data } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}`));
                        }}
                    >{"会议室情况"}</NavBar>
                </div>
                <div className={s.calendar} style={{ margin: 'auto', marginTop: '15%', width: '95%', border: '1px solid #d9d9d9', borderRadius: 4 }}>
                    <div className={s.meeting} onClick={this.handleClick}>{value[0]}</div>
                    <Calendar fullscreen={false} onChange={this.onPanelChange} mode='month' />
                </div>
                <Spin spinning={this.state.loading}>
                    <div className={s.center}>
                        <div className={s.centerl}>
                            <div className={s.centerl_l}>上午:</div>
                            <div className={s.centerl_r}>
                                {
                                    data && data.morning && data.morning[0] && data.morning[0] != '' ? data.morning[0].split('&').map((item) => {
                                        return <div>{item}</div>
                                    }) : '暂无数据...'
                                }
                            </div>
                        </div>
                        <div className={s.centerr}>
                            <div className={s.centerr_l}>下午:</div>
                            <div className={s.centerr_r}>
                                {
                                    data && data.afternoon && data.afternoon[0] && data.afternoon[0] != '' ? data.afternoon[0].split('&').map((item) => {
                                        return <div>{item}</div>
                                    }) : '暂无数据...'
                                }
                            </div>
                        </div>
                    </div>
                </Spin>
                <div>
                    <Modal
                        visible={this.state.show}
                        transparent
                        maskClosable={false}
                        onClose={() => {
                            this.setState({
                                show: false
                            })
                        }}
                        title="选择会议室"
                        footer={[{
                            text: '确定', onPress: () => {
                                this.setState({
                                    show: false
                                }, () => {
                                    if (this.state.recordid[0] == '') {
                                        this.setState({
                                            value: ['请选择会议室']
                                        }, () => {
                                            this.loadTherr();
                                        })
                                    } else {
                                        const { myFetch } = this.props
                                        myFetch("getZjMeetingRoomList", { recordid: recordid[0] }).then(({ success, message, data }) => {
                                            if (success) {
                                                this.setState({
                                                    value: [data[0].meetingRoomName]
                                                }, () => {
                                                    this.loadTherr();
                                                })
                                            } else {
                                                Toast.fail(message)
                                            }
                                        })
                                    }
                                })
                            }
                        }]}
                        wrapProps={{ onTouchStart: this.onWrapTouchStart }}
                    >
                        <div>
                            <PickerView
                                value={recordid}
                                onChange={this.onChange}
                                data={season}
                                cascade={false}
                            />
                        </div>
                    </Modal>
                </div>
            </div>
        )
    }
}
const Indexs = createForm()(Index);
export default Indexs;