(window.webpackJsonp=window.webpackJsonp||[]).push([[88],{tESR:function(e,a,l){"use strict";l("Z5ek"),l("IdsT"),l("l9AF");var i=l("UpC8"),t=l("Id3l"),s=l("RNsw"),o=l("OR/T"),n=l("bn5B"),p=l("PnTt"),r=l("r0ML"),m=l.n(r),d=l("tjlV"),u={workFlowConfig:{title:["","","\u76d1\u4e8b\u4f1a"],apiNameByAdd:"updateZjTzThreeSupervisorForFlow",apiNameByUpdate:"updateZjTzThreeSupervisorForFlow",apiNameByGet:"getZjTzThreeSupervisorDetails",flowId:"zjTzThreeSupervisor",todo:"TodoHasTo",hasTodo:"TodoHasToq"}},f=function(e){function a(){return Object(t.a)(this,a),Object(o.a)(this,Object(n.a)(a).apply(this,arguments))}return Object(p.a)(a,e),Object(s.a)(a,[{key:"render",value:function(){var e=this.props.isInQnnTable,a=this.props.flowData,l=void 0===a?{}:a;return m.a.createElement("div",{style:{height:e?"":"100vh"}},m.a.createElement(d.a,Object(i.a)({},this.props,u,{formConfig:[{field:"threeSupervisorId",type:"string",placeholder:"\u8bf7\u8f93\u5165",initialValue:l.threeSupervisorId?l.threeSupervisorId:"",hide:!0},{field:"projectId",type:"select",qnnDisabled:!0,label:"\u9879\u76ee\u540d\u79f0",placeholder:"\u8bf7\u8f93\u5165",initialValue:l.projectId?l.projectId:"",optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"},span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}},{label:"\u671f\u6b21",type:"select",required:!0,field:"periodId",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiJieCi"}},qnnDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",initialValue:l.periodId?l.periodId:"",span:6,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"select",field:"meetNumberId",qnnDisabled:!0,initialValue:l.meetNumberId?l.meetNumberId:"",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiQiCi"}},placeholder:"\u8bf7\u8f93\u5165",required:!0,span:6,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{label:"\u4f1a\u8bae\u65f6\u95f4",type:"date",qnnDisabled:!0,initialValue:l.meetDate?l.meetDate:"",field:"meetDate",placeholder:"\u8bf7\u8f93\u5165",required:!0,span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}},{label:"\u4f1a\u8bae\u7c7b\u578b",type:"select",qnnDisabled:!0,field:"meetTypeId",initialValue:l.meetTypeId?l.meetTypeId:"",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiLeiXing"}},span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}},{label:"\u4f1a\u8bae\u8868\u51b3\u65b9\u5f0f",type:"select",qnnDisabled:!0,field:"meetVoteId",initialValue:l.meetVoteId?l.meetVoteId:"",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiBiaoJueFangShi"}},span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}},{label:"\u539f\u4ef6\u662f\u5426\u5907\u6848",type:"select",qnnDisabled:!0,field:"originalId",initialValue:l.originalId?l.originalId:"",optionData:[{label:"\u662f",value:"1"},{label:"\u5426",value:"0"}],span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}},{label:"\u4f1a\u8bae\u5730\u70b9",type:"textarea",initialValue:l.meetPlace?l.meetPlace:"",qnnDisabled:!0,field:"meetPlace",formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},{label:"\u4f1a\u8bae\u901a\u77e5\u6587\u4ef6",qnnDisabled:!0,field:"zjTzFileList1",initialValue:l.zjTzFileList1?l.zjTzFileList1:"",type:"files",fetchConfig:{apiName:window.configs.domain+"upload"},formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},{label:"\u8bae\u6848\u8d44\u6599",qnnDisabled:!0,field:"zjTzFileList2",initialValue:l.zjTzFileList2?l.zjTzFileList2:"",type:"files",fetchConfig:{apiName:window.configs.domain+"upload"},formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},{label:"\u51b3\u8bae\u53ca\u4f1a\u8bae\u7eaa\u8981",qnnDisabled:!0,field:"zjTzFileList3",initialValue:l.zjTzFileList3?l.zjTzFileList3:"",type:"files",fetchConfig:{apiName:window.configs.domain+"upload"},formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},{field:"opinionField1",type:"textarea",initialValue:l.contentDesc?l.contentDesc:"",label:"\u9879\u76ee\u516c\u53f8\u610f\u89c1",required:!0,placeholder:"\u8bf7\u8f93\u5165",formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},{type:"textarea",label:"\u6258\u7ba1\u516c\u53f8",field:"opinionField2",opinionField:!0,addShow:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},{type:"textarea",label:"\u6295\u8d44\u4e8b\u4e1a\u90e8\u5ba1\u6838",field:"opinionField3",opinionField:!0,addShow:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},{type:"textarea",label:"\u5c40\u5404\u90e8\u95e8\u8bc4\u5ba1",field:"opinionField4",opinionField:!0,addShow:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}]},this.props.workFlowConfig,u.workFlowConfig,{fieldsCURD:function(e,a,l){return e.map(function(e){var a=e.field,i=l.match.url,t=l.myPublic.appInfo.mainModule;return i==="".concat(t,"TodoHasTo")&&("meetDate"!==a&&"meetTypeId"!==a&&"meetVoteId"!==a&&"originalId"!==a&&"meetPlace"!==a&&"zjTzFileList1"!==a&&"zjTzFileList2"!==a&&"zjTzFileList3"!==a||(e.disabled=!1)),e})}})))}}]),a}(r.Component);a.a=f}}]);