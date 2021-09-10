import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import moment from 'moment';
import { ImgPreview } from "qnn-form";
import s from "./style.less";
import { push } from 'react-router-redux';
import { message as Msg, Modal, Spin } from "antd";
const confirm = Modal.confirm;
const play = require('./play.png');
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visibleSend: false,
            loadingSend: false,
            videoId: '',
            videoUrl: '',
            visibleVideo: false,
            visibleReplay: false,
            loadingReplay: false,
            releaseId: ''
        }
    }
    componentDidMount () {

    }
    handleCancelReplay = () => {
        this.setState({ visibleReplay: false, loadingReplay: false });
    }
    handleCancelVideo = () => {
        this.setState({ visibleVideo: false });
    }
    render () {
        const { visibleSend, loadingSend, videoId, videoUrl, visibleVideo, visibleReplay, loadingReplay, releaseId } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    {...window.PublicityVideoPage}
                    desc={ext1 === '1' ? '发布：使数据全平台用户可见；广而告之：将已发布的数据展示至首页' : null}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'videoId',
                                type: 'string',
                                hide: true,
                            }
                        }, {
                            isInSearch: false,
                            table: {
                                title: '标题',
                                dataIndex: 'title',
                                onClick: 'detail',
                                filter: true,
                                width: 300,
                                tooltip: 25,
                                key: 'title',
                            },
                            form: {
                                type: 'string',
                                placeholder: '请输入',
                                required: true,
                            }
                        }, {
                            table: {
                                title: '发布日期',
                                dataIndex: 'releaseDate',
                                key: 'releaseDate',
                                filter: true,
                                format: 'YYYY-MM-DD'
                            },
                            form: {
                                field: 'releaseDate',
                                type: 'date',
                                required: true,
                                placeholder: '请输入'
                            },
                        },

                        {
                            table: {
                                title: '主讲人',
                                dataIndex: 'keynoteSpeaker',
                                filter: true,
                                key: 'keynoteSpeaker'
                            },
                            form: {
                                field: 'keynoteSpeaker',
                                required: true,
                                type: 'string',
                                placeholder: '请输入'
                            },
                        },
                        {
                            table: {
                                title: '内容简介',
                                dataIndex: 'content',
                                filter: true,
                                tooltip: 14,
                                key: 'content'
                            },
                            form: {
                                type: "textarea",
                                required: true,
                                field: "content",
                                autoSize: {
                                    minRows: 2
                                },
                            },
                        },

                        {
                            table: {
                                title: '视频',
                                width: 80,
                                dataIndex: 'zjTzCoverList',
                                key: 'zjTzCoverList',
                                align: 'center',
                                onClick: (obj) => {
                                    if (obj.rowData && obj.rowData.videoList && obj.rowData.videoList.length > 0) {
                                        // obj.rowData.videoList[0].name = obj.rowData.videoName;
                                        const { myFetch } = obj.props;
                                        myFetch('addZjTzVideoHistory', { videoId: obj.rowData.videoId }).then(({ success, data, message }) => {
                                            if (success) {
                                                this.setState({
                                                    videoUrl: obj.rowData.videoList,
                                                    visibleVideo: true
                                                })
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    } else {
                                        confirm({ title: '未加载到视频' })
                                    }
                                },
                                render: (data) => {
                                    return '<img style="height:19px" alt="图片加载失败" src=' + play + ' />'
                                }
                            },
                            isInForm: false
                        },
                        {
                            isInTable: false,
                            // form: {
                            //     label: "视频(3G以内)",
                            //     field: "videoList",
                            //     required:true,
                            //     type: "files",
                            //     max: 1,
                            //     fetchConfig: {
                            //         apiName: window.configs.domain + "upload"
                            //     }
                            // },
                            form: {
                                label: "视频(3G以内)",
                                field: "videoList",
                                required: true,
                                type: "breakpointResume",
                                max: 1,
                                fetchConfig: {
                                    apiName: window.configs.apis.files
                                },
                                accept: 'audio/mp4,video/mp4',
                                hide: (obj) => {
                                    if (obj.Pstate.drawerDetailTitle === "新增") {
                                        return false
                                    }
                                    return true
                                }
                            }
                        },
                        {
                            table: {
                                title: '状态',
                                dataIndex: 'releaseName',
                                key: 'releaseName',
                                filter: true,
                                width: 100
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "releaseId",
                                hide: true,
                                optionData: [
                                    {
                                        label: "未发布",
                                        value: "0"
                                    },
                                    {
                                        label: "发布",
                                        value: "1"
                                    }
                                ]
                            },
                        },
                        {
                            table: {
                                title: '是否在首页广而告之',
                                dataIndex: 'homeShow',
                                filter: true,
                                width: 160,
                                key: 'homeShow',
                                render: (data) => {
                                    if (data === '0') {
                                        return '否'
                                    } else {
                                        return '是'
                                    }
                                }
                            },
                            isInForm: false,
                            form: {
                                type: "select",
                                field: "homeShow",
                                hide: true,
                                optionData: [
                                    {
                                        label: "否",
                                        value: "0"
                                    },
                                    {
                                        label: "是",
                                        value: "1"
                                    }
                                ]
                            }
                        },
                        {
                            isInForm: false,
                            isInTable: this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '1' ? true : false,
                            table: {
                                title: "操作",
                                fixed: 'right',
                                dataIndex: 'action',
                                key: 'action',
                                align: "center",
                                noHaveSearchInput: true,
                                showType: "bubble",
                                width: 80,
                                btns: [
                                    {
                                        name: 'diy',
                                        render: function (rowData) {
                                            return '<a>浏览详情</a>'
                                        },
                                        onClick: (obj) => {
                                            // const { myFetch } = obj.props;
                                            // myFetch('addZjTzVideoHistory', { videoId: obj.rowData.videoId }).then(({ success, data, message }) => {
                                            //     if (success) {
                                            //         Msg.success(message);
                                            //         dispatch(push(`${mainModule}videoDetail/${obj.rowData.videoId}`));
                                            //     } else {
                                            //         Msg.error(message);
                                            //     }
                                            // });
                                            dispatch(push(`${mainModule}videoDetail/${obj.rowData.videoId}`));
                                        }
                                    },
                                    {
                                        name: 'diy',
                                        render: function (rowData) {
                                            return '<a>留言记录</a>'
                                        },
                                        onClick: (obj) => {
                                            // const { myFetch } = obj.props;
                                            // myFetch('addZjTzVideoHistory', { videoId: obj.rowData.videoId }).then(({ success, data, message }) => {
                                            //     if (success) {
                                            //         Msg.success(message);
                                            //         dispatch(push(`${mainModule}videoReplayDetail/${obj.rowData.videoId}`));
                                            //     } else {
                                            //         Msg.error(message);
                                            //     }
                                            // });
                                            dispatch(push(`${mainModule}videoReplayDetail/${obj.rowData.videoId}`));
                                        }
                                    }
                                ]
                            }
                        }
                    ]}
                    method={{
                        editClick: (obj) => {
                            if (obj.selectedRows[0].releaseId === '1') {
                                obj.btnCallbackFn.closeDrawer();
                                Msg.warn('已发布的不能修改!');
                                this.table.clearSelectedRows();
                            } else {
                                this.table.clearSelectedRows();
                            }
                        },
                        faBuClick: (obj) => {//发布
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {

                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('已发布的消息不能发布！');
                                } else {
                                    confirm({
                                        title: "确定发布么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteReleaseZjTzVideo', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        cheHuiClick: (obj) => {//撤回
                            this.table.clearSelectedRows();
                            const { myFetch } = obj.props;
                            if (obj.selectedRows.length > 0) {
                                let aa = [];
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '0') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    Msg.warn('未发布的消息不能撤回！');
                                } else {
                                    confirm({
                                        title: "确定撤回么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteRecallZjTzVideo', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    });
                                }


                            } else {
                                Msg.warn('请选择数据');
                            }

                        },

                        guangErGaoZhi: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].releaseId === '1') {

                                        confirm({
                                            title: "确定广而告之到首页么？",
                                            okText: "确认",
                                            cancelText: "取消",
                                            onOk: () => {
                                                this.props.myFetch('toHomeShowZjTzVideo', obj.selectedRows[0]).then(
                                                    ({ success, message, data }) => {
                                                        if (success) {
                                                            Msg.success(message);
                                                            this.table.refresh();
                                                        } else {
                                                            Msg.error(message)
                                                        }
                                                    }
                                                );
                                            }
                                        })
                                    } else {
                                        Msg.warn('未发布的消息不能广而告之！')
                                    }

                                } else {
                                    Msg.warn('请选择一条数据');
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }
                        },
                        replay: (obj) => {
                            this.table.clearSelectedRows();
                            if (obj.selectedRows.length > 0) {
                                if ((obj.selectedRows.length === 1)) {
                                    if (obj.selectedRows[0].releaseId === '1') {
                                        this.setState({
                                            visibleReplay: true,
                                            videoId: obj.selectedRows[0].videoId
                                        })
                                    } else {
                                        Msg.warn('未发布的消息不能留言！')
                                    }
                                } else {
                                    Msg.warn('请选择一条数据');
                                }
                            } else {
                                Msg.warn('请选择数据');
                            }

                        },
                        delClick: (obj) => {
                            const { myFetch } = obj.props;
                            let aa = [];
                            if (obj.selectedRows.length > 0) {
                                for (var i = 0; i < obj.selectedRows.length; i++) {
                                    if (obj.selectedRows[i].releaseId === '1') {
                                        aa.push(obj.selectedRows[i].releaseId);
                                    }
                                }
                                if (aa.length > 0) {
                                    obj.btnCallbackFn.closeDrawer();
                                    Msg.warn('已发布的不能删除!');
                                    this.table.clearSelectedRows();
                                } else {
                                    confirm({
                                        title: "确定删除么?",
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            myFetch('batchDeleteUpdateZjTzVideo', obj.selectedRows).then(
                                                ({ success, message }) => {
                                                    if (success) {
                                                        Msg.success(message);
                                                        this.table.refresh();
                                                        this.table.clearSelectedRows();
                                                    } else {
                                                        Msg.error(message)
                                                    }
                                                }
                                            );
                                        }
                                    })

                                }
                            } else {
                                Msg.warn('请选择数据！')
                            }
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                />
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="留言"
                    visible={visibleReplay}
                    footer={null}
                    onCancel={this.handleCancelReplay}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelReplay}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingReplay}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    type: 'string',
                                    field: "videoId",
                                    initialValue: videoId,
                                    hide: true
                                },
                                {
                                    field: 'note',
                                    type: 'textarea',
                                    label: "留言",
                                    required: true,
                                    placeholder: '请输入'
                                },
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                    isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleReplay: false,
                                            loadingReplay: false
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingReplay: true
                                        })
                                        this.props.myFetch('addZjTzVideoNote', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.table.refresh();
                                                    this.setState({
                                                        visibleReplay: false,
                                                        loadingReplay: false
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );

                                    }
                                }
                            ]}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }}
                        />
                    </Spin>
                </Modal>
                {/* <Modal
                        width='800px'
                        style={{ top: '0' }}
                        title="视频"
                        visible={visibleVideo}
                        footer={null}
                        onCancel={this.handleCancelVideo}
                        bodyStyle={{ width:'800px',textAlign:'center'}}
                        centered={true}
                        destroyOnClose={this.handleCancelVideo}
                        wrapClassName={'modals'}
                >
                    <video src={videoUrl} controls="controls" width="700" ></video>
                </Modal> */}
                {visibleVideo ? <ImgPreview
                    curIndex={0}
                    onClose={() => {
                        this.setState({
                            visibleVideo: false
                        })
                    }}
                    fileList={videoUrl}
                    allVideo
                /> : null}
            </div>
        );
    }
}

export default index;