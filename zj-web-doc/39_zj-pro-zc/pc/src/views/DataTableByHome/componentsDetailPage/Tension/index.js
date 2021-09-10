import React from 'react';
import Apih5 from 'qnn-apih5';
import QnnTable from 'qnn-table';
import style from "./style.less"
import moment from "moment"

class Page extends Apih5 {
    state = {};
    componentDidMount() {
        this.resize();
        window.addEventListener("resize", this.resize, false);

    }
    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
    }
    resize = () => {
        this.setState({
            tableHeight: window.innerHeight - 230
        })
    };

    config = ()=>{
        return {
            fetchConfig: {
                apiName: "getZjProZcStretchDrawList"
            },
            antd: {
                rowKey: "stretchDrawId",
                size: "small",
                scroll: {
                    y: this.state.tableHeight
                }
            },
            drawerConfig: {
                width: 1000
            },
            firstRowIsSearch: false,
            isShowRowSelect: false
        }
    };


    render() {
        return (
            <div className={style.page}>
                <div className={style.title}>
                    预应力张拉监控
                </div>
                <QnnTable
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { this.qnnTable = me }}
                    method={{}}
                    componentsKey={{}}
                    fetchConfig={{
                        apiName: "getZjProZcQsList"
                    }}
                    antd={{
                        rowKey: "qsId",
                        size: "small"
                    }}
                    isShowRowSelect={false}

                    {...this.config()}
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
                                render: (data,rows,index) => {
                                    return index + 1;
                                }
                            },
                        },
                        {
                            table: {
                                align: 'center',
                                title: '梁体编号',
                                dataIndex: 'tbeamNo'
                            },
                            form: {
                                type: 'string',
                                field: 'tbeamNo',
                                editDisabled: true,
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        // {
                        //     table: {
                        //         title: 'T梁名称',
                        //         onClick: 'detail',
                        //         dataIndex: 'tbeamName'
                        //     },
                        //     form: {
                        //         type: 'string',
                        //         required: true,
                        //         field: 'tbeamName',
                        //         editDisabled: true,
                        //         spanForm: 12,
                        //         formItemLayout: {
                        //             labelCol: {
                        //                 sm: { span: 6 }
                        //             },
                        //             wrapperCol: {
                        //                 sm: { span: 18 }
                        //             }
                        //         }
                        //     }
                        // },
                        {
                            table: {
                                align: 'center',
                                title: '预应力束编号',
                                dataIndex: 'preStressedNoId',
                                key: 'preStressedNoId',
                                // type: 'select',
                            },
                            form: {
                                // type: 'select',
                                type: 'string',
                                required: true,
                                field: 'preStressedNoId',
                                // fetchConfig: {
                                //     apiName: "getBaseCodeSelect",
                                //     otherParams: {
                                //         itemId: "yuYingLiShuBianHao"
                                //     }
                                // },
                                // optionConfig: {
                                //     label: "itemName",
                                //     value: "itemId"
                                // }
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
                                align: 'center',
                                title: '设计力',
                                dataIndex: 'designForce'
                            },
                            form: {
                                type: 'string',
                                required: true,
                                field: 'designForce'
                            }
                        },
                        // {
                        //     table: {
                        //         title: "油压机读数(MPa)",
                        //         noHaveSearchInput: true,
                        //         children: [
                        //             {
                        //                 dataIndex: 'oilA1',
                        //                 title: "A1油压读数",
                        //                 width: 100,
                        //                 noHaveSearchInput: true,
                        //                 key: 'oilA1',
                        //                 type: "string"
                        //             },
                        //             {
                        //                 title: 'A2油压读数',
                        //                 dataIndex: 'oilA2',
                        //                 type: "number",
                        //                 width: 100,
                        //                 noHaveSearchInput: true,
                        //                 key: 'oilA2'
                        //             }
                        //         ]
                        //     },
                        //     isInForm: false
                        // },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         type: 'number',
                        //         label: 'A1油压读数',
                        //         required: true,
                        //         field: 'oilA1',
                        //         spanForm: 12,
                        //         formItemLayout: {
                        //             labelCol: {
                        //                 sm: { span: 6 }
                        //             },
                        //             wrapperCol: {
                        //                 sm: { span: 18 }
                        //             }
                        //         },
                        //         render: (val,rowData) => {
                        //             return <span style={{ color: rowData[`oilA1Color`] }}>{val}</span>
                        //         }
                        //     }
                        // },
                        // {
                        //     isInTable: false,
                        //     form: {
                        //         type: 'number',
                        //         label: 'A2油压读数',
                        //         required: true,
                        //         field: 'oilA2',
                        //         spanForm: 12,
                        //         formItemLayout: {
                        //             labelCol: {
                        //                 sm: { span: 6 }
                        //             },
                        //             wrapperCol: {
                        //                 sm: { span: 18 }
                        //             }
                        //         },
                        //         render: (val,rowData) => {
                        //             return <span style={{ color: rowData[`oilA2Color`] }}>{val}</span>
                        //         }
                        //     }
                        // },
                        {
                            table: {
                                align: 'center',
                                title: "张拉值(KN)",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'tensionA1',
                                        title: "A1张拉力",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'tensionA1',
                                        type: "number",
                                        align: 'center',
                                        render: (data, rows) => {
                                            let designForce = rows.designForce;
                                            if (designForce <= data && data <= parseFloat(designForce) + 10) {
                                                return <span style={{ color: 'rgb(68 243 68)' }}>{data + '↑'}</span>
                                            } else if (parseFloat(designForce) + 10 < data) {
                                                return <span style={{ color: '#f72222' }}>{data + '↑'}</span>
                                            } else if (designForce > data) {
                                                return <span style={{ color: '#f72222' }}>{data + '↓'}</span>
                                            }
                                        }
                                    },
                                    {
                                        title: 'A2张拉力',
                                        dataIndex: 'tensionA2',
                                        type: "number",
                                        width: 100,
                                        noHaveSearchInput: true,
                                        key: 'tensionA2',
                                        align: 'center',
                                        render:(data,rows,index)=>{
                                            let designForce= rows.designForce;
                                            if(designForce <= data && data <= parseFloat(designForce)+10){
                                                return <span style={{color: 'rgb(68 243 68)'}}>{data+'↑'}</span>
                                            }else if(parseFloat(designForce)+10 < data){
                                                return <span style={{color: '#f72222'}}>{data+'↑'}</span>
                                            }else if(designForce > data){
                                                return <span style={{color: '#f72222'}}>{data+'↓'}</span>
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
                                required: true,
                                field: 'tensionA1',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 18 }
                                    }
                                }
                            }
                        },
                        {
                            isInTable: false,
                            form: {
                                type: 'number',
                                label: 'A2张拉力',
                                required: true,
                                field: 'tensionA2',
                                spanForm: 12,
                                formItemLayout: {
                                    labelCol: {
                                        sm: { span: 6 }
                                    },
                                    wrapperCol: {
                                        sm: { span: 18 }
                                    }
                                }
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
                                align: 'center',
                                title: '偏差',
                                dataIndex: 'offset',
                                key: 'offset',
                                render:(data,rows,index)=>{
                                    let designExpansion= rows.designExpansion;
                                    let actualExpansion= rows.actualExpansion;
                                    data = ((actualExpansion-designExpansion)/designExpansion)*100;
                                    if(-6 <= data && data <= 6){
                                        return <span style={{color: 'rgb(68 243 68)'}}>{data.toFixed(2)+"%"+(data>0?'↑':'↓')}</span>
                                    }else if(6 < data){
                                        return <span style={{color: '#f72222'}}>{data.toFixed(2)+'%↑'}</span>
                                    }else if(-6 > data){
                                        return <span style={{color: '#f72222'}}>{data.toFixed(2)+'%↓'}</span>
                                    }
                                }
                            },
                            form: {
                                type: 'string',
                                required: true,
                                field: 'offset',
                            }
                        }
                    ]}
                />
            </div>)
    }
}
export default Page;