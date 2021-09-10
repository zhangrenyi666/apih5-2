import React, { Component } from "react";
import s from './style.less';
import { Drawer, Button, Spin } from 'antd';
import QnnForm from "../../../modules/qnn-form";
import $ from 'jquery';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: [],
            autoplay: true,
            visible: false,
            DrawerData: null,
            scrollTops: 0
        }
    }
    componentDidMount() {
        this.setState({
            loading: true
        })
        this.props.myFetch('getHomeZjTzNoteInstructSug', {}).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        data: data
                    }, () => {
                        setTimeout(() => {
                            this.scrollData();
                        }, 2000)
                    })
                } else {
                    this.setState({
                        loading: false,
                    })
                }
            }
        );
    }
    componentWillUnmount() {
        clearInterval(this.timer);
    }
    scrollData = () => {
        const dataList = this.state.data;
        if (dataList.length) {
            window.cancelAnimationFrame(this.timer);
            this.timer = window.requestAnimationFrame(() => {
                this.setState({
                    scrollTops: this.state.scrollTops + 0.066666
                }, () => {
                    $('#bottomCB').scrollTop(this.state.scrollTops);
                    if (this.state.scrollTops >= $('#bottomCB').get(0).scrollHeight - $('#bottomCB').height()) {
                        window.cancelAnimationFrame(this.timer);
                        setTimeout(() => {
                            let delEle = dataList.shift();
                            dataList.push(delEle);
                            this.setState({
                                scrollTops: 0,
                                data: dataList
                            }, () => {
                                setTimeout(() => {
                                    this.scrollData();
                                }, 2000)
                            })
                        }, 8000)
                    } else {
                        this.scrollData();
                    }
                })
            });
        }
    }
    render() {
        const { data, visible, DrawerData, loading } = this.state;
        return (
            <div className={s.BulletinBoard}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        公告栏
                    </div>
                </div>
                <div className={s.bottom}>
                    <Spin spinning={loading}>
                        {
                            data.length ? data.map((item, index) => {
                                return (
                                    <div className={s.bottomC} key={index} onClick={() => {
                                        this.setState({
                                            DrawerData: item,
                                            visible: true
                                        })
                                    }}>
                                        <div className={s.bottomCT}>
                                            {item.title}
                                        </div>
                                        <div className={s.bottomCB} id='bottomCB'>
                                            {item.registerPerson}
                                        </div>
                                    </div>
                                )
                            }) : <div style={{ width: '100%', height: '100%', textAlign: 'center', marginTop: '3%', color: 'white', fontWeight: 'bold' }}>暂无数据展示...</div>
                        }
                    </Spin>
                </div>
                {
                    visible ? <Drawer
                        title="详情"
                        placement="right"
                        width={'1000px'}
                        onClose={() => {
                            this.setState({
                                visible: false
                            })
                        }}
                        className='DrawerQnnForm'
                        visible={visible}
                        bodyStyle={{ height: window.innerHeight - 155 }}
                        footer={<div style={{ textAlign: 'right', paddingRight: '20px' }}><Button type="dashed" onClick={() => {
                            this.setState({
                                visible: false
                            })
                        }}>取消</Button></div>}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig={{
                                apiName: 'getZjTzNoteInstructSugDetails',
                                otherParams: {
                                    noteInstructSugId: DrawerData.noteInstructSugId
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'noteInstructSugId',
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
                                    label: '登记人',
                                    field: 'publisher',
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
                                    disabled: true,
                                },
                                {
                                    type: 'textarea',
                                    label: '主要内容',
                                    field: 'registerPerson',
                                    disabled: true,
                                    autoSize: {
                                        minRows: 2
                                    },
                                    placeholder: '请输入',
                                },
                                {
                                    label: '附件',
                                    field: 'zjTzFileList',
                                    type: 'files',
                                    disabled: true,
                                    showDownloadIcon: true,//是否显示下载按钮
                                    onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                    }
                                }
                            ]}
                        />
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;