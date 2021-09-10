import React, { Component } from "react";
import { NavBar, Icon, Toast } from "antd-mobile";
import { push } from 'react-router-redux';
import s from "./style.less";
import { Divider } from 'antd';
import moment from 'moment';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            newsId: props.match.params.newsId || '',
            data: null
        }
    }
    componentDidMount() {
        const { newsId } = this.state;
        this.props.myFetch('getZjSjConsultNewsDetails', { newsId: newsId }).then(({ success, message, data }) => {
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
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { data } = this.state;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}News`));
                        }}
                    >
                        系统公告详情
                    </NavBar>
                    {data ? <div style={{ height: window.innerHeight - 45 }}>
                        <div style={{ fontSize: '20px', fontWeight: 'bold', textAlign: 'center', paddingTop: '10px' }}>{data.title}</div>
                        <div style={{ width: '100%', margin: '0 auto', overflow: 'hidden', fontSize: '16px', textAlign: 'center' }}>
                            发布者:{data.modifyUserName}&nbsp;&nbsp;&nbsp;{moment(data.releaseTime).format('YYYY-MM-DD HH:mm:ss')}
                        </div>
                        <div style={{ width: '90%', margin: '0 auto', overflow: 'hidden', paddingTop: '10px' }} dangerouslySetInnerHTML={{ __html: data.content }}></div>
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
                    </div> : null}
                </div>
            </div>
        );
    }
}

export default Index;
