import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg } from 'antd';
import s from "./style.less";
import Tree from "../modules/tree";
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
            deptId: '',
            deptName:'',
            data: []
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
    render() {
        const { deptId, deptName, data } = this.state;
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
                            this.setState({
                                deptId: ''
                            }, () => {
                                this.setState({
                                    deptId: node.value,
                                    deptName: node.label,
                                })
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
                            apiName: 'getZjXmJxIndexLibraryList',
                            otherParams: {
                                deptId:deptId
                            }
                        }}
                        actionBtns={[
                            {
                                name: 'add',
                                icon: 'plus',
                                type: 'primary',
                                label: '??????',
                                formBtns: [
                                    {
                                        name: 'cancel',
                                        type: 'dashed',
                                        label: '??????',
                                    },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '??????',
                                        fetchConfig: {
                                            apiName: 'addZjXmJxIndexLibrary',
                                        }
                                    }
                                ]
                            },
                            {
                                name: 'edit',
                                icon: 'edit',
                                type: 'primary',
                                label: '??????',
                                onClick: (obj) => {
                                    obj.btnCallbackFn.clearSelectedRows();
                                },
                                formBtns: [
                                    {
                                        name: 'cancel',
                                        type: 'dashed',
                                        label: '??????',
                                    },
                                    {
                                        name: 'submit',
                                        type: 'primary',
                                        label: '??????',
                                        fetchConfig: {
                                            apiName: 'updateZjXmJxIndexLibrary',
                                        }
                                    }
                                ]
                            },
                            {
                                name: 'del',
                                icon: 'delete',
                                type: 'danger',
                                label: '??????',
                                fetchConfig: {
                                    apiName: 'batchDeleteUpdateZjXmJxIndexLibrary',
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
                                    field: 'deptId',
                                    type: 'string',
                                    initialValue: deptId,
                                    hide: true,
                                }
                            },
                            {
                                isInTable: false,
                                form: {
                                    field: 'deptName',
                                    type: 'string',
                                    initialValue: deptName,
                                    hide: true,
                                }
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    dataIndex: 'costDutyIndex',
                                    key: 'costDutyIndex',
                                    width: 200,
                                    tooltip:12,
                                    fixed: 'left',
                                    filter: true,
                                    onClick: 'detail'
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '?????????',
                                    required: true
                                }
                            },
                            {
                                table: {
                                    title: '?????????',
                                    dataIndex: 'targetValue',
                                    key: 'targetValue',
                                    width: 100,
                                    tooltip:6,
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '?????????',
                                    required: true
                                }
                            },
                            {
                                table: {
                                    title: '??????????????????',
                                    dataIndex: 'scoringStandard',
                                    key: 'scoringStandard',
                                    width: 150,
                                    tooltip:9,
                                },
                                form: {
                                    type: 'textarea',
                                    placeholder: '?????????',
                                    required: true
                                }
                            },
                            // {
                            //     table: {
                            //         title: '??????',
                            //         dataIndex: 'weightValue',
                            //         key: 'weightValue',
                            //         width: 100
                            //     },
                            //     form: {
                            //         type: 'number',
                            //         placeholder: '?????????',
                            //         required: true
                            //     }
                            // },
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
                            //     form: {
                            //         type: 'radio',
                            //         placeholder: '?????????',
                            //         required: true,
                            //         optionData: [  //??????function (props)=>array
                            //             {
                            //                 label: "???",
                            //                 value: "0"
                            //             },
                            //             {
                            //                 label: "???",
                            //                 value: "1"
                            //             }
                            //         ]
                            //     }
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
                                form: {
                                    type: 'radio',
                                    placeholder: '?????????',
                                    required: true,
                                    optionData: [  //??????function (props)=>array
                                        {
                                            label: "???",
                                            value: "0"
                                        },
                                        {
                                            label: "???",
                                            value: "1"
                                        }
                                    ]
                                }
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'dataSources',
                                    key: 'dataSources',
                                    width: 150,
                                    tooltip:9,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '?????????'
                                }
                            },
                            {
                                table: {
                                    title: '????????????',
                                    dataIndex: 'forbidFlag',
                                    key: 'forbidFlag',
                                    width: 100,
                                    render:(data) => {
                                        if(data === '1'){
                                            return '???';
                                        }else{
                                            return '???';
                                        }
                                    }
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '??????',
                                    dataIndex: 'remarks',
                                    key: 'remarks',
                                    width: 150,
                                    tooltip:9,
                                },
                                form: {
                                    type: 'textarea',
                                    placeholder: '?????????'
                                }
                            }
                        ]}
                    /> : <div className={s.alert}>??????????????????????????????????????????</div>}
                </div>
            </div>
        );
    }
}

export default index;