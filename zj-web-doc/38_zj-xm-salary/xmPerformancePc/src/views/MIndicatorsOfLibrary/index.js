import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import QnnForm from '../modules/qnn-table/qnn-form';
import { message as Msg, Modal } from 'antd';
import s from "./style.less";
import Tree from "../modules/tree";
const config = {
    antd: {
        rowKey: function (row) {
            return row.userKey
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
            deptId: '',
            projectId: '',
            projectName: '',
            data: [],
            visible: false,
            userList: [],
            mInData: [],
            rowData: null
        }
    }
    componentDidMount() {
        this.props.myFetch('getSysDepartmentTree').then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data
                })
            } else {
                Msg.error(message);
            }
        });
    }
    handleCancel = () => {
        this.setState({
            visible: false
        })
    }
    render() {
        const { deptId, data, visible } = this.state;
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
                    {data.length ? <div className={s.rootlTree}><Tree
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
                        rightMenuOption={[]}
                        nodeClick={(node) => {
                            this.props.myFetch('getSysDepartmentListByProjectXmrz', { departmentId: node.value }).then(({ success, message, data }) => {
                                if (success) {
                                    this.setState({
                                        deptId: ''
                                    }, () => {
                                        this.setState({
                                            deptId: node.value,
                                            projectId: data.projectId,
                                            projectName: data.projectName
                                        })
                                    });
                                } else {
                                    Msg.error(message);
                                }
                            });
                        }}
                        //ajax请求配置
                        data={data}
                        //键值配置 默认{value:value,label:label,children:children}
                        keys={{
                            label: "label",
                            value: "value",
                            children: "children"
                        }}
                    /></div> : null}
                </div>
                <div className={s.rootr}>
                    {deptId ? <QnnTable
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        wrappedComponentRef={(me) => {
                            this.table = me;
                        }}
                        fetchConfig={{
                            apiName: 'getSysUserListByBgXMJX',
                            otherParams: {
                                departmentId: deptId
                            }
                        }}
                        actionBtns={[
                            {
                                name: 'pfld',
                                icon: 'plus',
                                type: 'primary',
                                label: '评分领导',
                                disabled: (obj) => {
                                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                onClick: (obj) => {
                                    if (obj.selectedRows.length) {
                                        this.setState({
                                            visible: true,
                                            userList: obj.selectedRows
                                        })
                                    } else {
                                        Msg.error('请选择数据！');
                                    }
                                }
                            }
                        ]}
                        {...config}
                        formConfig={[
                            {
                                isInTable: false,
                                form: {
                                    field: 'userKey',
                                    type: 'string',
                                    hide: true,
                                }
                            },
                            {
                                table: {
                                    title: '姓名',
                                    dataIndex: 'realName',
                                    key: 'realName',
                                    width: 100,
                                    filter: true,
                                    onClick: (obj) => {
                                        this.props.myFetch('getZjXmJxUserIndexLibraryList', { userKey: obj.rowData.userKey, deptId: deptId }).then(({ success, message, data }) => {
                                            if (success) {
                                                this.setState({
                                                    mInData: data,
                                                    rowData: obj.rowData
                                                }, () => {
                                                    obj.btnCallbackFn.closeDrawer(true);
                                                    obj.btnCallbackFn.setDrawerBtns([
                                                        {
                                                            field: 'zbkCancel',
                                                            name: "cancel",
                                                            type: "dashed",
                                                            label: "取消"
                                                        },
                                                        {
                                                            field: 'zbkSumbit',
                                                            name: "zbkSumbit",
                                                            type: "primary",
                                                            label: "提交",
                                                            isValidate: false,
                                                            onClick: (objs) => {
                                                                let setBtnsLoading = objs.btnCallbackFn.setBtnsLoading;
                                                                setBtnsLoading('add', 'zbkSumbit');
                                                                this.state.mInData.map((item) => {
                                                                    item.userKey = obj.rowData.userKey;
                                                                    item.realName = obj.rowData.realName;
                                                                    return item;
                                                                })
                                                                let body = {
                                                                    userKey: obj.rowData.userKey,
                                                                    deptId: deptId,
                                                                    saveFlag: this.state.mInData.length ? '1' : '0',
                                                                    userLibraryList: this.state.mInData
                                                                };
                                                                objs.btnCallbackFn.fetchByCb('batchSubmitZjXmJxUserIndexLibrary', body, ({ data, success, message }) => {
                                                                    if (success) {
                                                                        setBtnsLoading('remove', 'zbkSumbit');
                                                                        Msg.success(message);
                                                                        this.table.closeDrawer(false);
                                                                    } else {
                                                                        setBtnsLoading('remove', 'zbkSumbit');
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    ])
                                                })
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    disabled: true,
                                    initialValue: () => {
                                        return this.state.rowData ?.realName;
                                    },
                                    spanForm: 6
                                }
                            },
                            {
                                table: {
                                    title: '部门',
                                    dataIndex: 'sysDepartmentShowList',
                                    key: 'sysDepartmentShowList',
                                    width: 500,
                                    render: (data) => {
                                        return data.join('');
                                    }
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    disabled: true,
                                    initialValue: () => {
                                        return this.state.rowData ?.sysDepartmentShowList;
                                    },
                                    spanForm: 18
                                }
                            },
                            {
                                table: {
                                    title: '评分领导',
                                    dataIndex: 'ext4',
                                    key: 'ext4',
                                    width: 100,
                                },
                                isInForm: false
                            },
                            {
                                isInTable: false,
                                form: {
                                    type: "component",
                                    field: "component1",
                                    Component: objs => {
                                        return (
                                            <div style={{ padding: '0 10px' }}>
                                                <QnnTable
                                                    fetch={this.props.myFetch}
                                                    upload={this.props.myUpload}
                                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                                    data={this.state.mInData}
                                                    antd={{
                                                        rowKey: 'libraryId',
                                                        size: 'small'
                                                    }}
                                                    actionBtns={[
                                                        {
                                                            name: 'yjhq',
                                                            icon: 'plus',
                                                            type: 'primary',
                                                            label: '一键获取',
                                                            onClick: (obj) => {
                                                                this.props.myFetch('getZjXmJxIndexLibraryList', { deptId: deptId }).then(({ success, message, data }) => {
                                                                    if (success) {
                                                                        this.setState({
                                                                            mInData: data
                                                                        })
                                                                    } else {
                                                                        Msg.error(message);
                                                                    }
                                                                });
                                                            }
                                                        },
                                                        {
                                                            name: "delDiy",
                                                            icon: "delete",
                                                            type: "danger",
                                                            label: "删除",
                                                            disabled: (obj) => {
                                                                if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                                                    return false;
                                                                } else {
                                                                    return true;
                                                                }
                                                            },
                                                            onClick: (obj) => {
                                                                let selectedRows = obj.selectedRows;
                                                                let mInDatas = this.state.mInData;
                                                                mInDatas = mInDatas.filter(item => {
                                                                    let idList = selectedRows.map(v => v.libraryId)
                                                                    return !idList.includes(item.libraryId)
                                                                })
                                                                this.setState({
                                                                    mInData: mInDatas
                                                                })
                                                            }
                                                        }
                                                    ]}
                                                    formConfig={[
                                                        {
                                                            table: {
                                                                title: '成本责任指标',
                                                                dataIndex: 'costDutyIndex',
                                                                key: 'costDutyIndex',
                                                                width: 150,
                                                                tooltip: 9,
                                                                fixed: 'left'
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '目标值',
                                                                dataIndex: 'targetValue',
                                                                key: 'targetValue',
                                                                width: 100,
                                                                tooltip: 6,
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '评价计分标准',
                                                                dataIndex: 'scoringStandard',
                                                                key: 'scoringStandard',
                                                                width: 150,
                                                                tooltip: 9
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '权重',
                                                                dataIndex: 'weightValue',
                                                                key: 'weightValue',
                                                                width: 100,
                                                                fieldsConfig: {
                                                                    type: "number",
                                                                    min: 0,
                                                                    max: 100,
                                                                    placeholder: "请输入"
                                                                },
                                                                tdEdit: true,
                                                                tdEditCb: (obj) => {
                                                                    this.state.mInData.map((item) => {
                                                                        if (item.libraryId === obj.newRowData.libraryId) {
                                                                            item.weightValue = obj.newRowData.weightValue;
                                                                        }
                                                                        return item;
                                                                    });
                                                                }
                                                            },
                                                            isInForm: false
                                                        },
                                                        // {
                                                        //     table: {
                                                        //         title: '是否必选',
                                                        //         dataIndex: 'isMandatory',
                                                        //         key: 'isMandatory',
                                                        //         width: 100,
                                                        //         render: (data) => {
                                                        //             if (data === '0') {
                                                        //                 return '否';
                                                        //             }
                                                        //             return '是';
                                                        //         }
                                                        //     },
                                                        //     isInForm:false
                                                        // },
                                                        {
                                                            table: {
                                                                title: '是否自动评分',
                                                                dataIndex: 'isAutomaticScoring',
                                                                key: 'isAutomaticScoring',
                                                                width: 120,
                                                                render: (data) => {
                                                                    if (data === '0') {
                                                                        return '否';
                                                                    }
                                                                    return '是';
                                                                }
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '来源',
                                                                dataIndex: 'dataSources',
                                                                key: 'dataSources',
                                                                width: 150,
                                                                tooltip: 9,
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '备注',
                                                                dataIndex: 'remarks',
                                                                key: 'remarks',
                                                                width: 150,
                                                                tooltip: 9,
                                                            },
                                                            isInForm: false
                                                        }
                                                    ]}
                                                />
                                            </div>
                                        );
                                    }
                                }
                            }
                        ]}
                    /> : <div className={s.alert}>点击左侧节点即可查看详细信息</div>}
                </div>
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="请选择评分领导"
                        visible={visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'replyData'}
                    >
                        <QnnForm
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    label: '评分领导',
                                    field: 'leaderArray',
                                    type: 'treeSelect',
                                    required: true,
                                    treeSelectOption: {
                                        selectType: '2',
                                        maxNumber: 1,
                                        fetchConfig: {
                                            apiName: 'getSysUserTree',
                                        },
                                        //search: true,
                                        //searchPlaceholder: '姓名、账号、电话',
                                        // searchApi:'getSysUserCurrentTree',  //搜索时调用的api  [string]
                                        searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                        searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    field: 'formCancel',
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    field: 'formSubmit',
                                    name: "submit",
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        let body = {
                                            userList: this.state.userList,
                                            leaderArray: obj.values.leaderArray,
                                            projectId: this.state.projectId,
                                            projectName: this.state.projectName
                                        };
                                        obj.btnfns.fetchByCb('setUpZjXmJxUserScoringLeader', body, ({ data, success, message }) => {
                                            if (success) {
                                                Msg.success(message);
                                                this.setState({ visible: false });
                                                this.table.clearSelectedRows();
                                                this.table.refresh();
                                            } else {
                                                Msg.error(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        );
    }
}

export default index;