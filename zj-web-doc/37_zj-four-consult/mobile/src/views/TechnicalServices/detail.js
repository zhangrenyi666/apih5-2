import React, { Component } from "react";
import s from "./style.less";
import { NavBar, Icon, Tabs, Toast } from "antd-mobile";
import { Timeline } from 'antd';
import { push } from 'react-router-redux';
import moment from 'moment';
import FlowFormByzjSjTechnicalServiceEdit from './formEdit';
class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            serviceId: props.match.params.serviceId || '',
            selectType: props.match.params.selectType || '',
            data: null,
            flowWebUrl: '',
            flowData: null
        }
    }
    componentDidMount() {
        this.props.myFetch('getZjSjConsultTechnicalServiceFlowDetails', { serviceId: this.state.serviceId }).then(({ success, message, data }) => {
            if (success) {
                this.setState({
                    data: data
                }, () => {
                    if (this.state.data.apih5FlowStatus !== '4') {
                        this.props.myFetch('openFlowByReport', { apiName: 'getZjSjConsultTechnicalServiceFlowDetails', apiType: 'POST', flowId: 'zjSjTechnicalService', title: data.title, workId: data.workId }).then(({ success, message, data }) => {
                            if (success) {
                                this.setState({
                                    flowData: data.flowHistoryList,
                                    flowWebUrl: data.flowWebUrl
                                });
                            } else {
                                Toast.fail(message);
                            }
                        });
                    }
                });
            } else {
                Toast.fail(message);
            }
        });
    }
    render() {
        const { data, flowData, flowWebUrl, selectType } = this.state;
        const { dispatch, myPublic: { appInfo: { mainModule } } } = this.props;
        return (
            <div>
                <div>
                    <NavBar
                        style={{ width: "100%" }}
                        mode="dark"
                        icon={<Icon type="left" />}
                        onLeftClick={() => {
                            dispatch(push(`${mainModule}TechnicalServices/${selectType === '0' ? '1' : '0'}`));
                        }}
                    >
                        {"技术服务详情"}
                    </NavBar>
                </div>
                <div style={{ height: document.documentElement.clientHeight - 45 }}>
                    {data ? <Tabs tabs={data.apih5FlowStatus !== '4' ? [{ title: "基础信息" }, { title: "操作历史" }] : [{ title: "基础信息" }]} swipeable={false}>
                        <div style={{ height: document.documentElement.clientHeight - 90 }}>
                            {
                                selectType === '1' && data.apih5FlowStatus === '4' ? <FlowFormByzjSjTechnicalServiceEdit {...this.props} flowData={data}/> : <FlowFormByzjSjTechnicalServiceEdit {...this.props}  flowData={data} noButton={true} />
                            }
                        </div>
                        <div style={{ height: document.documentElement.clientHeight - 90 }}>
                            {flowData && flowData.length ? <div style={{ height: '50%', overflow: 'auto', padding: '20px' }}>
                                <Timeline>
                                    {
                                        flowData.map((item, index) => {
                                            return (
                                                <Timeline.Item key={index}>
                                                    <div><span>{item.nodeName}：{item.realName}</span> <span>{moment(item.actionTime).format('YYYY-MM-DD HH:mm:ss')}</span></div>
                                                    <div>{item.comments}</div>
                                                </Timeline.Item>
                                            )
                                        })
                                    }
                                </Timeline>
                            </div> : null}
                            {
                                flowWebUrl ? <div style={{ height: '50%', overflow: 'auto', padding: '20px' }}>
                                    <iframe width="100%" height='100%' frameBorder={0} src={flowWebUrl}></iframe>
                                </div> : null
                            }
                        </div>
                    </Tabs> : null}
                </div>
            </div>
        );
    }
}
export default Index;