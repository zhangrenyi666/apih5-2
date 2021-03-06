

var allCompany = {};

l.ajax('getSignatureCompanyTotalList', { supervisionId: l.getUrlParam("supervisionId") }, function (data, message, success) {
    if (success && data) { 
        //create tabbar
        // var $tabBar = $('<div />', {
        //     class: 'tabBar clearfix',
        //     css: {
        //         margin:'20px 0'
        //     }
        // }).appendTo('#tab-system');

        var $tabBar = $('<div style="margin:20px 0" class="tabBar clearfix"></div>');
        $('#tab-system').append($tabBar);

        //each data
        $.each(data, function (index, item) {
            // var $bar = $('<div />', {
            //     html: '<span>' + item.companyName + '</span>',
            //     click: function () {
            //         var supervisionCompanyId = item.supervisionCompanyId;
            //         pager.page('remote', {
            //             supervisionCompanyId: supervisionCompanyId
            //         })
            //     }
            // }).appendTo($tabBar);

            var $bar = $('<div><span>' + item.companyName + '</span></div>');
            $bar.click(function () {
                var supervisionCompanyId = item.supervisionCompanyId;
                pager.page('remote', {
                    supervisionCompanyId: supervisionCompanyId
                });
            });
            $tabBar.append($bar);

            // var $barCon = $('<div/>', {
            //     class: 'tabCon',
            //     html: '<div>' +
            //         '<form class="form form-horizontal" id="form' + item['companyId'] + '"></form>'
            //         +
            //         '<table id="table' + item['companyId'] + '" class="table table-border table-bordered table-bg table-hover"></table>'
            //         +
            //         '<div class="cl"> <div id="pager' + item['companyId'] + '" class="m-pagination f-r"></div></div>'
            //         + '</div>'
            // }).appendTo('#tab-system');

            var $barCon = $('<div class="tabCon"><div>'+
            '<form class="form form-horizontal" id="form' + item['companyId'] + '"></form>'+
            '<table id="table' + item['companyId'] + '" class="table table-border table-bordered table-bg table-hover"></table>'+
            '<div class="cl"> <div id="pager' + item['companyId'] + '" class="m-pagination f-r"></div></div>'
            +'</div></div>');
            // console.log($barCon)
            $('#tab-system').append($barCon);


            var table = allCompany[item.companyId] = $('#table' + item['companyId']).DataTable({
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
                        "data": "objectName",//??????????????????
                        "title": "?????????",//?????????
                        "defaultContent": "-",//????????????
                    },
                    {
                        "targets": [3],//?????????
                        "data": "fileName",//??????????????????
                        "title": "????????????",//?????????
                        "defaultContent": "-",//????????????
                    },
                    {
                        "targets": [4],//?????????
                        "data": "state",//??????????????????
                        "title": "????????????",//?????????
                        "defaultContent": "-",//????????????
                        "render": function (data) {
                            var text = ""
                            switch (data) {
                                case "0": text = "?????????";
                                    break;
                                case "1": text = "?????????";
                                    break;
                                default: text = "??????";
                                    break;
                            }
                            return text
                        }
                    },
                    {
                        "targets": [5],//?????????
                        "width": 300,
                        "data": "signTime",//??????????????????
                        "title": "????????????",//?????????
                        "defaultContent": "-",//????????????
                        "render": function (data) {
                            return !data ? "-" : l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                        }
                    },
                    {
                        "targets": [6],//?????????
                        "width": 80,
                        "data": function (row) {
                            return row
                        },//??????????????????
                        "title": "??????",//?????????
                        "render": function (data) {
                            return '<div style="color:#0099FF;text-align:center; cursor:pointer" onclick="myOpen(\'' + data.url + '\')">????????????</div>';

                        }
                    }
                ]
            });

            var form = $('#form'+item.companyId).filterfrom({
                conditions: [//???????????????
                    {
                        type: "text",//???????????????text,select,date,
                        name: "objectName",//???????????????
                        label: "??????",//?????????????????????????????????
                        placeholder: "????????????????????????",
                    }
                ],
                onSearch: function (arr) {//??????????????????
                    var _params = {};
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                            if (arr[i].value) {
                                _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                            } else {
                                _params[arr[i].name] = "";
                            }
                        } else {
                            _params[arr[i].name] = arr[i].value;
                        }
                    }
                    //console.log('??????????????????',_params)
                    pager.page('remote', 0, _params)
                },
                onReset: function (arr) {//??????????????????
                    var _params = {};
                    for (var i = 0; i < arr.length; i++) {
                        _params[arr[i].name] = arr[i].value;
                    }
                    pager.page('remote', _params)
                }
            })

            var pager = $('#pager'+item['companyId']).page({
                remote: {// ajax????????????
                    url: l.getApiUrl("getSignatureTotalList"),
                    params: {
                        supervisionCompanyId: item.supervisionCompanyId,
                        objectName: ''
                    },
                    success: function (result) {
                        if (result.success) {
                            var data = result.data;
                            // console.log('data:', data)
                            allData = data;
                            $.each(data, function (index, item) {
                                item.rowIndex = index
                            })
                            allCompany[item.companyId].clear().rows.add(data).draw();
                        } else {
                            layer.alert(result.message, { icon: 0, }, function (index) {
                                layer.close(index);
                            });
                        }
                    },
                }
            });
        })
    }

    $("#tab-system").Huitab({
        index: 0
    });
})

function myOpen(url) {
    if (url) {
        window.location.href = url;
    }
}



