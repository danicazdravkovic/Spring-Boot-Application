// https://www.agirl.codes/complete-guide-build-react-forms-with-usestate-hook

import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

export default function AddChocolate() {

    let navigate = useNavigate()


    const [chocolate, setChocolate] = useState({
        name: "",
        description: "",
        price: "",
        discount:"",
        pictureUrl:""
    })


    const { name, description, price,discount,pictureUrl } = chocolate

    const onInputChange = (e) => {
        setChocolate({ ...chocolate, [e.target.name]: e.target.value })
    }

    const [categories, setCategories] = useState([])

    useEffect(() => {
        loadCategories()
    }, [])

    const loadCategories = async () => {
        const result = await axios.get(`http://localhost:8080/categories`, { mode: 'no-cors' })
        // console.log(result.data)
        setCategories(result.data)
    }

    const onSubmit = async (e) => {
        e.preventDefault();//da se ne generise automatski nakon subita url neki
        var mylist = document.getElementById("myList");
        var index = mylist.selectedIndex;
        let choc = {
            ...chocolate, "categoryDto": {
                "categoryID": categories[index].categoryID,
                "name": categories[index].name,
                "description": categories[index].description
            }
        }
        await axios.post("http://localhost:8080/chocolate", choc);
        navigate("/index");

    };

    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Chocolate</h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className='mb-3'>
                            <label htmlFor='Name' className='form-label'>Name</label>
                            <input type={"text"} className='form-control' placeholder='Enter chocolate name' required name="name" value={name}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='Name' className='form-label'>Description</label>
                            <input type={"text"} className='form-control' placeholder='Enter chocolate description' required name="description" value={description}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className='mb-3'>
                            <label htmlFor='Price' className='form-label'>Price</label>
                            <input type={"number"} className='form-control' placeholder='Enter chocolate price' required name="price" value={price}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='Discount' className='form-label'>Discount</label>
                            <input type={"number"} className='form-control' placeholder='Enter chocolate discount' required name="discount" value={discount}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className='mb-3'>
                            <label htmlFor='pictureUrl' className='form-label'>Picture url</label>
                            <input type={"text"} className='form-control' placeholder='Enter picture url' required name="pictureUrl" value={pictureUrl}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>


                        <div className='mb-3'>
                            <label htmlFor='Category' className='form-label'>Category</label>
                            <div>
                                {/* <select id="myList" onchange="favTutorial()" > */}
                                <select id="myList" className='dropList'>
                                    {categories.map((category) => <option value={category.categoryID}>{category.name}</option>)}
                                </select>
                            </div>
                        </div>
                      
                        <button type='submit' className='btn btn-primary'>Save  </button>
                        <Link type='submit' className='btn btn-danger mx-2' to="/index  ">Cancel</Link>
                    </form>
                </div>
            </div>

        </div>
    )
}
