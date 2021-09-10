import React, { Component } from 'react';
import { Result, Icon, WhiteSpace, WingBlank, Flex, Button } from 'antd-mobile';
import { withRouter } from 'react-router'
import { clearLocalDB, getCurUri } from '../../main';
class ErrPage extends Component {
    render() {
        const { state, message, history } = this.props
        // const myImg = src => <img src={src} style={{ width: "1rem", height: "1rem" }} className="am-icon am-icon-md" alt="" />;
        let com;
        switch (state) {
            case -2:
                // com = <Result
                //     img={myImg('https://gw.alipayobjects.com/zos/rmsportal/HWuSTipkjJRfTWekgTUG.svg')}
                //     title="正在处理"
                //     message={message}
                // />
                com = <div className="lny-root"><div className="lny-logo"></div><h1>中交一公局</h1></div>
                break;
            case -1:
                com = <Result
                    img={<Icon type="cross-circle-o" style={{ fill: '#F13642', width: "1rem", height: "1rem" }} />}
                    title="无权限访问"
                    message={message}
                />
                break;
            case 0:
                // com = <Result
                //     img={myImg('https://gw.alipayobjects.com/zos/rmsportal/HWuSTipkjJRfTWekgTUG.svg')}
                //     title="正在处理"
                //     message={message}
                // />
                com = <div className="lny-root"><div className="lny-logo"></div><h1>中交一公局</h1></div>
                break;
            case 2:
                // com = <Result
                //     img={myImg('https://gw.alipayobjects.com/zos/rmsportal/GIyMDJnuqmcqPLpHCSkj.svg')}
                //     title="授权信息过期"
                //     message={message}
                // />
                com = <div className="lny-root"><div className="lny-logo"></div><h1>中交一公局</h1></div>
                break;
            case 3:
                // com = <Result
                //     img={myImg('https://gw.alipayobjects.com/zos/rmsportal/GIyMDJnuqmcqPLpHCSkj.svg')}
                //     title="未加入任何部门"
                //     message={message}
                // />
                com = <Result
                    img={<Icon type="cross-circle-o" style={{ fill: '#F13642', width: "1rem", height: "1rem" }} />}
                    title="无权限访问"
                    message={message}
                />
                break;
            case 4:
                com = <Result
                    img={<Icon type="cross-circle-o" style={{ fill: '#F13642', width: "1rem", height: "1rem" }} />}
                    title="无权限访问"
                    message={message}
                />
                // com = <Result
                //     img={<Icon type="cross-circle-o" style={{ fill: '#F13642', width: "1rem", height: "1rem" }} />}
                //     title="用户信息有误"
                //     message={message}
                // />
                break;
            default:
                com = <Result
                    img={<Icon type="cross-circle-o" style={{ fill: '#F13642', width: "1rem", height: "1rem" }} />}
                    title="无权限访问"
                    message={message}
                />
                // com = <Result
                //     img={<Icon type="cross-circle-o" style={{ fill: '#F13642', width: "1rem", height: "1rem" }} />}
                //     title="未知错误"
                //     message={"未知错误，请联系管理员"}
                // />
                break;
        }
        return (
            <div className="page flexBox column">
                <div className="flex">{com}</div>
                {state !== 0 && state !== 2 ?
                    <div className="lny-color-white-bg">
                        <WhiteSpace />
                        <WingBlank>
                            <Flex >
                            <Flex.Item><Button onClick={() => {
                                    clearLocalDB()
                                    const { noQueryStr, domainStr } = getCurUri();
                                    document.location.href = document.location.href === noQueryStr ? domainStr : noQueryStr
                                }} type="primary">{"清理缓存重试"}</Button></Flex.Item>                            
                                </Flex>
                        </WingBlank>
                        <WhiteSpace />
                    </div > : ""}
            </div>
        )
    }
}
export default withRouter(ErrPage);