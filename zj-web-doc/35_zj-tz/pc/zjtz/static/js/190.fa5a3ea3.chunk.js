(window.webpackJsonp=window.webpackJsonp||[]).push([[190],{AJKU:function(a,e,s){"use strict";s.r(e);s("IdsT"),s("l9AF");var l=s("UpC8"),t=(s("am1t"),s("5WK1")),p=(s("5fiz"),s("LepS")),n=(s("07Zm"),s("2HLw")),o=(s("fx2i"),s("cZi0")),r=s("Id3l"),m=s("RNsw"),i=s("OR/T"),d=s("bn5B"),b=s("PnTt"),f=s("r0ML"),y=s.n(f),c=s("6eYn"),u=s("pBSS"),x=s("U32T"),C={antd:{rowKey:function(a){return a.id},size:"small",scroll:{y:.4*document.documentElement.clientHeight}},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!1},h=function(a){function e(a){var s;return Object(r.a)(this,e),(s=Object(i.a)(this,Object(d.a)(e).call(this,a))).state={invProId:a.match.params.invProId||"",periodValue:a.match.params.periodValue||"",projectNameParams:a.match.params.projectNameParams||"",dataBottom:null},s}return Object(b.a)(e,a),Object(m.a)(e,[{key:"componentDidMount",value:function(){var a=this,e=this.state,s=e.invProId,l=e.periodValue;(0,this.props.myFetch)("getZjTzInvXmyyqkMonthlyReportListBasicDataDetail",{proId:s,periodValue:l}).then(function(e){var s=e.data,l=e.success,t=e.message;l?(o.b.success(t),console.log(s.children),a.setState({dataBottom:s.children})):o.b.error(t)})}},{key:"render",value:function(){var a=this,e=this.state,s=e.invProId,o=e.periodValue,r=e.projectNameParams;return y.a.createElement("div",null,y.a.createElement(t.a,null,y.a.createElement(p.a,{span:4},y.a.createElement(n.a,{type:"dashed",onClick:function(){(0,a.props.dispatch)(Object(x.c)())}},"\u8fd4\u56de")),y.a.createElement(p.a,{span:20},y.a.createElement("div",{style:{width:"100%",marginLeft:"10%",fontSize:"18px",fontWeight:"bold"}},r," \u9879\u76ee\u8fd0\u8425\u60c5\u51b5\u8868"))),y.a.createElement(u.a,{style:{paddingRight:"10px",paddingLeft:"10px"},fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},fetchConfig:{apiName:"getZjTzInvXmyyqkMonthlyReportListBasicDataDetail",otherParams:{proId:s,periodValue:o}},drawerConfig:{width:"600px"},wrappedComponentRef:function(e){a.formHasTicket=e},formConfig:[{type:"string",label:"\u9879\u76ee\u7f16\u53f7",disabled:!0,field:"proNum",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"string",label:"\u9879\u76ee\u540d\u79f0",disabled:!0,field:"proName",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"string",label:"\u7ba1\u7406\u5355\u4f4d",disabled:!0,field:"comname",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"string",label:"\u9879\u76ee\u7c7b\u578b",disabled:!0,field:"typeName",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"string",label:"\u9879\u76ee\u7c7b\u522b",disabled:!0,field:"categoryName",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"string",label:"\u80a1\u6743\u6bd4\u4f8b",disabled:!0,field:"szgq",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"date",label:"\u8fd0\u8425\u8d77\u59cb\u65e5",disabled:!0,field:"yyksrq",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"string",disabled:!0,label:"\u7d2f\u8ba1\u7ecf\u8425\u671f\u6570(\u6708)",field:"ljjyqs",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"number",disabled:!0,label:"\u6295\u8bc4\u6708\u5747\u6536\u5165",field:"tpyjsr",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"string",disabled:!0,label:"\u7d2f\u8ba1\u8fd0\u8425\u65f6\u95f4(\u65e5)",field:"ljyxsj",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"number",disabled:!0,label:"\u671f\u672b\u8d44\u4ea7\u603b\u989d",field:"ljZcze",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"number",disabled:!0,label:"\u671f\u672b\u8d1f\u503a\u603b\u989d",field:"ljFzze",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"number",disabled:!0,label:"\u6295\u8bc4\u6708\u5747\u8f66\u8f86\u901a\u884c\u6570\u91cf\uff08\u8f86\uff09",field:"tpyjcltxsl",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:18}},wrapperCol:{xs:{span:24},sm:{span:6}}}},{type:"number",disabled:!0,label:"\u672c\u6708\u8f66\u8f86\u901a\u884c\u6570\u91cf\uff08\u8f86\uff09",field:"cltxqkBysl",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:15}},wrapperCol:{xs:{span:24},sm:{span:9}}}},{type:"number",disabled:!0,label:"\u672c\u5e74\u8f66\u8f86\u901a\u884c\u6570\u91cf\uff08\u8f86\uff09",field:"cltxqkBnsl",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:15}},wrapperCol:{xs:{span:24},sm:{span:9}}}},{type:"number",disabled:!0,label:"\u7d2f\u8ba1\u8f66\u8f86\u901a\u884c\u6570\u91cf\uff08\u8f86\uff09",field:"ljCltxsl",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:15}},wrapperCol:{xs:{span:24},sm:{span:9}}}},{type:"number",disabled:!0,label:"\u65e5\u5747\u8f66\u8f86\u901a\u884c\u6570\u91cf\uff08\u8f86\uff09",field:"rjcltxsl",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:15}},wrapperCol:{xs:{span:24},sm:{span:9}}}},{type:"number",disabled:!0,label:"\u5f53\u5e74\u65e5\u5747\u8fd0\u8425\u8f66\u8f86\u6570\u91cf\u9884\u6d4b\u503c\uff08\u8f86\uff09",field:"dnrjyyclslycz",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:15}},wrapperCol:{xs:{span:24},sm:{span:9}}}},{type:"number",disabled:!0,label:"\u5f53\u5e74\u65e5\u5747\u8fd0\u8425\u8f66\u8f86\u6570\u91cf\u5b9e\u9645\u503c\uff08\u8f86\uff09",field:"dnrjyyclslsjz",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:15}},wrapperCol:{xs:{span:24},sm:{span:9}}}},{type:"date",disabled:!0,label:"\u586b\u62a5\u65f6\u95f4",field:"createDate",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"date",disabled:!0,label:"\u586b\u62a5\u5e74\u6708",field:"periodValue",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:9}},wrapperCol:{xs:{span:24},sm:{span:15}}}},{type:"textarea",disabled:!0,label:"\u5907\u6ce8",field:"bz",autoSize:{minRows:1,maxRows:3},span:24,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}]}),y.a.createElement("div",{style:{marginTop:"10px"}},y.a.createElement(c.a,Object(l.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token}},C,{data:this.state.dataBottom,wrappedComponentRef:function(e){a.tableB=e},formConfig:[{isInTable:!1,form:{label:"\u4e3b\u952eid",field:"id",hide:!0}},{table:{title:"",width:160,dataIndex:"workName",key:"workName"}},{table:{title:"\u672c\u671f",dataIndex:"bq",key:"bq"}},{table:{title:"\u672c\u5b63",dataIndex:"bj",key:"bj"}},{table:{title:"\u672c\u5e74",dataIndex:"bn",key:"bn"}},{table:{title:"\u5f00\u7d2f",dataIndex:"kl",key:"kl"}}]}))))}}]),e}(f.Component);e.default=h}}]);