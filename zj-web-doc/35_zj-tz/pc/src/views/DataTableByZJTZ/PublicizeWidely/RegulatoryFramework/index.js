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
        this.props.myFetch('getZjTzRulesHome', {}).then(
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
            <div className={s.RegulatoryFramework}>
                <div className={s.leftTop}>
                    <div className={s.leftTopOneL}>
                        规章制度
                    </div>
                </div>
                <div className={s.bottom}>
                    <Spin spinning={loading}>
                        {
                            data.length ?  data.map((item, index) => {
                                return (
                                    <div className={s.bottomT} key={index} onClick={() => {
                                        this.setState({
                                            DrawerData:item,
                                            visible:true
                                        })
                                    }}>
                                        <div className={s.bottomTT}>
                                            <div className={s.bottomTTL} style={{color:'#1890ff'}}>【{item.typeName}】</div> 
                                            <div className={s.bottomTTR} style={{color:'white'}}>{item.title}</div>
                                        </div>
                                        <div className={s.bottomTB}>
                                            <div className={s.bottomTBL}>发布日期：{item.releaseDate ? moment(item.releaseDate).format('YYYY-MM-DD') : null}</div> 
                                            <div className={s.bottomTBR}>{item.departmentName}</div>
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
                            DrawerData.typeName === '规章制度' ? <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                fetchConfig={{
                                    apiName: 'getZjTzRulesDetails',
                                    otherParams: {
                                        rulesId: DrawerData.rulesId
                                    }
                                }}
                                formConfig={[
                                    {
                                        field: 'rulesId',
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
                                        label: '文号',
                                        field: 'symbolNo',
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '发文层级',
                                        field: 'releaseRankName',
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '发文部门',
                                        field: 'departmentName',
                                        type: 'string',
                                        placeholder: '请输入',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: "是否有效文件",
                                        field: "effectiveId",
                                        type: "radio",
                                        required: true,
                                        disabled: true,
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
                                    },
                                    {
                                        label: '由此废除的相关文件号',
                                        field: 'abolishSymbolNo',
                                        type: 'string',
                                        placeholder: '请输入',
                                        disabled: true,
                                    },
                                    {
                                        label: '发文日期',
                                        field: 'releaseDate',
                                        type: 'date',
                                        placeholder: '请选择',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '备注',
                                        field: 'remarks',
                                        type: 'textarea',
                                        placeholder: '请输入',
                                        disabled: true,
                                    },
                                    {
                                        label: '登记日期',
                                        field: 'registeredTime',
                                        type: 'date',
                                        placeholder: '请选择',
                                        required: true,
                                        disabled: true,
                                    },
                                    {
                                        label: '登记用户',
                                        field: 'registeredUser',
                                        type: 'string',
                                        required: true,
                                        disabled: true,
                                        placeholder: '请输入'
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
                                        apiName: 'getZjTzImportDocDetails',
                                        otherParams: {
                                            importDocId: DrawerData.importDocId
                                        }
                                    }}
                                    formConfig={[
                                        {
                                            field: 'importDocId',
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
                                            label: '文号',
                                            field: 'symbolNo',
                                            type: 'string',
                                            placeholder: '请输入',
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            label: '发文层级',
                                            field: 'releaseRankName',
                                            type: 'string',
                                            placeholder: '请输入',
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            label: '发文部门',
                                            field: 'departmentName',
                                            type: 'string',
                                            placeholder: '请输入',
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            label: "是否有效文件",
                                            field: "effectiveId",
                                            type: "radio",
                                            required: true,
                                            disabled: true,
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
                                        },
                                        {
                                            label: '由此废除的相关文件号',
                                            field: 'abolishSymbolNo',
                                            type: 'string',
                                            placeholder: '请输入',
                                            disabled: true,
                                        },
                                        {
                                            label: '发文日期',
                                            field: 'releaseDate',
                                            type: 'date',
                                            placeholder: '请选择',
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            label: '备注',
                                            field: 'remarks',
                                            type: 'textarea',
                                            placeholder: '请输入',
                                            disabled: true,
                                        },
                                        {
                                            label: '登记日期',
                                            field: 'registeredTime',
                                            type: 'date',
                                            placeholder: '请选择',
                                            required: true,
                                            disabled: true,
                                        },
                                        {
                                            label: '登记用户',
                                            field: 'registeredUser',
                                            type: 'string',
                                            required: true,
                                            disabled: true,
                                            placeholder: '请输入'
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