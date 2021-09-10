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
            fetchConfig: {
                apiName: "getZjProZcMudjackList"
            },
            antd: {
                rowKey: "mudjackId",
                size: "small",
                scroll: {
                    y: this.state.tableHeight
                }
            },
            firstRowIsSearch: false,
            isShowRowSelect: false,
            // limit: 20
        }
    };


    render() {
        return (
            <div className={style.page}>
                <div className={style.title}>
                    智能压浆监控
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
                            table: {
                                title: '梁体编号',
                                dataIndex: 'tbeamId',
                                width: 50,
                                align: "center",

                            }
                        },
                        {
                            table: {
                                title: '预应力束编号',
                                dataIndex: 'preStressedNoId',
                                width: 60,
                                align: "center", 
                                type: 'select',
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
                                dataIndex: 'mudjackDate',
                                width: 70,
                                format: 'YYYY/MM/DD',
                                align: "center"
                            }
                        },
                        {
                            table: {
                                title: '浆液配合比',
                                dataIndex: 'slurryMixRatio',
                                width: 50,
                                align: "center"
                            }
                        },
                        {
                            table: {
                                title: '浆液稠度(s)',
                                dataIndex: 'slurryConsistency',
                                width: 50,
                                align: "center"
                            }
                        },
                        // {
                        //     table: {
                        //         title: '注浆时间(s)',
                        //         dataIndex: 'groutingTime',
                        //         width: 70, 
                        //         align: "center"
                        //     }
                        // },
                        {
                            table: {
                                align: 'center',
                                title: '注浆压力(Mpa)',
                                dataIndex: 'groutingPress',
                                width: 50,
                            }
                        },
                        {
                            table: {
                                title: '保压压力(Mpa)',
                                dataIndex: 'pulpPress',
                                flxed: 'left',
                                width: 50,
                                align: "center"
                            }
                        },
                        {
                            table: {
                                title: '保压时间(s)',
                                dataIndex: 'dwellTime',
                                width: 50, 
                                align: "center"
                            }
                        },
                    ]}
                />
            </div>)
    }
}
export default Page;