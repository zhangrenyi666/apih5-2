import React, { Component } from 'react'
import s from './style.less'
import QnnTable from "../modules/qnn-table";
import QnnForm from "../modules/qnn-table/qnn-form";
import Tree from '../modules/tree-v2';
import { message as Msg, Spin, Modal } from 'antd';
const config = {
    antd: {
        rowKey: 'thousandCheckBaseId',
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
        directoryId: null,
        inShow: "1",
        outShow: "1",
        visibleSend: false,
        loadingSend: false,
        tolCount: 0,
        //键值配置
        treeNodeKeys: {
            label: "evalPro",
            value: "thousandCheckBaseId",
            children: "getZjTzThousandCheckBaseList"
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
                    label: 'id',
                    field: 'thousandCheckBaseId',
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
            // {
            //     isInTable: false,
            //     form: {
            //         type: 'string',
            //         label: '字典ID',//自己添加？
            //         field: 'itemId',
            //         placeholder: '请输入',
            //         editDisabled: true,
            //         addDisabled: false,
            //         required: true
            //     }
            // },
            {
                isInTable: false,
                form: {
                    label: '排序',
                    required: true,
                    precision: 0, //数值精度
                    type: 'number',
                    field: 'orderFlag',
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
            },
            {
                isInTable: false,
                form: {
                    label: '项目名称',
                    type: 'string',
                    required: true,
                    field: 'evalPro',
                    // optionConfig: {
                    //     label: 'projectName',
                    //     value: 'projectId'
                    // },
                    // fetchConfig: {
                    //     apiName:"getZjTzProManageList"
                    // },
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
            },
            {
                isInTable: false,
                form: {
                    type: 'textarea',
                    label: '备注',
                    field: 'remarks',
                    placeholder: '请输入',
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
                    field: 'thousandCheckBaseId',
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
                    label: '排序',
                    required: true,
                    precision: 0, //数值精度
                    type: 'number',
                    field: 'orderFlag',
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
                    label: '评价指标',
                    required: true,
                    type: 'string',
                    field: 'evalIndex',
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
                    label: '评分内容及扣分标准',
                    type: 'textarea',
                    field: 'evalContent',
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
                    label: '标准配分',
                    type: 'number',
                    required: true,
                    field: 'score',
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
        const { visibleSend, loadingSend, directoryId, outShow, treeNodeKeys, inShow, loading, treeShow } = this.state;
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
                                    directoryId: node.props.dataRef['thousandCheckBaseId'],
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
                            let aa = '';
                            if (nodeData["scoreSubtotal"]) {
                                aa = nodeData["evalPro"] + '(' + nodeData["scoreSubtotal"] + '分)';
                            } else {
                                aa = nodeData["evalPro"];
                            }
                            return (
                                <span>
                                    {aa}
                                </span>
                            );
                        }}
                        //右键菜单选项 
                        rightMenuOption={(rightClickNode) => {
                            if (ext1 && ext1 === '1') {
                                if (rightClickNode.codePid === '0') {
                                    return [
                                        {
                                            label: "新增评价指标",
                                            name: "diy",
                                            // name: "addRoot",
                                            cb: (rightClickNode) => {
                                                //打开弹窗
                                                if (this.tableByInRootObj) {
                                                    let nodeInfo = rightClickNode.props.dataRef;
                                                    let expanded = rightClickNode.props.expanded;
                                                    let otherParams = {
                                                        // codePid: nodeInfo.codeId,
                                                        interfaceFlag: '1',
                                                        codePid: nodeInfo.thousandCheckBaseId,
                                                        evalPro: nodeInfo.evalPro,
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
                                                                        apiName: "addZjTzThousandCheckBase",
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
                                            label: "编辑评价项目",
                                            name: "editDiy11",
                                            cb: (rightClickNode) => {
                                                this.tableByRootObj.action(
                                                    {
                                                        drawerTitle: "编辑评价项目",
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
                                                                    apiName: "updateZjTzThousandCheckBase",
                                                                    otherParams: {
                                                                        interfaceFlag: '0'
                                                                    }
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
                                            label: "删除评价项目",
                                            name: "del",
                                        }
                                    ];
                                } else {
                                    return [
                                        {
                                            label: "编辑评价指标",
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
                                                                    apiName: "updateZjTzThousandCheckBase",
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
                                            label: "删除评价指标",
                                            name: "del",//内置 add del edit  addRootNode（新增跟节点）
                                        }
                                    ]
                                }
                            } else {
                                return []
                            }


                        }}
                        rightMenuOptionByContainer={ext1 && ext1 === '1' ? [
                            {
                                label: "新增评价项目",
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
                                                            apiName: "addZjTzThousandCheckBase",
                                                            otherParams: { codePid: "0" }
                                                        },
                                                        onClick: (res) => {
                                                            let _d = this.tree.getTreeData();
                                                            _d.push(res.data);
                                                            this.tree.setTreeData(_d);
                                                            this.setState({
                                                                treeShow: false
                                                            }, () => {
                                                                this.setState({
                                                                    treeShow: true
                                                                })
                                                            })
                                                        }
                                                    }
                                                ]
                                            }
                                        );
                                    }
                                }
                            },
                            {
                                label: "导入千分制检查项",
                                name: "diyExport",
                                cb: (obj) => {
                                    this.setState({
                                        visibleSend: true
                                    })
                                    this.setState({
                                        treeShow: false
                                    }, () => {
                                        this.setState({
                                            treeShow: true
                                        })
                                    })
                                }
                            }
                        ] : []}
                        fetchConfig={{
                            parmasKey: "codePid",  //点击节点后将节点id赋值该key上传递给后台
                            apiName: "getZjTzThousandCheckBaseList",
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
                            nodeAddApiName: "addZjTzThousandCheckBase",
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
                            nodeEditApiName: "pcUpdateBaseCodeOnTree",
                            nodeEditCb: () => {
                                if (this.table) {
                                    this.table.refresh();
                                }
                            },

                            //删除
                            nodeDelApiName: "batchDeleteUpdateZjTzThousandCheckBase",
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
                                    apiName: 'getZjTzThousandCheckBaseList',
                                    otherParams: {
                                        codePid: directoryId,
                                        interfaceFlag: '1'
                                    }
                                }}
                                {...config}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'thousandCheckBaseId',
                                            hide: true,
                                            label: '主键id',
                                            type: 'string'
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'thousandCheckId',//???传什么外层的主键id
                                            hide: true,
                                            initialValue: directoryId,
                                            label: '外层的id',
                                            type: 'string'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '排序',
                                            dataIndex: 'orderFlag',
                                            key: 'orderFlag',
                                        },
                                        form: {
                                            label: '排序',
                                            required: true,
                                            precision: 0, //数值精度
                                            type: 'number',
                                            field: 'orderFlag',
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
                                            title: '评价指标',
                                            dataIndex: 'evalIndex',
                                            onClick: 'detail',
                                            key: 'evalIndex'
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            field: 'evalIndex',
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
                                            title: '评分内容及扣分标准',
                                            dataIndex: 'evalContent',
                                            tooltip: 50,
                                            width: '50%',
                                            key: 'evalContent',
                                        },
                                        form: {
                                            label: '评分内容及扣分标准',
                                            type: 'textarea',
                                            field: 'evalContent',
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
                                            title: '标准配分',
                                            width: 120,
                                            dataIndex: 'score',
                                            key: 'score',
                                        },
                                        form: {
                                            label: '标准配分',
                                            type: 'number',
                                            required: true,
                                            field: 'score',
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
                        {outShow === "1" && inShow === "1" ? <div className={s.alert}>点击左侧节点即可查看千分制检查项信息</div> : null}
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
                                    type: 'files',
                                    showDownloadIcon: true,//是否显示下载按钮
                                    onPreview: "bind:_docFilesByOfficeUrl",//365显示
                                    
                                    fetchConfig: {
                                        apiName: window.configs.domain + 'upload',
                                        otherParams: {
                                            name: '千分制设置'
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
                                        this.props.myFetch('batchImportZjTzThousandCheckBase', obj.values).then(
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
