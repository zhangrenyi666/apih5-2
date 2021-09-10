// import React from 'react';

//注意antd和antd-mobile如有一样名字的组件在移动端组件名字前面加上Mobile名字
//ui组件
//暂不可用，某些组件这么导出存在问题
export {
    Input,
    Form,
    Icon,
    Upload,
    InputNumber,
    DatePicker,
    TimePicker,
    Select,
    Spin,
    Cascader,
    message,
    Radio,
    Checkbox,
    Switch,
    Slider,
    Rate
} from "antd"; 
// import { DatePicker,List } from "antd-mobile";

// export { DatePicker as MobileDatePicker, List }

//其他组件
export { default as PullPerson } from "../../pullPersion"
export { default as PullPersonMobile } from "../../pullPersionMobile"
export { default as Tree } from "../../tree"
export { default as QnnTable } from "../../qnn-table"
