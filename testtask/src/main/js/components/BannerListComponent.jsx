
const React = require("react");

class BannerListComponent extends React.Component{
    render() {
        const banners = this.props.banners.map(banner =>
            <Banner key={banner.idBanner} banner={banner}/>
        );
        return (
            <div className="sidebar__menu">
                {banners}
            </div>
        )
    }
}
class Banner extends React.Component{
    render() {
        return (
            <a href={this.props.banner.idBanner} className="sidebar__menu-item">{this.props.banner.name}</a>
        )
    }
}
export default BannerListComponent
