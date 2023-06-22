import axios from 'axios'
import React, {  useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'

export default function EditChocolate() {

    let navigate = useNavigate()
    const { id } = useParams()

    const [chocolate, setChocolate] = useState({
        chocolateID: "",
        name: "",
        description: "",
        price: "",
        discount: "",
        pictureUrl: ""
    })

    const { name, description, price, discount, pictureUrl } = chocolate
    const onInputChange = (e) => {
        setChocolate({ ...chocolate, [e.target.name]: e.target.value })
    }


    const onSubmit = async (e) => {
        e.preventDefault();//da se ne generise automatski nakon subita url neki
        console.log(chocolate)

        await axios.put(`http://localhost:8080/chocolate`, chocolate);
        navigate("/index");
    };

    const loadChocolate = async () => {
        const result = await axios.get(`http://localhost:8080/chocolate/${id}`)
        setChocolate(result.data)
    }
    loadChocolate()
    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Edit chocolate</h2>
                    <form onSubmit={(e) => onSubmit(e)}>
                        <div className='mb-3' >
                            <label htmlFor='Name' className='form-label'>Name</label>
                            <input type={"text"} className='form-control' placeholder='Enter chocolate name' name="name" value={name}
                                onChange={(e) => onInputChange(e)}

                            />
                        </div>

                        <div className='mb-3' >
                            <label htmlFor='Description' className='form-label'>Description</label>
                            <input type={"text"} className='form-control' placeholder='Enter chocolate description' name="description" value={description}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className='mb-3' >
                            <label htmlFor='Price' className='form-label'>Price</label>
                            <input type={"text"} className='form-control' placeholder='Enter chocolate price' name="price" value={price}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className='mb-3' >
                            <label htmlFor='Discount' className='form-label'>Discount</label>
                            <input type={"text"} className='form-control' placeholder='Enter chocolate discount' name="discount" value={discount}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className='mb-3' >
                            <label htmlFor='PictureUrl' className='form-label'>Picture url</label>
                            <input type={"text"} className='form-control' placeholder='Enter chocolate picture url' name="pictureUrl" value={pictureUrl}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>
                        <button type='submit' className='btn btn-outline-primary'>Save changes </button>
                        <Link type='submit' className='btn btn-outline-danger mx-2' to="/index">Cancel</Link>
                    </form>
                </div>
            </div>

        </div>
    )
}
