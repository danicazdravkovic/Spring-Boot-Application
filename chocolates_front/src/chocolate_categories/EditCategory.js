import axios from 'axios'
import React, {  useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'

export default function EditCategory() {

    let navigate = useNavigate()
    const {id}=useParams()

    const [category, setCategory] = useState({
        name: "",
       description:""
    })

    const { name, description } = category
    const onInputChange = (e) => {
        setCategory({ ...category, [e.target.name]: e.target.value })
    }

    const onSubmit = async (e) => {
        e.preventDefault();//da se ne generise automatski nakon subita url neki
        await axios.put(`http://localhost:8080/category`, category);
        console.log(category)
        navigate("/viewcategories");
    };

    const loadCategory=async()=>{
        const result=await axios.get(`http://localhost:8080/category/${id}`)
        setCategory(result.data)
    }
    loadCategory()

    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Edit category</h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className='mb-3' >
                            <label htmlFor='Name' className='form-label'>Name</label>
                            <input type={"text"} className='form-control' placeholder='Enter category name' name="name" value={name}
                                onChange={(e) => onInputChange(e)}

                            />
                        </div>

                        <div className='mb-3' >
                            <label htmlFor='Description' className='form-label'>Description</label>
                            <input type={"text"} className='form-control' placeholder='Enter category description' name="description" value={description}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                       

                        <button type='submit' className='btn btn-outline-primary'>Save changes </button>
                        <Link type='submit' className='btn btn-outline-danger mx-2' to="/viewcategories">Cancel</Link>
                    </form>
                </div>
            </div>

        </div>
    )
}
