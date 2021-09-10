import React from 'react';
import { Redirect } from 'react-router-dom';
const Home = (props) => {
    const { myPublic: { appInfo: { mainModule } }} = props
    return (
        <Redirect to={`${mainModule}todo`}/>
    )
}
export default Home;
