import React, { Component } from 'react';
import QnnTable from 'qnn-table';
import s from "./style.less";
import Detail from './Detail';
import Tree from "../../modules/tree";
import { message as Msg, Tabs } from 'antd';
const { TabPane } = Tabs;
class Page extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            treeData: [],
            value: '',
            tabList: [],
            clickNodeId: null,
            clickNodeName: null
        }
    }
    componentDidMount() {
        this.refresh('0');
    }
    refresh(departmentParentId, key) {
        let tableData = null
        this.props.myFetch('getSysDepartmentCurrentTree', { departmentParentId }).then(
            ({ data, success, message }) => {
                if (departmentParentId !== '0') {
                    var loopFn = function (dataList) {
                        for (var i = 0; i < dataList.length; i++) {
                            if (dataList[i].value === key) {
                                dataList[i].children = data;
                            } else if (dataList[i].children) {
                                dataList[i].children = loopFn(dataList[i].children)
                            }
                        }
                        return dataList;
                    }

                    tableData = loopFn(this.state.treeData);
                } else {
                    tableData = data
                }

                if (success) {
                    this.setState({
                        treeData: tableData
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    tabsCallback = (activeKey) => {
        this.setState({
            activeKey
        })
    }
    render() {
        const { activeKey, tabList, clickNodeId, clickNodeName } = this.state;
        const { zhujian, dod } = this.props.match.params;
        return (
            <div className={s.page}>
                <div className={s.rootl}
                    ref={(me) => {
                        if (me) {
                            this.leftDom = me;
                        }
                    }}>
                    <div
                        className={s.hr}
                        ref={(me) => {
                            if (me) {
                                let _this = this;
                                function moveFn(e) {
                                    let conDomLeft = document.getElementsByClassName('ant-layout-content')[0].offsetLeft;
                                    _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                                }
                                me.addEventListener('mousedown', (e) => {
                                    this.onDragStartPos = e.pageX;
                                    document.addEventListener('mousemove', moveFn, false)
                                }, false);
                                document.addEventListener('mouseup', (e) => {
                                    document.removeEventListener('mousemove', moveFn, false)
                                }, false)
                            }
                        }}
                    ></div>
                    {this.state.treeData.length ? <Tree
                        selectText={false}
                        modalType="common" //common  drawer  抽屉出现方式或者普通的
                        visible
                        selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        btnShow={false} //是否显示底部按钮
                        disabled={true}
                        draggable={false}
                        nodeRender={nodeData => {
                            return (
                                <span>
                                    {nodeData["label"]}
                                </span>
                            );
                        }}
                        treeProps={{
                            showLine: true
                        }}
                        rightMenuOption={[]}
                        nodeClick={(node, selectedKeys) => {
                            this.refresh(node.value, selectedKeys[0])
                            this.setState({
                                value: ''
                            }, async () => {
                                const { data, success, message } = await this.props.myFetch('getZjXmSalaryProvidentFundSchemeListStatus', {});
                                this.setState({
                                    value: node,
                                    tabList: data,
                                    clickNodeId: node.value,
                                    clickNodeName: node.label
                                })
                            })
                        }}
                        data={this.state.treeData}
                        keys={{
                            label: "label",
                            value: "value",
                            children: "children"
                        }}
                    /> : null}
                </div>
                <div className={s.rootr}>
                    {Object.keys(this.state.value).length && tabList ?
                        <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                            {
                                tabList.map((item, index) => {
                                    return <TabPane tab={item.name} key={(index + 1).toString()}>
                                        {activeKey ? <Detail {...this.props} clickNodeId={clickNodeId} clickNodeName={clickNodeName} zhujiandetail={zhujian} tabId={item.zjXmSalaryProvidentFundSchemeId} tabName={item.name} dod={dod}/> : null}
                                    </TabPane>
                                })
                            }
                        </Tabs> : <div className={s.alert}>点击左侧节点即可查看数据</div>}
                </div>
            </div>)
    }
}
export default Page;