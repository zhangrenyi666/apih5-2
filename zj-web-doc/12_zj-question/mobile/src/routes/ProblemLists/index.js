import React, { Component } from "react";
import MyList from "../modules/MListA";
import { Divider } from "antd";
import { myFetch } from '../../tools';
import s from "./index.less";
import { NavBar, Icon, Badge } from "antd-mobile";
import { goBack } from 'connected-react-router';
import { push } from 'react-router-redux';
import moment from 'moment';
class Index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      height: document.documentElement.clientHeight - 44 - 43 - 45 - 1,
    };
  }
  render() {
    const { dispatch } = this.props;
    return (
      <div className={s.root}>
        <div>
          <NavBar
            style={{ width: "100%" }}
            mode="dark"
            icon={<Icon type="left" />}
            onLeftClick={() => {
              dispatch(goBack());
            }}
          >
            {"待整改项列表"}
          </NavBar>
        </div>
        <div
          style={{
            height: window.innerHeight - 45
          }}
        >
          <MyList
            myFetch={myFetch} //ajax方法必须返回一个promise
            searchKey="departmentName" //搜索时的key
            fetchConfig={{
              apiName: 'getZjQuestionApprovalListWechat', //后台api
            }}
            {...this.props}
            Item={props => {
              //列表模板 props里有所有数据
              const { rowData, rowID } = props;
              const item = rowData;
              const index = rowID;
              let questionStateText = '';
              let color = '';
              switch (item.questionState) {
                case '0':
                  questionStateText = '未提交';
                  color = '#ff9900'
                  break;
                case '1':
                  questionStateText = '初审中';
                  color = '#99cc00'
                  break;
                case '2':
                  questionStateText = '初审通过'
                  color = '#007bc7'
                  break;
                case '3':
                  questionStateText = '初审失败'
                  color = 'orange'
                  break;
                case '4':
                  questionStateText = '通过审核'
                  color = 'gray'
                  break;
                case '5':
                  questionStateText = '复审驳回'
                  color = '#ff9900'
                  break;
                case '6':
                  questionStateText = '复审驳回'
                  color = '#99cc00'
                  break;
                default:
                  questionStateText = '未知'
                  color = 'gray'
                  break;
              }
              return (
                <div
                  className={s.center}
                  key={index}
                  style={{
                    borderLeft: `3px solid ${
                      index % 2 === 0 ? "#ff4000" : "#ff9900"
                      }`
                  }}
                  onClick={() => {
                    dispatch(push(`/problemDetail/${item.recordid}/${item.personnelFlag}`))
                  }}
                >
                  <div>
                    {`【${item.questionTitleName || item.questionTitleText}】`}
                    {
                      < Badge text={questionStateText}
                        style={{
                          padding: '0 3px',
                          borderRadius: '2px',
                          border: '1px solid ' + color,
                          backgroundColor: 'transparent',
                          color: color,
                        }}
                      />
                    }
                  </div>
                  <Divider style={{ margin: "8px 0px", backgroundColor: '#1890ff' }} />
                  <div style={{ paddingLeft: '3%' }}>
                    {item.questionDescribe}
                  </div>
                  <Divider style={{ margin: "8px 0px" }} />
                  <div style={{color:"#999"}}>来&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自：{item.checkDepartmentName}</div>
                  <div style={{color:"#999"}}>提&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;&nbsp;者：{item.checkDepartmentName}</div>
                  <div style={{color:"#999"}}>提&nbsp;出&nbsp;时&nbsp;间：{moment(item.modifyTime).format('YYYY-MM-DD HH:mm:ss')}</div>
                </div>
              );
            }}
          />
        </div>
      </div>
    );
  }
}
export default Index;
