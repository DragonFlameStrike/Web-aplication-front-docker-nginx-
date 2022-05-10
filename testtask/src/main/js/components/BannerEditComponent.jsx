import ReactDOM from "react-dom";
import app from "../app";
import BannerService from "../services/BannerService";
import { useParams } from "react-router-dom";

const React = require("react");

class BannerEditComponent extends React.Component{

    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.bid,
            name: '',
            price: '',
            text: '',
            categories: []
        }
        this.changeName = this.changeName.bind(this);
        this.changePrice = this.changePrice.bind(this);
        this.changeCategories = this.changeCategories.bind(this);
        this.changeText = this.changeText.bind(this);
    }
    componentDidMount() {
        BannerService.getBannerById(this.state.id).then((res) => {
            let banner = res.data;
            this.setState({
                name: banner.name,
                price: banner.price,
                text: banner.text,
                categories: banner.categories
            });
        });
    }

    changeName = (event) => {
        this.setState({name: event.target.value});
    };

    changePrice= (event) => {
        this.setState({price: event.target.value});
    }

    changeCategories= (event) => {
        this.setState({categories: event.target.value});
    }
    changeText= (event) => {
        this.setState({text: event.target.value});
    }
    saveBanner =(e) => {
        e.preventDefault();
        let banner = {id:this.state.id, name: this.state.name.toString(), price: parseInt(this.state.price),
            categories: this.state.categories, text:this.state.text.toString()};
        BannerService.updateBanner(banner, this.state.id).then( res => {
            this.props.history.push('/');
            window.location.reload();
        });
    }

    render() {
        return (
            <form>
                <header className="content__header">
                    <span className="content__header-text">Edit new banner</span>
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
                            <div className="content__form-item-title">Price</div>
                            <div className="content__form-item-content">
                                <input className="content__input" type="number" name="price" value={this.state.price}
                                       onChange={this.changePrice}/>
                            </div>
                        </div>
                        <div className="content__form-item">
                            <div className="content__form-item-title">Category</div>
                            <div className="content__form-item-content">
                                <select className="content__select" name="categories" multiple="multiple">
                                    {/*<option th:each="element : ${categories}" th:value="${element.idCategory}"*/}
                                    {/*        th:text="${element.name}"></option>*/}
                                    <input className="content__input" type="text" name="categories" value={this.state.categories}
                                           onChange={this.changeCategories}/>
                                </select>
                            </div>
                        </div>
                        <div className="content__form-item">
                            <div className="content__form-item-title">Text</div>
                            <div className="content__form-item-content">
                            <textarea className="content__textarea" name="text" value={this.state.text}
                                      onChange={this.changeText}>{this.state.text}</textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <footer className="content__footer">
                    <div className="content__buttons">
                        <button type="submit" className="content__button content__button_dark" onClick={this.saveBanner} >Save</button>
                        {/*<button class="content__button content__button_red">Delete</button>*/}
                    </div>
                </footer>
                <div className="error">
                    <span className="error__text">Banner with name "some banner" is already exist</span>
                </div>
            </form>
        );
    }
}
export default BannerEditComponent
