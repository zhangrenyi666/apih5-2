import React, { Component } from "react";
import QnnTable from "../modules/qnn-table";
import s from "./style.less";

const config = {
    fetchConfig: {
        apiName: 'getZjTzDesignChangeStatisticsList'
    },
    antd: {
        rowKey: 'designChangeStatisticsId',
        size: 'small',
        scroll:{
            y:document.documentElement.clientHeight*0.6
        }
    },
    drawerConfig: {
        width: '1100px'
    },
    // limit: 999999,
    // curPage: 1,
    paginationConfig: {
        position: 'bottom'
    },
    // paginationConfig: false,
    firstRowIsSearch: false,
    isShowRowSelect: false
};
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }
    componentDidMount() {}
    render() {
        let { myPublic: { domain,appInfo: { ureport } } } = this.props;
        return (
            <div className={s.root}>
                <QnnTable
                    {...this.props}
                    fetch={this.props.myFetch} 
		            upload={this.props.myUpload}
                    headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => {
                        this.table = me;
                    }}
                    {...config}
                    formConfig={[
                        {
                            isInTable: false,
                            form: {
                                field: 'designChangeStatisticsId',
                                type: 'string',
                                hide:true,
                            }
                        },
                        {
                            table:{
                                title: "项目名称",
                                children: [
                                    {
                                        dataIndex: 'projectName',
                                        title: "项目包名称",
                                        fixed:'left',
                                        width: 180,
                                        tooltip:23
                                    },
                                    {
                                        title: '子项目名称',
                                        width: 180,
                                        fixed:'left',
                                        tooltip:23,
                                        filter:true,
                                        dataIndex: 'subprojectName'
                                    }
                                ],
                            },
                            isInForm: false
                        },
                        {
                            table:{
                                title: "A类设计变更（行业重大或超过500万）",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'extA1',
                                        title: "主动变更数量",
                                        width: 110
                                    },
                                    {
                                        title: '主动变更总金额（万元）',
                                        dataIndex: 'extA2',
                                        width: 130
                                    },
                                    {
                                        title: '被动变更数量',
                                        dataIndex: 'extA3',
                                        width: 110
                                    },
                                    {
                                        title: '被动变更总金额（万元）',
                                        dataIndex: 'extA4',
                                        width: 130
                                    },
                                    {
                                        title: 'A类总数量',
                                        dataIndex: 'extA5',
                                        width: 100
                                    },
                                    {
                                        title: 'A类设计变更总金额（万元）',
                                        dataIndex: 'extA6',
                                        width: 150
                                    },
                                    {
                                        title: '已完成内部审查流程数量',
                                        dataIndex: 'extA7',
                                        width: 130
                                    },
                                    {
                                        title: '已完成合法合规流程设计变更数量',
                                        dataIndex: 'extA8',
                                        width: 150
                                    }
                                ]
                            },
                            isInForm:false
                        },
                        {
                            table:{
                                title: "B类设计变更数量（行业较大或200~500万）",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'extB1',
                                        title: "主动变更数量",
                                        width: 120
                                    },
                                    {
                                        title: '主动变更总金额（万元）',
                                        dataIndex: 'extB2',
                                        width: 130
                                    },
                                    {
                                        title: '被动变更数量',
                                        width:130,
                                        dataIndex: 'extB3'
                                    },
                                    {
                                        title: '被动变更总金额（万元）',
                                        width:130,
                                        dataIndex: 'extB4'
                                    },
                                    {
                                        title: 'B类总数量',
                                        width:100,
                                        dataIndex: 'extB5'
                                    },
                                    {
                                        title: 'B类设计变更总金额（万元）',
                                        width:150,
                                        dataIndex: 'extB6'
                                    },
                                    {
                                        title: '已完成内部审查流程数量',
                                        dataIndex: 'extB7',
                                        width:150
                                    },
                                    {
                                        title: '已完成合法合规流程设计变更数量',
                                        width:150,
                                        dataIndex: 'extB8'
                                    }
                                ]
                            },
                            isInForm:false
                        },
                        {
                            table:{
                                title: "C1类设计变更（80~200万主动）",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'extC11',
                                        title: "设计变更数量",
                                        width:100
                                    },
                                    {
                                        title: '设计变更金额（万元）',
                                        width:120,
                                        dataIndex: 'extC12'
                                    },
                                    {
                                        title: '动态设计管控的设计变更数量',
                                        width:130,
                                        dataIndex: 'extC13'
                                    },
                                    {
                                        title: '动态设计管控的设计变更金额绝对值（万元）',
                                        width:160,
                                        dataIndex: 'extC14'
                                    },
                                    {
                                        title: '已完成内部审查流程数量',
                                        width:150,
                                        dataIndex: 'extC15'
                                    },
                                    {
                                        title: '已完成合法合规流程设计变更数量',
                                        width:160,
                                        dataIndex: 'extC16'
                                    }
                                ]
                            },
                            isInForm:false
                        },
                        {
                            table:{
                                title: "C2类设计变更（80~200万被动）",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'extC21',
                                        title: "设计变更数量",
                                        width: 110
                                    },
                                    {
                                        title: '设计变更金额（万元）',
                                        dataIndex: 'extC22',
                                        width:120
                                    },
                                    {
                                        title: '已完成内部审查流程数量',
                                        dataIndex: 'extC23',
                                        width:150
                                    },
                                    {
                                        title: '已完成合法合规流程设计变更数量',
                                        width:160,
                                        dataIndex: 'extC24'
                                    }
                                ]
                            },
                            isInForm:false
                        },
                        {
                            table:{
                                title: "D1类设计变更（30~80万主动）",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'extD11',
                                        title: "设计变更数量",
                                        width:120
                                    },
                                    {
                                        title: '设计变更金额（万元）',
                                        width:120,
                                        dataIndex: 'extD12'
                                    },
                                    {
                                        title: '动态设计管控的设计变更数量',
                                        width:160,
                                        dataIndex: 'extD13'
                                    },
                                    {
                                        title: '动态设计管控的设计变更金额绝对值（万元）',
                                        width:180,
                                        dataIndex: 'extD14'
                                    },
                                    {
                                        title: '已完成内部审查流程数量',
                                        width:150,
                                        dataIndex: 'extD15'
                                    },
                                    {
                                        title: '已完成合法合规流程设计变更数量',
                                        width:160,
                                        dataIndex: 'extD16'
                                    }
                                ]
                            },
                            isInForm:false
                        },
                        {
                            table:{
                                title: "D2类设计变更（30~80万被动）",
                                noHaveSearchInput: true,
                                children: [
                                    {
                                        dataIndex: 'extD21',
                                        width:110,
                                        title: "设计变更数量"
                                    },
                                    {
                                        title: '设计变更金额（万元）',
                                        width:120,
                                        dataIndex: 'extD22'
                                    },
                                    {
                                        title: '已完成合法合规流程设计变更数量',
                                        width:140,
                                        dataIndex: 'extD23'
                                    }
                                ]
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '主动变更总数量',
                                width:130,
                                dataIndex: 'extAll1'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '<div>主动变更总金额<br>（万元）</div>',
                                width:130,
                                dataIndex: 'extAll2'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '被动变更总数量',
                                width:130,
                                dataIndex: 'extAll3'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '<div>被动变更总金额<br>（万元）</div>',
                                width:130,
                                dataIndex: 'extAll4'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '动态设计管控的设计变更总数量',
                                width:220,
                                dataIndex: 'extAll5'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '<div>动态设计管控的设计变更总金额的绝对值<br>（万元）</div>',
                                width:280,
                                dataIndex: 'extAll6'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '累计设计变更数量',
                                width:130,
                                dataIndex: 'extAll7'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '<div>累计设计变更金额<br>（万元）</div>',
                                width:130,
                                dataIndex: 'extAll8'
                            },
                            isInForm:false
                        },
                        {
                            table: {
                                title: '备注',
                                width: 130,
                                tooltip:23,
                                dataIndex: 'remarks'
                            },
                            isInForm:false
                        },
                    ]}
                    method={{
                        exportClick: (obj) => {
                            window.open(ureport+'excel?_u=file:zjTzDesignChangeStatisticsList.xml&url='+domain+'&delFlag=0');
                        }
                    }}
                    actionBtns={{
                        apiName: "getSysMenuBtn",
                        otherParams: function (obj) {
                            var props = obj.Pprops;
                            let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                            return {
                                menuParentId: curRouteData._value,
                                tableField: "projectInfo"
                            }
                        }
                    }}
                />
            </div>
        );
    }
}

export default index;