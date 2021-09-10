import React,{ Component } from 'react';
import QnnTable from '../modules/qnn-table'; 
import moment from 'moment';
const config = {
    fetchConfig: {
        apiName: "getZjProZcMudjackList"
    },
    antd: {
        rowKey: "mudjackId",
        size: "small"
    },
    drawerConfig: {
        width:1000
    },
    firstRowIsSearch: false,
    formItemLayout: {
        labelCol: {
            sm: { span: 3 }
        },
        wrapperCol: {
            sm: { span: 21 }
        }
    }
}
class index extends Component { 

   
    //获取min,max之间的随机数
    RandomNum = (Min, Max) => {
        var Range = Max - Min;
        var Rand = Math.random();
        var num = Min + Math.round(Rand * Range);
        if(num==Max||num==Min){
            return this.RandomNum(Min,Max)
        }else{
            return num;
        }
    }
    //获取min,max之间相等的随机数
    RandomNumBothequality = (Min, Max) => {
        var Range = Max - Min;
        var Rand = Math.random();
        var num = Min + Math.round(Rand * Range);
        return num
    }

    //获取小数
    RandomNumDecimal = (Min,Max) => {
        var Range = Max - Min;
        var Rand = Math.random();
        var num = Min + Math.round(Rand * Range);
        if(num==Max||num==Min){
            return this.RandomNumDecimal(Min,Max)
        }else{
            return parseFloat((num*0.01).toFixed(2));
        }
    }
    

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
                            field:'mudjackId',
                            hide: true
                        }
                    },    
                    {
                        isInForm: false,
                        table: {
                            width: 80,
                            align: 'center',
                            title: '序号',
                            dataIndex: 'no',
                            key: 'no',
                            render: (data, rows, index) => {
                                return index + 1;
                            }
                        },
                    },
                    {
                        table: {
                            title: '梁体编号',
                            dataIndex: 'tbeamId',
                            align: 'center'
                        },
                        form: {
                            label:'梁体编号',
                            type: 'string',
                            field: 'tbeamId',
                            editDisabled:true,
                            required: true,
                            help:'梁体编号填写示例:  左线22-1T梁'
                        }
                    },
                    {
                        table: {
                            title: '预应力束编号id',
                            dataIndex: 'preStressedNoId',
                            key: 'preStressedNoId',
                            type: 'select',
                            align: 'center'
                        },
                        form: {
                            type: 'select',
                            required: true,
                            field: 'preStressedNoId',
                            fetchConfig: {
                                apiName: "getBaseCodeSelect",
                                otherParams: {
                                    itemId: "yuYingLiShuBianHao"
                                }
                            },
                            optionConfig: {
                                label: "itemName",
                                value: "itemId"
                            },
                            // addShow:false,
                        }
                    },
                    {
                        table: {
                            title: '压浆时间',
                            format:'YYYY/MM/DD',
                            dataIndex: 'mudjackDate',
                            align: 'center'
                        },
                        form: {
                            type: 'date',
                            required:true,
                            field: 'mudjackDate',
                        },
                    
                    },
                    {
                        table: {
                            title: '浆液水灰比',
                            dataIndex: 'slurryMixRatio',
                            align: 'center'
                        },
                        form: {
                            field: 'slurryMixRatio',
                            type:'number',
                            addShow:false,
                            initialValue:(obj)=>{
                                //【浆液水灰比】自动生成数据为0.28;
                                return 0.28;
                            }
                        },
                        
                    },
                    {
                        table: {
                            title: '浆液稠度(s)',
                            dataIndex: 'slurryConsistency',
                            align: 'center'
                        },
                        form: {
                            field: 'slurryConsistency',
                            type:'number',
                            addShow:false,
                            initialValue:(obj)=>{
                                //【浆液稠度(s)】自动生成数据为10-17中的随机数字，包含10和17;
                                return this.RandomNumBothequality(10,17);
                            }
                        },
                       
                    },
                    // {
                    //     table: {
                    //         title: '注浆时间(s)',
                    //         dataIndex: 'groutingTime',
                    //         align: 'center'
                    //     },
                    //     form: {
                    //         label:'注浆时间(s)',
                    //         field: 'groutingTime',
                    //         type: 'number',
                    //         required:true,
                    //     }
                    // },
                    {
                        table: {
                            title: '注浆压力(MPa)',
                            dataIndex: 'groutingPress',
                            align: 'center'
                        },
                        form: {
                            field: 'groutingPress',
                            type:'number',
                            addShow:false,
                            precision: 2,   
                            initialValue:(obj)=>{
                               //【注浆压力(MPa)】为0.5-0.7里面产生的保留2为小数的随机数，第二位小数可以为0;
                               return this.RandomNumDecimal(50,70);
                            }
                        },
                        
                    },
                    {
                        table: {
                            title: '保压压力(MPa)',
                            dataIndex: 'pulpPress',
                            align: 'center'
                        },
                        form: {
                            field: 'pulpPress',
                            type:'number',
                            addShow:false,
                            precision: 2,
                            initialValue:(obj)=>{
                                //【保压压力(MPa)】为不小于0.5且不大于1的保留2为小数的随机数，第二位小数可以为0;
                                return this.RandomNumDecimal(50,100);
                            }
                        },
                    },
                    {
                        table: {
                            title: '保压时间',
                            dataIndex: 'dwellTime',
                            align: 'center'
                        },
                        form: {
                            field: 'dwellTime',
                            type:'number',
                            addShow:false,
                            initialValue:(obj)=>{
                                //【保压时间(s)】为220-260秒中的一个随机数。
                                return this.RandomNum(220,260);
                            }
                        }
                    },
                ]}
                actionBtns={[
                    {
                        name: 'add',
                        icon: 'plus',
                        type: 'primary',
                        label: '新增',
                        field: 'addOutBtn',
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
                                    apiName: 'addZjProZcMudjack'
                                }
                            }
                        ]
                    },
                    {
                        name: 'edit',
                        type: 'primary',
                        label: '修改',
                        editDisabled:false,
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
                                    apiName: 'updateZjProZcMudjack'
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
                            apiName: 'batchDeleteUpdateZjProZcMudjack'
                        },
                    }
                ]}
                method={{
                    
                }}
            />
        )
    }
}

export default index