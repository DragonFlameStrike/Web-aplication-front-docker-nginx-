import ReactDOM from "react-dom";
import app from "../app";

const React = require("react");

class BannerEditComponent extends React.Component{
    render() {
        return (
            <form>
                <header className="content__header">
                    <span className="content__header-text">Create new banner</span>
                </header>
                <div className="content__body">
                    <div className="content__form">
                        <div className="content__form-item">
                            <div className="content__form-item-title">Name</div>
                            <div className="content__form-item-content">
                                <input className="content__input" type="text" name="name"/>
                            </div>
                        </div>
                        <div className="content__form-item">
                            <div className="content__form-item-title">Price</div>
                            <div className="content__form-item-content">
                                <input className="content__input" type="number" name="price"/>
                            </div>
                        </div>
                        {/*<div className="content__form-item">*/}
                        {/*    <div className="content__form-item-title">Category</div>*/}
                        {/*    <div className="content__form-item-content">*/}
                        {/*        <select className="content__select" name="categories" multiple="multiple">*/}
                        {/*            <option th:each="element : ${categories}" th:value="${element.idCategory}"*/}
                        {/*                    th:text="${element.name}"></option>*/}
                        {/*        </select>*/}
                        {/*    </div>*/}
                        {/*</div>*/}
                        <div className="content__form-item">
                            <div className="content__form-item-title">Text</div>
                            <div className="content__form-item-content">
                                <textarea className="content__textarea" name="text"/>
                            </div>
                        </div>
                    </div>
                </div>

                <footer className="content__footer">
                    <div className="content__buttons">
                        <button type="submit" className="content__button content__button_dark">Save</button>
                        <button type="submit" className="content__button content__button_red">Delete</button>
                    </div>
                </footer>
                <div className="error">
                    <span className="error__text">Banner with name "some banner" is already exist</span>
                </div>
            </form>
        )
    }
}
export default BannerEditComponent
