import React, { Component } from 'react';
import QnnForm from "../../../modules/qnn-form";
import QnnTable from "../../../modules/qnn-table";
import s from './index.less';
import { Button, Spin, Modal } from "antd";
import $ from 'jquery';
import downLoad from "../../../modules/download";
const confirm = Modal.confirm;
class index extends Component {
    state = {
        loading: false,
        leftTableData: [],
        leftTableConfig: [],
        tableHeight: 850 * 0.5 - 70,
        companyName: '',
        areaName: this.props.areaName,
        proProcessName: this.props.proProcessName,
        proTypeName: this.props.proTypeName
    };
    componentDidMount() {
        this.resize();
        window.addEventListener("resize", this.resize, false);
        this.refresh();
    }

    //滚动列表数据
    scrollTableData = () => {
        const timer = 1 * 2000; //动画间隔
        const aniFn = ($dom, dataName, tdHeight = 51) => {
            const dataList = this.state[dataName];
            let bodyHeight = $(`.ProjectList .ant-table-body`).height();
            if ((dataList.length * 51) <= (bodyHeight + 2)) {
                clearInterval(this.scrollLeftTableDataTimer);
                return;
            }
            $dom.css({
                transition: `${timer / 1000}s`,
                transform: `translateY(-${tdHeight}px)`
            });
            this.scrollLeftTableDataSetTimeout = setTimeout(() => {
                let delEle = dataList.shift();
                dataList.push(delEle);
                this.setState({
                    leftTableData: dataList
                }, () => {
                    $dom.css({
                        transition: `0s`,
                        transform: `translateY(-0px)`
                    })
                })
            }, timer)
        }

        //执行两表格的动画 
        clearInterval(this.scrollLeftTableDataTimer);
        this.scrollLeftTableDataTimer = setInterval(() => aniFn($(`.ProjectList .ant-table-body table`).eq(0), "leftTableData"), timer * 2);

    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.resize);
        clearInterval(this.scrollLeftTableDataTimer);
    }
    resize = () => {
        this.setState({
            tableHeight: 850 * 0.5 - 70,
        })
    };
    componentWillReceiveProps(nextProps) {
        const { areaName, proProcessName, proTypeName } = this.state;
        if (areaName !== nextProps.areaName || proProcessName !== nextProps.proProcessName || proTypeName !== nextProps.proTypeName) {
            clearInterval(this.scrollLeftTableDataTimer);
            clearTimeout(this.scrollLeftTableDataSetTimeout);
            this.setState({
                areaName: nextProps.areaName,
                proProcessName: nextProps.proProcessName,
                proTypeName: nextProps.proTypeName,
                leftTableData: [],
                leftTableConfig: []
            }, () => {
                this.refresh();
            })
        }
    }
    refresh = () => {
        this.setState({
            loading: true
        })
        let { areaName, companyName, proProcessName, proTypeName } = this.state;
        this.props.myFetch('getHomeRegionalOverviewProList', { areaName: areaName, companyName: companyName, proProcessName: proProcessName, proTypeName: proTypeName }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        leftTableData: data.map((item, index) => {
                            return {
                                ...item,
                                ranking: index + 1
                            }
                        }),
                        leftTableConfig: [
                            {
                                table: {
                                    title: '项目简称',
                                    dataIndex: 'projectShortName',
                                    width: 50,
                                    tooltip:15,
                                    align: "center",
                                    render: (rowData) => {
                                        return rowData
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '投资总额',
                                    dataIndex: 'amount1',
                                    width: 50,
                                    align: "center",
                                    render:(data) => {
                                        if(data){
                                           return data.toFixed(2); 
                                        }else{
                                            return '0.00';
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '建安费',
                                    dataIndex: 'amount3',
                                    width: 50,
                                    align: "center",
                                    render:(data) => {
                                        if(data){
                                           return data.toFixed(2); 
                                        }else{
                                            return '0.00';
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: '我方持股比例',
                                    dataIndex: 'company7',
                                    width: 70,
                                    align: "center",
                                    render:(data) => {
                                        if(data){
                                           return data.toFixed(2) + '%'; 
                                        }else{
                                            return '0.00%';
                                        }
                                    }
                                }
                            },
                            {
                                table: {
                                    title: proTypeName ? '<span style="color:#cb4b37">项目类型</span>' : '项目类型',
                                    dataIndex: 'proTypeName',
                                    width: 50,
                                    tooltip:15,
                                    align: "center"
                                }
                            },
                            {
                                table: {
                                    title: proProcessName ? '<span style="color:#cb4b37">项目进展</span>' : '项目进展',
                                    dataIndex: 'proProcessName',
                                    width: 50,
                                    tooltip:15,
                                    align: "center"
                                }
                            },
                            {
                                table: {
                                    title: companyName ? '<span style="color:#cb4b37">管理单位</span>' : '管理单位',
                                    dataIndex: 'companyName',
                                    width: 50,
                                    tooltip:15,
                                    align: "center"
                                }
                            },
                            {
                                table: {
                                    title: areaName ? '<span style="color:#cb4b37">项目所在地</span>' : '项目所在地',
                                    dataIndex: 'areaName',
                                    width: 60,
                                    tooltip:15,
                                    align: "center"
                                }
                            },

                        ]
                    }, () => {
                        this.scrollTableData(this.state.leftTableData);
                    })
                } else {
                    this.setState({
                        loading: false,
                    })
                }
            }
        );
    }
    render() {
        const {
            loading,
            leftTableData, leftTableConfig, tableHeight, areaName, proProcessName, proTypeName, companyName
        } = this.state;
        const qnnTableCommConfig = {
            fetch: this.props.myFetch,
            paginationConfig: false,
            isShowRowSelect: false,
        }
        return (
            <div className={s.ProjectList}>
                <div className={s.rightTopOne}>
                    <div className={s.rightTopOneL}>
                        项目列表
                    </div>
                    <div className={s.rightTopOneC}>
                        单位：万元
                    </div>
                    <div className={s.rightTopOneRR}>
                        <Button size="small" style={{ width: '70px', height: '30px' }} type="primary" onClick={() => {
                            const {
                                loginAndLogoutInfo: {
                                    loginInfo: { token }
                                },
                                myPublic: { domain }
                            } = this.props;
                            let body = {
                                fileName: '项目列表',
                                areaName: areaName,
                                companyName: companyName,
                                proProcessName: proProcessName,
                                proTypeName: proTypeName
                            }
                            let URL = `${domain + "exportHomeRegionalOverviewProList"}`;
                            confirm({
                                content: '确定导出数据吗?',
                                centered: true,
                                onOk: () => {
                                    downLoad(URL, body, { token });
                                }
                            });
                        }}>导出</Button>
                    </div>
                    <div className={s.rightTopOneR}>
                        <QnnForm
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            wrappedComponentRef={(me) => {
                                this.form = me;
                            }}
                            formItemLayout={{
                                labelCol: {
                                    xs: { span: 8 },
                                    sm: { span: 8 }
                                },
                                wrapperCol: {
                                    xs: { span: 16 },
                                    sm: { span: 16 }
                                }
                            }}
                            formConfig={[
                                {
                                    label: '管理单位',
                                    field: 'companyName',
                                    type: 'select',
                                    optionConfig: {
                                        label: 'companyName',
                                        value: 'companyName'
                                    },
                                    fetchConfig: {
                                        apiName: 'getHomeProgressPlaningComname'
                                    },
                                    showSearch: true,
                                    onMouseEnter: () => {
                                        clearInterval(this.scrollLeftTableDataTimer);
                                    },
                                    onMouseLeave: () => {
                                        this.scrollTableData();
                                    },
                                    onChange: (val) => {
                                        this.setState({
                                            leftTableConfig: [],
                                            leftTableData:[],
                                            companyName: val
                                        }, () => {
                                            this.refresh();
                                        })
                                    },
                                    size: 'small',
                                    labelStyle: {
                                        color: 'rgba(255,255,255,0.5)'
                                    },
                                    hide: areaName ? true : false,
                                    placeholder: '请选择'
                                }
                            ]}
                        />
                    </div>
                </div>
                <div className={s.rightTopTow}>
                    <div className={`${s.rightTopTowTable}  ProjectList`}>
                        <Spin spinning={loading}>
                            {leftTableConfig.length ? <QnnTable
                                formConfig={leftTableConfig}
                                data={leftTableData}
                                antd={{
                                    rowKey: "ranking",
                                    scroll: {
                                        y: tableHeight
                                    }
                                }}
                                {...qnnTableCommConfig}
                            /> : null}
                        </Spin>
                    </div>
                </div>
            </div>
        )
    }
}
export default index;