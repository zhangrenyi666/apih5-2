import React, { Component } from "react";
import { NavBar, Icon } from "antd-mobile";
import s from './style.less';
import MyList from "../modules/MEList";
import { push } from 'react-router-redux';
import { Divider, Tag } from 'antd';
import moment from 'moment';
class index extends Component {
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}ScientificList/0`));
                        }}
                    >
                        {"发布列表"}
                    </NavBar>
                </div>
                <div style={{ height: document.documentElement.clientHeight - 45, width: '100%' }}>
                    <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        searchKey="title"
                        fetchConfig={{
                            apiName: 'getZjSjConsultScientificInformationList',
                            otherParams: {
                                searchType: '2'
                            }
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
                                        if(item.technologicalAchievementsName){
                                            dispatch(push(`${mainModule}detailFBCG/${item.infoId}`));
                                        }else{
                                            dispatch(push(`${mainModule}detailFBHY/${item.infoId}`));
                                        }
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topc}>
                                            {item.technologicalAchievementsName === '专利' ? item.patentName : item.title}
                                        </div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>
                                            <Tag color={'green'}>
                                                {item.technologicalAchievementsName ? item.technologicalAchievementsName : item.industryScientificInfoName}
                                            </Tag>
                                        </div>
                                        <div className={s.topr}>{item.releaseTime ? moment(item.releaseTime).format('YYYY-MM-DD') : null}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>
                                            {item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.split(',').pop() : item.deptName}：{item.userName}
                                        </div>
                                        <div className={s.topr}>阅读次数：{item.readNum}</div>
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