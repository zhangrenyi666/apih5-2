import React from 'react';
import { Redirect } from 'react-router-dom';
import { blank } from '../modules/layouts';
const Home = (props) => {
    const { myPublic: { appInfo: { mainModule } }} = props
    return (
        <Redirect to={`${mainModule}meetingMaterialList`}/>
    )
}
export default blank(Home)