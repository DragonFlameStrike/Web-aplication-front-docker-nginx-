
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
                        <Route path="/search/{name}" component={EmptyFieldComponent}/>
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
                <section>
                <div className="sidebar__search">
                    <form>
                        <input className="sidebar__search-input"
                            type="text"
                            placeholder="Enter category name..."
                        />
                    </form>
                </div>
                <div className="sidebar__menu">
                    <CategoryListComponent categories={this.state.elements}/>
                </div>
                </section>
            )
        }
        else{
            return (

                <section>
                    <div className="sidebar__search">
                        <form>
                            <input className="sidebar__search-input"
                                   type="text"
                                   placeholder="Enter category name..."
                            />
                            <span className="sidebar__search-icon"/>
                        </form>
                    </div>
                    <div className="sidebar__menu">
                        <BannerListComponent banners={this.state.elements}/>
                    </div>
                </section>
            )
        }
    }
}
ReactDOM.render(
    <BarApp />,
    document.getElementById('Bar')
)
// class SearchApp extends React.Component { // <1>
//     constructor(props) {
//         super(props);
//         this.state = {elements: []};
//     }
//
//     componentDidMount() { // <2>
//         if(window.location.href.includes("categories")) {
//             // CategoryService.getCategories().then(r => {
//             //     this.setState({elements: r.data});
//             // });
//         }
//         else{
//             // BannerService.getBanners().then(r => {
//             //     this.setState({elements: r.data});
//             // });
//         }
//     }
//     render() { // <3>
//         if(window.location.href.includes("categories")) {
//             return (
//                 <form>
//                     <input className="sidebar__search-input"
//                            type="text"
//                            placeholder="Enter category name..."
//                     />
//                 </form>
//             )
//         }
//         else {
//             return (
//                 <form method="get">
//                     <input className="sidebar__search-input"
//                            type="text"
//                            placeholder="Enter banner name..."
//                            id="name"
//                     />
//                 </form>
//             )
//         }
//     }
// }
// ReactDOM.render(
//     <SearchApp />,
//     document.getElementById('Search')
// )



