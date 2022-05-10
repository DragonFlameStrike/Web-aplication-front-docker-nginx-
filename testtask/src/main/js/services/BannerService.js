
import axios from "axios";

const BANNER_API_BASE_URL = "http://localhost:8080/api/"

class BannerService{
    getBanners(){
        return axios.get(BANNER_API_BASE_URL);
    }
    getBannerById(id){
        return axios.get(BANNER_API_BASE_URL + id);
    }

    updateBanner(banner, id) {
        console.log(banner)
        return axios.post(BANNER_API_BASE_URL + id, banner);
    }
}

export default new BannerService()