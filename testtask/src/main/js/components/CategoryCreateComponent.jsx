import CategoryService from "../services/CategoryService";


const React = require("react");

class CategoryCreateComponent extends React.Component{
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

    changeName = (event) => {
        this.setState({name: event.target.value});
    };

    changeIdRequest= (event) => {
        this.setState({idRequest: event.target.value});
    }
    createCategory =(e) => {
        e.preventDefault();
        let category = {name: this.state.name.toString(), idRequest: this.state.idRequest.toString()};
        CategoryService.createCategory(category).then(res =>{
            this.props.history.push('/categories/');
            window.location.reload();
        });
    }

    render() {
        return (
            <section className="content">
                <form>
                    <header className="content__header">
                        <span className="content__header-text">Create new category</span>
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
                                <div className="content__form-item-title">IdRequest</div>
                                <div className="content__form-item-content">
                                    <input className="content__input" type="text" name="idRequest" value={this.state.idRequest}
                                           onChange={this.changeIdRequest}/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <footer className="content__footer">
                        <div className="content__buttons">
                            <button type="submit" className="content__button content__button_dark"
                                    onClick={this.createCategory}>Save
                            </button>
                        </div>
                    </footer>
                    <div className="error">
                        <span className="error__text">Category with name "some category" is already exist</span>
                    </div>
                </form>
            </section>
        );
    }
}
export default CategoryCreateComponent
