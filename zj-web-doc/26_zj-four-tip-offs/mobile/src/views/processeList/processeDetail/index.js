import React, { Component } from 'react';
import QnnForm from '../../modules/qnn-table/qnn-form';
import { Form, Timeline, message as Msg, Empty, Modal } from 'antd';
import s from './style.less';
import { NavBar, Icon } from 'antd-mobile';
import { push } from 'react-router-redux';
import moment from 'moment';
class idnex extends Component {
    constructor(props) {
        super(props);
        this.state = {
            page: Number(this.props.match.params.page) || 0,
            reportId: this.props.match.params.reportId || '',
            data:[],
            visible:false,
            file:''
        }
    }
    // componentDidMount() {
    //     let body = {
    //         reportId: this.state.reportId
    //     }
    //     this.props.myFetch('getZjOperationRecordList', body).then(({ data, success, message }) => {
    //         if (success) {
    //             this.setState({
    //                 data
    //             })
    //         } else {
    //             Msg.error(message)
    //         }
    //     })
    // }
    componentDidMount(){
        const { wx } = window;
        wx.ready(() => {
            wx.hideOptionMenu();
        }) 
    }
    handleCancel = () => {
        this.setState({ visible: false });
    }
    render() {
        const { page, type, data } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div className={s.root}>
                <div className={s.top}>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}processeList/${page}`));
                        }}
                    >{"解决详情"}</NavBar>
                </div>
                <div className={s.center}>
                    <QnnForm
                        {...this.props} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
                        fetch={this.props.myFetch} //必须返回一个promise
                        headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                        fetchConfig={{
                            apiName: 'getZjJszlDetails',//可为function 返回必须为string
                            params: {//从表单中取字段  此时可以取默认值或者网址上的值
                                reportId: 'reportId',
                            }
                        }}
                        formConfig = {[
                            {
                                type: "string",
                                label: "主键ID",
                                field: "reportId", //唯一的字段名 ***必传
                                isUrlParams: true,
                                hide: true
                            },
                            {//普通选择框
                                type: 'string',
                                label: '所属项目',
                                field: 'itemId', //唯一的字段名 ***必传
                                placeholder: '无',
                                disabled: true,
                            },
                            // {
                            //     type: "string",
                            //     label: "地理位置",
                            //     field: "reportLocal", //唯一的字段名 ***必传
                            //     disabled: true,
                            //     placeholder: "无", //占位符
                            // },
                            // {
                            //     type: "string",
                            //     label: "标题",
                            //     field: "title", //唯一的字段名 ***必传
                            //     disabled: true,
                            //     placeholder: "无", //占位符
                            // },
                            {
                                type: "string",
                                label: "工程位置",
                                field: "reportProjectLocation", //唯一的字段名 ***必传
                                disabled: true,
                                placeholder: "无", //占位符
                            },
                            {
                                type: "textarea",
                                label: "问题描述",
                                field: "problemDescribe", //唯一的字段名 ***必传
                                disabled: true,
                                placeholder: "无", //占位符
                            },
                            {
                                type: 'camera',
                                label: '图片附件',
                                field: 'files', //唯一的字段名 ***必传
                                disabled: true,
                                cameraConfig:{
                                    type:"images",
                                },
                                fetchConfig: {//配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + 'upload',
                                },
                            },
                            {
                                type: 'files',
                                label: '其它附件',
                                field: 'otherFiles', //唯一的字段名 ***必传
                                initialValue: [],
                                disabled: true,
                                // onPreview: (file) => {
                                //     this.setState({
                                //         file: file.mobileUrl
                                //     }, () => {
                                //         this.setState({ visible: true })
                                //     })
                                // },
                                fetchConfig: {//配置后将会去请求下拉选项数据
                                    apiName: window.configs.domain + 'upload',
                                },
                            }
                        ]}
                    />
                </div>
                {/* <div style={{width:'90%',margin:'0 auto'}}>
                    <div style={{fontSize:'13px',fontWeight:'bold',padding:'10px 0'}}>操作历史：</div>
                    <Timeline>
                        {
                            data.length ? data.map((item, index) => {
                                return (
                                    <Timeline.Item key={index}>
                                        <div><span>{item.createUserName}</span> <span>{moment(item.operationTime).format('YYYY-MM-DD HH:mm:ss')}</span></div>
                                        <div>{item.operationInfo}</div>
                                    </Timeline.Item>
                                )
                            }) : <div style={{fontSize:'13px',fontWeight:'bold',padding:'10px 0',textAlign:'center'}}>暂无操作记录...</div>
                        }
                    </Timeline>
                </div> */}
                <div>
                    <Modal
                        width={'90%'}
                        style={{ paddingBottom: '0', top: '0',overflow:'hidden' }}
                        title="附件详情"
                        visible={this.state.visible}
                        footer={null}
                        onCancel={this.handleCancel}
                        bodyStyle={{ padding: '10px', height: '85vh',overflow:'scroll' }}
                        centered={true}
                        wrapClassName={'modals'}
                    >
                        <iframe width='100%' height='100%' frameBorder={0} src={this.state.file}></iframe>
                    </Modal>
                </div>
            </div>
        )
    }
}
export default Form.create()(idnex)