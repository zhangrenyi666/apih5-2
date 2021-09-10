import React,{ Component } from 'react'
import { message as Msg,Tabs,Row,Col,Radio,Button,Spin } from "antd"
import s from "./index.less"

const { TabPane } = Tabs;
class index extends Component {

    state = {
        data: [],
        curImg: null,
        curSubList: [],
        curTabsValue: null,
        curTabsNameValue: null,
        curSubValue: null,
        curSubNameValue: null,
        curSubPriceValue: null,
        loadding: true,

        submitIng: false,
        iframeLoading: true
    }

    componentDidMount() {
        this.refresh();
        this.setIframeH();

        window.addEventListener("resize",this.setIframeH.bind(this),false);
    }

    componentWillUnmount() {
        window.removeEventListener("resize",this.setIframeH.bind(this),false);
    }


    refresh = () => {
        Msg.loading("loading...",0);
        this.props.myFetch("getZjTjMedicalList",{}).then(({ data,success,message }) => {
            Msg.destroy();
            if (success) {

                let curTabsValueData = data.filter((item) => {
                    return item.chooseFlag === "1"
                })[0] || data[0];

                let curTabsValueDataSubList = curTabsValueData.subList;

                let curSubValueData = curTabsValueDataSubList.filter((item) => {
                    return item.chooseFlag === "1"
                })[0] || {};

                let curImg = curSubValueData.fileUrl ? curSubValueData.fileUrl : curTabsValueDataSubList[0].fileUrl;


                this.setState({
                    curSubList: curTabsValueDataSubList,
                    data: data,
                    loadding: false,
                    curTabsValue: curTabsValueData.medicalId,
                    curTabsNameValue: curTabsValueData.medicalName,
                    curSubValue: curSubValueData.medicalId,
                    curSubNameValue: curSubValueData.medicalName,
                    curSubPriceValue: curSubValueData.price,
                    curImg: curImg,

                    curTabsValueData,
                    curSubValueData

                })
            } else {
                Msg.error(message)
            }
        });
    }

    radioChange = e => {
        const { curSubList = [] } = this.state;
        let curSubValueData = curSubList.filter(item => {
            return item.medicalId === e.target.value
        })[0] || {};

        this.setState({
            curSubValue: e.target.value,
            curSubNameValue: curSubValueData.medicalName,
            curSubPriceValue: curSubValueData.price,
            medicalName: curSubValueData.medicalName,
            curImg: curSubList.filter(item => {
                return item.medicalId === e.target.value
            })[0].fileUrl,
            curSubValueData: curSubValueData, 
        });
    };

    submit = () => {
        const { curSubValue,curTabsValue,curTabsNameValue,curSubNameValue,curSubPriceValue} = this.state;
        const { loginAndLogoutInfo = {} } = this.props;
        const { loginInfo: { userInfo: { departmentList = [] } } } = loginAndLogoutInfo;
        let body = {
            hospitalId: curTabsValue,
			hospitalName:curTabsNameValue,
            medicalId: curSubValue,
            medicalName: curSubNameValue,
            price: curSubPriceValue,
            deptId: departmentList[0] ? departmentList[0].departmentId : null,
            deptName: departmentList[0] ? departmentList[0].departmentName : null,
        };

        if(!body.medicalId){
            Msg.error("请选择套餐！");
            return;
        } 
        Msg.loading("loading...",0);
        this.setState({ submitIng: true });
        this.props.myFetch("addZjTjMedicaStatistical",body).then(({ data,success,message }) => {
            Msg.destroy();
            this.setState({ submitIng: false });
            if (success) {
                Msg.success(message)
            } else {
                Msg.error(message)
            }
        });
    }

    setIframeH = () => {
        this.setState({
            iframeH: window.innerHeight - 64 - 21 - 16 - 24 - 52 - 56 - 41 - 12
        })
    }

    iframeOnload = () => {
        this.setState({
            iframeLoading: false
        })
    }

    render() {
        const { data = [],curSubList = [],curTabsValue = null,curImg,curSubValue,submitIng,
            curTabsValueData = {},
            curSubValueData = {},
            iframeH,
            iframeLoading
        } = this.state;
        return (
            <div className={s.pageContainer}>
                <div className={s.tabsContainer}>
                    <Row>
                        <Col span={18}>
                            <Tabs activeKey={curTabsValue} size={"small"} onChange={(key) => {
                                let curSubList = data.filter(item => {
                                    return item.medicalId === key
                                })[0].subList;
                                let curSubValueData = curSubList.filter((item) => {
                                    return item.chooseFlag === "1"
                                })[0] || curSubList[0];
                                let curTabsValueData = data.filter(item => {
                                    return item.medicalId === key
                                })[0];

                                this.setState({
                                    curSubList,
                                    curTabsValue: key,
                                    curTabsValueData: curTabsValueData,
                                    curTabsNameValue: curTabsValueData.medicalName,
                                    curImg: curSubValueData.fileUrl,
                                    curSubValueData: curSubList.filter((item) => {
                                        return item.chooseFlag === "1"
                                    })[0] || {},
                                    iframeLoading: true
                                })
                            }}>
                                {
                                    data.map((item,index) => {
                                        let { medicalName,medicalId } = item;
                                        return <TabPane tab={medicalName} key={medicalId}></TabPane>
                                    })
                                }
                            </Tabs>
                        </Col>
                        <Col span={6}>
                            <div style={{ textAlign: "right",marginTop: '10px' }}>
                                <Radio.Group onChange={this.radioChange.bind(this)} defaultValue={curSubValue} value={curSubValue}>
                                    {curSubList.map((item,index) => {
                                        return <Radio key={index} value={item.medicalId}>{item.medicalName}</Radio>
                                    })}
                                </Radio.Group>
                            </div>

                        </Col>
                    </Row>
                </div>

                {
                    <div className={s.imgContainer}>{curImg && <Spin tip="loading..." spinning={iframeLoading}><iframe title="if" onLoad={this.iframeOnload} style={{ width: "100%",height: iframeH,border: 'none' }} src={curImg} alt="" /></Spin>}</div>
                }

                <div className={s.bottomBtns}>
                    当前选中：<a>{curTabsValueData.medicalName} {curSubValueData.medicalName && `（套餐：${curSubValueData.medicalName}）`}</a>
                    <Button onClick={this.submit} style={{ marginLeft: '16px' }} loading={submitIng} type="primary">确认提交</Button>
                </div>
            </div>
        )
    }
}

export default index
