
import BannerService from "../services/BannerService";
import {InputLabel, MenuItem, OutlinedInput, Typography} from "@material-ui/core";
import { Select } from "@material-ui/core";
import CategoryService from "../services/CategoryService";

const React = require("react");
const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};
class BannerCreateComponent extends React.Component{
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.bid,
            name: '',
            price: '',
            text: '',
            error: false,
            currCategories: [],
            sameNameError: false,
            allCategories:[]
        }
        this.changeName = this.changeName.bind(this);
        this.changePrice = this.changePrice.bind(this);
        this.changeCategories = this.changeCategories.bind(this);
        this.changeText = this.changeText.bind(this);
        this.createBanner = this.createBanner.bind(this);
    }

    componentDidMount() {
        CategoryService.getCategories("").then(listCategories => {
            this.state.allCategories = listCategories.data;
        })
    }

    changeName = (event) => {
        this.setState({name: event.target.value});
    };

    changePrice= (event) => {
        this.setState({price: event.target.value});
    }

    changeCategories= (event) => {

        let arr =  event.target.value;
        let newCategories = [];
        for(let i=0;i<arr.length;i++){
            let tmpIdRequest = arr[i]
            for(let j=0;j<this.state.allCategories.length;j++){
                if(this.state.allCategories[j].idRequest === tmpIdRequest){
                    newCategories.push(this.state.allCategories[j]);
                }
            }
        }
        this.setState({currCategories: newCategories});
    }
    changeText= (event) => {
        this.setState({text: event.target.value});
    }
    createBanner =(e) => {
        this.setState({ error: false});
        e.preventDefault();
        let banner = {name: this.state.name.toString(), price: parseInt(this.state.price),
            categories: this.state.currCategories, text:this.state.text.toString()};
        BannerService.createBanner(banner).then(res =>{
            console.log(this.state.error);
            if(this.state.error === false) {
                this.props.history.push('/');
                window.location.reload();
            }
        })
            .catch((error) => {
                this.setState({error: true});
                console.log(this.state.error);
            })
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

    renderMenuItem = (category) => {
        return (
            <MenuItem key={category.idRequest} value={category.idRequest}>
                <Typography>{category.name}</Typography>
            </MenuItem>
        );
    };

    render() {
        const {currCategories,allCategories} = this.state;
        return (
            <section className="content">
                <form>
                    <header className="content__header">
                        <span className="content__header-text">Create new banner</span>
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
                                    <input className="content__input" type="number" name="price"
                                           value={this.state.price}
                                           onChange={this.changePrice}/>
                                </div>
                            </div>
                            <div className="content__form-item">
                                <div className="content__form-item-title">Category</div>
                                <div className="content__form-item-content">
                                    <div className="content__select">
                                        <Select
                                            multiple
                                            labelId="demo-multiple-name-label"
                                            id="demo-multiple-name"
                                            value={currCategories.map(category => {
                                                return category.idRequest
                                            })}
                                            fullWidth
                                            input={<OutlinedInput label="name" />}
                                            renderValue={(data) => <div>{data.join(", ")}</div>}
                                            MenuProps={MenuProps}
                                            onChange={this.changeCategories}
                                        >
                                            {allCategories.map(this.renderMenuItem)}
                                        </Select>
                                    </div>
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
                            <button type="submit" className="content__button content__button_dark"
                                    onClick={this.createBanner}>Save
                            </button>
                            {/*<button class="content__button content__button_red">Delete</button>*/}
                        </div>
                    </footer>
                    {this.errorView()}
                </form>
            </section>
        );
    }
}
export default BannerCreateComponent
