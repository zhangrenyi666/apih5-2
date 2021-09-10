import React from "react";
import { Layout,Menu,Breadcrumb,Icon,Dropdown,message as Msg,Spin,Popover,Modal,Form } from "antd";
// import { push } from 'connected-react-router';
import styles from "./style/index.less";

import QnnForm from '../../qnn-table/qnn-form';


class EditPwdModalCom extends React.Component {
    render() {
        const { editPwdModal,editPwdOk,editPwdConfirmLoading,editPwdCancel,form,myFetch,history,match,wrappedComponentRef } = this.props;
        return (
            <div>
                <Modal
                    title="修改密码"
                    visible={editPwdModal}
                    onOk={editPwdOk}
                    confirmLoading={editPwdConfirmLoading}
                    onCancel={editPwdCancel}
                >
                    <div>
                        <QnnForm
                            {...this.props}
                            form={form}
                            myFetch={myFetch}
                            history={history}
                            match={match}
                            wrappedComponentRef={wrappedComponentRef}

                            formConfig={[
                                {
                                    label: "旧密码",
                                    field: "oldPwd",
                                    type: 'password',
                                    required: true,
                                    placeholder: "请输入..."
                                },
                                {
                                    label: "新密码",
                                    field: "pwd",
                                    type: 'password',
                                    required: true,
                                    placeholder: "请输入..."
                                },
                                {
                                    label: "确认密码",
                                    field: "pwd2",
                                    type: 'password',
                                    required: true,
                                    placeholder: "请输入..."
                                }
                            ]}
                        />
                    </div>
                </Modal>
            </div>
        );
    }
}

const EditPwdModalComIncForm = Form.create()(EditPwdModalCom);


const { Header,Content,Sider,Footer } = Layout;
class SiderDemo extends React.Component {
    constructor(...args) {
        super(...args);
        const LoginAndLogoutInfo = this.props.loginAndLogoutInfo || {};

        //获取当前选中的公司其属性levelId为公司值
        const curCompany = LoginAndLogoutInfo.loginInfo ? LoginAndLogoutInfo.loginInfo.userInfo.curCompany : null;

        const projectList = LoginAndLogoutInfo.loginInfo ? LoginAndLogoutInfo.loginInfo.userInfo.projectList : [];
        this.state = {
            collapsed: false,
            //公司列表
            companyList: projectList || null,
            //当前公司
            curCompany: curCompany || null,

            //修改密码的弹出是否打开
            editPwdModal: false,

            //修改密码是否请求中
            editPwdConfirmLoading: false,

        };

        //是否能切换项目  默认是可以的
        if (window.configs.canChangeProject === false) {
            window.configs.canChangeProject = false
        } else {
            window.configs.canChangeProject = true
        };
    }
    componentDidMount() {
        const { curCompany } = this.state;
        if (!curCompany && window.configs.canChangeProject) {
            this.getCompanyList.bind(this)();
        }
    }
    //获取公司列表
    getCompanyList = () => {
        this.props.myFetch('getZxQrcodePermissionObjectListByProject',{}).then(({ success,data,message }) => {
            if (success) {
                let _curCompany = null;
                let _d = data.map(item => {
                    let _item = {
                        ...item,
                        value: item.levelId,
                        realLabel: item.levelShortName,
                        label: (item.levelShortName && item.levelShortName.length > 7) ? item.levelShortName.substr(0,7) + '...' : item.levelShortName
                    }
                    if (_item.selectFlag === "1") {
                        _curCompany = _item;
                    }
                    return {
                        ..._item
                    }
                });
                if (!_curCompany && _d && _d.length) {
                    _curCompany = _d[0];
                }
                this.changCompany.bind(this)(_curCompany,_d);
            } else {
                Msg.error(message)
            }
        })
    }

    changCompany = (companyInfo,projectList) => {
        const { dispatch,actions: { setLoginAndLogoutInfo } } = this.props;
        const { curCompany,companyList } = this.state;

        if (!companyList && projectList) {
            const LoginAndLogoutInfo = this.props.loginAndLogoutInfo;
            const oldCurCompany = LoginAndLogoutInfo.loginInfo.userInfo.curCompany
            if (!oldCurCompany) {
                LoginAndLogoutInfo.loginInfo.userInfo.curCompany = companyInfo;
                if (projectList) {
                    LoginAndLogoutInfo.loginInfo.userInfo.projectList = projectList;
                }
                dispatch(setLoginAndLogoutInfo({ ...LoginAndLogoutInfo }));
            }
            return;
        }

        if (curCompany && curCompany.value) {
            this.props.myFetch('changeZxQrcodePermissionProject',companyInfo).then(({ success,data,message }) => {
                if (success) {
                    //将切换的公司信息存进userInfo中
                    this.setState({
                        curCompany: companyInfo
                    },() => {
                        const oldLoginAndLogoutInfo = this.props.loginAndLogoutInfo;
                        oldLoginAndLogoutInfo.loginInfo.userInfo.curCompany = companyInfo;
                        if (projectList) {
                            oldLoginAndLogoutInfo.loginInfo.userInfo.projectList = projectList;
                        }
                        dispatch(setLoginAndLogoutInfo({ ...oldLoginAndLogoutInfo }));
                    })
                } else {
                    // Msg.error(message); 
                    this.setState({
                        curCompany: null,
                        companyList: null
                    })
                }
            });
        }
    }

    //修改密码
    editPwdOk = () => {
        const { pwd,pwd2,oldPwd } = this.qnnForm.props.form.getFieldsValue();

        if (pwd !== pwd2) {
            Msg.error("两次输入的密码不一致！请检查重试。");
            return;
        }

        // this.setState({
        //     editPwdConfirmLoading: true,
        // });
        this.props.myFetch('editPwd',{
            userPwdOld: oldPwd,
            userPwd: pwd,
            userPwdNew: pwd2
        }).then(({ success,message }) => {
            this.setState({
                editPwdConfirmLoading: false,
            });
            if (success) {
                this.setState({
                    editPwdModal: false,
                });
                Msg.success(message);
            } else {
                Msg.error(message);
            }
        });
    };

    editPwdCancel = () => {
        this.setState({
            editPwdModal: false,
        });
    };


    render() {
        const { companyList,curCompany,editPwdModal,editPwdConfirmLoading } = this.state;
        const {
            dispatch,
            actions: { logout },
            routerInfo: { curKey,routeData,routeTree },
            myPublic: { URI,appInfo },
            loginAndLogoutInfo
        } = this.props;
        const { loginInfo } = loginAndLogoutInfo;
        let userInfo = {};
        if (loginInfo) {
            userInfo = loginInfo.userInfo;
        }
        let realName = userInfo.realName;

        const { pathName } = routeData[curKey];

        const loopOpenKeys = (key,openKeys = []) => {
            if (routeData[key] && routeData[key].pid) {
                openKeys.unshift(routeData[key].pid);
                openKeys = loopOpenKeys(routeData[key].pid,openKeys);
            }
            return openKeys;
        };
        const openKeys = loopOpenKeys(curKey);

        const siderMenuLv = 1;
        let siderRouteTree = [];
        const openKeysObj = openKeys.map(key => {
            if (routeData[key].menuLv + 1 === siderMenuLv) {
                siderRouteTree = routeData[key].routeTree;
            }
            return routeData[key];
        });
        openKeysObj.push(routeData[curKey]);
        const breadcrumbs = openKeysObj.map((item,index) => {
            return <Breadcrumb.Item key={index}>{item.label}</Breadcrumb.Item>;
        });
        const loopRouteTree = (treeData = [],end,hasIcon) => {
            let menuArr = [];
            for (let i = 0; i < treeData.length; i++) {
                const {
                    id,
                    label,
                    routeTree,
                    menuLv,
                    iconType,
                    hide
                } = treeData[i];
                if (label && !hide) {
                    if (routeTree && routeTree.length) {
                        if (end > menuLv) {
                            menuArr.push(
                                <Menu.SubMenu
                                    className={styles.SubMenu}
                                    key={id}
                                    title={
                                        <span>
                                            {hasIcon ? (
                                                <Icon
                                                    type={iconType || "user"}
                                                />
                                            ) : null}
                                            {label}
                                        </span>
                                    }
                                >
                                    {loopRouteTree(routeTree,end,hasIcon)}
                                </Menu.SubMenu>
                            );
                        } else {
                            menuArr.push(
                                <Menu.Item key={id}>
                                    {hasIcon ? (
                                        <Icon type={iconType || "user"} />
                                    ) : null}
                                    <span>{label}</span>
                                </Menu.Item>
                            );
                        }
                    } else {
                        menuArr.push(
                            <Menu.Item key={id}>
                                {hasIcon ? (
                                    <Icon type={iconType || "user"} />
                                ) : null}{" "}
                                <span>{label}</span>
                            </Menu.Item>
                        );
                    }
                }
            }
            return menuArr;
        };
        const mainMenuTree = loopRouteTree(routeTree,0);
        const siderMenuTree = loopRouteTree(siderRouteTree,3,true);
        const menu = (
            <Menu>
                <Menu.Item style={{ textAlign: "center" }}>
                    <a
                        target="_blank"
                        rel="noopener noreferrer"
                        onClick={() => {
                            this.setState({ editPwdModal: true })
                        }}
                    >
                        <Icon
                            type="edit"
                            theme="outlined"
                            style={{ marginRight: "3px" }}
                        />{" "}
                        修改密码
                    </a>
                </Menu.Item>
                <Menu.Item style={{ textAlign: "center" }}>
                    <a
                        target="_blank"
                        rel="noopener noreferrer"
                        onClick={() => {
                            dispatch(logout(pathName));
                        }}
                    >
                        <Icon
                            type="logout"
                            theme="outlined"
                            style={{ marginRight: "3px" }}
                        />{" "}
                        退出登录
                    </a>
                </Menu.Item>
                {/* <Menu.Item style={{textAlign:'center'}}>
                    <a target="_blank" rel="noopener noreferrer" onClick={() => { dispatch(push(`/editPassword`)) }}>修改密码</a>
                </Menu.Item> */}
            </Menu>
        );
        const menuByCompany = (
            <Menu
                style={
                    companyList && companyList.length > 11 ?
                        {
                            height: "400px",
                            overflowY: "scroll"
                        } : null}
            >
                {
                    companyList && companyList.map((item,index) => {
                        let { label,value,realLabel } = item;
                        return <Menu.Item disabled={value === curCompany.value} key={index} onClick={() => {
                            this.changCompany.bind(this)(item)
                        }}>
                            {/* {label} */}
                            <Popover placement="left" style={{ zIndex: '9999999' }} content={realLabel}>
                                {label}
                            </Popover>
                        </Menu.Item>
                    })
                }
            </Menu>
        );
        if (window.configs.canChangeProject) {
            if (!companyList || !curCompany) {
                return <div style={{
                    textAlign: "center",
                    color: "#ccc",
                    margin: '32px 0px'
                }}>请求项目列表中...<span style={{ paddingLeft: 8 }}><Spin /></span></div>
            }
        }

        if (getDeviceType() === "mobile") {
            return this.props.children;
        }

        return (
            <Layout
                style={{
                    overflow: "auto",
                    height: "100vh",
                    position: "fixed",
                    left: 0,
                    width: "100vw"
                }}
            >
                <Header className={styles.header}>
                    {appInfo.logo ? (
                        <img height="60%" src={appInfo.logo} alt="" />
                    ) : (
                            ""
                        )}
                    <div className={styles.title}>{appInfo.title}</div>
                    {/* 一级菜单 */}
                    <Menu
                        className={styles.Menu}
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={[...openKeys,curKey]}
                        onClick={({ item,key,keyPath }) => {
                            let { routeTree } = routeData[key];
                            if (routeTree && routeTree.length) {
                                // console.log(routeTree);
                                let { moduleName,defaultPath } = routeTree[0];
                                window.location.href = new URI()
                                    .host(new URI().host())
                                    .path(moduleName)
                                    .hash(
                                        `${moduleName}${
                                        defaultPath === "/"
                                            ? ""
                                            : defaultPath
                                        }`
                                    )
                                    .toString();
                            } else {
                                let { moduleName,defaultPath } = routeData[
                                    key
                                ];
                                window.location.href = new URI()
                                    .host(new URI().host())
                                    .path(moduleName)
                                    .hash(
                                        `${moduleName}${
                                        defaultPath === "/"
                                            ? ""
                                            : defaultPath
                                        }`
                                    )
                                    .toString();
                            }
                        }}
                        // style={{ lineHeight: '48px', margin: "8px" }}
                        style={{ margin: "auto",maxWidth: '70%' }}
                    >
                        {mainMenuTree}
                    </Menu>
                    <div style={{ flex: 1,width: 0 }} />


                    {/* 个人下拉 */}
                    <Dropdown overlay={menu} placement="bottomRight">
                        {/* <Icon style={{ fontSize: 22 }} type="appstore" /> */}
                        <div
                            style={{
                                color: "rgba(255, 255, 255, 0.65)",
                                padding: "0 20px",
                                cursor: "pointer",
                                textAlign: "center"
                            }}
                        >
                            {realName ? (
                                <div>
                                    <Icon
                                        type="user"
                                        theme="outlined"
                                        style={{ marginRight: "3px" }}
                                    />
                                    {realName}
                                </div>
                            ) : (
                                    ""
                                )}
                        </div>
                    </Dropdown>
                    {/* 切换公司下拉 */}
                    {
                        companyList && companyList.length ? <div style={{ marginRight: 12 }}>
                            <span style={{ color: "rgba(255, 255, 255, 0.65)" }}>当前项目： </span><Dropdown overlay={menuByCompany} >
                                <a className="ant-dropdown-link" style={{ display: 'inline-block',marginRight: 8 }}>
                                    {curCompany.label} <Icon type="down" />
                                </a>
                            </Dropdown>
                        </div> : null
                    }


                </Header>
                <Layout>
                    {siderMenuTree.length ? (
                        // <div> 
                        <Sider
                            collapsedWidth={0}
                            className={styles.Sider}
                            collapsed={this.state.collapsed}
                        >
                            <Menu
                                className={styles.Menu}
                                theme="dark"
                                mode="inline"
                                defaultSelectedKeys={[curKey]}
                                defaultOpenKeys={openKeys}
                                onClick={({ item,key,keyPath }) => {
                                    let { moduleName,defaultPath } = routeData[
                                        key
                                    ];
                                    window.location.href = new URI()
                                        .host(new URI().host())
                                        .path(moduleName)
                                        .hash(
                                            `${moduleName}${
                                            defaultPath === "/"
                                                ? ""
                                                : defaultPath
                                            }`
                                        )
                                        .toString();
                                }}
                                style={{ lineHeight: "64px" }}
                            >
                                {siderMenuTree}
                            </Menu>

                            <div>
                                <span
                                    className={`${styles.collapsBtn} ${
                                        !this.state.collapsed
                                            ? styles.collapsed
                                            : ""
                                        }`}
                                    onClick={() => {
                                        this.setState({
                                            collapsed: !this.state.collapsed
                                        });
                                    }}
                                >
                                    {this.state.collapsed ? (
                                        <img
                                            src={require("./img/right.png")}
                                            alt="arr"
                                        />
                                    ) : (
                                            <img
                                                src={require("./img/left.png")}
                                                alt="arr"
                                            />
                                        )}
                                </span>
                            </div>
                        </Sider>
                        // </div>
                    ) : null}

                    <Layout style={{ padding: "0 12px" }}>
                        <Breadcrumb style={{ margin: "8px 0" }}>
                            {breadcrumbs}
                        </Breadcrumb>
                        <Content
                            className={styles.ani}
                            style={{
                                background: "#fff",
                                padding: "12px",
                                minHeight: "auto"
                            }}
                        >
                            {this.props.children}
                        </Content>
                        <Footer
                            style={{ textAlign: "center",padding: "10px 0" }}
                        >
                            {" "}
                            {appInfo.copyright}
                        </Footer>
                    </Layout>
                </Layout>

                {/* 修改密码弹窗 */}
                <EditPwdModalComIncForm
                    editPwdModal={editPwdModal}
                    editPwdConfirmLoading={editPwdConfirmLoading}
                    editPwdCancel={this.editPwdCancel.bind(this)}
                    editPwdOk={this.editPwdOk.bind(this)}
                    wrappedComponentRef={(me) => {
                        this.qnnForm = me;
                    }}
                    {...this.props} />

            </Layout>
        );
    }
}

function getDeviceType() {
    //简单判断
    if (
        navigator.userAgent.match(
            /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
        )
    ) {
        return "mobile";
    } else {
        return "pc";
    }
};

const blank = Children => {
    return props => {
        // if (getDeviceType() === "mobile") {
        //     return <Children {...props} />;
        // }
        return (
            <SiderDemo {...props}>
                <Children {...props} />
            </SiderDemo>
        );
    };
};

export default blank;
