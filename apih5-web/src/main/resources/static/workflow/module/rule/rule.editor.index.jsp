<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.horizon.cn/taglib/path" prefix="path"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <title>规则编辑器</title>
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <%@include file="../../common/bootstrap.css.include.jsp"%>
    <link rel="stylesheet" href="<path:frontPath/>/jquery/jquery-ui/css/jquery-ui.css" />
    <%@include file="../../common/ace.css.include.jsp"%>
    <%@include file="../../common/base.css.include.jsp"%>
    <link type="text/css" rel="stylesheet" href="../../assets/module/rule/css/rule.editor.css" />
     <link type="text/css" rel="stylesheet" href="<path:frontPath/>/ztree/css/zTreeStyle/zTreeStyle.css" />
    <%@include file="../../common/oldbrowser.script.include.jsp"%>
    <%@include file="../../common/base.script.include.jsp"%>
</head>

<body class="no-skin">
<div class="container-fluid">
<div class="widget-box transparent">
<div class="widget-header">
    <h3 class="widget-title lighter">规则编辑器</h3>
    <div class="widget-toolbar no-border">
        <div class="flow-buttons">
            <button class="btn btn-sm btn-success btn-rule-script">
                <i class="ace-icon fa fa-pencil-square-o bigger-120"></i> <span
                    class="hidden-xs">规则脚本 </span>
            </button>
        </div>
    </div>
</div>
<div class="widget-body">
<div class="widget-main no-padding">
    <div class="row ">
        <div class="col-xs-12 if-container">
            <div class="widget-box if-box">
                <div class="widget-header widget-header-small rule-header">
                    <h4 class="widget-title">规则</h4>
                    <div class="widget-toolbar">
                        <a href="#nogo" data-action="close"> <i
                                class="ace-icon fa fa-times"></i>
                        </a>
                    </div>
                    <div class="widget-toolbar hidden">
                        <button class="btn btn-xs btn-info btn-white btn-round pull-right addRule" style="margin-top: 2px;">
                            <i class="glyphicon glyphicon-plus green"></i> 添加规则&nbsp;
                        </button>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <div class="row">
                            <div class="when-box col-xs-12">
                                <h4 class="smaller lighter blue no-margin">
                                    <i class="ace-icon fa fa-hand-o-right smaller-90"></i>&nbsp;如果
                                    <small class="blue">(WHEN)</small>
                                </h4>
                                <div class="when-info col-xs-12">
                                    <div class="col-xs-12 default-bds">
                                        <div class="add-ljpd-div tooltip-success" data-rel="tooltip" data-placement="right"  title="添加逻辑条件" >
                                            <a href="#nogo" class="add-ljpd">
                                                <i class="ace-icon fa fa-plus green"></i>
                                            </a>
                                        </div>
                                        <div class="tooltip-info bds-left" data-rel="tooltip">
                                            <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
                                            <ul class="dropdown-menu dropdown-caret">
                                                <li><a href="#nogo" class="kh" dataType="addZkh">添加左括号</a></li>
                                                <li class="divider no-margin"></li>
                                                <li class="dropdown-hover">
                                                    <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                        <span class="pull-left">添加规则对象</span>
                                                        <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                                    </a>
                                                    <ul class="dropdown-menu menu-ruleObject-type"></ul>
                                                </li>
                                                <li class="divider no-margin hidden"></li>
                                                <li class="hidden">
                                                    <a href="#nogo" class="update-return-bds">修改</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="ysf-box">
                                            <a href="#nogo" class="dropdown-toggle green tooltip-info"data-toggle="dropdown" code="equal" codeType="equal"
                                               title="（字符）等于" data-rel="tooltip">equal</a>
                                            <ul class="dropdown-menu dropdown-caret">
                                                <li class="hidden"><a href="#nogo" class="ysf" code="in" codeCN="包含" codeType="in">in</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code="unin" codeCN="不包含" codeType="unin">unin</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code=">" codeCN="大于" codeType="dy">&gt;</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code=">=" codeCN="大于等于" codeType="dydy">>=</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code="<" codeType=" xy" codeCN="小于">&lt;</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code="<=" codeType="xydy" codeCN="小于等于">&lt;=</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code="==" codeType="eq" codeCN="等于">==</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code="!=" codeType="uneq" codeCN="不等于">!=</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code="equal" codeType="equal" codeCN="（字符）等于">equal</a></li>
                                                <li class="hidden"><a href="#nogo" class="ysf" code="unequal" codeType="unequal" codeCN="（字符）不等于">unequal</a></li>
                                            </ul>
                                        </div>
                                        <div class="tooltip-info bds-right" data-rel="tooltip">
                                            <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
                                            <ul class="dropdown-menu dropdown-caret">
                                                <li><a href="#nogo" class="kh" dataType="addYkh">添加右括号</a></li>
                                                <li class="divider no-margin"></li>
                                                <li class="dropdown-hover">
                                                    <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                        <span class="pull-left">添加规则对象</span>
                                                        <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                                    </a>
                                                    <ul class="dropdown-menu menu-ruleObject-type"></ul>
                                                </li>
                                                <li class="divider no-margin hidden"></li>
                                                <li class="hidden"><a href="#nogo" class="update-return-bds">修改</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="then-box col-xs-12">
                                <h4 class="smaller lighter blue no-margin">
                                    <i class="ace-icon fa fa-hand-o-right smaller-90"></i>&nbsp;那么
                                    <small class="blue">(THEN)</small>
                                </h4>
                                <div class="space-4"></div>
                                <div class="then-info col-xs-12">
                                    <label class="col-xs-2 align-right no-margin">返回值等于:</label>
                                    <div class="col-xs-10 no-padding">
                                        <div class="div-return no-margin">
                                            <div class="tooltip-info bds-return" data-rel="tooltip">
                                                <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
                                                <ul class="dropdown-menu dropdown-caret">
                                                    <li><a href="#nogo" class="kh" dataType="addZkh">添加左括号</a></li>
                                                    <li><a href="#nogo" class="kh" dataType="addYkh">添加右括号</a></li>
                                                    <li class="divider no-margin"></li>
                                                    <li class="dropdown-hover">
                                                        <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                            <span class="pull-left">添加运算符</span>
                                                            <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                                        </a>
                                                        <ul class="dropdown-menu ">
                                                            <li><a href="#nogo" class="ysfUN"type="orgIntersection">组织机构交集</a></li>
                                                            <li><a href="#nogo" class="ysfUN" type="orgUnion">组织机构并集</a></li>
                                                        </ul>
                                                    </li>
                                                    <li class="dropdown-hover">
                                                        <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                            <span class="pull-left">添加规则对象</span>
                                                            <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                                        </a>
                                                        <ul class="dropdown-menu menu-ruleObject-return"></ul>
                                                    </li>
                                                    <li class="divider no-margin hidden"></li>
                                                    <li class="hidden">
                                                        <a href="#nogo" class="update-return-bds">修改</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="space-4 col-xs-12"></div>
                                    <label class="col-xs-2 no-margin align-right">消息提示:</label>
                                    <div class="col-xs-10 no-padding">
                                        <textarea class="msg"></textarea>
                                    </div>
                                    <a href="#nogo" class="btn btn-white btn-info btn-bold tooltip-success app-addRule addRule" data-placement="right" data-rel="tooltip" title="添加规则">
                                        <i class="ace-icon fa fa-plus bigger-160 green no-margin"></i>
                                    </a>
                                </div>
                            </div><!--then-box  -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="space-2"></div>
    <div class="row">
        <div class="col-xs-12 else-container">
            <div class="widget-box else-box">
                <div class="widget-header widget-header-small rule-header">
                    <h4 class="widget-title">否则</h4>
                    <small class="blue">(ELSE)</small>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <div class="row">
                            <div class="else-info col-xs-12">
                                <label class="col-xs-2 align-right no-margin">返回值等于:</label>
                                <div class="col-xs-10 no-padding">
                                    <div class="div-return no-margin">
                                        <div class="tooltip-info bds-return" data-rel="tooltip">
                                            <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
                                            <ul class="dropdown-menu dropdown-caret">
                                                <li><a href="#nogo" class="kh" dataType="addZkh">添加左括号</a></li>
                                                <li><a href="#nogo" class="kh" dataType="addYkh">添加右括号</a></li>
                                                <li class="divider no-margin"></li>
                                                <li class="dropdown-hover">
                                                    <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                        <span class="pull-left">添加运算符</span>
                                                        <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                                    </a>
                                                    <ul class="dropdown-menu">
                                                        <li><a href="#nogo" class="ysfUN" type="orgIntersection">组织机构交集</a></li>
                                                        <li><a href="#nogo" class="ysfUN" type="orgUnion">组织机构并集</a></li>
                                                    </ul>
                                                </li>
                                                <li class="dropdown-hover">
                                                    <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                        <span class="pull-left">添加规则对象</span>
                                                        <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                                    </a>
                                                    <ul class="dropdown-menu menu-ruleObject-return"></ul>
                                                </li>
                                                <li class="divider no-margin hidden"></li>
                                                <li class="hidden">
                                                    <a href="#nogo" class="update-return-bds">修改</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="space-4 col-xs-12"></div>
                                <label class="col-xs-2 no-margin align-right">消息提示:</label>
                                <div class="col-xs-10 no-padding">
                                    <textarea class="msg"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- else-box -->
        </div><!-- end else-container -->
    </div>
</div>
</div>
</div>
</div>
<a href="#nogo" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>
<div class="hidden hidden-box">
<div class="widget-box if-box">
    <div class="widget-header widget-header-small rule-header">
        <h4 class="widget-title">规则</h4>
        <div class="widget-toolbar">
            <a href="#nogo" data-action="close">
                <i class="ace-icon fa fa-times"></i>
            </a>
        </div>
        <div class="widget-toolbar hidden">
            <button class="btn btn-xs btn-info btn-white btn-round pull-right tool-addRule addRule" style="margin-top: 2px;">
                <i class="glyphicon glyphicon-plus green"></i> 添加规则&nbsp;
            </button>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="row">
                <div class="when-box col-xs-12">
                    <h4 class="smaller lighter blue no-margin">
                        <i class="ace-icon fa fa-hand-o-right smaller-90"></i>&nbsp;如果
                        <small class="blue">(WHEN)</small>
                    </h4>
                    <div class="when-info col-xs-12">
                        <div class="col-xs-12 default-bds">
                            <div class="add-ljpd-div tooltip-success" data-rel="tooltip" data-placement="right"  title="添加逻辑条件" >
                                <a href="#nogo" class="add-ljpd">
                                    <i class="ace-icon fa fa-plus green"></i>
                                </a>
                            </div>
                            <div class="tooltip-info bds-left" data-rel="tooltip">
                                <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
                                <ul class="dropdown-menu dropdown-caret">
                                    <li><a href="#nogo" class="kh" dataType="addZkh">添加左括号</a></li>
                                    <li class="divider no-margin"></li>
                                    <li class="dropdown-hover">
                                        <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                            <span class="pull-left">添加规则对象</span>
                                            <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                        </a>
                                        <ul class="dropdown-menu menu-ruleObject-type"></ul>
                                    </li>
                                    <li class="divider no-margin hidden"></li>
                                    <li class="hidden">
                                        <a href="#nogo" class="update-return-bds">修改</a>
                                    </li>
                                </ul>
                            </div>
                            <div class="ysf-box">
                                <a href="#nogo" class="dropdown-toggle green tooltip-info"data-toggle="dropdown" code="equal" codeType="equal"
                                   title="（字符）等于" data-rel="tooltip">equal</a>
                                <ul class="dropdown-menu dropdown-caret">
                                    <li class="hidden"><a href="#nogo" class="ysf" code="in" codeCN="包含" codeType="in">in</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code="unin" codeCN="不包含" codeType="unin">unin</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code=">" codeCN="大于" codeType="dy">&gt;</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code=">=" codeCN="大于等于" codeType="dydy">>=</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code="<" codeType=" xy" codeCN="小于">&lt;</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code="<=" codeType="xydy" codeCN="小于等于">&lt;=</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code="==" codeType="eq" codeCN="等于">==</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code="!=" codeType="uneq" codeCN="不等于">!=</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code="equal" codeType="equal" codeCN="（字符）等于">equal</a></li>
                                    <li class="hidden"><a href="#nogo" class="ysf" code="unequal" codeType="unequal" codeCN="（字符）不等于">unequal</a></li>
                                </ul>
                            </div>
                            <div class="tooltip-info bds-right" data-rel="tooltip">
                                <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
                                <ul class="dropdown-menu dropdown-caret">
                                    <li><a href="#nogo" class="kh" dataType="addYkh">添加右括号</a></li>
                                    <li class="divider no-margin"></li>
                                    <li class="dropdown-hover">
                                        <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                            <span class="pull-left">添加规则对象</span>
                                            <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                        </a>
                                        <ul class="dropdown-menu menu-ruleObject-type"></ul>
                                    </li>
                                    <li class="divider no-margin hidden"></li>
                                    <li class="hidden"><a href="#nogo" class="update-return-bds">修改</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="then-box col-xs-12">
                    <h4 class="smaller lighter blue no-margin">
                        <i class="ace-icon fa fa-hand-o-right smaller-90"></i>&nbsp;那么
                        <small class="blue">(THEN)</small>
                    </h4>
                    <div class="space-4"></div>
                    <div class="then-info col-xs-12">
                        <label class="col-xs-2 align-right no-margin">返回值等于:</label>
                        <div class="col-xs-10 no-padding">
                            <div class="div-return no-margin">
                                <div class="tooltip-info bds-return" data-rel="tooltip">
                                    <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
                                    <ul class="dropdown-menu dropdown-caret">
                                        <li><a href="#nogo" class="kh" dataType="addZkh">添加左括号</a></li>
                                        <li><a href="#nogo" class="kh" dataType="addYkh">添加右括号</a></li>
                                        <li class="divider no-margin"></li>
                                        <li class="dropdown-hover">
                                            <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                <span class="pull-left">添加运算符</span>
                                                <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                            </a>
                                            <ul class="dropdown-menu ">
                                                <li><a href="#nogo" class="ysfUN"type="orgIntersection">组织机构交集</a></li>
                                                <li><a href="#nogo" class="ysfUN" type="orgUnion">组织机构并集</a></li>
                                            </ul>
                                        </li>
                                        <li class="dropdown-hover">
                                            <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                                                <span class="pull-left">添加规则对象</span>
                                                <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                                            </a>
                                            <ul class="dropdown-menu menu-ruleObject-return"></ul>
                                        </li>
                                        <li class="divider no-margin hidden"></li>
                                        <li class="hidden">
                                            <a href="#nogo" class="update-return-bds">修改</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="space-4 col-xs-12"></div>
                        <label class="col-xs-2 no-margin align-right">消息提示:</label>
                        <div class="col-xs-10 no-padding">
                            <textarea class="msg"></textarea>
                        </div>
                        <a href="#nogo" class="btn btn-white btn-info btn-bold tooltip-success app-addRule addRule" data-placement="right" data-rel="tooltip" title="添加规则">
                            <i class="ace-icon fa fa-plus bigger-160 green no-margin"></i>
                        </a>
                    </div>
                </div><!--then-box  -->
            </div>
        </div>
    </div>
</div><!-- if-box end-->
<div class="col-xs-12 ljpd-box">
    <div class="add-ljpd-div tooltip-success" data-rel="tooltip" data-placement="right"  title="添加逻辑条件" >
        <a href="#nogo" class="add-ljpd">
            <i class="ace-icon fa fa-plus green"></i>
        </a>
    </div>
    <div class="ljf-box">
        <a href="#nogo" class="dropdown-toggle orange" code="&&" codetype="lj" data-toggle="dropdown">并且</a>
        <ul class="dropdown-menu dropdown-caret">
            <li><a href="#nogo" class="ljf" code="||" codetype="lj" type="or">或者</a></li>
            <li class="hidden"><a href="#nogo" class="ljf" code="&&" codetype="lj" type="and">并且</a></li>
        </ul>
    </div>
    <div class="tooltip-info bds-left" data-rel="tooltip">
        <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
        <ul class="dropdown-menu dropdown-caret">
            <li><a href="#nogo" class="kh" dataType="addZkh">添加左括号</a></li>
            <li class="divider no-margin"></li>
            <li class="dropdown-hover">
                <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                    <span class="pull-left">添加规则对象</span>
                    <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                </a>
                <ul class="dropdown-menu menu-ruleObject-type"></ul>
            </li>
            <li class="divider no-margin hidden"></li>
            <li class="hidden"><a href="#nogo" class="update-return-bds">修改</a></li>
        </ul>
    </div>
    <div class="ysf-box">
        <a href="#nogo" class="dropdown-toggle green tooltip-info" data-toggle="dropdown" code="equal"
           codeType="equal" title="（字符）等于" data-rel="tooltip">equal</a>
        <ul class="dropdown-menu dropdown-caret">
            <li class="hidden"><a href="#nogo" class="ysf" code="in" codeCN="包含" codeType="in">in</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code="unin" codeCN="不包含" codeType="unin">unin</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code=">" codeCN="大于" codeType="dy">&gt;</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code=">=" codeCN="大于等于" codeType="dydy">>=</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code="<" codeType=" xy" codeCN="小于">&lt;</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code="<=" codeType="xydy" codeCN="小于等于">&lt;=</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code="==" codeType="eq" codeCN="等于">==</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code="!=" codeType="uneq" codeCN="不等于">!=</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code="equal" codeType="equal" codeCN="（字符）等于">equal</a></li>
            <li class="hidden"><a href="#nogo" class="ysf" code="unequal" codeType="unequal" codeCN="（字符）不等于">unequal</a></li>
        </ul>
    </div>
    <div class="tooltip-info bds-right" data-rel="tooltip">
        <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
        <ul class="dropdown-menu dropdown-caret">
            <li><a href="#nogo" class="kh" dataType="addYkh">添加右括号</a></li>
            <li class="divider no-margin"></li>
            <li class="dropdown-hover">
                <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                    <span class="pull-left">添加规则对象</span>
                    <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
                </a>
                <ul class="dropdown-menu menu-ruleObject-type"></ul></li>
            <li class="divider no-margin hidden"></li>
            <li class="hidden"><a href="#nogo" class="update-return-bds">修改</a></li>
        </ul>
    </div>
    <div class="del-ljpd-div tooltip-success" data-rel="tooltip" data-placement="left"  title="删除逻辑条件" >
        <a href="#nogo" class="del-ljpd ">
            <i class="ace-icon fa fa-minus red"></i>
        </a>
    </div>
</div>
<!-- ljpd-box -->
<div class="rule-script">
    <pre id="ruleCodeShow"></pre>
    <textarea id="ruleCN" class="hidden"></textarea>
    <textarea class="hidden" id="ruleCode"></textarea>
</div>
<div class="kh-box">
    <a href="#nogo" class="dropdown-toggle orange2" codetype="kh" data-toggle="dropdown"></a>
    <ul class="dropdown-menu dropdown-caret">
        <li><a href="#nogo" class="delKh">删除</a></li>
    </ul>
</div>
<div class="default-menu-ruleObject">
    <li class="dropdown-hover">
        <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
            <span class="pull-left"></span>
            <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
        </a>
        <ul class="dropdown-menu menu-ruleObject-name"></ul>
    </li>
</div>
<div class="UN-box"><a href="#nogo" class="dropdown-toggle green"
                       codetype="ysf" data-toggle="dropdown"></a>
    <ul class="dropdown-menu dropdown-caret">
        <li><a href="#nogo" class="UN" code="N" codetype="ysf">交集</a></li>
        <li><a href="#nogo" class="UN" code="U" codetype="ysf">并集</a></li>
    </ul>
</div>
<div class="tooltip-info bds-return" data-rel="tooltip">
    <a href="#nogo" class="dropdown-toggle" code="" data-toggle="dropdown">请选择</a>
    <ul class="dropdown-menu dropdown-caret">
        <li><a href="#nogo" class="kh" dataType="addZkh">添加左括号</a></li>
        <li><a href="#nogo" class="kh" dataType="addYkh">添加右括号</a></li>
        <li class="divider no-margin"></li>
        <li class="dropdown-hover">
            <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                <span class="pull-left">添加运算符</span>
                <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
            </a>
            <ul class="dropdown-menu">
                <li><a href="#nogo" class="ysfUN" type="orgIntersection">组织机构交集</a></li>
                <li><a href="#nogo" class="ysfUN" type="orgUnion">组织机构并集</a></li>
            </ul>
        </li>
        <li class="dropdown-hover">
            <a href="#nogo" class="dropdown-toggle clearfix" data-toggle="dropdown">
                <span class="pull-left">添加规则对象</span>
                <i class="ace-icon fa fa-caret-right pull-right i-caret"></i>
            </a>
            <ul class="dropdown-menu menu-ruleObject-return"></ul>
        </li>
        <li class="divider no-margin"></li>
        <li class="hidden"><a href="#nogo" class="update-return-bds">修改</a></li>
        <li><a href="#nogo" class="del-return-bds">删除</a></li>
    </ul>
</div>
<div id="dialog"></div>
<ul class="dropdown-menu menu-ruleObject-type"></ul>
</div>
<!-- hidden -->
<div class="rule-container hidden"></div>
<!-- basic scripts -->
<script type="text/javascript">
    var RETURNTYPE = "${param.returnType}";
    var dataid = "${param.dataid}";
    var flowid = "${param.flowid}";
    var winObj = window.dialogArguments;
    var ckValidityInfo, getCodeResult, getCodeCNResult, getCodeId, getCodeHtml, getCodeHtmlSWF;
</script>
<script data-main="<path:assetsPath/>/module/rule/js/rule.editor" src="<path:frontPath/>/require/require.js"></script>
</body>
</html>
