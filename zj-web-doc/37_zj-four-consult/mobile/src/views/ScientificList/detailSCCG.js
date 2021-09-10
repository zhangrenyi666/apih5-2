import React, { Component } from "react";
import { NavBar, Icon, Toast } from "antd-mobile";
import s from './style.less';
import { push } from 'react-router-redux';
import { Tag, Rate, Divider } from 'antd';
import moment from 'moment';
import { HeartOutlined } from '@ant-design/icons';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            infoId: props.match.params.infoId || '',
            data: null
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.props.myFetch('getZjSjConsultScientificInformationDetails', { infoId: this.state.infoId }).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data: data
                });
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
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}scList`));
                        }}
                    >
                        {"科技成果详情"}
                    </NavBar>
                    {data ? <div style={{ height: document.documentElement.clientHeight - 45, width: '100%', overflowY: 'auto', padding: '5% 0' }}>
                        <div style={{ fontSize: '18px', fontWeight: 'bold', textAlign: 'center' }}>{data.technologicalAchievementsName === '专利' ? data.patentName : data.title}</div>
                        <div style={{ textAlign: 'center' }}>
                            {data.deptName && data.deptName.indexOf(',') !== -1 ? data.deptName.split(',').pop() : data.deptName}：{data.userName}
                            &nbsp;&nbsp;&nbsp;&nbsp;{data.releaseTime ? moment(data.releaseTime).format('YYYY-MM-DD') : null}
                            &nbsp;&nbsp;&nbsp;&nbsp;阅读次数：{data.readNum}
                        </div>
                        <div style={{ textAlign: 'center' }}>
                            <Tag color={'green'}>
                                {data.technologicalAchievementsName}
                            </Tag>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <Rate count={1} style={{ fontSize: 25 }} allowHalf character={<HeartOutlined />} value={Number(data.favoriteId)} onChange={(value) => {
                                if (value === 1) {
                                    this.props.myFetch('addZjSjConsultFavorite', { ...data }).then(({ success, message, data }) => {
                                        if (success) {
                                            this.refresh();
                                        } else {
                                            Toast.fail(message);
                                        }
                                    });
                                } else if (value === 0) {
                                    this.props.myFetch('cancelZjSjConsultFavorite', { ...data }).then(({ success, message, data }) => {
                                        if (success) {
                                            this.refresh();
                                        } else {
                                            Toast.fail(message);
                                        }
                                    });
                                }
                            }} />
                        </div>
                        {data.technologicalAchievementsName === '专利' ? <div style={{ padding: '0 10px', fontSize: '16px' }}>
                            <div>专利号：{data.patentNo}</div>
                            <div>专利名称：{data.patentName}</div>
                            <div>专利类型：{data.patentTypeName}</div>
                            <div>授权时间：{moment(data.authorizationTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                            <div>申请单位：{data.applicant}</div>
                            <div>专利权人：{data.patentee}</div>
                            <div>附件：</div>
                            <div>
                                {
                                    data.attachmentList && data.attachmentList.length ? data.attachmentList.map((item, index) => {
                                        return (
                                            <div key={index} style={{ textAlign: 'center' }}>
                                                <a target='_blank' href={item.url}>{item.name}</a>
                                                <Divider style={{ margin: "8px 0px", height: '1px' }} />
                                            </div>
                                        )
                                    }) : null
                                }
                            </div>
                        </div> : <div>
                                <div style={{ textAlign: 'center' }} dangerouslySetInnerHTML={{ __html: data.content }}></div>
                                <div style={{ textAlign: 'center' }}>附件：</div>
                                <div>
                                    {
                                        data.attachmentList && data.attachmentList.length ? data.attachmentList.map((item, index) => {
                                            return (
                                                <div key={index} style={{ textAlign: 'center' }}>
                                                    <a target='_blank' href={item.url}>{item.name}</a>
                                                    <Divider style={{ margin: "8px 0px", height: '1px' }} />
                                                </div>
                                            )
                                        }) : null
                                    }
                                </div>
                            </div>}
                    </div> : null}
                </div>
            </div>
        );
    }
}

export default index;