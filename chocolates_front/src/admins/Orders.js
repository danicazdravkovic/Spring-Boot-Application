import axios from 'axios'
import React, { useEffect, useState } from 'react'
import moment from 'moment';
import { Link } from 'react-router-dom';

export default function Orders() {

    const [orders, setOrders] = useState([])

    useEffect(() => {
        loadOrders()
    }, [])

    //order from search bar

    const [searcheredOrder, setSearcheredOrder] = useState({})
    // const { orderID, customerID, chocolateID, date } = searcheredOrder
    const onInputChange = (e) => {
        setSearcheredOrder({ ...searcheredOrder, [e.target.name]: e.target.value })

    }
    const loadOrders = async () => {
        const result = await axios.get(`http://localhost:8080/orders`)
        setOrders(result.data)
    }


    const changeOrderStatus = async (searcheredOrderID) => {
        var find=false
      orders.forEach(order => {
        if(searcheredOrderID==order.orderID){
        window.open(`/editorder/${searcheredOrderID}`,"_self"); 
        find=true
        }
      });
      if (!find) window.alert("Order "+searcheredOrderID+" does not exist.");


    }

    function formatDate(date) {
        return new moment(date).format('llll');
    }
    return (

        <div>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link>
            <body>
                <div class="admin_sidebar">
                    <a href="/viewcategories"><i class="fa fa-align-center"></i>Category</a>
                    <a href="/orders"><i class="fa fa-cog"></i><br></br>Orders</a>
                    <a href="/viewchocolates"><i class="fa fa-th-large"></i>Chocolate</a>
                    <a href="/statistics"><i class="fa fa-bar-chart"></i>Satistics</a>

                </div>

                <input type={"number"} className='search-bar' placeholder='Enter order number' name="orderID" value={searcheredOrder.orderID}
                    onChange={(e) => onInputChange(e)} />
                <Link className='btn btn-dark mx-2' id="view_button" onClick={() => changeOrderStatus(searcheredOrder.orderID)}>Search order </Link>
                <div className='container'>
                    {
                        orders.map((order) => (

                            <div className='row'>
                                <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                                    <h2 className='text-center m-4 '>Order details</h2>

                                    <div className='card'>
                                        <div className='card-header'>
                                            Order number: {order.orderID}
                                            <ul className='list-group list-group-flush'>
                                                
                                                 
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
                                                    {order.status}
                                                </li>                
                                            </ul>
                                        </div>
                                    </div>

                                    <div>

                                        <Link className='btn btn-success mx-2' id="edit_button" to={`/editorder/${order.orderID}`}>View order</Link>
                                    </div>
                                </div>
                            </div>

                        ))
                    }
                </div>
            </body>
        </div>
    )
}
