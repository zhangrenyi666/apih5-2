
import PropTypes from "prop-types";
const propTypes = () => {
    return {
        edit: PropTypes.bool,
        _addFieldCb: PropTypes.func,
        _editFieldCb: PropTypes.func, 
        fetch:PropTypes.func
    }
}

export default propTypes;