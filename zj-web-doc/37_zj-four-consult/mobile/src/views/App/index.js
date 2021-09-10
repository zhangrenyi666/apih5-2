import React, { Component } from "react";
import s from './style.less';
import { push } from 'react-router-redux';
import { Grid, NoticeBar, Tabs, Carousel, Toast } from 'antd-mobile';
import MyList from "../modules/MAppList";
import moment from 'moment';
import { Divider, Tag } from 'antd';
class AppPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: props.loginAndLogoutInfo.loginInfo.userInfo.ext2 === '1' ? [
                {
                    icon: require('../../imgs/zxzx.svg'),
                    text: '在线咨询',
                    link: 'OnlineConsultationList/0'
                },
                {
                    icon: require('../../imgs/jsfw.svg'),
                    text: '技术服务',
                    link: 'TechnicalServices/0'
                },
                {
                    icon: require('../../imgs/zjjs.svg'),
                    text: '专家介绍',
                    link: 'ExpertIntroduction/0'
                },
                {
                    icon: require('../../imgs/kjxx.svg'),
                    text: '科技信息',
                    link: 'ScientificList/0'
                },
                {
                    icon: require('../../imgs/tjfx.svg'),
                    text: '统计分析',
                    link: 'StatisticalList'
                }
            ] : [
                {
                    icon: require('../../imgs/zxzx.svg'),
                    text: '在线咨询',
                    link: 'OnlineConsultationList/0'
                },
                {
                    icon: require('../../imgs/jsfw.svg'),
                    text: '技术服务',
                    link: 'TechnicalServices/0'
                },
                {
                    icon: require('../../imgs/zjjs.svg'),
                    text: '专家介绍',
                    link: 'ExpertIntroduction/0'
                },
                {
                    icon: require('../../imgs/kjxx.svg'),
                    text: '科技信息',
                    link: 'ScientificList/0'
                }
            ],
            tabs: [
                { title: '科技成果' },
                { title: '行业科技信息' },
                { title: '科研课题' },
            ],
            page: 0,
            CarouselData: [],
            todoData: {}
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.props.myFetch('getZjSjConsultHomeStatistics', {}).then((StatisticsObj) => {
            if (StatisticsObj.success) {
                this.setState({
                    todoData: StatisticsObj.data
                },() => {
                    this.props.myFetch('getZjSjConsultBannerList', {}).then((BannerObj) => {
                        if (BannerObj.success) {
                            this.setState({
                                CarouselData: BannerObj.data
                            });
                        } else {
                            Toast.fail(BannerObj.message);
                        }
                    });
                })
            } else {
                Toast.fail(StatisticsObj.message);
            }
        });
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { data, tabs, page } = this.state;
        return (
            <div className={s.root}>
                <div className={s.header}>
                    {this.state.CarouselData.length ? <Carousel
                        autoplay
                        infinite
                        autoplayInterval={5000}
                        dots={false}
                        style={{ height: '22vh' }}
                    >
                        {this.state.CarouselData.map((item, index) => (
                            <a
                                key={index}
                                href={item.clickLink ? item.clickLink : null}
                                target="_blank"
                                style={{ display: 'inline-block', width: '100%', height: '22vh' }}
                            >
                                <img
                                    src={item.imgUrl}
                                    alt=""
                                    style={{ width: '100%', height: '100%' }}
                                />
                            </a>

                        ))}
                    </Carousel> : null}
                </div>
                <div className={s.noticeBar}>
                    <NoticeBar mode="link" onClick={() => {
                        dispatch(push(`${mainModule}News`));
                    }}>
                        关于四公局技术服务咨询平台上线通知
                    </NoticeBar>
                </div>
                <div className={s.tabContent}>
                    <div className={s.tabSponsor} onClick={() => {
                        dispatch(push(`${mainModule}SponsorList`));
                    }}>
                        <div style={{ fontSize: '16px' }}>{this.state.todoData && this.state.todoData.sendNum ? this.state.todoData.sendNum : '0'}</div>
                        <div style={{ fontSize: '15px' }}>发起</div>
                    </div>
                    <div className={s.tabCommission} onClick={() => {
                        dispatch(push(`${mainModule}todoByzjSjTechnicalService`));
                    }}>
                        <div style={{ fontSize: '16px' }}>{this.state.todoData && this.state.todoData.toDoNum ? this.state.todoData.toDoNum : '0'}</div>
                        <div style={{ fontSize: '15px' }}>待办</div>
                    </div>
                    <div className={s.tabAccomplish} onClick={() => {
                        dispatch(push(`${mainModule}hasTodoByzjSjTechnicalService`));
                    }}>
                        <div style={{ fontSize: '16px' }}>{this.state.todoData && this.state.todoData.finishNum ? this.state.todoData.finishNum : '0'}</div>
                        <div style={{ fontSize: '15px' }}>完成</div>
                    </div>
                </div>
                <div className={s.gridContent}>
                    <Grid data={data} itemStyle={{ height: 90 }} columnNum={3} onClick={(obj) => {
                        dispatch(push(`${mainModule}${obj.link}`));
                    }} />
                </div>
                <div className={s.tabsBarContent}>
                    <Tabs
                        tabs={tabs}
                        page={page}
                        swipeable={false}
                        onTabClick={(_, page) => {
                            this.setState({ page: page })
                        }}
                    >
                        <div style={{ width: '100%', height: 'calc(78vh - 333px)' }}>
                            {
                                page === 0 ? <MyList
                                    myFetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    fetchConfig={{
                                        apiName: 'getZjSjConsultScientificInformationList',
                                        otherParams: {
                                            technologyIndustryFlag: '0',
                                            searchType: '0'
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
                                                    dispatch(push(`${mainModule}detailCGApp/${item.infoId}`));
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
                                                            {item.technologicalAchievementsName}
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
                                /> : null
                            }
                        </div>
                        <div style={{ width: '100%', height: 'calc(78vh - 333px)' }}>
                            {
                                page === 1 ? <MyList
                                    myFetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    fetchConfig={{
                                        apiName: 'getZjSjConsultScientificInformationList',
                                        otherParams: {
                                            technologyIndustryFlag: '1',
                                            searchType: '0'
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
                                                    dispatch(push(`${mainModule}detailHYApp/${item.infoId}`));
                                                }}
                                            >
                                                <div className={s.top}>
                                                    <div className={s.topc}>
                                                        {item.title}
                                                    </div>
                                                </div>
                                                <Divider style={{ margin: "8px 0px" }} />
                                                <div className={s.top}>
                                                    <div className={s.topl}>
                                                        <Tag color={'green'}>
                                                            {item.industryScientificInfoName}
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
                                /> : null
                            }
                        </div>
                        <div style={{ width: '100%', height: 'calc(78vh - 333px)' }}>
                            {
                                page === 2 ? <MyList
                                    myFetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    fetchConfig={{
                                        apiName: 'getZjSjConsultScientificResearchTopicList'
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
                                                    dispatch(push(`${mainModule}detailKTApp/${item.topicId}`));
                                                }}
                                            >
                                                <div className={s.top}>
                                                    <div className={s.topc}>
                                                        {item.topicName}
                                                    </div>
                                                </div>
                                                <Divider style={{ margin: "8px 0px" }} />
                                                <div className={s.top}>
                                                    <div className={s.topl}>
                                                        <Tag color={'green'}>
                                                            {item.topicTypeName}
                                                        </Tag>
                                                    </div>
                                                    <div className={s.topr}>{item.topicNo}</div>
                                                </div>
                                                <Divider style={{ margin: "8px 0px" }} />
                                                <div className={s.top}>
                                                    <div className={s.topl}>
                                                        {item.responsiblePerson}
                                                    </div>
                                                    <div className={s.topr}>
                                                        {item.contactMode}
                                                    </div>
                                                </div>
                                            </div>
                                        );
                                    }}
                                /> : null
                            }
                        </div>
                    </Tabs>
                </div>
            </div >
        );
    }
}

export default AppPage;
