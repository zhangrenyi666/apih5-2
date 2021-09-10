import React from "react";
import { NavBar, Icon } from "antd-mobile";
import { push } from "react-router-redux";
import s from "./style.less";
import TreeNode from "../modules/tree";
import MyList from "../modules/MEList";
import { Avatar } from 'antd';
class Demo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            treeOpen: false,
            itemId:props.match.params.itemId === '0' ? '' : props.match.params.itemId,
        };
    }
    componentDidMount() {
        if (!this.state.itemId) {
            this.setState({ treeOpen: true });
        }
    }
    treeClose = () => {
        this.setState({
            treeOpen: false
        });
    };
    onRef = (ref) => {
        this.child = ref
    }
    render() {
        const {
            dispatch,
            myFetch,
            myPublic: { appInfo: { mainModule } },
        } = this.props;
        let { treeOpen, itemId } = this.state;
        return (
            <div className={s.root}>
                <NavBar
                    className={"w-hflow-nav"}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}App`));
                    }}
                >
                    专家介绍
                </NavBar>
                <div
                    style={{
                        height: document.documentElement.clientHeight - 45
                    }}>
                    {itemId ? <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload} //ajax方法必须返回一个promise
                        searchKey="userName" //搜索时的key
                        fetchConfig={{//表格的ajax配置
                            apiName: 'getZjSjConsultExpertList',
                            otherParams:{
                                areasOfExpertiseId: itemId
                            }
                        }}
                        onRef={this.onRef}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    onClick={() => {
                                        dispatch(push(`${mainModule}ExpertIntroductionDetail/${itemId}/${item.expertId}`));
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>
                                            <Avatar size={100} src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')} />
                                        </div>
                                        <div className={s.topr}>
                                            <div>{item.userName}&nbsp;&nbsp;{item.position}</div>
                                            <div>电话：{item.phone}</div>
                                            <div>擅长领域：{item.areasOfExpertiseName}</div>
                                        </div>
                                    </div>
                                </div>
                            );
                        }}
                    /> : <div style={{ textAlign: 'center', fontWeight: 'bold', paddingTop: '10vh' }}>请选择节点查看数据...</div>}
                </div>
                {/* 控制工序选择面包显隐状态的按钮 */}
                {treeOpen ? <div
                        className={s.treeBtns}
                        onClick={() => {
                            this.setState({
                                treeOpen: false
                            });
                        }}
                    >
                        <img style={{width:'100%',height:'100%'}} src={require('./you.png')} alt=""/>
                    </div> : (
                    <div
                        className={s.treeBtn}
                        onClick={() => {
                            this.setState({
                                treeOpen: true
                            });
                        }}
                    >
                        <img style={{width:'100%',height:'100%'}} src={require('./zuo.png')} alt=""/>
                    </div>
                )}

                {treeOpen ? (
                    <TreeNode
                        myFetch={myFetch}
                        visible
                        keys={{
                            label: "itemName",
                            value: "itemId",
                            children:'children'
                        }}
                        yes={value => {
                            this.setState({
                                itemId: ''
                            }, () => {
                                this.setState({
                                    itemId: value.itemId
                                }, () => {
                                    if (itemId) {
                                        this.child.onRefresh();
                                    }
                                    this.treeClose.bind(this)();
                                })
                            })
                        }}
                        cancel={() => {
                            this.treeClose.bind(this)();
                        }}
                        nodeRender={nodeData => {
                            return (
                                <div>
                                    {`${nodeData["itemName"]}`}
                                </div>
                            );
                        }}
                        fetchConfig={{
                            parmasKey: "parentId",
                            apiName: "getBaseCodeSelect",
                            params: {
                                itemId: 'wenTiLeiXing'
                            }
                        }}
                    />
                ) : null}
            </div>
        );
    }
}

export default Demo;
