import React, { Component } from 'react';
import { HashRouter as Router, Switch, Route, Redirect } from 'react-router-dom';
import './App.less';
import { getAllowToPath } from './main'

import Home from './routes/Home';

import TitleList from './routes/title/TitleList';// 发票抬头列表
import TitleDetail from './routes/title/TitleDetail';// 发票抬头详情

import ErrPage from './routes/common/ErrPage';// 错误页面



class App extends Component {
  constructor(props) {
    super(props);
    document.getElementsByTagName("title")[0].innerHTML = `发票抬头`
  }
  render() {
    return (
      <Router>
        <Switch>
          <Route exact path={`/`} render={()=>{
              let { state, message } = getAllowToPath();
              if (state === 1) {
                return  <Home />
              } else {
                return <div><ErrPage state={state} message={message} /></div>
              }
          }}  />

          <Route path={`/Title`} render={({ match, history }) => {
            let { state, message } = getAllowToPath();
            if (state === 1) {
              return [
                <Route key={"otherList"} exact path={`${match.url}/List/:invoiceTitleListId`} render={({ match: { params: { invoiceTitleListId } } }) => {
                  return <Redirect to={`${match.url}/List/${invoiceTitleListId}/0`} />
                }} />,
                <Route key={"List"} exact path={`${match.url}/List/:invoiceTitleListId/:linkType`} component={TitleList} />,
                <Route key={"otherDetail"} exact path={`${match.url}/Detail/:invoiceTitleListId/:invoiceTitleId`} render={({ match: { params: { invoiceTitleListId, invoiceTitleId, invoiceId } } }) => {
                  return <Redirect to={`${match.url}/Detail/${invoiceTitleListId}/${invoiceTitleId}/0`} />
                }} />,
                <Route key={"Detail"} exact path={`${match.url}/Detail/:invoiceTitleListId/:invoiceTitleId/:linkType`} component={TitleDetail} />
              ].map(item => item)
            } else {
              return <div><ErrPage state={state} message={message} /></div>
            }

          }} />
        </Switch>
      </Router>
    );
  }
}
export default App;
