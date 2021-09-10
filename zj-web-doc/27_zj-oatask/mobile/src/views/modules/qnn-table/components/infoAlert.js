import React from "react";
import { Alert } from "antd";
const index = function() {
    const { selectedRows, infoAlert } = this.state;
    if (!infoAlert) {
        return <div />;
    }
    return (
        <Alert
            style={{ marginBottom: "10px" }}
            message={<div>{infoAlert(selectedRows)}</div>}
            type="info"
            showIcon
        />
    );
};

export default index;
