import React,{ Component } from "react"; 
import Contacts from "apih5/pages/contacts" 
class ContactsPage extends Component {
    render() { 
        return (
            <Contacts 
                {...this.props}
            />
        );
    }
}
 
export default ContactsPage;
