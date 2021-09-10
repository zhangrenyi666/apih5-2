import React, { Component } from "react";
import { Spin } from "antd";
import s from "./style.less";
import $ from "jquery"
class Readme extends Component {
    state = {
        loading: true,
        data: ``,
    };
    isDidMount = false;
    componentDidMount() {
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                data: `测试数据`,
            })
        }
        this.isDidMount = true;
    }
    componentWillUnmount() {
        this.isDidMount = false;
        this.scrollFn = null;
    }

    scrollTop = 0;
    scrollFn = () => {
        const { seconds = 60 } = this.state; 
        // const seconds = 30;
        $(`.${s.con} .${s.condiv}`).css({
            transform: `translateY(0px)`,
            transition: `0s linear`,
        });
        
        // console.log('执行动画')
        $(`.${s.con} .${s.condiv}`).css({
            transform: `translateY(-${$(`.${s.con}`)?.[0]?.scrollHeight}px)`,
            transition: `${seconds}s linear`,
        });
        // + ($(`.${s.con}`)?.height?.() || 0)
        clearTimeout(this.scrollFnTimer);
        this.scrollFnTimer = setTimeout(() => {
            // console.log('重新执行调用')
            this.scrollFn && this.scrollFn()
        }, (seconds - 10) * 1000)

    }

    refresh = async () => {
        const { myFetch, errMsg } = this.props;
        const { data, success, message, code } = await myFetch("getZjProZcProIntroduceList", {});
        if (success) {
            //处理数据data
            this.isDidMount && this.setState({
                data: data[0]?.introduce,
                seconds: (data[0]?.seconds === 0 ? 60 : data[0]?.seconds),
                loading: false
            }, () => {
                this.scrollFn();
            });
        } else {
            errMsg(message, code);
        }
    };

    render() {
        const {
            loading,
            data,
        } = this.state;
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        工程简介
                    </div>
                    <div className={s.con}>
                        <div className={s.condiv} dangerouslySetInnerHTML={{ __html: data }}></div>
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Readme;
