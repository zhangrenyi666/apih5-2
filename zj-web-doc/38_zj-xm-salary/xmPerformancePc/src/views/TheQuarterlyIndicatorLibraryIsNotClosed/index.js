import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Tabs } from 'antd';
import s from "./style.less";
import Tree from "../modules/tree";
const { TabPane } = Tabs;
const config = {
    antd: {
        rowKey: function (row) {
            return row.libraryId
        },
        size: 'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {
        position: 'bottom'
    },
    searchBtnsStyle: 'inline'
}
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rowData: null,
            treeData: [],
            activeKey: '1'
        }
    }
    componentDidMount() {
        this.props.myFetch('getZjXmJxQuarterlyAssessmentDeptList', { isClosed: '0' }).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    treeData: [
                        {
                            deptId: '888',
                            deptName: '公司部门',
                            childrenList: data
                        }
                    ]
                })
            } else {
                Msg.error(message);
            }
        });
    }
    tabsCallback = (activeKey) => {
        this.setState({
            activeKey
        })
    }
    render() {
        const { rowData, activeKey, treeData } = this.state;
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
                    {treeData.length ? <div className={s.rootlTree}><Tree
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
                                    {nodeData["deptName"]}
                                </span>
                            );
                        }}
                        defaultExpandedKeys={['888']}
                        rightMenuOption={[]}
                        nodeClick={(node) => {
                            this.setState({
                                rowData: null,
                                activeKey:'1'
                            }, () => {
                                if (node.deptId !== '888') {
                                    this.setState({
                                        rowData: node
                                    })
                                }
                            });
                        }}
                        //ajax请求配置
                        data={treeData}
                        //键值配置 默认{value:value,label:label,children:children}
                        keys={{
                            label: "deptName",
                            value: "deptId",
                            children: "childrenList"
                        }}
                    /></div> : null}
                </div>
                <div className={s.rootr}>
                    {rowData && rowData.deptId ? <Tabs activeKey={activeKey} onChange={this.tabsCallback}>
                        {
                            rowData.projectStatus === '0' || rowData.projectStatus === '1' ? <TabPane tab="在建" key="1">
                                <QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.table = me;
                                    }}
                                    fetchConfig={{
                                        apiName: 'getZjXmJxQuarterlyIndexLibraryList',
                                        otherParams: {
                                            deptId: rowData.deptId,
                                            projectStatus: '1'
                                        }
                                    }}
                                    actionBtns={[
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
                                                        apiName: 'addZjXmJxQuarterlyIndexLibrary',
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            name: 'edit',
                                            icon: 'edit',
                                            type: 'primary',
                                            label: '修改',
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
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
                                                        apiName: 'updateZjXmJxQuarterlyIndexLibrary',
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
                                                apiName: 'batchDeleteUpdateZjXmJxQuarterlyIndexLibrary',
                                            },
                                        }
                                    ]}
                                    {...config}
                                    formConfig={[
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'libraryId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'isClosed',
                                                type: 'string',
                                                initialValue: rowData.isClosed,
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'deptId',
                                                type: 'string',
                                                initialValue: rowData.deptId,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectStatus',
                                                type: 'string',
                                                initialValue: '1',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectStatusName',
                                                type: 'string',
                                                initialValue: rowData.projectStatusName,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectType',
                                                type: 'string',
                                                initialValue: rowData.projectType,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectTypeName',
                                                type: 'string',
                                                initialValue: rowData.projectTypeName,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'deptName',
                                                type: 'string',
                                                initialValue: rowData.deptName,
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '考核标题',
                                                dataIndex: 'libraryTitle',
                                                key: 'libraryTitle',
                                                fixed: 'left',
                                                filter: true,
                                                onClick: 'detail'
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '考核内容',
                                                dataIndex: 'libraryContent',
                                                key: 'libraryContent',
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '考核责任人',
                                                dataIndex: 'personLiableName',
                                                key: 'personLiableName'
                                            },
                                            form: {
                                                field: 'personLiableArray',
                                                type: 'treeSelect',
                                                treeSelectOption: {
                                                    selectType: "2",
                                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                                        apiName: 'getSysDepartmentUserAllTree'
                                                    },
                                                    search: true,
                                                    maxNumber: 1,
                                                    searchPlaceholder: '姓名、账号、电话',
                                                    // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                                    searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                                    searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                                },
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否固定分数',
                                                dataIndex: 'isFixedScore',
                                                key: 'isFixedScore',
                                                width: 120,
                                                render: (data) => {
                                                    if (data === '0') {
                                                        return '否';
                                                    }
                                                    return '是';
                                                }
                                            },
                                            form: {
                                                type: 'radio',
                                                required: true,
                                                optionData: [  //可为function (props)=>array
                                                    {
                                                        label: "否",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '分数',
                                                field: 'score',
                                                type: 'number',
                                                dependencies: ["isFixedScore"],
                                                hide: function (obj) {
                                                    if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                required: true,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '加/减分下限',
                                                field: 'lowerLimitScore',
                                                type: 'number',
                                                required: true,
                                                dependencies: ["isFixedScore"],
                                                hide: function (obj) {
                                                    if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '加/减分上限',
                                                field: 'upperLimitScore',
                                                type: 'number',
                                                required: true,
                                                dependencies: ["isFixedScore"],
                                                hide: function (obj) {
                                                    if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                key: 'remarks'
                                            },
                                            form: {
                                                type: 'textarea',
                                                placeholder: '请输入'
                                            }
                                        }
                                    ]}
                                />
                            </TabPane> : null
                        }
                        {
                            rowData.projectStatus === '0' || rowData.projectStatus === '2' ? <TabPane tab="主体完工" key={rowData.projectStatus === '2' ? "1" : '2'}>
                                <QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.table = me;
                                    }}
                                    fetchConfig={{
                                        apiName: 'getZjXmJxQuarterlyIndexLibraryList',
                                        otherParams: {
                                            deptId: rowData.deptId,
                                            projectStatus: '2'
                                        }
                                    }}
                                    actionBtns={[
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
                                                        apiName: 'addZjXmJxQuarterlyIndexLibrary',
                                                    }
                                                }
                                            ]
                                        },
                                        {
                                            name: 'edit',
                                            icon: 'edit',
                                            type: 'primary',
                                            label: '修改',
                                            onClick: (obj) => {
                                                obj.btnCallbackFn.clearSelectedRows();
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
                                                        apiName: 'updateZjXmJxQuarterlyIndexLibrary',
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
                                                apiName: 'batchDeleteUpdateZjXmJxQuarterlyIndexLibrary',
                                            },
                                        }
                                    ]}
                                    {...config}
                                    formConfig={[
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'libraryId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'isClosed',
                                                type: 'string',
                                                initialValue: rowData.isClosed,
                                                hide: true
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'deptId',
                                                type: 'string',
                                                initialValue: rowData.deptId,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectStatus',
                                                type: 'string',
                                                initialValue: '2',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectStatusName',
                                                type: 'string',
                                                initialValue: rowData.projectStatusName,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectType',
                                                type: 'string',
                                                initialValue: rowData.projectType,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'projectTypeName',
                                                type: 'string',
                                                initialValue: rowData.projectTypeName,
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'deptName',
                                                type: 'string',
                                                initialValue: rowData.deptName,
                                                hide: true,
                                            }
                                        },
                                        {
                                            table: {
                                                title: '考核标题',
                                                dataIndex: 'libraryTitle',
                                                key: 'libraryTitle',
                                                fixed: 'left',
                                                filter: true,
                                                onClick: 'detail'
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '考核内容',
                                                dataIndex: 'libraryContent',
                                                key: 'libraryContent',
                                            },
                                            form: {
                                                type: 'string',
                                                placeholder: '请输入',
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '考核责任人',
                                                dataIndex: 'personLiableName',
                                                key: 'personLiableName'
                                            },
                                            form: {
                                                field: 'personLiableArray',
                                                type: 'treeSelect',
                                                treeSelectOption: {
                                                    selectType: "2",
                                                    fetchConfig: {//配置后将会去请求下拉选项数据
                                                        apiName: 'getSysDepartmentUserAllTree'
                                                    },
                                                    search: true,
                                                    maxNumber: 1,
                                                    searchPlaceholder: '姓名、账号、电话',
                                                    // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                                    searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                                    searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                                },
                                                required: true
                                            }
                                        },
                                        {
                                            table: {
                                                title: '是否固定分数',
                                                dataIndex: 'isFixedScore',
                                                key: 'isFixedScore',
                                                width: 120,
                                                render: (data) => {
                                                    if (data === '0') {
                                                        return '否';
                                                    }
                                                    return '是';
                                                }
                                            },
                                            form: {
                                                type: 'radio',
                                                required: true,
                                                optionData: [  //可为function (props)=>array
                                                    {
                                                        label: "否",
                                                        value: "0"
                                                    },
                                                    {
                                                        label: "是",
                                                        value: "1"
                                                    }
                                                ]
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '分数',
                                                field: 'score',
                                                type: 'number',
                                                dependencies: ["isFixedScore"],
                                                hide: function (obj) {
                                                    if (obj.form.getFieldValue("isFixedScore") === '1') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                required: true,
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '加/减分下限',
                                                field: 'lowerLimitScore',
                                                type: 'number',
                                                required: true,
                                                dependencies: ["isFixedScore"],
                                                hide: function (obj) {
                                                    if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                label: '加/减分上限',
                                                field: 'upperLimitScore',
                                                type: 'number',
                                                required: true,
                                                dependencies: ["isFixedScore"],
                                                hide: function (obj) {
                                                    if (obj.form.getFieldValue("isFixedScore") === '0') {
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                },
                                                placeholder: '请输入'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '备注',
                                                dataIndex: 'remarks',
                                                key: 'remarks'
                                            },
                                            form: {
                                                type: 'textarea',
                                                placeholder: '请输入'
                                            }
                                        }
                                    ]}
                                />
                            </TabPane> : null
                        }
                    </Tabs> : <div className={s.alert}>点击左侧节点即可查看详细信息</div>}
                </div>
            </div>
        );
    }
}

export default index;