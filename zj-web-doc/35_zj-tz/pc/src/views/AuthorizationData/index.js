//劳务人员列表
import React,{ Component } from "react";
import s from "./style.less";
import { Input } from 'antd'; // Modal,
import Tree from '../modules/tree-v2'
import QnnTable from '../modules/qnn-table';
const { Search } = Input;

class index extends Component {
    state = {
        //当前点击节点的id
        curClickNodeId: null,

        //当前点击节点的数据dataRef
        curClickNodeData: null,

        //是否在加载状态
        loading: false,

        treeNodeKeys: {
            label: "projectShortName",
            value: "projectId",
        },

        searchParams: {}
    }

    onSearch = (value) => {
        this.setState({
            searchParams: {
                projectShortName: value
            }
        },this.tree.onLoadData)
    }

    render() {
        const { treeNodeKeys,curClickNodeId,searchParams } = this.state; // label,children  
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

                    <div style={{ width: "100%",paddingRight: "18px" }}>
                        <Search
                            style={{ width: "100%" }}
                            placeholder="搜索关键词"
                            onSearch={this.onSearch}
                            enterButton
                        />
                    </div>


                    <Tree
                        noDataText="暂无数据"
                        visible
                        selectText={false}
                        rightMenuOption={[]}
                        nodeClick={(node) => {
                            let levelId = node.props.dataRef.projectId;
                            this.setState({
                                curClickNodeId: levelId,
                                curClickNodeData: node.props.dataRef,
                            })

                        }}
                        myFetch={this.props.myFetch} 
		 upload={this.props.myUpload}
                        btnShow={false}
                        draggable={false}
                        fetchConfig={{
                            parmasKey: "parentId",  //点击节点后将节点id赋值该key上传递给后台
                            apiName: "getZjTzPermissionListByProject",
                            searchApiName: "",
                            otherParams: {
                                ...searchParams
                            }
                        }}
                        keys={{
                            ...treeNodeKeys
                        }}
                        canExpand={() => {
                            return false;
                        }}
                        ref={(me) => {
                            if (me) { this.tree = me }
                        }}
                    />

                </div>
                <div className={s.right}>
                    {
                        curClickNodeId ?
                            <QnnTable
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                history={this.props.history}
                                match={this.props.match}
                                fetch={this.props.myFetch}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                antd={{
                                    rowKey: "permissionId",
                                    size: 'small'
                                }}
                                actionBtns = {[
                                    {
                                        name: 'add',//【add, addRow,  del, edit, detail, Component, form】
                                        icon: 'plus',//icon
                                        type: 'primary',//类型  默认 primary
                                        label: '授权',
                                        drawerTitle: "授权", //点击后的抽屉标题
                                        //表单里面的按钮  name内置 【submit， cancel】
                                        formBtns: [
                                            {
                                                name: 'cancel', //关闭右边抽屉
                                                type: 'dashed',//类型  默认 primary
                                                label: '取消',
                                            },
                                            {
                                                name: 'submit',//内置add del
                                                type: 'primary',//类型  默认 primary
                                                label: '保存',//保存数据并且关闭右边抽屉
                                                // paramsFormat:({params, props, ...})=>{return {...}}, 
                                                fetchConfig: {//ajax配置
                                                    apiName: 'addZjTzPermission',
                                                    otherParams: {
                                                        projectId: this.state.curClickNodeData ? this.state.curClickNodeData.projectId : '',
                                                        projectName: this.state.curClickNodeData ? this.state.curClickNodeData.projectName : '',
                                                        projectShortName: this.state.curClickNodeData ? this.state.curClickNodeData.projectShortName : '',
                                                        enginnerId: this.state.curClickNodeData ? this.state.curClickNodeData.enginnerId : '',
                                                        enginnerName: this.state.curClickNodeData ? this.state.curClickNodeData.enginnerName : '',
                                                        projectStateId: this.state.curClickNodeData ? this.state.curClickNodeData.projectStateId : '',
                                                        projectCaseId: this.state.curClickNodeData ? this.state.curClickNodeData.projectCaseId : '',
                                                        projectStateName: this.state.curClickNodeData ? this.state.curClickNodeData.projectStateName : '',
                                                        projectCaseName: this.state.curClickNodeData ? this.state.curClickNodeData.projectCaseName : '',
                                                    }
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        name: 'del',
                                        icon: 'delete',//icon
                                        type: 'danger',//类型  默认 primary  [primary dashed danger]
                                        label: '删除',
                                        fetchConfig: {//ajax配置
                                            apiName: 'batchDeleteUpdateZjTzPermission',
                                        }
                                    },
                    
                                ]}
                                fetchConfig={{
                                    apiName: "getZjTzPermissionList",
                                    otherParams: {
                                        projectId: curClickNodeId
                                    }
                                }}
                                formConfig = {[

                                    {
                                        isInTable: false,
                                        form: {
                                            type: 'string',
                                            label: 'permissionId',
                                            field: 'permissionId',
                                            hide: true,
                                        }
                                    },
                                    {
                                        table: {
                                            dataIndex: "permissionName",
                                            key: "permissionName",
                                            title: "<center>权限名称</center>",
                                        },
                                        form: {
                                            label: '权限名称',
                                            required: true,
                                            placeholder: "请输入",
                                            type: "string",
                                        }
                                    },
                    
                                    {
                                        isInTable: false,
                                        table: {
                                            dataIndex: "zjTzPermissionUserList",
                                            key: "zjTzPermissionUserList",
                                            title: "权限对象",
                                        },
                                        form: {
                                            required: true,
                                            type: "treeSelect",
                                            treeSelectOption: {
                                                fetchConfig: {
                                                    apiName: 'getSysDepartmentUserAllTree',
                                                },
                                                search: true,
                                                useCollect:true,
                                        collectApi:"appGetSysFrequentContactsList",  //查询收藏人员     接受后台参数[{xx:xxx,...}]
                                        collectApiByAdd:"appAddSysFrequentContacts", //新增收藏人员   传给后台的参数[{xx:xxx,...}]
                                        collectApiByDel:"appRemoveSysFrequentContacts", //删除收藏人员
                                                searchPlaceholder: '姓名、账号、电话',
                                                // searchApi:'getSysDepartmentUserAllTree',  //搜索时调用的api  [string]
                                                searchParamsKey: 'search',//搜索文字的K 默认是'searchText'   [string]
                                                searchOtherParams: { pageSize: 999 }//搜索时的其他参数  [object]
                    
                                            }
                                        },
                    
                                    },
                                    // {
                                    //     table: {
                                    //         dataIndex: "updateFlag",
                                    //         key: "updateFlag",
                                    //         title: "<center>权限范围</center>",
                                    //         render: (data) => data === "0" ? '修改' : data === '1' ? '只读' : '-'
                                    //     },
                                    //     form: {
                                    //         label:'权限范围',
                                    //         required: true,
                                    //         type: "radio",
                                    //         optionData: [
                                    //             {
                                    //                 value: '0',
                                    //                 label: "修改"
                                    //             },
                                    //             {
                                    //                 value: '1',
                                    //                 label: "只读"
                                    //             }
                                    //         ]
                                    //     }
                                    // }, 
                                    {
                                        isInForm: false,
                                        table: {
                                            dataIndex: "modifyUserName",
                                            key: "modifyUserName",
                                            title: "<center>操作者</center>",
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            dataIndex: "modifyTime",
                                            key: "modifyTime",
                                            title: "<center>操作时间</center>",
                                            format: 'YYYY-MM-DD HH:mm:ss'
                                        },
                                        form: {
                                            label: '操作时间',
                                            field: "modifyTime",
                                            placeholder: "请输入",
                                            type: "datetime",
                                        }
                                    },
                                    {
                                        isInForm: false,
                                        table: {
                                            width: 80,
                                            showType: 'tile',
                                            dataIndex: 'action', //表格里面的字段
                                            key: 'action',//表格的唯一key  
                                            fixed: 'right',
                                            btns: [
                                                {
                                                    name: 'edit',
                                                    render: function (rowData) {
                                                        return '<a>修改</a>';
                                                    },
                                                    formBtns: [
                                                        {
                                                            name: 'cancel', //关闭右边抽屉
                                                            type: 'dashed',//类型  默认 primary
                                                            label: '取消',
                                                        },
                                                        {
                                                            name: 'diySubmit',//内置add del
                                                            type: 'primary',//类型  默认 primary
                                                            label: '保存',//保存数据并且关闭右边抽屉
                                                            field: "diySubmit", 
                                                            onClick:(obj) => {
                                                                obj.btnCallbackFn.setBtnsLoading('add', 'diySubmit');
                                                                obj._formData.projectId = this.state.curClickNodeData.projectId;
                                                                obj._formData.projectName = this.state.curClickNodeData.projectName;
                                                                obj._formData.projectShortName = this.state.curClickNodeData.projectShortName;
                                                                obj._formData.enginnerId = this.state.curClickNodeData.enginnerId;
                                                                obj._formData.enginnerName = this.state.curClickNodeData.enginnerName;
                                                                obj._formData.projectStateId = this.state.curClickNodeData.projectStateId;
                                                                obj._formData.projectCaseId = this.state.curClickNodeData.projectCaseId;
                                                                obj._formData.projectStateName = this.state.curClickNodeData.projectStateName;
                                                                obj._formData.projectCaseName = this.state.curClickNodeData.projectCaseName;
                                                                obj.btnCallbackFn.fetch('updateZjTzPermission', obj._formData, ({ data, success, message }) => {
                                                                    if (success) {
                                                                        obj.btnCallbackFn.msg.success(message)
                                                                        obj.btnCallbackFn.refresh();
                                                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                                        obj.btnCallbackFn.closeDrawer(false);
                                                                    } else {
                                                                        obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
                                                                        obj.btnCallbackFn.msg.error(message);
                                                                    }
                                                                })
                                                            }
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    }
                                ]}
                            /> : null
                    }
                    {!curClickNodeId ? <div className={s.alert}>点击左侧节点即可查看详细信息</div> : null}
                </div>

            </div>
        );
    }
}

export default index;