import React from "react"; 
import PullPerson from "../../pullPersion"; 
const index = function() {
    const { getFieldDecorator } = this.props.form;
    const {
        openTree,
        pullpersonChangeFn,
        treeOption,
        pullpersonField
    } = this.state;
    if (openTree) {
        return (
            <div>
                {getFieldDecorator(pullpersonField, {
                    valuePropName: "defaultValue",
                    onChange: pullpersonChangeFn
                })(
                    <PullPerson
                        visible
                        label={<a>{null}</a>}
                        myFetch={this.fetch}
                        {...treeOption}
                    />
                )}
            </div>
        );
    } else {
        return null;
    }
};

export default index;
