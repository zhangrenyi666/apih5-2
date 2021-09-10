import React, { Component } from 'react';
import { HashRouter as Router, Switch, Route } from 'react-router-dom';//Redirect
import './App.less';
import { getAllowToPath } from './main'

import Home from './routes/Home/Home.js';
import Select from './routes/Select/index';
import ErrPage from './common/ErrPage'

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route exact path={`/`} render={(props) => {
            getAllowToPath()
            return <div />
          }} />
          <Route path={`/select`} render={(props) => {
            let { state, message } = getAllowToPath();  
            if (state === 1) {
              return <Select {...props} />
            }else{
              return <div><ErrPage state={state} message={message} /></div>
            }
          }} />
          <Route exact path={`/:year/:month/:flag`} render={(props) => {//flag 0工资详情  1绩效详情
            let { state, message } = getAllowToPath();
            if (state === 1) {
              return <Home {...props} />
            }else{
              return <div><ErrPage state={state} message={message} /></div>
            }
          }} />
        </Switch>
      </Router>
    );
  }
}
export default App;
