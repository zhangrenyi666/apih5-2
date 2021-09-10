//分页点击
const paginationChange = function(page, pageSize) {
  this.setState(
    {
      limit: pageSize,
      curPage: page
    },
    () => {
      this.refresh();
    }
  );
};

//选择 当点击第二页时第一页选择的数据不见了
const rowSelection = function() {
  return {
    onChange: (selectedRowKeys, selectedRows) => {
      this.setState({
        selectedRows
      });
    }
  };
};

export { paginationChange, rowSelection };
