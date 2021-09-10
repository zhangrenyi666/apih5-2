import React, { Component } from "react";
import { message as Msg, Modal, Card, Avatar } from 'antd';
import s from "./style.less";
import Tree from "../modules/tree-v2";
class index extends Component {
    constructor() {
        super();
        this.state = {
            visible: false,
            departmentId: '',
            data: [],
            formData: {}
        }
    }
    render() {
        const { visible, departmentId, data, formData } = this.state;
        return (
            <div className={s.root}>
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
                    <Tree
                        selectText={false}
                        modalType="common" //common  drawer  抽屉出现方式或者普通的
                        visible
                        selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        btnShow={false} //是否显示底部按钮
                        disabled={true}
                        draggable={false}
                        canExpand={({ nodeData, props }) => {
                            return false;
                        }}
                        nodeRender={nodeData => {
                            return (
                                <span>
                                    {nodeData["itemName"]}
                                </span>
                            );
                        }}
                        rightMenuOption={[]}
                        nodeClick={(node, selectedKeys) => {
                            this.props.myFetch('getZjSjConsultExpertList', { areasOfExpertiseId: node.props.dataRef.itemId }).then(({ success, message, data }) => {
                                if (success) {
                                    this.setState({
                                        data: data,
                                        departmentId: ''
                                    }, () => {
                                        this.setState({
                                            departmentId: node.props.dataRef.itemId
                                        })
                                    });
                                } else {
                                    Msg.error(message);
                                }
                            });
                        }}
                        //ajax请求配置
                        fetchConfig={{
                            apiName: "getBaseCodeSelect",
                            params: {
                                itemId: 'wenTiLeiXing'
                            }
                        }}
                        //键值配置 默认{value:value,label:label,children:children}
                        keys={{
                            label: "itemName",
                            value: "codeId",
                            children: "children"
                        }}
                    />
                </div>
                <div className={s.rootr}>
                    {departmentId ? <div style={{ overflow: 'hidden', width: '100%', height: '100%' }}>
                        {
                            data.map((item) => {
                                return (
                                    <div key={item.expertId} style={{ float: 'left', padding: '5px', cursor: "pointer" }} onClick={() => {
                                        this.setState({
                                            visible: true,
                                            formData: item
                                        })
                                    }}>
                                        <Card
                                            style={{ width: 180 }}
                                            bodyStyle={{ padding: '5px' }}
                                            cover={
                                                <div style={{width: 180, textAlign:'center'}}>
                                                    <Avatar size={100} src={item.userHeadUrl ? item.userHeadUrl : require('../../imgs/userHeadUrl.svg')} />
                                                </div>
                                            }
                                            actions={null}
                                        >
                                            <div style={{ width: '100%', overflow: 'hidden', whiteSpace: 'normal', textOverflow: 'ellipsis' }}>姓名：{item.userName}</div>
                                            <div style={{ width: '100%', overflow: 'hidden', whiteSpace: 'normal', textOverflow: 'ellipsis' }}>职称：{item.position}</div>
                                            <div style={{ width: '100%', overflow: 'hidden', whiteSpace: 'normal', textOverflow: 'ellipsis' }}>手机号：{item.phone}</div>
                                        </Card>
                                    </div>
                                )
                            })
                        }
                    </div> : <div className={s.alert}>点击左侧节点即可查看详细信息</div>}
                </div>
                {visible ? <div>
                    <Modal
                        width={'400px'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="专家详情"
                        visible={visible}
                        footer={null}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        onCancel={() => {
                            this.setState({
                                visible:false
                            })
                        }}
                        wrapClassName={'replyData'}
                    >
                        <div style={{ width: '100%', overflow: 'hidden' }}>
                            <div style={{ width: '100%', overflow: 'hidden' }}>
                                <div style={{ width: "40%", float: 'left' }}>
                                    <Avatar size={150} src={formData.userHeadUrl ? formData.userHeadUrl : require('../../imgs/userHeadUrl.svg')} />
                                </div>
                                <div style={{ width: "60%", float: 'left' }}>
                                    <div><span style={{fontWeight:'bold'}}>姓名：</span>{formData.userName}</div>
                                    <div><span style={{fontWeight:'bold'}}>手机号：</span>{formData.phone}</div>
                                    <div><span style={{fontWeight:'bold'}}>职务：</span>{formData.postionsName}</div>
                                    <div><span style={{fontWeight:'bold'}}>性别：</span>{formData.sex === '0' ? '未知' : (formData.sex === '1' ? '男' : '女')}</div>
                                    <div><span style={{fontWeight:'bold'}}>年龄：</span>{formData.age ? formData.age : ''}</div>
                                    <div><span style={{fontWeight:'bold'}}>邮箱：</span>{formData.email}</div>
                                    <div><span style={{fontWeight:'bold'}}>职称：</span>{formData.position}</div>
                                    <div><span style={{fontWeight:'bold'}}>擅长领域：</span>{formData.areasOfExpertiseName}</div>
                                </div>
                            </div>
                            <div>
                                <span style={{fontWeight:'bold'}}>简介：</span>{formData.introduction}
                            </div>
                        </div>
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;