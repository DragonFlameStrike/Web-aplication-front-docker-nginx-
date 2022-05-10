
import axios from "axios";

const CATEGORY_API_BASE_URL = "http://localhost:8080/api/categories/"

class CategoryService{
    getCategories(){
        return axios.get(CATEGORY_API_BASE_URL);
    }
    getCategoryById(id){
        console.log(id,"get")
        return axios.get(CATEGORY_API_BASE_URL + id);
    }

    updateCategory(category, id) {
        console.log(category,"update")
        return axios.post(CATEGORY_API_BASE_URL + id, category);
    }
    createCategory(category){
        console.log(category,"create")
        return axios.post(CATEGORY_API_BASE_URL + 'create', category);
    }
    deleteCategory(id){
        console.log(id,"delete")
        return axios.delete(CATEGORY_API_BASE_URL + id);
    }
}

export default new CategoryService()