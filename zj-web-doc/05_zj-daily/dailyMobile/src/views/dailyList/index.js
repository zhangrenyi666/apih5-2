import React, { Component } from 'react';
import { Form, Divider } from 'antd';
import Mlist from '../modules/MList';
import s from "./style.less";
import moment from 'moment';
import { push } from 'react-router-redux';
import { NoticeBar, Toast } from 'antd-mobile';
class Page extends Component {
    constructor() {
        super();
        this.state = {
            data: {}
        }
    }
    componentDidMount() {
        this.props.myFetch('zjDailyProFillRoll', { fillDate: moment(new Date()).valueOf() }).then(({ success, data, message }) => {
            if (success) {
                this.setState({
                    data: data
                })
            } else {
                Toast.fail(message);
            }
        });
    }
    render() {
        const { data } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <NoticeBar marqueeProps={{ loop: true, style: { padding: '0 7.5px' } }}>
                    {data ? `${data.fillSize}个投资项目${moment(data.fillDate).format('YYYY年MM月DD日')}工班日收到现金${(data.cashTotal / 10000).toFixed(2)}万元，非现金${(data.unCash / 10000).toFixed(2)}万元，合计金额${(data.cashAllTotal / 10000).toFixed(2)}万元，收费站入口车次${data.entrTotal}辆，出口车次${data.exportTotal}辆，合计车次${data.allTotal}辆，绿通车${data.green1}辆，减免金额${(data.green2 / 10000).toFixed(2)}万元` : null}
                </NoticeBar>
                <div style={{ height: 'calc(100vh - 36px)' }}>
                    <Mlist
                        myFetch={this.props.myFetch}
                        searchKey="proName"
                        fetchConfig={{
                            apiName: 'getZjDailyProFillListForMobile'
                        }}
                        onRef={this.onRef}
                        Item={(props) => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    style={{
                                        borderLeft: `3px solid ${index % 2 === 0 ? "#ff4000" : "#ff9900"}`
                                    }}
                                    key={index}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}DailyList/${item.proFillId}`));
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.proName}</div>
                                        <div className={s.topr}>{item.tollStation}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.ext4}</div>
                                        <div className={s.topr}>{moment(item.fillDate).format('YYYY-MM-DD')}</div>
                                    </div>
                                </div>
                            );
                        }}
                    />
                </div>
            </div>)
    }
}
export default Form.create()(Page);