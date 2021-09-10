import React, { Component } from 'react';
import s from "./style.less";
import MyList from "../modules/MList";
import { Flex, Icon, List, NavBar } from 'antd-mobile';
import { push } from 'react-router-redux';
import { goBack } from 'connected-react-router';
import moment from 'moment';
const { Item } = List;
const { Brief } = Item;
class Index extends Component {
    render() {
        const { dispatch, myPublic: { domain, appInfo: { mainModule } } } = this.props;

        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(goBack());
                    }}
                >
                    {"审批管理"}
                </NavBar>
                <div style={{ height: document.documentElement.clientHeight - 45 }}>
                    <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        fetchConfig={{
                            apiName: 'getZjMeetingPlanFallInInfoListForWechat'
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.item}
                                    key={index}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}worldYouthMeetingDetail/${item.fallInDeptId}/${item.fallInYear}/${item.approvalState}`));
                                    }}
                                >
                                    <Flex className={s.itemHeader}>
                                        <Flex.Item>{item.fallInYear}</Flex.Item>
                                    </Flex>
                                    <div className={s.itemBody}>
                                        <Flex wrap="wrap" align="center" alignContent="center" style={{ color: "#666", fontSize: '13px' }}>
                                            <Flex.Item>主办部门：{item.oaUserName}</Flex.Item>
                                            <div className={s.itemBodyArr}>{
                                                <Icon style={{ color: '#bbb', width: 25, height: 25 }} type={'right'} />
                                            }</div>
                                        </Flex>
                                    </div>
                                    <Brief style={{ borderTop: '1px solid #e8e8e8', paddingTop: '5px', }}>
                                        <Flex>
                                            <Flex.Item style={{ fontSize: '13px' }}>
                                                <span style={{ marginRight: '10px' }}>填报人：{item.fallInUser}</span>
                                            </Flex.Item>
                                            <div style={{ textAlign: "right", fontSize: '13px' }}>填报时间：{item.fallInTime ? moment(item.fallInTime).format("YYYY-MM-DD HH:mm:ss") : ''}</div>
                                        </Flex>
                                    </Brief>
                                </div>
                            );
                        }}
                    />
                </div>
            </div>
        )
    }
}
export default Index