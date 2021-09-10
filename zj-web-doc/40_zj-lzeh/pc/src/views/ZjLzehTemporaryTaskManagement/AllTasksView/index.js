import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-table/qnn-form";
import {message as Msg, Tabs, Radio, Tree, Button, Modal } from 'antd';
import { DownOutlined } from '@ant-design/icons';
import Comment from '../../comment'
import SelectFilesDownLoad from '../common/SelectFilesDownLoad'

class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            mode: 'task',
            isModalVisible: false,
            treeData: [],
            taskManageId: '',
            currentKey: '',
            name: '',
            startDate: null,
            endDate: null
        }
    }
    componentDidMount() {
        this.props.myFetch('getZjLzehTempTaskManageTree').then(({ data, message, success }) => {
            this.setState({
                treeData: data
            })
        })
    }
    // 树结构选择某个节点的 方法
    onSelect = (selectedKeys, { selected, selectedNodes, node, event }) => {
        this.setState({
            currentKey: selectedKeys[0]
        })
    }

    handleModeChange = e => {
        const mode = e.target.value;
        this.setState({ mode, currentKey: null });
    }

    showModal = () => {
        this.setState({
            isModalVisible: true
        })
    };

    handleOk = () => {
        this.setState({
            isModalVisible: false
        })
    };

    handleCancel = () => {
        this.setState({
            isModalVisible: false
        })
    };

    cellMergeList = []
    render() {
        const { mode, isModalVisible, treeData, taskManageId, currentKey, name, startDate, endDate } = this.state
        return (
            <div>
                <Radio.Group onChange={this.handleModeChange} value={mode} style={{ marginBottom: 8 }}>
                    <Radio.Button value="task">按任务查看</Radio.Button>
                    <Radio.Button value="personnel">按人员查看</Radio.Button>
                </Radio.Group>
                {
                    mode === 'task' ?
                        // 按任务查看
                        <div style={{ marginTop: '30px', display: 'flex', width: '100%' }}>
                            {/* 左侧树结构 */}
                            <div style={{ width: '25%' }}>
                                <Tree
                                    showLine
                                    switcherIcon={<DownOutlined />}
                                    defaultExpandedKeys={['0-0-0']}
                                    onSelect={this.onSelect}
                                    treeData={treeData} />
                            </div>
                            {/* 右侧table */}
                            <div style={{ width: '75%' }}>
                                <QnnTable
                                    {...this.props}
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                    wrappedComponentRef={(me) => {
                                        this.table = me;
                                    }}
                                    antd={
                                        {
                                            rowKey: 'zjLzehTempTaskManageId',
                                            size: 'small'
                                        }
                                    }
                                    isShowRowSelect={false}
                                    fetchConfig={
                                        {
                                            apiName: 'getZjLzehTempTaskManageTreeList',
                                            otherParams: {
                                                // implementPersonId:this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                                                zjLzehTempTaskManageId: currentKey
                                            },

                                        }
                                    }
                                    method={{
                                        taskCommitDisabled: (obj) => {
                                            let rowData = obj.btnCallbackFn.getSelectedRows();
                                            if (rowData.length === 1 && rowData[0].status === '1') {
                                                return false;
                                            } else {
                                                return true
                                            }
                                        },
                                        submittedDisabled: (obj) => {
                                            let rowData = obj.btnCallbackFn.getSelectedRows();
                                            if (rowData.length === 1 && rowData[0].status === '3') {
                                                return false;
                                            } else {
                                                return true
                                            }
                                        },
                                        createClildrenTaskDisabled: (obj) => {
                                            let rowData = obj.btnCallbackFn.getSelectedRows();
                                            if (rowData.length === 1 && (rowData[0].status === '0' || rowData[0].status === '1')) {
                                                return false;
                                            } else {
                                                return true
                                            }
                                        },
                                        isChildrenTaskNoCommitDistributionConfirmFunc: (obj) => {
                                            let rowData = obj.btnCallbackFn.getSelectedRows();
                                            if (rowData.length === 1 && rowData[0].parentId && rowData[0].status === '1') {
                                                return false;
                                            } else {
                                                return true
                                            }
                                        },
                                        returnStatusDisabled: (obj) => {
                                            let rowData = obj.btnCallbackFn.getSelectedRows();
                                            if (rowData.length === 1 && (rowData[0].status === '0' || rowData[0].status === '3')) {
                                                return false;
                                            } else {
                                                return true
                                            }
                                        },
                                    }}

                                    formConfig={[
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'zjLzehTempTaskManageId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'parentId',
                                                type: 'string',
                                                hide: true,
                                            }
                                        },
                                        {
                                            isInTable: false,
                                            form: {
                                                field: 'implementPersonId',
                                                type: 'string',
                                                hide: true,
                                                initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                                                // initialValue: '9999999999'
                                            }
                                        },
                                        {
                                            table: {
                                                title: '任务名称',
                                                dataIndex: 'taskName',
                                                key: 'taskName',
                                                width: 150,
                                                onClick: 'detail',

                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '任务描述',
                                                dataIndex: 'taskDescribe',
                                                key: 'taskDescribe',
                                                width: 150,
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '分配人名',
                                                dataIndex: 'allotPerson',
                                                key: 'allotPerson',
                                                width: 150,

                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '开始日期',
                                                dataIndex: 'beginDate',
                                                key: 'beginDate',
                                                width: 150,
                                                format: 'YYYY-MM-DD'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '要求完成日期',
                                                dataIndex: 'requireComplateDate',
                                                key: 'requireComplateDate',
                                                width: 150,
                                                format: 'YYYY-MM-DD'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '分配对象',
                                                dataIndex: 'implementPerson',
                                                key: 'implementPerson',
                                                width: 150,
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '实际完成日期',
                                                dataIndex: 'realCompalteDate',
                                                key: 'realCompalteDate',
                                                width: 150,
                                                format: 'YYYY-MM-DD'
                                            },
                                            isInForm: false
                                        },
                                        {
                                            // table: {
                                            //     title: '附件',
                                            //     dataIndex: 'fileList',
                                            //     key: 'fileList',
                                            //     width: 180,
                                            //     render: (val) => {
                                            //         if (val.length) {
                                            //             return <SelectFilesDownLoad dataList={val}/>
                                            //         } else {
                                            //             return '无附件'
                                            //         }
                                            //     }
                                            // },
                                            isInTable: false,
                                            form: {
                                                label: '附件上传',
                                                field: "fileList",
                                                type: 'files',
                                            }
                                        },
                                        {
                                            table: {
                                                title: '完成情况说明',
                                                dataIndex: 'complateExplain',
                                                key: 'complateExplain',
                                                width: 150,
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '状态',
                                                dataIndex: 'status',
                                                key: 'status',
                                                width: 150,
                                                render: (val) => {
                                                    switch (val) {
                                                        case '0':
                                                            return '未分配'
                                                        case '1':
                                                            return '已分配'
                                                        case '2':
                                                            return '收回'
                                                        case '3':
                                                            return '已提交 '
                                                        case '4':
                                                            return '已确认 '
                                                    }
                                                }
                                            },
                                            isInForm: false
                                        },
                                        {
                                            table: {
                                                title: '沟通交流',
                                                dataIndex: 'gt',
                                                key: 'gt',
                                                width: 150,
                                                render: () => {
                                                    return <span style={{ cursor: 'pointer ', color: '#1890ff' }} >{'>>>'}</span>
                                                },
                                                onClick: (obj) => {
                                                    setTimeout(() => {
                                                        this.setState({
                                                            taskManageId: obj.rowData.zjLzehTempTaskManageId
                                                        })
                                                        this.showModal()
                                                    }, 0)
                                                }
                                            },
                                            isInForm: false
                                        },
                                    ]}
                                ></QnnTable>
                                <Modal title="沟通交流" width={'70%'} visible={isModalVisible} onOk={this.handleOk} onCancel={this.handleCancel} footer={null}>
                                    {isModalVisible ? <Comment currentLogin={this.props.loginAndLogoutInfo.loginInfo.userInfo} propVal={this.props} taskManageId={taskManageId} /> : null}
                                </Modal>
                            </div>
                        </div> :
                        // 按人员查看
                        <div style={{ marginTop: '30px' }}>
                            <QnnForm
                                wrappedComponentRef={(me) => {
                                    this.formOne = me;
                                }}
                                formItemLayout={{
                                    labelCol: {
                                        xs: { span: 24 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 24 },
                                        sm: { span: 12 }
                                    }
                                }}
                                formConfig={[
                                    {
                                        type: 'string',
                                        label: '人员姓名',
                                        field: 'name', //唯一的字段名 ***必传
                                        placeholder: '请输入',
                                        required: false,
                                        span: 6
                                    },
                                    {
                                        type: 'date',
                                        label: '开始日期',
                                        field: 'startDate', //唯一的字段名 ***必传
                                        placeholder: '请选择',
                                        required: false,
                                        format: "YYYY-MM-DD",
                                        showTime: false, //不显示时间
                                        scope: false, //是否可选择范围
                                        span: 6
                                    },
                                    {
                                        type: 'date',
                                        label: '要求完成日期',
                                        field: 'endDate', //唯一的字段名 ***必传
                                        placeholder: '请选择',
                                        required: false,
                                        format: "YYYY-MM-DD",
                                        showTime: false, //不显示时间
                                        scope: false, //是否可选择范围
                                        span: 6
                                    },
                                    {
                                        type: 'component',
                                        field: 'diy',
                                        span: 6,
                                        //第一种，推荐定义方式 需要将componentsKey对象传到qnn-form
                                        Component: "myDiyComponent",
                                        //第二种自定义组件方式
                                        Component: obj => {
                                            return (
                                                <div style={{ height: '100%', display: 'flex', alignItems: 'center' }}>
                                                    <Button type="primary" onClick={() => {
                                                        this.formOne.getValues().then(val => {
                                                            setTimeout(() => {
                                                                this.setState({
                                                                    name: val.name,
                                                                    startDate: val.startDate,
                                                                    endDate: val.endDate
                                                                })
                                                                // this.table.refresh()
                                                            }, 0)
                                                            // if (!this.table.getTableData().length) {
                                                            //     Msg.warning('没有符合条件的数据！')
                                                            // }
                                                            
                                                            if (!this.table.state.data.length) {
                                                                Msg.warning('没有符合条件的数据！')
                                                            }
                                                        })
                                                    }}>查询</Button>
                                                </div>
                                            );
                                        }
                                    }
                                ]}
                            ></QnnForm>
                            <QnnTable
                                {...this.props}
                                fetch={this.props.myFetch}
                                upload={this.props.myUpload}
                                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                                wrappedComponentRef={(me) => {
                                    this.table = me;
                                }}
                                antd={
                                    {
                                        rowKey: 'zjLzehTempTaskManageId',
                                        size: 'small'
                                    }
                                }

                                fetchConfig={
                                    {
                                        apiName: 'getZjLzehTempTaskManageListByPerson',
                                        otherParams: {
                                            implementPerson: name,
                                            beginDate: startDate,
                                            requireComplateDate: endDate
                                        },
                                        success:(res)=>{
                                            const {data} = res
                                            this.cellMergeList = data
                                            if(!data.length){
                                                Msg.warning('没有符合条件的数据！')
                                            }
                                        }
                                    }
                                }

                                isShowRowSelect={false}
                                method={{
                                    taskCommitDisabled: (obj) => {
                                        let rowData = obj.btnCallbackFn.getSelectedRows();
                                        if (rowData.length === 1 && rowData[0].status === '1') {
                                            return false;
                                        } else {
                                            return true
                                        }
                                    },
                                    submittedDisabled: (obj) => {
                                        let rowData = obj.btnCallbackFn.getSelectedRows();
                                        if (rowData.length === 1 && rowData[0].status === '3') {
                                            return false;
                                        } else {
                                            return true
                                        }
                                    },
                                    createClildrenTaskDisabled: (obj) => {
                                        let rowData = obj.btnCallbackFn.getSelectedRows();
                                        if (rowData.length === 1 && (rowData[0].status === '0' || rowData[0].status === '1')) {
                                            return false;
                                        } else {
                                            return true
                                        }
                                    },
                                    isChildrenTaskNoCommitDistributionConfirmFunc: (obj) => {
                                        let rowData = obj.btnCallbackFn.getSelectedRows();
                                        if (rowData.length === 1 && rowData[0].parentId && rowData[0].status === '1') {
                                            return false;
                                        } else {
                                            return true
                                        }
                                    },
                                    returnStatusDisabled: (obj) => {
                                        let rowData = obj.btnCallbackFn.getSelectedRows();
                                        if (rowData.length === 1 && (rowData[0].status === '0' || rowData[0].status === '3')) {
                                            return false;
                                        } else {
                                            return true
                                        }
                                    },
                                }}
                                dataFormat={"bind:dataFormatCell"}
                                method={
                                    {
                                        dataFormatCell: (data) => {
                                            const newData = [...data].reduce((preArr, curData, i) => {
                                                //需要计算 rowSpan 的数 
                                                //每次map只能加一次rowSpan 然后需要等下次遍历在加
                                                let added = false;
                                                //改变 preArr、curData
                                                const newArr = [...preArr].map(item => {
                                                    //说明列表中已经有这条清单了
                                                    if (item.implementPerson === curData.implementPerson) {
                                                        //第一个 workNo 需要加一  他的数据的 workNo 不需要加一 
                                                        const defaultRowSpan = item.rowSpan || 1;
                                                        const rowSpan = !added ? defaultRowSpan + 1 : 0;
                                                        //如果发现列表中已经有相同的数据时 一定需要将当前这条数据的 rowSpan设置为0
                                                        curData.rowSpan = 0;
                                                        added = true;
                                                        return { ...item, rowSpan: rowSpan };
                                                    }
                                                    return { ...item };
                                                }).concat([{ ...curData }]);
                                                return newArr;
                                            }, []);
                                            return newData
                                        }
                                    }
                                }
                                formConfig={[
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'zjLzehTempTaskManageId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'parentId',
                                            type: 'string',
                                            hide: true,
                                        }
                                    },
                                    {
                                        isInTable: false,
                                        form: {
                                            field: 'implementPersonId',
                                            type: 'string',
                                            hide: true,
                                            initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                                            // initialValue: '9999999999'
                                        }
                                    },
                                    {
                                        table: {
                                            title: '人员姓名',
                                            dataIndex: 'implementPerson',
                                            key: 'implementPerson',
                                            width: 150,
                                            render: (data, rowData) => {
                                                return {
                                                    children: data,
                                                    props: {
                                                        rowSpan: rowData.rowSpan
                                                    },
                                                };
                                            }

                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '任务名称',
                                            dataIndex: 'taskName',
                                            key: 'taskName',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '任务描述',
                                            dataIndex: 'taskDescribe',
                                            key: 'taskDescribe',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '任务分配人',
                                            dataIndex: 'allotPerson',
                                            key: 'allotPerson',
                                            width: 150,

                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '开始日期',
                                            dataIndex: 'beginDate',
                                            key: 'beginDate',
                                            width: 150,
                                            format: 'YYYY-MM-DD'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '要求完成日期',
                                            dataIndex: 'requireComplateDate',
                                            key: 'requireComplateDate',
                                            width: 150,
                                            format: 'YYYY-MM-DD'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '实际完成日期',
                                            dataIndex: 'realCompalteDate',
                                            key: 'realCompalteDate',
                                            width: 150,
                                            format: 'YYYY-MM-DD'
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '完成情况说明',
                                            dataIndex: 'complateExplain',
                                            key: 'complateExplain',
                                            width: 150,
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '状态',
                                            dataIndex: 'status',
                                            key: 'status',
                                            width: 150,
                                            render: (val) => {
                                                switch (val) {
                                                    case '0':
                                                        return '未分配'
                                                    case '1':
                                                        return '已分配'
                                                    case '2':
                                                        return '收回'
                                                    case '3':
                                                        return '已提交 '
                                                    case '4':
                                                        return '已确认 '
                                                }
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '交流信息',
                                            dataIndex: 'gt',
                                            key: 'gt',
                                            width: 150,
                                            render: () => {
                                                return '>>>'
                                            },
                                            onClick: (obj) => {
                                                setTimeout(() => {
                                                    this.setState({
                                                        taskManageId: obj.rowData.zjLzehTempTaskManageId
                                                    })
                                                    this.showModal()
                                                }, 0)
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '任务完成比例',
                                            dataIndex: 'persent',
                                            key: 'persent',
                                            width: 150,
                                            render: (data, rowData) => {
                                                return {
                                                    children: data ? data + '%' : '0%',
                                                    props: {
                                                        rowSpan: rowData.rowSpan
                                                    },
                                                };
                                            }
                                        },
                                        isInForm: false
                                    },
                                    {
                                        table: {
                                            title: '排名',
                                            dataIndex: 'sort',
                                            key: 'sort',
                                            width: 150,
                                            render: (data, rowData) => {
                                                return {
                                                    children: data,
                                                    props: {
                                                        rowSpan: rowData.rowSpan
                                                    },
                                                };
                                            }
                                        },
                                        isInForm: false
                                    },
                                ]}
                            ></QnnTable>
                        </div>
                }
                <Modal title="沟通交流" width={'70%'} visible={isModalVisible} onOk={this.handleOk} onCancel={this.handleCancel} footer={null}>
                    {isModalVisible ? <Comment currentLogin={this.props.loginAndLogoutInfo.loginInfo.userInfo} propVal={this.props} taskManageId={taskManageId} /> : null}
                </Modal>
            </div>
        )
    }
}
export default index;