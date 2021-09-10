import React, { Component } from "react";
import { Tabs, message as Msg, Divider, Tag, Modal, Rate, Button } from 'antd';
import s from './style.less';
import MyList from "../modules/MMList";
import moment from 'moment';
import QnnForm from '../modules/qnn-table/qnn-form';
import { SearchOutlined, ReloadOutlined, HeartOutlined } from '@ant-design/icons';
const { TabPane } = Tabs;
class index extends Component {
    constructor() {
        super();
        this.state = {
            key: '',
            TabsData: [
                {
                    itemId: '',
                    itemName: '全部'
                }
            ],
            modelData: {},
            visible: false,
            mListFlag:true,
            serchData:{}
        }
    }
    componentDidMount() {
        this.props.myFetch('getBaseCodeSelect', { itemId: 'wenTiLeiXing' }).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    TabsData: this.state.TabsData.concat(data)
                });
            } else {
                Msg.error(message);
            }
        });
    }
    callback = (key) => {
        this.setState({ key });
    }
    onRef = (ref) => {
        this.child = ref
    }
    render() {
        const { key, TabsData, modelData, visible, mListFlag, serchData } = this.state;
        return (
            <div className={s.root}>
                {TabsData.length && TabsData.length > 1 ? <Tabs activeKey={key} onChange={this.callback}>
                    {
                        TabsData.map((item) => {
                            return (
                                <TabPane tab={item.itemName} key={item.itemId}>
                                    <div>
                                        <QnnForm
                                            fetch={this.props.myFetch}
                                            upload={this.props.myUpload}
                                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                            wrappedComponentRef={(me) => this.qnnForm = me}
                                            formConfig={[
                                                {
                                                    label: '发布人',
                                                    type: "string",
                                                    field: 'userName',
                                                    placeholder: "请输入",
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 7 },
                                                            sm: { span: 7 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 17 },
                                                            sm: { span: 17 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '发布单位',
                                                    type: "string",
                                                    field: 'deptName',
                                                    placeholder: "请输入",
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 7 },
                                                            sm: { span: 7 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 17 },
                                                            sm: { span: 17 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '科技类型',
                                                    field: 'industryScientificInfoId',
                                                    type: 'select',
                                                    optionConfig: {
                                                        label: 'itemName',
                                                        value: 'itemId',
                                                    },
                                                    fetchConfig: {
                                                        apiName: 'getBaseCodeSelect',
                                                        otherParams: {
                                                            itemId: 'wenTiLeiXing'
                                                        }
                                                    },
                                                    placeholder: '请选择',
                                                    hide:item.itemId ? true : false,
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 7 },
                                                            sm: { span: 7 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 17 },
                                                            sm: { span: 17 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '科技标题',
                                                    type: "string",
                                                    field: 'title',
                                                    placeholder: "请输入",
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 7 },
                                                            sm: { span: 7 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 17 },
                                                            sm: { span: 17 }
                                                        }
                                                    }
                                                },
                                                {
                                                    label: '发布时间',
                                                    type: "rangeDate",
                                                    field: 'planTime',
                                                    placeholder: "请输入",
                                                    span: 8,
                                                    formItemLayout: {
                                                        labelCol: {
                                                            xs: { span: 7 },
                                                            sm: { span: 7 }
                                                        },
                                                        wrapperCol: {
                                                            xs: { span: 17 },
                                                            sm: { span: 17 }
                                                        }
                                                    }
                                                },
                                                {
                                                    type: 'component',
                                                    field: 'diy',
                                                    span: 8,
                                                    Component: obj => {
                                                        return (
                                                            <div
                                                                style={{ width: "100%", height: '56px', lineHeight: '56px', textAlign:'right' }}
                                                            >
                                                                <Button style={{ marginRight: '10px' }} type="primary" icon={<SearchOutlined />} onClick={() => {
                                                                    this.qnnForm.getValues(false, (value) => {
                                                                        this.setState({
                                                                            mListFlag: false
                                                                        }, () => {
                                                                            this.setState({
                                                                                serchData:value,
                                                                                mListFlag: true
                                                                            })
                                                                        })
                                                                    })
                                                                }}>搜索</Button>
                                                                <Button style={{ marginRight: '10px' }} icon={<ReloadOutlined />} onClick={() => {
                                                                    this.qnnForm.form.resetFields();
                                                                    this.setState({
                                                                        mListFlag: false
                                                                    }, () => {
                                                                        this.setState({
                                                                            serchData:{},
                                                                            mListFlag: true
                                                                        })
                                                                    })
                                                                }}>重置</Button>
                                                            </div>
                                                        );
                                                    }
                                                }
                                            ]}
                                        />
                                    </div>
                                    <div style={{ height: window.innerHeight - 338, width: '100%' }}>
                                        {key === item.itemId && mListFlag ? <MyList
                                            myFetch={this.props.myFetch}
                                            upload={this.props.myUpload} //ajax方法必须返回一个promise
                                            onRef={this.onRef}
                                            fetchConfig={{//表格的ajax配置
                                                apiName: 'getZjSjConsultScientificInformationList',
                                                otherParams: {
                                                    technologyIndustryFlag: '1',
                                                    industryScientificInfoId: item.itemId,
                                                    ...serchData
                                                }
                                            }}
                                            Item={props => {
                                                //列表模板 props里有所有数据
                                                const { rowData, rowID } = props;
                                                const item = rowData;
                                                const index = rowID;
                                                return (
                                                    <div
                                                        className={s.center}
                                                        key={index}
                                                    >
                                                        <div>
                                                            <span>
                                                                {moment(item.releaseTime).format('MM月DD日 HH:mm')}&nbsp;&nbsp;&nbsp;
                                                            </span>
                                                            <span>
                                                                <Tag color={'green'}>
                                                                    {item.industryScientificInfoName}
                                                                </Tag>
                                                            </span>
                                                            <span>
                                                                &nbsp;&nbsp;&nbsp;{item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.split(',').join('→') : item.deptName}：{item.userName}&nbsp;&nbsp;&nbsp;
                                                            </span>
                                                            <span>
                                                                阅读：{item.readNum}&nbsp;&nbsp;&nbsp;
                                                            </span>
                                                            <span><Rate count={1}  style={{ fontSize: 25 }} character={<HeartOutlined />}  value={Number(item.favoriteId)} onChange={(value) => {
                                                                if (value === 1) {
                                                                    this.props.myFetch('addZjSjConsultFavorite', { ...item }).then(({ success, message, data }) => {
                                                                        if (success) {
                                                                            let rowData = modelData;
                                                                            rowData.favoriteId = '1';
                                                                            this.setState({
                                                                                modelData: rowData
                                                                            }, () => {
                                                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                            })
                                                                        } else {
                                                                            Msg.error(message);
                                                                        }
                                                                    });
                                                                } else if (value === 0) {
                                                                    this.props.myFetch('cancelZjSjConsultFavorite', { ...item }).then(({ success, message, data }) => {
                                                                        if (success) {
                                                                            let rowData = modelData;
                                                                            rowData.favoriteId = '0';
                                                                            this.setState({
                                                                                modelData: rowData
                                                                            }, () => {
                                                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                            })
                                                                        } else {
                                                                            Msg.error(message);
                                                                        }
                                                                    });
                                                                }
                                                            }} /></span>
                                                        </div>
                                                        <div style={{ fontSize: '20px', fontWeight: 'bold', overflow: 'hidden', whiteSpace: 'normal', textOverflow: 'ellipsis', cursor: 'pointer', color:'#0369a7' }}
                                                            onClick={() => {
                                                                this.props.myFetch('getZjSjConsultScientificInformationDetails', { infoId: item.infoId }).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        this.setState({
                                                                            modelData: data,
                                                                            visible: true
                                                                        }, () => {
                                                                            this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                        })
                                                                    } else {
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }}>
                                                            {item.title}
                                                        </div>
                                                        <Divider style={{ margin: "8px 0px", height: '3px' }} />
                                                    </div>
                                                );
                                            }}
                                        /> : null}
                                    </div>
                                </TabPane>
                            )
                        })
                    }
                </Tabs> : null}
                {visible ? <div>
                    <Modal
                        width={'60%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title={
                            <div>
                                <div style={{ fontSize: '18px', fontWeight: 'bold' }}>
                                    <span>{modelData.title}&nbsp;&nbsp;&nbsp;</span>
                                </div>
                                <div>
                                    <span>{modelData.deptName && modelData.deptName.indexOf(',') !== -1 ? modelData.deptName.split(',').join('→') : modelData.deptName}：{modelData.userName}&nbsp;&nbsp;&nbsp;</span><span>阅读：{modelData.readNum}&nbsp;&nbsp;&nbsp;</span><span><Rate count={1}  style={{ fontSize: 25 }} character={<HeartOutlined />}  onChange={(value) => {
                                        if (value === 1) {
                                            this.props.myFetch('addZjSjConsultFavorite', { ...modelData }).then(({ success, message, data }) => {
                                                if (success) {
                                                    let rowData = modelData;
                                                    rowData.favoriteId = '1';
                                                    this.setState({
                                                        modelData: rowData
                                                    }, () => {
                                                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                    })
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        } else if (value === 0) {
                                            this.props.myFetch('cancelZjSjConsultFavorite', { ...modelData }).then(({ success, message, data }) => {
                                                if (success) {
                                                    let rowData = modelData;
                                                    rowData.favoriteId = '0';
                                                    this.setState({
                                                        modelData: rowData
                                                    }, () => {
                                                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                    })
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    }} value={Number(modelData.favoriteId)} /></span>
                                </div>
                                <div>
                                    <span>发布时间：{moment(modelData.releaseTime).format('YYYY-MM-DD HH:mm:ss')}&nbsp;&nbsp;&nbsp;</span>
                                    <span>
                                        <Tag color={'green'}>
                                            {modelData.industryScientificInfoName}
                                        </Tag>
                                    </span>
                                </div>
                            </div>
                        }
                        visible={visible}
                        footer={null}
                        bodyStyle={{ width:'100%', maxHeight:'60vh', padding: '10px', overflow: 'auto' }}
                        centered={true}
                        onCancel={() => {
                            this.setState({ visible: false });
                        }}
                    >
                        <div>
                            <div dangerouslySetInnerHTML={{ __html: modelData.content }}></div>
                            <div>
                                <div>附件：</div>
                                {
                                    modelData.attachmentList && modelData.attachmentList.length ? modelData.attachmentList.map((item, index) => {
                                        return (
                                            <div key={index}>
                                                <a target='_blank' href={item.url}>{item.name}</a>
                                                <Divider style={{ margin: "8px 0px", height: '1px' }} />
                                            </div>
                                        )
                                    }) : null
                                }
                            </div>
                        </div>
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;