
//节点挂接列表
export default {
   antd: {
      rowKey: "id",
      size: 'small',
      expandable: {
         //不可以展开和收缩
         // onExpand: () => { },
         // expandIcon: () => ""
      },
      scroll: {
         y: window.innerHeight - 380
      }
   },
   formConfig: [
      {
         table: {
            title: '编码',
            dataIndex: 'processNo',
            fixed: "left",
            width: 200
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '标准工序名称',
            dataIndex: 'processName',
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '单位',
            dataIndex: 'processUnit',
         }
         , form: {
            type: 'string',
         }
      },
      {
         table: {
            title: '施工内容',
            dataIndex: 'content',
            width: 500
         }
         , form: {
            type: 'string',
         }
      },

   ],
}