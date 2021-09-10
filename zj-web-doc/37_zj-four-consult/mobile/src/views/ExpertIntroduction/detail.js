import React from "react";
import { NavBar, Icon, Toast } from "antd-mobile";
import { push } from "react-router-redux";
import s from "./style.less";
import { Avatar, Divider, Tag } from 'antd';
class Demo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            itemId: props.match.params.itemId || '',
            expertId: props.match.params.expertId || '',
            data: null
        };
    }
    componentDidMount() {
        this.props.myFetch('getZjSjConsultExpertDetails', { expertId: this.state.expertId }).then(({ success, message, data }) => {
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
        const {
            dispatch,
            myPublic: { appInfo: { mainModule } },
        } = this.props;
        let { itemId, data } = this.state;
        return (
            <div className={s.root}>
                <NavBar
                    className={"w-hflow-nav"}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}ExpertIntroduction/${itemId}`));
                    }}
                >
                    专家详情
                </NavBar>
                {data ? <div style={{ height: document.documentElement.clientHeight - 45, overflowY:'auto', paddingBottom:'10px' }}>
                    <div className={s.header}>
                        <div className={s.headerl}>
                            <Avatar size={75} src={data.userHeadUrl ? data.userHeadUrl : require('../../imgs/userHeadUrl.svg')} />
                        </div>
                        <div className={s.headerr}>
                            <div>{data.userName}</div>
                            <div>{data.position}</div>
                        </div>
                    </div>
                    <div className={s.content}>
                        <div style={{ fontSize: '16px', fontWeight: 'bold' }}>基本信息</div>
                        <div className={s.contentc} style={{marginTop:'5px'}}>
                            <div className={s.contentcl}>手机号</div>
                            <div className={s.contentcr}>{data.phone}</div>
                        </div>
                        <Divider style={{ margin: "8px 0px" }} />
                        <div className={s.contentc}>
                            <div className={s.contentcl}>职务</div>
                            <div className={s.contentcr}>{data.postionsName}</div>
                        </div>
                        <Divider style={{ margin: "8px 0px" }} />
                        <div className={s.contentc}>
                            <div className={s.contentcl}>性别</div>
                            <div className={s.contentcr}>{data.sex === '0' ? '未知' : (data.sex === '1' ? '男' : '女')}</div>
                        </div>
                        <Divider style={{ margin: "8px 0px" }} />
                        <div className={s.contentc}>
                            <div className={s.contentcl}>年龄</div>
                            <div className={s.contentcr}>{data.age ? data.age : ''}</div>
                        </div>
                        <Divider style={{ margin: "8px 0px" }} />
                        <div className={s.contentc}>
                            <div className={s.contentcl}>邮箱</div>
                            <div className={s.contentcr}>{data.email}</div>
                        </div>
                        <Divider style={{ margin: "8px 0px" }} />
                        <div className={s.contentc}>
                            <div className={s.contentcl}>职称</div>
                            <div className={s.contentcr}>{data.position}</div>
                        </div>
                    </div>
                    <div className={s.content}>
                        <div style={{ fontSize: '16px', fontWeight: 'bold' }}>擅长领域</div>
                        <div>
                            {
                                data.areasOfExpertiseName ? data.areasOfExpertiseName.split(',').map((item, index) => {
                                    return (
                                        <Tag style={{marginTop:'5px'}} key={index} color={'green'}>
                                            {item}
                                        </Tag>
                                    )
                                }) : null
                            }
                        </div>
                    </div>
                    <div className={s.content}>
                        <div style={{ fontSize: '16px', fontWeight: 'bold' }}>专家简介</div>
                        <div style={{textIndent:'20px'}}>
                            {data.introduction}
                        </div>
                    </div>
                </div> : null}
            </div>
        );
    }
}

export default Demo;
