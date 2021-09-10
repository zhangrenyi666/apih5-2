import React, { Component } from "react";
import { NavBar, Icon } from "antd-mobile";
import { Modal } from 'antd';
import s from './style.less';
import { push } from 'react-router-redux';
import QnnForm from '../modules/qnn-table/qnn-form';
const confirm = Modal.confirm;
class index extends Component {
    render() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { companyId, companyName } = this.props.loginAndLogoutInfo.loginInfo.userInfo.companyList[0];
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}ScientificList/1`));
                        }}
                    >
                        {"行业科技信息新增"}
                    </NavBar>
                </div>
                <div style={{ height: window.innerHeight - 45 }}>
                    <QnnForm
                        {...this.props}
                        fetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        formConfig={[
                            {
                                field: 'deptId',
                                type: 'string',
                                initialValue: companyId,
                                placeholder: '请输入',
                                hide: true,
                            },
                            {
                                field: 'technologyIndustryFlag',
                                type: 'string',
                                initialValue: '1',
                                hide: true,
                            },
                            {
                                field:'deptName',
                                type: "string",
                                initialValue: companyName,
                                hide: true
                            },
                            {
                                label:'科技类型',
                                field: 'industryScientificInfoId',
                                type: 'select',
                                optionConfig: {
                                    label: 'itemName',
                                    value: 'itemId',
                                },
                                fetchConfig: {
                                    apiName: 'getBaseCodeSelect',
                                    otherParams: {
                                        itemId: 'wenTiLeiXing'
                                    }
                                },
                                required: true,
                                placeholder: '请选择'
                            },
                            {
                                label:'科技标题',
                                field: 'title',
                                type: 'string',
                                required: true,
                                placeholder: '请输入'
                            },
                            {
                                label: '内容',
                                field: 'content',
                                type: 'richtext',
                                fetchConfig: {
                                    //必须配置  上传图片地址
                                    uploadUrl: window.configs.domain + 'upload' //***必传
                                }
                            },
                            {
                                label: '附件',
                                field: 'attachmentList',
                                type: 'files',
                                fetchConfig: {
                                    apiName: window.configs.domain + 'upload'
                                }
                            }
                        ]}
                        btns={[
                            {
                                label: '发布',
                                field:'submit',
                                type: 'primary',
                                onClick: (obj) => {
                                    const { fetchByCb, Msg } = obj.btnCallbackFn;
                                    confirm({
                                        centered:true,
                                        title: `确认要发布这条信息吗？`,
                                        okText: "确认",
                                        cancelText: "取消",
                                        onOk: () => {
                                            fetchByCb('addZjSjConsultScientificInformation', obj.values, function ({ data, success, message }) {
                                                if (success) {
                                                    Msg.success(message);
                                                    dispatch(push(`${mainModule}ScientificList/1`));
                                                } else {
                                                    Msg.error(message);
                                                }
                                            })
                                        },
                                    });
                                }
                            }
                        ]}
                    />
                </div>
            </div>
        );
    }
}

export default index;