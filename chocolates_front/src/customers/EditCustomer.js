import axios from 'axios'
import React, {  useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { getLoggedUser } from '../sessions/Session'

export default function EditCustomer() {

    let navigate = useNavigate()

    const [customer, setCustomer] = useState({
        customerID: "",
        name: "",
        username: "",
        password: ""
    })
    const id = getLoggedUser().id
    const {  username, password } = customer
    const onInputChange = (e) => {
        setCustomer({ ...customer, [e.target.name]: e.target.value })
    }


    const onSubmit = async (e) => {
        e.preventDefault();//da se ne generise automatski nakon subita url neki

            await axios.put(`http://localhost:8080/customer`, customer);
            navigate("/index");
    };

    const loadCustomer = async () => {
        const result = await axios.get(`http://localhost:8080/customer/${id}`)
        setCustomer(result.data)
    }
    loadCustomer()
    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Edit customer details</h2>
                    <form onSubmit={(e) => onSubmit(e)}>

                        <div className='mb-3' >
                            <label htmlFor='Username' className='form-label'>Username</label>
                            <input type={"text"} className='form-control' placeholder='Enter customer username' name="username" value={username}
                                onChange={(e) => onInputChange(e)}
                            />
                        </div>

                        <div className='mb-3' >
                            <label htmlFor='Password' className='form-label'>New password</label>
                            <input type={"password"} className='form-control' placeholder='Enter new password' name="password" value={password}
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
