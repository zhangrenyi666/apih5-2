import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import { goBack } from "connected-react-router";
import { Button } from 'antd';
import { push } from 'react-router-redux';
import s from "./style.less";
const conFig = {
    
    antd: {
        rowKey: function (row) {
            return row.policyId
        },
        size:'small'
    },
    drawerConfig: {
        width: '900px'
    },
    paginationConfig: {  
        position: 'bottom'
    },

    firstRowIsSearch: false,
    isShowRowSelect: false,
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            videoId:props.match.params.videoId
        }
    }
    
    componentDidMount() {
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        const { videoId } = this.state;
        if (videoId) {
            
        } else {
            dispatch(push(`${mainModule}PublicityVideo`));
        }
    }
    cancelBtn = () =>{
        const { dispatch } = this.props;
        dispatch(goBack());
    }
    render() {
        const { videoId } = this.state;
        return (
            <div className={s.root}>
                <div style={{marginBottom:'10px'}}><Button type="dashed" onClick={this.cancelBtn}>返回</Button></div>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch} 
		            upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig={{
                        apiName: 'getZjTzVideoNoteList',
                        otherParams: function (obj) {
                            return {
                                videoId:videoId
                            }
                        }
                    }}
                    {...conFig}
                    actionBtns={[]}
                    formItemLayout={{
                        labelCol: {
                            xs: { span: 24 },
                            sm: { span: 4 }
                        },
                        wrapperCol: {
                            xs: { span: 24 },
                            sm: { span: 20 }
                        }
                    }}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'policyId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        // {
                        //     isInSearch: false,
                        //     table: {
                        //         title: '标题',
                        //         dataIndex: 'title',
                        //         width:300,
                        //         key: 'title',
                        //     },
                        //     isInForm:false
                        // },
                        // {
                        //     isInSearch: false,
                        //     table: {
                        //         title: '所属项目',
                        //         dataIndex: 'projectName',
                        //         key: 'projectName',
                        //     },
                        //     isInForm:false
                        // },
                        {   
                            table: {
                                title: '留言',
                                dataIndex: 'note',
                                width: '70%',
                                tooltip:80,
                                key: 'note'
                            },
                            isInForm:false
                        },
                        {   
                            table: {
                                title: '日期',
                                dataIndex: 'createTime',
                                key: 'createTime',
                                format:'YYYY-MM-DD'
                            },
                            isInForm:false
                        }
                    ]}
                    
                />
            </div>
        );
    }
}

export default index;