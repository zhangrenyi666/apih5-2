import React, { Component } from "react";
import MList from "../../modules/MList";
import s from "./index.less"
import { NavBar, Icon, Flex } from "antd-mobile"
import moment from 'moment';
import { push } from "react-router-redux";

class index extends Component {
    componentDidMount() {
        document.title = "年度计划进度";
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
                        {"年度计划进度"}
                    </NavBar>
                </div>

                <div style={{
                    height: window.innerHeight - 45,
                }}>
                    <Flex direction={'row'} align={'center'} style={{ padding: '6px 10px 0px 10px' }} justify={'between'}>
                        <div style={{ width: '30%', textAlign: 'center' }}>年度</div>
                        <div style={{ width: '30%', textAlign: 'center' }}>计划年产值(万元)</div>
                        <div style={{ width: '30%', textAlign: 'center' }}>实际年产值(万元)</div>
                    </Flex>
                    <MList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        showSearch={false}
                        fetchConfig={{
                            apiName: 'getZjLzehYearPlanProgressList',
                        }}
                        Item={(props) => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    style={{
                                        borderLeft: `3px solid ${index % 2 === 0 ? "#ff4000" : "#ff9900"}`,
                                    }}
                                    onClick={() => {
                                        this.props.dispatch(
                                            push(`${mainModule}AnnualScheduleDetail/${item.zjLzehYearPlanProgressId}`)
                                        );
                                    }}
                                >
                                    <Flex direction={'row'} align={'center'} style={{ padding: '6px 10px 6px 10px' }} justify={'between'}>
                                        <div style={{ width: '30%',fontWeight: 700, textAlign: 'center' }}>{moment(item.year).format('YYYY')}</div>
                                        <div style={{ width: '30%', textAlign: 'center' }}>{item.planYearOutValue}</div>
                                        <div style={{ width: '30%', textAlign: 'center' }}>{item.yearOutValue}</div>
                                    </Flex>
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
