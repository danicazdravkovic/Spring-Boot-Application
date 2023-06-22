import React, { useEffect, useState } from 'react'
import axios from 'axios';
import { Link } from 'react-router-dom';

export default function AdminChocolatesView() {



  const [chocolates, setChocolates] = useState([])
  useEffect(() => {

    // console.log("Hello world")//dk->inspect->console
    loadChocolates();
  }, []);
  const loadChocolates = async () => {
    const result = await axios.get("http://localhost:8080/chocolates");
    // console.log(result.data);
    setChocolates(result.data);

  }

  const deleteChocolate = async (id) => {
    if(window.confirm("Are you really want to delete chocolate?")){
     await axios.delete(`http://localhost:8080/chocolate/${id}`)
    // console.log(result.data)
    loadChocolates()
    }
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


        <div className='container'>
          <h2>Chocolates</h2>
          <div className='py-4'>
            <table className="table border shadow">
              <thead>
                <tr>
                  <th scope="col"> </th>
                  <th scope="col">Name</th>
                  <th scope="col">Description</th>
                  <th scope="col">Price</th>
                  <th scope="col">Discount</th>
                  <th scope="col">Category</th>
                  <th scope="col">Picture</th>
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
                      <td>{chocolate.price}</td>
                      <td>{chocolate.discount}%</td>
                      <td> {chocolate.categoryDto.name}</td>
                      <td><img className='chocolatePicture' alt='Choco' src={chocolate.pictureUrl} /></td>
                      <td>
                        {/* <button className='btn btn-primary mx-2'>View </button> */}


                        <Link className='btn btn-outline-primary mx-2' id="edit_button" to={`/editchocolate/${chocolate.chocolateID}`}>Edit </Link>
                        <button className='btn btn-danger mx-2' id="delete_button" onClick={() => deleteChocolate(chocolate.chocolateID)}>Delete </button>

                        <Link className='btn btn-primary mx-2' id="view_button" to={`/viewchocolate/${chocolate.chocolateID}`}>View </Link>


                      </td>
                    </tr>
                  ))
                }
              </tbody>
            </table>
            <div>
              <Link className='btn btn-warning mx-2' id="add_button" to={`/addchocolate`}>Add chocolate </Link>

            </div>
          </div>
        </div>
        </body>
    </div>

  )
}