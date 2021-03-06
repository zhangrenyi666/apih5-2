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
                        ?????????
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
                            }) : <div style={{ width: '100%', height: '100%', textAlign: 'center', marginTop: '3%', color: 'white', fontWeight: 'bold' }}>??????????????????...</div>
                        }
                    </Spin>
                </div>
                {
                    visible ? <Drawer
                        title="??????"
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
                        }}>??????</Button></div>}
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
                                    label: '??????',
                                    field: 'title',
                                    type: 'string',
                                    placeholder: '?????????',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '?????????',
                                    field: 'publisher',
                                    type: 'string',
                                    placeholder: '?????????',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '????????????',
                                    field: 'releaseDate',
                                    type: 'date',
                                    placeholder: '?????????',
                                    disabled: true,
                                },
                                {
                                    type: 'textarea',
                                    label: '????????????',
                                    field: 'registerPerson',
                                    disabled: true,
                                    autoSize: {
                                        minRows: 2
                                    },
                                    placeholder: '?????????',
                                },
                                {
                                    label: '??????',
                                    field: 'zjTzFileList',
                                    type: 'files',
                                    disabled: true,
                                    showDownloadIcon: true,//????????????????????????
                                    onPreview: "bind:_docFilesByOfficeUrl",//365??????
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