import React, { Component } from "react";
import s from './style.less';
import { Drawer, Button, Spin } from 'antd';
import moment from 'moment';
import QnnForm from "../../../modules/qnn-form";
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            data: [],
            visible: false,
            DrawerData: null
        }
    }
    componentDidMount() {
        this.setState({
            loading:true
        })
        this.props.myFetch('getZjTzBrandYearPointHome', {}).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        data: data
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
        const { data, loading, visible, DrawerData } = this.state;
        return (
            <div className={s.Building}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        品牌建设/专项活动
                    </div>
                </div>
                <div className={s.bottom}>
                    <Spin spinning={loading}>
                        {
                            data.length ? data.map((item, index) => {
                                return (
                                    <div className={s.bottomT} key={index} onClick={() => {
                                        this.setState({
                                            DrawerData:item,
                                            visible:true
                                        })
                                    }}>
                                        <div className={s.bottomTT} style={{color:'white'}}>
                                            <span style={{color:'#1890ff'}}>【{item.typeName}】</span>{item.content}
                                        </div>
                                        <div className={s.bottomTB}>
                                            <div className={s.bottomTBL}>发布日期：{item.registerDate ? moment(item.registerDate).format('YYYY-MM-DD') : null}</div> 
                                            <div className={s.bottomTBR}>{item.companyName}</div>
                                        </div>
                                    </div>
                                )
                            }) : <div style={{ width: '100%', height:'100%', textAlign: 'center', marginTop: '10%', color: 'white', fontWeight: 'bold' }}>暂无数据展示...</div>
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
                                visible: false,
                                // autoplay: true
                            })
                        }}
                        className='DrawerQnnForm'
                        visible={visible}
                        bodyStyle={{height:window.innerHeight - 155}}
                        footer={<div style={{textAlign:'right',paddingRight:'20px'}}><Button type="dashed" onClick={() => {
                            this.setState({
                                visible: false,
                                // autoplay: true
                            })
                        }}>取消</Button></div>}
                    >
                        {
                            DrawerData.typeName === '品牌建设' ? <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                fetchConfig={{
                                    apiName: 'getZjTzBrandYearPointDetails',
                                    otherParams: {
                                        yearPointId: DrawerData.yearPointId
                                    }
                                }}
                                formConfig={[
                                    {
                                        field: 'yearPointId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '年度',
                                        field: 'yearDate',
                                        type: 'year',
                                        placeholder: '请选择',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '登记日期',
                                        field: 'registerDate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        disabled: true,
                                    },
                                    {
                                        label: '登记人',
                                        field: 'registerPerson',
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '内容简介',
                                        field: 'content',
                                        type: 'textarea',
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
                            /> : DrawerData.typeName === '专项活动' ? <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                fetchConfig={{
                                    apiName: 'getZjTzSpecialYearPointDetails',
                                    otherParams: {
                                        yearPointId: DrawerData.yearPointId
                                    }
                                }}
                                formConfig={[
                                    {
                                        field: 'yearPointId',
                                        type: 'string',
                                        hide: true,
                                    },
                                    {
                                        label: '年度',
                                        field: 'yearDate',
                                        type: 'year',
                                        placeholder: '请选择',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '所属机构',
                                        field: 'companyName',
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '登记日期',
                                        field: 'registerDate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        disabled: true,
                                    },
                                    {
                                        label: '登记人',
                                        field: 'registerPerson',
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '内容简介',
                                        field: 'content',
                                        type: 'textarea',
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
                                            apiName: 'getZjTzBrandImplementPointDetails',
                                            otherParams: {
                                                implementPointId: DrawerData.implementPointId
                                            }
                                        }}
                                        formConfig={[
                                            {
                                                field: 'implementPointId',
                                                type: 'string',
                                                hide: true,
                                            },
                                            {
                                                label: '年度',
                                                field: 'yearDate',
                                                type: 'year',
                                                placeholder: '请选择',
                                                required: true,
                                                disabled: true,
                                            },
                                            {
                                                label: '所属机构',
                                                field: 'companyName',
                                                type: 'string',
                                                placeholder: '请输入',
                                                required: true,
                                                disabled: true,
                                            },
                                            {
                                                label: '登记日期',
                                                field: 'registerDate',
                                                type: 'date',
                                                placeholder: '请选择',
                                                disabled: true,
                                            },
                                            {
                                                label: '登记人',
                                                field: 'registerPerson',
                                                type: 'string',
                                                placeholder: '请输入',
                                                required: true,
                                                disabled: true,
                                            },
                                            {
                                                label: '内容简介',
                                                field: 'content',
                                                type: 'textarea',
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
                                    />
                        }
                    </Drawer> : null
                }
            </div>
        );
    }
}

export default index;