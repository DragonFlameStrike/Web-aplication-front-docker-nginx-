
import React from 'react';
const ReactDOM = require('react-dom'); // <2>
const client = require('./client');
const {assertSourceType} = require("@babel/core/lib/config/validation/option-assertions"); // <3>

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import BannerListComponent from './components/BannerListComponent.jsx';
import BannerEditComponent from "./components/BannerEditComponent.jsx";
import BannerCreateComponent from "./components/BannerCreateComponent.jsx";
import EmptyFieldComponent from "./components/EmptyFieldComponent.jsx";
import CategoryCreateComponent from "./components/CategoryCreateComponent.jsx";
import CategoryEditComponent from "./components/CategoryEditComponent.jsx";
import CategoryListComponent from "./components/CategoryListComponent.jsx";
import CategoryService from "./services/CategoryService";
import BannerService from "./services/BannerService";


class  MainFieldApp extends React.Component {
    render() {
        return (
                <Router>

                    <Switch>
                        <Route path="/categories/create" component={CategoryCreateComponent}/>
                        <Route path="/categories/:cid" component={CategoryEditComponent}/>
                        <Route path="/categories/" component={EmptyFieldComponent}/>
                        <Route path="/create" component={BannerCreateComponent}/>
                        <Route path="/:bid" component={BannerEditComponent}/>
                        <Route path="/" exact component={EmptyFieldComponent}/>
                    </Switch>

                </Router>
        );
    }
}
ReactDOM.render(
    <MainFieldApp />,
    document.getElementById('mainField')
)
class BarApp extends React.Component { // <1>
    constructor(props) {
        super(props);
        this.state = {elements: []};
    }
    componentDidMount() { // <2>
        if(window.location.href.includes("categories")) {
            CategoryService.getCategories().then(r => {
                this.setState({elements: r.data});
            });
        }
        else{
            BannerService.getBanners().then(r => {
                this.setState({elements: r.data});
            });
        }
    }
    render() { // <3>
        if(window.location.href.includes("categories")) {
            return (
                <CategoryListComponent categories={this.state.elements}/>
            )
        }
        else{
            return (
                <BannerListComponent banners={this.state.elements}/>
            )
        }
    }
}
ReactDOM.render(
    <BarApp />,
    document.getElementById('Bar')
)
// class BannersBarApp extends React.Component { // <1>
//     constructor(props) {
//         super(props);
//         this.state = {banners: []};
//     }
//     componentDidMount() { // <2>
//         console.log(window.location.href);
//
//             BannerService.getBanners().then(r => {
//                 this.setState({banners: r.data});
//             });
//
//     }
//     render() { // <3>
//         return (
//             <BannerListComponent banners={this.state.banners}/>
//         )
//     }
// }
// ReactDOM.render(
//     <BannersBarApp />,
//     document.getElementById('bannersBar')
// )
// class CategoryBarApp extends React.Component { // <1>
//     constructor(props) {
//         super(props);
//         this.state = {categories: []};
//     }
//     componentDidMount() { // <2>
//         CategoryService.getCategories().then(r => {
//             this.setState({categories: r.data});
//         });
//     }
//     render() { // <3>
//         return (
//             <CategoryListComponent categories={this.state.categories}/>
//         )
//     }
// }
// ReactDOM.render(
//     <CategoryBarApp />,
//     document.getElementById('categoriesBar')
// )


