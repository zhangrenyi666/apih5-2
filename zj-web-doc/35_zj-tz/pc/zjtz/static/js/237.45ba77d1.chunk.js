(window.webpackJsonp=window.webpackJsonp||[]).push([[237],{PRUH:function(e,t,a){"use strict";a.r(t);a("IdsT"),a("l9AF");var n=a("UpC8"),i=(a("07Zm"),a("2HLw")),o=a("Id3l"),s=a("RNsw"),r=a("OR/T"),c=a("bn5B"),d=a("PnTt"),p=a("r0ML"),l=a.n(p),m=a("6eYn"),h=a("U32T"),u=a("U8v4"),I=a("zt2q"),f=a.n(I),b={antd:{rowKey:function(e){return e.policyId},size:"small"},drawerConfig:{width:"900px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!1},y=function(e){function t(e){var a;return Object(o.a)(this,t),(a=Object(r.a)(this,Object(c.a)(t).call(this,e))).cancelBtn=function(){(0,a.props.dispatch)(Object(h.c)())},a.state={videoId:e.match.params.videoId},a}return Object(d.a)(t,e),Object(s.a)(t,[{key:"componentDidMount",value:function(){var e=this.props,t=e.dispatch,a=e.myPublic.appInfo.mainModule;this.state.videoId||t(Object(u.push)("".concat(a,"PublicityVideo")))}},{key:"render",value:function(){var e=this.state.videoId;return l.a.createElement("div",{className:f.a.root},l.a.createElement("div",{style:{marginBottom:"10px"}},l.a.createElement(i.a,{type:"dashed",onClick:this.cancelBtn},"\u8fd4\u56de")),l.a.createElement(m.a,Object(n.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},fetchConfig:{apiName:"getZjTzVideoHistoryList",otherParams:function(t){return{videoId:e}}}},b,{actionBtns:[],formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}},formConfig:[{isInTable:!1,form:{field:"policyId",type:"string",hide:!0}},{isInSearch:!1,table:{title:"\u6807\u9898",dataIndex:"title",width:300,key:"title"},isInForm:!1},{isInSearch:!1,table:{title:"\u6240\u5c5e\u9879\u76ee",dataIndex:"projectName",key:"projectName"},isInForm:!1},{table:{title:"\u6d4f\u89c8\u8005",dataIndex:"createUserName",key:"createUserName"},isInForm:!1},{table:{title:"\u6d4f\u89c8\u65e5\u671f",dataIndex:"createTime",key:"createTime",format:"YYYY-MM-DD"},isInForm:!1}]})))}}]),t}(p.Component);t.default=y}}]);