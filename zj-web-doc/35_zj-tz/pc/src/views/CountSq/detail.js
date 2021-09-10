import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";
import { push } from "react-router-redux";
class index extends Component {
    constructor(props){
        super(props);
        this.state = {
            projectId:props.match.params.projectId || ''
        }
    }
    render() {
        const { projectId } = this.state;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    fetchConfig = {{
                        apiName: 'getZxHwAqHiddenDangerList',
                        otherParams:{
                            levelId:projectId
                        }
                    }}
                    actionBtns={[
                        {
                            name: 'goback',
                            type: 'dashed',
                            label: '返回',
                            isValidate: false,
                            onClick: (obj) => {
                                const { mainModule } = obj.props.myPublic.appInfo;
                                obj.props.dispatch(
                                    push(`${mainModule}CountSq`)
                                )
                            }
                        }
                    ]}
                    formConfig = {[
                        {
                            isInTable: false,
                            form: {
                                field: 'dangerId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        {   
                            table: {
                                title: '工程部位',
                                dataIndex: 'dangerTitle',
                                key: 'dangerTitle', 
                                winth:150
                            },
                            isForm:false
                        },
                        {   
                            table: {
                                title: '隐患级别',
                                dataIndex: 'dangerLevel',
                                key: 'dangerLevel', 
                                winth:150,
								  render:(val)=>{
                    return {
                        "0":"轻微",
                        "1":"可接受",
                        "2":"较大",
                        "3":"重大"                       
                    }[val]
                }
                            },
                            isForm:false
                        },
						 {   
            table: {
                title: '隐患类型',
                dataIndex: 'dangerType',
                key: 'dangerType',
                winth:200,
                render:(val)=>{
                    return {
                        "100":"安全管理",
                        "101":"文明施工",
                        "102":"临边防护",
                        "103":"高处作业",
                        "104":"基坑支护",
                        "105":"模板工程",
                        "106":"施工机具",
                        "107":"交通安全",
                        "108":"个体防护",
                        "109":"起重吊装",
                        "110":"施工用电",
                        "111":"消防防火",
                        "112":"消防防火"
                    }[val]
                }
            },
            isForm:false
        },
						 {   
                            table: {
                                title: '隐患描述',
                                dataIndex: 'dangerContent',
                                key: 'dangerContent',
                                winth:200
                            },
                            isForm:false
                        },
						 {   
                            table: {
                                title: '检查人',
                                dataIndex: 'createUserName',
                                key: 'createUserName',
                                winth:200
                            },
                            isForm:false
                        },
                        {   
                            table: {
                                title: '检查时间',
                                dataIndex: 'deadline',
                                key: 'deadline',
                                format:'YYYY-MM-DD',
                                winth:150
                            },
                            isForm:false
                        }, 
						{   
                            table: {
                                title: '整改要求',
                                dataIndex: 'dangerRequire',
                                key: 'dangerRequire',
                                winth:200
                            },
                            isForm:false
                        },
						{   
                            table: {
                                title: '班组长',
                                dataIndex: 'createUserName',
                                key: 'createUserName',
                                winth:200
                            },
                            isForm:false
                        },
						{   
                            table: {
                                title: '整改完成时间',
                                dataIndex: 'finishTime',
                                key: 'finishTime',
                                format:'YYYY-MM-DD',
                                winth:150
                            },
                            isForm:false
                        }
                    ]}
                    {...window.CountSqDetailPage}
                />
            </div>
        );
    }
}

export default index;