/**
 * 代办管理
 * @author yaodd
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(['jquery',  'horizonTable', 'jqueryValidateAll','jqueryForm' ,
            'horizonSelectuser', 'bootstrapDatepicker', 'gritter'], factory);
    }else {
        factory(jQuery);
    }
}(function($) {
    "use strict";
    var $dialog=$('#flowagentmanager-dialog');
    var table = {
        //初始化视图数据
        init: function() {
            table.mytable = $('#myDatatable').horizonTable({
                settings: {
                    title: horizon.lang.base['infoList'],
                    multipleSearchable : true,
                    height: function() {
                        var _height = horizon.tools.getPageContentHeight(),
                            $pageHeader = $('.page-header');
                        if($pageHeader.css('display') != 'none') {
                            _height -= $pageHeader.outerHeight(true);
                        }
                        return _height;
                    },
                    checkbox: 0,
                    columns: [
                        {
                            dataProp : 'flowId'
                        },
                        {
                            name : 'flowName',
                            title : horizon.lang['agent-common']['flowName'],
                            width : '200px',
                            searchable : true,
                            multipleSearchable : true
                        },
                        {
                            name : 'agentUserName',
                            title : horizon.lang['agent-common']['agentUserName'],
                            width : '150px',
                            searchable : true,
                            multipleSearchable:true,
                            columnClass :'center'
                        },
                        {
                            name : 'userName',
                            title : horizon.lang['agent-common']['userName'],
                            width : '150px',
                            searchable : true,
                            columnClass :'center',
                            multipleSearchable:true
                        },
                        {
                            name : 'beginDate',
                            title : horizon.lang['agent-common']['beginDate'],
                            width : '100px',
                            columnClass :'center'
                        },
                        {
                            name : 'endDate',
                            title : horizon.lang['agent-common']['endDate'],
                            width : '100px',
                            columnClass :'center'
                        }
                    ],
                    buttons : [
                        {
                            id: 'setFlow',
                            text: horizon.lang['manager-agent']['buttonsSetFlow'],
                            icon: 'fa fa-cog green',
                            fnClick: function(){
                                var ids = table.mytable.checkRowKeyArray;
                                if(!ids.length){
                                	horizon.notice.error(horizon.lang['manager-agent']['selectSet']);
                                }else{
                                    var idsArr=[];
                                    $.each(table.mytable.checkRowDataArray,function(i,checkData){
                                        idsArr.push(checkData.flowId +'~'+ checkData.flowName);
                                    });
                                    operate.setFlow(idsArr.join(";"));
                                }
                            }
                        },
                        {
                            id: 'cancelSetFlow',
                            text: horizon.lang['manager-agent']['buttonsCancelSet'],
                            icon: 'fa fa-times red2',
                            fnClick: function(){
                                var ids = table.mytable.checkRowKeyArray;
                                if(!ids.length){
                                	horizon.notice.error(horizon.lang['manager-agent']['selectCancel']);
                                }else{
                                    var idsArr=[];
                                    $.each(table.mytable.checkRowDataArray,function(i,checkData){
                                        if(checkData.agentUserName !=null && checkData.agentUserName !=""){
                                            idsArr.push(checkData.flowId +'~'+ checkData.id);
                                        }
                                    });
                                    if(idsArr != null && idsArr.length >0){
                                        var ids=idsArr.join(";");
                                        $('input[name="ids"]').val(ids);
                                        $('input[name="actionName"]').val('2');
                                        $dialog.dialog({
                                            closeText:horizon.lang.base['close'],
                                            title:horizon.lang.message['title'],
                                            dialogText:horizon.lang['manager-agent']['confirm'],
                                            dialogTextType:'alert-danger',
                                            buttons:[
                                                {
                                                    html: horizon.lang.base['ok'],
                                                    "class" : "btn btn-primary btn-xs",
                                                    click: function() {
                                                        $("#setAgent").submit();
                                                        $( this ).dialog( "close" );
                                                    }
                                                }
                                            ]
                                        });
                                    }else{
                                    	horizon.notice.error(horizon.lang['manager-agent']['selected']);
                                    }
                                }
                            }
                        }
                    ],
                    pluginSettings: {
                        "order":[[4,'asc'],[5,'asc']]
                    }
                },
                ajaxDataSource: horizon.tools.formatUrl('/workcenter/agent/manage/list')
            });
        }
    };


    var operate = {
        initSetAgentForm: function() {
            operate.initDatepicker();
            operate.openAgenter();
            //验证被代理人
            jQuery.validator.addMethod("isUserName", function(value, element) {
                if($('input[name="userid"]').val() == $('input[name="agentID"]').val()){
                    return false;
                }else{
                    return true;
                }
            },horizon.lang['manager-agent']['sameError']);
            //验证时间
            jQuery.validator.addMethod("isDate", function(value, element) {
                var beginDate = $('input[name="begindate"]').val();
                var endDate = $('input[name="enddate"]').val();
                if(beginDate <= endDate){
                    return true;
                }else{
                    return false;
                }
            },horizon.lang['manager-agent']['timeValid']);
            operate.checkForm();
        },
        setFlow:function(ids) {
            var $validate = operate.checkForm();
            $validate.resetForm();
            $('input[name="actionName"]').val('1');
            $('.form-group').removeClass('has-error');
            $('input[name="ids"]').val(ids);
            $('input[name="agentID"]').val('');
            $('input[name="userid"]').val('');
            $('#setFlowAgent').dialog({
                closeText:horizon.lang.base['cancel'],
                width: $(window).width() > 550?'550':'auto',
                height: 'auto',
                maxHeight: $(window).height(),
                title:horizon.lang['manager-agent']['buttonsSet'],
                buttons:[
                    {
                        html: horizon.lang.base['ok'],
                        "class" : "btn btn-primary btn-xs",
                        click: function() {
                            $("#setAgent").submit();
                        }
                    },
                    {
                        html: horizon.lang.base['reset'],
                        "class" : "btn btn-yellow btn-xs",
                        click: function() {
                            $('input[name="agentID"]').val('');
                            $('input[name="userid"]').val('');
                            $validate.resetForm();
                            $('.form-group').removeClass('has-error');
                        }
                    }
                ]
            });
        },
        initDatepicker:function() {
            $('input[name="begindate"]').datepicker({
                format: 'yyyy-mm-dd',
                language: horizon.vars.lang,
                autoclose: true,
                startDate: new Date()
            });
            $('input[name="enddate"]').datepicker({
                format: 'yyyy-mm-dd',
                language: horizon.vars.lang,
                autoclose: true,
                startDate: new Date()
            });
        },
        openAgenter:function() {
            $('input[name="agentName"]').click(function() {
                $.horizon.selectUser({
                    idField: 'agentID',
                    cnField: 'agentName',
                    multiple: false,
                    selectDept: false,
                    selectPosition: false,
                    selectGroup: false,
                    fnClose:function() {
                        $('#setFlowAgent').closest('.ui-dialog').show().next().show();
                    }
                });
                $('#setFlowAgent').closest('.ui-dialog').hide().next().hide();
            });
            $('input[name="username"]').click(function() {
                $.horizon.selectUser({
                    idField: 'userid',
                    cnField: 'username',
                    multiple: false,
                    selectDept: false,
                    selectPosition: false,
                    selectGroup: false,
                    fnClose:function() {
                        $('#setFlowAgent').closest('.ui-dialog').show().next().show();
                    }
                });
                $('#setFlowAgent').closest('.ui-dialog').hide().next().hide();
            });
        },
        checkForm:function() {
            return $("#setAgent").validate({
                focusInvalid: false,
                errorClass: 'help-block col-xs-12 col-sm-reset inline',
                rules: {
                    enddate: {
                        isDate:true
                    },
                    username:{
                        isUserName:true
                    }
                },
                errorPlacement: function (error, element) {
                    error.insertAfter(element.closest('div[class*="col-"]'));
                },
                highlight: function (e) {
                    $(e).closest('.form-group').addClass('has-error');
                },
                success: function (e) {
                    $(e).closest('.form-group').removeClass('has-error');
                    $(e).remove();
                },
                submitHandler: function (form){
                    table.mytable.showProcessing();
                    $(form).ajaxSubmit({
                        url:horizon.tools.formatUrl('/workcenter/agent/setagent'),
                        dataType: 'json',
                        type:'POST',
                        cache:false,
                        error: function() {
                            table.mytable.hideProcessing();
                            horizon.notice.error(horizon.lang.message['operateError']);
                        },
                        success: function(data) {
                            table.mytable.hideProcessing();
                            if(data.restype == "success"){
                            	horizon.notice.success(data.msg[0]);
                                if(data.msg[0] == horizon.lang['manager-agent']['setSuccess']){
                                    $('#setFlowAgent').dialog('close');
                                }
                                table.mytable.reload();
                            }else{
                            	horizon.notice.error(horizon.lang.message['operateError']);
                            }
                        }
                    });
                }
            });
        }
    };

    return horizon.engine['manageragentlist'] = {
        initTable: table.init,
        initSetAgentForm: operate.initSetAgentForm
    };

}));