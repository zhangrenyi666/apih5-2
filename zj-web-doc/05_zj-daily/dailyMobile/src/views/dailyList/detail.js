import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import s from "./style.less";
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form, Divider } from 'antd';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            proFillId: props.match.params.proFillId || '',
        };
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { proFillId } = this.state;
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}dailyList`));
                    }}
                >
                    {"日报详情"}
                </NavBar>
                <div style={{height: window.innerHeight - 45}}>
                    <QnnForm
                        {...this.props}
                        match={this.props.match}
                        fetch={this.props.myFetch} 
		                upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        fetchConfig={{//表格的ajax配置
                            apiName: 'getZjDailyProFillDetails',
                            otherParams: {
                                proFillId: proFillId
                            }
                        }}
                        formConfig = {[
                            {
                                label:'项目名称',
                                type: 'string',
                                field:'proName',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label:'收费站',
                                type: 'string',
                                field:'tollStation',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label:'填报日期',
                                type: 'date',
                                field:'fillDate',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label:'现金收入(万元)',
                                type: 'number',
                                field:'cashTotal',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'非现金收入(万元)',
                                type: 'number',
                                field:'unCash',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'当日合计收入(万元)',
                                type: 'number',
                                field:'cashAllTotal',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'本年度累计收入(万元)',
                                type: 'number',
                                field:'ext2',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'本年度日均收入(万元)',
                                type: 'number',
                                field:'ext3',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'工可日收入(万元)',
                                type: 'number',
                                field:'ext1',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'实际/工可(%)',
                                type: 'string',
                                field:'ext4',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'当日入口车次',
                                type: 'number',
                                field:'entrTotal',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'当日出口车次',
                                type: 'number',
                                field:'exportTotal',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'当日绿通车车次',
                                type: 'number',
                                field:'green1',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'当日绿通车减免金额',
                                type: 'number',
                                field:'green2',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            },
                            {
                                label:'当日合计车流量',
                                type: 'number',
                                field:'allTotal',
                                disabled: true,
                                placeholder: '请输入',
                                formItemLayout: {
                                    labelCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    },
                                    wrapperCol: {
                                        xs: { span: 12 },
                                        sm: { span: 12 }
                                    }
                                }
                            }
                        ]}
                    />
                </div>
        </div>
        )
    }
}
export default Form.create()(Index)