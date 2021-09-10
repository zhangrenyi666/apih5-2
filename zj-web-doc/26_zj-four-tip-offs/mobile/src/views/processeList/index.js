import React, { Component } from 'react';
import s from './style.less';
import { Spin } from 'antd';
import { NavBar, Icon, Tabs } from 'antd-mobile';
import Untreated from "./untreated"
import Processeing from "./processeing"
import Processed from "./processed"
import { push } from 'react-router-redux';
class Exposure extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: Number(this.props.match.params.page) || 0,
            loading: false,
        };
    }
    componentDidMount(){
        const { wx } = window;
        wx.ready(() => {
            wx.hideOptionMenu();
        }) 
    }
    render() {
        const { loading, page } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}HomePage`));
                        }}
                    >{"处理列表"}</NavBar>
                </div>
                <Spin spinning={loading}>
                    <div className={s.footer}>
                        <Tabs
                            swipeable={false}
                            tabs={[{ title: "待解决" }, { title: "未解决" }, { title: "已解决" }]}
                            initialPage={page}
                            page={page}
                            onChange={(_, page) => {
                                this.setState({ page });
                            }}
                        >
                            <Untreated {...this.props} tabBarHeight={88.5} />
                            <Processeing {...this.props} tabBarHeight={88.5} />
                            <Processed {...this.props} tabBarHeight={88.5} />
                        </Tabs>
                    </div>
                </Spin>
            </div>
        )
    }
}

export default Exposure;
