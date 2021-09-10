import React, { Component } from "react";
import { NavBar, Icon, Tabs, Modal as ModalMobile, Radio, List, Toast } from "antd-mobile";
import s from './style.less';
import MyListCG from "../modules/MCGList";
import MyListKJ from "../modules/MKJList";
import MyListKT from "../modules/MKTList";
import { push } from 'react-router-redux';
import { Divider, Tag, Avatar, Drawer } from 'antd';
import moment from 'moment';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: Number(props.match.params.page) || 0,
            season: props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '3' ? [
                {
                    label: '科技成果',
                    value: 0,
                },
                {
                    label: '行业科技信息',
                    value: 1,
                },
                {
                    label: '科研课题',
                    value: 2,
                },
                {
                    label: '我收藏的',
                    value: 3,
                },
                {
                    label: '我发布的',
                    value: 4,
                }
            ] : [
                {
                    label: '我收藏的',
                    value: 3,
                },
                {
                    label: '我发布的',
                    value: 4,
                }
            ],
            show: false,
            val: null,
            ext1: props.loginAndLogoutInfo.loginInfo.userInfo.ext1 || '',
            treeOpen: false
        }
    }
    onChange = (value) => {
        this.setState({
            val: value
        })
    }
    // onClose = () => {
    //     this.setState({
    //         treeBtn: false
    //     })
    // }
    render() {
        const { page, season, show, val, ext1, treeOpen } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0];
        const { realName, imageUrl } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}App`));
                        }}
                    >
                        {"科技信息列表"}
                    </NavBar>
                </div>
                <div style={{ height: document.documentElement.clientHeight - 45, width: '100%' }}>
                    <Tabs
                        tabs={[{ title: '科技成果' }, { title: '行业科技信息' }, { title: '科研课题' }]}
                        swipeable={false}
                        page={page}
                        onChange={(_, page) => {
                            this.setState({
                                page
                            })
                        }}
                    >
                        <div style={{ height: document.documentElement.clientHeight - 90, width: '100%' }}>
                            {page === 0 ? <MyListCG
                                loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                searchKey="title"
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
                                                dispatch(push(`${mainModule}detailCG/${item.infoId}`));
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
                            /> : null}
                        </div>
                        <div style={{ height: document.documentElement.clientHeight - 90, width: '100%' }}>
                            {page === 1 ? <MyListKJ
                                loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                searchKey="title"
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
                                                dispatch(push(`${mainModule}detailHY/${item.infoId}`));
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
                            /> : null}
                        </div>
                        <div style={{ height: document.documentElement.clientHeight - 90, width: '100%' }}>
                            {page === 2 ? <MyListKT
                                loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                searchKey="topicName"
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
                                                dispatch(push(`${mainModule}detailKT/${item.topicId}`));
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
                            /> : null}
                        </div>
                    </Tabs>
                </div>
                <div
                    style={{ position: 'fixed', bottom: 100, right: 35, background: '#eaecec', border: '1px solid #1890ff', borderRadius: '50%', padding: '5px' }}
                    onClick={() => {
                        this.setState({
                            show: true,
                            val: null
                        })
                    }}
                >
                    <Avatar size={35} src={require('../../imgs/fb.svg')} />
                </div>
                <div>
                    <ModalMobile
                        visible={show}
                        transparent
                        onClose={() => {
                            this.setState({
                                show: false
                            })
                        }}
                        title='选择类型'
                        footer={[{
                            text: '取消', onPress: () => {
                                this.setState({
                                    show: false
                                })
                            }
                        }, {
                            text: '确定', onPress: () => {
                                if (val === 0) {
                                    this.setState({
                                        show: false
                                    }, () => {
                                        dispatch(push(`${mainModule}addCG`));
                                    })
                                } else if (val === 1) {
                                    this.setState({
                                        show: false
                                    }, () => {
                                        dispatch(push(`${mainModule}addHY`));
                                    })
                                } else if (val === 2) {
                                    this.setState({
                                        show: false
                                    }, () => {
                                        dispatch(push(`${mainModule}addKT`));
                                    })
                                } else if (val === 3) {
                                    this.setState({
                                        show: false
                                    }, () => {
                                        dispatch(push(`${mainModule}scList`));
                                    })
                                } else if (val === 4) {
                                    this.setState({
                                        show: false
                                    }, () => {
                                        dispatch(push(`${mainModule}fbList`));
                                    })
                                } else {
                                    Toast.fail('请点击圆框选择类型！');
                                }
                            }
                        }]}
                        wrapProps={{ onTouchStart: this.onWrapTouchStart }}
                        wrapClassName='ModalMobile'
                    >
                        <List>
                            {
                                season.map((item) => {
                                    return (
                                        <List.Item key={item.value}>
                                            <Radio checked={val === item.value} onChange={() => this.onChange(item.value)}>{item.label}</Radio>
                                        </List.Item>
                                    )
                                })
                            }
                        </List>
                    </ModalMobile>
                </div>
                {/* {!treeOpen ? <div
                    className={s.treeBtnRights}
                    onClick={() => {
                        this.setState({
                            treeOpen: true
                        });
                    }}
                >
                    <Icon
                        style={{ height: "100%" }}
                        type="right"
                        size="lg"
                    />
                </div> : <div
                    className={s.treeBtnLefts}
                    onClick={() => {
                        this.setState({
                            treeOpen: false
                        });
                    }}
                >
                        <Icon
                            style={{ height: "100%" }}
                            type="left"
                            size="lg"
                        />
                    </div>}
                <div>
                    <Drawer
                        title={
                            <div style={{ overflow: 'hidden' }}>
                                <div style={{ float: 'left' }}>
                                    <Avatar
                                        size={50}
                                        src={imageUrl ? imageUrl : require('../../imgs/userHeadUrl.svg')}
                                        alt=""
                                    />
                                </div>
                                <div style={{ float: 'left', paddingLeft: '5%' }}>
                                    <div>{realName}</div>
                                    <div>{companyName && companyName.indexOf(',') !== -1 ? companyName.split(',').pop() : companyName}</div>
                                </div>
                            </div>
                        }
                        placement="left"
                        closable={false}
                        onClose={this.onClose}
                        visible={treeOpen}
                    >
                        <p onClick={() => {
                            dispatch(push(`${mainModule}scList`));
                        }}>
                            <Avatar
                                size={30}
                                src={imageUrl ? imageUrl : require('../../imgs/sc.svg')}
                                alt=""
                            />
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            我收藏的
                        </p>
                        <p onClick={() => {
                            dispatch(push(`${mainModule}fbList`));
                        }}>
                            <Avatar
                                size={30}
                                src={imageUrl ? imageUrl : require('../../imgs/fb.svg')}
                                alt=""
                            />
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            我发布的
                        </p>
                    </Drawer>
                </div> */}
            </div>
        );
    }
}

export default index;