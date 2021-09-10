//分包清单与业主合同清单的关联

import React, { useCallback, useRef, useState, useMemo, useEffect } from "react"
import style from "./FenBaoYuYeZhu.less"
import QnnTable from "qnn-table"
import { msg } from "qnn-apih5"
import { LoadingOutlined } from '@ant-design/icons';
import { Radio, message as antdMsg } from 'antd';

export default (props) => {

    //跟列表ref
    const rootTable = props.getRootTable();
    const myFetch = useCallback(rootTable.fetch, [])

    //表单主键
    const rootId = rootTable.qnnForm?.form?.getFieldValue?.("ctContractId") || props.clickCb?.rowData?.["ctContractId"];
    const firstId = rootTable.qnnForm?.form?.getFieldValue?.("firstId") || props.clickCb?.rowData?.["firstId"];
    const contractNo = rootTable.qnnForm?.form?.getFieldValue?.("contractNo") || props.clickCb?.rowData?.["contractNo"];
    const contractName = rootTable.qnnForm?.form?.getFieldValue?.("contractName") || props.clickCb?.rowData?.["contractName"];
    const loginInfo = props.loginInfo || {};
    const { userInfo = {} } = loginInfo;
    const { curCompany = {} } = userInfo;
    const { companyId, companyName, departmentId, departmentName } = curCompany;

    // companyName
    //当前环境
    const curEnv = props.clickCb?.rowInfo?.name;

    //左右区域宽带设置线
    const mouseDownLine = useRef();
    //左侧列表ref
    const leftTableRef = useRef();
    //右侧列表ref
    const rightTableRef = useRef();
    //左侧选择的列表数据 
    const [selectLeftTableData, setSelectLeftTableData] = useState([]);
    // 解决闭包导致的读取不到状态
    const selectLeftTableDataRef = useRef([]);
    //延时渲染，防止卡顿
    const [canRender, setCanRender] = useState(false);
    useEffect(() => {
        setTimeout(() => {
            setCanRender(true);
        }, 300)
    }, [])

    //右侧选择的列表数据 
    // const [selectRightTableData, setSelectRightTableData] = useState([]);
    //右侧表格选择请求loading
    const [rightTableLoading, setRightTableLoading] = useState(false);

    // 左右区域width改变方法
    const hrlineOnMouseDown = useCallback((event) => {
        event.stopPropagation();
        mouseDownLine.current = true;
        document.onselectstart = () => false; //取消字段选择功能
    }, [mouseDownLine.current])

    // 左右区域width改变方法
    const hrlineOnMouseUp = useCallback((event) => {
        event.stopPropagation();
        mouseDownLine.current = false;
        document.onselectstart = null
    }, [mouseDownLine.current])

    // 左右区域width改变方法
    const hrlineOnMouseMove = useCallback((event) => {
        if (mouseDownLine.current) {
            event.stopPropagation();
            const leftW = event.clientX;
            const rightW = window.innerWidth - leftW - 18; //18为右侧滚动条宽度 
            document.querySelector(`.${style.treeContainer}`).style.width = `${leftW}px`;
            document.querySelector(`.${style.tableContainer}`).style.width = `${rightW}px`;
        }
    }, [mouseDownLine.current])

    //列表组件共通props
    const qnnTablecomProps = {
        fetch: myFetch,
        upload: rootTable.props.upload
    }

    // //左侧表格行被点击
    // const rowClick = useCallback((event, rowData) => {
    //     // selectLeftTableData.current = [rowData]
    //     //设置左侧选中的数据 
    //     setSelectLeftTableData([rowData])
    //     selectLeftTableDataRef.current = [rowData];

    //     leftTableRef.current.setSelectedRows([rowData])
    //     //设置右侧选中的数据 
    //     rightTableRef.current.setSelectedRows([{
    //         id: rowData.workID
    //     }])
    // }, [])

    //左侧 antd Table.onRow
    // const onRow = useCallback(rowData => {
    //     return {
    //         onClick: event => {
    //             rowClick(event, rowData)
    //         },
    //     };
    // }, [])


    // 左侧列表 选择数据 操作(选择和点击是两个操作，但目的都是为了选中数据)
    const leftTableChangeSelect = useCallback((selectedRowKeys, tableSelectedRows) => { 
        selectLeftTableDataRef.current = tableSelectedRows;
        setSelectLeftTableData(tableSelectedRows)

        // 需要设置右侧选择数据
        const rightData = rightTableRef.current.getTableData();
        const workID = tableSelectedRows[0]?.workID;
        if (workID) {
            const needSelectRows = [];// workID === id就需要被选中
            const loopFn = (data) => {
                data.forEach((item) => {
                    if (item.id === workID) { needSelectRows.push(item) }
                    else if (item.children) {
                        loopFn(item.children)
                    }
                })
            }
            loopFn(rightData)
            rightTableRef.current.setSelectedRows(needSelectRows)
        } else {
            // 清空右侧表格选中
            rightTableRef.current.setSelectedRows([])
        }
    }, [])

    //右侧列表 选择数据 操作(选择和点击是两个操作，但目的都是为了选中数据)
    const rightTableChangeSelect = useCallback(async (selectedRowKeys, tableSelectedRows) => {
        // setSelectRightTableData(tableSelectedRows);
        const selectLeftTableData = selectLeftTableDataRef.current;
        const isLeaf = selectLeftTableData[0]?.isLeaf === '1' || selectLeftTableData[0]?.isLeaf === 1;
        if (!selectLeftTableData.length || !isLeaf) {
            antdMsg.error("没有选择分包清单叶子节点！")
            rightTableRef.current.setSelectedRows([])
            return;
        }
        tableSelectedRows.length && rightTableRef.current.setSelectedRows([tableSelectedRows[tableSelectedRows.length - 1]])
        // console.log('选择的：', tableSelectedRows[0])
        //需要去 请求后台接口 然后 刷新左侧数据
        setRightTableLoading(true)
        const { success, message, code } = await rootTable.fetch('clickAddOrDeleteZxGcsgSaCoWorkLinkWork', {
            ...tableSelectedRows[0],
            workID: tableSelectedRows[0]?.id,
            ctContractId: selectLeftTableData[0]?.ctContractId,
            ccWorksId: selectLeftTableData[0]?.ccWorksId,
            isMain: selectLeftTableData[0]?.isMain,
            coWorkNo: selectLeftTableData[0]?.coWorkNo,
            coWorkName: selectLeftTableData[0]?.coWorkName,
            contractNo: contractNo,
            contractName: contractName,
            orgId: departmentId, orgName: departmentName,
            comId: companyId, comName: companyName,
        });
        setRightTableLoading(false)
        success && leftTableRef.current.refresh();
        !success && msg.errMsg(message, code);
    }, [selectLeftTableData])

    // 展开所有树节点
    // @treeRef 参数存在即需要操作改对象，否则直接返回id集合
    const expendAllNode = (treeData, treeRef, idKey) => {
        const expendNodeId = [];
        //展开所有节点
        const loopNodeFn = (data) => {
            data.forEach(item => {
                expendNodeId.push(item[idKey]); //id
                if (item["children"] && item["children"].length) {
                    loopNodeFn(item["children"])
                }
            })
        }
        loopNodeFn(treeData)
        if (treeRef) {
            treeRef.qnnSetState({
                expandedRowKeys: expendNodeId
            })
        } else {
            return expendNodeId
        }

    }

    //左侧树表加载完成
    const leftTableLoadDataEd = (res) => {
        const { data = [], success } = res;
        if (success) {
            expendAllNode(data, leftTableRef.current, "ccWorksId")
        }
    }

    //是否为主项字段切换
    const isMainChange = async (val, rowData) => {

        //需要手动改变表格数据  
        //树结构需要递归
        const loopNodeFn = (data) => {
            return data.map(item => {
                if (item['ccWorksId'] === rowData['ccWorksId']) {
                    item['isMain'] = val
                }
                if (item["children"] && item["children"].length) {
                    item["children"] = loopNodeFn(item["children"])
                }
                return item
            })
        }
        const newTreeData = loopNodeFn(leftTableRef.current.state.data);
         
        // leftTableRef.current.qnnSetState({
        //     qnnTableData: newTreeData
        // }) 
        leftTableRef.current.setTableData(newTreeData);

        // 更新当前选中行数据中的isMain
        if(selectLeftTableDataRef.current[0]){
            selectLeftTableDataRef.current[0].isMain = val
        }

        //当该条数据中 workID 存在时(表示已经挂载了数据)，需要去请求接口保存是否为主项
        if (rowData["saCoWorkLinkWorkId"]) {
            const { success, message, code } = await rootTable.fetch('updateZxGcsgSaCoWorkLinkWorkIsMain', {
                saCoWorkLinkWorkId: rowData.saCoWorkLinkWorkId,
                isMain: rowData.isMain === "1" ? "0" : "1",
            });
            !success && msg.errMsg(message, code);
        }

        // 更新右侧树表的选择框禁用状态 
        setTimeout(() => {
            const rightTreeIds = expendAllNode(rightTableRef.current.getTableData(), null, "id");
            // console.log('需要更新：', rightTreeIds)
            rightTreeIds.forEach((item) => {
                const tdRef = rightTableRef.current.getTdRef({
                    rowId: item, //行id
                    field: "_rowSelection" //选择列的field, 固定的
                });
                tdRef && tdRef.forceUpdateCheckboxProps();
            })
        }, 300)
    }

    //左侧table字段配置数据
    const leftTableFormConfig = {
        ...qnnTablecomProps,
        field: "leftTable",
        wrappedComponentRef: (table) => leftTableRef.current = table,
        desc: "数据量大时可折叠树节点以提升性能体验",
        antd: {
            rowKey: "ccWorksId",
            size: "small",
            // onRow: onRow,
            rowSelection: {
                type: "radio",
                onChange: leftTableChangeSelect,
                clickRowCanSelect: true,
                clickRowCanCancelSelect: false,
                hideSelectAll: true,
            },
            scroll: {
                y: window.innerHeight - 55 - 46 - 34 - 56 - 100
            },

            expandable: {
                //不可以展开和收缩
                //    onExpand: () => { },
                //    expandIcon: () => "",
                defaultExpandAllRows: true
            }
        },
        fetchConfig: {
            apiName: 'getZxGcsgSaCoWorkLinkWorkLeftTree',//可为function 返回必须为string 
            otherParams: {
                ctContractId: rootId
            },
            success: leftTableLoadDataEd
        },
        formConfig: [
            {
                table: {
                    title: '分包清单编号',
                    dataIndex: 'coWorkNo',
                    // dataIndex: 'itemName',
                    width: 200
                }
                , form: {
                    type: 'string',
                }
            },
            {
                table: {
                    title: '分包清单名称',
                    dataIndex: 'coWorkName',
                    width: 130
                }
                , form: {
                    type: 'string',
                }
            },
            {
                table: {
                    title: '单位',
                    dataIndex: 'coUnit',
                }
                , form: {
                    type: 'string',
                }
            },
            {
                table: {
                    title: '是否为主项',
                    dataIndex: 'isMain',
                    width: 120,
                    // tdEdit: true
                    render: (val, rowData) => {
                        const isLeaf = rowData.isLeaf === '1' || rowData.isLeaf === 1;
                        return isLeaf ? <div onClick={(e) => {
                            // e.stopPropagation(); 
                        }}>
                            <Radio.Group onChange={(e) => {
                                isMainChange(e.target.value, rowData)
                            }} value={val || "0"} defaultValue="0">
                                <Radio value={"0"}>否</Radio>
                                <Radio value={"1"}>是</Radio>
                            </Radio.Group>
                        </div> : ""
                    }
                }
                , form: {
                    type: 'radio',
                    // initialValue: "0",
                    // optionData: [
                    //     { label: "否", value: "0" },
                    //     { label: "是", value: "1" }
                    // ],
                    // onChange: isMainChange
                }
            },
            {
                table: {
                    title: '业主清单编号',
                    dataIndex: 'workNo',
                    width: 110
                }
                , form: {
                    type: 'string',
                }
            },
            {
                table: {
                    title: '业主清单名称',
                    dataIndex: 'workName',
                    width: 110
                }
                , form: {
                    type: 'string',
                }
            },
        ]
    }

    //右侧树表加载完成
    const rightTableLoadDataEd = (res) => {
        const { data = [], success } = res;
        if (success) {
            expendAllNode(data, rightTableRef.current, "id")
        }
    }

    //右侧树表不能点击选择，需要让用户手动选中才能挂接
    //右侧table字段配置数据
    const rightTableFormConfig = {
        ...qnnTablecomProps,
        field: "leftTable",
        wrappedComponentRef: (table) => rightTableRef.current = table,
        desc: <div>{rightTableLoading ? <a><LoadingOutlined /> loading...</a> : "选择时将自动关联数据"}</div>,
        antd: {
            rowKey: "id",
            size: "small",
            // onRow: onRowByRight,
            rowSelection: {
                // type: "radio",
                checkStrictly: true,
                onChange: rightTableChangeSelect,
                // 设置某行为禁止选中
                getCheckboxProps: record => {
                    // const idDisabeld = (selectLeftTableDataRef.current[0]?.isMain === "1") && record.isLeaf === 1;
                    const idDisabeld = (selectLeftTableDataRef.current[0]?.isMain === "1") && record.isLeaf !== 1; 
                    // console.log('--',record, selectLeftTableDataRef.current[0], idDisabeld)
                    return {
                        disabled: idDisabeld,
                    }
                },
            },
            scroll: {
                y: window.innerHeight - 55 - 46 - 34 - 56 - 100
            },
        },
        fetchConfig: {
            apiName: 'getZxCtWorksRightTree',//可为function 返回必须为string 
            otherParams: {
                orgID: firstId
            },
            success: rightTableLoadDataEd
        },
        formConfig: [
            {
                table: {
                    title: '业主清单编号1',
                    dataIndex: 'workNo',
                    // dataIndex: 'itemName',
                    width: 120
                }
                , form: {
                    type: 'string',
                }
            },
            {
                table: {
                    title: '业主清单名称',
                    dataIndex: 'workName',
                    width: 120
                }
                , form: {
                    type: 'string',
                }
            },

            {
                table: {
                    title: '单位',
                    dataIndex: 'coUnit',
                }
                , form: {
                    type: 'string',
                }
            },


        ]
    }


    //onMouseUp onMouseMove 事件应该给根元素
    return <div className={`${style.container} ${curEnv === 'detail' ? style.detailContainer : ''}`} onMouseUp={hrlineOnMouseUp} onMouseMove={hrlineOnMouseMove}>
        <div className={style.gcsghtNo}>
            <b>工程施工合同编号</b>：{contractNo}
        </div>
        <div className={`${style.treeContainer}`}>
            <div className={style.treeContent}>
                {
                    useMemo(() => canRender ? <QnnTable  {...leftTableFormConfig} /> : <div />, [canRender])
                }
            </div>
            <div className={style.hrline} onMouseDown={hrlineOnMouseDown}></div>
        </div>
        <div className={`${style.tableContainer}`}>
            {
                useMemo(() => canRender ? <QnnTable  {...rightTableFormConfig} /> : <div />, [canRender])
            }
        </div>
    </div>
}
