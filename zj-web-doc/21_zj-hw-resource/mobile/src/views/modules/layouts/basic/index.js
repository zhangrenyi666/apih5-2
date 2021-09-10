import React from 'react';
import { Layout, Menu, Breadcrumb, Icon, Dropdown } from 'antd';
import styles from "./style/index.less";
// const SubMenu = Menu.SubMenu;
// const MenuItemGroup = Menu.ItemGroup;
const { Header, Content, Sider, Footer } = Layout;
class SiderDemo extends React.Component {
    render() {
        const { dispatch, actions: { logout }, routerInfo: { curKey, routeData, routeTree }, myPublic: { URI, appInfo } } = this.props
        const { pathName,
            //  moduleName
        } = routeData[curKey]
        const loopOpenKeys = (key, openKeys = [], ) => {
            if (routeData[key] && routeData[key].pid) {
                openKeys.unshift(routeData[key].pid)
                openKeys = loopOpenKeys(routeData[key].pid, openKeys)
            }
            return openKeys
        }
        const openKeys = loopOpenKeys(curKey)

        const siderMenuLv = 1
        let siderRouteTree = []
        const openKeysObj = openKeys.map((key) => {
            if (routeData[key].menuLv + 1 === siderMenuLv) {
                siderRouteTree = routeData[key].routeTree
            }
            return routeData[key]
        })
        openKeysObj.push(routeData[curKey])
        const breadcrumbs = openKeysObj.map((item, index) => {
            return <Breadcrumb.Item key={index}>{item.label}</Breadcrumb.Item>
        })
        const loopRouteTree = (treeData = [], end, hasIcon) => { 
            let menuArr = []
            for (let i = 0; i < treeData.length; i++) {
                const { id, label, routeTree, menuLv, iconType, hide } = treeData[i];
                if (label && !hide) {
                    if (routeTree && routeTree.length) {
                        if (end > menuLv) {
                            menuArr.push(<Menu.SubMenu key={id} title={<span>{hasIcon ? <Icon type={iconType || "user"} /> : null}{label}</span>}>{loopRouteTree(routeTree, end, hasIcon)}</Menu.SubMenu>)
                        } else {
                            menuArr.push(<Menu.Item key={id}>{hasIcon ? <Icon type={iconType || "user"} /> : null}<span>{label}</span></Menu.Item>)
                        }
                    } else {
                        menuArr.push(<Menu.Item key={id}>{hasIcon ? <Icon type={iconType || "user"} /> : null} <span>{label}</span></Menu.Item>)
                    }
                }
            }
            return menuArr
        }
        const mainMenuTree = loopRouteTree(routeTree, 0)
        const siderMenuTree = loopRouteTree(siderRouteTree, 3, true);
        const menu = (
            <Menu>
                <Menu.Item>
                    <a target="_blank" rel="noopener noreferrer" onClick={() => { dispatch(logout(pathName)) }}>退出</a>
                </Menu.Item>
            </Menu>
        );
        return (
            <Layout style={{ overflow: 'auto', height: '100vh', position: 'fixed', left: 0, width: '100vw' }}>
                <Header className={styles.header}>
                    <div style={{ color: "rgba(255, 255, 255, 0.65)", padding: "0 20px", fontSize: 24 }}>
                        {appInfo.title}
                    </div>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={[...openKeys, curKey]}
                        onClick={({ item, key, keyPath }) => {
                            let { routeTree } = routeData[key];
                            if (routeTree && routeTree.length) {
                                // console.log(routeTree)
                                let { moduleName, defaultPath } = routeTree[0]
                                window.location.href = new URI().host(new URI().host()).path(moduleName).hash(`${moduleName}${defaultPath === "/" ? "" : defaultPath}`).toString()
                            } else {
                                let { moduleName, defaultPath } = routeData[key];
                                window.location.href = new URI().host(new URI().host()).path(moduleName).hash(`${moduleName}${defaultPath === "/" ? "" : defaultPath}`).toString()
                            }
                        }}
                        style={{ lineHeight: '48px', margin: "8px" }}
                    >
                        {mainMenuTree}
                    </Menu>
                    <div style={{ flex: 1, width: 0 }}></div>
                    <Dropdown overlay={menu} placement="bottomRight">
                        <div style={{ color: "rgba(255, 255, 255, 0.65)", padding: "0 20px", cursor: "pointer" }}><Icon style={{ fontSize: 22 }} type="appstore" /></div>
                    </Dropdown>
                </Header>
                <Layout>
                    {siderMenuTree.length ?
                        <Sider
                            collapsible
                        >
                            <Menu
                                theme="dark"
                                mode="inline"
                                defaultSelectedKeys={[curKey]}
                                defaultOpenKeys={openKeys}
                                onClick={({ item, key, keyPath }) => {
                                    let { moduleName, defaultPath } = routeData[key];
                                    window.location.href = new URI().host(new URI().host()).path(moduleName).hash(`${moduleName}${defaultPath === "/" ? "" : defaultPath}`).toString()
                                }}
                                style={{ lineHeight: '64px' }}
                            >
                                {siderMenuTree}
                            </Menu>
                        </Sider> : null
                    }

                    <Layout style={{ padding: '0 12px' }}>
                        <Breadcrumb style={{ margin: '8px 0' }}>
                            {breadcrumbs}
                        </Breadcrumb>
                        <Content  className={styles.ani}  style={{ background: '#fff', padding: '12px' }}>
                            {this.props.children}
                        </Content>
                        <Footer style={{ textAlign: 'center', padding: "10px 0", }}> {appInfo.copyright}</Footer>
                    </Layout>
                </Layout>
            </Layout>)
    }
}


const blank = (Children) => {
    return (props) => {
        return (
            <SiderDemo {...props}>
                <Children {...props} />
            </SiderDemo>
        )
    }
}










export default blank