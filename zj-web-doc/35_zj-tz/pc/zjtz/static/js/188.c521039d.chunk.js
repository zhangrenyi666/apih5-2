(window.webpackJsonp=window.webpackJsonp||[]).push([[188],{"kBP+":function(a,e,t){"use strict";t.r(e);t("IdsT"),t("l9AF");var s=t("UpC8"),n=(t("am1t"),t("5WK1")),l=(t("5fiz"),t("LepS")),p=(t("07Zm"),t("2HLw")),o=(t("fx2i"),t("cZi0")),i=t("Id3l"),r=t("RNsw"),d=t("OR/T"),m=t("bn5B"),b=t("PnTt"),f=t("r0ML"),c=t.n(f),h=t("6eYn"),y=t("pBSS"),u=t("U32T"),x={antd:{rowKey:function(a){return a.id},size:"small"},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!1},g=function(a){function e(a){var t;return Object(i.a)(this,e),(t=Object(d.a)(this,Object(m.a)(e).call(this,a))).state={invProId:a.match.params.invProId||"",periodValue:a.match.params.periodValue||"",projectNameParams:a.match.params.projectNameParams||"",dataTop:null,dataBottom:null},t}return Object(b.a)(e,a),Object(r.a)(e,[{key:"componentDidMount",value:function(){var a=this,e=this.state,t=e.invProId,s=e.periodValue;(0,this.props.myFetch)("getZjTzInvXmhgqkMonthlyReportListBasicDataDetails",{proId:t,periodValue:s}).then(function(e){var t=e.data,s=e.success,n=e.message;s?(o.b.success(n),a.setState({dataTop:t.children,dataBottom:t.children2})):o.b.error(n)})}},{key:"render",value:function(){var a=this,e=this.state,t=e.invProId,o=e.periodValue,i=e.projectNameParams;return c.a.createElement("div",null,c.a.createElement(n.a,null,c.a.createElement(l.a,{span:4},c.a.createElement(p.a,{type:"dashed",onClick:function(){(0,a.props.dispatch)(Object(u.c)())}},"\u8fd4\u56de")),c.a.createElement(l.a,{span:20},c.a.createElement("div",{style:{width:"100%",marginLeft:"15%",fontSize:"18px",fontWeight:"bold"}},i," \u9879\u76ee\u56de\u8d2d\u60c5\u51b5\u8868"))),c.a.createElement(y.a,{style:{paddingRight:"10px",paddingLeft:"10px"},fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},fetchConfig:{apiName:"getZjTzInvXmhgqkMonthlyReportListBasicDataDetails",otherParams:{proId:t,periodValue:o}},drawerConfig:{width:"600px"},wrappedComponentRef:function(e){a.formHasTicket=e},formConfig:[{type:"string",label:"\u9879\u76ee\u7f16\u53f7",disabled:!0,field:"proNum",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",label:"\u9879\u76ee\u540d\u79f0",disabled:!0,field:"proName",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",label:"\u7ba1\u7406\u5355\u4f4d",disabled:!0,field:"comname",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",label:"\u80a1\u6743\u6bd4\u4f8b",disabled:!0,field:"szgq",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",label:"\u9879\u76ee\u7c7b\u522b",disabled:!0,field:"categoryName",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",label:"\u9879\u76ee\u7c7b\u578b",disabled:!0,field:"typeName",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"number",label:"\u9884\u8ba1\u56de\u8d2d\u603b\u989d",disabled:!0,field:"hgxyMoney",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",disabled:!0,label:"\u5408\u540c\u56de\u8d2d\u671f\uff08\u5e74\uff09",field:"hgq",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"date",disabled:!0,label:"\u56de\u8d2d\u8d77\u59cb\u65e5",field:"hgxyDate",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",disabled:!0,label:"\u7d2f\u8ba1\u56de\u8d2d\u671f\uff08\u6708\uff09",field:"ljhgq",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"string",disabled:!0,label:"\u56de\u8d2d\u6bd4\u4f8b\uff08%\uff09",field:"hgjeHgbl",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"date",disabled:!0,label:"\u586b\u62a5\u65f6\u95f4",field:"createDate",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}},{type:"textarea",disabled:!0,label:"\u5907\u6ce8",field:"remarks",autoSize:{minRows:1,maxRows:3},span:16,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}},{type:"string",disabled:!0,label:"\u62a5\u8868\u5e74\u6708",field:"periodValue",span:8,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}}]}),c.a.createElement(h.a,Object(s.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(e){a.table=e},data:this.state.dataTop},x,{formConfig:[{isInTable:!1,form:{label:"\u4e3b\u952eid",field:"id",hide:!0}},{table:{title:"",width:160,dataIndex:"workName",key:"workName"}},{table:{title:"\u672c\u671f",dataIndex:"bq",key:"bq"}},{table:{title:"\u672c\u5e74",dataIndex:"bn",key:"bn"}},{table:{title:"\u5f00\u7d2f",dataIndex:"kl",key:"kl"}}]})),c.a.createElement(h.a,Object(s.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(e){a.table=e},data:this.state.dataBottom},x,{formConfig:[{isInTable:!1,form:{label:"\u4e3b\u952eid",field:"id",hide:!0}},{table:{title:"",width:160,dataIndex:"workName",key:"workName"}},{table:{title:"\u507f\u8fd8\u94f6\u884c\u8d37\u6b3e",dataIndex:"chyhdk",key:"chyhdk"}},{table:{title:"\u507f\u8fd8\u8d37\u6b3e\u5229\u606f",dataIndex:"chdklx",key:"chdklx"}},{table:{title:"\u507f\u8fd8\u8d44\u672c\u91d1",dataIndex:"chzbj",key:"chzbj"}},{table:{title:"\u8d44\u91d1\u96c6\u4e2d",dataIndex:"zjjz",key:"zjjz"}},{table:{title:"\u5176\u4ed6",dataIndex:"qt",key:"qt"}}]})))}}]),e}(f.Component);e.default=g}}]);