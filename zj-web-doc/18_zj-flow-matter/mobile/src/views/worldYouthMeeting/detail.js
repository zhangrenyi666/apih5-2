import React, { Component } from 'react';
import QnnForm from '../modules/qnn-table/qnn-form';
import { NavBar, Icon } from "antd-mobile";
import { push } from 'react-router-redux';
import { Form, Modal } from 'antd';
import Particular from './particular';
import { Toast } from 'antd-mobile';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            fallInDeptId: props.match.params.fallInDeptId || '',
            fallInYear: props.match.params.fallInYear || '',
            approvalState:props.match.params.approvalState || '',
            visible:false,
            title:'',
            approvalResult:'',
            approvalOpinion:''
        }
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        const { fallInDeptId, fallInYear, visible, title, approvalResult, approvalState } = this.state;
        let { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div>
                <NavBar
                    style={{ width: "100%" }}
                    mode="dark"
                    icon={<Icon type="left" />}
                    onLeftClick={() => {
                        dispatch(push(`${mainModule}worldYouthMeeting`));
                    }}
                >
                    {"审批详情"}
                </NavBar>
                <div>
                    <QnnForm
                        form={this.props.form}
                        history={this.props.history}
                        match={this.props.match}
                        fetch={this.props.myFetch}
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        qnnFormContextHeight={document.documentElement.clientHeight - 90}
                        tabs={[
                            {
                                field: "form1",
                                name: "qnnForm",
                                title: "基础数据",
                                content: {
                                    fetchConfig: {
                                        apiName: 'getZjMeetingPlanFallInInfoDetailByDeptId',
                                        otherParams: {
                                            fallInDeptId: fallInDeptId
                                        }
                                    },
                                    formConfig: [
                                        {
                                            field: 'fallInDeptId',
                                            type: 'string',
                                            placeholder: '请输入',
                                            hide: true
                                        },
                                        {
                                            label: '年度',
                                            field: 'fallInYear',
                                            type: 'string',
                                            placeholder: '请输入',
                                            disabled: true,
                                        },
                                        {
                                            label: '主办部门',
                                            field: 'oaUserName',
                                            type: 'string',
                                            placeholder: '请输入',
                                            disabled: true,
                                        },
                                        {
                                            label: '填报人',
                                            field: 'fallInUser',
                                            type: 'string',
                                            placeholder: '请输入',
                                            disabled: true,
                                        },
                                        {
                                            label: '填报时间',
                                            field: 'fallInTime',
                                            type: 'date',
                                            placeholder: '请选择',
                                            format:"YYYY-MM-DD HH:mm:ss",
                                            disabled: true,
                                        },
                                        {
                                            label: '备注',
                                            field: 'fallInRemakes',
                                            type: 'string',
                                            placeholder: '请输入',
                                            disabled: true,
                                        }
                                    ],
                                    btns: approvalState === '1' ? [
                                        {
                                            label: '驳回',
                                            type: 'primary', //primary dashed danger
                                            onClick: (obj) => { //此时里面会多一个 response
                                                this.setState({
                                                    title: '驳回',
                                                    approvalResult:'0',
                                                    visible: true
                                                });
                                            }
                                        },
                                        {
                                            label: '同意',
                                            type: 'primary', //primary dashed danger
                                            onClick: (obj) => { //此时里面会多一个 response
                                                this.setState({
                                                    title: '同意',
                                                    approvalResult:'1',
                                                    visible: true
                                                });
                                            }
                                        }
                                    ] : []
                                }
                            },
                            {
                                //自定義組件
                                field: "form2",
                                name: "Detail",
                                title: "详细信息",
                                content: props => {
                                    return <Particular fallInDeptId={fallInDeptId} fallInYear={fallInYear} approvalState={approvalState}  {...this.props} />;
                                }
                            }
                        ]}
                    />
                </div>
                {visible ? <div>
                    <Modal
                        width={'90%'}
                        style={{ paddingBottom: '0', top: '0' }}
                        title={title}
                        visible={visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', overflow: 'hidden' }}
                        centered={true}
                        closable={false}
                        maskClosable={false}
                        wrapClassName={'modals'}
                    > 
                        <QnnForm
                            form={this.props.form}
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            upload={this.props.myUpload} //必须返回一个promise
                            //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
                            headers={{
                                token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            formConfig={[
                                {
                                    label: '审批意见',
                                    field: 'approvalOpinion',
                                    type: 'textarea',
                                    initialValue:title,
                                    placeholder:'请输入',
                                    onChange:(value) => {
                                        this.setState({
                                            approvalOpinion:value
                                        })
                                    },
                                    formItemLayout:{
                                        labelCol: {
                                            xs: { span: 24 },
                                            sm: { span: 24 }
                                        },
                                        wrapperCol: {
                                            xs: { span: 24 },
                                            sm: { span: 24 }
                                        }
                                    }
                                },
                            ]}
                            btns={[
                                {
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    type: "primary",
                                    label: "确定",
                                    onClick: (obj) => {
                                        let body = {
                                            fallInDeptId,
                                            approvalResult,
                                            approvalOpinion:this.state.approvalOpinion
                                        }
                                        obj.btnfns.myFetch('zjMeetingPlanSubmitApproval', body, ({ data, success, message }) => {
                                            if (success) {
                                                Toast.success(message);
                                                dispatch(push(`${mainModule}worldYouthMeeting`));
                                            } else {
                                                Toast.fail(message);
                                            }
                                        });
                                    }
                                }
                            ]}
                        />
                    </Modal>
                </div> : null}
            </div>
        )
    }
}
export default Form.create()(Index)