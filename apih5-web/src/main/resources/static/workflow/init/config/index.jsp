<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.horizon.cn/taglib/path" prefix="path"%>
<!DOCTYPE html>
<html lang='<path:i18n group="" key="lang" />'>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <title><path:i18n group="init/load-license" key="init.load.license.title" /></title>
    <%@include file="../../common/bootstrap.css.include.jsp"%>
    <link rel="stylesheet" href="<path:frontPath/>/jquery/jquery-ui/css/jquery-ui.css" />
    <link rel="stylesheet" href="<path:frontPath/>/gritter/css/jquery.gritter.css" />
    <%@include file="../../common/ace.css.include.jsp"%>
    <link rel="stylesheet" href="<path:assetsPath/>/common/css/horizon.base.css" />
    <%@include file="../../common/oldbrowser.script.include.jsp"%>
</head>
<body class="no-skin">
<div class="container-fluid">
    <div class="page-header">
        <h1>
           <path:i18n group="load-license" key="largeTitle" /> <small> <i class="ace-icon fa fa-angle-double-right"></i>
           <path:i18n group="load-license" key="smallTitle" />
        </small>
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <div id="fuelux-wizard-container">
                <div>
                    <ul class="steps">
                        <li data-step="1" class="active">
                            <span class="step">1</span>
                            <span class="title"><path:i18n group="load-license" key="buttonsInit" /></span>
                        </li>
                        <li data-step="2">
                            <span class="step">2</span>
                            <span class="title"><path:i18n group="load-license" key="uploadLicense" /></span>
                        </li>
                        <li data-step="3">
                            <span class="step">3</span>
                            <span class="title"><path:i18n group="load-license" key="buttonsFinish" /></span>
                        </li>
                    </ul>
                </div>
                <hr />
                <div class="step-content">
                    <div class="step-pane active" data-step="1">
                        <form class="form-horizontal" action="" id="database-form" method="post">
                           
                            <div class="row no-margin">                                
                                <div class="col-xs-12 col-md-10 col-md-offset-1">
                                    <h5 class="header blue bigger"><path:i18n group="load-license" key="databaseConfig" /></h5>
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                 <path:i18n group="load-license" key="datasourceIdentifier" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <span class="block input-icon input-icon-right">
                                                        <input name="identifier" type="text" value="system" class="form-control"  maxlength="20" notChinese="true"/>
                                                        <i class="ace-icon fa fa-coffee"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                    <path:i18n group="load-license" key="datasourceConnectType" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <select name="connectionType" class="form-control">
                                                        <option value="DBCP">Spring</option>
                                                        <option value="JNDI">JNDI</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 hidden" for="JNDI">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                    <path:i18n group="load-license" key="datasourceJndi" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <span class="block input-icon input-icon-right">
                                                        <input name="jndiName" type="text" class="form-control ignore" maxlength="100" notChinese="true"/>
                                                        <i class="ace-icon fa fa-coffee"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                   <path:i18n group="load-license" key="datasourceType" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <select name="databaseType" id="databaseType" class="form-control">
                                                        <option value="Oracle">Oracle</option>
                                                        <option value="MySQL">MySQL</option>
                                                        <option value="DB2">DB2</option>
                                                        <option value="SqlServer">SqlServer</option>
                                                        <option value="DM">DM</option>
                                                        <option value="GBase">GBase</option>
                                                        <option value="GBase8s">GBase8s</option>
                                                        <option value="KingBase">KingBase</option>
                                                        <option value="Postgresql">Postgresql</option>
                                                        <option value="Oscar">ShenTong</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12" for="DBCP">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                  <path:i18n group="load-license" key="datasourceDriverClass" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <span class="block input-icon input-icon-right">
                                                        <input for="DBCP" name="driverClass" type="text" class="form-control" maxlength="200" notChinese="true"/>
                                                        <i class="ace-icon fa fa-coffee"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12" for="DBCP">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                    <path:i18n group="load-license" key="datasourceUrl" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <span class="block input-icon input-icon-right">
                                                        <input for="DBCP" name="jdbcUrl" type="text" class="form-control" maxlength="200" notChinese="true"/>
                                                        <i class="ace-icon fa fa-coffee"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12" for="DBCP">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                   <path:i18n group="load-license" key="datasourceUserName" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <span class="block input-icon input-icon-right">
                                                        <input for="DBCP" name="userName" type="text" class="form-control" maxlength="100" notChinese="true"/>
                                                        <i class="ace-icon fa fa-coffee"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12" for="DBCP">
                                            <div class="form-group has-info">
                                                <label class="col-xs-12 col-sm-3 control-label no-padding-right">
                                                    <path:i18n group="load-license" key="datasourceUserPassword" />
                                                </label>
                                                <div class="col-xs-12 col-sm-5">
                                                    <span class="block input-icon input-icon-right">
                                                        <input for="DBCP" name="password" type="text" class="form-control" maxlength="100" notChinese="true"/>
                                                        <i class="ace-icon fa fa-coffee"></i>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="hidden">                                
                                <input type="hidden" name="lobProcessorClass" />
                                <input type="hidden" name="lobBufferSize" value="1048576"/>
                                <input type="hidden" name="minPoolSize" value="3"/>
                                <input type="hidden" name="maxPoolSize" value="15"/>
                                <input type="hidden" name="initialPoolSize" value="3"/>
                                <input type="hidden" name="maxIdelTime" value="0"/>
                                <input type="hidden" name="isDefault" value="true" />
                                <input type="hidden" name="initServletName" />
                                <input type="hidden" name="initFlag" value="3"/> <!-- ?????????????????? -->
                                <input type="hidden" name="haveConn"/><!-- ???????????????????????? -->
                            </div>
                        </form>                        
                    </div>
                    <div class="step-pane" data-step="2">
                        <form class="form-horizontal"  id="upload-license-form" method="post" enctype="multipart/form-data">
                            <h4 class="green lighter bigger"><path:i18n group="load-license" key="uploadLicense" /></h4>
                            <div class="row no-margin">
                                <div class="col-xs-12 col-md-10 col-md-offset-1">
                                    <h5 class="blue bigger"><path:i18n group="load-license" key="licenseCode" /></h5>
                                    <div class="form-group">
                                         <div class="col-xs-12" id="registerCode" style="word-break: break-all;">
                                            <!--<textarea name="registerCode" class="form-control" rows="4"></textarea>-->
                                         </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <input name="license" type="file" class="hidden"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="step-pane" data-step="3">
                        <div class="center">
                            <h4 class="green lighter bigger"><path:i18n group="load-license" key="datasourceInitFinish" /></h4>
                            <span id="time" class="bolder bigger-200 red">3</span>
                        </div>
                    </div>
                </div>
            </div>
            <hr />
            <div class="wizard-actions">
                <button class="btn btn btn-info" id="btn-test-connection" data-initservletname="initSetUpCheckConn">
                    <i class="ace-icon fa fa-cog hidden-xs"></i>
                   <path:i18n group="load-license" key="buttonsTestConnect" />
                </button>
                <button class="btn btn-success" id="btn-init-database" data-initservletname="initSetUpData">
                    <i class="ace-icon glyphicon glyphicon-play-circle hidden-xs"></i>
                    <path:i18n group="load-license" key="buttonsInit" />
                </button>
                <button class="btn btn-success hidden" id="btn-upload-license" data-initservletname="initSetUpData">
                    <i class="ace-icon fa fa-cloud-upload hidden-xs"></i>
                    <path:i18n group="load-license" key="buttonsUpload" />
                </button>
                <button class="btn btn-success hidden btn-next">
                    <path:i18n group="load-license" key="buttonsFinish" />
                    <i class="ace-icon fa fa-arrow-right icon-on-right hidden-xs"></i>
                </button>
            </div>
            <div class="space-6"></div>
        </div>
    </div>
</div>
<%@include file="../../common/base.script.include.jsp"%>
<script type="text/javascript">
	var loadFlag = '${param.loadFlag}'; //1.?????????-license 2.????????? 3.license
	if(loadFlag==""){
	     loadFlag = "1";
	}	
    var gotoPage = '${param.gotoPage}';
</script>
<script data-main="<path:assetsPath/>/init/config/system.js" src="<path:frontPath/>/require/require.js"></script>
</body>
</html>