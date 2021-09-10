window.zjPrEvaluationInfoPage = {//考评表
    fetchConfig: {
        apiName: "getPrEvaluationInfoStatisticsList",
    },
    antd: {
        rowKey: function (row) {
            //---row.主键id
            return row.evaluationId;
        },
        size: 'small',
        scroll:{
            y:window.innerHeight * 0.5
        }
    },
    //没有分页的table才能在使用增加行的操作
    paginationConfig: false,
    isShowRowSelect: false,
    //不设置分页后需要将pageSize手动调大些
    // pageSize: 9999,
    //操作按钮的位置  top | bottom  [string]  默认top
    // actionBtnsPosition: "bottom",
    actionBtns: [
        {
            name: 'export',//内置add del
            icon: 'export',//icon
            type: 'primary',//类型  默认 primary  [primary dashed danger]
            label: '导出',
            onClick: (obj) => {
                console.log(obj);
                console.log(obj.searchData);
                if (obj.searchData.modifyTimeArr) {
                    window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:方案考评表.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/&startDate=" + obj.searchData.modifyTimeArr[0]+"&endDate=" + obj.searchData.modifyTimeArr[1];
                } else {
                    window.location.href = "http://weixin.fheb.cn:91/ureport/excel?_u=file:方案考评表.ureport.xml&url=http://weixin.fheb.cn:98/apifangan/&evaluationQuarter=";
                }
            }
        },
    ],
    formConfig: [
        {
            table: {
                title: '季度',
                dataIndex: 'projectGeneralUser',
                key: 'projectGeneralUser'
            },
            // isInTable: false,
            isInTable: false,
            isInSearch: true,
            form: {
                label: '考核时间',
                field: 'modifyTimeArr', //唯一的字段名 ***必传
                spanSearch: 12,
                type: 'rangeDate',
                showTime: true,
                placeholder: '请选择',
                formItemLayoutSearch: {
                    //默认数据
                    labelCol: {
                        sm: { span: 4 }
                    },
                    wrapperCol: {
                        sm: { span: 20 }
                    }
                },
            }
        },
        // {
        //     table: {
        //         title: '季度',
        //         dataIndex: 'projectGeneralUser',
        //         key: 'projectGeneralUser',
        //     },
        //     isInTable: false,
        //     isInSearch: true,
        //     form: {
        //         type: 'select',
        //         label: '季度',
        //         field: 'evaluationQuarter', //唯一的字段名 ***必传
        //         placeholder: '请选择',
        //         required: true,
        //         multiple: false, //是否开启多选功能 开启后自动开启搜索功能
        //         showSearch: false, //是否开启搜索功能 (移动端不建议开启)
        //         optionData: [//默认选项数据//可为function (props)=>array
        //             {
        //                 value: '1',
        //                 text: '第一季度'
        //             },
        //             {
        //                 value: '2',
        //                 text: '第二季度'
        //             },
        //             {
        //                 value: '3',
        //                 text: '第三季度'
        //             },
        //             {
        //                 value: '4',
        //                 text: '第四季度'
        //             },
        //         ],
        //         optionConfig: {//下拉选项配置
        //             label: 'text', //默认 label
        //             value: 'value',// [array | string]  最终的值使用逗号连接 默认值使用valueName 默认['value']
        //         },
        //     }
        // },
        {
            table: {
                title: "考评分项",
                dataIndex: "1",
                key: "1",
                fixed: 'left',
                align: "center",
                width: 150,
                children: [
                    {
                        width: 150,
                        title: '公司',
                        dataIndex: 'unitName',
                        key: 'unitName'
                    },
                ],
            },
            isInForm: false
        },
        {
            table: {
                width: 560,
                title: "方案清单（5）",
                // dataIndex: "departmentName",
                // key: "departmentName",
                children: [
                    // {
                    //     width: 110,
                    //     title: '在建项目总数',
                    //     dataIndex: 'projectNum',
                    //     key: 'projectNum'
                    // },
                    {
                        width: 120,
                        title: '在建项目总数',
                        dataIndex: 'projectNum',
                        key: 'projectNum',
                        align: "center",
                        // tdEdit: true,
                        fieldsConfig: {
                            type: "string",
                            placeholder: "请输入..."
                        },
                        // className:'colSpans',
                        // tdEditFetchConfig: {
                        //     apiName: "updateZjPrEvaluationInfo",
                        //     params: {}, //可为func
                        //     otherParams: {} //可为func
                        // }
                    },
                    {
                        width: 120,
                        title: '上报清单份数',
                        dataIndex: 'reportListNum',
                        key: 'reportListNum',
                        align: "center",
                        // tdEdit: true,
                        // className:'colSpans',
                        fieldsConfig: {
                            type: "string",
                            placeholder: "请输入..."
                        },
                        // tdEditFetchConfig: {
                        //     apiName: "updateZjPrEvaluationInfo",
                        //     params: {}, //可为func
                        //     otherParams: {} //可为func
                        // }
                    },
                    // {
                    //     width: 110,
                    //     title: '上报清单份数',
                    //     dataIndex: 'reportListNum',
                    //     key: 'reportListNum'
                    // },
                    {
                        width: 120,
                        title: '上报率',
                        align: "center",
                        dataIndex: 'reportRate',
                        key: 'reportRate'
                    },
                    {
                        width: 100,
                        title: '得分',
                        align: "center",
                        dataIndex: 'listScore',
                        key: 'listScore'
                    },
                    {
                        width: 100,
                        title: '排名',
                        align: "center",
                        dataIndex: 'listRanking',
                        key: 'listRanking'
                    },
                ],
            },
            isInForm: false
        },
        {
            table: {
                width: 520,
                // title: "数量均值（10）",
                 title: "方案数量（10）",
                // dataIndex: "departmentName",
                // key: "departmentName",
                children: [
                    {
                        width: 120,
                        title: '清单方案总数量',
                        align: "center",
                        dataIndex: 'programmeTotalNum',
                        key: 'programmeTotalNum'
                    },
                    {
                        width: 200,
                        title: '每个项目III级及以上方案数量平均数量',
                        align: "center",
                        dataIndex: 'proAverageValue',
                        key: 'proAverageValue'
                    },
                    {
                        width: 100,
                        title: '得分',
                        align: "center",
                        dataIndex: 'numScore',
                        key: 'numScore'
                    },
                    {
                        width: 100,
                        title: '排名',
                        align: "center",
                        dataIndex: 'numRanking',
                        key: 'numRanking'
                    },
                ],
            },
            isInForm: false
        },
        // {
        //     table: {
        //         width: 390,
        //         title: "方案评审率（10）",
        //         dataIndex: "departmentName",
        //         key: "departmentName",
        //         children: [
        //             {
        //                 width: 120,
        //                 title: '已评审通过方案数量',
        //                 align: "center",
        //                 dataIndex: 'adoptProgrammeNum',
        //                 key: 'adoptProgrammeNum'
        //             },
        //             {
        //                 width: 120,
        //                 title: '应评审数量',
        //                 align: "center",
        //                 dataIndex: 'shouldProgrammeNum',
        //                 key: 'shouldProgrammeNum'
        //             },
        //             {
        //                 width: 50,
        //                 title: '占比',
        //                 align: "center",
        //                 dataIndex: 'proportion',
        //                 key: 'proportion'
        //             },
        //             {
        //                 width: 50,
        //                 title: '得分',
        //                 align: "center",
        //                 dataIndex: 'reviewRateScore',
        //                 key: 'reviewRateScore'
        //             },
        //             {
        //                 width: 50,
        //                 title: '排名',
        //                 align: "center",
        //                 dataIndex: 'reviewRateRanking',
        //                 key: 'reviewRateRanking'
        //             },
        //         ],
        //     },
        //     isInForm: false
        // },
        // {
        //     table: {
        //         width: 230,
        //         title: "会议评审次数（10）",
        //         // dataIndex: "departmentName",
        //         // key: "departmentName",
        //         children: [
        //             {
        //                 width: 130,
        //                 title: '会议评审次数',
        //                 dataIndex: 'meetingReviewSecond',
        //                 align: "center",
        //                 key: 'meetingReviewSecond',
        //                 tdEdit: true,
        //                 fieldsConfig: {
        //                     type: "string",
        //                     placeholder: "请输入..."
        //                 },
        //                 tdEditFetchConfig: {
        //                     apiName: "updateZjPrEvaluationInfo",
        //                     params: {}, //可为func
        //                     otherParams: {} //可为func
        //                 }
        //             },
        //             {
        //                 width: 50,
        //                 title: '得分',
        //                 align: "center",
        //                 dataIndex: 'meetingReviewScore',
        //                 key: 'meetingReviewScore'
        //             },
        //             {
        //                 width: 50,
        //                 title: '排名',
        //                 align: "center",
        //                 dataIndex: 'meetingReviewRanking',
        //                 key: 'meetingReviewRanking'
        //             },
        //         ],
        //     },
        //     isInForm: false
        // },
        {
            table: {
                width: 250,
                title: "方案执行（10）",
                // dataIndex: "departmentName",
                // key: "departmentName",
                children: [
                    {
                        width: 150,
                        title: '得分',
                        align: "center",
                        dataIndex: 'selectionScore',
                        key: 'selectionScore',
                        tdEdit: true,
                        fieldsConfig: {
                            type: "string",
                            placeholder: "请输入..."
                        },
                        tdEditFetchConfig: {
                            apiName: "updateZjPrEvaluationInfo",
                            params: {}, //可为func
                            otherParams: {} //可为func
                        }
                    },
                    // {
                    //     // width: 90,
                    //     title: '得分',
                    //     dataIndex: 'selectionScore',
                    //     key: 'selectionScore'
                    // },
                    {
                        width: 100,
                        title: '排名',
                        align: "center",
                        dataIndex: 'selectionRanking',
                        key: 'selectionRanking'
                    },
                ],
            },
            isInForm: false
        },
        {
            table: {
                width: 240,
                title: "编制质量得分（75）",
                // dataIndex: "departmentName",
                // key: "departmentName",
                children: [
                    {
                        width: 120,
                        title: '得分',
                        align: "center",
                        dataIndex: 'qualityScore',
                        key: 'qualityScore'
                    },
                    {
                        width: 120,
                        title: '排名',
                        align: "center",
                        dataIndex: 'qualityRanking',
                        key: 'qualityRanking'
                    },
                ],
            },
            isInForm: false
        },
        {
            table: {
                width: 100,
                fixed: "right",
                title: "总分",
                align: "center",
                dataIndex: "companyTotalScore",
                key: "companyTotalScore",
            },
            isInForm: false
        },
        {
            table: {
                // width: 180,
                fixed: "right",
                width: 100,
                title: "排名",
                align: "center",
                dataIndex: "companyRanking",
                key: "companyRanking",
            },
            isInForm: false
        },
    ]
}