import React, { Component } from "react";
import MyList from "../modules/MList";
import { Divider } from "antd";
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import moment from 'moment';
import { push } from 'react-router-redux';
class Index extends Component {
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root} id='root'>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}Home`));
                        }}
                    >
                        {"审批列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: document.documentElement.clientHeight - 45
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        searchKey="proposalName"
                        fetchConfig={{
                            apiName: 'getZjLabourUnionProposalListByChairman',
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    onClick={() => {
                                        if(item.feedbackStatus === '11'){
                                            dispatch(push(`${mainModule}ChairmanOfTheCompanyUnionEdit/${item.proposalId}`));
                                        }else{
                                            dispatch(push(`${mainModule}ChairmanOfTheCompanyUnionDetail/${item.proposalId}`));
                                        }
                                    }}
                                >
                                    <div style={{ paddingLeft: '3%' }}>
                                        提案名称：{item.proposalName}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>所属类别：{item.belongTypeName}</div>
                                        <div className={s.topr}>反馈状态：{item.feedbackStatusName}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>提案人：{item.proposer}</div>
                                        <div className={s.topr}>提案日期：{moment(item.proposalDate).format('YYYY-MM-DD')}</div>
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
export default Index;