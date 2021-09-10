import React, { Component } from 'react'

export default class Test extends Component {
    componentDidMount() {
        this.refs.test.addEventListener('unload',()=>{
            alert(123)
        } )
        function* countAppleSales() {
             yield '哈哈';
             yield '嘿嘿';
             yield '打断点';
        }

        var appleStore = countAppleSales(); // Generator { }  
        do { 
            var ii = appleStore.next()
            console.log(ii.value);
        } while (!ii.done); 
    }  

    render() {
        return (
            <div ref="test">
                Test
            </div>
        )
    }
}
