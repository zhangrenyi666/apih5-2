(window.webpackJsonp=window.webpackJsonp||[]).push([[278],{hXaI:function(e,t,a){"use strict";a.r(t);var n=a("+n0K"),r=(a("3fBH"),a("r0ML")),o=a.n(r),l=(a("C/2i"),a("ID3L"),a("+K9n"),a("0DJy"),a("tpuf"),a("fx2i"),a("40Q9"),a("VpwF"),a("9M8h"),a("w9C0"),a("HJbo"),a("daJc"),a("Sw1S"),a("07Zm"),a("2HLw")),u=a("WZsh"),p=a("R0KL"),i=(a("739E"),a("j0L1"),a("5fiz"),a("NExk"),a("ftLu")),c="\t\n\v\f\r \xa0\u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u202f\u205f\u3000\u2028\u2029\ufeff",s="["+c+"]",d=RegExp("^"+s+s+"*"),f=RegExp(s+s+"*$"),v=function(e){return function(t){var a=String(Object(n.x)(t));return 1&e&&(a=a.replace(d,"")),2&e&&(a=a.replace(f,"")),a}},m=(v(1),v(2),v(3)),h=n.v.parseInt,b=/^[+-]?0[Xx]/,g=8!==h(c+"08")||22!==h(c+"0x16")?function(e,t){var a=m(String(e));return h(a,t>>>0||(b.test(a)?16:10))}:h;Object(n.k)({global:!0,forced:parseInt!=g},{parseInt:g});var C=function(e){function t(){var e,a;Object(n.A)(this,t);for(var r=arguments.length,o=new Array(r),l=0;l<r;l++)o[l]=arguments[l];return(a=Object(n.B)(this,(e=Object(n.C)(t)).call.apply(e,[this].concat(o)))).state={value:a.props.value||[],disabled:a.props.disabled,addLabel:a.props.addLabel||"\u6dfb\u52a0"},a.addBtn=function(){var e=a.state.value,t=void 0===e?[]:e;t||(t=[]);var n={label:"",id:t.length};if(t.push(n),a.props.onChange){var r=t.map(function(e){return e.label});a.props.onChange(r)}a.setState({value:t})},a.getAfter=function(e){return a.props.getAfter?a.props.getAfter(e):String.fromCharCode(64+parseInt(e+1,10))},a}return Object(n.w)(t,r.Component),Object(n.D)(t,[{key:"render",value:function(){var e=this,t=this.state,a=t.value,n=void 0===a?[]:a,r=t.disabled,c=void 0!==r&&r,s=t.addLabel,d=this.props,f=d.qnnformData.style,v=d.className;return o.a.createElement("div",{className:"".concat(v," ").concat(f.qnnFormItemCom," qnnFormItemCom")},n&&n.map(function(t,a){var n=t.label,r=t.id,l=void 0===r?{index:a}:r;return o.a.createElement("div",{key:a,style:{marginBottom:"6px"}},o.a.createElement(i.a,{onChange:function(t){t=t.target.value;for(var a=e.state.value,n=0;n<a.length;n++)a[n].id===l&&(a[n].label=t);if(e.props.onChange){var r=a.map(function(e){return e.label});e.props.onChange(r)}e.setState({value:a})},placeholder:e.props.placeholder||"\u8bf7\u8f93\u5165...",value:n,addonAfter:o.a.createElement("span",{onClick:function(){for(var t=e.state.value,a=0;a<t.length;a++)t[a].id===l&&t.splice(a,1);if(e.props.onChange){var n=t.map(function(e){return e.label});e.props.onChange(n)}e.setState({value:t})},style:{color:"red",cursor:"pointer"}},o.a.createElement(u.a,null)),addonBefore:o.a.createElement("span",null,e.getAfter(a))}))}),c?null:o.a.createElement(l.a,{icon:o.a.createElement(p.a,null),ghost:!0,type:"primary",onClick:this.addBtn},s))}}],[{key:"getDerivedStateFromProps",value:function(e,t){var a=Object(n.l)({},t,{},e);return a.value&&(a.value=a.value.map(function(e,t){return{id:t,label:e}})),a}}]),t}();t.default=function(e){var t=e.inputProps;return o.a.createElement(C,Object(n.q)({},e,t))}}}]);