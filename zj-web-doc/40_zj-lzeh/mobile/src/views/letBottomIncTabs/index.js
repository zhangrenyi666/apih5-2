import React,{ Component } from 'react';
import s from './style.less';
import { TabBar } from 'antd-mobile';
import { connect } from 'react-redux';
import { push } from 'react-router-redux'
import { blank } from '../modules/layouts'

const imgs = {
    me: require('../../imgs/tabs/me.svg'),
    add: require('../../imgs/tabs/add.svg'),
    app: require('../../imgs/tabs/app.svg'),
    new: require('../../imgs/tabs/new.svg'),
    addressBook: require('../../imgs/tabs/addressBook.svg'),

    meed: require('../../imgs/tabs/meed.svg'),
    added: require('../../imgs/tabs/added.svg'),
    apped: require('../../imgs/tabs/apped.svg'),
    newed: require('../../imgs/tabs/newed.svg'),
    addressBooked: require('../../imgs/tabs/addressBooked.svg'),
}

class Tabs extends Component {
    state = {
        selectedTab: 'App',
        hidden: false,
    };

    tabs = [ //底部所有的切换项
        {
            title: '应用',
            key: 'App',
            route: 'App',
            icon: <img className={s.tabsBtn} src={imgs.app} alt="" />,
            selectedIcon: <img className={s.tabsBtn} src={imgs.apped} alt="" />,
            click: () => { },
            style: {}
        },
        {
            title: '',
            key: 'Add',
            route: 'Add',
            icon: <img className={s.tabsBtn}
                style={{ width: '50px',height: '50px' }}
                src={imgs.add} alt="" />,
            selectedIcon: <img className={s.tabsBtn} style={{ width: '50px',height: '50px' }} src={imgs.added} alt="" />,
            click: () => { },
            style: {}
        },
        {
            title: '个人中心',
            key: 'Me',
            route: 'Me',
            icon: <img className={s.tabsBtn} src={imgs.me} alt="" />,
            selectedIcon: <img className={s.tabsBtn} src={imgs.meed} alt="" />,
            click: () => { },
            style: {}
        },
    ]

    render() {
        const path = this.props.match.path;
        const { myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.Tabs}>
                <TabBar
                    unselectedTintColor="#949494"
                    tintColor="#ec6c25"
                    barTintColor="white"
                    hidden={this.state.hidden}
                >
                    {
                        this.tabs.map((item) => {
                            const { key,route,click } = item;
                            return <TabBar.Item
                                selected={path === `${mainModule}${route}`}
                                onPress={() => {
                                    if (click) { click() }
                                    const { dispatch } = this.props;
                                    dispatch(push(`${mainModule}${key}`))
                                }}
                                {...item}>
                                <div className={s.pageContainer}>
                                    {
                                        path === `${mainModule}${route}` && this.props.children && React.Children.map(this.props.children,(children) => {
                                            return React.cloneElement(children,{
                                                ...this.props
                                            })
                                        })
                                    }
                                </div> 
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

export default incTab
