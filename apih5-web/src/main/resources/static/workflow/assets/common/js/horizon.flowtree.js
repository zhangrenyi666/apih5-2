/*
  流程树
  @author zhouwf
*/
(function (root, factory) {
    if(typeof define === 'function' && define.amd) {
        define('horizonFlowtree', ['jquery', 'fueluxTree'], factory);
    }else if (typeof exports === 'object') {
        factory(require('jquery'), require( 'fueluxTree' ));
    }else {
        factory(root.jQuery);
    }
}(this, function($) {
    "use strict";
    var flowtree = {
        sourceData: null,
        setSourceData: function(data, option) {
            if(option.defaultNode && $.isArray(option.defaultNode)) {
                if(option.defaultNodePosition == 'top') {
                    data = $.merge($.merge([], option.defaultNode), data);
                }else {
                    $.merge(data, option.defaultNode);
                }
            }
            if(option.openFirstFolder) {
                for(var i = 0, iLen = data.length; i < iLen; i++) {
                    if(data[i].type == 'folder') {
                        data[i]['additionalParameters']['item-selected'] = true;
                        break;
                    }
                }
            }
            flowtree.sourceData = data;
        },
        init: function($ele, option) {
            $ele.addClass('tree').attr('role', 'tree');
            $ele.html('<li class="tree-branch hide" data-template="treebranch" role="treeitem" aria-expanded="false">\
                        <div class="tree-branch-header">\
                            <span class="tree-branch-name">\
                                <i class="icon-folder '+option['closeIcon']+'"></i>\
						<span class="tree-label"></span>\
					</span>\
				</div>\
				<ul class="tree-branch-children" role="group"></ul>\
				<div class="tree-loader" role="alert">'+option['loadingHTML']+'</div>\
			</div>\
			<li class="tree-item hide" data-template="treeitem" role="treeitem">\
				<span class="tree-item-name">\
				  '+(option['unselectedIcon'] == null ? '' : '<i class="icon-item '+option['unselectedIcon']+'"></i>')+'\
				  <span class="tree-label"></span>\
				</span>\
			</li>');
            $ele.addClass($ele['selectable'] == true ? 'tree-selectable' : 'tree-unselectable');

            if(typeof option.localData == 'object' && option.localData != null) {
                flowtree.setSourceData(option.localData, option);
                flowtree.makeTree($ele, option);
            }else {
                $.ajax({
                    url: option.url,
                    data: option.data,
                    dataType: 'json',
                    cache: false,
                    async:false,
                    success: function(data) {
                        flowtree.setSourceData(data, option);
                        flowtree.makeTree($ele, option);
                    }
                });
            }
        },
        makeTree: function($ele, option) {
            $ele.tree({
                dataSource: function(options, callback) {
                    var $data = null;
                    if(!('text' in options) && !('type' in options)){
                        $data = flowtree.sourceData;//the root tree
                    }else if('type' in options && options.type == 'folder') {
                        if('additionalParameters' in options && 'children' in options.additionalParameters)
                            $data = options.additionalParameters.children;
                        else $data = {};//no data
                    }
                    if($data != null)
                        callback({ data: $data });
                },
                multiSelect: option.multiSelect,
                cacheItems: option.cacheItems,
                'open-icon' : option.openIcon,
                'close-icon' : option.closeIcon,
                'selectable' : option.selectable,
                folderSelect : option.folderSelect,
                'selected-icon' : option.selectedIcon,
                'unselected-icon' : option.unselectedIcon,
                loadingHTML : option.loadingHTML
            })
                .on('loaded.fu.tree', function(e) {
                    if(typeof option.loaded === 'function')
                        option.loaded.apply(this, arguments);
                })
                .on('updated.fu.tree', function(e, result) {
                    if(typeof option.updated === 'function')
                        option.updated.apply(this, arguments);
                })
                .on('selected.fu.tree', function(e) {
                    if(typeof option.selected === 'function')
                        option.selected.apply(this, arguments);
                })
                .on('deselected.fu.tree', function(e) {
                    if(typeof option.deselected === 'function')
                        option.deselected.apply(this, arguments);
                })
                .on('opened.fu.tree', function(e) {
                    if(typeof option.opened === 'function')
                        option.opened.apply(this, arguments);
                })
                .on('closed.fu.tree', function(e) {
                    if(typeof option.closed === 'function')
                        option.closed.apply(this, arguments);
                });
            $ele.data('flowtree_option', option);
        },
        reload: function($ele) {
            $ele.find("li").remove();
            $ele.unbind();
            $ele.data( 'fu.tree', null);
            flowtree.init($ele, $ele.data('flowtree_option'));
        },
        destroy: function($ele) {
            return $ele.tree('destroy');
        },
        selectedItems: function($ele) {
            return $ele.tree('selectedItems');
        }
    };

    $.fn.flowtree = function (option) {
        var $this = $(this);
        if(typeof option === 'string') {
            return flowtree[option].apply(this, [$this]);
        }
        option = option ? $.extend({}, defaults, option) : defaults;
        flowtree.init($this, option);
        return this;
    };

    var defaults = {
        url: horizon.tools.formatUrl('/manager/flowtree/list'),//获取流程数据的请求路径
        data: null,//请求参数
        localData: null, //本地数据
        defaultNode: null,//默认节点
        defaultNodePosition: 'bottom',//默认节点位置 top|bottom
        openFirstFolder: false, //是否默认打开第一个文件夹

        //以下为ace_tree参数
        multiSelect: false,
        cacheItems: true,
        openIcon : 'ace-icon tree-minus',
        closeIcon : 'ace-icon tree-plus',
        selectable : true,
        folderSelect : true,
        selectedIcon : null,//'ace-icon fa fa-check',
        unselectedIcon : null,//'ace-icon fa fa-times',
        loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',

        //以下为回掉函数
        loaded: null,
        updated: null,
        selected: null,
        deselected: null,
        opened: null,
        closed: null
    };
}));

