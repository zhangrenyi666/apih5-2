import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Modal, Spin, Button, Tabs, Input, Row, Col, Tooltip, Tree as TreeModal } from 'antd';
import s from "./style.less";
import styles from "./styles.less";
import Tree from "../../modules/qnn-tree-v2";
import $ from "jquery";
const confirm = Modal.confirm;
const { TabPane } = Tabs;
const Search = Input.Search;
const config = {
    antd: {
        rowKey: "titleId",
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
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
const config1 = {
    antd: {
        rowKey: "fileId",
        size: 'small'
    },
    drawerConfig: {
        width: '1200px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    isShowRowSelect: false,
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
        this.k = {
            label: "label",
            value: "value",
            type: "type",
            children: "children",
            department: "departmentName"
        };
        const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
        this.renderTree = this.renderTree.bind(this);
        this.searchFn = this.searchFn.bind(this);
        this.nodeCheck = this.nodeCheck.bind(this);
        this.state = {
            departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
            visibleFZ: false,
            FZData: null,
            FZMBData: null,
            visibleQX: false,
            QXData: null,
            activeKey: '1',
            QXincludeChild: '',
            treeData: null,
            ModalData: [],
            checkedKeys: [],
            seatchText: null,
            searchLoading: false,
            paramsData: null,
            ModalTableData: null,
            ModalTableType: '',
            visibleTable: false,
            ModalTableTreeData: null
        }
    }
    handleCancelFZ = () => {
        this.setState({
            visibleFZ: false
        })
    }
    handleFZOnclick = () => {
        const { FZData, FZMBData } = this.state;
        if (FZMBData) {
            this.props.myFetch('copyAddZxZlkFolderLevel', { sourceLevelId: FZData.levelId, targetLevelId: FZMBData.levelId, permissionType: FZMBData.permissionType, includeChild: FZMBData.includeChild }).then(
                ({ success, message, data }) => {
                    if (success) {
                        Msg.success(message);
                        this.setState({
                            visibleFZ: false,
                            FZMBData: null
                        }, () => {
                            this.tree.refresh();
                        })
                    } else {
                        Msg.error(message);
                    }
                }
            );
        } else {
            Msg.error('请选择目标节点!');
        }
    }
    handleCancelQX = () => {
        this.setState({
            visibleQX: false,
            QXincludeChild:''
        })
    }
    handleQXOnclick = () => {
        confirm({
            content: '确定保存吗?',
            onOk: () => {
                const { QXData, ModalData, QXincludeChild, activeKey } = this.state;
                this.props.myFetch(
                    'confirmUpdateZxZlkFolderPermission',
                    { levelId: QXData.levelId, levelIdPath: QXData.parentIdPath, levelNamePath: QXData.parentNamePath, includeChild: QXincludeChild, permissionType: activeKey === '1' ? '0' : '1', permissionUserList: ModalData }
                ).then((obj) => {
                    if (obj.success) {
                        Msg.success(obj.message);
                        this.setState({
                            visibleQX: false,
                            QXincludeChild:''
                        })
                    } else {
                        Msg.error(obj.message);
                    }
                });
            }
        });
    }
    handleCancelTable = () => {
        this.setState({
            visibleTable: false
        })
    }
    handleTableOnclick = () => {
        const { ModalTableData, ModalTableTreeData, ModalTableType } = this.state;
        let fileList = this.ModalTable.getSelectedRows();
        if (ModalTableTreeData) {
            if (ModalTableType === 'FZ') {
                confirm({
                    content: '确定此操作吗?',
                    onOk: () => {
                        this.props.myFetch(
                            'copyZxZlkFileTitleByFolder',
                            { ...ModalTableData, levelId: ModalTableTreeData.levelId, permissionTyp: ModalTableTreeData.permissionTyp, levelIdPath: ModalTableTreeData.parentIdPath, levelNamePath: ModalTableTreeData.parentNamePath, includeChild: ModalTableTreeData.includeChild, zxErpFileList: fileList }
                        ).then((obj) => {
                            if (obj.success) {
                                Msg.success(obj.message);
                                this.setState({
                                    visibleTable: false,
                                    ModalTableTreeData: null
                                })
                                this.table.clearSelectedRows();
                                this.table.refresh();
                            } else {
                                Msg.error(obj.message);
                            }
                        });
                    }
                });
            } else {
                confirm({
                    content: '确定此操作吗?',
                    onOk: () => {
                        this.props.myFetch(
                            'cutZxZlkFileTitleByFolder',
                            { ...ModalTableData, levelId: ModalTableTreeData.levelId, permissionTyp: ModalTableTreeData.permissionTyp, levelIdPath: ModalTableTreeData.parentIdPath, levelNamePath: ModalTableTreeData.parentNamePath, includeChild: ModalTableTreeData.includeChild, zxErpFileList: fileList }
                        ).then((obj) => {
                            if (obj.success) {
                                Msg.success(obj.message);
                                this.setState({
                                    visibleTable: false,
                                    ModalTableTreeData: null
                                })
                                this.table.clearSelectedRows();
                                this.table.refresh();
                            } else {
                                Msg.error(obj.message);
                            }
                        });
                    }
                });
            }
        } else {
            Msg.error('请选择目标节点!');
        }
    }
    callback = (activeKey) => {
        this.setState({
            activeKey,
            treeData: null,
            checkedKeys: [],
            QXincludeChild:''
        }, () => {
            this.refresh();
        })
    }
    searchFn (val) {
        let { treeData, departmentId } = this.state;
        clearTimeout(window.searchTimer);
        window.searchTimer = setTimeout(() => {
            if (treeData) {
                //执行搜索
                this.setState({ searchLoading: true });
                this.props.myFetch('getSysUserCurrentTree', {
                    search: val,
                    searchType: "0",
                    topId: departmentId,
                }).then(({ data, message, success }) => {
                    if (success && data) {
                        if (Array.isArray(data)) {
                            //备份树结构数据
                            this.treeData = treeData;
                            this.setState({
                                searchLoading: false,
                                treeData: [
                                    ...this.loopDByAddKey(data)
                                ]
                            });
                        } else {
                            //备份树结构数据
                            this.treeData = treeData;
                            this.setState(
                                {

                                    treeData: data ? [
                                        ...this.loopDByAddKey([data])] : treeData,
                                    searchLoading: false
                                }
                            );
                        }
                    }
                });
            } else {
                //将树结构数据还原
                this.setState({
                    treeData: treeData
                });
            }
        }, 300);
    }
    searchChange = (e) => {
        this.setState({ seatchText: e.target.value });
    }
    refresh = (parentNodeId, parentNodeType) => {
        let { departmentId, QXData, activeKey } = this.state;
        //只是去请求子节点
        //请求子节点的时候不能设置全局loading
        let params = {};
        if (parentNodeId) {
            params.topId = departmentId;
            params.departmentParentId = parentNodeId;
            params.type = parentNodeType;
            this.setNodeLoadingByPid(parentNodeId, true)
            this.props.myFetch(
                'getSysUserCurrentTree',
                { ...params }
            ).then(({ success, message, data }) => {
                if (success) {
                    if (parentNodeId) {
                        //将数据附加到子节点就行
                        const addKeyEdData = this.loopDByAddKey(data);
                        const concatChildren = this.setChildrenDataByPid(parentNodeId, addKeyEdData);
                        this.setState({
                            treeData: concatChildren
                        });
                        this.setNodeLoadingByPid(parentNodeId, false)
                    } else {
                        this.setState({
                            treeData: this.loopDByAddKey(data)
                        });
                    }
                } else {
                    Msg.error(message)
                }
            });
        } else {
            params.topId = departmentId;
            this.props.myFetch(
                'getSysUserCurrentTree',
                { ...params }
            ).then(({ success, message, data }) => {
                if (success) {
                    if (parentNodeId) {
                        //将数据附加到子节点就行
                        const addKeyEdData = this.loopDByAddKey(data);
                        const concatChildren = this.setChildrenDataByPid(parentNodeId, addKeyEdData);
                        this.setState({
                            treeData: concatChildren
                        });
                        this.setNodeLoadingByPid(parentNodeId, false)
                    } else {
                        this.setState({
                            treeData: this.loopDByAddKey(data)
                        });
                    }
                    this.props.myFetch(
                        'getZxZlkFolderPermissionListBySetUp',
                        { levelId: QXData.levelId, permissionType: activeKey === '1' ? '0' : '1' }
                    ).then((obj) => {
                        if (obj.success) {
                            this.setState({
                                ModalData: obj.data.map((item) => {
                                    item.title = item.label;
                                    return item;
                                }),
                                QXincludeChild: obj.data?.[0]?.includeChild || '1'
                            }, () => {
                                this.setState({
                                    checkedKeys: this.state.ModalData.map((item) => {
                                        return item.value;
                                    })
                                })
                            })
                        } else {
                            Msg.error(obj.message)
                        }
                    });

                } else {
                    Msg.error(message)
                }
            });
        }
    };
    setNodeLoadingByPid = (nodeId, loadingStatus) => {
        let { children, value } = this.k;
        const { treeData } = this.state;
        let _realData = [...treeData];

        let loopFn = (treeData) => {
            return treeData.map(item => {
                let newAttr = {};
                if (item[value] === nodeId) {
                    newAttr.loading = loadingStatus;
                } else if (item[children] && item[children].length) {
                    newAttr[children] = loopFn(item[children])
                }
                return {
                    ...item,
                    ...newAttr
                };
            });
        }

        this.setState({
            treeData: loopFn(_realData)
        });
    }
    loopDByAddKey = (data = []) => {
        if (!data) { return data };
        data = Array.isArray(data) ? data : [data];
        let { value, children } = this.k;
        return data.map(item => {
            if (item) {
                item._key = item[value];
                if (item[children] && item[children].length) {
                    item[children] = this.loopDByAddKey(item[children])
                }
            }
            return item;
        });
    }
    //传入父节点id 和父节点的子节点数据返回连接好的数据
    setChildrenDataByPid = (pid, childrenData = []) => {
        let { children, value } = this.k;
        const { treeData } = this.state;
        let _realData = [...treeData];

        let loopFn = (treeData) => {
            return treeData.map(item => {
                let newAttr = {};
                if (item[value] === pid) {
                    newAttr[children] = childrenData;
                } else if (item[children] && item[children].length) {
                    newAttr[children] = loopFn(item[children])
                }
                return {
                    ...item,
                    ...newAttr
                };
            });
        }

        return loopFn(_realData);
    }
    renderTree (data) {
        const { checkedKeys } = this.state;
        if (data) {
            return (
                <TreeModal
                    checkable
                    blockNode
                    onCheck={(selected, e) => {
                        this.nodeCheck(selected, e);
                    }}
                    checkedKeys={checkedKeys}
                    treeData={this.loopD((Array.isArray(data) ? [...data] : [{ ...data }]))}
                    height={300}
                    className={styles.tree}
                    ref={() => {
                        $('.ant-tree-list').on('scroll', () => {
                            this.setCheckEdStyleAndText()
                        })
                    }}
                    onExpand={(expandedKeys, { expanded, node }) => {
                        //需要去请求子节点数据
                        //请求数据渲染树
                        if (expanded) {
                            this.refresh(node.value, node.type);
                        }
                    }}
                >
                </TreeModal>
            );
        }
    }
    //节点被点击时
    nodeCheck (select, nodeInfo) {
        this.setState({
            checkedKeys: select
        }, () => {
            let treeDataQX = [...this.state.ModalData];
            if (nodeInfo.checked) {
                treeDataQX.push({
                    label: nodeInfo.node.label,
                    type: nodeInfo.node.type,
                    value: nodeInfo.node.value,
                    valuePid: nodeInfo.node.valuePid,
                    showDepartmentName: nodeInfo.node.showDepartmentName,
                    title: nodeInfo.node.label,
                    userDeptId: nodeInfo.node.userDeptId
                });
            } else {
                for (let i = 0; i < treeDataQX.length; i++) {
                    if (treeDataQX[i].value === nodeInfo.node.value) {
                        treeDataQX.splice(i, 1);
                        return;
                    }
                }
            }
            this.props.myFetch(
                'getSysUserTreeToListByDept',
                treeDataQX
            ).then((obj) => {
                if (obj.success) {
                    this.setState({
                        ModalData: obj.data
                    })
                } else {
                    Msg.error(obj.message)
                }
            });
        })
    }
    //删除选择列表里面的数据 （弹层里面的）
    selectedListDel (delValue) {
        //传入id
        let { value } = this.k;
        let { ModalData } = this.state; //, defaultCheckedKeys

        for (let i = 0; i < ModalData.length; i++) {
            if (ModalData[i][value] === delValue) {
                this.removeCheckEdStyleAndText(ModalData[i])
                //删除该人员或者部门
                ModalData.splice(i, 1);
                this.setState({
                    ModalData
                }, () => {
                    this.setState({
                        checkedKeys: this.state.ModalData.map((item) => {
                            return item.value;
                        })
                    })
                });
                return;
            }
        }
    }
    removeCheckEdStyleAndText = (item) => {
        let { value } = this.k;
        let className = `.${item[value]}_gou`;
        $(className).text('');
    }
    loopD = (data = []) => {
        let { children, value } = this.k;
        let _realData = [...data];
        return _realData.map(item => {
            let _item = { ...item };
            _item.key = _item[value];
            _item.title = this.renderTreeNodeTitle(_item);
            _item.children = _item[children];
            _item.isLeaf = item.type === "2";
            if (_item.children && _item.children.length) {
                _item.children = this.loopD(_item.children)
            }
            return _item;
        });
    }
    setCheckEdStyleAndText = () => {
        const { ModalData } = this.state;
        let { value } = this.k;
        ModalData.forEach((item) => {
            let className = `.${item[value]}_gou`;
            $(className).text('√');
        })
    }
    //渲染子节点 传入的data一定是个数组
    renderTreeNodeTitle (item) {
        let { label, value, type, department } = this.k;
        let { ModalData, collect = [], useCollect } = this.state; //,selectType
        //selectType === '0' 不用管
        //selectType === '1' 加上pullperson-blueImg类名将部门节点变成蓝色
        //selectType === '2' 加上pullperson-blueImg类名将人员节点变成蓝色
        //搜索的列表数据每一项都带有search属性值为true  强制没有子节点 因为搜索结果是个列表

        // item.showDepartmentName = "索的列表数据每一项都带有search属性值为true  强制没有子节点 因为搜索结果是个列表 "

        let title = null;
        //判断这条数据是否被选中
        let _checked = false;
        for (let i = 0; i < ModalData.length; i++) {
            if (item[value] === ModalData[i][value]) {
                _checked = true;
            }
        }

        //是否被收藏
        //是否是收藏人员
        let isCollect = false;
        collect.map((collectItem) => {
            let _val = collectItem && collectItem[value];
            if (_val === item[value] && !isCollect) {
                isCollect = true;
            }
            return item;
        });

        if (item[type] === "2") {
            //人员节点
            //没有子节点
            title = <span className={`${styles.pullPersonPerNode} ${styles.myNode}  ${styles.pullpersonTitleCon} ${item[value]}_sc_title`}>
                <div className={styles.label}>
                    {
                        item.showDepartmentName ? (<Tooltip title={item.showDepartmentName}>
                            <span
                                className={styles.labelTexts}
                            >
                                <div>{item[label]}</div>
                                <div>{item[department]}</div>
                            </span>
                        </Tooltip>) : (
                            <span
                                className={styles.labelTexts}
                            >
                                <div>{item[label]}</div>
                                <div>{item[department]}</div>
                            </span>
                        )
                    }
                </div>

                {useCollect ? (
                    <span
                        className={`${styles.pullpersonTitleConCollect} ${isCollect ? styles.blue : ""}  ${item[value]}_sc`}
                        onClick={e => {
                            e.stopPropagation();
                            if (isCollect) {
                                this.collectDel(item);
                            } else {
                                this.collectAdd(item);
                            }
                        }}
                    >
                        ♡
                    </span>
                ) : null}

                <span
                    className={`${styles.pullpersonCheckedGou}  ${item[value]}_gou`}
                    ref={item[value]}
                >
                    {_checked ? "√" : null}
                </span>
            </span>

        } else {
            //部门节点或者根节点
            //有子节点

            title = <span className={`${styles.pullpersonBlueImg} ${styles.myNode}  ${styles.pullpersonTitleCon}`} >
                <div className={styles.label}>
                    {
                        item.showDepartmentName ? (<Tooltip title={item.showDepartmentName}>
                            <span>
                                <div> <Spin size="small" spinning={item.loading || false}> {item[label]}</Spin></div>
                            </span>
                        </Tooltip>) : (
                            <span>
                                <div><Spin size="small" spinning={item.loading || false}> {item[label]}</Spin></div>
                            </span>
                        )
                    }
                </div>

                <span
                    className={`${styles.pullpersonCheckedGou} ${item[value]}_gou`}
                    ref={item[value]}
                >
                    {_checked ? "√" : null}
                </span>
            </span>
        }
        return title;
    }
    render () {
        const { visibleFZ, visibleQX, activeKey, seatchText, searchLoading, treeData, ModalData, QXincludeChild, paramsData, ModalTableData, visibleTable } = this.state;
        const { userKey } = this.props.loginAndLogoutInfo.loginInfo.userInfo;
        let { label, value, type } = this.k;
        this.TreeDoms = treeData ? this.renderTree(treeData) : null;
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
                                function moveFn (e) {
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
                        nodeRender={nodeData => {
                            return (
                                <span>
                                    {`${nodeData["levelName"]} ${(nodeData.permissionType === '-1' || nodeData.parentId === '0') ? '【仅显示】' : nodeData.permissionType === '0' ? '【浏览权限】' : '【管理权限】'}`}
                                </span>
                            );
                        }}
                        ref={(me) => this.tree = me}
                        rightMenuOption={(rightClickNode) => {
                            if (rightClickNode.parentId === '0' && (rightClickNode.permissionType === '1' || userKey === 'admin')) {
                                return [
                                    {
                                        label: "添加子资料库",
                                        name: "add"
                                    }
                                ]
                            } else if (rightClickNode.permissionType === '1' || userKey === 'admin') {
                                if (rightClickNode.includeChild === '1' || userKey === 'admin') {
                                    return [
                                        {
                                            label: "添加子资料库",
                                            name: "add"
                                        },
                                        {
                                            label: "重命名",
                                            name: "edit"
                                        },
                                        {
                                            label: "权限设置",
                                            name: "qxszdiy",
                                            // name: "addRoot",
                                            cb: (QXData) => {
                                                this.setState({
                                                    QXData,
                                                    visibleQX: true,
                                                    checkedKeys: [],
                                                    treeData: null,
                                                    activeKey: '1'
                                                }, () => {
                                                    this.refresh();
                                                })
                                            }
                                        },
                                        {
                                            label: "复制目录结构",
                                            name: "fzdiy",
                                            // name: "addRoot",
                                            cb: (FZData) => {
                                                this.setState({
                                                    FZData,
                                                    visibleFZ: true
                                                })
                                            }
                                        },
                                        {
                                            label: "删除资料夹",
                                            name: "del"
                                        }
                                    ]
                                } else {
                                    return [
                                        {
                                            label: "添加子资料库",
                                            name: "add"
                                        },
                                        {
                                            label: "重命名",
                                            name: "edit"
                                        },
                                        {
                                            label: "删除资料夹",
                                            name: "del"
                                        }
                                    ]
                                }
                            }

                        }}
                        //拖动配置
                        draggable={true}  //启用拖动功能
                        onDrop={(obj) => {
                            const { dragData, afterNode, beforeNode, targetNode, parentNode, restoreTreeData } = obj;
                            let params = {
                                permissionType: dragData.permissionType,
                                includeChild: parentNode.includeChild,
                                newParentId: parentNode.levelId,
                                toMoveId: targetNode,
                                newAfterId: afterNode,
                                newBeforeId: beforeNode
                            };
                            this.props.myFetch("moveZxZlkFolderLevel", params).then(({ data, success, message }) => {
                                if (success) {
                                    Msg.success(message)
                                } else {
                                    Msg.error(message);
                                    restoreTreeData();
                                }
                            });
                        }}
                        nodeClick={(node) => {
                            this.setState({
                                paramsData: null
                            }, () => {
                                this.setState({
                                    paramsData: node.props.dataRef
                                }, () => {
                                    if (this.table) {
                                        this.table.clearSelectedRows();
                                    }
                                })
                            })
                        }}
                        //键值配置 默认{value:value,label:label,children:children}
                        fetchConfig={{
                            parmasKey: {
                                parentId: "0",
                                includeChild: '-1',
                                permissionTypeParam: '-1'
                            },  //点击节点后将节点id赋值该key上传递给后台
                            apiName: 'getZxZlkFolderLevelListByPermission',
                            searchApiName: "",
                            params: {
                                parentId: "0",
                                includeChild: '-1',
                                permissionTypeParam: '-1'
                            },
                            // 新增节点的接口
                            nodeAddApiName: "addZxZlkFolderLevelByChild",
                            // 数据处理 新增根节时parentNode === 'root' 
                            nodeAddParamsFormat: (nodeInfo, parentNode) => {
                                let params = {
                                    levelName: nodeInfo?.levelName,
                                    includeChild: parentNode?.includeChild,
                                    permissionType: parentNode?.permissionType,
                                    isLeaf: parentNode?.isLeaf,
                                    parentId: parentNode?.levelId,
                                    parentIdPath: parentNode?.parentIdPath,
                                    parentNamePath: parentNode?.parentNamePath,
                                }
                                return params;
                            },
                            //编辑
                            nodeEditApiName: "updateZxZlkFolderLevelByRename",
                            //删除
                            nodeDelCheckApiName: "checkDeleteZxZlkFolderLevel",
                            nodeDelApiName: "cascadeDeleteZxZlkFolderLevel",
                        }}
                        keys={{
                            label: "levelName",
                            value: "levelId",
                            children: "children"
                        }}
                    />
                </div>
                {
                    paramsData && (paramsData.permissionType === '0' || paramsData.permissionType === '1') ? <div className={s.rootr}>
                        <div className={s.rootrTop}>
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                antd={{
                                    rowKey: "id",
                                    size: 'small'
                                }}
                                fetchConfig={{
                                    apiName: 'getZxZlkFileTitleListByFolder',
                                    otherParams: {
                                        permissionType: paramsData.permissionType,
                                        includeChild: paramsData.includeChild,
                                        levelId: paramsData.levelId
                                    }
                                }}
                                actionBtns={(paramsData.permissionType === '1' && paramsData.parentId !== '0') ? [
                                    {
                                        name: 'add',//内置add del
                                        icon: 'plus',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '新增',
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                field: "cancel",
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',
                                                type: 'primary',
                                                label: '保存',
                                                fetchConfig: {
                                                    apiName: 'addZxZlkFileTitleByFolder'
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'edit',
                                        icon: 'edit',
                                        type: 'primary',
                                        label: '修改',
                                        formBtns: [
                                            {
                                                name: 'cancel',
                                                type: 'dashed',
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',
                                                type: 'primary',
                                                label: '保存',
                                                fetchConfig: {
                                                    apiName: 'updateZxZlkFileTitleByFolder'
                                                },
                                            }
                                        ]
                                    },
                                    {
                                        name: 'copy',
                                        type: 'primary',
                                        label: '复制',
                                        disabled: (obj) => {
                                            let data = obj.btnCallbackFn.getSelectedRows();
                                            if (data.length === 1) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            this.setState({
                                                ModalTableType: 'FZ',
                                                ModalTableData: obj.selectedRows[0],
                                                visibleTable: true
                                            })
                                        }
                                    },
                                    {
                                        name: 'copy',
                                        type: 'primary',
                                        label: '剪切',
                                        disabled: (obj) => {
                                            let data = obj.btnCallbackFn.getSelectedRows();
                                            if (data.length === 1) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            this.setState({
                                                ModalTableType: 'JQ',
                                                ModalTableData: obj.selectedRows[0],
                                                visibleTable: true
                                            })
                                        }
                                    },
                                    {
                                        name: 'downLoad',
                                        type: 'primary',
                                        label: '下载',
                                        disabled: (obj) => {
                                            let data = obj.btnCallbackFn.getSelectedRows();
                                            if (data.length === 1) {
                                                return false;
                                            } else {
                                                return true;
                                            }
                                        },
                                        onClick: (obj) => {
                                            const { download } = obj.btnCallbackFn;
                                            const { domain, apis } = this.props.myPublic;
                                            const { access_token } = this.props.loginAndLogoutInfo.loginInfo;
                                            confirm({
                                                content: '确定下载吗?',
                                                onOk: () => {
                                                    download(`${domain}${apis["downloadZxZlkFileTitleByDoubleClick"]}`, ...obj.selectedRows, { Authorization: `Bearer ${access_token}` });
                                                }
                                            });
                                        }
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {
                                            apiName: "deleteZxZlkFileTitleByFolder"
                                        }
                                    }
                                ] : []}
                                {...config}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'titleId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'permissionType',
                                            type: 'string',
                                            initialValue: paramsData.permissionType,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'includeChild',
                                            type: 'string',
                                            initialValue: paramsData.includeChild,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'levelId',
                                            type: 'string',
                                            initialValue: paramsData.levelId,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'levelIdPath',
                                            type: 'string',
                                            initialValue: paramsData.parentIdPath,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'levelNamePath',
                                            type: 'string',
                                            initialValue: paramsData.parentNamePath,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            title: '序号',
                                            dataIndex: 'index',
                                            key: 'index',
                                            fixed: "left",
                                            width: 50,
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '文件标题',
                                            dataIndex: 'titleName',
                                            key: 'titleName',
                                            fixed: "left",
                                            filter: true,
                                            width: 600,
                                            onClick: (obj) => {
                                                this.table1.setTableData(obj.rowData.zxErpFileList);
                                            }
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '归档时间',
                                            dataIndex: 'filingTime',
                                            key: 'filingTime',
                                            width: 100,
                                            format: 'YYYY-MM-DD HH:mm:ss'
                                        },
                                        form: {
                                            type: 'date',
                                            hide: true,
                                            initialValue: 0,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '归档人',
                                            dataIndex: 'filingUserName',
                                            key: 'filingUserName',
                                            width: 100
                                        },
                                        form: {
                                            type: 'string',
                                            hide: true,
                                            initialValue: '',
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            label: '附件',
                                            field: 'zxErpFileList',
                                            type: 'files',
                                            // required: true,
                                            fetchConfig: {
                                                apiName: 'upload'
                                            }
                                        }
                                    }
                                ]}
                            />
                        </div>
                        <div className={s.rootrBottom}>
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table1 = me;
                                }}
                                {...config1}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'fileId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            title: '序号',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '附件名称',
                                            dataIndex: 'name',
                                            key: 'name',
                                            width: 600,
                                            onClick: (obj) => {
                                                window.open(obj.rowData.url);
                                            }
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            title: '附件大小',
                                            dataIndex: 'fileSize',
                                            key: 'fileSize',
                                            width: 100
                                        }
                                    }
                                ]}
                            />
                        </div>
                    </div> : paramsData && paramsData.permissionType === '-1' ? <div className={s.alert}>
                        无权限查看数据
                    </div> : <div className={s.alert}>
                        点击左侧节点即可查看详细信息
                    </div>
                }
                {visibleFZ ? <Modal
                    width='600px'
                    style={{ top: '0' }}
                    title="选择目标文件夹"
                    visible={visibleFZ}
                    footer={
                        <div>
                            <Button type="primary" onClick={this.handleFZOnclick}>确定</Button>
                            <Button type="dashed" onClick={this.handleCancelFZ}>取消</Button>
                        </div>
                    }
                    onCancel={this.handleCancelFZ}
                    bodyStyle={{ width: '600px', height: '50vh', overflow: 'auto' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
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
                        nodeRender={nodeData => {
                            return (
                                <span>
                                    {`${nodeData["levelName"]} ${nodeData.permissionType === '-1' ? '【仅显示】' : nodeData.permissionType === '0' ? '【浏览权限】' : '【管理权限】'}`}
                                </span>
                            );
                        }}
                        rightMenuOption={[]}
                        nodeClick={(node) => {
                            this.setState({
                                FZMBData: node.props.dataRef
                            })
                        }}
                        //键值配置 默认{value:value,label:label,children:children}
                        fetchConfig={{
                            parmasKey: {
                                parentId: "0",
                                includeChild: '-1',
                                permissionTypeParam: '-1'
                            },  //点击节点后将节点id赋值该key上传递给后台
                            apiName: 'getZxZlkFolderLevelListByPermission',
                            searchApiName: "",
                            params: {
                                parentId: "0",
                                includeChild: '-1',
                                permissionTypeParam: '-1'
                            }
                        }}
                        keys={{
                            label: "levelName",
                            value: "levelId",
                            children: "children"
                        }}
                    />
                </Modal> : null}
                {visibleQX ? <Modal
                    width='600px'
                    style={{ top: '0' }}
                    title="权限设置"
                    visible={visibleQX}
                    footer={
                        <div>
                            <Button type="primary" onClick={this.handleQXOnclick}>确定</Button>
                            <Button type="dashed" onClick={this.handleCancelQX}>取消</Button>
                        </div>
                    }
                    onCancel={this.handleCancelQX}
                    bodyStyle={{ width: '600px', height: '50vh', overflow: 'auto' }}
                    centered={true}
                    wrapClassName={'modalQX'}
                >
                    <div>
                        <Tabs activeKey={activeKey} onChange={this.callback}>
                            <TabPane tab="浏览权限" key="1">
                                <div style={{ height: '32px', overflow: 'hidden' }}>
                                    {QXincludeChild ? <QnnForm
                                        fetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        formItemLayout={{
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }}
                                        formConfig={[
                                            {
                                                label: '是否包含所有子目录',
                                                field: 'includeChild',
                                                type: 'radio',
                                                optionData: [  //可为function (props)=>array
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "否",
                                                        value: "0"
                                                    }
                                                ],
                                                initialValue: QXincludeChild,
                                                onChange: (val) => {
                                                    this.setState({
                                                        QXincludeChild: val
                                                    })
                                                }
                                            }
                                        ]}
                                    /> : null}
                                </div>
                                <div className={styles.pullpersonmodalBody}>
                                    <div className={styles.treeContainer}>
                                        <div
                                            className={
                                                styles.pullPersonSearchContainer
                                            }
                                        >
                                            <Search
                                                style={{ marginBottom: 8 }}
                                                placeholder={
                                                    "请输入搜索条件后回车..."
                                                }
                                                onSearch={this.searchFn}
                                                onChange={this.searchChange}
                                                value={seatchText}
                                            />
                                        </div>
                                        <div className={styles.treeDom}>
                                            <Spin spinning={searchLoading}>
                                                {treeData ? this.TreeDoms : '暂无数据'}

                                                {Array.isArray(treeData) && !treeData.length ? (
                                                    <center> 暂无数据 </center>
                                                ) : null}
                                            </Spin>
                                        </div>
                                    </div>
                                    <div className={styles.selectedContainer}>
                                        <span
                                            className={
                                                styles.selectedContainerTitle
                                            }
                                        >
                                            已具有权限的用户
                                            </span>
                                        {ModalData.map((item, i) => {
                                            return (
                                                <div
                                                    key={i}
                                                    className={`${item[type] === "2"
                                                        ? styles.persion
                                                        : styles.folder
                                                        }  ${styles.selectList}`}
                                                >
                                                    <Row
                                                        type="flex"
                                                        align="middle"
                                                        justify="space-between"
                                                    >
                                                        <Col style={{ display: "flex", justify: "space-between" }}>{item[label]}</Col>
                                                        <Col>
                                                            <span
                                                                onClick={this.selectedListDel.bind(
                                                                    this,
                                                                    item[value]
                                                                )}
                                                                className={
                                                                    styles.selectedListDel
                                                                }
                                                            >
                                                                ×
                                                            </span>
                                                        </Col>
                                                    </Row>
                                                </div>
                                            );
                                        })}
                                    </div>
                                </div>
                            </TabPane>
                            <TabPane tab="管理权限" key="2">
                                <div style={{ height: '32px', overflow: 'hidden' }}>
                                    {QXincludeChild ? <QnnForm
                                        fetch={this.props.myFetch}
                                        upload={this.props.myUpload}
                                        headers={{
                                            token: this.props.loginAndLogoutInfo.loginInfo.token
                                        }}
                                        formItemLayout={{
                                            labelCol: {
                                                xs: { span: 6 },
                                                sm: { span: 6 }
                                            },
                                            wrapperCol: {
                                                xs: { span: 18 },
                                                sm: { span: 18 }
                                            }
                                        }}
                                        formConfig={[
                                            {
                                                label: '是否包含所有子目录',
                                                field: 'includeChild',
                                                type: 'radio',
                                                optionData: [  //可为function (props)=>array
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    },
                                                    {
                                                        label: "否",
                                                        value: "0"
                                                    }
                                                ],
                                                initialValue: QXincludeChild,
                                                onChange: (val) => {
                                                    this.setState({
                                                        QXincludeChild: val
                                                    })
                                                }
                                            }
                                        ]}
                                    /> : null}
                                </div>
                                <div className={styles.pullpersonmodalBody}>
                                    <div className={styles.treeContainer}>
                                        <div
                                            className={
                                                styles.pullPersonSearchContainer
                                            }
                                        >
                                            <Search
                                                style={{ marginBottom: 8 }}
                                                placeholder={
                                                    "请输入搜索条件后回车..."
                                                }
                                                onSearch={this.searchFn}
                                                onChange={this.searchChange}
                                                value={seatchText}
                                            />
                                        </div>
                                        <div className={styles.treeDom}>
                                            <Spin spinning={searchLoading}>
                                                {treeData ? this.TreeDoms : '暂无数据'}

                                                {Array.isArray(treeData) && !treeData.length ? (
                                                    <center> 暂无数据 </center>
                                                ) : null}
                                            </Spin>
                                        </div>
                                    </div>
                                    <div className={styles.selectedContainer}>
                                        <span
                                            className={
                                                styles.selectedContainerTitle
                                            }
                                        >
                                            已具有权限的用户
                                            </span>
                                        {ModalData.map((item, i) => {
                                            return (
                                                <div
                                                    key={i}
                                                    className={`${item[type] === "2"
                                                        ? styles.persion
                                                        : styles.folder
                                                        }  ${styles.selectList}`}
                                                >
                                                    <Row
                                                        type="flex"
                                                        align="middle"
                                                        justify="space-between"
                                                    >
                                                        <Col style={{ display: "flex", justify: "space-between" }}>{item[label]}</Col>
                                                        <Col>
                                                            <span
                                                                onClick={this.selectedListDel.bind(
                                                                    this,
                                                                    item[value]
                                                                )}
                                                                className={
                                                                    styles.selectedListDel
                                                                }
                                                            >
                                                                ×
                                                            </span>
                                                        </Col>
                                                    </Row>
                                                </div>
                                            );
                                        })}
                                    </div>
                                </div>
                            </TabPane>
                        </Tabs>
                    </div>
                </Modal> : null}
                {visibleTable ? <Modal
                    width='800px'
                    style={{ top: '0' }}
                    title="设置要索对应关系"
                    visible={visibleTable}
                    footer={
                        <div>
                            <Button type="primary" onClick={this.handleTableOnclick}>确定</Button>
                            <Button type="dashed" onClick={this.handleCancelTable}>取消</Button>
                        </div>
                    }
                    onCancel={this.handleCancelTable}
                    bodyStyle={{ width: '800px', height: '80vh', overflow: 'auto' }}
                    centered={true}
                    wrapClassName={'modals'}
                >
                    <div style={{ height: '100%', overflow: 'hidden' }}>
                        <div style={{ height: '50%', overflow: 'auto' }}>
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
                                nodeRender={nodeData => {
                                    return (
                                        <span>
                                            {`${nodeData["levelName"]} ${nodeData.permissionType === '-1' ? '【仅显示】' : nodeData.permissionType === '0' ? '【浏览权限】' : '【管理权限】'}`}
                                        </span>
                                    );
                                }}
                                rightMenuOption={[]}
                                nodeClick={(node) => {
                                    this.setState({
                                        ModalTableTreeData: node.props.dataRef
                                    })
                                }}
                                //键值配置 默认{value:value,label:label,children:children}
                                fetchConfig={{
                                    parmasKey: {
                                        parentId: "0",
                                        includeChild: '-1',
                                        permissionTypeParam: '-1'
                                    },  //点击节点后将节点id赋值该key上传递给后台
                                    apiName: 'getZxZlkFolderLevelListByPermission',
                                    searchApiName: "",
                                    params: {
                                        parentId: "0",
                                        includeChild: '-1',
                                        permissionTypeParam: '-1'
                                    }
                                }}
                                keys={{
                                    label: "levelName",
                                    value: "levelId",
                                    children: "children"
                                }}
                            />
                        </div>
                        <div style={{ height: '50%' }}>
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                data={ModalTableData?.zxErpFileList}
                                antd={{
                                    rowKey: "fileId",
                                    size: 'small'
                                }}
                                wrappedComponentRef={(me) => {
                                    let selectedData = ModalTableData?.zxErpFileList.map((item) => {
                                        return {
                                            fileId: item.fileId
                                        }
                                    });
                                    me.setSelectedRows(selectedData);
                                    this.ModalTable = me;
                                }}
                                paginationConfig={false}
                                pageSize={99999999}
                                isShowRowSelect={true}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'fileId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            title: '序号',
                                            dataIndex: 'index',
                                            key: 'index',
                                            width: 50,
                                            render: (data, rowData, index) => {
                                                return index + 1;
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '附件名称',
                                            dataIndex: 'name',
                                            key: 'name',
                                            width: 400,
                                            onClick: (obj) => {
                                                window.open(obj.rowData.url);
                                            }
                                        },
                                        form: {
                                            type: 'string',
                                            required: true,
                                            placeholder: '请输入'
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            title: '附件大小',
                                            dataIndex: 'fileSize',
                                            key: 'fileSize',
                                            width: 100
                                        }
                                    }
                                ]}
                            />
                        </div>
                    </div>
                </Modal> : null}
            </div>
        );
    }
}

export default index;