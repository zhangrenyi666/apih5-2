import React from "react";
import QnnTable from "../../../qnn-table/index";
const createQnnTable = function(content, style = {}) {
    const { isInQnnTable } = this.props;  
    return (
        <div name="QnnTable" style={isInQnnTable ? {padding:"0px 12px"} : null}>
            <QnnTable
                {...this.props}
                fetch={this.props.myFetch}
                headers={{
                    token: this.props.loginAndLogoutInfo.loginInfo.token
                }}
                {...content}
            />
        </div>
    );
};
export default createQnnTable;
