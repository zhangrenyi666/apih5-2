import React, { Component } from "react";
import MyList from "../modules/MList";
import { Modal, Spin } from "antd";
import s from "./style.less";
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            mobileUrl: '',
            visible: false,
            flag: true,
            loading: false
        };
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        const { visible, loading, flag, mobileUrl } = this.state;
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <div className={s.title}>
                        中交一公局集团2020年信息化工作会议
                    </div>
                    <div className={s.logo}>
                        <div style={{ width: '55px', height: '55px', background: 'white', borderRadius: '5px', margin: '0 auto' }}>
                            <img style={{ width: '100%', height: '100%' }} src="http://weixin.fheb.cn:99/icon/logo.png" alt="" />
                        </div>
                    </div>
                </div>
                <div
                    style={{
                        height: document.documentElement.clientHeight * 0.75
                    }}
                >
                    <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        fetchConfig={{
                            apiName: 'getZjMeetingFileEnclosureList'
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    onClick={() => {
                                        if (flag) {
                                            this.setState({
                                                mobileUrl: item.mobileUrl,
                                                visible: true,
                                                loading: true
                                            })
                                        }else{
                                            this.setState({
                                                mobileUrl: item.mobileUrl,
                                                visible: true,
                                            })
                                        }
                                    }}
                                >
                                    <div className={s.tops}>
                                        <div className={s.topl}>
                                            <img style={{ width: '100%', height: '100%' }} src={require('./zl.png')} alt="" />
                                        </div>
                                        <div className={s.topr}>{item.name}</div>
                                    </div>
                                </div>
                            );
                        }}
                    />
                </div>
                <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="预览"
                    visible={visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    bodyStyle={{ padding: '10px', overflow: 'auto', height: window.innerHeight * 0.8 }}
                    centered={true}
                    destroyOnClose={this.handleCancel}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loading}>
                        <iframe title="myf" width={window.innerWidth * 0.85} height={window.innerHeight * 0.8} frameBorder={0} onLoad={(obj) => {
                            this.setState({ loading: false, flag: false })
                        }} src={mobileUrl}></iframe>
                    </Spin>
                </Modal>
            </div>
        );
    }
}
export default Index;