import React from "react";
import { Table } from "antd";
const index = function() {
    const {
        data,
        loading,
        paginationConfig,
        totalNumber,
        curPage,
        limit,
        antd,
        columns,
        isShowRowSelect,
        tableContentWidth
    } = this.state;
    //设置滚动条
    if (tableContentWidth !== "100%" && tableContentWidth) {
        antd.scroll = {
            x: tableContentWidth
        };
    }
    return (
        <Table
            loading={loading}
            dataSource={data}
            columns={columns}
            bordered={true}
            rowSelection={isShowRowSelect ? this.rowSelection() : null}
            {...antd}
            pagination={{
                position: "both",
                current: curPage,
                defaultCurrent: 1,
                defaultPageSize: 10,
                pageSize: limit,
                total: totalNumber,
                showTotal: total => {
                    return `共查询到 ${total} 条数据`;
                },
                onChange: this.paginationChange,
                showSizeChanger: true,
                onShowSizeChange: (current, pageSize) => {
                    this.setState(
                        {
                            limit: pageSize
                        },
                        () => {
                            this.refresh();
                        }
                    );
                },
                ...paginationConfig
            }}
        />
    );
};

export default index;
