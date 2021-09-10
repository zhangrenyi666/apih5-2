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
            levelId: props.match.params.levelId || '',
            databaseId: props.match.params.databaseId || ''
        };
    }
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { levelId, databaseId } = this.state;
        return (
            <div className={s.root}>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}safetyStandardizationList/${levelId}`));
                    }}
                >
                    {"安全资料库管理详情"}
                </NavBar>
                <div style={{height: window.innerHeight - 45}}>
                    <QnnForm
                        {...this.props}
                        match={this.props.match}
                        fetch={this.props.myFetch} 
		                upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        fetchConfig={{//表格的ajax配置
                            apiName: 'getXmZlDatabaseDetail',
                            otherParams: {
                                databaseId: databaseId
                            }
                        }}
                        formConfig = {[
                            {
                                field: 'databaseId',
                                hide: true,
                                type: 'string',
                                placeholder: '请输入'
                            },
                            {
                                field: 'levelId',
                                hide: true,
                                type: 'string',
                                placeholder: '请输入'
                            },
                            {
                                label:'资料名称',
                                type: 'string',
                                field:'title',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label:'备注',
                                type: 'textarea',
                                field:'remarks',
                                disabled: true,
                                placeholder: '请输入',
                            },
                            {
                                label:'附件',
                                type: 'files',
                                field:'attachment',
                                disabled: true,
                                onRemove:false,
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload',
                                }
                            }
                        ]}
                        // btns = {[
                        //     {
                        //         type: 'primary',
                        //         label: '提交',
                        //         fetchConfig: {
                        //             apiName: 'updateXmZlDatabase',
                        //         },
                        //     }
                        // ]}
                    />
                </div>
        </div>
        )
    }
}
export default Form.create()(Index)