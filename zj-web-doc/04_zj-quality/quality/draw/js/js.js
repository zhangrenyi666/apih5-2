

var wxx = {
    personArr : [],//随机转动的人数
    clickState : false,// 点击状态
    transformTimer : null,//转动特效定时器
    $str:'',//中奖人dom
    totalNumber:0,//抽奖总人数
    lotteryNum:0,//剩余抽奖次数
    transform:function(cb){// 点击按钮时的转动特效  
        wxx.transformTimer = setInterval(function(){
            var unitNum=parseInt(Math.random()*(wxx.personArr.length));
            var nameNum=parseInt(Math.random()*(wxx.personArr.length));
            var telNum=parseInt(Math.random()*(wxx.personArr.length));
            $(".unit").text(wxx.personArr[unitNum].unit.slice(0,6));
            $(".name").text(wxx.personArr[nameNum].name)
            var strTel=wxx.personArr[telNum].phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
            $(".tel").text(strTel)
        },120);
        cb(wxx.transformTimer);
    },
    createWinList:function(data,cb){//创建中奖名单  传入中奖人数组
        wxx.$str = '';
        $.each(data, function(i, v){
            wxx.$str+='<li class="list-con animated rollIn">';
            wxx.$str+='<span>';
            wxx.$str+=(v.unit).slice(0,6);
            wxx.$str+='</span>';
            wxx.$str+='<span>';
            wxx.$str+=v.name;
            wxx.$str+='</span>';
            wxx.$str+='<span>';
            var strTel=v.phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
            wxx.$str+=strTel;
            wxx.$str+='</span>';
            wxx.$str+='</li>';  
        })
        cb(wxx.$str)
    }
}

//设置活动名称
if( l.getUrlParam("title") && l.getUrlParam("id") ){
    $(".lotteryName").text(l.getUrlParam("title"));
}else{
    layer.alert('请从 “抽奖候选人列表” 页面打开本页！')
}

l.ajax("getCandidateList", { lotteryId:l.getUrlParam("id") },function(res){
    wxx.lotteryNum = Number(res.settingTimes) -  Number(res.actualTimes); //设置抽奖次数
    // console.log('lotteryNum:',wxx.lotteryNum)

    wxx.personArr = res.candidateList ;
    wxx.totalNumber = res.totalNumber;//抽奖总数量
    $(".sum-person-span").text(wxx.totalNumber);//选人总抽奖人数

    $('.btn').click(function(){
        var lotteryNum = $("#select-num").val();//抽奖数量
        if(wxx.clickState){//处于点击状态
            clearInterval(wxx.transformTimer);
            $(".btn").text("开始抽奖");
            wxx.clickState = false;//设置状态为未点击状态
            var params = {
                lotteryId:l.getUrlParam("id"),
                winningNum:lotteryNum
            }
            l.ajax('toDrawAndReturnResult', params, function(data, message, success){
                if(success){
                    wxx.totalNumber = data.totalNumber;//抽奖总数量
                    $(".sum-person-span").text(wxx.totalNumber);//选人总抽奖人数
                    wxx.createWinList(data.candidateList, function(html){
                        $("#list_ul").html(html);
                    });
                }
            })
        }else{//处于未被点击
            if(lotteryNum > wxx.totalNumber){
               layer.alert("抽奖数量不能大于抽奖总人数！") 
            }else if(lotteryNum < 1){
                layer.alert("抽奖数量不能小于1！") 
            }else if(wxx.lotteryNum <= 0){
                layer.alert("抽奖次数用完了！"); 
            }else{
                wxx.transform(function(timer){// 开始执行动画
                    $(".btn").text("停止抽奖");
                    wxx.lotteryNum --;
                    wxx.clickState = true;//设置状态为点击状态
                });
            }
        }
    })
})

//导出数据
$(".derive").click(function(){
    // if(ajaxData.length<1){
    //     layer.alert("请先抽奖！");
    //     return;
    // }
    layer.alert("确定导出此数据？", { icon: 0 }, function (index) {
        var params={};
        params.lotteryId=l.getUrlParam("id");
        layer.close(index);
        l.ajax("exportWinnerList",params,function(res){
            layer.alert("导出成功！", { icon: 0 }, function (index) {
                window.location.href=res;
                layer.close(index);
            });
        });
    });
})

//底部效果
var a=false;
$(".jiantou").click(function(){
    if(a){
        $(".jiantou img").attr("src","./img/opened.png");   
        a=false;
    }else{
        $(".jiantou img").attr("src","./img/chosed.png");
        a=true;
    }
    $(".full-screen").eq(0).slideToggle();
    $(".derive").eq(0).slideToggle();
})


//全屏
var body=document.getElementsByTagName("html")[0];

var fullScreen=false;
$(".full-screen").click(function(){
    if(fullScreen){
        exitFullscreen();
        fullScreen=false; 
    }else{
        makeFullScreen(document.documentElement)
         fullScreen=true; 
    }
})

function makeFullScreen(divObj) {
    //Use the specification method before using prefixed versions
   if (divObj.requestFullscreen) {
     divObj.requestFullscreen();
   }
   else if (divObj.msRequestFullscreen) {
     divObj.msRequestFullscreen();
   }
   else if (divObj.mozRequestFullScreen) {
     divObj.mozRequestFullScreen();
   }
   else if (divObj.webkitRequestFullscreen) {
     divObj.webkitRequestFullscreen();
   } else {
     var requestMethod = divObj.requestFullScreen || divObj.webkitRequestFullScreen || divObj.mozRequestFullScreen || divObj.msRequestFullScreen || divObj.msRequestFullScreen;
     if (requestMethod) {      
         requestMethod.call(divObj);    
     } else if (typeof window.ActiveXObject !== "undefined") {      
         var wscript = new ActiveXObject("WScript.Shell");    
         if (wscript !== null) {
             wscript.SendKeys("{F11}");    
         }
     } 
   } 
}

//退出全屏
function exitFullscreen() {
    if(document.exitFullscreen) {
     document.exitFullscreen();
    } else if(document.mozCancelFullScreen) {
     document.mozCancelFullScreen();
    } else if(document.webkitExitFullscreen) { 
     document.webkitExitFullscreen();
    }
}
 
 //更新时间和让灯一直闪
 var onewDate=new Date();
 var dateStr='';
setInterval(function(){
    $(".d1").toggle();
    onewDate=new Date();
    var y=onewDate.getFullYear();
    var m=onewDate.getMonth()+1;
    var d=onewDate.getDate();
    dateStr=y+"年"+m+"月"+d+"日";
    var dateTime=new Date().toLocaleTimeString();
    $(".date-con").text(dateStr+"  "+dateTime)
},200)
