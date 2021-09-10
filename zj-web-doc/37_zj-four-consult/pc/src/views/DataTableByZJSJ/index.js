import React, { Component } from "react";
import { BMTJ } from "./components";
import s from "./style.less";
import QnnForm from '../modules/qnn-table/qnn-form';
import { Button, Modal } from 'antd';
import { SearchOutlined, ReloadOutlined } from '@ant-design/icons';
const bg = require('../../imgs/bg.jpg');
const config = require('../../imgs/config.png');
const ranking = require('../../imgs/ranking.png');
const rootStyle = {
  backgroundImage: `url(${bg})`,
  backgroundRepeat: 'no-repeat',
  backgroundSize: '100% 100%'
};
const configStyle = {
  backgroundImage: `url(${config})`,
  backgroundRepeat: 'no-repeat',
  backgroundSize: '100% 100%'
};
const rankingStyle = {
  backgroundImage: `url(${ranking})`,
  backgroundRepeat: 'no-repeat',
  backgroundSize: '100% 100%'
};
class index extends Component {
  state = {
    visible: false
  }
  componentDidMount() {
    this.setLayerStyle("in");
  }
  componentWillUnmount() {
    this.setLayerStyle("out");
  }

  //改变框架的样式
  setLayerStyle = (type = "in") => {
    if (type === "in") {
      //进入页面时
      let _conDom = document.getElementsByClassName("ant-layout");
      let _headerDom = document.getElementsByClassName("ant-layout-header");
      let _footerDom = document.getElementsByClassName("ant-layout-footer");
      let _breadcrumbDom = document.getElementsByClassName("ant-breadcrumb");
      let _alc = document.getElementsByClassName("ant-layout-content");
      if (_conDom && _conDom[5]) {
        _conDom[5].style.padding = 0;
        _conDom[5].style.background = "rgb(0, 21, 43)";
      }
      if (_conDom && _conDom[2]) {
        _conDom[2].style.padding = 0;
        _conDom[2].style.background = "rgb(0, 21, 43)";
      }
      //解决header不显示问题
      if (_headerDom && _headerDom[0]) {
        _headerDom[0].style.zIndex = "1";
      }
      if (_headerDom && _headerDom[1]) {
        _headerDom[1].style.display = "none";
      }
      if (_breadcrumbDom && _breadcrumbDom[0]) {
        _breadcrumbDom[0].style.display = "none";
      }
      if (_breadcrumbDom && _breadcrumbDom[1]) {
        _breadcrumbDom[1].style.display = "none";
      }
      if (_footerDom && _footerDom[0]) {
        _footerDom[0].style.display = "none";
      }
      if (_alc && _alc[0]) {
        _alc[0].style.background = "";
        _alc[0].style.padding = "0";
      }
      if (_alc && _alc[1]) {
        _alc[1].style.background = "";
        _alc[1].style.padding = "0";
      }
    } else {
      //离开页面
      let _conDom = document.getElementsByClassName(".ant-layout");
      let _headerDom = document.getElementsByClassName("ant-layout-header");
      let _breadcrumbDom = document.getElementsByClassName(".ant-breadcrumb");
      let _footerDom = document.getElementsByClassName("ant-layout-footer");
      let _alc = document.getElementsByClassName("ant-layout-content");
      if (_conDom && _conDom[5]) {
        _conDom[5].style.padding = "0 12px";
        _conDom[5].style.background = "#f0f2f5";
      }
      if (_conDom && _conDom[2]) {
        _conDom[2].style.padding = "0 12px";
        _conDom[2].style.background = "#f0f2f5";
      }
      if (_headerDom && _headerDom[1]) {
        _headerDom[1].style.display = "none";
      }
      if (_breadcrumbDom && _breadcrumbDom[0]) {
        _breadcrumbDom[0].style.display = "none";
      }
      if (_breadcrumbDom && _breadcrumbDom[1]) {
        _breadcrumbDom[1].style.display = "none";
      }
      if (_footerDom && _footerDom[0]) {
        _footerDom[0].style.display = "none";
      }
      if (_alc && _alc[0]) {
        _alc[0].style.background = "rgb(255, 255, 255)";
        _alc[0].style.padding = "12";
      }
      if (_alc && _alc[1]) {
        _alc[1].style.background = "rgb(255, 255, 255)";
        _alc[1].style.padding = "12";
      }
    }
  };
  render() {
    const { visible } = this.state;
    return (
      <div className={s.root} style={rootStyle}>
        {/* <div className={s.header}>
          <QnnForm
            fetch={this.props.myFetch}
            upload={this.props.myUpload}
            headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
            wrappedComponentRef={(me) => this.qnnForm = me}
            formConfig={[
              {
                label: '日期',
                type: "rangeDate",
                field: 'rangeDate',
                placeholder: "请输入",
                span: 6
              },
              {
                type: 'component',
                field: 'diy',
                span: 6,
                Component: obj => {
                  return (
                    <div
                      style={{ width: "100%", height: '56px', lineHeight: '56px',paddingLeft:'20px' }}
                    >
                      <Button style={{ marginRight: '10px' }} type="primary" icon={<SearchOutlined />} onClick={() => {
                        this.qnnForm.getValues(false, (value) => {
                          this.setState({
                              
                          })
                        })
                      }}>搜索</Button>
                      <Button style={{ marginRight: '10px' }} icon={<ReloadOutlined />} onClick={() => {
                        this.qnnForm.setValues({  });
                        this.setState({
                              
                        })
                      }}>重置</Button>
                    </div>
                  );
                }
              }
            ]}
          />
        </div> */}
        <div className={s.top}>
          <div className={s.topOne} style={configStyle}>
            <div className={s.topOnel}>
              <BMTJ {...this.props} />
            </div>
            <div className={s.topOner}>
              <Button type="primary" style={{ position: 'absolute', bottom: 10, right: 5 }} onClick={() => {
                this.setState({ visible: true })
              }}>更多</Button>
            </div>
          </div>
          <div className={s.topTwo} style={rankingStyle}>

          </div>
          <div className={s.topTherr} style={configStyle}>

          </div>
        </div>
        <div className={s.bottom} style={rankingStyle}>

        </div>
        {visible ? <div>
          <Modal
            width={'90%'}
            style={{ paddingBottom: '0', top: '0' }}
            title="部门统计"
            visible={visible}
            footer={null}
            bodyStyle={{ padding: '10px', overflow: 'hidden', background: '#001529' }}
            centered={true}
            onCancel={() => {
              this.setState({ visible: false })
            }}
            wrapClassName={'replyData'}
          >
            <div>
              <BMTJ {...this.props} />
            </div>
          </Modal>
        </div> : null}
      </div>
    );
  }
}
export default index;
