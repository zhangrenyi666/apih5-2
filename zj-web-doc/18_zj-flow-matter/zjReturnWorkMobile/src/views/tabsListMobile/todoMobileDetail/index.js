import React,{ Component } from 'react';
import { Form,Divider } from 'antd'; //Spin,Button,
// import QnnForm from 'apih5/modules/qnn-table/qnn-form';
import { Tabs,Toast } from 'antd-mobile';
import Mlist from 'apih5/modules/MList';
import s from "./style.less"
// import moment from "moment"
import FlowForm from "./form"
import FlowFormOut from "./formOut"

class Page extends Component {

    state = {
        curTab: "form",
        //项目下拉数据
        proOptionData: []
    }

    tabs = [
        { title: "基本信息",key: "form" },
        { title: "人员详情",key: "list" }
    ];

    componentDidMount() {

        // console.log(this.props)
        // var ua = window.navigator.userAgent.toLowerCase();
        // const _this = this;
        // this.props.myFetch('getZjWoaSarsListByOpenPage',{}).then(({ success,data,message }) => {
        //     // console.log(data)
        //     // data.identityCard = "230103198606181987"
        //     if (success) {
        //         if (data.identityCard) {
        //             this.changeIdentityCard(data.identityCard)
        //         }
        //         //没有地址就去请求微信授权
        //         // if (!data.currentAddress) {
        //         window.wx.ready(function () {
        //             window.wx.getLocation({
        //                 type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
        //                 success: function (res) {
        //                     // console.log("res",res)
        //                     var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
        //                     var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。 
        //                     var ggPoint = new window.BMap.Point(longitude,latitude);
        //                     var myGeo = new window.BMap.Geocoder();

        //                     //坐标转换 
        //                     var convertor = new window.BMap.Convertor();
        //                     var pointArr = [];
        //                     pointArr.push(ggPoint);
        //                     convertor.translate(pointArr,1,5,(addressData) => {
        //                         if (addressData.status === 0) {
        //                             myGeo.getLocation(addressData.points[0],function (rs) {
        //                                 var addComp = rs.addressComponents;
        //                                 var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
        //                                 _this.setState({
        //                                     pageInfo: {
        //                                         ...data,
        //                                         currentAddress: address,
        //                                     }
        //                                 })
        //                             })

        //                         }
        //                     })
        //                 }
        //             });
        //         });
        //         // } else {
        //         //     _this.setState({
        //         //         pageInfo: {
        //         //             ...data,
        //         //         }
        //         //     })
        //         // }

        //     } else {
        //         Toast.fail(message)
        //     }
        // })

    }

    goList = () => {
        this.setState({ curTab: "list" })
    }
    changeIdentityCard = async (val) => {
        const { success,data,message } = await this.props.myFetch("getZjWoaSarsInfoListByDept",{ identityCard: val });
        if (success) {
            this.setState({ proOptionData: data })
        } else {
            Toast.fail(message)
        }
    }
    render() {

        return (
            <div style={{ height: '100vh',overflow: "hidden" }} id="yiQingDiaoCha">
                <div style={{ height: 43.5 }}>
                    <Tabs tabs={this.tabs}
                        initialPage={0}
                        page={this.state.curTab}
                        onChange={(tab,index) => { this.setState({ curTab: tab.key }) }}
                    ></Tabs>
                </div>

                {
                    this.state.curTab === "form" ? <div style={{ height: 'calc(100vh - 43.5px)' }}>
                        {this.props.match.params.flowId === 'hwfgApplyOut' ? <FlowFormOut {...this.props} /> : <FlowForm {...this.props} />}

                    </div> : <div style={{ height: 'calc(100vh - 43.5px)' }}>
                            <Mlist
                                myFetch={this.props.myFetch}
                                searchKey="realName"
                                searchBarProps={{ placeholder: "请输入姓名" }}
                                fetchConfig={{
                                    apiName: 'getZjHwSarsUserListByWork',
                                    otherParams: {
                                        workId: this.props.match.params.workId
                                    }
                                }}
                                Item={(props) => {
                                    //列表模板 props里有所有数据
                                    const { rowData,rowID } = props;
                                    const item = rowData;
                                    const index = rowID;
                                    return (
                                        <div
                                            className={s.center}
                                            style={{
                                                borderLeft: `3px solid ${index % 2 === 0 ? "#ff4000" : "#ff9900"}`
                                            }}
                                            key={index}
                                        // onClick={() => {}}
                                        >
                                            <div className={s.top}>
                                                【{item.realName}】
                                                <span>
                                                    {item.zjHwSarsFileList && <a href={item.zjHwSarsFileList[0].mobileUrl}>承诺书</a>}
                                                    {item.zjHwSarsFileOtherList && <a style={{ marginLeft: "6px" }} href={item.zjHwSarsFileOtherList[0].mobileUrl}>其他附件</a>}
                                                </span>
                                            </div>
                                            <Divider style={{ margin: "8px 0px" }} />
                                            <div
                                                style={{ fontSize: "13px" }}
                                            >
                                                <div>职务/岗位：{item.mobile}</div>
                                                <div>春节所在地：{item.festival}</div>
                                                <div>有无赴湖北旅行史：{item.isContactName}</div>
                                                <div>有无与湖北人员接触情况：{item.contactInfo}</div>
                                                <div>有无与确诊病人接触和同乘交通工具：{item.isDiagnosisName}</div>
                                                <div>有无发烧、咳嗽、乏力等症状：{item.isIllnessName}</div>
                                                <div>是否被隔离：{item.isQuarantineName} </div>
                                                <div>隔离情况说明：{item.quarantineInfo}</div>
                                            </div>
                                        </div>
                                    );
                                }}
                            />
                        </div>
                }


            </div>)
    }
}
export default Form.create()(Page);