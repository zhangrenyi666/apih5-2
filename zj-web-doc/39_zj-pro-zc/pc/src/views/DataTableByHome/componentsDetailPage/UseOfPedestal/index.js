import React from 'react';
import Apih5 from 'qnn-apih5';
import QnnTable from 'qnn-table';
import style from "./style.less"

class Page extends Apih5 {

    state = {
        dataOne: [],
        dataTwo: [],

        height: (window.innerHeight - 64 - 28 * 2 - 32 * 2) - 30
    }

    commConfig = {
        fetch: this.props.myFetch,
        limit: 999,
        curPage: 1,
        paginationConfig: false,
        firstRowIsSearch: false,
        isShowRowSelect: false,
        formConfig: [
            {
                table: {
                    align: 'center',
                    title: '预制台座编号',
                    dataIndex: 'prefabPedestalNo',
                    tdEdit: false,
                    key: 'prefabPedestalNo'
                },
                isInForm: false
            },
            {
                table: {
                    align: 'center',
                    title: '台座状态',
                    dataIndex: 'pedestalState',
                    key: 'pedestalState',
                    render: (data) => {
                        let color = '';
                        if (data === '0') {
                            color = 'green';
                        } else if (data === '1') {
                            color = 'red';
                        } else if (data === '2') {
                            color = '#0bd0d9';
                        } else {

                        }
                        let str = [
                            {
                                label: "使用中",
                                value: "0",
                            },
                            {
                                label: "占用中",
                                value: "1",
                            },
                            {
                                label: "闲置中",
                                value: "2",
                            }
                        ].filter(item => item.value === data)[0]?.label;

                        return <span style={{
                            color: color
                        }}>{str}</span>
                    },
                },
                isInForm: false
            },
            {
                table: {
                    align: 'center',
                    title: '备注',
                    width: 200,
                    dataIndex: 'remarks',
                    key: 'remarks',
                },
                isInForm: false
            }
        ]
    }

    componentDidMount() {
        this.apih5.fetch('getZjProZcTbeamPedestalUseConditionList', {
            typeFlag: '0'
        }).then(({ data, success }) => {
            if (success) {
                this.setState({
                    dataOne: data[0]?.zjProZcTbeamPedestalUseConditionList || []
                })
            }
        })
        this.apih5.fetch('getZjProZcTbeamPedestalUseConditionList', {
            typeFlag: '1'
        }).then(({ data, success }) => {
            if (success) {
                this.setState({
                    dataTwo: data[0]?.zjProZcTbeamPedestalUseConditionList || []
                })
            }
        })


        this.resize()
        window.addEventListener('resize', this.resize, false)
    }

    componentWillUnmount() {
        window.removeEventListener('resize', this.resize)
    }
    resize = () => {
        this.setState({
            height: (window.innerHeight - 64 - 28 * 2 - 32 * 2) - 30
        })
    }


    render() {
        const { height } = this.state;
        return (
            <div className={style.page}>
                <div className={style.title}>
                    台座使用情况
                </div>
                <div className={style.con}>
                    <div className={style.left}>
                        <div className={style.title}>
                            预制台座使用情况表
                     </div>
                        <QnnTable
                            fetch={this.props.myFetch}
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { this.qnnTable = me }}
                            method={{}}
                            componentsKey={{}}
                            data={this.state.dataOne}
                            antd={{
                                rowKey: 'tbeamPedestalUseConditionId',
                                size: 'small',
                                scroll: {
                                    y: height,
                                }
                            }}
                            {...this.commConfig}
                        />
                    </div>
                    <div className={style.right}>
                        <div className={style.title}>
                            存梁台座使用情况列表
                     </div>
                        <QnnTable
                            fetch={this.props.myFetch}
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                            wrappedComponentRef={(me) => { this.qnnTable = me }}
                            method={{}}
                            componentsKey={{}}
                            antd={{
                                rowKey: 'tbeamPedestalUseConditionId',
                                size: 'small',
                                scroll: {
                                    y: height,
                                }
                            }}
                            data={this.state.dataTwo}
                            {...this.commConfig}
                        />
                    </div>
                </div>
            </div>)
    }
}
export default Page;