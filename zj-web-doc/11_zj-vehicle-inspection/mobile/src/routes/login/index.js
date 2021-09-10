import React from 'react';
import { connect } from 'react-redux'
import { Redirect } from 'react-router-dom';
import { Toast } from '../../components';
import { localDB } from '../../tools';
import { User } from '../../module';
import { app } from '../../main';
import { push } from 'react-router-redux';
import { createForm } from 'rc-form';
import { WhiteSpace, InputItem, Button } from 'antd-mobile'; 
import styles from './index.less' 
const { get: getLocalDB, set: setLocalDB, remove: removeLocalDB } = localDB;
const Login = ({ dispatch, status, uri }) => {
    setLocalDB("status", status)
    if (User.isLogin(status)) {
        let redirect_url = getLocalDB("redirect_url") || `/`
        removeLocalDB("redirect_url");
        return <Redirect to={redirect_url} />
    } else if (User.isOauth2()) {
        Toast.show(() => { });
        User.loginByOauth2(uri).then(({ message, action }) => {
            Toast.hide(() => {
                dispatch(action)
            })
        })
        return <div />
    } else {
        return (
            <WrappedNormalLoginForm dispatch={dispatch} />
        )
    }
}
const select = ({ status }) => {
    return {
        status,
    }
}

class NormalLoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            actionIndex: 0,
        }
        this.btnLoading = this.btnLoading.bind(this)
    } 

    btnLoading(isLoading, loadingText = '', cb) {
        if (typeof loadingText === 'function') { loadingText = () => { } }
        if (isLoading) {
            this.setState({ btnLoading: true, loadingText }, cb)
        } else {
            this.setState({ btnLoading: false, loadingText }, cb)
        }
    }

    forget() {
        console.log('忘记密码！')
    }

    //登录
    land() {
        this.btnLoading(true, '登录中', () => {
            const { dispatch, form: { validateFields } } = this.props
            validateFields((err, values) => {
                const { username, password, remember } = values
                if (!err) {
                    console.log(username, password, remember)
                    const body = {
                        remember: remember,
                        userId: username,
                        userPwd: password,
                    }
                    User.login(body).then(({ message, action }) => {
                        this.btnLoading(false, '登录成功', () => {
                            dispatch(action)
                        })
                    }).then(()=>{
                        this.btnLoading(false, '登录')
                    })
                }else{
                    this.btnLoading(false, '登录')
                }
            });
        });
    }

    render() {
        const { dispatch, form: { getFieldDecorator } } = this.props
        let { actionIndex, btnLoading, loadingText, remember } = this.state;
        const { getFieldProps } = this.props.form;
        return (
            <div className={styles.box}>
                <div className={styles.title}>{app.name}</div>
                <div className={styles.content}>
                    <div className={styles.myTabs} style={{display:'none'}}>
                        <div className={`${styles.tabsItem} ${actionIndex === 0 ? styles.action : ''}`}
                            onClick={() => { this.setState({ actionIndex: 0 }) }}>登录</div>
                        <div className={`${styles.tabsItem} ${actionIndex === 1 ? styles.action : ''}`}
                            onClick={() => { this.setState({ actionIndex: 1 }) }}>注册
                        </div>
                    </div>
                    <div className={styles.formCon}>
                        {
                            actionIndex === 0
                                ?
                                <div className={styles.inpsCon}>
                                    <div className={styles.inpItem}> 
                                        <InputItem
                                            className={styles.input}
                                            {...getFieldProps('username', {
                                                rules: [{ required: true, message: '请输入账号' }]
                                            })}
                                            clear
                                            labelNumber={3}
                                            placeholder="请输入账号"
                                            ref={el => this.customFocusInst = el}
                                        >账号</InputItem>
                                    </div>
                                    <div className={styles.inpItem}> 
                                        <InputItem
                                            labelNumber={3}
                                            className={styles.input}
                                            {...getFieldProps('password', {
                                                rules: [{ required: true, message: '请输入密码' }]
                                            })}
                                            clear
                                            type="password"
                                            placeholder="请输入密码"
                                            ref={el => this.customFocusInst = el}
                                        >密码</InputItem>
                                    </div>
                                    <div className={styles.freeLand}>
                                        {getFieldDecorator('remember', {
                                            valuePropName: 'checked',
                                            initialValue: false,
                                        })(
                                            <div>
                                                <span
                                                    style={{ background: remember ? '#108ee9' : '' }}
                                                    onChange={(e) => {
                                                        this.setState({ remember: e.target.checked })
                                                    }} className={styles.checkboxCon}>
                                                    <input type="checkbox" />
                                                </span>
                                                <span>保存7天</span>
                                            </div>
                                        )}
                                    </div>

                                    <div className={styles.footer}>
                                        <div className={styles.fLine}></div>
                                        <div className={styles.forget} onClick={this.forget.bind(this)}>忘记密码 </div>
                                    </div> 
                                </div>
                                :
                                <div className={styles.register}>
                                    <p>暂不支持注册</p>
                                </div>
                        }

                    </div>

                    <div>
                        {
                            actionIndex === 0
                                ?
                                <Button loading={btnLoading ? true : false} className={styles.button} onClick={this.land.bind(this)} type="primary">{loadingText ? loadingText : '登录'}</Button>
                                :
                                ''
                        }

                    </div>

                </div>
            </div> 
        );
    }
}

// const WrappedNormalLoginForm = Form.create()(NormalLoginForm);
const WrappedNormalLoginForm = createForm()(NormalLoginForm)
 
export default connect(select)(Login)