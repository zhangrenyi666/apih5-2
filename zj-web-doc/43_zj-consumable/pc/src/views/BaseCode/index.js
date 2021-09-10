import React, { Component } from "react";
import BaseCode from "apih5/pages/BaseCode"
class BaseCodePage extends Component {
    render() {
        return (
            <div>
                <BaseCode
                    {...this.props}
                />
            </div>
        );
    }
}

export default BaseCodePage;
