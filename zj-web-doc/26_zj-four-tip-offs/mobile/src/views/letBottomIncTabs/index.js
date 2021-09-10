import React, { Component } from 'react';
import s from './style.less';
import { TabBar } from 'antd-mobile';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { blank } from '../modules/layouts';
const imgs = {
    home: require('../../imgs/tabs/home.svg'),
    me: require('../../imgs/tabs/me.svg'),
    homeed: require('../../imgs/tabs/homeed.svg'),
    meed: require('../../imgs/tabs/meed.svg'),
    
}
class Tabs extends Component {
    state = {
        selectedTab: 'HomePage/0',
        hidden: false,
    };

    tabs = [ //底部所有的切换项
        {
            title: '首页',
            key: 'HomePage/0',
            route: 'HomePage/:key',
            icon: <img className={s.tabsBtn} src={imgs.home} alt="" />,
            selectedIcon: <img className={s.tabsBtn} src={imgs.homeed} alt="" />,
        },
        {
            title: '个人中心',
            key: 'Me',
            route: 'Me',
            icon: <img className={s.tabsBtn} src={imgs.me} alt="" />,
            selectedIcon: <img className={s.tabsBtn} src={imgs.meed} alt="" />,
        },
    ]

    render() {
        const path = this.props.match.path;
        const { myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.Tabs}>
                <TabBar
                    unselectedTintColor="#949494"
                    tintColor="#0a90f1"
                    barTintColor="#fdfbfb"
                    hidden={this.state.hidden}
                >
                    {
                        this.tabs.map((item) => {
                            const { key, route } = item; 
                            return <TabBar.Item
                                selected={path === `${mainModule}${route}`}
                                onPress={() => {
                                    const { dispatch } = this.props;
                                    dispatch(push(`${mainModule}${key}`))
                                }}
                                {...item}> 
                                {
                                    path === `${mainModule}${route}` && this.props.children && React.Children.map(this.props.children, (children) => {
                                        return React.cloneElement(children, {
                                            ...this.props
                                        })
                                    })
                                }
                            </TabBar.Item>
                        })
                    }
                </TabBar>
            </div>
        )
    }
}
Tabs = connect(state => state)(blank(Tabs));
const incTab = (Component) => {
  return (props) => {
      return <Tabs {...props}><Component /></Tabs>
  }
}
export default incTab;
