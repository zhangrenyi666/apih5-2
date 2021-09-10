import React, { Component } from 'react'
import s from './style.less'
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import Tree from '../modules/tree-v2';
import { message as Msg, Spin, Modal } from 'antd';
const config = {
    antd: {
        rowKey: 'pppTermBaseId',
        size: "small"
    },
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    },
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: '800px'
    },
    firstRowIsSearch: false,
    isShowRowSelect: false,

};
class index extends Component {
    state = {
        loading: false,
        ClickPppTermBaseId: null,
        inShow: "1",
        outShow: "1",
        visibleSend: false,
        loadingSend: false,
        tolCount: 0,
        //键值配置
        treeNodeKeys: {
            label: "analysisKey",
            value: "pppTermBaseId",
            children: "getZjTzPppTermBaseList"
        },
        treeShow: true
    }
    tableByRoot = {
        antd: {
            rowKey: "codeId",
            locale: {
                emptyText: ''
            },
            style: { display: 'none' }
        },
        formConfig: [
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: 'id',
                    field: 'codeId',
                    hide: true,
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    initialValue: '0',
                    field: 'interfaceFlag',
                    hide: true,
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: 'codePid',
                    field: 'codePid',
                    hide: true,
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: 'pidAll',
                    field: 'pidAll',
                    hide: true,
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: 'pidNameAll',
                    field: 'pidNameAll',
                    hide: true,
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    label: '分析重点',
                    type: 'string',
                    required: true,
                    field: 'analysisKey',
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 3 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 21 }
                        }
                    },
                }
            }
        ]
    }
    tableByInRoot = {
        antd: {
            rowKey: "codeId",
            locale: {
                emptyText: ''
            },
            style: { display: 'none' }
        },
        formConfig: [
            {
                isInTable: false,
                form: {
                    field: 'pppTermBaseId',
                    hide: true,
                    label: '主键id',
                    type: 'string'
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    initialValue: '1',
                    field: 'interfaceFlag',
                    hide: true,
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    label: '重点条款',
                    required: true,
                    type: 'string',
                    field: 'keyTerm',
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 5 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 19 }
                        }
                    },
                }
            },
            {
                isInTable: false,
                form: {
                    label: '重点分析内容',
                    type: 'textarea',
                    field: 'keyAnalysisContent',
                    formItemLayoutForm: {
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 5 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 19 }
                        }
                    },
                }
            }
        ]
    }
    handleCancelSend = () => {
        this.setState({ visibleSend: false, loadingSend: false });
    }
    render() {
        const { visibleSend, loadingSend, ClickPppTermBaseId, outShow, treeNodeKeys, inShow, loading, treeShow } = this.state;
        const tableByRoot = this.tableByRoot;
        const tableByInRoot = this.tableByInRoot;
        const tableZB = this.table;
        const { ext1 } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        return (
            <div className={s.root}>
                <div className={s.left}
                    ref={(me) => {
                        if (me) {
                            this.leftDom = me;
                        }
                    }}
                >
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

                    {treeShow ? <Tree
                        visible
                        modalType="common"
                        nodeClick={(node, selectedKeys) => {
                            if (node.props.dataRef['codePid'] === '0') {
                                this.setState({
                                    ClickPppTermBaseId: node.props.dataRef['pppTermBaseId'],
                                    outShow: node.props.dataRef['codePid'],//外层
                                    inShow: node.props.dataRef['codePid']//内层
                                })
                            } else {
                                // Msg.warn('不允许点击')
                            }
                            if (tableZB) {
                                tableZB.clearSelectedRows();
                            }

                        }}
                        myFetch={this.props.myFetch}
                        selectModal="0"
                        btnShow={false}
                        selectText={false}
                        ref={(me) => this.tree = me}
                        nodeRender={nodeData => {
                            return (
                                <span>
                                    {nodeData["analysisKey"]}
                                </span>
                            );
                        }}
                        //右键菜单选项 
                        rightMenuOption={(rightClickNode) => {
                            if (ext1 && ext1 === '1') {
                                // 锁定的加个状态，用状态判断有没有按钮
                                if (rightClickNode.codePid === '0') {
                                    return [
                                        {
                                            label: "新增重点条款",
                                            name: "diy",
                                            cb: (rightClickNode) => {
                                                //打开弹窗
                                                if (this.tableByInRootObj) {
                                                    let nodeInfo = rightClickNode.props.dataRef;
                                                    let expanded = rightClickNode.props.expanded;
                                                    let otherParams = {
                                                        // codePid: nodeInfo.codeId,
                                                        interfaceFlag: '1',
                                                        codePid: nodeInfo.pppTermBaseId,
                                                        analysisKey: nodeInfo.analysisKey,
                                                    }

                                                    this.tableByInRootObj.action(
                                                        {
                                                            drawerTitle: "新增",
                                                            name: "add",
                                                            formBtns: [
                                                                {
                                                                    name: "cancel", //关闭右边抽屉
                                                                    type: "dashed", //类型  默认 primary
                                                                    label: "取消"
                                                                },
                                                                {
                                                                    name: "submit", //内置add del
                                                                    type: "primary", //类型  默认 primary
                                                                    label: "保存", //保存数据并且关闭右边抽屉
                                                                    fetchConfig: {
                                                                        apiName: "addZjTzPppTermBase",
                                                                        otherParams: { ...otherParams }
                                                                    },
                                                                    onClick: (res) => {
                                                                        let _oldData = this.tree.state.data || [];
                                                                        let { value, children } = this.state.treeNodeKeys;

                                                                        let forNode = (nodes) => {
                                                                            for (let i = 0; i < nodes.length; i++) {
                                                                                let item = nodes[i];
                                                                                if (item[value] === res.data.codePid) {
                                                                                    if (!nodes[i][children]) {
                                                                                        nodes[i][children] = [];
                                                                                    }
                                                                                    nodes[i][children].unshift(res.data);
                                                                                }
                                                                                if (item[children] && Array.isArray(item[children]) && item[children].length) {
                                                                                    forNode(item[children]);
                                                                                }
                                                                            }
                                                                            return nodes;
                                                                        }


                                                                        // 更新左边树结构 
                                                                        if (!rightClickNode.props.expanded) {

                                                                            rightClickNode.onExpand();
                                                                        }

                                                                        //展开状态才可以在节点里添加新增的子节点，否则会导致子节点重复

                                                                        if (expanded) {
                                                                            let _d = [..._oldData];
                                                                            let _data = forNode(_d);
                                                                            this.tree.setTreeData(_data)
                                                                        }
                                                                        this.setState({
                                                                            treeShow: false
                                                                        }, () => {
                                                                            this.setState({
                                                                                treeShow: true
                                                                            })
                                                                        })
                                                                        // //更新表格 
                                                                        if (this.table) {
                                                                            this.table.refresh();
                                                                        }

                                                                    }
                                                                }
                                                            ]
                                                        }
                                                    );
                                                }
                                            }
                                        },
                                        {
                                            label: "删除分析重点",
                                            name: "del"
                                        },
                                        // {
                                        //     label: "锁定/解锁",//???
                                        //     name: "diyLock",
                                        //     cb: (obj) => {

                                        //     }
                                        // }
                                    ];
                                } else {
                                    return [
                                        {
                                            label: "编辑重点条款",
                                            name: "editDiy",
                                            cb: (rightClickNode) => {
                                                this.tableByInRootObj.action(
                                                    {
                                                        drawerTitle: "编辑评价指标",
                                                        name: "edit",
                                                        formBtns: [
                                                            {
                                                                name: "cancel", //关闭右边抽屉
                                                                type: "dashed", //类型  默认 primary
                                                                label: "取消"
                                                            },
                                                            {
                                                                name: "submit", //内置add del
                                                                type: "primary", //类型  默认 primary
                                                                label: "保存", //保存数据并且关闭右边抽屉
                                                                fetchConfig: {
                                                                    apiName: "updateZjTzPppTermBase",
                                                                },
                                                                onClick: (res) => {
                                                                    let _oldData = this.tree.state.data || [];
                                                                    let { value, children } = this.state.treeNodeKeys;

                                                                    //更新左边树结构
                                                                    let forNode = (nodes) => {
                                                                        for (let i = 0; i < nodes.length; i++) {
                                                                            let item = nodes[i];
                                                                            if (item[value] === res.data[value]) {
                                                                                nodes[i] = {
                                                                                    ...nodes[i],
                                                                                    ...res.data
                                                                                };
                                                                            }
                                                                            if (item[children] && Array.isArray(item[children]) && item[children].length) {
                                                                                forNode(item[children]);
                                                                            }
                                                                        }
                                                                        return nodes;
                                                                    }
                                                                    let _d = [..._oldData];
                                                                    let _data = forNode(_d);
                                                                    this.tree.setTreeData(_data)
                                                                    this.setState({
                                                                        treeShow: false
                                                                    }, () => {
                                                                        this.setState({
                                                                            treeShow: true
                                                                        })
                                                                    })
                                                                    // //更新表格 
                                                                    if (this.table) {
                                                                        this.table.refresh();
                                                                    }
                                                                }
                                                            }
                                                        ]
                                                    },
                                                    rightClickNode.props.dataRef
                                                );
                                            }
                                        },
                                        {
                                            label: "删除重点条款",
                                            name: "del"
                                        }
                                    ]
                                }
                            } else {
                                return []
                            }


                        }}
                        rightMenuOptionByContainer={ext1 && ext1 === '1' ? [
                            {
                                label: "新增分析重点",
                                name: "diy",
                                cb: () => {
                                    if (this.tableByRootObj) {
                                        this.tableByRootObj.action(
                                            {
                                                drawerTitle: "新增",
                                                name: "add",
                                                formBtns: [
                                                    {
                                                        name: "cancel",
                                                        type: "dashed",
                                                        label: "取消"
                                                    },
                                                    {
                                                        name: "submit",
                                                        type: "primary",
                                                        label: "保存",
                                                        fetchConfig: {
                                                            apiName: "addZjTzPppTermBase",
                                                            otherParams: { codePid: "0" }
                                                        },
                                                        onClick: (res) => {
                                                            let _d = this.tree.getTreeData();
                                                            _d.push(res.data);
                                                            this.tree.setTreeData(_d);
                                                        }
                                                    }
                                                ]
                                            }
                                        );
                                    }
                                }
                            },
                            {
                                label: "导入",
                                name: "diyLock",
                                cb: (obj) => {
                                    this.setState({
                                        visibleSend: true
                                    })
                                }
                            }
                        ] : []}
                        fetchConfig={{
                            parmasKey: "codePid",  //点击节点后将节点id赋值该key上传递给后台
                            apiName: "getZjTzPppTermBaseList",
                            searchApiName: "",
                            params: {
                                codePid: "0"
                            },
                            success: (data) => {
                                let abc = 0;
                                if (data) {
                                    for (var j = 0; j < data.length; j++) {
                                        if (data[j].scoreSubtotal) {
                                            abc += data[j].scoreSubtotal;
                                        }
                                    }
                                    if (abc > 0) {
                                        this.setState({
                                            tolCount: abc
                                        })
                                    }
                                }
                            },
                            // 新增节点的接口
                            nodeAddApiName: "addZjTzPppTermBase",
                            // 数据处理 新增根节时parentNode === 'root' 
                            nodeAddParamsFormat: (nodeInfo, parentNode) => {
                                let params = {
                                    ...nodeInfo,
                                    levelName: '未命名',
                                    codePid: parentNode.props.dataRef.codeId,
                                    pidAll: parentNode.props.dataRef.pidAll,
                                    pidNameAll: parentNode.props.dataRef.pidNameAll,
                                    // propertyFlag: parentNode.props.dataRef.propertyFlag
                                }
                                return params;
                            },
                            nodeAddCb: () => {
                                if (this.table) {
                                    this.table.refresh();
                                }
                            },

                            //编辑
                            nodeEditApiName: "updateZjTzPppTermBase",
                            nodeEditCb: () => {
                                if (this.table) {
                                    this.table.refresh();
                                }
                            },

                            //删除
                            nodeDelApiName: "batchDeleteUpdateZjTzPppTermBase",
                            nodeDelParamsFormat: (nodeInfo, curNode) => {
                                return [nodeInfo]
                            },

                            nodeDelCb: (obj) => {
                                this.setState({
                                    treeShow: false
                                }, () => {
                                    this.setState({
                                        treeShow: true
                                    })
                                })
                                if (this.table) {
                                    this.table.refresh();
                                }
                            },
                        }}
                        keys={{
                            ...treeNodeKeys
                        }}
                        draggable={false}
                    /> : null}

                </div>
                <div className={s.right}>
                    <Spin spinning={loading} >
                        {//点外层---表格
                            inShow === "1" ? null : <QnnTable
                                {...this.props}
                                firstRowIsSearch={true}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                fetchConfig={{
                                    apiName: 'getZjTzPppTermBaseList',
                                    otherParams: {
                                        codePid: ClickPppTermBaseId,
                                        interfaceFlag: '1'
                                    }
                                }}
                                {...config}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'pppTermBaseId',
                                            hide: true,
                                            label: '主键id',
                                            type: 'string'
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'thousandCheckId',//???
                                            hide: true,
                                            initialValue: ClickPppTermBaseId,
                                            label: '外层的id',
                                            type: 'string'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '重点条款',
                                            dataIndex: 'keyTerm',
                                            onClick: 'detail',
                                            key: 'keyTerm'
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            field: 'keyTerm',
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 5 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 19 }
                                                }
                                            },
                                        }
                                    },

                                    {
                                        table: {
                                            title: '重点分析内容',
                                            dataIndex: 'keyAnalysisContent',
                                            key: 'keyAnalysisContent',
                                        },
                                        form: {
                                            label: '重点分析内容',
                                            type: 'textarea',
                                            field: 'keyAnalysisContent',
                                            formItemLayoutForm: {
                                                labelCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 5 }
                                                },
                                                wrapperCol: {
                                                    xs: { span: 24 },
                                                    sm: { span: 19 }
                                                }
                                            },
                                        }
                                    }
                                ]}
                                actionBtns={[]}
                            />
                        }

                        {/* 只做新增根节点的作用 */}
                        <QnnTable
                            wrappedComponentRef={(me) => {
                                this.tableByRootObj = me;
                            }}
                            fetch={this.props.myFetch}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            {...tableByRoot}
                        />
                        {/* 只做新增子节点的作用 */}
                        <QnnTable
                            wrappedComponentRef={(me) => {
                                this.tableByInRootObj = me;
                            }}
                            fetch={this.props.myFetch}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            {...tableByInRoot}
                        />
                        {outShow === "1" && inShow === "1" ? <div className={s.alert}>点击左侧节点即可查看PPP合同风险条款信息</div> : null}
                    </Spin>
                </div>
                <Modal
                    width='500px'
                    style={{ top: '0' }}
                    title="导入"
                    visible={visibleSend}
                    footer={null}
                    onCancel={this.handleCancelSend}
                    bodyStyle={{ width: '500px' }}
                    centered={true}
                    destroyOnClose={this.handleCancelSend}
                    wrapClassName={'modals'}
                >
                    <Spin spinning={loadingSend}>
                        <QnnForm
                            {...this.props}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            formConfig={[
                                {
                                    label: '附件',
                                    field: 'fileList',
                                    required: true,
                                    showDownloadIcon: true,//是否显示下载按钮
                                    onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                    
                                    type: 'files',
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                        otherParams: {
                                            name: '风险及条款设置'
                                        }
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    name: 'cancel',
                                    type: 'dashed',
                                    label: '取消',
                                    isValidate: false,
                                    onClick: () => {
                                        this.setState({
                                            visibleSend: false,
                                            loadingSend: false
                                        })
                                    }
                                },
                                {
                                    name: 'submit',
                                    type: 'primary',
                                    label: '保存',
                                    onClick: (obj) => {
                                        this.setState({
                                            loadingSend: true
                                        })
                                        this.props.myFetch('batchImportZjTzPppTermBase', obj.values).then(
                                            ({ success, message }) => {
                                                if (success) {
                                                    Msg.success(message);
                                                    // this.table.refresh();
                                                    this.setState({
                                                        visibleSend: false,
                                                        loadingSend: false
                                                    }, () => {
                                                        this.setState({
                                                            treeShow: false
                                                        }, () => {
                                                            this.setState({
                                                                treeShow: true
                                                            })
                                                        })
                                                    })
                                                } else {
                                                    Msg.error(message)
                                                }
                                            }
                                        );

                                    }
                                }
                            ]}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 4 },
                                    sm: { span: 4 }
                                },
                                wrapperCol: {
                                    xs: { span: 20 },
                                    sm: { span: 20 }
                                }
                            }}
                        />
                    </Spin>
                </Modal>
            </div >
        )
    }
}

export default index
