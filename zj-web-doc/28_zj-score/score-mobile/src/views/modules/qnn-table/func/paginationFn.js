//分页点击
const paginationChange = function (page,pageSize) {
    this.setState({
        limit: pageSize,
        curPage: page
    },
        () => {
            this.refresh();
        }
    );
};

//选择 当点击第二页时第一页选择的数据不见了
const rowSelection = function () {
    const {
        selectedRows,
        antd,
        getRowSelection
    } = this.state;
    let _getRowSelection = {};
    if (getRowSelection) {
        _getRowSelection = getRowSelection({
            ...this.props,
            fetch: this.fetch,
            btnCallbackFn: this.btnCallbackFn
        });
    }
    return {
        onChange: (selectedRowKeys,selectedRows) => {
            this.setState({
                selectedRows
            });
        },
        selectedRowKeys: selectedRows.map(item => {
            let rowKey = (typeof antd.rowKey) === 'string' ? item[antd.rowKey] : antd.rowKey(item);
            return rowKey
        }),
        ..._getRowSelection
    };
};

export {
    paginationChange,
    rowSelection
};