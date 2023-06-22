import React, {  useState } from 'react'
import axios from 'axios';
import {  useNavigate } from 'react-router-dom';
import { store } from '../sessions/Session'

export default function Login() {

    let navigate = useNavigate()


    const [user, setUser] = useState({
        id: "",
        name: "",
        username: "",
        password: "",
        role: ""
    })


    const { username, password } = user

    const onInputChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value })
    }

    const customerLogin = async () => {
        var unloggedCustomer = {
            customerID: null,
            name: null,
            username: user.username,
            password: user.password,
        }

        if (username === "" || password === "") {
            document.getElementById('ErrorMessage').innerHTML = "*All fields are required.";
            return
        }
        try {
            const result = await axios.post("http://localhost:8080/customer/login", unloggedCustomer);
            store(result.data, "customer");
            navigate("/index");
        } catch (error) {
            document.getElementById('ErrorMessage').innerHTML = error.response.data;
        }

    }
    const adminLogin = async () => {
        var unloggedAdmin = {
            adminID: null,
            name: null,
            username: user.username,
            password: user.password,
        }

        if (username === "" || password === "") {
            document.getElementById('ErrorMessage').innerHTML = "*All fields are required.";
            return
        }
        try {
            const result = await axios.post("http://localhost:8080/admin/login", unloggedAdmin);
            // console.log(result.data)
            store(result.data, "admin");
            navigate("/index");
        } catch (error) {
            document.getElementById('ErrorMessage').innerHTML = error.response.data;
        }
    }

    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Log in</h2>
                    {/* <form onSubmit={(e) => onSubmit(e)}> */}

                    <div className='mb-3' >
                        <label htmlFor='Username' className='form-label'>Username</label>
                        <input type={"text"} className='form-control' placeholder='Enter your username' name="username" value={username}
                            id="input_username" onChange={(e) => onInputChange(e)}
                        />
                    </div>

                    <div className=' mb-3'>
                        <label htmlFor='Password' className='form-label'>Password</label>
                        <input type={"password"} className='form-control' placeholder='Enter your password' name="password" value={password}
                            id="input_password" onChange={(e) => onInputChange(e)}
                        />
                    </div>
                    <div>
                        <label htmlFor='ErrorMessage' className='ErrorMessage' id='ErrorMessage'></label>
                    </div>
                    {/* <Link className='btn btn-outline-primary mx-2' id="edit_button" to={`/editchocolate/${chocolate.chocolateID}`}>Edit </Link>
                    <button className='btn btn-danger mx-2' id="delete_button" onClick={() => deleteChocolate(chocolate.chocolateID)}>Delete </button> */}

                    <div>
                        <button type='submit' className='btn btn-outline-primary mx-2' id="customer_login_button" onClick={() => customerLogin()}>Log in as a customer</button>
                        <button type='submit' className='btn btn-outline-primary mx-2' id="admin_login_button" onClick={() => adminLogin()}>Log in as an admin</button>
                    </div>
                    <hr></hr>
                    <p>Don't have an account?</p>
                    <a href='/addcustomer'>Sign up</a>
                </div>
            </div>

        </div>
    )
}
