import React, { Component } from "react";
import { Modal, Select, Divider, InputNumber } from 'antd';
import { NavBar, Toast } from 'antd-mobile';
import MyList from "../modules/MList";
import s from './style.less';
const { Option } = Select;
class componentName extends Component {
  state = {
    flag: true,
    data: [],
    visible: true,
    visibles: false,
    assessmentId: '',
    item: {},
    companyScore: 0
  }
  componentDidMount() {
    const { myFetch } = this.props;
    myFetch('getZjScoreAnnualAssessmentListByAuditor', {}).then(({ success, data, message }) => {
      if (success) {
        this.setState({
          data: data
        }, () => {
          if (this.state.data.length) {
            this.setState({ flag: false });
          }
        })
      } else {
        Toast.fail(message);
      }
    })
  }
  handleOk = () => {
    if (this.state.assessmentId === '') {
      Toast.fail('请选择打分活动!');
    } else {
      this.setState({
        visible: false
      });
    }
  }
  handleCancel = () => {
    this.setState({
      visibles: false
    })
  }
  handleOks = () => {
    const { myFetch } = this.props;
    const { item, companyScore } = this.state;
    let body = {
      ...item,
      companyScore
    };
    myFetch('batchUpdateZjScoreCompanyDetail', [body]).then(({ success, data, message }) => {
      if (success) {
        this.MyList.onRefresh();
        this.setState({ visibles: false });
      } else {
        Toast.fail(message);
      }
    })
  }
  onRef = (ref) => {
    this.MyList = ref
  }
  render() {
    const { data, visible, visibles, flag, companyScore } = this.state;
    return (
      <div className={s.root}>
        {!visible ? <div className={s.center}>
          <div>
            <NavBar
              style={{ width: "100%" }}
              mode="dark"
            >
              {"打分活动列表"}
            </NavBar>
          </div>
          <div
            style={{
              height: window.innerHeight - 45
            }}
          >
            <MyList
              myFetch={this.props.myFetch} //ajax方法必须返回一个promise
              searchKey="companyShortName" //搜索时的key
              fetchConfig={{
                apiName: 'getZjScoreCompanyDetailListByAuditor', //后台api
                otherParams: { assessmentId: this.state.assessmentId }
              }}
              onRef={this.onRef}
              Item={props => {
                //列表模板 props里有所有数据
                const { rowData, rowID } = props;
                const item = rowData;
                const index = rowID;
                return (
                  <div
                    className={s.centers}
                    style={{
                      borderLeft: `3px solid ${
                        index % 2 === 0 ? "#ff4000" : "#ff9900"
                        }`
                    }}
                    key={index}
                    onClick={() => {
                      this.setState({
                        companyScore: item.companyScore,
                        item,
                        visibles: true
                      })
                    }}
                  >
                    <div style={{ paddingLeft: '3%' }}>
                      单位全称：{item.companyFullName}
                    </div>
                    <Divider style={{ margin: "8px 0px", backgroundColor: '#1890ff' }} />
                    <div className={s.top}>
                      <div className={s.topl}>单位简称：{item.companyShortName}</div>
                      <div className={s.topr}>类别：{item.moduleType === '0' ? '一公局自建' : (item.moduleType === '1' ? '隧道局自建' : '集团统建')}</div>
                    </div>
                    <Divider style={{ margin: "8px 0px" }} />
                    <div className={s.top}>
                      <div className={s.topl}>打分项：{item.content}</div>
                      <div className={s.topr}>分数：{item.companyScore || item.companyScore === 0 ? item.companyScore : '-'}</div>
                    </div>
                  </div>
                );
              }}
            />
          </div>
        </div> : null}
        <Modal
          title="请选择打分活动"
          visible={visible}
          centered={true}
          closable={false}
          maskClosable={false}
          onOk={this.handleOk}
          okButtonProps={{ disabled: flag }}
          cancelButtonProps={{ disabled: true }}
        >
          {data && data.length ? <div>打分活动：<Select placeholder='请选择' style={{ width: 200 }} onSelect={(assessmentId) => {
            this.setState({ assessmentId });
          }}>
            {
              data.map((item, index) => {
                return <Option key={index} value={item.assessmentId}>{item.title}</Option>
              })
            }
          </Select></div> : <div>暂无打分活动...</div>}
        </Modal>
        <Modal
          title="请打分"
          visible={visibles}
          centered={true}
          onOk={this.handleOks}
          onCancel={this.handleCancel}
        >
          {<div style={{ textAlign: 'center' }}>分数：<InputNumber min={0} value={companyScore} placeholder="请输入" onChange={(companyScore) => {
            this.setState({ companyScore });
          }} /></div>}
        </Modal>
      </div>
    );
  }
}
export default componentName;
