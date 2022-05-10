
const React = require("react");

class CategoryListComponent extends React.Component{
    render() {
        const categories = this.props.categories.map(category =>
            <Category key={category.idCategory} category={category}/>
        );
        return (
            <div className="sidebar__menu">
                {categories}
            </div>
        )
    }
}
class Category extends React.Component{
    render() {
        return (
            <a href={this.props.category.idCategory} className="sidebar__menu-item">{this.props.category.name}</a>
        )
    }
}
export default CategoryListComponent
