import React from 'react';
import Apih5 from 'qnn-apih5';
import QnnTable from 'qnn-table';
import style from "./style.less"

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
            tableHeight: window.innerHeight - 200
        })
    };
    config = ()=>{
        return {
            //获取数据
            fetchConfig: {
                apiName: 'getZjProZcTbeamPrefabricateInformationList'
            },
            //checkbox
            antd: {
                rowKey: function (row) {
                    return row.smartSprayId
                },
                size: 'small',
                scroll: {
                    y: this.state.tableHeight
                }
            },
            //drawer:抽屉
            drawerConfig: {
                width: '1100px'
            },
            //分页
            paginationConfig: {
                position: 'bottom'
            },
    
            firstRowIsSearch: false,
            isShowRowSelect: false
        }
    };


    render() {
        return (
            <div className={style.page}>
                <div className={style.title}>
                    T梁预制情况
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
                        size: "small",
                        scroll: {
                            y: this.state.tableHeight
                        }
                    }}
                    isShowRowSelect={false}

                    {...this.config()}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                label: '主键id',
                                field: 'smartSprayId',
                                hide: true
                            },
                        },
                        {
                            table: {
                                title: '构建编号',
                                dataIndex: 'tbeamId',
                                key: 'TbeamId',
                                align: 'center',
                                width: 100
                            },
                            form: {
                                label: '构建编号',
                                field: 'TbeamId',
                                type: 'string',
                                required: true
                            },
                        },
                        {
                            table: {
                                title: '梁长m',
                                dataIndex: 'beamSize',
                                key: 'beamSize',
                                align: 'center',
                            },
                            form: {
                                label: '梁长m',
                                field: 'beamSize',
                                type: 'number',
                                min: 0,
                                precision: 2,
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '混凝土标号',
                                dataIndex: 'concreteNumber',
                                key: 'concreteNumber'
                            },
                            form: {
                                label: '混凝土标号',
                                type: 'number',
                                field: 'concreteNumber',
                                min: 0,
                                precision: 2,
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '混凝土方量m³',
                                dataIndex: 'concreteVolume',
                                key: 'concreteVolume'
                            },
                            form: {
                                label: '混凝土方量m³',
                                type: 'number',
                                field: 'concreteVolume',
                                precision: 2,
                                min: 0,
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '钢筋绑扎时间',
                                dataIndex: 'colligationTime',
                                key: 'colligationTime',
                                format:"YYYY-MM-DD",
                            },
                            form: {
                                align: 'center',
                                label: '钢筋绑扎时间',
                                type: "date",
                                // showTime:false,
                                // format:"YYYY-MM-DD"
                                field: 'colligationTime',
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '混凝土浇筑时间',
                                dataIndex: 'concretingTime',
                                key: 'concretingTime',
                                
                                format:"YYYY-MM-DD",
                            },
                            form: {
                                label: '混凝土浇筑时间',
                                type: "date",
                                // showTime:false,
                                // format:"YYYY-MM-DD"
                                field: 'concretingTime',
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '张拉时间',
                                dataIndex: 'stretchDrawTime',
                                key: 'stretchDrawTime',
                                
                                format:"YYYY-MM-DD",
                            },
                            form: {
                                label: '张拉时间',
                                type: "date",
                                // showTime:false,
                                // format:"YYYY-MM-DD"
                                field: 'stretchDrawTime',
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '压浆时间',
                                dataIndex: 'mudjackTime',
                                format:"YYYY-MM-DD",
                                key: 'mudjackTime'
                            },
                            form: {
                                label: '压浆时间',
                                type: "date",
                                // showTime:false,
                                // format:"YYYY-MM-DD"
                                field: 'mudjackTime',
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '架设时间',
                                dataIndex: 'setUpTime',
                                format:"YYYY-MM-DD",
                                key: 'setUpTime'
                            },
                            form: {
                                label: '架设时间',
                                type: "date",
                                // showTime:false,
                                // format:"YYYY-MM-DD"
                                field: 'setUpTime',
                                required: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '状态',
                                dataIndex: 'tbeamPrefabricateTypeId',
                                key: 'tbeamPrefabricateTypeId',
                                type: 'select'
                            },
                            form: {
                                label: '状态',
                                type: "select",
                                // showTime:false,
                                // format:"YYYY-MM-DD"
                                field: 'tbeamPrefabricateTypeId',
                                required: true,
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",//???
                                    otherParams: {
                                        itemId: "tLiangMingXi"
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId"
                                }
                            }
                        }

                    ]}
                />
            </div>)
    }
}
export default Page;