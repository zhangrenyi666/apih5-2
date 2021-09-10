$.support.cors = true;
var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器 
window.w = {
    // domain: 'http://weixin.fheb.cn:99/apiwoa/',  
    domain: window.config.domain[window.config.model] ,//'http://192.168.1.107:8081/web/', //'http://192.168.1.107:8081/web/',
    index:window.config.provinceUrl[window.config.model], //'/index.html', //全国地图地址  http://weixin.fheb.cn:99/data-screen/index.html
    getApiUrl: function (apiName) {
        apiName = apiName || "";
        // if (!this.apiNames[apiName]) {
        //     alert(apiName + "尚未在mainjs中定义")
        // }
        // return this.domain + this.apiNames[apiName]
        return this.domain + apiName
    },
    ajax: function (apiName, params, success) {
        var params = params || {}; 
        $.support.cors = true;
        $.ajax({
            url: this.getApiUrl(apiName),
            type: 'post',
            dataType: 'json',
            data: params ? JSON.stringify(params) : '',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            },
            error: function (result, status, error) {//服务器响应失败处理函数 
                // alert('错误:' + status);
                // console.log(error.code)
                // console.log(result)
            },
            success: function (result) {
                success(result.data, result.message, result.success);
            }
        })
    },
    get:function(apiName, params, success){
        var params = params; 
        $.support.cors = true;
        $.ajax({
            url: this.getApiUrl(apiName),
            type: 'get', 
            data: params, 
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            },
            error: function (result, status, error) {//服务器响应失败处理函数 
                alert('错误:' + status); 
            },
            success: function (result) {
                success(result);
            }
        })
    },
    //判断当前浏览类型  
    //判断是否是IE浏览器   不是ie返回"-1"
    isIE: function () {
        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
        if (isIE) {
            return "1";
        }
        else {
            return "-1";
        }
    },
    getUrlParam: function (k, windowObj) {//获取地址栏参数，k未键名
        var windowObj = windowObj || window
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = windowObj.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    },
    mapData:[
        { citys: false, province: '北京市' }
        , { citys: false, province: '天津市' }
        , { citys: false, province: '上海市' }
        , { citys: false, province: '重庆市' }
        , { citys: false, province: '香港特别行政区' }
        , { citys: false, province: '澳门特别行政区' }
        , { citys: ['石家庄', '唐山', '邯郸', '秦皇岛', '保定', '张家口', '承德', '廊坊', '沧州', '衡水', '邢台'], province: '河北' }
        , { citys: ['郑州', '洛阳', '开封', '漯河', '安阳', '新乡', '周口', '三门峡', '焦作', '平顶山', '信阳', '南阳', '鹤壁', '濮阳', '许昌', '商丘', '驻马店'], province: '河南' }
        , { citys: ['昆明', '曲靖', '玉溪', '保山', '昭通', '丽江', '普洱', '临沧'], province: '云南省' }
        , { citys: ['沈阳', '大连', '鞍山', '抚顺', '本溪', '丹东', '锦州', '营口', '阜新', '辽阳', '盘锦', '铁岭', '朝阳', '葫芦岛'], province: '辽宁' }
        , { citys: ['哈尔滨', '大庆', '齐齐哈尔', '佳木斯', '鸡西', '鹤岗', '双鸭山', '牡丹江', '伊春', '七台河', '黑河', '绥化'], province: '黑龙江' }
        , { citys: ['长沙', '株洲', '湘潭', '衡阳', '岳阳', '郴州', '永州', '邵阳', '怀化', '常德', '益阳', '张家界', '娄底'], province: '湖南' }
        , { citys: ['合肥', '蚌埠', '芜湖', '淮南', '亳州', '阜阳', '淮北', '宿州', '滁州', '安庆', '巢湖', '马鞍山', '宣城', '黄山', '池州', '铜陵'], province: '安徽' }
        , { citys: ['济南', '青岛', '淄博', '枣庄', '东营', '烟台', '潍坊', '济宁', '泰安', '威海', '日照', '莱芜', '临沂', '德州', '聊城', '菏泽', '滨州'], province: '山东' }
        , { citys: ['乌鲁木齐市', '克拉玛依市', '吐鲁番市', '哈密市'], province: '新疆' }
        , { citys: ['南京', '镇江', '常州', '无锡', '苏州', '徐州', '连云港', '淮安', '盐城', '扬州', '泰州', '南通', '宿迁'], province: '江苏' }
        , { citys: ['杭州', '嘉兴', '湖州', '宁波', '金华', '温州', '丽水', '绍兴', '衢州', '舟山', '台州'], province: '浙江' }
        , { citys: ['南昌', '九江', '赣州', '吉安', '鹰潭', '上饶', '萍乡', '景德镇', '新余', '宜春', '抚州'], province: '江西' }
        , { citys: ['武汉', '襄樊', '宜昌', '黄石', '鄂州', '随州', '荆州', '荆门', '十堰', '孝感', '黄冈', '咸宁'], province: '湖北' }
        , { citys: ['南宁', '柳州', '桂林', '梧州', '北海', '崇左', '来宾', '贺州', '玉林', '百色', '河池', '钦州', '防城港', '贵港'], province: '广西' }
        , { citys: ['兰州', '天水', '平凉', '酒泉', '嘉峪关', '金昌', '白银', '武威', '张掖', '庆阳', '定西', '陇南'], province: '甘肃' }
        , { citys: ['太原', '大同', '忻州', '阳泉', '长治', '晋城', '朔州', '晋中', '运城', '临汾', '吕梁'], province: '山西省' }
        , { citys: ['呼和浩特', '包头', '乌海', '赤峰', '通辽', '鄂尔多斯', '呼伦贝尔', '巴彦淖尔', '乌兰察布'], province: '内蒙古' }
        , { citys: ['西安', '咸阳', '铜川', '延安', '宝鸡', '渭南', '汉中', '安康', '商洛', '榆林'], province: '陕西' }
        , { citys: ['长春', '吉林', '四平', '辽源', '通化', '白山', '松原', '白城'], province: '吉林' }
        , { citys: ['福州', '厦门', '泉州', '三明', '南平', '漳州', '莆田', '宁德', '龙岩'], province: '福建' }
        , { citys: ['贵阳', '六盘水', '遵义', '安顺'], province: '贵州' }
        , { citys: ['广州', '深圳', '汕头', '惠州', '珠海', '揭阳', '佛山', '河源', '阳江', '茂名', '湛江', '梅州', '肇庆', '韶关', '潮州', '东莞', '中山', '清远', '江门', '汕尾', '云浮'], province: '广东' }
        , { citys: ['西宁'], province: '青海' }
        , { citys: ['拉萨市', '日喀则市', '昌都市', '林芝市', '山南市', '那曲市'], province: '西藏' }
        , { citys: ['成都', '绵阳', '德阳', '广元', '自贡', '攀枝花', '乐山', '南充', '内江', '遂宁', '广安', '泸州', '达州', '眉山', '宜宾', '雅安', '资阳'], province: '四川' }
        , { citys: ['银川', '石嘴山', '吴忠', '固原', '中卫'], province: '宁夏' }
        , { citys: ['海口市', '三亚市', '三沙市', '儋州市'], province: '海南' }
        , { citys: ['台北', '台中', '基隆', '高雄', '台南', '新竹', '嘉义'], province: '台湾' }
    ]
}
