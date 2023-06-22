import axios from 'axios'
import React, {  useState } from 'react'
import { Link, useParams } from 'react-router-dom'

export default function ViewChocolate() {

const [chocolate,setChocolate] = useState({

    name: "",
    description:"",
    price:"",
    categoryDto:{
        categoryID:"",
        name:"",
        description:""
    }
})
const {id}=useParams()



const loadChocolate=async()=>{
const result= await axios.get(`http://localhost:8080/chocolate/${id}`)
setChocolate(result.data)
}
loadChocolate()
    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Chocolate details</h2>
                    <div className='card'>

                        <div className='card-header'>
                            Details of chocolate id: {chocolate.chocolateID}
                            <ul className='list-group list-group-flush'>
                                <li className='list-group-item'>
                                    <b> Name: </b>
                                    {chocolate.name}
                                </li>
                                <li className='list-group-item'>
                                    <b> Description: </b>
                                    {chocolate.description}

                                </li>

                                <li className='list-group-item'>
                                    <b> Price: </b>
                                    {chocolate.price}

                                </li>
                                <li className='list-group-item'>
                                    <b> Discount: </b>
                                    {chocolate.discount} %

                                </li>
                                <li className='list-group-item'>
                                    <b> Category: </b>
                                    {chocolate.categoryDto.name}

                                </li>
                            </ul>
                        </div>

                    </div>
                    <Link className='btn btn-primary my-2' to={"/viewchocolates"}>Back to chocolates</Link>
                </div>
            </div>
        </div>
    )
}
