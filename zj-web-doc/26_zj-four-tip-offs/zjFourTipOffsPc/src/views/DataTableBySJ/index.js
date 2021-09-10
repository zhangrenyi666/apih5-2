import React, { Component } from "react";
import { GCLBJ, YZCDJ, GSTJJ, JBPCJ, YZCDW, GSTJW, JBPCW, JBLBA, YHLBA, JBSJA, YHFXA, NowTime } from "./components";
import {
  Tabs, message as Msg, Button
} from 'antd';
import s from "./style.less";
import moment from 'moment';
import QnnForm from '../modules/qnn-table/qnn-form';
import { SearchOutlined, ReloadOutlined } from '@ant-design/icons';
const { TabPane } = Tabs;
class index extends Component {
  state = {
    itemId: '',
    startDate: moment(new Date(new Date().setDate(1))).valueOf(),
    endDate: moment(new Date()).valueOf(),
    classify: [],
    ext1: this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1
  };
  componentDidMount() {
    this.refresh();
    this.setLayerStyle("in");
  }
  componentWillUnmount() {
    this.setLayerStyle("out");
  }
  refresh = () => {
    const { myFetch } = this.props;
    const { startDate, endDate } = this.state;
    myFetch('getZszlWgwjAqyhNum', { startDate, endDate }).then(({ data, success, message }) => {
      if (success) {
        this.setState({
          classify: [
            {
              label: '技术质量',
              value: data.jszlAllNum
            },
            {
              label: '违规违纪',
              value: data.wgwjAllNum
            },
            {
              label: '安环隐患',
              value: data.aqyhAllNum
            }
          ]
        })
      } else {
        Msg.error(message)
      }
    })
  }
  //改变框架的样式
  setLayerStyle = (type = "in") => {
    if (type === "in") {
      //进入页面时
      let _conDom = document.getElementsByClassName("ant-layout");
      let _footerDom = document.getElementsByClassName("ant-layout-footer");
      let _breadcrumbDom = document.getElementsByClassName("ant-breadcrumb");
      let _alc = document.getElementsByClassName("ant-layout-content");
      if (_conDom && _conDom[2]) {
        _conDom[2].style.padding = 0;
        _conDom[2].style.background = "rgb(0, 21, 43)";
      }
      if (_breadcrumbDom && _breadcrumbDom[0]) {
        _breadcrumbDom[0].style.display = "none";
      }
      if (_footerDom && _footerDom[0]) {
        _footerDom[0].style.display = "none";
      }
      if (_alc && _alc[0]) {
        _alc[0].style.background = "";
        _alc[0].style.padding = "0";
      }
    } else {
      //离开页面
      let _conDom = document.getElementsByClassName(".ant-layout");
      let _breadcrumbDom = document.getElementsByClassName(".ant-breadcrumb");
      let _footerDom = document.getElementsByClassName("ant-layout-footer");
      let _alc = document.getElementsByClassName("ant-layout-content");
      if (_conDom && _conDom[2]) {
        _conDom[2].style.padding = "0 12px";
        _conDom[2].style.background = "#f0f2f5";
      }
      if (_breadcrumbDom && _breadcrumbDom[0]) {
        _breadcrumbDom[0].style.display = "block";
      }
      if (_footerDom && _footerDom[0]) {
        _footerDom[0].style.display = "none";
      }
      if (_alc && _alc[0]) {
        _alc[0].style.background = "rgb(255, 255, 255)";
        _alc[0].style.padding = "12";
      }
    }
  };
  callback = (key) => {
    this.setState({ key });
  }
  render() {
    const { startDate, endDate, classify, ext1 } = this.state;
    let key = '1';
    if (ext1 === '1') {
      key = '1';
    } else if (ext1 === '2') {
      key = '3';
    } else if (ext1 === '3') {
      key = '2';
    } else if (ext1 === '4') {
      key = '1';
    }
    return (
      <div className={s.root} id="fullScreen">
        <div className={s.header}>
          <div className={s.left}>
            <div className={s.userInfo}>
              <div className={s.nowTime}><NowTime /></div>
              <div className={s.rightCon}>
                <QnnForm
                  wrappedComponentRef={(me) => this.qnnForm = me}
                  formConfig={[
                    {
                      label: '日期',
                      type: "rangeDate",
                      field: 'rangeDate',
                      initialValue:[moment(new Date(new Date().setDate(1))).valueOf(),moment(new Date()).valueOf()],
                      placeholder: "请输入",
                      span:10,
                    },
                    {
                      type: 'component',
                      field: 'diy',
                      span:6,
                      Component: obj => {
                        return (
                          <div
                            style={{ width: "100%", height: '56px', lineHeight: '56px' }}
                          >
                            <Button style={{ marginRight: '10px' }} type="primary" icon={<SearchOutlined />} onClick={() => {
                              this.qnnForm.getValues(false, (value) => {
                                this.setState({
                                  startDate: value.rangeDate[0] ? value.rangeDate[0] : moment(new Date(new Date().setDate(1))).valueOf(),
                                  endDate: value.rangeDate[1] ? value.rangeDate[1] : moment(new Date()).valueOf(),
                                },() => {
                                  this.refresh();
                                })
                              })
                            }}>搜索</Button>
                            <Button style={{ marginRight: '10px' }} icon={<ReloadOutlined />} onClick={() => {
                              this.qnnForm.setValues({ rangeDate: [moment(new Date(new Date().setDate(1))).valueOf(),moment(new Date()).valueOf()] });
                              this.setState({
                                startDate: moment(new Date(new Date().setDate(1))).valueOf(),
                                endDate: moment(new Date()).valueOf()
                              },() => {
                                this.refresh();
                              })
                            }}>重置</Button>
                          </div>
                        );
                      }
                    }
                  ]}
                />
              </div>
            </div>
          </div>
          <div className={s.right}>
            <div className={s.rightCon}>
              {classify.map(({ label, value }, index) => {
                return (
                  <div key={index}>
                    <div className={s.label}>{label}</div>
                    <div className={s.value}>
                      {value}
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        </div>
        <div className={s.con}>
          <Tabs defaultActiveKey={key} tabBarStyle={{ color: "white", margin: 0, textAlign: 'center', width: '100%' }} onChange={this.callback} size="small" animated={true}>
            <TabPane tab="技术质量" key="1" disabled={ext1 === '1' || ext1 === '4' ? false : true}>
              <div className={s.left}>
                <div className={s.block1}>
                  <div className={s.block1_l}>
                    <GCLBJ startDate={startDate} endDate={endDate}  {...this.props} />
                  </div>
                  <div className={s.block1_r}>
                    <YZCDJ startDate={startDate} endDate={endDate} {...this.props} />
                  </div>
                  <div className={s.block1_r}>
                    <GSTJJ startDate={startDate} endDate={endDate} {...this.props} />
                  </div>
                </div>
                <div className={s.block2}>
                  <JBPCJ startDate={startDate} endDate={endDate} {...this.props} />
                </div>
              </div>
            </TabPane>
            <TabPane tab="违规违纪" key="2" disabled={ext1 === '3' || ext1 === '4' ? false : true}>
              <div className={s.left}>
                <div className={s.block1}>
                  <div className={s.block1_l}>
                    <YZCDW startDate={startDate} endDate={endDate}  {...this.props} />
                  </div>
                  <div className={s.block1_c}>
                    <GSTJW startDate={startDate} endDate={endDate} {...this.props} />
                  </div>
                </div>
                <div className={s.block2}>
                  <JBPCW startDate={startDate} endDate={endDate} {...this.props} />
                </div>
              </div>
            </TabPane>
            <TabPane tab="安环隐患" key="3" disabled={ext1 === '2' || ext1 === '4' ? false : true}>
              <div className={s.left}>
                <div className={s.block1}>
                  <div className={s.block1_l}>
                    <JBLBA startDate={startDate} endDate={endDate}  {...this.props} />
                  </div>
                  <div className={s.block1_r}>
                    <YHLBA startDate={startDate} endDate={endDate} {...this.props} />
                  </div>
                  <div className={s.block1_r}>
                    <JBSJA startDate={startDate} endDate={endDate} {...this.props} />
                  </div>
                </div>
                <div className={s.block2}>
                  <YHFXA startDate={startDate} endDate={endDate} {...this.props} />
                </div>
              </div>
            </TabPane>
          </Tabs>
        </div>
      </div>
    );
  }
}
export default index;
