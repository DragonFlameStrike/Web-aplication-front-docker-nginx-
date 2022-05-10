import ReactDOM from "react-dom";
import app from "../app";
import CategoryService from "../services/CategoryService";


const React = require("react");

class CategoryEditComponent extends React.Component{

    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.cid,
            name: '',
            oldname: '',
            idRequest: '',

        }
        this.changeName = this.changeName.bind(this);
        this.changeIdRequest = this.changeIdRequest.bind(this);

    }
    componentDidMount() {
        CategoryService.getCategoryById(this.state.id).then((res) => {
            let category = res.data;
            this.setState({
                name: category.name,
                oldname: category.name,
                idRequest: category.idRequest,
            });
        });
    }

    changeName = (event) => {
        this.setState({name: event.target.value});
    };

    changeIdRequest= (event) => {
        this.setState({idRequest: event.target.value});
    }

    saveCategory=(e) => {
        e.preventDefault();
        let category = {name: this.state.name.toString(), idRequest: this.state.idRequest.toString()};
        CategoryService.updateCategory(category, this.state.id).then( res => {
            this.props.history.push('/categories/');
            window.location.reload();

        });

    }
    deleteCategory=(e) => {
        e.preventDefault();
        CategoryService.deleteCategory(this.state.id).then( res => {
            this.props.history.push('/');
            window.location.reload();
        });
    }

    render() {
        return (
            <section className="content">
                <form method="post">
                    <header className="content__header">
                        <span className="content__header-text">Edit category {this.state.oldname}</span>
                    </header>

                    <div className="content__body">
                        <div className="content__form">
                            <div className="content__form-item">
                                <div className="content__form-item-title">Name</div>
                                <div className="content__form-item-content">
                                    <input className="content__input" type="text" name="name" value={this.state.name}
                                           onChange={this.changeName}/>
                                </div>
                            </div>
                            <div className="content__form-item">
                                <div className="content__form-item-title">Request ID</div>
                                <div className="content__form-item-content">
                                    <input className="content__input" type="text" name="RequestId" value={this.state.idRequest}
                                           onChange={this.changeIdRequest}/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <footer className="content__footer">
                        <div className="content__buttons">
                            <button type="submit" className="content__button content__button_dark" onClick={this.saveCategory}>Save</button>
                            <button className="content__button content__button_red" onClick={this.deleteCategory}>Delete</button>
                        </div>
                    </footer>
                </form>
            </section>
        );
    }
}
export default CategoryEditComponent
