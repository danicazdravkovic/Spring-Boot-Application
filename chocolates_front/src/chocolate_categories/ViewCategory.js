import axios from 'axios'
import React, { useState } from 'react'
import { Link, useParams } from 'react-router-dom'

export default function ViewCategory() {
    const [category, setCategory] = useState({

        name: "",
        description:""
    })
const {id}=useParams()

const loadCategory=async()=>{
    const result= await axios.get(`http://localhost:8080/category/${id}`)
    setCategory(result.data)
}
loadCategory()

    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Category details</h2>
                    <div className='card'>

                        <div className='card-header'>
                            Category id: {category.categoryID}
                            <ul className='list-group list-group-flush'>
                                <li className='list-group-item'>
                                    <b> Name: </b>
                                    {category.name}
                                </li>
                                <li className='list-group-item'>
                                    <b> Description: </b>
                                    {category.description}

                                </li>
                            </ul>
                        </div>

                    </div>
                    <Link className='btn btn-primary my-2' to={"/viewcategories"}>Back to categories</Link>
                </div>
            </div>
        </div>
    )
}
