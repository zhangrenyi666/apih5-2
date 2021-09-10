<!DOCTYPE html>
<html lang='${LANG}'>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <title>${workFlow.flowName}</title>
    <link rel="stylesheet" href="${frontPath}/bootstrap/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${frontPath}/bootstrap/bootstrap/css/font-awesome.css" />
    <link rel="stylesheet" href="${frontPath}/jquery/jquery-ui/css/jquery-ui.css"/>
    <link rel="stylesheet" href="${frontPath}/gritter/css/jquery.gritter.css"/>
    <link rel="stylesheet" href="${frontPath}/ztree/css/zTreeStyle/zTreeStyle.css"/>
    <!-- ace styles & text fonts ace基础样式和字体 -->
    <link rel="stylesheet" href="${frontPath}/bootstrap/ace/css/ace-fonts.css" />
    <link rel="stylesheet" href="${frontPath}/bootstrap/ace/css/ace.css" class="ace-main-stylesheet main-stylesheet" id="main-ace-style" />
    <!-- 低版本IE样式 -->
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="${frontPath}/bootstrap/ace/css/ace-part2.css" class="ace-main-stylesheet" />
    <link rel="stylesheet" href="${frontPath}/bootstrap/ace/css/ace-ie.css" />
    <![endif]-->
    <link rel="stylesheet" href="${assetsPath}/common/css/horizon.base.css" />
    <!-- 网站LOGO -->
    <link rel="icon" type="image/png" href="${assetsPath}/common/img/favicon.png" />
    <script src="${assetsPath}/common/js/horizon.base.js"></script>
    <!--
   HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries
   用于处理IE8不兼容HTML5和CSS3新特性
   -->
    <!--[if lte IE 8]>
    <script src="${frontPath}/old-browser/html5shiv.js"></script>
    <script src="${frontPath}/old-browser/respond.js"></script>
    <![endif]-->

</head>
<body class="workflow-body overflow-hidden">
<#if  workFlow.openType=="true">
    <div class="main-container">
        <!-- 操作开始 -->
        <div class="flow-buttons">
            <ul class="nav nav-pills no-margin">
                    <#list workFlow.flowButtons as button>
                        <li class="hidden">
                            <a href="javascript:void(0);" data-operate="${button.buttonId}"
                               data-clazz="${button.buttonClass}"
                               data-text="${button.buttonName}">
                                <i class="ace-icon ${button.icon}"></i> ${button.buttonName}
                            </a>
                        </li>
                    </#list>
                <li>
                    <a href="javascript:void(0);" data-operate="close">
                        <i class="ace-icon fa fa-times"></i>
                        ${i18n["close" ]}
                    </a>
                </li>
                <li class="more hidden">
                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdown-toggle">
                        <i class="ace-icon fa fa-list"></i>
                        ${i18n["more" ]}
                    </a>
                    <ul class="dropdown-menu dropdown-light-blue dropdown-caret dropdown-menu-right">
                            <#list workFlow.flowButtons as button>
                                 <li class="hidden">
                                     <a href="javascript:void(0);" data-operate="${button.buttonId}"
                                        data-clazz="${button.buttonClass}">
                                         <i class="ace-icon ${button.icon}"></i>${button.buttonName}
                                     </a>
                                 </li>
                            </#list>
                        <li class="hidden">
                            <a href="javascript:void(0);" data-operate="close">
                                <i class="ace-icon fa fa-times"></i>
                                ${i18n["close" ]}
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- 操作结束 -->
        <div class="flow-body">
            <#if workFlow.openType == "false">
                <div class="alert alert-danger alert-inflow bigger-110 center">${workFlow.flowMsg}</div>
            </#if>
            <div class="tabbable">
                <ul class="nav nav-tabs" id="myTab">
                <#list workFlow.flowForms as form>
                    <li <#if form.isDef == "true">class="active"</#if>>
                        <a data-toggle="tab" href="#${form.formId}">${form.formName}</a>
                    </li>
                </#list>
                </ul>
                <form class="base-form form-horizontal" role="form" method="post">
                    <#if workFlow.flowForms??>
                    <div class="tab-content no-padding-left no-padding-right no-padding-top">
                    <#list  workFlow.flowForms as form>
                        <#if ( form.formId=="FlowTrack")>
                            <!-- 流程跟踪图 -->
                            <div id="${form.formId}"
                                 class="empty tab-pane fade <#if form.isDef=="true">active in</#if>">
                                <div class="row no-margin">
                                    <div class="col-xs-12">
                                        <div class="widget-box transparent">
                                            <div class="widget-header">
                                                <h5 class="widget-title lighter">
                                                    <i class="ace-icon fa fa-sitemap"></i>${i18n["flowChart" ]}
                                                </h5>
                                                <div class="widget-toolbar">
                                                    <a href="javascript:void(0);" data-action="collapse">
                                                        <i class="ace-icon fa fa-chevron-up"></i>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="widget-body">
                                                <div class="widget-main no-padding-left no-padding-right">
                                                    <div class="flowchart-container"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="widget-box transparent">
                                            <div class="widget-header">
                                                <h5 class="widget-title lighter">
                                                    <i class="ace-icon fa fa-clock-o"></i>
                                                    ${i18n["history" ]}
                                                </h5>
                                                <div class="widget-toolbar">
                                                    <div class="widget-menu">
                                                        <a href="javascript:void(0);" data-action="settings"
                                                           data-toggle="dropdown">
                                                            <i class="ace-icon fa fa-bars"></i>
                                                        </a>
                                                        <ul class="dropdown-menu dropdown-menu-right dropdown-light-blue dropdown-caret dropdown-closer">
                                                            <li class="active">
                                                                <a href="javascript:void(0)" data-action="timeline">
                                                                    <i class="ace-icon fa fa-clock-o"></i>
                                                                    ${i18n["timeLine" ]}
                                                                </a>
                                                            </li>
                                                            <li>
                                                                <a href="javascript:void(0)" data-action="table">
                                                                    <i class="ace-icon fa fa-list-alt"></i>
                                                                    ${i18n["table" ]}
                                                                </a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <a href="javascript:void(0);" data-action="collapse"> <i
                                                            class="ace-icon fa fa-chevron-up"></i> </a>
                                                </div>
                                            </div>
                                            <div class="widget-body">
                                                <div class="widget-main no-padding-left no-padding-right note-container"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <#elseif (form.formType=="JSP")>
                            <!-- 外部表单 -->
                            <div id="${form.formId}" class="tab-pane fade <#if form.isDef=="true">active in</#if>">
                                <div class="row no-margin form-body">
                                    <div class="col-xs-12">
                                        <@includeX template="${form.formBody}"/>
                                    </div>
                                </div>
                            </div>
                        <#else>
                            <div id="${form.formId}" class="tab-pane fade <#if form.isDef=="true">active in</#if>">
                                <#assign customForm=forms[form.formId]>
                                <#if customForm.formButtons??>
                                    <div class="row no-margin">
                                        <div class="col-xs-12 form-buttons">
                                            <ul class="nav nav-pills no-margin">
                                                <#list customForm.formButtons as formButton>
                                                    <li>
                                                        <a href="javascript:void(0)"
                                                           onclick="${formButton.func}">
                                                          <#if formButton.buttonIcon??>
                                                              <i class="ace-icon ${formButton.buttonIcon} hidden-xs"></i>
                                                          </#if>
                                                          <#if (formButton.buttonIcon??) ||  formButton.buttonIcon == "">
                                                              <i class="ace-icon fa fa-coffee hidden-xs"></i>
                                                          </#if>
                                                            ${formButton.buttonName}
                                                        </a>
                                                    </li>
                                                </#list>
                                            </ul>
                                        </div>
                                    </div>
                                </#if>
                                <div class="row no-margin form-body">
                                    <div class="col-xs-12">${bodys[form.formId]}</div>
                                    <input type="hidden" name="${form.formId}_WORKFLOW_DATA" value='${form.formDataId}'/>
                                </div>
                            </div>
                        </#if>
                    </#list>
                    </div>
                    <#else>
                     <div class="alert alert-danger alert-inflow bigger-110 center">
                         ${i18n["noPermission" ]}
                     </div>
                    </#if>
                    <div id="right-menu" class="modal aside" data-body-scroll="false" data-offset="true"
                         data-placement="right" data-fixed="${fixedModal}" data-backdrop="invisible" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <h5 class="header lighter blue no-margin">
                                        ${i18n["handleStatus" ]}
                                        <label class="pull-right">
                                            <a id="fixed-modal" class="pointer">
                                                <i class="ace-icon fa fa-unlock bigger-160" data-fixed-icon="fa-lock" data-unfixed-icon="fa-unlock"></i>
                                            </a>
                                        </label>
                                    </h5>
                                    <div class="space-6"></div>
                                    <div class="well well-sm">
                                        <div class="row no-margin">
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <i class="ace-icon fa fa-circle-o green bigger-110"></i>
                                                ${i18n["currentNode" ]}:
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <span class="orange text-break">${workFlow.flowNode.nodeName}</span>
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <i class="ace-icon fa fa-edit green smaller-90"></i>
                                                ${i18n["transactor" ]}:
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <span class="orange text-break">${workFlow.flowUser.transactorName}</span>
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <i class="ace-icon fa fa-flag green smaller-90"></i>
                                                ${i18n["flowStatus" ]}:
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <span class="orange">${workFlow.flowStatusName}</span>
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <i class="ace-icon fa fa-user green bigger-110"></i>
                                                ${i18n["currentUser" ]}:
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <span class="orange text-break">${workFlow.flowUser.userName}</span>
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <i class="ace-icon fa fa-gavel green smaller-90"></i>
                                                ${i18n["userIdentity" ]}:
                                            </label>
                                            <label class="col-xs-12 col-sm-6 no-padding control-label">
                                                <span class="orange text-break">${workFlow.flowUser.authorName}</span>
                                            </label>
                                        </div>
                                    </div>
                                    <h5 class="header lighter blue no-margin">
                                        <i class="ace-icon fa fa-pencil-square-o"></i>
                                        ${i18n["comment" ]}
                                    </h5>
                                    <div class="space-6"></div>
                                    <div>
                                        <textarea placeholder= ${i18n["comment" ]} name="comment" class="form-control" rows="8"></textarea>
                                    </div>
                                </div>
                            </div>
                            <button class="aside-trigger btn btn-info btn-app btn-xs ace-settings-btn"
                                    data-target="#right-menu" data-toggle="modal" type="button">
                                <i data-icon1="fa-fire" data-icon2="fa-minus"
                                   class="ace-icon fa fa-fire bigger-110 icon-only"></i>
                            </button>
                        </div>
                    </div>
                    <div class="hide">
                        <input type="hidden" name="flowId" value='${workFlow.flowId}'/>
                        <input type="hidden" name="workId" value='${workFlow.workId}'/>
                        <input type="hidden" name="subjection" value='${workFlow.subjectionId}'/>
                        <input type="hidden" name="authorKey" value='${workFlow.authorKey}'/>
                        <input type="hidden" name="nodeId" value='${workFlow.flowNode.nodeId}'/>
                        <input type="hidden" name="nodeName" value='${workFlow.flowNode.nodeName}'/>
                        <input type="hidden" name="trackId" value='${workFlow.flowNode.trackId}'/>
                        <input type="hidden" name="userId" value='${workFlow.flowUser.userId}'/>
                        <input type="hidden" name="userName" value='${workFlow.flowUser.userName}'/>
                        <input type="hidden" name="deptId" value='${workFlow.flowUser.deptId}'/>
                        <input type="hidden" name="deptName" value='${workFlow.flowUser.deptName}'/>
                        <input type="hidden" name="unValidate" value='${unValidate}'/>
                    </div>
                </form>
            </div>
        </div>
        <a href="javascript:void(0);" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
    </div>
    <div class="templates hidden">
        <div class="horizon-dialog-modal modal fade" tabindex="-1" role="dialog">
            <div class="modal-backdrop in"></div>
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><i class="ace-icon fa fa-times"></i></button>
                        <h5 class="modal-title">
                            <i class="ace-icon fa fa-sitemap blue"></i>
                            <span class="modal-title-text"></span>
                        </h5>
                    </div>
                    <div class="modal-body"></div>
                    <div class="modal-footer">
                        <div class="msg-type-container col-xs-12 col-sm-6 align-left no-padding"></div>
                        <div class="col-xs-12 col-sm-6 no-padding">
                            <button type="button" class="horizon-btn btn btn-primary" data-action="submit">
                                <i class="ace-icon fa fa-check"></i>${i18n["ok" ]}
                            </button>
                            <button type="button" class="horizon-btn btn btn-default" data-dismiss="modal">
                                <i class="ace-icon fa fa-times"></i>${i18n["close" ]}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-group accordion-style1 accordion-style2" data-required="true"></div>
        <div data-setup="true" class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a class="accordion-toggle" data-toggle="collapse">
                        <i class="panel-title-icon bigger-110 ace-icon fa" data-icon-hide="fa-angle-down" data-icon-show="fa-angle-right"></i>
                        <span class="panel-title-text"></span>
                        <i class="panel-list-icon bigger-110 ace-icon fa fa-list blue pull-right hidden"></i>
                    </a>
                </h4>
            </div>
            <div class="panel-collapse collapse">
                <div class="panel-body no-padding-right empty">
                    <i class="ace-icon fa fa-spinner fa-spin orange bigger-125"></i>
                </div>
            </div>
        </div>
        <div class="tag-box col-xs-6 col-sm-4 col-md-3 col-lg-2 align-center">
            <span class="tag">
                <span class="tag-text"></span>
                <button type="button" class="close">
                    <i class="ace-icon fa fa-times"></i>
                </button>
            </span>
        </div>
        <div data-setup="true" data-axis="${freeSubmit}" class="free-node-box padding-10 no-padding-top no-padding-left no-padding-right">
            <div class="row">
                <div class="col-sm-5" data-for="nodeName">
                    <div class="input-group">
                        <span class="input-group-addon">${i18n["nodeName" ]}</span>
                        <input class="form-control" type="text" maxlength="50" name="nodeName" />
                    </div>
                    <div class="space-2 visible-xs"></div>
                </div>
                <div class="col-xs-5 col-sm-3 no-padding-right" data-for="axis">
                    <div class="input-group">
                        <span class="input-group-btn">
                            <button data-toggle="dropdown" class="horizon-btn btn btn-default btn-sm" type="button">
                                <span></span>
                                <i class="ace-icon fa fa-angle-down bigger-130"></i>
                            </button>
                            <ul data-for="axisTypeY" class="dropdown-menu dropdown-light-blue dropdown-caret">
                                <li data-code="absolute-y">
                                    <a href="javascript:void(0);">${i18n["absoluteY" ]}</a>
                                </li>
                                <li data-code="relative-top">
                                    <a href="javascript:void(0);">${i18n["relativeTop" ]}</a>
                                </li>
                                <li data-code="relative-bottom">
                                    <a href="javascript:void(0);">${i18n["relativeBottom" ]}</a>
                                </li>
                            </ul>
                        </span>
                        <input class="form-control" type="text" name="axisY" onkeyup="this.value=this.value.replace(/\D/g, '')"/>
                    </div>
                </div>
                <div class="col-xs-5 col-sm-3 no-padding-right" data-for="axis">
                    <div class="input-group">
                        <span class="input-group-btn">
                            <button data-toggle="dropdown" class="horizon-btn btn btn-default btn-sm" type="button">
                                <span></span>
                                <i class="ace-icon fa fa-angle-down bigger-130"></i>
                            </button>
                            <ul data-for="axisTypeX" class="dropdown-menu dropdown-light-blue dropdown-caret">
                                <li data-code="absolute-x">
                                    <a href="javascript:void(0);">${i18n["absoluteX" ]}</a>
                                </li>
                                <li data-code="relative-left">
                                    <a href="javascript:void(0);">${i18n["relativeLeft" ]}</a>
                                </li>
                                <li data-code="relative-right">
                                    <a href="javascript:void(0);">${i18n["relativeRight" ]}</a>
                                </li>
                            </ul>
                        </span>
                        <input class="form-control" type="text" name="axisX" onkeyup="this.value=this.value.replace(/\D/g, '')"/>
                    </div>
                </div>
                <div class="col-xs-2 col-sm-1 align-right no-padding-left" data-for="axis">
                    <button type="button" title="" class="horizon-btn btn btn-primary btn-sm" data-action="position">
                        <i class="ace-icon fa fa-crosshairs"></i>
                    </button>
                </div>
            </div>
            <div class="padding-10 no-padding-left no-padding-right"></div>
        </div>
        <div class="authors">
            <div class="horizon-tab">
                <ul class="tab">
                    <li class="col-xs-4 no-padding" data-type="Author">
                        <a class="width-100 center" data-toggle="not-allowed">${i18n["author" ]}</a>
                    </li>
                    <li class="col-xs-4 no-padding" data-type="SecondAuthor">
                        <a class="width-100 center" data-toggle="not-allowed">${i18n["secondAuthor" ]}</a>
                    </li>
                    <li class="col-xs-4 no-padding" data-type="Reader">
                        <a class="width-100 center" data-toggle="not-allowed">${i18n["reader" ]}</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane" data-type="Author">
                        <div class="tags row">
                            <input type="hidden" name="id"/>
                            <input type="hidden" name="name"/>
                        </div>
                    </div>
                    <div class="tab-pane" data-type="SecondAuthor">
                        <div class="tags row">
                            <input type="hidden" name="id"/>
                            <input type="hidden" name="name"/>
                        </div>
                    </div>
                    <div class="tab-pane" data-type="Reader">
                        <div class="tags row">
                            <input type="hidden" name="id"/>
                            <input type="hidden" name="name"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row no-margin">
                <div class="col-xs-6 col-sm-3 col-md-2 no-padding timeLimit-container"></div>
                <div class="col-xs-6 col-sm-9 col-md-10 no-padding-right align-right">
                    <button disabled="disabled" class="horizon-btn btn btn-primary btn-sm" type="button" data-action="selectuser">
                        <i class="ace-icon fa fa-users"></i>${i18n["selectUser" ]}
                    </button>
                </div>
            </div>
        </div>
        <div class="timeLimit-group input-group">
            <input tabindex="-1" name="timeLimit" class="input-sm form-control"
                   onkeyup="this.value=this.value.replace(/\D/g, '')" onblur="this.value=this.value.replace(/\D/g, '')" type="text">
            <span class="input-group-btn dropup">
                <button data-toggle="dropdown" class="timeLimitBtn horizon-btn btn btn-default btn-sm" type="button">
                    <span>${i18n["workday" ]}></span>
                    <i class="ace-icon fa fa-angle-down bigger-130"></i>
                </button>
                <ul class="dropdown-menu dropdown-light-blue dropdown-menu-right dropdown-caret">
                    <li data-code="0" class="active">
                        <a href="javascript:void(0);">${i18n["workday" ]}></a>
                    </li>
                    <li data-code="1">
                        <a href="javascript:void(0);">${i18n["day" ]}</a>
                    </li>
                    <!-- 小时
                    <li data-code="2">
                        <a href="javascript:void(0);"><path:i18n group="workflow" key="hour" /></a>
                    </li>
                    -->
                </ul>
            </span>
        </div>
        <div class="subwork-list hidden"></div>
        <div class="msg-type" data-msg-type="workflow.messageType">
            <label>
                <input name="messageType" type="checkbox" class="ace" value="Mail"/>
                <span class="lbl">${i18n["mail" ]}></span>
            </label>
            <label>
                <input name="messageType" type="checkbox" class="ace" value="SMS"/>
                <span class="lbl">${i18n["sms" ]}></span>
            </label>
            <label>
                <input name="messageType" type="checkbox" class="ace" value="Msg"/>
                <span class="lbl">${i18n["msg" ]}></span>
            </label>
            <label>
                <input name="messageType" type="checkbox" class="ace" value="IM"/>
                <span class="lbl">${i18n["im" ]}></span>
            </label>
        </div>
        <div class="help-block red clearfix"></div>
    </div>
    <button type="button" class="hidden" id="focus">focus</button>
    <script data-main="${assetsPath}/module/workflow/js/horizon.workflow" src="${frontPath}/require/require.js"></script>
<#elseif workFlow.selectNodes?? &&  workFlow.openType=="false">
    <form class="form-horizontal" role="form">
        <div class="alert alert-info alert-inflow bigger-110 center">
            <div class="control-group">
                <label class="control-label bolder blue">
                    ${i18n["selectActiveNode" ]}
                </label>
                <br/>
                <label class="control-label">
                    <#list workFlow.selectNodes as node>
                        <div class="radio align-left">
                            <label>
                                <input name="trackId" type="radio" class="ace" value="${node.nodeId}"/>
                                <span class="lbl">${node.nodeName}</span>
                            </label>
                        </div>
                    </#list>
                </label>
                <input type="hidden" name="workId" value="${workFlow.workId}"/>
                <div class="hr hr-dotted hr18"></div>
                <p>
                    <button type="submit" class="btn btn-sm btn-success">
                        ${i18n["ok" ]}
                    </button>
                </p>
            </div>
        </div>
    </form>
<#else>
    <div class="alert alert-danger alert-inflow bigger-110 center">
        ${workFlow.flowMsg}
    </div>
</#if>
</body>
</html>
