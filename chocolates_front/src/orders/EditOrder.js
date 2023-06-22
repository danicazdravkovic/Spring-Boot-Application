import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import moment from 'moment';


export default function EditOrder() {

    let navigate = useNavigate()
    const { id } = useParams()

    const [order, setOrder] = useState({
        orderID: "",
        date: "",
        status: "",
        customerDto: {
            customerID: "",
            name: "",
            username: "",
            password: ""
        }
    })
    const [orderItems, setOrderItems] = useState([])

    const loadOrder = async () => {
        const result = await axios.get(`http://localhost:8080/order/${id}`)
        setOrder(result.data)
    }
    const loadOrderItems = async () => {
        const result = await axios.get(`http://localhost:8080/orderItems/${id}`)
        setOrderItems(result.data)
    }
    loadOrder()
    loadOrderItems()
    const onSubmit = async (e) => {
        var mylist = document.getElementById("myList");
        order.status = mylist.value
        await axios.put(`http://localhost:8080/order`, order);
        navigate("/orders");
    };


    function formatDate(date) {
        return new moment(date).format('llll');
    }
    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                    <h2 className='text-center m-4 '>Order details</h2>

                    <div className='card'>
                        <div className='card-header'>
                            Order number: {order.orderID}
                            <ul className='list-group list-group-flush'>

                            <li>
                               
                               <div className='py-4'>
                                   <table className="table border shadow" >
                                       <thead >
                                           <tr>
                                               <th scope="col"></th>
                                               {/* <th scope="col">Order item id</th> */}
                                               <th scope="col">Chocolate </th>
                                               <th scope="col">Price</th>
                                               <th scope="col">Quantity</th>

                                           </tr>
                                       </thead>
                                       <tbody>
                                           {

                                               orderItems.map((orderItem,index) => (
                                                   <tr>
                                                       <th scope="row" key={index}>{index + 1}</th>
                                                       <td>{orderItem.chocolateDto.name}</td>
                                                       <td>{orderItem.chocolateDto.price} &euro;</td>

                                                       <td>{orderItem.quantity}</td>
                                                   </tr>
                                               ))
                                           }
                                       </tbody>
                                   </table>
                                  
                               </div>

                           </li>
                                <li className='list-group-item'>
                                    <b> Order date and time: </b>
                                    {formatDate(order.date)}
                                </li>

                                <li className='list-group-item'>
                                    <b> Customer name and id: </b>
                                    {order.customerDto.name}, {order.customerDto.customerID}
                                </li>

                              

                                <li className='list-group-item'>
                                    <b> Status: </b>
                                    <select id="myList" className='dropList_status'>
                                        <option value="preparing">preparing</option>
                                        <option value="ready to deliver">ready to deliver</option>
                                        <option value="on the way">on the way</option>
                                        <option value="paid">paid</option>
                                    </select>
                                </li>

                            </ul>
                        </div>
                    </div>

                    <div>

                        <button type='submit' className='btn btn-success' onClick={() => onSubmit()}>Save changes </button>
                        <Link type='submit' className='btn btn-danger mx-2' to="/orders">Cancel</Link>

                    </div>
                </div>
            </div>

        </div>

    )
}
