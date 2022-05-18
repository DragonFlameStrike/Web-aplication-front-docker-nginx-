
import axios from "axios";



const BANNER_API_BASE_URL = "http://" + window.location.hostname +":8080/root/api/"

class BannerService{
    getBanners(searchValue){
        return axios.get(BANNER_API_BASE_URL + 'search/' + searchValue);
    }
    getBannerById(id){
        console.log(id,"get")
        return axios.get(BANNER_API_BASE_URL + id);
    }

    updateBanner(banner, id) {
        console.log(banner,"update")
        return axios.post(BANNER_API_BASE_URL + id, banner);

    }
    createBanner(banner){
        console.log(banner,"create")
        return axios.post(BANNER_API_BASE_URL + 'create', banner)

    }
    deleteBanner(id){
        console.log(id,"delete")
        return axios.delete(BANNER_API_BASE_URL + id);
    }
}

export default new BannerService()