(window.webpackJsonp=window.webpackJsonp||[]).push([[244],{"1ipt":function(t,e,n){"use strict";n.r(e);n("9U97"),n("zIPO");var o=n("UpC8"),a=n("Id3l"),i=n("RNsw"),d=n("OR/T"),r=n("bn5B"),c=n("PnTt"),l=n("r0ML"),p=n.n(l),s=n("6eYn"),I=n("ID3L"),u=n.n(I),h={drawerConfig:{width:"1100px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!1},m=function(t){function e(t){var n;return Object(a.a)(this,e),(n=Object(d.a)(this,Object(r.a)(e).call(this,t))).state={},n}return Object(c.a)(e,t),Object(i.a)(e,[{key:"componentDidMount",value:function(){}},{key:"render",value:function(){var t=this,e=this.props.myPublic,n=e.domain,a=e.appInfo.ureport,i=this.props.loginAndLogoutInfo.loginInfo.userInfo,d=(i.ext1,i.userId,i.curCompany);return p.a.createElement("div",null,p.a.createElement(s.a,Object(o.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(e){t.table=e},fetchConfig:{apiName:"getZjTzComplianceDetailListForReport",otherParams:{projectId:d.projectId}}},h,{antd:{rowKey:"id",size:"small",scroll:{y:.55*document.documentElement.clientHeight}},formConfig:[{isInTable:!1,form:{field:"id",type:"string",hide:!0}},{table:{title:"\u5404\u9879\u76ee\u5408\u6cd5\u5408\u89c4\u6027\u6587\u4ef6\u529e\u7406\u60c5\u51b5\u6c47\u603b",noHaveSearchInput:!0,children:[{dataIndex:"companyName",title:"\u7ba1\u7406\u5355\u4f4d",width:130},{title:"\u9879\u76ee\u540d\u79f0",dataIndex:"projectName",width:260,tooltip:23},{title:"\u9879\u76ee\u7c7b\u578b",dataIndex:"proTypeName",width:130},{title:"\u9879\u76ee\u516c\u53f8\u6210\u7acb\u65f6\u95f4",dataIndex:"company2",width:130},{title:"\u5b50\u9879\u76ee",dataIndex:"subprojectName",width:260},{title:"\u5de5\u7a0b\u53ef\u884c\u6027\u7814\u7a76\u62a5\u544a",dataIndex:"e1",width:130},{title:"\u5de5\u7a0b\u6279\u590d\u6216\u9879\u76ee\u6838\u51c6",dataIndex:"e2",width:130},{title:"\u521d\u6d89\u6279\u590d",dataIndex:"e3",width:130},{title:"\u65bd\u5de5\u56fe\u8bbe\u8ba1\u6279\u590d",dataIndex:"e4",width:130},{title:"\u7528\u5730\u6279\u590d",dataIndex:"e5",width:130},{title:"\u65bd\u5de5\u8bb8\u53ef\u8bc1",dataIndex:"e6",width:130},{title:"\u878d\u8d44\u534f\u8bae",dataIndex:"e7",width:130},{title:"\u73af\u8bc4\u6279\u590d",dataIndex:"e8",width:130}]},isInForm:!1}],actionBtns:[{name:"goback",type:"primary",label:"\u5bfc\u51fa",isValidate:!1,onClick:function(e){var o=t.props.loginAndLogoutInfo.loginInfo.userInfo,i=o.ext1,d=o.userId,r=o.curCompany,c="".concat(a,"excel?_u=file:zjTzComplianceDetailList.xml&url=").concat(n,"&userId=").concat(d,"&ext1=").concat(i,"&projectId=").concat(r.projectId,"&_n=\u5408\u6cd5\u5408\u89c4\u62a5\u8868_").concat(u()(new Date).format("YYYYMMDD"),"&token=").concat(t.props.loginAndLogoutInfo.loginInfo.token);window.open(c)}}]})))}}]),e}(l.Component);e.default=m}}]);