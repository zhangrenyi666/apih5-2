import React, { Component } from "react";
import MList from "../modules/MList";
import s from "./index.less"
import { NavBar, Icon, Flex } from "antd-mobile"
import moment from 'moment';
import { push } from "react-router-redux";

class index extends Component {
    componentDidMount() {
        document.title = "生产目标任务计划";
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
                        {"生产目标任务计划"}
                    </NavBar>
                </div>

                <div style={{
                    height: window.innerHeight - 45,
                }}>
                    <Flex direction={'row'}  align={'center'} style={{ padding: '6px 10px 0px 10px' }} >
                        {/* <div style={{ width: '20%', textAlign: 'center' }}>序号</div> */}
                        <div style={{ width: '20%',textAlign: 'center' }}>月份</div>
                        <div style={{ width: '20%',textAlign: 'center' }}>任务数</div>
                        <div style={{ width: '30%', textAlign: 'center' }}>备注</div>
                    </Flex>
                    <MList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        showSearch={false}
                        fetchConfig={{
                            apiName: 'getZjLzehProduceTaskPlanList',
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
                                        borderLeft: `3px solid ${index % 2 === 0 ? "#ff4000" : "#ff9900"
                                            }`
                                    }}
                                    onClick={() => {
                                        this.props.dispatch(
                                            push(`${mainModule}ProductionPlanDetail/${item.zjLzehProduceTaskPlanId}`)
                                        );
                                    }}
                                >
                                    <Flex direction={'row'}  align={'center'} style={{ padding: '8px' }} >
                                        {/* <div style={{ width: '20%',fontWeight: 700, textAlign: 'center' }}>{item.sort}</div> */}
                                        <div style={{ width: '20%',textAlign: 'center' }}>{moment(item.month).format('YYYY-MM')}</div>
                                        <div style={{ width: '20%',textAlign: 'center' }}>{item.taskQty}</div>
                                        <div style={{ width: '30%', textAlign: 'center' }}>{item.remarks}</div>
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
