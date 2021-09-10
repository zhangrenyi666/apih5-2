import React, { Component } from "react";
import { Divider,Modal,Spin } from "antd"; //Spin,
import { NavBar, List,Button,Drawer } from "antd-mobile";
import { push } from "react-router-redux";
import MyList from "../../modules/MList";
import s from "./style.less";
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            page: 1,
            limit: 10,
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
            refreshing: false,
            loading: false,
            content: "",
            title: "",
            loadOver: false,
            apiName:'getHasTodoList',
            open: false,
            visible: false,
            fildListOption: [],
            filListUrl: '',
            abd:'none'
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
        const { myFetch } = this.props;
        const { apiName,fildListOption,filListUrl,loading } = this.state;
        myFetch(apiName, { flowId: 'sanMinister,sanPerson' }).then(({ success, data }) => {
            this.setState({
                fildListOption:data
            });

        })
    }
    render() {
        const { apiName,fildListOption,filListUrl,loading,abd } = this.state;
        const { match = {} } = this.props;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const sidebar = (<List>
            {fildListOption.map((item, index) => {
              return (<List.Item key={index} onClick={() => {
                this.setState({
                    visible:true
              })
          }}>{item.flowName}</List.Item>);
            })}
          </List>);
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
                                                dispatch(push(`${mainModule}MinisterPage/${item.workId}/${item.status}/${item.nodeName}`));
                                            }
                                            if(item.flowId === 'sanPerson'){
                                                dispatch(push(`${mainModule}PersonPage/${item.workId}/${item.status}/${item.nodeName}`));
                                            }
                                        }}>明细</Button>
                                        <Button type="primary" size="small" style={{display:aa,marginLeft:'5px'}} onClick={(e) => {
                                            e.stopPropagation();
                                            this.setState({
                                                open: true,
                                                abd:'block'
                                            })
                                        }}>附件</Button>
                                    </div>
                                </div>
                            );
                        }}
                    />
                    
                <Drawer
                    title="附件列表"
                    width="80%"
                    style={{display:abd}}
                    className="my-drawer"
                    // style={{ height: window.innerHeight }}
                    enableDragHandle
                    maskClosable={false}
                    // contentStyle={{ color: '#A6A6A6', textAlign: 'center' }}
                    sidebar={sidebar}
                    open={this.state.open}
                    position='bottom'
                    onOpenChange={this.onOpenChange}
                    docked={false}
                ></Drawer>
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
                            this.setState({ loading: false })
                        }} src={filListUrl}></iframe>
                    </Spin>
                </Modal>
            </div>
        );
    }
}
export default Index;
