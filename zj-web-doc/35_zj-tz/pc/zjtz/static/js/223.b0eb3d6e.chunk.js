(window.webpackJsonp=window.webpackJsonp||[]).push([[223],{"5qqc":function(e,t,i){"use strict";i.r(t);i("IdsT"),i("l9AF");var n=i("UpC8"),a=(i("fx2i"),i("cZi0")),o=i("Id3l"),r=i("RNsw"),l=i("OR/T"),s=i("bn5B"),d=i("PnTt"),c=i("r0ML"),p=i.n(c),m=i("6eYn"),f=i("U32T"),u=i("bsUV"),h=i.n(u),w={antd:{rowKey:function(e){return e.lifeCycleOpinionId},size:"small",scroll:{y:.65*document.documentElement.clientHeight}},drawerConfig:{width:"700px"},limit:99999,curPage:1,paginationConfig:!1,firstRowIsSearch:!1,isShowRowSelect:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},b=function(e){function t(e){var i;return Object(o.a)(this,t),(i=Object(l.a)(this,Object(s.a)(t).call(this,e))).state={lifeCycleId:e.match.params.lifeCycleId||"",number:e.match.params.number||"",apiName:"0"===e.match.params.number?"getZjTzLifeCycleChangeOpinionAndProIdList":"getZjTzLifeCycleOpinionList"},i}return Object(d.a)(t,e),Object(r.a)(t,[{key:"componentDidMount",value:function(){}},{key:"tdEditCb",value:function(e){var t=this;(0,this.props.myFetch)("updateZjTzPppTermReply",e.newRowData).then(function(e){var i=e.success,n=e.message;i?(a.b.success(n),t.table.refresh()):a.b.error(n)})}},{key:"render",value:function(){var e=this,t=this.state.lifeCycleId;return p.a.createElement("div",{className:h.a.root},p.a.createElement(m.a,Object(n.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(t){e.table=t}},w,{fetchConfig:{apiName:this.state.apiName,otherParams:{lifeCycleId:t}},formConfig:[{isInTable:!1,form:{label:"\u4e3b\u952eid",field:"lifeCycleOpinionId",hide:!0}},{table:{title:"\u8bc4\u5ba1\u6b21\u6570",dataIndex:"reviewNumber",width:120,key:"reviewNumber",render:function(e,t,i){return{children:p.a.createElement("div",null,"\u7b2c",e,"\u6b21"),props:{rowSpan:Number(t.count)}}}},isInForm:!1},{table:{title:"\u8bc4\u5ba1\u90e8\u95e8",width:160,dataIndex:"reviewerDeptName",key:"reviewerDeptName"},isInForm:!1},{table:{title:"\u8bc4\u5ba1\u4eba",width:120,dataIndex:"reviewer",key:"reviewer"},isInForm:!1},{table:{title:"\u8bc4\u5ba1\u610f\u89c1",dataIndex:"opinion",tooltip:80,width:"30%",key:"opinion"},isInForm:!1},{table:{title:"\u8bc4\u5ba1\u53d1\u8d77\u65f6\u95f4",dataIndex:"reviewStartTime",width:140,format:"YYYY-MM-DD",key:"reviewStartTime"},isInForm:!1},{table:{title:"\u8bc4\u5ba1\u7ed3\u675f\u65f6\u95f4",width:140,dataIndex:"reviewEndTime",format:"YYYY-MM-DD",key:"reviewEndTime"},isInForm:!1},{table:{title:"\u9644\u4ef6",dataIndex:"zjTzFileOpinionList",width:100,onClick:"detail",key:"zjTzFileOpinionList",render:function(e){return p.a.createElement("a",null,"\u67e5\u770b\u9644\u4ef6")}},form:{label:"\u9644\u4ef6",field:"zjTzFileOpinionList",type:"files",showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u5168\u751f\u547d\u5468\u671f\u7b56\u5212"}}}}],actionBtns:[{name:"goback",type:"dashed",label:"\u8fd4\u56de",isValidate:!1,onClick:function(t){(0,e.props.dispatch)(Object(f.c)())}}]})))}}]),t}(c.Component);t.default=b}}]);