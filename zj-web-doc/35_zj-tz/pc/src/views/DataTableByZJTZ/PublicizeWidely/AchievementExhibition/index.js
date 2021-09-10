import React, { Component } from "react";
import s from './style.less';
import { Carousel, Drawer, Button, Spin } from 'antd';
import QnnForm from "../../../modules/qnn-form";
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: [],
            autoplay: false,
            visible: false,
            DrawerData: null
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        this.props.myFetch('getHomeAdvertZjTzBrandResultShow', {}).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        data: data.map((item) => {
                            let reg = /<img [^>]*src=['"]([^'"]+)[^>]*>/;
                            let str = reg.exec(item.content)?.[1];
                            item.url = str;
                            return item;
                        }),
                        autoplay:data.length > 6 ? true : false
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
        const { data, autoplay, visible, DrawerData, loading } = this.state;
        return (
            <div className={s.AchievementExhibition}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        成果展示
                    </div>
                </div>
                <div className={s.right}>
                    <Spin spinning={loading}>
                        {data.length ? <Carousel
                            autoplay={autoplay}
                            autoplaySpeed={30}
                            speed={5000}
                            slidesToShow={6}
                            dotPosition='bottom'
                            style={{ height: '100%', width: '100%' }}
                        >
                            {data.map((item, index) => (
                                <div key={index} className={s.carousel} onClick={() => {
                                    this.setState({
                                        visible: true,
                                        DrawerData: item
                                    })
                                }}>
                                    <div className={s.carouselCenter}>
                                        <img style={{ width: '100%', height: '100%' }} src={item.url ? item.url : require('../../../../imgs/cgzs.png')} alt='' />
                                        <div className={s.carouselCenterB}>{item.title}</div>
                                    </div>
                                </div>
                            ))}
                        </Carousel> : <div style={{ width: '100%', textAlign: 'center', marginTop: '3%', color: 'white', fontWeight: 'bold' }}>暂无数据展示...</div>}
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
                        {DrawerData?.flag === '1' ? <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig={{
                                apiName: 'getZjTzBrandResultShowDetails',
                                otherParams: {
                                    resultShowId: DrawerData.resultShowId
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'resultShowId',
                                    type: 'string',
                                    hide: true,
                                },
                                {
                                    label: '填报单位',
                                    field: 'companyName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '成果名称',
                                    field: 'title',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '获得主体',
                                    field: 'getSubjectId',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId'
                                    },
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'huoDeZhuTi'
                                        }
                                    },
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '获得时间',
                                    field: 'getTime',
                                    type: 'date',
                                    required: true,
                                    disabled: true,
                                },
                                // {
                                //     label:'备注',
                                //     field:'bz',
                                //     type: 'textarea',
                                //     disabled: true,
                                // },
                                {
                                    label: '成果权属单位',
                                    field: 'resultUnit',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '成果类型',
                                    field: 'resultTypeName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '成果级别',
                                    field: 'getSubjectId',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId'
                                    },
                                    fetchConfig: {
                                        apiName: "getBaseCodeSelect",
                                        otherParams: {
                                            itemId: 'chengGuoJiBie'
                                        }
                                    },
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    type: 'richtext',
                                    label: '内容简介',
                                    field: 'content',
                                    disabled: true,
                                    fetchConfig: {
                                        uploadUrl: window.configs.domain + 'upload'
                                    }
                                },
                                {
                                    label: '登记日期',
                                    field: 'createTime',
                                    type: 'date',
                                    placeholder: '请选择',
                                    disabled: true,
                                },
                                {
                                    label: '登记人',
                                    field: 'createUserName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
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
                        /> : DrawerData?.flag === '2' ? <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig={{
                                apiName: 'getZjTzSpecialResultShowDetails',
                                otherParams: {
                                    resultShowId: DrawerData.resultShowId
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'resultShowId',
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
                                    label: '成果权属',
                                    field: 'companyName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '成果类型',
                                    field: 'resultTypeName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    type: 'richtext',
                                    label: '内容简介',
                                    field: 'content',
                                    disabled: true,
                                    fetchConfig: {
                                        uploadUrl: window.configs.domain + 'upload'
                                    }
                                },
                                {
                                    label: '登记日期',
                                    field: 'createTime',
                                    type: 'date',
                                    placeholder: '请选择',
                                    disabled: true,
                                },
                                {
                                    label: '登记人',
                                    field: 'createUserName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
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
                        /> : <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig={{
                                apiName: 'getZjTzProEventDetails',
                                otherParams: {
                                    proEventId: DrawerData.resultShowId
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'proEventId',
                                    type: 'string',
                                    hide: true,
                                },
                                {
                                    label: '项目名称',
                                    field: 'projectName',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
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
                                    label: '发生时间',
                                    field: 'happenTime',
                                    type: 'date',
                                    placeholder: '请选择',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    type: 'richtext',
                                    label: '主要内容',
                                    field: 'content',
                                    required: true,
                                    disabled: true,
                                    fetchConfig: {
                                        uploadUrl: window.configs.domain + 'upload'
                                    }
                                },
                                {
                                    label: '登记日期',
                                    field: 'registerDate',
                                    type: 'date',
                                    placeholder: '请选择',
                                    required: true,
                                    disabled: true,
                                },
                                {
                                    label: '登记用户',
                                    field: 'registerPerson',
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true,
                                    disabled: true,
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
                        />}
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;