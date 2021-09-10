import React, { Component } from "react";
import QnnForm from '../modules/qnn-table/qnn-form';
import { message as Msg } from 'antd';
import s from './style.less';
class index extends Component {
    state = {
        data:null
    }
    componentDidMount(){
        this.props.myFetch('getValidZjXmJxPeripheryScoreRule', {}).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data:data
                })
            }else{
                this.setState({
                    data:{}
                })
            }
        });
    }
    render() {
        const { data } = this.state;
        return (
            <div className={s.root}>
                <div style={{ textAlign: 'center', fontSize: '20px',width:'50%' }}>周边考核分数规则(每n人各去掉1人)</div>
                <div style={{width:'50%'}}>
                    {data ? <QnnForm
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        data={data}
                        formConfig={[
                            {
                                type: 'component',
                                field: 'diyfz1',
                                span:6,
                                Component: obj => {
                                    return (
                                        <div style={{lineHeight:'56px',textAlign:'right'}}>
                                            副职:
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'number',
                                field: 'diyfzNumber',
                                initialValue:1,
                                placeholder: '请输入',
                                disabled: true,
                                span:4
                            },
                            {
                                type: 'component',
                                field: 'diyfz2',
                                span:1,
                                Component: obj => {
                                    return (
                                        <div style={{fontSize:'20px',lineHeight:'56px',textAlign:'center'}}>
                                            /
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'number',
                                field: 'deputyDivisor',
                                placeholder: '人数',
                                required: true,
                                span:4
                            },
                            {
                                type: 'component',
                                field: 'diyfz3',
                                span:9,
                                Component: obj => {
                                    return (
                                        <div style={{lineHeight:'56px'}}>
                                            各去掉人数/每n人数
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'component',
                                field: 'diybm1',
                                span:6,
                                Component: obj => {
                                    return (
                                        <div style={{lineHeight:'56px',textAlign:'right'}}>
                                            部门负责人:
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'number',
                                field: 'diybmNumber',
                                initialValue:1,
                                placeholder: '请输入',
                                disabled: true,
                                span:4
                            },
                            {
                                type: 'component',
                                field: 'diybm2',
                                span:1,
                                Component: obj => {
                                    return (
                                        <div style={{fontSize:'20px',lineHeight:'56px',textAlign:'center'}}>
                                            /
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'number',
                                field: 'leaderDivisor',
                                placeholder: '人数',
                                required: true,
                                span:4
                            },
                            {
                                type: 'component',
                                field: 'diybm3',
                                span:9,
                                Component: obj => {
                                    return (
                                        <div style={{lineHeight:'56px'}}>
                                            各去掉人数/每n人数
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'component',
                                field: 'diypt1',
                                span:6,
                                Component: obj => {
                                    return (
                                        <div style={{lineHeight:'56px',textAlign:'right'}}>
                                            普通员工:
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'number',
                                field: 'diyptNumber',
                                initialValue:1,
                                placeholder: '请输入',
                                disabled: true,
                                span:4
                            },
                            {
                                type: 'component',
                                field: 'diypt2',
                                span:1,
                                Component: obj => {
                                    return (
                                        <div style={{fontSize:'20px',lineHeight:'56px',textAlign:'center'}}>
                                            /
                                        </div>
                                    );
                                }
                            },
                            {
                                type: 'number',
                                field: 'employeeDivisor',
                                placeholder: '人数',
                                required: true,
                                span:4
                            },
                            {
                                type: 'component',
                                field: 'diypt3',
                                span:9,
                                Component: obj => {
                                    return (
                                        <div style={{lineHeight:'56px'}}>
                                            各去掉人数/每n人数
                                        </div>
                                    );
                                }
                            }
                        ]}
                        btns={[
                            {
                                label: '完成设置',
                                field: 'diyprimary',
                                type: 'primary',
                                onClick:(obj) => {
                                    this.props.myFetch('setUpZjXmJxPeripheryScoreRule', obj.values).then(({ success, message, data }) => {
                                        if (success) {
                                            Msg.success(message);
                                        }else{
                                            Msg.error(message);
                                        }
                                    });
                                }
                            }
                        ]}
                    /> : null}
                </div>

            </div>
        );
    }
}

export default index;