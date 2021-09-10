import React, { Component } from 'react';
import { List, Toast, InputItem, Modal, NavBar, Button, Flex } from 'antd-mobile';
import MyUser from "../../lny_modules/User"
import styles from './Org.less';
import { replace } from 'connected-react-router';
const alert = Modal.alert;
class OrgRegister extends Component {
    constructor(props) {
        super(props);
        const { match: { params: { isInit } } } = this.props
        this.isInit = isInit === "1"
        this.modal = {}
        this.state = {
            mobile: "",
            realName: "",
            hasError: false,
        }
    }
    componentDidMount() {
        this.updateInfo()
    }
    componentWillUnmount() {
        for (var key in this.modal) {
            if (this.modal.hasOwnProperty(key)) {
                this.modal[key].close()
            }
        }
    }
    format = (value) => {
        value = value.replace(/\s/g, "");
        var result = "";
        for (let i = 0; i < value.length; i++) {
            if (i === 3 || i === 7) {
                result = result + " " + value[i];
            } else {
                result = result + value[i];
            }
        }
        return result
    }
    updateInfo = (data = {}) => {
        MyUser.updateUserInfo(data).then((userInfo) => {
            const { mobile, realName } = userInfo
            this.mobile = mobile;
            this.realName = realName;
            var _mobile = this.format(mobile)
            this.setState({ mobile: _mobile, realName })
        })
    }
    onErrorClick = () => {
        if (this.state.hasError) {
            Toast.info('请输入正确的手机号码');
        }
    }
    onMobileChange = (mobile) => {
        if (mobile.replace(/\s/g, '').length < 11) {
            this.setState({
                hasError: true,
            });
        } else {
            this.setState({
                hasError: false,
            });
        }
        this.setState({
            mobile,
        });
    }
    onNameChange = (realName) => {
        this.setState({
            realName,
        });
    }
    save = () => {
        var { realName, mobile, hasError } = this.state;
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName } = routeData[curKey]

        const reg = /^用户.*$/;
        mobile = mobile.replace(/\s/g, '')
        if (realName && mobile && !hasError && !reg.test(realName)) {
            const body = {
                mobile,
                realName,
            }
            Toast.loading('Loading...', 0);
            myFetch('toRegisterZjSysUser', body).then(({ data, code, success, message }) => {
                if (success) {
                    Toast.success("修改成功", 1, () => {
                        this.updateInfo(body)
                    })
                } else if (code === "3003" || code === "3004") {
                    dispatch(logout(pathName))
                } else {
                    Toast.offline(message, 2)
                }
            })
        } else {
            this.modal.alert = alert('提醒', '请按照规则重置您的姓名和手机号！')
        }
    }
    back = () => {
        const { dispatch, routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        dispatch(replace(`${moduleName}quality`))
    }
    ignore = () => {
        const { dispatch, routerInfo: { routeData, curKey } } = this.props
        const { moduleName } = routeData[curKey]
        dispatch(replace(`${moduleName}quality`))
    }
    saveAndtoIndex = () => {
        const { myFetch, actions: { logout }, dispatch, routerInfo: { routeData, curKey } } = this.props
        const { pathName, moduleName } = routeData[curKey]
        var { realName, mobile, hasError } = this.state;
        const reg = /^用户.*$/;
        mobile = mobile.replace(/\s/g, '')
        if (realName && mobile && !hasError && !reg.test(realName)) {
            const body = {
                mobile,
                realName,
            }
            Toast.loading('Loading...', 0);
            myFetch('toRegisterZjSysUser', body).then(({ data, success, code, message }) => {
                if (success) {
                    MyUser.updateUserInfo(body).then((userInfo) => {
                        Toast.success("注册成功", 1, () => {
                            dispatch(replace(`${moduleName}quality`))
                        })
                    })
                } else if (code === "3003" || code === "3004") {
                    dispatch(logout(pathName))
                } else {
                    Toast.offline(message, 2)
                }
            })
        } else {
            this.modal.alert = alert('提醒', '请检查您的真实姓名和手机号是否符合规定！')
        }
    }
    render() {
        const { realName, hasError, mobile } = this.state;
        return (
            <div className={`${styles["lny-page"]} ${styles["lny-Org"]} ${styles["flexBox"]} ${styles["column"]}`}>
                <NavBar className={styles["lny-header"]} mode="light">{`个人信息注册与修改`}</NavBar>
                <div className={styles["lny-body"]}>
                    <List className={styles["lny-boxShadow"]}>
                        <InputItem
                            placeholder="请输入真实姓名"
                            onChange={this.onNameChange}
                            value={realName}
                        >真实姓名：</InputItem>
                        <InputItem
                            type="phone"
                            placeholder="请输入手机号码"
                            error={hasError}
                            onErrorClick={this.onErrorClick}
                            onChange={this.onMobileChange}
                            value={mobile}
                        >手机号码：</InputItem>
                        <div className={styles["lny-list-footer"]} style={{ textAlign: "left" }}>{}</div>
                        <div className={styles["lny-list-footer"]} style={{ textAlign: "left" }}>
                            {"规范说明："} < br />
                            {"1、首次进入本系统时，真实姓名默认为“用户”+随机编号，请您修改为本人的正确信息；"} < br />
                            {"2、真实姓名不能为空，且不能以 “用户” 二字开头；"} < br />
                            {"3、手机号为11位阿拉伯数字。"}
                        </div>
                    </List>
                </div>
                <Flex className={styles["lny-footer"]}>
                    {this.isInit ? <Flex.Item><Button onClick={this.ignore} type="ghost">{"忽略"}</Button></Flex.Item> : ""}
                    {this.isInit ? <Flex.Item><Button onClick={this.saveAndtoIndex} type="primary">{"保存信息并注册"}</Button></Flex.Item> : ""}
                    {!this.isInit ? <Flex.Item><Button onClick={this.back} type="ghost">{"返回"}</Button></Flex.Item> : ""}
                    {!this.isInit ? <Flex.Item><Button onClick={this.save} type="primary">{"保存修改"}</Button></Flex.Item> : ""}
                </Flex>
            </div >
        )
    }
}

export default OrgRegister;