import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
class Home extends Component {
    componentDidMount() {
        document.getElementsByTagName("title")[0].innerHTML = `发票中心`
    }
    render() {
        return (
         <Redirect to="/Title/List/1/1"/>
        )
    }
}
export default Home;