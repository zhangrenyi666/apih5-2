import React, { Component } from 'react';
import { basic } from '../modules/layouts'
import DataTableByZJ from './DataTableByZJs'

const Home = (props) => {
    return (
        <DataTableByZJ {...props} />
    )
}



export default basic(Home)