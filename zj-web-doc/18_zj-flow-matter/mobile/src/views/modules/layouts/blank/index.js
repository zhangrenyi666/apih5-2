import React from 'react'; 
const blank = (Children) => {
    return (props) => {
        return <Children {...props} />
    }
}
export default blank