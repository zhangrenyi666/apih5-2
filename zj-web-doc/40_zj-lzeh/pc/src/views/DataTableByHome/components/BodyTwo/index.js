import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import Apih5 from 'qnn-apih5'
class Readme extends Apih5 {
    state = {
        loading: true,
        data: ""
    };
    componentDidMount() {
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                // data: `测试数据`,
            })
        }
    }
    refresh = async () => {
        const { myFetch,errMsg } = this.props;
        const { data,success,message,code } = await myFetch("getZjLzehVideo",{});
        if (success) {
            //处理数据data
            this._isMounted && this.setState({
                data: data?.fileList?.[0]?.url,
                loading: false
            });
        } else {
            errMsg(message,code);
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
                        宣传片
                    </div>
                    <div className={s.con}>
                        <video
                            src={data}
                            autoPlay={true} loop={true}
                            ref={(me) => {
                                if (me) {
                                    setTimeout(() => {
                                        // 设置静音
                                        // 设置进度为0
                                        me.currentTime = 1;
                                        me.volume = 0;
                                    },100)
                                }
                            }}
                        ></video>
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Readme;
