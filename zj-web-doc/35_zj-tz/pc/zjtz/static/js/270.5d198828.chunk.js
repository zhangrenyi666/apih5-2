(window.webpackJsonp=window.webpackJsonp||[]).push([[270],{b9TP:function(e,t,n){"use strict";n.r(t);var a=n("UpC8"),o=n("Id3l"),r=n("RNsw"),i=n("OR/T"),p=n("bn5B"),s=n("PnTt"),l=n("r0ML"),d=n.n(l),c=n("6eYn"),f={antd:{rowKey:function(e){return e.id},size:"small"},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},m=function(e){function t(){return Object(o.a)(this,t),Object(i.a)(this,Object(p.a)(t).apply(this,arguments))}return Object(s.a)(t,e),Object(r.a)(t,[{key:"render",value:function(){var e=this;return d.a.createElement("div",null,d.a.createElement(c.a,Object(a.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token}},f,{fetchConfig:{apiName:"getProInvBasicList"},method:{editClick:function(){e.table.clearSelectedRows()}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var t=e.Pprops;return{menuParentId:t.routerInfo.routeData[t.routerInfo.curKey]._value,tableField:"projectInfo"}}},wrappedComponentRef:function(t){e.table=t},formConfig:[{isInTable:!1,form:{field:"id",type:"string",hide:!0}},{isInForm:!1,table:{width:80,align:"center",title:"\u5e8f\u53f7",dataIndex:"no",key:"no",render:function(e,t,n){return n+1}}},{table:{title:"\u96c6\u91c7\u9879\u76ee\u540d\u79f0",dataIndex:"proName",key:"proName",width:"50%",filter:!0,onClick:"detail"},form:{type:"string",field:"proName",placeholder:"\u8bf7\u8f93\u5165",addDisabled:!0,editDisabled:!0,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}},{table:{title:"\u672c\u9879\u76ee\u540d\u79f0",filter:!0,width:"50%",dataIndex:"projectId",key:"projectId",type:"select"},form:{field:"projectId",type:"select",showSearch:!0,required:!1,optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"},placeholder:"\u8bf7\u9009\u62e9",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}}]})))}}]),t}(l.Component);t.default=m}}]);