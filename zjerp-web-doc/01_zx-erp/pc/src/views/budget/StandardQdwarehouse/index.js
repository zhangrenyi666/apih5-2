import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const config = {
    antd: {
        rowKey: function (row) {
            return row.zxBuWorksId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 3 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 21 },
            sm: { span: 21 }
        }
    }
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
            valuePid:'',
            value:'',
            treeData: null,
            loading: false,
            defaultExpandedKeys: []
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        this.props.myFetch('getZxBuWorksTree', {
            "orgID": "0"
        }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { valuePid, value, treeData, defaultExpandedKeys, loading } = this.state;
        // const { projectid } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        return (
            <Spin spinning={loading}>
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
                        {treeData ? <Tree
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
                            defaultExpandedKeys={defaultExpandedKeys}
                            rightMenuOption={[]}
                            nodeClick={(node) => {
                                this.setState({
                                    valuePid: ''
                                }, () => {
                                    this.setState({
                                        defaultExpandedKeys: [node.value],
                                        value: node.value,
                                        valuePid: node.valuePid
                                    })
                                })
                            }}
                            data={this.state.treeData}
                            //键值配置 默认{value:value,label:label,children:children}
                            keys={{
                                label: "label",
                                value: "value",
                                children: "children"
                            }}
                        /> : null}
                    </div>
                    <div className={s.rootr}>
                        {valuePid ? <QnnTable
                            {...this.props}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => {
                                this.tableQD = me;
                            }}
                            fetchConfig={{
                                apiName: 'getZxBuWorksTreeList',
                                otherParams: {
                                    zxBuWorksId: this.state.value,
                                    // orgID: props.initialValues.orgID
                                    orgID: "0",
                                }
                            }}
                            // desc={<div><span style={{ color: "black" }}>提示：黑色字体表示未变更</span> <span style={{ color: "#ffd400" }}>黄色字体表示变更中</span> <span style={{ color: "red" }}>红色字体表示变更后</span></div>}
                            actionBtns={[
                                
                            ]}
                            {...config}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'zxBuWorksId',
                                        initialValue: value,
                                        type: 'string',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'parentID',
                                        type: 'string',
                                        initialValue: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                if (this.tableQD.state.data.length) {
                                                    let qdxzRowData = this.tableQD.state.data[0];
                                                    return qdxzRowData.parentID
                                                }
                                            }
                                        },
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'orgID',
                                        type: 'string',
                                        initialValue: '0',
                                        hide: true,
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'treeNode',
                                        type: 'string',
                                        hide: true,
                                        initialValue: (obj) => {
                                            if (obj.clickCb.rowInfo.name === 'add') {
                                                if (this.tableQD.state.data.length) {
                                                    let qdxzRowData = this.tableQD.state.data[0];
                                                    return qdxzRowData.treeNode
                                                }
                                            }
                                        },
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'isLeaf',
                                        type: 'number',
                                        hide: true,
                                    }
                                },
                                {
                                    table: {
                                        title: '清单编号',
                                        dataIndex: 'workNo',
                                        key: 'workNo',
                                        // align: "center",
                                        render: (data, rowData) => {
                                            return <div style={{ textIndent: (rowData.treeNode.length - 4) * 4 + 'px' }}>{data}</div>;
                                            // return data;
                                        }
                                    },
                                    form: {
                                        type: 'string',
                                        placeholder: '请输入',
                                        spanForm: 12,
                                        formItemLayout: {
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '清单名称',
                                        dataIndex: 'workName',
                                        key: 'workName',
                                        align: "center",
                                        // onClick: 'detail',
                                    }
                                },
                                {
                                    table: {
                                        title: '计量单位',
                                        dataIndex: 'unit',
                                        key: 'unit',
                                        align: "center"
                                    }
                                },
                                {
                                    table: {
                                        title: '是否局下达',
                                        dataIndex: 'isGroup',
                                        key: 'isGroup',
                                        align: "center",
                                        render: (val)=>{
                                            if(val===0){
                                                return '是'
                                            }else {
                                                return '否'
                                            }
                                        }
                                    },
                                }
                            ]}
                        /> : null}
                    </div>
                </div>
            </Spin>
        );
    }
}

export default index;