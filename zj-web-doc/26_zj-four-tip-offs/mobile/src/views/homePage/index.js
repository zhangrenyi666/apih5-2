import React, { Component } from 'react';
import s from './style.less';
import { Modal, Spin, Button } from 'antd';
import { Toast, Tabs, Modal as ModalMobile, Radio, List } from 'antd-mobile';
import incTab from '../letBottomIncTabs'
import { push } from 'react-router-redux';
const bg = require('../../imgs/bg.png');
const sm = require('../../imgs/sm.svg');
const smr = require('../../imgs/smr.svg');
const nm = require('../../imgs/nm.svg');
const nmx = require('../../imgs/nmx.svg');
const fw = require('../../imgs/fw.svg');
const xl = require('../../imgs/xl.svg');
const jg = require('../../imgs/jg.svg');
const sectionsStyle = {
    backgroundImage: `url(${bg})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class homePage extends Component {
    state = {
        visible: false,
        show: false,
        loading: false,
        data: [],
        datas: [],
        key: this.props.match.params.key === '0' ? '' : (this.props.match.params.key === '3' ? 0 : Number(this.props.match.params.key)),
        path: '',
        type: '0',
        val: '',
        label: '实名举报'
    };
    componentDidMount() {
        // this.refresh();
        // this.refreshs();
        const { wx } = window;
        wx.ready(() => {
            wx.hideOptionMenu();
        })
    }
    // refresh = () => {
    //     this.setState({
    //         loading: true
    //     })
    //     const { myFetch } = this.props;
    //     myFetch('getZjHomePageData', {}).then(({ data, success, message }) => {
    //         if (success) {
    //             this.setState({
    //                 loading: false,
    //                 data: data
    //             })
    //         } else {
    //             Toast.fail(message)
    //         }
    //     })
    // }
    refreshs = () => {
        const { myFetch } = this.props;
        myFetch('getZjUseNoticeDetails', { type: this.state.type }).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    datas: data
                })
            } else {
                Toast.fail(message)
            }
        })
    }
    handleCancel = () => {
        this.setState({
            visible: false,
        });
    }
    onChange = (value) => {
        this.setState({
            val: value
        })
    }
    render() {
        const { loading, data, key, datas, path, show, val, label } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const tabs = [
            { title: <div style={{ border: '1px solid #108ee9', padding: '0 18px', height: '35px', lineHeight: '35px', borderRadius: '5px', background: key === 0 ? '#108ee9' : null, color: key === 0 ? 'white' : '#108ee9', fontWeight: "bold" }}>技术质量</div> },
            { title: <div style={{ border: '1px solid #108ee9', padding: '0 18px', height: '35px', lineHeight: '35px', borderRadius: '5px', background: key === 1 ? '#108ee9' : null, color: key === 1 ? 'white' : '#108ee9', fontWeight: "bold" }}>违规违纪</div> },
            { title: <div style={{ border: '1px solid #108ee9', padding: '0 18px', height: '35px', lineHeight: '35px', borderRadius: '5px', background: key === 2 ? '#108ee9' : null, color: key === 2 ? 'white' : '#108ee9', fontWeight: "bold" }}>安环隐患</div> },
        ];
        const season = [
            {
                label: '技术质量',
                value: 0,
            },
            {
                label: '违规违纪',
                value: 1,
            },
            {
                label: '安环隐患',
                value: 2,
            }
        ];
        return (
            <Spin spinning={loading}>
                <div className={s.root}>
                    <div className={s.top} style={sectionsStyle}>
                        <div className={s.topc}>
                            中交四公局举报平台
                    </div>
                        {/* <div className={s.topb}>
                            <div className={s.topb_d} onClick={() => {
                                dispatch(push(`${mainModule}processeList/0`));
                            }}>
                                <div style={{ fontSize: '22px' }}>{data.toBeTreated}</div>
                                <div style={{ fontSize: '16px' }}>待解决</div>
                            </div>
                            <div className={s.topb_j} onClick={() => {
                                dispatch(push(`${mainModule}processeList/1`));
                            }}>
                                <div style={{ fontSize: '22px' }}>{data.unsolved}</div>
                                <div style={{ fontSize: '16px' }}>未解决</div>
                            </div>
                            <div className={s.topb_t} onClick={() => {
                                dispatch(push(`${mainModule}processeList/2`));
                            }}>
                                <div style={{ fontSize: '22px' }}>{data.solved}</div>
                                <div style={{ fontSize: '16px' }}>已解决</div>
                            </div>
                        </div> */}
                    </div>
                    <div>
                        <Tabs
                            tabs={tabs}
                            page={key}
                            tabBarUnderlineStyle={{ display: 'none' }}
                            swipeable={false}
                            onTabClick={(tab, index) => {
                                this.setState({ key: index })
                            }}
                        >
                            <div>
                                <div style={{ width: '95%', margin: 'auto', height: '18vh', backgroundColor: '#108ee9', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '18vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                                    if (this.state.key === '') {
                                        this.setState({
                                            show: true,
                                            label: '实名举报'
                                        });
                                    } else {
                                        this.setState({
                                            type: '0'
                                        }, () => {
                                            this.refreshs();
                                            this.setState({
                                                path: 'RealName',
                                                visible: true
                                            })
                                        })
                                    }
                                }}>
                                    <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={sm} alt="" />
                                    <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>实名举报</span>
                                </div>
                                <div style={{ width: '95%', margin: 'auto', height: '18vh', backgroundColor: '#efd414', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '18vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                                    if (this.state.key === '') {
                                        this.setState({
                                            show: true,
                                            label: '匿名举报'
                                        });
                                    } else {
                                        this.setState({
                                            type: '0'
                                        }, () => {
                                            this.refreshs();
                                            this.setState({
                                                path: 'Anonymity',
                                                visible: true
                                            })
                                        })
                                    }
                                }}>
                                    <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={nm} alt="" />
                                    <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>匿名举报</span>
                                </div>
                                <div style={{ width: '95%', margin: 'auto', height: '18vh', backgroundColor: '#14efc7', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '18vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                                    this.setState({
                                        type: '3'
                                    }, () => {
                                        this.refreshs();
                                        this.setState({
                                            path: 'Serve',
                                            visible: true
                                        })
                                    })
                                }}>
                                    <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={fw} alt="" />
                                    <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '1%' }}>合作、共享、服务、交流</span>
                                </div>
                            </div>
                            <div>
                                <div style={{ width: '95%', margin: 'auto', height: '18vh', backgroundColor: '#0ee629', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '18vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                                    this.setState({
                                        type: '1'
                                    }, () => {
                                        this.refreshs();
                                        this.setState({
                                            path: 'RealNameW',
                                            visible: true
                                        })
                                    })
                                }}>
                                    <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={smr} alt="" />
                                    <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>实名举报</span>
                                </div>
                                <div style={{ width: '95%', margin: 'auto', height: '18vh', backgroundColor: '#0fe4e4', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '18vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                                    this.setState({
                                        type: '1'
                                    }, () => {
                                        this.refreshs();
                                        this.setState({
                                            path: 'AnonymityW',
                                            visible: true
                                        })
                                    })
                                }}>
                                    <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={nmx} alt="" />
                                    <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>匿名举报</span>
                                </div>
                            </div>
                            <div>
                                <div style={{ width: '95%', margin: 'auto', height: '18vh', backgroundColor: '#00a6f5', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '18vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                                    this.setState({
                                        type: '2'
                                    }, () => {
                                        this.refreshs();
                                        this.setState({
                                            path: 'RealNameA',
                                            visible: true
                                        })
                                    })
                                }}>
                                    <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={sm} alt="" />
                                    <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>实名举报</span>
                                </div>
                                <div style={{ width: '95%', margin: 'auto', height: '18vh', backgroundColor: '#f5cc00', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '18vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                                    this.setState({
                                        type: '2'
                                    }, () => {
                                        this.refreshs();
                                        this.setState({
                                            path: 'AnonymityA',
                                            visible: true
                                        })
                                    })
                                }}>
                                    <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={nm} alt="" />
                                    <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>匿名举报</span>
                                </div>
                            </div>
                        </Tabs>
                        {/* <div style={{ width: '95%', margin: 'auto', height: '20vh', backgroundColor: '#108ee9', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '20vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                            this.setState({
                                show: true,
                                label: '实名举报'
                            });
                        }}>
                            <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={sm} alt="" />
                            <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>实名举报</span>
                        </div>
                        <div style={{ width: '95%', margin: 'auto', height: '20vh', backgroundColor: '#efd414', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '20vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                            this.setState({
                                show: true,
                                label: '匿名举报'
                            });
                        }}>
                            <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={nm} alt="" />
                            <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '10%' }}>匿名举报</span>
                        </div>
                        <div style={{ width: '95%', margin: 'auto', height: '20vh', backgroundColor: '#14efc7', marginTop: '10px', borderRadius: '5px', textAlign: 'center', lineHeight: '20vh', overflow: 'hidden', position: 'relative' }} onClick={() => {
                            this.setState({
                                type: '3'
                            }, () => {
                                this.refreshs();
                                this.setState({
                                    path: 'Serve',
                                    visible: true
                                })
                            })
                        }}>
                            <img style={{ display: 'inline-block', width: '50px', height: '50px' }} src={fw} alt="" />
                            <span style={{ fontSize: '20px', fontWeight: 'bold', color: 'white', paddingLeft: '1%' }}>合作、共享、服务、交流</span>
                        </div> */}
                    </div>
                    <div>
                        <ModalMobile
                            visible={show}
                            transparent
                            onClose={() => {
                                this.setState({
                                    show: false
                                })
                            }}
                            title='选择举报类别'
                            footer={[{
                                text: '取消', onPress: () => {
                                    this.setState({
                                        show: false
                                    })
                                }
                            }, {
                                text: '确定', onPress: () => {
                                    if (val || val === 0) {
                                        this.setState({
                                            show: false,
                                            key: this.state.val,
                                            type: String(this.state.val)
                                        }, () => {
                                            if (val === 0 && label === '实名举报') {
                                                this.refreshs();
                                                this.setState({
                                                    path: 'RealName',
                                                    visible: true
                                                })
                                            } else if (val === 0 && label === '匿名举报') {
                                                this.refreshs();
                                                this.setState({
                                                    path: 'Anonymity',
                                                    visible: true
                                                })
                                            } else if (val === 1 && label === '实名举报') {
                                                this.refreshs();
                                                this.setState({
                                                    path: 'RealNameW',
                                                    visible: true
                                                })
                                            } else if (val === 1 && label === '匿名举报') {
                                                this.refreshs();
                                                this.setState({
                                                    path: 'AnonymityW',
                                                    visible: true
                                                })
                                            } else if (val === 2 && label === '实名举报') {
                                                this.refreshs();
                                                this.setState({
                                                    path: 'RealNameA',
                                                    visible: true
                                                })
                                            } else if (val === 2 && label === '匿名举报') {
                                                this.refreshs();
                                                this.setState({
                                                    path: 'AnonymityA',
                                                    visible: true
                                                })
                                            }
                                        })
                                    }else{
                                        Toast.fail('请点击圆框选择举报类别！');
                                    }
                                }
                            }]}
                            wrapProps={{ onTouchStart: this.onWrapTouchStart }}
                        >
                            <List>
                                {
                                    season.map((item) => {
                                        return (
                                            <List.Item key={item.value}>
                                                <Radio checked={val === item.value} onChange={() => this.onChange(item.value)}>{item.label}</Radio>
                                            </List.Item>
                                        )
                                    })
                                }
                            </List>
                        </ModalMobile>
                    </div>
                    <div>
                        <Modal
                            width={'80%'}
                            title={<div style={{ textAlign: 'center', fontWeight: 'bold' }}><img style={{ width: '20px' }} src={xl} />  举报须知</div>}
                            visible={this.state.visible}
                            footer={null}
                            onCancel={this.handleCancel}
                            bodyStyle={{ padding: '10px', height: '50vh' }}
                            destroyOnClose
                            wrapClassName={'modals'}
                        >
                            <div style={{ overflow: 'hidden', width: '100%', height: '100%' }}>
                                <div className={s.tishi} dangerouslySetInnerHTML={{ __html: datas && datas.problemDescribe ? datas.problemDescribe : '' }}></div>
                                <div className={s.tishit}>
                                    <Button style={{ width: '35%' }} onClick={() => {
                                        this.setState({
                                            visible: false
                                        })
                                    }}>关闭</Button>
                                    <Button style={{ width: '35%', marginLeft: '20px' }} type="primary" onClick={() => {
                                        dispatch(push(`${mainModule}${path}`));
                                    }}>同意</Button>
                                </div>
                            </div>
                        </Modal>
                    </div>
                </div>
            </Spin>
        )
    }
}

export default incTab(homePage);
