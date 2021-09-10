

displaynavbar(this); 
l.addApi({ zpIndex: "zpIndex", confirmPwdZp: "confirmPwdZp" })
l.ajax("zpIndex", {}, function (data,message,success) {
    if(!success){
        return logout() 
    }
    data={}
    $("#userId").text(data.userId)
    $("body").Huitab({
        tabBar: ".navbar-wrapper .navbar-levelone",
        tabCon: ".Hui-aside .menu_dropdown",
        className: "current",
        index: data.roleId == 1 ? "0" : "1",
    });
})

var detailLayer = $('#detailLayer').detailLayer({
    layerTitle: "修改密码",
    layerArea: ["720px", "360px"],
    conditions: [
        {
            type: "text",//
            name: "oldUserPwd",//
            label: "原始密码",//
            placeholder: "请输入原始密码",
            must: true,
        },
        {
            type: "text",//
            name: "newUserPwd",//
            label: "新密码",//
            placeholder: "请输入8-20个字符",
            must: true,
        },
        {
            type: "text",//
            name: "newUserPwd2",//
            label: "重复密码",//
            placeholder: "请输入8-20个字符",
            must: true,
        }
    ],
    onAdd: function (obj, params) {
        var _params = {}
        if (params.newUserPwd != params.newUserPwd2) {
            layer.alert("新密码与重复密码不一致", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return
        }
        if (params.newUserPwd.length < 8 || params.newUserPwd.length > 20) {
            layer.alert("密码长度不符合规定", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return
        }
        _params['oldUserPwd']=params.oldUserPwd;
        _params['newUserPwd']=params.newUserPwd;
        l.ajax("confirmPwdZp", _params, function (data) {
            Apih5.log(data)
            obj.close()
        })
    }
})


/*个人信息*/
function myselfinfo() {
    detailLayer.open();
}
function logout() {
    Apih5.log(l.domain + "logoutZp")
    window.location.href = l.domain + "logoutZp"
}