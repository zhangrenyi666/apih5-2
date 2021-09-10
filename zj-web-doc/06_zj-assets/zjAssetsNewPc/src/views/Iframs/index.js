import React,{ Component } from 'react';
import { Spin } from "antd"
import s from "./style.less"

class index extends Component {
    state = { loading: true }
    render() {
        const { match,routerInfo: { routeTree = [] },loginAndLogoutInfo: { loginInfo: { token } } } = this.props;

        //获取路由配置中的url
        let url = null;
        const loopFn = (data) => {
            for (let i = 0; i < data.length; i++) {
                let item = data[i]; 
                if (match.url === `${item.moduleName}${item.pathName}`) {
                    url = item.url;
                } else if (item.children && item.children.length) {
                    loopFn(item.children);
                }
            }
        }
        loopFn(routeTree);
        if (!url) {
            return <div>未匹配到页面</div>
        }
        return (
            <div style={{ height: "100%" }} key={match.url}>
                <Spin
                    tip="loading..."
                    wrapperClassName={s.spincs}
                    spinning={this.state.loading} >
                    <iframe
                        title="iframe"
                        onLoad={() => this.setState({ loading: false })}
                        style={{ height: "100%",width: "100%",border: "none" }}
                        src={`${url}?code=${token}`}></iframe>
                </Spin>
            </div>
        );
    }
}

export default index;