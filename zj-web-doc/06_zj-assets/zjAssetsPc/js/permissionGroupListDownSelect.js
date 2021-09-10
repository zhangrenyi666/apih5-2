var userData = {
    rows: [{
        name: 'oaGly',
        label: '选择用户'
    }]
}

myPickTree.initDataWidthName('oaGly');

// createDom(userData, 'form');
// function createDom(userData, dom) {
//     var dom = document.getElementById(dom);
//     dom.innerHTML='';
//     var row = '';
//     row += '<div data-key="0" class="row cl">';
//     row += '<label class="form-label  col-2 f-l"><i style="color: red;">';
//     row += '</i><span style="color:red">*</span><span>选择用户</span>：</label>';
//     row += '<div class="formControls col-8 f-l">';
//     row += '<div data-id="pickTree" data-name="oaGly" class="pickTree">';
//     row += '<ul data-id="pickTreeList" class="cl"></ul>';
//     row += '<button data-id="pickTreeAdd" data-name="oaGly" class="pickTreeAdd" type="button"></button>';
//     row += '</div>';
//     row += '</div>';
//     row += '<label class="form-label  col-2 f-l"><i style="color: red;">';
//     row += '</i><button data-name="userSave" class="btn  f-l btn-primary" type="button"><i  class="Hui-iconfont "></i> 保存</label>';
//     row += '</div>';
//     dom.innerHTML += row;
    

    // $.each(userData.rows, function (i, v) {
    //     row += '<div data-key="' + i + '" class="row cl">';
    //     row += '<label class="form-label  col-2 f-l"><i style="color: red;">';
    //     row += '</i><span style="color:red">*</span><span>' + v.label + '</span>：</label>';
    //     row += '<div class="formControls col-8 f-l">';
    //     row += '<div data-id="pickTree" data-name="' + v.name + '" class="pickTree">';
    //     row += '<ul data-id="pickTreeList" class="cl"></ul>';
    //     row += '<button data-id="pickTreeAdd" data-name="' + v.name + '" class="pickTreeAdd" type="button"></button>'
    //     row += '</div>';
    //     row += '</div>';
    //     row += '<label class="form-label  col-2 f-l"><i style="color: red;">';
    //     row += '</i><button data-name="userSave" class="btn  f-l btn-primary" type="button"><i  class="Hui-iconfont "></i> 保存</label>';
    //     row += '</div>';
    //      $(dom).append(row);
    //      myPickTree.initDataWidthName(v.name);
    // })

// }

function setSelect() {//资产状态（前）
    $("[data-id=pickTreeAdd]").on("click", function () {
        var pickType = {}
        pickType = {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
        };
        myPickTree.open($(this).attr("data-name"), pickType)
    })
}

function userSave(cb) {
    var _params = {};
    $.each(userData.rows, function (i, v) {
        _params[v.name] = {
            oaMemberList: myPickTree.getDataByName(v.name).member
        };
    })
    // console.log(_params)
    cb(_params);
}
