import React, { Component } from 'react';
import s from "./style.less";
import MyList from "../modules/MList";
import { Form, Modal, Button, Divider } from 'antd';
import { push } from 'react-router-redux';
import { Toast } from 'antd-mobile';
import QnnForm from '../modules/qnn-table/qnn-form';
class Index extends Component {
    constructor(props){
        super(props);
        this.state = {
            fallInDeptId: props.fallInDeptId || '',
            fallInYear: props.fallInYear || '',
            approvalState:props.approvalState || '',
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
            <div className={s.root}> 
                <div style={{ height: document.documentElement.clientHeight - 90 }}>
                    <MyList
                        myFetch={this.props.myFetch}
                        upload={this.props.myUpload}
                        fetchConfig={{
                            apiName: 'getZjMeetingPlanFallInListFallInYear',
                            otherParams:{
                                fallInDeptId:fallInDeptId,
                                fallInYear:fallInYear
                            }
                        }}
                        Item={props => {
                            const { rowData, rowID } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    onClick={() => {
                                        
                                    }}
                                >
                                    <div style={{ paddingLeft: '3%' }}>
                                        会议名称：{item.meetingNameStr}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>会议时间：{item.meetingTimeStr}</div>
                                        <div className={s.topr}>会议形式：{item.meetingForm}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        参加对象：{item.joinObject}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        协办部门：{item.coOperationDept}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>参会人数：{item.joinNumber}</div>
                                        <div className={s.topr}>预算费用(万元)：{item.budgetaryCost}</div>
                                    </div>
                                </div>
                            );
                        }}
                    />
                </div>
                {approvalState === '1' ? <div className={s.btnCon}>
                    <Button type="primary" onClick={() => {
                        this.setState({
                            title: '驳回',
                            approvalResult:'0',
                            visible: true
                        });
                    }}>驳回</Button>
                    <Button type="primary"  onClick={() => {
                        this.setState({
                            title: '同意',
                            approvalResult:'1',
                            visible: true
                        });
                    }}>同意</Button>
                </div> : null}
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
                                    name: "cancel",
                                    type: "dashed",
                                    label: "取消",
                                    isValidate: false,
                                    onClick: (obj) => {
                                        this.setState({ visible: false });
                                    }
                                },
                                {
                                    name: "submit",
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
export default Form.create()(Index);