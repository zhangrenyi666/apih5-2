import React from 'react';
import { connect } from 'react-redux'
import { localDB } from '../../tools';
const { get: getLocalDB } = localDB
const Home = ({ name, src }) => {
    const code = getLocalDB("token")
    return (
        <iframe title={name} name={name} frameBorder="0" height="100%" width="100%" src={`${src}?code=${code}`} />
    )
}
const select = (state) => {
    return {
        ...state
    }
}
export default connect(select)(Home)
