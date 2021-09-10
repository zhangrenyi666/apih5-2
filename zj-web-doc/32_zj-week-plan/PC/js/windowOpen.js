var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var now = new Date(); 
var nowTime = now.setHours(23,59,59); 
var day = now.getDay();
var oneDayLong = 24*60*60*1000; 
var MondayTime = nowTime - (day-8)*oneDayLong;
var tuesdayTime = nowTime - (day -9)*oneDayLong;
var wednesdayTime = nowTime - (day -10)*oneDayLong;
var thursdayTime = nowTime - (day -11)*oneDayLong;
var fridayTime = nowTime - (day -12)*oneDayLong;
var saturdayTime = nowTime - (day -13)*oneDayLong; 
var SundayTime =  nowTime - (day -14)*oneDayLong; 
var monday = new Date(MondayTime);
var tuesday = new Date(tuesdayTime);
var wednesday = new Date(wednesdayTime);
var thursday = new Date(thursdayTime);
var friday = new Date(fridayTime);
var saturday = new Date(saturdayTime);
var sunday = new Date(SundayTime);
monday = l.dateFormat(new Date(new Date(monday).getTime()), "yyyy-MM-dd");
tuesday = l.dateFormat(new Date(new Date(tuesday).getTime()), "yyyy-MM-dd");
wednesday = l.dateFormat(new Date(new Date(wednesday).getTime()), "yyyy-MM-dd");
thursday = l.dateFormat(new Date(new Date(thursday).getTime()), "yyyy-MM-dd");
friday = l.dateFormat(new Date(new Date(friday).getTime()), "yyyy-MM-dd");
saturday = l.dateFormat(new Date(new Date(saturday).getTime()), "yyyy-MM-dd");
sunday = l.dateFormat(new Date(new Date(sunday).getTime()), "yyyy-MM-dd");
var form = $('#form').detailLayer({
    conditions: [// 表单项配置
        {
            type: "select",
            name: "oaMemberId",
            label: "领导姓名",
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭               
            ],
            ajax: {
                api: "getZjLdzbOaMemberAllSelectList",
                valueName: "otherId",
                textName: "oaUserName",
            },
           // must: true
        },
        {
            type: "textarea",//
            name: "mondayContent",//
            label: "周一(" +monday+")",//
            placeholder: "领导日程填写请注明陪同部门",
            //must: true
        },
        {
            type: "textarea",//
            name: "tuesdayContent",//
            label: "周二(" +tuesday+")",//
            placeholder: "领导日程填写请注明陪同部门",
           // must: true
        },
        {
            type: "textarea",//
            name: "wednesdayContent",//
            label: "周三(" +wednesday+")",//
            placeholder: "领导日程填写请注明陪同部门",
            //must: true
        },
        {
            type: "textarea",//
            name: "thursdayContent",//
            label: "周四(" +thursday+")",//
            placeholder: "领导日程填写请注明陪同部门",
           // must: true
        },
        {
            type: "textarea",//
            name: "fridayContent",//
            label: "周五(" +friday+")",//
            placeholder: "领导日程填写请注明陪同部门",
            //must: true
        },
        {
            type: "textarea",//
            name: "saturdayContent",//
            label: "周六(" +saturday+")",//
            placeholder: "领导日程填写请注明陪同部门",
            //must: true
        },
        {
            type: "textarea",//
            name: "sundayContent",//
            label: "周日(" +sunday+")",//
            placeholder: "领导日程填写请注明陪同部门",
            //must: true
        }

    ],
    onAdd: function (obj, _params) {
        l.ajax('addZjLdzbLeaderPlan', _params, function (data,message,success) {
            if(success == true){
                layer.alert('新增成功',{ icon: 1 },function(index){
                    layer.close(index);
                    window.opener.location.reload();
                    window.close();
                });
            }
        })
    }
})
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "formclose":
            window.close('windowOpen.html');
            break;
    }
})