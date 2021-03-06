var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var selectDataList = [
    {
        value: new Date(((new Date().getFullYear()) + '/'+ 1 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-01'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 2 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-02'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 3 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-03'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 4 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-04'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 5 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-05'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 6 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-06'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 7 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-07'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 8 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-08'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 9 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-09'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 10 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-10'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 11 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-11'
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + 12 + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear() + '-12'
    }
];
var table = $('#table').DataTable({
    "info": false,//????????????????????????
    "paging": false,//????????????????????????
    "ordering": false, //????????????DataTables??????????????????
    "processing": false,//??????????????????????????????
    "searching": false,//????????????????????????
    "autoWidth": false,//????????????????????????
    "columnDefs": [//???????????????
        {
            "targets": [0],
            "data": "rowIndex",
            "width": 13,
            "title": '<input type="checkbox">',
            "render": function (data) {
                return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
            }
        },
        {
            "targets": [1],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//?????????
            "data": "assessmentTitle",//??????????????????
            "title": "??????",//?????????
            "defaultContent": "-",//????????????
            "render": function (data, _, rowData) {
                var html = '<a style="color:blue;" onclick="myOpen(\'????????????\',\'' + encodeURI(JSON.stringify(rowData)) + '\',\'' + 'MinisterPerson' + '\')">' + data + '</a>';
                return html;
            }
        },
        {
            "targets": [3],//?????????
            "data": "assessmentYears",//??????????????????
            "title": "????????????",//?????????
            "defaultContent": "-",//????????????,
            "width": 100,
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM");
            }
        },
        // {
        //     "targets": [4],//?????????
        //     "width": 100,
        //     "data": "assessmentQuarter",//??????????????????
        //     "title": "????????????",//?????????
        //     "defaultContent": "-",//????????????,
        //     "render": function (data) {
        //         if (data === "0") {
        //             return '????????????'
        //         } else if (data === "1") {
        //             return '????????????'
        //         } else if (data === "2") {
        //             return '????????????'
        //         } else if (data === "3") {
        //             return '????????????'
        //         } else if (data === '4') {
        //             return '????????????';
        //         } else if (data === '5') {
        //             return '????????????';
        //         } else if (data === '6') {
        //             return '????????????';
        //         } else if (data === '7') {
        //             return '????????????';
        //         } else if (data === '8') {
        //             return '????????????';
        //         } else if (data === '9') {
        //             return '????????????';
        //         } else if (data === '10') {
        //             return '???????????????';
        //         } else if (data === '11') {
        //             return '???????????????';
        //         } else {
        //             return '--'
        //         }
        //     }
        // },
        {
            "targets": [4],//?????????
            "width": 100,
            "data": "assessmentType",//??????????????????
            "title": "????????????",//?????????
            "defaultContent": "-",//????????????,
            "render": function (data) {
                if (data === "1") {
                    return '??????????????????'
                } else if (data === "2") {
                    return '??????????????????'
                } else if (data === "3") {
                    return '??????'
                } else {
                    return '--'
                }
            }
        },
        {
            "targets": [5],//?????????
            "data": "assessmentRemarks",//??????????????????
            "title": "??????",//?????????
            "width": 100,
            "defaultContent": "-",//????????????
        },
        {
            "targets": [6],//?????????
            "width": 200,
            "data": "modifyTime",//??????????????????
            "title": "????????????",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        // {
        //     "targets": [7],//?????????
        //     "data": "apih5FlowStatus",//??????????????????//???
        //     "title": "????????????",//?????????
        //     "defaultContent": "-",//????????????
        //     "render": function (data) {
        //         if (data === '') {
        //             return '??????';
        //         } else if (data === '0') {
        //             return '?????????';
        //         } else if (data === '1') {
        //             return '?????????';
        //         } else if (data === '2') {
        //             return '????????????';
        //         } else if (data === '3') {
        //             return '??????';
        //         } else {
        //             return '--';
        //         }
        //     }
        // },
        {
            "targets": [7],//?????????
            "data": "state",//??????????????????
            "title": "????????????",//?????????
            "width": 100,
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data === '2') {
                    return '?????????????????????';
                } else if (data === '1') {
                    return '?????????????????????';
                } else if (data === '0') {
                    return '?????????';
                } else {
                    return '--'
                }
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//???????????????
        {
            type: "text",
            name: "assessmentTitle",//???????????????
            label: "??????",//?????????????????????????????????
            placeholder: "???????????????",
        }
    ],
    onSearch: function (arr) {//??????????????????
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    },
    onReset: function (arr) {//??????????????????
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax????????????
        url: l.getApiUrl("getZjXmCqjxProjectAssessmentManageList"),
        params: {
            // sarsType:"1"
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});
//??????????????????
var yearDetailLayer = $('#yearDetailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",
            name: "managerId",
        },
        {
            type: "pickTree",
            name: "assessmentMemberList",
            label: "????????????",
            must: true,
            pickType: {
                department: false,//?????????????????????????????????,false?????????????????????
                member: "oaMemberList",//?????????????????????????????????,false?????????????????????
            }
        },
        {
            type: "text",
            name: "assessmentTitle",
            label: "??????",
            placeholder: "?????????",
            must: true
        },
        {
            type: "select",
            name: "assessmentType",
            label: "????????????",
            placeholder: "?????????",
            must: true,
            selectList: [
                {
                    value: "1",
                    text: "??????????????????"
                },
                {
                    value: "2",
                    text: "??????????????????"
                },
                {
                    value: "3",
                    text: "??????"
                }
            ],
        },
        {
            type: "select",
            name: "assessmentYears",
            label: "??????",
            placeholder: "?????????",
            defaultValue: new Date(((new Date().getFullYear()) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
            selectList: selectDataList,
            must: true
        },
        // {
        //     type: "select",
        //     name: "assessmentQuarter",
        //     label: "??????",
        //     placeholder: "?????????",
        //     must: true,
        //     selectList: [
        //         {
        //             value:"0",
        //             text:"????????????"
        //         },
        //         {
        //             value:"1",
        //             text:"????????????"
        //         },
        //         {
        //             value:"2",
        //             text:"????????????"
        //         },
        //         {
        //             value:"3",
        //             text:"????????????"
        //         },
        //         {
        //             value:"4",
        //             text:"????????????"
        //         },
        //         {
        //             value:"5",
        //             text:"????????????"
        //         }
        //     ]
        // },
        {
            type: "date",//
            name: "dutyClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "dutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "firstDutyClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalDutyClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "scoreClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "scoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "firstScoreClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstScoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalScoreClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalScoreClosingEndDate",
            must: true
        },
        {
            type: "textarea",
            name: "assessmentRemarks",
            label: "??????",
            placeholder: "?????????"
        }
    ],
    onSave: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
        }
        l.ajax('updateZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
            _params.assessmentQuarter = "6";
        }
        l.ajax('addZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    }
})
//??????????????????
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",
            name: "managerId",
        },
        {
            type: "pickTree",
            name: "assessmentDeptList",
            label: "????????????",
            pickType: {
                department: "oaDepartmentList",// ?????????????????????????????????,false?????????????????????
                member: false,// ?????????????????????????????????,false?????????????????????
            },
            must: true
        },
        {
            type: "pickTree",
            name: "assessmentMemberList",
            label: "????????????",
            must: true,
            pickType: {
                department: false,//?????????????????????????????????,false?????????????????????
                member: "oaMemberList",//?????????????????????????????????,false?????????????????????
            }
        },
        {
            type: "text",
            name: "assessmentTitle",
            label: "??????",
            placeholder: "?????????",
            must: true
        },
        {
            type: "select",
            name: "assessmentType",
            label: "????????????",
            placeholder: "?????????",
            must: true,
            selectList: [
                {
                    value: "1",
                    text: "??????????????????"
                },
                {
                    value: "2",
                    text: "??????????????????"
                },
                {
                    value: "3",
                    text: "??????"
                }
            ],
        },
        // {
        //     type: "date",//
        //     name: "dutyClosingEndDate11",//
        //     label: "??????????????????????????????",//
        //     dateFmt: "yyyy/MM",
        //     defaultValue: new Date(),
        //     id: "dutyClosingEndDate11",
        //     must: true
        // },	        
        {
            type: "select",
            name: "assessmentYears",
            label: "????????????",
            placeholder: "?????????",
            defaultValue: new Date(((new Date().getFullYear()) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
            selectList: selectDataList,
            must: true
        },
        // {
        //     type: "select",
        //     name: "assessmentQuarter",
        //     label: "??????",
        //     placeholder: "?????????",
        //     must: true,
        //     selectList: [
        //         {
        //             value: "0",
        //             text: "????????????"
        //         },
        //         {
        //             value: "1",
        //             text: "????????????"
        //         },
        //         {
        //             value: "2",
        //             text: "????????????"
        //         },
        //         {
        //             value: "3",
        //             text: "????????????"
        //         },
        //         {
        //             value: "4",
        //             text: "????????????"
        //         },
        //         {
        //             value: "5",
        //             text: "????????????"
        //         },
        //         {
        //             value: "6",
        //             text: "????????????"
        //         },
        //         {
        //             value: "7",
        //             text: "????????????"
        //         },
        //         {
        //             value: "8",
        //             text: "????????????"
        //         },
        //         {
        //             value: "9",
        //             text: "????????????"
        //         },
        //         {
        //             value: "10",
        //             text: "???????????????"
        //         },
        //         {
        //             value: "11",
        //             text: "???????????????"
        //         }
        //     ]
        // },
        {
            type: "date",//
            name: "dutyClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "dutyClosingEndDate",
            must: true
        },	
        {
            type: "date",//
            name: "firstDutyClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalDutyClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "scoreClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "scoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "firstScoreClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstScoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalScoreClosingEndDate",//
            label: "??????????????????????????????",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalScoreClosingEndDate",
            must: true
        },
        {
            type: "textarea",
            name: "assessmentRemarks",
            label: "??????",
            placeholder: "?????????"
        }
    ],
    onSave: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
        }
        l.ajax('updateZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
        }
        l.ajax('addZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    }
})

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            // layer.confirm('???????????????????????????????????????', {
            //     btn: ['???', '???'] //?????????????????????
            // }, function (index, layero) {
            //     yearDetailLayer.open();
            //     layer.close(index);
            // }, function (index) {
            //     detailLayer.open();
            // });
            break;
        case "edit":
            if (checkedData.length == 1) {
                if (checkedData[0].apih5FlowStatus == 1 || checkedData[0].apih5FlowStatus == 2) {
                    layer.alert("???????????????????????????", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                } else {
                    detailLayer.open(checkedData[0]);
                }
            } else if (checkedData.length == 0) {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "yearAssLaunch":
            if (checkedData.length == 1) {
                if (!checkedData[0].assessmentQuarter == 6) {
                    layer.alert("??????????????????????????????", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                } else {
                    layer.confirm("???????????????", { icon: 0, }, function (index) {
                        l.ajax("batchZjXmCqjxProjectAssessmentManageSendMessage", checkedData[0], function () {
                            pager.page('remote')
                        })
                        layer.close(index);
                    });
                }
            } else if (checkedData.length == 0) {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "planLaunch":
            if (checkedData.length >= 1) {
                layer.confirm("???????????????", { icon: 0, }, function (index) {
                    for (i = 0; i < checkedData.length; i++) {
                        checkedData[i].state = "1";
                    }
                    l.ajax("batchZjXmCqjxProjectAssessmentManageSendMessage", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });

            } else {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "assLaunch":
            if (checkedData.length == 1) {
                layer.confirm("???????????????", { icon: 0, }, function (index) {
                        if (checkedData[0].state === "1") {
                            l.ajax("checkZjXmCqjxProjectFinishPlanAssistant", checkedData[0], function (data, message, success) {
                                if(success){
                                    checkedData[0].state = "2";
                                    l.ajax("batchZjXmCqjxProjectAssessmentManageSendMessage", checkedData, function () {
                                        pager.page('remote')
                                    })
                                    layer.close(index);    
                                }
                            })                                                    
                        } else {
                            layer.alert("???????????????????????????", { icon: 0, }, function (index) {
                                layer.close(index);
                            });
                        }
                });    
            } else if (checkedData.length == 0) {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }

            // if (checkedData.length >= 1) {
            //     layer.confirm("???????????????", { icon: 0, }, function (index) {
            //         for (i = 0; i < checkedData.length; i++) {
            //             // console.log(checkedData[i].state);
            //             if (checkedData[i].state == "1") {
            //                 checkedData[i].state = "2";
            //             } else {
            //                 layer.alert("???????????????????????????", { icon: 0, }, function (index) {
            //                     layer.close(index);
            //                 });
            //                 break;
            //             }
            //             if (i == checkedData.length - 1) {
            //                 l.ajax("batchZjXmCqjxProjectAssessmentManageSendMessage", checkedData, function () {
            //                     pager.page('remote')
            //                 })
            //                 layer.close(index);
            //             }
            //         }
            //     });

            // } else {
            //     layer.alert("??????????????????", { icon: 0, }, function (index) {
            //         layer.close(index);
            //     });
            // }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("??????????????????", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                // for(var i = 0; i < checkedData.length; i++){
                //     if(checkedData[i].apih5FlowStatus == 1 && checkedData[i].apih5FlowStatus == 2){
                //         layer.alert("??????????????????????????????!",{ icon: 0,},function (index) {
                //             layer.close(index);
                //         });
                //         break;
                //     }else if(i === checkedData.length - 1){
                layer.confirm("???????????????", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjXmCqjxProjectAssessmentManage", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
                //     }
                // }
            }
            break;
    }
})
var myOpenLayer;
function myOpen(title, rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    l.ajax("batchCheckZjXmCqjxExecutiveAssistantLaunch", rowData, function () {
    })
    if (rowData.assessmentType === "3") {
        url = "MinisterPersonStaff";
    }
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&managerId=' + rowData.managerId
    };
    myOpenLayer = layer.open(options);
}
