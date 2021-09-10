import React from "react";
import { NavBar, Icon } from "antd-mobile";
import { Form, Divider } from "antd";
import { push } from "react-router-redux";
import { goBack } from "connected-react-router";
import s from "./style.less";
import TreeNode from "../modules/tree";
import MyList from "../modules/MListBG";
import moment from 'moment';
class Demo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            treeOpen: false,
            levelId:props.match.params.levelId === '0' ? '' : props.match.params.levelId,
        };
    }
    componentDidMount() {
        if (!this.state.levelId) {
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
        let { treeOpen, levelId } = this.state;
        return (
            <div className={s.root}>
                <NavBar
                    className={"w-hflow-nav"}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(goBack());
                    }}
                >
                    安全资料库管理列表
                </NavBar>
                <div
                    style={{
                        height: document.documentElement.clientHeight - 45
                    }}>
                    {levelId ? <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload} //ajax方法必须返回一个promise
                        searchKey="title" //搜索时的key
                        fetchConfig={{//表格的ajax配置
                            apiName: 'getXmZlDatabaseList',
                            otherParams:{
                                levelId: levelId
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
                                        dispatch(push(`${mainModule}safetyStandardizationDetail/${levelId}/${item.databaseId}`));
                                    }}
                                >
                                    <div className={s.topc}>
                                        【{item.title}】
                                    </div>
                                    <Divider style={{ margin: "8px 0px", background: '#108ee9' }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>{item.modifyUserName}</div>
                                        <div className={s.topr}>{moment(item.modifyTime).format('YYYY-MM-DD HH:mm:ss')}</div>
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
                            label: "levelName",
                            value: "levelId",
                            children: "children"
                        }}
                        yes={value => {
                            this.setState({
                                levelId: ''
                            }, () => {
                                this.setState({
                                    levelId: value.levelId
                                }, () => {
                                    if (levelId) {
                                        this.child.onRefresh();
                                    }
                                    this.treeClose.bind(this)();
                                })
                            })
                        }}
                        cancel={() => {
                            this.treeClose.bind(this)();
                        }}
                        setNodeProps={nodeInfo => {
                            return { disableCheckbox: false };
                        }}
                        nodeRender={nodeData => {
                            return (
                                <div>
                                    {`${nodeData["levelName"]}（编码：${nodeData["levelCode"]}）`}
                                </div>
                            );
                        }}
                        fetchConfig={{
                            parmasKey: "parentId",
                            apiName: "getXmZlDatabaseLevelList",
                            params: {
                                parentId: '0'
                            }
                        }}
                    />
                ) : null}
            </div>
        );
    }
}

export default Form.create()(Demo);
