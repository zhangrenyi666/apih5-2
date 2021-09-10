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
                            this.setState({
                                deptId: ''
                            }, () => {
                                this.setState({
                                    deptId: node.value,
                                    deptName: node.label,
                                })
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
                                            apiName: 'addZjXmJxIndexLibrary',
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
                                            apiName: 'updateZjXmJxIndexLibrary',
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
                                    title: '成本责任指标',
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
                                    placeholder: '请输入',
                                    required: true
                                }
                            },
                            {
                                table: {
                                    title: '目标值',
                                    dataIndex: 'targetValue',
                                    key: 'targetValue',
                                    width: 100,
                                    tooltip:6,
                                },
                                form: {
                                    type: 'string',
                                    placeholder: '请输入',
                                    required: true
                                }
                            },
                            {
                                table: {
                                    title: '评价计分标准',
                                    dataIndex: 'scoringStandard',
                                    key: 'scoringStandard',
                                    width: 150,
                                    tooltip:9,
                                },
                                form: {
                                    type: 'textarea',
                                    placeholder: '请输入',
                                    required: true
                                }
                            },
                            // {
                            //     table: {
                            //         title: '权重',
                            //         dataIndex: 'weightValue',
                            //         key: 'weightValue',
                            //         width: 100
                            //     },
                            //     form: {
                            //         type: 'number',
                            //         placeholder: '请输入',
                            //         required: true
                            //     }
                            // },
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
                            //     form: {
                            //         type: 'radio',
                            //         placeholder: '请输入',
                            //         required: true,
                            //         optionData: [  //可为function (props)=>array
                            //             {
                            //                 label: "否",
                            //                 value: "0"
                            //             },
                            //             {
                            //                 label: "是",
                            //                 value: "1"
                            //             }
                            //         ]
                            //     }
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
                                form: {
                                    type: 'radio',
                                    placeholder: '请输入',
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
                                table: {
                                    title: '来源',
                                    dataIndex: 'dataSources',
                                    key: 'dataSources',
                                    width: 150,
                                    tooltip:9,
                                },
                                form: {
                                    type: 'string',
                                    required: true,
                                    placeholder: '请输入'
                                }
                            },
                            {
                                table: {
                                    title: '禁止修改',
                                    dataIndex: 'forbidFlag',
                                    key: 'forbidFlag',
                                    width: 100,
                                    render:(data) => {
                                        if(data === '1'){
                                            return '是';
                                        }else{
                                            return '否';
                                        }
                                    }
                                },
                                isInForm:false
                            },
                            {
                                table: {
                                    title: '备注',
                                    dataIndex: 'remarks',
                                    key: 'remarks',
                                    width: 150,
                                    tooltip:9,
                                },
                                form: {
                                    type: 'textarea',
                                    placeholder: '请输入'
                                }
                            }
                        ]}
                    /> : <div className={s.alert}>点击左侧节点即可查看详细信息</div>}
                </div>
            </div>
        );
    }
}

export default index;