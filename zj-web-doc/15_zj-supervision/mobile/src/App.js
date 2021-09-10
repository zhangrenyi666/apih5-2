import React, { Component } from 'react';
import { Switch, Route, Redirect, withRouter } from 'react-router-dom';
import { getRouteData } from "./routes"
import { app } from "./main"
import { Toast } from './components'
import { User, Corp } from './module';
import './App.less'
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      routes: []
    }
  }
  componentDidMount() {
    document.title = app.name
    Toast.hide()
    Corp.getCorpInfo().then(({ uri }) => {
      getRouteData().then(({ routeData, routeIdByPath }) => {
        let routes = []
        for (const key in routeData) {
          if (routeData.hasOwnProperty(key)) {
            const { path, redirect, component: RouteComponent } = routeData[key];
            if (redirect) {
              routes.push(<Route key={path} exact path={path} render={(props) => <Redirect to={redirect} />} />)
            } else {
              routes.push(<Route key={path} exact path={path} render={(props) => <RouteComponent {...props} uri={uri} routeData={routeData} routeIdByPath={routeIdByPath} curRoute={routeData[key]} />} />)
            }
          }
        }
        this.setState({ routes })
      })
    })
    window.addEventListener('message', function (e) {
      if (e.data && !e.data.success && e.data.code === "3003" || e.data.code === "3004") {
        User.logout()
      }
    })
  }
  render() {
    const { routes } = this.state
    if (routes.length > 0) {
      return (
        <Switch >
          {routes}
          <Route render={() => <Redirect to={`/error/404`} />} />
        </Switch>
      )
    } else {
      return (
        <div />
      )
    }
  }
}
export default withRouter(App);

