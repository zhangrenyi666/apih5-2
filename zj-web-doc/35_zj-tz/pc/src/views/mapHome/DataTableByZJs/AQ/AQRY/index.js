import React, { Component } from 'react'
import { Spin } from 'antd'; 
import s from './style.less';
class AQRY extends Component {
    state = {
        aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 27) * 0.65,
        data: [],
        loading: false
    }
    componentDidMount() {
        this.refresh();
        window.addEventListener('resize', this.autoSize, false);
    }
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 27) * 0.65,
        })
    }
    refresh = () => {
        this.setState({ loading: true });
        this.setState({
            loading: false
        });
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.autoSize)
    }
    render() {
        const { loading, aqH } = this.state;
        return (
            <div className={s.AQRY}>
                <div className={s.title}>公告</div>
                <Spin spinning={loading}>
                    <div className={s.center} style={{ height: aqH }}>
                    蚂蚁的企业级产品是一个庞大且复杂的体系。这类产品不仅量级巨大且功能复杂，而且变动和并发频繁，常常需要设计与开发能够快速的做出响应。同时这类产品中有存在很多类似的页面以及组件，可以通过抽象得到一些稳定且高复用性的内容
                    </div>
                </Spin>
            </div>
        )
    }
}
export { AQRY }