import React, { Component } from "react";
import QnnForm from '../modules/qnn-table/qnn-form';
import { PlusOutlined, SearchOutlined, ReloadOutlined } from '@ant-design/icons';
import s from './style.less';
import MyList from "../modules/MMList";
import { message as Msg, Modal, Button, Comment, Avatar, Tag, Divider, Collapse } from 'antd';
import moment from 'moment';
const { confirm } = Modal;
const { Panel } = Collapse;
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            realName: props.loginAndLogoutInfo.loginInfo.userInfo.realName || '',
            userKey: props.loginAndLogoutInfo.loginInfo.userInfo.userKey || '',
            visible: false,
            visibles: false,
            visibleReply: false,
            visibleEdit: false,
            mListFlag: true,
            buttonLoading: false,
            serchData: {
                selectType: '0'
            },
            rowData: null,
            replyData: null,
            editData: null,
            CollapseKey: [],
            CollapseData: null,
            userFlag:false
        }
    }
    componentDidMount(){
        const { userKey } = this.state;
        this.props.myFetch('getSysRoleUserList', { roleId:'1EBD3858H000BE330F0A0000199917BB' }).then(({ success, message, data }) => {
            if (success) {
                let userFlag = false;
                for(let i = 0; i < data.length; i++){
                    if(data[i].value === userKey){
                        userFlag = true;
                        break;
                    }
                }
                this.setState({
                    userFlag:userFlag
                })
            } else {
                Msg.error(message);
            }
        });
    }
    onRef = (ref) => {
        this.child = ref
    }
    handelTopFlag = (item) => {
        if (this.state.realName === 'admin') {
            let rowData = item;
            if (rowData.topFlag === '0') {
                rowData.topFlag = '1';
            } else {
                rowData.topFlag = '0';
            }
            this.props.myFetch('updateZjSjConsultOnlineConsult', { ...rowData }).then(({ success, message, data }) => {
                if (success) {
                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                } else {
                    Msg.error(message);
                }
            });
        }
    }
    handelDelete = (item) => {
        confirm({
            content: '????????????????????????????',
            centered: true,
            onOk: () => {
                this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsult', [item]).then(({ success, message, data }) => {
                    if (success) {
                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                    } else {
                        Msg.error(message);
                    }
                });
            }
        });
    }
    handelReadFlag = (item) => {
        let rowData = item;
        if (rowData.readFlag === '0') {
            rowData.readFlag = '1';
            this.props.myFetch('addZjSjConsultOnlineConsultReadFlag', { ...rowData }).then(({ success, message, data }) => {
                if (success) {
                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                } else {
                    Msg.error(message);
                }
            });
        }
    }
    handelQuestionTypeName = (item) => {
        if (this.state.realName === 'admin') {
            this.setState({
                rowData: item,
                visibles: true
            })
        }
    }
    // getData = (childrenList) => {
    //     var arr = []
    //     for (var i = 0; i < childrenList.length; i++) {
    //         arr.push(childrenList[i]);
    //         if (childrenList[i].childrenList) arr = arr.concat(this.getData(childrenList[i].childrenList));
    //     }
    //     // console.log(arr);
    //     return arr;
    // }
    generateMenu(data) {
        const { userKey } = this.state;
        let vdom = [];
        if (data && data.length) {
            // data = this.getData(data);
            data.map((item) => {
                const ExampleComment = ({ children }) => (
                    <Comment
                        author={
                            <div style={{ width: '100%', fontSize: '14px' }}>
                                {item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.substring(item.deptName.indexOf(",") + 1, item.deptName.length).split(',').join('???') : item.deptName}???{item.userName}&nbsp;&nbsp;??????&nbsp;&nbsp;
                                {item.consultDept && item.consultDept.indexOf(',') !== -1 ? item.consultDept.substring(item.consultDept.indexOf(",") + 1, item.consultDept.length).split(',').join('???') : item.consultDept}???{item.consultUser}&nbsp;&nbsp;
                                {moment(item.replyTime).format('YYYY-MM-DD HH:mm:ss')}&nbsp;&nbsp;
                                <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={() => {
                                    this.setState({
                                        replyData: item,
                                        visibleReply: true
                                    })
                                }} src={require('../../imgs/huifu.svg')} alt="" />
                                &nbsp;&nbsp;
                                {userKey === item.userKey ? <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={() => {
                                    this.setState({
                                        visibleEdit: true,
                                        editData: item
                                    })
                                }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                &nbsp;&nbsp;
                                {userKey === item.userKey ? <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={() => {
                                    confirm({
                                        content: '??????????????????????',
                                        centered: true,
                                        onOk: () => {
                                            this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [item]).then(({ success, message, data }) => {
                                                if (success) {
                                                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    });
                                }} src={require('../../imgs/shanchu.svg')} alt="" /> : null}
                            </div>
                        }
                        avatar={
                            <Avatar
                                src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                alt=""
                            />
                        }
                        content={
                            <div>
                                {item.replyContent}
                            </div>
                        }
                    >
                        {children}
                    </Comment>
                );
                vdom.push(<ExampleComment key={item.consultRecordId}></ExampleComment>);
                return item;
            })
        }
        return vdom;
    }
    render() {
        const { visible, visibles, rowData, mListFlag, replyData, visibleReply, editData, visibleEdit, realName, serchData, userKey } = this.state;
        return (
            <div className={s.root}>
                <div>
                    <QnnForm
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => this.qnnForm = me}
                        formConfig={[
                            {
                                label: '????????????',
                                type: "select",
                                field: 'selectType',
                                initialValue: '0',
                                placeholder: "?????????",
                                optionData: [
                                    //??????????????????
                                    {
                                        label: "??????",
                                        value: "0"
                                    },
                                    {
                                        label: "???????????????",
                                        value: "1"
                                    },
                                    {
                                        label: "???????????????",
                                        value: "2"
                                    }
                                ],
                                span: 6,
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
                                label: '????????????',
                                type: "cascader",
                                field: 'questionTypeId',
                                placeholder: "?????????",
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                    children: 'children'
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeTree',
                                    otherParams: {
                                        itemId: 'wenTiLeiXing'
                                    }
                                },
                                span: 6,
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
                                label: '????????????',
                                type: "string",
                                field: 'questionTitle',
                                placeholder: "?????????",
                                span: 6,
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
                                label: '?????????',
                                type: "string",
                                field: 'keyWord',
                                placeholder: "?????????",
                                span: 6,
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
                                label: '?????????',
                                type: "string",
                                field: 'userName',
                                placeholder: "?????????",
                                span: 6,
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
                                label: '????????????',
                                type: "string",
                                field: 'deptName',
                                placeholder: "?????????",
                                span: 6,
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
                                span: 12,
                                Component: obj => {
                                    return (
                                        <div
                                            style={{ width: "100%", height: '56px', lineHeight: '56px', textAlign: 'right' }}
                                        >
                                            <Button style={{ marginRight: '10px' }} type="primary" icon={<SearchOutlined />} onClick={() => {
                                                this.qnnForm.getValues(false, (value) => {
                                                    this.setState({
                                                        mListFlag: false
                                                    }, () => {
                                                        this.setState({
                                                            serchData: value,
                                                            mListFlag: true
                                                        })
                                                    })
                                                })
                                            }}>??????</Button>
                                            <Button style={{ marginRight: '10px' }} icon={<ReloadOutlined />} onClick={() => {
                                                this.qnnForm.form.resetFields();
                                                this.setState({
                                                    mListFlag: false
                                                }, () => {
                                                    this.setState({
                                                        serchData: {
                                                            selectType: '0'
                                                        },
                                                        mListFlag: true
                                                    })
                                                })
                                            }}>??????</Button>
                                            <Button type="primary" icon={<PlusOutlined />} onClick={() => {
                                                this.setState({ visible: true, rowData: null });
                                            }}>??????</Button>
                                        </div>
                                    );
                                }
                            }
                        ]}
                    />
                </div>
                <div style={{ height: window.innerHeight - 280, width: '100%' }}>
                    {mListFlag ? <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload} //ajax????????????????????????promise
                        onRef={this.onRef}
                        fetchConfig={{//?????????ajax??????
                            apiName: 'getZjSjConsultOnlineConsultList',
                            otherParams: {
                                ...serchData
                            }
                        }}
                        Item={props => {
                            //???????????? props??????????????????
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            const ExampleComment = ({ children }) => (
                                <Comment
                                    author={
                                        <div style={{ width: '100%' }}>
                                            <div style={{ width: '100%', overflow: 'hidden' }}>
                                                <div style={{ fontSize: '18px', fontWeight: 'bold', width: '80%', float: 'left' }}>
                                                    <span style={{ color: 'black' }}>{item.questionTitle}&nbsp;&nbsp;&nbsp;</span>
                                                    <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={() => {
                                                        this.setState({
                                                            replyData: item,
                                                            visibleReply: true
                                                        })
                                                    }} src={require('../../imgs/huifu.svg')} alt="" />
                                                    &nbsp;&nbsp;
                                                    {userKey === item.userKey ? <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={() => {
                                                        this.setState({
                                                            visible: true,
                                                            rowData: item
                                                        })
                                                    }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                    &nbsp;&nbsp;
                                                    {userKey === item.userKey ? <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={() => {
                                                        confirm({
                                                            content: '??????????????????????',
                                                            centered: true,
                                                            onOk: () => {
                                                                this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsult', [item]).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                    } else {
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }} src={require('../../imgs/shanchu.svg')} alt="" /> : null}
                                                </div>
                                                <div style={{ width: '20%', float: 'right', textAlign: 'right' }}>
                                                    {realName === 'admin' || this.state.userFlag ? <Tag color='red' style={{ cursor: 'pointer' }} onClick={this.handelDelete.bind(this, item)}>
                                                        ??????
                                                    </Tag> : null}
                                                    <Tag color={item.topFlag === '0' ? 'grey' : 'blue'} style={{ cursor: 'pointer' }} onClick={this.handelTopFlag.bind(this, item)}>
                                                        {item.topFlag === '0' ? '?????????' : '??????'}
                                                    </Tag>
                                                </div>
                                            </div>
                                            <div style={{ fontSize: '14px' }}>
                                                <Tag color={'red'} style={{ cursor: 'pointer' }} onClick={this.handelQuestionTypeName.bind(this, item)}>
                                                    {item.questionTypeName.split(',')[1]}&nbsp;&nbsp;
                                                </Tag>
                                                <Tag color={item.readFlag === '0' ? 'grey' : 'blue'} style={{ cursor: 'pointer' }} onClick={this.handelReadFlag.bind(this, item)}>
                                                    {item.readFlag === '0' ? '??????' : '??????'}
                                                </Tag>
                                            </div>
                                        </div>
                                    }
                                    avatar={
                                        <Avatar
                                            src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                            alt=""
                                        />
                                    }
                                    content={
                                        <div style={{ width: '100%', overflow: 'hidden' }}>
                                            <div style={{ width: '80%', float: 'left', paddingRight: '5%' }}>
                                                <div><span style={{ fontWeight: 'bold' }}>????????????</span>{item.keyWord}</div>
                                                <div style={{ fontWeight: 'bold' }}>{item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.substring(item.deptName.indexOf(",") + 1, item.deptName.length).split(',').join('???') : item.deptName}???{item.userName}&nbsp;&nbsp;&nbsp;{moment(item.releaseTime).format('YYYY-MM-DD')}</div>
                                                <div style={{ textIndent: '20px' }}>{item.questionDescribe}</div>
                                            </div>
                                            <div style={{ width: '20%', float: 'right' }}>
                                                <div>?????????</div>
                                                {
                                                    item.attachmentList.length ? item.attachmentList.map((item, index) => {
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
                                    }
                                >
                                    {children}
                                </Comment>
                            );
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                >
                                    <ExampleComment>
                                        <Collapse onChange={(CollapseKey) => {
                                            this.setState({
                                                CollapseData: item,
                                                CollapseKey
                                            },() => {
                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                            })
                                        }} activeKey={(this.state.CollapseData && this.state.CollapseData.consultId === item.consultId) ? this.state.CollapseKey : []}>
                                            {
                                                item.childrenList.length && item.childrenList.map((items, index) => {
                                                    const ExampleComments = ({ children }) => (
                                                        <Comment
                                                            author={
                                                                <div style={{ width: '100%', fontSize: '14px' }}>
                                                                    {items.deptName && items.deptName.indexOf(',') !== -1 ? items.deptName.substring(items.deptName.indexOf(",") + 1, items.deptName.length).split(',').join('???') : items.deptName}???{items.userName}&nbsp;&nbsp;??????&nbsp;&nbsp;
                                                                    {items.consultDept && items.consultDept.indexOf(',') !== -1 ? items.consultDept.substring(items.consultDept.indexOf(",") + 1, items.consultDept.length).split(',').join('???') : items.consultDept}???{items.consultUser}&nbsp;&nbsp;
                                                                    {moment(items.replyTime).format('YYYY-MM-DD HH:mm:ss')}&nbsp;&nbsp;
                                                                <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={(e) => {
                                                                        e.stopPropagation();
                                                                        this.setState({
                                                                            replyData: items,
                                                                            visibleReply: true
                                                                        })
                                                                    }} src={require('../../imgs/huifu.svg')} alt="" />
                                                                    &nbsp;&nbsp;
                                                                {userKey === items.userKey ? <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={(e) => {
                                                                        e.stopPropagation();
                                                                        this.setState({
                                                                            visibleEdit: true,
                                                                            editData: items
                                                                        })
                                                                    }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                                    &nbsp;&nbsp;
                                                                {userKey === items.userKey ? <img style={{ cursor: 'pointer', width: '22px', height: '22px' }} onClick={(e) => {
                                                                        e.stopPropagation();
                                                                        confirm({
                                                                            content: '??????????????????????',
                                                                            centered: true,
                                                                            onOk: () => {
                                                                                this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [items]).then(({ success, message, data }) => {
                                                                                    if (success) {
                                                                                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                                    } else {
                                                                                        Msg.error(message);
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }} src={require('../../imgs/shanchu.svg')} alt="" /> : null}
                                                                </div>
                                                            }
                                                            avatar={
                                                                <Avatar
                                                                    src={items.userHeadUrl ? items.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                                                    alt=""
                                                                />
                                                            }
                                                            content={
                                                                <div>
                                                                    {items.replyContent}
                                                                </div>
                                                            }
                                                        >
                                                            {children}
                                                        </Comment>
                                                    );
                                                    return (
                                                        <Panel showArrow={items.childrenList.length ? true : false} header={<ExampleComments />} key={index}>
                                                            {this.generateMenu(items.childrenList)}
                                                        </Panel>
                                                    )
                                                })
                                            }
                                        </Collapse>
                                    </ExampleComment>
                                </div>
                            );
                        }}
                    /> : null}
                </div>
                {visible ? <div>
                    <Modal
                        width={'800px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title={rowData ? '??????' : "??????"}
                        visible={visible}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            data={rowData}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 3 },
                                    sm: { span: 3 }
                                },
                                wrapperCol: {
                                    xs: { span: 21 },
                                    sm: { span: 21 }
                                }
                            }}
                            formConfig={[
                                {
                                    field: 'consultId',
                                    label: '??????',
                                    type: 'string',
                                    hide: true
                                },
                                {
                                    field: 'questionTypeId',
                                    label: '????????????',
                                    type: 'cascader',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId',
                                    },
                                    fetchConfig: {
                                        apiName: 'getBaseCodeTree',
                                        otherParams: {
                                            itemId: 'wenTiLeiXing'
                                        }
                                    },
                                    required: true,
                                    placeholder: '?????????',
                                },
                                {
                                    field: 'keyStr',
                                    label: '?????????',
                                    type: 'select',
                                    mode: "tags",
                                    multiple: false,
                                    optionData: [{ label: '???????????????', value: '???????????????' }],
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    field: 'questionTitle',
                                    label: '????????????',
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    field: 'questionDescribe',
                                    label: '????????????',
                                    type: 'textarea',
                                    required: true,
                                    placeholder: '?????????'
                                },
                                {
                                    field: 'releaseTime',
                                    label: '????????????',
                                    type: 'date',
                                    format: 'YYYY-MM-DD',
                                    hide: true,
                                    initialValue: new Date(),
                                    placeholder: '?????????'
                                },
                                {
                                    label: '????????????',
                                    field: 'expertList',
                                    type: 'treeSelect',
                                    treeSelectOption: {
                                        selectType: "2",
                                        fetchConfig: {//??????????????????????????????????????????
                                            apiName: 'getZjSjConsultExpertTree'
                                        }
                                    }
                                },
                                {
                                    type: 'component',
                                    field: 'warning',
                                    Component: obj => {
                                        return (
                                            <div style={{ height: '32px', lineHeight: '32px', paddingLeft: '110px', background: '#f0f2f5' }}>
                                                ?????????????????????????????????,?????????????????????,???????????????????????????????????????
                                            </div>
                                        );
                                    },
                                    offse: 4,
                                    formItemLayout: {
                                        labelCol: {
                                            xs: { span: 0 },
                                            sm: { span: 0 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 24 }
                                        }
                                    },
                                },
                                {
                                    label: '??????',
                                    field: 'attachmentList',
                                    type: 'files',
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload'
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
                                    field: 'submit',
                                    onClick: (obj) => {
                                        if (rowData) {
                                            let body = {
                                                ...rowData,
                                                ...obj.values
                                            };
                                            obj.btnfns.fetchByCb('updateZjSjConsultOnlineConsult', body, ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                    this.setState({ visible: false });
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        } else {
                                            let body = {
                                                ...obj.values
                                            };
                                            obj.btnfns.fetchByCb('addZjSjConsultOnlineConsult', body, ({ data, success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                    this.setState({ visible: false });
                                                } else {
                                                    Msg.error(message);
                                                }
                                            });
                                        }
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
                {visibles ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="????????????"
                        visible={visibles}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            data={rowData}
                            formConfig={[
                                {
                                    field: 'questionTitle',
                                    label: '????????????',
                                    type: 'string',
                                    disabled: true,
                                    placeholder: '?????????'
                                },
                                {
                                    field: 'questionTypeId',
                                    label: '????????????',
                                    type: 'cascader',
                                    optionConfig: {
                                        label: 'itemName',
                                        value: 'itemId',
                                    },
                                    fetchConfig: {
                                        apiName: 'getBaseCodeTree',
                                        otherParams: {
                                            itemId: 'wenTiLeiXing'
                                        }
                                    },
                                    required: true,
                                    placeholder: '?????????',
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visibles: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
                                    field: 'submit',
                                    onClick: (obj) => {
                                        rowData.questionTypeId = obj.values.questionTypeId;
                                        obj.btnfns.fetchByCb('updateZjSjConsultOnlineConsult', rowData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                this.setState({ visibles: false });
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
                {visibleReply ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="??????"
                        visible={visibleReply}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    type: "string",
                                    label: "????????????",
                                    field: "userName", //?????????????????? ***??????
                                    disabled: true,
                                    initialValue: replyData && replyData.userName ? replyData.userName : null,
                                    placeholder: "?????????",
                                },
                                {
                                    type: "textarea",
                                    label: "????????????",
                                    field: "replyContent", //?????????????????? ***??????
                                    required: true,
                                    placeholder: "?????????",
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visibleReply: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
                                    field: 'submit',
                                    onClick: (obj) => {
                                        let body = {};
                                        if (replyData && replyData.consultRecordId) {
                                            body.parentId = replyData.consultRecordId;
                                            body.parentIdAll = replyData.parentIdAll;
                                            body.replyContent = obj.values.replyContent;
                                            body.consultId = replyData.consultId;
                                            body.questionTypeId = replyData.questionTypeId;
                                            body.consultUser = replyData.userName;
                                            body.consultDept = replyData.deptName;
                                        } else {
                                            body.parentId = '0';
                                            body.replyContent = obj.values.replyContent;
                                            body.consultId = replyData.consultId;
                                            body.questionTypeId = replyData.questionTypeId;
                                            body.consultUser = replyData.userName;
                                            body.consultDept = replyData.deptName;
                                        }
                                        obj.btnfns.fetchByCb('addZjSjConsultOnlineConsultReplyRecord', body, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                this.setState({ visibleReply: false });
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
                {visibleEdit ? <div>
                    <Modal
                        width={'500px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="??????????????????"
                        visible={visibleEdit}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            data={editData}
                            formConfig={[
                                {
                                    type: "string",
                                    label: "????????????",
                                    field: "consultUser", //?????????????????? ***??????
                                    disabled: true,
                                    placeholder: "?????????",
                                },
                                {
                                    type: "textarea",
                                    label: "????????????",
                                    field: "replyContent", //?????????????????? ***??????
                                    required: true,
                                    placeholder: "?????????",
                                }
                            ]}
                            btns={[
                                {
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    field: 'cancel',
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visibleEdit: false });
                                    }
                                },
                                {
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
                                    field: 'submit',
                                    onClick: (obj) => {
                                        editData.replyContent = obj.values.replyContent;
                                        obj.btnfns.fetchByCb('updateZjSjConsultOnlineConsultReplyRecord', editData, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                this.setState({ visibleEdit: false });
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}
export default Index;