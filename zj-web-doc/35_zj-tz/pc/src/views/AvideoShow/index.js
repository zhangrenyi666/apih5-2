import React, { Component } from "react";
import s from "./style.less";
import { message as Msg, Row, Col, Spin } from "antd";
import QnnForm from "../modules/qnn-table/qnn-form";
import { ImgPreview } from "qnn-form";
import Button from "antd/es/button";
import $ from 'jquery';
const play = require('./play.png');
let indexVall = 10;
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dataArry: null,
            visible: false,
            loadingBtn: false,
            loadingDirectional: false,
            videoList: null,
            totalNumber: 0,
            ext1: props.loginAndLogoutInfo.loginInfo.userInfo.ext1,
            spanWid: props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '1' ? 8 : (props.loginAndLogoutInfo.loginInfo.userInfo.ext1 === '2' ? 6 : 8)
        }
    }
    componentDidMount() {
        const { projectId, companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const { ext1 } = this.state;
        let paramsArryPage = {};
        if (ext1 === '1' || ext1 === '2') {
            paramsArryPage = {
                projectId:projectId,
                yearDate: null,
                companyId: companyId,
                //issue: '2',
                keyFlag: ''
            };
        } else {
            paramsArryPage = {
                projectId: projectId,
                yearDate: null,
                companyId: companyId,
                //issue: '2',
                keyFlag: ''
            };

        }
        this.getNewDate(10, 1, paramsArryPage);
    }
    getNewDate(limit, page, paramsArry){
        console.log(paramsArry)
        const { myFetch } = this.props;
        myFetch('getZjTzAerialVideoList', {
            limit: limit,
            page: page,
            projectId: paramsArry ? paramsArry.projectId : '',
            yearDate: paramsArry && paramsArry.yearDate ? new Date(paramsArry.yearDate).getTime() : null,
            companyId: paramsArry ? paramsArry.companyId : '',
            //issue: paramsArry ? paramsArry.issue : '',
            keyFlag: paramsArry ? paramsArry.keyFlag : ''
        }).then(
            ({ data, success, message, totalNumber }) => {
                if (success) {
                    this.setState({
                        dataArry: data,
                        totalNumber: totalNumber
                    }, () => {
                        this.setState({
                            loadingDirectional: false
                        })
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { dataArry, visible, loadingBtn, loadingDirectional, videoList, totalNumber, spanWid, ext1 } = this.state;
        const { projectId, companyId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        let _this = this;
        return (
            <div className={s.root}>
                <Row>
                    <Col span={24}>
                        <QnnForm
                            style={{ background: '#f0f2f5' }}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.formHasTicket = me;
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 24 },
                                    sm: { span: 6 }
                                },
                                wrapperCol: {
                                    xs: { span: 24 },
                                    sm: { span: 18 }
                                }
                            }}
                            formConfig={[
                                {
                                    type: 'select',
                                    label: '管理单位',
                                    field: 'companyId',
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyId'
                                    },
                                    fetchConfig: {
                                        apiName: "getHomeProgressPlaningComname"
                                    },
                                    initialValue:companyId  ? companyId : null,
                                    span: spanWid,
                                    hide: () => {
                                        if (ext1 === '1') {
                                            return false
                                        } else {
                                            return true
                                        }
                                    }
                                },
                                {
                                    label: '项目名称',
                                    field: 'projectId',
                                    type: 'select',
                                    showSearch: true,
                                    optionConfig: {
                                        label: 'projectShortName',
                                        value: 'projectId'
                                    },
                                    fetchConfig: {
                                        // apiName: "getZjTzProManageList",
                                        apiName: "getZjTzPermissionListByProject",
                                        otherParams: {
                                            // projectId:projectId,
                                            allFlag: '0'
                                        }
                                    },
                                    initialValue:projectId === 'all' ? null : projectId,
                                    placeholder: '请选择',
                                    span: spanWid,
                                    hide: () => {
                                        if (ext1 === '1' || ext1 === '2') {
                                            return false
                                        } else {
                                            return true
                                        }
                                    }
                                },
                                {
                                    type: 'month',
                                    label: '年月',
                                    field: 'yearDate',
                                    span: spanWid,
                                },
                              
                                {
                                    type: 'select',
                                    label: '筛选',
                                    field: 'keyFlag',
                                    optionConfig: {
                                        label: 'label',
                                        value: 'value'
                                    },
                                    optionData: [
                                        {
                                            label: "非重点项目",
                                            value: "0"
                                        },
                                        {
                                            label: "重点项目",
                                            value: "1"
                                        }
                                    ],
                                    span: spanWid,
                                    hide: () => {
                                        if (ext1 === '1') {
                                            return false
                                        } else {
                                            return true
                                        }
                                    }
                                },
                                {
                                    type: 'component',
                                    field: 'diySK1',
                                    Component: obj => {
                                        return (
                                            <div style={{ padding: '11px 11px 11px 71px', textAlign: 'center' }}>
                                                <Button type="primary" onClick={() => {
                                                    let paramsArry = this.formHasTicket.form.getFieldsValue();
                                                    if (ext1 === '1') {
                                                        paramsArry = {
                                                            projectId: paramsArry.projectId ? paramsArry.projectId : projectId,
                                                            companyId: paramsArry.companyId ? paramsArry.companyId : null,
                                                            yearDate: paramsArry && paramsArry.yearDate ? new Date(paramsArry.yearDate).getTime() : null,
                                                            //issue: paramsArry ? paramsArry.issue : '',
                                                            keyFlag: paramsArry.keyFlag ? paramsArry.keyFlag : null,
                                                        };
                                                    } else if (ext1 === '2') {
                                                        paramsArry = {
                                                            projectId: paramsArry.projectId ? paramsArry.projectId : projectId,
                                                            companyId: companyId,
                                                            yearDate: paramsArry && paramsArry.yearDate ? new Date(paramsArry.yearDate).getTime() : null,
                                                            //issue: paramsArry ? paramsArry.issue : '',
                                                            keyFlag: null,
                                                        };
                                                    } else if (ext1 === '3' || ext1 === '4') {
                                                        paramsArry = {
                                                            projectId: projectId,
                                                            companyId: companyId,
                                                            yearDate: paramsArry && paramsArry.yearDate ? new Date(paramsArry.yearDate).getTime() : null,
                                                            //issue: paramsArry ? paramsArry.issue : '',
                                                            keyFlag: null,
                                                        };
                                                    }
                                                    _this.getNewDate(indexVall, 1, paramsArry);
                                                }}>查询</Button>
                                            </div>
                                        );
                                    },
                                    span: spanWid,
                                },
                            ]}
                        />
                    </Col>
                    <Col span={24}>
                        <Spin spinning={loadingDirectional}>
                            <div className={s.imgList} id="myBtn" ref={(me) => {
                                if (me) {
                                    // function moveFn(e) {
                                    //     e.stopPropagation();
                                    //     let scrollTop = document.getElementById('myBtn').scrollTop;
                                    //     if (scrollTop > 60) {
                                    //         $(".loading").css({
                                    //             display:'block !important'
                                    //         });
                                    //         // _this.setState({
                                    //         //     loadingBtn:true
                                    //         // })
                                    //     }
                                    // }
                                    // me.addEventListener('scroll', moveFn, false);
                                }
                            }}>
                                {
                                    dataArry && dataArry.map((item, index) => {
                                        return <div className={s.imgStyle}>
                                            <div className={s.immg}>
                                                <img className={s.img1} src={item.imageUrl} />
                                                <img className={s.img2} src={play} onClick={() => {
                                                    if (item.videoList[0].url) {
                                                        this.setState({
                                                            visible: true,
                                                            videoList: item.videoList.map(val => {
                                                                val.name = item.videoName;
                                                                return val
                                                            })
                                                        })
                                                    } else {
                                                        Msg.warn('视频不存在！')
                                                    }

                                                }} />
                                            </div>
                                            <div className={s.spanStyle}>{item.videoName}</div>
                                        </div>
                                    })
                                }
                            </div>
                        </Spin>
                    </Col>
                    {totalNumber && totalNumber > 10 ? <Col span={24} className={s.loading}>
                        <div className={s.loadingBtn} onClick={() => {
                            indexVall = indexVall + 10;
                            if (dataArry.length != totalNumber) {
                                this.setState({
                                    loadingDirectional: true
                                })
                                let paramsArry = this.formHasTicket.form.getFieldsValue();
                                if (ext1 === '1') {
                                    paramsArry = {
                                        projectId: paramsArry.projectId ? paramsArry.projectId : null,
                                        companyId: paramsArry.companyId ? paramsArry.companyId : null,
                                        yearDate: paramsArry && paramsArry.yearDate ? new Date(paramsArry.yearDate).getTime() : null,
                                        //issue: paramsArry ? paramsArry.issue : '',
                                        keyFlag: paramsArry.keyFlag ? paramsArry.keyFlag : null,
                                    };
                                } else if (ext1 === '2') {
                                    paramsArry = {
                                        projectId: paramsArry.projectId ? paramsArry.projectId : null,
                                        companyId: companyId,
                                        yearDate: paramsArry && paramsArry.yearDate ? new Date(paramsArry.yearDate).getTime() : null,
                                        //issue: paramsArry ? paramsArry.issue : '',
                                        keyFlag: null,
                                    };
                                } else if (ext1 === '3' || ext1 === '4') {
                                    paramsArry = {
                                        projectId: projectId,
                                        companyId: companyId,
                                        yearDate: paramsArry && paramsArry.yearDate ? new Date(paramsArry.yearDate).getTime() : null,
                                        //issue: paramsArry ? paramsArry.issue : '',
                                        keyFlag: null,
                                    };
                                }
                                _this.getNewDate(indexVall, 1, paramsArry);

                            } else {
                                Msg.warn('已加载全部视频');
                            }

                        }}>加载更多...</div>
                    </Col> : null}
                </Row>
                {
                    visible ? <ImgPreview
                        curIndex={0}
                        onClose={() => {
                            this.setState({
                                visible: false
                            })
                        }}
                        fileList={videoList}
                        allVideo
                    /> : null
                }
            </div>
        );
    }
}

export default index;