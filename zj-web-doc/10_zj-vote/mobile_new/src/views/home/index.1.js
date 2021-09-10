import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { push } from 'connected-react-router'
import { connect } from 'react-redux'
import { List, Grid } from 'antd-mobile'
import { basic, blank } from '../modules/layouts'
const { Item } = List
const { Brief } = Item;

const Home = (props) => {
    const { dispatch, loginAndLogoutInfo: { loginInfo: { userInfo: { realName } } }, myPublic: { URI, appInfo: { mainModule } }, myLogout, routerInfo: { routeData, curKey } } = props
    const { pathName, moduleName } = routeData[curKey]
    const data = [
        {
            icon: 'http://192.168.1.192:5000/发起.png',
            text: `流程发起`,
            onClick: () => {
                dispatch(push(`${mainModule}approveList`))
            }
        },
        {
            icon: 'http://192.168.1.192:5000/已办.png',
            text: `流程列表`,
            onClick: () => {
                dispatch(push(`${mainModule}tabsListMobile/0`))
            }
        },
        {
            icon: 'http://192.168.1.192:5000/退出.png',
            text: `退出`,
            onClick: () => {
                myLogout()
            }
        }
    ]
    return (
        // <Redirect to={`${mainModule}tabsListMobile/1`}/>
        <Grid onClick={(el, index) => { data[index].onClick() }} columnNum={2}  data={data}  />
    )
}



export default blank(Home)