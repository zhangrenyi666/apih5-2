import React,{ Component } from "react"; 
import AddressBook from "apih5/pages/AddressBook"
class AddressBookPage extends Component {
    render() {
        return (
            <AddressBook 
                {...this.props}
            />
        );
    }
}
 
export default AddressBookPage;
