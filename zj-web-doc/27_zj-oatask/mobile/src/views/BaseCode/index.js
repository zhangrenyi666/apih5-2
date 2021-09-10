import React,{ Component } from 'react'
import QnnTable from '../modules/qnn-table';
import QnnForm from '../modules/qnn-table/qnn-form';
import s from './style.less'
import Tree from '../modules/tree'
import { Form,message as Msg,Spin } from 'antd'

class index extends Component {
    state = {
        loading: false,

        //当前点击节点的id
        curClickNodeId: null,

        //当前点击的节点数据
        curClickNodeData: null,

        //键值配置
        treeNodeKeys: {
            label: "itemName",
            value: "codeId",
            children: "getBaseCodeList"
        },


        //右上信息字段 {...config}
        info: null,
        //右边下面部分配置 {...config}
        table: null,
    }

    getConfig = (node) => {
        let codeId = node.props.dataRef.codeId;

        // let data = {
        //     info: {
        //         formConfig: [

        //             {
        //                 type: 'string',
        //                 label: '字段3',
        //                 field: 'field3', //唯一的字段名 ***必传
        //                 placeholder: '请输入',//占位符
        //                 disabled: true,
        //                 span: 8,
        //                 initialValue: '测试001'
        //             }
        //         ]
        //     },
        //     table: {
        //         antd: {
        //             rowKey: "levelId",
        //             title: "标段列表数据"
        //         },
        //         formConfig: [ 
        //         ]
        //     }
        // } 

        //去请求右边配置
        this.setState({ loading: true });
        this.props.myFetch('getBaseCodeUIConfig',{ codeId: codeId }).then(({ success,message,data }) => {
            this.setState({ loading: false });
            if (success) {
                let _res = {
                    table: null,
                    info: null,
                    ...data
                }
                this.setState({
                    ..._res
                },() => {
                    const { info } = this.state;
                    //设置表单信息
                    if (info) {
                        let { formConfig = [] } = info;
                        let _d = QnnForm.sFormatData(node.props.dataRef,formConfig,'set');
                        this.props.form.setFieldsValue({
                            ..._d
                        })
                    }
                })
            } else {
                Msg.error(message)
            }
        })
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
                    type: 'string',
                    label: '字典ID',
                    field: 'itemId',
                    placeholder: '请输入',
                    required: true
                }
            },

            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: '项目名',
                    field: 'itemName',
                    placeholder: '请输入',
                    required: true
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: '项目别名',
                    field: 'itemAsName',
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: '扩展1',
                    field: 'ext1',
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: '扩展2',
                    field: 'ext2',
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: '扩展3',
                    field: 'ext3',
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: '扩展4',
                    field: 'ext4',
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'string',
                    label: '扩展5',
                    field: 'ext5',
                    placeholder: '请输入',
                }
            },
            {
                isInTable: false,
                form: {
                    type: 'textarea',
                    label: '备注',
                    field: 'remarks',
                    placeholder: '请输入',
                }
            }

        ]
    }

    render() {
        const { curClickNodeId,treeNodeKeys: { value },treeNodeKeys,table,info,loading } = this.state;
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
                                me.addEventListener('mousedown',(e) => {
                                    this.onDragStartPos = e.pageX;
                                    document.addEventListener('mousemove',moveFn,false)
                                },false);
                                document.addEventListener('mouseup',(e) => {
                                    document.removeEventListener('mousemove',moveFn,false)
                                },false)
                            }
                        }}
                    ></div>

                    <Tree
                        visible
                        modalType="common"
                        nodeClick={(node,selectedKeys) => {
                            let levelId = node.props.dataRef[value];
                            this.setState({
                                curClickNodeId: levelId,
                                curClickNodeData: node.props.dataRef,
                                table: null
                            })
                            this.getConfig.bind(this)(node);
                        }}
                        myFetch={this.props.myFetch}
                        //0 不可选 1 单选(默认) 2 多选（暂不可用）
                        selectModal="0"
                        btnShow={false}
                        selectText={false}

                        ref={(me) => this.tree = me}

                        //节点的自定义渲染(obj{nodeInfo})=>string 
                        nodeRender={nodeData => {
                            // if (nodeData.propertyFlag === "3") {
                            //     return false;
                            // }
                            return (
                                <span>
                                    {nodeData["itemName"]}
                                </span>
                            );
                        }}

                        //右键菜单选项 
                        rightMenuOption={(rightClickNode) => {
                            return [
                                // {
                                //     label: "新增",
                                //     name: "add",//内置 add del edit  addRootNode（新增跟节点）
                                // },
                                {
                                    label: "新增子节点",
                                    name: "diy",
                                    // name: "addRoot",
                                    cb: (rightClickNode) => {
                                        //打开弹窗
                                        if (this.tableByRootObj) {
                                            let nodeInfo = rightClickNode.props.dataRef;
                                            let otherParams = {
                                                codePid: nodeInfo.codeId,
                                                pidAll: nodeInfo.pidAll,
                                                pidNameAll: nodeInfo.pidNameAll,
                                            }
                                            // console.log(otherParams)

                                            this.tableByRootObj.action(
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
                                                            label: "提交", //提交数据并且关闭右边抽屉
                                                            fetchConfig: {
                                                                apiName: "addBaseCode",
                                                                otherParams: { ...otherParams }
                                                            },
                                                            onClick: (res) => {
                                                                let _oldData = this.tree.state.data || [];
                                                                let { value,children } = this.state.treeNodeKeys;

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

                                                                //更新左边树结构 
                                                                if(!rightClickNode.props.expanded){
                                                                    rightClickNode.onExpand(); 
                                                                }
                                                                let _d = [..._oldData];
                                                                let _data = forNode(_d); 
                                                                this.tree.setState({
                                                                    data: _data
                                                                })
                                                                // //更新表格 
                                                                // if (this.table) {
                                                                //     this.table.refresh();
                                                                // }
                                                            }
                                                        }
                                                    ]
                                                }
                                            );
                                        }
                                    }
                                },
                                {
                                    label: "编辑",
                                    name: "editDiy",//内置 add del edit  addRootNode（新增跟节点）
                                    cb: (rightClickNode) => {
                                        this.tableByRootObj.action(
                                            {
                                                drawerTitle: "编辑",
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
                                                        label: "提交", //提交数据并且关闭右边抽屉
                                                        fetchConfig: {
                                                            apiName: "updateBaseCode",
                                                        },
                                                        onClick: (res) => {
                                                            let _oldData = this.tree.state.data || [];
                                                            let { value,children } = this.state.treeNodeKeys;

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
                                                            this.tree.setState({
                                                                data: _data
                                                            })

                                                            //更新右上
                                                            let _formD = QnnForm.sFormatData(res.data,this.state.info.formConfig,'set');
                                                            this.props.form.setFieldsValue({
                                                                ..._formD
                                                            })
                                                        }
                                                    }
                                                ]
                                            },
                                            rightClickNode.props.dataRef
                                        );
                                    }
                                },
                                {
                                    label: "删除",
                                    name: "del",//内置 add del edit  addRootNode（新增跟节点）
                                }
                            ];
                        }}

                        rightMenuOptionByContainer={[
                            {
                                label: "新增字典",
                                name: "diy",
                                // name: "addRoot",
                                cb: () => {
                                    //打开弹窗
                                    if (this.tableByRootObj) {
                                        this.tableByRootObj.action(
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
                                                        label: "提交", //提交数据并且关闭右边抽屉
                                                        fetchConfig: {
                                                            apiName: "addBaseCode",
                                                            otherParams: { codePid: "0" }
                                                        },
                                                        onClick: (res) => {
                                                            let _d = this.tree.state.data || [];
                                                            _d.push(res.data);
                                                            this.tree.setState({
                                                                data: _d
                                                            })
                                                        }
                                                    }
                                                ]
                                            }
                                        );
                                    }
                                }
                            }
                        ]}


                        fetchConfig={{
                            parmasKey: "codePid",  //点击节点后将节点id赋值该key上传递给后台
                            apiName: "getBaseCodeList",
                            searchApiName: "",
                            params: {
                                codePid: "0"
                            },

                            // 新增节点的接口
                            nodeAddApiName: "addBaseCode",
                            // 数据处理 新增根节时parentNode === 'root' 
                            nodeAddParamsFormat: (nodeInfo,parentNode) => {
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
                            nodeDelApiName: "batchDeleteUpdateBaseCode",
                            nodeDelParamsFormat: (nodeInfo,curNode) => {
                                return [nodeInfo]
                            },

                            nodeDelCb: () => {
                                if (this.table) {
                                    this.table.refresh();
                                }
                            }, 
                        }}
                        keys={{
                            ...treeNodeKeys
                        }}
                    />
                </div>
                <div className={s.right}>
                    <Spin spinning={loading} >
                        {
                            info ? <QnnForm
                                form={this.props.form}
                                history={this.props.history}
                                match={this.props.match}
                                fetch={this.props.myFetch}
                                {...info}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 7 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 17 }
                                    }
                                }}
                            /> : null
                        }

                        {
                            table ? <QnnTable
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                history={this.props.history}
                                match={this.props.match}
                                fetch={this.props.myFetch}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                {...table}
                                fetchConfig={//表格的ajax配置
                                    {
                                        apiName: 'getBaseCodeList',
                                        otherParams: {
                                            codeId: curClickNodeId || "no"
                                        }
                                    }
                                }
                            /> : null
                        }

                        {/* 只做新增根节点的作用 */}
                        <QnnTable
                            wrappedComponentRef={(me) => {
                                this.tableByRootObj = me;
                            }}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            {...tableByRoot}
                        />

                        {!table && !info && !loading ? <div className={s.alert}>点击左侧节点即可查看详细信息（最后一个层级没有详细信息）</div> : null}
                    </Spin>
                </div>
            </div >
        )
    }
}

export default Form.create()(index)
