import React from 'react';
import QnnTable from './index.js';
import Enzyme,{ shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16'; 

Enzyme.configure({
    adapter:new Adapter()
});


describe("QnnTable", ()=>{
    
    //actionBtns测试
    describe("actionBtns", ()=>{
 
        it("新增按钮测试（name=add） ----点击测试", ()=>{
            const app = shallow(<QnnTable />);
            
            
        })

    })

})