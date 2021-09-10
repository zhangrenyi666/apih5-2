import React from 'react';

import { blank } from '../modules/layouts'
import { Form, Icon, Input, Button, Checkbox } from 'antd';
import styles from "./style/index.less";
const FormItem = Form.Item;
const Home = (props) => {
    return (
        <WrappedNormalLoginForm {...props} />
    )
}

class NormalLoginForm extends React.Component {
    handleSubmit = (e) => {
        const { dispatch, actions: { login }, form: { validateFields } } = this.props
        e.preventDefault();
        validateFields((err, values) => {
            const { userId, userPwd, remember } = values
            if (!err) {
                const body = {
                    remember,
                    userId,
                    userPwd,
                    loginType: "1"
                }
                dispatch(login(body))
            }
        });
    }
    render() {
        const { dispatch, actions: { tabLoginType }, form: { getFieldDecorator }, loginAndLogoutInfo: { loginInfo, logoutInfo }, myPublic } = this.props
        const { appInfo } = myPublic
        var loading = false
        if (loginInfo && loginInfo.loading) {
            loading = true
        }
        let loginType = appInfo.loginType
        if (logoutInfo) {
            loginType = logoutInfo.loginType
        }
        return (
            <div className={styles.container}>
                <div className={styles.header}>
                    {`${appInfo.title}`}
                </div>
                <div className={styles.bg}>
                    <div className={styles.box}>
                        <div id={"form"} className={styles.impowerBox}>
                            {loginType === "1" ? <div>
                                <div className={styles.title}>
                                    {/* <img src={require("../../images/sxlogo.png")} alt="logo" className={styles.logo} /> */}
                                    {"密码登录"}
                                </div>
                                <Form onSubmit={this.handleSubmit} >
                                    <FormItem>
                                        {getFieldDecorator('userId', {
                                            rules: [{ required: true, message: '请输入用户名！' }],
                                        })(
                                            <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="用户名" />
                                        )}
                                    </FormItem>
                                    <FormItem>
                                        {getFieldDecorator('userPwd', {
                                            rules: [{ required: true, message: '请输入密码！' }],
                                        })(
                                            <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="密码" />
                                        )}
                                    </FormItem>
                                    <FormItem>
                                        {getFieldDecorator('remember', {
                                            valuePropName: 'checked',
                                            initialValue: true,
                                        })(
                                            <Checkbox>保存7天</Checkbox>
                                        )}

                                        <Button loading={loading} type="primary" htmlType="submit" style={{ width: "100%" }}>
                                            {"登录"}
                                        </Button>
                                        {/* <a style={{ float: "right" }} href="javascript:void(0);" onClick={() => { dispatch(push(`/forgot`)) }}>忘记密码？</a> */}
                                        {/* {"点击"}
                                        <a href="javascript:void(0);" onClick={() => {
                                            dispatch(push(`/register`))
                                        }}>{"用户注册"}!</a>                                     */}
                                    </FormItem>
                                </Form>
                            </div> : ""}
                        </div>
                        <div className={`${styles.tab} ${loginType === "1" ? styles.static : styles.quick}`}
                            onClick={() => {
                                dispatch(tabLoginType(loginType === "1" ? "5" : "1"))
                            }}>
                            {/*  */}
                        </div>
                        <div className="login-tip">
                            <div className="poptip">
                                <div className="poptip-arrow">
                                    <em ></em>
                                    <span></span>
                                </div>
                                <div className="poptip-content" >
                                    {loginType === "1" ? "扫码登录" : "账号登录"}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className={styles.footer}>
                    {`${appInfo.copyright}`}
                </div>
            </div>
        );
    }
}

const WrappedNormalLoginForm = Form.create()(NormalLoginForm);



export default blank(Home)