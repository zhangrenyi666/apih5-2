(window.webpackJsonp=window.webpackJsonp||[]).push([[189],{EjUa:function(e,t,n){"use strict";n.r(t);n("9U97");var a=n("UpC8"),o=n("Id3l"),i=n("RNsw"),r=n("OR/T"),d=n("bn5B"),l=n("PnTt"),c=(n("w9C0"),n("XW+2")),p=n("r0ML"),s=n.n(p),f=n("6eYn"),u=n("U8v4"),m=(c.a.confirm,{antd:{rowKey:function(e){return e.id},size:"small"},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!1}),h=function(e){function t(e){var n;return Object(o.a)(this,t),(n=Object(r.a)(this,Object(d.a)(t).call(this,e))).handleCancelSend=function(){n.setState({visibleSend:!1,loadingSend:!1})},n.state={id:""},n}return Object(l.a)(t,e),Object(i.a)(t,[{key:"componentDidMount",value:function(){}},{key:"render",value:function(){var e=this,t=this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId;return s.a.createElement("div",null,s.a.createElement(f.a,Object(a.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(t){e.table=t}},m,{fetchConfig:{apiName:"getZjTzInvXmhgqkMonthlyReportListBasicData",otherParams:{projectId:t}},formConfig:[{isInTable:!1,form:{field:"id",type:"string",hide:!0}},{isInForm:!1,table:{width:300,tooltip:23,title:"\u9879\u76ee\u7f16\u53f7",dataIndex:"proNum",key:"proNum"}},{table:{title:"\u7ba1\u7406\u5355\u4f4d",filter:!0,width:300,dataIndex:"comname",key:"comname"},form:{field:"comname",type:"string",showSearch:!0,addDisabled:!0,disabled:!0,editDisabled:!0}},{table:{title:"\u9879\u76ee\u540d\u79f0",width:300,dataIndex:"proName",key:"proName"}},{table:{title:"\u5e74\u6708",dataIndex:"periodValue",key:"periodValue"},form:{type:"string",field:"periodValue"}},{table:{title:"\u586b\u62a5\u65e5\u671f",format:"YYYY-MM-DD",dataIndex:"createDate",key:"createDate"},form:{type:"date",field:"createDate"}},{isInForm:!1,table:{title:"\u64cd\u4f5c",fixed:"right",dataIndex:"action",key:"action",align:"center",noHaveSearchInput:!0,showType:"tile",width:120,btns:[{name:"PolicyDetail",render:function(e){return"<a>\u660e\u7ec6</a>"},onClick:function(e){var t=e.props.myPublic.appInfo.mainModule,n=e.rowData,a=n.proId,o=n.periodValue,i=n.proName;e.props.dispatch(Object(u.push)("".concat(t,"ProjectInvestmentHgDetail/").concat(a,"/").concat(o,"/").concat(i||null)))}}]}}]})))}}]),t}(p.Component);t.default=h}}]);