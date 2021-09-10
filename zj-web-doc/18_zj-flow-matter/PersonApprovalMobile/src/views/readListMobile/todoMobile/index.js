import React, { Component } from "react";
import { Divider,Modal,Spin,message as Msg } from "antd"; //Spin,
import { NavBar, List,Button,Drawer } from "antd-mobile";
import { push } from "react-router-redux";
import MyList from "../../modules/MList";
import s from "./style.less";
class Index extends Component {
    constructor(props) {
        super(props);
        const { page } = this.props;
        let apiName =
            page === 0 ? "getReadedList" : "getHasReadedList";
        this.state = {
            data: [],
            page: 1,
            limit: 10,
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            refreshing: false,
            loading: false,
            sidebarLoading:false,
            content: "",
            title: "",
            loadOver: false,
            apiName,
            open: false,
            visible: false,
            fildListOption: [],
            filListUrl: '',
            abd: 'none',
            // tupian:''
        };
    }
    onClose = () => {
        this.setState({
            open: false,
        });
    };
    handleCancel = () => {
        this.setState({ visible: false });
    }
    onOpenChange = (...args) => {
        this.setState({ open: !this.state.open,abd:'none' });
    }
    componentDidMount() {
        
    }
    render() {
        const { apiName,fildListOption,filListUrl,loading,abd,sidebarLoading } = this.state;
        const { match = {} } = this.props;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div
                style={{
                    height: window.innerHeight - 43 - 45 - 1,
                    position:"relative"
                }}
            >
                    <MyList
                        myFetch={this.props.myFetch} 
                        upload={this.props.myUpload}
                        searchKey="sendUserName"
                        fetchConfig={{
                            apiName: apiName, 
                            otherParams: {
                                flowId:'sanMinister,sanPerson'
                            }
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            let aa = '';
                            return (
                                <div
                                    className={s.center}
                                    style={{
                                        borderLeft: `3px solid ${
                                            index % 2 === 0 ? "#ff4000" : "#ff9900"
                                        }`
                                    }}
                                    key={index}
                                    onClick={() => {
                                        const {
                                            mainModule
                                        } = this.props.myPublic.appInfo;
                                        this.props.dispatch(
                                            push(
                                                `${mainModule}FlowBy${
                                                    item.flowId
                                                }/${item.flowId}/${
                                                    item.workId
                                                }/${item.title}`
                                            )
                                        );
                                    }}
                                >
                                    <div className={s.top}>
                                        发起人：{item.sendUserName}
                                        <span>{item.flowStatus}</span>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div
                                        style={{
                                            fontSize: "13px",
                                            color: item.cssx === "1" ? "red" : ""
                                        }}
                                    >
                                        {item.title}
                                    </div>
                                    <Divider style={{ margin: "8px 0px",display:aa }} />
                                    <div style={{display:aa,display: 'flex',justifyContent: 'flex-end'}}>
                                        <Button type="primary" size="small" style={{display:aa}} onClick={(e) => {
                                            e.stopPropagation();
                                            if(item.flowId === 'sanMinister'){
                                                dispatch(push(`${mainModule}MinisterPage/${item.workId}/${item.status}/${item.nodeName}/1`));
                                            }
                                            if(item.flowId === 'sanPerson'){
                                                dispatch(push(`${mainModule}PersonPage/${item.workId}/${item.status}/${item.nodeName}/1`));
                                            }
                                        }}>明细</Button>
                                        <Button type="primary" size="small" style={{display:aa,marginLeft:'5px'}} onClick={(e) => {
                                            e.stopPropagation();
                                            this.setState({
                                                open: true,
                                                abd: 'block',
                                                sidebarLoading:true
                                            })
                                            let apiNName = '';
                                            if (item.flowId === 'sanPerson') {
                                                apiNName = 'getZjFlowPersonApproveDetail';
                                            } else if(item.flowId === 'sanMinister') {
                                                apiNName = 'getZjFlowMinisterApproveDetail';
                                            } else {
                                                Msg.warn('当前数据没有流程ID');
                                            }
                                            if (apiNName) {
                                                this.props.myFetch(apiNName, { workId:item.workId }).then(({ success, data, message }) => {
                                                    if (success) {
                                                        if (data.fileList.length) {
                                                            this.setState({
                                                                fildListOption:data.fileList
                                                            }, () => {
                                                                this.setState({
                                                                    sidebarLoading:false
                                                                })
                                                            });
                                                        } else {
                                                            Msg.warn('没有附件数据');
                                                            this.setState({
                                                                open: false,
                                                                abd: 'none',
                                                                sidebarLoading:false
                                                            })
                                                        }
                                                        
                                                    } else {
                                                        Msg.error(message);
                                                        this.setState({
                                                            open: false,
                                                            abd: 'none',
                                                            sidebarLoading:false
                                                        })
                                                    }
                                                });
                                            }
                                        }}>附件</Button>
                                    </div>
                                </div>
                            );
                        }}
                />
                <Drawer
                    title="附件列表"
                    width="80%"
                    style={{ display: abd }}
                    className="my-drawers"
                    enableDragHandle
                    maskClosable={false}
                    contentStyle={{ color: '#A6A6A6', textAlign: 'center',zIndex:0,display:'none' }}
                    sidebar={
                        <Spin spinning={sidebarLoading}>
                            <List style={{ height: fildListOption.length >6 ? window.innerHeight * 0.38 : fildListOption.length*44 }}>
                                {fildListOption && fildListOption.length ? fildListOption.map((item,index) => {
                                    return (<List.Item key={index} onClick={() => {
                                        
                                        this.setState({
                                            visible: true,
                                            loading:true,
                                            filListUrl: item.mobileUrl
                                    })
                                }}>{item.fileName}</List.Item>);
                                }) : ''}
                            </List>
                        </Spin>
                    }
                    open={this.state.open}
                    position='bottom'
                    onOpenChange={this.onOpenChange}
                    docked={false}
                >11
                </Drawer>
                <Modal
                    width={'90%'}
                    style={{ paddingBottom: '0', top: '0' }}
                    title="附件预览"
                    visible={this.state.visible}
                    footer={null}
                    onCancel={this.handleCancel}
                    bodyStyle={{ padding: '10px', overflow: 'auto', height: window.innerHeight * 0.8 }}
                    centered={true}
                    destroyOnClose={this.handleCancel}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loading}>
                        <iframe title="myf" width={window.innerWidth * 2.5} height={window.innerHeight * 10} frameBorder={0} onLoad={(obj) => {
                            this.setState({ loading: false });
                        }} src={filListUrl}></iframe>
                        
                    </Spin>
                </Modal>
            </div>
        );
    }
}
export default Index;
