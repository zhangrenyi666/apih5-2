import React from "react";

import { blank } from "apih5/modules/layouts";
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Input, Button, Checkbox, message as Msg, Spin, Form } from "antd";
import styles from "./style/index.less";
import $ from "jquery";
import { Encrypt, Decrypt } from "./md5"
window.configs.loginPageConfig = window.configs.loginPageConfig || {};
const { bgImgUrl = ["bg.jpg"] } = window.configs.loginPageConfig;
const FormItem = Form.Item;
const Home = props => {
    return <WrappedNormalLoginForm {...props} />;
};

const { language = "zh_CN", loginFormIncVerCode = true } = window.configs;
const isEn = language === "en_US";
const rowText = (isEn ? window.configs.loginPageConfig.rowTextUS : window.configs.loginPageConfig.rowText) || [];
class NormalLoginForm extends React.Component {

    state = {

        //验证码信息 
        imgData: "",
        loginFormDataEncrypt: window.configs.apis.login === 'user/login?WEBFlag=Ab25DB'
    }

    handleSubmit = e => {
        const {
            dispatch,
            actions: { login },
        } = this.props;
        e.preventDefault();
        const { validateFields } = this.form;
        validateFields().then((values) => {
            const { userId, userPwd, remember, captchaId, captchaCode } = values;

            //ios还原
            setTimeout(() => {
                window.scrollTo(0, document.body.scrollTop + 1);
                document.body.scrollTop >= 1 && window.scrollTo(0, document.body.scrollTop - 1);
            }, 10);

            const body = {
                remember,
                userId: this.state.loginFormDataEncrypt ? Encrypt(userId) : userId,
                userPwd: this.state.loginFormDataEncrypt ? Encrypt(userPwd) : userPwd,
                loginType: "1",
                captchaId,
                captchaCode
            };
            dispatch(login(body));
        }).catch((err) => {
            console.log(err)
        })
    };

    componentDidMount() {

        let _bannerIndex = 1;
        if (bgImgUrl && bgImgUrl.length > 1) {
            window.clearInterval(window.loginBannerTimer);
            window.loginBannerTimer = setInterval(() => {
                $(".sliderItem").fadeOut(300, function () {
                    $(".sliderItem")
                        .eq(_bannerIndex)
                        .fadeIn(320);
                });
                if (_bannerIndex >= bgImgUrl.length - 1) {
                    _bannerIndex = 0;
                } else {
                    _bannerIndex += 1;
                }
            }, 6500);
        }

        !this.props.loginAndLogoutInfo?.loginInfo?.loading && loginFormIncVerCode && this.getVCode();
    }
    componentWillUnmount() {
        window.clearInterval(window.loginBannerTimer);
    }

    getVCode = () => {
        //设置默认的getCaptchaCode接口
        if (!window.configs.apis['getCaptchaCode']) {
            window.configs.apis['getCaptchaCode'] = "getCaptchaCode";
        }
        this.props.myFetch("getCaptchaCode", {}, "1").then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    imgData: data.imgData
                }, () => {
                    if (this.form?.setFieldsValue) {
                        this.form.setFieldsValue({
                            captchaId: data.captchaId
                        })
                    }
                });

            } else {
                Msg.error(message)
            }
        })
    }

    render() {
        let { imgData } = this.state;
        const {
            dispatch,
            actions: { tabLoginType },
            // form: { getFieldDecorator },
            loginAndLogoutInfo: { loginInfo, logoutInfo },
            myPublic,
        } = this.props;
        const { appInfo } = myPublic;

        var loading = false;
        if (loginInfo && loginInfo.loading) {
            loading = true;
        }
        let loginType = appInfo.loginType;
        let webCodeLoginType = appInfo.webCodeLoginType;
        if (logoutInfo) {
            loginType = logoutInfo.loginType;
        }
        if (webCodeLoginType) {
            // history.goBack();
            return <div style={{ textAlign: "center" }}>
                <div style={{ color: "red", marginTop: '24px' }}>
                    {isEn ? 'Login without permission!' : '无权限登录！'}
                </div>
            </div>
        }
        return (
            <div className={styles.container}>
                {/* <div className={styles.header}>{`${appInfo.title}`}</div> */}
                <div className={styles.bg}>
                    <div className={`${styles.shadow} shadow`} />
                    <div className={styles.slider}>
                        <div className={styles.wrapper}>
                            {bgImgUrl &&
                                bgImgUrl.map((item, index) => {
                                    return (
                                        <div
                                            key={index}
                                            className={`${styles.sliderItem
                                                } sliderItem`}
                                        >
                                            <img
                                                src={this.props.getBGUrl(item)}
                                                alt="bg"
                                            />
                                        </div>
                                    );
                                })}
                        </div>
                    </div>
                    <div className={styles.bigText}>
                        {rowText &&
                            rowText.map((item, index) => {
                                return (
                                    <div
                                        key={index}
                                        className={`${styles.bigTextItem
                                            } bigTextItem`}
                                    >
                                        {item}
                                    </div>
                                );
                            })}
                    </div>

                    <div className={styles.box}>
                        <div id={"form"} className={styles.impowerBox}>
                            {loginType === "1" ? (
                                <div>
                                    <div className={styles.title}>
                                        {/* <img src={require("../../images/sxlogo.png")} alt="logo" className={styles.logo} /> */}
                                        用户登录 | User Login
                                    </div>
                                    <Form
                                        initialValues={{
                                            userId: logoutInfo ? (this.state.loginFormDataEncrypt ? Decrypt(logoutInfo.username) : logoutInfo.username) : null,
                                            userPwd: logoutInfo ? (this.state.loginFormDataEncrypt ? Decrypt(logoutInfo.password) : logoutInfo.password) : null,
                                            remember: true
                                        }}

                                        ref={(me) => {
                                            if (me) {
                                                this.form = me
                                            }
                                        }}>
                                        <FormItem
                                            name={["userId"]}
                                            rules={[
                                                {
                                                    required: true,
                                                    message: isEn ? 'Please input user name' : "请输入用户名！"
                                                }
                                            ]}
                                            style={{ marginBottom: "18px" }} >
                                            <Input
                                                style={{ width: "100%" }}
                                                prefix={
                                                    <UserOutlined
                                                        style={{
                                                            color:
                                                                "rgba(0,0,0,.25)"
                                                        }} />
                                                }
                                                placeholder={isEn ? 'Please enter your user name' : "用户名"}
                                            />
                                            {/* )} */}
                                        </FormItem>
                                        <FormItem
                                            name={["userPwd"]}
                                            rules={[
                                                {
                                                    required: true,
                                                    message: isEn ? 'Please enter your password' : "请输入密码！"
                                                }
                                            ]}
                                            style={{ marginBottom: "18px" }}>
                                            <Input
                                                prefix={
                                                    <LockOutlined
                                                        style={{
                                                            color:
                                                                "rgba(0,0,0,.25)"
                                                        }} />
                                                }
                                                type="password"
                                                placeholder={isEn ? 'Please enter your password' : "密码"}
                                            />
                                            {/* )} */}
                                        </FormItem>

                                        <FormItem
                                            name={["captchaId"]}
                                            noStyle
                                        >
                                            <Input hidden />
                                        </FormItem>


                                        {/* 验证码 */}
                                        {
                                            loginFormIncVerCode ? <FormItem>
                                                <FormItem noStyle name={["captchaCode"]} rules={[
                                                    {
                                                        required: true,
                                                        message: isEn ? 'please enter the verification code' : "请输入验证码！"
                                                    }
                                                ]}>
                                                    <Input
                                                        style={{ width: "50%" }}
                                                        placeholder={isEn ? 'verification code' : "验证码"}
                                                    />
                                                </FormItem>
                                                <FormItem noStyle>
                                                    <div style={{ width: "50%", textAlign: 'center', display: "inline-block", paddingLeft: "8px", boxSizing: "border-box" }}>
                                                        {
                                                            imgData ? <img
                                                                style={{
                                                                    width: "100%",
                                                                    height: "30px",
                                                                    verticalAlign: "middle",
                                                                    marginTop: "-5px",
                                                                    borderRadius: "3px"

                                                                }}
                                                                onClick={this.getVCode}
                                                                src={`data:image/png;base64,${imgData}`}
                                                                alt="验证码" /> : <Spin />
                                                        }
                                                    </div>
                                                </FormItem>



                                            </FormItem> : null

                                        }

                                        <FormItem
                                            name={["remember"]}
                                            valuePropName="checked"
                                        >
                                            <Checkbox>{isEn ? "Save 7 days" : '保存7天'}</Checkbox>
                                        </FormItem>
                                        <FormItem>
                                            <Button
                                                loading={loading}
                                                type="primary"
                                                onClick={this.handleSubmit}
                                                style={{ width: "100%" }}
                                            >
                                                {isEn ? "Login" : "登录"}
                                            </Button>
                                            {/* <a style={{ float: "right" }} href="javascript:void(0);" onClick={() => { dispatch(push(`/forgot`)) }}>忘记密码？</a> */}
                                            {/* {"点击"}
                                        <a href="javascript:void(0);" onClick={() => {
                                            dispatch(push(`/register`))
                                        }}>{"用户注册"}!</a>                                     */}
                                        </FormItem>
                                    </Form>
                                </div>
                            ) : (
                                    ""
                                )}
                        </div>
                        {/* <div
                            className={`${styles.tab} ${loginType === "1" ? styles.static : styles.quick
                                }`}
                            onClick={() => {
                                dispatch(
                                    tabLoginType(loginType === "1" ? "5" : "1")
                                );
                            }}
                        >
                            
                        </div> */}
                        {/* <div className="login-tip">
                            <div className="poptip">
                                <div className="poptip-arrow">
                                    <em />
                                    <span />
                                </div>
                                <div className="poptip-content">
                                    {loginType === "1" ? (isEn ? "Sweep the login code" : "扫码登录") : (isEn ? "account number login" : "账号登录")}
                                </div>
                            </div>
                        </div> */}
                    </div>
                </div>
            </div >
        );
    }
}

const WrappedNormalLoginForm = NormalLoginForm;

export default blank(Home);
