import React, { Component } from "react";
import MList from "../../modules/MList";
import s from "./index.less"
import { NavBar, Icon } from "antd-mobile"
import moment from 'moment';
import { push } from "react-router-redux";

class index extends Component {
    componentDidMount() {
        document.title = "月度计划进度";
    }
    render() {
        const {
            mainModule
        } = this.props.myPublic.appInfo;

        return (
            <div style={{ width: '100%', height: "100vh" }}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            // this.props.dispatch(push(`${mainModule}App`));
                            this.props.myPublic?.androidApi?.closeActivity?.() || this.props.dispatch(push(`${mainModule}App`));
                        }}
                    >
                        {"月度计划进度"}
                    </NavBar>
                </div>

                <div style={{
                    height: window.innerHeight - 45,
                }}>
                    <MList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        showSearch={false}
                        fetchConfig={{
                            apiName: 'getZjLzehMonthPlanProgressList',
                        }}
                        Item={(props) => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    onClick={() => {
                                        this.props.dispatch(
                                            push(`${mainModule}MonthlyScheduleDetail/${item.zjLzehMonthPlanProgressId}`)
                                        );
                                    }}
                                >
                                    <div className={s.top}>
                                        <div style={{fontWeight:700}}>月份</div>
                                        <div>计划月产值(万元)</div>
                                        <div>实际月产值(万元)</div>
                                    </div>
                                    <div className={s.top} >
                                        <div style={{fontWeight:700}}>{moment(item.month).format('YYYY-MM')}</div>
                                        <div>{item.planMonthOutValue}</div>
                                        <div>{item.monthOutValue}</div>
                                    </div>
                                </div>
                            );
                        }}
                    />

                </div>

            </div>
        );
    }
}
export default index;
