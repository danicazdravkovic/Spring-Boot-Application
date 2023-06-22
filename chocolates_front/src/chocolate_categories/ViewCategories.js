import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
export default function ViewCategories() {
  const [categories, setCategories] = useState([])

  useEffect(() => {
    loadCategories()
  }, [])
  const loadCategories = async () => {
    const result = await axios.get("http://localhost:8080/categories")
    setCategories(result.data)
  }
  const deleteCategory = async (id) => {
    if(window.confirm("Are you really want to delete category?")){
     await axios.delete(`http://localhost:8080/category/${id}`)
    // console.log(result.data)
    loadCategories()
    }
  }

  return (
    <div>

      {/* <div class="sidebar">
        <a class="active" href="/viewcategories ">Categories</a>
        <a href="/orders">Orders</a>
        <a href="/viewchocolates">Chocolates</a>
      </div> */}
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"></link>
      <body>
        <div className="admin_sidebar">
          <a href="/viewcategories"><i class="fa fa-align-center"></i>Category</a>
          <a href="/orders"><i class="fa fa-cog"></i><br></br>Orders</a>
          <a href="/viewchocolates"><i class="fa fa-th-large"></i>Chocolate</a>
          <a href="/statistics"><i class="fa fa-bar-chart"></i>Satistics</a>

        </div>

        <div className='container'>
          <h2>Categories</h2>
          <div className='py-4'>
            <table className="table border shadow">
              <thead>
                <tr>
                  <th scope="col"></th>
                  <th scope="col">Category name</th>
                  <th scope="col">Category description</th>
                  <th scope="col"></th>

                </tr>
              </thead>
              <tbody>
                {

                  categories.map((category, index) => (
                    <tr>
                      <th scope="row" key={index}>{index + 1}</th>
                      <td>{category.name}</td>
                      <td>{category.description}</td>
                      <td>
                        <Link className='btn btn-outline-primary mx-2' id="edit_button" to={`/editcategory/${category.categoryID}`}>Edit </Link>
                        <button className='btn btn-danger mx-2' id="delete_button" onClick={() => deleteCategory(category.categoryID)}>Delete </button>
                        <Link className='btn btn-primary mx-2' id="view_button" to={`/viewcategory/${category.categoryID}`}>View </Link>
                      </td>
                    </tr>
                  ))
                }
              </tbody>
            </table>
          </div>
          <div>
            <Link className='btn btn-warning mx-2' id="add_button" to={`/addcategory`}>Add category </Link>

          </div>

        </div>
      </body>
    </div>

  )
}
