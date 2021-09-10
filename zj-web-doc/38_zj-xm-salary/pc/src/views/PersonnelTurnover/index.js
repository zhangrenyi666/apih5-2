import React from "react";
import Apih5 from "qnn-apih5"
import QnnTable from '../modules/qnn-table';
import { message as Msg } from 'antd';
import FormRedraw from "./FormRedraw";
class Index extends Apih5 {
    constructor(props) {
        super(props)
        this.state = {
            changeBmValue: '',
            treeData: [],
            value: '',
            changeType: '1',
            oneItemLayout: {
                labelCol: {
                    sm: { span: 2 }
                },
                wrapperCol: {
                    sm: { span: 22 }
                }
            },
            oneFormItemWrapperStyle: -5,
            fourItemSpan: 6,
            fourItemLayout: {
                labelCol: {
                    sm: { span: 8 }
                },
                wrapperCol: {
                    sm: { span: 16 }
                }
            },
            fourItemLayout2: {
                labelCol: {
                    sm: { span: 12 }
                },
                wrapperCol: {
                    sm: { span: 12 }
                }
            },
        }
    }
    componentDidMount() {
        this.refresh('0');
    }
    refresh(departmentParentId, key) {
        let tableData = null
        this.props.myFetch('getSysDepartmentCurrentTree', { departmentParentId }).then(
            ({ data, success, message }) => {
                if (departmentParentId !== '0') {
                    var loopFn = function (dataList) {
                        for (var i = 0; i < dataList.length; i++) {
                            if (dataList[i].value === key) {
                                dataList[i].children = data;
                            } else if (dataList[i].children) {
                                dataList[i].children = loopFn(dataList[i].children)
                            }
                        }
                        return dataList;
                    }

                    tableData = loopFn(this.state.treeData);
                } else {
                    tableData = data
                }

                if (success) {
                    this.setState({
                        treeData: tableData
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    render() {
        const orgId = this.apih5.getOrgId()
        return (
            // <div className={s.page}>
            //     <div className={s.rootl}
            //         ref={(me) => {
            //             if (me) {
            //                 this.leftDom = me;
            //             }
            //         }}>
            //         <div
            //             className={s.hr}
            //             ref={(me) => {
            //                 if (me) {
            //                     let _this = this;
            //                     function moveFn(e) {
            //                         let conDomLeft = document.getElementsByClassName('ant-layout-content')[0].offsetLeft;
            //                         _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
            //                     }
            //                     me.addEventListener('mousedown', (e) => {
            //                         this.onDragStartPos = e.pageX;
            //                         document.addEventListener('mousemove', moveFn, false)
            //                     }, false);
            //                     document.addEventListener('mouseup', (e) => {
            //                         document.removeEventListener('mousemove', moveFn, false)
            //                     }, false)
            //                 }
            //             }}
            //         ></div>
            //         {this.state.treeData.length ? <Tree
            //             selectText={false}
            //             modalType="common" //common  drawer  抽屉出现方式或者普通的
            //             visible
            //             selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
            //             myFetch={this.props.myFetch}
            //             upload={this.props.myUpload}
            //             btnShow={false} //是否显示底部按钮
            //             disabled={true}
            //             draggable={false}
            //             nodeRender={nodeData => {
            //                 return (
            //                     <span>
            //                         {nodeData["label"]}
            //                     </span>
            //                 );
            //             }}
            //             treeProps={{
            //                 showLine: true
            //             }}
            //             rightMenuOption={[]}
            //             nodeClick={(node, selectedKeys) => {
            //                 this.refresh(node.value, selectedKeys[0])
            //                 this.setState({
            //                     value: ''
            //                 }, () => {
            //                     this.setState({
            //                         value: node
            //                     })
            //                 })
            //             }}
            //             data={this.state.treeData}
            //             //键值配置 默认{value:value,label:label,children:children}
            //             keys={{
            //                 label: "label",
            //                 value: "value",
            //                 children: "children"
            //             }}
            //         /> : null}
            //     </div>
            //     <div className={s.rootr}>
            //         {
            //             Object.keys(this.state.value).length ? <QnnTable
            //                 fetch={this.props.myFetch}
            //                 upload={this.props.myUpload}
            //                 wrappedComponentRef={(me) => { this.myQnnTable = me }}
            //                 method={{}}
            //                 antd={{
            //                     rowKey: 'zjXmSalaryUserReshuffleId'
            //                 }}
            //                 componentsKey={{}}
            //                 fetchConfig={
            //                     {
            //                         apiName: 'getZjXmSalaryUserReshuffleList',
            //                         otherParams: {
            //                             topId: this.state.value.value
            //                         }
            //                     }
            //                 }
            //                 actionBtns={[
            //                     {
            //                         name: "add", //内置add del
            //                         icon: "plus", //icon
            //                         type: "primary", //类型  默认 primary  [primary dashed danger]
            //                         label: "新增",
            //                         formBtns: [
            //                             {
            //                                 name: "cancel", //关闭右边抽屉
            //                                 type: "dashed", //类型  默认 primary
            //                                 label: "取消"
            //                             },

            //                             {
            //                                 field: "submit",
            //                                 name: "submit",
            //                                 type: "primary",
            //                                 label: "提交",
            //                                 fetchConfig: {
            //                                     apiName: 'addZjXmSalaryUserExtension',
            //                                     params: {},
            //                                     otherParams: async (obj) => {
            //                                     },
            //                                 },
            //                             }
            //                         ]
            //                     },
            //                     {
            //                         field: 'submitsp',
            //                         name: 'del',
            //                         fetchConfig: {//ajax配置
            //                             apiName: '提交审批',
            //                         },
            //                         type: 'primary',
            //                         label: '提交审批',
            //                     },
            //                     {
            //                         field: 'submitsczh',
            //                         name: 'del',
            //                         fetchConfig: {//ajax配置
            //                             apiName: '生成账号',
            //                         },
            //                         type: 'primary',
            //                         label: '生成账号',
            //                     },
            //                     {
            //                         field: 'delBtn',
            //                         name: 'del',
            //                         icon: 'delete',
            //                         fetchConfig: {//ajax配置
            //                             apiName: 'login',
            //                         },
            //                         type: 'danger',
            //                         label: '删除',
            //                     },
            //                 ]}
            //                 formConfig={[
            //                     {
            //                         isInTable: false,
            //                         form: {
            //                             field: 'zjXmSalaryUserReshuffleId',
            //                             type: 'string',
            //                             hide: true
            //                         }
            //                     },
            //                     {
            //                         table: {
            //                             title: '姓名',
            //                             dataIndex: 'name',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '岗位',
            //                             dataIndex: 'position',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '所属项目',
            //                             dataIndex: 'ssxm',
            //                             key: 'ssxm',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '所属科室',
            //                             dataIndex: 'ssks',
            //                             key: 'ssks',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '所属部门',
            //                             dataIndex: 'ssbm',
            //                             key: 'ssbm',
            //                             fixed: 'left'
            //                         },
            //                         isInForm: false
            //                     },
            //                     // ↑↑↑↑↑↑
            //                     {
            //                         table: {
            //                             title: '异动岗位',
            //                             dataIndex: 'ydgw',
            //                             key: 'ydgw',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '异动项目',
            //                             dataIndex: 'ydxm',
            //                             key: 'ydxm',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '异动科室',
            //                             dataIndex: 'ydks',
            //                             key: 'ydks',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '异动部门',
            //                             dataIndex: 'ydbm',
            //                             key: 'ydbm',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '异动类型',
            //                             dataIndex: 'ydlx',
            //                             key: 'ydlx',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '到岗状态',
            //                             dataIndex: 'dgzt',
            //                             key: 'dgzt',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '当前审批位置',
            //                             dataIndex: 'approvalLocation',
            //                             key: 'approvalLocation',
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         table: {
            //                             title: '审批状态',
            //                             dataIndex: 'apih5FlowStatus',
            //                             key: 'apih5FlowStatus',
            //                             width: 100,
            //                             type: 'select'
            //                         },
            //                         form: {
            //                             field: "apih5FlowStatus",
            //                             type: "select",
            //                             placeholder: "请选择...",
            //                             fetchConfig: {
            //                                 apiName: "getBaseCodeSelect",
            //                                 otherParams: {
            //                                     itemId: 'liuChengZhuangTai'
            //                                 }
            //                             },
            //                             optionConfig: {
            //                                 label: 'itemName',
            //                                 value: 'itemId',
            //                             },
            //                         }
            //                     },
            //                     {
            //                         table: {
            //                             title: '审批时间',
            //                             dataIndex: 'approvalTime',
            //                             key: 'approvalTime',
            //                             width: 100,
            //                             format: 'YYYY-MM-DD'
            //                         },
            //                         isInForm: false
            //                     },
            //                     {
            //                         isInForm: false,
            //                         table: {
            //                             showType: "tile", //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
            //                             width: 110,
            //                             title: "操作",
            //                             key: "action", //操作列名称
            //                             fixed: "right", //固定到右边
            //                             align: "center",
            //                             btns: [
            //                                 {
            //                                     name: "edit", // 内置name有【add,  del, edit, detail, Component, form】
            //                                     label: "修改",
            //                                     onClick: (obj) => {
            //                                         // zjXmSalaryUserReshuffleId
            //                                     }
            //                                 },
            //                                 {
            //                                     name: 'diy',//内置add del
            //                                     label: "更多",
            //                                     btns: [
            //                                         {
            //                                             name: "jlsc", //关闭右边抽屉
            //                                             label: "简历生成",
            //                                             onClick: "bind:jlsc"
            //                                         },
            //                                         // {
            //                                         //     name: "pyjl", //关闭右边抽屉
            //                                         //     label: "聘用记录",
            //                                         //     onClick: "bind:pyjl"
            //                                         // }
            //                                     ]
            //                                 },
            //                             ]
            //                         }
            //                     }
            //                 ]}
            //                 tabs={[
            //                     {
            //                         field: "basic",
            //                         name: "qnnFormDiy",
            //                         title: "人员异动",
            //                         content: (obj) => {
            //                             return (
            //                                 <div>
            //                                     <FormRedraw {...this.props} refInstance={this.myQnnTable} clickInfo={obj.clickCb.rowInfo} />
            //                                 </div>
            //                             )
            //                         }
            //                     }
            //                 ]}
            //             /> : <div className={s.alert}>点击左侧节点即可查看数据</div>
            //         }
            //     </div>
            // </div>

            <div>
                <QnnTable
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => { this.myQnnTable = me }}
                    antd={{
                        rowKey: 'zjXmSalaryUserReshuffleId'
                    }}
                    componentsKey={{}}
                    fetchConfig={
                        {
                            apiName: 'getZjXmSalaryUserReshuffleList',
                            otherParams: {
                                orgId
                            }
                        }
                    }
                    method={{
                        changeConfirmTypeFunc: (obj) => {
                            const rowData = obj.qnnTableInstance.getSelectedRows()
                            if (rowData.length === 1 && rowData[0].changeConfirmType === '0') {
                                return false
                            } else {
                                return true
                            }
                        }
                    }}
                    actionBtns={[
                        {
                            name: "add", //内置add del
                            icon: "plus", //icon
                            type: "primary", //类型  默认 primary  [primary dashed danger]
                            label: "新增",
                            formBtns: [
                                {
                                    name: "cancel", //关闭右边抽屉
                                    type: "dashed", //类型  默认 primary
                                    label: "取消"
                                },

                                {
                                    field: "submit",
                                    name: "submit",
                                    type: "primary",
                                    label: "提交",
                                    fetchConfig: {
                                        apiName: 'addZjXmSalaryUserExtension',
                                        params: {},
                                        otherParams: async (obj) => {
                                        },
                                    },
                                }
                            ]
                        },
                        // {
                        //     field: 'submitsp',
                        //     name: 'del',
                        //     fetchConfig: {//ajax配置
                        //         apiName: '提交审批',
                        //     },
                        //     type: 'primary',
                        //     label: '提交审批',
                        // },
                        // {
                        //     field: 'submitsczh',
                        //     name: 'del',
                        //     fetchConfig: {//ajax配置
                        //         apiName: '生成账号',
                        //     },
                        //     type: 'primary',
                        //     label: '生成账号',
                        // },
                        {
                            field: 'confirm',
                            name: 'confirm',
                            type: 'primary',
                            label: '确认',
                            disabled: 'bind:changeConfirmTypeFunc',
                            onClick: async (obj) => {
                                const {  success, message } = await this.props.myFetch('changeConfirmZjXmSalaryUserReshuffle', { ...obj.selectedRows[0] })
                                if (success) {
                                    Msg.success('确认成功')
                                    this.myQnnTable.clearSelectedRows()
                                    this.myQnnTable.refresh()
                                } else {
                                    Msg.error(message)
                                }
                            }
                        },
                        {
                            field: 'delBtn',
                            name: 'del',
                            icon: 'delete',
                            fetchConfig: {//ajax配置
                                apiName: 'batchDeleteZjXmSalaryUserReshuffle',
                            },
                            type: 'danger',
                            label: '删除',
                        },
                    ]}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'zjXmSalaryUserReshuffleId',
                                type: 'string',
                                hide: true
                            }
                        },
                        {
                            table: {
                                title: '姓名',
                                dataIndex: 'realName',
                                fixed: 'left',
                                onClick: 'detail'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '岗位',
                                dataIndex: 'positionName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },

                        {
                            table: {
                                title: '所属单位/项目',
                                dataIndex: 'orgName',
                                key: 'orgName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所属科室',
                                dataIndex: 'officeName',
                                key: 'officeName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '所属部门',
                                dataIndex: 'departmentName',
                                key: 'departmentName',
                                fixed: 'left'
                            },
                            isInForm: false
                        },
                        // ↑↑↑↑↑↑
                        // {
                        //     table: {
                        //         title: '异动岗位',
                        //         dataIndex: 'positionNameAfter',
                        //         key: 'positionNameAfter',
                        //     },
                        //     isInForm: false
                        // },
                        {
                            table: {
                                title: '异动单位/项目',
                                dataIndex: 'orgAfterName',
                                key: 'orgAfterName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '异动科室',
                                dataIndex: 'officeAfterName',
                                key: 'officeAfterName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '异动部门',
                                dataIndex: 'departmentAfterName',
                                key: 'departmentAfterName',
                            },
                            isInForm: false
                        },
                        {
                            table: {
                                title: '异动类型',
                                dataIndex: 'changeType',
                                key: 'changeType',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                label: '异动类型',
                                field: 'changeType',
                                placeholder: '请选择',
                                optionData: [
                                    {
                                        label: "正常异动",
                                        value: "1"
                                    },
                                    {
                                        label: "离职异动",
                                        value: "2"
                                    }
                                ],
                            }
                        },
                        {
                            table: {
                                title: '是否已确认',
                                dataIndex: 'changeConfirmType',
                                key: 'changeConfirmType',
                                type: 'select'
                            },
                            form: {
                                type: 'select',
                                label: '是否已确认',
                                field: 'changeConfirmType',
                                placeholder: '请选择',
                                optionData: [
                                    {
                                        label: "未确定",
                                        value: "0"
                                    },
                                    {
                                        label: "已确定",
                                        value: "1"
                                    }
                                ],
                            }
                        },
                        // {
                        //     table: {
                        //         title: '到岗状态',
                        //         dataIndex: 'dgzt',
                        //         key: 'dgzt',
                        //     },
                        //     isInForm: false
                        // },
                        // {
                        //     table: {
                        //         title: '当前审批位置',
                        //         dataIndex: 'approvalLocation',
                        //         key: 'approvalLocation',
                        //     },
                        //     isInForm: false
                        // },
                        // {
                        //     table: {
                        //         title: '审批状态',
                        //         dataIndex: 'apih5FlowStatus',
                        //         key: 'apih5FlowStatus',
                        //         width: 100,
                        //         type: 'select'
                        //     },
                        //     form: {
                        //         field: "apih5FlowStatus",
                        //         type: "select",
                        //         placeholder: "请选择...",
                        //         fetchConfig: {
                        //             apiName: "getBaseCodeSelect",
                        //             otherParams: {
                        //                 itemId: 'liuChengZhuangTai'
                        //             }
                        //         },
                        //         optionConfig: {
                        //             label: 'itemName',
                        //             value: 'itemId',
                        //         },
                        //     }
                        // },
                        // {
                        //     table: {
                        //         title: '审批时间',
                        //         dataIndex: 'approvalTime',
                        //         key: 'approvalTime',
                        //         width: 100,
                        //         format: 'YYYY-MM-DD'
                        //     },
                        //     isInForm: false
                        // },
                        {
                            isInForm: false,
                            table: {
                                showType: "tile", //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
                                width: 110,
                                title: "操作",
                                key: "action", //操作列名称
                                fixed: "right", //固定到右边
                                align: "center",
                                btns: [
                                    {
                                        name: "edit", // 内置name有【add,  del, edit, detail, Component, form】
                                        label: "修改",
                                        disabled: (obj) => {
                                            return obj.rowData.changeConfirmType !== '0'
                                        },
                                        onClick: (obj) => {
                                            // zjXmSalaryUserReshuffleId
                                        }
                                    },
                                    // {
                                    //     name: 'diy',//内置add del
                                    //     label: "更多",
                                    //     btns: [
                                    //         {
                                    //             name: "jlsc", //关闭右边抽屉
                                    //             label: "简历生成",
                                    //             onClick: "bind:jlsc"
                                    //         },
                                    //         // {
                                    //         //     name: "pyjl", //关闭右边抽屉
                                    //         //     label: "聘用记录",
                                    //         //     onClick: "bind:pyjl"
                                    //         // }
                                    //     ]
                                    // },
                                ]
                            }
                        }
                    ]}
                    tabs={[
                        {
                            field: "basic",
                            name: "qnnFormDiy",
                            title: "人员异动",
                            content: (obj) => {
                                return (
                                    <div>
                                        <FormRedraw {...this.props} refInstance={this.myQnnTable} clickInfo={obj.clickCb} />
                                    </div>
                                )
                            }
                        }
                    ]}
                />
            </div>
        )
    }
}

export default Index