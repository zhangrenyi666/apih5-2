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
                        modalType="common" //common  drawer  ?????????????????????????????????
                        visible
                        selectModal="0" //0?????????  1??????(??????)  2????????????????????????
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        btnShow={false} //????????????????????????
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
                        //ajax????????????
                        data={data}
                        //???????????? ??????{value:value,label:label,children:children}
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
                                label: '????????????',
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
                                        Msg.error('??????????????????');
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
                                    title: '??????',
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
                                                            label: "??????"
                                                        },
                                                        {
                                                            field: 'zbkSumbit',
                                                            name: "zbkSumbit",
                                                            type: "primary",
                                                            label: "??????",
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
                                    placeholder: '?????????',
                                    disabled: true,
                                    initialValue: () => {
                                        return this.state.rowData ?.realName;
                                    },
                                    spanForm: 6
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'sysDepartmentShowList',
                                    key: 'sysDepartmentShowList',
                                    width: 500,
                                    render: (data) => {
                                        return data.join('');
                                    }
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '?????????',
                                    disabled: true,
                                    initialValue: () => {
                                        return this.state.rowData ?.sysDepartmentShowList;
                                    },
                                    spanForm: 18
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
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
                                                            label: '????????????',
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
                                                            label: "??????",
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
                                                                title: '??????????????????',
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
                                                                title: '?????????',
                                                                dataIndex: 'targetValue',
                                                                key: 'targetValue',
                                                                width: 100,
                                                                tooltip: 6,
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '??????????????????',
                                                                dataIndex: 'scoringStandard',
                                                                key: 'scoringStandard',
                                                                width: 150,
                                                                tooltip: 9
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '??????',
                                                                dataIndex: 'weightValue',
                                                                key: 'weightValue',
                                                                width: 100,
                                                                fieldsConfig: {
                                                                    type: "number",
                                                                    min: 0,
                                                                    max: 100,
                                                                    placeholder: "?????????"
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
                                                        //         title: '????????????',
                                                        //         dataIndex: 'isMandatory',
                                                        //         key: 'isMandatory',
                                                        //         width: 100,
                                                        //         render: (data) => {
                                                        //             if (data === '0') {
                                                        //                 return '???';
                                                        //             }
                                                        //             return '???';
                                                        //         }
                                                        //     },
                                                        //     isInForm:false
                                                        // },
                                                        {
                                                            table: {
                                                                title: '??????????????????',
                                                                dataIndex: 'isAutomaticScoring',
                                                                key: 'isAutomaticScoring',
                                                                width: 120,
                                                                render: (data) => {
                                                                    if (data === '0') {
                                                                        return '???';
                                                                    }
                                                                    return '???';
                                                                }
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '??????',
                                                                dataIndex: 'dataSources',
                                                                key: 'dataSources',
                                                                width: 150,
                                                                tooltip: 9,
                                                            },
                                                            isInForm: false
                                                        },
                                                        {
                                                            table: {
                                                                title: '??????',
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
                    /> : <div className={s.alert}>??????????????????????????????????????????</div>}
                </div>
                {visible ? <div>
                    <Modal
                        width={'40%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title="?????????????????????"
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
                            upload={this.props.myUpload} //??????????????????promise
                            //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    label: '????????????',
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
                                        //searchPlaceholder: '????????????????????????',
                                        // searchApi:'getSysUserCurrentTree',  //??????????????????api  [string]
                                        searchParamsKey: 'search',//???????????????K ?????????'searchText'   [string]
                                        searchOtherParams: { pageSize: 999 }//????????????????????????  [object]
                                    }
                                }
                            ]}
                            btns={[
                                {
                                    field: 'formCancel',
                                    name: "cancel",
                                    type: "dashed",
                                    label: "??????",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    field: 'formSubmit',
                                    name: "submit",
                                    type: "primary",
                                    label: "??????",
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