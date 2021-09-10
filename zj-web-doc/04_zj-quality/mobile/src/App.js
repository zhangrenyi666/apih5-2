import React from 'react';
import { connect } from 'react-redux'
import { Switch } from 'react-router-dom';
import { ConnectedRouter } from 'connected-react-router'
import MyRouter from './lny_modules/Router'
const App = (props) => {
  const { history, corpInfo: { routerInfo = [] } } = props
  return (
    <ConnectedRouter history={history}>
      <Switch>
        {MyRouter.createRoutes(routerInfo)}
      </Switch>
    </ConnectedRouter>
  )
}
const selectApp = (state) => {
  return {
    corpInfo: state.corpInfo,
  }
}


export default connect(selectApp)(App);

