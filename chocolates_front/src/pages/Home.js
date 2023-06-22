import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { getLoggedUser, getCustomerItems, storeCustomerItems } from '../sessions/Session'

export default function Home() {

  const loggedUser = getLoggedUser()

  const [chocolates, setChocolates] = useState([])
  const [customers, setCustomers] = useState([])
  const [categories, setCategories] = useState([])
  const [orders, setOrders] = useState([])
  // const [orderItems, setOrderItems] = useState([])
  var orderItems = []
  if (getCustomerItems() != null) {
    orderItems = getCustomerItems()
  }
  // console.log(orderItems)

  useEffect(() => {

    // console.log("Hello world")//dk->inspect->console
    loadChocolates();
    loadCustomers();
    loadCategories();
    loadOrders();
    loadOrderItems();
    // loadFavouriteChocolate()
    // loadInfoData();
  }, []);
  const loadChocolates = async () => {
    const result = await axios.get("http://localhost:8080/chocolates");
    // console.log(result.data);
    setChocolates(result.data);
  }
  const loadCustomers = async () => {
    const result = await axios.get("http://localhost:8080/customers");
    setCustomers(result.data);
  }
  const loadCategories = async () => {
    const result = await axios.get("http://localhost:8080/categories");
    setCategories(result.data);
  }
  const loadOrders = async () => {
    const result = await axios.get("http://localhost:8080/orders");
    setOrders(result.data);
  }
  const loadOrderItems = async () => {
    let oItems = getCustomerItems()
    orderItems = oItems;
  }
  // const addOrdeItem = (newItem) => setOrderItems(oldItems => [...oldItems, newItem])

  const buyChocolate = async (id) => {
    var chocolate = await axios.get(`http://localhost:8080/chocolate/${id}`)
    // var customer = await axios.get(`http://localhost:8080/customer/${loggedUser.id}`)


    var newItem = { chocolateDto: chocolate.data, orderDto: null, quantity: 1 }
    let signal = false

    orderItems.forEach(o => {
      if (o.chocolateDto.chocolateID == newItem.chocolateDto.chocolateID) {
        o.quantity++
        signal = true
      }
    })
    if (!signal) {
      orderItems.push(newItem)
    }
    storeCustomerItems(orderItems)

    // await axios.post("http://localhost:8080/order", order)
  }

  function finalPrice(choco) {
    if (choco.discount > 0) {
      return (choco.price - (choco.price * choco.discount / 100))
    }
    return choco.price
  }



  if (loggedUser.role === "customer") {
    return (
      <div>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link>
        <body>

          <div class="customer_sidebar">
            <a href="/editcustomer"><i class="fa fa-cog"></i>Account</a>
            <a href="/cart"><i class="fa fa-shopping-cart"></i> <br></br>My cart</a>
          </div>
          <div className='container'>
            <div className='py-4'>
              <table className="table border shadow">
                <thead>
                  <tr>
                    <th scope="col"></th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Category</th>
                    <th scope="col">Picture</th>
                    <th scope="col">Price</th>
                    <th scope="col">Discount</th>
                    <th scope="col"></th>

                  </tr>
                </thead>
                <tbody>
                  {

                    chocolates.map((chocolate, index) => (
                      <tr>
                        <th scope="row" key={index}>{index + 1}</th>
                        <td>{chocolate.name}</td>
                        <td>{chocolate.description}</td>
                        <td> {chocolate.categoryDto.name}</td>
                        <td>
                          <img src={chocolate.pictureUrl} alt="Choco image" className='chocolatePicture' />
                        </td>
                        <td>{chocolate.price}&euro;</td>
                        <td>{chocolate.discount}% <br></br><p className='discount_text'>{finalPrice(chocolate)}&euro;</p> </td>

                        <td>

                          <button className='btn btn-primary mx-2' id="buy_button" onClick={() => buyChocolate(chocolate.chocolateID)}>Add to cart </button>


                        </td>
                      </tr>
                    ))
                  }


                </tbody>
              </table>
            </div>
            <div>
            </div>

          </div>
        </body></div>
    )
  }
  if (loggedUser.role === "admin") {
    return (

      <div>

        <div className="row">
          <div className="column">
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQL9HQrGfOyzklECtHhNkrV06GTpzFrRXbIIbJncpiabA&s" alt="Customers" height="120px" width="120px" />
            <br></br>
            <p>{customers.length}</p>
            <p>Customers</p>
          </div>

          <div className="column">
            <a href='/viewcategories'><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWmQC2QoAFgE5nKqtY6KB4ICIA4vW7L9YQUdxsjeSyJQ&s" alt="Categories" height="120px" width="120px" /></a>
            <p>{categories.length}</p>
            <p>Total categories</p>
          </div>

          <div className="column">
            <a href='/orders'><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMrvTh2J44Ed3POi3Yc5dCAOjDlM6VWIQCng&usqp=CAU" alt="Done" height="120px" width="120px" /></a>
            <p>{orders.length}</p>
            <p>Total orders</p>
          </div>

          <div className="column">
            <a href='/viewchocolates'><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQS3JHKZ5AJS-QjjRXzb7DjSsyNp2GilsTmAWx0KZM&s" alt="Cart" height="120px" width="120px" /></a>
            <p>{chocolates.length}</p>
            <p>Total chocolates</p>
          </div>

          <div className="column">
            <a href='/addcategory'><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKsW-1g8GK2Htt5mkBn45PCX7DXH29mxDu0v_fRaI&s" alt="Plus" height="120px" width="120px" /></a>
            <p>Add category</p>
          </div>

          <div className="column">
            <a href='/addchocolate'><img src="https://cdn-icons-png.flaticon.com/512/1152/1152198.png" alt="Chocolate" height="120px" width="120px" /></a>
            <p>Add chocolate</p>
          </div>

        </div>
      </div>
    )
  }
  else return (<div>no logged user</div>)
}
