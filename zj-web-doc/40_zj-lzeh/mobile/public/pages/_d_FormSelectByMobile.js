
window._d_FormSelectByMobile = { 
    formConfig: [
         
        {
            "type": "select",
            "field": "select",
            "label": "普通下拉",
            // disabled:true,
            // initialValue:"debz",
            // optionDataGroup: true,
            "optionConfig": {
                "label": "itemName",
                "value": "itemId",
                "children": "children",
            },
            // "fetchConfig": {
            //     "apiName": "getBaseCodeTree",
            //     otherParams: {
            //         // itemId: "xingzhengquhuadaima"
            //         itemId:"gongchengbanzuleibie"
            //     }
            // }
            // "fetchConfig": {
            //     "apiName": "getBaseCodeSelect",
            //     otherParams:{
            //         itemId:"minzhu"
            //     }
            // }
            "optionData": [
                ...Array.from(new Array(20)).map((_, i) => {
                    return {
                        "itemName": "阿里巴巴" + i,
                        "itemId": "alibaba" + i,
                        "ext1": "第一条数据" + i
                    }
                })
            ]
        },
        {
            "type": "select",
            "field": "select_search",
            "label": "下拉可搜索",
            showSearch: true,
            "optionConfig": {
                "label": "itemName",
                "value": "itemId",
                "children": "children",
            },
            "optionData": [
                ...Array.from(new Array(20)).map((_, i) => {
                    return {
                        "itemName": "阿里巴巴" + i,
                        "itemId": "alibaba" + i,
                        "ext1": "第一条数据" + i
                    }
                })
            ]
        },
        {
            "type": "select",
            "field": "select_group",
            optionDataGroup: true,
            showSearch: true,
            "label": "普通分组下拉",
            "optionConfig": {
                "label": "itemName",
                "value": "itemId",
                "children": "children",
            },
            "optionData": [
                ...Array.from(new Array(5)).map((_, i) => {
                    return {
                        "itemName": "阿里巴巴" + i,
                        "itemId": "alibaba" + i,
                        "children": [
                            ...Array.from(new Array(5)).map((_, _i) => {
                                return {
                                    "itemName": i + "_支付宝" + _i,
                                    "itemId": i + "_zhifubao" + _i
                                }
                            })
                        ]
                    }
                })
            ]
        },

        {
            "type": "select",
            "field": "selectByMultiple",
            "label": "多选分组下拉",
            "showSearch": true,
            "multiple": true, 
            "optionConfig": {
                "label": "itemName",
                "value": "itemId",
                "children": "children",
            }, 
            optionDataGroup: true,
            // "fetchConfig": {
            //     "apiName": "getBaseCodeTree",
            //     otherParams: {
            //         itemId: "xingzhengquhuadaima"
            //         // itemId:"gongchengbanzuleibie"
            //     }
            // }

            "optionData": [
                ...Array.from(new Array(5)).map((_, i) => {
                    return {
                        "itemName": "阿里巴巴" + i,
                        "itemId": "alibaba" + i,
                        "children": [
                            ...Array.from(new Array(5)).map((_, _i) => {
                                return {
                                    "itemName": i + "_支付宝" + _i,
                                    "itemId": i + "_zhifubao" + _i
                                }
                            })
                        ]
                    }
                })
            ]
        },
        

    ],
    btns: [
        {
            field: "diy",
            label: "设置值",
            type: 'primary',
            icon: "testIcon",
            onClick: function (obj) {
                obj.btnCallbackFn.setValues({  
                })
            }
        },
        {
            field: "diy",
            label: "获取值",
            type: 'primary',
            icon: "testIcon",
            onClick: function (obj) { //此时里面会多一个 response
                obj.btnCallbackFn.getValues(true, function (vals) {
                    console.log(vals)
                })
            }
        },

    ]
};


