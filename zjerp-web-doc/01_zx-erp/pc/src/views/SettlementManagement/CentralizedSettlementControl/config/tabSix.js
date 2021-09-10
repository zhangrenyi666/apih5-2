

//分包合同清单
import React, { useCallback, useRef, useState, useEffect } from "react"
import style from "./ListSubcontracts.less"
import Tree from "qnn-tree"
import QnnTable from "qnn-table";
import QnnForm from "qnn-form";
import tabTwoRootTable from "./tabSixRootTable"
import tabTwoGjTable from "./tabTwoGjTable"
// import tabTwoEditTable from "./tabTwoEditTable"
import { Button, Select, Row, Col, Input, Spin, message } from 'antd';
import { RollbackOutlined, SaveOutlined } from '@ant-design/icons';
import { msg } from "qnn-apih5"
import jjgzFieldConfig from "./jjgzFieldConfig"
import { isMoment } from "moment";
const { Option } = Select;

const ListSubcontracts = (props) => {

    const mounted = useRef(false);

    //当前环境
    const curEnv = props.clickCb?.rowInfo?.name;
    //是否是详情
    // const isDetail = curEnv === "detail";

    //跟列表ref
    const rootTable = props.getRootTable();

    //表单主键
    const rootId = rootTable.qnnForm.form?.getFieldValue?.("ctContrApplyId");

    //首次渲染
    const firstRender = useRef(true);

    //左右区域宽带设置线
    const mouseDownLine = useRef();

    //树
    const treeRef = useRef();
    //当前点击的树节点数据
    const curSelectedTreeNodeDataRef = useRef({});

    //下级清单ref
    const tabTwoTableRef = useRef();

    //挂接列表ref
    const tabTwoGjTableRef = useRef();
    //编辑表格的ref
    // const tabTwoEditTableRef = useRef();

    //选择的计价规则数据
    const selectJjgzValueRef = useRef();
    //数据清单列表数据
    const rootTableData = useRef([]);
    //选择的清单列表数据
    const selectRootTableData = useRef([]);

    //如果是挂接列表的话 会存一个被点击的行的数据
    const curGJRowData = useRef();

    //挂接页面 计价规则ref
    const jjgzRef = useRef();

    const myFetch = useCallback(rootTable.fetch, [])

    // 左侧树组件key
    const treeKey = {
        label: "workName",
        value: "ctContrApplyWorksId",
        children: "children"
        // label: "itemName",
        // value: "codeId",
        // children: "children"
    }
    // 工序、工序库下拉的key
    const gxkKey = {
        label: "processName",
        value: "id",
        baseType: "baseType",
        children: "children"
    }

    //清单树列表key
    const rootTableKey = {
        children: "children",
        value: tabTwoRootTable.antd.rowKey
    }
    //挂接树列表key
    const gjTableKey = {
        children: "children",
        value: tabTwoGjTable.antd.rowKey
    }

    //右侧视图 table（清单列表） | gjTable（挂接列表） 
    const [rightView, setRightView] = useState("table");

    //右侧视图是否是loading状态
    const [rightViewLoading, setRightViewLoading] = useState(false);
    //请求挂接节点loading状态
    const [gjLoading, setGjLoading] = useState(false);

    //表格数据
    const [tableData, setTableData] = useState([])

    //挂接页面表格数据
    const [gjTableData, setGjTableData] = useState([])

    //工序库下拉数据
    const [gxkOptionData, setGxkOptionData] = useState([])
    //选择的工序库
    const [gxkValue, setGxkValue] = useState(undefined)
    //baseType 后台需要用的 在工序库下拉中可以获取到
    const [baseType, setBaseType] = useState(undefined)

    //工序下拉数据
    const [gxOptionData, setGxOptionData] = useState([])
    //选择的工序
    const [gxValue, setGxValue] = useState(undefined)

    //获取工序库下拉列表
    const getGXK = async () => {
        const { success, message, code, data } = await rootTable.fetch('gcsgGetZxCtSZProcessSelect', {});
        if (success) {
            return data
        } else {
            msg.errMsg(message, code)
        }
    }

    useEffect(() => {

        mounted.current = true;

        //请求工序库数据 
        getGXK().then((data) => {
            if (mounted.current) {
                //设置工序库数据和默认选中的工序库
                setGxkOptionData(data);
                setGxkValue(data[0]?.[gxkKey.value])
                setBaseType(data[0]?.[gxkKey.baseType])
            }
        })

        firstRender.current = false;
        return () => mounted.current = false
    }, [])

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

    //自动挂接
    // const autoArticulatedClick = useCallback(async () => {
    //     const { success, message, code } = await rootTable.fetch('autoHookZxGcsgCtContrApplyWorksProcess', { ctContrApplyId: rootId });
    //     if (success) {
    //         msg.successMsg(message)
    //     } else {
    //         msg.errMsg(message, code)
    //     }
    // }, [gxkValue, baseType])

    //设置工序的下拉数据和下拉值
    const setGxOptionDataAndValue = (_gxkValue = gxkValue, _gxkOptionData = gxkOptionData) => {
        //设置工序列表
        const gxOptionData = _gxkOptionData.filter(item => item[gxkKey.value] === _gxkValue)[0]?.[gxkKey.children] || [];
        setGxOptionData(gxOptionData);
        //设置工序选中数据
        const gxValue = gxOptionData[0]?.[gxkKey.value] || undefined;
        setGxValue(gxValue)
    }

    //工序库下拉切换
    //需要联动设置工序下拉和值
    const gxkChange = useCallback((val) => {
        //设置选中的工序库
        setGxkValue(val);
        setBaseType(gxkOptionData.filter(item => item[gxkKey.value] === val)[0]?.[gxkKey.baseType]);
        //清单列表页面不需要设置工序下拉的数据
        if (rightView === 'table') return;
        //设置工序列表、工序选中数据 
        setGxOptionDataAndValue(val)
    }, [gxkOptionData, rightView]);

    //工序下拉切换
    const gxChange = useCallback((val) => setGxValue(val), []);

    //左侧树组件ref获取
    //下级清单表格ref获取
    //挂接列表ref获取
    const getTreeREf = useCallback((me) => treeRef.current = me, [])
    const getTableREf = useCallback((me) => tabTwoTableRef.current = me, [])
    const getGjTableREf = useCallback((me) => tabTwoGjTableRef.current = me, [])

    //获取一个树列表的所有节点主键（用于展开一个树列表）
    //@treeData  树节点数据  Array<nodeInfo>
    //@keys{ value, children }   { value:string, children:string }
    const getTreeAllId = (treeData, { value, children }) => {
        if (!treeData) return;
        const IDs = [];
        const loopFn = (treeData) => {
            for (let i = 0; i < treeData.length; i++) {
                const item = treeData[i];
                IDs.push(item[value])
                item[children] && item[children].length && loopFn(item[children])
            }
        }
        loopFn(treeData);
        return IDs;
    }

    //获取 挂接列表 数据的ajax
    const getGjTableData = async () => {
        const { success, message, code, data } = await rootTable.fetch('gcsgGetZxCtSZProcessList', {
            ctContrApplyId: rootId, //最外层表格的id
            applyAlterWorksId: curGJRowData.current?.ctContrApplyWorksId, //抽屉中清单id
            baseType: baseType, //baseType
            parentID: gxValue, //工序id 
        });
        if (success) return data;
        msg.errMsg(message, code);
    }

    //展示清单列表表格时候需要做的事
    const showRootTableDo = () => {
        //应让表格选中数据
        //还需要展开所有子节点 
        tabTwoTableRef.current.qnnSetState({
            expandedRowKeys: getTreeAllId(rootTableData.current, rootTableKey),
            selectedRows: selectRootTableData.current
        })
    }

    //行数据右侧 挂接按钮
    const rowGJClick = useCallback((args) => {
        //测试完毕需要恢复注释下面判断
        if (args.rowData.isLeaf !== 1) {
            message.warning('当前节点不可挂接，请选择叶子节点挂接。')
            return;
        }
        curGJRowData.current = args.rowData;
        setRightView("gjTable")
    }, [])


    //监听切换列表后进行数据请求和设置等操作 
    useEffect(() => {
        if (rightView === 'gjTable') {
            //打开挂接列表
            (async () => {
                //首次打开需要设置（其实也就是没有gxValue时候，因为gxValue存在后在调用这个方法将会把gxValue刷新没了）
                //设置工序值和下拉选项
                !gxValue && setGxOptionDataAndValue()

                //设置计价规则输入框值
                //这里因为只需要显示一个name  所以回显就直接显示name就行
                //如果用id回显的话 会存在一个问题  请求计价规则时候没保存baseType  但是请求计价规则需要根据basetype来请求...
                //如果需要使用id回显 那需要后台在存一个baseType(和计价规则一样存)
                const ruleID = curGJRowData.current?.["ruleName"];
                jjgzRef.current.setValues({
                    ruleName: ruleID
                })

                //获取挂接列表数据
                //必须是工序选择后才能去请求
                if (gxValue) {
                    setRightViewLoading(true)
                    const listData = await getGjTableData() || [];
                    if (!mounted.current) return;
                    setRightViewLoading(false)

                    //设置默认选中的数据 
                    //默认展开全部树节点
                    tabTwoGjTableRef.current.qnnSetState({
                        expandedRowKeys: getTreeAllId(listData, gjTableKey),

                        //选择的节点的所有子节点也需要放入到选中节点中（否则就不能放入父节点，而是放入选择的子节点）
                        selectedRows: listData.filter(item => item["ctContrProcessGuajieId"] && item["ctContrProcessGuajieId"] !== "")
                    })
                    setGjTableData(listData);
                }

            })()
        } else if (rightView === 'table' && !firstRender.current) {
            refreshRootTable();

            treeRef.current.onLoadData(curSelectedTreeNodeDataRef.current, true)

        } else if (rightView === 'editLowerList') {
            //编辑下级清单
            //需要请求下级清单数据
        }
    }, [gxValue, rightView])

    //返回 下级清单列表
    const goLowerList = useCallback(() => {
        setRightView("table")
    }, [])

    //清单列表 选择数据 操作
    const rootTableChangeSelect = useCallback((selectedRowKeys, tableSelectedRows) => {
        selectRootTableData.current = tableSelectedRows;
    }, [])

    //挂接列表 选择数据 操作
    const gjTableChangeSelect = useCallback(async (selectedRowKeys, tableSelectedRows) => {
        setGjLoading(true)
        const { success, message, code, data } = await rootTable.fetch('manualHookZxGcsgCtContrApplyWorksProcess', {
            inputWorkType: baseType,
            ctContrApplyWorksId: curGJRowData.current?.ctContrApplyWorksId,
            processArray: tableSelectedRows
        });
        isMoment && setGjLoading(false)
        if (success) return data;
        msg.errMsg(message, code);

    }, [baseType])

    //表格行被点击
    const rowClick = useCallback((event, rowData) => {
        selectRootTableData.current = [rowData]
        tabTwoTableRef.current.setSelectedRows([rowData])
    }, [])

    //antd Table.onRow
    const onRow = useCallback(rowData => {
        return {
            onClick: event => {
                rowClick(event, rowData)
            },
        };
    }, [])

    //树节点每次请求后的回调函数
    const treeGetDataCb = useCallback((data, treeNode, allNodeData) => {
        if (!treeNode) {
            //首次请求
            const needExpendData = data?.[0];
            if (needExpendData) {
                //默认展开第一个节点
                //默认让第一个节点点击 然后请求出表格数据
                treeRef.current.onExpand(needExpendData);
                nodeClick({
                    props: { dataRef: { ...needExpendData } }
                })
            }
        }
    }, [])

    //获取下级清单列表数据的ajax
    const getRootTableData = async ({ levelId }) => {
        const { success, message, code, data } = await rootTable.fetch('getZxGcsgCtContrApplyWorksListAmount', { parentID: levelId, ctContrApplyId: rootId });
        if (success) {
            return data
        } else {
            msg.errMsg(message, code)
        } 
    }

    //刷新清单列表的方法
    const refreshRootTable = async () => {
        const levelId = curSelectedTreeNodeDataRef.current?.props?.dataRef?.[treeKey.value];
        if (!levelId) return;

        setRightViewLoading(true)
        const listData = await getRootTableData({ levelId });
        if (!mounted.current) return;
        setRightViewLoading(false)
        if (listData) {
            setTableData(listData)

            //默认选中表格第一条数据
            //默认展开全部树节点
            rootTableData.current = listData;
            selectRootTableData.current = listData[0] ? [listData[0]] : []
            showRootTableDo()
        }
    }

    //树节点点击函数
    const nodeClick = useCallback(async (node) => {
        //如果视图不是 table 不让点击
        if (rightView !== 'table') return;
        if (!mounted.current) return;
        curSelectedTreeNodeDataRef.current = node;
        refreshRootTable();
    }, [rightView, mounted.current])

    //保存计价规则
    const jjgzSaveClick = useCallback(async () => {
        const { success, message, code } = await rootTable.fetch('manualHookZxGcsgCtContrApplyWorksRule', {
            ruleID: selectJjgzValueRef.current,
            //这个是当前挂接的清单id
            ctContrApplyWorksId: curGJRowData.current?.["ctContrApplyWorksId"],
            inputWorkType: baseType
        });
        if (success) {
            //保存成功
            msg.successMsg(message)
        } else {
            msg.errMsg(message, code)
        }
    }, [baseType, rootId])


    //列表组件共通props
    const qnnTablecomProps = {
        fetch: myFetch,
        upload: rootTable.props.upload
    }

    //左侧树节点视图
    const treeView = (<Tree
        visible
        modalType="common"
        nodeClick={nodeClick}
        myFetch={myFetch}
        //0 不可选 1 单选(默认) 2 多选（暂不可用）
        selectModal="0"
        btnShow={false}
        selectText={false}
        draggable={false}
        ref={getTreeREf}
        //右键菜单选项 
        rightMenuOption={[]}
        rightMenuOptionByContainer={[]}
        fetchConfig={{
            parmasKey: "parentID",  //点击节点后将节点id赋值该key上传递给后台
            apiName: "getZxGcsgCtContrApplyWorksTree",
            params: {
                ctContrApplyId: rootId,
                parentID: "-1",
                // itemId: "xingzhengquhuadaima"
            },
            success: treeGetDataCb
        }}
        keys={{ ...treeKey }}
    />)

    //清单列表视图
    const tableView = (<>
        <QnnTable
            {...qnnTablecomProps}
            {...tabTwoRootTable}
            field={"tabTwoRootTable"}
            data={tableData}
            wrappedComponentRef={getTableREf}
            method={{
                rowGJClick: rowGJClick,
            }}
            curEnv={curEnv}
            antd={{
                ...tabTwoRootTable.antd,
                onRow: onRow,
                rowSelection: {
                    type: "radio",
                    onChange: rootTableChangeSelect
                }
            }}
        />

    </>)

    //计价规则搜索框配置
    const realJjgzFieldConfig = {
        ...jjgzFieldConfig,
        qnnTableConfig: {
            ...jjgzFieldConfig.qnnTableConfig,

            fetchConfig: {
                apiName: "gcsgGetZxCtValuationSZRulesList",
                otherParams: {
                    baseType: baseType
                }
            },
        },
        onChange: (val) => {
            selectJjgzValueRef.current = val;
        }
    }

    //挂接列表视图
    const GJTableView = (<>
        <div style={{ marginBottom: 12 }}>
            <Row justify="space-between" align="middle">
                <Col>
                    <Row align="middle">
                        <Col>标准工序库：</Col>
                        <Col>
                            <Input.Group compact>
                                <Select value={gxkValue} style={{ width: 180, }} onChange={gxkChange} placeholder="请选择">
                                    {gxkOptionData.map(item => <Option value={item[gxkKey.value]} key={item[gxkKey.value]}>{item[gxkKey.label]}</Option>)}
                                </Select>
                                <Select value={gxValue} style={{ width: 180 }} onChange={gxChange} placeholder="请选择">
                                    {gxOptionData.map(item => <Option value={item[gxkKey.value]} key={item[gxkKey.value]}>{item[gxkKey.label]}</Option>)}
                                </Select>
                            </Input.Group>
                        </Col>
                    </Row>
                </Col>

                <Col span={10}>
                    <Row align="middle">
                        <Col span={19}>
                            <span style={{ width: 500, display: "inlineBlock" }}>
                                <QnnForm {...qnnTablecomProps} style={{ paddingBottom: 0 }} wrappedComponentRef={(qnnForm) => jjgzRef.current = qnnForm}>
                                    <QnnForm.Field {...realJjgzFieldConfig} />
                                </QnnForm>
                            </span>
                        </Col>
                        <Col>
                            <Button type="primary" style={{ marginLeft: 6 }} onClick={jjgzSaveClick} icon={<SaveOutlined />}>保存</Button>
                        </Col>
                    </Row>

                </Col>

                <Col>
                    <Button onClick={goLowerList} ghost type="primary" icon={<RollbackOutlined />}>返回</Button>
                </Col>
            </Row>
        </div>
        <QnnTable
            {...qnnTablecomProps}
            {...tabTwoGjTable}
            antd={{
                ...tabTwoGjTable.antd,
                rowSelection: {
                    checkStrictly: false,
                    onChange: gjTableChangeSelect
                }
            }}
            desc={gjLoading ? <a>请稍等，保存中...</a> : "请选择挂接的节点"}
            field={"tabTwoGjTable"}
            data={gjTableData}
            wrappedComponentRef={getGjTableREf}
            method={{
                // gjTableRightBtns: gjTableRightBtns
            }}
        />
    </>)

    //根据rightView来判断需要渲染哪个视图
    const realViews = {
        table: tableView, //（清单列表） 
        gjTable: GJTableView, //（挂接列表）  
    }[rightView];

    //onMouseUp onMouseMove 事件应该给根元素
    return <div className={`${style.container} ${curEnv === 'detail' ? style.detailContainer : ''}`} onMouseUp={hrlineOnMouseUp} onMouseMove={hrlineOnMouseMove}>
        <div className={`${style.treeContainer} ${rightView !== 'table' ? style.hide : ""}`}>
            <div className={style.treeContent}> {treeView} </div>
            <div className={style.hrline} onMouseDown={hrlineOnMouseDown}></div>
        </div>
        <div className={`${style.tableContainer} ${rightView !== 'table' ? style.fullWidth : ""}`} key={rightView}>
            <Spin spinning={rightViewLoading}>{realViews}</Spin>
        </div>
    </div>
}

export default ListSubcontracts
