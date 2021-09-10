

//分包合同清单 第二个tab页面
import React, { useCallback, useRef, useState, useEffect } from "react"
import style from "./ListSubcontracts.less"
import Tree from "qnn-tree"
import QnnTable from "qnn-table";
import tabTwoRootTable from "./tabTwoRootTable"
import { Spin, message as Msg } from 'antd';

const ListSubcontracts = (props) => {
  console.log(props);
  const mounted = useRef(false);
  //表单主键
  const rootId = props.contractID;

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

  //数据清单列表数据
  const rootTableData = useRef([]);

  const myFetch = useCallback(props.myFetch, [])

  // 左侧树组件key
  const treeKey = {
    label: "workName",
    value: "ccWorksId",
    children: "children"
  }

  // 清单树列表key
  const rootTableKey = {
    children: "children",
    value: tabTwoRootTable.antd.rowKey
  }

  //右侧视图是否是loading状态
  const [rightViewLoading, setRightViewLoading] = useState(false);

  //表格数据
  const [tableData, setTableData] = useState([])

  useEffect(() => {
    mounted.current = true;

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

  //挂接列表ref获取
  const getTreeREf = useCallback((me) => treeRef.current = me, [])
  const getTableREf = useCallback((me) => tabTwoTableRef.current = me, [])

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

  //展示清单列表表格时候需要做的事
  const showRootTableDo = () => {
    tabTwoTableRef.current.qnnSetState({
      expandedRowKeys: getTreeAllId(rootTableData.current, rootTableKey),
    })
  }


  //树节点每次请求后的回调函数
  const treeGetDataCb = useCallback((data, treeNode) => {
    if (!treeNode) {
      //首次请求
      const needExpendData = data?.[0];
      if (needExpendData) {
        //默认让第一个节点点击 然后请求出表格数据
        treeRef.current.onExpand(needExpendData);
        nodeClick({
          props: { dataRef: { ...needExpendData } }
        })
      }
    }
  }, [])

  // 获取下级清单列表数据的ajax
  const getRootTableData = async () => {
    const levelId = curSelectedTreeNodeDataRef.current?.props?.dataRef?.treeNode;
    if (!levelId) return;
    setRightViewLoading(true)
    await props.btnCallbackFn.fetchByCb('getZxGcsgCcWorksListAmount', {
      treeNode: levelId,
      ctContractId: rootId
    }, ({
      success, message, code, data
    }) => {
      setRightViewLoading(false)
      if (!mounted.current) return;
      if (success && data) {
        setTableData(data)
        rootTableData.current = data;
        showRootTableDo()
      } else {
        Msg.err(message, code)
      }
    });
  }


  //树节点点击函数
  const nodeClick = useCallback(async (node) => {
    if (!mounted.current) return;
    curSelectedTreeNodeDataRef.current = node;
    getRootTableData();
  }, [mounted.current])

  const qnnTablecomProps = {
    fetch: myFetch,
  }

  return <div className={`${style.container} ${style.detailContainer}`} onMouseUp={hrlineOnMouseUp} onMouseMove={hrlineOnMouseMove}>
    <div className={`${style.treeContainer}`}>
      <div className={style.treeContent}>
        <Tree
          visible
          modalType="common"
          nodeClick={nodeClick}
          myFetch={myFetch}
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
            apiName: "getZxGcsgCcWorksTree",
            params: {
              ctContractId: rootId,
              parentID: "-1",
            },
            success: treeGetDataCb
          }}
          keys={{ ...treeKey }}
        />
        <div className={style.hrline} onMouseDown={hrlineOnMouseDown}></div>
      </div>
      <div className={style.hrline}></div>
    </div>
    <div className={`${style.tableContainer} `}>
      <Spin spinning={rightViewLoading}>
        <QnnTable
          {...qnnTablecomProps}
          {...tabTwoRootTable}
          field={"tabTwoRootTable"}
          data={tableData}
          wrappedComponentRef={getTableREf}
          antd={{
            ...tabTwoRootTable.antd,
          }}
          isShowRowSelect={false}
        />
      </Spin>
    </div>
  </div>
}

export default ListSubcontracts
