import React, { Component } from 'react';
import { myFetch } from '../../tools';
const newPage = () => {
    myFetch("queryAll", {}).then((json) => {
        console.log(json)
        const { success, data } = json
        if (success) {

        }
    })
    return <div />
}
export default newPage

