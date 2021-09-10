import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { message as Msg, Spin } from 'antd';
import s from "./tableTow.less";
import Tree from "../modules/tree";
const config = {
    antd: {
        rowKey: 'sysProjectDeptConfId',
        size: 'small'
    },
    drawerConfig: {
        width: '1000px'
    },
    paginationConfig: {
        position: 'bottom'
    }
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rowData:props?.clickCb?.rowData || {},
            loading:false,
            treeData: [],
            value: ''
        }
    }
    componentDidMount() {
        this.refresh();
    }
    refresh() {
        this.setState({
            loading: true
        })
        const { rowData } = this.state;
        this.props.myFetch('getSysDepartmentCurrentTree', { departmentParentId: rowData?.departmentId }).then(
            ({ data, success, message }) => {
                if (success) {
                    this.setState({
                        treeData: data,
                        loading: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const { loading, rowData } = this.state;
        return (
            <div className={s.root}>
                <Spin spinning={loading}>
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
                                            let conDomLeft = document.getElementsByClassName('ant-drawer-content-wrapper')[0].offsetLeft;
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
                            {this.state.treeData.length ? <Tree
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
                                treeProps={{
                                    showLine: true
                                }}
                                rightMenuOption={[]}
                                nodeClick={(node) => {
                                    this.setState({
                                        value: ''
                                    }, () => {
                                        this.setState({
                                            value: node.value
                                        })
                                    })
                                }}
                                data={this.state.treeData}
                                //键值配置 默认{value:value,label:label,children:children}
                                keys={{
                                    label: "label",
                                    value: "value",
                                    children: "children"
                                }}
                            /> : null}
                        </div>
                        <div className={s.rootr}>
                            {this.state.value ? <QnnTable
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                fetchConfig={{
                                    apiName: 'getSysProjectDeptConfList',
                                    otherParams: {
                                        departmentId: this.state.value
                                    }
                                }}
                                actionBtns={[
                                    {
                                        name: 'add',//内置add del
                                        icon: 'plus',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '新增',
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'addSysProjectDeptConf',
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'edit',//内置add del
                                        icon: 'edit',//icon
                                        type: 'primary',//类型  默认 primary  [primary dashed danger]
                                        label: '修改',
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//提交数据并且关闭右边抽屉 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'updateSysProjectDeptConf',
                                                },
                                                onClick: (obj) => {
                                                    obj.btnCallbackFn.clearSelectedRows();
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',//内置add del
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateSysProjectDeptConf',
                                        }
                                    }
                                ]}
                                {...config}
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'sysProjectDeptConfId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'departmentId',
                                            type: 'string',
                                            initialValue:this.state.value,
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'projectId',
                                            type: 'string',
                                            initialValue:rowData?.projectId,
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '岗位',
                                            dataIndex: 'jobType',
                                            key: 'jobType',
                                            width: 150,
                                            type:'select'
                                        },
                                        form: {
                                            type: 'select',
                                            required: true,
                                            optionConfig: {
                                                label: 'itemName',
                                                value: 'itemId'
                                            },
                                            fetchConfig: {
                                                apiName: "getBaseCodeTreeByLevel",
                                                otherParams: {
                                                    itemId: "gangWeiGuanLi",
                                                    subItemId: "03"
                                                }
                                            }
                                        }
                                    },
                                    {
                                        table: {
                                            title: '人数类型',
                                            dataIndex: 'numType',
                                            key: 'numType',
                                            width: 100,
                                            type:'select'
                                        },
                                        form: {
                                            type: 'select',
                                            required: true,
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value'
                                            },
                                            optionData: [
                                                {
                                                    label: '个值',
                                                    value: '0'
                                                },
                                                {
                                                    label: '区间',
                                                    value: '1'
                                                }
                                            ]
                                        }
                                    },
                                    {
                                        table: {
                                            title: '人数(最小)',
                                            width: 150,
                                            dataIndex: 'numMin',
                                            key: 'numMin'
                                        },
                                        form: {
                                            required: true,
                                            type: 'number'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '人数(最大)',
                                            width: 150,
                                            dataIndex: 'numMax',
                                            key: 'numMax'
                                        },
                                        form: {
                                            required: true,
                                            type: 'number'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '补录人数',
                                            width: 150,
                                            dataIndex: 'supplementNum',
                                            key: 'supplementNum'
                                        },
                                        form: {
                                            required: false,
                                            disabled:true,
                                            type: 'number'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '是否兼职',
                                            dataIndex: 'jobFlag',
                                            width: 100,
                                            key: 'jobFlag',
                                            type: 'select'
                                        },
                                        form: {
                                            type: 'select',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value'
                                            },
                                            optionData: [
                                                {
                                                    label: '否',
                                                    value: '0'
                                                },
                                                {
                                                    label: '是',
                                                    value: '1'
                                                }
                                            ],
                                            required: true,
                                        }
                                    },
                                    {
                                        table: {
                                            title: '备注',
                                            width: 120,
                                            dataIndex: 'remarks',
                                            key: 'remarks'
                                        },
                                        form: {
                                            type: 'textarea',
                                            autoSize: {
                                                minRows: 1,
                                                maxRows: 3
                                            }
                                        }
                                    }
                                ]}
                            /> : <div className={s.alert}>点击左侧节点即可查看数据</div>}
                        </div>
                    </div>
                </Spin>
            </div>
        );
    }
}

export default index;