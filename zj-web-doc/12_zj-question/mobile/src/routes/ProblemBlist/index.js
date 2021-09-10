import React, { Component } from "react";
import MyList from "../modules/MListA";
import { Divider, Modal } from "antd";
import { myFetch } from '../../tools';
import s from "./style.less";
import { NavBar, Icon, Button, Toast } from "antd-mobile";
import { goBack } from 'connected-react-router';
const confirm = Modal.info;
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
        };
    }
    render() {
        const { dispatch } = this.props;
        return (
            <div className={s.root}>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(goBack());
                        }}
                    >
                        {"定制清单列表"}
                    </NavBar>
                </div>
                <div
                    style={{
                        height: window.innerHeight - 45
                    }}
                >
                    <MyList
                        myFetch={myFetch} //ajax方法必须返回一个promise
                        searchKey="departmentName" //搜索时的key
                        fetchConfig={{
                            apiName: 'getZjQueApprovalListByInformationIdForWechat', //后台api
                        }}
                        {...this.props}
                        Item={props => {
                            //列表模板 props里有所有数据
                            const { rowData, rowID, projectId, projectName, questionTitle, projectOrgId } = props;
                            const item = rowData;
                            const index = rowID;
                            return (
                                <div
                                    className={s.center}
                                    key={index}
                                    style={{ color: item.readFlag == '1' ? 'green' : (item.readFlag == '2' ? '#6a0080' : '#666666') }}
                                    onClick={(e) => {
                                        e.stopPropagation();
                                        confirm({
                                            centered: true,
                                            title: `备注：`,
                                            content:item.remarks,
                                            okText: "确认",
                                            cancelText: "取消",
                                        });
                                    }}
                                >
                                    <div className={s.top}>
                                        <div className={s.topl}>部门：{item.departmentName}</div>
                                        <div className={s.topr}>序号：{Number(index) + 1}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px", backgroundColor: '#1890ff' }} />
                                    <div className={s.top}>
                                        <div className={s.topl}>类别：{item.questionClassName}</div>
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ paddingLeft: '3%' }}>
                                        检查项：{item.questionCheckItemName}
                                    </div>
                                    <Divider style={{ margin: "8px 0px" }} />
                                    <div style={{ overflow: 'hidden' }}>
                                        {item.readFlag ? <Button style={{ width: '30%', float: 'left', }} size='small' type="ghost" onClick={(e) => {
                                            e.stopPropagation();
                                            if(item.readFlag == '1'){
                                                Toast.info('该问题已通过!!!', 2);
                                            }else{
                                                Toast.info('该问题已整改无需通过!!!', 2);
                                            }                                    
                                        }}>通过</Button> : <Button style={{ width: '30%', float: 'left' }} size='small' type="ghost" onClick={(e) => {
                                            e.stopPropagation();
                                            confirm({
                                                centered: true,
                                                title: `是否通过?`,
                                                okText: "确认",
                                                cancelText: "取消",
                                                onOk: () => {
                                                    let params = {
                                                        checkDepartmentId: item.departmentId,
                                                        checkDepartmentName: item.departmentName,
                                                        checkQuestionCheckItemId: item.questionCheckItemId,
                                                        checkQuestionCheckItemName: item.questionCheckItemName,
                                                        checkQuestionClassId: item.questionClassId,
                                                        checkQuestionClassName: item.questionClassName,
                                                        checkQuestionTitleId: item.questionTitleId,
                                                        checkTitleId: item.questionTitleId,
                                                        checkProjectId: projectId
                                                    }
                                                    myFetch('addQuestionCheckQuestion', params).then(({ success, message, data }) => {
                                                        if (success) {
                                                            props.updateParent(projectId);
                                                        } else {
                                                            Toast.fail(message);
                                                        }

                                                    })
                                                },
                                            });
                                        }}>通过</Button>}
                                        <Button style={{ width: '30%', float:'left', marginLeft:'5%' }} size='small' type="primary"  onClick={(e) => {
                                            e.stopPropagation();
                                            if (item.readFlag == '2') {
                                                Toast.info('该问题已改进!!!', 2);
                                            } else {
                                                this.props.history.push(`/ProblemAddt/${item.screenId}/${projectId}/${projectName}/${questionTitle}/${projectOrgId}`);
                                            }
                                        }}>改进</Button>
                                        <Button style={{ width: '30%', float: 'right' }} size='small' type="primary" onClick={(e) => {
                                            e.stopPropagation();
                                            if (item.readFlag == '2') {
                                                Toast.info('该问题已整改!!!', 2);
                                            } else {
                                                this.props.history.push(`/ProblemAdds/${item.screenId}/${projectId}/${projectName}/${questionTitle}/${projectOrgId}`);
                                            }
                                        }}>整改</Button>
                                    </div>
                                </div>
                            );
                        }}
                    />
                </div>
            </div>
        );
    }
}
export default Index;
