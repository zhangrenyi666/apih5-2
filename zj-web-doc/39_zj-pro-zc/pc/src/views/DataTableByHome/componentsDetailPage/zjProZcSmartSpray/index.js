import React from 'react';
import Apih5 from 'qnn-apih5';
import QnnTable from 'qnn-table';
import style from "./style.less"

class Page extends Apih5 {
    state = {};

    config = () => {
        return {
            fetchConfig: {
                apiName: "getZjProZcSmartSprayList"
            },
            antd: {
                rowKey: "smartSprayId",
                size: "small",
                scroll: {
                    y: this.state.tableHeight
                }
            },
            drawerConfig: {
                width: 1000
            },
            firstRowIsSearch: false,
            formConfig: [
                {
                    isInTable: false,
                    form: {
                        type: "string",
                        field: 'smartSprayId',
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
                        render: (data, rows, index) => {
                            return index + 1;
                        }
                    },
                },
                {
                    isInForm: false,
                    table: {
                        align: 'center',
                        title: '台座编号',
                        dataIndex: 'pedestalNo'
                    }
                },
                {
                    isInForm: false,
                    table: {
                        align: 'center',
                        title: '日期',
                        format: 'YYYY-MM-DD',
                        dataIndex: 'dateDate'
                    }
                },
                {
                    isInForm: false,
                    table: {
                        align: 'center',
                        title: '时间',
                        // format:'HH:MM',
                        dataIndex: 'timeStr'
                    }
                },
                {
                    isInForm: false,
                    table: {
                        align: 'center',
                        title: '养护时长(分)',
                        dataIndex: 'curingTime'
                    }
                }
            ]
        }
    };

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
    render() {
        return (
            <div className={style.page}>
                <div className={style.title}>
                    喷淋养护信息
                </div>
                <QnnTable
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { this.qnnTable = me }}
                    method={{}}
                    componentsKey={{}}
                    isShowRowSelect={false}
                    // {...window.zjProZcSmartSpray}
                    {...this.config()}
                />
            </div>)
    }
}
export default Page;