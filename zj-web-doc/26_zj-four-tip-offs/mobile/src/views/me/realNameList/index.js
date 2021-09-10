import React, { Component } from 'react';
import s from './style.less';
import { NavBar, Icon, Tabs } from 'antd-mobile';
import All from './all';
import RealNameReporting from './realNameReporting';
import AnonymityReporting from './anonymityReporting';
import { push } from 'react-router-redux';
class RealNameList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: Number(this.props.match.params.page) || 0,
        };
    }
    render() {
        const { page } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}Me`));
                        }}
                    >{"举报列表"}</NavBar>
                </div>
                <div className={s.footer}>
                    <Tabs
                        swipeable={false}
                        tabs={[{ title: "全部" }, { title: "实名举报" }, { title: "匿名举报" }]}
                        initialPage={page}
                        page={page}
                        onChange={(_, page) => {
                            this.setState({ page });
                        }}
                    >
                        <All {...this.props} tabBarHeight={88.5} />
                        <RealNameReporting {...this.props} tabBarHeight={88.5} />
                        <AnonymityReporting {...this.props} tabBarHeight={88.5} />
                    </Tabs>
                </div>
            </div>
        )
    }
}

export default RealNameList;
