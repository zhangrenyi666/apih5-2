import React, { Component } from 'react';
import QnnTable from '../modules/qnn-table';
import moment from 'moment';
import {message as Msg, Modal} from 'antd'
const confirm = Modal.confirm
const config = {
    fetchConfig: {
        apiName: "getZjProZcStretchDrawList"
    },
    antd: {
        rowKey: "stretchDrawId",
        size: "small"
    },
    drawerConfig: {
        width: 1000
    },
    firstRowIsSearch: false,
    formItemLayout: {
        labelCol: {
            xs: { span: 24 },
            sm: { span: 3 }
        },
        wrapperCol: {
            xs: { span: 24 },
            sm: { span: 21 }
        }
    }
}
class index extends Component {
    render() {
        return (
            <QnnTable
                history={this.props.history}
                match={this.props.match}
                fetch={this.props.myFetch}
                headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                wrappedComponentRef={(me) => {
                    this.table = me;
                }}
                {...config}
                formConfig={[
                    {
                        isInTable: false,
                        form: {
                            type: "string",
                            field: 'stretchDrawId',
                            hide: true
                        }
                    },
                    {
                        isInForm: false,
                        table: {
                            width: 80,
                            align: 'center',
                            title: '序号', //表头标题
                            dataIndex: 'no', //表格里面的字段
                            key: 'no',//表格的唯一key    
                            align:'center',
                            render: (data, rows, index) => {
                                return index + 1;
                            }
                        },
                    },
                    {
                        table: {
                            title: '梁体编号',
                            dataIndex: 'tbeamNo',
                            align:'center',
                        },
                        form: {
                            type: 'string',
                            field: 'tbeamNo',
                            editDisabled: true,
                            required:true,
                            help:'梁体编号填写示例:  左线22-1T梁'
                        }
                    },
                    {
                        table: {
                            title: '预应力束编号',
                            dataIndex: 'preStressedNoId',
                            key: 'preStressedNoId',
                            align:'center',
                        },
                        form: {
                            type: 'string',
                            addShow:false,
                            field: 'preStressedNoId'
                        }
                    },
                    {
                        table: {
                            title: '张拉时间',
                            dataIndex: 'stretchDrawDate',
                            align:'center',
                            render: (data) => {
                                if (data) {
                                    return moment(data).format('YYYY-MM-DD')
                                } else {
                                    return ''
                                }
                            }
                        },
                        form: {
                            type: 'date',
                            addShow:false,
                            field: 'stretchDrawDate',
                        }
                    },
                    {
                        table: {
                            title: '设计力',
                            dataIndex: 'designForce',
                            align:'center',
                        },
                        form: {
                            type: 'number',
                            required: true,
                            field: 'designForce',
                            label:'设计力(KN)'
                        }
                    },
                    {
                        table: {
                            title: "张拉值(KN)",
                            noHaveSearchInput: true,
                            children: [
                                {
                                    dataIndex: 'tensionA1',
                                    title: "A1张拉力",
                                    width: 100,
                                    noHaveSearchInput: true,
                                    align:'center',
                                    key: 'tensionA1',
                                    type: "number",
                                    render:(data,rows,index)=>{
                                        let designForce= rows.designForce;
                                        if(designForce <= data && data <= parseFloat(designForce)+10){
                                            return <span style={{color: 'green'}}>{data+'↑'}</span>
                                        }else if(parseFloat(designForce)+10 < data){
                                            return <span style={{color: 'red'}}>{data+'↑'}</span>
                                        }else if(designForce > data){
                                            return <span style={{color: 'red'}}>{data+'↓'}</span>
                                        }
                                    }
                                },
                                {
                                    title: 'A2张拉力',
                                    dataIndex: 'tensionA2',
                                    type: "number",
                                    width: 100,
                                    align:'center',
                                    noHaveSearchInput: true,
                                    key: 'tensionA2',
                                    render:(data,rows,index)=>{
                                        let designForce= rows.designForce;
                                        if(designForce <= data && data <= parseFloat(designForce)+10){
                                            return <span style={{color: 'green'}}>{data+'↑'}</span>
                                        }else if(parseFloat(designForce)+10 < data){
                                            return <span style={{color: 'red'}}>{data+'↑'}</span>
                                        }else if(designForce > data){
                                            return <span style={{color: 'red'}}>{data+'↓'}</span>
                                        }
                                    }
                                }
                            ]
                        },
                        isInForm: false
                    },
                    {
                        isInTable: false,
                        form: {
                            type: 'number',
                            label: 'A1张拉力',
                            addShow:false,
                            field: 'tensionA1',
                        }
                    },
                    {
                        isInTable: false,
                        form: {
                            type: 'number',
                            label: 'A2张拉力',
                            addShow:false,
                            field: 'tensionA2',
                        }
                    },
                    {
                        table: {
                            title: '设计引申量(mm)',
                            dataIndex: 'designExpansion',
                            key: 'designExpansion',
                            align:'center',
                        },
                        form: {
                            type: 'number',
                            required: true,
                            field: 'designExpansion',
                        }
                    },
                    {
                        table: {
                            title: '实际引申量(mm)',
                            align:'center',
                            dataIndex: 'actualExpansion',
                            key: 'actualExpansion',
                        },
                        form: {
                            type: 'number',
                            addShow:false,
                            field: 'actualExpansion',
                        }
                    },
                    {
                        table: {
                            title: '偏差',
                            dataIndex: 'offset',
                            key: 'offset',
                            align:'center',
                            render:(data,rows,index)=>{
                                let designExpansion= rows.designExpansion;
                                let actualExpansion= rows.actualExpansion;
                                data = ((actualExpansion-designExpansion)/designExpansion)*100;
                                if(-6 <= data && data <= 6){
                                    return <span style={{color: 'green'}}>{data.toFixed(2)+"%"+(data>0?'↑':'↓')}</span>
                                }else if(6 < data){
                                    return <span style={{color: 'red'}}>{data.toFixed(2)+'%↑'}</span>
                                }else if(-6 > data){
                                    return <span style={{color: 'red'}}>{data.toFixed(2)+'%↓'}</span>
                                }
                            }
                        },
                        form: {
                            type: 'string',
                            addShow:false,
                            field: 'offset',
                        }
                    }
                ]}
                method={{
                    getValue: (obj)=>{
                        const { fetch } = obj.props;
                        confirm({
                            title: "确定引入数据么?",
                            okText: "确认",
                            cancelText: "取消",
                            onOk: () => {
                                fetch('getZjProZcStretchDrawValue').then(
                                    ({ success, message, data }) => {
                                        if (success) {
                                            Msg.success(message);
                                            this.table.refresh();
                                        } else {
                                            Msg.error(message)
                                        }
                                    }
                                );
                            }
                        });
                    },
                }}
                actionBtns={[
                    // {
                    //     name: 'add',
                    //     icon: 'plus',
                    //     type: 'primary',
                    //     label: '新增',
                    //     field: 'addOutBtn',
                    //     formBtns: [
                    //         {
                    //             name: 'cancel',
                    //             type: 'dashed',
                    //             label: '取消',
                    //         },
                    //         {
                    //             name: 'submit',
                    //             type: 'primary',
                    //             label: '保存',
                    //             fetchConfig: {
                    //                 apiName: 'addZjProZcStretchDraw'
                    //             }
                    //         }
                    //     ]
                    // },
                    {
                        name: 'edit',
                        type: 'primary',
                        label: '修改',
                        editDisabled: false,
                        onClick: (obj) => {
                            this.table.clearSelectedRows();
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
                                label: '保存',
                                fetchConfig: {
                                    apiName: 'updateZjProZcStretchDraw'
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
                            apiName: 'batchDeleteUpdateZjProZcStretchDraw'
                        },
                    },
                    {
                        name: 'getValue',
                        type: 'primary',
                        label: '引入数据',
                        onClick: "bind:getValue",
                    }
                ]}
            />
        )
    }
}

export default index