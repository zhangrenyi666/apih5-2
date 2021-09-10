import React from 'react';
import Apih5 from 'qnn-apih5';
import QnnTable from 'qnn-table';
import style from "./style.less"

class Page extends Apih5 {
    state = {
        tableBodyHeight: document.documentElement.clientHeight - 64 - 28 - 48,
    };

    componentDidMount() {
        window.addEventListener("resize", this.resize, false);
    }
    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
    }
    resize = () => {
        this.setState({
            tableBodyHeight: document.documentElement.clientHeight - 64 - 28 - 48,
        })
    };

    render() {
        return (
            <div className={style.page}>
                <div className={style.title}>
                    人员安全积分排名
                </div>
                <QnnTable
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    wrappedComponentRef={(me) => { this.qnnTable = me }}
                    method={{}}
                    componentsKey={{}}
                    isShowRowSelect={false}
                    {...this.config}
                    antd={{
                        rowKey: 'safeScoreId',
                        size: "small",
                        scroll: {
                            y: this.state.tableBodyHeight
                        }
                    }}
                    limit={999}
                    fetchConfig={{
                        apiName: 'getZjProZcSafeScoreListByNowScore',

                    }}
                    formConfig={[
                        {
                            table: {
                                title: '排名',
                                dataIndex: 'ranking',
                                width: 50,
                                align: "center",
                                render: (data, rowData, index) => {
                                    return index + 1
                                }
                            }
                        },
                        {
                            table: {
                                title: '头像',
                                width: 80,
                                dataIndex: 'headUrl',
                                key: 'headUrl',
                                align: 'center',
                                render: (data, rowData) => {
                                    return <div style={{ textAlign: 'center' }}><img alt="img" style={{ width: '60px', margin: '0px auto' }} src={data} /></div>
                                }
                            },
                            form: {
                                type: 'images',
                                label: '附件',
                                field: 'zjProZcFileList',
                                required: true,
                                wrapperStyle: {},
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                },
                                accept: 'image/jpeg',
                                max: 1
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '人员编号',
                                width: 120,
                                dataIndex: 'personNo',
                                key: 'personNo'
                            },
                            form: {
                                type: 'string',
                                field: 'personNo',
                                addDisabled: true,
                                editDisabled: true
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '人员姓名',
                                width: 120,
                                dataIndex: 'personName',
                                key: 'personName'
                            },
                            form: {
                                type: 'string',
                                field: 'personName'
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '所属班组',
                                width: 160,
                                dataIndex: 'teamId',
                                key: 'teamId',
                                type: 'select',
                            },
                            form: {
                                type: 'select',
                                field: 'teamId',
                                fetchConfig: {
                                    apiName: "getBaseCodeSelect",//???
                                    otherParams: {
                                        itemId: "suoShuBanZu"
                                    }
                                },
                                optionConfig: {
                                    label: "itemName",
                                    value: "itemId"
                                }
                            }
                        },
                        {
                            table: {
                                align: 'center',
                                title: '当前积分',
                                width: 160,
                                dataIndex: 'nowScore',
                                key: 'nowScore'
                            },
                            form: {
                                type: 'number',
                                field: 'nowScore',
                                initialValue: 100
                            }
                        },
                    ]}
                />
            </div>)
    }
}
export default Page;