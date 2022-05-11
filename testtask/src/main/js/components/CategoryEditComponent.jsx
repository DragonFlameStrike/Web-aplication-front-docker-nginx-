
import CategoryService from "../services/CategoryService";


const React = require("react");

class CategoryEditComponent extends React.Component{

    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.cid,
            name: '',
            oldname: '',
            error: false,
            idRequest: '',
            sameNameError: false

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
        this.setState({ error: false});
        let category = {name: this.state.name.toString(), idRequest: this.state.idRequest.toString()};
        CategoryService.updateCategory(category, this.state.id).then( res => {
            console.log(this.state.error);
            if(this.state.error === false) {
                this.props.history.push('/categories/');
                window.location.reload();
            }
        })
            .catch((error) => {
                this.setState({error: true});
                console.log(this.state.error);
            })

    }
    deleteCategory=(e) => {
        e.preventDefault();
        CategoryService.deleteCategory(this.state.id).then( res => {
            this.props.history.push('/');
            window.location.reload();
        });
    }
    errorView = (e) => {
        if(this.state.error === true){
            return (
                <div className="error">
                    <span className="error__text">Wrong input</span>
                </div>
            )
        }
        if(this.state.sameNameError){
            return (
                <div className="error">
                    <span className="error__text">Banner with name "some banner" is already exist</span>
                </div>
            )
        }
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
                    {this.errorView()}
                </form>
            </section>
        );
    }
}
export default CategoryEditComponent
