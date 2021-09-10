import React,{ Component } from "react"; 
import News from "apih5/pages/News"
class NewsPage extends Component {
    render() {
        return (
            <News 
                {...this.props}
            />
        );
    }
}
 
export default NewsPage;
