import React, { Component } from "react";
import s from "./style.less";
import { NavBar, Icon, Tabs, Toast } from "antd-mobile";
import { ProfileOutlined, PlusOutlined, UserOutlined } from '@ant-design/icons';
import { push } from 'react-router-redux';
import MyZXList from "../modules/MZXList";
import { Modal, Comment, Avatar, Tag, Divider, Drawer } from 'antd';
import QnnForm from '../modules/qnn-table/qnn-form';
import moment from 'moment';
const { confirm } = Modal;
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            realName: props.loginAndLogoutInfo.loginInfo.userInfo.realName || '',
            userKey: props.loginAndLogoutInfo.loginInfo.userInfo.userKey || '',
            consultId: props.match.params.consultId === '0' ? '' : props.match.params.consultId,
            visibleReply: false,
            visible: false,
            visibleEdit: false,
            replyData: null,
            rowData: null,
            editData: null,
            page: 0,
            pages: 0
        }
    }
    onRef = (ref) => {
        this.child = ref
    }
    // getData = (childrenList) => {
    //     var arr = []
    //     for (var i = 0; i < childrenList.length; i++) {
    //         arr.push(childrenList[i]);
    //         if (childrenList[i].childrenList) arr = arr.concat(this.getData(childrenList[i].childrenList));
    //     }
    //     return arr;
    // }
    handelReadFlag = (item) => {
        let rowData = item;
        if (rowData.readFlag === '0') {
            rowData.readFlag = '1';
            this.props.myFetch('addZjSjConsultOnlineConsultReadFlag', { ...rowData }).then(({ success, message, data }) => {
                if (success) {
                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords })
                } else {
                    Toast.fail(message);
                }
            });
        }
    }
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
                                {item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.substring(item.deptName.indexOf(",") + 1, item.deptName.length).split(',').pop() : item.deptName}???{item.userName}&nbsp;&nbsp;??????&nbsp;&nbsp;
                                {item.consultDept && item.consultDept.indexOf(',') !== -1 ? item.consultDept.substring(item.consultDept.indexOf(",") + 1, item.consultDept.length).split(',').pop() : item.consultDept}???{item.consultUser}&nbsp;&nbsp;
                                {moment(item.replyTime).format('YYYY-MM-DD HH:mm:ss')}
                            </div>
                        }
                        avatar={
                            <div style={{ overflow: 'hidden' }}>
                                <div style={{ float: 'left' }}>
                                    <div>
                                        <img
                                            onClick={() => {
                                                this.setState({
                                                    replyData: item,
                                                    visibleReply: true
                                                })
                                            }}
                                            style={{ width: '22px', height: '22px' }}
                                            src={require('../../imgs/hf.svg')}
                                            alt=""
                                        />
                                    </div>
                                    {userKey === item.userKey ? <div style={{ paddingTop: '5px' }}>
                                        <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                            this.setState({
                                                visibleEdit: true,
                                                editData: item
                                            })
                                        }} src={require('../../imgs/bianji.svg')} alt="" />
                                    </div> : null}
                                    {userKey === item.userKey ? <div style={{ paddingTop: '5px' }}>
                                        <img
                                            onClick={() => {
                                                confirm({
                                                    content: '??????????????????????',
                                                    centered: true,
                                                    onOk: () => {
                                                        this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [item]).then(({ success, message, data }) => {
                                                            if (success) {
                                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                            } else {
                                                                Toast.fail(message);
                                                            }
                                                        });
                                                    }
                                                });
                                            }}
                                            style={{ width: '22px', height: '22px' }}
                                            src={require('../../imgs/shanchu.svg')}
                                            alt=""
                                        />
                                    </div> : null}
                                </div>
                                <div style={{ float: 'left' }}>
                                    <Avatar
                                        src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                        alt=""
                                    />
                                </div>
                            </div>
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
        const { visibleReply, visible, visibleEdit, replyData, rowData, editData, page, pages, userKey, consultId } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root} id='root'>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}App`));
                        }}
                    >
                        {"??????????????????"}
                    </NavBar>
                </div>
                <div style={{ height: document.documentElement.clientHeight - 45, width: '100%' }}>
                    <Tabs
                        tabs={[{ title: <div><ProfileOutlined /> ????????????</div> }, { title: <div><PlusOutlined /> ??????</div> }, { title: <div><UserOutlined /> ????????????</div> }]}
                        tabBarPosition='bottom'
                        swipeable={false} page={page}
                        onChange={(_, page) => {
                            this.setState({
                                page
                            })
                        }}>
                        <div>
                            {page === 0 ? <div style={{ height: document.documentElement.clientHeight - 88.5, width: '100%', padding: '5px' }}><MyZXList
                                loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                myFetch={this.props.myFetch}
                                upload={this.props.myUpload} //ajax????????????????????????promise
                                onRef={this.onRef}
                                searchKey="searchContent"
                                fetchConfig={{//?????????ajax??????
                                    apiName: 'getZjSjConsultOnlineConsultList',
                                    otherParams: {
                                        selectType: '0',
                                        consultId: this.state.consultId
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
                                                        <div style={{ fontSize: '14px', fontWeight: 'bold', width: '100%', color: 'black' }}>
                                                            {item.questionTitle}
                                                        </div>
                                                    </div>
                                                    <div style={{ width: '100%', overflow: 'hidden' }}>
                                                        <div style={{ width: '70%', float: 'left' }}>
                                                            <Tag color={'red'}>
                                                                {item.questionTypeName.split(',')[1]}&nbsp;&nbsp;
                                                            </Tag>
                                                            <Tag color={item.readFlag === '0' ? 'grey' : 'blue'} onClick={this.handelReadFlag.bind(this, item)}>
                                                                {item.readFlag === '0' ? '??????' : '??????'}
                                                            </Tag>
                                                        </div>
                                                        <div style={{ width: '30%', float: 'right', textAlign: 'right' }}>
                                                            <Tag color={item.topFlag === '0' ? 'grey' : 'blue'}>
                                                                {item.topFlag === '0' ? '?????????' : '??????'}
                                                            </Tag>
                                                        </div>
                                                    </div>
                                                </div>
                                            }
                                            avatar={
                                                <div style={{ overflow: 'hidden' }}>
                                                    <div style={{ float: 'left' }}>
                                                        <div>
                                                            <img
                                                                onClick={() => {
                                                                    this.setState({
                                                                        replyData: item,
                                                                        visibleReply: true
                                                                    })
                                                                }}
                                                                style={{ width: '22px', height: '22px' }}
                                                                src={require('../../imgs/hf.svg')}
                                                                alt=""
                                                            />
                                                        </div>
                                                        <div style={{ paddingTop: '5px' }}>
                                                            {userKey === item.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                this.setState({
                                                                    visible: true,
                                                                    rowData: item
                                                                })
                                                            }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                        </div>
                                                        <div style={{ paddingTop: '5px' }}>
                                                            {userKey === item.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                confirm({
                                                                    content: '??????????????????????',
                                                                    centered: true,
                                                                    onOk: () => {
                                                                        this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [item]).then(({ success, message, data }) => {
                                                                            if (success) {
                                                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                            } else {
                                                                                Toast.fail(message);
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                            }} src={require('../../imgs/shanchu.svg')} alt="" /> : null}
                                                        </div>
                                                    </div>
                                                    <div style={{ float: 'left' }}>
                                                        <Avatar
                                                            src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                                            alt=""
                                                        />
                                                    </div>
                                                </div>
                                            }
                                            content={
                                                <div style={{ width: '100%', overflow: 'hidden' }}>
                                                    <div><span style={{ fontWeight: 'bold' }}>????????????</span>{item.keyWord}</div>
                                                    <div><span style={{ fontWeight: 'bold' }}>{item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.substring(item.deptName.indexOf(",") + 1, item.deptName.length).split(',').pop() : item.deptName}???{item.userName}&nbsp;&nbsp;&nbsp;{moment(item.releaseTime).format('YYYY-MM-DD')}</span></div>
                                                    <div style={{ textIndent: '20px' }}>{item.questionDescribe}</div>
                                                    <div>?????????</div>
                                                    <div>
                                                        {
                                                            item.attachmentList.length ? item.attachmentList.map((item, index) => {
                                                                return (
                                                                    <div key={index}>
                                                                        <a target='_blank' href={item.mobileUrl}>{item.name}</a>
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
                                                {
                                                    item.childrenList.length && item.childrenList.map((items) => {
                                                        const ExampleComments = ({ children }) => (
                                                            <Comment
                                                                author={
                                                                    <div style={{ width: '100%', fontSize: '14px' }}>
                                                                        {items.deptName && items.deptName.indexOf(',') !== -1 ? items.deptName.substring(items.deptName.indexOf(",") + 1, items.deptName.length).split(',').pop() : items.deptName}???{items.userName}&nbsp;&nbsp;??????&nbsp;&nbsp;
                                                                        {items.consultDept && items.consultDept.indexOf(',') !== -1 ? items.consultDept.substring(items.consultDept.indexOf(",") + 1, items.consultDept.length).split(',').pop() : items.consultDept}???{items.consultUser}&nbsp;&nbsp;
                                                                        {moment(items.replyTime).format('YYYY-MM-DD HH:mm:ss')}
                                                                    </div>
                                                                }
                                                                avatar={
                                                                    <div style={{ overflow: 'hidden' }}>
                                                                        <div style={{ float: 'left' }}>
                                                                            <div>
                                                                                <img
                                                                                    onClick={() => {
                                                                                        this.setState({
                                                                                            replyData: items,
                                                                                            visibleReply: true
                                                                                        })
                                                                                    }}
                                                                                    style={{ width: '22px', height: '22px' }}
                                                                                    src={require('../../imgs/hf.svg')}
                                                                                    alt=""
                                                                                />
                                                                            </div>
                                                                            <div style={{ paddingTop: '5px' }}>
                                                                                {userKey === items.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                                    this.setState({
                                                                                        visibleEdit: true,
                                                                                        editData: items
                                                                                    })
                                                                                }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                                            </div>
                                                                            <div style={{ paddingTop: '5px' }}>
                                                                                {userKey === items.userKey ? <img
                                                                                    onClick={() => {
                                                                                        confirm({
                                                                                            content: '??????????????????????',
                                                                                            centered: true,
                                                                                            onOk: () => {
                                                                                                this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [items]).then(({ success, message, data }) => {
                                                                                                    if (success) {
                                                                                                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                                                    } else {
                                                                                                        Toast.fail(message);
                                                                                                    }
                                                                                                });
                                                                                            }
                                                                                        });
                                                                                    }}
                                                                                    style={{ width: '22px', height: '22px' }}
                                                                                    src={require('../../imgs/shanchu.svg')}
                                                                                    alt=""
                                                                                /> : null}
                                                                            </div>
                                                                        </div>
                                                                        <div style={{ float: 'left' }}>
                                                                            <Avatar
                                                                                src={items.userHeadUrl ? items.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                                                                alt=""
                                                                            />
                                                                        </div>
                                                                    </div>
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
                                                            <ExampleComments key={items.consultRecordId}>
                                                                {this.generateMenu(items.childrenList)}
                                                            </ExampleComments>
                                                        )
                                                    })
                                                }
                                            </ExampleComment>
                                        </div>
                                    );
                                }}
                            /></div> : null}
                        </div>
                        <div style={{ height: document.documentElement.clientHeight - 88.5, width: '100%' }}>
                            {page === 1 ? <QnnForm
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload} //??????????????????promise
                                //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                                headers={{
                                    token: this.props.loginAndLogoutInfo.loginInfo.token
                                }}
                                formConfig={[
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
                                                <div style={{ background: '#f0f2f5', padding: '10px' }}>
                                                    ?????????????????????????????????,?????????????????????,???????????????????????????????????????
                                                </div>
                                            );
                                        },
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
                                        name: "submit",
                                        type: "primary",
                                        label: "??????",
                                        field: 'submit',
                                        onClick: (obj) => {
                                            obj.btnfns.fetchByCb('addZjSjConsultOnlineConsult', obj.values, ({ data, success, message }) => {
                                                if (success) {
                                                    Toast.success(message);
                                                    this.setState({ page: 0 })
                                                } else {
                                                    Toast.fail(message);
                                                }
                                            });
                                        }
                                    }
                                ]}
                            /> : null}
                        </div>
                        <div style={{ height: document.documentElement.clientHeight - 88.5, width: '100%' }}>
                            {page === 2 ? <Tabs
                                tabs={[{ title: '???????????????' }, { title: '???????????????' }]}
                                swipeable={false} page={pages}
                                onChange={(_, pages) => {
                                    this.setState({
                                        pages
                                    })
                                }}>
                                <div style={{ height: document.documentElement.clientHeight - 133.5, width: '100%' }}>
                                    {pages === 0 ? <div style={{ height: document.documentElement.clientHeight - 133.5, width: '100%', padding: '5px' }}><MyZXList
                                        loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload} //ajax????????????????????????promise
                                        onRef={this.onRef}
                                        searchKey="searchContent"
                                        fetchConfig={{//?????????ajax??????
                                            apiName: 'getZjSjConsultOnlineConsultList',
                                            otherParams: {
                                                selectType: '1'
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
                                                                <div style={{ fontSize: '14px', fontWeight: 'bold', width: '100%', color: 'black' }}>
                                                                    {item.questionTitle}
                                                                </div>
                                                            </div>
                                                            <div style={{ width: '100%', overflow: 'hidden' }}>
                                                                <div style={{ width: '70%', float: 'left' }}>
                                                                    <Tag color={'red'}>
                                                                        {item.questionTypeName.split(',')[1]}&nbsp;&nbsp;
                                                                </Tag>
                                                                    <Tag color={item.readFlag === '0' ? 'grey' : 'blue'} onClick={this.handelReadFlag.bind(this, item)}>
                                                                        {item.readFlag === '0' ? '??????' : '??????'}
                                                                    </Tag>
                                                                </div>
                                                                <div style={{ width: '30%', float: 'right', textAlign: 'right' }}>
                                                                    <Tag color={item.topFlag === '0' ? 'grey' : 'blue'}>
                                                                        {item.topFlag === '0' ? '?????????' : '??????'}
                                                                    </Tag>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    }
                                                    avatar={
                                                        <div style={{ overflow: 'hidden' }}>
                                                            <div style={{ float: 'left' }}>
                                                                <div>
                                                                    <img
                                                                        onClick={() => {
                                                                            this.setState({
                                                                                replyData: item,
                                                                                visibleReply: true
                                                                            })
                                                                        }}
                                                                        style={{ width: '22px', height: '22px' }}
                                                                        src={require('../../imgs/hf.svg')}
                                                                        alt=""
                                                                    />
                                                                </div>
                                                                <div style={{ paddingTop: '5px' }}>
                                                                    {userKey === item.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                        this.setState({
                                                                            visible: true,
                                                                            rowData: item
                                                                        })
                                                                    }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                                </div>
                                                                <div style={{ paddingTop: '5px' }}>
                                                                    {userKey === item.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                        confirm({
                                                                            content: '??????????????????????',
                                                                            centered: true,
                                                                            onOk: () => {
                                                                                this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [item]).then(({ success, message, data }) => {
                                                                                    if (success) {
                                                                                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                                    } else {
                                                                                        Toast.fail(message);
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }} src={require('../../imgs/shanchu.svg')} alt="" /> : null}
                                                                </div>
                                                            </div>
                                                            <div style={{ float: 'left' }}>
                                                                <Avatar
                                                                    src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                                                    alt=""
                                                                />
                                                            </div>
                                                        </div>
                                                    }
                                                    content={
                                                        <div style={{ width: '100%', overflow: 'hidden' }}>
                                                            <div><span style={{ fontWeight: 'bold' }}>????????????</span>{item.keyWord}</div>
                                                            <div><span style={{ fontWeight: 'bold' }}>{item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.substring(item.deptName.indexOf(",") + 1, item.deptName.length).split(',').pop() : item.deptName}???{item.userName}&nbsp;&nbsp;&nbsp;{moment(item.releaseTime).format('YYYY-MM-DD')}</span></div>
                                                            <div style={{ textIndent: '20px' }}>{item.questionDescribe}</div>
                                                            <div>?????????</div>
                                                            <div>
                                                                {
                                                                    item.attachmentList.length ? item.attachmentList.map((item, index) => {
                                                                        return (
                                                                            <div key={index}>
                                                                                <a target='_blank' href={item.mobileUrl}>{item.name}</a>
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
                                                        {
                                                            item.childrenList.length && item.childrenList.map((items) => {
                                                                const ExampleComments = ({ children }) => (
                                                                    <Comment
                                                                        author={
                                                                            <div style={{ width: '100%', fontSize: '14px' }}>
                                                                                {items.deptName && items.deptName.indexOf(',') !== -1 ? items.deptName.substring(items.deptName.indexOf(",") + 1, items.deptName.length).split(',').pop() : items.deptName}???{items.userName}&nbsp;&nbsp;??????&nbsp;&nbsp;
                                                                            {items.consultDept && items.consultDept.indexOf(',') !== -1 ? items.consultDept.substring(items.consultDept.indexOf(",") + 1, items.consultDept.length).split(',').pop() : items.consultDept}???{items.consultUser}&nbsp;&nbsp;
                                                                            {moment(items.replyTime).format('YYYY-MM-DD HH:mm:ss')}
                                                                            </div>
                                                                        }
                                                                        avatar={
                                                                            <div style={{ overflow: 'hidden' }}>
                                                                                <div style={{ float: 'left' }}>
                                                                                    <div>
                                                                                        <img
                                                                                            onClick={() => {
                                                                                                this.setState({
                                                                                                    replyData: items,
                                                                                                    visibleReply: true
                                                                                                })
                                                                                            }}
                                                                                            style={{ width: '22px', height: '22px' }}
                                                                                            src={require('../../imgs/hf.svg')}
                                                                                            alt=""
                                                                                        />
                                                                                    </div>
                                                                                    <div style={{ paddingTop: '5px' }}>
                                                                                        {userKey === items.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                                            this.setState({
                                                                                                visibleEdit: true,
                                                                                                editData: items
                                                                                            })
                                                                                        }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                                                    </div>
                                                                                    <div style={{ paddingTop: '5px' }}>
                                                                                        {userKey === items.userKey ? <img
                                                                                            onClick={() => {
                                                                                                confirm({
                                                                                                    content: '??????????????????????',
                                                                                                    centered: true,
                                                                                                    onOk: () => {
                                                                                                        this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [items]).then(({ success, message, data }) => {
                                                                                                            if (success) {
                                                                                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                                                            } else {
                                                                                                                Toast.fail(message);
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });
                                                                                            }}
                                                                                            style={{ width: '22px', height: '22px' }}
                                                                                            src={require('../../imgs/shanchu.svg')}
                                                                                            alt=""
                                                                                        /> : null}
                                                                                    </div>
                                                                                </div>
                                                                                <div style={{ float: 'left' }}>
                                                                                    <Avatar
                                                                                        src={items.userHeadUrl ? items.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                                                                        alt=""
                                                                                    />
                                                                                </div>
                                                                            </div>
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
                                                                    <ExampleComments key={items.consultRecordId}>
                                                                        {this.generateMenu(items.childrenList)}
                                                                    </ExampleComments>
                                                                )
                                                            })
                                                        }
                                                    </ExampleComment>
                                                </div>
                                            );
                                        }}
                                    /></div> : null}
                                </div>
                                <div style={{ height: document.documentElement.clientHeight - 133.5, width: '100%' }}>
                                    {pages === 1 ? <div style={{ height: document.documentElement.clientHeight - 133.5, width: '100%', padding: '5px' }}><MyZXList
                                        loginAndLogoutInfo={this.props.loginAndLogoutInfo}
                                        myFetch={this.props.myFetch}
                                        upload={this.props.myUpload} //ajax????????????????????????promise
                                        onRef={this.onRef}
                                        searchKey="searchContent"
                                        fetchConfig={{//?????????ajax??????
                                            apiName: 'getZjSjConsultOnlineConsultList',
                                            otherParams: {
                                                selectType: '2'
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
                                                                <div style={{ fontSize: '14px', fontWeight: 'bold', width: '100%', color: 'black' }}>
                                                                    {item.questionTitle}
                                                                </div>
                                                            </div>
                                                            <div style={{ width: '100%', overflow: 'hidden' }}>
                                                                <div style={{ width: '70%', float: 'left' }}>
                                                                    <Tag color={'red'}>
                                                                        {item.questionTypeName.split(',')[1]}&nbsp;&nbsp;
                                                                </Tag>
                                                                    <Tag color={item.readFlag === '0' ? 'grey' : 'blue'} onClick={this.handelReadFlag.bind(this, item)}>
                                                                        {item.readFlag === '0' ? '??????' : '??????'}
                                                                    </Tag>
                                                                </div>
                                                                <div style={{ width: '30%', float: 'right', textAlign: 'right' }}>
                                                                    <Tag color={item.topFlag === '0' ? 'grey' : 'blue'}>
                                                                        {item.topFlag === '0' ? '?????????' : '??????'}
                                                                    </Tag>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    }
                                                    avatar={
                                                        <div style={{ overflow: 'hidden' }}>
                                                            <div style={{ float: 'left' }}>
                                                                <div>
                                                                    <img
                                                                        onClick={() => {
                                                                            this.setState({
                                                                                replyData: item,
                                                                                visibleReply: true
                                                                            })
                                                                        }}
                                                                        style={{ width: '22px', height: '22px' }}
                                                                        src={require('../../imgs/hf.svg')}
                                                                        alt=""
                                                                    />
                                                                </div>
                                                                <div style={{ paddingTop: '5px' }}>
                                                                    {userKey === item.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                        this.setState({
                                                                            visible: true,
                                                                            rowData: item
                                                                        })
                                                                    }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                                </div>
                                                                <div style={{ paddingTop: '5px' }}>
                                                                    {userKey === item.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                        confirm({
                                                                            content: '??????????????????????',
                                                                            centered: true,
                                                                            onOk: () => {
                                                                                this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [item]).then(({ success, message, data }) => {
                                                                                    if (success) {
                                                                                        this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                                    } else {
                                                                                        Toast.fail(message);
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
                                                                    }} src={require('../../imgs/shanchu.svg')} alt="" /> : null}
                                                                </div>
                                                            </div>
                                                            <div style={{ float: 'left' }}>
                                                                <Avatar
                                                                    src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                                                    alt=""
                                                                />
                                                            </div>
                                                        </div>
                                                    }
                                                    content={
                                                        <div style={{ width: '100%', overflow: 'hidden' }}>
                                                            <div><span style={{ fontWeight: 'bold' }}>????????????</span>{item.keyWord}</div>
                                                            <div><span style={{ fontWeight: 'bold' }}>{item.deptName && item.deptName.indexOf(',') !== -1 ? item.deptName.substring(item.deptName.indexOf(",") + 1, item.deptName.length).split(',').pop() : item.deptName}???{item.userName}&nbsp;&nbsp;&nbsp;{moment(item.releaseTime).format('YYYY-MM-DD')}</span></div>
                                                            <div style={{ textIndent: '20px' }}>{item.questionDescribe}</div>
                                                            <div>?????????</div>
                                                            <div>
                                                                {
                                                                    item.attachmentList.length ? item.attachmentList.map((item, index) => {
                                                                        return (
                                                                            <div key={index}>
                                                                                <a target='_blank' href={item.mobileUrl}>{item.name}</a>
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
                                                        {
                                                            item.childrenList.length && item.childrenList.map((items) => {
                                                                const ExampleComments = ({ children }) => (
                                                                    <Comment
                                                                        author={
                                                                            <div style={{ width: '100%', fontSize: '14px' }}>
                                                                                {items.deptName && items.deptName.indexOf(',') !== -1 ? items.deptName.substring(items.deptName.indexOf(",") + 1, items.deptName.length).split(',').pop() : items.deptName}???{items.userName}&nbsp;&nbsp;??????&nbsp;&nbsp;
                                                                            {items.consultDept && items.consultDept.indexOf(',') !== -1 ? items.consultDept.substring(items.consultDept.indexOf(",") + 1, items.consultDept.length).split(',').pop() : items.consultDept}???{items.consultUser}&nbsp;&nbsp;
                                                                            {moment(items.replyTime).format('YYYY-MM-DD HH:mm:ss')}
                                                                            </div>
                                                                        }
                                                                        avatar={
                                                                            <div style={{ overflow: 'hidden' }}>
                                                                                <div style={{ float: 'left' }}>
                                                                                    <div>
                                                                                        <img
                                                                                            onClick={() => {
                                                                                                this.setState({
                                                                                                    replyData: items,
                                                                                                    visibleReply: true
                                                                                                })
                                                                                            }}
                                                                                            style={{ width: '22px', height: '22px' }}
                                                                                            src={require('../../imgs/hf.svg')}
                                                                                            alt=""
                                                                                        />
                                                                                    </div>
                                                                                    <div style={{ paddingTop: '5px' }}>
                                                                                        {userKey === items.userKey ? <img style={{ width: '22px', height: '22px' }} onClick={() => {
                                                                                            this.setState({
                                                                                                visibleEdit: true,
                                                                                                editData: items
                                                                                            })
                                                                                        }} src={require('../../imgs/bianji.svg')} alt="" /> : null}
                                                                                    </div>
                                                                                    <div style={{ paddingTop: '5px' }}>
                                                                                        {userKey === items.userKey ? <img
                                                                                            onClick={() => {
                                                                                                confirm({
                                                                                                    content: '??????????????????????',
                                                                                                    centered: true,
                                                                                                    onOk: () => {
                                                                                                        this.props.myFetch('batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord', [items]).then(({ success, message, data }) => {
                                                                                                            if (success) {
                                                                                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                                                                            } else {
                                                                                                                Toast.fail(message);
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });
                                                                                            }}
                                                                                            style={{ width: '22px', height: '22px' }}
                                                                                            src={require('../../imgs/shanchu.svg')}
                                                                                            alt=""
                                                                                        /> : null}
                                                                                    </div>
                                                                                </div>
                                                                                <div style={{ float: 'left' }}>
                                                                                    <Avatar
                                                                                        src={items.userHeadUrl ? items.userHeadUrl : require('../../imgs/userHeadUrl.svg')}
                                                                                        alt=""
                                                                                    />
                                                                                </div>
                                                                            </div>
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
                                                                    <ExampleComments key={items.consultRecordId}>
                                                                        {this.generateMenu(items.childrenList)}
                                                                    </ExampleComments>
                                                                )
                                                            })
                                                        }
                                                    </ExampleComment>
                                                </div>
                                            );
                                        }}
                                    /></div> : null}
                                </div>
                            </Tabs> : null}
                        </div>
                    </Tabs>
                </div>
                {visible ? <div>
                    <Drawer
                        title={'??????'}
                        placement="left"
                        closable={false}
                        onClose={() => {
                            this.setState({
                                visible: false
                            })
                        }}
                        visible={visible}
                        width={300}
                        className='myListDrawer'
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
                                    xs: { span: 10 },
                                    sm: { span: 10 }
                                },
                                wrapperCol: {
                                    xs: { span: 14 },
                                    sm: { span: 14 }
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
                                            <div style={{ background: '#f0f2f5', padding: '10px' }}>
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
                                                    Toast.success(message);
                                                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                    this.setState({ visible: false });
                                                } else {
                                                    Toast.fail(message);
                                                }
                                            });
                                        } else {
                                            let body = {
                                                ...obj.values
                                            };
                                            obj.btnfns.fetchByCb('addZjSjConsultOnlineConsult', body, ({ data, success, message }) => {
                                                if (success) {
                                                    Toast.success(message);
                                                    this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                    this.setState({ visible: false });
                                                } else {
                                                    Toast.fail(message);
                                                }
                                            });
                                        }
                                    }
                                }
                            ]}
                        />
                    </Drawer>
                </div> : null}
                {visibleReply ? <div>
                    <Drawer
                        title={'??????'}
                        placement="left"
                        closable={false}
                        onClose={() => {
                            this.setState({
                                visibleReply: false
                            })
                        }}
                        visible={visibleReply}
                        width={300}
                        className='myListDrawer'
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
                                                Toast.success(message);
                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                this.setState({
                                                    visibleReply: false
                                                });
                                            } else {
                                                Toast.fail(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Drawer>
                </div> : null}
                {visibleEdit ? <div>
                    <Drawer
                        title={'????????????'}
                        placement="left"
                        closable={false}
                        onClose={() => {
                            this.setState({
                                visibleEdit: false
                            })
                        }}
                        visible={visibleEdit}
                        width={300}
                        className='myListDrawer'
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
                                                Toast.success(message);
                                                this.child.manuallyRefreshFun({ searchKeywords: this.child.toSearchKeywords });
                                                this.setState({ visibleEdit: false });
                                            } else {
                                                Toast.fail(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Drawer>
                </div> : null}
            </div>
        );
    }
}
export default Index;