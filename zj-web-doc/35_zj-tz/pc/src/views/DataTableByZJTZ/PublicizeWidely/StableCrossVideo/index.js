import React, { Component } from "react";
import s from './style.less';
import { Drawer, Button, message as Msg, Modal, Spin } from 'antd';
import QnnForm from "../../../modules/qnn-form";
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: {},
            visible: false,
            DrawerData: null,
            MsgModalvisible: false
        }
    }
    componentDidMount() {
        const Playerjs = window.Playerjs;
        this.setState({
            loading: true
        })
        this.props.myFetch('getZjTzVideoHome', {}).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        data: data
                    },() => {
                        if (document.querySelector(`#imgPreview-video`) && Playerjs) {  
                            new Playerjs({
                                id: `imgPreview-video`,
                                autoplay: false,
                                loop: false,
                                file: data?.videoUrl,
                                controlslist:'nodownload'
                            }); 
                        }
                    })
                } else {
                    this.setState({
                        loading: false,
                    })
                }
            }
        );
    }
    render() {
        const { data, loading, visible, DrawerData, MsgModalvisible } = this.state;
        return (
            <div className={s.StableCrossVideo}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        宣贯视频
                    </div>
                    <div className={s.leftTopOneC} onClick={() => {
                        if (data?.videoId) {
                            this.setState({
                                visible: true,
                                DrawerData: data
                            })
                        }
                    }}>
                        {data?.title}
                    </div>
                    <div className={s.leftTopOneR}>
                        {data?.videoId ? <Button type="primary" onClick={() => {
                            this.setState({
                                MsgModalvisible: true,
                                DrawerData: data
                            })
                        }}>匿名留言</Button> : null}
                    </div>
                </div>
                <div className={s.center}>
                    <Spin spinning={loading}>
                        {data?.videoUrl ? <div id={`imgPreview-video`} style={{ width: "100%", height: "100%" }}></div> : <div style={{ width: '100%', textAlign: 'center', marginTop: '15%', color: 'white', fontWeight: 'bold' }}>暂无数据展示...</div>}
                    </Spin>
                </div>
                {
                    visible ? <Drawer
                        title="详情"
                        placement="right"
                        width={'1000px'}
                        onClose={() => {
                            this.setState({
                                visible: false,
                                // autoplay: true
                            })
                        }}
                        className='DrawerQnnForm'
                        visible={visible}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visible: false,
                                // autoplay: true
                            })
                        }}>取消</Button></div>}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig={{
                                apiName: 'getZjTzVideoDetails',
                                otherParams: {
                                    videoId: DrawerData.videoId
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'videoId',
                                    type: 'string',
                                    hide: true,
                                },
                                {
                                    label: '标题',
                                    field: 'title',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '发布日期',
                                    field: 'releaseDate',
                                    type: 'date',
                                    placeholder: '请选择',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '主讲人',
                                    field: 'keynoteSpeaker',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    type: 'textarea',
                                    label: '内容简介',
                                    field: 'content',
                                    required: true,
                                    disabled: true,
                                    autoSize: {
                                        minRows: 2
                                    },
                                    placeholder: '请输入',
                                },
                                {
                                    label: '视频(500M以内)',
                                    field: 'zjTzFileList',
                                    type: 'files',
                                    disabled: true,
                                    required: true,
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                    }
                                }
                            ]}
                        />
                    </Drawer> : null
                }
                {MsgModalvisible ? <Modal
                    width={'500px'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="请输入留言内容"
                    visible={MsgModalvisible}
                    footer={null}
                    bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                    centered={true}
                    closable={false}
                    maskClosable={false}
                    wrapClassName={'replyData'}
                >
                    <QnnForm
                        {...this.props}
                        match={this.props.match}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        data={DrawerData}
                        formConfig={[
                            {
                                type: 'string',
                                field: "videoId",
                                hide: true
                            },
                            {
                                field: 'note',
                                type: 'textarea',
                                required: true,
                                placeholder: '请输入',
                            },
                        ]}
                        btns={[
                            {
                                name: 'cancel',
                                type: 'dashed',
                                label: '返回',
                                isValidate: false,
                                onClick: () => {
                                    this.setState({
                                        MsgModalvisible: false,
                                        autoplay: true
                                    })
                                }
                            },
                            {
                                name: 'submit',
                                type: 'primary',
                                label: '确定',
                                onClick: (obj) => {
                                    this.props.myFetch("addZjTzVideoNote", obj.values).then(({ success, data, message }) => {
                                        if (success) {
                                            this.setState({
                                                MsgModalvisible: false,
                                                autoplay: true
                                            })
                                            Msg.success('留言成功!');
                                        } else {
                                            Msg.error(message);
                                        }
                                    })
                                }
                            }
                        ]}
                        formItemLayout={{
                            labelCol: {
                                xs: { span: 8 },
                                sm: { span: 8 }
                            },
                            wrapperCol: {
                                xs: { span: 16 },
                                sm: { span: 16 }
                            }
                        }}
                    />
                </Modal> : null}
            </div>
        );
    }
}

export default index;