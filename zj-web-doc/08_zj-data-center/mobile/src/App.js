import React, { Component } from 'react';
import { HashRouter as Router, Switch, Route, Redirect, Link } from 'react-router-dom';
import './App.less';
import { getAllowToPath, initWxJsSdk } from './main'; //,  

import ErrPage from './routes/common/ErrPage';// 错误页面
import ImgPreview from './routes/common/ImgPreview';

import TurnoverExamineAdd from './routes/TurnoverExamineAdd';// 出入机房审批新增
import TurnoverExamineDetail from './routes/TurnoverExamineDetail';// 出入机房审批详情
import TurnoverExamineList from './routes/TurnoverExamineList';// 出入机房审批列表
import TurnoverApplyList from './routes/TurnoverApplyList';// 出入机房申请列表

class App extends Component {
  constructor(props) {
    super(props);
    initWxJsSdk();
  }

  render() {  
    // const { lnyConfig: { appInfo: { TYPE: type } } } = window;
    return (
      <Router>
        <Switch>
          <Route exact path={`/`} render={() => {
            const { state, message } = getAllowToPath();
            if (state === 1) {
              return (<div> </div>)
            } else {
              return (<ErrPage state={state} message={message} />)
            }
          }} />
          <Route exact path={`/pages`} render={() => {
            return <div>
              <li><Link to="/page/TurnoverExamineAdd">出入机房审批新增</Link></li>
              <li><Link to="/page/TurnoverExamineDetail/test">出入机房审批详情</Link></li>
              <li><Link to="/page/TurnoverExamineList">出入机房审批列表</Link></li>
              <li><Link to="/page/TurnoverApplyList">出入机房申请列表</Link></li>
            </div>
          }} />
          <Route exact path={`/ImgPreview/:src`} component={ImgPreview} />
          <Route path={'/page'} render={({ match }) => {// 
            const { state, message } = getAllowToPath()
            if (state === 1) {
              return (
                <div>
                  <Route exact path={`${match.url}/TurnoverExamineAdd`} component={TurnoverExamineAdd} />
                  <Route exact path={`${match.url}/TurnoverExamineDetail/:approvalId/:flag`} component={TurnoverExamineDetail} />
                  <Route exact path={`${match.url}/TurnoverExamineList`} component={TurnoverExamineList} />
                  <Route exact path={`${match.url}/TurnoverApplyList`} component={TurnoverApplyList} />
                </div>)
            } else {
              return (<ErrPage state={state} message={message} />)
            }
          }} />
        </Switch>
      </Router>
    );
  }
}
export default App;
