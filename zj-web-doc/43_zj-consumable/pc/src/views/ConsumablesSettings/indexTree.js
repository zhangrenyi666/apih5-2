import React, { Component } from 'react'
import s from './style.less'
import QnnTable from "../modules/qnn-table";
import Tree from '../modules/tree-v2';
import { message as Msg, Spin } from 'antd';
const config = {
    antd: {
        rowKey: 'setId',
        size: "small"
    },
    isShowRowSelect: true,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 6 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 18 }
        }
    },
    paginationConfig: {
        position: 'bottom'
    },
    drawerConfig: {
        width: '800px'
    },
    firstRowIsSearch: false

};
class index extends Component {
    state = {
        directoryId: null,
        inShow: "5",
        //键值配置
        treeNodeKeys: {
            label: "category",
            value: "setId",
            children: "childrenList"
        },
        treeShow: true,
        categoryVal: '',
        brandVal: '',
        modelVal: ''
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
                    initialValue: '0',
                    field: 'typeFlag',
                    hide: true,
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    initialValue: '0',
                    field: 'codePid',
                    hide: true,
                    placeholder: '请输入',
                }
            },

            {
                isInTable: false,
                form: {
                    label: '类别',
                    type: 'string',
                    required: true,
                    field: 'category',
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
    render() {
        const { directoryId, treeNodeKeys, inShow, treeShow, categoryVal, brandVal, modelVal } = this.state;
        const tableZB = this.table;
        const tableByRoot = this.tableByRoot;
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
                            // console.log(node.props.dataRef.typeFlag);
                            if (node.props.dataRef.typeFlag === '3') {

                            } else {
                                this.setState({
                                    categoryVal: null,
                                    inShow: null,
                                    brandVal: null,
                                    modelVal: null
                                }, () => {
                                    this.setState({
                                        directoryId: node.props.dataRef['setId'],
                                        inShow: node.props.dataRef.typeFlag,//内层
                                        categoryVal: node.props.dataRef.category,
                                        brandVal: node.props.dataRef.brand,
                                        modelVal: node.props.dataRef.model,
                                    })
                                    if (tableZB) {
                                        tableZB.clearSelectedRows();
                                    }
                                })
                            }
                        }}
                        myFetch={this.props.myFetch}
                        selectModal="0"
                        btnShow={false}
                        selectText={false}
                        ref={(me) => this.tree = me}
                        nodeRender={nodeData => {
                            let name = '';
                            if (nodeData.typeFlag === '0') {
                                name = nodeData["category"];
                            } else if (nodeData.typeFlag === '1') {
                                name = nodeData["brand"];
                            } else if (nodeData.typeFlag === '2') {
                                name = nodeData["model"];
                            } else if (nodeData.typeFlag === '3') {
                                name = nodeData["colour"];
                            } else {

                            }

                            return (
                                <span>
                                    {name}
                                </span>
                            );
                        }}
                        rightMenuOption={(rightClickNode) => {
                            // if (rightClickNode.typeFlag === '0') {
                            //     return [
                            //         {
                            //             label: "删除类别",
                            //             name: "del"
                            //         }
                            //     ];
                            // } else {}
                            return []
                        }}
                        rightMenuOptionByContainer={[
                            {
                                label: "新增类别",
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
                                                            apiName: "addZjConsumableSet",
                                                            otherParams: { codePid: "0" }
                                                        },
                                                        onClick: (res) => {
                                                            let _d = this.tree.getTreeData();
                                                            _d.push(res._formData);
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
                        ]}
                        fetchConfig={{
                            parmasKey: "codePid",  //点击节点后将节点id赋值该key上传递给后台
                            apiName: "getZjConsumableSetList",
                            searchApiName: "",
                            params: {
                                codePid: "0"
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
                            //删除
                            nodeDelApiName: "batchDeleteUpdateZjConsumableSet",
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
                    {//点外层---表格
                        // 新增品牌
                        inShow && inShow !== "5" ? <QnnTable
                            // inShow ? <QnnTable
                            {...this.props}
                            firstRowIsSearch={true}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            wrappedComponentRef={(me) => {
                                this.table = me;
                            }}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            fetchConfig={{
                                apiName: 'getZjConsumableSetList',
                                otherParams: () => {
                                    if (directoryId) {
                                        return {
                                            codePid: directoryId
                                        }
                                    } else {
                                        return {
                                            codePid: 0
                                        }
                                    }

                                }
                            }}
                            {...config}
                            formConfig={[
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'setId',
                                        type: 'string',
                                        hide: true
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        type: 'string',
                                        initialValue: () => {
                                            if (inShow === '0') {
                                                return '1'
                                            } else if (inShow === '1') {
                                                return '2'
                                            } else if (inShow === '2') {
                                                return '3'
                                            } else if (inShow === '3') {
                                                return '4'
                                            } else {
                                                return ''
                                            }
                                        },
                                        field: 'typeFlag',
                                        hide: true,
                                        placeholder: '请输入',
                                    }
                                },
                                {
                                    isInTable: false,
                                    form: {
                                        field: 'codePid',
                                        type: 'string',
                                        hide: true,
                                        initialValue: () => {
                                            return directoryId
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '类别',
                                        dataIndex: 'category',
                                        key: 'category',
                                        filter: true
                                    },
                                    form: {
                                        type: 'string',
                                        spanSearch: 8,
                                        placeholder: '请输入',
                                        spanForm: 12,
                                        required: true,
                                        addDisabled: true,
                                        editDisabled: true,
                                        initialValue: () => {
                                            return categoryVal
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '品牌',
                                        dataIndex: 'brand',
                                        key: 'brand',
                                        filter: true
                                    },
                                    form: {
                                        type: 'string',
                                        spanSearch: 8,
                                        placeholder: '请输入',
                                        spanForm: 12,
                                        required: true,
                                        editDisabled: true,
                                        addDisabled: (inShow && inShow === '1' || inShow === '2') ? true : false,
                                        initialValue: () => {
                                            return brandVal
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '型号',
                                        dataIndex: 'model',
                                        key: 'model',
                                        filter: true
                                    },
                                    isInForm: (inShow && inShow === '1' || inShow === '2') ? true : false,
                                    form: {
                                        type: 'string',
                                        spanSearch: 8,
                                        placeholder: '请输入',
                                        spanForm: 12,
                                        required: true,
                                        editDisabled: true,
                                        addDisabled: (inShow && inShow === '2') ? true : false,
                                        initialValue: () => {
                                            return modelVal
                                        }
                                    }
                                },
                                {
                                    table: {
                                        title: '颜色',
                                        dataIndex: 'colour',
                                        key: 'colour',
                                        filter: true
                                    },
                                    isInForm: (inShow && inShow === '2') ? true : false,
                                    form: {
                                        type: 'string',
                                        spanSearch: 8,
                                        placeholder: '请输入',
                                        spanForm: 12,
                                        required: true,
                                        editDisabled: true
                                    }
                                },
                                {
                                    table: {
                                        title: '使用状态',
                                        dataIndex: 'useState',
                                        key: 'useState',
                                        render: (val) => {
                                            return val ? (val === '0' ? '使用中' : '停用') : ''
                                        },
                                        filter: true
                                    },
                                    isInForm: (inShow && inShow === '2') ? true : false,
                                    form: {
                                        type: 'select',
                                        placeholder: '请输入',
                                        spanSearch: 8,
                                        spanForm: 12,
                                        required: true,
                                        optionConfig: {
                                            label: 'label',
                                            value: 'value'
                                        },
                                        optionData: [
                                            {
                                                value: '0',
                                                label: '使用中'
                                            },
                                            {
                                                value: '1',
                                                label: '停用'
                                            }
                                        ]
                                    }
                                }
                            ]}
                            actionBtns={inShow && inShow !== "5" && inShow !== "3" ? [
                                {
                                    name: 'add',
                                    icon: 'plus',
                                    type: 'primary',
                                    label: '新增',
                                    formBtns: [
                                        {
                                            name: 'cancel',
                                            type: 'dashed',
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',
                                            type: 'primary',
                                            label: '提交',
                                            fetchConfig: {
                                                apiName: 'addZjConsumableSet',
                                            },
                                            onClick: (val) => {
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
                                },
                                {
                                    name: 'edit',
                                    icon: 'plus',
                                    type: 'primary',
                                    label: '修改',
                                    hide: () => {
                                        if (inShow === '2') {
                                            return false
                                        } else {
                                            return true
                                        }
                                    },
                                    formBtns: [
                                        {
                                            name: 'cancel',
                                            type: 'dashed',
                                            label: '取消',
                                        },
                                        {
                                            name: 'submit',
                                            type: 'primary',
                                            label: '提交',
                                            fetchConfig: {
                                                apiName: 'updateZjConsumableSet',
                                            },
                                            onClick: (val) => {
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
                                },
                                {
                                    name: 'del',
                                    icon: 'delete',
                                    type: 'danger',
                                    label: '删除',
                                    fetchConfig: {
                                        apiName: 'batchDeleteUpdateZjConsumableSet',
                                    },
                                    onClick: (val) => {
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
                        /> : null
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
                    {inShow === "5" ? <div className={s.alert}>点击左侧节点即可查看耗材相关设置</div> : null}
                </div>
            </div >
        )
    }
}

export default index
