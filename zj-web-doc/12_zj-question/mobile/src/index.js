import React from 'react'
import { render } from 'react-dom'
import { createStore, combineReducers, applyMiddleware } from 'redux'
import { Provider } from 'react-redux'
import { Router } from 'react-router-dom';
import { createHashHistory } from 'history'
import { routerMiddleware, routerReducer, syncHistoryWithStore } from 'react-router-redux'
import App from './App'
import { statusTypes,updateTime } from './actions'//, updateTime 
import reducers from './reducers'
import { localDB } from './tools';
const { GUEST } = statusTypes
const { get: getLocalDB, set: setLocalDB } = localDB
const history = createHashHistory()
const middleware = routerMiddleware(history)
const reducer = combineReducers({
  ...reducers,
  routing: routerReducer
})
const store = createStore(reducer, { status: getLocalDB("status") || GUEST }, applyMiddleware(middleware))

// const unsubscribe = 
store.subscribe(() => {
  // const states = store.getState();
  // console.log('getState:',states)
  // if (states && states.routing && states.routing.locationBeforeTransitions) {
  //   const { routing: { locationBeforeTransitions: { pathname } } } = states
  //   setLocalDB("lastPath", pathname)
  // }
})
syncHistoryWithStore(history, store)
// setInterval(() => { store.dispatch(updateTime()) }, 1000)
// 停止监听 state 更新
// unsubscribe();
let rootElement = document.getElementById('root')
setTimeout(() => {
  render(
    <Provider store={store}>
      <Router history={history}>
        <App />
      </Router>
    </Provider>,
    rootElement
  )
}, 0);//2000