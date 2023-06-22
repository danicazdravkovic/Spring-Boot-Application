import React, { useState } from 'react'
import { Link } from 'react-router-dom'

export default function AddCategory() {


    const [category, setCategory] = useState({
        name: "", 
        description:""
    })

    const { name,description } = category
    const onInputChange = (e) => {
        setCategory({ ...category, [e.target.name]: e.target.value })
    }

    const onSubmit = async (e) => {
        category.name.trim()
        category.description.trim()

        console.log( category.name)
        console.log( category.description)
        console.log("aa")

        if(category.name==="" || category.description==="") {
         document.getElementById('ErrorMessage').innerHTML = "*All fields are required.";
        }

        e.preventDefault();//da se ne generise automatski nakon subita url neki
        // await axios.post("http://localhost:8080/category", category);
        // navigate("/viewcategories");

    };
    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Catgory</h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className='mb-3'>
                            <label htmlFor='Name' className='form-label'>Name</label>
                            <input type={"text"} className='form-control' placeholder='Enter category name' required name="name" value={name}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='Name' className='form-label'>Description</label>
                            <input type={"text"} className='form-control' placeholder='Enter category description' required name="description" value={description}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div>
                        <label htmlFor='ErrorInfo' className='ErrorInfo' id='ErrorInfo'></label>

                        </div>

                        <button type='submit' className='btn btn-outline-primary'>Save </button>
                        <Link type='submit' className='btn btn-outline-danger mx-2' to="/viewcategories">Cancel</Link>
                    </form>
                </div>
            </div>

        </div>
    )
}
