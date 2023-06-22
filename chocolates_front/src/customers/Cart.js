import axios from 'axios'
import React, { useState } from 'react'
import moment from 'moment';
import { getLoggedUser, getCustomerItems, storeCustomerItems } from '../sessions/Session'

export default function Cart() {

    // const [orders, setOrders] = useState([])

    let orderItems = getCustomerItems()

    const loggedUser = getLoggedUser()
    const deleteOrderItem = async (orderItemIndex) => {

        orderItems.splice(orderItemIndex, 1)
        storeCustomerItems(orderItems)
        window.location.reload()
    }
    const increaseQuantity = async (orderItem) => {

        orderItem.quantity++
        storeCustomerItems(orderItems)
        window.location.reload()
    }
    const decreaseQuantity = async (orderItem) => {

        orderItem.quantity--
        if (orderItem.quantity === 0) {
            deleteOrderItem(orderItems.indexOf(orderItem))
        }
        storeCustomerItems(orderItems)
        window.location.reload()
    }

    const createOrder = async () => {
        if (orderItems.length == 0) {
            window.alert("Add some items to create order!");
            window.open("/index", "_self");
            return
        }

        var customer = await axios.get(`http://localhost:8080/customer/${loggedUser.id}`)

        var order = { date: null, status: "preparing", customerDto: customer.data }

        var result = await axios.post("http://localhost:8080/order", order)//returns created order with id
        orderItems.forEach(orderItem => {
            orderItem.orderDto = result.data
        });
        await axios.post("http://localhost:8080/orderItems", orderItems)
        orderItems = []
        storeCustomerItems([])
        window.alert("Order has been created!");
        window.open("/index", "_self");


        // window.location.reload()
    }
    function totalAmount() {
        var amount = 0

        orderItems.forEach(orderItem => {
            if (orderItem.chocolateDto.discount > 0) {
                let newPrice = orderItem.chocolateDto.price - (orderItem.chocolateDto.price * orderItem.chocolateDto.discount / 100)
                amount += newPrice
            } else {
                amount += orderItem.chocolateDto.price
            }
        });
        return amount
    }
    // function formatDate(date){
    //     return new moment(date).format('llll');
    // }
    return (

        <div className='container'>
        
            {
                orderItems.map((orderItem) => (

                    <div className='row'>
                        <div className='col-md-6 offset-md-3 border rounded p-4 mt-2 shadow'>
                            <h2 className='text-center m-4 '>Order item details</h2>

                            <div className='card'>
                                <div className='card-header'>
                                    {/* Order item details:  */}
                                    <ul className='list-group list-group-flush'>
                                        <li className='list-group-item'>
                                            <b> Chocolate name: </b>
                                            {orderItem.chocolateDto.name}
                                        </li>
                                        <li className='list-group-item'>
                                            <b> Chocolate description: </b>
                                            {orderItem.chocolateDto.description}

                                        </li>

                                        <li className='list-group-item'>
                                            <b> Price: </b>
                                            {orderItem.chocolateDto.price} &euro;

                                        </li>
                                        <li className='list-group-item'>
                                            <b> Discount: </b>
                                            {orderItem.chocolateDto.discount} %;

                                        </li>
                                        <li className='list-group-item'>
                                            <b> Chocolate category: </b>
                                            {orderItem.chocolateDto.categoryDto.name}

                                        </li>

                                        <li className='list-group-item'>
                                            <b> Order item quantity: </b>
                                            {orderItem.quantity}
                                            <button className="btn btn-outline-success btn-sm  mx-2  btn-floating" id="increase_button" onClick={() => increaseQuantity(orderItem)}>+</button>
                                            <button className="btn btn-outline-secondary btn-sm mx-2" id="decrease_button" onClick={() => decreaseQuantity(orderItem)}>-</button>

                                        </li>


                                    </ul>
                                </div>
                            </div>
                            <div>
                                <button className='btn btn-danger mx-2' id="delete_button" onClick={() => deleteOrderItem(orderItems.indexOf(orderItem))}>Delete item</button>

                            </div>

                        </div>
                    </div>
                ))
            }
            <div>
                <hr></hr>
                <button className='btn btn-warning mx-2' id="add_button" onClick={() => createOrder()}>Create order </button>

            </div>
        </div>

    )
}
