import React,{ Component } from "react";
// import { MyFetch, getUserInfo } from "../../main";
import {
    Toast,
    WingBlank,
    WhiteSpace,
    Result,
    Icon,
    Modal,
    SegmentedControl
} from "antd-mobile";
import s from "./index.less"; 
const MyList = (data,title) => {
    if (!data || data.length < 1) {
        return;
    }
    // console.log(data);
    return (
        <div className={s["list"]}>
            <WhiteSpace />
            <WingBlank>
                <center className={s["title"]}>{title}</center>
                <ul>
                    {data.map((v,i) => {
                        let newValue;
                        let isHide;
                        if (!isNaN(v.value - 1)) {
                            let num = Number(v.value);
                            newValue = num.toFixed(2);
                            if (num <= 0) {
                                isHide = "none";
                            }
                        } else {
                            newValue = v.value;
                        }
                        if (isHide !== "none") {
                            return (
                                <li key={i} style={{ display: isHide }}>
                                    <div className={s["left"]}>{v.name}</div>
                                    <div className={s["right"]}>{newValue}</div>
                                </li>
                            );
                        } else {
                            return "";
                        }
                    })}
                </ul>
            </WingBlank>
            <WhiteSpace />
        </div>
    );
};

class Home extends Component {
    constructor(props) {
        super(props);

        let { year,month } = props.match.params;
        this.ajaxBody = { year: year,month: month };
        this.flag = props.match.params.flag;//0工资详情  1绩效详情
        this.state = {
            error: 0, // 0初始化 1正确 2错误 3工资详情被删除 4绩效被删除
            selectedIndex: this.flag === '1' ? 1 : 0, //当前选中的tabs
            errorText: "",
            total: { name: "应发金额",value: 0 }, //总额
            newTotal: { name: "应扣金额",value: 0 }, //实际总额
            time: 1464710400000, //时间
            userInfo: [], //用户信息
            award: {
                performance: {
                    name: "",
                    value: 0
                }, //绩效奖
                exploit: {
                    name: "",
                    value: 0
                } //开发奖
            },
            remark: { name: "备注",value: "" }, //备注
            ShouldSend: [
                //应发工资
                {
                    name: "基本工资",
                    value: "暂无数据"
                }
            ],
            ShouldButton: [
                //应扣工资
                {
                    name: "基本工资",
                    value: "暂无数据"
                }
            ],
            salaryIsShow: false, //工资是否显示

            performDataIsShow: false, //应发绩效金额 扣税金  实发绩效金额 是否显示
            ShouldPerformance: {}, //应发绩效金额
            WithholdTaxes: {}, //扣税金
            actualPerformance: {}, //实发绩效金额
            totalPerformance: {}, //全年应发绩效金额

            footer: false,//阅后即焚的按钮是否显示
        };
    }

    refresh(selectedIndex) {
        // getUserInfo(({ userId }) => {
        const body = {
            ...this.ajaxBody,
            userId: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
        };

        if (selectedIndex === 0) {
            //说明是工资详情 
            let _p = this.ajaxBody.year + '/' + this.ajaxBody.month;

            //因为2018年一月有一条数据使用的是以前模板后台返回的数据格式不一样所以特殊处理
            if (_p === '2018/1') {
                //一月的工资需要这段代码
                this.props.myFetch("getZjWageDetail",body).then(res => {
                    // console.log("getZjWageDetail：", res);
                    if (res.success) {
                        if (!res.data) {
                            this.setState({
                                salaryIsShow: false,
                                error: 3,//这个状态表明工资详情被删除了
                                errorText: res.message,
                                total: [], //总额
                                newTotal: [], //实际总额
                                ShouldSend: [], //应发工资
                                ShouldButton: [], //应扣工资
                                userInfo: [], //用户信息
                                remark: [], //备注 
                                footer: false,
                            });
                        } else {
                            let data = res.data ? JSON.parse(res.data.jsonData) : false;
                            console.log('data：',data)
                            let performData = res.data.perform ? JSON.parse(res.data.perform.jsonData) : false;

                            if (data) {
                                //应发工资
                                data[2].children.map((v,i,newArr) => {
                                    for (let obj in v) {
                                        let o = {
                                            name: obj,
                                            value: v[obj]
                                        };
                                        newArr[i] = o;
                                    }
                                    return newArr;
                                });

                                //应扣工资
                                data[4].children.map((v,i,newArr) => {
                                    for (let obj in v) {
                                        let o = {
                                            name: obj,
                                            value: v[obj]
                                        };
                                        newArr[i] = o;
                                    }
                                    return newArr;
                                });
                            }

                            this.setState({
                                salaryIsShow: data ? true : false, //工资是否显示
                                error: 1,
                                total: data ? data[3] : "", //总额
                                newTotal: data ? data[5] : "", //实际总额
                                ShouldSend: data ? data[2].children : "", //应发工资
                                ShouldButton: data ? data[4].children : "", //应扣工资
                                userInfo: data ? [data[1],data[0]] : performData ? [performData[1],performData[0]] : "", //用户信息
                                remark: data ? data[6] : "", //备注 
                                footer: true,
                            });
                        }
                    }
                });

            } else {
                //正常情况
                this.props.myFetch("getZjWageDetail",body).then(({ data,success,message }) => {
                    if (success) {
                        // console.log("getZjWageDetail：", data);
                        if (!data) {
                            this.setState({
                                salaryIsShow: false,
                                error: 3,//这个状态表明工资详情被删除了
                                errorText: message,
                                total: [], //总额
                                newTotal: [], //实际总额
                                ShouldSend: [], //应发工资
                                ShouldButton: [], //应扣工资
                                userInfo: [], //用户信息
                                remark: [], //备注 
                                footer: false,
                            });
                        } else {
                            let _data = data.jsonData ? JSON.parse(data.jsonData) : false;
                            let performData = data.perform ? JSON.parse(data.perform.jsonData) : false;

                            if (_data) {
                                this.setState({
                                    salaryIsShow: _data ? true : false, //工资是否显示
                                    error: 1,
                                    total: _data ? _data[3] : "", //总额
                                    newTotal: _data ? _data[5] : "", //实际总额
                                    ShouldSend: _data ? _data[2].children : "", //应发工资
                                    ShouldButton: _data ? _data[4].children : "", //应扣工资
                                    userInfo: _data ? [_data[1],_data[0]] : performData ? [performData[1],performData[0]] : "", //用户信息
                                    remark: _data ? _data[6] : "", //备注 
                                    footer: true,
                                });
                            }
                        }
                    }
                });
            }
        } else {
            //说明是绩效详情  
            this.props.myFetch("getZjPerformDetail",body).then(res => {
                // console.log('getZjPerformDetail:',res)
                if (res.success) {
                    if (!res.data) {
                        this.setState({
                            error: 4,//这个状态表明绩效被删除
                            errorText: res.message,
                            listData: [],//设置空数据
                            footer: false,
                        });
                    } else {
                        // console.log("绩效总数据：", JSON.parse(res.data.jsonData));
                        //总数据
                        let data = res.data ? JSON.parse(res.data.jsonData) : false;
                        //过滤出页面需要的数据 放到一个数组里面去
                        let _arr = [data[2],data[3],data[4]];
                        this.setState({
                            error: 1,
                            userInfo: [data[1],data[0]], //用户信息
                            listData: _arr,//设置值
                            remark: data ? data[5] : "", //备注 
                            footer: true,
                        });
                    }
                }
            });
        }
        // });
    }

    componentDidMount() {
        this.refresh(Number(this.flag));
        this.setState({
            error: 1
        });
    }

    clickFn() {
        let { selectedIndex } = this.state;
        let apiUrl = ""; //绩效奖和工资详情是不一样的删除接口
        if (selectedIndex === 1) {
            //绩效奖删除
            apiUrl = "deleteZjPerform";
        } else {
            //正常删除
            apiUrl = "deleteZjWage";
        }

        Modal.alert("确定焚毁？","焚毁后该页数据将会被删除！",[
            {
                text: "取消",
                onPress: () => {
                    console.log("cancel");
                },
                style: "default"
            },
            {
                text: "确定",
                onPress: () => {
                    // getUserInfo(({ userId }) => {
                    const body = {
                        ...this.ajaxBody,
                        userId: this.props.loginAndLogoutInfo.loginInfo.userInfo.userId
                    };
                    // console.log("url:", apiUrl);
                    this.props.myFetch(apiUrl,body).then(res => {
                        // console.log('删除后的返回值：', res)
                        if (res.success) {
                            Toast.success("已焚毁！",2,() => {
                                this.refresh.bind(this)(selectedIndex);
                            });
                        }
                    });
                    // });
                }
            }
        ]);
    }

    render() {
        return (
            <div className={s.home} style={{ height: "100%" }}>
                {this.state.error === 0 ? (
                    <div />
                ) : this.state.error === 2 ? (
                    <Result
                        img={
                            <Icon
                                type="cross-circle-o"
                                style={{ fill: "#F13642",width: "60px" }}
                            />
                        }

                        title={this.state.errorText}
                    />
                ) : (
                            <div>
                                <div className={s.herader}>
                                    <SegmentedControl
                                        style={{ height: "100%" }}
                                        selectedIndex={this.state.selectedIndex}
                                        onChange={e => {
                                            this.setState({
                                                // error: e.nativeEvent.selectedSegmentIndex === 0 ? 3 : 4,
                                                selectedIndex: e.nativeEvent.selectedSegmentIndex
                                            });
                                            this.refresh(e.nativeEvent.selectedSegmentIndex);
                                        }}
                                        values={[
                                            `${this.ajaxBody.year} 年 ${this.ajaxBody.month}月工资详情`,
                                            `${this.ajaxBody.year} 年 ${this.ajaxBody.month}月绩效详情`
                                        ]}
                                    />
                                </div>
                                <div
                                    className={s.content}
                                    ref="content"
                                    style={{ height: window.innerHeight }}
                                >
                                    <WhiteSpace />
                                    <WingBlank>
                                        {/* 设置同样的部分   --个人信息 */}
                                        <div className={s.model}>
                                            {MyList(this.state.userInfo,"个人信息")}
                                        </div>

                                        {//根据 state.selectedIndex 判断显示显示工资还是绩效
                                            this.state.selectedIndex === 0 ? (
                                                //显示工资详情  
                                                this.state.error === 3
                                                    ?
                                                    <div>
                                                        <Result
                                                            img={
                                                                <Icon
                                                                    type="cross-circle-o"
                                                                    style={{ fill: "#F13642",width: "60px",height: "60px" }}
                                                                />
                                                            }

                                                            title={this.state.errorText}
                                                        />
                                                    </div>
                                                    :
                                                    <div>

                                                        <div
                                                            className={s.model}
                                                            style={{
                                                                display: this.state.salaryIsShow ? "block" : "none"
                                                            }}
                                                        >
                                                            {MyList(this.state.ShouldSend,"应发工资")}
                                                        </div>

                                                        <div className={s.model}
                                                            style={{
                                                                display: this.state.salaryIsShow ? "block" : "none"
                                                            }}
                                                        >
                                                            {MyList(this.state.ShouldButton,"应扣工资")}
                                                        </div>

                                                        <div className={s.list}>
                                                            {//工资总额和实付工资
                                                                this.state.salaryIsShow ? (
                                                                    <WingBlank>
                                                                        <ul>
                                                                            <WhiteSpace />
                                                                            <div>
                                                                                <li className={s.ShouldSend}>
                                                                                    {this.state.total.name}：{Number(
                                                                                        this.state.total.value
                                                                                    ).toFixed(2)}
                                                                                </li>
                                                                                <li className={s.ShouldBotton}>
                                                                                    {this.state.newTotal.name}：{Number(
                                                                                        this.state.newTotal.value
                                                                                    ).toFixed(2)}
                                                                                </li>
                                                                            </div>
                                                                            <WhiteSpace />
                                                                        </ul>
                                                                    </WingBlank>
                                                                ) : (
                                                                        ""
                                                                    )
                                                            }
                                                        </div>

                                                        {/* 备注 */}
                                                        <div className={s.model} style={{ fontSize: "0.8rem",color: "#999" }}>
                                                            <span style={{ display: this.state.remark.value ? '' : 'none' }}>{this.state.remark.name}：</span>
                                                            <span>{this.state.remark.value}</span>
                                                        </div>

                                                        {/* style={{display:this.state.error !== 3 ? 'block' : 'none'}} */}
                                                        {/* <div className="footer" style={{display:'none'}}>
                              <div onClick={this.clickFn.bind(this)}>阅后即焚</div>
                            </div>  */}
                                                    </div>
                                            ) : (
                                                    //显示绩效详情
                                                    <div>
                                                        {
                                                            this.state.error === 4
                                                                ?
                                                                <div>
                                                                    <Result
                                                                        img={
                                                                            <Icon
                                                                                type="cross-circle-o"
                                                                                style={{ fill: "#F13642",width: "60px",height: "60px" }}
                                                                            />
                                                                        }
                                                                        title={this.state.errorText}
                                                                    />
                                                                </div>
                                                                :
                                                                <div>
                                                                    {
                                                                        MyList(this.state.listData,'绩效详情')
                                                                    }
                                                                    <div className={s.model} style={{ fontSize: ".72rem",color: "#999" }}>
                                                                        <span style={{ display: this.state.remark.value ? '' : 'none' }}>{this.state.remark.name}：</span>
                                                                        <span>{this.state.remark.value}</span>
                                                                    </div>
                                                                    {/* <div  style={{display:this.state.error !== 4 ? 'block' : 'none'}} className="footer">
                                    <div onClick={this.clickFn.bind(this)}>阅后即焚</div>
                                  </div> */}
                                                                </div>
                                                        }

                                                    </div>
                                                )}
                                        <div ref={(me) => this.footer = me} className={s.footer} style={{ display: this.state.footer ? 'block' : 'none' }}>
                                            <div onClick={this.clickFn.bind(this)}>阅后即焚</div>
                                        </div>
                                    </WingBlank>
                                    <WhiteSpace />
                                </div>
                            </div>
                        )}
            </div>
        );
    }
}

export default Home;
