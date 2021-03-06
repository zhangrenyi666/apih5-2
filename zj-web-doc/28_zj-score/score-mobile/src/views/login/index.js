import React from "react";

import { blank } from "../modules/layouts";
import { Form,Icon,Input,Button,Checkbox } from "antd";
import styles from "./style/index.less";
import $ from "jquery";

const { bgImgUrl = ["bg.jpg"],rowText = [] } = window.configs.loginPageConfig;
const FormItem = Form.Item;
const Home = props => {
    return <WrappedNormalLoginForm {...props} />;
};
const getBGUrl = (name) => {
    if (name.indexOf('http://') >= 0 || name.indexOf('https://') >= 0) {
        return name;
    } else {
        return require(`./img/${name}`);
    }
};

class NormalLoginForm extends React.Component {
    handleSubmit = e => {
        const {
            dispatch,
            actions: { login },
            form: { validateFields }
        } = this.props;
        e.preventDefault();
        validateFields((err,values) => {
            const { userId,userPwd,remember } = values;
            if (!err) {
                const body = {
                    remember,
                    userId,
                    userPwd,
                    loginType: "1"
                };
                dispatch(login(body));
            }
        });
    };
    componentDidMount() {
        let _bannerIndex = 1;
        if (bgImgUrl.length > 1) {
            window.clearInterval(window.loginBannerTimer);
            window.loginBannerTimer = setInterval(() => {
                $(".sliderItem").fadeOut(300,function () {
                    $(".sliderItem")
                        .eq(_bannerIndex)
                        .fadeIn(320);
                });
                if (_bannerIndex >= bgImgUrl.length - 1) {
                    _bannerIndex = 0;
                } else {
                    _bannerIndex += 1;
                }
            },6500);
        }
    }
    componentWillUnmount() {
        window.clearInterval(window.loginBannerTimer);
    }
    render() {
        const {
            dispatch,
            actions: { tabLoginType },
            form: { getFieldDecorator },
            loginAndLogoutInfo: { loginInfo,logoutInfo },
            myPublic,
            // history
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
                <div style={{ color: "red",marginTop: '24px' }}>
                    ??????????????????
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
                            {/* <div className={`${styles.sliderItem} sliderItem`}>
                                <img src={getBGUrl("img1.png")} alt="bg" />
                            </div>  */}
                            {bgImgUrl &&
                                bgImgUrl.map((item,index) => {
                                    return (
                                        <div
                                            key={index}
                                            className={`${
                                                styles.sliderItem
                                                } sliderItem`}
                                        >
                                            <img
                                                src={getBGUrl(item)}
                                                alt="bg"
                                            />
                                        </div>
                                    );
                                })}
                        </div>
                    </div>
                    <div className={styles.bigText}>
                        {rowText &&
                            rowText.map((item,index) => {
                                return (
                                    <div
                                        key={index}
                                        className={`${
                                            styles.bigTextItem
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
                                        ???????????? | User Login
                                    </div>
                                    <Form onSubmit={this.handleSubmit}>
                                        <FormItem>
                                            {getFieldDecorator("userId",{
                                                rules: [
                                                    {
                                                        required: true,
                                                        message:
                                                            "?????????????????????"
                                                    }
                                                ]
                                            })(
                                                <Input
                                                    prefix={
                                                        <Icon
                                                            type="user"
                                                            style={{
                                                                color:
                                                                    "rgba(0,0,0,.25)"
                                                            }}
                                                        />
                                                    }
                                                    placeholder="?????????"
                                                // onFocus={() => {
                                                //     $(".shadow").fadeIn();
                                                // }}
                                                // onBlur={() => {
                                                //     $(".shadow").fadeOut();
                                                // }}
                                                />
                                            )}
                                        </FormItem>
                                        <FormItem>
                                            {getFieldDecorator("userPwd",{
                                                rules: [
                                                    {
                                                        required: true,
                                                        message: "??????????????????"
                                                    }
                                                ]
                                            })(
                                                <Input
                                                    prefix={
                                                        <Icon
                                                            type="lock"
                                                            style={{
                                                                color:
                                                                    "rgba(0,0,0,.25)"
                                                            }}
                                                        />
                                                    }
                                                    type="password"
                                                    placeholder="??????"
                                                // onFocus={() => {
                                                //     $(".shadow").fadeIn();
                                                // }}
                                                // onBlur={() => {
                                                //     $(".shadow").fadeOut();
                                                // }}
                                                />
                                            )}
                                        </FormItem>
                                        <FormItem>
                                            {getFieldDecorator("remember",{
                                                valuePropName: "checked",
                                                initialValue: true
                                            })(<Checkbox>??????7???</Checkbox>)}

                                            <Button
                                                loading={loading}
                                                type="primary"
                                                htmlType="submit"
                                                style={{ width: "100%" }}
                                            >
                                                {"??????"}
                                            </Button>
                                            {/* <a style={{ float: "right" }} href="javascript:void(0);" onClick={() => { dispatch(push(`/forgot`)) }}>???????????????</a> */}
                                            {/* {"??????"}
                                        <a href="javascript:void(0);" onClick={() => {
                                            dispatch(push(`/register`))
                                        }}>{"????????????"}!</a>                                     */}
                                        </FormItem>
                                    </Form>
                                </div>
                            ) : (
                                    ""
                                )}
                        </div>
                        <div
                            className={`${styles.tab} ${
                                loginType === "1" ? styles.static : styles.quick
                                }`}
                            onClick={() => {
                                dispatch(
                                    tabLoginType(loginType === "1" ? "5" : "1")
                                );
                            }}
                        >
                            {/*  */}
                        </div>
                        <div className="login-tip">
                            <div className="poptip">
                                <div className="poptip-arrow">
                                    <em />
                                    <span />
                                </div>
                                <div className="poptip-content">
                                    {loginType === "1"
                                        ? "????????????"
                                        : "????????????"}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                {/* <div className={styles.footer}>{`${appInfo.copyright}`}</div> */}
            </div>
        );
    }
}

const WrappedNormalLoginForm = Form.create()(NormalLoginForm);

export default blank(Home);
