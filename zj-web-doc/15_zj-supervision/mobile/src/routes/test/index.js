import React, { Component } from 'react'

export default class Test extends Component {
    componentDidMount(){
        let canvas = document.querySelectorAll('canvas');
        let ctx = canvas.getContent('2d');
        console.log(canvas)
    }
    render() {
        return (
             <div>  
                <canvas width="500" style={{border:'1px solid red'}}>
                </canvas>
             </div>
        )
    }
}
