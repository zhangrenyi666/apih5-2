(window.webpackJsonp=window.webpackJsonp||[]).push([[81],{"96eo":function(e,a,s){"use strict";var l=s("UpC8"),o=s("Id3l"),n=s("RNsw"),t=s("OR/T"),i=s("bn5B"),p=s("PnTt"),r=s("r0ML"),d=s.n(r),m=s("tjlV"),h={workFlowConfig:{title:["","registerPerson","\u73b0\u91d1\u6d41\u91cf\u8868"],apiNameByAdd:"updateZjTzCash",apiNameByUpdate:"updateZjTzCash",apiNameByGet:"getZjTzCashDetails",apiTitle:"getZjTzFlowTitle",flowId:"zjTzCash",todo:"TodoHasTo",hasTodo:"TodoHasToq"}},c=function(e){function a(){return Object(o.a)(this,a),Object(t.a)(this,Object(i.a)(a).apply(this,arguments))}return Object(p.a)(a,e),Object(n.a)(a,[{key:"render",value:function(){var e=this.props.isInQnnTable,a=this.props.flowData,s=void 0===a?{}:a;return d.a.createElement("div",{style:{height:e?"":"100vh"}},d.a.createElement(m.a,Object(l.a)({},this.props,h,{formConfig:[{field:"cashId",type:"string",placeholder:"\u8bf7\u8f93\u5165",initialValue:s.cashId?s.cashId:"",hide:!0},{field:"flowId",type:"string",placeholder:"\u8bf7\u8f93\u5165",initialValue:"zjTzCash",hide:!0},{field:"projectId",type:"string",placeholder:"\u8bf7\u8f93\u5165",initialValue:s.projectId?s.projectId:"",hide:!0},{field:"projectName",type:"string",qnnDisabled:!0,label:"\u9879\u76ee\u540d\u79f0",placeholder:"\u8bf7\u8f93\u5165",initialValue:s.projectName?s.projectName:"",span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{label:"\u5e74\u6708",type:"month",qnnDisabled:!0,initialValue:s.yearMonthDate?s.yearMonthDate:"",field:"yearMonthDate",span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{field:"registerDate",type:"date",label:"\u767b\u8bb0\u65e5\u671f",required:!0,qnnDisabled:!0,initialValue:s.registerDate?s.registerDate:"",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{field:"registerPerson",type:"string",qnnDisabled:!0,label:"\u767b\u8bb0\u7528\u6237",required:!0,initialValue:s.registerPerson?s.registerPerson:"",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{field:"opinionField1",type:"textarea",initialValue:s.contentDesc?s.contentDesc:"",label:"\u9879\u76ee\u516c\u53f8\u610f\u89c1",required:!0,placeholder:"\u8bf7\u8f93\u5165",formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}},{type:"textarea",label:"\u6258\u7ba1\u516c\u53f8",field:"opinionField2",opinionField:!0,addShow:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}},{type:"textarea",label:"\u6295\u8d44\u4e8b\u4e1a\u90e8\u5ba1\u6838",field:"opinionField3",opinionField:!0,addShow:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}},{type:"textarea",label:"\u5c40\u5404\u90e8\u95e8\u8bc4\u5ba1",field:"opinionField4",opinionField:!0,addShow:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}},{label:"\u9644\u4ef6",hide:!0,field:"zjTzFileOpinionList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u73b0\u91d1\u6d41\u91cf\u8868"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}}]},this.props.workFlowConfig,h.workFlowConfig)))}}]),a}(r.Component);a.a=c}}]);