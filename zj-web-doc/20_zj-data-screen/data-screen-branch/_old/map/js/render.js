var indexFn = {
    ins: [],
    //渲染底部小圈
    renderBottomArc: function (params, quanIdArr) {
        //小圈dom Id
        quanIdArr = quanIdArr || ['railway', 'municipal', 'highway', 'housing', 'pathway', 'tunnel'];
        for (var i = 0; i < quanIdArr.length; i++) {
            var _id = quanIdArr[i];
            var option = getQuanOptionData({});
            var dom = document.getElementById(_id);
            var edom = echarts.init(dom, 'shine');
            edom.setOption(option);
            this.ins.push(edom);
            getData.getQuanData(edom, _id, '公路', '#35ffff');
        }
    },
    renderConfig: function (data) {
        //渲染配置块 传入数据 {inventory:[{icon:'', title:'',value:''}],equipment:[] }}
        data = data || {};
        $('#config_left_con').html('');
        $('#config_right_con').html('');
        if (data) {
            var inventory = data.inventory;
            var equipment = data.equipment;
            if (inventory) {
                for (var i = 0; i < inventory.length; i++) {
                    var icon = inventory[i].icon;
                    var title = inventory[i].title;
                    var value = inventory[i].value || '-';
                    var link = inventory[i].link;

                    var _dom = '<li link="' + link + '" flag="6" onclick="window.gloFn.configItemClick($(this))">';
                    _dom += icon ? '<img src="' + icon + '" />' : '';
                    _dom += title;
                    _dom += '<span class="num">' + value + '</span>';
                    _dom += '</li>';
                    $('#config_left_con').append(_dom);
                }
            }
            if (equipment) {
                for (var i = 0; i < equipment.length; i++) {
                    var icon = equipment[i].icon;
                    var title = equipment[i].title;
                    var value = equipment[i].value || '-';
                    var link = equipment[i].link;
                    var _dom = '<li link="' + link + '"  flag="7" onclick="window.gloFn.configItemClick($(this))">';
                    _dom += icon ? '<img src="' + icon + '" />' : '';
                    _dom += title;
                    _dom += '<span class="num">' + value + '</span>';
                    _dom += '</li>';
                    $('#config_right_con').append(_dom);
                }

            }

            var dw = $(document).width();
            $('#config .title').css({
                "font-size": dw / 100 * 0.9 + 'px'
            })
            $('#config ul li').css({
                "font-size": dw / 100 * 0.8 + 'px'
            })
        }

    },
    renderBannerCon: function (data, name) {// 
        //渲染滚动轮播的内容 并且让滚动条回顶部
        // var _titRow = data[0];
        $('#Broadcast .con').html('');
        $('#Broadcast .tabTit').html('');
        $('#Broadcast .con').scrollTop(0);
        if (!data) {
            $('#Broadcast .con').html('<div style="text-align:center;color:#bbb;margin:10px 0px;">暂无数据</div>');
            return;
        }


        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            var link = item[3];
            //供应商不让被点击  
            var row = '<div class="row bannerRow" name="' + name + '" link="' + link + '">';
            //注意：第四个是连接
            for (var j = 0; j <= 2; j++) {
                var _item = item[j] || '';
                var text = _item || ''; //_item.text1 || _item.text2 || _item.text3; 
                row += '<div class="col">' + text + '</div>';
            }
            row += '</div>';
            if (i === 0) {
                $('#Broadcast .tabTit').append(row);
            } else {
                $('#Broadcast .con').append(row);
            }
        }

        var dw = $(document).width();
        $('#Broadcast .row').click(function () {
            if ($(this).attr('name') !== 'supplier') { // ‘supplier’ 不可点
                var flag = '';
                switch ($(this).attr('name')) {
                    case 'account':
                        flag = '3'
                        break;
                    case 'signed':
                        flag = '4'
                        break;
                    case 'storage':
                        flag = '5'
                        break;
                } 
                window.gloFn.bannerRowClick($(this).attr('link'), flag)
            }
        })

        $('#Broadcast .row .col').hover(function () {
            window.gloFn.bannerRowHover(0, $(this))
        }, function () {
            window.gloFn.bannerRowHover(1, $(this))
        })
        //轮播
        $('#Broadcast .nav, #Broadcast .row').css({
            "font-size": dw / 100 * 0.9 + 'px'
        })

    },
    //智慧工地
    renderNavCon: function (data) {//传入数据[{icon:, title:'',url:''}]    
        $('#nav .con').html('');
        for (var i = 0; i < data.length; i++) {
            var item = data[i] || {};
            var title = item.title;
            var url = item.url;
            var icon = item.icon;
            var _item = '<div class="item">';
            if (icon) {
                _item += '<img width="30px" src="' + icon + '" />'
            }
            _item += '<span class="text" style="color:' + (item.isOpen ? "" : "#7f7a7a;") + '" onclick="javascript:window.gloFn.navClick(' + item.isOpen + ');">' + title + '</span>';
            _item += '</div>';
            $('#nav .con').append(_item);
        }


        var dw = $(document).width();
        //自适应
        $('#nav .title').css({
            "font-size": dw / 100 * 0.9 + 'px'
        })
        $('#nav .con .item').css({
            "font-size": dw / 100 * 0.8 + 'px'
        })
        $('#nav .con .item img').css({
            "width": dw / 100 * 1.65 + 'px'
        })
    },
    //渲染市的第一块
    renderOneBlank: function (data) {
        var title = '<div class="title">' + data.title + '</div>';
        if (data.data) {
            var _ul = '<ul class="scrollstyle">';
            for (var i = 0; i < data.data.length; i++) {
                var item = data.data[i];
                _ul += '<li onclick="window.gloFn.projectItemClick($(this))" class="clearfix">';
                //标题和状态
                _ul += '<div class="row clearfix">';
                _ul += '<div class="tit"><span class="r_tit">名称：</span><br/>';
                _ul += '<div class="r_con">';
                _ul += item.orgName;
                _ul += '</div>';

                _ul += '</div>';
                _ul += '<div class="status"><span class="r_tit">建设单位：</span><br/>';

                _ul += '<div class="r_con">';
                _ul += item.consultative || '-';
                _ul += '</div>';
                _ul += '</div>';
                _ul += '</div>';
                //类型和合同额
                _ul += '<div class="row clearfix">';

                _ul += '<div><span class="r_tit">工程类型：</span><br/>';
                _ul += '<div class="r_con">';
                _ul += item.projType || '-';
                _ul += '</div>';
                _ul += '</div>';
                _ul += '<div class=""><span class="r_tit">合同额（万元）：</span><br/>';
                _ul += '<div class="r_con">';
                _ul += '<span class="money">' + item.contractCost + '</span>';;
                _ul += '<span class="unit">' + (item.unit || "") + '</span>';
                _ul += '</div>';
                _ul += '</div>';

                _ul += '</div>';
                _ul += '</li>';
            }
            _ul += '</ul>';
            $('#ranking').append(title);
            $('#ranking').append(_ul);

            //调整字体
            var dw = $(document).width();
            $('#ranking li').css({
                "font-size": dw / 100 * 0.9 + 'px'
            })
        }
    },
    //渲染项目地图的第一块
    renderProjectOneBlankData: function (res) {
        var title = '<div class="title">' + res.title + '</div>';
        var data = res.data;
        if (data) {
            var _ul = '<div class="scrollstyle ul">';
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                item.value = item.value || '-';
                _ul += '<div class="clearfix row">';
                _ul += '<img class="icon row_item" src="' + item.icon + '"/>';
                _ul += '<div class="t_left row_item  ' + (item.value.length > 30 ? "black_item_l" : "") + '">';
                _ul += item.label || '-'
                _ul += '</div>';
                _ul += '<div class="t_right row_item  ' + (item.value.length > 30 ? "black_item_r" : "") + '">';
                _ul += item.value || '-'
                _ul += '</div>';
                _ul += '</div>';
            }
            _ul += '</div>';
            $('#ranking').append(title);
            $('#ranking').append(_ul);

            //调整字体
            var dw = $(document).width();
            $('#ranking .row').css({
                "font-size": dw / 100 * 0.9 + 'px'
            })
        }
    },
    //渲染月趋势图
    renderMonthTrendDiagram: function (res) {
        var title = res.title;
        var infoList = res.infoList;
        var status = res.status;
        var number = res.number;
        var unit = res.unit;
        //标题
        var $title = $('<div class="title">' + title + '</div>');

        //内容
        var $con = '<div class="b_con clearfix">';
        $con += '<div class="Broadcast_left">';
        $con += '<div class="number">';
        $con += number + '<span class="unit">' + unit + '</span>';
        $con += '</div>';
        $con += '<div class="status">';
        $con += status;
        $con += '</div>';
        $con += '<img src="../img/aq.png" class="bgImg"/>'
        $con += '</div>';

        //信息列表项
        $con += '<div class="Broadcast_right">';
        for (var i = 0; i < infoList.length; i++) {
            $con += '<div class="infoRow clearfix">';
            $con += '<div class="infoItem"><img src="' + infoList[i].icon + '" alt=""/></div>';
            $con += '<div class="infoItem">' + infoList[i].label + '：</div>';
            $con += '<div class="infoItem">' + (infoList[i].value || '-') + '</div>';
            $con += '</div>';
        }
        $con += '</div>';
        $con += '</div>';

        $('#Broadcast').append($title);
        $('#Broadcast').append($con);

        var dw = $(document).width();
        $('#Broadcast .title').css({
            'font-size': dw / 100 * 1
        })

        $('#Broadcast .b_con').css({
            'font-size': dw / 100 * 0.8
        })
    },
    //渲染标注点的项目列表
    renderMarkerProjectList: function (data) {
        if (data && data.length > 0) {
            var _ul = '<ul class="scrollstyle marker_project_list_ul">';
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                _ul += '<li onclick="window.gloFn.projectClick($(this))" cityName="' + item.cityName + '" orgName="' + item.orgName + '" class="clearfix">';
                //标题和状态
                _ul += '<div class="row clearfix">';
                _ul += '<div class="tit"><span class="r_tit">名称：</span><br/>';
                _ul += '<div class="r_con">';
                _ul += item.orgName;
                _ul += '</div>';

                _ul += '</div>';
                _ul += '<div class="status"><span class="r_tit">建设单位：</span><br/>';

                _ul += '<div class="r_con">';
                _ul += item.consultative || '-';
                _ul += '</div>';
                _ul += '</div>';
                _ul += '</div>';
                //类型和合同额
                _ul += '<div class="row clearfix">';

                _ul += '<div><span class="r_tit">工程类型：</span><br/>';
                _ul += '<div class="r_con">';
                _ul += item.projType || '-';
                _ul += '</div>';
                _ul += '</div>';
                _ul += '<div class=""><span class="r_tit">合同额（万元）：</span><br/>';
                _ul += '<div class="r_con">';
                _ul += '<span class="money">' + item.contractCost + '</span>';;
                _ul += '<span class="unit">' + (item.unit || "") + '</span>';
                _ul += '</div>';
                _ul += '</div>';

                _ul += '</div>';
                _ul += '</li>';
            }
            _ul += '</ul>';

            return _ul;
        } else {
            return '<div style="color:#999;text-align:center;">暂无数据</div>'
        }

    },
    //自适应
    autoResize: function () {
        var ins = this['ins'];
        var _autoResize = function () {
            for (var i = 0; i < ins.length; i++) {
                ins[i].resize();
            }
        }
        //监听地图自适应
        $(window).resize(function (e) {
            _autoResize()
        })
        //首次执行一遍
        _autoResize();
    }
}
window.indexFn = indexFn;